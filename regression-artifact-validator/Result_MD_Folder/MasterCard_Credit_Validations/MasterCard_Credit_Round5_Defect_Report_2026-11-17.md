# ATL105 Test Case & Test Data Defect Report – MasterCard Credit – Round 5 (2026-11-17, "plan v12" delivery)

| Item | Value |
|---|---|
| For | AI Dev team. Please record your response against each ID. |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **Release 2026-4** (October 09, 2026). Page numbers are PDF pages. "PIPS" = BUYPASS Petroleum Industry Processing Specifications 2025-1. |
| Delivery reviewed | `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output_1611\mastercard_credit_new`. Build `buypass-atl105-mastercard-credit-generated-923e8c2eac`, run `20261116T152226421085Z`, built 2026-11-16 15:22 UTC ("plan v12", state `CANDIDATE`). Sub-package builds: `…-a88b54e39e` (data element) and `…-70fc786d3e` (terminal & host operations). |
| Baseline | Round 4: [MasterCard_Credit_Round4_Validation_2026-11-16.md](./MasterCard_Credit_Round4_Validation_2026-11-16.md). That round reviewed v10 build `cdf683cfd7`, which this delivery also ships as `archive/v10_2026-11-13`. |
| Method | (1) All 3,205 TCs and 3,479 TD files in the three packages were parsed and checked by script. The checks are the Round-4 rule set plus new checks for the fuel-machine, acceptance-context and MCC features. (2) Every TD was mapped to its v10 predecessor through `id_migration.csv` and diffed field by field. (3) The Java `TestDataSpecFieldValidationRunner` was run on a 40-TC sample. (4) Every pattern reported here was checked by hand in the raw JSON and against the spec text. |
| Scope of this report | As requested, this report covers **defects only**. It does not repeat coverage, scenario or BR figures. Prior defects are not explained again. Only their current status and the v12 evidence (new TC IDs) are given. |
| Run month for expiry checks | `2611` |

**Result:** Packaging and traceability are now correct: CD-19 and CD-20 are fixed. The test cases and test data are **not yet correct**.
- 17 prior items are still open, and 3 of them (R4-02, R4-03, R4-11) have more instances than before. 2 items are partly fixed.
- This round found **16 new defects**, 5 of them High. Most come from the new acceptance-context features (fuel machine and MCC contexts). Many others are TCs whose stated condition is not in the request.
- The data element and host-operations packages were not regenerated. Apart from the Table 030 value, their test data is identical to v10, so R4-05 – R4-09 are unchanged.
- Three README build gates do not hold: "validation findings 0", "context alignment mismatches 0" and "name readability findings 0". See R5-01, R5-03, R5-07 and R5-13.

---

## 1. Summary

| Area | Result |
|---|---|
| Prior register (CD / R4) | ✅ **Fixed 2:** CD-19, CD-20<br>↩ **Close as not applicable 1:** CD-15<br>⚠️ **Partly fixed 2:** R4-10, R4-14<br>❌ **Not fixed 17:** CD-07, CD-08, CD-13, CD-18, R4-01 – R4-09, R4-11, R4-12, R4-13, R4-15. R4-02, R4-03 and R4-11 have more instances than in Round 4.<br>⛔ **Still not generated 2:** CD-12, CD-17 |
| Regression check | No regression. CD-01 – CD-06, CD-09 – CD-11, CD-14, CD-16, NEW-01, RV-12 and RV-21 still hold. They were re-checked on all 616 EMV, 248 COF, 238 fallback, 126 TOR and 47 Totals legs. |
| New defects | **16:** High 5 (R5-01 – R5-05) · Medium 6 (R5-06 – R5-11) · Low 3 (R5-12 – R5-14) · Review 2 (R5-15, R5-16) |

Result categories are the same as in previous rounds:
- **DEFECT:** breaks an ATL105 rule, or the data cannot prove what the TC claims.
- **REVIEW:** needs SME or BUYPASS confirmation.
- **NEG** (CSV only): the intended mutation of a negative test. It is informational.

**Systemic root causes.** Fixing these removes most findings.
1. **The TC condition is not in the request.** The generator keeps TCs, names and objectives for tables, parameters and mutations that the TD does not carry (R5-02, R5-05, R5-09, R4-12). A gate that checks "the objective's element is present or mutated in the TD, and no negative TD equals a positive TD" would catch all of them.
2. **Fuel-machine amount mapping.** Nonfuel Amount is set to `0` instead of being left empty. The split-tender remainder and the Seg 102 template are still built from Nonfuel Amount (R5-01, R5-06).
3. **Oracle rules.**
   - A pre-authorization completion is treated as an invalid transition.
   - Download-only segments are expected in financial responses.
   - Declines are expected although the request contains nothing that would cause one.
   - (R5-04, R5-05, R5-08, R5-10)
4. **Acceptance contexts are only partly applied.** The MCC 6051/6211 indicator is not sent. The N-C/BD1 carriers were not moved to the context's terminal and product data (R5-03, R5-07, R5-16).
5. **The data element and operations templates have not changed since v10** (R4-05 – R4-09).

---

## 2. Prior defect register – Round 5 status

### ✅ Fixed in Round 5

