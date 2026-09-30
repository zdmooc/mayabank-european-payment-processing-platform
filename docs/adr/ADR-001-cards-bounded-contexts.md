# ADR-001 — Cards bounded contexts

**Status:** Accepted

## Decision

Separate at least the following bounded contexts:

- Acquiring
- Issuing
- Authorization
- Scheme Routing
- Capture
- Clearing
- Settlement
- Ledger
- Reconciliation
- Dispute

## Rationale

These domains have different state, ownership, failure behavior and recovery rules. They must not collapse into one payment-service model.
