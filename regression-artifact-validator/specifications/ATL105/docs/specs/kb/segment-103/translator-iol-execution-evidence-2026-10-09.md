# Translator and IOL Execution Evidence - 2026-10-09

**Evidence class:** External Jira translator / integration / IOL evidence. This is not ATL105 specification text and does not certify this repository's Java validators.

**Handling:** The source PDFs contain real or production-like payment identifiers and serialized request/response material. This note intentionally excludes payloads, card/PIN/track values, merchant identifiers, trace IDs, and raw logs. Do not copy those values into Test Solution fixtures. Use synthetic or separately approved masked data.

## Source Register

| Ticket | Source and status | SHA-256 | Evidence limit |
|---|---|---|---|
| `MSB-10325` | PIN debit IOL failure; created 2026-06-16; unresolved / To Do | `D543869FE1056E799BDFAABF042C396AE3AFAB843576969FFFF5D7C2FE853CD3` | One QA report of `INVALID TRAN 2G`; no root cause, approved mapping, or successful retest is recorded. |
| `MCH-40201` | Petro IOL Canada regression; resolved 2026-06-29 | `ACB5784E3725BD26F8AD00CE24894DCD9852641852D325A7514AB0A745CA1758` | Primarily an MVP/support-scope matrix; it does not provide a complete executable case/result set. |
| `MCH-39388` | eWIC Voucher Clear / Force Post; resolved 2026-08-25 | `124C84738CCC5DD0EB0C424272FC464B891370212ABDFB4FC0C3EAC352C1E278` | Acceptance criteria and a QA mapping comment exist, but the transaction declined and downstream support/test-data questions remain. |
| `MCH-39380` | CP eWIC Segment 103 translator mapping; resolved 2026-07-15 | `39AB77C0676559E56E12AFD25D65969F691A679564D1EA9EE92D0D39794204F6` | Field mappings and one development authorization test are recorded; this does not establish complete operation or field coverage. |

Source files remain in the user's evidence folder and were read in place. The hashes above identify the reviewed exports; they do not establish source authority or approve interpretation.

## CP eWIC Translator Mapping Candidates

`MCH-39380` gives useful converter-level mapping candidates for a card-present eWIC request:

| ATL105 / Segment 103 input | Commerce Hub request target in the ticket |
|---|---|
| Clerk ID | `transactionInteraction.additionalPosInformation.cashierId` |
| Voucher ID | `transactionDetails.merchantInvoiceNumber` |
| WIC Product Data `PS` UPC/PLU (bitmap bit 2) | `orderData.itemDetails[].productUPC` or `productPLU` |
| WIC Product Data `PS` item price (bitmap bit 6) | `orderData.itemDetails[].amountComponents.grossAmount` |
| WIC Product Data `PS` purchase quantity (bitmap bit 7) | `orderData.itemDetails[].quantity` |
| WIC Discount Amount | `amountComponents.priceAdjustments[].adjustmentAmount`, with `adjustmentType=DISCOUNT` |

The ticket says PIN entry is required but PIN translation is not. It records a development authorization test and deployment of translator version `2.128.0` to QA-CHD. Treat the mappings as integration-test candidates; do not infer that the single authorization test validates every mapping or flow.

## eWIC Voucher Clear and Prompt Context

`MCH-39388` describes a card-present Voucher Clear / Force Post for a previously authorized but uncaptured eWIC transaction. Its criteria call for POS/CAT, EBT/EWIC context, prompt `0086`, Commerce Hub Force Post, and Segment 100, 103, and 111 content; PIN entry is required and PIN translation is not. A QA comment says the mapping was validated, while also reporting a transaction decline; a later comment asks whether the downstream processor supports Voucher Clear and requests appropriate test data.

ATL105 2026-3 itself lists prompt `0086` for both eWIC Purchase Completion and Voucher Clear. Preserve operation/lifecycle context when testing the mapping; prompt code alone cannot distinguish the two operations. The ticket's downstream decline is an observed result, not a universal expected response. Keep processor support and approval outcome `REVIEW_REQUIRED` until reproducible, authorized test data and a resolved response oracle exist.

The existing [eWIC prompt/lifecycle note](ewic-prompt-lifecycle-sme-tba-note.md) and [validator](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment100EwicValidator.java) already cover the source-level prompt mapping. This evidence suggests an adapter-level mapping test, not a new ATL105 prompt rule.

## Petro IOL Canada Scope

`MCH-40201` defines an MVP scope of ATL105, Bulloch device, CAD currency `124`, Canadian merchant ID, and Canadian card. Its matrix names track, EMV, TransArmor, and manual entry; in-store and AFD origins; purchase/auth, completion/capture, refund, void, timeout cancel/reversal, EBT balance inquiry, gift-card operations; and Interac, major card brands, and fleet products. The ticket also says CAD and Canadian postal-code values are passed through without Commerce Hub validation.

Use this as a candidate **scope matrix** for gap analysis, not as an expected-outcome catalog. Per-flow eligibility, processor-specific exceptions, executed results, and stable case identifiers are not established by the matrix alone. The ticket's pass-through statement is integration context and must not become an ATL105 field-validation rule.

## PIN Debit IOL Failure

`MSB-10325` reports a PIN-debit IOL request in QA on 2026-06-15 receiving `INVALID TRAN 2G`; the Jira issue remains unresolved. It can seed a failure-reproduction question, but it does not identify whether the cause is transaction mapping, test configuration, authorizer support, or another integration dependency. Do not encode `2G` as the expected outcome for PIN debit generally.

## Training Boundary and Next Evidence

- Repository Segment 103 validation covers ATL105-defined structure and applicable source rules; it does not execute Commerce Hub translators, Canadian processor routing, or host authorization behavior.
- Keep translator field mapping, prompt-to-API transaction mapping, and host response outcome as separate assertions.
- For each promoted integration case, retain the operation, network/card context, environment/configuration, original/follow-up relationship, sanitized request and response, stable test-case ID, and an independently reviewed expected result.
- These PDFs do not change the Segment 103 rule catalog, approval status, training denominator, or execution certification.
