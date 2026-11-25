# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `buypass-atl105-mastercard-credit-generated-70fc786d3e` · Filter `buypass-atl105-mastercard-credit-generated` · Selection `{"br_kind": ["OPERATIONS"], "platform": ["BUYPASS"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-16T15:34:49.150221+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-16T15:34:49.845643+00:00

## 1. Summary

| Metric | Value |
| --- | --- |
| Total business requirements | 1 |
| Requirements with ≥ 1 scenario | 1 |
| Requirements with ≥ 1 test case | 1 |
| Requirements fully traced to test data | 1 |
| Requirements not applicable (output-only element) | 0 |
| Requirement coverage | 100.0% |
| Full-chain coverage | 100.0% |
| Total scenarios / test cases | 9 / 47 |
| Orphan scenarios / test cases | 0 / 0 |
| Response-side scenarios linked | 0 |
| Requirements traced via response-side chain only | 0 |

## 2. How to read this document

Section 5 has one entry per business requirement, walking Requirement -> Scenario -> Test Case -> Test Data in order. Each scenario carries its own `chain_side`: `request` is a scenario composed into its own sendable test case as usual; `response` is a scenario about a RESPONSE message (e.g. a field/business rule on 'X Response') that can never be composed into its own test case, instead linked to the REQUEST test case(s) that actually expect that response (test_generation/response_side_mapper.py). `trace_status` combines both chains for a requirement: `FULLY_TRACED` means concrete test data was resolved on disk via EITHER chain; `TEST_CASE_NO_DATA`, `SCENARIO_ONLY` and `REQUIREMENT_ONLY` name the first missing link; `BROKEN_LINK` means a downstream artifact points at an ID that no longer exists upstream. Section 3 lists only the gaps; section 4 is the flat row-per-leaf view for spreadsheet use, carrying its own `Chain` column.

## 3. Coverage gaps (requirements not fully traced)

_No gaps — every requirement is fully traced to test data._

## 4. Full matrix

| Requirement | Statement (truncated) | Scenario | Type | Chain | Test Case | Priority | Verification | Test Data File | Status | Review (BR / Scenario / TC) |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-039804 |  | PASS | test_data/TD-TC-039804.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-039805 |  | PASS | test_data/TD-TC-039805.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900 | business_scenario | request | TC-039806 |  | PASS | test_data/TD-TC-039806.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-039767 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-039768 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-039769 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-039770 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-039771 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-039772 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-039807 |  | PASS | test_data/TD-TC-039807.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-039808 |  | PASS | test_data/TD-TC-039808.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-039809 |  | PASS | test_data/TD-TC-039809.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-039810 |  | PASS | test_data/TD-TC-039810.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-039811 |  | PASS | test_data/TD-TC-039811.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-039812 |  | PASS | test_data/TD-TC-039812.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-039813 |  | PASS | test_data/TD-TC-039813.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039784 |  | PASS | test_data/TD-TC-039784.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039785 |  | PASS | test_data/TD-TC-039785.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039786 |  | PASS | test_data/TD-TC-039786.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039787 |  | PASS | test_data/TD-TC-039787.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039788 |  | PASS | test_data/TD-TC-039788.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039789 |  | PASS | test_data/TD-TC-039789.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039790 |  | PASS | test_data/TD-TC-039790.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-039791 |  | PASS | test_data/TD-TC-039791.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039792 |  | PASS | test_data/TD-TC-039792.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039793 |  | PASS | test_data/TD-TC-039793.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039794 |  | PASS | test_data/TD-TC-039794.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039795 |  | PASS | test_data/TD-TC-039795.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039796 |  | PASS | test_data/TD-TC-039796.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039797 |  | PASS | test_data/TD-TC-039797.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039798 |  | PASS | test_data/TD-TC-039798.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039799 |  | PASS | test_data/TD-TC-039799.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039800 |  | PASS | test_data/TD-TC-039800.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-039801 |  | PASS | test_data/TD-TC-039801.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039773 |  | PASS | test_data/TD-TC-039773.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039774 |  | PASS | test_data/TD-TC-039774.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039775 |  | PASS | test_data/TD-TC-039775.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039776 |  | PASS | test_data/TD-TC-039776.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039777 |  | PASS | test_data/TD-TC-039777.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039778 |  | PASS | test_data/TD-TC-039778.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039779 |  | PASS | test_data/TD-TC-039779.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039780 |  | PASS | test_data/TD-TC-039780.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039781 |  | PASS | test_data/TD-TC-039781.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039782 |  | PASS | test_data/TD-TC-039782.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-039783 |  | PASS | test_data/TD-TC-039783.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-039802 |  | PASS | test_data/TD-TC-039802.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-039803 |  | PASS | test_data/TD-TC-039803.json | FULLY_TRACED | pending_review / pending_review / pending_review |

