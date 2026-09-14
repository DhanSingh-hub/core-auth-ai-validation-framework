# AI Solution Test Data: Independent Review

**Input folder:** `test-input/ai-solution/test-data/`  
**Files reviewed:** `Sale.json`, `Void.json`  
**Review type:** Independent structural and semantic review  
**Specification:** ATL105 2026-3  
**Status:** Review findings require AI Solution/SME clarification before canonical package validation

## Executive Summary

The two files are syntactically valid JSON and represent ATL105-style Financial Request messages. They are not currently in the canonical AI test-data artifact contract used by the Test Validation framework.

They are raw message documents with a `Financial Request` wrapper, not canonical test-data artifacts containing:

- `testDataId`
- `testCaseId`
- `scenarioId`
- `expectedValidation`
- `sourceAnchors`
- `payload`

Therefore, the current canonical traceability validator cannot independently prove which test case each file implements or whether the AI expects the message to pass or fail.

## Automated Structural Checks

| Check | Sale | Void | Result |
| --- | --- | --- | --- |
| JSON syntax | Pass | Pass | Valid JSON |
| Message Type = `ATL105` | Pass | Pass | Correct message identity |
| Declared segment count | `3` | `3` | Matches observed segments |
| Observed segment types | `100, 111, 130` | `100, 111, 130` | Count and types are structurally consistent |
| EMV Chip Data length | Declared `147` bytes; observed `147` bytes | Same | Pass |
| Prompt Code shape | `0020` | `8020` | Valid shape |
| Sequence Number shape | `842280` | `842271` | Six digits |
| Terminal Identifier shape | `HC375003` | `HC375003` | Alphanumeric and within 22 characters |

## Findings

### F-001: Not in canonical AI artifact format

**Severity:** Blocking for traceability validation

The files contain raw message data only. They do not identify:

- Which BR they implement
- Which scenario they implement
- Which test case they implement
- Expected validation result
- Canonical ATL105 source anchor
- Positive/negative/lifecycle classification

**Required action:** AI Solution Team must provide either canonical wrappers or an approved adapter mapping this format into the canonical model.

### F-002: Sale and Void lifecycle relationship is not declared

**Severity:** High

`Sale.json` uses Sequence Number `842280`. `Void.json` uses Sequence Number `842271`.

If `Void.json` is intended to reverse the sale in `Sale.json`, the sequence numbers do not correlate. If they are independent samples, the package must explicitly say so.

**Required clarification:**

```text
Are Sale.json and Void.json related lifecycle messages?
If yes, which original transaction does Void.json reverse?
If no, provide independent test-case/scenario identifiers.
```

### F-003: Local Date/Time encoding appears inconsistent with the current knowledge base

**Severity:** High

Both files contain:

```text
LocalDateTime = 2608101430
```

The current ATL105 data-element note documents Element 49 as `MMDDYYHHMM`. Under that format, `26` is not a valid month. The value looks more like `YYMMDDHHMM` for 2026-08-10 14:30.

**Required clarification:** Confirm the exact date representation for this AI output format and update the knowledge base or adapter if the raw format intentionally uses `YYMMDDHHMM`.

### F-004: EMV Card Sequence Number is not three characters

**Severity:** Medium

Both files contain:

```text
EMVCardSequenceNumber = "25"
```

The current ATL105 note documents Element 188 as three characters: `000-099`, or three spaces when the tag is absent. The value should likely be `025` if it represents sequence 25.

**Required clarification:** Confirm whether the AI format permits an unpadded value or whether the source artifact is invalid.

### F-005: Track data and EMV data are both present

**Severity:** Review required

Both files contain TrackData and an EMV Data Segment. This may be valid for a specific fallback or representation, but the entry-method relationship is not declared.

Variable Information Table 005 has value `051`, which appears consistent with an ICC/EMV entry mode, but the package does not explain why TrackData is also present.

**Required clarification:** Identify the intended POS entry method and explain whether TrackData is:

- actual host-submitted data,
- retained device data,
- fallback data,
- synthetic placeholder data, or
- an artifact-generation convenience field.

### F-006: Sensitive-looking card data requires synthetic-data confirmation

**Severity:** Security review required

Both files contain PAN/track-like values:

```text
5121076312111357:4912
```

The AI Solution Team must confirm that this is synthetic test data and not production card data. The Test Validation repository should not accept live PAN, track, PIN, key, or production token values.

## Sale-Specific Interpretation

Observed:

```text
Prompt Code: 0020
Transaction Type: 0
Card Type: 020
Nonfuel Amount: 00002500
Partial Approval Indicator: 1
Segments: 100 + 111 + 130
```

This looks like a POS purchase/capture with EMV and variable-information data. The package should identify:

- Why Segment 111 is required.
- Which Variable Information tables 047, 005, 030, and 031 represent.
- Whether Segment 130 is required because the transaction is EMV.
- Whether the expected result is PASS.

## Void-Specific Interpretation

Observed:

```text
Prompt Code: 8020
Transaction Type: 8
Card Type: 020
Approval Number: 039038
Segments: 100 + 111 + 130
```

