# Delivery contract

This package is self-contained. Every test-data reference in `test_cases/` is relative to this folder.

| Item | Contract |
|---|---|
| Test-data folder | `test_data/` - one request JSON per test case; a flow has one file per leg (`TD-<TC>.step<n>.<leg>.json`) |
| Reference field | `test_data_file` (first leg) and `test_data_leg_files` (all legs) in `test_cases/test_cases.jsonl` / `.csv` |
| Inline data | The `request` / `steps[].request` blocks in `test_cases.jsonl` are the composed source of the files; the files are the executable wire format and win if the two ever differ |
| Runtime tokens | Values shaped `<...>` are bound by the test harness at run time (e.g. Approval Number from a prior response, split-tender remaining balance) |
| Client inputs | Fields awaiting a client value (debit PAN / Encrypted PIN Block, client cards, client terminal / AVS table data) carry the explicit placeholder `<AWAITING:AccountNumber>` - never an empty field - count as 0 bytes in their Segment Length (flagged provisional) and are listed in `awaiting_inputs` and the file's `meta.unavailableFields` (dataSource `TODO_CLIENT_VALUE`); such test cases have status `needs_client_input` and are not executable until supplied |
| Producer verification | `PASS` = no rule violated; `PASS_WITH_ADVISORIES` = no violation but some constraints not verifiable by the producer (listed per TC) - not a full validity certificate |
| Approval scope | Client sample approval covers message format and hierarchy only, not sample-content validity |

## Reference check

- Test cases: 47
- Distinct test-data references: 42
- Test-data folder rendered: yes
- Unresolved references: 0
- Test-case lines that do not parse as JSON: 0
- Trace entries (reports/) re-checked against this folder: 47 (0 with a missing leg file; 6 device-only / not composed - no request by design)
