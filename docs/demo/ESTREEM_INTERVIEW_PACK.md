# Estreem Solution Architect — Interview Pack

**Date:** 2026-09-30  
**Basis:** public Estreem Solution Architect posting + runtime evidence from this repository.

## 1. Positioning statement

This repository is an **independent synthetic reference platform** built to demonstrate how I reason about a modern European card-processing platform.

It is not a reproduction of Estreem internal architecture.

The purpose is to show:
- architecture discovery;
- option analysis and decision records;
- Java platform foundations;
- distributed-systems correctness;
- API and event-driven integration;
- security-by-design;
- NFR discipline;
- OpenShift/Kubernetes packaging;
- multi-cluster resilience reasoning;
- explicit evidence boundaries.

## 2. Mapping to the public Estreem role

| Public role expectation | Evidence in this repository |
|---|---|
| Technical discovery / INSIGHT | ADRs, architecture options, truth model, decision gates |
| Java hands-on expertise | Java 21 multi-module code, Spring Boot API, SDK |
| Internal SDK / transversal foundations | Payment Platform SDK |
| Distributed systems | idempotency, UNKNOWN, Outbox/Inbox, reconciliation |
| Kafka | real CI Kafka produce/consume |
| PostgreSQL | real CI integrity/uniqueness constraints |
| API & integration | OpenAPI, Spring REST API, scheme adapters |
| Security by design | token-only synthetic cards, OAuth2 scopes, non-root container, NetworkPolicy |
| Keycloak | reference configuration + Spring Resource Server integration |
| Gravitee | API Management boundary |
| OpenShift | OpenShift-compatible manifests, Route, security controls |
| NFR culture | SLI/SLO, failure scenarios, HA/PRA, DORA evidence map |
| Hands-on POC | API E2E, Kafka, PostgreSQL, Kind multi-cluster workflows |
| Influence / standards | ADRs, SDK conventions, architecture guidelines |

The Estreem posting explicitly emphasizes technical discovery, Java/internal SDK, distributed systems, Kafka, PostgreSQL, IBM Cloud, security-by-design, Gravitee, Keycloak, Axway, GitLab and OpenShift.

## 3. 90-second opening

> I approached the subject as a platform architect rather than as a product-cloning exercise. I first separated public facts from assumptions, then defined the core card-processing bounded contexts: acquiring, issuing, authorization, capture, clearing, settlement, ledger and reconciliation. I built the safety invariants first — idempotency, explicit UNKNOWN state, no blind financial retry and reconciliation — before adding scale and platform concerns.
>
> The POC is executable. The API proves an end-to-end authorization-to-settlement flow. Kafka and PostgreSQL are exercised in CI. The most important failure case is also proven: the issuer authorizes, the response is lost, the acquirer sees UNKNOWN, and reconciliation resolves the transaction from authoritative inquiry without sending a second financial authorization.
>
> On the platform side I package for OpenShift, use GitOps, and run a three-cluster Kind resilience proof where Region A is deleted and Region B remains operational. I also created a Java Payment Platform SDK because the public Estreem role specifically highlights reusable Java foundations across teams.
>
> I am careful about evidence boundaries: Kind is a local synthetic multi-cluster proof, not a datacenter HA claim; Keycloak and Gravitee are configured but not claimed runtime-proven; and the scheme simulators do not reproduce proprietary CB, Visa or Mastercard protocols.

## 4. Architecture walk-through

```text
Merchant / POS / E-commerce
        |
        v
API Management boundary
        |
        v
Acquirer Processor
        |
        v
Fraud / Risk
        |
        v
Scheme Router
  +-----+------+-----+
  |            |     |
 CB-SIM     VISA-SIM MC-SIM
  \            |     /
        v
Issuer Processor
        |
        v
Authorization
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
        +--> Ledger / Audit
        +--> Refund / Dispute
```

Cross-cutting:
- Payment Platform SDK;
- Kafka;
- PostgreSQL;
- OAuth2/OIDC;
- observability;
- GitOps;
- OpenShift/Kubernetes;
- resilience evidence.

