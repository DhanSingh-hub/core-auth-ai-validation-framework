# Manual validation documents - one per test case (MasterCard Credit run of 16 Nov 2026 (plan v12))

2,129 test cases in 182 scenarios: FAIL 245 · BLOCKED 1,748 · REVIEW 0 · PASS 0 · DEVICE 136.

Every document has the same sections: 1 how to check by hand, 2 what the test case does, 3 field-by-field check of the test data against ATL105, 4 links between steps and expected responses, 5 findings, 6 how the test data was generated and what is missing, 7 the ATL105 text the test case quotes, 8 the ATL105 text used (copied with PDF page and printed page), 9 sign-off.

| Scenario | Name | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE |
|---|---|---:|---:|---:|---:|---:|---:|
| [BR1_TS1](#br1_ts1) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · attended POS | 30 | 5 | 25 | 0 | 0 | 0 |
| [BR1_TS2](#br1_ts2) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · attended POS | 26 | 2 | 24 | 0 | 0 | 0 |
| [BR1_TS3](#br1_ts3) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · attended POS | 27 | 1 | 24 | 0 | 0 | 2 |
| [BR1_TS4](#br1_ts4) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · attended POS | 24 | 0 | 24 | 0 | 0 | 0 |
| [BR1_TS5](#br1_ts5) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ General Retail · attended POS | 24 | 0 | 24 | 0 | 0 | 0 |
| [BR1_TS6](#br1_ts6) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ General Retail · attended POS | 24 | 0 | 24 | 0 | 0 | 0 |
| [BR1_TS7](#br1_ts7) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File @ Credential on File · customer present; card not present | 26 | 2 | 24 | 0 | 0 | 0 |
| [BR1_TS8](#br1_ts8) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · attended POS | 24 | 0 | 24 | 0 | 0 | 0 |
| [BR1_TS9](#br1_ts9) | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed @ Mail Order · mail and/or telephone order | 35 | 1 | 27 | 0 | 0 | 7 |
| [BR1_TS10](#br1_ts10) | BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File @ Mail Order · mail and/or telephone order | 22 | 1 | 21 | 0 | 0 | 0 |
| [BR1_TS11](#br1_ts11) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · attended POS | 119 | 24 | 95 | 0 | 0 | 0 |
| [BR1_TS12](#br1_ts12) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · attended POS | 115 | 20 | 95 | 0 | 0 | 0 |
| [BR1_TS13](#br1_ts13) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · attended POS | 205 | 21 | 96 | 0 | 0 | 88 |
| [BR1_TS14](#br1_ts14) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · attended POS | 111 | 19 | 92 | 0 | 0 | 0 |
| [BR1_TS15](#br1_ts15) | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ General Retail · attended POS | 115 | 21 | 94 | 0 | 0 | 0 |
| [BR1_TS16](#br1_ts16) | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ General Retail · attended POS | 115 | 19 | 96 | 0 | 0 | 0 |
| [BR1_TS17](#br1_ts17) | BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File @ Credential on File · customer present; card not present | 125 | 23 | 102 | 0 | 0 | 0 |
| [BR1_TS18](#br1_ts18) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · attended POS | 110 | 19 | 91 | 0 | 0 | 0 |
| [BR1_TS19](#br1_ts19) | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed @ Mail Order · mail and/or telephone order | 24 | 3 | 14 | 0 | 0 | 7 |
| [BR1_TS20](#br1_ts20) | BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File @ Mail Order · mail and/or telephone order | 11 | 3 | 8 | 0 | 0 | 0 |
| [BR1_TS21](#br1_ts21) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ General Retail · attended POS | 11 | 3 | 8 | 0 | 0 | 0 |
| [BR1_TS22](#br1_ts22) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ General Retail · attended POS | 9 | 1 | 8 | 0 | 0 | 0 |
| [BR1_TS23](#br1_ts23) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ General Retail · attended POS | 9 | 1 | 8 | 0 | 0 | 0 |
| [BR1_TS24](#br1_ts24) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ General Retail · attended POS | 9 | 1 | 8 | 0 | 0 | 0 |
| [BR1_TS25](#br1_ts25) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ General Retail · attended POS | 9 | 1 | 8 | 0 | 0 | 0 |
| [BR1_TS26](#br1_ts26) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ General Retail · attended POS | 10 | 1 | 9 | 0 | 0 | 0 |
| [BR1_TS27](#br1_ts27) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File @ Credential on File · customer present; card not present | 10 | 1 | 9 | 0 | 0 | 0 |
| [BR1_TS28](#br1_ts28) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ General Retail · attended POS | 9 | 1 | 8 | 0 | 0 | 0 |
| [BR1_TS29](#br1_ts29) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip @ General Retail · attended POS | 5 | 3 | 2 | 0 | 0 | 0 |
| [BR1_TS30](#br1_ts30) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip @ General Retail · attended POS | 2 | 1 | 1 | 0 | 0 | 0 |
| [BR1_TS31](#br1_ts31) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe @ General Retail · attended POS | 2 | 1 | 1 | 0 | 0 | 0 |
| [BR1_TS32](#br1_ts32) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe @ General Retail · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS33](#br1_ts33) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed @ General Retail · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS34](#br1_ts34) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback @ General Retail · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS35](#br1_ts35) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File @ Credential on File · customer present; card not present | 4 | 0 | 4 | 0 | 0 | 0 |
| [BR1_TS36](#br1_ts36) | BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device @ General Retail · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS37](#br1_ts37) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS38](#br1_ts38) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS39](#br1_ts39) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS40](#br1_ts40) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS41](#br1_ts41) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS42](#br1_ts42) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS43](#br1_ts43) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS44](#br1_ts44) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS45](#br1_ts45) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS46](#br1_ts46) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS47](#br1_ts47) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · attended POS | 6 | 0 | 6 | 0 | 0 | 0 |
| [BR1_TS48](#br1_ts48) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · attended POS | 2 | 0 | 2 | 0 | 0 | 0 |
| [BR1_TS49](#br1_ts49) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · attended POS | 2 | 0 | 2 | 0 | 0 | 0 |
| [BR1_TS50](#br1_ts50) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · attended POS | 2 | 0 | 2 | 0 | 0 | 0 |
| [BR1_TS51](#br1_ts51) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Hospitality · attended POS | 2 | 0 | 2 | 0 | 0 | 0 |
| [BR1_TS52](#br1_ts52) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Hospitality · attended POS | 2 | 0 | 2 | 0 | 0 | 0 |
| [BR1_TS53](#br1_ts53) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · attended POS | 2 | 0 | 2 | 0 | 0 | 0 |
| [BR1_TS54](#br1_ts54) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · attended POS | 30 | 2 | 28 | 0 | 0 | 0 |
| [BR1_TS55](#br1_ts55) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS56](#br1_ts56) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · attended POS | 4 | 0 | 3 | 0 | 0 | 1 |
| [BR1_TS57](#br1_ts57) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS58](#br1_ts58) | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Hospitality · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS59](#br1_ts59) | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Hospitality · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS60](#br1_ts60) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS61](#br1_ts61) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS62](#br1_ts62) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS63](#br1_ts63) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS64](#br1_ts64) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS65](#br1_ts65) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS66](#br1_ts66) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS67](#br1_ts67) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS68](#br1_ts68) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS69](#br1_ts69) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS70](#br1_ts70) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS71](#br1_ts71) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS72](#br1_ts72) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS73](#br1_ts73) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS74](#br1_ts74) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS75](#br1_ts75) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS76](#br1_ts76) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS77](#br1_ts77) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS78](#br1_ts78) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS79](#br1_ts79) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS80](#br1_ts80) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS81](#br1_ts81) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS82](#br1_ts82) | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS83](#br1_ts83) | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS84](#br1_ts84) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS85](#br1_ts85) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS86](#br1_ts86) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS87](#br1_ts87) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS88](#br1_ts88) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS89](#br1_ts89) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS90](#br1_ts90) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS91](#br1_ts91) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Supermarket · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS99](#br1_ts99) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS100](#br1_ts100) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS101](#br1_ts101) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS102](#br1_ts102) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS103](#br1_ts103) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS104](#br1_ts104) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS105](#br1_ts105) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS106](#br1_ts106) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS107](#br1_ts107) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS108](#br1_ts108) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS109](#br1_ts109) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS110](#br1_ts110) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS111](#br1_ts111) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS112](#br1_ts112) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS113](#br1_ts113) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS114](#br1_ts114) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS115](#br1_ts115) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS116](#br1_ts116) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS117](#br1_ts117) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS118](#br1_ts118) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS119](#br1_ts119) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS120](#br1_ts120) | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS121](#br1_ts121) | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS122](#br1_ts122) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS123](#br1_ts123) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS124](#br1_ts124) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS125](#br1_ts125) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS126](#br1_ts126) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS127](#br1_ts127) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS128](#br1_ts128) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS129](#br1_ts129) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Utilities · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS137](#br1_ts137) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS138](#br1_ts138) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS139](#br1_ts139) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS140](#br1_ts140) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS141](#br1_ts141) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS142](#br1_ts142) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS143](#br1_ts143) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS144](#br1_ts144) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS145](#br1_ts145) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS146](#br1_ts146) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS147](#br1_ts147) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · attended POS | 14 | 3 | 11 | 0 | 0 | 0 |
| [BR1_TS148](#br1_ts148) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · attended POS | 8 | 2 | 6 | 0 | 0 | 0 |
| [BR1_TS149](#br1_ts149) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · attended POS | 6 | 0 | 6 | 0 | 0 | 0 |
| [BR1_TS150](#br1_ts150) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · attended POS | 6 | 0 | 6 | 0 | 0 | 0 |
| [BR1_TS151](#br1_ts151) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Petroleum · attended POS | 6 | 0 | 6 | 0 | 0 | 0 |
| [BR1_TS152](#br1_ts152) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Petroleum · attended POS | 6 | 0 | 6 | 0 | 0 | 0 |
| [BR1_TS153](#br1_ts153) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · attended POS | 6 | 0 | 6 | 0 | 0 | 0 |
| [BR1_TS154](#br1_ts154) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · attended POS | 62 | 8 | 54 | 0 | 0 | 0 |
| [BR1_TS155](#br1_ts155) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · attended POS | 32 | 3 | 29 | 0 | 0 | 0 |
| [BR1_TS156](#br1_ts156) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · attended POS | 43 | 2 | 26 | 0 | 0 | 15 |
| [BR1_TS157](#br1_ts157) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · attended POS | 29 | 3 | 26 | 0 | 0 | 0 |
| [BR1_TS158](#br1_ts158) | BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Petroleum · attended POS | 30 | 3 | 27 | 0 | 0 | 0 |
| [BR1_TS159](#br1_ts159) | BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Petroleum · attended POS | 28 | 2 | 26 | 0 | 0 | 0 |
| [BR1_TS160](#br1_ts160) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · attended POS | 28 | 2 | 26 | 0 | 0 | 0 |
| [BR1_TS161](#br1_ts161) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Petroleum · attended POS | 7 | 2 | 5 | 0 | 0 | 0 |
| [BR1_TS162](#br1_ts162) | BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Petroleum · attended POS | 5 | 2 | 3 | 0 | 0 | 0 |
| [BR1_TS163](#br1_ts163) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Petroleum · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS164](#br1_ts164) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Petroleum · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS165](#br1_ts165) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Petroleum · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS166](#br1_ts166) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Petroleum · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS167](#br1_ts167) | BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Petroleum · attended POS | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS168](#br1_ts168) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) | 7 | 0 | 7 | 0 | 0 | 0 |
| [BR1_TS169](#br1_ts169) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) | 4 | 0 | 4 | 0 | 0 | 0 |
| [BR1_TS170](#br1_ts170) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 4 | 0 | 4 | 0 | 0 | 0 |
| [BR1_TS171](#br1_ts171) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 4 | 0 | 4 | 0 | 0 | 0 |
| [BR1_TS172](#br1_ts172) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine) | 4 | 0 | 4 | 0 | 0 | 0 |
| [BR1_TS173](#br1_ts173) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine) | 59 | 4 | 55 | 0 | 0 | 0 |
| [BR1_TS174](#br1_ts174) | BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine) | 32 | 2 | 30 | 0 | 0 | 0 |
| [BR1_TS175](#br1_ts175) | BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 45 | 2 | 27 | 0 | 0 | 16 |
| [BR1_TS176](#br1_ts176) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine) | 29 | 2 | 27 | 0 | 0 | 0 |
| [BR1_TS177](#br1_ts177) | BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine) | 29 | 2 | 27 | 0 | 0 | 0 |
| [BR1_TS178](#br1_ts178) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 3 | 0 | 3 | 0 | 0 | 0 |
| [BR1_TS179](#br1_ts179) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS180](#br1_ts180) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS181](#br1_ts181) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS182](#br1_ts182) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS183](#br1_ts183) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 4 | 0 | 4 | 0 | 0 | 0 |
| [BR1_TS184](#br1_ts184) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS185](#br1_ts185) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS186](#br1_ts186) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS187](#br1_ts187) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS188](#br1_ts188) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS189](#br1_ts189) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6051 (cryptocurrency purchase) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS190](#br1_ts190) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6211 (high risk security) · attended POS | 4 | 0 | 4 | 0 | 0 | 0 |
| [BR1_TS191](#br1_ts191) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6211 (high risk security) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS192](#br1_ts192) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS193](#br1_ts193) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS194](#br1_ts194) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6211 (high risk security) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS195](#br1_ts195) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6211 (high risk security) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |
| [BR1_TS196](#br1_ts196) | BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6211 (high risk security) · attended POS | 1 | 0 | 1 | 0 | 0 | 0 |

<a id="br1_ts1"></a>
## BR1_TS1 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038627 | BR1_TS1_TC1 | Required field EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. omitted | **FAIL** | [TC-038627.md](BR1_TS1/TC-038627.md) |
| TC-038626 | BR1_TS1_TC2 | EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. one byte over its documented maximum length (999) | **FAIL** | [TC-038626.md](BR1_TS1/TC-038626.md) |
| TC-038635 | BR1_TS1_TC3 | Variable Information one byte over its documented maximum length (982) | **FAIL** | [TC-038635.md](BR1_TS1/TC-038635.md) |
| TC-038630 | BR1_TS1_TC4 | EMV Card Sequence Number value not in its documented valid values | **FAIL** | [TC-038630.md](BR1_TS1/TC-038630.md) |
| TC-038596 | BR1_TS1_TC5 | EMV Request Data Segment (segment 130) omitted where p.162 requires it | **BLOCKED** | [TC-038596.md](BR1_TS1/TC-038596.md) |
| TC-038598 | BR1_TS1_TC6 | Network Token Data Request (segment 153) omitted where p.310 requires it | **FAIL** | [TC-038598.md](BR1_TS1/TC-038598.md) |
| TC-039814 | BR1_TS1_TC7 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039814.md](BR1_TS1/TC-039814.md) |
| TC-039815 | BR1_TS1_TC8 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039815.md](BR1_TS1/TC-039815.md) |
| TC-039816 | BR1_TS1_TC9 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039816.md](BR1_TS1/TC-039816.md) |
| TC-039817 | BR1_TS1_TC10 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039817.md](BR1_TS1/TC-039817.md) |
| TC-039818 | BR1_TS1_TC11 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039818.md](BR1_TS1/TC-039818.md) |
| TC-039819 | BR1_TS1_TC12 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039819.md](BR1_TS1/TC-039819.md) |
| TC-039820 | BR1_TS1_TC13 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039820.md](BR1_TS1/TC-039820.md) |
| TC-039821 | BR1_TS1_TC14 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039821.md](BR1_TS1/TC-039821.md) |
| TC-039822 | BR1_TS1_TC15 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039822.md](BR1_TS1/TC-039822.md) |
| TC-039823 | BR1_TS1_TC16 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039823.md](BR1_TS1/TC-039823.md) |
| TC-039824 | BR1_TS1_TC17 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039824.md](BR1_TS1/TC-039824.md) |
| TC-039825 | BR1_TS1_TC18 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039825.md](BR1_TS1/TC-039825.md) |
| TC-039826 | BR1_TS1_TC19 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039826.md](BR1_TS1/TC-039826.md) |
| TC-039827 | BR1_TS1_TC20 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039827.md](BR1_TS1/TC-039827.md) |
| TC-039828 | BR1_TS1_TC21 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039828.md](BR1_TS1/TC-039828.md) |
| TC-039829 | BR1_TS1_TC22 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-039829.md](BR1_TS1/TC-039829.md) |
| TC-039830 | BR1_TS1_TC23 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-039830.md](BR1_TS1/TC-039830.md) |
| TC-039831 | BR1_TS1_TC24 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-039831.md](BR1_TS1/TC-039831.md) |
| TC-039832 | BR1_TS1_TC25 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-039832.md](BR1_TS1/TC-039832.md) |
| TC-039833 | BR1_TS1_TC26 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-039833.md](BR1_TS1/TC-039833.md) |
| TC-039834 | BR1_TS1_TC27 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-039834.md](BR1_TS1/TC-039834.md) |
| TC-039835 | BR1_TS1_TC28 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039835.md](BR1_TS1/TC-039835.md) |
| TC-039836 | BR1_TS1_TC29 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039836.md](BR1_TS1/TC-039836.md) |
| TC-039837 | BR1_TS1_TC30 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-039837.md](BR1_TS1/TC-039837.md) |

<a id="br1_ts2"></a>
## BR1_TS2 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038632 | BR1_TS2_TC1 | CA Public Key File Checksum exactly at its documented maximum length (25 bytes) is accepted | **FAIL** | [TC-038632.md](BR1_TS2/TC-038632.md) |
| TC-038631 | BR1_TS2_TC2 | Required field Prompt Code omitted | **FAIL** | [TC-038631.md](BR1_TS2/TC-038631.md) |
| TC-039838 | BR1_TS2_TC3 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039838.md](BR1_TS2/TC-039838.md) |
| TC-039839 | BR1_TS2_TC4 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039839.md](BR1_TS2/TC-039839.md) |
| TC-039840 | BR1_TS2_TC5 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039840.md](BR1_TS2/TC-039840.md) |
| TC-039841 | BR1_TS2_TC6 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039841.md](BR1_TS2/TC-039841.md) |
| TC-039842 | BR1_TS2_TC7 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039842.md](BR1_TS2/TC-039842.md) |
| TC-039843 | BR1_TS2_TC8 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039843.md](BR1_TS2/TC-039843.md) |
| TC-039844 | BR1_TS2_TC9 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039844.md](BR1_TS2/TC-039844.md) |
| TC-039845 | BR1_TS2_TC10 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039845.md](BR1_TS2/TC-039845.md) |
| TC-039846 | BR1_TS2_TC11 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039846.md](BR1_TS2/TC-039846.md) |
| TC-039847 | BR1_TS2_TC12 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039847.md](BR1_TS2/TC-039847.md) |
| TC-039848 | BR1_TS2_TC13 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039848.md](BR1_TS2/TC-039848.md) |
| TC-039849 | BR1_TS2_TC14 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039849.md](BR1_TS2/TC-039849.md) |
| TC-039850 | BR1_TS2_TC15 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039850.md](BR1_TS2/TC-039850.md) |
| TC-039851 | BR1_TS2_TC16 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039851.md](BR1_TS2/TC-039851.md) |
| TC-039852 | BR1_TS2_TC17 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039852.md](BR1_TS2/TC-039852.md) |
| TC-039853 | BR1_TS2_TC18 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-039853.md](BR1_TS2/TC-039853.md) |
| TC-039854 | BR1_TS2_TC19 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-039854.md](BR1_TS2/TC-039854.md) |
| TC-039855 | BR1_TS2_TC20 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-039855.md](BR1_TS2/TC-039855.md) |
| TC-039856 | BR1_TS2_TC21 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-039856.md](BR1_TS2/TC-039856.md) |
| TC-039857 | BR1_TS2_TC22 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-039857.md](BR1_TS2/TC-039857.md) |
| TC-039858 | BR1_TS2_TC23 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-039858.md](BR1_TS2/TC-039858.md) |
| TC-039859 | BR1_TS2_TC24 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039859.md](BR1_TS2/TC-039859.md) |
| TC-039860 | BR1_TS2_TC25 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039860.md](BR1_TS2/TC-039860.md) |
| TC-039861 | BR1_TS2_TC26 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-039861.md](BR1_TS2/TC-039861.md) |

<a id="br1_ts3"></a>
## BR1_TS3 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038628 | BR1_TS3_TC1 | Cryptogram Token Data one byte over its documented maximum length (56) | **FAIL** | [TC-038628.md](BR1_TS3/TC-038628.md) |
| TC-039862 | BR1_TS3_TC2 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039862.md](BR1_TS3/TC-039862.md) |
| TC-039863 | BR1_TS3_TC3 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039863.md](BR1_TS3/TC-039863.md) |
| TC-039864 | BR1_TS3_TC4 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039864.md](BR1_TS3/TC-039864.md) |
| TC-039865 | BR1_TS3_TC5 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039865.md](BR1_TS3/TC-039865.md) |
| TC-039866 | BR1_TS3_TC6 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039866.md](BR1_TS3/TC-039866.md) |
| TC-039867 | BR1_TS3_TC7 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039867.md](BR1_TS3/TC-039867.md) |
| TC-039868 | BR1_TS3_TC8 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039868.md](BR1_TS3/TC-039868.md) |
| TC-039869 | BR1_TS3_TC9 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039869.md](BR1_TS3/TC-039869.md) |
| TC-039870 | BR1_TS3_TC10 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039870.md](BR1_TS3/TC-039870.md) |
| TC-039871 | BR1_TS3_TC11 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039871.md](BR1_TS3/TC-039871.md) |
| TC-039872 | BR1_TS3_TC12 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039872.md](BR1_TS3/TC-039872.md) |
| TC-039873 | BR1_TS3_TC13 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039873.md](BR1_TS3/TC-039873.md) |
| TC-039874 | BR1_TS3_TC14 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039874.md](BR1_TS3/TC-039874.md) |
| TC-039875 | BR1_TS3_TC15 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039875.md](BR1_TS3/TC-039875.md) |
| TC-039876 | BR1_TS3_TC16 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039876.md](BR1_TS3/TC-039876.md) |
| TC-039877 | BR1_TS3_TC17 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-039877.md](BR1_TS3/TC-039877.md) |
| TC-039878 | BR1_TS3_TC18 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-039878.md](BR1_TS3/TC-039878.md) |
| TC-039879 | BR1_TS3_TC19 | Ordering: Ordering check: Summary of TOR Processing Rules | **DEVICE** | [TC-039879.md](BR1_TS3/TC-039879.md) |
| TC-039880 | BR1_TS3_TC20 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-039880.md](BR1_TS3/TC-039880.md) |
| TC-039881 | BR1_TS3_TC21 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-039881.md](BR1_TS3/TC-039881.md) |
| TC-039882 | BR1_TS3_TC22 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-039882.md](BR1_TS3/TC-039882.md) |
| TC-039883 | BR1_TS3_TC23 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-039883.md](BR1_TS3/TC-039883.md) |
| TC-039884 | BR1_TS3_TC24 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039884.md](BR1_TS3/TC-039884.md) |
| TC-039885 | BR1_TS3_TC25 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039885.md](BR1_TS3/TC-039885.md) |
| TC-039886 | BR1_TS3_TC26 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-039886.md](BR1_TS3/TC-039886.md) |
| TC-039887 | BR1_TS3_TC27 | Device checkpoint: Device behaviour - 3 checks p.44-47 - ATL105 | **DEVICE** | [TC-039887.md](BR1_TS3/TC-039887.md) |

<a id="br1_ts4"></a>
## BR1_TS4 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-039888 | BR1_TS4_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039888.md](BR1_TS4/TC-039888.md) |
| TC-039889 | BR1_TS4_TC2 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039889.md](BR1_TS4/TC-039889.md) |
| TC-039890 | BR1_TS4_TC3 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039890.md](BR1_TS4/TC-039890.md) |
| TC-039891 | BR1_TS4_TC4 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039891.md](BR1_TS4/TC-039891.md) |
| TC-039892 | BR1_TS4_TC5 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039892.md](BR1_TS4/TC-039892.md) |
| TC-039893 | BR1_TS4_TC6 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039893.md](BR1_TS4/TC-039893.md) |
| TC-039894 | BR1_TS4_TC7 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039894.md](BR1_TS4/TC-039894.md) |
| TC-039895 | BR1_TS4_TC8 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039895.md](BR1_TS4/TC-039895.md) |
| TC-039896 | BR1_TS4_TC9 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039896.md](BR1_TS4/TC-039896.md) |
| TC-039897 | BR1_TS4_TC10 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039897.md](BR1_TS4/TC-039897.md) |
| TC-039898 | BR1_TS4_TC11 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039898.md](BR1_TS4/TC-039898.md) |
| TC-039899 | BR1_TS4_TC12 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039899.md](BR1_TS4/TC-039899.md) |
| TC-039900 | BR1_TS4_TC13 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039900.md](BR1_TS4/TC-039900.md) |
| TC-039901 | BR1_TS4_TC14 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039901.md](BR1_TS4/TC-039901.md) |
| TC-039902 | BR1_TS4_TC15 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039902.md](BR1_TS4/TC-039902.md) |
| TC-039903 | BR1_TS4_TC16 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-039903.md](BR1_TS4/TC-039903.md) |
| TC-039904 | BR1_TS4_TC17 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-039904.md](BR1_TS4/TC-039904.md) |
| TC-039905 | BR1_TS4_TC18 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-039905.md](BR1_TS4/TC-039905.md) |
| TC-039906 | BR1_TS4_TC19 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-039906.md](BR1_TS4/TC-039906.md) |
| TC-039907 | BR1_TS4_TC20 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-039907.md](BR1_TS4/TC-039907.md) |
| TC-039908 | BR1_TS4_TC21 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-039908.md](BR1_TS4/TC-039908.md) |
| TC-039909 | BR1_TS4_TC22 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039909.md](BR1_TS4/TC-039909.md) |
| TC-039910 | BR1_TS4_TC23 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039910.md](BR1_TS4/TC-039910.md) |
| TC-039911 | BR1_TS4_TC24 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-039911.md](BR1_TS4/TC-039911.md) |

<a id="br1_ts5"></a>
## BR1_TS5 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-039912 | BR1_TS5_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039912.md](BR1_TS5/TC-039912.md) |
| TC-039913 | BR1_TS5_TC2 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039913.md](BR1_TS5/TC-039913.md) |
| TC-039914 | BR1_TS5_TC3 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039914.md](BR1_TS5/TC-039914.md) |
| TC-039915 | BR1_TS5_TC4 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039915.md](BR1_TS5/TC-039915.md) |
| TC-039916 | BR1_TS5_TC5 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039916.md](BR1_TS5/TC-039916.md) |
| TC-039917 | BR1_TS5_TC6 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039917.md](BR1_TS5/TC-039917.md) |
| TC-039918 | BR1_TS5_TC7 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039918.md](BR1_TS5/TC-039918.md) |
| TC-039919 | BR1_TS5_TC8 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039919.md](BR1_TS5/TC-039919.md) |
| TC-039920 | BR1_TS5_TC9 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039920.md](BR1_TS5/TC-039920.md) |
| TC-039921 | BR1_TS5_TC10 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039921.md](BR1_TS5/TC-039921.md) |
| TC-039922 | BR1_TS5_TC11 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039922.md](BR1_TS5/TC-039922.md) |
| TC-039923 | BR1_TS5_TC12 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039923.md](BR1_TS5/TC-039923.md) |
| TC-039924 | BR1_TS5_TC13 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039924.md](BR1_TS5/TC-039924.md) |
| TC-039925 | BR1_TS5_TC14 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039925.md](BR1_TS5/TC-039925.md) |
| TC-039926 | BR1_TS5_TC15 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039926.md](BR1_TS5/TC-039926.md) |
| TC-039927 | BR1_TS5_TC16 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-039927.md](BR1_TS5/TC-039927.md) |
| TC-039928 | BR1_TS5_TC17 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-039928.md](BR1_TS5/TC-039928.md) |
| TC-039929 | BR1_TS5_TC18 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-039929.md](BR1_TS5/TC-039929.md) |
| TC-039930 | BR1_TS5_TC19 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-039930.md](BR1_TS5/TC-039930.md) |
| TC-039931 | BR1_TS5_TC20 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-039931.md](BR1_TS5/TC-039931.md) |
| TC-039932 | BR1_TS5_TC21 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-039932.md](BR1_TS5/TC-039932.md) |
| TC-039933 | BR1_TS5_TC22 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039933.md](BR1_TS5/TC-039933.md) |
| TC-039934 | BR1_TS5_TC23 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039934.md](BR1_TS5/TC-039934.md) |
| TC-039935 | BR1_TS5_TC24 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-039935.md](BR1_TS5/TC-039935.md) |

<a id="br1_ts6"></a>
## BR1_TS6 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-039936 | BR1_TS6_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039936.md](BR1_TS6/TC-039936.md) |
| TC-039937 | BR1_TS6_TC2 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039937.md](BR1_TS6/TC-039937.md) |
| TC-039938 | BR1_TS6_TC3 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039938.md](BR1_TS6/TC-039938.md) |
| TC-039939 | BR1_TS6_TC4 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039939.md](BR1_TS6/TC-039939.md) |
| TC-039940 | BR1_TS6_TC5 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039940.md](BR1_TS6/TC-039940.md) |
| TC-039941 | BR1_TS6_TC6 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039941.md](BR1_TS6/TC-039941.md) |
| TC-039942 | BR1_TS6_TC7 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039942.md](BR1_TS6/TC-039942.md) |
| TC-039943 | BR1_TS6_TC8 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039943.md](BR1_TS6/TC-039943.md) |
| TC-039944 | BR1_TS6_TC9 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039944.md](BR1_TS6/TC-039944.md) |
| TC-039945 | BR1_TS6_TC10 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039945.md](BR1_TS6/TC-039945.md) |
| TC-039946 | BR1_TS6_TC11 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039946.md](BR1_TS6/TC-039946.md) |
| TC-039947 | BR1_TS6_TC12 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039947.md](BR1_TS6/TC-039947.md) |
| TC-039948 | BR1_TS6_TC13 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039948.md](BR1_TS6/TC-039948.md) |
| TC-039949 | BR1_TS6_TC14 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039949.md](BR1_TS6/TC-039949.md) |
| TC-039950 | BR1_TS6_TC15 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039950.md](BR1_TS6/TC-039950.md) |
| TC-039951 | BR1_TS6_TC16 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-039951.md](BR1_TS6/TC-039951.md) |
| TC-039952 | BR1_TS6_TC17 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-039952.md](BR1_TS6/TC-039952.md) |
| TC-039953 | BR1_TS6_TC18 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-039953.md](BR1_TS6/TC-039953.md) |
| TC-039954 | BR1_TS6_TC19 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-039954.md](BR1_TS6/TC-039954.md) |
| TC-039955 | BR1_TS6_TC20 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-039955.md](BR1_TS6/TC-039955.md) |
| TC-039956 | BR1_TS6_TC21 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-039956.md](BR1_TS6/TC-039956.md) |
| TC-039957 | BR1_TS6_TC22 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039957.md](BR1_TS6/TC-039957.md) |
| TC-039958 | BR1_TS6_TC23 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039958.md](BR1_TS6/TC-039958.md) |
| TC-039959 | BR1_TS6_TC24 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-039959.md](BR1_TS6/TC-039959.md) |

<a id="br1_ts7"></a>
## BR1_TS7 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File @ Credential on File · customer present; card not present

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038602 | BR1_TS7_TC1 | Enhanced Fleet Request (segment 145) omitted where p.561 requires it | **FAIL** | [TC-038602.md](BR1_TS7/TC-038602.md) |
| TC-039960 | BR1_TS7_TC2 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039960.md](BR1_TS7/TC-039960.md) |
| TC-039961 | BR1_TS7_TC3 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039961.md](BR1_TS7/TC-039961.md) |
| TC-039962 | BR1_TS7_TC4 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039962.md](BR1_TS7/TC-039962.md) |
| TC-039963 | BR1_TS7_TC5 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039963.md](BR1_TS7/TC-039963.md) |
| TC-039964 | BR1_TS7_TC6 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039964.md](BR1_TS7/TC-039964.md) |
| TC-039965 | BR1_TS7_TC7 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039965.md](BR1_TS7/TC-039965.md) |
| TC-039966 | BR1_TS7_TC8 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039966.md](BR1_TS7/TC-039966.md) |
| TC-039967 | BR1_TS7_TC9 | Decision table: Purchase/Capture with segment 145 sent (the rule's condition holds) | **FAIL** | [TC-039967.md](BR1_TS7/TC-039967.md) |
| TC-039968 | BR1_TS7_TC10 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039968.md](BR1_TS7/TC-039968.md) |
| TC-039969 | BR1_TS7_TC11 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039969.md](BR1_TS7/TC-039969.md) |
| TC-039970 | BR1_TS7_TC12 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039970.md](BR1_TS7/TC-039970.md) |
| TC-039971 | BR1_TS7_TC13 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039971.md](BR1_TS7/TC-039971.md) |
| TC-039972 | BR1_TS7_TC14 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039972.md](BR1_TS7/TC-039972.md) |
| TC-039973 | BR1_TS7_TC15 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039973.md](BR1_TS7/TC-039973.md) |
| TC-039974 | BR1_TS7_TC16 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039974.md](BR1_TS7/TC-039974.md) |
| TC-039975 | BR1_TS7_TC17 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039975.md](BR1_TS7/TC-039975.md) |
| TC-039976 | BR1_TS7_TC18 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-039976.md](BR1_TS7/TC-039976.md) |
| TC-039977 | BR1_TS7_TC19 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-039977.md](BR1_TS7/TC-039977.md) |
| TC-039978 | BR1_TS7_TC20 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-039978.md](BR1_TS7/TC-039978.md) |
| TC-039979 | BR1_TS7_TC21 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-039979.md](BR1_TS7/TC-039979.md) |
| TC-039980 | BR1_TS7_TC22 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-039980.md](BR1_TS7/TC-039980.md) |
| TC-039981 | BR1_TS7_TC23 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-039981.md](BR1_TS7/TC-039981.md) |
| TC-039982 | BR1_TS7_TC24 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039982.md](BR1_TS7/TC-039982.md) |
| TC-039983 | BR1_TS7_TC25 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-039983.md](BR1_TS7/TC-039983.md) |
| TC-039984 | BR1_TS7_TC26 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-039984.md](BR1_TS7/TC-039984.md) |

<a id="br1_ts8"></a>
## BR1_TS8 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-039985 | BR1_TS8_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-039985.md](BR1_TS8/TC-039985.md) |
| TC-039986 | BR1_TS8_TC2 | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-039986.md](BR1_TS8/TC-039986.md) |
| TC-039987 | BR1_TS8_TC3 | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039987.md](BR1_TS8/TC-039987.md) |
| TC-039988 | BR1_TS8_TC4 | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-039988.md](BR1_TS8/TC-039988.md) |
| TC-039989 | BR1_TS8_TC5 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-039989.md](BR1_TS8/TC-039989.md) |
| TC-039990 | BR1_TS8_TC6 | Decision table: Purchase/Capture with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-039990.md](BR1_TS8/TC-039990.md) |
| TC-039991 | BR1_TS8_TC7 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-039991.md](BR1_TS8/TC-039991.md) |
| TC-039992 | BR1_TS8_TC8 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-039992.md](BR1_TS8/TC-039992.md) |
| TC-039993 | BR1_TS8_TC9 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-039993.md](BR1_TS8/TC-039993.md) |
| TC-039994 | BR1_TS8_TC10 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-039994.md](BR1_TS8/TC-039994.md) |
| TC-039995 | BR1_TS8_TC11 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-039995.md](BR1_TS8/TC-039995.md) |
| TC-039996 | BR1_TS8_TC12 | Fault / recovery: Purchase/Capture unanswered -> Purchase Reversal | **BLOCKED** | [TC-039996.md](BR1_TS8/TC-039996.md) |
| TC-039997 | BR1_TS8_TC13 | Fault / recovery: Purchase/Capture unanswered -> Time-out Reversal | **BLOCKED** | [TC-039997.md](BR1_TS8/TC-039997.md) |
| TC-039998 | BR1_TS8_TC14 | Idempotency / duplicate / retry: Purchase/Capture unanswered -> Time-out Reversal retried 3 times | **BLOCKED** | [TC-039998.md](BR1_TS8/TC-039998.md) |
| TC-039999 | BR1_TS8_TC15 | Running total / state: Purchase/Capture -> Purchase Reversal -> Totals Request | **BLOCKED** | [TC-039999.md](BR1_TS8/TC-039999.md) |
| TC-040000 | BR1_TS8_TC16 | Running total / state: Purchase/Capture -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-040000.md](BR1_TS8/TC-040000.md) |
| TC-040001 | BR1_TS8_TC17 | Running total / state: Purchase/Capture approved -> Totals Request | **BLOCKED** | [TC-040001.md](BR1_TS8/TC-040001.md) |
| TC-040002 | BR1_TS8_TC18 | Interaction (pairwise): Purchase/Capture on a CAT terminal with online pin | **BLOCKED** | [TC-040002.md](BR1_TS8/TC-040002.md) |
| TC-040003 | BR1_TS8_TC19 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-040003.md](BR1_TS8/TC-040003.md) |
| TC-040004 | BR1_TS8_TC20 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-040004.md](BR1_TS8/TC-040004.md) |
| TC-040005 | BR1_TS8_TC21 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-040005.md](BR1_TS8/TC-040005.md) |
| TC-040006 | BR1_TS8_TC22 | Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline) | **BLOCKED** | [TC-040006.md](BR1_TS8/TC-040006.md) |
| TC-040007 | BR1_TS8_TC23 | Response / oracle check: Purchase/Capture -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-040007.md](BR1_TS8/TC-040007.md) |
| TC-040008 | BR1_TS8_TC24 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-040008.md](BR1_TS8/TC-040008.md) |

