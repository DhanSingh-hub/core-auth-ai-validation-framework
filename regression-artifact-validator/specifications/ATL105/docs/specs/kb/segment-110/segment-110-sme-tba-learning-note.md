# Segment 110 Check Data: SME and TBA Learning Note

## Purpose

Segment 110 is the Check Data Segment. It carries MICR-read or manually keyed check identification data. It is confirmed as a required Data Section 3 field of the ECA/TeleCheck® Service Transaction Request (Section 11.3.1), traveling alongside Segment 100 (Data Section 2), Segment 111, and Segment 113 (also Data Section 3).

## Business Decision Model

| Intent | Entry method | Source-confirmed behavior | Review boundary |
|---|---|---|---|
| MICR-read check | Swipe/read | MICR Data (Element 122) required, up to 50 bytes; overflow expected in Extended MICR Data (Element 137) elsewhere. | Hosting segment for Element 137 (`SEG110-SME-002`). |
| Manually keyed personal check | Manual entry | Driver's License, State Code, Check Type = `P`, Check Number required. | Machine-testable "manually entered" trigger (`SEG110-SME-003`). |
| Manually keyed company check | Manual entry | Same as above with Check Type = `C`. | Same trigger question. |
| Alternate/RAW TOAD MICR format | Either | Alternate MICR IND = `Y` signals the alternate format. | Element 239 identity conflict (`SEG110-SME-005`). |

The source limits the segment to 168 alphanumeric characters and requires a Field Separator after every field, including empty ones.

## Required Message Shape (Confirmed Envelope)

```text
ECA/TeleCheck Service Transaction Request
  Data Section 1
    Element 55: Message Format Version Identifier
    Element 63: Number of Segments
  Data Section 2
    Segment 100: Standard Message Data Segment
  Data Section 3
    Field 4: Segment 110 (Check Data Segment)
    Field 5: Segment 111 (Variable Information Data Segment)
    Field 6: Segment 113 (ECA/TeleCheck Data Segment)
```

The response is not a Segment 110 structure. Section 11.3.2 states the ECA/TeleCheck® Service Financial Transaction Response reuses the general Financial Transaction Response layout (Section 11.1.2); no check-specific response fields are defined.

## BR to Test-Data Decomposition

```text
BR: A manually keyed check transaction shall include Driver's License, State Code, Check Type, and Check Number in Segment 110.
TS: Device submits a manually keyed personal check with a valid State Code.
TC: Validate segment placement, field order, separators, conditional presence, and the Check Type/State Code value catalogs.
TD: Sanitized converter-ready request with synthetic MICR data, driver's license, and check numbers.
```

## Training Boundaries

- Do not present a general Financial Transaction Request fixture as proof that Segment 110 may appear outside the ECA/TeleCheck® envelope.
- Do not resolve the element 239 conflict ("Alternate MICR IND" vs. "Enhanced Fleet Data") without an approved SME decision; keep both interpretations visible.
- Do not infer the "manually entered"/"manually keyed" trigger from field names; it requires a source or configuration-owner decision.
- Do not expose real MICR line data, driver's license numbers, or dates of birth in training data.
- Do not claim AI coverage from framework-generated fixtures; the supplied requirement-catalog extraction remains independent evidence under test, not certified truth.
