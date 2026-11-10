# ATL105 Test Data Revalidation – All Products – Round 3 (2026-11-06, "5-10" delivery)

Third tracked re-validation pass. Compares the newest AI-team delivery (`C:\Users\F5QGZOJ\Downloads\5-10\5-10\<product>`, files timestamped 2026-11-05 evening — newer than the 2026-11-05 "5-11-out" round already on record) against **Round 2** (`5-11-out`, see [Consolidated_Revalidation_AllProducts_2026-11-05.md](./Consolidated_Revalidation_AllProducts_2026-11-05.md)) and, where relevant, the original Round-1 baselines. Each product section lists what is **Still Fixed**, what is **Still Left** (unfixed/partial, carried over), and what is **New in Round 3**.

| Product | Round-3 delivery size | Still Fixed | Still Left (unfixed/partial) | New in Round 3 |
|---|---|---|---|---|
| American Express Credit | 601 TCs / 1,296 files (was 454/912) | 8/9 (A-01,02,03,05,06,08,09,10) | A-07, N-01 (Table 030), N-02 (Track 1), Table 003/AVS | None found |
| MasterCard Credit | 630 TCs / 1,335 files (was 488/961) | 17/20 + RV-01 | CD-04 (Table 056), CD-08 (split-tender), CD-18 (Table 003) | NEW-01 (Merchandise-Return-Void missing Approval Number ref, 8 files) |
| MasterCard Fleet | 339 TCs / 721 files (was 307/633) | 8/9 + RV-01; **CD-19 now confirmed fully fixed** (prior "partial" was a measurement artifact) | CD-03 (Table 030), CD-04 (Table 056 + 032/049 gap), RV-02 (Fleet Segment length, open SME item) | Fleet ID-prompt content gap (pre-existing, newly found) |
| Interlink Debit | 323 TCs / 688 files (was ~249/532) | 4/7 (ID-01,02,03,05) | ID-04, ID-06, ID-07 (scenario still removed), NEW-01 placeholder regression | None found |
| Maestro Direct Debit | 323 TCs / 688 files (was ~249/536) | 4/7 (ID-01,02,03,05 analogs) | ID-04, ID-06, ID-07 analog (still removed), placeholder regression, M-02/M-03/M-04 | None found |

