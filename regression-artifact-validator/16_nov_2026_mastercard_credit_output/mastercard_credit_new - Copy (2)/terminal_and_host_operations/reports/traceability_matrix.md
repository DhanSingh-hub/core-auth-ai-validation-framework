# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `buypass-atl105-mastercard-credit-generated-35c827f03e` · Filter `buypass-atl105-mastercard-credit-generated` · Selection `{"br_kind": ["OPERATIONS"], "platform": ["BUYPASS"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-13T17:50:07.679453+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-13T17:50:08.074405+00:00

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
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-020696 |  | PASS | qe_shaped_test_data/TD-TC-020696.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-022221 |  | PASS | qe_shaped_test_data/TD-TC-022221.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900 | business_scenario | request | TC-020697 |  | PASS | qe_shaped_test_data/TD-TC-020697.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-020658 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-020659 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-020660 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-020661 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-020662 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-020663 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-022222 |  | PASS | qe_shaped_test_data/TD-TC-022222.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-022223 |  | PASS | qe_shaped_test_data/TD-TC-022223.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-022224 |  | PASS | qe_shaped_test_data/TD-TC-022224.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-022225 |  | PASS | qe_shaped_test_data/TD-TC-022225.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-022226 |  | PASS | qe_shaped_test_data/TD-TC-022226.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-022227 |  | PASS | qe_shaped_test_data/TD-TC-022227.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-022228 |  | PASS | qe_shaped_test_data/TD-TC-022228.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-020679 |  | PASS | qe_shaped_test_data/TD-TC-020679.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-020680 |  | PASS | qe_shaped_test_data/TD-TC-020680.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-020681 |  | PASS | qe_shaped_test_data/TD-TC-020681.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-020682 |  | PASS | qe_shaped_test_data/TD-TC-020682.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-022211 |  | PASS | qe_shaped_test_data/TD-TC-022211.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-022212 |  | PASS | qe_shaped_test_data/TD-TC-022212.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-022213 |  | PASS | qe_shaped_test_data/TD-TC-022213.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-022214 |  | PASS | qe_shaped_test_data/TD-TC-022214.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-020688 |  | PASS | qe_shaped_test_data/TD-TC-020688.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-020689 |  | PASS | qe_shaped_test_data/TD-TC-020689.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-020690 |  | PASS | qe_shaped_test_data/TD-TC-020690.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-020691 |  | PASS | qe_shaped_test_data/TD-TC-020691.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-020692 |  | PASS | qe_shaped_test_data/TD-TC-020692.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-022215 |  | PASS | qe_shaped_test_data/TD-TC-022215.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-022216 |  | PASS | qe_shaped_test_data/TD-TC-022216.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-022217 |  | PASS | qe_shaped_test_data/TD-TC-022217.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-022218 |  | PASS | qe_shaped_test_data/TD-TC-022218.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-022219 |  | PASS | qe_shaped_test_data/TD-TC-022219.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-020672 |  | PASS | qe_shaped_test_data/TD-TC-020672.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022202 |  | PASS | qe_shaped_test_data/TD-TC-022202.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022203 |  | PASS | qe_shaped_test_data/TD-TC-022203.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022204 |  | PASS | qe_shaped_test_data/TD-TC-022204.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022205 |  | PASS | qe_shaped_test_data/TD-TC-022205.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022206 |  | PASS | qe_shaped_test_data/TD-TC-022206.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022207 |  | PASS | qe_shaped_test_data/TD-TC-022207.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022208 |  | PASS | qe_shaped_test_data/TD-TC-022208.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022209 |  | PASS | qe_shaped_test_data/TD-TC-022209.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-020674 |  | PASS | qe_shaped_test_data/TD-TC-020674.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-022210 |  | PASS | qe_shaped_test_data/TD-TC-022210.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-020694 |  | PASS | qe_shaped_test_data/TD-TC-020694.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-022220 |  | PASS | qe_shaped_test_data/TD-TC-022220.json | FULLY_TRACED | pending_review / pending_review / pending_review |

## 5. Requirement detail

### BR-A0098BFB — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 Terminal & Host Operations
Segment `UNMAPPED` · Source page 77 · Derivation `business_requirement`

- **TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020696** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-020696 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020696.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 094` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022221** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-022221 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022221.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 094` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020697** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) — TC-020697 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020697.json` — 2 segments, 2 elements (0 provisional, 0 awaiting client value)
    - Key values: `COMMUNICATIONS_TEST_REQUEST.NetworkManagementMessage = COMMTEST` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020658** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-020658 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-020658 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-020659** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-020659 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-020659 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-020660** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-020660 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-020660 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-020661** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-020661 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-020661 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-020662** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-020662 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-020662 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-020663** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-020663 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-020663 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-022222** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-022222 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022222.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-022223** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-022223 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022223.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `PHONE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `PHONE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `PHONE_LOAD_REQUEST.TerminalIdentifier = JX72948216050257001` (platform_default, pNone)
  - **TC-022224** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-022224 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022224.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `DATE_AND_TIME_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.LoadType = D` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.TerminalIdentifier = JX72948216050257001` (platform_default, pNone)
  - **TC-022225** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-022225 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022225.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `SOFTWARE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.FirmwareVersion = 5RK9B8QS` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.HardwareVersion = W3SOAZVJ` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.SoftwareVersion = WSBB6ETC` (platform_default, pNone)
  - **TC-022226** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-022226 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022226.json` — 1 segments, 7 elements (4 provisional, 0 awaiting client value)
    - Key values: `MONERIS_KEY_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadSubtype = M` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadType = K` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisMerchantID = KC3M9G7VAO3RU` (platform_default, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisTerminalIdentifier = JWMSF4Z5` (platform_default, pNone)
  - **TC-022227** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-022227 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022227.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-022228** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-022228 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022228.step1.purchase.json` — 5 segments, 38 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 089` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020679** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-020679 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020679.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-020680** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-020680 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020680.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-020681** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-020681 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020681.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-020682** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-020682 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020682.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022211** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-022211 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022211.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022212** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-022212 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022212.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022213** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-022213 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022213.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022214** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-022214 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022214.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 052` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020688** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-020688 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020688.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-020689** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-020689 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020689.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-020690** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-020690 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020690.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-020691** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-020691 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020691.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-020692** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-020692 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020692.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022215** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-022215 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022215.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022216** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-022216 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022216.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022217** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-022217 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022217.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022218** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-022218 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022218.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-022219** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-022219 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022219.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0199` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020672** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-020672 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020672.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022202** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022202 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022202.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022203** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022203 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022203.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022204** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022204 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022204.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022205** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022205 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022205.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022206** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022206 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022206.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022207** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022207 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022207.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022208** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022208 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022208.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-022209** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-022209 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022209.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 090` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020674** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-020674 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020674.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 159` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
  - **TC-022210** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-022210 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022210.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 159` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-020694** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-020694 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-020694.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 065` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)
  - **TC-022220** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-022220 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-022220.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 065` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-020658`
- `TC-020659`
- `TC-020660`
- `TC-020661`
- `TC-020662`
- `TC-020663`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