| ID | Round-5 evidence |
|---|---|
| CD-19 | All 3,479 `test_data_file` / `test_data_leg_files` references resolve as written (`test_data/…`): main 2,422, data element 1,015, operations 42. The traceability matrices trace every leg file (0 untraced; Round 4 had 437). `file_exists` is true for every TC that has a TD. |
| CD-20 | Each package's `MANIFEST.json` reports `single_build: true` with one build ID. Its counts match the folder (2,129 / 2,422 · 1,029 / 1,015 · 47 / 42). There are 0 missing files and 0 SHA-256 mismatches. `DELIVERY_CONTRACT.md` reports the real counts, and `archive/` is delivered. *Residual (Low):* the main MANIFEST does not hash `README.md`, `CONTEXTS.md`, `SCENARIOS.md` or `id_migration.csv`. |

### ↩ Reclassify

| ID | Decision |
|---|---|
| CD-15 (cash back) | **Close as not applicable.** p.160 says: "First Data does not support cashback for Visa or MasterCard credit / Signature Debit transactions." The Round-4 recommendation to re-add a cash-back scenario is withdrawn. As a result, the 8 R4-04 TCs that send `CashAmount "1"` on MasterCard credit are also wrong for this reason. |

### ⚠️ Partly fixed

| ID | Round-5 evidence |
|---|---|
| R4-10 | `pending_sme` TCs carrying `<AWAITING>` table content dropped from 46 to **9**: TC-039991, TC-040047, TC-041011, TC-041015 – TC-041018, TC-041253, TC-041589. **Still open:**<br>• E33 is still an empty string on the online-PIN TCs (CD-13).<br>• Awaiting tables still send the Variable Information Length as a placeholder that is counted as 0 bytes. E112 is a fixed 3-digit field, so the Seg 111 lengths on 62 legs are at least 3 bytes short per awaiting table. Example: TD-TC-040118 declares `045`.<br>• See also R5-14. |
| R4-14 | The two worst examples were removed: TC-024336 (Service Level `A`) and TC-024341 ("24-byte PAN" that sent 16 bytes). All **5** remaining BD1 TCs are still named "… → declined", while their objective is "… is accepted":<br>• TC-038618 expects `2`.<br>• TC-038622 and TC-038632 expect `0`.<br>• TC-038601 and TC-038634 are FLAG.<br>TC-038622 claims "Additional Information … maximum length (984 bytes)" but sends the 6-byte `EMVYES`, so the boundary is not exercised. |

### ❌ Not fixed

