# ATL105 Segment 112 Additional Information Value Catalog — Business Requirements

## Scope and provenance

This catalog defines the per-code business requirements for Element 116 (Additional Information Indicator) and its paired Element 118 (Additional Information) content, as transcribed directly from ATL105 2026-3 Section 12.11 and Section 13.2 (Elements 115-118). It mirrors the structure of [ATL105 Segment 100 Financial Card-Type Business Requirements](../financial-card-type-business-requirements.md) — one row per independently testable allowlist/shape requirement.

These are requirements for the Element 116/117/118 triad dimension of Segment 112. They do not replace the segment-level structural requirements in the [rule catalog](coverage/segment-112-rule-catalog.json) (Segment Type, Segment Length, 990/999-byte caps, Element 115 applicability). A Segment 112 triad is business-valid only when the Element 116 code, the Element 117 length, and the Element 118 shape all agree.

## Common requirements

| ID | Requirement | Acceptance criteria |
|---|---|---|
| AI-112-001 | A Segment 112 triad shall pair one valid Element 116 code with an Element 117 length and Element 118 value consistent with that code's documented shape. | `AdditionalInformationIndicator` is one of the documented/reserved codes in this catalog; `AdditionalInformationLength` equals the byte length of `AdditionalInformation`. |
| AI-112-002 | The Element 116 code shall be preserved as a three-digit value, including leading zeroes. | `004` is valid; `4` is invalid. |
| AI-112-003 | A fixed-length code (`012`, `013`, `017`, `018`) shall have an Element 118 value of exactly its documented fixed length, not merely within the general 984-byte maximum. | `012` → exactly 15 bytes; `013` → exactly 4 bytes; `017` → exactly 3 bytes; `018` → exactly 3 numeric bytes. |
| AI-112-004 | A variable-length code shall have an Element 118 value no longer than 984 bytes and no longer than the value declared in Element 117. | `AdditionalInformation.length <= AdditionalInformationLength <= 984`. |
| AI-112-005 | Codes `014`, `015`, and `033` shall be treated as reserved/invalid, the same disposition as documented code `002` (resolved 2026-09-26). | Validator result is `FAIL` for these three codes, consistent with `002`. See [SEG112-SME-002](segment-112-sme-tba-input-register.md). |
| AI-112-006 | An Element 116 code shall not be treated as evidence that a specific companion segment is required; Segment 112 has no companion-segment dependency of its own. | The presence of any Element 116 code does not, by itself, promote a Segment 101/102/103/104/130 requirement. |

## Additional Information Indicator (Element 116) requirements

