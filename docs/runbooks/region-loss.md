# Runbook — Payment Region Loss

## Detection
- API health failure;
- cluster unreachable;
- traffic error increase;
- Kafka/DB dependency alarms as applicable.

## Immediate controls
1. stop blind payment replay;
2. identify last authoritative states;
3. confirm surviving region readiness;
4. switch traffic only according to approved routing procedure;
5. open reconciliation for UNKNOWN/in-flight transactions.

## Recovery
1. restore failed region;
2. validate platform dependencies;
3. reconcile event/data backlog;
4. compare authorization/capture/clearing/settlement state;
5. verify no duplicate financial postings;
6. reintroduce traffic progressively.

## Exit criteria
- surviving service stable;
- no unexplained UNKNOWN backlog;
- ledger/reconciliation checks clean;
- evidence archived;
- post-incident review opened.
