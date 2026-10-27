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
178 selected logical candidates for Tables 010-022, 024-059, 062, 064-073, and 075-081 now independently
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

- [ATL105 2026-3 source extract](../../extracted_text.txt): Appendix I, Section 12.10,
  Chapter 13 Elements 111, 112, 113, and relevant Chapter 11 message layouts.
  text/PDF SHA-256, exact source lines, printed appendix pages, PDF page markers,
  table IDs, and nested-selector occurrences.
- [First knowledge batch, Tables 001-009](../../../../test-output/test-json/knowledge/appendix-i-001-009.json):
  independent field attributes, contextual rules, source evidence and positive/
  negative test design, including the referenced Appendix J code and lifecycle rules.
- [Segment 111 knowledge](../segment-111/README.md): existing segment training,
  not exhaustive Appendix I table training.
- [Common handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md).
  unresolved framing is tracked once as `SEG111-SME-002`; existing ECA-specific

## Trainer decisions recorded on 2026-10-06
These are user-directed process decisions, not SME approval of business rules:

   nested sub-tables, rather than only the indicator.
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
contains 178 BR/TS pairs and 372 TC/TD pairs for selected examples from Tables
010-022, 024-059, 062, 064-073, and 075-081. Each TD has a physical JSON file, the canonical
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
372 logical evaluations, with findings against all 302 draft rules: 236 have
partial logical evidence in this batch and 66 have no scoped candidate. All 178
supplied targeted mutations are detected by their specific predicate. This is not
the appendix-wide mutation threshold or complete coverage of those rules.
The ledger identifies partial evidence **in this scoped batch**; it does not
discard or override earlier first-batch evidence. Table 018/019 checks cover
only explicit entry-point and recharge contexts, Table 020 checks only
synthetic numeric capacity, and Table 021 retains the external Signature Debit
availability gate; unsupported contexts and service validity remain
`REVIEW_REQUIRED`. Table 025 checks only alphanumeric width and equality to an
explicit synthetic associated-card value; issuer/card validity remains
unassessed. Tables 026/027 check supplied order/name grouping and bounded
character-count data; Tables 028/029 check supplied logical UUID request and
follow-up identity relationships, not host response generation or provenance.
Table 044 checks only the illustrated Visa response-ID chain and 15-character
shape; issuer provenance and generalized brand rules remain unverified.
Table 045 checks numeric scale and the supplied Discover original-approved
amount context. Table 046 checks only the explicit authorizer fixture, with its
applicability and length conflict still open. Table 047 checks the Discover
provider*retailer descriptor example, not enrollment. Table 048 checks listed
protocol/length mappings and protocol-plus-DS-ID shape; server issuance and
EMV 3DS participation remain unverified.
Table 049 adds 24 nested-selector candidates, covering 40 positive data
variants and 24 targeted negatives. They remain logical fragments; aggregate
framing/order, external catalogue and eligibility checks, and 14 open SME items
are not resolved by these cases.
Tables 050/051 add source-bounded checks for 1-16 alphanumeric values, exact
logical length, and the four-byte SVS exception. Security-code authenticity and
Additional Card ID allocation/uniqueness remain unverified.
Tables 053-055 cover canonical base64 identifier/cryptogram shape with companion
context, Moneris identifier length/mapping, and the four listed UCOM provider
codes. Table 053's 028 interpretation, cryptographic validity, actual processor
transmission, and UCOM assignment remain review-required.
Tables 057-059 compare Discover third-party-provider city, state, and country
against the supplied underlying-retailer context. They do not resolve source
selector/length/padding conflicts or independently verify country-code authority;
their registered SME questions remain open.
Table 062 checks the defined phone/email record maxima, supplied nested/aggregate
counts, and Visa account-verification context. It does not impose E.164, email
RFC, deliverability, or mandatory-contact rules; the email character/count conflict
remains open.
Tables 016 and 052 add selected EMV-tag and nested-record checks; aggregate framing,
tag-set completeness and cryptographic/key validity remain unresolved. Tables
065-073 add bounded gateway, amount, EV fee, EVV, loyalty-link, and fixed-width
field checks. Table 066 also checks the listed domain/program pair and supplied
Auth/completion selector recurrence. Table 056 checks selected supplied
recurrence/history values and synthetic registration-reference equality, but
cryptographic and network validity remain unverified. Tables 075-077 check
selected logical discount tags and positional amounts without asserting TLV
serialization or program enrollment. Table 078 checks selected listed subtable
values, product-code linkage and supplied decimal examples; wrapper, length/count
basis, ordering and conflicting tax examples remain review-required. Table 079
checks selected local tax fragments and the explicit small aggregate example;
required tag sets, ordering and reconciliation to Table 078 remain review-required.
Tables 080/081 check eleven-byte values while their header and reserved-byte
layouts remain review-required.
Table 064 checks supplied service-versus-acceptor differences, local nested
record widths and follow-on recurrence. USPS city validity, Appendix D/AC code
membership, country postal standards and the conflicting outer header remain
review-required.
The current increment adds bounded predicates for selected Table 056, 078, and
079 candidates. These remain logical review evidence; unresolved source conflicts,
applicability, full-message behavior, and SME approval are not inferred from them.
Table 030 checks only explicit EMV presence, the no-security-concern code and
the MasterCard-only terminal type 29 context; Table 031 checks the listed
capability codes and certified-capability invariance across entry modes.
Table 032 has bounded logical candidates for every selector listed in the
source (01, 02, 04-14; no 03 is defined there), but these do not validate
combined subtable order, aggregate encoding, the an100-versus-digits wording,
reserved remaining bytes, or merchant/network eligibility.
Table 033 checks one product record's count, listed re-price type, field widths,
matching UOM and backslash delimiter; NACS product semantics, price meaning and
the an15-versus-133-character aggregate conflict remain `REVIEW_REQUIRED`.
Table 034 checks Discover first/last and Visa first/last/middle 35-character
left-aligned fields; name grammar and aggregate serialization remain
`REVIEW_REQUIRED`.
Table 035 checks the logical 20-octet XID/CAVV lengths and order only;
cryptogram validity, transaction applicability and binary wire encoding remain
`REVIEW_REQUIRED`.
Table 036 checks the three-character channel/state example and the 43/28/28
AAV component arithmetic; payload validity, state compatibility and complete
UCAF wire construction remain `REVIEW_REQUIRED`.
Tables 037/038 check only fixed-width left-justified token/identifier shapes;
real MCX token validity and Paydiant registration remain unverified. Table 039
checks supplied DDMMCCYY component ordering, not calendar-edge policy. Table 040
checks the supplied EMV-initial eligibility and known-amount omission cases;
actual settlement outcomes remain unverified.
Table 041 checks conditional presence for the supplied numeric-state-00 context
but does not validate ISO subdivision membership. Table 042 checks only the
English/French examples. Table 043 checks an explicit synthetic short postal
fragment and separate AVS-address presence; actual international postal
formatting remains `REVIEW_REQUIRED`.
Proposed outcomes in the
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