<a id="br1_ts9"></a>
## BR1_TS9 - BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed @ Mail Order · mail and/or telephone order

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038592 | BR1_TS9_TC1 | Required field Partial Approval Indicator omitted | **BLOCKED** | [TC-038592.md](BR1_TS9/TC-038592.md) |
| TC-040009 | BR1_TS9_TC2 | Happy path: Mail/Phone Purchase approved | **BLOCKED** | [TC-040009.md](BR1_TS9/TC-040009.md) |
| TC-040010 | BR1_TS9_TC3 | Single rule broken: Mail/Phone Purchase -> Mail/Phone Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040010.md](BR1_TS9/TC-040010.md) |
| TC-040011 | BR1_TS9_TC4 | Single rule broken: Mail/Phone Purchase -> Mail/Phone Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040011.md](BR1_TS9/TC-040011.md) |
| TC-040012 | BR1_TS9_TC5 | Single rule broken: Mail/Phone Purchase -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040012.md](BR1_TS9/TC-040012.md) |
| TC-040013 | BR1_TS9_TC6 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-040013.md](BR1_TS9/TC-040013.md) |
| TC-040014 | BR1_TS9_TC7 | Valid flow: Mail/Phone Purchase -> Mail/Phone Reversal | **BLOCKED** | [TC-040014.md](BR1_TS9/TC-040014.md) |
| TC-040015 | BR1_TS9_TC8 | Valid flow: Mail/Phone Purchase -> Time-out Reversal | **BLOCKED** | [TC-040015.md](BR1_TS9/TC-040015.md) |
| TC-040016 | BR1_TS9_TC9 | Invalid state transition: Mail/Phone Purchase -> Mail/Phone Reversal -> Mail/Phone Reversal again (already closed) -> declined | **BLOCKED** | [TC-040016.md](BR1_TS9/TC-040016.md) |
| TC-040017 | BR1_TS9_TC10 | Invalid state transition: Mail/Phone Purchase -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-040017.md](BR1_TS9/TC-040017.md) |
| TC-040018 | BR1_TS9_TC11 | Invalid state transition: Mail/Phone Reversal with no original -> declined | **BLOCKED** | [TC-040018.md](BR1_TS9/TC-040018.md) |
| TC-040019 | BR1_TS9_TC12 | Fault / recovery: Mail/Phone Purchase unanswered -> Time-out Reversal | **BLOCKED** | [TC-040019.md](BR1_TS9/TC-040019.md) |
| TC-040020 | BR1_TS9_TC13 | Running total / state: Mail/Phone Purchase -> Mail/Phone Reversal -> Totals Request | **BLOCKED** | [TC-040020.md](BR1_TS9/TC-040020.md) |
| TC-040021 | BR1_TS9_TC14 | Running total / state: Mail/Phone Purchase -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-040021.md](BR1_TS9/TC-040021.md) |
| TC-040022 | BR1_TS9_TC15 | Running total / state: Mail/Phone Purchase approved -> Totals Request | **BLOCKED** | [TC-040022.md](BR1_TS9/TC-040022.md) |
| TC-040023 | BR1_TS9_TC16 | Running total / state: Running total / state check: Response Code | **DEVICE** | [TC-040023.md](BR1_TS9/TC-040023.md) |
| TC-040024 | BR1_TS9_TC17 | Running total / state: Running total / state check: rule BR-404-11 (p.404) | **DEVICE** | [TC-040024.md](BR1_TS9/TC-040024.md) |
| TC-040025 | BR1_TS9_TC18 | Running total / state: Running total / state check: rule BR-404-12 (p.404) | **DEVICE** | [TC-040025.md](BR1_TS9/TC-040025.md) |
| TC-040026 | BR1_TS9_TC19 | Running total / state: Running total / state check: rule BR-404-7 (p.404) | **DEVICE** | [TC-040026.md](BR1_TS9/TC-040026.md) |
| TC-040027 | BR1_TS9_TC20 | Running total / state: Running total / state check: rule BR-404-8 (p.404) | **DEVICE** | [TC-040027.md](BR1_TS9/TC-040027.md) |
| TC-040028 | BR1_TS9_TC21 | Ordering: Ordering check: Electronic Mail Data Segment (segment 109) | **DEVICE** | [TC-040028.md](BR1_TS9/TC-040028.md) |
| TC-040029 | BR1_TS9_TC22 | Interaction (pairwise): Mail/Phone Purchase on a POS terminal with offline pin | **BLOCKED** | [TC-040029.md](BR1_TS9/TC-040029.md) |
| TC-040030 | BR1_TS9_TC23 | Response / oracle check: Mail/Phone Purchase -> AVS or NVS or Both Information returned | **BLOCKED** | [TC-040030.md](BR1_TS9/TC-040030.md) |
| TC-040031 | BR1_TS9_TC24 | Response / oracle check: Mail/Phone Purchase -> Mail/Phone Reversal -> declined (issuer decline) | **BLOCKED** | [TC-040031.md](BR1_TS9/TC-040031.md) |
| TC-040032 | BR1_TS9_TC25 | Response / oracle check: Mail/Phone Purchase -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-040032.md](BR1_TS9/TC-040032.md) |
| TC-040033 | BR1_TS9_TC26 | Response / oracle check: Mail/Phone Purchase -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-040033.md](BR1_TS9/TC-040033.md) |
| TC-040034 | BR1_TS9_TC27 | Response / oracle check: Mail/Phone Purchase -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-040034.md](BR1_TS9/TC-040034.md) |
| TC-040035 | BR1_TS9_TC28 | Response / oracle check: Mail/Phone Purchase -> declined (issuer decline) | **FAIL** | [TC-040035.md](BR1_TS9/TC-040035.md) |
| TC-040036 | BR1_TS9_TC29 | Device checkpoint: Theme safety net - Message Format Version Identifier (p.184) | **DEVICE** | [TC-040036.md](BR1_TS9/TC-040036.md) |
| TC-040037 | BR1_TS9_TC30 | Single rule broken: Mail/Phone Purchase with entry mode 05 (EMV Contact Chip, not supported) -> declined | **BLOCKED** | [TC-040037.md](BR1_TS9/TC-040037.md) |
| TC-040038 | BR1_TS9_TC31 | Single rule broken: Mail/Phone Purchase with entry mode 07 (EMV Contactless Chip, not supported) -> declined | **BLOCKED** | [TC-040038.md](BR1_TS9/TC-040038.md) |
| TC-040039 | BR1_TS9_TC32 | Single rule broken: Mail/Phone Purchase with entry mode 02 (Magnetic Stripe, not supported) -> declined | **BLOCKED** | [TC-040039.md](BR1_TS9/TC-040039.md) |
| TC-040040 | BR1_TS9_TC33 | Single rule broken: Mail/Phone Purchase with entry mode 91 (Contactless Magnetic Stripe, not supported) -> declined | **BLOCKED** | [TC-040040.md](BR1_TS9/TC-040040.md) |
| TC-040041 | BR1_TS9_TC34 | Single rule broken: Mail/Phone Purchase with entry mode 79 (Chip Fallback, not supported) -> declined | **BLOCKED** | [TC-040041.md](BR1_TS9/TC-040041.md) |
| TC-040042 | BR1_TS9_TC35 | Single rule broken: Mail/Phone Purchase with entry mode 82 (Contactless Mobile Commerce Device, not supported) -> declined | **BLOCKED** | [TC-040042.md](BR1_TS9/TC-040042.md) |

<a id="br1_ts10"></a>
## BR1_TS10 - BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File @ Mail Order · mail and/or telephone order

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-040043 | BR1_TS10_TC1 | Happy path: Mail/Phone Purchase approved | **BLOCKED** | [TC-040043.md](BR1_TS10/TC-040043.md) |
| TC-040044 | BR1_TS10_TC2 | Single rule broken: Mail/Phone Purchase -> Mail/Phone Reversal with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040044.md](BR1_TS10/TC-040044.md) |
| TC-040045 | BR1_TS10_TC3 | Single rule broken: Mail/Phone Purchase -> Mail/Phone Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040045.md](BR1_TS10/TC-040045.md) |
| TC-040046 | BR1_TS10_TC4 | Single rule broken: Mail/Phone Purchase -> Time-out Reversal with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040046.md](BR1_TS10/TC-040046.md) |
| TC-040047 | BR1_TS10_TC5 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-040047.md](BR1_TS10/TC-040047.md) |
| TC-040048 | BR1_TS10_TC6 | Valid flow: Mail/Phone Purchase -> Mail/Phone Reversal | **BLOCKED** | [TC-040048.md](BR1_TS10/TC-040048.md) |
| TC-040049 | BR1_TS10_TC7 | Valid flow: Mail/Phone Purchase -> Time-out Reversal | **BLOCKED** | [TC-040049.md](BR1_TS10/TC-040049.md) |
| TC-040050 | BR1_TS10_TC8 | Invalid state transition: Mail/Phone Purchase -> Mail/Phone Reversal -> Mail/Phone Reversal again (already closed) -> declined | **BLOCKED** | [TC-040050.md](BR1_TS10/TC-040050.md) |
| TC-040051 | BR1_TS10_TC9 | Invalid state transition: Mail/Phone Purchase -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-040051.md](BR1_TS10/TC-040051.md) |
| TC-040052 | BR1_TS10_TC10 | Invalid state transition: Mail/Phone Reversal with no original -> declined | **BLOCKED** | [TC-040052.md](BR1_TS10/TC-040052.md) |
| TC-040053 | BR1_TS10_TC11 | Fault / recovery: Mail/Phone Purchase unanswered -> Mail/Phone Reversal | **BLOCKED** | [TC-040053.md](BR1_TS10/TC-040053.md) |
| TC-040054 | BR1_TS10_TC12 | Fault / recovery: Mail/Phone Purchase unanswered -> Time-out Reversal | **BLOCKED** | [TC-040054.md](BR1_TS10/TC-040054.md) |
| TC-040055 | BR1_TS10_TC13 | Running total / state: Mail/Phone Purchase -> Mail/Phone Reversal -> Totals Request | **BLOCKED** | [TC-040055.md](BR1_TS10/TC-040055.md) |
| TC-040056 | BR1_TS10_TC14 | Running total / state: Mail/Phone Purchase -> Time-out Reversal -> Totals Request | **BLOCKED** | [TC-040056.md](BR1_TS10/TC-040056.md) |
| TC-040057 | BR1_TS10_TC15 | Running total / state: Mail/Phone Purchase approved -> Totals Request | **BLOCKED** | [TC-040057.md](BR1_TS10/TC-040057.md) |
| TC-040058 | BR1_TS10_TC16 | Interaction (pairwise): Mail/Phone Purchase on a POS terminal with offline pin | **BLOCKED** | [TC-040058.md](BR1_TS10/TC-040058.md) |
| TC-040059 | BR1_TS10_TC17 | Response / oracle check: Mail/Phone Purchase -> AVS or NVS or Both Information returned | **BLOCKED** | [TC-040059.md](BR1_TS10/TC-040059.md) |
| TC-040060 | BR1_TS10_TC18 | Response / oracle check: Mail/Phone Purchase -> Mail/Phone Reversal -> declined (issuer decline) | **BLOCKED** | [TC-040060.md](BR1_TS10/TC-040060.md) |
| TC-040061 | BR1_TS10_TC19 | Response / oracle check: Mail/Phone Purchase -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-040061.md](BR1_TS10/TC-040061.md) |
| TC-040062 | BR1_TS10_TC20 | Response / oracle check: Mail/Phone Purchase -> Merchant Data Segment returned and checked (p.89-156) | **BLOCKED** | [TC-040062.md](BR1_TS10/TC-040062.md) |
| TC-040063 | BR1_TS10_TC21 | Response / oracle check: Mail/Phone Purchase -> Time-out Reversal -> declined (issuer decline) | **BLOCKED** | [TC-040063.md](BR1_TS10/TC-040063.md) |
| TC-040064 | BR1_TS10_TC22 | Response / oracle check: Mail/Phone Purchase -> declined (issuer decline) | **FAIL** | [TC-040064.md](BR1_TS10/TC-040064.md) |

<a id="br1_ts11"></a>
## BR1_TS11 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038603 | BR1_TS11_TC1 | Required field EMV Chip Data Length sent empty | **FAIL** | [TC-038603.md](BR1_TS11/TC-038603.md) |
| TC-038593 | BR1_TS11_TC2 | Partial Approval Indicator value not in its documented valid values | **FAIL** | [TC-038593.md](BR1_TS11/TC-038593.md) |
| TC-038636 | BR1_TS11_TC3 | Variable Information Indicator value not in its documented valid values | **FAIL** | [TC-038636.md](BR1_TS11/TC-038636.md) |
| TC-038604 | BR1_TS11_TC4 | NFC Payment Tokenization Data Segment (segment 123) omitted where p.688 requires it | **FAIL** | [TC-038604.md](BR1_TS11/TC-038604.md) |
| TC-038608 | BR1_TS11_TC5 | Purchase Card Data Segment (segment 104) omitted where p.28 requires it | **FAIL** | [TC-038608.md](BR1_TS11/TC-038608.md) |
| TC-040065 | BR1_TS11_TC6 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040065.md](BR1_TS11/TC-040065.md) |
| TC-040066 | BR1_TS11_TC7 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040066.md](BR1_TS11/TC-040066.md) |
| TC-040067 | BR1_TS11_TC8 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040067.md](BR1_TS11/TC-040067.md) |
| TC-040068 | BR1_TS11_TC9 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040068.md](BR1_TS11/TC-040068.md) |
| TC-040069 | BR1_TS11_TC10 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040069.md](BR1_TS11/TC-040069.md) |
| TC-040070 | BR1_TS11_TC11 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040070.md](BR1_TS11/TC-040070.md) |
| TC-040071 | BR1_TS11_TC12 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040071.md](BR1_TS11/TC-040071.md) |
| TC-040072 | BR1_TS11_TC13 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040072.md](BR1_TS11/TC-040072.md) |
| TC-040073 | BR1_TS11_TC14 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040073.md](BR1_TS11/TC-040073.md) |
| TC-040074 | BR1_TS11_TC15 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040074.md](BR1_TS11/TC-040074.md) |
| TC-040075 | BR1_TS11_TC16 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040075.md](BR1_TS11/TC-040075.md) |
| TC-040076 | BR1_TS11_TC17 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040076.md](BR1_TS11/TC-040076.md) |
| TC-040077 | BR1_TS11_TC18 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040077.md](BR1_TS11/TC-040077.md) |
| TC-040078 | BR1_TS11_TC19 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040078.md](BR1_TS11/TC-040078.md) |
| TC-040079 | BR1_TS11_TC20 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040079.md](BR1_TS11/TC-040079.md) |
| TC-040080 | BR1_TS11_TC21 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040080.md](BR1_TS11/TC-040080.md) |
| TC-040081 | BR1_TS11_TC22 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040081.md](BR1_TS11/TC-040081.md) |
| TC-040082 | BR1_TS11_TC23 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040082.md](BR1_TS11/TC-040082.md) |
| TC-040083 | BR1_TS11_TC24 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040083.md](BR1_TS11/TC-040083.md) |
| TC-040084 | BR1_TS11_TC25 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040084.md](BR1_TS11/TC-040084.md) |
| TC-040085 | BR1_TS11_TC26 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040085.md](BR1_TS11/TC-040085.md) |
| TC-040086 | BR1_TS11_TC27 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040086.md](BR1_TS11/TC-040086.md) |
| TC-040087 | BR1_TS11_TC28 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040087.md](BR1_TS11/TC-040087.md) |
| TC-040088 | BR1_TS11_TC29 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040088.md](BR1_TS11/TC-040088.md) |
| TC-040089 | BR1_TS11_TC30 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040089.md](BR1_TS11/TC-040089.md) |
| TC-040090 | BR1_TS11_TC31 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040090.md](BR1_TS11/TC-040090.md) |
| TC-040091 | BR1_TS11_TC32 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040091.md](BR1_TS11/TC-040091.md) |
| TC-040092 | BR1_TS11_TC33 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040092.md](BR1_TS11/TC-040092.md) |
| TC-040093 | BR1_TS11_TC34 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040093.md](BR1_TS11/TC-040093.md) |
| TC-040094 | BR1_TS11_TC35 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040094.md](BR1_TS11/TC-040094.md) |
| TC-040095 | BR1_TS11_TC36 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040095.md](BR1_TS11/TC-040095.md) |
| TC-040096 | BR1_TS11_TC37 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040096.md](BR1_TS11/TC-040096.md) |
| TC-040097 | BR1_TS11_TC38 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040097.md](BR1_TS11/TC-040097.md) |
| TC-040098 | BR1_TS11_TC39 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040098.md](BR1_TS11/TC-040098.md) |
| TC-040099 | BR1_TS11_TC40 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040099.md](BR1_TS11/TC-040099.md) |
| TC-040100 | BR1_TS11_TC41 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040100.md](BR1_TS11/TC-040100.md) |
| TC-040101 | BR1_TS11_TC42 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040101.md](BR1_TS11/TC-040101.md) |
| TC-040102 | BR1_TS11_TC43 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040102.md](BR1_TS11/TC-040102.md) |
| TC-040103 | BR1_TS11_TC44 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040103.md](BR1_TS11/TC-040103.md) |
| TC-040104 | BR1_TS11_TC45 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040104.md](BR1_TS11/TC-040104.md) |
| TC-040105 | BR1_TS11_TC46 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040105.md](BR1_TS11/TC-040105.md) |
| TC-040106 | BR1_TS11_TC47 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040106.md](BR1_TS11/TC-040106.md) |
| TC-040107 | BR1_TS11_TC48 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-040107.md](BR1_TS11/TC-040107.md) |
| TC-040108 | BR1_TS11_TC49 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-040108.md](BR1_TS11/TC-040108.md) |
| TC-040109 | BR1_TS11_TC50 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-040109.md](BR1_TS11/TC-040109.md) |
| TC-040110 | BR1_TS11_TC51 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-040110.md](BR1_TS11/TC-040110.md) |
| TC-040111 | BR1_TS11_TC52 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-040111.md](BR1_TS11/TC-040111.md) |
| TC-040112 | BR1_TS11_TC53 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-040112.md](BR1_TS11/TC-040112.md) |
| TC-040113 | BR1_TS11_TC54 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-040113.md](BR1_TS11/TC-040113.md) |
| TC-040114 | BR1_TS11_TC55 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-040114.md](BR1_TS11/TC-040114.md) |
| TC-040115 | BR1_TS11_TC56 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-040115.md](BR1_TS11/TC-040115.md) |
| TC-040116 | BR1_TS11_TC57 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-040116.md](BR1_TS11/TC-040116.md) |
| TC-040117 | BR1_TS11_TC58 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-040117.md](BR1_TS11/TC-040117.md) |
| TC-040118 | BR1_TS11_TC59 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-040118.md](BR1_TS11/TC-040118.md) |
| TC-040119 | BR1_TS11_TC60 | Decision table: Authorization Only with cof = initial | **BLOCKED** | [TC-040119.md](BR1_TS11/TC-040119.md) |
| TC-040120 | BR1_TS11_TC61 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-040120.md](BR1_TS11/TC-040120.md) |
| TC-040121 | BR1_TS11_TC62 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-040121.md](BR1_TS11/TC-040121.md) |
| TC-040122 | BR1_TS11_TC63 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-040122.md](BR1_TS11/TC-040122.md) |
| TC-040123 | BR1_TS11_TC64 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-040123.md](BR1_TS11/TC-040123.md) |
| TC-040124 | BR1_TS11_TC65 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-040124.md](BR1_TS11/TC-040124.md) |
| TC-040125 | BR1_TS11_TC66 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-040125.md](BR1_TS11/TC-040125.md) |
| TC-040126 | BR1_TS11_TC67 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-040126.md](BR1_TS11/TC-040126.md) |
| TC-040127 | BR1_TS11_TC68 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-040127.md](BR1_TS11/TC-040127.md) |
| TC-040128 | BR1_TS11_TC69 | Decision table: Authorization Only with optional segment 130 present | **BLOCKED** | [TC-040128.md](BR1_TS11/TC-040128.md) |
| TC-040129 | BR1_TS11_TC70 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-040129.md](BR1_TS11/TC-040129.md) |
| TC-040130 | BR1_TS11_TC71 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-040130.md](BR1_TS11/TC-040130.md) |
| TC-040131 | BR1_TS11_TC72 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-040131.md](BR1_TS11/TC-040131.md) |
| TC-040132 | BR1_TS11_TC73 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-040132.md](BR1_TS11/TC-040132.md) |
| TC-040133 | BR1_TS11_TC74 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-040133.md](BR1_TS11/TC-040133.md) |
| TC-040134 | BR1_TS11_TC75 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-040134.md](BR1_TS11/TC-040134.md) |
| TC-040135 | BR1_TS11_TC76 | Time-based: Authorization Only -> Cancellation after 24 hour(s) (at the stated limit) | **BLOCKED** | [TC-040135.md](BR1_TS11/TC-040135.md) |
| TC-040136 | BR1_TS11_TC77 | Time-based: Authorization Only -> Cancellation after 25 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-040136.md](BR1_TS11/TC-040136.md) |
| TC-040137 | BR1_TS11_TC78 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-040137.md](BR1_TS11/TC-040137.md) |
| TC-040138 | BR1_TS11_TC79 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-040138.md](BR1_TS11/TC-040138.md) |
| TC-040139 | BR1_TS11_TC80 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-040139.md](BR1_TS11/TC-040139.md) |
| TC-040140 | BR1_TS11_TC81 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-040140.md](BR1_TS11/TC-040140.md) |
| TC-040141 | BR1_TS11_TC82 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-040141.md](BR1_TS11/TC-040141.md) |
| TC-040142 | BR1_TS11_TC83 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-040142.md](BR1_TS11/TC-040142.md) |
| TC-040143 | BR1_TS11_TC84 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-040143.md](BR1_TS11/TC-040143.md) |
| TC-040144 | BR1_TS11_TC85 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-040144.md](BR1_TS11/TC-040144.md) |
| TC-040145 | BR1_TS11_TC86 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-040145.md](BR1_TS11/TC-040145.md) |
| TC-040146 | BR1_TS11_TC87 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-040146.md](BR1_TS11/TC-040146.md) |
| TC-040147 | BR1_TS11_TC88 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-040147.md](BR1_TS11/TC-040147.md) |
| TC-040148 | BR1_TS11_TC89 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040148.md](BR1_TS11/TC-040148.md) |
| TC-040149 | BR1_TS11_TC90 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.44-209) | **BLOCKED** | [TC-040149.md](BR1_TS11/TC-040149.md) |
| TC-040150 | BR1_TS11_TC91 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.89-176) | **BLOCKED** | [TC-040150.md](BR1_TS11/TC-040150.md) |
| TC-040151 | BR1_TS11_TC92 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-040151.md](BR1_TS11/TC-040151.md) |
| TC-040152 | BR1_TS11_TC93 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-040152.md](BR1_TS11/TC-040152.md) |
| TC-040153 | BR1_TS11_TC94 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-040153.md](BR1_TS11/TC-040153.md) |
| TC-040154 | BR1_TS11_TC95 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-040154.md](BR1_TS11/TC-040154.md) |
| TC-040155 | BR1_TS11_TC96 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-040155.md](BR1_TS11/TC-040155.md) |
| TC-040156 | BR1_TS11_TC97 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-040156.md](BR1_TS11/TC-040156.md) |
| TC-040157 | BR1_TS11_TC98 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040157.md](BR1_TS11/TC-040157.md) |
| TC-040158 | BR1_TS11_TC99 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-040158.md](BR1_TS11/TC-040158.md) |
| TC-040159 | BR1_TS11_TC100 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-040159.md](BR1_TS11/TC-040159.md) |
| TC-040160 | BR1_TS11_TC101 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-040160.md](BR1_TS11/TC-040160.md) |
| TC-040161 | BR1_TS11_TC102 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-040161.md](BR1_TS11/TC-040161.md) |
| TC-040162 | BR1_TS11_TC103 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-040162.md](BR1_TS11/TC-040162.md) |
| TC-040163 | BR1_TS11_TC104 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-040163.md](BR1_TS11/TC-040163.md) |
| TC-040164 | BR1_TS11_TC105 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-040164.md](BR1_TS11/TC-040164.md) |
| TC-040165 | BR1_TS11_TC106 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-040165.md](BR1_TS11/TC-040165.md) |
| TC-040166 | BR1_TS11_TC107 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-040166.md](BR1_TS11/TC-040166.md) |
| TC-040167 | BR1_TS11_TC108 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-040167.md](BR1_TS11/TC-040167.md) |
| TC-040168 | BR1_TS11_TC109 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-040168.md](BR1_TS11/TC-040168.md) |
| TC-040169 | BR1_TS11_TC110 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-040169.md](BR1_TS11/TC-040169.md) |
| TC-040170 | BR1_TS11_TC111 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-040170.md](BR1_TS11/TC-040170.md) |
| TC-040171 | BR1_TS11_TC112 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-040171.md](BR1_TS11/TC-040171.md) |
| TC-040172 | BR1_TS11_TC113 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-040172.md](BR1_TS11/TC-040172.md) |
| TC-040173 | BR1_TS11_TC114 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-040173.md](BR1_TS11/TC-040173.md) |
| TC-040174 | BR1_TS11_TC115 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-040174.md](BR1_TS11/TC-040174.md) |
| TC-040175 | BR1_TS11_TC116 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-040175.md](BR1_TS11/TC-040175.md) |
| TC-040176 | BR1_TS11_TC117 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-040176.md](BR1_TS11/TC-040176.md) |
| TC-040177 | BR1_TS11_TC118 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-040177.md](BR1_TS11/TC-040177.md) |
| TC-040178 | BR1_TS11_TC119 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-040178.md](BR1_TS11/TC-040178.md) |

<a id="br1_ts12"></a>
## BR1_TS12 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038594 | BR1_TS12_TC1 | Product Code one byte over its documented maximum length (3) | **FAIL** | [TC-038594.md](BR1_TS12/TC-038594.md) |
| TC-040179 | BR1_TS12_TC2 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040179.md](BR1_TS12/TC-040179.md) |
| TC-040180 | BR1_TS12_TC3 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040180.md](BR1_TS12/TC-040180.md) |
| TC-040181 | BR1_TS12_TC4 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040181.md](BR1_TS12/TC-040181.md) |
| TC-040182 | BR1_TS12_TC5 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040182.md](BR1_TS12/TC-040182.md) |
| TC-040183 | BR1_TS12_TC6 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040183.md](BR1_TS12/TC-040183.md) |
| TC-040184 | BR1_TS12_TC7 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040184.md](BR1_TS12/TC-040184.md) |
| TC-040185 | BR1_TS12_TC8 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040185.md](BR1_TS12/TC-040185.md) |
| TC-040186 | BR1_TS12_TC9 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040186.md](BR1_TS12/TC-040186.md) |
| TC-040187 | BR1_TS12_TC10 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040187.md](BR1_TS12/TC-040187.md) |
| TC-040188 | BR1_TS12_TC11 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040188.md](BR1_TS12/TC-040188.md) |
| TC-040189 | BR1_TS12_TC12 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040189.md](BR1_TS12/TC-040189.md) |
| TC-040190 | BR1_TS12_TC13 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040190.md](BR1_TS12/TC-040190.md) |
| TC-040191 | BR1_TS12_TC14 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040191.md](BR1_TS12/TC-040191.md) |
| TC-040192 | BR1_TS12_TC15 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040192.md](BR1_TS12/TC-040192.md) |
| TC-040193 | BR1_TS12_TC16 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040193.md](BR1_TS12/TC-040193.md) |
| TC-040194 | BR1_TS12_TC17 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040194.md](BR1_TS12/TC-040194.md) |
| TC-040195 | BR1_TS12_TC18 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040195.md](BR1_TS12/TC-040195.md) |
| TC-040196 | BR1_TS12_TC19 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040196.md](BR1_TS12/TC-040196.md) |
| TC-040197 | BR1_TS12_TC20 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040197.md](BR1_TS12/TC-040197.md) |
| TC-040198 | BR1_TS12_TC21 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040198.md](BR1_TS12/TC-040198.md) |
| TC-040199 | BR1_TS12_TC22 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040199.md](BR1_TS12/TC-040199.md) |
| TC-040200 | BR1_TS12_TC23 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040200.md](BR1_TS12/TC-040200.md) |
| TC-040201 | BR1_TS12_TC24 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040201.md](BR1_TS12/TC-040201.md) |
| TC-040202 | BR1_TS12_TC25 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040202.md](BR1_TS12/TC-040202.md) |
| TC-040203 | BR1_TS12_TC26 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040203.md](BR1_TS12/TC-040203.md) |
| TC-040204 | BR1_TS12_TC27 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040204.md](BR1_TS12/TC-040204.md) |
| TC-040205 | BR1_TS12_TC28 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040205.md](BR1_TS12/TC-040205.md) |
| TC-040206 | BR1_TS12_TC29 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040206.md](BR1_TS12/TC-040206.md) |
| TC-040207 | BR1_TS12_TC30 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040207.md](BR1_TS12/TC-040207.md) |
| TC-040208 | BR1_TS12_TC31 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040208.md](BR1_TS12/TC-040208.md) |
| TC-040209 | BR1_TS12_TC32 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040209.md](BR1_TS12/TC-040209.md) |
| TC-040210 | BR1_TS12_TC33 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040210.md](BR1_TS12/TC-040210.md) |
| TC-040211 | BR1_TS12_TC34 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040211.md](BR1_TS12/TC-040211.md) |
| TC-040212 | BR1_TS12_TC35 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040212.md](BR1_TS12/TC-040212.md) |
| TC-040213 | BR1_TS12_TC36 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040213.md](BR1_TS12/TC-040213.md) |
| TC-040214 | BR1_TS12_TC37 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040214.md](BR1_TS12/TC-040214.md) |
| TC-040215 | BR1_TS12_TC38 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040215.md](BR1_TS12/TC-040215.md) |
| TC-040216 | BR1_TS12_TC39 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040216.md](BR1_TS12/TC-040216.md) |
| TC-040217 | BR1_TS12_TC40 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040217.md](BR1_TS12/TC-040217.md) |
| TC-040218 | BR1_TS12_TC41 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040218.md](BR1_TS12/TC-040218.md) |
| TC-040219 | BR1_TS12_TC42 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040219.md](BR1_TS12/TC-040219.md) |
| TC-040220 | BR1_TS12_TC43 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040220.md](BR1_TS12/TC-040220.md) |
| TC-040221 | BR1_TS12_TC44 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-040221.md](BR1_TS12/TC-040221.md) |
| TC-040222 | BR1_TS12_TC45 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-040222.md](BR1_TS12/TC-040222.md) |
| TC-040223 | BR1_TS12_TC46 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-040223.md](BR1_TS12/TC-040223.md) |
| TC-040224 | BR1_TS12_TC47 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-040224.md](BR1_TS12/TC-040224.md) |
| TC-040225 | BR1_TS12_TC48 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-040225.md](BR1_TS12/TC-040225.md) |
| TC-040226 | BR1_TS12_TC49 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-040226.md](BR1_TS12/TC-040226.md) |
| TC-040227 | BR1_TS12_TC50 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-040227.md](BR1_TS12/TC-040227.md) |
| TC-040228 | BR1_TS12_TC51 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-040228.md](BR1_TS12/TC-040228.md) |
| TC-040229 | BR1_TS12_TC52 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-040229.md](BR1_TS12/TC-040229.md) |
| TC-040230 | BR1_TS12_TC53 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-040230.md](BR1_TS12/TC-040230.md) |
| TC-040231 | BR1_TS12_TC54 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-040231.md](BR1_TS12/TC-040231.md) |
| TC-040232 | BR1_TS12_TC55 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-040232.md](BR1_TS12/TC-040232.md) |
| TC-040233 | BR1_TS12_TC56 | Decision table: Authorization Only with cof = initial | **BLOCKED** | [TC-040233.md](BR1_TS12/TC-040233.md) |
| TC-040234 | BR1_TS12_TC57 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-040234.md](BR1_TS12/TC-040234.md) |
| TC-040235 | BR1_TS12_TC58 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-040235.md](BR1_TS12/TC-040235.md) |
| TC-040236 | BR1_TS12_TC59 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-040236.md](BR1_TS12/TC-040236.md) |
| TC-040237 | BR1_TS12_TC60 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-040237.md](BR1_TS12/TC-040237.md) |
| TC-040238 | BR1_TS12_TC61 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-040238.md](BR1_TS12/TC-040238.md) |
| TC-040239 | BR1_TS12_TC62 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-040239.md](BR1_TS12/TC-040239.md) |
| TC-040240 | BR1_TS12_TC63 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-040240.md](BR1_TS12/TC-040240.md) |
| TC-040241 | BR1_TS12_TC64 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-040241.md](BR1_TS12/TC-040241.md) |
| TC-040242 | BR1_TS12_TC65 | Decision table: Authorization Only with optional segment 130 present | **BLOCKED** | [TC-040242.md](BR1_TS12/TC-040242.md) |
| TC-040243 | BR1_TS12_TC66 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-040243.md](BR1_TS12/TC-040243.md) |
| TC-040244 | BR1_TS12_TC67 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-040244.md](BR1_TS12/TC-040244.md) |
| TC-040245 | BR1_TS12_TC68 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-040245.md](BR1_TS12/TC-040245.md) |
| TC-040246 | BR1_TS12_TC69 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-040246.md](BR1_TS12/TC-040246.md) |
| TC-040247 | BR1_TS12_TC70 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-040247.md](BR1_TS12/TC-040247.md) |
| TC-040248 | BR1_TS12_TC71 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-040248.md](BR1_TS12/TC-040248.md) |
| TC-040249 | BR1_TS12_TC72 | Time-based: Authorization Only -> Cancellation after 24 hour(s) (at the stated limit) | **BLOCKED** | [TC-040249.md](BR1_TS12/TC-040249.md) |
| TC-040250 | BR1_TS12_TC73 | Time-based: Authorization Only -> Cancellation after 25 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-040250.md](BR1_TS12/TC-040250.md) |
| TC-040251 | BR1_TS12_TC74 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-040251.md](BR1_TS12/TC-040251.md) |
| TC-040252 | BR1_TS12_TC75 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-040252.md](BR1_TS12/TC-040252.md) |
| TC-040253 | BR1_TS12_TC76 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-040253.md](BR1_TS12/TC-040253.md) |
| TC-040254 | BR1_TS12_TC77 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-040254.md](BR1_TS12/TC-040254.md) |
| TC-040255 | BR1_TS12_TC78 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-040255.md](BR1_TS12/TC-040255.md) |
| TC-040256 | BR1_TS12_TC79 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-040256.md](BR1_TS12/TC-040256.md) |
| TC-040257 | BR1_TS12_TC80 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-040257.md](BR1_TS12/TC-040257.md) |
| TC-040258 | BR1_TS12_TC81 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-040258.md](BR1_TS12/TC-040258.md) |
| TC-040259 | BR1_TS12_TC82 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-040259.md](BR1_TS12/TC-040259.md) |
| TC-040260 | BR1_TS12_TC83 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-040260.md](BR1_TS12/TC-040260.md) |
| TC-040261 | BR1_TS12_TC84 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-040261.md](BR1_TS12/TC-040261.md) |
| TC-040262 | BR1_TS12_TC85 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040262.md](BR1_TS12/TC-040262.md) |
| TC-040263 | BR1_TS12_TC86 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.44-209) | **BLOCKED** | [TC-040263.md](BR1_TS12/TC-040263.md) |
| TC-040264 | BR1_TS12_TC87 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.89-176) | **BLOCKED** | [TC-040264.md](BR1_TS12/TC-040264.md) |
| TC-040265 | BR1_TS12_TC88 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-040265.md](BR1_TS12/TC-040265.md) |
| TC-040266 | BR1_TS12_TC89 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-040266.md](BR1_TS12/TC-040266.md) |
| TC-040267 | BR1_TS12_TC90 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-040267.md](BR1_TS12/TC-040267.md) |
| TC-040268 | BR1_TS12_TC91 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-040268.md](BR1_TS12/TC-040268.md) |
| TC-040269 | BR1_TS12_TC92 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-040269.md](BR1_TS12/TC-040269.md) |
| TC-040270 | BR1_TS12_TC93 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-040270.md](BR1_TS12/TC-040270.md) |
| TC-040271 | BR1_TS12_TC94 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040271.md](BR1_TS12/TC-040271.md) |
| TC-040272 | BR1_TS12_TC95 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-040272.md](BR1_TS12/TC-040272.md) |
| TC-040273 | BR1_TS12_TC96 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-040273.md](BR1_TS12/TC-040273.md) |
| TC-040274 | BR1_TS12_TC97 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-040274.md](BR1_TS12/TC-040274.md) |
| TC-040275 | BR1_TS12_TC98 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-040275.md](BR1_TS12/TC-040275.md) |
| TC-040276 | BR1_TS12_TC99 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-040276.md](BR1_TS12/TC-040276.md) |
| TC-040277 | BR1_TS12_TC100 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-040277.md](BR1_TS12/TC-040277.md) |
| TC-040278 | BR1_TS12_TC101 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-040278.md](BR1_TS12/TC-040278.md) |
| TC-040279 | BR1_TS12_TC102 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-040279.md](BR1_TS12/TC-040279.md) |
| TC-040280 | BR1_TS12_TC103 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-040280.md](BR1_TS12/TC-040280.md) |
| TC-040281 | BR1_TS12_TC104 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-040281.md](BR1_TS12/TC-040281.md) |
| TC-040282 | BR1_TS12_TC105 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-040282.md](BR1_TS12/TC-040282.md) |
| TC-040283 | BR1_TS12_TC106 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-040283.md](BR1_TS12/TC-040283.md) |
| TC-040284 | BR1_TS12_TC107 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-040284.md](BR1_TS12/TC-040284.md) |
| TC-040285 | BR1_TS12_TC108 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-040285.md](BR1_TS12/TC-040285.md) |
| TC-040286 | BR1_TS12_TC109 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-040286.md](BR1_TS12/TC-040286.md) |
| TC-040287 | BR1_TS12_TC110 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-040287.md](BR1_TS12/TC-040287.md) |
| TC-040288 | BR1_TS12_TC111 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-040288.md](BR1_TS12/TC-040288.md) |
| TC-040289 | BR1_TS12_TC112 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-040289.md](BR1_TS12/TC-040289.md) |
| TC-040290 | BR1_TS12_TC113 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-040290.md](BR1_TS12/TC-040290.md) |
| TC-040291 | BR1_TS12_TC114 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-040291.md](BR1_TS12/TC-040291.md) |
| TC-040292 | BR1_TS12_TC115 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-040292.md](BR1_TS12/TC-040292.md) |

