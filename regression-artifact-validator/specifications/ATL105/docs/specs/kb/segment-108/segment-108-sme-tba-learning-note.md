# Segment 108 Loyalty Card Data Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, sections 10.9, 11.2, 12.7, and 13.2 Elements 138-151.

## 1. What Segment 108 Means

Segment 108 is the **Loyalty Card Data Segment**. Unlike Segments 101 (Fleet), 102 (Product Code), and 103 (EBT) — which are optional Data Section 3 *companions* of a generic Financial Transaction Request — Segment 108 belongs to its own dedicated **Loyalty Card Transaction Request** message family. It is always required there, always in Field No. 4 of Data Section 3, and it is paired only with the optional Segment 114 (SKU Data Segment).

A useful message mental model is:

```text
TCP/IP header
  + Data Section 1: ATL105 identity and segment count
  + Data Section 2: Segment 100 standard transaction data
  + Data Section 3: Segment 108 loyalty program data (required) + Segment 114 SKU data (optional)
```

Segment 108 is not a "loyalty add-on" to a regular purchase's Financial Transaction Request — a Loyalty Card Transaction is a distinct request type in its own right, submitted for loyalty-specific advice functions (add account, coupon redemption, points redemption, account inquiry, sale update, totals report, and more).

## 2. Segment 108 Layout

| Field | Element | Length | Status | SME meaning |
| --- | ---: | ---: | --- | --- |
| Segment Type | 85 | 3 | Required | Fixed value `108` |
| Segment Length | 84 | 3 | Required | Encoded length of Segment 108, including its separators |
| Loyalty Program ID | 138 | 6 | Required | Which loyalty program the transaction belongs to |
| Loyalty Account Number | 139 | 24 | Conditional | The cardholder's loyalty account; may be replaced by Street Address + Phone Number when the card isn't present |
| Points to Redeem | 140 | 6 | Optional | Loyalty points applied to the purchase |
| Coupon ID | 141 | 19 | Optional | Identifies a redeemed loyalty coupon |
| Coupon Amount | 142 | 8 | Optional | Dollar value of the redeemed coupon |
| Update Code | 143 | 1 | Conditional | The loyalty advice function being performed |
| Street Address | 144 | 5 | Conditional | Cardholder's street number (used when card not present) |
| Phone Number, Loyalty | 145 | 10 | Conditional | Cardholder's phone number (used when card not present) |
| Expiration Date | 146 | 4 | Conditional | Reserved for future use — do not treat as a real field yet |
| Payment Tender Type | 148 | 2 | Required | Whether this is a loyalty-only transaction or a dual-card (loyalty + payment) transaction |
| Loyalty Track 2 Data | 147 | 38 | Optional | Raw Track 2 data read from the loyalty card |
| Loyalty Information Version | 150 | 1 | Optional | Which Appendix K loyalty-information table version the device supports (defaults to 1) |
| Unit of Work | 151 | 19 | Optional | Loyalty host's transaction-matching key, required on reversals |

Maximum Segment 108 length is **142 alphanumeric characters** (confirmed authoritative over a conflicting "84" figure that appears in the Loyalty Card Transaction Request layout table — see SME intake `SEG108-SME-001`, resolved).

### Important field-ordering quirk

Fields 12 and 13 are **not** in ascending element-number order: field 12 is element 148 (Payment Tender Type) and field 13 is element 147 (Loyalty Track 2 Data). A converter or validator that assumes element-number order will mis-parse the wire message.

### Serialization rule

All fields are separated by Field Separators, including a separator following the last field (field 15). When a field is not populated, still send the Field Separator.

## 3. When Segment 108 Is Required

Segment 108 is **always required** in a Loyalty Card Transaction Request — there is no conditional-applicability decision tree here, unlike Segment 103 (EBT) or Segment 101 (Fleet). The applicability question that matters is upstream: *is this transaction being submitted as a Loyalty Card Transaction Request at all*, versus a standard Financial Transaction Request. Per Element 63's processing rules, Financial Transaction Requests never carry Segment 108; only Loyalty Card Transaction Requests do.

## 4. Loyalty Advice Functions (Update Code)

| Update Code | Function | POS meaning |
| --- | --- | --- |
| `A` | Add account | Enroll a new loyalty account; clerk swipes new card and enters street number + phone |
| `C` | Coupon redemption | Redeem a coupon for payment; enter Coupon ID + Coupon Amount |
| `E` | Expiration date update | Extend the loyalty account's expiration by a host-defined amount |
| `I` | Account Inquiry | Query loyalty account status; host returns Loyalty Print Data |
| `P` | Points redemption | Redeem loyalty points for payment |
| `S` | Sale update | Post noncredit-card sale data to the loyalty host |
| `T` | Totals Report, Loyalty | Request a summary of all loyalty transactions, subtotaled by card |
| `U` | Update account | Update street address / phone number on file |

`[PROVISIONAL P-02]` — Section 10.9.3 additionally describes "Reversal of coupon redeem" and "Reversal of points redeemed" as distinct advice functions, each requiring the original approval number, but no Update Code value is documented for either. Do not guess a code for these; treat as `REVIEW_REQUIRED` until SME input arrives (`SEG108-SME-002`).

## 5. Loyalty Card Not Present