## Prior baseline and completion boundary

The earlier 151-test targeted checkpoint covered Appendix I
inventory/knowledge/candidate metadata, first-batch predicates and framing,
representative immutable AI intake, register/view consistency and existing
Segment 111/producer-neutral contract behavior, plus a reproducible Appendix B
dependency-length inspection. It was not the full-framework suite. The two
earlier physical-TD gaps are preserved in AIF-0017; AI comparison was deferred
at that checkpoint.

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

## 2026-10-17 continuation

The [current canonical review-only package](../../../../test-output/test-json/appendix-i-scoped-training/package.json)
contains 178 BR/TS chains and 372 linked logical TC/TD cases. The [execution and
rule ledger](../../../../test-output/test-json/appendix-i-scoped-training/evidence.json)
records 178 detected supplied mutations. All records remain REVIEW_REQUIRED;
complete requests, host responses, approved rules and certification remain zero.
The focused generator class passes 217 tests after regenerating the persisted
package and fixtures; the combined focused Appendix I/Segment 111 suite passes
325 tests with zero failures or errors.

The [2026-10-17 Run1 comparison](../../../../test-output/ai-solution-independent-review/appendix-i-segment111-run1-comparison/comparison.json)
binds twelve approved AI cases to their scenario, requirement, TD and metadata
records under the verified intake manifest. The slice has ten scenarios and three
requirements; six cases carry producer status DELIBERATE_OMISSION and six COMPLETE.
All twelve logical TD records contain Table 005 value 000 and Table 031 value 00.
This is traceability evidence only: semantic equivalence is NOT_ASSESSED, SME
approval is not granted, and no raw wire capture or host response was found in
the selected TD records.

The [full-message evidence-gap register](../../../../test-output/ai-solution-independent-review/appendix-i-segment111-run1-comparison/appendix-i-full-message-evidence-gap.json)
keeps full-message validation NOT_ASSESSED. It lists the required byte-identical
capture pair, request-bound hashes, converter identity, framing manifest,
line-bound source quotes and complete request context. It retains the Segment 100
82-versus-78 source conflict as open SME item SEG111-SME-121 and does not synthesize
captures or infer host acceptance.

## 2026-10-27 Conexxus PSPC reference ingested

The Conexxus Payment System Product Codes document cited by Appendix K-27 for Table 049 Sub Table 18's
host-based restriction output (Appendix K Table 034) is now ingested in versions 4.1 and 3.7 (62 codes differ between them; unbound codes must agree in both). Register item
`SEG111-SME-012` is `ANSWERED`. The PSPC version binding and the `ans32` packing format remain open SME confirmations.
`SEG111-SME-003` (Visa versus MasterCard Enhanced Fleet scope) is unchanged. See the
[PSPC training](../../../test-validation-strategy/pspc-payment-system-product-codes/README.md) and its
[Validation Strategy](../../../test-validation-strategy/pspc-payment-system-product-codes/Validation%20Strategy.md).
