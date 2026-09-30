# I06 Evidence — Ledger

**Status:** IMPLEMENTED / JAVA CI PASS / POSTGRESQL CONSTRAINTS RUNTIME_PROVEN  
**Persistence evidence:** PostgreSQL 16 schema constraints run `36685669720` — SUCCESS

## Assertions

- every movement contains debit and credit lines;
- every movement nets to zero;
- duplicate capture reference does not create a second movement;
- duplicate settlement reference does not create a second movement;
- ledger is separated from commercial and scheme states.

## Boundary

This proves ledger logic plus selected PostgreSQL uniqueness/integrity constraints. It does not claim accounting completeness, production ledger semantics, PostgreSQL HA, backup/PITR or cross-region durability.
