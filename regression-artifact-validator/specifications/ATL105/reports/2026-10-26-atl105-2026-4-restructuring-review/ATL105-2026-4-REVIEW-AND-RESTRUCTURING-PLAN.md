# ATL105 full-document gap review and restructuring plan

**Disposition: review and proposal only; migration NOT performed.**

**Current baseline:** ATL105 2026-3. **Proposed target:** ATL105 2026-4,
released October 09, 2026. Preserve the old release, artefacts, decisions and
delivery results. BA/SME templates and AI outputs are inputs to assess, not
independent expected-behaviour sources.

## 1. Executive decision

Adopt the new business navigation as a **view of a source-versioned independent
Test model**, not as a replacement oracle or a reason to regenerate all IDs.
Extend the existing canonical model, validators, coverage machinery and
nine-gate training process. Do not build a second competing framework.

The migration needs four connected workstreams:

1. **Test artefacts:** normalize independent obligations and preserve BR/TS/TC/TD
   lineage; add or revise concrete tests for source changes and verified gaps.
2. **Validation:** version-select the independent rules and source evidence;
   extend intake, semantic assertions, physical request/response and lifecycle
   validation together.
3. **Coverage:** use deduplicated, release-specific independent obligations,
   and separately report source accounting, chain completeness, business
   equivalence, bounded execution and host evidence.
4. **Training:** retain the existing nine gates, adding migration crosswalks,
   release-contrast examples and held-out validation. No automatic
   self-certification or claim that model weights have been updated.

### Principal findings

| ID | Priority | Finding | Required disposition |
| --- | --- | --- | --- |
| F01 | P0 | Appendix X and Table 032/Subtable 11 become Reserved in 2026-4. Existing Appendix X logic still models the old refund indicator. | Retain for 2026-3; prevent it from providing a 2026-4 semantic PASS. Retirement is not evidence that all refunds are prohibited or that a particular host decline code applies. |
| F02 | P0 | Section 12.38 changes Segment 153 request layout and lifecycle semantics; Section 12.50 adds Segment 154 response data. | Migrate request and response as a pair. Extend source inventory, adapter dispatch, predicates, physical data, lifecycle evidence and coverage. Existing 153 checks are not the new profile. |
| F03 | P0 | Incremental-auth rules now span Appendix AF, Tables 063/82 and Elements 111/113. | Build versioned network/lifecycle profiles. Preserve source-specific mandatory versus recommended language and unresolved identity/approval-field mappings. |
| F04 | P0 | The current semantic-claim gate and pilot explicitly bind 2026-3. The AF overlay is a separate bounded 2026-4 path. | Add deliberate release dispatch and cross-release refusal; do not simply replace all `2026-3` constants. |
| F05 | P1 | Seven named review groups plus Debit/EBT are not an exhaustive full-document network inventory. The target tokenization chapter explicitly names additional debit networks. | Keep the agreed nine navigation groups, but retain additional source-supported network/routing contexts and request a taxonomy decision before changing top-level groups. |
| F06 | P1 | All retained Chapter 12 source records have been reviewed, but chapter-wide normalization and code/case equivalence are incomplete. | Reuse the existing adjudications and actual execution links. Reconcile remaining records rather than declaring every unlinked clause a missing validator. |
| F07 | P1 | Catalog counts, contextual drafts, placeholder chains and execution observations describe different populations. | Publish separate denominators and statuses; no global BR percentage until the in-scope atomic baseline is established. |
| F08 | P1 | A network-only hierarchy would omit transport, management, totals, downloads, checks, loyalty and external/process obligations. | Keep operational and specialized domains alongside the card-network navigation. |

P0/P1 are migration sequencing priorities, not vulnerability severities.

## 2. Review method, evidence and limits

### 2.1 What was actually reviewed

- Scanned the complete existing extracted text for both releases, identifying
  every PDF page marker and every chapter/appendix page family.
- Compared all body pages by release-local printed-page label after removing
  known release headers, dates and whitespace differences.
- Compared changed **whole chapter/appendix text** as well, to distinguish
  page reflow from substantive changes. Read the changed normative bodies for
  tokenization, Segment 153/154, originating-device indicators, stored credentials,
  refund retirement and incremental authorization.
- Reconciled the proposal with the existing strategies, canonical semantic
  evidence contract, current training registry, segment catalogs, latest
  Chapter 12 evidence and targeted impacted validator implementations.
- Read the latest Chapter 12.2 final reconciliation, rather than treating the
  earlier historical OPEN counts in that register as the current backlog.

This is a **full-document inventory and migration/gap review**, with targeted
semantic and implementation checks. It is **not** exhaustive atomic
adjudication of every sentence, exhaustive code/case equivalence, an SME
approval, a fresh regression run, or network/host certification.

### 2.2 Reproducible evidence

| Deliverable | Purpose |
| --- | --- |
| [Source-page inventory](source-page-inventory.csv) | All 730 target pages, printed page, exact extracted-text line interval, matched baseline PDF page/line and comparison disposition. |
| [Source-family inventory](source-family-inventory.csv) | All 14 chapters, 32 appendices and front matter, with page/line boundaries and page-comparison totals. |
| [Review evidence snapshot](review-evidence.json) | Separate PDF/text SHA-256 values, byte sizes, baseline/catalog counts and current Chapter 12 evidence provenance. |
| [Manifest](manifest.json) | Integrity binding for the review deliverables. |

The existing extracted files have **722 baseline page markers / 730 target page
markers**, corresponding to the supplied release sources. Target text contains
39,238 lines; baseline text contains 36,877. Target inventory:

| Comparison | Target pages |
| --- | ---: |
| Front matter, inventoried separately | 32 |
| Normalized extracted text equal at the same printed-page label | 567 |
| Text changed at the same printed-page label; review required | 125 |
| Added printed-page labels | 6 |
| **Total** | **730** |

