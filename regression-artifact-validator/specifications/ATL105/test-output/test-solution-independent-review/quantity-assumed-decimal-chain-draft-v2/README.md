# Quantity Assumed-Decimal Encoding: Automated Candidate Review v2

**Status:** Source-example regression checks pass. AI evidence remains review-only; no semantic match or coverage is credited.

ATL105 explicitly states in both Segment 102 and Segment 157 that quantity `0.05` with two assumed decimal places is encoded as `205`, while `25` is invalid. The existing product validator now has focused regression coverage for that example in both segment paths. This tests the source example only; it does not invent a general Unit of Measure decimal table or validate business quantity semantics.

All eight linked AI payloads and sidecars were staged unchanged under `regression-artifact-validator/specifications/ATL105/test-input/ai-solution/runs/2026-10-16/Run5/`. The redacted audit finds a Segment 102 Quantity in all eight cases, and sidecar values/case IDs agree with payloads; duplicate copies are byte-identical. Four AI BRs linked to Segment 157 nevertheless contain only Segment 102 data. No case establishes the assumed-decimal context, all sidecars lack `sourceBatchSha256`, and no approved copies were identified. The Segment 157-linked cases therefore remain scope mismatches, not evidence for Segment 157.

Run from `regression-artifact-validator`:

```powershell
python scripts/review-quantity-assumed-decimal.py --self-test
python scripts/review-quantity-assumed-decimal.py
mvn.cmd '-Dmaven.compiler.useIncrementalCompilation=false' '-Dtest=ProductSegmentPayloadValidatorTest,ProductActualPayloadAdapterTest' test
```

The last command passed 12 tests. Reports and manifests contain hashes and value shapes only, not raw quantity values. The source example and local tests do not grant approval to either linked AI chain. Test-owner confirmation of applicable decimal context, approved in-scope fixtures, independent outcomes, readiness, and execution remain open. The official Test baseline is unchanged; coverage credit remains zero.