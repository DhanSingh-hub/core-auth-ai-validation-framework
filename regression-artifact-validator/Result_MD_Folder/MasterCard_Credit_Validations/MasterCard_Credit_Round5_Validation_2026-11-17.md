# MasterCard Credit – Test Case & Test Data Defect Report – Round 5 (2026-11-17)

| Item | Value |
|---|---|
| Purpose | Defects found by the test team when validating the delivered MasterCard Credit test cases (TC) and test data (TD). For each defect: the ATL105 rule, what the TC/TD contains, what ATL105 requires, the scope and how it was detected. |
| Source of truth | BUYPASS Platform ATL105 Message Format Specifications **Release 2026-4** (October 09, 2026). Page numbers are PDF pages. |
| Validated | 3,205 TCs and their 3,479 TD files from `mastercard_credit_output_1611`: <ul><li>MasterCard Credit network: 2,129 TCs / 2,422 TD.</li><li>Data Element Validation: 1,029 / 1,015.</li><li>Terminal & Host Operations: 47 / 42.</li></ul> |
| Framework | `regression-artifact-validator`: <ul><li>Java `TestDataSpecFieldValidationRunner` – field-by-field, with the App. G/E/I/J/Q/R/T oracles.</li><li>Scripted catalog checks in [Round5_Evidence/scripts](./Round5_Evidence/scripts/).</li><li>Every finding is a row in [MC_Credit_Round5_Findings.csv](./Round5_Evidence/MC_Credit_Round5_Findings.csv) under the `check_id` named below.</li></ul> |
| Earlier rounds | IDs from earlier rounds (CD-xx, R4-xx) are given in the "Earlier ID" column so earlier responses can be traced. See [Round 4](./MasterCard_Credit_Round4_Validation_2026-11-16.md). |

**Bottom line:**
- **Network:** 1,211 of 2,129 TCs have at least one defect, including 231 of the 619 `ready` TCs. Only **388 TCs are both `ready` and defect-free**: General Retail 311, Credential on File 47 and Mail/Phone 30. No other acceptance context has one.
- **Data Element Validation:** 610 of 1,029 TCs have a defect (390 `ready`).
- **Terminal & Host Operations:** 29 of 47 TCs have a defect.

---

## 1. How the test cases and test data were validated

Every TD file was treated as the message the test harness will send, and checked against ATL105 in the order a tester would. Every TC was then checked against its own TD and against ATL105.

| # | Validation step | What is checked | ATL105 basis | Framework `check_id` |
|---|---|---|---|---|
| 1 | Message structure | Message type vs entry mode; Number of Segments; only the segments allowed in that message | 11.1.1 p.166–169, 11.8.1 p.206–207, 11.2.1 p.174, 11.7, E63 p.395 | MSG-02, MSG-03, SEG-OPT-APPL |
| 2 | Segment layout and length | Field order, Field Separators, declared Segment Length = bytes actually sent | 12.1 p.223–225, 12.3, 12.10, 12.20, E84 p.406 | S100-LEN, S111-LEN, S130-LEN, S102-LEN |
| 3 | Element values | Type, length and valid values of each element (PAN, E12 expiry by entry mode, E33, E44, E58, E5, E49, E86, E121, E78 TT/card type) | Ch. 13, App. E, App. G | S100-* |
| 4 | Variable-information tables | Table 005 entry mode vs scenario; Table 030 present and decoded; Table 031; COF tables; E112 = data length | App. I, App. J p.638, App. Q p.690–696 | S111-* |
| 5 | EMV data | CSN = 5F34; E189 = bytes; TLV well-formed; 9F02 = request amount; 9C vs TT; CVM (9F34) vs PIN; E191/192/118 | 12.20, E187–E192 p.476–479, App. R/T | S130-* |
| 6 | Multi-leg flows | Follow-on carries the original sequence number, TID, PAN, entry mode and YYMM expiry; Approval Number on reversals; TOR rules | E86, E5 p.368, §2.3.4 p.47, p.94–95, p.161 | FO-* |
| 7 | TC ↔ TD | The thing the TC claims to test is on the wire. A negative breaks exactly one rule and differs from the approved request. | – | TC-CLAIM-*, TC-DUP-CONFLICT, TC-NC1-NOT-OMITTED |
| 8 | Expected result | Expected Response Code is an E83 value for that outcome; the expected response content can occur in that response message | E83 p.404, 11.1.2 / 11.8.2 vs 11.7 | TC-EXP-*, TC-NAME-EXP |
| 9 | Test-case set vs ATL105 | Every MasterCard credit transaction and entry mode in ATL105 has at least one valid executable TC | p.83, §10.14.2.4 p.161, App. J | coverage (D-30 – D-35) |

**Result classes:**
- **DEFECT** – breaks an ATL105 rule, or the TC cannot prove its objective.
- **REVIEW** – needs SME/BUYPASS confirmation (§4).
- **NEG** – the intended mutation of a negative test. It is not a defect and is shown in the CSV only.
- A violation on a negative test that is *not* the claimed one is a DEFECT, because the decline could no longer be attributed.

**Severity:**
- **High** – the request is invalid, or the TC cannot prove what it claims.
- **Medium** – the TC is wrong but its intent survives, or the issue is limited.
- **Low** – traceability or naming only.

---

## 2. Defect summary

