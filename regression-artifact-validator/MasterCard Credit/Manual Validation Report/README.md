# MasterCard Credit – Manual Validation Report (ATL105 test data, by transaction category and entry mode)

| Item | Value |
|---|---|
| Product / program | BUYPASS ATL105 – MasterCard Credit – Purchase/Capture, Mail/Phone Purchase, Authorization Only, Mail/Phone Authorization Only, Merchandise Return, Balance Inquiry |
| Specification | BUYPASS Platform ATL105 Message Format Specifications **2026-4** (October 09, 2026) – `specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf` |
| Test data under review | `MasterCard Credit/mastercard_credit/test_data` |
| Test case catalog | `MasterCard Credit/mastercard_credit/test_cases/test_cases.jsonl` (450 test cases in 36 scenarios, BR1_TS1 to BR1_TS36) |
| Scope | 118 test cases (up to 5 per scenario) and 185 test data files |
| Date | 2026-11-03 |
| Overall result | **72 FAIL**, **18 BLOCKED**, **28 REVIEW** |

Each scenario folder (`<transaction category>/<entry mode>/`) holds a findings summary (`<entry mode>_Findings.md`, sections 1–8 as in the model report `EMVcontact_contactless_findings.md`) and one field-by-field report per test case.

**For the development team:** [Consolidated_Defect_Report.md](./Consolidated_Defect_Report.md) – every defect field by field across all scenarios (actual vs expected per test data file, ATL105 reference, how to confirm it by hand, what to fix).

## Results by scenario

