# Element 2/12 Local Execution Coverage

Scope: source-bound local predicates, not complete Element 2/12 business certification.

The [focused package](../test-json/segment-100-elements-2-and-12-execution-package.json)
has 13 atomic BRs, 13 scenarios, 42 test cases and 42 synthetic fixtures. All pass
the strict execution-readiness contract with the default ATL105 source-evidence root.
The [execution evidence](segment-100-elements-2-and-12-local-execution.json) records
42 matched outcomes and binds the package, source specification and validator source
using SHA-256. The response objects are local expectations, not fabricated host captures.

| Element | Executable Local Coverage |
| --- | --- |
| 2 | Manual account presence and character representation; non-TransArmor request 24/25-byte boundary; Track 1 and Track 2 account split and wrong-delimiter negatives |
| 12 | Independent Track 1/2 suffix matches and omission negatives; 51/52-byte boundary; manual-card MMYY including expired-card input; skip rules for 093/095/096; TransArmor expiration presence; Accel/STAR required expiration and optional internal-database COF recurring contexts |

Each case targets one BR and checks its declared outcome and failure context/reason.
Both elements have positive and negative fixtures; boundaries and conditional omission
are explicit rather than one combined PASS_AND_FAIL placeholder. Optional contexts
have both omitted and supplied positive fixtures instead of invented failure rules.

The consolidated Test Solution generator merges these chains without altering the
governed rule denominator or promoting broader draft BRs. Legacy/catalog placeholders
remain visible: shared anchors do not prove that a narrow predicate covers an entire BR.
The full consolidated package is therefore still not execution-ready.

## Reproduce

Run from the regression-artifact-validator module with the installed JDK/Maven:

```powershell
mvn.cmd compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateSegment100AccountEntryPackage'
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateCompleteCanonicalTestSolutionPackage'
mvn.cmd test '-Dtest=Segment100Elements2And12ExecutionTest,CanonicalTraceabilityValidatorTest,CanonicalRuleDenominatorTest'
```

The generator verifies source hashes and literal quotes before publication and then
executes every fixture. The tests additionally reject missing assertions, stale source
hashes, disabled validation, malformed values and conflicting consolidation records.
The focused package must reproduce exactly from its generator.

## Remaining Gates

- Semantic interpretation and authorized review are NOT_ASSESSED, not automatically approved.
- Host execution and wire validation are NOT_EXECUTED. These are local predicate fixtures, not full financial messages.
- Full track grammar and combined track limits, encrypted TransArmor header/EDATA and token BIN/Luhn qualification remain outside this package.
- Certegy/SCAN identity formats, Account Updater retry history, full EMV content, and all entry-mode/transaction contexts are not covered here.
- No mapping or normalization is inferred for an external JSON TrackData alias or ':' delimiter. The supplied Authorization/Completion inputs are unchanged.

These remaining scopes prevent a claim that all Element 2/12 tests, or the whole Test
Model, are fully execution-ready. No SME decision, AI coverage credit, or host approval
is manufactured by passing the local suite.