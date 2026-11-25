# ATL105 MasterCard Fleet – AI Test Data Defects (Consolidated, TC-002172 – TC-002176)

**For:** AI Dev team – please validate each defect and record your response in the register (§3).
**Date:** 2026-11-03
**Specification:** BUYPASS Platform ATL105 Message Format Specifications, **Release 2026-4** (October 09, 2026). All page numbers below are **PDF page numbers** in the 2026-4 PDF.
**AI delivery reviewed:** `31-10\mastercard_fleet` (`test_cases\test_cases.jsonl`, `test_data\TD-TC-*.json`)

---

## 1. Scope

| Review | Test cases | Test data files | Method |
|---|---|---|---|
| MasterCard Fleet EMV Contact Purchase (BR1_TS1) | TC-002172 – TC-002176 (5) | 9 (5 EMV requests, 4 follow-on Financial Transaction Requests) | Automated field-by-field validation (`TestDataSpecFieldValidationRunner` extended with Segment 101 Fleet checks + trained Appendix R/T/I/J oracles), every defect re-verified manually against the 2026-4 PDF |
| Package and traceability (O31-01) | Same 5 TCs | Trace rows in `reports/traceability_matrix_full.json` | Path and `file_exists` check against the delivered folder |

Message and transaction types covered: EMV Financial Transaction Request (purchase `0`), Financial Transaction Request (purchase reversal `8`, time-out reversal `Z`); card type `080` (MasterCard fleet); entry mode chip contact `05`.

Detailed evidence (one report per test case, same format):

- [TC-002172](./TC-002172_ATL105_Field_Validation.md) – Purchase approved
- [TC-002173](./TC-002173_ATL105_Field_Validation.md) – Purchase -> Purchase Reversal
- [TC-002174](./TC-002174_ATL105_Field_Validation.md) – Purchase, no response -> Time-out Reversal
- [TC-002175](./TC-002175_ATL105_Field_Validation.md) – Purchase, late approval -> Time-out Reversal
- [TC-002176](./TC-002176_ATL105_Field_Validation.md) – Purchase, unreadable response -> Time-out Reversal

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
| Test cases with test data reviewed | **5** (9 files) field by field |
| Test cases with at least one defect | **5** |
| Defect types | **4** (FL-02 – FL-04, O31-01); FL-01 moved to review item R-07 |
| Review items | **17** (R-01 – R-17) |

The field-level items (FL-01 – FL-04; FL-01 is now a review item, R-07) are generator rules, not test-case-specific errors: they occur identically in every test case. Fixing FL-02 – FL-04 removes all confirmed field-level defects from these 9 files. FL-03 and FL-04 are the same defects already reported for MasterCard Credit (D-01, D-02); FL-02 is the same rule as Credit D-08; FL-01 is new and specific to the Fleet Data Segment.

| TC | Objective | PASS | FAIL | REVIEW | N/A | Defects |
|---|---|---|---|---|---|---|
| [TC-002172](./TC-002172_ATL105_Field_Validation.md) | Purchase approved | 176 | 7 | 8 | 19 | FL-02, FL-03, FL-04, O31-01 (+ R-07 = former FL-01) |
| [TC-002173](./TC-002173_ATL105_Field_Validation.md) | Purchase -> Purchase Reversal | 319 | 10 | 14 | 35 | FL-02, FL-03, FL-04, O31-01 (+ R-07 = former FL-01) |
| [TC-002174](./TC-002174_ATL105_Field_Validation.md) | Purchase, no response -> Time-out Reversal | 321 | 10 | 9 | 36 | FL-02, FL-03, FL-04, O31-01 (+ R-07 = former FL-01) |
| [TC-002175](./TC-002175_ATL105_Field_Validation.md) | Purchase, late approval -> Time-out Reversal | 321 | 10 | 9 | 36 | FL-02, FL-03, FL-04, O31-01 (+ R-07 = former FL-01) |
| [TC-002176](./TC-002176_ATL105_Field_Validation.md) | Purchase, unreadable response -> Time-out Reversal | 321 | 10 | 9 | 36 | FL-02, FL-03, FL-04, O31-01 (+ R-07 = former FL-01) |

