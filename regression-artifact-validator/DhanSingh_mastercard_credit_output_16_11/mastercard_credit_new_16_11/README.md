# MasterCard Credit test package - BUYPASS ATL105 (plan v12)

Generated from the ATL105 knowledge base by the rule-driven test case generator (`--tc-model generated`), MasterCard Credit only. Previous packages are in [archive/](archive/) (`v10_2026-11-13`, `v8_2026-11-13`).

## What is in this folder

| Path | Content |
|---|---|
| `test_cases/test_cases_by_transaction_type.xlsx` | simple workbook - 6 columns: BR ID, BR Name, Scenario ID, Scenario Name, Test Case ID, Test Case Name; sheet `Blocked` lists test cases that cannot run, with the reason |
| `test_cases/test_cases.xlsx` | detailed workbook - context, technique, polarity, expected result, run priority, status, missing inputs, spec reference, test data file; sheets `Blocked` and `Steps` |
| `test_cases/test_cases.jsonl` / `.csv` | every test case with its full detail (spec basis, rule checks, expected response per step) |
| `test_data/` | one executable request per test case (`TD-TC-######.json`), one file per leg for flows (`TD-TC-######.step<n>.<role>.json`) |
| `coverage/` | coverage, contexts (`contexts.csv`, `context_profiles.csv`, `alignment_report.csv`, `sme_pack_contexts.csv`), spec role map (`spec_roles.csv`), theme / rule ledgers, SME pack |
| `requirements_scenarios/`, `reports/` | business requirement + scenarios; traceability matrix |
| `data_element_validation/`, `terminal_and_host_operations/` | the shared BRs, same layout |
| `id_migration.csv` | old (v10) -> new test case id, scenario id, name and test data files |
| `DELIVERY_CONTRACT.md`, `MANIFEST.json` | package contract (0 unresolved references) and file hashes |

## Numbers

| BR | Scenarios | Test cases | Smoke (P1) | Regression (P1+P2) |
|---|---:|---:|---:|---:|
| MasterCard Credit (network BR) | 196 | 2129 | 734 | 1901 |
| Data Element Validation (shared BR) | 35 | 1029 | 0 | 0 |
| Terminal & Host Operations (shared BR) | 9 | 47 | 0 | 0 |

Acceptance contexts built: 15 (6 candidates pending SME review - 82 test cases, run set `sme_review`); see [CONTEXTS.md](CONTEXTS.md).
Context on the wire (alignment report): 5066 checks ok, 0 mismatches, 0 not sent (438 steps send no table carrier).
Id migration from v10: 2886 kept (new ids), 319 added, 51 removed.

## How to read a test

- **Scenario name**: `<BR> <Transaction> - <Entry mode> @ <context>`, e.g. `... Purchase/Capture - EMV Contact Chip @ Petroleum · unattended CAT (fuel machine)`.
- **Test case name**: `<BR> <merchant> <what is done>[, then <next step>] → <expected outcome>`; no page, table, segment or element numbers - those are in the test case JSON (`spec_basis`, `rule_checks`).
- **Context on the wire**: the terminal-profile table (ATL105 Table 030, Appendix Q) carries the terminal class, presentation and terminal type of the context; a merchant-category context sends Table 007; a fuel machine sends the amount as the fuel purchase, a fuel product first and a pump number.
- **Expected result**: a single code when the spec states it (ASSERT), else the outcome class (any of the listed codes, INFER), else FLAG with the SME question.

## Gates of this build

validation findings 0 (all BRs); test case audit 0 high (all BRs); name readability findings 0; context alignment mismatches 0; package contract holds (0 unresolved references); full test suite green.

## SME review

`coverage/sme_pack_contexts.csv` asks one question per candidate context and per terminal combination no rule names. Record decisions in `src/pipeline/business_model/approved/context_decisions.json` (`approve` / `reject` / `same_as`) and rebuild: approved candidates get the full test set.
