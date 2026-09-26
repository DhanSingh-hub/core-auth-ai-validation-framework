# Segment 110 Canonical Source Anchors

The training and validation artifacts for Segment 110 use these canonical anchors. They are source references, not inferred operational policy.

| Anchor | Source evidence | Scope |
|---|---|---|
| `ATL105|2026-3|11.1.1|FinancialTransactionRequest|DataSection3|segmentList` | Pages 11-3 to 11-4 | Generic Financial Transaction Request Data Section 3 lists Segments 101, 102, 103, 104, 111 only; Segment 110 is absent. |
| `ATL105|2026-3|11.3.1|ECATeleCheckRequest|DataSection3|field4` | Pages 11-13 to 11-14 | Segment 110 is Field 4, Required, alongside Segment 111 (Field 5) and Segment 113 (Field 6). |
| `ATL105|2026-3|11.3.2|ECATeleCheckResponse|layout` | Page 11-15 | Response reuses the general Financial Transaction Response layout (Section 11.1.2). |
| `ATL105|2026-3|12.9|Segment110|segment-type` | Page 12-27 | Element 85 is fixed `110`. |
| `ATL105|2026-3|12.9|Segment110|max-length` | Page 12-25 | Segment length is 001-168 alphanumeric characters. |
| `ATL105|2026-3|12.9|Segment110|field-order` | Pages 12-27 to 12-28 | Twelve ordered fields. |
| `ATL105|2026-3|12.9|Segment110|empty-separators` | Page 12-25 | Empty fields retain Field Separators. |
| `ATL105|2026-3|12.9|Segment110|element-122|micr-data` | Page 12-27 | MICR Data required, alphanumeric, max 50 bytes; first 50 bytes retained on overflow. |
| `ATL105|2026-3|12.9|Segment110|element-126|check-type` | Page 12-27, Chapter 13 | Check Type required, 1 byte; valid values `P` Personal, `C` Company. |
| `ATL105|2026-3|Appendix D|Segment110|element-124|state-code` | Appendix D-1 to D-4 | 76 alphabetical State Codes: US states, DC, territories, military designations, Canadian provinces, and `NA`. |
| `ATL105|2026-3|12.9|Segment110|element-239|alternate-micr-indicator` | Pages 12-26 to 12-27 | Alternate MICR IND, 1 byte, optional, valid value `Y`; introduced because element 131 was already in use. |
| `ATL105|2026-3|Chapter13|element-239|enhanced-fleet-data` | Page (Chapter 13 element catalog) | Same element number independently defined as Enhanced Fleet Data, 999 bytes, used in Segment 145 - a documented source conflict with the Segment 110 usage above. |
| `ATL105|2026-3|Chapter13|element-137|extended-micr-data` | Chapter 13 element catalog | Extended MICR Data, 65 bytes, must be populated in addition to Element 122 in Segment 110 when raw MICR data exceeds its length; hosting data segment not confirmed. |
| `ATL105|2026-3|AppendixI-17|Segment111|variable-information-024|manual-check-micr-type` | Appendix I-17 | Manual Check MICR Type sub-table (Variable Information Indicator `024`) in Segment 111 documents four MICR format codes (`T$`, `18`, `09`, `19`); independently confirmed as a Segment 111 companion table, not a Segment 110 field. |

## Boundary

An anchor confirms only the behavior stated by its source. The manually-entered/manually-keyed trigger, the Element 239 identity conflict, the Extended MICR Data hosting segment, and generic Financial Transaction Request applicability remain `REVIEW_REQUIRED` until the SME/TBA input register records an approved decision.
