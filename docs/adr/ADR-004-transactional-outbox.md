# ADR-004 — Transactional Outbox

**Status:** Accepted

## Decision

Persist business state mutation and Outbox event in the same database transaction.

Kafka publication occurs asynchronously after commit.

## Rule

Kafka unavailability must not force the caller to replay a financial action blindly.
