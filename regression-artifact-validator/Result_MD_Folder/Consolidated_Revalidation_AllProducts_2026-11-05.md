# ATL105 Test Data Revalidation – All Products – 2026-11-05 AI Team Delivery (Consolidated)

Consolidated from the 5 individual product reports produced during the 2026-11-05 re-validation pass. Each product section below lists **Fixed**, **Left (not fixed / partially fixed)**, and **New defects found in this run**, in that order. A cross-product summary of shared/systemic issues follows at the end.

| Product | Baseline defects checked | Fixed | Left (partial/not fixed) | New defects found |
|---|---|---|---|---|
| American Express Credit | 9 (A-01…A-10, A-04 withdrawn) | 8 | 1 (A-07) | N-01, N-02 |
| MasterCard Credit | 20 (CD-01…CD-20, RV-01) | 17 | 3 (CD-04, CD-08, CD-18) | Table 056/003 blank-length (shared) |
| MasterCard Fleet | 13 applicable | 9 | 4 (CD-03, CD-04, CD-19, RV-02) | Table 030/056 blank-length (shared) + COF coverage gap |
| Interlink Debit | 7 (ID-01…ID-07) | 4 | 3 (ID-04, ID-06, ID-07) | `<AWAITING:...>` placeholder regression (shared) |
| Maestro Direct Debit | 7 (Interlink analog, independently confirmed) | 4 | 3 (ID-04, ID-06, ID-07 analogs) | `<AWAITING:...>` placeholder regression (shared) |

---

## 1. American Express Credit

Baseline: `AMEX_Credit_Findings.md` (A-01…A-10). New delivery: `5-11-out\american_express_credit` (912 TD files / 454 TCs). Prior: `31-10-out\american_express_credit` (790 TD files / 416 TCs). Full catalog re-run: 238 FAIL / 206 PASS_WITH_REVIEW / 10 N/A out of 454 TCs.

### ✅ Fixed
| ID | Description | Evidence |
|---|---|---|
| A-01 | EMV Card Sequence Number 2 bytes instead of 3 | 132/132 EMV files now `"EMVCardSequenceNumber": "001"` |
| A-02 | EMV Additional Information = 984 random chars instead of `EMVYES`/`EMVNOT` | 132/132 now `EMVYES`/`EMVNOT` (6 chars) — content is fixed; a validator oracle bug still mis-flags this, see §6 |
| A-03 | Chip Fallback `79` + swiped Track 2 (contradiction) | All 100 `791` legs now MMYY-only, no Track 2 data |
| A-05 | Totals Card Label 2 bytes instead of 4 | All sampled Totals legs now `"TE  "` |
| A-06 | Split-tender step 2 Segment Length from impossible amount | Segment Length now correctly recomputed from the runtime remainder |
| A-08 | Expired-card test uses a non-expired card | Expiry now genuinely expired, Tag `5F24` present, TVR "expired application" bit set |
| A-09 | Offline-PIN test carries a signature CVM | CVM now `04` (offline enciphered PIN) |
| A-10 | RFID-terminal test uses capability `8` on a chip-capable device | Table 031 now `07` (non-chip Contactless-MSR profile) |
| A-04 | (withdrawn 2026-11-04 — was never a confirmed defect) | n/a |

### ⚠️ Left (not fixed / partially fixed)
| ID | Description | Status | Evidence |
|---|---|---|---|
| A-07 | Split-tender card B chip amount (`9F02`) = full original amount | **NOT FIXED** (value changed, root cause remains) | `9F02` is now a *different* static value (e.g. `000000000750`), but `NonfuelAmount` in the same file is still a runtime placeholder (`<RemainingBalance = ...>`) — the two can never be made to match at execution time. 4/4 EMV split-tender legs |

