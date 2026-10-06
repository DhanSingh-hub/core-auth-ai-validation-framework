# Segment 156 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 156 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG156-R-003 EV Transaction Indicator (Table 01) is mandatory for an EV transaction and must be 'Y'; if the EV segment is sent without this indicator set to 'Y', BUYPASS will decline the transaction"}
    R1 -->|Fail| X1[Reject citing SEG156-R-003]
    R1 -->|Pass| R2{"SEG156-R-004 Time-based sub-tables (02 Total Time Plugged In, 03 Total Charging Time, 04 Start Time of Charge, 05 Finish Time of Charge) all use fixed 6-character hhmmss format, hh 00-99, mm/ss 00-59"}
    R2 -->|Fail| X2[Reject citing SEG156-R-004]
    R2 -->|Pass| R3{"SEG156-R-005 Charging Reason Code (Table 07) uses a documented Visa-specific enumeration (010-023, 100, 200); other card networks' valid values are not documented here"}
    R3 -->|Fail| X3[Reject citing SEG156-R-005]
    R3 -->|Pass| R4{"SEG156-R-006 Connector Type (Table 12) uses a documented Visa-defined enumeration (001-003, 100-103, 200)"}
    R4 -->|Fail| X4[Reject citing SEG156-R-006]
    R4 -->|Pass| R5{"SEG156-R-007 Additional numeric measurement sub-tables exist: 06 Charging Power Output Capacity (kW), 08 Estimated KM/Miles Added, 09 Carbon Footprint (CO2e grams), 10 Estimated Vehicle KM/Miles Available, 11 Maximum Power Dispensed — all variable-length numeric fields with documented max lengths"}
    R5 -->|Fail| X5[Reject citing SEG156-R-007]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-156-rule-catalog.json](coverage/segment-156-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
