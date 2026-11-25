# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `mastercard-fleet-f8e91ab05e` · Filter `mastercard-fleet` · Selection `{"network": ["MASTERCARD"], "platform": ["BUYPASS"], "program": ["FLEET"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-05T14:28:56.371653+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-05T14:28:57.257805+00:00

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
| Total scenarios / test cases | 24 / 339 |
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
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003701 |  | PASS | test_data/TD-TC-003701.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003702 |  | PASS | test_data/TD-TC-003702.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003703 |  | PASS | test_data/TD-TC-003703.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003704 |  | PASS | test_data/TD-TC-003704.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003705 |  | PASS | test_data/TD-TC-003705.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003706 |  | PASS | test_data/TD-TC-003706.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003707 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003708 |  | PASS | test_data/TD-TC-003708.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003709 |  | PASS | test_data/TD-TC-003709.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003710 |  | PASS | test_data/TD-TC-003710.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003742 |  | PASS | test_data/TD-TC-003742.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003743 |  | PASS | test_data/TD-TC-003743.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003744 |  | PASS | test_data/TD-TC-003744.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003745 |  | PASS | test_data/TD-TC-003745.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003746 |  | PASS | test_data/TD-TC-003746.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003747 |  | PASS | test_data/TD-TC-003747.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003748 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003749 |  | PASS | test_data/TD-TC-003749.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003750 |  | PASS | test_data/TD-TC-003750.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE | business_scenario | request | TC-003751 |  | PASS | test_data/TD-TC-003751.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003732 |  | PASS | test_data/TD-TC-003732.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003733 |  | PASS | test_data/TD-TC-003733.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003734 |  | PASS | test_data/TD-TC-003734.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003735 |  | PASS | test_data/TD-TC-003735.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003736 |  | PASS | test_data/TD-TC-003736.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003737 |  | PASS | test_data/TD-TC-003737.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003738 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003739 |  | PASS | test_data/TD-TC-003739.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003740 |  | PASS | test_data/TD-TC-003740.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE | business_scenario | request | TC-003741 |  | PASS | test_data/TD-TC-003741.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003670 |  | PASS | test_data/TD-TC-003670.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003671 |  | PASS | test_data/TD-TC-003671.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003672 |  | PASS | test_data/TD-TC-003672.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003673 |  | PASS | test_data/TD-TC-003673.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003674 |  | PASS | test_data/TD-TC-003674.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003675 |  | PASS | test_data/TD-TC-003675.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003676 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003677 |  | PASS | test_data/TD-TC-003677.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003678 |  | PASS | test_data/TD-TC-003678.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT | business_scenario | request | TC-003679 |  | PASS | test_data/TD-TC-003679.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003680 |  | PASS | test_data/TD-TC-003680.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003681 |  | PASS | test_data/TD-TC-003681.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003682 |  | PASS | test_data/TD-TC-003682.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003683 |  | PASS | test_data/TD-TC-003683.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003684 |  | PASS | test_data/TD-TC-003684.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003685 |  | PASS | test_data/TD-TC-003685.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003686 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003687 |  | PASS | test_data/TD-TC-003687.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003688 |  | PASS | test_data/TD-TC-003688.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS | business_scenario | request | TC-003689 |  | PASS | test_data/TD-TC-003689.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003722 |  | PASS | test_data/TD-TC-003722.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003723 |  | PASS | test_data/TD-TC-003723.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003724 |  | PASS | test_data/TD-TC-003724.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003725 |  | PASS | test_data/TD-TC-003725.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003726 |  | PASS | test_data/TD-TC-003726.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003727 |  | PASS | test_data/TD-TC-003727.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003728 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003729 |  | PASS | test_data/TD-TC-003729.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003730 |  | PASS | test_data/TD-TC-003730.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK | business_scenario | request | TC-003731 |  | PASS | test_data/TD-TC-003731.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003690 |  | PASS | test_data/TD-TC-003690.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003691 |  | PASS | test_data/TD-TC-003691.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003692 |  | PASS | test_data/TD-TC-003692.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003693 |  | PASS | test_data/TD-TC-003693.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003694 |  | PASS | test_data/TD-TC-003694.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003695 |  | PASS | test_data/TD-TC-003695.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003696 |  | PASS | test_data/TD-TC-003696.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003697 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003698 |  | PASS | test_data/TD-TC-003698.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003699 |  | PASS | test_data/TD-TC-003699.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE | business_scenario | request | TC-003700 |  | PASS | test_data/TD-TC-003700.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003711 |  | PASS | test_data/TD-TC-003711.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003712 |  | PASS | test_data/TD-TC-003712.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003713 |  | PASS | test_data/TD-TC-003713.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003714 |  | PASS | test_data/TD-TC-003714.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003715 |  | PASS | test_data/TD-TC-003715.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003716 |  | PASS | test_data/TD-TC-003716.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003717 |  | PASS | test_data/TD-TC-003717.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003718 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003719 |  | PASS | test_data/TD-TC-003719.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003720 |  | PASS | test_data/TD-TC-003720.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL | business_scenario | request | TC-003721 |  | PASS | test_data/TD-TC-003721.step1.auth-only.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003523 |  | PASS | test_data/TD-TC-003523.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003524 |  | PASS | test_data/TD-TC-003524.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003525 |  | PASS | test_data/TD-TC-003525.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003526 |  | PASS | test_data/TD-TC-003526.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003527 |  | PASS | test_data/TD-TC-003527.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003528 |  | PASS | test_data/TD-TC-003528.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003529 |  | PASS | test_data/TD-TC-003529.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003530 |  | PASS | test_data/TD-TC-003530.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003531 |  | PASS | test_data/TD-TC-003531.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003532 |  | PASS | test_data/TD-TC-003532.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003533 |  | PASS | test_data/TD-TC-003533.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003534 |  | PASS | test_data/TD-TC-003534.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003535 |  | PASS | test_data/TD-TC-003535.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003536 |  | PASS | test_data/TD-TC-003536.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003537 |  | PASS | test_data/TD-TC-003537.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003538 |  | PASS | test_data/TD-TC-003538.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003539 |  | PASS | test_data/TD-TC-003539.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003540 |  | PASS | test_data/TD-TC-003540.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003541 |  | PASS | test_data/TD-TC-003541.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003542 |  | PASS | test_data/TD-TC-003542.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003543 |  | PASS | test_data/TD-TC-003543.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003544 |  | PASS | test_data/TD-TC-003544.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003545 |  | PASS | test_data/TD-TC-003545.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003546 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003547 |  | PASS | test_data/TD-TC-003547.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003548 |  | PASS | test_data/TD-TC-003548.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003549 |  | PASS | test_data/TD-TC-003549.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003550 |  | PASS | test_data/TD-TC-003550.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003551 |  | PASS | test_data/TD-TC-003551.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003641 |  | PASS | test_data/TD-TC-003641.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003642 |  | PASS | test_data/TD-TC-003642.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003643 |  | PASS | test_data/TD-TC-003643.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003644 |  | PASS | test_data/TD-TC-003644.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003645 |  | PASS | test_data/TD-TC-003645.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003646 |  | PASS | test_data/TD-TC-003646.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003647 |  | PASS | test_data/TD-TC-003647.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003648 |  | PASS | test_data/TD-TC-003648.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003649 |  | PASS | test_data/TD-TC-003649.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003650 |  | PASS | test_data/TD-TC-003650.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003651 |  | PASS | test_data/TD-TC-003651.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003652 |  | PASS | test_data/TD-TC-003652.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003653 |  | PASS | test_data/TD-TC-003653.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003654 |  | PASS | test_data/TD-TC-003654.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003655 |  | PASS | test_data/TD-TC-003655.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003656 |  | PASS | test_data/TD-TC-003656.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003657 |  | PASS | test_data/TD-TC-003657.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003658 |  | PASS | test_data/TD-TC-003658.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003659 |  | PASS | test_data/TD-TC-003659.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003660 |  | PASS | test_data/TD-TC-003660.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003661 |  | PASS | test_data/TD-TC-003661.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003662 |  | PASS | test_data/TD-TC-003662.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003663 |  | PASS | test_data/TD-TC-003663.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003664 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003665 |  | PASS | test_data/TD-TC-003665.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003666 |  | PASS | test_data/TD-TC-003666.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003667 |  | PASS | test_data/TD-TC-003667.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003668 |  | PASS | test_data/TD-TC-003668.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE | business_scenario | request | TC-003669 |  | PASS | test_data/TD-TC-003669.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003612 |  | PASS | test_data/TD-TC-003612.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003613 |  | PASS | test_data/TD-TC-003613.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003614 |  | PASS | test_data/TD-TC-003614.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003615 |  | PASS | test_data/TD-TC-003615.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003616 |  | PASS | test_data/TD-TC-003616.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003617 |  | PASS | test_data/TD-TC-003617.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003618 |  | PASS | test_data/TD-TC-003618.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003619 |  | PASS | test_data/TD-TC-003619.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003620 |  | PASS | test_data/TD-TC-003620.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003621 |  | PASS | test_data/TD-TC-003621.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003622 |  | PASS | test_data/TD-TC-003622.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003623 |  | PASS | test_data/TD-TC-003623.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003624 |  | PASS | test_data/TD-TC-003624.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003625 |  | PASS | test_data/TD-TC-003625.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003626 |  | PASS | test_data/TD-TC-003626.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003627 |  | PASS | test_data/TD-TC-003627.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003628 |  | PASS | test_data/TD-TC-003628.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003629 |  | PASS | test_data/TD-TC-003629.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003630 |  | PASS | test_data/TD-TC-003630.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003631 |  | PASS | test_data/TD-TC-003631.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003632 |  | PASS | test_data/TD-TC-003632.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003633 |  | PASS | test_data/TD-TC-003633.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003634 |  | PASS | test_data/TD-TC-003634.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003635 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003636 |  | PASS | test_data/TD-TC-003636.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003637 |  | PASS | test_data/TD-TC-003637.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003638 |  | PASS | test_data/TD-TC-003638.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003639 |  | PASS | test_data/TD-TC-003639.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE | business_scenario | request | TC-003640 |  | PASS | test_data/TD-TC-003640.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003429 |  | PASS | test_data/TD-TC-003429.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003430 |  | PASS | test_data/TD-TC-003430.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003431 |  | PASS | test_data/TD-TC-003431.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003432 |  | PASS | test_data/TD-TC-003432.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003433 |  | PASS | test_data/TD-TC-003433.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003434 |  | PASS | test_data/TD-TC-003434.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003435 |  | PASS | test_data/TD-TC-003435.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003436 |  | PASS | test_data/TD-TC-003436.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003437 |  | PASS | test_data/TD-TC-003437.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003438 |  | PASS | test_data/TD-TC-003438.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003439 |  | PASS | test_data/TD-TC-003439.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003440 |  | PASS | test_data/TD-TC-003440.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003441 |  | PASS | test_data/TD-TC-003441.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003442 |  | PASS | test_data/TD-TC-003442.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003443 |  | PASS | test_data/TD-TC-003443.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003444 |  | PASS | test_data/TD-TC-003444.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003445 |  | PASS | test_data/TD-TC-003445.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003446 |  | PASS | test_data/TD-TC-003446.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003447 |  | PASS | test_data/TD-TC-003447.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003448 |  | PASS | test_data/TD-TC-003448.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003449 |  | PASS | test_data/TD-TC-003449.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003450 |  | PASS | test_data/TD-TC-003450.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003451 |  | PASS | test_data/TD-TC-003451.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003452 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003453 |  | PASS | test_data/TD-TC-003453.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003454 |  | PASS | test_data/TD-TC-003454.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003455 |  | PASS | test_data/TD-TC-003455.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003456 |  | PASS | test_data/TD-TC-003456.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT | business_scenario | request | TC-003457 |  | PASS | test_data/TD-TC-003457.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003458 |  | PASS | test_data/TD-TC-003458.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003459 |  | PASS | test_data/TD-TC-003459.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003460 |  | PASS | test_data/TD-TC-003460.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003461 |  | PASS | test_data/TD-TC-003461.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003462 |  | PASS | test_data/TD-TC-003462.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003463 |  | PASS | test_data/TD-TC-003463.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003464 |  | PASS | test_data/TD-TC-003464.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003465 |  | PASS | test_data/TD-TC-003465.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003466 |  | PASS | test_data/TD-TC-003466.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003467 |  | PASS | test_data/TD-TC-003467.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003468 |  | PASS | test_data/TD-TC-003468.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003469 |  | PASS | test_data/TD-TC-003469.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003470 |  | PASS | test_data/TD-TC-003470.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003471 |  | PASS | test_data/TD-TC-003471.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003472 |  | PASS | test_data/TD-TC-003472.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003473 |  | PASS | test_data/TD-TC-003473.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003474 |  | PASS | test_data/TD-TC-003474.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003475 |  | PASS | test_data/TD-TC-003475.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003476 |  | PASS | test_data/TD-TC-003476.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003477 |  | PASS | test_data/TD-TC-003477.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003478 |  | PASS | test_data/TD-TC-003478.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003479 |  | PASS | test_data/TD-TC-003479.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003480 |  | PASS | test_data/TD-TC-003480.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003481 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003482 |  | PASS | test_data/TD-TC-003482.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003483 |  | PASS | test_data/TD-TC-003483.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003484 |  | PASS | test_data/TD-TC-003484.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003485 |  | PASS | test_data/TD-TC-003485.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS | business_scenario | request | TC-003486 |  | PASS | test_data/TD-TC-003486.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003583 |  | PASS | test_data/TD-TC-003583.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003584 |  | PASS | test_data/TD-TC-003584.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003585 |  | PASS | test_data/TD-TC-003585.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003586 |  | PASS | test_data/TD-TC-003586.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003587 |  | PASS | test_data/TD-TC-003587.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003588 |  | PASS | test_data/TD-TC-003588.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003589 |  | PASS | test_data/TD-TC-003589.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003590 |  | PASS | test_data/TD-TC-003590.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003591 |  | PASS | test_data/TD-TC-003591.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003592 |  | PASS | test_data/TD-TC-003592.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003593 |  | PASS | test_data/TD-TC-003593.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003594 |  | PASS | test_data/TD-TC-003594.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003595 |  | PASS | test_data/TD-TC-003595.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003596 |  | PASS | test_data/TD-TC-003596.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003597 |  | PASS | test_data/TD-TC-003597.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003598 |  | PASS | test_data/TD-TC-003598.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003599 |  | PASS | test_data/TD-TC-003599.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003600 |  | PASS | test_data/TD-TC-003600.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003601 |  | PASS | test_data/TD-TC-003601.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003602 |  | PASS | test_data/TD-TC-003602.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003603 |  | PASS | test_data/TD-TC-003603.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003604 |  | PASS | test_data/TD-TC-003604.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003605 |  | PASS | test_data/TD-TC-003605.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003606 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003607 |  | PASS | test_data/TD-TC-003607.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003608 |  | PASS | test_data/TD-TC-003608.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003609 |  | PASS | test_data/TD-TC-003609.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003610 |  | PASS | test_data/TD-TC-003610.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK | business_scenario | request | TC-003611 |  | PASS | test_data/TD-TC-003611.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003487 |  | PASS | test_data/TD-TC-003487.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003488 |  | PASS | test_data/TD-TC-003488.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003489 |  | PASS | test_data/TD-TC-003489.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003490 |  | PASS | test_data/TD-TC-003490.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003491 |  | PASS | test_data/TD-TC-003491.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003492 |  | PASS | test_data/TD-TC-003492.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003493 |  | PASS | test_data/TD-TC-003493.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003494 |  | PASS | test_data/TD-TC-003494.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003495 |  | PASS | test_data/TD-TC-003495.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003496 |  | PASS | test_data/TD-TC-003496.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003497 |  | PASS | test_data/TD-TC-003497.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003498 |  | PASS | test_data/TD-TC-003498.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003499 |  | PASS | test_data/TD-TC-003499.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003500 |  | PASS | test_data/TD-TC-003500.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003501 |  | PASS | test_data/TD-TC-003501.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003502 |  | PASS | test_data/TD-TC-003502.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003503 |  | PASS | test_data/TD-TC-003503.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003504 |  | PASS | test_data/TD-TC-003504.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003505 |  | PASS | test_data/TD-TC-003505.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003506 |  | PASS | test_data/TD-TC-003506.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003507 |  | PASS | test_data/TD-TC-003507.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003508 |  | PASS | test_data/TD-TC-003508.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003509 |  | PASS | test_data/TD-TC-003509.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003510 |  | PASS | test_data/TD-TC-003510.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003511 |  | PASS | test_data/TD-TC-003511.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003512 |  | PASS | test_data/TD-TC-003512.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003513 |  | PASS | test_data/TD-TC-003513.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003514 |  | PASS | test_data/TD-TC-003514.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003515 |  | PASS | test_data/TD-TC-003515.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003516 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003517 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003518 |  | PASS | test_data/TD-TC-003518.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003519 |  | PASS | test_data/TD-TC-003519.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003520 |  | PASS | test_data/TD-TC-003520.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003521 |  | PASS | test_data/TD-TC-003521.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE | business_scenario | request | TC-003522 |  | PASS | test_data/TD-TC-003522.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003552 |  | PASS | test_data/TD-TC-003552.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003553 |  | PASS | test_data/TD-TC-003553.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003554 |  | PASS | test_data/TD-TC-003554.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003555 |  | PASS | test_data/TD-TC-003555.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003556 |  | PASS | test_data/TD-TC-003556.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003557 |  | PASS | test_data/TD-TC-003557.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003558 |  | PASS | test_data/TD-TC-003558.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003559 |  | PASS | test_data/TD-TC-003559.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003560 |  | PASS | test_data/TD-TC-003560.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003561 |  | PASS | test_data/TD-TC-003561.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003562 |  | PASS | test_data/TD-TC-003562.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003563 |  | PASS | test_data/TD-TC-003563.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003564 |  | PASS | test_data/TD-TC-003564.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003565 |  | PASS | test_data/TD-TC-003565.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003566 |  | PASS | test_data/TD-TC-003566.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003567 |  | PASS | test_data/TD-TC-003567.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003568 |  | PASS | test_data/TD-TC-003568.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003569 |  | PASS | test_data/TD-TC-003569.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003570 |  | PASS | test_data/TD-TC-003570.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003571 |  | PASS | test_data/TD-TC-003571.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003572 |  | PASS | test_data/TD-TC-003572.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003573 |  | PASS | test_data/TD-TC-003573.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003574 |  | PASS | test_data/TD-TC-003574.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003575 |  | PASS | test_data/TD-TC-003575.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003576 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003577 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003578 |  | PASS | test_data/TD-TC-003578.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003579 |  | PASS | test_data/TD-TC-003579.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003580 |  | PASS | test_data/TD-TC-003580.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003581 |  | PASS | test_data/TD-TC-003581.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL | business_scenario | request | TC-003582 |  | PASS | test_data/TD-TC-003582.step1.purchase.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003758 |  | PASS | test_data/TD-TC-003758.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MAGSTRIPE | business_scenario | request | TC-003759 |  | PASS | test_data/TD-TC-003759.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-003766 |  | PASS | test_data/TD-TC-003766.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MOBILE | business_scenario | request | TC-003767 |  | PASS | test_data/TD-TC-003767.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CREDENTIAL_ON_FILE | business_scenario | request | TC-003764 |  | PASS | test_data/TD-TC-003764.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-CREDENTIAL_ON_FILE | business_scenario | request | TC-003765 |  | PASS | test_data/TD-TC-003765.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACT | business_scenario | request | TC-003752 |  | PASS | test_data/TD-TC-003752.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACT | business_scenario | request | TC-003753 |  | PASS | test_data/TD-TC-003753.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-003754 |  | PASS | test_data/TD-TC-003754.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACTLESS | business_scenario | request | TC-003755 |  | PASS | test_data/TD-TC-003755.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_FALLBACK | business_scenario | request | TC-003762 |  | PASS | test_data/TD-TC-003762.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_FALLBACK | business_scenario | request | TC-003763 |  | PASS | test_data/TD-TC-003763.json | FULLY_TRACED | pending_review / pending_review / pending_sme |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MAGSTRIPE | business_scenario | request | TC-003756 |  | PASS | test_data/TD-TC-003756.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MAGSTRIPE | business_scenario | request | TC-003757 |  | PASS | test_data/TD-TC-003757.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MANUAL | business_scenario | request | TC-003760 |  | PASS | test_data/TD-TC-003760.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A47DDF5D | BUYPASS ATL105 MasterCard Fleet | TS-ATL105-FLEET-MASTERCARD-RETURN-MANUAL | business_scenario | request | TC-003761 |  | PASS | test_data/TD-TC-003761.json | FULLY_TRACED | pending_review / pending_review / pending_review |

