# MayaBank European Payment Processing Platform

Independent reference implementation for a modern European **card payment processor**.

**Scope:** Issuing · Acquiring · Authorization · Capture · Clearing · Settlement · Reconciliation · Synthetic CB/Visa/Mastercard simulators · Kafka · OpenShift/Kubernetes · Resilience.

> This repository is an independent synthetic reference implementation. It does **not** reproduce Estreem, BNP Paribas, Groupe BPCE, EPI, CB, Visa or Mastercard internal systems, protocols, contracts or proprietary architectures.

## Status

**I00 → I16: IMPLEMENTED — CORE CI / RUNTIME EVIDENCE PASS**

The repository has reached its complete reference-POC baseline.

Executed evidence:

| Gate | GitHub Actions run | Result |
|---|---:|---|
| Payment API E2E + live Prometheus endpoint | `36686201540` | SUCCESS |
| Kafka single-node runtime | `36685727430` | SUCCESS |
| PostgreSQL schema/integrity runtime | `36685669720` | SUCCESS |
| Kind local synthetic multi-cluster resilience | `36685357031` | SUCCESS |
| Platform container/Kustomize CI | `36686040853` | SUCCESS |
| Java domain/build/tests | `36686041016` | SUCCESS |

Runtime-proven scope includes:
- Dockerized Spring Boot payment API;
- `AUTHORIZE -> CAPTURE -> CLEAR -> SETTLE` happy path;
- `TIMEOUT_AFTER_EFFECT -> AUTH_UNKNOWN -> issuer inquiry -> AUTH_APPROVED` reconciliation;
- live application Prometheus metrics endpoint;
- real single-node Kafka produce/consume;
- real PostgreSQL uniqueness/integrity constraints;
- real local three-cluster Kind topology with complete Region A loss and Region B survival/recovery.

Still **not** runtime-proven:
- live OpenShift deployment;
- live Keycloak or Gravitee;
- Prometheus/Grafana/OTel backend stack;
- Kafka HA/cross-cluster replication;
- PostgreSQL HA/PITR/cross-region failover;
- IBM Cloud or Axway runtime;
- production PCI-DSS/DORA compliance;
- real CB/Visa/Mastercard network connectivity.

See `docs/evidence/INDEX.md` for the exact claim boundary.

## Why this project exists

The portfolio already contains strong runtime evidence for instant payments, Wero-like Consumer/Acceptor journeys, idempotency, UNKNOWN handling, Transactional Outbox/Inbox, reconciliation, OAuth2, observability and resilience.

This repository closes a different gap: the **card processing lifecycle**.

```text
Merchant / POS / E-commerce
        |
        v
Acquiring Edge
        |
        v
Acquirer Processor
        |
        v
Scheme Router
   +----+----+----+
   |         |    |
   v         v    v
 CB-SIM   VISA-SIM MC-SIM
   \         |    /
        v
Issuer Processor
        |
        v
Authorization Engine
        |
   +----+---------+---------+
   |              |         |
APPROVED       DECLINED   UNKNOWN
   |
   v
Capture
   |
   v
Clearing
   |
   v
Settlement
   |
   v
Reconciliation
   |
   v
Ledger / Audit
```

## Wero boundary

Wero remains a **distinct A2A / SCT Inst rail**.

```text
CARD:
Merchant -> Acquirer -> Scheme -> Issuer

WERO:
User/Merchant -> Wero/EPI boundary -> SCT Inst -> PSP/Bank
```

Wero is never modelled as a CB/Visa/Mastercard scheme.

## Truth model

Every architectural or runtime claim is classified as one of:

- `PUBLIC_VERIFIED`
- `REFERENCE_ARCHITECTURE`
- `INFERRED`
- `RUNTIME_PROVEN`
- `TO_BE_VERIFIED`

Rules:

- synthetic scheme simulators are clearly labelled as synthetic;
- ISO 8583 / EMV / 3DS are treated as reference boundaries unless a public, authorized implementation is explicitly built and tested;
- local single-node CRC evidence never proves multi-worker, multi-AZ or multi-site HA;
- local Kind multi-cluster evidence proves only a **synthetic local multi-cluster topology**, not datacenter or cloud-region independence.

## Core invariants

The final platform must demonstrate:

```text
one merchant intent
-> at most one authorization effect
-> at most one capture effect
-> traceable clearing
-> at most one settlement posting
-> reconciliation resolves ambiguity
-> no blind financial retry
-> no hidden state overwrite
```

## Bounded contexts

- Merchant / Acquiring
- Card / Issuing
- Authorization
- Scheme Routing
- Capture
- Clearing
- Settlement
- Ledger
- Reconciliation
- Dispute / Chargeback
- Fraud / Risk
- Platform / Security / Observability

## Technology direction

The public Estreem Solution Architect posting names **Java, Gravitee, Kafka, PostgreSQL, Keycloak, Axway, GitLab and OpenShift**, and also emphasizes an internal Java SDK, IBM Cloud, distributed systems, API/integration, security-by-design and strong NFR culture.

The POC keeps these signals selectively.

### Core runtime

- Java / Spring Boot
- PostgreSQL
- Kafka
- OpenShift-compatible packaging
- Kubernetes / Kind for local multi-cluster experiments
- Argo CD / GitOps
- OpenTelemetry
- Prometheus / Grafana

### Strategic platform foundation

- internal Java **Payment Platform SDK** — implemented from I01 and evolved across the program

