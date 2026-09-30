# Synthetic Scheme Behavior

**Iteration:** I03  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

The repository contains three provider-neutral simulators:

- CB-SIM;
- VISA-SIM;
- MC-SIM.

They do not implement proprietary scheme protocols.

## Scenarios

| Scenario | External result | Issuer effect |
|---|---|---|
| SUCCESS | APPROVED | APPROVED |
| DECLINE | DECLINED | DECLINED |
| TIMEOUT_BEFORE_EFFECT | UNKNOWN | none |
| TIMEOUT_AFTER_EFFECT | UNKNOWN | APPROVED |
| UNAVAILABLE | UNKNOWN | none |

The TIMEOUT_AFTER_EFFECT split is intentionally preserved for later inquiry/reconciliation tests.