## 5. The most important design decision

The most important decision is to keep these state planes separate:

```text
Authorization
Capture
Clearing
Settlement
Reconciliation
Dispute
```

A card payment can be:
- authorized but not captured;
- captured but not cleared;
- cleared but not settled;
- locally UNKNOWN while the issuer has already committed the effect.

Flattening all of this into a single `paymentStatus` would hide operational ambiguity.

## 6. The UNKNOWN scenario

```text
Merchant
  |
  v
Acquirer
  |
  v
Visa synthetic simulator
  |
  v
Issuer -> APPROVED
  |
  X response lost
  |
Acquirer -> AUTH_UNKNOWN
  |
  v
Issuer inquiry -> AUTH_APPROVED
  |
  v
Reconciliation resolves
  |
  v
NO SECOND AUTHORIZATION
```

This is the key distributed-payment correctness story.

## 7. Why a Java Platform SDK

The SDK contains only cross-cutting foundations:
- correlation;
- common error model;
- event envelope;
- idempotency helpers;
- security context abstractions;
- observability conventions;
- API conventions;
- compatibility/versioning.

It does **not** contain:
- acquiring business rules;
- issuing rules;
- authorization policy;
- clearing logic;
- settlement logic.

This keeps team autonomy while providing engineering coherence.

## 8. Technology choices

### Runtime-proven in CI
- Java / Spring Boot;
- Dockerized API;
- Kafka single-node produce/consume;
- PostgreSQL integrity constraints;
- Kind multi-cluster resilience;
- Prometheus endpoint;
- Kustomize/container build.

### Implemented/configured, not live-proven
- Keycloak integration;
- Gravitee boundary;
- Argo CD ApplicationSet;
- OpenShift-specific target artifacts;
- OTel/Prometheus/Grafana backend stack.

### Architecture projection
- IBM Cloud;
- Axway MFT;
- GitLab CI portability.

## 9. Questions likely to be asked

### Why did you not deploy every Estreem technology?

Because the objective is to prove architectural capabilities, not product-logo parity. I deploy a technology only when it adds evidence. GitHub Actions proves CI/CD here; running GitLab locally would add operational weight but little architectural evidence.

### Why Kind if OpenShift is the target?

Kind is the low-cost local execution environment for multi-cluster behavior. OpenShift remains the enterprise orchestration target. The same packaging principles and GitOps model are preserved.

### Why not claim active/active database?

Because financial state needs explicit conflict and writer semantics. I prefer a controlled single-writer/promotion model until a real distributed database design and evidence justify something stronger.

### How do you prevent double payment?

By combining:
- business idempotency keys;
- canonical authorization/capture/settlement references;
- database uniqueness;
- Outbox/Inbox;
- explicit UNKNOWN;
- authoritative inquiry;
- reconciliation;
- no blind retry.

### Where does Wero fit?

Wero remains a distinct A2A/SCT Inst rail. A common orchestration facade may expose both card and Wero journeys, but the rails, state machines and external dependencies remain separate.

### What is still missing for production?

Real OpenShift execution, real IAM/API-gateway runtime, Kafka HA/cross-cluster replication, PostgreSQL HA/PITR/failover, real external-scheme connectivity, production security certification and operational acceptance.

## 10. Evidence runs to quote

| Proof | Run |
|---|---:|
| Payment API E2E + Prometheus | 36686201540 |
| Kafka runtime | 36685727430 |
| PostgreSQL constraints | 36685669720 |
| Kind multi-cluster resilience | 36685357031 |
| Platform CI | 36686040853 |
| Java CI | 36686041016 |

## 11. Closing message

The strongest message is not that this repository “looks like Estreem”.

The stronger message is:

> I can take a strategic platform problem, separate facts from assumptions, structure the bounded contexts, define the distributed-system invariants, implement a working reference path, prove failure behavior, and keep architecture claims aligned with actual evidence.
