# Segment 156 EV Charging Data Segment: SME and TBA Learning Note

Verified against Section 12.40, Element 242.

Visa-only, sent only for an EV charging transaction (Segment 102 EV fuel code). Fixed-length, no separators. EV Transaction Indicator (Table 01) MUST be "Y" — its absence causes a BUYPASS decline. 4 time-format tables (02-05) use hhmmss with hh 00-99 (supports multi-day elapsed durations, not just clock time). Charging Reason Code (07) and Connector Type (12) use documented Visa-specific enumerations. Several numeric measurement tables (06, 08-11) report kW, distance, and carbon footprint.

Source: Section 12.40, lines 16635-16904. [Rule Catalog](coverage/segment-156-rule-catalog.json).
