# Segment 118 Coverage Report: Supplied AI Requirement Catalog vs Test Solution

**AI requirement source:** `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`
**Segment filter:** `segment_number == "118"` OR `related_entity_ids` contains `ENT-SEG-118` (107 requirements; scope label `DIRECT_SEGMENT_118`)
**Independent Test Solution oracle:** `segment-118-rule-catalog.json` (`SEG118-R-001` through `SEG118-R-030`)
**Report date:** 2026-09-26

## AI Artifact Inventory

| Artifact | Count / status |
|---|---:|
| Segment 118 AI requirements (direct scope) | 107 |
| Field-level/dependency requirements (`ENT-*`, `REL-*`) | 45 |
| Business-rule requirements (`BR-*`) | 62 |
| AI test-case/scenario catalog for Segment 118 | Not supplied in this run; execution coverage is not asserted in this report |

This is the largest AI evidence set of any segment trained so far in this workspace (exceeding Segment 110's 94), consistent with Segment 118 being one of the most fully documented segments in the base ATL105 extract.

## Coverage Result

| Measure | Result |
|---|---:|
| Canonical Segment 118 rules | 30 |
| Rules with at least some AI evidence | 26 / 30 (86.7%) |
| Rules directly supported by AI evidence without an unresolved policy | 21 / 30 (70.0%) |
| Rules partially supported by AI evidence | 5 / 30 (16.7%) |
| Rules requiring SME/TBA review | 0 / 30 |
| Rules with no matching AI evidence | 4 / 30 (13.3%) |

## Rule-by-Rule Crosswalk

| Rule | AI requirement evidence | Status | Assessment |
|---|---|---|---|
| `SEG118-R-001` | `BR-251-1`, `BR-251-2`, `ENT-SEG-118` | `COVERED_FOR_BR_MAPPING` | Host-push vs. device-push purpose is directly derived. |
| `SEG118-R-002` | `BR-251-3`, `BR-253-1`, `BR-400-16` | `COVERED_FOR_BR_MAPPING` | Request max length 3,800 is directly derived. |
| `SEG118-R-003` | `BR-251-3`, `BR-253-2` | `COVERED_FOR_BR_MAPPING` | Response max length 3,800 is directly derived. |
| `SEG118-R-004` | None | `MISSING` | AI bucket has no requirement stating device origin. |
| `SEG118-R-005` | `BR-251-4`, `BR-251-5`, `BR-251-6` | `COVERED_FOR_BR_MAPPING` | Request separator and response positional rules are directly derived. |
| `SEG118-R-006` | `BR-251-7`, `ENT-FIELD-118-1` | `COVERED_FOR_BR_MAPPING` | Segment Type fixed `118` is directly derived. |
| `SEG118-R-007` | `BR-399-1`, `ENT-FIELD-118-2` | `COVERED_FOR_BR_MAPPING` | 4-digit Segment Length is directly derived (and cross-confirms it is shared with Segments 103/114/115/120/130/131). |
| `SEG118-R-008` | `ENT-FIELD-118-3` | `COVERED_FOR_BR_MAPPING` | Sequence Number presence is directly derived. |
| `SEG118-R-009` | `ENT-FIELD-118-4` | `PARTIALLY_COVERED` | Requiredness is derived; the Information Byte value catalog is not (`SEG118-SME-001`). |
| `SEG118-R-010` | `ENT-FIELD-118-5` | `COVERED_FOR_BR_MAPPING` | Terminal Identifier presence is directly derived. |
| `SEG118-R-011` | `ENT-FIELD-118-6`, `BR-468-2` | `PARTIALLY_COVERED` | Requiredness and general 4-character special-transaction format are derived; the specific 901-905 value catalog is not independently re-derived by the AI (only Prompt Code Pending's and Card Table Type's catalogs are). |
| `SEG118-R-012` | `ENT-FIELD-118-7`, `BR-252-2`, `ENT-ELEM-182` (value deps for 0901/0902/0904/0981), processing rule | `COVERED_FOR_BR_MAPPING` | Requiredness and the complete 4-value catalog are both directly derived. |
| `SEG118-R-013` | `ENT-FIELD-118-8`, `BR-252-7`, `BR-465-1`, `BR-465-2`, `ENT-ELEM-176` (value dep `99999`), processing rule | `COVERED_FOR_BR_MAPPING` | Presence, 7x5-digit structure, and the `99999` no-card-table sentinel are all directly derived. |
| `SEG118-R-014` | `ENT-FIELD-118-9`, `BR-252-3`, `BR-465-3`, `ENT-ELEM-177`, processing rule | `COVERED_FOR_BR_MAPPING` | Presence and host-echo behavior are directly derived. |
| `SEG118-R-015` | `ENT-FIELD-118-10`, `BR-252-4`, `BR-337-2`, `BR-464-1`, `ENT-ELEM-174` (full 0001-0006 value catalog), processing rule, `REL-ENT-ELEM-174-ENT-ELEM-78` | `COVERED_FOR_BR_MAPPING` | Presence, host-echo behavior, and the complete 6-value catalog are all directly derived, matching the independent Chapter 13 verification. |
| `SEG118-R-016` | `ENT-FIELD-118-11`, `BR-252-5`, `ENT-ELEM-178`, processing rule | `COVERED_FOR_BR_MAPPING` | Presence and host-echo behavior are directly derived. |
| `SEG118-R-017` | `ENT-FIELD-118-12`, `BR-252-6`, `BR-466-1`, `BR-466-2`, `ENT-ELEM-179`, processing rule | `COVERED_FOR_BR_MAPPING` | Presence, CCYYMMDDHHMM format, and the request-vs-response timestamp-meaning distinction are all directly derived. |
| `SEG118-R-018` | `ENT-FIELD-118-13`, `ENT-ELEM-11`, `REL-ENT-ELEM-11-ENT-SEG-118` | `COVERED_FOR_BR_MAPPING` | Conditional presence and host-driven sequencing are directly derived. |
| `SEG118-R-019` | `BR-460-5`, `BR-461-1`, `BR-462-1`, `BR-462-3`, `ENT-ELEM-165/166/168/169/170` | `COVERED_FOR_BR_MAPPING` | All five Custom Receipt Text fields (dates, times, line count, length-prefixed text) are directly derived. |
| `SEG118-R-020` | `BR-464-2`, `ENT-ELEM-175` | `COVERED_FOR_BR_MAPPING` | Card Table Data presence and length-from-segment-length behavior are directly derived. |
| `SEG118-R-021` | `BR-467-1`, `ENT-ELEM-180` | `COVERED_FOR_BR_MAPPING` | Site Configuration Data presence and request-only direction are directly derived. |
| `SEG118-R-022` | `BR-336-1/2/3`, `BR-462-4`, `BR-463-1`, `BR-463-3`, `BR-468-3`, `ENT-ELEM-171/172/173` | `COVERED_FOR_BR_MAPPING` | The Host Discount repeat-block fields (count, dates, card type, BIN range, product code, amount) are directly derived. |
| `SEG118-R-023` | `BR-477-3`, `ENT-ELEM-201` | `COVERED_FOR_BR_MAPPING` | Fuel Volume Data presence and request-only direction are directly derived. |
| `SEG118-R-024` | `BR-196-5`, `BR-197-3` | `COVERED_FOR_BR_MAPPING` | Data Section 3 placement and the explicit Segment 100 exclusion are both directly derived. |
| `SEG118-R-025` | `BR-198-2` | `PARTIALLY_COVERED` | Data Section 3 presence in the response is derived; the specific Data Section 1 positional field layout (Response Code, Download Indicator, Initiation Date/Time, Sequence Number) is not independently re-derived. |
| `SEG118-R-026` | None | `MISSING` | AI bucket has no requirement enumerating the proprietary-load-specific Response Code values (H, O, T, U, X, Y). |
| `SEG118-R-027` | `ENT-ELEM-11` (starts at 1, increments per block) | `PARTIALLY_COVERED` | Block Number start/increment behavior is derived; the "Block Number = 0 is a final, data-less message" convention is not independently re-derived. |
| `SEG118-R-028` | `BR-257-2` | `PARTIALLY_COVERED` | General "device applies host discount data after receiving response" behavior is derived; the specific Response Code (D/E/M/N) trigger linkage is not. |
| `SEG118-R-029` | None | `MISSING` | AI bucket has no requirement for the Segment 102 Product Code 941/991 cross-reference. |
| `SEG118-R-030` | None | `MISSING` | AI bucket has no requirement for the Custom Receipt Text pending Response Codes (V, W). |

## AI Requirements Outside the Current Canonical Rule Scope

| AI requirement | Topic | Disposition |
|---|---|---|
| `REQ-SRC-ATL105-PDF-001:993` (`BR-176-6`, confidence 41%) | "Data Section No. 3 of Totals with Proprietary Data Load Request contains Data Segment No. 116." | This is the **same Segment 116-vs-119 source conflict** already documented in the [Segment 116 KB](../../segment-116/segment-116-vs-119-disambiguation-sme-tba-note.md). The low AI confidence (41%) again corroborates the conflict. It is not evidence about Segment 118 itself and must not be counted toward this segment's coverage. |
| `REQ-SRC-ATL105-PDF-001:1256` (`BR-253-3`) | "Proprietary Load Request/Response Data section corresponds to Element No. 78 (Prompt Code) values." | General framing statement; already reflected across `SEG118-R-019` through `SEG118-R-023`, not a distinct rule. |
| `REQ-SRC-ATL105-PDF-001:2136` (`BR-468-1`) | "Prompt Code, Pending must be a valid Transaction Type and/or Card Type Code." | Overlaps with `SEG118-R-012`; kept as supplemental corroboration, not a separate rule. |

## Required Remediation

1. Add AI requirements (or confirm none exist) for: device origin (`SEG118-R-004`), the proprietary-load Response Code catalog (`SEG118-R-026`), the Segment 102 discount cross-reference (`SEG118-R-029`), and the Custom Receipt Text pending codes (`SEG118-R-030`).
2. Resolve `SEG118-SME-001` (Information Byte values) and `SEG118-SME-002` (Receipt Text Data encoding) before treating `SEG118-R-009`/`SEG118-R-019` as fully covered.
3. Confirm the Block Number = 0 final-message convention's scope (`SEG118-SME-003`) and the Response Code D/E/M/N host-discount trigger linkage.
4. Do not count `BR-176-6` toward Segment 118 coverage; it belongs to the Segment 116/119 disambiguation already resolved elsewhere.

## Verdict

The supplied AI requirement catalog has **strong, comprehensive field-level extraction** for Segment 118 (`26/30` rules with some evidence, `21/30` directly covered without an open policy question) — the best coverage ratio of any segment trained so far. It independently confirmed several details the Test Solution had to verify manually (the `99999` no-card-table sentinel, the full Card Table Type and Prompt-Code-Pending value catalogs) and even flagged the Segment 116/119 conflict at low confidence. It is **not yet ready for Segment 118 certification** because four rules (envelope response-code catalog, device origin, the Segment 102 cross-reference, and the receipt-text pending codes) have no AI evidence, and two SME items (Information Byte values, Receipt Text Data encoding) remain open.
