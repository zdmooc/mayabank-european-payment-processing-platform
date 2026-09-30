package com.mayabank.processing.eventing;
import java.util.UUID;
public record OutboxRecord(UUID outboxId,CardEvent event,Status status) {
  public enum Status { PENDING, PUBLISHED }
  public static OutboxRecord pending(CardEvent event){ return new OutboxRecord(UUID.randomUUID(),event,Status.PENDING); }
  public OutboxRecord published(){ return new OutboxRecord(outboxId,event,Status.PUBLISHED); }
}