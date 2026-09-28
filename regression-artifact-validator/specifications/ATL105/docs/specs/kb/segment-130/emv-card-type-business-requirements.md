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

## Open SME items raised by this catalog

> **SEG130-SME-008 (new):** Section 10.14.2.3 lists EMV-supported cards by brand/network name only. Provide the authoritative mapping from those 17 card products to Appendix E three-digit ATL105 card-type codes, so that EMV eligibility can be validated from the Prompt Code in Segment 100. Without this mapping, EMV card eligibility cannot be machine-checked from the message alone.

> **SEG130-SME-009 (new):** Confirm which issuers participate in EMV for the "Generic Proprietary" card product (`EMV-130-PF-001`), since the specification qualifies support as partial.

## Implementation traceability

- Supported card list: ATL105 Section 10.14.2.3
- Supported transaction list: ATL105 Section 10.14.2.4
- PIN-mode rules: ATL105 Section 10.14.3
- Fallback/entry-mode rules: ATL105 Section 10.14.4, and [EMV entry mode and fallback note](emv-entry-mode-fallback-sme-tba-note.md)
- Segment 130 field rules: [segment-130-rule-catalog.json](coverage/segment-130-rule-catalog.json)
- This catalog does not use AI-generated JSON artifacts as business-requirement evidence.
