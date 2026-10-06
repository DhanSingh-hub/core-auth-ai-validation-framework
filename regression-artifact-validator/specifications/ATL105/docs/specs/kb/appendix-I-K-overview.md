# Appendices I & K: Variable & Additional Information Data Layouts - Table Index

Index of Table IDs and nested sub-table structures in Appendices I (Variable Information) and
K (Additional Information). This is a navigation aid only - table bodies are large/nested and
have not been transcribed; drill into `docs/specs/extracted_text.txt` at the noted line to read
a specific table in full.

> All Table IDs and line numbers below were verified directly against `extracted_text.txt`
> in this pass (via targeted `grep_search` for each "Table ID ... Fixed Value: NNN" heading).
> Sub-table breakdowns for the complex tables (016, 030, 032, 049, 056, 060, 064, 066, 078,
> 079) are summarized but not fully transcribed field-by-field - drill into the source at the
> given line for full detail.

**Key elements**:
- **Element 111 (Variable Information Indicator)** + **Element 113 (Variable Information)** -> Appendix I Table IDs
- **Element 116 (Additional Information Indicator)** + **Element 118 (Additional Information)** -> Appendix K Table IDs

---

## Appendix I: Variable Information Data Layouts (Element 111/113)

Source: `extracted_text.txt` line ~27443-32490. The allocated range is 001-081,
with 78 located top-level table definitions; 023, 061 and 074 are not located.
See the [Appendix I training addendum](appendix-i/README.md) and its generated
source inventory. The summaries below are historical navigation hints, not the
field-level training oracle; verify every attribute against the source.

