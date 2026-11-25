# Scenarios - MasterCard Credit package (plan v10)

Source: `requirements_scenarios/business_scenarios.json` of each folder; test case counts from `test_cases/test_cases.jsonl` (host = sends a request, device = device / terminal check). Contexts: [CONTEXTS.md](CONTEXTS.md) and `coverage/context_matrix.csv`.

## Summary

| BR | Scenarios | Test cases | Host | Device | Blocked |
|---|---:|---:|---:|---:|---:|
| MasterCard Credit (network BR) | 109 | 1861 | 1724 | 137 | 14 |
| Data Element Validation (shared BR) | 35 | 1029 | 1029 | 0 | 35 |
| Terminal & Host Operations (shared BR) | 9 | 47 | 41 | 6 | 0 |
| **Total** | **153** | **2937** | | | |

MasterCard Credit scenarios without a test case: 0.

Scenario ids are the mapping's own ids; a context scenario is `<base id>@<context>`. The scenario name carries the readable context (`@ <industry> · <attendance> <terminal>`).

## MasterCard Credit scenarios by acceptance context

| Context | Scenarios | Test cases |
|---|---:|---:|
| Card not present | 8 | 257 |
| General (default) · attended POS | 28 | 1148 |
| Hospitality · attended POS | 14 | 48 |
| Merchant category 5542 · attended POS | 7 | 10 |
| Merchant category 5552 · attended POS | 7 | 10 |
| Merchant category 6051 · attended POS | 7 | 10 |
| Merchant category 6211 · attended POS | 7 | 10 |
| Petroleum · attended POS | 21 | 214 |
| Petroleum · unattended CAT | 10 | 154 |

### Card not present

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS10 | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File | unconfirmed | 22 |
| BR1_TS17 | BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File | valid | 125 |
| BR1_TS19 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed | valid | 25 |
| BR1_TS20 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File | unconfirmed | 11 |
| BR1_TS27 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File | unconfirmed | 10 |
| BR1_TS35 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File | unconfirmed | 4 |
| BR1_TS7 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File | valid | 26 |
| BR1_TS9 | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed | valid | 34 |

### General (default) · attended POS

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS1 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip | valid | 30 |
| BR1_TS11 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip | valid | 118 |
| BR1_TS12 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip | valid | 118 |
| BR1_TS13 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe | valid | 205 |
| BR1_TS14 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe | valid | 111 |
| BR1_TS15 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed | valid | 116 |
| BR1_TS16 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback | valid | 115 |
| BR1_TS18 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device | unconfirmed | 110 |
| BR1_TS2 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip | valid | 28 |
| BR1_TS21 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip | valid | 9 |
| BR1_TS22 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip | valid | 13 |
| BR1_TS23 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe | spec_implied | 9 |
| BR1_TS24 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe | valid | 9 |
| BR1_TS25 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed | valid | 9 |
| BR1_TS26 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback | valid | 9 |
| BR1_TS28 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device | unconfirmed | 9 |
| BR1_TS29 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip | valid | 1 |
| BR1_TS3 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe | valid | 26 |
| BR1_TS30 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip | valid | 2 |
| BR1_TS31 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe | spec_implied | 1 |
| BR1_TS32 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe | valid | 1 |
| BR1_TS33 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed | valid | 1 |
| BR1_TS34 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback | valid | 1 |
| BR1_TS36 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device | unconfirmed | 1 |
| BR1_TS4 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe | valid | 24 |
| BR1_TS5 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed | valid | 24 |
| BR1_TS6 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback | valid | 24 |
| BR1_TS8 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device | unconfirmed | 24 |

### Hospitality · attended POS

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS37 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · attended POS | valid | 6 |
| BR1_TS38 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · attended POS | valid | 1 |
| BR1_TS39 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · attended POS | valid | 1 |
| BR1_TS40 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · attended POS | valid | 1 |
| BR1_TS41 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Hospitality · attended POS | valid | 1 |
| BR1_TS42 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Hospitality · attended POS | valid | 1 |
| BR1_TS43 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · attended POS | unconfirmed | 1 |
| BR1_TS44 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · attended POS | valid | 29 |
| BR1_TS45 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · attended POS | valid | 1 |
| BR1_TS46 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · attended POS | valid | 2 |
| BR1_TS47 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · attended POS | valid | 1 |
| BR1_TS48 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Hospitality · attended POS | valid | 1 |
| BR1_TS49 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Hospitality · attended POS | valid | 1 |
| BR1_TS50 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · attended POS | unconfirmed | 1 |