<a id="br1_ts13"></a>
## BR1_TS13 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038639 | BR1_TS13_TC1 | EMV Request Data Segment (segment 130) omitted where p.142 requires it | **FAIL** | [TC-038639.md](BR1_TS13/TC-038639.md) |
| TC-040293 | BR1_TS13_TC2 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040293.md](BR1_TS13/TC-040293.md) |
| TC-040294 | BR1_TS13_TC3 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040294.md](BR1_TS13/TC-040294.md) |
| TC-040295 | BR1_TS13_TC4 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040295.md](BR1_TS13/TC-040295.md) |
| TC-040296 | BR1_TS13_TC5 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040296.md](BR1_TS13/TC-040296.md) |
| TC-040297 | BR1_TS13_TC6 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040297.md](BR1_TS13/TC-040297.md) |
| TC-040298 | BR1_TS13_TC7 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040298.md](BR1_TS13/TC-040298.md) |
| TC-040299 | BR1_TS13_TC8 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040299.md](BR1_TS13/TC-040299.md) |
| TC-040300 | BR1_TS13_TC9 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040300.md](BR1_TS13/TC-040300.md) |
| TC-040301 | BR1_TS13_TC10 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040301.md](BR1_TS13/TC-040301.md) |
| TC-040302 | BR1_TS13_TC11 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040302.md](BR1_TS13/TC-040302.md) |
| TC-040303 | BR1_TS13_TC12 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040303.md](BR1_TS13/TC-040303.md) |
| TC-040304 | BR1_TS13_TC13 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040304.md](BR1_TS13/TC-040304.md) |
| TC-040305 | BR1_TS13_TC14 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040305.md](BR1_TS13/TC-040305.md) |
| TC-040306 | BR1_TS13_TC15 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040306.md](BR1_TS13/TC-040306.md) |
| TC-040307 | BR1_TS13_TC16 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040307.md](BR1_TS13/TC-040307.md) |
| TC-040308 | BR1_TS13_TC17 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040308.md](BR1_TS13/TC-040308.md) |
| TC-040309 | BR1_TS13_TC18 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040309.md](BR1_TS13/TC-040309.md) |
| TC-040310 | BR1_TS13_TC19 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040310.md](BR1_TS13/TC-040310.md) |
| TC-040311 | BR1_TS13_TC20 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040311.md](BR1_TS13/TC-040311.md) |
| TC-040312 | BR1_TS13_TC21 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040312.md](BR1_TS13/TC-040312.md) |
| TC-040313 | BR1_TS13_TC22 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040313.md](BR1_TS13/TC-040313.md) |
| TC-040314 | BR1_TS13_TC23 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040314.md](BR1_TS13/TC-040314.md) |
| TC-040315 | BR1_TS13_TC24 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040315.md](BR1_TS13/TC-040315.md) |
| TC-040316 | BR1_TS13_TC25 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040316.md](BR1_TS13/TC-040316.md) |
| TC-040317 | BR1_TS13_TC26 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040317.md](BR1_TS13/TC-040317.md) |
| TC-040318 | BR1_TS13_TC27 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040318.md](BR1_TS13/TC-040318.md) |
| TC-040319 | BR1_TS13_TC28 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040319.md](BR1_TS13/TC-040319.md) |
| TC-040320 | BR1_TS13_TC29 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040320.md](BR1_TS13/TC-040320.md) |
| TC-040321 | BR1_TS13_TC30 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040321.md](BR1_TS13/TC-040321.md) |
| TC-040322 | BR1_TS13_TC31 | Alternate path: Authorization Only via alternate path: Access Code | **BLOCKED** | [TC-040322.md](BR1_TS13/TC-040322.md) |
| TC-040323 | BR1_TS13_TC32 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040323.md](BR1_TS13/TC-040323.md) |
| TC-040324 | BR1_TS13_TC33 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040324.md](BR1_TS13/TC-040324.md) |
| TC-040325 | BR1_TS13_TC34 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040325.md](BR1_TS13/TC-040325.md) |
| TC-040326 | BR1_TS13_TC35 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040326.md](BR1_TS13/TC-040326.md) |
| TC-040327 | BR1_TS13_TC36 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040327.md](BR1_TS13/TC-040327.md) |
| TC-040328 | BR1_TS13_TC37 | Alternate path: Authorization Only via alternate path: EMV Chip Data Length | **BLOCKED** | [TC-040328.md](BR1_TS13/TC-040328.md) |
| TC-040329 | BR1_TS13_TC38 | Alternate path: Authorization Only via alternate path: EMV Request Data Segment (segment 130) | **BLOCKED** | [TC-040329.md](BR1_TS13/TC-040329.md) |
| TC-040330 | BR1_TS13_TC39 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040330.md](BR1_TS13/TC-040330.md) |
| TC-040331 | BR1_TS13_TC40 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040331.md](BR1_TS13/TC-040331.md) |
| TC-040332 | BR1_TS13_TC41 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040332.md](BR1_TS13/TC-040332.md) |
| TC-040333 | BR1_TS13_TC42 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040333.md](BR1_TS13/TC-040333.md) |
| TC-040334 | BR1_TS13_TC43 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040334.md](BR1_TS13/TC-040334.md) |
| TC-040335 | BR1_TS13_TC44 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040335.md](BR1_TS13/TC-040335.md) |
| TC-040336 | BR1_TS13_TC45 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040336.md](BR1_TS13/TC-040336.md) |
| TC-040337 | BR1_TS13_TC46 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-040337.md](BR1_TS13/TC-040337.md) |
| TC-040338 | BR1_TS13_TC47 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-040338.md](BR1_TS13/TC-040338.md) |
| TC-040339 | BR1_TS13_TC48 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-040339.md](BR1_TS13/TC-040339.md) |
| TC-040340 | BR1_TS13_TC49 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-040340.md](BR1_TS13/TC-040340.md) |
| TC-040341 | BR1_TS13_TC50 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-040341.md](BR1_TS13/TC-040341.md) |
| TC-040342 | BR1_TS13_TC51 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-040342.md](BR1_TS13/TC-040342.md) |
| TC-040343 | BR1_TS13_TC52 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-040343.md](BR1_TS13/TC-040343.md) |
| TC-040344 | BR1_TS13_TC53 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-040344.md](BR1_TS13/TC-040344.md) |
| TC-040345 | BR1_TS13_TC54 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-040345.md](BR1_TS13/TC-040345.md) |
| TC-040346 | BR1_TS13_TC55 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-040346.md](BR1_TS13/TC-040346.md) |
| TC-040347 | BR1_TS13_TC56 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-040347.md](BR1_TS13/TC-040347.md) |
| TC-040348 | BR1_TS13_TC57 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-040348.md](BR1_TS13/TC-040348.md) |
| TC-040349 | BR1_TS13_TC58 | Decision table: Authorization Only with cof = initial | **BLOCKED** | [TC-040349.md](BR1_TS13/TC-040349.md) |
| TC-040350 | BR1_TS13_TC59 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-040350.md](BR1_TS13/TC-040350.md) |
| TC-040351 | BR1_TS13_TC60 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-040351.md](BR1_TS13/TC-040351.md) |
| TC-040352 | BR1_TS13_TC61 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-040352.md](BR1_TS13/TC-040352.md) |
| TC-040353 | BR1_TS13_TC62 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-040353.md](BR1_TS13/TC-040353.md) |
| TC-040354 | BR1_TS13_TC63 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-040354.md](BR1_TS13/TC-040354.md) |
| TC-040355 | BR1_TS13_TC64 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-040355.md](BR1_TS13/TC-040355.md) |
| TC-040356 | BR1_TS13_TC65 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-040356.md](BR1_TS13/TC-040356.md) |
| TC-040357 | BR1_TS13_TC66 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-040357.md](BR1_TS13/TC-040357.md) |
| TC-040358 | BR1_TS13_TC67 | Decision table: Authorization Only with optional segment 123 present | **BLOCKED** | [TC-040358.md](BR1_TS13/TC-040358.md) |
| TC-040359 | BR1_TS13_TC68 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-040359.md](BR1_TS13/TC-040359.md) |
| TC-040360 | BR1_TS13_TC69 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-040360.md](BR1_TS13/TC-040360.md) |
| TC-040361 | BR1_TS13_TC70 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-040361.md](BR1_TS13/TC-040361.md) |
| TC-040362 | BR1_TS13_TC71 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-040362.md](BR1_TS13/TC-040362.md) |
| TC-040363 | BR1_TS13_TC72 | Soft constraint: Device behaviour - 12 checks p.110-115 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040363.md](BR1_TS13/TC-040363.md) |
| TC-040364 | BR1_TS13_TC73 | Soft constraint: Device behaviour - 2 checks p.313 - ATL105 | **DEVICE** | [TC-040364.md](BR1_TS13/TC-040364.md) |
| TC-040365 | BR1_TS13_TC74 | Soft constraint: Device behaviour - 2 checks p.639 - ATL105 | **DEVICE** | [TC-040365.md](BR1_TS13/TC-040365.md) |
| TC-040366 | BR1_TS13_TC75 | Soft constraint: Device behaviour - Load Type - ATL105 | **DEVICE** | [TC-040366.md](BR1_TS13/TC-040366.md) |
| TC-040367 | BR1_TS13_TC76 | Soft constraint: Device behaviour - Pause Indicator - ATL105 | **DEVICE** | [TC-040367.md](BR1_TS13/TC-040367.md) |
| TC-040368 | BR1_TS13_TC77 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-040368.md](BR1_TS13/TC-040368.md) |
| TC-040369 | BR1_TS13_TC78 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-040369.md](BR1_TS13/TC-040369.md) |
| TC-040370 | BR1_TS13_TC79 | Time-based: Authorization Only -> Cancellation after 24 hour(s) (at the stated limit) | **BLOCKED** | [TC-040370.md](BR1_TS13/TC-040370.md) |
| TC-040371 | BR1_TS13_TC80 | Time-based: Authorization Only -> Cancellation after 25 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-040371.md](BR1_TS13/TC-040371.md) |
| TC-040372 | BR1_TS13_TC81 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-040372.md](BR1_TS13/TC-040372.md) |
| TC-040373 | BR1_TS13_TC82 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-040373.md](BR1_TS13/TC-040373.md) |
| TC-040374 | BR1_TS13_TC83 | Running total / state: Running total / state check: Proprietary Data Load Segment (segment 118) | **DEVICE** | [TC-040374.md](BR1_TS13/TC-040374.md) |
| TC-040375 | BR1_TS13_TC84 | Running total / state: Running total / state check: rule BR-72-2 (p.72) | **DEVICE** | [TC-040375.md](BR1_TS13/TC-040375.md) |
| TC-040376 | BR1_TS13_TC85 | Running total / state: Running total / state check: rule BR-FISERV-EMV-GUIDE-130-1 (p.130) | **DEVICE** | [TC-040376.md](BR1_TS13/TC-040376.md) |
| TC-040377 | BR1_TS13_TC86 | Running total / state: Running total / state check: rule BR-FISERV-EMV-GUIDE-135-1 (p.135) | **DEVICE** | [TC-040377.md](BR1_TS13/TC-040377.md) |
| TC-040378 | BR1_TS13_TC87 | Ordering: Ordering check: Expiration Date | **DEVICE** | [TC-040378.md](BR1_TS13/TC-040378.md) |
| TC-040379 | BR1_TS13_TC88 | Ordering: Ordering check: TransArmor Load Data Segment (segment 116) | **DEVICE** | [TC-040379.md](BR1_TS13/TC-040379.md) |
| TC-040380 | BR1_TS13_TC89 | Ordering: Ordering check: rule BR-718-6 (p.718) | **DEVICE** | [TC-040380.md](BR1_TS13/TC-040380.md) |
| TC-040381 | BR1_TS13_TC90 | Co-requirement / mutual exclusion: Co-requirement / mutual exclusion check: ENT-TABLE-I-049-SUB-PURPOSE_IDENTIFIES_A_DEFERRED_TRANSACTION | **DEVICE** | [TC-040381.md](BR1_TS13/TC-040381.md) |
| TC-040382 | BR1_TS13_TC91 | Co-requirement / mutual exclusion: Co-requirement / mutual exclusion check: Table 032 POS Additional Information Indicator | **DEVICE** | [TC-040382.md](BR1_TS13/TC-040382.md) |
| TC-040383 | BR1_TS13_TC92 | Routing: Routing check: Card Label | **DEVICE** | [TC-040383.md](BR1_TS13/TC-040383.md) |
| TC-040384 | BR1_TS13_TC93 | Routing: Routing check: Dial Backup and Diverse Facilities | **DEVICE** | [TC-040384.md](BR1_TS13/TC-040384.md) |
| TC-040385 | BR1_TS13_TC94 | Routing: Routing check: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **DEVICE** | [TC-040385.md](BR1_TS13/TC-040385.md) |
| TC-040386 | BR1_TS13_TC95 | Routing: Routing check: ENT-TABLE-K-017-SUB-PINLESS_DEBIT_INFORMATION | **DEVICE** | [TC-040386.md](BR1_TS13/TC-040386.md) |
| TC-040387 | BR1_TS13_TC96 | Routing: Routing check: Encrypted PIN Block Data | **DEVICE** | [TC-040387.md](BR1_TS13/TC-040387.md) |
| TC-040388 | BR1_TS13_TC97 | Routing: Routing check: Network Management Message | **DEVICE** | [TC-040388.md](BR1_TS13/TC-040388.md) |
| TC-040389 | BR1_TS13_TC98 | Routing: Routing check: Response Code | **DEVICE** | [TC-040389.md](BR1_TS13/TC-040389.md) |
| TC-040390 | BR1_TS13_TC99 | Routing: Routing check: Standard Message Data Segment (segment 100) | **DEVICE** | [TC-040390.md](BR1_TS13/TC-040390.md) |
| TC-040391 | BR1_TS13_TC100 | Routing: Routing check: Table 016 BUYPASS Card Type Code | **DEVICE** | [TC-040391.md](BR1_TS13/TC-040391.md) |
| TC-040392 | BR1_TS13_TC101 | Routing: Routing check: rule BR-ATL105-TRANSARMOR-14-2 (p.14) | **DEVICE** | [TC-040392.md](BR1_TS13/TC-040392.md) |
| TC-040393 | BR1_TS13_TC102 | Routing: Routing check: rule BR-FISERV-EMV-GUIDE-125-1 (p.125) | **DEVICE** | [TC-040393.md](BR1_TS13/TC-040393.md) |
| TC-040394 | BR1_TS13_TC103 | Routing: Routing check: rule BR-FISERV-EMV-GUIDE-87-8 (p.87) | **DEVICE** | [TC-040394.md](BR1_TS13/TC-040394.md) |
| TC-040395 | BR1_TS13_TC104 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-040395.md](BR1_TS13/TC-040395.md) |
| TC-040396 | BR1_TS13_TC105 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-040396.md](BR1_TS13/TC-040396.md) |
| TC-040397 | BR1_TS13_TC106 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-040397.md](BR1_TS13/TC-040397.md) |
| TC-040398 | BR1_TS13_TC107 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-040398.md](BR1_TS13/TC-040398.md) |
| TC-040399 | BR1_TS13_TC108 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-040399.md](BR1_TS13/TC-040399.md) |
| TC-040400 | BR1_TS13_TC109 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-040400.md](BR1_TS13/TC-040400.md) |
| TC-040401 | BR1_TS13_TC110 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-040401.md](BR1_TS13/TC-040401.md) |
| TC-040402 | BR1_TS13_TC111 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-040402.md](BR1_TS13/TC-040402.md) |
| TC-040403 | BR1_TS13_TC112 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-040403.md](BR1_TS13/TC-040403.md) |
| TC-040404 | BR1_TS13_TC113 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040404.md](BR1_TS13/TC-040404.md) |
| TC-040405 | BR1_TS13_TC114 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-040405.md](BR1_TS13/TC-040405.md) |
| TC-040406 | BR1_TS13_TC115 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-040406.md](BR1_TS13/TC-040406.md) |
| TC-040407 | BR1_TS13_TC116 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-040407.md](BR1_TS13/TC-040407.md) |
| TC-040408 | BR1_TS13_TC117 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-040408.md](BR1_TS13/TC-040408.md) |
| TC-040409 | BR1_TS13_TC118 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-040409.md](BR1_TS13/TC-040409.md) |
| TC-040410 | BR1_TS13_TC119 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-040410.md](BR1_TS13/TC-040410.md) |
| TC-040411 | BR1_TS13_TC120 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040411.md](BR1_TS13/TC-040411.md) |
| TC-040412 | BR1_TS13_TC121 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-040412.md](BR1_TS13/TC-040412.md) |
| TC-040413 | BR1_TS13_TC122 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-040413.md](BR1_TS13/TC-040413.md) |
| TC-040414 | BR1_TS13_TC123 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-040414.md](BR1_TS13/TC-040414.md) |
| TC-040415 | BR1_TS13_TC124 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-040415.md](BR1_TS13/TC-040415.md) |
| TC-040416 | BR1_TS13_TC125 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-040416.md](BR1_TS13/TC-040416.md) |
| TC-040417 | BR1_TS13_TC126 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-040417.md](BR1_TS13/TC-040417.md) |
| TC-040418 | BR1_TS13_TC127 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-040418.md](BR1_TS13/TC-040418.md) |
| TC-040419 | BR1_TS13_TC128 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-040419.md](BR1_TS13/TC-040419.md) |
| TC-040420 | BR1_TS13_TC129 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-040420.md](BR1_TS13/TC-040420.md) |
| TC-040421 | BR1_TS13_TC130 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-040421.md](BR1_TS13/TC-040421.md) |
| TC-040422 | BR1_TS13_TC131 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-040422.md](BR1_TS13/TC-040422.md) |
| TC-040423 | BR1_TS13_TC132 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-040423.md](BR1_TS13/TC-040423.md) |
| TC-040424 | BR1_TS13_TC133 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-040424.md](BR1_TS13/TC-040424.md) |
| TC-040425 | BR1_TS13_TC134 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-040425.md](BR1_TS13/TC-040425.md) |
| TC-040426 | BR1_TS13_TC135 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-040426.md](BR1_TS13/TC-040426.md) |
| TC-040427 | BR1_TS13_TC136 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-040427.md](BR1_TS13/TC-040427.md) |
| TC-040428 | BR1_TS13_TC137 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-040428.md](BR1_TS13/TC-040428.md) |
| TC-040429 | BR1_TS13_TC138 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-040429.md](BR1_TS13/TC-040429.md) |
| TC-040430 | BR1_TS13_TC139 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-040430.md](BR1_TS13/TC-040430.md) |
| TC-040431 | BR1_TS13_TC140 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-040431.md](BR1_TS13/TC-040431.md) |
| TC-040432 | BR1_TS13_TC141 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-040432.md](BR1_TS13/TC-040432.md) |
| TC-040433 | BR1_TS13_TC142 | Device checkpoint: CAPK: lifecycle limit 6 months | **DEVICE** | [TC-040433.md](BR1_TS13/TC-040433.md) |
| TC-040434 | BR1_TS13_TC143 | Device checkpoint: CAPK: lifecycle limit 6 months | **DEVICE** | [TC-040434.md](BR1_TS13/TC-040434.md) |
| TC-040435 | BR1_TS13_TC144 | Device checkpoint: Device behaviour - 12 checks p.101-102 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040435.md](BR1_TS13/TC-040435.md) |
| TC-040436 | BR1_TS13_TC145 | Device checkpoint: Device behaviour - 12 checks p.103-108 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040436.md](BR1_TS13/TC-040436.md) |
| TC-040437 | BR1_TS13_TC146 | Device checkpoint: Device behaviour - 12 checks p.115-161 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040437.md](BR1_TS13/TC-040437.md) |
| TC-040438 | BR1_TS13_TC147 | Device checkpoint: Device behaviour - 12 checks p.161-163 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040438.md](BR1_TS13/TC-040438.md) |
| TC-040439 | BR1_TS13_TC148 | Device checkpoint: Device behaviour - 12 checks p.18-39 - BUYPASS-DCT | **DEVICE** | [TC-040439.md](BR1_TS13/TC-040439.md) |
| TC-040440 | BR1_TS13_TC149 | Device checkpoint: Device behaviour - 12 checks p.343-354 - ATL105 | **DEVICE** | [TC-040440.md](BR1_TS13/TC-040440.md) |
| TC-040441 | BR1_TS13_TC150 | Device checkpoint: Device behaviour - 12 checks p.364-394 - ATL105 | **DEVICE** | [TC-040441.md](BR1_TS13/TC-040441.md) |
| TC-040442 | BR1_TS13_TC151 | Device checkpoint: Device behaviour - 12 checks p.37-50 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040442.md](BR1_TS13/TC-040442.md) |
| TC-040443 | BR1_TS13_TC152 | Device checkpoint: Device behaviour - 12 checks p.400-416 - ATL105 | **DEVICE** | [TC-040443.md](BR1_TS13/TC-040443.md) |
| TC-040444 | BR1_TS13_TC153 | Device checkpoint: Device behaviour - 12 checks p.416-469 - ATL105 | **DEVICE** | [TC-040444.md](BR1_TS13/TC-040444.md) |
| TC-040445 | BR1_TS13_TC154 | Device checkpoint: Device behaviour - 12 checks p.470-476 - ATL105 | **DEVICE** | [TC-040445.md](BR1_TS13/TC-040445.md) |
| TC-040446 | BR1_TS13_TC155 | Device checkpoint: Device behaviour - 12 checks p.50-75 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040446.md](BR1_TS13/TC-040446.md) |
| TC-040447 | BR1_TS13_TC156 | Device checkpoint: Device behaviour - 12 checks p.75-98 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040447.md](BR1_TS13/TC-040447.md) |
| TC-040448 | BR1_TS13_TC157 | Device checkpoint: Device behaviour - 12 checks p.81-91 - ATL105 | **DEVICE** | [TC-040448.md](BR1_TS13/TC-040448.md) |
| TC-040449 | BR1_TS13_TC158 | Device checkpoint: Device behaviour - 12 checks p.91-93 - ATL105 | **DEVICE** | [TC-040449.md](BR1_TS13/TC-040449.md) |
| TC-040450 | BR1_TS13_TC159 | Device checkpoint: Device behaviour - 12 checks p.98-100 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040450.md](BR1_TS13/TC-040450.md) |
| TC-040451 | BR1_TS13_TC160 | Device checkpoint: Device behaviour - 2 checks p.138 - ATL105 | **DEVICE** | [TC-040451.md](BR1_TS13/TC-040451.md) |
| TC-040452 | BR1_TS13_TC161 | Device checkpoint: Device behaviour - 2 checks p.160 - ATL105 | **DEVICE** | [TC-040452.md](BR1_TS13/TC-040452.md) |
| TC-040453 | BR1_TS13_TC162 | Device checkpoint: Device behaviour - 2 checks p.171-172 - ATL105 | **DEVICE** | [TC-040453.md](BR1_TS13/TC-040453.md) |
| TC-040454 | BR1_TS13_TC163 | Device checkpoint: Device behaviour - 2 checks p.189-200 - ATL105 | **DEVICE** | [TC-040454.md](BR1_TS13/TC-040454.md) |
| TC-040455 | BR1_TS13_TC164 | Device checkpoint: Device behaviour - 2 checks p.547-548 - ATL105 | **DEVICE** | [TC-040455.md](BR1_TS13/TC-040455.md) |
| TC-040456 | BR1_TS13_TC165 | Device checkpoint: Device behaviour - 3 checks p.27-32 - ATL105-TRANSARMOR | **DEVICE** | [TC-040456.md](BR1_TS13/TC-040456.md) |
| TC-040457 | BR1_TS13_TC166 | Device checkpoint: Device behaviour - 3 checks p.34 - ATL105 | **DEVICE** | [TC-040457.md](BR1_TS13/TC-040457.md) |
| TC-040458 | BR1_TS13_TC167 | Device checkpoint: Device behaviour - 3 checks p.66 - ATL105 | **DEVICE** | [TC-040458.md](BR1_TS13/TC-040458.md) |
| TC-040459 | BR1_TS13_TC168 | Device checkpoint: Device behaviour - 3 checks p.8-11 - BUYPASS-DCT | **DEVICE** | [TC-040459.md](BR1_TS13/TC-040459.md) |
| TC-040460 | BR1_TS13_TC169 | Device checkpoint: Device behaviour - 4 checks p.266 - ATL105 | **DEVICE** | [TC-040460.md](BR1_TS13/TC-040460.md) |
| TC-040461 | BR1_TS13_TC170 | Device checkpoint: Device behaviour - 4 checks p.476-484 - ATL105 | **DEVICE** | [TC-040461.md](BR1_TS13/TC-040461.md) |
| TC-040462 | BR1_TS13_TC171 | Device checkpoint: Device behaviour - 5 checks p.252 - ATL105 | **DEVICE** | [TC-040462.md](BR1_TS13/TC-040462.md) |
| TC-040463 | BR1_TS13_TC172 | Device checkpoint: Device behaviour - 5 checks p.71-76 - ATL105 | **DEVICE** | [TC-040463.md](BR1_TS13/TC-040463.md) |
| TC-040464 | BR1_TS13_TC173 | Device checkpoint: Device behaviour - 5 checks p.93-94 - ATL105 | **DEVICE** | [TC-040464.md](BR1_TS13/TC-040464.md) |
| TC-040465 | BR1_TS13_TC174 | Device checkpoint: Device behaviour - 6 checks p.518-522 - ATL105 | **DEVICE** | [TC-040465.md](BR1_TS13/TC-040465.md) |
| TC-040466 | BR1_TS13_TC175 | Device checkpoint: Device behaviour - 7 checks p.254-262 - ATL105 | **DEVICE** | [TC-040466.md](BR1_TS13/TC-040466.md) |
| TC-040467 | BR1_TS13_TC176 | Device checkpoint: Device behaviour - 7 checks p.57-77 - ATL105-TRANSARMOR | **DEVICE** | [TC-040467.md](BR1_TS13/TC-040467.md) |
| TC-040468 | BR1_TS13_TC177 | Device checkpoint: Device behaviour - 9 checks p.163-175 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040468.md](BR1_TS13/TC-040468.md) |
| TC-040469 | BR1_TS13_TC178 | Device checkpoint: Device behaviour - 9 checks p.39-43 - BUYPASS-DCT | **DEVICE** | [TC-040469.md](BR1_TS13/TC-040469.md) |
| TC-040470 | BR1_TS13_TC179 | Device checkpoint: Device behaviour - CA Public Key File Block - ATL105 | **DEVICE** | [TC-040470.md](BR1_TS13/TC-040470.md) |
| TC-040471 | BR1_TS13_TC180 | Device checkpoint: Device behaviour - Card Table Master - BUYPASS-DCT | **DEVICE** | [TC-040471.md](BR1_TS13/TC-040471.md) |
| TC-040472 | BR1_TS13_TC181 | Device checkpoint: Device behaviour - Card Type - ATL105 | **DEVICE** | [TC-040472.md](BR1_TS13/TC-040472.md) |
| TC-040473 | BR1_TS13_TC182 | Device checkpoint: Device behaviour - ENT-TABLE-K-020-SUB-HOST_PROMPTS_INFORMATION_CONT - ATL105 | **DEVICE** | [TC-040473.md](BR1_TS13/TC-040473.md) |
| TC-040474 | BR1_TS13_TC183 | Device checkpoint: Device behaviour - Encrypted PIN Block Data - ATL105 | **DEVICE** | [TC-040474.md](BR1_TS13/TC-040474.md) |
| TC-040475 | BR1_TS13_TC184 | Device checkpoint: Device behaviour - Enhanced Fleet Request (segment 145) - ATL105 | **DEVICE** | [TC-040475.md](BR1_TS13/TC-040475.md) |
| TC-040476 | BR1_TS13_TC185 | Device checkpoint: Device behaviour - Key ID - ATL105-TRANSARMOR | **DEVICE** | [TC-040476.md](BR1_TS13/TC-040476.md) |
| TC-040477 | BR1_TS13_TC186 | Device checkpoint: Device behaviour - Terminal Display /Printer Message - ATL105 | **DEVICE** | [TC-040477.md](BR1_TS13/TC-040477.md) |
| TC-040478 | BR1_TS13_TC187 | Device checkpoint: Device behaviour - Terminal Display /Printer Message - ATL105-TRANSARMOR | **DEVICE** | [TC-040478.md](BR1_TS13/TC-040478.md) |
| TC-040479 | BR1_TS13_TC188 | Device checkpoint: Device behaviour - Terminal Display Communications Message - ATL105 | **DEVICE** | [TC-040479.md](BR1_TS13/TC-040479.md) |
| TC-040480 | BR1_TS13_TC189 | Device checkpoint: Device behaviour - Terminal Display Communications Message - ATL105 | **DEVICE** | [TC-040480.md](BR1_TS13/TC-040480.md) |
| TC-040481 | BR1_TS13_TC190 | Device checkpoint: Device behaviour - Token PAN Suffix - ATL105 | **DEVICE** | [TC-040481.md](BR1_TS13/TC-040481.md) |
| TC-040482 | BR1_TS13_TC191 | Device checkpoint: Device behaviour - Token PAN Suffix - ATL105-TRANSARMOR | **DEVICE** | [TC-040482.md](BR1_TS13/TC-040482.md) |
| TC-040483 | BR1_TS13_TC192 | Device checkpoint: Device behaviour - Vehicle ID# - FISERV-EMV-GUIDE | **DEVICE** | [TC-040483.md](BR1_TS13/TC-040483.md) |
| TC-040484 | BR1_TS13_TC193 | Device checkpoint: Device behaviour - rule BR-BUYPASS-MTAP-44-2 (p.44) - BUYPASS-MTAP | **DEVICE** | [TC-040484.md](BR1_TS13/TC-040484.md) |
| TC-040485 | BR1_TS13_TC194 | Device checkpoint: Device checkpoint check: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **DEVICE** | [TC-040485.md](BR1_TS13/TC-040485.md) |
| TC-040486 | BR1_TS13_TC195 | Device checkpoint: Device checkpoint check: TransArmor Load Data Segment (segment 116) | **DEVICE** | [TC-040486.md](BR1_TS13/TC-040486.md) |
| TC-040487 | BR1_TS13_TC196 | Device checkpoint: Terminal / device certification - 12 checks p.24-33 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040487.md](BR1_TS13/TC-040487.md) |
| TC-040488 | BR1_TS13_TC197 | Device checkpoint: Terminal / device certification - 12 checks p.33-36 - FISERV-EMV-GUIDE | **DEVICE** | [TC-040488.md](BR1_TS13/TC-040488.md) |
| TC-040489 | BR1_TS13_TC198 | Device checkpoint: Terminal / device certification - rule BR-FISERV-EMV-GUIDE-36-7 (p.36) - FISERV-EMV-GUIDE | **DEVICE** | [TC-040489.md](BR1_TS13/TC-040489.md) |
| TC-040490 | BR1_TS13_TC199 | Device checkpoint: Terminal / device certification - rule BR-FISERV-EMV-GUIDE-4-3 (p.4) - FISERV-EMV-GUIDE | **DEVICE** | [TC-040490.md](BR1_TS13/TC-040490.md) |
| TC-040491 | BR1_TS13_TC200 | Device checkpoint: Theme safety net - CA Public Key File Data Segment (segment 132) (p.33) | **DEVICE** | [TC-040491.md](BR1_TS13/TC-040491.md) |
| TC-040492 | BR1_TS13_TC201 | Device checkpoint: Theme safety net - Merchant Trace ID (p.503) | **DEVICE** | [TC-040492.md](BR1_TS13/TC-040492.md) |
| TC-040493 | BR1_TS13_TC202 | Device checkpoint: Theme safety net - Table 060 Real Time Account Updater Request Data (p.35) | **DEVICE** | [TC-040493.md](BR1_TS13/TC-040493.md) |
| TC-040494 | BR1_TS13_TC203 | Device checkpoint: Theme safety net - Token Requestor ID (p.16) | **DEVICE** | [TC-040494.md](BR1_TS13/TC-040494.md) |
| TC-040495 | BR1_TS13_TC204 | Single rule broken: Mail/Phone Reversal sent on a BUYPASS ATL105 MasterCard Credit card (transaction not supported) -> declined | **FAIL** | [TC-040495.md](BR1_TS13/TC-040495.md) |
| TC-040496 | BR1_TS13_TC205 | Single rule broken: Account Verification sent on a BUYPASS ATL105 MasterCard Credit card (transaction not supported) -> declined | **BLOCKED** | [TC-040496.md](BR1_TS13/TC-040496.md) |

