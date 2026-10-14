# Appendix AF: Estimated and Incremental Authorization

**Source:** ATL105 2026-4, Appendix AF-1, PDF page 730. Source PDF SHA-256 is recorded in `appendix-af-rule-catalog.json`. **Status:** `IN_PROGRESS` / `REVIEW_REQUIRED`. This release overlay does not replace the 2026-3 Appendix AE Visa model.

## Modeled Requirements

The source lists Original Auth (Estimated Auth), Incremental Auth, Partial Reversal (IA Reversal), and Incremental Auth Completion (IA Completion). Discover use is mandatory with no MCC restriction. Visa, Mastercard, and American Express use is strongly recommended alongside their applicable existing lodging and EV fields.

All IA types should include Incremental Auth Data in Segment 111 Table 82; the requirement is mandatory for Discover. Appendix I-87/I-88 specifies Table ID N2 fixed `82`, Table Length N3 fixed `004`, and a Terminal Transaction Number N4. It says that number should be unique for each Original Auth and should retain its value for all subsequent Incremental Auths, Partial or Full Reversals, and IA Completions until the series completes. Appendix AF calls its Table 82 value a Transaction ID. The model keeps that cross-section field-name mapping `REVIEW_REQUIRED` rather than silently assuming identity.

Appendix AF's transaction role is modeled as `IA_REVERSAL`, distinct from the legacy phrase “partial reversal” used for a reversal of a partially approved original authorization. The older rule's scope is not applied to an IA Reversal without an explicit source/SME mapping.

Incremental Auth, IA Reversal, and completion are subsequent transactions and require an Original Auth first. Subsequent transactions carry the Approval Code (DE 39) from the approved original. Completion closes the IA; no further subsequent transaction is approved for that Terminal Transaction ID. The source's “should” wording for same-number continuity is a review warning, not a newly invented hard rejection.

## Test Model Boundary

`appendix-af-segment-100-coverage.json` contains draft BR/TS/TC/TD links and logical test data for these clauses. `appendix-af-logical-flows.json` provides replayable observations for Discover presence, non-Discover recommendation handling, fixed Table 82 layout, terminal-number collisions, continuity review for incremental/reversal/completion roles, predecessor order, approval-code carry-forward, IA Reversal naming, and completion closure.

The validator checks only logical observations. It does not infer Table 82 byte layout, ID syntax, MCC eligibility, approval outcomes, network host behavior, or an exhaustive open-purchase state. Any hard failure or clean logical pass remains review-required; no SME approval, execution readiness, wire validity, or host certification is granted.

## Promotion Gates

1. Confirm source anchors and interpretation with the Test Team/SME.
2. Confirm the Appendix AF Transaction ID and Appendix I Terminal Transaction Number refer to the same Table 82 field.
3. Supply independent state fixtures for per-terminal open-series uniqueness and post-completion closure.
4. Add approved or independently captured request/response sequences before making execution claims.