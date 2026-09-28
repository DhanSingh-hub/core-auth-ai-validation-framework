# Segment 130 — AI Solution Business-Requirement Coverage Report

**Report date:** 2026-09-28
**AI pipeline analysed:** `C:\Users\F5A1SS2\Downloads\src 2\src\pipeline`
**Pipeline run:** `SRC-ATL105-PDF-001`, derived `2026-09-18T13:38:02Z`, state `APPROVED`
**Oracle:** [segment-130-rule-catalog.json](coverage/segment-130-rule-catalog.json) (23 authoritative rules)
**Method:** Requirements filtered on `segment_number == "130"`, then traced requirement → scenario → test case and scored against the authoritative rule catalog.

---

## 1. Headline

| Measure | Result |
|---|---:|
| AI requirements for Segment 130 | **53** of 6,487 (0.82%) |
| Scenarios generated | **104** |
| Test cases generated | **243** |
| Requirements with ≥1 scenario | **49 / 53 (92.5%)** |
| Scenarios with ≥1 test case | **104 / 104 (100%)** |
| **Authoritative rules addressed** | **11 / 23 (47.8%)** |
| Test cases on a **legal** message family | **54 / 243 (22.2%)** |
| Test cases the pipeline itself marks uncovered | **240 / 243 (98.8%)** |

> **Bottom line.** The AI pipeline produces high *volume* for Segment 130 (243 test cases from 53 requirements) but covers **fewer than half** the authoritative rules, and **78% of its test cases place Segment 130 in a message family where the specification does not permit it.** Volume is not coverage.

---

## 2. Rule-by-rule coverage (11 / 23)

### ✅ Addressed (11)

| Rule | Class | AI reqs | Assessment |
|---|---|---:|---|
| SEG130-R-001 | applicability | 1 | "only segment required for all EMV financial transactions" — correct |
| SEG130-R-002 | field | 2 | Segment Type fixed `130` — correct |
| SEG130-R-003 | field | 2 | Segment Length — correct |
| SEG130-R-004 | serialization | 2 | **Cites 3,043 — matches our adopted value.** Never cites the conflicting 9,999 |
| SEG130-R-005 | field | 7 | CA Public Key File Checksum — well covered |
| SEG130-R-006 | field | 7 | EMV Card Sequence Number — well covered |
| SEG130-R-007 | field | 7 | EMV Chip Data Length — well covered |
| SEG130-R-008 | field | **14** | EMV Chip Data / TLV — strongest area |
| SEG130-R-013 | serialization | 4 | **Excellent.** All four separator sub-rules captured verbatim |
| SEG130-R-015 | lifecycle | 1 | Checksum echo — present but thin |
| SEG130-R-016 | field | 1 | CA_KEYS record layout (SHA-1 over RID/Index/Modulus/Exponent) |

**Genuine strength:** separator handling. Requirements `:1311`–`:1314` capture the full four-part rule — separators between fixed fields even when unpopulated, none *within* the Additional Information Section, none *between* repetitions, one after the final repetition. That is the hardest part of Segment 130's wire format and the AI got it exactly right.

### ❌ Not addressed (12)

| Rule | Class | Why it matters |
|---|---|---|
| SEG130-R-009 | field | Cross-field agreement with Segment 100 (`9F02`/`9C`/`5F2A`/`9F1A`) — no requirement exists |
| SEG130-R-010 | field | Cryptogram (9F26) scope boundary never stated → risk of fabricated crypto assertions |
| SEG130-R-011 | structure | **2,000-byte cap on the Additional Information Section is absent** |
| SEG130-R-012 | metadata | Element 118 reuse (Seg 112 vs Seg 130) not flagged |
| SEG130-R-014 | metadata | "Originates at the device" not captured |
| SEG130-R-017 | applicability | **EMV data waived on Reversals (§10.14.2.4) — entirely missing** |
| SEG130-R-018 | structure | Five field slots (Nos. 4–8) / Element 63 ≤ `06` — missing |
| SEG130-R-019 | applicability | **Fallback (Entry Mode 80) exclusion — entirely missing** |
| SEG130-R-020 | field | Appendix T Table `001` (`EMVYES`/`EMVNOT`) — **zero references** |
| SEG130-R-021 | field | Appendix T Table `002` (CARC) — **zero references** |
| SEG130-R-022 | serialization | No trailing-omission allowance — missing (and contradicted, see §5) |
| SEG130-R-023 | serialization | Segment 131 inverted rules — missing |

