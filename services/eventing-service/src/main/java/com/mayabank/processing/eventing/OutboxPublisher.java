package com.mayabank.processing.eventing;
public final class OutboxPublisher {
  private final OutboxStore store; private final EventPublisher publisher;
  public OutboxPublisher(OutboxStore store,EventPublisher publisher){ this.store=store; this.publisher=publisher; }
  public int drain(){
    int sent=0;
    for(var record:store.pending()){
      publisher.publish(record.event());
      store.markPublished(record.outboxId());
      sent++;
    }
    return sent;
  }
}