# ATL105 MasterCard Credit – AI Test Data Defects (Consolidated)

**For:** AI Dev team – please validate each defect and record your response in the register (§3).
**Date:** 2026-11-02
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, **Release 2026-4** (October 09, 2026). All page numbers below are **PDF page numbers** in the 2026-4 PDF. Each rule was also verified unchanged against 2026-3.
**AI delivery reviewed:** `31-10-out\mastercard_credit` (`test_cases\test_cases.jsonl`, `test_data\TD-TC-*.json`)

---

## 1. Scope

| Review | Test cases | Test data files | Method |
|---|---|---|---|
| A. EMV Contact + EMV Contactless (all) | 112 in catalog; **104 with test data** validated; the other 8 are device-only (T11) and have no host message | 210 (126 EMV requests, 66 follow-on Financial Transaction Requests, 18 Totals Requests) | Automated field-by-field validation (`TestDataSpecFieldValidationRunner` + trained Appendix R/T/I/J oracles), spot-checked manually |
| B. Magnetic stripe purchase (BR1_TS3) | TC-001796 – TC-001800 | 7 | Manual field-by-field review |
| C. Package and traceability (O31) | All 5 programs in `31-10-out` (Mastercard Credit 450 TCs / 839 TD files; plus Amex Credit, Interlink Debit, Maestro Direct Debit, Mastercard Fleet) | 3,270 TD files; 5 traceability matrices | Findings O31-01 – O31-03 reported by the package review, re-verified against the delivery on 2026-11-02 |

Message and transaction types covered: EMV Financial Transaction Request, Financial Transaction Request (reversal `8`, time-out reversal `Z`, cancellation `S`, void of return `U`, completion), Totals Request (`990`); purchase `0`, auth only `3`, return `7`, balance inquiry `E`; entry modes chip contact `05`, contactless `07`, swipe `90`.

Detailed evidence:
- EMV: [EMV_Full_Validation/README.md](./EMV_Full_Validation/README.md) (index with one report per test case)
- Magstripe: `ATL105-TD-Validation-Findings-Explained.md` (TC-001796 – TC-001800)
- Earlier narrative findings: [EMVcontact_contactless_findings.md](./EMVcontact_contactless_findings.md)

### Result categories

| Result | Meaning |
|---|---|
| **DEFECT** | A value breaks an ATL105 rule, or the data cannot prove what the test case claims. Must be fixed. |
| **REVIEW** | The data may be acceptable, but something must be confirmed (spec gap, placeholder, citation). |
| **INFO** | Note for the wire-message builder; not an error in the JSON. |

---

## 2. Summary

| Measure | Value |
|---|---|
| Test cases with test data reviewed | **109** (104 EMV + 5 magstripe) field by field; package checks across all 5 programs |
| Test cases with at least one defect | **108** (104 EMV + 4 magstripe; TC-001796 has review items only) – field level |
| Defect types | **17** (EMV D-01 – D-10 and D-04b; magstripe M-01 – M-04; package O31-01, O31-03). O31-02 is the same defect as D-01, shown at full-delivery scope. |
| Review items | **21** (R-01 – R-21) plus 5 Medium package-review topics (§4c) |

The two defects that affect **every** EMV test case (D-01, D-02) are generator rules, not test-case-specific errors. Fixing those two rules removes the defects from all 126 Mastercard Credit EMV request files. D-01 also occurs in the other four programs: 500 files / 402 TCs in total (O31-02).

The package defects (O31-01, O31-03) affect how the delivery is traced and identified, not the message content. They must be fixed before any coverage or traceability figure from the delivery can be trusted.

---

## 3. Defect register

Please fill in the last two columns for each item.

