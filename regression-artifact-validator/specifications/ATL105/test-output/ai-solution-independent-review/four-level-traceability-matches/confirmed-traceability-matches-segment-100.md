# Confirmed BR to TS/TC/TD Side-by-Side Comparison - Segment 100

Only CONFIRMED business-requirement matches for Segment 100 are included: evidence-backed Test Solution decisions, not candidate or review-required matches.

- Confirmed BR mappings: **6**

## SEG100-R-015 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |25|1|
| TS |24|1|
| TC |58|2|
| TD |58|2|

### BR side-by-side (25)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1089|EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1095|Incomm Market Basket Data (Response) Segment returned when Request segment was included in the request.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1131|Standard Message Data Segment is the only required data segment on all Financial Transaction Request messages.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1148|Each Fleet Tag has a 3-byte fixed-length tag portion and up to 31 bytes of data.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1153|a field is not populated — still send the Product Data Field Delimiter.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1174|A Field Separator follows Product Amount if it is the last element in the segment.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1180|All fields are separated by Field Separators; unpopulated fields still send Field Separator.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1235|Print Data Segment always appears at the end of a Financial Transaction response.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1253|Device Card Table Version consists of seven version values, each 5 characters, strung together.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1277|Product Code 991 indicates an administrative amount, used in a pre-pay environment when price is adjusted to include discount amount.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1283|Fields are not separated by Field Separators; unpopulated fields are immediately followed by the next field.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1285|Segment 119 always appears in Field No. 3 in Data Section No. 3.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1293|Field Nos. 17-19 are sent up to a maximum of 20 times, once for each card type, in order shown.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1298|Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amounts of print data.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1391|Prompt Tokens differ by authorizer; different authorizers support different prompts.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1402|Table Length for Payee Name has a maximum variable value of 017.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1407|Flags in Table Data must be separated by a comma; if a flag is absent, it can be skipped using just the separator.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1467|Field is included in response only when merchant sent Account Updater Request Indicator with value 'Y' or 'I' in request.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (24)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7229|REQ-SRC-ATL105-PDF-001:1072|MATCHED|SCN-SEG100-ID-001|BR-SEG100-ID-001|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`|
|SC-7246|REQ-SRC-ATL105-PDF-001:1089|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7252|REQ-SRC-ATL105-PDF-001:1095|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7288|REQ-SRC-ATL105-PDF-001:1131|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7298|REQ-SRC-ATL105-PDF-001:1141|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7305|REQ-SRC-ATL105-PDF-001:1148|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7310|REQ-SRC-ATL105-PDF-001:1153|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7321|REQ-SRC-ATL105-PDF-001:1167|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7327|REQ-SRC-ATL105-PDF-001:1174|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7333|REQ-SRC-ATL105-PDF-001:1180|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7342|REQ-SRC-ATL105-PDF-001:1190|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7347|REQ-SRC-ATL105-PDF-001:1196|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7385|REQ-SRC-ATL105-PDF-001:1235|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7403|REQ-SRC-ATL105-PDF-001:1253|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7427|REQ-SRC-ATL105-PDF-001:1277|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7433|REQ-SRC-ATL105-PDF-001:1283|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7442|REQ-SRC-ATL105-PDF-001:1293|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7447|REQ-SRC-ATL105-PDF-001:1298|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7505|REQ-SRC-ATL105-PDF-001:1356|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7540|REQ-SRC-ATL105-PDF-001:1391|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7551|REQ-SRC-ATL105-PDF-001:1402|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7556|REQ-SRC-ATL105-PDF-001:1407|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7616|REQ-SRC-ATL105-PDF-001:1467|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7432|REQ-SRC-ATL105-PDF-001:1282|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (58)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031462|verification_status=PASS|MATCHED|TC-SEG100-ID-001|PASS|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`|
|TC-031476|verification_status=PASS|MATCHED|TC-SEG100-ID-002|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`|
|TC-031477|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031578|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031579|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031580|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031581|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031582|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031583|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031699|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031700|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031701|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031702|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031703|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031704|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031851|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031852|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031854|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031855|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031897|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031898|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031899|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031900|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031985|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031986|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031987|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032188|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032289|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032415|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032416|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032418|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032419|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032513|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032877|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032878|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032879|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032880|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032886|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032888|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032891|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032893|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032894|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033040|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033271|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033272|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033273|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033274|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033275|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032432|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032433|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032434|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032435|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032441|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032443|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032446|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032448|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032449|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (58)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031462|33 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-ID-001|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`; alias crosswalk exists for this segment but no AI element name resolves to Test element 85 (AI observed: AccountNumber, ApprovalNumber, CardDiscretionaryBlockData, CashAmount, FuelPurchaseAmount). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-031476|33 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-ID-002|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`; alias crosswalk exists for this segment but no AI element name resolves to Test element 85 (AI observed: AccountNumber, ApprovalNumber, CardDiscretionaryBlockData, CashAmount, FuelPurchaseAmount). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-031477|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031578|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031579|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031580|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031581|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031582|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031583|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031699|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031700|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031701|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031702|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031703|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031704|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031851|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031852|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031854|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031855|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031897|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031898|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031899|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031900|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031985|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031986|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031987|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032188|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032289|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032415|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032416|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032418|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032419|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032513|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032530|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032877|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032878|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032879|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032880|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032886|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032888|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032891|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032893|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032894|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033040|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033271|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033272|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033273|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033274|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033275|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032432|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032433|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032434|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032435|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032441|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032443|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032446|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032448|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032449|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-039 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |18|1|
| TS |18|1|
| TC |58|2|
| TD |15|2|

