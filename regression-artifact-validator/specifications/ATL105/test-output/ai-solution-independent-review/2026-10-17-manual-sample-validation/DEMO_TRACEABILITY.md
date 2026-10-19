# Demo - One Test Case per Flow, Traced in `traceability_matrix_full.json`

This folder holds **one representative test case for every distinct flow** in the generated suite: **20 single-leg** (one per transaction type) and **19 multi-leg** (one per flow), **39 test cases** in total. No permutations or variants are repeated (no second card brand, field profile or boundary of the same flow), so every BR, TS, TC and TD below can be checked by hand.

All content comes from the **candidates** artifacts and `traceability_matrix_full.json`. Links are relative to this folder (`10_oct_artifacts/Demo/`).

## Folder contents

| Item | Contents |
|------|----------|
| [DEMO_TRACEABILITY.md](DEMO_TRACEABILITY.md) | This guide |
| [validation_checklist.csv](validation_checklist.csv) | One row per test case with every matrix line number, plus empty `Validated` / `Comments` columns for your sign-off |
| [test_case_records/](test_case_records/) | 39 test case records, copied 1:1 from `test_generation/test_generation/candidates/test_case_candidates.json` |
| [test_data_records/](test_data_records/) | 63 test data messages (one per single leg, one per flow leg) + their `.meta.json`, byte-identical (SHA-256) copies of `candidates/qe_shaped_test_data/` |
| [matrix_extracts/](matrix_extracts/) | One `.txt` per test case with the exact lines from `traceability_matrix_full.json` (BR, TS, TC, TD and the `flat_rows` entry), **with the original line numbers**, so you don't need to open the 302 MB file |

## How the selection was made

For each single-leg transaction type and each multi-leg `flow_id`, the test case chosen is the first one meeting these rules, in this order:
1. Present in the `requirements` section of `traceability_matrix_full.json` under its **own** requirement.
2. Linked to a requirement (`requirement_id` not null).
3. Scenario **not** failed by the LLM judge (`FAIL` / `NEEDS_REVIEW`).
4. Positive scenario preferred (business rule, then other types).
5. Completeness `COMPLETE` preferred, then LLM judge PASS/HIGH, then human-approved, then priority.

Only verification `PASS` test cases with a test data file on disk were considered. Exceptions: for **Totals Request** and **Totals with Proprietary Data Load Request** every positive scenario was failed or flagged by the LLM judge, so a Totals-specific boundary test (`Grand Total` = exactly 8 bytes) was used. For three multi-leg flows no test case meets rule 1 or 2 (see Findings).

## How to validate manually

For each row in the tables below (or the CSV):
1. **BR**: open the matrix excerpt `matrix_extracts/<TC>.txt` → section *Requirement (BR)*. Check the `requirement_id` and statement.
2. **TS**: same file → *Scenario (TS)*. Check that `scenario_id` and `name` match the test case record, and that it sits under that requirement.
3. **TC**: same file → *Test case (TC)*. Check `test_case_id`, `transaction_type`, `verification_status = PASS`, `expected_response`. Compare with [test_case_records/](test_case_records/).
4. **TD**: same file → `test_data.test_data_file`. Open that file in [test_data_records/](test_data_records/) and check `key_values` against it. For multi-leg flows the matrix lists **only the step-1 file**; the other legs are in `test_data_records/` (named `<TC>.step<N>.<role>.json`).
5. Optional, to see the line in the full matrix: `python -c "import itertools;print(next(itertools.islice(open(r'..\traceability_matrix_full.json',encoding='utf-8'),N-1,N)))"` (replace `N`).

Automated pre-check already done for all 39: the matrix scenario, requirement, test data file and verification status equal the values in the test case record, and every test data file listed in the record exists in `test_data_records/`. Only the 3 rows marked ⚠/❌ differ, as described.

Matrix line columns: **BR** = `requirement_id` line, **TS** = `scenario_id` line, **TC** = `test_case_id` line, **TD** = `test_data_file` line, **Flat** = start of the `flat_rows` entry. Line links open `traceability_matrix_full.json`, which is very large; the `matrix_extracts` files are quicker.

---

## A. Single-leg test cases (20 transaction types)