| ID | Defect | Element / field | Test cases affected | Severity | AI team: Agree / Disagree | AI team: Fix plan / comment |
|---|---|---|---|---|---|---|
| D-01 / O31-02 | EMV Card Sequence Number `01` instead of `001` | E188, Seg 130 | 104 MC Credit (all EMV); 402 TCs / 500 files across all 5 programs | High – every EMV request | | |
| D-02 | EMV Additional Information is 984 random characters instead of `EMVYES`/`EMVNOT` | E191/E192/E118, Seg 130 | 104 (all EMV) | High – every EMV request | | |
| D-03 | Expired-card test uses a card that is not expired | E12 | 2 | High – test cannot prove its objective | | |
| D-04 | Online-PIN test has no PIN block and a signature CVM | E33, Tag 9F34 | 4 | High – test cannot prove its objective | | |
| D-04b | Offline-PIN test has a signature CVM | Tag 9F34 | 2 | Medium | | |
| D-05 | Split-tender card B chip amount = full original amount | Tag 9F02 | 4 | High | | |
| D-06 | Totals Card Label `CC` instead of 4-byte `CC  ` | E13, Seg 105 | 18 | Medium | | |
| D-07 | Segment 100 length calculated from an 8-digit runtime amount (MasterCard max 7) | E84 / E58 | 10 | High | | |
| D-08 | FSA Product Code segment length `033`, spec arithmetic gives `029` | E84, Seg 102 | 4 | Medium | | |
| D-09 | Cashback test: chip data ignores the cash amount | Tags 9C, 9F02, 9F03 | 2 | Medium | | |
| D-10 | Totals category sent with count `00000` | E16, Seg 105 | 2 | Medium | | |
| M-01 | "Track 1" test carries Track 2 data (identical to the Track 2 test) | E2 / E12 | 1 | High – test cannot prove its objective | | |
| M-02 | RFID-without-chip test: Table 005 `908` contradicts Table 031 `12` | Seg 111 Tables 005/031 | 1 | High | | |
| M-03 | Split-tender step 2: amount is a sentence; length `093` built from 8-digit provisional amount | E58 / E84 | 1 | High – same root cause as D-07 | | |
| M-04 | Reversal step 2: amount/approval are sentences; length `088` built from 8-digit provisional amount | E58 / E5 / E84 | 1 | High – same root cause as D-07 | | |
| O31-01 | Traceability matrix points to a legacy folder (`qe_shaped_test_data/`) not in the delivery, while reporting `file_exists: true`; follow-on legs are not traced | `reports/traceability_matrix_full.json`, `TRACEABILITY.md` | 1,529 trace rows (all 5 programs; MC Credit 408); 431 MC Credit TD files never traced | High – traceability unusable | | |
| O31-03 | Mastercard Credit package mixes two builds: export = 450 TCs / 839 TD files, but the embedded zip and top-level README describe an older 241-TC build | `BR_SCE_Test_cases.zip`, `README.md` | Whole MC Credit package | High – no single authoritative build | | |

---

## 4. Defects in detail

Each defect lists **where**, **generated vs expected**, **the ATL105 rule** and **how to verify** it.

### D-01 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3

| | |
|---|---|
| Where | Every EMV request file (126 files in 104 test cases), `"EMV Request Data Segment" → "EMVCardSequenceNumber"` (line 47) |
| Generated | `01` |
| Expected | `001` |
| ATL105 rule | **Element 188, p.477:** *"Character Type: AN, Maximum Length: 3 bytes. Representation: **Fixed length of three bytes, right-aligned and padded with zeroes**. Space-filled if EMV Tag 5F34 is not provided by the EMV chip card … Valid Codes/Values: 000—099."* 12.20 Field 4 (p.270). |
| How to verify | Open any EMV TD (e.g. `TD-TC-001722.json`), line 47 → `"01"` is 2 characters. The chip data contains Tag 5F34 = `01`, so the value is right but the width is wrong. |
| Fix | Zero-pad Tag 5F34 to 3 bytes (`001`), or send 3 spaces when 5F34 is absent. Keep it consistent with Tag 5F34 and recompute the Segment 130 length (+1). |
| Scope across the delivery (O31-02) | The same rendering occurs in **500 request files / 402 TCs** across all five programs in `31-10-out`. Re-verified 2026-11-02: |

| Program | Files | TCs |
|---|---|---|
| Mastercard Credit | 126 | 104 |
| American Express Credit | 116 | 94 |
| Mastercard Fleet | 78 | 60 |
| Interlink Debit | 90 | 72 |
| Maestro Direct Debit | 90 | 72 |
| **Total** | **500** | **402** |

### D-02 – EMV Additional Information (Element 118) does not contain EMV Table Data

| | |
|---|---|
| Where | Every EMV request file (126 files in 104 test cases), lines 50–52 |
| Generated | Indicator `001`, Length `984`, Information = 984 random A–Z/0–9 characters (e.g. `XPXZRQBX814S3CAQ0ZNJ…C5K8MMTF`) |
| Expected | Indicator `001`, Information **`EMVYES`** or **`EMVNOT`** |
| ATL105 rule chain | 1. **E191 (p.479):** `001` = *"EMV table data"* → the indicator is correct.<br>2. **E192 (p.480):** valid `001–985` → `984` is in range, but this is only the generic range.<br>3. **E118 EMV Additional Information (p.439):** *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here … Please refer to Appendix T."*<br>4. **Appendix T-1 (p.701):** EMV Table Data – *"Maximum Length n6, Len.: 6"*, Request values **`EMVYES`** (device able to receive EMV Table Data) / **`EMVNOT`** (unable). |
| Why E192 = `984` is not the defect | E192 is valid in range and equals the content length. The defect is the **content** of E118: random text is not allowed for indicator `001` at any length. |
| Root cause | TC marks this value `method: format_synthesis` with flag `UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped`. The generator filled the generic 984 maximum instead of applying Appendix T. The same random string is also reused across test cases (TC-001722/1723/1724 → `XPXZ…`; TC-001759/1761/1762 → `EAMH…`). |
| How to verify | Open `TD-TC-001722.json` lines 50–52, then Appendix T-1 on p.701. |
| Fix | Use `EMVYES`/`EMVNOT`, set E192 to the resulting length (see R-14 for the framing question), and recompute the Segment 130 length. With `EMVYES` and no framing, `1181` becomes `0204` (−978 for E118, +1 for D-01). |

