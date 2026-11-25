# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `buypass-atl105-interlink-debit-5d305cd6cb` · Filter `buypass-atl105-interlink-debit` · Selection `{"network": ["INTERLINK"], "platform": ["BUYPASS"], "program": ["DEBIT"], "spec": ["ATL105"]}`
**Review status as of:** 2026-10-31T10:20:40.081351+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-10-31T10:20:41.036786+00:00

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
| Total scenarios / test cases | 28 / 278 |
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
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003115 |  | PASS | qe_shaped_test_data/TD-TC-003115.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003116 |  | PASS | qe_shaped_test_data/TD-TC-003116.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003117 |  | PASS | qe_shaped_test_data/TD-TC-003117.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003118 |  | PASS | qe_shaped_test_data/TD-TC-003118.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003119 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003120 |  | PASS | qe_shaped_test_data/TD-TC-003120.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003121 |  | PASS | qe_shaped_test_data/TD-TC-003121.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003137 |  | PASS | qe_shaped_test_data/TD-TC-003137.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003138 |  | PASS | qe_shaped_test_data/TD-TC-003138.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003139 |  | PASS | qe_shaped_test_data/TD-TC-003139.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003140 |  | PASS | qe_shaped_test_data/TD-TC-003140.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003141 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003142 |  | PASS | qe_shaped_test_data/TD-TC-003142.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003143 |  | PASS | qe_shaped_test_data/TD-TC-003143.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003130 |  | PASS | qe_shaped_test_data/TD-TC-003130.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003131 |  | PASS | qe_shaped_test_data/TD-TC-003131.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003132 |  | PASS | qe_shaped_test_data/TD-TC-003132.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003133 |  | PASS | qe_shaped_test_data/TD-TC-003133.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003134 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003135 |  | PASS | qe_shaped_test_data/TD-TC-003135.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003136 |  | PASS | qe_shaped_test_data/TD-TC-003136.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003093 |  | PASS | qe_shaped_test_data/TD-TC-003093.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003094 |  | PASS | qe_shaped_test_data/TD-TC-003094.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003095 |  | PASS | qe_shaped_test_data/TD-TC-003095.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003096 |  | PASS | qe_shaped_test_data/TD-TC-003096.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003097 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003098 |  | PASS | qe_shaped_test_data/TD-TC-003098.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003099 |  | PASS | qe_shaped_test_data/TD-TC-003099.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003100 |  | PASS | qe_shaped_test_data/TD-TC-003100.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003101 |  | PASS | qe_shaped_test_data/TD-TC-003101.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003102 |  | PASS | qe_shaped_test_data/TD-TC-003102.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003103 |  | PASS | qe_shaped_test_data/TD-TC-003103.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003104 |  | PASS | qe_shaped_test_data/TD-TC-003104.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003105 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003106 |  | PASS | qe_shaped_test_data/TD-TC-003106.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003107 |  | PASS | qe_shaped_test_data/TD-TC-003107.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003122 |  | PASS | qe_shaped_test_data/TD-TC-003122.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003123 |  | PASS | qe_shaped_test_data/TD-TC-003123.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003124 |  | PASS | qe_shaped_test_data/TD-TC-003124.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003125 |  | PASS | qe_shaped_test_data/TD-TC-003125.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003126 |  | PASS | qe_shaped_test_data/TD-TC-003126.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003127 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003128 |  | PASS | qe_shaped_test_data/TD-TC-003128.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003129 |  | PASS | qe_shaped_test_data/TD-TC-003129.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003108 |  | PASS | qe_shaped_test_data/TD-TC-003108.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003109 |  | PASS | qe_shaped_test_data/TD-TC-003109.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003110 |  | PASS | qe_shaped_test_data/TD-TC-003110.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003111 |  | PASS | qe_shaped_test_data/TD-TC-003111.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003112 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003113 |  | PASS | qe_shaped_test_data/TD-TC-003113.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003114 |  | PASS | qe_shaped_test_data/TD-TC-003114.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MOBILE | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CREDENTIAL_ON_FILE | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACT | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACTLESS | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_FALLBACK | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-MAGSTRIPE | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002976 |  | PASS | qe_shaped_test_data/TD-TC-002976.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002977 |  | PASS | qe_shaped_test_data/TD-TC-002977.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002978 |  | PASS | qe_shaped_test_data/TD-TC-002978.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002979 |  | PASS | qe_shaped_test_data/TD-TC-002979.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002980 |  | PASS | qe_shaped_test_data/TD-TC-002980.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002981 |  | PASS | qe_shaped_test_data/TD-TC-002981.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002982 |  | PASS | qe_shaped_test_data/TD-TC-002982.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002983 |  | PASS | qe_shaped_test_data/TD-TC-002983.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002984 |  | PASS | qe_shaped_test_data/TD-TC-002984.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002985 |  | PASS | qe_shaped_test_data/TD-TC-002985.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002986 |  | PASS | qe_shaped_test_data/TD-TC-002986.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002987 |  | PASS | qe_shaped_test_data/TD-TC-002987.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002988 |  | PASS | qe_shaped_test_data/TD-TC-002988.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002989 |  | PASS | qe_shaped_test_data/TD-TC-002989.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002990 |  | PASS | qe_shaped_test_data/TD-TC-002990.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002991 |  | PASS | qe_shaped_test_data/TD-TC-002991.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002992 |  | PASS | qe_shaped_test_data/TD-TC-002992.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002993 |  | PASS | qe_shaped_test_data/TD-TC-002993.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002994 |  | PASS | qe_shaped_test_data/TD-TC-002994.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002995 |  | PASS | qe_shaped_test_data/TD-TC-002995.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002996 |  | PASS | qe_shaped_test_data/TD-TC-002996.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002997 |  | PASS | qe_shaped_test_data/TD-TC-002997.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002998 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-002999 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003000 |  | PASS | qe_shaped_test_data/TD-TC-003000.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003001 |  | PASS | qe_shaped_test_data/TD-TC-003001.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003002 |  | PASS | qe_shaped_test_data/TD-TC-003002.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003003 |  | PASS | qe_shaped_test_data/TD-TC-003003.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003004 |  | PASS | qe_shaped_test_data/TD-TC-003004.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003064 |  | PASS | qe_shaped_test_data/TD-TC-003064.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003065 |  | PASS | qe_shaped_test_data/TD-TC-003065.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003066 |  | PASS | qe_shaped_test_data/TD-TC-003066.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003067 |  | PASS | qe_shaped_test_data/TD-TC-003067.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003068 |  | PASS | qe_shaped_test_data/TD-TC-003068.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003069 |  | PASS | qe_shaped_test_data/TD-TC-003069.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003070 |  | PASS | qe_shaped_test_data/TD-TC-003070.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003071 |  | PASS | qe_shaped_test_data/TD-TC-003071.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003072 |  | PASS | qe_shaped_test_data/TD-TC-003072.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003073 |  | PASS | qe_shaped_test_data/TD-TC-003073.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003074 |  | PASS | qe_shaped_test_data/TD-TC-003074.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003075 |  | PASS | qe_shaped_test_data/TD-TC-003075.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003076 |  | PASS | qe_shaped_test_data/TD-TC-003076.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003077 |  | PASS | qe_shaped_test_data/TD-TC-003077.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003078 |  | PASS | qe_shaped_test_data/TD-TC-003078.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003079 |  | PASS | qe_shaped_test_data/TD-TC-003079.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003080 |  | PASS | qe_shaped_test_data/TD-TC-003080.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003081 |  | PASS | qe_shaped_test_data/TD-TC-003081.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003082 |  | PASS | qe_shaped_test_data/TD-TC-003082.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003083 |  | PASS | qe_shaped_test_data/TD-TC-003083.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003084 |  | PASS | qe_shaped_test_data/TD-TC-003084.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003085 |  | PASS | qe_shaped_test_data/TD-TC-003085.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003086 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003087 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003088 |  | PASS | qe_shaped_test_data/TD-TC-003088.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003089 |  | PASS | qe_shaped_test_data/TD-TC-003089.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003090 |  | PASS | qe_shaped_test_data/TD-TC-003090.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003091 |  | PASS | qe_shaped_test_data/TD-TC-003091.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003092 |  | PASS | qe_shaped_test_data/TD-TC-003092.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003035 |  | PASS | qe_shaped_test_data/TD-TC-003035.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003036 |  | PASS | qe_shaped_test_data/TD-TC-003036.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003037 |  | PASS | qe_shaped_test_data/TD-TC-003037.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003038 |  | PASS | qe_shaped_test_data/TD-TC-003038.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003039 |  | PASS | qe_shaped_test_data/TD-TC-003039.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003040 |  | PASS | qe_shaped_test_data/TD-TC-003040.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003041 |  | PASS | qe_shaped_test_data/TD-TC-003041.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003042 |  | PASS | qe_shaped_test_data/TD-TC-003042.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003043 |  | PASS | qe_shaped_test_data/TD-TC-003043.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003044 |  | PASS | qe_shaped_test_data/TD-TC-003044.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003045 |  | PASS | qe_shaped_test_data/TD-TC-003045.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003046 |  | PASS | qe_shaped_test_data/TD-TC-003046.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003047 |  | PASS | qe_shaped_test_data/TD-TC-003047.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003048 |  | PASS | qe_shaped_test_data/TD-TC-003048.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003049 |  | PASS | qe_shaped_test_data/TD-TC-003049.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003050 |  | PASS | qe_shaped_test_data/TD-TC-003050.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003051 |  | PASS | qe_shaped_test_data/TD-TC-003051.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003052 |  | PASS | qe_shaped_test_data/TD-TC-003052.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003053 |  | PASS | qe_shaped_test_data/TD-TC-003053.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003054 |  | PASS | qe_shaped_test_data/TD-TC-003054.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003055 |  | PASS | qe_shaped_test_data/TD-TC-003055.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003056 |  | PASS | qe_shaped_test_data/TD-TC-003056.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003057 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003058 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003059 |  | PASS | qe_shaped_test_data/TD-TC-003059.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003060 |  | PASS | qe_shaped_test_data/TD-TC-003060.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003061 |  | PASS | qe_shaped_test_data/TD-TC-003061.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003062 |  | PASS | qe_shaped_test_data/TD-TC-003062.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003063 |  | PASS | qe_shaped_test_data/TD-TC-003063.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002887 |  | PASS | qe_shaped_test_data/TD-TC-002887.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002888 |  | PASS | qe_shaped_test_data/TD-TC-002888.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002889 |  | PASS | qe_shaped_test_data/TD-TC-002889.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002890 |  | PASS | qe_shaped_test_data/TD-TC-002890.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002891 |  | PASS | qe_shaped_test_data/TD-TC-002891.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002892 |  | PASS | qe_shaped_test_data/TD-TC-002892.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002893 |  | PASS | qe_shaped_test_data/TD-TC-002893.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002894 |  | PASS | qe_shaped_test_data/TD-TC-002894.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002895 |  | PASS | qe_shaped_test_data/TD-TC-002895.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002896 |  | PASS | qe_shaped_test_data/TD-TC-002896.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002897 |  | PASS | qe_shaped_test_data/TD-TC-002897.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002898 |  | PASS | qe_shaped_test_data/TD-TC-002898.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002899 |  | PASS | qe_shaped_test_data/TD-TC-002899.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002900 |  | PASS | qe_shaped_test_data/TD-TC-002900.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002901 |  | PASS | qe_shaped_test_data/TD-TC-002901.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002902 |  | PASS | qe_shaped_test_data/TD-TC-002902.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002903 |  | PASS | qe_shaped_test_data/TD-TC-002903.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002904 |  | PASS | qe_shaped_test_data/TD-TC-002904.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002905 |  | PASS | qe_shaped_test_data/TD-TC-002905.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002906 |  | PASS | qe_shaped_test_data/TD-TC-002906.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002907 |  | PASS | qe_shaped_test_data/TD-TC-002907.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002908 |  | PASS | qe_shaped_test_data/TD-TC-002908.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002909 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002910 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002911 |  | PASS | qe_shaped_test_data/TD-TC-002911.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002912 |  | PASS | qe_shaped_test_data/TD-TC-002912.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002913 |  | PASS | qe_shaped_test_data/TD-TC-002913.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002914 |  | PASS | qe_shaped_test_data/TD-TC-002914.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-002915 |  | PASS | qe_shaped_test_data/TD-TC-002915.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002916 |  | PASS | qe_shaped_test_data/TD-TC-002916.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002917 |  | PASS | qe_shaped_test_data/TD-TC-002917.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002918 |  | PASS | qe_shaped_test_data/TD-TC-002918.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002919 |  | PASS | qe_shaped_test_data/TD-TC-002919.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002920 |  | PASS | qe_shaped_test_data/TD-TC-002920.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002921 |  | PASS | qe_shaped_test_data/TD-TC-002921.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002922 |  | PASS | qe_shaped_test_data/TD-TC-002922.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002923 |  | PASS | qe_shaped_test_data/TD-TC-002923.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002924 |  | PASS | qe_shaped_test_data/TD-TC-002924.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002925 |  | PASS | qe_shaped_test_data/TD-TC-002925.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002926 |  | PASS | qe_shaped_test_data/TD-TC-002926.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002927 |  | PASS | qe_shaped_test_data/TD-TC-002927.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002928 |  | PASS | qe_shaped_test_data/TD-TC-002928.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002929 |  | PASS | qe_shaped_test_data/TD-TC-002929.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002930 |  | PASS | qe_shaped_test_data/TD-TC-002930.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002931 |  | PASS | qe_shaped_test_data/TD-TC-002931.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002932 |  | PASS | qe_shaped_test_data/TD-TC-002932.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002933 |  | PASS | qe_shaped_test_data/TD-TC-002933.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002934 |  | PASS | qe_shaped_test_data/TD-TC-002934.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002935 |  | PASS | qe_shaped_test_data/TD-TC-002935.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002936 |  | PASS | qe_shaped_test_data/TD-TC-002936.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002937 |  | PASS | qe_shaped_test_data/TD-TC-002937.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002938 |  | PASS | qe_shaped_test_data/TD-TC-002938.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002939 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002940 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002941 |  | PASS | qe_shaped_test_data/TD-TC-002941.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002942 |  | PASS | qe_shaped_test_data/TD-TC-002942.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002943 |  | PASS | qe_shaped_test_data/TD-TC-002943.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002944 |  | PASS | qe_shaped_test_data/TD-TC-002944.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-002945 |  | PASS | qe_shaped_test_data/TD-TC-002945.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003005 |  | PASS | qe_shaped_test_data/TD-TC-003005.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003006 |  | PASS | qe_shaped_test_data/TD-TC-003006.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003007 |  | PASS | qe_shaped_test_data/TD-TC-003007.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003008 |  | PASS | qe_shaped_test_data/TD-TC-003008.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003009 |  | PASS | qe_shaped_test_data/TD-TC-003009.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003010 |  | PASS | qe_shaped_test_data/TD-TC-003010.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003011 |  | PASS | qe_shaped_test_data/TD-TC-003011.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003012 |  | PASS | qe_shaped_test_data/TD-TC-003012.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003013 |  | PASS | qe_shaped_test_data/TD-TC-003013.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003014 |  | PASS | qe_shaped_test_data/TD-TC-003014.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003015 |  | PASS | qe_shaped_test_data/TD-TC-003015.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003016 |  | PASS | qe_shaped_test_data/TD-TC-003016.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003017 |  | PASS | qe_shaped_test_data/TD-TC-003017.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003018 |  | PASS | qe_shaped_test_data/TD-TC-003018.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003019 |  | PASS | qe_shaped_test_data/TD-TC-003019.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003020 |  | PASS | qe_shaped_test_data/TD-TC-003020.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003021 |  | PASS | qe_shaped_test_data/TD-TC-003021.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003022 |  | PASS | qe_shaped_test_data/TD-TC-003022.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003023 |  | PASS | qe_shaped_test_data/TD-TC-003023.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003024 |  | PASS | qe_shaped_test_data/TD-TC-003024.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003025 |  | PASS | qe_shaped_test_data/TD-TC-003025.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003026 |  | PASS | qe_shaped_test_data/TD-TC-003026.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003027 |  | PASS | qe_shaped_test_data/TD-TC-003027.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003028 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003029 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003030 |  | PASS | qe_shaped_test_data/TD-TC-003030.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003031 |  | PASS | qe_shaped_test_data/TD-TC-003031.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003032 |  | PASS | qe_shaped_test_data/TD-TC-003032.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003033 |  | PASS | qe_shaped_test_data/TD-TC-003033.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003034 |  | PASS | qe_shaped_test_data/TD-TC-003034.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002946 |  | PASS | qe_shaped_test_data/TD-TC-002946.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002947 |  | PASS | qe_shaped_test_data/TD-TC-002947.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002948 |  | PASS | qe_shaped_test_data/TD-TC-002948.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002949 |  | PASS | qe_shaped_test_data/TD-TC-002949.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002950 |  | PASS | qe_shaped_test_data/TD-TC-002950.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002951 |  | PASS | qe_shaped_test_data/TD-TC-002951.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002952 |  | PASS | qe_shaped_test_data/TD-TC-002952.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002953 |  | PASS | qe_shaped_test_data/TD-TC-002953.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002954 |  | PASS | qe_shaped_test_data/TD-TC-002954.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002955 |  | PASS | qe_shaped_test_data/TD-TC-002955.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002956 |  | PASS | qe_shaped_test_data/TD-TC-002956.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002957 |  | PASS | qe_shaped_test_data/TD-TC-002957.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002958 |  | PASS | qe_shaped_test_data/TD-TC-002958.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002959 |  | PASS | qe_shaped_test_data/TD-TC-002959.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002960 |  | PASS | qe_shaped_test_data/TD-TC-002960.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002961 |  | PASS | qe_shaped_test_data/TD-TC-002961.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002962 |  | PASS | qe_shaped_test_data/TD-TC-002962.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002963 |  | PASS | qe_shaped_test_data/TD-TC-002963.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002964 |  | PASS | qe_shaped_test_data/TD-TC-002964.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002965 |  | PASS | qe_shaped_test_data/TD-TC-002965.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002966 |  | PASS | qe_shaped_test_data/TD-TC-002966.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002967 |  | PASS | qe_shaped_test_data/TD-TC-002967.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002968 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002969 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002970 |  | PASS | qe_shaped_test_data/TD-TC-002970.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002971 |  | PASS | qe_shaped_test_data/TD-TC-002971.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002972 |  | PASS | qe_shaped_test_data/TD-TC-002972.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002973 |  | PASS | qe_shaped_test_data/TD-TC-002973.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002974 |  | PASS | qe_shaped_test_data/TD-TC-002974.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-002975 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003153 |  | PASS | qe_shaped_test_data/TD-TC-003153.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003154 |  | PASS | qe_shaped_test_data/TD-TC-003154.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003155 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-003162 |  | PASS | qe_shaped_test_data/TD-TC-003162.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-003163 |  | PASS | qe_shaped_test_data/TD-TC-003163.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-003164 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CREDENTIAL_ON_FILE | business_scenario | request | TC-003159 |  | PASS | qe_shaped_test_data/TD-TC-003159.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CREDENTIAL_ON_FILE | business_scenario | request | TC-003160 |  | PASS | qe_shaped_test_data/TD-TC-003160.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CREDENTIAL_ON_FILE | business_scenario | request | TC-003161 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT | business_scenario | request | TC-003144 |  | PASS | qe_shaped_test_data/TD-TC-003144.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT | business_scenario | request | TC-003145 |  | PASS | qe_shaped_test_data/TD-TC-003145.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT | business_scenario | request | TC-003146 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-003147 |  | PASS | qe_shaped_test_data/TD-TC-003147.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-003148 |  | PASS | qe_shaped_test_data/TD-TC-003148.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-003149 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK | business_scenario | request | TC-003156 |  | PASS | qe_shaped_test_data/TD-TC-003156.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK | business_scenario | request | TC-003157 |  | PASS | qe_shaped_test_data/TD-TC-003157.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK | business_scenario | request | TC-003158 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE | business_scenario | request | TC-003150 |  | PASS | qe_shaped_test_data/TD-TC-003150.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE | business_scenario | request | TC-003151 |  | PASS | qe_shaped_test_data/TD-TC-003151.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE | business_scenario | request | TC-003152 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |

