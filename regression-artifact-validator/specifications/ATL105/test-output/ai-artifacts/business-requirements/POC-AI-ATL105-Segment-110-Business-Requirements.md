# POC AI Segment 110 Business Requirements

Source: `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`

Scope: requirements whose `segment_number` equals `110` or whose `related_entity_ids` include `ENT-SEG-110` in the approved requirement catalog (DIRECT_SEGMENT_110).

Count: 94

| ID | Source rule | Page | Category | Requirement type | Confidence | Requirement |
|---|---|---:|---|---|---:|---|
| `REQ-SRC-ATL105-PDF-001:138` | `ENT-ELEM-122` | 434 | Field | Boundary | 50% | MICR Data must be alphanumeric and must not exceed 50 bytes. |
| `REQ-SRC-ATL105-PDF-001:139` | `ENT-ELEM-123` | 434 | Field | Boundary | 50% | Driver's License must be alphanumeric and must not exceed 40 bytes. |
| `REQ-SRC-ATL105-PDF-001:140` | `ENT-ELEM-124` | 435 | Field | Boundary | 50% | State Code must be alphanumeric and must not exceed 2 bytes. |
| `REQ-SRC-ATL105-PDF-001:141` | `ENT-ELEM-124` | 435 | Field | Field Validation | 50% | State Code must be one of: AL, AK, AZ, AR, CA, CO, CT, DE, DC, FL, GA, HI, ID, IL, IN, IA, KS, KY, LA, ME, MD, MA, MI, MN, MS, MO, MT, NE, NV, NH, NJ, NM, NY, NC, ND, OH, OK, OR, PA, RI, SC, SD, TN, TX, UT, VT, VA, WA, WV, WI, WY, GU, PR, VI, AA, AE, AP, XX, AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT, NA. |
| `REQ-SRC-ATL105-PDF-001:142` | `ENT-ELEM-125` | 435 | Field | Boundary | 50% | Date of Birth must be numeric and must not exceed 8 bytes. |
| `REQ-SRC-ATL105-PDF-001:143` | `ENT-ELEM-126` | 435 | Field | Boundary | 50% | Check Type must be alphanumeric and must not exceed 1 bytes. |
| `REQ-SRC-ATL105-PDF-001:144` | `ENT-ELEM-126` | 435 | Field | Field Validation | 50% | Check Type must be one of: P Personal, C Company. |
| `REQ-SRC-ATL105-PDF-001:145` | `ENT-ELEM-127` | 436 | Field | Boundary | 50% | Check Number must be alphanumeric and must not exceed 8 bytes. |
| `REQ-SRC-ATL105-PDF-001:146` | `ENT-ELEM-128` | 436 | Field | Boundary | 50% | Customer Phone Number must be numeric and must not exceed 10 bytes. |
| `REQ-SRC-ATL105-PDF-001:147` | `ENT-ELEM-129` | 436 | Field | Boundary | 50% | Customer Last Name must be alphanumeric and must not exceed 24 bytes. |
| `REQ-SRC-ATL105-PDF-001:148` | `ENT-ELEM-130` | 437 | Field | Boundary | 50% | Check Issue Date must be numeric and must not exceed 8 bytes. |
| `REQ-SRC-ATL105-PDF-001:254` | `ENT-ELEM-239` | 490 | Field | Boundary | 50% | Enhanced Fleet Data must be alphanumeric and must not exceed 999 bytes. |
| `REQ-SRC-ATL105-PDF-001:780` | `BR-130-1` | 130 | Field | Business Rule | 74% | MICR data required for check processing when swiped entry is used, for SCAN (ETC) and ECA/TeleCheck. |
| `REQ-SRC-ATL105-PDF-001:781` | `BR-130-2` | 130 | Field | Business Rule | 68% | Driver's license data and alphabetical State Code required for manual entry check processing via SCAN (ETC) or ECA/TeleCheck. |
| `REQ-SRC-ATL105-PDF-001:782` | `BR-130-3` | 130 | Field | Business Rule | 70% | Certegy requires driver's license data, State Code, and date of birth for manual entry check processing. |
| `REQ-SRC-ATL105-PDF-001:790` | `BR-131-6` | 131 | Amount | Business Rule | 69% | Total Check Amount, Response Check Number, Return Fee Amount must appear on receipt. |
| `REQ-SRC-ATL105-PDF-001:793` | `BR-131-9` | 131 | Field | Business Rule | 48% | Additional recommended fields include Customer Telephone Number if prompted, Custom Field prompt/data, Merchant Trace ID. |
| `REQ-SRC-ATL105-PDF-001:979` | `BR-173-3` | 173 | Field | Business Rule | 85% | Data Section No. 3 contains none, one, or more of Check, Variable Information, or ECA/TeleCheck Data Segments. |
| `REQ-SRC-ATL105-PDF-001:983` | `BR-174-4` | 174 | Field | Business Rule | 85% | ECA/TeleCheck Service Transaction Request contains segments in Field Nos. 4-6: Segments 110, 111, 113. |
| `REQ-SRC-ATL105-PDF-001:1202` | `BR-239-1` | 239 | Field | Business Rule | 70% | Check Data Segment can appear in any field of Data Section No. 3 (Financial Transactions). |
| `REQ-SRC-ATL105-PDF-001:1203` | `BR-239-2` | 239 | Field | Business Rule | 90% | Check Data Segment has a maximum length of 168 alphanumeric characters. |
| `REQ-SRC-ATL105-PDF-001:1204` | `BR-239-3` | 239 | Message | Business Rule | 90% | All fields in Check Data Segment are separated by Field Separators; unpopulated fields still send the separator. |
| `REQ-SRC-ATL105-PDF-001:1205` | `BR-239-4` | 239 | Field | Business Rule | 85% | Check Data Segment originates at the device. |
| `REQ-SRC-ATL105-PDF-001:1206` | `BR-240-1` | 240 | Lifecycle | Business Rule | 21% | Merchant supporting Alternate MICR format should send 'Y' in Alternate MICR Indicator field. |
| `REQ-SRC-ATL105-PDF-001:1207` | `BR-240-2` | 240 | Lifecycle | Business Rule | 15% | Alternate MICR Indicator is typically used with MICR/Driver's License number in a check transaction. |
| `REQ-SRC-ATL105-PDF-001:1210` | `BR-241-1` | 241 | Field | Business Rule | 79% | Check Number is required for manually keyed check data. |
| `REQ-SRC-ATL105-PDF-001:1211` | `BR-242-1` | 242 | Field | Business Rule | 18% | Alternate MICR IND is not required for merchants who do not support the Alternate MICR format. |
| `REQ-SRC-ATL105-PDF-001:1212` | `BR-242-2` | 242 | Field | Business Rule | 18% | Value 'Y' in Alternate MICR IND indicates Alternate MICR format is being sent from the terminal. |
| `REQ-SRC-ATL105-PDF-001:1401` | `BR-294-2` | 294 | Field | Business Rule | 18% | Money Code Check Number is required for all Money Code transactions. |
| `REQ-SRC-ATL105-PDF-001:1403` | `BR-294-4` | 294 | Field | Business Rule | 18% | Table Length for Money Code Check Number has a maximum variable value of 015. |
| `REQ-SRC-ATL105-PDF-001:1577` | `BR-343-1` | 343 | Field | Business Rule | 84% | MICR Data maximum length is 50 bytes. |
| `REQ-SRC-ATL105-PDF-001:1612` | `BR-359-1` | 359 | Field | Business Rule | 71% | MICR data up to 23 bytes must also be included in MICR Data of Check Data Segment. |
| `REQ-SRC-ATL105-PDF-001:1652` | `BR-365-4` | 365 | Field | Business Rule | 74% | For Certegy transaction, information consists of 2-char State Code and 6-char birth date (MMDDYY). |
| `REQ-SRC-ATL105-PDF-001:1653` | `BR-365-5` | 365 | Field | Business Rule | 68% | If driver's license used as ID for SCAN (ETC) transaction, information consists of 2-char State Code. |
| `REQ-SRC-ATL105-PDF-001:1756` | `BR-388-3` | 388 | Field | Business Rule | 90% | ECA/TeleCheck Service Transaction Requests: Number of Segments followed by Segment 100, Segment 110, and Segment 111. |
| `REQ-SRC-ATL105-PDF-001:1832` | `BR-400-9` | 400 | Message | Business Rule | 57% | Check Data Segment length is 001-168. |
| `REQ-SRC-ATL105-PDF-001:2040` | `BR-434-1` | 434 | Field | Business Rule | 84% | MICR Data is required on all check transactions. |
| `REQ-SRC-ATL105-PDF-001:2041` | `BR-434-2` | 434 | Field | Business Rule | 84% | If MICR data is greater than 50 bytes, include only the first 50 bytes from left to right. |
| `REQ-SRC-ATL105-PDF-001:2044` | `BR-434-5` | 434 | Field | Business Rule | 81% | Driver's License is required on all manually entered check transactions. |
| `REQ-SRC-ATL105-PDF-001:2045` | `BR-435-1` | 435 | Field | Business Rule | 78% | State Code is required when Driver's License (Element 123) is included in a check transaction. |
| `REQ-SRC-ATL105-PDF-001:2046` | `BR-435-2` | 435 | Field | Business Rule | 84% | Check Type is required on all manually entered check transactions. |
| `REQ-SRC-ATL105-PDF-001:2047` | `BR-435-3` | 435 | Field | Business Rule | 84% | Check Type valid codes are P (Personal) and C (Company). |
| `REQ-SRC-ATL105-PDF-001:2048` | `BR-436-1` | 436 | Field | Business Rule | 84% | Check Number is required on all manually entered check transactions. |
| `REQ-SRC-ATL105-PDF-001:2050` | `BR-439-1` | 439 | Field | Business Rule | 58% | Extended MICR Data must be populated in addition to MICR Data when raw MICR data exceeds the length. |
| `REQ-SRC-ATL105-PDF-001:2375` | `BR-559-4` | 559 | Field | Business Rule | 47% | MICR Type '18' formats data as <aba><acct> concatenated without delimiters. |
| `REQ-SRC-ATL105-PDF-001:2376` | `BR-559-5` | 559 | Field | Business Rule | 47% | MICR Type 'T$' formats data as T<aba>A<acct>C<checknum>. |
| `REQ-SRC-ATL105-PDF-001:2377` | `BR-559-6` | 559 | Field | Business Rule | 44% | MICR Type '09' indicates raw MICR data that is not touched/modified. |
| `REQ-SRC-ATL105-PDF-001:2378` | `BR-559-7` | 559 | Field | Business Rule | 47% | MICR Type '19' uses numerals from bottom of check. |
| `REQ-SRC-ATL105-PDF-001:3143` | `ENT-FIELD-110-1` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 1 (Segment Type) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3144` | `ENT-FIELD-110-1` | 241 | Field | Format | 95% | In Check Data Segment (Data Segment No. 110), field 1 (Segment Type) must equal the fixed value 110. |
| `REQ-SRC-ATL105-PDF-001:3145` | `ENT-FIELD-110-2` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 2 (Segment Length) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3146` | `ENT-FIELD-110-3` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 3 (MICR Data) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3147` | `ENT-FIELD-110-4` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 4 (Driver's License) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3148` | `ENT-FIELD-110-5` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 5 (State Code) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3149` | `ENT-FIELD-110-6` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 6 (Date of Birth) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3150` | `ENT-FIELD-110-7` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 7 (Check Type) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3151` | `ENT-FIELD-110-8` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 8 (Check Number) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3152` | `ENT-FIELD-110-9` | 241 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 9 (Customer Phone Number) is optional and may be omitted. |
| `REQ-SRC-ATL105-PDF-001:3153` | `ENT-FIELD-110-10` | 242 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 10 (Customer Last Name) is optional and may be omitted. |
| `REQ-SRC-ATL105-PDF-001:3154` | `ENT-FIELD-110-11` | 242 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 11 (Check Issue Date) is optional and may be omitted. |
| `REQ-SRC-ATL105-PDF-001:3155` | `ENT-FIELD-110-12` | 242 | Field | Field Validation | 95% | In Check Data Segment (Data Segment No. 110), field 12 (Alternate MICR IND) is optional and may be omitted. |
| `REQ-SRC-ATL105-PDF-001:3838` | `REL-ENT-SEG-110-FINANCIAL_TRANSACTION_REQUEST` | 216 | Dependency | Dependency | 68% | The Financial Transaction Request transaction includes Check Data Segment (Data Segment No. 110). |
| `REQ-SRC-ATL105-PDF-001:3944` | `REL-ENT-ELEM-122-ENT-ELEM-2` | 434 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-122 depends on ENT-ELEM-2; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:3945` | `REL-ENT-ELEM-122-ENT-SEG-110` | 434 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-122 depends on ENT-SEG-110; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:3946` | `REL-ENT-ELEM-124-ENT-ELEM-123` | 435 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-124 depends on ENT-ELEM-123; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:3992` | `REL-ENT-ELEM-239-ENT-ELEM-239` | 490 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-239 depends on ENT-ELEM-239; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:4011` | `REL-ENT-FIELD-113-9-ENT-ELEM-122` | 247 | Dependency | Cross-Segment Rule | 80% | ENT-FIELD-113-9 depends on ENT-ELEM-122; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:4164` | `ENT-SEG-110` | 173 | Dependency | Dependency | 70% | The ECA/TeleCheck Service Transaction Request transaction includes Check Data Segment (Data Segment No. 110). |
| `REQ-SRC-ATL105-PDF-001:4706` | `ENT-ELEM-126` | 435 | Dependency | Cross-Segment Rule | 87% | Data element 126 accepts documented value 'P' â€” P denotes Personal check type. |
| `REQ-SRC-ATL105-PDF-001:4707` | `ENT-ELEM-126` | 435 | Dependency | Cross-Segment Rule | 87% | Data element 126 accepts documented value 'C' â€” C denotes Company check type. |
| `REQ-SRC-ATL105-PDF-001:4802` | `ENT-ELEM-239` | 490 | Dependency | Cross-Segment Rule | 77% | Data element 239 accepts documented value '004' â€” Prompt Data Request value triggers inclusion of enhanced prompts. |
| `REQ-SRC-ATL105-PDF-001:4850` | `ENT-ELEM-124` | 507 | Dependency | Cross-Segment Rule | 57% | Data element 124 accepts documented value 'AL/01 etc (State Codes)' â€” State Code field uses alphabetical and ANSI codes per state table. |
| `REQ-SRC-ATL105-PDF-001:5414` | `ENT-SEG-110` | 239 | Field | Field Validation | 84% | Check Data Segment: Data Segment No. 110, can appear in any field of Data Section No. 3, originates at device. |
| `REQ-SRC-ATL105-PDF-001:5415` | `ENT-ELEM-MICR-TAC` | 239 | Field | Field Validation | 44% | Full MICR Line - TAC Format: MICR format example: T999999999A999999999999999999C999999. |
| `REQ-SRC-ATL105-PDF-001:5416` | `ENT-ELEM-MICR-TOAD` | 239 | Field | Field Validation | 44% | Full MICR Line - RAW TOAD Format: TOAD is symbol substitution for MICR characters on check, using T, O, A, D symbols. |
| `REQ-SRC-ATL105-PDF-001:5417` | `ENT-ELEM-ALT-MICR` | 240 | Field | Field Validation | 67% | Alternate MICR Indicator: New field identifying 'ALB1' format so Buypass supports both RAW TOAD MICR and TAC MICR data. |
| `REQ-SRC-ATL105-PDF-001:5418` | `ENT-ELEM-ALT-MICR-IND` | 242 | Field | Field Validation | 31% | Alternate MICR IND: Denotes merchant supports Alternate MICR format; optional field. |
| `REQ-SRC-ATL105-PDF-001:5581` | `ENT-ELEM-122` | 343 | Field | Field Validation | 84% | MICR Data: MICR Data has a maximum length of 50 bytes, found in Check Data Segment. |
| `REQ-SRC-ATL105-PDF-001:5733` | `ENT-ELEM-122` | 434 | Field | Field Validation | 76% | MICR Data: Identifies the data encoded along the bottom of a check. |
| `REQ-SRC-ATL105-PDF-001:5734` | `ENT-ELEM-123` | 434 | Field | Field Validation | 79% | Driver's License: Identifies the driver's license number used as identification in a check transaction. |
| `REQ-SRC-ATL105-PDF-001:5735` | `ENT-ELEM-124` | 435 | Field | Field Validation | 84% | State Code: 2-character alphabetical state code used with driver's license in check transaction. |
| `REQ-SRC-ATL105-PDF-001:5736` | `ENT-ELEM-125` | 435 | Field | Field Validation | 84% | Date of Birth: 8-digit MMDDYYYY consumer date of birth in a check transaction. |
| `REQ-SRC-ATL105-PDF-001:5737` | `ENT-ELEM-126` | 435 | Field | Field Validation | 84% | Check Type: 1 alphanumeric character identifying type of check used in transaction. |
| `REQ-SRC-ATL105-PDF-001:5738` | `ENT-ELEM-127` | 436 | Field | Field Validation | 79% | Check Number: Identifies the manually entered check number. |
| `REQ-SRC-ATL105-PDF-001:5739` | `ENT-ELEM-128` | 436 | Field | Field Validation | 79% | Customer Phone Number: Identifies the phone number of the consumer presenting the check. |
| `REQ-SRC-ATL105-PDF-001:5740` | `ENT-ELEM-129` | 436 | Field | Field Validation | 79% | Customer Last Name: Identifies the last name of the consumer presenting the check. |
| `REQ-SRC-ATL105-PDF-001:5741` | `ENT-ELEM-130` | 437 | Field | Field Validation | 54% | Check Issue Date: Fixed 8-digit MMDDYYYY date identifying the issue date of the check. |
| `REQ-SRC-ATL105-PDF-001:5748` | `ENT-ELEM-137` | 439 | Field | Field Validation | 54% | Extended MICR Data: Identifies data encoded along the bottom of a check. |
| `REQ-SRC-ATL105-PDF-001:6389` | `ENT-ELEM-122` | 434 | Field | Processing Rule | 90% | MICR Data must be present on all check transactions. |
| `REQ-SRC-ATL105-PDF-001:6390` | `ENT-ELEM-123` | 434 | Field | Processing Rule | 90% | Driver's License must be present when transaction is a manually entered check transaction. |
| `REQ-SRC-ATL105-PDF-001:6391` | `ENT-ELEM-124` | 435 | Field | Processing Rule | 90% | State Code must be present when Driver's License is included in a check transaction. |
| `REQ-SRC-ATL105-PDF-001:6392` | `ENT-ELEM-126` | 435 | Field | Processing Rule | 90% | Check Type must be present when transaction is a manually entered check transaction. |
| `REQ-SRC-ATL105-PDF-001:6393` | `ENT-ELEM-127` | 436 | Field | Processing Rule | 90% | Check Number must be present when transaction is a manually entered check transaction. |
| `REQ-SRC-ATL105-PDF-001:6469` | `ENT-ELEM-239` | 490 | Field | Processing Rule | 90% | Enhanced Fleet Data must include list of enhanced prompts when Element No. 239 equals '004' |
