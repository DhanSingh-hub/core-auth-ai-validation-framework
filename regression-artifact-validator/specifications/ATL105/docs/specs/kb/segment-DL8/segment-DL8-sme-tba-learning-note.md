# Segment DL8 EMV Terminal Floor Limits Data Segment: SME and TBA Learning Note

Verified against Section 12.49, Elements 24, 84, 233, 234, 235, 236.

## What Segment DL8 Means

Carries EMV floor limits per RID (EMV Application Identifier) for the terminal. Inclusion in a table load depends on a terminal-level "Special" flag; data is maintained at the BUYPASS Host, and any change to it sets the table download flag for all terminals with that Special flag set. Shares Segment DL7's hybrid framing convention: Data Type Indicator `%` plus Segment Length Indicator, no End-of-Data Indicator.

## Field Layout

Data Type Indicator(24, fixed `%`), Segment Length Indicator(84,3, exclusive of Data Type Indicator's length), then a repeating Floor Limit Data group (max 24 RIDs, total variable length up to 624 bytes): RID(233,10, Host), Stand-in Indicator(234,1, Host), Floor Limit(235,12, Host, maximum allowable stand-in value), BUYPASS RID Card Type(236,3, Host).

## Source References

Section 12.49: lines 17636-17650 (end of Chapter 12; Chapter 13 "Data Element Descriptions" begins after). [Rule Catalog](coverage/segment-DL8-rule-catalog.json).
