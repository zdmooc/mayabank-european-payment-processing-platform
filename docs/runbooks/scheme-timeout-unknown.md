# Runbook — Scheme Timeout / UNKNOWN Authorization

1. Never assume timeout means decline.
2. Do not blindly submit a second authorization.
3. Preserve paymentId, authorizationId, schemeReference and correlationId.
4. Query authoritative issuer/scheme status where available.
5. If APPROVED, reconcile local state.
6. If DECLINED, reconcile local state.
7. If NOT_FOUND/non-final, keep case unresolved and follow controlled recovery policy.
8. Record all actions for audit.

Canonical lab scenario: TIMEOUT_AFTER_EFFECT -> local AUTH_UNKNOWN -> issuer AUTH_APPROVED -> reconciliation -> no duplicate authorization.
