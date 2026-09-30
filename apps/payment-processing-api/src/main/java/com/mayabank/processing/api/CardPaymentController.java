package com.mayabank.processing.api;
import com.mayabank.processing.clearing.ClearingService;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.settlement.SettlementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal; import java.util.UUID;

@RestController
@RequestMapping("/api/v1/card-payments")
public class CardPaymentController {
  private final PaymentFlowFacade flow;
  public CardPaymentController(PaymentFlowFacade flow){ this.flow=flow; }

  public record AuthorizeRequest(String merchantOrderId,String merchantId,String acquirerId,String token,String issuerId,
      Scheme scheme,BigDecimal amount,String currency,String idempotencyKey,SchemeScenario scenario,String correlationId){}
  public record CaptureRequest(BigDecimal amount,String idempotencyKey){}
  public record ClearRequest(ClearingService.Scenario scenario){}
  public record SettleRequest(SettlementService.Scenario scenario){}

  @PostMapping("/authorize")
  @ResponseStatus(HttpStatus.CREATED)
  public PaymentFlowFacade.FlowState authorize(@RequestBody AuthorizeRequest r){
    var payment=new CardPayment(UUID.randomUUID(),r.merchantOrderId(),new Merchant(r.merchantId(),r.acquirerId()),
      new Cardholder("synthetic-cardholder",new SyntheticCardToken(r.token(),r.issuerId())),r.scheme(),r.amount(),r.currency(),r.correlationId());
    return flow.authorize(payment,r.idempotencyKey(),r.scenario()==null?SchemeScenario.SUCCESS:r.scenario());
  }

  @PostMapping("/{paymentId}/capture")
  public PaymentFlowFacade.FlowState capture(@PathVariable UUID paymentId,@RequestBody CaptureRequest r){ return flow.capture(paymentId,r.amount(),r.idempotencyKey()); }

  @PostMapping("/{paymentId}/clear")
  public PaymentFlowFacade.FlowState clear(@PathVariable UUID paymentId,@RequestBody ClearRequest r){ return flow.clear(paymentId,r.scenario()==null?ClearingService.Scenario.SUCCESS:r.scenario()); }

  @PostMapping("/{paymentId}/settle")
  public PaymentFlowFacade.FlowState settle(@PathVariable UUID paymentId,@RequestBody SettleRequest r){ return flow.settle(paymentId,r.scenario()==null?SettlementService.Scenario.SUCCESS:r.scenario()); }

  @PostMapping("/{paymentId}/reconcile-authorization")
  public ReconciliationResult reconcileAuthorization(@PathVariable UUID paymentId){ return flow.reconcileAuthorization(paymentId); }

  @GetMapping("/{paymentId}")
  public PaymentFlowFacade.FlowState get(@PathVariable UUID paymentId){ return flow.get(paymentId); }
}