### Configured / runtime pending

- Keycloak — security configuration present; live runtime pending
- Gravitee — API-management boundary present; live runtime pending

### Projection / compatibility only

- GitLab — CI/CD compatibility; executable CI remains GitHub Actions
- Axway MFT — generic MFT/SFTP boundary for clearing/settlement scenarios
- IBM Cloud — enterprise cloud target architecture, not required for local runtime

Principle: **architecture fidelity > product-logo fidelity**.

No technology is considered runtime-proven until evidence is committed.

See:
- `docs/architecture/03-technology-baseline.md`
- `docs/architecture/04-java-platform-sdk.md`
- `docs/sources/ESTREEM_SOLUTION_ARCHITECT_TECH_SIGNAL_2026-09-28.md`

## Reuse strategy

Patterns may be reused from existing MayaBank repositories, especially:

- `mayabank-instant-payments-resilience-platform`
  - idempotency
  - UNKNOWN handling
  - Transactional Outbox
  - Inbox/DLQ
  - reconciliation
  - correlation
  - OAuth2 workloads
  - observability
  - chaos evidence methodology

- `payment-hub-iso20022-opf-reference`
  - settlement / reconciliation concepts
  - NFR templates
  - runbook structure
  - operational model

- `mayabank-api-management-architecture`
  - OAuth/OIDC/mTLS
  - OpenAPI/AsyncAPI
  - API lifecycle and governance

- `dora-operational-resilience-architecture-masterbook`
  - resilience test planning
  - BIA / RTO / RPO framing
  - evidence methodology

The Cards domain itself is new and must not be copied from Wero/SCT Inst runtimes.

## Roadmap

| Iteration | Scope | Status |
|---|---|---|
| I00 | Repository foundation, truth model, C4, ADRs, DoD | COMPLETE |
| I01 | Card domain model + Platform SDK | COMPLETE / JAVA CI |
| I02 | Authorization vertical slice | COMPLETE / API E2E |
| I03 | Synthetic CB/Visa/Mastercard simulators | COMPLETE / JAVA CI |
| I04 | Capture & reversal | COMPLETE / JAVA CI |
| I05 | Kafka / Outbox / Inbox | COMPLETE / KAFKA RUNTIME PROVEN |
| I06 | Ledger | COMPLETE / POSTGRESQL CONSTRAINTS PROVEN |
| I07 | Clearing | COMPLETE / API E2E |
| I08 | Settlement | COMPLETE / API E2E |
| I09 | Reconciliation | COMPLETE / UNKNOWN RESOLUTION PROVEN |
| I10 | Refund / chargeback / dispute | COMPLETE / JAVA CI |
| I11 | Fraud / risk | COMPLETE / JAVA CI |
| I12 | Security / API | IMPLEMENTED; KEYCLOAK/GRAVITEE LIVE PENDING |
| I13 | Observability / SRE | APP METRICS PROVEN; BACKENDS PENDING |
| I14 | OpenShift / GitOps packaging | CONTAINER/KUSTOMIZE CI PROVEN; OPENSHIFT LIVE PENDING |
| I15 | Kind multi-cluster resilience | RUNTIME_PROVEN — LOCAL SYNTHETIC MULTI_CLUSTER |
| I16 | Enterprise HLD / HA-PRA / DORA / projections / demo | COMPLETE |

## Local multi-cluster target

When the project reaches resilience validation, the local topology may use Kind:

```text
kind-pay-mgmt
   |
   +--> kind-pay-region-a
   |
   +--> kind-pay-region-b
```

Target use cases:

- Argo CD multi-cluster;
- active/passive or controlled active/active experiments;
- cluster loss;
- cross-cluster event replication;
- controlled failover;
- reconciliation after recovery.

This is a **local synthetic proof**, not a production HA claim.

## Initial repository structure

```text
docs/
  architecture/
  adr/
  nfr/
  security/
  resilience/
  dora/
  runbooks/
  evidence/

contracts/
  openapi/
  asyncapi/
  events/

services/
  acquiring-service/
  issuer-service/
  authorization-service/
  scheme-router/
  capture-service/
  clearing-service/
  settlement-service/
  reconciliation-service/
  ledger-service/
  dispute-service/

simulators/
  cb-simulator/
  visa-simulator/
  mastercard-simulator/

platform/
  kubernetes/
  kind/
  openshift/
  kafka/
  postgresql/
  keycloak/
  observability/

gitops/
scripts/
tests/
evidence/
```

## Definition of Done

Each implementation iteration must produce:

```text
CODE
+ TESTS
+ ARCHITECTURE
+ RUNTIME EVIDENCE
+ CLAIM BOUNDARY
```

Allowed status labels:

- `DESIGNED`
- `IMPLEMENTED`
- `TESTED`
- `DEPLOYED`
- `RUNTIME_PROVEN`
- `REFERENCE_DESIGN`

## Portfolio governance

Program decision and bootstrap source:

- `zdmooc/cadrage_202682030`
- `portfolio/ESTREEM_CARD_PROCESSING_SIGNAL_2026-09-30.md`
- `portfolio/CARD_PROCESSING_REUSE_ADAPT_NEW_MATRIX_2026-09-30.md`
- `portfolio/CARD_PROCESSING_BOOTSTRAP_BLUEPRINT_2026-09-30.md`

## License

This repository is intended for educational, architectural and portfolio demonstration purposes. No proprietary payment network implementation is included.
