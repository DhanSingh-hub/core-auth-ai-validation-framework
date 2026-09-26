# Variable Information Indicator (Table ID) Dependency Flow

```mermaid
flowchart TD
    A[Business condition identified] --> B{Which Appendix I Table ID applies?}
    B -->|ZIP Code| C1[Table 003]
    B -->|SIC Information| C2[Table 007]
    B -->|Point-of-Service Entry Mode| C3[Table 005]
    B -->|Soft Descriptor - Merchant Name| C4[Table 047]
    B -->|POS Additional Data Information| C5[Table 032]
    B -->|MIT/CIT category and subcategory| C6[Table 056]
    B -->|Anticipated Completion Amount| C7[Table 070]
    B -->|Enabler Verification Value| C8[Table 071]
    B -->|Digital Commerce Data| C9[Table 066]
    B -->|Additional Transaction Fee 1/2| C10[Table 068/069]
    B -->|Other approved Table ID| C11[Any remaining Appendix I value]

    C1 --> D[Set Variable Information Indicator = 3-digit Table ID]
    C2 --> D
    C3 --> D
    C4 --> D
    C5 --> D
    C6 --> D
    C7 --> D
    C8 --> D
    C9 --> D
    C10 --> D
    C11 --> D

    D --> E[Set Variable Information Length = 3-digit value length]
    E --> F[Set Variable Information = Table-ID-specific payload]
    F --> G{Envelope-only validator scope?}
    G -->|Yes| H[Validate indicator shape, length/value match,<br/>and repeated-section/total-length caps only]
    G -->|No, Appendix I content scope| I[Validate Table-ID-specific format<br/>REVIEW_REQUIRED until SME confirms]
    H --> J[Pass envelope validation]
    I --> K[Pass or fail Appendix I content validation<br/>— separate module]
```

## Key Insight

The Variable Information Indicator is a **discriminator**, not free text: its
value selects which Appendix I Table ID layout governs the accompanying value.
The current validator checks only that the indicator is a present three-digit
field (`SEG111-R-003`); it does not yet decode the Table ID to select a
Table-ID-specific value grammar. That decoding is explicitly parked as
`REVIEW_REQUIRED` (see [SME/TBA note](variable-information-indicator-sme-tba-note.md),
question 1) until the SME confirms which identifiers are in scope for the current
release.

## Grounded Table ID Examples

These Table IDs are cited directly in the approved requirement catalog
(`test-output/ai-artifacts/business-requirements/POC-AI-ATL105-Segment-111-Business-Requirements.md`)
and are used here only to illustrate the discriminator pattern, not to certify
their business rules:

| Table ID | Business meaning | Source requirement |
| --- | --- | --- |
| `003` | ZIP Code (reporting only, not validated by issuer) | BR-129-6 |
| `005` | Point-of-Service Entry Mode | BR-143-1 |
| `007` | SIC Information (MCC of underlying retailer) | BR-83-5, BR-142-3 |
| `018` | Terminal ID (TPP/VAR ID) | BR-46-3 |
| `032` | POS Additional Data Information | BR-143-2 |
| `047` | Soft Descriptor - Merchant Name | BR-142-1, BR-142-2 |
| `056` | MIT/CIT category and subcategory data | BR-142-4, BR-143-3 |
| `066` | Digital Commerce Data | BR-12-2, BR-12-3 |
| `068` / `069` | Additional Transaction Fee 1 / 2 | BR-11-1, BR-11-2 |
| `070` | Anticipated Completion Amount | BR-11-5, BR-12-5 |
| `071` | Enabler Verification Value | BR-7-1 |
