package com.mayabank.processing.sdk;
import java.time.Instant;
import java.util.UUID;
public record EventEnvelope<T>(UUID eventId,String eventType,int eventVersion,String correlationId,Instant occurredAt,T payload) {
  public static <T> EventEnvelope<T> of(String type,String correlationId,T payload) {
    return new EventEnvelope<>(UUID.randomUUID(),type,1,correlationId,Instant.now(),payload);
  }
}