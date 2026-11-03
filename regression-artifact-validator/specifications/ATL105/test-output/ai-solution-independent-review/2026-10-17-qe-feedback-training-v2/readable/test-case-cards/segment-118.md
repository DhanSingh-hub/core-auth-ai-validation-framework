# Segment 118 — Test Case Cards

One card per test case (QE feedback Def_SepRun07/Def_SepRun10). Derived from the canonical JSON on every run; JSON stays authoritative. Fields flagged UNTRUSTED_VALID_VALUES, AMBIGUOUS_LENGTH or DELIBERATE_CONSTRAINT_VIOLATION are marked ⚠.

## TC-001867 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1891 (positive) → TC-001867 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0902 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 505792 | format_synthesis |  |
| 118 | Card Table Load Version | 051359 | format_synthesis |  |
| 118 | Card Table Type | 0001 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1891 → TC-001867 → `TC-001867.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001868 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1892 (positive) → TC-001868 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0904 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 488484 | format_synthesis |  |
| 118 | Card Table Load Version | 264799 | format_synthesis |  |
| 118 | Card Table Type | 0001 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1892 → TC-001868 → `TC-001868.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001869 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1893 (positive) → TC-001869 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 1 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0981 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 496445 | format_synthesis |  |
| 118 | Card Table Load Version | 748557 | format_synthesis |  |
| 118 | Card Table Type | 0001 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1893 → TC-001869 → `TC-001869.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001870 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1894 (positive) → TC-001870 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 494452 | format_synthesis |  |
| 118 | Card Table Load Version | 932597 | format_synthesis |  |
| 118 | Card Table Type | 0002 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1894 → TC-001870 → `TC-001870.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001871 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1895 (positive) → TC-001871 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 1 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0904 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 943629 | format_synthesis |  |
| 118 | Card Table Load Version | 056422 | format_synthesis |  |
| 118 | Card Table Type | 0002 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1895 → TC-001871 → `TC-001871.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001872 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1896 (positive) → TC-001872 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 1 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0981 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 029528 | format_synthesis |  |
| 118 | Card Table Load Version | 534676 | format_synthesis |  |
| 118 | Card Table Type | 0002 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1896 → TC-001872 → `TC-001872.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001873 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1897 (positive) → TC-001873 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 207488 | format_synthesis |  |
| 118 | Card Table Load Version | 124337 | format_synthesis |  |
| 118 | Card Table Type | 0003 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1897 → TC-001873 → `TC-001873.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001874 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1898 (positive) → TC-001874 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 1 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0902 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 266795 | format_synthesis |  |
| 118 | Card Table Load Version | 364832 | format_synthesis |  |
| 118 | Card Table Type | 0003 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1898 → TC-001874 → `TC-001874.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001875 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1899 (positive) → TC-001875 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0981 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 583391 | format_synthesis |  |
| 118 | Card Table Load Version | 169623 | format_synthesis |  |
| 118 | Card Table Type | 0003 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1899 → TC-001875 → `TC-001875.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001876 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1900 (positive) → TC-001876 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 1 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 074371 | format_synthesis |  |
| 118 | Card Table Load Version | 068522 | format_synthesis |  |
| 118 | Card Table Type | 0004 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1900 → TC-001876 → `TC-001876.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001877 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1901 (positive) → TC-001877 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0902 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 819598 | format_synthesis |  |
| 118 | Card Table Load Version | 054297 | format_synthesis |  |
| 118 | Card Table Type | 0004 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1901 → TC-001877 → `TC-001877.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001878 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1902 (positive) → TC-001878 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0904 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 493427 | format_synthesis |  |
| 118 | Card Table Load Version | 565718 | format_synthesis |  |
| 118 | Card Table Type | 0004 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1902 → TC-001878 → `TC-001878.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001879 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1903 (positive) → TC-001879 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 1 | scenario_parameter_literal |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0904 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 920172 | format_synthesis |  |
| 118 | Card Table Load Version | 027958 | format_synthesis |  |
| 118 | Card Table Type | 0005 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1903 → TC-001879 → `TC-001879.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001880 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1904 (positive) → TC-001880 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0981 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 079913 | format_synthesis |  |
| 118 | Card Table Load Version | 214461 | format_synthesis |  |
| 118 | Card Table Type | 0005 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1904 → TC-001880 → `TC-001880.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001881 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1905 (positive) → TC-001881 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0904 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 783107 | format_synthesis |  |
| 118 | Card Table Load Version | 123915 | format_synthesis |  |
| 118 | Card Table Type | 0006 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1905 → TC-001881 → `TC-001881.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-001882 — Positive — Proprietary Data Load Request — Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-1906 (positive) → TC-001882 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0981 | scenario_parameter_literal |  |
| 118 | Device Card Table Version | 596547 | format_synthesis |  |
| 118 | Card Table Load Version | 537047 | format_synthesis |  |
| 118 | Card Table Type | 0006 | scenario_parameter_literal |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 118 | Block Number | 000 | scenario_parameter_literal |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-1906 → TC-001882 → `TC-001882.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004034 — Negative — Proprietary Data Load Request — Card Table Type

**Purpose:** Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:198 → derivation_method = python_template → SC-4276 (negative, violated = Card Table Type) → TC-004034 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 867933 | format_synthesis |  |
| 118 | Card Table Load Version | 199349 | format_synthesis |  |
| 118 | Card Table Type | 000000 | deliberate_violation_enum | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1, decline code 2H (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:198](../traceability/segment-118.md) → SC-4276 → TC-004034 → `TC-004034.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004035 — Negative — Proprietary Data Load Request — Prompt Code, Pending

**Purpose:** Prompt Code, Pending must be one of: 0901 Custom Receipt Test Data, 0902 Proprietary Data Load, 0904 Host Discount Data, 0981 Electronic Mail. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:199 → derivation_method = python_template → SC-4277 (negative, violated = Prompt Code, Pending) → TC-004035 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | AAAAAA | deliberate_violation_enum | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Device Card Table Version | 279194 | format_synthesis |  |
| 118 | Card Table Load Version | 360547 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:199](../traceability/segment-118.md) → SC-4277 → TC-004035 → `TC-004035.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004050 — Negative — Proprietary Data Load Request — Segment Type

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3395 → derivation_method = field_constraint → SC-4292 (negative, violated = Segment Type) → TC-004050 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 188684 | format_synthesis |  |
| 118 | Card Table Load Version | 914600 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3395](../traceability/segment-118.md) → SC-4292 → TC-004050 → `TC-004050.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004051 — Negative — Proprietary Data Load Request — Segment Length

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segment Length) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-2 → REQ-SRC-ATL105-PDF-001:3397 → derivation_method = field_constraint → SC-4293 (negative, violated = Segment Length) → TC-004051 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Segment Length |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 580373 | format_synthesis |  |
| 118 | Card Table Load Version | 049260 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3397](../traceability/segment-118.md) → SC-4293 → TC-004051 → `TC-004051.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004052 — Negative — Proprietary Data Load Request — Sequence Number

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-3 → REQ-SRC-ATL105-PDF-001:3398 → derivation_method = field_constraint → SC-4294 (negative, violated = Sequence Number) → TC-004052 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 799729 | format_synthesis |  |
| 118 | Card Table Load Version | 481489 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3398](../traceability/segment-118.md) → SC-4294 → TC-004052 → `TC-004052.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004053 — Negative — Proprietary Data Load Request — Information Byte

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-4 → REQ-SRC-ATL105-PDF-001:3399 → derivation_method = field_constraint → SC-4295 (negative, violated = Information Byte) → TC-004053 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 679656 | format_synthesis |  |
| 118 | Card Table Load Version | 095933 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3399](../traceability/segment-118.md) → SC-4295 → TC-004053 → `TC-004053.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004054 — Negative — Proprietary Data Load Request — Terminal Identifier

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-5 → REQ-SRC-ATL105-PDF-001:3400 → derivation_method = field_constraint → SC-4296 (negative, violated = Terminal Identifier) → TC-004054 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 913356 | format_synthesis |  |
| 118 | Card Table Load Version | 449815 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3400](../traceability/segment-118.md) → SC-4296 → TC-004054 → `TC-004054.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004055 — Negative — Proprietary Data Load Request — Prompt Code

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-6 → REQ-SRC-ATL105-PDF-001:3401 → derivation_method = field_constraint → SC-4297 (negative, violated = Prompt Code) → TC-004055 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 410529 | format_synthesis |  |
| 118 | Card Table Load Version | 924238 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3401](../traceability/segment-118.md) → SC-4297 → TC-004055 → 

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004056 — Negative — Proprietary Data Load Request — Prompt Code, Pending

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 7 (Prompt Code, Pending) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-7 → REQ-SRC-ATL105-PDF-001:3402 → derivation_method = field_constraint → SC-4298 (negative, violated = Prompt Code, Pending) → TC-004056 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Device Card Table Version | 646556 | format_synthesis |  |
| 118 | Card Table Load Version | 200965 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3402](../traceability/segment-118.md) → SC-4298 → TC-004056 → `TC-004056.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004057 — Negative — Proprietary Data Load Request — Device Card Table Version

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Device Card Table Version) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-8 → REQ-SRC-ATL105-PDF-001:3403 → derivation_method = field_constraint → SC-4299 (negative, violated = Device Card Table Version) → TC-004057 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Card Table Load Version | 513825 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1, decline code 2H (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3403](../traceability/segment-118.md) → SC-4299 → TC-004057 → `TC-004057.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004058 — Negative — Proprietary Data Load Request — Card Table Load Version

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 9 (Card Table Load Version) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-9 → REQ-SRC-ATL105-PDF-001:3404 → derivation_method = field_constraint → SC-4300 (negative, violated = Card Table Load Version) → TC-004058 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 290808 | format_synthesis |  |
| 118 | Card Table Load Version |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1, decline code 2H (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3404](../traceability/segment-118.md) → SC-4300 → TC-004058 → `TC-004058.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004059 — Negative — Proprietary Data Load Request — Card Table Type

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 10 (Card Table Type) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-10 → REQ-SRC-ATL105-PDF-001:3405 → derivation_method = field_constraint → SC-4301 (negative, violated = Card Table Type) → TC-004059 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 687778 | format_synthesis |  |
| 118 | Card Table Load Version | 559041 | format_synthesis |  |
| 118 | Card Table Type |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1, decline code 2H (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3405](../traceability/segment-118.md) → SC-4301 → TC-004059 → `TC-004059.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004060 — Negative — Proprietary Data Load Request — Load Control Key

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 11 (Load Control Key) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-11 → REQ-SRC-ATL105-PDF-001:3406 → derivation_method = field_constraint → SC-4302 (negative, violated = Load Control Key) → TC-004060 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 569388 | format_synthesis |  |
| 118 | Card Table Load Version | 908510 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3406](../traceability/segment-118.md) → SC-4302 → TC-004060 → `TC-004060.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004061 — Negative — Proprietary Data Load Request — Host Discount Timestamp

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Host Discount Timestamp) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-12 → REQ-SRC-ATL105-PDF-001:3407 → derivation_method = field_constraint → SC-4303 (negative, violated = Host Discount Timestamp) → TC-004061 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 272470 | format_synthesis |  |
| 118 | Card Table Load Version | 245456 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3407](../traceability/segment-118.md) → SC-4303 → TC-004061 → `TC-004061.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004062 — Negative — Proprietary Data Load Request — Segment Type

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) must equal the fixed value 118. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3396 → derivation_method = field_fixed_value → SC-4304 (negative, violated = Segment Type) → TC-004062 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 119 | deliberate_violation_wrong_fixed_value | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 904735 | format_synthesis |  |
| 118 | Card Table Load Version | 945986 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3396](../traceability/segment-118.md) → SC-4304 → TC-004062 → `TC-004062.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-006993 — Business_Rule — Proprietary Data Load Request — Proprietary Data Load Request contains Data Section No. 3 with Proprie

**Purpose:** Proprietary Data Load Request contains Data Section No. 3 with Proprietary Data Load Segment (Segment 118). (source [p.196](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=196))

**Derivation path:** Spec [p.196](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=196) (ATL105 2026-3) → BR-196-5 → REQ-SRC-ATL105-PDF-001:1107 → derivation_method = business_rule_derived → SC-6481 (business_rule) → TC-006993 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 165019 | format_synthesis |  |
| 118 | Card Table Load Version | 211033 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1107](../traceability/segment-118.md) → SC-6481 → TC-006993 → `TC-006993.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007021 — Business_Rule — Proprietary Data Load Request — All Proprietary Data Load Responses contain Data Section No. 3

**Purpose:** All Proprietary Data Load Responses contain Data Section No. 3 (source [p.198](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=198))

**Derivation path:** Spec [p.198](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=198) (ATL105 2026-3) → BR-198-2 → REQ-SRC-ATL105-PDF-001:1113 → derivation_method = business_rule_derived → SC-6487 (business_rule) → TC-007021 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 782897 | format_synthesis |  |
| 118 | Card Table Load Version | 670361 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1113](../traceability/segment-118.md) → SC-6487 → TC-007021 → `TC-007021.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007514 — Business_Rule — Proprietary Data Load Request — Dynamic Card Table, Custom Receipt Text, and Host Discounts are pushed

**Purpose:** Dynamic Card Table, Custom Receipt Text, and Host Discounts are pushed by host to POS. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → BR-251-1 → REQ-SRC-ATL105-PDF-001:1272 → derivation_method = business_rule_derived → SC-6646 (business_rule) → TC-007514 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 808413 | format_synthesis |  |
| 118 | Card Table Load Version | 351590 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1272](../traceability/segment-118.md) → SC-6646 → TC-007514 → `TC-007514.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007515 — Business_Rule — Proprietary Data Load Request — Site Configuration data and Fuel Volume data are driven by the POS in 

**Purpose:** Site Configuration data and Fuel Volume data are driven by the POS in a data push to the host. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → BR-251-2 → REQ-SRC-ATL105-PDF-001:1273 → derivation_method = business_rule_derived → SC-6647 (business_rule) → TC-007515 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 586175 | format_synthesis |  |
| 118 | Card Table Load Version | 522328 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1273](../traceability/segment-118.md) → SC-6647 → TC-007515 → `TC-007515.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007516 — Business_Rule — Proprietary Data Load Request — Segment 118 has a maximum length of 3,800 alphanumeric characters in a

**Purpose:** Segment 118 has a maximum length of 3,800 alphanumeric characters in a Request. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → BR-251-3 → REQ-SRC-ATL105-PDF-001:1274 → derivation_method = business_rule_derived → SC-6648 (business_rule) → TC-007516 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 524888 | format_synthesis |  |
| 118 | Card Table Load Version | 807623 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1274](../traceability/segment-118.md) → SC-6648 → TC-007516 → `TC-007516.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007517 — Business_Rule — Proprietary Data Load Request — Segment 118 has a maximum length of 3,800 alphanumeric characters in a

**Purpose:** Segment 118 has a maximum length of 3,800 alphanumeric characters in a Response. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → BR-251-4 → REQ-SRC-ATL105-PDF-001:1275 → derivation_method = business_rule_derived → SC-6649 (business_rule) → TC-007517 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 873483 | format_synthesis |  |
| 118 | Card Table Load Version | 759785 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1275](../traceability/segment-118.md) → SC-6649 → TC-007517 → `TC-007517.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007518 — Business_Rule — Proprietary Data Load Request — For transaction requests, all fields are separated by Field Separators

**Purpose:** For transaction requests, all fields are separated by Field Separators; unpopulated fields still send the Field Separator. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → BR-251-5 → REQ-SRC-ATL105-PDF-001:1276 → derivation_method = business_rule_derived → SC-6650 (business_rule) → TC-007518 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 101957 | format_synthesis |  |
| 118 | Card Table Load Version | 372247 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1276](../traceability/segment-118.md) → SC-6650 → TC-007518 → `TC-007518.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007519 — Business_Rule — Proprietary Data Load Request — A Field Separator follows Field No. 13.

**Purpose:** A Field Separator follows Field No. 13. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → BR-251-6 → REQ-SRC-ATL105-PDF-001:1277 → derivation_method = business_rule_derived → SC-6651 (business_rule) → TC-007519 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 070061 | format_synthesis |  |
| 118 | Card Table Load Version | 697305 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1277](../traceability/segment-118.md) → SC-6651 → TC-007519 → `TC-007519.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007520 — Business_Rule — Proprietary Data Load Request — For transaction responses, there are no Field Separators between field

**Purpose:** For transaction responses, there are no Field Separators between fields in this message. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → BR-251-7 → REQ-SRC-ATL105-PDF-001:1278 → derivation_method = business_rule_derived → SC-6652 (business_rule) → TC-007520 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 777973 | format_synthesis |  |
| 118 | Card Table Load Version | 405724 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1278](../traceability/segment-118.md) → SC-6652 → TC-007520 → `TC-007520.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007527 — Business_Rule — Proprietary Data Load Request — Prompt Code, Pending valid values are 0901, 0902, 0904, 0981.

**Purpose:** Prompt Code, Pending valid values are 0901, 0902, 0904, 0981. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-2 → REQ-SRC-ATL105-PDF-001:1280 → derivation_method = business_rule_derived → SC-6654 (business_rule) → TC-007527 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 418381 | format_synthesis |  |
| 118 | Card Table Load Version | 494518 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1280](../traceability/segment-118.md) → SC-6654 → TC-007527 → `TC-007527.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007528 — Business_Rule — Financial Transaction Request — Host Discount Timestamp format is CCYYMMDDHHMM.

