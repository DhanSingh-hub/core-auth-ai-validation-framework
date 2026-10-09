# Appendix I training: Variable Information

## Purpose and current stage

Train the independent Test Solution knowledge and validation baseline for Element 111,
with Element 112 length, Element 113 data, Segment 111 framing, nested selectors,
conditions, exceptions, and referenced appendices. AI-generated BR/TS/TC/TD are the
system under test, never the source of the expected rules.

This is repository-based knowledge/rule training, not model-weight fine-tuning.
No model-training service has been invoked.

**Current stage: all 78 defined tables represented in draft source-knowledge
batches; bounded Tables 001-009 predicate/fragment candidates implemented;
11 selected logical examples for Tables 010-015 and 017 now independently
evaluated and linked using the canonical review-only contract.
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
8. Perform full-message validation offline first. Offline structural/predicate
   results must not be described as host acceptance.
9. Calculate new synthetic Segment 100 lengths using Section 12.1 while retaining
   the Appendix B example conflict as `SEG111-SME-121`. Its seventeen printed
   fields total 82 ASCII bytes, but its declared length is 078. This decision
   does not approve the rest of the example or make it a ready full-request seed.
   [Source-example inspection](../../../../test-output/test-json/knowledge/appendix-i-full-message-source-inspection.json)
   records the discrepancy without copying account or verification values.
10. Continue training on `Appendix-I`. AI Solution comparison is deferred by the
    user. Any subsequent merge to `Develop` requires the user's explicit approval;
    neither a passing test batch nor a pushed checkpoint is completion sign-off.

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

## Remaining-layout synthetic candidate stage

The subsequent source-only batches contain 178 scoped candidate records across
the remaining 69 tables. Records include concrete synthetic strings, structured
fields/byte arrays, mutation descriptions, proposed predicate outcomes and
explicit excluded claims. These are not machine-executed predicates or canonical
BR/TS/TC/TD packages; multiple variants/outcomes per record are not comparable
to a count of executed cases. Consumer adapters must preserve these boundaries.

| Candidate batch | Records | Source scope |
|---|---:|---|
| [010-048](../../../../test-output/test-json/knowledge/appendix-i-candidates-010-048.json) | 75 | All 38 tables classified; 82 distinct draft rule references |
| [049](../../../../test-output/test-json/knowledge/appendix-i-candidates-049.json) | 24 | All 24 defined nested selectors classified; fleet/framing conflicts retained |
| [050-067](../../../../test-output/test-json/knowledge/appendix-i-candidates-050-067.json) | 42 | 17 table-level and 25 nested-layout records; 129 field paths classified |
| [068-081](../../../../test-output/test-json/knowledge/appendix-i-candidates-068-081.json) | 37 | All 13 tables and 24 nested layouts classified; 078 blob grammar withheld |

Every record remains REVIEW_REQUIRED / LOGICAL_FRAGMENT. Classification does not
prove exhaustive conditions, variants or tested semantics. Full-message assembly
requires source-backed Data Sections 1/2/3, an applicable synthetic Segment 100
context, optional/required companion segments and exact wire reconstruction.
Those complete request fixtures and adapters are still pending, not silently
substituted by a fragment or producer sample.

## Executed scoped logical batch (continuation)

[Canonical review-only package](../../../../test-output/test-json/appendix-i-scoped-training/package.json)
contains 11 BR/TS pairs and 22 TC/TD pairs for selected examples from Tables
010-015 and 017. Each TD has a physical JSON file, the canonical
`testCaseIds` link, and REVIEW_REQUIRED readiness. Source anchors use a
distinct scoped-check identity: these partial predicates must not be matched
as though they implement every clause of the original source rule.

`AppendixIScopedCandidateValidator` checks TAP component widths/hexadecimal and
zero fill, tax mappings including the meaningful blank, printed market mappings,
special-payment mapping and supplied recurring eligibility, user-data width/
logical echo/nonforwarding, supplied Discover reference equality, and the
fixed fraud indicator in the selected reversal context. Context comes from
explicit synthetic preconditions; no real BIN, assignment, issuer trace,
network availability or host response is verified.

[Execution and rule ledger](../../../../test-output/test-json/appendix-i-scoped-training/evidence.json)
pins the source and knowledge/example hashes, records actual findings for all
22 logical evaluations, and reconciles all 302 draft rules. Eleven supplied
targeted mutations are detected by their specific predicate. This is not
the appendix-wide mutation threshold or complete coverage of those rules.
The ledger identifies partial evidence **in this scoped batch**; it does not
discard or override earlier first-batch evidence. Proposed outcomes in the
original 178 example records remain unchanged.

