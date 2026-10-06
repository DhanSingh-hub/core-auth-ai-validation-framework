# Segment 123 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 123 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG123-R-001 Segment Type is fixed value 123 (Element 85), Device-sourced"}
    R1 -->|Fail| X1[Reject citing SEG123-R-001]
    R1 -->|Pass| R2{"SEG123-R-006 Token PAN Suffix (Element 197, 4 chars, Conditional, Issuer/Authorizer-sourced) — the last 4 digits of the cardholder PAN — is returned in the response only if supplied by the authorizer"}
    R2 -->|Fail| X2[Reject citing SEG123-R-006]
    R2 -->|Pass| R3{"SEG123-R-007 CAVV, Revised Format (Element 195, 20 chars, Optional, Device-sourced) identifies Verified By Visa data with an Authentication Tracking Number (ATN) replacing XID"}
    R3 -->|Fail| X3[Reject citing SEG123-R-007]
    R3 -->|Pass| R4{"SEG123-R-008 Cryptogram Token Data (Element 202) length is 28 or 56 bytes — Block A alone, or Block A and B combined"}
    R4 -->|Fail| X4[Reject citing SEG123-R-008]
    R4 -->|Pass| R5{"SEG123-R-009 SafeKey Data (Element 203, 58 chars) is composed of a fixed 'SK' indicator (2 bytes), AEVV (28 bytes), and AESK Transaction Identifier (28 bytes); unused 28-byte portions must be space-filled rather than omitted"}
    R5 -->|Fail| X5[Reject citing SEG123-R-009]
    R5 -->|Pass| R6{"SEG123-R-010 TAVV Cryptogram (Element 237, 28 bytes, base64 alphabet a-z/A-Z/0-9/+/=// ) may only be populated by the merchant during the transaction request, not the response"}
    R6 -->|Fail| X6[Reject citing SEG123-R-010]
    R6 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