<a id="br1_ts14"></a>
## BR1_TS14 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-040497 | BR1_TS14_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040497.md](BR1_TS14/TC-040497.md) |
| TC-040498 | BR1_TS14_TC2 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040498.md](BR1_TS14/TC-040498.md) |
| TC-040499 | BR1_TS14_TC3 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040499.md](BR1_TS14/TC-040499.md) |
| TC-040500 | BR1_TS14_TC4 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040500.md](BR1_TS14/TC-040500.md) |
| TC-040501 | BR1_TS14_TC5 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040501.md](BR1_TS14/TC-040501.md) |
| TC-040502 | BR1_TS14_TC6 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040502.md](BR1_TS14/TC-040502.md) |
| TC-040503 | BR1_TS14_TC7 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040503.md](BR1_TS14/TC-040503.md) |
| TC-040504 | BR1_TS14_TC8 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040504.md](BR1_TS14/TC-040504.md) |
| TC-040505 | BR1_TS14_TC9 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040505.md](BR1_TS14/TC-040505.md) |
| TC-040506 | BR1_TS14_TC10 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040506.md](BR1_TS14/TC-040506.md) |
| TC-040507 | BR1_TS14_TC11 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040507.md](BR1_TS14/TC-040507.md) |
| TC-040508 | BR1_TS14_TC12 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040508.md](BR1_TS14/TC-040508.md) |
| TC-040509 | BR1_TS14_TC13 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040509.md](BR1_TS14/TC-040509.md) |
| TC-040510 | BR1_TS14_TC14 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040510.md](BR1_TS14/TC-040510.md) |
| TC-040511 | BR1_TS14_TC15 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040511.md](BR1_TS14/TC-040511.md) |
| TC-040512 | BR1_TS14_TC16 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040512.md](BR1_TS14/TC-040512.md) |
| TC-040513 | BR1_TS14_TC17 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040513.md](BR1_TS14/TC-040513.md) |
| TC-040514 | BR1_TS14_TC18 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040514.md](BR1_TS14/TC-040514.md) |
| TC-040515 | BR1_TS14_TC19 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040515.md](BR1_TS14/TC-040515.md) |
| TC-040516 | BR1_TS14_TC20 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040516.md](BR1_TS14/TC-040516.md) |
| TC-040517 | BR1_TS14_TC21 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040517.md](BR1_TS14/TC-040517.md) |
| TC-040518 | BR1_TS14_TC22 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040518.md](BR1_TS14/TC-040518.md) |
| TC-040519 | BR1_TS14_TC23 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040519.md](BR1_TS14/TC-040519.md) |
| TC-040520 | BR1_TS14_TC24 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040520.md](BR1_TS14/TC-040520.md) |
| TC-040521 | BR1_TS14_TC25 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040521.md](BR1_TS14/TC-040521.md) |
| TC-040522 | BR1_TS14_TC26 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040522.md](BR1_TS14/TC-040522.md) |
| TC-040523 | BR1_TS14_TC27 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040523.md](BR1_TS14/TC-040523.md) |
| TC-040524 | BR1_TS14_TC28 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040524.md](BR1_TS14/TC-040524.md) |
| TC-040525 | BR1_TS14_TC29 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040525.md](BR1_TS14/TC-040525.md) |
| TC-040526 | BR1_TS14_TC30 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040526.md](BR1_TS14/TC-040526.md) |
| TC-040527 | BR1_TS14_TC31 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040527.md](BR1_TS14/TC-040527.md) |
| TC-040528 | BR1_TS14_TC32 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040528.md](BR1_TS14/TC-040528.md) |
| TC-040529 | BR1_TS14_TC33 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040529.md](BR1_TS14/TC-040529.md) |
| TC-040530 | BR1_TS14_TC34 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040530.md](BR1_TS14/TC-040530.md) |
| TC-040531 | BR1_TS14_TC35 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040531.md](BR1_TS14/TC-040531.md) |
| TC-040532 | BR1_TS14_TC36 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040532.md](BR1_TS14/TC-040532.md) |
| TC-040533 | BR1_TS14_TC37 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040533.md](BR1_TS14/TC-040533.md) |
| TC-040534 | BR1_TS14_TC38 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040534.md](BR1_TS14/TC-040534.md) |
| TC-040535 | BR1_TS14_TC39 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040535.md](BR1_TS14/TC-040535.md) |
| TC-040536 | BR1_TS14_TC40 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040536.md](BR1_TS14/TC-040536.md) |
| TC-040537 | BR1_TS14_TC41 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040537.md](BR1_TS14/TC-040537.md) |
| TC-040538 | BR1_TS14_TC42 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-040538.md](BR1_TS14/TC-040538.md) |
| TC-040539 | BR1_TS14_TC43 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-040539.md](BR1_TS14/TC-040539.md) |
| TC-040540 | BR1_TS14_TC44 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-040540.md](BR1_TS14/TC-040540.md) |
| TC-040541 | BR1_TS14_TC45 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-040541.md](BR1_TS14/TC-040541.md) |
| TC-040542 | BR1_TS14_TC46 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-040542.md](BR1_TS14/TC-040542.md) |
| TC-040543 | BR1_TS14_TC47 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-040543.md](BR1_TS14/TC-040543.md) |
| TC-040544 | BR1_TS14_TC48 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-040544.md](BR1_TS14/TC-040544.md) |
| TC-040545 | BR1_TS14_TC49 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-040545.md](BR1_TS14/TC-040545.md) |
| TC-040546 | BR1_TS14_TC50 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-040546.md](BR1_TS14/TC-040546.md) |
| TC-040547 | BR1_TS14_TC51 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-040547.md](BR1_TS14/TC-040547.md) |
| TC-040548 | BR1_TS14_TC52 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-040548.md](BR1_TS14/TC-040548.md) |
| TC-040549 | BR1_TS14_TC53 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-040549.md](BR1_TS14/TC-040549.md) |
| TC-040550 | BR1_TS14_TC54 | Decision table: Authorization Only with cof = initial | **BLOCKED** | [TC-040550.md](BR1_TS14/TC-040550.md) |
| TC-040551 | BR1_TS14_TC55 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-040551.md](BR1_TS14/TC-040551.md) |
| TC-040552 | BR1_TS14_TC56 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-040552.md](BR1_TS14/TC-040552.md) |
| TC-040553 | BR1_TS14_TC57 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-040553.md](BR1_TS14/TC-040553.md) |
| TC-040554 | BR1_TS14_TC58 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-040554.md](BR1_TS14/TC-040554.md) |
| TC-040555 | BR1_TS14_TC59 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-040555.md](BR1_TS14/TC-040555.md) |
| TC-040556 | BR1_TS14_TC60 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-040556.md](BR1_TS14/TC-040556.md) |
| TC-040557 | BR1_TS14_TC61 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-040557.md](BR1_TS14/TC-040557.md) |
| TC-040558 | BR1_TS14_TC62 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-040558.md](BR1_TS14/TC-040558.md) |
| TC-040559 | BR1_TS14_TC63 | Decision table: Authorization Only with optional segment 123 present | **BLOCKED** | [TC-040559.md](BR1_TS14/TC-040559.md) |
| TC-040560 | BR1_TS14_TC64 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-040560.md](BR1_TS14/TC-040560.md) |
| TC-040561 | BR1_TS14_TC65 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-040561.md](BR1_TS14/TC-040561.md) |
| TC-040562 | BR1_TS14_TC66 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-040562.md](BR1_TS14/TC-040562.md) |
| TC-040563 | BR1_TS14_TC67 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-040563.md](BR1_TS14/TC-040563.md) |
| TC-040564 | BR1_TS14_TC68 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-040564.md](BR1_TS14/TC-040564.md) |
| TC-040565 | BR1_TS14_TC69 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-040565.md](BR1_TS14/TC-040565.md) |
| TC-040566 | BR1_TS14_TC70 | Time-based: Authorization Only -> Cancellation after 24 hour(s) (at the stated limit) | **BLOCKED** | [TC-040566.md](BR1_TS14/TC-040566.md) |
| TC-040567 | BR1_TS14_TC71 | Time-based: Authorization Only -> Cancellation after 25 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-040567.md](BR1_TS14/TC-040567.md) |
| TC-040568 | BR1_TS14_TC72 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-040568.md](BR1_TS14/TC-040568.md) |
| TC-040569 | BR1_TS14_TC73 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-040569.md](BR1_TS14/TC-040569.md) |
| TC-040570 | BR1_TS14_TC74 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-040570.md](BR1_TS14/TC-040570.md) |
| TC-040571 | BR1_TS14_TC75 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-040571.md](BR1_TS14/TC-040571.md) |
| TC-040572 | BR1_TS14_TC76 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-040572.md](BR1_TS14/TC-040572.md) |
| TC-040573 | BR1_TS14_TC77 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-040573.md](BR1_TS14/TC-040573.md) |
| TC-040574 | BR1_TS14_TC78 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-040574.md](BR1_TS14/TC-040574.md) |
| TC-040575 | BR1_TS14_TC79 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-040575.md](BR1_TS14/TC-040575.md) |
| TC-040576 | BR1_TS14_TC80 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-040576.md](BR1_TS14/TC-040576.md) |
| TC-040577 | BR1_TS14_TC81 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-040577.md](BR1_TS14/TC-040577.md) |
| TC-040578 | BR1_TS14_TC82 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-040578.md](BR1_TS14/TC-040578.md) |
| TC-040579 | BR1_TS14_TC83 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040579.md](BR1_TS14/TC-040579.md) |
| TC-040580 | BR1_TS14_TC84 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-040580.md](BR1_TS14/TC-040580.md) |
| TC-040581 | BR1_TS14_TC85 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-040581.md](BR1_TS14/TC-040581.md) |
| TC-040582 | BR1_TS14_TC86 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-040582.md](BR1_TS14/TC-040582.md) |
| TC-040583 | BR1_TS14_TC87 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-040583.md](BR1_TS14/TC-040583.md) |
| TC-040584 | BR1_TS14_TC88 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-040584.md](BR1_TS14/TC-040584.md) |
| TC-040585 | BR1_TS14_TC89 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-040585.md](BR1_TS14/TC-040585.md) |
| TC-040586 | BR1_TS14_TC90 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040586.md](BR1_TS14/TC-040586.md) |
| TC-040587 | BR1_TS14_TC91 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-040587.md](BR1_TS14/TC-040587.md) |
| TC-040588 | BR1_TS14_TC92 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-040588.md](BR1_TS14/TC-040588.md) |
| TC-040589 | BR1_TS14_TC93 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-040589.md](BR1_TS14/TC-040589.md) |
| TC-040590 | BR1_TS14_TC94 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-040590.md](BR1_TS14/TC-040590.md) |
| TC-040591 | BR1_TS14_TC95 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-040591.md](BR1_TS14/TC-040591.md) |
| TC-040592 | BR1_TS14_TC96 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-040592.md](BR1_TS14/TC-040592.md) |
| TC-040593 | BR1_TS14_TC97 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-040593.md](BR1_TS14/TC-040593.md) |
| TC-040594 | BR1_TS14_TC98 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-040594.md](BR1_TS14/TC-040594.md) |
| TC-040595 | BR1_TS14_TC99 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-040595.md](BR1_TS14/TC-040595.md) |
| TC-040596 | BR1_TS14_TC100 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-040596.md](BR1_TS14/TC-040596.md) |
| TC-040597 | BR1_TS14_TC101 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-040597.md](BR1_TS14/TC-040597.md) |
| TC-040598 | BR1_TS14_TC102 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-040598.md](BR1_TS14/TC-040598.md) |
| TC-040599 | BR1_TS14_TC103 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-040599.md](BR1_TS14/TC-040599.md) |
| TC-040600 | BR1_TS14_TC104 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-040600.md](BR1_TS14/TC-040600.md) |
| TC-040601 | BR1_TS14_TC105 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-040601.md](BR1_TS14/TC-040601.md) |
| TC-040602 | BR1_TS14_TC106 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-040602.md](BR1_TS14/TC-040602.md) |
| TC-040603 | BR1_TS14_TC107 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-040603.md](BR1_TS14/TC-040603.md) |
| TC-040604 | BR1_TS14_TC108 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-040604.md](BR1_TS14/TC-040604.md) |
| TC-040605 | BR1_TS14_TC109 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-040605.md](BR1_TS14/TC-040605.md) |
| TC-040606 | BR1_TS14_TC110 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-040606.md](BR1_TS14/TC-040606.md) |
| TC-040607 | BR1_TS14_TC111 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-040607.md](BR1_TS14/TC-040607.md) |

<a id="br1_ts15"></a>
## BR1_TS15 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038607 | BR1_TS15_TC1 | Loyalty Card Data Segment (segment 108) omitted where p.15 requires it | **FAIL** | [TC-038607.md](BR1_TS15/TC-038607.md) |
| TC-040608 | BR1_TS15_TC2 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040608.md](BR1_TS15/TC-040608.md) |
| TC-040609 | BR1_TS15_TC3 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040609.md](BR1_TS15/TC-040609.md) |
| TC-040610 | BR1_TS15_TC4 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040610.md](BR1_TS15/TC-040610.md) |
| TC-040611 | BR1_TS15_TC5 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040611.md](BR1_TS15/TC-040611.md) |
| TC-040612 | BR1_TS15_TC6 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040612.md](BR1_TS15/TC-040612.md) |
| TC-040613 | BR1_TS15_TC7 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040613.md](BR1_TS15/TC-040613.md) |
| TC-040614 | BR1_TS15_TC8 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040614.md](BR1_TS15/TC-040614.md) |
| TC-040615 | BR1_TS15_TC9 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040615.md](BR1_TS15/TC-040615.md) |
| TC-040616 | BR1_TS15_TC10 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040616.md](BR1_TS15/TC-040616.md) |
| TC-040617 | BR1_TS15_TC11 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040617.md](BR1_TS15/TC-040617.md) |
| TC-040618 | BR1_TS15_TC12 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040618.md](BR1_TS15/TC-040618.md) |
| TC-040619 | BR1_TS15_TC13 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040619.md](BR1_TS15/TC-040619.md) |
| TC-040620 | BR1_TS15_TC14 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040620.md](BR1_TS15/TC-040620.md) |
| TC-040621 | BR1_TS15_TC15 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040621.md](BR1_TS15/TC-040621.md) |
| TC-040622 | BR1_TS15_TC16 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040622.md](BR1_TS15/TC-040622.md) |
| TC-040623 | BR1_TS15_TC17 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040623.md](BR1_TS15/TC-040623.md) |
| TC-040624 | BR1_TS15_TC18 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040624.md](BR1_TS15/TC-040624.md) |
| TC-040625 | BR1_TS15_TC19 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040625.md](BR1_TS15/TC-040625.md) |
| TC-040626 | BR1_TS15_TC20 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040626.md](BR1_TS15/TC-040626.md) |
| TC-040627 | BR1_TS15_TC21 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040627.md](BR1_TS15/TC-040627.md) |
| TC-040628 | BR1_TS15_TC22 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040628.md](BR1_TS15/TC-040628.md) |
| TC-040629 | BR1_TS15_TC23 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040629.md](BR1_TS15/TC-040629.md) |
| TC-040630 | BR1_TS15_TC24 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040630.md](BR1_TS15/TC-040630.md) |
| TC-040631 | BR1_TS15_TC25 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040631.md](BR1_TS15/TC-040631.md) |
| TC-040632 | BR1_TS15_TC26 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040632.md](BR1_TS15/TC-040632.md) |
| TC-040633 | BR1_TS15_TC27 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040633.md](BR1_TS15/TC-040633.md) |
| TC-040634 | BR1_TS15_TC28 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040634.md](BR1_TS15/TC-040634.md) |
| TC-040635 | BR1_TS15_TC29 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040635.md](BR1_TS15/TC-040635.md) |
| TC-040636 | BR1_TS15_TC30 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040636.md](BR1_TS15/TC-040636.md) |
| TC-040637 | BR1_TS15_TC31 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040637.md](BR1_TS15/TC-040637.md) |
| TC-040638 | BR1_TS15_TC32 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040638.md](BR1_TS15/TC-040638.md) |
| TC-040639 | BR1_TS15_TC33 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040639.md](BR1_TS15/TC-040639.md) |
| TC-040640 | BR1_TS15_TC34 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040640.md](BR1_TS15/TC-040640.md) |
| TC-040641 | BR1_TS15_TC35 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040641.md](BR1_TS15/TC-040641.md) |
| TC-040642 | BR1_TS15_TC36 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040642.md](BR1_TS15/TC-040642.md) |
| TC-040643 | BR1_TS15_TC37 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040643.md](BR1_TS15/TC-040643.md) |
| TC-040644 | BR1_TS15_TC38 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040644.md](BR1_TS15/TC-040644.md) |
| TC-040645 | BR1_TS15_TC39 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040645.md](BR1_TS15/TC-040645.md) |
| TC-040646 | BR1_TS15_TC40 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040646.md](BR1_TS15/TC-040646.md) |
| TC-040647 | BR1_TS15_TC41 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040647.md](BR1_TS15/TC-040647.md) |
| TC-040648 | BR1_TS15_TC42 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040648.md](BR1_TS15/TC-040648.md) |
| TC-040649 | BR1_TS15_TC43 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-040649.md](BR1_TS15/TC-040649.md) |
| TC-040650 | BR1_TS15_TC44 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-040650.md](BR1_TS15/TC-040650.md) |
| TC-040651 | BR1_TS15_TC45 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-040651.md](BR1_TS15/TC-040651.md) |
| TC-040652 | BR1_TS15_TC46 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-040652.md](BR1_TS15/TC-040652.md) |
| TC-040653 | BR1_TS15_TC47 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-040653.md](BR1_TS15/TC-040653.md) |
| TC-040654 | BR1_TS15_TC48 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-040654.md](BR1_TS15/TC-040654.md) |
| TC-040655 | BR1_TS15_TC49 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-040655.md](BR1_TS15/TC-040655.md) |
| TC-040656 | BR1_TS15_TC50 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-040656.md](BR1_TS15/TC-040656.md) |
| TC-040657 | BR1_TS15_TC51 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-040657.md](BR1_TS15/TC-040657.md) |
| TC-040658 | BR1_TS15_TC52 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-040658.md](BR1_TS15/TC-040658.md) |
| TC-040659 | BR1_TS15_TC53 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-040659.md](BR1_TS15/TC-040659.md) |
| TC-040660 | BR1_TS15_TC54 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-040660.md](BR1_TS15/TC-040660.md) |
| TC-040661 | BR1_TS15_TC55 | Decision table: Authorization Only with cof = initial | **BLOCKED** | [TC-040661.md](BR1_TS15/TC-040661.md) |
| TC-040662 | BR1_TS15_TC56 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-040662.md](BR1_TS15/TC-040662.md) |
| TC-040663 | BR1_TS15_TC57 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-040663.md](BR1_TS15/TC-040663.md) |
| TC-040664 | BR1_TS15_TC58 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-040664.md](BR1_TS15/TC-040664.md) |
| TC-040665 | BR1_TS15_TC59 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-040665.md](BR1_TS15/TC-040665.md) |
| TC-040666 | BR1_TS15_TC60 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-040666.md](BR1_TS15/TC-040666.md) |
| TC-040667 | BR1_TS15_TC61 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-040667.md](BR1_TS15/TC-040667.md) |
| TC-040668 | BR1_TS15_TC62 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-040668.md](BR1_TS15/TC-040668.md) |
| TC-040669 | BR1_TS15_TC63 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-040669.md](BR1_TS15/TC-040669.md) |
| TC-040670 | BR1_TS15_TC64 | Decision table: Authorization Only with optional segment 123 present | **BLOCKED** | [TC-040670.md](BR1_TS15/TC-040670.md) |
| TC-040671 | BR1_TS15_TC65 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-040671.md](BR1_TS15/TC-040671.md) |
| TC-040672 | BR1_TS15_TC66 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-040672.md](BR1_TS15/TC-040672.md) |
| TC-040673 | BR1_TS15_TC67 | Decision table: Authorization Only with segment 108 sent (the rule's condition holds) | **FAIL** | [TC-040673.md](BR1_TS15/TC-040673.md) |
| TC-040674 | BR1_TS15_TC68 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-040674.md](BR1_TS15/TC-040674.md) |
| TC-040675 | BR1_TS15_TC69 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-040675.md](BR1_TS15/TC-040675.md) |
| TC-040676 | BR1_TS15_TC70 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-040676.md](BR1_TS15/TC-040676.md) |
| TC-040677 | BR1_TS15_TC71 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-040677.md](BR1_TS15/TC-040677.md) |
| TC-040678 | BR1_TS15_TC72 | Time-based: Authorization Only -> Cancellation after 24 hour(s) (at the stated limit) | **BLOCKED** | [TC-040678.md](BR1_TS15/TC-040678.md) |
| TC-040679 | BR1_TS15_TC73 | Time-based: Authorization Only -> Cancellation after 25 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-040679.md](BR1_TS15/TC-040679.md) |
| TC-040680 | BR1_TS15_TC74 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-040680.md](BR1_TS15/TC-040680.md) |
| TC-040681 | BR1_TS15_TC75 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-040681.md](BR1_TS15/TC-040681.md) |
| TC-040682 | BR1_TS15_TC76 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-040682.md](BR1_TS15/TC-040682.md) |
| TC-040683 | BR1_TS15_TC77 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-040683.md](BR1_TS15/TC-040683.md) |
| TC-040684 | BR1_TS15_TC78 | Response / oracle check: Authorization Only -> Account Type returned | **BLOCKED** | [TC-040684.md](BR1_TS15/TC-040684.md) |
| TC-040685 | BR1_TS15_TC79 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-040685.md](BR1_TS15/TC-040685.md) |
| TC-040686 | BR1_TS15_TC80 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-040686.md](BR1_TS15/TC-040686.md) |
| TC-040687 | BR1_TS15_TC81 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-040687.md](BR1_TS15/TC-040687.md) |
| TC-040688 | BR1_TS15_TC82 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-040688.md](BR1_TS15/TC-040688.md) |
| TC-040689 | BR1_TS15_TC83 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-040689.md](BR1_TS15/TC-040689.md) |
| TC-040690 | BR1_TS15_TC84 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-040690.md](BR1_TS15/TC-040690.md) |
| TC-040691 | BR1_TS15_TC85 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-040691.md](BR1_TS15/TC-040691.md) |
| TC-040692 | BR1_TS15_TC86 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040692.md](BR1_TS15/TC-040692.md) |
| TC-040693 | BR1_TS15_TC87 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-040693.md](BR1_TS15/TC-040693.md) |
| TC-040694 | BR1_TS15_TC88 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-040694.md](BR1_TS15/TC-040694.md) |
| TC-040695 | BR1_TS15_TC89 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-040695.md](BR1_TS15/TC-040695.md) |
| TC-040696 | BR1_TS15_TC90 | Response / oracle check: Authorization Only -> Merchant Data Segment returned and checked (p.4) | **BLOCKED** | [TC-040696.md](BR1_TS15/TC-040696.md) |
| TC-040697 | BR1_TS15_TC91 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-040697.md](BR1_TS15/TC-040697.md) |
| TC-040698 | BR1_TS15_TC92 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-040698.md](BR1_TS15/TC-040698.md) |
| TC-040699 | BR1_TS15_TC93 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-040699.md](BR1_TS15/TC-040699.md) |
| TC-040700 | BR1_TS15_TC94 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040700.md](BR1_TS15/TC-040700.md) |
| TC-040701 | BR1_TS15_TC95 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-040701.md](BR1_TS15/TC-040701.md) |
| TC-040702 | BR1_TS15_TC96 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-040702.md](BR1_TS15/TC-040702.md) |
| TC-040703 | BR1_TS15_TC97 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-040703.md](BR1_TS15/TC-040703.md) |
| TC-040704 | BR1_TS15_TC98 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-040704.md](BR1_TS15/TC-040704.md) |
| TC-040705 | BR1_TS15_TC99 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-040705.md](BR1_TS15/TC-040705.md) |
| TC-040706 | BR1_TS15_TC100 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-040706.md](BR1_TS15/TC-040706.md) |
| TC-040707 | BR1_TS15_TC101 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-040707.md](BR1_TS15/TC-040707.md) |
| TC-040708 | BR1_TS15_TC102 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-040708.md](BR1_TS15/TC-040708.md) |
| TC-040709 | BR1_TS15_TC103 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-040709.md](BR1_TS15/TC-040709.md) |
| TC-040710 | BR1_TS15_TC104 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-040710.md](BR1_TS15/TC-040710.md) |
| TC-040711 | BR1_TS15_TC105 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-040711.md](BR1_TS15/TC-040711.md) |
| TC-040712 | BR1_TS15_TC106 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-040712.md](BR1_TS15/TC-040712.md) |
| TC-040713 | BR1_TS15_TC107 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-040713.md](BR1_TS15/TC-040713.md) |
| TC-040714 | BR1_TS15_TC108 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-040714.md](BR1_TS15/TC-040714.md) |
| TC-040715 | BR1_TS15_TC109 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-040715.md](BR1_TS15/TC-040715.md) |
| TC-040716 | BR1_TS15_TC110 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-040716.md](BR1_TS15/TC-040716.md) |
| TC-040717 | BR1_TS15_TC111 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-040717.md](BR1_TS15/TC-040717.md) |
| TC-040718 | BR1_TS15_TC112 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-040718.md](BR1_TS15/TC-040718.md) |
| TC-040719 | BR1_TS15_TC113 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-040719.md](BR1_TS15/TC-040719.md) |
| TC-040720 | BR1_TS15_TC114 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-040720.md](BR1_TS15/TC-040720.md) |
| TC-040721 | BR1_TS15_TC115 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-040721.md](BR1_TS15/TC-040721.md) |

<a id="br1_ts16"></a>
## BR1_TS16 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-040722 | BR1_TS16_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040722.md](BR1_TS16/TC-040722.md) |
| TC-040723 | BR1_TS16_TC2 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040723.md](BR1_TS16/TC-040723.md) |
| TC-040724 | BR1_TS16_TC3 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040724.md](BR1_TS16/TC-040724.md) |
| TC-040725 | BR1_TS16_TC4 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040725.md](BR1_TS16/TC-040725.md) |
| TC-040726 | BR1_TS16_TC5 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040726.md](BR1_TS16/TC-040726.md) |
| TC-040727 | BR1_TS16_TC6 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040727.md](BR1_TS16/TC-040727.md) |
| TC-040728 | BR1_TS16_TC7 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040728.md](BR1_TS16/TC-040728.md) |
| TC-040729 | BR1_TS16_TC8 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040729.md](BR1_TS16/TC-040729.md) |
| TC-040730 | BR1_TS16_TC9 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040730.md](BR1_TS16/TC-040730.md) |
| TC-040731 | BR1_TS16_TC10 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040731.md](BR1_TS16/TC-040731.md) |
| TC-040732 | BR1_TS16_TC11 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040732.md](BR1_TS16/TC-040732.md) |
| TC-040733 | BR1_TS16_TC12 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040733.md](BR1_TS16/TC-040733.md) |
| TC-040734 | BR1_TS16_TC13 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040734.md](BR1_TS16/TC-040734.md) |
| TC-040735 | BR1_TS16_TC14 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040735.md](BR1_TS16/TC-040735.md) |
| TC-040736 | BR1_TS16_TC15 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040736.md](BR1_TS16/TC-040736.md) |
| TC-040737 | BR1_TS16_TC16 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040737.md](BR1_TS16/TC-040737.md) |
| TC-040738 | BR1_TS16_TC17 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040738.md](BR1_TS16/TC-040738.md) |
| TC-040739 | BR1_TS16_TC18 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040739.md](BR1_TS16/TC-040739.md) |
| TC-040740 | BR1_TS16_TC19 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040740.md](BR1_TS16/TC-040740.md) |
| TC-040741 | BR1_TS16_TC20 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040741.md](BR1_TS16/TC-040741.md) |
| TC-040742 | BR1_TS16_TC21 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040742.md](BR1_TS16/TC-040742.md) |
| TC-040743 | BR1_TS16_TC22 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040743.md](BR1_TS16/TC-040743.md) |
| TC-040744 | BR1_TS16_TC23 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040744.md](BR1_TS16/TC-040744.md) |
| TC-040745 | BR1_TS16_TC24 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040745.md](BR1_TS16/TC-040745.md) |
| TC-040746 | BR1_TS16_TC25 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040746.md](BR1_TS16/TC-040746.md) |
| TC-040747 | BR1_TS16_TC26 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040747.md](BR1_TS16/TC-040747.md) |
| TC-040748 | BR1_TS16_TC27 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040748.md](BR1_TS16/TC-040748.md) |
| TC-040749 | BR1_TS16_TC28 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040749.md](BR1_TS16/TC-040749.md) |
| TC-040750 | BR1_TS16_TC29 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040750.md](BR1_TS16/TC-040750.md) |
| TC-040751 | BR1_TS16_TC30 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040751.md](BR1_TS16/TC-040751.md) |
| TC-040752 | BR1_TS16_TC31 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040752.md](BR1_TS16/TC-040752.md) |
| TC-040753 | BR1_TS16_TC32 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040753.md](BR1_TS16/TC-040753.md) |
| TC-040754 | BR1_TS16_TC33 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040754.md](BR1_TS16/TC-040754.md) |
| TC-040755 | BR1_TS16_TC34 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040755.md](BR1_TS16/TC-040755.md) |
| TC-040756 | BR1_TS16_TC35 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040756.md](BR1_TS16/TC-040756.md) |
| TC-040757 | BR1_TS16_TC36 | Alternate path: Authorization Only via alternate path: EMV Chip Data Length | **BLOCKED** | [TC-040757.md](BR1_TS16/TC-040757.md) |
| TC-040758 | BR1_TS16_TC37 | Alternate path: Authorization Only via alternate path: EMV Request Data Segment (segment 130) | **BLOCKED** | [TC-040758.md](BR1_TS16/TC-040758.md) |
| TC-040759 | BR1_TS16_TC38 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040759.md](BR1_TS16/TC-040759.md) |
| TC-040760 | BR1_TS16_TC39 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040760.md](BR1_TS16/TC-040760.md) |
| TC-040761 | BR1_TS16_TC40 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040761.md](BR1_TS16/TC-040761.md) |
| TC-040762 | BR1_TS16_TC41 | Alternate path: Authorization Only via alternate path: Network Management Message | **BLOCKED** | [TC-040762.md](BR1_TS16/TC-040762.md) |
| TC-040763 | BR1_TS16_TC42 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040763.md](BR1_TS16/TC-040763.md) |
| TC-040764 | BR1_TS16_TC43 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040764.md](BR1_TS16/TC-040764.md) |
| TC-040765 | BR1_TS16_TC44 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040765.md](BR1_TS16/TC-040765.md) |
| TC-040766 | BR1_TS16_TC45 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040766.md](BR1_TS16/TC-040766.md) |
| TC-040767 | BR1_TS16_TC46 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-040767.md](BR1_TS16/TC-040767.md) |
| TC-040768 | BR1_TS16_TC47 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-040768.md](BR1_TS16/TC-040768.md) |
| TC-040769 | BR1_TS16_TC48 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-040769.md](BR1_TS16/TC-040769.md) |
| TC-040770 | BR1_TS16_TC49 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-040770.md](BR1_TS16/TC-040770.md) |
| TC-040771 | BR1_TS16_TC50 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-040771.md](BR1_TS16/TC-040771.md) |
| TC-040772 | BR1_TS16_TC51 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-040772.md](BR1_TS16/TC-040772.md) |
| TC-040773 | BR1_TS16_TC52 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-040773.md](BR1_TS16/TC-040773.md) |
| TC-040774 | BR1_TS16_TC53 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-040774.md](BR1_TS16/TC-040774.md) |
| TC-040775 | BR1_TS16_TC54 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-040775.md](BR1_TS16/TC-040775.md) |
| TC-040776 | BR1_TS16_TC55 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-040776.md](BR1_TS16/TC-040776.md) |
| TC-040777 | BR1_TS16_TC56 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-040777.md](BR1_TS16/TC-040777.md) |
| TC-040778 | BR1_TS16_TC57 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-040778.md](BR1_TS16/TC-040778.md) |
| TC-040779 | BR1_TS16_TC58 | Decision table: Authorization Only with cof = initial | **BLOCKED** | [TC-040779.md](BR1_TS16/TC-040779.md) |
| TC-040780 | BR1_TS16_TC59 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-040780.md](BR1_TS16/TC-040780.md) |
| TC-040781 | BR1_TS16_TC60 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-040781.md](BR1_TS16/TC-040781.md) |
| TC-040782 | BR1_TS16_TC61 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-040782.md](BR1_TS16/TC-040782.md) |
| TC-040783 | BR1_TS16_TC62 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-040783.md](BR1_TS16/TC-040783.md) |
| TC-040784 | BR1_TS16_TC63 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-040784.md](BR1_TS16/TC-040784.md) |
| TC-040785 | BR1_TS16_TC64 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-040785.md](BR1_TS16/TC-040785.md) |
| TC-040786 | BR1_TS16_TC65 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-040786.md](BR1_TS16/TC-040786.md) |
| TC-040787 | BR1_TS16_TC66 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-040787.md](BR1_TS16/TC-040787.md) |
| TC-040788 | BR1_TS16_TC67 | Decision table: Authorization Only with optional segment 123 present | **BLOCKED** | [TC-040788.md](BR1_TS16/TC-040788.md) |
| TC-040789 | BR1_TS16_TC68 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-040789.md](BR1_TS16/TC-040789.md) |
| TC-040790 | BR1_TS16_TC69 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-040790.md](BR1_TS16/TC-040790.md) |
| TC-040791 | BR1_TS16_TC70 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-040791.md](BR1_TS16/TC-040791.md) |
| TC-040792 | BR1_TS16_TC71 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-040792.md](BR1_TS16/TC-040792.md) |
| TC-040793 | BR1_TS16_TC72 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-040793.md](BR1_TS16/TC-040793.md) |
| TC-040794 | BR1_TS16_TC73 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-040794.md](BR1_TS16/TC-040794.md) |
| TC-040795 | BR1_TS16_TC74 | Time-based: Authorization Only -> Cancellation after 24 hour(s) (at the stated limit) | **BLOCKED** | [TC-040795.md](BR1_TS16/TC-040795.md) |
| TC-040796 | BR1_TS16_TC75 | Time-based: Authorization Only -> Cancellation after 25 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-040796.md](BR1_TS16/TC-040796.md) |
| TC-040797 | BR1_TS16_TC76 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-040797.md](BR1_TS16/TC-040797.md) |
| TC-040798 | BR1_TS16_TC77 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-040798.md](BR1_TS16/TC-040798.md) |
| TC-040799 | BR1_TS16_TC78 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-040799.md](BR1_TS16/TC-040799.md) |
| TC-040800 | BR1_TS16_TC79 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-040800.md](BR1_TS16/TC-040800.md) |
| TC-040801 | BR1_TS16_TC80 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-040801.md](BR1_TS16/TC-040801.md) |
| TC-040802 | BR1_TS16_TC81 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-040802.md](BR1_TS16/TC-040802.md) |
| TC-040803 | BR1_TS16_TC82 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-040803.md](BR1_TS16/TC-040803.md) |
| TC-040804 | BR1_TS16_TC83 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-040804.md](BR1_TS16/TC-040804.md) |
| TC-040805 | BR1_TS16_TC84 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-040805.md](BR1_TS16/TC-040805.md) |
| TC-040806 | BR1_TS16_TC85 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-040806.md](BR1_TS16/TC-040806.md) |
| TC-040807 | BR1_TS16_TC86 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-040807.md](BR1_TS16/TC-040807.md) |
| TC-040808 | BR1_TS16_TC87 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040808.md](BR1_TS16/TC-040808.md) |
| TC-040809 | BR1_TS16_TC88 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-040809.md](BR1_TS16/TC-040809.md) |
| TC-040810 | BR1_TS16_TC89 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-040810.md](BR1_TS16/TC-040810.md) |
| TC-040811 | BR1_TS16_TC90 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-040811.md](BR1_TS16/TC-040811.md) |
| TC-040812 | BR1_TS16_TC91 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-040812.md](BR1_TS16/TC-040812.md) |
| TC-040813 | BR1_TS16_TC92 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-040813.md](BR1_TS16/TC-040813.md) |
| TC-040814 | BR1_TS16_TC93 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-040814.md](BR1_TS16/TC-040814.md) |
| TC-040815 | BR1_TS16_TC94 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040815.md](BR1_TS16/TC-040815.md) |
| TC-040816 | BR1_TS16_TC95 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-040816.md](BR1_TS16/TC-040816.md) |
| TC-040817 | BR1_TS16_TC96 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-040817.md](BR1_TS16/TC-040817.md) |
| TC-040818 | BR1_TS16_TC97 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-040818.md](BR1_TS16/TC-040818.md) |
| TC-040819 | BR1_TS16_TC98 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-040819.md](BR1_TS16/TC-040819.md) |
| TC-040820 | BR1_TS16_TC99 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-040820.md](BR1_TS16/TC-040820.md) |
| TC-040821 | BR1_TS16_TC100 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-040821.md](BR1_TS16/TC-040821.md) |
| TC-040822 | BR1_TS16_TC101 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-040822.md](BR1_TS16/TC-040822.md) |
| TC-040823 | BR1_TS16_TC102 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-040823.md](BR1_TS16/TC-040823.md) |
| TC-040824 | BR1_TS16_TC103 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-040824.md](BR1_TS16/TC-040824.md) |
| TC-040825 | BR1_TS16_TC104 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-040825.md](BR1_TS16/TC-040825.md) |
| TC-040826 | BR1_TS16_TC105 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-040826.md](BR1_TS16/TC-040826.md) |
| TC-040827 | BR1_TS16_TC106 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-040827.md](BR1_TS16/TC-040827.md) |
| TC-040828 | BR1_TS16_TC107 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-040828.md](BR1_TS16/TC-040828.md) |
| TC-040829 | BR1_TS16_TC108 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-040829.md](BR1_TS16/TC-040829.md) |
| TC-040830 | BR1_TS16_TC109 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-040830.md](BR1_TS16/TC-040830.md) |
| TC-040831 | BR1_TS16_TC110 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-040831.md](BR1_TS16/TC-040831.md) |
| TC-040832 | BR1_TS16_TC111 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-040832.md](BR1_TS16/TC-040832.md) |
| TC-040833 | BR1_TS16_TC112 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-040833.md](BR1_TS16/TC-040833.md) |
| TC-040834 | BR1_TS16_TC113 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-040834.md](BR1_TS16/TC-040834.md) |
| TC-040835 | BR1_TS16_TC114 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-040835.md](BR1_TS16/TC-040835.md) |
| TC-040836 | BR1_TS16_TC115 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-040836.md](BR1_TS16/TC-040836.md) |

