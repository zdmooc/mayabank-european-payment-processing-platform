# Clearing

**Iteration:** I07  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

A captured card payment creates one canonical clearing position.

```text
CAPTURED
  |
  v
CLEARING_PENDING
  |
  +--> CLEARED
  |
  +--> CLEARING_EXCEPTION
```

The current lab uses a synthetic clearing service. Batch/window mechanics and MFT/file exchange remain later concerns.

A replay for the same captureId resolves to the same clearing position.