### D-03 – Expired-card test uses a non-expired card

| TC | File : line | E12 value | Expiry (YYMM) |
|---|---|---|---|
| TC-001723 (contact) | `TD-TC-001723.json` : 15 | `311020127370826` | `3110` = Oct 2031 |
| TC-001761 (contactless) | `TD-TC-001761.json` : 15 | `290720142194190` | `2907` = Jul 2029 |

| | |
|---|---|
| Test objective | "Expired EMV card -> sent online -> host decides" |
| ATL105 rule | p.160: *"The device does not perform expiration date validation in an EMV card transaction."* E12 (p.372): *"For all authorizations, merchants must send a value even for expired cards."* |
| How to verify | The first 4 digits of Card Discretionary Block Data are the expiry (YYMM). Both are in the future. |
| Fix | Use a card with an expiry earlier than the run date, include chip Tag 5F24, and set TVR byte 2 "Expired application". The TC already lists *"Expired EMV test card"* as an open client input. |

### D-04 – Online-PIN test has no PIN block and a signature CVM

| TC | Entry mode | Transaction |
|---|---|---|
| TC-001724 | Contact | Purchase |
| TC-001762 | Contactless | Purchase |
| TC-002021 | Contact | Auth Only |
| TC-002037 | Contactless | Auth Only |

| Check | Location | Generated | Expected |
|---|---|---|---|
| PIN block | `"EncryptedPINBlockData"`, line 16 | `""` | DUKPT KSN + PIN block (AN36) |
| CVM Results | `EMVChipData` → Tag 9F34 | `5E0302` → CVM code `1E` = **signature** | CVM code `02`/`42` = enciphered PIN verified online (e.g. `420302`) |

| | |
|---|---|
| ATL105 rule | 10.14.3, p.162: *"Online PIN: This is applicable for **credit** or debit transactions, in which the PIN is electronically sent to and validated by the card issuer."* MasterCard requires PIN-capable devices to *"submit a PIN in the authorization request message"*. E33 (p.382): *"For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."* TC execution step: *"the PIN block is sent online (Encrypted PIN Block Data, DUKPT)"*. |
| Not a rule for all credit | For normal credit purchases a PIN is optional (p.162: *"PINs are an optional field for a credit/signature debit transaction"*), so an empty E33 is **correct** on non-PIN tests. D-04 applies only to the online-PIN test cases. |
| Classification | Test-objective defect, **blocked by client input** (TC open item: *"Test card with a known online PIN and the test DUKPT key (p.57)"*). |
| How to verify | Decode Tag 9F34 in the chip data (TLV walk, or search `9F3403` and read the next 6 hex characters). The low 6 bits of byte 1 give the CVM code (`5E` → `1E` signature). The CVM code table is EMV Book 3 Annex C3. |
| Fix | Populate E33 and set 9F34 to online PIN once the test card/key is available. Otherwise mark the TC **blocked – needs client input** instead of releasing signature data under an online-PIN objective. |

### D-04b – Offline-PIN test has a signature CVM

| | |
|---|---|
| Where | TC-001725 (contact purchase), TC-002022 (contact auth only); Tag 9F34 |
| Generated | `5E0302` (CVM code `1E` = signature) |
| Expected | CVM code `01`/`04` (plaintext/enciphered PIN verified offline), optionally with signature `03`/`05` |
| Basis | EMV Book 3 Annex C3; E190 (p.478): chip data must contain *"the exact values that were present on the card to terminal interface"*. E33 is correctly empty for offline PIN. |

### D-05 – Split-tender second card carries the full original amount in chip data

| TC | File | Tag 9F02 |
|---|---|---|
| TC-001726 (contact) | `TD-TC-001726.step2.split-tender.json` | `000000001500` |
| TC-001763 (contactless) | `TD-TC-001763.step2.split-tender.json` | `000000001500` |
| TC-001743 (contact, FSA) | `TD-TC-001743.step2.split-tender.json` | `000000001500` |
| TC-001780 (contactless, FSA) | `TD-TC-001780.step2.split-tender.json` | `000000001500` |

**Simple example (TC-001726).** A customer buys something for **$15.00**:
1. **Card A** (`5144283846699542`) is asked for $15.00 but only has, say, $10.00. The issuer approves $10.00 (partial approval, Response Code `F`).
2. **Card B** (`5172304375757090`) pays the **remaining $5.00**.

