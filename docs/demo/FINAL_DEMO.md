# Final Demo — MayaBank European Payment Processing

## Scenario A — happy path

```text
authorize -> APPROVED
capture -> CAPTURED
clear -> CLEARED
settle -> SETTLED
ledger -> one settlement posting
```

## Scenario B — ambiguous authorization

```text
Visa synthetic simulator processes authorization
-> response is lost
-> local AUTH_UNKNOWN
-> issuer inquiry returns AUTH_APPROVED
-> reconciliation resolves
-> no second authorization
```

## Scenario C — financial duplication attempt

Repeat authorization/capture/refund requests with the same business intent and verify canonical effects are returned rather than duplicate postings.

## Scenario D — region loss

Execute `Kind Multi-Cluster Resilience`:
- API healthy in A/B;
- delete region A;
- prove B remains healthy;
- kill B application pod;
- prove Kubernetes recovery.

## Talking points

- correctness before scale;
- state-plane separation;
- no blind financial retry;
- Outbox/Inbox;
- reconciliation as a first-class capability;
- Java platform SDK;
- OpenShift target / Kind local evidence;
- public Estreem technology signals without proprietary claims.
