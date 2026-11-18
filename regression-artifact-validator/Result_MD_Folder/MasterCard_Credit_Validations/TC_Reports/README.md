# ATL105 Test Data Field Validation - Result Index

- Test data folder: `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output\mastercard_credit_new - Copy (2)\test_data`
- Test case catalog: `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output\mastercard_credit_new - Copy (2)\test_cases\test_cases.jsonl`
- Specification: BUYPASS ATL105 Message Format Specifications 2026-3 (specifications/ATL105/docs/specs)
- Generated: 2026-11-16T17:02:39.6798945

## Coverage

| Measure | Value |
|---|---|
| Test cases in scope | 35 |
| Test cases with test data (validated field by field) | 35 (100.0%) |
| Test cases without test data (device-only / not generated) | 0 |
| Test data files validated | 48 |
| Result: FAIL / PASS_WITH_REVIEW / PASS | 25 / 10 / 0 |

## Results by entry mode and transaction category

| Entry mode | Category | TCs | FAIL | PASS_WITH_REVIEW | PASS / N/A |
|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | 8 | 7 | 1 | 0 |
| EMV_CONTACTLESS | PURCHASE | 2 | 2 | 0 | 0 |
| MAGSTRIPE | PURCHASE | 3 | 0 | 3 | 0 |
| CONTACTLESS_MAGSTRIPE | PURCHASE | 1 | 0 | 1 | 0 |
| MANUAL | PURCHASE | 1 | 0 | 1 | 0 |
| EMV_FALLBACK | PURCHASE | 1 | 0 | 1 | 0 |
| CREDENTIAL_ON_FILE | PURCHASE | 2 | 1 | 1 | 0 |
| CONTACTLESS_MOBILE | PURCHASE | 1 | 0 | 1 | 0 |
| EMV_CONTACT | AUTH_ONLY | 11 | 11 | 0 | 0 |
| EMV_CONTACT | RETURN | 2 | 2 | 0 | 0 |
| CREDENTIAL_ON_FILE | AUTH_ONLY | 1 | 1 | 0 | 0 |
| MANUAL | MOTO_PURCHASE | 1 | 1 | 0 | 0 |
| CREDENTIAL_ON_FILE | BALANCE_INQUIRY | 1 | 0 | 1 | 0 |

## Defect frequency (FAIL checks, grouped)

| # | Area | Field | Element | Rule | TCs affected | % of TCs with TD | Occurrences |
|---|---|---|---|---|---|---|---|
| 1 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-TABLE-ID-MATCH | 22 | 62.9% | 23 |
| 2 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-EMV-TABLE-DATA | 22 | 62.9% | 23 |
| 3 | Seg 100 | CardDiscretionaryBlockData.Expiry | E12 | E12-KEYED-EXPIRY-MMYY | 2 | 5.7% | 2 |
| 4 | Flow | Expiry = original expiry | E12 | FOLLOW-ON-SAME-EXPIRY | 2 | 5.7% | 2 |
| 5 | Seg 100 | EncryptedPINBlockData | E33 | SEG100-E33-FORMAT | 2 | 5.7% | 2 |
| 6 | Seg 100 | CardDiscretionaryBlockData.Expiry | E12 | TRACK2-EXPIRY-YYMM | 2 | 5.7% | 2 |
| 7 | Seg 130 | Tag 9F02 vs NonfuelAmount | E190/58 | SEG130-R-009 | 1 | 2.9% | 1 |
| 8 | Flow | Step 2 9F02 = step 2 amount | E190 | SPLIT-TENDER-9F02 | 1 | 2.9% | 1 |
| 9 | Seg 111 | VariableInformationLength | E112 | SEG111-E112 | 1 | 2.9% | 1 |
| 10 | Seg 111 | VariableInformation | E113 | APPI-T003-R001 | 1 | 2.9% | 1 |
| 11 | Seg 111 | SegmentLength (value) | E84 | E84-SEG111-LENGTH | 1 | 2.9% | 1 |
| 12 | Message | Loyalty Card Data Segment |  | 11.8.1-SECTION3 | 1 | 2.9% | 1 |
| 13 | Seg 100 | PromptCode.TransactionType | E78 | APPG-TXN-TYPE | 1 | 2.9% | 1 |
| 14 | Seg 102 | ServiceLevel | E87 | SEG102-E87 | 1 | 2.9% | 1 |
| 15 | Entry mode | Table 005 vs Element 12 content | E113/12 | APPJ-READ-HAS-TRACK | 1 | 2.9% | 1 |
| 16 | Segment presence | EMV Request Data Segment (130) |  | SEG130-R-017/R-001 | 1 | 2.9% | 1 |

## Per test case

