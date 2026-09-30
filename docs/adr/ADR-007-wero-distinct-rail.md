# ADR-007 — Wero remains a distinct rail

**Status:** Accepted

## Decision

Wero/EPI/SCT Inst is modeled outside the card-scheme path.

## Rationale

Wero is an account-to-account instant-payment rail, not a CB/Visa/Mastercard card scheme.

## Consequence

A future orchestration facade may route both card and Wero journeys, but their state machines and external rails remain distinct.
