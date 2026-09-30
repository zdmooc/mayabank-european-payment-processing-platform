# Ledger

**Iteration:** I06  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

The lab uses a simplified immutable double-entry model.

## Capture movement

```text
DEBIT  CARDHOLDER_RECEIVABLE
CREDIT MERCHANT_PAYABLE
```

## Settlement movement

```text
DEBIT  MERCHANT_PAYABLE
CREDIT CASH
```

Every movement must net to zero.

Financial references such as `CAPTURE:<captureId>` and `SETTLEMENT:<settlementId>` are idempotent keys: replay returns the canonical movement instead of posting again.

This is an educational ledger model, not a bank general ledger.