| ID | Code | Information Type | Requirement |
|---|---:|---|---|
| AI-112-001-001 | `001` | Balance (gift/EBT/credit/phone card) | A Segment 112 triad may carry balance information with code `001` when the transaction is balance-eligible per its card type. |
| AI-112-001-002 | `002` | Reserved | Code `002` is explicitly reserved and shall not carry a business value. |
| AI-112-001-003 | `003` | AVS and/or NVS result | A Segment 112 triad may carry AVS/NVS result information with code `003` when the originating request carried AVS/NVS data. |
| AI-112-001-004 | `004` | CVV/CVV2/CVC2/CID/DTVV | A Segment 112 triad may carry card-verification result information with code `004` when the originating request carried verification data. |
| AI-112-001-005 | `005` | ECA/TeleCheck® Trace ID | A Segment 112 triad may carry ECA/TeleCheck® Trace ID information with code `005` in check-processing contexts. |
| AI-112-001-006 | `006` | ECA/TeleCheck® Denial Record Number | A Segment 112 triad may carry ECA/TeleCheck® denial-record information with code `006` in check-processing contexts. |
| AI-112-001-007 | `007` | ECA/TeleCheck® Return Check Data | A Segment 112 triad may carry ECA/TeleCheck® return-check information with code `007` in check-processing contexts. |
| AI-112-001-008 | `008` | Loyalty Information — Version 1 | A Segment 112 triad may carry Version 1 loyalty information with code `008` when a Version 1 loyalty program applies. |
| AI-112-001-009 | `009` | Visa® product result | A Segment 112 triad may carry Visa product-result information with code `009` when the originating request applies. |
| AI-112-001-010 | `010` | Loyalty Information — Version 2 | A Segment 112 triad may carry Version 2 loyalty information with code `010` when a Version 2 loyalty program applies. |
| AI-112-001-011 | `011` | User Data Information | A Segment 112 triad may carry user data with code `011` when the applicable rule is satisfied. |
| AI-112-001-012 | `012` | Discover® Network Retrieval Reference Number | A Segment 112 triad shall carry a fixed 15-byte Discover Network Retrieval Reference Number with code `012` in all Discover Network follow-on transactions. |
| AI-112-001-013 | `013` | Expiration Date (MMYY), TransArmor - VeriFone edition | A Segment 112 triad shall carry a fixed 4-byte MMYY expiration date with code `013` in TransArmor - VeriFone-edition contexts. |
| AI-112-001-016 | `016` | PIN-on-Receipt Information | A Segment 112 triad may carry PIN-on-receipt information with code `016` when the applicable rule is satisfied. |
| AI-112-001-017 | `017` | BUYPASS Host Card Type | A Segment 112 triad shall carry a fixed 3-byte BUYPASS host card type with code `017` when applicable. |
| AI-112-001-018 | `018` | PINless Debit Information | A Segment 112 triad shall carry fixed 3-digit numeric PINless-processing information with code `018` when applicable. |
| AI-112-001-019 | `019` | Visa Spend Qualified Indicator Information | A Segment 112 triad may carry Visa Spend Qualified Indicator information with code `019` when applicable. |
| AI-112-001-020 | `020` | Host Prompts Information | A Segment 112 triad may carry host-prompts information with code `020` when applicable. |
| AI-112-001-021 | `021` | Re-Price Data Response Information | A Segment 112 triad may carry re-price data response information with code `021` when applicable. |
| AI-112-001-022 | `022` | CAVV Result | A Segment 112 triad may carry CAVV result information with code `022` when applicable. |
| AI-112-001-023 | `023` | MCX Reference Number | A Segment 112 triad may carry an MCX reference number with code `023` when applicable. |
| AI-112-001-024 | `024` | Carwash Indicator | A Segment 112 triad may carry carwash-indicator information with code `024` when applicable. |
| AI-112-001-025 | `025` | Language Indicator | A Segment 112 triad may carry language-indicator information with code `025` when applicable. |
| AI-112-001-026 | `026` | DST Response | A Segment 112 triad may carry DST response information with code `026` when applicable. |
| AI-112-001-027 | `027` | Universal Unique Identifier | A Segment 112 triad may carry a Universal Unique Identifier with code `027` when applicable. |
| AI-112-001-028 | `028` | Transaction Identifier | A Segment 112 triad may carry a transaction identifier with code `028` when applicable. |
| AI-112-001-029 | `029` | PAR (Payment Account Reference) Data | A Segment 112 triad may carry PAR data with code `029` when applicable. |
| AI-112-001-030 | `030` | Merchant Advice Code | A Segment 112 triad may carry a merchant advice code with code `030` when applicable. |
| AI-112-001-031 | `031` | DAF Indicator | A Segment 112 triad may carry DAF-indicator information with code `031` when applicable. |
| AI-112-001-032 | `032` | Agreement ID | A Segment 112 triad may carry an agreement ID with code `032` when applicable. |
| AI-112-001-034 | `034` | Host-based Purchase Restriction | A Segment 112 triad may carry host-based purchase-restriction information with code `034` when applicable. |
| AI-112-001-035 | `035` | Cardholder Additional Information Result Code | A Segment 112 triad may carry a cardholder additional-information result code with code `035` when applicable. |
| AI-112-001-036 | `036` | Account Type | A Segment 112 triad may carry account-type information with code `036` when applicable. |
| AI-112-001-037 | `037` | Account Funding Source | A Segment 112 triad may carry account-funding-source information with code `037` when applicable. |
| AI-112-001-038 | `038` | Transaction Link Identifier | A Segment 112 triad may carry a transaction link identifier with code `038` when applicable. |
| AI-112-001-039 | `039` | Transaction Link Action Indicator | A Segment 112 triad may carry a transaction link action indicator with code `039` when applicable. |
| AI-112-001-040 | `040` | Fraud Score | A Segment 112 triad may carry a fraud score with code `040` when applicable. |
| AI-112-001-041 | `041` | Fraud Score Reason Code | A Segment 112 triad may carry a fraud score reason code with code `041` when applicable. |
| AI-112-001-042 | `042` | Authentication Data Quality Indicator | A Segment 112 triad may carry an authentication data quality indicator with code `042` when applicable. |
| AI-112-001-043 | `043` | Token Update First Use Indicator | A Segment 112 triad may carry a token update first-use indicator with code `043` when applicable. |
| AI-112-001-044 | `044` | Merchant Tran ID | A Segment 112 triad may carry a merchant transaction ID with code `044` when applicable. |
| AI-112-001-045 | `045` | Applied Special Service | A Segment 112 triad may carry applied special-service information with code `045` when applicable. |
| AI-112-001-046 | `046` | Voyager Restriction Code | A Segment 112 triad may carry a Voyager restriction code with code `046` when applicable. |
| AI-112-001-047 | `047` | Visa Category Code | A Segment 112 triad may carry a Visa category code with code `047` when applicable. |

