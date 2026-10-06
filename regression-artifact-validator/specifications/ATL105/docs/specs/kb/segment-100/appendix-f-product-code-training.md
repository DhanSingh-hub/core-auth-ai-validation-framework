# Appendix F Product Code Training

## Purpose

Train the Test Solution to identify and validate ATL105 Product Code values without treating Appendix F as a single undifferentiated allowlist. The authoritative source is ATL105 2026-3 Appendix F, with Element 77 and Section 12.3 supplying field and transaction-context rules.

## Model Decision Sequence

1. Identify the message role. Appendix F codes are for Element 77 in Segment 102 Product Code Data or Segment 157 Adjusted Product Code Data. Section 12.3 says Segment 102 is unavailable for Comdata and mutually exclusive with Segment 157. Do not apply the Product Code oracle to unrelated code fields or message families.
2. Validate the field representation. Ordinary Element 77 values are fixed-width, three-digit numeric strings. Preserve leading zeroes. Do not coerce `001` to `1`.
3. Resolve the exact code against the detailed Appendix F table, not just its broad numeric range. Return both the assigned description and its class: product/service, fuel, explicitly undefined table entry, unused, reserved, debit surcharge, FSA/HRA, negative, or administrative.
4. Apply code-specific and transaction-context rules. A table entry does not by itself prove that a merchant's Dynamic Card Table permits the code or that the code is appropriate for the transaction role. When a Dynamic Card Table is used, only codes defined in that table are valid for transaction processing.
5. Apply Segment 102 composition rules separately: fuel products first; EV fuel first where applicable; primary fuel first for multi-fuel OTR purchases; no more than ten products; unique Product Code per fuel type; product amounts reconcile to Segment 100 fuel, nonfuel, tax, and cash amounts; and included tax-product amounts reconcile to Element 99. Do not turn these into Product Code membership rules.
6. Emit a source-backed result: `PASS`, `FAIL`, or `REVIEW_REQUIRED`, with the code, table/class, source anchor, transaction context, and reason. If the source does not define acceptance or rejection for a reserved/undefined code in its context, use `REVIEW_REQUIRED`; do not invent a rejection rule.

## Appendix F Table Classes

| Code(s) | Source classification | Training treatment |
|---|---|---|
| `000` | Unused | Never label as an assigned product. The specification says “Not used”; do not infer other processing behavior. |
| `001-099` | Motor fuel | Keep individual assigned codes and the explicitly undefined `071-098` distinct. Enforce fuel uniqueness when multiple fuel types are present. |
| `100-149` | Automotive product/service | Use the detailed descriptions; preserve `143-148` as undefined part/service entries. |
| `150-174` | Aviation fuel | Keep assigned codes, undefined `156-173`, and miscellaneous `174` distinct. |
| `175-224` | Aviation product/service | Keep assigned and undefined `214-223` entries distinct. |
| `225-249` | Marine fuel | Keep assigned codes, undefined `231-248`, and miscellaneous `249` distinct. |
| `250-299` | Marine product/service | Keep assigned and undefined `255-298` entries distinct. |
| `300-399` | Other fuel | Keep assigned codes and undefined `325-398` distinct. EV charging codes `308-310` are in this table. |
| `400-599` | Merchandise | Keep assigned descriptions and each explicitly undefined subrange distinct. |
| `600-799` | Reserved-future-use table | Do not classify this whole range as reserved: `600-605`, `624`, and `650-657` have listed descriptions. Treat specifically reserved subranges as reservation states, not ordinary product assignments. |
| `800–809` | Debit surcharge table | These entries have mixed administrative, merchandise, motor-fuel, and negative-transaction descriptions. Preserve the per-code role. Code `800` is expressly applicable to both credit and debit transactions. |
| `810–889` | Reserved proprietary use | Preserve as reserved; do not claim universal transaction acceptance or rejection absent a merchant/processor rule. |
| `890-896` | FSA/HRA table in detailed table | The Appendix F overview conflicts with the detailed table: the overview says FSA/HRA `890-894` and reserved proprietary `895-899`, while the detailed table lists `890-896` and the next reserved table says `897-899`. Keep this conflict `REVIEW_REQUIRED`; do not silently normalize it. |
| `900-949` | Negative | Preserve the per-code negative description and category. Appendix F says discounts/coupons apply first to nonfuel amounts; do not infer additional discount math. |
| `950-999` | Administrative | Preserve the per-code tax, cash-back, fee, aviation-tax, undefined, or miscellaneous description. Do not treat these as ordinary merchandise. |

“Undefined” is a source description for a listed table entry, not permission to invent the underlying product type. “Reserved” is not synonymous with “unknown numeric code.” A specific code may be listed in a table whose heading says reserved while the row itself assigns a description; use the row-level evidence and retain any unresolved source conflict.

## Important Exceptions and Boundaries

- Element 77's normal three-digit numeric representation applies to transaction product data. Section 13.2 Element 77 separately permits `*` in positions two or three for the Product Code pattern in a Proprietary Load Response (Prompt 904). This is a host-discount pattern exception, not an Appendix F transaction product code and not a general relaxation of the numeric format.
- Appendix F calls its table entries valid Element 77 values, but Section 13.2 says Dynamic Card Table users may process only codes defined in that table. A source-table match is therefore not proof of merchant configuration or production enablement.
- Segment 102 ordering, maximum-count, amount reconciliation, tax reporting, fuel-merchant completeness, and multi-fuel rules come from Section 12.3. The Appendix F code table alone does not establish those behaviors.
- A distinct Product Code is required for each type of fuel purchase. The model must not reject repeated nonfuel codes based on this rule.
- Code `800`'s explicit credit/debit applicability must not be generalized to other surcharge or administrative codes.

