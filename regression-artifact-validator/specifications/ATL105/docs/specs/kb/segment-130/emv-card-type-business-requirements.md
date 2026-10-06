# ATL105 Segment 130 EMV Card-Type and Transaction-Type Business Requirements

## Scope and provenance

This catalog defines the card-product and transaction-type requirements that govern whether Segment 130 (EMV Request Data Segment) is applicable to a given request. The lists are anchored to **Section 10.14.2.3 (Supported Cards)** and **Section 10.14.2.4 (Supported Transaction Types)** of the ATL105 specification, release 2026-3.

These are requirements for the **EMV applicability dimension**. They do not replace Segment 130's field-level rules (see [the rule catalog](coverage/segment-130-rule-catalog.json)), entry-mode rules (see [EMV entry mode and fallback](emv-entry-mode-fallback-sme-tba-note.md)), or serialization rules. A request is business-valid only when card product, transaction type, entry mode, and segment content all agree.

> **Important scoping note.** Section 10.14.2.3 enumerates EMV-supported cards by **brand/network name**. It does **not** provide the corresponding Appendix E three-digit ATL105 card-type codes. This catalog therefore does **not** assert a code mapping. See `SEG130-SME-008` below.

## Common requirements

| ID | Requirement | Acceptance criteria |
|---|---|---|
| EMV-130-001 | An EMV Financial Transaction Request shall contain Segment 130 when the transaction is an EMV authorization-class transaction. | `EMV Financial Request.EMV Request Data Segment.SegmentType` is `130`. |
| EMV-130-002 | Segment 130 shall be absent when the transaction is a fallback (`80`) or plain MSR (`90`) swipe. | No segment with `SegmentType` `130` is present. |
| EMV-130-003 | Segment 130 shall not be required on Reversal or Time-out Reversal transactions. | Section 10.14.2.4: "EMV data is not required on Reversal transactions." Absence is not an error. |
| EMV-130-004 | The card product shall be drawn from the Section 10.14.2.3 supported list for the transaction to be EMV-eligible. | Card product appears in one of the three tables below. |
| EMV-130-005 | The transaction type shall be drawn from the Section 10.14.2.4 supported list. | Transaction type appears in the transaction-type table below. |
| EMV-130-006 | A supported card product shall not by itself be treated as evidence that a transaction is EMV. | Entry mode and chip-read outcome determine EMV applicability, not card product. |
| EMV-130-007 | Segment 130 applicability shall be evaluated independently of the presence of companion segments. | Companion segments 101/102/103/104/111/123/135/143/145/146/151/152/153 neither imply nor preclude Segment 130. |

## Supported credit card products

| ID | Card Product | Category | Requirement |
|---|---|---|---|
| EMV-130-CR-001 | American Express | Credit | An EMV transaction may identify American Express when the applicable credit, entry-mode, and chip-read rules are satisfied. |
| EMV-130-CR-002 | Discover® | Credit | An EMV transaction may identify Discover when the applicable credit, entry-mode, and chip-read rules are satisfied. |
| EMV-130-CR-003 | MasterCard® | Credit | An EMV transaction may identify MasterCard when the applicable credit, entry-mode, and chip-read rules are satisfied. |
| EMV-130-CR-004 | Visa® | Credit | An EMV transaction may identify Visa when the applicable credit, entry-mode, and chip-read rules are satisfied. |
| EMV-130-CR-005 | STAR® Signature Debit | Credit-listed | An EMV transaction may identify STAR Signature Debit when the applicable signature-debit and chip-read rules are satisfied. Note: the specification lists this product under *credit* cards in Section 10.14.2.3. |

## Supported debit card products

