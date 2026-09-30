# Segment DL3 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL3 — Date and Time Data Segment · **Sources:** 11.7.1.2, 11.7.3, 11.7.3.2 · **Oracle:** [rule catalog](coverage/segment-DL3-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md)

## Core Idea

DL3 is the only download segment the device can obtain **without** a merchant load flag: the Date and Time Load (Load Type `D`) is always allowed. In a Table Load Response DL3 is Conditional (Data Block 3).

## Rules

| Rule | Statement | Status |
|---|---|---|
| `SEGDL3-R-007` | Table Load Response Block 3 (C) and Date and Time Load Response only | REVIEW_REQUIRED (P-03) |
| `SEGDL3-R-008` | Date and Time Load: device-initiated, Load Type `D`, no flag, time-zone/DST adjusted | SPEC_DERIVED |

## Load Types (Element 48)

| Load Type | Meaning | DL3 expected? |
|---|---|---|
| `P` | Partial load (Phone, Table) | Only inside a Table Load Response, Conditional |
| `D` | Date and Time Load | Yes |
| `K` | TransArmor / CA Public Key load | No |

## SME Questions

1. `SEGDL3-SME-003`: does the Date and Time Load Response end with `~`?
2. When is DL3 omitted from a Table Load Response?
3. For a device in a different time zone from BUYPASS, which reference time should the test oracle use?

## TBA Decomposition

```text
BR:  A Date and Time Load Request (Load Type 'D') shall be answered with DL3 data regardless of the merchant load flag (SEGDL3-R-008).
TS:  Merchant with no load flag set requests a Date and Time Load.
TC+: Request '?' + TID + 'D'; response ':' + 21 characters (+ '~' per SME-003). Expected PASS.
TC-: Response is an error block because no flag is set. Expected FAIL citing SEGDL3-R-008.
```
