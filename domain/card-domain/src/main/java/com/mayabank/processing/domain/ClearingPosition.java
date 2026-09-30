package com.mayabank.processing.domain;
import java.math.BigDecimal; import java.util.UUID;
public record ClearingPosition(UUID clearingId,UUID paymentId,UUID captureId,ClearingState state,BigDecimal amount,String currency,String reason) {}