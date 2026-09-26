# Segment DL6 Store and Forward Data Segment: SME and TBA Learning Note

Verified against Section 12.47, Elements 24, 34, 166.

## What Segment DL6 Means

Defines a blocking window (Start Time / End Time, HHMM) during which store-and-forward transaction processing is disabled. Sent ONLY in a Table Load Response, and only when Segment DL1 includes a Card Type value of `173`. Self-delimited by Data Type Indicator `\` and End-of-Data Indicator `~`.

## Field Layout

Data Type Indicator(24, fixed `\`), Start Time(166,4,HHMM), End Time(166,4,HHMM), End-of-Data Indicator(34, fixed `~`).

## Notable Finding (Provisional)

Both Start Time and End Time cite Element 166 — flagged `SEGDL6-SME-001` to confirm this is intentional element-number reuse rather than a transcription error.

## Source References

Section 12.47: lines 17532-17588. [Rule Catalog](coverage/segment-DL6-rule-catalog.json).