The 131 changed/added body pages are **not 131 changed BRs**. Page reflow,
index/cross-reference updates, table-extraction layout and editorial changes
contribute to this count. No baseline printed-page label disappears; the
Appendix X **content** is nevertheless retired.

The 2026-4 PDF SHA-256 is
`568c04d3081cac6c5cc545adee9c6f8f3a34334e788fdf5ee0260ffef57c77af`,
matching the existing AF catalog. PDF and text hashes are recorded separately:
a matching catalog PDF hash does not prove the extraction faithfully preserves
every table cell, image or diagram.

**Extraction limitation:** no fresh independent PDF extraction or visual
table-by-table verification was performed. In particular, Chapter 12's rotated
presence-matrix labels and the token-usage grid require PDF cell verification
before eligibility/presence profiles are promoted. Normalized text equality is
change-triage evidence, not semantic equivalence or a reason to carry approval
across releases automatically.

### 2.3 Current Test baseline, without promotion

| Population | Verified current evidence | Interpretation |
| --- | --- | --- |
| Registered segment catalogs | 49 files / 605 cataloged rules | Segment-catalog inventory, not the whole-spec atomic denominator. |
| Training registry | All 49 IN_PROGRESS; 0 TRAINED_FOR_INTAKE; 46 with open blockers | No segment certification created by this review. |
| Chapter 12 retained-source accounting | All 49 baseline sections reviewed in the existing adjudication checkpoint | Source accounting is not complete rule normalization or implementation closure. |
| Finalized Chapter 12 scope | Sections 12.1 and 12.2: 99 distinct obligations, comprising 97 BOUNDED_EXECUTED, 1 SOURCE_CONFLICT, 1 EXTERNAL_EVIDENCE | A bounded source-local denominator for those two sections only; not 97 whole-message or host passes. |
| Chapter 12 remaining reconciliation | 1,847 RECONCILIATION_REQUIRED records in 12.3-12.49 | Candidate records needing exact normalization/code/case reconciliation, not 1,847 proven missing validators or independent BRs. |
| Chapter 12 generated package | 1,356 BR / 1,356 TS / 1,384 TC / 1,384 TD / 1,384 executed observations; approved requirements 0 | Existing 2026-3 evidence, read but not rerun or promoted here. |
| All-network review supplement | 145 shared requirements; 176 contextual BR/TS drafts; 64 shared rows linked / 81 unmapped; 44 selected conditional lifecycle contexts | Review supplement, not 176 approved independent rules or completed concrete TC/TD chains. |
| New-format pilot | 1 BR / 2 TS / 6 logical TC / 6 synthetic field TD | Bounded Element 86 claim/field example, not general prose semantics, eligibility or host readiness. |

Chapter 12's legacy summary field names include "independent" and "distinct";
its global atomic-denominator flags remain **false**. Do not reuse the
1,978 reviewed-record or 1,962 alias-adjusted totals as a certified denominator.
The older 601-rule snapshots and historical coverage percentages remain frozen
historical reports, not current 2026-4 semantic coverage.

## 3. Verified release-impact register

Source references below are to the [target extracted source](../../docs/specs/extracted_text_new.txt)
and [baseline extracted source](../../docs/specs/extracted_text.txt).
Use the page inventory to resolve each printed page into exact PDF page and
line interval for both releases. Printed-page numbers are not globally stable
identifiers for clauses.

| Change | Target source locator | Verified impact and required tests |
| --- | --- | --- |
| Refund source retired | Appendix X-1, PDF 709; Appendix I-27, PDF 576, Table 032/Subtable 11 | Baseline indicator/lifecycle/order rules must remain version-scoped. Test old valid profile under 2026-3, retired source under 2026-4, missing replacement evidence and mixed-release chains. Do not invent replacement rules or host rejection codes. |
| Tokenization network scope expands | 10-62, PDF 141; 10-77/78, PDF 156/157 | Target prose/grid includes Discover and STAR/NYCE/PULSE/ACCEL/Shazam, alongside Visa/MC/AMEX/Interlink/Maestro contexts. Reconcile exact In App/NFC/COF cells visually before authoring eligibility assertions. This is more than a receipt-label change. |
| Segment 153 framing and lifecycle | 12-93, PDF 310, Section 12.38 | Request direction, separator placement and exact original-request information carry-forward for follow-ups when both provisioned-network-token and Auth Optimization entitlement apply. Include changed PAN/expiry received in the original response; do not overwrite required original follow-up data with it. |
| Segment 153 table constraints | 12-94/96, PDF 311-313 | Table 001 moves from the baseline 013-018 representation to numeric maximum 19; Table 002 has explicit YYMM semantics; Tables 003-006 gain explicit descriptions/applicability; Table 007 adds TransArmor billing context/value Y. Test release-specific widths, types, values, missing context and original/subsequent roles. Do not invent a minimum token length from the target maximum-only statement. |
| New Segment 154 response | 12-121/123, PDF 338-340, Section 12.50 | Add source model and adapter/validator coverage for response Tables 001-005: network token, PAN/token indicator and first/second/third-try authorization indicators. Test T/P values, lengths, conditional tries and physical layout. Do not apply request Table 002 expiry semantics to response Table 002. |
| Presence matrix and later Chapter 12 locators | 12-2/5, PDF 219-222; 12-97/123, PDF 314-340 | Matrix adds 154; later section page positions shift. Match sections/clauses/content, not equal page numbers. Text shows substantial rotated-header extraction changes: cell applicability remains pending visual verification. |
| Device indicators | Appendix I-16, PDF 565, Table 022 | New values 35/36/37 describe card interface combinations; the future-device range becomes 38-99. Test exact new meanings and retain the source's future-value handling; do not reject the entire reserved range by assumption. |
| Stored credentials, Discover issued in India | Appendix I-55/60, PDF 604-609, Table 056 subtables 01-07 | Reconcile each subtable separately, including initial/subsequent distinctions and source "should" language. Add network + issuer-country + payment-type + lifecycle context; no blanket Discover-only or all-country mandate. |
| Existing lodging/IA table scope | Appendix I-66/67, PDF 615/616, Table 063 | Visa/MC/Amex scope and same-card series terminal-number continuity need explicit profiles. Keep this table distinct from the new Table 82 and avoid applying it universally to Discover. |
| Incremental-auth indicator and table | 13-85, PDF 425, Element 111; 13-91, PDF 431, Element 113; Appendix I-87/88, PDF 636/637 | Bind indicator `082` to Incremental Auth Data; the printed table itself states N2 ID `82`, N3 length `004`, N4 terminal number. Preserve indicator versus nested-table identity/width rather than padding both to the same shape. Verify physical interpretation before execution promotion. |
| Incremental-auth lifecycle | Appendix AF-1, PDF 730 | Original/Estimated Auth, Incremental Auth, IA Reversal and IA Completion; Discover mandatory with no MCC restriction; other named networks strongly recommended with applicable existing fields. Test approved predecessor, terminal/open-purchase uniqueness, continuity, approval reference and post-completion refusal in the appropriate oracle scope. |
| Editorial/reflow only candidates | Chapter 10 later pages; Chapter 13 index references; Appendix I later pages; Appendix J-1/2 | Whole-family comparison identifies many page shifts and footnote-spacing changes; no independent substantive Appendix J change was found by that text comparison. Preserve and revalidate affected locators; do not regenerate business rules solely because a page moved. |