### BR side-by-side (18)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:484|Bill payment/recurring and installment payment transactions must be identified by Variable Information Indicator in Segment 111.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:485|Point-of-Sale Entry Mode in Element 113 must be populated correctly on all transactions when RFID receiver is connected.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:489|For Split Tender, the cardholder must use a different payment form for nonqualified items.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:492|Market-Specific Data Indicator required in FSA/HRA transaction requests.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:519|Card Type ID for Visa is 'VISA'.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:554|Any response to Debit Card Completion transaction ends retry attempts, whether approved or declined.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:565|Merchants supporting cashback must also support Partial Approval transactions (Interlink mandate, April 2013).|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:566|If cashback is partially approved, partially approved amount applies to purchase only, not cashback.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:571|Acquirers must ensure merchants process authorization reversals for card-present errors/cancellations within 24 hours.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:575|Approval Number present in original transaction must be present and identical in the Authorization Only Reversal transaction.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:580|Total QHP Amount (Code 894) is required and includes Clinical, Dental, Prescription/Rx, and Vision/Optical amounts.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:637|Transactions cannot be keyed.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:719|an activation (Please see section 10.7.3.6 — "Activation Transaction.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1566|Inclusion of the EMV Terminal Floor Limits segment in a table load depends on a Special set at the terminal level.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1963|When Variable Information Indicator = 066, Digital Commerce Data is included.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1964|When Variable Information Indicator = 067, Anticipated Amount data is included.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1965|When Variable Information Indicator = 068, Additional Transaction Fee 1 Transaction Fee data is included.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2257|Encrypted PIN Block Data field has maximum length 36 bytes with no example data.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (18)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-6887|REQ-SRC-ATL105-PDF-001:719|MATCHED|SCN-SEG100-E121-002|BR-SEG100-E121-002|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`|
|SC-7712|REQ-SRC-ATL105-PDF-001:1566|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8109|REQ-SRC-ATL105-PDF-001:1963|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8110|REQ-SRC-ATL105-PDF-001:1964|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8111|REQ-SRC-ATL105-PDF-001:1965|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8402|REQ-SRC-ATL105-PDF-001:2257|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6660|REQ-SRC-ATL105-PDF-001:484|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6661|REQ-SRC-ATL105-PDF-001:485|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6665|REQ-SRC-ATL105-PDF-001:489|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6668|REQ-SRC-ATL105-PDF-001:492|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6695|REQ-SRC-ATL105-PDF-001:519|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6728|REQ-SRC-ATL105-PDF-001:554|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6739|REQ-SRC-ATL105-PDF-001:565|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6740|REQ-SRC-ATL105-PDF-001:566|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6745|REQ-SRC-ATL105-PDF-001:571|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6749|REQ-SRC-ATL105-PDF-001:575|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6754|REQ-SRC-ATL105-PDF-001:580|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6808|REQ-SRC-ATL105-PDF-001:637|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (58)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-035653|verification_status=PASS|MATCHED|TC-SEG100-E121-003|PASS|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`|
|TC-035654|verification_status=PASS|MATCHED|TC-SEG100-E121-004|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`|
|TC-035655|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035656|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035657|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035658|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035659|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035660|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035661|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035662|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035663|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035664|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035665|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035666|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035667|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036563|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036564|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036565|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036566|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036567|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036568|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029698|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029699|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029700|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029701|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029702|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029703|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029704|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029705|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029706|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029707|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029730|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029731|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029732|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029733|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029734|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029980|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029981|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029982|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029983|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029984|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029985|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029986|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029987|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029988|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029989|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029990|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029991|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030017|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030018|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030019|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030020|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030021|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030022|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030060|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030061|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030063|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030064|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (15)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-035653|11 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-E121-003|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`; alias crosswalk exists for this segment but no AI element name resolves to Test element 121 (AI observed: SegmentType, SegmentLength, VariableInformation). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-035654|27 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-E121-004|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`; alias crosswalk exists for this segment but no AI element name resolves to Test element 121 (AI observed: AccountNumber, ApprovalNumber, CardDiscretionaryBlockData, CashAmount, FuelPurchaseAmount). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-035655|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035656|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035657|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035658|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035659|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035660|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035661|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035662|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035663|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035664|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035665|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035666|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035667|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-041 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |13|1|
| TS |13|1|
| TC |13|2|
| TD |13|2|

