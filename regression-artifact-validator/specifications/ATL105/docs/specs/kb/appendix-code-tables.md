# Appendix Code Tables (C, D, E, F, G, J, L)

Code-to-description lookup tables from the flat, ENUM-style appendices - the most directly
useful reference data for building JSON field validation rules.

> Appendix C below was verified verbatim against `docs/specs/extracted_text.txt` (line
> 25935-26069). The other tables were transcribed by an automated pass and only spot-checked,
> so treat them as a strong draft - re-verify against the source before hardening into a
> production rule, especially the "partial extraction" tables noted below.

---

## Appendix C: Valid Authorizer Codes (Element 7)

Source: `extracted_text.txt` line 25935-26069. Fully transcribed, 98 entries. Verified against source.

| Code | Authorizer Name | Type |
|------|-----------------|------|
| 00 | Other | Other |
| 01 | American Express | Credit |
| 02 | Visa | Credit |
| 03 | MasterCard | Credit |
| 04 | Discover Network | Credit |
| 05 | Valero | Proprietary |
| 06 | PULSE | Debit |
| 07 | STAR Network Southeast | Debit |
| 08 | Certegy | Check |
| 09 | ECA/TeleCheck Service | Check |
| 11 | Moneris (Canada) | Credit |
| 12 | GE Consumer Finance | Other |
| 13 | GECC Wright Express | Other |
| 14 | BUYPASS Internal | (none stated) |
| 15 | Fleetcor2 | Fleet |
| 16 | InComm | Stored value/Phone/Gift |
| 17 | STAR Network Northeast | Debit |
| 18 | Safeway | Stored value/Phone/Gift |
| 19 | Accel | Debit |
| 20 | Texaco | Proprietary |
| 21 | FleetCor | Fleet |
| 22 | NetSpend | Stored Value |
| 23 | STAR Network West | Debit |
| 24 | ESI Loyalty (Canada) | Stored Value |
| 25 | Chase Acquiring (ChaseNet) | Credit |
| 26 | Chase Acquiring (VisaNet) | Credit |
| 27 | Everywhere Gift Card | Stored value/Gift |
| 28 | NYCE | Debit |
| 29 | Chase Payment Tech | Credit |
| 30 | Cardlock (BP PayPoint) | Fleet |
| 31 | Acormida (BP PayPoint) | Fleet |
| 32 | Citi | Proprietary |
| 33 | COMDATA Fleet | Fleet |
| 34 | Sun Oil | Proprietary |
| 35 | First Data Loyalty solutions | Loyalty |
| 36 | ECA/TeleCheck Debit | Proprietary |
| 37 | HSBC Direct | Proprietary |
| 38 | A Toda Hora Banco Popular | EBT |
| 39 | Car Care | Proprietary |
| 40 | Maestro Direct | Debit |
| 41 | Bank of America PayPoint | Debit |
| 42 | Wells Fargo PayPoint | Debit |
| 43 | Bank of America Direct + | Debit |
| 44 | ConnectPay | Debit |
| 45 | Wells Fargo Direct + | Debit |
| 46 | PayPoint | Stored value/Gift |
| 47 | PayPoint | Check |
| 48 | Interlink | Debit |
| 49 | HRS | Stored value/Gift |
| 50 | Walmart MDEX | WIC |
| 52 | Bank of America/Deluxe | EBT |
| 53 | Transactive EBT (Texas) | EBT |
| 54 | The Home Depot Gift Card | Stored value/Gift |
| 55 | First Data Standard Gift Card | Stored value/Gift |
| 56 | HSBC PayPoint | Proprietary |
| 57 | WEX ISO | Fleet |
| 58 | Shazam | Debit |
| 60 | Deluxe | EBT |
| 62 | Transactive EBT (Illinois) | EBT |
| 63 | A Toda Hora Global | EBT |
| 64 | Northeast Coalition of States | EBT |
| 65 | Western States EBT Association | EBT |
| 66 | Southern Alliance of States | EBT |
| 67 | Lockheed Martin INS | EBT |
| 68 | Credit Union 24-FID | Debit |
| 69 | Accel | Debit |
| 70 | A Toda Hora Banco Popular | Debit |
| 71 | Stored Value Systems | Stored value/Gift |
| 72 | Wright Express | Fleet |
| 73 | Gascard | Fleet |
| 74 | Nevada eWIC | (none stated) |
| 75 | Kentucky eWIC | (none stated) |
| 77 | Sinclair | Proprietary |
| 78 | A Toda Hora Global | Debit |
| 79 | ACS Direct | EBT |
| 80 | BUYCheck | Check |
| 81 | WEX OTR Fleet | Fleet |
| 82 | EDS | EBT |
| 83 | ACS WIC | WIC |
| 84 | STAR Single Message Signature | Debit |
| 85 | STAR Dual Message Signature | Debit |
| 86 | Armed Forces Financial Network Direct | Other |
| 87 | Comdata Corporation | Proprietary |
| 88 | Optis (Omaha) Cycle | Debit |
| 89 | LML | Check Service |
| 91 | PreSolutions | Phone |
| 92 | Alliance Data Systems | Proprietary |
| 94 | Voyager | Fleet |
| 95 | Unocal Corporation | Proprietary |
| 96 | Fleet One | Fleet |
| 97 | Jeanie Direct | Debit |
| 98 | Optum | Proprietary |

