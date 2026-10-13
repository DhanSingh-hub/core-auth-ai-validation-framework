# Appendix AF: Estimated and Incremental Authorization

**Source:** ATL105 2026-4, Appendix AF-1, PDF page 730. Source PDF SHA-256 is recorded in `appendix-af-rule-catalog.json`. **Status:** `IN_PROGRESS` / `REVIEW_REQUIRED`. This release overlay does not replace the 2026-3 Appendix AE Visa model.

## Modeled Requirements

The source lists Original Auth (Estimated Auth), Incremental Auth, Partial Reversal (IA Reversal), and Incremental Auth Completion (IA Completion). Discover use is mandatory with no MCC restriction. Visa, Mastercard, and American Express use is strongly recommended alongside their applicable existing lodging and EV fields.

All IA types should include Incremental Auth Data in Segment 111 Table 82; the requirement is mandatory for Discover. That data carries a Transaction ID unique per terminal among open IA purchases. Incremental Auth, reversal, and completion are subsequent transactions and require an Original Auth first. Subsequent transactions carry the Approval Code (DE 39) from the approved original. Completion closes the IA; no further subsequent transaction is approved for that Terminal Transaction ID.

## Test Model Boundary

`appendix-af-segment-100-coverage.json` contains draft BR/TS/TC/TD links and logical test data for these clauses. `appendix-af-logical-flows.json` provides replayable observations for Discover presence, non-Discover recommendation handling, transaction-ID collisions, predecessor order, approval-code carry-forward, and completion closure.

The validator checks only logical observations. It does not infer Table 82 byte layout, ID syntax, MCC eligibility, approval outcomes, network host behavior, or an exhaustive open-purchase state. Any hard failure or clean logical pass remains review-required; no SME approval, execution readiness, wire validity, or host certification is granted.

## Promotion Gates

1. Confirm source anchors and interpretation with the Test Team/SME.
2. Reconcile Table 82's Appendix I layout, including physical field representation and requiredness.
3. Supply independent state fixtures for per-terminal open-ID uniqueness and post-completion closure.
4. Add approved or independently captured request/response sequences before making execution claims.