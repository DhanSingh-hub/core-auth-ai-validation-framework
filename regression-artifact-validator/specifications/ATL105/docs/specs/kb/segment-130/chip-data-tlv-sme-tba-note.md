# EMV Chip Data and TLV Encoding: SME/TBA Learning Note

## Core Idea

Element 190, EMV Chip Data, is not an opaque blob. It is a length-prefixed, TLV-encoded structure whose *validatable* properties and *non-validatable* properties must be separated before any requirement is written.

```text
Card performs EMV kernel processing
  -> kernel emits TLV tag set
  -> device serializes TLV into Element 190
  -> Element 189 declares the byte length
  -> Segment 130 carries it
  -> host forwards to issuer for cryptographic verification
```

The framework validates **structure and consistency**. It does not, and cannot, validate **cryptographic authenticity**.

## The Length/Payload Contract

Element 189 (EMV Chip Data Length) and Element 190 (EMV Chip Data) form a matched pair.

| Element | Name | Len | R/O/C | Rule |
| --- | --- | --- | --- | --- |
| 189 | EMV Chip Data Length | 3 | R | Valid values `000`–`999` |
| 190 | EMV Chip Data | 999 | R | Length must equal the value declared in 189 |

Three independent failure modes exist and must be tested separately:

1. **Declared-vs-actual mismatch** — 189 says `120`, 190 carries 118 bytes.
2. **Out-of-range declaration** — 189 carries a non-numeric or >999 value.
3. **Structurally invalid TLV** — length matches, but the tag stream does not parse.

A test that only asserts "chip data present" catches none of these.

## What Is and Is Not Validatable

| Property | Validatable here? | Reason |
| --- | --- | --- |
| Element 189 is 3 numeric digits, `000`–`999` | Yes | Pure format rule |
| Element 190 length equals Element 189 | Yes | Pure consistency rule |
| TLV tag/length/value framing parses cleanly | Yes | Structural grammar |
| Required tags present for the transaction type | Partially | Needs SME tag list (`SEG130-SME-003`) |
| Tag values agree with Segment 100 context | **REVIEW_REQUIRED** | `SEG130-R-009`, open |
| Tag 9F26 (Application Cryptogram) is authentic | **No** | Requires certified EMV kernel/HSM (`SEG130-R-010`) |
| CA public key resolves for the card's AID | **No** | Requires real CA_KEYS file (`SEG130-R-016`, `SEG130-SME-002`) |

The last two rows are permanent boundaries for synthetic test generation. Any AI-generated requirement claiming to "verify the cryptogram" is wrong by construction and must be rejected during artifact comparison.

## Cross-Field Consistency With Segment 100

Certain EMV tags duplicate values that Segment 100 already carries. When both are present they must agree, otherwise the issuer will decline.

```text
Segment 100 transaction context        EMV chip tag (Element 190)
  authorized amount              <-->    9F02 (Amount, Authorised)
  transaction type               <-->    9C   (Transaction Type)
  currency                       <-->    5F2A (Transaction Currency Code)
  terminal country               <-->    9F1A (Terminal Country Code)
```

This mapping is recorded as `SEG130-R-009` and is **not yet certified**. `SEG130-SME-003` must confirm the authoritative tag list and the exact comparison semantics (for example, whether amount is compared before or after partial approval) before any of these become enforceable rules.

## SME Reasoning

Ask:

1. Is this an EMV authorization-class request, or a reversal? Reversals do not require EMV data at all (Section 10.14.2.4).
2. Was the transaction contact, contactless, or fallback? Fallback carries no chip data despite a chip card being present.
3. Does Element 189 match the actual Element 190 byte count?
4. Which tags does the acquirer mandate for this card brand and transaction type?
5. Which tag values must mirror Segment 100, and what is the tolerance?
6. Is any tag carrying cardholder data that must be synthetic in test artifacts?

## TBA Dependency Chain

```text
Entry mode (contact / contactless / fallback)
  -> whether chip data exists at all
  -> Element 189 declared length
  -> Element 190 TLV tag stream
  -> cross-field agreement with Segment 100
  -> issuer-side cryptographic verification (out of scope)
```

A requirement such as "EMV chip data is valid" is untestable. A useful requirement names the property:

```text
For an EMV contact authorization request, Element 189 shall contain a
three-digit length in the range 000-999, and the byte count of Element 190
shall equal that declared length. A mismatch shall fail validation
independently of TLV content correctness.
```

## Security and Test-Data Guidance

- Use synthetic TLV fixtures only. Never copy production chip data into test artifacts.
- Tag 57 (Track 2 Equivalent Data) and tag 5A (Application PAN) carry cardholder data — mask or synthesize.
- Never attempt to fabricate a valid 9F26 cryptogram; a synthetic value is acceptable *only* because authenticity is explicitly out of scope.
- Do not store real CA public keys or key-load artifacts in the repository (`SEG130-SME-002`).

## Current Validator Boundary

Planned `Segment130PayloadValidator` coverage:

- Element 189 presence, numeric type, 3-digit format, `000`–`999` range
- Element 190 presence and length agreement with 189
- TLV framing parse (tag, length, value walk to clean termination)

Deferred pending SME input:

- Mandatory tag set per card brand / transaction type
- Cross-field agreement with Segment 100 (`SEG130-R-009`)

Permanently out of scope:

- Cryptogram authenticity (`SEG130-R-010`)
- CA key resolution and checksum authenticity (`SEG130-R-016`)

## Review Checklist

- Is the transaction EMV authorization-class rather than a reversal?
- Is Element 189 exactly three numeric digits within range?
- Does Element 190's byte count equal Element 189's declared value?
- Does the TLV stream parse to clean termination with no trailing bytes?
- Are cross-field tags flagged `REVIEW_REQUIRED` rather than asserted as covered?
- Is cryptogram authenticity explicitly excluded rather than silently assumed?
- Are all tag values in test artifacts synthetic?
