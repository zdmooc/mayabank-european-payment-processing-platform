# Refund / Chargeback / Dispute

**Iteration:** I10  
**Status:** IMPLEMENTED / CI TESTED when Java CI passes

## Distinctions

- **Reversal** cancels/reverses a capture-side movement before the refund lifecycle.
- **Refund** is a new financial movement linked to an already settled payment.
- **Dispute/chargeback** is modeled as a case lifecycle, not a direct status overwrite.

```text
SETTLED -> REFUND -> REFUNDED

OPEN -> UNDER_REVIEW -> CARDHOLDER_WON | MERCHANT_WON -> CLOSED
```

Refund posting is idempotent and uses a dedicated ledger reference.
