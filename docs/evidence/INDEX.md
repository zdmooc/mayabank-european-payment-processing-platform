# Evidence Index

## I00 → I16 status

| Iteration | Evidence | Current claim |
|---|---|---|
| I00 | `I00-foundation.md` | COMPLETE / CI PASS |
| I01 | `I01-domain-sdk.md` | IMPLEMENTED / JAVA CI PASS |
| I02 | `I02-authorization-vertical-slice.md` + `I02-I09-api-runtime.md` | AUTHORIZATION + API E2E RUNTIME_PROVEN |
| I03 | `I03-scheme-simulators.md` | SYNTHETIC CB/VISA/MC / JAVA CI PASS |
| I04 | `I04-capture-reversal.md` | CAPTURE/REVERSAL / JAVA CI PASS |
| I05 | `I05-eventing.md` + `I05-kafka-runtime.md` | OUTBOX/INBOX CI + KAFKA SINGLE-NODE RUNTIME_PROVEN |
| I06 | `I06-ledger.md` + `I06-postgresql-runtime.md` | LEDGER CI + POSTGRESQL CONSTRAINTS RUNTIME_PROVEN |
| I07 | `I07-clearing.md` | CLEARING / JAVA CI PASS + API E2E |
| I08 | `I08-settlement.md` | SETTLEMENT / JAVA CI PASS + API E2E |
| I09 | `I09-reconciliation.md` + `I02-I09-api-runtime.md` | UNKNOWN RECONCILIATION RUNTIME_PROVEN |
| I10 | `I10-refund-dispute.md` | REFUND/DISPUTE / JAVA CI PASS |
| I11 | `I11-fraud-risk.md` | SYNTHETIC FRAUD/RISK / JAVA CI PASS |
| I12 | `I12-security-api.md` | SPRING SECURITY/API IMPLEMENTED; KEYCLOAK/GRAVITEE LIVE PENDING |
| I13 | `I13-observability.md` | APP PROMETHEUS ENDPOINT RUNTIME_PROVEN; EXTERNAL STACK PENDING |
| I14 | `I14-openshift-gitops.md` | CONTAINER BUILD + KUSTOMIZE PLATFORM CI PASS; OPENSHIFT LIVE PENDING |
| I15 | `I15-kind-multicluster.md` | RUNTIME_PROVEN — CI LOCAL SYNTHETIC MULTI_CLUSTER |
| I16 | `I16-enterprise-reference.md` + HLD/PRA/DORA/demo | REFERENCE_ARCHITECTURE COMPLETE |

## Executed runtime / CI gates

| Gate | Run | Result | Claim |
|---|---:|---|---|
| Payment API E2E + Prometheus endpoint | `36686201540` | SUCCESS | CI PAYMENT API E2E |
| Kafka Runtime Evidence | `36685727430` | SUCCESS | CI SINGLE-NODE KAFKA |
| PostgreSQL Runtime Evidence | `36685669720` | SUCCESS | CI POSTGRESQL SCHEMA CONSTRAINTS |
| Kind Multi-Cluster Resilience | `36685357031` | SUCCESS | CI LOCAL SYNTHETIC MULTI_CLUSTER |
| Platform CI | `36686040853` | SUCCESS | container build + Kustomize validation |
| Java Domain CI | `36686041016` | SUCCESS | full Java build/tests |

## Not runtime-proven

- live OpenShift cluster deployment;
- live Keycloak;
- live Gravitee;
- Prometheus/Grafana/OTel backend stack;
- Kafka HA / cross-cluster replication;
- PostgreSQL HA / PITR / cross-region failover;
- IBM Cloud deployment;
- Axway runtime;
- GitLab runtime;
- production PCI-DSS / DORA compliance;
- real CB/Visa/Mastercard network integration.

## Claim discipline

Evidence says exactly what was executed. Design/configuration is never silently promoted to production or HA proof.
