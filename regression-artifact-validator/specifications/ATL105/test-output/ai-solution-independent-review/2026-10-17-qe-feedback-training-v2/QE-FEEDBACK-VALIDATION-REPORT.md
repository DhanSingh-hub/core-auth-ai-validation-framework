# QE-Fiserv Feedback Training — Validation Report

- Catalog: `QE-FEEDBACK-RULES` v1.0.0 (SHA-256 `91e842d23cc29d68…`), 33 rules enforced as written regardless of workbook status.
- Delivery: preserved October-10 AI delivery `runs/2026-10-17/Run1` (intake manifest SHA-256 `f211dcd85ad0b2af…`).
- Population: 6880 BRs, 12254 scenarios, 21254 test cases (387 flows), 17769/17769 test-data payloads validated, 0 unreferenced payloads.
- Silent coverage: 0 BRs without scenario, 3171 scenarios without test case, 2806 BRs without test case.
- Semantic coverage: NOT_ASSESSED; host execution: NOT_EXECUTED. A PASS is feedback conformance only.

| Rule | Title | Artifact | Feedback | Applicable | Pass | Fail | Review | Fail % |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| QEF-001 | Every BR is linked to a segment | BR | Def_SepRun01, Def_SepRun04, Status-SepDef#1, Status-SepDef#4 | 6880 | 4388 | 1599 | 893 | 23.2 |
| QEF-002 | Every BR has at least one scenario | BR | Def_SepRun02, Def_SepRun05, Def_SepRun21, Status-SepDef#2, Status-SepDef#5 | 6880 | 6880 | 0 | 0 | 0.0 |
| QEF-003 | Every scenario has at least one test case | TS | Def_SepRun02, Def_SepRun05, Def_SepRun21 | 12254 | 9083 | 3171 | 0 | 25.9 |
| QEF-004 | Every test case has physical test data | TC | Def_SepRun02, Def_SepRun21 | 21254 | 17284 | 3970 | 0 | 18.7 |
| QEF-005 | BR segment is present in the test data | TC | Def_SepRun15, Def_SepRun20 | 16325 | 13220 | 3105 | 0 | 19.0 |
| QEF-006 | Scenario adds test design beyond the BR | TS | Def_Sep30Run04, Def_SepRun16 | 12254 | 5600 | 6654 | 0 | 54.3 |
| QEF-007 | Test case has a readable name | TC | Oct10Run_D007, Def_SepRun16 | 21254 | 0 | 21254 | 0 | 100.0 |
| QEF-008 | No duplicate test cases | TC | Def_SepRun17 | 21254 | 21254 | 0 | 0 | 0.0 |
| QEF-009 | Concrete expected response code | TC | Def_Sep30Run03 | 21254 | 15811 | 5443 | 0 | 25.6 |
| QEF-010 | BR has negative coverage | BR | Def_Sep30Run05 | 4074 | 311 | 3763 | 0 | 92.4 |
| QEF-011 | TC and TD segment identity resolvable | TD | Def_Sep30Run06 | 17769 | 17769 | 0 | 0 | 0.0 |
| QEF-012 | Canonical source anchor on every leg | BR/TS/TC | Def_Sep30Run07 | 40388 | 7744 | 32644 | 0 | 80.8 |
| QEF-013 | BR keeps the source-row value constraint | BR | Def_Sep30Run08 | 6880 | 6459 | 421 | 0 | 6.1 |
| QEF-014 | No PASS verdict with unresolved advisories | TC | Def_Sep30Run09 | 21237 | 2407 | 18830 | 0 | 88.7 |
| QEF-015 | Test data is not empty | TD | Def_Sep30Run02, Def_SepRun19 | 17769 | 17760 | 9 | 0 | 0.1 |
| QEF-016 | Composed segments are fully serialized | TD | Def_Sep30Run01, Oct10Run_D004, Oct10Run_D014 | 17769 | 23 | 17746 | 0 | 99.9 |
| QEF-017 | Approval Number by transaction context | TD | Def_SepRun18, Oct10Run_D001, Oct10Run_D002 | 3438 | 227 | 3211 | 0 | 93.4 |
| QEF-018 | Segment Length present and valid | TD | Oct10Run_D003, Def_SepRun19 | 17762 | 0 | 17762 | 0 | 100.0 |
| QEF-019 | Segment 100 field set present | TD | Oct10Run_D012 | 3594 | 392 | 3202 | 0 | 89.1 |
| QEF-020 | Terminal ID length 13-22 | TD | Oct10Run_D005 | 23100 | 23072 | 28 | 0 | 0.1 |
| QEF-021 | Segment Type matches the segment | TD | Oct10Run_D006, Oct10Run_D015 | 17762 | 7783 | 9979 | 0 | 56.2 |
| QEF-022 | Test data ID differs from test case ID | TD | Oct10Run_D008 | 17769 | 0 | 17769 | 0 | 100.0 |
| QEF-023 | NumSegments name and count | TD | Oct10Run_D009 | 17769 | 0 | 17769 | 0 | 100.0 |
| QEF-024 | Segment 123 has Token PAN Suffix | TD | Oct10Run_D010 | 3650 | 560 | 3090 | 0 | 84.7 |
| QEF-025 | CAVV Revised Format is alphanumeric data | TD | Oct10Run_D011 | 3650 | 3370 | 280 | 0 | 7.7 |
| QEF-026 | Track Data combines Account Number and discretionary data | TD | Oct10Run_D013, Action Items#2 | 3594 | 504 | 3090 | 0 | 86.0 |
| QEF-027 | Segment 111 indicator and information fields present | TD | Oct10Run_D016 | 13272 | 0 | 13272 | 0 | 100.0 |
| QEF-028 | No duplicated field aliases | TD | Oct10Run_D017 | 17762 | 4520 | 13242 | 0 | 74.6 |
| QEF-029 | Specification source set and version | Delivery | Def_SepRun22, Oct10Run_D018, Oct10Run_D019 | 1 | 0 | 1 | 0 | 100.0 |
| QEF-030 | Financial request has 1-17 segments | TD | Oct10Run_D020, Oct10Run_D004 | 3594 | 0 | 3594 | 0 | 100.0 |
| QEF-031 | Prompt Code shape by message family | TD | Action Items#1 | 5301 | 3592 | 1709 | 0 | 32.2 |
| QEF-032 | TransArmor requests carry expiration date | TD | Action Items#2 | 0 | 0 | 0 | 0 | n/a |
| QEF-033 | Readable review outputs | Delivery | Def_SepRun03, Def_SepRun06, Def_SepRun07, Def_SepRun08, Def_SepRun09, Def_SepRun10, Status-SepDef#3, Status-SepDef#6, Status-SepDef#7, Status-SepDef#8, Status-SepDef#9, Status-SepDef#10 | 1 | 0 | 1 | 0 | 100.0 |

