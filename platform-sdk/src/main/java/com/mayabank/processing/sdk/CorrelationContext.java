package com.mayabank.processing.sdk;
import java.util.UUID;
public record CorrelationContext(String correlationId) {
  public CorrelationContext { if (correlationId == null || correlationId.isBlank()) correlationId = UUID.randomUUID().toString(); }
  public static CorrelationContext create() { return new CorrelationContext(null); }
}