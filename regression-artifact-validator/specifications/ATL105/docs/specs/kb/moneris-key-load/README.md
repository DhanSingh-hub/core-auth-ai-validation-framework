# Moneris Key Load Message Templates

**Specification:** BUYPASS Platform ATL105 Message Format Specifications, Release 2026-3
**Source:** Section 11.7.8 (pages 11-39 to 11-40), Appendix V (pages V-1 to V-4)
**Catalog entries:** `Moneris Key Load Request` and `Moneris Key Load Response` in [atl105_complete_templates.json](../../../atl105_complete_templates.json)
**Review status:** Source-derived layouts added; the master catalog remains `EXTRACTED` and `human_review_required`.

## Request

The device sends a positional message with no Field Separators.

| Field | Element | Name | Position | Length | Rule |
|---:|---:|---|---:|---:|---|
| 1 | 44 | Information Byte | 1 | 1 | Fixed `?` |
| 2 | 102 | Terminal Identifier | 2 | 13 | Device identifier |
| 3 | 48 | Load Type | 15 | 1 | Fixed `K` |
| 4 | 205 | Load Subtype | 16 | 1 | Fixed `M` (Moneris) |
| 5 | 206 | SPDH Header | 17 | 48 | Appendix V Table ID 001 |
| 6 | 207 | Moneris Terminal Identifier | 65 | 8 | Terminal ID from Moneris initialization |
| 7 | 208 | Moneris Merchant ID | 73 | 13 | Merchant ID from Moneris initialization |

## Response

The host returns a positional message with no Field Separators.

| Field | Element | Name | Position | Length | Rule |
|---:|---:|---|---:|---:|---|
| 1 | 24 | Data Type Indicator | 1 | 1 | Fixed `K` |
| 2 | 206 | SPDH Header | 2 | 48 | Appendix V Table ID 001 |
| 3 | 207 | Moneris Terminal Identifier | 50 | 8 | Host-provided |
| 4 | 208 | Moneris Merchant ID | 58 | 13 | Host-provided |
| 5 | 209 | Moneris Response Code | 71 | 2 | Moneris result |
| 6 | 210 | MAC | 73 | 16 | Validated by terminal |
| 7 | 211 | Key Indicators | 89 | 3 | Controls which keys follow |
| 8 | 212 | Moneris Key Data | 92 | 16 per key | Zero to three keys; maximum 48 bytes |

The key-data repetition is conditional on the three key indicators for MAC, Data Encryption, and PIN Encryption keys. Key values, MAC data, SPDH transaction values, and production terminal identifiers are sensitive: use approved synthetic values only and do not create fixtures containing real key material.

## SPDH Header

Appendix V defines the 48-byte SPDH Header as these contiguous subfields for both request and response:

| Subfield | Position | Length | Format |
|---|---:|---:|---|
| Device Type | 1 | 2 | A |
| Transmission Number | 3 | 2 | N |
| Moneris Terminal ID | 5 | 8 | A |
| Filler | 13 | 8 | A |
| Clerk ID | 21 | 6 | A |
| Local Date/Time | 27 | 12 | A |
| Message Type | 39 | 1 | A |
| Message Subtype | 40 | 1 | A |
| Transaction Code | 41 | 2 | N |
| Processing Flags | 43 | 3 | N |
| Base24 Response Code | 46 | 3 | N |

The 11 subfields total 48 bytes. The message templates are layout references, not evidence of AI-generated BR, TS, TC, or test data coverage.