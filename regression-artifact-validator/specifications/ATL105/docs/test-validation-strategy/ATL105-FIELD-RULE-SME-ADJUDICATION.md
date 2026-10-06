# ATL105 Field-Rule SME Adjudication

The generated `atl105-field-rule-adjudication-queue.json` is read-only intake. Do not edit it to record a decision. A decision applies to one queued element, message family, and segment context; it does not imply applicability to other transaction codes or contexts.

## Decision Input

Pass one JSON file to `FieldRuleSmeDecisionRegister --decision-json`. Copy `subjectId`, `element`, `messageFamily`, `segment`, and `sourceAnchor` from one queue subject. Supply a named reviewer, review rationale, and all four dimensions below.

Each dimension requires a `status`, reviewer rationale, and non-empty `evidence` array. Every citation must identify source `SRC-ATL105-PDF-001`, a PDF page, an exact quote present on that page in `docs/specs/extracted_text.txt`, and a complete ATL105 2026-3 source anchor.

| Dimension | Allowed statuses | Additional rule |
|---|---|---|
| `applicability` | `REQUIRED`, `OPTIONAL`, `CONDITIONAL`, `NOT_APPLICABLE`, `UNRESOLVED` | Required fields cannot permit omission; optional fields must permit it or remain unresolved. |
| `conditionalTrigger` | `NOT_APPLICABLE`, `SOURCE_STATED`, `CONFIGURATION_DEPENDENT`, `UNRESOLVED` | `SOURCE_STATED` requires a `condition`; configuration-dependent decisions also require `configurationKey`. |
| `permittedOmission` | `PERMITTED`, `NOT_PERMITTED`, `CONDITION_DEPENDENT`, `UNRESOLVED` | `CONDITION_DEPENDENT` requires a `condition`. |
| `crossSegmentDependency` | `NONE`, `REQUIRES`, `CONDITION_DEPENDENT`, `MUTUALLY_CONSTRAINED`, `UNRESOLVED` | Non-`NONE` resolved decisions require a relation and queued related-context IDs in a different segment. |

Example shape (replace all example values with the actual queue context and source evidence):

```json
{
  "subjectType": "FIELD_RULE_CONTEXT",
  "subjectId": "FRC-REPLACE-WITH-QUEUE-ID",
  "element": "41",
  "messageFamily": "Financial Transaction Request",
  "segment": "100",
  "reviewer": "reviewer@example.test",
  "rationale": "Explain the independent source interpretation and scope.",
  "sourceAnchor": {
    "specification": "ATL105",
    "version": "2026-3",
    "section": "11.1.1",
    "segment": "100",
    "element": "41",
    "rule": "copy exactly from the queue subject"
  },
  "fieldRuleDecision": {
    "applicability": {
      "status": "CONDITIONAL",
      "condition": "State the precise transaction condition.",
      "rationale": "Why the source supports this applicability decision.",
      "evidence": [
        {
          "sourceId": "SRC-ATL105-PDF-001",
          "sourcePage": 164,
          "quote": "Exact text copied from this page.",
          "sourceAnchor": {
            "specification": "ATL105",
            "version": "2026-3",
            "section": "11.1.1",
            "segment": "100",
            "element": "41",
            "rule": "source rule label"
          }
        }
      ]
    },
    "conditionalTrigger": {
      "status": "SOURCE_STATED",
      "condition": "State the exact trigger.",
      "rationale": "Why the cited text establishes this trigger.",
      "evidence": []
    },
    "permittedOmission": {
      "status": "CONDITION_DEPENDENT",
      "condition": "State when omission is permitted.",
      "rationale": "Explain the omission rule.",
      "evidence": []
    },
    "crossSegmentDependency": {
      "status": "REQUIRES",
      "relation": "Describe the required companion segment relationship.",
      "relatedContextSubjectIds": ["FRC-REPLACE-WITH-RELATED-QUEUE-ID"],
      "rationale": "Explain the dependency scope.",
      "evidence": []
    }
  }
}
```

For every dimension, replace the empty evidence array with its own source citation. A context becomes `RULE_CONFIRMED` only when no dimension is `UNRESOLVED`; otherwise the event is retained as `REVIEW_REQUIRED`. Decisions are append-only and supersede prior events for the same subject.

From the `regression-artifact-validator` module, validate the queue and register without changing them:

```powershell
mvn.cmd org.codehaus.mojo:exec-maven-plugin:3.5.0:java '-Dexec.mainClass=com.coreauth.validator.coverage.FieldRuleSmeDecisionRegister' '-Dexec.args=--validate'
```

Record a reviewer-authored input file with `-Dexec.args=--decision-json path/to/decision.json` using the same main class. The decision register is written to `specifications/ATL105/test-output/test-solution-independent-review/atl105-field-rule-sme-decision-register.json`; the queue remains unchanged. A confirmed context is only one approved family/segment context; whole-message execution remains blocked until transaction-code applicability and all required context decisions are resolved.
