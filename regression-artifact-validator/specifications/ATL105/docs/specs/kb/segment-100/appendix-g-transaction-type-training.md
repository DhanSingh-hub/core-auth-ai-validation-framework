# Appendix G Transaction Type Training

## Scope

Appendix G lists 23 valid one-character Transaction Type codes used in Element 78 Prompt Code. The independent [catalog](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixGTransactionTypeCatalog.java) classifies all 23 as 13 standard financial types and 10 specialized types. This catalog answers only “is this exact code listed, and how does Appendix G describe it?” It does not establish transaction eligibility, prompt composition, operation behavior, or network acceptance.

## Decision Rules

1. Preserve the exact one-character code, including its case. Classify only the Appendix G allowlist; unknown and malformed values fail code recognition.
2. Keep the 13 standard financial types separate from the ten specialized meanings. A specialized code must not be silently validated as a standard financial request.
3. The standard financial oracle remains limited to `0`, `3`, `4`, `5`, `6`, `7`, `8`, `A`, `B`, `C`, `S`, `U`, and `Z`. The Appendix G catalog's specialized result is not permission to run those codes through the financial Segment 100 validator.
4. Code `9` denotes Special Transactions. Appendix G says it can only be combined with Card Type `00` and `81-97`. Preserve this source rule, but keep its enforcement `REVIEW_REQUIRED` until the table's Card Type notation and Element 78's 3-/4-character Prompt Code forms are reconciled with Appendix E.
5. Do not infer a full operation contract from a one-line code description. Codes `D`, `E`, `K`, `L`, `M`, `N`, `Q`, `T`, and `V` need their source-defined special-flow rules. `T` additionally references an external TransArmor document for detailed behavior.
6. Lifecycle behavior is a separate cross-message rule set. Use the lifecycle catalog and pair validator; a recognized code alone does not prove an original/follow-up pairing is valid.
7. Preserve the MasterCard note as a separate network-context gate. The preauthorization/final-authorization mapping and hold durations do not change the Appendix G code allowlist.

## Independent Evidence

The [classification chain package](../../../../test-output/test-json/appendix-g-transaction-type-chain-package.json) contains 24 BRs, 30 scenarios, 30 cases, and 30 executable synthetic TDs: one positive chain for each of the 23 listed codes plus seven unknown/malformed negatives. Each TD is produced by the Test Solution catalog harness and is linked to its BR, TS, TC, and Appendix G source anchor.

The [Appendix G coverage package](../../../../test-output/test-json/appendices/appendix-g-segment-100-coverage.json) keeps Prompt Code composition `PARTIALLY_COVERED` and MasterCard context `REVIEW_REQUIRED`. The legacy 13-code financial oracle and lifecycle validators remain separate evidence. This is source-list classification coverage, not AI-artifact, converter, special-operation, Prompt Code/card-type matrix, or lifecycle certification.

## SME Review Handoff

Keep these questions open for the next SME/Test Team review. Do not promote Appendix G beyond `PARTIALLY_COVERED` based only on the classification package.

| Review item | Current evidence | Question to resolve |
|---|---|---|
| Code `9` Prompt Code composition | Appendix G says code `9` may only combine with Card Type `00` and `81-97`; Element 78 separately describes financial four-character and special three-character Prompt Codes. | Which wire representation and Appendix E code namespace does the `00` / `81-97` restriction refer to? What exact positive and negative Prompt Code cases should be enforced? |
| Specialized type flows | Appendix G names `D`, `E`, `K`, `L`, `M`, `N`, `Q`, `T`, and `V`; specialized behavior is distributed across ATL105 sections. Code `T` also references an external TransArmor processing document. | Confirm message-family, required fields, response expectations, configuration dependencies, and which operations can be validated with synthetic independent TDs. |
| MasterCard authorization context | Appendix G maps Authorization/Completion/Auth Cancellation to Preauthorization and Sale/Sale Reversal/Merchandise Return/Void of Merchandise Return to Final Authorization; it also gives 30-day and 7-day hold periods. | Confirm applicability and the source-backed assertion boundary for current processing/hold behavior; identify any network rules or configuration inputs that must remain external. |
| Lifecycle link certification | Existing transaction-type and lifecycle validators cover selected pairs and correlation checks; that evidence is separate from Appendix G code recognition. | Review the allowed original/follow-up pair matrix and confirm each claimed negative is source-backed before connecting it to Appendix G BRs. |

**Current disposition:** The 23-code catalog and its 30 independent classification TD chains are source-classification evidence only. Preserve `REVIEW_REQUIRED` for unresolved semantics and keep AI artifact, converter, network, and production-configuration certification outside this package until separately evidenced.

## Source References

- [ATL105 2026-3 Appendix G](../../extracted_text.txt)
- [ATL105 2026-3 Element 78](../../extracted_text.txt)
- [Appendix G coverage package](../../../../test-output/test-json/appendices/appendix-g-segment-100-coverage.json)
- [Appendix G classification chains](../../../../test-output/test-json/appendix-g-transaction-type-chain-package.json)
- [13-code standard financial oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment100TransactionTypeOracle.java)
- [Lifecycle validator](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment100MultiStepCoverageValidator.java)
- [Special transaction BR baseline](../../../../test-output/test-json/special-transaction-type-br-baseline.json)