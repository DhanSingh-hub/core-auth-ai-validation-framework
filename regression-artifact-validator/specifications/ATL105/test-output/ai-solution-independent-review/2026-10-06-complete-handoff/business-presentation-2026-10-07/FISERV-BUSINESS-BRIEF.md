# Fiserv Business Brief | ATL105 AI Validation

As of October 7, 2026. Scope: October 5 Run1; figures are inventories unless explicitly stated otherwise.

## ATL105 | AI validation business briefing

- AI produced substantial BR, scenario, case and payload inventories.
- Independent reconstruction makes the full structural graph auditable.
- Structural links and matching field values do not establish full business equivalence or host acceptance.
- Decision today: review the discrepancies and authorize a defined evidence-qualified scope.

**Speaker note:** Lead with progress, then explain that inventory, semantic matching and execution certification are different measures. Do not describe 53.13% as validated semantic coverage.

## 1 | What did the AI Solution create?

| Artifact | Count |
| --- | --- |
| BR | 6,887 |
| TS | 12,679 |
| TC | 21,123 |
| TD request files | 21,210 |
| Logical TCs with physical TD | 21,096 / 21,123 |

- Each of the 21,210 request files has a metadata companion; metadata is not another TD.
- Flows have multiple payload legs. Therefore physical TD files and logical TC counts differ.
- 27 TCs have no physical TD; 3,732 scenarios have no TC.

**Speaker note:** Say 21,210 physical request-data files covering 21,096 logical cases. Do not say 42,420 test data artifacts by counting metadata twice.

## 2 | Is there a complete AI traceability matrix?

- Yes, a matrix document was delivered; its displayed content is truncated.
- AI declares 61,107 rows, but Markdown shows 2,000 rows and 200 BR details.
- Our independent reconstruction has 61,044 leaves and represents all 6,887 BRs.
- 3,659 BRs have TC links (53.13% internal linkage); 8,947 scenarios have TCs.
- Producer's 3,661 linked BRs and 92 orphan scenarios differ from frozen 3,659 and 155.
- All 200 displayed BR-level statuses agree; 75 leaf labels inherit a BR's FULLY_TRACED status.

**Speaker note:** A matrix exists, but not all BRs have complete generated chains. The 63 additional producer rows are unconfirmed without the omitted producer edges. Reconstruction is independent evidence, not the missing producer JSON.

## 3 | What has the Test Solution identified?

| Inventory | BR | TS | TC | TD |
| --- | --- | --- | --- | --- |
| Populated aggregate | 725 | 1,343 | 1,424 | 1,416 |
| Structural package | 1,326 | 1,961 | 2,043 | 2,057 |
| Approved-chain package | 0 | 0 | 0 | 0 |

- Independent segment rule-catalog inventory: 601 rules across 49 segments.
- Structural package includes placeholders: 618 TS, 619 TC and 641 TD.
- These are inventory counts, not 1:1 AI matches or execution-ready chains.

**Speaker note:** Use populated aggregate as the primary answer and explain structural inventory separately. Neither is an approved semantic denominator for the October AI delivery. The approved-chain package is empty, not proof that no source rules exist.

## 4 | How are AI and Test artifacts matched?

- Freeze delivery/version and source hashes; preserve original producer artifacts.
- Normalize formats through adapters; preserve each producer's local IDs.
- Match source anchors plus transaction/card/network/lifecycle context.
- Compare BR conditions, obligations, exceptions and expected behavior.
- Trace TS objective > TC assertions > TD predicates and dependencies.
- Assign CONFIRMED, REVIEW_REQUIRED or MISSING with evidence and owners.

**Speaker note:** Different IDs and legal data values can match. Literal constants must match; variable data may differ if both satisfy the same rule and preserve dependencies. Similar titles, shared IDs and AI confidence are only candidate evidence.

## 5 | How many perfect matches can we claim?

- No evidence-qualified full-chain perfect-match count is established for October Run1.
- Zero confirmed full-chain matches can currently be claimed; this is not proof of zero real equivalence.
- 200/200 BR status agreement is status reconciliation, not business-rule equivalence.
- 40/40 synthetic probes passed two scoped predicates; they are not forty approved AI tests.
- The 522 atomic candidates belong to September Run2 and are not October confirmed matches.

**Speaker note:** Answer honestly: current confirmed count is none established. We have concrete field-level alignment and partial candidates, not an approved complete cross-producer matrix.

