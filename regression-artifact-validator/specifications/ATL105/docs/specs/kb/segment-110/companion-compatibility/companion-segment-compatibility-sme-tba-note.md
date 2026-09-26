# Segment 110 Companion and Envelope Compatibility: SME/TBA Learning Note

## Core Rule

Segment 110 is confirmed as a required companion of Segment 100, Segment 111, and Segment 113 inside the Section 11.3.1 ECA/TeleCheck® Service Transaction Request. It is not confirmed as a Data Section 3 companion of the generic Financial Transaction Request (Section 11.1.1), whose Data Section 3 list is Fleet (101), Product Code (102), EBT (103), Purchase Card (104), and Variable Information (111) only.

```text
Check transaction
  -> Section 11.3.1 ECA/TeleCheck request envelope
  -> Data Section 1: Elements 55 and 63
  -> Data Section 2: Segment 100
  -> Data Section 3: Segment 110 (Field 4), Segment 111 (Field 5), Segment 113 (Field 6)
  -> Financial Transaction Response layout (Section 11.1.2, reused per 11.3.2)
```

## Compatibility Checks

| Condition | Expected result |
|---|---|
| ECA/TeleCheck request with Elements 55/63, Segment 100, and Segments 110/111/113 | Continue validation |
| Segment 110 present without Segment 111 or Segment 113 in an ECA/TeleCheck request | `REVIEW_REQUIRED` - confirm whether all three are always mandatory together |
| Segment 110 present in a generic Financial Transaction Request Data Section 3 | `REVIEW_REQUIRED` under `SEG110-SME-001`; not in the confirmed segment list |
| Segment 110 fixture relabeled from a MICR-less financial transaction | Reject or review |

## SME Decision

Confirm whether Segment 110 may appear in any Financial Transaction Request that includes check-related prompt codes, or whether it is strictly confined to the ECA/TeleCheck® Service Transaction Request family described in Section 11.3. Section 12.9's general statement ("can appear in any of the fields in Data Section No. 3") is broader than the two concrete envelope tables the current source extract provides evidence for.
