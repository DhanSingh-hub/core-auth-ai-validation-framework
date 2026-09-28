# Segment 130 Final Closure: Serialization and Lifecycle Learning Note

## Empty Fixed Fields Still Occupy A Position

Section 12.20 is explicit: *"Even when a field is not populated, you still need to send the Field Separator."*

Segment 130 has two commonly-empty fixed fields — Element 187 (CA Public Key File Checksum, Optional) and Element 188 (EMV Card Sequence Number, Conditional). Both keep their separators.

```text
130 | 0412 |        | 001 | 120 | <TLV> | ...
              ^ Element 187 empty, separator retained
```

Dropping the separator shifts Element 188 into position 3 and corrupts every subsequent field.

## Segment 130 Has No Trailing-Omission Allowance

This is a **key divergence from Segment 100**.

| Segment | Trailing optional fields |
| --- | --- |
| 100 | *"Any trailing fields within the data segment that are not needed should not be transmitted."* |
| **130** | **No equivalent statement.** Section 12.20 mandates separators between all six fixed fields. |

The only legitimately omissible construct in Segment 130 is the **entire EMV Additional Information Section** (zero repetitions). Individual fixed fields are not omissible — they are emptiable.

Do not port Segment 100's trailing-suffix rule into a Segment 130 validator.

## The Repeating Section Inverts The Separator Rule

Inside the EMV Additional Information Section the convention reverses:

```text
fixed fields        -> separator between every pair, even when empty
repeating section   -> NO separator inside a repetition
                    -> NO separator between repetitions
                    -> exactly ONE separator after the final repetition
```

A validator built on "separator between every field" will reject every valid multi-repetition segment.

## Lifecycle Message Correlation

Two Segment 130 rules cannot be certified from a single message:

```text
SEG130-R-015  CA Public Key File Checksum echo
              requires: EMV Request + EMV Response

Appendix T 001  EMV Table Data echo
              requires: authorization response + subsequent
                        advice or batch upload request
```

Both need real paired messages in the test data. A `testControls` flag asserting "correlated" or "echoed" is description, not evidence — the same anti-pattern documented for Segment 100.

## Certification Meaning

- **Serialization rules** prove the segment can be parsed without desynchronization.
- **Lifecycle rules** prove the segment belongs to a coherent EMV exchange.
- **Cryptographic rules** are deliberately excluded and remain the issuer's responsibility.

Closing Segment 130 means the first two are demonstrated and the third is *explicitly scoped out* rather than silently ignored.

## SME/TBA Review Questions

- Is every empty fixed field still emitting its separator?
- Is the validator refraining from applying Segment 100's trailing-omission rule?
- Is the repeating section's inverted separator rule implemented separately?
- Does Element 192 exactly match each Element 118 length?
- Are the echo rules tested with genuine paired messages?
- Is the adopted maximum length 3,043 with the 11.8.1 conflict logged as a specification defect?
- Are Segment 131's differing rules (no separators, 3,834 max, 2,800-byte section) handled by a distinct parser?

## Closure Gate

Segment 130 is not closeable while these remain open:

| Item | Blocks |
| --- | --- |
| `SEG130-SME-002` | CA key authenticity (`EXTERNAL_FIXTURE_REQUIRED`) |
| `SEG130-SME-003` | Cross-field consistency with Segment 100 |
| `SEG130-SME-004` | Cryptogram scope confirmation |
| `SEG130-SME-005` | Canonical AI artifact selection |
| `SEG130-SME-007` | Appendix T Table `002` request-side legality |
