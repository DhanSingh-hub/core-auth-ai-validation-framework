# ATL105 Card Type ID Business Requirements

## Scope

This draft covers the **receipt Card Type ID list** in ATL105 2026-3 Section 10.1.7.3. It contains seven named brand/network labels and two generic card categories. These identifiers are receipt labels; they are not Appendix E Element 14 card-type codes, Appendix C Element 7 authorizer codes, or Section 5 payment-category enums.

All requirements below are `DRAFT_REVIEW_REQUIRED` pending Test Team review and receipt-level positive/negative examples. The source lists the values, but does not define a complete mapping from each Appendix E card code, Section 5 category, or authorizer code to a receipt Card Type ID. No such mapping is inferred here.

## Draft BRs

| BR ID | Draft requirement | Expected Card Type ID | Source |
|---|---|---|---|
| `BR-SEG100-RECEIPT-CARD-TYPE-ID-PRINT` | The card type shall be printed on each credit-card receipt. | A supported Card Type ID corresponding to the card type | ATL105 2026-3, Section 10.1.7.3 |
| `BR-SEG100-RECEIPT-CARDTYPE-AMEX` | When the card type is American Express (Amex), the receipt Card Type ID shall be `AX`. | `AX` | ATL105 2026-3, Section 10.1.7.3, `Amex: AX` |
| `BR-SEG100-RECEIPT-CARDTYPE-DEBIT` | When the card type is represented by the generic Debit label, the receipt Card Type ID shall be `DB`. This does not identify a debit network. | `DB` | ATL105 2026-3, Section 10.1.7.3, `Debit: DB` |
| `BR-SEG100-RECEIPT-CARDTYPE-DINERS-CLUB` | When the card type is Diners Club, the receipt Card Type ID shall be `DCI-DISC`. | `DCI-DISC` | ATL105 2026-3, Section 10.1.7.3, `Diners Club DCI-DISC` |
| `BR-SEG100-RECEIPT-CARDTYPE-DISCOVER` | When the card type is Discover Network, the receipt Card Type ID shall be `DS`. | `DS` | ATL105 2026-3, Section 10.1.7.3, `Discover Network: DS` |
| `BR-SEG100-RECEIPT-CARDTYPE-EBT` | When the card type is represented by the generic EBT label, the receipt Card Type ID shall be `EB`. This does not identify a specific EBT program or processor. | `EB` | ATL105 2026-3, Section 10.1.7.3, `EBT: EB` |
| `BR-SEG100-RECEIPT-CARDTYPE-JCB` | When the card type is JCB, the receipt Card Type ID shall be `JCB-DISC`. | `JCB-DISC` | ATL105 2026-3, Section 10.1.7.3, `JCB JCB-DISC` |
| `BR-SEG100-RECEIPT-CARDTYPE-MASTERCARD` | When the card type is MasterCard, the receipt Card Type ID shall be `MC`. | `MC` | ATL105 2026-3, Section 10.1.7.3, `MasterCard: MC` |
| `BR-SEG100-RECEIPT-CARDTYPE-VISA` | When the card type is Visa, the receipt Card Type ID shall be `VISA`. | `VISA` | ATL105 2026-3, Section 10.1.7.3, `Visa: VISA` |
| `BR-SEG100-RECEIPT-CARDTYPE-STAR-SIGNATURE-DEBIT` | When the card type is STAR Signature Debit, the receipt Card Type ID shall be `STAR`. | `STAR` | ATL105 2026-3, Section 10.1.7.3, `STAR Signature Debit: STAR` |

**Count:** 9 mapping BRs (7 named labels and 2 generic categories), plus 1 shared receipt-printing BR.

## Existing BR Validation

| Existing artifact/rule | Validation result | Boundary |
|---|---|---|
| `CARD-100-001-020` (`020` is labeled `Credit`) | No conflict with this receipt list when scoped to the Appendix E Financial Transaction Prompt Code table. | Appendix E has multiple tables: Table Load Response labels code `020` as Visa Fleet Credit, while the Financial Transaction Prompt Code table labels `020` as Credit. Do not transplant the Table Load Response label into the Prompt Code rule. |
| Other `CARD-100-001-*` BRs | No direct conflict found. They validate Element 14 financial Prompt Code card-code allowlisting and category-level use. | They do not define receipt Card Type ID values. |
| `BR-SEG100-SEC5-NETWORK` and 16 Section 5 leaf BRs | No direct conflict found. They classify broad payment methods/services/programs in training metadata. | They do not define receipt abbreviations or map brands to Appendix E codes. |
| `BR-SEG100-SEC5-CONTEXT` | Correctly remains `REVIEW_REQUIRED`. | No full source-backed join currently establishes the mapping among Section 5 category, Appendix E card code, authorizer, receipt ID, and transaction context. |
| Appendix C authorizer codes | No direct conflict found. | Authorizer codes identify processors/authorizers in response context; they are not receipt Card Type IDs. |

## Do Not Infer

- Do not treat `AX`, `MC`, `VISA`, `DS`, `DCI-DISC`, `JCB-DISC`, or `STAR` as Appendix E Element 14 card codes.
- Do not infer that generic `DB` identifies STAR, PULSE, Interlink, NYCE, or another debit network.
- Do not infer that generic `EB` distinguishes food benefits, cash benefits, or eWIC.
- Do not equate receipt ID `MC` with Appendix E card code `080` (MasterCard Fleet) without a source rule that establishes the relationship.
- Do not treat the nine receipt IDs as a complete list of all authorizers, schemes, programs, or Section 5 categories supported by ATL105.

## Validation Needed

- Positive receipt cases for all nine Card Type ID values, with source-appropriate card context.
- Negative cases for an incorrect/unknown ID and for confusing receipt IDs with Appendix E three-digit card codes.
- Confirm the field location and exact receipt variants where the ID is mandatory; Section 10.1.7.3 states that card type prints on all credit card receipts, while its list also includes generic Debit, EBT, and STAR Signature Debit labels.
- Add cross-dimension mappings only when another ATL105 rule explicitly joins the dimensions; otherwise keep the case `REVIEW_REQUIRED`.
