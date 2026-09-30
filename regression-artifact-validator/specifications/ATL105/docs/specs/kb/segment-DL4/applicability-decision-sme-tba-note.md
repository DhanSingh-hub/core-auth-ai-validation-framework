# Segment DL4 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL4 — Software Dial Load Data Segment · **Sources:** 12.45, 11.7, 11.7.4, 11.7.4.2, 10.10 · **Oracle:** [rule catalog](coverage/segment-DL4-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md)

## Core Idea

DL4 has two independent applicability gates:

1. **Device model** — only devices loaded through the BUYPASS device management system (`SEGDL4-R-001`; 12.45 note, 11.7 note, 10.10 note).
2. **Message** — the Software Load Response, Data Block 1, with DL5 (`SEGDL4-R-006`), gated by merchant load flag `SOFT`.

## Rules

| Rule | Statement | Status |
|---|---|---|
| `SEGDL4-R-001` | BUYPASS-managed devices only | SPEC_DERIVED |
| `SEGDL4-R-006` | Software Load Response, Required with DL5, flag `SOFT` | REVIEW_REQUIRED (P-02) |

## The Placement Conflict (`SEGDL4-SME-002`)

| Source | Request | Response carrying DL4 | Gate |
|---|---|---|---|
| 11.7.4 / 11.7.4.2 | Software Load Request (`?`, TID, `P`, HW/SW/FW versions) | Software Load Response | Load flag `SOFT` |
| 10.10 steps 1-4 | Table Load request after Download Indicator `1` | "responds to the Table Load request by sending" DL4 and DL5 | Profile "DLL" bit |
| Chapter 12 matrix | — | Software Load Response only | — |

Note that the Software Load Request and the Table Load Request have identical layouts (`?`, Terminal Identifier, Load Type `P`, Hardware/Software/Firmware Version). The host distinguishes them by the merchant profile, not by the message. Test data must therefore carry the profile flag.

## SME Questions

1. `SEGDL4-SME-002`: which exchange carries DL4, and is "DLL bit" the same as flag `SOFT`?
2. How does the host know a device is vendor-managed?

## TBA Decomposition

```text
BR:  DL4 shall be sent only to BUYPASS-managed devices (SEGDL4-R-001).
TS:  Vendor-managed device requests a load while a new version exists.
TC-: Response contains DL4. Expected FAIL citing SEGDL4-R-001.
TD:  Context {deviceManagement: "VENDOR"}, response with '@...~'.
```
