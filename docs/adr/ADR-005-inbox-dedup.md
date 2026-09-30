# ADR-005 — Inbox deduplication

**Status:** Accepted

## Decision

Treat event transport as at-least-once.

Consumers persist event identity before side effects. Redelivery becomes a no-op.

## Rule

Business exactly-once effects are achieved through idempotent processing, not transport assumptions.
