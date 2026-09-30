# MayaBank European Payment Processing Platform

Independent reference implementation for a modern European **card payment processor**.

**Scope:** Issuing · Acquiring · Authorization · Capture · Clearing · Settlement · Reconciliation · Synthetic CB/Visa/Mastercard simulators · Kafka · OpenShift/Kubernetes · Resilience.

> This repository is an independent synthetic reference implementation. It does **not** reproduce Estreem, BNP Paribas, Groupe BPCE, EPI, CB, Visa or Mastercard internal systems, protocols, contracts or proprietary architectures.

## Status

**I00 — Repository Foundation: IMPLEMENTED / CI VALIDATION PENDING**

No runtime claim is made at this stage.

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

Initial reference stack:

- Java / Spring Boot
- PostgreSQL
- Kafka
- OpenAPI / AsyncAPI
- OAuth2/OIDC / Keycloak
- OpenTelemetry
- Prometheus / Grafana
- GitOps / Argo CD
- Kubernetes / Kind for local multi-cluster experiments
- OpenShift-compatible packaging for enterprise projection

No technology is considered runtime-proven until evidence is committed.

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

| Iteration | Scope |
|---|---|
| I00 | Repository foundation, truth model, C4, ADRs, DoD |
| I01 | Card domain model |
| I02 | Authorization vertical slice |
| I03 | Synthetic CB/Visa/Mastercard simulators |
| I04 | Capture & reversal |
| I05 | Kafka / Outbox / Inbox |
| I06 | Ledger |
| I07 | Clearing |
| I08 | Settlement |
| I09 | Reconciliation |
| I10 | Refund / chargeback / dispute |
| I11 | Fraud / risk |
| I12 | Security / API |
| I13 | Observability / SRE |
| I14 | OpenShift / GitOps packaging |
| I15 | Chaos / resilience + local Kind multi-cluster experiments |
| I16 | Enterprise reference architecture, HA/PRA design, DORA evidence, interview/demo pack |

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
