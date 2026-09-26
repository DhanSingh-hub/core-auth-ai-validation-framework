# Chapter 13.2: Data Elements in Data Element Number Order

Extraction of data element definitions from BUYPASS ATL105 Specification Chapter 13.2
(starts ~line 19320 of `docs/specs/extracted_text.txt`).

> **Coverage**: Elements 1-99 were transcribed by an automated pass and spot-checked
> against the source text (Appendix C cross-check passed) but not individually re-verified
> line by line. Elements 100-243 (below) were read and transcribed directly from
> `extracted_text.txt` lines 21719-25439 in this pass - higher confidence, but still worth a
> final proofread against the PDF before hardening into production rules. Note the
> specification's element numbering has gaps (e.g. no 9/10/19/32/59/60/67-71/110/166/181
> etc.), so "228 total" is a count, not a max element number (highest number used is 243).

---

## Data Element Definitions (1-99)

| Elem # | Name | Format/Length | Allowed Values or Reference | Notes |
|--------|------|---------------|---------------------------|-------|
| 1 | Access Code | AN, 12 bytes max | Variable up to 12 alphanumeric; "B" for pause | Used for dial strings; PBX access codes |
| 2 | Account Number | ANS, 24-425 bytes | Per element definition; varies by TransArmor mode | Identifies card or account; Track 1/2 data rules apply |
| 3 | Address Line 1 | AN, 24 bytes | Valid street address (24 alphanum chars) | Device location street address |
| 4 | Address Line 2 | AN, 21 bytes | City (12) + Space (1) + State Code alphabetical (2) + Space (1) + ZIP (5); per Appendix D | Device location city, state, ZIP |
| 5 | Approval Number | AN, 6 bytes | Any alphanumeric; space-filled | Required on reversals; indicates preauthorization if in purchase request |
| 6 | Approved Amount | N, 9 bytes | 000000001-999999999; fixed 9 digits, 2 assumed decimals | Amount approved by issuer; may differ from requested amount |
| 7 | Authorizer Code | N, 2 bytes | 00-98; see Appendix C | Identifies authorizer (BUYPASS-defined) |
| 8 | Authorizer Response Code | AN, 2 bytes | Any valid code from BUYPASS/authorizer | Identifies response type; from BUYPASS |
| 11 | Block Number | N, 3 bytes | 000, 001, or specific block number | Tracks data blocks in Electronic Mail & CA Public Key File responses |
| 12 | Card Discretionary Block Data | AN, 51 bytes max | Variable up to 51 alphanum; expiration date + discretionary data | Format varies by application; Track 1/2 parsing rules |
| 13 | Card Label | AN, 4 bytes | "CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR", "CK", "EF", "EC", "SV1-4", "ECA", "EWIC", "EK" | Identifies card type in Totals Response |
| 14 | Card Type | AN, 3 bytes | Valid codes per Appendix E | Identifies card in transaction or feature in Table Load |
| 15 | Card Type Total Amount | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Total amount for particular card type |
| 16 | Card Type Total Count | N, 5 bytes | 00001-99999; fixed 5 digits | Transaction count for card type in Totals Response |
| 17 | Cash Amount | N, 8 bytes max | 1-99999999; variable, 2 assumed decimals | Cash back amount; includes fee if supported |
| 18 | Clerk ID | N, 10 bytes max | 1-9999999999; variable digits | Clerk identification number |
| 20 | Currency Code | N, 3 bytes | 001-999 per Appendix L; default 840 (USD) | Identifies currency by country |
| 21 | Current Date | N, 6 bytes | MMDDYY | From BUYPASS; includes timezone/DST adjustments |
| 22 | Current Time | N, 4 bytes | HHMM (01-24 hours, 01-60 minutes) | From BUYPASS; includes timezone/DST adjustments |
| 23 | Cut Time | N, 4 bytes | HHMM (01-24 hours, 01-60 minutes) | Merchant end-of-day settlement time |
| 24 | Data Type Indicator | AN, 1 byte | "#", "!", ":", "@", "$", "\", "&", "K", "^", "%" | Identifies segment type in host response |
| 25 | Day of the Week | N, 1 byte | 0-6 (0=Sunday, 6=Saturday) | From BUYPASS |
| 26 | Decline Code | AN, 2 bytes | Any valid BUYPASS decline code | Identifies decline reason; see Appendix H for codes allowing intervention |
| 27 | Dial String Terminator | AN, 1 byte | "A" (first dial string), "F" (second dial string) | Marks end of dial string |
| 28 | Dial String Type | N, 1 byte | Fixed value: 1 | Indicates transaction dial strings |
| 29 | Direct Marketing Invoice Number | AN, 10 bytes max | Variable up to 10 alphanum | Invoice number for Direct Marketing/AVS requests |
| 30 | Download Indicator | N, 1 byte | 0 (no download), 1 (partial load), 8 (update key), 9 (update public key) | Indicates device load type |
| 31 | Driver/Identification Number | N, 10 bytes max | Variable up to 10 digits | Fleet card driver identification (unencrypted) |
| 32 | Employee Number | N, 4 bytes | Fixed 4 digits; use "1111" if none assigned | Utility transaction employee (reporting only) |
| 33 | Encrypted PIN Block Data | AN, 36 bytes | 16-20 byte KSN + 16-byte PIN block | Required for debit/EBT; DUKPT encryption |
| 34 | End-of-Data Indicator | A, 1 byte | Fixed value: "~" | Marks end of data segment |
| 35 | End-of-Load Indicator | A, 1 byte | Fixed value: "*" | Marks end of Table Load |
| 36 | Extract Date | N, 6 bytes | MMDDYY | Date info extracted for Electronic Mail |
| 37 | Extract Time | N, 4 bytes | HHMM | Time info extracted for Electronic Mail |
| 38 | Fee Amount | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Fee calculated by BUYPASS |
| 39 | Firmware Version | AN, 8 bytes | Fixed 8 alphanum characters | Device firmware version |
| 40 | Fleet Employee Number | N, 10 bytes max | Variable up to 10 digits | Fleet card employee identifier |
| 41 | Fuel Purchase Amount | N, 8 bytes max | 1-99999999; variable, 2 assumed decimals (7 bytes max for non-Amex) | Total fuel dollar amount |
| 42 | Grand Total | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Final total in Totals Response |
| 43 | Hardware Version | AN, 4 or 8 bytes | Fixed 4 or 8 alphanum characters | Device hardware version |
| 44 | Information Byte | AN, 1 byte | "?" (download), "0" (single-message), "1" (multimessage) | Identifies request type |
| 45 | Initiation Date | N, 6 bytes | MMDDYY | Local date transaction performed (BUYPASS-calculated) |
| 46 | Initiation Time | N, 4 bytes | HHMM | Local time transaction performed (BUYPASS-calculated) |
| 47 | Job Number | N, 10 bytes max | Variable up to 10 digits | Fleet card job identifier |
| 48 | Load Type | A, 1 byte | "P" (partial), "D" (date/time), "K" (TransArmor key), "S" (signing key ID) | Identifies load type for TransArmor/EMV |
| 49 | Local Date and Local Time | N, 10 bytes | MMDDYYHHMM | Local date/time of preauthorized transaction |
| 50 | Local Time | N, 4 bytes | HHMM | Time of Electronic Mail data segment |
| 51 | Mail Text Data | AN, up to 750 bytes | Variable alphanum (IP: 750 max, dial: 150 max) | Data in Electronic Mail Response |
| 52 | Mail Text Data Length | N, 3 bytes | 001-750 (IP) or 001-150 (dial) | Length of Mail Text Data |
| 53 | Merchant Name | AN, 24 bytes | Fixed 24 alphanum chars | Merchant name at device location |
| 54 | Merchant Phone Number | AN, 13 bytes | Fixed 13 alphanum chars, format (nnn)nnn-nnnn | Telephone of device location |
| 55 | Message Format Version Identifier | AN, 6 bytes | Fixed value: "ATL105" | Message format identifier |
| 56 | Net Amount | N, 8 bytes | 00000000-99999999; fixed 8 digits, 2 assumed decimals | Grand Total minus Fee Amount |
| 57 | New Software Version | AN, 8 bytes | Fixed 8 alphanum characters | New software application version number |
| 58 | Nonfuel Amount | N, 8 bytes max | 1-99999999; variable, 2 assumed decimals (7 bytes max for non-Amex) | Non-fuel transaction dollar amount |
| 59 | Number of Card Types | N, 2 bytes | 01-99; fixed 2 digits | Count of card types in Merchant Data Segment |
| 61 | Number of Print Lines | N, 1 byte | 1, 2, or 3 | Count of Terminal Display/Printer Messages in response |
| 62 | Number of Products | N, 2 bytes | 01-10; fixed 2 digits | Count of products in transaction |
| 63 | Number of Segments | N, 2 bytes | Variable up to 2 digits | Count of BUYPASS data segments in request |
| 64 | Odometer | N, 8 bytes max | 1-99999999; variable digits | Odometer reading from fleet card transaction |
| 65 | Password | N, 6 bytes max | 1-999999; default "123456" | Password for end-of-day function |
| 66 | Pause Indicator | AN, 1 byte | Fixed value: "B" | 1-second pause for slow phone systems |
| 72 | PC Duty Amount | N, 7 bytes max | Variable up to 7 digits, 2 assumed decimals | Duty amount in Purchase Card transaction |
| 73 | PC Freight Amount | N, 7 bytes max | Variable up to 7 digits, 2 assumed decimals | Freight charges in Direct Marketing/AVS request |
| 74 | PC Tax Amount | N, 7 bytes max | Variable up to 7 digits, 2 assumed decimals | Tax in Purchase Card Data Segment (distinct from Element 99) |
| 75 | Phone Number | N, up to 18 bytes | Variable up to 18 digits | Primary/secondary/tertiary phone number for dialing |
| 76 | Product Amount | N, 12 bytes max | 1-999999999999; variable, 2 assumed decimals | Monetary value of product (1-10 products repeating) |
| 77 | Product Code | N, 3 bytes | Fixed 3 digits per Appendix F | Product type identifier; see Appendix F |
| 78 | Prompt Code | AN, 3 or 4 bytes | 3-char or 4-char; position 1 = Transaction Type (Appendix G), positions 2-4 = Card Type (Appendix E) | Identifies transaction/card type and special data |
| 79 | Pump/Lane Number | N, 2 bytes max | 1-99; variable digits | Pump or lane identifier at POS |
| 80 | Purchase Code | AN, 16 bytes max | Variable up to 16 alphanum | Purchase code associated with transaction |
| 81 | Quantity | N, 9 bytes max | 00000000.01-399999999; variable, up to 3 assumed decimals | Number of product units (1-10 products repeating) |
| 82 | Redial Count | N, 1 byte | 1-3; fixed 1 digit | Number of times subsequent phone can be redialed |
| 83 | Response Code | AN, 1 byte | "0"-"Y"; see element definition table | Indicates approval, decline, mail, test, or load response |
| 84 | Segment Length | N, 3 or 4 bytes | Varies by segment; see element definition table | Length of segment (3 or 4 digits) |
| 85 | Segment Type | N, 3 bytes | 100, 101, 102, 103, 104, 105, 108, 109, 111, 112, 116, 118, 119, 120, 123, 130, 131, 132, 134, 157, DL1-DL6 | Identifies segment type in request |
| 86 | Sequence Number | N, 6 bytes | 000001-999999 (normal) or 100000-199999 (multithreaded) | Unique transaction identifier; must persist through lifecycle |
| 87 | Service Level | A, 1 byte | "F" (full-serve), "S" (self-serve), "N" (mini-serve), "X" (maxi-serve), "H" (high-speed), "O" (other), 0-9 (private) | Sale type in Product Data Segment |
| 88 | Ship-from Postal Code | AN, 10 bytes | Fixed format: nnnnn-nnnn | Postal code of shipping location |
| 89 | Ship-to Country Code | N, 3 bytes | Fixed 3 digits | Country code of shipment destination |
| 90 | Ship-to Postal Code | AN, 10 bytes | Fixed format: nnnnn-nnnn | Postal code of shipment destination |
| 91 | Software Load Phone Number | AN, 18 bytes max | Variable up to 18 alphanum | Phone number for device management system |
| 92 | Software Load Request Date | N, 6 bytes | MMDDYY | Date device requests full application load |
| 93 | Software Load Request Time | N, 4 bytes | HHMM | Time device requests full application load |
| 94 | Software Load Type | A, 1 byte | "F" (full application), "P" (partial application) | Type of software load |
| 95 | Software Terminal Record ID | AN, 13 bytes | Fixed 13 alphanum | ID from device management system |
| 96 | Software Version | AN, 8 bytes | Fixed 8 alphanum | Device software version at customer location |
| 97 | Start-of-Data Block Indicator | A, 1 byte | Fixed value: ")" | Marks start of data block in Table Load Response |
| 98 | Store Number | N, 16 bytes | 0000000000000001-9999999999999999; fixed 16 digits | Store identifier (asterisks if masked) |
| 99 | Tax Amount | N, 8 bytes max | 1-9999999; variable, 2 assumed decimals (7 bytes max for non-Amex) | Transaction tax amount in Standard Message Data Segment (distinct from Element 74) |

