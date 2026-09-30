package com.mayabank.processing.domain;
import java.util.UUID;
public record DisputeCase(UUID disputeId,UUID paymentId,String reason,DisputeState state,String resolution) {
  public DisputeCase transition(DisputeState next,String resolution){ return new DisputeCase(disputeId,paymentId,reason,next,resolution); }
}