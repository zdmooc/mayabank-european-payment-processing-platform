# I02 Evidence — Authorization Vertical Slice

**Status:** IMPLEMENTED / JAVA CI PASS / API E2E RUNTIME_PROVEN  
**Date:** 2026-09-30

## Scope

Merchant -> Acquirer -> Scheme Router -> Issuer -> Authorization.

## Assertions

- APPROVED is explicit;
- DECLINED is explicit;
- repeated same idempotency key/payment returns the same authorization effect;
- same key for another payment is rejected by the authorization store;
- authorization state is independent from capture/clearing/settlement.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.
- Payment API E2E: run `36686201540` — **SUCCESS**.

## Boundary

The I02 scheme port is an in-process test boundary. It is not a CB/Visa/Mastercard simulator or network.
