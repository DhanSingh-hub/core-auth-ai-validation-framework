# POC AI Segment 109 Business Requirements

Source: `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`

Scope: requirements whose `segment_number` equals `109` or whose `related_entity_ids` include `ENT-SEG-109` in the approved requirement catalog (DIRECT_SEGMENT_109).

Count: 61

Note: the existing [Segment 109 supplied-pipeline coverage report](../../../docs/specs/kb/segment-109/coverage/segment-109-supplied-pipeline-ai-coverage-report.md) cites 38 Segment 109 business requirements sourced from an external pipeline segment-filter file (`step5_requirements/approved/readable_by_segment/markdown/SEG-109.md`) that is not present in this workspace. This artifact instead applies the same declared-linkage filter (`segment_number`/`related_entity_ids`) used for Segment 110, which yields 61 requirements. Both counts should be reconciled once the external segment-filter file is made available (see `SEG109-SME-011`).

| ID | Source rule | Page | Category | Requirement type | Confidence | Requirement |
|---|---|---:|---|---|---:|---|
| `REQ-SRC-ATL105-PDF-001:010` | `ENT-ELEM-11` | 363 | Field | Boundary | 50% | Block Number must be numeric and must not exceed 3 bytes. |
| `REQ-SRC-ATL105-PDF-001:011` | `ENT-ELEM-11` | 363 | Field | Field Validation | 50% | Block Number must be one of: Public Key File Load., the Electronic Mail response as the next, block., sending that Block Number in the next, Electronic Mail request or CA Public Key, File Load request.. |
| `REQ-SRC-ATL105-PDF-001:040` | `ENT-ELEM-36` | 376 | Field | Boundary | 50% | Extract Date must be numeric and must not exceed 6 bytes. |
| `REQ-SRC-ATL105-PDF-001:041` | `ENT-ELEM-37` | 377 | Field | Boundary | 50% | Extract Time must be numeric and must not exceed 4 bytes. |
| `REQ-SRC-ATL105-PDF-001:056` | `ENT-ELEM-50` | 382 | Field | Boundary | 50% | Local Time must be numeric and must not exceed 4 bytes. |
| `REQ-SRC-ATL105-PDF-001:112` | `ENT-ELEM-103` | 411 | Field | Boundary | 50% | Text Data must be alphanumeric and must not exceed 150 bytes. |
| `REQ-SRC-ATL105-PDF-001:113` | `ENT-ELEM-104` | 411 | Field | Boundary | 50% | Text Data Length must be numeric and must not exceed 3 bytes. |
| `REQ-SRC-ATL105-PDF-001:833` | `BR-137-5` | 137 | Field | Business Rule | 85% | The Electronic Mail Request (submission) contains up to 217 bytes of data. |
| `REQ-SRC-ATL105-PDF-001:1007` | `BR-182-2` | 182 | Field | Business Rule | 90% | Electronic Mail Data Segment is sent only on transactions requiring electronic mail. |
| `REQ-SRC-ATL105-PDF-001:1009` | `BR-183-1` | 183 | Field | Business Rule | 51% | Mail Text Data Length is conditional, required to specify length of Mail Text Data. |
| `REQ-SRC-ATL105-PDF-001:1010` | `BR-183-2` | 183 | Field | Business Rule | 51% | Mail Text Data is conditional and has variable length. |
| `REQ-SRC-ATL105-PDF-001:1011` | `BR-183-3` | 183 | Message | Business Rule | 84% | Electronic Mail Response message is variable length due to Mail Text Data and has no Field Separators. |
| `REQ-SRC-ATL105-PDF-001:1198` | `BR-237-1` | 237 | Field | Business Rule | 90% | Electronic Mail Data Segment has a maximum length of 232 alphanumeric characters. |
| `REQ-SRC-ATL105-PDF-001:1199` | `BR-237-2` | 237 | Message | Business Rule | 90% | All fields in the segment are separated by Field Separators; unpopulated fields still send the Field Separator. |
| `REQ-SRC-ATL105-PDF-001:1200` | `BR-237-3` | 237 | Field | Business Rule | 44% | Employee Number is conditional in the Electronic Mail Data Segment. |
| `REQ-SRC-ATL105-PDF-001:1201` | `BR-237-4` | 237 | Field | Business Rule | 44% | Password is conditional in the Electronic Mail Data Segment, used to identify end-of-day function's password. |
| `REQ-SRC-ATL105-PDF-001:1263` | `BR-255-4` | 255 | Field | Business Rule | 90% | Block Number starts at 1 and increments with each segment of site configuration data sent. |
| `REQ-SRC-ATL105-PDF-001:1264` | `BR-255-5` | 255 | Field | Business Rule | 90% | A Block Number value of 0 indicates all data was sent in previous blocks and is a final message with no data. |
| `REQ-SRC-ATL105-PDF-001:1634` | `BR-363-1` | 363 | Security | Business Rule | 80% | Host tracks Block Number of last complete data block sent in Electronic Mail response and CA Public Key File Load response. |
| `REQ-SRC-ATL105-PDF-001:1635` | `BR-363-2` | 363 | Field | Business Rule | 70% | Device may reinstate interrupted Electronic Mail response transmission using Block Number in next request. |
| `REQ-SRC-ATL105-PDF-001:1636` | `BR-363-3` | 363 | Account / Card | Business Rule | 80% | Device always uses Block Number to track data block sent in a Proprietary Data Load request. |
| `REQ-SRC-ATL105-PDF-001:1637` | `BR-363-4` | 363 | Security | Business Rule | 80% | Device is required to track Block Number received in CA Public Key File Load response. |
| `REQ-SRC-ATL105-PDF-001:1638` | `BR-363-5` | 363 | Security | Business Rule | 80% | Block Number value 000 means host sends first data block for CA Public Key File Load. |
| `REQ-SRC-ATL105-PDF-001:1639` | `BR-363-6` | 363 | Amount | Business Rule | 80% | Block Number value 000 sent by device indicates last block of site/fuel volume data in Proprietary Data Load request. |
| `REQ-SRC-ATL105-PDF-001:1640` | `BR-363-7` | 363 | Field | Business Rule | 80% | Block Number value 001 means host restarts data block processing sending block that was Block No. 1. |
| `REQ-SRC-ATL105-PDF-001:1641` | `BR-363-8` | 363 | Security | Business Rule | 70% | Device can request a particular block by sending that Block Number in next Electronic Mail or CA Public Key File Load request. |
| `REQ-SRC-ATL105-PDF-001:1701` | `BR-376-2` | 376 | Field | Business Rule | 54% | Extract Date must be a valid date in MMDDYY format for Electronic Mail Data Segment. |
| `REQ-SRC-ATL105-PDF-001:1725` | `BR-382-1` | 382 | Field | Business Rule | 74% | For IP response, Mail Text Data may be up to 750 alphanumeric characters. |
| `REQ-SRC-ATL105-PDF-001:1726` | `BR-382-2` | 382 | Field | Business Rule | 74% | For dial up response, Mail Text Data may be up to 150 alphanumeric characters. |
| `REQ-SRC-ATL105-PDF-001:1727` | `BR-382-3` | 382 | Field | Business Rule | 71% | For IP response, Mail Text Data Length valid values are 001-750. |
| `REQ-SRC-ATL105-PDF-001:1728` | `BR-382-4` | 382 | Field | Business Rule | 71% | For dial up response, Mail Text Data Length valid values are 001-150. |
| `REQ-SRC-ATL105-PDF-001:1736` | `BR-384-7` | 384 | Field | Business Rule | 85% | For Electronic Mail Request messages, Element 63 (Number of Segments) and Segment 109 (Electronic Mail Data Segment) always follow Element 55. |
| `REQ-SRC-ATL105-PDF-001:1758` | `BR-388-5` | 388 | Field | Business Rule | 90% | Electronic Mail Requests: Number of Segments always followed by Segment 109, Electronic Mail Data Segment. |
| `REQ-SRC-ATL105-PDF-001:1815` | `BR-398-7` | 398 | Field | Business Rule | 64% | Response code 'V' indicates declined totals with proprietary custom receipt text data pending. |
| `REQ-SRC-ATL105-PDF-001:1831` | `BR-400-8` | 400 | Message | Business Rule | 57% | Electronic Mail Data Segment length is 001-232. |
| `REQ-SRC-ATL105-PDF-001:1880` | `BR-411-1` | 411 | Field | Business Rule | 81% | Text Data Length valid codes/values range from 001 to 750. |
| `REQ-SRC-ATL105-PDF-001:2118` | `BR-462-2` | 462 | Field | Business Rule | 81% | Receipt Text Data Length valid values range from 1 to a maximum of 40. |
| `REQ-SRC-ATL105-PDF-001:3128` | `ENT-FIELD-109-1` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 1 (Segment Type) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3129` | `ENT-FIELD-109-1` | 237 | Field | Format | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 1 (Segment Type) must equal the fixed value 109. |
| `REQ-SRC-ATL105-PDF-001:3130` | `ENT-FIELD-109-2` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 2 (Segment Length) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3131` | `ENT-FIELD-109-3` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 3 (Information Byte) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3132` | `ENT-FIELD-109-4` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 4 (Terminal Identifier) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3133` | `ENT-FIELD-109-5` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 5 (Prompt Code) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3134` | `ENT-FIELD-109-6` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 6 (Block Number) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3135` | `ENT-FIELD-109-7` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 7 (Employee Number) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3136` | `ENT-FIELD-109-8` | 237 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 8 (Password) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3137` | `ENT-FIELD-109-9` | 238 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 9 (Sequence Number) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3138` | `ENT-FIELD-109-10` | 238 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 10 (Local Time) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3139` | `ENT-FIELD-109-11` | 238 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 11 (Extract Date) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3140` | `ENT-FIELD-109-12` | 238 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 12 (Extract Time) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3141` | `ENT-FIELD-109-13` | 238 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 13 (Text Data Length) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3142` | `ENT-FIELD-109-14` | 238 | Field | Field Validation | 95% | In Electronic Mail Data Segment (Data Segment No. 109), field 14 (Text Data) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3457` | `ENT-FIELD-ELECTRONIC_MAIL_RESPONSE-6` | 183 | Field | Field Validation | 95% | In the Electronic Mail Response message, field 6 is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3863` | `REL-ENT-ELEM-11-ENT-SEG-109` | 363 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-11 depends on ENT-SEG-109; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:3864` | `REL-ENT-ELEM-11-CA_PUBLIC_KEY_FILE_LOAD` | 363 | Dependency | Cross-Segment Rule | 77% | ENT-ELEM-11 depends on CA_PUBLIC_KEY_FILE_LOAD; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:4187` | `ENT-SEG-109` | 181 | Dependency | Dependency | 85% | The Electronic Mail Request transaction includes Electronic Mail Data Segment (Data Segment No. 109). |
| `REQ-SRC-ATL105-PDF-001:5413` | `ENT-SEG-109` | 237 | Field | Field Validation | 84% | Electronic Mail Data Segment: Segment appears in Field No. 3 of Data Section No. 2, originates at device. |
| `REQ-SRC-ATL105-PDF-001:5592` | `ENT-ELEM-11` | 363 | Field | Field Validation | 81% | Block Number: Identifies specific proprietary or public key data block requested by device or sent by host. |
| `REQ-SRC-ATL105-PDF-001:5617` | `ENT-ELEM-36` | 376 | Field | Field Validation | 74% | Extract Date: Fixed six digit date (MMDDYY) identifying when info was extracted for Electronic Mail Data Segment. |
| `REQ-SRC-ATL105-PDF-001:5618` | `ENT-ELEM-37` | 377 | Field | Field Validation | 84% | Extract Time: Identifies the time information was extracted for an Electronic Mail Data Segment, format HHMM. |
| `REQ-SRC-ATL105-PDF-001:6329` | `ENT-ELEM-11` | 363 | Account / Card | Processing Rule | 90% | Host must use a Block Number to track data block sent in Electronic Mail response. |