<a id="br1_ts17"></a>
## BR1_TS17 - BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File @ Credential on File · customer present; card not present

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038600 | BR1_TS17_TC1 | CAVV Revised Format contains a character outside its character type (AN) | **BLOCKED** | [TC-038600.md](BR1_TS17/TC-038600.md) |
| TC-038597 | BR1_TS17_TC2 | NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it | **BLOCKED** | [TC-038597.md](BR1_TS17/TC-038597.md) |
| TC-038605 | BR1_TS17_TC3 | Purchase Card Data Segment (segment 104) omitted where p.728 requires it | **FAIL** | [TC-038605.md](BR1_TS17/TC-038605.md) |
| TC-040837 | BR1_TS17_TC4 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040837.md](BR1_TS17/TC-040837.md) |
| TC-040838 | BR1_TS17_TC5 | Happy path: Authorization Only sending Table 005 Point-of-Service Entry Mode Information | **BLOCKED** | [TC-040838.md](BR1_TS17/TC-040838.md) |
| TC-040839 | BR1_TS17_TC6 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040839.md](BR1_TS17/TC-040839.md) |
| TC-040840 | BR1_TS17_TC7 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040840.md](BR1_TS17/TC-040840.md) |
| TC-040841 | BR1_TS17_TC8 | Happy path: Authorization Only sending Table 009 Partial Approval Indicator Information | **FAIL** | [TC-040841.md](BR1_TS17/TC-040841.md) |
| TC-040842 | BR1_TS17_TC9 | Happy path: Authorization Only sending Table 012 Market-Specific Data Indicator Information | **FAIL** | [TC-040842.md](BR1_TS17/TC-040842.md) |
| TC-040843 | BR1_TS17_TC10 | Happy path: Authorization Only sending Table 014 User Data Information | **BLOCKED** | [TC-040843.md](BR1_TS17/TC-040843.md) |
| TC-040844 | BR1_TS17_TC11 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040844.md](BR1_TS17/TC-040844.md) |
| TC-040845 | BR1_TS17_TC12 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040845.md](BR1_TS17/TC-040845.md) |
| TC-040846 | BR1_TS17_TC13 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040846.md](BR1_TS17/TC-040846.md) |
| TC-040847 | BR1_TS17_TC14 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040847.md](BR1_TS17/TC-040847.md) |
| TC-040848 | BR1_TS17_TC15 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040848.md](BR1_TS17/TC-040848.md) |
| TC-040849 | BR1_TS17_TC16 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040849.md](BR1_TS17/TC-040849.md) |
| TC-040850 | BR1_TS17_TC17 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040850.md](BR1_TS17/TC-040850.md) |
| TC-040851 | BR1_TS17_TC18 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040851.md](BR1_TS17/TC-040851.md) |
| TC-040852 | BR1_TS17_TC19 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040852.md](BR1_TS17/TC-040852.md) |
| TC-040853 | BR1_TS17_TC20 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040853.md](BR1_TS17/TC-040853.md) |
| TC-040854 | BR1_TS17_TC21 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040854.md](BR1_TS17/TC-040854.md) |
| TC-040855 | BR1_TS17_TC22 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040855.md](BR1_TS17/TC-040855.md) |
| TC-040856 | BR1_TS17_TC23 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040856.md](BR1_TS17/TC-040856.md) |
| TC-040857 | BR1_TS17_TC24 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040857.md](BR1_TS17/TC-040857.md) |
| TC-040858 | BR1_TS17_TC25 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040858.md](BR1_TS17/TC-040858.md) |
| TC-040859 | BR1_TS17_TC26 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040859.md](BR1_TS17/TC-040859.md) |
| TC-040860 | BR1_TS17_TC27 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040860.md](BR1_TS17/TC-040860.md) |
| TC-040861 | BR1_TS17_TC28 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040861.md](BR1_TS17/TC-040861.md) |
| TC-040862 | BR1_TS17_TC29 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040862.md](BR1_TS17/TC-040862.md) |
| TC-040863 | BR1_TS17_TC30 | Happy path: Authorization Only sending Table 057 Soft Descriptor - Merchant City | **BLOCKED** | [TC-040863.md](BR1_TS17/TC-040863.md) |
| TC-040864 | BR1_TS17_TC31 | Happy path: Authorization Only sending Table 058 Soft Descriptor - Merchant State | **BLOCKED** | [TC-040864.md](BR1_TS17/TC-040864.md) |
| TC-040865 | BR1_TS17_TC32 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040865.md](BR1_TS17/TC-040865.md) |
| TC-040866 | BR1_TS17_TC33 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040866.md](BR1_TS17/TC-040866.md) |
| TC-040867 | BR1_TS17_TC34 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040867.md](BR1_TS17/TC-040867.md) |
| TC-040868 | BR1_TS17_TC35 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040868.md](BR1_TS17/TC-040868.md) |
| TC-040869 | BR1_TS17_TC36 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040869.md](BR1_TS17/TC-040869.md) |
| TC-040870 | BR1_TS17_TC37 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040870.md](BR1_TS17/TC-040870.md) |
| TC-040871 | BR1_TS17_TC38 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040871.md](BR1_TS17/TC-040871.md) |
| TC-040872 | BR1_TS17_TC39 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040872.md](BR1_TS17/TC-040872.md) |
| TC-040873 | BR1_TS17_TC40 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040873.md](BR1_TS17/TC-040873.md) |
| TC-040874 | BR1_TS17_TC41 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040874.md](BR1_TS17/TC-040874.md) |
| TC-040875 | BR1_TS17_TC42 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040875.md](BR1_TS17/TC-040875.md) |
| TC-040876 | BR1_TS17_TC43 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040876.md](BR1_TS17/TC-040876.md) |
| TC-040877 | BR1_TS17_TC44 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040877.md](BR1_TS17/TC-040877.md) |
| TC-040878 | BR1_TS17_TC45 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040878.md](BR1_TS17/TC-040878.md) |
| TC-040879 | BR1_TS17_TC46 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040879.md](BR1_TS17/TC-040879.md) |
| TC-040880 | BR1_TS17_TC47 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040880.md](BR1_TS17/TC-040880.md) |
| TC-040881 | BR1_TS17_TC48 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040881.md](BR1_TS17/TC-040881.md) |
| TC-040882 | BR1_TS17_TC49 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040882.md](BR1_TS17/TC-040882.md) |
| TC-040883 | BR1_TS17_TC50 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040883.md](BR1_TS17/TC-040883.md) |
| TC-040884 | BR1_TS17_TC51 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-040884.md](BR1_TS17/TC-040884.md) |
| TC-040885 | BR1_TS17_TC52 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-040885.md](BR1_TS17/TC-040885.md) |
| TC-040886 | BR1_TS17_TC53 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-040886.md](BR1_TS17/TC-040886.md) |
| TC-040887 | BR1_TS17_TC54 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-040887.md](BR1_TS17/TC-040887.md) |
| TC-040888 | BR1_TS17_TC55 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-040888.md](BR1_TS17/TC-040888.md) |
| TC-040889 | BR1_TS17_TC56 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-040889.md](BR1_TS17/TC-040889.md) |
| TC-040890 | BR1_TS17_TC57 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-040890.md](BR1_TS17/TC-040890.md) |
| TC-040891 | BR1_TS17_TC58 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-040891.md](BR1_TS17/TC-040891.md) |
| TC-040892 | BR1_TS17_TC59 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-040892.md](BR1_TS17/TC-040892.md) |
| TC-040893 | BR1_TS17_TC60 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-040893.md](BR1_TS17/TC-040893.md) |
| TC-040894 | BR1_TS17_TC61 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-040894.md](BR1_TS17/TC-040894.md) |
| TC-040895 | BR1_TS17_TC62 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-040895.md](BR1_TS17/TC-040895.md) |
| TC-040896 | BR1_TS17_TC63 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-040896.md](BR1_TS17/TC-040896.md) |
| TC-040897 | BR1_TS17_TC64 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-040897.md](BR1_TS17/TC-040897.md) |
| TC-040898 | BR1_TS17_TC65 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-040898.md](BR1_TS17/TC-040898.md) |
| TC-040899 | BR1_TS17_TC66 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-040899.md](BR1_TS17/TC-040899.md) |
| TC-040900 | BR1_TS17_TC67 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-040900.md](BR1_TS17/TC-040900.md) |
| TC-040901 | BR1_TS17_TC68 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-040901.md](BR1_TS17/TC-040901.md) |
| TC-040902 | BR1_TS17_TC69 | Decision table: Authorization Only with initiation = M | **BLOCKED** | [TC-040902.md](BR1_TS17/TC-040902.md) |
| TC-040903 | BR1_TS17_TC70 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-040903.md](BR1_TS17/TC-040903.md) |
| TC-040904 | BR1_TS17_TC71 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-040904.md](BR1_TS17/TC-040904.md) |
| TC-040905 | BR1_TS17_TC72 | Decision table: Authorization Only with optional segment 123 present | **BLOCKED** | [TC-040905.md](BR1_TS17/TC-040905.md) |
| TC-040906 | BR1_TS17_TC73 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-040906.md](BR1_TS17/TC-040906.md) |
| TC-040907 | BR1_TS17_TC74 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-040907.md](BR1_TS17/TC-040907.md) |
| TC-040908 | BR1_TS17_TC75 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-040908.md](BR1_TS17/TC-040908.md) |
| TC-040909 | BR1_TS17_TC76 | Decision table: Authorization Only with special payment = I | **BLOCKED** | [TC-040909.md](BR1_TS17/TC-040909.md) |
| TC-040910 | BR1_TS17_TC77 | Decision table: Authorization Only with special payment = R | **BLOCKED** | [TC-040910.md](BR1_TS17/TC-040910.md) |
| TC-040911 | BR1_TS17_TC78 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-040911.md](BR1_TS17/TC-040911.md) |
| TC-040912 | BR1_TS17_TC79 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-040912.md](BR1_TS17/TC-040912.md) |
| TC-040913 | BR1_TS17_TC80 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-040913.md](BR1_TS17/TC-040913.md) |
| TC-040914 | BR1_TS17_TC81 | Time-based: Authorization Only -> Cancellation after 72 hour(s) (at the stated limit) | **BLOCKED** | [TC-040914.md](BR1_TS17/TC-040914.md) |
| TC-040915 | BR1_TS17_TC82 | Time-based: Authorization Only -> Cancellation after 73 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-040915.md](BR1_TS17/TC-040915.md) |
| TC-040916 | BR1_TS17_TC83 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-040916.md](BR1_TS17/TC-040916.md) |
| TC-040917 | BR1_TS17_TC84 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-040917.md](BR1_TS17/TC-040917.md) |
| TC-040918 | BR1_TS17_TC85 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-040918.md](BR1_TS17/TC-040918.md) |
| TC-040919 | BR1_TS17_TC86 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-040919.md](BR1_TS17/TC-040919.md) |
| TC-040920 | BR1_TS17_TC87 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-040920.md](BR1_TS17/TC-040920.md) |
| TC-040921 | BR1_TS17_TC88 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-040921.md](BR1_TS17/TC-040921.md) |
| TC-040922 | BR1_TS17_TC89 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-040922.md](BR1_TS17/TC-040922.md) |
| TC-040923 | BR1_TS17_TC90 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-040923.md](BR1_TS17/TC-040923.md) |
| TC-040924 | BR1_TS17_TC91 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-040924.md](BR1_TS17/TC-040924.md) |
| TC-040925 | BR1_TS17_TC92 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-040925.md](BR1_TS17/TC-040925.md) |
| TC-040926 | BR1_TS17_TC93 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-040926.md](BR1_TS17/TC-040926.md) |
| TC-040927 | BR1_TS17_TC94 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040927.md](BR1_TS17/TC-040927.md) |
| TC-040928 | BR1_TS17_TC95 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-040928.md](BR1_TS17/TC-040928.md) |
| TC-040929 | BR1_TS17_TC96 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-040929.md](BR1_TS17/TC-040929.md) |
| TC-040930 | BR1_TS17_TC97 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-040930.md](BR1_TS17/TC-040930.md) |
| TC-040931 | BR1_TS17_TC98 | Response / oracle check: Authorization Only -> Merchant Data Segment returned and checked (p.713-716) | **BLOCKED** | [TC-040931.md](BR1_TS17/TC-040931.md) |
| TC-040932 | BR1_TS17_TC99 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-040932.md](BR1_TS17/TC-040932.md) |
| TC-040933 | BR1_TS17_TC100 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-040933.md](BR1_TS17/TC-040933.md) |
| TC-040934 | BR1_TS17_TC101 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-040934.md](BR1_TS17/TC-040934.md) |
| TC-040935 | BR1_TS17_TC102 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-040935.md](BR1_TS17/TC-040935.md) |
| TC-040936 | BR1_TS17_TC103 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-040936.md](BR1_TS17/TC-040936.md) |
| TC-040937 | BR1_TS17_TC104 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-040937.md](BR1_TS17/TC-040937.md) |
| TC-040938 | BR1_TS17_TC105 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-040938.md](BR1_TS17/TC-040938.md) |
| TC-040939 | BR1_TS17_TC106 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-040939.md](BR1_TS17/TC-040939.md) |
| TC-040940 | BR1_TS17_TC107 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.713) | **BLOCKED** | [TC-040940.md](BR1_TS17/TC-040940.md) |
| TC-040941 | BR1_TS17_TC108 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-040941.md](BR1_TS17/TC-040941.md) |
| TC-040942 | BR1_TS17_TC109 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-040942.md](BR1_TS17/TC-040942.md) |
| TC-040943 | BR1_TS17_TC110 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-040943.md](BR1_TS17/TC-040943.md) |
| TC-040944 | BR1_TS17_TC111 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-040944.md](BR1_TS17/TC-040944.md) |
| TC-040945 | BR1_TS17_TC112 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-040945.md](BR1_TS17/TC-040945.md) |
| TC-040946 | BR1_TS17_TC113 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-040946.md](BR1_TS17/TC-040946.md) |
| TC-040947 | BR1_TS17_TC114 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-040947.md](BR1_TS17/TC-040947.md) |
| TC-040948 | BR1_TS17_TC115 | Response / oracle check: Authorization Only sending Table 021 Settlement Data Acceptance Flag -> response table checked | **FAIL** | [TC-040948.md](BR1_TS17/TC-040948.md) |
| TC-040949 | BR1_TS17_TC116 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-040949.md](BR1_TS17/TC-040949.md) |
| TC-040950 | BR1_TS17_TC117 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-040950.md](BR1_TS17/TC-040950.md) |
| TC-040951 | BR1_TS17_TC118 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-040951.md](BR1_TS17/TC-040951.md) |
| TC-040952 | BR1_TS17_TC119 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-040952.md](BR1_TS17/TC-040952.md) |
| TC-040953 | BR1_TS17_TC120 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-040953.md](BR1_TS17/TC-040953.md) |
| TC-040954 | BR1_TS17_TC121 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-040954.md](BR1_TS17/TC-040954.md) |
| TC-040955 | BR1_TS17_TC122 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-040955.md](BR1_TS17/TC-040955.md) |
| TC-040956 | BR1_TS17_TC123 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-040956.md](BR1_TS17/TC-040956.md) |
| TC-040957 | BR1_TS17_TC124 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-040957.md](BR1_TS17/TC-040957.md) |
| TC-040958 | BR1_TS17_TC125 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-040958.md](BR1_TS17/TC-040958.md) |

<a id="br1_ts18"></a>
## BR1_TS18 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-040959 | BR1_TS18_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-040959.md](BR1_TS18/TC-040959.md) |
| TC-040960 | BR1_TS18_TC2 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-040960.md](BR1_TS18/TC-040960.md) |
| TC-040961 | BR1_TS18_TC3 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-040961.md](BR1_TS18/TC-040961.md) |
| TC-040962 | BR1_TS18_TC4 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-040962.md](BR1_TS18/TC-040962.md) |
| TC-040963 | BR1_TS18_TC5 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-040963.md](BR1_TS18/TC-040963.md) |
| TC-040964 | BR1_TS18_TC6 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-040964.md](BR1_TS18/TC-040964.md) |
| TC-040965 | BR1_TS18_TC7 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-040965.md](BR1_TS18/TC-040965.md) |
| TC-040966 | BR1_TS18_TC8 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-040966.md](BR1_TS18/TC-040966.md) |
| TC-040967 | BR1_TS18_TC9 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-040967.md](BR1_TS18/TC-040967.md) |
| TC-040968 | BR1_TS18_TC10 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-040968.md](BR1_TS18/TC-040968.md) |
| TC-040969 | BR1_TS18_TC11 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-040969.md](BR1_TS18/TC-040969.md) |
| TC-040970 | BR1_TS18_TC12 | Happy path: Authorization Only sending Table 040 Final Amount Indicator | **BLOCKED** | [TC-040970.md](BR1_TS18/TC-040970.md) |
| TC-040971 | BR1_TS18_TC13 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-040971.md](BR1_TS18/TC-040971.md) |
| TC-040972 | BR1_TS18_TC14 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-040972.md](BR1_TS18/TC-040972.md) |
| TC-040973 | BR1_TS18_TC15 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-040973.md](BR1_TS18/TC-040973.md) |
| TC-040974 | BR1_TS18_TC16 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-040974.md](BR1_TS18/TC-040974.md) |
| TC-040975 | BR1_TS18_TC17 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-040975.md](BR1_TS18/TC-040975.md) |
| TC-040976 | BR1_TS18_TC18 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-040976.md](BR1_TS18/TC-040976.md) |
| TC-040977 | BR1_TS18_TC19 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-040977.md](BR1_TS18/TC-040977.md) |
| TC-040978 | BR1_TS18_TC20 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-040978.md](BR1_TS18/TC-040978.md) |
| TC-040979 | BR1_TS18_TC21 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-040979.md](BR1_TS18/TC-040979.md) |
| TC-040980 | BR1_TS18_TC22 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-040980.md](BR1_TS18/TC-040980.md) |
| TC-040981 | BR1_TS18_TC23 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-040981.md](BR1_TS18/TC-040981.md) |
| TC-040982 | BR1_TS18_TC24 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-040982.md](BR1_TS18/TC-040982.md) |
| TC-040983 | BR1_TS18_TC25 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-040983.md](BR1_TS18/TC-040983.md) |
| TC-040984 | BR1_TS18_TC26 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-040984.md](BR1_TS18/TC-040984.md) |
| TC-040985 | BR1_TS18_TC27 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-040985.md](BR1_TS18/TC-040985.md) |
| TC-040986 | BR1_TS18_TC28 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-040986.md](BR1_TS18/TC-040986.md) |
| TC-040987 | BR1_TS18_TC29 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-040987.md](BR1_TS18/TC-040987.md) |
| TC-040988 | BR1_TS18_TC30 | Alternate path: Authorization Only via alternate path: Approved Amount | **BLOCKED** | [TC-040988.md](BR1_TS18/TC-040988.md) |
| TC-040989 | BR1_TS18_TC31 | Alternate path: Authorization Only via alternate path: Card Type | **BLOCKED** | [TC-040989.md](BR1_TS18/TC-040989.md) |
| TC-040990 | BR1_TS18_TC32 | Alternate path: Authorization Only via alternate path: Download Indicator | **BLOCKED** | [TC-040990.md](BR1_TS18/TC-040990.md) |
| TC-040991 | BR1_TS18_TC33 | Alternate path: Authorization Only via alternate path: EMV Card Sequence Number | **BLOCKED** | [TC-040991.md](BR1_TS18/TC-040991.md) |
| TC-040992 | BR1_TS18_TC34 | Alternate path: Authorization Only via alternate path: EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, depending on the length of the field specified by the precedinglength portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data. | **BLOCKED** | [TC-040992.md](BR1_TS18/TC-040992.md) |
| TC-040993 | BR1_TS18_TC35 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040993.md](BR1_TS18/TC-040993.md) |
| TC-040994 | BR1_TS18_TC36 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_13 | **BLOCKED** | [TC-040994.md](BR1_TS18/TC-040994.md) |
| TC-040995 | BR1_TS18_TC37 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-049-SUB-SUB_TABLE_ID_23 | **BLOCKED** | [TC-040995.md](BR1_TS18/TC-040995.md) |
| TC-040996 | BR1_TS18_TC38 | Alternate path: Authorization Only via alternate path: Service Level | **BLOCKED** | [TC-040996.md](BR1_TS18/TC-040996.md) |
| TC-040997 | BR1_TS18_TC39 | Alternate path: Authorization Only via alternate path: Table 030 POS Condition Code | **BLOCKED** | [TC-040997.md](BR1_TS18/TC-040997.md) |
| TC-040998 | BR1_TS18_TC40 | Single rule broken: Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-040998.md](BR1_TS18/TC-040998.md) |
| TC-040999 | BR1_TS18_TC41 | Single rule broken: Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-040999.md](BR1_TS18/TC-040999.md) |
| TC-041000 | BR1_TS18_TC42 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041000.md](BR1_TS18/TC-041000.md) |
| TC-041001 | BR1_TS18_TC43 | Single rule broken: Authorization Only with Information Byte sent where the spec forbids it -> declined | **FAIL** | [TC-041001.md](BR1_TS18/TC-041001.md) |
| TC-041002 | BR1_TS18_TC44 | Single rule broken: Authorization Only with Payment Tender Type sent where the spec forbids it -> declined | **FAIL** | [TC-041002.md](BR1_TS18/TC-041002.md) |
| TC-041003 | BR1_TS18_TC45 | Single rule broken: Authorization Only with Service Level sent where the spec forbids it -> declined | **FAIL** | [TC-041003.md](BR1_TS18/TC-041003.md) |
| TC-041004 | BR1_TS18_TC46 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041004.md](BR1_TS18/TC-041004.md) |
| TC-041005 | BR1_TS18_TC47 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041005.md](BR1_TS18/TC-041005.md) |
| TC-041006 | BR1_TS18_TC48 | Decision table: Authorization Only with Partial Approval Indicator = 0 (the rule's condition holds) | **BLOCKED** | [TC-041006.md](BR1_TS18/TC-041006.md) |
| TC-041007 | BR1_TS18_TC49 | Decision table: Authorization Only with Payment Tender Type = AX (the rule's condition holds) | **FAIL** | [TC-041007.md](BR1_TS18/TC-041007.md) |
| TC-041008 | BR1_TS18_TC50 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **FAIL** | [TC-041008.md](BR1_TS18/TC-041008.md) |
| TC-041009 | BR1_TS18_TC51 | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | **FAIL** | [TC-041009.md](BR1_TS18/TC-041009.md) |
| TC-041010 | BR1_TS18_TC52 | Decision table: Authorization Only with Update Code = A (the rule's condition holds) | **BLOCKED** | [TC-041010.md](BR1_TS18/TC-041010.md) |
| TC-041011 | BR1_TS18_TC53 | Decision table: Authorization Only with avs = true | **BLOCKED** | [TC-041011.md](BR1_TS18/TC-041011.md) |
| TC-041012 | BR1_TS18_TC54 | Decision table: Authorization Only with cof = initial | **BLOCKED** | [TC-041012.md](BR1_TS18/TC-041012.md) |
| TC-041013 | BR1_TS18_TC55 | Decision table: Authorization Only with cvm = offline_pin | **BLOCKED** | [TC-041013.md](BR1_TS18/TC-041013.md) |
| TC-041014 | BR1_TS18_TC56 | Decision table: Authorization Only with cvm = online_pin | **BLOCKED** | [TC-041014.md](BR1_TS18/TC-041014.md) |
| TC-041015 | BR1_TS18_TC57 | Decision table: Authorization Only with cvv2 = mismatch | **BLOCKED** | [TC-041015.md](BR1_TS18/TC-041015.md) |
| TC-041016 | BR1_TS18_TC58 | Decision table: Authorization Only with cvv2 = true | **BLOCKED** | [TC-041016.md](BR1_TS18/TC-041016.md) |
| TC-041017 | BR1_TS18_TC59 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041017.md](BR1_TS18/TC-041017.md) |
| TC-041018 | BR1_TS18_TC60 | Decision table: Authorization Only with incremental = original | **BLOCKED** | [TC-041018.md](BR1_TS18/TC-041018.md) |
| TC-041019 | BR1_TS18_TC61 | Decision table: Authorization Only with optional segment 100 present | **FAIL** | [TC-041019.md](BR1_TS18/TC-041019.md) |
| TC-041020 | BR1_TS18_TC62 | Decision table: Authorization Only with optional segment 104 present | **BLOCKED** | [TC-041020.md](BR1_TS18/TC-041020.md) |
| TC-041021 | BR1_TS18_TC63 | Decision table: Authorization Only with optional segment 145 present | **FAIL** | [TC-041021.md](BR1_TS18/TC-041021.md) |
| TC-041022 | BR1_TS18_TC64 | Decision table: Authorization Only with optional segment 153 present | **BLOCKED** | [TC-041022.md](BR1_TS18/TC-041022.md) |
| TC-041023 | BR1_TS18_TC65 | Decision table: Authorization Only with segment 111 sent (the rule's condition holds) | **BLOCKED** | [TC-041023.md](BR1_TS18/TC-041023.md) |
| TC-041024 | BR1_TS18_TC66 | Decision table: Authorization Only with track = 1 | **BLOCKED** | [TC-041024.md](BR1_TS18/TC-041024.md) |
| TC-041025 | BR1_TS18_TC67 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-041025.md](BR1_TS18/TC-041025.md) |
| TC-041026 | BR1_TS18_TC68 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-041026.md](BR1_TS18/TC-041026.md) |
| TC-041027 | BR1_TS18_TC69 | Time-based: Authorization Only -> Cancellation after 24 hour(s) (at the stated limit) | **BLOCKED** | [TC-041027.md](BR1_TS18/TC-041027.md) |
| TC-041028 | BR1_TS18_TC70 | Time-based: Authorization Only -> Cancellation after 25 hour(s) (beyond the stated limit) | **BLOCKED** | [TC-041028.md](BR1_TS18/TC-041028.md) |
| TC-041029 | BR1_TS18_TC71 | Running total / state: Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-041029.md](BR1_TS18/TC-041029.md) |
| TC-041030 | BR1_TS18_TC72 | Running total / state: Authorization Only approved -> Totals Request | **FAIL** | [TC-041030.md](BR1_TS18/TC-041030.md) |
| TC-041031 | BR1_TS18_TC73 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-041031.md](BR1_TS18/TC-041031.md) |
| TC-041032 | BR1_TS18_TC74 | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-041032.md](BR1_TS18/TC-041032.md) |
| TC-041033 | BR1_TS18_TC75 | Response / oracle check: Authorization Only -> Additional Information Data Layouts returned | **BLOCKED** | [TC-041033.md](BR1_TS18/TC-041033.md) |
| TC-041034 | BR1_TS18_TC76 | Response / oracle check: Authorization Only -> Additional Information Data Segment returned and checked (p.177) | **BLOCKED** | [TC-041034.md](BR1_TS18/TC-041034.md) |
| TC-041035 | BR1_TS18_TC77 | Response / oracle check: Authorization Only -> BUYPASS Card Type Code, PIN on Receipt Information returned | **BLOCKED** | [TC-041035.md](BR1_TS18/TC-041035.md) |
| TC-041036 | BR1_TS18_TC78 | Response / oracle check: Authorization Only -> CVV Information returned | **FAIL** | [TC-041036.md](BR1_TS18/TC-041036.md) |
| TC-041037 | BR1_TS18_TC79 | Response / oracle check: Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-041037.md](BR1_TS18/TC-041037.md) |
| TC-041038 | BR1_TS18_TC80 | Response / oracle check: Authorization Only -> Cardholder Additional Information Result Code returned | **BLOCKED** | [TC-041038.md](BR1_TS18/TC-041038.md) |
| TC-041039 | BR1_TS18_TC81 | Response / oracle check: Authorization Only -> Date and Time Data Segment returned and checked (p.195) | **BLOCKED** | [TC-041039.md](BR1_TS18/TC-041039.md) |
| TC-041040 | BR1_TS18_TC82 | Response / oracle check: Authorization Only -> Dial String Data Segment returned and checked (p.189) | **BLOCKED** | [TC-041040.md](BR1_TS18/TC-041040.md) |
| TC-041041 | BR1_TS18_TC83 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041041.md](BR1_TS18/TC-041041.md) |
| TC-041042 | BR1_TS18_TC84 | Response / oracle check: Authorization Only -> Merchant Advice Code returned | **BLOCKED** | [TC-041042.md](BR1_TS18/TC-041042.md) |
| TC-041043 | BR1_TS18_TC85 | Response / oracle check: Authorization Only -> Merchant Advice Code, DAF Indicator returned | **BLOCKED** | [TC-041043.md](BR1_TS18/TC-041043.md) |
| TC-041044 | BR1_TS18_TC86 | Response / oracle check: Authorization Only -> Network Token Data Response returned and checked (p.339-340) | **BLOCKED** | [TC-041044.md](BR1_TS18/TC-041044.md) |
| TC-041045 | BR1_TS18_TC87 | Response / oracle check: Authorization Only -> PIN on Receipt Information returned | **BLOCKED** | [TC-041045.md](BR1_TS18/TC-041045.md) |
| TC-041046 | BR1_TS18_TC88 | Response / oracle check: Authorization Only -> Real Time Account Updater Response Segment returned and checked (p.317) | **BLOCKED** | [TC-041046.md](BR1_TS18/TC-041046.md) |
| TC-041047 | BR1_TS18_TC89 | Response / oracle check: Authorization Only -> Software Dial Load Data Segment returned and checked (p.189) | **BLOCKED** | [TC-041047.md](BR1_TS18/TC-041047.md) |
| TC-041048 | BR1_TS18_TC90 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041048.md](BR1_TS18/TC-041048.md) |
| TC-041049 | BR1_TS18_TC91 | Response / oracle check: Authorization Only -> Token Update First Use Indicator returned | **BLOCKED** | [TC-041049.md](BR1_TS18/TC-041049.md) |
| TC-041050 | BR1_TS18_TC92 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041050.md](BR1_TS18/TC-041050.md) |
| TC-041051 | BR1_TS18_TC93 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041051.md](BR1_TS18/TC-041051.md) |
| TC-041052 | BR1_TS18_TC94 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041052.md](BR1_TS18/TC-041052.md) |
| TC-041053 | BR1_TS18_TC95 | Response / oracle check: Authorization Only -> Transaction Link Identifier, Transaction Link Action Indicator returned | **BLOCKED** | [TC-041053.md](BR1_TS18/TC-041053.md) |
| TC-041054 | BR1_TS18_TC96 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041054.md](BR1_TS18/TC-041054.md) |
| TC-041055 | BR1_TS18_TC97 | Response / oracle check: Authorization Only sending Table 001 UPC Information -> response table checked | **BLOCKED** | [TC-041055.md](BR1_TS18/TC-041055.md) |
| TC-041056 | BR1_TS18_TC98 | Response / oracle check: Authorization Only sending Table 002 Program Identifier Information -> response table checked | **BLOCKED** | [TC-041056.md](BR1_TS18/TC-041056.md) |
| TC-041057 | BR1_TS18_TC99 | Response / oracle check: Authorization Only sending Table 017 Suspected Fraud Reversal Indicator Information -> response table checked | **BLOCKED** | [TC-041057.md](BR1_TS18/TC-041057.md) |
| TC-041058 | BR1_TS18_TC100 | Response / oracle check: Authorization Only sending Table 018 Card Acceptor Terminal Identifier Information -> response table checked | **BLOCKED** | [TC-041058.md](BR1_TS18/TC-041058.md) |
| TC-041059 | BR1_TS18_TC101 | Response / oracle check: Authorization Only sending Table 034 Name Verification Service (NVS) Information -> response table checked | **BLOCKED** | [TC-041059.md](BR1_TS18/TC-041059.md) |
| TC-041060 | BR1_TS18_TC102 | Response / oracle check: Authorization Only sending Table 036 MasterCard UCAF Data -> response table checked | **FAIL** | [TC-041060.md](BR1_TS18/TC-041060.md) |
| TC-041061 | BR1_TS18_TC103 | Response / oracle check: Authorization Only sending Table 038 Paydiant Tender ID -> response table checked | **BLOCKED** | [TC-041061.md](BR1_TS18/TC-041061.md) |
| TC-041062 | BR1_TS18_TC104 | Response / oracle check: Authorization Only sending Table 047 Soft Descriptor - Merchant Name -> response table checked | **BLOCKED** | [TC-041062.md](BR1_TS18/TC-041062.md) |
| TC-041063 | BR1_TS18_TC105 | Response / oracle check: Authorization Only sending Table 059 Soft Descriptor - Merchant Country -> response table checked | **BLOCKED** | [TC-041063.md](BR1_TS18/TC-041063.md) |
| TC-041064 | BR1_TS18_TC106 | Response / oracle check: Authorization Only sending Table 060 Real Time Account Updater Request Data -> response table checked | **BLOCKED** | [TC-041064.md](BR1_TS18/TC-041064.md) |
| TC-041065 | BR1_TS18_TC107 | Response / oracle check: Authorization Only sending Table 062 Customer Additional Information Data -> response table checked | **BLOCKED** | [TC-041065.md](BR1_TS18/TC-041065.md) |
| TC-041066 | BR1_TS18_TC108 | Response / oracle check: Authorization Only sending Table 066 Digital Commerce Data -> response table checked | **FAIL** | [TC-041066.md](BR1_TS18/TC-041066.md) |
| TC-041067 | BR1_TS18_TC109 | Response / oracle check: Authorization Only sending Table 067 Anticipated Amount -> response table checked | **FAIL** | [TC-041067.md](BR1_TS18/TC-041067.md) |
| TC-041068 | BR1_TS18_TC110 | Response / oracle check: Authorization Only sending Table 080 Staged Wallet ID -> response table checked | **BLOCKED** | [TC-041068.md](BR1_TS18/TC-041068.md) |

<a id="br1_ts19"></a>
## BR1_TS19 - BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed @ Mail Order · mail and/or telephone order

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041069 | BR1_TS19_TC1 | Happy path: Mail/Phone Authorization Only approved | **BLOCKED** | [TC-041069.md](BR1_TS19/TC-041069.md) |
| TC-041070 | BR1_TS19_TC2 | Single rule broken: Mail/Phone Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041070.md](BR1_TS19/TC-041070.md) |
| TC-041071 | BR1_TS19_TC3 | Single rule broken: Mail/Phone Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041071.md](BR1_TS19/TC-041071.md) |
| TC-041072 | BR1_TS19_TC4 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-041072.md](BR1_TS19/TC-041072.md) |
| TC-041073 | BR1_TS19_TC5 | Valid flow: Mail/Phone Authorization Only -> Cancellation | **BLOCKED** | [TC-041073.md](BR1_TS19/TC-041073.md) |
| TC-041074 | BR1_TS19_TC6 | Running total / state: Mail/Phone Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-041074.md](BR1_TS19/TC-041074.md) |
| TC-041075 | BR1_TS19_TC7 | Running total / state: Mail/Phone Authorization Only approved -> Totals Request | **FAIL** | [TC-041075.md](BR1_TS19/TC-041075.md) |
| TC-041076 | BR1_TS19_TC8 | Running total / state: Running total / state check: Response Code | **DEVICE** | [TC-041076.md](BR1_TS19/TC-041076.md) |
| TC-041077 | BR1_TS19_TC9 | Running total / state: Running total / state check: rule BR-404-11 (p.404) | **DEVICE** | [TC-041077.md](BR1_TS19/TC-041077.md) |
| TC-041078 | BR1_TS19_TC10 | Running total / state: Running total / state check: rule BR-404-12 (p.404) | **DEVICE** | [TC-041078.md](BR1_TS19/TC-041078.md) |
| TC-041079 | BR1_TS19_TC11 | Running total / state: Running total / state check: rule BR-404-7 (p.404) | **DEVICE** | [TC-041079.md](BR1_TS19/TC-041079.md) |
| TC-041080 | BR1_TS19_TC12 | Running total / state: Running total / state check: rule BR-404-8 (p.404) | **DEVICE** | [TC-041080.md](BR1_TS19/TC-041080.md) |
| TC-041081 | BR1_TS19_TC13 | Ordering: Ordering check: Electronic Mail Data Segment (segment 109) | **DEVICE** | [TC-041081.md](BR1_TS19/TC-041081.md) |
| TC-041082 | BR1_TS19_TC14 | Interaction (pairwise): Mail/Phone Authorization Only on a POS terminal with offline pin | **BLOCKED** | [TC-041082.md](BR1_TS19/TC-041082.md) |
| TC-041083 | BR1_TS19_TC15 | Response / oracle check: Mail/Phone Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-041083.md](BR1_TS19/TC-041083.md) |
| TC-041084 | BR1_TS19_TC16 | Response / oracle check: Mail/Phone Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-041084.md](BR1_TS19/TC-041084.md) |
| TC-041085 | BR1_TS19_TC17 | Response / oracle check: Mail/Phone Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041085.md](BR1_TS19/TC-041085.md) |
| TC-041086 | BR1_TS19_TC18 | Device checkpoint: Theme safety net - Message Format Version Identifier (p.184) | **DEVICE** | [TC-041086.md](BR1_TS19/TC-041086.md) |
| TC-041087 | BR1_TS19_TC19 | Single rule broken: Mail/Phone Authorization Only with entry mode 05 (EMV Contact Chip, not supported) -> declined | **BLOCKED** | [TC-041087.md](BR1_TS19/TC-041087.md) |
| TC-041088 | BR1_TS19_TC20 | Single rule broken: Mail/Phone Authorization Only with entry mode 07 (EMV Contactless Chip, not supported) -> declined | **BLOCKED** | [TC-041088.md](BR1_TS19/TC-041088.md) |
| TC-041089 | BR1_TS19_TC21 | Single rule broken: Mail/Phone Authorization Only with entry mode 02 (Magnetic Stripe, not supported) -> declined | **BLOCKED** | [TC-041089.md](BR1_TS19/TC-041089.md) |
| TC-041090 | BR1_TS19_TC22 | Single rule broken: Mail/Phone Authorization Only with entry mode 91 (Contactless Magnetic Stripe, not supported) -> declined | **BLOCKED** | [TC-041090.md](BR1_TS19/TC-041090.md) |
| TC-041091 | BR1_TS19_TC23 | Single rule broken: Mail/Phone Authorization Only with entry mode 79 (Chip Fallback, not supported) -> declined | **BLOCKED** | [TC-041091.md](BR1_TS19/TC-041091.md) |
| TC-041092 | BR1_TS19_TC24 | Single rule broken: Mail/Phone Authorization Only with entry mode 82 (Contactless Mobile Commerce Device, not supported) -> declined | **BLOCKED** | [TC-041092.md](BR1_TS19/TC-041092.md) |

<a id="br1_ts20"></a>
## BR1_TS20 - BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File @ Mail Order · mail and/or telephone order

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041093 | BR1_TS20_TC1 | Happy path: Mail/Phone Authorization Only approved | **BLOCKED** | [TC-041093.md](BR1_TS20/TC-041093.md) |
| TC-041094 | BR1_TS20_TC2 | Single rule broken: Mail/Phone Authorization Only -> Cancellation with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041094.md](BR1_TS20/TC-041094.md) |
| TC-041095 | BR1_TS20_TC3 | Single rule broken: Mail/Phone Authorization Only -> Cancellation with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041095.md](BR1_TS20/TC-041095.md) |
| TC-041096 | BR1_TS20_TC4 | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | **BLOCKED** | [TC-041096.md](BR1_TS20/TC-041096.md) |
| TC-041097 | BR1_TS20_TC5 | Valid flow: Mail/Phone Authorization Only -> Cancellation | **BLOCKED** | [TC-041097.md](BR1_TS20/TC-041097.md) |
| TC-041098 | BR1_TS20_TC6 | Running total / state: Mail/Phone Authorization Only -> Cancellation -> Totals Request | **FAIL** | [TC-041098.md](BR1_TS20/TC-041098.md) |
| TC-041099 | BR1_TS20_TC7 | Running total / state: Mail/Phone Authorization Only approved -> Totals Request | **FAIL** | [TC-041099.md](BR1_TS20/TC-041099.md) |
| TC-041100 | BR1_TS20_TC8 | Interaction (pairwise): Mail/Phone Authorization Only on a POS terminal with offline pin | **BLOCKED** | [TC-041100.md](BR1_TS20/TC-041100.md) |
| TC-041101 | BR1_TS20_TC9 | Response / oracle check: Mail/Phone Authorization Only -> AVS or NVS or Both Information returned | **FAIL** | [TC-041101.md](BR1_TS20/TC-041101.md) |
| TC-041102 | BR1_TS20_TC10 | Response / oracle check: Mail/Phone Authorization Only -> Cancellation -> declined (issuer decline) | **BLOCKED** | [TC-041102.md](BR1_TS20/TC-041102.md) |
| TC-041103 | BR1_TS20_TC11 | Response / oracle check: Mail/Phone Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041103.md](BR1_TS20/TC-041103.md) |

