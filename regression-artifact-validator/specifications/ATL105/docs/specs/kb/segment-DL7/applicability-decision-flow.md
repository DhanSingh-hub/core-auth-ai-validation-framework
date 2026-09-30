# Segment DL7 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host has supplemental terminal data] --> B{Which message carries DL7?}
    B -->|Not stated in 11.7 layouts or Chapter 12 matrix| R1[REVIEW_REQUIRED - SEGDL7-SME-004]
    R1 --> C{Test as isolated segment}
    C --> D["Validate '^' + Segment Length + Download Data only"]
    B -->|Request message| X1[Fail - DL7 is download data from the host]
```

| Evidence | What it says about placement |
|---|---|
| 12.48 | Layout only; no message named |
| Element 24 | `^` identifies DL7 in "a host response" |
| 11.7.1.2 Table Load Response | Lists DL1, DL2, DL3, DL6 only |
| Chapter 12 matrix | Lists DL1-DL6 only |

Source: [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