## Examples and source conflicts

### QEF-001 — Every BR is linked to a segment
- `REQ-SRC-ATL105-PDF-001:006`: no segment_number or ENT-SEG segment_ids
- `REQ-SRC-ATL105-PDF-001:007`: no segment_number or ENT-SEG segment_ids
- `REQ-SRC-ATL105-PDF-001:008`: no segment_number or ENT-SEG segment_ids
- `REQ-SRC-ATL105-PDF-001:009`: no segment_number or ENT-SEG segment_ids

### QEF-002 — Every BR has at least one scenario

### QEF-003 — Every scenario has at least one test case
- `SC-0513`: scenario has no test case
- `SC-0514`: scenario has no test case
- `SC-0515`: scenario has no test case
- `SC-0516`: scenario has no test case

### QEF-004 — Every test case has physical test data
- `TC-002305`: no test data declared
- `TC-002315`: no test data declared
- `TC-002317`: no test data declared
- `TC-002426`: no test data declared

### QEF-005 — BR segment is present in the test data
- `TC-000267`: BR segment 123 not in serialized segments ['100', '101', '102', '103', '104', '108', '111', '114', '130', '135', '143', '145', '151', '153', '156']
- `TC-000268`: BR segment 123 not in serialized segments ['100', '101', '102', '103', '104', '108', '111', '114', '130', '135', '143', '145', '151', '153', '156']
- `TC-000269`: BR segment 123 not in serialized segments ['100', '101', '102', '103', '104', '108', '111', '114', '130', '135', '143', '145', '151', '153', '156']
- `TC-000270`: BR segment 123 not in serialized segments ['100', '101', '102', '103', '104', '108', '111', '114', '130', '135', '143', '145', '151', '153', '156']

### QEF-006 — Scenario adds test design beyond the BR
- `SC-5530`: name repeats BR statement; no parameters and no target transaction
- `SC-5531`: name repeats BR statement; no parameters and no target transaction
- `SC-5532`: name repeats BR statement; no parameters and no target transaction
- `SC-5533`: name repeats BR statement; no parameters and no target transaction

