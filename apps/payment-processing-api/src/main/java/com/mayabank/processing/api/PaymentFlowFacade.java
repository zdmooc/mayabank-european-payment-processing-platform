package com.mayabank.processing.api;
import com.mayabank.processing.acquiring.AcquirerProcessor;
import com.mayabank.processing.capture.CaptureService;
import com.mayabank.processing.clearing.ClearingService;
import com.mayabank.processing.dispute.DisputeService;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.fraud.FraudService;
import com.mayabank.processing.issuer.IssuerProcessor;
import com.mayabank.processing.reconciliation.ReconciliationService;
import com.mayabank.processing.settlement.SettlementService;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class PaymentFlowFacade {
  public record FlowState(CardPayment payment,FraudAssessment fraud,AuthorizationResult authorization,CaptureResult capture,
      ClearingPosition clearing,SettlementResult settlement) {}
  private final AcquirerProcessor acquirer; private final CaptureService capture; private final ClearingService clearing;
  private final SettlementService settlement; private final ReconciliationService reconciliation; private final FraudService fraud;
  private final DisputeService dispute; private final IssuerProcessor issuer;
  private final Map<UUID,FlowState> states=new ConcurrentHashMap<>();

  public PaymentFlowFacade(AcquirerProcessor a,CaptureService c,ClearingService cl,SettlementService s,
      ReconciliationService r,FraudService f,DisputeService d,IssuerProcessor i){
    acquirer=a; capture=c; clearing=cl; settlement=s; reconciliation=r; fraud=f; dispute=d; issuer=i;
  }

  public FlowState authorize(CardPayment payment,String idempotencyKey,SchemeScenario scenario){
    var risk=fraud.assess(payment);
    if(risk.decision()==FraudDecision.DECLINE){
      var rejected=new AuthorizationResult(UUID.randomUUID(),AuthorizationState.AUTH_DECLINED,null,null,"FRAUD_DECLINE");
      var state=new FlowState(payment,risk,rejected,null,null,null); states.put(payment.paymentId(),state); return state;
    }
    var auth=acquirer.authorize(payment,idempotencyKey,scenario);
    var state=new FlowState(payment,risk,auth,null,null,null); states.put(payment.paymentId(),state); return state;
  }

  public FlowState capture(UUID paymentId,BigDecimal amount,String key){
    var s=require(paymentId); var result=capture.capture(s.payment(),s.authorization(),amount,key);
    var next=new FlowState(s.payment(),s.fraud(),s.authorization(),result,s.clearing(),s.settlement()); states.put(paymentId,next); return next;
  }

  public FlowState clear(UUID paymentId,ClearingService.Scenario scenario){
    var s=require(paymentId); var result=clearing.clear(s.payment(),s.capture(),scenario);
    var next=new FlowState(s.payment(),s.fraud(),s.authorization(),s.capture(),result,s.settlement()); states.put(paymentId,next); return next;
  }

  public FlowState settle(UUID paymentId,SettlementService.Scenario scenario){
    var s=require(paymentId); var result=settlement.settle(s.clearing(),scenario);
    var next=new FlowState(s.payment(),s.fraud(),s.authorization(),s.capture(),s.clearing(),result); states.put(paymentId,next); return next;
  }

  public ReconciliationResult reconcileAuthorization(UUID paymentId){ return reconciliation.reconcileAuthorization(require(paymentId).authorization(),issuer); }
  public FlowState get(UUID paymentId){ return require(paymentId); }
  private FlowState require(UUID id){ var s=states.get(id); if(s==null) throw new IllegalArgumentException("PAYMENT_NOT_FOUND"); return s; }
}