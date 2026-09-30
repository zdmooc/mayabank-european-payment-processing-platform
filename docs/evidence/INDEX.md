# Evidence Index

## Foundation and domain

| Iteration | Evidence | Current claim |
|---|---|---|
| I00 | `I00-foundation.md` | COMPLETE / CI PASS |
| I01 | `I01-domain-sdk.md` | Java domain/SDK CI evidence |
| I02 | `I02-authorization-vertical-slice.md` | authorization logic CI evidence |
| I03 | `I03-scheme-simulators.md` | synthetic scheme CI evidence |
| I04 | `I04-capture-reversal.md` | capture/reversal CI evidence |
| I05 | `I05-eventing.md` | Outbox/Inbox logic; Kafka live pending |
| I06 | `I06-ledger.md` | ledger logic; durable DB pending |
| I07 | `I07-clearing.md` | clearing logic CI evidence |
| I08 | `I08-settlement.md` | settlement logic CI evidence |
| I09 | `I09-reconciliation.md` | reconciliation logic CI evidence |
| I10 | `I10-refund-dispute.md` | refund/dispute CI evidence |
| I11 | `I11-fraud-risk.md` | fraud/risk CI evidence |
| I12 | `I12-security-api.md` | API/security config; Keycloak/Gravitee runtime pending |
| I13 | `I13-observability.md` | observability config; stack runtime pending |
| I14 | `I14-openshift-gitops.md` | image/manifests CI; OpenShift live pending |
| I15 | `I15-kind-multicluster.md` | Kind workflow determines runtime claim |
| I16 | this index + HLD/PRA/DORA | enterprise reference complete |

## Claim discipline

Evidence must say exactly what was executed. Design/configuration is never silently promoted to runtime proof.
