# MasterCard Credit - generated test package (BUYPASS ATL105), plan v10

Built with the rule-driven generator (`--tc-model generated`) after the plan v10 fixes. Every brand, program, code,
category, entry mode and page comes from the knowledge base / dimension mapping; only language-level wording lives in
config (`src/pipeline/config/tc_generation.json` `spec_roles`, `test_case_names`). The previous (v8) package is in
[archive/v8_2026-11-13](archive/v8_2026-11-13/).

Scenario list: [SCENARIOS.md](SCENARIOS.md) · contexts: [CONTEXTS.md](CONTEXTS.md) · genericity:
[GENERICITY_DRY_RUN.md](GENERICITY_DRY_RUN.md) · design: [docs/ACCEPTANCE_CONTEXT_DESIGN.md](../docs/ACCEPTANCE_CONTEXT_DESIGN.md) section 13.

| Folder | BR | Scenarios | Test cases |
|---|---|---:|---|
| `./` | BR1 MasterCard Credit (network, generated) | 109 | 1,861: 1,724 host (all verification PASS) + 137 device; 14 blocked (sheet `Blocked`) |
| `data_element_validation/` | Data Element Validation (shared) | 35 | 1,029 (MasterCard cards only); 35 blocked |
| `terminal_and_host_operations/` | Terminal & Host Operations (shared) | 9 | 47 |

All three BRs: 0 validation findings, 0 high-severity test case audit findings (`coverage/test_case_audit.csv`),
0 unresolved references (`DELIVERY_CONTRACT.md`). Every network scenario has a test case; all 34 follow-on legs are
exercised. Run sets: smoke = P1 (584), regression = P1 + P2 (1,632), P3 = device / harness (229).

## What changed from v8

| Problem found in the v8 review | v10 |
|---|---|
| Rules of other brands tested on MasterCard (Visa payWave, WEX, Voyager, Discover, eWIC, Moneris ...) | brand / program words derived from the mapping's own network / program lists; 115 network rules + 30 tables / segments not applicable (`coverage/not_applicable_rules.csv`); 13 data element checks supported only by such rules left the build (`data_element_validation/coverage/not_applicable_checks.csv`) |
| Data element / operations tests built on American Express / Voyager scenarios and cards | carriers are this selection's own scenarios: MasterCard cards only |
| Card-absent rules (72 h reversal, recurring, RBI mandate) on chip | card-absent rules run on card-not-present entry modes only and are never cited by card-present tests |
| Time shift on a single message (24 h / 7 days / 6 months on one authorization) | windows are original -> follow-on after N / N+1 (e.g. Authorization Only, then Cancellation after 24 / 25 hours); CA public key limits are device checks; completion / hold windows whose follow-on is not an allowed flow are gaps with the reason |
| "Sending Table NNN" with empty content and unrelated citations | table definition cited first, only rules naming the table targeted, one test per transaction, awaited content -> `needs_client_input` with Missing Inputs |
| Text-only "arithmetic" / format / sequence-number tests | field layout -> data element validation; link equality -> the flow mismatch tests (`coverage/covered_elsewhere.csv`, 25 rules); arithmetic only with amount wording |
| Negatives expecting approval; positives with no expected code | violations keep their decline; no single spec code -> outcome class asserted as `approved (any of 0, 2)` / totals `5, 6, B, D, N, W` (tier INFER); per-transaction approval codes |
| Contexts GENERAL / -1 / -2 / MCC_5542 | one context per industry x attendance with a readable label (e.g. `Petroleum · unattended CAT`); no keyed / stored-credential entry on unattended terminals; `coverage/context_matrix.csv` |
| Unreadable / duplicate names | `<BR> <merchant> <entry mode> <steps>[ <change>] → <expected>`; 62 same-test duplicates merged (`coverage/merged_test_cases.csv`, all rules still credited); 0 duplicate names |
| Excel | sheets `Test Cases` and `Blocked` (with reason); columns Technique, Polarity, Expected Result, Context, Status, Missing Inputs |

## Still open (reported, not hidden)

- **Terminal-profile contexts**: the spec's 42 terminal-profile codes are listed in `CONTEXTS.md` but not built as
  contexts of their own; contexts remain industry x attendance (+ card not present, MCC).
- **Business-level oracle (D4)**: 77 network host tests still assert only generic checks with an unconfirmed (FLAG)
  decline (`coverage/test_case_audit.csv` `M-BUSINESS`); they are SME items, not ready.
- **MCC 5542 / 5552 / 6051 / 6211** stay standalone contexts: their rules do not name an industry in their own words.
- **Completion / authorization-hold windows** (p.545 30 / 7 days, PIPS p.18): not testable on the host because the
  Authorization Only -> Purchase/Capture completion is forbidden for this BR (gap with reason).
- **Data element names** still use "(n)" counters and some long names (layout-driven BR, not renamed in v10).
- Carried over from v8: element-level follow-on violation classes, operation flows from rules, parity with reviewed
  templates.

## Rebuild

```powershell
& $py src\pipeline\run_pipeline.py --business-model --build-tests --tc-model generated `
    --platform BUYPASS --spec ATL105 --network MASTERCARD --program CREDIT `
    --filter-name buypass-atl105-mastercard-credit-generated --allow-unapproved --out <tmp>
# render each build (network -> ./, data element / operations -> subfolders) and copy its pack, test_cases,
# coverage and reports
& $py src\pipeline\business_model\cli.py --render <build_id> --run-set all --out mastercard_credit_new
```
