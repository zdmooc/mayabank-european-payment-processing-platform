package com.mayabank.processing.domain;
import java.math.BigDecimal; import java.util.UUID;
public record CaptureResult(UUID captureId,UUID authorizationId,CaptureState state,BigDecimal amount,String reason) {}