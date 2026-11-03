# Independent Ledger Verification

Assessment date: 2026-10-14. Result: PASS for the bounded inventory and
structural-ledger checks below. This does not certify source interpretation,
payload semantics, processor execution, or SME approval.

- Recomputed and matched all 15 SHA-256 output hashes recorded in summary.json.
- Recounted 79,040 files: 8 catalogs, 73 auxiliary files, 39,480 payloads,
  and 39,479 metadata files. QE traceability.json is auxiliary, not test data.
- Reconciled every catalog occurrence: 13,760 BR, 24,508 TS, and 47,226 TC
  occurrences; unique populations are 6,880 BR, 12,254 TS, and 21,254 TC IDs.
- Verified 2,359 conflicting TC IDs are excluded from clean physical-chain
  credit. 18,868 TCs have an unconflicted complete declared physical route;
  27 further unconflicted TCs do not. Zero physical payloads are unreferenced.
- Recounted all 601 independent Test BR ledger rows. Candidate reach is
  344/601 (57.24%); clean physical-chain candidate reach is 304/601 (50.58%).
  The 40 candidate-reached Test BRs without a complete route have gap rows.
- Recounted 1,180 candidate pair rows; every decision remains pending
  authorized source review and semantic status remains NOT_ASSESSED.
- Recounted 6,880 anchor-review rows; all lack a supplied literal source quote.
  Producer anchor types/context remain proposals, not confirmed mappings.
- Confirmed semantic-confirmed and executed coverage percentages are null and
  executionCertified remains false.

The producer matrix, archives and other auxiliary formats are inventoried and
hashed only; they still require their applicable format and semantic validators.
The 601-rule denominator is the selected, hash-pinned 2026-3 inventory, not a
claim of full 2026-4 support or approved completeness of the Test BR baseline.

Next work: reconcile conflicting TC revisions, repair/disposition missing
declarations, establish source-backed anchor interpretations, and obtain
authorized pair decisions. Do not raise percentages by relaxing the join rules.