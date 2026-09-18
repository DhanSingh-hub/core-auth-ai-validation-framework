# Segment 101 Companion-Segment Compatibility: SME and TBA Learning Note

## Purpose

Segment 101 (Fleet Data Segment) is a Data Section 3 companion of Segment 100 in an ATL105 Financial Transaction Request. Its compatibility rules define which Section 3 segments may coexist with it, which are mandatory, and which are forbidden. This note teaches the SME and TBA how to reason about that decision and convert it into independently testable rules.

This is a learning and validation note, not a business approval record.

## Core Mental Model

```text
Fleet-card transaction intent
  -> Segment 100 (always required)
  -> Segment 101 (required for fleet flows)
  -> other Section 3 companions triggered by feature context
  -> Segment 145 (Enhanced Fleet) explicitly forbidden alongside Segment 101
  -> Element 63 = number of serialized segments
  -> serialized message validation
```

Segment 101 must not be treated as sufficient by itself. A fleet purchase can be valid JSON and still be invalid because Segment 100 is missing, because Segment 145 is coexisting, or because a feature-driven companion (Segment 102 for fuel, Segment 130 for EMV) is absent.

## SME Questions

Before defining the expected segment set for a Segment 101 request:

1. Is the transaction actually a fleet-card transaction?
2. Is Segment 100 present exactly once?
3. Is Segment 145 (Enhanced Fleet) present? If yes, either drop Segment 101 or drop Segment 145 — they must not coexist.
4. Is the transaction EMV? If yes, add Segment 130.
5. Is the transaction fuel/product? If yes, add Segment 102.
6. Is the transaction EBT/purchase-card/variable-info/NFC/Moneris? Add the corresponding segment.
7. Is the message an Auth Completion (0220)? If yes, are Host Prompts supported so Fleet Tag fields 14..18 can be populated?

## Compatibility Baseline for Segment 101

| Business condition | Segment 100 | Segment 101 | Other companions | Validation disposition |
| --- | :---: | :---: | --- | --- |
| Non-fleet transaction | Required | Not present | Per-feature | Segment 101 must not appear |
| Fleet-only purchase | Required | Required once | None | Baseline fleet flow |
| Fleet purchase with fuel/product data | Required | Required once | + Segment 102 | Fleet + fuel companion |
| Fleet purchase with EMV | Required | Required once | + Segment 130 | Fleet + EMV companion |
| Fleet purchase with EBT | Required | Required once | + Segment 103 | Fleet + EBT companion |
| Fleet purchase with purchase-card | Required | Required once | + Segment 104 | Fleet + purchase-card |
| Fleet purchase with variable information | Required | Required once | + Segment 111 | Fleet + variable info |
| Fleet purchase with NFC tokenization | Required | Required once | + Segment 123 | Fleet + NFC |
| Fleet purchase with Moneris authorizer | Required | Required once | + Segment 135 | Fleet + Moneris |
| Fleet Auth Completion 0220 with Host Prompts | Required | Required once with Fleet Tags 1..5 | Feature-driven | Auth-Completion-only Fleet Tags |
| Enhanced fleet flow | Required | **Forbidden** | + Segment 145 | Segment 145 replaces Segment 101 |
| Multiple conditions apply | Required | Required once | Every applicable companion | Validate the complete set |
| Source does not define the combination | Required | Required once | Unknown | `REVIEW_REQUIRED`, not auto-approved |

Segment 101 and Segment 145 are the only strict mutual exclusion inside Section 3 that involves Segment 101.

## Element 63 Rule

Element 63, Number of Segments, must equal the number of segments actually serialized in the request.

Examples with Segment 101:

```text
Segment 100 + 101                    -> Element 63 = 02
Segment 100 + 101 + 102              -> Element 63 = 03
Segment 100 + 101 + 130              -> Element 63 = 03
Segment 100 + 145                    -> Element 63 = 02 (fleet enhanced flow, no 101)
Segment 100 + 101 + 145              -> INVALID (mutual exclusion)
```

Do not count business conditions or JSON objects. Count the final serialized segments.

## TBA Rule Decomposition

Turn one compatibility decision into atomic artifacts:

```text
BR:
  A fleet-card Financial Transaction Request shall contain Segment 100 and
  Segment 101, and shall not contain Segment 145.

TS:
  Fleet purchase carrying Segment 100 + Segment 101 only.

TC-Positive:
  Submit Segment 100 + Segment 101 with Element 63 = 02.
  Expected result: PASS.

TC-Negative-1:
  Omit Segment 100 while retaining Segment 101.
  Expected result: FAIL.

TC-Negative-2:
  Include Segment 100 + Segment 101 + Segment 145.
  Expected result: FAIL (mutual exclusion).

TD:
  Test-data record contains fleet-card context, Segment 100, Segment 101, no Segment 145,
  and expected result PASS or FAIL as above.
```

## Negative and Boundary Cases

For each Segment 101 compatibility rule, create at least:

- Required companion (Segment 100) present, Segment 101 present, correct count: pass.
- Segment 100 missing: fail.
- Segment 101 duplicated: fail (Rule SEG101-R-026).
- Segment 145 added alongside Segment 101: fail (Rule SEG101-R-003).
- Feature-driven companion (Segment 102 for fuel) missing when required: fail.
- Element 63 too low: fail.
- Unexpected non-fleet transaction with Segment 101 included: fail or review according to source evidence.

## What Not To Assume

- Do not assume fleet transactions always trigger Segment 102 — fuel is a separate condition.
- Do not treat Segment 145 as an "upgrade" of Segment 101; the two are mutually exclusive.
- Do not treat "Segment 101 and other Section 3 segments" combinations as automatically approved for unmapped combinations — flag as `REVIEW_REQUIRED`.

## Related Rules

- `SEG101-R-001` — Segment 101 is a Section 3 companion of Segment 100.
- `SEG101-R-002` — Segment 101 is required in every fleet-card financial transaction request.
- `SEG101-R-003` — Segment 101 must not coexist with Segment 145.
- `SEG101-R-026` — Segment 101 appears exactly once per message.
- Segment 100 compatibility rules SEG100-R-003 (fleet data requires Segment 101) and SEG100-R-007 (Element 63 = serialized count).
