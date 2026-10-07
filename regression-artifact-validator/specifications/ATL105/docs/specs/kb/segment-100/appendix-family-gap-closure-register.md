# Appendix Family Gap Closure Register

## Purpose

This register tracks the Test Solution BR -> TS -> TC -> TD work for every ATL105 Appendix A-AE family. The canonical appendix inventory records source/coverage status; this register separately records the artifact-chain gap. A `COVERED` appendix status is not, by itself, evidence of a complete executable BR chain.

`BR/TS/TC/TD` below are counts physically embedded in that appendix's `test-output/test-json/appendices/appendix-*-segment-100-coverage.json` record. They do not include referenced tests in other packages. A TD object without a payload or resolvable independent file is not an executable TD.

## Family-by-Family Audit

| Appendix | Family | Inventory status | BR/TS/TC/TD entries | Main Test Solution gap / next closure action |
|---|---|---|---:|---|
| A | TCP/IP message header | COVERED | 4/2/2/0 | Link the existing Item 01/02 payload cases to these Appendix BRs; retain streaming/converter behavior as an external runtime check. |
| B | POS purchase example | PARTIALLY_COVERED | 6/2/2/1 | Source-derived 100/102/111 composite fixture and example validator are present. Segment 100 compatibility and product order execute; Appendix B's product total (2,290) conflicts with §12.3's Segment 100 aggregate (2,387, including 97 tax). `SEG102-SME-006` remains open; converter validation and independent AI intake remain. |
| C | Authorizer codes | PARTIALLY_COVERED | 4/4/4/0 | Add code/name/type catalog validation and positive TDs for all 98 values; test invalid formats and unknown values. Keep production enablement configuration `REVIEW_REQUIRED`. |
| D | Valid state codes | PARTIALLY_COVERED | 3/3/5/11 | ANSI and alphabetical code sets now have independent vectors and validator tests. Close Element 12 Card Discretionary and numeric `00`/Table 041 only with their source-backed fixtures. See `APPD-REVIEW-001/002`. |
| E | Valid card type codes | PARTIALLY_COVERED | 61/57/57/57 | Table Load 56-value allowlist chains and one unknown-code negative are implemented; financial 36-code syntax tests exist. Nine special prompts have validator-harness chains; `900`/`997` remain blocked; feature effects and converter/business certification remain open. |
| F | Payment Systems Product Codes | PARTIALLY_COVERED | 5/3/3/0 | Referenced [classification/format package](../../../../test-output/test-json/appendix-f-product-code-chain-package.json): 381/1013/1013/1013; 380 source rows, all 1000 codes classified and 13 format TDs. Codes `895`/`896` stay review-required. Configuration/transaction eligibility, ordering, uniqueness and reconciliation remain separate; see the [training note](appendix-f-product-code-training.md). |
| G | Transaction type codes | PARTIALLY_COVERED | 5/3/3/0 | Referenced [classification package](../../../../test-output/test-json/appendix-g-transaction-type-chain-package.json) adds 23 source-code positives and seven unknown/malformed negatives (24/30/30/30). Keep Prompt Code/card composition, code 9's Card Type restriction, special-operation behavior, and MasterCard interpretation separate/review-gated; see the [training note](appendix-g-transaction-type-training.md). |
| H | Decline/intervention codes | PARTIALLY_COVERED | 4/4/4/0 | Add source-enumerated decline-code TDs for re-entry, retry, recurring-stop, and token behavior; keep code/family context explicit. |
| I | Variable Information layouts | PARTIALLY_COVERED | 6/5/5/0 | Add independently owned TDs for Table ID/subtable layouts, length/repetition and request/response direction; resolve remaining table/context questions. |
| J | POS Entry Mode | PARTIALLY_COVERED | 5/3/5/26 | All 14 PAN and 6 terminal values have executable recognition TDs, with composition invalid-PAN, invalid-terminal, and invalid-width cases. Context/lifecycle TDs remain review-required; add serialized Table ID 005 payloads and resolve channel/card/terminal restrictions and lifecycle persistence. |
| K | Additional Information layouts | PARTIALLY_COVERED | 49/46/46/47 | All 43 assigned Element 116 selectors have recognition-only chains; bounded Element 118 checks cover Tables 001/003/004/024–026/028–032/035–047. These are representation/value-set bounds only, not full-response semantics; Tables 035/038/044 use character-count caps, Tables 026/039 do not establish DST/lifecycle semantics, and Tables 042/043/047 do not establish network/card eligibility. Reserved 002 and unlisted 014/015/033 remain review-gated. Derive the 19 remaining table layouts, add physical Segment 112 response fixtures, and resolve network/external meanings before promoting semantic coverage. |
| L | Currency codes | PARTIALLY_COVERED | 3/2/2/0 | Referenced [classification chain package](../../../../test-output/test-json/appendix-l-currency-code-chain-package.json): 155/173/173/173; all 153 distinct codes (161 source rows) classified, 10 unknown-code and 10 malformed-format negatives. Codes `230`/`356`/`578`/`710`/`840`/`978` stay network-scope-conflict `REVIEW_REQUIRED`. Element 76 implied-decimal, Appendix I Table 006/`056`, Appendix M fixed-840 HIP subelement, and per-country network eligibility remain separate; see the [training note](../appendix-l/README.md). |
| M | EBT program data | PARTIALLY_COVERED | 4/2/2/4 | Appendix M-1/M-3 source examples and negative Total Length/detail-length TDs now execute through Segment103PayloadValidator. TAG 50 AMOUNT TYPE candidates remain `REVIEW_REQUIRED` under `SEG103-SME-009`; real AI artifacts/sample approval remain open. |
| N | Premium Gift Card Track 2 | PARTIALLY_COVERED | 6/2/2/4 | `AppendixNPremiumGiftCardTrackValidator` independently implements and tests all six BRs: 16-digit Account Number + Visa mod-10 check digit, Track II field-width layout, BIN(6)/Partial-Account(9)/check-digit(1) decomposition, serial-number derivation, Start/End Sentinel exclusion from the transmitted packet, and PVKI presence-only handling. See the [training note](appendix-n-premium-gift-card-training.md). BIN-registry membership, Service Code/PVV semantics, and real AI-generated fixtures remain external/review-required. |
| O | Payment token terminology | REVIEW_REQUIRED | 7/2/2/7 | Existing TD records have no payload. Add format-only independent fixtures where source-supported; keep authentic cryptogram/token lifecycle assertions external/review-required. |
| P | TransArmor administrative responses | REVIEW_REQUIRED | 2/2/2/0 | Obtain the separate TransArmor administrative-command specification and real contract before deriving response BRs or fixtures. |
| Q | POS Condition Codes | PARTIALLY_COVERED | 5/2/2/0 | Enumerate Table 030 values and create valid/invalid context-sensitive fixtures for device, presentation, security and transaction conditions. |
| R | EMV chip data | PARTIALLY_COVERED | 4/3/3/5 | Existing TD objects lack payloads. Add synthetic TLV/length/cross-field fixtures; genuine cryptogram authenticity remains out of scope without certified kernel/HSM evidence. |
| S | CA public key files | REVIEW_REQUIRED | 3/2/2/3 | TD objects lack payloads. Validate source-defined file framing with synthetic fixtures; real AID-to-key resolution requires authorized key material/configuration. |
| T | EMV Additional Information | REVIEW_REQUIRED | 3/3/3/3 | TD objects lack payloads. Add synthetic Table 001/002 response cases and echo assertions; confirm request-side legality before promotion. |
| U | Visa Digital Wallet | REVIEW_REQUIRED | 7/3/3/0 | Obtain complete Appendix U field contract and wallet artifacts; keep DWO/staged-funding lifecycle claims blocked pending source/business review. |
| V | Moneris layouts | REVIEW_REQUIRED | 6/3/3/0 | Obtain the external Moneris request/response contract and implementation configuration before creating executable fixtures. |
| W | Download data layouts | REVIEW_REQUIRED | 2/1/1/0 | Get approved DL7/terminal-configuration examples; validate Data Section framing and table behavior when available. |
| X | Online Refund/Refund Authorization | REVIEW_REQUIRED | 3/2/2/0 | Add source-backed Table ID 032/Subtable 11 data and lifecycle-persistence cases; confirm the intended request context. |
| Y | 3-D Secure | REVIEW_REQUIRED | 4/4/4/4 | Existing TD objects lack payloads. Synthetic ECI/format fixtures are feasible; TAVV/CAVV and AAV/DSRP authenticity require genuine network-issued data. |
| Z | Stored Credential | REVIEW_REQUIRED | 7/5/5/0 | Obtain source-anchored initial/CIT/MIT/recurring fixtures and network reference carryover evidence; do not infer mappings across brands. |
| AA | TransArmor processing | REVIEW_REQUIRED | 4/3/3/1 | TD object lacks payload. Confirm external TransArmor account/token rules and add independent format cases only for source-supported assertions. |
| AB | Purchase Repayment | REVIEW_REQUIRED | 4/3/3/1 | TD object lacks payload. Confirm card-brand/MCC/repayment eligibility with the source or business owner before generating cases. |
| AC | Country codes | PARTIALLY_COVERED | 2/2/2/0 | Add full valid country-code vector and negative cases; retain acquirer/configuration-specific acceptance as review-required. |
| AD | Real Time Account Updater | REVIEW_REQUIRED | 3/2/2/0 | Confirm participating acquirer/merchant configuration and obtain applicable AI artifacts; then test trigger indicator and account-update dependencies. |
| AE | Estimated/incremental authorization | REVIEW_REQUIRED | 4/3/3/1 | TD object lacks payload. Confirm MCC/merchant eligibility and obtain a representative lodging/rental lifecycle fixture before promotion. |