| Entry mode | Category | TC | Display | Template | Objective | Legs | PASS | FAIL | REVIEW | N/A | Overall | Report | Key defects |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | TC-020705 | BR1_TS1_TC7 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 127 | 2 | 10 | 10 | **FAIL** | [TC-020705_ATL105_Field_Validation.md](./TC-020705_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACTLESS | PURCHASE | TC-020729 | BR1_TS2_TC5 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 127 | 2 | 11 | 10 | **FAIL** | [TC-020729_ATL105_Field_Validation.md](./TC-020729_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | PURCHASE | TC-020753 | BR1_TS3_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 85 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-020753_ATL105_Field_Validation.md](./TC-020753_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-020779 | BR1_TS4_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 85 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-020779_ATL105_Field_Validation.md](./TC-020779_ATL105_Field_Validation.md) |  |
| MANUAL | PURCHASE | TC-020803 | BR1_TS5_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 83 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-020803_ATL105_Field_Validation.md](./TC-020803_ATL105_Field_Validation.md) |  |
| EMV_FALLBACK | PURCHASE | TC-020827 | BR1_TS6_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 84 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-020827_ATL105_Field_Validation.md](./TC-020827_ATL105_Field_Validation.md) |  |
| CREDENTIAL_ON_FILE | PURCHASE | TC-020851 | BR1_TS7_TC2 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 107 | 0 | 6 | 9 | **PASS_WITH_REVIEW** | [TC-020851_ATL105_Field_Validation.md](./TC-020851_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MOBILE | PURCHASE | TC-020876 | BR1_TS8_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 84 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-020876_ATL105_Field_Validation.md](./TC-020876_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | PURCHASE | TC-023308 | BR1_TS1_TC14 | G-VF-35e1056df6 | Valid flow: Purchase/Capture -> Purchase Reversal | 2 | 214 | 2 | 17 | 17 | **FAIL** | [TC-023308_ATL105_Field_Validation.md](./TC-023308_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | PURCHASE | TC-023346 | BR1_TS3_TC8 | G-VF-35e1056df6 | Valid flow: Purchase/Capture -> Purchase Reversal | 2 | 171 | 0 | 9 | 17 | **PASS_WITH_REVIEW** | [TC-023346_ATL105_Field_Validation.md](./TC-023346_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | PURCHASE | TC-023309 | BR1_TS1_TC15 | G-VF-5883641281 | Valid flow: Purchase/Capture -> Time-out Reversal | 2 | 215 | 2 | 12 | 18 | **FAIL** | [TC-023309_ATL105_Field_Validation.md](./TC-023309_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-023509 | BR1_TS11_TC73 | G-VF-86d03977d5 | Valid flow: Authorization Only -> Cancellation | 2 | 212 | 2 | 17 | 18 | **FAIL** | [TC-023509_ATL105_Field_Validation.md](./TC-023509_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | RETURN | TC-023987 | BR1_TS21_TC4 | G-VF-5702611ba2 | Valid flow: Merchandise Return -> Void of a Merchandise Return | 2 | 213 | 2 | 16 | 18 | **FAIL** | [TC-023987_ATL105_Field_Validation.md](./TC-023987_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-023510 | BR1_TS11_TC74 | G-VF-9ba75bd7e4 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | 2 | 253 | 6 | 25 | 21 | **FAIL** | [TC-023510_ATL105_Field_Validation.md](./TC-023510_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle); E190/58 Tag 9F02 vs NonfuelAmount; E190 Step 2 9F02 = step 2 amount |
| EMV_CONTACT | PURCHASE | TC-023317 | BR1_TS1_TC23 | G-RT-4000a95dae | Running total / state: Purchase/Capture approved -> Totals Request | 2 | 169 | 2 | 12 | 11 | **FAIL** | [TC-023317_ATL105_Field_Validation.md](./TC-023317_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-023315 | BR1_TS1_TC21 | G-RT-7ce0403b11 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | 3 | 246 | 2 | 20 | 18 | **FAIL** | [TC-023315_ATL105_Field_Validation.md](./TC-023315_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | RETURN | TC-023988 | BR1_TS21_TC5 | G-RT-28732b99a2 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | 3 | 254 | 2 | 19 | 19 | **FAIL** | [TC-023988_ATL105_Field_Validation.md](./TC-023988_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| CREDENTIAL_ON_FILE | AUTH_ONLY | TC-021571 | BR1_TS17_TC49 | G-NR-2165baf7bd | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | 2 | 213 | 2 | 17 | 18 | **FAIL** | [TC-021571_ATL105_Field_Validation.md](./TC-021571_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E12 Expiry = original expiry |
| MANUAL | MOTO_PURCHASE | TC-022444 | BR1_TS9_TC2 | G-NR-dc5a44b156 | Single rule broken: Mail/Phone Purchase -> Mail/Phone Reversal with Sequence Number not matching the original -> declined | 2 | 166 | 2 | 9 | 18 | **FAIL** | [TC-022444_ATL105_Field_Validation.md](./TC-022444_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E12 Expiry = original expiry |
| CREDENTIAL_ON_FILE | BALANCE_INQUIRY | TC-023074 | BR1_TS35_TC1 | G-HP-e1ed5b66dc | Happy path: Balance Inquiry approved | 1 | 105 | 0 | 6 | 11 | **PASS_WITH_REVIEW** | [TC-023074_ATL105_Field_Validation.md](./TC-023074_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | AUTH_ONLY | TC-022498 | BR1_TS11_TC72 | G-DT-77b3ee1dfd | Decision table: Authorization Only with track = 1 | 1 | 126 | 2 | 10 | 11 | **FAIL** | [TC-022498_ATL105_Field_Validation.md](./TC-022498_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-023318 | BR1_TS1_TC24 | G-PW-f73c3a3e3a | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | 1 | 126 | 2 | 10 | 11 | **FAIL** | [TC-023318_ATL105_Field_Validation.md](./TC-023318_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-023319 | BR1_TS1_TC25 | G-PW-5e4736a61a | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | 1 | 127 | 2 | 10 | 10 | **FAIL** | [TC-023319_ATL105_Field_Validation.md](./TC-023319_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | PURCHASE | TC-023358 | BR1_TS3_TC20 | G-PW-5e4736a61a | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | 1 | 85 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-023358_ATL105_Field_Validation.md](./TC-023358_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | AUTH_ONLY | TC-020981 | BR1_TS11_TC58 | G-DT-714da61977 | Decision table: Authorization Only with avs = true | 1 | 127 | 5 | 12 | 11 | **FAIL** | [TC-020981_ATL105_Field_Validation.md](./TC-020981_ATL105_Field_Validation.md) | E112 Section[4].VariableInformationLength; E113 Section[4].VariableInformation; E84 SegmentLength (value); E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-023516 | BR1_TS11_TC80 | G-RC-f5fd6259cf | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | 1 | 126 | 2 | 10 | 11 | **FAIL** | [TC-023516_ATL105_Field_Validation.md](./TC-023516_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-023311 | BR1_TS1_TC17 | G-IT-661d9db441 | Invalid state transition: Time-out Reversal with no original -> declined | 1 | 82 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-023311_ATL105_Field_Validation.md](./TC-023311_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | AUTH_ONLY | TC-020974 | BR1_TS11_TC51 | G-DT-feb8fa1588 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | 1 | 127 | 3 | 9 | 10 | **FAIL** | [TC-020974_ATL105_Field_Validation.md](./TC-020974_ATL105_Field_Validation.md) | E33 EncryptedPINBlockData; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-020989 | BR1_TS11_TC66 | G-DT-2663e0f6bc | Decision table: Authorization Only with optional segment 100 present | 1 | 133 | 3 | 10 | 4 | **FAIL** | [TC-020989_ATL105_Field_Validation.md](./TC-020989_ATL105_Field_Validation.md) | E33 EncryptedPINBlockData; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-020977 | BR1_TS11_TC54 | G-DT-c602847c28 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | 1 | 140 | 3 | 10 | 11 | **FAIL** | [TC-020977_ATL105_Field_Validation.md](./TC-020977_ATL105_Field_Validation.md) | Loyalty Card Data Segment; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-020990 | BR1_TS11_TC67 | G-DT-4e5ed46e33 | Decision table: Authorization Only with optional segment 104 present | 1 | 136 | 2 | 10 | 11 | **FAIL** | [TC-020990_ATL105_Field_Validation.md](./TC-020990_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-020992 | BR1_TS11_TC69 | G-DT-91a54a2353 | Decision table: Authorization Only with optional segment 145 present | 1 | 129 | 2 | 10 | 11 | **FAIL** | [TC-020992_ATL105_Field_Validation.md](./TC-020992_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-019956 | BR1_TS77_TC2 | G-HP-4bbfb714c7 | Happy path: Authorization Only approved | 1 | 148 | 2 | 10 | 11 | **FAIL** | [TC-019956_ATL105_Field_Validation.md](./TC-019956_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACTLESS | PURCHASE | TC-024336 | BR1_TS73_TC1 | G-NR-64487a5bbc | Service Level exactly at its documented maximum length (1 bytes) is accepted | 1 | 143 | 6 | 11 | 10 | **FAIL** | [TC-024336_ATL105_Field_Validation.md](./TC-024336_ATL105_Field_Validation.md) | E78 PromptCode.TransactionType; E12 CardDiscretionaryBlockData.Expiry; E87 ServiceLevel; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| CREDENTIAL_ON_FILE | PURCHASE | TC-022198 | BR1_TS7_TC1 | G-NR-3d0c7684ae | Enhanced Fleet Request (segment 145) omitted where p.561 requires it | 1 | 81 | 2 | 2 | 8 | **FAIL** | [TC-022198_ATL105_Field_Validation.md](./TC-022198_ATL105_Field_Validation.md) | EMV Request Data Segment (130); E12 CardDiscretionaryBlockData.Expiry |
