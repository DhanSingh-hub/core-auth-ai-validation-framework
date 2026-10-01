# Segment 153 Network Token Data Request Segment: SME and TBA Learning Note

Verified against Section 12.38, Element 239.

TLV-of-TLVs container (mirrors Segment 145/146's structure) with 6 fixed-format sub-tables: 001 Network Token (013-018 bytes), 002 Expiration Date (fixed 4), 003 Provisional Fee Indicator (fixed 1), 004 Input Indicator (fixed 1), 005 Eligible Indicator (fixed 1), 006 PAN Indicator (fixed 1). Element 239 is shared with Segments 145/146 — same cross-segment element-reuse pattern as Element 118.

Source: Section 12.38, lines 16307-16426. [Rule Catalog](coverage/segment-153-rule-catalog.json).