### 3.1 Implementation evidence behind the migration gaps

- [AppendixXOnlineRefundOracle](../../../../src/main/java/com/coreauth/validator/canonical/AppendixXOnlineRefundOracle.java)
  defines the old Table 32/Subtable 11 indicator, carry-forward and Visa order
  behavior. Its `Scenario` has no specification-release field. This proves a
  release-selection review is necessary; it does not prove every caller
  currently dispatches the oracle incorrectly.
- [Chapter12SegmentPayloadValidator](../../../../src/main/java/com/coreauth/validator/canonical/Chapter12SegmentPayloadValidator.java),
  `networkTokens`, currently checks request Tables 001-006, token length 13-18,
  and leaves separator placement for review. Those checks differ from the
  target body. Its dispatch and the
  [actual payload adapter](../../../../src/main/java/com/coreauth/validator/canonical/Chapter12ActualPayloadAdapter.java)
  enumerate 153 but not the new 154. No 154 segment KB directory or training
  registration was found in the examined inventory. This is not a claim that
  every repository location has been proved devoid of token-response code.
- [SemanticEvidenceValidator](../../../../src/main/java/com/coreauth/validator/canonical/SemanticEvidenceValidator.java)
  explicitly accepts 2026-3 for the BR-claim context. The existing
  [semantic contract](../../docs/canonical-artifact-contract.md) documents that
  limitation. Reusing the method is appropriate; pretending it already accepts
  arbitrary releases is not.
- [AppendixAFIncrementalAuthorizationValidator](../../../../src/main/java/com/coreauth/validator/canonical/AppendixAFIncrementalAuthorizationValidator.java)
  already requires 2026-4 and checks bounded logical observations. Its warning
  models AF Transaction ID as Table 82's N4 terminal number, while the
  [AF training note](../../docs/specs/kb/appendix-af-estimated-incremental-training.md)
  says the physical candidate does **not** map AF Transaction ID into that
  field. Preserve both as a documented model/interpretation discrepancy.
  Do not train either interpretation as resolved truth.
- AF source explicitly calls its approval reference "DE 39"; the earlier
  corrected core flow uses Element 5 for approval. Determine the exact
  source-to-wire mapping before normalizing either into the other. The AF
  physical candidate also mixes a 2026-3 request layout and 2026-4 response
  layout and is explicitly review-only, not a migrated reference pair.

## 4. Whole-document restructuring map

The source-family inventory gives exact page and line boundaries for every row.
The following matrix identifies where each family belongs and what the
restructuring must retain. An unchanged source family is not automatically
implemented, semantically approved, not applicable, or complete.

### 4.1 Chapters 1-14