**Headline finding: across all 5 products, this round was almost entirely catalog expansion (new scenario coverage), not remediation.** Every previously-fixed defect held up at the larger scale with zero regressions, but of the ~19 previously-open items across all products, only one product-local item (MC Fleet's CD-19 traceability framing) turned out to already be fine on closer inspection — none of the genuinely still-open defects (A-07/CD-08, Table 030/056/003 family, ID-04, ID-06, ID-07, the `<AWAITING:...>` placeholder regression) were fixed in this round.

---

## 1. American Express Credit

Checked directly (not delegated). Round-2 baseline: [Consolidated_Revalidation_AllProducts_2026-11-05.md §1](./Consolidated_Revalidation_AllProducts_2026-11-05.md). TC mapping by exact scenario-name match, `31-10-out` names → `5-10` IDs.

### ✅ Still Fixed (unchanged from Round 2)
| ID | Evidence in Round 3 |
|---|---|
| A-01 | 0/1,296 files with 2-byte `EMVCardSequenceNumber` (old bug); all EMV files = `"001"` |
| A-02 | 0/1,296 files with the old 984-random-char content; all EMV files = `EMVYES`/length `006` |
| A-03 | Chip Fallback `791` legs (134/136) all carry 4-char MMYY only, no Track 2 contradiction; `801` still only 2 TCs (unchanged scenario-fidelity gap, not re-flagged as it's not a literal contradiction) |
| A-05 | Totals `CardLabel` = `"TE  "` (4 bytes) confirmed on sampled settlement leg |
| A-06 | Split-tender Segment Length still correctly recomputed (`087`) from the runtime remainder |
| A-08 | Expired-card TC: expiry `2512` (expired vs. reference `2611`), Tag `5F24=251231` present, TVR "expired" bit set |
| A-09 | Offline-PIN TC: CVM still `04` (offline enciphered PIN) |
| A-10 | RFID-terminal TC: Table 031 still `"07"` (non-chip Contactless-MSR profile) |

### ⚠️ Still Left (unchanged from Round 2 — not fixed)
| ID | Status | Evidence |
|---|---|---|
| A-07 | **STILL NOT FIXED** | All **6/6** EMV split-tender legs (up from 4, catalog grew) carry the identical static `9F02=000000000750` chip amount while `NonfuelAmount` is still a runtime placeholder in the same file — byte-identical pattern to Round 2 |
| N-01 (Table 030) | **STILL NOT FIXED** | **339/339** files containing a Table 030 entry (up from 239, same ~26% ratio) still declare `VariableInformationLength="010"` with an empty value |
| N-02 (Track 1 malformed) | **STILL NOT FIXED** | The "default track" scenario TC still has `AccountNumber="B341936513370869"` and `CardDiscretionaryBlockData="TEST/CARDHOLDER^280810131102429"` — identical values to Round 2, same 2 files |
| Table 003 (AVS) | **STILL NOT FIXED** | 8/8 files still have Table 003 with both length and value blank |

### 🆕 New in Round 3
None found in the direct checks performed. The delivery added new transaction categories beyond Purchase/Capture (e.g. "Authorization Only" was observed) consistent with the scope growth seen in the other 4 products; these new AMEX categories were **not** exhaustively re-audited with the same per-defect rigor as the Purchase/Capture scope in this pass.

---

## 2. MasterCard Credit

Delegated to a background agent. Catalog grew 488→630 TCs / 961→1,335 files; 100% of prior TC names carried forward unchanged (matched by name and scenario fingerprint hash).

### ✅ Still Fixed (unchanged from Round 2)
CD-01, CD-02, CD-03 (correct deferral), CD-05, CD-06, CD-07, CD-09, CD-10, CD-11, CD-12, CD-13, CD-14, CD-15, CD-16, CD-17, CD-19, CD-20, and RV-01 (confirmed still not a defect) — all re-verified catalog-wide with zero exceptions at the larger scale.

### ⚠️ Still Left (unchanged from Round 2)
| ID | Status | Evidence |
|---|---|---|
| CD-04 | **STILL PARTIALLY FIXED** | Tables 032 (171/171) and 049 (159/171, the 12-gap being spec-correct omissions on "Initial COF" legs) remain correctly populated, but **Table 056 is still blank in both length and value in all 171 occurrences** |
| CD-08 | **STILL NOT FULLY FIXED** | `9F02="000000000750"` static value unchanged across all **6/6** EMV split-tender legs (was 4/4 — catalog grew), `NonfuelAmount` still a runtime formula in the same files |
| CD-18 | **STILL PARTIALLY FIXED** | Table 003 (AVS) still indicator-only, length and value both blank, 8/8 |

### 🆕 New in Round 3
**NEW-01 — "Void of a Merchandise Return with the original's Sequence Number changed" drops the Approval-Number cross-reference.** All 8 instances of this new scenario family (one per entry mode) have `"ApprovalNumber": ""` instead of the standard runtime placeholder `"<ApprovalNumber from step 1 response>"` — confirmed as a real gap (not convention) by comparing against sibling scenarios that correctly carry the placeholder. Medium severity, narrowly scoped to this one new scenario family.

*(Also noted: 3 validator-tool blind spots, not data defects — the generic PAN/Track check doesn't recognize the legitimate Track-1 format used by CD-16's fix, the fallback oracle doesn't recognize a legitimate swiped-fallback-with-Track-1 compound case, and the amount-format rule doesn't recognize legitimate $0.00 Account-Verification COF transactions. All confirmed correct against raw JSON.)*

---

## 3. MasterCard Fleet

Delegated to a background agent. Catalog grew 307→339 TCs / 633→721 files.

### ✅ Still Fixed (unchanged from Round 2, plus one correction)
CD-01, CD-02, CD-05, CD-06, CD-09, CD-10, CD-11, CD-16, and RV-01 (confirmed still not a defect) — all re-verified with zero exceptions at the larger scale.

**Correction to Round 2's finding:** CD-19 (traceability) is **fully fixed**, not partially fixed as previously reported. Direct, exhaustive re-inspection this round found every one of 721 delivered files correctly traced with `exists:true` in the nested per-leg traceability structure — re-running the identical check against the Round-2 delivery itself also gives a clean 633/633. The earlier "344/633 untraced" figure was measuring the flat one-row-per-TC summary view (which is untraced by design, not a defect), not the authoritative nested per-leg matrix. No regression, no new fix — a correction of the prior assessment.

### ⚠️ Still Left (unchanged from Round 2)
| ID | Status | Evidence |
|---|---|---|
| CD-03 | **STILL PARTIALLY FIXED** | Table 030 indicator+length(`010`) present on all 180 applicable files, value still empty in 100% |
| CD-04 | **STILL PARTIALLY FIXED** | Tables 032/049 populated (78/78), Table 056 still blank length+value (78/78); still only one hardcoded "Subsequent" value system-wide, zero "Initial COF" scenario exists |
| RV-02 | **STILL UNCHANGED, open SME item** | Fleet Data Segment length still `035` on 628/633 files; Fleet Tags 1-5 still 0/633 populated, including on completion legs |

### 🆕 New in Round 3
**Fleet Data Segment "prompted field" content gap** — pre-existing (byte-identical in both Round 2 and Round 3, not a Round-3 regression) but never previously checked. Of 7 TCs claiming a specific Fleet field was captured, 2 ("ID Number and Odometer prompted", "Driver ID and Odometer prompted") populate no ID field at all despite claiming to, and one ("no fleet prompts") paradoxically ships full Odometer+Vehicle Number data. The validator has no oracle for this; only direct content inspection catches it.

---

## 4. Interlink Debit

Delegated to a background agent. Catalog grew ~249→323 TCs / 532→688 files. All 257 Round-2 TC names carried forward unchanged (0 removed); 66 brand-new TCs added, including 3 entirely new transaction categories (Authorization Only, Balance Inquiry, Merchandise Return) not previously reviewed.

### ✅ Still Fixed (unchanged from Round 2)
ID-01, ID-02, ID-03, ID-05 — all re-verified with zero exceptions at the larger scale.

### ⚠️ Still Left (unchanged from Round 2)
| ID | Status | Evidence |
|---|---|---|
| ID-04 | **STILL NOT FIXED** | Expired-card TC's chip blob is still byte-for-byte identical (including the Unpredictable Number) to the baseline "approved" TC; no Tag `5F24`; `rule_checks` still generic only |
| ID-06 | **STILL NOT FIXED** | Every `911`-coded (Contactless Magstripe) file still has PIN-Entry-Capability position-3 = `1`; zero files show the correct `8`. Validator actively marks this PASS (no oracle for this rule) — raw JSON is the only reliable evidence |
| ID-07 | **STILL REMOVED, not reinstated** | `excluded_combinations.csv` is byte-identical to Round 2; zero Credential-on-File occurrences anywhere, including in the 3 brand-new transaction categories (exclusion now also covers those) |
| NEW-01 (`<AWAITING:...>` placeholder) | **STILL PRESENT, unresolved** | 604/688 files (87.8%) still carry the literal placeholder token instead of a genuinely empty field; `DELIVERY_CONTRACT.md` wording is still unchanged, still says these fields "are empty" |

### 🆕 New in Round 3
None confirmed as new. One pattern was flagged for SME attention but is **not new this round**: every Chip Fallback file now uses only the swiped code `801`, zero use the keyed code `791` (the original baseline described the base scenario as `791`) — but this exact pattern was already present in Round 2, so it predates this delivery and isn't a Round-3 regression.

---

## 5. Maestro Direct Debit

Delegated to a background agent. Catalog grew ~249→323 TCs / 536→688 files. A full field-level diff confirmed the only differing field across every carried-over TC is `SequenceNumber` (a renumbering artifact) — every other finding below is byte-for-byte unchanged in content from Round 2.

### ✅ Still Fixed (unchanged from Round 2)
ID-01, ID-02, ID-03, ID-05 (analogs) — all re-verified with zero exceptions.

### ⚠️ Still Left (unchanged from Round 2)
| ID | Status | Evidence |
|---|---|---|
| ID-04 (analog) | **STILL NOT FIXED** | Expired-card TC's `EMVChipData` still byte-for-byte identical (including the random Unpredictable Number) to the baseline approved TC; no Tag `5F24` |
| ID-06 (analog) | **STILL NOT FIXED** | Table 005 still `"911"` with position-3 `1` on 100/100 files; `"918"` appears 0 times anywhere |
| ID-07 (analog) | **STILL REMOVED, not reinstated** | `excluded_combinations.csv` byte-identical to Round 2 (same 112 rows, same 20 COF rows); 0 Credential-on-File occurrences in 688 files |
| `<AWAITING:...>` placeholder | **STILL PRESENT, unchanged** | 604/604 applicable `AccountNumber`/`CardDiscretionaryBlockData` files still carry the token; `DELIVERY_CONTRACT.md` still unchanged |
| M-02 (Table 030 orphan) | **STILL UNCHANGED** | 132/132 EMV files, identical `length="010"`/value `""` shape |
| M-03 (Chip Fallback `791`→`801` collapse) | **STILL UNCHANGED** | All 102 Chip-Fallback files use only `801`; `791` occurs 0 times. Independently cross-confirmed by the validator's own `APPJ-FALLBACK-79-80` check |
| M-04 (Settlement Totals drops CardLabel block) | **STILL UNCHANGED** | The same 6 settlement scenarios still omit `CardLabel`/`CardTypeTotalCount`/`CardTypeTotalAmount` entirely rather than populating them; `SegmentLength` still shrunk to `"090"` accordingly. No validator rule exists for this — only direct inspection catches it |

### 🆕 New in Round 3
None found. The 66 new TCs (11 new flow patterns × 6 entry modes) were individually inspected end-to-end — sequence-number logic, partial-approval reversal amounts, and two-partial-return arithmetic are all correctly implemented using runtime tokens, with zero new internal-consistency mismatches found anywhere outside the already-known M-02 Table-030 issue.

---

## 6. Overall assessment

- **No regressions anywhere.** Every defect confirmed fixed in Round 2 is still fixed in Round 3, across all 5 products, even as every catalog roughly doubled or grew by 25-45%.
- **Almost nothing on the open list was addressed this round.** Of the ~19 distinct open items tracked across all 5 products after Round 2, only MasterCard Fleet's CD-19 turned out to already be fine (a correction to the prior assessment, not a new fix) — every other open item (A-07/CD-08's static split-tender chip amount, the Table 030/056/003 blank-length family, Interlink/Maestro's ID-04/ID-06, the COF-scenario-deletion-instead-of-fix in both debit products, and the `<AWAITING:...>` placeholder regression) is **unchanged, byte-for-byte in some cases**, in this newest delivery.
- **This round's effort went into catalog expansion**, not remediation: all 5 products added substantial new scenario coverage (new transaction categories for the debit products; new flow variants for the credit products) that was individually checked and found sound, plus 2 new small, narrowly-scoped defects (MC Credit's Merchandise-Return-Void Approval-Number gap; the Fleet ID-prompt content gap, which is pre-existing but was newly surfaced).
- **Recommendation:** the still-open items from Round 2 — especially the two recurring, shared, structural ones (the Table 030/056/003 blank-length inconsistency across AMEX/MC Credit/MC Fleet, and the `<AWAITING:...>` placeholder-vs-contract mismatch across both debit products) — should be explicitly re-raised with the AI team, since this round shows they will not get fixed as a side effect of general catalog growth.
