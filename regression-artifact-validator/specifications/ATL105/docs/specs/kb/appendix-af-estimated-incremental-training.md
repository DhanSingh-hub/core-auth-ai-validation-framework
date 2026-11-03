# Appendix AF: Estimated and Incremental Authorization

**Source:** ATL105 2026-4, Appendix AF-1, PDF page 730. Source PDF SHA-256 is recorded in `appendix-af-rule-catalog.json`. **Status:** `IN_PROGRESS` / `REVIEW_REQUIRED`. This release overlay does not replace the 2026-3 Appendix AE Visa model.

## Modeled Requirements

The source lists Original Auth (Estimated Auth), Incremental Auth, Partial Reversal (IA Reversal), and Incremental Auth Completion (IA Completion). Discover use is mandatory with no MCC restriction. Visa, Mastercard, and American Express use is strongly recommended alongside their applicable existing lodging and EV fields.

All IA types should include Incremental Auth Data in Segment 111 Table 82; the requirement is mandatory for Discover. Appendix I-87/I-88 specifies Table ID N2 fixed `82`, Table Length N3 fixed `004`, and a Terminal Transaction Number N4. It says that number should be unique for each Original Auth and should retain its value for all subsequent Incremental Auths, Partial or Full Reversals, and IA Completions until the series completes. Appendix AF calls its Table 82 value a Transaction ID. The model keeps that cross-section field-name mapping `REVIEW_REQUIRED` rather than silently assuming identity.

Appendix AF's transaction role is modeled as `IA_REVERSAL`, distinct from the legacy phrase “partial reversal” used for a reversal of a partially approved original authorization. The older rule's scope is not applied to an IA Reversal without an explicit source/SME mapping.

For open-purchase uniqueness, the logical model carries the Appendix AF `transactionId` observation separately from `table82.terminalTransactionNumber`. The validator does not infer that these fields are equivalent. The synthetic base flow includes a complete two-entry open-state list, and its cases cover same-terminal duplicate IDs, cross-terminal reuse, same Table 82 number with a different AF ID, a differing Table 82 number with the same AF ID, and explicitly incomplete history. This closes the local synthetic-state fixture gap; it is not an independently observed terminal ledger or a real transaction uniqueness verdict.

Incremental Auth, IA Reversal, and completion are subsequent transactions and require an Original Auth first. Subsequent transactions carry the Approval Code (DE 39) from the approved original. Completion closes the IA; no further subsequent transaction is approved for that Terminal Transaction ID. The source's “should” wording for same-number continuity is a review warning, not a newly invented hard rejection.

## Test Model Boundary

`appendix-af-segment-100-coverage.json` contains draft BR/TS/TC/TD links and 21 logical test-data records. `appendix-af-logical-flows.json` provides replayable observations for Discover presence, non-Discover recommendation handling, fixed Table 82 layout, AF Transaction ID collisions over a complete synthetic open-state list, continuity review for incremental/reversal/completion roles, predecessor order, approval-code carry-forward, IA Reversal naming, and completion closure.

The validator checks only logical observations. A separate [physical payload candidate](../../../test-output/test-solution-independent-review/appendix-af-physical-wire-candidate-v1.json) serializes a synthetic 2026-3 Financial Transaction Request application payload (110 bytes; Segment 100/111 lengths 081/019) and a synthetic 2026-4 Financial Transaction Response layout (101 bytes, positional, no field separators). The request indicator `082` is source-confirmed: 2026-4 Chapter 13.2 Element 113 explicitly assigns value 082 to Incremental Auth Data, while Appendix I specifies Table ID N2 fixed 82, length N3 fixed 004, and N4 Terminal Transaction Number. The AF Transaction ID is not mapped into that N4 field. The response is a shape fixture only, not a captured host response or expected-outcome oracle. TCP/IP/TPDU framing is excluded. The pair remains review-only, not an approved AF wire profile. Any hard failure or clean local check remains review-required; no SME approval, execution readiness, host outcome, or full wire certification is granted.

## Promotion Gates

1. Confirm source anchors and interpretation with the Test Team/SME.
2. Confirm the Appendix AF Transaction ID and Appendix I Terminal Transaction Number refer to the same Table 82 field.
3. The local synthetic state fixtures now exercise per-terminal uniqueness and post-completion closure. Supply independently observed open-state evidence before making a terminal-specific uniqueness claim.
4. Resolve the AF Transaction ID-to-Table 82 N4 mapping and any transport framing needed by the converter; obtain an approved or independently captured host response before making execution claims. The current request/response application-payload pair is synthetic layout evidence only.