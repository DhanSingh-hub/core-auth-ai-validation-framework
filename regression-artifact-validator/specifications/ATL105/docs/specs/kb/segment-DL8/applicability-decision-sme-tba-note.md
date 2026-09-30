# Segment DL8 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment · **Sources:** 12.49, 11.7.1.2, 13.2 (Element 24) · **Oracle:** [rule catalog](coverage/segment-DL8-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md)

## Core Idea

DL8 has a clear gate (terminal-level Special) but no defined position. 12.49 places it "in a table load"; the Table Load Response layout does not list it. Applicability can be tested as "Special → DL8 present, no Special → DL8 absent"; position cannot yet.

## Rules

| Rule | Statement | Status |
|---|---|---|
| `SEGDL8-R-001` | Included in a table load only for terminals with the Special | REVIEW_REQUIRED (P-02) |

## Comparison With Other Gates

| Segment | Gate | Kind |
|---|---|---|
| DL1 | Load flag `TABL` | Merchant profile |
| DL4/DL5 | Load flag `SOFT`, BUYPASS-managed device | Merchant profile + device model |
| DL6 | DL1 Card Type `173` | Another segment's value |
| DL8 | Terminal-level Special | Terminal profile |

## SME Questions

1. `SEGDL8-SME-002`: position, Special name, Segment Length source.
2. Is DL8 sent in every table load for a Special terminal, or only after a floor-limit change?
