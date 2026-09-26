# Segment 103 EBT: Card and POS SME Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, sections 10.5.5, 10.5.6, 12.4, 13.2 Elements 153, 154, and 164, and Appendix M.

## 1. What Segment 103 Means

Segment 103 is the **EBT Data Segment**. It belongs in Data Section No. 3 and carries information that cannot be represented by the standard Segment 100 alone.

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

This request/response difference must be explicit in test data. A validator must not apply request serialization rules to a response.

## 3. When Segment 103 Is Required

Segment 103 is required when the transaction flow needs EBT-specific data. The transaction type alone is not enough to decide this.

Evaluate all of these questions:

1. Is the payment or benefit program EBT, SNAP, cash benefit, WIC, or eWIC?
2. Is the transaction a normal EBT purchase, an authorization/benefits inquiry, a balance inquiry, a completion, a reversal, or voucher clear?
3. Is a clerk or voucher involved?
4. Is the transaction carrying WIC discounts or product-level WIC results?
5. Is the merchant using EBT program data such as HIP data?
6. Does the source flow require Segment 103 in Data Section No. 3?

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

- eWIC does not support Return transactions according to the cited section.
- Supported eWIC payment type is EBT WIC (`50`).
- Supported eWIC authorizers include Nevada (`74`), Kentucky (`75`), and ACS WIC (`83`).
- Attended self-checkout is treated as unattended by eWIC processors for POS Condition Code purposes.
- eWIC receipts require payment tender type, transaction type, clerk ID, voucher ID where applicable, amounts, balances, approval/decline messages, and benefit expiration information.

## 5. Element 153: WIC Discount Amount

Element 153 is used for eWIC merchant discounts or cents-off coupons.

The positional format is:

```text
Positions 1-2:   Account Type = 97
Positions 3-4:   Amount Type = 52
Positions 5-7:   Currency Code
Positions 8-20:  Amount with sign convention
```

The amount uses the specification's `0`, `C`, or `D` amount-sign convention. The currency code must be valid under Appendix L.

SME question: Is this amount a merchant discount/coupon adjustment, or is it the normal transaction amount in Segment 100? They are not interchangeable.

## 6. Element 154: WIC Product Data

Element 154 carries eWIC product information and can contain:

- Total Length
- Earliest WIC Benefit Expiration Date
- WIC Prescription Balance Information
- WIC UPC Exception/Denial Information
- WIC UPC Purchase Information

It is variable length up to 3,001 bytes and is composed of tagged subelements. It is not a free-form product description.

Important POS behavior:

- Balance Inquiry and Authorization responses may return benefit expiration and prescription balance information.
- Purchase Completion and Voucher Clear responses may return UPC exception/denial information.
- For WIC fruits and vegetables purchased through a Cash Value Benefit, quantity is set equal to the price in WIC Purchase Information.
- If an item price exceeds the state Approved Product List value, an exception may be returned and the settlement/approved amount may be adjusted.

## 7. Element 164: EBT Program Data

Element 164 is used for EBT program-specific data and supports HIP-related behavior. It has a maximum length of 267 bytes and contains a total length plus one to six program-data subelements, with each subelement limited to 44 bytes.

The source identifies program-data tags including `50`, `51`, `52`, and `IT`. The exact interpretation depends on the program layout and Appendix M examples. Do not infer a tag's business meaning from the tag number alone.

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

## 9. Validator Rules To Implement

The Segment 103 validator should check:

- Segment type is exactly `103`.
- Segment length is present and includes field separators.
- Segment 103 length is within `001-3334`.
- A four-digit Segment Length is permitted for Segment 103.
- Request fields preserve separators, including empty conditional fields.
- Response serialization uses the response-specific separator rule.
- Clerk ID and Voucher ID are validated when the flow requires them.
- Element 153 follows the positional WIC discount format and valid currency code.
- Element 154 is bounded to 3,001 bytes and uses recognized subelement structure.
- Element 164 is bounded to 267 bytes and contains valid program-data structure.
- EBT/eWIC flow conditions require Segment 103 when the source rule says so.
- Non-EBT transactions do not receive Segment 103 without an applicable condition.
- Element 63 counts Segment 100 plus Segment 103 and every other serialized segment.
- eWIC prompt code, payment type, authorizer, and POS condition are consistent with the declared flow.

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
- Element 84, Segment Length: lines 21319-21359.
- Element 153, WIC Discount Amount: lines 23350-23390.
- Element 154, WIC Product Data: lines 23390-23430 and following.
- Element 164, EBT Program Data: extracted source around line 18092 and Appendix M.
- [Financial Transaction Request Sections](11-financial-transaction-request-sections.md).
- [Segment Compatibility Matrix](segment-compatibility-matrix.md).