| # | Transaction type | BR (REQ-…:) | TS | TC | TD | Matrix BR | TS | TC | TD | Flat | Extract | Status |
|---|------------------|-------------|----|----|----|-----------|----|----|----|------|---------|--------|
| S1 | ECA/TeleCheck Service Transaction | `013` | `SC-0009` | [`TC-000009`](test_case_records/TC-000009.json) | [TC-000009.json](test_data_records/TC-000009.json) | [66252](../traceability_matrix_full.json#L66252) | [66999](../traceability_matrix_full.json#L66999) | [67022](../traceability_matrix_full.json#L67022) | [67036](../traceability_matrix_full.json#L67036) | [4388368](../traceability_matrix_full.json#L4388368) | [txt](matrix_extracts/TC-000009.txt) | ✅ BR→TS→TC→TD traced |
| S2 | ECA/TeleCheck Service Transaction Request | `013` | `SC-0134` | [`TC-000134`](test_case_records/TC-000134.json) | [TC-000134.json](test_data_records/TC-000134.json) | [66252](../traceability_matrix_full.json#L66252) | [78374](../traceability_matrix_full.json#L78374) | [78397](../traceability_matrix_full.json#L78397) | [78411](../traceability_matrix_full.json#L78411) | [4391243](../traceability_matrix_full.json#L4391243) | [txt](matrix_extracts/TC-000134.txt) | ✅ BR→TS→TC→TD traced |
| S3 | EMV Financial Transaction Request | `209` | `SC-0275` | [`TC-000275`](test_case_records/TC-000275.json) | [TC-000275.json](test_data_records/TC-000275.json) | [2544980](../traceability_matrix_full.json#L2544980) | [2545863](../traceability_matrix_full.json#L2545863) | [2545903](../traceability_matrix_full.json#L2545903) | [2545917](../traceability_matrix_full.json#L2545917) | [4394486](../traceability_matrix_full.json#L4394486) | [txt](matrix_extracts/TC-000275.txt) | ✅ BR→TS→TC→TD traced |
| S4 | EMV Request Transaction | `095` | `SC-0581` | [`TC-000569`](test_case_records/TC-000569.json) | [TC-000569.json](test_data_records/TC-000569.json) | [697846](../traceability_matrix_full.json#L697846) | [754007](../traceability_matrix_full.json#L754007) | [754031](../traceability_matrix_full.json#L754031) | [754045](../traceability_matrix_full.json#L754045) | [4401248](../traceability_matrix_full.json#L4401248) | [txt](matrix_extracts/TC-000569.txt) | ✅ BR→TS→TC→TD traced |
| S5 | Electronic Mail | `011` | `SC-0707` | [`TC-000695`](test_case_records/TC-000695.json) | [TC-000695.json](test_data_records/TC-000695.json) | [5705](../traceability_matrix_full.json#L5705) | [5724](../traceability_matrix_full.json#L5724) | [5742](../traceability_matrix_full.json#L5742) | [5761](../traceability_matrix_full.json#L5761) | [4373004](../traceability_matrix_full.json#L4373004) | [txt](matrix_extracts/TC-000695.txt) | ✅ BR→TS→TC→TD traced |
| S6 | Electronic Mail Request | `011` | `SC-0728` | [`TC-000716`](test_case_records/TC-000716.json) | [TC-000716.json](test_data_records/TC-000716.json) | [5705](../traceability_matrix_full.json#L5705) | [7635](../traceability_matrix_full.json#L7635) | [7653](../traceability_matrix_full.json#L7653) | [7672](../traceability_matrix_full.json#L7672) | [4373487](../traceability_matrix_full.json#L4373487) | [txt](matrix_extracts/TC-000716.txt) | ✅ BR→TS→TC→TD traced |
| S7 | FSA/HRA Purchase/Capture | `083` | `SC-0781` | [`TC-000769`](test_case_records/TC-000769.json) | [TC-000769.json](test_data_records/TC-000769.json) | [583470](../traceability_matrix_full.json#L583470) | [612841](../traceability_matrix_full.json#L612841) | [612860](../traceability_matrix_full.json#L612860) | [612874](../traceability_matrix_full.json#L612874) | [4516524](../traceability_matrix_full.json#L4516524) | [txt](matrix_extracts/TC-000769.txt) | ✅ BR→TS→TC→TD traced |
| S8 | FSA/HRA Transaction Request | `127` | `SC-0884` | [`TC-000872`](test_case_records/TC-000872.json) | [TC-000872.json](test_data_records/TC-000872.json) | [1248829](../traceability_matrix_full.json#L1248829) | [1320494](../traceability_matrix_full.json#L1320494) | [1320512](../traceability_matrix_full.json#L1320512) | [1320526](../traceability_matrix_full.json#L1320526) | [4558982](../traceability_matrix_full.json#L4558982) | [txt](matrix_extracts/TC-000872.txt) | ✅ BR→TS→TC→TD traced |
| S9 | Financial Transaction | `121` | `SC-1051` | [`TC-001039`](test_case_records/TC-001039.json) | [TC-001039.json](test_data_records/TC-001039.json) | [1139141](../traceability_matrix_full.json#L1139141) | [1183176](../traceability_matrix_full.json#L1183176) | [1183197](../traceability_matrix_full.json#L1183197) | [1183211](../traceability_matrix_full.json#L1183211) | [4520342](../traceability_matrix_full.json#L4520342) | [txt](matrix_extracts/TC-001039.txt) | ✅ BR→TS→TC→TD traced |
| S10 | Financial Transaction Request | `173` | `SC-1341` | [`TC-001329`](test_case_records/TC-001329.json) | [TC-001329.json](test_data_records/TC-001329.json) | [1997114](../traceability_matrix_full.json#L1997114) | [2063473](../traceability_matrix_full.json#L2063473) | [2063519](../traceability_matrix_full.json#L2063519) | [2063533](../traceability_matrix_full.json#L2063533) | [4378616](../traceability_matrix_full.json#L4378616) | [txt](matrix_extracts/TC-001329.txt) | ✅ BR→TS→TC→TD traced |
| S11 | Financial Transactions | `011` | `SC-1478` | [`TC-001454`](test_case_records/TC-001454.json) | [TC-001454.json](test_data_records/TC-001454.json) | [5705](../traceability_matrix_full.json#L5705) | [46594](../traceability_matrix_full.json#L46594) | [46617](../traceability_matrix_full.json#L46617) | [46631](../traceability_matrix_full.json#L46631) | [4381491](../traceability_matrix_full.json#L4381491) | [txt](matrix_extracts/TC-001454.txt) | ✅ BR→TS→TC→TD traced |
| S12 | Financial Transactions (Data Section No. 3) | `095` | `SC-1586` | [`TC-001562`](test_case_records/TC-001562.json) | [TC-001562.json](test_data_records/TC-001562.json) | [697846](../traceability_matrix_full.json#L697846) | [850737](../traceability_matrix_full.json#L850737) | [850753](../traceability_matrix_full.json#L850753) | [850767](../traceability_matrix_full.json#L850767) | [4575404](../traceability_matrix_full.json#L4575404) | [txt](matrix_extracts/TC-001562.txt) | ✅ BR→TS→TC→TD traced |
| S13 | Key Load | `095` | `SC-1592` | [`TC-001568`](test_case_records/TC-001568.json) | [TC-001568.json](test_data_records/TC-001568.json) | [697846](../traceability_matrix_full.json#L697846) | [851145](../traceability_matrix_full.json#L851145) | [851161](../traceability_matrix_full.json#L851161) | [851175](../traceability_matrix_full.json#L851175) | [4575542](../traceability_matrix_full.json#L4575542) | [txt](matrix_extracts/TC-001568.txt) | ✅ BR→TS→TC→TD traced |
| S14 | Loyalty Card Transaction | `013` | `SC-1611` | [`TC-001587`](test_case_records/TC-001587.json) | [TC-001587.json](test_data_records/TC-001587.json) | [66252](../traceability_matrix_full.json#L66252) | [171097](../traceability_matrix_full.json#L171097) | [171119](../traceability_matrix_full.json#L171119) | [171133](../traceability_matrix_full.json#L171133) | [4411736](../traceability_matrix_full.json#L4411736) | [txt](matrix_extracts/TC-001587.txt) | ✅ BR→TS→TC→TD traced |
| S15 | Loyalty Card Transaction Request | `013` | `SC-1751` | [`TC-001727`](test_case_records/TC-001727.json) | [TC-001727.json](test_data_records/TC-001727.json) | [66252](../traceability_matrix_full.json#L66252) | [183697](../traceability_matrix_full.json#L183697) | [183719](../traceability_matrix_full.json#L183719) | [183733](../traceability_matrix_full.json#L183733) | [4414956](../traceability_matrix_full.json#L4414956) | [txt](matrix_extracts/TC-001727.txt) | ✅ BR→TS→TC→TD traced |
| S16 | Mail Request | `052` | `SC-1871` | [`TC-001847`](test_case_records/TC-001847.json) | [TC-001847.json](test_data_records/TC-001847.json) | [348795](../traceability_matrix_full.json#L348795) | [491613](../traceability_matrix_full.json#L491613) | [491631](../traceability_matrix_full.json#L491631) | [491650](../traceability_matrix_full.json#L491650) | [4384274](../traceability_matrix_full.json#L4384274) | [txt](matrix_extracts/TC-001847.txt) | ✅ BR→TS→TC→TD traced |
| S17 | Proprietary Data Load Request | `011` | `SC-1879` | [`TC-001855`](test_case_records/TC-001855.json) | [TC-001855.json](test_data_records/TC-001855.json) | [5705](../traceability_matrix_full.json#L5705) | [58333](../traceability_matrix_full.json#L58333) | [58353](../traceability_matrix_full.json#L58353) | [58371](../traceability_matrix_full.json#L58371) | [4384458](../traceability_matrix_full.json#L4384458) | [txt](matrix_extracts/TC-001855.txt) | ✅ BR→TS→TC→TD traced |
| S18 | Totals Request | `049` | `SC-5501` | [`TC-005134`](test_case_records/TC-005134.json) | [TC-005134.json](test_data_records/TC-005134.json) | [341783](../traceability_matrix_full.json#L341783) | [342642](../traceability_matrix_full.json#L342642) | [342658](../traceability_matrix_full.json#L342658) | [342679](../traceability_matrix_full.json#L342679) | [4451549](../traceability_matrix_full.json#L4451549) | [txt](matrix_extracts/TC-005134.txt) | ✅ BR→TS→TC→TD traced |
| S19 | Totals with Proprietary Data Load Request | `049` | `SC-5519` | [`TC-005150`](test_case_records/TC-005150.json) | [TC-005150.json](test_data_records/TC-005150.json) | [341783](../traceability_matrix_full.json#L341783) | [342733](../traceability_matrix_full.json#L342733) | [342749](../traceability_matrix_full.json#L342749) | [342770](../traceability_matrix_full.json#L342770) | [4451572](../traceability_matrix_full.json#L4451572) | [txt](matrix_extracts/TC-005150.txt) | ✅ BR→TS→TC→TD traced |
| S20 | TransArmor Key and Key ID Load | `648` | `SC-6022` | [`TC-005852`](test_case_records/TC-005852.json) | [TC-005852.json](test_data_records/TC-005852.json) | [4217211](../traceability_matrix_full.json#L4217211) | [4217230](../traceability_matrix_full.json#L4217230) | [4217244](../traceability_matrix_full.json#L4217244) | [4217258](../traceability_matrix_full.json#L4217258) | [5506007](../traceability_matrix_full.json#L5506007) | [txt](matrix_extracts/TC-005852.txt) | ✅ BR→TS→TC→TD traced |

## B. Multi-leg test cases (19 flows)

| # | Flow | Legs (role) | BR (REQ-…:) | TS | TC | TD (all legs) | Matrix BR | TS | TC | TD (step 1) | Flat | Extract | Status |
|---|------|-------------|-------------|----|----|---------------|-----------|----|----|-------------|------|---------|--------|
| M1 | `FLOW-AUTH-CANCELLATION` | authorization → cancellation | `096` | `SC-4820` | [`TC-004475`](test_case_records/TC-004475.json) | [TC-004475.step1.authorization.json](test_data_records/TC-004475.step1.authorization.json)<br>[TC-004475.step2.cancellation.json](test_data_records/TC-004475.step2.cancellation.json) | [914656](../traceability_matrix_full.json#L914656) | [918674](../traceability_matrix_full.json#L918674) | [918690](../traceability_matrix_full.json#L918690) | [918704](../traceability_matrix_full.json#L918704) | [4594540](../traceability_matrix_full.json#L4594540) | [txt](matrix_extracts/TC-004475.txt) | ✅ BR→TS→TC→TD traced |
| M2 | `FLOW-AUTH-COMPLETION` | authorization → completion | `096` | `SC-4693` | [`TC-004348`](test_case_records/TC-004348.json) | [TC-004348.step1.authorization.json](test_data_records/TC-004348.step1.authorization.json)<br>[TC-004348.step2.completion.json](test_data_records/TC-004348.step2.completion.json) | [914656](../traceability_matrix_full.json#L914656) | [918254](../traceability_matrix_full.json#L918254) | [918270](../traceability_matrix_full.json#L918270) | [918284](../traceability_matrix_full.json#L918284) | [4594425](../traceability_matrix_full.json#L4594425) | [txt](matrix_extracts/TC-004348.txt) | ✅ BR→TS→TC→TD traced |
| M3 | `FLOW-AUTH-COMPLETION-DUPLICATE` | authorization → completion → duplicate_completion | `020` | `SC-4753` | [`TC-004408`](test_case_records/TC-004408.json) | [TC-004408.step1.authorization.json](test_data_records/TC-004408.step1.authorization.json)<br>[TC-004408.step2.completion.json](test_data_records/TC-004408.step2.completion.json)<br>[TC-004408.step3.duplicate-completion.json](test_data_records/TC-004408.step3.duplicate-completion.json) | [261259](../traceability_matrix_full.json#L261259) | [263126](../traceability_matrix_full.json#L263126) | [263142](../traceability_matrix_full.json#L263142) | [263156](../traceability_matrix_full.json#L263156) | [4433011](../traceability_matrix_full.json#L4433011) | [txt](matrix_extracts/TC-004408.txt) | ✅ BR→TS→TC→TD traced |
| M4 | `FLOW-AUTH-COMPLETION-VOID` | authorization → completion → void | `096` | `SC-4873` | [`TC-004528`](test_case_records/TC-004528.json) | [TC-004528.step1.authorization.json](test_data_records/TC-004528.step1.authorization.json)<br>[TC-004528.step2.completion.json](test_data_records/TC-004528.step2.completion.json)<br>[TC-004528.step3.void.json](test_data_records/TC-004528.step3.void.json) | [914656](../traceability_matrix_full.json#L914656) | [918758](../traceability_matrix_full.json#L918758) | [918774](../traceability_matrix_full.json#L918774) | [918788](../traceability_matrix_full.json#L918788) | [4594563](../traceability_matrix_full.json#L4594563) | [txt](matrix_extracts/TC-004528.txt) | ✅ BR→TS→TC→TD traced |
| M5 | `FLOW-AUTH-PARTIAL-COMPLETION` | authorization → partial_completion | `020` | `SC-4721` | [`TC-004376`](test_case_records/TC-004376.json) | [TC-004376.step1.authorization.json](test_data_records/TC-004376.step1.authorization.json)<br>[TC-004376.step2.partial-completion.json](test_data_records/TC-004376.step2.partial-completion.json) | [261259](../traceability_matrix_full.json#L261259) | [263042](../traceability_matrix_full.json#L263042) | [263058](../traceability_matrix_full.json#L263058) | [263072](../traceability_matrix_full.json#L263072) | [4432988](../traceability_matrix_full.json#L4432988) | [txt](matrix_extracts/TC-004376.txt) | ✅ BR→TS→TC→TD traced |
| M6 | `FLOW-AUTH-VOID` | authorization → void | - (matrix: `6786`) | `SC-4847` | [`TC-004502`](test_case_records/TC-004502.json) | [TC-004502.step1.authorization.json](test_data_records/TC-004502.step1.authorization.json)<br>[TC-004502.step2.void.json](test_data_records/TC-004502.step2.void.json) | [4255140](../traceability_matrix_full.json#L4255140) | [4264926](../traceability_matrix_full.json#L4264926) | [4264942](../traceability_matrix_full.json#L4264942) | [4264956](../traceability_matrix_full.json#L4264956) | [5524016](../traceability_matrix_full.json#L5524016) | [txt](matrix_extracts/TC-004502.txt) | ⚠ TC has no requirement; matrix relinks it |
| M7 | `FLOW-DUPLICATE-SEQUENCE-NUMBER-VIOLATION` | first_independent_transaction → second_independent_transaction_duplicate_sequence | - | `SC-4994` | [`TC-004649`](test_case_records/TC-004649.json) | [TC-004649.step1.first-independent-transaction.json](test_data_records/TC-004649.step1.first-independent-transaction.json)<br>[TC-004649.step2.second-independent-transaction-duplicate-sequence.json](test_data_records/TC-004649.step2.second-independent-transaction-duplicate-sequence.json) | - | - | - | - | - | [txt](matrix_extracts/TC-004649.txt) | ❌ not in matrix (orphan scenario) |
| M8 | `FLOW-EMV-AUTH-CANCELLATION` | emv_authorization → cancellation | `096` | `SC-5045` | [`TC-004700`](test_case_records/TC-004700.json) | [TC-004700.step1.emv-authorization.json](test_data_records/TC-004700.step1.emv-authorization.json)<br>[TC-004700.step2.cancellation.json](test_data_records/TC-004700.step2.cancellation.json) | [914656](../traceability_matrix_full.json#L914656) | [919346](../traceability_matrix_full.json#L919346) | [919362](../traceability_matrix_full.json#L919362) | [919376](../traceability_matrix_full.json#L919376) | [4594724](../traceability_matrix_full.json#L4594724) | [txt](matrix_extracts/TC-004700.txt) | ✅ BR→TS→TC→TD traced |
| M9 | `FLOW-EMV-AUTH-COMPLETION` | emv_authorization → completion | `096` | `SC-5011` | [`TC-004666`](test_case_records/TC-004666.json) | [TC-004666.step1.emv-authorization.json](test_data_records/TC-004666.step1.emv-authorization.json)<br>[TC-004666.step2.completion.json](test_data_records/TC-004666.step2.completion.json) | [914656](../traceability_matrix_full.json#L914656) | [919178](../traceability_matrix_full.json#L919178) | [919194](../traceability_matrix_full.json#L919194) | [919208](../traceability_matrix_full.json#L919208) | [4594678](../traceability_matrix_full.json#L4594678) | [txt](matrix_extracts/TC-004666.txt) | ✅ BR→TS→TC→TD traced |
| M10 | `FLOW-EMV-SALE-VOID` | emv_sale → void | `096` | `SC-5028` | [`TC-004683`](test_case_records/TC-004683.json) | [TC-004683.step1.emv-sale.json](test_data_records/TC-004683.step1.emv-sale.json)<br>[TC-004683.step2.void.json](test_data_records/TC-004683.step2.void.json) | [914656](../traceability_matrix_full.json#L914656) | [919262](../traceability_matrix_full.json#L919262) | [919278](../traceability_matrix_full.json#L919278) | [919292](../traceability_matrix_full.json#L919292) | [4594701](../traceability_matrix_full.json#L4594701) | [txt](matrix_extracts/TC-004683.txt) | ✅ BR→TS→TC→TD traced |
| M11 | `FLOW-EMV-TIMEOUT-REVERSAL` | emv_financial_transaction → timeout_reversal | `096` | `SC-5062` | [`TC-004717`](test_case_records/TC-004717.json) | [TC-004717.step1.emv-financial-transaction.json](test_data_records/TC-004717.step1.emv-financial-transaction.json)<br>[TC-004717.step2.timeout-reversal.json](test_data_records/TC-004717.step2.timeout-reversal.json) | [914656](../traceability_matrix_full.json#L914656) | [919430](../traceability_matrix_full.json#L919430) | [919446](../traceability_matrix_full.json#L919446) | [919460](../traceability_matrix_full.json#L919460) | [4594747](../traceability_matrix_full.json#L4594747) | [txt](matrix_extracts/TC-004717.txt) | ✅ BR→TS→TC→TD traced |
| M12 | `FLOW-ESTIMATED-INCREMENTAL-AUTH` | estimated_or_initial_authorization → incremental_authorization → completion | `096` | `SC-5009` | [`TC-004664`](test_case_records/TC-004664.json) | [TC-004664.step1.estimated-or-initial-authorization.json](test_data_records/TC-004664.step1.estimated-or-initial-authorization.json)<br>[TC-004664.step2.incremental-authorization.json](test_data_records/TC-004664.step2.incremental-authorization.json)<br>[TC-004664.step3.completion.json](test_data_records/TC-004664.step3.completion.json) | [914656](../traceability_matrix_full.json#L914656) | [919094](../traceability_matrix_full.json#L919094) | [919110](../traceability_matrix_full.json#L919110) | [919124](../traceability_matrix_full.json#L919124) | [4594655](../traceability_matrix_full.json#L4594655) | [txt](matrix_extracts/TC-004664.txt) | ✅ BR→TS→TC→TD traced |
| M13 | `FLOW-LOYALTY-SALE-REVERSAL` | sale → reversal | `177` | `SC-4944` | [`TC-004599`](test_case_records/TC-004599.json) | [TC-004599.step1.sale.json](test_data_records/TC-004599.step1.sale.json)<br>[TC-004599.step2.reversal.json](test_data_records/TC-004599.step2.reversal.json) | [2233409](../traceability_matrix_full.json#L2233409) | [2234940](../traceability_matrix_full.json#L2234940) | [2234956](../traceability_matrix_full.json#L2234956) | [2234970](../traceability_matrix_full.json#L2234970) | [4915781](../traceability_matrix_full.json#L4915781) | [txt](matrix_extracts/TC-004599.txt) | ✅ BR→TS→TC→TD traced |
| M14 | `FLOW-RETURN-VOID` | return → void | `096` | `SC-4785` | [`TC-004440`](test_case_records/TC-004440.json) | [TC-004440.step1.return.json](test_data_records/TC-004440.step1.return.json)<br>[TC-004440.step2.void.json](test_data_records/TC-004440.step2.void.json) | [914656](../traceability_matrix_full.json#L914656) | [918506](../traceability_matrix_full.json#L918506) | [918522](../traceability_matrix_full.json#L918522) | [918536](../traceability_matrix_full.json#L918536) | [4594494](../traceability_matrix_full.json#L4594494) | [txt](matrix_extracts/TC-004440.txt) | ✅ BR→TS→TC→TD traced |
| M15 | `FLOW-SALE-VOID` | sale → void | `096` | `SC-4900` | [`TC-004555`](test_case_records/TC-004555.json) | [TC-004555.step1.sale.json](test_data_records/TC-004555.step1.sale.json)<br>[TC-004555.step2.void.json](test_data_records/TC-004555.step2.void.json) | [914656](../traceability_matrix_full.json#L914656) | [918842](../traceability_matrix_full.json#L918842) | [918858](../traceability_matrix_full.json#L918858) | [918872](../traceability_matrix_full.json#L918872) | [4594586](../traceability_matrix_full.json#L4594586) | [txt](matrix_extracts/TC-004555.txt) | ✅ BR→TS→TC→TD traced |
| M16 | `FLOW-TIMEOUT-NEXT-TRANSACTION` | original_transaction → timeout_reversal → next_transaction | `096` | `SC-4926` | [`TC-004581`](test_case_records/TC-004581.json) | [TC-004581.step1.original-transaction.json](test_data_records/TC-004581.step1.original-transaction.json)<br>[TC-004581.step2.timeout-reversal.json](test_data_records/TC-004581.step2.timeout-reversal.json)<br>[TC-004581.step3.next-transaction.json](test_data_records/TC-004581.step3.next-transaction.json) | [914656](../traceability_matrix_full.json#L914656) | [918926](../traceability_matrix_full.json#L918926) | [918942](../traceability_matrix_full.json#L918942) | [918956](../traceability_matrix_full.json#L918956) | [4594609](../traceability_matrix_full.json#L4594609) | [txt](matrix_extracts/TC-004581.txt) | ✅ BR→TS→TC→TD traced |
| M17 | `FLOW-TIMEOUT-REVERSAL` | financial_transaction → timeout_reversal | `096` | `SC-4802` | [`TC-004457`](test_case_records/TC-004457.json) | [TC-004457.step1.financial-transaction.json](test_data_records/TC-004457.step1.financial-transaction.json)<br>[TC-004457.step2.timeout-reversal.json](test_data_records/TC-004457.step2.timeout-reversal.json) | [914656](../traceability_matrix_full.json#L914656) | [918590](../traceability_matrix_full.json#L918590) | [918606](../traceability_matrix_full.json#L918606) | [918620](../traceability_matrix_full.json#L918620) | [4594517](../traceability_matrix_full.json#L4594517) | [txt](matrix_extracts/TC-004457.txt) | ✅ BR→TS→TC→TD traced |
| M18 | `FLOW-TOR-RETRY` | timeout_reversal_attempt_1 → timeout_reversal_attempt_2 → timeout_reversal_attempt_3 | `096` | `SC-4961` | [`TC-004616`](test_case_records/TC-004616.json) | [TC-004616.step1.timeout-reversal-attempt-1.json](test_data_records/TC-004616.step1.timeout-reversal-attempt-1.json)<br>[TC-004616.step2.timeout-reversal-attempt-2.json](test_data_records/TC-004616.step2.timeout-reversal-attempt-2.json)<br>[TC-004616.step3.timeout-reversal-attempt-3.json](test_data_records/TC-004616.step3.timeout-reversal-attempt-3.json) | [914656](../traceability_matrix_full.json#L914656) | [919010](../traceability_matrix_full.json#L919010) | [919026](../traceability_matrix_full.json#L919026) | [919040](../traceability_matrix_full.json#L919040) | [4594632](../traceability_matrix_full.json#L4594632) | [txt](matrix_extracts/TC-004616.txt) | ✅ BR→TS→TC→TD traced |
| M19 | `FLOW-TOR-RETRY-FOURTH-INVALID` | timeout_reversal_attempt_3 → timeout_reversal_attempt_4_invalid | - | `SC-4979` | [`TC-004634`](test_case_records/TC-004634.json) | [TC-004634.step1.timeout-reversal-attempt-3.json](test_data_records/TC-004634.step1.timeout-reversal-attempt-3.json)<br>[TC-004634.step2.timeout-reversal-attempt-4-invalid.json](test_data_records/TC-004634.step2.timeout-reversal-attempt-4-invalid.json) | - | - | - | - | - | [txt](matrix_extracts/TC-004634.txt) | ❌ not in matrix (orphan scenario) |

---

## C. Details per test case

### S1 - ECA/TeleCheck Service Transaction - `TC-000009`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:013` - "Card Discretionary
Block Data must be one of: depends upon the application., for expired cards., (MMDDYY)., transactions associated with a Token., included as usual.." (spec p.364) |
| **TS** | `SC-0009` (positive) - Positive — ECA/TeleCheck Service Transaction — Card Discretionary Block Data=depends upon the application., Check Type=C, Information Byte=0, Partial Approval Indicator=0, Segment Type=100, State Code=001, Variable Information=00, Variable Information Indicator=001 |
| **TC** | `TC-000009` · ECA/TeleCheck Service Transaction · P2 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code `0` Approved-Purchase/Capture (`ASSERT`) · `test_case_candidates.json` line 6515 |
| **TD** | [TC-000009.json](test_data_records/TC-000009.json) |
| **Matrix** | BR [66252](../traceability_matrix_full.json#L66252) · TS [66999](../traceability_matrix_full.json#L66999) · TC [67022](../traceability_matrix_full.json#L67022) · TD [67036](../traceability_matrix_full.json#L67036) (`qe_shaped_test_data/TC-000009.json`) · flat_rows [4388368](../traceability_matrix_full.json#L4388368) · also listed under 7 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000009.txt) |

### S2 - ECA/TeleCheck Service Transaction Request - `TC-000134`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:013` - "Card Discretionary
Block Data must be one of: depends upon the application., for expired cards., (MMDDYY)., transactions associated with a Token., included as usual.." (spec p.364) |
| **TS** | `SC-0134` (positive) - Positive — ECA/TeleCheck Service Transaction Request — Card Discretionary Block Data=(MMDDYY)., Check Type=C, Information Byte=0, Partial Approval Indicator=0, Segment Type=100, State Code=001, Variable Information=00, Variable Information Indicator=001 |
| **TC** | `TC-000134` · ECA/TeleCheck Service Transaction Request · P3 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code `0` Approved-Purchase/Capture (`ASSERT`) · `test_case_candidates.json` line 108347 |
| **TD** | [TC-000134.json](test_data_records/TC-000134.json) |
| **Matrix** | BR [66252](../traceability_matrix_full.json#L66252) · TS [78374](../traceability_matrix_full.json#L78374) · TC [78397](../traceability_matrix_full.json#L78397) · TD [78411](../traceability_matrix_full.json#L78411) (`qe_shaped_test_data/TC-000134.json`) · flat_rows [4391243](../traceability_matrix_full.json#L4391243) · also listed under 7 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000134.txt) |

### S3 - EMV Financial Transaction Request - `TC-000275`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:209` - "CAVV Revised Format must be one of: Position Description, replaces the need for the XID)., section in Appendix Y (3-D Secure).." (spec p.474) |
| **TS** | `SC-0275` (positive) - Positive — EMV Financial Transaction Request — CAVV Revised Format=replaces the need for the XID)., Cryptogram Token Data=Position Description, Inclusive/Exclusive for Tax 1=I, Inclusive/Exclusive for Tax 2=I, Inclusive/Exclusive for Tax 3=I, Safekey Data=POS Condition, Tax Type 1=HST, Tax Type 2=HST Harmonized Sales Tax, Tax Type 3=HST Harmonized Sales Tax, Token Requestor ID=if applicable., Card Discretionary Block Data=depends upon the application., EBT Program Data=5-32, EMV Card Sequence Number=card, Information Byte=0, Loyalty Information Version=1, Partial Approval Indicator=0, Payment Tender Type=CS, Product Code=000, Segment Type=100, Service Level=F, Unit of Measure=C, Update Code=A, Variable Information=00, Variable Information Indicator=001, WIC Discount Amount=012 |
| **TC** | `TC-000275` · EMV Financial Transaction Request · P2 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code `0` Approved-Purchase/Capture (`ASSERT`) · `test_case_candidates.json` line 237127 |
| **TD** | [TC-000275.json](test_data_records/TC-000275.json) |
| **Matrix** | BR [2544980](../traceability_matrix_full.json#L2544980) · TS [2545863](../traceability_matrix_full.json#L2545863) · TC [2545903](../traceability_matrix_full.json#L2545903) · TD [2545917](../traceability_matrix_full.json#L2545917) (`qe_shaped_test_data/TC-000275.json`) · flat_rows [4394486](../traceability_matrix_full.json#L4394486) · `response_side_test_cases["EMV Financial Transaction Response"]` line [5559415](../traceability_matrix_full.json#L5559415) · also listed under 24 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000275.txt) |

### S4 - EMV Request Transaction - `TC-000569`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:095` - "Segment Type must be one of: Segment, Segment, Load Data Segment, Segment, Segment, Segment." (spec p.401) |
| **TS** | `SC-0581` (positive) - Positive — EMV Request Transaction — Segment Type=103, Payment Tender Type=DS, Update Code=C, Variable Information=01, Variable Information Indicator=019, Card Discretionary Block Data=depends upon the application., Information Byte=?, Partial Approval Indicator=0, Loyalty Information Version=1 |
| **TC** | `TC-000569` · EMV Request Transaction · P2 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code `0` Approved-Purchase/Capture (`ASSERT`) · `test_case_candidates.json` line 891760 |
| **TD** | [TC-000569.json](test_data_records/TC-000569.json) |
| **Matrix** | BR [697846](../traceability_matrix_full.json#L697846) · TS [754007](../traceability_matrix_full.json#L754007) · TC [754031](../traceability_matrix_full.json#L754031) · TD [754045](../traceability_matrix_full.json#L754045) (`qe_shaped_test_data/TC-000569.json`) · flat_rows [4401248](../traceability_matrix_full.json#L4401248) · also listed under 8 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000569.txt) |

### S5 - Electronic Mail - `TC-000695`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:011` - "Block Number must be one of: Public Key File Load., the Electronic Mail response as the next, block., sending that Block Number in the next, Electronic Mail request or CA Public Key, File Load request.." (spec p.363) |
| **TS** | `SC-0707` (positive) - Positive — Electronic Mail — Block Number=000, Information Byte=0, Segment Type=100 |
| **TC** | `TC-000695` · Electronic Mail · P3 · verification **PASS** · completeness `COMPLETE` · **human-approved** · Response Code not asserted (`INFER`) · `test_case_candidates.json` line 993568 |
| **TD** | [TC-000695.json](test_data_records/TC-000695.json) |
| **Matrix** | BR [5705](../traceability_matrix_full.json#L5705) · TS [5724](../traceability_matrix_full.json#L5724) · TC [5742](../traceability_matrix_full.json#L5742) · TD [5761](../traceability_matrix_full.json#L5761) (`qe_shaped_test_data/TC-000695.json`) · flat_rows [4373004](../traceability_matrix_full.json#L4373004) · also listed under 2 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000695.txt) |

### S6 - Electronic Mail Request - `TC-000716`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:011` - "Block Number must be one of: Public Key File Load., the Electronic Mail response as the next, block., sending that Block Number in the next, Electronic Mail request or CA Public Key, File Load request.." (spec p.363) |
| **TS** | `SC-0728` (positive) - Positive — Electronic Mail Request — Block Number=000, Information Byte=0, Segment Type=100 |
| **TC** | `TC-000716` · Electronic Mail Request · P3 · verification **PASS** · completeness `COMPLETE` · **human-approved** · Response Code not asserted (`INFER`) · `test_case_candidates.json` line 1000470 |
| **TD** | [TC-000716.json](test_data_records/TC-000716.json) |
| **Matrix** | BR [5705](../traceability_matrix_full.json#L5705) · TS [7635](../traceability_matrix_full.json#L7635) · TC [7653](../traceability_matrix_full.json#L7653) · TD [7672](../traceability_matrix_full.json#L7672) (`qe_shaped_test_data/TC-000716.json`) · flat_rows [4373487](../traceability_matrix_full.json#L4373487) · also listed under 2 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000716.txt) |

### S7 - FSA/HRA Purchase/Capture - `TC-000769`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:083` - "Product Code must be one of: 000, 001, 002, 003, 004, 005, 006, 007, 008, 009, 010, 011, 012, 013, 014, 015, 016, 017, 018, 019, 020, 021, 022, 023, 024, 025, 026, 027, 028, 029, 030, 031, 032, 033, 034, 035, 036, 037, 038, 039, 040, 041, 042, 043, 044, 045, 046, 047, 048, 049, 050, 051, 052, 053, 054, 055, 056, 057, 058, 059, 060, 061, 062, 063, 064, 065, 066, 067, 068, 069, 070, 099, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 149, 150, 151, 152, 153, 154, 155, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 224, 225, 226, 227, 228, 229, 230, 249, 250, 251, 252, 253, 254, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 399, 400, 401, 410, 411, 412, 420, 421, 422, 423, 430, 431, 432, 433, 434, 440, 441, 442, 443, 450, 460, 461, 462, 463, 470, 471, 472, 473, 474, 475, 480, 481, 482, 483, 484, 490, 491, 492, 493, 500, 510, 511, 512, 513, 520, 521, 522, 523, 524, 525, 526, 527, 528, 529, 530, 531, 532, 533, 534, 540, 550, 560, 561, 562, 563, 564, 565, 566, 567, 568, 569, 570, 571, 572, 573, 574, 575, 600, 601, 602, 603, 604, 605, 624, 650, 651, 652, 653, 654, 655, 656, 657, 800, 801, 802, 803, 804, 805, 806, 807, 808, 809, 890, 891, 892, 893, 894, 895, 896, 900, 901, 902, 903, 904, 905, 906, 907, 908, 909, 910, 911, 912, 913, 914, 949, 950, 951, 952, 953, 954, 955, 956, 957, 958, 959, 960, 961, 962, 963, 964, 965, 966, 967, 968, 969, 970, 999." (spec p.393) |
| **TS** | `SC-0781` (positive) - Positive — FSA/HRA Purchase/Capture — Product Code=560, Segment Type=100, Service Level=F, Unit of Measure=C |
| **TC** | `TC-000769` · FSA/HRA Purchase/Capture · P3 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 1015188 |
| **TD** | [TC-000769.json](test_data_records/TC-000769.json) |
| **Matrix** | BR [583470](../traceability_matrix_full.json#L583470) · TS [612841](../traceability_matrix_full.json#L612841) · TC [612860](../traceability_matrix_full.json#L612860) · TD [612874](../traceability_matrix_full.json#L612874) (`qe_shaped_test_data/TC-000769.json`) · flat_rows [4516524](../traceability_matrix_full.json#L4516524) · also listed under 3 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000769.txt) |

### S8 - FSA/HRA Transaction Request - `TC-000872`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:127` - "Variable Information
Indicator must be one of: 002 Program Identifier indicator, indicator, 006 Currency code indicator, number indicator, 009 Partial Approval Indicator, 011 Tax Collector indicator, 012 Market-Specific Data indicator, 013 Special Payments indicator, 014 User Data Information, indicator, 016 Visa payWaveTM card data indicator, 017 Suspected Fraud Reversal indicator, Indicator, 019 Recharge Type Information, 020 Top Up Phone Number Information, 021 Settlement Data Acceptance Flag, 022 Originating Device Type Indicator, 024 Manual Check MICR Type, 025 Gift Card Number, 026 Order ID, 027 Customer Name, 028 Use Unique ID, 030 POS Condition Code, 032 POS Additional Data Information, 035 Verified By Visa® Data, 036 MasterCard UCAF Data, 037 MCX Checkout Token, 038 Paydiant Tender ID, 039 Fraud Enhanced Data, 040 Final Amount Indicator, 041 Country Subdivision, 042 Cardholder Language, 043 Cardholder Postal Code (for AVS), 044 Original Transaction Identifier, 045 First Auth Amount, 046 Previous Transaction ID, 047 Soft Descriptor - Merchant Name, 048 MasterCard Authentication Data, 049 Merchant Supplementary Data, 050 Additional Security Code, 051 Additional Card ID, 052 Additional TransArmor Data, 053 Remote Commerce Acceptor Identifier, 054 Merchant Tran ID, 055 Experience Provider Value, 057 Soft Descriptor Merchant City, 058 Soft Descriptor Merchant State, 059 Soft Descriptor Merchant Country, 062 Customer Additional Information, 063 Visa® Incremental Auth Lodging Data, 064 Service Location Information, 065 Merchant Payment Gateway ID Data, 066 Digital Commerce Data, 067 Anticipated Amount, 068 Additional Transaction Fee 1, 069 Additional Transaction Fee 2, 070 Anticipated Completion Amount, 071 Enabler Verification Value, 072 Transaction Link Identifier, 073 Future Transaction Amount, 075 Discount Program Data, 076 Discount Line-Item Data, 077 Discount Amount, 078 Line Item, 079 Visa Commercial Card Data, 080 Staged Wallet ID, 081 Visa Gateway ID." (spec p.415) |
| **TS** | `SC-0884` (positive) - Positive — FSA/HRA Transaction Request — Variable Information Indicator=001, Variable Information=01, Segment Type=100 |
| **TC** | `TC-000872` · FSA/HRA Transaction Request · P3 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 1040093 |
| **TD** | [TC-000872.json](test_data_records/TC-000872.json) |
| **Matrix** | BR [1248829](../traceability_matrix_full.json#L1248829) · TS [1320494](../traceability_matrix_full.json#L1320494) · TC [1320512](../traceability_matrix_full.json#L1320512) · TD [1320526](../traceability_matrix_full.json#L1320526) (`qe_shaped_test_data/TC-000872.json`) · flat_rows [4558982](../traceability_matrix_full.json#L4558982) · also listed under 2 other requirement(s) (see extract) · [extract](matrix_extracts/TC-000872.txt) |

### S9 - Financial Transaction - `TC-001039`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:121` - "Unit of Measure must be one of: C Case/Carton, G Gallon, H Hours, I Imperial Gallons, K Kilogram, L Liter, M Charging Minutes (EV), P Pound, Q Quart, U Unit, W Kilowatt per hour (EV), Z Ounce, O Other/Unknown unit, 0-9 Reserved for private use., Other Reserved for future use.." (spec p.413) |
| **TS** | `SC-1051` (positive) - Positive — Financial Transaction — Unit of Measure=L, EBT Program Data=This element contains two subelements:, WIC Discount Amount=952, Product Code=000, Segment Type=100, Service Level=F |
| **TC** | `TC-001039` · Financial Transaction · P3 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 1164681 |
| **TD** | [TC-001039.json](test_data_records/TC-001039.json) |
| **Matrix** | BR [1139141](../traceability_matrix_full.json#L1139141) · TS [1183176](../traceability_matrix_full.json#L1183176) · TC [1183197](../traceability_matrix_full.json#L1183197) · TD [1183211](../traceability_matrix_full.json#L1183211) (`qe_shaped_test_data/TC-001039.json`) · flat_rows [4520342](../traceability_matrix_full.json#L4520342) · `response_side_test_cases["Financial Transaction Response"]` line [5795772](../traceability_matrix_full.json#L5795772) · also listed under 5 other requirement(s) (see extract) · [extract](matrix_extracts/TC-001039.txt) |

### S10 - Financial Transaction Request - `TC-001329`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:173` - "Payment Tender Type must be one of: AX American Express, CK Check, account inquiry), DB Debit, DN Diners Club®, DS Discover® Network, EB EBT Food Stamps, EC EBT Cash Benefits, FL Fleet, GC Gift Card, JC JCB®, MC MasterCard, PC Phone Card, PR Proprietary, VS Visa®." (spec p.443) |
| **TS** | `SC-1341` (positive) - Positive — Financial Transaction Request — Payment Tender Type=MC, EBT Program Data=5-32, Safekey Data=Sample Data Format, WIC Discount Amount=012, Segment Type=105, Totals Date=222222, Unit of Measure=C, Update Code=A, Variable Information=00, Variable Information Indicator=001, Block Number=000, Card Discretionary Block Data=depends upon the application., Card Label=CC, Currency Code=008, Information Byte=?, Load Type=P, Product Code=000, Service Level=F, Partial Approval Indicator=0, State Code=AL, Check Type=P, Loyalty Information Version=1, CAVV Revised Format=Position Description, Token Requestor ID=Position Description, Cryptogram Token Data=Position Description, Inclusive/Exclusive for Tax 1=I, Tax Type 1=GST, Inclusive/Exclusive for Tax 2=I, Tax Type 2=GST Goods and Services Tax, Inclusive/Exclusive for Tax 3=I, Tax Type 3=GST Goods and Services Tax |
| **TC** | `TC-001329` · Financial Transaction Request · P2 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code `0` Approved-Purchase/Capture (`ASSERT`) · `test_case_candidates.json` line 1913502 |
| **TD** | [TC-001329.json](test_data_records/TC-001329.json) |
| **Matrix** | BR [1997114](../traceability_matrix_full.json#L1997114) · TS [2063473](../traceability_matrix_full.json#L2063473) · TC [2063519](../traceability_matrix_full.json#L2063519) · TD [2063533](../traceability_matrix_full.json#L2063533) (`qe_shaped_test_data/TC-001329.json`) · flat_rows [4378616](../traceability_matrix_full.json#L4378616) · `response_side_test_cases["Financial Transaction Response"]` line [5814912](../traceability_matrix_full.json#L5814912) · also listed under 30 other requirement(s) (see extract) · [extract](matrix_extracts/TC-001329.txt) |

### S11 - Financial Transactions - `TC-001454`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:011` - "Block Number must be one of: Public Key File Load., the Electronic Mail response as the next, block., sending that Block Number in the next, Electronic Mail request or CA Public Key, File Load request.." (spec p.363) |
| **TS** | `SC-1478` (positive) - Positive — Financial Transactions — Block Number=001, Information Byte=?, Payment Tender Type=EB, Segment Type=102, State Code=AL, Update Code=E, Check Type=P, Loyalty Information Version=1 |
| **TC** | `TC-001454` · Financial Transactions · P3 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 2300774 |
| **TD** | [TC-001454.json](test_data_records/TC-001454.json) |
| **Matrix** | BR [5705](../traceability_matrix_full.json#L5705) · TS [46594](../traceability_matrix_full.json#L46594) · TC [46617](../traceability_matrix_full.json#L46617) · TD [46631](../traceability_matrix_full.json#L46631) (`qe_shaped_test_data/TC-001454.json`) · flat_rows [4381491](../traceability_matrix_full.json#L4381491) · also listed under 7 other requirement(s) (see extract) · [extract](matrix_extracts/TC-001454.txt) |

### S12 - Financial Transactions (Data Section No. 3) - `TC-001562`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:095` - "Segment Type must be one of: Segment, Segment, Load Data Segment, Segment, Segment, Segment." (spec p.401) |
| **TS** | `SC-1586` (positive) - Positive — Financial Transactions (Data Section No. 3) — Segment Type=100 |
| **TC** | `TC-001562` · Financial Transactions (Data Section No. 3) · P3 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 2418249 |
| **TD** | [TC-001562.json](test_data_records/TC-001562.json) |
| **Matrix** | BR [697846](../traceability_matrix_full.json#L697846) · TS [850737](../traceability_matrix_full.json#L850737) · TC [850753](../traceability_matrix_full.json#L850753) · TD [850767](../traceability_matrix_full.json#L850767) (`qe_shaped_test_data/TC-001562.json`) · flat_rows [4575404](../traceability_matrix_full.json#L4575404) · [extract](matrix_extracts/TC-001562.txt) |

### S13 - Key Load - `TC-001568`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:095` - "Segment Type must be one of: Segment, Segment, Load Data Segment, Segment, Segment, Segment." (spec p.401) |
| **TS** | `SC-1592` (positive) - Positive — Key Load — Segment Type=100 |
| **TC** | `TC-001568` · Key Load · P3 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 2419485 |
| **TD** | [TC-001568.json](test_data_records/TC-001568.json) |
| **Matrix** | BR [697846](../traceability_matrix_full.json#L697846) · TS [851145](../traceability_matrix_full.json#L851145) · TC [851161](../traceability_matrix_full.json#L851161) · TD [851175](../traceability_matrix_full.json#L851175) (`qe_shaped_test_data/TC-001568.json`) · flat_rows [4575542](../traceability_matrix_full.json#L4575542) · [extract](matrix_extracts/TC-001568.txt) |

### S14 - Loyalty Card Transaction - `TC-001587`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:013` - "Card Discretionary
Block Data must be one of: depends upon the application., for expired cards., (MMDDYY)., transactions associated with a Token., included as usual.." (spec p.364) |
| **TS** | `SC-1611` (positive) - Positive — Loyalty Card Transaction — Card Discretionary Block Data=depends upon the application., Payment Tender Type=JC, Segment Type=105, Update Code=S, Information Byte=1, Partial Approval Indicator=1, Loyalty Information Version=1 |
| **TC** | `TC-001587` · Loyalty Card Transaction · P3 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · **human-approved** · Response Code `0` Approved-Purchase/Capture (`ASSERT`) · `test_case_candidates.json` line 2428872 |
| **TD** | [TC-001587.json](test_data_records/TC-001587.json) |
| **Matrix** | BR [66252](../traceability_matrix_full.json#L66252) · TS [171097](../traceability_matrix_full.json#L171097) · TC [171119](../traceability_matrix_full.json#L171119) · TD [171133](../traceability_matrix_full.json#L171133) (`qe_shaped_test_data/TC-001587.json`) · flat_rows [4411736](../traceability_matrix_full.json#L4411736) · also listed under 6 other requirement(s) (see extract) · [extract](matrix_extracts/TC-001587.txt) |

### S15 - Loyalty Card Transaction Request - `TC-001727`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:013` - "Card Discretionary
Block Data must be one of: depends upon the application., for expired cards., (MMDDYY)., transactions associated with a Token., included as usual.." (spec p.364) |
| **TS** | `SC-1751` (positive) - Positive — Loyalty Card Transaction Request — Card Discretionary Block Data=for expired cards., Payment Tender Type=VS, Update Code=U, Information Byte=?, Segment Type=101, Partial Approval Indicator=5, Loyalty Information Version=1 |
| **TC** | `TC-001727` · Loyalty Card Transaction Request · P3 · verification **PASS** · completeness `INCOMPLETE_PARAMETER_NOT_PLACED` · **human-approved** · Response Code `0` Approved-Purchase/Capture (`ASSERT`) · `test_case_candidates.json` line 2520007 |
| **TD** | [TC-001727.json](test_data_records/TC-001727.json) |
| **Matrix** | BR [66252](../traceability_matrix_full.json#L66252) · TS [183697](../traceability_matrix_full.json#L183697) · TC [183719](../traceability_matrix_full.json#L183719) · TD [183733](../traceability_matrix_full.json#L183733) (`qe_shaped_test_data/TC-001727.json`) · flat_rows [4414956](../traceability_matrix_full.json#L4414956) · also listed under 6 other requirement(s) (see extract) · [extract](matrix_extracts/TC-001727.txt) |

### S16 - Mail Request - `TC-001847`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:052` - "Information Byte must be one of: ? Download Request, 0 Single-message transmission, 1 Multimessage transmission." (spec p.379) |
| **TS** | `SC-1871` (positive) - Positive — Mail Request — Information Byte=0, Segment Type=102, Block Number=000 |
| **TC** | `TC-001847` · Mail Request · P3 · verification **PASS** · completeness `COMPLETE` · **human-approved** · Response Code not asserted (`INFER`) · `test_case_candidates.json` line 2593992 |
| **TD** | [TC-001847.json](test_data_records/TC-001847.json) |
| **Matrix** | BR [348795](../traceability_matrix_full.json#L348795) · TS [491613](../traceability_matrix_full.json#L491613) · TC [491631](../traceability_matrix_full.json#L491631) · TD [491650](../traceability_matrix_full.json#L491650) (`qe_shaped_test_data/TC-001847.json`) · flat_rows [4384274](../traceability_matrix_full.json#L4384274) · also listed under 2 other requirement(s) (see extract) · [extract](matrix_extracts/TC-001847.txt) |

### S17 - Proprietary Data Load Request - `TC-001855`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:011` - "Block Number must be one of: Public Key File Load., the Electronic Mail response as the next, block., sending that Block Number in the next, Electronic Mail request or CA Public Key, File Load request.." (spec p.363) |
| **TS** | `SC-1879` (positive) - Positive — Proprietary Data Load Request — Block Number=000, Card Table Type=0001, Information Byte=0, Prompt Code, Pending=0901, Segment Type=100 |
| **TC** | `TC-001855` · Proprietary Data Load Request · P3 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`INFER`) · `test_case_candidates.json` line 2596633 |
| **TD** | [TC-001855.json](test_data_records/TC-001855.json) |
| **Matrix** | BR [5705](../traceability_matrix_full.json#L5705) · TS [58333](../traceability_matrix_full.json#L58333) · TC [58353](../traceability_matrix_full.json#L58353) · TD [58371](../traceability_matrix_full.json#L58371) (`qe_shaped_test_data/TC-001855.json`) · flat_rows [4384458](../traceability_matrix_full.json#L4384458) · `response_side_test_cases["Proprietary Data Load Response"]` line [6068388](../traceability_matrix_full.json#L6068388) · also listed under 4 other requirement(s) (see extract) · [extract](matrix_extracts/TC-001855.txt) |

### S18 - Totals Request - `TC-005134`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:049` - "Grand Total must be numeric and must not exceed 8 bytes." (spec p.379) |
| **TS** | `SC-5501` (boundary) - Boundary — Totals Request — Grand Total = exactly 8 bytes (documented max) |
| **TC** | `TC-005134` · Totals Request · P3 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`INFER`) · `test_case_candidates.json` line 10124348 |
| **TD** | [TC-005134.json](test_data_records/TC-005134.json) |
| **Matrix** | BR [341783](../traceability_matrix_full.json#L341783) · TS [342642](../traceability_matrix_full.json#L342642) · TC [342658](../traceability_matrix_full.json#L342658) · TD [342679](../traceability_matrix_full.json#L342679) (`qe_shaped_test_data/TC-005134.json`) · flat_rows [4451549](../traceability_matrix_full.json#L4451549) · `response_side_test_cases["Totals Response"]` line [6103251](../traceability_matrix_full.json#L6103251) · [extract](matrix_extracts/TC-005134.txt) |
| **Note** | manual pick: all positive Totals scenarios failed / need review by the LLM judge; Totals-specific boundary used |

### S19 - Totals with Proprietary Data Load Request - `TC-005150`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:049` - "Grand Total must be numeric and must not exceed 8 bytes." (spec p.379) |
| **TS** | `SC-5519` (boundary) - Boundary — Totals with Proprietary Data Load Request — Grand Total = exactly 8 bytes (documented max) |
| **TC** | `TC-005150` · Totals with Proprietary Data Load Request · P3 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`INFER`) · `test_case_candidates.json` line 10131220 |
| **TD** | [TC-005150.json](test_data_records/TC-005150.json) |
| **Matrix** | BR [341783](../traceability_matrix_full.json#L341783) · TS [342733](../traceability_matrix_full.json#L342733) · TC [342749](../traceability_matrix_full.json#L342749) · TD [342770](../traceability_matrix_full.json#L342770) (`qe_shaped_test_data/TC-005150.json`) · flat_rows [4451572](../traceability_matrix_full.json#L4451572) · [extract](matrix_extracts/TC-005150.txt) |
| **Note** | manual pick: all positive Totals scenarios failed / need review by the LLM judge; Totals-specific boundary used |

### S20 - TransArmor Key and Key ID Load - `TC-005852`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:648` - "This track data storage restriction does not apply to TransArmor-Verifone Edition processing." (spec p.103) |
| **TS** | `SC-6022` (business_rule) - Business Rule — BR-103-8: This track data storage restriction does not apply to TransArmor-Verifone Edition processing. |
| **TC** | `TC-005852` · TransArmor Key and Key ID Load · P3 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 11078765 |
| **TD** | [TC-005852.json](test_data_records/TC-005852.json) |
| **Matrix** | BR [4217211](../traceability_matrix_full.json#L4217211) · TS [4217230](../traceability_matrix_full.json#L4217230) · TC [4217244](../traceability_matrix_full.json#L4217244) · TD [4217258](../traceability_matrix_full.json#L4217258) (`qe_shaped_test_data/TC-005852.json`) · flat_rows [5506007](../traceability_matrix_full.json#L5506007) · [extract](matrix_extracts/TC-005852.txt) |

### M1 - FLOW-AUTH-CANCELLATION - `TC-004475`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4820` (flow) - Flow — Authorization followed by Cancellation (Financial Transaction Request → Financial Transaction Request) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004475` · Financial Transaction Request → Financial Transaction Request · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 7111961 |
| **Legs** | step 1 authorization (Financial Transaction Request) → RC 2; step 2 cancellation (Financial Transaction Request) → RC 2, Approval Number ← step 1, Prompt Code ← step 1 |
| **TD** | [TC-004475.step1.authorization.json](test_data_records/TC-004475.step1.authorization.json)<br>[TC-004475.step2.cancellation.json](test_data_records/TC-004475.step2.cancellation.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [918674](../traceability_matrix_full.json#L918674) · TC [918690](../traceability_matrix_full.json#L918690) · TD [918704](../traceability_matrix_full.json#L918704) (`qe_shaped_test_data/TC-004475.step1.authorization.json`) · flat_rows [4594540](../traceability_matrix_full.json#L4594540) · [extract](matrix_extracts/TC-004475.txt) |

### M2 - FLOW-AUTH-COMPLETION - `TC-004348`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4693` (flow) - Flow — Authorization followed by Completion (Financial Transaction Request → Purchase/Capture) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004348` · Financial Transaction Request → Purchase/Capture · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 5893591 |
| **Legs** | step 1 authorization (Financial Transaction Request) → RC 2; step 2 completion (Purchase/Capture) → RC 0, Approval Number ← step 1 |
| **TD** | [TC-004348.step1.authorization.json](test_data_records/TC-004348.step1.authorization.json)<br>[TC-004348.step2.completion.json](test_data_records/TC-004348.step2.completion.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [918254](../traceability_matrix_full.json#L918254) · TC [918270](../traceability_matrix_full.json#L918270) · TD [918284](../traceability_matrix_full.json#L918284) (`qe_shaped_test_data/TC-004348.step1.authorization.json`) · flat_rows [4594425](../traceability_matrix_full.json#L4594425) · [extract](matrix_extracts/TC-004348.txt) |

### M3 - FLOW-AUTH-COMPLETION-DUPLICATE - `TC-004408`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:020` - "Cash Amount must be numeric and must not exceed 8 bytes." (spec p.368) |
| **TS** | `SC-4753` (flow) - Flow — Authorization, Completion, then duplicate Completion (Financial Transaction Request → Purchase/Capture → Purchase/Capture) [boundary-at-limit-cash-amount] |
| **TC** | `TC-004408` · Financial Transaction Request → Purchase/Capture → Purchase/Capture · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 6428053 |
| **Legs** | step 1 authorization (Financial Transaction Request) → RC 2; step 2 completion (Purchase/Capture) → RC 0, Approval Number ← step 1; step 3 duplicate_completion (Purchase/Capture) → RC 0, Approval Number ← step 1 |
| **TD** | [TC-004408.step1.authorization.json](test_data_records/TC-004408.step1.authorization.json)<br>[TC-004408.step2.completion.json](test_data_records/TC-004408.step2.completion.json)<br>[TC-004408.step3.duplicate-completion.json](test_data_records/TC-004408.step3.duplicate-completion.json) |
| **Matrix** | BR [261259](../traceability_matrix_full.json#L261259) · TS [263126](../traceability_matrix_full.json#L263126) · TC [263142](../traceability_matrix_full.json#L263142) · TD [263156](../traceability_matrix_full.json#L263156) (`qe_shaped_test_data/TC-004408.step1.authorization.json`) · flat_rows [4433011](../traceability_matrix_full.json#L4433011) · [extract](matrix_extracts/TC-004408.txt) |

### M4 - FLOW-AUTH-COMPLETION-VOID - `TC-004528`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4873` (flow) - Flow — Authorization, Completion, then Void (Financial Transaction Request → Purchase/Capture → Purchase Reversal) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004528` · Financial Transaction Request → Purchase/Capture → Purchase Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 7600929 |
| **Legs** | step 1 authorization (Financial Transaction Request) → RC 2; step 2 completion (Purchase/Capture) → RC 0, Approval Number ← step 1; step 3 void (Purchase Reversal) → RC not asserted, Approval Number ← step 2 |
| **TD** | [TC-004528.step1.authorization.json](test_data_records/TC-004528.step1.authorization.json)<br>[TC-004528.step2.completion.json](test_data_records/TC-004528.step2.completion.json)<br>[TC-004528.step3.void.json](test_data_records/TC-004528.step3.void.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [918758](../traceability_matrix_full.json#L918758) · TC [918774](../traceability_matrix_full.json#L918774) · TD [918788](../traceability_matrix_full.json#L918788) (`qe_shaped_test_data/TC-004528.step1.authorization.json`) · flat_rows [4594563](../traceability_matrix_full.json#L4594563) · [extract](matrix_extracts/TC-004528.txt) |

### M5 - FLOW-AUTH-PARTIAL-COMPLETION - `TC-004376`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:020` - "Cash Amount must be numeric and must not exceed 8 bytes." (spec p.368) |
| **TS** | `SC-4721` (flow) - Flow — Authorization followed by Partial Completion (Financial Transaction Request → Purchase/Capture) [boundary-at-limit-cash-amount] |
| **TC** | `TC-004376` · Financial Transaction Request → Purchase/Capture · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 6143114 |
| **Legs** | step 1 authorization (Financial Transaction Request) → RC 2; step 2 partial_completion (Purchase/Capture) → RC 0, Approval Number ← step 1 |
| **TD** | [TC-004376.step1.authorization.json](test_data_records/TC-004376.step1.authorization.json)<br>[TC-004376.step2.partial-completion.json](test_data_records/TC-004376.step2.partial-completion.json) |
| **Matrix** | BR [261259](../traceability_matrix_full.json#L261259) · TS [263042](../traceability_matrix_full.json#L263042) · TC [263058](../traceability_matrix_full.json#L263058) · TD [263072](../traceability_matrix_full.json#L263072) (`qe_shaped_test_data/TC-004376.step1.authorization.json`) · flat_rows [4432988](../traceability_matrix_full.json#L4432988) · [extract](matrix_extracts/TC-004376.txt) |

### M6 - FLOW-AUTH-VOID - `TC-004502`

| Layer | Value |
|-------|-------|
| **BR** | Test case has **no** `requirement_id`. The matrix lists it under `REQ-SRC-ATL105-PDF-001:6786` - "Sequence Number must be present on all transaction types." (p.402) - via `link_basis: relinked_via_source_rule_id` (source rule `ENT-ELEM-86`) |
| **TS** | `SC-4847` (flow) - Flow — Authorization reversed with the wrong Transaction Type code (negative) (Financial Transaction Request → Purchase Reversal) [typical-visa] |
| **TC** | `TC-004502` · Financial Transaction Request → Purchase Reversal · P1 · verification **PASS** · completeness `DELIBERATE_OMISSION` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 7369417 |
| **Legs** | step 1 authorization (Financial Transaction Request) → RC 2; step 2 void (Purchase Reversal) → RC not asserted |
| **TD** | [TC-004502.step1.authorization.json](test_data_records/TC-004502.step1.authorization.json)<br>[TC-004502.step2.void.json](test_data_records/TC-004502.step2.void.json) |
| **Matrix** | BR [4255140](../traceability_matrix_full.json#L4255140) · TS [4264926](../traceability_matrix_full.json#L4264926) · TC [4264942](../traceability_matrix_full.json#L4264942) · TD [4264956](../traceability_matrix_full.json#L4264956) (`qe_shaped_test_data/TC-004502.step1.authorization.json`) · flat_rows [5524016](../traceability_matrix_full.json#L5524016) · [extract](matrix_extracts/TC-004502.txt) |

### M7 - FLOW-DUPLICATE-SEQUENCE-NUMBER-VIOLATION - `TC-004649`

| Layer | Value |
|-------|-------|
| **BR** | **None** - test case has no `requirement_id` (source rule `BR-new-sequence-per-transaction`) and is not in the matrix |
| **TS** | `SC-4994` (flow) - Flow — Two independent transactions forced to share a Sequence Number (negative) (Financial Transaction Request → Financial Transaction Request) [typical-visa] |
| **TC** | `TC-004649` · Financial Transaction Request → Financial Transaction Request · P1 · verification **PASS** · completeness `DELIBERATE_OMISSION` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 8841741 |
| **Legs** | step 1 first_independent_transaction (Financial Transaction Request) → RC 0; step 2 second_independent_transaction_duplicate_sequence (Financial Transaction Request) → RC 0 |
| **TD** | [TC-004649.step1.first-independent-transaction.json](test_data_records/TC-004649.step1.first-independent-transaction.json)<br>[TC-004649.step2.second-independent-transaction-duplicate-sequence.json](test_data_records/TC-004649.step2.second-independent-transaction-duplicate-sequence.json) |
| **Matrix** | ❌ test case not present; scenario `SC-4994` only under `orphans.scenarios_without_requirement` at line [4370949](../traceability_matrix_full.json#L4370949) (reason `UNRESOLVED_REQUIREMENT`) · [extract](matrix_extracts/TC-004649.txt) |

### M8 - FLOW-EMV-AUTH-CANCELLATION - `TC-004700`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-5045` (flow) - Flow — EMV Authorization followed by Cancellation (EMV Financial Transaction Request → Financial Transaction Request) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004700` · EMV Financial Transaction Request → Financial Transaction Request · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 9264457 |
| **Legs** | step 1 emv_authorization (EMV Financial Transaction Request) → RC 2; step 2 cancellation (Financial Transaction Request) → RC 0, Approval Number ← step 1, Prompt Code ← step 1 |
| **TD** | [TC-004700.step1.emv-authorization.json](test_data_records/TC-004700.step1.emv-authorization.json)<br>[TC-004700.step2.cancellation.json](test_data_records/TC-004700.step2.cancellation.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [919346](../traceability_matrix_full.json#L919346) · TC [919362](../traceability_matrix_full.json#L919362) · TD [919376](../traceability_matrix_full.json#L919376) (`qe_shaped_test_data/TC-004700.step1.emv-authorization.json`) · flat_rows [4594724](../traceability_matrix_full.json#L4594724) · [extract](matrix_extracts/TC-004700.txt) |

### M9 - FLOW-EMV-AUTH-COMPLETION - `TC-004666`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-5011` (flow) - Flow — EMV Authorization followed by Completion (EMV Financial Transaction Request → Purchase/Capture) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004666` · EMV Financial Transaction Request → Purchase/Capture · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 9008670 |
| **Legs** | step 1 emv_authorization (EMV Financial Transaction Request) → RC 2; step 2 completion (Purchase/Capture) → RC 0, Approval Number ← step 1 |
| **TD** | [TC-004666.step1.emv-authorization.json](test_data_records/TC-004666.step1.emv-authorization.json)<br>[TC-004666.step2.completion.json](test_data_records/TC-004666.step2.completion.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [919178](../traceability_matrix_full.json#L919178) · TC [919194](../traceability_matrix_full.json#L919194) · TD [919208](../traceability_matrix_full.json#L919208) (`qe_shaped_test_data/TC-004666.step1.emv-authorization.json`) · flat_rows [4594678](../traceability_matrix_full.json#L4594678) · [extract](matrix_extracts/TC-004666.txt) |

### M10 - FLOW-EMV-SALE-VOID - `TC-004683`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-5028` (flow) - Flow — EMV Purchase followed by Void or Reversal (EMV Financial Transaction Request → Purchase Reversal) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004683` · EMV Financial Transaction Request → Purchase Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 9136555 |
| **Legs** | step 1 emv_sale (EMV Financial Transaction Request) → RC 0; step 2 void (Purchase Reversal) → RC not asserted, Approval Number ← step 1 |
| **TD** | [TC-004683.step1.emv-sale.json](test_data_records/TC-004683.step1.emv-sale.json)<br>[TC-004683.step2.void.json](test_data_records/TC-004683.step2.void.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [919262](../traceability_matrix_full.json#L919262) · TC [919278](../traceability_matrix_full.json#L919278) · TD [919292](../traceability_matrix_full.json#L919292) (`qe_shaped_test_data/TC-004683.step1.emv-sale.json`) · flat_rows [4594701](../traceability_matrix_full.json#L4594701) · [extract](matrix_extracts/TC-004683.txt) |

### M11 - FLOW-EMV-TIMEOUT-REVERSAL - `TC-004717`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-5062` (flow) - Flow — EMV transaction followed by Time-out Reversal (EMV Financial Transaction Request → Time-out Reversal) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004717` · EMV Financial Transaction Request → Time-out Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 9402879 |
| **Legs** | step 1 emv_financial_transaction (EMV Financial Transaction Request) → RC 0; step 2 timeout_reversal (Time-out Reversal) → RC not asserted |
| **TD** | [TC-004717.step1.emv-financial-transaction.json](test_data_records/TC-004717.step1.emv-financial-transaction.json)<br>[TC-004717.step2.timeout-reversal.json](test_data_records/TC-004717.step2.timeout-reversal.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [919430](../traceability_matrix_full.json#L919430) · TC [919446](../traceability_matrix_full.json#L919446) · TD [919460](../traceability_matrix_full.json#L919460) (`qe_shaped_test_data/TC-004717.step1.emv-financial-transaction.json`) · flat_rows [4594747](../traceability_matrix_full.json#L4594747) · [extract](matrix_extracts/TC-004717.txt) |

### M12 - FLOW-ESTIMATED-INCREMENTAL-AUTH - `TC-004664`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-5009` (flow) - Flow — Visa Estimated/Initial Authorization followed by Incremental Authorization then Completion (Financial Transaction Request → Financial Transaction Request → Purchase/Capture) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004664` · Financial Transaction Request → Financial Transaction Request → Purchase/Capture · P2 · verification **PASS** · completeness `INCOMPLETE_UNRESOLVED` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 8984360 |
| **Legs** | step 1 estimated_or_initial_authorization (Financial Transaction Request) → RC 2; step 2 incremental_authorization (Financial Transaction Request) → RC 2, Approval Number ← step 1; step 3 completion (Purchase/Capture) → RC 0, Approval Number ← step 1 |
| **TD** | [TC-004664.step1.estimated-or-initial-authorization.json](test_data_records/TC-004664.step1.estimated-or-initial-authorization.json)<br>[TC-004664.step2.incremental-authorization.json](test_data_records/TC-004664.step2.incremental-authorization.json)<br>[TC-004664.step3.completion.json](test_data_records/TC-004664.step3.completion.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [919094](../traceability_matrix_full.json#L919094) · TC [919110](../traceability_matrix_full.json#L919110) · TD [919124](../traceability_matrix_full.json#L919124) (`qe_shaped_test_data/TC-004664.step1.estimated-or-initial-authorization.json`) · flat_rows [4594655](../traceability_matrix_full.json#L4594655) · [extract](matrix_extracts/TC-004664.txt) |

### M13 - FLOW-LOYALTY-SALE-REVERSAL - `TC-004599`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:177` - "Unit of Work must be numeric and must not exceed 19 bytes." (spec p.444) |
| **TS** | `SC-4944` (flow) - Flow — Loyalty Sale followed by Reversal (Financial Transaction Request → Purchase Reversal) [boundary-at-limit-unit-of-work] |
| **TC** | `TC-004599` · Financial Transaction Request → Purchase Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 8367759 |
| **Legs** | step 1 sale (Financial Transaction Request) → RC 0; step 2 reversal (Purchase Reversal) → RC not asserted, Approval Number ← step 1 |
| **TD** | [TC-004599.step1.sale.json](test_data_records/TC-004599.step1.sale.json)<br>[TC-004599.step2.reversal.json](test_data_records/TC-004599.step2.reversal.json) |
| **Matrix** | BR [2233409](../traceability_matrix_full.json#L2233409) · TS [2234940](../traceability_matrix_full.json#L2234940) · TC [2234956](../traceability_matrix_full.json#L2234956) · TD [2234970](../traceability_matrix_full.json#L2234970) (`qe_shaped_test_data/TC-004599.step1.sale.json`) · flat_rows [4915781](../traceability_matrix_full.json#L4915781) · [extract](matrix_extracts/TC-004599.txt) |

### M14 - FLOW-RETURN-VOID - `TC-004440`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4785` (flow) - Flow — Return followed by Void of return (Financial Transaction Request → Purchase Reversal) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004440` · Financial Transaction Request → Purchase Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 6800614 |
| **Legs** | step 1 return (Financial Transaction Request) → RC not asserted; step 2 void (Purchase Reversal) → RC not asserted, Approval Number ← step 1 |
| **TD** | [TC-004440.step1.return.json](test_data_records/TC-004440.step1.return.json)<br>[TC-004440.step2.void.json](test_data_records/TC-004440.step2.void.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [918506](../traceability_matrix_full.json#L918506) · TC [918522](../traceability_matrix_full.json#L918522) · TD [918536](../traceability_matrix_full.json#L918536) (`qe_shaped_test_data/TC-004440.step1.return.json`) · flat_rows [4594494](../traceability_matrix_full.json#L4594494) · [extract](matrix_extracts/TC-004440.txt) |

### M15 - FLOW-SALE-VOID - `TC-004555`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4900` (flow) - Flow — Purchase/Sale followed by Void or Reversal (Financial Transaction Request → Purchase Reversal) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004555` · Financial Transaction Request → Purchase Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 7915843 |
| **Legs** | step 1 sale (Financial Transaction Request) → RC 0; step 2 void (Purchase Reversal) → RC not asserted, Approval Number ← step 1 |
| **TD** | [TC-004555.step1.sale.json](test_data_records/TC-004555.step1.sale.json)<br>[TC-004555.step2.void.json](test_data_records/TC-004555.step2.void.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [918842](../traceability_matrix_full.json#L918842) · TC [918858](../traceability_matrix_full.json#L918858) · TD [918872](../traceability_matrix_full.json#L918872) (`qe_shaped_test_data/TC-004555.step1.sale.json`) · flat_rows [4594586](../traceability_matrix_full.json#L4594586) · [extract](matrix_extracts/TC-004555.txt) |

### M16 - FLOW-TIMEOUT-NEXT-TRANSACTION - `TC-004581`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4926` (flow) - Flow — Original request, Time-out Reversal, then next financial transaction (Financial Transaction Request → Time-out Reversal → Financial Transaction Request) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004581` · Financial Transaction Request → Time-out Reversal → Financial Transaction Request · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 8147769 |
| **Legs** | step 1 original_transaction (Financial Transaction Request) → RC 0; step 2 timeout_reversal (Time-out Reversal) → RC not asserted; step 3 next_transaction (Financial Transaction Request) → RC 0 |
| **TD** | [TC-004581.step1.original-transaction.json](test_data_records/TC-004581.step1.original-transaction.json)<br>[TC-004581.step2.timeout-reversal.json](test_data_records/TC-004581.step2.timeout-reversal.json)<br>[TC-004581.step3.next-transaction.json](test_data_records/TC-004581.step3.next-transaction.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [918926](../traceability_matrix_full.json#L918926) · TC [918942](../traceability_matrix_full.json#L918942) · TD [918956](../traceability_matrix_full.json#L918956) (`qe_shaped_test_data/TC-004581.step1.original-transaction.json`) · flat_rows [4594609](../traceability_matrix_full.json#L4594609) · [extract](matrix_extracts/TC-004581.txt) |

### M17 - FLOW-TIMEOUT-REVERSAL - `TC-004457`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4802` (flow) - Flow — Timed-out transaction followed by Time-out Reversal (Financial Transaction Request → Time-out Reversal) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004457` · Financial Transaction Request → Time-out Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 6952001 |
| **Legs** | step 1 financial_transaction (Financial Transaction Request) → RC 0; step 2 timeout_reversal (Time-out Reversal) → RC not asserted |
| **TD** | [TC-004457.step1.financial-transaction.json](test_data_records/TC-004457.step1.financial-transaction.json)<br>[TC-004457.step2.timeout-reversal.json](test_data_records/TC-004457.step2.timeout-reversal.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [918590](../traceability_matrix_full.json#L918590) · TC [918606](../traceability_matrix_full.json#L918606) · TD [918620](../traceability_matrix_full.json#L918620) (`qe_shaped_test_data/TC-004457.step1.financial-transaction.json`) · flat_rows [4594517](../traceability_matrix_full.json#L4594517) · [extract](matrix_extracts/TC-004457.txt) |

### M18 - FLOW-TOR-RETRY - `TC-004616`

| Layer | Value |
|-------|-------|
| **BR** | `REQ-SRC-ATL105-PDF-001:096` - "Sequence Number must be numeric and must not exceed 6 bytes." (spec p.402) |
| **TS** | `SC-4961` (flow) - Flow — Time-out Reversal retransmitted up to three times (Time-out Reversal → Time-out Reversal → Time-out Reversal) [boundary-at-limit-sequence-number] |
| **TC** | `TC-004616` · Time-out Reversal → Time-out Reversal → Time-out Reversal · P2 · verification **PASS** · completeness `COMPLETE` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 8519197 |
| **Legs** | step 1 timeout_reversal_attempt_1 (Time-out Reversal) → RC not asserted; step 2 timeout_reversal_attempt_2 (Time-out Reversal) → RC not asserted; step 3 timeout_reversal_attempt_3 (Time-out Reversal) → RC not asserted |
| **TD** | [TC-004616.step1.timeout-reversal-attempt-1.json](test_data_records/TC-004616.step1.timeout-reversal-attempt-1.json)<br>[TC-004616.step2.timeout-reversal-attempt-2.json](test_data_records/TC-004616.step2.timeout-reversal-attempt-2.json)<br>[TC-004616.step3.timeout-reversal-attempt-3.json](test_data_records/TC-004616.step3.timeout-reversal-attempt-3.json) |
| **Matrix** | BR [914656](../traceability_matrix_full.json#L914656) · TS [919010](../traceability_matrix_full.json#L919010) · TC [919026](../traceability_matrix_full.json#L919026) · TD [919040](../traceability_matrix_full.json#L919040) (`qe_shaped_test_data/TC-004616.step1.timeout-reversal-attempt-1.json`) · flat_rows [4594632](../traceability_matrix_full.json#L4594632) · [extract](matrix_extracts/TC-004616.txt) |

### M19 - FLOW-TOR-RETRY-FOURTH-INVALID - `TC-004634`

| Layer | Value |
|-------|-------|
| **BR** | **None** - test case has no `requirement_id` (source rule `QE-2026-09-16`) and is not in the matrix |
| **TS** | `SC-4979` (flow) - Flow — Fourth Time-out Reversal attempt (invalid, negative) (Time-out Reversal → Time-out Reversal) [typical-visa] |
| **TC** | `TC-004634` · Time-out Reversal → Time-out Reversal · P2 · verification **PASS** · completeness `DELIBERATE_OMISSION` · Response Code not asserted (`FLAG`) · `test_case_candidates.json` line 8717317 |
| **Legs** | step 1 timeout_reversal_attempt_3 (Time-out Reversal) → RC not asserted; step 2 timeout_reversal_attempt_4_invalid (Time-out Reversal) → RC not asserted |
| **TD** | [TC-004634.step1.timeout-reversal-attempt-3.json](test_data_records/TC-004634.step1.timeout-reversal-attempt-3.json)<br>[TC-004634.step2.timeout-reversal-attempt-4-invalid.json](test_data_records/TC-004634.step2.timeout-reversal-attempt-4-invalid.json) |
| **Matrix** | ❌ test case not present; scenario `SC-4979` only under `orphans.scenarios_without_requirement` at line [4370889](../traceability_matrix_full.json#L4370889) (reason `UNRESOLVED_REQUIREMENT`) · [extract](matrix_extracts/TC-004634.txt) |

---

## D. Findings for the validation

| # | Finding | Test cases |
|---|---------|-----------|
| 1 | **2 multi-leg flows cannot be traced.** None of their 15 test cases has a requirement, and none appears in `traceability_matrix_full.json`; their scenarios are listed only as orphans (`UNRESOLVED_REQUIREMENT`). | `TC-004649` (FLOW-DUPLICATE-SEQUENCE-NUMBER-VIOLATION), `TC-004634` (FLOW-TOR-RETRY-FOURTH-INVALID) |
| 2 | **FLOW-AUTH-VOID has no direct requirement.** The test case has `requirement_id = null`; the matrix attaches it to `REQ-…:6786` "Sequence Number must be present on all transaction types" (an `llm_phrased` requirement) via the source rule. This flow is also a **negative** flow: "Authorization reversed with the wrong Transaction Type code". | `TC-004502` |
| 3 | **Three flows are negative by design** (the whole flow, not a variant): FLOW-AUTH-VOID, FLOW-DUPLICATE-SEQUENCE-NUMBER-VIOLATION, FLOW-TOR-RETRY-FOURTH-INVALID (completeness `DELIBERATE_OMISSION`). | `TC-004502`, `TC-004649`, `TC-004634` |
| 4 | **Matrix lists only the step-1 test data file for multi-leg flows.** Later legs are on disk but not referenced in the matrix. | all M rows |
| 5 | **10 positive single-leg test cases are `INCOMPLETE_PARAMETER_NOT_PLACED`**: some scenario parameters could not be mapped to a message field. No COMPLETE positive alternative exists in the matrix for these transaction types. | `TC-000009`, `TC-000134`, `TC-000275`, `TC-000569`, `TC-000872`, `TC-001039`, `TC-001329`, `TC-001454`, `TC-001587`, `TC-001727` |
| 6 | **Totals: no usable positive scenario.** All positive Totals scenarios are `FAIL` or `NEEDS_REVIEW` by the LLM judge (e.g. `TRACEBACK_MISMATCH`), so boundary tests were used. | `TC-005134`, `TC-005150` |
| 7 | **TransArmor Key and Key ID Load has no positive scenario at all**; a business-rule test case represents it. | `TC-005852` |
| 8 | **FLOW-ESTIMATED-INCREMENTAL-AUTH is `INCOMPLETE_UNRESOLVED`** (a declared flow link could not be resolved); only 2 test cases exist for this flow. | `TC-004664` |
| 9 | **Many test cases sit under several requirements** (multi-requirement scenarios). The extract lists every other requirement and its line. | see "also listed under" in section C |
| 10 | **Only 5 of the 39 are human-approved** (Electronic Mail `TC-000695`, Electronic Mail Request `TC-000716`, Loyalty Card Transaction `TC-001587`, Loyalty Card Transaction Request `TC-001727`, Mail Request `TC-001847`); the rest are verification `PASS` candidates. | - |
