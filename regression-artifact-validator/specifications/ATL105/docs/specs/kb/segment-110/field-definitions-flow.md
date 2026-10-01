# Segment 110 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 110 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG110-R-004 Segment Type (Element 85) is fixed value 110}
    R1 -->|Fail| X1[Reject citing SEG110-R-004]
    R1 -->|Pass| R2{SEG110-R-005 Segment Length (Element 84) is required and identifies the segment's serialized length including Segment Type and Field Separators}
    R2 -->|Fail| X2[Reject citing SEG110-R-005]
    R2 -->|Pass| R3{SEG110-R-008 MICR Data (Element 122) is required, alphanumeric, maximum 50 bytes, and required on all check transactions; when raw MICR data exceeds 50 bytes, only the first 50 bytes (left to right) are placed here and the remainder is expected in Extended MICR Data (Element 137), whose hosting data segment is not confirmed by the extracted Segment 110 table}
    R3 -->|Fail| X3[Reject citing SEG110-R-008]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| R4{SEG110-R-009 Driver's License (Element 123) is conditional, alphanumeric, maximum 40 bytes, and required on all manually entered check transactions; the machine-testable trigger that distinguishes a manually entered check transaction from a MICR-read one is not defined in the extracted Segment 110 table}
    R4 -->|Fail| X4[Reject citing SEG110-R-009]
    R4 -.->|Provisional| P4[REVIEW_REQUIRED]
    R4 -->|Pass| R5{SEG110-R-011 Date of Birth (Element 125) is conditional, numeric, fixed 8 digits (MMDDYYYY); the extracted Segment 110 table marks it conditional but states no explicit trigger separate from the Driver's License/State Code identification bundle}
    R5 -->|Fail| X5[Reject citing SEG110-R-011]
    R5 -.->|Provisional| P5[REVIEW_REQUIRED]
    R5 -->|Pass| R6{SEG110-R-012 Check Type (Element 126) is required, alphanumeric, fixed 1 character, and must be one of the documented codes: P (Personal) or C (Company)}
    R6 -->|Fail| X6[Reject citing SEG110-R-012]
    R6 -->|Pass| R7{SEG110-R-013 Check Number (Element 127) is conditional, alphanumeric, maximum 8 bytes, and required for manually keyed check data; it shares the same undefined manually-entered trigger as Driver's License}
    R7 -->|Fail| X7[Reject citing SEG110-R-013]
    R7 -.->|Provisional| P7[REVIEW_REQUIRED]
    R7 -->|Pass| R8{SEG110-R-014 Customer Phone Number (Element 128) is optional, numeric, maximum 10 digits}
    R8 -->|Fail| X8[Reject citing SEG110-R-014]
    R8 -->|Pass| R9{SEG110-R-015 Customer Last Name (Element 129) is optional, alphanumeric, maximum 24 bytes}
    R9 -->|Fail| X9[Reject citing SEG110-R-015]
    R9 -->|Pass| R10{SEG110-R-016 Check Issue Date (Element 130) is optional, numeric, fixed 8 digits (MMDDYYYY)}
    R10 -->|Fail| X10[Reject citing SEG110-R-016]
    R10 -->|Pass| R11{SEG110-R-017 Alternate MICR IND, documented in Section 12.9 as Segment 110 field 12 using element number 239, is optional, fixed 1 byte, and its only documented value is 'Y' (Alternate/RAW TOAD MICR format is being sent); this element number is separately defined in the Chapter 13 master element catalog as 'Enhanced Fleet Data' (999 bytes, used by Segment 145). Per SME decision (2026-09-26), both usages are modeled as distinct, segment-scoped entities (element 239 @ Segment 110 vs. element 239 @ Segment 145); this rule intentionally remains REVIEW_REQUIRED by design, not due to a gap}
    R11 -->|Fail| X11[Reject citing SEG110-R-017]
    R11 -.->|Provisional| P11[REVIEW_REQUIRED]
    R11 -->|Pass| R12{SEG110-R-018 Two MICR encodings are documented in narrative text: Full MICR Line TAC format (example T999999999A999999999999999999C999999) and Full MICR Line RAW TOAD format (symbol substitution using T, O, A, D); Alternate MICR IND = 'Y' signals RAW TOAD/ALB1 format, but no machine-checkable grammar beyond the example and the 50-byte length limit is given}
    R12 -->|Fail| X12[Reject citing SEG110-R-018]
    R12 -.->|Provisional| P12[REVIEW_REQUIRED]
    R12 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-110-rule-catalog.json](coverage/segment-110-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
