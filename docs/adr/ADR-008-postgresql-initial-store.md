# ADR-008 — PostgreSQL as initial lab store

**Status:** Accepted

## Decision

Use PostgreSQL for the initial reference implementation.

## Rationale

It supports transactional state, unique constraints for idempotency, Outbox/Inbox tables and auditable relational models.

## Boundary

This is an implementation choice for the lab, not a claim about Estreem or any external processor.