<a id="br1_ts21"></a>
## BR1_TS21 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038637 | BR1_TS21_TC1 | Variable Information Indicator one byte over its documented maximum length (3) | **FAIL** | [TC-038637.md](BR1_TS21/TC-038637.md) |
| TC-038599 | BR1_TS21_TC2 | Required field Product Amount sent empty | **FAIL** | [TC-038599.md](BR1_TS21/TC-038599.md) |
| TC-041104 | BR1_TS21_TC3 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041104.md](BR1_TS21/TC-041104.md) |
| TC-041105 | BR1_TS21_TC4 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041105.md](BR1_TS21/TC-041105.md) |
| TC-041106 | BR1_TS21_TC5 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041106.md](BR1_TS21/TC-041106.md) |
| TC-041107 | BR1_TS21_TC6 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041107.md](BR1_TS21/TC-041107.md) |
| TC-041108 | BR1_TS21_TC7 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041108.md](BR1_TS21/TC-041108.md) |
| TC-041109 | BR1_TS21_TC8 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041109.md](BR1_TS21/TC-041109.md) |
| TC-041110 | BR1_TS21_TC9 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041110.md](BR1_TS21/TC-041110.md) |
| TC-041111 | BR1_TS21_TC10 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041111.md](BR1_TS21/TC-041111.md) |
| TC-041112 | BR1_TS21_TC11 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041112.md](BR1_TS21/TC-041112.md) |

<a id="br1_ts22"></a>
## BR1_TS22 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041113 | BR1_TS22_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041113.md](BR1_TS22/TC-041113.md) |
| TC-041114 | BR1_TS22_TC2 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041114.md](BR1_TS22/TC-041114.md) |
| TC-041115 | BR1_TS22_TC3 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041115.md](BR1_TS22/TC-041115.md) |
| TC-041116 | BR1_TS22_TC4 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041116.md](BR1_TS22/TC-041116.md) |
| TC-041117 | BR1_TS22_TC5 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041117.md](BR1_TS22/TC-041117.md) |
| TC-041118 | BR1_TS22_TC6 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041118.md](BR1_TS22/TC-041118.md) |
| TC-041119 | BR1_TS22_TC7 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041119.md](BR1_TS22/TC-041119.md) |
| TC-041120 | BR1_TS22_TC8 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041120.md](BR1_TS22/TC-041120.md) |
| TC-041121 | BR1_TS22_TC9 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041121.md](BR1_TS22/TC-041121.md) |

<a id="br1_ts23"></a>
## BR1_TS23 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041122 | BR1_TS23_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041122.md](BR1_TS23/TC-041122.md) |
| TC-041123 | BR1_TS23_TC2 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041123.md](BR1_TS23/TC-041123.md) |
| TC-041124 | BR1_TS23_TC3 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041124.md](BR1_TS23/TC-041124.md) |
| TC-041125 | BR1_TS23_TC4 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041125.md](BR1_TS23/TC-041125.md) |
| TC-041126 | BR1_TS23_TC5 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041126.md](BR1_TS23/TC-041126.md) |
| TC-041127 | BR1_TS23_TC6 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041127.md](BR1_TS23/TC-041127.md) |
| TC-041128 | BR1_TS23_TC7 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041128.md](BR1_TS23/TC-041128.md) |
| TC-041129 | BR1_TS23_TC8 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041129.md](BR1_TS23/TC-041129.md) |
| TC-041130 | BR1_TS23_TC9 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041130.md](BR1_TS23/TC-041130.md) |

<a id="br1_ts24"></a>
## BR1_TS24 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041131 | BR1_TS24_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041131.md](BR1_TS24/TC-041131.md) |
| TC-041132 | BR1_TS24_TC2 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041132.md](BR1_TS24/TC-041132.md) |
| TC-041133 | BR1_TS24_TC3 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041133.md](BR1_TS24/TC-041133.md) |
| TC-041134 | BR1_TS24_TC4 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041134.md](BR1_TS24/TC-041134.md) |
| TC-041135 | BR1_TS24_TC5 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041135.md](BR1_TS24/TC-041135.md) |
| TC-041136 | BR1_TS24_TC6 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041136.md](BR1_TS24/TC-041136.md) |
| TC-041137 | BR1_TS24_TC7 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041137.md](BR1_TS24/TC-041137.md) |
| TC-041138 | BR1_TS24_TC8 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041138.md](BR1_TS24/TC-041138.md) |
| TC-041139 | BR1_TS24_TC9 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041139.md](BR1_TS24/TC-041139.md) |

<a id="br1_ts25"></a>
## BR1_TS25 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041140 | BR1_TS25_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041140.md](BR1_TS25/TC-041140.md) |
| TC-041141 | BR1_TS25_TC2 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041141.md](BR1_TS25/TC-041141.md) |
| TC-041142 | BR1_TS25_TC3 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041142.md](BR1_TS25/TC-041142.md) |
| TC-041143 | BR1_TS25_TC4 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041143.md](BR1_TS25/TC-041143.md) |
| TC-041144 | BR1_TS25_TC5 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041144.md](BR1_TS25/TC-041144.md) |
| TC-041145 | BR1_TS25_TC6 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041145.md](BR1_TS25/TC-041145.md) |
| TC-041146 | BR1_TS25_TC7 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041146.md](BR1_TS25/TC-041146.md) |
| TC-041147 | BR1_TS25_TC8 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041147.md](BR1_TS25/TC-041147.md) |
| TC-041148 | BR1_TS25_TC9 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041148.md](BR1_TS25/TC-041148.md) |

<a id="br1_ts26"></a>
## BR1_TS26 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038606 | BR1_TS26_TC1 | Token PAN Suffix one byte over its documented maximum length (4) | **BLOCKED** | [TC-038606.md](BR1_TS26/TC-038606.md) |
| TC-041149 | BR1_TS26_TC2 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041149.md](BR1_TS26/TC-041149.md) |
| TC-041150 | BR1_TS26_TC3 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041150.md](BR1_TS26/TC-041150.md) |
| TC-041151 | BR1_TS26_TC4 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041151.md](BR1_TS26/TC-041151.md) |
| TC-041152 | BR1_TS26_TC5 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041152.md](BR1_TS26/TC-041152.md) |
| TC-041153 | BR1_TS26_TC6 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041153.md](BR1_TS26/TC-041153.md) |
| TC-041154 | BR1_TS26_TC7 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041154.md](BR1_TS26/TC-041154.md) |
| TC-041155 | BR1_TS26_TC8 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041155.md](BR1_TS26/TC-041155.md) |
| TC-041156 | BR1_TS26_TC9 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041156.md](BR1_TS26/TC-041156.md) |
| TC-041157 | BR1_TS26_TC10 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041157.md](BR1_TS26/TC-041157.md) |

<a id="br1_ts27"></a>
## BR1_TS27 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File @ Credential on File · customer present; card not present

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041158 | BR1_TS27_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041158.md](BR1_TS27/TC-041158.md) |
| TC-041159 | BR1_TS27_TC2 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041159.md](BR1_TS27/TC-041159.md) |
| TC-041160 | BR1_TS27_TC3 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041160.md](BR1_TS27/TC-041160.md) |
| TC-041161 | BR1_TS27_TC4 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041161.md](BR1_TS27/TC-041161.md) |
| TC-041162 | BR1_TS27_TC5 | Fault / recovery: Merchandise Return unanswered -> Void of a Merchandise Return | **BLOCKED** | [TC-041162.md](BR1_TS27/TC-041162.md) |
| TC-041163 | BR1_TS27_TC6 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041163.md](BR1_TS27/TC-041163.md) |
| TC-041164 | BR1_TS27_TC7 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041164.md](BR1_TS27/TC-041164.md) |
| TC-041165 | BR1_TS27_TC8 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041165.md](BR1_TS27/TC-041165.md) |
| TC-041166 | BR1_TS27_TC9 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041166.md](BR1_TS27/TC-041166.md) |
| TC-041167 | BR1_TS27_TC10 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041167.md](BR1_TS27/TC-041167.md) |

<a id="br1_ts28"></a>
## BR1_TS28 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041168 | BR1_TS28_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041168.md](BR1_TS28/TC-041168.md) |
| TC-041169 | BR1_TS28_TC2 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Sequence Number not matching the original -> declined | **BLOCKED** | [TC-041169.md](BR1_TS28/TC-041169.md) |
| TC-041170 | BR1_TS28_TC3 | Single rule broken: Merchandise Return -> Void of a Merchandise Return with Terminal Identifier not matching the original -> declined | **BLOCKED** | [TC-041170.md](BR1_TS28/TC-041170.md) |
| TC-041171 | BR1_TS28_TC4 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041171.md](BR1_TS28/TC-041171.md) |
| TC-041172 | BR1_TS28_TC5 | Running total / state: Merchandise Return -> Void of a Merchandise Return -> Totals Request | **FAIL** | [TC-041172.md](BR1_TS28/TC-041172.md) |
| TC-041173 | BR1_TS28_TC6 | Running total / state: Merchandise Return approved -> Totals Request | **BLOCKED** | [TC-041173.md](BR1_TS28/TC-041173.md) |
| TC-041174 | BR1_TS28_TC7 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041174.md](BR1_TS28/TC-041174.md) |
| TC-041175 | BR1_TS28_TC8 | Response / oracle check: Merchandise Return -> Void of a Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041175.md](BR1_TS28/TC-041175.md) |
| TC-041176 | BR1_TS28_TC9 | Response / oracle check: Merchandise Return -> declined (issuer decline) | **BLOCKED** | [TC-041176.md](BR1_TS28/TC-041176.md) |

<a id="br1_ts29"></a>
## BR1_TS29 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038634 | BR1_TS29_TC1 | Information Byte exactly at its documented maximum length (1 bytes) is accepted | **FAIL** | [TC-038634.md](BR1_TS29/TC-038634.md) |
| TC-038629 | BR1_TS29_TC2 | EMV Chip Data Length contains a character outside its character type (N) | **FAIL** | [TC-038629.md](BR1_TS29/TC-038629.md) |
| TC-038633 | BR1_TS29_TC3 | Tax Type 1 value not in its documented valid values | **FAIL** | [TC-038633.md](BR1_TS29/TC-038633.md) |
| TC-038595 | BR1_TS29_TC4 | required segment 100 Standard Message Data Segment omitted | **BLOCKED** | [TC-038595.md](BR1_TS29/TC-038595.md) |
| TC-041177 | BR1_TS29_TC5 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041177.md](BR1_TS29/TC-041177.md) |

<a id="br1_ts30"></a>
## BR1_TS30 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038625 | BR1_TS30_TC1 | Update Code one byte over its documented maximum length (1) | **FAIL** | [TC-038625.md](BR1_TS30/TC-038625.md) |
| TC-041178 | BR1_TS30_TC2 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041178.md](BR1_TS30/TC-041178.md) |

<a id="br1_ts31"></a>
## BR1_TS31 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038601 | BR1_TS31_TC1 | Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted | **FAIL** | [TC-038601.md](BR1_TS31/TC-038601.md) |
| TC-041179 | BR1_TS31_TC2 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041179.md](BR1_TS31/TC-041179.md) |

<a id="br1_ts32"></a>
## BR1_TS32 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041180 | BR1_TS32_TC1 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041180.md](BR1_TS32/TC-041180.md) |

<a id="br1_ts33"></a>
## BR1_TS33 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041181 | BR1_TS33_TC1 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041181.md](BR1_TS33/TC-041181.md) |

<a id="br1_ts34"></a>
## BR1_TS34 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041182 | BR1_TS34_TC1 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041182.md](BR1_TS34/TC-041182.md) |

<a id="br1_ts35"></a>
## BR1_TS35 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File @ Credential on File · customer present; card not present

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041183 | BR1_TS35_TC1 | Happy path: Balance Inquiry approved | **BLOCKED** | [TC-041183.md](BR1_TS35/TC-041183.md) |
| TC-041184 | BR1_TS35_TC2 | Running total / state: Balance Inquiry approved -> Totals Request | **BLOCKED** | [TC-041184.md](BR1_TS35/TC-041184.md) |
| TC-041185 | BR1_TS35_TC3 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041185.md](BR1_TS35/TC-041185.md) |
| TC-041186 | BR1_TS35_TC4 | Response / oracle check: Balance Inquiry -> declined (issuer decline) | **BLOCKED** | [TC-041186.md](BR1_TS35/TC-041186.md) |

<a id="br1_ts36"></a>
## BR1_TS36 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device @ General Retail · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041187 | BR1_TS36_TC1 | Interaction (pairwise): Balance Inquiry on a POS terminal with offline pin | **BLOCKED** | [TC-041187.md](BR1_TS36/TC-041187.md) |

<a id="br1_ts37"></a>
## BR1_TS37 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041797 | BR1_TS37_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041797.md](BR1_TS37/TC-041797.md) |

<a id="br1_ts38"></a>
## BR1_TS38 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041798 | BR1_TS38_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041798.md](BR1_TS38/TC-041798.md) |

<a id="br1_ts39"></a>
## BR1_TS39 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041799 | BR1_TS39_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041799.md](BR1_TS39/TC-041799.md) |

<a id="br1_ts40"></a>
## BR1_TS40 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041800 | BR1_TS40_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041800.md](BR1_TS40/TC-041800.md) |

<a id="br1_ts41"></a>
## BR1_TS41 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041801 | BR1_TS41_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041801.md](BR1_TS41/TC-041801.md) |

<a id="br1_ts42"></a>
## BR1_TS42 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041802 | BR1_TS42_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041802.md](BR1_TS42/TC-041802.md) |

<a id="br1_ts43"></a>
## BR1_TS43 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041803 | BR1_TS43_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041803.md](BR1_TS43/TC-041803.md) |

<a id="br1_ts44"></a>
## BR1_TS44 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041804 | BR1_TS44_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041804.md](BR1_TS44/TC-041804.md) |

<a id="br1_ts45"></a>
## BR1_TS45 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041805 | BR1_TS45_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041805.md](BR1_TS45/TC-041805.md) |

<a id="br1_ts46"></a>
## BR1_TS46 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041806 | BR1_TS46_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041806.md](BR1_TS46/TC-041806.md) |

<a id="br1_ts47"></a>
## BR1_TS47 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041188 | BR1_TS47_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041188.md](BR1_TS47/TC-041188.md) |
| TC-041189 | BR1_TS47_TC2 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-041189.md](BR1_TS47/TC-041189.md) |
| TC-041190 | BR1_TS47_TC3 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-041190.md](BR1_TS47/TC-041190.md) |
| TC-041191 | BR1_TS47_TC4 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-041191.md](BR1_TS47/TC-041191.md) |
| TC-041192 | BR1_TS47_TC5 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-041192.md](BR1_TS47/TC-041192.md) |
| TC-041193 | BR1_TS47_TC6 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041193.md](BR1_TS47/TC-041193.md) |

<a id="br1_ts48"></a>
## BR1_TS48 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041194 | BR1_TS48_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041194.md](BR1_TS48/TC-041194.md) |
| TC-041195 | BR1_TS48_TC2 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041195.md](BR1_TS48/TC-041195.md) |

<a id="br1_ts49"></a>
## BR1_TS49 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041196 | BR1_TS49_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041196.md](BR1_TS49/TC-041196.md) |
| TC-041197 | BR1_TS49_TC2 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041197.md](BR1_TS49/TC-041197.md) |

<a id="br1_ts50"></a>
## BR1_TS50 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041198 | BR1_TS50_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041198.md](BR1_TS50/TC-041198.md) |
| TC-041199 | BR1_TS50_TC2 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041199.md](BR1_TS50/TC-041199.md) |

<a id="br1_ts51"></a>
## BR1_TS51 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041200 | BR1_TS51_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041200.md](BR1_TS51/TC-041200.md) |
| TC-041201 | BR1_TS51_TC2 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041201.md](BR1_TS51/TC-041201.md) |

<a id="br1_ts52"></a>
## BR1_TS52 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041202 | BR1_TS52_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041202.md](BR1_TS52/TC-041202.md) |
| TC-041203 | BR1_TS52_TC2 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041203.md](BR1_TS52/TC-041203.md) |

<a id="br1_ts53"></a>
## BR1_TS53 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041204 | BR1_TS53_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041204.md](BR1_TS53/TC-041204.md) |
| TC-041205 | BR1_TS53_TC2 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041205.md](BR1_TS53/TC-041205.md) |

<a id="br1_ts54"></a>
## BR1_TS54 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041206 | BR1_TS54_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041206.md](BR1_TS54/TC-041206.md) |
| TC-041207 | BR1_TS54_TC2 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-041207.md](BR1_TS54/TC-041207.md) |
| TC-041208 | BR1_TS54_TC3 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-041208.md](BR1_TS54/TC-041208.md) |
| TC-041209 | BR1_TS54_TC4 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-041209.md](BR1_TS54/TC-041209.md) |
| TC-041210 | BR1_TS54_TC5 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-041210.md](BR1_TS54/TC-041210.md) |
| TC-041211 | BR1_TS54_TC6 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-041211.md](BR1_TS54/TC-041211.md) |
| TC-041212 | BR1_TS54_TC7 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-041212.md](BR1_TS54/TC-041212.md) |
| TC-041213 | BR1_TS54_TC8 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-041213.md](BR1_TS54/TC-041213.md) |
| TC-041214 | BR1_TS54_TC9 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-041214.md](BR1_TS54/TC-041214.md) |
| TC-041215 | BR1_TS54_TC10 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-041215.md](BR1_TS54/TC-041215.md) |
| TC-041216 | BR1_TS54_TC11 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-041216.md](BR1_TS54/TC-041216.md) |
| TC-041217 | BR1_TS54_TC12 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-041217.md](BR1_TS54/TC-041217.md) |
| TC-041218 | BR1_TS54_TC13 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-041218.md](BR1_TS54/TC-041218.md) |
| TC-041219 | BR1_TS54_TC14 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-041219.md](BR1_TS54/TC-041219.md) |
| TC-041220 | BR1_TS54_TC15 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-041220.md](BR1_TS54/TC-041220.md) |
| TC-041221 | BR1_TS54_TC16 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-041221.md](BR1_TS54/TC-041221.md) |
| TC-041222 | BR1_TS54_TC17 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-041222.md](BR1_TS54/TC-041222.md) |
| TC-041223 | BR1_TS54_TC18 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-041223.md](BR1_TS54/TC-041223.md) |
| TC-041224 | BR1_TS54_TC19 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-041224.md](BR1_TS54/TC-041224.md) |
| TC-041225 | BR1_TS54_TC20 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-041225.md](BR1_TS54/TC-041225.md) |
| TC-041226 | BR1_TS54_TC21 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-041226.md](BR1_TS54/TC-041226.md) |
| TC-041227 | BR1_TS54_TC22 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-041227.md](BR1_TS54/TC-041227.md) |
| TC-041228 | BR1_TS54_TC23 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-041228.md](BR1_TS54/TC-041228.md) |
| TC-041229 | BR1_TS54_TC24 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-041229.md](BR1_TS54/TC-041229.md) |
| TC-041230 | BR1_TS54_TC25 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-041230.md](BR1_TS54/TC-041230.md) |
| TC-041231 | BR1_TS54_TC26 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-041231.md](BR1_TS54/TC-041231.md) |
| TC-041232 | BR1_TS54_TC27 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041232.md](BR1_TS54/TC-041232.md) |
| TC-041233 | BR1_TS54_TC28 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-041233.md](BR1_TS54/TC-041233.md) |
| TC-041234 | BR1_TS54_TC29 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-041234.md](BR1_TS54/TC-041234.md) |
| TC-041235 | BR1_TS54_TC30 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041235.md](BR1_TS54/TC-041235.md) |

<a id="br1_ts55"></a>
## BR1_TS55 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041236 | BR1_TS55_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041236.md](BR1_TS55/TC-041236.md) |
| TC-041237 | BR1_TS55_TC2 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041237.md](BR1_TS55/TC-041237.md) |
| TC-041238 | BR1_TS55_TC3 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041238.md](BR1_TS55/TC-041238.md) |

<a id="br1_ts56"></a>
## BR1_TS56 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041239 | BR1_TS56_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041239.md](BR1_TS56/TC-041239.md) |
| TC-041240 | BR1_TS56_TC2 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041240.md](BR1_TS56/TC-041240.md) |
| TC-041241 | BR1_TS56_TC3 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041241.md](BR1_TS56/TC-041241.md) |
| TC-041242 | BR1_TS56_TC4 | Device checkpoint: Device behaviour - rule BR-64-6 (p.64) - ATL105 | **DEVICE** | [TC-041242.md](BR1_TS56/TC-041242.md) |

<a id="br1_ts57"></a>
## BR1_TS57 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041243 | BR1_TS57_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041243.md](BR1_TS57/TC-041243.md) |
| TC-041244 | BR1_TS57_TC2 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041244.md](BR1_TS57/TC-041244.md) |
| TC-041245 | BR1_TS57_TC3 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041245.md](BR1_TS57/TC-041245.md) |

<a id="br1_ts58"></a>
## BR1_TS58 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041246 | BR1_TS58_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041246.md](BR1_TS58/TC-041246.md) |
| TC-041247 | BR1_TS58_TC2 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041247.md](BR1_TS58/TC-041247.md) |
| TC-041248 | BR1_TS58_TC3 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041248.md](BR1_TS58/TC-041248.md) |

<a id="br1_ts59"></a>
## BR1_TS59 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041249 | BR1_TS59_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041249.md](BR1_TS59/TC-041249.md) |
| TC-041250 | BR1_TS59_TC2 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041250.md](BR1_TS59/TC-041250.md) |
| TC-041251 | BR1_TS59_TC3 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041251.md](BR1_TS59/TC-041251.md) |

<a id="br1_ts60"></a>
## BR1_TS60 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041252 | BR1_TS60_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041252.md](BR1_TS60/TC-041252.md) |
| TC-041253 | BR1_TS60_TC2 | Decision table: Authorization Only with incremental = incremental | **BLOCKED** | [TC-041253.md](BR1_TS60/TC-041253.md) |
| TC-041254 | BR1_TS60_TC3 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041254.md](BR1_TS60/TC-041254.md) |

<a id="br1_ts61"></a>
## BR1_TS61 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041807 | BR1_TS61_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041807.md](BR1_TS61/TC-041807.md) |

<a id="br1_ts62"></a>
## BR1_TS62 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041808 | BR1_TS62_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041808.md](BR1_TS62/TC-041808.md) |

<a id="br1_ts63"></a>
## BR1_TS63 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041809 | BR1_TS63_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041809.md](BR1_TS63/TC-041809.md) |

<a id="br1_ts64"></a>
## BR1_TS64 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041810 | BR1_TS64_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041810.md](BR1_TS64/TC-041810.md) |

<a id="br1_ts65"></a>
## BR1_TS65 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041811 | BR1_TS65_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041811.md](BR1_TS65/TC-041811.md) |

<a id="br1_ts66"></a>
## BR1_TS66 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041812 | BR1_TS66_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041812.md](BR1_TS66/TC-041812.md) |

<a id="br1_ts67"></a>
## BR1_TS67 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041813 | BR1_TS67_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041813.md](BR1_TS67/TC-041813.md) |

<a id="br1_ts68"></a>
## BR1_TS68 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041814 | BR1_TS68_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041814.md](BR1_TS68/TC-041814.md) |

<a id="br1_ts69"></a>
## BR1_TS69 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041815 | BR1_TS69_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041815.md](BR1_TS69/TC-041815.md) |

<a id="br1_ts70"></a>
## BR1_TS70 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041816 | BR1_TS70_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041816.md](BR1_TS70/TC-041816.md) |

<a id="br1_ts71"></a>
## BR1_TS71 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041817 | BR1_TS71_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041817.md](BR1_TS71/TC-041817.md) |

<a id="br1_ts72"></a>
## BR1_TS72 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041818 | BR1_TS72_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041818.md](BR1_TS72/TC-041818.md) |

<a id="br1_ts73"></a>
## BR1_TS73 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041819 | BR1_TS73_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041819.md](BR1_TS73/TC-041819.md) |

<a id="br1_ts74"></a>
## BR1_TS74 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041820 | BR1_TS74_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041820.md](BR1_TS74/TC-041820.md) |

<a id="br1_ts75"></a>
## BR1_TS75 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041821 | BR1_TS75_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041821.md](BR1_TS75/TC-041821.md) |

<a id="br1_ts76"></a>
## BR1_TS76 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041822 | BR1_TS76_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041822.md](BR1_TS76/TC-041822.md) |

<a id="br1_ts77"></a>
## BR1_TS77 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041823 | BR1_TS77_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041823.md](BR1_TS77/TC-041823.md) |

<a id="br1_ts78"></a>
## BR1_TS78 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041824 | BR1_TS78_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041824.md](BR1_TS78/TC-041824.md) |

<a id="br1_ts79"></a>
## BR1_TS79 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041825 | BR1_TS79_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041825.md](BR1_TS79/TC-041825.md) |

<a id="br1_ts80"></a>
## BR1_TS80 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041826 | BR1_TS80_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041826.md](BR1_TS80/TC-041826.md) |

<a id="br1_ts81"></a>
## BR1_TS81 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041827 | BR1_TS81_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041827.md](BR1_TS81/TC-041827.md) |

<a id="br1_ts82"></a>
## BR1_TS82 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041828 | BR1_TS82_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041828.md](BR1_TS82/TC-041828.md) |

<a id="br1_ts83"></a>
## BR1_TS83 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041829 | BR1_TS83_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041829.md](BR1_TS83/TC-041829.md) |

<a id="br1_ts84"></a>
## BR1_TS84 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041830 | BR1_TS84_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041830.md](BR1_TS84/TC-041830.md) |

<a id="br1_ts85"></a>
## BR1_TS85 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041831 | BR1_TS85_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041831.md](BR1_TS85/TC-041831.md) |

<a id="br1_ts86"></a>
## BR1_TS86 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041832 | BR1_TS86_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041832.md](BR1_TS86/TC-041832.md) |

<a id="br1_ts87"></a>
## BR1_TS87 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041833 | BR1_TS87_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041833.md](BR1_TS87/TC-041833.md) |

<a id="br1_ts88"></a>
## BR1_TS88 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041834 | BR1_TS88_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041834.md](BR1_TS88/TC-041834.md) |

<a id="br1_ts89"></a>
## BR1_TS89 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041835 | BR1_TS89_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041835.md](BR1_TS89/TC-041835.md) |

<a id="br1_ts90"></a>
## BR1_TS90 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041836 | BR1_TS90_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041836.md](BR1_TS90/TC-041836.md) |

<a id="br1_ts91"></a>
## BR1_TS91 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Supermarket · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041837 | BR1_TS91_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041837.md](BR1_TS91/TC-041837.md) |

<a id="br1_ts99"></a>
## BR1_TS99 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041838 | BR1_TS99_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041838.md](BR1_TS99/TC-041838.md) |

<a id="br1_ts100"></a>
## BR1_TS100 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041839 | BR1_TS100_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041839.md](BR1_TS100/TC-041839.md) |

<a id="br1_ts101"></a>
## BR1_TS101 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041840 | BR1_TS101_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041840.md](BR1_TS101/TC-041840.md) |

<a id="br1_ts102"></a>
## BR1_TS102 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041841 | BR1_TS102_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041841.md](BR1_TS102/TC-041841.md) |

<a id="br1_ts103"></a>
## BR1_TS103 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041842 | BR1_TS103_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041842.md](BR1_TS103/TC-041842.md) |

<a id="br1_ts104"></a>
## BR1_TS104 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041843 | BR1_TS104_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041843.md](BR1_TS104/TC-041843.md) |

<a id="br1_ts105"></a>
## BR1_TS105 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041844 | BR1_TS105_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041844.md](BR1_TS105/TC-041844.md) |

<a id="br1_ts106"></a>
## BR1_TS106 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041845 | BR1_TS106_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041845.md](BR1_TS106/TC-041845.md) |

<a id="br1_ts107"></a>
## BR1_TS107 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041846 | BR1_TS107_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041846.md](BR1_TS107/TC-041846.md) |

<a id="br1_ts108"></a>
## BR1_TS108 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041847 | BR1_TS108_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041847.md](BR1_TS108/TC-041847.md) |

<a id="br1_ts109"></a>
## BR1_TS109 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041848 | BR1_TS109_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041848.md](BR1_TS109/TC-041848.md) |

<a id="br1_ts110"></a>
## BR1_TS110 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041849 | BR1_TS110_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041849.md](BR1_TS110/TC-041849.md) |

<a id="br1_ts111"></a>
## BR1_TS111 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041850 | BR1_TS111_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041850.md](BR1_TS111/TC-041850.md) |

<a id="br1_ts112"></a>
## BR1_TS112 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041851 | BR1_TS112_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041851.md](BR1_TS112/TC-041851.md) |

<a id="br1_ts113"></a>
## BR1_TS113 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041852 | BR1_TS113_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041852.md](BR1_TS113/TC-041852.md) |

<a id="br1_ts114"></a>
## BR1_TS114 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041853 | BR1_TS114_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041853.md](BR1_TS114/TC-041853.md) |

<a id="br1_ts115"></a>
## BR1_TS115 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041854 | BR1_TS115_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041854.md](BR1_TS115/TC-041854.md) |

<a id="br1_ts116"></a>
## BR1_TS116 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041855 | BR1_TS116_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041855.md](BR1_TS116/TC-041855.md) |

<a id="br1_ts117"></a>
## BR1_TS117 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041856 | BR1_TS117_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041856.md](BR1_TS117/TC-041856.md) |

<a id="br1_ts118"></a>
## BR1_TS118 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041857 | BR1_TS118_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041857.md](BR1_TS118/TC-041857.md) |

<a id="br1_ts119"></a>
## BR1_TS119 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041858 | BR1_TS119_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041858.md](BR1_TS119/TC-041858.md) |

<a id="br1_ts120"></a>
## BR1_TS120 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041859 | BR1_TS120_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041859.md](BR1_TS120/TC-041859.md) |

<a id="br1_ts121"></a>
## BR1_TS121 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041860 | BR1_TS121_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041860.md](BR1_TS121/TC-041860.md) |

<a id="br1_ts122"></a>
## BR1_TS122 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041861 | BR1_TS122_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041861.md](BR1_TS122/TC-041861.md) |

<a id="br1_ts123"></a>
## BR1_TS123 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041862 | BR1_TS123_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041862.md](BR1_TS123/TC-041862.md) |

<a id="br1_ts124"></a>
## BR1_TS124 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041863 | BR1_TS124_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041863.md](BR1_TS124/TC-041863.md) |

<a id="br1_ts125"></a>
## BR1_TS125 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041864 | BR1_TS125_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041864.md](BR1_TS125/TC-041864.md) |

<a id="br1_ts126"></a>
## BR1_TS126 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041865 | BR1_TS126_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041865.md](BR1_TS126/TC-041865.md) |

<a id="br1_ts127"></a>
## BR1_TS127 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041866 | BR1_TS127_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041866.md](BR1_TS127/TC-041866.md) |

<a id="br1_ts128"></a>
## BR1_TS128 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041867 | BR1_TS128_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041867.md](BR1_TS128/TC-041867.md) |

<a id="br1_ts129"></a>
## BR1_TS129 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Utilities · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041868 | BR1_TS129_TC1 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041868.md](BR1_TS129/TC-041868.md) |

<a id="br1_ts137"></a>
## BR1_TS137 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041869 | BR1_TS137_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041869.md](BR1_TS137/TC-041869.md) |

<a id="br1_ts138"></a>
## BR1_TS138 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041870 | BR1_TS138_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041870.md](BR1_TS138/TC-041870.md) |

<a id="br1_ts139"></a>
## BR1_TS139 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041871 | BR1_TS139_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041871.md](BR1_TS139/TC-041871.md) |

<a id="br1_ts140"></a>
## BR1_TS140 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041872 | BR1_TS140_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041872.md](BR1_TS140/TC-041872.md) |

<a id="br1_ts141"></a>
## BR1_TS141 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041873 | BR1_TS141_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041873.md](BR1_TS141/TC-041873.md) |

<a id="br1_ts142"></a>
## BR1_TS142 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041874 | BR1_TS142_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041874.md](BR1_TS142/TC-041874.md) |

<a id="br1_ts143"></a>
## BR1_TS143 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041875 | BR1_TS143_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041875.md](BR1_TS143/TC-041875.md) |

<a id="br1_ts144"></a>
## BR1_TS144 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041876 | BR1_TS144_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041876.md](BR1_TS144/TC-041876.md) |

<a id="br1_ts145"></a>
## BR1_TS145 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041877 | BR1_TS145_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041877.md](BR1_TS145/TC-041877.md) |

<a id="br1_ts146"></a>
## BR1_TS146 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041878 | BR1_TS146_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041878.md](BR1_TS146/TC-041878.md) |

<a id="br1_ts147"></a>
## BR1_TS147 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038622 | BR1_TS147_TC1 | Additional Information exactly at its documented maximum length (984 bytes) is accepted | **FAIL** | [TC-038622.md](BR1_TS147/TC-038622.md) |
| TC-038624 | BR1_TS147_TC2 | Customer Data contains a character outside its character type (AN) | **FAIL** | [TC-038624.md](BR1_TS147/TC-038624.md) |
| TC-038613 | BR1_TS147_TC3 | Service Level value not in its documented valid values | **FAIL** | [TC-038613.md](BR1_TS147/TC-038613.md) |
| TC-038614 | BR1_TS147_TC4 | EMV Request Data Segment (segment 130) omitted where p.85 requires it | **BLOCKED** | [TC-038614.md](BR1_TS147/TC-038614.md) |
| TC-041255 | BR1_TS147_TC5 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041255.md](BR1_TS147/TC-041255.md) |
| TC-041256 | BR1_TS147_TC6 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-041256.md](BR1_TS147/TC-041256.md) |
| TC-041257 | BR1_TS147_TC7 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-041257.md](BR1_TS147/TC-041257.md) |
| TC-041258 | BR1_TS147_TC8 | Invalid state transition: Purchase Reversal with no original -> declined | **BLOCKED** | [TC-041258.md](BR1_TS147/TC-041258.md) |
| TC-041259 | BR1_TS147_TC9 | Invalid state transition: Purchase/Capture -> Purchase Reversal -> Purchase Reversal again (already closed) -> declined | **BLOCKED** | [TC-041259.md](BR1_TS147/TC-041259.md) |
| TC-041260 | BR1_TS147_TC10 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-041260.md](BR1_TS147/TC-041260.md) |
| TC-041261 | BR1_TS147_TC11 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-041261.md](BR1_TS147/TC-041261.md) |
| TC-041262 | BR1_TS147_TC12 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-041262.md](BR1_TS147/TC-041262.md) |
| TC-041263 | BR1_TS147_TC13 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041263.md](BR1_TS147/TC-041263.md) |
| TC-041264 | BR1_TS147_TC14 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041264.md](BR1_TS147/TC-041264.md) |

<a id="br1_ts148"></a>
## BR1_TS148 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038611 | BR1_TS148_TC1 | Loyalty Track 2 Data one byte over its documented maximum length (38) | **FAIL** | [TC-038611.md](BR1_TS148/TC-038611.md) |
| TC-038621 | BR1_TS148_TC2 | Information Byte value not in its documented valid values | **FAIL** | [TC-038621.md](BR1_TS148/TC-038621.md) |
| TC-041265 | BR1_TS148_TC3 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041265.md](BR1_TS148/TC-041265.md) |
| TC-041266 | BR1_TS148_TC4 | Invalid state transition: Purchase Reversal with no original -> declined | **BLOCKED** | [TC-041266.md](BR1_TS148/TC-041266.md) |
| TC-041267 | BR1_TS148_TC5 | Invalid state transition: Purchase/Capture -> Purchase Reversal -> Purchase Reversal again (already closed) -> declined | **BLOCKED** | [TC-041267.md](BR1_TS148/TC-041267.md) |
| TC-041268 | BR1_TS148_TC6 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-041268.md](BR1_TS148/TC-041268.md) |
| TC-041269 | BR1_TS148_TC7 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041269.md](BR1_TS148/TC-041269.md) |
| TC-041270 | BR1_TS148_TC8 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041270.md](BR1_TS148/TC-041270.md) |

<a id="br1_ts149"></a>
## BR1_TS149 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041271 | BR1_TS149_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041271.md](BR1_TS149/TC-041271.md) |
| TC-041272 | BR1_TS149_TC2 | Invalid state transition: Purchase Reversal with no original -> declined | **BLOCKED** | [TC-041272.md](BR1_TS149/TC-041272.md) |
| TC-041273 | BR1_TS149_TC3 | Invalid state transition: Purchase/Capture -> Purchase Reversal -> Purchase Reversal again (already closed) -> declined | **BLOCKED** | [TC-041273.md](BR1_TS149/TC-041273.md) |
| TC-041274 | BR1_TS149_TC4 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-041274.md](BR1_TS149/TC-041274.md) |
| TC-041275 | BR1_TS149_TC5 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041275.md](BR1_TS149/TC-041275.md) |
| TC-041276 | BR1_TS149_TC6 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041276.md](BR1_TS149/TC-041276.md) |

<a id="br1_ts150"></a>
## BR1_TS150 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041277 | BR1_TS150_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041277.md](BR1_TS150/TC-041277.md) |
| TC-041278 | BR1_TS150_TC2 | Invalid state transition: Purchase Reversal with no original -> declined | **BLOCKED** | [TC-041278.md](BR1_TS150/TC-041278.md) |
| TC-041279 | BR1_TS150_TC3 | Invalid state transition: Purchase/Capture -> Purchase Reversal -> Purchase Reversal again (already closed) -> declined | **BLOCKED** | [TC-041279.md](BR1_TS150/TC-041279.md) |
| TC-041280 | BR1_TS150_TC4 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-041280.md](BR1_TS150/TC-041280.md) |
| TC-041281 | BR1_TS150_TC5 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041281.md](BR1_TS150/TC-041281.md) |
| TC-041282 | BR1_TS150_TC6 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041282.md](BR1_TS150/TC-041282.md) |