## Segment-Level Rule Requirements

These requirements cover Segment 112 structure and framing; the AI-112 requirements above remain the detailed indicator/value catalog.

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG112-001 | field | Segment Type (Element 85) is fixed value 112 | Element 85 equals `112`. | `SEG112-R-001` §12.11 | SPEC_DERIVED |
| BR-SEG112-002 | field | Segment Length (Element 84) equals serialized length including Segment Type and Field Separators | The declared length equals the encoded Segment 112 length. | `SEG112-R-002` §12.11 | SPEC_DERIVED |
| BR-SEG112-003 | serialization | Segment 112 has a maximum length of 999 alphanumeric characters | The serialized segment does not exceed 999 characters. | `SEG112-R-003` §12.11 | SPEC_DERIVED |
| BR-SEG112-004 | structure | Segment 112 appears only at the end of a Financial Transaction Response | Segment 112 is present only in the response position specified by the rule. | `SEG112-R-004` §12.11 | SPEC_DERIVED |
| BR-SEG112-005 | metadata | Segment 112 content originates at BUYPASS (Host), not the device | Documented for traceability; not independently asserted by a validator. | `SEG112-R-005` §12.11 | SPEC_DERIVED |
| BR-SEG112-006 | serialization | The Additional Information section (Elements 116/117/118) repeats once per Additional Information Indicator, up to 990 bytes total | Repetition count and combined section length conform to the source rule. | `SEG112-R-006` §12.11 | SPEC_DERIVED |
| BR-SEG112-007 | field | Additional Information Indicator (Element 116) identifies the type of associated Additional Information | The indicator selects its documented data meaning; per-code shapes remain specified in the AI-112 catalog above. | `SEG112-R-007` §12.11 | SPEC_DERIVED |
| BR-SEG112-008 | dependency | Additional Information Length (Element 117) identifies the length of the following Additional Information (Element 118) | Element 117 equals the encoded length of its paired Element 118 value. | `SEG112-R-008` §12.11 | SPEC_DERIVED |
| BR-SEG112-009 | field | Additional Information (Element 118) is variable length and carries the value identified by its paired Indicator and Length | The value conforms to the Element 116 meaning and paired Element 117 length; per-code shapes remain specified above. | `SEG112-R-009` §12.11 | SPEC_DERIVED |
| BR-SEG112-010 | compatibility | Segment 112 is required in a Financial Transaction Response only when Element 115 equals 1 | Presence agrees with Element 115; ownership remains under SME review. | `SEG112-R-010` §13 | REVIEW_REQUIRED |

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG112-NEG-001 | `SEG112-R-001` | MUT-001 wrong fixed value | Validation error citing SEG112-R-001 |
| BR-SEG112-NEG-002 | `SEG112-R-002` | MUT-003 length mismatch | Validation error citing SEG112-R-002 |
| BR-SEG112-NEG-003 | `SEG112-R-003` | MUT-003 length exceeded | Validation error citing SEG112-R-003 |
| BR-SEG112-NEG-004 | `SEG112-R-004` | MUT-010 wrong response position | Validation error citing SEG112-R-004 |
| BR-SEG112-NEG-006 | `SEG112-R-006` | MUT-003 repetition/length exceeded | Validation error citing SEG112-R-006 |
| BR-SEG112-NEG-007 | `SEG112-R-007` | MUT-004/MUT-008 invalid indicator | Validation error citing SEG112-R-007 |
| BR-SEG112-NEG-008 | `SEG112-R-008` | MUT-009 length/value mismatch | Validation error citing SEG112-R-008 |
| BR-SEG112-NEG-009 | `SEG112-R-009` | MUT-009 indicator/value mismatch | Validation error citing SEG112-R-009 |
| BR-SEG112-NEG-010 | `SEG112-R-010` | MUT-009 flag/presence mismatch | Held at REVIEW_REQUIRED until ownership is confirmed |

