# C4 Context — European Card Payment Processing Reference Platform

**Status:** REFERENCE_ARCHITECTURE

## Purpose

Model the external actors and trust boundaries of an independent synthetic card payment processor.

```text
Cardholder
   |
Merchant / POS / E-commerce
   |
   v
MayaBank European Payment Processing Platform
   |
   +--> Acquirer
   +--> Synthetic Card Schemes
   +--> Issuer
   +--> Merchant Reporting
   +--> Operations / Reconciliation
```

## External actors

- **Cardholder** — initiates a payment.
- **Merchant** — creates the commercial payment intent.
- **Acquirer** — represents the merchant-side financial institution.
- **Issuer** — represents the cardholder-side financial institution.
- **Synthetic Scheme** — provider-neutral CB/Visa/Mastercard-style simulator.
- **Operations** — reconciliation, investigation, dispute and audit users.
- **Platform Operator** — Kubernetes/OpenShift/GitOps operational role.

## Explicit non-goals

- no reproduction of Estreem internal architecture;
- no proprietary CB/Visa/Mastercard protocols;
- no real PANs or payment-network credentials;
- no claim of PCI-DSS certification;
- no production HA claim from local Kind/CRC experiments.

## Companion rail

Wero remains a separate A2A/SCT Inst rail and is referenced through companion repositories, never modeled as a card scheme.
