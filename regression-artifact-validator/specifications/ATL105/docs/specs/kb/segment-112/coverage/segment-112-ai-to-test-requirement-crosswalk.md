# Segment 112 AI-to-Test Solution Requirement Crosswalk

**Specification:** ATL105 2026-3
**Scope:** Segment 112 only
**Generated from:** `regression-artifact-validator/specifications/ATL105/test-output/test-solution-independent-review/atomic-br-ai-crosswalk.csv` (and its `.json` companion), filtered to rows tagged `segment = "112"`
**Interpretation:** The AI Solution and Test Solution remain independent producers. AI-side atomic extraction is compared against the current Segment 112 Test Solution baseline (the draft [rule catalog](segment-112-rule-catalog.json)); it is not copied into or used to modify the Test Solution.

## Executive Summary

| Metric | Result |
|---|---:|
| AI-side atomic candidates tagged `segment = 112` | 309 |
| AI candidates matched to a current Segment 112 test rule (`testRuleId`) | 59 |
| AI candidates with `NO_EXACT_ATOMIC_MATCH` against the current catalog | 250 |
| Test Solution baseline rules (current draft catalog) | 10 |
| Test rules with at least one confirmed AI match | 3 of 10 (`SEG112-R-007`, `SEG112-R-008`, `SEG112-R-009`) |
| Test rules with zero AI matches | 7 of 10 (`SEG112-R-001` through `SEG112-R-006`, `SEG112-R-010`) |
| Confirmed rule-level baseline coverage | **30% (3/10)** — see caveat below |

## Match Classification

### Confirmed Matches (59 rows)

| Test Rule | Matched AI rows | What they cover |
|---|---:|---|
| `SEG112-R-007` (Additional Information Indicator) | 48 | Element 116 format/enumeration, and per-value documented codes `001`-`047` |
| `SEG112-R-008` (Additional Information Length) | 2 | Element 117 format/enumeration |
| `SEG112-R-009` (Additional Information value) | 9 | Element 118 format, dependency on Element 117, and EMV-context rows (see caveat below) |

All 9 `SEG112-R-009` matches are classified `REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS` in the source crosswalk, not `EXACT_ATOMIC_CANDIDATE` — they are composite/dependency statements rather than single-fact atomic rules, and should not be treated as fully confirmed without SME review.

### Important Caveat: Zero Matches for 7 of 10 Rules Does Not Mean an AI Coverage Gap

`SEG112-R-001` (Segment Type), `SEG112-R-002` (Segment Length), `SEG112-R-003` (999-byte max), `SEG112-R-004` (response-only position), `SEG112-R-005` (host origin), `SEG112-R-006` (990-byte section cap), and `SEG112-R-010` (Element 115 trigger) show **zero** AI-side matches in the current crosswalk. Manual inspection of the AI-side rows for these concepts (see below) shows the AI extraction **did** produce the relevant atomic candidates — they were simply classified `NO_EXACT_ATOMIC_MATCH` because the crosswalk file predates the current 10-rule draft catalog, so no `SEG112-R-00x` identifiers existed yet to match against. Recommend re-running the crosswalk matcher after this documentation pass so these 7 rules can be evaluated on their merits, rather than reporting a misleading "0% AI coverage."

Evidence that the underlying AI candidates exist (sample rows, by concept):

| Concept | Sample AI row IDs | Local AI classification |
|---|---|---|
| Segment Type = 112 | `REQ-SRC-ATL105-PDF-001:3162`, `:3163` | `NO_EXACT_ATOMIC_MATCH` (`ENT-FIELD-112-1`) |
| Segment Length | `REQ-SRC-ATL105-PDF-001:3164` | `NO_EXACT_ATOMIC_MATCH` (`ENT-FIELD-112-2`) |
| 999-byte max length | `REQ-SRC-ATL105-PDF-001:1220`, `:1834` | `NO_EXACT_ATOMIC_MATCH` (`BR-244-1`, `BR-400-11`) |
| Response-only position | `REQ-SRC-ATL105-PDF-001:1221`, `:1733` | `NO_EXACT_ATOMIC_MATCH` (`BR-244-2`, `BR-384-4`) |
| Host origin | `REQ-SRC-ATL105-PDF-001:1222` | `NO_EXACT_ATOMIC_MATCH` (`BR-244-3`) |
| 990-byte section cap | `REQ-SRC-ATL105-PDF-001:1223` | `NO_EXACT_ATOMIC_MATCH` (`BR-245-1`) |
| Element 115 trigger | `REQ-SRC-ATL105-PDF-001:1102`, `:1105`, `:1979`, `:1981`-`:1984` | `NO_EXACT_ATOMIC_MATCH` (`BR-207-2`, `BR-208-1`, `BR-425-1`, `BR-426-*`) |

