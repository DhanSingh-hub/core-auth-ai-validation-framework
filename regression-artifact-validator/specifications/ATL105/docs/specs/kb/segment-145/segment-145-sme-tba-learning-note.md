# Segment 145 Enhanced Fleet Request Segment: SME and TBA Learning Note

Verified against Section 12.31, Element 239, and its embedded sub-segment tables.

## 1. What Segment 145 Means

Segment 145 carries enhanced fleet data for Wex OTR, Comdata, Voyager EMV, Visa Fleet 2.0, and MasterCard Enhanced Fleet EMV transactions — a more sophisticated successor to the plain Fleet Data Segment (101). Its single data field (Enhanced Fleet Data, Element 239) is itself a container for multiple TLV-encoded sub-segments.

## 2. Mutual Exclusion with Segment 101

Merchants should NOT send Segment 101 (Fleet) and Segment 145 (Enhanced Fleet) together (`SEG145-R-002`) — they represent two generations of fleet-card support and are not meant to be combined.

## 3. The Prompt-Table-Only Restriction

For **Voyager EMV, Visa Fleet 2.0, Comdata, and MasterCard Enhanced Fleet EMV** transactions, only the Prompt Table (Table ID 004) sub-segment is valid. `[PROVISIONAL SEG145-SME-001]` — the specification does not explicitly cross-reference which authorizer(s) DO use the other tables (001, 002, 006, 007, 008); WEX OTR is implied by context but not explicitly confirmed.

## 4. MasterCard Enhanced Fleet EMV Availability Notice

As of the specification's authoring, this functionality was "expected to be enabled in First Data production no earlier than June 2026" — until then, for development/certification only, not live production. `[PROVISIONAL SEG145-SME-002]` — confirm current status as of the training date (2026-09-26).

## 5. Sub-Segment Table Catalog (Request Side)

| Table ID | Name | Notes |
| --- | --- | --- |
| 001 | Request Flags | Comma-separated flags; Flag 1 = Commercial/Retail |
| 002 | Non-Fuel Product Data | `\|`-delimited repeating; WEX OTR product categories documented (ADD, ANFR, ... WWFL) |
| 004 | Prompt Data | `\|`-delimited repeating; authorizer-specific prompt tokens (Voyager EMV DF-tags, Visa Fleet 2.0, Comdata, WEX OTR, Conexxus numeric codes) |
| 006 | Money Code Payee Name | Optional for Money Code transactions |
| 007 | Money Code Check Number | Required for all Money Code transactions |
| 008 | Cash Advance Limit | Maximum dollar amount for cash advance |

`[PROVISIONAL SEG145-SME-003]` — Table IDs 003 and 005 are absent here but present in Segment 146's response-side catalog (Fuel Product Limits, Customer Information) — confirm this asymmetry is intentional.

`[PROVISIONAL SEG145-SME-004]` — the full per-authorizer prompt-token enumeration (dozens of tokens across 5 authorizer tables) is documented in Section 12.31 but not individually transcribed into machine-readable rules here; scope confirmation needed for Item 1.

## Source References

Section 12.31: lines 15191-15648. [Rule Catalog](coverage/segment-145-rule-catalog.json).