**Purpose:** Host Discount Timestamp format is CCYYMMDDHHMM. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-3 → REQ-SRC-ATL105-PDF-001:1281 → derivation_method = business_rule_derived → SC-6655 (business_rule) → TC-007528 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 086676 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 131959 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 10242127 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 582QV0X6 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | ZM33RO1D | format_synthesis |  |
| 119 | Firmware Version | IMLL80DH | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 169479 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | 582QV0X6 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | ZM33RO1D | format_synthesis |  |
| 132 | Firmware Version | IMLL80DH | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:1281](../traceability/segment-118.md) → SC-6655 → TC-007528 → `TC-007528.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007529 — Business_Rule — Proprietary Data Load Request — Host Discount Timestamp format is CCYYMMDDHHMM.

**Purpose:** Host Discount Timestamp format is CCYYMMDDHHMM. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-3 → REQ-SRC-ATL105-PDF-001:1281 → derivation_method = business_rule_derived → SC-6655 (business_rule) → TC-007529 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 055065 | format_synthesis |  |
| 118 | Card Table Load Version | 580135 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1281](../traceability/segment-118.md) → SC-6655 → TC-007529 → `TC-007529.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007530 — Business_Rule — Totals with Proprietary Data Load Request — Host Discount Timestamp format is CCYYMMDDHHMM.

**Purpose:** Host Discount Timestamp format is CCYYMMDDHHMM. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-3 → REQ-SRC-ATL105-PDF-001:1281 → derivation_method = business_rule_derived → SC-6655 (business_rule) → TC-007530 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | FS314SDO | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | UUGC60XC | format_synthesis |  |
| 119 | Firmware Version | AYA5QOFV | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 009704 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1281](../traceability/segment-118.md) → SC-6655 → TC-007530 → `TC-007530.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007531 — Business_Rule — Proprietary Data Load Request — Card Table Load Version value comes from Host and is echoed back in ea

**Purpose:** Card Table Load Version value comes from Host and is echoed back in each subsequent block request. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-4 → REQ-SRC-ATL105-PDF-001:1282 → derivation_method = business_rule_derived → SC-6656 (business_rule) → TC-007531 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 820845 | format_synthesis |  |
| 118 | Card Table Load Version | 248658 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1282](../traceability/segment-118.md) → SC-6656 → TC-007531 → `TC-007531.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007532 — Business_Rule — Proprietary Data Load Request — Card Table Type value comes from host and is echoed back in each subse

**Purpose:** Card Table Type value comes from host and is echoed back in each subsequent block request. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-5 → REQ-SRC-ATL105-PDF-001:1283 → derivation_method = business_rule_derived → SC-6657 (business_rule) → TC-007532 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 730391 | format_synthesis |  |
| 118 | Card Table Load Version | 956524 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1283](../traceability/segment-118.md) → SC-6657 → TC-007532 → `TC-007532.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007533 — Business_Rule — Proprietary Data Load Request — Load Control Key value comes from host and is echoed back in each subs

**Purpose:** Load Control Key value comes from host and is echoed back in each subsequent block request. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-6 → REQ-SRC-ATL105-PDF-001:1284 → derivation_method = business_rule_derived → SC-6658 (business_rule) → TC-007533 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 791895 | format_synthesis |  |
| 118 | Card Table Load Version | 587815 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1284](../traceability/segment-118.md) → SC-6658 → TC-007533 → `TC-007533.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007534 — Business_Rule — Financial Transaction Request — Device Card Table Version fields are each 5 characters individually, s

**Purpose:** Device Card Table Version fields are each 5 characters individually, strung together. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-7 → REQ-SRC-ATL105-PDF-001:1285 → derivation_method = business_rule_derived → SC-6659 (business_rule) → TC-007534 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 364743 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 818466 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 90379591 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | SLVHBKXS | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 1W432I2B | format_synthesis |  |
| 119 | Firmware Version | 1K8MUHVE | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 631418 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | SLVHBKXS | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | 1W432I2B | format_synthesis |  |
| 132 | Firmware Version | 1K8MUHVE | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:1285](../traceability/segment-118.md) → SC-6659 → TC-007534 → `TC-007534.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007535 — Business_Rule — Proprietary Data Load Request — Device Card Table Version fields are each 5 characters individually, s

**Purpose:** Device Card Table Version fields are each 5 characters individually, strung together. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-7 → REQ-SRC-ATL105-PDF-001:1285 → derivation_method = business_rule_derived → SC-6659 (business_rule) → TC-007535 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 032905 | format_synthesis |  |
| 118 | Card Table Load Version | 246969 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1285](../traceability/segment-118.md) → SC-6659 → TC-007535 → `TC-007535.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007536 — Business_Rule — Totals with Proprietary Data Load Request — Device Card Table Version fields are each 5 characters individually, s

**Purpose:** Device Card Table Version fields are each 5 characters individually, strung together. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → BR-252-7 → REQ-SRC-ATL105-PDF-001:1285 → derivation_method = business_rule_derived → SC-6659 (business_rule) → TC-007536 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | XP6W459P | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | L4SAERL7 | format_synthesis |  |
| 119 | Firmware Version | HV7QT46V | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 031741 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1285](../traceability/segment-118.md) → SC-6659 → TC-007536 → `TC-007536.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007555 — Business_Rule — Proprietary Data Load Request — Proprietary Load Request Data section included in addition to Field No

**Purpose:** Proprietary Load Request Data section included in addition to Field Nos. 1-13 in a Proprietary Data Load Request. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → BR-253-4 → REQ-SRC-ATL105-PDF-001:1289 → derivation_method = business_rule_derived → SC-6663 (business_rule) → TC-007555 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 908087 | format_synthesis |  |
| 118 | Card Table Load Version | 180589 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1289](../traceability/segment-118.md) → SC-6663 → TC-007555 → `TC-007555.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007556 — Business_Rule — Proprietary Data Load Request — Proprietary Load Response Data section included in addition to Field N

**Purpose:** Proprietary Load Response Data section included in addition to Field Nos. 1-13 in a Proprietary Data Load Response. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → BR-253-5 → REQ-SRC-ATL105-PDF-001:1290 → derivation_method = business_rule_derived → SC-6664 (business_rule) → TC-007556 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 743651 | format_synthesis |  |
| 118 | Card Table Load Version | 832147 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1290](../traceability/segment-118.md) → SC-6664 → TC-007556 → `TC-007556.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007581 — Business_Rule — Electronic Mail — A value of 0 in Block Number indicates all data was sent in previous b

**Purpose:** A value of 0 in Block Number indicates all data was sent in previous blocks and is sent as a final message with no data. (source [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255))

**Derivation path:** Spec [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255) (ATL105 2026-3) → BR-255-5 → REQ-SRC-ATL105-PDF-001:1298 → derivation_method = business_rule_derived → SC-6672 (business_rule) → TC-007581 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1298](../traceability/segment-118.md) → SC-6672 → TC-007581 → `TC-007581.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007582 — Business_Rule — Electronic Mail Request — A value of 0 in Block Number indicates all data was sent in previous b

**Purpose:** A value of 0 in Block Number indicates all data was sent in previous blocks and is sent as a final message with no data. (source [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255))

**Derivation path:** Spec [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255) (ATL105 2026-3) → BR-255-5 → REQ-SRC-ATL105-PDF-001:1298 → derivation_method = business_rule_derived → SC-6672 (business_rule) → TC-007582 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1298](../traceability/segment-118.md) → SC-6672 → TC-007582 → `TC-007582.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007583 — Business_Rule — Financial Transactions — A value of 0 in Block Number indicates all data was sent in previous b

**Purpose:** A value of 0 in Block Number indicates all data was sent in previous blocks and is sent as a final message with no data. (source [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255))

**Derivation path:** Spec [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255) (ATL105 2026-3) → BR-255-5 → REQ-SRC-ATL105-PDF-001:1298 → derivation_method = business_rule_derived → SC-6672 (business_rule) → TC-007583 (transaction = Financial Transactions)

**Preconditions:** transaction type Financial Transactions; message layout step 1 Financial Transactions segments 101, 104, 108, 109, 110

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 101 | Segment Type | 101 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 305474 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 458301 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 90051940 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1298](../traceability/segment-118.md) → SC-6672 → TC-007583 → `TC-007583.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007584 — Business_Rule — Mail Request — A value of 0 in Block Number indicates all data was sent in previous b

**Purpose:** A value of 0 in Block Number indicates all data was sent in previous blocks and is sent as a final message with no data. (source [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255))

**Derivation path:** Spec [p.255](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=255) (ATL105 2026-3) → BR-255-5 → REQ-SRC-ATL105-PDF-001:1298 → derivation_method = business_rule_derived → SC-6672 (business_rule) → TC-007584 (transaction = Mail Request)

**Preconditions:** transaction type Mail Request; message layout step 1 Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1298](../traceability/segment-118.md) → SC-6672 → TC-007584 → `TC-007584.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008158 — Business_Rule — ECA/TeleCheck Service Transaction — Card Table Type appears when Prompt Code 902 is returned.

**Purpose:** Card Table Type appears when Prompt Code 902 is returned. (source [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337))

**Derivation path:** Spec [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337) (ATL105 2026-3) → BR-337-2 → REQ-SRC-ATL105-PDF-001:1624 → derivation_method = business_rule_derived → SC-6998 (business_rule) → TC-008158 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:1624](../traceability/segment-118.md) → SC-6998 → TC-008158 → `TC-008158.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008159 — Business_Rule — ECA/TeleCheck Service Transaction Request — Card Table Type appears when Prompt Code 902 is returned.

**Purpose:** Card Table Type appears when Prompt Code 902 is returned. (source [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337))

**Derivation path:** Spec [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337) (ATL105 2026-3) → BR-337-2 → REQ-SRC-ATL105-PDF-001:1624 → derivation_method = business_rule_derived → SC-6998 (business_rule) → TC-008159 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:1624](../traceability/segment-118.md) → SC-6998 → TC-008159 → `TC-008159.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008160 — Business_Rule — EMV Financial Transaction Request — Card Table Type appears when Prompt Code 902 is returned.

**Purpose:** Card Table Type appears when Prompt Code 902 is returned. (source [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337))

**Derivation path:** Spec [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337) (ATL105 2026-3) → BR-337-2 → REQ-SRC-ATL105-PDF-001:1624 → derivation_method = business_rule_derived → SC-6998 (business_rule) → TC-008160 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 058434 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 966532 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 04891912 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:1624](../traceability/segment-118.md) → SC-6998 → TC-008160 → `TC-008160.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008161 — Business_Rule — EMV Request Transaction — Card Table Type appears when Prompt Code 902 is returned.

**Purpose:** Card Table Type appears when Prompt Code 902 is returned. (source [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337))

**Derivation path:** Spec [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337) (ATL105 2026-3) → BR-337-2 → REQ-SRC-ATL105-PDF-001:1624 → derivation_method = business_rule_derived → SC-6998 (business_rule) → TC-008161 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 513069 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 752827 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 43224843 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:1624](../traceability/segment-118.md) → SC-6998 → TC-008161 → `TC-008161.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008162 — Business_Rule — Electronic Mail — Card Table Type appears when Prompt Code 902 is returned.

**Purpose:** Card Table Type appears when Prompt Code 902 is returned. (source [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337))

**Derivation path:** Spec [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337) (ATL105 2026-3) → BR-337-2 → REQ-SRC-ATL105-PDF-001:1624 → derivation_method = business_rule_derived → SC-6998 (business_rule) → TC-008162 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1624](../traceability/segment-118.md) → SC-6998 → TC-008162 → `TC-008162.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008163 — Business_Rule — Electronic Mail Request — Card Table Type appears when Prompt Code 902 is returned.

**Purpose:** Card Table Type appears when Prompt Code 902 is returned. (source [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337))

**Derivation path:** Spec [p.337](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=337) (ATL105 2026-3) → BR-337-2 → REQ-SRC-ATL105-PDF-001:1624 → derivation_method = business_rule_derived → SC-6998 (business_rule) → TC-008163 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1624](../traceability/segment-118.md) → SC-6998 → TC-008163 → `TC-008163.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008208 — Business_Rule — Electronic Mail — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-1 → REQ-SRC-ATL105-PDF-001:1633 → derivation_method = business_rule_derived → SC-7007 (business_rule) → TC-008208 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1633](../traceability/segment-118.md) → SC-7007 → TC-008208 → `TC-008208.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008209 — Business_Rule — Electronic Mail Request — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-1 → REQ-SRC-ATL105-PDF-001:1633 → derivation_method = business_rule_derived → SC-7007 (business_rule) → TC-008209 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1633](../traceability/segment-118.md) → SC-7007 → TC-008209 → `TC-008209.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008210 — Business_Rule — Financial Transactions — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-1 → REQ-SRC-ATL105-PDF-001:1633 → derivation_method = business_rule_derived → SC-7007 (business_rule) → TC-008210 (transaction = Financial Transactions)

**Preconditions:** transaction type Financial Transactions; message layout step 1 Financial Transactions segments 101, 104, 108, 109, 110

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 101 | Segment Type | 101 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 396675 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 066683 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 45472263 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1633](../traceability/segment-118.md) → SC-7007 → TC-008210 → `TC-008210.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008211 — Business_Rule — Mail Request — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-1 → REQ-SRC-ATL105-PDF-001:1633 → derivation_method = business_rule_derived → SC-7007 (business_rule) → TC-008211 (transaction = Mail Request)

**Preconditions:** transaction type Mail Request; message layout step 1 Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1633](../traceability/segment-118.md) → SC-7007 → TC-008211 → `TC-008211.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008212 — Business_Rule — Proprietary Data Load Request — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-1 → REQ-SRC-ATL105-PDF-001:1633 → derivation_method = business_rule_derived → SC-7007 (business_rule) → TC-008212 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 018892 | format_synthesis |  |
| 118 | Card Table Load Version | 280214 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1633](../traceability/segment-118.md) → SC-7007 → TC-008212 → `TC-008212.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008213 — Business_Rule — Electronic Mail — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-2 → REQ-SRC-ATL105-PDF-001:1634 → derivation_method = business_rule_derived → SC-7008 (business_rule) → TC-008213 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1634](../traceability/segment-118.md) → SC-7008 → TC-008213 → `TC-008213.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008214 — Business_Rule — Electronic Mail Request — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-2 → REQ-SRC-ATL105-PDF-001:1634 → derivation_method = business_rule_derived → SC-7008 (business_rule) → TC-008214 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1634](../traceability/segment-118.md) → SC-7008 → TC-008214 → `TC-008214.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008215 — Business_Rule — Financial Transactions — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-2 → REQ-SRC-ATL105-PDF-001:1634 → derivation_method = business_rule_derived → SC-7008 (business_rule) → TC-008215 (transaction = Financial Transactions)

**Preconditions:** transaction type Financial Transactions; message layout step 1 Financial Transactions segments 101, 104, 108, 109, 110

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 101 | Segment Type | 101 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 005279 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 248785 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 50877452 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1634](../traceability/segment-118.md) → SC-7008 → TC-008215 → `TC-008215.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008216 — Business_Rule — Mail Request — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-2 → REQ-SRC-ATL105-PDF-001:1634 → derivation_method = business_rule_derived → SC-7008 (business_rule) → TC-008216 (transaction = Mail Request)

**Preconditions:** transaction type Mail Request; message layout step 1 Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1634](../traceability/segment-118.md) → SC-7008 → TC-008216 → `TC-008216.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-008217 — Business_Rule — Proprietary Data Load Request — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned. (source [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347))

**Derivation path:** Spec [p.347](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=347) (ATL105 2026-3) → BR-347-2 → REQ-SRC-ATL105-PDF-001:1634 → derivation_method = business_rule_derived → SC-7008 (business_rule) → TC-008217 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 414785 | format_synthesis |  |
| 118 | Card Table Load Version | 013376 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1634](../traceability/segment-118.md) → SC-7008 → TC-008217 → `TC-008217.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-009181 — Business_Rule — Proprietary Data Load Request — Proprietary Data Load Segment (No. 118) data length ranges 0001-3800.

**Purpose:** Proprietary Data Load Segment (No. 118) data length ranges 0001-3800. (source [p.400](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=400))

**Derivation path:** Spec [p.400](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=400) (ATL105 2026-3) → BR-400-16 → REQ-SRC-ATL105-PDF-001:1925 → derivation_method = business_rule_derived → SC-7299 (business_rule) → TC-009181 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 713269 | format_synthesis |  |
| 118 | Card Table Load Version | 144143 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1925](../traceability/segment-118.md) → SC-7299 → TC-009181 → `TC-009181.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010122 — Business_Rule — Proprietary Data Load Request — Element 165 appears in Proprietary Data Load Segment (Segment No. 118)

**Purpose:** Element 165 appears in Proprietary Data Load Segment (Segment No. 118). (source [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460))

