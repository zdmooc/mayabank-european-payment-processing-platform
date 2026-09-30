# I06 Evidence — Ledger

**Status:** IMPLEMENTED / CI_VALIDATION_PENDING  
**Persistence:** in-memory logic only at this stage

## Assertions

- every movement contains debit and credit lines;
- every movement nets to zero;
- duplicate capture reference does not create a second movement;
- duplicate settlement reference does not create a second movement;
- ledger is separated from commercial and scheme states.

## Boundary

This does not claim accounting completeness, production ledger semantics or PostgreSQL durability yet.