This looks like a purchase reversal/void. The package should identify the original purchase and confirm whether the sequence number, approval number, account context, amount, and required companion segments correlate correctly.

## Required AI Solution Metadata

For these files to enter canonical validation, provide:

```json
{
  "testDataId": "...",
  "testCaseId": "...",
  "scenarioId": "...",
  "expectedValidation": "PASS or FAIL",
  "sourceAnchors": [],
  "messageCategory": "STANDARD_FINANCIAL_REQUEST",
  "lifecycleKey": "...",
  "entryMode": "...",
  "syntheticData": true
}
```

Also provide the schema/format note explaining the raw `Financial Request` representation.

## Independent Verdict

```text
JSON syntax: PASS
ATL105 structural shape: PASS with semantic review items
Canonical traceability: NOT VALIDATABLE YET
Lifecycle correlation: NOT PROVEN
Date encoding: REVIEW REQUIRED
EMV card-sequence formatting: REVIEW REQUIRED
Sensitive-data status: CONFIRMATION REQUIRED
Overall: HOLD_FOR_AI_TEAM_CLARIFICATION
```

## Revalidation Addendum (2026-09-14)

`Sale.json` and `Void.json` are byte-identical to the versions reviewed above. This pass re-checked the
prior findings directly against the authoritative field-level layouts in Chapter 12 (Data Segment No. 100,
111, 130) and Chapter 13 (element definitions) of
`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf`, rather than the earlier
knowledge-base notes. Results:

| Item | Result | Basis |
| --- | --- | --- |
| Segment 100 field order/elements (17 fields: 85,84,44,102,78,2,12,33,79,41,58,99,17,86,5,49,121) | Confirmed match | Chapter 12.1 layout table |
| Segment 111 field order/elements (85,84,111,112,113) | Confirmed match | Chapter 12.10 layout table |
| Segment 130 core fields (85,84,187,188,189,190) | Confirmed match | Chapter 12.20 layout table |
| `EMVAddlInfo` = `"001006EMVYES"` | **Resolved — spec-compliant.** Decomposes cleanly as Indicator `001` (elem 191, 3 bytes) + Length `006` (elem 192, 3 bytes) + Data `EMVYES` (elem 118, 6 bytes, length matches). This is the EMV Additional Information Section trailer of Segment 130, which per spec has no field separators between its elements. | Chapter 12.20, EMV Additional Information Section note |
| `EMVChipDataLength` = `"147"` vs raw `EMVChipData` hex string (294 characters) | **Resolved — consistent.** 294 hex characters = 147 bytes; Element 189 counts raw bytes, not hex-string characters. | Element 189 definition (Chapter 13.2) |
| `EMVCardSequenceNumber` = `"25"` | **Confirmed hard violation (upgraded from F-004).** Element 188 is defined as "Fixed length of three bytes, right-aligned and padded with zeroes." `"25"` must be `"025"`. | Element 188 definition, line ~24341 of `extracted_text.txt` |
| `LocalDateTime` = `"2608101430"` | **Confirmed hard violation (upgraded from F-003).** Element 49 is defined as `MMDDYY` + `HHMM` (10 digits). Parsed as MMDDYYHHMM, month = `26`, which is not a valid month (01-12). The value is almost certainly `YYMMDDHHMM` (2026-08-10 14:30), which is not a valid encoding for this element. | Element 49 definition, line ~20521 of `extracted_text.txt` |
| `PromptCode.CardType` = `"020"` | **New finding.** Per Appendix E this code is `Visa Fleet Credit`, not a generic Visa credit card. Neither file includes a Fleet Data Segment (No. 101). This is not a hard violation (Segment 101 is conditional per the segment compatibility matrix), but it should be confirmed with the AI Solution Team — either the card type is wrong for a generic "Sale"/"Void" sample, or Fleet data is intentionally omitted and that should be stated. | `docs/specs/kb/appendix-code-tables.md` |
| `PromptCode.TransactionType` = `"0"` (Sale) / `"8"` (Void) | Confirmed correct | Appendix G: `0` = POS Purchase/Capture, `8` = Purchase reversal/void |
| Declared `SegmentLength` values (`092`, `071`, `203`) | **Not independently verifiable from this JSON.** Computing the true wire-format byte count requires the actual Field Separator byte and raw (non-JSON-wrapped) field values, which this representation does not preserve. Treat as unverified rather than pass. | N/A |

### Updated Verdict

```text
Previously-blocking findings (F-001, F-002, F-006): UNCHANGED, still open — files are unmodified.
F-003 (Local Date/Time encoding): CONFIRMED VIOLATION (was "appears inconsistent")
F-004 (EMV Card Sequence Number padding): CONFIRMED VIOLATION (was "review required")
F-005 (EMV Additional Information trailer): RESOLVED — structurally valid, not a defect
New: Card Type 020 (Visa Fleet Credit) without a Fleet Data Segment: CLARIFICATION REQUESTED
Segment Length wire-byte accuracy: UNVERIFIABLE from this artifact format
Overall: HOLD_FOR_AI_TEAM_CLARIFICATION (unchanged; two findings upgraded to confirmed, one resolved, one new)
```
