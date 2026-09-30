# I06 Runtime Evidence — PostgreSQL

**Status:** WORKFLOW_IMPLEMENTED / RUNTIME_RESULT_PENDING

Workflow: `PostgreSQL Runtime Evidence`

It runs PostgreSQL 16 in CI and validates:
- idempotency-key uniqueness;
- Inbox event deduplication;
- unique financial business references;
- balanced double-entry movement.

Successful execution permits:

`RUNTIME_PROVEN — CI POSTGRESQL SCHEMA CONSTRAINTS`

It does not prove PostgreSQL HA, backup/PITR or cross-region failover.
