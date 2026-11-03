# Mastercard BR Coverage Review - 31 October 2026 AI Delivery

## Scope and verdict

Mastercard Credit and Mastercard Fleet are the primary scope. Maestro Direct Debit is a
separate companion section and is never added to the Mastercard totals.
Confirmed independent ATL105 BR coverage: **NOT CALCULABLE / NOT ASSESSED**.
The delivery declares ATL105 2026-4; no release-qualified independent denominator or
delivery-bound semantic crosswalk is established by this audit. The historical
2026-3 Mastercard Purchase 168/189 training result is not AI-delivery coverage.
Named native-gate outcomes are shown below; execution remains BLOCKED.

## Recomputed inventory and structural reach

| Package | Umbrella BRs | TS | TC | TC with all declared TD | Physical TD | Supporting rules | Rule physical lineage | Explicit predicate + TD lineage |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| mastercard_credit | 1 | 36 | 450 | 408 | 839 | 590 | 576/590 (97.63%) | 3/590 (0.51%) |
| mastercard_fleet | 1 | 24 | 299 | 249 | 569 | 814 | 793/814 (97.42%) | 5/814 (0.61%) |
| maestro_direct_debit | 1 | 28 | 278 | 249 | 536 | 447 | 431/447 (96.42%) | 1/447 (0.22%) |

Supporting-rule IDs are a producer inventory, not an independent or approved atomic
denominator. Scenario rule lists broadcast lineage to linked TCs; lineage does not
prove that a TC tests each rule. Explicit predicate lineage requires a TC rule_checks
kb_rule_ids reference, the matching scenario-rule link, and all declared TD JSON files.
Predicates have NOT been executed, and their source applicability has NOT been certified.
Generic named checks without kb_rule_ids are excluded from this narrow ID-lineage metric;
a low percentage is not proof that the other rules have no tests or that those tests fail.
Response-check declarations are retained separately; FLAG/ASSERT labels are not observed responses.
Shared rules across packages are not added into a unique rule denominator.

Primary Mastercard totals: **60 TS, 749 TC, 657 TC with all declared TD, 1408 physical TD files**.
These totals are package-qualified artifact counts, not a deduplicated BR denominator.

## mastercard_credit

- Declared build time: `2026-10-31T10:06:57.878016+00:00`.
- Declared release: `2026-4`.
- TC status inventory (producer declarations): `{'blocked_spec_gap': 2, 'needs_client_input': 61, 'pending_sme': 61, 'ready': 180, 'requires_review': 146}`.
- Scenario status inventory (producer declarations): `{'valid': 26, 'unconfirmed': 8, 'spec_implied': 2}`.
- Supporting rules marked requires_review by producer: 354.
- Supporting rules without explicit predicate + TD lineage: 587.
- Supporting rules with response-check declarations: 14.
- TC without TD declarations: 42; missing declared TD: 0; unreferenced TD: 0.
- Missing-declaration dispositions: `{'T11 / requires_review': 8, 'T11 / ready': 27, 'T11 / pending_sme': 5, 'T5 / blocked_spec_gap': 2}`.
- Supporting-rule source inventory: `{'SRC-ATL105-PDF-001': 297, 'SRC-ATL105-TRANSARMOR-PDF-001': 26, 'SRC-BUYPASS-IIN-PDF-001': 1, 'SRC-BUYPASS-INCOMM-MBD-PDF-001': 4, 'SRC-BUYPASS-PIPS-PDF-001': 36, 'SRC-FISERV-EMV-GUIDE-PDF-001': 226}`.
- JSON segment-object count disagreements: 0; unassessed counts: 0; duplicate segment IDs: 0.
- Direct-link findings: 0.
- Producer matrix TC total: 450; recomputed TC total: 450.

- Existing bounded native-delivery gate result: **FAIL**.
- Gate finding counts: `{'ELEMENT_188_WIDTH': 126, 'ELEMENT_188_TAG': 126, 'EMV_CRYPTO_PLACEHOLDER': 126, 'EMV_RUNTIME_AMOUNT': 4, 'READY_OPEN_ITEMS': 33, 'TRACE_POINTER': 816, 'TRACE_PATH': 816, 'TRACE_EXISTS': 408, 'TRACE_ALL_LEGS': 900, 'COVERAGE_BASIS': 1, 'STALE_ARCHIVE': 1, 'STALE_README': 1}`.
- Gate details: [native-gates.json](native-gates.json). Execution readiness remains BLOCKED.

## mastercard_fleet

- Declared build time: `2026-10-31T10:10:59.238091+00:00`.
- Declared release: `2026-4`.
- TC status inventory (producer declarations): `{'needs_client_input': 13, 'pending_sme': 144, 'ready': 76, 'requires_review': 66}`.
- Scenario status inventory (producer declarations): `{'unconfirmed': 12, 'valid_conditional': 6, 'valid': 5, 'spec_implied': 1}`.
- Supporting rules marked requires_review by producer: 537.
- Supporting rules without explicit predicate + TD lineage: 809.
- Supporting rules with response-check declarations: 21.
- TC without TD declarations: 50; missing declared TD: 0; unreferenced TD: 0.
- Missing-declaration dispositions: `{'T11 / requires_review': 14, 'T11 / ready': 12, 'T11 / pending_sme': 24}`.
- Supporting-rule source inventory: `{'SRC-ATL105-PDF-001': 254, 'SRC-ATL105-TRANSARMOR-PDF-001': 23, 'SRC-BUYPASS-IIN-PDF-001': 2, 'SRC-BUYPASS-INCOMM-MBD-PDF-001': 4, 'SRC-BUYPASS-PIPS-PDF-001': 301, 'SRC-FISERV-EMV-GUIDE-PDF-001': 230}`.
- JSON segment-object count disagreements: 0; unassessed counts: 0; duplicate segment IDs: 0.
- Direct-link findings: 0.
- Producer matrix TC total: 299; recomputed TC total: 299.

