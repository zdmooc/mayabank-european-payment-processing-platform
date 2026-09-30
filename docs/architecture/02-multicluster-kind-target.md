# Local Multi-Cluster Target with Kind

**Status:** REFERENCE_DESIGN  
**Target iteration:** I15/I16

## Goal

Provide a low-cost local environment for multi-cluster behavior without claiming datacenter or cloud-region independence.

```text
                 kind-pay-mgmt
              Argo CD / GitOps
                     |
          +----------+----------+
          |                     |
          v                     v
 kind-pay-region-a       kind-pay-region-b
 1 CP + 2 workers        1 CP + 2 workers
          |                     |
   payment workloads      payment workloads
          |                     |
     Kafka A / DB A        Kafka B / DB B
```

## Candidate experiments

- Argo CD multi-cluster registration;
- ApplicationSet deployment;
- region A workload loss;
- controlled traffic switch to B;
- Kafka cross-cluster replication;
- reconciliation after recovery;
- PostgreSQL controlled primary/standby promotion;
- rollback and rejoin.

## Evidence boundary

A local Kind multi-cluster run may be labelled:

`RUNTIME_PROVEN — LOCAL SYNTHETIC MULTI_CLUSTER`

It must never be labelled:

- multi-datacenter HA;
- multi-AZ HA;
- production PRA;
- independent power/network failure domains.

All clusters share the same workstation and Docker host.
