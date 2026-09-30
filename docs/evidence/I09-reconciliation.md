# I09 Evidence — Reconciliation

**Status:** IMPLEMENTED / CI_VALIDATION_PENDING

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

## Boundary

Inquiry targets are in-process synthetic components. External network connectivity and production reconciliation feeds are not claimed.