| Chapter / target PDF pages | Required Test model and review work |
| --- | --- |
| 1 Introduction / 33-35 | Scope, terminology, dependency and version metadata. Separate narrative from operative clauses; do not invent behavioral BRs for every paragraph. |
| 2 Message Flow / 36-47 | Ordered request/response histories, timeouts, late/unrecognized responses, reversals, correlation and retry behavior. Preserve observable versus authenticated history distinctions. |
| 3 Specifications Requirements / 48-49 | Terminal/TPP/VAR identities and operational requirements. Preserve strict thresholds: more than 1,500 monthly transactions for dial backup; more than 500,000 for the specified diverse-connection requirements. Not a wholly N/A chapter. |
| 4 Supported Industries / 50 | Source-backed industry/MCC applicability; reference data supports conditional rules, not mandatory presence of all industries. |
| 5 Supported Payment Network Types / 51 | Keep source categories and their processing applicability, without treating all payment/program labels as named card networks. Reconcile with Chapters 10 and 13 and code tables. |
| 6 PIN Encryption / 52-58 | PIN/KSN shape, conditional dependencies and representation. Retain the 6.7 versus Element 33 KSN-length conflict. HSM/key authenticity is separate from local format tests. |
| 7 Network Management / 59 | Direct operational anchors and communication request/response chains, not just incidental related Chapter 11 tests. |
| 8 Store-and-Forward / 60-64 | AFP/restaurant/lodging conditions, local approval sequences, retry/reformat limits, queues and exception behavior. External references do not make source-local obligations untestable. |
| 9 End-of-Day / 65-79 | Activity windows, cutoff, totals, reset/roll, settlement and history correlations; not only numeric amount formats. |
| 10 Processing / 80-163 | Credit/debit/PINless/signature debit/EBT/stored value/check/fleet/loyalty/download/tokenization/EMV families. Apply changed token network/usage contexts; retain external TransArmor/fleet boundaries. |
| 11 Message Formats / 164-217 | Every request/response/message family: sections, position, required/conditional segments, wire/framing and counts. Text-unchanged layouts still need reconciliation against changed segment inventory. |
| 12 Segment Formats / 218-340 | Preserve existing Standard/Fleet finalized obligations and all reviewed source-record provenance; reconcile 12.3-12.49 and add new 12.50. Separate code gaps from missing execution evidence, source conflicts and external evidence. |
| 13 Element Descriptions / 341-500 | Contextual representations, values and cross-field predicates. Keep 231 identities/232 baseline definition occurrences as an old-release inventory, not an assumed target count; re-inventory target definitions and repeated/scoped meanings. |
| 14 Support/Testing/Certification / 501-502 | Certification prerequisites, process evidence, accountable owners and external results. Offline predicate success never substitutes for processor/network certification. |

### 4.2 Appendices A-AF

| Appendix / target PDF pages | Required model, evidence or migration work |
| --- | --- |
| A TCP/IP header / 503-504 | Transport length/framing and byte-level boundaries, separate from application payload validity. |
| B POS purchase example / 505-510 | Example-derived fixtures with provenance; retain source conflicts such as product aggregate interpretation. Examples do not universally override normative rules. |
| C Authorizers / 511-513 | Authorizer/routing reference dimension, separate from receipt network label and card funding type. |
| D State codes / 514-517 | Contextual dictionary membership; no fabricated real-world address validity. |
| E Financial card types / 518-522 | Funding/program/product codes and conditions, not network identities. |
| F Product codes / 523-544 | Applicable product dictionaries and product structure; distinguish examples, counts, primary fuel and real aggregate predicates. |
| G Transaction codes / 545-546 | Transaction-code catalog and operation/flow applicability. A code is not a distinct lifecycle family or executed flow count. |
| H Declines / 547-549 | Response-family membership and source-bound expected outcomes; do not assign deterministic host declines for every malformed field. |
| I Variable information / 550-637 | Versioned nested table/subtable schemas, applicable contexts and lifecycle echoes; highest-impact migration area, including 022/032/056/063/82. |
| J POS entry / 638-639 | Composite PAN-entry/PIN-capability rules, hierarchy and lifecycle persistence. Separate presence, entry mode and channel. Existing baseline test failure needs explicit regression disposition. |
| K Additional information / 640-673 | Response tables and cross-request opt-ins/echoes; no automatic reinterpretation as request data. |
| L Currencies / 674-682 | Currency + network applicability dictionary, with versioned membership rules. |
| M EBT / 683-686 | EBT/eWIC, benefits/program and transaction context; not an invented named network/provider. |
| N Gift-card track / 687 | Conditional stored-value/track representation; not equivalent to all open-loop prepaid. |
| O Token terminology / 688 | Terminology/relations supporting token rules, not an independent execution oracle. |
| P TransArmor administration / 689 | Named external administration source boundary and approved prerequisite evidence. |
| Q POS conditions / 690-696 | Contextual condition dictionaries and field combinations; not unqualified mandatory presence of all values. |
| R EMV / 697 | EMV data/processing dependencies and external authority boundaries. |
| S CAPK / 698-700 | Download format, scope, blocks/history and checksum evidence; cryptographic trust remains separate. |
| T Additional EMV / 701-702 | Contextual EMV fields, request/response and lifecycle dependencies. |
| U Visa wallet / 703 | Conditional wallet/network requirements and links to applicable variable tables. |
| V Moneris / 704-707 | Dedicated request/response, batch correlation and reiteration conflicts; not forced into a generic purchase chain. |
| W Download layout / 708 | Configuration/download lifecycle and physical layout rather than card-network navigation. |
| X Reserved / 709 | Retired source tombstone for target release; retain old-release traceability and unresolved replacement policy. |
| Y 3-D Secure / 710-712 | Authentication versus authorization, participant/message roles and supported external evidence. |
| Z Stored credentials / 713-716 | CIT/MIT/category/subcategory and initial/subsequent relationships; reconcile changed I/056 country/network notes. |
| AA TransArmor / 717-722 | Token services and approved external contracts; token identity and format do not prove token authenticity. |
| AB Purchase repayment / 723 | Separate source-defined operation/context; do not merge by a similar prompt or transaction name. |
| AC Countries / 724-727 | Issuer/merchant country operands with explicit role; do not infer India-issued applicability from merchant country. |
| AD Account updater / 728 | Request opt-ins, returned values and retry/follow-up behavior, including interaction with original-data carry-forward. |
| AE Visa estimated/incremental / 729 | Preserve Visa-specific rules and conditions; assess compatibility with AF rather than replacing AE wholesale. |
| AF Estimated/incremental auth / 730 | New cross-network IA lifecycle, source modality, identity scope, closure and original approval reference. Retain existing bounded overlay but resolve model discrepancies. |

Front matter (PDF 1-32) is a separately inventoried release/change-history
source. Its revision summaries guide review; they are not substitutes for
operative body clauses.

## 5. Proposed artefact structure and migration policy

### 5.1 One model, multiple navigation views

