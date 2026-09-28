# Segment DL1 Merchant Data Segment: SME and TBA Learning Note

Verified against Section 12.42, Elements 3, 4, 14, 24, 34, 53, 54, 59, 98.

## What Segment DL1 Means

Carries merchant identity (name, store number, addresses, phone) and accepted card types in a Table Load Response. Uses the DL-family's self-delimiting convention: Data Type Indicator `#` opens the segment, End-of-Data Indicator `~` closes it — **no Field Separators, no Segment Type/Length pair**.

## Field Layout

Data Type Indicator(24, fixed `#`), Merchant Name(53,24), Store Number(98,16), Address Line 1(3,24), Address Line 2(4,21), Merchant Phone Number(54,13), Number of Card Types(59,2), Card Type(14,3, repeats 01-99 times), End-of-Data Indicator(34, fixed `~`).

## The DL1 → DL6 Trigger

If any repeated Card Type value equals `173`, Segment DL6 (Store and Forward) must also be included in the same Table Load Response (`SEGDL1-R-005`, confirmed by Segment DL6's own applicability note).

## Source References

Section 12.42: lines 17101-17208. [Rule Catalog](coverage/segment-DL1-rule-catalog.json).