- Existing bounded native-delivery gate result: **FAIL**.
- Gate finding counts: `{'ELEMENT_188_WIDTH': 78, 'ELEMENT_188_TAG': 78, 'EMV_CRYPTO_PLACEHOLDER': 78, 'READY_OPEN_ITEMS': 12, 'TRACE_POINTER': 498, 'TRACE_PATH': 498, 'TRACE_EXISTS': 249, 'TRACE_ALL_LEGS': 598, 'COVERAGE_BASIS': 1}`.
- Gate details: [native-gates.json](native-gates.json). Execution readiness remains BLOCKED.

## maestro_direct_debit

- Declared build time: `2026-10-31T10:21:01.546243+00:00`.
- Declared release: `2026-4`.
- TC status inventory (producer declarations): `{'needs_client_input': 48, 'pending_sme': 198, 'ready': 6, 'requires_review': 26}`.
- Scenario status inventory (producer declarations): `{'unconfirmed': 20, 'valid': 8}`.
- Supporting rules marked requires_review by producer: 261.
- Supporting rules without explicit predicate + TD lineage: 446.
- Supporting rules with response-check declarations: 14.
- TC without TD declarations: 29; missing declared TD: 0; unreferenced TD: 0.
- Missing-declaration dispositions: `{'T11 / requires_review': 2, 'T11 / ready': 6, 'T11 / pending_sme': 21}`.
- Supporting-rule source inventory: `{'SRC-ATL105-PDF-001': 194, 'SRC-ATL105-TRANSARMOR-PDF-001': 12, 'SRC-BUYPASS-INCOMM-MBD-PDF-001': 4, 'SRC-BUYPASS-PIPS-PDF-001': 33, 'SRC-FISERV-EMV-GUIDE-PDF-001': 204}`.
- JSON segment-object count disagreements: 0; unassessed counts: 0; duplicate segment IDs: 0.
- Direct-link findings: 0.
- Producer matrix TC total: 278; recomputed TC total: 278.

- Existing bounded native-delivery gate result: **FAIL**.
- Gate finding counts: `{'ELEMENT_188_WIDTH': 90, 'ELEMENT_188_TAG': 90, 'EMV_CRYPTO_PLACEHOLDER': 90, 'READY_OPEN_ITEMS': 6, 'TRACE_POINTER': 498, 'TRACE_PATH': 498, 'TRACE_EXISTS': 249, 'TRACE_ALL_LEGS': 556, 'UNCOVERED_SCENARIO': 7, 'COVERAGE_BASIS': 1}`.
- Gate details: [native-gates.json](native-gates.json). Execution readiness remains BLOCKED.

## Key validation findings

| Finding | Credit | Fleet | Interpretation |
|---|---:|---:|---|
| ELEMENT_188_WIDTH | 126 | 78 | EMV Element 188 does not meet the source-pinned three-byte representation |
| ELEMENT_188_TAG | 126 | 78 | Element 188 does not agree with EMV tag 5F34 |
| READY_OPEN_ITEMS | 33 | 12 | Producer marks TC ready despite open execution prerequisites; review required |
| TRACE_PATH | 816 | 498 | Trace pointers do not resolve from the delivered package root |
| STALE_ARCHIVE | 1 | 0 | Embedded ZIP TC build/population differs from the authoritative JSONL |
| STALE_README | 1 | 0 | README does not identify the authoritative build |

Counts above are emitted finding occurrences, not unique defective TCs; trace surfaces
can repeat the same TC. Trace-path failures do not mean the authoritative TC-declared
TD files are missing: those declarations all resolve. EMV width/tag findings are
bounded source-predicate violations, not observed issuer declines.

## Verification boundaries and next actions

1. Freeze inputs with SHA-256; input-manifest.csv includes every file in the three
   selected packages, including unreferenced TD and archives. Hashes are rechecked
   before writing this report. Source/reference hashes are in summary.json.
2. Establish an independently derived, release-pinned 2026-4 denominator for Credit
   and Fleet separately. Do not transfer matches by reused producer BR IDs.
3. Review supporting-rule applicability, including cross-program and companion-source
   rules, then map objectives and source-backed assertions to independent BRs.
4. Validate required/conditional companions using independent transaction context.
   The supplied compatibility SME/TBA note is a learning note, not approval evidence.
   JSON segment-object counts are NOT serialized Element 63 validation; no order,
   segment-length, wire framing, token, or host-acceptance validation is claimed.
   Existing source-pinned native gates add only their named EMV/export predicates;
   their findings do not establish complete message validity or BR semantics.
5. Bind runtime placeholders/client inputs, establish response oracles, and execute
   positive controls and isolated negative mutations before readiness or coverage promotion.
6. Package README/contract counts can be stale; use recomputed JSONL/physical inventories.
   Missing TD on device-only/blocked cases is reported, not automatically a defect.

## Evidence files

- [Summary and input/source identity](summary.json)
- [Input SHA-256 inventory](input-manifest.csv)
- [Supporting-rule ledger](supporting-rule-ledger.csv)
- [Scenario ledger](scenario-ledger.csv)
- [Case/TD linkage ledger](case-ledger.csv)
- [Physical TD observations](physical-td-ledger.csv)
- [Direct-link findings](link-findings.csv)
