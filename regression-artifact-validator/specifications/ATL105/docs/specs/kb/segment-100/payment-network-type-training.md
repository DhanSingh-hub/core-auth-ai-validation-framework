# Segment 100 Payment-Network Type Training

## Purpose and Scope

ATL105 Section 5 calls these **supported payment network types**, but the list is a mixed taxonomy: it includes payment methods, check services, card programs, and stored-value/loyalty products. Do not teach all entries as card brands or as technically equivalent networks.

The 16 `networkType` values in the training context model are normalized labels for the Section 5 leaves. `metadata.paymentNetworkType` is Test Solution/LLM training metadata; it is not an ATL105 wire field. The 16 leaf BRs below teach the classifier to recognize the source-listed category only. They do not assert transaction eligibility, Appendix E card-code mappings, authorizer mappings, or required companion segments.

## Leaf BRs (16)

| BR ID | Training label | Section 5 meaning | Classification note |
|---|---|---|---|
| `BR-SEG100-SEC5-LEAF-ACH-PREAUTHORIZED-DEBIT` | `ACH_PREAUTHORIZED_DEBIT` | ACH preauthorized debit cards | Payment method/program classification; do not map to a Prompt Code without separate evidence. |
| `BR-SEG100-SEC5-LEAF-ATM-PIN-DEBIT` | `ATM_PIN_DEBIT` | ATM debit: PIN debit | Debit subtype under ATM debit; not a named card brand. |
| `BR-SEG100-SEC5-LEAF-ATM-PINLESS-DEBIT` | `ATM_PINLESS_DEBIT` | ATM debit: PINless debit | Separate subtype; PINless processing eligibility is governed by its own rules. |
| `BR-SEG100-SEC5-LEAF-CHECK-GUARANTEE` | `CHECK_GUARANTEE` | Check guarantee services | Check-processing service, not a payment-card network. |
| `BR-SEG100-SEC5-LEAF-CHECK-VERIFICATION-BUYPASS` | `CHECK_VERIFICATION_BUYPASS` | Check verification provided by BUYPASS | Provider-specific check verification service. |
| `BR-SEG100-SEC5-LEAF-CHECK-VERIFICATION-EXTERNAL` | `CHECK_VERIFICATION_EXTERNAL` | Check verification services provided by others | External-provider check verification; Section 5 does not name the provider. |
| `BR-SEG100-SEC5-LEAF-CREDIT-NONPROPRIETARY` | `CREDIT_NONPROPRIETARY` | Credit cards: nonproprietary | Credit-card category; does not identify Visa, Mastercard, or another brand. |
| `BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY` | `CREDIT_PROPRIETARY` | Credit cards: proprietary | Proprietary credit category; do not infer the program or card code. |
| `BR-SEG100-SEC5-LEAF-EBT-FOOD` | `EBT_FOOD` | EBT food-stamp benefits | EBT benefit subtype; detailed EBT processing rules remain applicable. |
| `BR-SEG100-SEC5-LEAF-EBT-CASH` | `EBT_CASH` | EBT cash benefits | EBT benefit subtype; distinct from food benefits and eWIC. |
| `BR-SEG100-SEC5-LEAF-EBT-EWIC` | `EBT_EWIC` | eWIC | Distinct EBT program subtype; use the dedicated eWIC rules. |
| `BR-SEG100-SEC5-LEAF-PROPRIETARY-CARD` | `PROPRIETARY_CARD` | Proprietary cards | Broad proprietary-card category; keep distinct from the separately listed petroleum proprietary and fleet categories in this taxonomy. |
| `BR-SEG100-SEC5-LEAF-PETROLEUM-PROPRIETARY` | `PETROLEUM_PROPRIETARY` | Petroleum proprietary cards | Product/program classification; does not by itself require a fuel transaction. |
| `BR-SEG100-SEC5-LEAF-FLEET` | `FLEET` | Fleet cards | Fleet-card category; apply fleet-specific rules only when their source conditions are met. |
| `BR-SEG100-SEC5-LEAF-STORED-VALUE` | `STORED_VALUE` | Stored value cards | Stored-value category; activation, recharge, balance, and cancellation rules are separate. |
| `BR-SEG100-SEC5-LEAF-LOYALTY` | `LOYALTY` | Loyalty cards | Loyalty category; loyalty operations and responses are separate. |

