# ADR-012 — Adopt public Estreem technology signals selectively

**Status:** Accepted

## Decision

Use public Estreem technology signals as an alignment input, not as a requirement to deploy every named product.

## Core runtime

- Java / Spring Boot
- PostgreSQL
- Kafka
- OpenShift-compatible packaging

## Deferred runtime

- Keycloak
- Gravitee

## Projection / compatibility

- GitLab
- Axway MFT
- IBM Cloud

## Local proof

- GitHub Actions for CI;
- Kind for local multi-cluster;
- Argo CD for GitOps.

## Rationale

The project aims to demonstrate architectural capabilities and platform engineering, not reproduce a proprietary vendor topology.
