# I15 Evidence — Kind Multi-Cluster / Chaos

**Status:** IMPLEMENTED / RUNTIME CI PENDING

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
