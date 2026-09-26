# Segment DL2 Dial String Data Segment: SME and TBA Learning Note

Verified against Section 12.43, Elements 1, 24, 27, 28, 66, 75, 82, 34.

## What Segment DL2 Means

Carries transaction dial-string configuration: a primary and secondary phone number, each with an optional access code and pause indicator, terminated by distinct fixed terminator characters (`A` for primary, `F` for secondary). Self-delimited by Data Type Indicator `!` and End-of-Data Indicator `~`.

## Field Layout

Data Type Indicator(24, fixed `!`), Dial String Type(28, fixed `1`), Primary block — Redial Count(82,1), Access Code(1,12,conditional), Pause Indicator(66,1,conditional,fixed `B`), Phone Number(75,18), Dial String Terminator(27, fixed `A`) — Secondary block mirrors Primary, terminated by Dial String Terminator(27, fixed `F`), End-of-Data Indicator(34, fixed `~`).

## Fallback Logic (Provisional)

The secondary phone number is used only after all primary-number retry attempts are exhausted, per the Asynchronous Communications Protocol Specifications (external document, not yet in scope — `SEGDL2-SME-001`).

## Source References

Section 12.43: lines 17208-17302. [Rule Catalog](coverage/segment-DL2-rule-catalog.json).