## Open Rule Ownership

- **SEG112-SME-003** (`SEG112-R-010`): Confirm whether the Element 115 applicability rule belongs in the Segment 100 catalog, the Segment 112 catalog, or both. The Segment 100 catalog currently has no Element 115 reference; this BR remains `REVIEW_REQUIRED` pending the decision.

## Reserved and previously-undocumented codes

| ID | Code | Status |
|---|---:|---|
| AI-112-RESERVED-001 | `014` | Resolved 2026-09-26: reserved/invalid, same disposition as `002`. A Segment 112 triad shall not use code `014` until a future specification revision documents it. |
| AI-112-RESERVED-002 | `015` | Resolved 2026-09-26: reserved/invalid, same disposition as `002`. |
| AI-112-RESERVED-003 | `033` | Resolved 2026-09-26: reserved/invalid, same disposition as `002`. |

## Required negative coverage

| ID | Requirement | Expected result |
|---|---|---|
| AI-112-NEG-001 | Reject an Element 116 code that is not exactly three numeric digits. | Validation error: Additional Information Indicator must be exactly three digits. |
| AI-112-NEG-002 | Reject an Element 116 code that is reserved/invalid (`002`, `014`, `015`, `033`) or otherwise outside the documented set (e.g., `099`). | Validation error: unsupported or reserved Additional Information Indicator. |
| AI-112-NEG-003 | Reject an Element 118 value whose length does not match its declared Element 117 length. | Validation error: Additional Information Length mismatch. |
| AI-112-NEG-004 | Reject a fixed-length code (`012`, `013`, `017`, `018`) whose Element 118 value length does not equal its documented fixed length. | Validation error: fixed-length Additional Information code has wrong length. |
| AI-112-NEG-005 | Do not infer a companion-segment requirement from an Element 116 code alone. | Requirement remains scoped to Segment 112; no Segment 101/102/103/104/130 rule is triggered. |

## Implementation traceability

- Value table source: ATL105 2026-3 Section 12.11 (opening list) and Section 13.2, Element 116 (pages 426-428).
- Fixed-length exceptions: Section 13.2, Elements 116/118 processing-rule narrative (codes `012`, `013`, `017`, `018`).
- Undocumented codes: cross-checked against the full Section 12.11 list and Section 13.2 narrative; confirmed absent (not a transcription omission in this KB pass).
- This catalog does not use AI Solution artifacts as business-requirement evidence; see the [AI-to-Test Requirement Crosswalk](coverage/segment-112-ai-to-test-requirement-crosswalk.md) for the independent comparison against AI-side extraction.
