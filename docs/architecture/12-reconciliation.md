# Reconciliation

**Iteration:** I09  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

Reconciliation resolves ambiguity through authoritative inquiry/status. It does not resubmit the original financial action.

## Authorization

```text
local AUTH_UNKNOWN
   |
   v
issuer inquiry
   |
   +--> AUTH_APPROVED
   +--> AUTH_DECLINED
   +--> NOT_FOUND -> remain unresolved / no blind retry
```

## Settlement

```text
local SETTLEMENT_UNKNOWN
   |
   v
external status
   |
   +--> SETTLED -> one ledger posting
   +--> SETTLEMENT_FAILED
   +--> non-final -> remain UNKNOWN
```

The reconciliation case records before state, after state, action and resolution flag.
