# Segment 157 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 157 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG157-R-003 Segment 157 does not allow any product codes above '899' except for '955' (Cash Back); if any other code above 899 is present, the transaction will be declined"}
    R1 -->|Fail| X1[Reject citing SEG157-R-003]
    R1 -->|Pass| R2{"SEG157-R-005 The total of Adjusted Product Amounts in the segment must equal the total of Element 41 (Fuel Purchase Amount) + Element 58 (Nonfuel Amount) + Element 99 (Tax Amount) + Element 17 (Cash Amount) in Segment 100"}
    R2 -->|Fail| X2[Reject citing SEG157-R-005]
    R2 -->|Pass| R3{SEG157-R-006 Tax, discount, and coupon amounts are already accounted for in the individual Adjusted Product Amounts; separate tax/discount/coupon product codes must NOT be included in the segment}
    R3 -->|Fail| X3[Reject citing SEG157-R-006]
    R3 -->|Pass| R4{"SEG157-R-007 Multi-fuel support: BUYPASS can accept transactions with multiple Fuel Type Codes in one transaction (e.g., Diesel + DEF + Reefer); the primary fuel must be the very first product code in the segment"}
    R4 -->|Fail| X4[Reject citing SEG157-R-007]
    R4 -->|Pass| R5{"SEG157-R-009 Segment Type fixed 157, Segment Length 3 digits, Service Level (Element 87), Number of Products (Element 62, 2 digits), then per-product: Product Code (77, max 3 including 899-cap/955-exception), Unit of Measure (106), Quantity (81, 9 digits, must encode assumed decimal places as whole digits, e.g. 0.05 with 2 decimals = '205'), Unit Price (107, 9 digits, same decimal-encoding rule), Product Amount (76, 12 digits)"}
    R5 -->|Fail| X5[Reject citing SEG157-R-009]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-157-rule-catalog.json](coverage/segment-157-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
