# Segment 109 Companion and Envelope Compatibility: SME/TBA Learning Note

## Core Rule

Segment 109 belongs to the Section 11.5 Electronic Mail Request message family. It is not a Data Section 3 companion of Segment 100, and a Segment 100 financial request is not valid proof of Segment 109 behavior.

```text
Electronic Mail operation
  -> Section 11.5 request envelope
  -> Data Section 1: Elements 55 and 63
  -> Data Section 2: Segment 109
  -> Electronic Mail Response
```

## Compatibility Checks

| Condition | Expected result |
|---|---|
| Electronic Mail Request with Elements 55/63 and Segment 109 | Continue validation |
| Segment 109 absent from declared electronic-mail request | Reject |
| Financial Segment 100 fixture relabeled as electronic mail | Reject or review |
| Segment 109 used as a Section 3 companion | Reject or review |
| Additional segment/extension claimed without source evidence | `REVIEW_REQUIRED` |

## SME Decision

Confirm whether any product-specific extension permits other segments in an Electronic Mail Request. ATL105 Section 11.5 establishes the basic two-section envelope but the current source extract does not certify local extensions.