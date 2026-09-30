# I01 Evidence — Card Domain Model + Platform SDK Baseline

**Status:** IMPLEMENTED / JAVA CI PASS  
**Date:** 2026-09-30

## Implemented

- Java 21 Maven multi-module foundation;
- Payment Platform SDK baseline:
  - correlation context;
  - idempotency key;
  - event envelope;
  - standardized platform error;
- Card domain:
  - Scheme;
  - SyntheticCardToken;
  - Cardholder;
  - Merchant;
  - CardPayment;
  - AuthorizationRequest/Result;
  - Authorization/Capture/Clearing/Settlement states;
  - issuer/scheme ports;
  - Capture aggregate.

## Safety assertions

- raw PAN-like values are rejected by the synthetic-token boundary;
- payment amount must be positive;
- capture lifecycle is independent from authorization lifecycle;
- Wero/SCT Inst domain is not imported into the card model.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.

## Claim boundary

I01 is code/domain evidence only. It does not prove a payment network, database, Kafka, OpenShift, multi-cluster or external scheme runtime.
