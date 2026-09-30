# ATL105 All-Element Validation

Status: **PARTIAL_IMPLEMENTATION_NOT_CERTIFIED**. This is source-grounded knowledge and deterministic Java validation, not model fine-tuning or evidence of LLM accuracy.

## Inventory and BR Reuse

- [All-element inventory](../../../../test-output/test-solution-independent-review/all-element-inventory.json) preserves 232 Chapter 13 definition occurrences for 231 distinct element IDs. Element 118 has two definitions; wrapped names are retained.
- [Element-by-element summary](../../../../test-output/test-solution-independent-review/all-element-inventory.md) lists source names, catalog references, BR references and active template field occurrences.
- Each element keeps its raw source text and line, existing catalog rules and BR locations, contextual template usages and legacy field rules. Multiple exports of a BR remain separate references, not separate coverage credits.
- Element-ID matching is a reuse-candidate search only. Compare the actual source section, segment, message family, conditions and meaning before reusing a BR. A rule about Element 239 in Segment 110 must not become a rule about Element 239 in Segment 145.
- Missing explicit element anchors are unresolved references, not proof that no corresponding BR exists. Do not create duplicate BRs merely because an automated lookup found none. Review unanchored and prose BRs as well.
- The inventory excludes immutable AI runs and AI reconciliation/decision queues from BR authority. Extracted candidate segment lists are not active message membership.

## Implementation Plan

| Batch | Work | Current evidence |
|---|---|---|
| 1 | Reconcile all element identities with the spec; preserve reused IDs | 231 IDs / 232 definitions inventoried and tested |
| 2 | Locate existing BRs, source anchors and template uses before proposing BRs | Automated candidate references recorded for every element; semantic comparison remains pending |
| 3 | Add exact message/segment/context profiles for fixed values, length, charset, ranges and enumeration | Chapter 13 type/length baseline for 220 of 231 elements; 67 profiles for Elements 30, 55, 63, 84, 85 and 86 |
| 4 | Validate request type, card/network, entry mode, protocol, lifecycle, companion and subtable conditions | Implemented: CAPK protocol, Element 63 count = segments supplied, Element 85 = declared segment, required Section 11 segments. Remaining combinations pending |
| 5 | Derive positive, negative, boundary and missing-context TS/TC/TD from reviewed BRs | Baseline mutation test covers every extracted element; full formal chains pending |
| 6 | Apply profiles to independently mapped AI fields and compare complete BR/TS/TC/TD/mapping evidence | Run2 intake adapter validates 22,789 AI payloads read-only |
| 7 | Report coverage and resolve source/SME issues before certification | Per-element stage dashboard and review queue (130 elements); no certification claim |

The proposed expansion order is message identity/count/framing (55/63/84/85), response/lifecycle fields (83/86/5/6/26/30), transaction/card/entry-mode fields, amounts/product data, customer/merchant fields, then family-specific fields and Appendix I/K/V/W subtables. For every batch, source and existing BR comparison precede enforcement. Do not assume a code or length applies globally.

## Validation Contract

[Profiles](validation-profiles.json) use existing rule IDs. `Atl105ElementValueValidator` verifies the quoted source evidence occurs inside the specified element definition and that the referenced catalog rule has the same element and specification version. These mechanical gates detect drift, but do not prove the profile's business interpretation or all conditions are correct.

The caller supplies independently normalized field values, not unverified AI-provided applicability claims:

```json
{
  "specificationVersion": "2026-3",
  "messageFamily": "CA Public Key File Load Request",
  "segment": "132",
  "qualifiers": {"protocol": "MULTITHREADED_DIAL"},
  "elements": {"86": "100000"}
}
```

Exact family and segment identity is intentional. No fuzzy field-name matching or automatic fallback to global rules is performed. Values must remain strings to preserve zero padding. Call once for each independently identified segment occurrence; `MESSAGE` identifies fields outside a data segment. This API checks submitted fields and the required fields in matching profiles, not completeness of the entire message.

- `CHECKS_PASSED`: matched profiles passed for submitted scope only. Not execution readiness or business approval.
- `INVALID`: at least one verified contextual constraint failed. Findings omit sensitive values.
- `REVIEW_REQUIRED`: missing/unknown context, unsupported element/combination, absent profiles or source identity uncertainty. Never count as passed coverage.

Initial checks reuse `SEG100-R-013` (Element 55 fixed ATL105), `SEG100-R-019` (Element 86 six-digit shape only) and `SEG132-R-005` (Element 86 range 100000-199999 in the CAPK multithreaded dial context). General sequence range/lifecycle validation and other contexts are not established by the shape-only profile.

### Chapter 13 Baseline

The inventory extracts character type, length label, maximum length and fixed/variable representation from each definition's own `Character Type` line. It accepts `Maximum`, `Max.`, `Fixed` and `Variable` length labels. A label contradicting the representation is `CONFLICT`. Multiple lengths, B64, direction-dependent lengths and repeated definitions stay `REVIEW_REQUIRED` (currently Elements 2, 33, 43, 51, 94, 118, 183, 184, 202, 203 and 243).