### BR side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:511|Card Type ID must print on all credit card receipts.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:512|Card Type ID for Amex is 'AX'.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:649|Split Tender Processing requirements for credit cards also apply to Signature Debit card.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:650|Qualified Healthcare Products requirements for credit cards also apply to Signature Debit card.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1526|Product Data section repeats per product for a maximum of 10 products, total variable length up to 370 bytes.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1532|If unit price omits assumed decimal place digits (e.g. '359' instead of '3059'), it is invalid.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1546|Pause Indicator is conditional; fixed value B indicates device should pause about one second before proceeding.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1548|Dial String Terminator has fixed value F, sourced from Host.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1550|Date and Time Data Segment has a maximum length of 23 alphanumeric characters.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1551|Fields in the segment are not separated by Field Separators; unpopulated fields are followed immediately by the next field.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1552|Data Type Indicator field has fixed value colon (:) indicating day of week, date, and time data follows.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1968|When Variable Information Indicator = 071, Enabler Verification Value data is included.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2860|Only Hash Algorithm Indicator value 01 (SHA-1) is currently supported.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7672|REQ-SRC-ATL105-PDF-001:1526|MATCHED|TS-SEG100-GAP-ACCOUNT|BR-SEG100-GAP-ACCOUNT|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`|
|SC-7678|REQ-SRC-ATL105-PDF-001:1532|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7692|REQ-SRC-ATL105-PDF-001:1546|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7694|REQ-SRC-ATL105-PDF-001:1548|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7696|REQ-SRC-ATL105-PDF-001:1550|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7697|REQ-SRC-ATL105-PDF-001:1551|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7698|REQ-SRC-ATL105-PDF-001:1552|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8114|REQ-SRC-ATL105-PDF-001:1968|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-9000|REQ-SRC-ATL105-PDF-001:2860|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6687|REQ-SRC-ATL105-PDF-001:511|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6688|REQ-SRC-ATL105-PDF-001:512|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6820|REQ-SRC-ATL105-PDF-001:649|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6821|REQ-SRC-ATL105-PDF-001:650|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-033352|verification_status=PASS|MATCHED|TC-SEG100-GAP-ACCOUNT-PASS|PASS|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`|
|TC-033353|verification_status=PASS|MATCHED|TC-SEG100-GAP-ACCOUNT-FAIL|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`|
|TC-033355|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033356|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033382|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033383|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033385|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033386|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035678|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035679|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035680|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035681|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035682|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-033352|11 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-GAP-ACCOUNT-PASS|4 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`; alias crosswalk exists for this segment but no AI element name resolves to Test element 2 (AI observed: SegmentType, SegmentLength, VariableInformation). JSON key-name overlap 0% (0/4 Test keys found by name).|
|TD-TC-033353|33 keys|MATCHED_ELEMENT_CONFIRMED|TD-SEG100-GAP-ACCOUNT-FAIL|4 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`; CONFIRMED via field-alias crosswalk - AI element name `AccountNumber` maps to Test element 2. JSON key-name overlap (fallback signal) 0%.|
|TD-TC-033355|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033356|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033382|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033383|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033385|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033386|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035678|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035679|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035680|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035681|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035682|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-043 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |16|1|
| TS |15|1|
| TC |26|1|
| TD |20|1|