---

## 3. Defect register

Please fill in the last two columns for each item.

| ID | Defect | Element / field | Test cases affected | Severity | AI team: Agree / Disagree | AI team: Fix plan / comment |
|---|---|---|---|---|---|---|
| FL-01 | **Moved to REVIEW (R-07):** Fleet Data Segment length `035` – `021` / `030` / `035` depends on spec reading | E84, Seg 101 | 5 (9 files) | REVIEW – SME to confirm | | |
| FL-02 | Product Code Data Segment length `033` instead of `029` | E84, Seg 102 | 5 (9 files) | Medium – wire length wrong | | |
| FL-03 | EMV Card Sequence Number `01` instead of `001` (= Credit D-01) | E188, Seg 130 | 5 (5 EMV files) | High – every EMV request | | |
| FL-04 | EMV Additional Information is 984 random characters instead of `EMVYES`/`EMVNOT` (= Credit D-02) | E191/E192/E118, Seg 130 | 5 (5 EMV files) | High – every EMV request | | |
| O31-01 | Traceability matrix points to `qe_shaped_test_data/` (not delivered) with `file_exists: true`; step 2 files not traced | `reports/traceability_matrix_full.json` | 5 trace rows; 4 step 2 files never traced | High – traceability unusable | | |

---

## 4. Defects in detail

Each defect lists **where**, **generated vs expected**, **the ATL105 rule** and **how to verify** it.

### FL-01 – Fleet Data Segment (Segment 101) length is `035` – **REVIEW (not a confirmed defect): `021` / `030` / `035` to be confirmed by SME (R-07)**

> **Reclassified to REVIEW (2026-11-03).** After re-checking the 2026-4 spec, the correct Segment 101 length cannot be proven from ATL105 alone:
> - **p.226** (12.2, Fleet segment note): *"All fields are separated by Field Separators. When a field is not populated, still send the Field Separator."* supports keeping every slot (`035`).
> - **p.223** (12.1): *"Any trailing fields within the data segment that are not needed should not be transmitted."* supports `021`, but it is written in the Segment 100 section and uses "should", not "must". It is the only trailing-field rule in the whole spec.
> - **p.227** (12.2, fields 14–18): Fleet Tag 1–5 *"Sent only in Auth Completion message"* supports leaving the 5 Fleet Tag slots out (`030`).
> - The spec has **no worked Fleet segment example** (Appendix B, p.505–509, has Segments 100, 102 and 111 only).
> - The project's own KB (`kb/segment-101`) lists trailing-field omission as an open **SME TBA** item.
>
> So `021`, `030` and `035` are all defensible readings. FL-01 is now tracked as review item **R-07** (SME/BUYPASS to confirm) and is no longer counted as a defect. The analysis below is kept for the SME discussion.

| | |
|---|---|
| Where | All 9 files, `"Fleet Data Segment" → "SegmentLength"` (line 29) |
| Generated | `035` |
| Expected | `021` |
| Calculation | `101`▲`021`▲`0128345`▲`0042`▲ = 4 + 4 + 8 + 5 = **21** (Segment Type, Segment Length, Odometer, Vehicle Number – last populated field is field 4) |
| How `035` was built | 21 + **14** Field Separators for fields 5–18: 9 empty base fields **plus 5 Fleet Tags**. The TC flags say *"Field Separator still sent (p.226)"* for every unprompted field. |
| ATL105 rule | **12.2, p.226:** *"Segment Length … Identifies the data segment's length, including Segment Type's length and Field Separators."* and *"All fields are separated by Field Separators. When a field is not populated, still send the Field Separator."*<br>**12.2, p.227:** Fleet Tag 1–5 (fields 14–18): *"Sent only in Auth Completion message"*.<br>**12.1, p.223:** *"Any trailing fields within the data segment that are not needed should not be transmitted."*<br>**E84, p.406:** length includes Segment Type and all Field Separators. KB rules SEG101-R-005 / SEG101-R-008 (empty **non-trailing** fields keep their Field Separator). |
| Why `035` is wrong under any reading | Fleet Tags 1–5 are only sent in an Auth Completion message, so their separators can never be on a purchase, reversal or time-out reversal. Even if every empty base field kept its separator, the length would be `030`. With trailing empty fields not transmitted (p.223, SEG101-R-008) it is `021`. See R-07. |
| How to verify | Open `TD-TC-002172.json` line 29 and add the field lengths above. |
| Fix | Stop after the last populated field → `021`. Never count Fleet Tag separators outside an Auth Completion. |

