package com.mayabank.processing.authorization;
import com.mayabank.processing.domain.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AuthorizationEngine {
  private record Stored(String paymentId, AuthorizationResult result) {}
  private final Map<String,Stored> byIdempotencyKey=new ConcurrentHashMap<>();
  private final Map<String,AuthorizationResult> byAuthorizationId=new ConcurrentHashMap<>();

  public AuthorizationResult decide(AuthorizationRequest request, AuthorizationState decision, String issuerReference, String reason) {
    if(decision!=AuthorizationState.AUTH_APPROVED && decision!=AuthorizationState.AUTH_DECLINED && decision!=AuthorizationState.AUTH_UNKNOWN)
      throw new IllegalArgumentException("unsupported authorization decision");
    String key=request.idempotencyKey();
    String paymentId=request.payment().paymentId().toString();
    Stored existing=byIdempotencyKey.get(key);
    if(existing!=null){
      if(!existing.paymentId().equals(paymentId)) throw new IllegalStateException("IDEMPOTENCY_CONFLICT");
      return existing.result();
    }
    var result=new AuthorizationResult(request.authorizationId(),decision,null,issuerReference,reason);
    var stored=new Stored(paymentId,result);
    var winner=byIdempotencyKey.putIfAbsent(key,stored);
    var effective=winner==null?stored:winner;
    if(!effective.paymentId().equals(paymentId)) throw new IllegalStateException("IDEMPOTENCY_CONFLICT");
    byAuthorizationId.putIfAbsent(effective.result().authorizationId().toString(),effective.result());
    return effective.result();
  }

  public AuthorizationResult inquire(String authorizationId) {
    return byAuthorizationId.get(authorizationId);
  }
}