### BR side-by-side (16)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:352|The encrypted PIN block and the KSN are sent to the customer's host system for forwarding to BUYPASS.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:354|A seed key and corresponding serial number can generate approximately one million unique PIN encryption keys.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:359|Pathway terminal configuration for the PIN pad value must be defined for 3DES DUKPT.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:370|Pad digits F consist of (14-L) digits of 1111 (F16) after the entered PIN.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:371|For PAN field, the check digit before the field separator is dropped and the remaining right-most 12 digits of Track 2 data are used.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:375|Device may receive 4R or 4< Decline Code if encrypted PIN data is missing or not sent.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:377|Decline may occur if Terminal Profile PIN Encryption type is not set to UKP.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:390|BUYPASS assigns one merchant number and one device number to the overall AFP system.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:541|For American Express, default track should be Track 1; if unreadable, send unaltered Track 2 on initial transaction before manual entry.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:645|Bill Payment/Recurring Payment Transaction rules for credit card also apply to Signature Debit card.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:669|EBT receipts must show Tender Type, Transaction Type, Clerk ID, Voucher Number, amounts, balances, approved/declined messages, HIP data.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:910|CAVV Revised Format applies to Visa only, identifying Verified By Visa Format with ATN replacing XID.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1623|Address Line 2 positions 13 and 16 must be a space character.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1624|Approval Number is required on reversals for matching information to the original purchase.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1625|If Approval Number is included in a Purchase Request, it is considered preauthorized and BUYPASS does not seek authorization.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1626|In an Authorization Only Reversal, Approval Number present in original transaction must be present and identical in the reversal.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (15)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7769|REQ-SRC-ATL105-PDF-001:1623|MATCHED|TS-SEG100-GAP-PIN|BR-SEG100-GAP-PIN|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|33\|encrypted-pin-block-data`|
|SC-7770|REQ-SRC-ATL105-PDF-001:1624|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7771|REQ-SRC-ATL105-PDF-001:1625|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7772|REQ-SRC-ATL105-PDF-001:1626|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6533|REQ-SRC-ATL105-PDF-001:352|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6535|REQ-SRC-ATL105-PDF-001:354|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6539|REQ-SRC-ATL105-PDF-001:359|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6548|REQ-SRC-ATL105-PDF-001:370|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6549|REQ-SRC-ATL105-PDF-001:371|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6553|REQ-SRC-ATL105-PDF-001:375|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6555|REQ-SRC-ATL105-PDF-001:377|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6568|REQ-SRC-ATL105-PDF-001:390|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6715|REQ-SRC-ATL105-PDF-001:541|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6816|REQ-SRC-ATL105-PDF-001:645|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7067|REQ-SRC-ATL105-PDF-001:910|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (26)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-033927|verification_status=PASS|MATCHED|TC-SEG100-GAP-FIELD-POSITIVE-NEGATIVE|PASS_AND_FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|33\|encrypted-pin-block-data`|
|TC-033928|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033929|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033930|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033931|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033932|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033933|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033934|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033935|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033936|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033937|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033938|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033939|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033940|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033941|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033942|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033943|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033944|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029465|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029466|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029467|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029468|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029469|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029470|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031030|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031031|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (20)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-033927|27 keys|MATCHED_ELEMENT_CONFIRMED|TD-SEG100-GAP-CONDITIONAL-FIELDS|3 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|33\|encrypted-pin-block-data`; CONFIRMED via field-alias crosswalk - AI element name `CardDiscretionaryBlockData` maps to Test element 12. JSON key-name overlap (fallback signal) 0%.|
|TD-TC-033928|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033929|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033930|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033931|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033932|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033933|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033934|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033935|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033936|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033937|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033938|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033939|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033940|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033941|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033942|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033943|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033944|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031030|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031031|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-047 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |5|1|
| TS |5|1|
| TC |20|1|
| TD |20|1|

