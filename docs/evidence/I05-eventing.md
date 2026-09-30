# I05 Evidence — Eventing / Outbox / Inbox

**Status:** IMPLEMENTED / JAVA CI PASS / KAFKA RUNTIME_PROVEN  
**Kafka runtime:** CI SINGLE-NODE PROVEN — run `36685727430`

## CI assertions

- publish failure leaves Outbox row pending;
- replay can drain pending Outbox work;
- Inbox rejects duplicate eventId;
- event schema is versioned;
- Kafka topic catalog exists.

## Claim boundary

Eventing correctness logic is Java-CI proven and a real single-node Kafka broker was exercised for topic creation and produce/consume. Broker HA, cross-cluster replication and production retention remain unproven.
