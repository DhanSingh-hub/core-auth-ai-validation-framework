# Traceability Matrix — Business Requirement -> Test Scenario -> Test Case -> Test Data

**Specification:** BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf · Spec ID `ATL105` · Version `2026-4`
**Build:** `buypass-atl105-mastercard-credit-generated-6181e6cf0e` · Filter `buypass-atl105-mastercard-credit-generated` · Selection `{"br_kind": ["OPERATIONS"], "platform": ["BUYPASS"], "spec": ["ATL105"]}`
**Review status as of:** 2026-11-19T16:39:44.071013+00:00 (approval ledger; regenerate with `business_model/cli.py --report <build>`)
**Generated:** 2026-11-19T16:39:44.504363+00:00

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
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-078290 |  | PASS | test_data/TD-TC-078290.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST | business_scenario | request | TC-078291 |  | PASS | test_data/TD-TC-078291.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900 | business_scenario | request | TC-078292 |  | PASS | test_data/TD-TC-078292.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-078253 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-078254 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-078255 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-078256 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-078257 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR | business_scenario | request | TC-078258 |  | NOT_APPLICABLE |  | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-078293 |  | PASS | test_data/TD-TC-078293.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-078294 |  | PASS | test_data/TD-TC-078294.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-078295 |  | PASS | test_data/TD-TC-078295.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-078296 |  | PASS | test_data/TD-TC-078296.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-078297 |  | PASS | test_data/TD-TC-078297.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-078298 |  | PASS | test_data/TD-TC-078298.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST | business_scenario | request | TC-078299 |  | PASS | step 1: test_data/TD-TC-078299.step1.purchase.json; step 2: test_data/TD-TC-078299.step2.table-load.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078270 |  | PASS | test_data/TD-TC-078270.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078271 |  | PASS | test_data/TD-TC-078271.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078272 |  | PASS | test_data/TD-TC-078272.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078273 |  | PASS | test_data/TD-TC-078273.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078274 |  | PASS | test_data/TD-TC-078274.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078275 |  | PASS | test_data/TD-TC-078275.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078276 |  | PASS | test_data/TD-TC-078276.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST | business_scenario | request | TC-078277 |  | PASS | test_data/TD-TC-078277.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078278 |  | PASS | test_data/TD-TC-078278.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078279 |  | PASS | test_data/TD-TC-078279.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078280 |  | PASS | test_data/TD-TC-078280.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078281 |  | PASS | test_data/TD-TC-078281.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078282 |  | PASS | test_data/TD-TC-078282.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078283 |  | PASS | test_data/TD-TC-078283.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078284 |  | PASS | test_data/TD-TC-078284.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078285 |  | PASS | test_data/TD-TC-078285.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078286 |  | PASS | test_data/TD-TC-078286.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST | business_scenario | request | TC-078287 |  | PASS | test_data/TD-TC-078287.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078259 |  | PASS | test_data/TD-TC-078259.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078260 |  | PASS | test_data/TD-TC-078260.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078261 |  | PASS | test_data/TD-TC-078261.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078262 |  | PASS | test_data/TD-TC-078262.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078263 |  | PASS | test_data/TD-TC-078263.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078264 |  | PASS | test_data/TD-TC-078264.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078265 |  | PASS | test_data/TD-TC-078265.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078266 |  | PASS | test_data/TD-TC-078266.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078267 |  | PASS | test_data/TD-TC-078267.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078268 |  | PASS | test_data/TD-TC-078268.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990 | business_scenario | request | TC-078269 |  | PASS | test_data/TD-TC-078269.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-078288 |  | PASS | test_data/TD-TC-078288.json | FULLY_TRACED | pending_review / pending_review / pending_review |
| BR-A0098BFB | BUYPASS ATL105 Terminal & Host Operations | TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST | business_scenario | request | TC-078289 |  | PASS | test_data/TD-TC-078289.json | FULLY_TRACED | pending_review / pending_review / pending_review |

## 5. Requirement detail

### BR-A0098BFB — FULLY_TRACED · review `pending_review`
> BUYPASS ATL105 Terminal & Host Operations
Segment `UNMAPPED` · Source page 77 · Derivation `business_requirement`