#### FL-01 explained in plain words

**What is Segment 101?** A fleet card is a company card used by drivers (trucks, delivery vans). When it is used, the terminal asks extra questions such as *"What is the odometer reading?"* and *"What is the vehicle number?"*. The answers travel to BUYPASS in a separate block of the message called the **Fleet Data Segment (Segment 101)**.

**What is "Segment Length"?** The second field of every segment says **how many characters the whole segment is**. BUYPASS uses this number to know where this block ends and the next one begins. Think of it as the *"number of pages"* written on the cover of a booklet: if it says 35 but the booklet has 21 pages, the reader will start reading the next booklet as if it were still this one.

**The 18 slots of Segment 101 (ATL105 12.2, p.226–227):**

| Field | Name | What the test data has |
|---|---|---|
| 1 | Segment Type | `101` |
| 2 | Segment Length | `035` ← the defect |
| 3 | Odometer | `0128345` |
| 4 | Vehicle Number | `0042` |
| 5–13 | Job Number, Driver ID, Employee No., License, Job ID, Department, Customer Data, User ID, Vehicle ID | all empty |
| 14–18 | Fleet Tag 1 – Fleet Tag 5 | all empty – ATL105 p.227: *"Sent only in Auth Completion message"* |

**Counting what is actually sent** (▲ = Field Separator, counts as 1 character):

```
1 0 1 ▲ 0 2 1 ▲ 0 1 2 8 3 4 5 ▲ 0 0 4 2 ▲
  3  +1+  3  +1+      7      +1+   4   +1  =  21
```

| Piece | Characters |
|---|---|
| `101` + ▲ | 4 |
| `021` + ▲ | 4 |
| `0128345` + ▲ | 8 |
| `0042` + ▲ | 5 |
| **Total** | **21** |

Fields 5–18 are all empty **and come at the end**. ATL105 12.1 (p.223): *"Any trailing fields within the data segment that are not needed should not be transmitted. For example, if a data segment has five fields defined, but the last two fields are optional, only three fields should be transmitted with the third field followed by a Field Separator."* Applying this rule to Segment 101 means nothing is sent for fields 5–18, not even a separator, so the length is **21 → `021`**.

> **Important – this is an interpretation, not a single sentence in the spec.** ATL105 has no sentence saying "Field Separators are not counted for Purchase/Reversal". The `021` result combines three rules:
> 1. **p.227** (12.2, fields 14–18): Fleet Tag 1–5 *"Sent only in Auth Completion message"*, so they are not part of a Purchase or Reversal.
> 2. **p.223** (12.1): trailing fields that are not needed *"should not be transmitted"*. This rule is written in the Segment 100 section, not the Fleet section.
> 3. **p.406** (E84): the length counts *"all data elements in the segment plus all Field Separators … related to those elements"*, i.e. only what is actually sent.
>
> Against this, the Fleet section's own note on **p.226** says: *"All fields are separated by Field Separators. When a field is not populated, still send the Field Separator."* That is why `021` vs `030` is an SME question (R-07).

**How the generator got `035`:** it added **one separator for each of the 14 empty fields (5 to 18)**:

