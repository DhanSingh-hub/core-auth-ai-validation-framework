# Segment 103 EBT: Card and POS SME/TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, sections 10.5.5, 10.5.6, 12.4, 13.2 Elements 18, 109, 153, 154, and 164, and Appendix M.

## 1. What Segment 103 Means

Segment 103 is the **EBT Data Segment**. It can appear in any of the fields in Data Section No. 3 and carries information that cannot be represented by the standard Segment 100 alone.

A useful message mental model is:

```text
TCP/IP header
  + Data Section 1: ATL105 identity and segment count
  + Data Section 2: Segment 100 standard transaction data
  + Data Section 3: Segment 103 EBT-specific data when the EBT flow requires it
```

Segment 100 identifies the basic transaction. Segment 103 carries EBT-specific context such as voucher information, WIC discounts, WIC product results, and EBT program data.

Segment 103 is not the same thing as an EBT card, an EBT authorizer, or a POS payment type. It is the message segment that transports EBT-specific fields.

## 2. Segment 103 Layout

| Field | Element | Length | Status | SME meaning |
| --- | ---: | ---: | --- | --- |
| Segment Type | 85 | 3 | Required | Fixed value `103` |
| Segment Length | 84 | 3 or 4 | Required | Encoded length of Segment 103, including its separators |
| Clerk ID | 18 | 10 | Conditional | Clerk involved in the EBT operation |
| Voucher ID | 109 | 10 | Conditional | Preprinted EBT voucher used for a local approval |
| WIC Discount Amount | 153 | 40 | Conditional | eWIC merchant discount or cents-off amount |
| WIC Product Data | 154 | 3001 | Conditional | eWIC product, exception, balance, or purchase information |
| EBT Program Data | 164 | 267 | Conditional | EBT program-specific data, including HIP-related data |

Maximum Segment 103 length is **3,334 alphanumeric characters**.

### Important serialization rule

For a **Financial Transaction request**, every Segment 103 field is separated by a Field Separator. If a field is empty, its separator is still sent.

For a **Financial Transaction response**, the specification states that there is no Field Separator between Segment 103 fields.

This request/response difference must be explicit in test data. A validator must not apply request serialization rules to a response. See the dedicated [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md).

## 3. When Segment 103 Is Required