**Derivation path:** Spec [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460) (ATL105 2026-3) → BR-460-1 → REQ-SRC-ATL105-PDF-001:2224 → derivation_method = business_rule_derived → SC-7598 (business_rule) → TC-010122 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 265302 | format_synthesis |  |
| 118 | Card Table Load Version | 317363 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2224](../traceability/segment-118.md) → SC-7598 → TC-010122 → `TC-010122.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010126 — Business_Rule — Proprietary Data Load Request — Element 166 appears in Proprietary Data Load Segment (Segment No. 118)

**Purpose:** Element 166 appears in Proprietary Data Load Segment (Segment No. 118). (source [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460))

**Derivation path:** Spec [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460) (ATL105 2026-3) → BR-460-5 → REQ-SRC-ATL105-PDF-001:2228 → derivation_method = business_rule_derived → SC-7602 (business_rule) → TC-010126 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 780947 | format_synthesis |  |
| 118 | Card Table Load Version | 357513 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2228](../traceability/segment-118.md) → SC-7602 → TC-010126 → `TC-010126.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010127 — Business_Rule — ECA/TeleCheck Service Transaction — Number of Receipt Text Lines appears in Proprietary Data Load Segment 

**Purpose:** Number of Receipt Text Lines appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461))

**Derivation path:** Spec [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461) (ATL105 2026-3) → BR-461-1 → REQ-SRC-ATL105-PDF-001:2230 → derivation_method = business_rule_derived → SC-7604 (business_rule) → TC-010127 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2230](../traceability/segment-118.md) → SC-7604 → TC-010127 → `TC-010127.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010128 — Business_Rule — ECA/TeleCheck Service Transaction Request — Number of Receipt Text Lines appears in Proprietary Data Load Segment 

**Purpose:** Number of Receipt Text Lines appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461))

**Derivation path:** Spec [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461) (ATL105 2026-3) → BR-461-1 → REQ-SRC-ATL105-PDF-001:2230 → derivation_method = business_rule_derived → SC-7604 (business_rule) → TC-010128 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2230](../traceability/segment-118.md) → SC-7604 → TC-010128 → `TC-010128.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010129 — Business_Rule — EMV Financial Transaction Request — Number of Receipt Text Lines appears in Proprietary Data Load Segment 

**Purpose:** Number of Receipt Text Lines appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461))

**Derivation path:** Spec [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461) (ATL105 2026-3) → BR-461-1 → REQ-SRC-ATL105-PDF-001:2230 → derivation_method = business_rule_derived → SC-7604 (business_rule) → TC-010129 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 176132 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 542790 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 09843225 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2230](../traceability/segment-118.md) → SC-7604 → TC-010129 → `TC-010129.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010130 — Business_Rule — EMV Request Transaction — Number of Receipt Text Lines appears in Proprietary Data Load Segment 

**Purpose:** Number of Receipt Text Lines appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461))

**Derivation path:** Spec [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461) (ATL105 2026-3) → BR-461-1 → REQ-SRC-ATL105-PDF-001:2230 → derivation_method = business_rule_derived → SC-7604 (business_rule) → TC-010130 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 463369 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 472846 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 93184975 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2230](../traceability/segment-118.md) → SC-7604 → TC-010130 → `TC-010130.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010131 — Business_Rule — Electronic Mail — Number of Receipt Text Lines appears in Proprietary Data Load Segment 

**Purpose:** Number of Receipt Text Lines appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461))

**Derivation path:** Spec [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461) (ATL105 2026-3) → BR-461-1 → REQ-SRC-ATL105-PDF-001:2230 → derivation_method = business_rule_derived → SC-7604 (business_rule) → TC-010131 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2230](../traceability/segment-118.md) → SC-7604 → TC-010131 → `TC-010131.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010132 — Business_Rule — Electronic Mail Request — Number of Receipt Text Lines appears in Proprietary Data Load Segment 

**Purpose:** Number of Receipt Text Lines appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461))

**Derivation path:** Spec [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461) (ATL105 2026-3) → BR-461-1 → REQ-SRC-ATL105-PDF-001:2230 → derivation_method = business_rule_derived → SC-7604 (business_rule) → TC-010132 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2230](../traceability/segment-118.md) → SC-7604 → TC-010132 → `TC-010132.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010134 — Business_Rule — ECA/TeleCheck Service Transaction — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-1 → REQ-SRC-ATL105-PDF-001:2232 → derivation_method = business_rule_derived → SC-7606 (business_rule) → TC-010134 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2232](../traceability/segment-118.md) → SC-7606 → TC-010134 → `TC-010134.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010135 — Business_Rule — ECA/TeleCheck Service Transaction Request — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-1 → REQ-SRC-ATL105-PDF-001:2232 → derivation_method = business_rule_derived → SC-7606 (business_rule) → TC-010135 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2232](../traceability/segment-118.md) → SC-7606 → TC-010135 → `TC-010135.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010136 — Business_Rule — EMV Financial Transaction Request — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-1 → REQ-SRC-ATL105-PDF-001:2232 → derivation_method = business_rule_derived → SC-7606 (business_rule) → TC-010136 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 802419 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 321062 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 67939042 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2232](../traceability/segment-118.md) → SC-7606 → TC-010136 → `TC-010136.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010137 — Business_Rule — EMV Request Transaction — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-1 → REQ-SRC-ATL105-PDF-001:2232 → derivation_method = business_rule_derived → SC-7606 (business_rule) → TC-010137 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 471670 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 130001 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 63417697 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2232](../traceability/segment-118.md) → SC-7606 → TC-010137 → `TC-010137.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010138 — Business_Rule — Electronic Mail — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-1 → REQ-SRC-ATL105-PDF-001:2232 → derivation_method = business_rule_derived → SC-7606 (business_rule) → TC-010138 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2232](../traceability/segment-118.md) → SC-7606 → TC-010138 → `TC-010138.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010139 — Business_Rule — Electronic Mail Request — Receipt Text Data Length appears in Proprietary Data Load Segment when

**Purpose:** Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-1 → REQ-SRC-ATL105-PDF-001:2232 → derivation_method = business_rule_derived → SC-7606 (business_rule) → TC-010139 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2232](../traceability/segment-118.md) → SC-7606 → TC-010139 → `TC-010139.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010145 — Business_Rule — ECA/TeleCheck Service Transaction — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-3 → REQ-SRC-ATL105-PDF-001:2234 → derivation_method = business_rule_derived → SC-7608 (business_rule) → TC-010145 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2234](../traceability/segment-118.md) → SC-7608 → TC-010145 → `TC-010145.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010146 — Business_Rule — ECA/TeleCheck Service Transaction Request — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-3 → REQ-SRC-ATL105-PDF-001:2234 → derivation_method = business_rule_derived → SC-7608 (business_rule) → TC-010146 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2234](../traceability/segment-118.md) → SC-7608 → TC-010146 → `TC-010146.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010147 — Business_Rule — EMV Financial Transaction Request — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-3 → REQ-SRC-ATL105-PDF-001:2234 → derivation_method = business_rule_derived → SC-7608 (business_rule) → TC-010147 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 805556 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 648965 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 77919922 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2234](../traceability/segment-118.md) → SC-7608 → TC-010147 → `TC-010147.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010148 — Business_Rule — EMV Request Transaction — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-3 → REQ-SRC-ATL105-PDF-001:2234 → derivation_method = business_rule_derived → SC-7608 (business_rule) → TC-010148 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 795778 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 504114 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 79302371 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2234](../traceability/segment-118.md) → SC-7608 → TC-010148 → `TC-010148.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010149 — Business_Rule — Electronic Mail — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-3 → REQ-SRC-ATL105-PDF-001:2234 → derivation_method = business_rule_derived → SC-7608 (business_rule) → TC-010149 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2234](../traceability/segment-118.md) → SC-7608 → TC-010149 → `TC-010149.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010150 — Business_Rule — Electronic Mail Request — Receipt Text Data appears in Proprietary Data Load Segment when Prompt

**Purpose:** Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-3 → REQ-SRC-ATL105-PDF-001:2234 → derivation_method = business_rule_derived → SC-7608 (business_rule) → TC-010150 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2234](../traceability/segment-118.md) → SC-7608 → TC-010150 → `TC-010150.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010151 — Business_Rule — ECA/TeleCheck Service Transaction — Number of Discounts appears in Proprietary Data Load Segment when Prom

**Purpose:** Number of Discounts appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-4 → REQ-SRC-ATL105-PDF-001:2235 → derivation_method = business_rule_derived → SC-7609 (business_rule) → TC-010151 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2235](../traceability/segment-118.md) → SC-7609 → TC-010151 → `TC-010151.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010152 — Business_Rule — ECA/TeleCheck Service Transaction Request — Number of Discounts appears in Proprietary Data Load Segment when Prom

**Purpose:** Number of Discounts appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-4 → REQ-SRC-ATL105-PDF-001:2235 → derivation_method = business_rule_derived → SC-7609 (business_rule) → TC-010152 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2235](../traceability/segment-118.md) → SC-7609 → TC-010152 → `TC-010152.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010153 — Business_Rule — EMV Financial Transaction Request — Number of Discounts appears in Proprietary Data Load Segment when Prom

**Purpose:** Number of Discounts appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-4 → REQ-SRC-ATL105-PDF-001:2235 → derivation_method = business_rule_derived → SC-7609 (business_rule) → TC-010153 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 156089 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 776546 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 14682016 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2235](../traceability/segment-118.md) → SC-7609 → TC-010153 → `TC-010153.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010154 — Business_Rule — EMV Request Transaction — Number of Discounts appears in Proprietary Data Load Segment when Prom

**Purpose:** Number of Discounts appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-4 → REQ-SRC-ATL105-PDF-001:2235 → derivation_method = business_rule_derived → SC-7609 (business_rule) → TC-010154 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 365694 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 642488 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 11808287 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2235](../traceability/segment-118.md) → SC-7609 → TC-010154 → `TC-010154.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010155 — Business_Rule — Electronic Mail — Number of Discounts appears in Proprietary Data Load Segment when Prom

**Purpose:** Number of Discounts appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-4 → REQ-SRC-ATL105-PDF-001:2235 → derivation_method = business_rule_derived → SC-7609 (business_rule) → TC-010155 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2235](../traceability/segment-118.md) → SC-7609 → TC-010155 → `TC-010155.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010156 — Business_Rule — Electronic Mail Request — Number of Discounts appears in Proprietary Data Load Segment when Prom

**Purpose:** Number of Discounts appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → BR-462-4 → REQ-SRC-ATL105-PDF-001:2235 → derivation_method = business_rule_derived → SC-7609 (business_rule) → TC-010156 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2235](../traceability/segment-118.md) → SC-7609 → TC-010156 → `TC-010156.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010162 — Business_Rule — ECA/TeleCheck Service Transaction — Product Discount Amount appears in Proprietary Data Load Segment when 

**Purpose:** Product Discount Amount appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-1 → REQ-SRC-ATL105-PDF-001:2237 → derivation_method = business_rule_derived → SC-7611 (business_rule) → TC-010162 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2237](../traceability/segment-118.md) → SC-7611 → TC-010162 → `TC-010162.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010163 — Business_Rule — ECA/TeleCheck Service Transaction Request — Product Discount Amount appears in Proprietary Data Load Segment when 

**Purpose:** Product Discount Amount appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-1 → REQ-SRC-ATL105-PDF-001:2237 → derivation_method = business_rule_derived → SC-7611 (business_rule) → TC-010163 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2237](../traceability/segment-118.md) → SC-7611 → TC-010163 → `TC-010163.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010164 — Business_Rule — EMV Financial Transaction Request — Product Discount Amount appears in Proprietary Data Load Segment when 

**Purpose:** Product Discount Amount appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-1 → REQ-SRC-ATL105-PDF-001:2237 → derivation_method = business_rule_derived → SC-7611 (business_rule) → TC-010164 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 355020 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 866339 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 75360802 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2237](../traceability/segment-118.md) → SC-7611 → TC-010164 → `TC-010164.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010165 — Business_Rule — EMV Request Transaction — Product Discount Amount appears in Proprietary Data Load Segment when 

**Purpose:** Product Discount Amount appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-1 → REQ-SRC-ATL105-PDF-001:2237 → derivation_method = business_rule_derived → SC-7611 (business_rule) → TC-010165 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 288735 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 412366 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 54942782 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2237](../traceability/segment-118.md) → SC-7611 → TC-010165 → `TC-010165.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010166 — Business_Rule — Electronic Mail — Product Discount Amount appears in Proprietary Data Load Segment when 

**Purpose:** Product Discount Amount appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-1 → REQ-SRC-ATL105-PDF-001:2237 → derivation_method = business_rule_derived → SC-7611 (business_rule) → TC-010166 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2237](../traceability/segment-118.md) → SC-7611 → TC-010166 → `TC-010166.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010167 — Business_Rule — Electronic Mail Request — Product Discount Amount appears in Proprietary Data Load Segment when 

**Purpose:** Product Discount Amount appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-1 → REQ-SRC-ATL105-PDF-001:2237 → derivation_method = business_rule_derived → SC-7611 (business_rule) → TC-010167 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2237](../traceability/segment-118.md) → SC-7611 → TC-010167 → `TC-010167.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010169 — Business_Rule — ECA/TeleCheck Service Transaction — BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt

**Purpose:** BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-3 → REQ-SRC-ATL105-PDF-001:2239 → derivation_method = business_rule_derived → SC-7613 (business_rule) → TC-010169 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2239](../traceability/segment-118.md) → SC-7613 → TC-010169 → `TC-010169.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010170 — Business_Rule — ECA/TeleCheck Service Transaction Request — BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt

**Purpose:** BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-3 → REQ-SRC-ATL105-PDF-001:2239 → derivation_method = business_rule_derived → SC-7613 (business_rule) → TC-010170 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2239](../traceability/segment-118.md) → SC-7613 → TC-010170 → `TC-010170.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010171 — Business_Rule — EMV Financial Transaction Request — BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt

**Purpose:** BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-3 → REQ-SRC-ATL105-PDF-001:2239 → derivation_method = business_rule_derived → SC-7613 (business_rule) → TC-010171 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 153510 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 161955 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 57847550 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2239](../traceability/segment-118.md) → SC-7613 → TC-010171 → `TC-010171.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010172 — Business_Rule — EMV Request Transaction — BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt

**Purpose:** BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-3 → REQ-SRC-ATL105-PDF-001:2239 → derivation_method = business_rule_derived → SC-7613 (business_rule) → TC-010172 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 333882 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 427670 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 71744517 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2239](../traceability/segment-118.md) → SC-7613 → TC-010172 → `TC-010172.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010173 — Business_Rule — Electronic Mail — BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt

**Purpose:** BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-3 → REQ-SRC-ATL105-PDF-001:2239 → derivation_method = business_rule_derived → SC-7613 (business_rule) → TC-010173 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2239](../traceability/segment-118.md) → SC-7613 → TC-010173 → `TC-010173.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010174 — Business_Rule — Electronic Mail Request — BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt

**Purpose:** BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → BR-463-3 → REQ-SRC-ATL105-PDF-001:2239 → derivation_method = business_rule_derived → SC-7613 (business_rule) → TC-010174 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2239](../traceability/segment-118.md) → SC-7613 → TC-010174 → `TC-010174.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010176 — Business_Rule — ECA/TeleCheck Service Transaction — Card Table Type appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Type appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-1 → REQ-SRC-ATL105-PDF-001:2241 → derivation_method = business_rule_derived → SC-7615 (business_rule) → TC-010176 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2241](../traceability/segment-118.md) → SC-7615 → TC-010176 → `TC-010176.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010177 — Business_Rule — ECA/TeleCheck Service Transaction Request — Card Table Type appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Type appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-1 → REQ-SRC-ATL105-PDF-001:2241 → derivation_method = business_rule_derived → SC-7615 (business_rule) → TC-010177 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2241](../traceability/segment-118.md) → SC-7615 → TC-010177 → `TC-010177.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010178 — Business_Rule — EMV Financial Transaction Request — Card Table Type appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Type appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-1 → REQ-SRC-ATL105-PDF-001:2241 → derivation_method = business_rule_derived → SC-7615 (business_rule) → TC-010178 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 435315 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 155762 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 57557469 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2241](../traceability/segment-118.md) → SC-7615 → TC-010178 → `TC-010178.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010179 — Business_Rule — EMV Request Transaction — Card Table Type appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Type appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-1 → REQ-SRC-ATL105-PDF-001:2241 → derivation_method = business_rule_derived → SC-7615 (business_rule) → TC-010179 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 476225 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 525020 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 11785942 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2241](../traceability/segment-118.md) → SC-7615 → TC-010179 → `TC-010179.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010180 — Business_Rule — Electronic Mail — Card Table Type appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Type appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-1 → REQ-SRC-ATL105-PDF-001:2241 → derivation_method = business_rule_derived → SC-7615 (business_rule) → TC-010180 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2241](../traceability/segment-118.md) → SC-7615 → TC-010180 → `TC-010180.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010181 — Business_Rule — Electronic Mail Request — Card Table Type appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Type appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-1 → REQ-SRC-ATL105-PDF-001:2241 → derivation_method = business_rule_derived → SC-7615 (business_rule) → TC-010181 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2241](../traceability/segment-118.md) → SC-7615 → TC-010181 → `TC-010181.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010182 — Business_Rule — ECA/TeleCheck Service Transaction — Card Table Data appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Data appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-2 → REQ-SRC-ATL105-PDF-001:2242 → derivation_method = business_rule_derived → SC-7616 (business_rule) → TC-010182 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2242](../traceability/segment-118.md) → SC-7616 → TC-010182 → `TC-010182.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010183 — Business_Rule — ECA/TeleCheck Service Transaction Request — Card Table Data appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Data appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-2 → REQ-SRC-ATL105-PDF-001:2242 → derivation_method = business_rule_derived → SC-7616 (business_rule) → TC-010183 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2242](../traceability/segment-118.md) → SC-7616 → TC-010183 → `TC-010183.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010184 — Business_Rule — EMV Financial Transaction Request — Card Table Data appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Data appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-2 → REQ-SRC-ATL105-PDF-001:2242 → derivation_method = business_rule_derived → SC-7616 (business_rule) → TC-010184 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 446276 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 985887 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 50900682 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2242](../traceability/segment-118.md) → SC-7616 → TC-010184 → `TC-010184.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010185 — Business_Rule — EMV Request Transaction — Card Table Data appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Data appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-2 → REQ-SRC-ATL105-PDF-001:2242 → derivation_method = business_rule_derived → SC-7616 (business_rule) → TC-010185 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 285500 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 317062 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 60827608 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2242](../traceability/segment-118.md) → SC-7616 → TC-010185 → `TC-010185.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010186 — Business_Rule — Electronic Mail — Card Table Data appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Data appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-2 → REQ-SRC-ATL105-PDF-001:2242 → derivation_method = business_rule_derived → SC-7616 (business_rule) → TC-010186 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2242](../traceability/segment-118.md) → SC-7616 → TC-010186 → `TC-010186.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010187 — Business_Rule — Electronic Mail Request — Card Table Data appears in Proprietary Data Load Segment when Prompt C

**Purpose:** Card Table Data appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-2 → REQ-SRC-ATL105-PDF-001:2242 → derivation_method = business_rule_derived → SC-7616 (business_rule) → TC-010187 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2242](../traceability/segment-118.md) → SC-7616 → TC-010187 → `TC-010187.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010188 — Business_Rule — Proprietary Data Load Request — POS Device uses Card Table Data to create/replace its Card Table infor

