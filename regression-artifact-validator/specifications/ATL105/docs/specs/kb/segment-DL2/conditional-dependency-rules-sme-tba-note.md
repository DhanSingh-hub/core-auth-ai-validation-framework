# Segment DL2 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL2 — Dial String Data Segment · **Sources:** 12.43, 13.2 (Elements 1, 66, 75, 82) · **Oracle:** [rule catalog](coverage/segment-DL2-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md)

## Core Idea

DL2 has four Conditional fields (Access Code and Pause Indicator in each block). Element 1 states the condition: "If Access Code information is not necessary, the dial string contains neither this element nor the Pause Indicator that immediately follows it." The two fields are a pair.

## Dependencies

| Dependency | Trigger | Consequence | Rule |
|---|---|---|---|
| Access Code ↔ Pause Indicator | Access Code needed | Access Code then `B`; otherwise neither | `SEGDL2-R-005` |
| Access Code pauses | Slow dial tone | One or more `B` inside the Access Code, 1 second each | `SEGDL2-R-005` |
| Redial Count → dialing | Redial Count N (1-3) | Primary redialed up to N times before secondary | `SEGDL2-R-003`, `R-006` |
| Primary → secondary | Primary attempts exhausted | Secondary number dialed | `SEGDL2-R-003` |
| Terminator order | Block position | `A` closes primary, `F` closes secondary | `SEGDL2-R-007` |

The previous version of this note recorded "no conditional rules". That was a gap: the Access Code / Pause Indicator pairing is stated in Element 1 and is now `SEGDL2-R-005`.

## SME Questions

1. Can the primary block have an Access Code while the secondary does not? (Nothing forbids it; treat as valid.)
2. Can an Access Code consist only of `B` characters (pure pause)? Element 1 suggests yes.
3. What is the parse rule when the Access Code contains `B`? (`SEGDL2-SME-004`)

## TBA Decomposition

```text
BR:  Access Code and Pause Indicator shall be present together or not at all (SEGDL2-R-005).
TS:  PBX merchant dialing '9' before the phone number.
TC+: Primary '3' '9' 'B' '5555550100' 'A'. Expected PASS.
TC+: Primary '3' '5555550100' 'A' (no access code). Expected PASS.
TC-: Structured primary {redialCount: 3, accessCode: '9', pauseIndicator: absent, phoneNumber: '5555550100'}.
     Expected FAIL citing SEGDL2-R-005. Do not assert this as serialized bytes until SEGDL2-SME-004 resolves field-boundary parsing.
```
