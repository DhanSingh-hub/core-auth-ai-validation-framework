# Segment 132 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 132 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG132-R-002 Segment Type is fixed value 132, sourced at the Device}
    R1 -->|Fail| X1[Reject citing SEG132-R-002]
    R1 -->|Pass| R2{SEG132-R-003 Segment Length is 3 digits (not 4 — Segment 132 is NOT one of the seven 4-digit-length segments)}
    R2 -->|Fail| X2[Reject citing SEG132-R-003]
    R2 -->|Pass| R3{SEG132-R-005 Sequence Number (Element 86) is required, 6 digits; a limited range (100000-199999) applies ONLY when using the Multithreaded Dial Protocol Communications header for a CAPK File Load}
    R3 -->|Fail| X3[Reject citing SEG132-R-005]
    R3 -->|Pass| R4{SEG132-R-006 Terminal Identifier (Element 102) is required, 13 characters; for EMV, the first two characters (Device Type) must be '+*' regardless of the actual device type}
    R4 -->|Fail| X4[Reject citing SEG132-R-006]
    R4 -->|Pass| R5{SEG132-R-007 Load Type (Element 48) is required, fixed value 'K' (indicates Public Key information is requested)}
    R5 -->|Fail| X5[Reject citing SEG132-R-007]
    R5 -->|Pass| R6{SEG132-R-008 Hardware Version (Element 43), Software Version (Element 96), and Firmware Version (Element 39) are all required device-version identifiers}
    R6 -->|Fail| X6[Reject citing SEG132-R-008]
    R6 -->|Pass| R7{SEG132-R-009 CA Public Key File Checksum (Element 187) is required in Segment 132, identifying the checksum currently in use — a THIRD segment (after 130 request, 131 response) referencing this same element}
    R7 -->|Fail| X7[Reject citing SEG132-R-009]
    R7 -->|Pass| R8{SEG132-R-010 Block Number (Element 11) is required, 3 digits, identifying the specific data block requested by a device or sent by the host — implying a multi-block CA key file transfer protocol not fully detailed in this section}
    R8 -->|Fail| X8[Reject citing SEG132-R-010]
    R8 -.->|Provisional| P8[REVIEW_REQUIRED]
    R8 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-132-rule-catalog.json](coverage/segment-132-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