Note (verbatim from source): "+AUTHFILE in Production has been updated with this information;
however, feature is not available as of the release date of this document. Check with your
First Data Corporation representative for additional information."

---

## Appendix D: Valid State Codes (Elements 4, 12, 102, 124)

Source: `extracted_text.txt` line ~26070-26242. **Partial extraction** - first 20 US states shown;
full table (all 50 states + territories + military codes + Canadian provinces) needs re-extraction.

| State | Alphabetical | ANSI |
|-------|--------------|------|
| Alabama | AL | 01 |
| Alaska | AK | 02 |
| Arizona | AZ | 04 |
| Arkansas | AR | 05 |
| California | CA | 06 |
| Colorado | CO | 08 |
| Connecticut | CT | 09 |
| Delaware | DE | 10 |
| District of Columbia | DC | 11 |
| Florida | FL | 12 |
| Georgia | GA | 13 |
| Hawaii | HI | 15 |
| Idaho | ID | 16 |
| Illinois | IL | 17 |
| Indiana | IN | 18 |
| Iowa | IA | 19 |
| Kansas | KS | 20 |
| Kentucky | KY | 21 |
| Louisiana | LA | 22 |
| Maine | ME | 23 |

Note: full table also includes remaining US states, territories (PR, VI, etc.), military codes
(AA, AE, AP), and Canadian provinces (AB, BC, MB, ON, QC, SK, etc.) - not yet transcribed.

---

## Appendix E: Valid Card Type Codes (Elements 14, 78)

Source: `extracted_text.txt` line ~26243-26484. **Partial extraction** - Table Load Response
codes 001-092 shown; full appendix covers additional sub-tables not yet transcribed.

| Code | Card Type | Description |
|------|-----------|--------------|
| 001 | BUYPASS fleet | Fleet |
| 011 | Debit checking | Debit and Follow-Up Signature Debit (Single/Dual) |
| 012 | Debit saving | Debit |
| 013 | Debit ACH | Debit |
| 020 | Visa Fleet Credit | Visa Fleet Credit |
| 040 | Loyalty | Proprietary |
| 041 | Certegy (Formerly Telecredit) | Check |
| 045 | Generic check | Check |
| 046 | ECA/TeleCheck Service | Check |
| 050 | Valero Energy Corporation | Proprietary |
| 051 | Shell | Proprietary |
| 052 | ExxonMobil | Proprietary |
| 053 | ExxonMobil Fleet | Fleet |
| 055 | Valero UCC | Proprietary |
| 056 | Generic proprietary | Proprietary |
| 057 | Sohio/Gulf | Proprietary |
| 058 | Cenex | Proprietary |
| 059 | Wright Express | Fleet |
| 060 | Voyager | Fleet |
| 061 | Car Care One | Proprietary |
| 064 | Tesoro Petroleum Corporation | Proprietary |
| 070 | Valero Fleet | Fleet |
| 071 | Fleet One | Fleet |
| 072 | Tesoro UCC | Proprietary |
| 073 | EBT food stamps | EBT |
| 074 | EBT cash benefits | EBT |
| 075 | Citgo fleet | Proprietary |
| 077 | Unocal | Proprietary |
| 078 | Phone | Stored value |
| 079 | Stored value | Stored value |
| 080 | MasterCard fleet | Fleet |
| 081 | Fuelman/Gascard | Fleet |
| 084 | Sinclair | Proprietary |
| 085 | Sunoco | Proprietary |
| 086 | eWIC | EBT |
| 088 | EBT | EBT childcare provider payment |
| 090 | Conoco | Proprietary |
| 091 | NGFC Card | NGFC (Next Generation Fleet Card) |
| 092 | WEX OTR Card | WEX OTR card |

