package com.mayabank.processing.domain;
import java.math.BigDecimal; import java.util.UUID;
public record SettlementResult(UUID settlementId,UUID clearingId,UUID paymentId,SettlementState state,BigDecimal amount,String currency,String reason) {}