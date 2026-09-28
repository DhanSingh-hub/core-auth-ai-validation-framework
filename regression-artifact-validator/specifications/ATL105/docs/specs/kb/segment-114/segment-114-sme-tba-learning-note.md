# Segment 114 SKU Data Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, Sections 11.1.1, 11.2.1, 11.3.1, 11.9.1, 12.13, and 13.2 Element 149.

## 1. What Segment 114 Means

Segment 114 is the **SKU Data Segment**. It is the smallest and simplest segment trained so far — just 3 fields, versus Segment 108's 15 or Segment 100's 7 core fields. Its only job is to carry bar code SKU data scanned at the point of sale.

Like Segment 108, Segment 114 belongs exclusively to the **Loyalty Card Transaction Request** message family (Section 11.2.1). Unlike Segment 108, Segment 114 is **optional**, not required — it is only included when the device actually scanned a bar code SKU. A useful mental model:

```text
TCP/IP header
  + Data Section 1: ATL105 identity and segment count
  + Data Section 2: Segment 100 standard transaction data
  + Data Section 3: Segment 108 loyalty program data (required)
                  + Segment 114 SKU data (optional — present only if a SKU was scanned)
```

## 2. Segment 114 Layout

| Field | Element | Length | Status | SME meaning |
| --- | ---: | ---: | --- | --- |
| Segment Type | 85 | 3 | Required | Identifies Segment 114 — see the field-order caveat below |
| Segment Length | 84 | 4 | Required | Encoded length of Segment 114, including its separators. **Four digits**, not three — see below. |
| SKU Data | 149 | 1000 | Required (when the segment is present) | The scanned bar code SKU value |

Maximum Segment 114 length is **1010 alphanumeric characters** per Section 12.13's opening statement and its own valid-values range (`001–1010`). **RESOLVED 2026-09-26 (`SEG114-SME-001`)**: 1010 is confirmed authoritative; the Loyalty Card Transaction Request layout table's conflicting "1009" figure (Section 11.2.1) is a spec table typo — the same class of table-vs-body discrepancy already resolved for Segment 108 (142 vs 84).

### Why 4 digits, not 3

Segment 108 uses a 3-digit Segment Length. Segment 114 is one of only **seven segments in the entire ATL105 specification** that require a 4-digit Segment Length: EBT Data Segment (103), **SKU Data Segment (114)**, Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), and EMV Response Data Segment (131). This is confirmed independently in Segment 120's rule catalog and SME note — do not assume Segment 114 uses the more common 3-digit format.

### The Segment Type fixed value

Section 12.14 (Segment 115, Print Data Segment) explicitly prints "Fixed value: 115" for its Segment Type field. Section 12.13 (Segment 114) does **not** print an equivalent "Fixed value: 114" phrase for its Segment Type field — it only says "Identifies the type of data segment being formatted." **RESOLVED 2026-09-26 (`SEG114-SME-004`)**: the validator enforces `SegmentType == 114` as a hard rule regardless of the missing literal citation.

## 3. When Segment 114 Is Required

Segment 114 is **never required** — it is always `Optional` (Entry `O`) in the Loyalty Card Transaction Request's Data Section 3 table. The applicability question is simply: *did the device scan a bar code SKU during this loyalty transaction?* If yes, Segment 114 is populated; if no, it is omitted entirely (there is no documented "present but empty" variant).

Segment 114 is **exclusive** to the Loyalty Card Transaction Request. It is not listed among the Data Section 3 companions of:
- the Financial Transaction Request (Section 11.1.1: only 101, 102, 103, 104, 111),
- the ECA/TeleCheck Service Transaction Request (Section 11.3.1: only 110, 111, 113), or
- the CA Public Key File Load Request (Section 11.9.1: only 101, 102, 104, 111, 132).

`[RESOLVED SEG114-SME-002]` — the AI Solution Team's requirement catalog contains a statement asserting "The Financial Transaction Request transaction includes SKU Data Segment (Data Segment No. 114)" (`REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST`). No specification citation supports this. This was confirmed 2026-09-26 to be an AI-generated error and is rejected from the approved catalog; it should be reported back to the AI Solution Team as a defect.

## 4. POS Flow Example

### Loyalty purchase with a scanned SKU item

```text
Clerk scans items, including at least one bar-coded SKU item
  -> Clerk swipes loyalty card (or keys Street Address + Phone Number, per Segment 108)
  -> Segment 100 carries standard transaction identity/amount
  -> Segment 108 carries Loyalty Program ID, Account Number, Update Code, etc.
  -> Segment 114 carries the scanned SKU value (SkuData)
  -> Response processes the loyalty transaction as usual; Segment 114 does not
     appear in the response (`SEG114-R-013`, confirmed 2026-09-26 `SEG114-SME-003`)
```

