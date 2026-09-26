# Segment 101 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 101 (Fleet Data Segment) moves from business and logical test-data representation into the serialized ATL105 request consumed by the Fiserv converter or transport layer.

A JSON object claiming to represent Segment 101 can be syntactically valid and semantically plausible while the serialized ATL105 message is still invalid. Serialization is therefore a separate validation responsibility, on top of Segment 100 wire-format rules described in the [Segment 100 serialization note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md).

## Three Representations

Keep these representations separate:

```text
Fleet business intent
  -> structured test-data JSON (fleetSegment container)
  -> serialized ATL105 Segment 101
  -> TCP/IP framed payload (Data Section 3 companion of Segment 100)
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Fleet business intent | Is fleet data actually required for this transaction? | Fleet card program, Auth vs. Auth Completion |
| Logical JSON | Are Segment 101 fields represented structurally? | 13 base fields + Fleet Tag 1..5 |
| ATL105 message | Are fields encoded in the required order and delimiters? | Field separators, Segment Length, base cap 61 |
| TCP/IP payload | Is the message framed for transport? | Message length, TPDU, byte order (inherited from Segment 100) |

The logical JSON is input to the converter. It is not automatically an ATL105 wire message.

## Segment 101 Serialization Rules

Segment 101 fields have a fixed order. The converter must serialize them in that order even when some values are empty.

1. Segment Type is fixed as `101`.
2. Segment Length (Element 84) is 3 digits representing the encoded Segment 101 content — including Segment Type and Field Separators.
3. Segment Length base valid range is 001..061 alphanumeric characters (PROVISIONAL P-01 for Auth Completion carrying Fleet Tags).
4. Fields 1..13 are always in the wire message in that order; fields 14..18 (Fleet Tag 1..5) appear only in Auth Completion (0220) with Host Prompts supported.
5. Empty non-trailing fields retain their Field Separators.
6. Unneeded trailing optional fields may be omitted only as a trailing suffix.
7. A later field must never appear after an omitted field without preserving the omitted field's position.
8. Alphanumeric fields obey their maximum length (Odometer N(8); Vehicle Number, Job Number, Driver/ID, Fleet Employee, License AN(10); Job ID, Department #, Customer Data, User ID AN(12); Vehicle ID# AN(8); Fleet Tags AN(34)).
9. Segment 101 must not be preceded by, or followed by, another Segment 101 in the same message (Rule SEG101-R-026).
10. Segment 101 must be counted in Element 63 in Data Section 1 alongside Segment 100 and any other serialized companion.

## Empty Fields and Separators

The separator preserves position. If field 3 (Odometer) is empty but field 6 (Driver ID) is populated, the separators for fields 3, 4, and 5 must still be present:

```text
segType FS segLen FS emptyField3 FS emptyField4 FS emptyField5 FS driverId FS ...
```

Do not confuse these cases:

| Case | Expected behavior |
| --- | --- |
| Empty middle field | Preserve its separator |
| Unused trailing optional fields | Omit the unused suffix when allowed |
| Reordered field | Reject |
| Missing field followed by later field | Reject or parse as a shifted message |
| Extra separator in an invalid location | Reject or review per specification |

## Fleet Tag Encoding

Each populated Fleet Tag field is encoded as:

```text
<3-byte code><per-code payload>
```

The 3-byte code is one of 17 registered values (DLS, DLN, PON, INV, TRP, UNT, TLH, DOB, ZIP, END, MID, VIN, TRA, HUB, TLR, CBA, VHT). The payload conforms to the per-code format documented in the [rule catalog](../coverage/segment-101-rule-catalog.json).

Empty Fleet Tag positions may be omitted as a trailing suffix once all populated tags precede them.

## What Not To Assume

- Do not assume Segment 101 max length is always 61 — flag `PROVISIONAL P-01` when Auth Completion carries Fleet Tags.
- Do not omit empty non-trailing base fields.
- Do not serialize Fleet Tag positions when the message is not Auth Completion.
- Do not compute Element 63 from JSON object counts — count the final wire-serialized segments.

## Related Rules

- `SEG101-R-004..019` — Field-level type and length rules for Segment 101.
- `SEG101-R-005..006` — Segment Length rules and base cap.
- `SEG101-R-007` — Field order.
- `SEG101-R-008` — Empty non-trailing field separators.
- `SEG101-R-020..024` — Fleet Tag structural, applicability, and per-code format rules.
- `SEG101-R-026` — Segment 101 occurrence.
