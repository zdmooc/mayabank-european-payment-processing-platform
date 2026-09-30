# Kind Multi-Cluster Resilience

**Iteration:** I15  
**Target claim:** RUNTIME_PROVEN — CI LOCAL SYNTHETIC MULTI_CLUSTER

Topology used by CI:

```text
pay-mgmt
pay-region-a
pay-region-b
```

Each CI cluster intentionally uses one Kind control-plane node to fit GitHub runner capacity.

The workstation reference topology may later use more workers where resources allow.

## Runtime scenarios

1. create three independent Kubernetes clusters;
2. build the payment-processing API image;
3. deploy the API to region A and B;
4. prove region B health;
5. delete region A entirely;
6. prove region B remains healthy;
7. delete the surviving API pod;
8. prove Deployment recreates it.

## Boundary

This proves local Kubernetes control-plane separation inside one CI host.

It does **not** prove:
- independent datacenters;
- independent power/network failure domains;
- multi-AZ cloud behavior;
- Kafka cross-cluster replication;
- PostgreSQL cross-region failover;
- contractual RTO/RPO.
