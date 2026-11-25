# BUYPASS ATL105 MasterCard Credit: transaction-level test cases

Built by the business model layer with the transaction-level test case model, on 2026-10-28:
- Build: `buypass-atl105-mastercard-credit-65482e4498`.
- Dimension mapping: candidate, not yet SME-approved.
- Merchant: 1 (`MERCHANT 01 (POS PLACEHOLDER)`, attended POS).

```
python src/pipeline/business_model/cli.py --build-tests --filter-name buypass-atl105-mastercard-credit \
    --platform BUYPASS --spec ATL105 --network MASTERCARD --program CREDIT --allow-unapproved --out mastercard_credit
python src/pipeline/business_model/cli.py --render buypass-atl105-mastercard-credit --run-set all --out mastercard_credit
```

## Naming

| Level | Id | Name | Example |
|---|---|---|---|
| BR | `BR1` | `{Platform} {Spec} {Network} {Program}` | BUYPASS ATL105 MasterCard Credit |
| Scenario | `BR1_TS{m}` | `{BR} {Transaction Category} – {Entry Mode}` | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe |
| Test case | `BR1_TS{m}_TC{k}` + `TC-######` | `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}` | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Purchase Reversal |

## Counts

- 1 BR.
- 36 scenarios (original transaction categories × entry modes): 26 valid, 2 spec-implied, 8 unconfirmed.
- 34 follow-on combinations. These are steps inside flow test cases, not scenarios.
- 90 excluded combinations.
- **241 test cases**, all transaction level:

| Type | Test cases | Type | Test cases |
|---|---|---|---|
| T1 Base transaction | 36 | T7 Stored credential | 5 |
| T2 Card capture path | 12 | T8 Settlement | 7 |
| T3 Cardholder verification | 6 | T9 Value-added service | 14 |
| T4 Partial approval | 42 | T10 Business-rule decline | 8 |
| T5 Follow-on lifecycle | 54 | T11 Device-only | 7 |
| T6 Time-out & recovery | 50 | | |

| Status | Test cases | Meaning |
|---|---|---|
| ready | 125 | can run now |
| needs_client_input | 47 | needs a client test card or data (partial approval, AVS, expired card, PIN, FSA) |
| requires_review | 35 | an SME question is attached (`open_items`) |
| pending_sme | 32 | the scenario itself is unconfirmed by the spec |
| blocked_spec_gap | 2 | Mail/Phone Authorization → Completion: no completion code in ATL105 |

- Verification: 232 PASS. 9 are not applicable (7 device-only, 2 blocked).
- Validation and consistency findings: 0.
- Test data: 446 request files, one per single-message test case and one per step of a flow.
- Not generated with this merchant: "Partial requested at CAT purchase" (needs a CAT merchant).

## Files

| Path | Content |
|---|---|
| `requirements_scenarios/BUSINESS_REQUIREMENTS.md` | BR, scenarios, follow-on combinations, SME pairs, excluded combinations, spec citations |
| `requirements_scenarios/business_scenarios.csv` / `.json` | 36 scenarios |
| `requirements_scenarios/follow_on_leg_combinations.csv` | follow-on combinations (flow steps) and their status |
| `requirements_scenarios/open_pair_questions.csv` | SME questions on unconfirmed category/entry-mode pairs |
| `requirements_scenarios/excluded_combinations.csv` | combinations the spec rules out, with the quote |
| `test_cases/TEST_CASES.md` | BR → scenario → test case tree with steps and expected response per step |
| `test_cases/test_cases.csv` | one row per test case (see below) |
| `test_cases/test_cases.jsonl` | full test cases: composed requests per step, rule checks, expected response per step, verification |
| `test_cases/coverage_report.json`, `test_case_validation.json`, `test_case_skips.json` | coverage, validation, skips |
| `test_data/TD-TC-######[.stepN.role].json` | request test data (QE wire format); runtime values are tokens such as `<ApprovalNumber from step 1 response>` |
| `run_set.json` | index of test cases → test data files |
| `SME_QUESTIONS.md` | SME questions, major ones first |

`test_cases.csv` columns include:
- `steps`: the step chain, e.g. `1. Purchase/Capture [0] … entry 90 | 2. Purchase Reversal [8] … entry 02`;
- `expected_response_per_step`;
- `harness`: e.g. withhold the response for more than 30 s;
- `execution_settings`: test card or data needed at execution;
- `device_checkpoints`;
- `open_items`;
- `spec_basis` (page and quote);
- `test_data_files`;
- empty `sme_decision` and `sme_comment` columns for review.

## How to read a test case

- Each step's request carries the spec's element rules as rule checks, verified for every step:
  - Appendix G Transaction Type;
  - Appendix E card type `020`;
  - Appendix J entry mode, with a follow-on of `90` sending `02` (p.638);
  - Segment 130 on EMV.
- Follow-on steps carry the original's Sequence Number, Terminal Identifier and amount. The Approval Number,
  and any amount that depends on a partial approval, come from the earlier response as runtime links. They
  are never invented.
- Expected responses: `ASSERT` cites the spec page. `FLAG` means the code is not stated in ATL105 and needs
  SME or processor confirmation. "no response expected" marks a step whose response is withheld by the harness.
