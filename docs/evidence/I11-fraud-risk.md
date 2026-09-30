# I11 Evidence — Fraud / Risk

**Status:** IMPLEMENTED / JAVA CI PASS — SYNTHETIC RULES

## Assertions

- normal synthetic payment approves;
- blocked synthetic token declines;
- high amount triggers review;
- velocity threshold triggers review;
- fraud state is separate from issuer authorization state.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.

## Boundary

Rules are synthetic educational controls, not a production fraud engine or scheme/bank fraud policy.