| Leg | Field | Value | Meaning | OK? |
|---|---|---|---|---|
| Step 1 (Card A) | NonfuelAmount | `1500` | $15.00 | ✅ |
| Step 1 (Card A) | Tag 9F02 | `000000001500` | $15.00 | ✅ |
| Step 2 (Card B) | NonfuelAmount | `<RemainingBalance = …>` | leftover at runtime (e.g. $5.00) | ✅ approach |
| Step 2 (Card B) | Tag 9F02 | `000000001500` | **$15.00** | ❌ copied from Card A |

The leftover is always less than $15.00, so `1500` on Card B is always wrong.

| | |
|---|---|
| ATL105 rule | 10.1.2.1, p.84: *"Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the **remaining balance due** on the sale."* KB rule SEG130-R-009: chip values (9F02) must agree with Segment 100. Generator's own flag: *"9F02 = request amount"*. |
| Fix | Build Card B's 9F02 from the step-2 amount (the remainder), not from step 1. |

### D-07 – Runtime amounts are 8-digit random values, and segment lengths were calculated from them

| TC | Leg file | Declared length | Implied runtime width |
|---|---|---|---|
| TC-001726 / TC-001763 | `…step2.split-tender.json` | `093` | Amount = **8** |
| TC-001743 / TC-001780 (FSA) | `…step2.split-tender.json` | `093` | Amount = **8** |
| TC-001727 / TC-001764 | `…step2.reversal.json` | `088` | Approval 6 + Amount **8** |
| TC-002023 / TC-002038 | `…step2.completion.json` | `088` | Approval 6 + Amount **8** |
| TC-002024 / TC-002039 | `…step2.cancellation.json` | `088` | Approval 6 + Amount **8** |

| | |
|---|---|
| What happened | The TD correctly uses a runtime placeholder (e.g. `<NonfuelAmount from step 1 response>`). But the AI's own test case stores an 8-digit provisional value (e.g. `24677595` = $246,775.95, `83968206`), and the declared Segment 100 length was calculated from it. |
| How to verify (TC-001726 step 2) | Sum every other field and its Field Separator: 85 characters. 93 − 85 = **8** characters reserved for the amount. A real remainder of a $15.00 sale is 1–1499 (1–4 digits), so the length must be **086–089**. `093` can never be correct. |
| ATL105 rule | **E58, p.393:** *"A maximum length of 8 bytes is allowed for American Express cards only. A maximum length of **7 bytes** is allowed for all other card types."* **12.1 (p.223) / E84 (p.406):** length includes Segment Type and all Field Separators. |
| Fix | Provisional amounts must satisfy E58 (≤ 7 digits for MasterCard) and the business relation (remainder < original; reversal/cancellation = approved amount; completion ≤ authorized). Calculate the Segment Length **after** substitution. |

### D-06 – Totals Card Label is 2 bytes instead of 4 (Element 13)

| | |
|---|---|
| Where | All 18 Totals Request legs, `"Totals Data Segment" → "CardLabel"`: TC-001736, 1737, 1738, 1739, 1740, 1741, 1754, 1755, 1773, 1774, 1775, 1776, 1777, 1778, 1791, 1792, 2030, 2045 |
| Generated | `CC` |
| Expected | `CC  ` (CC + 2 spaces) |
| ATL105 rule | **E13, p.372:** *"Fixed length of four alphanumeric characters; left-aligned and space-filled"*. 12.6 Field 15 (p.235–237): Card Label, Len. 4, R. |
| Note | The declared Segment Length `108` reproduces only with a 4-byte Card Label plus a trailing Field Separator (see R-19). So the generator counted the padding but did not emit it. |

### D-08 – FSA Product Code segment length is wrong (Segment 102)

