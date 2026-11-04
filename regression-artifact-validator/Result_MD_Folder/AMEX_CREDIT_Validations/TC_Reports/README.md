# ATL105 Test Data Field Validation - Result Index

- Test data folder: `C:\Users\F5QGZOJ\Downloads\31-10-out\american_express_credit\test_data`
- Test case catalog: `C:\Users\F5QGZOJ\Downloads\31-10-out\american_express_credit\test_cases\test_cases.jsonl`
- Specification: BUYPASS ATL105 Message Format Specifications 2026-3 (specifications/ATL105/docs/specs)
- Generated: 2026-11-03T16:42:27.9972187

## Coverage

| Measure | Value |
|---|---|
| Test cases in scope | 40 |
| Test cases with test data (validated field by field) | 40 (100.0%) |
| Test cases without test data (device-only / not generated) | 0 |
| Test data files validated | 64 |
| Result: FAIL / PASS_WITH_REVIEW / PASS | 28 / 12 / 0 |

## Results by entry mode and transaction category

| Entry mode | Category | TCs | FAIL | PASS_WITH_REVIEW | PASS / N/A |
|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | 5 | 5 | 0 | 0 |
| EMV_CONTACTLESS | PURCHASE | 5 | 5 | 0 | 0 |
| MAGSTRIPE | PURCHASE | 5 | 3 | 2 | 0 |
| CONTACTLESS_MAGSTRIPE | PURCHASE | 5 | 2 | 3 | 0 |
| MANUAL | PURCHASE | 5 | 3 | 2 | 0 |
| EMV_FALLBACK | PURCHASE | 5 | 5 | 0 | 0 |
| CREDENTIAL_ON_FILE | PURCHASE | 5 | 3 | 2 | 0 |
| CONTACTLESS_MOBILE | PURCHASE | 5 | 2 | 3 | 0 |

## Defect frequency (FAIL checks, grouped)

| # | Area | Field | Element | Rule | TCs affected | % of TCs with TD | Occurrences |
|---|---|---|---|---|---|---|---|
| 1 | Seg 130 | EMVCardSequenceNumber | E188 | SEG130-R-006/E188 | 10 | 25.0% | 12 |
| 2 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-TABLE-ID-MATCH | 10 | 25.0% | 12 |
| 3 | Seg 130 | EMV Additional Information (Appendix T oracle) | E191/192/118 | BR-SEG100-APPT-EMV-TABLE-DATA | 10 | 25.0% | 12 |
| 4 | Seg 130 | EMVAdditionalInformation | E118 | SEG130-R-020/BR-SEG100-APPT-EMV-TABLE-DATA | 10 | 25.0% | 12 |
| 5 | Flow | Step 2 SegmentLength vs possible remainder | E84/58 | SPLIT-TENDER-LENGTH | 7 | 17.5% | 7 |
| 6 | Seg 105 | CardLabel (width) | E13 | SEG105-E13-WIDTH | 6 | 15.0% | 6 |
| 7 | Entry mode | Table 005 vs Element 12 content | E113/12 | APPJ-KEYED-NO-TRACK | 5 | 12.5% | 6 |
| 8 | Entry mode | Chip-fallback code vs data | E113/12 | APPJ-FALLBACK-79-80 | 5 | 12.5% | 6 |
| 9 | Seg 100 | CardDiscretionaryBlockData.Expiry | E12 | E12-KEYED-EXPIRY-MMYY | 4 | 10.0% | 4 |
| 10 | Flow | Expiry = original expiry | E12 | FOLLOW-ON-SAME-EXPIRY | 3 | 7.5% | 3 |
| 11 | Objective | Card expiry for expired-card test | E12 | T2-EXPIRED-EMV | 2 | 5.0% | 2 |
| 12 | Seg 130 | Tag 9F02 vs NonfuelAmount | E190/58 | SEG130-R-009 | 2 | 5.0% | 2 |
| 13 | Flow | Step 2 9F02 = step 2 amount | E190 | SPLIT-TENDER-9F02 | 2 | 5.0% | 2 |
| 14 | Objective | CVM Results (9F34) | E190 | T3-OFFLINE-PIN-CVM | 1 | 2.5% | 1 |
| 15 | Entry mode | Terminal capability 8 vs Table 031 | E113 | APPJ-CAP-8-HIERARCHY | 1 | 2.5% | 1 |

## Per test case