### 🆕 New defects found in this run
| ID | Description | Scope |
|---|---|---|
| N-01 | Orphan Variable-Information "Table 030" entry: declared length `010` but value is empty (13 E112 requires length = actual content length) | 239/912 files (EMV Contact, EMV Contactless, Manual/Keyed, Credential-on-File). Not present in Magstripe/Contactless Magstripe/Chip Fallback/Contactless Mobile; did not exist in the prior delivery. A related blank-length version also affects Table `003` (AVS) on 8 files/4 TCs |
| N-02 | Malformed Track 1 data pasted into the wrong ATL105 fields on new "default track" scenarios: `AccountNumber="B341936513370869"` (raw Track-1 `B` marker glued onto the PAN), `CardDiscretionaryBlockData="TEST/CARDHOLDER^280810131102429"` (cardholder name dumped in) | 2/912 files (`TC-001151`, `TC-001365`) |

---

## 2. MasterCard Credit

Baseline: master `Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md` (CD-01…CD-20, RV-01). New delivery: `5-11-out\mastercard_credit`. Prior: `31-10-out\mastercard_credit` (408 TCs with data / 839 TD files).

### ✅ Fixed
| ID | Description | Evidence |
|---|---|---|
| CD-01 | EMV Card Sequence Number 2 bytes not 3 | 142/142 EMV files = `"001"` |
| CD-02 | EMV Additional Info = 984 random chars | 142/142 = `EMVYES`, length `006` |
| CD-03 | Table 030 missing on EMV/COF | Indicator+length (`010`) present, value transparently deferred, TC explicitly `needs_client_input` naming Table 030 — correct deferral style. 263/263 |
| CD-05 | Fallback `79` carries swiped Track 2 | 104/104 now 4-digit MMYY only |
| CD-06 | Unmatched time-out reversal carries full track data | 122/122 now 4-digit |
| CD-07 | Impossible 8-digit runtime amount drives Segment Length | `NonfuelAmount` now the correct runtime formula; 0/961 files have an impossible static amount |
| CD-09 | Product Code Segment length `033` vs correct `029` | 6/6 now `029` |
| CD-10 | Totals CardLabel 2 bytes not 4 | 64/64 now `"CC  "` |
| CD-11 | Totals card-type sent with zero count `00000` | 0/72 zero-count occurrences; block now omitted entirely |
| CD-12 | Expired-card test uses a non-expired card | Expiry `2512` (before run month `2611`), Tag `5F24` now present (one TVR-bit nuance flagged for SME, not re-raised as a defect) |
| CD-13 | Online-PIN test: no PIN block, signature CVM | CVM now `02` (online PIN); E33 transparently deferred as `needs_client_input` |
| CD-14 | Offline-PIN test has signature CVM | CVM now `04` |
| CD-15 | Cashback: chip ignores cash amount | `9C=09`, `9F02`/`9F03` now reflect the cashback split |
| CD-16 | "Track 1" test actually carries Track 2 data | Now `AccountNumber="B5159417392576181"`, `CardDiscretionaryBlockData="TEST/CARDHOLDER^..."` |
| CD-17 | Terminal capability `8` conflicts with Table 031 `12` | 0/961 files still show the conflict; now `031="07"` |
| CD-19 | Traceability matrix points to a non-existent folder; follow-on legs untraced | Matrix now points at the real `test_data/` folder with a per-leg `exists` array |
| CD-20 | Two builds mixed in the package | Single coherent build (`"single_build": true`) |
| RV-01 | MMYY purchase/YYMM follow-on (withdrawn review item) | Reconfirmed not a defect; no new breakage |

### ⚠️ Left (not fixed / partially fixed)
| ID | Description | Status | Evidence |
|---|---|---|---|
| CD-04 | Tables 032/049/056 missing on COF | **PARTIALLY FIXED** | 032/049 now populated (121/121, 117/117), but Table 056 has **both length and value blank** in all 121 occurrences — worse than Table 030's framing |
| CD-08 | Split-tender card B chip amount (9F02) = full original amount | **NOT FULLY FIXED** | `9F02` changed to a different static value, but `NonfuelAmount` is still a runtime placeholder in the same file — same unresolved pattern as AMEX A-07. 4/4 files |
| CD-18 | AVS test: Table 003 missing entirely | **PARTIALLY FIXED** | Indicator now present but length **and** value both blank in all 8 occurrences — same pattern as Table 056 |