### Merchant category 5542 · attended POS

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS82 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 5542 · attended POS | valid | 4 |
| BR1_TS83 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 5542 · attended POS | valid | 1 |
| BR1_TS84 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 5542 · attended POS | valid | 1 |
| BR1_TS85 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 5542 · attended POS | valid | 1 |
| BR1_TS86 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 5542 · attended POS | valid | 1 |
| BR1_TS87 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 5542 · attended POS | valid | 1 |
| BR1_TS88 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 5542 · attended POS | unconfirmed | 1 |

### Merchant category 5552 · attended POS

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS89 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 5552 · attended POS | valid | 4 |
| BR1_TS90 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 5552 · attended POS | valid | 1 |
| BR1_TS91 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 5552 · attended POS | valid | 1 |
| BR1_TS92 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 5552 · attended POS | valid | 1 |
| BR1_TS93 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 5552 · attended POS | valid | 1 |
| BR1_TS94 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 5552 · attended POS | valid | 1 |
| BR1_TS95 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 5552 · attended POS | unconfirmed | 1 |

### Merchant category 6051 · attended POS

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS100 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6051 · attended POS | valid | 1 |
| BR1_TS101 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6051 · attended POS | valid | 1 |
| BR1_TS102 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6051 · attended POS | unconfirmed | 1 |
| BR1_TS96 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6051 · attended POS | valid | 4 |
| BR1_TS97 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6051 · attended POS | valid | 1 |
| BR1_TS98 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6051 · attended POS | valid | 1 |
| BR1_TS99 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6051 · attended POS | valid | 1 |

### Merchant category 6211 · attended POS

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS103 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6211 · attended POS | valid | 4 |
| BR1_TS104 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6211 · attended POS | valid | 1 |
| BR1_TS105 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6211 · attended POS | valid | 1 |
| BR1_TS106 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6211 · attended POS | valid | 1 |
| BR1_TS107 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6211 · attended POS | valid | 1 |
| BR1_TS108 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6211 · attended POS | valid | 1 |
| BR1_TS109 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6211 · attended POS | unconfirmed | 1 |

### Petroleum · attended POS

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS51 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · attended POS | valid | 13 |
| BR1_TS52 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · attended POS | valid | 3 |
| BR1_TS53 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · attended POS | valid | 2 |
| BR1_TS54 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · attended POS | valid | 2 |
| BR1_TS55 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Petroleum · attended POS | valid | 2 |
| BR1_TS56 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Petroleum · attended POS | valid | 2 |
| BR1_TS57 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · attended POS | unconfirmed | 2 |
| BR1_TS58 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · attended POS | valid | 61 |
| BR1_TS59 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · attended POS | valid | 16 |
| BR1_TS60 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · attended POS | valid | 30 |
| BR1_TS61 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · attended POS | valid | 15 |
| BR1_TS62 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Petroleum · attended POS | valid | 17 |
| BR1_TS63 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Petroleum · attended POS | valid | 15 |
| BR1_TS64 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · attended POS | unconfirmed | 15 |
| BR1_TS65 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Petroleum · attended POS | valid | 6 |
| BR1_TS66 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Petroleum · attended POS | valid | 3 |
| BR1_TS67 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Petroleum · attended POS | spec_implied | 2 |
| BR1_TS68 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Petroleum · attended POS | valid | 2 |
| BR1_TS69 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Petroleum · attended POS | valid | 2 |
| BR1_TS70 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Petroleum · attended POS | valid | 2 |
| BR1_TS71 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Petroleum · attended POS | unconfirmed | 2 |

### Petroleum · unattended CAT

| Display id | Scenario | Status | Test cases |
|---|---|---|---:|
| BR1_TS72 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · unattended CAT | valid | 10 |
| BR1_TS73 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · unattended CAT | valid | 4 |
| BR1_TS74 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · unattended CAT | valid | 1 |
| BR1_TS75 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · unattended CAT | valid | 1 |
| BR1_TS76 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · unattended CAT | unconfirmed | 1 |
| BR1_TS77 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · unattended CAT | valid | 60 |
| BR1_TS78 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · unattended CAT | valid | 16 |
| BR1_TS79 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · unattended CAT | valid | 31 |
| BR1_TS80 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · unattended CAT | valid | 15 |
| BR1_TS81 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · unattended CAT | unconfirmed | 15 |

## Data Element Validation (shared BR)

