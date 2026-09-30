# Axway MFT Boundary

**Status:** REFERENCE_BOUNDARY / NOT DEPLOYED

Axway is a public Estreem technology signal and is retained as an MFT target for file-oriented clearing/settlement/reconciliation exchanges.

Synthetic boundary:

```text
Clearing / Settlement
      |
      v
Generic MFT Adapter
      |
      +--> outbound file
      +--> checksum
      +--> ack / reject
      +--> duplicate detection
      +--> missing-file investigation
      +--> controlled replay
```

The POC does not reproduce Axway internals and does not require an Axway runtime to validate the payment-domain model.
