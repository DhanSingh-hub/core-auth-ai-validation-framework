# BA business-context format: sample and validation process

## Purpose and authority

The BA/SME diagram defines business navigation, not correct transaction content or
an eligibility policy. Independently verify the content and update/reuse the Test
Solution's existing BR/TS/TC/TD records before assessing AI artefacts.
The [canonical contract](canonical-artifact-contract.md) remains authoritative for
canonical traceability and execution gates.

This pilot demonstrates one independently sourced field requirement with concrete
logical cases and data. It does not complete the network-first review package.

## Runnable sample

- [New-format BR/TS/TC/TD JSON](../test-output/test-solution-independent-review/new-format-sequence-sample-v1/sample-br-ts-tc-td.json)
- [Local validation report](../test-output/test-solution-independent-review/new-format-sequence-sample-v1/validation-report.json)
- [Normalized review envelope](../test-output/test-solution-independent-review/new-format-sequence-sample-v1/normalized-review-envelope.json)
- [Network-first review and outstanding gaps](../test-output/test-solution-independent-review/all-network-requirements-review-v1/NETWORK-REVIEW.md)

### Business context

| Dimension | Sample value |
|---|---|
| Platform | BUYPASS |
| Specification / version | ATL105 / 2026-3 |
| Card program | Credit |
| Commercial usage | Unresolved, not assumed consumer or commercial |
| Network | Mastercard, declared review context only |
| Transaction category | Sale |
| Operation / code | Single-step original purchase / `0` |
| Candidate financial card code | `020`, Credit; does not prove Mastercard identity |
| Card presence | Present |
| Entry mode | Contact chip |
| Channel | POS |
| Eligibility | REVIEW_REQUIRED |
| Payload scope | Logical Element 86 observation only; no EMV/cryptographic data or full message |

Presence, entry mode and channel are separate fields. Commercial usage is
orthogonal to credit/debit/prepaid funding. Generic Debit and EBT must not be
treated as named network brands. ATL104, ISO and Gen2 are not enabled here.

### BR -> TS -> TC -> TD

**BR `SAMPLE-BR-SEQ`:** In the scoped financial request, Sequence Number
(Element 86) must contain exactly six ASCII digits with value `000001` through
`999999`. This is a shared financial-field rule demonstrated in a Mastercard
context, not a newly discovered Mastercard-exclusive requirement.

Independent evidence: [Element 86 source](specs/extracted_text.txt), including
its fixed-six-digit representation and valid range. The checker locates Element
86 independently, verifies the width/range quotes within that element and records
the current source hash. This establishes provenance, not semantic approval.
The separate CAPK file-load restriction is outside the financial sample.

| Scenario | Test case | Synthetic TD | Element 86 observation | Expected local field result |
|---|---|---|---|---|
| `SAMPLE-TS-VALID` | `SAMPLE-TC-MIN` | `SAMPLE-TD-MIN` | `"000001"` | PASS |
| `SAMPLE-TS-VALID` | `SAMPLE-TC-MAX` | `SAMPLE-TD-MAX` | `"999999"` | PASS |
| `SAMPLE-TS-INVALID` | `SAMPLE-TC-ZERO` | `SAMPLE-TD-ZERO` | `"000000"` | FAIL |
| `SAMPLE-TS-INVALID` | `SAMPLE-TC-WIDTH` | `SAMPLE-TD-WIDTH` | `"12345"` | FAIL |
| `SAMPLE-TS-INVALID` | `SAMPLE-TC-NONNUMERIC` | `SAMPLE-TD-NONNUMERIC` | `"12A456"` | FAIL |
| `SAMPLE-TS-INVALID` | `SAMPLE-TC-MISSING` | `SAMPLE-TD-MISSING` | Field absent | FAIL |

Each artefact includes its producer-local ID, business context, source anchors
and draft status. Scenarios link to the BR; cases link to scenarios and include
steps and BR-linked assertions; TDs link to cases and contain actual synthetic
logical payloads, not `"..."` placeholders.

Example positive TD payload:

```json
{
  "standardSegment": {
    "sequenceNumber": "000001"
  }
}
```

The missing-field TD has `"standardSegment": {}` intentionally. Every TD has
`response: null` and `readiness: LOGICAL_HARNESS_ONLY`.
These are **1 BR, 2 TS, 6 TC and 6 TD** in a demonstration package. Do not add
their counts to canonical coverage or mistake them for six complete financial
messages. Local field validation is executed; host transactions are not.

