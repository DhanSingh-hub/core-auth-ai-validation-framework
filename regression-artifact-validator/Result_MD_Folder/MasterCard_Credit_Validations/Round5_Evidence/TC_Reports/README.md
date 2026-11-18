# ATL105 Test Data Field Validation - Result Index

- Test data folder: `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output_1611\mastercard_credit_new\test_data`
- Test case catalog: `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output_1611\mastercard_credit_new\test_cases\test_cases.jsonl`
- Specification: BUYPASS ATL105 Message Format Specifications 2026-3 (specifications/ATL105/docs/specs)
- Generated: 2026-11-17T15:42:47.6194668

## Coverage

| Measure | Value |
|---|---|
| Test cases in scope | 42 |
| Test cases with test data (validated field by field) | 42 (100.0%) |
| Test cases without test data (device-only / not generated) | 0 |
| Test data files validated | 55 |
| Result: FAIL / PASS_WITH_REVIEW / PASS | 33 / 9 / 0 |

## Results by entry mode and transaction category

| Entry mode | Category | TCs | FAIL | PASS_WITH_REVIEW | PASS / N/A |
|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | 10 | 10 | 0 | 0 |
| EMV_CONTACTLESS | PURCHASE | 1 | 1 | 0 | 0 |
| MAGSTRIPE | PURCHASE | 1 | 0 | 1 | 0 |
| CONTACTLESS_MAGSTRIPE | PURCHASE | 1 | 0 | 1 | 0 |
| MANUAL | PURCHASE | 1 | 0 | 1 | 0 |
| EMV_FALLBACK | PURCHASE | 1 | 0 | 1 | 0 |
| CREDENTIAL_ON_FILE | PURCHASE | 1 | 0 | 1 | 0 |
| CONTACTLESS_MOBILE | PURCHASE | 1 | 0 | 1 | 0 |
| EMV_CONTACT | AUTH_ONLY | 17 | 17 | 0 | 0 |
| EMV_CONTACT | RETURN | 1 | 1 | 0 | 0 |
| MANUAL | MOTO_PURCHASE | 1 | 1 | 0 | 0 |
| CREDENTIAL_ON_FILE | BALANCE_INQUIRY | 1 | 0 | 1 | 0 |
| MAGSTRIPE | AUTH_ONLY | 3 | 1 | 2 | 0 |
| EMV_CONTACT | BALANCE_INQUIRY | 1 | 1 | 0 | 0 |
| CREDENTIAL_ON_FILE | AUTH_ONLY | 1 | 1 | 0 | 0 |

## Defect frequency (FAIL checks, grouped)

| # | Area | Field | Element | Rule | TCs affected | % of TCs with TD | Occurrences |
|---|---|---|---|---|---|---|---|
| 1 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-TABLE-ID-MATCH | 30 | 71.4% | 33 |
| 2 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-EMV-TABLE-DATA | 30 | 71.4% | 33 |
| 3 | Seg 100 | CardDiscretionaryBlockData.Expiry | E12 | TRACK2-EXPIRY-YYMM | 6 | 14.3% | 6 |
| 4 | Entry mode | Table 005 vs Element 12 content | E113/12 | APPJ-READ-HAS-TRACK | 5 | 11.9% | 5 |
| 5 | Seg 100 | NonfuelAmount | E58 | SEG100-E58-FORMAT | 3 | 7.1% | 4 |
| 6 | Seg 111 | VariableInformationLength | E112 | SEG111-E112 | 2 | 4.8% | 2 |
| 7 | Seg 100 | EncryptedPINBlockData | E33 | SEG100-E33-FORMAT | 2 | 4.8% | 2 |
| 8 | Seg 100 | CardDiscretionaryBlockData.Expiry | E12 | E12-KEYED-EXPIRY-MMYY | 1 | 2.4% | 1 |
| 9 | Flow | Expiry = original expiry | E12 | FOLLOW-ON-SAME-EXPIRY | 1 | 2.4% | 1 |
| 10 | Seg 130 | Tag 9F02 vs NonfuelAmount | E190/58 | SEG130-R-009 | 1 | 2.4% | 1 |
| 11 | Flow | Step 2 9F02 = step 2 amount | E190 | SPLIT-TENDER-9F02 | 1 | 2.4% | 1 |
| 12 | Seg 100 | AccountNumber | E2 | SEG100-R-041 | 1 | 2.4% | 1 |
| 13 | Seg 100 | CardDiscretionaryBlockData | E12 | SEG100-R-042 | 1 | 2.4% | 1 |
| 14 | Seg 111 | VariableInformation | E113 | APPI-T003-R001 | 1 | 2.4% | 1 |
| 15 | Seg 111 | SegmentLength (value) | E84 | E84-SEG111-LENGTH | 1 | 2.4% | 1 |
| 16 | Message | Loyalty Card Data Segment |  | 11.8.1-SECTION3 | 1 | 2.4% | 1 |
| 17 | Seg 100 | PartialApprovalIndicator | E121 | SEG100-E121 | 1 | 2.4% | 1 |
| 18 | Seg 130 | EMVChipDataLength | E189 | REQUIRED-FIELD | 1 | 2.4% | 1 |
| 19 | Seg 130 | EMVChipDataLength | E189 | SEG130-R-007 | 1 | 2.4% | 1 |
| 20 | Seg 130 | EMVChipData (Appendix R oracle) | E190 | BR-SEG100-APPR-EMV-COMPANION | 1 | 2.4% | 1 |
| 21 | Seg 130 | SegmentLength (value) | E84 | SEG130-R-003/R-004 | 1 | 2.4% | 1 |
| 22 | Seg 111 | VariableInformation | E113 | APPI-T005-R001 | 1 | 2.4% | 1 |
| 23 | Seg 111 | POS Entry Mode (App. J) | E113 | APPJ-POS-ENTRY-MODE | 1 | 2.4% | 1 |
| 24 | Seg 111 | POS Entry Mode vs TC leg entry_code | E113 | LEG-ENTRY-MODE | 1 | 2.4% | 1 |
| 25 | Seg 111 | Variable Information Section total |  | 12.10-SECTION-991 | 1 | 2.4% | 1 |
| 26 | Seg 111 | SegmentLength (format) | E84 | E84-SEG111-LENGTH | 1 | 2.4% | 1 |
| 27 | Segment presence | EMV Request Data Segment (130) |  | SEG130-R-017/R-001 | 1 | 2.4% | 1 |

