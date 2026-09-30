package com.mayabank.processing.eventing;
import org.junit.jupiter.api.Test;
import java.util.*; import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
class EventingCorrectnessTest {
  @Test void failedPublishLeavesOutboxPendingForReplay(){
    var store=new OutboxStore(); var event=CardEvent.of("AUTH_APPROVED",UUID.randomUUID(),"corr","{}"); store.enqueue(event);
    var publisher=new OutboxPublisher(store,e->{ throw new IllegalStateException("broker down"); });
    assertThrows(IllegalStateException.class,publisher::drain);
    assertEquals(1,store.pendingCount());
  }
  @Test void successfulReplayMarksPublished(){
    var store=new OutboxStore(); store.enqueue(CardEvent.of("CAPTURED",UUID.randomUUID(),"corr","{}"));
    var count=new AtomicInteger(); var publisher=new OutboxPublisher(store,e->count.incrementAndGet());
    assertEquals(1,publisher.drain()); assertEquals(1,count.get()); assertEquals(0,store.pendingCount());
  }
  @Test void inboxDeduplicatesAtLeastOnceDelivery(){
    var inbox=new InboxStore(); var id=UUID.randomUUID();
    assertTrue(inbox.acceptOnce(id)); assertFalse(inbox.acceptOnce(id)); assertEquals(1,inbox.size());
  }
}