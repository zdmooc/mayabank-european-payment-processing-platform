# I03 Evidence — Synthetic Scheme Simulators

**Status:** IMPLEMENTED / CI_VALIDATION_PENDING  
**Date:** 2026-09-30

## Implemented

- CB synthetic simulator;
- Visa synthetic simulator;
- Mastercard synthetic simulator;
- routing for all three schemes;
- explicit synthetic scheme references;
- success / decline / unavailable / timeout-before-effect / timeout-after-effect scenarios.

## Critical invariant

For timeout after effect:

```text
Acquirer view = AUTH_UNKNOWN
Issuer view   = AUTH_APPROVED
```

The caller must not blindly submit a second financial authorization.

## Boundary

No proprietary CB/Visa/Mastercard protocol, certification, key material or network connectivity is implemented.