| Reading | Calculation | Result | Valid? |
|---|---|---|---|
| Trailing empty fields not sent (p.223 + p.227 + p.406) | 21 | **`021`** | ✅ our reading – SME to confirm (R-07) |
| Keep a separator for empty fields 5–13 (p.226 note), but none for Fleet Tags (p.227) | 21 + 9 | `030` | ⚠️ possible reading (R-07) |
| Separator for fields 5–13 **and** Fleet Tags 14–18 (what the generator did) | 21 + 9 + 5 | `035` | ❌ counts fields p.227 limits to Auth Completion |

`035` is the one value that is wrong under both readings. It counts 5 separators for Fleet Tags 1–5, which p.227 says are *"Sent only in Auth Completion message"*. These test cases send a Purchase, a Purchase Reversal or a Time-out Reversal, not an Auth Completion. Whether the correct value is `021` or `030` must be confirmed by an SME/BUYPASS (R-07).

**Why it matters:** BUYPASS would expect 14 more characters in the Fleet segment than are present. It would read into the next segment (Product Code, `102…`), so the message may be rejected as badly formatted.

**What to tell the AI team:** *"Segment 101 length must not count separators for Fleet Tags 1–5 (p.227: Auth Completion only), so `035` is wrong. Under the trailing-field rule (p.223) the length is `021`; if an SME confirms the p.226 note applies to fields 5–13 instead, it is `030`."*

### FL-02 – Product Code Data Segment (Segment 102) length is `033`, spec arithmetic gives `029`

| | |
|---|---|
| Where | All 9 files, `"Product Code Data Segment" → "SegmentLength"` (line 49) |
| Generated | `033` |
| Expected | `029` |
| Calculation | `102`▲`029`▲ (8) + `F` (1) + `01` (2) + `400` `U` `01` `\` `21500` `\` `1500` ▲ (18) = **29** |
| ATL105 rule | **12.3, p.229:** *"A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment … A Product Data Field Delimiter always follows Quantity and Unit Price."* The same counting reproduces the **Appendix B example (p.508)** length `049` exactly. |
| Correct in these files | Product Code `400`, UoM `U`, Quantity `01`, Unit Price `21500` ($15.00), Product Amount `1500` = Segment 100 Nonfuel Amount (p.229). |
| Fix | Apply the 12.3 separator/delimiter rules → `029`. Same rule as MasterCard Credit D-08. |

#### FL-02 explained in plain words

**What is Segment 102?** It is the **itemised receipt** inside the message: what was bought, how many, at what price. Fleet companies use it to control what drivers may buy (fuel only, no snacks, etc.). In these test cases there is one item: product `400` (a non-fuel item), 1 unit at $15.00.

**What the test data contains:**

| Field | Name | Value | Meaning |
|---|---|---|---|
| 1 | Segment Type | `102` | Product Code segment |
| 2 | Segment Length | `033` | ← the defect |
| 3 | Service Level | `F` | Full serve (E87) |
| 4 | Number of Products | `01` | 1 product |
| 5 | Product Code | `400` | Non-fuel product |
| 6 | Unit of Measure | `U` | Units |
| 7 | Quantity | `01` | First digit = number of decimals (0) → **1** unit |
| 8 | Unit Price | `21500` | First digit = number of decimals (2) → **$15.00** |
| 9 | Product Amount | `1500` | **$15.00** = Nonfuel Amount in Segment 100 ✅ |

**The special rule for this segment (ATL105 12.3, p.229).** Unlike most segments, fields here are **not** all separated by Field Separators. ATL105 uses two different markers:

- **▲ Field Separator** – only after **Segment Type**, after **Segment Length**, and after the **last Product Amount**.
- **`\` Product Data Field Delimiter** – after **Quantity** and after **Unit Price**.
- **Nothing** between Service Level, Number of Products, Product Code and Unit of Measure: they are simply written next to each other.

ATL105 p.229: *"A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment … A Product Data Field Delimiter always follows Quantity and Unit Price."*

**Counting what should be sent:**

```
1 0 2 ▲ 0 2 9 ▲ F 0 1 4 0 0 U 0 1 \ 2 1 5 0 0 \ 1 5 0 0 ▲
  3  +1+  3  +1+1+ 2 +  3  +1+ 2 +1+    5    +1+   4   +1  =  29
