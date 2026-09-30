package com.mayabank.processing.fraud;
import com.mayabank.processing.domain.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class FraudServiceTest {
  private static CardPayment payment(String token,String amount){
    return new CardPayment(UUID.randomUUID(),"ORDER-"+UUID.randomUUID(),new Merchant("M","A"),
      new Cardholder("C",new SyntheticCardToken(token,"ISS")),Scheme.VISA,new BigDecimal(amount),"EUR","corr");
  }
  @Test void normalPaymentApproves(){ assertEquals(FraudDecision.APPROVE,new FraudService().assess(payment("tok_ok","25.00")).decision()); }
  @Test void blockedTokenDeclines(){
    var s=new FraudService(); s.block("tok_blocked");
    assertEquals(FraudDecision.DECLINE,s.assess(payment("tok_blocked","25.00")).decision());
  }
  @Test void highAmountRequiresReview(){ assertEquals(FraudDecision.REVIEW,new FraudService().assess(payment("tok_high","1500.00")).decision()); }
  @Test void velocityEscalatesRisk(){
    var s=new FraudService(new BigDecimal("999999"),2,java.time.Duration.ofMinutes(1));
    s.assess(payment("tok_velocity","10")); s.assess(payment("tok_velocity","10"));
    assertEquals(FraudDecision.REVIEW,s.assess(payment("tok_velocity","10")).decision());
  }
}