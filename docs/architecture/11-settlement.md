# Settlement

**Iteration:** I08  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

Only a `CLEARED` position is settlement-eligible.

```text
CLEARED
  |
  v
SETTLEMENT_PENDING
  |
  +--> SETTLED --------> ledger posting
  +--> SETTLEMENT_FAILED
  +--> SETTLEMENT_UNKNOWN
```

The canonical key is the clearingId: replay cannot create a second settlement result or a second settlement ledger movement.
