# Segment 100 Companion-Segment Compatibility: SME and TBA Learning Note

## Purpose

Segment 100 is the standard core of a standard ATL105 Financial Transaction Request. The transaction context determines whether additional Section 3 segments are required. This note teaches the SME and TBA how to reason about that decision and convert it into independently testable rules.

This is a learning and validation note, not a business approval record.

## Core Mental Model

```text
Transaction intent
  -> card/POS and feature context
  -> Segment 100 Prompt Code
  -> companion-segment conditions
  -> expected segment set
  -> Element 63 count
  -> serialized message validation
```

Segment 100 must not be treated as the entire message. A request can be valid JSON and still be invalid because a required companion segment is absent, duplicated, misordered, or inconsistent with the declared segment count.

## SME Questions

Before defining the expected segment set, establish:

1. What transaction did the POS initiate?
2. What card or account-entry method was used?
3. Is the flow EMV, NFC, fleet, fuel/product, EBT, purchase-card, variable-information, or Moneris-specific?
4. Does the merchant or authorizer configuration introduce another condition?
5. Is this an initial request or a lifecycle follow-up?
6. Which companion segments are required, conditional, optional, unknown, or prohibited by the source?
7. What should Element 63 contain after all segments are serialized?

## Compatibility Baseline

| Business condition | Expected companion segment | Segment 100 role | Validation disposition |
| --- | ---: | --- | --- |
| Standard non-specialized transaction | None | Required exactly once | Validate Segment 100 baseline |
| EMV transaction | `130` | Required exactly once | Missing `130` is an error |
| Fleet data required | `101` | Required exactly once | Missing `101` is an error |
| Product or fuel data required | `102` | Required exactly once | Missing `102` is an error |
| EBT data required | `103` | Required exactly once | Missing `103` is an error |
| Purchase-card data required | `104` | Required exactly once | Missing `104` is an error |
| Variable information required | `111` | Required exactly once | Missing `111` is an error |
| NFC tokenized transaction | `123` | Required exactly once | Missing `123` is an error |
| Moneris authorizer destination | `135` | Required exactly once | Missing `135` is an error |
| Multiple conditions apply | Every applicable companion | Required exactly once | Validate the complete set |
| Source does not define the combination | Unknown | Required exactly once | `REVIEW_REQUIRED`, not auto-approved |

The matrix expresses conditions, not universal transaction permissions. A `C` or review result means the source or another independent condition must be evaluated.

## Element 63 Rule

Element 63, Number of Segments, must equal the number of segments actually serialized in the request.

Examples:

```text
Segment 100 only       -> Element 63 = 01
Segment 100 + 130      -> Element 63 = 02
Segment 100 + 101 + 130 -> Element 63 = 03
```

Do not count business conditions, JSON objects, or configured segment templates. Count the final serialized segments.

## TBA Rule Decomposition

Turn one compatibility decision into atomic artifacts:

```text
BR:
  An EMV Financial Transaction Request shall contain Segment 130 in addition
  to the required Segment 100.

TS:
  EMV purchase with Segment 100 and Segment 130.

TC-Positive:
  Submit both required segments and Element 63 = 02.
  Expected result: PASS.

TC-Negative:
  Omit Segment 130 while retaining the EMV context.
  Expected result: FAIL.

TD:
  Test-data record contains EMV context, Segment 100, no Segment 130,
  and expected result FAIL.
```

## Negative and Boundary Cases

For each compatibility rule, create at least:

- Required companion present, correct count: pass.
- Required companion missing: fail.
- Required companion duplicated: fail.
- Unexpected companion included: fail or review according to source evidence.
- Element 63 too low: fail.
- Element 63 too high: fail.
- Multiple applicable companions present: validate all required segments and order.
- Unknown combination: review, never silent pass.

## Common Analysis Errors

- Assuming transaction type alone determines the segment set.
- Treating Segment 100 as sufficient for EMV, fleet, or other specialized flows.
- Counting JSON objects instead of serialized segments.
- Accepting a metadata flag without the required segment in the payload.
- Treating an unresolved compatibility row as valid by default.
- Copying the AI team's segment decision without independent source evidence.
- Validating presence but not duplicate occurrence or ordering.

## Training Exercise

Determine the expected segment set and Element 63 value:

| Case | Context | Expected outcome |
| --- | --- | --- |
| A | Standard retail request, no specialized data | Segment `100`; count `01` |
| B | EMV purchase with chip data | Segments `100, 130`; count `02` |
| C | Fleet request with fleet data | Segments `100, 101`; count `02` |
| D | EMV request with Segment 130 omitted | Fail; missing required companion |
| E | EMV request with `100, 130`, but count `01` | Fail; count mismatch |
| F | EMV + fleet request where both conditions apply | Segments `100, 101, 130`; count `03`, subject to order rules |
| G | Combination not defined by the source | `REVIEW_REQUIRED` |

## Covered Training So Far

The Segment 100 learning path has covered:

1. Segment 100's role as the standard transaction core.
2. The distinction between TCP/IP header, Data Section 1, Segment 100, and Section 3.
3. Segment 100's ordered fields and required, conditional, and optional behavior.
4. Prompt Code as transaction and card/program context, not merely a string pattern.
5. SME/TBA decomposition from POS intent to BR, TS, TC, and TD.
6. Companion-segment compatibility and Element 63 serialized counting.

Next topics are serialization/separator behavior, lifecycle correlation, and independent comparison of AI artifacts against the Test Team rule catalog.
