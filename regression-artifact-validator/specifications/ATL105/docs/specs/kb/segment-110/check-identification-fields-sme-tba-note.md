# Check Identification Fields: SME/TBA Learning Note

## Core Idea

Four Segment 110 fields identify the check and the person presenting it: Driver's License (123), State Code (124), Date of Birth (125), and Check Number (127). Check Type (126) is required on every check transaction and is not part of this conditional bundle.

```text
Check transaction
  -> entry method (MICR read or manually entered/keyed)
  -> manually entered/keyed
      -> Driver's License required
      -> State Code required (must accompany Driver's License)
      -> Date of Birth likely required (Certegy-style bundling, unconfirmed trigger)
      -> Check Number required
  -> Check Type always required (P or C)
```

## Source-Confirmed Rules

| Field | Rule | Source |
|---|---|---|
| Driver's License (123) | Alphanumeric, max 40 bytes; required on all manually entered check transactions. | Section 12.9 / Chapter 13 |
| State Code (124) | Fixed 2 alphanumeric characters; required when Driver's License is included; valid values per Appendix D (76 codes). | Section 12.9 / Appendix D |
| Date of Birth (125) | Numeric, fixed 8 digits (MMDDYYYY); marked conditional with no explicit "Processing Rules" text. | Section 12.9 / Chapter 13 |
| Check Type (126) | Alphanumeric, fixed 1 character, required; valid values `P` (Personal) and `C` (Company). | Section 12.9 / Chapter 13 |
| Check Number (127) | Alphanumeric, max 8 bytes; required for manually keyed check data. | Section 12.9 |

## SME Questions

1. What field, prompt code, or POS entry-mode value machine-testably marks a transaction as "manually entered"/"manually keyed"?
2. Is Date of Birth required whenever Driver's License/State Code are present, or does it have a distinct trigger (for example, only for specific check services such as Certegy)?
3. Are there additional Check Type codes beyond `P` and `C` used by any enabled check service?

## TBA Rule Pattern

```text
BR: A manually entered check transaction shall include Driver's License, State Code, and Check Number.
TS: Device submits a manually keyed personal check with a missing Check Number.
TC: Submit the transaction without Check Number; expect rejection once the manually-entered trigger is approved.
TD: Synthetic driver's license, valid Appendix D state code, and synthetic check number.
```

## Current Boundary

The validator enforces Check Type's fixed length and value catalog, and State Code's fixed length and Appendix D catalog, unconditionally. It cannot yet enforce the manually-entered trigger for Driver's License, Date of Birth, or Check Number until `SEG110-SME-003` and `SEG110-SME-004` are resolved.