## Per test case

| Entry mode | Category | TC | Display | Template | Objective | Legs | PASS | FAIL | REVIEW | N/A | Overall | Report | Key defects |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | TC-039814 | BR1_TS1_TC7 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 129 | 2 | 10 | 10 | **FAIL** | [TC-039814_ATL105_Field_Validation.md](./TC-039814_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACTLESS | PURCHASE | TC-039838 | BR1_TS2_TC3 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 129 | 2 | 11 | 10 | **FAIL** | [TC-039838_ATL105_Field_Validation.md](./TC-039838_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | PURCHASE | TC-039862 | BR1_TS3_TC2 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 93 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039862_ATL105_Field_Validation.md](./TC-039862_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-039888 | BR1_TS4_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 93 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039888_ATL105_Field_Validation.md](./TC-039888_ATL105_Field_Validation.md) |  |
| MANUAL | PURCHASE | TC-039912 | BR1_TS5_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 91 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039912_ATL105_Field_Validation.md](./TC-039912_ATL105_Field_Validation.md) |  |
| EMV_FALLBACK | PURCHASE | TC-039936 | BR1_TS6_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 92 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039936_ATL105_Field_Validation.md](./TC-039936_ATL105_Field_Validation.md) |  |
| CREDENTIAL_ON_FILE | PURCHASE | TC-039960 | BR1_TS7_TC2 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 109 | 0 | 6 | 9 | **PASS_WITH_REVIEW** | [TC-039960_ATL105_Field_Validation.md](./TC-039960_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MOBILE | PURCHASE | TC-039985 | BR1_TS8_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 92 | 0 | 4 | 9 | **PASS_WITH_REVIEW** | [TC-039985_ATL105_Field_Validation.md](./TC-039985_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | AUTH_ONLY | TC-041343 | BR1_TS154_TC47 | G-IT-d32476c4da | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | 2 | 300 | 4 | 24 | 20 | **FAIL** | [TC-041343_ATL105_Field_Validation.md](./TC-041343_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-041634 | BR1_TS173_TC43 | G-IT-d32476c4da | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | 2 | 302 | 6 | 24 | 16 | **FAIL** | [TC-041634_ATL105_Field_Validation.md](./TC-041634_ATL105_Field_Validation.md) | E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-039821 | BR1_TS1_TC14 | G-VF-35e1056df6 | Valid flow: Purchase/Capture -> Purchase Reversal | 2 | 224 | 2 | 18 | 17 | **FAIL** | [TC-039821_ATL105_Field_Validation.md](./TC-039821_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-039822 | BR1_TS1_TC15 | G-VF-5883641281 | Valid flow: Purchase/Capture -> Time-out Reversal | 2 | 225 | 2 | 13 | 18 | **FAIL** | [TC-039822_ATL105_Field_Validation.md](./TC-039822_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040133 | BR1_TS11_TC74 | G-VF-86d03977d5 | Valid flow: Authorization Only -> Cancellation | 2 | 222 | 2 | 18 | 18 | **FAIL** | [TC-040133_ATL105_Field_Validation.md](./TC-040133_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | RETURN | TC-041107 | BR1_TS21_TC6 | G-VF-5702611ba2 | Valid flow: Merchandise Return -> Void of a Merchandise Return | 2 | 223 | 2 | 17 | 18 | **FAIL** | [TC-041107_ATL105_Field_Validation.md](./TC-041107_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MANUAL | MOTO_PURCHASE | TC-040014 | BR1_TS9_TC7 | G-VF-77ee20eb5b | Valid flow: Mail/Phone Purchase -> Mail/Phone Reversal | 2 | 183 | 2 | 11 | 18 | **FAIL** | [TC-040014_ATL105_Field_Validation.md](./TC-040014_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E12 Expiry = original expiry |
| EMV_CONTACT | AUTH_ONLY | TC-040134 | BR1_TS11_TC75 | G-VF-9ba75bd7e4 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | 2 | 257 | 6 | 25 | 21 | **FAIL** | [TC-040134_ATL105_Field_Validation.md](./TC-040134_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle); E190/58 Tag 9F02 vs NonfuelAmount; E190 Step 2 9F02 = step 2 amount |
| EMV_CONTACT | PURCHASE | TC-039830 | BR1_TS1_TC23 | G-RT-4000a95dae | Running total / state: Purchase/Capture approved -> Totals Request | 2 | 171 | 2 | 12 | 11 | **FAIL** | [TC-039830_ATL105_Field_Validation.md](./TC-039830_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040138 | BR1_TS11_TC79 | G-RT-76fc07b457 | Running total / state: Authorization Only approved -> Totals Request | 2 | 169 | 2 | 13 | 12 | **FAIL** | [TC-040138_ATL105_Field_Validation.md](./TC-040138_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040119 | BR1_TS11_TC60 | G-DT-9334878b5f | Decision table: Authorization Only with cof = initial | 1 | 140 | 2 | 12 | 11 | **FAIL** | [TC-040119_ATL105_Field_Validation.md](./TC-040119_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| CREDENTIAL_ON_FILE | BALANCE_INQUIRY | TC-041183 | BR1_TS35_TC1 | G-HP-e1ed5b66dc | Happy path: Balance Inquiry approved | 1 | 107 | 0 | 6 | 11 | **PASS_WITH_REVIEW** | [TC-041183_ATL105_Field_Validation.md](./TC-041183_ATL105_Field_Validation.md) |  |
| MAGSTRIPE | AUTH_ONLY | TC-040362 | BR1_TS13_TC71 | G-DT-77b3ee1dfd | Decision table: Authorization Only with track = 1 | 1 | 84 | 3 | 3 | 10 | **FAIL** | [TC-040362_ATL105_Field_Validation.md](./TC-040362_ATL105_Field_Validation.md) | E2 AccountNumber; E12 CardDiscretionaryBlockData; E113/12 Table 005 vs Element 12 content |
| EMV_CONTACT | AUTH_ONLY | TC-040132 | BR1_TS11_TC73 | G-DT-77b3ee1dfd | Decision table: Authorization Only with track = 1 | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-040132_ATL105_Field_Validation.md](./TC-040132_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040121 | BR1_TS11_TC62 | G-DT-2ed676d133 | Decision table: Authorization Only with cvm = online_pin | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-040121_ATL105_Field_Validation.md](./TC-040121_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040120 | BR1_TS11_TC61 | G-DT-c0d9e40115 | Decision table: Authorization Only with cvm = offline_pin | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-040120_ATL105_Field_Validation.md](./TC-040120_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | AUTH_ONLY | TC-040350 | BR1_TS13_TC59 | G-DT-c0d9e40115 | Decision table: Authorization Only with cvm = offline_pin | 1 | 92 | 0 | 3 | 10 | **PASS_WITH_REVIEW** | [TC-040350_ATL105_Field_Validation.md](./TC-040350_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | AUTH_ONLY | TC-040118 | BR1_TS11_TC59 | G-DT-714da61977 | Decision table: Authorization Only with avs = true | 1 | 129 | 5 | 12 | 11 | **FAIL** | [TC-040118_ATL105_Field_Validation.md](./TC-040118_ATL105_Field_Validation.md) | E112 Section[4].VariableInformationLength; E113 Section[4].VariableInformation; E84 SegmentLength (value); E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040111 | BR1_TS11_TC52 | G-DT-feb8fa1588 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | 1 | 129 | 3 | 9 | 10 | **FAIL** | [TC-040111_ATL105_Field_Validation.md](./TC-040111_ATL105_Field_Validation.md) | E33 EncryptedPINBlockData; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040126 | BR1_TS11_TC67 | G-DT-2663e0f6bc | Decision table: Authorization Only with optional segment 100 present | 1 | 135 | 3 | 10 | 4 | **FAIL** | [TC-040126_ATL105_Field_Validation.md](./TC-040126_ATL105_Field_Validation.md) | E33 EncryptedPINBlockData; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040114 | BR1_TS11_TC55 | G-DT-c602847c28 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | 1 | 142 | 3 | 10 | 11 | **FAIL** | [TC-040114_ATL105_Field_Validation.md](./TC-040114_ATL105_Field_Validation.md) | Loyalty Card Data Segment; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-041338 | BR1_TS154_TC42 | G-DT-359580cdac | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | 1 | 168 | 2 | 12 | 11 | **FAIL** | [TC-041338_ATL105_Field_Validation.md](./TC-041338_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040066 | BR1_TS11_TC7 | G-HP-fc1ccbe408 | Happy path: Authorization Only sending Table 006 Currency Code Information | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-040066_ATL105_Field_Validation.md](./TC-040066_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041569 | BR1_TS168_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 151 | 3 | 10 | 9 | **FAIL** | [TC-041569_ATL105_Field_Validation.md](./TC-041569_ATL105_Field_Validation.md) | E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-039831 | BR1_TS1_TC24 | G-PW-f73c3a3e3a | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-039831_ATL105_Field_Validation.md](./TC-039831_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-038593 | BR1_TS11_TC2 | G-NR-a891466321 | Partial Approval Indicator value not in its documented valid values | 1 | 124 | 5 | 10 | 10 | **FAIL** | [TC-038593_ATL105_Field_Validation.md](./TC-038593_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E121 PartialApprovalIndicator; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| EMV_CONTACT | AUTH_ONLY | TC-038603 | BR1_TS11_TC1 | G-NR-7e70bdd90e | Required field EMV Chip Data Length sent empty | 1 | 120 | 9 | 10 | 10 | **FAIL** | [TC-038603_ATL105_Field_Validation.md](./TC-038603_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E189 EMVChipDataLength; E190 EMVChipData (Appendix R oracle); E191/192/118 EMV Additional Information (Appendix T oracle); E84 SegmentLength (value); E113/12 Table 005 vs Element 12 content |
| EMV_CONTACT | PURCHASE | TC-038635 | BR1_TS1_TC3 | G-NR-7fa15ac1b2 | Variable Information one byte over its documented maximum length (982) | 1 | 117 | 9 | 10 | 10 | **FAIL** | [TC-038635_ATL105_Field_Validation.md](./TC-038635_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E112 Section[1].VariableInformationLength; E113 Section[1].VariableInformation (Table 005); E113 Section[1].POS Entry Mode (App. J); E113 Section[1].POS Entry Mode vs TC leg entry_code; Variable Information Section total |
| EMV_CONTACT | PURCHASE | TC-038622 | BR1_TS147_TC1 | G-NR-b5931b1551 | Additional Information exactly at its documented maximum length (984 bytes) is accepted | 1 | 125 | 4 | 10 | 10 | **FAIL** | [TC-038622_ATL105_Field_Validation.md](./TC-038622_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| EMV_CONTACT | BALANCE_INQUIRY | TC-038633 | BR1_TS29_TC3 | G-NR-dc6da3a5ae | Tax Type 1 value not in its documented valid values | 1 | 123 | 5 | 10 | 10 | **FAIL** | [TC-038633_ATL105_Field_Validation.md](./TC-038633_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| EMV_CONTACT | PURCHASE | TC-039837 | BR1_TS1_TC30 | G-RC-3dcde81faa | Response / oracle check: Purchase/Capture -> declined (issuer decline) | 1 | 129 | 2 | 10 | 10 | **FAIL** | [TC-039837_ATL105_Field_Validation.md](./TC-039837_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | AUTH_ONLY | TC-040338 | BR1_TS13_TC47 | G-NR-2bc3557f46 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | 1 | 92 | 0 | 3 | 10 | **PASS_WITH_REVIEW** | [TC-040338_ATL105_Field_Validation.md](./TC-040338_ATL105_Field_Validation.md) |  |
| CREDENTIAL_ON_FILE | AUTH_ONLY | TC-038605 | BR1_TS17_TC3 | G-NR-ee04315be7 | Purchase Card Data Segment (segment 104) omitted where p.728 requires it | 1 | 107 | 2 | 6 | 8 | **FAIL** | [TC-038605_ATL105_Field_Validation.md](./TC-038605_ATL105_Field_Validation.md) | EMV Request Data Segment (130); E12 CardDiscretionaryBlockData.Expiry |
| EMV_CONTACT | PURCHASE | TC-039827 | BR1_TS1_TC20 | G-IR-20c2b00d52 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | 4 | 417 | 2 | 19 | 34 | **FAIL** | [TC-039827_ATL105_Field_Validation.md](./TC-039827_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