### QEF-007 — Test case has a readable name
- `TC-000001`: no name/description field in catalog record
- `TC-000002`: no name/description field in catalog record
- `TC-000003`: no name/description field in catalog record
- `TC-000004`: no name/description field in catalog record

### QEF-008 — No duplicate test cases

### QEF-009 — Concrete expected response code
- `TC-000695`: expected response code null (tier INFER)
- `TC-000696`: expected response code null (tier INFER)
- `TC-000697`: expected response code null (tier INFER)
- `TC-000698`: expected response code null (tier INFER)

### QEF-010 — BR has negative coverage
- `REQ-SRC-ATL105-PDF-001:060`: 1 linked TC(s), all positive
- `REQ-SRC-ATL105-PDF-001:061`: 1 linked TC(s), all positive
- `REQ-SRC-ATL105-PDF-001:062`: 1 linked TC(s), all positive
- `REQ-SRC-ATL105-PDF-001:065`: 5 linked TC(s), all positive

### QEF-011 — TC and TD segment identity resolvable
- Source conflict (SME confirmation needed): The QE reference request (Authorization.json) itself uses short names such as 'Standard Segment' and 'Variable Info Segment', so renames are evaluated by segment number, not by display name.

### QEF-012 — Canonical source anchor on every leg
- `REQ-SRC-ATL105-PDF-001:001`: missing version
- `REQ-SRC-ATL105-PDF-001:002`: missing version
- `REQ-SRC-ATL105-PDF-001:003`: missing version
- `REQ-SRC-ATL105-PDF-001:004`: missing version

### QEF-013 — BR keeps the source-row value constraint
- `REQ-SRC-ATL105-PDF-001:3237`: expected_behavior='is required and must be present'
- `REQ-SRC-ATL105-PDF-001:3239`: expected_behavior='is required and must be present'
- `REQ-SRC-ATL105-PDF-001:3240`: expected_behavior='is required and must be present'
- `REQ-SRC-ATL105-PDF-001:3241`: expected_behavior='is required and must be present'

### QEF-014 — No PASS verdict with unresolved advisories
- `TC-000001`: PASS verdict with unresolved KB-gap advisories
- `TC-000002`: PASS verdict with unresolved KB-gap advisories
- `TC-000003`: PASS verdict with unresolved KB-gap advisories
- `TC-000004`: PASS verdict with unresolved KB-gap advisories

### QEF-015 — Test data is not empty
- `TC-003737.json`: no data segment content; producer completeness=DELIBERATE_OMISSION verification=PASS
- `TC-003755.json`: no data segment content; producer completeness=DELIBERATE_OMISSION verification=PASS
- `TC-005852.json`: no data segment content; producer completeness=COMPLETE verification=PASS
- `TC-009180.json`: no data segment content; producer completeness=COMPLETE verification=PASS

### QEF-016 — Composed segments are fully serialized
- Source conflict (SME confirmation needed): Required/optional/conditional presence per transaction context is not SME-adjudicated; per feedback all fields of a serialized segment are expected.
- `TC-000001.json`: fields omitted by composer per segment: 100=10, 110=4, 111=1, 113=5
- `TC-000002.json`: fields omitted by composer per segment: 100=10, 110=4, 111=1, 113=5
- `TC-000003.json`: fields omitted by composer per segment: 100=10, 110=4, 111=1, 113=5
- `TC-000004.json`: fields omitted by composer per segment: 100=10, 110=4, 111=1, 113=5

### QEF-017 — Approval Number by transaction context
- Source conflict (SME confirmation needed): Element 5 says Approval Number in a Purchase Request makes it preauthorized; empty-field presence on sale/auth/refund is a QE serialization convention pending SME confirmation.
- `TC-001127.json`: txn 0: ApprovalNumber field absent, expected present and empty
- `TC-001128.json`: txn 0: ApprovalNumber field absent, expected present and empty
- `TC-001129.json`: txn 0: ApprovalNumber field absent, expected present and empty
- `TC-001130.json`: txn 0: ApprovalNumber field absent, expected present and empty