**The pattern is clear.** The AI covers **field-level syntax** well (8 of 11 hits are `class: field`) and **applicability / structural / cross-segment semantics** not at all. Every rule derived from narrative prose in §10.14.2–10.14.4 or Appendix T is absent — which matches the pipeline's own disclosed caveat:

> *"Requirement Derivation produced 0 llm_phrased requirements — elements whose only documented rule is narrative (processing_rules text, not a clean length/enum pattern) never got a requirement at all, so they can never produce a scenario or test case either. This is an unquantified coverage ceiling below the element level."*

---

## 3. Message-family placement — the most serious defect

Section 12.20 is unambiguous: Segment 130 *"always appears in Data Section No. 3 of an **EMV Financial Transaction Request**."*

| Severity | Count | % | Meaning |
|---|---:|---:|---|
| ✅ **EXACT** | 54 | 22.2% | `EMV Financial Transaction Request` |
| ⚠️ **ADJACENT** | 91 | 37.4% | EMV *Response* / `EMV Request` / `CA Public Key File Load Request` — right domain, **wrong segment** (these are Segment 131 concerns) |
| ❌ **IMPOSSIBLE** | 98 | 40.3% | Segment 130 can **never** appear here |

Impossible placements include:

```
x38  Financial Transaction Response          x3  Loyalty Card Transaction Request
x3   Electronic Mail Request                 x3  Totals Request
x3   ECA/TeleCheck Service Transaction Req   x3  Proprietary Data Load Request
x3   Moneris Day End Batch Close Response    x3  FSA/HRA Purchase/Capture
x3   Auth Completion (0220)                  x3  Purchase Reversal
```

A Segment 130 test case bound to `Totals Request` or `Electronic Mail Request` is not a weak test — it is an **invalid** one. It asserts a structure the specification forbids.

The pipeline is partly aware of this. It sets `messageFamilyCovered: false` on **240 of 243** test cases with the reason:

> `no RI-003 sample for this message family`

So the generator knew it had no evidence for the placement and emitted the test case anyway.

---

## 4. Downstream traceability

```text
53 requirements
   |--> 49 have >= 1 scenario        (92.5%)   4 orphaned
   |--> 104 scenarios total
          |--> 104 have >= 1 test case (100%)
                 |--> 243 test cases
                        |--> 54 on a legal message family (22.2%)
                        |--> 81 with any real QE field data (33.3%)
```

### 4.1 Orphaned requirements (no scenario) — all rated HIGH confidence

| Requirement | Page | Statement |
|---|---:|---|
| `:5442` | 267 | EMV Request Data Segment … max 3043 alphanumeric chars |
| `:6447` | 469 | CA Public Key File Checksum must be present in the EMV Financial Transaction Request |
| `:6448` | 470 | EMV Card Sequence Number must be sent when the card product includes it |
| `:6450` | 471 | EMV Chip Data must contain the exact values present on the card-to-terminal interface |

Note `:6447` asserts the checksum **must be present**. Section 12.20 marks Element 187 **Optional** in the request (Required only in Segment 131). This requirement is likely **wrong**, and it produced no scenario — so the error was never exercised.

### 4.2 Scenario type mix — no positive scenarios

| Type | Count |
|---|---:|
| negative | 57 |
| business_rule | 27 |
| field_constraint | 6 |
| relationship | 6 |
| boundary | 4 |
| supplemental_entity | 3 |
| narrative_rule | 1 |
| **positive** | **0** |

Segment 130 has **zero scenarios typed `positive`**, against 2,731 pipeline-wide. There is no clean happy-path EMV chip-read case.

### 4.3 Negative-class mix

`wrong_fixed_value` 26 · `length_exceeded` 14 · `missing_mandatory` 8 · `charset_invalid` 4 · `empty_value` 4 · `boundary_at_limit` 4 · `enum_invalid` 1 — 182 test cases carry no negative class at all.

`duplicate_sequence_number` (declared pipeline-wide) is **unused** for Segment 130.

### 4.4 Test-data richness

**162 of 243 (66.7%)** test cases report `qeCoverage.ratio == 0.0` — no QE-shaped field data. Only 44 test cases reach a ratio above 0.8.

Segment **131** itself is listed in `unavailableSegments` on 114 test cases — so the EMV response half of the exchange has no sample evidence, which is precisely why `SEG130-R-015` (checksum echo) and `SEG130-R-023` cannot be exercised.