**Purpose:** POS Device uses Card Table Data to create/replace its Card Table information for the table type in Field 174. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → BR-464-3 → REQ-SRC-ATL105-PDF-001:2243 → derivation_method = business_rule_derived → SC-7617 (business_rule) → TC-010188 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 612400 | format_synthesis |  |
| 118 | Card Table Load Version | 247311 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2243](../traceability/segment-118.md) → SC-7617 → TC-010188 → `TC-010188.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010189 — Business_Rule — Financial Transaction Request — If Device Card Table Version begins with 99999, there is no card table

**Purpose:** If Device Card Table Version begins with 99999, there is no card table used by the location. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → BR-465-1 → REQ-SRC-ATL105-PDF-001:2244 → derivation_method = business_rule_derived → SC-7618 (business_rule) → TC-010189 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 169442 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 926703 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 64405178 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 707CZQ1H | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 8IJ7N0IK | format_synthesis |  |
| 119 | Firmware Version | 8YLTHVJB | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 718539 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | 707CZQ1H | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | 8IJ7N0IK | format_synthesis |  |
| 132 | Firmware Version | 8YLTHVJB | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2244](../traceability/segment-118.md) → SC-7618 → TC-010189 → `TC-010189.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010190 — Business_Rule — Proprietary Data Load Request — If Device Card Table Version begins with 99999, there is no card table

**Purpose:** If Device Card Table Version begins with 99999, there is no card table used by the location. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → BR-465-1 → REQ-SRC-ATL105-PDF-001:2244 → derivation_method = business_rule_derived → SC-7618 (business_rule) → TC-010190 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 622921 | format_synthesis |  |
| 118 | Card Table Load Version | 746465 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2244](../traceability/segment-118.md) → SC-7618 → TC-010190 → `TC-010190.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010191 — Business_Rule — Totals with Proprietary Data Load Request — If Device Card Table Version begins with 99999, there is no card table

**Purpose:** If Device Card Table Version begins with 99999, there is no card table used by the location. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → BR-465-1 → REQ-SRC-ATL105-PDF-001:2244 → derivation_method = business_rule_derived → SC-7618 (business_rule) → TC-010191 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | XOBKWJ22 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | TLVUOPF3 | format_synthesis |  |
| 119 | Firmware Version | 08TYK0GP | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 717880 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2244](../traceability/segment-118.md) → SC-7618 → TC-010191 → `TC-010191.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010192 — Business_Rule — Financial Transaction Request — Device Card Table Version appears in Proprietary Data Load Segment so 

**Purpose:** Device Card Table Version appears in Proprietary Data Load Segment so Host can check for card table updates. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → BR-465-2 → REQ-SRC-ATL105-PDF-001:2245 → derivation_method = business_rule_derived → SC-7619 (business_rule) → TC-010192 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 324304 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 261672 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 30172889 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | CZ31CQR0 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 1VFTOSD6 | format_synthesis |  |
| 119 | Firmware Version | J3C74I33 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 176736 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | CZ31CQR0 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | 1VFTOSD6 | format_synthesis |  |
| 132 | Firmware Version | J3C74I33 | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2245](../traceability/segment-118.md) → SC-7619 → TC-010192 → `TC-010192.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010193 — Business_Rule — Proprietary Data Load Request — Device Card Table Version appears in Proprietary Data Load Segment so 

**Purpose:** Device Card Table Version appears in Proprietary Data Load Segment so Host can check for card table updates. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → BR-465-2 → REQ-SRC-ATL105-PDF-001:2245 → derivation_method = business_rule_derived → SC-7619 (business_rule) → TC-010193 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 038878 | format_synthesis |  |
| 118 | Card Table Load Version | 182340 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2245](../traceability/segment-118.md) → SC-7619 → TC-010193 → `TC-010193.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010194 — Business_Rule — Totals with Proprietary Data Load Request — Device Card Table Version appears in Proprietary Data Load Segment so 

**Purpose:** Device Card Table Version appears in Proprietary Data Load Segment so Host can check for card table updates. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → BR-465-2 → REQ-SRC-ATL105-PDF-001:2245 → derivation_method = business_rule_derived → SC-7619 (business_rule) → TC-010194 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 5VVAOXZG | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | BQMYEZQ7 | format_synthesis |  |
| 119 | Firmware Version | 33EDT7UP | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 469214 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2245](../traceability/segment-118.md) → SC-7619 → TC-010194 → `TC-010194.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010195 — Business_Rule — Proprietary Data Load Request — Card Table Load Version appears in Proprietary Data Load Segment so Ho

**Purpose:** Card Table Load Version appears in Proprietary Data Load Segment so Host can retain operation in process in multi-block exchange. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → BR-465-3 → REQ-SRC-ATL105-PDF-001:2246 → derivation_method = business_rule_derived → SC-7620 (business_rule) → TC-010195 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 666755 | format_synthesis |  |
| 118 | Card Table Load Version | 189684 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2246](../traceability/segment-118.md) → SC-7620 → TC-010195 → `TC-010195.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010196 — Business_Rule — Financial Transaction Request — In Proprietary Load Request Data, Host Discount Timestamp represents e

**Purpose:** In Proprietary Load Request Data, Host Discount Timestamp represents existing host discount timestamp used by device. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → BR-466-1 → REQ-SRC-ATL105-PDF-001:2247 → derivation_method = business_rule_derived → SC-7621 (business_rule) → TC-010196 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 399962 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 177673 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 63066888 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | NE4UUAMR | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | JPOZ4EAD | format_synthesis |  |
| 119 | Firmware Version | I98I028V | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 885366 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | NE4UUAMR | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | JPOZ4EAD | format_synthesis |  |
| 132 | Firmware Version | I98I028V | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2247](../traceability/segment-118.md) → SC-7621 → TC-010196 → `TC-010196.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010197 — Business_Rule — Proprietary Data Load Request — In Proprietary Load Request Data, Host Discount Timestamp represents e

**Purpose:** In Proprietary Load Request Data, Host Discount Timestamp represents existing host discount timestamp used by device. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → BR-466-1 → REQ-SRC-ATL105-PDF-001:2247 → derivation_method = business_rule_derived → SC-7621 (business_rule) → TC-010197 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 885351 | format_synthesis |  |
| 118 | Card Table Load Version | 500622 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2247](../traceability/segment-118.md) → SC-7621 → TC-010197 → `TC-010197.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010198 — Business_Rule — Totals with Proprietary Data Load Request — In Proprietary Load Request Data, Host Discount Timestamp represents e

**Purpose:** In Proprietary Load Request Data, Host Discount Timestamp represents existing host discount timestamp used by device. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → BR-466-1 → REQ-SRC-ATL105-PDF-001:2247 → derivation_method = business_rule_derived → SC-7621 (business_rule) → TC-010198 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | OK4WNYDV | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | N2HW92LJ | format_synthesis |  |
| 119 | Firmware Version | L8H0Z8CV | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 917707 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2247](../traceability/segment-118.md) → SC-7621 → TC-010198 → `TC-010198.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010199 — Business_Rule — Financial Transaction Request — In Proprietary Load Response Data, Host Discount Timestamp represents 

**Purpose:** In Proprietary Load Response Data, Host Discount Timestamp represents new timestamp used after a successful load. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → BR-466-2 → REQ-SRC-ATL105-PDF-001:2248 → derivation_method = business_rule_derived → SC-7622 (business_rule) → TC-010199 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 063809 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 616478 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 51276423 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 5WSORHM2 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | NYCDYC1V | format_synthesis |  |
| 119 | Firmware Version | FHQ4MFPK | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 923567 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | 5WSORHM2 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | NYCDYC1V | format_synthesis |  |
| 132 | Firmware Version | FHQ4MFPK | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2248](../traceability/segment-118.md) → SC-7622 → TC-010199 → `TC-010199.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010200 — Business_Rule — Proprietary Data Load Request — In Proprietary Load Response Data, Host Discount Timestamp represents 

**Purpose:** In Proprietary Load Response Data, Host Discount Timestamp represents new timestamp used after a successful load. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → BR-466-2 → REQ-SRC-ATL105-PDF-001:2248 → derivation_method = business_rule_derived → SC-7622 (business_rule) → TC-010200 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 637071 | format_synthesis |  |
| 118 | Card Table Load Version | 868521 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2248](../traceability/segment-118.md) → SC-7622 → TC-010200 → `TC-010200.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010201 — Business_Rule — Totals with Proprietary Data Load Request — In Proprietary Load Response Data, Host Discount Timestamp represents 

**Purpose:** In Proprietary Load Response Data, Host Discount Timestamp represents new timestamp used after a successful load. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → BR-466-2 → REQ-SRC-ATL105-PDF-001:2248 → derivation_method = business_rule_derived → SC-7622 (business_rule) → TC-010201 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | VHVCPI3A | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | H9B7K4SU | format_synthesis |  |
| 119 | Firmware Version | MJ3C8KSC | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 634538 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2248](../traceability/segment-118.md) → SC-7622 → TC-010201 → `TC-010201.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010202 — Business_Rule — ECA/TeleCheck Service Transaction — Site Configuration Data appears in Proprietary Data Load Segment when 

**Purpose:** Site Configuration Data appears in Proprietary Data Load Segment when Prompt Code 903 is sent in the request. (source [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467))

**Derivation path:** Spec [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467) (ATL105 2026-3) → BR-467-1 → REQ-SRC-ATL105-PDF-001:2249 → derivation_method = business_rule_derived → SC-7623 (business_rule) → TC-010202 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2249](../traceability/segment-118.md) → SC-7623 → TC-010202 → `TC-010202.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010203 — Business_Rule — ECA/TeleCheck Service Transaction Request — Site Configuration Data appears in Proprietary Data Load Segment when 

**Purpose:** Site Configuration Data appears in Proprietary Data Load Segment when Prompt Code 903 is sent in the request. (source [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467))

**Derivation path:** Spec [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467) (ATL105 2026-3) → BR-467-1 → REQ-SRC-ATL105-PDF-001:2249 → derivation_method = business_rule_derived → SC-7623 (business_rule) → TC-010203 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2249](../traceability/segment-118.md) → SC-7623 → TC-010203 → `TC-010203.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010204 — Business_Rule — EMV Financial Transaction Request — Site Configuration Data appears in Proprietary Data Load Segment when 

**Purpose:** Site Configuration Data appears in Proprietary Data Load Segment when Prompt Code 903 is sent in the request. (source [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467))

**Derivation path:** Spec [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467) (ATL105 2026-3) → BR-467-1 → REQ-SRC-ATL105-PDF-001:2249 → derivation_method = business_rule_derived → SC-7623 (business_rule) → TC-010204 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 116641 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 030116 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 62252910 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2249](../traceability/segment-118.md) → SC-7623 → TC-010204 → `TC-010204.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010205 — Business_Rule — EMV Request Transaction — Site Configuration Data appears in Proprietary Data Load Segment when 

**Purpose:** Site Configuration Data appears in Proprietary Data Load Segment when Prompt Code 903 is sent in the request. (source [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467))

**Derivation path:** Spec [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467) (ATL105 2026-3) → BR-467-1 → REQ-SRC-ATL105-PDF-001:2249 → derivation_method = business_rule_derived → SC-7623 (business_rule) → TC-010205 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 862499 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 017540 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 45979994 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2249](../traceability/segment-118.md) → SC-7623 → TC-010205 → `TC-010205.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010206 — Business_Rule — Electronic Mail — Site Configuration Data appears in Proprietary Data Load Segment when 

**Purpose:** Site Configuration Data appears in Proprietary Data Load Segment when Prompt Code 903 is sent in the request. (source [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467))

**Derivation path:** Spec [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467) (ATL105 2026-3) → BR-467-1 → REQ-SRC-ATL105-PDF-001:2249 → derivation_method = business_rule_derived → SC-7623 (business_rule) → TC-010206 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2249](../traceability/segment-118.md) → SC-7623 → TC-010206 → `TC-010206.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010207 — Business_Rule — Electronic Mail Request — Site Configuration Data appears in Proprietary Data Load Segment when 

**Purpose:** Site Configuration Data appears in Proprietary Data Load Segment when Prompt Code 903 is sent in the request. (source [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467))

**Derivation path:** Spec [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467) (ATL105 2026-3) → BR-467-1 → REQ-SRC-ATL105-PDF-001:2249 → derivation_method = business_rule_derived → SC-7623 (business_rule) → TC-010207 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2249](../traceability/segment-118.md) → SC-7623 → TC-010207 → `TC-010207.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010210 — Business_Rule — Proprietary Data Load Request — Prompt Code, Pending must be a valid Transaction Type and/or Card Type

**Purpose:** Prompt Code, Pending must be a valid Transaction Type and/or Card Type Code. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → BR-468-1 → REQ-SRC-ATL105-PDF-001:2252 → derivation_method = business_rule_derived → SC-7626 (business_rule) → TC-010210 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 690499 | format_synthesis |  |
| 118 | Card Table Load Version | 998264 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2252](../traceability/segment-118.md) → SC-7626 → TC-010210 → `TC-010210.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010211 — Business_Rule — Proprietary Data Load Request — Device transmits a 4-character Prompt Code in case of a special transa

**Purpose:** Device transmits a 4-character Prompt Code in case of a special transaction. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → BR-468-2 → REQ-SRC-ATL105-PDF-001:2253 → derivation_method = business_rule_derived → SC-7627 (business_rule) → TC-010211 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 814439 | format_synthesis |  |
| 118 | Card Table Load Version | 160477 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2253](../traceability/segment-118.md) → SC-7627 → TC-010211 → `TC-010211.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010212 — Business_Rule — Proprietary Data Load Request — Card BIN Range, Beginning appears in Proprietary Load Response Data, P

**Purpose:** Card BIN Range, Beginning appears in Proprietary Load Response Data, Prompt Code 904 (Host Discount Data). (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → BR-468-3 → REQ-SRC-ATL105-PDF-001:2254 → derivation_method = business_rule_derived → SC-7628 (business_rule) → TC-010212 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 081752 | format_synthesis |  |
| 118 | Card Table Load Version | 162063 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2254](../traceability/segment-118.md) → SC-7628 → TC-010212 → `TC-010212.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010265 — Business_Rule — ECA/TeleCheck Service Transaction — Fuel Volume Data appears in Proprietary Data Load Segment (118) when P

**Purpose:** Fuel Volume Data appears in Proprietary Data Load Segment (118) when Prompt Code 905 is sent in the request. (source [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477))

**Derivation path:** Spec [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477) (ATL105 2026-3) → BR-477-3 → REQ-SRC-ATL105-PDF-001:2297 → derivation_method = business_rule_derived → SC-7671 (business_rule) → TC-010265 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2297](../traceability/segment-118.md) → SC-7671 → TC-010265 → `TC-010265.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010266 — Business_Rule — ECA/TeleCheck Service Transaction Request — Fuel Volume Data appears in Proprietary Data Load Segment (118) when P