```text
Versioned source pack + independent evidence + authorized decisions
  -> source-clause inventory and exclusions
  -> normalized independent Test obligations
  -> BR -> TS -> TC -> TD -> predicate/wire/lifecycle/execution evidence
  -> delivery-bound AI-to-Test dispositions and separate coverage views
```

Business navigation:

```text
Platform -> Specification + Release -> Business Domain
  -> Card Program / Funding Type -> Network or Generic Category
  -> Transaction Category -> Operation / Message Family
  -> Flow -> Leg + Request/Response role
```

Presence, entry mode, channel, commercial usage, issuer country, merchant
configuration and entitlements are **orthogonal context fields**, not mutually
exclusive levels of one tree. Use source-supported values and explicit unknown/
not-applicable states. Never infer eligibility from a navigation label.

Maintain the seven agreed named groups: **AMEX, Visa, Mastercard, Discover,
Diners Club, JCB, STAR Signature Debit**, plus **generic Debit and generic EBT**.
Credit remains a card/program type. Additional tokenization network names remain
visible in contextual/source inventory; do not silently alias STAR token
processing to STAR Signature Debit or discard NYCE/PULSE/ACCEL/Shazam.

Non-card/process domains can use not-applicable network/program fields only
where genuinely appropriate, with an explicit reason. They must not acquire
fake Mastercard/Sale contexts merely to fit the diagram.

### 5.2 Minimum evidence by artefact

| Layer | Proposed required content |
| --- | --- |
| Source clause | Source pack/version/hash, PDF and printed page, section, text locator, segment/element/table/subtable where applicable, normative/supporting/example classification, exact evidence, conflicts and dependencies. Do not invent element IDs for process or flow rules. |
| Independent obligation / BR | Stable identity and revision, one normalized obligation, scope/conditions/exceptions, must/must-not/should/may, explicit operand types/units, source evidence, related shared rules and review/approval state. Atomicity is adjudicated, not established by an `ATOMIC` label. |
| TS | Business intent, supported context, original/follow-up/precondition model, linked BR obligations and required test partitions. A restated BR title alone remains a draft proposal. |
| TC | Specific Given/When/Then, source-bound assertions for every reached obligation, concrete expected result and oracle scope, positive control, mutation target, TD links, flow/leg order and expected evidence gaps. |
| TD | Synthetic/approved-masked values, TC links, explicit logical versus serialized representation, sections/segments/fields, actual byte/length/count evidence when claimed, history/configuration fixtures and request/response bindings. |
| Result | Delivery, source/catalog/schema/adapter/validator versions and hashes, assessed scope, decisive findings, unassessed reasons, execution provenance and approval evidence. Preserve producer verdicts only as claims. |

Keep current canonical link fields: `businessRequirements[].id`,
`testScenarios[].requirementIds`, `testCases[].scenarioIds`,
`testData[].testCaseIds`. Use explicit versioned adapters for BA/SME or AI shapes.
Business navigation is a sidecar/view until the canonical-contract extension
is approved. Preserve unsupported records with an explicit disposition; never
drop them to make totals agree.

### 5.3 Cross-release crosswalk

For each existing rule/BR/TS/TC/TD, record old and new IDs/revisions, source
anchors/hashes, context and mapping evidence. Proposed relationships:

- `UNCHANGED_REVALIDATED`: same obligation/context, new source evidence verified.
- `MODIFIED`: changed predicate, applicability, modality or representation.
- `SPLIT` / `MERGED`: explicit many-to-many mapping with no lost old obligations.
- `ADDED`: new independent target obligation.
- `RETIRED`: old obligation retained historically, not eligible as target truth.
- `UNRESOLVED`: ambiguity, source conflict or incomplete equivalence.

Keep stable conceptual IDs when meaning is unchanged; version the record and
source binding. When meaning changes, preserve an explicit supersession relation
instead of rewriting old evidence. Never reuse an old approved decision on a
different rule meaning or target release without authorized revalidation.

Every old record must be accounted for exactly once in the migration ledger
(with explicit one-to-many relationships where necessary). Keep all 145 shared
supplement rows and the 81 unmapped-row backlog. Context views may reference a
shared rule many times; that does not create new independent-rule counts.

## 6. Validation strategy changes proposed

Use the existing architecture and
[AIV controls](../../docs/test-validation-strategy/AI-ARTIFACT-VALIDATION-CONTROLS.md).
Extend their scope and evidence rather than renaming existing gates as complete.

| Layer | Required validation | Non-success condition |
| --- | --- | --- |
| Intake/provenance | Freeze full delivery inventory and original bytes; hash every supported input; version-select adapter/source; refuse malformed/duplicate IDs/keys and ambiguous identities. | Unsupported format and normalization failures remain distinct from semantic defects or baseline omissions. |
| Schema/graph | Required fields, real IDs, every BR/TS/TC/TD edge, source/context continuity, duplicate/conflicting records, orphans and physical files. | Graph completeness never establishes correct business meaning. |
| Applicability | Network/program/transaction/message/direction/entry/channel/country/entitlement/flow profiles from independent source. | Unknown or unimplemented context is NOT_ASSESSED/REVIEW_REQUIRED, not a guessed allowlist or implicit PASS. |
| BR equivalence | Compare complete, reviewed structured claims: predicate, modality, values, units, conditions and exceptions. Use prose similarity only for candidate discovery. | Missing independent interpretation or unsupported claim cannot pass merely by source-anchor/ID equality. |
| Field/dependency | Types, lengths, requiredness, dictionaries, companion/exclusion and cross-field constraints in their actual context. | Passing one scalar predicate is not a whole-case pass. |
| Scenario/negative intent | Valid scoped control, declared target, isolated mutation, target failure and unrelated-predicate results. | A negative TD violating its intended rule is not automatically an AI defect; missing control or confounders block effectiveness claims. |
| Physical request/response | Independently reproduce/capture bytes, reconcile fields, calculated lengths/counts, delimiters/omissions, sections, ordering, framing and new response layouts. | Logical JSON or declared `wireHex` alone is not converter/execution proof. |
| Lifecycle/history | Actual ordered legs, original association, approved predecessor, carry-forward, timeout/retry/reversal/completion/closed state and applicable uniqueness scope. | Format-valid IDs are not authenticated history; absent observations stay unassessed. |
| Outcome/execution | Request-bound source-deterministic or independent host-reference oracle, captured response and environment/state provenance. | Expected response declarations, fixture shapes and offline checks do not establish observed host behavior. |
| Review/certification | Authorized append-only decisions and mandatory gate evidence. | Automation does not sign off its own interpretation or confer network certification. |

