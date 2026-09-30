# I05 Runtime Evidence — Apache Kafka

**Status:** RUNTIME_PROVEN — CI SINGLE-NODE KAFKA

Workflow: `Kafka Runtime Evidence`

It creates a real single-node Apache Kafka KRaft broker in CI, creates the card lifecycle topics, produces a synthetic authorization event and consumes it back.

Successful execution permits only this claim:

`RUNTIME_PROVEN — CI SINGLE-NODE KAFKA`

It does not prove broker HA, cross-cluster replication or production retention.


## Executed evidence

- GitHub Actions workflow: `Kafka Runtime Evidence`
- Run: `36685727430`
- Result: **SUCCESS**
- Apache Kafka KRaft broker started
- card lifecycle topics created
- synthetic `AUTH_APPROVED` event produced and consumed
- correlationId preserved

Claim: `RUNTIME_PROVEN — CI SINGLE-NODE KAFKA`.

Boundary unchanged: no broker HA or cross-cluster replication claim.
