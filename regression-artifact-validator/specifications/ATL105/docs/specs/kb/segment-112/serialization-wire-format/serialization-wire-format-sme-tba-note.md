# Segment 112 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 112 moves from business intent (host determines which supplemental information to return) into the serialized ATL105 message appended to a Financial Transaction Response.

A JSON object describing Segment 112 can be syntactically valid and semantically plausible while the serialized ATL105 message is still invalid. Serialization is a separate validation responsibility, same as for Segment 100.

## Three Representations

```text
Host business decision (which information types apply)
  -> structured test-data JSON (one or more triads)
  -> serialized ATL105 Segment 112 (Indicator/Length/Value repeated, Field Separators per the field table)
  -> appended to the Financial Transaction Response, after the last data element
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Business intent | Which information types must the response carry? | Element 116 code selection |
| Logical JSON | Are the triads represented structurally (indicator, length, value)? | One JSON object per triad |
| ATL105 message | Are fields encoded in the required order, with correct Field Separators? | Separators between fields 1-5; per-triad boundaries |
| Response placement | Is Segment 112 the last segment in the message? | Position check |

## Segment 112 Serialization Rules

1. Segment Type is fixed as `112`.
2. Segment Length (Element 84) represents the encoded Segment 112 content — all triads plus the fixed 2-field header — not JSON text length.
3. Each triad's Additional Information Length (Element 117) must equal the actual byte length of its paired Additional Information (Element 118).
4. The Additional Information Section (all triads combined) must not exceed 990 bytes.
5. Segment 112 overall must not exceed 999 alphanumeric characters.
6. Segment 112 is appended only at the end of the Financial Transaction Response — no other segment follows it.
7. Element 115 in the Financial Transaction Response and Segment 112 presence must agree bidirectionally.
8. Fixed-length codes (`012`, `013`, `017`, `018`) must serialize to their exact documented length, not merely within Element 117's declared value.

## Length Rules

```text
Logical JSON length != Element 117 (per-triad Additional Information Length)
Sum of triad lengths != Segment Length (Element 84)
Segment Length != TCP/IP Message Length
```

Validation should verify, independently:

- Each triad's Element 117 matches its Element 118's actual length.
- The combined triad section is within 990 bytes.
- Segment 112's Segment Length (Element 84) matches its actual encoded content.
- Segment 112's overall length is within 999 bytes.
- Segment 112 is positioned as the final segment in the response.

A hard-coded length copied from a sample fixture is not sufficient evidence for a generated test case; it must be recalculated from the actual triad content.

## SME Questions

1. How many triads does this scenario require, and in what order (the specification does not document a required triad order — confirm with SME before asserting one)?
2. Does any triad in this scenario use a fixed-length code (`012`, `013`, `017`, `018`) whose length must be validated exactly?
3. Is the combined triad byte count close to the 990-byte boundary, requiring a boundary test?
4. Does the response context set Element 115 correctly for the Segment 112 presence/absence being tested?

## TBA Artifact Decomposition

```text
BR:
  Segment 112 shall serialize its Additional Information Section as a
  sequence of Indicator/Length/Value triads whose combined byte length does
  not exceed 990 bytes, and whose Segment Length (Element 84) matches the
  segment's actual encoded content, with a segment-level cap of 999 bytes.

TS:
  Financial Transaction Response with two Additional Information triads
  (AVS and CVV) near the 990-byte combined boundary.

TC-Positive:
  Two triads totaling <= 990 bytes; Segment Length matches encoded content.
  Expected result: PASS.

TC-Negative:
  Two triads totaling > 990 bytes.
  Expected result: FAIL (section-cap violation).

TC-Negative:
  Element 117 for a triad does not match its Element 118 actual length.
  Expected result: FAIL (length mismatch).

TD:
  Test-data record with the two-triad payload, the expected byte counts, and
  the expected PASS/FAIL outcome for each boundary case.
```
