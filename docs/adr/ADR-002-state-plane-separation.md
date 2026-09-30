# ADR-002 — Separate authorization, capture, clearing and settlement state

**Status:** Accepted

## Decision

Persist authorization, capture, clearing and settlement state independently.

## Rationale

A card payment may be authorized but never captured, captured but not yet cleared, or cleared while settlement is pending. Flattening these states hides operational ambiguity.

## Consequence

External views may derive a simplified status, but canonical state history remains intact.
