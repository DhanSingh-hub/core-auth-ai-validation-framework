# Terminal Identifier: SME/TBA Learning Note

## Core Idea

Element 102 identifies the device by device type, state code, BUYPASS merchant number, and device number. It is mandatory in Segment 109, but the extracted Segment 109 layout does not state the local format, length, or enabled device population.

```text
Electronic-mail operation
  -> device identity
  -> Terminal Identifier
  -> request authorization and response correlation
```

## SME Questions

1. Which device types may send Electronic Mail Requests?
2. What exact Terminal Identifier grammar applies in this environment?
3. Does the terminal identity restrict retrieval, proprietary retrieval, or submission?
4. Must the response be correlated to the same terminal identity?

## TBA Rule Pattern

```text
BR: An Electronic Mail Request shall identify an enabled device terminal.
TS: Enabled terminal submits an Electronic Mail Request.
TC: Submit a syntactically valid request with a disabled or malformed terminal identifier.
TD: Synthetic terminal identifier, expected result FAIL or REVIEW_REQUIRED according to approved policy.
```

## Current Boundary

The validator requires a nonblank Terminal Identifier. Format and authorization behavior remain `REVIEW_REQUIRED` under `SEG109-SME-005`; do not infer them from financial Segment 100 fixtures.