---

## 5. Correctness concerns in what *was* produced

| Requirement | Issue |
|---|---|
| `:6447` — *"CA Public Key File Checksum must be present in the EMV Financial Transaction Request"* | Element 187 is **Optional** in Segment 130 (Required only in Segment 131). Overstates the obligation. |
| `:3238` — *"field 3 (CA Public Key File Checksum) is optional and may be omitted"* | Ambiguous against §12.20's *"Even when a field is not populated, you still need to send the Field Separator."* "Omitted" must mean *empty*, not *absent*. Risks a parser dropping the separator. |
| `:1123` — *"CA Public Key File Checksum must be used with all subsequent EMV transactions from this device"* | Not a stated §12.20 rule; appears inferred. |

---

## 6. Process-level caveats (from the pipeline's own run report)

These apply to the whole run, Segment 130 included:

- **No Independent AI Review occurred.** *"LLM bridge was unavailable for every scenario/requirement generation run this session — zero items passed through real Independent AI Review."* All 11,120 scenarios are `flagged_for_review`.
- **Approval was scripted, not human.** The catalog's own `approval_note` reads `"Scripted approval (no SME review)"`. 22,773 audit entries all show action `approve`.
- **36% of all requirements have no segment at all** — `by_segment_assignment_method.unresolved = 2,364`. Segment 130 is clean here (all 53 `declared`), but the catalog-wide figure limits confidence in any cross-segment claim.
- **34% of Segment 130 requirements sit at MEDIUM or LOW confidence** (16 MEDIUM, 2 LOW).

---

## 7. Comparison to the Test-Team position

| Dimension | AI solution | Test Team (this KB) |
|---|---|---|
| Rules identified | 11 of 23 | 23 |
| Max length | 3,043 ✅ | 3,043 (9,999 conflict logged as spec defect) |
| Separator rules | ✅ all four sub-rules | ✅ |
| Appendix T | ❌ none | ✅ Tables `001` / `002` transcribed |
| Reversal waiver §10.14.2.4 | ❌ | ✅ `SEG130-R-017` |
| Fallback exclusion §10.14.4 | ❌ | ✅ `SEG130-R-019` |
| Segment 103 companion legality | not addressed | ✅ corrected |
| Segment 131 contrast | ❌ | ✅ `SEG130-R-023` |
| Cryptogram scope boundary | ❌ silent | ✅ explicit out-of-scope |

**Convergence worth noting:** both independently landed on **3,043**, and the AI never cited the conflicting 9,999 from §11.8.1. That corroborates the adopted value in `SEG130-R-004`.

---

## 8. Recommendations

**Blocking**

1. **Reject the 98 IMPOSSIBLE-family test cases.** They assert structures the specification forbids. Gate generation on the Chapter 12 segment/transaction matrix before composition.
2. **Treat `messageFamilyCovered: false` as a generation stop**, not a label. 240 of 243 test cases were emitted without placement evidence.
3. **Correct `:6447`** — Element 187 is Optional in the request.

**High**

4. Add requirements for the 12 uncovered rules, prioritising the three **applicability** gaps (`R-017` reversal waiver, `R-019` fallback exclusion, `R-018` field-slot cap). These cause *false rejects* in production validation.
5. Add Appendix T coverage (`R-020`, `R-021`) — currently zero.
6. Generate at least one **positive** happy-path EMV chip-read scenario.

**Medium**

7. Raise QE data richness above the current 33.3%.
8. Supply Segment 131 sample evidence to unblock the echo-lifecycle rules.
9. Route the 4 orphaned requirements into scenario generation.

---

## 9. Reproduction

```text
step5_requirements/approved/requirement_catalog.json   filter segment_number == "130"  -> 53
scenarios/approved/approved_scenarios.json             filter requirement_id in set    -> 104
reporting/output/qe_shaped_test_data/*.meta.json       filter requirementId|scenarioId -> 243
score statements against coverage/segment-130-rule-catalog.json (word-boundary regex)
```

> **Method note.** An initial keyword pass scored 14/23. Three matches were false positives — the probe `tor` matched inside *"separa**tor**"*, and `field nos…4` matched *"Field Nos. 1-2, 2-3, 3-**4**"*. Re-scoring with word-boundary regexes gives the corrected **11/23**. Counts above are post-correction.
