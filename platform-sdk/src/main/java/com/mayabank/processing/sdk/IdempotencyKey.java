package com.mayabank.processing.sdk;
public record IdempotencyKey(String value) {
  public IdempotencyKey {
    if (value == null || value.isBlank()) throw new IllegalArgumentException("Idempotency key is required");
    value = value.trim();
  }
}