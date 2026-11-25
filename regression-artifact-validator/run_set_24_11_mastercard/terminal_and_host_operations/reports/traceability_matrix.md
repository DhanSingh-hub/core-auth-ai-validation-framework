# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `mastercard-credit-generated-6a83879d81` · Filter `mastercard-credit-generated` · Selection `{"br_kind": ["OPERATIONS"], "platform": ["BUYPASS"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-24T16:08:18.424450+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-24T16:08:18.805731+00:00

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
| Total scenarios / test cases | 9 / 207 |
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
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-001984 |  | PASS | test_data/TD-TC-001984.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-001985 |  | PASS | test_data/TD-TC-001985.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900 | business_scenario | request | TC-001986 |  | PASS | test_data/TD-TC-001986.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001787 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001788 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001789 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001790 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001791 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001792 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001793 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001794 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001795 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001796 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001797 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001798 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001799 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001800 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001801 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001802 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001803 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001804 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001805 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001806 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001807 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001808 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001809 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001810 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001811 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001812 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001813 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001814 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001815 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001816 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001817 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001818 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001819 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001820 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001821 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001822 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001823 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001824 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001825 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001826 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001827 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001828 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001829 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001830 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001831 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001832 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001833 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001834 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001835 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001836 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001837 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001838 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001839 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001840 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001841 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001842 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001843 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001844 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001845 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001846 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001847 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001848 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001849 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001850 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001851 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001852 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001853 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001854 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001855 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001856 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001857 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001858 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001859 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001860 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001861 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001862 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001863 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001864 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001865 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001866 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001867 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001868 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001869 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001870 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001871 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001872 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001873 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001874 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001875 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001876 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001877 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001878 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001879 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001880 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001881 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001882 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001883 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001884 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001885 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001886 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001887 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001888 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001889 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001890 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001891 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001892 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001893 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001894 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001895 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001896 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001897 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001898 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001899 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001900 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001901 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001902 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001903 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001904 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001905 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001906 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001907 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001908 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001909 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001910 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001911 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001912 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001913 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001914 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001915 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001916 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001917 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001918 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001919 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001920 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001921 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001922 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001923 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001924 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001925 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001926 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001927 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001928 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001929 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001930 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001931 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001932 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001933 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001934 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001935 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001936 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001937 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001938 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001939 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001940 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001941 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001942 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001943 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001944 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001945 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001946 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001947 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001948 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001949 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-001950 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001987 |  | PASS | test_data/TD-TC-001987.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001988 |  | PASS | test_data/TD-TC-001988.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001989 |  | PASS | test_data/TD-TC-001989.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001990 |  | PASS | test_data/TD-TC-001990.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001991 |  | PASS | test_data/TD-TC-001991.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001992 |  | PASS | test_data/TD-TC-001992.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-001993 |  | PASS | step 1: test_data/TD-TC-001993.step1.purchase.json; step 2: test_data/TD-TC-001993.step2.table-load.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001964 |  | PASS | test_data/TD-TC-001964.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001965 |  | PASS | test_data/TD-TC-001965.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001966 |  | PASS | test_data/TD-TC-001966.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001967 |  | PASS | test_data/TD-TC-001967.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001968 |  | PASS | test_data/TD-TC-001968.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001969 |  | PASS | test_data/TD-TC-001969.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001970 |  | PASS | test_data/TD-TC-001970.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-001971 |  | PASS | test_data/TD-TC-001971.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001972 |  | PASS | test_data/TD-TC-001972.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001973 |  | PASS | test_data/TD-TC-001973.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001974 |  | PASS | test_data/TD-TC-001974.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001975 |  | PASS | test_data/TD-TC-001975.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001976 |  | PASS | test_data/TD-TC-001976.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001977 |  | PASS | test_data/TD-TC-001977.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001978 |  | PASS | test_data/TD-TC-001978.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001979 |  | PASS | test_data/TD-TC-001979.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001980 |  | PASS | test_data/TD-TC-001980.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-001981 |  | PASS | test_data/TD-TC-001981.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001951 |  | PASS | test_data/TD-TC-001951.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001952 |  | PASS | test_data/TD-TC-001952.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001953 |  | PASS | test_data/TD-TC-001953.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001954 |  | PASS | test_data/TD-TC-001954.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001955 |  | PASS | test_data/TD-TC-001955.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001956 |  | PASS | test_data/TD-TC-001956.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001957 |  | REJECTED_FINAL | step 1: test_data/TD-TC-001957.step1.totals.json; step 2: test_data/TD-TC-001957.step2.totals.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001958 |  | PASS | test_data/TD-TC-001958.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001959 |  | PASS | test_data/TD-TC-001959.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001960 |  | PASS | test_data/TD-TC-001960.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001961 |  | REJECTED_FINAL | step 1: test_data/TD-TC-001961.step1.totals.json; step 2: test_data/TD-TC-001961.step2.totals.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001962 |  | PASS | test_data/TD-TC-001962.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-001963 |  | PASS | test_data/TD-TC-001963.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-001982 |  | PASS | test_data/TD-TC-001982.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-001983 |  | PASS | test_data/TD-TC-001983.json | FULLY_TRACED | pending_review / pending_review / pending_review |

