# Segment DL4 Software Dial Load Data Segment: SME and TBA Learning Note

Verified against Section 12.45, Elements 24, 34, 57, 91, 92, 93, 94, 95.

## What Segment DL4 Means

Schedules a full software load via dial-up download from a BUYPASS-supported device management system. Vendors who support their own applications do NOT use this segment. Self-delimited by Data Type Indicator `@` and End-of-Data Indicator `~`.

## Field Layout

Data Type Indicator(24, fixed `@`), New Software Version(57,8), Software Terminal Record ID(95,13), Software Load Phone Number(91,18), Software Load Request Date(92,6), Software Load Request Time(93,4), Software Load Type(94,1), End-of-Data Indicator(34, fixed `~`).

## Applicability Boundary

This segment applies only to devices under a BUYPASS-supported device management system; a device using vendor-managed software updates never receives this segment — flagged as an applicability rule (`SEGDL4-R-001`) rather than a field-level rule.

## Source References

Section 12.45: lines 17363-17447. [Rule Catalog](coverage/segment-DL4-rule-catalog.json).