## Run from the repository root

```powershell
.\regression-artifact-validator\framework.ps1 -Task new-format-sample-build
.\regression-artifact-validator\framework.ps1 -Task new-format-sample-check
.\regression-artifact-validator\framework.ps1 -Task new-format-sample-test
```

The build publishes the example, normalized review envelope and report.
The check is read-only and fails stale/missing outputs. The focused tests include
wrong expectations, mutated payloads, malformed links, unsupported claims/contexts,
ASCII/range/type boundaries and preservation of original AI input bytes.

For a new AI delivery expressed in the same narrow pilot format:

```powershell
python.exe .\regression-artifact-validator\scripts\new-format-sample.py `
  --input 'C:\review\ai-new-format.json' `
  --output 'C:\review\new-format-assessment-001'
```

The output directory must not already exist. The input is never changed.
`input-snapshot.json` preserves its original bytes, including any BOM; the report
records their SHA-256 along with validator/helper hashes and independent source
evidence. Producer-local IDs may differ from sample IDs.
Duplicate JSON keys and non-JSON numeric constants are rejected.

CLI exits:

- `0`: all implemented local field expectations agree; overall review is still required.
- `1`: malformed graph/schema/evidence or an implemented expectation mismatch.
- `2`: supported structure but claim/context/assertion is not implemented (or CLI usage error).

This is a bounded review adapter, **not** a universal BA spreadsheet/image parser
or Java canonical execution adapter. Unsupported payload fields are rejected to
prevent unexamined whole-message content being presented as validated.

## Validation process for new-format AI artefacts

| Gate | Process | Current pilot support |
|---|---|---|
| 1. Freeze intake | Preserve original AI delivery, producer IDs and input hashes; never use AI expected results as the oracle. | Implemented for one JSON input; byte snapshot and hashes |
| 2. Validate structure | Check schema/version, unique IDs, business dimensions, BR/TS/TC/TD links, assertion coverage, anchor continuity, missing/orphan artefacts. | Implemented for the pilot envelope |
| 3. Validate context | Keep seven named networks separate from generic Debit/EBT; validate program, commercial usage, presence, entry, channel and flow roles independently. | Dimension checks implemented; real identity/eligibility remains REVIEW_REQUIRED |
| 4. Reconcile Test knowledge | Map source anchors to independent rules; reuse/update existing Test artefacts, add only justified gaps; review conditions/exceptions and prose meaning. | One independent Element 86 structured predicate; prose equivalence NOT_ASSESSED |
| 5. Compare declared claim | Check the entire implemented typed claim, conditions and exception boundary against the independent profile, not prose similarity. | Exact structured match for the scoped six-digit/range rule only |
| 6. Execute bounded assertions | Run independent predicates on concrete TDs; compare observed field result with each declared TC/TD expectation. | Implemented for Element 86; invalid TD can correctly produce expected FAIL |
| 7. Validate message and lifecycle | Check exact operation/message family, allowed card/network combination, companion fields, ordered legs, correlation, amount/response dependencies. | NOT_ASSESSED beyond the sample's declared single-step profile; existing source-backed validators must be integrated |
| 8. Validate serialization and outcome | Validate full wire bytes, converter provenance and request-bound independent host/simulator outcomes; do not infer decline from field failure. | NOT_ASSESSED |
| 9. Review and promote | Persist authorized decisions and genuine Test artefact updates; report confirmed coverage only after applicable gates. | No automatic promotion |

An AI BR claiming sequence `000000` is valid does not change the independent
minimum: the changed structured claim is unsupported and remains NOT_ASSESSED
for semantic review. An AI TC expecting PASS for `"000000"` under the correct
supported claim produces a concrete local expectation mismatch (FAIL).

Result dimensions are reported separately:

- `structuralTraceability`
- `supportedStructuredClaim`
- `testExpectationAgreement`
- `semanticProseEquivalence`
- `networkEligibility`
- `completeMessageAndWire`
- `hostOutcome`

A local PASS never means source interpretation approved, network accepted,
issuer approved, settlement completed or coverage certified. The overall pilot
result remains REVIEW_REQUIRED, including when all six local expectations pass.

## Next integration boundary

This sample adds executable evidence only for the pilot field rule; it does not
fill the network review's missing concrete case/data ledger. Before broad intake,
agree the AI delivery schema, add the remaining independent rule predicates and
context-aware canonical adapters, author complete source-backed cases/fixtures,
then run existing Java canonical/semantic gates and converter/host checks.
