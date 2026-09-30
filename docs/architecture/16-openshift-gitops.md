# OpenShift / GitOps Packaging

**Iteration:** I14  
**Status:** IMPLEMENTED / CI_VALIDATION_PENDING

## Packaging

- non-root container;
- resource requests/limits;
- readiness/liveness probes;
- ServiceAccount;
- NetworkPolicy;
- PodDisruptionBudget;
- Kustomize base;
- region-a / region-b overlays;
- Argo CD ApplicationSet reference.

## Portability

Base manifests stay Kubernetes-compatible. OpenShift-specific Route is kept separate from the base render path until an OpenShift runtime is used.

## GitOps

ApplicationSet targets clusters labelled:
- `mayabank.io/payment-region=true`
- `mayabank.io/region=region-a|region-b`

## Boundary

Manifest rendering and image build in GitHub CI are not OpenShift runtime proof.
