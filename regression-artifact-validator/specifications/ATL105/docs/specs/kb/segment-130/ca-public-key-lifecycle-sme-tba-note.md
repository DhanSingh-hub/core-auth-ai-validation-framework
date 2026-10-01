# CA Public Key File Checksum Lifecycle: SME/TBA Learning Note

## Core Idea

Element 187, CA Public Key File Checksum, is a **handshake token**, not a payload value. Its purpose is to let the host detect that a device is running a stale certificate-authority key set and trigger a new download.

```text
Device holds CA_KEYS file
  -> computes/stores its checksum
  -> sends checksum in Segment 130 (Element 187, Optional)
  -> host compares against its own current checksum
  -> host echoes checksum in Segment 131 (Element 187, Required)
  -> mismatch implies the device needs a CA Public Key File Load
```

This is a **lifecycle** rule, not a field-format rule. Validating it requires both the request and the response message.

## Request/Response Asymmetry

The same element number behaves differently on each side of the exchange.

| Aspect | Segment 130 (Request) | Segment 131 (Response) |
| --- | --- | --- |
| Field position | 3 | 3 |
| Entry | **Optional** | **Required** |
| Max length | 25 | 25 |
| Source | Device/Host | Device/Host |
| Semantics | Checksum currently in use at the device | Echo of the request value |

Because the request field is Optional but the response field is Required, a validator cannot simply assert "present on both sides". It must model the conditional relationship.

## What Can and Cannot Be Verified

| Property | Verifiable here? | Reason |
| --- | --- | --- |
| Element 187 is ≤ 25 alphanumeric characters | Yes | Format rule |
| Response value equals request value | Yes | Requires paired messages (`SEG130-R-015`) |
| Checksum is Required in the response | Yes | Section 12.21 |
| Checksum mathematically matches a real CA_KEYS file | **No** | Needs production key file (`SEG130-SME-002`) |
| AID resolves to a specific CA public key index | **No** | Needs production key file (`SEG130-R-016`) |

The bottom two rows are `EXTERNAL_FIXTURE_REQUIRED` and were already flagged as such by the pre-existing Test Team Appendix S package. This training pass does not change that status.

## The CA_KEYS File (Appendix S)

`SEG130-R-016` covers the file itself, which is a separate artifact from the message field:

```text
CA_KEYS file
  -> correct file name and version header
  -> comma-separated records
  -> CRLF record termination
  -> per record: expiry date, SHA-1 hash indicator, RSA algorithm indicator,
                 RID, index, modulus, exponent, checksum
```

Structural checks against this layout **are** synthesizable. Genuine AID-to-key resolution is not.

## Lifecycle Correlation Requirement

Like Segment 100's sequence/lifecycle rules, this rule cannot be certified from a single message:

```text
EMV Financial Transaction Request  (Segment 130, Element 187 = C1)
  -> EMV Financial Transaction Response (Segment 131, Element 187 must echo C1)
```

A test control asserting "checksum echoed" is not evidence. Both messages must be present in the test data, and the validator must compare the actual values. This is the same anti-pattern already documented in the Segment 100 final-closure note.

## SME Reasoning

Ask:

1. Does the device populate Element 187 at all? It is Optional in the request.
2. If omitted in the request, what must the response carry, given the response field is Required?
3. What triggers a CA Public Key File Load — mismatch only, or also an explicit host flag?
4. Is the checksum environment-specific (test vs production key sets)?
5. Who owns the CA_KEYS artifact, and can a redacted or synthetic version be supplied for testing?
6. What is the expected behaviour when the device's key set is expired rather than merely stale?

## Open Items

- `SEG130-SME-002` — a real CA_KEYS file is required for authenticity checks, or explicit confirmation that structural-only validation is acceptable. **Status: open.**
- Behaviour when Element 187 is absent from the request but Required in the response is **not stated** in Sections 12.20/12.21 and should be added to the SME intake.

## Security and Test-Data Guidance

- Never commit real CA public keys, moduli, exponents, or production checksums.
- Use clearly synthetic checksum values with a recognizable test prefix.
- Treat the CA_KEYS file as a controlled artifact even in redacted form.

## Review Checklist

- Is Element 187 within 25 characters?
- Is the request/response pair actually present in the test data?
- Does the response value equal the request value?
- Is the response field treated as Required and the request field as Optional?
- Are authenticity claims marked `EXTERNAL_FIXTURE_REQUIRED` rather than `COVERED`?
- Are all key materials synthetic?
