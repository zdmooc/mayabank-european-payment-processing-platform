package com.mayabank.processing.reconciliation;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.issuer.IssuerProcessor;
import com.mayabank.processing.settlement.SettlementService;
import java.util.UUID;

public final class ReconciliationService {
  public ReconciliationResult reconcileAuthorization(AuthorizationResult local, IssuerProcessor issuer){
    if(local.state()!=AuthorizationState.AUTH_UNKNOWN){
      return new ReconciliationResult(UUID.randomUUID(),"AUTHORIZATION",local.state().name(),local.state().name(),"ALREADY_FINAL",true);
    }
    var authoritative=issuer.inquire(local.authorizationId().toString());
    if(authoritative==null){
      return new ReconciliationResult(UUID.randomUUID(),"AUTHORIZATION",local.state().name(),local.state().name(),"ISSUER_NOT_FOUND_NO_RETRY",false);
    }
    return new ReconciliationResult(UUID.randomUUID(),"AUTHORIZATION",local.state().name(),authoritative.state().name(),"RESOLVED_FROM_ISSUER_INQUIRY",true);
  }

  public ReconciliationResult reconcileSettlement(SettlementResult local, SettlementState externalState, SettlementService settlement){
    if(local.state()!=SettlementState.SETTLEMENT_UNKNOWN){
      return new ReconciliationResult(UUID.randomUUID(),"SETTLEMENT",local.state().name(),local.state().name(),"ALREADY_FINAL",true);
    }
    var resolved=settlement.reconcileUnknown(local.clearingId(),externalState);
    boolean finalState=resolved.state()==SettlementState.SETTLED || resolved.state()==SettlementState.SETTLEMENT_FAILED;
    return new ReconciliationResult(UUID.randomUUID(),"SETTLEMENT",local.state().name(),resolved.state().name(),finalState?"RESOLVED_FROM_EXTERNAL_STATUS":"EXTERNAL_STATUS_NOT_FINAL",finalState);
  }
}