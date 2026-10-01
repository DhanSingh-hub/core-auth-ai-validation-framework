# Segment DL7 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL7 — Supplemental Terminal Data Segment · **Sources:** 12.48, 11.7.1.2, 13.2 (Element 24) · **Oracle:** [rule catalog](coverage/segment-DL7-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md)

## Core Idea

DL7 is defined but not placed. Element 24 says Data Type Indicators identify data "in a host response", so DL7 is host download data, but no message layout lists it. Applicability is therefore `REVIEW_REQUIRED` in full (`SEGDL7-SME-004`).

## What Can Be Tested Now

- The segment in isolation: `^`, Segment Length, Download Data entries.
- That DL7 is never in a device request.

## What Cannot Be Tested Yet

- Presence or absence in a specific response.
- Position relative to DL1-DL6 or the End-of-Load Indicator.
- Multi-segment Download Data ("all or some").

## SME Questions

1. `SEGDL7-SME-004`: message and position; multi-segment rule.
2. Is DL7 gated by a terminal "Special" like DL8?
