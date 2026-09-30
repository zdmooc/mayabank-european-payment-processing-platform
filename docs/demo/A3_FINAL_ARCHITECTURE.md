# A3 Final Architecture — European Payment Processing Reference Platform

**Format intent:** one-page landscape architecture board for interview/demo use.

## Main message

**Correctness before scale — architecture evidence before claims.**

```mermaid
flowchart LR
    M[Merchant / POS / E-commerce] --> G[API Management Boundary]
    G --> A[Acquirer Processor]
    A --> F[Fraud / Risk]
    F --> R[Scheme Router]

    R --> CB[CB-SIM]
    R --> VI[VISA-SIM]
    R --> MC[MC-SIM]

    CB --> I[Issuer Processor]
    VI --> I
    MC --> I

    I --> AU[Authorization]
    AU --> C[Capture]
    C --> CL[Clearing]
    CL --> S[Settlement]
    S --> RC[Reconciliation]

    RC --> L[Ledger / Audit]
    RC --> D[Refund / Dispute]

    SDK[Payment Platform SDK] -.-> A
    SDK -.-> I
    SDK -.-> AU
    SDK -.-> CL
    SDK -.-> S
    SDK -.-> RC

    K[Kafka / Outbox / Inbox] -.-> AU
    K -.-> C
    K -.-> CL
    K -.-> S
    K -.-> RC

    P[(PostgreSQL)] -.-> AU
    P -.-> L
    P -.-> RC

    O[OpenTelemetry / Prometheus / Grafana] -.-> A
    O -.-> AU
    O -.-> S

    subgraph PLATFORM[Platform]
      K8S[Kubernetes-compatible]
      OCP[OpenShift target]
      ARGO[Argo CD / GitOps]
      KIND[Kind: mgmt + region-a + region-b]
    end

    K8S --> OCP
    ARGO --> OCP
    KIND -. runtime proof .-> K8S
```

## Critical failure path

```mermaid
sequenceDiagram
    participant M as Merchant
    participant A as Acquirer
    participant S as Scheme Simulator
    participant I as Issuer
    participant R as Reconciliation

    M->>A: authorize
    A->>S: authorization request
    S->>I: authorize
    I-->>S: APPROVED
    S--xA: response lost
    A-->>M: AUTH_UNKNOWN
    A->>R: open reconciliation
    R->>I: authoritative inquiry
    I-->>R: AUTH_APPROVED
    R-->>A: resolved APPROVED
    Note over A,R: no second authorization
```

## Runtime evidence panel

- Payment API E2E — **PASS**
- Kafka produce/consume — **PASS**
- PostgreSQL financial constraints — **PASS**
- Kind 3-cluster region-loss test — **PASS**
- Docker/Kustomize platform CI — **PASS**
- Java build/tests — **PASS**

## Claim boundary panel

**Runtime-proven**
- payment API E2E;
- Kafka single-node;
- PostgreSQL constraints;
- local synthetic multi-cluster;
- application Prometheus endpoint.

**Reference/configured**
- OpenShift live deployment;
- Keycloak;
- Gravitee;
- external Prometheus/Grafana/OTel stack;
- Kafka HA;
- PostgreSQL HA;
- IBM Cloud;
- Axway.

## Wero boundary

```text
CARD:
Merchant -> Acquirer -> Scheme -> Issuer

WERO:
User/Merchant -> Wero/EPI boundary -> SCT Inst -> PSP/Bank
```

Wero is not modelled as a card scheme.
