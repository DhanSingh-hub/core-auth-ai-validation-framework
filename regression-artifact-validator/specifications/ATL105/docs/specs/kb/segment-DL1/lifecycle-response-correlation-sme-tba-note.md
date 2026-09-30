# Segment DL1 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL1 — Merchant Data Segment · **Sources:** 11.7, 11.7.1, 11.7.1.2, 12.47, 13.2 (Elements 30, 35, 97) · **Oracle:** [rule catalog](coverage/segment-DL1-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

Segment 100 lifecycle is original → follow-up. DL1 lifecycle is trigger → request → response → next transaction. DL1 can only be proven with the paired Table Load Request and the full Table Load Response; a lone DL1 string is not lifecycle evidence.

## Lifecycle Steps

| Step | Message | What to assert | Rule |
|---|---|---|---|
| 1 | Host response with Download Indicator `1` (or manual request) | Device is instructed to request a partial load | context |
| 2 | Table Load Request | `?` + Terminal Identifier (13) + Load Type `P` + Hardware (4), Software (8), Firmware (8) Version; no DL segment | `SEGDL1-R-008` |
| 3 | Table Load Response | Load flag `TABL` → `)` DL1 [DL2] [DL3] `*`; otherwise no DL1 | `SEGDL1-R-007`, `SEGDL1-R-008` |
| 4 | Same response, Data Block 4 | Card Type `173` → DL6 + `*` | `SEGDL1-R-005`, `SEGDL1-R-012` |
| 5 | Next transaction | Device uses the downloaded configuration | out of scope for message validation |

## Correlation Points

- The Table Load Response has no sequence number; correlation to the request is by session (the device stays online). Test data must therefore keep request and response in one lifecycle record.
- The DL1 Card Type list and DL6 must be in the **same** response object.
- For TCP/IP the `)` indicator appears only before Data Block 1; for dial-up it precedes each block.

## SME Questions

1. `SEGDL1-SME-003`: is End-of-Load `*` sent after DL3 and again after DL6?
2. When the host truncates the response to "a subset of the complete table load containing only dial string information", is DL1 still sent? (11.7 text says the response may be a dial-string-only subset; the Table Load layout marks DL1 `R`.) Keep this case `REVIEW_REQUIRED`.

## Review Checklist

- Does each lifecycle fixture contain the request and the response?
- Is the load flag recorded as test precondition?
- Are TCP/IP and dial framing tested separately?