### Mandatory migration regression examples

1. A 2026-3 Appendix X example remains assessed only by the 2026-3 profile;
   target retirement yields a retired/unsupported-source disposition, not an
   invented response code.
2. Segment 153 Table 001 release-specific length 18/19 and 20 boundary,
   correct target numeric handling, Table 007 Y/context tests and unknown tables.
3. Segment 153 original data retained across relevant follow-ups even when the
   original response contains updated PAN/expiry; test absent entitlement and
   unknown history without inventing eligibility.
4. Segment 154 Table 002 accepts only its response PAN/token meaning, not
   request expiry semantics; test all three try indicators with applicable
   history and unsupported/missing context.
5. Table 022 new interface values and source-compatible future-device handling.
6. Table 056 subtable-specific initial/subsequent and India-issued Discover
   positive, negative, unknown-country and wrong-country-role cases.
7. AF approved predecessor, Discover table mandate, recommendation-only
   non-Discover context, duplicate open identity, completion closure and
   unresolved identity/approval mapping. Keep IA Reversal distinct from a
   reversal of a partially approved non-IA transaction.
8. Mixed-release source evidence, wrong source hash, moved page, stale quote,
   ambiguous aliases, duplicate IDs and unsupported business context never
   become success-shaped fallbacks.
9. Retained 12.1/12.2 canonical predicate cases preserve their prior scoped
   behavior; no loss of negative targets or mutation boundaries during adapters/
   source-pack changes.

## 7. BR coverage strategy proposed

### 7.1 Establish the right baseline first

Build a release-specific source-to-obligation ledger across every family in
Section 4. Classify every retained record as normative obligation, supporting
operand/dictionary, example, reference, conflict, external dependency or justified
exclusion. Normalize compound/repeated statements before deduplication.
Reconcile exact existing code/predicate/case targets before creating new logic.

Reuse Chapter 12's existing semantic decisions and finalized 12.1/12.2 evidence,
but revalidate their target release bindings. Do not restart all reviewed
records or infer missing implementation solely from the remaining queue.

Publish whole-spec percentages only after the independent in-scope atomic
denominator has been established and authorized. Until then:

- Publish **bounded, named-scope** metrics with denominator and maturity.
- Keep whole-spec semantic coverage `NOT_CALCULABLE` or `NOT_ASSESSED`, with
  reason and known inventory totals.
- Preserve conflicts and external-evidence obligations in the gross in-scope
  inventory; separately disclose the assessable subset and authorized
  exemptions. Do not inflate completion by silently removing blockers.
- Preserve rows for AI-only candidates and baseline-only/unassessed obligations.
  An absent crosswalk is not proof of no equivalent AI content.

### 7.2 Separate metrics

Let `D` be distinct approved in-scope independent obligations for one declared
release/scope, and `A` the frozen AI delivery population. Never substitute `A`
or a context Cartesian product for `D`.

| Metric | Numerator / denominator |
| --- | --- |
| Source-accounting progress | Reviewed retained source records / all retained source records; **not BR coverage**. |
| Catalog/normalization progress | Reconciled obligation scopes versus declared inventory, with unresolved counts; do not imply an unknown final denominator is known. |
| Confirmed AI requirement coverage | Distinct obligations in D with delivery-bound, evidence-supported business-equivalent AI matches / D. |
| AI requirement precision | Correct assessed AI requirements / assessed AI requirements; disclose assessed AI / all AI separately. |
| Independent TS coverage | Obligations in D with accepted source/context-correct scenarios / D. |
| Independent TC coverage | Obligations in D with accepted cases/assertions and required applicable partitions / D. |
| Structural chain coverage | Obligations in D with a valid BR -> TS -> TC -> TD graph / D; explicitly structural. |
| Semantic full-chain coverage | Obligations in D whose complete required scenario/case/data partitions meet independent semantic gates / D. |
| Physical data readiness | Cases with verified required physical data / accepted in-scope cases; do not count logical placeholders. |
| Bounded execution coverage | Obligations in D with the required independently executed target-predicate evidence / D, with exact scope and required boundaries. |
| Host-executed coverage | Applicable obligations with authoritative request/response/environment/state execution evidence / declared host-executable obligation denominator. |
| Mutation detection | Intended, isolated target catches / valid applicable mutations; disclose misses, false positives, unsupported and invalid-control rows. |
| Validator-control coverage | Implemented/regression-proven applicable AIV controls / declared applicable controls, subject to existing governance; never ATL105 BR coverage. |

Zero confirmed evidence and unassessed semantics must be reported together:
`0/N confirmed; assessment NOT_ASSESSED` is not proof that no equivalent AI
requirements exist. An invalid/empty denominator produces `null` /
`NOT_CALCULABLE`, never a misleading `0%`.

### 7.3 Grouping and rollups

- Report network/generic category, funding/program, operation, message family,
  lifecycle role, entry/presence/channel, segment/element and chapter/appendix
  as independent filters over the same rule IDs.