```

| Piece | Characters |
|---|---|
| `102` + ▲ | 4 |
| `029` + ▲ | 4 |
| `F` | 1 |
| `01` | 2 |
| `400` | 3 |
| `U` | 1 |
| `01` + `\` | 3 |
| `21500` + `\` | 6 |
| `1500` + ▲ | 5 |
| **Total** | **29** |

**How the generator got `033`:** it treated this segment like the others and put a separator after **every** field, which adds 4 that ATL105 does not allow:

```
102 ▲ 033 ▲ F ▲ 01 ▲ 400 ▲ U ▲ 01 \ 21500 \ 1500 ▲   = 33
            ↑     ↑      ↑    ↑
            4 extra separators (after Service Level, Number of Products, Product Code, Unit of Measure)
```

**Proof from ATL105's own example (Appendix B, p.505 and p.508):** the spec's POS Purchase example sends

```
102 ▲ 049 ▲ S 02 001 G 34170 \ 31199 \ 500 \ 411 U 010 \ 2179 \ 1790 ▲
```

and states its length as **`049`**. Counting it the same way as above gives exactly 49: 8 + `S` 1 + `02` 2 + first product 20 + second product 18. `S 02 001 G` are written with **no separators between them**, which confirms the rule.

**Why it matters:** BUYPASS would expect 4 more characters than are present, so it would cut into the next segment (`111…`) and misread the message. The amounts themselves are correct: `1500` = $15.00 matches the Nonfuel Amount, as p.229 requires.

**What to tell the AI team:** *"Segment 102 must use the 12.3 rules: Field Separator only after Segment Type, Segment Length and the final Product Amount; `\` after Quantity and Unit Price; nothing between Service Level, Number of Products, Product Code and Unit of Measure. Length = `029`."*

### FL-03 – EMV Card Sequence Number (Element 188) is 2 bytes instead of 3

| | |
|---|---|
| Where | Every EMV request file (5 files, step 1 of each TC), `"EMV Request Data Segment" → "EMVCardSequenceNumber"` (line 78) |
| Generated | `01` |
| Expected | `001` |
| ATL105 rule | **Element 188, p.477:** *"Character Type: AN, Maximum Length: 3 bytes. Representation: **Fixed length of three bytes, right-aligned and padded with zeroes**. Space-filled if EMV Tag 5F34 is not provided by the EMV chip card … Valid Codes/Values: 000—099."* 12.20 Field 4 (p.270). |
| How to verify | Open any EMV TD (e.g. `TD-TC-002172.json`), line 78 → `"01"` is 2 characters. Tag 5F34 = `01`, so the value is right but the width is wrong. |
| Fix | Zero-pad Tag 5F34 to 3 bytes (`001`). Recompute the Segment 130 length (+1). |

### FL-04 – EMV Additional Information (Element 118) does not contain EMV Table Data

| TC | File | Generated E118 (984 characters) |
|---|---|---|
| TC-002172 | `TD-TC-002172.json` : 83 | `S6KXPIA1QC1V6AWD9F8L…KLKQ2PMZ` |
| TC-002173 | `TD-TC-002173.step1.purchase.json` : 83 | `2M6XFE1UTKNTHGORQ8EW…6PIBBVXF` |
| TC-002174 | `TD-TC-002174.step1.purchase.json` : 83 | `S5B0PHAC04MPE7UDJT6K…SCR288J9` |
| TC-002175 | `TD-TC-002175.step1.purchase.json` : 83 | `R12G25W5I9XGNSU1SBWA…XABXTSHU` |
| TC-002176 | `TD-TC-002176.step1.purchase.json` : 83 | `7VJDGR7J19VKJG1NJ3A8…A1EJQE5L` |

| | |
|---|---|
| Generated | Indicator `001`, Length `984`, Information = 984 random A–Z/0–9 characters |
| Expected | Indicator `001`, Information **`EMVYES`** (or `EMVNOT`), Length `006` (see R-13) |
| ATL105 rule chain | 1. **E191 (p.479):** `001` = *"EMV table data"* → the indicator is correct.<br>2. **E192 (p.480):** valid `001–985` → `984` is in range, but this is only the generic range.<br>3. **E118 (p.439):** *"When EMV Additional Information Indicator has a value of 001, EMV table data is included here … Please refer to Appendix T."*<br>4. **Appendix T-1 (p.701):** EMV Table Data – *"Maximum Length n6, Len.: 6"*, Request values **`EMVYES`** (device able to receive EMV Table Data) / **`EMVNOT`** (unable). |
| Root cause | TC marks this value `method: format_synthesis` with flag `UNTRUSTED_VALID_VALUES: attribute's valid_values_parsed skipped`. The generator filled the generic 984 maximum instead of applying Appendix T. |
| Fix | Use `EMVYES`/`EMVNOT`, set E192 to the resulting length, and recompute the Segment 130 length. With `EMVYES` and no framing, `1181` becomes `0204` (−978 for E118, +1 for FL-03). |