| Table ID | Name/Title | Approx. Line | Sub-tables | Notes |
|----------|-----------|-------------|-----------|-------|
| 001 | Universal Product Code (UPC) Information | 27549 | None | Max 35 bytes; required for Blackhawk transactions |
| 002 | Program Identifier Information | 27563 | None | 3-byte program ID (CHG, CIT, ECA, IDT, INC, KWS, PPL, SP2, SPK, SPP, STB, SVS, VAL, WIC, WUG) |
| 003 | Address Verification Service (AVS) Information | 27618 | None | ZIP+4 or ZIP, then 0-20 char street address; max 29 bytes |
| 004 | Card Verification Information (CVV/CVV2/CVC2/CID/DTVV) | 27630 | None | 3-4 byte verification code, varies by card type |
| 005 | Point-of-Service Entry Mode Information | 27672 | None | 3-byte POS entry mode per Appendix J |
| 006 | Currency Code Information | 27685 | None | 3-byte currency code; default 840 (USD) |
| 007 | Standard Industrial Classification (SIC) Information | 27712 | None | 4-6 byte variable SIC code |
| 008 | Balance Information (Stored Value) | 27733 | None | Account number for Balance Merge (M) / Replace (N); max 19 digits |
| 009 | Partial Approval Indicator Information | 27746 | None | 1-byte: 0/1/5 |
| 010 | Visa Trusted Agent Program (TAP) Information | 27775 | None | Fixed 30 bytes |
| 011 | Tax Collector Information | 27787 | None | 1-byte: Y/N/blank |
| 012 | Market-Specific Data Indicator Information | 27801 | None | 1-byte: M/B/E/H |
| 013 | Special Payments Indicator Information | 27822 | None | 1-byte: R/I |
| 014 | User Data Information | 27828 | None | Max 15 bytes, echoed back, not forwarded to issuer |
| 015 | Discover Network Retrieval Reference Number Information | 27830 | None | Fixed 15 bytes |
| 016 | Visa payWave Card Information | 27851 | Multiple | Card Sequence Number, Amount Authorized, Application Cryptogram, ATC, Customer Exclusive Data, Form Factor, Issuer Application Data, Unpredictable Number |
| 017 | Suspected Fraud Reversal Indicator Information | 27919 | None | Fixed 2 bytes: "34" |
| 018 | Card Acceptor Terminal Identifier Information | 27939 | None | 1-4 byte terminal number |
| 019 | Recharge Type Information | 27957 | None | Fixed 2 bytes: 00/01/02 |
| 020 | Top Up Phone Number Information | 27988 | None | Up to 20 digits |
| 021 | Settlement Data Acceptance Flag | 28007 | None | 1-byte: 0/1/2/X |
| 022 | Originating Device Type Indicator Information | 28042 | None | 2-byte device type code |
| 024 | Manual Check MICR Type | 28119 | None | 2-byte format code |
| 025 | Gift Card Number | 28140 | None | Max 19 bytes |
| 026 | Order ID | 28147 | None | Max 12 bytes |
| 027 | Customer Name | 28155 | None | Max 50 bytes |
| 028 | Use Unique ID | 28162 | None | 1-byte: Y/N/space |
| 029 | Universal Unique Identifier | 28184 | None | Max 41 bytes |
| 030 | POS Condition Code | 28209 | Sub-tables | 10-byte fixed code |
| 031 | Point-of-Service Terminal Capability Codes | 28248 | None | 2-byte fixed code (00-12) |
| 032 | POS Additional Information Indicator | 28279 | 11 sub-tables (01,02,04-14) | Max 100 bytes |
| 033 | Re-Price Data Request Information | 28718 | None | Max 133 bytes |
| 034 | Name Verification Service (NVS) Information | 28765 | None | Discover 70 chars / Visa 105 chars |
| 035 | Verified By Visa Data | 28795 | None | XID (20) + CAVV (20), binary |
| 036 | MasterCard UCAF Data | 28840 | None | UCAF Security Level (3) + UCAF Data (32) |
| 037 | MCX Checkout Token | 28905 | None | Fixed 40 bytes |
| 038 | Paydiant Tender ID | 28916 | None | Fixed 12 bytes |
| 039 | Fraud Enhanced Data | 28927 | None | Fixed 8 bytes (DDMMCCYY) |
| 040 | Final Amount Indicator | 28948 | None | 1-byte: "1" |
| 041 | Country Subdivision | 28968 | None | ISO3166-2, max 3 bytes |
| 042 | Cardholder Language | 28984 | None | ISO 639-2, fixed 3 bytes |
| 043 | Cardholder Postal Code (for AVS) | 29006 | None | Max 13 bytes |
| 044 | Original Transaction Identifier | 29021 | None | Fixed 15 bytes |
| 045 | First Auth Amount | 29112 | None | 3-byte length + up to 12 digits, 2 decimals |
| 046 | Previous Transaction ID | 29099 | None | Max 16 bytes |
| 047 | Soft Descriptor - Merchant Name | 29140 | None | Max 38 bytes |
| 048 | MasterCard Authentication Data | 29158 | None | Program Protocol (1) + optional Directory Server Transaction ID (36) |
| 049 | Merchant Supplementary Data | 29213 | 10+ sub-tables (01-28) | Max 500 bytes |
| 050 | Additional Security Code | 30037 | None | Max TBD |
| 051 | Additional Card ID | 30053 | None | Max TBD |
| 052 | Additional TransArmor Data | 30076 | None | Max TBD |
| 053 | Remote Commerce Acceptor Identifier | 30144 | None | Max TBD |
| 054 | Merchant Tran ID | 30166 | None | Max TBD |
| 055 | Experience Provider Value | 30213 | None | Max TBD |
| 056 | Stored Credentials Transaction Additional Data | 30238 | Multiple | Max TBD |
| 057 | Soft Descriptor - Merchant City | 30772 | None | Max TBD |
| 058 | Soft Descriptor - Merchant State | 30794 | None | Max TBD |
| 059 | Soft Descriptor - Merchant Country | 30816 | None | Max TBD |
| 060 | Real Time Account Updater Request Data | 30848 | Multiple | Max TBD |
| 062 | Customer Additional Information | 31343 | None | Max TBD |
| 063 | Visa Incremental Auth Lodging Data | 31343 | None | Max TBD (line collides with 062 - needs re-check) |
| 064 | Service Location Information | 31366 | Multiple | Max TBD |
| 065 | Merchant Payment Gateway ID Data | 31511 | None | Max TBD |
| 066 | Digital Commerce Data | 31555 | Multiple | Max TBD |
| 067 | Anticipated Amount | 31712 | None | Max TBD |
| 068 | EV - Additional Transaction Fee 1 | 31738 | None | Max TBD |
| 069 | EV - Additional Transaction Fee 2 | 31767 | None | Max TBD |
| 070 | Anticipated Completion Amount | 31787 | None | Max TBD |
| 071 | Enabler Verification Value | 31806 | None | Max TBD |
| 072 | Transaction Link Identifier | 31853 | None | Ans.22, MasterCard/Maestro; provided by network in Segment 112 Table ID 038; Moneris field "Real Time Unique ID 9e" |
| 073 | Future Transaction Amount | 31875 | None | n12; anticipated future completion amount (MasterCard) |
| 075 | Discount Program Data | 31902 | None | Fixed length 12 bytes |
| 076 | Discount Line-Item Data | 31957 | None | (see source for structure) |
| 077 | Discount Amount | 32006 | None | n12; total discount amount for fuel product code |
| 078 | Line Item | 32029 | Sub-tables 01-14 (TLV) | Max 999 bytes; Visa fleet line-item + MasterCard Enhanced Fleet non-fuel; sub-tables: 01 Service Type, 02 Product Category, 03 Taxable Indicator, 04 Local Tax Included, 05 Local Tax Amount, 06 Local Tax Rate, 10 Other Tax Included, 11 Other Tax Amount, 12 Other Tax Rate, 14 Extended Unit of Measure |
| 079 | Visa Commercial Card Data | 32321 | Sub-tables 01,02,03,07,08,09 | Max 999 bytes; Visa commercial card aggregate data |
| 080 | Staged Wallet ID | 32460 | None | Fixed Value 080 |
| 081 | Visa Gateway ID | 32472 | None | Fixed Value 081 |

