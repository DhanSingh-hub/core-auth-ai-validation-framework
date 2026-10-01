# Segment DL1 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL1 — Merchant Data Segment · **Sources:** 11.7, 11.7.1, 11.7.1.2, 12.42 · **Oracle:** [rule catalog](coverage/segment-DL1-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md) (the inclusion decision)

## Core Idea

For Segment 100 the question is "which companions does this transaction need?". For DL1 the question is simpler but stricter: DL1 exists in exactly one message — the Table Load Response — and there it is Required as Data Block 1. A structurally perfect DL1 in any other message is an invalid message.

## Rules

| Rule | Statement | Status |
|---|---|---|
| `SEGDL1-R-007` | DL1 is Required, Data Block 1 after `)`, Table Load Response only | SPEC_DERIVED |
| `SEGDL1-R-008` | Table Load only when merchant load flag is `TABL`; otherwise error + terminating block, no DL1 | SPEC_DERIVED |
| `SEGDL1-R-002` | DL1 is recognised by Data Type Indicator `#` (the device parses blocks by this indicator) | SPEC_DERIVED |

## Download Trigger Context (11.7)

- A download can be requested manually at the device or automatically when a host response carries Download Indicator (Element 30) `1` ("request a partial load").
- The Table Load Request uses Load Type `P`; there is no separate "full load" request.
- The information in the download is used in the very next transaction, and device software should not be activated until at least one successful Table Load.

## SME Questions

1. For each fixture, is the merchant load flag `TABL`? The expected response is completely different when it is not.
2. What is the exact content of the "error message block" returned when the flag is not set? (Not defined in 11.7.1; keep negative expectations at "no DL1".)
3. Was the load requested manually or triggered by Download Indicator `1`? Record it as lifecycle context.

## TBA Decomposition

```text
BR:  DL1 shall appear only as Data Block 1 of a Table Load Response (SEGDL1-R-007).
TS:  Table Load for a merchant with load flag TABL.
TC+: Response = ')' + DL1 + ... ; Expected PASS.
TC-: Phone Load Response containing '#...~'; Expected FAIL citing SEGDL1-R-007.
TD:  Response JSON with messageType TABLE_LOAD_RESPONSE, dataBlocks[0].segment = DL1.
```

## Review Checklist

- Does every DL1 fixture declare the message type and the load flag?
- Is there a negative fixture for DL1 in a non-Table-Load response and for flag ≠ `TABL`?
- Is DL1 always the first data block?
