# Segment 111 vs. Segment 112 Companion-Segment Compatibility: SME and TBA Learning Note

## Purpose

Segment 111 (Variable Information Data Segment) and Segment 112 (Additional Information Data Segment) are the two "repeating Indicator/Length/Value triad" segments in ATL105. Their structural similarity is a well-known source of test-data authoring mistakes. This note exists to prevent that confusion — it is not a compatibility rule that requires the two segments together.

This is a learning and validation note, not a business approval record.

## Why This Note Exists (Instead of a Fabricated Compatibility Rule)

Unlike Segment 100's genuine companion-segment rules (e.g., "EMV requires Segment 130"), there is no specification text stating that Segment 111 requires Segment 112, or that Segment 112 requires Segment 111. Fabricating such a rule would violate the Test Solution's independent-training policy. Instead, this note documents the two segments' side-by-side differences so that test-data authors do not accidentally treat them as interchangeable or co-required.

## Side-by-Side Comparison

| Attribute | Segment 111 (Variable Information) | Segment 112 (Additional Information) |
| --- | --- | --- |
| Message side | Financial Transaction **Request** | Financial Transaction **Response** |
| Origin | Device | Host (BUYPASS) |
| Repeating indicator element | 111 (Variable Information Indicator) | 116 (Additional Information Indicator) |
| Repeating length element | 112 (Variable Information Length) | 117 (Additional Information Length) |
| Repeating value element | 113 (Variable Information) | 118 (Additional Information) |
| Repeating section cap | 991 bytes | 990 bytes |
| Segment overall cap | Per Segment 111's own rule catalog | 999 alphanumeric characters |
| Indicator code range | 001-982, per Section 13.2 Element 111 value table | 001-047 documented (`002`, `014`, `015`, `033` reserved/undocumented) |
| Position in message | Part of Data Section No. 3 (conditional companion segments) | Appears only at the end of a Financial Transaction Response |
| Sub-layout reference | Appendix I (Variable Information Data Layouts) | Appendix K (Additional Information Data Layouts) |

## SME Questions

1. Is the test scenario building a **request** (Segment 111 candidate) or a **response** (Segment 112 candidate)? Confirm before selecting which rule catalog and which Indicator code table applies.
2. Does the fixture reuse a Segment 111 Indicator code number by coincidence (e.g., `009`) that also happens to be a Segment 112 code? These are two unrelated code tables — the same numeric code means different things in each segment.
3. Is the byte-cap being validated the correct one for the segment (991 vs. 990) and not swapped?
4. Does the fixture correctly assign device origin to Segment 111 fields and host origin to Segment 112 fields?

## Common Analysis Errors

- Copy-pasting a Segment 111 fixture, renumbering `SegmentType` to `112`, and leaving Segment 111's Indicator codes, byte caps, or device-origin assumptions in place.
- Assuming a Segment 111 Indicator code and a Segment 112 Indicator code with the same numeric value describe the same information (they do not; the two tables are independent).
- Asserting a compatibility rule ("Segment 111 requires Segment 112") without a specification citation — no such rule currently exists in Section 12.10, 12.11, or the Section 11.1.1 companion-segment table.
- Validating Segment 112 against Segment 111's 991-byte cap (or vice versa) instead of each segment's own documented cap (990 vs. 991).

## Turning This Into Requirements

```text
BR: Segment 111 and Segment 112 shall each be validated against their own
    independent rule catalogs, byte caps, and Indicator code tables. Neither
    segment's presence shall be treated as evidence that the other is
    required, absent a specification citation establishing that dependency.

TC: Construct a Financial Transaction Request containing Segment 111 with
    Indicator = 009 (Visa TAP), and its Financial Transaction Response
    containing Segment 112 with Indicator = 009 (Visa product result).
    Expected result: both segments validate independently and pass; the
    shared numeric code 009 is coincidental, not a cross-segment link.
```