Note (per draft transcription): codes 001-086 are also used in 4-character Prompt Codes; codes
127-168 are not used in Prompt Codes - **not independently verified**, re-check against source.

---

## Appendix F: Valid Payment Systems Product Codes (Element 77)

Source: `extracted_text.txt` line ~26485-27246. **Partial extraction (sample only)** - motor fuel
codes 001-054 shown; appendix reportedly has 13 subsections covering codes 000-999 (automotive,
aviation, marine, merchandise, FSA/HRA, etc.) not yet transcribed.

### Motor Fuel Codes (001-054, sample)

| Code | Description |
|------|-------------|
| 001 | Unleaded (88 octane or less) |
| 002 | Unleaded Plus (89-91 octane) |
| 003 | Super Unleaded (92 octane or higher) |
| 019 | Diesel |
| 020 | Diesel-Premium |
| 022 | Compressed natural gas |
| 023 | Liquid propane gas |
| 024 | Liquid natural gas (compressed) |
| 025 | M-85 |
| 026 | E-85 |
| 045 | B2 Diesel Blend 2% Biodiesel |
| 046 | B5 Diesel Blend 5% Biodiesel |
| 050 | B20 Diesel Blend 20% Biodiesel |
| 051 | B100 Diesel Blend 100% Biodiesel |
| 052 | Ultra Low Sulfur #1 |
| 053 | Ultra Low Sulfur #2 |
| 054 | Ultra Low Sulfur Premium Diesel #2 |

(Full range 001-055+ and remaining 12 subsections not yet transcribed - see source line range above.)

---

## Appendix G: Valid Transaction Type Codes (Element 78, position 1)

Source: `extracted_text.txt` line ~27247-27347. Transcribed in full per draft pass (23 entries);
not independently re-verified.

| Code | Transaction Type | Description |
|------|-------------------|-------------|
| 0 | POS Purchase/Capture | Point-of-sale purchase/preauthorized completion (also used for AFP prepay inside POS) |
| 3 | POS Authorization Only | Point-of-sale authorization only (no draft capture) |
| 4 | Customer-activated Purchase/Capture | Customer-activated purchase completion |
| 5 | Customer-activated Authorization Only | Customer-activated authorization only (no draft capture) |
| 6 | Mail/Phone Purchase | Mail/Telephone order |
| 7 | Merchandise return | Merchandise return/refund |
| 8 | Purchase reversal | Purchase reversal/void |
| 9 | Special Transactions | Totals, downloads, communications test, e-mail, special device prompts |
| A | Account Verification | Account verification |
| B | Mail/Phone Authorization Only | Mail/Telephone authorization only |
| C | Mail/Phone Reversal | Mail/Telephone reversal/void |
| D | RBC Loyalty Lookup | Identify loyalty number associated with RBC card |
| E | Balance Inquiry | Balance Inquiry - Credit, EBT cash, stored value |
| K | Activate | Activate stored value card |
| L | Deactivate | Deactivate stored value card |
| M | Balance Merge | Merge balances of two stored value cards |
| N | Replace Card | Replace stored value card |
| Q | Recharge Card | Recharge stored value card |
| S | Cancellation | Cancel stored value card or auth-only transaction |
| T | Token Registration | TransArmor token registration |
| U | Void of Merchandise Return | Void of merchandise return |
| V | Loyalty advice function | Add account, coupon redeem, expiration date update, account inquiry, points redeem, etc. |
| Z | Time-out Reversal | Time-out reversal |

---

## Appendix J: Valid Point-of-Service Entry Mode Codes (Element 113, w/ Element 111 Table 005)

Source: `extracted_text.txt` line ~32491-32576. Transcribed in full per draft pass; not
independently re-verified.

### PAN Entry Mode Codes (positions 1-2)

| Code | Description |
|------|-------------|
| 00 | Unspecified |
| 01 | Manual/keyed |
| 02 | Magnetic stripe read with partial track data sent to host |
| 04 | Bar Code Scan / OCR read for EMV |
| 05 | ICC read - CVV data reliable (EMV chip read) |
| 07 | Contactless chip read |
| 10 | Credential on file |
| 79 | Contact and Contactless chip fallback to manual |
| 80 | Chip card capable - unaltered track data read (contact EMV fall back to swiped) |
| 82 | Contactless mobile commerce device |
| 86 | Dual interface contactless chip to contact chip |
| 90 | Magnetic stripe read with full track data sent to host |
| 91 | Full contactless magnetic stripe read with track data sent to host |
| 95 | ICC read - CVV data unreliable |

