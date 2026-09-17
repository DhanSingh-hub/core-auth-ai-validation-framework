# Odometer (Element 64 in Segment 101): SME/TBA Learning Note

## Core Idea

Element 64, Odometer, is not a random six-to-eight-digit number. Its representation depends on whether the fleet-program requires the device to capture and transmit the current vehicle odometer.

```text
Fleet program requirement
  -> Odometer capture at POS/pump
  -> Segment 101 field 3 (Element 64)
  -> numeric max length 8
  -> Segment Length rolls up into Element 84
```

## SME Reasoning

Ask:

1. Does the issuer or fleet program require odometer capture for this transaction?
2. Was the odometer entered by the driver, read from a telematics device, or defaulted?
3. Is the vehicle context available (is Vehicle Number, Vehicle ID#, or a Fleet Tag TRA/TLR populated to identify which vehicle the odometer belongs to)?
4. Is the value plausible (non-negative, within the eight-digit cap, consistent with prior transactions)?
5. Is odometer capture applicable to the transaction type (a Reversal typically inherits the original odometer)?

The same odometer value can be valid in one context and invalid in another because the fleet-program requirement changes the expected representation.

## TBA Dependency Chain

```text
Fleet program requirement
  -> Segment 101 conditional field 3 populated
  -> numeric N(8) representation
  -> Segment Length includes odometer characters
  -> Serialization preserves the field separator
  -> Lifecycle: Reversal reuses the auth's odometer where applicable
```

A requirement such as "Odometer is valid" is too vague. A useful requirement states the fleet-program trigger and expected representation (Rule anchor: `ATL105 | 2026-3 | 12.2 | 101 | 64 | odometer`).

## What Not To Assume

- Do not assume Odometer is always required — it is Conditional and the trigger lives in the fleet program (PROVISIONAL P-06).
- Do not assume the value is always numeric-only in the AI JSON representation — check the payload against the N(8) rule (`SEG101-R-009`).
- Do not populate Odometer in a non-fleet transaction to fabricate coverage.

## Related Rules

- `SEG101-R-009` — Odometer, when populated, is numeric with maximum length 8.
- `SEG101-R-008` — Empty non-trailing fields retain their Field Separators (applies when Odometer is absent but later fields are populated).
- `SEG101-R-005` / `SEG101-R-006` — Segment Length envelope.