### BR side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1110|A Field Separator follows Field No. 12 in the CA Public Key File Load Request.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1697|For EMV offline PIN validated debit, PIN block must contain a PIN or all F's, else transaction declines as invalid transaction.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1795|Pump/Lane Number valid codes/values are 1-99.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1798|Quantity, Product Code, Unit of Measure, Unit Price, Product Amount repeat for up to 10 products in Segment 102 or 157.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1799|Quantity valid codes/values are 00000000, 01-399999999.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7843|REQ-SRC-ATL105-PDF-001:1697|MATCHED|TS-SEG100-GAP-TAX-AMOUNT|BR-SEG100-GAP-TAX-AMOUNT|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|99\|tax-amount`|
|SC-7945|REQ-SRC-ATL105-PDF-001:1799|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7267|REQ-SRC-ATL105-PDF-001:1110|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7941|REQ-SRC-ATL105-PDF-001:1795|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7944|REQ-SRC-ATL105-PDF-001:1798|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (20)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-034229|verification_status=PASS|MATCHED|TC-SEG100-GAP-FIELD-POSITIVE-NEGATIVE|PASS_AND_FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|99\|tax-amount`|
|TC-034230|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034231|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034232|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034233|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034234|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034774|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034775|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034777|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034778|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034753|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034754|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034755|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034756|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034757|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034758|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034769|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034770|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034772|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034773|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (20)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-034229|27 keys|MATCHED_ELEMENT_CONFIRMED|TD-SEG100-GAP-CONDITIONAL-FIELDS|3 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|99\|tax-amount`; CONFIRMED via field-alias crosswalk - AI element name `CardDiscretionaryBlockData` maps to Test element 12. JSON key-name overlap (fallback signal) 0%.|
|TD-TC-034230|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034231|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034232|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034233|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034234|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034774|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034775|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034777|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034778|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034753|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034754|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034755|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034756|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034757|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034758|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034769|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034770|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034772|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034773|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-060 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |2|1|
| TS |2|1|
| TC |5|1|
| TD |5|1|

### BR side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1717|Initiation Time is calculated by BUYPASS using system clock, merchant profile time zone, and daylight saving flag.|CONFIRMED|BR-SEG100-EWIC-VOID|eWIC Purchase Reversal/Void uses Prompt Code 8086 and completes before another eWIC transaction.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1718|If required by the issuer, Job Number is found in the Fleet Data Segment (Segment No. 101).|CONFIRMED|BR-SEG100-EWIC-VOID|eWIC Purchase Reversal/Void uses Prompt Code 8086 and completes before another eWIC transaction.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7863|REQ-SRC-ATL105-PDF-001:1717|MATCHED|TS-SEG100-EWIC-VOID|BR-SEG100-EWIC-VOID|Shares canonical anchor `ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-reversal`|
|SC-7864|REQ-SRC-ATL105-PDF-001:1718|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-034345|verification_status=PASS|MATCHED|TC-SEG100-EWIC-PROMPTS|PASS_AND_FAIL|Shares canonical anchor `ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-reversal`|
|TC-034346|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034347|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034348|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034349|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-034345|6 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-EWIC-PROMPTS|6 keys|Shares canonical anchor `ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-reversal`; alias crosswalk exists for this segment but no AI element name resolves to Test element 78 (AI observed: SegmentType, SegmentLength). JSON key-name overlap 0% (0/6 Test keys found by name).|
|TD-TC-034346|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034347|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034348|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034349|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