## 5. Requirement detail

### BR-A47DDF5D — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 MasterCard Fleet
Segment `UNMAPPED` · Source page 522 · Derivation `business_requirement`

- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003701** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003701 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003701.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003702** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003702 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003702.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003703** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003703 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003703.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003704** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003704 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003704.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003705** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003705 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003705.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003706** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003706 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003706.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003707** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003707 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003707 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003708** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003708 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003708.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003709** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003709 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003709.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003710** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Magnetic Stripe — TC-003710 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003710.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CONTACTLESS_MOBILE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003742** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003742 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003742.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003743** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003743 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003743.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003744** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003744 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003744.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003745** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003745 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003745.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003746** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003746 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003746.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003747** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003747 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003747.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003748** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003748 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003748 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003749** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003749 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003749.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003750** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003750 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003750.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003751** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Contactless Mobile Commerce Device — TC-003751 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003751.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-CREDENTIAL_ON_FILE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003732** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003732 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003732.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003733** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003733 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003733.step1.auth-only.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003734** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003734 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003734.step1.auth-only.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003735** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003735 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003735.step1.auth-only.json` — 5 segments, 170 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003736** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003736 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003736.step1.auth-only.json` — 6 segments, 102 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003737** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003737 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003737.step1.auth-only.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003738** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003738 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003738 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003739** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003739 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003739.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003740** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003740 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003740.step1.auth-only.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003741** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Credential on File — TC-003741 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003741.step1.auth-only.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACT** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003670** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003670 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003670.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003671** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003671 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003671.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003672** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003672 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003672.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003673** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003673 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003673.step1.auth-only.json` — 6 segments, 145 elements (41 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003674** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003674 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003674.step1.auth-only.json` — 7 segments, 95 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003675** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003675 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003675.step1.auth-only.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003676** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003676 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003676 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003677** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003677 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003677.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003678** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003678 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003678.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003679** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contact Chip — TC-003679 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003679.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_CONTACTLESS** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003680** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003680 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003680.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003681** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003681 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003681.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003682** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003682 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003682.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003683** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003683 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003683.step1.auth-only.json` — 6 segments, 145 elements (41 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003684** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003684 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003684.step1.auth-only.json` — 7 segments, 95 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003685** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003685 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003685.step1.auth-only.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003686** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003686 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003686 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003687** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003687 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003687.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003688** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003688 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003688.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003689** — BUYPASS ATL105 MasterCard Fleet Authorization Only – EMV Contactless Chip — TC-003689 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003689.step1.auth-only.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-EMV_FALLBACK** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003722** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003722 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003722.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003723** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003723 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003723.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003724** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003724 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003724.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003725** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003725 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003725.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003726** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003726 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003726.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003727** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003727 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003727.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003728** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003728 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003728 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003729** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003729 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003729.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003730** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003730 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003730.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003731** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Chip Fallback — TC-003731 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003731.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003690** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003690 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003690.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003691** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003691 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003691.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003692** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003692 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003692.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003693** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003693 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003693.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003694** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003694 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003694.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003695** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003695 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003695.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003696** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003696 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003696.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 122` (spec_derived, pNone)
  - **TC-003697** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003697 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003697 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003698** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003698 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003698.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003699** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003699 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003699.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003700** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Magnetic Stripe — TC-003700 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003700.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-AUTH_ONLY-MANUAL** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003711** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003711 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003711.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003712** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003712 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003712.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003713** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003713 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003713.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003714** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003714 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003714.step1.auth-only.json` — 5 segments, 134 elements (38 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003715** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003715 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003715.step1.auth-only.json` — 6 segments, 84 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003716** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003716 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003716.step1.auth-only.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003717** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003717 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003717.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003718** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003718 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003718 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003719** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003719 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003719.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 3080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003720** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003720 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003720.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003721** — BUYPASS ATL105 MasterCard Fleet Authorization Only – Manual / Keyed — TC-003721 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003721.step1.auth-only.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003523** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003523 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003523.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003524** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003524 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003524.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003525** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003525 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003525.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003526** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003526 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003526.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003527** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003527 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003527.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003528** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003528 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003528.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003529** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003529 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003529.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003530** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003530 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003530.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003531** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003531 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003531.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003532** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003532 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003532.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003533** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003533 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003533.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003534** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003534 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003534.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003535** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003535 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003535.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003536** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003536 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003536.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003537** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003537 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003537.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003538** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003538 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003538.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003539** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003539 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003539.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003540** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003540 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003540.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003541** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003541 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003541.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003542** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003542 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003542.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003543** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003543 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003543.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003544** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003544 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003544.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003545** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003545 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003545.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003546** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003546 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003546 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003547** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003547 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003547.step1.purchase.json` — 6 segments, 116 elements (32 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003548** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003548 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003548.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003549** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003549 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003549.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003550** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003550 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003550.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003551** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Magnetic Stripe — TC-003551 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003551.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-CONTACTLESS_MOBILE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003641** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003641 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003641.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003642** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003642 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003642.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003643** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003643 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003643.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003644** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003644 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003644.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003645** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003645 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003645.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003646** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003646 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003646.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003647** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003647 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003647.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003648** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003648 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003648.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003649** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003649 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003649.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003650** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003650 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003650.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003651** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003651 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003651.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003652** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003652 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003652.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003653** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003653 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003653.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003654** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003654 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003654.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003655** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003655 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003655.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003656** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003656 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003656.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003657** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003657 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003657.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003658** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003658 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003658.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003659** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003659 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003659.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003660** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003660 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003660.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003661** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003661 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003661.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003662** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003662 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003662.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003663** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003663 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003663.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003664** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003664 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003664 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003665** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003665 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003665.step1.purchase.json` — 6 segments, 116 elements (32 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003666** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003666 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003666.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003667** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003667 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003667.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003668** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003668 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003668.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003669** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Contactless Mobile Commerce Device — TC-003669 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003669.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-CREDENTIAL_ON_FILE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003612** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003612 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003612.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003613** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003613 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003613.step1.purchase.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003614** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003614 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003614.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003615** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003615 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003615.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003616** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003616 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003616.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003617** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003617 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003617.step1.purchase.json` — 5 segments, 126 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003618** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003618 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003618.step1.purchase.json` — 5 segments, 168 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003619** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003619 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003619.step1.purchase.json` — 6 segments, 59 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003620** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003620 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003620.step1.purchase.json` — 6 segments, 99 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003621** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003621 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003621.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003622** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003622 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003622.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003623** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003623 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003623.step1.purchase.json` — 6 segments, 59 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003624** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003624 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003624.step1.purchase.json` — 6 segments, 59 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003625** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003625 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003625.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003626** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003626 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003626.step1.purchase.json` — 5 segments, 127 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003627** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003627 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003627.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003628** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003628 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003628.step1.purchase.json` — 5 segments, 127 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003629** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003629 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003629.step1.purchase.json` — 5 segments, 126 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003630** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003630 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003630.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003631** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003631 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003631.step1.purchase.json` — 5 segments, 126 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003632** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003632 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003632.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003633** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003633 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003633.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003634** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003634 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003634.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003635** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003635 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003635 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003636** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003636 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003636.step1.purchase.json` — 6 segments, 143 elements (32 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003637** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003637 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003637.step1.purchase.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003638** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003638 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003638.step1.purchase.json` — 5 segments, 84 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003639** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003639 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003639.step1.purchase.json` — 6 segments, 101 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003640** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Credential on File — TC-003640 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003640.step1.purchase.json` — 5 segments, 85 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACT** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003429** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003429 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003429.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003430** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003430 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003430.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003431** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003431 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003431.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003432** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003432 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003432.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003433** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003433 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003433.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003434** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003434 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003434.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003435** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003435 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003435.step1.purchase.json` — 6 segments, 143 elements (39 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003436** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003436 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003436.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003437** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003437 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003437.step1.purchase.json` — 7 segments, 92 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003438** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003438 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003438.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003439** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003439 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003439.step1.purchase.json` — 7 segments, 94 elements (26 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003440** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003440 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003440.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003441** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003441 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003441.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003442** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003442 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003442.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003443** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003443 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003443.step1.purchase.json` — 6 segments, 111 elements (31 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003444** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003444 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003444.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003445** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003445 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003445.step1.purchase.json` — 6 segments, 122 elements (34 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003446** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003446 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003446.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003447** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003447 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003447.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003448** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003448 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003448.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003449** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003449 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003449.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003450** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003450 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003450.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003451** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003451 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003451.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003452** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003452 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003452 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003453** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003453 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003453.step1.purchase.json` — 7 segments, 149 elements (41 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003454** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003454 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003454.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003455** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003455 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003455.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003456** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003456 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003456.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003457** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contact Chip — TC-003457 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003457.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_CONTACTLESS** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003458** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003458 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003458.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003459** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003459 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003459.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003460** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003460 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003460.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003461** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003461 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003461.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003462** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003462 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003462.step1.purchase.json` — 6 segments, 77 elements (21 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003463** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003463 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003463.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003464** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003464 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003464.step1.purchase.json` — 6 segments, 143 elements (39 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003465** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003465 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003465.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003466** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003466 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003466.step1.purchase.json` — 7 segments, 92 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003467** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003467 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003467.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003468** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003468 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003468.step1.purchase.json` — 7 segments, 94 elements (26 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003469** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003469 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003469.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003470** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003470 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003470.step1.purchase.json` — 7 segments, 61 elements (17 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003471** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003471 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003471.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003472** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003472 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003472.step1.purchase.json` — 6 segments, 111 elements (31 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003473** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003473 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003473.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003474** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003474 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003474.step1.purchase.json` — 6 segments, 122 elements (34 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003475** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003475 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003475.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003476** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003476 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003476.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003477** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003477 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003477.step1.purchase.json` — 6 segments, 121 elements (33 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003478** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003478 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003478.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003479** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003479 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003479.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003480** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003480 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003480.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003481** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003481 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003481 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003482** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003482 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003482.step1.purchase.json` — 7 segments, 149 elements (41 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003483** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003483 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003483.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003484** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003484 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003484.step1.purchase.json` — 6 segments, 88 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003485** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003485 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003485.step1.purchase.json` — 7 segments, 105 elements (29 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003486** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – EMV Contactless Chip — TC-003486 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003486.step1.purchase.json` — 6 segments, 78 elements (22 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-EMV_FALLBACK** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003583** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003583 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003583.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003584** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003584 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003584.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003585** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003585 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003585.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003586** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003586 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003586.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003587** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003587 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003587.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003588** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003588 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003588.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003589** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003589 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003589.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003590** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003590 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003590.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003591** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003591 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003591.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003592** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003592 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003592.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003593** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003593 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003593.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003594** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003594 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003594.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003595** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003595 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003595.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003596** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003596 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003596.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003597** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003597 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003597.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003598** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003598 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003598.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003599** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003599 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003599.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003600** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003600 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003600.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003601** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003601 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003601.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003602** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003602 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003602.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003603** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003603 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003603.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003604** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003604 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003604.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003605** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003605 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003605.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003606** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003606 · `None` · verification `NOT_APPLICABLE` · review `pending_sme`
    - Test data: _Expected test data file for TC-003606 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003607** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003607 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003607.step1.purchase.json` — 6 segments, 116 elements (32 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003608** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003608 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003608.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003609** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003609 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003609.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003610** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003610 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003610.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003611** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Chip Fallback — TC-003611 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003611.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003487** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003487 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003487.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003488** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003488 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003488.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003489** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003489 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003489.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003490** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003490 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003490.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003491** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003491 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003491.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003492** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003492 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003492.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003493** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003493 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003493.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003494** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003494 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003494.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003495** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003495 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003495.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003496** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003496 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003496.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003497** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003497 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003497.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003498** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003498 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003498.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003499** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003499 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003499.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003500** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003500 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003500.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003501** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003501 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003501.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003502** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003502 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003502.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003503** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003503 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003503.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003504** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003504 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003504.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003505** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003505 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003505.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003506** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003506 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003506.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003507** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003507 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003507.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003508** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003508 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003508.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003509** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003509 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003509.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003510** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003510 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003510.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003511** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003511 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003511.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003512** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003512 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003512.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003513** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003513 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003513.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003514** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003514 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003514.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003515** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003515 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003515.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 122` (spec_derived, pNone)
  - **TC-003516** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003516 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003516 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003517** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003517 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003517 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003518** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003518 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003518.step1.purchase.json` — 6 segments, 116 elements (32 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003519** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003519 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003519.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003520** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003520 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003520.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003521** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003521 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003521.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003522** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Magnetic Stripe — TC-003522 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003522.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 094` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-PURCHASE-MANUAL** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003552** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003552 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003552.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003553** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003553 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003553.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003554** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003554 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003554.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003555** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003555 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003555.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003556** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003556 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003556.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003557** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003557 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003557.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003558** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003558 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003558.step1.purchase.json` — 5 segments, 132 elements (36 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003559** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003559 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003559.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003560** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003560 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003560.step1.purchase.json` — 6 segments, 81 elements (24 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003561** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003561 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003561.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003562** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003562 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003562.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003563** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003563 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003563.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003564** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003564 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003564.step1.purchase.json` — 6 segments, 50 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003565** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003565 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003565.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = Z080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003566** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003566 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003566.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003567** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003567 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003567.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003568** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003568 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003568.step1.purchase.json` — 5 segments, 100 elements (28 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003569** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003569 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003569.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003570** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003570 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003570.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003571** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003571 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003571.step1.purchase.json` — 5 segments, 99 elements (27 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003572** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003572 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003572.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003573** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003573 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003573.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003574** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003574 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003574.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003575** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003575 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003575.json` — 5 segments, 32 elements (9 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 0080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003576** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003576 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003576 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003577** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003577 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-003577 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-003578** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003578 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003578.step1.purchase.json` — 6 segments, 116 elements (32 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003579** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003579 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003579.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003580** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003580 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003580.step1.purchase.json` — 5 segments, 66 elements (18 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003581** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003581 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003581.step1.purchase.json` — 6 segments, 83 elements (23 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
  - **TC-003582** — BUYPASS ATL105 MasterCard Fleet Purchase/Capture – Manual / Keyed — TC-003582 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003582.step1.purchase.json` — 5 segments, 67 elements (19 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 078` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003758** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe — TC-003758 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003758.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003759** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Magnetic Stripe — TC-003759 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003759.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-CONTACTLESS_MOBILE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003766** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device — TC-003766 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003766.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003767** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Contactless Mobile Commerce Device — TC-003767 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003767.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-CREDENTIAL_ON_FILE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003764** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File — TC-003764 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003764.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003765** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Credential on File — TC-003765 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003765.json` — 5 segments, 42 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACT** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003752** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip — TC-003752 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003752.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003753** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contact Chip — TC-003753 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003753.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_CONTACTLESS** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003754** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip — TC-003754 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003754.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003755** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – EMV Contactless Chip — TC-003755 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003755.json` — 6 segments, 44 elements (13 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-EMV_FALLBACK** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003762** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback — TC-003762 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003762.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003763** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Chip Fallback — TC-003763 · `None` · verification `PASS` · review `pending_sme`
    - Test data: `test_data/TD-TC-003763.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-MAGSTRIPE** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003756** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe — TC-003756 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003756.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
  - **TC-003757** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Magnetic Stripe — TC-003757 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003757.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 094` (spec_derived, pNone)
- **TS-ATL105-FLEET-MASTERCARD-RETURN-MANUAL** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-003760** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed — TC-003760 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003760.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)
  - **TC-003761** — BUYPASS ATL105 MasterCard Fleet Merchandise Return – Manual / Keyed — TC-003761 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-003761.json` — 5 segments, 33 elements (10 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.PromptCode = 7080` (spec_derived, pNone); `100.SegmentLength = 078` (spec_derived, pNone)

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-003452`
- `TC-003481`
- `TC-003516`
- `TC-003517`
- `TC-003546`
- `TC-003576`
- `TC-003577`
- `TC-003606`
- `TC-003635`
- `TC-003664`
- `TC-003676`
- `TC-003686`
- `TC-003697`
- `TC-003707`
- `TC-003718`
- `TC-003728`
- `TC-003738`
- `TC-003748`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