---

## Data Element Definitions (100-243)

| Elem # | Name | Format/Length | Allowed Values or Reference | Notes |
|--------|------|---------------|---------------------------|-------|
| 100 | Terminal Display Communications Message | A, 16 bytes | Fixed: "COMM_TEST_PASSED" | 14 upper-case alpha chars + 2 spaces |
| 101 | Terminal Display/Printer Message | AN, 31 bytes | Free text, space-filled to 31 chars | Repeatable up to 3x per Number of Print Lines (Elem 61) |
| 102 | Terminal Identifier | AN, 22 bytes max | Device Type (2) + State Code/Appendix D (2) + Merchant Number (6-15) + Device Number (3) | 13 bytes for Table/Phone/Date-Time/Software Load requests |
| 103 | Text Data | AN, 150 bytes max | Free-form text | Electronic Mail Data Segment |
| 104 | Text Data Length | N, 3 bytes | 001-750 | Length of Text Data (Elem 103) |
| 105 | Totals Date | N, 6 bytes | MMDDYY / 111111 / 222222 / 333333 / 444444 / 555555 / 999999 | Special codes for relative-date totals requests |
| 106 | Unit of Measure | A, 1 byte | C,G,H,I,K,L,M,P,Q,U,W,Z,O,0-9 | M=EV charging minutes, W=kWh |
| 107 | Unit Price | N, 9 bytes | 000000.000-999999.999 | Up to 3 assumed decimals |
| 108 | Vehicle Number | N, 10 bytes max | Any numeric | Fleet Data Segment (101) |
| 109 | Voucher ID | N, 10 bytes max | (not stated) | Preprinted voucher for local-approval EBT |
| 111 | Variable Information Indicator | AN, 3 bytes | 001-081 (see Appendix I) | Identifies Table ID for Element 113 |
| 112 | Variable Information Length | N, 3 bytes | 001-999 | Length of Element 113 |
| 113 | Variable Information | AN, 982 bytes max | Per Table ID in Element 111; see Appendix I | a-z, A-Z, numeric |
| 114 | Software Load IP/URL Address | AN, 30 bytes | 01-999, a-z, A-Z | Software IP Load Data Segment (DL5) |
| 115 | Additional Information Data Segment Flag | N, 1 byte | 0 (none follows), 1 (follows) | Financial Transaction Response |
| 116 | Additional Information Indicator | N, 3 bytes | 001-047 (see Appendix K) | Identifies Table ID for Element 118 |
| 117 | Additional Information Length | N, 3 bytes | 001-985 | Length of Element 118 |
| 118 | Additional Information | AN, 984 bytes max | Per Table ID in Element 116; see Appendix K | a-z, A-Z, numeric |
| 118 | EMV Additional Information | ANSB, 984 bytes max | 001-984, a-z, A-Z | Distinct element also numbered 118; see Appendix T |
| 119 | Settlement Date | N, 4 bytes | MMDD (01-12, 01-31) | Actual financial settlement date |
| 120 | Network Management Message | A, 8 bytes | Fixed: "COMMTEST" | Communications test message |
| 121 | Partial Approval Indicator | N, 1 byte | 0 (not supported/default), 1 (supported), 5 (balance receipt only, Amex prepaid) | Mandatory population for all card-present transactions |
| 122 | MICR Data | AN, 50 bytes max | (not stated) | Required on all check transactions; also mirrored in Element 2 |
| 123 | Driver's License | AN, 40 bytes max | (not stated) | Check transaction ID |
| 124 | State Code | AN, 2 bytes | Per Appendix D (alphabetical) | Used with Driver's License (Elem 123) |
| 125 | Date of Birth | N, 8 bytes | MMDDYYYY | Check transaction |
| 126 | Check Type | AN, 1 byte | P (Personal), C (Company) | |
| 127 | Check Number | AN, 8 bytes max | (not stated) | |
| 128 | Customer Phone Number | N, 10 bytes max | (not stated) | Check presenter's phone |
| 129 | Customer Last Name | AN, 24 bytes max | (not stated) | Check presenter |
| 130 | Check Issue Date | N, 8 bytes | MMDDYYYY | |
| 131 | ECA/TeleCheck Clerk ID | AN, 6 bytes max | 1-999999 | |
| 132 | ECA/TeleCheck Product Code | AN, 6 bytes max | (not stated) | |
| 133 | ECA/TeleCheck Phone Number | N, 10 bytes max | (not stated) | |
| 134 | ECA/TeleCheck Trace ID | AN, 22 bytes max | (not stated) | |
| 135 | Merchant Trace ID | AN, 25 bytes max | (not stated) | Optional merchant-assigned ID |
| 136 | Denial Record Number | AN, 7 bytes max | (not stated) | Declined ECA/TeleCheck transaction |
| 137 | Extended MICR Data | AN, 65 bytes max | (not stated) | Used with Element 122 when raw MICR exceeds length |
| 138 | Loyalty Program ID | N, 6 bytes max | "ERN" (per source) | Loyalty Card Data Segment (108) |
| 139 | Loyalty Account Number | N, 24 bytes max | (not stated) | |
| 140 | Points to Redeem | N, 6 bytes max | (not stated) | |
| 141 | Coupon ID | N, 19 bytes max | (not stated) | |
| 142 | Coupon Amount | N, 8 bytes max | (not stated) | |
| 143 | Update Code | AN, 1 byte | A,C,E,I,P,S,T,U | Add/Coupon/Expiration/Inquiry/Points/Sale/Totals/Update |
| 144 | Street Address | N, 5 bytes max | (not stated) | Consumer street number, loyalty |
| 145 | Phone Number, Loyalty | N, 10 bytes max | (not stated) | |
| 146 | Expiration Date | N, 4 bytes | MMYY | Loyalty transaction |
| 147 | Loyalty Track 2 Data | AN, 38 bytes max | (not stated) | |
| 148 | Payment Tender Type | AN, 2 bytes | AX,CK,CS,DB,DN,DS,EB,EC,FL,GC,JC,MC,PC,PR,VS | CS = loyalty-only |
| 149 | SKU Data | AN, 1000 bytes max | (not stated) | Bar code SKU, Segment 114 |
| 150 | Loyalty Information Version | N, 1 byte | 1 (Table ID 008), 2 (Table ID 010) | Defaults to 1 if omitted |
| 151 | Unit of Work | N, 19 bytes | (not stated) | Required on loyalty reversals |
| 152 | Print Data | ANS, 900 bytes max | (not stated) | Print Data Segment (115) |
| 153 | WIC Discount Amount | N, 40 bytes max | Account Type=97, Amount Type=52, Currency per Appendix L, Amount sign 0/C/D | Positional subfields |
| 154 | WIC Product Data | AN, 3001 bytes max | Composed of Total Length + up to 6 subelements (EF/EA/PS tags) | EBT Data Segment (103); see Appendix M |
| 155 | Key ID | AN, 11 bytes | Any valid Key ID (incl. CA Key ID) | TransArmor PKI Load Response |
| 156 | Key Data Length | N, 3 bytes | 000-999 | Length of Element 157 |
| 157 | Key Data | AN, 999 bytes max | Any valid key | TransArmor Load Response |
| 158 | License # | AN, 10 bytes max | (not stated) | Fleet card, Segment 101 |
| 159 | Job ID | AN, 12 bytes max | (not stated) | Fleet card, Segment 101 |
| 160 | Department # | AN, 12 bytes max | (not stated) | Fleet card, Segment 101 |
| 161 | Customer Data | AN, 12 bytes max | (not stated) | Fleet card, Segment 101 |
| 162 | User ID | AN, 12 bytes max | Any except zero | Fleet card, Segment 101 |
| 163 | Vehicle ID# | AN, 8 bytes max | (not stated) | Fleet card, Segment 101 |
| 164 | EBT Program Data | AN, 267 bytes max | Total Length (3) + 1-6 Program Data subelements (max 44 bytes each) | TAG 50/51/52/IT; see Appendix M |
| 165 | Start Date or End Date | N, 8 bytes | CCYYMMDD; 00000000=immediate, 99999999=no end | Proprietary Data Load Segment (118) |
| 166 | Start Time or End Time | N, 4 bytes | 0000-2359 | Used with Element 165 |
| 168 | Number of Receipt Text Lines | N, 2 bytes | 1-10 | Prompt Code 901 |
| 169 | Receipt Text Data Length | N, 2 bytes | 1-40 | Prompt Code 901 |
| 170 | Receipt Text Data | AN, 20 bytes max | (not stated) | Prompt Code 901 |
| 171 | Number of Discounts | N, 2 bytes | (not stated) | Prompt Code 904 |
| 172 | Product Discount Amount | N, 5 bytes | Format 99v999 | Prompt Code 904 |
| 173 | BUYPASS Card Type | N, 4 bytes | Format 9999 | Dynamic BIN table match |
| 174 | Card Table Type | N, 4 bytes | 0001 BIN, 0002 RULES, 0003 RESTRICTIONS, 0004 SAF, 0005 PROMPT, 0006 PRODUCT | Prompt Code 902 |
| 175 | Card Table Data | AN, 3600 bytes max | (not stated) | Prompt Code 902 |
| 176 | Device Card Table Version | N, 35 bytes | Leading "99999" = no card table used | |
| 177 | Card Table Load Version | N, 35 bytes | (not stated) | |
| 178 | Load Control Key | AN, 60 bytes | (not stated) | |
| 179 | Host Discount Timestamp | N, 12 bytes | CCYYMMDDHHMM | |
| 180 | Site Configuration Data | AN, 3600 bytes max | (not stated) | Prompt Code 903 |
| 182 | Prompt Code, Pending | AN, 4 bytes | 0901,0902,0904,0981 | Custom Receipt/Proprietary Load/Host Discount/Electronic Mail |
| 183 | Card BIN Range, Beginning | AN, 12 bytes | 12 digits, left-justified space-filled | Prompt Code 904 |
| 184 | Card BIN Range, Ending | AN, 12 bytes | 12 digits, left-justified space-filled | Prompt Code 904 |
| 185 | Discount Quantity Limit | N, 3 bytes | (not stated) | Prompt Code 904 |
| 186 | Discount Program Description | AN, 15 bytes | (not stated) | Prompt Code 904 |
| 187 | CA Public Key File Checksum | N, 25 bytes | (not stated) | EMV Financial Transaction Request |
| 188 | EMV Card Sequence Number | AN, 3 bytes | 000-099, or 3 spaces if Tag 5F34 absent | EMV Tag 5F34 |
| 189 | EMV Chip Data Length | N, 3 bytes | 000-999 | Length of Element 190 |
| 190 | EMV Chip Data | ANSB, 999 bytes max | TLV combinations; must include Tag 9F06/84; must exclude Tag 5A/57 | See Appendix R |
| 191 | EMV Additional Information Indicator | N, 3 bytes | 001 (EMV table data) | |
| 192 | EMV Additional Information Length | N, 3 bytes | 001-985 | |
| 193 | CA Public Key File Block Length | N, 3 bytes | 000-999 | Length of Element 194 |
| 194 | CA Public Key File Block | AN, 9999 bytes max | Any valid key; error codes RQST/CURRENT HASH MATCH, BLOCK NBR NOT NUMERIC, BLOCK NBR NOT 000-xxx | See Appendix S |
| 195 | CAVV Revised Format | AN, 20 bytes | Cardholder auth verification value data | Visa/Interlink tokenized only; see Appendix Y |
| 196 | Token Requestor ID | AN, 11 bytes | Left-justified, space-filled | Visa/MasterCard tokenized transactions |
| 197 | Token PAN Suffix | AN, 4 bytes | Last 4 digits of PAN | Visa/MasterCard/Interlink/Maestro tokenized |
| 198 | Settlement Type | AN, 1 byte | D (Dual/credit), S (Single/debit), X (Dual, non-traditional Signature Debit) | Common AID EMV / Signature Debit |
| 199 | Signature Required | AN, 1 byte | T (required), F (not required), space (device determines) | Common AID EMV / Signature Debit |
| 200 | Receipt Card Description | AN, 10 bytes | Left-justified, space-filled card type text (e.g. "STAR") | Common AID EMV / Signature Debit |
| 201 | Fuel Volume Data | AN, 3600 bytes max | (not stated) | Prompt Code 905 |
| 202 | Cryptogram Token Data | B64, 28 or 56 bytes | Base64: a-z,A-Z,0-9,+,/,= | In-app payment tokenization |
| 203 | Safekey Data | B64, 58 bytes | Pos 1-2 fixed "SK"; 3-30 AEVV; 31-58 AESK (Base64) | NFC Payment Tokenization Data Segment (123) |
| 204 | SafeKey Response | AN, 1 byte | 0,1,2,3,4,5,6,7,8,9,A,B,C,D,U | AEVV validation result codes |
| 205 | Load Subtype | AN, 1 byte | M (Moneris Key Load) | |
| 206 | SPDH Header | AN, 48 bytes | See Appendix V | Moneris Key Load request/response |
| 207 | Moneris Terminal Identifier | AN, 8 bytes | (not stated) | From Moneris terminal initialization |
| 208 | Moneris Merchant ID | AN, 13 bytes | (not stated) | From Moneris terminal initialization |
| 209 | Moneris Response Code | AN, 2 bytes | (not stated) | For MAC Encryption |
| 210 | MAC | AN, 16 bytes | Any alphanumeric | Moneris debit transactions |
| 211 | Moneris Key Indicators | AN, 3 bytes | Each char Y/N: MAC key, Data Encryption key, PIN Encryption key present | |
| 212 | Moneris Key Data | AN, 16 bytes | Any alphanumeric | One iteration per "Y" in Element 211 |
| 213 | Moneris Data | AN, 100 bytes max | \<tag\>\<len\>\<data\> format | |
| 214 | Batch Number | AN, 3 bytes | 000-999 | Moneris batch |
| 215 | Language Indicator | AN, 1 byte | See Appendix V Moneris Language Indicator Matrix | |
| 216 | Response Display | AN, 16 bytes | Any alphanumeric | Moneris batch close response |
| 217 | Number of Debits | N, 4 bytes | 0001-9999 | Moneris batch |
| 218 | Debit Dollar Value | AN, 19 bytes | (+/-)000000000000000000-(+/-)999999999999999999 | Moneris batch |
| 219 | Number of Credits | N, 4 bytes | 0001-9999 | Moneris batch |
| 220 | Credit Dollar Value | AN, 19 bytes | (+/-)000000000000000000-(+/-)999999999999999999 | Moneris batch |
| 221 | Number of Corrections | N, 4 bytes | 0001-9999 | Moneris batch |
| 222 | Corrections Dollar Value | AN, 19 bytes | (+/-)000000000000000000-(+/-)999999999999999999 | Moneris batch |
| 223 | Inclusive/Exclusive for Tax 1 | AN, 1 byte | I,E,N | If N, tax type/amount omitted |
| 224 | Tax Type 1 | AN, 3 bytes | GST,HST,PST,QST (Canada) | Country-dependent |
| 225 | Tax Amount 1 | N, 9 bytes | 000000000-999999999 | Decimals per currency |
| 226 | Inclusive/Exclusive for Tax 2 | AN, 3 bytes | I,E,N | If N, tax type/amount omitted |
| 227 | Tax Type 2 | AN, 3 bytes | GST,HST,PST,QST (Canada) | Country-dependent |
| 228 | Tax Amount 2 | N, 9 bytes | 000000000-999999999 | Decimals per currency |
| 229 | Inclusive/Exclusive for Tax 3 | AN, 1 byte | I,E,N | If N, tax type/amount omitted |
| 230 | Tax Type 3 | AN, 3 bytes | GST,HST,PST,QST (Canada) | Country-dependent |
| 231 | Tax Amount 3 | N, 9 bytes | 000000000-999999999 | Decimals per currency |
| 232 | Download Data | AN, 100 bytes max | Any alphanumeric | |
| 233 | RID | AN, 10 bytes | Valid EMV Registered Application Provider identifier | |
| 234 | Stand-in Indicator | N, 1 byte | 1 (No), 2 (Domestic only), 3 (Domestic & Foreign) | |
| 235 | Floor Limit | N, 12 bytes | 000000000000-999999999999 | Max allowable stand-in value |
| 236 | BUYPASS RID Card Type | AN, 3 bytes | (not stated) | EMV Terminal Floor Limits Data Segment (DL8) |
| 237 | TAVV Cryptogram | AN, 28 bytes | (not stated) | NFC Payment Tokenization Data Segment (123); see Appendix Y |
| 238 | TAVV Result Code | AN, 1 byte | 1,2,3,4 | Cryptogram/DTVV validation results |
| 239 | Enhanced Fleet Data | AN, 999 bytes max | See Data Segment 145 | WEX OTR, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV, Voyager EMV |
| 240 | Available Product Information | AN, 17 bytes max | Any alphanumeric/special chars | WEX Available Product Fleet Information, Segment 148 |
| 241 | Price Data | AN, 999 bytes max | See Data Segment 149 | Fuel Price Update Request Segment |
| 242 | EV Charging Data | AN, 200 bytes max | See Data Segment 156 | EV Charging Data Segment |
| 243 | Extended Unit of Measure | A, 4 bytes | ACRE,ARES,CELI,CMET,EACH,FOOT,GBGA,GBOU,GBPI,GBQA,GRAM,HECT,INCH,KILO,KMET,LITR,METR,MILE,MILI,MMET,PIEC,PUND,SCMT,SMET,SMIL,SQFO,SQIN,SQKI,SQMI,SQYA,TONS,USGA,USOU,USPI,USQA,YARD | MasterCard Enhanced Fleet EMV non-fuel products |

---

## Extraction status

- **Extracted**: elements 1-243 (all element numbers found in the source; the document's "228 total" is a count with numbering gaps, not a max element number).
- Directly transcribed from `extracted_text.txt` lines 19367-25439 (chapter 13.2) in this pass; elements 1-99 were an earlier automated pass, spot-checked but not fully re-verified.

## Key cross-references

- Element 4 (Address Line 2) -> Appendix D (Valid State Codes)
- Element 7 (Authorizer Code) -> Appendix C (Valid Authorizer Codes)
- Element 14 (Card Type) -> Appendix E (Valid Card Type Codes)
- Element 20 (Currency Code) -> Appendix L (Valid Currency Codes)
- Element 77 (Product Code) -> Appendix F (Valid Payment Systems Product Codes)
- Element 78 (Prompt Code) -> Appendices E & G (Card Type + Transaction Type)
- Element 111/113 (Variable Information) -> Appendix I (Table IDs 001-081)
- Element 116/118 (Additional Information) -> Appendix K (Table IDs 001-047)
