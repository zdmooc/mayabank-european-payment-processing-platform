# Changelog

## 2026-09-30 — I00 Repository Foundation

- Repository initialized.
- Scope fixed to independent European card payment processing reference architecture.
- Truth model introduced.
- Wero/SCT Inst explicitly separated from card schemes.
- I00→I16 roadmap established.
- Kind multi-cluster target documented as local synthetic proof only.
- Initial architecture and ADR baseline added.

No runtime claim is made in I00.


## 2026-09-30 — Estreem technology alignment

- Public Estreem Solution Architect technology signal recorded.
- Core POC stack retained: Java/Spring Boot, PostgreSQL, Kafka, OpenShift-compatible packaging.
- Kind retained for local synthetic multi-cluster proof.
- Keycloak and Gravitee scheduled for later iterations.
- Internal Java Payment Platform SDK promoted to first-class architecture component.
- GitLab treated as CI/CD portability target; GitHub Actions remains executable CI.
- Axway retained as optional MFT boundary.
- IBM Cloud retained as enterprise target projection.
- Architecture fidelity explicitly prioritized over product-logo fidelity.


## 2026-09-30 — I01 → I16 complete reference-POC baseline

Implemented:
- Card domain model and Java Payment Platform SDK;
- authorization/acquiring/issuing/scheme routing;
- synthetic CB/Visa/Mastercard behavior;
- capture/reversal;
- Outbox/Inbox eventing;
- ledger;
- clearing;
- settlement;
- reconciliation;
- refund/dispute;
- fraud/risk;
- executable Spring Boot API;
- OAuth2/Keycloak and Gravitee integration boundaries;
- observability/SRE configuration;
- OpenShift-compatible packaging and Argo CD GitOps;
- executable Kind multi-cluster resilience proof;
- Enterprise HLD, HA/PRA, DORA evidence map and final demo.

Executed runtime evidence:
- Payment API E2E + live Prometheus endpoint: run `36686201540` SUCCESS;
- Kafka runtime: run `36685727430` SUCCESS;
- PostgreSQL constraints: run `36685669720` SUCCESS;
- Kind multi-cluster resilience: run `36685357031` SUCCESS;
- Platform CI: run `36686040853` SUCCESS;
- Java CI: run `36686041016` SUCCESS.

Remaining external/product runtime gates are intentionally not promoted: OpenShift live, Keycloak live, Gravitee live, external observability stack, Kafka HA, PostgreSQL HA, IBM Cloud and Axway.
