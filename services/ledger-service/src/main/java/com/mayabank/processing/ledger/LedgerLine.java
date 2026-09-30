package com.mayabank.processing.ledger;
import java.math.BigDecimal; import java.util.UUID;
public record LedgerLine(UUID lineId,UUID movementId,UUID paymentId,String account,String direction,BigDecimal amount,String currency,String reference) {}