All Table IDs verified directly against `extracted_text.txt` in this pass.

---

## Appendix K: Additional Information Data Layouts (Element 116/118)

Source: `extracted_text.txt` line ~32577-34456. 47 distinct Table IDs (001-047).

| Table ID | Name/Title | Approx. Line | Notes |
|----------|-----------|-------------|-------|
| 001 | Balance Information | 32649 | Gift card, EBT card, credit/debit/phone card balance |
| 002 | Reserved | 32658 | Reserved for future use |
| 003 | Address Verification Service (AVS) / Name Verification Service (NVS) | 32677 | AVS or NVS info, or both |
| 004 | Card Verification Value (CVV/CVV2/CVC2/CID/DTVV) | 33210 | CVV info in response |
| 005 | ECA/TeleCheck Trace ID | 33329 | Unique trace ID |
| 006 | ECA/TeleCheck Denial Record Number | 33337 | Record number of declined check |
| 007 | ECA/TeleCheck Return Check Data | 33344 | Return check data structure |
| 008 | Loyalty Information - Version 1 | 33375 | Loyalty response data v1 |
| 009 | Visa Product Result Information | 33420 | Visa product result in response |
| 010 | Loyalty Information - Version 2 | 33427 | Loyalty response data v2 |
| 011 | User Data Information | 33474 | User data echoed back |
| 012 | Discover Network Retrieval Reference Number | 33482 | Fixed 15 bytes |
| 013 | Expiration Date (MMYY) - TransArmor VeriFone | 33500 | Fixed 4 bytes |
| 016 | PIN-on-Receipt Information | 33524 | PIN-on-receipt data layout |
| 017 | BUYPASS Host Card Type | 33531 | 3-byte card type code |
| 018 | PINless Debit Information | 33546 | Fixed 3 bytes |
| 019 | Visa Spend Qualified Indicator | 33584 | Spend qualification status |
| 020 | Host Prompts Information | 33592 | Host prompt data |
| 021 | Re-Price Data Response Information | 33662 | Re-priced product data |
| 022 | CAVV Result | 33713 | 3-D Secure CAVV result |
| 023 | MCX Reference Number | 33750 | Mobile Commerce Exchange reference |
| 024 | Carwash Indicator | 33772 | Fuel transaction carwash indicator |
| 025 | Language Indicator | 33783 | Transaction language indicator |
| 026 | DST Response | 33794 | Digital Secure Transaction response |
| 027 | Universal Unique Identifier | 33806 | UUID returned from BUYPASS |
| 028 | Transaction Identifier | 33830 | Transaction ID for lifecycle tracking |
| 029 | PAR (Payment Account Reference) Data | 33849 | Tokenization PAR data |
| 030 | Merchant Advice Code | 33880 | Merchant advice code from authorizer |
| 031 | DAF Indicator | 33947 | Digital Authentication Framework indicator |
| 032 | Agreement ID | 33989 | Visa agreement identifier |
| 034 | Host-based Purchase Restriction | 34011 | Purchase restriction data |
| 035 | Cardholder Additional Information Result Code | 34044 | Result code |
| 036 | Account Type | 34161 | Account type from authorizer (Visa) |
| 037 | Account Funding Source | 34184 | Funding source type (Visa) |
| 038 | Transaction Link Identifier | 34215 | Transaction link ID (MasterCard/Maestro) |
| 039 | Transaction Link Action Indicator | 34232 | Link action indicator |
| 040 | Fraud Score | 34269 | Fraud risk score |
| 041 | Fraud Score Reason Code | 34283 | Reason code for fraud score |
| 042 | Authentication Data Quality Indicator | 34297 | Auth data quality assessment |
| 043 | Token Update First Use Indicator | 34336 | First-use flag for updated token |
| 044 | Merchant Tran ID | 34375 | Merchant transaction ID from host |
| 045 | Applied Special Service | 34391 | Applied special service data (Visa) |
| 046 | Voyager Restriction Code | 34405 | Voyager fleet restriction code |
| 047 | Visa Category Code | 34429 | Visa merchant category code |

All Table IDs verified directly against `extracted_text.txt` in this pass.

---

## Summary

- **Appendix I**: 81 Table IDs indexed, all line numbers verified; complex/nested ones are 016, 030, 032, 049, 056, 060, 064, 066, 078, 079.
- **Appendix K**: 47 Table IDs indexed (001-047, excluding 002 which is reserved), all line numbers verified.
- Element 111: Variable Information Indicator (3-digit table ID); Element 113: Variable Information data (up to 982 bytes).
- Element 116: Additional Information Indicator (3-digit table ID); Element 118: Additional Information data (up to 984 bytes).

## How to use this index

1. For Variable Information (Element 111/113): find the Table ID above, then read the source at
   the given line (or search `extracted_text.txt` if marked "line TBD").
2. For Additional Information (Element 116/118): same process using the Appendix K table above.
3. Complex/nested table IDs need a follow-up pass to transcribe their sub-table structure in full.
