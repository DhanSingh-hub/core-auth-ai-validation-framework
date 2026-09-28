# Segment 130 (EMV Request Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.20 EMV Request Data Segment (pages 12-53/267 to 268), 11.8.1 EMV Financial Transaction Request, Appendix R (EMV Chip Data Example), Appendix S (CA Public Key File), Elements 84, 85, 118, 187-192
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure in progress, building on a **pre-existing partial Test Team baseline** (see Section 5). SME intake: **2 of 10 items resolved from source**, 7 open, 1 administrative (see [SME/TBA Input Register](segment-130-sme-tba-input-register.md))

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md) topic-note pattern)

| Segment 100 topic | Segment 130 equivalent |
|---|---|
| SME/TBA learning note | [SME and Technical Business Analysis Note](segment-130-sme-tba-learning-note.md) |
| End-to-end flow | [Segment 130 End-to-End Flow](segment-130-flow.md) |
| `account-number-*` (core data carrier) | [EMV Chip Data and TLV Note](chip-data-tlv-sme-tba-note.md) · [Flow](chip-data-tlv-flow.md) |
| `sequence-lifecycle-*` (cross-message correlation) | [CA Public Key Lifecycle Note](ca-public-key-lifecycle-sme-tba-note.md) · [Flow](ca-public-key-lifecycle-flow.md) |
| `partial-approval-*` (conditional feature) | [EMV Additional Information Note](emv-additional-information-sme-tba-note.md) · [Flow](emv-additional-information-flow.md) |
| `prompt-code-*` (applicability decision) | [EMV Entry Mode and Fallback Note](emv-entry-mode-fallback-sme-tba-note.md) · [Flow](emv-entry-mode-fallback-flow.md) |
| `final-closure-*` | [Final Closure Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| `financial-card-type-business-requirements.md` | [EMV Card-Type and Transaction-Type Business Requirements](emv-card-type-business-requirements.md) |
| `companion-compatibility/` | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| `serialization-wire-format/` | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| `coverage/` | [Coverage Closure](coverage/README.md) · [Rule Catalog](coverage/segment-130-rule-catalog.json) |
| *(Segment 130 only)* | [AI-Generated vs Test-Generated Requirement Comparison](segment-130-ai-vs-test-requirement-comparison.md) |
| *(Segment 130 only)* | [**AI Solution BR Coverage Report**](segment-130-ai-coverage-report.md) — 11/23 rules, 47.8% |
| *(Segment 130 only)* | [SME/TBA Input Register](segment-130-sme-tba-input-register.md) |

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 130 | Section 12.20 |
| Segment name | EMV Request Data Segment | Section 12.20 heading |
| Purpose | Carries EMV chip-card transaction data (TLV-encoded) | Section 12.20 opening |
| Placement | Data Section 3 of the EMV Financial Transaction Request | Section 11.8.1 |
| Origin | Device | Section 12.20 |
| Segment length range | 001–3,043 alphanumeric characters (`SEG130-R-004`) | Section 12.20 + Element 84 valid-codes table. **Conflicts with Section 11.8.1's 9,999** — genuine spec defect, 3,043 adopted |
| Included when | Required for every EMV **authorization-class** request — "the only segment required for all EMV financial transactions". **Waived on Reversals/TORs** (§10.14.2.4) and absent on fallback/MSR entry modes (§10.14.4) | Section 11.8.1, 10.14.2.4, 10.14.4 |
| Message family | EMV Financial Transaction Request only. Data Section 3 occupies **Field Nos. 4–8** (five slots), so Segment 130 + at most four companions from {101, 102, **103**, 104, 111, 123, 135, 143, 145, 146, 151, 152, 153} | Section 11.8.1 table + Chapter 12 matrix |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-130-type-fixed-value` (fixed value 130) | `SegmentType` |
| 2 | 84 | Segment Length | N, 4 | R | `segment-130-length-format` | `SegmentLength` |
| 3 | 187 | CA Public Key File Checksum | AN, 25 | O | `segment-130-ca-key-checksum` | `CAPublicKeyFileChecksum` |
| 4 | 188 | EMV Card Sequence Number | N, 3 | C | `emv-card-sequence-number` | `EMVCardSequenceNumber` |
| 5 | 189 | EMV Chip Data Length | N, 3 | R | `segment-130-chip-data-length-range` (000-999) | `EMVChipDataLength` |
| 6 | 190 | EMV Chip Data | AN, 999 | R | `emv-chip-data-tlv-format` | `EMVChipData` |
| 7* | 191 | EMV Additional Information Indicator | N, 3 | R (when section present) | `emv-additional-info-repetition` | `EMVAdditionalInformationIndicator` |
| 8* | 192 | EMV Additional Information Length | N, 3 | R (when section present) | `emv-additional-info-repetition` | `EMVAdditionalInformationLength` |
| 9* | 118 | EMV Additional Information | AN, Var. | R (when section present) | `emv-additional-info-repetition` | `EMVAdditionalInformation` |

`*` Fields 7-9 form a repeating group (the EMV Additional Information Section), capped at 2,000 bytes total, with no internal separators — structurally identical to Segment 111's Variable Information Section repetition rule.

Maximum Segment 130 length is **3,043 alphanumeric characters** (`SEG130-R-004`). Note Element 118's reuse across Segment 112 ("Additional Information") and Segment 130 ("EMV Additional Information") — same number, different fields (`SEG130-R-012`).

---

## 3. Rule Set — Approved (Directly Derived from Specification + Pre-Existing Test Baseline)

Rule ID prefix: `SEG130-R-###`. See [the authoritative catalog](coverage/segment-130-rule-catalog.json) for the full machine-readable list (**23 rules**).

| Rule ID | Title | Class |
|---|---|---|
| SEG130-R-001 | Required for EMV authorization-class requests (companions include 103) | applicability |
| SEG130-R-002 | Segment Type fixed value 130 | field |
| SEG130-R-003 | Segment Length 4 digits | field |
| SEG130-R-004 | Maximum length 3,043 — resolved; 11.8.1's 9,999 is a spec defect | serialization |
| SEG130-R-005 | CA Public Key File Checksum (external-fixture-required for authenticity) | field |
| SEG130-R-006 | EMV Card Sequence Number | field |
| SEG130-R-007 | EMV Chip Data Length, valid 000-999 | field |
| SEG130-R-008 | EMV Chip Data TLV structure | field |
| SEG130-R-009 | Cross-field consistency with Segment 100 (review required) | field |
| SEG130-R-010 | Cryptogram (9F26) authenticity — out of scope | field |
| SEG130-R-011 | EMV Additional Information Section repetition, max 2,000 bytes | structure |
| SEG130-R-012 | Element 118 cross-segment reuse (112 vs 130) | metadata |
| SEG130-R-013 | Separator rules (fixed fields + repeating section) | serialization |
| SEG130-R-014 | Originates at the device | metadata |
| SEG130-R-015 | CA Public Key File Checksum echoed by Segment 131 | lifecycle |
| SEG130-R-016 | CA Public Key File (CA_KEYS) header/record layout | field |
| **SEG130-R-017** | **EMV data waived on Reversal/TOR transactions** | applicability |
| **SEG130-R-018** | **EMV Data Section 3 has five field slots (4–8), not six** | structure |
| **SEG130-R-019** | **Absent on fallback (Entry Mode 80) and MSR (90)** | applicability |
| **SEG130-R-020** | **Appendix T Table 001 — EMV Table Data (EMVYES/EMVNOT)** | field |
| **SEG130-R-021** | **Appendix T Table 002 — CARC (request legality open)** | field |
| **SEG130-R-022** | **No trailing-omission allowance (diverges from Segment 100)** | serialization |
| **SEG130-R-023** | **Segment 131 contrast — no separators, 3,834 max, 2,800-byte section** | serialization |

---

## 4. `[PROVISIONAL]` Items — 2 Resolved From Source, 7 Open

See the [SME/TBA Input Register](segment-130-sme-tba-input-register.md).

**Resolved without SME intake:**

- **P-01 / SEG130-SME-001** — **RESOLVED.** The max-length discrepancy is a *genuine* specification conflict, not OCR noise. §12.20 and the Element 84 valid-codes table both say 3,043; §11.8.1's table says 9,999 (column wraps after 3 chars — confirmed by siblings 103→3,334 and 151→2,309). **3,043 adopted.** Administrative follow-up: log a spec defect.
- **P-06 / SEG130-SME-006** — **RESOLVED.** Appendix T *is* in the extracted spec text and is now transcribed. Two indicators only: Table `001` (EMV Table Data, `EMVYES`/`EMVNOT`, len 6, echo obligation) and Table `002` (CARC, len 1, Visa Bit 44.8).

**Still open:**

- **P-02 / SEG130-SME-002**: Real CA Public Key File needed for genuine checksum/AID authenticity (`EXTERNAL_FIXTURE_REQUIRED`).
- **P-03 / SEG130-SME-003**: Complete EMV chip-value cross-field consistency rules vs Segment 100. Candidate tags derived: `9F02`, `9C`, `5F2A`, `9F1A`.
- **P-04 / SEG130-SME-004**: Confirm cryptogram (9F26) authenticity remains permanently out of scope.
- **P-05 / SEG130-SME-005**: Whether the existing AI Solution Team BR package is canonical for Item 2.
- **P-07 / SEG130-SME-007** *(new)*: Is Appendix T Table `002` (CARC) ever legal in a Segment 130 **request**? Determines allow-list `{001}` vs `{001,002}`.
- **P-08 / SEG130-SME-008** *(new)*: Brand-to-Appendix-E code mapping for the 17 EMV-supported card products (§10.14.2.3 names brands only).
- **P-09 / SEG130-SME-009** *(new)*: Which issuers participate for "Generic Proprietary" EMV (§10.14.2.3 says "some").
- **P-10 / SEG130-SME-010** *(new)*: What must Segment 131 echo when Segment 130 omits the Optional Element 187?

---

## 5. Pre-Existing Test Team Baseline (Important Context)

Unlike Segments 108/114/115 (which had no dedicated Test Team artifacts before this training pass), Segment 130 already had:

- `test-json/segment-130-core-structure-package.json` — Segment Type/Length and EMV Chip Data Length baseline (4 BRs).
- `test-json/appendices/appendix-r-segment-100-coverage.json` — EMV Chip Data TLV format and cross-field consistency (4 BRs, 1 explicitly `EXTERNAL_FIXTURE_REQUIRED`).
- `test-json/appendices/appendix-s-segment-100-coverage.json` — CA Public Key File layout and AID-to-key link (3 BRs, 1 `EXTERNAL_FIXTURE_REQUIRED`).

This training pass **consolidates** these pre-existing BRs into `segment-130-rule-catalog.json` (see Section 3) rather than duplicating or contradicting them, and folds their known gaps into the standard SME/TBA Input Register format used across all segments in this KB.

---

## 6. AI-Generated vs Test-Generated Requirement Matching

See [segment-130-ai-vs-test-requirement-comparison.md](segment-130-ai-vs-test-requirement-comparison.md). Headline finding: Segment 130 is the **first segment in this training pass where the AI Solution Team's statements largely align** with the Test Team's rules, because a dedicated AI BR package and dedicated coverage-report files already existed for Segment 130 (unlike Segments 108/114/115, where every AI statement was auto-matched against the wrong segment).

---

## 7. Do-Not-Assume Rules

1. **Do not** reject Segment 103 (EBT) as a Segment 130 companion — it **is** permitted. The Section 11.8.1 prose bullet list is an abbreviated summary; the table beneath it and the Chapter 12 matrix both include 103. *(This corrects an error in the prior revision of this KB.)*
2. Do not treat a missing Segment 130 as automatically invalid — Reversals/TORs waive EMV data (`SEG130-R-017`) and fallback/MSR entry modes exclude it (`SEG130-R-019`). These need distinct diagnostics.
3. Do not attempt to synthesize CA Public Key File authenticity or cryptogram (9F26) verification — both are `EXTERNAL_FIXTURE_REQUIRED`.
4. Do not emit a Field Separator between EMV Additional Information Section repetitions, or omit the single trailing separator after the last repetition.
5. Do not conflate Element 118 in Segment 112 with Element 118 in Segment 130 — same number, independent fields.
6. Do not certify EMV chip cross-field consistency as `COVERED` — it remains `REVIEW_REQUIRED` pending `SEG130-SME-003`.
7. Do not port Segment 100's trailing-optional-field omission allowance to Segment 130 — Section 12.20 grants no such allowance (`SEG130-R-022`).
8. Do not reuse the Segment 130 parser for Segment 131 — the response has **no** separators, a 3,834 maximum, and a 2,800-byte additional-information cap (`SEG130-R-023`).
9. Do not infer EMV applicability from card product alone — entry mode and chip-read outcome decide it.
10. Do not exceed five Data Section 3 segments in an EMV request (Field Nos. 4–8), which caps Element 63 at `06`.
