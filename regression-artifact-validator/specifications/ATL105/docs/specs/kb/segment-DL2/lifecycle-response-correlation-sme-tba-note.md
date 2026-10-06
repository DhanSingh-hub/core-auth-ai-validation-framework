# Segment DL2 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL2 — Dial String Data Segment · **Sources:** 11.7, 11.7.2, 12.43, 13.2 (Elements 75, 82) · **Oracle:** [rule catalog](coverage/segment-DL2-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

DL2 has two lifecycles: the **load** lifecycle (request → response carrying DL2) and the **dialing** lifecycle it configures (primary → redial → secondary). Only the first is observable in ATL105 messages; the second is device behaviour governed partly by an external document.

## Load Lifecycle

| Step | Message | Assert | Rule |
|---|---|---|---|
| 1 | Phone Load Request | `?` + Terminal Identifier + Load Type `P`; no DL segment | `SEGDL2-R-009` |
| 2 | Phone Load Response | Flag `PHON` → DL2 present; otherwise error + terminating block | `SEGDL2-R-008`, `R-009` |
| 1' | Table Load Request | As DL1 lifecycle | `SEGDL1-R-008` |
| 2' | Table Load Response | DL2 as Data Block 2 when configured | `SEGDL2-R-008` |

## Dialing Lifecycle (in-spec evidence)

- "Transaction dialing always begins with the primary Phone Number." (Element 75)
- "The secondary Phone Number is used once attempts using the primary Phone Number are exhausted." (Element 75; 12.43)
- Redial Count 1-3 bounds the attempts per number (Element 82).
- Error-recovery timing is in the Asynchronous Communications Protocol Specifications (`SEGDL2-SME-001`).

## SME Questions

1. Is Redial Count the number of *re*dials (attempts = N + 1) or the total number of attempts?
2. After the secondary number is exhausted, does the device return to the primary?
3. Does a successful Phone Load reset the `PHON` flag?

Keep dialing-lifecycle fixtures `REVIEW_REQUIRED` until these are answered.
