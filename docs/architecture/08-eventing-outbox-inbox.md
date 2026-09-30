# Eventing / Outbox / Inbox

**Iteration:** I05  
**Status:** IMPLEMENTED / CI-TESTED logic; Kafka runtime pending

## Semantics

- transport is at-least-once;
- producer-side Outbox records remain PENDING until publish succeeds;
- consumer-side Inbox accepts each eventId once;
- business exactly-once effects come from idempotency and deduplication, not Kafka transport guarantees;
- event contracts are versioned.

## Kafka topics

See `platform/kafka/topics.yaml`.

The current replication factor is lab-only and must not be read as a production topology decision.
