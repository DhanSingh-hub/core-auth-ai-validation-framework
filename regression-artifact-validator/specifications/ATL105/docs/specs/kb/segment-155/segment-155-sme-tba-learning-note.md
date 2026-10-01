# Segment 155 Real Time Account Updater Response Data Segment: SME and TBA Learning Note

Verified against Section 12.39.

Sent ONLY when the transaction qualifies for and uses First Data Auth Optimizer service. Fixed-length, no separators (same pattern as Segments 131/134). 6 sub-tables gated by Segment 111's Account Updater Request Indicator (Table 060/01) — most applicable only to Visa/MasterCard. Card Status (004): A/E/Q/C/U. Result Code (006, Visa only): VAU001-VAU014,VAU016 (`[PROVISIONAL SEG155-SME-001]` — VAU015 is absent from the documented list).

Source: Section 12.39, lines 16426-16635. [Rule Catalog](coverage/segment-155-rule-catalog.json).
