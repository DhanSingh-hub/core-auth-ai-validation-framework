# Segment DL8 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment · **Sources:** 12.49, 13.2 (Elements 84, 234, 235) · **Oracle:** [rule catalog](coverage/segment-DL8-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md)

## Core Idea

DL8 has no Conditional fields; its dependencies are structural (length ↔ groups) and semantic (stand-in ↔ floor limit).

| Dependency | Consequence | Rule / status |
|---|---|---|
| Segment Length ↔ groups | Content is a whole number (1-24) of 26-byte groups | `SEGDL8-R-003`, `R-005` |
| Stand-in `1` ↔ Floor Limit | No offline approval expected; non-zero limit is a question | `SEGDL8-SME-003` |
| RID uniqueness | One group per RID ("repeated — by RID") | Derived, `REVIEW_REQUIRED` |
| Special ↔ DL8 | DL8 only for Special terminals | `SEGDL8-R-001` |

The earlier note had "no conditional rules"; the group-count dependency was only implied.
