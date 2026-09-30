package com.mayabank.processing.issuer;
import com.mayabank.processing.authorization.AuthorizationEngine;
import com.mayabank.processing.domain.*;
import java.util.UUID;

public final class IssuerProcessor implements IssuerAuthorizationPort {
  private final AuthorizationEngine engine;
  public IssuerProcessor(AuthorizationEngine engine){ this.engine=engine; }

  @Override public AuthorizationResult authorize(AuthorizationRequest request, SchemeScenario scenario){
    return switch(scenario){
      case SUCCESS -> engine.decide(request,AuthorizationState.AUTH_APPROVED,"ISS-"+UUID.randomUUID(),"APPROVED");
      case DECLINE -> engine.decide(request,AuthorizationState.AUTH_DECLINED,"ISS-"+UUID.randomUUID(),"ISSUER_DECLINE");
      default -> throw new IllegalArgumentException("scenario handled at scheme boundary: "+scenario);
    };
  }

  public AuthorizationResult inquire(String authorizationId){ return engine.inquire(authorizationId); }
}