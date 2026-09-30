package com.mayabank.processing.eventing;
import java.util.*; import java.util.concurrent.ConcurrentHashMap;
public final class OutboxStore {
  private final Map<UUID,OutboxRecord> records=new ConcurrentHashMap<>();
  public OutboxRecord enqueue(CardEvent event){ var r=OutboxRecord.pending(event); records.put(r.outboxId(),r); return r; }
  public List<OutboxRecord> pending(){ return records.values().stream().filter(r->r.status()==OutboxRecord.Status.PENDING).sorted(Comparator.comparing(r->r.event().occurredAt())).toList(); }
  public void markPublished(UUID id){ records.computeIfPresent(id,(k,v)->v.published()); }
  public long pendingCount(){ return pending().size(); }
}