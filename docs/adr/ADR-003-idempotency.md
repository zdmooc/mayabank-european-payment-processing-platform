# ADR-003 — Idempotency strategy

**Status:** Accepted

## Decision

Use stable business idempotency keys and database-enforced claims before any financial side effect.

## Invariant

One merchant intent must not create multiple authorization, capture or settlement effects because of retries or concurrency.
