package com.mayabank.processing.capture;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.sdk.IdempotencyKey;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class CaptureService {
  private record Claim(UUID paymentId, CaptureResult result) {}
  private final Map<String,Claim> byIdempotency=new ConcurrentHashMap<>();
  private final Map<UUID,CaptureResult> byAuthorization=new ConcurrentHashMap<>();
  private final Map<UUID,CaptureResult> byCapture=new ConcurrentHashMap<>();

  public CaptureResult capture(CardPayment payment, AuthorizationResult authorization, BigDecimal amount, String idempotencyKey){
    if(authorization.state()!=AuthorizationState.AUTH_APPROVED) throw new IllegalStateException("AUTHORIZATION_NOT_APPROVED");
    if(amount==null || amount.signum()<=0 || amount.compareTo(payment.amount())>0) throw new IllegalArgumentException("INVALID_CAPTURE_AMOUNT");
    var key=new IdempotencyKey(idempotencyKey).value();

    var claim=byIdempotency.get(key);
    if(claim!=null){
      if(!claim.paymentId().equals(payment.paymentId())) throw new IllegalStateException("IDEMPOTENCY_CONFLICT");
      return claim.result();
    }

    var existingForAuth=byAuthorization.get(authorization.authorizationId());
    if(existingForAuth!=null){
      byIdempotency.putIfAbsent(key,new Claim(payment.paymentId(),existingForAuth));
      return existingForAuth;
    }

    var created=new CaptureResult(UUID.randomUUID(),authorization.authorizationId(),CaptureState.CAPTURED,amount,"CAPTURED");
    var winner=byAuthorization.putIfAbsent(authorization.authorizationId(),created);
    var effective=winner==null?created:winner;
    byCapture.putIfAbsent(effective.captureId(),effective);
    byIdempotency.putIfAbsent(key,new Claim(payment.paymentId(),effective));
    return effective;
  }

  public CaptureResult reverse(UUID captureId){
    var current=byCapture.get(captureId);
    if(current==null) throw new IllegalArgumentException("CAPTURE_NOT_FOUND");
    if(current.state()==CaptureState.REVERSED) return current;
    if(current.state()!=CaptureState.CAPTURED) throw new IllegalStateException("CAPTURE_NOT_REVERSIBLE");
    var reversed=new CaptureResult(current.captureId(),current.authorizationId(),CaptureState.REVERSED,current.amount(),"REVERSED");
    byCapture.put(captureId,reversed);
    byAuthorization.put(current.authorizationId(),reversed);
    byIdempotency.replaceAll((k,v)->v.result().captureId().equals(captureId)?new Claim(v.paymentId(),reversed):v);
    return reversed;
  }

  public CaptureResult inquire(UUID captureId){ return byCapture.get(captureId); }
}