| ID | Round-5 evidence (v12 IDs) |
|---|---|
| CD-07 | Unchanged. 11 split-tender step-2 legs declare a Seg 100 length that assumes a 1-digit amount: `086` (×7), `075` (×3), `089` (×1). The real amount is a runtime token (e.g. TD-TC-040134.step2). A Seg 102 length of `022` is precomputed from tokens on TC-041341 and TC-041632. The contract still does not tell the harness to recompute lengths after token substitution. |
| CD-08 | Unchanged. **5/5** EMV split-tender step-2 legs send `9F02 = 000000000000`: TC-040134, TC-040248, TC-041234, TC-041341, TC-041632. Java flags SPLIT-TENDER-9F02 / SEG130-R-009. |
| CD-13 | Unchanged. The 8 online-PIN TCs send `EncryptedPINBlockData ""`: TC-039831, TC-039855, TC-039880, TC-039905, TC-039929, TC-039953, TC-039978, TC-040002. The contract requires `<AWAITING:…>` and says "never an empty field". 6 of these 8 are non-EMV credit (see R4-11). |
| CD-18 | Unchanged. The 12 "(AVS or NVS or Both Information returned) → approved" TCs send no Table 003. 9 are `ready`: TC-040030, TC-040140, TC-040254, TC-040396, TC-040571, TC-040683, TC-040800, TC-040919, TC-041083. The others are TC-040059, TC-041032 and TC-041101. |
| R4-01 | Unchanged: 46 DEFECT findings on 38 positive TCs (34 `ready`), plus 12 negatives. The random content is the same as in Round 4:<br>• TC-040114: Seg 108 `PaymentTenderType AX`, coupon larger than the sale.<br>• TC-040117: tender `VS`.<br>• TC-040127: Seg 104 random tax/freight/duty, `ShiptoCountryCode 407`.<br>• TC-039967: Seg 145 = 999 random characters.<br>• TC-038606: Seg 123 on fallback `79`. |
| R4-02 | **More instances.**<br>• Loyalty Seg 108 on 27 legs (19 FTR + 8 EMV), e.g. TC-040114, TC-040344.<br>• **Fleet Data Segment 101 on 14 TCs** (Round 4: 1). These are new "… with Fleet Data Segment sent → approved" decision tables in both petroleum contexts, e.g. TC-041338 and TC-041629 (`Odometer 0128345`, `VehicleNumber 0042`).<br>• Enhanced Fleet Seg 145 on 9 TCs, e.g. TC-039967.<br>• **New: ECA/TeleCheck Data Segment (Seg 113) in a Balance Inquiry** (TC-038601). This is an 11.3 check-service segment and is not in the 11.1.1 list.<br>• SKU Seg 114 on 4 data element legs.<br>Java flags 11.8.1-SECTION3 on TC-038601 and TC-040114. |
| R4-03 | **More instances.** `EncryptedPINBlockData "PIN"` is now on 40 legs (Round 4: 20): 20 "… = PIN → approved" positives and 20 "… sent where it is not allowed" negatives. `"16"` is on the 8 R4-04 TCs. Examples: TC-040111, TC-040107, TC-040126. The 14 EMV legs with a PIN block still carry 9F34 `5E0302` (signature). |
| R4-04 | Unchanged: 8 TCs, 7 of them `ready`: TC-040126, TC-040240, TC-040356, TC-040557, TC-040668, TC-040786, TC-040903, TC-041019. Each sends:<br>• a random `ApprovalNumber` and `LocalDateandLocalTime 0615251430` on an original Auth Only<br>• PIN `16`<br>• `Pump 1 / Fuel 1 / Tax 1 / Cash 1` at a general merchant<br>Two further problems on the same TCs: the fuel amount is sent without Seg 102 (12.3 p.229), and cash is sent on MasterCard credit (p.160). |
| R4-05 | Unchanged.<br>**Network:**<br>• TC-038634: IB `A`.<br>• TC-038632: CA key checksum `0000000000000000000000000`, expected approved.<br>• TC-038622: see R4-14.<br>**Data element:**<br>• The same 22 positive boundary/profile TCs carry invalid values, e.g. TC-038740 (IB `A`), TC-038767 (Fuel `00000000`), TC-038770 / TC-038771 (8-byte amounts on MasterCard), TC-038803 (LDT on TT 0), TC-038945 (Totals Date `000000`).<br>• TC-039403 still sends the original CD-02 984-character random Additional Information.<br>• The 21 `blocked_verification` / `REJECTED_FINAL` TCs still ship a TD. |
| R4-06 | Unchanged in the data element package. Examples:<br>• TC-038754, TC-038756, TC-038812: PAN `TOTALS REQUEST`.<br>• TC-038810, TC-038811: PIN `16`.<br>• TC-038984, TC-038986: the Totals label tests drop count/amount.<br>Network negatives also carry R4-07 (below) and R5-07. |
| R4-07 | Unchanged.<br>**Data element:** 438 chip/track legs carry an MMYY E12 (374 `051`, 34 `821`, 30 `911`).<br>**Network:** 37 N-C/BD1 TCs (5 BD1 + 32 negatives, 34 `ready`), e.g. TC-038593 (`051` / `0529`), TC-038622 (`051` / `1130`), TC-038639 (`901` / `0427`).<br>Java flags TRACK2-EXPIRY-YYMM and APPJ-READ-HAS-TRACK. |
| R4-08 | Unchanged.<br>**Data element:** CA Public Key TID on 54 legs, TransArmor TID on 45, download TID (19 bytes instead of 13) on 70, Proprietary Data Load `NumberofSegments "1"` on 75, Seg 132 longer than 77 bytes on 49.<br>**Operations:** TC-039804 / TC-039805 (CA key), TC-039802 / TC-039803 (TransArmor), TC-039807 – TC-039810 and TC-039812 (downloads), and 10 Proprietary Data Load legs (e.g. TC-039792). |
| R4-09 | Unchanged: random Hardware/Software/Firmware versions (334 / 326 / 328), 79 random `LoadControlKey` values, 23 random Moneris values. **Also on all 67 network Totals legs:** every leg of the same terminal reports a different random version (e.g. `3W92CYI0`, `PWPVVWSL`). |
| R4-11 | **More instances.** "… with offline pin → approved" on non-chip entry modes: **34 TCs, 15 `ready`** (Round 4: 24).<br>• By entry mode: magnetic stripe 7, contactless magstripe 7, manual 8, fallback 6, COF 6.<br>• The manual ones include MOTO keyed TC-040029 and TC-041082.<br>• Examples: TC-039881, TC-039930, TC-041181 (keyed Balance Inquiry with offline PIN).<br>Contactless offline PIN: 14 TCs (Review; Round 4: 8). CAT online PIN on non-EMV credit: the same 6 TCs as in the CD-13 list. |
| R4-12 | Not fixed. Only the page numbers were removed from the names, which now read "… omitted where requires it → declined". The objectives still cite the pages (e.g. TC-038596 "…where p.162 requires it"), and the spec_basis is unchanged.<br>• **11 of the 14 N-C7 TCs (8 `ready`)** still claim that an optional or inapplicable segment is required: TC-038597, TC-038598, TC-038602, TC-038604, TC-038605, TC-038607, TC-038608, TC-038609, TC-038610, TC-038638 (new), TC-038639.<br>• Their TDs are byte-identical to approved positive requests, which proves that no rule is broken.<br>• TC-038639 omits Seg 130 from a **magnetic-stripe** auth.<br>• The 3 valid N-C7s (Seg 100 / Seg 130 omitted on EMV: TC-038595, TC-038596, TC-038614) are still `blocked_no_test_data`. |
| R4-13 | Unchanged (Review). 37 Totals legs after Auth Only, Auth Only → Cancellation, Return, Return → Void and Balance Inquiry send `CC  `, count `00001` and amount `0` (e.g. TC-041108). |
| R4-15 | Not fixed, only renamed. "Sending Table NNN" became "… with <table name> → approved", and the tables are no longer sent at all. See **R5-02**. |

### ⛔ Still not generated

| ID | Observation |
|---|---|
| CD-12 | There is no expired-card TC. The "(card issuer declines)" TCs list "Expired EMV test card" as an open item but send a valid expiry (see R5-05). |
| CD-17 | There is no contactless-MSR-only terminal: 0 legs send Table 031 `07` or Table 005 capability `8`. |

### Review items carried over (unchanged)
- **RV-01:** keyed/COF original is MMYY, follow-on is YYMM.
- **RV-03:** Appendix T framing. The Java runner fails 29 of the 40 sample TCs on this rule.
- **RV-04 / RV-05:** E189 `147` includes the BCD prefix; the CA key checksum is the QE sample.
- **RV-06:** TVR `8000048000` ("online PIN entered") on 600 EMV legs that carry no PIN.
- **RV-08:** `merchant_provenance` is still `placeholder`, and the new context terminals are "SAMPLE" placeholders.
- **RV-11:** Seg 105 declares `107`; the computed length is `108` (47 legs).
- **RV-13:** 251 contactless EMV legs carry no contactless-specific tags.