Generation fails on a changed source, invalid input scope, missing candidate,
missing rule, absent mutation path, wrong target outcome or invalid canonical
links. Unsupported candidates remain NOT_ASSERTABLE and incomplete context
remains REVIEW_REQUIRED. No conflicted length header is serialized by this
batch. The response object records NOT_EXECUTED, not a fabricated response.

The [training status](../../../../training-status.json) records Appendix I
separately under `appendixTraining.I`, using the existing nine phases.
The 302 draft rules are not added to the numbered-segment catalog denominator.
Source completeness, full messages, approval and final certification remain
unestablished. This scoped batch follows the same framework gates without
promoting a logical fragment to execution-ready request data.

## PAR request indicator predicate

`AppendixIParRequestOracle` checks only the explicit ATL105 request representation
from Appendix I-30: Table `032`, Sub-Table `14`, fixed length `001`, value `Y`.
An absent indicator remains `REVIEW_REQUIRED` when merchant opt-in context is
unknown. Appendix K-24 Table `029` response formatting is checked separately by
`AppendixKDataLayoutValidator`. Requesting PAR does not make a PAR response
mandatory, establish authorizer support, or prove merchant eligibility; the
MSUC-14867 simulator history is not a universal response oracle.

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
mvn '-Dtest=GenerateAppendixITrainingInventoryTest,AppendixITrainingKnowledgeTest,AppendixILogicalCandidateKnowledgeTest,AppendixILogicalDataValidatorTest,AppendixISegmentWireValidatorTest,GenerateAppendixIFirstBatchCandidatesTest,GenerateAppendixIRepresentativeIntakeTest,CommunicationRegisterTest,Segment111*Test,ProducerNeutralContractTest' test

$jars = Get-ChildItem "$env:USERPROFILE\.m2\repository\com\fasterxml\jackson\core" `
    -Filter '*2.18.9.jar' -Recurse | Select-Object -ExpandProperty FullName
if ($jars.Count -ne 3) { throw 'Expected the three cached Jackson 2.18.9 core jars' }
java -cp ("target\classes;" + ($jars -join ';')) `
    com.coreauth.validator.coverage.GenerateAppendixITrainingInventory
if ($LASTEXITCODE -ne 0) { throw 'Appendix I inventory generation failed' }
java -cp ("target\classes;" + ($jars -join ';')) `
    com.coreauth.validator.coverage.GenerateAppendixIFirstBatchCandidates
if ($LASTEXITCODE -ne 0) { throw 'Appendix I candidate generation failed' }
java -cp ("target\classes;" + ($jars -join ';')) `
    com.coreauth.validator.coverage.GenerateAppendixIRepresentativeIntake
if ($LASTEXITCODE -ne 0) { throw 'Appendix I representative intake failed' }
java -cp ("target\classes;" + ($jars -join ';')) `
    com.coreauth.validator.coverage.GenerateAppendixIScopedTraining
if ($LASTEXITCODE -ne 0) { throw 'Appendix I scoped training generation failed' }
```

The generator accepts optional ATL105-root and output-file arguments. Tests do
not publish files. Missing sources, boundaries, duplicate IDs and I/O failures
fail explicitly. Generation uses only the specification; AI input cannot change
its denominator.

## Baseline and completion boundary

Current targeted validation: 151 tests, zero failures/errors, covering Appendix I
inventory/knowledge/candidate metadata, first-batch predicates and framing,
representative immutable AI intake, register/view consistency and existing
Segment 111/producer-neutral contract behavior, plus a reproducible Appendix B
dependency-length inspection. This is not the full-framework
suite. The continuation adds 23 focused checks for selected scoped predicates,
canonical links, physical-file reproducibility and training-status gates,
plus shared training-status/denominator regressions. Eleven of the 178
remaining-layout example records now have separately recorded logical
execution evidence; predicates for the others remain unimplemented.
The two earlier physical-TD gaps are preserved in AIF-0017; AI comparison
is deferred, and no new delivery assessment is made by this continuation.

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
chain, TC-000016, labelled OK by its producer. The
[independent representative intake](../../../../test-output/ai-solution-independent-review/appendix-i/representative-intake.json)
now hashes the five immutable BR/TS/TC/TD/metadata inputs and joins their explicit
IDs. Selector presence and the bounded UPC representation pass, but physical TD
omits the required Segment Length. Under the explicit single-record alias
interpretation, its declared data length is 1 while actual data has 2 characters.
These gaps are recorded as `AIF-0017` for the AI Solution Team.

Identity links and the bounded findings are not complete BR/TS/TC/TD semantic
equivalence. The CA Public Key File Load message family, companion segments,
negative-case intent, full wire and host responses remain unassessed. Complete
Appendix I AI mapping and October-delivery TC/TD intake remain pending. Producer
approval/OK labels never enter Test Solution approval or execution counts.
