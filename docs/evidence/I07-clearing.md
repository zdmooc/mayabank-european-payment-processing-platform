# I07 Evidence — Clearing

**Status:** IMPLEMENTED / CI_VALIDATION_PENDING

## Assertions

- only CAPTURED movement is clearing-eligible;
- one capture creates one clearing position;
- success produces CLEARED;
- failure produces CLEARING_EXCEPTION;
- clearing state remains distinct from settlement state.

## Boundary

No external scheme clearing network or production batch/file exchange is claimed.