## 6A | Real example: different data, same predicate

| Layer | AI Solution | Independent Test Solution |
| --- | --- | --- |
| BR | REQ-SRC-ATL105-PDF-001:097 | BR-SEG100-E86-001 |
| TS | SC-1486 | SCN-SEG100-E86-001 |
| TC | TC-001436 | TC-SEG100-E86-001 |
| TD | TC-001436.json | TD-SEG100-E86-001 |
| Sequence value | 000001 | 100001 |

- Both actual values satisfy exactly six ASCII digits.
- Disposition: verified field-predicate alignment; partial full-chain comparison candidate.
- AI expected response is empty; independent fixture readiness is not declared. Range/allocation/lifecycle and complete outcomes remain unproven.

**Speaker note:** Do not call this a perfect BR match. AI BR also carries range/application language, while the Test BR shown is a six-digit representation obligation. Show the full statements in the companion brief.

## 6B | Real example: identity aligns, context differs

- Identity: AI TC-001436 and Test TC-SEG100-ID-001 both carry Segment Type 100.
- AI identity BR REQ-SRC-ATL105-PDF-001:095 is generic; Test BR-SEG100-ID-001 explicitly fixes Standard Segment type to 100.
- Partial approval: AI value 0 vs Test value 5.
- The Test BR requires value 5 in Amex prepaid balance-receipt context.
- AI value 0 does not prove that value-5 context. Different values are not automatically interchangeable.
- These examples prove scoped alignment or a context gap, not approved full-chain equivalence.

**Speaker note:** The partial-approval example is a not-proven context comparison, not an automatic AI defect. A positive value-0 case may legitimately test a different branch.

## 7 | How can we filter artifacts for analysis?

- Run report: no-TC search, generation cause, producer verdict and evidence conditions; selected CSV export.
- Coverage view: overall, segment, transaction target, response-code declaration and scenario-type summaries.
- Semantic report: 100-case intent/condition filters, artifact/BR search, detail and CSV export.
- Code-1 queue: producer transaction label, source/context filters and paginated export.
- Full reconstruction CSV: filter BR, scenario, TC, leaf status and TD paths in Excel.
- Scope warning: no-TC view covers 3,732 scenarios; semantic case view covers 100, not the entire delivery.

**Speaker note:** Demo one scoped filter at a time and state its denominator. Coverage selectors are summary dimensions, not a full 21,123-case explorer. Unattributed cases remain separately in the full JSON.

## Business decision and next evidence

- Request the complete producer matrix and source catalog hashes/changed artifacts.
- Resolve the 63 additional rows and BR :521 / :3226 mappings without inventing edges.
- Adjudicate a narrow business scope against independent source-derived requirements.
- Supply approved control fixtures, actual wire, authoritative host expectations and a permitted test environment.
- Track structural, semantic and executed coverage separately; retain every assessment history point.

**Speaker note:** Close with the specific evidence and authorization needed. Source/SME review and processor authorization are business controls, not issues to bypass for a green report.

## BR Meaning and Supporting-Chain Validation

We assess these in two separate steps. BR-level equivalence and complete-chain alignment are different conclusions.

### 1. Compare BR Meaning

Check whether AI BR(s) cover the independent Test BR's applicability and business context, triggering conditions, required behavior and outcome, and exceptions and restrictions.

Both interpretations must be supported by the specification. Similar wording or shared IDs is not enough.

### 2. Validate the Supporting Chain

Then check whether the linked artifacts actually implement that meaning.

| Artifact | Question |
|---|---|
| TS | Does the scenario exercise the BR's condition and behavior? |
| TC | Does the case assert the required result? |
| TD | Does the data activate the intended condition and preserve dependencies? |

### Illustrative Lifecycle Example

Suppose both BRs say: Completion must reuse the authorization's Sequence Number.

AI could use 000001 for both messages; Test could use 100001 for both. Different values are acceptable because reuse within each transaction pair is preserved.

If the AI case checks only that Sequence Number has six digits, its chain proves formatting, not lifecycle reuse.

This is an illustrative example, not a claim of an approved lifecycle match in the assessed delivery. The actual 000001 versus 100001 comparison below establishes six-digit formatting only.

### Interpret the Result

- Equivalent BR statements + inadequate tests: BR-level match, incomplete test coverage.
- Complete links + wrong business assertion: structurally complete, not semantically covered.
- Equivalent BRs + aligned TS/TC/TD: aligned full-chain design, still not proof of successful host execution.