## Test Solution Training Method

Build the training/evaluation set in layers rather than asking the model to memorize a prose list:

1. **Source oracle:** store each individually assigned code and each explicitly described range with its exact source description, class, table heading, and citation. Expand ranges only in executable tests; retain the source range in the oracle for provenance.
2. **Boundary examples:** cover the first/last code and both sides of every listed range boundary, unused `000`, reserved ranges, explicitly assigned exceptions within the 600-799 table, and the Appendix F FSA/HRA conflict. There are no numeric gaps within `000-999`.
3. **Context examples:** pair codes with Segment 102 versus 157, Dynamic Card Table present/absent, fuel versus nonfuel, multi-fuel uniqueness, EV/primary-fuel ordering, tax products, amount reconciliation, and the Prompt 904 wildcard exception. Mark unsupported context combinations for review, not fabricated pass/fail labels.
4. **Independent test data:** every claimed BR -> TS -> TC chain needs a concrete TD with a three-character code and a fully specified expected result. Metadata-only TDs, generated prose, and package counts do not establish coverage.
5. **Negative and abstention set:** include `000`, out-of-width/non-numeric values, reserved codes without configuration evidence, and conflicting FSA/HRA claims. Appendix F's table ranges cover `000-999`; do not invent an unlisted three-digit gap code. Distinguish source-defined failures from cases that must abstain as `REVIEW_REQUIRED`.
6. **Leakage control:** keep source-backed expected labels separate from model-generated rationales. Score exact classification, correct context gating, and correct abstention independently; a plausible explanation cannot override a wrong label.

## Current Coverage and Open Work

The [row-level source oracle](../../../../../../src/main/resources/atl105/appendix-f-product-code-oracle.json) now preserves 380 detailed source rows, their exact descriptions, original ranges and line anchors. Those rows cover every code from `000` through `999` exactly once. Table family, row assignment (`ASSIGNED`, `UNDEFINED`, `RESERVED`, `UNUSED`) and semantic role are separate attributes; no fuel role is inferred solely from a product name in a mixed table.

The [independent chain package](../../../../test-output/test-json/appendix-f-product-code-chain-package.json) contains 381 BRs (380 row BRs plus format), 1,013 scenarios, 1,013 cases and 1,013 concrete TDs. Classification TDs cover all 1,000 codes: 998 classification assertions pass; `895` and `896` retain both conflicting source claims and their BR/TS/TC statuses remain `REVIEW_REQUIRED`. The 13 format TDs cover three well-formed strings and ten malformed inputs, including missing/null, numeric JSON, Unicode digits, trailing whitespace and transaction wildcards.

`AppendixFProductCodeOracle.evaluate(payload)` accepts `mode` (`CLASSIFICATION` or `FORMAT`), `messageRole` (`TRANSACTION_PRODUCT_CODE`) and textual `ProductCode`. Its result always states `transactionEligibility: NOT_EVALUATED`. A classification `PASS` for `000` means its unused classification was preserved; it does not authorize its use as a product. Reserved or undefined classification success likewise grants no processing eligibility. Format checks do not decide membership or source conflicts. Prompt 904 wildcard-pattern handling is outside this harness.

Expected labels are generated from the independently owned source transcription, not from classifier output. Tests compare all committed source rows to the specification, exercise separately authored edge expectations and replay every TD with resolved BR -> TS -> TC -> TD IDs and matching anchors. This is source-derived harness evidence, not SME approval.

The [legacy Appendix F package](../../../../test-output/test-json/appendices/appendix-f-segment-100-coverage.json) retains its five transaction-level BRs and references this separate classification/format evidence. Its statuses alone do not establish executable coverage for fuel uniqueness, ordering or reconciliation. Keep Appendix F `PARTIALLY_COVERED`: resolve or retain the FSA/HRA conflict, record Dynamic Card Table/configuration eligibility separately, and validate the remaining Segment 102 composition and AI-artifact gates.

## Regeneration

Run from the `regression-artifact-validator` module directory:

```powershell
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAppendixFProductCodeCoverage'
mvn '-Dtest=AppendixFProductCodeOracleTest' test
```

The generator rewrites only the source oracle and its dedicated chain package. Any changed source row, gap, overlap or unexpected wrapped reservation text must be reviewed before accepting regenerated evidence.

## Source References

- [ATL105 2026-3 Appendix F and Element 77](../../extracted_text.txt)
- [ATL105 2026-3 Section 12.3 Product Code Data Segment](../../extracted_text.txt)
- [Appendix F coverage package](../../../../test-output/test-json/appendices/appendix-f-segment-100-coverage.json)
- [Source oracle](../../../../../../src/main/resources/atl105/appendix-f-product-code-oracle.json)
- [Classification/format chain package](../../../../test-output/test-json/appendix-f-product-code-chain-package.json)
- [Classifier](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixFProductCodeOracle.java)
- [Source generator](../../../../../../src/main/java/com/coreauth/validator/coverage/GenerateAppendixFProductCodeCoverage.java)
- [Independent oracle and chain tests](../../../../../../src/test/java/com/coreauth/validator/AppendixFProductCodeOracleTest.java)
- [Appendix family gap closure register](appendix-family-gap-closure-register.md)