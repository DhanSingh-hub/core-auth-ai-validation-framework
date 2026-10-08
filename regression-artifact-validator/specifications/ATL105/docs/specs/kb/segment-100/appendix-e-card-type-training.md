# Appendix E Card Type Training

## Scope

ATL105 Appendix E contains three distinct code families. Keep them separate in BRs, validators, test data, and coverage counts. A code valid in one family is not automatically valid in another.

| Appendix E family | Count | Wire/message use | Current Test Solution evidence |
|---|---:|---|---|
| Table Load Response codes | 56 entries: 43 card-type entries and 13 terminal feature/configuration entries | DL blocks in a Table Load Response; not the same table as financial Prompt Code types | `AppendixETableLoadCardTypeCodes` and `TableLoadPayloadValidator` accept all 56 listed values and reject unknown values; per-value BR -> TS -> TC -> independent TD chains exist. Feature effects and production configuration remain separate. |
| Financial Transaction Prompt Code card types | 36 codes | Three-digit Element 14 card type combined with an Appendix G transaction type in Prompt Code 78 | 36 per-code BRs in `financial-card-type-business-requirements.md`; `Segment100CardTypeOracle` and tests cover all 36 syntactic card values. Transaction/card business eligibility remains a separate check. |
| Special Transaction Prompt Codes | 11 codes: `900`-`905`, `981`, `990`, `995`-`997` | Three-character special Prompt Code values, separate from financial Prompt Codes | Nine have independent validator-harness chains. `900` and `997` remain blocked; harness validation is not business/converter certification. |

## Existing BR Audit

The machine-readable [Appendix E coverage package](../../../../test-output/test-json/appendices/appendix-e-segment-100-coverage.json) now has five family-level BRs: financial card types, Prompt Code composition, Conoco's internal-code boundary, special-code separation, and the Table Load family boundary. The [canonical appendix inventory](../../../../test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json) marks Appendix E `PARTIALLY_COVERED`. The 36 financial card-code BRs are maintained separately in the financial card-type catalog.

The [actual execution pair record](coverage/segment-100-observed-prompt-code-pairs.json) captures 16 financial Prompt Code pairs observed in 46 certification requests: transaction types `0`, `3`, `4`, `5`, `7`, `8`, `S`, and `U`, each with card types `011` and `020`. These are useful independent positive examples for their recorded POS/CA/refund/reversal/cancel/void contexts. The workbook's runner assertion passed on all 46 cases; that is not proof every transaction was approved or a complete card/transaction eligibility table. Keep unobserved combinations `REVIEW_REQUIRED` and do not promote this evidence to a universal allowlist.

- The financial allowlist BR is supported for syntactic recognition of the 36 financial card types. It must not be described as proving every transaction/card combination is business-valid.
- Revalidation found and corrected a financial-card BR conflict: Appendix E's Financial Transaction Prompt Code table gives card type `075` as `Citgo fleet` with type `Proprietary`; the former BR incorrectly directed it to fleet rules. Do not infer the type from the word "fleet" in the display label.
- The same numeric code may have different names by table: `020` is `Visa Fleet Credit` in Table Load Response but `Credit` in the Financial Transaction Prompt Code table. The financial `020 = Credit` BR is valid in its stated scope.
- Code `040` is named `Loyalty` and has Appendix E Card Type Description `Proprietary`. The BR identifies the named financial card type; it does not infer all proprietary processing rules from that description.
- Prompt Code composition checks the financial code namespaces; applicability and product/card restrictions remain separate.
- Conoco's backend code `089` is not the merchant Prompt Code; merchants use `090`, as Appendix E states.
- Special Prompt Code family separation is distinct from the special prompt operations' business behavior.
- The package now includes 56 code-specific Table Load BR chains plus an unknown-code negative. Those test `DL1.CardType` allowlist recognition, not every terminal feature effect or production setting.
- Nine special prompts (`901`-`905`, `981`, `990`, `995`, `996`) have structured validator-harness chains in the [special-prompt chain package](../../../../test-output/test-json/appendix-e-special-prompt-chain-package.json). Codes `900` and `997` remain blocked on exact message-family/payload contracts.
- Harness-validator success is not a claim of converter, receipt-rendering, AI-artifact, or business-operation certification.

## Deferred Table Load Behavior

All 56 Table Load values now have per-code allowlist BR -> TS -> TC -> independent TD chains. Remaining work is outside the allowlist assertion:

1. Validate device behavior/side effects for each terminal feature code where Appendix E describes an effect.
2. Confirm production/configuration enablement separately; a code can be source-valid but unavailable in an environment.
3. Add DL block fields and dependencies only where their message-family source defines them.

## Deferred Special Prompt Coverage

Appendix E's 11 special Prompt Codes are separate from the financial card oracle. The new package chains nine values through existing source-backed validators; `900` and `997` remain `REVIEW_REQUIRED`. The nine chains validate only the linked message-family validator scope, not full operation behavior or converter output. Keep them `VALIDATOR_HARNESS_SCOPE_ONLY` until SME/Test Team and converter-level gates pass.

## Learning Rules

1. Select the Appendix E table by message family before validating a code.
2. Preserve leading zeroes and the source's exact code width.
3. A financial Prompt Code is a transaction type plus a three-digit financial card type; a special Prompt Code is its separate source-defined three-character value.
4. Table Load Response values include both accepted card types and terminal features; do not feed all of them to the financial card-type oracle.
5. Use only source-supported transaction/card combinations. A cross-product generated from two independent allowlists is not proof of business eligibility.
6. Keep terminal feature effects, production enablement, prompts `900`/`997`, and special-operation/converter certification `REVIEW_REQUIRED`; do not report Appendix E as fully covered while these gaps remain.

## Source References

- [ATL105 2026-3 Appendix E](../../extracted_text.txt)
- [Appendix E coverage package](../../../../test-output/test-json/appendices/appendix-e-segment-100-coverage.json)
- [Financial card-type BRs](financial-card-type-business-requirements.md)
- [Special transaction-type BR draft](../../../../test-output/test-json/special-transaction-type-br-baseline.json)
- [Special Prompt Code validator-harness chains](../../../../test-output/test-json/appendix-e-special-prompt-chain-package.json)
- [Table Load validator](../../../../../../src/main/java/com/coreauth/validator/canonical/TableLoadPayloadValidator.java)
- [Table Load validator tests](../../../../../../src/test/java/com/coreauth/validator/TableLoadPayloadValidatorTest.java)
- [Financial card-type oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment100CardTypeOracle.java)
- [Financial card-type oracle tests](../../../../../../src/test/java/com/coreauth/validator/Segment100CardTypeOracleTest.java)
