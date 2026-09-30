package com.mayabank.processing.api;
import com.mayabank.processing.acquiring.AcquirerProcessor;
import com.mayabank.processing.authorization.AuthorizationEngine;
import com.mayabank.processing.capture.CaptureService;
import com.mayabank.processing.clearing.ClearingService;
import com.mayabank.processing.dispute.DisputeService;
import com.mayabank.processing.fraud.FraudService;
import com.mayabank.processing.issuer.IssuerProcessor;
import com.mayabank.processing.ledger.LedgerService;
import com.mayabank.processing.reconciliation.ReconciliationService;
import com.mayabank.processing.router.SchemeRouter;
import com.mayabank.processing.settlement.SettlementService;
import com.mayabank.processing.simulator.cb.CbSimulator;
import com.mayabank.processing.simulator.mastercard.MastercardSimulator;
import com.mayabank.processing.simulator.visa.VisaSimulator;
import org.springframework.context.annotation.*;

@Configuration
public class ProcessingConfiguration {
  @Bean AuthorizationEngine authorizationEngine(){ return new AuthorizationEngine(); }
  @Bean IssuerProcessor issuerProcessor(AuthorizationEngine e){ return new IssuerProcessor(e); }
  @Bean SchemeRouter schemeRouter(IssuerProcessor i){ return new SchemeRouter(java.util.List.of(new CbSimulator(i),new VisaSimulator(i),new MastercardSimulator(i))); }
  @Bean AcquirerProcessor acquirerProcessor(SchemeRouter r){ return new AcquirerProcessor(r); }
  @Bean CaptureService captureService(){ return new CaptureService(); }
  @Bean LedgerService ledgerService(){ return new LedgerService(); }
  @Bean ClearingService clearingService(){ return new ClearingService(); }
  @Bean SettlementService settlementService(LedgerService l){ return new SettlementService(l); }
  @Bean ReconciliationService reconciliationService(){ return new ReconciliationService(); }
  @Bean FraudService fraudService(){ return new FraudService(); }
  @Bean DisputeService disputeService(LedgerService l){ return new DisputeService(l); }
  @Bean PaymentFlowFacade paymentFlowFacade(AcquirerProcessor a,CaptureService c,ClearingService cl,SettlementService s,
      ReconciliationService r,FraudService f,DisputeService d,IssuerProcessor i){
    return new PaymentFlowFacade(a,c,cl,s,r,f,d,i);
  }
}