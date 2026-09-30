package com.mayabank.processing.domain;
import java.math.BigDecimal; import java.util.UUID;
public record CardPayment(UUID paymentId,String merchantOrderId,Merchant merchant,Cardholder cardholder,Scheme scheme,BigDecimal amount,String currency,String correlationId) {
  public CardPayment {
    if (paymentId == null) throw new IllegalArgumentException("paymentId is required");
    if (merchantOrderId == null || merchantOrderId.isBlank()) throw new IllegalArgumentException("merchantOrderId is required");
    if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
    if (currency == null || currency.length()!=3) throw new IllegalArgumentException("currency must be ISO-4217 style");
  }
}