- A shared obligation shown under several networks counts once globally.
  Context-specific exceptions have their own independently justified obligation.
- Measure approved applicability combinations separately from rule coverage.
  Use explicit selected profiles, not every network x card x operation product.
- Count unique flows, legs and transaction codes separately. Single-step and
  multi-step eligible sets may overlap; they are not additive flow totals.
- Report uncovered rules, missing scenarios/cases/data, unassessed semantic
  mappings, missing physical evidence and execution blockers as separate gaps.
- Risk-weighted prioritization is a secondary view, never a replacement for
  unweighted source-obligation coverage.

## 8. Training strategy proposed

The [common handbook](../../docs/specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)
already supplies the required nine gates. Revise it through its existing
controlled-improvement/approval process **after** this proposal is accepted;
do not introduce a second mandatory training handbook.

| Existing gate | Target-release extension |
| --- | --- |
| SOURCE_INVENTORY | Two immutable release packs, verified extraction limitations, complete chapter/appendix inventory and source-delta crosswalk. |
| SEGMENT_KNOWLEDGE_MODEL | Versioned 153 request model, new 154 response model, I-table changes and preserved scoped element identities; non-segment obligations retained. |
| CONTEXT_MATRIX | Existing nine business groups plus explicit expanded token networks, country, entitlement, message direction and lifecycle conditions. Unsupported contexts remain visible. |
| INDEPENDENT_BR_DERIVATION | Normalize old obligations, source-supported modifications/additions and retirement tombstones; preserve existing semantic decisions without carrying approval silently. |
| TS_TC_TEST_DATA_CHAIN | Author real source-backed tests/TD for gaps; migrate stable IDs with crosswalks. Templates and generated title-only proposals remain drafts. |
| LIFECYCLE_AND_SPECIALIZED_FLOWS | IA predecessor/continuity/closure, token original-data carry-forward, retries/SAF/EOD and specialized service flows. |
| SERIALIZATION_AND_MUTATION | Direction-specific wire fixtures, independent conversion and exact mutation-target evidence for changed and retained profiles. |
| INDEPENDENT_AI_ARTIFACT_INTAKE | New-format adapters, full delivery conservation, semantic crosswalk and held-out contrastive evaluation. |
| CONVERTER_AND_EXECUTION_READINESS | Source/approval/canonical/wire/oracle gates, environment evidence and reproducible release-bound results. |

### Training corpus and independent evaluation

- Approved source-linked Test rules are the truth set. BA/SME diagrams,
  screenshots, proposed BRs and AI artefacts are reference/intake evidence,
  not unquestioned training answers.
- Separate source-derived drafts from reviewed/authorized lessons. Keep the
  source, interpretation, approval and execution statuses independently.
- Add paired 2026-3/2026-4 lessons: active versus retired X, old versus new 153
  tables, request versus response table meanings, new device values, country/
  lifecycle-specific stored credentials and AF versus AE.
- Add intentionally wrong network/card-type, country-role, modality, field
  mapping and mixed-release examples. Expected answers must be independently
  supported; absent evidence should teach abstention, not fabricated defaults.
- Keep evaluation fixtures/deliveries held out from lesson creation. Evaluate
  all supported artefact types and unfamiliar valid formatting through the
  adapter, not just repeated training examples.
- Record generator/model configuration when applicable, source/catalog/adapter/
  validator hashes, lesson revision, reviewer, evaluation population, results,
  failures and unresolved questions.
- Reuse the handbook's mutation targets: at least **85% overall** and **100%
  source-critical** catches for valid applicable mutations. Unassessed/invalid
  controls must not be disguised as detected mutations.
- A new rule does not "train itself" into approval. Source-derived automation
  may propose content; authorized review, regression evidence and promotion
  remain mandatory. Updating this repository's knowledge/rules is not evidence
  of neural model weight training.

## 9. Implementation waves and acceptance gates

These are proposed implementation tasks, **not changes performed by this review**.
Estimates in calendar days would be premature until the atomic inventory,
external contracts and required execution scope are agreed.

| Wave | Dependencies | Concrete outputs and exit evidence |
| --- | --- | --- |
| W0 Freeze and source assurance | None | Preserve source/artefact/delivery snapshots; independently verify target PDF extraction and high-risk tables; retain all known baseline failures. Every target page has a resolvable locator. |
| W1 Obligation/crosswalk baseline | W0 | Reuse reviewed source records; normalize and reconcile existing 12.3-12.49 candidates; account for all other families and add 12.50. Every old record has a mapping/retirement/unresolved disposition; no lost IDs or silent source substitution. Publish scope maturity, not an invented global denominator. |
| W2 Contract and release dispatch | W0; agreed context/crosswalk design from W1 | Versioned context/adapter contract; strict release profiles across Python and Java intake/semantic/wire paths; full input conservation and exact source-hash checks. Unknown/mixed releases cannot pass accidentally. |
| W3 Changed-source vertical slices | W1/W2 for each slice | First gate X retirement; then 153/154 request-response + lifecycle, AF/I/111/113, 022 and 056. For each: independent BR/TS/TC/TD, positive/negative/boundary tests, exact predicates, physical evidence or explicit block and reviewed migration crosswalk. |
| W4 Remaining full-document gaps | W1/W2 | Reconcile existing validators before adding code; close verified source-local semantic/wire/lifecycle gaps by risk across Chapters 2/3/6-14 and appendices. Keep source conflicts/external boundaries separately owned. |
| W5 Coverage and independent training evaluation | Each relevant W3/W4 slice | Version-bound coverage reports and lessons; held-out AI delivery assessment; stable count/edge/hash reconciliation; authorized review of changed interpretations. No placeholder or overlapping-context inflation. |
| W6 Promote target baseline | W0-W5 for declared approved scope | Target and preserved baseline regressions, resolved new failures, authoritative approvals and exact readiness/host evidence. Only then update active pack/status/handbook pointers; retain old reports and replay paths. |

