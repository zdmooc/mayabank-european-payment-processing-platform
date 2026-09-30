# I09 Evidence — Reconciliation

**Status:** IMPLEMENTED / JAVA CI PASS / UNKNOWN RECONCILIATION RUNTIME_PROVEN

## Assertions

- AUTH_UNKNOWN can resolve to issuer APPROVED without replay;
- missing issuer state remains unresolved and explicitly says NO_RETRY;
- SETTLEMENT_UNKNOWN can resolve to SETTLED;
- resolved settlement posts the ledger once;
- replay after final resolution does not post again.

## Critical scenario

```text
scheme processed authorization
-> response lost
-> acquirer AUTH_UNKNOWN
-> issuer inquiry AUTH_APPROVED
-> reconciliation resolves
-> no second authorization
```

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.
- `TIMEOUT_AFTER_EFFECT -> AUTH_UNKNOWN -> AUTH_APPROVED by issuer inquiry` executed through API E2E run `36686201540` — **SUCCESS**.

## Boundary

Inquiry targets are in-process synthetic components. External network connectivity and production reconciliation feeds are not claimed.
