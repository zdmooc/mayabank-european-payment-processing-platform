package com.mayabank.processing.domain;
import java.math.BigDecimal; import java.util.UUID;
public record RefundResult(UUID refundId,UUID paymentId,RefundState state,BigDecimal amount,String currency,String reason) {}