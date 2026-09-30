package com.mayabank.processing.eventing;
import java.time.Instant; import java.util.UUID;
public record CardEvent(UUID eventId,String eventType,int eventVersion,UUID paymentId,String correlationId,Instant occurredAt,String payload) {
  public static CardEvent of(String type,UUID paymentId,String correlationId,String payload){
    return new CardEvent(UUID.randomUUID(),type,1,paymentId,correlationId,Instant.now(),payload);
  }
}