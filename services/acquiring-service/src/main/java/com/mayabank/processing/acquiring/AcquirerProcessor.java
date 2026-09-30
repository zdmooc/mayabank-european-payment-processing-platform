package com.mayabank.processing.acquiring;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.router.SchemeRouter;
import com.mayabank.processing.sdk.IdempotencyKey;
import java.util.UUID;

public final class AcquirerProcessor {
  private final SchemeRouter router;
  public AcquirerProcessor(SchemeRouter router){ this.router=router; }

  public AuthorizationResult authorize(CardPayment payment, String idempotencyKey, SchemeScenario scenario){
    var key=new IdempotencyKey(idempotencyKey);
    var request=new AuthorizationRequest(UUID.randomUUID(),payment,key.value());
    return router.authorize(request,scenario);
  }
}