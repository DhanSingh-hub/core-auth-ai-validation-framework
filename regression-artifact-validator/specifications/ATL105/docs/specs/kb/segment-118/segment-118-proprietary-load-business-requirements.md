# ATL105 Segment 118 Proprietary Load Business Requirements

## Scope and provenance

This catalog defines the business requirements for the Proprietary Data Load Segment (Data Segment No. 118) that are independently confirmed from the ATL105 2026-3 extract available in this workspace. Unlike [Segment 116](../segment-116/segment-116-transarmor-business-requirements.md), whose specification sections were stubs, Segment 118's sections are fully documented, so this catalog is comparable in depth to [Segment 100's Financial Card-Type Business Requirements](../segment-100/financial-card-type-business-requirements.md).

These are requirements for the Proprietary Data Load dimension of the platform. They do not replace Segment 100, Segment 102, Segment 109, Segment 110, or Segment 111 requirements, which are trained independently. The Host Discount cross-reference to Segment 102 (`PDL-118-023`, `PDL-118-024`) is noted but not itself a Segment 102 requirement.

## Common requirements

| ID | Requirement | Acceptance criteria |
|---|---|---|
| PDL-118-001 | A Proprietary Data Load Request shall contain Segment 118 in Data Section 3 and shall exclude Segment 100. | `Request.DataSection3.SegmentType == "118"`; `Request` has no Data Section 2. |
| PDL-118-002 | Segment 118's Segment Type shall be the fixed value `118`. | `SegmentType == "118"`. |
| PDL-118-003 | Segment 118's Segment Length shall be exactly 4 numeric digits. | `len(SegmentLength) == 4` and numeric. |
| PDL-118-004 | Segment 118's serialized length shall not exceed 3,800 alphanumeric characters, in both request and response. | `len(serialized Segment 118) <= 3800`. |
| PDL-118-005 | Prompt Code shall be one of the five documented values: 901, 902, 903, 904, 905. | `PromptCode in {901,902,903,904,905}`. |
| PDL-118-006 | Prompt Code, Pending shall be one of the four documented values: 0901, 0902, 0904, 0981. | `PromptCodePending in {0901,0902,0904,0981}`. |
| PDL-118-007 | Request fields (1-13) shall be separated by Field Separators, including empty fields; response fields shall have no separators. | Serialization matches Section 12.16's note. |

## Prompt-Code-specific requirements

| ID | Prompt Code | Requirement | Acceptance criteria |
|---|---|---|---|
| PDL-118-010 | 901 | A Custom Receipt Text response shall include a validity window (Start/End Date/Time) and one or more text lines whose total does not exceed 220 bytes. | Fields 14-20 present; total repeat-block length <= 220. |
| PDL-118-011 | 902 | A Dynamic Card Table response shall identify one of the six documented Card Table Types and its data, up to 3,600 bytes. | `CardTableType in {0001..0006}`; `len(CardTableData) <= 3600`. |
| PDL-118-012 | 903 | A Site Configuration Data request shall be sent only when a configuration change occurred, and only the final state shall be transmitted; Block Number = 0 signals the final, data-less message. | Field 14 present when a change occurred; Block Number lifecycle honored. |
| PDL-118-013 | 904 | A Host Discount Data response shall include a timestamp, a discount count, and that many discount blocks, up to 3,600 bytes total. | Fields 14-27 present; block count matches Number of Discounts. |
| PDL-118-014 | 905 | A Fuel Volume Data request shall carry the fuel volume tables, up to 3,600 bytes. | Field 14 present; `len(FuelVolumeData) <= 3600`. |
| PDL-118-020 | 903 | Site Configuration Data responses shall use Response Code `T` (approved) or `U` (declined), both meaning no more data is pending. | `ResponseCode in {T,U}` for 903 responses. |
| PDL-118-021 | 902/904 | Multi-block Proprietary Data Load responses shall use Response Code `H` or `O` while more data is pending, and `T`/`U`/`X`/`Y` to signal completion or continuation to the next Prompt Code. | `ResponseCode in {H,O,T,U,X,Y}` for 902/904 responses. |
| PDL-118-023 | 904 | A Host Discount amount applied in post-pay environments shall use Product Code 941 (negative amount) in the subsequent Financial Transaction Request's Segment 102. | Companion Segment 102 fixture uses Product Code 941. |
| PDL-118-024 | 904 | A Host Discount amount applied in pre-pay environments shall use Product Code 991 (administrative amount) in the subsequent Financial Transaction Request's Segment 102. | Companion Segment 102 fixture uses Product Code 991. |

## Requirements pending SME input