### QEF-018 — Segment Length present and valid
- Source conflict (SME confirmation needed): The length value is checked for presence/width/non-zero only; exact calculated length needs the wire encoding.
- `TC-000001.json`: SegmentLength missing in 4/4: Standard Segment; Check Data Segment; Variable Info Segment; ECA/ TeleCheck Data Segment Format ®
- `TC-000002.json`: SegmentLength missing in 4/4: Standard Segment; Check Data Segment; Variable Info Segment; ECA/ TeleCheck Data Segment Format ®
- `TC-000003.json`: SegmentLength missing in 4/4: Standard Segment; Check Data Segment; Variable Info Segment; ECA/ TeleCheck Data Segment Format ®
- `TC-000004.json`: SegmentLength missing in 4/4: Standard Segment; Check Data Segment; Variable Info Segment; ECA/ TeleCheck Data Segment Format ®

### QEF-019 — Segment 100 field set present
- Source conflict (SME confirmation needed): Matches the QE reference request shape; ATL105 marks several of these conditional.
- `TC-001127.json`: missing Track/Card Discretionary Block Data, Encrypted PIN Block Data, Pump/Lane Number, Fuel Purchase Amount, Non-Fuel Amount, Tax Amount, Cash Amount, Approval Number, Local Date/Time
- `TC-001128.json`: missing Track/Card Discretionary Block Data, Encrypted PIN Block Data, Pump/Lane Number, Fuel Purchase Amount, Non-Fuel Amount, Tax Amount, Cash Amount, Approval Number, Local Date/Time
- `TC-001129.json`: missing Track/Card Discretionary Block Data, Encrypted PIN Block Data, Pump/Lane Number, Fuel Purchase Amount, Non-Fuel Amount, Tax Amount, Cash Amount, Approval Number, Local Date/Time
- `TC-001130.json`: missing Track/Card Discretionary Block Data, Encrypted PIN Block Data, Pump/Lane Number, Fuel Purchase Amount, Non-Fuel Amount, Tax Amount, Cash Amount, Approval Number, Local Date/Time

### QEF-020 — Terminal ID length 13-22
- Source conflict (SME confirmation needed): ATL105 2026-3 Element 102 defines Terminal Identifier as 22 bytes; the 13-22 range is QE feedback pending SME confirmation.
- `TC-002224.json`: TerminalID length 23 outside 13-22
- `TC-002274.json`: TerminalID length 0 outside 13-22
- `TC-002345.json`: TerminalID length 23 outside 13-22
- `TC-002395.json`: TerminalID length 0 outside 13-22

### QEF-021 — Segment Type matches the segment
- `TC-000267.json`: Moneris Data Segment (Request): SegmentType=100 expected 135; Enhanced Fleet Request: SegmentType=100 expected 145; Request InComm OTC Market Basket Data: SegmentType=100 expected 151; Network Token Data Request: SegmentType=100 expected 153; SKU Data Segment: SegmentType=100 expected 114
- `TC-000268.json`: Moneris Data Segment (Request): SegmentType=101 expected 135; Enhanced Fleet Request: SegmentType=101 expected 145; Request InComm OTC Market Basket Data: SegmentType=101 expected 151; Network Token Data Request: SegmentType=101 expected 153; SKU Data Segment: SegmentType=101 expected 114
- `TC-000269.json`: Moneris Data Segment (Request): SegmentType=102 expected 135; Enhanced Fleet Request: SegmentType=102 expected 145; Request InComm OTC Market Basket Data: SegmentType=102 expected 151; Network Token Data Request: SegmentType=102 expected 153; SKU Data Segment: SegmentType=102 expected 114
- `TC-000270.json`: Moneris Data Segment (Request): SegmentType=103 expected 135; Enhanced Fleet Request: SegmentType=103 expected 145; Request InComm OTC Market Basket Data: SegmentType=103 expected 151; Network Token Data Request: SegmentType=103 expected 153; SKU Data Segment: SegmentType=103 expected 114

### QEF-022 — Test data ID differs from test case ID
- `TC-000001.json`: test data identified as TC-000001; expected TD-nnnn or TD-TC-nnnn
- `TC-000002.json`: test data identified as TC-000002; expected TD-nnnn or TD-TC-nnnn
- `TC-000003.json`: test data identified as TC-000003; expected TD-nnnn or TD-TC-nnnn
- `TC-000004.json`: test data identified as TC-000004; expected TD-nnnn or TD-TC-nnnn

### QEF-023 — NumSegments name and count
- Source conflict (SME confirmation needed): ATL105 names Element 63 'Number of Segments'; NumSegments is the QE/ProvaMerchant naming convention.
- `TC-000001.json`: field named NumberofSegments, expected NumSegments
- `TC-000002.json`: field named NumberofSegments, expected NumSegments
- `TC-000003.json`: field named NumberofSegments, expected NumSegments
- `TC-000004.json`: field named NumberofSegments, expected NumSegments

