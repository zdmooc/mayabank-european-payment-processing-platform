# Authorization State Model

**Iteration:** I02  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

```text
AUTH_RECEIVED
  -> AUTH_VALIDATED
      -> AUTH_APPROVED
      -> AUTH_DECLINED
      -> AUTH_UNKNOWN
AUTH_APPROVED
  -> AUTH_REVERSED
```

Authorization state remains separate from capture, clearing and settlement.

## I02 vertical slice

```text
Merchant
 -> AcquirerProcessor
 -> SchemeRouter
 -> SchemeAuthorizationPort
 -> IssuerProcessor
 -> AuthorizationEngine
```

I02 uses an in-test direct scheme port. Synthetic CB/Visa/Mastercard implementations are introduced in I03.