| | |
|---|---|
| Where | TC-001742, 1743, 1779, 1780 – `"Product Code Data Segment" → "SegmentLength"` |
| Generated | `033` |
| Expected | `029` |
| Calculation | `102`▲`029`▲ (8) + `F` (1) + `01` (2) + `894` `U` `01` `\` `21500` `\` `1500` ▲ (18) = **29** |
| ATL105 rule | **12.3, p.229:** *"A Field Separator always follows Segment Type and Segment Length … A Product Data Field Delimiter always follows Quantity and Unit Price … A Field Separator follows Product Amount when Product Amount is the very last element."* The same counting reproduces the **Appendix B example (p.505)** length `049` exactly. |
| Correct in these files | Product code 894 (QHP, App. F p.541), Table 012 = `M` (healthcare, p.550), product total = Segment 100 amount. |

### D-09 – Cashback test: chip data ignores the cash amount

| | |
|---|---|
| Where | TC-001747 (contact), TC-001784 (contactless) – objective "Cashback on MasterCard credit -> declined" |
| Generated | Segment 100: NonfuelAmount `1500` + CashAmount `500`. Chip: 9C `00`, 9F02 `000000001500`, 9F03 `000000000000` |
| Expected | 9C `09` (purchase with cashback), 9F02 `000000002000` (total including cash), 9F03 `000000000500` (cash) |
| Basis | SEG130-R-009 (chip values must agree with Segment 100); EMV Book 3 tag definitions. This is an EMV consistency rule, not an ATL105 field-format rule. |

### D-10 – Totals category sent with a zero count

| | |
|---|---|
| Where | TC-001737, TC-001774 (Purchase → Reversal → Settlement), Totals leg |
| Generated | CardLabel `CC`, CardTypeTotalCount `00000`, CardTypeTotalAmount `00000000` |
| ATL105 rule | **E16, p.375:** valid **`00001–99999`**. **12.6 (p.237):** Fields 15–17 *"are sent … only when data occurs for a card type."* |
| Fix | If the only purchase was reversed, omit the CC category and recompute the Segment Length. |

### M-01 – TC-001797: "Track 1" test carries Track 2 data

| | |
|---|---|
| Where | `TD-TC-001797.json`, Segment 100: `AccountNumber` `5159417392576181`, `CardDiscretionaryBlockData` `300510138259706` |
| Test objective | "Track 2 cannot be read, so the device sends **Track 1** and the purchase is approved." |
| ATL105 rule | 10.1.1.2 (p.81): *"If the device cannot read Track 2, then the device should send unaltered Track 1 before attempting manual entry."* E2 (p.364–365): Track 2 splits at `=`, Track 1 at the first `^`; combined ≤ 37 (Track 2) / 76 (Track 1). |
| Why wrong | Numeric Track 2 data (expiry `3005`, service code `101`), identical to TC-001796 except the Sequence Number. The TC request even carries `CARD_DATA_TRACK2`. |
| Fix | Regenerate with unaltered Track 1 (e.g. `B5159417392576181` + data after the first `^`), using a card whose Track 2 is unreadable. |

### M-02 – TC-001798: terminal described two different ways

| | |
|---|---|
| Where | `TD-TC-001798.json`, Segment 111: Table 005 = `908`, Table 031 = `12` |
| Test objective | Swipe at an RFID-equipped terminal with **no chip reader** |
| ATL105 rule | App. J (p.639): code `8` = Contactless Magnetic Stripe Read Capability (no chip); *"For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8."* App. I Table 031 (p.569): `07` = contactless magstripe, no chip; `12` = hybrid contact + contactless chip; *"Once certified, the respective terminal capability values do not change regardless of the POS Entry Mode."* |
| Why wrong | `908` says no chip; `12` says contact and contactless chip. Both cannot be true. The chip terminal `JX72948216050257001` was reused. |
| Fix | Keep `908`, set Table 031 to `07`, and use a separate RFID terminal ID. |

### M-03 – TC-001799 step 2 (split tender): amount and segment length

| | |
|---|---|
| Where | `TD-TC-001799.step2.split-tender.json`: `SegmentLength` `093`, `NonfuelAmount` `<RemainingBalance = …>` (TC provisional value `55105548`) |
| Why wrong | Same root cause as **D-07**: an 8-digit provisional amount (> 7-digit MasterCard max, and larger than the $15.00 sale). With a real remainder (1–4 digits) the length must be `086–089`. |
| ATL105 rule | E58 (p.393), 10.1.2.1 (p.84), E6 Approved Amount (p.368: 9 digits, zero-filled), 12.1 (p.223), E84 (p.406) |
| Fix | Remainder = 1500 − step-1 Approved Amount (leading zeros dropped). Recompute the length (e.g. approved `000001000` → `500` → `088`). |

### M-04 – TC-001800 step 2 (reversal): amount and segment length

| | |
|---|---|
| Where | `TD-TC-001800.step2.reversal.json`: `SegmentLength` `088`, `NonfuelAmount` `<NonfuelAmount from step 1 response>` (provisional `63096215`), `ApprovalNumber` `<ApprovalNumber from step 1 response>` (provisional `OFAKPT`) |
| Why wrong | Same root cause as **D-07**. 10.1.2.1 (p.84): *"The amount in a Reversal (Void) request must be the amount approved in the original transaction response when a transaction has been partially approved."* That amount is always < 1500, so the length must be `081–084`. |
| Fix | Reversal amount = step-1 Approved Amount (E6, leading zeros dropped); Approval Number = the exact 6 characters from step 1. Recompute the length. See also R-11 (placeholder wording). |

---

## 4b. Package and traceability defects (O31)

### O31-01 – Traceability matrix references a folder that is not in the delivery

| | |
|---|---|
| Where | `reports/traceability_matrix_full.json` (and the matching `TRACEABILITY.md` / `reports/traceability_matrix.md`) in every program folder of `31-10-out` |
| Generated | Each traced test case points to `qe_shaped_test_data/TD-TC-xxxxxx.json` with `"file_exists": true` (e.g. MC Credit TC-002065 → `qe_shaped_test_data/TD-TC-002065.json`) |
| Actual delivery | There is no `qe_shaped_test_data/` folder. The files are in `test_data/TD-TC-xxxxxx.json` (and multi-leg cases are split into `*.step1.*.json`, `*.step2.*.json`, …). |
| Count (re-verified) | **1,529** primary trace rows reference the missing path, and **all 1,529** `file_exists: true` flags are false in the delivered package. |
| Legs not traced | The matrix references only one TD file per TC. Follow-on legs are never traced: MC Credit has 839 TD files but only 408 are referenced (**431 never traced**). |

| Program | Trace rows with missing path (`file_exists: true`) | TD files delivered | TD files never traced |
|---|---|---|---|
| Mastercard Credit | 408 | 839 | 431 |
| American Express Credit | 374 | 790 | 416 |
| Mastercard Fleet | 249 | 569 | 320 |
| Interlink Debit | 249 | 536 | 287 |
| Maestro Direct Debit | 249 | 536 | 287 |
| **Total** | **1,529** | **3,270** | **1,741** |

| | |
|---|---|
| Why it matters | The matrix claims 100 % full-chain coverage, but none of its TD references resolve in the delivered package. Every request leg after step 1 is invisible to traceability. |
| How to verify | Open `mastercard_credit/reports/traceability_matrix_full.json`, find any `test_data` entry, and check that the `test_data_file` path exists relative to the program folder. |
| Fix | Rebuild every trace representation (JSON matrix, Markdown matrix, `TRACEABILITY.md`) from the **final delivered paths**. Emit one trace entry per request leg (step file). Compute `file_exists` against the delivered package, not the build workspace. |

### O31-03 – Obsolete artifacts in the Mastercard Credit package

| | |
|---|---|
| Current export | `test_cases/test_cases.jsonl` = **450 TCs**; `test_data/` = **839 TD files**; build `buypass-atl105-mastercard-credit-bada93f456` |
| Embedded archive | `BR_SCE_Test_cases.zip` → `test_cases/test_cases.jsonl` = **241 TCs**, build `buypass-atl105-mastercard-credit-65482e4498` (an older build; contains no TD files) |
| Top-level documentation | `mastercard_credit/README.md` describes the previous 241-TC delivery |
| Why it matters | Reviewers cannot tell which build is authoritative, and counts in the documentation do not match the delivered content. |
| How to verify | Count lines in `test_cases/test_cases.jsonl` (450) and in the zip's `test_cases/test_cases.jsonl` (241); compare the `build_id` values; read `README.md`. |
| Fix | Ship one authoritative build: remove (or clearly label as superseded) the old archive, regenerate `README.md` / `DELIVERY_CONTRACT.md` for the current build, and add a manifest (build ID, TC count, TD file count, file hashes) that matches the contents. |

## 4c. Medium-priority package-review topics

The package review reported five Medium-priority findings; they are detailed in that review's report. The topics are listed here so they are tracked in one place. Where this review has related evidence, it is cross-referenced. The package review distinguishes its review items from its confirmed defects, so classify each by that report.

| Topic | Related evidence in this document |
|---|---|
| Historical TC identity reuse | Not reproduced in the MC Credit package: the old (zip, 241 TCs) and current (450 TCs) builds share no TC IDs. See the package review report for the affected programs/runs. |
| Runtime amount / EMV dependencies | D-05, D-07, D-09, M-03, M-04, R-11 (runtime amounts, chip 9F02/9F03 built per leg) |
| Execution prerequisites | D-03, D-04 (client test cards / DUKPT key), R-02, R-03 (terminal ID), R-07 (partial-approval card), R-16, R-17 |
| Inline-versus-rendered consistency | Fields where the inline TC value and the rendered TD differ: runtime placeholders vs provisional TC values (D-07, M-03, M-04); padded length vs emitted value (D-01, D-06) |
| Coverage / source-applicability questions | R-01, R-04, R-06, R-08 (citations), R-12 (completion without EMV data), R-13 (contactless profile), and the 8 device-only T11 TCs without TD |

---

## 5. Review items (data may be acceptable – confirmation needed)

| ID | Item | Applies to | Reference (2026-4 PDF page) | Action / owner |
|---|---|---|---|---|
| R-01 | Table 005 cited as **p.548**; that was its page in 2026-3. In 2026-4 the layout is App. I p.555 and the codes are App. J p.638–639. | All TCs (EMV and magstripe) | App. I p.555; App. J p.638–639 | AI team: update citations |
| R-02 | Terminal Identifier `JX72948216050257001` is a placeholder (`MERCHANT_PLACEHOLDER`); State Code `72` = **Yukon, Canada** | All TCs | E102 p.417; App. D p.517 | Client/BUYPASS: supply the real terminal ID |
| R-03 | Test cases marked `ready` while the Terminal Identifier is still a placeholder | All `ready` TCs | E102 p.417 | AI team: do not mark `ready` until the real ID is supplied |
| R-04 | Quote "03 MasterCard - Authorizer Type: Credit" is rebuilt, not verbatim | All TCs | App. C p.511 (`03 / MasterCard® / Credit`) | AI team: quote the table row |
| R-05 | TC-001798 objective says "Swiped/keyed" but only the swiped leg exists | TC-001798 | 10.1.5 p.86; App. J p.638 | AI team: add a keyed leg (Table 005 `018`) or rename |
| R-06 | Split-tender quote taken from the healthcare section (10.1.6.1.3, p.87) | Split-tender TCs | 10.1.2.1 p.84 | AI team: cite p.84 |
| R-07 | Partial-approval tests need a card with balance < $15.00 (or a simulator trigger) | Partial-approval TCs | p.84; E83 p.404; E121 p.440 | Client: supply test card |
| R-08 | Approval Number link cites **p.85** (10.1.2.2 Authorization Only Reversal, type `S`); for Purchase Reversal (`8`) the basis is E5 | Purchase-reversal TCs (e.g. TC-001730, 1767, 1800) | E5 p.368; App. G p.546 | AI team: cite p.368 |
| R-09 | Partial Approval Indicator `1` sent on reversal legs; ATL105 does not say whether it belongs there | Reversal TCs | E121 p.440 | SME |
| R-10 | **INFO:** in follow-ons, Account Number and expiry are joined with `=` on the wire (e.g. `5199141082730948=2708`) | Follow-on legs | 10.1.8.2 p.94 | Wire builder |
| R-11 | Placeholder `<NonfuelAmount from step 1 response>`: the response has no Nonfuel Amount; the value comes from **Element 6 Approved Amount** (9 digits, zero-filled) | TC-001727, 1764, 1800, 2023, 2024, 2038, 2039 | 11.1.2 Response p.170; E6 p.368 | AI team: rename the placeholder; strip leading zeros at runtime |
| R-12 | Completion legs sent without Segment 130. ATL105 exempts only reversals from EMV data (p.161); completions are preauthorized (E5) | 14 completion legs | p.161; E5 p.368 | SME |
| R-13 | Contactless chip data identical to the contact template (no 9F6E/9F6D/9F7C; CVM = signature). App. R tag list is not exhaustive. | 61 contactless EMV legs | App. R p.697 | Validate against a MasterCard contactless kernel trace |
| R-14 | Exact E192 value / E118 framing for Table 001: App. T lists Table ID n3 and Table Length (n, fixed) but no width, so it could be `EMVYES` (`006`) or framed | All EMV TCs | App. T p.701 | BUYPASS/SME |
| R-15 | Packed-BCD 2-byte prefix (`0145`) inside E190 counted in E189 (`147`); matches the App. R example (`01\06`), but the field boundary is not stated | All EMV TCs | E189/E190 p.477–478; App. R p.697 | SME |
| R-16 | CA Public Key File Checksum `2012091700000100000102171` is a QE sample | All EMV TCs | E187 p.476 | Host CA key file |
| R-17 | Cryptogram / IAD / ATC / UN / TVR are placeholders; TVR byte 3 "online PIN entered" is set on non-PIN transactions | All EMV TCs | App. R; EMV Book 3 | Card simulator |
| R-18 | Totals Password (E65) empty: layout says C, but E65 says *"Password required for totals request"* | 18 Totals legs | E65 p.396; 12.6 p.235 | SME / merchant profile |
| R-19 | Totals Segment Length `108` matches only with a trailing Field Separator after Field 17, which 12.6 does not state | 18 Totals legs | 12.6 p.235–237 | SME |
| R-20 | Totals Hardware/Software/Firmware versions are synthesized (format OK) | 18 Totals legs | E39/E43/E96 | Real device versions |
| R-21 | `test_cases.jsonl` lines 3–4 (TC-001722, TC-001725) are malformed JSON (`{{` / missing `{`) | Catalog | – | AI team: fix the catalog file |

---

## 6. What is correct (passes in all reviewed test data)

| Area | Result |
|---|---|
| Message type per leg; E55 `ATL105`; E63 segment count (Totals `01`); message length ≤ 4,000 | PASS |
| Segment 100: all 17 fields present in spec order; Segment Length correct wherever no runtime placeholder is involved | PASS |
| E44 `0`; E86 unique per terminal (same on reversals, new on split tender / next transaction); E121 `1`; E5 empty on new purchases | PASS |
| E78 Prompt Code per leg: `0020`, `3020`, `7020`, `E020`, `8020`, `Z020`, `S020`, `U020`; Totals `990` | PASS |
| E2 PAN: Luhn, MasterCard IIN, ≤ 24; Track 2 combined ≤ 37; chip service code `2xx` | PASS (except M-01) |
| Segment 111: Table 005 `051` (contact), `071` (contactless), `901` (swipe); Table 031 `12`; Segment Length `026` | PASS (except M-02) |
| Segment 130: E187 N25; E189 = chip byte count (147); Segment Length consistent with the generated values | PASS (fix D-01/D-02 and recompute) |
| EMV chip TLV: well-formed; AID A0000000041010; no 5A/57; no duplicates; 5F2A/9F1A = 0840; 9C correct for purchase, auth, return (`20`), balance inquiry (`31`) | PASS (except D-05, D-09) |
| Follow-on legs: same card, expiry, entry mode, terminal as the original; Segment 130 correctly omitted on reversals | PASS |
| Test case `rule_checks` and `link_defs` (prompt code, entry mode, Table 031, segment presence, sequence / terminal / amount links) | PASS (runtime links = REVIEW) |

---

## 7. Generator fix checklist (to prevent repeats in the next run)

| # | Rule for the generator | Fixes |
|---|---|---|
| 1 | Pad fixed-length AN fields to their full width: E188 → 3 bytes (`001`), E13 → 4 bytes (`CC  `). Emit the same value that was used to calculate the Segment Length. | D-01, D-06 |
| 2 | Never synthesize free text for coded fields; apply the appendix allowed values (App. T: `EMVYES`/`EMVNOT`). Do not skip `valid_values_parsed`. Do not reuse one random value across test cases. | D-02 |
| 3 | Make the data match the test objective: expired card for T2; PIN block + online-PIN CVM for T3 online; offline-PIN CVM for T3 offline; Track 1 data for the Track 1 test. If client input is missing, mark the TC **blocked** instead of releasing default data. | D-03, D-04, D-04b, M-01 |
| 4 | Runtime amounts: derive from step-1 **Approved Amount (E6)**; any provisional value must satisfy E58 (≤ 7 digits for MasterCard) and the business relation (remainder < original; reversal = approved). Calculate the Segment Length **after** substitution. | D-07, M-03, M-04, R-11 |
| 5 | Chip data must be built per leg from that leg's Segment 100: 9F02 = total amount, 9F03 = cash, 9C from transaction type (00 / 09 cashback / 20 return / 30–31 balance inquiry). | D-05, D-09 |
| 6 | Calculate segment lengths with the exact separator rules of each segment (Seg 102: FS after type/length, `\` after quantity/price, FS/`\` after amount). | D-08 |
| 7 | Totals: send Card Type categories only when data occurs (count ≥ 00001). | D-10 |
| 8 | Keep Table 005 position 3 and Table 031 consistent for a terminal; use a separate terminal ID for different terminal capabilities. | M-02 |
| 9 | Cite 2026-4 page numbers and verbatim quotes. | R-01, R-04, R-06, R-08 |
| 10 | Build traceability from the final delivered paths, one entry per request leg; compute `file_exists` against the package. | O31-01 |
| 11 | Ship one authoritative build per program with a manifest (build ID, counts, hashes) and documentation generated from that build. | O31-03 |

---

## 8. Affected test cases by defect

| ID | Test cases |
|---|---|
| D-01, D-02 | All 104 EMV Contact/Contactless TCs with test data: 52 contact (35 purchase, 14 auth only, 2 return, 1 balance inquiry) + 52 contactless (same split). Excluded (no message, device-only T11): TC-001757, 001758, 001794, 001795, 002034, 002049, 002142, 002145 |
| D-03 | TC-001723, TC-001761 |
| D-04 | TC-001724, TC-001762, TC-002021, TC-002037 |
| D-04b | TC-001725, TC-002022 |
| D-05 | TC-001726, TC-001743, TC-001763, TC-001780 |
| D-06 | TC-001736, 001737, 001738, 001739, 001740, 001741, 001754, 001755, 001773, 001774, 001775, 001776, 001777, 001778, 001791, 001792, 002030, 002045 |
| D-07 | TC-001726, 001727, 001743, 001763, 001764, 001780, 002023, 002024, 002038, 002039 |
| D-08 | TC-001742, 001743, 001779, 001780 |
| D-09 | TC-001747, TC-001784 |
| D-10 | TC-001737, TC-001774 |
| M-01 | TC-001797 |
| M-02 | TC-001798 |
| M-03 | TC-001799 |
| M-04 | TC-001800 |
| O31-01 | All traced TCs in all 5 programs (1,529 rows); all follow-on leg files (1,741, of which 431 in MC Credit) |
| O31-02 | = D-01 at full-delivery scope: 402 TCs / 500 files (see the D-01 table) |
| O31-03 | Mastercard Credit package (`BR_SCE_Test_cases.zip`, `README.md`) |

### How to re-run the EMV validation after fixes

From `regression-artifact-validator` (the ID file lists the test case IDs to validate):

```powershell
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.canonical.TestDataSpecFieldValidationRunner' "-Dexec.args=<test_data dir> <test_cases.jsonl> Result_MD_Folder\EMV_Full_Validation @<file with TC IDs>"
```
