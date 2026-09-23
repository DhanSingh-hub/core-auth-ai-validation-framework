# WIC Product Data (Element 154): SME/TBA Learning Note

## Meaning

Element 154 carries eWIC product and benefit information. It is structured data, not a free-form product description.

## Subelement Catalog

| Identifier | Meaning | Maximum/data rule |
| --- | --- | --- |
| Total Length | Count of following bytes | Four digits, maximum `2997` |
| `EF` | Earliest WIC Benefit Expiration Date | 8 digits, `CCYYMMDD` |
| `EA` | WIC Prescription Balance Information | Up to 14 bytes; bitmap/category/subcategory/quantity structure |
| `PS` | WIC UPC Exception/Denial Information | Up to 47 bytes; internal bitmap selects layout |
| `PS` | WIC UPC Purchase Information | Up to 34 bytes; same identifier, purchase bitmap selects layout |

The shared `PS` identifier is intentional. Structural validation can enforce the identifier and maximum bound; the internal bitmap determines whether the record is an exception/denial or purchase record.

## Context Rules

- EF and EA can appear in eWIC Balance Inquiry, Authorization, and Purchase Completion responses.
- PS exception/denial data can appear in Purchase Completion and Voucher Clear responses.
- Cash Value Benefit fruit and vegetable quantity is represented according to the WIC Purchase Information rule.
- A price above the state Approved Product List can produce an exception and adjusted approved/settlement amounts.

## TBA Artifact Pattern

```text
BR: WIC Product Data begins with Total Length and contains a documented subelement.
TS: Return EF, EA, and PS records in the applicable eWIC response.
TC: Reject unknown tags, invalid EF length, and Total Length mismatch.
TD: EF20270101, EA..., PS... with expected PASS/FAIL outcomes.
```

## Validator Boundary

`Segment103PayloadValidator` validates Total Length, aggregate bounds, EF/EA/PS identifiers, EF length, and granular data bounds. Internal PS bit-map semantics require a separate converter-level test when raw wire samples are available.
