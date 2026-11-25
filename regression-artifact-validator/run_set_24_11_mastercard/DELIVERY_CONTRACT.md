# Delivery contract

This package is self-contained. Every test-data reference in `test_cases/` is relative to this folder.

| Item | Contract |
|---|---|
| Test-data folder | `test_data/` - one request JSON per test case; a flow has one file per leg (`TD-<TC>.step<n>.<leg>.json`) |
| Reference field | `test_data_leg_files` (all legs, in step order) is authoritative for execution; `test_data_file` is the FIRST leg only. Every human-facing output lists every leg: `test_cases.xlsx` (Test Cases sheet: all legs; Steps sheet: one file per step), `reports/traceability_matrix.md` (one line per leg) and `TRACEABILITY.md` |
| Inline data | The `request` / `steps[].request` blocks in `test_cases.jsonl` are the composed source of the files; the files are the executable wire format and win if the two ever differ |
| Runtime values | A value from an earlier response (e.g. Approval Number, split-tender remaining balance) is sent as a format-valid stand-in and listed in the file's sidecar `test_data_meta/<file>.meta.json` `meta.valueSubstitutions` (`kind: runtime_link`, `replaceWith` = the run-time expression) for the test harness to replace at run time |
| Client inputs | Fields awaiting a client value (debit PAN / Encrypted PIN Block, client cards, client terminal / AVS table data, device totals) are sent as a provisional value of the element's own layout - never an empty field or a token - listed in the sidecar's `meta.valueSubstitutions` (`kind: awaiting_client_value`) and `awaiting_inputs`; Segment Lengths are computed with them (flagged provisional); such test cases have status `needs_client_input` and are not executable until supplied |
| Sidecars | `test_data_meta/` holds one `<file>.meta.json` per test-data file (field provenance, value substitutions, segments not sent); `test_data/` holds only the executable messages |
| Producer verification | `PASS` = no rule violated; `PASS_WITH_ADVISORIES` = no violation but some constraints not verifiable by the producer (listed per TC) - not a full validity certificate |
| Approval scope | Client sample approval covers message format and hierarchy only, not sample-content validity |

## Reference check

- Test cases: 973
- Distinct test-data references: 1522
- Test-data folder rendered: yes
- Unresolved references: 0
- Multi-leg test cases whose leg-file count differs from their step count: 0
- Test-case lines that do not parse as JSON: 0
- Trace entries (reports/) re-checked against this folder: 973 (0 with a missing leg file; 0 device-only / not composed - no request by design)
