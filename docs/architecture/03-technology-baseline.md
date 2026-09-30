# Technology Baseline

**Status:** ACCEPTED REFERENCE BASELINE  
**Date:** 2026-09-30

## Objective

Align the reference platform with the strongest public Estreem technology signals while keeping the POC small enough to execute locally.

## Tier 1 — Core runtime

These technologies are part of the implementation baseline:

| Capability | Technology | Role |
|---|---|---|
| Application runtime | Java / Spring Boot | payment services |
| Relational state | PostgreSQL | transactional state, idempotency, ledger, outbox/inbox |
| Event backbone | Kafka | asynchronous payment lifecycle events |
| Containers | Kubernetes-compatible | portable packaging |
| Enterprise orchestration target | OpenShift | target orchestration platform |
| Local multi-cluster | Kind | low-cost local proof |
| GitOps | Argo CD | deployment/promotion control |
| Observability | OpenTelemetry | traces/metrics/log correlation |
| Metrics | Prometheus | technical/business metrics |
| Dashboards | Grafana | operations/SRE |

## Tier 2 — Deferred but planned

These are retained because they directly match public Estreem technology signals, but they are introduced only when they add architectural proof.

### Keycloak

Target iteration: I12.

Use for:

- OAuth2/OIDC;
- machine identities;
- service scopes;
- role separation;
- negative authorization tests.

### Gravitee

Target iteration: I12/I14.

Use for:

- Acquiring APIs;
- issuer/partner APIs;
- rate limits;
- policies;
- API product/lifecycle concepts.

The POC can start without Gravitee.

### Payment Platform SDK

Target: starts in I01/I02 and evolves continuously.

Use for reusable Java foundations:

- correlation;
- error model;
- event envelope;
- idempotency helpers;
- security context abstractions;
- observability conventions;
- API conventions;
- compatibility/versioning rules.

This is a first-class platform component.

## Tier 3 — Architecture / compatibility only

### GitLab

Estreem publicly cites GitLab for CI/CD.

The POC uses GitHub Actions as its executable CI because the repository is hosted on GitHub.

We model CI/CD portability instead of installing a second SCM/CI platform locally.

Possible future artifact:

- reference `.gitlab-ci.yml` equivalent;
- pipeline mapping GitHub Actions -> GitLab CI.

No local GitLab runtime is required.

### Axway MFT

Retained as a future managed-file-transfer boundary, mainly for clearing/settlement/reconciliation file flows.

Initial implementation:

- generic MFT/SFTP adapter;
- synthetic clearing/settlement files;
- checksum/duplicate/missing-file scenarios.

Axway itself is not required for the local POC.

### IBM Cloud

Retained as an enterprise cloud projection because the job posting explicitly mentions IBM Cloud.

The local POC does not require paid IBM Cloud resources.

I16 may document:

- IBM Cloud/OpenShift target mapping;
- network/IAM/observability placement;
- multi-region projection;
- cost and operational trade-offs.

## Local resource rule

Do not run all enterprise products simultaneously.

Progressive enablement:

```text
I01-I02  Java + tests + PostgreSQL
I03-I04  scheme simulators + platform SDK
I05      + Kafka
I06-I11  core payment processing
I12      + Keycloak, API security, optional Gravitee
I13      + OpenTelemetry / Prometheus / Grafana
I14      + OpenShift-compatible packaging / GitOps
I15      + Kind multi-cluster
I16      + IBM Cloud / Axway / GitLab compatibility projections
```

## Principle

The POC follows:

**architecture fidelity > product-logo fidelity**

A technology is deployed only when it proves a useful capability.