Per Section 10.9.1.2: when a loyalty card is not present, the device manually keys the consumer's Street Address and Phone Number **in lieu of** the Loyalty Account Number. This is a genuine cross-field business condition (a form of "either/or" requirement), but per SME guidance it is cataloged only, not enforced in the JSON payload validator — consistent with how similar business-condition rules are treated for other segments (e.g., Segment 101's fleet-eligible card types, Segment 103's EBT applicability triggers).

## 6. POS Flow Examples

### Loyalty purchase with points redemption

```text
Clerk scans items
  -> Clerk swipes loyalty card (or keys Street Address + Phone Number)
  -> Clerk enters Points to Redeem
  -> Segment 100 carries standard transaction identity/amount
  -> Segment 108 carries Loyalty Program ID, Account Number, Points to Redeem, Update Code = P
  -> Response reduces the payment amount by the redeemed points value
```

### Account inquiry

```text
Clerk selects "Account Inquiry" from the loyalty menu
  -> Clerk swipes loyalty card (or keys Street Address + Phone Number)
  -> Segment 108 carries Update Code = I
  -> Host returns Loyalty Print Data (pre-programmed message) for the device to print
```

### Totals Report, Loyalty

```text
Clerk selects the loyalty totals menu option
  -> Segment 108 carries Update Code = T
  -> Host returns a summary of all loyalty transactions, subtotaled by card number
```

## 7. Validator Rules Implemented (`Segment108PayloadValidator`)

- Segment type is exactly `108` (`SEG108-R-003`).
- Segment 108 requires a Standard Segment (100) sibling within a Loyalty Card Transaction Request (`SEG108-R-001`, `SEG108-R-002`).
- Segment length is 3 digits and within `001-142` (`SEG108-R-004`, `SEG108-R-005`).
- Loyalty Program ID is required and numeric max 6 (`SEG108-R-008`).
- Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Street Address, Phone Number, Loyalty Track 2 Data, Loyalty Information Version, and Unit of Work follow their documented type/length bounds when populated (`SEG108-R-009` through `SEG108-R-020`, excluding the reserved `SEG108-R-016`).
- Update Code, when populated, is one of the 8 documented values (`SEG108-R-013`).
- Payment Tender Type is required and one of the 15 documented values (`SEG108-R-017`).
- Segment 108 appears at most once per message (`SEG108-R-021`).

Rules not enforced in code (cataloged only, pending SME/TBA input or because they are wire-serialization/cross-field business concerns not representable as simple JSON structural checks): `SEG108-R-002` (upstream message-family applicability), `SEG108-R-006`, `SEG108-R-007` (wire field order/separators), `SEG108-R-009`'s card-not-present substitution condition, `SEG108-R-016` (reserved field, intentionally not validated), `SEG108-R-022`, `SEG108-R-023`, `SEG108-R-024`.

## 8. Suggested Segment 108 Test Scenarios

| ID | Scenario | Expected result |
| --- | --- | --- |
| LOY-108-001 | Loyalty Card Transaction Request with valid Segment 108 type and length | Pass |
| LOY-108-002 | Loyalty Card Transaction Request omits Segment 108 | Fail |
| LOY-108-003 | Segment 108 uses invalid type `100` | Fail |
| LOY-108-004 | Points redemption with Update Code `P` and Points to Redeem populated | Pass |
| LOY-108-005 | Coupon redemption with Update Code `C`, Coupon ID, and Coupon Amount populated | Pass |
| LOY-108-006 | Account Inquiry with Update Code `I` and no card present (Street Address + Phone instead) | Pass |
| LOY-108-007 | Update Code contains an undocumented value | Fail |
| LOY-108-008 | Payment Tender Type contains an undocumented value | Fail |
| LOY-108-009 | Payment Tender Type omitted (required field) | Fail |
| LOY-108-010 | Segment Length exceeds 142 | Fail |
| LOY-108-011 | Loyalty Information Version outside {1,2} | Fail |
| LOY-108-012 | Duplicate Segment 108 occurrence in one message | Fail |
| LOY-108-013 | Segment 108 present alongside Segment 101 (Fleet) in the same message | Review — no documented conflict, but also no documented support; `REVIEW_REQUIRED` |
| LOY-108-014 | Reversal-of-coupon-redeem style Update Code | Review pending `SEG108-SME-002` |

## 9. SME Checklist

When reviewing an AI-generated loyalty artifact, ask:

- Does the artifact treat Segment 108 as belonging to a distinct Loyalty Card Transaction Request, not a Financial Transaction Request companion?
- Does it serialize fields 12-13 in the correct order (148 then 147), not ascending element-number order?
- Does it use a documented Update Code and Payment Tender Type value?
- Does it model the loyalty-card-not-present substitution (Street Address + Phone instead of Account Number) where applicable?
- Does it avoid inventing an Update Code for the undocumented reversal functions?
- Does it leave Expiration Date (146) unpopulated or ignore its content, consistent with it being reserved?
- Does it require manual review where the specification has not defined the combination (e.g., Segment 108 with 101/102/103)?

## Source References

- Section 10.9, Loyalty Card Processing Requirements: lines 6039-6165.
- Section 11.2, Loyalty Card Transactions (request/response message format): lines 7986-8090.
- Section 12.7, Loyalty Card Data Segment: lines 12111-12200.
- Element 63, Number of Segments (message-family exclusivity): lines 20770-20825.
- Elements 138-151: lines 23106-23335.
- [Segment 108 Rule Catalog](coverage/segment-108-rule-catalog.json).
- [SME/TBA Input Register](segment-108-sme-tba-input-register.md).