| ID | Requirement (proposed, unconfirmed) | Why it cannot be certified yet |
|---|---|---|
| PDL-118-P01 | Information Byte shall be one of [unknown values], indicating single- vs. multi-message request. | No value catalog is shown for Segment 118's Information Byte (`SEG118-SME-001`). |
| PDL-118-P02 | Receipt Text Data shall be [fixed 20 bytes / variable per Receipt Text Data Length]. | Ambiguous between the field table's fixed length-20 and the length-prefixed repeat-block pattern (`SEG118-SME-002`). |
| PDL-118-P03 | Block Number = 0 final-message convention shall/shall not apply uniformly to Prompt Codes 902 and 904. | Only confirmed for 903 in the source text (`SEG118-SME-003`). |

## Catalog-derived requirements

Rules that no `PDL-118-*` requirement restates, derived from the rule catalog in the standard segment format.

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG118-001 | structure | Proprietary Data Load Segment requests or carries Customer Specific Proprietary data for loading (host-to-device: Dynamic Card Table, Custom Receipt Text, Host Discounts) or capturing (device-to-host: Site Configuration, Fuel Volume) at the BUYPASS FEP | The segment's structural position and composition match the rule. | `SEG118-R-001` §12.16 | SPEC_DERIVED |
| BR-SEG118-004 | structure | Segment 118 originates at the device | Documented for traceability; not independently asserted by a validator. | `SEG118-R-004` §12.16 | SPEC_DERIVED |
| BR-SEG118-008 | field | Sequence Number (Element 86) is Required, field 3, 6 digits, and identifies the unique transaction sequence number for the life of the transaction | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-008` §12.16 | SPEC_DERIVED |
| BR-SEG118-010 | field | Terminal Identifier (Element 102) is Required, field 5, 13 characters, identifying the device by Device Type, State Code, BUYPASS Merchant Number and Device Number | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-010` §12.16 | SPEC_DERIVED |
| BR-SEG118-013 | field | Device Card Table Version (Element 176) is Required, field 8, 35 digits: seven 5-digit versions (Card Table Master ID, BIN, RULES, RESTRICTIONS, SAF, PROMPT, PRODUCT); a value beginning with 99999 indicates no card table is used | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-013` §12.16,Chapter13-Element176 | SPEC_DERIVED |
| BR-SEG118-014 | field | Card Table Load Version (Element 177) is Required, field 9, 35 digits, representing the versions being loaded; sourced from the Host and echoed back in each subsequent block request | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-014` §12.16,Chapter13-Element177 | SPEC_DERIVED |
| BR-SEG118-015 | field | Card Table Type (Element 174) is Required, field 10, 4 digits, sourced from the Host and echoed back; valid values 0001 (BIN), 0002 (RULES), 0003 (RESTRICTIONS), 0004 (SAF), 0005 (PROMPT), 0006 (PRODUCT) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-015` §12.16,Chapter13-Element174 | SPEC_DERIVED |
| BR-SEG118-016 | field | Load Control Key (Element 178) is Required, field 11, 60 characters, sourced from the Host and echoed back in each subsequent block request | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-016` §12.16 | SPEC_DERIVED |
| BR-SEG118-017 | field | Host Discount Timestamp (Element 179) is Required, field 12, 12 digits, format CCYYMMDDHHMM, sourced from the Host | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-017` §12.16 | SPEC_DERIVED |
| BR-SEG118-018 | field | Block Number (Element 11) is Conditional, field 13, 3 digits, identifying the block sent by device or Host; the Host uses it to select the next block and it is echoed in the subsequent block request | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-018` §12.16 | SPEC_DERIVED |
| BR-SEG118-021 | field | When Prompt Code is 903 (Site Configuration Data, request only), field 14 is Site Configuration Data (Element 180, max 3,600 bytes, Source: Vendor) followed by a Field Separator | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG118-R-021` §12.16.3 | SPEC_DERIVED |
| BR-SEG118-025 | response | A Proprietary Data Load Response contains Data Section 1 (Response Code 83 pos. 1 len. 1, Download Indicator 30 pos. 2 len. 1, Initiation Date 45 pos. 3 len. 6, Initiation Time 46 pos. 9 len. 4, Sequence Number 86 pos. 13 len. 6, all Required) and Data Section 3 (field 6, Segment 118, Required) | The response carries the stated values in the stated positions. | `SEG118-R-025` §11.7.7 | SPEC_DERIVED |
| BR-SEG118-028 | applicability | Host Discount Data (Prompt Code 904) is triggered when a Totals Response Response Code indicates pending proprietary data (D/E) or pending Host Discount data (M/N); the device then sends a Proprietary Data Load Request with Prompt Code 904 | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG118-R-028` §12.16.4,Chapter13-Element83 | SPEC_DERIVED |
| BR-SEG118-030 | response | Response Code V (Declined, Totals with Proprietary Custom Receipt Text data pending) and W (Approved, Totals with Proprietary Custom Receipt Text pending) indicate that a Prompt Code 901 Custom Receipt Text load is pending | The response carries the stated values in the stated positions. | `SEG118-R-030` §Chapter13-Element83 | SPEC_DERIVED |

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG118-NEG-001 | `SEG118-R-001` | MUT-010 structural requirement | Validation error citing SEG118-R-001 |
| BR-SEG118-NEG-008 | `SEG118-R-008` | MUT-005 required field omitted | Validation error citing SEG118-R-008 |
| BR-SEG118-NEG-010 | `SEG118-R-010` | MUT-003 length violation | Validation error citing SEG118-R-010 |
| BR-SEG118-NEG-013 | `SEG118-R-013` | MUT-003 length violation | Validation error citing SEG118-R-013 |
| BR-SEG118-NEG-014 | `SEG118-R-014` | MUT-003 length violation | Validation error citing SEG118-R-014 |
| BR-SEG118-NEG-015 | `SEG118-R-015` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG118-R-015 |
| BR-SEG118-NEG-016 | `SEG118-R-016` | MUT-005 required field omitted | Validation error citing SEG118-R-016 |
| BR-SEG118-NEG-017 | `SEG118-R-017` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG118-R-017 |
| BR-SEG118-NEG-018 | `SEG118-R-018` | MUT-003 length violation | Validation error citing SEG118-R-018 |
| BR-SEG118-NEG-021 | `SEG118-R-021` | MUT-003 length violation | Validation error citing SEG118-R-021 |
| BR-SEG118-NEG-025 | `SEG118-R-025` | MUT-010 structural requirement | Validation error citing SEG118-R-025 |
| BR-SEG118-NEG-028 | `SEG118-R-028` | MUT-009 interdependency violation | Validation error citing SEG118-R-028 |
| BR-SEG118-NEG-030 | `SEG118-R-030` | MUT-009 interdependency violation | Validation error citing SEG118-R-030 |