### Likely Mis-Tagged Rows (Requires SME Confirmation — SEG112-SME-006)

A small number of rows tagged `segment = 112` describe "EMV Additional Information," a concept that textually and structurally belongs to Segment 130 (EMV Request Data Segment, Elements 191/192/118, max 2,000-byte repeating section) and Segment 131 (EMV Response Data Segment, same elements, max 2,800-byte repeating section) — not Segment 112. The likely cause is that Segment 130/131 reuse Element No. 118's name ("Additional Information") for their own EMV-specific field, and Section 13.2's shared element-dictionary entry for Element 118 documents all of its usages (Segment 112, 130, and 131) together, which appears to have caused the AI extraction to tag all Element-118-related text as `segment = 112`.

| AI row ID | Text (excerpt) | Why it looks mis-tagged |
|---|---|---|
| `REQ-SRC-ATL105-PDF-001:1317` | "EMV Additional Information section repeats per EMV Additional Information Indicator, max 2,000 bytes total" | 2,000-byte cap matches Segment 130 (Request), not Segment 112's 990-byte cap |
| `REQ-SRC-ATL105-PDF-001:1318` | "Field Separator following Field No. 9 is included only with the last repetition" | Field No. 9 matches Segment 130's field numbering, not Segment 112's 5-field layout |
| `REQ-SRC-ATL105-PDF-001:1324` | "EMV Additional Information Section repeats... maximum length of 2,800 bytes" | 2,800-byte cap matches Segment 131 (Response) |
| `REQ-SRC-ATL105-PDF-001:2158` | "EMV Additional Information Length must be between 001 and 985" | Ambiguous — could be a transcription of Segment 130/131's Element 192, needs confirmation |

**Recommendation:** exclude these rows from the Segment 112 denominator once SME-confirmed, and file them instead against a future Segment 130/131 training pass. Until confirmed, they remain counted in the 309 total above (not yet subtracted), so the true Segment 112-only denominator may be slightly lower than 309.

### Appendix K Sub-Table Rows (In-Scope but Deferred)

A large share of the remaining `NO_EXACT_ATOMIC_MATCH` rows (`ENT-FIELD-TABLE-K-*` IDs, e.g., Table 001 Balance Information, Table 002 Reserved Indicator, Table 004 CVV Information) are genuinely Segment 112 scope — they describe Appendix K's per-Element-116-code sub-layouts — but are intentionally deferred per [SEG112-SME-004](../segment-112-sme-tba-input-register.md). They are not mis-tagged; they are out-of-scope-for-now.

## Recommended Review Order

1. Confirm and exclude the likely mis-tagged EMV rows (SEG112-SME-006).
2. Re-run the crosswalk matcher against the current 10-rule catalog so `SEG112-R-001` through `SEG112-R-006` and `SEG112-R-010` can be evaluated for real AI matches instead of showing a stale 0%.
3. Resolve the three undocumented Element 116 codes (`014`, `015`, `033`) before treating the `SEG112-R-007` match set as complete (SEG112-SME-002).
4. Decide whether Appendix K sub-table rows should be promoted into the rule catalog for this segment or deferred to a dedicated workstream (SEG112-SME-004).
5. Recalculate confirmed coverage only after SME decisions are recorded — the same principle Segment 100's crosswalk report uses.

## Authority and Data Boundaries

- **AI Solution:** the atomic extraction in `test-solution-independent-review/atomic-br-ai-crosswalk.csv`, currently the only available AI-side evidence for Segment 112 (see [SEG112-SME-005](../segment-112-sme-tba-input-register.md) — a dedicated Segment 112 AI package has not been ingested).
- **Test Solution:** the independently authored draft [rule catalog](segment-112-rule-catalog.json) in this KB.
- **Crosswalk:** the comparison artifact owned by the Test Validation framework (pre-existing, not authored in this documentation pass; this report re-summarizes it scoped to Segment 112).
- **SME review:** required authority for the mis-tagged-row exclusion, the undocumented-code disposition, and the Appendix K scope decision.

No AI-only requirement is automatically added to the Test Solution. No Test-only baseline rule is automatically declared missing from the AI Solution without checking the crosswalk's staleness relative to the current catalog, as documented above.
