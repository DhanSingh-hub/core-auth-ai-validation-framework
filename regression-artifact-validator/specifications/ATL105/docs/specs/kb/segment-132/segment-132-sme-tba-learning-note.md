# Segment 132 CA Public Key File Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, Sections 11.9.1 and 12.22, Elements 11, 39, 43, 48, 84-86, 96, 102, 187.

## 1. What Segment 132 Means

Segment 132 is the **CA Public Key File Segment**, the vehicle by which a device requests (or receives blocks of) the Certificate Authority public key data needed for EMV chip transaction verification. It belongs to the dedicated **CA Public Key File Load Request** message family (Section 11.9.1), alongside optional companions 101 (Fleet), 102 (Product Code), 104 (Purchase Card), and 111 (Variable Information).

## 2. Segment 132 Layout (10 Fields)

| Field | Element | Length | SME meaning |
| --- | ---: | ---: | --- |
| Segment Type | 85 | 3 | Fixed `132` |
| Segment Length | 84 | 3 | 3-digit (not 4 — this segment is not in the 4-digit-length family) |
| Sequence Number | 86 | 6 | Merchant-assigned; range 100000-199999 restricted to Multithreaded Dial Protocol Communications header contexts |
| Terminal Identifier | 102 | 13 | Device identity; **for EMV, the first two characters (Device Type) must be `+*` regardless of the actual device type** — a notable override rule |
| Load Type | 48 | 1 | Fixed `K` (Public Key information requested) |
| Hardware Version | 43 | 4 | Device hardware level |
| Software Version | 96 | 8 | Device software application version |
| Firmware Version | 39 | 8 | Device EPROM level |
| CA Public Key File Checksum | 187 | 25 | Checksum currently in use — the **third** segment in this KB referencing Element 187 (after Segments 130 and 131) |
| Block Number | 11 | 3 | Identifies the specific data block requested/sent — implies a multi-block transfer protocol not fully detailed here |

Maximum length is documented as 77 characters, though the fixed-field lengths sum to 74 — a minor reconciliation gap (`[PROVISIONAL SEG132-SME-002]`).

## 3. Two Open Structural Questions

- **Field Separator behavior** for fields 4-10 is not documented with the usual summary sentence (only fields 1→2 and 2→3 show inline `<FS>` markers). Do not assume fully-delimited or non-delimited encoding (`[PROVISIONAL SEG132-SME-004]`).
- **Multi-block transfer protocol**: Block Number's description ("requested by a device or sent by the host") implies the CA key file may be split across multiple Segment 132 exchanges, but the mechanics are not detailed in Section 12.22 (`[PROVISIONAL SEG132-SME-003]`).

## 4. The EMV Terminal Identifier Override

Element 102 (Terminal Identifier) normally encodes the real Device Type in its first two characters. For EMV contexts, Segment 132 overrides this: the first two characters must always be `+*`, regardless of the device's actual type. This same override rule is referenced elsewhere (see the AI Solution Team's `BR-271-2` statement extracted during Segment 131 research) — it is a cross-cutting EMV convention, not unique to Segment 132.

## 5. Validator Rules Planned (`Segment132PayloadValidator`, not yet implemented)

- Segment Type fixed `132`; Segment Length 3-digit within `001-77` (pending `SEG132-SME-002`).
- Load Type fixed `K`.
- Terminal Identifier's first 2 characters forced to `+*` in EMV contexts.
- Applicability (Required vs Conditional in the CA Public Key File Load Request) pending `SEG132-SME-001`.

## Source References

- Section 11.9.1, CA Public Key File Load Request: lines 10460-10490.
- Section 12.22, CA Public Key File Segment: lines 14234-14350.
- [Segment 132 Rule Catalog](coverage/segment-132-rule-catalog.json).
- [SME/TBA Input Register](segment-132-sme-tba-input-register.md).
