package com.mayabank.processing.ledger;
import java.math.BigDecimal; import java.util.*; import java.util.concurrent.ConcurrentHashMap;

public final class LedgerService {
  private final Map<String,LedgerMovement> byReference=new ConcurrentHashMap<>();

  public LedgerMovement postCapture(UUID paymentId,UUID captureId,BigDecimal amount,String currency){
    return post("CAPTURE","CAPTURE:"+captureId,paymentId,amount,currency,"CARDHOLDER_RECEIVABLE","MERCHANT_PAYABLE");
  }

  public LedgerMovement postSettlement(UUID paymentId,UUID settlementId,BigDecimal amount,String currency){
    return post("SETTLEMENT","SETTLEMENT:"+settlementId,paymentId,amount,currency,"MERCHANT_PAYABLE","CASH");
  }

  private LedgerMovement post(String type,String reference,UUID paymentId,BigDecimal amount,String currency,String debitAccount,String creditAccount){
    if(amount==null || amount.signum()<=0) throw new IllegalArgumentException("amount must be > 0");
    return byReference.computeIfAbsent(reference,ref->{
      UUID movementId=UUID.randomUUID();
      var debit=new LedgerLine(UUID.randomUUID(),movementId,paymentId,debitAccount,"DEBIT",amount,currency,ref);
      var credit=new LedgerLine(UUID.randomUUID(),movementId,paymentId,creditAccount,"CREDIT",amount,currency,ref);
      var movement=new LedgerMovement(movementId,type,ref,List.of(debit,credit));
      if(movement.net().signum()!=0) throw new IllegalStateException("UNBALANCED_LEDGER");
      return movement;
    });
  }

  public List<LedgerMovement> movements(){ return List.copyOf(byReference.values()); }
  public long count(){ return byReference.size(); }
}