**Purpose:** Fuel Volume Data appears in Proprietary Data Load Segment (118) when Prompt Code 905 is sent in the request. (source [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477))

**Derivation path:** Spec [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477) (ATL105 2026-3) → BR-477-3 → REQ-SRC-ATL105-PDF-001:2297 → derivation_method = business_rule_derived → SC-7671 (business_rule) → TC-010266 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2297](../traceability/segment-118.md) → SC-7671 → TC-010266 → `TC-010266.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010267 — Business_Rule — EMV Financial Transaction Request — Fuel Volume Data appears in Proprietary Data Load Segment (118) when P

**Purpose:** Fuel Volume Data appears in Proprietary Data Load Segment (118) when Prompt Code 905 is sent in the request. (source [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477))

**Derivation path:** Spec [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477) (ATL105 2026-3) → BR-477-3 → REQ-SRC-ATL105-PDF-001:2297 → derivation_method = business_rule_derived → SC-7671 (business_rule) → TC-010267 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 828574 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 609969 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 76958076 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2297](../traceability/segment-118.md) → SC-7671 → TC-010267 → `TC-010267.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010268 — Business_Rule — EMV Request Transaction — Fuel Volume Data appears in Proprietary Data Load Segment (118) when P

**Purpose:** Fuel Volume Data appears in Proprietary Data Load Segment (118) when Prompt Code 905 is sent in the request. (source [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477))

**Derivation path:** Spec [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477) (ATL105 2026-3) → BR-477-3 → REQ-SRC-ATL105-PDF-001:2297 → derivation_method = business_rule_derived → SC-7671 (business_rule) → TC-010268 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 814461 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 650276 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 69637484 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2297](../traceability/segment-118.md) → SC-7671 → TC-010268 → `TC-010268.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010269 — Business_Rule — Electronic Mail — Fuel Volume Data appears in Proprietary Data Load Segment (118) when P

**Purpose:** Fuel Volume Data appears in Proprietary Data Load Segment (118) when Prompt Code 905 is sent in the request. (source [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477))

**Derivation path:** Spec [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477) (ATL105 2026-3) → BR-477-3 → REQ-SRC-ATL105-PDF-001:2297 → derivation_method = business_rule_derived → SC-7671 (business_rule) → TC-010269 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2297](../traceability/segment-118.md) → SC-7671 → TC-010269 → `TC-010269.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010270 — Business_Rule — Electronic Mail Request — Fuel Volume Data appears in Proprietary Data Load Segment (118) when P

**Purpose:** Fuel Volume Data appears in Proprietary Data Load Segment (118) when Prompt Code 905 is sent in the request. (source [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477))

**Derivation path:** Spec [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477) (ATL105 2026-3) → BR-477-3 → REQ-SRC-ATL105-PDF-001:2297 → derivation_method = business_rule_derived → SC-7671 (business_rule) → TC-010270 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2297](../traceability/segment-118.md) → SC-7671 → TC-010270 → `TC-010270.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010538 — Business_Rule — Proprietary Data Load Request — Code 164 Auto Close feature closes the batch 30 minutes prior to the d

**Purpose:** Code 164 Auto Close feature closes the batch 30 minutes prior to the default cut time. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-1 → REQ-SRC-ATL105-PDF-001:2398 → derivation_method = business_rule_derived → SC-7772 (business_rule) → TC-010538 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 994731 | format_synthesis |  |
| 118 | Card Table Load Version | 099758 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2398](../traceability/segment-118.md) → SC-7772 → TC-010538 → `TC-010538.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010539 — Business_Rule — Proprietary Data Load Request — Code 150 instructs terminal to prompt for Debit Convenience Fee before

**Purpose:** Code 150 instructs terminal to prompt for Debit Convenience Fee before sending a debit transaction. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-2 → REQ-SRC-ATL105-PDF-001:2399 → derivation_method = business_rule_derived → SC-7773 (business_rule) → TC-010539 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 867539 | format_synthesis |  |
| 118 | Card Table Load Version | 583036 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2399](../traceability/segment-118.md) → SC-7773 → TC-010539 → `TC-010539.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010540 — Business_Rule — Proprietary Data Load Request — Code 162 configures device to prompt for cardholder's address for manu

**Purpose:** Code 162 configures device to prompt for cardholder's address for manually keyed credit card sales. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-3 → REQ-SRC-ATL105-PDF-001:2400 → derivation_method = business_rule_derived → SC-7774 (business_rule) → TC-010540 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 454832 | format_synthesis |  |
| 118 | Card Table Load Version | 475595 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2400](../traceability/segment-118.md) → SC-7774 → TC-010540 → `TC-010540.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010541 — Business_Rule — Proprietary Data Load Request — Code 163 configures device to prompt for cardholder's address and ZIP 

**Purpose:** Code 163 configures device to prompt for cardholder's address and ZIP Code/ZIP+4. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-4 → REQ-SRC-ATL105-PDF-001:2401 → derivation_method = business_rule_derived → SC-7775 (business_rule) → TC-010541 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 437623 | format_synthesis |  |
| 118 | Card Table Load Version | 060318 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2401](../traceability/segment-118.md) → SC-7775 → TC-010541 → `TC-010541.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010542 — Business_Rule — Proprietary Data Load Request — Code 166 disables the Return/Refund function.

**Purpose:** Code 166 disables the Return/Refund function. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-5 → REQ-SRC-ATL105-PDF-001:2402 → derivation_method = business_rule_derived → SC-7776 (business_rule) → TC-010542 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 410162 | format_synthesis |  |
| 118 | Card Table Load Version | 249909 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2402](../traceability/segment-118.md) → SC-7776 → TC-010542 → `TC-010542.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010543 — Business_Rule — Proprietary Data Load Request — Code 167 disables the Void/Reversal function.

**Purpose:** Code 167 disables the Void/Reversal function. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-6 → REQ-SRC-ATL105-PDF-001:2403 → derivation_method = business_rule_derived → SC-7777 (business_rule) → TC-010543 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 032555 | format_synthesis |  |
| 118 | Card Table Load Version | 193960 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2403](../traceability/segment-118.md) → SC-7777 → TC-010543 → `TC-010543.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010544 — Business_Rule — Proprietary Data Load Request — Code 168 disables the Preauthorization function except for proprietary

**Purpose:** Code 168 disables the Preauthorization function except for proprietary cards. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-7 → REQ-SRC-ATL105-PDF-001:2404 → derivation_method = business_rule_derived → SC-7778 (business_rule) → TC-010544 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 841832 | format_synthesis |  |
| 118 | Card Table Load Version | 054041 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2404](../traceability/segment-118.md) → SC-7778 → TC-010544 → `TC-010544.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010545 — Business_Rule — Proprietary Data Load Request — Code 171 enables BUYPASS TAP function.

**Purpose:** Code 171 enables BUYPASS TAP function. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-8 → REQ-SRC-ATL105-PDF-001:2405 → derivation_method = business_rule_derived → SC-7779 (business_rule) → TC-010545 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 261516 | format_synthesis |  |
| 118 | Card Table Load Version | 713076 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2405](../traceability/segment-118.md) → SC-7779 → TC-010545 → `TC-010545.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010546 — Business_Rule — Proprietary Data Load Request — Code 173 enables Store and Forward blocking for a designated range of 

**Purpose:** Code 173 enables Store and Forward blocking for a designated range of time each day. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-9 → REQ-SRC-ATL105-PDF-001:2406 → derivation_method = business_rule_derived → SC-7780 (business_rule) → TC-010546 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 611177 | format_synthesis |  |
| 118 | Card Table Load Version | 381418 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2406](../traceability/segment-118.md) → SC-7780 → TC-010546 → `TC-010546.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010547 — Business_Rule — Proprietary Data Load Request — Code 174 configures device to prompt for card verification data (CVC2,

**Purpose:** Code 174 configures device to prompt for card verification data (CVC2, CVV2, CID) for inside sales only. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-10 → REQ-SRC-ATL105-PDF-001:2407 → derivation_method = business_rule_derived → SC-7781 (business_rule) → TC-010547 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 600173 | format_synthesis |  |
| 118 | Card Table Load Version | 957814 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2407](../traceability/segment-118.md) → SC-7781 → TC-010547 → `TC-010547.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010548 — Business_Rule — ECA/TeleCheck Service Transaction — Code 148 truncates account number to four digits and omits expiration 

**Purpose:** Code 148 truncates account number to four digits and omits expiration date on customer receipt copy. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-11 → REQ-SRC-ATL105-PDF-001:2408 → derivation_method = business_rule_derived → SC-7782 (business_rule) → TC-010548 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2408](../traceability/segment-118.md) → SC-7782 → TC-010548 → `TC-010548.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010549 — Business_Rule — ECA/TeleCheck Service Transaction Request — Code 148 truncates account number to four digits and omits expiration 

**Purpose:** Code 148 truncates account number to four digits and omits expiration date on customer receipt copy. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-11 → REQ-SRC-ATL105-PDF-001:2408 → derivation_method = business_rule_derived → SC-7782 (business_rule) → TC-010549 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2408](../traceability/segment-118.md) → SC-7782 → TC-010549 → `TC-010549.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010550 — Business_Rule — EMV Financial Transaction Request — Code 148 truncates account number to four digits and omits expiration 

**Purpose:** Code 148 truncates account number to four digits and omits expiration date on customer receipt copy. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-11 → REQ-SRC-ATL105-PDF-001:2408 → derivation_method = business_rule_derived → SC-7782 (business_rule) → TC-010550 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 524507 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 879810 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 28755739 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2408](../traceability/segment-118.md) → SC-7782 → TC-010550 → `TC-010550.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010551 — Business_Rule — EMV Request Transaction — Code 148 truncates account number to four digits and omits expiration 

**Purpose:** Code 148 truncates account number to four digits and omits expiration date on customer receipt copy. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-11 → REQ-SRC-ATL105-PDF-001:2408 → derivation_method = business_rule_derived → SC-7782 (business_rule) → TC-010551 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 452329 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 033852 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 16323875 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2408](../traceability/segment-118.md) → SC-7782 → TC-010551 → `TC-010551.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010552 — Business_Rule — Financial Transaction Request — Code 148 truncates account number to four digits and omits expiration 

**Purpose:** Code 148 truncates account number to four digits and omits expiration date on customer receipt copy. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-11 → REQ-SRC-ATL105-PDF-001:2408 → derivation_method = business_rule_derived → SC-7782 (business_rule) → TC-010552 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 545422 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 509667 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 19023317 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | PDN3P8Q5 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | GM7YFAE5 | format_synthesis |  |
| 119 | Firmware Version | RZBY71DD | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 824280 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | PDN3P8Q5 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | GM7YFAE5 | format_synthesis |  |
| 132 | Firmware Version | RZBY71DD | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:2408](../traceability/segment-118.md) → SC-7782 → TC-010552 → `TC-010552.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010553 — Business_Rule — Financial Transactions — Code 148 truncates account number to four digits and omits expiration 

**Purpose:** Code 148 truncates account number to four digits and omits expiration date on customer receipt copy. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-11 → REQ-SRC-ATL105-PDF-001:2408 → derivation_method = business_rule_derived → SC-7782 (business_rule) → TC-010553 (transaction = Financial Transactions)

