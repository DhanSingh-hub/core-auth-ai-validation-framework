# ATL105 Test Data Field Validation - Result Index

- Test data folder: `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output_1611\mastercard_credit_new\test_data`
- Test case catalog: `C:\Users\F5QGZOJ\Downloads\mastercard_credit_output_1611\mastercard_credit_new\test_cases\test_cases.jsonl`
- Specification: BUYPASS ATL105 Message Format Specifications 2026-3 (specifications/ATL105/docs/specs)
- Generated: 2026-11-17T16:15:30.0623475

## Coverage

| Measure | Value |
|---|---|
| Test cases in scope | 40 |
| Test cases with test data (validated field by field) | 40 (100.0%) |
| Test cases without test data (device-only / not generated) | 0 |
| Test data files validated | 50 |
| Result: FAIL / PASS_WITH_REVIEW / PASS | 32 / 8 / 0 |

## Results by entry mode and transaction category

| Entry mode | Category | TCs | FAIL | PASS_WITH_REVIEW | PASS / N/A |
|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | 12 | 12 | 0 | 0 |
| EMV_CONTACTLESS | PURCHASE | 2 | 2 | 0 | 0 |
| MAGSTRIPE | PURCHASE | 1 | 0 | 1 | 0 |
| CONTACTLESS_MAGSTRIPE | PURCHASE | 1 | 0 | 1 | 0 |
| MANUAL | PURCHASE | 1 | 0 | 1 | 0 |
| EMV_FALLBACK | PURCHASE | 1 | 0 | 1 | 0 |
| CONTACTLESS_MOBILE | PURCHASE | 1 | 0 | 1 | 0 |
| EMV_CONTACT | AUTH_ONLY | 13 | 13 | 0 | 0 |
| MAGSTRIPE | BALANCE_INQUIRY | 1 | 1 | 0 | 0 |
| EMV_CONTACT | BALANCE_INQUIRY | 1 | 1 | 0 | 0 |
| MAGSTRIPE | AUTH_ONLY | 1 | 1 | 0 | 0 |
| CREDENTIAL_ON_FILE | PURCHASE | 2 | 1 | 1 | 0 |
| EMV_CONTACT | RETURN | 1 | 1 | 0 | 0 |
| CREDENTIAL_ON_FILE | AUTH_ONLY | 1 | 0 | 1 | 0 |
| MANUAL | MOTO_PURCHASE | 1 | 0 | 1 | 0 |

## Defect frequency (FAIL checks, grouped)

| # | Area | Field | Element | Rule | TCs affected | % of TCs with TD | Occurrences |
|---|---|---|---|---|---|---|---|
| 1 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-TABLE-ID-MATCH | 29 | 72.5% | 33 |
| 2 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-EMV-TABLE-DATA | 29 | 72.5% | 33 |
| 3 | Seg 100 | NonfuelAmount | E58 | SEG100-E58-FORMAT | 7 | 17.5% | 10 |
| 4 | Seg 100 | CardDiscretionaryBlockData.Expiry | E12 | TRACK2-EXPIRY-YYMM | 7 | 17.5% | 7 |
| 5 | Entry mode | Table 005 vs Element 12 content | E113/12 | APPJ-READ-HAS-TRACK | 5 | 12.5% | 5 |
| 6 | Segment presence | EMV Request Data Segment (130) |  | SEG130-R-017/R-001 | 3 | 7.5% | 3 |
| 7 | Seg 100 | EncryptedPINBlockData | E33 | SEG100-E33-FORMAT | 2 | 5.0% | 2 |
| 8 | Seg 130 | Tag 9F02 vs NonfuelAmount | E190/58 | SEG130-R-009 | 1 | 2.5% | 1 |
| 9 | Flow | Step 2 9F02 = step 2 amount | E190 | SPLIT-TENDER-9F02 | 1 | 2.5% | 1 |
| 10 | Seg 100 | FuelPurchaseAmount | E41 | SEG100-E41-FORMAT | 1 | 2.5% | 1 |
| 11 | Seg 102 | UnitPrice | E107 | SEG102-E107 | 1 | 2.5% | 1 |
| 12 | Seg 102 | ProductAmount | E76 | SEG102-E76 | 1 | 2.5% | 1 |
| 13 | Seg 102 | SegmentLength (value) | E84 | E84-SEG102-LENGTH | 1 | 2.5% | 1 |
| 14 | Flow | Step 2 NonfuelAmount < Step 1 amount | E58 | SPLIT-TENDER-REMAINDER | 1 | 2.5% | 1 |
| 15 | Message | ECA/ TeleCheck Data Segment Format ® |  | 11.8.1-SECTION3 | 1 | 2.5% | 1 |
| 16 | Seg 100 | InformationByte | E44 | SEG100-E44 | 1 | 2.5% | 1 |
| 17 | Message | Loyalty Card Data Segment |  | 11.8.1-SECTION3 | 1 | 2.5% | 1 |

