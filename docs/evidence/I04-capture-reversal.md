# I04 Evidence — Capture & Reversal

**Status:** IMPLEMENTED / CI_VALIDATION_PENDING  
**Date:** 2026-09-30

## Assertions

- declined authorization cannot be captured;
- capture amount is bounded by payment amount;
- same authorization cannot create a second capture effect;
- repeated reversal is idempotent;
- reversal is not modelled as refund.

## Claim boundary

I04 is in-memory correctness evidence. Persistent financial posting and PostgreSQL constraints are introduced with the ledger/persistence iterations.
