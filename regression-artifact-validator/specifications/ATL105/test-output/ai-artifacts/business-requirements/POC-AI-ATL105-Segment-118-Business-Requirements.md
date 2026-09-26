# POC AI Segment 118 Business Requirements

Source: `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`

Scope: requirements whose `segment_number` equals `118` or whose `related_entity_ids` include `ENT-SEG-118` in the approved requirement catalog (DIRECT_SEGMENT_118).

Count: 107

| ID | Source rule | Page | Category | Requirement type | Confidence | Requirement |
|---|---|---:|---|---|---:|---|
| `REQ-SRC-ATL105-PDF-001:193` | `ENT-ELEM-174` | 464 | Field | Field Validation | 50% | Card Table Type must be one of: 0001 BIN Table, 0002 RULES Table, 0003 RESTRICTIONS Table, 0004 SAF Table, 0005 PROMPT Table, 0006 PRODUCT Table. |
| `REQ-SRC-ATL105-PDF-001:194` | `ENT-ELEM-182` | 468 | Field | Field Validation | 50% | Prompt Code, Pending must be one of: 0901 Custom Receipt Test Data, 0902 Proprietary Data Load, 0904 Host Discount Data, 0981 Electronic Mail. |
| `REQ-SRC-ATL105-PDF-001:993` | `BR-176-6` | 176 | Field | Business Rule | 41% | Data Section No. 3 of Totals with Proprietary Data Load Request contains Data Segment No. 116. |
| `REQ-SRC-ATL105-PDF-001:1057` | `BR-196-2` | 196 | Field | Business Rule | 90% | Proprietary Data Load functions occur only at end of day as part of Day Close processing. |
| `REQ-SRC-ATL105-PDF-001:1060` | `BR-196-5` | 196 | Field | Business Rule | 80% | Proprietary Data Load Request contains Data Section No. 3 with Proprietary Data Load Segment (Segment 118). |
| `REQ-SRC-ATL105-PDF-001:1064` | `BR-197-3` | 197 | Field | Business Rule | 80% | Proprietary Data Load Request does not contain the Standard Message Data Segment (Segment No. 100). |
| `REQ-SRC-ATL105-PDF-001:1066` | `BR-198-2` | 198 | Field | Business Rule | 90% | All Proprietary Data Load Responses contain Data Section No. 3. |
| `REQ-SRC-ATL105-PDF-001:1240` | `BR-251-1` | 251 | Account / Card | Business Rule | 70% | Dynamic Card Table, Custom Receipt Text, and Host Discounts are pushed from host to POS. |
| `REQ-SRC-ATL105-PDF-001:1241` | `BR-251-2` | 251 | Amount | Business Rule | 70% | Site Configuration data and Fuel Volume data are pushed from POS to host. |
| `REQ-SRC-ATL105-PDF-001:1242` | `BR-251-3` | 251 | Field | Business Rule | 90% | Segment 118 has a maximum length of 3,800 alphanumeric characters in Request and Response. |
| `REQ-SRC-ATL105-PDF-001:1243` | `BR-251-4` | 251 | Message | Business Rule | 90% | In requests, all fields are separated by Field Separators; unpopulated fields still send the separator. |
| `REQ-SRC-ATL105-PDF-001:1244` | `BR-251-5` | 251 | Message | Business Rule | 90% | A Field Separator follows Field No. 13 in the Proprietary Data Load Segment. |
| `REQ-SRC-ATL105-PDF-001:1245` | `BR-251-6` | 251 | Message | Business Rule | 90% | In transaction responses, there are no Field Separators between fields in this message. |
| `REQ-SRC-ATL105-PDF-001:1246` | `BR-251-7` | 251 | Field | Business Rule | 90% | Segment Type fixed value is 118 for the Proprietary Data Load Segment. |
| `REQ-SRC-ATL105-PDF-001:1248` | `BR-252-2` | 252 | Field | Business Rule | 84% | Prompt Code, Pending valid values are 0901, 0902, 0904, 0981. |
| `REQ-SRC-ATL105-PDF-001:1249` | `BR-252-3` | 252 | Account / Card | Business Rule | 74% | Card Table Load Version comes from Host and is echoed back in each subsequent block request. |
| `REQ-SRC-ATL105-PDF-001:1250` | `BR-252-4` | 252 | Account / Card | Business Rule | 74% | Card Table Type value comes from host and is echoed back in each subsequent block request. |
| `REQ-SRC-ATL105-PDF-001:1251` | `BR-252-5` | 252 | Security | Business Rule | 74% | Load Control Key value comes from host and is echoed back in each subsequent block request. |
| `REQ-SRC-ATL105-PDF-001:1252` | `BR-252-6` | 252 | Field | Business Rule | 90% | Host Discount Timestamp format is CCYYMMDDHHMM. |
| `REQ-SRC-ATL105-PDF-001:1253` | `BR-252-7` | 252 | Account / Card | Business Rule | 70% | Device Card Table Version consists of seven version values, each 5 characters, strung together. |
| `REQ-SRC-ATL105-PDF-001:1254` | `BR-253-1` | 253 | Field | Business Rule | 85% | Proprietary Load Request Data maximum variable length must not exceed 3,800 bytes. |
| `REQ-SRC-ATL105-PDF-001:1255` | `BR-253-2` | 253 | Field | Business Rule | 85% | Proprietary Load Response Data maximum variable length must not exceed 3,800 bytes. |
| `REQ-SRC-ATL105-PDF-001:1256` | `BR-253-3` | 253 | Field | Business Rule | 70% | Proprietary Load Request/Response Data section corresponds to Element No. 78 (Prompt Code) values. |
| `REQ-SRC-ATL105-PDF-001:1274` | `BR-257-2` | 257 | Field | Business Rule | 80% | Device applies host discount data to applicable transactions during designated date and time period after receiving proprietary load response data. |
| `REQ-SRC-ATL105-PDF-001:1569` | `BR-336-1` | 336 | Account / Card | Business Rule | 85% | BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 is returned. |
| `REQ-SRC-ATL105-PDF-001:1570` | `BR-336-2` | 336 | Account / Card | Business Rule | 85% | Card BIN Range, Beginning appears in Proprietary Data Load Segment when Prompt Code 904 is returned. |
| `REQ-SRC-ATL105-PDF-001:1571` | `BR-336-3` | 336 | Account / Card | Business Rule | 85% | Card BIN Range, Ending appears in Proprietary Data Load Segment when Prompt Code 904 is returned. |
| `REQ-SRC-ATL105-PDF-001:1573` | `BR-337-2` | 337 | Account / Card | Business Rule | 79% | Card Table Type appears when Prompt Code 902 is returned. |
| `REQ-SRC-ATL105-PDF-001:1821` | `BR-399-1` | 399 | Message | Business Rule | 90% | Segment Length has four digits only for segments 103, 114, 115, 118, 120, 130, and 131. |
| `REQ-SRC-ATL105-PDF-001:1839` | `BR-400-16` | 400 | Message | Business Rule | 57% | Proprietary Data Load Segment length is 0001-3800. |
| `REQ-SRC-ATL105-PDF-001:2114` | `BR-460-5` | 460 | Field | Business Rule | 90% | Elements 165 and 166 appear in Proprietary Data Load Segment (Segment No. 118). |
| `REQ-SRC-ATL105-PDF-001:2115` | `BR-461-1` | 461 | Field | Business Rule | 90% | Number of Receipt Text Lines appears in Proprietary Data Load Segment when Prompt Code 901 (Customized Receipt Text) is returned. |
| `REQ-SRC-ATL105-PDF-001:2117` | `BR-462-1` | 462 | Field | Business Rule | 81% | Receipt Text Data Length appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). |
| `REQ-SRC-ATL105-PDF-001:2119` | `BR-462-3` | 462 | Field | Business Rule | 84% | Receipt Text Data appears in Proprietary Data Load Segment when Prompt Code 901 is returned (Customized Receipt Text). |
| `REQ-SRC-ATL105-PDF-001:2120` | `BR-462-4` | 462 | Field | Business Rule | 90% | Number of Discounts appears in Proprietary Data Load Segment when Prompt Code 904 is returned (Host Discount). |
| `REQ-SRC-ATL105-PDF-001:2121` | `BR-463-1` | 463 | Amount | Business Rule | 90% | Product Discount Amount appears in Proprietary Data Load Segment when Prompt Code 904 (Host Discount) is returned. |
| `REQ-SRC-ATL105-PDF-001:2123` | `BR-463-3` | 463 | Account / Card | Business Rule | 90% | BUYPASS Card Type appears in Proprietary Data Load Segment when Prompt Code 904 (Host Discount) is returned. |
| `REQ-SRC-ATL105-PDF-001:2125` | `BR-464-1` | 464 | Account / Card | Business Rule | 79% | Card Table Type appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). |
| `REQ-SRC-ATL105-PDF-001:2126` | `BR-464-2` | 464 | Account / Card | Business Rule | 85% | Card Table Data appears in Proprietary Data Load Segment when Prompt Code 902 is returned (Dynamic Card Table Load). |
| `REQ-SRC-ATL105-PDF-001:2127` | `BR-465-1` | 465 | Account / Card | Business Rule | 90% | If Device Card Table Version begins with 99999, this indicates there is no card table used by the location. |
| `REQ-SRC-ATL105-PDF-001:2128` | `BR-465-2` | 465 | Account / Card | Business Rule | 80% | Device Card Table Version appears in Proprietary Data Load Segment so Host can check for updates to Card Table data. |
| `REQ-SRC-ATL105-PDF-001:2129` | `BR-465-3` | 465 | Account / Card | Business Rule | 74% | Card Table Load Version appears in Proprietary Data Load Segment to retain operation in process during multi-block exchange. |
| `REQ-SRC-ATL105-PDF-001:2130` | `BR-466-1` | 466 | Field | Business Rule | 80% | In Proprietary Load Request Data, Host Discount Timestamp represents the existing timestamp used by the device. |
| `REQ-SRC-ATL105-PDF-001:2131` | `BR-466-2` | 466 | Field | Business Rule | 80% | In Proprietary Load Response Data, Host Discount Timestamp represents the new timestamp after a successful load. |
| `REQ-SRC-ATL105-PDF-001:2132` | `BR-467-1` | 467 | Field | Business Rule | 80% | Site Configuration Data appears in Proprietary Data Load Segment when Prompt Code 903 is sent in the request. |
| `REQ-SRC-ATL105-PDF-001:2136` | `BR-468-1` | 468 | Account / Card | Business Rule | 79% | Prompt Code, Pending must be a valid Transaction Type and/or Card Type Code. |
| `REQ-SRC-ATL105-PDF-001:2137` | `BR-468-2` | 468 | Field | Business Rule | 85% | For a special transaction, a device transmits a 4-character Prompt Code. |
| `REQ-SRC-ATL105-PDF-001:2138` | `BR-468-3` | 468 | Account / Card | Business Rule | 85% | Card BIN Range, Beginning appears in Proprietary Load Response Data when Prompt Code is 904 (Host Discount Data). |
| `REQ-SRC-ATL105-PDF-001:2180` | `BR-477-3` | 477 | Amount | Business Rule | 85% | Fuel Volume Data appears in Proprietary Data Load Segment when Prompt Code 905 is sent in the request. |
| `REQ-SRC-ATL105-PDF-001:3185` | `ENT-FIELD-118-1` | 251 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3186` | `ENT-FIELD-118-1` | 251 | Field | Format | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 1 (Segment Type) must equal the fixed value 118. |
| `REQ-SRC-ATL105-PDF-001:3187` | `ENT-FIELD-118-2` | 251 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 2 (Segment Length) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3188` | `ENT-FIELD-118-3` | 251 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 3 (Sequence Number) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3189` | `ENT-FIELD-118-4` | 251 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 4 (Information Byte) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3190` | `ENT-FIELD-118-5` | 251 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 5 (Terminal Identifier) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3191` | `ENT-FIELD-118-6` | 252 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 6 (Prompt Code) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3192` | `ENT-FIELD-118-7` | 252 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 7 (Prompt Code, Pending) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3193` | `ENT-FIELD-118-8` | 252 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 8 (Device Card Table Version) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3194` | `ENT-FIELD-118-9` | 252 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 9 (Card Table Load Version) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3195` | `ENT-FIELD-118-10` | 252 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 10 (Card Table Type) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3196` | `ENT-FIELD-118-11` | 252 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 11 (Load Control Key) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3197` | `ENT-FIELD-118-12` | 252 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 12 (Host Discount Timestamp) is required and must be present. |
| `REQ-SRC-ATL105-PDF-001:3198` | `ENT-FIELD-118-13` | 253 | Field | Field Validation | 95% | In Proprietary Data Load Segment (Data Segment No. 118), field 13 (Block Number) is conditional and must be present only when its documented condition holds. |
| `REQ-SRC-ATL105-PDF-001:3845` | `REL-ENT-SEG-118-PROPRIETARY_DATA_LOAD_REQUEST` | 217 | Dependency | Dependency | 68% | The Proprietary Data Load Request transaction includes Proprietary Data Load Segment (Data Segment No. 118). |
| `REQ-SRC-ATL105-PDF-001:3865` | `REL-ENT-ELEM-11-ENT-SEG-118` | 363 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-11 depends on ENT-SEG-118; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:3972` | `REL-ENT-ELEM-174-ENT-ELEM-78` | 464 | Dependency | Cross-Segment Rule | 80% | ENT-ELEM-174 depends on ENT-ELEM-78; the documented dependency must hold. |
| `REQ-SRC-ATL105-PDF-001:4198` | `ENT-SEG-118` | 196 | Dependency | Dependency | 80% | The Proprietary Data Load Request transaction includes Proprietary Data Load Segment (Data Segment No. 118). |
| `REQ-SRC-ATL105-PDF-001:4263` | `ENT-ELEM-182` | 252 | Dependency | Cross-Segment Rule | 67% | Data element 182 accepts documented value '0901' â€” Valid value for Prompt Code, Pending. |
| `REQ-SRC-ATL105-PDF-001:4264` | `ENT-ELEM-182` | 252 | Dependency | Cross-Segment Rule | 67% | Data element 182 accepts documented value '0902' â€” Valid value for Prompt Code, Pending. |
| `REQ-SRC-ATL105-PDF-001:4265` | `ENT-ELEM-182` | 252 | Dependency | Cross-Segment Rule | 67% | Data element 182 accepts documented value '0904' â€” Valid value for Prompt Code, Pending. |
| `REQ-SRC-ATL105-PDF-001:4266` | `ENT-ELEM-182` | 252 | Dependency | Cross-Segment Rule | 67% | Data element 182 accepts documented value '0981' â€” Valid value for Prompt Code, Pending. |
| `REQ-SRC-ATL105-PDF-001:4750` | `ENT-ELEM-174` | 464 | Dependency | Cross-Segment Rule | 82% | Data element 174 accepts documented value '0001' â€” 0001 indicates BIN Table. |
| `REQ-SRC-ATL105-PDF-001:4751` | `ENT-ELEM-174` | 464 | Dependency | Cross-Segment Rule | 82% | Data element 174 accepts documented value '0002' â€” 0002 indicates RULES Table. |
| `REQ-SRC-ATL105-PDF-001:4752` | `ENT-ELEM-174` | 464 | Dependency | Cross-Segment Rule | 82% | Data element 174 accepts documented value '0003' â€” 0003 indicates RESTRICTIONS Table. |
| `REQ-SRC-ATL105-PDF-001:4753` | `ENT-ELEM-174` | 464 | Dependency | Cross-Segment Rule | 82% | Data element 174 accepts documented value '0004' â€” 0004 indicates SAF Table. |
| `REQ-SRC-ATL105-PDF-001:4754` | `ENT-ELEM-174` | 464 | Dependency | Cross-Segment Rule | 82% | Data element 174 accepts documented value '0005' â€” 0005 indicates PROMPT Table. |
| `REQ-SRC-ATL105-PDF-001:4755` | `ENT-ELEM-174` | 464 | Dependency | Cross-Segment Rule | 82% | Data element 174 accepts documented value '0006' â€” 0006 indicates PRODUCT Table. |
| `REQ-SRC-ATL105-PDF-001:4756` | `ENT-ELEM-176` | 465 | Dependency | Cross-Segment Rule | 67% | Data element 176 accepts documented value '99999' â€” Value beginning with 99999 indicates no card table used at location. |
| `REQ-SRC-ATL105-PDF-001:5394` | `ENT-SEG-118` | 197 | Field | Field Validation | 54% | Proprietary Data Load Segment: Indicates that this is a Proprietary Data Load Request. |
| `REQ-SRC-ATL105-PDF-001:5422` | `ENT-SEG-118` | 251 | Field | Field Validation | 81% | Proprietary Data Load Segment: Used to request/load or capture Customer Specific Proprietary data at BUYPASS FEP. |
| `REQ-SRC-ATL105-PDF-001:5423` | `ENT-ELEM-11` | 253 | Field | Field Validation | 74% | Block Number: Identifies specific block number sent by device or host; echoed in subsequent block request. |
| `REQ-SRC-ATL105-PDF-001:5424` | `ENT-ELEM-175` | 255 | Field | Field Validation | 54% | Card Table Data: Data for indicated table type; length determined by segment length field. |
| `REQ-SRC-ATL105-PDF-001:5425` | `ENT-ELEM-11` | 255 | Field | Field Validation | 51% | Block Number: Starts at 1, increments per segment of site configuration data sent. |
| `REQ-SRC-ATL105-PDF-001:5427` | `ENT-ELEM-179` | 257 | Field | Field Validation | 54% | Host Discount Timestamp: Identifies new host discount timestamp to use after successful load; format CCYYMMDDHHMM. |
| `REQ-SRC-ATL105-PDF-001:5802` | `ENT-ELEM-165` | 460 | Field | Field Validation | 81% | Start Date or End Date: Fixed 8-digit date (CCYYMMDD) for start/end of custom receipt text or host discount data. |
| `REQ-SRC-ATL105-PDF-001:5803` | `ENT-ELEM-166` | 460 | Field | Field Validation | 81% | Start Time or End Time: Fixed 4-digit time (HHMM) for start/end of custom receipt text or host discount data. |
| `REQ-SRC-ATL105-PDF-001:5804` | `ENT-ELEM-168` | 461 | Field | Field Validation | 84% | Number of Receipt Text Lines: Provides a count of Receipt Text Line items to follow as part of Customized Receipt Text. |
| `REQ-SRC-ATL105-PDF-001:5805` | `ENT-ELEM-169` | 462 | Field | Field Validation | 84% | Receipt Text Data Length: Provides length of characters representing a line of Receipt Text for POS printing. |
| `REQ-SRC-ATL105-PDF-001:5806` | `ENT-ELEM-170` | 462 | Field | Field Validation | 84% | Receipt Text Data: Provides text to be printed on customer's receipt. |
| `REQ-SRC-ATL105-PDF-001:5807` | `ENT-ELEM-171` | 462 | Field | Field Validation | 81% | Number of Discounts: Provides number of host discount items that will follow. |
| `REQ-SRC-ATL105-PDF-001:5808` | `ENT-ELEM-172` | 463 | Field | Field Validation | 84% | Product Discount Amount: Flat rate discount amount applied to previously indicated Product Code. |
| `REQ-SRC-ATL105-PDF-001:5809` | `ENT-ELEM-173` | 463 | Field | Field Validation | 84% | BUYPASS Card Type: BUYPASS recognized Card Type value matched to Dynamic BIN tables for Host Discounts. |
| `REQ-SRC-ATL105-PDF-001:5810` | `ENT-ELEM-174` | 464 | Field | Field Validation | 84% | Card Table Type: Defines the specific sub-table type within Dynamic Card Table being provided in element 175. |
| `REQ-SRC-ATL105-PDF-001:5811` | `ENT-ELEM-175` | 464 | Field | Field Validation | 81% | Card Table Data: Provides actual Card Table data for the table identified in field 174. |
| `REQ-SRC-ATL105-PDF-001:5812` | `ENT-ELEM-176` | 465 | Field | Field Validation | 84% | Device Card Table Version: Provides the complete Card Table Version data that the device currently has loaded. |
| `REQ-SRC-ATL105-PDF-001:5813` | `ENT-ELEM-177` | 465 | Field | Field Validation | 84% | Card Table Load Version: Provides the complete Card Table Version data being sent to the device. |
| `REQ-SRC-ATL105-PDF-001:5814` | `ENT-ELEM-178` | 466 | Field | Field Validation | 74% | Load Control Key: Alphanumeric key for tracking block data in Proprietary Data Load Segment. |
| `REQ-SRC-ATL105-PDF-001:5815` | `ENT-ELEM-179` | 466 | Field | Field Validation | 71% | Host Discount Timestamp: Numeric timestamp CCYYMMDDHHMM supporting host discounts in Proprietary Data Load Segment. |
| `REQ-SRC-ATL105-PDF-001:5816` | `ENT-ELEM-180` | 467 | Field | Field Validation | 81% | Site Configuration Data: Alphanumeric variable-length field providing site configuration data for merchant location. |
| `REQ-SRC-ATL105-PDF-001:5817` | `ENT-ELEM-182` | 468 | Field | Field Validation | 84% | Prompt Code, Pending: 4-character alphanumeric code identifying pending requested data in special transactions. |
| `REQ-SRC-ATL105-PDF-001:5834` | `ENT-ELEM-201` | 477 | Field | Field Validation | 84% | Fuel Volume Data: Provides the fuel volume data for merchant location. |
| `REQ-SRC-ATL105-PDF-001:6426` | `ENT-ELEM-174` | 464 | Account / Card | Processing Rule | 90% | Card Table Type must appear in Segment No. 118 when Prompt Code 902 is returned. |
| `REQ-SRC-ATL105-PDF-001:6428` | `ENT-ELEM-176` | 465 | Account / Card | Processing Rule | 90% | Device Card Table Version must indicate no card table used when value begins with 99999. |
| `REQ-SRC-ATL105-PDF-001:6429` | `ENT-ELEM-177` | 465 | Account / Card | Processing Rule | 90% | Card Table Load Version must appear in Segment No. 118. |
| `REQ-SRC-ATL105-PDF-001:6430` | `ENT-ELEM-178` | 466 | Security | Processing Rule | 90% | Load Control Key must appear in Segment No. 118. |
| `REQ-SRC-ATL105-PDF-001:6431` | `ENT-ELEM-179` | 466 | Field | Processing Rule | 90% | Host Discount Timestamp must use format CCYYMMDDHHMM. |
| `REQ-SRC-ATL105-PDF-001:6433` | `ENT-ELEM-182` | 468 | Account / Card | Processing Rule | 90% | Prompt Code, Pending must be a valid Transaction Type and/or Card Type Code. |
