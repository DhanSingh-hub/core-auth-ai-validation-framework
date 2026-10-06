# Segment 145 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## The TLV-of-TLVs Structure

Segment 145's Enhanced Fleet Data field is itself a container of independently-formatted `<tag><len><data>` sub-segments, each of which (Table 002, 004) further uses `|`-delimited repeating sub-structures internally. This is a two-level TLV nesting, distinct from Segment 130/131's single-level EMV Additional Information repetition.

## What Not To Assume

- Do not assume sub-segment tables appear in a fixed order — they are identified by their own Table ID tags.
- Do not assume all 5 authorizer prompt-token catalogs share the same tokens — Voyager EMV, Visa Fleet 2.0, Comdata, WEX OTR, and Conexxus each define independent token sets.