### QEF-024 — Segment 123 has Token PAN Suffix
- Source conflict (SME confirmation needed): ATL105 2026-3 Element 197 says Token PAN Suffix is returned only for Visa/MasterCard/Interlink/Maestro tokenized transactions (response side).
- `TC-001127.json`: NFC Payment Tokenization Data Segment: TokenPANSuffix missing
- `TC-001128.json`: NFC Payment Tokenization Data Segment: TokenPANSuffix missing
- `TC-001129.json`: NFC Payment Tokenization Data Segment: TokenPANSuffix missing
- `TC-001130.json`: NFC Payment Tokenization Data Segment: TokenPANSuffix missing

### QEF-025 — CAVV Revised Format is alphanumeric data
- `TC-001127.json`: CAVVRevisedFormat is the literal word 'Position'
- `TC-001130.json`: CAVVRevisedFormat is the literal word 'Position'
- `TC-001131.json`: CAVVRevisedFormat is the literal word 'Position'
- `TC-001132.json`: CAVVRevisedFormat is the literal word 'Position'

### QEF-026 — Track Data combines Account Number and discretionary data
- Source conflict (SME confirmation needed): Keyed (non-track) entry and TransArmor token lengths differ; context-specific exceptions need SME confirmation.
- `TC-001127.json`: TrackData absent; separate AccountNumber field used
- `TC-001128.json`: TrackData absent; separate AccountNumber field used
- `TC-001129.json`: TrackData absent; separate AccountNumber field used
- `TC-001130.json`: TrackData absent; separate AccountNumber field used

### QEF-027 — Segment 111 indicator and information fields present
- Source conflict (SME confirmation needed): The QE reference request represents these as VarInfoTable TableID/Value entries; explicit fields are required per feedback.
- `TC-000001.json`: missing VariableInformationIndicator, VariableInformation
- `TC-000002.json`: missing VariableInformationIndicator, VariableInformation
- `TC-000003.json`: missing VariableInformationIndicator, VariableInformation
- `TC-000004.json`: missing VariableInformationIndicator, VariableInformation

### QEF-028 — No duplicated field aliases
- `TC-000001.json`: Variable Info Segment: duplicated alias VariableInformationLength2
- `TC-000002.json`: Variable Info Segment: duplicated alias VariableInformationLength2
- `TC-000003.json`: Variable Info Segment: duplicated alias VariableInformationLength2
- `TC-000004.json`: Variable Info Segment: duplicated alias VariableInformationLength2

### QEF-029 — Specification source set and version
- Source conflict (SME confirmation needed): Which release is 'current' and the addenda applicability require source-owner confirmation.
- `source-set`: source ids ['SRC-ATL105-PDF-001']: Dynamic Card Table Addendum 2014-1 and TransArmor Updates 2024-2 not ingested; no '2025-3' literal found in generator code or matrix

### QEF-030 — Financial request has 1-17 segments
- Source conflict (SME confirmation needed): The 17-segment maximum is QE feedback; ATL105 Element 63 allows two digits.
- `TC-001127.json`: 19 data segments serialized
- `TC-001128.json`: 19 data segments serialized
- `TC-001129.json`: 19 data segments serialized
- `TC-001130.json`: 19 data segments serialized

### QEF-031 — Prompt Code shape by message family
- Source conflict (SME confirmation needed): ATL105 Element 78 is 4 bytes and Element 182 lists 4-digit special prompt codes (e.g. 0981 Electronic Mail); the 3-character special form is QE feedback pending SME confirmation.
- `TC-000695.json`: Electronic Mail PromptCode={}; expected a single 3-character value
- `TC-000696.json`: Electronic Mail PromptCode={}; expected a single 3-character value
- `TC-000697.json`: Electronic Mail PromptCode={}; expected a single 3-character value
- `TC-000698.json`: Electronic Mail PromptCode={}; expected a single 3-character value

### QEF-032 — TransArmor requests carry expiration date

### QEF-033 — Readable review outputs
- `readable-outputs`: markdown files delivered: ['traceability_matrix.md']; 8-column manual table=no; per-TC cards=no; source-page hyperlinks=no

Readable outputs: [index](readable/README.md). Full findings: `findings.csv.gz`. Hashes: `manifest.json`.