## 5. Requirement detail

### BR-A0098BFB — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 Terminal & Host Operations
Segment `UNMAPPED` · Source page 77 · Derivation `business_requirement`

- **TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039804** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-039804 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039804.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 094` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039805** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-039805 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039805.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 094` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039806** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) — TC-039806 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039806.json` — 2 segments, 2 elements (0 provisional, 0 awaiting client value)
    - Key values: `COMMUNICATIONS_TEST_REQUEST.NetworkManagementMessage = COMMTEST` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039767** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-039767 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-039767 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-039768** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-039768 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-039768 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-039769** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-039769 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-039769 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-039770** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-039770 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-039770 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-039771** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-039771 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-039771 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-039772** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-039772 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-039772 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039807** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-039807 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039807.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-039808** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-039808 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039808.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `PHONE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `PHONE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `PHONE_LOAD_REQUEST.TerminalIdentifier = JX72948216050257001` (platform_default, pNone)
  - **TC-039809** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-039809 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039809.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `DATE_AND_TIME_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.LoadType = D` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.TerminalIdentifier = JX72948216050257001` (platform_default, pNone)
  - **TC-039810** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-039810 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039810.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `SOFTWARE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.FirmwareVersion = 5RK9B8QS` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.HardwareVersion = W3SOAZVJ` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.SoftwareVersion = WSBB6ETC` (platform_default, pNone)
  - **TC-039811** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-039811 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039811.json` — 1 segments, 7 elements (4 provisional, 0 awaiting client value)
    - Key values: `MONERIS_KEY_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadSubtype = M` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadType = K` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisMerchantID = KC3M9G7VAO3RU` (platform_default, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisTerminalIdentifier = JWMSF4Z5` (platform_default, pNone)
  - **TC-039812** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-039812 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039812.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-039813** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-039813 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039813.step1.purchase.json` — 5 segments, 38 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 089` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039784** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039784 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039784.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039785** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039785 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039785.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039786** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039786 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039786.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039787** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039787 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039787.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039788** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039788 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039788.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039789** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039789 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039789.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039790** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039790 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039790.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039791** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-039791 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039791.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039792** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039792 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039792.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039793** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039793 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039793.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039794** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039794 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039794.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039795** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039795 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039795.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039796** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039796 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039796.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039797** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039797 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039797.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039798** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039798 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039798.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039799** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039799 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039799.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039800** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039800 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039800.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-039801** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-039801 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039801.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039773** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039773 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039773.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039774** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039774 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039774.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039775** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039775 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039775.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039776** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039776 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039776.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039777** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039777 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039777.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039778** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039778 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039778.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039779** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039779 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039779.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039780** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039780 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039780.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-039781** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-039781 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039781.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039782** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-039782 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039782.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 159` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
  - **TC-039783** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-039783 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039783.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 159` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-039802** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-039802 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039802.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 065` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)
  - **TC-039803** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-039803 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-039803.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 065` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-039767`
- `TC-039768`
- `TC-039769`
- `TC-039770`
- `TC-039771`
- `TC-039772`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
