# Driver / Identification Number (Element 31 in Segment 101): SME/TBA Learning Note

## Core Idea

Element 31, Driver/Identification Number, is the driver-identity captured at the POS or pump when the fleet program requires it. It **must be transmitted unencrypted** in Segment 101 field 6.

```text
Fleet program requirement
  -> Driver identity capture (keyed, swiped, prompted)
  -> Segment 101 field 6 (Element 31)
  -> alphanumeric max length 10
  -> unencrypted representation on the wire
```

## SME Reasoning

Ask:

1. Does the issuer or fleet program require a driver identifier for this transaction?
2. How did the device capture the identifier — driver key entry, Host Prompt, telematics feed?
3. Does the value uniquely identify the driver in the fleet program's records?
4. Is there a distinct Fleet Employee Number (field 7) that should carry the identifier instead? A single number should not be echoed into both fields.
5. Is the value being sent in clear text? Encrypted representation is not permitted for this element.

## TBA Dependency Chain

```text
Fleet program requirement
  -> Segment 101 conditional field 6 populated
  -> Alphanumeric AN(10) representation
  -> Unencrypted
  -> Segment Length envelope updated
  -> Field-order and separator rules preserved
```

A requirement such as "Driver ID is valid" is too vague. Prefer:

> If required by the issuer, the Driver/Identification Number (Element 31) is present as an unencrypted alphanumeric value (max 10) in Segment 101 field 6. Anchor: `ATL105 | 2026-3 | 12.2 | 101 | 31 | driver-identification-number`.

## What Not To Assume

- Do not assume Driver/Identification Number is populated for every fleet transaction — it is Conditional (PROVISIONAL P-06).
- Do not assume the value is numeric-only. AN(10) means alphanumeric.
- Do not encrypt the value or replace it with a token — that violates the spec's explicit "unencrypted" requirement.
- Do not overload with Fleet Employee Number.

## Related Rules

- `SEG101-R-012` — Driver/Identification Number, when populated, is alphanumeric with maximum length 10.
- `SEG101-R-013` — Fleet Employee Number is a distinct field 7.
- `SEG101-R-008` — Empty non-trailing fields retain their Field Separators.
- `SEG101-R-005` — Segment Length includes this field's contribution when populated.
