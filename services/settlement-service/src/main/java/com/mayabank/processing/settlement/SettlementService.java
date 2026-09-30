package com.mayabank.processing.settlement;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.ledger.LedgerService;
import java.util.*; import java.util.concurrent.ConcurrentHashMap;

public final class SettlementService {
  public enum Scenario { SUCCESS, FAILURE, UNKNOWN }
  private final LedgerService ledger;
  private final Map<UUID,SettlementResult> byClearing=new ConcurrentHashMap<>();

  public SettlementService(LedgerService ledger){ this.ledger=ledger; }

  public SettlementResult settle(ClearingPosition clearing,Scenario scenario){
    if(clearing.state()!=ClearingState.CLEARED) throw new IllegalStateException("CLEARING_NOT_SETTLEMENT_ELIGIBLE");
    return byClearing.computeIfAbsent(clearing.clearingId(),id->{
      var settlementId=UUID.randomUUID();
      var state=switch(scenario){
        case SUCCESS -> SettlementState.SETTLED;
        case FAILURE -> SettlementState.SETTLEMENT_FAILED;
        case UNKNOWN -> SettlementState.SETTLEMENT_UNKNOWN;
      };
      var result=new SettlementResult(settlementId,clearing.clearingId(),clearing.paymentId(),state,clearing.amount(),clearing.currency(),state.name());
      if(state==SettlementState.SETTLED){
        ledger.postSettlement(clearing.paymentId(),settlementId,clearing.amount(),clearing.currency());
      }
      return result;
    });
  }

  public SettlementResult reconcileUnknown(UUID clearingId, SettlementState externalState){
    var current=byClearing.get(clearingId);
    if(current==null) throw new IllegalArgumentException("SETTLEMENT_NOT_FOUND");
    if(current.state()!=SettlementState.SETTLEMENT_UNKNOWN) return current;
    if(externalState!=SettlementState.SETTLED && externalState!=SettlementState.SETTLEMENT_FAILED) return current;
    var reconciled=new SettlementResult(current.settlementId(),current.clearingId(),current.paymentId(),externalState,current.amount(),current.currency(),"RECONCILED_"+externalState);
    byClearing.put(clearingId,reconciled);
    if(externalState==SettlementState.SETTLED){
      ledger.postSettlement(current.paymentId(),current.settlementId(),current.amount(),current.currency());
    }
    return reconciled;
  }

  public SettlementResult inquireByClearing(UUID clearingId){ return byClearing.get(clearingId); }
}