## 5. Requirement detail

### BR-EAD513C5 — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 Interlink Debit
Segment `UNMAPPED` · Source page 512 · Derivation `business_requirement`

- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003115** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-003115 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003115.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003116** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-003116 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003116.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003117** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-003117 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003117.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003118** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-003118 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003118.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003119** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-003119 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003119 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003120** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-003120 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003120.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003121** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-003121 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003121.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003137** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-003137 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003137.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003138** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-003138 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003138.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003139** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-003139 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003139.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003140** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-003140 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003140.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003141** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-003141 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003141 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003142** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-003142 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003142.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003143** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-003143 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003143.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CREDENTIAL_ON_FILE** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003130** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File — TC-003130 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003130.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003131** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File — TC-003131 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003131.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003132** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File — TC-003132 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003132.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003133** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File — TC-003133 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003133.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003134** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File — TC-003134 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003134 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003135** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File — TC-003135 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003135.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003136** — BUYPASS ATL105 Interlink Debit Authorization Only – Credential on File — TC-003136 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003136.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003093** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-003093 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003093.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003094** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-003094 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003094.step1.auth-only.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003095** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-003095 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003095.step1.auth-only.json` — 5 segments, 63 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003096** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-003096 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003096.step1.auth-only.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003097** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-003097 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003097 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003098** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-003098 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003098.step1.auth-only.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003099** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-003099 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003099.step1.auth-only.json` — 4 segments, 65 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003100** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003100 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003100.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003101** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003101 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003101.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003102** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003102 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003102.step1.auth-only.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003103** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003103 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003103.step1.auth-only.json` — 5 segments, 63 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003104** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003104 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003104.step1.auth-only.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003105** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003105 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003105 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003106** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003106 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003106.step1.auth-only.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003107** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-003107 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003107.step1.auth-only.json` — 4 segments, 65 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003122** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003122 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003122.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003123** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003123 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003123.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003124** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003124 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003124.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003125** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003125 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003125.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003126** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003126 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003126.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003127** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003127 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003127 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003128** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003128 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003128.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003129** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-003129 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003129.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003108** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-003108 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003108.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003109** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-003109 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003109.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003110** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-003110 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003110.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003111** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-003111 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003111.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003112** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-003112 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003112 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003113** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-003113 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003113.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003114** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-003114 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003114.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CREDENTIAL_ON_FILE** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-002976** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002976 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002976.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002977** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002977 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002977.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002978** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002978 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002978.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002979** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002979 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002979.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002980** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002980 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002980.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002981** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002981 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002981.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002982** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002982 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002982.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002983** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002983 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002983.step1.purchase.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002984** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002984 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002984.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002985** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002985 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002985.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002986** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002986 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002986.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002987** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002987 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002987.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002988** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002988 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002988.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002989** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002989 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002989.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002990** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002990 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002990.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002991** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002991 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002991.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002992** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002992 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002992.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002993** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002993 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002993.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002994** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002994 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002994.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002995** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002995 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002995.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002996** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002996 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002996.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002997** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002997 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002997.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002998** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002998 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-002998 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002999** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-002999 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-002999 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003000** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-003000 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003000.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003001** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-003001 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003001.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003002** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-003002 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003002.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003003** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-003003 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003003.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003004** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-003004 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003004.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003064** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003064 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003064.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003065** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003065 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003065.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003066** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003066 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003066.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003067** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003067 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003067.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003068** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003068 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003068.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003069** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003069 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003069.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003070** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003070 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003070.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003071** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003071 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003071.step1.purchase.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003072** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003072 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003072.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003073** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003073 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003073.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003074** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003074 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003074.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003075** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003075 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003075.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003076** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003076 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003076.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003077** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003077 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003077.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003078** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003078 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003078.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003079** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003079 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003079.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003080** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003080 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003080.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003081** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003081 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003081.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003082** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003082 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003082.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003083** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003083 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003083.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003084** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003084 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003084.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003085** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003085 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003085.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003086** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003086 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003086 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003087** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003087 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003087 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003088** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003088 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003088.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003089** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003089 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003089.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003090** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003090 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003090.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003091** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003091 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003091.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003092** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-003092 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003092.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-CREDENTIAL_ON_FILE** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003035** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003035 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003035.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003036** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003036 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003036.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003037** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003037 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003037.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003038** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003038 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003038.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003039** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003039 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003039.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003040** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003040 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003040.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003041** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003041 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003041.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003042** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003042 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003042.step1.purchase.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003043** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003043 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003043.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003044** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003044 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003044.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003045** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003045 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003045.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003046** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003046 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003046.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003047** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003047 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003047.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003048** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003048 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003048.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003049** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003049 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003049.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003050** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003050 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003050.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003051** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003051 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003051.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003052** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003052 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003052.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003053** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003053 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003053.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003054** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003054 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003054.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003055** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003055 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003055.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003056** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003056 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003056.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003057** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003057 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003057 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003058** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003058 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003058 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003059** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003059 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003059.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003060** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003060 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003060.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003061** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003061 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003061.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003062** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003062 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003062.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003063** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Credential on File — TC-003063 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003063.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-002887** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002887 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002887.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002888** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002888 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002888.step1.purchase.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002889** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002889 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002889.step1.purchase.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002890** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002890 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002890.step1.purchase.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002891** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002891 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002891.step1.purchase.json` — 4 segments, 72 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002892** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002892 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002892.step1.purchase.json` — 4 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002893** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002893 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002893.step1.purchase.json` — 5 segments, 44 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002894** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002894 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002894.step1.purchase.json` — 5 segments, 63 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002895** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002895 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002895.step1.purchase.json` — 5 segments, 71 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002896** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002896 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002896.step1.purchase.json` — 5 segments, 62 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002897** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002897 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002897.step1.purchase.json` — 5 segments, 44 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002898** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002898 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002898.step1.purchase.json` — 5 segments, 44 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002899** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002899 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002899.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002900** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002900 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002900.step1.purchase.json` — 4 segments, 64 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002901** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002901 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002901.step1.purchase.json` — 4 segments, 54 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002902** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002902 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002902.step1.purchase.json` — 4 segments, 73 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002903** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002903 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002903.step1.purchase.json` — 4 segments, 72 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002904** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002904 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002904.step1.purchase.json` — 4 segments, 54 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002905** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002905 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002905.step1.purchase.json` — 4 segments, 72 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002906** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002906 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002906.step1.purchase.json` — 5 segments, 71 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002907** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002907 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002907.step1.purchase.json` — 5 segments, 71 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002908** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002908 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002908.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002909** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002909 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-002909 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002910** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002910 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-002910 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002911** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002911 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002911.step1.purchase.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002912** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002912 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002912.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002913** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002913 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002913.json` — 4 segments, 28 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-002914** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002914 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002914.json` — 4 segments, 28 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-002915** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-002915 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002915.step1.purchase.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-002916** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002916 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002916.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002917** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002917 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002917.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002918** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002918 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002918.step1.purchase.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002919** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002919 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002919.step1.purchase.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002920** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002920 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002920.step1.purchase.json` — 4 segments, 45 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002921** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002921 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002921.step1.purchase.json` — 4 segments, 72 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002922** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002922 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002922.step1.purchase.json` — 4 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002923** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002923 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002923.step1.purchase.json` — 5 segments, 44 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002924** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002924 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002924.step1.purchase.json` — 5 segments, 63 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002925** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002925 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002925.step1.purchase.json` — 5 segments, 71 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002926** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002926 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002926.step1.purchase.json` — 5 segments, 62 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002927** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002927 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002927.step1.purchase.json` — 5 segments, 44 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002928** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002928 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002928.step1.purchase.json` — 5 segments, 44 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002929** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002929 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002929.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002930** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002930 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002930.step1.purchase.json` — 4 segments, 64 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002931** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002931 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002931.step1.purchase.json` — 4 segments, 54 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002932** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002932 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002932.step1.purchase.json` — 4 segments, 73 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002933** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002933 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002933.step1.purchase.json` — 4 segments, 72 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002934** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002934 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002934.step1.purchase.json` — 4 segments, 54 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002935** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002935 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002935.step1.purchase.json` — 4 segments, 72 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002936** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002936 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002936.step1.purchase.json` — 5 segments, 71 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002937** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002937 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002937.step1.purchase.json` — 5 segments, 71 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002938** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002938 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002938.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002939** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002939 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-002939 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002940** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002940 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-002940 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002941** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002941 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002941.step1.purchase.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002942** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002942 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002942.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002943** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002943 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002943.json` — 4 segments, 28 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-002944** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002944 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002944.json` — 4 segments, 28 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-002945** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-002945 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-002945.step1.purchase.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003005** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003005 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003005.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003006** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003006 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003006.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003007** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003007 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003007.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003008** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003008 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003008.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003009** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003009 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003009.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003010** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003010 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003010.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003011** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003011 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003011.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003012** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003012 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003012.step1.purchase.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003013** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003013 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003013.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003014** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003014 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003014.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003015** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003015 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003015.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003016** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003016 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003016.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003017** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003017 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003017.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003018** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003018 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003018.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003019** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003019 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003019.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003020** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003020 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003020.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003021** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003021 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003021.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003022** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003022 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003022.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003023** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003023 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003023.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003024** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003024 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003024.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003025** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003025 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003025.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003026** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003026 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003026.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003027** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003027 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003027.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003028** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003028 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003028 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003029** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003029 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003029 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003030** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003030 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003030.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003031** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003031 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003031.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003032** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003032 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003032.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003033** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003033 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003033.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-003034** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-003034 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003034.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-002946** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002946 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002946.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002947** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002947 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002947.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002948** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002948 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002948.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002949** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002949 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002949.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002950** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002950 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002950.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002951** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002951 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002951.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002952** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002952 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002952.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002953** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002953 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002953.step1.purchase.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002954** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002954 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002954.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002955** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002955 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002955.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002956** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002956 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002956.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002957** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002957 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002957.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002958** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002958 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002958.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002959** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002959 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002959.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002960** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002960 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002960.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002961** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002961 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002961.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002962** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002962 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002962.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002963** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002963 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002963.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002964** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002964 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002964.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002965** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002965 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002965.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002966** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002966 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002966.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002967** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002967 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002967.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002968** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002968 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-002968 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002969** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002969 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-002969 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-002970** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002970 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002970.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-002971** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002971 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002971.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-002972** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002972 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002972.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-002973** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002973 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002973.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-002974** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002974 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-002974.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
  - **TC-002975** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-002975 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-002975 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003153** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe — TC-003153 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003153.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003154** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe — TC-003154 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003154.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003155** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe — TC-003155 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003155 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003162** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device — TC-003162 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003162.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003163** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device — TC-003163 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003163.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003164** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device — TC-003164 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003164 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-DEBIT-INTERLINK-RETURN-CREDENTIAL_ON_FILE** — BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003159** — BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File — TC-003159 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003159.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003160** — BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File — TC-003160 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003160.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003161** — BUYPASS ATL105 Interlink Debit Merchandise Return – Credential on File — TC-003161 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003161 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003144** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip — TC-003144 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003144.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003145** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip — TC-003145 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003145.step1.return.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003146** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip — TC-003146 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003146 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003147** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip — TC-003147 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003147.json` — 4 segments, 27 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003148** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip — TC-003148 · `None` · verification `PASS` · review `pending_review`
    - Test data: `qe_shaped_test_data/TD-TC-003148.step1.return.json` — 4 segments, 46 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003149** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip — TC-003149 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003149 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003156** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback — TC-003156 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003156.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003157** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback — TC-003157 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003157.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003158** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback — TC-003158 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003158 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003150** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe — TC-003150 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003150.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-003151** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe — TC-003151 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `qe_shaped_test_data/TD-TC-003151.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003152** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe — TC-003152 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003152 was not found on disk. Re-run Test Case Generation to regenerate test data files._

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-002909`
- `TC-002910`
- `TC-002939`
- `TC-002940`
- `TC-002968`
- `TC-002969`
- `TC-002975`
- `TC-002998`
- `TC-002999`
- `TC-003028`
- `TC-003029`
- `TC-003057`
- `TC-003058`
- `TC-003086`
- `TC-003087`
- `TC-003097`
- `TC-003105`
- `TC-003112`
- `TC-003119`
- `TC-003127`
- `TC-003134`
- `TC-003141`
- `TC-003146`
- `TC-003149`
- `TC-003152`
- `TC-003155`
- `TC-003158`
- `TC-003161`
- `TC-003164`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
