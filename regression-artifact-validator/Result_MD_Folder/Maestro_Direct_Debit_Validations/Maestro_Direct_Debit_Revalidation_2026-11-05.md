# ATL105 Test Data Revalidation – Maestro Direct Debit – 2026-11-05 AI Team Delivery

| Item | Value |
|---|---|
| Purpose | Maestro Direct Debit was **never validated in the previous (31-10) run** — no dedicated findings doc exists for it. This pass (1) independently establishes, from Maestro's own old delivery, which defects actually existed (using Interlink Debit's ID-01…ID-07 as the *candidate* checklist only, since both products share the same generator/spec), then (2) checks the new 05-11 delivery against that confirmed baseline, and (3) sweeps the new delivery on its own for anything new. |
| New delivery under review | `C:\Users\F5QGZOJ\Downloads\5-11-out\maestro_direct_debit` — 257 test cases / 532 test-data files (per its own `DELIVERY_CONTRACT.md`) |
| Prior delivery (baseline) | `C:\Users\F5QGZOJ\Downloads\31-10-out\maestro_direct_debit` — 278 test cases / 536 test-data files |
| Method | Every candidate defect confirmed directly against Maestro's own raw TD JSON (not assumed from Interlink); old→new test cases matched by exact scenario `name`; catalog-wide counts computed by parsing every `test_cases.jsonl` line and every TD JSON file (536 old + 532 new) with a script, not sampling; cross-checked against the producer's own `test_case_validation.json` self-check and `excluded_combinations.csv` |
| Result | **3 of 7** candidate defects cleanly fixed, **1** fixed-with-a-new-side-effect, **2** still unfixed, **1** resolved by deleting the scenario rather than correcting it, plus **4 new defects/gaps found** — one of them (a Chip Fallback entry-mode code regression) is a high-severity, 100%-prevalent new regression not present in the old delivery |

---

## 0. Why this isn't just a copy of another in-progress file

While investigating, a file with this same name/purpose appeared, changed, and partly disappeared in this same folder *during* this session — another process is concurrently writing revalidation reports here. I did not reuse or trust its contents; every number below was independently re-derived from the raw JSON in `31-10-out` and `5-11-out`. Where its (unverified) claims happened to overlap with what I independently confirmed, that's noted as cross-confirmation, not as a source.

---

## 1. Delivery shape, old vs new

| | OLD (31-10) | NEW (05-11) |
|---|---|---|
| Test cases | 278 | 257 |
| Test-data files | 536 | 532 |
| Entry modes | 7: EMV Contact, EMV Contactless, Magnetic Stripe, Contactless Magnetic Stripe, Chip Fallback, **Credential on File**, Contactless Mobile | 6 — **Credential on File removed entirely** (39 TCs: 29 Purchase + 7 Auth Only + 3 Return) |
| Status mix | needs_client_input 48 · requires_review 26 · **ready 6** · pending_sme 198 | needs_client_input 60 · requires_review 26 · **ready 0** · pending_sme 171 |
| Scenario catalog | 24 "device-capability-disabled" scenarios (Preauth/Return/Void disabled, Store-and-Forward window) across the 6 non-COF entry modes | Those 24 replaced by 42 "…declined" scenarios (2–3 per entry mode) — legitimate scenario-design churn, confirmed by name-mapping, not a defect |

The 6 TCs that were `ready` (no client input or review needed) in the old delivery were the "disabled" scenarios above; they were deleted rather than carried forward, so the new delivery currently has **zero** immediately-executable TCs (all need client PAN/PIN, SME review, or sign-off on the COF removal). Worth flagging for execution planning even though it isn't a wire-format bug.

Producer's own `test_cases/test_case_validation.json` for the new delivery reports `"valid": true, "findings": []` — its self-check does not catch any of the issues below; they only surface on direct field-level inspection.

---

## 2. Candidate checklist (ID-01…ID-07), independently confirmed on Maestro's own data

