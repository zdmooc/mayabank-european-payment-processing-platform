# C4 Container View

**Status:** REFERENCE_ARCHITECTURE

```text
Merchant / POS
    |
    v
API Management boundary
(Gravitee from I12/I14)
    |
    v
Acquiring API
    |
    v
Acquirer Processor
    |
    v
Scheme Router
  /    |     \
CB   VISA    MC
SIM   SIM    SIM
  \    |     /
    v
Issuer Processor
    |
    v
Authorization Engine
    |
    +--> Capture Service
    |       |
    |       v
    |    Clearing Service
    |       |
    |       v
    |    Settlement Service
    |
    +--> Kafka Event Backbone
    |
    +--> Ledger
    |
    +--> Reconciliation Service
    |
    +--> Dispute Service
```

## Cross-cutting containers

### Core

- Java / Spring Boot
- Payment Platform SDK
- PostgreSQL
- Kafka
- OpenTelemetry
- Prometheus/Grafana
- Argo CD / GitOps
- Kubernetes/Kind
- OpenShift-compatible packaging

### Deferred

- Keycloak/OIDC
- Gravitee API Management

### Enterprise projection / compatibility

- Axway MFT boundary
- GitLab CI/CD portability
- IBM Cloud target mapping

## State separation

Authorization, capture, clearing and settlement are separate state planes. A single flattened status must never destroy their history.