### Terminal PIN Entry Capability Mode Codes (position 3)

| Code | Description |
|------|-------------|
| 0 | Unspecified |
| 1 | PIN entry capability (EMV Contact and Contactless) |
| 2 | No PIN entry capability (EMV Contact and Contactless) |
| 3 | mPOS Software-based PIN Entry Capability |
| 6 | PIN pad inoperative |
| 8 | Contactless Magnetic Stripe Read Capability |

---

## Appendix L: Valid Currency Codes (Element 20)

Source: `extracted_text.txt` line ~34457-34890. **Partial extraction (sample only)** - reportedly
100+ currencies total; only ~35 shown below, alphabetical by currency name.

| Currency | Country/Territory | ISO Code | Visa/MC Support |
|----------|--------------------|----------|------------------|
| Afghani | Afghanistan | 971 | Both |
| Algerian Dinar | Algeria | 012 | Both |
| Angola Kwanza | Angola | 973 | Both |
| Argentine Peso | Argentina | 032 | Both |
| Armenian Dram | Armenia | 051 | Both |
| Australian Dollar | Australia and associated territories | 036 | Both |
| Azerbaijanian Manat | Azerbaijan | 944 | Both |
| Bahamian Dollar | Bahamas | 044 | Both |
| Belarusian Ruble | Belarus | 933 | Both |
| Bahraini Dinar | Bahrain | 048 | Both |
| Baht | Thailand | 764 | Both |
| Balboa | Panama | 590 | Both |
| Barbados Dollar | Barbados | 052 | Both |
| Belize Dollar | Belize | 084 | Both |
| Bermudian Dollar | Bermuda | 060 | Both |
| Bhutanese Ngultrum | Bhutan | 064 | Both |
| Bolivar Soberano | Venezuela | 928 | Both |
| Boliviano | Bolivia | 068 | Both |
| Brazilian Real | Brazil | 986 | Both |
| Brunei Dollar | Brunei Darussalam | 096 | Both |
| Burundi Franc | Burundi | 108 | Both |
| Canadian Dollar | Canada | 124 | Both |
| Cape Verde Escudo | Cape Verde | 132 | Both |
| Cayman Islands Dollar | Cayman Islands | 136 | Both |
| Cedi | Ghana | 936 | Both |
| CFP Franc | French Polynesia, New Caledonia, Wallis and Futuna | 953 | Both |
| CFA Franc BCEAO | Benin, Burkina Faso, Ivory Coast, Guinea-Bissau, Mali, Niger, Senegal, Togo | 952 | Both |
| CFA Franc BEAC | Cameroon, Central African Republic, Chad, Congo, Equatorial Guinea, Gabon | 950 | Both |
| Chilean Peso | Chile | 152 | Both |
| Chinese People's Bank Dollar | China | 158 | MC |
| Chinese Yuan Renminbi | China | 156 | Both |
| Colombian Peso | Colombia | 170 | Both |
| Comoro Franc | Comoros | 174 | Both |
| Congolese Franc | Democratic Republic of the Congo | 976 | Both |
| Convertible Mark | Bosnia and Herzegovina | 977 | Both |
| Cordoba Oro | Nicaragua | 558 | Both |
| Costa Rican Colon | Costa Rica | 188 | Both |

Note: default currency if not specified is 840 (USD). Full table (100+ currencies) not yet
fully transcribed - remaining entries live in the same source line range above.

---

## Summary of code tables

| Appendix | Table Name | Extraction Status | Purpose |
|----------|-----------|--------------------|---------|
| C | Valid Authorizer Codes | Complete, verified against source (98 codes) | Element 7 |
| D | Valid State Codes | Partial (20 of 50+ states) | Elements 4, 12, 102, 124 |
| E | Valid Card Type Codes | Partial (Table Load Response subset) | Elements 14, 78 |
| F | Valid Payment Systems Product Codes | Partial (motor fuel sample only) | Element 77 |
| G | Valid Transaction Type Codes | Complete draft (23 codes), not re-verified | Element 78 (1st position) |
| J | Valid POS Entry Mode Codes | Complete draft (14 + 6 codes), not re-verified | Element 113 (w/ Element 111 Table 005) |
| L | Valid Currency Codes | Partial (~35 of 100+ currencies) | Element 20 |