| ID | Description | Confirmed old Maestro pattern | New status | Catalog-wide (new) |
|---|---|---|---|---|
| ID-01 | EMV Card Sequence Number 2 bytes not 3 | `"EMVCardSequenceNumber": "01"` — **90/90** EMV files | **FIXED** | **104/104** now `"001"`; 0 remaining bad |
| ID-02 | EMV Additional Information = random filler, not EMV Table Data | 984 random A–Z/0–9 chars, length `"984"` — **90/90** | **FIXED** | **104/104** now `"EMVYES"`, length `"006"`; 0 remaining bad |
| ID-03 | Cashback purchase: EMV chip Tags 9F02/9F03 ignore the cashback amount | `9F02=000000001500` (merchandise only), `9F03=000000000000` — **4/4** EMV cashback files | **FIXED** | **4/4** now `9F02=000000002000` (=$15 + $5 cashback), `9F03=000000000500`; 0 remaining bad |
| ID-04 | "Expired debit card" test case has no rule/content that can ever prove its objective | `rule_checks` only generic (txn-type/card-type/entry-mode/EMV-segment/Table 031); no Tag `5F24`; **7/7** sampled | **NOT FIXED** | **6/6** (COF's copy removed along with COF) — `rule_checks` still has no expiry/decline assertion; chip data still has no Tag `5F24` |
| ID-05 | Totals Request `CardLabel` 2 bytes, not the fixed 4 | `"CardLabel": "DB"` — **63/63** Totals files | **FIXED, where the field exists** | **48/48** now `"DB  "` — but see **M-04**: 6 Totals files lost the field (and its siblings) entirely |
| ID-06 | Contactless Magnetic Stripe uses PIN-Entry-Capability code `911`, not the reserved `918` | Table 005 = `"911"` on every Contactless-MSR file — **67/67** | **NOT FIXED** | **79/79** still `"911"`; 0 instances of `"918"` in either delivery |
| ID-07 | Credential on File never carries the required COF Initial/Subsequent indicator (Table 032) | Table 032 appears in **0/536** files; 67 COF files use entry-mode `101` with no indicator | **"Fixed" by deleting the scenario, not by adding the field** | Table 005 code `101` and all 39 COF test cases are **gone**. New delivery's own `excluded_combinations.csv` now carries a Maestro Debit-COF exclusion rule citing *"p.638 … also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction"* — **this exact rule is absent from the old delivery's own `excluded_combinations.csv`**, confirming it's new, not something that was always there and ignored. Eliminates the defect but also all COF coverage — needs business sign-off before treating this as resolved. |

---

## 3. New defects found (not on the candidate checklist — found by sweeping the new delivery itself)

### M-03 — Chip Fallback entry mode loses its keyed/swiped distinction; every transaction now claims to be a swipe-fallback (highest severity of the new findings)

| | |
|---|---|
| Where | Every Chip Fallback test-data file, `"Variable Information Data Segment"` → Table `005` |
| Old (correct) | **67/69** Chip Fallback files use `"791"` (keyed fallback) — every scenario *except* the one TC explicitly named *"Chip unreadable → swiped fallback (80) → approved"*, which correctly uses `"801"` (2/69) |
| New (regressed) | **All 81/81** Chip Fallback files — the baseline purchase, every timeout-reversal, resubmit, settlement, reversal, and the one genuinely-swiped TC alike — now use `"801"`. `"791"` does not appear anywhere in the new delivery. |
| How verified | Mapped every `EMV_FALLBACK`-scenario TC (old 41, new 44) to its Table-005 code and scenario name; the old data cleanly separates the one "swiped" scenario from the other 40; the new data shows the identical code on all 44, confirmed file-by-file, not by aggregate count alone |
| Why it matters | This code tells the issuer/back-end whether the terminal actually swiped a stripe or keyed the PAN after a chip failure; collapsing it to one hard-coded value means every Chip Fallback transaction now misrepresents its own entry condition, not just the 40 that were already "keyed" |
| Certainty | Certain — confirmed by direct scenario-by-scenario diff against the old delivery, which itself correctly distinguishes the two cases |

### M-04 — Settlement ("Purchase → Purchase Reversal → Settlement") Totals Request silently drops the entire Category-Total breakdown

| | |
|---|---|
| Where | The Totals Request leg of the "Purchase → Purchase Reversal → Settlement" scenario, one per surviving entry mode: `TD-TC-001514/001544/001573/001603/001632/001662.step3.totals.json` |
| Old (correct) | Same 6 scenarios (by name) in the old delivery all include `"CardLabel": "DB"`, `"CardTypeTotalCount": "00000"`, `"CardTypeTotalAmount": "00000000"` (`SegmentLength "108"`) — confirmed by loading each file's direct old-delivery counterpart |
| New (regressed) | All 3 fields are **absent outright** (not blank) in all 6 files; `SegmentLength` is correspondingly shrunk to `"090"`. Every *other* Totals Request in the new delivery (48/54) still carries the block correctly, now with the ID-05 fix applied (`"DB  "`) |
| Why it matters | Chapter 12.6 requires the Category Total Data (Field 15–17) on every Totals Request a device sends, even when the running total is zero (which the old delivery shows is fine) — omitting the fields outright is a different, new failure mode from the 2-vs-4-byte padding bug already tracked as ID-05 |
| Certainty | Certain — not a formatting difference, the fields are missing; confirmed against each file's own old-delivery equivalent |

### M-02 — Orphan Table 030 Variable-Information entry on every EMV request (declared length doesn't match its own empty content)

| | |
|---|---|
| Where | Every EMV Financial Transaction Request, new `"Variable Information Data Segment"` entry: `{"VariableInformationIndicator": "030", "VariableInformationLength": "010", "VariableInformation": ""}` |
| Scope | **104/104** EMV files (100% of all EMV requests); **0/428** non-EMV files; **0/536** old-delivery files — did not exist before this delivery |
| Why it matters | A declared length of 10 with literally empty content is self-contradictory regardless of what Table 030 is supposed to carry; this is the same defect shape independently found in the AI team's American Express Credit delivery (same date), suggesting a shared template issue across products, though that cross-product scope wasn't re-verified here beyond Maestro |
| Certainty | Certain |

### M-01 — `<AWAITING:FieldName>` placeholder tokens replace blank client-input fields; delivery contract text not updated

| | |
|---|---|
| Old convention | `"AccountNumber": ""`, `"CardDiscretionaryBlockData": ""`, `"EncryptedPINBlockData": ""` — **473/473** instances genuinely empty, matching `DELIVERY_CONTRACT.md`'s wording that these fields "are empty" |
| New convention | `AccountNumber` / `CardDiscretionaryBlockData`: **478/478** now `"<AWAITING:AccountNumber>"` / `"<AWAITING:CardDiscretionaryBlockData>"`. `EncryptedPINBlockData`: **310/478** `"<AWAITING:EncryptedPINBlockData>"`, **168/478** still `""` |
| Checked and found *not* a defect | The 310/168 PIN-block split is not noise: it lines up exactly with each leg's own scenario design — `AWAITING` only appears on legs that are a genuine new card/PIN presentment (purchase, auth-only, return, next-transaction, resubmitted), and stays empty on legs the scenario itself names "(no PIN)" (reversal, timeout-reversal, completion, cancellation, void-return, TOR-retry). Verified this reconciles to the exact file counts in both buckets. |
| Remaining gap | `DELIVERY_CONTRACT.md` in the new delivery is byte-for-byte unchanged in its wording ("are empty and listed in `awaiting_inputs`") — it does not mention the new token convention at all, so any downstream harness doing a literal `value == ""` check on `AccountNumber`/`CardDiscretionaryBlockData` will now misread the placeholder as malformed data |
| Certainty | Certain (token presence); documentation-gap, not a wire-format accident |

---

## 4. Checked and found correct (due diligence, no defect)

- **Magnetic Stripe base/follow-on code split** (Table 005 `"901"` on the first leg of a flow, `"021"` on reversal/timeout-reversal/completion/cancellation/void-return legs): confirmed scenario-by-scenario in both deliveries, identical logic preserved old→new (44/23 old, 51/28 new — consistent proportion, no flip). This rules out a false positive that would have looked similar to M-03 from aggregate counts alone.
- **Table 003 (AVS)**: absent from both old and new (0/536, 0/532) — not used by this product, nothing to validate.
- **EMVChipData payload itself and `EMVChipDataLength`**: byte-for-byte identical between old and new on every EMV file — no new EMV-payload regression beyond the ID-01/ID-02 field overwrites already tracked.
- **EMV Contactless "861" fallback-to-contact variant** (the one TC-equivalent to Interlink's "Contactless chip → contact chip (86)"): unchanged at 2/2 old and new.
- `TRACEABILITY.md`, present at the root of the old delivery folder, is absent from the new delivery folder — a packaging-completeness note, not a wire-format defect.

---

## 5. Net assessment

- **Cleanly fixed, catalog-wide, 0 remaining:** ID-01 (EMV Card Sequence Number), ID-02 (EMV Table Data), ID-03 (Cashback chip-tag math).
- **Fixed where the field exists, but a new gap appeared on a 6-file subset:** ID-05 (Totals `CardLabel` width) — see M-04.
- **Resolved by removing the scenario, not by correcting it — needs business sign-off:** ID-07 (Credential on File).
- **Still open, unchanged, 100% prevalent:** ID-04 (expired-card test-design gap), ID-06 (Contactless Magnetic Stripe PIN-entry-capability code).
- **New, high-severity regression found only in this pass:** M-03 — Chip Fallback's keyed/swiped entry-mode code has collapsed to a single wrong value on every one of its 81 files.
- **New, scope-limited regression found only in this pass:** M-04 — 6 Totals Request files lost their required Category-Total breakdown outright.
- **New, systemic but logically-consistent convention change, undocumented:** M-01 (`<AWAITING:...>` tokens) and M-02 (orphan Table 030 entry) — the latter is a hard defect, the former is a documentation gap.

### Recommended priority for the next fix cycle
1. **M-03** (Chip Fallback code) — highest severity, 100% of an entire entry mode, straightforward regression against the product's own prior-correct behavior.
2. **ID-06** (Contactless Magnetic Stripe code) and **ID-04** (expired-card assertion) — both still fully unfixed from the original checklist.
3. **M-04** (Totals breakdown drop) — scope-limited (6 files) but a hard content omission, not a formatting nuance.
4. **M-02** (orphan Table 030) — mechanical template fix, clears 104 files.
5. **M-01** documentation update to `DELIVERY_CONTRACT.md` describing the `<AWAITING:...>` convention.
6. **ID-07** — get explicit business/SME sign-off that dropping Credential on File coverage entirely (rather than adding the missing indicator) is the intended outcome.
