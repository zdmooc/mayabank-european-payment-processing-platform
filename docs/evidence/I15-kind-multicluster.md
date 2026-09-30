# I15 Evidence — Kind Multi-Cluster / Chaos

**Status:** RUNTIME_PROVEN — CI LOCAL SYNTHETIC MULTI_CLUSTER

Expected automated evidence:
- 3 Kind clusters created;
- API deployed in region A and B;
- region B health = UP before failure;
- region A cluster deleted;
- region B remains UP;
- region B application pod deleted and recovered.

On successful workflow run, claim may be promoted to:

`RUNTIME_PROVEN — CI LOCAL SYNTHETIC MULTI_CLUSTER`

No stronger HA/PRA claim is permitted.


## Executed evidence

- GitHub Actions workflow: `Kind Multi-Cluster Resilience`
- Run: `36685357031`
- Result: **SUCCESS**
- management + region A + region B clusters created
- API deployed to both payment regions
- region B health proved before failure
- region A cluster deleted
- region B remained healthy
- surviving region application pod deleted and recovered

Claim: `RUNTIME_PROVEN — CI LOCAL SYNTHETIC MULTI_CLUSTER`.

Boundary unchanged: this is one CI host, not independent datacenters or cloud AZs.
