# ADR-010 — Kubernetes/OpenShift packaging with GitOps

**Status:** Accepted

## Decision

Package workloads as portable Kubernetes resources with OpenShift-compatible overlays and GitOps promotion.

## Local strategy

- I00/I01: no cluster required;
- I02/I03: local app/database runtime first;
- I05+: Kafka;
- I14: OpenShift packaging;
- I15/I16: Kind multi-cluster experiments.

## Boundary

Kind proves local multi-cluster behavior only. OpenShift target design is not production proof until executed and evidenced.
