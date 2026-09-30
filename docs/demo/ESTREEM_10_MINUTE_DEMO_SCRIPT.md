# 10-Minute Demo Script

## 0:00–1:00 — Context

Explain that the repository is an independent reference processor, not an Estreem clone.

Show:
- I00→I16 complete;
- evidence index;
- truth model.

## 1:00–3:00 — Functional architecture

Walk through:

`Merchant -> Acquirer -> Fraud -> Scheme -> Issuer -> Authorization -> Capture -> Clearing -> Settlement -> Reconciliation -> Ledger`.

Emphasize state-plane separation.

## 3:00–5:00 — Distributed correctness

Show the UNKNOWN scenario:

`TIMEOUT_AFTER_EFFECT -> local UNKNOWN -> issuer APPROVED -> reconciliation -> no retry`.

Explain why timeout is not equivalent to failure.

## 5:00–6:30 — Platform foundations

Show:
- Payment Platform SDK;
- Kafka Outbox/Inbox;
- PostgreSQL uniqueness;
- OAuth2 scopes;
- observability/correlation.

## 6:30–8:00 — Multi-cluster resilience

Show Kind topology:
- pay-mgmt;
- pay-region-a;
- pay-region-b.

Quote runtime proof:
- Region A deleted;
- Region B remains healthy;
- Region B pod deleted and recreated.

Clearly state that this is local synthetic multi-cluster proof, not datacenter HA.

## 8:00–9:00 — Estreem technology alignment

Map public signals:
- Java;
- Kafka;
- PostgreSQL;
- Keycloak;
- Gravitee;
- OpenShift;
- Axway;
- GitLab;
- IBM Cloud;
- internal SDK.

Explain selective adoption.

## 9:00–10:00 — Close

Close on the architecture method:

1. facts vs assumptions;
2. discovery;
3. bounded contexts;
4. invariants;
5. POC;
6. runtime evidence;
7. explicit claim boundaries;
8. enterprise projection.
