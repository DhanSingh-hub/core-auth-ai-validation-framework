# Segment 119 Totals Request Structure: SME/TBA Note

## Core Decision

Segment 119 is selected only for the proprietary-data-load totals request path approved by merchant/device configuration. A normal Totals Request uses the ordinary totals path; Segment 119 must not be inserted merely because totals are being requested.

## Request Structure

```text
Data Section 1
  -> Message Format Version Identifier
  -> Number of Segments = 01
Data Section 3
  -> Field 3: Segment 119
Data Section 2
  -> absent
```

## Rules

- The request has no Standard Message Data Segment 100.
- Segment 119 is the only segment in this totals-with-proprietary-load request layout.
- Segment Type is `119`.
- Prompt Code is `990`.
- Segment Length is encoded from the fields and separators.
- The device supplies the fields except the Host Discount Timestamp source described by ATL105.

## Manual Input Boundary

The exact business trigger for choosing Segment 119 is configuration/policy knowledge, not inferable from the segment layout. It is recorded as `SEG119-R-031`.
