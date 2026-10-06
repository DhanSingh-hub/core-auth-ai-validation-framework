# Appendix I training: Variable Information

## Purpose and current stage

Train the independent Test Solution knowledge and validation baseline for Element 111,
with Element 112 length, Element 113 data, Segment 111 framing, nested selectors,
conditions, exceptions, and referenced appendices. AI-generated BR/TS/TC/TD are the
system under test, never the source of the expected rules.

This is repository-based knowledge/rule training, not model-weight fine-tuning.
No model-training service has been invoked.

**Current stage: all 78 defined tables represented in draft source-knowledge
batches; bounded Tables 001-009 predicate/fragment candidates implemented.
Semantic completeness, full-message training and AI equivalence remain incomplete.**
The generator locates 78 source-defined top-level table headings, not 81 consecutive
definitions. IDs 023, 061 and 074 are unlocated within the allocated range; this alone
does not establish that they are valid, reserved, or prohibited.

Nested-selector occurrences include both descriptive headings and field rows.
They are navigation evidence, not distinct sub-table definitions or completed rules.
Navigation spans begin at ID fields; the trainer must also read preceding titles,
notes, page continuations and context. No BR/TS/TC/TD or execution coverage is
claimed by this inventory.

## Authoritative and related evidence

- [ATL105 2026-3 source](../../extracted_text.txt): Appendix I, Section 12.10,
  Chapter 13 Elements 111, 112, 113, and relevant Chapter 11 message layouts.
- [Generated source inventory](../../../../test-output/test-json/knowledge/appendix-i-inventory.json):
  text/PDF SHA-256, exact source lines, printed appendix pages, PDF page markers,
  table IDs, and nested-selector occurrences.
- [First knowledge batch, Tables 001-009](../../../../test-output/test-json/knowledge/appendix-i-001-009.json):
  independent field attributes, contextual rules, source evidence and positive/
  negative test design, including the referenced Appendix J code and lifecycle rules.
- [Segment 111 knowledge](../segment-111/README.md): existing segment training,
  not exhaustive Appendix I table training.
- [Common handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md).
- [Communication register](../../../../registers/atl105-communication-register.json):
  unresolved framing is tracked once as `SEG111-SME-002`; existing ECA-specific
  length ambiguity remains `SEG111-SME-001`.

## Trainer decisions recorded on 2026-10-06

These are user-directed process decisions, not SME approval of business rules:

1. Train the complete appendix incrementally, including Elements 111/112/113 and
   nested sub-tables, rather than only the indicator.
2. Compare with existing immutable repository AI deliveries. Missing appendix
   artifacts remain explicit gaps; do not fabricate producer output.
3. Continue unambiguous training while the Table 009 framing issue remains
   REVIEW_REQUIRED under `SEG111-SME-002`.
4. Repair the communication-register prerequisite using the DL6-owned
   `SEGDL6-SME-006`, related to the still-open `SEGDL1-SME-003`.
   No framing answer was supplied by this repair.
5. Include directly referenced ATL105 rules (including Appendix J). Unavailable
   external-standard details remain review-gated.
6. Continue clear field-level Table 078 test design while its contradictory tax
   selectors and length/grammar examples remain review-gated. Do not issue a
   definitive serialized-message or AI equivalence verdict for those conflicts.
7. Build synthetic logical and full-message candidates for every unambiguous
   layout, preserving conflicted layouts as review-gated. Full-message generation
   is a subsequent gate, not a claim about the current logical/segment fragments.

## Training batches and gates

Every batch follows source inventory -> field/context model -> independent rules
-> BR -> TS -> TC -> synthetic TD -> serialization/mutations -> independent AI
intake -> technical readiness. Human review and approval are separate gates.

| Batch | Scope | Status |
|---|---|---|
| Foundation | Element 111 selector, Element 112 length, Element 113 data, Segment 111 framing | BOUNDED_ASCII_WIRE_PREDICATES_IMPLEMENTED_NOT_FULL_MESSAGE |
| 1 | Tables 001-009: UPC, program, AVS, verification, POS mode, currency, SIC, balance, partial approval | 17_DRAFT_RULES_9_SELECTED_PREDICATES_18_LOGICAL_CANDIDATES |
| 2 | Tables 010-022, including Visa payWave structured fields | KNOWLEDGE_DRAFTED_IN_010_048_BATCH_REVIEW_REQUIRED |
| 3 | Defined Tables 024-048; nested Table 030/032; cross-appendix dependencies | KNOWLEDGE_DRAFTED_98_RULES_IN_010_048_BATCH_REVIEW_REQUIRED |
| 4 | Table 049 and every source-defined nested layout | KNOWLEDGE_DRAFTED_60_RULES_24_LAYOUTS_REVIEW_REQUIRED |
| 5 | Tables 050-059, including Table 056 and all nested layouts | KNOWLEDGE_DRAFTED_IN_050_067_BATCH_REVIEW_REQUIRED |
| 6 | Defined Tables 060-073, including nested/conditional 060, 062, 063, 064, 066 | KNOWLEDGE_DRAFTED_ACROSS_050_067_AND_068_081_REVIEW_REQUIRED |
| 7 | Defined Tables 075-081, including Line Item 078 and Commercial Card 079 | KNOWLEDGE_DRAFTED_IN_068_081_BATCH_REVIEW_REQUIRED |
| Reconciliation | Field/rule inventory, all source notes/conditions, TS/TC/TD and AI evidence gaps | EXACT_TABLE_ID_AND_QUERY_LINK_RECONCILIATION_IMPLEMENTED_SEMANTIC_REVIEW_PENDING |

