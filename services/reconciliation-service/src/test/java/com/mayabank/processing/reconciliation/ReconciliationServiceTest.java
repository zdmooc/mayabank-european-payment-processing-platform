package com.mayabank.processing.reconciliation;
import com.mayabank.processing.authorization.AuthorizationEngine;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.issuer.IssuerProcessor;
import com.mayabank.processing.ledger.LedgerService;
import com.mayabank.processing.settlement.SettlementService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ReconciliationServiceTest {
  @Test void unknownAuthorizationResolvesFromIssuerWithoutReplay(){
    var engine=new AuthorizationEngine(); var issuer=new IssuerProcessor(engine);
    var payment=new CardPayment(UUID.randomUUID(),"ORDER-R",new Merchant("M","A"),new Cardholder("C",new SyntheticCardToken("tok_recon","ISS")),Scheme.VISA,new BigDecimal("10.00"),"EUR","corr");
    var request=new AuthorizationRequest(UUID.randomUUID(),payment,"idem-r");
    var approved=issuer.authorize(request,SchemeScenario.SUCCESS);
    var localUnknown=new AuthorizationResult(approved.authorizationId(),AuthorizationState.AUTH_UNKNOWN,"VISA-SIM-X",approved.issuerReference(),"TIMEOUT_AFTER_EFFECT");
    var result=new ReconciliationService().reconcileAuthorization(localUnknown,issuer);
    assertTrue(result.resolved()); assertEquals("AUTH_APPROVED",result.afterState());
  }

  @Test void unknownSettlementResolvesAndPostsLedgerOnce(){
    var ledger=new LedgerService(); var settlement=new SettlementService(ledger);
    var clearing=new ClearingPosition(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),ClearingState.CLEARED,new BigDecimal("33.00"),"EUR","CLEARED");
    var local=settlement.settle(clearing,SettlementService.Scenario.UNKNOWN);
    var service=new ReconciliationService();
    var first=service.reconcileSettlement(local,SettlementState.SETTLED,settlement);
    var second=service.reconcileSettlement(settlement.inquireByClearing(clearing.clearingId()),SettlementState.SETTLED,settlement);
    assertTrue(first.resolved()); assertEquals("SETTLED",first.afterState()); assertEquals(1,ledger.count()); assertEquals("ALREADY_FINAL",second.action());
  }

  @Test void missingIssuerStateNeverTriggersBlindRetry(){
    var issuer=new IssuerProcessor(new AuthorizationEngine());
    var local=new AuthorizationResult(UUID.randomUUID(),AuthorizationState.AUTH_UNKNOWN,"CB-SIM-X",null,"TIMEOUT");
    var r=new ReconciliationService().reconcileAuthorization(local,issuer);
    assertFalse(r.resolved()); assertEquals("ISSUER_NOT_FOUND_NO_RETRY",r.action());
  }
}