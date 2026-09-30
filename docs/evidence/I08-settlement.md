# I08 Evidence — Settlement

**Status:** IMPLEMENTED / CI_VALIDATION_PENDING

## Assertions

- non-cleared positions cannot settle;
- one clearing position yields one canonical settlement;
- SETTLED posts one ledger movement;
- replay does not double-post;
- SETTLEMENT_UNKNOWN does not create a false settled ledger posting.

## Boundary

The service is an in-memory lab model. External settlement network and PostgreSQL durability are not yet runtime-proven.
