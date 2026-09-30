# Segment DL4 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** DL4 — Software Dial Load Data Segment · **Sources:** 10.10, 11.7, 11.7.4, 13.2 (Element 30) · **Oracle:** [rule catalog](coverage/segment-DL4-rule-catalog.json) · **Benchmark:** Segment 100 [sequence-lifecycle note](../segment-100/sequence-lifecycle-sme-tba-note.md)

## Core Idea

DL4 is the middle step of an eight-step software update process (10.10). A DL4 fixture without the preceding Download Indicator `1` and the following scheduled load / Table Load is only a structural test.

## Software Update Processing (10.10)

| Step | Actor | Event | Observable in ATL105? |
|---|---|---|---|
| 1 | BUYPASS | Profile bit set: device must request a DLL | No (profile) |
| 2 | BUYPASS | Download Indicator (Element 30) `1` in the next transaction response | Yes |
| 3 | Device | Sends a load request (Table Load per 10.10; Software Load per 11.7.4) | Yes |
| 4 | BUYPASS | Responds with DL4 and DL5 | Yes |
| 5 | Device | Stores Version, Record ID, Phone or IP/URL, Date, Time | No |
| 6 | Device | At the scheduled time, dials the device management system for a full load | No (not BUYPASS) |
| 7 | Device | After the load, requests a Table Load | Yes |
| 8 | BUYPASS | Sends the Table Load without requiring the profile bit | Yes |

Notes: at most three attempts; an unsuccessful attempt prints the "decline" message.

## Correlation Points

- Steps 2, 3, 4, 7 and 8 are ATL105 messages and belong in one lifecycle test-data record.
- The New Software Version in DL4 should become the Software Version (Element 96) the device reports in its step-7 Table Load Request. The source does not state this; treat as `REVIEW_REQUIRED`.

## SME Questions

1. `SEGDL4-SME-002`: Table Load or Software Load request at step 3?
2. Does the step-7 Table Load Request carry the new Software Version?
3. Does "full load" at step 6 apply when Software Load Type is `P`?
