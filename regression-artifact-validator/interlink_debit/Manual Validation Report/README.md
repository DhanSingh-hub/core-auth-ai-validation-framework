# Interlink Debit – Manual Validation Report (ATL105 test data, by transaction category and entry mode)

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – Interlink Debit – Purchase/Capture, Authorization Only, Merchandise Return |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `interlink_debit/test_data` |
| Test case catalog | `interlink_debit/test_cases/test_cases.jsonl` (278 test cases in 21 scenarios, BR1_TS1 to BR1_TS21) |
| Scope | All 278 test cases: 249 with test data (536 test data files, 63 of them Totals Requests) and 29 device-only test cases (no host message; ATL105 citations checked) |
| Date | 2026-11-04 |
| Overall result | **249 FAIL**, **29 REVIEW** |

Each scenario folder (`<transaction category>/<entry mode>/`) holds a findings summary (`<entry mode>_Findings.md`, sections 1–8 as in the model report `EMVcontact_contactless_findings.md`) and one field-by-field report per test case.

**For the development team:** [Consolidated_Defect_Report.md](./Consolidated_Defect_Report.md) – every defect field by field across all scenarios (actual vs expected per test data file, ATL105 reference, how to confirm it by hand, what to fix) – and [Consolidated_Defect_Sheet.xlsx](./Consolidated_Defect_Sheet.xlsx), the same findings as filterable sheets (defects, one row per field finding, test cases, review items).

## Results by scenario