<a id="br1_ts151"></a>
## BR1_TS151 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041283 | BR1_TS151_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041283.md](BR1_TS151/TC-041283.md) |
| TC-041284 | BR1_TS151_TC2 | Invalid state transition: Purchase Reversal with no original -> declined | **BLOCKED** | [TC-041284.md](BR1_TS151/TC-041284.md) |
| TC-041285 | BR1_TS151_TC3 | Invalid state transition: Purchase/Capture -> Purchase Reversal -> Purchase Reversal again (already closed) -> declined | **BLOCKED** | [TC-041285.md](BR1_TS151/TC-041285.md) |
| TC-041286 | BR1_TS151_TC4 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-041286.md](BR1_TS151/TC-041286.md) |
| TC-041287 | BR1_TS151_TC5 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041287.md](BR1_TS151/TC-041287.md) |
| TC-041288 | BR1_TS151_TC6 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041288.md](BR1_TS151/TC-041288.md) |

<a id="br1_ts152"></a>
## BR1_TS152 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041289 | BR1_TS152_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041289.md](BR1_TS152/TC-041289.md) |
| TC-041290 | BR1_TS152_TC2 | Invalid state transition: Purchase Reversal with no original -> declined | **BLOCKED** | [TC-041290.md](BR1_TS152/TC-041290.md) |
| TC-041291 | BR1_TS152_TC3 | Invalid state transition: Purchase/Capture -> Purchase Reversal -> Purchase Reversal again (already closed) -> declined | **BLOCKED** | [TC-041291.md](BR1_TS152/TC-041291.md) |
| TC-041292 | BR1_TS152_TC4 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-041292.md](BR1_TS152/TC-041292.md) |
| TC-041293 | BR1_TS152_TC5 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041293.md](BR1_TS152/TC-041293.md) |
| TC-041294 | BR1_TS152_TC6 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041294.md](BR1_TS152/TC-041294.md) |

<a id="br1_ts153"></a>
## BR1_TS153 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041295 | BR1_TS153_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041295.md](BR1_TS153/TC-041295.md) |
| TC-041296 | BR1_TS153_TC2 | Invalid state transition: Purchase Reversal with no original -> declined | **BLOCKED** | [TC-041296.md](BR1_TS153/TC-041296.md) |
| TC-041297 | BR1_TS153_TC3 | Invalid state transition: Purchase/Capture -> Purchase Reversal -> Purchase Reversal again (already closed) -> declined | **BLOCKED** | [TC-041297.md](BR1_TS153/TC-041297.md) |
| TC-041298 | BR1_TS153_TC4 | Interaction (pairwise): Purchase/Capture on a POS terminal with offline pin | **BLOCKED** | [TC-041298.md](BR1_TS153/TC-041298.md) |
| TC-041299 | BR1_TS153_TC5 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041299.md](BR1_TS153/TC-041299.md) |
| TC-041300 | BR1_TS153_TC6 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041300.md](BR1_TS153/TC-041300.md) |

<a id="br1_ts154"></a>
## BR1_TS154 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038618 | BR1_TS154_TC1 | Purchase Code exactly at its documented maximum length (16 bytes) is accepted | **FAIL** | [TC-038618.md](BR1_TS154/TC-038618.md) |
| TC-038623 | BR1_TS154_TC2 | Customer Data one byte over its documented maximum length (12) | **FAIL** | [TC-038623.md](BR1_TS154/TC-038623.md) |
| TC-038609 | BR1_TS154_TC3 | Fleet Data Segment (segment 101) omitted where p.23 requires it | **FAIL** | [TC-038609.md](BR1_TS154/TC-038609.md) |
| TC-038610 | BR1_TS154_TC4 | Purchase Card Data Segment (segment 104) omitted where p.24 requires it | **FAIL** | [TC-038610.md](BR1_TS154/TC-038610.md) |
| TC-041301 | BR1_TS154_TC5 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041301.md](BR1_TS154/TC-041301.md) |
| TC-041302 | BR1_TS154_TC6 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-041302.md](BR1_TS154/TC-041302.md) |
| TC-041303 | BR1_TS154_TC7 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-041303.md](BR1_TS154/TC-041303.md) |
| TC-041304 | BR1_TS154_TC8 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-041304.md](BR1_TS154/TC-041304.md) |
| TC-041305 | BR1_TS154_TC9 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-041305.md](BR1_TS154/TC-041305.md) |
| TC-041306 | BR1_TS154_TC10 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-041306.md](BR1_TS154/TC-041306.md) |
| TC-041307 | BR1_TS154_TC11 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-041307.md](BR1_TS154/TC-041307.md) |
| TC-041308 | BR1_TS154_TC12 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041308.md](BR1_TS154/TC-041308.md) |
| TC-041309 | BR1_TS154_TC13 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-041309.md](BR1_TS154/TC-041309.md) |
| TC-041310 | BR1_TS154_TC14 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-041310.md](BR1_TS154/TC-041310.md) |
| TC-041311 | BR1_TS154_TC15 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-041311.md](BR1_TS154/TC-041311.md) |
| TC-041312 | BR1_TS154_TC16 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-041312.md](BR1_TS154/TC-041312.md) |
| TC-041313 | BR1_TS154_TC17 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-041313.md](BR1_TS154/TC-041313.md) |
| TC-041314 | BR1_TS154_TC18 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-041314.md](BR1_TS154/TC-041314.md) |
| TC-041315 | BR1_TS154_TC19 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-041315.md](BR1_TS154/TC-041315.md) |
| TC-041316 | BR1_TS154_TC20 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-041316.md](BR1_TS154/TC-041316.md) |
| TC-041317 | BR1_TS154_TC21 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-041317.md](BR1_TS154/TC-041317.md) |
| TC-041318 | BR1_TS154_TC22 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-041318.md](BR1_TS154/TC-041318.md) |
| TC-041319 | BR1_TS154_TC23 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-041319.md](BR1_TS154/TC-041319.md) |
| TC-041320 | BR1_TS154_TC24 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-041320.md](BR1_TS154/TC-041320.md) |
| TC-041321 | BR1_TS154_TC25 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-041321.md](BR1_TS154/TC-041321.md) |
| TC-041322 | BR1_TS154_TC26 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-041322.md](BR1_TS154/TC-041322.md) |
| TC-041323 | BR1_TS154_TC27 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-041323.md](BR1_TS154/TC-041323.md) |
| TC-041324 | BR1_TS154_TC28 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-041324.md](BR1_TS154/TC-041324.md) |
| TC-041325 | BR1_TS154_TC29 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-041325.md](BR1_TS154/TC-041325.md) |
| TC-041326 | BR1_TS154_TC30 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041326.md](BR1_TS154/TC-041326.md) |
| TC-041327 | BR1_TS154_TC31 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-041327.md](BR1_TS154/TC-041327.md) |
| TC-041328 | BR1_TS154_TC32 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-041328.md](BR1_TS154/TC-041328.md) |
| TC-041329 | BR1_TS154_TC33 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041329.md](BR1_TS154/TC-041329.md) |
| TC-041330 | BR1_TS154_TC34 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041330.md](BR1_TS154/TC-041330.md) |
| TC-041331 | BR1_TS154_TC35 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041331.md](BR1_TS154/TC-041331.md) |
| TC-041332 | BR1_TS154_TC36 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041332.md](BR1_TS154/TC-041332.md) |
| TC-041333 | BR1_TS154_TC37 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041333.md](BR1_TS154/TC-041333.md) |
| TC-041334 | BR1_TS154_TC38 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041334.md](BR1_TS154/TC-041334.md) |
| TC-041335 | BR1_TS154_TC39 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041335.md](BR1_TS154/TC-041335.md) |
| TC-041336 | BR1_TS154_TC40 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041336.md](BR1_TS154/TC-041336.md) |
| TC-041337 | BR1_TS154_TC41 | Decision table: Authorization Only with optional segment 130 present | **BLOCKED** | [TC-041337.md](BR1_TS154/TC-041337.md) |
| TC-041338 | BR1_TS154_TC42 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041338.md](BR1_TS154/TC-041338.md) |
| TC-041339 | BR1_TS154_TC43 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041339.md](BR1_TS154/TC-041339.md) |
| TC-041340 | BR1_TS154_TC44 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-041340.md](BR1_TS154/TC-041340.md) |
| TC-041341 | BR1_TS154_TC45 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-041341.md](BR1_TS154/TC-041341.md) |
| TC-041342 | BR1_TS154_TC46 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041342.md](BR1_TS154/TC-041342.md) |
| TC-041343 | BR1_TS154_TC47 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041343.md](BR1_TS154/TC-041343.md) |
| TC-041344 | BR1_TS154_TC48 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041344.md](BR1_TS154/TC-041344.md) |
| TC-041345 | BR1_TS154_TC49 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041345.md](BR1_TS154/TC-041345.md) |
| TC-041346 | BR1_TS154_TC50 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041346.md](BR1_TS154/TC-041346.md) |
| TC-041347 | BR1_TS154_TC51 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041347.md](BR1_TS154/TC-041347.md) |
| TC-041348 | BR1_TS154_TC52 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041348.md](BR1_TS154/TC-041348.md) |
| TC-041349 | BR1_TS154_TC53 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.44-209) | **BLOCKED** | [TC-041349.md](BR1_TS154/TC-041349.md) |
| TC-041350 | BR1_TS154_TC54 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.89-176) | **BLOCKED** | [TC-041350.md](BR1_TS154/TC-041350.md) |
| TC-041351 | BR1_TS154_TC55 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041351.md](BR1_TS154/TC-041351.md) |
| TC-041352 | BR1_TS154_TC56 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041352.md](BR1_TS154/TC-041352.md) |
| TC-041353 | BR1_TS154_TC57 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041353.md](BR1_TS154/TC-041353.md) |
| TC-041354 | BR1_TS154_TC58 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041354.md](BR1_TS154/TC-041354.md) |
| TC-041355 | BR1_TS154_TC59 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041355.md](BR1_TS154/TC-041355.md) |
| TC-041356 | BR1_TS154_TC60 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041356.md](BR1_TS154/TC-041356.md) |
| TC-041357 | BR1_TS154_TC61 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041357.md](BR1_TS154/TC-041357.md) |
| TC-041358 | BR1_TS154_TC62 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041358.md](BR1_TS154/TC-041358.md) |

<a id="br1_ts155"></a>
## BR1_TS155 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038616 | BR1_TS155_TC1 | Fuel Purchase Amount one byte over its documented maximum length (8) | **FAIL** | [TC-038616.md](BR1_TS155/TC-038616.md) |
| TC-041359 | BR1_TS155_TC2 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041359.md](BR1_TS155/TC-041359.md) |
| TC-041360 | BR1_TS155_TC3 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041360.md](BR1_TS155/TC-041360.md) |
| TC-041361 | BR1_TS155_TC4 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041361.md](BR1_TS155/TC-041361.md) |
| TC-041362 | BR1_TS155_TC5 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041362.md](BR1_TS155/TC-041362.md) |
| TC-041363 | BR1_TS155_TC6 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041363.md](BR1_TS155/TC-041363.md) |
| TC-041364 | BR1_TS155_TC7 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041364.md](BR1_TS155/TC-041364.md) |
| TC-041365 | BR1_TS155_TC8 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041365.md](BR1_TS155/TC-041365.md) |
| TC-041366 | BR1_TS155_TC9 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041366.md](BR1_TS155/TC-041366.md) |
| TC-041367 | BR1_TS155_TC10 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041367.md](BR1_TS155/TC-041367.md) |
| TC-041368 | BR1_TS155_TC11 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041368.md](BR1_TS155/TC-041368.md) |
| TC-041369 | BR1_TS155_TC12 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041369.md](BR1_TS155/TC-041369.md) |
| TC-041370 | BR1_TS155_TC13 | Decision table: Authorization Only with optional segment 130 present | **BLOCKED** | [TC-041370.md](BR1_TS155/TC-041370.md) |
| TC-041371 | BR1_TS155_TC14 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041371.md](BR1_TS155/TC-041371.md) |
| TC-041372 | BR1_TS155_TC15 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041372.md](BR1_TS155/TC-041372.md) |
| TC-041373 | BR1_TS155_TC16 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041373.md](BR1_TS155/TC-041373.md) |
| TC-041374 | BR1_TS155_TC17 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041374.md](BR1_TS155/TC-041374.md) |
| TC-041375 | BR1_TS155_TC18 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041375.md](BR1_TS155/TC-041375.md) |
| TC-041376 | BR1_TS155_TC19 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041376.md](BR1_TS155/TC-041376.md) |
| TC-041377 | BR1_TS155_TC20 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041377.md](BR1_TS155/TC-041377.md) |
| TC-041378 | BR1_TS155_TC21 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041378.md](BR1_TS155/TC-041378.md) |
| TC-041379 | BR1_TS155_TC22 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041379.md](BR1_TS155/TC-041379.md) |
| TC-041380 | BR1_TS155_TC23 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.44-209) | **BLOCKED** | [TC-041380.md](BR1_TS155/TC-041380.md) |
| TC-041381 | BR1_TS155_TC24 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.89-176) | **BLOCKED** | [TC-041381.md](BR1_TS155/TC-041381.md) |
| TC-041382 | BR1_TS155_TC25 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041382.md](BR1_TS155/TC-041382.md) |
| TC-041383 | BR1_TS155_TC26 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041383.md](BR1_TS155/TC-041383.md) |
| TC-041384 | BR1_TS155_TC27 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041384.md](BR1_TS155/TC-041384.md) |
| TC-041385 | BR1_TS155_TC28 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041385.md](BR1_TS155/TC-041385.md) |
| TC-041386 | BR1_TS155_TC29 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041386.md](BR1_TS155/TC-041386.md) |
| TC-041387 | BR1_TS155_TC30 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041387.md](BR1_TS155/TC-041387.md) |
| TC-041388 | BR1_TS155_TC31 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041388.md](BR1_TS155/TC-041388.md) |
| TC-041389 | BR1_TS155_TC32 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041389.md](BR1_TS155/TC-041389.md) |

<a id="br1_ts156"></a>
## BR1_TS156 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041390 | BR1_TS156_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041390.md](BR1_TS156/TC-041390.md) |
| TC-041391 | BR1_TS156_TC2 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041391.md](BR1_TS156/TC-041391.md) |
| TC-041392 | BR1_TS156_TC3 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041392.md](BR1_TS156/TC-041392.md) |
| TC-041393 | BR1_TS156_TC4 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041393.md](BR1_TS156/TC-041393.md) |
| TC-041394 | BR1_TS156_TC5 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041394.md](BR1_TS156/TC-041394.md) |
| TC-041395 | BR1_TS156_TC6 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041395.md](BR1_TS156/TC-041395.md) |
| TC-041396 | BR1_TS156_TC7 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041396.md](BR1_TS156/TC-041396.md) |
| TC-041397 | BR1_TS156_TC8 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041397.md](BR1_TS156/TC-041397.md) |
| TC-041398 | BR1_TS156_TC9 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041398.md](BR1_TS156/TC-041398.md) |
| TC-041399 | BR1_TS156_TC10 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041399.md](BR1_TS156/TC-041399.md) |
| TC-041400 | BR1_TS156_TC11 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041400.md](BR1_TS156/TC-041400.md) |
| TC-041401 | BR1_TS156_TC12 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041401.md](BR1_TS156/TC-041401.md) |
| TC-041402 | BR1_TS156_TC13 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041402.md](BR1_TS156/TC-041402.md) |
| TC-041403 | BR1_TS156_TC14 | Soft constraint: Device behaviour - EMV Request Data Segment (segment 130) - FISERV-EMV-GUIDE | **DEVICE** | [TC-041403.md](BR1_TS156/TC-041403.md) |
| TC-041404 | BR1_TS156_TC15 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041404.md](BR1_TS156/TC-041404.md) |
| TC-041405 | BR1_TS156_TC16 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041405.md](BR1_TS156/TC-041405.md) |
| TC-041406 | BR1_TS156_TC17 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041406.md](BR1_TS156/TC-041406.md) |
| TC-041407 | BR1_TS156_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041407.md](BR1_TS156/TC-041407.md) |
| TC-041408 | BR1_TS156_TC19 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041408.md](BR1_TS156/TC-041408.md) |
| TC-041409 | BR1_TS156_TC20 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041409.md](BR1_TS156/TC-041409.md) |
| TC-041410 | BR1_TS156_TC21 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041410.md](BR1_TS156/TC-041410.md) |
| TC-041411 | BR1_TS156_TC22 | Running total / state: Running total / state check: Moneris Day End Batch Balance Segment Format (Request) (segment 139) | **DEVICE** | [TC-041411.md](BR1_TS156/TC-041411.md) |
| TC-041412 | BR1_TS156_TC23 | Arithmetic consistency: Arithmetic consistency check: Product Code Data Segment (segment 102) | **DEVICE** | [TC-041412.md](BR1_TS156/TC-041412.md) |
| TC-041413 | BR1_TS156_TC24 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041413.md](BR1_TS156/TC-041413.md) |
| TC-041414 | BR1_TS156_TC25 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041414.md](BR1_TS156/TC-041414.md) |
| TC-041415 | BR1_TS156_TC26 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041415.md](BR1_TS156/TC-041415.md) |
| TC-041416 | BR1_TS156_TC27 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041416.md](BR1_TS156/TC-041416.md) |
| TC-041417 | BR1_TS156_TC28 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041417.md](BR1_TS156/TC-041417.md) |
| TC-041418 | BR1_TS156_TC29 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041418.md](BR1_TS156/TC-041418.md) |
| TC-041419 | BR1_TS156_TC30 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041419.md](BR1_TS156/TC-041419.md) |
| TC-041420 | BR1_TS156_TC31 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041420.md](BR1_TS156/TC-041420.md) |
| TC-041421 | BR1_TS156_TC32 | Device checkpoint: Device behaviour - 12 checks p.104-128 - BUYPASS-PIPS | **DEVICE** | [TC-041421.md](BR1_TS156/TC-041421.md) |
| TC-041422 | BR1_TS156_TC33 | Device checkpoint: Device behaviour - 12 checks p.129-134 - BUYPASS-PIPS | **DEVICE** | [TC-041422.md](BR1_TS156/TC-041422.md) |
| TC-041423 | BR1_TS156_TC34 | Device checkpoint: Device behaviour - 12 checks p.136-180 - BUYPASS-PIPS | **DEVICE** | [TC-041423.md](BR1_TS156/TC-041423.md) |
| TC-041424 | BR1_TS156_TC35 | Device checkpoint: Device behaviour - 12 checks p.16-28 - BUYPASS-PIPS | **DEVICE** | [TC-041424.md](BR1_TS156/TC-041424.md) |
| TC-041425 | BR1_TS156_TC36 | Device checkpoint: Device behaviour - 12 checks p.29-41 - BUYPASS-PIPS | **DEVICE** | [TC-041425.md](BR1_TS156/TC-041425.md) |
| TC-041426 | BR1_TS156_TC37 | Device checkpoint: Device behaviour - 12 checks p.43-52 - BUYPASS-PIPS | **DEVICE** | [TC-041426.md](BR1_TS156/TC-041426.md) |
| TC-041427 | BR1_TS156_TC38 | Device checkpoint: Device behaviour - 12 checks p.53-69 - BUYPASS-PIPS | **DEVICE** | [TC-041427.md](BR1_TS156/TC-041427.md) |
| TC-041428 | BR1_TS156_TC39 | Device checkpoint: Device behaviour - 12 checks p.69-73 - BUYPASS-PIPS | **DEVICE** | [TC-041428.md](BR1_TS156/TC-041428.md) |
| TC-041429 | BR1_TS156_TC40 | Device checkpoint: Device behaviour - 12 checks p.74-95 - BUYPASS-PIPS | **DEVICE** | [TC-041429.md](BR1_TS156/TC-041429.md) |
| TC-041430 | BR1_TS156_TC41 | Device checkpoint: Device behaviour - 12 checks p.95-104 - BUYPASS-PIPS | **DEVICE** | [TC-041430.md](BR1_TS156/TC-041430.md) |
| TC-041431 | BR1_TS156_TC42 | Device checkpoint: Device behaviour - 2 checks p.183 - BUYPASS-PIPS | **DEVICE** | [TC-041431.md](BR1_TS156/TC-041431.md) |
| TC-041432 | BR1_TS156_TC43 | Device checkpoint: Device checkpoint check: Service Level | **DEVICE** | [TC-041432.md](BR1_TS156/TC-041432.md) |

<a id="br1_ts157"></a>
## BR1_TS157 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038619 | BR1_TS157_TC1 | Required field Information Byte omitted | **FAIL** | [TC-038619.md](BR1_TS157/TC-038619.md) |
| TC-041433 | BR1_TS157_TC2 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041433.md](BR1_TS157/TC-041433.md) |
| TC-041434 | BR1_TS157_TC3 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041434.md](BR1_TS157/TC-041434.md) |
| TC-041435 | BR1_TS157_TC4 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041435.md](BR1_TS157/TC-041435.md) |
| TC-041436 | BR1_TS157_TC5 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041436.md](BR1_TS157/TC-041436.md) |
| TC-041437 | BR1_TS157_TC6 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041437.md](BR1_TS157/TC-041437.md) |
| TC-041438 | BR1_TS157_TC7 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041438.md](BR1_TS157/TC-041438.md) |
| TC-041439 | BR1_TS157_TC8 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041439.md](BR1_TS157/TC-041439.md) |
| TC-041440 | BR1_TS157_TC9 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041440.md](BR1_TS157/TC-041440.md) |
| TC-041441 | BR1_TS157_TC10 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041441.md](BR1_TS157/TC-041441.md) |
| TC-041442 | BR1_TS157_TC11 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041442.md](BR1_TS157/TC-041442.md) |
| TC-041443 | BR1_TS157_TC12 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041443.md](BR1_TS157/TC-041443.md) |
| TC-041444 | BR1_TS157_TC13 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041444.md](BR1_TS157/TC-041444.md) |
| TC-041445 | BR1_TS157_TC14 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041445.md](BR1_TS157/TC-041445.md) |
| TC-041446 | BR1_TS157_TC15 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041446.md](BR1_TS157/TC-041446.md) |
| TC-041447 | BR1_TS157_TC16 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041447.md](BR1_TS157/TC-041447.md) |
| TC-041448 | BR1_TS157_TC17 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041448.md](BR1_TS157/TC-041448.md) |
| TC-041449 | BR1_TS157_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041449.md](BR1_TS157/TC-041449.md) |
| TC-041450 | BR1_TS157_TC19 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041450.md](BR1_TS157/TC-041450.md) |
| TC-041451 | BR1_TS157_TC20 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041451.md](BR1_TS157/TC-041451.md) |
| TC-041452 | BR1_TS157_TC21 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041452.md](BR1_TS157/TC-041452.md) |
| TC-041453 | BR1_TS157_TC22 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041453.md](BR1_TS157/TC-041453.md) |
| TC-041454 | BR1_TS157_TC23 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041454.md](BR1_TS157/TC-041454.md) |
| TC-041455 | BR1_TS157_TC24 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041455.md](BR1_TS157/TC-041455.md) |
| TC-041456 | BR1_TS157_TC25 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041456.md](BR1_TS157/TC-041456.md) |
| TC-041457 | BR1_TS157_TC26 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041457.md](BR1_TS157/TC-041457.md) |
| TC-041458 | BR1_TS157_TC27 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041458.md](BR1_TS157/TC-041458.md) |
| TC-041459 | BR1_TS157_TC28 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041459.md](BR1_TS157/TC-041459.md) |
| TC-041460 | BR1_TS157_TC29 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041460.md](BR1_TS157/TC-041460.md) |

<a id="br1_ts158"></a>
## BR1_TS158 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038638 | BR1_TS158_TC1 | Loyalty Card Data Segment (segment 108) omitted where p.73 requires it | **FAIL** | [TC-038638.md](BR1_TS158/TC-038638.md) |
| TC-041461 | BR1_TS158_TC2 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041461.md](BR1_TS158/TC-041461.md) |
| TC-041462 | BR1_TS158_TC3 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041462.md](BR1_TS158/TC-041462.md) |
| TC-041463 | BR1_TS158_TC4 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041463.md](BR1_TS158/TC-041463.md) |
| TC-041464 | BR1_TS158_TC5 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041464.md](BR1_TS158/TC-041464.md) |
| TC-041465 | BR1_TS158_TC6 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041465.md](BR1_TS158/TC-041465.md) |
| TC-041466 | BR1_TS158_TC7 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041466.md](BR1_TS158/TC-041466.md) |
| TC-041467 | BR1_TS158_TC8 | Alternate path: Authorization Only via alternate path: rule BR-BUYPASS-PIPS-63-1 (p.63) | **BLOCKED** | [TC-041467.md](BR1_TS158/TC-041467.md) |
| TC-041468 | BR1_TS158_TC9 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041468.md](BR1_TS158/TC-041468.md) |
| TC-041469 | BR1_TS158_TC10 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041469.md](BR1_TS158/TC-041469.md) |
| TC-041470 | BR1_TS158_TC11 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041470.md](BR1_TS158/TC-041470.md) |
| TC-041471 | BR1_TS158_TC12 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041471.md](BR1_TS158/TC-041471.md) |
| TC-041472 | BR1_TS158_TC13 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041472.md](BR1_TS158/TC-041472.md) |
| TC-041473 | BR1_TS158_TC14 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041473.md](BR1_TS158/TC-041473.md) |
| TC-041474 | BR1_TS158_TC15 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041474.md](BR1_TS158/TC-041474.md) |
| TC-041475 | BR1_TS158_TC16 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041475.md](BR1_TS158/TC-041475.md) |
| TC-041476 | BR1_TS158_TC17 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041476.md](BR1_TS158/TC-041476.md) |
| TC-041477 | BR1_TS158_TC18 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041477.md](BR1_TS158/TC-041477.md) |
| TC-041478 | BR1_TS158_TC19 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041478.md](BR1_TS158/TC-041478.md) |
| TC-041479 | BR1_TS158_TC20 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041479.md](BR1_TS158/TC-041479.md) |
| TC-041480 | BR1_TS158_TC21 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041480.md](BR1_TS158/TC-041480.md) |
| TC-041481 | BR1_TS158_TC22 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041481.md](BR1_TS158/TC-041481.md) |
| TC-041482 | BR1_TS158_TC23 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041482.md](BR1_TS158/TC-041482.md) |
| TC-041483 | BR1_TS158_TC24 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041483.md](BR1_TS158/TC-041483.md) |
| TC-041484 | BR1_TS158_TC25 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041484.md](BR1_TS158/TC-041484.md) |
| TC-041485 | BR1_TS158_TC26 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041485.md](BR1_TS158/TC-041485.md) |
| TC-041486 | BR1_TS158_TC27 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041486.md](BR1_TS158/TC-041486.md) |
| TC-041487 | BR1_TS158_TC28 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041487.md](BR1_TS158/TC-041487.md) |
| TC-041488 | BR1_TS158_TC29 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041488.md](BR1_TS158/TC-041488.md) |
| TC-041489 | BR1_TS158_TC30 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041489.md](BR1_TS158/TC-041489.md) |

<a id="br1_ts159"></a>
## BR1_TS159 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041490 | BR1_TS159_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041490.md](BR1_TS159/TC-041490.md) |
| TC-041491 | BR1_TS159_TC2 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041491.md](BR1_TS159/TC-041491.md) |
| TC-041492 | BR1_TS159_TC3 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041492.md](BR1_TS159/TC-041492.md) |
| TC-041493 | BR1_TS159_TC4 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041493.md](BR1_TS159/TC-041493.md) |
| TC-041494 | BR1_TS159_TC5 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041494.md](BR1_TS159/TC-041494.md) |
| TC-041495 | BR1_TS159_TC6 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041495.md](BR1_TS159/TC-041495.md) |
| TC-041496 | BR1_TS159_TC7 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041496.md](BR1_TS159/TC-041496.md) |
| TC-041497 | BR1_TS159_TC8 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041497.md](BR1_TS159/TC-041497.md) |
| TC-041498 | BR1_TS159_TC9 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041498.md](BR1_TS159/TC-041498.md) |
| TC-041499 | BR1_TS159_TC10 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041499.md](BR1_TS159/TC-041499.md) |
| TC-041500 | BR1_TS159_TC11 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041500.md](BR1_TS159/TC-041500.md) |
| TC-041501 | BR1_TS159_TC12 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041501.md](BR1_TS159/TC-041501.md) |
| TC-041502 | BR1_TS159_TC13 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041502.md](BR1_TS159/TC-041502.md) |
| TC-041503 | BR1_TS159_TC14 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041503.md](BR1_TS159/TC-041503.md) |
| TC-041504 | BR1_TS159_TC15 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041504.md](BR1_TS159/TC-041504.md) |
| TC-041505 | BR1_TS159_TC16 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041505.md](BR1_TS159/TC-041505.md) |
| TC-041506 | BR1_TS159_TC17 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041506.md](BR1_TS159/TC-041506.md) |
| TC-041507 | BR1_TS159_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041507.md](BR1_TS159/TC-041507.md) |
| TC-041508 | BR1_TS159_TC19 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041508.md](BR1_TS159/TC-041508.md) |
| TC-041509 | BR1_TS159_TC20 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041509.md](BR1_TS159/TC-041509.md) |
| TC-041510 | BR1_TS159_TC21 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041510.md](BR1_TS159/TC-041510.md) |
| TC-041511 | BR1_TS159_TC22 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041511.md](BR1_TS159/TC-041511.md) |
| TC-041512 | BR1_TS159_TC23 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041512.md](BR1_TS159/TC-041512.md) |
| TC-041513 | BR1_TS159_TC24 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041513.md](BR1_TS159/TC-041513.md) |
| TC-041514 | BR1_TS159_TC25 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041514.md](BR1_TS159/TC-041514.md) |
| TC-041515 | BR1_TS159_TC26 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041515.md](BR1_TS159/TC-041515.md) |
| TC-041516 | BR1_TS159_TC27 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041516.md](BR1_TS159/TC-041516.md) |
| TC-041517 | BR1_TS159_TC28 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041517.md](BR1_TS159/TC-041517.md) |

<a id="br1_ts160"></a>
## BR1_TS160 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041518 | BR1_TS160_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041518.md](BR1_TS160/TC-041518.md) |
| TC-041519 | BR1_TS160_TC2 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041519.md](BR1_TS160/TC-041519.md) |
| TC-041520 | BR1_TS160_TC3 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041520.md](BR1_TS160/TC-041520.md) |
| TC-041521 | BR1_TS160_TC4 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041521.md](BR1_TS160/TC-041521.md) |
| TC-041522 | BR1_TS160_TC5 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041522.md](BR1_TS160/TC-041522.md) |
| TC-041523 | BR1_TS160_TC6 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041523.md](BR1_TS160/TC-041523.md) |
| TC-041524 | BR1_TS160_TC7 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041524.md](BR1_TS160/TC-041524.md) |
| TC-041525 | BR1_TS160_TC8 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041525.md](BR1_TS160/TC-041525.md) |
| TC-041526 | BR1_TS160_TC9 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041526.md](BR1_TS160/TC-041526.md) |
| TC-041527 | BR1_TS160_TC10 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041527.md](BR1_TS160/TC-041527.md) |
| TC-041528 | BR1_TS160_TC11 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041528.md](BR1_TS160/TC-041528.md) |
| TC-041529 | BR1_TS160_TC12 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041529.md](BR1_TS160/TC-041529.md) |
| TC-041530 | BR1_TS160_TC13 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041530.md](BR1_TS160/TC-041530.md) |
| TC-041531 | BR1_TS160_TC14 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041531.md](BR1_TS160/TC-041531.md) |
| TC-041532 | BR1_TS160_TC15 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041532.md](BR1_TS160/TC-041532.md) |
| TC-041533 | BR1_TS160_TC16 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041533.md](BR1_TS160/TC-041533.md) |
| TC-041534 | BR1_TS160_TC17 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041534.md](BR1_TS160/TC-041534.md) |
| TC-041535 | BR1_TS160_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041535.md](BR1_TS160/TC-041535.md) |
| TC-041536 | BR1_TS160_TC19 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041536.md](BR1_TS160/TC-041536.md) |
| TC-041537 | BR1_TS160_TC20 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041537.md](BR1_TS160/TC-041537.md) |
| TC-041538 | BR1_TS160_TC21 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041538.md](BR1_TS160/TC-041538.md) |
| TC-041539 | BR1_TS160_TC22 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041539.md](BR1_TS160/TC-041539.md) |
| TC-041540 | BR1_TS160_TC23 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041540.md](BR1_TS160/TC-041540.md) |
| TC-041541 | BR1_TS160_TC24 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041541.md](BR1_TS160/TC-041541.md) |
| TC-041542 | BR1_TS160_TC25 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041542.md](BR1_TS160/TC-041542.md) |
| TC-041543 | BR1_TS160_TC26 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041543.md](BR1_TS160/TC-041543.md) |
| TC-041544 | BR1_TS160_TC27 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041544.md](BR1_TS160/TC-041544.md) |
| TC-041545 | BR1_TS160_TC28 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041545.md](BR1_TS160/TC-041545.md) |

<a id="br1_ts161"></a>
## BR1_TS161 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038615 | BR1_TS161_TC1 | Purchase Code one byte over its documented maximum length (16) | **FAIL** | [TC-038615.md](BR1_TS161/TC-038615.md) |
| TC-038620 | BR1_TS161_TC2 | Required field Information Byte sent empty | **FAIL** | [TC-038620.md](BR1_TS161/TC-038620.md) |
| TC-041546 | BR1_TS161_TC3 | Happy path: Merchandise Return approved | **BLOCKED** | [TC-041546.md](BR1_TS161/TC-041546.md) |
| TC-041547 | BR1_TS161_TC4 | Valid flow: Merchandise Return -> Void of a Merchandise Return | **BLOCKED** | [TC-041547.md](BR1_TS161/TC-041547.md) |
| TC-041548 | BR1_TS161_TC5 | Invalid state transition: Merchandise Return -> Void of a Merchandise Return -> Void of a Merchandise Return again (already closed) -> declined | **BLOCKED** | [TC-041548.md](BR1_TS161/TC-041548.md) |
| TC-041549 | BR1_TS161_TC6 | Invalid state transition: Void of a Merchandise Return with no original -> declined | **BLOCKED** | [TC-041549.md](BR1_TS161/TC-041549.md) |
| TC-041550 | BR1_TS161_TC7 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041550.md](BR1_TS161/TC-041550.md) |

<a id="br1_ts162"></a>
## BR1_TS162 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-038612 | BR1_TS162_TC1 | Service Level one byte over its documented maximum length (1) | **FAIL** | [TC-038612.md](BR1_TS162/TC-038612.md) |
| TC-038617 | BR1_TS162_TC2 | Account Number one byte over its documented maximum length (24) | **FAIL** | [TC-038617.md](BR1_TS162/TC-038617.md) |
| TC-041551 | BR1_TS162_TC3 | Invalid state transition: Merchandise Return -> Void of a Merchandise Return -> Void of a Merchandise Return again (already closed) -> declined | **BLOCKED** | [TC-041551.md](BR1_TS162/TC-041551.md) |
| TC-041552 | BR1_TS162_TC4 | Invalid state transition: Void of a Merchandise Return with no original -> declined | **BLOCKED** | [TC-041552.md](BR1_TS162/TC-041552.md) |
| TC-041553 | BR1_TS162_TC5 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041553.md](BR1_TS162/TC-041553.md) |

<a id="br1_ts163"></a>
## BR1_TS163 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041554 | BR1_TS163_TC1 | Invalid state transition: Merchandise Return -> Void of a Merchandise Return -> Void of a Merchandise Return again (already closed) -> declined | **BLOCKED** | [TC-041554.md](BR1_TS163/TC-041554.md) |
| TC-041555 | BR1_TS163_TC2 | Invalid state transition: Void of a Merchandise Return with no original -> declined | **BLOCKED** | [TC-041555.md](BR1_TS163/TC-041555.md) |
| TC-041556 | BR1_TS163_TC3 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041556.md](BR1_TS163/TC-041556.md) |

<a id="br1_ts164"></a>
## BR1_TS164 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041557 | BR1_TS164_TC1 | Invalid state transition: Merchandise Return -> Void of a Merchandise Return -> Void of a Merchandise Return again (already closed) -> declined | **BLOCKED** | [TC-041557.md](BR1_TS164/TC-041557.md) |
| TC-041558 | BR1_TS164_TC2 | Invalid state transition: Void of a Merchandise Return with no original -> declined | **BLOCKED** | [TC-041558.md](BR1_TS164/TC-041558.md) |
| TC-041559 | BR1_TS164_TC3 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041559.md](BR1_TS164/TC-041559.md) |

<a id="br1_ts165"></a>
## BR1_TS165 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041560 | BR1_TS165_TC1 | Invalid state transition: Merchandise Return -> Void of a Merchandise Return -> Void of a Merchandise Return again (already closed) -> declined | **BLOCKED** | [TC-041560.md](BR1_TS165/TC-041560.md) |
| TC-041561 | BR1_TS165_TC2 | Invalid state transition: Void of a Merchandise Return with no original -> declined | **BLOCKED** | [TC-041561.md](BR1_TS165/TC-041561.md) |
| TC-041562 | BR1_TS165_TC3 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041562.md](BR1_TS165/TC-041562.md) |

