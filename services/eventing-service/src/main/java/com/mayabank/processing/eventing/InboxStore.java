package com.mayabank.processing.eventing;
import java.util.Set; import java.util.UUID; import java.util.concurrent.ConcurrentHashMap;
public final class InboxStore {
  private final Set<UUID> processed=ConcurrentHashMap.newKeySet();
  public boolean acceptOnce(UUID eventId){ return processed.add(eventId); }
  public int size(){ return processed.size(); }
}