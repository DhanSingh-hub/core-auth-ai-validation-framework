# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `mastercard-fleet-a26a6c0310` · Filter `mastercard-fleet` · Selection `{"network": ["MASTERCARD"], "platform": ["BUYPASS"], "program": ["FLEET"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-05T08:56:01.543513+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-05T08:56:02.324118+00:00

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
| Total scenarios / test cases | 24 / 307 |
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
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000991 |  | PASS | test_data/TD-TC-000991.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000992 |  | PASS | test_data/TD-TC-000992.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000993 |  | PASS | test_data/TD-TC-000993.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000994 |  | PASS | test_data/TD-TC-000994.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000995 |  | PASS | test_data/TD-TC-000995.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000996 |  | PASS | test_data/TD-TC-000996.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000997 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000998 |  | PASS | test_data/TD-TC-000998.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000999 |  | PASS | test_data/TD-TC-000999.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001028 |  | PASS | test_data/TD-TC-001028.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001029 |  | PASS | test_data/TD-TC-001029.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001030 |  | PASS | test_data/TD-TC-001030.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001031 |  | PASS | test_data/TD-TC-001031.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001032 |  | PASS | test_data/TD-TC-001032.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001033 |  | PASS | test_data/TD-TC-001033.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001034 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001035 |  | PASS | test_data/TD-TC-001035.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-001036 |  | PASS | test_data/TD-TC-001036.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001019 |  | PASS | test_data/TD-TC-001019.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001020 |  | PASS | test_data/TD-TC-001020.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001021 |  | PASS | test_data/TD-TC-001021.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001022 |  | PASS | test_data/TD-TC-001022.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001023 |  | PASS | test_data/TD-TC-001023.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001024 |  | PASS | test_data/TD-TC-001024.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001025 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001026 |  | PASS | test_data/TD-TC-001026.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-001027 |  | PASS | test_data/TD-TC-001027.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000963 |  | PASS | test_data/TD-TC-000963.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000964 |  | PASS | test_data/TD-TC-000964.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000965 |  | PASS | test_data/TD-TC-000965.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000966 |  | PASS | test_data/TD-TC-000966.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000967 |  | PASS | test_data/TD-TC-000967.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000968 |  | PASS | test_data/TD-TC-000968.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000969 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000970 |  | PASS | test_data/TD-TC-000970.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-000971 |  | PASS | test_data/TD-TC-000971.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000972 |  | PASS | test_data/TD-TC-000972.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000973 |  | PASS | test_data/TD-TC-000973.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000974 |  | PASS | test_data/TD-TC-000974.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000975 |  | PASS | test_data/TD-TC-000975.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000976 |  | PASS | test_data/TD-TC-000976.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000977 |  | PASS | test_data/TD-TC-000977.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000978 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000979 |  | PASS | test_data/TD-TC-000979.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-000980 |  | PASS | test_data/TD-TC-000980.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001010 |  | PASS | test_data/TD-TC-001010.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001011 |  | PASS | test_data/TD-TC-001011.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001012 |  | PASS | test_data/TD-TC-001012.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001013 |  | PASS | test_data/TD-TC-001013.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001014 |  | PASS | test_data/TD-TC-001014.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001015 |  | PASS | test_data/TD-TC-001015.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001016 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001017 |  | PASS | test_data/TD-TC-001017.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-001018 |  | PASS | test_data/TD-TC-001018.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000981 |  | PASS | test_data/TD-TC-000981.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000982 |  | PASS | test_data/TD-TC-000982.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000983 |  | PASS | test_data/TD-TC-000983.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000984 |  | PASS | test_data/TD-TC-000984.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000985 |  | PASS | test_data/TD-TC-000985.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000986 |  | PASS | test_data/TD-TC-000986.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000987 |  | PASS | test_data/TD-TC-000987.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000988 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000989 |  | PASS | test_data/TD-TC-000989.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-000990 |  | PASS | test_data/TD-TC-000990.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001000 |  | PASS | test_data/TD-TC-001000.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001001 |  | PASS | test_data/TD-TC-001001.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001002 |  | PASS | test_data/TD-TC-001002.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001003 |  | PASS | test_data/TD-TC-001003.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001004 |  | PASS | test_data/TD-TC-001004.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001005 |  | PASS | test_data/TD-TC-001005.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001006 |  | PASS | test_data/TD-TC-001006.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001007 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001008 |  | PASS | test_data/TD-TC-001008.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-001009 |  | PASS | test_data/TD-TC-001009.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000831 |  | PASS | test_data/TD-TC-000831.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000832 |  | PASS | test_data/TD-TC-000832.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000833 |  | PASS | test_data/TD-TC-000833.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000834 |  | PASS | test_data/TD-TC-000834.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000835 |  | PASS | test_data/TD-TC-000835.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000836 |  | PASS | test_data/TD-TC-000836.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000837 |  | PASS | test_data/TD-TC-000837.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000838 |  | PASS | test_data/TD-TC-000838.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000839 |  | PASS | test_data/TD-TC-000839.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000840 |  | PASS | test_data/TD-TC-000840.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000841 |  | PASS | test_data/TD-TC-000841.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000842 |  | PASS | test_data/TD-TC-000842.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000843 |  | PASS | test_data/TD-TC-000843.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000844 |  | PASS | test_data/TD-TC-000844.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000845 |  | PASS | test_data/TD-TC-000845.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000846 |  | PASS | test_data/TD-TC-000846.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000847 |  | PASS | test_data/TD-TC-000847.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000848 |  | PASS | test_data/TD-TC-000848.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000849 |  | PASS | test_data/TD-TC-000849.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000850 |  | PASS | test_data/TD-TC-000850.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000851 |  | PASS | test_data/TD-TC-000851.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000852 |  | PASS | test_data/TD-TC-000852.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000853 |  | PASS | test_data/TD-TC-000853.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000854 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000855 |  | PASS | test_data/TD-TC-000855.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-000856 |  | PASS | test_data/TD-TC-000856.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000937 |  | PASS | test_data/TD-TC-000937.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000938 |  | PASS | test_data/TD-TC-000938.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000939 |  | PASS | test_data/TD-TC-000939.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000940 |  | PASS | test_data/TD-TC-000940.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000941 |  | PASS | test_data/TD-TC-000941.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000942 |  | PASS | test_data/TD-TC-000942.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000943 |  | PASS | test_data/TD-TC-000943.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000944 |  | PASS | test_data/TD-TC-000944.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000945 |  | PASS | test_data/TD-TC-000945.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000946 |  | PASS | test_data/TD-TC-000946.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000947 |  | PASS | test_data/TD-TC-000947.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000948 |  | PASS | test_data/TD-TC-000948.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000949 |  | PASS | test_data/TD-TC-000949.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000950 |  | PASS | test_data/TD-TC-000950.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000951 |  | PASS | test_data/TD-TC-000951.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000952 |  | PASS | test_data/TD-TC-000952.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000953 |  | PASS | test_data/TD-TC-000953.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000954 |  | PASS | test_data/TD-TC-000954.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000955 |  | PASS | test_data/TD-TC-000955.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000956 |  | PASS | test_data/TD-TC-000956.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000957 |  | PASS | test_data/TD-TC-000957.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000958 |  | PASS | test_data/TD-TC-000958.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000959 |  | PASS | test_data/TD-TC-000959.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000960 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000961 |  | PASS | test_data/TD-TC-000961.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-000962 |  | PASS | test_data/TD-TC-000962.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000911 |  | PASS | test_data/TD-TC-000911.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000912 |  | PASS | test_data/TD-TC-000912.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000913 |  | PASS | test_data/TD-TC-000913.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000914 |  | PASS | test_data/TD-TC-000914.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000915 |  | PASS | test_data/TD-TC-000915.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000916 |  | PASS | test_data/TD-TC-000916.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000917 |  | PASS | test_data/TD-TC-000917.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000918 |  | PASS | test_data/TD-TC-000918.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000919 |  | PASS | test_data/TD-TC-000919.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000920 |  | PASS | test_data/TD-TC-000920.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000921 |  | PASS | test_data/TD-TC-000921.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000922 |  | PASS | test_data/TD-TC-000922.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000923 |  | PASS | test_data/TD-TC-000923.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000924 |  | PASS | test_data/TD-TC-000924.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000925 |  | PASS | test_data/TD-TC-000925.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000926 |  | PASS | test_data/TD-TC-000926.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000927 |  | PASS | test_data/TD-TC-000927.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000928 |  | PASS | test_data/TD-TC-000928.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000929 |  | PASS | test_data/TD-TC-000929.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000930 |  | PASS | test_data/TD-TC-000930.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000931 |  | PASS | test_data/TD-TC-000931.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000932 |  | PASS | test_data/TD-TC-000932.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000933 |  | PASS | test_data/TD-TC-000933.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000934 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000935 |  | PASS | test_data/TD-TC-000935.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-000936 |  | PASS | test_data/TD-TC-000936.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000746 |  | PASS | test_data/TD-TC-000746.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000747 |  | PASS | test_data/TD-TC-000747.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000748 |  | PASS | test_data/TD-TC-000748.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000749 |  | PASS | test_data/TD-TC-000749.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000750 |  | PASS | test_data/TD-TC-000750.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000751 |  | PASS | test_data/TD-TC-000751.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000752 |  | PASS | test_data/TD-TC-000752.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000753 |  | PASS | test_data/TD-TC-000753.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000754 |  | PASS | test_data/TD-TC-000754.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000755 |  | PASS | test_data/TD-TC-000755.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000756 |  | PASS | test_data/TD-TC-000756.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000757 |  | PASS | test_data/TD-TC-000757.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000758 |  | PASS | test_data/TD-TC-000758.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000759 |  | PASS | test_data/TD-TC-000759.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000760 |  | PASS | test_data/TD-TC-000760.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000761 |  | PASS | test_data/TD-TC-000761.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000762 |  | PASS | test_data/TD-TC-000762.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000763 |  | PASS | test_data/TD-TC-000763.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000764 |  | PASS | test_data/TD-TC-000764.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000765 |  | PASS | test_data/TD-TC-000765.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000766 |  | PASS | test_data/TD-TC-000766.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000767 |  | PASS | test_data/TD-TC-000767.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000768 |  | PASS | test_data/TD-TC-000768.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000769 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000770 |  | PASS | test_data/TD-TC-000770.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-000771 |  | PASS | test_data/TD-TC-000771.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000772 |  | PASS | test_data/TD-TC-000772.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000773 |  | PASS | test_data/TD-TC-000773.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000774 |  | PASS | test_data/TD-TC-000774.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000775 |  | PASS | test_data/TD-TC-000775.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000776 |  | PASS | test_data/TD-TC-000776.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000777 |  | PASS | test_data/TD-TC-000777.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000778 |  | PASS | test_data/TD-TC-000778.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000779 |  | PASS | test_data/TD-TC-000779.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000780 |  | PASS | test_data/TD-TC-000780.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000781 |  | PASS | test_data/TD-TC-000781.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000782 |  | PASS | test_data/TD-TC-000782.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000783 |  | PASS | test_data/TD-TC-000783.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000784 |  | PASS | test_data/TD-TC-000784.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000785 |  | PASS | test_data/TD-TC-000785.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000786 |  | PASS | test_data/TD-TC-000786.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000787 |  | PASS | test_data/TD-TC-000787.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000788 |  | PASS | test_data/TD-TC-000788.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000789 |  | PASS | test_data/TD-TC-000789.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000790 |  | PASS | test_data/TD-TC-000790.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000791 |  | PASS | test_data/TD-TC-000791.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000792 |  | PASS | test_data/TD-TC-000792.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000793 |  | PASS | test_data/TD-TC-000793.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000794 |  | PASS | test_data/TD-TC-000794.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000795 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000796 |  | PASS | test_data/TD-TC-000796.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-000797 |  | PASS | test_data/TD-TC-000797.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000885 |  | PASS | test_data/TD-TC-000885.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000886 |  | PASS | test_data/TD-TC-000886.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000887 |  | PASS | test_data/TD-TC-000887.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000888 |  | PASS | test_data/TD-TC-000888.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000889 |  | PASS | test_data/TD-TC-000889.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000890 |  | PASS | test_data/TD-TC-000890.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000891 |  | PASS | test_data/TD-TC-000891.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000892 |  | PASS | test_data/TD-TC-000892.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000893 |  | PASS | test_data/TD-TC-000893.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000894 |  | PASS | test_data/TD-TC-000894.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000895 |  | PASS | test_data/TD-TC-000895.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000896 |  | PASS | test_data/TD-TC-000896.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000897 |  | PASS | test_data/TD-TC-000897.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000898 |  | PASS | test_data/TD-TC-000898.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000899 |  | PASS | test_data/TD-TC-000899.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000900 |  | PASS | test_data/TD-TC-000900.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000901 |  | PASS | test_data/TD-TC-000901.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000902 |  | PASS | test_data/TD-TC-000902.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000903 |  | PASS | test_data/TD-TC-000903.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000904 |  | PASS | test_data/TD-TC-000904.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000905 |  | PASS | test_data/TD-TC-000905.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000906 |  | PASS | test_data/TD-TC-000906.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000907 |  | PASS | test_data/TD-TC-000907.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000908 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000909 |  | PASS | test_data/TD-TC-000909.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-000910 |  | PASS | test_data/TD-TC-000910.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000798 |  | PASS | test_data/TD-TC-000798.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000799 |  | PASS | test_data/TD-TC-000799.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000800 |  | PASS | test_data/TD-TC-000800.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000801 |  | PASS | test_data/TD-TC-000801.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000802 |  | PASS | test_data/TD-TC-000802.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000803 |  | PASS | test_data/TD-TC-000803.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000804 |  | PASS | test_data/TD-TC-000804.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000805 |  | PASS | test_data/TD-TC-000805.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000806 |  | PASS | test_data/TD-TC-000806.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000807 |  | PASS | test_data/TD-TC-000807.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000808 |  | PASS | test_data/TD-TC-000808.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000809 |  | PASS | test_data/TD-TC-000809.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000810 |  | PASS | test_data/TD-TC-000810.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000811 |  | PASS | test_data/TD-TC-000811.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000812 |  | PASS | test_data/TD-TC-000812.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000813 |  | PASS | test_data/TD-TC-000813.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000814 |  | PASS | test_data/TD-TC-000814.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000815 |  | PASS | test_data/TD-TC-000815.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000816 |  | PASS | test_data/TD-TC-000816.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000817 |  | PASS | test_data/TD-TC-000817.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000818 |  | PASS | test_data/TD-TC-000818.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000819 |  | PASS | test_data/TD-TC-000819.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000820 |  | PASS | test_data/TD-TC-000820.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000821 |  | PASS | test_data/TD-TC-000821.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000822 |  | PASS | test_data/TD-TC-000822.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000823 |  | PASS | test_data/TD-TC-000823.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000824 |  | PASS | test_data/TD-TC-000824.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000825 |  | PASS | test_data/TD-TC-000825.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000826 |  | PASS | test_data/TD-TC-000826.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000827 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000828 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000829 |  | PASS | test_data/TD-TC-000829.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-000830 |  | PASS | test_data/TD-TC-000830.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000857 |  | PASS | test_data/TD-TC-000857.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000858 |  | PASS | test_data/TD-TC-000858.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000859 |  | PASS | test_data/TD-TC-000859.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000860 |  | PASS | test_data/TD-TC-000860.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000861 |  | PASS | test_data/TD-TC-000861.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000862 |  | PASS | test_data/TD-TC-000862.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000863 |  | PASS | test_data/TD-TC-000863.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000864 |  | PASS | test_data/TD-TC-000864.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000865 |  | PASS | test_data/TD-TC-000865.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000866 |  | PASS | test_data/TD-TC-000866.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000867 |  | PASS | test_data/TD-TC-000867.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000868 |  | PASS | test_data/TD-TC-000868.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000869 |  | PASS | test_data/TD-TC-000869.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000870 |  | PASS | test_data/TD-TC-000870.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000871 |  | PASS | test_data/TD-TC-000871.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000872 |  | PASS | test_data/TD-TC-000872.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000873 |  | PASS | test_data/TD-TC-000873.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000874 |  | PASS | test_data/TD-TC-000874.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000875 |  | PASS | test_data/TD-TC-000875.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000876 |  | PASS | test_data/TD-TC-000876.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000877 |  | PASS | test_data/TD-TC-000877.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000878 |  | PASS | test_data/TD-TC-000878.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000879 |  | PASS | test_data/TD-TC-000879.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000880 |  | PASS | test_data/TD-TC-000880.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000881 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000882 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000883 |  | PASS | test_data/TD-TC-000883.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-000884 |  | PASS | test_data/TD-TC-000884.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-001043 |  | PASS | test_data/TD-TC-001043.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-001044 |  | PASS | test_data/TD-TC-001044.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-001051 |  | PASS | test_data/TD-TC-001051.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-001052 |  | PASS | test_data/TD-TC-001052.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CREDENTIAL_ON_FILE | business_scenario | request | TC-001049 |  | PASS | test_data/TD-TC-001049.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CREDENTIAL_ON_FILE | business_scenario | request | TC-001050 |  | PASS | test_data/TD-TC-001050.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACT | business_scenario | request | TC-001037 |  | PASS | test_data/TD-TC-001037.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACT | business_scenario | request | TC-001038 |  | PASS | test_data/TD-TC-001038.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-001039 |  | PASS | test_data/TD-TC-001039.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-001040 |  | PASS | test_data/TD-TC-001040.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_FALLBACK | business_scenario | request | TC-001047 |  | PASS | test_data/TD-TC-001047.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_FALLBACK | business_scenario | request | TC-001048 |  | PASS | test_data/TD-TC-001048.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MAGSTRIPE | business_scenario | request | TC-001041 |  | PASS | test_data/TD-TC-001041.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MAGSTRIPE | business_scenario | request | TC-001042 |  | PASS | test_data/TD-TC-001042.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MANUAL | business_scenario | request | TC-001045 |  | PASS | test_data/TD-TC-001045.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MANUAL | business_scenario | request | TC-001046 |  | PASS | test_data/TD-TC-001046.json | FULLY_TRACED | pending_review / pending_review / pending_review |