## Rule-catalog crosswalk

The rule each existing requirement restates. `PENDING` rows stay unconfirmed until the linked SME item is resolved.

| Requirement | Rule | Match |
|---|---|---|
| PDL-118-001 | `SEG118-R-024` | EQUIVALENT |
| PDL-118-002 | `SEG118-R-006` | EQUIVALENT |
| PDL-118-003 | `SEG118-R-007` | EQUIVALENT |
| PDL-118-004 | `SEG118-R-002`, `SEG118-R-003` | EQUIVALENT (request and response maximum) |
| PDL-118-005 | `SEG118-R-011` | EQUIVALENT |
| PDL-118-006 | `SEG118-R-012` | EQUIVALENT |
| PDL-118-007 | `SEG118-R-005` | EQUIVALENT |
| PDL-118-010 | `SEG118-R-019` | EQUIVALENT |
| PDL-118-011 | `SEG118-R-020` | EQUIVALENT (Card Table Data); Card Table Type is restated by `BR-SEG118-015` |
| PDL-118-012 | `SEG118-R-027` | PARTIAL (Block Number = 0 final message) |
| PDL-118-013 | `SEG118-R-022` | EQUIVALENT |
| PDL-118-014 | `SEG118-R-023` | EQUIVALENT |
| PDL-118-020 | `SEG118-R-027` | EQUIVALENT (Response Codes T/U) |
| PDL-118-021 | `SEG118-R-026` | EQUIVALENT |
| PDL-118-023 | `SEG118-R-029` | EQUIVALENT (post-pay Product Code 941) |
| PDL-118-024 | `SEG118-R-029` | EQUIVALENT (pre-pay Product Code 991) |
| PDL-118-P01 | `SEG118-R-009` | PENDING (`SEG118-SME-001`) |
| PDL-118-P02 | `SEG118-R-019` | PENDING (Receipt Text Data length, `SEG118-SME-002`) |
| PDL-118-P03 | `SEG118-R-027` | PENDING (Prompt Codes 902/904, `SEG118-SME-003`) |

## Traceability

Every confirmed requirement (`PDL-118-001` through `PDL-118-024`) traces to a rule in [coverage/segment-118-rule-catalog.json](coverage/segment-118-rule-catalog.json) (`SEG118-R-001` through `SEG118-R-030`); the crosswalk above records the exact rule, and `BR-SEG118-*` rows cover the remaining rules. Every pending requirement (`PDL-118-P01` through `PDL-118-P03`) traces to an entry in the [Segment 118 SME/TBA Input Register](segment-118-sme-tba-input-register.md).
