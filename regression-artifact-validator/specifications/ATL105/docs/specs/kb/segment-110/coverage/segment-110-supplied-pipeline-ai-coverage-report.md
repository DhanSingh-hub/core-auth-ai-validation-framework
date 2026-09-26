# Segment 110 Coverage Report: Supplied AI Requirement Catalog vs Test Solution

**AI requirement source:** `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`
**Segment filter:** `segment_number == "110"` OR `related_entity_ids` contains `ENT-SEG-110` (94 requirements; scope label `DIRECT_SEGMENT_110`)
**Independent Test Solution oracle:** `segment-110-rule-catalog.json` (`SEG110-R-001` through `SEG110-R-020`)
**Report date:** 2026-09-26

## AI Artifact Inventory

| Artifact | Count / status |
|---|---:|
| Segment 110 AI requirements (direct scope) | 94 |
| Field-level boundary/format/value requirements (`ENT-ELEM-*`, `ENT-FIELD-110-*`) | 51 |
| Business-rule requirements (`BR-*`) | 34 |
| Cross-segment dependency requirements (`REL-*`) | 5 |
| Narrative/format requirements (`ENT-ELEM-MICR-TAC`, `ENT-ELEM-MICR-TOAD`, `ENT-ELEM-ALT-MICR*`) | 4 |
| AI test-case/scenario catalog for Segment 110 | Not supplied in this run; execution coverage is not asserted in this report |

The 94 requirements were filtered directly from the approved requirement catalog using the segment's own declared `segment_number`/`related_entity_ids` fields (scope `DIRECT_SEGMENT_110`), rather than a page-proximity heuristic, to minimize false positives.

## Coverage Result

| Measure | Result |
|---|---:|
| Canonical Segment 110 rules | 20 |
| Rules with at least some AI evidence | 19 / 20 (95.0%) |
| Rules directly supported by AI evidence without an unresolved policy | 9 / 20 (45.0%) |
| Rules partially supported by AI evidence | 4 / 20 (20.0%) |
| Rules requiring SME/TBA review | 6 / 20 (30.0%) |
| Rules with no matching AI evidence | 1 / 20 (5.0%) |

## Rule-by-Rule Crosswalk