### Loyalty purchase with no SKU scan

```text
Clerk performs a loyalty transaction that does not involve a scanned bar-coded item
  -> Segment 100 and Segment 108 populated as usual
  -> Segment 114 omitted entirely (it is optional, not "present but empty")
```

## 5. Validator Rules Planned (`Segment114PayloadValidator`, not yet implemented)

- Segment 114 is optional; its absence is never a failure (`SEG114-R-002`).
- When present, Segment Type equals `114` (`SEG114-R-003`, confirmed enforceable 2026-09-26).
- When present, Segment Length is 4 digits within `0001-1010` (`SEG114-R-004`, `SEG114-R-005`, upper bound confirmed 1010 2026-09-26).
- When present, SKU Data is required, alphanumeric, max 1000 characters (`SEG114-R-008`).
- Segment 114 requires a sibling Segment 108 in the same message (`SEG114-R-011`).
- Segment 114 may repeat, once per scanned SKU — not limited to zero-or-one (`SEG114-R-010`, confirmed 2026-09-26).
- Segment 114 alongside any segment other than 100/108 is `REVIEW_REQUIRED`, not certified (`SEG114-R-001`); a Financial Transaction Request + Segment 114 combination is `REJECTED` outright (`SEG114-R-012`, confirmed 2026-09-26).

## 6. Suggested Segment 114 Test Scenarios

| ID | Scenario | Expected result |
| --- | --- | --- |
| SKU-114-001 | Loyalty Card Transaction Request with valid Segment 114 type, length, and SKU data | Pass |
| SKU-114-002 | Loyalty Card Transaction Request omits Segment 114 entirely | Pass (Segment 114 is optional) |
| SKU-114-003 | Segment 114 present without a sibling Segment 108 | Fail (`SEG114-R-011`) |
| SKU-114-004 | Segment 114 uses invalid type (e.g., `100`) | Fail (`SEG114-R-003`, pending enforceability confirmation) |
| SKU-114-005 | Segment Length is only 3 digits instead of 4 | Fail (`SEG114-R-004`) |
| SKU-114-006 | Segment Length / SKU Data exceeds the documented maximum | Fail (`SEG114-R-005`, boundary confirmed at 1010, 2026-09-26) |
| SKU-114-007 | SKU Data omitted while Segment 114 is present | Fail (`SEG114-R-008`) |
| SKU-114-008 | Second Segment 114 occurrence (different SKU) in one message | Pass (`SEG114-R-010`, confirmed repeatable 2026-09-26) |
| SKU-114-009 | Segment 114 present in a Financial Transaction Request | Fail (`SEG114-R-012`, confirmed rejected 2026-09-26 — AI Solution Team's assertion is an error) |
| SKU-114-010 | Segment 114 present in the Loyalty Card Transaction Response | Fail (`SEG114-R-013`, confirmed request-only 2026-09-26) |

## 7. SME Checklist

When reviewing an AI-generated Segment 114 artifact, ask:

- Does the artifact treat Segment 114 as exclusive to the Loyalty Card Transaction Request, not a Financial Transaction Request companion? (The AI Solution Team's own catalog currently asserts the opposite in one place — flag it.)
- Does it use a 4-digit Segment Length, not 3?
- Does it require a sibling Segment 108 whenever Segment 114 is present?
- Does it avoid inventing a maximum length without flagging the 1010-vs-1009 discrepancy?
- Does it correctly treat Segment 114's absence as valid (optional), not a defect?

## Source References

- Section 11.1.1, Financial Transaction Request (Data Section 3 companion list): lines 7511-7546.
- Section 11.2.1, Loyalty Card Transactions (request message format): lines 7986-8090.
- Section 11.3.1, ECA/TeleCheck Service Transaction Request (Data Section 3 companion list): lines 8100-8140.
- Section 11.9.1, CA Public Key File Load Request (Data Section 3 companion list): lines 10460-10490.
- Section 12.13, SKU Data Segment: lines 12861-12900.
- Section 12.14, Print Data Segment (contrast for the "Fixed value" citation gap): lines 12900-12960.
- Element 149, SKU Data: `13-data-elements.md` row.
- [Segment 114 Rule Catalog](coverage/segment-114-rule-catalog.json).
- [SME/TBA Input Register](segment-114-sme-tba-input-register.md).
- [AI-Generated vs Test-Generated Requirement Comparison](segment-114-ai-vs-test-requirement-comparison.md).
