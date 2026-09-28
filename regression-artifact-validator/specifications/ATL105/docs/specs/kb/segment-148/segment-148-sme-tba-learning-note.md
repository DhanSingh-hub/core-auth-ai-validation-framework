# Segment 148 WEX Available Product Fleet Information Data Segment: SME and TBA Learning Note

Verified against Section 12.33, Element 240.

Sends WEX fuel-restriction information to the terminal in a WEX Financial Transaction response. Available Product Information is a fixed 17-character sub-structure: Restriction Code (2-3 AN, fuel product group: 00 none, 01 gasoline, 06 biodiesel, 07 diesel, 08 off-road diesel, 10 alternative fuels, 11 hybrid, 12 aviation, 13 marine) `=` Amount `,` Quantity `<space>` Unit of Measure.

`[PROVISIONAL SEG148-SME-001]` — three length figures conflict: 23 (opening statement), 001-999 (valid-values range), 17 (Element 240 itself).

Source: Section 12.33, lines 15932-16066. [Rule Catalog](coverage/segment-148-rule-catalog.json).
