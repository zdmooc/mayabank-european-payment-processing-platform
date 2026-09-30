# I05 Runtime Evidence — Apache Kafka

**Status:** WORKFLOW_IMPLEMENTED / RUNTIME_RESULT_PENDING

Workflow: `Kafka Runtime Evidence`

It creates a real single-node Apache Kafka KRaft broker in CI, creates the card lifecycle topics, produces a synthetic authorization event and consumes it back.

Successful execution permits only this claim:

`RUNTIME_PROVEN — CI SINGLE-NODE KAFKA`

It does not prove broker HA, cross-cluster replication or production retention.