- **TS-ATL105-OPERATIONS-CA-PUBLIC-KEY-FILE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078290** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-078290 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078290.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 088` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078291** — BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request — TC-078291 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078291.json` — 2 segments, 12 elements (6 provisional, 0 awaiting client value)
    - Key values: `132.CAPublicKeyFileChecksum = 2012091700000100000102171` (qe_sample_observed, pNone); `132.LoadType = K` (spec_derived, pNone); `132.SegmentLength = 088` (spec_derived, pNone); `132.SegmentType = 132` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-COMMUNICATIONS-TEST-TRANSACTION-TYPE-900** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078292** — BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) — TC-078292 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078292.json` — 2 segments, 2 elements (0 provisional, 0 awaiting client value)
    - Key values: `COMMUNICATIONS_TEST_REQUEST.NetworkManagementMessage = COMMTEST` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-DEVICE-TERMINAL-BEHAVIOUR** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078253** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-078253 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-078253 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-078254** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-078254 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-078254 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-078255** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-078255 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-078255 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-078256** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-078256 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-078256 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-078257** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-078257 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-078257 was not found on disk. Re-run Test Case Generation to regenerate test data files._
  - **TC-078258** — BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour — TC-078258 · `None` · verification `NOT_APPLICABLE` · review `pending_review`
    - Test data: _Expected test data file for TC-078258 was not found on disk. Re-run Test Case Generation to regenerate test data files._
- **TS-ATL105-OPERATIONS-DOWNLOADS-DOWNLINE-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078293** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-078293 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078293.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-078294** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-078294 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078294.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `PHONE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `PHONE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `PHONE_LOAD_REQUEST.TerminalIdentifier = JX41894821001` (platform_default, pNone)
  - **TC-078295** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-078295 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078295.json` — 1 segments, 3 elements (1 provisional, 0 awaiting client value)
    - Key values: `DATE_AND_TIME_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.LoadType = D` (spec_derived, pNone); `DATE_AND_TIME_LOAD_REQUEST.TerminalIdentifier = JX41894821001` (platform_default, pNone)
  - **TC-078296** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-078296 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078296.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `SOFTWARE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `SOFTWARE_LOAD_REQUEST.FirmwareVersion = 5RK9B8QS` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.HardwareVersion = W3SOAZVJ` (platform_default, pNone); `SOFTWARE_LOAD_REQUEST.SoftwareVersion = WSBB6ETC` (platform_default, pNone)
  - **TC-078297** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-078297 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078297.json` — 1 segments, 7 elements (4 provisional, 0 awaiting client value)
    - Key values: `MONERIS_KEY_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadSubtype = M` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.LoadType = K` (spec_derived, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisMerchantID = KC3M9G7VAO3RU` (platform_default, pNone); `MONERIS_KEY_LOAD_REQUEST.MonerisTerminalIdentifier = JWMSF4Z5` (platform_default, pNone)
  - **TC-078298** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-078298 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078298.json` — 1 segments, 6 elements (4 provisional, 0 awaiting client value)
    - Key values: `TABLE_LOAD_REQUEST.InformationByte = ?` (spec_derived, pNone); `TABLE_LOAD_REQUEST.LoadType = P` (spec_derived, pNone); `TABLE_LOAD_REQUEST.FirmwareVersion = 28WCOQSF` (platform_default, pNone); `TABLE_LOAD_REQUEST.HardwareVersion = 6AGEDQY4` (platform_default, pNone); `TABLE_LOAD_REQUEST.SoftwareVersion = TVTTCAXW` (platform_default, pNone)
  - **TC-078299** — BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) — TC-078299 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078299.step1.purchase.json` — 5 segments, 38 elements (14 provisional, 0 awaiting client value)
    - Key values: `100.InformationByte = 0` (qe_sample_observed, pNone); `100.NonfuelAmount = 1500` (spec_derived, pNone); `100.PartialApprovalIndicator = 1` (qe_sample_observed, pNone); `100.SegmentLength = 083` (spec_derived, pNone); `100.SegmentType = 100` (spec_derived, pNone)
    - Step 1 (purchase, Purchase/Capture): `test_data/TD-TC-078299.step1.purchase.json`
    - Step 2 (table_load, OP_TABLE_LOAD): `test_data/TD-TC-078299.step2.table-load.json`
- **TS-ATL105-OPERATIONS-ELECTRONIC-MAIL-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078270** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078270 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078270.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078271** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078271 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078271.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078272** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078272 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078272.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078273** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078273 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078273.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078274** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078274 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078274.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 981` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078275** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078275 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078275.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 996` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078276** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078276 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078276.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 997` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078277** — BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request — TC-078277 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078277.json` — 2 segments, 9 elements (3 provisional, 0 awaiting client value)
    - Key values: `109.InformationByte = 0` (qe_sample_observed, pNone); `109.PromptCode = 995` (spec_derived, pNone); `109.SegmentLength = 046` (spec_derived, pNone); `109.SegmentType = 109` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-PROPRIETARY-DATA-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078278** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078278 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078278.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078279** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078279 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078279.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078280** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078280 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078280.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078281** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078281 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078281.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078282** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078282 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078282.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078283** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078283 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078283.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 901` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078284** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078284 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078284.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 902` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078285** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078285 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078285.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 903` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078286** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078286 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078286.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 904` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
  - **TC-078287** — BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request — TC-078287 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078287.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `118.InformationByte = 0` (qe_sample_observed, pNone); `118.PromptCode = 905` (spec_derived, pNone); `118.SegmentLength = 0193` (spec_derived, pNone); `118.SegmentType = 118` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TOTALS-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078259** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078259 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078259.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078260** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078260 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078260.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078261** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078261 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078261.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078262** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078262 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078262.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078263** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078263 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078263.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078264** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078264 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078264.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078265** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078265 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078265.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078266** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078266 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078266.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
  - **TC-078267** — BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) — TC-078267 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078267.json` — 2 segments, 14 elements (6 provisional, 0 awaiting client value)
    - Key values: `105.GrandTotal = 00000000` (spec_derived, pNone); `105.InformationByte = 0` (qe_sample_observed, pNone); `105.PromptCode = 990` (spec_derived, pNone); `105.SegmentLength = 084` (spec_derived, pNone); `105.SegmentType = 105` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TOTALS-WITH-PROPRIETARY-DATA-LOAD-REQUEST-PROMPT-CODE-990** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078268** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-078268 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078268.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 153` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
  - **TC-078269** — BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) — TC-078269 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078269.json` — 2 segments, 19 elements (7 provisional, 0 awaiting client value)
    - Key values: `119.InformationByte = 0` (qe_sample_observed, pNone); `119.PromptCode = 990` (spec_derived, pNone); `119.SegmentLength = 153` (spec_derived, pNone); `119.SegmentType = 119` (spec_derived, pNone); `119.TotalsDate = 333333` (spec_derived, pNone)
