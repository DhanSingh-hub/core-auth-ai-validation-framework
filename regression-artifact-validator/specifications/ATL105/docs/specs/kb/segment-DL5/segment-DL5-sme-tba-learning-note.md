# Segment DL5 Software IP Load Data Segment: SME and TBA Learning Note

Verified against Section 12.46, Elements 24, 34, 57, 92, 93, 94, 95, 114.

## What Segment DL5 Means

Schedules a full software load via IP/URL download from a BUYPASS-supported device management system — the IP-based counterpart to Segment DL4's dial-based delivery. Vendors who support their own applications do NOT use this segment. Self-delimited by Data Type Indicator `$` and End-of-Data Indicator `~`.

## Field Layout

Identical to Segment DL4 except field 4 is Software Load IP/URL Address(114,30) instead of Software Load Phone Number(91,18). All other fields (New Software Version, Software Terminal Record ID, Request Date/Time, Load Type, markers) match DL4 exactly.

## Source References

Section 12.46: lines 17447-17532. [Rule Catalog](coverage/segment-DL5-rule-catalog.json).
