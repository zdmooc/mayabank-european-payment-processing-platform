package com.mayabank.processing.clearing;
import com.mayabank.processing.domain.*;
import java.util.*; import java.util.concurrent.ConcurrentHashMap;

public final class ClearingService {
  public enum Scenario { SUCCESS, EXCEPTION }
  private final Map<UUID,ClearingPosition> byCapture=new ConcurrentHashMap<>();

  public ClearingPosition clear(CardPayment payment,CaptureResult capture,Scenario scenario){
    if(capture.state()!=CaptureState.CAPTURED) throw new IllegalStateException("CAPTURE_NOT_CLEARED_ELIGIBLE");
    return byCapture.computeIfAbsent(capture.captureId(),id->{
      var state=scenario==Scenario.SUCCESS?ClearingState.CLEARED:ClearingState.CLEARING_EXCEPTION;
      var reason=scenario==Scenario.SUCCESS?"CLEARED":"SYNTHETIC_CLEARING_EXCEPTION";
      return new ClearingPosition(UUID.randomUUID(),payment.paymentId(),capture.captureId(),state,capture.amount(),payment.currency(),reason);
    });
  }

  public ClearingPosition inquireByCapture(UUID captureId){ return byCapture.get(captureId); }
  public long count(){ return byCapture.size(); }
}