## 5. Requirement detail

### BR-A0098BFB — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 Terminal & Host Operations
Segment `UNMAPPED` · Source page 77 · Derivation `business_requirement`

- **TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001984** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-001984 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001984.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 088` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001985** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-001985 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001985.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 088` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001986** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) — TC-001986 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001986.json` — 2 segments, 2 elements (0 provisional, 0 awaiting client value)
    - Key values: `COMMUNICATIONS_TEST_REQUEST.NetworkManagementMessage = COMMTEST` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001787** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001787 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001787 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001788** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001788 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001788 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001789** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001789 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001789 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001790** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001790 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001790 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001791** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001791 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001791 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001792** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001792 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001792 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001793** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001793 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001793 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001794** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001794 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001794 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001795** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001795 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001795 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001796** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001796 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001796 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001797** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001797 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001797 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001798** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001798 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001798 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001799** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001799 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001799 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001800** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001800 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001800 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001801** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001801 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001801 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001802** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001802 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001802 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001803** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001803 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001803 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001804** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001804 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001804 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001805** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001805 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001805 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001806** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001806 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001806 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001807** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001807 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001807 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001808** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001808 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001808 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001809** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001809 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001809 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001810** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001810 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001810 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001811** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001811 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001811 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001812** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001812 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001812 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001813** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001813 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001813 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001814** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001814 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001814 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001815** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001815 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001815 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001816** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001816 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001816 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001817** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001817 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001817 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001818** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001818 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001818 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001819** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001819 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001819 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001820** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001820 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001820 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001821** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001821 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001821 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001822** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001822 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001822 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001823** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001823 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001823 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001824** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001824 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001824 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001825** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001825 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001825 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001826** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001826 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001826 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001827** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001827 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001827 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001828** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001828 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001828 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001829** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001829 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001829 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001830** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001830 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001830 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001831** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001831 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001831 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001832** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001832 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001832 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001833** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001833 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001833 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001834** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001834 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001834 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001835** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001835 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001835 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001836** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001836 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001836 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001837** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001837 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001837 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001838** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001838 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001838 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001839** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001839 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001839 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001840** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001840 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001840 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001841** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001841 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001841 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001842** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001842 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001842 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001843** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001843 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001843 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001844** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001844 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001844 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001845** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001845 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001845 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001846** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001846 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001846 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001847** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001847 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001847 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001848** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001848 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001848 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001849** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001849 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001849 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001850** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001850 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001850 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001851** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001851 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001851 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001852** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001852 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001852 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001853** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001853 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001853 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001854** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001854 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001854 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001855** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001855 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001855 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001856** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001856 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001856 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001857** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001857 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001857 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001858** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001858 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001858 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001859** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001859 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001859 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001860** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001860 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001860 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001861** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001861 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001861 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001862** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001862 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001862 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001863** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001863 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001863 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001864** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001864 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001864 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001865** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001865 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001865 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001866** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001866 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001866 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001867** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001867 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001867 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001868** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001868 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001868 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001869** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001869 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001869 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001870** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001870 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001870 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001871** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001871 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001871 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001872** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001872 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001872 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001873** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001873 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001873 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001874** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001874 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001874 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001875** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001875 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001875 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001876** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001876 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001876 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001877** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001877 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001877 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001878** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001878 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001878 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001879** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001879 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001879 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001880** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001880 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001880 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001881** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001881 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001881 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001882** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001882 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001882 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001883** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001883 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001883 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001884** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001884 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001884 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001885** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001885 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001885 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001886** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001886 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001886 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001887** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001887 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001887 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001888** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001888 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001888 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001889** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001889 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001889 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001890** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001890 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001890 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001891** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001891 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001891 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001892** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001892 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001892 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001893** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001893 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001893 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001894** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001894 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001894 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001895** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001895 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001895 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001896** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001896 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001896 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001897** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001897 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001897 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001898** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001898 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001898 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001899** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001899 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001899 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001900** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001900 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001900 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001901** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001901 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001901 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001902** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001902 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001902 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001903** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001903 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001903 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001904** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001904 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001904 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001905** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001905 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001905 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001906** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001906 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001906 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001907** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001907 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001907 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001908** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001908 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001908 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001909** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001909 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001909 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001910** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001910 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001910 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001911** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001911 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001911 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001912** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001912 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001912 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001913** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001913 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001913 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001914** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001914 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001914 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001915** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001915 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001915 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001916** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001916 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001916 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001917** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001917 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001917 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001918** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001918 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001918 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001919** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001919 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001919 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001920** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001920 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001920 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001921** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001921 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001921 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001922** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001922 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001922 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001923** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001923 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001923 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001924** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001924 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001924 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001925** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001925 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001925 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001926** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001926 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001926 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001927** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001927 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001927 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001928** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001928 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001928 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001929** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001929 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001929 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001930** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001930 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001930 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001931** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001931 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001931 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001932** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001932 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001932 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001933** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001933 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001933 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001934** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001934 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001934 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001935** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001935 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001935 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001936** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001936 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001936 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001937** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001937 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001937 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001938** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001938 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001938 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001939** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001939 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001939 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001940** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001940 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001940 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001941** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001941 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001941 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001942** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001942 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001942 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001943** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001943 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001943 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001944** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001944 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001944 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001945** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001945 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001945 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001946** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001946 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001946 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001947** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001947 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001947 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001948** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001948 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001948 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001949** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001949 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001949 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001950** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-001950 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001950 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001987** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001987 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001987.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-001988** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001988 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001988.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `PHONE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `PHONE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `PHONE_LOAD_REQUEST.TerminalIdentifier = JX41894821001` (platform_default, pNone)
  - **TC-001989** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001989 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001989.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `DATE_AND_TIME_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.LoadType = D` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.TerminalIdentifier = JX41894821001` (platform_default, pNone)
  - **TC-001990** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001990 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001990.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `SOFTWARE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.FirmwareVersion = 5RK9B8QS` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.HardwareVersion = W3SOAZVJ` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.SoftwareVersion = WSBB6ETC` (platform_default, pNone)
  - **TC-001991** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001991 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001991.json` — 1 segments, 7 elements (4 provisional, 0 awaiting client value)
    - Key values: `MONERIS_KEY_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadSubtype = M` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadType = K` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisMerchantID = KC3M9G7VAO3RU` (platform_default, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisTerminalIdentifier = JWMSF4Z5` (platform_default, pNone)
  - **TC-001992** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001992 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001992.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-001993** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-001993 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001993.step1.purchase.json` — 5 segments, 38 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 083` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
    - Step 1 (purchase, Purchase/Capture): `test_data/TD-TC-001993.step1.purchase.json`
    - Step 2 (table_load, OP_TABLE_LOAD): `test_data/TD-TC-001993.step2.table-load.json`