## 4b. Package and traceability defects (O31)

### O31-01 – Traceability matrix references a folder that is not in the delivery

| TC | Trace path (`file_exists: true`) | Delivered file | Never traced |
|---|---|---|---|
| TC-002172 | `qe_shaped_test_data/TD-TC-002172.json` | `test_data/TD-TC-002172.json` | – |
| TC-002173 | `qe_shaped_test_data/TD-TC-002173.step1.purchase.json` | `test_data/TD-TC-002173.step1.purchase.json` | `TD-TC-002173.step2.reversal.json` |
| TC-002174 | `qe_shaped_test_data/TD-TC-002174.step1.purchase.json` | `test_data/TD-TC-002174.step1.purchase.json` | `TD-TC-002174.step2.timeout-reversal.json` |
| TC-002175 | `qe_shaped_test_data/TD-TC-002175.step1.purchase.json` | `test_data/TD-TC-002175.step1.purchase.json` | `TD-TC-002175.step2.timeout-reversal.json` |
| TC-002176 | `qe_shaped_test_data/TD-TC-002176.step1.purchase.json` | `test_data/TD-TC-002176.step1.purchase.json` | `TD-TC-002176.step2.timeout-reversal.json` |

| | |
|---|---|
| Rule | `reports/traceability_matrix_full.json` points to `qe_shaped_test_data/TD-TC-…` with `"file_exists": true`, but the delivered package has no `qe_shaped_test_data/` folder (files are in `test_data/`). Only the step 1 file is referenced; step 2 files are never traced. |
| Fix | Rebuild every trace representation from the final delivered paths, one entry per request leg; compute `file_exists` against the delivered package. Same defect as MasterCard Credit O31-01 (Fleet: 249 rows, 320 TD files never traced). |

---

## 5. Review items (data may be acceptable – confirmation needed)