| Rule | AI requirement evidence | Status | Assessment |
|---|---|---|---|
| `SEG110-R-001` | `ENT-SEG-110`, `REL-ENT-SEG-110-FINANCIAL_TRANSACTION_REQUEST`, `BR-173-3`, `BR-174-4`, `BR-239-1`, `BR-388-3` | `REVIEW_REQUIRED` | AI derives both the ECA/TeleCheck placement and the broader "any field of Data Section 3" statement without reconciling that the generic Financial Transaction Request segment list omits 110; scope remains `SEG110-SME-001`. |
| `SEG110-R-002` | `BR-239-2`, `BR-400-9` | `COVERED_FOR_BR_MAPPING` | Maximum length 168 is directly derived from two independent AI requirements. |
| `SEG110-R-003` | `BR-239-4` | `COVERED_FOR_BR_MAPPING` | Device origin is directly derived. |
| `SEG110-R-004` | `ENT-FIELD-110-1` (requiredness and fixed-value `110` format) | `COVERED_FOR_BR_MAPPING` | Segment Type presence and fixed value are directly derived. |
| `SEG110-R-005` | `ENT-FIELD-110-2` | `PARTIALLY_COVERED` | Requiredness is derived; the "includes Segment Type and Field Separators" length-calculation rule is not explicit. |
| `SEG110-R-006` | None | `MISSING` | AI bucket has no requirement enumerating the ordered 12-field serialization sequence. |
| `SEG110-R-007` | `BR-239-3` | `COVERED_FOR_BR_MAPPING` | Empty-field separator preservation is directly derived. |
| `SEG110-R-008` | `ENT-ELEM-122`, `BR-343-1`, `BR-434-1`, `BR-434-2`, `ENT-ELEM-137`, `BR-439-1`, `ENT-FIELD-110-3` | `PARTIALLY_COVERED` | Requiredness, 50-byte maximum, and the 50-byte overflow truncation rule are directly derived; the hosting segment for Extended MICR Data (137) is not confirmed (`SEG110-SME-002`). |
| `SEG110-R-009` | `ENT-ELEM-123`, `BR-130-2`, `BR-434-5`, `ENT-FIELD-110-4` | `REVIEW_REQUIRED` | Conditional presence and 40-byte maximum exist; the manually-entered trigger remains `SEG110-SME-003`. |
| `SEG110-R-010` | `ENT-ELEM-124`, `REL-ENT-ELEM-124-ENT-ELEM-123`, `BR-435-1`, `BR-365-5`, `ENT-FIELD-110-5` | `COVERED_FOR_BR_MAPPING` | Presence trigger (tied to Driver's License), fixed 2-byte length, and the full Appendix D value catalog are all directly derived and independently re-confirmed against Appendix D. |
| `SEG110-R-011` | `ENT-ELEM-125`, `BR-130-3`, `BR-365-4`, `ENT-FIELD-110-6` | `REVIEW_REQUIRED` | Conditional presence and MMDDYYYY format exist; the exact trigger (bundled with Driver's License vs. a narrower Certegy-specific rule) remains `SEG110-SME-004`. |
| `SEG110-R-012` | `ENT-ELEM-126`, dependency values `P`/`C`, `BR-435-2`, `BR-435-3`, `ENT-FIELD-110-7` | `COVERED_FOR_BR_MAPPING` | Requiredness, fixed 1-byte length, and the complete `P`/`C` value catalog are directly derived. |
| `SEG110-R-013` | `ENT-ELEM-127`, `BR-436-1`, `BR-241-1`, `ENT-FIELD-110-8` | `REVIEW_REQUIRED` | Conditional presence and 8-byte maximum exist; shares the unresolved manually-keyed trigger with `SEG110-R-009` (`SEG110-SME-003`). |
| `SEG110-R-014` | `ENT-ELEM-128`, `ENT-FIELD-110-9` | `COVERED_FOR_BR_MAPPING` | Optional presence and 10-digit numeric maximum are directly derived. |
| `SEG110-R-015` | `ENT-ELEM-129`, `ENT-FIELD-110-10` | `COVERED_FOR_BR_MAPPING` | Optional presence and 24-byte alphanumeric maximum are directly derived. |
| `SEG110-R-016` | `ENT-ELEM-130`, `ENT-FIELD-110-11` | `COVERED_FOR_BR_MAPPING` | Optional presence and MMDDYYYY format are directly derived. |
| `SEG110-R-017` | `ENT-ELEM-239`, `REL-ENT-ELEM-239-ENT-ELEM-239`, `BR-240-1`, `BR-240-2`, `BR-242-1`, `BR-242-2`, `ENT-ELEM-ALT-MICR`, `ENT-ELEM-ALT-MICR-IND`, `ENT-FIELD-110-12` | `REVIEW_REQUIRED` | The AI evidence itself is internally split: some entries describe "Enhanced Fleet Data" (999 bytes, value `004`) and others describe "Alternate MICR IND" (1 byte, value `Y`) for the same element number. This independently corroborates the `SEG110-SME-005` source conflict rather than resolving it; do not average or merge the two into one requirement. |
| `SEG110-R-018` | `ENT-ELEM-MICR-TAC`, `ENT-ELEM-MICR-TOAD` | `PARTIALLY_COVERED` | Both narrative MICR formats are directly derived; no formal per-character grammar is present, and Appendix I-17 supplies related but distinct format codes (see `SEG110-R-020`) (`SEG110-SME-006`). |
| `SEG110-R-019` | `REL-ENT-ELEM-122-ENT-ELEM-2`, `BR-359-1` | `REVIEW_REQUIRED` | The Segment 100/Segment 110 MICR cross-reference is derived, and `BR-359-1` adds an unverified "23 bytes" figure that the Test Solution has not independently confirmed in Section 12.9 or Chapter 13; treat the byte count as unverified pending `SEG110-SME-007`. |
| `SEG110-R-020` | `BR-559-4`, `BR-559-5`, `BR-559-6`, `BR-559-7` | `PARTIALLY_COVERED` | The AI catalog correctly captured all four MICR Type format codes (`T$`, `18`, `09`, `19`) and their examples, but scoped them as generic Segment 110 "Field" requirements. The Test Solution independently traced them to Appendix I-17 and corrected the scope to Segment 111's Variable Information Indicator `024` sub-table. |

## AI Requirements Outside the Current Canonical Rule Scope

The following AI requirements are source-relevant but exceed the current canonical rule statements; they must remain `REVIEW_REQUIRED`, not silently counted as coverage:

| AI requirement | Topic | Disposition |
|---|---|---|
| `REQ-SRC-ATL105-PDF-001:4011` (`REL-ENT-FIELD-113-9-ENT-ELEM-122`) | Segment 113 field 9 depends on MICR Data (122) | Needs a modeled cross-segment rule once Segment 113's own training reaches this field. |
| `REQ-SRC-ATL105-PDF-001:790` (`BR-131-6`) | Receipt must show Total Check Amount, Response Check Number, Return Fee Amount | Belongs to receipt/response behavior, not the Segment 110 request; out of scope for this catalog. |
| `REQ-SRC-ATL105-PDF-001:793` (`BR-131-9`) | Recommended receipt fields (Customer Telephone, Custom Field, Merchant Trace ID) | Low confidence (48%); same receipt-scope disposition as above. |
| `REQ-SRC-ATL105-PDF-001:1401`, `:1403` (`BR-294-2`, `BR-294-4`) | "Money Code Check Number" required for all Money Code transactions; Table Length max 015 | Not confirmed as part of Segment 110; needs verification of which segment/feature "Money Code" belongs to before any disposition. |

## Required Remediation

1. Add an AI requirement (or confirm none exists) for the ordered 12-field Segment 110 serialization sequence (`SEG110-R-006`).
2. Resolve the Element 239 conflict at the source or via an approved scoping decision before treating `SEG110-R-017` as covered (`SEG110-SME-005`).
3. Confirm the hosting segment for Extended MICR Data (Element 137) and the manually-entered/manually-keyed trigger for Driver's License, Date of Birth, and Check Number (`SEG110-SME-002`, `SEG110-SME-003`, `SEG110-SME-004`).
4. Independently verify the "23 bytes" MICR figure in `BR-359-1` against a primary source page before using it in a validator (`SEG110-SME-007`).
5. Classify the four out-of-scope AI requirements above and either fold them into a future rule or record them as explicitly excluded.

## Verdict

The supplied AI requirement catalog has strong **field-level extraction** for Segment 110 (`19/20` rules with some evidence, `9/20` directly covered without an open policy question), and the independent Test Solution review caught one AI mis-scoping (`SEG110-R-020`, MICR Type codes attributed to the wrong segment) and one internally inconsistent AI element definition (`SEG110-R-017`, element 239). It is **not ready for Segment 110 certification** because one rule has no AI evidence, six rules are explicitly blocked by SME/TBA decisions, and no AI scenario/test-case execution evidence was supplied for this run.