<a id="br1_ts166"></a>
## BR1_TS166 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041563 | BR1_TS166_TC1 | Invalid state transition: Merchandise Return -> Void of a Merchandise Return -> Void of a Merchandise Return again (already closed) -> declined | **BLOCKED** | [TC-041563.md](BR1_TS166/TC-041563.md) |
| TC-041564 | BR1_TS166_TC2 | Invalid state transition: Void of a Merchandise Return with no original -> declined | **BLOCKED** | [TC-041564.md](BR1_TS166/TC-041564.md) |
| TC-041565 | BR1_TS166_TC3 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041565.md](BR1_TS166/TC-041565.md) |

<a id="br1_ts167"></a>
## BR1_TS167 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Petroleum · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041566 | BR1_TS167_TC1 | Invalid state transition: Merchandise Return -> Void of a Merchandise Return -> Void of a Merchandise Return again (already closed) -> declined | **BLOCKED** | [TC-041566.md](BR1_TS167/TC-041566.md) |
| TC-041567 | BR1_TS167_TC2 | Invalid state transition: Void of a Merchandise Return with no original -> declined | **BLOCKED** | [TC-041567.md](BR1_TS167/TC-041567.md) |
| TC-041568 | BR1_TS167_TC3 | Interaction (pairwise): Merchandise Return on a POS terminal with offline pin | **BLOCKED** | [TC-041568.md](BR1_TS167/TC-041568.md) |

<a id="br1_ts168"></a>
## BR1_TS168 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041569 | BR1_TS168_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041569.md](BR1_TS168/TC-041569.md) |
| TC-041570 | BR1_TS168_TC2 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-041570.md](BR1_TS168/TC-041570.md) |
| TC-041571 | BR1_TS168_TC3 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-041571.md](BR1_TS168/TC-041571.md) |
| TC-041572 | BR1_TS168_TC4 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-041572.md](BR1_TS168/TC-041572.md) |
| TC-041573 | BR1_TS168_TC5 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-041573.md](BR1_TS168/TC-041573.md) |
| TC-041574 | BR1_TS168_TC6 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041574.md](BR1_TS168/TC-041574.md) |
| TC-041575 | BR1_TS168_TC7 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041575.md](BR1_TS168/TC-041575.md) |

<a id="br1_ts169"></a>
## BR1_TS169 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041576 | BR1_TS169_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041576.md](BR1_TS169/TC-041576.md) |
| TC-041577 | BR1_TS169_TC2 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-041577.md](BR1_TS169/TC-041577.md) |
| TC-041578 | BR1_TS169_TC3 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041578.md](BR1_TS169/TC-041578.md) |
| TC-041579 | BR1_TS169_TC4 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041579.md](BR1_TS169/TC-041579.md) |

<a id="br1_ts170"></a>
## BR1_TS170 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041580 | BR1_TS170_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041580.md](BR1_TS170/TC-041580.md) |
| TC-041581 | BR1_TS170_TC2 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-041581.md](BR1_TS170/TC-041581.md) |
| TC-041582 | BR1_TS170_TC3 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041582.md](BR1_TS170/TC-041582.md) |
| TC-041583 | BR1_TS170_TC4 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041583.md](BR1_TS170/TC-041583.md) |

<a id="br1_ts171"></a>
## BR1_TS171 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041584 | BR1_TS171_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041584.md](BR1_TS171/TC-041584.md) |
| TC-041585 | BR1_TS171_TC2 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-041585.md](BR1_TS171/TC-041585.md) |
| TC-041586 | BR1_TS171_TC3 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041586.md](BR1_TS171/TC-041586.md) |
| TC-041587 | BR1_TS171_TC4 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041587.md](BR1_TS171/TC-041587.md) |

<a id="br1_ts172"></a>
## BR1_TS172 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041588 | BR1_TS172_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041588.md](BR1_TS172/TC-041588.md) |
| TC-041589 | BR1_TS172_TC2 | Decision table: Purchase/Capture with incremental = completion | **BLOCKED** | [TC-041589.md](BR1_TS172/TC-041589.md) |
| TC-041590 | BR1_TS172_TC3 | Response / oracle check: Purchase/Capture -> Merchant Data Segment returned and checked (p.6-715) | **BLOCKED** | [TC-041590.md](BR1_TS172/TC-041590.md) |
| TC-041591 | BR1_TS172_TC4 | Response / oracle check: Purchase/Capture -> declined (issuer decline) | **BLOCKED** | [TC-041591.md](BR1_TS172/TC-041591.md) |

<a id="br1_ts173"></a>
## BR1_TS173 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041592 | BR1_TS173_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041592.md](BR1_TS173/TC-041592.md) |
| TC-041593 | BR1_TS173_TC2 | Happy path: Authorization Only sending Table 006 Currency Code Information | **BLOCKED** | [TC-041593.md](BR1_TS173/TC-041593.md) |
| TC-041594 | BR1_TS173_TC3 | Happy path: Authorization Only sending Table 008 Balance Information | **BLOCKED** | [TC-041594.md](BR1_TS173/TC-041594.md) |
| TC-041595 | BR1_TS173_TC4 | Happy path: Authorization Only sending Table 019 Recharge Type Information | **BLOCKED** | [TC-041595.md](BR1_TS173/TC-041595.md) |
| TC-041596 | BR1_TS173_TC5 | Happy path: Authorization Only sending Table 022 Originating Device Type Indicator Information | **FAIL** | [TC-041596.md](BR1_TS173/TC-041596.md) |
| TC-041597 | BR1_TS173_TC6 | Happy path: Authorization Only sending Table 027 Customer Name | **BLOCKED** | [TC-041597.md](BR1_TS173/TC-041597.md) |
| TC-041598 | BR1_TS173_TC7 | Happy path: Authorization Only sending Table 029 Universal Unique Identifier | **BLOCKED** | [TC-041598.md](BR1_TS173/TC-041598.md) |
| TC-041599 | BR1_TS173_TC8 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041599.md](BR1_TS173/TC-041599.md) |
| TC-041600 | BR1_TS173_TC9 | Happy path: Authorization Only sending Table 033 Re-Price Data Request Information | **BLOCKED** | [TC-041600.md](BR1_TS173/TC-041600.md) |
| TC-041601 | BR1_TS173_TC10 | Happy path: Authorization Only sending Table 037 MCX Checkout Token | **BLOCKED** | [TC-041601.md](BR1_TS173/TC-041601.md) |
| TC-041602 | BR1_TS173_TC11 | Happy path: Authorization Only sending Table 039 Fraud Enhanced Data | **BLOCKED** | [TC-041602.md](BR1_TS173/TC-041602.md) |
| TC-041603 | BR1_TS173_TC12 | Happy path: Authorization Only sending Table 041 Country Subdivision | **BLOCKED** | [TC-041603.md](BR1_TS173/TC-041603.md) |
| TC-041604 | BR1_TS173_TC13 | Happy path: Authorization Only sending Table 042 Cardholder Language | **BLOCKED** | [TC-041604.md](BR1_TS173/TC-041604.md) |
| TC-041605 | BR1_TS173_TC14 | Happy path: Authorization Only sending Table 046 Previous Transaction ID | **BLOCKED** | [TC-041605.md](BR1_TS173/TC-041605.md) |
| TC-041606 | BR1_TS173_TC15 | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | **FAIL** | [TC-041606.md](BR1_TS173/TC-041606.md) |
| TC-041607 | BR1_TS173_TC16 | Happy path: Authorization Only sending Table 050 Additional Security Code | **BLOCKED** | [TC-041607.md](BR1_TS173/TC-041607.md) |
| TC-041608 | BR1_TS173_TC17 | Happy path: Authorization Only sending Table 051 Additional Card ID | **BLOCKED** | [TC-041608.md](BR1_TS173/TC-041608.md) |
| TC-041609 | BR1_TS173_TC18 | Happy path: Authorization Only sending Table 052 Additional TransArmor Data | **BLOCKED** | [TC-041609.md](BR1_TS173/TC-041609.md) |
| TC-041610 | BR1_TS173_TC19 | Happy path: Authorization Only sending Table 053 Remote Commerce Acceptor Identifier | **BLOCKED** | [TC-041610.md](BR1_TS173/TC-041610.md) |
| TC-041611 | BR1_TS173_TC20 | Happy path: Authorization Only sending Table 054 Merchant Tran ID | **BLOCKED** | [TC-041611.md](BR1_TS173/TC-041611.md) |
| TC-041612 | BR1_TS173_TC21 | Happy path: Authorization Only sending Table 055 Experience Provider Value | **BLOCKED** | [TC-041612.md](BR1_TS173/TC-041612.md) |
| TC-041613 | BR1_TS173_TC22 | Happy path: Authorization Only sending Table 063 Visa®/MC/AMEX Incremental Auth Lodging Data | **BLOCKED** | [TC-041613.md](BR1_TS173/TC-041613.md) |
| TC-041614 | BR1_TS173_TC23 | Happy path: Authorization Only sending Table 064 Service Location Information | **BLOCKED** | [TC-041614.md](BR1_TS173/TC-041614.md) |
| TC-041615 | BR1_TS173_TC24 | Happy path: Authorization Only sending Table 065 Merchant Payment Gateway ID Data | **BLOCKED** | [TC-041615.md](BR1_TS173/TC-041615.md) |
| TC-041616 | BR1_TS173_TC25 | Happy path: Authorization Only sending Table 070 Anticipated Completion Amount | **BLOCKED** | [TC-041616.md](BR1_TS173/TC-041616.md) |
| TC-041617 | BR1_TS173_TC26 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041617.md](BR1_TS173/TC-041617.md) |
| TC-041618 | BR1_TS173_TC27 | Happy path: Authorization Only sending Table 075 Discount Program Data | **BLOCKED** | [TC-041618.md](BR1_TS173/TC-041618.md) |
| TC-041619 | BR1_TS173_TC28 | Happy path: Authorization Only sending Table 076 Discount Line-Item Data | **BLOCKED** | [TC-041619.md](BR1_TS173/TC-041619.md) |
| TC-041620 | BR1_TS173_TC29 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041620.md](BR1_TS173/TC-041620.md) |
| TC-041621 | BR1_TS173_TC30 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041621.md](BR1_TS173/TC-041621.md) |
| TC-041622 | BR1_TS173_TC31 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041622.md](BR1_TS173/TC-041622.md) |
| TC-041623 | BR1_TS173_TC32 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041623.md](BR1_TS173/TC-041623.md) |
| TC-041624 | BR1_TS173_TC33 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041624.md](BR1_TS173/TC-041624.md) |
| TC-041625 | BR1_TS173_TC34 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041625.md](BR1_TS173/TC-041625.md) |
| TC-041626 | BR1_TS173_TC35 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041626.md](BR1_TS173/TC-041626.md) |
| TC-041627 | BR1_TS173_TC36 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041627.md](BR1_TS173/TC-041627.md) |
| TC-041628 | BR1_TS173_TC37 | Decision table: Authorization Only with optional segment 130 present | **BLOCKED** | [TC-041628.md](BR1_TS173/TC-041628.md) |
| TC-041629 | BR1_TS173_TC38 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041629.md](BR1_TS173/TC-041629.md) |
| TC-041630 | BR1_TS173_TC39 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041630.md](BR1_TS173/TC-041630.md) |
| TC-041631 | BR1_TS173_TC40 | Valid flow: Authorization Only -> Cancellation | **BLOCKED** | [TC-041631.md](BR1_TS173/TC-041631.md) |
| TC-041632 | BR1_TS173_TC41 | Valid flow: Authorization Only partly paid -> remaining balance on a second card (split payment) | **BLOCKED** | [TC-041632.md](BR1_TS173/TC-041632.md) |
| TC-041633 | BR1_TS173_TC42 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041633.md](BR1_TS173/TC-041633.md) |
| TC-041634 | BR1_TS173_TC43 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041634.md](BR1_TS173/TC-041634.md) |
| TC-041635 | BR1_TS173_TC44 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041635.md](BR1_TS173/TC-041635.md) |
| TC-041636 | BR1_TS173_TC45 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041636.md](BR1_TS173/TC-041636.md) |
| TC-041637 | BR1_TS173_TC46 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041637.md](BR1_TS173/TC-041637.md) |
| TC-041638 | BR1_TS173_TC47 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041638.md](BR1_TS173/TC-041638.md) |
| TC-041639 | BR1_TS173_TC48 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041639.md](BR1_TS173/TC-041639.md) |
| TC-041640 | BR1_TS173_TC49 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-041640.md](BR1_TS173/TC-041640.md) |
| TC-041641 | BR1_TS173_TC50 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.44-209) | **BLOCKED** | [TC-041641.md](BR1_TS173/TC-041641.md) |
| TC-041642 | BR1_TS173_TC51 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.89-176) | **BLOCKED** | [TC-041642.md](BR1_TS173/TC-041642.md) |
| TC-041643 | BR1_TS173_TC52 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041643.md](BR1_TS173/TC-041643.md) |
| TC-041644 | BR1_TS173_TC53 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041644.md](BR1_TS173/TC-041644.md) |
| TC-041645 | BR1_TS173_TC54 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041645.md](BR1_TS173/TC-041645.md) |
| TC-041646 | BR1_TS173_TC55 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041646.md](BR1_TS173/TC-041646.md) |
| TC-041647 | BR1_TS173_TC56 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041647.md](BR1_TS173/TC-041647.md) |
| TC-041648 | BR1_TS173_TC57 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041648.md](BR1_TS173/TC-041648.md) |
| TC-041649 | BR1_TS173_TC58 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041649.md](BR1_TS173/TC-041649.md) |
| TC-041650 | BR1_TS173_TC59 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041650.md](BR1_TS173/TC-041650.md) |

<a id="br1_ts174"></a>
## BR1_TS174 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041651 | BR1_TS174_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041651.md](BR1_TS174/TC-041651.md) |
| TC-041652 | BR1_TS174_TC2 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041652.md](BR1_TS174/TC-041652.md) |
| TC-041653 | BR1_TS174_TC3 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041653.md](BR1_TS174/TC-041653.md) |
| TC-041654 | BR1_TS174_TC4 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041654.md](BR1_TS174/TC-041654.md) |
| TC-041655 | BR1_TS174_TC5 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041655.md](BR1_TS174/TC-041655.md) |
| TC-041656 | BR1_TS174_TC6 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041656.md](BR1_TS174/TC-041656.md) |
| TC-041657 | BR1_TS174_TC7 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041657.md](BR1_TS174/TC-041657.md) |
| TC-041658 | BR1_TS174_TC8 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041658.md](BR1_TS174/TC-041658.md) |
| TC-041659 | BR1_TS174_TC9 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041659.md](BR1_TS174/TC-041659.md) |
| TC-041660 | BR1_TS174_TC10 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041660.md](BR1_TS174/TC-041660.md) |
| TC-041661 | BR1_TS174_TC11 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041661.md](BR1_TS174/TC-041661.md) |
| TC-041662 | BR1_TS174_TC12 | Decision table: Authorization Only with optional segment 130 present | **BLOCKED** | [TC-041662.md](BR1_TS174/TC-041662.md) |
| TC-041663 | BR1_TS174_TC13 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041663.md](BR1_TS174/TC-041663.md) |
| TC-041664 | BR1_TS174_TC14 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041664.md](BR1_TS174/TC-041664.md) |
| TC-041665 | BR1_TS174_TC15 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041665.md](BR1_TS174/TC-041665.md) |
| TC-041666 | BR1_TS174_TC16 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041666.md](BR1_TS174/TC-041666.md) |
| TC-041667 | BR1_TS174_TC17 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041667.md](BR1_TS174/TC-041667.md) |
| TC-041668 | BR1_TS174_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041668.md](BR1_TS174/TC-041668.md) |
| TC-041669 | BR1_TS174_TC19 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041669.md](BR1_TS174/TC-041669.md) |
| TC-041670 | BR1_TS174_TC20 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041670.md](BR1_TS174/TC-041670.md) |
| TC-041671 | BR1_TS174_TC21 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041671.md](BR1_TS174/TC-041671.md) |
| TC-041672 | BR1_TS174_TC22 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-041672.md](BR1_TS174/TC-041672.md) |
| TC-041673 | BR1_TS174_TC23 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.44-209) | **BLOCKED** | [TC-041673.md](BR1_TS174/TC-041673.md) |
| TC-041674 | BR1_TS174_TC24 | Response / oracle check: Authorization Only -> EMV Response Data Segment returned and checked (p.89-176) | **BLOCKED** | [TC-041674.md](BR1_TS174/TC-041674.md) |
| TC-041675 | BR1_TS174_TC25 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041675.md](BR1_TS174/TC-041675.md) |
| TC-041676 | BR1_TS174_TC26 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041676.md](BR1_TS174/TC-041676.md) |
| TC-041677 | BR1_TS174_TC27 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041677.md](BR1_TS174/TC-041677.md) |
| TC-041678 | BR1_TS174_TC28 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041678.md](BR1_TS174/TC-041678.md) |
| TC-041679 | BR1_TS174_TC29 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041679.md](BR1_TS174/TC-041679.md) |
| TC-041680 | BR1_TS174_TC30 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041680.md](BR1_TS174/TC-041680.md) |
| TC-041681 | BR1_TS174_TC31 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041681.md](BR1_TS174/TC-041681.md) |
| TC-041682 | BR1_TS174_TC32 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041682.md](BR1_TS174/TC-041682.md) |

<a id="br1_ts175"></a>
## BR1_TS175 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041683 | BR1_TS175_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041683.md](BR1_TS175/TC-041683.md) |
| TC-041684 | BR1_TS175_TC2 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041684.md](BR1_TS175/TC-041684.md) |
| TC-041685 | BR1_TS175_TC3 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041685.md](BR1_TS175/TC-041685.md) |
| TC-041686 | BR1_TS175_TC4 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041686.md](BR1_TS175/TC-041686.md) |
| TC-041687 | BR1_TS175_TC5 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041687.md](BR1_TS175/TC-041687.md) |
| TC-041688 | BR1_TS175_TC6 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041688.md](BR1_TS175/TC-041688.md) |
| TC-041689 | BR1_TS175_TC7 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041689.md](BR1_TS175/TC-041689.md) |
| TC-041690 | BR1_TS175_TC8 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041690.md](BR1_TS175/TC-041690.md) |
| TC-041691 | BR1_TS175_TC9 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041691.md](BR1_TS175/TC-041691.md) |
| TC-041692 | BR1_TS175_TC10 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041692.md](BR1_TS175/TC-041692.md) |
| TC-041693 | BR1_TS175_TC11 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041693.md](BR1_TS175/TC-041693.md) |
| TC-041694 | BR1_TS175_TC12 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041694.md](BR1_TS175/TC-041694.md) |
| TC-041695 | BR1_TS175_TC13 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041695.md](BR1_TS175/TC-041695.md) |
| TC-041696 | BR1_TS175_TC14 | Soft constraint: Device behaviour - EMV Request Data Segment (segment 130) - FISERV-EMV-GUIDE | **DEVICE** | [TC-041696.md](BR1_TS175/TC-041696.md) |
| TC-041697 | BR1_TS175_TC15 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041697.md](BR1_TS175/TC-041697.md) |
| TC-041698 | BR1_TS175_TC16 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041698.md](BR1_TS175/TC-041698.md) |
| TC-041699 | BR1_TS175_TC17 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041699.md](BR1_TS175/TC-041699.md) |
| TC-041700 | BR1_TS175_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041700.md](BR1_TS175/TC-041700.md) |
| TC-041701 | BR1_TS175_TC19 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041701.md](BR1_TS175/TC-041701.md) |
| TC-041702 | BR1_TS175_TC20 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041702.md](BR1_TS175/TC-041702.md) |
| TC-041703 | BR1_TS175_TC21 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041703.md](BR1_TS175/TC-041703.md) |
| TC-041704 | BR1_TS175_TC22 | Running total / state: Running total / state check: Moneris Day End Batch Balance Segment Format (Request) (segment 139) | **DEVICE** | [TC-041704.md](BR1_TS175/TC-041704.md) |
| TC-041705 | BR1_TS175_TC23 | Arithmetic consistency: Arithmetic consistency check: Product Code Data Segment (segment 102) | **DEVICE** | [TC-041705.md](BR1_TS175/TC-041705.md) |
| TC-041706 | BR1_TS175_TC24 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-041706.md](BR1_TS175/TC-041706.md) |
| TC-041707 | BR1_TS175_TC25 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041707.md](BR1_TS175/TC-041707.md) |
| TC-041708 | BR1_TS175_TC26 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041708.md](BR1_TS175/TC-041708.md) |
| TC-041709 | BR1_TS175_TC27 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041709.md](BR1_TS175/TC-041709.md) |
| TC-041710 | BR1_TS175_TC28 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041710.md](BR1_TS175/TC-041710.md) |
| TC-041711 | BR1_TS175_TC29 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041711.md](BR1_TS175/TC-041711.md) |
| TC-041712 | BR1_TS175_TC30 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041712.md](BR1_TS175/TC-041712.md) |
| TC-041713 | BR1_TS175_TC31 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041713.md](BR1_TS175/TC-041713.md) |
| TC-041714 | BR1_TS175_TC32 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041714.md](BR1_TS175/TC-041714.md) |
| TC-041715 | BR1_TS175_TC33 | Device checkpoint: Device behaviour - 12 checks p.104-128 - BUYPASS-PIPS | **DEVICE** | [TC-041715.md](BR1_TS175/TC-041715.md) |
| TC-041716 | BR1_TS175_TC34 | Device checkpoint: Device behaviour - 12 checks p.129-134 - BUYPASS-PIPS | **DEVICE** | [TC-041716.md](BR1_TS175/TC-041716.md) |
| TC-041717 | BR1_TS175_TC35 | Device checkpoint: Device behaviour - 12 checks p.136-180 - BUYPASS-PIPS | **DEVICE** | [TC-041717.md](BR1_TS175/TC-041717.md) |
| TC-041718 | BR1_TS175_TC36 | Device checkpoint: Device behaviour - 12 checks p.16-28 - BUYPASS-PIPS | **DEVICE** | [TC-041718.md](BR1_TS175/TC-041718.md) |
| TC-041719 | BR1_TS175_TC37 | Device checkpoint: Device behaviour - 12 checks p.29-41 - BUYPASS-PIPS | **DEVICE** | [TC-041719.md](BR1_TS175/TC-041719.md) |
| TC-041720 | BR1_TS175_TC38 | Device checkpoint: Device behaviour - 12 checks p.43-52 - BUYPASS-PIPS | **DEVICE** | [TC-041720.md](BR1_TS175/TC-041720.md) |
| TC-041721 | BR1_TS175_TC39 | Device checkpoint: Device behaviour - 12 checks p.53-69 - BUYPASS-PIPS | **DEVICE** | [TC-041721.md](BR1_TS175/TC-041721.md) |
| TC-041722 | BR1_TS175_TC40 | Device checkpoint: Device behaviour - 12 checks p.69-73 - BUYPASS-PIPS | **DEVICE** | [TC-041722.md](BR1_TS175/TC-041722.md) |
| TC-041723 | BR1_TS175_TC41 | Device checkpoint: Device behaviour - 12 checks p.74-95 - BUYPASS-PIPS | **DEVICE** | [TC-041723.md](BR1_TS175/TC-041723.md) |
| TC-041724 | BR1_TS175_TC42 | Device checkpoint: Device behaviour - 12 checks p.95-104 - BUYPASS-PIPS | **DEVICE** | [TC-041724.md](BR1_TS175/TC-041724.md) |
| TC-041725 | BR1_TS175_TC43 | Device checkpoint: Device behaviour - 2 checks p.183 - BUYPASS-PIPS | **DEVICE** | [TC-041725.md](BR1_TS175/TC-041725.md) |
| TC-041726 | BR1_TS175_TC44 | Device checkpoint: Device behaviour - 4 checks p.61-63 - ATL105 | **DEVICE** | [TC-041726.md](BR1_TS175/TC-041726.md) |
| TC-041727 | BR1_TS175_TC45 | Device checkpoint: Device checkpoint check: Service Level | **DEVICE** | [TC-041727.md](BR1_TS175/TC-041727.md) |

<a id="br1_ts176"></a>
## BR1_TS176 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041728 | BR1_TS176_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041728.md](BR1_TS176/TC-041728.md) |
| TC-041729 | BR1_TS176_TC2 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041729.md](BR1_TS176/TC-041729.md) |
| TC-041730 | BR1_TS176_TC3 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041730.md](BR1_TS176/TC-041730.md) |
| TC-041731 | BR1_TS176_TC4 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041731.md](BR1_TS176/TC-041731.md) |
| TC-041732 | BR1_TS176_TC5 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041732.md](BR1_TS176/TC-041732.md) |
| TC-041733 | BR1_TS176_TC6 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041733.md](BR1_TS176/TC-041733.md) |
| TC-041734 | BR1_TS176_TC7 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041734.md](BR1_TS176/TC-041734.md) |
| TC-041735 | BR1_TS176_TC8 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041735.md](BR1_TS176/TC-041735.md) |
| TC-041736 | BR1_TS176_TC9 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041736.md](BR1_TS176/TC-041736.md) |
| TC-041737 | BR1_TS176_TC10 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041737.md](BR1_TS176/TC-041737.md) |
| TC-041738 | BR1_TS176_TC11 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041738.md](BR1_TS176/TC-041738.md) |
| TC-041739 | BR1_TS176_TC12 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041739.md](BR1_TS176/TC-041739.md) |
| TC-041740 | BR1_TS176_TC13 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041740.md](BR1_TS176/TC-041740.md) |
| TC-041741 | BR1_TS176_TC14 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041741.md](BR1_TS176/TC-041741.md) |
| TC-041742 | BR1_TS176_TC15 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041742.md](BR1_TS176/TC-041742.md) |
| TC-041743 | BR1_TS176_TC16 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041743.md](BR1_TS176/TC-041743.md) |
| TC-041744 | BR1_TS176_TC17 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041744.md](BR1_TS176/TC-041744.md) |
| TC-041745 | BR1_TS176_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041745.md](BR1_TS176/TC-041745.md) |
| TC-041746 | BR1_TS176_TC19 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041746.md](BR1_TS176/TC-041746.md) |
| TC-041747 | BR1_TS176_TC20 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041747.md](BR1_TS176/TC-041747.md) |
| TC-041748 | BR1_TS176_TC21 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-041748.md](BR1_TS176/TC-041748.md) |
| TC-041749 | BR1_TS176_TC22 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041749.md](BR1_TS176/TC-041749.md) |
| TC-041750 | BR1_TS176_TC23 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041750.md](BR1_TS176/TC-041750.md) |
| TC-041751 | BR1_TS176_TC24 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041751.md](BR1_TS176/TC-041751.md) |
| TC-041752 | BR1_TS176_TC25 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041752.md](BR1_TS176/TC-041752.md) |
| TC-041753 | BR1_TS176_TC26 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041753.md](BR1_TS176/TC-041753.md) |
| TC-041754 | BR1_TS176_TC27 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041754.md](BR1_TS176/TC-041754.md) |
| TC-041755 | BR1_TS176_TC28 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041755.md](BR1_TS176/TC-041755.md) |
| TC-041756 | BR1_TS176_TC29 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041756.md](BR1_TS176/TC-041756.md) |

<a id="br1_ts177"></a>
## BR1_TS177 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine)

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041757 | BR1_TS177_TC1 | Happy path: Authorization Only approved | **BLOCKED** | [TC-041757.md](BR1_TS177/TC-041757.md) |
| TC-041758 | BR1_TS177_TC2 | Happy path: Authorization Only sending Table 031 Point-of-Service Terminal Capability Codes | **BLOCKED** | [TC-041758.md](BR1_TS177/TC-041758.md) |
| TC-041759 | BR1_TS177_TC3 | Happy path: Authorization Only sending Table 073 Future Transaction Amount | **BLOCKED** | [TC-041759.md](BR1_TS177/TC-041759.md) |
| TC-041760 | BR1_TS177_TC4 | Alternate path: Authorization Only via alternate path: ENT-TABLE-I-032-SUB-SUB_TABLE_ID_01 | **BLOCKED** | [TC-041760.md](BR1_TS177/TC-041760.md) |
| TC-041761 | BR1_TS177_TC5 | Alternate path: Authorization Only via alternate path: Prompt Code | **BLOCKED** | [TC-041761.md](BR1_TS177/TC-041761.md) |
| TC-041762 | BR1_TS177_TC6 | Alternate path: Authorization Only via alternate path: TransArmor Load Data Segment (segment 116) | **BLOCKED** | [TC-041762.md](BR1_TS177/TC-041762.md) |
| TC-041763 | BR1_TS177_TC7 | Single rule broken: Authorization Only with Encrypted PIN Block Data sent where the spec forbids it -> declined | **FAIL** | [TC-041763.md](BR1_TS177/TC-041763.md) |
| TC-041764 | BR1_TS177_TC8 | Single rule broken: Cancellation sent where the spec forbids it -> declined | **BLOCKED** | [TC-041764.md](BR1_TS177/TC-041764.md) |
| TC-041765 | BR1_TS177_TC9 | Decision table: Authorization Only with Encrypted PIN Block Data = PIN (the rule's condition holds) | **FAIL** | [TC-041765.md](BR1_TS177/TC-041765.md) |
| TC-041766 | BR1_TS177_TC10 | Decision table: Authorization Only with Information Byte = 0 (the rule's condition holds) | **BLOCKED** | [TC-041766.md](BR1_TS177/TC-041766.md) |
| TC-041767 | BR1_TS177_TC11 | Decision table: Authorization Only with Service Level = F (the rule's condition holds) | **BLOCKED** | [TC-041767.md](BR1_TS177/TC-041767.md) |
| TC-041768 | BR1_TS177_TC12 | Decision table: Authorization Only with segment 101 sent (the rule's condition holds) | **BLOCKED** | [TC-041768.md](BR1_TS177/TC-041768.md) |
| TC-041769 | BR1_TS177_TC13 | Decision table: Authorization Only with segment 102 sent (the rule's condition holds) | **BLOCKED** | [TC-041769.md](BR1_TS177/TC-041769.md) |
| TC-041770 | BR1_TS177_TC14 | Invalid state transition: Authorization Only -> Cancellation -> Cancellation again (already closed) -> declined | **BLOCKED** | [TC-041770.md](BR1_TS177/TC-041770.md) |
| TC-041771 | BR1_TS177_TC15 | Invalid state transition: Authorization Only -> Purchase/Capture (not allowed) -> declined | **BLOCKED** | [TC-041771.md](BR1_TS177/TC-041771.md) |
| TC-041772 | BR1_TS177_TC16 | Invalid state transition: Cancellation with no original -> declined | **BLOCKED** | [TC-041772.md](BR1_TS177/TC-041772.md) |
| TC-041773 | BR1_TS177_TC17 | Idempotency / duplicate / retry: Authorization Only resend (attempt 3, max_attempts 3) (at the stated limit) | **BLOCKED** | [TC-041773.md](BR1_TS177/TC-041773.md) |
| TC-041774 | BR1_TS177_TC18 | Idempotency / duplicate / retry: Authorization Only resend (attempt 4, max_attempts 3) (beyond the stated limit) | **BLOCKED** | [TC-041774.md](BR1_TS177/TC-041774.md) |
| TC-041775 | BR1_TS177_TC19 | Time-based: Authorization Only hold offline (value 55, unit minute) (at the stated limit) | **BLOCKED** | [TC-041775.md](BR1_TS177/TC-041775.md) |
| TC-041776 | BR1_TS177_TC20 | Time-based: Authorization Only hold offline (value 56, unit minute) (beyond the stated limit) | **BLOCKED** | [TC-041776.md](BR1_TS177/TC-041776.md) |
| TC-041777 | BR1_TS177_TC21 | Interaction (pairwise): Authorization Only on a CAT terminal with offline pin | **BLOCKED** | [TC-041777.md](BR1_TS177/TC-041777.md) |
| TC-041778 | BR1_TS177_TC22 | Response / oracle check: Authorization Only -> Host Prompts Information returned | **BLOCKED** | [TC-041778.md](BR1_TS177/TC-041778.md) |
| TC-041779 | BR1_TS177_TC23 | Response / oracle check: Authorization Only -> Host-based Purchase Restriction returned | **BLOCKED** | [TC-041779.md](BR1_TS177/TC-041779.md) |
| TC-041780 | BR1_TS177_TC24 | Response / oracle check: Authorization Only -> Reserved Indicator Information returned | **BLOCKED** | [TC-041780.md](BR1_TS177/TC-041780.md) |
| TC-041781 | BR1_TS177_TC25 | Response / oracle check: Authorization Only -> Store and Forward Data Segment returned and checked (p.83-140) | **BLOCKED** | [TC-041781.md](BR1_TS177/TC-041781.md) |
| TC-041782 | BR1_TS177_TC26 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.104-182) | **BLOCKED** | [TC-041782.md](BR1_TS177/TC-041782.md) |
| TC-041783 | BR1_TS177_TC27 | Response / oracle check: Authorization Only -> Transaction Attributes Segment returned and checked (p.46-607) | **BLOCKED** | [TC-041783.md](BR1_TS177/TC-041783.md) |
| TC-041784 | BR1_TS177_TC28 | Response / oracle check: Authorization Only -> Transaction Identifier returned | **BLOCKED** | [TC-041784.md](BR1_TS177/TC-041784.md) |
| TC-041785 | BR1_TS177_TC29 | Response / oracle check: Authorization Only -> declined (issuer decline) | **BLOCKED** | [TC-041785.md](BR1_TS177/TC-041785.md) |

<a id="br1_ts178"></a>
## BR1_TS178 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041786 | BR1_TS178_TC1 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-041786.md](BR1_TS178/TC-041786.md) |
| TC-041787 | BR1_TS178_TC2 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-041787.md](BR1_TS178/TC-041787.md) |
| TC-041788 | BR1_TS178_TC3 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-041788.md](BR1_TS178/TC-041788.md) |

<a id="br1_ts179"></a>
## BR1_TS179 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041879 | BR1_TS179_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041879.md](BR1_TS179/TC-041879.md) |

<a id="br1_ts180"></a>
## BR1_TS180 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041880 | BR1_TS180_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041880.md](BR1_TS180/TC-041880.md) |

<a id="br1_ts181"></a>
## BR1_TS181 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041881 | BR1_TS181_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041881.md](BR1_TS181/TC-041881.md) |

<a id="br1_ts182"></a>
## BR1_TS182 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041882 | BR1_TS182_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041882.md](BR1_TS182/TC-041882.md) |

<a id="br1_ts183"></a>
## BR1_TS183 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041789 | BR1_TS183_TC1 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-041789.md](BR1_TS183/TC-041789.md) |
| TC-041790 | BR1_TS183_TC2 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-041790.md](BR1_TS183/TC-041790.md) |
| TC-041791 | BR1_TS183_TC3 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-041791.md](BR1_TS183/TC-041791.md) |
| TC-041792 | BR1_TS183_TC4 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-041792.md](BR1_TS183/TC-041792.md) |

<a id="br1_ts184"></a>
## BR1_TS184 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041883 | BR1_TS184_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041883.md](BR1_TS184/TC-041883.md) |

<a id="br1_ts185"></a>
## BR1_TS185 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041884 | BR1_TS185_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041884.md](BR1_TS185/TC-041884.md) |

<a id="br1_ts186"></a>
## BR1_TS186 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041885 | BR1_TS186_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041885.md](BR1_TS186/TC-041885.md) |

<a id="br1_ts187"></a>
## BR1_TS187 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6051 (cryptocurrency purchase) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041886 | BR1_TS187_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041886.md](BR1_TS187/TC-041886.md) |

<a id="br1_ts188"></a>
## BR1_TS188 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6051 (cryptocurrency purchase) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041887 | BR1_TS188_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041887.md](BR1_TS188/TC-041887.md) |

<a id="br1_ts189"></a>
## BR1_TS189 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6051 (cryptocurrency purchase) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041888 | BR1_TS189_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041888.md](BR1_TS189/TC-041888.md) |

<a id="br1_ts190"></a>
## BR1_TS190 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6211 (high risk security) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041793 | BR1_TS190_TC1 | Valid flow: Purchase/Capture -> Purchase Reversal | **BLOCKED** | [TC-041793.md](BR1_TS190/TC-041793.md) |
| TC-041794 | BR1_TS190_TC2 | Valid flow: Purchase/Capture -> Time-out Reversal | **BLOCKED** | [TC-041794.md](BR1_TS190/TC-041794.md) |
| TC-041795 | BR1_TS190_TC3 | Invalid state transition: Purchase/Capture -> Time-out Reversal -> Time-out Reversal again (already closed) -> declined | **BLOCKED** | [TC-041795.md](BR1_TS190/TC-041795.md) |
| TC-041796 | BR1_TS190_TC4 | Invalid state transition: Time-out Reversal with no original -> declined | **BLOCKED** | [TC-041796.md](BR1_TS190/TC-041796.md) |

<a id="br1_ts191"></a>
## BR1_TS191 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6211 (high risk security) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041889 | BR1_TS191_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041889.md](BR1_TS191/TC-041889.md) |

<a id="br1_ts192"></a>
## BR1_TS192 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041890 | BR1_TS192_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041890.md](BR1_TS192/TC-041890.md) |

<a id="br1_ts193"></a>
## BR1_TS193 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041891 | BR1_TS193_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041891.md](BR1_TS193/TC-041891.md) |

<a id="br1_ts194"></a>
## BR1_TS194 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6211 (high risk security) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041892 | BR1_TS194_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041892.md](BR1_TS194/TC-041892.md) |

<a id="br1_ts195"></a>
## BR1_TS195 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6211 (high risk security) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041893 | BR1_TS195_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041893.md](BR1_TS195/TC-041893.md) |

<a id="br1_ts196"></a>
## BR1_TS196 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6211 (high risk security) · attended POS

| Test case | Display id | Objective | Result | Document |
|---|---|---|---|---|
| TC-041894 | BR1_TS196_TC1 | Happy path: Purchase/Capture approved | **BLOCKED** | [TC-041894.md](BR1_TS196/TC-041894.md) |