| Scenario | Transaction category | Entry mode | Table 005 | Test cases | FAIL | BLOCKED | REVIEW | Defects | Findings report |
|---|---|---|---|---|---|---|---|---|---|
| BR1_TS1 (valid) | Purchase/Capture | EMV Contact Chip | `05` | TC-001722, TC-001723, TC-001724, TC-001726, TC-001730 | 5 | 0 | 0 | D-01, D-02, D-03, D-04, D-05, D-12 | [EMV_Contact_Chip_Findings.md](./Purchase_Capture/EMV_Contact_Chip/EMV_Contact_Chip_Findings.md) |
| BR1_TS2 (valid) | Purchase/Capture | EMV Contactless Chip | `07` | TC-001759, TC-001761, TC-001762, TC-001763, TC-001767 | 5 | 0 | 0 | D-01, D-02, D-03, D-04, D-05, D-12 | [EMV_Contactless_Chip_Findings.md](./Purchase_Capture/EMV_Contactless_Chip/EMV_Contactless_Chip_Findings.md) |
| BR1_TS3 (valid) | Purchase/Capture | Magnetic Stripe | `90` | TC-001796, TC-001797, TC-001798, TC-001799, TC-001803 | 3 | 1 | 1 | D-05, D-06, D-07 | [Magnetic_Stripe_Findings.md](./Purchase_Capture/Magnetic_Stripe/Magnetic_Stripe_Findings.md) |
| BR1_TS4 (valid) | Purchase/Capture | Contactless Magnetic Stripe | `91` | TC-001834, TC-001835, TC-001839, TC-001840, TC-001851 | 2 | 1 | 2 | D-05, D-13 | [Contactless_Magstripe_Findings.md](./Purchase_Capture/Contactless_Magstripe/Contactless_Magstripe_Findings.md) |
| BR1_TS5 (valid) | Purchase/Capture | Manual/Keyed | `01` | TC-001867, TC-001868, TC-001869, TC-001870, TC-001874 | 2 | 1 | 2 | D-05, D-06 | [Manual_Keyed_Findings.md](./Purchase_Capture/Manual_Keyed/Manual_Keyed_Findings.md) |
| BR1_TS6 (valid) | Purchase/Capture | Chip Fallback | `79`, `80` | TC-001906, TC-001907, TC-001908, TC-001912, TC-001929 | 4 | 0 | 1 | D-05, D-08 | [Chip_Fallback_Findings.md](./Purchase_Capture/Chip_Fallback/Chip_Fallback_Findings.md) |
| BR1_TS7 (valid) | Purchase/Capture | Credential on File | `10`, `01` | TC-001941, TC-001942, TC-001943, TC-001948, TC-001959 | 5 | 0 | 0 | D-09, D-10, D-12 | [Credential_on_File_Findings.md](./Purchase_Capture/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS8 (unconfirmed) | Purchase/Capture | Contactless Mobile Commerce Device | `82` | TC-001976, TC-001977, TC-001981, TC-001982, TC-001993 | 2 | 1 | 2 | D-05, D-13 | [Contactless_Mobile_Findings.md](./Purchase_Capture/Contactless_Mobile/Contactless_Mobile_Findings.md) |
| BR1_TS9 (valid) | Mail/Phone Purchase | Manual/Keyed | `01` | TC-002008, TC-002009, TC-002010, TC-002011, TC-002013 | 1 | 1 | 3 | D-10 | [Manual_Keyed_Findings.md](./Mail_Phone_Purchase/Manual_Keyed/Manual_Keyed_Findings.md) |
| BR1_TS10 (unconfirmed) | Mail/Phone Purchase | Credential on File | `10` | TC-002014, TC-002015, TC-002016, TC-002017, TC-002019 | 5 | 0 | 0 | D-09, D-10, D-12 | [Credential_on_File_Findings.md](./Mail_Phone_Purchase/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS11 (valid) | Authorization Only | EMV Contact Chip | `05` | TC-002020, TC-002021, TC-002022, TC-002023, TC-002025 | 5 | 0 | 0 | D-01, D-02, D-04, D-11, D-12, D-14 | [EMV_Contact_Chip_Findings.md](./Auth_Only/EMV_Contact_Chip/EMV_Contact_Chip_Findings.md) |
| BR1_TS12 (valid) | Authorization Only | EMV Contactless Chip | `07`, `86` | TC-002035, TC-002036, TC-002037, TC-002038, TC-002040 | 5 | 0 | 0 | D-01, D-02, D-04, D-12, D-14 | [EMV_Contactless_Chip_Findings.md](./Auth_Only/EMV_Contactless_Chip/EMV_Contactless_Chip_Findings.md) |
| BR1_TS13 (valid) | Authorization Only | Magnetic Stripe | `90` | TC-002050, TC-002051, TC-002052, TC-002054, TC-002058 | 2 | 2 | 1 | D-07, D-14 | [Magnetic_Stripe_Findings.md](./Auth_Only/Magnetic_Stripe/Magnetic_Stripe_Findings.md) |
| BR1_TS14 (valid) | Authorization Only | Contactless Magnetic Stripe | `91` | TC-002065, TC-002066, TC-002068, TC-002072, TC-002074 | 1 | 2 | 2 | D-14 | [Contactless_Magstripe_Findings.md](./Auth_Only/Contactless_Magstripe/Contactless_Magstripe_Findings.md) |
| BR1_TS15 (valid) | Authorization Only | Manual/Keyed | `01` | TC-002079, TC-002080, TC-002081, TC-002083, TC-002087 | 1 | 2 | 2 | D-14 | [Manual_Keyed_Findings.md](./Auth_Only/Manual_Keyed/Manual_Keyed_Findings.md) |
| BR1_TS16 (valid) | Authorization Only | Chip Fallback | `79`, `80` | TC-002094, TC-002095, TC-002096, TC-002098, TC-002107 | 4 | 0 | 1 | D-08, D-14 | [Chip_Fallback_Findings.md](./Auth_Only/Chip_Fallback/Chip_Fallback_Findings.md) |
| BR1_TS17 (valid) | Authorization Only | Credential on File | `10` | TC-002110, TC-002111, TC-002112, TC-002115, TC-002117 | 5 | 0 | 0 | D-09, D-12 | [Credential_on_File_Findings.md](./Auth_Only/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS18 (unconfirmed) | Authorization Only | Contactless Mobile Commerce Device | `82` | TC-002121, TC-002122, TC-002124, TC-002128, TC-002130 | 1 | 2 | 2 | D-14 | [Contactless_Mobile_Findings.md](./Auth_Only/Contactless_Mobile/Contactless_Mobile_Findings.md) |
| BR1_TS19 (valid) | Mail/Phone Authorization Only | Manual/Keyed | `01` | TC-002134, TC-002135 | 0 | 1 | 1 | – | [Manual_Keyed_Findings.md](./Mail_Phone_Auth/Manual_Keyed/Manual_Keyed_Findings.md) |
| BR1_TS20 (unconfirmed) | Mail/Phone Authorization Only | Credential on File | `10` | TC-002137, TC-002138 | 2 | 0 | 0 | D-09, D-12 | [Credential_on_File_Findings.md](./Mail_Phone_Auth/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS21 (valid) | Merchandise Return | EMV Contact Chip | `05` | TC-002140, TC-002141 | 2 | 0 | 0 | D-01, D-02, D-12 | [EMV_Contact_Chip_Findings.md](./Return/EMV_Contact_Chip/EMV_Contact_Chip_Findings.md) |
| BR1_TS22 (valid) | Merchandise Return | EMV Contactless Chip | `07` | TC-002143, TC-002144 | 2 | 0 | 0 | D-01, D-02, D-12 | [EMV_Contactless_Chip_Findings.md](./Return/EMV_Contactless_Chip/EMV_Contactless_Chip_Findings.md) |
| BR1_TS23 (spec_implied) | Merchandise Return | Magnetic Stripe | `90` | TC-002146, TC-002147 | 0 | 1 | 1 | – | [Magnetic_Stripe_Findings.md](./Return/Magnetic_Stripe/Magnetic_Stripe_Findings.md) |
| BR1_TS24 (valid) | Merchandise Return | Contactless Magnetic Stripe | `91` | TC-002149, TC-002150 | 0 | 1 | 1 | – | [Contactless_Magstripe_Findings.md](./Return/Contactless_Magstripe/Contactless_Magstripe_Findings.md) |
| BR1_TS25 (valid) | Merchandise Return | Manual/Keyed | `01` | TC-002152, TC-002153 | 0 | 1 | 1 | – | [Manual_Keyed_Findings.md](./Return/Manual_Keyed/Manual_Keyed_Findings.md) |
| BR1_TS26 (valid) | Merchandise Return | Chip Fallback | `79` | TC-002155, TC-002156 | 2 | 0 | 0 | D-08 | [Chip_Fallback_Findings.md](./Return/Chip_Fallback/Chip_Fallback_Findings.md) |
| BR1_TS27 (unconfirmed) | Merchandise Return | Credential on File | `10` | TC-002158, TC-002159 | 2 | 0 | 0 | D-09, D-12 | [Credential_on_File_Findings.md](./Return/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS28 (unconfirmed) | Merchandise Return | Contactless Mobile Commerce Device | `82` | TC-002161, TC-002162 | 0 | 1 | 1 | – | [Contactless_Mobile_Findings.md](./Return/Contactless_Mobile/Contactless_Mobile_Findings.md) |
| BR1_TS29 (valid) | Balance Inquiry | EMV Contact Chip | `05` | TC-002164 | 1 | 0 | 0 | D-01, D-02, D-12 | [EMV_Contact_Chip_Findings.md](./Balance_Inquiry/EMV_Contact_Chip/EMV_Contact_Chip_Findings.md) |
| BR1_TS30 (valid) | Balance Inquiry | EMV Contactless Chip | `07` | TC-002165 | 1 | 0 | 0 | D-01, D-02, D-12 | [EMV_Contactless_Chip_Findings.md](./Balance_Inquiry/EMV_Contactless_Chip/EMV_Contactless_Chip_Findings.md) |
| BR1_TS31 (spec_implied) | Balance Inquiry | Magnetic Stripe | `90` | TC-002166 | 0 | 0 | 1 | – | [Magnetic_Stripe_Findings.md](./Balance_Inquiry/Magnetic_Stripe/Magnetic_Stripe_Findings.md) |
| BR1_TS32 (valid) | Balance Inquiry | Contactless Magnetic Stripe | `91` | TC-002167 | 0 | 0 | 1 | – | [Contactless_Magstripe_Findings.md](./Balance_Inquiry/Contactless_Magstripe/Contactless_Magstripe_Findings.md) |
| BR1_TS33 (valid) | Balance Inquiry | Manual/Keyed | `01` | TC-002168 | 0 | 0 | 1 | – | [Manual_Keyed_Findings.md](./Balance_Inquiry/Manual_Keyed/Manual_Keyed_Findings.md) |
| BR1_TS34 (valid) | Balance Inquiry | Chip Fallback | `79` | TC-002169 | 1 | 0 | 0 | D-08 | [Chip_Fallback_Findings.md](./Balance_Inquiry/Chip_Fallback/Chip_Fallback_Findings.md) |
| BR1_TS35 (unconfirmed) | Balance Inquiry | Credential on File | `10` | TC-002170 | 1 | 0 | 0 | D-09, D-12 | [Credential_on_File_Findings.md](./Balance_Inquiry/Credential_on_File/Credential_on_File_Findings.md) |
| BR1_TS36 (unconfirmed) | Balance Inquiry | Contactless Mobile Commerce Device | `82` | TC-002171 | 0 | 0 | 1 | – | [Contactless_Mobile_Findings.md](./Balance_Inquiry/Contactless_Mobile/Contactless_Mobile_Findings.md) |

## Defect catalog

Defect IDs are the same in every findings report.

| Defect | What is wrong | Scenarios | Test cases |
|---|---|---|---|
| **D-01** | EMV Card Sequence Number (Element 188) has the wrong width | TS1, TS2, TS11, TS12, TS21, TS22, TS29, TS30 | 26: TC-001722, TC-001723, TC-001724, TC-001726, TC-001730, TC-001759, TC-001761, TC-001762, TC-001763, TC-001767, TC-002020, TC-002021, TC-002022, TC-002023, TC-002025, TC-002035, TC-002036, TC-002037, TC-002038, TC-002040, TC-002140, TC-002141, TC-002143, TC-002144, TC-002164, TC-002165 |
| **D-02** | EMV Additional Information (Element 118) does not contain EMV Table Data | TS1, TS2, TS11, TS12, TS21, TS22, TS29, TS30 | 26: TC-001722, TC-001723, TC-001724, TC-001726, TC-001730, TC-001759, TC-001761, TC-001762, TC-001763, TC-001767, TC-002020, TC-002021, TC-002022, TC-002023, TC-002025, TC-002035, TC-002036, TC-002037, TC-002038, TC-002040, TC-002140, TC-002141, TC-002143, TC-002144, TC-002164, TC-002165 |
| **D-03** | Expired-card test data uses a card that is not expired | TS1, TS2 | 2: TC-001723, TC-001761 |
| **D-04** | Online-PIN test data carries no PIN block and a signature CVM | TS1, TS2, TS11, TS12 | 4: TC-001724, TC-001762, TC-002021, TC-002037 |
| **D-05** | Split tender: the second card's amount data was built from an impossible value | TS1, TS2, TS3, TS4, TS5, TS6, TS8 | 7: TC-001726, TC-001763, TC-001799, TC-001835, TC-001870, TC-001908, TC-001977 |
| **D-06** | The terminal is described two different ways in the same message (Table 005 vs Table 031) | TS3, TS5 | 2: TC-001798, TC-001869 |
| **D-07** | The Track 1 test sends Track 2 data | TS3, TS13 | 2: TC-001797, TC-002051 |
| **D-08** | Track data is sent with an entry mode that does not read the card | TS6, TS16, TS26, TS34 | 11: TC-001906, TC-001907, TC-001908, TC-001912, TC-002094, TC-002095, TC-002096, TC-002098, TC-002155, TC-002156, TC-002169 |
| **D-09** | Credential on File indicators (Tables 032, 049, 056) are missing | TS7, TS10, TS17, TS20, TS27, TS35 | 20: TC-001941, TC-001942, TC-001943, TC-001948, TC-001959, TC-002014, TC-002015, TC-002016, TC-002017, TC-002019, TC-002110, TC-002111, TC-002112, TC-002115, TC-002117, TC-002137, TC-002138, TC-002158, TC-002159, TC-002170 |
| **D-10** | The AVS test sends no address data (Table 003) | TS7, TS9, TS10 | 3: TC-001959, TC-002011, TC-002017 |
| **D-11** | Offline-PIN test data shows a signature CVM | TS11 | 1: TC-002022 |
| **D-12** | Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions | TS1, TS2, TS7, TS10, TS11, TS12, TS17, TS20, TS21, TS22, TS27, TS29, TS30, TS35 | 46: TC-001722, TC-001723, TC-001724, TC-001726, TC-001730, TC-001759, TC-001761, TC-001762, TC-001763, TC-001767, TC-001941, TC-001942, TC-001943, TC-001948, TC-001959, TC-002014, TC-002015, TC-002016, TC-002017, TC-002019, TC-002020, TC-002021, TC-002022, TC-002023, TC-002025, TC-002035, TC-002036, TC-002037, TC-002038, TC-002040, TC-002110, TC-002111, TC-002112, TC-002115, TC-002117, TC-002137, TC-002138, TC-002140, TC-002141, TC-002143, TC-002144, TC-002158, TC-002159, TC-002164, TC-002165, TC-002170 |
| **D-13** | A follow-on message (time-out reversal) carries full track data | TS4, TS8 | 2: TC-001851, TC-001993 |
| **D-14** | Follow-on step (completion, cancellation, reversal): amount data built from an impossible value | TS11, TS12, TS13, TS14, TS15, TS16, TS18 | 7: TC-002023, TC-002038, TC-002052, TC-002066, TC-002081, TC-002096, TC-002122 |

## Changes since the first report (Purchase/Capture, 4 entry modes)

- The four entry-mode folders of the first report now sit under `Purchase_Capture/`, next to the other Purchase/Capture entry modes. Their test cases are unchanged (EMV Contact Chip: TC-001722, TC-001723, TC-001724, TC-001726, TC-001730; EMV Contactless Chip: TC-001759, TC-001761, TC-001762, TC-001763, TC-001767; Magnetic Stripe: TC-001796, TC-001797, TC-001798, TC-001799, TC-001803; Manual/Keyed: TC-001867, TC-001868, TC-001869, TC-001870, TC-001874).
- Rules D-08 to D-14 were added for the new scenarios (keyed fallback, Credential on File, AVS, offline PIN, follow-on messages).
- One new rule also applies to the first report: **D-12 Table 030 missing** on every EMV request (TC-001722, TC-001723, TC-001724, TC-001726, TC-001730, TC-001759, TC-001761, TC-001762, TC-001763, TC-001767). Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), p.568 (printed Appendix I-19): *"Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."*

## How the test cases were chosen

Only test cases that send a financial host message can be validated against the ATL105 message layouts. Not sampled: T8 settlement tests (they send only a Totals Request), T11 device-only tests and blocked spec-gap test cases (no message is generated for them). From the remaining test cases of each scenario, up to 5 were taken in this fixed order: the first T1 (base transaction), the first two T2/T3 (tests specific to the entry mode), the first T4 and T5 (two-step flows: split tender, completion, cancellation, reversal), then T7 (stored credential), T9 (AVS/FSA), T6 (time-out reversal) and T10 (edge cases) and, while still fewer than 5, further test cases in catalog order. Purchase/Capture TS1, TS2, TS3 and TS5 keep the test cases of the first report.

| Scenario | Selected test cases (catalog line) | Test cases in the scenario | Not sampled |
|---|---|---|---|
| BR1_TS1 Purchase/Capture – EMV Contact Chip | TC-001722 (line 1) – T1 – Purchase approved<br>TC-001723 (line 2) – T2 – Expired EMV card → sent online → host decides<br>TC-001724 (line 3) – T3 – EMV online PIN → approved<br>TC-001726 (line 5) – T4 – Partial approval → remainder on 2nd card (split tender)<br>TC-001730 (line 9) – T5 – Purchase → Purchase Reversal | 37 | 22 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS2 Purchase/Capture – EMV Contactless Chip | TC-001759 (line 38) – T1 – Purchase approved<br>TC-001761 (line 40) – T2 – Expired EMV card → sent online → host decides<br>TC-001762 (line 41) – T3 – EMV online PIN → approved<br>TC-001763 (line 42) – T4 – Partial approval → remainder on 2nd card (split tender)<br>TC-001767 (line 46) – T5 – Purchase → Purchase Reversal | 37 | 22 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS3 Purchase/Capture – Magnetic Stripe | TC-001796 (line 75) – T1 – Purchase approved<br>TC-001797 (line 76) – T2 – Track 2 unreadable → Track 1 sent → approved<br>TC-001798 (line 77) – T2 – Swiped/keyed at RFID-equipped terminal → approved<br>TC-001799 (line 78) – T4 – Partial approval → remainder on 2nd card (split tender)<br>TC-001803 (line 82) – T5 – Purchase → Purchase Reversal | 38 | 22 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>3 T11 device-only (no host message) |
| BR1_TS4 Purchase/Capture – Contactless Magnetic Stripe | TC-001834 (line 113) – T1 – Purchase approved<br>TC-001835 (line 114) – T4 – Partial approval → remainder on 2nd card (split tender)<br>TC-001839 (line 118) – T5 – Purchase → Purchase Reversal<br>TC-001840 (line 119) – T6 – Purchase, no response → Time-out Reversal<br>TC-001851 (line 130) – T10 – Time-out Reversal with no original → declined (unmatched) | 33 | 17 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>3 T11 device-only (no host message) |
| BR1_TS5 Purchase/Capture – Manual/Keyed | TC-001867 (line 146) – T1 – Purchase approved<br>TC-001868 (line 147) – T2 – 3 failed swipes → keyed entry → approved<br>TC-001869 (line 148) – T2 – Swiped/keyed at RFID-equipped terminal → approved<br>TC-001870 (line 149) – T4 – Partial approval → remainder on 2nd card (split tender)<br>TC-001874 (line 153) – T5 – Purchase → Purchase Reversal | 39 | 21 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>5 T11 device-only (no host message) |
| BR1_TS6 Purchase/Capture – Chip Fallback | TC-001906 (line 185) – T1 – Purchase approved<br>TC-001907 (line 186) – T2 – Chip unreadable → keyed fallback (79) → approved<br>TC-001908 (line 187) – T4 – Partial approval → remainder on 2nd card (split tender)<br>TC-001912 (line 191) – T5 – Purchase → Purchase Reversal<br>TC-001929 (line 208) – T2 – Chip unreadable → swiped fallback (80) → approved | 35 | 20 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS7 Purchase/Capture – Credential on File | TC-001941 (line 220) – T1 – Purchase approved<br>TC-001942 (line 221) – T5 – Purchase → Purchase Reversal<br>TC-001943 (line 222) – T6 – Purchase, no response → Time-out Reversal<br>TC-001948 (line 227) – T7 – Store card (cardholder-initiated) → repeat cardholder-initiated purchase<br>TC-001959 (line 238) – T9 – Purchase with AVS mismatch → approved | 35 | 20 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS8 Purchase/Capture – Contactless Mobile Commerce Device | TC-001976 (line 255) – T1 – Purchase approved<br>TC-001977 (line 256) – T4 – Partial approval → remainder on 2nd card (split tender)<br>TC-001981 (line 260) – T5 – Purchase → Purchase Reversal<br>TC-001982 (line 261) – T6 – Purchase, no response → Time-out Reversal<br>TC-001993 (line 272) – T10 – Time-out Reversal with no original → declined (unmatched) | 32 | 17 further host-message test cases (sample limit 5)<br>8 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS9 Mail/Phone Purchase – Manual/Keyed | TC-002008 (line 287) – T1 – Mail/Phone Purchase approved<br>TC-002009 (line 288) – T5 – Mail/Phone Purchase → Mail/Phone Reversal<br>TC-002010 (line 289) – T6 – Mail/Phone Purchase, no response → Time-out Reversal<br>TC-002011 (line 290) – T9 – Mail/Phone Purchase with AVS mismatch → approved<br>TC-002013 (line 292) – T10 – Partial requested on Mail/Phone Purchase → not partially approved | 6 | 1 further host-message test cases (sample limit 5) |
| BR1_TS10 Mail/Phone Purchase – Credential on File | TC-002014 (line 293) – T1 – Mail/Phone Purchase approved<br>TC-002015 (line 294) – T5 – Mail/Phone Purchase → Mail/Phone Reversal<br>TC-002016 (line 295) – T6 – Mail/Phone Purchase, no response → Time-out Reversal<br>TC-002017 (line 296) – T9 – Mail/Phone Purchase with AVS mismatch → approved<br>TC-002019 (line 298) – T10 – Partial requested on Mail/Phone Purchase → not partially approved | 6 | 1 further host-message test cases (sample limit 5) |
| BR1_TS11 Authorization Only – EMV Contact Chip | TC-002020 (line 299) – T1 – Authorization Only approved<br>TC-002021 (line 300) – T3 – EMV online PIN → approved<br>TC-002022 (line 301) – T3 – EMV offline PIN → approved<br>TC-002023 (line 302) – T4 – Partial authorization → Completion for approved amount<br>TC-002025 (line 304) – T5 – Authorization → Completion | 15 | 8 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>1 T11 device-only (no host message) |
| BR1_TS12 Authorization Only – EMV Contactless Chip | TC-002035 (line 314) – T1 – Authorization Only approved<br>TC-002036 (line 315) – T2 – Contactless chip → contact chip (86) → approved<br>TC-002037 (line 316) – T3 – EMV online PIN → approved<br>TC-002038 (line 317) – T4 – Partial authorization → Completion for approved amount<br>TC-002040 (line 319) – T5 – Authorization → Completion | 15 | 8 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>1 T11 device-only (no host message) |
| BR1_TS13 Authorization Only – Magnetic Stripe | TC-002050 (line 329) – T1 – Authorization Only approved<br>TC-002051 (line 330) – T2 – Track 2 unreadable → Track 1 sent → approved<br>TC-002052 (line 331) – T4 – Partial authorization → Completion for approved amount<br>TC-002054 (line 333) – T5 – Authorization → Completion<br>TC-002058 (line 337) – T6 – Completion timed out → Time-out Reversal → Completion resubmitted | 15 | 7 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS14 Authorization Only – Contactless Magnetic Stripe | TC-002065 (line 344) – T1 – Authorization Only approved<br>TC-002066 (line 345) – T4 – Partial authorization → Completion for approved amount<br>TC-002068 (line 347) – T5 – Authorization → Completion<br>TC-002072 (line 351) – T6 – Completion timed out → Time-out Reversal → Completion resubmitted<br>TC-002074 (line 353) – T10 – Authorization released with Time-out Reversal → declined | 14 | 6 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS15 Authorization Only – Manual/Keyed | TC-002079 (line 358) – T1 – Authorization Only approved<br>TC-002080 (line 359) – T2 – 3 failed swipes → keyed entry → approved<br>TC-002081 (line 360) – T4 – Partial authorization → Completion for approved amount<br>TC-002083 (line 362) – T5 – Authorization → Completion<br>TC-002087 (line 366) – T6 – Completion timed out → Time-out Reversal → Completion resubmitted | 15 | 7 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>2 T11 device-only (no host message) |
| BR1_TS16 Authorization Only – Chip Fallback | TC-002094 (line 373) – T1 – Authorization Only approved<br>TC-002095 (line 374) – T2 – Chip unreadable → keyed fallback (79) → approved<br>TC-002096 (line 375) – T4 – Partial authorization → Completion for approved amount<br>TC-002098 (line 377) – T5 – Authorization → Completion<br>TC-002107 (line 386) – T2 – Chip unreadable → swiped fallback (80) → approved | 16 | 9 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>1 T11 device-only (no host message) |
| BR1_TS17 Authorization Only – Credential on File | TC-002110 (line 389) – T1 – Authorization Only approved<br>TC-002111 (line 390) – T5 – Authorization → Completion<br>TC-002112 (line 391) – T5 – Pre-authorization → Completion for a different final amount<br>TC-002115 (line 394) – T6 – Completion timed out → Time-out Reversal → Completion resubmitted<br>TC-002117 (line 396) – T10 – Authorization released with Time-out Reversal → declined | 11 | 4 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>1 T11 device-only (no host message) |
| BR1_TS18 Authorization Only – Contactless Mobile Commerce Device | TC-002121 (line 400) – T1 – Authorization Only approved<br>TC-002122 (line 401) – T4 – Partial authorization → Completion for approved amount<br>TC-002124 (line 403) – T5 – Authorization → Completion<br>TC-002128 (line 407) – T6 – Completion timed out → Time-out Reversal → Completion resubmitted<br>TC-002130 (line 409) – T10 – Authorization released with Time-out Reversal → declined | 13 | 6 further host-message test cases (sample limit 5)<br>1 T8 settlement (Totals Request only)<br>1 T11 device-only (no host message) |
| BR1_TS19 Mail/Phone Authorization Only – Manual/Keyed | TC-002134 (line 413) – T1 – Mail/Phone Authorization Only approved<br>TC-002135 (line 414) – T5 – Mail/Phone Authorization → Cancellation | 3 | 1 blocked spec-gap (no message generated) |
| BR1_TS20 Mail/Phone Authorization Only – Credential on File | TC-002137 (line 416) – T1 – Mail/Phone Authorization Only approved<br>TC-002138 (line 417) – T5 – Mail/Phone Authorization → Cancellation | 3 | 1 blocked spec-gap (no message generated) |
| BR1_TS21 Merchandise Return – EMV Contact Chip | TC-002140 (line 419) – T1 – Merchandise Return processed<br>TC-002141 (line 420) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS22 Merchandise Return – EMV Contactless Chip | TC-002143 (line 422) – T1 – Merchandise Return processed<br>TC-002144 (line 423) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS23 Merchandise Return – Magnetic Stripe | TC-002146 (line 425) – T1 – Merchandise Return processed<br>TC-002147 (line 426) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS24 Merchandise Return – Contactless Magnetic Stripe | TC-002149 (line 428) – T1 – Merchandise Return processed<br>TC-002150 (line 429) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS25 Merchandise Return – Manual/Keyed | TC-002152 (line 431) – T1 – Merchandise Return processed<br>TC-002153 (line 432) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS26 Merchandise Return – Chip Fallback | TC-002155 (line 434) – T1 – Merchandise Return processed<br>TC-002156 (line 435) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS27 Merchandise Return – Credential on File | TC-002158 (line 437) – T1 – Merchandise Return processed<br>TC-002159 (line 438) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS28 Merchandise Return – Contactless Mobile Commerce Device | TC-002161 (line 440) – T1 – Merchandise Return processed<br>TC-002162 (line 441) – T5 – Merchandise Return → Void of a Merchandise Return | 3 | 1 T11 device-only (no host message) |
| BR1_TS29 Balance Inquiry – EMV Contact Chip | TC-002164 (line 443) – T1 – Balance Inquiry answered | 1 | – |
| BR1_TS30 Balance Inquiry – EMV Contactless Chip | TC-002165 (line 444) – T1 – Balance Inquiry answered | 1 | – |
| BR1_TS31 Balance Inquiry – Magnetic Stripe | TC-002166 (line 445) – T1 – Balance Inquiry answered | 1 | – |
| BR1_TS32 Balance Inquiry – Contactless Magnetic Stripe | TC-002167 (line 446) – T1 – Balance Inquiry answered | 1 | – |
| BR1_TS33 Balance Inquiry – Manual/Keyed | TC-002168 (line 447) – T1 – Balance Inquiry answered | 1 | – |
| BR1_TS34 Balance Inquiry – Chip Fallback | TC-002169 (line 448) – T1 – Balance Inquiry answered | 1 | – |
| BR1_TS35 Balance Inquiry – Credential on File | TC-002170 (line 449) – T1 – Balance Inquiry answered | 1 | – |
| BR1_TS36 Balance Inquiry – Contactless Mobile Commerce Device | TC-002171 (line 450) – T1 – Balance Inquiry answered | 1 | – |

## Folder layout

```text
Manual Validation Report/
  README.md
  Consolidated_Defect_Report.md     all defects, field by field (for the development team)
  Purchase_Capture/
    EMV_Contact_Chip/
      EMV_Contact_Chip_Findings.md
      TC-001722_ATL105_Field_Validation.md
      TC-001723_ATL105_Field_Validation.md
      TC-001724_ATL105_Field_Validation.md
      TC-001726_ATL105_Field_Validation.md
      TC-001730_ATL105_Field_Validation.md
    EMV_Contactless_Chip/
      EMV_Contactless_Chip_Findings.md
      TC-001759_ATL105_Field_Validation.md
      TC-001761_ATL105_Field_Validation.md
      TC-001762_ATL105_Field_Validation.md
      TC-001763_ATL105_Field_Validation.md
      TC-001767_ATL105_Field_Validation.md
    Magnetic_Stripe/
      Magnetic_Stripe_Findings.md
      TC-001796_ATL105_Field_Validation.md
      TC-001797_ATL105_Field_Validation.md
      TC-001798_ATL105_Field_Validation.md
      TC-001799_ATL105_Field_Validation.md
      TC-001803_ATL105_Field_Validation.md
    Contactless_Magstripe/
      Contactless_Magstripe_Findings.md
      TC-001834_ATL105_Field_Validation.md
      TC-001835_ATL105_Field_Validation.md
      TC-001839_ATL105_Field_Validation.md
      TC-001840_ATL105_Field_Validation.md
      TC-001851_ATL105_Field_Validation.md
    Manual_Keyed/
      Manual_Keyed_Findings.md
      TC-001867_ATL105_Field_Validation.md
      TC-001868_ATL105_Field_Validation.md
      TC-001869_ATL105_Field_Validation.md
      TC-001870_ATL105_Field_Validation.md
      TC-001874_ATL105_Field_Validation.md
    Chip_Fallback/
      Chip_Fallback_Findings.md
      TC-001906_ATL105_Field_Validation.md
      TC-001907_ATL105_Field_Validation.md
      TC-001908_ATL105_Field_Validation.md
      TC-001912_ATL105_Field_Validation.md
      TC-001929_ATL105_Field_Validation.md
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-001941_ATL105_Field_Validation.md
      TC-001942_ATL105_Field_Validation.md
      TC-001943_ATL105_Field_Validation.md
      TC-001948_ATL105_Field_Validation.md
      TC-001959_ATL105_Field_Validation.md
    Contactless_Mobile/
      Contactless_Mobile_Findings.md
      TC-001976_ATL105_Field_Validation.md
      TC-001977_ATL105_Field_Validation.md
      TC-001981_ATL105_Field_Validation.md
      TC-001982_ATL105_Field_Validation.md
      TC-001993_ATL105_Field_Validation.md
  Mail_Phone_Purchase/
    Manual_Keyed/
      Manual_Keyed_Findings.md
      TC-002008_ATL105_Field_Validation.md
      TC-002009_ATL105_Field_Validation.md
      TC-002010_ATL105_Field_Validation.md
      TC-002011_ATL105_Field_Validation.md
      TC-002013_ATL105_Field_Validation.md
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-002014_ATL105_Field_Validation.md
      TC-002015_ATL105_Field_Validation.md
      TC-002016_ATL105_Field_Validation.md
      TC-002017_ATL105_Field_Validation.md
      TC-002019_ATL105_Field_Validation.md
  Auth_Only/
    EMV_Contact_Chip/
      EMV_Contact_Chip_Findings.md
      TC-002020_ATL105_Field_Validation.md
      TC-002021_ATL105_Field_Validation.md
      TC-002022_ATL105_Field_Validation.md
      TC-002023_ATL105_Field_Validation.md
      TC-002025_ATL105_Field_Validation.md
    EMV_Contactless_Chip/
      EMV_Contactless_Chip_Findings.md
      TC-002035_ATL105_Field_Validation.md
      TC-002036_ATL105_Field_Validation.md
      TC-002037_ATL105_Field_Validation.md
      TC-002038_ATL105_Field_Validation.md
      TC-002040_ATL105_Field_Validation.md
    Magnetic_Stripe/
      Magnetic_Stripe_Findings.md
      TC-002050_ATL105_Field_Validation.md
      TC-002051_ATL105_Field_Validation.md
      TC-002052_ATL105_Field_Validation.md
      TC-002054_ATL105_Field_Validation.md
      TC-002058_ATL105_Field_Validation.md
    Contactless_Magstripe/
      Contactless_Magstripe_Findings.md
      TC-002065_ATL105_Field_Validation.md
      TC-002066_ATL105_Field_Validation.md
      TC-002068_ATL105_Field_Validation.md
      TC-002072_ATL105_Field_Validation.md
      TC-002074_ATL105_Field_Validation.md
    Manual_Keyed/
      Manual_Keyed_Findings.md
      TC-002079_ATL105_Field_Validation.md
      TC-002080_ATL105_Field_Validation.md
      TC-002081_ATL105_Field_Validation.md
      TC-002083_ATL105_Field_Validation.md
      TC-002087_ATL105_Field_Validation.md
    Chip_Fallback/
      Chip_Fallback_Findings.md
      TC-002094_ATL105_Field_Validation.md
      TC-002095_ATL105_Field_Validation.md
      TC-002096_ATL105_Field_Validation.md
      TC-002098_ATL105_Field_Validation.md
      TC-002107_ATL105_Field_Validation.md
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-002110_ATL105_Field_Validation.md
      TC-002111_ATL105_Field_Validation.md
      TC-002112_ATL105_Field_Validation.md
      TC-002115_ATL105_Field_Validation.md
      TC-002117_ATL105_Field_Validation.md
    Contactless_Mobile/
      Contactless_Mobile_Findings.md
      TC-002121_ATL105_Field_Validation.md
      TC-002122_ATL105_Field_Validation.md
      TC-002124_ATL105_Field_Validation.md
      TC-002128_ATL105_Field_Validation.md
      TC-002130_ATL105_Field_Validation.md
  Mail_Phone_Auth/
    Manual_Keyed/
      Manual_Keyed_Findings.md
      TC-002134_ATL105_Field_Validation.md
      TC-002135_ATL105_Field_Validation.md
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-002137_ATL105_Field_Validation.md
      TC-002138_ATL105_Field_Validation.md
  Return/
    EMV_Contact_Chip/
      EMV_Contact_Chip_Findings.md
      TC-002140_ATL105_Field_Validation.md
      TC-002141_ATL105_Field_Validation.md
    EMV_Contactless_Chip/
      EMV_Contactless_Chip_Findings.md
      TC-002143_ATL105_Field_Validation.md
      TC-002144_ATL105_Field_Validation.md
    Magnetic_Stripe/
      Magnetic_Stripe_Findings.md
      TC-002146_ATL105_Field_Validation.md
      TC-002147_ATL105_Field_Validation.md
    Contactless_Magstripe/
      Contactless_Magstripe_Findings.md
      TC-002149_ATL105_Field_Validation.md
      TC-002150_ATL105_Field_Validation.md
    Manual_Keyed/
      Manual_Keyed_Findings.md
      TC-002152_ATL105_Field_Validation.md
      TC-002153_ATL105_Field_Validation.md
    Chip_Fallback/
      Chip_Fallback_Findings.md
      TC-002155_ATL105_Field_Validation.md
      TC-002156_ATL105_Field_Validation.md
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-002158_ATL105_Field_Validation.md
      TC-002159_ATL105_Field_Validation.md
    Contactless_Mobile/
      Contactless_Mobile_Findings.md
      TC-002161_ATL105_Field_Validation.md
      TC-002162_ATL105_Field_Validation.md
  Balance_Inquiry/
    EMV_Contact_Chip/
      EMV_Contact_Chip_Findings.md
      TC-002164_ATL105_Field_Validation.md
    EMV_Contactless_Chip/
      EMV_Contactless_Chip_Findings.md
      TC-002165_ATL105_Field_Validation.md
    Magnetic_Stripe/
      Magnetic_Stripe_Findings.md
      TC-002166_ATL105_Field_Validation.md
    Contactless_Magstripe/
      Contactless_Magstripe_Findings.md
      TC-002167_ATL105_Field_Validation.md
    Manual_Keyed/
      Manual_Keyed_Findings.md
      TC-002168_ATL105_Field_Validation.md
    Chip_Fallback/
      Chip_Fallback_Findings.md
      TC-002169_ATL105_Field_Validation.md
    Credential_on_File/
      Credential_on_File_Findings.md
      TC-002170_ATL105_Field_Validation.md
    Contactless_Mobile/
      Contactless_Mobile_Findings.md
      TC-002171_ATL105_Field_Validation.md
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

## Re-run

From `regression-artifact-validator`:

```powershell
$env:PYTHONUTF8 = '1'
python scripts/build-entry-mode-validation-report.py
```

The builder converts the selected test case records, runs `scripts/validate-td-against-atl105-spec.py` on their test data and re-verifies every quoted ATL105 text against `specifications/ATL105/docs/specs/extracted_text_new.txt` (the text of the 2026-4 PDF).

