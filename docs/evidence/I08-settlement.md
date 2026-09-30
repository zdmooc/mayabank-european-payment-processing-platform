# I08 Evidence — Settlement

**Status:** IMPLEMENTED / JAVA CI PASS / API E2E RUNTIME_PROVEN

## Assertions

- non-cleared positions cannot settle;
- one clearing position yields one canonical settlement;
- SETTLED posts one ledger movement;
- replay does not double-post;
- SETTLEMENT_UNKNOWN does not create a false settled ledger posting.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.
- `CLEARED -> SETTLED` executed through API E2E run `36686201540` — **SUCCESS**.
- PostgreSQL selected integrity constraints: run `36685669720` — **SUCCESS**.

## Boundary

External settlement networks and production PostgreSQL durability/HA are not claimed.
