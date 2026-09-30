# Fraud / Risk

**Iteration:** I11  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

Fraud decision is a separate bounded context and precedes authorization.

```text
CardPayment
   |
   v
Fraud/Risk
   |
   +--> APPROVE -> Authorization
   +--> REVIEW  -> step-up / manual/reference path
   +--> DECLINE -> stop
```

Synthetic rules:
- blocked token -> DECLINE;
- high amount -> REVIEW;
- velocity threshold -> REVIEW;
- otherwise -> APPROVE.

The fraud decision does not overwrite issuer authorization state.