**Preconditions:** transaction type Financial Transactions; message layout step 1 Financial Transactions segments 101, 104, 108, 109, 110

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 101 | Segment Type | 101 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 431556 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 859403 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 32146577 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2408](../traceability/segment-118.md) → SC-7782 → TC-010553 → `TC-010553.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010554 — Business_Rule — Proprietary Data Load Request — Code 165 allows manually keyed gift card transactions.

**Purpose:** Code 165 allows manually keyed gift card transactions. (source [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513))

**Derivation path:** Spec [p.513](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=513) (ATL105 2026-3) → BR-513-12 → REQ-SRC-ATL105-PDF-001:2409 → derivation_method = business_rule_derived → SC-7783 (business_rule) → TC-010554 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 971695 | format_synthesis |  |
| 118 | Card Table Load Version | 680896 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:2409](../traceability/segment-118.md) → SC-7783 → TC-010554 → `TC-010554.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012530 — Field_Constraint — ECA/TeleCheck Service Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3395 → derivation_method = field_constraint → SC-8769 (field_constraint) → TC-012530 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3395](../traceability/segment-118.md) → SC-8769 → TC-012530 → `TC-012530.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012531 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3395 → derivation_method = field_constraint → SC-8769 (field_constraint) → TC-012531 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3395](../traceability/segment-118.md) → SC-8769 → TC-012531 → `TC-012531.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012532 — Field_Constraint — EMV Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3395 → derivation_method = field_constraint → SC-8769 (field_constraint) → TC-012532 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 490510 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 909027 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 55334888 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3395](../traceability/segment-118.md) → SC-8769 → TC-012532 → `TC-012532.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012533 — Field_Constraint — EMV Request Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3395 → derivation_method = field_constraint → SC-8769 (field_constraint) → TC-012533 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 347398 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 161667 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 00122193 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3395](../traceability/segment-118.md) → SC-8769 → TC-012533 → `TC-012533.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012534 — Field_Constraint — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3395 → derivation_method = field_constraint → SC-8769 (field_constraint) → TC-012534 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3395](../traceability/segment-118.md) → SC-8769 → TC-012534 → `TC-012534.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012535 — Field_Fixed_Value — ECA/TeleCheck Service Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) must equal the fixed value 118. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3396 → derivation_method = field_fixed_value → SC-8770 (field_fixed_value) → TC-012535 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3396](../traceability/segment-118.md) → SC-8770 → TC-012535 → `TC-012535.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012536 — Field_Fixed_Value — ECA/TeleCheck Service Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) must equal the fixed value 118. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3396 → derivation_method = field_fixed_value → SC-8770 (field_fixed_value) → TC-012536 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3396](../traceability/segment-118.md) → SC-8770 → TC-012536 → `TC-012536.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012537 — Field_Fixed_Value — EMV Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) must equal the fixed value 118. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3396 → derivation_method = field_fixed_value → SC-8770 (field_fixed_value) → TC-012537 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 602101 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 575727 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 39555905 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3396](../traceability/segment-118.md) → SC-8770 → TC-012537 → `TC-012537.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012538 — Field_Fixed_Value — EMV Request Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) must equal the fixed value 118. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3396 → derivation_method = field_fixed_value → SC-8770 (field_fixed_value) → TC-012538 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 821867 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 607703 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 75858718 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3396](../traceability/segment-118.md) → SC-8770 → TC-012538 → `TC-012538.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012539 — Field_Fixed_Value — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) must equal the fixed value 118. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-1 → REQ-SRC-ATL105-PDF-001:3396 → derivation_method = field_fixed_value → SC-8770 (field_fixed_value) → TC-012539 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3396](../traceability/segment-118.md) → SC-8770 → TC-012539 → `TC-012539.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012540 — Field_Constraint — ECA/TeleCheck Service Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segment Length) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-2 → REQ-SRC-ATL105-PDF-001:3397 → derivation_method = field_constraint → SC-8771 (field_constraint) → TC-012540 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3397](../traceability/segment-118.md) → SC-8771 → TC-012540 → `TC-012540.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012541 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segment Length) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-2 → REQ-SRC-ATL105-PDF-001:3397 → derivation_method = field_constraint → SC-8771 (field_constraint) → TC-012541 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3397](../traceability/segment-118.md) → SC-8771 → TC-012541 → `TC-012541.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012542 — Field_Constraint — EMV Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segment Length) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-2 → REQ-SRC-ATL105-PDF-001:3397 → derivation_method = field_constraint → SC-8771 (field_constraint) → TC-012542 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 391533 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 744917 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 89868070 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3397](../traceability/segment-118.md) → SC-8771 → TC-012542 → `TC-012542.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012543 — Field_Constraint — EMV Request Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segment Length) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-2 → REQ-SRC-ATL105-PDF-001:3397 → derivation_method = field_constraint → SC-8771 (field_constraint) → TC-012543 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 619472 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 355185 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 23111965 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3397](../traceability/segment-118.md) → SC-8771 → TC-012543 → `TC-012543.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012544 — Field_Constraint — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segm

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segment Length) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-2 → REQ-SRC-ATL105-PDF-001:3397 → derivation_method = field_constraint → SC-8771 (field_constraint) → TC-012544 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3397](../traceability/segment-118.md) → SC-8771 → TC-012544 → `TC-012544.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012545 — Field_Constraint — ECA/TeleCheck Service Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequ

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-3 → REQ-SRC-ATL105-PDF-001:3398 → derivation_method = field_constraint → SC-8772 (field_constraint) → TC-012545 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3398](../traceability/segment-118.md) → SC-8772 → TC-012545 → `TC-012545.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012546 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequ

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-3 → REQ-SRC-ATL105-PDF-001:3398 → derivation_method = field_constraint → SC-8772 (field_constraint) → TC-012546 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3398](../traceability/segment-118.md) → SC-8772 → TC-012546 → `TC-012546.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012547 — Field_Constraint — EMV Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequ

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-3 → REQ-SRC-ATL105-PDF-001:3398 → derivation_method = field_constraint → SC-8772 (field_constraint) → TC-012547 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 598772 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 175948 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 99674114 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3398](../traceability/segment-118.md) → SC-8772 → TC-012547 → `TC-012547.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012548 — Field_Constraint — EMV Request Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequ

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-3 → REQ-SRC-ATL105-PDF-001:3398 → derivation_method = field_constraint → SC-8772 (field_constraint) → TC-012548 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 252460 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 398133 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 00999875 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3398](../traceability/segment-118.md) → SC-8772 → TC-012548 → `TC-012548.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012549 — Field_Constraint — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequ

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-3 → REQ-SRC-ATL105-PDF-001:3398 → derivation_method = field_constraint → SC-8772 (field_constraint) → TC-012549 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3398](../traceability/segment-118.md) → SC-8772 → TC-012549 → `TC-012549.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012550 — Field_Constraint — Electronic Mail Request — In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequ

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-3 → REQ-SRC-ATL105-PDF-001:3398 → derivation_method = field_constraint → SC-8772 (field_constraint) → TC-012550 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3398](../traceability/segment-118.md) → SC-8772 → TC-012550 → `TC-012550.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012551 — Field_Constraint — ECA/TeleCheck Service Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Info

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-4 → REQ-SRC-ATL105-PDF-001:3399 → derivation_method = field_constraint → SC-8773 (field_constraint) → TC-012551 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3399](../traceability/segment-118.md) → SC-8773 → TC-012551 → `TC-012551.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012552 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Info

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-4 → REQ-SRC-ATL105-PDF-001:3399 → derivation_method = field_constraint → SC-8773 (field_constraint) → TC-012552 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3399](../traceability/segment-118.md) → SC-8773 → TC-012552 → `TC-012552.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012553 — Field_Constraint — EMV Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Info

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-4 → REQ-SRC-ATL105-PDF-001:3399 → derivation_method = field_constraint → SC-8773 (field_constraint) → TC-012553 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 286651 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 446956 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 55653190 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3399](../traceability/segment-118.md) → SC-8773 → TC-012553 → `TC-012553.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012554 — Field_Constraint — EMV Request Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Info

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-4 → REQ-SRC-ATL105-PDF-001:3399 → derivation_method = field_constraint → SC-8773 (field_constraint) → TC-012554 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 119070 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 452424 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 66154661 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3399](../traceability/segment-118.md) → SC-8773 → TC-012554 → `TC-012554.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012555 — Field_Constraint — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Info

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-4 → REQ-SRC-ATL105-PDF-001:3399 → derivation_method = field_constraint → SC-8773 (field_constraint) → TC-012555 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3399](../traceability/segment-118.md) → SC-8773 → TC-012555 → `TC-012555.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012556 — Field_Constraint — Electronic Mail Request — In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Info

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-4 → REQ-SRC-ATL105-PDF-001:3399 → derivation_method = field_constraint → SC-8773 (field_constraint) → TC-012556 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3399](../traceability/segment-118.md) → SC-8773 → TC-012556 → `TC-012556.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012557 — Field_Constraint — ECA/TeleCheck Service Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Term

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-5 → REQ-SRC-ATL105-PDF-001:3400 → derivation_method = field_constraint → SC-8774 (field_constraint) → TC-012557 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3400](../traceability/segment-118.md) → SC-8774 → TC-012557 → `TC-012557.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012558 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Term

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-5 → REQ-SRC-ATL105-PDF-001:3400 → derivation_method = field_constraint → SC-8774 (field_constraint) → TC-012558 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3400](../traceability/segment-118.md) → SC-8774 → TC-012558 → `TC-012558.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012559 — Field_Constraint — EMV Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Term

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-5 → REQ-SRC-ATL105-PDF-001:3400 → derivation_method = field_constraint → SC-8774 (field_constraint) → TC-012559 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 692293 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 484519 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 81369411 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3400](../traceability/segment-118.md) → SC-8774 → TC-012559 → `TC-012559.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012560 — Field_Constraint — EMV Request Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Term

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-5 → REQ-SRC-ATL105-PDF-001:3400 → derivation_method = field_constraint → SC-8774 (field_constraint) → TC-012560 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 535756 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 208018 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 73299162 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3400](../traceability/segment-118.md) → SC-8774 → TC-012560 → `TC-012560.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012561 — Field_Constraint — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Term

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-5 → REQ-SRC-ATL105-PDF-001:3400 → derivation_method = field_constraint → SC-8774 (field_constraint) → TC-012561 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3400](../traceability/segment-118.md) → SC-8774 → TC-012561 → `TC-012561.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012562 — Field_Constraint — Electronic Mail Request — In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Term

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-FIELD-118-5 → REQ-SRC-ATL105-PDF-001:3400 → derivation_method = field_constraint → SC-8774 (field_constraint) → TC-012562 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3400](../traceability/segment-118.md) → SC-8774 → TC-012562 → `TC-012562.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012563 — Field_Constraint — ECA/TeleCheck Service Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prom

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-6 → REQ-SRC-ATL105-PDF-001:3401 → derivation_method = field_constraint → SC-8775 (field_constraint) → TC-012563 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3401](../traceability/segment-118.md) → SC-8775 → TC-012563 → `TC-012563.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012564 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prom

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-6 → REQ-SRC-ATL105-PDF-001:3401 → derivation_method = field_constraint → SC-8775 (field_constraint) → TC-012564 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3401](../traceability/segment-118.md) → SC-8775 → TC-012564 → `TC-012564.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012565 — Field_Constraint — EMV Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prom

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-6 → REQ-SRC-ATL105-PDF-001:3401 → derivation_method = field_constraint → SC-8775 (field_constraint) → TC-012565 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 179621 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 401726 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 94911642 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3401](../traceability/segment-118.md) → SC-8775 → TC-012565 → `TC-012565.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012566 — Field_Constraint — EMV Request Transaction — In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prom

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-6 → REQ-SRC-ATL105-PDF-001:3401 → derivation_method = field_constraint → SC-8775 (field_constraint) → TC-012566 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 228867 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 235523 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 76268546 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3401](../traceability/segment-118.md) → SC-8775 → TC-012566 → `TC-012566.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012567 — Field_Constraint — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prom

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-6 → REQ-SRC-ATL105-PDF-001:3401 → derivation_method = field_constraint → SC-8775 (field_constraint) → TC-012567 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3401](../traceability/segment-118.md) → SC-8775 → TC-012567 → `TC-012567.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012568 — Field_Constraint — Electronic Mail Request — In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prom

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-6 → REQ-SRC-ATL105-PDF-001:3401 → derivation_method = field_constraint → SC-8775 (field_constraint) → TC-012568 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3401](../traceability/segment-118.md) → SC-8775 → TC-012568 → `TC-012568.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012569 — Field_Constraint — Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 7 (Prom

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 7 (Prompt Code, Pending) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-7 → REQ-SRC-ATL105-PDF-001:3402 → derivation_method = field_constraint → SC-8776 (field_constraint) → TC-012569 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 980566 | format_synthesis |  |
| 118 | Card Table Load Version | 965807 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3402](../traceability/segment-118.md) → SC-8776 → TC-012569 → `TC-012569.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012570 — Field_Constraint — Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Devi

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Device Card Table Version) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-8 → REQ-SRC-ATL105-PDF-001:3403 → derivation_method = field_constraint → SC-8777 (field_constraint) → TC-012570 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 251607 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 705155 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 84944484 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | PONSFBLR | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | EXSE0ZI2 | format_synthesis |  |
| 119 | Firmware Version | NPKEC2AB | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 164645 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | PONSFBLR | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | EXSE0ZI2 | format_synthesis |  |
| 132 | Firmware Version | NPKEC2AB | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3403](../traceability/segment-118.md) → SC-8777 → TC-012570 → `TC-012570.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012571 — Field_Constraint — Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Devi

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Device Card Table Version) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-8 → REQ-SRC-ATL105-PDF-001:3403 → derivation_method = field_constraint → SC-8777 (field_constraint) → TC-012571 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 173950 | format_synthesis |  |
| 118 | Card Table Load Version | 831971 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3403](../traceability/segment-118.md) → SC-8777 → TC-012571 → `TC-012571.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012572 — Field_Constraint — Totals with Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Devi

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Device Card Table Version) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-8 → REQ-SRC-ATL105-PDF-001:3403 → derivation_method = field_constraint → SC-8777 (field_constraint) → TC-012572 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 33SDANR1 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 6CAF3BUX | format_synthesis |  |
| 119 | Firmware Version | G14ABN2Y | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 852798 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3403](../traceability/segment-118.md) → SC-8777 → TC-012572 → `TC-012572.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012573 — Field_Constraint — Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 9 (Card

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 9 (Card Table Load Version) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-9 → REQ-SRC-ATL105-PDF-001:3404 → derivation_method = field_constraint → SC-8778 (field_constraint) → TC-012573 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 525134 | format_synthesis |  |
| 118 | Card Table Load Version | 532576 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3404](../traceability/segment-118.md) → SC-8778 → TC-012573 → `TC-012573.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012574 — Field_Constraint — Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 10 (Car

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 10 (Card Table Type) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-10 → REQ-SRC-ATL105-PDF-001:3405 → derivation_method = field_constraint → SC-8779 (field_constraint) → TC-012574 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 461111 | format_synthesis |  |
| 118 | Card Table Load Version | 426039 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3405](../traceability/segment-118.md) → SC-8779 → TC-012574 → `TC-012574.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012575 — Field_Constraint — Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 11 (Loa

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 11 (Load Control Key) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-11 → REQ-SRC-ATL105-PDF-001:3406 → derivation_method = field_constraint → SC-8780 (field_constraint) → TC-012575 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 715816 | format_synthesis |  |
| 118 | Card Table Load Version | 842701 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3406](../traceability/segment-118.md) → SC-8780 → TC-012575 → `TC-012575.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012576 — Field_Constraint — Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Hos

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Host Discount Timestamp) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-12 → REQ-SRC-ATL105-PDF-001:3407 → derivation_method = field_constraint → SC-8781 (field_constraint) → TC-012576 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 823612 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 912393 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 13849138 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | NY4CZRPB | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | X39BYVY8 | format_synthesis |  |
| 119 | Firmware Version | VU24PHA4 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 365144 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | NY4CZRPB | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | X39BYVY8 | format_synthesis |  |
| 132 | Firmware Version | VU24PHA4 | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3407](../traceability/segment-118.md) → SC-8781 → TC-012576 → `TC-012576.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012577 — Field_Constraint — Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Hos

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Host Discount Timestamp) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-12 → REQ-SRC-ATL105-PDF-001:3407 → derivation_method = field_constraint → SC-8781 (field_constraint) → TC-012577 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 423343 | format_synthesis |  |
| 118 | Card Table Load Version | 576253 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3407](../traceability/segment-118.md) → SC-8781 → TC-012577 → `TC-012577.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012578 — Field_Constraint — Totals with Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Hos

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Host Discount Timestamp) is required and must be present. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-FIELD-118-12 → REQ-SRC-ATL105-PDF-001:3407 → derivation_method = field_constraint → SC-8781 (field_constraint) → TC-012578 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | YT0S6CKZ | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | WWLZS6P7 | format_synthesis |  |
| 119 | Firmware Version | 8YT5DEV9 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 793724 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3407](../traceability/segment-118.md) → SC-8781 → TC-012578 → `TC-012578.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012579 — Field_Constraint — Electronic Mail — In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Blo

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Block Number) is conditional and must be present only when its documented condition holds. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-FIELD-118-13 → REQ-SRC-ATL105-PDF-001:3408 → derivation_method = field_constraint → SC-8782 (field_constraint) → TC-012579 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3408](../traceability/segment-118.md) → SC-8782 → TC-012579 → `TC-012579.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012580 — Field_Constraint — Electronic Mail Request — In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Blo

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Block Number) is conditional and must be present only when its documented condition holds. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-FIELD-118-13 → REQ-SRC-ATL105-PDF-001:3408 → derivation_method = field_constraint → SC-8782 (field_constraint) → TC-012580 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3408](../traceability/segment-118.md) → SC-8782 → TC-012580 → `TC-012580.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012581 — Field_Constraint — Financial Transaction Request — In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Blo

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Block Number) is conditional and must be present only when its documented condition holds. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-FIELD-118-13 → REQ-SRC-ATL105-PDF-001:3408 → derivation_method = field_constraint → SC-8782 (field_constraint) → TC-012581 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 408241 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 052329 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 89863684 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | LCOZICAV | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | P86PQYYQ | format_synthesis |  |
| 119 | Firmware Version | AJKUTU25 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 700391 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | LCOZICAV | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | P86PQYYQ | format_synthesis |  |
| 132 | Firmware Version | AJKUTU25 | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3408](../traceability/segment-118.md) → SC-8782 → TC-012581 → `TC-012581.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012582 — Field_Constraint — Financial Transactions — In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Blo

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Block Number) is conditional and must be present only when its documented condition holds. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-FIELD-118-13 → REQ-SRC-ATL105-PDF-001:3408 → derivation_method = field_constraint → SC-8782 (field_constraint) → TC-012582 (transaction = Financial Transactions)

**Preconditions:** transaction type Financial Transactions; message layout step 1 Financial Transactions segments 101, 104, 108, 109, 110

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 101 | Segment Type | 101 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 778155 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 264183 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 72854618 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3408](../traceability/segment-118.md) → SC-8782 → TC-012582 → `TC-012582.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012583 — Field_Constraint — Mail Request — In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Blo

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Block Number) is conditional and must be present only when its documented condition holds. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-FIELD-118-13 → REQ-SRC-ATL105-PDF-001:3408 → derivation_method = field_constraint → SC-8782 (field_constraint) → TC-012583 (transaction = Mail Request)

**Preconditions:** transaction type Mail Request; message layout step 1 Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3408](../traceability/segment-118.md) → SC-8782 → TC-012583 → `TC-012583.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012584 — Field_Constraint — Proprietary Data Load Request — In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Blo

**Purpose:** In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Block Number) is conditional and must be present only when its documented condition holds. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-FIELD-118-13 → REQ-SRC-ATL105-PDF-001:3408 → derivation_method = field_constraint → SC-8782 (field_constraint) → TC-012584 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 901760 | format_synthesis |  |
| 118 | Card Table Load Version | 245884 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3408](../traceability/segment-118.md) → SC-8782 → TC-012584 → `TC-012584.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013382 — Message_Field_Constraint — Proprietary Data Load Request — In the Proprietary Data Load Request message, field 1 is required and 

**Purpose:** In the Proprietary Data Load Request message, field 1 is required and must be present. (source [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197))

**Derivation path:** Spec [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197) (ATL105 2026-3) → ENT-FIELD-PROPRIETARY_DATA_LOAD_REQUEST-1 → REQ-SRC-ATL105-PDF-001:3769 → derivation_method = message_field_constraint → SC-9143 (message_field_constraint) → TC-013382 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 443117 | format_synthesis |  |
| 118 | Card Table Load Version | 108761 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3769](../traceability/segment-118.md) → SC-9143 → TC-013382 → `TC-013382.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013383 — Message_Field_Constraint — Proprietary Data Load Request — In the Proprietary Data Load Request message, field 2 is required and 

**Purpose:** In the Proprietary Data Load Request message, field 2 is required and must be present. (source [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197))

**Derivation path:** Spec [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197) (ATL105 2026-3) → ENT-FIELD-PROPRIETARY_DATA_LOAD_REQUEST-2 → REQ-SRC-ATL105-PDF-001:3770 → derivation_method = message_field_constraint → SC-9144 (message_field_constraint) → TC-013383 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 916543 | format_synthesis |  |
| 118 | Card Table Load Version | 853590 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3770](../traceability/segment-118.md) → SC-9144 → TC-013383 → `TC-013383.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013384 — Message_Field_Constraint — Proprietary Data Load Request — In the Proprietary Data Load Request message, Data Segment No. 118 (Pr

**Purpose:** In the Proprietary Data Load Request message, Data Segment No. 118 (Proprietary Data Load Segment) is required and must be present. (source [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197))

**Derivation path:** Spec [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197) (ATL105 2026-3) → ENT-MSG-PROPRIETARY_DATA_LOAD_REQUEST → REQ-SRC-ATL105-PDF-001:3771 → derivation_method = message_field_constraint → SC-9145 (message_field_constraint) → TC-013384 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 074129 | format_synthesis |  |
| 118 | Card Table Load Version | 332579 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3771](../traceability/segment-118.md) → SC-9145 → TC-013384 → `TC-013384.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016363 — Relationship — Proprietary Data Load Request — The Proprietary Data Load Request transaction includes Proprietary Dat

**Purpose:** The Proprietary Data Load Request transaction includes Proprietary Data Load Segment (Data Segment No. 118). (source [p.217](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=217))