| Scenario | Transaction category | Entry mode | Table 005 | Test cases | FAIL | BLOCKED | REVIEW | Defects | Findings report |
|---|---|---|---|---|---|---|---|---|---|
| BR1_TS1 (valid) | Purchase/Capture | EMV Contact Chip | `05` | 29 | 27 | 0 | 2 | D-01, D-02, D-12, D-14, D-15, D-16, D-18, D-19, D-20, D-21, D-24 | [EMV_Contact_Chip_Findings.md](./Purchase_Capture/EMV_Contact_Chip/EMV_Contact_Chip_Findings.md) |
| BR1_TS2 (valid) | Purchase/Capture | EMV Contactless Chip | `07`, `86` | 30 | 28 | 0 | 2 | D-01, D-02, D-12, D-14, D-15, D-16, D-18, D-19, D-20, D-21, D-24 | [EMV_Contactless_Chip_Findings.md](./Purchase_Capture/EMV_Contactless_Chip/EMV_Contactless_Chip_Findings.md) |
| BR1_TS3 (unconfirmed) | Purchase/Capture | Magnetic Stripe | `90` | 30 | 27 | 0 | 3 | D-14, D-15, D-16, D-19, D-20, D-21, D-24 | [Magnetic_Stripe_Findings.md](./Purchase_Capture/Magnetic_Stripe/Magnetic_Stripe_Findings.md) |
| BR1_TS4 (unconfirmed) | Purchase/Capture | Contactless Magnetic Stripe | `91` | 29 | 27 | 0 | 2 | D-14, D-15, D-16, D-19, D-20, D-21, D-24 | [Contactless_Magstripe_Findings.md](./Purchase_Capture/Contactless_Magstripe/Contactless_Magstripe_Findings.md) |
| BR1_TS5 (unconfirmed) | Purchase/Capture | Chip Fallback | `79`, `80` | 30 | 28 | 0 | 2 | D-14, D-15, D-16, D-17, D-19, D-20, D-21, D-24 | [Chip_Fallback_Findings.md](./Purchase_Capture/Chip_Fallback/Chip_Fallback_Findings.md) |
| BR1_TS6 (unconfirmed) | Purchase/Capture | Credential on File | `10` | 29 | 27 | 0 | 2 | D-09, D-12, D-14, D-15, D-16, D-19, D-20, D-21, D-24 | [Credential_on_File_Findings.md](./Purchase_Capture/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS7 (unconfirmed) | Purchase/Capture | Contactless Mobile Commerce Device | `82` | 29 | 27 | 0 | 2 | D-14, D-15, D-16, D-19, D-20, D-21, D-24 | [Contactless_Mobile_Findings.md](./Purchase_Capture/Contactless_Mobile/Contactless_Mobile_Findings.md) |
| BR1_TS8 (valid) | Authorization Only | EMV Contact Chip | `05` | 7 | 6 | 0 | 1 | D-01, D-02, D-12, D-15, D-16, D-19, D-20, D-21 | [EMV_Contact_Chip_Findings.md](./Auth_Only/EMV_Contact_Chip/EMV_Contact_Chip_Findings.md) |
| BR1_TS9 (valid) | Authorization Only | EMV Contactless Chip | `07`, `86` | 8 | 7 | 0 | 1 | D-01, D-02, D-12, D-15, D-16, D-19, D-20, D-21 | [EMV_Contactless_Chip_Findings.md](./Auth_Only/EMV_Contactless_Chip/EMV_Contactless_Chip_Findings.md) |
| BR1_TS10 (unconfirmed) | Authorization Only | Magnetic Stripe | `90` | 7 | 6 | 0 | 1 | D-15, D-16, D-19, D-20, D-21 | [Magnetic_Stripe_Findings.md](./Auth_Only/Magnetic_Stripe/Magnetic_Stripe_Findings.md) |
| BR1_TS11 (unconfirmed) | Authorization Only | Contactless Magnetic Stripe | `91` | 7 | 6 | 0 | 1 | D-15, D-16, D-19, D-20, D-21 | [Contactless_Magstripe_Findings.md](./Auth_Only/Contactless_Magstripe/Contactless_Magstripe_Findings.md) |
| BR1_TS12 (unconfirmed) | Authorization Only | Chip Fallback | `79`, `80` | 8 | 7 | 0 | 1 | D-15, D-16, D-17, D-19, D-20, D-21 | [Chip_Fallback_Findings.md](./Auth_Only/Chip_Fallback/Chip_Fallback_Findings.md) |
| BR1_TS13 (unconfirmed) | Authorization Only | Credential on File | `10` | 7 | 6 | 0 | 1 | D-09, D-12, D-15, D-16, D-19, D-20, D-21 | [Credential_on_File_Findings.md](./Auth_Only/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS14 (unconfirmed) | Authorization Only | Contactless Mobile Commerce Device | `82` | 7 | 6 | 0 | 1 | D-15, D-16, D-19, D-20, D-21 | [Contactless_Mobile_Findings.md](./Auth_Only/Contactless_Mobile/Contactless_Mobile_Findings.md) |
| BR1_TS15 (valid) | Merchandise Return | EMV Contact Chip | `05` | 3 | 2 | 0 | 1 | D-01, D-02, D-12, D-15, D-16 | [EMV_Contact_Chip_Findings.md](./Return/EMV_Contact_Chip/EMV_Contact_Chip_Findings.md) |
| BR1_TS16 (valid) | Merchandise Return | EMV Contactless Chip | `07` | 3 | 2 | 0 | 1 | D-01, D-02, D-12, D-15, D-16 | [EMV_Contactless_Chip_Findings.md](./Return/EMV_Contactless_Chip/EMV_Contactless_Chip_Findings.md) |
| BR1_TS17 (unconfirmed) | Merchandise Return | Magnetic Stripe | `90` | 3 | 2 | 0 | 1 | D-15, D-16 | [Magnetic_Stripe_Findings.md](./Return/Magnetic_Stripe/Magnetic_Stripe_Findings.md) |
| BR1_TS18 (unconfirmed) | Merchandise Return | Contactless Magnetic Stripe | `91` | 3 | 2 | 0 | 1 | D-15, D-16 | [Contactless_Magstripe_Findings.md](./Return/Contactless_Magstripe/Contactless_Magstripe_Findings.md) |
| BR1_TS19 (unconfirmed) | Merchandise Return | Chip Fallback | `79` | 3 | 2 | 0 | 1 | D-15, D-16, D-17 | [Chip_Fallback_Findings.md](./Return/Chip_Fallback/Chip_Fallback_Findings.md) |
| BR1_TS20 (unconfirmed) | Merchandise Return | Credential on File | `10` | 3 | 2 | 0 | 1 | D-09, D-12, D-15, D-16 | [Credential_on_File_Findings.md](./Return/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS21 (unconfirmed) | Merchandise Return | Contactless Mobile Commerce Device | `82` | 3 | 2 | 0 | 1 | D-15, D-16 | [Contactless_Mobile_Findings.md](./Return/Contactless_Mobile/Contactless_Mobile_Findings.md) |

## Defect catalog

Defect IDs are the same in every findings report.

| Defect | What is wrong | Scenarios | Test cases |
|---|---|---|---|
| **D-01** | EMV Card Sequence Number (Element 188) has the wrong width | TS1, TS2, TS8, TS9, TS15, TS16 | 72 |
| **D-02** | EMV Additional Information (Element 118) does not contain EMV Table Data | TS1, TS2, TS8, TS9, TS15, TS16 | 72 |
| **D-09** | Credential on File indicators (Tables 032, 049, 056) are missing | TS6, TS13, TS20 | 35 |
| **D-12** | Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions | TS1, TS2, TS6, TS8, TS9, TS13, TS15, TS16, TS20 | 107 |
| **D-14** | Follow-on step (completion, cancellation, reversal): amount data built from an impossible value | TS1, TS2, TS3, TS4, TS5, TS6, TS7 | 7 |
| **D-15** | The card data (Account Number, Card Discretionary Block Data) is empty | TS1, TS2, TS3, TS4, TS5, TS6, TS7, TS8, TS9, TS10, TS11, TS12, TS13, TS14, TS15, TS16, TS17, TS18, TS19, TS20, TS21 | 249 |
| **D-16** | The debit PIN block (Element 33) is empty | TS1, TS2, TS3, TS4, TS5, TS6, TS7, TS8, TS9, TS10, TS11, TS12, TS13, TS14, TS15, TS16, TS17, TS18, TS19, TS20, TS21 | 242 |
| **D-17** | A keyed (manual) entry mode is used for a debit card | TS5, TS12, TS19 | 34 |
| **D-18** | Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back | TS1, TS2 | 4 |
| **D-19** | Totals Request: Card Label (Element 13) is not 4 characters | TS1, TS2, TS3, TS4, TS5, TS6, TS7, TS8, TS9, TS10, TS11, TS12, TS13, TS14 | 63 |
| **D-20** | Totals Request: Segment 105 length is counted with the wrong layout | TS1, TS2, TS3, TS4, TS5, TS6, TS7, TS8, TS9, TS10, TS11, TS12, TS13, TS14 | 63 |
| **D-21** | Totals Request: the totals do not match the transactions of the flow | TS1, TS2, TS3, TS4, TS5, TS6, TS7, TS8, TS9, TS10, TS11, TS12, TS13, TS14 | 14 |
| **D-24** | A test data value differs from the test case it was generated from | TS1, TS2, TS3, TS4, TS5, TS6, TS7 | 14 |

## Which test cases were validated

Every test case of the catalog. Test cases with a host message: every test data file of every step, field by field, including the Totals Request of the T8 settlement test cases (Segment 105 and the totals of the flow). T11 device-only test cases send no message to BUYPASS, so there is no test data; their ATL105 citations are checked and their device procedure is listed in their report.

| Scenario | Test cases by type | Test data files | Device-only |
|---|---|---|---|
| BR1_TS1 Purchase/Capture – EMV Contact Chip | T1 ×2, T2 ×1, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2 | 60 | TC-002909, TC-002910 |
| BR1_TS2 Purchase/Capture – EMV Contactless Chip | T1 ×2, T2 ×2, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2 | 61 | TC-002939, TC-002940 |
| BR1_TS3 Purchase/Capture – Magnetic Stripe | T1 ×2, T2 ×1, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×3 | 60 | TC-002968, TC-002969, TC-002975 |
| BR1_TS4 Purchase/Capture – Contactless Magnetic Stripe | T1 ×2, T2 ×1, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2 | 60 | TC-002998, TC-002999 |
| BR1_TS5 Purchase/Capture – Chip Fallback | T1 ×2, T2 ×2, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2 | 61 | TC-003028, TC-003029 |
| BR1_TS6 Purchase/Capture – Credential on File | T1 ×2, T2 ×1, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2 | 60 | TC-003057, TC-003058 |
| BR1_TS7 Purchase/Capture – Contactless Mobile Commerce Device | T1 ×2, T2 ×1, T4 ×2, T5 ×3, T6 ×8, T8 ×8, T10 ×3, T11 ×2 | 60 | TC-003086, TC-003087 |
| BR1_TS8 Authorization Only – EMV Contact Chip | T1 ×1, T5 ×3, T8 ×1, T10 ×1, T11 ×1 | 13 | TC-003097 |
| BR1_TS9 Authorization Only – EMV Contactless Chip | T1 ×1, T2 ×1, T5 ×3, T8 ×1, T10 ×1, T11 ×1 | 14 | TC-003105 |
| BR1_TS10 Authorization Only – Magnetic Stripe | T1 ×1, T5 ×3, T8 ×1, T10 ×1, T11 ×1 | 13 | TC-003112 |
| BR1_TS11 Authorization Only – Contactless Magnetic Stripe | T1 ×1, T5 ×3, T8 ×1, T10 ×1, T11 ×1 | 13 | TC-003119 |
| BR1_TS12 Authorization Only – Chip Fallback | T1 ×1, T2 ×1, T5 ×3, T8 ×1, T10 ×1, T11 ×1 | 14 | TC-003127 |
| BR1_TS13 Authorization Only – Credential on File | T1 ×1, T5 ×3, T8 ×1, T10 ×1, T11 ×1 | 13 | TC-003134 |
| BR1_TS14 Authorization Only – Contactless Mobile Commerce Device | T1 ×1, T5 ×3, T8 ×1, T10 ×1, T11 ×1 | 13 | TC-003141 |
| BR1_TS15 Merchandise Return – EMV Contact Chip | T1 ×1, T5 ×1, T11 ×1 | 3 | TC-003146 |
| BR1_TS16 Merchandise Return – EMV Contactless Chip | T1 ×1, T5 ×1, T11 ×1 | 3 | TC-003149 |
| BR1_TS17 Merchandise Return – Magnetic Stripe | T1 ×1, T5 ×1, T11 ×1 | 3 | TC-003152 |
| BR1_TS18 Merchandise Return – Contactless Magnetic Stripe | T1 ×1, T5 ×1, T11 ×1 | 3 | TC-003155 |
| BR1_TS19 Merchandise Return – Chip Fallback | T1 ×1, T5 ×1, T11 ×1 | 3 | TC-003158 |
| BR1_TS20 Merchandise Return – Credential on File | T1 ×1, T5 ×1, T11 ×1 | 3 | TC-003161 |
| BR1_TS21 Merchandise Return – Contactless Mobile Commerce Device | T1 ×1, T5 ×1, T11 ×1 | 3 | TC-003164 |

## Folder layout

```text
Manual Validation Report/
  README.md
  Consolidated_Defect_Report.md     all defects, field by field (for the development team)
  Consolidated_Defect_Sheet.xlsx    the same findings as filterable sheets
  Purchase_Capture/
    EMV_Contact_Chip/
      EMV_Contact_Chip_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (29 reports, one per test case)
    EMV_Contactless_Chip/
      EMV_Contactless_Chip_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (30 reports, one per test case)
    Magnetic_Stripe/
      Magnetic_Stripe_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (30 reports, one per test case)
    Contactless_Magstripe/
      Contactless_Magstripe_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (29 reports, one per test case)
    Chip_Fallback/
      Chip_Fallback_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (30 reports, one per test case)
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (29 reports, one per test case)
    Contactless_Mobile/
      Contactless_Mobile_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (29 reports, one per test case)
  Auth_Only/
    EMV_Contact_Chip/
      EMV_Contact_Chip_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (7 reports, one per test case)
    EMV_Contactless_Chip/
      EMV_Contactless_Chip_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (8 reports, one per test case)
    Magnetic_Stripe/
      Magnetic_Stripe_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (7 reports, one per test case)
    Contactless_Magstripe/
      Contactless_Magstripe_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (7 reports, one per test case)
    Chip_Fallback/
      Chip_Fallback_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (8 reports, one per test case)
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (7 reports, one per test case)
    Contactless_Mobile/
      Contactless_Mobile_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (7 reports, one per test case)
  Return/
    EMV_Contact_Chip/
      EMV_Contact_Chip_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (3 reports, one per test case)
    EMV_Contactless_Chip/
      EMV_Contactless_Chip_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (3 reports, one per test case)
    Magnetic_Stripe/
      Magnetic_Stripe_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (3 reports, one per test case)
    Contactless_Magstripe/
      Contactless_Magstripe_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (3 reports, one per test case)
    Chip_Fallback/
      Chip_Fallback_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (3 reports, one per test case)
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (3 reports, one per test case)
    Contactless_Mobile/
      Contactless_Mobile_Findings.md
      TC-xxxxxx_ATL105_Field_Validation.md   (3 reports, one per test case)
```

## Page numbers: 2026-4 versus 2026-3

All pages here are PDF pages of the 2026-4 edition. The model report used the 2026-3 edition, so the same text is on a different page. Both columns were found by searching the text of each edition:

| Spec text | 2026-3 edition | 2026-4 edition (this report) |
|---|---|---|
| Element 188 EMV Card Sequence Number | p.470 | p.477 |
| Element 191 EMV Additional Information Indicator | p.472 | p.479 |
| Element 192 EMV Additional Information Length | p.473 | p.480 |
| Appendix T-1 EMV Table Data | p.694 | p.701 |
| Element 58 Nonfuel Amount | p.386 | p.393 |
| Section 10.14.2.1 Expiration Date (EMV) | p.157 | p.160 |
| Section 12.20 EMV Request Data Segment | p.267 | p.270 |
| Section 2.3 Time-out reversal | p.37 | p.39 |
| Section 10.1.1.2 Track Support (follow-up transactions) | p.79 | p.81 |
| Section 10.1.2.2 Authorization Only Reversal | p.83 | p.85 |
| Element 86 Sequence Number (preauthorized completion) | p.402 | p.409 |
| Appendix I Table 030 POS Condition Code | p.561 | p.568 |
| Appendix I Table 032 sub-table 08 Credential on File indicator | p.568 | p.575 |
| Appendix J entry mode 10 Credential on file | p.631 | p.638 |
| Section 10.2.1.3 PIN Entry (debit) | p.94 | p.96 |
| Section 10.2.1.4 Manual Entry (debit) | p.94 | p.96 |
| Section 12.6 Totals Data Segment | p.232 | p.235 |
| Element 13 Card Label | p.365 | p.372 |
| Section 9.2 Local Totals | p.70 | p.72 |

## Re-run

From `regression-artifact-validator`:

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py --data "interlink_debit" --all
```

The builder converts the test case records, runs `scripts/validate-td-against-atl105-spec.py` on their test data and re-verifies every quoted ATL105 text against `specifications/ATL105/docs/specs/extracted_text_new.txt` (the text of the 2026-4 PDF).

