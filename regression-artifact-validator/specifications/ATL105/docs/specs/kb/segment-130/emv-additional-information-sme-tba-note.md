# EMV Additional Information Section: SME/TBA Learning Note

## Core Idea

Fields 7–9 of Segment 130 are not three ordinary fields. They are a **repeating group** keyed by Element 191 (EMV Additional Information Indicator), with its own length budget and its own separator rules.

```text
Zero or more repetitions of:
  Element 191  EMV Additional Information Indicator  (n3, Required)
  Element 192  EMV Additional Information Length     (n3, Required)
  Element 118  EMV Additional Information            (Var., Required)
```

The entire section is optional. Zero repetitions is a valid, complete Segment 130.

## Appendix T Resolves Open Item P-06

`SEG130-SME-006` asked whether Appendix T was in scope or a separate workstream. **Appendix T is present in the extracted specification text and is now transcribed below.** The question is therefore answered from source rather than requiring SME intake.

Appendix T defines exactly **two** indicator values:

### Table ID `001` — EMV Table Data

| Attribute | Value |
| --- | --- |
| Table ID | `n3`, fixed value `001` |
| Table Length | `n`, fixed |
| Maximum Length | `n6`, length 6 |

Purpose: notifies the chip when the MasterCard X-Code system was unable to go online, ensuring correct processing by a card personalized for full-grade processing.

**Request values (mutually exclusive):**

| Code | Meaning |
| --- | --- |
| `EMVYES` | Device **is able** to receive EMV Table Data. Sent only in an EMV Transaction request. |
| `EMVNOT` | Device **is unable** to receive EMV Table Data. Sent only in an EMV Transaction request. |

**Response behaviour:** if this field is included in an authorization response, the device **shall echo it back** on any subsequent advice or batch upload request.

### Table ID `002` — Card Authentication Results Code (CARC)

| Attribute | Value |
| --- | --- |
| Table ID | `n3`, fixed value `002` |
| Table Length | `n`, fixed |
| Maximum Length | `n6`, length **1** |

Purpose: carries the CARC value returned by Visa in Bit No. 44.8 of the response.

**Directionality note:** CARC is described as a value *returned by Visa*, which makes Table `002` a response-side construct. Its presence in a Segment 130 **request** would therefore be anomalous. This asymmetry is flagged below as a new SME item.

## Length Budget — Request vs Response

The two sides do **not** share a cap. This is a frequent defect source.

| Segment | Section cap |
| --- | --- |
| 130 (Request) | **2,000 bytes** |
| 131 (Response) | **2,800 bytes** |

A validator that applies 2,000 to both will incorrectly reject valid responses.

## Separator Rules

The section deliberately breaks the surrounding field-separator convention:

```text
... field 6 (EMV Chip Data) <FS> [rep1][rep2][rep3] <FS>
                              ^                      ^
                              |                      exactly one FS after the FINAL repetition
                              FS before the section starts

    NO separator between 191/192/118 inside a repetition
    NO separator between repetitions
```

This is structurally identical to Segment 111's Variable Information Section rule, so the same validator logic should be reused rather than reimplemented.

## Why Element 192 Matters

Because there are no internal separators, Element 192 is the **only** way a parser can find the end of Element 118 and the start of the next repetition. A wrong length value does not produce a localized error — it desynchronizes the parse for every subsequent repetition.

```text
correct:  [191=001][192=006][118=EMVYES][191=002][192=001][118=Y]
corrupt:  [191=001][192=005][118=EMVYE ][S...    <-- all following reps garbage
```

Test coverage must therefore include an off-by-one length mutation, not merely a missing-field mutation.

## Element 118 Is Reused Across Segments

`SEG130-R-012`. Element 118 appears in two unrelated roles:

| Segment | Field name | Occurrence | Max length |
| --- | --- | --- | --- |
| 112 | Additional Information | single | 984 |
| 130 | EMV Additional Information | repeating | variable |

Same number, different fields, no shared business meaning. Do not build a single shared validator for "Element 118".

## SME Reasoning

Ask:

1. Is the section present at all? Zero repetitions is legal.
2. Is each indicator one of the two Appendix T values (`001`, `002`)?
3. For Table `001`, is the value exactly `EMVYES` or `EMVNOT`, and is length 6?
4. For Table `002`, is length exactly 1?
5. Does the total section stay within 2,000 bytes for the request?
6. Does each Element 192 exactly match its Element 118 byte count?
7. For the echo rule, is there a prior authorization response to echo from?

## New SME Item Raised By This Analysis

> **SEG130-SME-007 (new):** Appendix T Table `002` (CARC) is defined as a value returned by Visa in the response. Confirm whether Table `002` may ever legitimately appear in a Segment 130 **request**, or whether request-side indicators are restricted to Table `001` only. This determines whether an indicator allow-list for the request should be `{001}` or `{001, 002}`.

## Security and Test-Data Guidance

- `EMVYES`/`EMVNOT` are device-capability flags, not cardholder data — safe to use literally.
- CARC values are issuer-returned single characters — synthesize rather than copy from production traces.

## Review Checklist

- Is the section genuinely optional in the test model?
- Is every indicator drawn from the Appendix T allow-list?
- Does each Element 192 match its Element 118 length exactly?
- Is the request cap 2,000 and the response cap 2,800?
- Is exactly one Field Separator emitted after the final repetition?
- Are there zero separators inside and between repetitions?
- Is the echo rule tested with a real prior response rather than a test control flag?
