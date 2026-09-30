# Segment 108 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 108 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG108-R-003 Segment Type is 108}
    R1 -->|Fail| X1[Reject citing SEG108-R-003]
    R1 -->|Pass| R2{SEG108-R-004 Segment Length is 3 digits representing the segment content length}
    R2 -->|Fail| X2[Reject citing SEG108-R-004]
    R2 -->|Pass| R3{SEG108-R-008 Loyalty Program ID, required, is numeric with maximum length 6}
    R3 -->|Fail| X3[Reject citing SEG108-R-008]
    R3 -->|Pass| R4{SEG108-R-009 Loyalty Account Number, when populated, is numeric with maximum length 24}
    R4 -->|Fail| X4[Reject citing SEG108-R-009]
    R4 -->|Pass| R5{SEG108-R-010 Points to Redeem, when populated, is numeric with maximum length 6}
    R5 -->|Fail| X5[Reject citing SEG108-R-010]
    R5 -->|Pass| R6{SEG108-R-011 Coupon ID, when populated, is numeric with maximum length 19}
    R6 -->|Fail| X6[Reject citing SEG108-R-011]
    R6 -->|Pass| R7{SEG108-R-012 Coupon Amount, when populated, is numeric with maximum length 8}
    R7 -->|Fail| X7[Reject citing SEG108-R-012]
    R7 -->|Pass| R8{SEG108-R-013 Update Code, when populated, is exactly one alphanumeric character from the documented set A, C, E, I, P, S, T, U}
    R8 -->|Fail| X8[Reject citing SEG108-R-013]
    R8 -.->|Provisional| P8[REVIEW_REQUIRED]
    R8 -->|Pass| R9{SEG108-R-014 Street Address, when populated, is numeric with maximum length 5}
    R9 -->|Fail| X9[Reject citing SEG108-R-014]
    R9 -->|Pass| R10{SEG108-R-015 Phone Number, Loyalty, when populated, is numeric with maximum length 10}
    R10 -->|Fail| X10[Reject citing SEG108-R-015]
    R10 -->|Pass| R11{SEG108-R-016 Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card}
    R11 -->|Fail| X11[Reject citing SEG108-R-016]
    R11 -->|Pass| R12{SEG108-R-017 Payment Tender Type is required, exactly 2 alphanumeric characters from the documented set AX, CK, CS, DB, DN, DS, EB, EC, FL, GC, JC, MC, PC, PR, VS}
    R12 -->|Fail| X12[Reject citing SEG108-R-017]
    R12 -->|Pass| Z[Rules satisfied]
    Z --> M[3 further rules - see catalog]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-108-rule-catalog.json](coverage/segment-108-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