Across the five knowledge batches: 78 top-level tables, 231 top-level and 319
nested field declarations (550 total), 97 recorded nested layouts, 302 draft
rules, 86 notes, and 122 local pending issues. Declaration counts are not a
proof that every source condition is captured or that equivalent fields are
semantically distinct. Every pending issue links to a central SME query;
shared claims reuse query IDs rather than receiving contradictory answers.

[First-batch candidate chains](../../../../test-output/test-json/appendix-i-logical-candidates.json)
contain 9 draft BR/TS pairs, 18 TC/TD pairs, 18 concrete synthetic logical
fragments and 16 serialized Segment 111 fragments. These deliberately use a
separate bounded-artifact shape, not the canonical execution-ready contract.
Table 009 has logical values only: no disputed wire interpretation is emitted.
Table 004 uses a declared synthetic VISA context; it does not certify a network
or replace AMEX/DISCOVER/MASTERCARD cases. Other rule clauses and contexts remain
unimplemented. Full requests, responses, approved fixtures and executed business
cases remain zero; AI equivalence is unassessed.

`AppendixISegmentWireValidator` checks the common ASCII segment grammar, exact
declared lengths, contiguous records, separator placement and Element 113's
982-character bound; it does not impose ASCII on the specification as a whole.
Unsupported encoding is NOT_ASSERTABLE. `AppendixILogicalDataValidator` checks
selected representation predicates only; currency/SIC/ZIP/account allocation,
network eligibility and conditional table presence are not established.
Zero data length is rejected by Element 112's range, not an inferred universal
table-specific minimum. The Table 009 framing conflict remains REVIEW_REQUIRED.

Do not infer source completeness from the heading count. For each table record
every field, width/type, valid codes, requiredness, nesting, transaction/card/network
conditions, directional role, absent/present behavior, exception, effective-date
note, source anchor and unresolved reference. Separate source fact from derivation
and Test Team instruction.

The first source reading already shows that the old overview omits Program
Identifier PWC, that Table 005 refers to Appendix J, and that Table 009 framing
needs review. These observations are not completed semantic training.

## Reproduce discovery

Run from `regression-artifact-validator`:

```powershell
mvn '-Dtest=GenerateAppendixITrainingInventoryTest,AppendixITrainingKnowledgeTest,AppendixILogicalDataValidatorTest,AppendixISegmentWireValidatorTest,GenerateAppendixIFirstBatchCandidatesTest,CommunicationRegisterTest,Segment111*Test,ProducerNeutralContractTest' test

$jars = Get-ChildItem "$env:USERPROFILE\.m2\repository\com\fasterxml\jackson\core" `
    -Filter '*2.18.9.jar' -Recurse | Select-Object -ExpandProperty FullName
if ($jars.Count -ne 3) { throw 'Expected the three cached Jackson 2.18.9 core jars' }
java -cp ("target\classes;" + ($jars -join ';')) `
    com.coreauth.validator.coverage.GenerateAppendixITrainingInventory
if ($LASTEXITCODE -ne 0) { throw 'Appendix I inventory generation failed' }
java -cp ("target\classes;" + ($jars -join ';')) `
    com.coreauth.validator.coverage.GenerateAppendixIFirstBatchCandidates
if ($LASTEXITCODE -ne 0) { throw 'Appendix I candidate generation failed' }
```

The generator accepts optional ATL105-root and output-file arguments. Tests do
not publish files. Missing sources, boundaries, duplicate IDs and I/O failures
fail explicitly. Generation uses only the specification; AI input cannot change
its denominator.

## Baseline and completion boundary

The targeted existing Segment 111 and contract tests passed before the change.
The prior full-framework review of this same `Develop` baseline recorded four
failures; the authorized register repair addresses only the query-ownership issue.
This batch does not repair unrelated Appendix F/G lifecycle statuses or stale
dependency snapshots.

The inventory explicitly reports zero semantically trained tables, zero approved
rules, zero executed cases, zero certification and unassessed/null AI coverage.
Do not promote these measures until field-level training and independent evidence
have actually been produced and verified.

## Existing AI intake inventory (not coverage)

The immutable 2026-10-05 Run1 requirement catalog has 6,887 producer requirements,
including 992 whose primary `segment_number` is 111 and 858 distinct producer
`source_rule_id` values in that subset. These records include references outside
Appendix I and must not be mistaken for 992 independent Appendix I rules.
No entry in the `segment_ids` arrays explicitly names 111 in that delivery.

Table 049's 14 pending issues are centrally registered as `SEG111-SME-003`
through `SEG111-SME-016`, with local issue IDs linked through `registerId`.
The user chose to keep its conflicting purchase-restriction applicability
REVIEW_REQUIRED while continuing unrelated unambiguous training. Table 049
knowledge covers 75 fields, 24 nested layouts and 60 rule drafts; these counts
do not establish semantic approval or executable test coverage.

Catalog SHA-256:
`28f5e1360bc79a0e4ce39c7c68226b6da2cbe55719f35359b656601dc75fee35`.
Source:
`test-input/ai-solution/runs/2026-10-05/Run1/step5_requirements/approved/requirement_catalog.json`.

The 2026-09-29 phase-one index lists one representative Segment 111 selector
chain, TC-000016, labelled OK by its producer. This is not exhaustive Appendix I
TS/TC/TD evidence or independent acceptance. Complete chain/payload reconstruction
and semantic comparison remain pending. Do not carry producer approval/OK labels
into Test Solution approval or execution counts.
