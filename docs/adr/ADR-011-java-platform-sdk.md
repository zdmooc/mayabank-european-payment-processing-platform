# ADR-011 — Internal Java Payment Platform SDK

**Status:** Accepted

## Decision

Create a versioned internal Java SDK for reusable technical foundations.

## Scope

- correlation;
- event envelope;
- standardized errors;
- idempotency primitives;
- observability conventions;
- security context;
- API conventions;
- compatibility helpers.

## Non-scope

No bounded-context business logic belongs in the SDK.

## Rationale

A multi-service, multi-squad payment platform needs coherent foundations and reusable reference implementations while preserving domain ownership.