## 5. Requirement detail

### BR-A47DDF5D — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 MasterCard Fleet
Segment `UNMAPPED` · Source page 522 · Derivation `business_requirement`

- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000991** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000991 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000991.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000992** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000992 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000992.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000993** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000993 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000993.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000994** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000994 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000994.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000995** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000995 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000995.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000996** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000996 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000996.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000997** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000997 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-000997 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000998** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000998 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000998.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000999** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-000999 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000999.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001028** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001028 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001028.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-001029** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001029 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001029.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001030** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001030 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001030.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001031** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001031 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001031.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001032** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001032 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001032.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001033** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001033 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001033.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001034** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001034 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-001034 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001035** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001035 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001035.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-001036** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-001036 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001036.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001019** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001019 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001019.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001020** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001020 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001020.step1.auth-only.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001021** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001021 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001021.step1.auth-only.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001022** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001022 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001022.step1.auth-only.json` — 5 segments, 170 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001023** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001023 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001023.step1.auth-only.json` — 6 segments, 102 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001024** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001024 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001024.step1.auth-only.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001025** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001025 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-001025 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001026** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001026 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001026.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001027** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-001027 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001027.step1.auth-only.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000963** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000963 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000963.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000964** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000964 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000964.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000965** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000965 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000965.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000966** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000966 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000966.step1.auth-only.json` — 6 segments, 145 elements (41 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000967** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000967 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000967.step1.auth-only.json` — 7 segments, 95 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000968** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000968 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000968.step1.auth-only.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000969** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000969 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000969 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000970** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000970 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000970.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000971** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-000971 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000971.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000972** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000972 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000972.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000973** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000973 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000973.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000974** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000974 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000974.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000975** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000975 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000975.step1.auth-only.json` — 6 segments, 145 elements (41 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000976** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000976 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000976.step1.auth-only.json` — 7 segments, 95 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000977** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000977 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000977.step1.auth-only.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000978** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000978 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000978 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000979** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000979 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000979.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000980** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-000980 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000980.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001010** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001010 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001010.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001011** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001011 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001011.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001012** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001012 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001012.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001013** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001013 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001013.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001014** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001014 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001014.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001015** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001015 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001015.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001016** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001016 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-001016 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001017** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001017 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001017.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001018** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-001018 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001018.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000981** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000981 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000981.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000982** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000982 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000982.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000983** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000983 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000983.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000984** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000984 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000984.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000985** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000985 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000985.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000986** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000986 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000986.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000987** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000987 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000987.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 122` (spec_derived, pNone)
  - **TC-000988** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000988 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000988 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000989** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000989 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000989.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000990** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-000990 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000990.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001000** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001000 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001000.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001001** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001001 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001001.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001002** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001002 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001002.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001003** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001003 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001003.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001004** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001004 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001004.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001005** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001005 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001005.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-001006** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001006 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001006.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001007** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001007 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-001007 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-001008** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001008 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001008.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001009** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-001009 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001009.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000831** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000831 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000831.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000832** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000832 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000832.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000833** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000833 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000833.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000834** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000834 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000834.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000835** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000835 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000835.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000836** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000836 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000836.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000837** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000837 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000837.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000838** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000838 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000838.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000839** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000839 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000839.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000840** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000840 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000840.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000841** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000841 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000841.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000842** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000842 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000842.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000843** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000843 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000843.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000844** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000844 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000844.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000845** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000845 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000845.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000846** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000846 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000846.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000847** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000847 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000847.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000848** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000848 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000848.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000849** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000849 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000849.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000850** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000850 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000850.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000851** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000851 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000851.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000852** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000852 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000852.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000853** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000853 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000853.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000854** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000854 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-000854 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000855** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000855 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000855.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000856** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-000856 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000856.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000937** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000937 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000937.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000938** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000938 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000938.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000939** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000939 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000939.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000940** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000940 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000940.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000941** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000941 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000941.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000942** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000942 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000942.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000943** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000943 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000943.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000944** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000944 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000944.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000945** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000945 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000945.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000946** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000946 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000946.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000947** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000947 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000947.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000948** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000948 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000948.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000949** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000949 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000949.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000950** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000950 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000950.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000951** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000951 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000951.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000952** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000952 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000952.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000953** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000953 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000953.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000954** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000954 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000954.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000955** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000955 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000955.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000956** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000956 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000956.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000957** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000957 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000957.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000958** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000958 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000958.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000959** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000959 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000959.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000960** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000960 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-000960 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000961** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000961 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000961.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000962** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-000962 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000962.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000911** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000911 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000911.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000912** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000912 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000912.step1.purchase.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000913** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000913 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000913.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000914** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000914 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000914.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000915** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000915 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000915.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000916** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000916 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000916.step1.purchase.json` — 5 segments, 126 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000917** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000917 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000917.step1.purchase.json` — 5 segments, 168 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000918** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000918 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000918.step1.purchase.json` — 6 segments, 59 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000919** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000919 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000919.step1.purchase.json` — 6 segments, 99 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000920** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000920 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000920.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000921** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000921 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000921.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000922** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000922 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000922.step1.purchase.json` — 6 segments, 59 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000923** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000923 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000923.step1.purchase.json` — 6 segments, 59 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000924** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000924 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000924.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000925** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000925 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000925.step1.purchase.json` — 5 segments, 127 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000926** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000926 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000926.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000927** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000927 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000927.step1.purchase.json` — 5 segments, 127 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000928** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000928 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000928.step1.purchase.json` — 5 segments, 126 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000929** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000929 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000929.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000930** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000930 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000930.step1.purchase.json` — 5 segments, 126 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000931** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000931 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000931.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000932** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000932 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000932.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000933** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000933 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000933.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000934** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000934 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-000934 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000935** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000935 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000935.step1.purchase.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000936** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-000936 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000936.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000746** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000746 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000746.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000747** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000747 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000747.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000748** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000748 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000748.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000749** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000749 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000749.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000750** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000750 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000750.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000751** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000751 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000751.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000752** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000752 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000752.step1.purchase.json` — 6 segments, 143 elements (39 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000753** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000753 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000753.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000754** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000754 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000754.step1.purchase.json` — 7 segments, 92 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000755** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000755 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000755.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000756** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000756 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000756.step1.purchase.json` — 7 segments, 94 elements (26 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000757** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000757 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000757.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000758** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000758 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000758.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000759** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000759 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000759.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000760** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000760 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000760.step1.purchase.json` — 6 segments, 111 elements (31 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000761** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000761 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000761.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000762** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000762 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000762.step1.purchase.json` — 6 segments, 122 elements (34 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000763** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000763 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000763.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000764** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000764 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000764.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000765** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000765 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000765.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000766** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000766 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000766.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000767** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000767 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000767.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000768** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000768 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000768.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000769** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000769 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000769 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000770** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000770 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000770.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000771** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-000771 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000771.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000772** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000772 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000772.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000773** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000773 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000773.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000774** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000774 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000774.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000775** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000775 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000775.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000776** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000776 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000776.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000777** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000777 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000777.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000778** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000778 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000778.step1.purchase.json` — 6 segments, 143 elements (39 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000779** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000779 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000779.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000780** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000780 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000780.step1.purchase.json` — 7 segments, 92 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000781** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000781 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000781.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000782** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000782 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000782.step1.purchase.json` — 7 segments, 94 elements (26 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000783** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000783 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000783.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000784** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000784 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000784.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000785** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000785 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000785.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000786** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000786 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000786.step1.purchase.json` — 6 segments, 111 elements (31 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000787** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000787 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000787.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000788** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000788 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000788.step1.purchase.json` — 6 segments, 122 elements (34 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000789** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000789 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000789.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000790** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000790 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000790.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000791** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000791 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000791.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000792** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000792 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000792.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000793** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000793 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000793.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000794** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000794 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000794.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000795** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000795 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000795 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000796** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000796 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000796.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000797** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-000797 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000797.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000885** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000885 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000885.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000886** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000886 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000886.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000887** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000887 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000887.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000888** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000888 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000888.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000889** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000889 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000889.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000890** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000890 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000890.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000891** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000891 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000891.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000892** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000892 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000892.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000893** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000893 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000893.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000894** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000894 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000894.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000895** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000895 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000895.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000896** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000896 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000896.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000897** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000897 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000897.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000898** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000898 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000898.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000899** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000899 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000899.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000900** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000900 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000900.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000901** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000901 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000901.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000902** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000902 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000902.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000903** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000903 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000903.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000904** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000904 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000904.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000905** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000905 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000905.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000906** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000906 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000906.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000907** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000907 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000907.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000908** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000908 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-000908 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000909** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000909 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000909.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000910** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-000910 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-000910.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000798** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000798 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000798.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000799** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000799 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000799.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000800** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000800 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000800.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000801** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000801 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000801.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000802** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000802 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000802.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000803** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000803 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000803.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000804** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000804 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000804.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000805** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000805 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000805.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000806** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000806 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000806.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000807** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000807 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000807.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000808** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000808 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000808.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000809** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000809 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000809.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000810** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000810 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000810.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000811** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000811 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000811.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000812** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000812 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000812.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000813** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000813 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000813.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000814** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000814 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000814.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000815** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000815 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000815.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000816** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000816 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000816.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000817** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000817 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000817.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000818** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000818 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000818.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000819** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000819 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000819.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000820** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000820 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000820.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000821** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000821 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000821.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000822** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000822 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000822.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000823** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000823 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000823.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000824** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000824 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000824.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000825** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000825 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000825.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-000826** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000826 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000826.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 122` (spec_derived, pNone)
  - **TC-000827** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000827 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000827 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000828** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000828 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000828 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000829** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000829 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000829.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000830** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-000830 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000830.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-000857** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000857 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000857.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000858** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000858 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000858.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000859** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000859 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000859.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000860** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000860 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000860.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000861** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000861 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000861.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000862** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000862 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000862.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000863** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000863 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000863.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000864** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000864 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000864.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000865** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000865 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000865.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000866** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000866 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000866.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000867** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000867 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000867.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000868** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000868 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000868.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000869** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000869 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000869.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000870** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000870 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000870.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000871** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000871 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000871.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000872** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000872 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000872.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000873** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000873 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000873.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000874** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000874 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000874.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000875** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000875 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000875.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000876** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000876 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000876.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000877** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000877 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000877.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000878** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000878 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000878.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000879** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000879 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000879.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000880** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000880 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000880.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-000881** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000881 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000881 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000882** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000882 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-000882 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-000883** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000883 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000883.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-000884** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-000884 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-000884.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001043** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe — TC-001043 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001043.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-001044** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe — TC-001044 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001044.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MOBILE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001051** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device — TC-001051 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001051.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-001052** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device — TC-001052 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001052.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-CREDENTIAL_ON_FILE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001049** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File — TC-001049 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001049.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001050** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File — TC-001050 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001050.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACT** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001037** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip — TC-001037 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001037.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-001038** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip — TC-001038 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001038.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACTLESS** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001039** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip — TC-001039 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001039.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-001040** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip — TC-001040 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001040.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_FALLBACK** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001047** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback — TC-001047 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001047.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001048** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback — TC-001048 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-001048.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001041** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe — TC-001041 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001041.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-001042** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe — TC-001042 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001042.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-MANUAL** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-001045** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed — TC-001045 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001045.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-001046** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed — TC-001046 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-001046.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-000769`
- `TC-000795`
- `TC-000827`
- `TC-000828`
- `TC-000854`
- `TC-000881`
- `TC-000882`
- `TC-000908`
- `TC-000934`
- `TC-000960`
- `TC-000969`
- `TC-000978`
- `TC-000988`
- `TC-000997`
- `TC-001007`
- `TC-001016`
- `TC-001025`
- `TC-001034`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
