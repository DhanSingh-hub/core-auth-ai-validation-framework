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

## Traceability

Every confirmed requirement (`PDL-118-001` through `PDL-118-024`) traces to a rule in [coverage/segment-118-rule-catalog.json](coverage/segment-118-rule-catalog.json) (`SEG118-R-001` through `SEG118-R-030`). Every pending requirement (`PDL-118-P01` through `PDL-118-P03`) traces to an entry in the [Segment 118 SME/TBA Input Register](segment-118-sme-tba-input-register.md).
