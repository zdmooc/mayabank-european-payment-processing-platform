# C4 Container View

**Status:** REFERENCE_ARCHITECTURE

```text
Merchant / POS
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

- PostgreSQL
- Kafka
- Keycloak/OIDC
- OpenTelemetry
- Prometheus/Grafana
- Argo CD / GitOps
- Kubernetes/Kind
- OpenShift-compatible packaging

## State separation

Authorization, capture, clearing and settlement are separate state planes. A single flattened status must never destroy their history.
