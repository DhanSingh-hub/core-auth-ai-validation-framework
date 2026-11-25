# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `interlink-debit-47c4cd511f` · Filter `interlink-debit` · Selection `{"network": ["INTERLINK"], "platform": ["BUYPASS"], "program": ["DEBIT"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-05T08:48:33.566659+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-05T08:48:34.306703+00:00

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
| Total scenarios / test cases | 24 / 257 |
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
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000206 |  | PASS | test_data/TD-TC-000206.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000207 |  | PASS | test_data/TD-TC-000207.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000208 |  | PASS | test_data/TD-TC-000208.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000209 |  | PASS | test_data/TD-TC-000209.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000210 |  | PASS | test_data/TD-TC-000210.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000211 |  | PASS | test_data/TD-TC-000211.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000212 |  | PASS | test_data/TD-TC-000212.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000213 |  | PASS | test_data/TD-TC-000213.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000214 |  | PASS | test_data/TD-TC-000214.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000225 |  | PASS | test_data/TD-TC-000225.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000226 |  | PASS | test_data/TD-TC-000226.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000227 |  | PASS | test_data/TD-TC-000227.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000228 |  | PASS | test_data/TD-TC-000228.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000229 |  | PASS | test_data/TD-TC-000229.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000230 |  | PASS | test_data/TD-TC-000230.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000231 |  | PASS | test_data/TD-TC-000231.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000232 |  | PASS | test_data/TD-TC-000232.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-000233 |  | PASS | test_data/TD-TC-000233.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000178 |  | PASS | test_data/TD-TC-000178.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000179 |  | PASS | test_data/TD-TC-000179.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000180 |  | PASS | test_data/TD-TC-000180.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000181 |  | PASS | test_data/TD-TC-000181.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000182 |  | PASS | test_data/TD-TC-000182.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000183 |  | PASS | test_data/TD-TC-000183.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000184 |  | PASS | test_data/TD-TC-000184.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000185 |  | PASS | test_data/TD-TC-000185.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000186 |  | PASS | test_data/TD-TC-000186.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000187 |  | PASS | test_data/TD-TC-000187.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000188 |  | PASS | test_data/TD-TC-000188.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000189 |  | PASS | test_data/TD-TC-000189.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000190 |  | PASS | test_data/TD-TC-000190.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000191 |  | PASS | test_data/TD-TC-000191.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000192 |  | PASS | test_data/TD-TC-000192.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000193 |  | PASS | test_data/TD-TC-000193.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000194 |  | PASS | test_data/TD-TC-000194.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000195 |  | PASS | test_data/TD-TC-000195.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000196 |  | PASS | test_data/TD-TC-000196.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000215 |  | PASS | test_data/TD-TC-000215.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000216 |  | PASS | test_data/TD-TC-000216.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000217 |  | PASS | test_data/TD-TC-000217.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000218 |  | PASS | test_data/TD-TC-000218.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000219 |  | PASS | test_data/TD-TC-000219.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000220 |  | PASS | test_data/TD-TC-000220.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000221 |  | PASS | test_data/TD-TC-000221.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000222 |  | PASS | test_data/TD-TC-000222.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000223 |  | PASS | test_data/TD-TC-000223.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-000224 |  | PASS | test_data/TD-TC-000224.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000197 |  | PASS | test_data/TD-TC-000197.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000198 |  | PASS | test_data/TD-TC-000198.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000199 |  | PASS | test_data/TD-TC-000199.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000200 |  | PASS | test_data/TD-TC-000200.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000201 |  | PASS | test_data/TD-TC-000201.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000202 |  | PASS | test_data/TD-TC-000202.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000203 |  | PASS | test_data/TD-TC-000203.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000204 |  | PASS | test_data/TD-TC-000204.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000205 |  | PASS | test_data/TD-TC-000205.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MOBILE | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACT | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACTLESS | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_FALLBACK | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-MAGSTRIPE | business_scenario | request |  |  |  |  | FULLY_TRACED | pending_review / pending_review / — |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000090 |  | PASS | test_data/TD-TC-000090.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000091 |  | PASS | test_data/TD-TC-000091.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000092 |  | PASS | test_data/TD-TC-000092.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000093 |  | PASS | test_data/TD-TC-000093.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000094 |  | PASS | test_data/TD-TC-000094.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000095 |  | PASS | test_data/TD-TC-000095.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000096 |  | PASS | test_data/TD-TC-000096.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000097 |  | PASS | test_data/TD-TC-000097.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000098 |  | PASS | test_data/TD-TC-000098.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000099 |  | PASS | test_data/TD-TC-000099.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000100 |  | PASS | test_data/TD-TC-000100.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000101 |  | PASS | test_data/TD-TC-000101.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000102 |  | PASS | test_data/TD-TC-000102.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000103 |  | PASS | test_data/TD-TC-000103.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000104 |  | PASS | test_data/TD-TC-000104.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000105 |  | PASS | test_data/TD-TC-000105.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000106 |  | PASS | test_data/TD-TC-000106.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000107 |  | PASS | test_data/TD-TC-000107.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000108 |  | PASS | test_data/TD-TC-000108.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000109 |  | PASS | test_data/TD-TC-000109.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000110 |  | PASS | test_data/TD-TC-000110.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000111 |  | PASS | test_data/TD-TC-000111.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000112 |  | PASS | test_data/TD-TC-000112.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000113 |  | PASS | test_data/TD-TC-000113.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000114 |  | PASS | test_data/TD-TC-000114.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000115 |  | PASS | test_data/TD-TC-000115.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000116 |  | PASS | test_data/TD-TC-000116.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000117 |  | PASS | test_data/TD-TC-000117.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000118 |  | PASS | test_data/TD-TC-000118.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000149 |  | PASS | test_data/TD-TC-000149.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000150 |  | PASS | test_data/TD-TC-000150.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000151 |  | PASS | test_data/TD-TC-000151.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000152 |  | PASS | test_data/TD-TC-000152.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000153 |  | PASS | test_data/TD-TC-000153.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000154 |  | PASS | test_data/TD-TC-000154.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000155 |  | PASS | test_data/TD-TC-000155.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000156 |  | PASS | test_data/TD-TC-000156.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000157 |  | PASS | test_data/TD-TC-000157.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000158 |  | PASS | test_data/TD-TC-000158.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000159 |  | PASS | test_data/TD-TC-000159.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000160 |  | PASS | test_data/TD-TC-000160.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000161 |  | PASS | test_data/TD-TC-000161.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000162 |  | PASS | test_data/TD-TC-000162.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000163 |  | PASS | test_data/TD-TC-000163.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000164 |  | PASS | test_data/TD-TC-000164.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000165 |  | PASS | test_data/TD-TC-000165.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000166 |  | PASS | test_data/TD-TC-000166.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000167 |  | PASS | test_data/TD-TC-000167.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000168 |  | PASS | test_data/TD-TC-000168.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000169 |  | PASS | test_data/TD-TC-000169.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000170 |  | PASS | test_data/TD-TC-000170.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000171 |  | PASS | test_data/TD-TC-000171.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000172 |  | PASS | test_data/TD-TC-000172.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000173 |  | PASS | test_data/TD-TC-000173.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000174 |  | PASS | test_data/TD-TC-000174.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000175 |  | PASS | test_data/TD-TC-000175.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000176 |  | PASS | test_data/TD-TC-000176.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000177 |  | PASS | test_data/TD-TC-000177.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000001 |  | PASS | test_data/TD-TC-000001.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000002 |  | PASS | test_data/TD-TC-000002.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000003 |  | PASS | test_data/TD-TC-000003.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000004 |  | PASS | test_data/TD-TC-000004.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000005 |  | PASS | test_data/TD-TC-000005.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000006 |  | PASS | test_data/TD-TC-000006.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000007 |  | PASS | test_data/TD-TC-000007.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000008 |  | PASS | test_data/TD-TC-000008.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000009 |  | PASS | test_data/TD-TC-000009.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000010 |  | PASS | test_data/TD-TC-000010.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000011 |  | PASS | test_data/TD-TC-000011.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000012 |  | PASS | test_data/TD-TC-000012.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000013 |  | PASS | test_data/TD-TC-000013.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000014 |  | PASS | test_data/TD-TC-000014.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000015 |  | PASS | test_data/TD-TC-000015.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000016 |  | PASS | test_data/TD-TC-000016.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000017 |  | PASS | test_data/TD-TC-000017.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000018 |  | PASS | test_data/TD-TC-000018.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000019 |  | PASS | test_data/TD-TC-000019.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000020 |  | PASS | test_data/TD-TC-000020.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000021 |  | PASS | test_data/TD-TC-000021.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000022 |  | PASS | test_data/TD-TC-000022.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000023 |  | PASS | test_data/TD-TC-000023.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000024 |  | PASS | test_data/TD-TC-000024.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000025 |  | PASS | test_data/TD-TC-000025.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000026 |  | PASS | test_data/TD-TC-000026.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000027 |  | PASS | test_data/TD-TC-000027.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000028 |  | PASS | test_data/TD-TC-000028.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000029 |  | PASS | test_data/TD-TC-000029.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000030 |  | PASS | test_data/TD-TC-000030.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000031 |  | PASS | test_data/TD-TC-000031.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000032 |  | PASS | test_data/TD-TC-000032.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000033 |  | PASS | test_data/TD-TC-000033.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000034 |  | PASS | test_data/TD-TC-000034.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000035 |  | PASS | test_data/TD-TC-000035.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000036 |  | PASS | test_data/TD-TC-000036.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000037 |  | PASS | test_data/TD-TC-000037.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000038 |  | PASS | test_data/TD-TC-000038.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000039 |  | PASS | test_data/TD-TC-000039.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000040 |  | PASS | test_data/TD-TC-000040.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000041 |  | PASS | test_data/TD-TC-000041.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000042 |  | PASS | test_data/TD-TC-000042.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000043 |  | PASS | test_data/TD-TC-000043.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000044 |  | PASS | test_data/TD-TC-000044.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000045 |  | PASS | test_data/TD-TC-000045.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000046 |  | PASS | test_data/TD-TC-000046.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000047 |  | PASS | test_data/TD-TC-000047.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000048 |  | PASS | test_data/TD-TC-000048.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000049 |  | PASS | test_data/TD-TC-000049.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000050 |  | PASS | test_data/TD-TC-000050.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000051 |  | PASS | test_data/TD-TC-000051.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000052 |  | PASS | test_data/TD-TC-000052.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000053 |  | PASS | test_data/TD-TC-000053.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000054 |  | PASS | test_data/TD-TC-000054.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000055 |  | PASS | test_data/TD-TC-000055.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000056 |  | PASS | test_data/TD-TC-000056.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000057 |  | PASS | test_data/TD-TC-000057.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000058 |  | PASS | test_data/TD-TC-000058.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000059 |  | PASS | test_data/TD-TC-000059.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000119 |  | PASS | test_data/TD-TC-000119.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000120 |  | PASS | test_data/TD-TC-000120.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000121 |  | PASS | test_data/TD-TC-000121.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000122 |  | PASS | test_data/TD-TC-000122.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000123 |  | PASS | test_data/TD-TC-000123.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000124 |  | PASS | test_data/TD-TC-000124.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000125 |  | PASS | test_data/TD-TC-000125.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000126 |  | PASS | test_data/TD-TC-000126.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000127 |  | PASS | test_data/TD-TC-000127.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000128 |  | PASS | test_data/TD-TC-000128.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000129 |  | PASS | test_data/TD-TC-000129.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000130 |  | PASS | test_data/TD-TC-000130.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000131 |  | PASS | test_data/TD-TC-000131.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000132 |  | PASS | test_data/TD-TC-000132.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000133 |  | PASS | test_data/TD-TC-000133.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000134 |  | PASS | test_data/TD-TC-000134.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000135 |  | PASS | test_data/TD-TC-000135.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000136 |  | PASS | test_data/TD-TC-000136.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000137 |  | PASS | test_data/TD-TC-000137.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000138 |  | PASS | test_data/TD-TC-000138.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000139 |  | PASS | test_data/TD-TC-000139.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000140 |  | PASS | test_data/TD-TC-000140.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000141 |  | PASS | test_data/TD-TC-000141.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000142 |  | PASS | test_data/TD-TC-000142.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000143 |  | PASS | test_data/TD-TC-000143.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000144 |  | PASS | test_data/TD-TC-000144.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000145 |  | PASS | test_data/TD-TC-000145.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000146 |  | PASS | test_data/TD-TC-000146.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000147 |  | PASS | test_data/TD-TC-000147.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000148 |  | PASS | test_data/TD-TC-000148.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000060 |  | PASS | test_data/TD-TC-000060.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000061 |  | PASS | test_data/TD-TC-000061.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000062 |  | PASS | test_data/TD-TC-000062.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000063 |  | PASS | test_data/TD-TC-000063.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000064 |  | PASS | test_data/TD-TC-000064.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000065 |  | PASS | test_data/TD-TC-000065.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000066 |  | PASS | test_data/TD-TC-000066.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000067 |  | PASS | test_data/TD-TC-000067.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000068 |  | PASS | test_data/TD-TC-000068.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000069 |  | PASS | test_data/TD-TC-000069.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000070 |  | PASS | test_data/TD-TC-000070.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000071 |  | PASS | test_data/TD-TC-000071.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000072 |  | PASS | test_data/TD-TC-000072.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000073 |  | PASS | test_data/TD-TC-000073.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000074 |  | PASS | test_data/TD-TC-000074.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000075 |  | PASS | test_data/TD-TC-000075.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000076 |  | PASS | test_data/TD-TC-000076.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000077 |  | PASS | test_data/TD-TC-000077.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000078 |  | PASS | test_data/TD-TC-000078.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000079 |  | PASS | test_data/TD-TC-000079.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000080 |  | PASS | test_data/TD-TC-000080.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000081 |  | PASS | test_data/TD-TC-000081.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000082 |  | PASS | test_data/TD-TC-000082.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000083 |  | PASS | test_data/TD-TC-000083.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000084 |  | PASS | test_data/TD-TC-000084.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000085 |  | PASS | test_data/TD-TC-000085.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000086 |  | PASS | test_data/TD-TC-000086.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000087 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000088 |  | PASS | test_data/TD-TC-000088.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000089 |  | PASS | test_data/TD-TC-000089.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000246 |  | PASS | test_data/TD-TC-000246.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000247 |  | PASS | test_data/TD-TC-000247.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000248 |  | PASS | test_data/TD-TC-000248.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000249 |  | PASS | test_data/TD-TC-000249.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-000254 |  | PASS | test_data/TD-TC-000254.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-000255 |  | PASS | test_data/TD-TC-000255.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-000256 |  | PASS | test_data/TD-TC-000256.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-000257 |  | PASS | test_data/TD-TC-000257.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT | business_scenario | request | TC-000234 |  | PASS | test_data/TD-TC-000234.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT | business_scenario | request | TC-000235 |  | PASS | test_data/TD-TC-000235.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT | business_scenario | request | TC-000236 |  | PASS | test_data/TD-TC-000236.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT | business_scenario | request | TC-000237 |  | PASS | test_data/TD-TC-000237.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-000238 |  | PASS | test_data/TD-TC-000238.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-000239 |  | PASS | test_data/TD-TC-000239.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-000240 |  | PASS | test_data/TD-TC-000240.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-000241 |  | PASS | test_data/TD-TC-000241.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK | business_scenario | request | TC-000250 |  | PASS | test_data/TD-TC-000250.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK | business_scenario | request | TC-000251 |  | PASS | test_data/TD-TC-000251.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK | business_scenario | request | TC-000252 |  | PASS | test_data/TD-TC-000252.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK | business_scenario | request | TC-000253 |  | PASS | test_data/TD-TC-000253.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE | business_scenario | request | TC-000242 |  | PASS | test_data/TD-TC-000242.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE | business_scenario | request | TC-000243 |  | PASS | test_data/TD-TC-000243.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE | business_scenario | request | TC-000244 |  | PASS | test_data/TD-TC-000244.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-EAD513C5 | BUYPASS ATL105 Interlink Debit | TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE | business_scenario | request | TC-000245 |  | PASS | test_data/TD-TC-000245.step1.return.json | FULLY_TRACED | pending_review / pending_review / pending_sme |

