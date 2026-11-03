# Client demo guide — ATL105 traceability samples

## Demo purpose

This folder contains **10 single-leg samples and 10 multi-leg transaction flows** from the AI Solution run in the sibling `181026 Run` folder. It is a small, navigable demonstration of how the output connects:

**Business Requirement (BR) → Test Scenario (TS) → Test Case (TC) → Test Data (TD)**

The sample contains **24 distinct BRs, 20 scenarios, 20 test cases, and 33 physical test-data legs**. The selected records are a subset chosen for a complete trace in the run's matrix; this is not a claim that the complete ATL105 requirement set is covered.

## Important status and data caveats

- The source run identifies ATL105 **2026-3** as its specification version. Do not present this package as reconciled to later releases.
- `FULLY_TRACED` and test-case `PASS` are the statuses recorded by the AI run's traceability pipeline. They do **not** mean that the cases passed independent Test Solution review, were executed against a host, or were approved for production.
- The full run reports 71.66% requirement coverage. These 20 examples were deliberately selected from fully traced matrix rows, so their coverage is not representative of the whole run.
- All 20 selected test cases are marked LOW confidence. The original case records retain their confidence, flags, and verification details so they can be discussed rather than hidden.
- Nine single-leg cases are negative scenarios; the tenth is a business-rule candidate whose BR does not establish cancellation-specific behavior. The multi-leg cases are flow scenarios. Do not present these as approved happy-path coverage.
- In single-leg rows, `transactionType` means the business operation decoded from Prompt Code; `messageFormatType` separately names the ATL105 message family. For multi-leg rows, `transactionType` remains the flow sequence and the data's message family is recorded separately.
- The source run does **not** provide independent standalone TCs for every requested operation. The single-leg set contains Prompt Code type `0` cases (POS Purchase/Capture; the same code may also represent preauthorized completion) and one Prompt Code `S` candidate. The candidate's linked BR concerns a follow-on transaction after an EMV response-S, not cancellation behavior, so its semantic alignment needs SME review.
- Authorization (`3`), merchandise return/refund (`7`), purchase reversal/void (`8`), void-of-return (`U`), and timeout reversal occur only as legs of flow TCs in this run. They remain in the multi-leg section rather than being presented as standalone TCs. The Prompt Code `0` single-leg samples do not establish a completion lifecycle.
- Sensitive-looking values in the copied test-case and test-data records are masked with `X` characters while preserving string length. These are **presentation copies**, not the original data and not executable fixtures. Do not use them for testing or infer validity from the masked values.
- The run's own case records may include unresolved or provisional constraints. Keep those flags visible during the demo.

## Folder map

| Path | What to open |
|---|---|
| [MANIFEST.csv](MANIFEST.csv) | Quick index of the 20 samples, linked IDs, business transaction type, message format, leg count, and matrix status |
| [SELECTION.json](SELECTION.json) | Selection criteria, provenance, counts, and redaction summary |
| `single-leg/requirements/` and `multi-leg/requirements/` | Selected BR records from `requirement_candidates.json`, with source page/rule references |
| `single-leg/scenarios/` and `multi-leg/scenarios/` | Scenario records from the run's approved-scenario artifact |
| `single-leg/test_cases/` and `multi-leg/test_cases/` | Extracted TC records from `test_case_candidates.json`; sensitive-looking values masked |
| `single-leg/test_data/` and `multi-leg/test_data/` | Masked TD JSON and matching metadata files copied from the run |
| `single-leg/traceability/` and `multi-leg/traceability/` | One consolidated BR → TS → TC → every TD-leg map per sample |

For any sample, start with its `traceability/TC-*.json`. Each BR entry points to its BR file, the common TS, TC, and all data legs. `MANIFEST.csv` provides the same navigation across all examples.

## A. Single-leg examples

Each example is one independently generated, non-flow TC with one physical TD JSON file. The business transaction type comes from the first Prompt Code position, not the message-format family. Nine samples use Prompt Code `0`; one candidate uses `S` and is explicitly flagged for semantic review.

