# Segment Type Element 85: Automated Payload Binding Review v2

**Status:** Full candidate payload/sidecar binding and source-format checks are automated. AI/Test adjudication remains review-required; no semantic match or coverage is credited.

The audit binds Element 85 using each sidecar row's segment ID and friendly container name, then matches that container to the exact nested `SegmentType` path in the payload. It compares the actual payload value to the sidecar value and, where available, to the source-derived fixed-value gate. It does not treat producer labels or sidecar values as expected outcomes.

All 76 linked candidate cases and their duplicate 10-Oct payload/sidecar copies were staged in `regression-artifact-validator/specifications/ATL105/test-input/ai-solution/runs/2026-10-19/Run1/`. The audit found 288 Segment Type observations and 288 uniquely bound rows; every sidecar value matches its payload and every duplicate copy is byte-identical. There were no parse failures, unbound rows, or ambiguous bindings.

Of the 76 cases, 57 have an explicit `deliberate_violation` Segment Type row. All 57 fail the exact three-digit numeric format check; 54 have a partially source-backed fixed segment value and differ from it, while 3 lack a source-backed fixed value. The remaining 19 cases are boundary-labelled but have no explicit mutation target; their labels are not treated as expected outcomes. The fixed-value gate itself has two source mismatches and zero SME approvals, so its values remain partial source evidence only.

Run from `regression-artifact-validator`:

```powershell
python scripts/review-segment-type-element-85.py --self-test
python scripts/review-segment-type-element-85.py
```

The report omits raw Segment Type values and binds all source/input hashes in `manifest-v2.json`. Existing segment-specific Test BRs are not promoted, no new generic Test BR is added, and no execution certification or coverage credit is granted. Authorized reviewer decisions and expected outcomes remain open.