# Segment DL2 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL2 — Dial String Data Segment · **Sources:** 11.7, 11.7.1.2, 11.7.2, 11.7.2.2 · **Oracle:** [rule catalog](coverage/segment-DL2-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md)

## Core Idea

DL2 is the only DL segment with two homes: it is Conditional inside a Table Load Response and Required as the sole content of a Phone Load Response. The same DL2 string is therefore valid or invalid depending on the message and on the merchant's load flag.

## Rules

| Rule | Statement | Status |
|---|---|---|
| `SEGDL2-R-008` | Table Load Response Block 2 (C); Phone Load Response (R); nowhere else | REVIEW_REQUIRED (P-03) |
| `SEGDL2-R-009` | Phone Load only with load flag `PHON`; otherwise error + terminating block | SPEC_DERIVED |

## Context

- 11.7 says a download response may be "a Table Load or a subset of the complete table load containing only dial string information" — that subset is the Phone Load Response.
- The `PHON` flag can be set by a BUYPASS representative or "automatically following a full load".
- The Phone Load Request is `?` + Terminal Identifier (13) + Load Type `P` (the same Load Type as a Table Load request).

## SME Questions

1. Does a Phone Load Response begin with `)` (Element 97) or directly with `!`? (`SEGDL2-SME-003`)
2. When is DL2 omitted from a Table Load Response? (The layout marks it `C` but gives no condition.) Keep "DL2 omitted" fixtures `REVIEW_REQUIRED` until answered.
3. How does the host tell a Table Load request from a Phone Load request when both use Load Type `P`? (By the merchant load flag — confirm.)

## TBA Decomposition

```text
BR:  A Phone Load Response shall contain DL2 (SEGDL2-R-008).
TS:  Merchant with load flag PHON requests a Phone Load.
TC+: Response contains '!1...F~'. Expected PASS.
TC-: Response without DL2. Expected FAIL citing SEGDL2-R-008.
TC-: Software Load Response containing DL2. Expected FAIL citing SEGDL2-R-008.
```

## Review Checklist

- Does each fixture record message type and load flag?
- Are Table Load fixtures with and without DL2 both present?
