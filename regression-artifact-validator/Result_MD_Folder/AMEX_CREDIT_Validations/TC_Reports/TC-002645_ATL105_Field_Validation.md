# ATL105 Field-Level Test Data Validation - TC-002645

| Item | Value |
|---|---|
| Test case | TC-002645 (BR1_TS6_TC4) |
| Name | BUYPASS ATL105 American Express Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → remainder on 2nd card (split tender) |
| Scenario | TS-ATL105-CREDIT-AMERICAN_EXPRESS-PURCHASE-EMV_FALLBACK |
| Template / type | T4-PUR-SPLIT / Partial approval |
| Objective | Partial approval -> remainder on 2nd card (split tender) |
| Transaction type | Financial Transaction Request → Financial Transaction Request |
| TC status | needs_client_input (catalog: needs_client_input) |
| Open items | ["Partial-approval test card (card A) and a second card (card B)."] |
| Test data file(s) | TD-TC-002645.step1.purchase.json, TD-TC-002645.step2.split-tender.json |
| Specification | BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T) |
| Validator | regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles |
| Run | 2026-11-03T16:42:27.8464042 |
| Overall verdict | **FAIL** |
| Counts | PASS 170 / FAIL 5 / REVIEW 8 / N/A 17 (individual field checks) |

## What is wrong in this test case and how to fix it

**3 defect(s) to fix.** Each one shows where it is in the test data, what the AI generated, why that is wrong, and the value that should be there.

### Defect 1 – Chip-fallback entry mode `79` ("fallback to manual") sent with swiped card data

| | |
|---|---|
| **Where** | `TD-TC-002645.step1.purchase.json` line 34 → `Variable Information Data Segment` → Table 005 entry → `VariableInformation` |
| **What the AI generated** | Table 005 (POS entry mode) = `791`, `CardDiscretionaryBlockData` = `280720131342660` = expiry `2807` + service code `201` + discretionary data |
| **What is wrong** | The first two digits of Table 005, `79`, tell the host the card number was **typed in by hand** after the chip could not be read. But `CardDiscretionaryBlockData` holds full Track 2 data (service code and discretionary data), which exists only when the card is **swiped**. The two fields contradict each other, so the host receives an impossible combination. |
| **What it should be** | Change Table 005 to **`801`** (chip card – unaltered track data read, used for EMV fallback to **swipe**) and keep the Track 2 data. Alternatively, if the test really means a keyed fallback, keep `791` and set `CardDiscretionaryBlockData` = `0728` (MMYY), with Segment 100 `SegmentLength` reduced by 11. |
| **Spec reference** | ATL105 Appendix J-1 (p.631): `79` = *"Contact and Contactless chip fallback to manual"*; `80` = *"Chip card capable – unaltered track data read (used for contact EMV fall back to swiped)"*. Ch. 13 Element 12 (p.13-32): *"For a card whose number has been manually entered, the expiration date is in MMYY format"* |

### Defect 2 – Chip-fallback entry mode `79` ("fallback to manual") sent with swiped card data

| | |
|---|---|
| **Where** | `TD-TC-002645.step2.split-tender.json` line 34 → `Variable Information Data Segment` → Table 005 entry → `VariableInformation` |
| **What the AI generated** | Table 005 (POS entry mode) = `791`, `CardDiscretionaryBlockData` = `280320103664349` = expiry `2803` + service code `201` + discretionary data |
| **What is wrong** | The first two digits of Table 005, `79`, tell the host the card number was **typed in by hand** after the chip could not be read. But `CardDiscretionaryBlockData` holds full Track 2 data (service code and discretionary data), which exists only when the card is **swiped**. The two fields contradict each other, so the host receives an impossible combination. |
| **What it should be** | Change Table 005 to **`801`** (chip card – unaltered track data read, used for EMV fallback to **swipe**) and keep the Track 2 data. Alternatively, if the test really means a keyed fallback, keep `791` and set `CardDiscretionaryBlockData` = `0328` (MMYY), with Segment 100 `SegmentLength` reduced by 11. |
| **Spec reference** | ATL105 Appendix J-1 (p.631): `79` = *"Contact and Contactless chip fallback to manual"*; `80` = *"Chip card capable – unaltered track data read (used for contact EMV fall back to swiped)"*. Ch. 13 Element 12 (p.13-32): *"For a card whose number has been manually entered, the expiration date is in MMYY format"* |

### Defect 3 – Second card's message length was calculated with an impossible amount

| | |
|---|---|
| **Where** | `TD-TC-002645.step2.split-tender.json` line 7 → `Standard Message Data Segment` → `SegmentLength` |
| **What the AI generated** | `SegmentLength` = `092`; amount used for the calculation = `15681335` ($156,813.35) |
| **What is wrong** | In a split-tender sale, card A pays part of the bill and card B pays **the rest**. Card B's amount is correctly left as a run-time value (`<RemainingBalance …>`). But the AI calculated card B's `SegmentLength` (the character count of Segment 100) using a made-up 8-digit amount `15681335` ($156,813.35) from the test case. What is left of the original bill can never be that large, so this length can never be right. |
| **What it should be** | Calculate `SegmentLength` **after** the real remainder is filled in. For a remainder of 1–4 digits it is between **`085`** and **`088`**. Also change the provisional amount in the test case to a value smaller than the original amount. |
| **Spec reference** | ATL105 Ch. 12.1 F2 (Segment Length = sum of the fields + separators); p.84 (remaining balance = requested − approved partial amount); Ch. 13 Element 58 |