## 5. Requirement detail

### BR-EAD513C5 — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 Interlink Debit
Segment `UNMAPPED` · Source page 512 · Derivation `business_requirement`

- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000206** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000206 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000206.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000207** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000207 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000207.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000208** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000208 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000208.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000209** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000209 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000209.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000210** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000210 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000210.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000211** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000211 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000211.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000212** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000212 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000212.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000213** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000213 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000213.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000214** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Magnetic Stripe — TC-000214 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000214.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000225** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000225 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000225.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000226** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000226 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000226.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000227** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000227 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000227.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000228** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000228 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000228.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000229** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000229 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000229.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000230** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000230 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000230.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000231** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000231 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000231.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000232** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000232 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000232.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000233** — BUYPASS ATL105 Interlink Debit Authorization Only – Contactless Mobile Commerce Device — TC-000233 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000233.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000178** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000178 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000178.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000179** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000179 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000179.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000180** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000180 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000180.step1.auth-only.json` — 5 segments, 65 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000181** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000181 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000181.step1.auth-only.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000182** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000182 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000182.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000183** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000183 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000183.step1.auth-only.json` — 4 segments, 67 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000184** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000184 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000184.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000185** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000185 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000185.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000186** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contact Chip — TC-000186 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000186.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000187** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000187 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000187.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000188** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000188 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000188.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000189** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000189 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000189.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000190** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000190 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000190.step1.auth-only.json` — 5 segments, 65 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000191** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000191 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000191.step1.auth-only.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000192** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000192 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000192.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000193** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000193 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000193.step1.auth-only.json` — 4 segments, 67 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000194** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000194 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000194.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000195** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000195 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000195.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000196** — BUYPASS ATL105 Interlink Debit Authorization Only – EMV Contactless Chip — TC-000196 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000196.step1.auth-only.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000215** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000215 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000215.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000216** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000216 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000216.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000217** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000217 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000217.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000218** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000218 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000218.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000219** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000219 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000219.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000220** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000220 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000220.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000221** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000221 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000221.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000222** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000222 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000222.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000223** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000223 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000223.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000224** — BUYPASS ATL105 Interlink Debit Authorization Only – Chip Fallback — TC-000224 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000224.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-AUTH_ONLY-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000197** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000197 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000197.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000198** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000198 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000198.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000199** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000199 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000199.step1.auth-only.json` — 4 segments, 54 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000200** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000200 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000200.step1.auth-only.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000201** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000201 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000201.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000202** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000202 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000202.step1.auth-only.json` — 3 segments, 56 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000203** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000203 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000203.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000204** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000204 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000204.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000205** — BUYPASS ATL105 Interlink Debit Authorization Only – Magnetic Stripe — TC-000205 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000205.step1.auth-only.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Balance Inquiry – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-BALANCE_INQUIRY-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Balance Inquiry – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000090** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000090 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000090.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000091** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000091 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000091.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000092** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000092 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000092.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000093** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000093 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000093.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000094** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000094 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000094.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000095** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000095 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000095.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000096** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000096 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000096.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000097** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000097 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000097.step1.purchase.json` — 4 segments, 51 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000098** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000098 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000098.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000099** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000099 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000099.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000100** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000100 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000100.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000101** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000101 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000101.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000102** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000102 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000102.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000103** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000103 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000103.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000104** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000104 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000104.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000105** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000105 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000105.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000106** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000106 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000106.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000107** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000107 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000107.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000108** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000108 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000108.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000109** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000109 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000109.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000110** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000110 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000110.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000111** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000111 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000111.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000112** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000112 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000112.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000113** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000113 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000113.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000114** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000114 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000114.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000115** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000115 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000115.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000116** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000116 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000116.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
  - **TC-000117** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000117 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000117.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000118** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Magnetic Stripe — TC-000118 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000118.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000149** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000149 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000149.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000150** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000150 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000150.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000151** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000151 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000151.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000152** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000152 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000152.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000153** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000153 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000153.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000154** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000154 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000154.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000155** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000155 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000155.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000156** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000156 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000156.step1.purchase.json` — 4 segments, 51 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000157** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000157 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000157.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000158** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000158 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000158.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000159** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000159 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000159.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000160** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000160 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000160.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000161** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000161 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000161.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000162** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000162 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000162.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000163** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000163 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000163.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000164** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000164 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000164.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000165** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000165 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000165.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000166** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000166 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000166.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000167** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000167 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000167.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000168** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000168 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000168.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000169** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000169 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000169.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000170** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000170 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000170.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000171** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000171 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000171.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000172** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000172 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000172.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000173** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000173 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000173.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000174** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000174 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000174.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000175** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000175 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000175.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
  - **TC-000176** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000176 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000176.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000177** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Contactless Mobile Commerce Device — TC-000177 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000177.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000001** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000001 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000001.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000002** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000002 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000002.step1.purchase.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000003** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000003 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000003.step1.purchase.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000004** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000004 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000004.step1.purchase.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000005** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000005 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000005.step1.purchase.json` — 4 segments, 76 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000006** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000006 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000006.step1.purchase.json` — 4 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000007** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000007 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000007.step1.purchase.json` — 5 segments, 46 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000008** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000008 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000008.step1.purchase.json` — 5 segments, 62 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000009** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000009 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000009.step1.purchase.json` — 5 segments, 75 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000010** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000010 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000010.step1.purchase.json` — 5 segments, 64 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000011** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000011 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000011.step1.purchase.json` — 5 segments, 46 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000012** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000012 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000012.step1.purchase.json` — 5 segments, 46 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000013** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000013 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000013.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000014** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000014 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000014.step1.purchase.json` — 4 segments, 66 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000015** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000015 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000015.step1.purchase.json` — 4 segments, 58 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000016** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000016 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000016.step1.purchase.json` — 4 segments, 77 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000017** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000017 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000017.step1.purchase.json` — 4 segments, 76 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000018** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000018 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000018.step1.purchase.json` — 4 segments, 58 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000019** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000019 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000019.step1.purchase.json` — 4 segments, 76 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000020** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000020 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000020.step1.purchase.json` — 5 segments, 75 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000021** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000021 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000021.step1.purchase.json` — 5 segments, 75 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000022** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000022 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000022.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000023** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000023 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000023.step1.purchase.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000024** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000024 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000024.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000025** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000025 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000025.json` — 4 segments, 30 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000026** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000026 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000026.json` — 4 segments, 30 elements (8 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000027** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000027 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000027.step1.purchase.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 032` (spec_derived, pNone)
  - **TC-000028** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000028 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000028.step1.purchase.json` — 4 segments, 58 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000029** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contact Chip — TC-000029 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000029.step1.purchase.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000030** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000030 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000030.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000031** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000031 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000031.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000032** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000032 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000032.step1.purchase.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000033** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000033 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000033.step1.purchase.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000034** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000034 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000034.step1.purchase.json` — 4 segments, 47 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000035** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000035 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000035.step1.purchase.json` — 4 segments, 76 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000036** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000036 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000036.step1.purchase.json` — 4 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000037** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000037 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000037.step1.purchase.json` — 5 segments, 46 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000038** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000038 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000038.step1.purchase.json` — 5 segments, 62 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000039** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000039 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000039.step1.purchase.json` — 5 segments, 75 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000040** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000040 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000040.step1.purchase.json` — 5 segments, 64 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000041** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000041 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000041.step1.purchase.json` — 5 segments, 46 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000042** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000042 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000042.step1.purchase.json` — 5 segments, 46 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000043** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000043 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000043.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000044** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000044 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000044.step1.purchase.json` — 4 segments, 66 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000045** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000045 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000045.step1.purchase.json` — 4 segments, 58 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000046** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000046 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000046.step1.purchase.json` — 4 segments, 77 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000047** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000047 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000047.step1.purchase.json` — 4 segments, 76 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000048** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000048 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000048.step1.purchase.json` — 4 segments, 58 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000049** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000049 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000049.step1.purchase.json` — 4 segments, 76 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000050** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000050 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000050.step1.purchase.json` — 5 segments, 75 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000051** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000051 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000051.step1.purchase.json` — 5 segments, 75 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000052** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000052 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000052.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000053** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000053 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000053.step1.purchase.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000054** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000054 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000054.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000055** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000055 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000055.json` — 4 segments, 30 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000056** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000056 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000056.json` — 4 segments, 30 elements (8 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000057** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000057 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000057.step1.purchase.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 032` (spec_derived, pNone)
  - **TC-000058** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000058 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000058.step1.purchase.json` — 4 segments, 58 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000059** — BUYPASS ATL105 Interlink Debit Purchase/Capture – EMV Contactless Chip — TC-000059 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000059.step1.purchase.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000119** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000119 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000119.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000120** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000120 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000120.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000121** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000121 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000121.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000122** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000122 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000122.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000123** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000123 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000123.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000124** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000124 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000124.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000125** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000125 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000125.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000126** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000126 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000126.step1.purchase.json` — 4 segments, 51 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000127** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000127 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000127.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000128** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000128 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000128.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000129** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000129 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000129.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000130** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000130 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000130.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000131** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000131 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000131.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000132** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000132 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000132.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000133** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000133 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000133.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000134** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000134 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000134.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000135** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000135 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000135.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000136** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000136 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000136.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000137** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000137 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000137.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000138** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000138 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000138.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000139** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000139 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000139.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000140** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000140 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000140.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000141** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000141 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000141.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000142** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000142 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000142.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000143** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000143 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000143.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000144** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000144 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000144.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000145** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000145 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000145.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000146** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000146 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000146.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
  - **TC-000147** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000147 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000147.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000148** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Chip Fallback — TC-000148 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000148.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-PURCHASE-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000060** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000060 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000060.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000061** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000061 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000061.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000062** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000062 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000062.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000063** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000063 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000063.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000064** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000064 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000064.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000065** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000065 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000065.step1.purchase.json` — 3 segments, 72 elements (20 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000066** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000066 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000066.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000067** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000067 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000067.step1.purchase.json` — 4 segments, 51 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000068** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000068 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000068.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000069** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000069 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000069.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000070** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000070 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000070.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000071** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000071 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000071.step1.purchase.json` — 4 segments, 35 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000072** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000072 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000072.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000073** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000073 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000073.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000074** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000074 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000074.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000075** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000075 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000075.step1.purchase.json` — 3 segments, 55 elements (16 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000076** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000076 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000076.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000077** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000077 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000077.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000078** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000078 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000078.step1.purchase.json` — 3 segments, 54 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000079** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000079 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000079.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000080** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000080 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000080.step1.purchase.json` — 4 segments, 53 elements (15 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000081** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000081 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000081.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000082** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000082 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000082.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000083** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000083 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000083.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000084** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000084 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000084.json` — 3 segments, 19 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000085** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000085 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000085.json` — 3 segments, 19 elements (5 provisional, 0 awaiting client value)
    - Key values: `100.CashAmount = 500` (spec_derived, pNone); `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (spec_derived, pNone); `100.PromptCode = 0011` (spec_derived, pNone)
  - **TC-000086** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000086 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000086.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone); `111.SegmentLength = 026` (spec_derived, pNone)
  - **TC-000087** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000087 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-000087 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000088** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000088 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000088.step1.purchase.json` — 3 segments, 36 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000089** — BUYPASS ATL105 Interlink Debit Purchase/Capture – Magnetic Stripe — TC-000089 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000089.step1.purchase.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000246** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe — TC-000246 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000246.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000247** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe — TC-000247 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000247.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000248** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe — TC-000248 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000248.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000249** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Magnetic Stripe — TC-000249 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000249.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-RETURN-CONTACTLESS_MOBILE** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000254** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device — TC-000254 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000254.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000255** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device — TC-000255 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000255.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000256** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device — TC-000256 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000256.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000257** — BUYPASS ATL105 Interlink Debit Merchandise Return – Contactless Mobile Commerce Device — TC-000257 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000257.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACT** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000234** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip — TC-000234 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000234.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000235** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip — TC-000235 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000235.step1.return.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000236** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip — TC-000236 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000236.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000237** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contact Chip — TC-000237 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000237.step1.return.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_CONTACTLESS** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000238** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip — TC-000238 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000238.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000239** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip — TC-000239 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000239.step1.return.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000240** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip — TC-000240 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000240.json` — 4 segments, 29 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000241** — BUYPASS ATL105 Interlink Debit Merchandise Return – EMV Contactless Chip — TC-000241 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000241.step1.return.json` — 4 segments, 48 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-RETURN-EMV_FALLBACK** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000250** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback — TC-000250 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000250.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000251** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback — TC-000251 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000251.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000252** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback — TC-000252 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000252.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000253** — BUYPASS ATL105 Interlink Debit Merchandise Return – Chip Fallback — TC-000253 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000253.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-DEBIT-INTERLINK-RETURN-MAGSTRIPE** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000242** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe — TC-000242 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000242.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000243** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe — TC-000243 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000243.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000244** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe — TC-000244 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000244.json` — 3 segments, 18 elements (6 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7011` (spec_derived, pNone); `100.SegmentLength = 058` (spec_derived, pNone)
  - **TC-000245** — BUYPASS ATL105 Interlink Debit Merchandise Return – Magnetic Stripe — TC-000245 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000245.step1.return.json` — 3 segments, 37 elements (11 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 058` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-000087`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
