# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `terminal-and-host-operations-45f78fecab` · Filter `terminal-and-host-operations` · Selection `{"br_kind": ["OPERATIONS"], "platform": ["BUYPASS"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-05T10:30:24.824184+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-05T10:30:25.274778+00:00

## 1. Summary

| Metric | Value |
| --- | --- |
| Total business requirements | 1 |
| Requirements with ≥ 1 scenario | 1 |
| Requirements with ≥ 1 test case | 1 |
| Requirements fully traced to test data | 0 |
| Requirements not applicable (output-only element) | 0 |
| Requirement coverage | 100.0% |
| Full-chain coverage | 0.0% |
| Total scenarios / test cases | 9 / 47 |
| Orphan scenarios / test cases | 0 / 0 |
| Response-side scenarios linked | 0 |
| Requirements traced via response-side chain only | 0 |

## 2. How to read this document

Section 5 has one entry per business requirement, walking Requirement -> Scenario -> Test Case -> Test Data in order. Each scenario carries its own `chain_side`: `request` is a scenario composed into its own sendable test case as usual; `response` is a scenario about a RESPONSE message (e.g. a field/business rule on 'X Response') that can never be composed into its own test case, instead linked to the REQUEST test case(s) that actually expect that response (test_generation/response_side_mapper.py). `trace_status` combines both chains for a requirement: `FULLY_TRACED` means concrete test data was resolved on disk via EITHER chain; `TEST_CASE_NO_DATA`, `SCENARIO_ONLY` and `REQUIREMENT_ONLY` name the first missing link; `BROKEN_LINK` means a downstream artifact points at an ID that no longer exists upstream. Section 3 lists only the gaps; section 4 is the flat row-per-leaf view for spreadsheet use, carrying its own `Chain` column.

## 3. Coverage gaps (requirements not fully traced)

| Requirement | Segment | Status | Gap reason | Suggested action | Source page |
| --- | --- | --- | --- | --- | --- |
| BR-A0098BFB | UNMAPPED | TEST_CASE_NO_DATA | Test cases exist but no test data file resolves on disk. | Re-run Test Case Generation to regenerate test data files. | 77 |

## 4. Full matrix

| Requirement | Statement (truncated) | Scenario | Type | Chain | Test Case | Priority | Verification | Test Data File | Status | Review (BR / Scenario / TC) |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-001801 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-001802 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900 | business_scenario | request | TC-001803 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001764 |  | NOT_APPLICABLE |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001765 |  | NOT_APPLICABLE |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001766 |  | NOT_APPLICABLE |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001767 |  | NOT_APPLICABLE |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001768 |  | NOT_APPLICABLE |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001769 |  | NOT_APPLICABLE |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001804 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001805 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001806 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001807 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001808 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001809 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001810 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002814 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002815 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002816 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002817 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002818 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002819 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002820 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-002821 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002822 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002823 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002824 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002825 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002826 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002827 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002828 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002829 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002830 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-002831 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001770 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001771 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001772 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001773 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001774 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001775 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001776 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001777 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001778 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-002812 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-002813 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-001799 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-001800 |  | PASS |  | TEST_CASE_NO_DATA | pending_review / pending_review / pending_review |

## 5. Requirement detail

### BR-A0098BFB — TEST_CASE_NO_DATA · review `pending_review`
> BUYPASS ATL105 Terminal & Host Operations
Segment `UNMAPPED` · Source page 77 · Derivation `business_requirement`

- **TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001801** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-001801 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001801 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001802** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-001802 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001802 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001803** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) — TC-001803 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001803 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001764** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001764 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001764 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001765** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001765 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001765 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001766** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001766 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001766 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001767** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001767 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001767 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001768** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001768 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001768 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001769** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001769 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001769 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001804** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001804 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001804 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001805** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001805 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001805 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001806** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001806 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001806 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001807** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001807 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001807 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001808** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001808 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001808 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001809** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001809 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001809 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001810** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001810 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001810 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-002814** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002814 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002814 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002815** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002815 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002815 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002816** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002816 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002816 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002817** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002817 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002817 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002818** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002818 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002818 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002819** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002819 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002819 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002820** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002820 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002820 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002821** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-002821 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002821 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-002822** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002822 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002822 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002823** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002823 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002823 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002824** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002824 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002824 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002825** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002825 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002825 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002826** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002826 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002826 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002827** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002827 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002827 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002828** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002828 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002828 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002829** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002829 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002829 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002830** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002830 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002830 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002831** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-002831 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002831 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001770** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001770 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001770 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001771** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001771 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001771 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001772** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001772 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001772 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001773** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001773 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001773 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001774** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001774 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001774 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001775** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001775 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001775 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001776** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001776 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001776 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001777** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001777 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001777 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001778** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001778 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001778 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-002812** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-002812 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002812 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002813** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-002813 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-002813 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001799** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-001799 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001799 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001800** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-001800 · `None` · verification `PASS` · review `pending_review`
    - Test data: _Expected test data file for TC-001800 was not found on disk. Re-run Test Case Generation to regenerate test data files._

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-001764`
- `TC-001765`
- `TC-001766`
- `TC-001767`
- `TC-001768`
- `TC-001769`
- `TC-001770`
- `TC-001771`
- `TC-001772`
- `TC-001773`
- `TC-001774`
- `TC-001775`
- `TC-001776`
- `TC-001777`
- `TC-001778`
- `TC-001799`
- `TC-001800`
- `TC-001801`
- `TC-001802`
- `TC-001803`
- `TC-001804`
- `TC-001805`
- `TC-001806`
- `TC-001807`
- `TC-001808`
- `TC-001809`
- `TC-001810`
- `TC-002812`
- `TC-002813`
- `TC-002814`
- `TC-002815`
- `TC-002816`
- `TC-002817`
- `TC-002818`
- `TC-002819`
- `TC-002820`
- `TC-002821`
- `TC-002822`
- `TC-002823`
- `TC-002824`
- `TC-002825`
- `TC-002826`
- `TC-002827`
- `TC-002828`
- `TC-002829`
- `TC-002830`
- `TC-002831`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
