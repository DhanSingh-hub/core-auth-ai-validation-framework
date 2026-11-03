# Appendix Code Tables (C, D, E, F, G, J, L)

Code-to-description lookup tables from the flat, ENUM-style appendices - the most directly
useful reference data for building JSON field validation rules.

> Appendix C was verified verbatim against `docs/specs/extracted_text.txt` (source lines
> 25935-26069). Appendix D's 72 alphabetical/ANSI pairs were transcribed against the full source
> table; tests exercise all 72 values in each code family. Other tables were transcribed by an
> automated pass and only spot-checked; treat them as drafts and re-verify before hardening.

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

Source: `extracted_text.txt` lines 26070-26242. The source table is complete and verified; this
page shows representative entries only. The full 72 alphabetical/ANSI pairs are maintained in
`AppendixDStateCodes`, and each 72-value code family is covered by the linked vectors in
`test-output/test-json/appendices/appendix-d-segment-100-coverage.json`.

Appendix D defines two different code families:

- Alphabetical codes are used by Elements 4, 12, and 124.
- Two-digit ANSI codes are used at positions 3-4 of Element 102 (Terminal Identifier).
- ANSI code `00` is listed for locations outside the US/Canada and conditionally requires
	Variable Information Table 041; its executable dependency remains `REVIEW_REQUIRED`.

Do not substitute an alphabetical code such as `CA` into Element 102 where the ANSI code `06`
is required.

### Representative entries (excerpt)

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

The remaining source entries cover all US states, territories, military identifiers, Canadian
provinces/territories, and `NA`; the full numeric and alphabetical value sets are exercised by
`AppendixDStateCodesTest`. The tests do not infer arbitrary conversions between the two code
families.

### Deferred Appendix D review items

- `APPD-REVIEW-001`: ANSI state code `00` is accepted as a listed value, but its Table 041 Country
	Subdivision dependency remains `REVIEW_REQUIRED` until an actual serialized Variable
	Information fixture is modeled and validated.
- `APPD-REVIEW-002`: Element 124 alphabetical State Code is covered against Appendix D; Element 12
	Card Discretionary Block Data remains partial until its source-backed representation and
	positive/negative validation fixtures are added.

These items remain outside the covered numerator. Do not promote their BR/TS/TC/TD statuses based
on the existence of the Appendix D lookup table alone.

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

Source: `extracted_text.txt` lines 32491-32576 (ATL105 2026-3, Appendix J-1/J-2). The 14 PAN
mode values and 6 terminal capability values are checked against the extracted source by
`AppendixJPosEntryModeOracleTest`. See the [Appendix J training note](segment-100/appendix-j-pos-entry-mode-training.md)
for context restrictions and remaining evidence gaps.

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

Source: `extracted_text.txt` lines 34456-34944 (Appendix L-1 through L-9). **Fully transcribed and
verified**: all 161 source rows (153 distinct three-digit codes) are listed below in source page
order, cross-checked by [`AppendixLCurrencyCodeCatalogTest`](../../../../src/test/java/com/coreauth/validator/AppendixLCurrencyCodeCatalogTest.java)
against the extracted specification text. Six codes - `230`, `356`, `578`, `710`, `840`, `978` - are
listed more than once with a *different* Visa/MC/Both value per country; both/all of their rows are
kept below rather than collapsed. See [appendix-l-currency-code-training.md](appendix-l/README.md)
for the full usage map (Element 20, Element 153, Element 76, Appendix I Table 006/056, Appendix M)
and network-scope-conflict handling.