| Entry mode | Category | TC | Display | Template | Objective | Legs | PASS | FAIL | REVIEW | N/A | Overall | Report | Key defects |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| EMV_CONTACT | PURCHASE | TC-002471 | BR1_TS1_TC1 | T1-PUR | Purchase approved | 1 | 120 | 4 | 8 | 10 | **FAIL** | [TC-002471_ATL105_Field_Validation.md](./TC-002471_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation |
| EMV_CONTACT | PURCHASE | TC-002472 | BR1_TS1_TC2 | T2-EXPIRED-EMV | Expired EMV card -> sent online -> host decides | 1 | 119 | 5 | 9 | 10 | **FAIL** | [TC-002472_ATL105_Field_Validation.md](./TC-002472_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation; E12 Card expiry for expired-card test |
| EMV_CONTACT | PURCHASE | TC-002473 | BR1_TS1_TC3 | T3-OFFLINE-PIN | EMV offline PIN -> approved | 1 | 120 | 5 | 8 | 10 | **FAIL** | [TC-002473_ATL105_Field_Validation.md](./TC-002473_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation; E190 CVM Results (9F34) |
| EMV_CONTACT | PURCHASE | TC-002474 | BR1_TS1_TC4 | T4-PUR-SPLIT | Partial approval -> remainder on 2nd card (split tender) | 2 | 239 | 11 | 21 | 19 | **FAIL** | [TC-002474_ATL105_Field_Validation.md](./TC-002474_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation; E190/58 Tag 9F02 vs NonfuelAmount; E84/58 Step 2 SegmentLength vs possible remainder; E190 Step 2 9F02 = step 2 amount |
| EMV_CONTACT | PURCHASE | TC-002478 | BR1_TS1_TC8 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 206 | 4 | 14 | 17 | **FAIL** | [TC-002478_ATL105_Field_Validation.md](./TC-002478_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation |
| EMV_CONTACTLESS | PURCHASE | TC-002505 | BR1_TS2_TC1 | T1-PUR | Purchase approved | 1 | 120 | 4 | 9 | 10 | **FAIL** | [TC-002505_ATL105_Field_Validation.md](./TC-002505_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation |
| EMV_CONTACTLESS | PURCHASE | TC-002506 | BR1_TS2_TC2 | T2-CL-TO-CONTACT | Contactless chip -> contact chip (86) -> approved | 1 | 119 | 4 | 8 | 10 | **FAIL** | [TC-002506_ATL105_Field_Validation.md](./TC-002506_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation |
| EMV_CONTACTLESS | PURCHASE | TC-002507 | BR1_TS2_TC3 | T2-EXPIRED-EMV | Expired EMV card -> sent online -> host decides | 1 | 119 | 5 | 10 | 10 | **FAIL** | [TC-002507_ATL105_Field_Validation.md](./TC-002507_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation; E12 Card expiry for expired-card test |
| EMV_CONTACTLESS | PURCHASE | TC-002508 | BR1_TS2_TC4 | T4-PUR-SPLIT | Partial approval -> remainder on 2nd card (split tender) | 2 | 239 | 11 | 23 | 19 | **FAIL** | [TC-002508_ATL105_Field_Validation.md](./TC-002508_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation; E190/58 Tag 9F02 vs NonfuelAmount; E84/58 Step 2 SegmentLength vs possible remainder; E190 Step 2 9F02 = step 2 amount |
| EMV_CONTACTLESS | PURCHASE | TC-002512 | BR1_TS2_TC8 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 206 | 4 | 15 | 17 | **FAIL** | [TC-002512_ATL105_Field_Validation.md](./TC-002512_ATL105_Field_Validation.md) | E188 EMVCardSequenceNumber; E191/192/118 EMV Additional Information (Appendix T oracle); E118 EMVAdditionalInformation |
| MAGSTRIPE | PURCHASE | TC-002539 | BR1_TS3_TC1 | T1-PUR | Purchase approved | 1 | 85 | 0 | 1 | 9 | **PASS_WITH_REVIEW** | [TC-002539_ATL105_Field_Validation.md](./TC-002539_ATL105_Field_Validation.md) |  |
| MAGSTRIPE | PURCHASE | TC-002540 | BR1_TS3_TC2 | T2-RFID-TERMINAL | Swiped/keyed at RFID-equipped terminal -> approved | 1 | 85 | 1 | 1 | 9 | **FAIL** | [TC-002540_ATL105_Field_Validation.md](./TC-002540_ATL105_Field_Validation.md) | E113 Terminal capability 8 vs Table 031 |
| MAGSTRIPE | PURCHASE | TC-002541 | BR1_TS3_TC5 | T4-PUR-SPLIT | Partial approval -> remainder on 2nd card (split tender) | 2 | 172 | 1 | 8 | 17 | **FAIL** | [TC-002541_ATL105_Field_Validation.md](./TC-002541_ATL105_Field_Validation.md) | E84/58 Step 2 SegmentLength vs possible remainder |
| MAGSTRIPE | PURCHASE | TC-002545 | BR1_TS3_TC9 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 171 | 0 | 7 | 17 | **PASS_WITH_REVIEW** | [TC-002545_ATL105_Field_Validation.md](./TC-002545_ATL105_Field_Validation.md) |  |
| MAGSTRIPE | PURCHASE | TC-002551 | BR1_TS3_TC20 | T8-PUR-SETTLE | Purchase -> Settlement (read and cut 222222) | 2 | 126 | 1 | 4 | 10 | **FAIL** | [TC-002551_ATL105_Field_Validation.md](./TC-002551_ATL105_Field_Validation.md) | E13 CardLabel (width) |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-002575 | BR1_TS4_TC1 | T1-PUR | Purchase approved | 1 | 85 | 0 | 1 | 9 | **PASS_WITH_REVIEW** | [TC-002575_ATL105_Field_Validation.md](./TC-002575_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-002576 | BR1_TS4_TC2 | T4-PUR-SPLIT | Partial approval -> remainder on 2nd card (split tender) | 2 | 172 | 1 | 8 | 17 | **FAIL** | [TC-002576_ATL105_Field_Validation.md](./TC-002576_ATL105_Field_Validation.md) | E84/58 Step 2 SegmentLength vs possible remainder |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-002580 | BR1_TS4_TC6 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 171 | 0 | 7 | 17 | **PASS_WITH_REVIEW** | [TC-002580_ATL105_Field_Validation.md](./TC-002580_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-002581 | BR1_TS4_TC9 | T6-PUR-NO-RESPONSE | Purchase, no response -> Time-out Reversal | 2 | 173 | 0 | 2 | 18 | **PASS_WITH_REVIEW** | [TC-002581_ATL105_Field_Validation.md](./TC-002581_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MAGSTRIPE | PURCHASE | TC-002586 | BR1_TS4_TC17 | T8-PUR-SETTLE | Purchase -> Settlement (read and cut 222222) | 2 | 126 | 1 | 4 | 10 | **FAIL** | [TC-002586_ATL105_Field_Validation.md](./TC-002586_ATL105_Field_Validation.md) | E13 CardLabel (width) |
| MANUAL | PURCHASE | TC-002606 | BR1_TS5_TC1 | T1-PUR | Purchase approved | 1 | 83 | 0 | 1 | 9 | **PASS_WITH_REVIEW** | [TC-002606_ATL105_Field_Validation.md](./TC-002606_ATL105_Field_Validation.md) |  |
| MANUAL | PURCHASE | TC-002607 | BR1_TS5_TC2 | T2-3SWIPES | 3 failed swipes -> keyed entry -> approved | 1 | 83 | 0 | 1 | 9 | **PASS_WITH_REVIEW** | [TC-002607_ATL105_Field_Validation.md](./TC-002607_ATL105_Field_Validation.md) |  |
| MANUAL | PURCHASE | TC-002609 | BR1_TS5_TC4 | T4-PUR-SPLIT | Partial approval -> remainder on 2nd card (split tender) | 2 | 168 | 1 | 8 | 17 | **FAIL** | [TC-002609_ATL105_Field_Validation.md](./TC-002609_ATL105_Field_Validation.md) | E84/58 Step 2 SegmentLength vs possible remainder |
| MANUAL | PURCHASE | TC-002613 | BR1_TS5_TC8 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 168 | 2 | 7 | 17 | **FAIL** | [TC-002613_ATL105_Field_Validation.md](./TC-002613_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E12 Expiry = original expiry |
| MANUAL | PURCHASE | TC-002619 | BR1_TS5_TC19 | T8-PUR-SETTLE | Purchase -> Settlement (read and cut 222222) | 2 | 124 | 1 | 4 | 10 | **FAIL** | [TC-002619_ATL105_Field_Validation.md](./TC-002619_ATL105_Field_Validation.md) | E13 CardLabel (width) |
| EMV_FALLBACK | PURCHASE | TC-002643 | BR1_TS6_TC1 | T1-PUR | Purchase approved | 1 | 84 | 2 | 1 | 9 | **FAIL** | [TC-002643_ATL105_Field_Validation.md](./TC-002643_ATL105_Field_Validation.md) | E113/12 Table 005 vs Element 12 content; E113/12 Chip-fallback code vs data |
| EMV_FALLBACK | PURCHASE | TC-002644 | BR1_TS6_TC2 | T2-CHIP-KEYED | Chip unreadable -> keyed fallback (79) -> approved | 1 | 84 | 2 | 1 | 9 | **FAIL** | [TC-002644_ATL105_Field_Validation.md](./TC-002644_ATL105_Field_Validation.md) | E113/12 Table 005 vs Element 12 content; E113/12 Chip-fallback code vs data |
| EMV_FALLBACK | PURCHASE | TC-002645 | BR1_TS6_TC4 | T4-PUR-SPLIT | Partial approval -> remainder on 2nd card (split tender) | 2 | 170 | 5 | 8 | 17 | **FAIL** | [TC-002645_ATL105_Field_Validation.md](./TC-002645_ATL105_Field_Validation.md) | E113/12 Table 005 vs Element 12 content; E113/12 Chip-fallback code vs data; E84/58 Step 2 SegmentLength vs possible remainder |
| EMV_FALLBACK | PURCHASE | TC-002649 | BR1_TS6_TC8 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 170 | 3 | 7 | 17 | **FAIL** | [TC-002649_ATL105_Field_Validation.md](./TC-002649_ATL105_Field_Validation.md) | E113/12 Table 005 vs Element 12 content; E113/12 Chip-fallback code vs data; E12 CardDiscretionaryBlockData.Expiry |
| EMV_FALLBACK | PURCHASE | TC-002655 | BR1_TS6_TC19 | T8-PUR-SETTLE | Purchase -> Settlement (read and cut 222222) | 2 | 125 | 3 | 4 | 10 | **FAIL** | [TC-002655_ATL105_Field_Validation.md](./TC-002655_ATL105_Field_Validation.md) | E113/12 Table 005 vs Element 12 content; E113/12 Chip-fallback code vs data; E13 CardLabel (width) |
| CREDENTIAL_ON_FILE | PURCHASE | TC-002675 | BR1_TS7_TC1 | T1-PUR | Purchase approved | 1 | 83 | 0 | 1 | 9 | **PASS_WITH_REVIEW** | [TC-002675_ATL105_Field_Validation.md](./TC-002675_ATL105_Field_Validation.md) |  |
| CREDENTIAL_ON_FILE | PURCHASE | TC-002676 | BR1_TS7_TC2 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 168 | 2 | 7 | 17 | **FAIL** | [TC-002676_ATL105_Field_Validation.md](./TC-002676_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E12 Expiry = original expiry |
| CREDENTIAL_ON_FILE | PURCHASE | TC-002677 | BR1_TS7_TC5 | T6-PUR-NO-RESPONSE | Purchase, no response -> Time-out Reversal | 2 | 170 | 2 | 2 | 18 | **FAIL** | [TC-002677_ATL105_Field_Validation.md](./TC-002677_ATL105_Field_Validation.md) | E12 CardDiscretionaryBlockData.Expiry; E12 Expiry = original expiry |
| CREDENTIAL_ON_FILE | PURCHASE | TC-002682 | BR1_TS7_TC13 | T8-PUR-SETTLE | Purchase -> Settlement (read and cut 222222) | 2 | 124 | 1 | 4 | 10 | **FAIL** | [TC-002682_ATL105_Field_Validation.md](./TC-002682_ATL105_Field_Validation.md) | E13 CardLabel (width) |
| CREDENTIAL_ON_FILE | PURCHASE | TC-002688 | BR1_TS7_TC21 | T9-PUR-AVS | Purchase with AVS mismatch -> approved | 1 | 83 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-002688_ATL105_Field_Validation.md](./TC-002688_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MOBILE | PURCHASE | TC-002703 | BR1_TS8_TC1 | T1-PUR | Purchase approved | 1 | 84 | 0 | 2 | 9 | **PASS_WITH_REVIEW** | [TC-002703_ATL105_Field_Validation.md](./TC-002703_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MOBILE | PURCHASE | TC-002704 | BR1_TS8_TC2 | T4-PUR-SPLIT | Partial approval -> remainder on 2nd card (split tender) | 2 | 170 | 1 | 10 | 17 | **FAIL** | [TC-002704_ATL105_Field_Validation.md](./TC-002704_ATL105_Field_Validation.md) | E84/58 Step 2 SegmentLength vs possible remainder |
| CONTACTLESS_MOBILE | PURCHASE | TC-002708 | BR1_TS8_TC6 | T5-PUR-REVERSAL | Purchase -> Purchase Reversal | 2 | 170 | 0 | 8 | 17 | **PASS_WITH_REVIEW** | [TC-002708_ATL105_Field_Validation.md](./TC-002708_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MOBILE | PURCHASE | TC-002709 | BR1_TS8_TC9 | T6-PUR-NO-RESPONSE | Purchase, no response -> Time-out Reversal | 2 | 172 | 0 | 3 | 18 | **PASS_WITH_REVIEW** | [TC-002709_ATL105_Field_Validation.md](./TC-002709_ATL105_Field_Validation.md) |  |
| CONTACTLESS_MOBILE | PURCHASE | TC-002714 | BR1_TS8_TC17 | T8-PUR-SETTLE | Purchase -> Settlement (read and cut 222222) | 2 | 125 | 1 | 5 | 10 | **FAIL** | [TC-002714_ATL105_Field_Validation.md](./TC-002714_ATL105_Field_Validation.md) | E13 CardLabel (width) |
