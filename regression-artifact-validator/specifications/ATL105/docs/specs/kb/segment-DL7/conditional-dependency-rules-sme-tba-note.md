# Segment DL7 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL7 — Supplemental Terminal Data Segment · **Sources:** 12.48, 13.2 (Elements 84, 232), Appendix W · **Oracle:** [rule catalog](coverage/segment-DL7-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md)

## Core Idea

DL7 has no Conditional fields, but it has two length dependencies that a parser relies on (Lesson L3: catalogue cross-field rules explicitly):

| Dependency | Consequence | Rule |
|---|---|---|
| Segment Length ↔ segment content | Value equals the characters after `^` (own digits counted or not per `SEGDL7-SME-005`) | `SEGDL7-R-002`, `R-006` |
| Table Length ↔ Table Data | Each entry's data is exactly Table Length characters | `SEGDL7-R-004` |
| Entries ↔ Download Data | Entries tile the Download Data exactly | `SEGDL7-R-003` |
| Download Data ↔ 100 bytes | Longer data needs another DL7 (if allowed) | `SEGDL7-R-005`, `SEGDL7-SME-004` |

The earlier note had no dependency rules for DL7.