| Currency | Country/Territory | ISO Code | Visa/MC Support |
|----------|--------------------|----------|------------------|
| Afghani | Afghanistan | 971 | Both |
| Algerian Dinar | Algeria | 012 | Both |
| Angola Kwanza | Angola | 973 | Both |
| Argentine Peso | Argentina | 032 | Both |
| Armenian Dram | Armenia | 051 | Both |
| Australian Dollar | Australia, Christmas Island, Cocos (Keeling) Islands, Heard and McDonald Islands, Kiribati, Nauru, Norfolk Island, Tuvalu | 036 | Both |
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
| CFA Franc BCEAO (footnote 2) | Benin, Burkina Faso, Cote D'Ivoire (Ivory Coast), Guinea-Bissau, Mali, Niger, Senegal, Togo | 952 | Both |
| CFA Franc BEAC (footnote 3) | Cameroon, Central African Republic, Chad, Congo, Equatorial Guinea, Gabon | 950 | Both |
| Chilean Peso | Chile | 152 | Both |
| Chinese People's Bank Dollar | China | 158 | MC |
| Chinese Yuan Renminbi | China | 156 | Both |
| Colombian Peso | Columbia | 170 | Both |
| Comoro Franc | Comoros | 174 | Both |
| Congolese Franc | Democratic Republic of the Congo | 976 | Both |
| Convertible Mark | Bosnia and Herzegovina | 977 | Both |
| Cordoba Oro | Nicaragua | 558 | Both |
| Costa Rican Colon | Costa Rica | 188 | Both |
| Dalasi | Gambia | 270 | Both |
| Danish Krone | Denmark, Faroe Islands, Greenland | 208 | Both |
| Denar | Macedonia | 807 | Both |
| Djibouti Franc | Djibouti | 262 | Both |
| Dobra | Sao Tome and Principe | 930 | Both |
| Dominican Peso | Dominican Republic | 214 | Both |
| Dong | Vietnam | 704 | Both |
| East Caribbean Dollar | Anguilla, Antigua and Barbuda, Dominica, Grenada, Montserrat, St. Kitts-Nevis, St. Lucia, St. Vincent and the Grenadines | 951 | Both |
| Egyptian Pound | Egypt | 818 | Both |
| El Salvador Colon | El Salvador | 222 | MC |
| Eritrean Nakfa | Eritrea | 232 | Visa |
| Ethiopian Birr | Ethiopia | 230 | Both |
| Ethiopian Birr | Eritrea | 230 | **MC** (network-scope conflict with the row above - see training note) |
| Euro | Saint Barthelemy, Saint Martin (French part) | 978 | MC |
| Euro | France, Metropolitan | 978 | **Visa** (network-scope conflict - see training note) |
| Euro (footnotes: Bulgaria eff. 1 Jan 2026\*\*, Croatia eff. 1 Jan 2023\*) | Aland Islands, Andorra, Austria, Belgium, Bulgaria, Croatia, Cyprus, Estonia, European Union Countries, Finland, France, French Guiana, French Southern Territories, Germany, Greece, Guadeloupe, Holy See (Vatican City State), Ireland, Italy, Kosovo (UNMIK), Latvia, Lithuania, Luxembourg, Malta, Martinique, Mayotte, Monaco, Montenegro, Netherlands, Portugal, Reunion, St. Pierre and Miquelon, San Marino, Slovakia, Slovenia, Spain | 978 | Both |
| Falkland Islands Pound | Falkland Islands (Malvinas) | 238 | Both |
| Fiji Dollar | Fiji | 242 | Both |
| Forint | Hungary | 348 | Both |
| Gibraltar Pound | Gibraltar | 292 | Both |
| Gourde | Haiti | 332 | Both |
| Guarani | Paraguay | 600 | Both |
| Guilder | Aruba | 533 | Both |
| Guinea Franc | Guinea | 324 | Both |
| Guyana Dollar | Guyana | 328 | Both |
| Hong Kong Dollar | Hong Kong | 344 | Both |
| Hryvnia | Ukraine | 980 | Both |
| Iceland Krona | Iceland | 352 | Both |
| Indian Rupee | Bhutan | 356 | MC |
| Indian Rupee | India | 356 | **Both** (network-scope conflict with the row above - see training note) |
| Iraqi Dinar | Iraq | 368 | Both |
| Jamaican Dollar | Jamaica | 388 | Both |
| Jordanian Dinar | Jordan | 400 | Both |
| Kenyan Shilling | Kenya | 404 | Both |
| Kina | Papua New Guinea | 598 | Both |
| Kip | Lao People's Democratic Republic | 418 | Both |
| Koruna | Czech Republic | 203 | Both |
| Kuwaiti Dinar | Kuwait | 414 | Both |
| Lari | Georgia | 981 | Both |
| Lebanese Pound | Lebanon | 422 | Both |
| Lek | Albania | 008 | Both |
| Lempira | Honduras | 340 | Both |
| Leone | Sierra Leone | 925 | Both |
| Liberian Dollar | Liberia | 430 | Both |
| Libyan Dinar | Libyan Arab Jamahiriya | 434 | Visa |
| Lilangeni | Swaziland | 748 | Both |
| Loti | Lesotho | 426 | Both |
| Malagasy Ariary | Madagascar | 969 | Both |
| Malaysian Ringgit | Malaysia | 458 | Both |
| Malawi Kwacha | Malawi | 454 | Both |
| Manat | Turkmenistan | 934 | Both |
| Mauritius Rupee | Mauritius | 480 | Both |
| Mexican Peso | Mexico | 484 | Both |
| Moldovan Lau | Moldova, Republic of | 498 | Both |
| Moroccan Dirham | Morocco, Western Sahara | 504 | Both |
| Mozambique Metical | Mozambique | 943 | Both |
| Myanmar Kyat | Myanmar | 104 | Both |
| Naira | Nigeria | 566 | Both |
| Namibian Dollar | Namibia | 516 | Both |
| Nepalese Rupee | Nepal | 524 | Both |
| Caribbean Guilder | Curacao, Sint Maarten (Dutch Part) | 532 | Both |
| New Israeli Shekel | Israel | 376 | Both |
| New Taiwan Dollar | The Republic of China (Taiwan) | 901 | Both |
| New Zealand Dollar | Cook Islands, New Zealand, Niue, Pitcairn, Tokelau | 554 | Both |
| Norwegian Krone | Bouvet Island, Norway, Svalbard and Jan Mayen | 578 | Both |
| Norwegian Krone | Antarctica | 578 | **MC** (network-scope conflict with the row above - see training note) |
| Nuevo Sol | Peru | 604 | Both |
| Ouguiya | Mauritania | 929 | Both |
| Pa'anga | Tonga | 776 | Both |
| Pakistan Rupee | Pakistan | 586 | Both |
| Pataca | Macao | 446 | Both |
| Peso Uruguayo | Uruguay | 858 | Both |
| Philippine Peso | Philippines | 608 | Both |
| Pound Sterling | Guernsey, Isle of Man, Jersey, United Kingdom | 826 | Both |
| Pound Sterling | South Georgia and South Sandwich Islands | 826 | Both (duplicate row, same scope - not a conflict) |
| Pula | Botswana | 072 | Both |
| Qatari Rial | Qatar | 634 | Both |
| Quetzal | Guatemala | 320 | Both |
| Rand | Lesotho, Namibia | 710 | Visa |
| Rand | South Africa | 710 | **Both** (network-scope conflict with the row above - see training note) |
| Rial Omani | Oman | 512 | Both |
| Riel | Cambodia | 116 | Both |
| Romanian Leu | Romania | 946 | Both |
| Rufiyaa | Maldives | 462 | Both |
| Rupiah | Indonesia | 360 | Both |
| Russian Ruble | Russian Federation | 643 | Both |
| Rwanda Franc | Rwanda | 646 | Both |
| Saudi Riyal | Saudi Arabia | 682 | Both |
| Serbian Dinar | Serbia | 891 | MC |
| Serbian Dinar | Serbia, Republic of | 941 | Both |
| Seychelles Rupee | Seychelles | 690 | Both |
| Singapore Dollar | Singapore | 702 | Both |
| Som | Kyrgyzstan | 417 | Both |
| Solomon Islands Dollar | Solomon Islands | 090 | Both |
| Somali Shilling | Somalia | 706 | Both |
| South Sudanese Pound | Republic of South Sudan | 728 | Both |
| Spanish Peseta (ESA) | Brazil | 996 | MC |
| Sri Lanka Rupee | Sri Lanka | 144 | Both |
| St. Helena Pound | St. Helena | 654 | Both |
| Sudan Pound | Sudan | 938 | Both |
| Suriname Dollar | Suriname | 968 | Both |
| Swedish Krona | Sweden | 752 | Both |
| Swiss Franc | Liechtenstein, Switzerland | 756 | Both |
| Tajikistan Somoni | Tajikistan | 972 | Both |
| Taka | Bangladesh | 050 | Both |
| Tala | Samoa | 882 | Both |
| Tanzanian Shilling | Tanzania, United Republic of | 834 | Both |
| Tenge | Kazakhstan | 398 | Both |
| Trinidad and Tobago Dollar | Trinidad and Tobago | 780 | Both |
| Tugrik | Mongolia | 496 | Both |
| Tunisian Dinar | Tunisia | 788 | Both |
| Turkish Lira | Turkey | 949 | Both |
| UAE Dirham | United Arab Emirates | 784 | Both |
| Uganda Shilling | Uganda | 800 | Both |
| U.S. Dollar | Libyan Arab Jamahiriya, Palestine, Panama | 840 | MC |
| U.S. Dollar | American Samoa, Bonaire/Sint Eustatius/Saba, British Indian Ocean Territory, Ecuador, El Salvador, Guam, Marshall Islands, Micronesia, Northern Mariana Islands, Palau, Puerto Rico, Timor-Leste, Turks and Caicos Islands, United States, U.S. Minor Outlying Islands, Virgin Islands (British), Virgin Islands (U.S.) | 840 | **Both** (network-scope conflict with the row above - see training note) |
| Uzbekistan Sum | Uzbekistan | 860 | Both |
| Vatu | Vanuatu | 548 | Both |
| Won | Korea, Republic of | 410 | Both |
| Yemeni Rial | Yemen | 886 | Both |
| Yen | Japan | 392 | Both |
| Zambian Kwacha | Zambia | 967 | Both |
| Zimbabwe Gold | Zimbabwe | 924 | Both |
| Zloty | Poland | 985 | Both |

