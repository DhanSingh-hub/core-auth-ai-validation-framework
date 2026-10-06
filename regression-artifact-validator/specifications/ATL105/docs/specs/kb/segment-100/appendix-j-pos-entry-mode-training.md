# Appendix J POS Entry Mode Training

**Specification:** BUYPASS ATL105 2026-3, Appendix J-1/J-2<br>
**Data:** Element 113, normally carried as Variable Information Element 111 Table ID `005`<br>
**Status:** `PARTIALLY_COVERED`; source-derived recognition is implemented, contextual and lifecycle certification remain open.

## Rule Boundaries

Appendix J defines a three-digit Point-of-Service Entry Mode:

- Positions 1-2: one of 14 PAN Entry Mode values.
- Position 3: one of 6 Terminal PIN Entry Capability Mode values.

Valid component values do not by themselves prove that a combination is appropriate for a card, network, transaction, or POS device. The recognition oracle therefore reports syntax/value validity only and leaves context as `NOT_EVALUATED`.

## Source-Listed Values

| PAN mode | Meaning |
|---|---|
| `00` | Unspecified |
| `01` | Manual/keyed |
| `02` | Magnetic stripe, partial track data |
| `04` | Bar Code Scan / OCR read for EMV |
| `05` | ICC read, CVV data reliable |
| `07` | Contactless chip read |
| `10` | Credential on file |
| `79` | Contact/contactless chip fallback to manual |
| `80` | Chip-capable terminal, unaltered track data / contact EMV fallback to swipe |
| `82` | Contactless mobile commerce device |
| `86` | Dual-interface contactless chip to contact chip |
| `90` | Magnetic stripe, full track data |
| `91` | Contactless magnetic-stripe read with track data |
| `95` | ICC read, CVV data unreliable |

| Terminal mode | Meaning |
|---|---|
| `0` | Unspecified |
| `1` | PIN capability for EMV Contact and Contactless |
| `2` | No PIN capability for EMV Contact and Contactless |
| `3` | mPOS software-based PIN entry capability |
| `6` | PIN pad inoperative |
| `8` | Contactless Magnetic Stripe Read capability |

The values are preserved at their source-defined widths. Do not collapse the two-digit PAN mode and one-digit terminal mode into separate wire fields.

## Source-Defined Context and Lifecycle Rules

- Void and completion transactions retain the original PAN mode, except that a follow-up referencing an original PAN mode `90` uses PAN mode `02`.
- PAN mode `10` applies to specified Visa, Mastercard, Discover, and Amex stored-credential contexts; it does not apply to the first credential verification/storage transaction. Discover has additional exclusions for QR/barcode/payment-code and issuer-stored or wallet-provisioned account data.
- Discover stored-card transactions must use PAN mode `10`; PAN mode `82` must not be used when the cardholder uses stored card-account information.
- Terminal mode `8` is reserved for a contactless MSR attached to a device without chip capability. EMV Contact and Contactless use terminal mode `1`, not `8`.
- Without contactless MSR, a POS device with PIN debit enabled uses terminal mode `1`; with PIN debit disabled it uses mode `2`.
- Terminal mode `3` applies only to Mastercard Mobile POS. Hardware-PIN-pad mPOS uses mode `1`; software PIN entry without a PIN pad uses mode `3`.

These rules require card/network, stored-credential, POS hardware, or lifecycle context. The code-value oracle must not infer that context from Element 113 alone.

## Test Solution Evidence

The independent oracle is `AppendixJPosEntryModeOracle`. Its tests verify that the 14 PAN values and 6 terminal values are present in the cited extracted-source range, recognize the three-digit composition, and reject malformed or unlisted components. `contextStatus` remains `NOT_EVALUATED` for syntactically valid values.

The appendix coverage package currently has five BRs, three TSs, five TCs, and 26 TDs. Each of the 14 PAN values and 6 terminal values has an executable code-recognition TD. Composition also has positive, invalid-PAN, invalid-terminal, and invalid-width TDs. Card/network restrictions and lifecycle behavior have review-required TDs only; they are not certified coverage. The harness does not yet validate a serialized Segment 100 request carrying Element 111 Table ID `005` and Element 113.

## Remaining Work

1. Add serialized request fixtures with Element 111 Table ID `005` and a three-byte Element 113 value.
2. Add focused context tests for credential-on-file, Discover restrictions, terminal capability hierarchy, and mPOS, preserving the source-defined conditions.
3. Add original/follow-up transaction fixtures for persistence and the `90` to `02` exception.
4. Keep behavior `REVIEW_REQUIRED` until the independent fixtures validate and the required SME/TBA review is recorded.

## Source

- ATL105 2026-3, Appendix J-1/J-2: [extracted specification](../../extracted_text.txt), lines 32491-32576.
- Element 111 Table ID `005` layout: Appendix I, [extracted specification](../../extracted_text.txt), lines 27672-27683.