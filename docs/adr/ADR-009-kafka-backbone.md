# ADR-009 — Kafka event backbone

**Status:** Accepted

## Decision

Use Kafka as the reference event backbone from I05 onward.

## Rules

- at-least-once delivery;
- explicit event IDs;
- versioned contracts;
- Inbox deduplication;
- bounded retries and DLQ;
- controlled replay.

Kafka is not required for I00/I01.