## Per test case

| Entry mode | Category | TC | Display | Template | Objective | Legs | PASS | FAIL | REVIEW | N/A | Overall | Report | Key defects |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | TC-039814 | BR1_TS1_TC7 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 129 | 2 | 10 | 10 | **FAIL** | [TC-039814_ATL105_Field_Validation.md](./TC-039814_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACTLESS | PURCHASE | TC-039838 | BR1_TS2_TC3 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 129 | 2 | 11 | 10 | **FAIL** | [TC-039838_ATL105_Field_Validation.md](./TC-039838_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | PURCHASE | TC-039862 | BR1_TS3_TC2 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 93 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039862_ATL105_Field_Validation.md](./TC-039862_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-039888 | BR1_TS4_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 93 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039888_ATL105_Field_Validation.md](./TC-039888_ATL105_Field_Validation.md) |  |
| MANUAL | PURCHASE | TC-039912 | BR1_TS5_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 91 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039912_ATL105_Field_Validation.md](./TC-039912_ATL105_Field_Validation.md) |  |
| EMV_FALLBACK | PURCHASE | TC-039936 | BR1_TS6_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 92 | 0 | 3 | 9 | **PASS_WITH_REVIEW** | [TC-039936_ATL105_Field_Validation.md](./TC-039936_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MOBILE | PURCHASE | TC-039985 | BR1_TS8_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 92 | 0 | 4 | 9 | **PASS_WITH_REVIEW** | [TC-039985_ATL105_Field_Validation.md](./TC-039985_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | PURCHASE | TC-041569 | BR1_TS168_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 151 | 3 | 10 | 9 | **FAIL** | [TC-041569_ATL105_Field_Validation.md](./TC-041569_ATL105_Field_Validation.md) | E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-041592 | BR1_TS173_TC1 | G-HP-4bbfb714c7 | Happy path: Authorization Only approved | 1 | 151 | 3 | 10 | 9 | **FAIL** | [TC-041592_ATL105_Field_Validation.md](./TC-041592_ATL105_Field_Validation.md) | E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041786 | BR1_TS178_TC1 | G-VF-5883641281 | Valid flow: Purchase/Capture -> Time-out Reversal | 2 | 286 | 4 | 13 | 15 | **FAIL** | [TC-041786_ATL105_Field_Validation.md](./TC-041786_ATL105_Field_Validation.md) | E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041789 | BR1_TS183_TC1 | G-VF-35e1056df6 | Valid flow: Purchase/Capture -> Purchase Reversal | 2 | 240 | 2 | 18 | 17 | **FAIL** | [TC-041789_ATL105_Field_Validation.md](./TC-041789_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041793 | BR1_TS190_TC1 | G-VF-35e1056df6 | Valid flow: Purchase/Capture -> Purchase Reversal | 2 | 240 | 2 | 18 | 17 | **FAIL** | [TC-041793_ATL105_Field_Validation.md](./TC-041793_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040134 | BR1_TS11_TC75 | G-VF-9ba75bd7e4 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | 2 | 257 | 6 | 25 | 21 | **FAIL** | [TC-040134_ATL105_Field_Validation.md](./TC-040134_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle); E190/58 Tag 9F02 vs NonfuelAmount; E190 Step 2 9F02 = step 2 amount |
| EMV_CONTACT | AUTH_ONLY | TC-041632 | BR1_TS173_TC41 | G-VF-9ba75bd7e4 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | 2 | 297 | 11 | 25 | 17 | **FAIL** | [TC-041632_ATL105_Field_Validation.md](./TC-041632_ATL105_Field_Validation.md) | E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle); E41 FuelPurchaseAmount; E107 UnitPrice; E76 ProductAmount; E84 SegmentLength (value) |
| EMV_CONTACT | AUTH_ONLY | TC-041634 | BR1_TS173_TC43 | G-IT-d32476c4da | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | 2 | 302 | 6 | 24 | 16 | **FAIL** | [TC-041634_ATL105_Field_Validation.md](./TC-041634_ATL105_Field_Validation.md) | E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-041343 | BR1_TS154_TC47 | G-IT-d32476c4da | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | 2 | 300 | 4 | 24 | 20 | **FAIL** | [TC-041343_ATL105_Field_Validation.md](./TC-041343_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| MAGSTRIPE | BALANCE_INQUIRY | TC-038601 | BR1_TS31_TC1 | G-NR-dce64db32f | Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted | 1 | 95 | 4 | 3 | 8 | **FAIL** | [TC-038601_ATL105_Field_Validation.md](./TC-038601_ATL105_Field_Validation.md) | ECA/ TeleCheck Data Segment Format ®; EMV Request Data Segment (130); E12 CardDiscretionaryBlockData.Expiry; E58 NonfuelAmount |
| EMV_CONTACT | AUTH_ONLY | TC-038618 | BR1_TS154_TC1 | G-NR-1a9f9efe43 | Purchase Code exactly at its documented maximum length (16 bytes) is accepted | 1 | 134 | 4 | 10 | 10 | **FAIL** | [TC-038618_ATL105_Field_Validation.md](./TC-038618_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| EMV_CONTACT | PURCHASE | TC-038622 | BR1_TS147_TC1 | G-NR-b5931b1551 | Additional Information exactly at its documented maximum length (984 bytes) is accepted | 1 | 125 | 4 | 10 | 10 | **FAIL** | [TC-038622_ATL105_Field_Validation.md](./TC-038622_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| EMV_CONTACTLESS | PURCHASE | TC-038632 | BR1_TS2_TC1 | G-NR-c5d550813d | CA Public Key File Checksum exactly at its documented maximum length (25 bytes) is accepted | 1 | 125 | 4 | 11 | 10 | **FAIL** | [TC-038632_ATL105_Field_Validation.md](./TC-038632_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| EMV_CONTACT | BALANCE_INQUIRY | TC-038634 | BR1_TS29_TC1 | G-NR-941d92c222 | Information Byte exactly at its documented maximum length (1 bytes) is accepted | 1 | 122 | 6 | 10 | 10 | **FAIL** | [TC-038634_ATL105_Field_Validation.md](./TC-038634_ATL105_Field_Validation.md) | E44 InformationByte; E12 CardDiscretionaryBlockData.Expiry; E58 NonfuelAmount; E191/192/118 EMV Additional Information (Appendix T oracle); E113/12 Table 005 vs Element 12 content |
| MAGSTRIPE | AUTH_ONLY | TC-038639 | BR1_TS13_TC1 | G-NR-fdea1b5700 | EMV Request Data Segment (segment 130) omitted where p.142 requires it | 1 | 88 | 3 | 3 | 8 | **FAIL** | [TC-038639_ATL105_Field_Validation.md](./TC-038639_ATL105_Field_Validation.md) | EMV Request Data Segment (130); E12 CardDiscretionaryBlockData.Expiry; E113/12 Table 005 vs Element 12 content |
| CREDENTIAL_ON_FILE | PURCHASE | TC-038602 | BR1_TS7_TC1 | G-NR-3d0c7684ae | Enhanced Fleet Request (segment 145) omitted where p.561 requires it | 1 | 107 | 2 | 6 | 8 | **FAIL** | [TC-038602_ATL105_Field_Validation.md](./TC-038602_ATL105_Field_Validation.md) | EMV Request Data Segment (130); E12 CardDiscretionaryBlockData.Expiry |
| EMV_CONTACT | AUTH_ONLY | TC-040114 | BR1_TS11_TC55 | G-DT-c602847c28 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | 1 | 142 | 3 | 10 | 11 | **FAIL** | [TC-040114_ATL105_Field_Validation.md](./TC-040114_ATL105_Field_Validation.md) | Loyalty Card Data Segment; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040127 | BR1_TS11_TC68 | G-DT-4e5ed46e33 | Decision table: Authorization Only with optional segment 104 present | 1 | 138 | 2 | 10 | 11 | **FAIL** | [TC-040127_ATL105_Field_Validation.md](./TC-040127_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| CREDENTIAL_ON_FILE | PURCHASE | TC-039967 | BR1_TS7_TC9 | G-DT-7e0614676f | Decision table: Purchase/Capture with segment 145 sent (the rule's condition holds) | 1 | 112 | 0 | 6 | 9 | **PASS_WITH_REVIEW** | [TC-039967_ATL105_Field_Validation.md](./TC-039967_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | AUTH_ONLY | TC-040111 | BR1_TS11_TC52 | G-DT-feb8fa1588 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | 1 | 129 | 3 | 9 | 10 | **FAIL** | [TC-040111_ATL105_Field_Validation.md](./TC-040111_ATL105_Field_Validation.md) | E33 EncryptedPINBlockData; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040126 | BR1_TS11_TC67 | G-DT-2663e0f6bc | Decision table: Authorization Only with optional segment 100 present | 1 | 135 | 3 | 10 | 4 | **FAIL** | [TC-040126_ATL105_Field_Validation.md](./TC-040126_ATL105_Field_Validation.md) | E33 EncryptedPINBlockData; E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040140 | BR1_TS11_TC81 | G-RC-f5fd6259cf | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-040140_ATL105_Field_Validation.md](./TC-040140_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-039831 | BR1_TS1_TC24 | G-PW-f73c3a3e3a | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-039831_ATL105_Field_Validation.md](./TC-039831_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-039830 | BR1_TS1_TC23 | G-RT-4000a95dae | Running total / state: Purchase/Capture approved -> Totals Request | 2 | 171 | 2 | 12 | 11 | **FAIL** | [TC-039830_ATL105_Field_Validation.md](./TC-039830_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | RETURN | TC-041108 | BR1_TS21_TC7 | G-RT-28732b99a2 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | 3 | 264 | 2 | 20 | 19 | **FAIL** | [TC-041108_ATL105_Field_Validation.md](./TC-041108_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| CREDENTIAL_ON_FILE | AUTH_ONLY | TC-040902 | BR1_TS17_TC69 | G-DT-ef2a28c7ea | Decision table: Authorization Only with initiation = M | 1 | 108 | 0 | 6 | 10 | **PASS_WITH_REVIEW** | [TC-040902_ATL105_Field_Validation.md](./TC-040902_ATL105_Field_Validation.md) |  |
| MANUAL | MOTO_PURCHASE | TC-040009 | BR1_TS9_TC2 | G-HP-6edbacca31 | Happy path: Mail/Phone Purchase approved | 1 | 90 | 0 | 3 | 10 | **PASS_WITH_REVIEW** | [TC-040009_ATL105_Field_Validation.md](./TC-040009_ATL105_Field_Validation.md) |  |
| EMV_CONTACT | AUTH_ONLY | TC-041338 | BR1_TS154_TC42 | G-DT-359580cdac | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | 1 | 168 | 2 | 12 | 11 | **FAIL** | [TC-041338_ATL105_Field_Validation.md](./TC-041338_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | AUTH_ONLY | TC-040080 | BR1_TS11_TC21 | G-HP-69beb2005a | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-040080_ATL105_Field_Validation.md](./TC-040080_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041188 | BR1_TS47_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 129 | 2 | 10 | 10 | **FAIL** | [TC-041188_ATL105_Field_Validation.md](./TC-041188_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041838 | BR1_TS99_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 128 | 2 | 10 | 11 | **FAIL** | [TC-041838_ATL105_Field_Validation.md](./TC-041838_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041848 | BR1_TS109_TC1 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 129 | 2 | 10 | 10 | **FAIL** | [TC-041848_ATL105_Field_Validation.md](./TC-041848_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
| EMV_CONTACT | PURCHASE | TC-041255 | BR1_TS147_TC5 | G-HP-a8f208eda7 | Happy path: Purchase/Capture approved | 1 | 151 | 2 | 10 | 10 | **FAIL** | [TC-041255_ATL105_Field_Validation.md](./TC-041255_ATL105_Field_Validation.md) | E191/192/118 EMV Additional Information (Appendix T oracle) |