---

## 3. New defects found in Round 5

Severity follows the previous rounds:
- **High:** the message is invalid, or the test cannot prove its objective.
- **Medium:** the message is wrong but the test intent survives, or the issue is limited in scope.
- **Low:** traceability or cosmetic.

"ready" counts show how many affected TCs would be executed today. Every row is in the evidence CSV under its ID.

| ID | Defect | Rule | Scope | Severity |
|---|---|---|---|---|
| [R5-01](#r5-01) | Fuel-machine legs send Nonfuel Amount `"0"` | E58 p.393; 12.1 p.224 | 231 legs / 208 TCs | **High** |
| [R5-02](#r5-02) | "… with <table> → approved" TCs do not send the table | objective vs TD | 421 TCs (17 `ready`, 47 tables) + 50 alternate-path TCs (Review) | **High** |
| [R5-03](#r5-03) | MCC 6051 / 6211 TCs omit the mandated Table 032 sub-table 13 indicator | App. I p.577–578 | 20 TCs / 28 legs | **High** |
| [R5-04](#r5-04) | Pre-authorization completion is modelled only as an invalid transition → declined | App. G p.545; E86 p.409; PIPS p.28 / p.128 | 12 TCs; 0 positive completion flows | **High** |
| [R5-05](#r5-05) | Negative TCs send exactly the same request as an approved positive TC | single-rule principle | 111 TCs (50 `ready`) | **High** |
| [R5-06](#r5-06) | Petroleum split tender: remainder token and Seg 102 are built from Nonfuel Amount (= 0) | 12.3 p.229; E190 p.478 | TC-041632 | Medium |
| [R5-07](#r5-07) | Petroleum-context N-C/BD1 TCs use the general-retail terminal and drop Seg 102 | 12.3 p.229 | 13 TCs (all `ready`) | Medium |
| [R5-08](#r5-08) | Response checks expect download-only segments (DL1 – DL6) in financial responses | 12.42 – 12.47 p.328–335; 11.1.2 p.170 | 78 TCs (46 `ready`) | Medium |
| [R5-09](#r5-09) | Decision-table / pairwise condition is not in the request | objective vs TD | 14 TCs (12 `ready`) + 58 no-op duplicates (Low) | Medium |
| [R5-10](#r5-10) | "Cancellation (not allowed here) → declined" is not supported by the request | 10.1.2 p.83; PIPS p.105 | 12 TCs | Medium |
| [R5-11](#r5-11) | spec_basis of N-C/BD1 TCs cites pages unrelated to the element under test | traceability | 25 TCs | Medium |
| [R5-12](#r5-12) | id_migration "kept" rows map to a different test | traceability | 12 rows | Low |
| [R5-13](#r5-13) | Names broken by mechanical number removal | naming; README gate | 13 + 750 TCs | Low |
| [R5-14](#r5-14) | Client-input contract not met in the TD files | DELIVERY_CONTRACT | 79 files | Low |
| [R5-15](#r5-15) | Resubmission-limit boundary is off by one, and the host decline is not specified | p.61; PIPS p.17 | 24 TCs | Review |
| [R5-16](#r5-16) | CAT-terminal TCs filed under attended contexts; mPOS indicator at an unattended fuel machine | App. Q p.690–696; App. I p.570 | 16 + 12 TCs | Review |

<a id="r5-01"></a>
### R5-01 – Fuel-machine legs send Nonfuel Amount `"0"` (High)

| | |
|---|---|
| Where | Every leg sent by the fuel machine in PETROLEUM-UNATTENDED (201 TCs) and MCC_5542 (7 TCs), including TOR, cancellation and split-tender follow-ons. 159 TCs are positive and 49 negative. Status: 146 `needs_client_input`, 34 `pending_sme`, 28 `requires_review`. The value does not depend on client input, so it will still be there when these TCs become executable. |
| Generated | `FuelPurchaseAmount "1500"`, `NonfuelAmount "0"`, `PumpLaneNumber "01"`, Seg 102 `001 / G / 01 / 21500 / 1500`. Examples: TD-TC-041569 (CAT Purchase → approved), TD-TC-041592 (CAT Auth Only → approved), TD-TC-041786 (MCC 5542). |
| Expected | On a fuel-only sale, leave Nonfuel Amount empty (send only the field separator). |
| ATL105 rule | • E58 p.393: valid values are `1–99999999`.<br>• 12.1 p.224: Nonfuel Amount is Conditional.<br>• 12.1 p.223: "When a field is not populated, still send the Field Separator."<br>The Java runner fails SEG100-E58-FORMAT on TC-041569, TC-041592 and TC-041786. |
| Fix | Leave Nonfuel Amount empty when the whole amount is fuel, and recompute the Seg 100 length. |

<a id="r5-02"></a>
### R5-02 – "… with <table> → approved" TCs do not send the table (High; replaces R4-15)

| | |
|---|---|
| Where | Positive happy-path TCs whose objective reads "Happy path: <transaction> sending Table NNN <name>". They are named "… with <name> → approved". This is the R4-15 family under a new name. |
| Generated | Neither the TD nor the inline `request` contains the table. The request is a plain transaction with only Tables 005 / 031 / 030 (plus the COF tables where applicable). `rule_checks` do not test the table. `awaiting_inputs` is empty, while `open_items` still asks for "Table NNN … data from the client's test data". In v10, the same TCs sent the table as `<AWAITING:…>`. |
| Examples | • TC-040080 (`ready`): "EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved". Table 048 is not sent.<br>• TC-040067: "with Balance Information". Table 008 is the stored-value Balance Merge/Replace account (App. I p.556).<br>• TC-040068: "with Recharge Type Information". Table 019 is for PIN-on-receipt / top-up (p.562).<br>• Tables 001 (UPC, Blackhawk/InComm) and 002 (Program Identifier) belong to other programs. |
| Scope | **421 TCs across 47 App. I tables:**<br>• 17 `ready`: TC-040069, TC-040080, TC-040183, TC-040194, TC-040297, TC-040308, TC-040501, TC-040512, TC-040612, TC-040623, TC-040726, TC-040737, TC-040841, TC-040842, TC-040845, TC-040856, TC-040948<br>• 361 `needs_client_input`, 43 `pending_sme`<br>**The same pattern on 50 alternate-path TCs** (`requires_review` / `pending_sme`). Their target is not sent:<br>• Table 032 sub-table 13: 16, e.g. TC-040100<br>• Table 032 sub-table 01: 12, e.g. TC-041329<br>• Seg 116 TransArmor Load Data: 12, e.g. TC-041331. This is a host key-load segment, not a financial one.<br>• Table 049 sub-table 23: 8, e.g. TC-040102<br>• Seg 130: 2 |
| Expected | Send the table (with a value, or an `<AWAITING:…>` placeholder and status `needs_client_input`), or remove the TC. Tables scoped to other programs should be dropped (R4-15). |

<a id="r5-03"></a>
### R5-03 – MCC 6051 / 6211 TCs omit the mandated Table 032 sub-table 13 indicator (High)

| | |
|---|---|
| Where | The contexts MCC_6051 "6051 (cryptocurrency purchase)" and MCC_6211 "6211 (high risk security)":<br>• 6051: TC-041789 – TC-041792 and TC-041883 – TC-041888<br>• 6211: TC-041793 – TC-041796 and TC-041889 – TC-041894<br>28 legs in total, including reversals and TORs. |
| Generated | Table 007 = `6051` / `6211` and Tables 005 / 030 / 031. **No Table 032.** |
| Expected | Table 032 with sub-table 13: `13001Y` (cryptocurrency purchase) for MCC 6051, and `13001H` (high-risk securities) for MCC 6211. |
| ATL105 rule | App. I p.577–578, Table 032 sub-table 13:<br>• "Visa and MasterCard are mandating this attribute in all the related transactions. Only MCC '6051' is applicable…"<br>• "'H' High risk security indicator … MasterCard is mandating this attribute in all the related transactions. Only MCC '6211' is applicable…"<br>Sub-table layout: p.570 (example `111+021+03200601001M+`). |
| Effect | The only TCs that represent these MCC-specific requirements send a request MasterCard would treat as non-compliant, but they expect approval. The Java runner cannot see this (it passes these TCs apart from RV-03). |

<a id="r5-04"></a>
### R5-04 – Pre-authorization completion is modelled only as an invalid transition (High)

| | |
|---|---|
| Where | "… Authorization Only, then Purchase/Capture (not allowed after this original) → declined (to confirm)":<br>• Petroleum attended: TC-041343, TC-041374, TC-041405, TC-041447, TC-041476, TC-041504, TC-041532<br>• Petroleum unattended (fuel machine): TC-041634, TC-041666, TC-041698, TC-041742, TC-041771 |
| Generated | Step 1 is an Auth Only (TT 3 / CAT TT 5). Step 2 is a Purchase/Capture (TT 0 / CAT TT 4) with `<ApprovalNumber from step 1 response>`, the same Sequence Number and the same amount. Example: TD-TC-041634 sends 5 → 4, sequence 416341, fuel 1500. |
| Expected | This is a preauthorized completion, the standard pay-at-the-pump and pre-auth flow. It should be a valid flow → approved. |
| ATL105 rule | • App. G p.545: TT `0` is "Point-of-sale purchase/**preauthorized completion**", and TT `4` is "Customer-activated purchase **completion**".<br>• E86 p.409: "In Purchase/Capture, Sequence Number is same as in Authorization Only request."<br>• PIPS p.28: "Final total purchase amount cannot exceed the authorized amount in the completed CAT Authorization Only transaction."<br>• PIPS p.128: "AFP devices should always send Completions."<br>These quotes are in the TCs' own `spec_basis`. |
| Effect | The package has **no positive Authorization Only → Completion flow at all**. The only 12 step-2 purchase legs are these negatives. The core fuel flow is therefore tested only with the opposite oracle. The same pattern existed in v10 (not reported in Round 4). It now also covers the fuel-machine context, where this is the main transaction flow. |

<a id="r5-05"></a>
### R5-05 – Negative TCs send exactly the same request as an approved positive TC (High)

The negative TD is byte-identical to an approved positive TD except for the Sequence Number: same PAN, expiry, amounts, tables and chip data. Two `ready` TCs send the same bytes and assert opposite results, so one of them must fail.

| Pattern | Evidence |
|---|---|
| A – "(card issuer declines) → declined" | **67 TCs, 22 `ready`.** Includes the 2-leg variants "… then Purchase Reversal / Time-out Reversal / Cancellation / Void (card issuer declines)".<br>Example: TC-039837 (`ready`, expects RC `1`) is identical to TC-039814 (`ready`, expects RC `0`). Both use PAN 5158644613167836 with expiry October 2031.<br>`open_items` says "Expired EMV test card.", but the request carries the same valid card as the approved test. This is also why CD-12 is still open. |
| B – "… with <field> sent where it is not allowed → declined (to confirm)" | **44 TCs, 28 `ready`:**<br>• Encrypted PIN Block Data, 17: identical to the "= PIN → approved" request, e.g. TC-040107 vs TC-040111.<br>• Information Byte, 8: identical to the plain happy path, e.g. TC-040108 vs TC-040065. IB is a required field of every request.<br>• Payment Tender Type, 7: identical to the Loyalty positive, e.g. TC-040109 vs TC-040114.<br>• Service Level, 7: identical to "Service Level = F → approved", e.g. TC-040110 vs TC-040115.<br>• 5 contactless-mobile variants. |
| Expected | A negative must differ from its positive twin by exactly the one rule it violates. A decline that depends on the card needs a real decline card, supplied as a client input (`<AWAITING:AccountNumber>` + `needs_client_input`). |

<a id="r5-06"></a>
### R5-06 – Petroleum split tender built from Nonfuel Amount (Medium)

| | |
|---|---|
| Where | TC-041632: "Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only, then Authorization Only (first card partly approved, balance on a second card) → approved" |
| Generated | Step 2 sends:<br>• `FuelPurchaseAmount "<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>"`, although step 1 has Nonfuel `0` and Fuel 1500<br>• `NonfuelAmount "0"`<br>• Seg 102 product `400` / `U` with `UnitPrice "<'2' + this request's Nonfuel Amount>"` and `ProductAmount "<this request's Nonfuel Amount>"`, which both evaluate to 0<br>• `9F02 000000000000` (CD-08) |
| Expected | Remainder = step-1 Fuel Purchase Amount − approved amount, sent as fuel. Seg 102 has the fuel product first, carrying that amount. 9F02 = the remainder. |
| ATL105 rule | 12.3 p.229: fuel products first, and the product total must equal the Seg 100 total. E190 p.478. The Java runner fails SPLIT-TENDER-REMAINDER, SEG102-E76 / E107, E84-SEG102-LENGTH and SEG100-E41-FORMAT on this TC. |

<a id="r5-07"></a>
### R5-07 – Petroleum-context N-C/BD1 TCs use the general-retail terminal and drop Seg 102 (Medium)

| | |
|---|---|
| Where | TC-038611 – TC-038624, excluding the blocked TC-038614. These are N-C / BD1 TCs whose context is "Petroleum · attended POS". All 13 are `ready`. |
| Generated | They send Terminal Identifier `JX72948216050257001`, the general-retail terminal, instead of the petroleum terminal `KC57457075622174001`. 11 of the 13 send no Seg 102. In v10, these TCs used the petroleum terminal and Seg 102. For example, TC-038616 lost its Seg 102 (NumberofSegments `04` → `03`), and TC-038613's TID changed from `KC…` to `JX…`. |
| ATL105 rule | 12.3 p.229: "All fuel merchants must send fuel and nonfuel product data." Every other Petroleum-attended leg sends Seg 102 (product 400) on terminal `KC…`. |
| Effect | Every negative carries a second, unintended violation, so the decline cannot be attributed to the named element. The BD1 positives cannot be accepted. This contradicts the README statement "context alignment mismatches 0". |

<a id="r5-08"></a>
### R5-08 – Response checks expect download-only segments in financial responses (Medium)

| | |
|---|---|
| Where | response_check TCs named "(… returned) → approved" whose `leg_response_fields` expect a DL segment in the Auth Only or Purchase response. |
| Scope | **78 TCs, 46 `ready`:**<br>• DL1 Merchant Data Segment: 34 (18 `ready`)<br>• DL6 Store and Forward: 20 (7)<br>• DL3 Date and Time: 8 (7)<br>• DL2 Dial String: 8 (7)<br>• DL4 Software Dial Load: 8 (7)<br>Examples:<br>• TC-039833 (`ready`): "EMV Contact Chip Purchase/Capture (Merchant Data Segment returned) → approved". It is grounded on p.190, which is the Table Load message description.<br>• TC-040158 (`ready`): "Authorization Only (Store and Forward Data Segment returned)". |
| ATL105 rule | 12.42 – 12.47 (p.328–335): DL segments originate at BUYPASS in download (Table Load) responses. p.335: "The Store and Forward Data Segment is only sent in a Table Load Response". The Financial Transaction Response layout (11.1.2 p.170) does not contain them. |
| Note | This family existed in v10 (not reported in Round 4) and grew in v12: Merchant Data 24 → 34, Store and Forward 10 → 20. |

<a id="r5-09"></a>
### R5-09 – Decision-table / pairwise condition is not in the request (Medium)

| Pattern | Evidence |
|---|---|
| "with track = 1 → approved" | 6 of the 8 TCs cannot carry Track 1, and none of them does:<br>• EMV contact TC-040132, contactless TC-040246, keyed TC-040675, fallback TC-040792, COF TC-040911 (all `ready`)<br>• mobile TC-041024<br>Only the magstripe TCs TC-040362 and TC-040563 send Track 1. |
| "with Tax Type 1 = GST → approved" | 8 TCs: 7 `ready` (TC-040116, TC-040230, TC-040346, TC-040547, TC-040658, TC-040776, TC-040893) and TC-041009. GST is a Canadian Tax Type in the Tax by Product segment 143 (p.285). The requests carry neither Seg 143 nor Seg 102. |
| No-op decision tables (Low) | 58 TCs whose "condition" is already the default of every request, so the TD equals the happy path. They add no test value:<br>• "Information Byte = 0": 20<br>• "Service Level = F" and "Product Code Data Segment sent" in the petroleum contexts: 12 + 12<br>• "Variable Information Data Segment sent": 8<br>• "optional EMV Request Data Segment" on EMV: 6 |

<a id="r5-10"></a>
### R5-10 – "Cancellation (not allowed here) → declined" is not supported by the request (Medium)

| | |
|---|---|
| Where | "<entry mode> Cancellation (not allowed here) → declined" (ASSERT `1`):<br>• Petroleum attended: TC-041333, TC-041366, TC-041397, TC-041440, TC-041469, TC-041497, TC-041525<br>• Fuel machine: TC-041624, TC-041658, TC-041690, TC-041735, TC-041764 |
| Generated | A single standalone Cancellation (TT `S`) with no original transaction. The only basis is PIPS p.105: "If the transaction was declined by the chip (AAC), there is no transaction to cancel." No AAC-declined original is modelled. |
| Issue | Cancellation is a supported credit transaction at POS and CAT (10.1.2 p.83). The same contexts contain "Authorization Only, then Cancellation → approved". As built, these TCs duplicate "Cancellation with no original transaction → declined", and the asserted reason ("not allowed here") is not what the request tests. |

<a id="r5-11"></a>
### R5-11 – spec_basis cites pages unrelated to the element under test (Medium)

| | |
|---|---|
| Where | 20 of the 42 network N-C TCs that name a violated element, and all 5 BD1 TCs. |
| Examples | Field under test ← page cited in `spec_basis`:<br>• TC-038601, Merchant Trace ID ← p.503 "TPDU Protocol ID … 0x60"<br>• TC-038634, Information Byte ← EMV guide p.157 "PIN entry required and PIN Pad not present"<br>• TC-038618, Purchase Code ← PIPS p.179 "Terminal must support … purchase of goods"<br>• TC-038622, Additional Information ← PIPS p.186 (cash back / terminal capabilities)<br>• TC-038632, CA Public Key File Checksum ← EMV guide p.94 (key algorithm)<br>• TC-038617, Account Number ← PIPS p.115 "ISO Number 690046"<br>• TC-038619, Information Byte ← PIPS p.181 (offline limits)<br>• The N-C7 TCs record the violated element as "Prompt Code" and cite TransArmor p.15 / p.19 (expiry), PIPS p.23 (prepay) and PIPS p.24 (forced/off-line). |
| Effect | The README tells reviewers to use `spec_basis` for the spec reference. These references point them to the wrong rule. |

<a id="r5-12"></a>
### R5-12 – id_migration "kept" rows map to a different test (Low)

12 "kept" rows in the network package map an old TC to a new TC with a different transaction type, entry mode, or both:
- TC-024316 (EMV Contactless Merchandise Return) → TC-038593 (EMV Contact Authorization Only)
- TC-024327 (Manual Authorization Only) → TC-038606 (Chip Fallback Merchandise Return)
- TC-024312 → TC-038611
- TC-024323 → TC-038603
- TC-024342 → TC-038617
- TC-024355 → TC-038635

17 kept legs change Transaction Type. Results or defects carried over through `id_migration.csv` would attach to a different test. Mark these rows as `changed`, or assign new IDs.

<a id="r5-13"></a>
### R5-13 – Names broken by mechanical number removal (Low)

- **Network:** 13 names now read "… omitted where requires it → declined" because the page number was deleted. 12 of these TCs still have the page in the objective, e.g. "…where p.162 requires it".
- **Data element:**
  - 750 names have a dangling "– " where the segment number was removed, e.g. TC-038738 "MERCHANT 01 (POS PLACEHOLDER) – Standard Message Data Segment: Segment Type differs …".
  - 10 names say "(1 bytes)".
- The README still states "name readability findings 0".

<a id="r5-14"></a>
### R5-14 – Client-input contract not met in the TD files (Low)

- 79 TD files contain `<AWAITING:…>` placeholders (63 network, 16 data element) but have no `meta.unavailableFields` block. DELIVERY_CONTRACT ("Client inputs") says awaiting fields are listed there with dataSource `TODO_CLIENT_VALUE`.
- The R5-02 TCs list client data in `open_items`, but their `awaiting_inputs` is empty and the TD has no placeholder.

<a id="r5-15"></a>
### R5-15 – Resubmission-limit boundary (Review)

24 idempotency TCs are affected (`requires_review` / `pending_sme`), e.g. TC-041346 and TC-041637:
- "… (resent) sent 3 times (at the limit of 3) → approved"
- "… sent 4 times (beyond the limit of 3) → declined"

The sources disagree on the limit:
- ATL105 p.61: a transaction "may be resubmitted up to **three more** times", i.e. 4 sends in total.
- PIPS p.17: "submitted up to three times".

This is a device rule, and no source states that the host declines a 4th send. SME to confirm the limit and the expected result.

<a id="r5-16"></a>
### R5-16 – Context labelling of CAT TCs (Review)

- 16 "… on a CAT terminal with online/offline pin" TCs have acceptance context GENERAL or CARD_NOT_PRESENT-0100 ("General Retail · attended POS"). Their requests send an unattended Table 030 (`1000000001` / `1000100001`). Examples: TC-039831, TC-040139, TC-039978.
- 12 alternate-path TCs "via the alternate path for Sub Table ID 01" (mPOS) are generated for the unattended fuel machine, e.g. TC-041620. App. I p.570 says: "Mobile POS are always attended devices and not cardholder activated."

---

## 4. Data element and terminal & host operations packages

- These packages were not regenerated in substance.
  - **Data element:** 539 of the 1,015 TD files are identical to v10 apart from volatile fields (PAN, sequence, chip bytes and lengths). The other 476 differ only in Table 030 (`0100000001` → `0100000004`; Table 030 was also added to 89 legs), except for 2 files.
  - Those 2 files are the Round-4 "Number of Products" boundary positives (TC-020268 → TC-038838, TC-020275 → TC-038845). They now send `01` instead of `00` / `10` and were moved to `requires_review`. The invalid value is gone, but TC-038845 ("range maximum (01-10) is accepted") no longer tests its boundary.
  - **Operations:** 40 of the 41 TD files are identical; 1 differs in Table 030 only.
- As a result, **R4-05, R4-06, R4-07, R4-08 and R4-09 have the same counts as in Round 4:**
  - 22 positive boundary/profile TCs with invalid values
  - TC-039403 still sends the 984-character random Additional Information
  - 21 `REJECTED_FINAL` TCs still ship a TD
  - 438 chip/track legs with an MMYY E12
  - the same host-message identity errors and random versions/keys
- Only the names changed, and in a way that broke them (R5-13).

## 5. Field-by-field sample (Java runner)

The runner checked 40 TCs / 50 legs: 32 FAIL and 8 PASS_WITH_REVIEW. See [TC_Reports/README.md](./Round5_Defect_Report_Evidence/TC_Reports/README.md).

- **Appendix T framing (RV-03)** fails on 29 TCs. For 16 of them it is the only rule-level FAIL, but several of those 16 carry defects the runner cannot see at TC level: R5-02 (TC-040080), R5-03 (TC-041789, TC-041793), R5-04 (TC-041343), CD-13 (TC-039831) and CD-18 (TC-040140).
- **Confirms the new findings:**
  - SEG100-E58-FORMAT (R5-01) on TC-041569, TC-041592 and TC-041786.
  - On TC-041632 (R5-06): SPLIT-TENDER-REMAINDER, SEG102-E76 / E107, E84-SEG102-LENGTH and SEG100-E41-FORMAT.
- **Confirms the prior findings:**
  - R4-07: TRACK2-EXPIRY-YYMM / APPJ-READ-HAS-TRACK
  - R4-03: SEG100-E33-FORMAT
  - CD-08: SEG130-R-009 / SPLIT-TENDER-9F02
  - R4-02: 11.8.1-SECTION3
  - R4-05: SEG100-E44
- **Runner false positives (not counted):**
  - SEG130-R-001 on 3 non-chip legs (TC-038601, TC-038602, TC-038639)
  - E58 on the empty amounts of Balance Inquiries (TC-038601, TC-038634)
- The non-EMV GENERAL happy paths pass with review: magstripe, contactless magstripe, keyed, fallback, mobile, COF and MOTO.

## 6. Recommendations (in priority order)

1. **Add a TC ↔ TD consistency gate to the generator and re-run it on all three packages.** It should check two things: (a) the element, table, segment or parameter named in the objective is present (positive) or mutated (negative) in the TD, and (b) no negative TD equals a positive TD. This removes R5-02, R5-05, R5-09 and R4-12.
2. **Fix the fuel-machine mapping:**
   - leave Nonfuel Amount empty
   - build the split-tender remainder from Fuel Purchase Amount
   - put the fuel product first in Seg 102
   - (R5-01, R5-06)
   Then carry the context terminal and Seg 102 into the N-C/BD1 carriers (R5-07).
3. **Correct the oracles:**
   - A pre-authorization completion is valid. Add positive Auth Only → Completion flows for POS (3 → 0) and CAT (5 → 4) (R5-04).
   - Remove the DL-segment response checks from financial TCs (R5-08).
   - Give the issuer-decline tests a real decline card as a client input (R5-05).
   - Re-ground or remove R5-10.
4. **Send Table 032 sub-table 13 for MCC 6051 / 6211** (R5-03).
5. **Close the carried-over items.** Start with R4-02, R4-03 and R4-11, which have more instances than before. Then fix CD-07, CD-08, CD-13, CD-18, R4-01 and R4-04.
6. **Regenerate the data element and operations packages** (R4-05 – R4-09). They were not changed in v12.
7. **Housekeeping:** id_migration (R5-12), names (R5-13), spec_basis (R5-11), contract metadata (R5-14) and the CD-20 residual. **Close CD-15** (p.160).

---

### Evidence files
- [MC_Credit_Round5_Defect_Findings.csv](./Round5_Defect_Report_Evidence/MC_Credit_Round5_Defect_Findings.csv) – 4,189 rows. The `defect_id` column maps each row to an ID in this report. `INFO` marks an intended mutation of a negative test or a non-defect validator note. Columns: defect_id, package, check, severity, tc, test_type, polarity, status, context, td_file, detail.
- [TC_Reports/](./Round5_Defect_Report_Evidence/TC_Reports/) – the 40 Java field-by-field reports and their index.
- Previous round: [MasterCard_Credit_Round4_Validation_2026-11-16.md](./MasterCard_Credit_Round4_Validation_2026-11-16.md).
