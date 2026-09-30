# I10 Evidence — Refund / Chargeback / Dispute

**Status:** IMPLEMENTED / JAVA CI PASS

## Assertions

- only SETTLED payment can be refunded;
- refund amount must be positive and bounded;
- one payment creates at most one refund effect in the current lab model;
- duplicate refund attempts do not double-post ledger;
- reversal and refund remain distinct;
- dispute state is independent from payment settlement state.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.

## Boundary

The chargeback model is a reference case workflow. No proprietary card-scheme dispute rules are reproduced.
