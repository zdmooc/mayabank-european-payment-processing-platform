package com.mayabank.processing.fraud;
import com.mayabank.processing.domain.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class FraudService {
  private final Set<String> blockedTokens=ConcurrentHashMap.newKeySet();
  private final Map<String,Deque<Instant>> attemptsByToken=new ConcurrentHashMap<>();
  private final BigDecimal reviewAmount;
  private final int velocityLimit;
  private final Duration velocityWindow;

  public FraudService(){ this(new BigDecimal("1000.00"),5,Duration.ofMinutes(1)); }
  public FraudService(BigDecimal reviewAmount,int velocityLimit,Duration velocityWindow){
    this.reviewAmount=reviewAmount; this.velocityLimit=velocityLimit; this.velocityWindow=velocityWindow;
  }

  public void block(String token){ blockedTokens.add(token); }

  public FraudAssessment assess(CardPayment payment){
    var reasons=new ArrayList<String>();
    int score=0;
    String token=payment.cardholder().cardToken().token();

    if(blockedTokens.contains(token)){ reasons.add("BLOCKED_TOKEN"); score+=100; }
    if(payment.amount().compareTo(reviewAmount)>=0){ reasons.add("HIGH_AMOUNT"); score+=40; }

    var now=Instant.now();
    var q=attemptsByToken.computeIfAbsent(token,k->new ArrayDeque<>());
    synchronized(q){
      while(!q.isEmpty() && q.peekFirst().isBefore(now.minus(velocityWindow))) q.removeFirst();
      q.addLast(now);
      if(q.size()>velocityLimit){ reasons.add("VELOCITY"); score+=50; }
    }

    var decision=score>=100?FraudDecision.DECLINE:score>=40?FraudDecision.REVIEW:FraudDecision.APPROVE;
    return new FraudAssessment(decision,score,List.copyOf(reasons));
  }
}