| Sample | Business transaction type | Message format | Prompt Code | BR | Scenario | TC | TD legs | Trace |
|---|---|---|---|---|---|---|---:|---|
| S01 | POS Purchase/Capture (type `0`; completion context not established) | Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:3665` | `SC-1415` | [TC-001272](single-leg/test_cases/TC-001272.json) | 1 | [trace](single-leg/traceability/TC-001272.json) |
| S02 | POS Purchase/Capture (type `0`; completion context not established) | Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:3667` | `SC-1417` | [TC-001274](single-leg/test_cases/TC-001274.json) | 1 | [trace](single-leg/traceability/TC-001274.json) |
| S03 | POS Purchase/Capture (type `0`; completion context not established) | EMV Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:3875` | `SC-1002` | [TC-000930](single-leg/test_cases/TC-000930.json) | 1 | [trace](single-leg/traceability/TC-000930.json) |
| S04 | POS Purchase/Capture (type `0`; completion context not established) | Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:080` | `SC-1307` | [TC-001164](single-leg/test_cases/TC-001164.json) | 1 | [trace](single-leg/traceability/TC-001164.json) |
| S05 | POS Purchase/Capture (type `0`; completion context not established) | EMV Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:005` | `SC-0752` | [TC-000680](single-leg/test_cases/TC-000680.json) | 1 | [trace](single-leg/traceability/TC-000680.json) |
| S06 | POS Purchase/Capture (type `0`; completion context not established) | EMV Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:018` | `SC-0754` | [TC-000682](single-leg/test_cases/TC-000682.json) | 1 | [trace](single-leg/traceability/TC-000682.json) |
| S07 | POS Purchase/Capture (type `0`; completion context not established) | EMV Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:019` | `SC-0755` | [TC-000683](single-leg/test_cases/TC-000683.json) | 1 | [trace](single-leg/traceability/TC-000683.json) |
| S08 | POS Purchase/Capture (type `0`; completion context not established) | EMV Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:044` | `SC-0760` | [TC-000688](single-leg/test_cases/TC-000688.json) | 1 | [trace](single-leg/traceability/TC-000688.json) |
| S09 | POS Purchase/Capture (type `0`; completion context not established) | EMV Financial Transaction Request | `0020` | `REQ-SRC-ATL105-PDF-001:063` | `SC-0765` | [TC-000693](single-leg/test_cases/TC-000693.json) | 1 | [trace](single-leg/traceability/TC-000693.json) |
| S10 | Cancellation candidate (type `S`; semantic review needed) | Financial Transaction Request | `S020` | `REQ-SRC-ATL105-PDF-001:1841` | `SC-4727` | [TC-005370](single-leg/test_cases/TC-005370.json) | 1 | [trace](single-leg/traceability/TC-005370.json) |

For S10, the source TC has a one-message request and Prompt Code `S020`, but its linked BR concerns an EMV response-S follow-on transaction. The trace is preserved; do not present it as a validated cancellation test without SME review.

## B. Multi-leg examples

Each example is a distinct `flow_id` with two or three ordered transactions. Every step has a separate TD JSON file; the consolidated trace record lists all legs.

