package com.mayabank.processing.simulator.cb;
import com.mayabank.processing.domain.*;
import java.util.UUID;

public final class CbSimulator implements SchemeAuthorizationPort {
  private final IssuerAuthorizationPort issuer;
  public CbSimulator(IssuerAuthorizationPort issuer){ this.issuer=issuer; }
  @Override public Scheme scheme(){ return Scheme.CB; }

  @Override public AuthorizationResult authorize(AuthorizationRequest request, SchemeScenario scenario){
    String schemeRef="CB-SIM-"+UUID.randomUUID();
    return switch(scenario){
      case SUCCESS, DECLINE -> decorate(issuer.authorize(request,scenario),schemeRef);
      case TIMEOUT_BEFORE_EFFECT -> new AuthorizationResult(request.authorizationId(),AuthorizationState.AUTH_UNKNOWN,schemeRef,null,"TIMEOUT_BEFORE_EFFECT");
      case UNAVAILABLE -> new AuthorizationResult(request.authorizationId(),AuthorizationState.AUTH_UNKNOWN,schemeRef,null,"SCHEME_UNAVAILABLE");
      case TIMEOUT_AFTER_EFFECT -> {
        var committed=issuer.authorize(request,SchemeScenario.SUCCESS);
        yield new AuthorizationResult(committed.authorizationId(),AuthorizationState.AUTH_UNKNOWN,schemeRef,committed.issuerReference(),"TIMEOUT_AFTER_EFFECT");
      }
    };
  }

  private AuthorizationResult decorate(AuthorizationResult r,String schemeRef){
    return new AuthorizationResult(r.authorizationId(),r.state(),schemeRef,r.issuerReference(),r.reason());
  }
}