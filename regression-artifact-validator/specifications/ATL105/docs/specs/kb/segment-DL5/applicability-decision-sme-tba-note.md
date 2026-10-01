# Segment DL5 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL5 — Software IP Load Data Segment · **Sources:** 12.46, 11.7, 11.7.4.2, 10.10 · **Oracle:** [rule catalog](coverage/segment-DL5-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md)

## Core Idea

DL5 applicability is identical to DL4's: BUYPASS-managed devices only, Software Load Response only, merchant flag `SOFT`. The only DL5-specific point is its position: field 3, after DL4.

## Rules

| Rule | Statement | Status |
|---|---|---|
| `SEGDL5-R-001` | BUYPASS-managed devices only | SPEC_DERIVED |
| `SEGDL5-R-006` | Software Load Response field 3 after DL4; flag `SOFT` | REVIEW_REQUIRED (`SEGDL4-SME-002`) |

## SME Questions

1. `SEGDL4-SME-002` (shared): Software Load vs Table Load exchange; how the device chooses IP vs dial.
2. Is DL5 still sent to a dial-only device?

## TBA Decomposition

```text
BR:  DL5 shall follow DL4 in the Software Load Response (SEGDL5-R-006).
TS:  Scheduled load for a BUYPASS-managed device.
TC+: ')' DL4 DL5. Expected PASS.
TC-: ')' DL5 DL4. Expected FAIL citing SEGDL5-R-006.
```