W3 and W4 can proceed in independently approved slices; semantic and execution
status must identify the exact completed scope. Full-spec migration is not
complete merely because the first slice passes.

### Acceptance checklist

- [ ] Every chapter/appendix/source page accounted for; high-risk extraction
      tables verified against PDF; unsupported image/table evidence disclosed.
- [ ] Every existing rule/artefact retained or explicitly mapped/retired;
      stable conceptual IDs and revisions preserved.
- [ ] Every promoted target obligation has reviewed source, exact context,
      applicable positive/negative/boundary partitions and real assertions/data.
- [ ] Release-specific tests cover all verified changes; no 2026-3-only rule
      grants 2026-4 correctness; no old approved snapshot silently overwritten.
- [ ] Required graph, semantic, physical, lifecycle and outcome evidence gates
      pass for the declared readiness level; unsupported gates remain explicit.
- [ ] Required mutation thresholds met, including 100% source-critical catches;
      no newly introduced regression failures.
- [ ] Retained 12.1/12.2 bounded assertions and original producer inputs remain
      reproducible and unchanged unless an authorized versioned change applies.
- [ ] Coverage uses independently established denominators and deduplicated
      global IDs; incomplete-source scopes cannot claim full-spec completion.
- [ ] Authorized decisions, unresolved conflicts/external owners and held-out
      evaluation results are recorded; no scripted approval.

The latest gap register records two known baseline full-suite failures:
`AppendixCoverageConsistencyTest.allAppendixRecordsMatchTheCanonicalInventory`
and `AppendixJPosEntryModeOracleTest.rejectsMalformedAndUnlistedComponents`.
These are historical evidence, **not a fresh test result from this review**.
Do not weaken assertions or claim a green baseline. Reproduce and disposition
them before promotion; repair in a separately scoped or directly coupled change.

### Rollback and compatibility

Keep active-version selection reversible. Publish new target packs and reports
alongside the old, never in place. A failed target gate restores selection of
the preserved 2026-3 baseline without deleting target evidence or rewriting
delivery history. Source/adapter/catalog/validator/decision versions must replay
the same frozen delivery. Rollback does not make retired target rules valid.

## 10. BA/SME/source-owner questionnaire

These questions affect interpretation or approval. Ordinary code/test
reconciliation should continue without pretending it is all blocked on SMEs.

| ID | Decision required | Evidence / blocked promotion |
| --- | --- | --- |
| Q01 | What replaces, if anything, the retired online-refund indicator guidance in 2026-4? Is there an approved addendum/host contract and effective-date policy? | X and I/032/11 are Reserved. No replacement outcome is assumed. |
| Q02 | Confirm the physical relationship between Element 111 indicator 082, the printed N2 Table ID 82, and the enclosing Segment 111 representation. | 13-85/91 and I-87/88; do not normalize away the different widths. |
| Q03 | Is AF Transaction ID exactly Table 82 Terminal Transaction Number? Confirm uniqueness scope, concurrency/history requirements and rollover/reuse. | AF prose, table wording and existing logical/physical model discrepancy. |
| Q04 | How does AF's "Approval Code (DE 39)" map to ATL105 request/response fields and the Element 5 core approval representation? | Needed for approved original-response/follow-up wire correlation; no field-number substitution by analogy. |
| Q05 | Confirm Table 063 versus Table 82 applicability and treatment of "should"/"strongly recommended" for Visa/MC/Amex. | Preserve Discover mandatory behavior separately; resolve continuity enforcement policy. |
| Q06 | Confirm target token-grid cells and message-presence matrix against the PDF, including actual eligible message roles and channel restrictions. | Extracted rotated/cell-aligned tables are insufficient for automatic eligibility promotion. |
| Q07 | Should NYCE/PULSE/ACCEL/Shazam and other explicit tokenization networks get additional top-level navigation, or remain named routing/token contexts under approved views? | Nine agreed groups are retained; STAR versus STAR Signature Debit is not assumed identical. |
| Q08 | Confirm issuer-country evidence and subtable-specific initial/subsequent rules for India-issued Discover. | Table 056/01-07; merchant country is not issuer country. |
| Q09 | What independent merchant entitlement, Auth Optimization, token-service and original-history observations can the test environment supply? | Needed to assess conditional carry-forward and token-response behavior without filling data from metadata claims. |
| Q10 | Who owns conflict resolution and external contracts for the retained Chapter 12/13, PIN, fleet, TransArmor, InComm, MAC/cryptographic and certification boundaries? | Preserve all sides and existing decision IDs; do not select a convenient source by priority guess. |
| Q11 | Who approves the target atomic denominator, mandatory partitions, release promotion and revised training/coverage policy? | Existing handbook/strategy approvals cannot be manufactured by this report. |
| Q12 | What is the required readiness level per domain: logical review, offline predicate execution, independent conversion, host integration or network certification? | Determines real evidence/acceptance scope and makes subsequent scheduling estimable. |

Record answers through existing subject-specific append-only decision/register
workflows, with reviewer authority, source, release, effective date, supersession
and impacted IDs. Do not treat a response in a draft questionnaire as signed
approval automatically.

## 11. What remains unchanged

This review adds only this report and generated review evidence. It does not
migrate source packs, change BR/TS/TC/TD, repair validators, amend strategies,
alter the common handbook/training status, change coverage counts, modify AI
inputs, approve a requirement, train model weights, run host transactions or
commit/push repository changes.

**Recommended next authorized action:** implement W0-W2 and the P0 vertical
slices, preserving the existing baseline, then expand by verified gaps. Do not
announce a completed 2026-4 Test model from a template conversion alone.