| ID | Card Product | Category | Requirement |
|---|---|---|---|
| EMV-130-DB-001 | Interlink® | Debit | An EMV transaction may identify Interlink when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-002 | Maestro® | Debit | An EMV transaction may identify Maestro when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-003 | STAR® West | Debit | An EMV transaction may identify STAR West when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-004 | STAR® Southeast | Debit | An EMV transaction may identify STAR Southeast when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-005 | STAR® Northeast | Debit | An EMV transaction may identify STAR Northeast when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-006 | NYCE® | Debit | An EMV transaction may identify NYCE when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-007 | PULSE® | Debit | An EMV transaction may identify PULSE when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-008 | Accel® | Debit | An EMV transaction may identify Accel when the applicable PIN-debit and chip-read rules are satisfied. |
| EMV-130-DB-009 | Shazam | Debit | An EMV transaction may identify Shazam when the applicable PIN-debit and chip-read rules are satisfied. |

## Supported proprietary and fleet card products

| ID | Card Product | Category | Requirement |
|---|---|---|---|
| EMV-130-PF-001 | Generic Proprietary | Proprietary | An EMV transaction may identify Generic Proprietary when the applicable proprietary-card rules are satisfied. **Conditional:** the specification qualifies this as "Some issuers participating" — issuer participation must be confirmed per deployment. |
| EMV-130-PF-002 | Voyager® | Fleet | An EMV transaction may identify Voyager when the applicable fleet and chip-read rules are satisfied. |
| EMV-130-PF-003 | Wright Express® | Fleet | An EMV transaction may identify Wright Express when the applicable fleet and chip-read rules are satisfied. |

## Supported transaction types

Section 10.14.2.4 enumerates eight point-of-sale transactions for EMV card processing.

| ID | Transaction Type | Segment 130 required? |
|---|---|---|
| EMV-130-TX-001 | Purchase | Yes |
| EMV-130-TX-002 | Purchase/Capture | Yes |
| EMV-130-TX-003 | Authorization Only | Yes |
| EMV-130-TX-004 | Merchandise Return | Yes |
| EMV-130-TX-005 | Purchase Reversal | **No** — EMV data waived (10.14.2.4) |
| EMV-130-TX-006 | Balance Inquiry | Yes |
| EMV-130-TX-007 | Cancellation | Yes |
| EMV-130-TX-008 | Time-out Reversal (TOR) | **No** — EMV data waived (10.14.2.4) |

## PIN-mode requirements

| ID | Requirement | Acceptance criteria |
|---|---|---|
| EMV-130-PIN-001 | An EMV-PIN-capable device shall support both online and offline PIN. | MasterCard mandate, Section 10.14.3. Visa supports but does not mandate online PIN. |
| EMV-130-PIN-002 | In a PIN Debit transaction the PIN field shall be populated with either a PIN or all `F`s. | An empty PIN field declines as "invalid transaction" (Section 10.14.3). |
| EMV-130-PIN-003 | In a credit or signature-debit transaction, all `F`s shall not be required. | PIN is optional for those flows (Section 10.14.3). |

## Required negative coverage

| ID | Requirement | Expected result |
|---|---|---|
| EMV-130-NEG-001 | Reject a card product that is not in the Section 10.14.2.3 supported list as EMV-eligible. | Validation error: card product not supported for EMV processing. |
| EMV-130-NEG-002 | Reject Segment 130 when entry mode indicates fallback (`80`). | Validation error: Segment 130 present on a magnetic-stripe fallback transaction. |
| EMV-130-NEG-003 | Do **not** raise an error when Segment 130 is absent on a Reversal or TOR. | Absence is legal per 10.14.2.4. |
| EMV-130-NEG-004 | Reject a transaction type outside the Section 10.14.2.4 list as EMV-eligible. | Validation error: unsupported EMV transaction type. |
| EMV-130-NEG-005 | Do not infer EMV applicability from card product alone. | Requirement remains conditional until entry mode and chip-read outcome are present. |
| EMV-130-NEG-006 | Reject an empty PIN field on a PIN Debit EMV transaction. | Validation error: PIN or all `F`s required. |

## Catalog-derived segment requirements

