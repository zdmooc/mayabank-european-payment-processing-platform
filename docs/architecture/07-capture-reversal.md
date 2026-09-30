# Capture and Reversal

**Iteration:** I04  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

## Rules

- only APPROVED authorization can be captured;
- capture amount must be positive and cannot exceed the authorized payment amount in the current model;
- one authorization creates at most one capture effect;
- retries with a different idempotency key still resolve to the canonical capture for that authorization;
- reversal is a separate transition from refund;
- repeated reversal is idempotent.

```text
AUTH_APPROVED
   |
   v
CAPTURE_PENDING
   |
   v
CAPTURED
   |
   v
REVERSED
```

Refund and dispute are intentionally deferred to I10.