| Sample | Flow / transaction sequence | BRs linked in matrix | Scenario | TC | TD legs | Trace |
|---|---|---|---|---|---:|---|
| M01 | `FLOW-AUTH-CANCELLATION` — authorization → cancellation | `:089`, `:592` | `SC-2309` | [TC-002001](multi-leg/test_cases/TC-002001.json) | 2 | [trace](multi-leg/traceability/TC-002001.json) |
| M02 | `FLOW-AUTH-COMPLETION` — authorization → completion | `:089`, `:1921` | `SC-2083` | [TC-001775](multi-leg/test_cases/TC-001775.json) | 2 | [trace](multi-leg/traceability/TC-001775.json) |
| M03 | `FLOW-AUTH-COMPLETION-VOID` — authorization → completion → reversal | `:089`, `:1661`, `:1921`, `:1923` | `SC-2408` | [TC-002100](multi-leg/test_cases/TC-002100.json) | 3 | [trace](multi-leg/traceability/TC-002100.json) |
| M04 | `FLOW-AUTH-PARTIAL-COMPLETION` — authorization → partial completion | `:018`, `:1921` | `SC-2134` | [TC-001826](multi-leg/test_cases/TC-001826.json) | 2 | [trace](multi-leg/traceability/TC-001826.json) |
| M05 | `FLOW-EMV-AUTH-COMPLETION` — EMV authorization → completion | `:089`, `:1921` | `SC-2667` | [TC-002359](multi-leg/test_cases/TC-002359.json) | 2 | [trace](multi-leg/traceability/TC-002359.json) |
| M06 | `FLOW-EMV-SALE-VOID` — EMV sale → reversal | `:089`, `:1923` | `SC-2695` | [TC-002387](multi-leg/test_cases/TC-002387.json) | 2 | [trace](multi-leg/traceability/TC-002387.json) |
| M07 | `FLOW-EMV-TIMEOUT-REVERSAL` — EMV transaction → timeout reversal | `:107`, `:288`, `:293` | `SC-2752` | [TC-002444](multi-leg/test_cases/TC-002444.json) | 2 | [trace](multi-leg/traceability/TC-002444.json) |
| M08 | `FLOW-ESTIMATED-INCREMENTAL-AUTH` — initial/estimated auth → incremental auth → completion | `:089`, `:1922`, `:3212`, `:3214`, `:3215` | `SC-2664` | [TC-002356](multi-leg/test_cases/TC-002356.json) | 3 | [trace](multi-leg/traceability/TC-002356.json) |
| M09 | `FLOW-TIMEOUT-NEXT-TRANSACTION` — transaction → timeout reversal → next transaction | `:107`, `:288`, `:293` | `SC-2508` | [TC-002200](multi-leg/test_cases/TC-002200.json) | 3 | [trace](multi-leg/traceability/TC-002200.json) |
| M10 | `FLOW-LOYALTY-SALE-REVERSAL` — loyalty sale → reversal | `:165`, `:2143` | `SC-2540` | [TC-002232](multi-leg/test_cases/TC-002232.json) | 2 | [trace](multi-leg/traceability/TC-002232.json) |

In the multi-leg BR column, each suffix represents the final numeric part of the full requirement ID `REQ-SRC-ATL105-PDF-001:<suffix>`. The exact full IDs and their statements are in `MANIFEST.csv` and each sample's trace JSON.

## Suggested client walkthrough (about five minutes)

1. **Set context:** “These are 20 selected examples from the October 18 AI Solution run. Ten are one-message cases; ten are transaction sequences. This is a traceability demonstration, not a certification result.”
2. **Show a single leg (S03):** Open its trace JSON. Point out that Prompt Code `0020` means transaction type `0` plus card type `020`, while `EMV Financial Transaction Request` is the message-format family. Follow the BR ID to `requirements/`, the scenario ID to `scenarios/`, then the TC record and its one TD file.
3. **Show a multi-leg lifecycle (M08):** Point out its five BR links, one scenario and one TC. Open the TC's `steps` array and then the three `step1`, `step2`, and `step3` TD files to show the sequence is represented as separate data legs.
4. **Show breadth and limits:** Use the manifest to compare operation codes and message-format families. Explain that authorization, completion, timeout, return/refund, and reversal/void semantics are traced in flow TCs, not standalone single-leg TCs in this run.
5. **State the guardrails:** The pipeline marked these rows `PASS`/`FULLY_TRACED`; those labels do not represent independent review or host execution. The demo data has been masked, and the original run retains unresolved and confidence flags.

## Source artifacts

All selections were made from the sibling `181026 Run` directory:

- `Requirement candidates/requirement_candidates.json`
- `scenarios/scenarios/approved/approved_scenarios.json`
- `test_case_candidates.json`
- `traceability_matrix_full.json`
- `qe_shaped_test_data/qe_shaped_test_data/`

This package contains the selected records and masked test-data copies only. It does not modify or replace the original run.
