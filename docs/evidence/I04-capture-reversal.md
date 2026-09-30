# I04 Evidence — Capture & Reversal

**Status:** IMPLEMENTED / JAVA CI PASS / CAPTURE API E2E  
**Date:** 2026-09-30

## Assertions

- declined authorization cannot be captured;
- capture amount is bounded by payment amount;
- same authorization cannot create a second capture effect;
- repeated reversal is idempotent;
- reversal is not modelled as refund.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.
- Capture happy path executed through API E2E run `36686201540` — **SUCCESS**.

## Claim boundary

I04 is in-memory correctness evidence. Persistent financial posting and PostgreSQL constraints are introduced with the ledger/persistence iterations.
