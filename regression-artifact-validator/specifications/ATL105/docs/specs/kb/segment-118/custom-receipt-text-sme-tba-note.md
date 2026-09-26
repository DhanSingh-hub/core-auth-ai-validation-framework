# Custom Receipt Text (Prompt Code 901): SME/TBA Learning Note

## Core Idea

Prompt Code 901 lets the host push text that should be printed on every receipt during a defined date/time window. It is response-only: the device requests it with a plain Prompt Code 901 request, and the host's response carries the actual text.

```text
Host has new receipt text
  -> Device sends Proprietary Data Load Request, Prompt Code 901
  -> Host responds with:
       Start Date/Time, End Date/Time (validity window)
       Number of Receipt Text Lines
       Repeated: Receipt Text Data Length + Receipt Text Data (up to 220 bytes total)
```

## Source-Confirmed Facts

| Field | Element | Type / Length | Notes |
|---|---|---|---|
| Start Date | 165 | N, 8 (CCYYMMDD) | Begin using the text |
| Start Time | 166 | N, 4 (HHMM) | Begin using the text |
| End Date | 165 | N, 8 (CCYYMMDD) | Stop using the text |
| End Time | 166 | N, 4 (HHMM) | Stop using the text |
| Number of Receipt Text Lines | 168 | N, 2 | Count of repeat blocks that follow |
| Receipt Text Data Length | 169 | N, 2 | Length of the following Receipt Text Data |
| Receipt Text Data | 170 | AN, 20 | The text itself |

Note that Element 165 (a date) and Element 166 (a time) are reused for both the start and end of the window — the field's role is determined by its position (fields 14/16 = dates, 15/17 = times), not by a distinct element number.

## SME Questions

1. Is Receipt Text Data (170) always a fixed 20-byte slot (space-padded when the actual text is shorter), or does its true length come solely from the preceding Receipt Text Data Length (169)?
2. Is there a maximum Number of Receipt Text Lines beyond what the 220-byte total implies?
3. Are Response Codes V (Declined, Custom Receipt Text pending) and W (Approved, Custom Receipt Text pending) used identically to the Host Discount D/E/M/N pattern to signal that a 901 load is available?

## TBA Rule Pattern

```text
BR: A Custom Receipt Text response shall include a validity window and one or more text lines whose total does not exceed 220 bytes.
TS: Host responds with 3 receipt text lines summing to under 220 bytes.
TC: Validate the date/time window fields and the repeat-block length arithmetic.
TD: Sanitized converter-ready response with synthetic receipt text.
```

## Current Boundary

The validator checks the fixed-position fields (dates, times, line count) and the presence of the repeated Text Length/Data blocks. It does not yet enforce a specific encoding for Receipt Text Data itself, pending `SEG118-SME-002`.