| Display id | Scenario | Test cases |
|---|---|---:|
| BRDE_TS1 | BUYPASS ATL105 Data Element Validation Segment 100 – Standard Message Data Segment | 93 |
| BRDE_TS10 | BUYPASS ATL105 Data Element Validation Segment 118 – Proprietary Data Load Segment | 60 |
| BRDE_TS11 | BUYPASS ATL105 Data Element Validation Segment 119 – Totals with Proprietary Data Load Data Segment | 91 |
| BRDE_TS12 | BUYPASS ATL105 Data Element Validation Segment 123 – NFC Payment Tokenization Data Segment | 34 |
| BRDE_TS13 | BUYPASS ATL105 Data Element Validation Segment 130 – EMV Request Data Segment | 54 |
| BRDE_TS14 | BUYPASS ATL105 Data Element Validation Segment 132 – CA Public Key File Data Segment | 41 |
| BRDE_TS15 | BUYPASS ATL105 Data Element Validation Segment 153 – Network Token Data Request | 10 |
| BRDE_TS16 | BUYPASS ATL105 Data Element Validation Communications Test Request – Message fields | 1 |
| BRDE_TS17 | BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message fields | 6 |
| BRDE_TS18 | BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message fields | 22 |
| BRDE_TS19 | BUYPASS ATL105 Data Element Validation Phone Load Request – Message fields | 6 |
| BRDE_TS2 | BUYPASS ATL105 Data Element Validation Segment 102 – Product Code Data Segment | 65 |
| BRDE_TS20 | BUYPASS ATL105 Data Element Validation Software Load Request – Message fields | 20 |
| BRDE_TS21 | BUYPASS ATL105 Data Element Validation Table Load Request – Message fields | 20 |
| BRDE_TS22 | BUYPASS ATL105 Data Element Validation Financial Transaction Request – Message structure | 51 |
| BRDE_TS23 | BUYPASS ATL105 Data Element Validation EMV Financial Transaction Request – Message structure | 56 |
| BRDE_TS24 | BUYPASS ATL105 Data Element Validation Totals Request – Message structure | 6 |
| BRDE_TS25 | BUYPASS ATL105 Data Element Validation Electronic Mail Request – Message structure | 6 |
| BRDE_TS26 | BUYPASS ATL105 Data Element Validation TransArmor Key Load Request – Message structure | 11 |
| BRDE_TS27 | BUYPASS ATL105 Data Element Validation Proprietary Data Load Request – Message structure | 16 |
| BRDE_TS28 | BUYPASS ATL105 Data Element Validation Totals with Proprietary Data Load Request – Message structure | 19 |
| BRDE_TS29 | BUYPASS ATL105 Data Element Validation CA Public Key File Load Request – Message structure | 13 |
| BRDE_TS3 | BUYPASS ATL105 Data Element Validation Segment 104 – Purchase Card Data Segment | 30 |
| BRDE_TS30 | BUYPASS ATL105 Data Element Validation Communications Test Request – Message structure | 2 |
| BRDE_TS31 | BUYPASS ATL105 Data Element Validation Table Load Request – Message structure | 6 |
| BRDE_TS32 | BUYPASS ATL105 Data Element Validation Phone Load Request – Message structure | 3 |
| BRDE_TS33 | BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message structure | 3 |
| BRDE_TS34 | BUYPASS ATL105 Data Element Validation Software Load Request – Message structure | 6 |
| BRDE_TS35 | BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message structure | 6 |
| BRDE_TS4 | BUYPASS ATL105 Data Element Validation Segment 105 – Totals Data Segment | 81 |
| BRDE_TS5 | BUYPASS ATL105 Data Element Validation Segment 108 – Loyalty Card Data Segment | 56 |
| BRDE_TS6 | BUYPASS ATL105 Data Element Validation Segment 109 – Electronic Mail Data Segment | 61 |
| BRDE_TS7 | BUYPASS ATL105 Data Element Validation Segment 111 – Variable Information Data Segment | 30 |
| BRDE_TS8 | BUYPASS ATL105 Data Element Validation Segment 114 – SKU Data Segment | 10 |
| BRDE_TS9 | BUYPASS ATL105 Data Element Validation Segment 116 – TransArmor Load Data Segment | 34 |

## Terminal & Host Operations (shared BR)

| Display id | Scenario | Test cases |
|---|---|---:|
| BROP_TS1 | BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour | 6 |
| BROP_TS2 | BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) | 9 |
| BROP_TS3 | BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) | 2 |
| BROP_TS4 | BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request | 8 |
| BROP_TS5 | BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request | 10 |
| BROP_TS6 | BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request | 2 |
| BROP_TS7 | BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request | 2 |
| BROP_TS8 | BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) | 1 |
| BROP_TS9 | BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) | 7 |
