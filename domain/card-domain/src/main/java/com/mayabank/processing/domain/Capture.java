package com.mayabank.processing.domain;
import java.math.BigDecimal; import java.util.UUID;
public final class Capture {
  private final UUID captureId; private final UUID authorizationId; private final BigDecimal amount; private CaptureState state;
  public Capture(UUID captureId,UUID authorizationId,BigDecimal amount) {
    if (captureId==null || authorizationId==null) throw new IllegalArgumentException("ids are required");
    if (amount==null || amount.signum()<=0) throw new IllegalArgumentException("capture amount must be > 0");
    this.captureId=captureId; this.authorizationId=authorizationId; this.amount=amount; this.state=CaptureState.CAPTURE_PENDING;
  }
  public void markCaptured(){ state=CaptureState.CAPTURED; }
  public void markUnknown(){ state=CaptureState.CAPTURE_UNKNOWN; }
  public void reverse(){ if(state!=CaptureState.CAPTURED) throw new IllegalStateException("only captured movement can be reversed"); state=CaptureState.REVERSED; }
  public UUID captureId(){ return captureId; } public UUID authorizationId(){ return authorizationId; } public BigDecimal amount(){ return amount; } public CaptureState state(){ return state; }
}