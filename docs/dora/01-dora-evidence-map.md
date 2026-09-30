# DORA-Oriented Evidence Map

**Status:** REFERENCE / EVIDENCE INDEX — not a compliance certification

| DORA-oriented concern | Repository evidence |
|---|---|
| Critical service mapping | HLD and bounded contexts |
| ICT dependency mapping | technology baseline / C4 |
| Resilience by design | ADRs, UNKNOWN/reconciliation |
| Incident/failure scenarios | I03, I05, I09, I15 |
| Data integrity | idempotency, ledger, Outbox/Inbox |
| Recovery | reconciliation + Kind region-loss workflow |
| Access control | I12 OAuth2/Keycloak configuration |
| Observability | I13 |
| Change control | GitHub CI + GitOps |
| Test evidence | docs/evidence + GitHub Actions |
| Third-party/scheme dependency | synthetic scheme boundaries |
| Exit/reversibility | portable Kubernetes/OpenShift manifests |

The repository demonstrates engineering patterns relevant to operational resilience. It does not claim organizational or regulatory DORA compliance.
