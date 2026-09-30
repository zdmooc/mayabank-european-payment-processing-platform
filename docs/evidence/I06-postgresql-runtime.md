# I06 Runtime Evidence — PostgreSQL

**Status:** RUNTIME_PROVEN — CI POSTGRESQL SCHEMA CONSTRAINTS

Workflow: `PostgreSQL Runtime Evidence`

It runs PostgreSQL 16 in CI and validates:
- idempotency-key uniqueness;
- Inbox event deduplication;
- unique financial business references;
- balanced double-entry movement.

Successful execution permits:

`RUNTIME_PROVEN — CI POSTGRESQL SCHEMA CONSTRAINTS`

It does not prove PostgreSQL HA, backup/PITR or cross-region failover.


## Executed evidence

- GitHub Actions workflow: `PostgreSQL Runtime Evidence`
- Run: `36685669720`
- Result: **SUCCESS**
- idempotency uniqueness enforced
- Inbox duplicate rejected
- financial business reference uniqueness enforced
- double-entry sample movement balanced

Claim: `RUNTIME_PROVEN — CI POSTGRESQL SCHEMA CONSTRAINTS`.

Boundary unchanged: no HA, PITR or cross-region failover claim.
