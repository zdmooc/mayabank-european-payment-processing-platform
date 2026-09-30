# Enterprise HLD — European Card Payment Processing Reference Platform

**Iteration:** I16  
**Status:** REFERENCE_ARCHITECTURE

## 1. Context

This HLD describes an independent, synthetic European card-processing reference platform.

It does not describe Estreem, BNP Paribas, Groupe BPCE, EPI, CB, Visa or Mastercard internal architecture.

## 2. Functional chain

```text
Merchant / POS / E-commerce
        |
        v
API Management
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
        +--> Dispute / Refund
```

## 3. Platform layers

### Edge
- Gravitee target;
- OAuth2/OIDC;
- correlation;
- throttling and API lifecycle.

### Application
- Java / Spring Boot;
- bounded contexts;
- Payment Platform SDK.

### Data
- PostgreSQL transactional reference store;
- immutable financial references;
- Outbox/Inbox.

### Eventing
- Kafka event backbone;
- versioned events;
- at-least-once delivery;
- replay and DLQ.

### Security
- Keycloak target;
- machine identities;
- payments.read/payments.write scopes;
- mTLS boundary for partner/scheme connectivity.

### Operations
- OpenTelemetry;
- Prometheus;
- Grafana;
- SLI/SLO/error-budget approach.

### Platform
- Kubernetes portable base;
- OpenShift enterprise target;
- Argo CD GitOps;
- local Kind multi-cluster evidence.

## 4. State planes

Authorization, Capture, Clearing, Settlement, Reconciliation and Dispute are independent planes. Derived customer-facing state never destroys canonical history.

## 5. Financial invariants

```text
one merchant intent
-> at most one authorization effect
-> at most one capture effect
-> traceable clearing
-> at most one settlement posting
-> reconciliation resolves ambiguity
-> no blind retry
-> no hidden state overwrite
```

## 6. Wero boundary

Wero remains a distinct A2A / SCT Inst rail. A future orchestration facade may expose a common commercial interface, but card and Wero rail semantics remain separate.
