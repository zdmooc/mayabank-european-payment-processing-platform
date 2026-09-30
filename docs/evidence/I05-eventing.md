# I05 Evidence — Eventing / Outbox / Inbox

**Status:** IMPLEMENTED / CI_VALIDATION_PENDING  
**Kafka runtime:** NOT YET PROVEN

## CI assertions

- publish failure leaves Outbox row pending;
- replay can drain pending Outbox work;
- Inbox rejects duplicate eventId;
- event schema is versioned;
- Kafka topic catalog exists.

## Claim boundary

This iteration proves eventing correctness logic in Java. It does not yet prove a running Kafka broker, broker HA, replication, persistence or cross-cluster replication.
