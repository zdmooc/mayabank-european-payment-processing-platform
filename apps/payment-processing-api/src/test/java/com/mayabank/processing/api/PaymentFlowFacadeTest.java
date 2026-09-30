package com.mayabank.processing.api;
import com.mayabank.processing.acquiring.AcquirerProcessor; import com.mayabank.processing.authorization.AuthorizationEngine;
import com.mayabank.processing.capture.CaptureService; import com.mayabank.processing.clearing.ClearingService;
import com.mayabank.processing.dispute.DisputeService; import com.mayabank.processing.domain.*; import com.mayabank.processing.fraud.FraudService;
import com.mayabank.processing.issuer.IssuerProcessor; import com.mayabank.processing.ledger.LedgerService;
import com.mayabank.processing.reconciliation.ReconciliationService; import com.mayabank.processing.router.SchemeRouter;
import com.mayabank.processing.settlement.SettlementService; import com.mayabank.processing.simulator.visa.VisaSimulator;
import org.junit.jupiter.api.Test; import java.math.BigDecimal; import java.util.List; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class PaymentFlowFacadeTest {
  @Test void completeHappyPath(){
    var e=new AuthorizationEngine(); var issuer=new IssuerProcessor(e); var ledger=new LedgerService();
    var flow=new PaymentFlowFacade(new AcquirerProcessor(new SchemeRouter(List.of(new VisaSimulator(issuer)))),
      new CaptureService(),new ClearingService(),new SettlementService(ledger),new ReconciliationService(),new FraudService(),new DisputeService(ledger),issuer);
    var p=new CardPayment(UUID.randomUUID(),"ORDER-API",new Merchant("M","A"),new Cardholder("C",new SyntheticCardToken("tok_api","ISS")),Scheme.VISA,new BigDecimal("50.00"),"EUR","corr-api");
    var s=flow.authorize(p,"idem-api",SchemeScenario.SUCCESS); assertEquals(AuthorizationState.AUTH_APPROVED,s.authorization().state());
    s=flow.capture(p.paymentId(),new BigDecimal("50.00"),"cap-api"); assertEquals(CaptureState.CAPTURED,s.capture().state());
    s=flow.clear(p.paymentId(),ClearingService.Scenario.SUCCESS); assertEquals(ClearingState.CLEARED,s.clearing().state());
    s=flow.settle(p.paymentId(),SettlementService.Scenario.SUCCESS); assertEquals(SettlementState.SETTLED,s.settlement().state());
  }
}