- **TS-ATL105-OPERATIONS-TRANSARMOR-KEY-LOAD-REQUEST** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (`business_scenario`), chain `request`, expects response `None`, review `pending_review`
  - **TC-078288** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-078288 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078288.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 059` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)
  - **TC-078289** — BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request — TC-078289 · `None` · verification `PASS` · review `pending_review`
    - Test data: `test_data/TD-TC-078289.json` — 2 segments, 10 elements (5 provisional, 0 awaiting client value)
    - Key values: `116.LoadType = K` (spec_derived, pNone); `116.SegmentLength = 059` (spec_derived, pNone); `116.SegmentType = 116` (spec_derived, pNone); `DS1.MessageFormatVersionIdentifier = ATL105` (spec_derived, pNone); `DS1.NumberofSegments = 01` (spec_derived, pNone)

## 6. Orphans and unresolved links

**Test cases whose test data file did not resolve:**
- `TC-078253`
- `TC-078254`
- `TC-078255`
- `TC-078256`
- `TC-078257`
- `TC-078258`

## 7. Caveats and known limitations

- Scenario.requirement_id (singular) is anchor-only, kept for backward compatibility. Where a scenario carries requirement_ids (plural — generator.py::build_positive_scenarios, Phase 2 of the traceability fix), it is counted against EVERY covered requirement, not just the anchor; row-level traceability_note flags whether a scenario is 'multi_requirement' or (for legacy/unpopulated data) still 'anchor_only'.
- Coverage is measured against the artifact universe this pipeline extracted, not independently against the source specification.
- Test data marked provisional is a platform default awaiting the client's real value.
- Elements with dataSource == TODO_CLIENT_VALUE have no documented character type or valid values in the spec and were deliberately left blank, awaiting the client's value.
- Element counts reflect the QE-shaped OUTPUT (test_generation/qe_shaped_test_data.py) — composite/merged/repeating field folding collapses several spec fields into one output key, so counts here are LOWER than the raw spec field count composer.py originally filled.
- A requirement's trace_status and coverage figures combine its request-side and response-side chains — each scenario entry carries chain_side ('request' or 'response'). A requirement with ONLY response-side coverage can now reach FULLY_TRACED, which was not possible before response-side scenario mapping existed; this can move requirement_coverage_percent / full_chain_coverage_percent upward relative to a request-side-only count.
- Response-side scenario mapping does not apply to business-model builds: every business scenario is composed into its own request test case(s).
