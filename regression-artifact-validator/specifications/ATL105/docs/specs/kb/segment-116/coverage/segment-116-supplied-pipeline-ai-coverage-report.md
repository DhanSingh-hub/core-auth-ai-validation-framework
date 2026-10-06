# Segment 116 Coverage Report: Supplied AI Requirement Catalog vs Test Solution

**AI requirement source:** `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`
**Segment filter:** `segment_number == "116"` OR `related_entity_ids` contains `ENT-SEG-116` (15 requirements; scope label `DIRECT_SEGMENT_116`)
**Independent Test Solution oracle:** `segment-116-rule-catalog.json` (`SEG116-R-001` through `SEG116-R-009`)
**Report date:** 2026-09-26

## AI Artifact Inventory

| Artifact | Count / status |
|---|---:|
| Segment 116 AI requirements (direct scope) | 15 |
| Business-rule requirements (`BR-*`) | 6 |
| Field-level/dependency requirements (`ENT-*`, `REL-*`) | 9 |
| AI test-case/scenario catalog for Segment 116 | Not supplied in this run; execution coverage is not asserted in this report |

This is a much smaller AI evidence set than Segment 110's 94 requirements, consistent with Segment 116's own specification sections being stubs — there is simply less source text for either the Test Solution or the AI pipeline to derive requirements from.

## Coverage Result

| Measure | Result |
|---|---:|
| Canonical Segment 116 rules | 9 |
| Rules with at least some AI evidence | 8 / 9 (88.9%) |
| Rules directly supported by AI evidence without an unresolved policy | 4 / 9 (44.4%) |
| Rules partially supported by AI evidence | 2 / 9 (22.2%) |
| Rules requiring SME/TBA review | 2 / 9 (22.2%) |
| Rules with no matching AI evidence | 1 / 9 (11.1%) |

## Rule-by-Rule Crosswalk

| Rule | AI requirement evidence | Status | Assessment |
|---|---|---|---|
| `SEG116-R-001` | `BR-385-1`, `BR-388-7` | `COVERED_FOR_BR_MAPPING` | Purpose (Key/Key ID Load) and placement (follows Number of Segments) are directly derived. |
| `SEG116-R-002` | `BR-400-15` | `COVERED_FOR_BR_MAPPING` | Length range 01-50 is directly derived. |
| `SEG116-R-003` | `BR-401-1` | `COVERED_FOR_BR_MAPPING` | Segment Type requiredness is directly derived. |
| `SEG116-R-004` | None | `MISSING` | AI bucket has no requirement stating that Segment Length (field 2) exists for Segment 116 specifically; the Test Solution's own claim here is pattern-derived, not independently confirmed by either source. |
| `SEG116-R-005` | `BR-385-1` | `PARTIALLY_COVERED` | The Data Section 1 -> Segment 116 sequence is derived; the exclusion of Segment 100 and Data Section 3 is not addressed by any AI requirement (`SEG116-SME-002`). |
| `SEG116-R-006` | `BR-402-5`, `REL-ENT-ELEM-86-ENT-SEG-116` | `REVIEW_REQUIRED` | The AI catalog suggests Sequence Number (Element 86) may be included in Segment 116 (80% confidence), but this derives from Element 86's generic "included on all transaction types" statement, not a Segment 116-specific table. This is a useful lead for whoever obtains the external TransArmor document, not a confirmed field. |
| `SEG116-R-007` | `BR-453-1`, `ENT-ELEM-155`, `ENT-ELEM-156`, `ENT-ELEM-157` | `PARTIALLY_COVERED` | All three response field definitions (Key ID, Key Data Length, Key Data) are directly derived; whether they sit inside a Segment 116 response container remains unconfirmed (`SEG116-SME-003`). |
| `SEG116-R-008` | `ENT-ELEM-ADDL-TRANSARMOR-DATA`, `ENT-ELEM-SUBTABLE-KSN`, `ENT-ELEM-SUBTABLE-DEVICETYPE` | `COVERED_FOR_BR_MAPPING` | The Segment 111 companion sub-table (052) and its two sub-tables (KSN, Device Type) are directly derived, matching the independent Appendix I-53/54 verification. |
| `SEG116-R-009` | `ENT-SEG-116` (confidence 34%) | `REVIEW_REQUIRED` | The AI catalog itself assigns only 34% confidence to "The Totals with Proprietary Data Load Request transaction includes TransArmor Load Data Segment (Data Segment No. 116)," which corroborates the source conflict rather than resolving it. The Test Solution resolves this independently using the Element 85 valid-codes table and Section 12.17 (the correct segment is 119), not the AI evidence. |

## AI Requirements Outside the Current Canonical Rule Scope

| AI requirement | Topic | Disposition |
|---|---|---|
| `REQ-SRC-ATL105-PDF-001:607` (`BR-103-7`) | "TransArmor-Verifone Edition processing is exempt from full card-read data storage restriction." | A card-data-storage compliance policy note, not a Segment 116 structural/field rule; out of scope for this catalog. |

## Required Remediation

1. **Obtain the external TransArmor specification-updates document.** This is the primary remediation for nearly every gap in this report, not just an AI-coverage gap (`SEG116-SME-001`).
2. Independently verify (once the external document is available) whether Sequence Number (Element 86) is truly field 3 of Segment 116, as the AI evidence in `SEG116-R-006` suggests but does not confirm.
3. Confirm the TransArmor Load Response's container structure (`SEG116-SME-003`) and the request envelope's exclusion of Segment 100/Data Section 3 (`SEG116-SME-002`).
4. Confirm the relationship between the Segment 111 companion sub-table and the Segment 116 flow (`SEG116-SME-004`).
5. Classify `BR-103-7` and either fold it into a future compliance-scoped rule or record it as explicitly excluded.

## Verdict

The supplied AI requirement catalog provides good coverage of the **facts that are actually available** in the base ATL105 extract for Segment 116 (`8/9` rules with some evidence), and its low confidence score on the Segment 116-vs-119 label (34%) usefully corroborates an independently-found source conflict. However, Segment 116 is **not ready for certification at any meaningful depth** because its own field-by-field layout is absent from both the base specification and the AI evidence; the AI pipeline appears to have drawn from the same incomplete base document, not the external TransArmor supplement.
