# Segment 112 Canonical Source Anchors

Verified against ATL105 release 2026-3, Section 12.11 (Additional Information Data Segment) and Section 13.2, Elements 115-118, and cross-checked against the Section 12.11 opening information-type list.

These anchors are the shared semantic identity for independently generated business requirements, scenarios, test cases, and test data. Producer-local IDs may differ; the anchor vocabulary must remain stable.

## Anchor Format

```text
ATL105 | version | section | segment | element | rule
```

Use the same values in the canonical artifact contract:

```json
{
  "specification": "ATL105",
  "version": "2026-3",
  "section": "12.11",
  "segment": "112",
  "element": "116",
  "rule": "additional-information-indicator"
}
```

Values are compared case-insensitively and with surrounding whitespace removed.

## Segment 112 Anchors

| Canonical rule | Section | Segment | Element | JSON field |
| --- | --- | --- | --- | --- |
| `segment-type` | `12.11` | `112` | `85` | `SegmentType` |
| `segment-length` | `12.11` | `112` | `84` | `SegmentLength` |
| `additional-information-indicator` | `12.11` | `112` | `116` | `AdditionalInformationIndicator` |
| `additional-information-length` | `12.11` | `112` | `117` | `AdditionalInformationLength` |
| `additional-information-value` | `12.11` | `112` | `118` | `AdditionalInformation` |

## Serialization And Eligibility Anchors

These rules do not represent a single data element, so they use a named rule value instead of a numeric element.

| Canonical rule | Section | Segment | Meaning |
| --- | --- | --- | --- |
| `segment-112-max-length` | `12.11` | `112` | Segment 112 does not exceed 999 alphanumeric characters |
| `segment-112-position-financial-response` | `12.11` | `112` | Segment 112 appears only at the end of a Financial Transaction response |
| `segment-112-origin-host` | `12.11` | `112` | BUYPASS (Host) originates transmission of Segment 112 content |
| `additional-information-section-repetition-max-990` | `12.11` | `112` | The Additional Information Section (Elements 116/117/118) repeats once per Indicator, up to 990 bytes combined |
| `segment-112-required-when-flag-set` | `13` (element dictionary) | `100` (Financial Transaction Response context) | Segment 112 is required in a Financial Transaction Response only when Element 115 equals 1 |

## Element 116 Value Anchors (Documented Codes)

| Code | Canonical rule suffix | Meaning |
| --- | --- | --- |
| `001` | `balance-information` | Gift/EBT/credit/phone card balance |
| `002` | `reserved` | Explicitly reserved, no value |
| `003` | `avs-nvs-result` | AVS and/or NVS result |
| `004` | `cvv-result` | CVV/CVV2/CVC2/CID/DTVV result |
| `005` | `eca-telecheck-trace-id` | ECA/TeleCheck Trace ID |
| `006` | `eca-telecheck-denial-record-number` | ECA/TeleCheck Denial Record Number |
| `007` | `eca-telecheck-return-check-data` | ECA/TeleCheck Return Check Data |
| `008` | `loyalty-information-v1` | Loyalty Information Version 1 |
| `009` | `visa-product-result` | Visa product result |
| `010` | `loyalty-information-v2` | Loyalty Information Version 2 |
| `011` | `user-data-information` | User Data Information |
| `012` | `discover-retrieval-reference-number` | Discover Network Retrieval Reference Number (fixed 15 bytes) |
| `013` | `expiration-date-mmyy-transarmor-verifone` | Expiration Date MMYY, TransArmor - VeriFone (fixed 4 bytes) |
| `014` | `reserved` | Not documented in Section 12.11 — resolved 2026-09-26 as reserved/invalid, same disposition as `002` |
| `015` | `reserved` | Not documented in Section 12.11 — resolved 2026-09-26 as reserved/invalid, same disposition as `002` |
| `016` | `pin-on-receipt-information` | PIN-on-Receipt Information |
| `017` | `buypass-host-card-type` | BUYPASS Host Card Type (fixed 3 bytes) |
| `018` | `pinless-debit-information` | PINless Debit Information (fixed numeric 3) |
| `019` | `visa-spend-qualified-indicator` | Visa Spend Qualified Indicator Information |
| `020` | `host-prompts-information` | Host Prompts Information |
| `021` | `re-price-data-response-information` | Re-Price Data Response Information |
| `022` | `cavv-result` | CAVV Result |
| `023` | `mcx-reference-number` | MCX Reference Number |
| `024` | `carwash-indicator` | Carwash Indicator |
| `025` | `language-indicator` | Language Indicator |
| `026` | `dst-response` | DST Response |
| `027` | `universal-unique-identifier` | Universal Unique Identifier |
| `028` | `transaction-identifier` | Transaction Identifier |
| `029` | `par-data` | PAR (Payment Account Reference) Data |
| `030` | `merchant-advice-code` | Merchant Advice Code |
| `031` | `daf-indicator` | DAF Indicator |
| `032` | `agreement-id` | Agreement ID |
| `033` | `reserved` | Not documented in Section 12.11 — resolved 2026-09-26 as reserved/invalid, same disposition as `002` |
| `034` | `host-based-purchase-restriction` | Host-based Purchase Restriction |
| `035` | `cardholder-additional-information-result-code` | Cardholder Additional Information Result Code |
| `036` | `account-type` | Account Type |
| `037` | `account-funding-source` | Account Funding Source |
| `038` | `transaction-link-identifier` | Transaction Link Identifier |
| `039` | `transaction-link-action-indicator` | Transaction Link Action Indicator |
| `040` | `fraud-score` | Fraud Score |
| `041` | `fraud-score-reason-code` | Fraud Score Reason Code |
| `042` | `authentication-data-quality-indicator` | Authentication Data Quality Indicator |
| `043` | `token-update-first-use-indicator` | Token Update First Use Indicator |
| `044` | `merchant-tran-id` | Merchant Tran ID |
| `045` | `applied-special-service` | Applied Special Service |
| `046` | `voyager-restriction-code` | Voyager Restriction Code |
| `047` | `visa-category-code` | Visa Category Code |

## Usage Rule

An AI artifact and a Test Validation artifact match semantically only when they resolve to the same canonical anchor and compatible expected behavior. Similar text or similar local IDs are not sufficient evidence of equivalence. In particular, do not match a Segment 112 anchor against Segment 130/131 "EMV Additional Information" text that happens to share Element 118's name — see [SEG112-SME-006](segment-112/segment-112-sme-tba-input-register.md).

## Source References

- Section 12.11, Additional Information Data Segment: extracted specification lines 12625-12676.
- Section 13.2, Elements 115-118: extracted specification lines 22490-22660.
- Appendix K, Additional Information Data Layouts (Table IDs 001-047): referenced but not transcribed in this pass — see [SEG112-SME-004](segment-112/segment-112-sme-tba-input-register.md).
