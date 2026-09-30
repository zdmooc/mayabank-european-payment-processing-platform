# I07 Evidence — Clearing

**Status:** IMPLEMENTED / JAVA CI PASS / API E2E RUNTIME_PROVEN

## Assertions

- only CAPTURED movement is clearing-eligible;
- one capture creates one clearing position;
- success produces CLEARED;
- failure produces CLEARING_EXCEPTION;
- clearing state remains distinct from settlement state.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.
- `CAPTURED -> CLEARED` executed through API E2E run `36686201540` — **SUCCESS**.

## Boundary

No external scheme clearing network or production batch/file exchange is claimed.
