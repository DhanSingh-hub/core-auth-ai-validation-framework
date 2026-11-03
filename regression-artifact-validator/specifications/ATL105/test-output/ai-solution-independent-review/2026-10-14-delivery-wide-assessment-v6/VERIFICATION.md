# Independent V6 Verification

Result: PASS for bounded inventory, structural joins, anchor proposal gates,
ledger arithmetic and all 15 output hashes recorded in summary.json.

Summary SHA-256:
`8b5166ae35435c9f6776d85418dc5cbafa01640ed7995f898e77adb7e7fa0e74`

Report SHA-256:
`832a1c90463c7dddccb8b446341205275259e89b8f6342ee2bd5cb5616f45612`

## Verified Populations

- 79,040 supplied files, with all eight approved/candidate catalogs retained.
- Unique IDs: 6,880 BR, 12,254 TS, 21,254 TC. Catalog copies and producer states
  remain occurrence evidence, not extra logical requirements or approvals.
- 2,359 TC IDs contain conflicting versions and receive no clean-chain credit.
- 18,868 unconflicted TCs have complete declared physical routes; 27 other
  unconflicted TCs lack such a route. This is not whole-payload validation.
- All 601 independent Test BR ledger rows reconcile. Candidate reach:
  347/601 (57.74%). Clean physical-chain candidate reach: 320/601 (53.24%).
- 27 candidate-reached Test BRs have no clean complete physical chain.
- 12,210 unique pair rows remain pending authorized source review; none is a
  semantic confirmation. Pair counts are not distinct-BR coverage counts.
- All 6,880 anchor proposals remain unassessed for source interpretation. None
  has a supplied literal source quote. Unresolved and LLM-proposed segment
  assignments do not participate in candidate coverage.

## Changed Anchor Method

V6 adds explicit evidence_ref.entity_id, source_rule_id entity IDs, segment_ids
and segment_number to producer-metadata candidate lookup, while excluding
uncertain segment assignments. The method changed from v4; differences are not
proof of improved AI output or increased validated business coverage.

Disposition partition of 6,880 AI BRs:

| Disposition | BRs |
|---|---:|
| Ambiguous/composite candidate | 2,769 |
| Unique exact entity/segment candidate | 817 |
| Unresolved segment anchor | 569 |
| Usable entity/segment fields but no baseline candidate | 140 |
| No numeric element anchor | 2,585 |

Non-element types preserve producer taxonomy; no invented numeric field makes
process, response or lifecycle rules match. Literal quote availability would
establish citation integrity, not interpretation or approval.

## Still Open

Source-backed anchor interpretation and authorized pair decisions are not
complete. Conflicting TC revisions, missing TD declarations and Test BR chain
gaps require remediation or explicit evidence-based disposition. Physical JSON,
metadata identities and file integrity do not establish expected-response,
negative-test, message-field, wire, lifecycle or host correctness.

Semantic-confirmed and executed coverage remain null/NOT_ASSESSED; execution
certification remains false. Auxiliary matrices/archives are inventoried and
hashed but still need their applicable format and semantic validation.