- **TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001964** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001964 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001964.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001965** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001965 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001965.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001966** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001966 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001966.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001967** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001967 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001967.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001968** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001968 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001968.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001969** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001969 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001969.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001970** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001970 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001970.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001971** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-001971 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001971.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001972** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001972 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001972.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001973** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001973 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001973.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001974** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001974 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001974.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001975** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001975 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001975.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001976** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001976 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001976.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001977** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001977 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001977.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001978** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001978 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001978.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001979** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001979 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001979.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001980** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001980 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001980.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-001981** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-001981 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001981.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001951** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001951 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001951.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001952** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001952 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001952.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001953** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001953 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001953.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001954** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001954 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001954.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001955** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001955 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001955.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001956** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001956 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001956.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001957** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001957 · `None` · verification `REJECTED_FINAL` · review `pending_review`
    - Test data: `test_data/TD-TC-001957.step1.totals.json` — 2 segments, 31 elements (15 provisional, 3 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
    - Step 1 (totals, OP_TOTALS): `test_data/TD-TC-001957.step1.totals.json`
    - Step 2 (totals, OP_TOTALS): `test_data/TD-TC-001957.step2.totals.json`
  - **TC-001958** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001958 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001958.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001959** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001959 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001959.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001960** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001960 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001960.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-001961** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-001961 · `None` · verification `REJECTED_FINAL` · review `pending_review`
    - Test data: `test_data/TD-TC-001961.step1.totals.json` — 2 segments, 31 elements (15 provisional, 3 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
    - Step 1 (totals, OP_TOTALS): `test_data/TD-TC-001961.step1.totals.json`
    - Step 2 (totals, OP_TOTALS): `test_data/TD-TC-001961.step2.totals.json`
- **TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001962** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-001962 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001962.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 153` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
  - **TC-001963** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-001963 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001963.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 153` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001982** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-001982 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001982.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 059` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)
  - **TC-001983** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-001983 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001983.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 059` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-001787`
- `TC-001788`
- `TC-001789`
- `TC-001790`
- `TC-001791`
- `TC-001792`
- `TC-001793`
- `TC-001794`
- `TC-001795`
- `TC-001796`
- `TC-001797`
- `TC-001798`
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
- `TC-001811`
- `TC-001812`
- `TC-001813`
- `TC-001814`
- `TC-001815`
- `TC-001816`
- `TC-001817`
- `TC-001818`
- `TC-001819`
- `TC-001820`
- `TC-001821`
- `TC-001822`
- `TC-001823`
- `TC-001824`
- `TC-001825`
- `TC-001826`
- `TC-001827`
- `TC-001828`
- `TC-001829`
- `TC-001830`
- `TC-001831`
- `TC-001832`
- `TC-001833`
- `TC-001834`
- `TC-001835`
- `TC-001836`
- `TC-001837`
- `TC-001838`
- `TC-001839`
- `TC-001840`
- `TC-001841`
- `TC-001842`
- `TC-001843`
- `TC-001844`
- `TC-001845`
- `TC-001846`
- `TC-001847`
- `TC-001848`
- `TC-001849`
- `TC-001850`
- `TC-001851`
- `TC-001852`
- `TC-001853`
- `TC-001854`
- `TC-001855`
- `TC-001856`
- `TC-001857`
- `TC-001858`
- `TC-001859`
- `TC-001860`
- `TC-001861`
- `TC-001862`
- `TC-001863`
- `TC-001864`
- `TC-001865`
- `TC-001866`
- `TC-001867`
- `TC-001868`
- `TC-001869`
- `TC-001870`
- `TC-001871`
- `TC-001872`
- `TC-001873`
- `TC-001874`
- `TC-001875`
- `TC-001876`
- `TC-001877`
- `TC-001878`
- `TC-001879`
- `TC-001880`
- `TC-001881`
- `TC-001882`
- `TC-001883`
- `TC-001884`
- `TC-001885`
- `TC-001886`
- `TC-001887`
- `TC-001888`
- `TC-001889`
- `TC-001890`
- `TC-001891`
- `TC-001892`
- `TC-001893`
- `TC-001894`
- `TC-001895`
- `TC-001896`
- `TC-001897`
- `TC-001898`
- `TC-001899`
- `TC-001900`
- `TC-001901`
- `TC-001902`
- `TC-001903`
- `TC-001904`
- `TC-001905`
- `TC-001906`
- `TC-001907`
- `TC-001908`
- `TC-001909`
- `TC-001910`
- `TC-001911`
- `TC-001912`
- `TC-001913`
- `TC-001914`
- `TC-001915`
- `TC-001916`
- `TC-001917`
- `TC-001918`
- `TC-001919`
- `TC-001920`
- `TC-001921`
- `TC-001922`
- `TC-001923`
- `TC-001924`
- `TC-001925`
- `TC-001926`
- `TC-001927`
- `TC-001928`
- `TC-001929`
- `TC-001930`
- `TC-001931`
- `TC-001932`
- `TC-001933`
- `TC-001934`
- `TC-001935`
- `TC-001936`
- `TC-001937`
- `TC-001938`
- `TC-001939`
- `TC-001940`
- `TC-001941`
- `TC-001942`
- `TC-001943`
- `TC-001944`
- `TC-001945`
- `TC-001946`
- `TC-001947`
- `TC-001948`
- `TC-001949`
- `TC-001950`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