## Actual Artifact Comparison Appendix

Examples are drawn from frozen AI artifacts and the independent Test Solution populated aggregate. No full-chain perfect match is certified.

### Sequence format

| Layer | AI Solution | Test Solution |
|---|---|---|
| BR | REQ-SRC-ATL105-PDF-001:097 | BR-SEG100-E86-001 |
| TS | SC-1486 | SCN-SEG100-E86-001 |
| TC | TC-001436 | TC-SEG100-E86-001 |
| TD | TC-001436.json | TD-SEG100-E86-001 |
| BR statement | Sequence Number must be one of: 000001-999999, 100000-199999, the ATL105 message format. | Sequence Number must be six digits. |
| Field value | 000001 | 100001 |
| Outcome | None | Validator PASS |

Disposition: FIELD_PREDICATE_ALIGNMENT_ONLY. Whole-chain confirmed: false.

### Segment identity

| Layer | AI Solution | Test Solution |
|---|---|---|
| BR | REQ-SRC-ATL105-PDF-001:095 | BR-SEG100-ID-001 |
| TS | SC-1486 | SCN-SEG100-ID-001 |
| TC | TC-001436 | TC-SEG100-ID-001 |
| TD | TC-001436.json | TD-SEG100-ID-001 |
| BR statement | Segment Type must be one of: Segment, Segment, Load Data Segment, Segment, Segment, Segment. | Segment Type must be 100 for the Standard Message Data Segment. |
| Field value | 100 | 100 |
| Outcome | None | Validator PASS |

Disposition: FIELD_VALUE_ALIGNMENT_ONLY. Whole-chain confirmed: false.

### Partial approval context

| Layer | AI Solution | Test Solution |
|---|---|---|
| BR | REQ-SRC-ATL105-PDF-001:142 | BR-SEG100-E121-002 |
| TS | SC-1486 | SCN-SEG100-E121-002 |
| TC | TC-001436 | TC-SEG100-E121-003 |
| TD | TC-001436.json | TD-SEG100-E121-003 |
| BR statement | Partial Approval Indicator must be one of: 0 Not specified (default)/Not supported, This value is the default value., 1 Supported, response., Approval transaction processing., Express prepaid cards., back in response.. | Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context. |
| Field value | 0 | 5 |
| Outcome | None | Validator PASS |

Disposition: NOT_EQUIVALENT_CONTEXT_OBLIGATION_NOT_PROVEN. Whole-chain confirmed: false.

## Evidence and Demo Links

- [Run report and filter views](../AI-ARTIFACT-FILTER-VIEW.html)
- [Semantic report and scoped cases](../SEMANTIC-FIRST-BATCH-REPORT.html)
- [Complete independent matrix CSV](../reconstructed-requirement-matrix.csv)
- [Presentation facts and hashes](presentation-facts.json)
- [Example artifact extracts and hashes](presentation-examples.json)

Do not present September candidates, placeholder chains, metadata companions or source-rule inventories as approved October full-chain matches.

## Five-Minute Demo Checklist

1. Open the Run report: show 12,679 scenarios, 21,123 cases and the execution boundary.
2. Open No-TC scenarios: select Transaction unresolved (2,026), then Response deferred (1,706). Reset between demonstrations.
3. Export selected CSV; explain that these filters cover the 3,732 no-TC backlog only.
4. Open Coverage: select Segment-wise linkage and point to Segment 100; label it structural linkage, not semantic coverage.
5. Open the semantic report: select negative intent (25) or source-predicate failure (2); show one case detail and source evidence.
6. Open Code-1 queue: select Financial Transaction Request (416 candidates); expected TC responses remain empty.
7. Show Matrix & history: complete reconstruction, 63 unresolved rows and retained previous reports.
8. Show the comparison appendix: 000001 versus 100001 satisfies the same six-digit predicate, but full-chain equivalence is unconfirmed.

## Statements to Avoid

- '53.13% of the business rules are independently validated.' It is producer-internal linkage.
- '522 perfect matches.' Those are September atomic candidates, not October full-chain confirmations.
- 'The Test Solution has 1,326 approved BRs.' The structural package includes review-required catalog records and placeholders.
- 'Different TD values always match.' Literal constants and context-sensitive values cannot be freely substituted.
- '40 passed tests prove production readiness.' These are synthetic isolated predicate probes, not executed host transactions.