# Segment DL7 Supplemental Terminal Data Segment: SME and TBA Learning Note

Verified against Section 12.48, Elements 24, 84, 232.

## What Segment DL7 Means

Carries supplemental terminal download data in `<tag><len><data>` TLV format. Unlike Segments DL1-DL6, this segment does NOT use an End-of-Data Indicator — instead it uses a Segment Length Indicator (matching the numbered-segment convention), making it a hybrid of the two families' framing conventions.

## Field Layout

Data Type Indicator(24, fixed `^`), Segment Length Indicator(84,3, **exclusive** of Data Type Indicator's length — Device-sourced), Download Data(232, max 100, TLV format per Appendix W).

## Notable Finding

Segment Length Indicator here excludes the Data Type Indicator's own length from its count — a distinct counting convention from most numbered segments, which include the Segment Type field's length. Not yet cross-verified against every numbered segment; treat as segment-specific until confirmed generalizable.

## Source References

Section 12.48: lines 17588-17637. [Rule Catalog](coverage/segment-DL7-rule-catalog.json). Appendix W not yet transcribed (`SEGDL7-SME-001`).