Segment 130 rules that no `EMV-130-*` requirement or adopted Test Team BR restates, derived from the rule catalog in the standard segment format.

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG130-004 | serialization | Segment 130 maximum length is 3,043 alphanumeric characters (001-3043); Section 12.20 and the Element 84 table agree, while the Section 11.8.1 table states 9999 | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG130-R-004` §12.20,13.2 | SPEC_DERIVED |
| BR-SEG130-006 | field | EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG130-R-006` §12.20 | SPEC_DERIVED |
| BR-SEG130-010 | field | Tag 9F26 (Application Cryptogram) authenticity verification is out of scope for AI/synthetic test generation; it requires a certified EMV kernel or HSM | Synthetic validation does not assert cryptogram authenticity; certification evidence comes from a certified EMV kernel or HSM. | `SEG130-R-010` §AppendixR | SPEC_DERIVED |
| BR-SEG130-011 | structure | The EMV Additional Information Section (fields 7-9: Indicator, Length, Information) repeats per EMV Additional Information Indicator for a maximum total length of 2,000 bytes | The segment's structural position and composition match the rule. | `SEG130-R-011` §12.20 | SPEC_DERIVED |
| BR-SEG130-012 | metadata | Element 118 is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable, repeating); element numbers are not globally unique and the two fields do not share business meaning | Documented for traceability; not independently asserted by a validator. | `SEG130-R-012` §12.11,12.20 | SPEC_DERIVED |
| BR-SEG130-013 | serialization | Field Separators appear between fields 1-6 and between field 6 and the EMV Additional Information Section, even when a field is not populated; no separators occur within or between repetitions of that section; exactly one Field Separator follows the final repetition | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG130-R-013` §12.20 | SPEC_DERIVED |
| BR-SEG130-014 | metadata | Segment 130 originates at the device | Documented for traceability; not independently asserted by a validator. | `SEG130-R-014` §12.20 | SPEC_DERIVED |
| BR-SEG130-015 | lifecycle | CA Public Key File Checksum (Element 187) is echoed by Segment 131 (Field 3, Required, Host-sourced) so the device can detect when it needs a new CA Public Key File download | Paired messages are present and the correlated values agree. | `SEG130-R-015` §12.20,12.21 | SPEC_DERIVED |
| BR-SEG130-018 | structure | The EMV Financial Transaction Request Data Section 3 occupies Field Nos. 4-8, so Segment 130 plus at most four companion segments may be present | The segment's structural position and composition match the rule. | `SEG130-R-018` §11.8.1 | SPEC_DERIVED |
| BR-SEG130-020 | field | Appendix T Table ID 001 (EMV Table Data) carries exactly EMVYES or EMVNOT, length 6, sent only in an EMV transaction request; if returned in an authorization response, the device echoes it on any subsequent advice or batch upload request | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG130-R-020` §AppendixT | SPEC_DERIVED |
| BR-SEG130-021 | field | Appendix T Table ID 002 (Card Authentication Results Code) has maximum length 1 and carries the CARC value returned by Visa in Bit No. 44.8 of the response | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG130-R-021` §AppendixT | REVIEW_REQUIRED |
| BR-SEG130-022 | serialization | Segment 130 has no trailing-optional-field omission allowance; all six fixed fields retain their Field Separators even when empty, and only the entire EMV Additional Information Section may be omitted | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG130-R-022` §12.20 | SPEC_DERIVED |
| BR-SEG130-023 | serialization | Segment 131 uses inverted serialization rules and must not reuse the Segment 130 parser: no Field Separators, maximum length 3,834, EMV Additional Information Section cap 2,800 bytes, CA Public Key File Checksum Required, and no EMV Card Sequence Number field | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG130-R-023` §12.21 | SPEC_DERIVED |

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG130-NEG-004 | `SEG130-R-004` | MUT-003 length violation | Validation error citing SEG130-R-004 |
| BR-SEG130-NEG-006 | `SEG130-R-006` | MUT-003 length violation | Validation error citing SEG130-R-006 |
| BR-SEG130-NEG-011 | `SEG130-R-011` | MUT-003 length violation | Validation error citing SEG130-R-011 |
| BR-SEG130-NEG-013 | `SEG130-R-013` | MUT-010 structural / separator violation | Validation error citing SEG130-R-013 |
| BR-SEG130-NEG-015 | `SEG130-R-015` | MUT-009 interdependency violation | Validation error citing SEG130-R-015 |
| BR-SEG130-NEG-018 | `SEG130-R-018` | MUT-010 structural requirement | Validation error citing SEG130-R-018 |
| BR-SEG130-NEG-020 | `SEG130-R-020` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG130-R-020 |
| BR-SEG130-NEG-021 | `SEG130-R-021` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG130-NEG-022 | `SEG130-R-022` | MUT-010 structural / separator violation | Validation error citing SEG130-R-022 |
| BR-SEG130-NEG-023 | `SEG130-R-023` | MUT-010 structural / separator violation | Validation error citing SEG130-R-023 |

## Rule-catalog crosswalk

The rule each existing requirement restates. `ADOPTED` rows are pre-existing Test Team BRs recorded in the rule's catalog note.

| Requirement | Rule | Match |
|---|---|---|
| EMV-130-001 | `SEG130-R-001` | EQUIVALENT |
| EMV-130-002 | `SEG130-R-019` | EQUIVALENT |
| EMV-130-003 | `SEG130-R-017` | EQUIVALENT |
| BR-SEG130-TYPE | `SEG130-R-002` | ADOPTED (`test-json/segment-130-core-structure-package.json`) |
| BR-SEG130-LENGTH | `SEG130-R-003` | ADOPTED (`test-json/segment-130-core-structure-package.json`) |
| BR-SEG130-CA-KEY-CHECKSUM | `SEG130-R-005` | ADOPTED (`test-json/segment-130-core-structure-package.json`) |
| BR-SEG130-CHIP-DATA-LENGTH-FIELD | `SEG130-R-007` | ADOPTED (`test-json/segment-130-core-structure-package.json`) |
| BR-SEG100-APPR-CHIP-LENGTH | `SEG130-R-008` | ADOPTED (`test-json/appendices/appendix-r-segment-100-coverage.json`) |
| BR-SEG100-APPR-TLV-FORMAT | `SEG130-R-008` | ADOPTED (`test-json/appendices/appendix-r-segment-100-coverage.json`) |
| BR-SEG100-APPR-CROSS-FIELD | `SEG130-R-009` | ADOPTED (`test-json/appendices/appendix-r-segment-100-coverage.json`) |
| BR-SEG100-APPS-CA-FILE-HEADER | `SEG130-R-016` | ADOPTED (`test-json/appendices/appendix-s-segment-100-coverage.json`) |
| BR-SEG100-APPS-CA-KEY-RECORD | `SEG130-R-016` | ADOPTED (`test-json/appendices/appendix-s-segment-100-coverage.json`) |

## Open SME items raised by this catalog

> **SEG130-SME-008 (new):** Section 10.14.2.3 lists EMV-supported cards by brand/network name only. Provide the authoritative mapping from those 17 card products to Appendix E three-digit ATL105 card-type codes, so that EMV eligibility can be validated from the Prompt Code in Segment 100. Without this mapping, EMV card eligibility cannot be machine-checked from the message alone.

> **SEG130-SME-009 (new):** Confirm which issuers participate in EMV for the "Generic Proprietary" card product (`EMV-130-PF-001`), since the specification qualifies support as partial.

> **SEG130-SME-007** (`BR-SEG130-021`): Appendix T Table ID 002 is issuer-returned, so its legality in a Segment 130 request is unconfirmed. Confirm whether the request-side EMV Additional Information Indicator allow-list is {001} or {001, 002}.

## Implementation traceability

- Supported card list: ATL105 Section 10.14.2.3
- Supported transaction list: ATL105 Section 10.14.2.4
- PIN-mode rules: ATL105 Section 10.14.3
- Fallback/entry-mode rules: ATL105 Section 10.14.4, and [EMV entry mode and fallback note](emv-entry-mode-fallback-sme-tba-note.md)
- Segment 130 field rules: [segment-130-rule-catalog.json](coverage/segment-130-rule-catalog.json)
- This catalog does not use AI-generated JSON artifacts as business-requirement evidence.
