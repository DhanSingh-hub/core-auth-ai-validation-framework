# ATL105 Segment 100 Demo Runbook

## Purpose

Demonstrate how the Core Auth Regression Test Solution validates AI-generated Business Requirements, Test Scenarios, Test Cases, and JSON Test Data while proving the reusable any-specification architecture.

## Demo Message

> ATL105 is the current specification pack. Segment 100 is the first completed module. The same Core Auth Test Solution will generate and validate canonical artifacts for every future specification through a specification pack.

## Demo Inputs

```text
test-input/ai-solution/
  manifest.json
  business-requirements/
  test-scenarios/
  test-cases/
  test-data/
  metadata/
  traceability/
  schemas/
```

Raw sample inputs currently available:

```text
test-input/ai-solution/test-data/Sale.json
test-input/ai-solution/test-data/Void.json
```

## Walkthrough

### 1. Show AI output

Open `Sale.json` and `Void.json`. Explain that they are valid ATL105-style messages but do not yet contain the canonical artifact metadata required for full traceability.

Ask the audience to identify:

- Message category.
- Segment count.
- Segment types.
- Prompt Code.
- Sequence Number.
- Approval Number.
- EMV and Variable Information segments.

### 2. Show normalization gap

Explain that raw message JSON is not automatically a complete Test Data artifact. The Test Solution needs:

- Test Data ID.
- Test Case ID.
- Scenario ID.
- Expected validation outcome.
- Canonical source anchors.
- Lifecycle relationship metadata.
- Synthetic-data confirmation.

Expected result for the raw samples before clarification:

```text
JSON syntax: PASS
ATL105 structure: PASS with review items
Canonical traceability: HOLD
Lifecycle correlation: NOT PROVEN
```

### 3. Show canonical artifact model

```text
Rule catalog
  -> Business Requirement
      -> Test Scenario
          -> Test Case
              -> Test Data payload
```

Explain that AI local IDs may differ from Test Solution IDs. Canonical source anchors provide the shared meaning.

### 4. Show Segment 100 validation

Use a standard financial request with:

```text
Segment 100
Segment 111
Segment 130
```

Demonstrate validation of:

- Exactly one Segment 100.
- Element 55 = `ATL105`.
- Element 63 = actual serialized segment count.
- Segment 100 type and length.
- Segment 100 field order.
- Terminal Identifier.
- Prompt Code transaction/card context.
- Account Number entry method.
- Sequence Number.
- Partial Approval Indicator.
- Separators and trailing optional fields.

### 5. Show related-segment boundary

For this demo, Segment 111 and Segment 130 are validated at the Segment 100 compatibility boundary:

- Required segment is present.
- Segment count includes it.
- Segment order is checked where known.
- Segment length representation is checked where available.
- Unknown internal rules are held for manual review or delegated to the future Segment 111/130 modules.

### 6. Show lifecycle validation

Compare Sale and Void:

```text
Sale: Prompt Code 0020
Void: Prompt Code 8020
```

Show that the Test Solution asks:

- Are these related messages?
- Does Void reuse the original Sequence Number?
- Is Approval Number consistent?
- Is account and amount context consistent?
- Is the relationship explicitly declared or only inferred?

An unexplained mismatch produces a deterministic failure or manual-review item.

### 7. Show final report

Open:

```text
test-output/traceability-matrix/segment-100/segment-100-coverage.json
test-output/traceability-matrix/segment-100/segment-100-coverage.md
```

Explain:

```text
COVERED
PARTIALLY_COVERED
REVIEW_REQUIRED
MISSING
```

Unknown segment combinations are held for manual review and are not auto-approved.

## Expected Demo Outcome

The audience should see that the Test Solution can:

1. Accept AI JSON in an approved format or identify a format gap.
2. Normalize AI output into a canonical artifact model.
3. Generate or validate BR, Scenario, Test Case, and Test Data relationships.
4. Validate ATL105 semantics independently of AI claims.
5. Identify missing, inconsistent, or ambiguous evidence.
6. Produce auditable reports.
7. Reuse the same core architecture for future specifications.

## Closing Statement

The demonstration is not only a Segment 100 validator demo. It is an example of the Core Auth Regression Test Solution operating with an ATL105 specification pack. The same flow will apply to every future specification after its rule catalog, schemas, dependencies, lifecycle rules, and coverage profile are supplied.