Segment 103 is required (`SEG103-R-002`/`SEG103-R-024`) for Food Stamp Electronic Voucher (Voucher ID, Section 10.5.2.3) and for eWIC Purchase Completion / eWIC Voucher Clear (WIC Discount Amount / WIC Product Data, Section 10.5.5.6). It is optional — present only when Clerk ID, HIP program data, or eWIC query-response data is supplied — for every other transaction type listed in Section 10.5.3, and it is prohibited for eWIC Return (Section 10.5.5.1). See the [full applicability matrix](README.md#7-segment-103-applicability-matrix-resolves-seg103-r-002--seg103-r-024) and `Segment103ApplicabilityValidator`.

A standard non-EBT purchase with no EBT-specific data should not receive Segment 103 merely because the card is present at a POS.

## 4. eWIC Transaction Model

The specification distinguishes several eWIC operations:

| Operation | Prompt Code | POS meaning |
| --- | --- | --- |
| eWIC Authorization / Benefits Inquiry | `3086` | Query approved products and account balance |
| eWIC Authorization Cancellation | `S086` | Cancel a previously submitted authorization |
| eWIC Balance Inquiry | `E086` | Query remaining account balance |
| eWIC Purchase Completion | `0086` | Complete an eWIC purchase after authorization |
| eWIC Purchase Reversal/Void | `8086` | Reverse a purchase after timeout or late response |
| eWIC Voucher Clear | `0086` | Submit an offline-approved voucher; optional transaction |

Additional eWIC facts:

- eWIC does not support Return transactions (`SEG103-R-020`) — this is an absolute prohibition per Section 10.5.5.1 ("These specifications do not support Return transactions"), enforced as a hard validation error, not a narrative caution.
- Supported eWIC payment type is EBT WIC (`50`).
- Supported eWIC authorizers include Nevada (`74`), Kentucky (`75`), and ACS WIC (`83`).
- Attended self-checkout is treated as unattended by eWIC processors for POS Condition Code purposes.
- eWIC receipts require payment tender type, transaction type, clerk ID, voucher ID where applicable, amounts, balances, approval/decline messages, and benefit expiration information.

## 5. Element 153: WIC Discount Amount

Element 153 is used for eWIC merchant discounts or cents-off coupons.

The positional format of each 20-byte block is:

```text
Positions 1-2:   Account Type = 97
Positions 3-4:   Amount Type = 52
Positions 5-7:   Currency Code
Positions 8-20:  Amount with sign convention
```

The amount uses the specification's `0`, `C`, or `D` amount-sign convention. The currency code is validated against the full Appendix L table (`AppendixLCurrencyCodes.java`, resolves P-06).

SME question: Is this amount a merchant discount/coupon adjustment, or is it the normal transaction amount in Segment 100? They are not interchangeable.

## 6. Element 154: WIC Product Data

Element 154 carries eWIC product information and can contain:

- Total Length (4-digit numeric subelement, maximum value 2997)
- Earliest WIC Benefit Expiration Date (`EF`, 8 bytes, format `CCYYMMDD`)
- WIC Prescription Balance Information (`EA`, up to 14 bytes)
- WIC UPC Exception/Denial Information (`PS`, up to 47 bytes)
- WIC UPC Purchase Information (`PS`, up to 34 bytes; shares the `PS` identifier with Exception/Denial and is disambiguated only by an internal bit-map, which is out of scope for structural JSON validation — `SEG103-R-023` bounds both variants at the larger 47-byte maximum)

It is variable length up to 3,001 bytes and is composed of tagged subelements. It is not a free-form product description.

Important POS behavior:

- Balance Inquiry and Authorization responses may return benefit expiration and prescription balance information.
- Purchase Completion and Voucher Clear responses may return UPC exception/denial information.
- For WIC fruits and vegetables purchased through a Cash Value Benefit, quantity is set equal to the price in WIC Purchase Information.
- If an item price exceeds the state Approved Product List value, an exception may be returned and the settlement/approved amount may be adjusted.

## 7. Element 164: EBT Program Data

Element 164 is used for EBT program-specific data and supports HIP-related behavior. It has a maximum length of 267 bytes and contains a 3-digit Total Length subelement (max value 264) plus one to six Program Data subelements, with each subelement limited to 44 bytes.

Section 13.2 and the Appendix M worked strings specify TAG/LEN, fixed ACCOUNT TYPE/CURRENCY/DESCRIPTOR, 12-digit amount details, and an IT address with up-to-9-digit ZIP. However, Appendix M-2/M-4 prose says request TAG `50` AMOUNT TYPE is `40`, while Section 13.2 and both worked strings use `50`. Historical P-03 remains unchanged; new `SEG103-SME-009` keeps both candidates `REVIEW_REQUIRED`. The validator warns for either source-listed TAG 50 candidate and rejects other values.

SME question: Is the program-data payload being validated against the correct state/program layout, or is it only being checked for length?

## 8. POS Flow Examples

### EBT purchase

```text
POS reads EBT card
  -> POS collects PIN and purchase information
  -> Segment 100 carries standard transaction identity and amount
  -> Segment 103 carries EBT-specific data when required
  -> BUYPASS routes/processes the EBT request
  -> POS displays approval, decline, balance, or receipt data
```

### eWIC purchase completion

```text
Earlier eWIC authorization
  -> POS receives approved products/balance
  -> Clerk scans eligible items and discounts
  -> Segment 100 carries completion identity
  -> Segment 103 carries WIC discount/product data
  -> Response returns balances and possible product exceptions
```

### eWIC timeout or late response

```text
Purchase completion response is missing or late
  -> POS sends eWIC Purchase Reversal/Void
  -> Device must receive the reversal response
  -> Device should not send additional eWIC transactions before completion
```

### eWIC voucher clear

```text
Host outage
  -> Authorization center approves by phone
  -> Clerk receives voucher number, account, approval, and amount
  -> POS later sends Voucher Clear
  -> Segment 103 carries applicable voucher/WIC data
```

## 9. Validator Rules Implemented

`Segment103PayloadValidator`:

- Segment type is exactly `103` (`SEG103-R-003`).
- Segment 103 requires a Standard Segment (100) sibling (`SEG103-R-001`).
- Segment length is 3 or 4 digits and within `001-3334` (`SEG103-R-004`, `SEG103-R-005`).
- Clerk ID and Voucher ID, when populated, are numeric max 10 (`SEG103-R-009`, `SEG103-R-010`).
- WIC Discount Amount follows the positional block format, 40-byte bound, and Appendix L currency code (`SEG103-R-011`, `SEG103-R-012`).
- WIC Product Data's Total Length subelement is bounded and consistent with the payload, and optional granular subelements are validated against the EF/EA/PS catalog (`SEG103-R-013`, `SEG103-R-023`).
- EBT Program Data's Total Length subelement is bounded, each of the 1-6 subelements is bounded to 44 bytes with a recognized TAG, and the full Appendix M positional layout is validated when granular fields are supplied (`SEG103-R-014` through `SEG103-R-017`, `SEG103-R-022`).
- Segment 103 appears at most once per message (`SEG103-R-018`).

`Segment103WireFormatValidator`: request/response field-separator rules, field order, device origin, eWIC prompt-code enumeration, and the hard eWIC-Return prohibition (`SEG103-R-006` through `SEG103-R-008`, `SEG103-R-019` through `SEG103-R-021`).

`Segment103ApplicabilityValidator`: the resolved applicability matrix (`SEG103-R-002`, `SEG103-R-024`).

Rule not enforced anywhere in code because Section 12.4 does not document a mutual-exclusion boundary for Segment 103 against another Data Section 3 segment: none — this is intentional, not a gap.

## 10. Suggested Segment 103 Test Scenarios

| ID | Scenario | Expected result |
| --- | --- | --- |
| EBT-103-001 | EBT request with valid Segment 103 type and length | Pass |
| EBT-103-002 | EBT request omits required Segment 103 | Fail |
| EBT-103-003 | Segment 103 uses invalid type `100` | Fail |
| EBT-103-004 | Segment 103 request contains empty conditional fields with separators | Pass |
| EBT-103-005 | Segment 103 request removes an empty-field separator | Fail |
| EBT-103-006 | Segment 103 exceeds 3,334 characters | Fail |
| EBT-103-007 | eWIC purchase completion carries WIC discount amount | Pass |
| EBT-103-008 | WIC discount amount has invalid account/amount type | Fail |
| EBT-103-009 | WIC product data contains an invalid or oversized payload | Fail |
| EBT-103-010 | EBT Program Data contains more than six subelements | Fail |
| EBT-103-011 | eWIC authorization uses prompt code `3086` | Pass |
| EBT-103-012 | eWIC return transaction is generated | Reject or review per flow rule |
| EBT-103-013 | eWIC timeout sends purchase reversal/void | Pass |
| EBT-103-014 | Additional eWIC transaction is sent before reversal response | Fail |
| EBT-103-015 | Unknown EBT/eWIC segment combination | Review |
| EBT-103-016 | Duplicate Segment 103 occurrence in one message | Fail |
| EBT-103-017 | EBT Program Data subelement TAG 50 missing ACCOUNT TYPE 98 | Fail |

## 11. SME Checklist

When reviewing an AI-generated EBT artifact, ask:

- Does the artifact distinguish Segment 100 standard data from Segment 103 EBT data?
- Does it identify whether the flow is SNAP/EBT, WIC/eWIC, HIP, voucher, balance inquiry, or reversal?
- Does it use the correct prompt code and payment type?
- Does it preserve request separators for empty Segment 103 fields?
- Does it distinguish request serialization from response serialization?
- Does it carry discounts and product exceptions in the correct WIC elements?
- Does it model timeout/reversal behavior rather than only a happy path?
- Does it avoid inventing support for eWIC returns?
- Does it require manual review where the specification has not defined the combination?

## Source References

- Section 10.5.5, eWIC Transaction Processing: extracted specification lines 5276-5510.
- Section 10.5.6, Healthy Incentives Program Transaction Processing: lines 5490-5510.
- Section 12.4, EBT Data Segment: lines 11734-11795.
- Element 18, Clerk ID: lines 19955-19965.
- Element 84, Segment Length: lines 21319-21359.
- Element 109, Voucher ID: lines 22015-22025.
- Element 153, WIC Discount Amount: lines 23340-23390.
- Element 154, WIC Product Data: lines 23390-23480 and following.
- Element 164, EBT Program Data: lines 23788-23880 and Appendix M.
- [Financial Transaction Request Sections](../11-financial-transaction-request-sections.md).
- [Segment Compatibility Matrix](../segment-compatibility-matrix.md).
- [Segment 103 Rule Catalog](coverage/segment-103-rule-catalog.json).
