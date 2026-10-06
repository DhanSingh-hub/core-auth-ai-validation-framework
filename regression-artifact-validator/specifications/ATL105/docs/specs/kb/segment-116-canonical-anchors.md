# Segment 116 Canonical Source Anchors

The training and validation artifacts for Segment 116 use these canonical anchors. They are source references, not inferred operational policy. Where a fact is pattern-derived or analogized rather than directly shown, this is stated explicitly.

| Anchor | Source evidence | Scope |
|---|---|---|
| `ATL105|2026-3|12.15|Segment116|stub|no-field-table` | Page 12-36 | Section 12.15 contains only a redirect note to the external TransArmor document; no field table is present. |
| `ATL105|2026-3|11.7.5|Segment116|stub|no-request-response-table` | Page 11-36 | Section 11.7.5 contains only the same redirect note; no request/response layout is present. |
| `ATL105|2026-3|Chapter13-Element85|Segment116|segment-type` | Chapter 13, Element 85 valid-codes table | Confirms Segment 116 = "TransArmor Load Data Segment"; Segment Type fixed `116`, Required for the TransArmor PKI Encryption and Tokenization Load Request. |
| `ATL105|2026-3|Chapter13-Element63|Segment116|placement` | Chapter 13, Element 63 processing rule | Confirms Data Section 1 (55, 63) is always followed by Data Section 2 containing Segment 116, for TransArmor PKI Encryption and Tokenization Key and Key ID Load. |
| `ATL105|2026-3|Chapter13-Element84-lengths|Segment116|max-length` | Chapter 13, Element 84 length table | Segment length range 01-50 alphanumeric characters. |
| `ATL105|2026-3|Chapter13-Element155|Segment116|key-id` | Chapter 13, Element 155 | Key ID: fixed 11 alphanumeric bytes, required in the TransArmor Load Response, reused for all subsequent transactions once approved. |
| `ATL105|2026-3|Chapter13-Element156|Segment116|key-data-length` | Chapter 13, Element 156 | Key Data Length: numeric, max 3 bytes, valid values 000-999, required in the TransArmor Load Response. |
| `ATL105|2026-3|Chapter13-Element157|Segment116|key-data` | Chapter 13, Element 157 | Key Data: alphanumeric, max 999 bytes, required in the TransArmor Load Response; carries new key/Key ID on approval or an error message on decline. |
| `ATL105|2026-3|AppendixI-53_54|Segment111|table-052|additional-transarmor-data` | Appendix I-53 to I-54 | Segment 111 Variable Information Indicator/Table ID 052, "Additional TransArmor Data," with Sub-Table ID 01 (KSN) and Sub-Table ID 02 (Device Type); a companion of the TransArmor flow, not a Segment 116 field. |
| `ATL105|2026-3|11.4.1.2-vs-Chapter13-Element85-vs-12.17|Segment116|source-conflict-resolved` | Section 11.4.1.2, Chapter 13 Element 85, Section 12.17 | Section 11.4.1.2 mislabels "Totals with Proprietary Data Load Request" as "(Data Segment No. 116)"; the Element 85 valid-codes table and Section 12.17 both confirm that segment is actually Data Segment No. 119. Resolved in favor of the master valid-codes table. |

## Boundary

An anchor confirms only the behavior stated by its source. Segment 116's own remaining field layout, its exact request/response message tables, and the relationship between the Segment 111 companion table and the Segment 116 flow remain `REVIEW_REQUIRED` until the external TransArmor document is supplied and the SME/TBA input register records an approved decision.
