# Network-first business requirements review

Primary groups: **seven named networks plus two generic categories (Debit and EBT)**. Credit is a card program, NOT a network. Section 5 leaves are secondary classification only.

BA navigation: BUYPASS -> ATL105/2026-3 -> card program -> network/generic group -> transaction category -> presence / entry mode / channel -> flow/leg -> contextual BR -> TS -> TC -> TD. The network-first index below is an alternate review view of the same contexts.

## Scope and counts

- section5CategoryCount: 16
- financialCardTypeCount: 36
- receiptLabelCount: 9
- transactionCodeCount: 23
- generalLifecycleFamilyCount: 5
- specialPromptCodeCount: 11
- specializedLifecycleProfileCount: 6
- lifecycleProfileCount: 11
- draftRequirementCount: 145
- networkGroupCount: 7
- genericGroupCount: 2
- primaryGroupCount: 9
- reviewProfileCount: 12
- contextualDraftBrCount: 176
- contextualDraftScenarioCount: 176
- linkedSharedRequirementCount: 64
- unmappedSharedRequirementCount: 81
- completeChainCount: 0
- authoredTestCaseCount: 0
- draftLessonCount: 141
- openQuestionCount: 10
- eligibilityReviewCellCount: 44
- approvedEligibilityRuleCount: 0
- authoredTestDataCount: 0
- executedCaseCount: 0

The original **145 shared requirements are retained once**, with stable IDs. Contextual BRs/TSs reference shared rules rather than pretending they are newly approved atomic BRs. Every shared row is linked to a group or explicitly retained in the unmapped library. The 16 Section 5 leaves, 36 financial card codes and nine receipt labels are secondary, overlapping dimensions, NOT 61 networks. All 23 Appendix G codes and 11 Appendix E special prompts are inventoried, including specialized operations. Five general lifecycle families and six program-specific profiles are modeled. This is not a claim of exhaustive source semantics, all optional subfunctions or arbitrary composed flows.

## Deliverables

- [Network review index](NETWORK-REVIEW.md): all seven network and two generic groups, with BR/TS summaries and gaps.
- [Contextual requirements](requirements-review.csv): primary BA hierarchy columns, source-linked conditional BRs/TSs and response columns.
- [Shared requirements library](shared-requirements-library.csv): all original 145 rows, not network-specific BRs.
- [Shared library mapping](shared-library-mapping.csv): every shared ID, linked groups/profiles or explicit unmapped disposition.
- [Network/flow review](network-flow-review.csv): explicitly selected conditional profiles, not a Cartesian product or executable policy. Unsupported program combinations are not fabricated.
- [BR -> TS -> TC -> TD gaps](chain-gaps.csv): real BR/TS links, empty TC/TD IDs and explicit missing evidence.
- [Questionnaire](ba-sme-questionnaire.csv): ten grouped questions, SEG100-SME-004.
- [Draft teaching lessons](training-lessons.json): classifications and curated conditional source rules only.
- [Structured review](review.json), [provenance](manifest.json), [email draft](BA-SME-EMAIL.md).

## Format migration

Review format version 2 replaces the previous 25 mixed primary contexts and 850 Cartesian candidates with nine primary groups and explicit conditional flow selections. requirements-review.csv is now the contextual BA table; the original 145-row CSV shape is preserved in shared-requirements-library.csv. JSON requirements remains the shared library; businessNavigation contains contextual businessRequirements/testScenarios and empty testCases/testData. The directory and existing shared IDs stay stable. Rebuild/check commands are unchanged.

## Boundaries

Concrete TCs and TDs are NOT_AUTHORED and every chain is incomplete. No placeholder ID is counted as an artefact. Actual results are Not Executed. These supplement IDs do not change canonical BR counts or coverage. No runtime policy, model weights, source approval, TC/TD fixtures, converter or host certification is created. All statements remain draft. Source matching establishes location and byte hashes, NOT semantic correctness. Issuer status, merchant eligibility, routing, fleet external sources, security, settlement and source ambiguities remain separately review-gated. Visa Appendix AE stays 2026-3; Appendix AF 2026-4 is an excluded, question-gated overlay, not silently adopted.

Commercial usage is orthogonal to funding program. Presence, entry mode and channel are separate, with unresolved values explicitly retained; a contactless entry is not an e-commerce channel. Prepaid/commercial eligibility and unresolved library mappings are BA gaps, not implicit support. ATL104, ISO and Gen2 are diagram examples only, not enabled specifications.

Return BA decisions in a separate copy; rebuilding overwrites generated files. Authorized answers must be persisted through the existing SME workflow before curated inputs change.
