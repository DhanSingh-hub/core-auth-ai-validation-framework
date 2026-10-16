# Segment 143 Tax Amount 3: N-Branch Fixture Review v2

**Status:** Synthetic full-request fixtures and local source-structural checks pass. Authorized Test review remains open. No AI-to-Test match is approved and no coverage is credited.

The source rule is explicit: when Element 229 is `N`, both Element 230 and Element 231 are skipped. The synthetic fixture at `regression-artifact-validator/src/test/resources/atl105/segment-143-tax3-n-requests.json` contains a full three-segment request, a matching Product Code Segment entry, and an actual serialized Segment 143. It includes the N positive and separate type-present and amount-present mutations. Each mutation updates only its affected field plus resulting Segment Length/wire bytes. The local validator rejects the violated omission while the declared wire length remains correct.

All 12 linked AI payloads and sidecars were staged unchanged in `regression-artifact-validator/specifications/ATL105/test-input/ai-solution/runs/2026-10-16/Run2/` and reviewed with values redacted. Both source-tree copies are byte-identical and sidecar case IDs match. Four financial/EMV payloads contain only Tax 1's `I` flag; their sidecars mark the Tax 2/3 fields unavailable. The other eight cases have no exact Segment 143 Tax 3 observation. Candidate payload copies exist for `TC-010324` and `TC-010340`, but approved copies remain absent. Thus none of the 12 establishes Element 229=`N` or a semantic match. This full-payload review supersedes v1's bounded-observation interpretation.

The focused local test passed:

```powershell
mvn.cmd '-Dmaven.compiler.useIncrementalCompilation=false' '-Dtest=Chapter12SegmentPayloadValidatorTest#tax3NoTaxRuleUsesCompleteWireFixturesAndIsolatedFieldMutations' test
```

The full owning validator and actual Product adapter suites also passed 42/42:

```powershell
mvn.cmd '-Dmaven.compiler.useIncrementalCompilation=false' '-Dtest=Chapter12SegmentPayloadValidatorTest,ProductActualPayloadAdapterTest' test
```

Local source-structural outcomes are not host responses or authorized Test execution. Test-owner approval, independent expected-outcome ruling, readiness, and execution evidence remain outstanding. The official baseline and canonical Test package are unchanged.