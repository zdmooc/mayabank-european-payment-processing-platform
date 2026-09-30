package com.mayabank.processing.dispute;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.ledger.LedgerService;
import java.math.BigDecimal; import java.util.*; import java.util.concurrent.ConcurrentHashMap;

public final class DisputeService {
  private final LedgerService ledger;
  private final Map<String,RefundResult> refundsByKey=new ConcurrentHashMap<>();
  private final Map<UUID,RefundResult> refundsByPayment=new ConcurrentHashMap<>();
  private final Map<UUID,DisputeCase> disputes=new ConcurrentHashMap<>();

  public DisputeService(LedgerService ledger){ this.ledger=ledger; }

  public RefundResult refund(SettlementResult settlement,BigDecimal amount,String idempotencyKey){
    if(settlement.state()!=SettlementState.SETTLED) throw new IllegalStateException("PAYMENT_NOT_SETTLED");
    if(amount==null || amount.signum()<=0 || amount.compareTo(settlement.amount())>0) throw new IllegalArgumentException("INVALID_REFUND_AMOUNT");
    var existingByKey=refundsByKey.get(idempotencyKey);
    if(existingByKey!=null){
      if(!existingByKey.paymentId().equals(settlement.paymentId())) throw new IllegalStateException("IDEMPOTENCY_CONFLICT");
      return existingByKey;
    }
    var existingForPayment=refundsByPayment.get(settlement.paymentId());
    if(existingForPayment!=null){
      refundsByKey.putIfAbsent(idempotencyKey,existingForPayment);
      return existingForPayment;
    }
    var result=new RefundResult(UUID.randomUUID(),settlement.paymentId(),RefundState.REFUNDED,amount,settlement.currency(),"REFUNDED");
    refundsByPayment.put(settlement.paymentId(),result);
    refundsByKey.put(idempotencyKey,result);
    ledger.postRefund(result.paymentId(),result.refundId(),result.amount(),result.currency());
    return result;
  }

  public DisputeCase openDispute(UUID paymentId,String reason){
    var c=new DisputeCase(UUID.randomUUID(),paymentId,reason,DisputeState.OPEN,null);
    disputes.put(c.disputeId(),c);
    return c;
  }

  public DisputeCase review(UUID disputeId){
    return update(disputeId,DisputeState.UNDER_REVIEW,null);
  }

  public DisputeCase resolve(UUID disputeId,boolean cardholderWon){
    var state=cardholderWon?DisputeState.CARDHOLDER_WON:DisputeState.MERCHANT_WON;
    return update(disputeId,state,cardholderWon?"CARDHOLDER_WON":"MERCHANT_WON");
  }

  public DisputeCase close(UUID disputeId){ return update(disputeId,DisputeState.CLOSED,"CLOSED"); }

  private DisputeCase update(UUID id,DisputeState state,String resolution){
    var current=disputes.get(id); if(current==null) throw new IllegalArgumentException("DISPUTE_NOT_FOUND");
    var next=current.transition(state,resolution); disputes.put(id,next); return next;
  }
}