| ID | Item | Applies to | Reference (2026-4 PDF page) | Action / owner |
|---|---|---|---|---|
| R-01 | Table 005 cited as **p.548**; that was its page in 2026-3. In 2026-4 the layout is App. I p.555 and the codes are App. J p.638–639. | All 5 TCs | App. I p.555; App. J p.638–639 | AI team: update citations |
| R-02 | Terminal Identifier `JX72948216050257001` is a placeholder (`MERCHANT_PLACEHOLDER`); State Code `72` = **Yukon, Canada** | All 5 TCs | E102 p.417; App. D p.517 | Client/BUYPASS: supply the real terminal ID |
| R-03 | Test case status `ready` while the Terminal Identifier and fleet card are placeholders | All 5 TCs | E102 p.417 | AI team: do not mark `ready` until real values are supplied |
| R-04 | Account Number is a synthetic Luhn-valid number in the MasterCard fleet range (TC flags `SYNTHETIC_FLEET_CARD`, `AWAITING_CLIENT_VALUE: card type 080 needs a client card profile`) | All 5 TCs | E2 p.364; App. E p.522 | Client: supply the fleet test card |
| R-05 | EMV AID `A0000000041010` (MasterCard credit/debit) used for a fleet card; the TC itself records *"EMV AID for MASTERCARD FLEET pending SME (SME-EMV-01)"* | All 5 TCs | App. R p.697 | SME: confirm the fleet AID |
| R-06 | Service Level `F` (Full serve) on a non-fuel sale (Product Code `400`, no fuel amount). E87 also offers `O` = *"Other/Fuel not present"*. `F` is a valid code. | All 5 TCs | E87 p.410 | SME |
| R-07 | Segment 101 trailing separators: the trailing-field rule is written in 12.1 (p.223); the 12.2 note says *"still send the Field Separator"*. Strict reading gives `021`, the most generous base-field reading `030`. `035` (all 18 slots, p.226 note) is also a defensible reading. No Fleet example in the spec; KB marks it SME TBA. **This item replaces defect FL-01.** | All 5 TCs | 12.1 p.223; 12.2 p.226–227 | SME |
| R-08 | Fleet prompt set for Product Type 2 (Odometer + Vehicle Number) comes from PIPS p.56, not ATL105; the data matches the TC's declared profile | All 5 TCs | PIPS p.56; E64 p.396; E108 p.421 | Client: confirm the fleet card's product type |
| R-09 | Approval Number link cites **p.85** (10.1.2.2 Authorization Only Reversal, type `S`); for Purchase Reversal (`8`) the basis is E5 | TC-002173 | E5 p.368; App. G p.546 | AI team: cite p.368 |
| R-10 | Approval Number is the runtime placeholder `<ApprovalNumber from step 1 response>` (TC provisional value `FCKYJH`). The declared Segment 100 length `084` is consistent with a 6-character Approval Number. | TC-002173 | E5 p.368; E84 p.406 | Recompute the length after substitution |
| R-11 | Partial Approval Indicator `1` sent on the follow-on leg; ATL105 does not say whether it belongs there | TC-002173, TC-002174, TC-002175, TC-002176 | E121 p.440 | SME |
| R-12 | **INFO:** in follow-ons, Account Number and expiry are joined with `=` on the wire | TC-002173, TC-002174, TC-002175, TC-002176 | 10.1.8.2 p.94 | Wire builder |
| R-13 | Exact E192 value / E118 framing for Table 001: App. T lists Table ID n3 and Table Length but no width, so it could be `EMVYES` (`006`) or framed | All 5 TCs | App. T p.701 | BUYPASS/SME |
| R-14 | Packed-BCD 2-byte prefix (`0145`) inside E190 counted in E189 (`147`); matches the App. R example, but the field boundary is not stated | All 5 TCs | E189/E190 p.477–478; App. R p.697 | SME |
| R-15 | CA Public Key File Checksum `2012091700000100000102171` is a QE sample | All 5 TCs | E187 p.476 | Host CA key file |
| R-16 | Cryptogram / IAD / ATC / UN / TVR are placeholders; TVR `8000048000` byte 3 *"online PIN entered"* is set although no PIN block is sent; transaction date 9A = `260101` | All 5 TCs | App. R; EMV Book 3 | Card simulator |
| R-17 | Step 1 needs a host harness ("Withhold the BUYPASS response for more than 30 seconds"). The TOR expected response code `0` is tier FLAG and the TC has no paired response template (`NO_RESPONSE_TEMPLATE`). | TC-002174, TC-002175, TC-002176 | p.40, p.45, p.47 | Test harness / SME |

---

## 6. What is correct (passes in all reviewed test data)

