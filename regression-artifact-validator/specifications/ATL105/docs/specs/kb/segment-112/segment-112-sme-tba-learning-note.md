# Segment 112: SME and Technical Business Analysis Learning Note

## Purpose

Segment 112 is the Additional Information Data Segment — a host-originated, response-only carrier that BUYPASS appends to a Financial Transaction Response when supplemental data (balances, AVS/CVV results, loyalty information, tokens, fraud scores, and 30+ other information types) must be returned to the device. Unlike Segment 100, Segment 112 is never built by the device and never appears in a request.

This note is a learning guide, not an approved client requirement set.

## 1. The Correct Mental Model

Do not start with the JSON field names. Start with the response flow:

```text
Financial Transaction Request processed by host
  -> host determines which supplemental information applies
  -> host sets Element 115 (Additional Information Data Segment Flag)
  -> host builds Segment 112 with one triad per applicable information type
  -> host appends Segment 112 at the end of the Financial Transaction Response
  -> device parses Segment 112 only if Element 115 = 1
```

| Layer | Business question | Technical representation |
| --- | --- | --- |
| Financial Transaction Response | Was the transaction approved/declined, and does supplemental data exist? | Element 115 flag |
| Segment 112 | What supplemental information types are present? | Repeating Indicator/Length/Value triad |
| Element 116 | What kind of information is this triad carrying? | 3-digit code, 40+ documented values |
| Element 117/118 | How long is the value, and what is it? | Length-prefixed variable value |

Segment 112 is not a general-purpose "extra data" bucket to be filled with anything convenient; each triad's Element 118 content is defined by its paired Element 116 code, and many codes have their own sub-layout in Appendix K.

## 2. SME Reasoning: When Does Segment 112 Apply?

A card/POS SME and TBA should answer these questions before defining Segment 112 test data:

1. Did the Financial Transaction Request context imply any of the ~40 documented information types (e.g., was AVS or CVV requested, is this a gift/EBT/phone-card balance inquiry, is loyalty in play, is a token or PAR being returned)?
2. Is more than one information type applicable at once (e.g., both AVS **and** CVV)? If so, expect multiple triads in one Segment 112, not multiple Segment 112 instances.
3. Does the triad's Element 118 content have a fixed length (e.g., `012` Discover Retrieval Reference Number is fixed 15 bytes, `013` Expiration Date is fixed 4 bytes, `017` BUYPASS Host Card Type is fixed 3 bytes, `018` PINless info is fixed numeric 3) or a variable length governed by Element 117?
4. Does this code have a documented Appendix K sub-table layout (e.g., `001` Balance Information, `004` CVV Information) that further decomposes Element 118?
5. Is the combined Additional Information Section within the 990-byte cap, and is total Segment 112 within the 999-byte cap?

A technical business analyst converts each answer into:

```text
condition -> Element 116 code -> Element 118 sub-layout (if any) -> expected result
```

Example:

```text
Financial Transaction Request included an AVS check
  -> Element 116 = 003
  -> Element 118 carries AVS result per its documented layout
  -> Segment 112 present, Element 115 = 1
```

## 3. Segment 112 Field Responsibilities

| Field | Element | SME interpretation | Common analysis risk |
| --- | ---: | --- | --- |
| Segment Type | 85 | Identifies Segment 112 | Section 12.11's field table prints `Source: Device`, resolved 2026-09-26 as a transcription error — Host is authoritative for all 5 fields |
| Segment Length | 84 | Length of the encoded Segment 112 content | Counting JSON character length instead of wire-encoded length |
| Additional Information Indicator | 116 | Which information type this triad carries | Treating it as a free-text label instead of a controlled 3-digit code |
| Additional Information Length | 117 | Length of the following Element 118 value | Confusing this with the overall Segment Length (Element 84) |
| Additional Information | 118 | The actual value, shaped by the paired Indicator | Assuming a single generic format for all 47 documented codes |

## 4. Segment 112 Repetition and Boundaries

Segment 112 is not a fixed 5-field record; fields 3-5 repeat as a unit:

| Boundary | Value | Consequence if violated |
| --- | --- | --- |
| Additional Information Section (all triads combined) | Max 990 bytes | Truncation or rejection — REVIEW_REQUIRED, no documented host behavior for overflow |
| Segment 112 total | Max 999 alphanumeric characters | Same as above at the segment level |
| Element 117 (per-triad length) | 001-985 | A single triad cannot itself exceed the section cap |
| Element 118 (per-triad value) | Max 984 bytes | Matches Element 117's practical ceiling |

Do not assume only one triad is ever present; production Financial Transaction Responses commonly carry two or more (for example, AVS **and** CVV together).

## 5. Segment 112 and the Financial Transaction Response

Segment 112 has exactly one structural trigger: Element 115.

| Element 115 value | Expected Segment 112 presence |
| --- | --- |
| `0` | Absent |
| `1` | Present, appended at the end of the response |

Both directions of mismatch are structural defects: `1` with no Segment 112, or a Segment 112 present while Element 115 = `0`.

## 6. Serialization Thinking

Validate these separately, same discipline as Segment 100:

1. JSON structure and data types.
2. Semantic field values (is the Element 116 code documented, and does Element 118's shape match its sub-layout?).
3. Segment presence consistency with Element 115.
4. Field separators between fields 1-5 (present per the field table's Field Separator notes).
5. Segment Length (Element 84) against actual encoded content.
6. Additional Information Length (Element 117) against actual Element 118 content.
7. The 990-byte section cap and 999-byte segment cap, independently.

## 7. Turning Knowledge Into Requirements

Good BR:

```text
When a Financial Transaction Request includes an Address Verification Service (AVS)
check, the Financial Transaction Response shall set Element 115 to 1 and include a
Segment 112 Additional Information Data Segment triad with Element 116 equal to 003.
```

Weak BR:

```text
The system should return AVS data when needed.
```

A good requirement identifies the triggering condition, the flag, the segment, the specific Element 116 code, the expected Element 118 shape, the source anchor, and the review boundary for undocumented codes (`014`, `015`, `033`).

## 8. Turning Requirements Into Tests

```text
BR: AVS request triggers Segment 112 with Element 116 = 003
  -> Scenario: Financial Transaction Request with AVS data present

TC-Positive:
  Submit AVS-eligible request; response contains Element 115 = 1 and one
  triad with Element 116 = 003.
  Expected result: PASS.

TC-Negative:
  Response contains Element 115 = 1 but no Segment 112 triad.
  Expected result: FAIL (structural inconsistency).

TD:
  Test-data record contains the AVS-eligible request context, the expected
  response Element 115 value, and the expected Segment 112 triad.
```