Every leaf BR is anchored to ATL105 2026-3, Section 5, using a leaf-specific rule key in [the Section 5 matrix](../../../../test-output/test-json/section-5-payment-network-segment-100-matrix.json). The source text supplies the category names; the normalized enum labels and leaf BR IDs are Test Solution training identifiers.

## Existing BR Audit

| Existing BR | Finding | Disposition |
|---|---|---|
| `BR-SEG100-SEC5-NETWORK` | Valid after scope clarification. Section 5 supports an allowlist of the named classifications, but does not require a payment-network field in the ATL105 message. | Retained as `READY` for optional training metadata classification only, not wire-level presence. |
| `BR-SEG100-SEC5-CONTEXT` | Not proven as written. Section 5 does not specify a complete mapping from each leaf to Prompt Code, Appendix E card type, companion segments, lifecycle, and response. | Set to `REVIEW_REQUIRED`; it must not drive positive/negative compatibility assertions until each mapping has its own source anchor and evidence. |
| `CARD-100-001-*` card-type BRs | Separate dimension. Their allowlist and descriptions do not establish a one-to-one mapping to the 16 Section 5 categories. No direct conflict is asserted by keeping the dimensions separate. | Retain; do not infer network type from card type alone. |

The current `Segment100PaymentNetworkOracle` checks only that the metadata label is in its 16-value allowlist and that a Financial Request contains Segment 100. Its positive test loops over those 16 labels. This validates taxonomy recognition, **not** leaf-specific transaction/card compatibility or AI artifact coverage.

## BR -> TS -> TC -> TD Matrix

The [dedicated payment-network and receipt-ID traceability matrix](../../../test-output/test-json/payment-network-card-type-br-ts-tc-td-matrix.json) contains:

- **26 active canonical BRs:** 16 Section 5 leaf BRs, one Section 5 allowlist BR, and nine receipt-ID mapping BRs.
- **26 TSs / 26 TCs / 26 TDs:** one positive chain per Section 5 leaf, one unknown-network negative, and one draft mapping chain per receipt ID.
- **Two blocked BRs:** cross-dimension network compatibility and general receipt printing remain in `blockedRequirements`, outside the active canonical `businessRequirements` chain, while their applicability or output contract remains unresolved. They are not silently dropped or counted as active coverage requirements.

Every TD is marked `TEST_SOLUTION` and synthetic. Network TDs are minimal oracle-harness payloads, not converter-ready ATL105 messages. Receipt TDs are mapping harnesses, not rendered receipts; their cases remain `DRAFT_REVIEW_REQUIRED`. The matrix does not assert any Section 5-to-brand/card-code mapping.

## Learning and Test Rules

1. First classify the Section 5 entry by kind: payment method, check service, card/benefit program, or stored-value/loyalty product.
2. Preserve parent/child distinctions: ATM debit has PIN and PINless leaves; credit has nonproprietary and proprietary leaves; EBT has food, cash, and eWIC leaves; check has three different services.
3. Do not treat the normalized 16 leaves as 16 external network brands.
4. Do not infer card type, transaction type, approval routing, companion-segment presence, or lifecycle behavior from a leaf label alone.
5. For every proposed compatibility rule, cite the exact ATL105 section/table that joins the two dimensions. Otherwise mark it `REVIEW_REQUIRED`.
6. Test taxonomy allowlisting independently from semantic compatibility: include one accepted metadata example for each leaf and an unknown-label negative case. Add compatibility tests only after source-backed mappings are documented.

## Sources

- [ATL105 2026-3 extracted source, Section 5](../../extracted_text.txt)
- [Section 5 16-leaf matrix and BRs](../../../../test-output/test-json/section-5-payment-network-segment-100-matrix.json)
- [Financial card-type BRs](financial-card-type-business-requirements.md)
- [Segment 100 context model](../../../../test-output/test-json/knowledge/segment-100-context-model.json)
- [Independent network oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment100PaymentNetworkOracle.java)
- [Network oracle tests](../../../../../../src/test/java/com/coreauth/validator/Segment100PaymentNetworkOracleTest.java)