| Area | Result |
|---|---|
| Message type per leg; E55 `ATL105`; E63 segment count (`05` EMV, `04` follow-on); message length ≤ 4,000 | PASS |
| Segment 100: all 17 fields present in spec order; Segment Length correct (`094` purchase, `078` time-out reversal; `084` reversal consistent with a 6-character Approval Number) | PASS |
| E78 Prompt Code per leg: `0080`, `8080`, `Z080` (App. G p.545–546; App. E `080` MasterCard fleet p.522) | PASS |
| E2 PAN: Luhn, MasterCard IIN, ≤ 24; Track 2 combined ≤ 37; chip service code `201`; expiry not expired | PASS |
| E44 `0`; E86 unique per TC and carried on follow-ons (p.40); E121 `1`; E5 empty on purchases and TORs | PASS |
| Segment 101: 18 fields in spec order; Type `101`; Odometer `0128345` (N ≤ 8, E64 p.396); Vehicle Number `0042` (N ≤ 10, E108 p.421); unprompted fields empty; Fleet Tags empty; no Segment 145 (p.226) | PASS (length: see R-07, former FL-01) |
| Segment 102: Product Code `400`, UoM `U`, Quantity `01`, Unit Price `21500`, Product Amount `1500` = Nonfuel Amount | PASS (except FL-02) |
| Segment 111: Table 005 `051` (contact, PIN capable); Table 031 `12`; Segment Length `026` | PASS |
| Segment 130: E187 N25; E189 = chip byte count (147); TLV well-formed; AID A0000000041010; 9F02 = `1500`; 9C `00`; 5F2A/9F1A `0840`; 5F34 = E188 value | PASS (fix FL-03/FL-04 and recompute) |
| Follow-on legs: same card, expiry, entry mode, terminal, sequence number and amount as the original; Segment 130 correctly omitted on reversals (p.161) | PASS |
| Test case `rule_checks` and `link_defs` (prompt code, entry mode, Table 031, segment presence 101/102/111/130, no Fleet Tags, 101/145 exclusion, product total, sequence / terminal / amount links); TD values = TC request values | PASS (runtime links = REVIEW) |

---

## 7. Generator fix checklist (to prevent repeats in the next run)

| # | Rule for the generator | Fixes |
|---|---|---|
| 1 | Pad fixed-length AN fields to their full width: E188 → 3 bytes (`001`). | FL-03 |
| 2 | Never synthesize free text for coded fields; apply the appendix allowed values (App. T: `EMVYES`/`EMVNOT`). Do not skip `valid_values_parsed`. | FL-04 |
| 3 | Segment 101: count separators only up to the last populated field; never emit or count Fleet Tags outside an Auth Completion message. **Apply only after SME confirms R-07.** | R-07 (former FL-01) |
| 4 | Calculate segment lengths with the exact separator rules of each segment (Seg 102: FS after type/length, `\` after quantity/price, FS/`\` after amount). | FL-02 |
| 5 | Recompute Segment 130 and message totals after fixing E188/E118. | FL-03, FL-04 |
| 6 | Cite 2026-4 page numbers and verbatim quotes. | R-01, R-09 |
| 7 | Build traceability from the final delivered paths, one entry per request leg; compute `file_exists` against the package. | O31-01 |

---

## 8. Affected test cases by defect

| ID | Test cases |
|---|---|
| FL-02 (+ R-07 = former FL-01) | TC-002172, 002173, 002174, 002175, 002176 (all 9 files) |
| FL-03, FL-04 | TC-002172, 002173, 002174, 002175, 002176 (step 1 / EMV request files) |
| O31-01 | TC-002172 – 002176 (5 trace rows); step 2 files of TC-002173 – 002176 never traced |
| R-09, R-10 | TC-002173 |
| R-11, R-12 | TC-002173, 002174, 002175, 002176 |
| R-17 | TC-002174, 002175, 002176 |

### How to re-run the validation after fixes

From `regression-artifact-validator` (Fleet checks are in `TestDataSpecFieldValidationRunner`):

```powershell
java -cp <target\classes;jackson jars> com.coreauth.validator.canonical.TestDataSpecFieldValidationRunner "<mastercard_fleet\test_data>" "<mastercard_fleet\test_cases\test_cases.jsonl>" <output dir> TC-002172 TC-002173 TC-002174 TC-002175 TC-002176
```