## Family-Wise Execution Order

1. **Deterministic source enumerations:** C, F, H, I, J, K, L, M, Q, AC. Extract each table into an independent oracle, then build positive/negative TD vectors. For I/K/M, preserve exact request/response and companion-segment context.
2. **Core package link closure:** A, B, G. Reuse the existing underlying Test Solution packages only after verifying payload provenance and adding explicit BR/TS/TC/TD IDs to the Appendix records.
3. **Partially covered specialized but synthesizable rules:** E, R, S, T, X, Z, AA, AB, AE. Build only format/structural checks supported by ATL105; keep external authenticity/configuration questions blocked.
4. **External/configuration-bound families:** N, O, P, U, V, W, Y, AD. Record the external owner/artifact needed; do not substitute generated payloads for provider-issued or configured evidence.
5. **Close deferred Appendix D items:** Keep `APPD-REVIEW-001` and `APPD-REVIEW-002` out of the covered numerator until Table 041 and Element 12 fixtures/tests are source-confirmed and executed.

## Completion and Coverage Rules

- Preserve each appendix's source BR and separate source status from chain status.
- Require resolvable canonical `requirementIds`, `scenarioIds`, and `testData[].testCaseIds`; include source anchors on each stage.
- Each TC must resolve to independent Test Solution TD with payload or a validated physical file; metadata-only TD records do not count as execution evidence.
- Mark test data as `EXECUTABLE`, `EXTERNAL_FIXTURE_REQUIRED`, or `REVIEW_REQUIRED`; external/review-required TDs do not count as executable coverage.
- Do not change an appendix to `COVERED` while any in-scope BR lacks an accepted chain or unresolved blocker is being reported as covered.
- Report BR/TS/TC/TD numerators separately by appendix. The denominator is the source-backed in-scope BR set, not the number of AI artifacts or the number of rows generated.