**Derivation path:** Spec [p.217](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=217) (ATL105 2026-3) → REL-ENT-SEG-118-PROPRIETARY_DATA_LOAD_REQUEST → REQ-SRC-ATL105-PDF-001:4489 → derivation_method = relationship_derived → SC-9863 (relationship) → TC-016363 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 851362 | format_synthesis |  |
| 118 | Card Table Load Version | 083232 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4489](../traceability/segment-118.md) → SC-9863 → TC-016363 → `TC-016363.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016727 — Relationship — Proprietary Data Load Request — ENT-ELEM-174 depends on Prompt Code 902; the documented dependency mus

**Purpose:** ENT-ELEM-174 depends on Prompt Code 902; the documented dependency must hold. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → REL-ENT-ELEM-174-Prompt_Code_902 → REQ-SRC-ATL105-PDF-001:4620 → derivation_method = relationship_derived → SC-9994 (relationship) → TC-016727 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 233733 | format_synthesis |  |
| 118 | Card Table Load Version | 743405 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4620](../traceability/segment-118.md) → SC-9994 → TC-016727 → `TC-016727.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016729 — Relationship — Proprietary Data Load Request — ENT-ELEM-177 depends on ENT-SEG-118; the documented dependency must ho

**Purpose:** ENT-ELEM-177 depends on ENT-SEG-118; the documented dependency must hold. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → REL-ENT-ELEM-177-ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:4622 → derivation_method = relationship_derived → SC-9996 (relationship) → TC-016729 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 012864 | format_synthesis |  |
| 118 | Card Table Load Version | 505606 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4622](../traceability/segment-118.md) → SC-9996 → TC-016729 → `TC-016729.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016730 — Relationship — Proprietary Data Load Request — ENT-ELEM-178 depends on ENT-SEG-118; the documented dependency must ho

**Purpose:** ENT-ELEM-178 depends on ENT-SEG-118; the documented dependency must hold. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → REL-ENT-ELEM-178-ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:4623 → derivation_method = relationship_derived → SC-9997 (relationship) → TC-016730 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 597886 | format_synthesis |  |
| 118 | Card Table Load Version | 129495 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4623](../traceability/segment-118.md) → SC-9997 → TC-016730 → `TC-016730.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016731 — Relationship — Financial Transaction Request — ENT-ELEM-179 depends on ENT-SEG-118; the documented dependency must ho

**Purpose:** ENT-ELEM-179 depends on ENT-SEG-118; the documented dependency must hold. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → REL-ENT-ELEM-179-ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:4624 → derivation_method = relationship_derived → SC-9998 (relationship) → TC-016731 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 047436 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 172675 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 43011047 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | PS04Y6YU | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 7ZX8SO3I | format_synthesis |  |
| 119 | Firmware Version | 54EF9CHI | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 823626 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | PS04Y6YU | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | 7ZX8SO3I | format_synthesis |  |
| 132 | Firmware Version | 54EF9CHI | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:4624](../traceability/segment-118.md) → SC-9998 → TC-016731 → `TC-016731.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016732 — Relationship — Proprietary Data Load Request — ENT-ELEM-179 depends on ENT-SEG-118; the documented dependency must ho

**Purpose:** ENT-ELEM-179 depends on ENT-SEG-118; the documented dependency must hold. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → REL-ENT-ELEM-179-ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:4624 → derivation_method = relationship_derived → SC-9998 (relationship) → TC-016732 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 038007 | format_synthesis |  |
| 118 | Card Table Load Version | 854032 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4624](../traceability/segment-118.md) → SC-9998 → TC-016732 → `TC-016732.json`

**Test Solution findings:** QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016733 — Relationship — Totals with Proprietary Data Load Request — ENT-ELEM-179 depends on ENT-SEG-118; the documented dependency must ho

**Purpose:** ENT-ELEM-179 depends on ENT-SEG-118; the documented dependency must hold. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → REL-ENT-ELEM-179-ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:4624 → derivation_method = relationship_derived → SC-9998 (relationship) → TC-016733 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 815E0XGV | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | BN1RAWGG | format_synthesis |  |
| 119 | Firmware Version | GQ56DEYP | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 084792 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4624](../traceability/segment-118.md) → SC-9998 → TC-016733 → `TC-016733.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-017344 — Relationship — Proprietary Data Load Request — The Proprietary Data Load Request transaction includes Proprietary Dat

**Purpose:** The Proprietary Data Load Request transaction includes Proprietary Data Load Segment (Data Segment No. 118). (source [p.196](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=196))

**Derivation path:** Spec [p.196](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=196) (ATL105 2026-3) → ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:4825 → derivation_method = relationship_derived → SC-10199 (relationship) → TC-017344 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 072791 | format_synthesis |  |
| 118 | Card Table Load Version | 832586 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4825](../traceability/segment-118.md) → SC-10199 → TC-017344 → `TC-017344.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-017467 — Relationship — Proprietary Data Load Request — Data element 182 accepts documented value '0901' — Valid Prompt Code, 

**Purpose:** Data element 182 accepts documented value '0901' — Valid Prompt Code, Pending value for special transaction data. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:4879 → derivation_method = relationship_derived → SC-10253 (relationship) → TC-017467 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 296521 | format_synthesis |  |
| 118 | Card Table Load Version | 950962 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4879](../traceability/segment-118.md) → SC-10253 → TC-017467 → `TC-017467.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-017468 — Relationship — Proprietary Data Load Request — Data element 182 accepts documented value '0902' — Valid Prompt Code, 

**Purpose:** Data element 182 accepts documented value '0902' — Valid Prompt Code, Pending value for special transaction data. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:4880 → derivation_method = relationship_derived → SC-10254 (relationship) → TC-017468 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 566571 | format_synthesis |  |
| 118 | Card Table Load Version | 950146 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4880](../traceability/segment-118.md) → SC-10254 → TC-017468 → `TC-017468.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-017469 — Relationship — Proprietary Data Load Request — Data element 182 accepts documented value '0904' — Valid Prompt Code, 

**Purpose:** Data element 182 accepts documented value '0904' — Valid Prompt Code, Pending value for special transaction data. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:4881 → derivation_method = relationship_derived → SC-10255 (relationship) → TC-017469 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 853345 | format_synthesis |  |
| 118 | Card Table Load Version | 471778 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4881](../traceability/segment-118.md) → SC-10255 → TC-017469 → `TC-017469.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-017470 — Relationship — Proprietary Data Load Request — Data element 182 accepts documented value '0981' — Valid Prompt Code, 

**Purpose:** Data element 182 accepts documented value '0981' — Valid Prompt Code, Pending value for special transaction data. (source [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252))

**Derivation path:** Spec [p.252](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=252) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:4882 → derivation_method = relationship_derived → SC-10256 (relationship) → TC-017470 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 098561 | format_synthesis |  |
| 118 | Card Table Load Version | 974480 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:4882](../traceability/segment-118.md) → SC-10256 → TC-017470 → `TC-017470.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018597 — Relationship — Proprietary Data Load Request — Data element 174 accepts documented value '0001' — 0001 indicates BIN 

**Purpose:** Data element 174 accepts documented value '0001' — 0001 indicates BIN Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:5280 → derivation_method = relationship_derived → SC-10654 (relationship) → TC-018597 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 552244 | format_synthesis |  |
| 118 | Card Table Load Version | 841321 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5280](../traceability/segment-118.md) → SC-10654 → TC-018597 → `TC-018597.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018598 — Relationship — Proprietary Data Load Request — Data element 174 accepts documented value '0002' — 0002 indicates RULE

**Purpose:** Data element 174 accepts documented value '0002' — 0002 indicates RULES Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:5281 → derivation_method = relationship_derived → SC-10655 (relationship) → TC-018598 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 469775 | format_synthesis |  |
| 118 | Card Table Load Version | 917678 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5281](../traceability/segment-118.md) → SC-10655 → TC-018598 → `TC-018598.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018599 — Relationship — Proprietary Data Load Request — Data element 174 accepts documented value '0003' — 0003 indicates REST

**Purpose:** Data element 174 accepts documented value '0003' — 0003 indicates RESTRICTIONS Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:5282 → derivation_method = relationship_derived → SC-10656 (relationship) → TC-018599 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 317463 | format_synthesis |  |
| 118 | Card Table Load Version | 602684 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5282](../traceability/segment-118.md) → SC-10656 → TC-018599 → `TC-018599.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018600 — Relationship — Proprietary Data Load Request — Data element 174 accepts documented value '0004' — 0004 indicates SAF 

**Purpose:** Data element 174 accepts documented value '0004' — 0004 indicates SAF Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:5283 → derivation_method = relationship_derived → SC-10657 (relationship) → TC-018600 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 507090 | format_synthesis |  |
| 118 | Card Table Load Version | 473701 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5283](../traceability/segment-118.md) → SC-10657 → TC-018600 → `TC-018600.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018601 — Relationship — Proprietary Data Load Request — Data element 174 accepts documented value '0005' — 0005 indicates PROM

**Purpose:** Data element 174 accepts documented value '0005' — 0005 indicates PROMPT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:5284 → derivation_method = relationship_derived → SC-10658 (relationship) → TC-018601 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 038608 | format_synthesis |  |
| 118 | Card Table Load Version | 537936 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5284](../traceability/segment-118.md) → SC-10658 → TC-018601 → `TC-018601.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018602 — Relationship — Proprietary Data Load Request — Data element 174 accepts documented value '0006' — 0006 indicates PROD

**Purpose:** Data element 174 accepts documented value '0006' — 0006 indicates PRODUCT Table. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:5285 → derivation_method = relationship_derived → SC-10659 (relationship) → TC-018602 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 671033 | format_synthesis |  |
| 118 | Card Table Load Version | 494722 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5285](../traceability/segment-118.md) → SC-10659 → TC-018602 → `TC-018602.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018603 — Relationship — Proprietary Data Load Request — Data element ENT-ELEM-182 accepts documented value '0901' — 0901 indic

**Purpose:** Data element ENT-ELEM-182 accepts documented value '0901' — 0901 indicates Custom Receipt Test Data. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:5286 → derivation_method = relationship_derived → SC-10660 (relationship) → TC-018603 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 045739 | format_synthesis |  |
| 118 | Card Table Load Version | 914959 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5286](../traceability/segment-118.md) → SC-10660 → TC-018603 → `TC-018603.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018604 — Relationship — Proprietary Data Load Request — Data element ENT-ELEM-182 accepts documented value '0902' — 0902 indic

**Purpose:** Data element ENT-ELEM-182 accepts documented value '0902' — 0902 indicates Proprietary Data Load. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:5287 → derivation_method = relationship_derived → SC-10661 (relationship) → TC-018604 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 303063 | format_synthesis |  |
| 118 | Card Table Load Version | 592194 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5287](../traceability/segment-118.md) → SC-10661 → TC-018604 → `TC-018604.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018605 — Relationship — Proprietary Data Load Request — Data element ENT-ELEM-182 accepts documented value '0904' — 0904 indic

**Purpose:** Data element ENT-ELEM-182 accepts documented value '0904' — 0904 indicates Host Discount Data. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:5288 → derivation_method = relationship_derived → SC-10662 (relationship) → TC-018605 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 923374 | format_synthesis |  |
| 118 | Card Table Load Version | 207879 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5288](../traceability/segment-118.md) → SC-10662 → TC-018605 → `TC-018605.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018606 — Relationship — Proprietary Data Load Request — Data element ENT-ELEM-182 accepts documented value '0981' — 0981 indic

**Purpose:** Data element ENT-ELEM-182 accepts documented value '0981' — 0981 indicates Electronic Mail. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:5289 → derivation_method = relationship_derived → SC-10663 (relationship) → TC-018606 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 536156 | format_synthesis |  |
| 118 | Card Table Load Version | 534520 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5289](../traceability/segment-118.md) → SC-10663 → TC-018606 → `TC-018606.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019480 — Supplemental_Entity — Proprietary Data Load Request — Proprietary Data Load Segment: Indicates that this is a Proprietary Da

**Purpose:** Proprietary Data Load Segment: Indicates that this is a Proprietary Data Load Request. (source [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197))

**Derivation path:** Spec [p.197](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=197) (ATL105 2026-3) → ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:5869 → derivation_method = supplemental_entity_derived → SC-11243 (supplemental_entity) → TC-019480 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 565103 | format_synthesis |  |
| 118 | Card Table Load Version | 051885 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5869](../traceability/segment-118.md) → SC-11243 → TC-019480 → `TC-019480.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019560 — Supplemental_Entity — Proprietary Data Load Request — Proprietary Data Load Segment: Requests or captures customer specific 

**Purpose:** Proprietary Data Load Segment: Requests or captures customer specific proprietary data for loading at BUYPASS FEP. (source [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251))

**Derivation path:** Spec [p.251](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=251) (ATL105 2026-3) → ENT-SEG-118 → REQ-SRC-ATL105-PDF-001:5895 → derivation_method = supplemental_entity_derived → SC-11269 (supplemental_entity) → TC-019560 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 273034 | format_synthesis |  |
| 118 | Card Table Load Version | 956169 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5895](../traceability/segment-118.md) → SC-11269 → TC-019560 → `TC-019560.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019561 — Supplemental_Entity — Electronic Mail — Block Number: Identifies the specific block number sent by device or H

**Purpose:** Block Number: Identifies the specific block number sent by device or Host for next block determination. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-ELEM-11 → REQ-SRC-ATL105-PDF-001:5896 → derivation_method = supplemental_entity_derived → SC-11270 (supplemental_entity) → TC-019561 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5896](../traceability/segment-118.md) → SC-11270 → TC-019561 → `TC-019561.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019562 — Supplemental_Entity — Electronic Mail Request — Block Number: Identifies the specific block number sent by device or H

**Purpose:** Block Number: Identifies the specific block number sent by device or Host for next block determination. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-ELEM-11 → REQ-SRC-ATL105-PDF-001:5896 → derivation_method = supplemental_entity_derived → SC-11270 (supplemental_entity) → TC-019562 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5896](../traceability/segment-118.md) → SC-11270 → TC-019562 → `TC-019562.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019563 — Supplemental_Entity — Financial Transaction Request — Block Number: Identifies the specific block number sent by device or H

**Purpose:** Block Number: Identifies the specific block number sent by device or Host for next block determination. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-ELEM-11 → REQ-SRC-ATL105-PDF-001:5896 → derivation_method = supplemental_entity_derived → SC-11270 (supplemental_entity) → TC-019563 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 012896 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 283692 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 38246945 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | H1HTJN2C | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | XE08LXQ3 | format_synthesis |  |
| 119 | Firmware Version | 3DB3C9SQ | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 075405 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | H1HTJN2C | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | XE08LXQ3 | format_synthesis |  |
| 132 | Firmware Version | 3DB3C9SQ | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:5896](../traceability/segment-118.md) → SC-11270 → TC-019563 → `TC-019563.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019564 — Supplemental_Entity — Financial Transactions — Block Number: Identifies the specific block number sent by device or H

**Purpose:** Block Number: Identifies the specific block number sent by device or Host for next block determination. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-ELEM-11 → REQ-SRC-ATL105-PDF-001:5896 → derivation_method = supplemental_entity_derived → SC-11270 (supplemental_entity) → TC-019564 (transaction = Financial Transactions)

**Preconditions:** transaction type Financial Transactions; message layout step 1 Financial Transactions segments 101, 104, 108, 109, 110

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 101 | Segment Type | 101 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 186816 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 381045 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 61680849 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5896](../traceability/segment-118.md) → SC-11270 → TC-019564 → `TC-019564.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019565 — Supplemental_Entity — Mail Request — Block Number: Identifies the specific block number sent by device or H

**Purpose:** Block Number: Identifies the specific block number sent by device or Host for next block determination. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-ELEM-11 → REQ-SRC-ATL105-PDF-001:5896 → derivation_method = supplemental_entity_derived → SC-11270 (supplemental_entity) → TC-019565 (transaction = Mail Request)

**Preconditions:** transaction type Mail Request; message layout step 1 Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5896](../traceability/segment-118.md) → SC-11270 → TC-019565 → `TC-019565.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019566 — Supplemental_Entity — Proprietary Data Load Request — Block Number: Identifies the specific block number sent by device or H

**Purpose:** Block Number: Identifies the specific block number sent by device or Host for next block determination. (source [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253))

**Derivation path:** Spec [p.253](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=253) (ATL105 2026-3) → ENT-ELEM-11 → REQ-SRC-ATL105-PDF-001:5896 → derivation_method = supplemental_entity_derived → SC-11270 (supplemental_entity) → TC-019566 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 615571 | format_synthesis |  |
| 118 | Card Table Load Version | 343167 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:5896](../traceability/segment-118.md) → SC-11270 → TC-019566 → `TC-019566.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020099 — Supplemental_Entity — Proprietary Data Load Request — Start Date or End Date: Fixed 8-digit CCYYMMDD date indicating start o

**Purpose:** Start Date or End Date: Fixed 8-digit CCYYMMDD date indicating start or end of custom receipt text or host discount data. (source [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460))

**Derivation path:** Spec [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460) (ATL105 2026-3) → ENT-ELEM-165 → REQ-SRC-ATL105-PDF-001:6239 → derivation_method = supplemental_entity_derived → SC-11613 (supplemental_entity) → TC-020099 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 669152 | format_synthesis |  |
| 118 | Card Table Load Version | 411412 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6239](../traceability/segment-118.md) → SC-11613 → TC-020099 → `TC-020099.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020100 — Supplemental_Entity — Proprietary Data Load Request — Start Time or End Time: Fixed 4-digit HHMM time indicating start or en

**Purpose:** Start Time or End Time: Fixed 4-digit HHMM time indicating start or end time for custom receipt text or host discount data. (source [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460))

