# HA / PRA Target Architecture

**Status:** REFERENCE_DESIGN  
**Runtime evidence:** local Kind scope only unless explicitly promoted

## Logical target

```text
                    Global entry / traffic policy
                         /                 \
                        v                   v
                 Payment Region A     Payment Region B
                 OpenShift/K8s        OpenShift/K8s
                    |     |              |      |
                  APIs  Kafka-A        APIs   Kafka-B
                    |     |              |      |
                  DB-A  events         DB-B   events
                         \              /
                          reconciliation
```

## Design choices to validate in a real environment

- active/active stateless API layer where safe;
- explicit single-writer or conflict-safe design for authoritative financial state;
- controlled PostgreSQL promotion rather than assumed multi-master;
- Kafka replication strategy selected per RPO/RTO and failure domain;
- deterministic traffic failover;
- reconciliation after recovery;
- no blind financial replay.

## RTO/RPO

No contractual numbers are claimed.

A mission-specific architecture must derive:
- service criticality;
- RTO;
- RPO;
- maximum tolerable disruption;
- dependency recovery order;
- degraded modes;
- evidence required for acceptance.

## Local Kind evidence

The I15 workflow tests cluster separation, region loss and surviving-region pod recovery on one CI host.

This is not proof of independent datacenter failure domains.
