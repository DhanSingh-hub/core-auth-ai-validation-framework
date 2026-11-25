# Scenarios - MasterCard Credit package (plan v12)

A scenario = transaction x entry mode x acceptance context. Its QE id is `SC-<spec>-<8 hex>` and its name ends with `@ <context>` (industry / terminal / presentation, from the spec). Test case counts from `test_cases/test_cases.jsonl`.

## Summary

| BR | Scenarios | Test cases | P1 | P2 | P3 | Positive | Negative |
|---|---:|---:|---:|---:|---:|---:|---:|
| MasterCard Credit (network BR) | 196 | 2129 | 734 | 1167 | 228 | 1696 | 428 |
| Data Element Validation (shared BR) | 35 | 1029 | 0 | 0 | 0 | 39 | 807 |
| Terminal & Host Operations (shared BR) | 9 | 47 | 0 | 0 | 0 | 26 | 21 |

## MasterCard Credit scenarios

| Scenario ID | Scenario | Test cases |
|---|---|---:|
| SC-ATL105-6ADB5621 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ General Retail · attended POS | 115 |
| SC-ATL105-D5FF2646 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Hospitality · attended POS | 3 |
| SC-ATL105-FDF8FDF0 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Petroleum · attended POS | 28 |
| SC-ATL105-16B42CC8 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Supermarket · attended POS | 1 |
| SC-ATL105-A91378A8 | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Utilities · attended POS | 1 |
| SC-ATL105-CE3AE8B8 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · attended POS | 111 |
| SC-ATL105-FCBF0C27 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · unattended CAT | 1 |
| SC-ATL105-A4123EFA | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · attended POS | 3 |
| SC-ATL105-DB112FC7 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · unattended CAT | 1 |
| SC-ATL105-95140904 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · attended POS | 29 |
| SC-ATL105-AB7323EA | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 29 |
| SC-ATL105-4A9C161D | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · attended POS | 1 |
| SC-ATL105-50E5DBD6 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · unattended CAT | 1 |
| SC-ATL105-52D5AFE1 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · attended POS | 1 |
| SC-ATL105-D52C068B | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · unattended CAT | 1 |
| SC-ATL105-ED496ED8 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · attended POS | 110 |
| SC-ATL105-C18E6DCC | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · unattended CAT | 1 |
| SC-ATL105-DF6AFFE2 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · attended POS | 3 |
| SC-ATL105-EDC8B5AD | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · unattended CAT | 1 |
| SC-ATL105-CAEE4401 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · attended POS | 28 |
| SC-ATL105-21D68F83 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine) | 29 |
| SC-ATL105-F8DAF225 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · attended POS | 1 |
| SC-ATL105-9B6B9609 | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · unattended CAT | 1 |
| SC-ATL105-5E1067FA | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · attended POS | 1 |
| SC-ATL105-A5669F4B | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · unattended CAT | 1 |
| SC-ATL105-0DFDDDDF | BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File @ Credential on File · customer present; card not present | 125 |
| SC-ATL105-48F4A035 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · attended POS | 119 |
| SC-ATL105-5ED2613B | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · unattended CAT | 1 |
| SC-ATL105-BC90B09B | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · attended POS | 30 |
| SC-ATL105-99B31463 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · unattended CAT | 1 |
| SC-ATL105-677D33AA | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · attended POS | 62 |
| SC-ATL105-A210C900 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) | 59 |
| SC-ATL105-9D599CDE | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · attended POS | 1 |
| SC-ATL105-EC6265FA | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · unattended CAT | 1 |
| SC-ATL105-79111A1A | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · attended POS | 1 |
| SC-ATL105-A86DCAB0 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · unattended CAT | 1 |
| SC-ATL105-6FE1F1D1 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · attended POS | 115 |
| SC-ATL105-70B0E17B | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · unattended CAT | 1 |
| SC-ATL105-4E91C0CE | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · attended POS | 3 |
| SC-ATL105-86BADD94 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · unattended CAT | 1 |
| SC-ATL105-7BE85284 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · attended POS | 32 |
| SC-ATL105-C1D2F2DB | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) | 32 |
| SC-ATL105-9CF93F41 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · attended POS | 1 |
| SC-ATL105-2263784C | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · unattended CAT | 1 |
| SC-ATL105-BF0048E4 | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · attended POS | 1 |
| SC-ATL105-616A6FCD | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · unattended CAT | 1 |
| SC-ATL105-76F82FFD | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · attended POS | 205 |
| SC-ATL105-F651389F | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · unattended CAT | 1 |
| SC-ATL105-DE8E9D65 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · attended POS | 4 |
| SC-ATL105-F6F1E237 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · unattended CAT | 1 |
| SC-ATL105-97DE7FC1 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · attended POS | 43 |
| SC-ATL105-B261473D | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 45 |
| SC-ATL105-557CF714 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · attended POS | 1 |
| SC-ATL105-A905C80E | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · unattended CAT | 1 |
| SC-ATL105-580E8706 | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · attended POS | 1 |
| SC-ATL105-113E712E | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · unattended CAT | 1 |
| SC-ATL105-6120DA43 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ General Retail · attended POS | 115 |
| SC-ATL105-D552FDE1 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Hospitality · attended POS | 3 |
| SC-ATL105-37350FC3 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Petroleum · attended POS | 30 |
| SC-ATL105-41CAD4E4 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Supermarket · attended POS | 1 |
| SC-ATL105-D3D1E256 | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Utilities · attended POS | 1 |
| SC-ATL105-2F941EC7 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback @ General Retail · attended POS | 1 |
| SC-ATL105-7645B626 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe @ General Retail · attended POS | 1 |
| SC-ATL105-09045E7A | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device @ General Retail · attended POS | 1 |
| SC-ATL105-A52A7906 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File @ Credential on File · customer present; card not present | 4 |
| SC-ATL105-491A7A04 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip @ General Retail · attended POS | 5 |
| SC-ATL105-4030B66D | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip @ General Retail · attended POS | 2 |
| SC-ATL105-CF668DB7 | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe @ General Retail · attended POS | 2 |
| SC-ATL105-AA20AEAE | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed @ General Retail · attended POS | 1 |
| SC-ATL105-3DF727E4 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File @ Mail Order · mail and/or telephone order | 11 |
| SC-ATL105-12B03219 | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed @ Mail Order · mail and/or telephone order | 24 |
| SC-ATL105-D2FEACFC | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File @ Mail Order · mail and/or telephone order | 22 |
| SC-ATL105-6BE2183C | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed @ Mail Order · mail and/or telephone order | 35 |
| SC-ATL105-873CDFFE | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ General Retail · attended POS | 10 |
| SC-ATL105-4B839359 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Petroleum · attended POS | 3 |
| SC-ATL105-C4EB18DF | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Supermarket · attended POS | 1 |
| SC-ATL105-801B9EC9 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Utilities · attended POS | 1 |
| SC-ATL105-FE1C30BA | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ General Retail · attended POS | 9 |
| SC-ATL105-D2A34953 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Petroleum · attended POS | 3 |
| SC-ATL105-AC2FB54A | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Supermarket · attended POS | 1 |
| SC-ATL105-9D86DA57 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Utilities · attended POS | 1 |
| SC-ATL105-DA3E80BE | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ General Retail · attended POS | 9 |
| SC-ATL105-7E0F33A6 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Petroleum · attended POS | 3 |
| SC-ATL105-D18425E7 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Supermarket · attended POS | 1 |
| SC-ATL105-30E7E142 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Utilities · attended POS | 1 |
| SC-ATL105-9E4464F0 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File @ Credential on File · customer present; card not present | 10 |
| SC-ATL105-09CE3848 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ General Retail · attended POS | 11 |
| SC-ATL105-D1464CF1 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Petroleum · attended POS | 7 |
| SC-ATL105-3104BAEA | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Supermarket · attended POS | 1 |
| SC-ATL105-A9025C95 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Utilities · attended POS | 1 |
| SC-ATL105-7E915769 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ General Retail · attended POS | 9 |
| SC-ATL105-DC794243 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Petroleum · attended POS | 5 |
| SC-ATL105-7FF90FB7 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Supermarket · attended POS | 1 |
| SC-ATL105-88B8EA02 | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Utilities · attended POS | 1 |
| SC-ATL105-975B8254 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ General Retail · attended POS | 9 |
| SC-ATL105-B3DE8567 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Petroleum · attended POS | 3 |
| SC-ATL105-5446114B | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Supermarket · attended POS | 1 |
| SC-ATL105-D9BBE155 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Utilities · attended POS | 1 |
| SC-ATL105-60C55097 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ General Retail · attended POS | 9 |
| SC-ATL105-DDF070B6 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Petroleum · attended POS | 3 |
| SC-ATL105-9B63DA61 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Supermarket · attended POS | 1 |
| SC-ATL105-ABF3F8D8 | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Utilities · attended POS | 1 |
| SC-ATL105-A0EF7F0D | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ General Retail · attended POS | 24 |
| SC-ATL105-0B4F31B7 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Hospitality · attended POS | 2 |
| SC-ATL105-2FB57EC0 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 |
| SC-ATL105-1CC160A6 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6211 (high risk security) · attended POS | 1 |
| SC-ATL105-00955B6A | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Petroleum · attended POS | 6 |
| SC-ATL105-8A82A179 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Supermarket · attended POS | 1 |
| SC-ATL105-6964A6BF | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Utilities · attended POS | 1 |
| SC-ATL105-EBD811C6 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · attended POS | 24 |
| SC-ATL105-DDB01831 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · unattended CAT | 1 |
| SC-ATL105-354E7BE3 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · attended POS | 2 |
| SC-ATL105-C679544C | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · unattended CAT | 1 |
| SC-ATL105-41B94458 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 |
| SC-ATL105-71D768CE | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 |
| SC-ATL105-7B5C5BF1 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS | 1 |
| SC-ATL105-000F41EE | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · attended POS | 6 |
| SC-ATL105-120C1A07 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 4 |
| SC-ATL105-8B9E2091 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · attended POS | 1 |
| SC-ATL105-3EE3082B | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · unattended CAT | 1 |
| SC-ATL105-7FBABC84 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · attended POS | 1 |
| SC-ATL105-053D91ED | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · unattended CAT | 1 |
| SC-ATL105-67649BC0 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · attended POS | 24 |
| SC-ATL105-ACA8835B | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · unattended CAT | 1 |
| SC-ATL105-1DAA6F93 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · attended POS | 2 |
| SC-ATL105-67352694 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · unattended CAT | 1 |
| SC-ATL105-7C4EE598 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 |
| SC-ATL105-C1EFED34 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 |
| SC-ATL105-EB7AFA8C | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6211 (high risk security) · attended POS | 1 |
| SC-ATL105-6070CD4F | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · attended POS | 6 |
| SC-ATL105-74D4DC2F | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine) | 4 |
| SC-ATL105-57B55A9C | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · attended POS | 1 |
| SC-ATL105-B4D86B80 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · unattended CAT | 1 |
| SC-ATL105-AE1C3A5C | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · attended POS | 1 |
| SC-ATL105-2469B825 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · unattended CAT | 1 |
| SC-ATL105-DA88DB74 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File @ Credential on File · customer present; card not present | 26 |
| SC-ATL105-93D7F6A8 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · attended POS | 30 |
| SC-ATL105-7C23049D | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · unattended CAT | 1 |
| SC-ATL105-EA8371EB | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · attended POS | 6 |
| SC-ATL105-78392C52 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · unattended CAT | 1 |
| SC-ATL105-B063BCE3 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 3 |
| SC-ATL105-5BF8E494 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 4 |
| SC-ATL105-1768C7CF | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6211 (high risk security) · attended POS | 4 |
| SC-ATL105-B5496FB5 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · attended POS | 14 |
| SC-ATL105-BF3D1777 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) | 7 |
| SC-ATL105-05C50700 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · attended POS | 1 |
| SC-ATL105-FCAA3088 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · unattended CAT | 1 |
| SC-ATL105-126ED70B | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · attended POS | 1 |
| SC-ATL105-940C9AA2 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · unattended CAT | 1 |
| SC-ATL105-A21F371F | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · attended POS | 26 |
| SC-ATL105-D75CF311 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · unattended CAT | 1 |
| SC-ATL105-2B9A3CD9 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · attended POS | 2 |
| SC-ATL105-948B2767 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · unattended CAT | 1 |
| SC-ATL105-87D1178C | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 |
| SC-ATL105-A94BEA8D | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 |
| SC-ATL105-881273CB | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6211 (high risk security) · attended POS | 1 |
| SC-ATL105-B4E6A1C1 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · attended POS | 8 |
| SC-ATL105-D4408D33 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) | 4 |
| SC-ATL105-7A4D2913 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · attended POS | 1 |
| SC-ATL105-AB57A844 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · unattended CAT | 1 |
| SC-ATL105-E6FF365F | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · attended POS | 1 |
| SC-ATL105-3F1DD348 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · unattended CAT | 1 |
| SC-ATL105-12BB9B38 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · attended POS | 27 |
| SC-ATL105-D3C1F6EE | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · unattended CAT | 1 |
| SC-ATL105-1B7DB9D5 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · attended POS | 2 |
| SC-ATL105-B45D131F | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · unattended CAT | 1 |
| SC-ATL105-E3520A16 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 |
| SC-ATL105-55A48834 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 |
| SC-ATL105-4ADF7122 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS | 1 |
| SC-ATL105-A28F1156 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · attended POS | 6 |
| SC-ATL105-8E9C2873 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 4 |
| SC-ATL105-29733E1E | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · attended POS | 1 |
| SC-ATL105-1927FCFC | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · unattended CAT | 1 |
| SC-ATL105-CEFB6DB4 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · attended POS | 1 |
| SC-ATL105-F64C26C1 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · unattended CAT | 1 |
| SC-ATL105-651AE253 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ General Retail · attended POS | 24 |
| SC-ATL105-7B7648CE | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Hospitality · attended POS | 2 |
| SC-ATL105-7CC97653 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 |
| SC-ATL105-6A7283D0 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6211 (high risk security) · attended POS | 1 |
| SC-ATL105-A5A02764 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Petroleum · attended POS | 6 |
| SC-ATL105-1D49A967 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Supermarket · attended POS | 1 |
| SC-ATL105-D10A1552 | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Utilities · attended POS | 1 |