### 🆕 New defects found in this run
| Description | Scope |
|---|---|
| Blank-length Variable Information fields for Tables 056 and 003 (vs. Table 030's correct framing, which keeps the declared length even when the value is deferred) — breaks fixed-width Segment 111 parsing | 121/121 (Table 056) + 8/8 (Table 003). Same generator bug independently found in AMEX and MC Fleet too — one shared issue, not three |

---

## 3. MasterCard Fleet

Baseline: master `Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md` + `MasterCard_Fleet_Consolidated_Defects.md`. New delivery: `5-11-out\mastercard_fleet` (307 TCs / 633 TD files). Prior: `31-10-out\mastercard_fleet` (299 TCs / 569 TD files).

### ✅ Fixed
| ID | Description | Evidence |
|---|---|---|
| CD-01 | EMV Card Sequence Number 2 bytes not 3 | 88/88 EMV files = `001` |
| CD-02 | EMV Additional Info = 984 random chars | 88/88 = `EMVYES` |
| CD-05 | Fallback `79` carries swiped Track 2 | 69/69 now 4-char MMYY (was 39/61 bad) |
| CD-06 | Unmatched time-out reversal carries full track + wrongly rebuilt as EMV message | 112/112 now 4-char, plain `Financial Transaction Request` with no Segment 130 |
| CD-09 | Product Code Segment length `033` vs correct `029` | 633/633 arithmetically correct (545×`029`, 16×`027` legitimately shorter) |
| CD-10 | Totals CardLabel 2 bytes not 4 | 64/64 now `"FL  "` |
| CD-11 | Totals card-type sent with zero count | 0/72 zero-count occurrences; block omitted |
| CD-16 | "Track 1" test actually carries Track 2 data | Both Track-1 TCs now correctly `B...`/`^...` |
| RV-01 | MMYY purchase/YYMM follow-on (withdrawn review item) | Confirmed not a defect; spot-checked 3 pairs, no regression |

### ⚠️ Left (not fixed / partially fixed)
| ID | Description | Status | Evidence |
|---|---|---|---|
| CD-03 | Table 030 missing on EMV/COF | **PARTIALLY FIXED** | Indicator+length (`010`) now present on 157/157 applicable files, but value is empty in 100% of them |
| CD-04 | Tables 032/049/056 missing on COF | **PARTIALLY FIXED** | 032/049 populated (69/69), but Table 056 has length **and** value blank in all 69 |
| CD-19 | Traceability matrix points to a non-existent folder; follow-on legs untraced | **PARTIALLY FIXED** | Folder/path bug fixed (0 missing of 289 referenced), but still 1 row per TC — 344/633 follow-on leg files remain untraced |
| RV-02 | Fleet Data Segment length `035` ambiguity, all 18 slots reserved | **UNCHANGED — still open** | Still `035` in 556/633 files; Fleet Tags 1–5 still empty even on completion legs, weakening the "035 reserves completion-only data" reading |

*(CD-07/CD-08/CD-12–CD-15/CD-17/CD-18/CD-20 do not apply to MC Fleet — different scenario mix in this program's Purchase/Capture scope.)*

### 🆕 New defects found in this run
| Description | Scope |
|---|---|
| Table 030 length/value mismatch (shared with AMEX & MC Credit) | Same pattern as CD-03's partial-fix state above |
| Table 056 structurally worse than Table 030 — both length and value blank (shared with MC Credit) | All 69 Fleet COF files |
| Tables 032/049 use one hardcoded "Subsequent" value (`08001C`/`021C`) everywhere — no "Initial COF" scenario exists in the Fleet catalog to exercise the other valid value (coverage gap, Fleet-specific) | 69/69 COF files |

---

## 4. Interlink Debit

Baseline: `Interlink_Debit_Findings.md` (ID-01…ID-07). New delivery: `5-11-out\interlink_debit` (532 TD files). Prior: `31-10-out\interlink_debit` (536 TD files).

### ✅ Fixed
| ID | Description | Evidence |
|---|---|---|
| ID-01 | EMV Card Sequence Number 2 bytes not 3 | 0/532 bad (was 90/90); Segment 130 length correctly recomputed to `0204` |
| ID-02 | EMV Additional Info = 984 random chars | 0/532 bad (was 90/90); validator false-positive noted and discounted |
| ID-03 | Cashback chip data (9F02/9F03/9C) ignores Cash Amount | 4/4 EMV-cashback files (the only 4 that exist) now correct |
| ID-05 | Totals Request CardLabel 2 bytes not 4 | 0/532 bad (was 63/63); 48 Totals files now `"DB  "` |

### ⚠️ Left (not fixed / partially fixed)
| ID | Description | Status | Evidence |
|---|---|---|---|
| ID-04 | "Expired debit card" TC has no expiry/decline rule assertion | **NOT FIXED** | Chip blob still byte-identical to the baseline "Purchase approved" TC; `rule_checks` still only generic (entry-mode/Table 005/031). 6/6 surviving entry modes unchanged |
| ID-06 | Contactless Magstripe PIN Entry Capability code `1` instead of reserved `8` | **NOT FIXED** | Table 005 `"911"` still has `1` at position 3; no corrected variant found anywhere. 79/532 files (was 67/536) — proportionally unchanged |
| ID-07 | Credential on File never carries COF Initial/Subsequent indicator | **NOT FIXED — scenario REMOVED instead of corrected** | Entire Credential on File entry mode deleted (39 TCs → 0), via a new `excluded_combinations.csv` rule citing spec p.638. Resolves the symptom but deletes all COF test coverage — needs SME/business sign-off |

### 🆕 New defects found in this run
| Description | Scope |
|---|---|
| **NEW-01** — Client-input "awaiting" fields (`AccountNumber`, `CardDiscretionaryBlockData`, `EncryptedPINBlockData`) now contain literal placeholder text `<AWAITING:FieldName>` instead of being genuinely empty, contradicting `DELIVERY_CONTRACT.md`'s own "these fields are empty" wording (unchanged across both deliveries) | 478/532 (≈90%) of all new-delivery files (0/536 in the old delivery). Shared with Maestro Direct Debit — one change across both debit products |

---

## 5. Maestro Direct Debit

No dedicated baseline doc exists — Interlink Debit's ID-01…ID-07 pattern was used as the candidate checklist, with each one **independently confirmed as genuinely present in Maestro's own old delivery** before checking the new one (justified by Maestro and Interlink Debit sharing identical old-delivery TC/file counts — twin products, same generator templates). New delivery: `5-11-out\maestro_direct_debit`. Prior: `31-10-out\maestro_direct_debit` (249 TCs with data / 536 TD files).

### ✅ Fixed
| ID (analog) | Description | Evidence |
|---|---|---|
| ID-01 | EMV Card Sequence Number 2 bytes not 3 | 0/104 bad (was 90/90); now `"001"` |
| ID-02 | EMV Additional Info = 984 random chars | 0/104 bad (was 90/90); now `EMVYES`, length `006` |
| ID-03 | Cashback chip data (9F02/9F03) ignores Cash Amount | 4/4 EMV cashback files now correct (`9F02=2000`, `9F03=0500`) |
| ID-05 | Totals Request CardLabel 2 bytes not 4 | 0/48 bad (was 63/63); now `"DB  "` |

### ⚠️ Left (not fixed / partially fixed)
| ID (analog) | Description | Status | Evidence |
|---|---|---|---|
| ID-04 | "Expired debit card" TC has no expiry/decline rule assertion | **NOT FIXED** | Chip data still byte-identical to the baseline "Purchase approved" TC; `rule_checks` still generic only. 6/6 surviving entry modes unchanged |
| ID-06 | Contactless Magstripe PIN Entry Capability code `1` instead of reserved `8` | **NOT FIXED** | Table 005 `"911"` still has `1` at position 3 in 79/79 files; validator has no oracle for this rule and silently passes it — raw JSON is the only reliable evidence |
| ID-07 | Credential on File never carries COF Initial/Subsequent indicator | **NOT FIXED — scenario REMOVED instead of corrected** | Entire COF entry mode deleted (29 Purchase/Capture TCs → 0), same `excluded_combinations.csv` rationale as Interlink Debit — needs SME/business sign-off |

### 🆕 New defects found in this run
| Description | Scope |
|---|---|
| Same `<AWAITING:FieldName>` placeholder regression as Interlink Debit — replaces genuinely-empty client-input fields, contradicting the unchanged `DELIVERY_CONTRACT.md` wording | 478/478 instances of `AccountNumber` (and the same convention on `CardDiscretionaryBlockData`/`EncryptedPINBlockData`); 0/473 in the old delivery |

---

## 6. Cross-product shared issues (appear in more than one product)

1. **Variable-Information blank-length framing (Tables 030/056/003)** — AMEX, MC Credit, and MC Fleet all show the same two-tier inconsistency: Table 030 was fixed correctly (keeps its declared length even when the value is deferred), but Tables 056 (MC-specific CIT/MIT category) and 003 (AVS address) leave **both** the length and the value blank. This breaks fixed-width Segment 111 parsing and needs the same remediation Table 030 already received. One shared generator bug across 3 products, not three separate ones.
2. **Split-tender card B chip amount still static (AMEX A-07 / MC Credit CD-08)** — in both products the AI team changed the hard-coded `9F02` value but didn't tie it to the runtime-resolved remainder (`NonfuelAmount` is still a runtime placeholder in the same file). They can never match at execution time. MC Fleet and Interlink/Maestro don't have this scenario, so it's a 2-product issue.
3. **Client-input placeholder format changed without a contract update (Interlink Debit / Maestro Direct Debit)** — both debit products changed genuinely-empty client-input fields to literal `<AWAITING:FieldName>` text, affecting ~90% of files, while `DELIVERY_CONTRACT.md` still says these fields "are empty." Not a wire-format accident (100% consistent, clearly deliberate) but undocumented, and will break any tooling doing a literal blank check.
4. **"Fixed" by deleting the scenario, not correcting the data (Interlink Debit ID-07 / Maestro ID-07 analog)** — both debit products resolved their Credential-on-File indicator defect by removing the Credential on File entry mode from Purchase/Capture entirely (citing a new `excluded_combinations.csv` rule), rather than adding the missing Table 032 indicator. This eliminates the defect's symptom but also eliminates COF test coverage — needs explicit SME/business sign-off before being treated as "resolved" rather than "regressed."
5. **Validator-tool bugs (not test-data defects) — apply wherever checked:**
   - An EMV-Additional-Information oracle assumes a 3-digit Table ID prefix before `EMVYES`/`EMVNOT`; the AI team's correct plain 6-character value gets a false FAIL from this one oracle even though the validator's own separate check PASSes the same field. Confirmed in AMEX, MC Credit, MC Fleet, Interlink Debit, Maestro.
   - A keyed-entry MMYY expiry rule wrongly applies to credit follow-on legs (reversals/time-out reversals) that should use YYMM per spec section 10.1.8.2 — reproduces an already-withdrawn false positive. Confirmed in AMEX, MC Credit, MC Fleet. Not applicable to Interlink Debit/Maestro (no expiry field in debit test data).
   - Debit-network-specific rules (e.g. Table 005 position-3 PIN-Entry-Capability code) have no implemented oracle at all in the validator, so it silently passes genuine defects (ID-06) — raw JSON inspection remains the only reliable check for these.

---

## 7. Overall net assessment

Across all 5 products, the AI team made substantial, fully-verified progress on the high-prevalence systemic bugs from the original findings (EMV Card Sequence Number, EMV Table Data content, chip-fallback Track 2 contradiction, Totals Card Label width, cashback chip math, Track 1/Track 2 mix-ups, terminal capability conflicts) — these are cleanly fixed catalog-wide in every product that has them. However, sign-off should wait on:
- The incomplete Table 030/032/049/056/003 (Variable Information) remediation shared across AMEX/MC Credit/MC Fleet,
- The still-unresolved split-tender chip-amount defect in AMEX/MC Credit,
- The two unfixed Interlink Debit/Maestro defects (ID-04, ID-06) and the scope-deletion-not-fix of ID-07 in both debit products (needs explicit business sign-off), and
- The undocumented `<AWAITING:...>` placeholder convention change in both debit products.
