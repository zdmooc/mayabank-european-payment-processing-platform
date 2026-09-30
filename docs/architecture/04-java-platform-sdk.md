# Java Payment Platform SDK

**Status:** REFERENCE_ARCHITECTURE  
**Source signal:** Estreem Solution Architect posting explicitly highlights an internal Java SDK and reusable platform foundations.

## Goal

Provide reusable Java foundations shared across payment services without creating a giant common-domain library.

Proposed module:

`platform-sdk/`

## Initial capabilities

```text
platform-sdk
├── correlation
├── error-model
├── event-envelope
├── idempotency
├── observability
├── security-context
├── api-conventions
└── compatibility
```

## Rules

### Allowed in the SDK

- technical conventions;
- cross-cutting abstractions;
- common event metadata;
- trace/correlation propagation;
- standardized error response;
- idempotency primitives;
- logging/metrics conventions;
- client/interceptor helpers;
- backward-compatible API utilities.

### Forbidden in the SDK

- acquiring business rules;
- issuing business rules;
- authorization policy;
- clearing rules;
- settlement logic;
- scheme-specific business behavior.

Those remain owned by bounded contexts.

## Compatibility policy

The SDK must preserve:

- semantic versioning;
- backward compatibility where practical;
- deprecation periods;
- migration notes;
- reference implementations;
- automated compatibility tests.

## Why this matters

The SDK demonstrates a platform engineering concern directly aligned with a multi-squad processor:

```text
Acquiring Service ──────┐
Issuer Service ─────────┤
Authorization Service ──┤
Clearing Service ───────┼──> Payment Platform SDK
Settlement Service ─────┤
Reconciliation Service ─┘
```

The objective is engineering coherence across services, not code centralization.