### Items to confirm (not defects)

These cannot be decided from the specification alone. They do not make the test data wrong.

| Item | Why it is open | Who confirms |
|---|---|---|
| Terminal ID / merchant number | The value is a placeholder. BUYPASS assigns the real device type, merchant and device number. | Client merchant list |
| Amount filled at run time | Correct approach. The real amount must fit the Element 58 length limit. | Execution |
| Length reserved for a run-time value | The declared length leaves room for a run-time value of a plausible size. | Execution |
| Segment 100 length with a run-time value | Cannot be confirmed until the run-time value is filled in. | Execution |
| Value taken from the previous response | Correctly left as a run-time value (e.g. Approval Number from step 1). It can only be checked during execution. | Execution |
| Split-tender remainder | The run-time formula is right, but the sample amount in the test case is not a valid remainder. | AI team (fix the test case sample) |

---

# Technical evidence

The tables below are the validator's raw checks behind the explanations above.

## Failing checks (FAIL)

| # | Area | Field | Element | Value | Rule | Finding | Spec source |
|---|---|---|---|---|---|---|---|
| 1 | Entry mode | Table 005 vs Element 12 content | 113/12 | `791 / 280720131342660` | APPJ-KEYED-NO-TRACK | PAN entry '79' (Contact and Contactless chip fallback to manual) means the card was not read; Element 12 must carry the manually entered expiry (MMYY), not Track 2 data. Generated value is full Track 2 data (expiry + service code + discretionary); a swiped chip fallback must use PAN entry 80 ('used for contact EMV fall back to swiped'). | App. J-1 (p.631); 13 E12 (p.13-32) |
| 2 | Entry mode | Chip-fallback code vs data | 113/12 | `791 / 280720131342660` | APPJ-FALLBACK-79-80 | Chip fallback: swiped fallback => 80 + Track 2 data; keyed fallback => 79 + MMYY expiry only. Generated: 79 with Track 2 data. | App. J-1 (p.631) codes 79/80 |
| 3 | Entry mode | Table 005 vs Element 12 content | 113/12 | `791 / 280320103664349` | APPJ-KEYED-NO-TRACK | PAN entry '79' (Contact and Contactless chip fallback to manual) means the card was not read; Element 12 must carry the manually entered expiry (MMYY), not Track 2 data. Generated value is full Track 2 data (expiry + service code + discretionary); a swiped chip fallback must use PAN entry 80 ('used for contact EMV fall back to swiped'). | App. J-1 (p.631); 13 E12 (p.13-32) |
| 4 | Entry mode | Chip-fallback code vs data | 113/12 | `791 / 280320103664349` | APPJ-FALLBACK-79-80 | Chip fallback: swiped fallback => 80 + Track 2 data; keyed fallback => 79 + MMYY expiry only. Generated: 79 with Track 2 data. | App. J-1 (p.631) codes 79/80 |
| 5 | Flow (step 1 -> step 2, split_tender) | Step 2 SegmentLength vs possible remainder | 84/58 | `092 => amount width 8` | SPLIT-TENDER-LENGTH | Any valid remainder of 1500 has 1-4 digits, so Segment 100 length must be 85-88; declared 092 implies 8 digits (built from the TC provisional amount '15681335'). | 12.1 F2; 13 E58; p.84 |

