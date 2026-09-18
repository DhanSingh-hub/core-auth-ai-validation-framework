# BUYPASS ATL105 Specification Knowledge Base Index

Navigation index for BUYPASS Platform ATL105 Message Format Specification, Version 2026-3
(722 pages). Source: `docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf`,
extracted to `docs/specs/extracted_text.txt` (36,873 lines; each PDF page is marked with a
`--- PAGE N ---` line).

> Line numbers below are approximate (derived from an automated pass over `extracted_text.txt`
> and spot-checked, but not every boundary was individually verified). Use them as a starting
> point and refine with `grep_search`/`read_file` on `extracted_text.txt` if a lookup seems off.

## Chapters

| Chapter | Title | Purpose | Approx. Line Range |
|---------|-------|---------|-----------|
| 1 | Introduction | Overview and document structure | 1688-1818 |
| 2 | Message Flow Overview | Message flow diagrams and TOR processing | 1819-2572 |
| 3 | Specifications Requirements | Hardware, software, telecommunications requirements | 2573-2646 |
| 4 | Supported Industries | General retail, hospitality, supermarket, mail order, utilities, petroleum | 2647-2694 |
| 5 | Supported Payment Network Types | Payment network support matrix | 2695-2728 |
| 6 | PIN Encryption | 3DES DUKPT PIN encryption, key derivation, PIN blocks | 2729-3040 |
| 7 | Network Management Processing | Communications test requests/responses | 3041-3071 |
| 8 | Store-and-Forward Processing | Automated fuel pump, restaurant, lodging processing | 3072-3286 |
| 9 | End-of-Day Processing | Electronic journal, local totals, host totals, settlement | 3287-3780 |
| 10 | Processing Requirements | Credit card, debit, EBT, fleet, stored value, check, loyalty processing | 3781-7444 |
| 11 | Message Formats | Request/response structures for all transaction types | 7445-10844 |
| 12 | Data Segment Formats | Definitions of message segment types | 10845-17699 |
| 13 | Data Element Descriptions | Detailed descriptions of data elements (13.1 alphabetical, 13.2 by number) | 17700-25439 |
| 14 | Support, Testing, and Certification | Project management, development support, testing, certification | 25440-25504 |

## Appendices

| Appendix | Title | Purpose | Approx. Line Range |
|----------|-------|---------|-----------|
| A | TCP/IP Message Header | TCP/IP message header for network communication | 25506-25541 |
| B | POS Purchase Example | Example data layout for a point-of-sale purchase transaction | 25577-25632 |
| C | Valid Authorizer Codes | Authorizer codes (Element 7); verified against source, 98 codes | 25935-26069 |
| D | Valid State Codes | US states/territories, military, Canadian provinces (Elements 4, 12, 102, 124) | 26070-26242 |
| E | Valid Card Type Codes | Card type codes (Elements 14, 78) | 26243-26484 |
| F | Valid Payment Systems Product Codes | Product codes: fuel, automotive, aviation, marine, merchandise, FSA/HRA (Element 77) | 26485-27246 |
| G | Valid Transaction Type Codes | Transaction type codes (Element 78, 1st position) | 27247-27347 |
| H | Decline Codes Permitting Clerk/Customer Intervention | Decline codes allowing merchant/customer action (Element 26) | 27348-27442 |
| I | Variable Information Data Layouts | Table IDs 001-081 for Element 111/113 | 27443-32490 |
| J | Valid Point-of-Service Entry Mode Codes | PAN entry mode + PIN entry capability (Element 113, w/ Element 111 Table 005) | 32491-32576 |
| K | Additional Information Data Layouts | Table IDs 001-047 for Element 116/118 | 32577-34456 |
| L | Valid Currency Codes | Currencies with ISO codes, Visa/MasterCard support (Element 20) | 34457-34890 |
| M | Element Format Examples for EBT Program Data | Format examples for Element 164 (EBT Program Data) | 34891-35045 |
| N | First Data Premium Gift Card Magnetic Stripe Data Layout | Track II layout for First Data Premium Gift Card | 35046-35090 |
| O | Payment Token Terminologies | Tokenization terminology | 35091-35141 |
| P | TransArmor VeriFone Edition: Responses to Administrative Commands | External spec reference | 35142-35157 |
| Q | Valid National Point-of-Service Condition Codes | POS condition codes (Element 111 Table 030) | 35158-35406 |
| R | EMV Chip Data Example | Example EMV chip data packet structure | 35407-35449 |
| S | Valid CA Public Key File Record Layout | CA Public Key File record layout for EMV | 35450-35558 |
| T | EMV Additional Information Data Layouts | EMV Table Data and CARC layouts | 35559-35625 |
| U | Visa Digital Wallet | Visa Pass-through and Staged Digital Wallet | 35626-35662 |
| V | Moneris Data Layout | Moneris request/response message data layout | 35663-35815 |
| W | Download Data Layout | Download data layout for Supplemental Terminal Data Segment | 35816-35840 |
| X | Online Refund/Refund Authorization | Merchandise return processing | 35841-36049 |
| Y | 3-D Secure | 3-D Secure authentication | 36050-36265 |
| Z | Stored Credential | Stored credential transactions | 36266-36595 |
| AA | TransArmor Processing Considerations | TransArmor PKI encryption/tokenization | 36596-36611 |
| AB | Purchase Repayment | Purchase repayment transaction processing | 36612-36806 |
| AC | Valid Country Codes | Country codes list | 36807-36840 |
| AD | Real Time Account Updater | Real Time Account Updater request data | 36841-36872 |
| AE | Visa Estimated and Incremental Authorization Transactions | Estimated/incremental authorization details | 36873-end |