Footnotes (source): 2 = CFA Franc BCEAO, responsible authority Banque Centrale des Etats de
l'Afrique de l'Ouest; 3 = CFA Franc BEAC, responsible authority Banque des Etats de l'Afrique
Centrale; \*\* Euro became Bulgaria's currency effective 1 January 2026; \* Euro became Croatia's
currency effective 1 January 2023. Default currency if Element 20 is not specified is `840` (USD),
and Element 20 is documented as ignored during transaction processing (totals follow the merchant's
bound currency) - see the training note for the full processing-rule text and every other place
this code set is reused (Element 153, Element 76, Appendix I Table 006/056, Appendix M).

---

## Summary of code tables

| Appendix | Table Name | Extraction Status | Purpose |
|----------|-----------|--------------------|---------|
| C | Valid Authorizer Codes | Complete, verified against source (98 codes) | Element 7 |
| D | Valid State Codes | Partial (20 of 50+ states) | Elements 4, 12, 102, 124 |
| E | Valid Card Type Codes | Partial (Table Load Response subset) | Elements 14, 78 |
| F | Valid Payment Systems Product Codes | Partial (motor fuel sample only) | Element 77 |
| G | Valid Transaction Type Codes | Complete draft (23 codes), not re-verified | Element 78 (1st position) |
| J | Valid POS Entry Mode Codes | 14 + 6 source-listed values verified; context/lifecycle partial | Element 113 (w/ Element 111 Table 005) |
| L | Valid Currency Codes | Complete, verified against source (161 rows / 153 codes); 6 network-scope-conflict codes review-gated | Element 20 (also Element 153, Element 76, Appendix I Table 006/056, Appendix M) |