| ID | Defect | ATL105 rule | TCs affected (ready) | Severity | Earlier ID |
|---|---|---|---|---|---|
| **A. Test data – message and element defects** ||||||
| [D-01](#d-01) | Fuel-machine requests send Nonfuel Amount `0` | E58 p.393 | 208 (0) | High | new |
| [D-02](#d-02) | Encrypted PIN Block Data is the literal `PIN` or `16` | E33 p.382, 12.1 p.224 | 28 + 20 negatives (14) | High | R4-03 |
| [D-03](#d-03) | Online-PIN tests send no PIN | 10.14.3 p.162, E33 | 16 (0) | High | CD-13 |
| [D-04](#d-04) | Original Authorization Only carries Approval Number, Local Date/Time, fuel, tax and cash fields | E5 p.368, E49 p.388, p.160 | 8 (7) | High | R4-04 |
| [D-05](#d-05) | Chip/track reads send only an expiry date in E12 | E2/E12 p.364–372, App. J | 32 network (29) + 435 DE (218) | High | R4-07 |
| [D-06](#d-06) | Split tender, card 2: EMV amount 9F02 = 0 | E190 p.478 | 4 (0) | High | CD-08 |
| [D-07](#d-07) | Split tender, card 2: Segment Length fixed before the amount is known | E84 p.406, 12.1 | 11 (0) | Medium | CD-07 |
| [D-08](#d-08) | Segments not allowed in a MasterCard credit financial request (108, 113, 101, 145, 114) | 11.1.1, 11.8.1, 11.2.1, 12.2 p.226, 12.12 p.249 | 51 (35) + 4 DE legs | High | R4-02 |
| [D-09](#d-09) | Optional segments filled with random values that contradict the sale | 12.5, 12.7, 12.19 p.267, 12.31 | 52 (47) + 2 DE | High | R4-01 |
| [D-10](#d-10) | "AVS information returned" tests send no AVS data | 10.1.3 p.86 | 12 (9) | High | CD-18 |
| **B. Test case ↔ test data** ||||||
| [D-11](#d-11) | "Authorization Only sending Table NNN" tests do not send the table | App. I | 421 (17) | High | new |
| [D-12](#d-12) | Decision-table parameter not in the TD (Tax Type, Track 1) | E2 p.365, §10.1.1.2 p.81 | 14 (12) | High | new |
| [D-13](#d-13) | Offline PIN on entry modes that have no chip | §10.14.3 p.162 | 47 (20) | High | R4-11 |
| [D-14](#d-14) | Negative test sends exactly the approved request | – | 113 (52) | High | new / R4-12 |
| [D-15](#d-15) | Unattended CAT tests filed under the attended General Retail scenario | App. Q p.693, App. G p.545 | 16 (0) | Medium | new |
| **C. Expected result (oracle)** ||||||
| [D-16](#d-16) | AVS approvals expect `2`/`0` instead of `3`/`4` | E83 p.404 | 20 (9) | High | new |
| [D-17](#d-17) | Partial approvals expect `2` instead of `F` | E83 p.404 | 11 (0) | High | new |
| [D-18](#d-18) | Response checks expect download-only segments in a financial response | 11.7.1.2 p.191–192, 11.7.2.2 p.194, 12.47 p.335 | 70 (39) | High | new |
| [D-19](#d-19) | Boundary tests named "→ declined" but expecting approval | E83 | 3 (3) | Medium | R4-14 |
| [D-20](#d-20) | Expected behaviour based on rules that are not ATL105 rules | §2.3.4 p.47, p.85 | 8 + 6 + 21 + 287 citing | Medium | new |
| [D-21](#d-21) | `ready` test cases with no asserted Response Code | E83 p.404 | 115 (115) | Medium | new |
| **D. Negative and boundary test design** ||||||
| [D-22](#d-22) | Negative tests that break more than the claimed rule, or break it incorrectly | – | 36 network, ≥ 47 DE (plus D-05) | Medium | R4-06 |
| [D-23](#d-23) | Negative tests on elements of segments that do not apply to MasterCard credit | 11.1.1, 11.2.1 | 10 (10) | Medium | R4-06 / R4-12 |
| [D-24](#d-24) | Boundary/profile tests that expect acceptance but send invalid values | E44, E41/E58, E86, E13, E191, E87, E5, E49, Seg 132 | 1 network + 137 DE | High | R4-05 |
| **E. Host and terminal messages (Data Element + Operations)** ||||||
| [D-25](#d-25) | CA Public Key File Load: TID length and Device Type, Hardware Version length, segment length | Seg 132 p.274–275, E48 p.388 | DE 53 legs, Ops 2 | High | R4-08 |
| [D-26](#d-26) | TransArmor Key Load: Device Type is not `++` | E102 p.417 | DE 44 legs, Ops 2 | High | R4-08 |
| [D-27](#d-27) | Table / Phone / Date-Time / Software Load: TID not 13 bytes | E102 p.417 | DE 70 legs, Ops 6 | High | R4-08 |
| [D-28](#d-28) | Device versions, table versions and keys filled with random strings | E39, E43, E96, 12.16, App. V | DE 344, Ops 19, network 67 legs | Medium | R4-09 |
| **F. Test case status** ||||||
| [D-29](#d-29) | Status does not match the test data | – | 509 + 310 + 9 + 21 DE | Medium | R4-10 |
| **G. Coverage – ATL105 behaviour without a valid test** ||||||
| [D-30](#d-30) | Authorization Only Reversal not tested | p.83, §10.1.2.2 p.85, E5 p.368 | 0 TCs exist | Medium | new |
| [D-31](#d-31) | Chip fallback to swipe (entry mode `80`) not tested | §10.14.4 p.162, App. J p.638 | 0 legs | Medium | Round 4 rec. |
| [D-32](#d-32) | Expired card not tested | E12 p.372, §10.1.1.1 p.81 | 0 | Low | CD-12 |
| [D-33](#d-33) | Contactless-magstripe-only terminal not tested | App. J p.639, App. I p.569 | 0 | Low | CD-17 |
| [D-34](#d-34) | Entry modes `95` and `86` not tested | App. J p.638 | 0 | Low | new |
| [D-35](#d-35) | ATL105 behaviour with no defect-free executable TC | – | see table | Medium | new |
| **H. Traceability to the spec** ||||||
| [D-36](#d-36) | TC spec references that are not ATL105 text | – | 705 quotes + 26 wrong page | Low | new |
| [D-37](#d-37) | Follow-on legs not traced to their test case | – | 432 legs | Low | CD-19 (residual) |

---

## 3. Defect details

### A. Test data – message and element defects

<a id="d-01"></a>
#### D-01 – Fuel-machine requests send Nonfuel Amount `0`

| | |
|---|---|
| Severity | **High** – the request is invalid · new |
| ATL105 rule | E58 Nonfuel Amount (p.393): *"Valid Codes/Values: 1–99999999"*. 12.1 (p.223): *"When a field is not populated, still send the Field Separator."* |
| Test data | `TD-TC-041569.json`, "SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Purchase/Capture → approved": <ul><li>Prompt Code `4020`, Pump/Lane `01`, Fuel Purchase Amount `1500`, **Nonfuel Amount `0`**</li><li>Seg 102 product `001` / `G` / `1500`; 9F02 `000000001500`</li></ul> |
| Expected | Nonfuel Amount empty (Field Separator only), and Seg 100 Segment Length `091` instead of `092`. |
| Scope | 231 legs in **208 TCs** – every fuel-at-pump TC in the Petroleum unattended and MCC 5542 contexts. This includes the CAT pre-authorization → completion flow TC-041634 (both legs). |
| Detected by | `S100-AMT-ZERO`; Java runner `SEG100-E58-FORMAT` (TC-041569, TC-041634) |
| Impact | The host rejects Element 58 before authorization, so no fuel-machine test can return its expected approval. |

<a id="d-02"></a>
#### D-02 – Encrypted PIN Block Data is the literal `PIN` or `16`

| | |
|---|---|
| Severity | **High** · Earlier ID R4-03 (not fixed) |
| ATL105 rule | E33 (p.382, max 36) and 12.1 field 8 (p.224): *"For DUKPT PIN encryption, this is a 16- to 20-byte KSN plus a 16-byte PIN block."* When a PIN block is sent, the EMV CVM result (9F34) must show online PIN. |
| Test data | <ul><li>`TD-TC-040111.json`, "EMV Contact Chip Authorization Only with Encrypted PIN Block Data = PIN → approved" (`ready`): **E33 `PIN`**, 9F34 `5E0302` (signature).</li><li>`TD-TC-040126.json`: **E33 `16`**.</li></ul> |
| Expected | KSN + PIN block (32–36 hex characters) with an online-PIN CVM (9F34 `42…`). If the PIN or key must come from the client, use `<AWAITING:EncryptedPINBlockData>` and status `needs_client_input`. |
| Scope | 28 positive TCs (14 `ready`), e.g. TC-040111, TC-040126, TC-040225, TC-040240, TC-040341, TC-040356. A further 20 "Encrypted PIN Block Data sent where it is not allowed" negatives use the same literal, so their "violation" is an invalid value, not a PIN block. |
| Detected by | `S100-PIN`, `S130-CVM`; Java `SEG100-E33-FORMAT` |

<a id="d-03"></a>
#### D-03 – Online-PIN tests send no PIN

| | |
|---|---|
| Severity | **High** · Earlier ID CD-13 (not fixed) |
| ATL105 rule | §10.14.3 (p.162): *"Online PIN: … the PIN is electronically sent to and validated by the card issuer."* The PIN travels in E33 (p.382). |
| Test data | <ul><li>`TD-TC-040121.json`, "EMV Contact Chip Authorization Only with cvm = online_pin → approved": **E33 empty**, while 9F34 = `420300` (online PIN) and TVR = `8000048000`.</li><li>`TD-TC-039831.json`, "… Purchase/Capture on a CAT terminal with online pin → approved": E33 empty.</li></ul> |
| Expected | An encrypted PIN block in E33, or `<AWAITING:EncryptedPINBlockData>` until the client supplies the test PIN/key. |
| Scope | **16 TCs** (8 decision-table, 8 pairwise; 14 `needs_client_input`, 2 `pending_sme`), e.g. TC-040121, TC-040235, TC-039831, TC-039855. 4 EMV legs carry an online-PIN CVM with no PIN. |
| Detected by | `TC-CLAIM-PARAM`, `TC-CLAIM-CVM`, `S130-CVM` |

<a id="d-04"></a>
#### D-04 – Original Authorization Only carries Approval Number, Local Date/Time, fuel, tax and cash fields

| | |
|---|---|
| Severity | **High** · Earlier ID R4-04 (not fixed) |
| ATL105 rule | <ul><li>E5 (p.368): *"If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."*</li><li>E49 (p.388): *"The local date and local time is only sent in a POS and CAT Capture transaction."*</li><li>E79 Pump/Lane applies to fuel transactions.</li><li>p.160: *"First Data does not support cashback for Visa or MasterCard credit."*</li></ul> |
| Test data | `TD-TC-040126.json`, "EMV Contact Chip Authorization Only with optional Standard Message Data Segment → approved" (`ready`, General Retail): <ul><li>Approval Number `J38FQA`, Local Date/Time `0615251430`, E33 `16`</li><li>Pump/Lane `1`, Fuel `1`, Tax `1`, **Cash `1`**, Nonfuel `1500`</li><li>As a result, 9F02 = `000000001503` and **9C = `09` (cash back)** on TT 3</li></ul> |
| Expected | None of these fields on an original authorization at a non-fuel merchant; 9F02 = `000000001500`, 9C = `00`. |
| Scope | **8 TCs (7 `ready`)**: TC-040126, TC-040240, TC-040356, TC-040557, TC-040668, TC-040786 and 2 more. |
| Detected by | `S100-APPR`, `S100-LDT`, `S100-PIN`, `S130-9C`; Review `S100-CASH` |
| Impact | Per E5, the host treats the request as already authorized, so the test does not exercise authorization at all. |

<a id="d-05"></a>
#### D-05 – Chip and track reads send only an expiry date in Card Discretionary Block Data (E12)

| | |
|---|---|
| Severity | **High** · Earlier ID R4-07 (not fixed) |
| ATL105 rule | <ul><li>E2 (p.364–365) and E12 (p.371): when the card is read, Account Number + E12 *"should consist of all data read from the card between … the Start Sentinel and End Sentinel"*.</li><li>E12 (p.372): MMYY is the format *"for a card whose number has been manually entered"*.</li><li>App. J (p.638): `05` = ICC read, `90`/`91` = track read.</li></ul> |
| Test data | <ul><li>`TD-TC-038593.json` (EMV contact Authorization Only, Table 005 `051`): **E12 `0529`** (MMYY only).</li><li>`TD-TC-038639.json` (magnetic stripe, Table 005 `901`): **`0427`**.</li><li>The positive EMV TDs do this correctly, e.g. `TD-TC-040121.json`: `290520101440570`.</li></ul> |
| Expected | Track-2-equivalent data: YYMM + service code (`2xx` for a chip card) + discretionary data. |
| Scope | <ul><li>**Network: 32 TCs (29 `ready`).** All are negative or boundary tests, so each carries a second, unintended violation; examples TC-038593, TC-038594, TC-038603, TC-038622.</li><li>**Data Element: 435 TCs (218 `ready`)**, including 77 boundary and 15 positive tests.</li></ul> |
| Detected by | `S100-CDBD`; Java `APPJ-READ-HAS-TRACK`, `TRACK2-EXPIRY-YYMM` |

<a id="d-06"></a>
#### D-06 – Split tender, second card: EMV amount (9F02) is zero

| | |
|---|---|
| Severity | **High** · Earlier ID CD-08 (not fixed) |
| ATL105 rule | E190 EMV Chip Data (p.478) carries the chip's transaction data. Amount Authorized (9F02) must equal the requested amount (App. R; framework rule `SEG130-R-009`). |
| Test data | `TD-TC-040134.step2.split-tender.json`: Nonfuel Amount `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`, **9F02 `000000000000`**. |
| Expected | 9F02 built from the same remaining balance at run time. |
| Scope | **4 TCs**: TC-040134, TC-040248, TC-041234, TC-041341 |
| Detected by | `S130-9F02`; Java `SPLIT-TENDER-9F02` / `SEG130-R-009` |

<a id="d-07"></a>
#### D-07 – Split tender, second card: Segment Length fixed before the amount is known

| | |
|---|---|
| Severity | **Medium** · Earlier ID CD-07 (not fixed) |
| ATL105 rule | E84 (p.406) and 12.1 (p.223): Segment Length includes every field and Field Separator. |
| Test data | Same leg as D-06: Seg 100 Segment Length **`086`**. The fixed fields add up to 85 bytes, so `086` is correct only if the remaining balance is a single digit ($0.01–$0.09). A $15.00 sale with a partial approval normally leaves 3–4 digits (e.g. `500` → length `088`). |
| Expected | The length is computed after the remaining balance is substituted, or is itself a run-time value. |
| Scope | **11 TCs**, e.g. TC-040134, TC-040248, TC-040369, TC-040565, TC-040677, TC-040794 |
| Detected by | `S100-LEN-RT` |

<a id="d-08"></a>
#### D-08 – Segments not allowed in a MasterCard credit financial request

| | |
|---|---|
| Severity | **High** · Earlier ID R4-02 (not fixed, more legs) |
| ATL105 rule | <ul><li>11.1.1 (p.166–169) and 11.8.1 (p.206–207) list the segments a (EMV) Financial Transaction Request may carry: 101, 102, 103, 104, 111, 123, (130), 135, 143, 145, 146, 151, 152, 153.</li><li>**Seg 108 Loyalty and Seg 114 SKU belong to the Loyalty Card Transaction Request** (11.2.1, p.174–175).</li><li>**Seg 113 ECA/TeleCheck** is the check-service segment (12.12, p.249; 11.3).</li><li>12.2 (p.226): *"The Fleet Data Segment is included in all fleet card transaction requests"*. Seg 145 is the enhanced-fleet segment (12.31). MasterCard credit (card type 020) is not a fleet card.</li></ul> |
| Test data | <ul><li>`TD-TC-040114.json` ("EMV Contact Chip Authorization Only with Payment Tender Type = AX", `ready`): Seg 108 inside an EMV Financial Transaction Request.</li><li>`TD-TC-041338.json` ("… with Fleet Data Segment sent"): Seg 101 with Odometer `0128345` and Vehicle Number `0042` on a MasterCard credit card.</li><li>`TD-TC-040129.json`: Seg 145.</li><li>`TD-TC-038601.json` (Balance Inquiry, `ready`): Seg 113 with Merchant Trace ID = 25 × `A`.</li></ul> |
| Expected | Only the segments ATL105 allows for a MasterCard credit financial request. |
| Scope | **51 TCs (35 `ready`)**: Seg 108 in 27, Seg 101 in 14, Seg 145 in 9, Seg 113 in 1. Data Element: Seg 114 in 4 financial legs. |
| Detected by | `SEG-OPT-APPL`; Java `11.8.1-SECTION3` |

<a id="d-09"></a>
#### D-09 – Optional segments filled with random values that contradict the sale

| | |
|---|---|
| Severity | **High** · Earlier ID R4-01 (not fixed) |
| ATL105 rule | <ul><li>12.5 Purchase Card (p.233–234): tax, freight and duty amounts of the purchase, ship-to country code (E89), postal codes.</li><li>12.7 Loyalty (p.238–239): coupon amount, points, Payment Tender Type (E148: `MC` = MasterCard).</li><li>12.19 (p.267): Seg 123 is *"required on all initial and recurring transactions involving tokenized data"*.</li><li>12.31: Seg 145 carries TLV prompt data.</li></ul> |
| Test data | <ul><li>**Seg 104** (`TD-TC-040127.json`, `ready`): Tax `4653199` ($46,531.99), Freight `0662246`, Duty `4555977` on a $15.00 sale; Ship-to Country `407` (not an ISO 3166 code); Ship-to Postal `6LNQYAPB8T`; Purchase Code `7UDLSFXCXQ2C5OU1`.</li><li>**Seg 108** (`TD-TC-040114.json`): Coupon Amount `91577796` ($915,777.96), Points `269486`, Payment Tender Type **`AX`** (American Express) on a MasterCard.</li><li>**Seg 145** (`TD-TC-040129.json`): Enhanced Fleet Data = 1,008 random characters.</li><li>**Seg 123** (`TD-TC-040358.json`, magnetic-stripe read `901`): random CAVV and Token Requestor ID, Token PAN Suffix `XSXX`, and American Express SafeKey data.</li></ul> |
| Expected | Values consistent with a $15.00 MasterCard sale. Otherwise the optional segment is left out, or marked as client input. |
| Scope | **52 TCs (47 `ready`)**, e.g. TC-040127, TC-040129, TC-040358, TC-038611, TC-039967. Data Element: 2 "Purchase Card segment present" profile tests (TC-039572, TC-039628). |
| Detected by | `SEG-OPT-RAND` |

<a id="d-10"></a>
#### D-10 – "AVS information returned" tests send no AVS data

| | |
|---|---|
| Severity | **High** · Earlier ID CD-18 (not fixed) |
| ATL105 rule | §10.1.3 (p.86): *"The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment … The authorizer returns a response containing both the transaction authorization Response Code and the AVS information."* |
| Test data | `TD-TC-040140.json`, "EMV Contact Chip Authorization Only (AVS or NVS or Both Information returned) → approved" (`ready`): only Tables 005, 031 and 030 – **no Table 003**. |
| Expected | Table 003 with the AVS data, or `<AWAITING:…>` for the client's AVS test address. |
| Scope | **12 TCs (9 `ready`)**: TC-040030, TC-040140, TC-040254, TC-040396, TC-040571, TC-040683, TC-040800, TC-040919, TC-041083 and 3 `pending_sme`. |
| Detected by | Scripted name/TD comparison; see also D-16 for the expected code. |

### B. Test case ↔ test data

<a id="d-11"></a>
#### D-11 – "Authorization Only sending Table NNN" tests do not send the table

| | |
|---|---|
| Severity | **High** · new |
| ATL105 rule | App. I (p.552–630) defines each Variable Information table. A test that sends Table NNN must carry it in Seg 111. |
| Test data | **TC-040066**, "EMV Contact Chip Authorization Only with Currency Code Information → approved" (objective *"Authorization Only sending Table 006 Currency Code Information"*). `TD-TC-040066.json` has **only Tables 005, 031 and 030** – the same request as the plain approved authorization. `awaiting_inputs` is empty; the table appears only as a free-text open item. The same applies to TC-040067 (Table 008), TC-040068 (019), TC-040070 (027), TC-040071 (029) and the rest. |
| Expected | Table NNN present with a valid App. I value, or `<AWAITING:…>` with status `needs_client_input`. Tables of other programs should not be MasterCard credit tests at all: <ul><li>001 UPC (Blackhawk/InComm)</li><li>002 Program Identifier (stored value / ECA / WIC)</li><li>019 Recharge Type (stored value)</li><li>029 UUID (Express Code)</li><li>048 MasterCard 3-DS (e-commerce only, App. I p.586–587)</li></ul> |
| Scope | **421 TCs**, all Authorization Only, covering 47 tables (1–20 TCs per table): 17 `ready`, 361 `needs_client_input`, 43 `pending_sme`. |
| Detected by | `TC-CLAIM-TABLE` |
| Impact | These TCs run as plain authorizations, so the coverage they report for 47 tables does not exist. |

<a id="d-12"></a>
#### D-12 – Decision-table parameter is not in the test data

| Parameter | TCs (`ready`) | Example | What the TD contains | ATL105 |
|---|---:|---|---|---|
| "with Tax Type 1 = GST → approved" | 8 (7) | TC-040116 | No tax segment or field at all (Seg 100/111/130 only) | The tested value is absent. GST is also not a US tax type. |
| "with track = 1 → approved" on EMV, keyed, COF, fallback, mobile | 6 (5) | TC-040132 (EMV `05`, E12 `290520101440570` = Track 2), TC-040675 (keyed, `0932`) | No Track-1 data (no `^` separator) | E2 (p.365): Track 1 comes from a *"Track 1 card reader (credit cards only)"*; §10.1.1.2 (p.81). Only magnetic-stripe reads can test it. |

Scope: **14 TCs (12 `ready`)**. Detected by `TC-CLAIM-PARAM`.

<a id="d-13"></a>
#### D-13 – Offline PIN on entry modes that have no chip

| | |
|---|---|
| Severity | **High** · Earlier ID R4-11 (not fixed, more TCs) |
| ATL105 rule | §10.14.3 (p.162): *"Offline PIN: Instead of sending the PIN to the issuer, the PIN entered by the consumer is matched to that on an application housed on the EMV chip card. This functionality is exclusive to EMV card transactions."* |
| Test data | <ul><li>TC-039881, "Magnetic Stripe Purchase/Capture on a POS terminal with offline pin → approved" (Table 005 `901`).</li><li>TC-040029, "Manual / Keyed Mail/Phone Purchase … with offline pin".</li><li>TC-040350, "Magnetic Stripe Authorization Only with cvm = offline_pin".</li><li>TC-041013 (contactless mobile `82`).</li></ul> |
| Expected | Offline-PIN tests only on EMV contact and contactless chip reads (`05`, `07`). |
| Scope | **47 TCs (20 `ready`)**: 41 pairwise and 6 decision-table, on entry modes `90`, `91`, `01`, `79`, `10` and `82`, including Mail/Phone. |
| Detected by | `TC-CLAIM-CVM`, `TC-CLAIM-PARAM` |

<a id="d-14"></a>
#### D-14 – Negative test sends exactly the approved request

Method: each negative's request was compared with the approved positive in the same scenario and context, after normalizing PAN, expiry, sequence number and chip cryptogram data. **113 negative TCs (52 `ready`) are identical to an approved request, yet expect a decline.** The decline therefore cannot happen deterministically, and cannot be attributed to the claimed cause.

| Family | TCs | Example | Why it cannot work |
|---|---:|---|---|
| "(card issuer declines) → declined" | 58 | TC-039837 = TC-039814 "EMV Contact Chip Purchase/Capture → approved" | The decline needs a decline test card or simulator trigger, and the TC does not provide one (it should be a client input). |
| "… sent where it is not allowed → declined (to confirm)" and other single-rule tests | 44 | TC-040338 "Magnetic Stripe Authorization Only with Information Byte sent where it is not allowed" sends Information Byte `0`, exactly like the approved TC. | Information Byte is **required** in every request (12.1 field 3 "R"; E44 p.386). No ATL105 rule forbids it, and the cited pages (p.157–158, TVR bit tables) are unrelated. |
| "… Data Segment omitted where requires it → declined" | 11 | TC-038605 = TC-040837 "Credential on File Authorization Only → approved" | The "required" segments (104, 108, 101, 145, 153, 123) are conditional, e.g. 11.1.1 "Sent only on transactions requiring …". Nothing is omitted compared with the approved request (R4-12). |

Detected by `TC-DUP-CONFLICT`. A further 24 time-based and resend negatives also share the approved request, but they rely on a harness action (clock offset or resend count), so they are listed in §4.

<a id="d-15"></a>
#### D-15 – Unattended CAT tests filed under the attended General Retail scenario

| | |
|---|---|
| Severity | **Medium** · new |
| ATL105 rule | App. Q (p.693): Table 030 terminal class `010` = attended card-acceptor ECR, `100` = unattended customer-operated. App. G (p.545): TT 4/5 = customer-activated. |
| Test data | TC-039831, "MERCHANT 07 (CAT PLACEHOLDER) EMV Contact Chip Purchase/Capture on a CAT terminal with online pin → approved": Table 030 `1000000001` (unattended) and TT `4`. It is filed under scenario BR1_TS1 "Purchase/Capture – EMV Contact Chip **@ General Retail · attended POS**". |
| Expected | Filed under an unattended (CAT) scenario, or rebuilt as an attended POS test. |
| Scope | **16 pairwise TCs** (14 `needs_client_input`, 2 `pending_sme`), e.g. TC-039831, TC-039855, TC-039880, TC-039905 |
| Detected by | `TC-CTX-MISMATCH` |

### C. Expected result (oracle)

<a id="d-16"></a>
#### D-16 – AVS approvals expect the wrong Response Code

| | |
|---|---|
| Severity | **High** · new |
| ATL105 rule | E83 Response Code (p.404): `2` = Approved—Authorization only; **`3` = Approved—Authorization only with AVS; `4` = Approved—Purchase/Capture with AVS**. |
| Test cases | <ul><li>TC-040140, "Authorization Only (AVS or NVS or Both Information returned) → approved", expects **`2`**.</li><li>TC-040118, "Authorization Only with avs = true → approved", expects **`2`**.</li><li>TC-040030, "Manual / Keyed Mail/Phone Purchase (AVS or NVS or Both Information returned) → approved", expects **`0`**.</li></ul> |
| Expected | `3` for Authorization Only and `4` for Purchase / Mail-Phone Purchase when AVS data is sent. |
| Scope | **20 TCs (9 `ready`)**. No TC in the catalog expects `3` or `4`. |
| Detected by | `TC-EXP-AVS` |

<a id="d-17"></a>
#### D-17 – Partial approvals expect the wrong Response Code

| | |
|---|---|
| Severity | **High** · new |
| ATL105 rule | E83 (p.404): **`F` = Approved—Partial approval** |
| Test cases | TC-040134 step 1, "first card partly approved" (split tender), expects **`2`**, with the note "partially approved: the card covers part of the amount". |
| Expected | `F` on the partially approved leg. |
| Scope | **11 TCs** (all split-tender flows). No TC in the catalog expects `F`, so the partial-approval response itself is never verified. |
| Detected by | `TC-EXP-PARTIAL` |

<a id="d-18"></a>
#### D-18 – Response checks expect download segments in a financial response

| | |
|---|---|
| Severity | **High** · new |
| ATL105 rule | Merchant Data (DL1), Dial String (DL2), Date and Time (DL3) and Store and Forward (DL6) are segments of the **Download responses**: Table Load Response 11.7.1.2 (p.191–192) and Phone Load Response 11.7.2.2 (p.194). 12.47 (p.335): *"The Store and Forward Data Segment is only sent in a Table Load Response when Segment No. DL1 … includes a Card Type value of 173."* They are not part of the (EMV) Financial Transaction Response. |
| Test cases | <ul><li>TC-039833, "EMV Contact Chip Purchase/Capture (Merchant Data Segment returned) → approved"</li><li>TC-040158, "Authorization Only (Store and Forward Data Segment returned) → approved"</li><li>TC-040148 (Dial String)</li><li>TC-040147 (Date and Time)</li></ul> |
| Expected | These expectations belong in Download (Table Load / Phone Load) tests, not in financial tests. |
| Scope | **70 TCs (39 `ready`)**: Merchant Data 34, Store and Forward 20, Dial String 8, Date and Time 8. |
| Detected by | `TC-EXP-DLSEG` |

<a id="d-19"></a>
#### D-19 – Boundary tests named "→ declined" but expecting approval

| | |
|---|---|
| Severity | **Medium** · Earlier ID R4-14 (partly fixed) |
| Test cases | <ul><li>TC-038618, "Authorization Only Purchase Code exactly at its documented maximum length (16 bytes) → declined": expects `2`.</li><li>TC-038622, "Purchase/Capture Additional Information exactly at its documented maximum length → declined": expects `0`.</li><li>TC-038632, "Purchase/Capture CA Public Key File Checksum exactly at its documented maximum length → declined": expects `0`.</li></ul> |
| Expected | The name and the expected code must agree. At the documented maximum, the value is accepted (E83 `0`/`2`). |
| Detected by | `TC-NAME-EXP` |

<a id="d-20"></a>
#### D-20 – Expected behaviour based on rules that are not ATL105 rules

| Test cases | Claimed rule | ATL105 |
|---|---|---|
| 8 × "Authorization Only (resent) sent 4 times (beyond the limit of 3) → declined (to confirm)", e.g. TC-041346 | The limit of 3 comes from the **Petroleum Industry spec (PIPS p.17)** | ATL105 §2.3.4 (p.47) limits only TOR attempts: *"A TOR should never be attempted more than 3 times."* No authorization resend limit exists. |
| 6 × "Authorization Only, then Cancellation after 25 hours (beyond the 24 hour limit) → declined (to confirm)", e.g. TC-040136 | p.85 | p.85 is an obligation on merchants and acquirers (*"Acquirers must ensure that their merchants process authorization reversals … within 24 hours"*), not a host decline rule. |
| 21 × "… sent where it is not allowed", e.g. TC-040338 | p.157–158 (TVR bit tables), p.13, p.70, p.75 | None of the cited pages forbids the element. See D-14. |
| 287 non-petroleum TCs | Petroleum spec (PIPS) rules in `spec_basis`, e.g. TC-039814 (General Retail EMV purchase) cites PIPS p.127 *"When pumping is complete …"* | Not applicable outside fuel contexts. |

Severity **Medium** · new.

<a id="d-21"></a>
#### D-21 – `ready` test cases with no asserted Response Code

| | |
|---|---|
| Severity | **Medium** · new |
| ATL105 rule | E83 (p.404) lists the Response Code to assert for every outcome. |
| Finding | **115 of the 619 `ready` network TCs have no expected code.** They are FLAG tier and their names end "→ declined (to confirm)": 90 single-rule-broken, 15 invalid-transition, 8 segment-omitted and 2 boundary tests. Examples: TC-038597, TC-038598, TC-038601. A tester cannot pass or fail these TCs. |
| Expected | Each executable TC states the E83 code (or the set of codes) that passes. Otherwise it should not be `ready`. |

### D. Negative and boundary test design

<a id="d-22"></a>
#### D-22 – Negative tests that break more than the claimed rule, or break it incorrectly

Rule: a negative test must break **exactly one** rule so the decline can be attributed. Network examples:

| TC | Claimed violation | What the TD actually does |
|---|---|---|
| TC-038635 | Variable Information one byte over its maximum | Replaces Table 005 with 983 × `A`, which destroys the POS entry mode. It also blanks E112 and makes the Seg 111 length 4 digits (`1019`). |
| TC-038636, TC-038637 | Variable Information Indicator invalid / over-length | The mutated table is Table 005, so the POS entry mode is lost as well. |
| TC-038603, TC-038629 | E189 sent empty / invalid character | Seg 130 Segment Length declared `0054` / `0057`, while the content is 201 / 204 bytes. |
| TC-038616 | Fuel Purchase Amount one byte over (8) | `000000000` – the value is also zero (E41 range 1–99999999). |
| TC-038633 | Balance Inquiry Tax Type 1 not a valid value | No Tax Type is sent at all. |
| TC-038592 | Partial Approval Indicator **omitted** (N-C1) | Its Field Separator is still sent (Segment Length `077`), so the request is identical to the "sent empty" (N-C4) test. |
| TC-038626, TC-038627 | "EMV Chip Data Component Name: Value (ANSB …255) The data of each Tag/Length/Value…" | The "element" is a paragraph of spec text, so the TC cannot be attributed to an element. |
| 32 TCs | various | Also carry the expiry-only E12 of D-05. |

Data Element examples:
- 19 TCs have a wrong Seg 130 length on E189/chip-data negatives (e.g. TC-039393 declared `0058` vs 205; TC-039396 `0054` vs 201).
- 15 TCs mutate Table 005 (e.g. TC-039125, TC-039130, TC-039137).
- 10 N-C1 "omitted" tests are identical to their "sent empty" twin (e.g. TC-039568, TC-039609 – TC-039613).
- 3 TCs replace the PAN with `TOTALS REQUEST` (e.g. TC-038754; E2 p.366, the loyalty totals literal).
- TC-038810 ("Segment Length one more") also sends E33 `16`, Local Date/Time and an Approval Number on a purchase.

Severity **Medium** · Earlier ID R4-06. Detected by `S111-TLEN`, `S130-LEN`, `MSG-03`, `TC-NC1-NOT-OMITTED`, `S100-PAN`, `S100-CDBD`.

<a id="d-23"></a>
#### D-23 – Negative tests on elements of segments that do not apply to MasterCard credit

The broken element sits in a segment that a MasterCard credit financial request cannot carry (D-08). The host may reject the segment itself, so the decline cannot be attributed to the element.

| TC | Element | Segment |
|---|---|---|
| TC-038601 | Merchant Trace ID | 113 ECA/TeleCheck |
| TC-038611, TC-038625 | Loyalty Track 2 Data, Update Code | 108 Loyalty |
| TC-038623, TC-038624 | Customer Data | 101 Fleet |
| TC-038600, TC-038606, TC-038628 | CAVV Revised Format, Token PAN Suffix, Cryptogram Token Data | 123 NFC tokenization (on non-tokenized reads) |
| TC-038615, TC-038618 | Purchase Code | 104 Purchase Card |

Scope **10 TCs (all `ready`)** · Severity **Medium** · Earlier ID R4-06 / R4-12.

<a id="d-24"></a>
#### D-24 – Boundary and profile tests that expect acceptance but send invalid values

Rule: a boundary test that expects acceptance must use a **valid** value at the limit.

| TC | Claim | Value sent | ATL105 |
|---|---|---|---|
| TC-038634 (network) | Information Byte at max length (1) | `A` | E44 p.386: `?`, `0`, `1` |
| TC-038740 | Information Byte at max length (1) – accepted | `A` – **identical to the negative TC-038743** | E44 |
| TC-038928 | Totals Information Byte at max | `A` | E44; Totals `0`/`1` |
| TC-038767 | Fuel Purchase Amount at max length (8) | `00000000` | E41: 1–99999999 |
| TC-038770, TC-038771 | Fuel Purchase Amount at range min / max | `00000001` / `99999999` (8 digits) | E58 p.393: *"A maximum length of 7 bytes is allowed for all other card types"* (8 is Amex only) |
| TC-039298 | Sequence Number at max length | `000000` | E86 p.409 (000001–999999) |
| TC-039327 | Card Label at max length (4) | `AAAA` | E13 p.372 (`CC` = MasterCard/Visa) |
| TC-039403 | EMV Additional Information Indicator at max (3) | `000`, length `984`, 984 random characters | E191 p.479 |
| TC-038833 | Service Level at max (1) | `A` | E87 |
| TC-038799 | Approval Number at max (6) | `AAAAAA` on an **original** purchase | E5 p.368 (makes the purchase preauthorized) |
| TC-038803 | Local Date and Time at max (10) | `0000000000` on a purchase | E49 p.388 |
| TC-039452 | CA Public Key Hardware Version at max (8) | `AAAAAAAA` | Seg 132 field 6 max length **4** (p.275) |

Scope: network 1 TC; **Data Element 137 boundary/profile TCs** with at least one defect (65 excluding the D-05 expiry issue). Severity **High** · Earlier ID R4-05.

### E. Host and terminal messages (Data Element and Operations packages)

<a id="d-25"></a>
#### D-25 – CA Public Key File Load Request (Seg 132)

| | |
|---|---|
| Severity | **High** · Earlier ID R4-08 (not fixed), plus Hardware Version (new) |
| ATL105 rule | <ul><li>Seg 132 (p.274–275), field 4 Terminal Identifier: **max 13**, *"the first two characters (Device Type) … must be '+*'"*.</li><li>Field 6 Hardware Version: **max 4**.</li><li>Segment *"maximum length of 77"*.</li><li>E48 (p.388): *"BUYPASS Device Type must be '+*' for the CA Public Key File Load Request."*</li></ul> |
| Test data | `TD-TC-039804.json` (Operations): Terminal Identifier `JX72948216050257001` (19 bytes, Device Type `JX`), Hardware Version `LJD9APK9` (8 bytes), Segment Length `094`. |
| Scope | Data Element: TID wrong on 53 of 54 CAPK legs, Hardware Version on 51, segment length > 77 on 49. Operations: TC-039804, TC-039805. |
| Detected by | `H-TID-CAPK`, `H-CAPK-HW`, `H-SEGMAX` |

<a id="d-26"></a>
#### D-26 – TransArmor Key Load Request: Device Type is not `++`

| | |
|---|---|
| Severity | **High** · Earlier ID R4-08 (not fixed) |
| ATL105 rule | E102 (p.417): *"For the TransArmor PKI Encryption and Tokenization Load Request, the first two characters (Device Type) of this element's value must be '++' regardless of the actual device type."* |
| Test data | `TD-TC-039802.json`: Terminal Identifier `JX72948216050257001`. |
| Scope | Data Element 44 legs; Operations TC-039802, TC-039803. |
| Detected by | `H-TID-TA` |

<a id="d-27"></a>
#### D-27 – Table, Phone, Date and Time, and Software Load Requests: TID is not 13 bytes

| | |
|---|---|
| Severity | **High** · Earlier ID R4-08 (not fixed) |
| ATL105 rule | E102 (p.417): *"In the case of a Table Load Request, Phone Load Request, Date and Time Load Request, or Software Load Request, the field length is 13 bytes."* |
| Test data | `TD-TC-039807.json` (Partial Table Load, `ready`): Terminal Identifier `JX72948216050257001` (19 bytes). |
| Scope | Data Element 70 legs; Operations 6 TCs (e.g. TC-039807). |
| Detected by | `H-TID13` |

<a id="d-28"></a>
#### D-28 – Device versions, table versions and keys filled with random strings

| | |
|---|---|
| Severity | **Medium** · Earlier ID R4-09 (not fixed) |
| ATL105 rule | <ul><li>E43, E96 and E39 identify the device's actual hardware, software and firmware versions (certified device values).</li><li>Card table versions and the Load Control Key come from BUYPASS (12.16, 12.17).</li><li>Moneris terminal and merchant IDs are *"as provided by Moneris"* (App. V p.704).</li></ul> |
| Test data | <ul><li>`TD-TC-039804.json`: Hardware `LJD9APK9`, Software `RUIV4CMM`, Firmware `92CWK6N4`.</li><li>`TD-TC-039792.json`: Device Card Table Version `57491451898883081425085190866195170`, Load Control Key `NC8W2N5A2DWLVVBH360MG1D4NQEVYDAWE6DWNYRFXW7PA8I8IH…`.</li></ul> |
| Expected | Real device and BUYPASS values, or `<AWAITING:…>` client inputs. |
| Scope | Data Element 344 legs, Operations 19, network Totals 67. |
| Detected by | `H-SYNTH-VER` |

### F. Test case status

<a id="d-29"></a>
#### D-29 – Test case status does not match its test data

A tester schedules TCs by status. A TC is executable (`ready`) only when its request is complete and its expected result is asserted; `needs_client_input` only when the TD has a field waiting for the client.

| Finding | Scope | Example |
|---|---:|---|
| `needs_client_input`, but the TD is complete. The only "awaiting" items are questions about segments the TD does not send (Purchase Card 104, Loyalty 108, SKU 114, Network Token 153, NFC 123). | 509 TCs | TC-041569 (fuel-machine purchase happy path) |
| `needs_client_input` with nothing awaiting at all – no placeholder, empty `awaiting_inputs` | 284 network + 26 Operations | TC-040066 (D-11), TC-039779 |
| `pending_sme`, but the TD carries `<AWAITING:…>` client fields | 9 TCs | TC-039991, TC-040047 |
| Rejected by verification (`REJECTED_FINAL` / `blocked_verification`) but test data still shipped | 21 Data Element TCs | TC-038776, TC-039145 – TC-039153, TC-039594 – TC-039598, TC-039643 – TC-039645 |

Severity **Medium** · Earlier ID R4-10. Detected by `TC-STATUS-INFLATED`, `TC-AWAIT-EMPTY`, `TC-AWAIT-STATUS`.

### G. Coverage – ATL105 behaviour without a valid test

<a id="d-30"></a>
#### D-30 – Authorization Only Reversal is not tested

| | |
|---|---|
| Severity | **Medium** · new |
| ATL105 rule | <ul><li>p.83 lists **Authorization Only Reversal** as a supported credit POS transaction and a supported customer-activated transaction.</li><li>§10.1.2.2 (p.85) defines it for TT 3 and TT 5.</li><li>E5 (p.368): the Approval Number *"present in the original transaction—must be present and identical in the Authorization Only Reversal transaction"*.</li></ul> |
| Finding | **0 TCs** – no Authorization Only → Purchase Reversal flow (TT 3 → 8, 5 → 8). Holds are released only through Cancellation (TT S). |

<a id="d-31"></a>
#### D-31 – Chip fallback to swipe (entry mode `80`) is not tested

| | |
|---|---|
| Severity | **Medium** · raised in Round 4, not done |
| ATL105 rule | §10.14.4 (p.162): when a chip card is swiped at an EMV device, *"Always send the Fallback Entry Mode of '80'"*. App. J (p.638): `80` = chip card capable, unaltered track data read. |
| Finding | The Chip Fallback scenarios list entry modes `79` and `80`, but **all 238 fallback legs use `79`** (fallback to manual). No request sends `80`. |

<a id="d-32"></a>
#### D-32 – Expired card is not tested

Severity **Low** · Earlier ID CD-12.
- E12 (p.372): *"For all authorizations, merchants must send a value even for expired cards."*
- §10.1.1.1 (p.81): the device validates expiry for credit; §10.14.2.1 (p.160): not for EMV.
- No TC sends an expired card.

<a id="d-33"></a>
#### D-33 – Contactless-magnetic-stripe-only terminal is not tested

Severity **Low** · Earlier ID CD-17.
- App. J (p.639): capability `8` is *"reserved only for Contactless MSR"*.
- App. I Table 031 (p.569): `07` = contactless magnetic stripe only.
- 0 legs use either value; Table 031 is always `12` or `00`.

<a id="d-34"></a>
#### D-34 – Entry modes `95` and `86` are not tested

Severity **Low** · new. App. J (p.638) defines `95` (ICC read – CVV data unreliable) and `86` (dual-interface contactless to contact). The EMV scenarios declare them, but 0 legs use them.

<a id="d-35"></a>
#### D-35 – ATL105 behaviour with no defect-free executable test case

| ATL105 behaviour | TCs | `ready` | `ready` and defect-free |
|---|---:|---:|---:|
| Authorization Only → Completion (TT 3 → 0, 5 → 4) | 12 | 0 | 0 |
| Partial approval → split tender | 11 | 0 | 0 |
| Totals after transactions | 67 | 0 | 0 |
| AVS (Table 003) / CVV2 (Table 004) | 8 / 16 | 0 / 0 | 0 / 0 |
| EMV online PIN | 4 | 0 | 0 |
| Incremental / estimated authorization (Table 082) | 38 | 0 | 0 |
| Customer-activated (CAT) purchase or authorization (TT 4/5) | 215 | 0 | 0 |
| Contactless mobile (`82`) – all transactions | 236 | 0 | 0 |
| Petroleum attended / unattended | 331 / 217 | 13 / 0 | 0 / 0 |
| Hospitality / Supermarket / Utilities / MCC 5542, 6051, 6211 / General Retail unattended | 77 / 31 / 31 / 27 / 10 | 0 | 0 |

The 388 executable defect-free TCs cover only General Retail attended (311), Credential on File (47) and Mail/Phone (30). Severity **Medium**.

### H. Traceability to the spec

<a id="d-36"></a>
#### D-36 – Test case spec references that are not ATL105 text

Severity **Low** · new. 705 of the 12,859 ATL105 quotes in the TCs' `spec_basis` do not appear on the cited page (±1), and 26 appear on a different page. Examples:
- **"Table 073 Future Transaction Amount"** (20 TCs): an invented title, not ATL105 text.
- **"Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original…"** (28 citations): an internal note, not ATL105 text.
- "In Purchase/Capture, Sequence Number is same as in Authorization Only request." (p.409): a paraphrase.
- The p.402 quotes are actually on p.84, 89 and 98.

<a id="d-37"></a>
#### D-37 – Follow-on legs not traced to their test case

Severity **Low** · Earlier ID CD-19 (residual). The traceability matrix lists only the first test-data file of each flow. **432 legs of 334 network flows** (and 1 Operations leg) cannot be traced from the requirement to their test case: reversals, TORs, cancellations, voids, completions and Totals.

---

## 4. Items that need SME / BUYPASS confirmation (not counted as defects)

| # | Item | Scope | ATL105 |
|---|---|---|---|
| R-01 | Table 031 `00` (unknown) on chip reads at fuel machines | 126 TCs | App. I p.569 – terminal capability "must be populated for all transactions" and does not change with entry mode |
| R-02 | TVR 95 = `8000048000` ("online PIN entered") on EMV legs that send no PIN | 600 legs (Earlier RV-06) | App. R |
| R-03 | Contactless EMV legs without contactless tags (9F6E / 9F66 / 9F6D / 9F7C) | 251 legs (RV-13) | App. R |
| R-04 | CA Public Key checksum is the QE sample `2012091700000100000102171` | 615 legs (RV-05) | E187 p.476 |
| R-05 | E189 counts the 2-byte BCD prefix of the chip data | 614 legs (RV-04) | E189/E190 |
| R-06 | Totals after Authorization Only, Return, Return → Void and Balance Inquiry send `CC`, count `00001`, amount `0` | 37 legs (R4-13) | E13 / E15 / E16 |
| R-07 | Seg 105 length `107`/`090` (Field Separator after the last field?) | 67 legs (RV-11) | 12.6 |
| R-08 | "Transaction Attributes Segment returned" expected on a MasterCard credit authorization. Seg 134 is conditional (Common AID p.161, settlement type p.109). | 41 TCs | p.211 |
| R-09 | Cancellation (TT S) at customer-activated terminals – not in the p.83 CAT list | 21 legs | p.83, p.47 |
| R-10 | Online PIN on non-EMV credit (magnetic stripe, keyed, COF) | 6 TCs | E33 ("required for debit and EBT") |
| R-11 | Time-based and resend negatives that rely on a harness action | 24 TCs | §2.3.4 |
| R-12 | Keyed/COF original MMYY → follow-on YYMM | all keyed flows (RV-01) | E12 p.372, p.94–95 |
| R-13 | EMV Additional Information framing (`001` / `006` / `EMVYES`) | all EMV legs (RV-03) | App. T |
| R-14 | Placeholder merchant / Terminal Identifier `JX72948216050257001` | 1,530 legs (RV-08) | E102 – must be BUYPASS-assigned |
| R-15 | Response checks such as "(Host Prompts / Host-based Purchase Restriction / Reserved Indicator / Transaction Identifier returned)" share the approved request. Whether the host returns them depends on the merchant profile, which the TC does not state. | ≈ 300 TCs | App. K |

---

## 5. Corrections to earlier rounds

| Earlier item | Correction | ATL105 |
|---|---|---|
| R4-08, Proprietary Data Load `NumberofSegments "1"` (should be `"01"`) | **Withdrawn – not a defect.** | p.200: Number of Segments *"Fixed value of 1"*; E63 (p.395): *"Variable length of up to two digits"* |
| CD-15 / Round-4 recommendation "re-add cash back" | **Withdrawn – cash back is not supported for MasterCard credit.** Cash Amount on MasterCard credit requests is now part of D-04. | p.160 |

---

## 6. Evidence

- **[MC_Credit_Round5_Findings.csv](./Round5_Evidence/MC_Credit_Round5_Findings.csv)** – every finding: 5,327 rows (2,788 DEFECT, 2,067 REVIEW, 472 NEG). Filter by `check_id` (named in each defect above) and `test_case`.
- **[TC_Reports/](./Round5_Evidence/TC_Reports/)** – field-by-field Java runner reports for a 42-TC sample, plus their index. It covers one approved purchase per entry mode, each flow type, and an example of most defects above.
- **[scripts/](./Round5_Evidence/scripts/)** – the checks used. To reproduce:
  1. Set `ROOT` to the delivery folder.
  2. Run `build_index.py`, then `r5_validate.py` (produces the CSV), then `coverage_check.py` (produces D-35).
  3. The Java runner sample IDs are in `java_sample_ids.txt`.