## Review checks (REVIEW)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | NonfuelAmount | 58 | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>` | SEG100-E58-RUNTIME | **REVIEW** | Runtime placeholder - value comes from the previous leg's response; must resolve to N 1-8 digits (American Express) at execution. | 13 E58 (p.384) |
| Seg 100 | SegmentLength (implied runtime width) | 84 | `092` | E84-SEG100-RUNTIME-WIDTH | **REVIEW** | Declared length implies the runtime value(s) [NonfuelAmount] occupy 8 character(s) (within Element 58 limit). | 12.1 F2; 13 E5/E58 |
| Seg 100 | SegmentLength (value) | 84 | `092 (computed with placeholder text: 160)` | E84-SEG100-LENGTH | **REVIEW** | Contains a runtime value; declared length cannot be verified until resolved (sum of transmitted fields (17) + one Field Separator after each). | 12.1 F2; 11.8.1 max 619 |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [flow_response_runtime_value] | - | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response ...(len 76) \| TC: 15681335` | TC-TD-FIDELITY | **REVIEW** | TD carries the runtime expression; TC carries a provisional value. | TC request |
| TC link_defs (step 2 <- step 1) | Nonfuel Amount from step 1 response | - | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>` | carry_response_field | **REVIEW** | Correctly left as a runtime link (response values are processor-generated). | p.84: Amount = requested amount minus the Approved Amount (Element 6) of the partial approval response (p.84 'remaining balance due'); runtime link. |
| Flow (step 1 -> step 2, split_tender) | Step 2 NonfuelAmount (remainder) | 58 | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>` | SPLIT-TENDER-REMAINDER | **REVIEW** | Runtime expression is correct in intent (requested - Approved Amount E6). TC provisional value '15681335' is NOT a valid remainder of 1500 (exceeds the original amount). | p.84; 13 E58 |

## Leg 1: `TD-TC-002645.step1.purchase.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `Financial Transaction Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'Financial Transaction Request' (role purchase, Transaction Type '0') => physical message 'Financial Transaction Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
| Data Section 1 | MessageFormatVersionIdentifier | 55 | `ATL105` | E55-FINANCIAL-REQUEST | **PASS** | Fixed value 'ATL105' (AN6). | ATL105 11.8.1 F1; 13 E55 |
| Data Section 1 | NumberofSegments | 63 | `02` | E63-EMV-FINANCIAL-REQUEST-COUNT | **PASS** | N up to 2 digits; must equal segments present (2: [100, 111]). | ATL105 11.8.1 F2; 13 E63 |
| Data Section 1 | Section 3 slots | - | `1` | 11.1-SECTION3 | **PASS** | Financial Transaction Request Data Section 3 occupies Field Nos. 4-9 (0..6 segments). | ATL105 11.1 / 11.8.1 p.11-43 |
| Segment presence | Standard Message Data Segment (100) | - | `present` | SEG-100-PRESENCE | **PASS** | Required in Data Section 2 of every Financial Transaction Request. TC decision: REQUIRED. | ATL105 11.1 / 11.8.1 p.11-42; 12.1 |
| Segment presence | EMV Request Data Segment (130) | - | `absent` | SEG130-R-017/R-001 | **NA** | POS entry mode '79' (Contact and Contactless chip fallback to manual) is not a chip read; no EMV data applies. | ATL105 10.14.2.4; 12.20; 13 E5 |
| Segment presence | Variable Information Data Segment (111) | - | `present` | SEG-111-PRESENCE | **PASS** | Table 031 must be populated for all transactions (BR-569-1). TC decision: REQUIRED_BY_CONDITION. | ATL105 App. I-20 (p.569) |
| Seg 100 | Field layout (17 fields) | - | `17 keys` | LAYOUT-Seg100 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 100 | SegmentType | 85 | `100` | E85-SEG100-TYPE | **PASS** | N3, fixed value 100. | 12.1 F1 |
| Seg 100 | InformationByte | 44 | `0` | SEG100-E44 | **PASS** | AN1: '?' download, '0' single-message, '1' multimessage. | 13 E44 (p.379) |
| Seg 100 | TerminalIdentifier | 102 | `JX72948216050257001` | SEG100-E102-FORMAT | **PASS** | AN up to 22: Device Type(2)+State Code(2)+Merchant No.(6-15)+Device No.(3) => 13..22 chars. | 13 E102 (p.410-411) |
| Seg 100 | TerminalIdentifier.StateCode | 102 | `72` | APPD-ANSI-STATE | **PASS** | Positions 3-4 must be a valid ANSI state code (Appendix D). | 13 E102; App. D |
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | PromptCode | 78 | `0020` | SEG100-E78-FORMAT | **PASS** | AN4: pos 1 Transaction Type (App. G) + pos 2-4 Card Type (App. E). | 12.1 F5; 13 E78 |
| Seg 100 | PromptCode.TransactionType | 78 | `0` | APPG-TXN-TYPE | **PASS** | Appendix G: POS Purchase/Capture; TC leg (role purchase) requires '0'. | App. G (p.545) |
| Seg 100 | PromptCode.CardType | 78 | `020` | APPE-CARD-TYPE | **PASS** | Appendix E code 020 = Credit (AMEX credit program). | App. E-4 |
| Seg 100 | AccountNumber | 2 | `340366675921211` | SEG100-R-041 | **PASS** | ANS, non-TransArmor request up to 24 bytes; card PAN numeric. | 13 E2 (p.364) |
| Seg 100 | AccountNumber.Luhn | 2 | `340366675921211` | PAN-LUHN | **PASS** | PAN check digit (ISO/IEC 7812 mod-10). | ISO/IEC 7812 |
| Seg 100 | AccountNumber.IIN | 2 | `340366` | PAN-IIN-AMEX | **PASS** | American Express IIN 34 / 37, PAN length 15 (TC network AMERICAN_EXPRESS). | BUYPASS IIN Reference Manual |
| Seg 100 | CardDiscretionaryBlockData | 12 | `280720131342660` | SEG100-R-042 | **PASS** | AN up to 51; Track 2 data after '=' => YYMM expiry + 3-digit service code + discretionary. | 12.1 F7; 13 E12 (p.364-365) |
| Seg 100 | CardDiscretionaryBlockData.Expiry | 12 | `2807` | TRACK2-EXPIRY-YYMM | **PASS** | Expiry YYMM; month '07' must be 01-12 (valid, not expired as of 2611). | 13 E12; Track 2 (ISO 7813) |
| Seg 100 | CardDiscretionaryBlockData.ServiceCode | 12 | `201` | TRACK2-SERVICE-CODE | **PASS** | Service code 3 digits, 1st digit 1-7 (ISO 7813). | ISO 7813 |
| Seg 100 | Account+Discretionary (Track 2) | 2+12 | `31` | TRACK2-COMBINED-37 | **PASS** | Account Number + '=' + Card Discretionary Block Data must not exceed 37 for Track 2. | 13 E12 (p.364) |
| Seg 100 | EncryptedPINBlockData | 33 | `(empty)` | SEG100-E33-COND | **NA** | Not populated; conditional field not applicable to this transaction. AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry. | 12.1 |
| Seg 100 | PumpLaneNumber | 79 | `(empty)` | SEG100-E79-COND | **NA** | Not populated; conditional field not applicable to this transaction. N2; fuel/pump context only. | 12.1 |
| Seg 100 | FuelPurchaseAmount | 41 | `(empty)` | SEG100-E41-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; fuel purchase only. | 12.1 |
| Seg 100 | TaxAmount | 99 | `(empty)` | SEG100-E99-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; optional tax amount. | 12.1 |
| Seg 100 | CashAmount | 17 | `(empty)` | SEG100-E17-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; cash back only. | 12.1 |
| Seg 100 | LocalDateandLocalTime | 49 | `(empty)` | SEG100-E49-COND | **NA** | Not populated; conditional field not applicable to this transaction. N10; preauthorized transaction only. | 12.1 |
| Seg 100 | NonfuelAmount | 58 | `1500` | SEG100-E58-FORMAT | **PASS** | N, up to 8 digits for AMEX (8 only for American Express), 2 assumed decimals, > 0. | 13 E58 (p.384) |
| Seg 100 | SequenceNumber | 86 | `026451` | E86-FINANCIAL-REQUEST-SHAPE | **PASS** | N6, 000001-999999. | 12.1 F14; 13 E86 |
| Seg 100 | ApprovalNumber | 5 | `` | BR-368-2 | **PASS** | Must be empty on a new Purchase request (an Approval Number marks it preauthorized). | 13 E5 (p.368) |
| Seg 100 | PartialApprovalIndicator | 121 | `1` | SEG100-E121 | **PASS** | N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only. | 13 E121 (p.440) |
| Seg 100 | SegmentLength (format) | 84 | `088` | E84-SEG100-LENGTH | **PASS** | N3, range 1-619. | 12.1 F2; 11.8.1 max 619 |
| Seg 100 | SegmentLength (value) | 84 | `088 (computed 88)` | E84-SEG100-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: sum of transmitted fields (17) + one Field Separator after each. | 12.1 F2; 11.8.1 max 619 |
| Seg 111 | SegmentType | 85 | `111` | E85-SEG111-TYPE | **PASS** | N3, fixed value 111. | 12.10 F1 |
| Seg 111 | Section[1].VariableInformationIndicator | 111 | `005` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[1].VariableInformationLength | 112 | `003` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (3). | 12.10 F4; 13 E112 |
| Seg 111 | Section[1].VariableInformation (Table 005) | 113 | `791` | APPI-T005-R001 | **PASS** | POS entry components are in Appendix J's enumerations. | App. I Table 005 / App. J |
| Seg 111 | Section[1].POS Entry Mode (App. J) | 113 | `791` | APPJ-POS-ENTRY-MODE | **PASS** | PAN entry '79' = Contact and Contactless chip fallback to manual; terminal capability '1' = PIN entry capability (EMV Contact and Contactless). | App. J (p.638) |
| Seg 111 | Section[1].POS Entry Mode vs TC leg entry_code | 113 | `79` | LEG-ENTRY-MODE | **PASS** | TC leg entry_code '79' (Contact and Contactless chip fallback to manual) for entry_mode EMV_FALLBACK. | App. J; TC rule_checks |
| Seg 111 | Section[2].VariableInformationIndicator | 111 | `031` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[2].VariableInformationLength | 112 | `002` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (2). | 12.10 F4; 13 E112 |
| Seg 111 | Section[2].VariableInformation (Table 031) | 113 | `12` | BR-569-1 | **PASS** | n2 fixed, Terminal Capability code in {00-07,09,12} (12 = Hybrid ICC contact & contactless). | App. I-20 (p.569) |
| Seg 111 | Table 031 present | 111 | `yes` | TABLE-031-POPULATED | **PASS** | Terminal Capability (Table 031) must be populated for all transactions. | App. I-20 (p.569) BR-569-1 |
| Seg 111 | Variable Information Section total | - | `17` | 12.10-SECTION-991 | **PASS** | Repeated section total must not exceed 991 bytes. | 12.10 |
| Seg 111 | SegmentLength (format) | 84 | `026` | E84-SEG111-LENGTH | **PASS** | N3, range 1-999. | 12.10 F2 |
| Seg 111 | SegmentLength (value) | 84 | `026 (computed 26)` | E84-SEG111-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+sections+final FS. | 12.10 F2 |
| Entry mode | Table 005 vs Element 12 content | 113/12 | `791 / 280720131342660` | APPJ-KEYED-NO-TRACK | **FAIL** | PAN entry '79' (Contact and Contactless chip fallback to manual) means the card was not read; Element 12 must carry the manually entered expiry (MMYY), not Track 2 data. Generated value is full Track 2 data (expiry + service code + discretionary); a swiped chip fallback must use PAN entry 80 ('used for contact EMV fall back to swiped'). | App. J-1 (p.631); 13 E12 (p.13-32) |
| Entry mode | Chip-fallback code vs data | 113/12 | `791 / 280720131342660` | APPJ-FALLBACK-79-80 | **FAIL** | Chip fallback: swiped fallback => 80 + Track 2 data; keyed fallback => 79 + MMYY expiry only. Generated: 79 with Track 2 data. | App. J-1 (p.631) codes 79/80 |
| Message | Total message length | - | `124` | 11.8.1-MAX-4000 | **PASS** | Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes. | ATL105 11.8.1 note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Standard Message Data Segment.Segment Type [fixed_value] | - | `100 \| TC: 100` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Segment Length | 84 | `088` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '088' (verified in spec checks). | TC method |
| TC<->TD | Standard Message Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Terminal Identifier [realistic_terminal_id] | - | `JX72948216050257001 \| TC: JX72948216050257001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Prompt Code [flow_fixed_transaction_code] | - | `0020 \| TC: 0020` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Account Number [realistic_pan] | - | `340366675921211 \| TC: 340366675921211` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Card Discretionary Block Data [realistic_track2_discretionary] | - | `280720131342660 \| TC: 280720131342660` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Encrypted PIN Block Data [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Pump/Lane Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Fuel Purchase Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [scenario_parameter_literal] | - | `1500 \| TC: 1500` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Tax Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Cash Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Sequence Number [qe_example_value] | - | `026451 \| TC: 026451` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Approval Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Local Date and Local Time [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Partial Approval Indicator [flow_fixed_transaction_code] | - | `1 \| TC: 1` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator | - | `005 \| TC: 005` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length | - | `003 \| TC: 003` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information | - | `791 \| TC: 791` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [2] | - | `031 \| TC: 031` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [2] | - | `002 \| TC: 002` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [2] | - | `12 \| TC: 12` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Segment Type | - | `111 \| TC: 111` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Fleet Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Product Code Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EBT Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Purchase Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Loyalty Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Check Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | ECA/ TeleCheck Data Segment Format ® | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | SKU Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | NFC Payment Tokenization Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Moneris Data Segment (Request) | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Tax by Product Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Enhanced Fleet Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Request InComm OTC Market Basket Data | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Network Token Data Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EV Charging Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |

## Leg 2: `TD-TC-002645.step2.split-tender.json`

### Field-by-field ATL105 checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Message | Message type | - | `Financial Transaction Request` | 11.1/11.4/11.8.1 | **PASS** | TC leg message 'Financial Transaction Request' (role split_tender, Transaction Type '0') => physical message 'Financial Transaction Request'. | ATL105 11.1 / 11.4 / 11.8.1 |
| Data Section 1 | MessageFormatVersionIdentifier | 55 | `ATL105` | E55-FINANCIAL-REQUEST | **PASS** | Fixed value 'ATL105' (AN6). | ATL105 11.8.1 F1; 13 E55 |
| Data Section 1 | NumberofSegments | 63 | `02` | E63-EMV-FINANCIAL-REQUEST-COUNT | **PASS** | N up to 2 digits; must equal segments present (2: [100, 111]). | ATL105 11.8.1 F2; 13 E63 |
| Data Section 1 | Section 3 slots | - | `1` | 11.1-SECTION3 | **PASS** | Financial Transaction Request Data Section 3 occupies Field Nos. 4-9 (0..6 segments). | ATL105 11.1 / 11.8.1 p.11-43 |
| Segment presence | Standard Message Data Segment (100) | - | `present` | SEG-100-PRESENCE | **PASS** | Required in Data Section 2 of every Financial Transaction Request. TC decision: REQUIRED. | ATL105 11.1 / 11.8.1 p.11-42; 12.1 |
| Segment presence | EMV Request Data Segment (130) | - | `absent` | SEG130-R-017/R-001 | **NA** | POS entry mode '79' (Contact and Contactless chip fallback to manual) is not a chip read; no EMV data applies. | ATL105 10.14.2.4; 12.20; 13 E5 |
| Segment presence | Variable Information Data Segment (111) | - | `present` | SEG-111-PRESENCE | **PASS** | Table 031 must be populated for all transactions (BR-569-1). TC decision: REQUIRED_BY_CONDITION. | ATL105 App. I-20 (p.569) |
| Seg 100 | Field layout (17 fields) | - | `17 keys` | LAYOUT-Seg100 | **PASS** | All spec fields present in spec order | ATL105 Chapter 12 layout table |
| Seg 100 | SegmentType | 85 | `100` | E85-SEG100-TYPE | **PASS** | N3, fixed value 100. | 12.1 F1 |
| Seg 100 | InformationByte | 44 | `0` | SEG100-E44 | **PASS** | AN1: '?' download, '0' single-message, '1' multimessage. | 13 E44 (p.379) |
| Seg 100 | TerminalIdentifier | 102 | `JX72948216050257001` | SEG100-E102-FORMAT | **PASS** | AN up to 22: Device Type(2)+State Code(2)+Merchant No.(6-15)+Device No.(3) => 13..22 chars. | 13 E102 (p.410-411) |
| Seg 100 | TerminalIdentifier.StateCode | 102 | `72` | APPD-ANSI-STATE | **PASS** | Positions 3-4 must be a valid ANSI state code (Appendix D). | 13 E102; App. D |
| Seg 100 | TerminalIdentifier.DeviceType/Merchant | 102 | `JX / 948216050257 / 001` | SEG100-E102-ASSIGNED | **REVIEW** | Device Type, Merchant Number and Device Number must be BUYPASS-assigned; TC merchant is a placeholder (merchant_provenance=placeholder). | 13 E102 |
| Seg 100 | PromptCode | 78 | `0020` | SEG100-E78-FORMAT | **PASS** | AN4: pos 1 Transaction Type (App. G) + pos 2-4 Card Type (App. E). | 12.1 F5; 13 E78 |
| Seg 100 | PromptCode.TransactionType | 78 | `0` | APPG-TXN-TYPE | **PASS** | Appendix G: POS Purchase/Capture; TC leg (role split_tender) requires '0'. | App. G (p.545) |
| Seg 100 | PromptCode.CardType | 78 | `020` | APPE-CARD-TYPE | **PASS** | Appendix E code 020 = Credit (AMEX credit program). | App. E-4 |
| Seg 100 | AccountNumber | 2 | `348988493070392` | SEG100-R-041 | **PASS** | ANS, non-TransArmor request up to 24 bytes; card PAN numeric. | 13 E2 (p.364) |
| Seg 100 | AccountNumber.Luhn | 2 | `348988493070392` | PAN-LUHN | **PASS** | PAN check digit (ISO/IEC 7812 mod-10). | ISO/IEC 7812 |
| Seg 100 | AccountNumber.IIN | 2 | `348988` | PAN-IIN-AMEX | **PASS** | American Express IIN 34 / 37, PAN length 15 (TC network AMERICAN_EXPRESS). | BUYPASS IIN Reference Manual |
| Seg 100 | CardDiscretionaryBlockData | 12 | `280320103664349` | SEG100-R-042 | **PASS** | AN up to 51; Track 2 data after '=' => YYMM expiry + 3-digit service code + discretionary. | 12.1 F7; 13 E12 (p.364-365) |
| Seg 100 | CardDiscretionaryBlockData.Expiry | 12 | `2803` | TRACK2-EXPIRY-YYMM | **PASS** | Expiry YYMM; month '03' must be 01-12 (valid, not expired as of 2611). | 13 E12; Track 2 (ISO 7813) |
| Seg 100 | CardDiscretionaryBlockData.ServiceCode | 12 | `201` | TRACK2-SERVICE-CODE | **PASS** | Service code 3 digits, 1st digit 1-7 (ISO 7813). | ISO 7813 |
| Seg 100 | Account+Discretionary (Track 2) | 2+12 | `31` | TRACK2-COMBINED-37 | **PASS** | Account Number + '=' + Card Discretionary Block Data must not exceed 37 for Track 2. | 13 E12 (p.364) |
| Seg 100 | EncryptedPINBlockData | 33 | `(empty)` | SEG100-E33-COND | **NA** | Not populated; conditional field not applicable to this transaction. AN36 (KSN 16-20 + PIN block 16); conditional on PIN entry. | 12.1 |
| Seg 100 | PumpLaneNumber | 79 | `(empty)` | SEG100-E79-COND | **NA** | Not populated; conditional field not applicable to this transaction. N2; fuel/pump context only. | 12.1 |
| Seg 100 | FuelPurchaseAmount | 41 | `(empty)` | SEG100-E41-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; fuel purchase only. | 12.1 |
| Seg 100 | TaxAmount | 99 | `(empty)` | SEG100-E99-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; optional tax amount. | 12.1 |
| Seg 100 | CashAmount | 17 | `(empty)` | SEG100-E17-COND | **NA** | Not populated; conditional field not applicable to this transaction. N up to 8; cash back only. | 12.1 |
| Seg 100 | LocalDateandLocalTime | 49 | `(empty)` | SEG100-E49-COND | **NA** | Not populated; conditional field not applicable to this transaction. N10; preauthorized transaction only. | 12.1 |
| Seg 100 | NonfuelAmount | 58 | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>` | SEG100-E58-RUNTIME | **REVIEW** | Runtime placeholder - value comes from the previous leg's response; must resolve to N 1-8 digits (American Express) at execution. | 13 E58 (p.384) |
| Seg 100 | SequenceNumber | 86 | `026452` | E86-FINANCIAL-REQUEST-SHAPE | **PASS** | N6, 000001-999999. | 12.1 F14; 13 E86 |
| Seg 100 | ApprovalNumber | 5 | `` | BR-368-2 | **PASS** | Must be empty on a new Purchase request (an Approval Number marks it preauthorized). | 13 E5 (p.368) |
| Seg 100 | PartialApprovalIndicator | 121 | `1` | SEG100-E121 | **PASS** | N1, required on all transactions: 0 not supported, 1 supported, 5 Amex prepaid only. | 13 E121 (p.440) |
| Seg 100 | SegmentLength (implied runtime width) | 84 | `092` | E84-SEG100-RUNTIME-WIDTH | **REVIEW** | Declared length implies the runtime value(s) [NonfuelAmount] occupy 8 character(s) (within Element 58 limit). | 12.1 F2; 13 E5/E58 |
| Seg 100 | SegmentLength (format) | 84 | `092` | E84-SEG100-LENGTH | **PASS** | N3, range 1-619. | 12.1 F2; 11.8.1 max 619 |
| Seg 100 | SegmentLength (value) | 84 | `092 (computed with placeholder text: 160)` | E84-SEG100-LENGTH | **REVIEW** | Contains a runtime value; declared length cannot be verified until resolved (sum of transmitted fields (17) + one Field Separator after each). | 12.1 F2; 11.8.1 max 619 |
| Seg 111 | SegmentType | 85 | `111` | E85-SEG111-TYPE | **PASS** | N3, fixed value 111. | 12.10 F1 |
| Seg 111 | Section[1].VariableInformationIndicator | 111 | `005` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[1].VariableInformationLength | 112 | `003` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (3). | 12.10 F4; 13 E112 |
| Seg 111 | Section[1].VariableInformation (Table 005) | 113 | `791` | APPI-T005-R001 | **PASS** | POS entry components are in Appendix J's enumerations. | App. I Table 005 / App. J |
| Seg 111 | Section[1].POS Entry Mode (App. J) | 113 | `791` | APPJ-POS-ENTRY-MODE | **PASS** | PAN entry '79' = Contact and Contactless chip fallback to manual; terminal capability '1' = PIN entry capability (EMV Contact and Contactless). | App. J (p.638) |
| Seg 111 | Section[1].POS Entry Mode vs TC leg entry_code | 113 | `79` | LEG-ENTRY-MODE | **PASS** | TC leg entry_code '79' (Contact and Contactless chip fallback to manual) for entry_mode EMV_FALLBACK. | App. J; TC rule_checks |
| Seg 111 | Section[2].VariableInformationIndicator | 111 | `031` | SEG111-E111 | **PASS** | N3 Table ID (Appendix I catalog: SOURCE_LOCATED). | 12.10 F3; 13 E111 |
| Seg 111 | Section[2].VariableInformationLength | 112 | `002` | SEG111-E112 | **PASS** | N3 001-999 and equal to the length of Element 113 (2). | 12.10 F4; 13 E112 |
| Seg 111 | Section[2].VariableInformation (Table 031) | 113 | `12` | BR-569-1 | **PASS** | n2 fixed, Terminal Capability code in {00-07,09,12} (12 = Hybrid ICC contact & contactless). | App. I-20 (p.569) |
| Seg 111 | Table 031 present | 111 | `yes` | TABLE-031-POPULATED | **PASS** | Terminal Capability (Table 031) must be populated for all transactions. | App. I-20 (p.569) BR-569-1 |
| Seg 111 | Variable Information Section total | - | `17` | 12.10-SECTION-991 | **PASS** | Repeated section total must not exceed 991 bytes. | 12.10 |
| Seg 111 | SegmentLength (format) | 84 | `026` | E84-SEG111-LENGTH | **PASS** | N3, range 1-999. | 12.10 F2 |
| Seg 111 | SegmentLength (value) | 84 | `026 (computed 26)` | E84-SEG111-LENGTH | **PASS** | Must include Segment Type, all fields and Field Separators: type+FS+length+FS+sections+final FS. | 12.10 F2 |
| Entry mode | Table 005 vs Element 12 content | 113/12 | `791 / 280320103664349` | APPJ-KEYED-NO-TRACK | **FAIL** | PAN entry '79' (Contact and Contactless chip fallback to manual) means the card was not read; Element 12 must carry the manually entered expiry (MMYY), not Track 2 data. Generated value is full Track 2 data (expiry + service code + discretionary); a swiped chip fallback must use PAN entry 80 ('used for contact EMV fall back to swiped'). | App. J-1 (p.631); 13 E12 (p.13-32) |
| Entry mode | Chip-fallback code vs data | 113/12 | `791 / 280320103664349` | APPJ-FALLBACK-79-80 | **FAIL** | Chip fallback: swiped fallback => 80 + Track 2 data; keyed fallback => 79 + MMYY expiry only. Generated: 79 with Track 2 data. | App. J-1 (p.631) codes 79/80 |
| Message | Total message length | - | `128` | 11.8.1-MAX-4000 | **PASS** | Computed wire length (Data Section 1 + segments) must not exceed 4,000 bytes. | ATL105 11.8.1 note |

### Test data vs test case request (generation fidelity)

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| TC<->TD | Standard Message Data Segment.Segment Type [fixed_value] | - | `100 \| TC: 100` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Segment Length | 84 | `092` | TC-DERIVED | **NA** | TC defers this value to wire encoding; TD computed '092' (verified in spec checks). | TC method |
| TC<->TD | Standard Message Data Segment.Information Byte [test_data_config_value] | - | `0 \| TC: 0` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Terminal Identifier [realistic_terminal_id] | - | `JX72948216050257001 \| TC: JX72948216050257001` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Prompt Code [flow_fixed_transaction_code] | - | `0020 \| TC: 0020` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Account Number [realistic_pan] | - | `348988493070392 \| TC: 348988493070392` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Card Discretionary Block Data [realistic_track2_discretionary] | - | `280320103664349 \| TC: 280320103664349` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Encrypted PIN Block Data [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Pump/Lane Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Fuel Purchase Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Nonfuel Amount [flow_response_runtime_value] | - | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response ...(len 76) \| TC: 15681335` | TC-TD-FIDELITY | **REVIEW** | TD carries the runtime expression; TC carries a provisional value. | TC request |
| TC<->TD | Standard Message Data Segment.Tax Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Cash Amount [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Sequence Number [flow_new_value_required] | - | `026452 \| TC: 026452` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Approval Number [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Local Date and Local Time [omitted] | - | `(null) \| TC: (null)` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Standard Message Data Segment.Partial Approval Indicator [test_data_config_value] | - | `1 \| TC: 1` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator | - | `005 \| TC: 005` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length | - | `003 \| TC: 003` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information | - | `791 \| TC: 791` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Indicator [2] | - | `031 \| TC: 031` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information Length [2] | - | `002 \| TC: 002` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Variable Information [2] | - | `12 \| TC: 12` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Variable Information Data Segment.Segment Type | - | `111 \| TC: 111` | TC-TD-FIDELITY | **PASS** | TD value equals TC request value. | TC request |
| TC<->TD | Fleet Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Product Code Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EBT Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Purchase Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Loyalty Card Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Check Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | ECA/ TeleCheck Data Segment Format ® | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | SKU Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | NFC Payment Tokenization Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Moneris Data Segment (Request) | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Tax by Product Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Enhanced Fleet Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Request InComm OTC Market Basket Data | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | Network Token Data Request | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |
| TC<->TD | EV Charging Data Segment | - | `absent` | TC-SEGMENT-DECISION | **PASS** | TC omits this segment; TD omits it. | TC request |

## Test objective and flow checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Objective (T4-PUR-SPLIT) | Test objective | - | `Partial approval -> remainder on 2nd card (split tender)` | T4-PUR-SPLIT | **NA** | catalog_status=needs_client_input; status=needs_client_input; open_items=["Partial-approval test card (card A) and a second card (card B)."] | TC |
| Objective (T4-PUR-SPLIT) | Step 1 PartialApprovalIndicator | 121 | `1` | T4-PAI | **PASS** | Partial approval precondition: Element 121 = 1. | 13 E121 (p.440); p.84 |
| TC rule_checks (step 1) | LEG-TXN-TYPE Prompt Code | - | `0` | value_slice_in | **PASS** | Expected to hold; allowed [0]. | Appendix G Transaction Type of the leg (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-CARD-TYPE Prompt Code | - | `020` | value_slice_in | **PASS** | Expected to hold; allowed [020]. | Appendix E card type (element 78 p.400-401) |
| TC rule_checks (step 1) | LEG-ENTRY-MODE 005 | - | `79` | table_slice_in | **PASS** | Expected to hold; allowed [79]. | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) |
| TC rule_checks (step 2) | LEG-TXN-TYPE Prompt Code | - | `0` | value_slice_in | **PASS** | Expected to hold; allowed [0]. | Appendix G Transaction Type of the leg (element 78 p.400-401) |
| TC rule_checks (step 2) | LEG-CARD-TYPE Prompt Code | - | `020` | value_slice_in | **PASS** | Expected to hold; allowed [020]. | Appendix E card type (element 78 p.400-401) |
| TC rule_checks (step 2) | LEG-ENTRY-MODE 005 | - | `79` | table_slice_in | **PASS** | Expected to hold; allowed [79]. | Appendix J PAN Entry Mode (Table 005 positions 1-2, p.638; follow-on 90 -> 02) |
| TC rule_checks (step 2) | LEG-NO-APPROVAL-ON-NEW-TRANSACTION Approval Number | - | `` | field_absent | **PASS** | Expected to hold. | element 5 (p.368): an Approval Number in a Purchase Request makes it preauthorized |
| TC rule_checks (step 2) | SPLIT-TENDER-CARD-B Account Number | - | `348988493070392 vs step 1 340366675921211` | value_differs_from_step | **PASS** | Expected to hold. | split tender: a different form of payment pays the remaining balance (p.84, p.87) |
| TC rule_checks (step 1) | SEG-111-REQUIRED 111 | - | `[100, 111]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] |
| TC rule_checks (step 1) | TABLE-031-POPULATED 031 | - | `12` | table_slice_in | **PASS** | Expected to hold; allowed [00, 01, 02, 03, 04, 05, 06, 07, 09, 12]. | Terminal Capability values must be populated for all transactions (p.569) |
| TC rule_checks (step 2) | SEG-111-REQUIRED 111 | - | `[100, 111]` | segment_present | **PASS** | Expected to hold. | REQUIRED_BY_CONDITION: 'Terminal Capability values must be populated for all transactions, which' (p.569) [BR-569-1] |
| TC rule_checks (step 2) | TABLE-031-POPULATED 031 | - | `12` | table_slice_in | **PASS** | Expected to hold; allowed [00, 01, 02, 03, 04, 05, 06, 07, 09, 12]. | Terminal Capability values must be populated for all transactions (p.569) |
| TC link_defs (step 2 <- step 1) | Terminal Identifier carried | - | `JX72948216050257001 / JX72948216050257001` | carry_request_field | **PASS** | Value must be identical to step 1. | p.40: A TOR must be sent from the same device as the original financial transaction request (applied to every follow-on and to the next transaction on the same device). |
| TC link_defs (step 2 <- step 1) | Sequence Number differs | - | `026451 / 026452` | must_differ | **PASS** | Value must differ from step 1. | p.40: Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number. |
| TC link_defs (step 2 <- step 1) | Nonfuel Amount from step 1 response | - | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>` | carry_response_field | **REVIEW** | Correctly left as a runtime link (response values are processor-generated). | p.84: Amount = requested amount minus the Approved Amount (Element 6) of the partial approval response (p.84 'remaining balance due'); runtime link. |
| Flow (step 1 -> step 2, split_tender) | Step 2 NonfuelAmount (remainder) | 58 | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>` | SPLIT-TENDER-REMAINDER | **REVIEW** | Runtime expression is correct in intent (requested - Approved Amount E6). TC provisional value '15681335' is NOT a valid remainder of 1500 (exceeds the original amount). | p.84; 13 E58 |
| Flow (step 1 -> step 2, split_tender) | Step 2 SegmentLength vs possible remainder | 84/58 | `092 => amount width 8` | SPLIT-TENDER-LENGTH | **FAIL** | Any valid remainder of 1500 has 1-4 digits, so Segment 100 length must be 85-88; declared 092 implies 8 digits (built from the TC provisional amount '15681335'). | 12.1 F2; 13 E58; p.84 |

---
Verdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); N/A = conditional field correctly not populated / informational.