**Derivation path:** Spec [p.460](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=460) (ATL105 2026-3) → ENT-ELEM-166 → REQ-SRC-ATL105-PDF-001:6240 → derivation_method = supplemental_entity_derived → SC-11614 (supplemental_entity) → TC-020100 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 623049 | format_synthesis |  |
| 118 | Card Table Load Version | 332615 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6240](../traceability/segment-118.md) → SC-11614 → TC-020100 → `TC-020100.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020101 — Supplemental_Entity — Proprietary Data Load Request — Number of Receipt Text Lines: Provides count of Receipt Text Line item

**Purpose:** Number of Receipt Text Lines: Provides count of Receipt Text Line items for Customized Receipt Text. (source [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461))

**Derivation path:** Spec [p.461](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=461) (ATL105 2026-3) → ENT-ELEM-168 → REQ-SRC-ATL105-PDF-001:6241 → derivation_method = supplemental_entity_derived → SC-11615 (supplemental_entity) → TC-020101 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 047991 | format_synthesis |  |
| 118 | Card Table Load Version | 819201 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6241](../traceability/segment-118.md) → SC-11615 → TC-020101 → `TC-020101.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020102 — Supplemental_Entity — Proprietary Data Load Request — Receipt Text Data Length: Length of characters representing a line of 

**Purpose:** Receipt Text Data Length: Length of characters representing a line of Receipt Text for printing on receipts. (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → ENT-ELEM-169 → REQ-SRC-ATL105-PDF-001:6242 → derivation_method = supplemental_entity_derived → SC-11616 (supplemental_entity) → TC-020102 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 838364 | format_synthesis |  |
| 118 | Card Table Load Version | 583095 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6242](../traceability/segment-118.md) → SC-11616 → TC-020102 → `TC-020102.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020103 — Supplemental_Entity — Proprietary Data Load Request — Receipt Text Data: Text to be printed on a customer's receipt.

**Purpose:** Receipt Text Data: Text to be printed on a customer's receipt. (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → ENT-ELEM-170 → REQ-SRC-ATL105-PDF-001:6243 → derivation_method = supplemental_entity_derived → SC-11617 (supplemental_entity) → TC-020103 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 999796 | format_synthesis |  |
| 118 | Card Table Load Version | 058471 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6243](../traceability/segment-118.md) → SC-11617 → TC-020103 → `TC-020103.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020104 — Supplemental_Entity — Proprietary Data Load Request — Number of Discounts: Number of host discount items that will follow in

**Purpose:** Number of Discounts: Number of host discount items that will follow in the message. (source [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462))

**Derivation path:** Spec [p.462](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=462) (ATL105 2026-3) → ENT-ELEM-171 → REQ-SRC-ATL105-PDF-001:6244 → derivation_method = supplemental_entity_derived → SC-11618 (supplemental_entity) → TC-020104 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 177189 | format_synthesis |  |
| 118 | Card Table Load Version | 884516 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6244](../traceability/segment-118.md) → SC-11618 → TC-020104 → `TC-020104.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020105 — Supplemental_Entity — Proprietary Data Load Request — Product Discount Amount: Flat rate discount amount applied to previous

**Purpose:** Product Discount Amount: Flat rate discount amount applied to previously indicated Product Code; format 99v999. (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → ENT-ELEM-172 → REQ-SRC-ATL105-PDF-001:6245 → derivation_method = supplemental_entity_derived → SC-11619 (supplemental_entity) → TC-020105 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 357710 | format_synthesis |  |
| 118 | Card Table Load Version | 458587 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6245](../traceability/segment-118.md) → SC-11619 → TC-020105 → `TC-020105.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020106 — Supplemental_Entity — Proprietary Data Load Request — BUYPASS Card Type: BUYPASS recognized Card Type value matched to Dynam

**Purpose:** BUYPASS Card Type: BUYPASS recognized Card Type value matched to Dynamic BIN tables for Host Discounts. (source [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463))

**Derivation path:** Spec [p.463](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=463) (ATL105 2026-3) → ENT-ELEM-173 → REQ-SRC-ATL105-PDF-001:6246 → derivation_method = supplemental_entity_derived → SC-11620 (supplemental_entity) → TC-020106 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 398130 | format_synthesis |  |
| 118 | Card Table Load Version | 788917 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6246](../traceability/segment-118.md) → SC-11620 → TC-020106 → `TC-020106.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020107 — Supplemental_Entity — Proprietary Data Load Request — Card Table Type: Defines specific sub-table type within Dynamic Card T

**Purpose:** Card Table Type: Defines specific sub-table type within Dynamic Card Table provided in data element 175. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:6247 → derivation_method = supplemental_entity_derived → SC-11621 (supplemental_entity) → TC-020107 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 453610 | format_synthesis |  |
| 118 | Card Table Load Version | 089126 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6247](../traceability/segment-118.md) → SC-11621 → TC-020107 → `TC-020107.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020108 — Supplemental_Entity — Proprietary Data Load Request — Card Table Data: Provides actual Card Table data for the table identif

**Purpose:** Card Table Data: Provides actual Card Table data for the table identified in Field 174. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-175 → REQ-SRC-ATL105-PDF-001:6248 → derivation_method = supplemental_entity_derived → SC-11622 (supplemental_entity) → TC-020108 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 029951 | format_synthesis |  |
| 118 | Card Table Load Version | 993366 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6248](../traceability/segment-118.md) → SC-11622 → TC-020108 → `TC-020108.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020109 — Supplemental_Entity — Proprietary Data Load Request — Load Control Key: Provides the load control key for tracking block dat

**Purpose:** Load Control Key: Provides the load control key for tracking block data. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-178 → REQ-SRC-ATL105-PDF-001:6249 → derivation_method = supplemental_entity_derived → SC-11623 (supplemental_entity) → TC-020109 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 516452 | format_synthesis |  |
| 118 | Card Table Load Version | 515344 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6249](../traceability/segment-118.md) → SC-11623 → TC-020109 → `TC-020109.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020110 — Supplemental_Entity — Financial Transaction Request — Host Discount Timestamp: Timestamp used to support host discounts in f

**Purpose:** Host Discount Timestamp: Timestamp used to support host discounts in format CCYYMMDDHHMM. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-179 → REQ-SRC-ATL105-PDF-001:6250 → derivation_method = supplemental_entity_derived → SC-11624 (supplemental_entity) → TC-020110 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 288387 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 294209 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 22302171 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | WB1E4JTD | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | FK0IO0UD | format_synthesis |  |
| 119 | Firmware Version | IZFZQMCX | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 437278 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | WB1E4JTD | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | FK0IO0UD | format_synthesis |  |
| 132 | Firmware Version | IZFZQMCX | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:6250](../traceability/segment-118.md) → SC-11624 → TC-020110 → `TC-020110.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020111 — Supplemental_Entity — Proprietary Data Load Request — Host Discount Timestamp: Timestamp used to support host discounts in f

**Purpose:** Host Discount Timestamp: Timestamp used to support host discounts in format CCYYMMDDHHMM. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-179 → REQ-SRC-ATL105-PDF-001:6250 → derivation_method = supplemental_entity_derived → SC-11624 (supplemental_entity) → TC-020111 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 624905 | format_synthesis |  |
| 118 | Card Table Load Version | 146430 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6250](../traceability/segment-118.md) → SC-11624 → TC-020111 → `TC-020111.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020112 — Supplemental_Entity — Totals with Proprietary Data Load Request — Host Discount Timestamp: Timestamp used to support host discounts in f

**Purpose:** Host Discount Timestamp: Timestamp used to support host discounts in format CCYYMMDDHHMM. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-179 → REQ-SRC-ATL105-PDF-001:6250 → derivation_method = supplemental_entity_derived → SC-11624 (supplemental_entity) → TC-020112 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | HALRFZCK | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | Q36ADXHS | format_synthesis |  |
| 119 | Firmware Version | 6GF0YW53 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 449548 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6250](../traceability/segment-118.md) → SC-11624 → TC-020112 → `TC-020112.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020113 — Supplemental_Entity — Proprietary Data Load Request — Site Configuration Data: Variable length alphanumeric field providing 

**Purpose:** Site Configuration Data: Variable length alphanumeric field providing site configuration data for merchant location. (source [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467))

**Derivation path:** Spec [p.467](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=467) (ATL105 2026-3) → ENT-ELEM-180 → REQ-SRC-ATL105-PDF-001:6251 → derivation_method = supplemental_entity_derived → SC-11625 (supplemental_entity) → TC-020113 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 094621 | format_synthesis |  |
| 118 | Card Table Load Version | 112536 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6251](../traceability/segment-118.md) → SC-11625 → TC-020113 → `TC-020113.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020114 — Supplemental_Entity — Proprietary Data Load Request — Prompt Code, Pending: 4-character Prompt Code identifying pending requ

**Purpose:** Prompt Code, Pending: 4-character Prompt Code identifying pending requested data for special transactions. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:6252 → derivation_method = supplemental_entity_derived → SC-11626 (supplemental_entity) → TC-020114 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 013677 | format_synthesis |  |
| 118 | Card Table Load Version | 764472 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6252](../traceability/segment-118.md) → SC-11626 → TC-020114 → `TC-020114.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020130 — Supplemental_Entity — Proprietary Data Load Request — Fuel Volume Data: Provides fuel volume data for merchant location.

**Purpose:** Fuel Volume Data: Provides fuel volume data for merchant location. (source [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477))

**Derivation path:** Spec [p.477](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=477) (ATL105 2026-3) → ENT-ELEM-201 → REQ-SRC-ATL105-PDF-001:6272 → derivation_method = supplemental_entity_derived → SC-11646 (supplemental_entity) → TC-020130 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 258231 | format_synthesis |  |
| 118 | Card Table Load Version | 409525 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6272](../traceability/segment-118.md) → SC-11646 → TC-020130 → `TC-020130.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020776 — Narrative_Rule — Proprietary Data Load Request — Card Table Type must appear in Segment No. 118 when Prompt Code 902 is

**Purpose:** Card Table Type must appear in Segment No. 118 when Prompt Code 902 is returned. (source [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464))

**Derivation path:** Spec [p.464](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=464) (ATL105 2026-3) → ENT-ELEM-174 → REQ-SRC-ATL105-PDF-001:6833 → derivation_method = llm_phrased → SC-12207 (narrative_rule) → TC-020776 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 134614 | format_synthesis |  |
| 118 | Card Table Load Version | 167490 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6833](../traceability/segment-118.md) → SC-12207 → TC-020776 → `TC-020776.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020778 — Narrative_Rule — Financial Transaction Request — Device Card Table Version value beginning with 99999 must indicate no 

**Purpose:** Device Card Table Version value beginning with 99999 must indicate no card table used by the location. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → ENT-ELEM-176 → REQ-SRC-ATL105-PDF-001:6835 → derivation_method = llm_phrased → SC-12209 (narrative_rule) → TC-020778 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 682292 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 002945 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 47351992 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 6K60C81D | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 0EB8Z4D9 | format_synthesis |  |
| 119 | Firmware Version | WC6BBZI1 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 206487 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | 6K60C81D | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | 0EB8Z4D9 | format_synthesis |  |
| 132 | Firmware Version | WC6BBZI1 | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:6835](../traceability/segment-118.md) → SC-12209 → TC-020778 → `TC-020778.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020779 — Narrative_Rule — Proprietary Data Load Request — Device Card Table Version value beginning with 99999 must indicate no 

**Purpose:** Device Card Table Version value beginning with 99999 must indicate no card table used by the location. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → ENT-ELEM-176 → REQ-SRC-ATL105-PDF-001:6835 → derivation_method = llm_phrased → SC-12209 (narrative_rule) → TC-020779 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 998544 | format_synthesis |  |
| 118 | Card Table Load Version | 177392 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6835](../traceability/segment-118.md) → SC-12209 → TC-020779 → `TC-020779.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020780 — Narrative_Rule — Totals with Proprietary Data Load Request — Device Card Table Version value beginning with 99999 must indicate no 

**Purpose:** Device Card Table Version value beginning with 99999 must indicate no card table used by the location. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → ENT-ELEM-176 → REQ-SRC-ATL105-PDF-001:6835 → derivation_method = llm_phrased → SC-12209 (narrative_rule) → TC-020780 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | IWGS751K | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | P973JC5V | format_synthesis |  |
| 119 | Firmware Version | 63TC2UAO | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 986452 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6835](../traceability/segment-118.md) → SC-12209 → TC-020780 → `TC-020780.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020781 — Narrative_Rule — Proprietary Data Load Request — Card Table Load Version must appear in Segment No. 118.

**Purpose:** Card Table Load Version must appear in Segment No. 118. (source [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465))

**Derivation path:** Spec [p.465](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=465) (ATL105 2026-3) → ENT-ELEM-177 → REQ-SRC-ATL105-PDF-001:6836 → derivation_method = llm_phrased → SC-12210 (narrative_rule) → TC-020781 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 427556 | format_synthesis |  |
| 118 | Card Table Load Version | 507360 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6836](../traceability/segment-118.md) → SC-12210 → TC-020781 → `TC-020781.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020782 — Narrative_Rule — Proprietary Data Load Request — Load Control Key must appear in Segment No. 118.

**Purpose:** Load Control Key must appear in Segment No. 118. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-178 → REQ-SRC-ATL105-PDF-001:6837 → derivation_method = llm_phrased → SC-12211 (narrative_rule) → TC-020782 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 849861 | format_synthesis |  |
| 118 | Card Table Load Version | 441161 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6837](../traceability/segment-118.md) → SC-12211 → TC-020782 → `TC-020782.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020783 — Narrative_Rule — Financial Transaction Request — Host Discount Timestamp must be formatted as CCYYMMDDHHMM.

**Purpose:** Host Discount Timestamp must be formatted as CCYYMMDDHHMM. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-179 → REQ-SRC-ATL105-PDF-001:6838 → derivation_method = llm_phrased → SC-12212 (narrative_rule) → TC-020783 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 549884 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 080548 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 89664423 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | ZCXXVCVO | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | LNXL45RG | format_synthesis |  |
| 119 | Firmware Version | ALNTT8HF | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 691958 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | ZCXXVCVO | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | LNXL45RG | format_synthesis |  |
| 132 | Firmware Version | ALNTT8HF | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:6838](../traceability/segment-118.md) → SC-12212 → TC-020783 → `TC-020783.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020784 — Narrative_Rule — Proprietary Data Load Request — Host Discount Timestamp must be formatted as CCYYMMDDHHMM.

**Purpose:** Host Discount Timestamp must be formatted as CCYYMMDDHHMM. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-179 → REQ-SRC-ATL105-PDF-001:6838 → derivation_method = llm_phrased → SC-12212 (narrative_rule) → TC-020784 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 175325 | format_synthesis |  |
| 118 | Card Table Load Version | 109550 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6838](../traceability/segment-118.md) → SC-12212 → TC-020784 → `TC-020784.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020785 — Narrative_Rule — Totals with Proprietary Data Load Request — Host Discount Timestamp must be formatted as CCYYMMDDHHMM.

**Purpose:** Host Discount Timestamp must be formatted as CCYYMMDDHHMM. (source [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466))

**Derivation path:** Spec [p.466](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=466) (ATL105 2026-3) → ENT-ELEM-179 → REQ-SRC-ATL105-PDF-001:6838 → derivation_method = llm_phrased → SC-12212 (narrative_rule) → TC-020785 (transaction = Totals with Proprietary Data Load Request)

**Preconditions:** transaction type Totals with Proprietary Data Load Request; message layout step 1 Totals with Proprietary Data Load Request segments 119

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | NPYGEDO9 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | CV18GJEO | format_synthesis |  |
| 119 | Firmware Version | TNF9S53R | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 440159 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6838](../traceability/segment-118.md) → SC-12212 → TC-020785 → `TC-020785.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020787 — Narrative_Rule — Proprietary Data Load Request — Prompt Code, Pending must be a valid Transaction Type and/or Card Type

**Purpose:** Prompt Code, Pending must be a valid Transaction Type and/or Card Type Code. (source [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468))

**Derivation path:** Spec [p.468](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=468) (ATL105 2026-3) → ENT-ELEM-182 → REQ-SRC-ATL105-PDF-001:6840 → derivation_method = llm_phrased → SC-12214 (narrative_rule) → TC-020787 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 648529 | format_synthesis |  |
| 118 | Card Table Load Version | 733650 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6840](../traceability/segment-118.md) → SC-12214 → TC-020787 → `TC-020787.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020975 — Business_Rule — Proprietary Data Load Request — Proprietary Data Load functions occur at end of day only, as part of D

**Purpose:** Proprietary Data Load functions occur at end of day only, as part of Day Close processing. (source [p.196](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=196))

**Derivation path:** Spec [p.196](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=196) (ATL105 2026-3) → BR-196-2 → REQ-SRC-ATL105-PDF-001:1104 → derivation_method = business_rule_derived → SC-6478 (business_rule) → TC-020975 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 535693 | format_synthesis |  |
| 118 | Card Table Load Version | 034097 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1104](../traceability/segment-118.md) → SC-6478 → TC-020975 → `TC-020975.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?