For a submitted element without a matching profile, the validator reports `INVALID` only if it exceeds the maximum length or contains a non-digit in a type `N` element. Shorter-than-fixed values, empty values and all passing baselines stay `REVIEW_REQUIRED`. AN/A character sets are not enforced because Chapter 13 AN values include special characters.

### Profiles and Combinations

- `Atl105ElementProfileGenerator` derives 19 Element 84 profiles from the Element 84 range table. It uses 4 digits only for the seven listed segments, including 103, 114, 115, 118, 120, 130 and 131. It also derives 38 Element 85 profiles, where value equals the declared segment, from existing catalog rules. Conflicts go to `generationReview` rather than profiles. Element 84 conflicts are 116, 132 and 134 (two-digit table ranges), 119 (`SEG119-R-038` conflict) and 157 (no rule). Element 85 conflicts are 142, 156 and 157 (no fixed-value rule). A test fails if the stored profiles drift from regenerated ones.
- `messageFamily: "ANY"` is allowed only with `familyIndependentEvidence` quoted from the element definition.
- `equalsObservation` checks combinations. For example, Element 63 must equal `qualifiers.dataSegmentCount`, and a missing count is `REVIEW_REQUIRED`. Element 63 profiles cover Financial, EMV Financial, Totals, Loyalty and ECA requests. Element 30 profiles cover the Chapter 13 code domain for E-Mail and PDL responses.
- For `MESSAGE` observations with `segmentsPresent`, a missing `R`/`PRESENT` Section 11 segment is `INVALID`. An unlisted segment or unknown family is `REVIEW_REQUIRED`.
- Observations are complete by default: a missing required profile element is `INVALID`. `"completeness": "PARTIAL"` changes that to `REVIEW_REQUIRED`, never to a pass.

## AI Run Intake

`Atl105AiElementIntake` reads qe-shaped AI `*.meta.json` and payload pairs without modifying them. It maps fields in this order:

1. Contract alias crosswalks, rejected when the AI name uniquely matches a different Chapter 13 name.
2. A unique Chapter 13 name that is also anchored in the same segment's rule catalog.
3. Otherwise, the field is unmapped and `REVIEW_REQUIRED`.

[ai-intake-crosswalk.json](ai-intake-crosswalk.json) maps only the payload root and envelope fields. A metadata response family on a request payload is `INVALID`. A non-Section-11 family label falls back to the payload-root family with `REVIEW_REQUIRED`.

Run2 result, from [run2-ai-element-validation.json](../../../../test-output/test-solution-independent-review/run2-ai-element-validation.json), covers 22,789 messages:

- 17,682 are `INVALID` and 5,107 are `REVIEW_REQUIRED`. None are fully passed.
- 14,977 of 19,633 non-negative AI scenarios are `INVALID` (AI defect candidates). Dominant causes are request fragments without required Segment 100/108/110/119/118, Element 63 not matching the segments supplied, four-digit Segment 101 lengths and response-family labels on request payloads.
- For 3,156 AI negative scenarios, 2,705 are `INVALID`, but only 624 are `INVALID` on the element the AI declared violated. Most detections come from structural defects rather than the injected violation, so this is not evidence of per-element negative-test quality.
- The Segment 111 contract alias maps AI `VariableInformation` to Element 112. Chapter 13 names Element 112 *Variable Information Length* and Element 113 *Variable Information*. The alias is rejected at intake (10,291 occurrences). The Segment 111 catalog has no Element 113 rule, which is a BR gap for SME review; no BR was created.

## Repeatable Commands

Run from the Java module with its configured Java/Maven environment:

```powershell
mvn '-Dtest=Atl105ElementInventoryTest,Atl105ElementValueValidatorTest,Atl105AiElementIntakeTest' test
mvn exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.Atl105ElementProfileGenerator' '-Dexec.args=specifications/ATL105'
mvn exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.Atl105ElementInventory' '-Dexec.args=specifications/ATL105'
mvn exec:java '-Dexec.mainClass=com.coreauth.validator.validation.Atl105ElementValueValidator' '-Dexec.args=specifications/ATL105 observation.json element-validation-report.json'
mvn exec:java '-Dexec.mainClass=com.coreauth.validator.validation.Atl105AiElementIntake' '-Dexec.args=specifications/ATL105 <qe_shaped_test_data-folder>'
```

Run the generator before the inventory, and the inventory before the intake. The inventory also writes the per-element stage dashboard (`stages`) and `reviewQueue`. Code/test counts are literal rule-ID references only. Generators write derived output only and never change BRs or AI inputs. The observation CLI returns nonzero for INVALID and REVIEW_REQUIRED. Existing family, wire, correlation and provenance validators remain necessary.

## Confidence

High confidence in the measured source inventory and regression-tested initial checks; no quantified confidence in all ATL105 behavior or LLM accuracy. Such confidence requires an independently reviewed held-out corpus, mutation detection results, false-positive/false-negative measurement and complete evidence chains. All other elements and combinations remain open development/review work, not implicitly correct.