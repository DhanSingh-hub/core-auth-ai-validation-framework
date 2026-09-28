# Segment 149 Fuel Price Update Request Segment: SME and TBA Learning Note

Verified against Section 12.34, Elements 84, 85, 102, 241.

Carries Comdata fuel price update data. Field Separators between fields 1-2, 2-3, 3-4; trailing separator; Price Data's internal tag:value pairs are NOT separator-delimited (pipe-delimited instead). Worked example: `"ATL105<FS>01<FS>149<FS>045<FS>1D13500101001<FS>|CASS:03.00|CRSS:3.05|<FS>"`.

Source: Section 12.34, lines 16066-16122. [Rule Catalog](coverage/segment-149-rule-catalog.json).