## Related files in this knowledge base

- [11-financial-transaction-request-sections.md](11-financial-transaction-request-sections.md) - TCP/IP header distinction, standard financial-request sections, Segment 100-only eligibility, and Section 3 conditions
- [segment-100-canonical-anchors.md](segment-100-canonical-anchors.md) - stable source-anchor vocabulary for independent AI and Test Validation artifacts in the current Segment 100 scope
- [segment-100/README.md](segment-100/README.md) - focused Segment 100 SME, technical business analysis, and flow-learning module
- [segment-100/coverage/README.md](segment-100/coverage/README.md) - Segment 100 rule coverage, approval gates, and report design
- [segment-102/README.md](segment-102/README.md) - focused Segment 102 Product Code SME, technical business analysis, and flow-learning module
- [segment-102/coverage/README.md](segment-102/coverage/README.md) - Segment 102 rule coverage, approval gates, and open manual-input items
- [Test Validation Strategy Package](../../test-validation-strategy/README.md) - complete ATL105 validation strategy and Segment 100 descriptive business requirements
- [segment-compatibility-matrix.md](segment-compatibility-matrix.md) - conditional compatibility of Segment 100 with currently mapped Section 3 segments
- [segment-103-ebt-sme-note.md](segment-103-ebt-sme-note.md) - Segment 103 EBT/eWIC layout, POS flow, serialization rules, validation rules, and SME learning checklist
- [13-data-elements.md](13-data-elements.md) - data dictionary from chapter 13.2 (elements 1-99 fully transcribed; 100-228 not yet done)
- [appendix-code-tables.md](appendix-code-tables.md) - flat ENUM-style code tables (Appendices C, D, E, F, G, J, L)
- [appendix-I-K-overview.md](appendix-I-K-overview.md) - Table ID index for Appendices I and K

## How to use this index

- For a specific data element: go to chapter 13.2 (~line 19320) and search by element number, or check [13-data-elements.md](13-data-elements.md) first.
- For a specific code table (authorizer, state, card type, currency, etc.): see the matching appendix, or check [appendix-code-tables.md](appendix-code-tables.md) first.
- For variable/additional information segments: see Appendices I and K, indexed in [appendix-I-K-overview.md](appendix-I-K-overview.md).
