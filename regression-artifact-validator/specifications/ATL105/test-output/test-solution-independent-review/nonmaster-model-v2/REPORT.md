# Non-Mastercard v2 program and application-body continuation

- 71 combined draft BRs, 115 TS, 230 TC / 230 logical TD.
- 18 new processing/program contexts beyond the seven named v1 contexts.
- Reverified Visa/Voyager companions: 61 draft BRs / 115 TC/TD, kept separate.
- 9 executed complete-Standard/application-body shape controls and isolated mutants.
- 1326 delivered JSON messages independently observed without copying account/PIN payloads.
- v1 is preserved and reverified; no original AI delivery was changed.
- Approved denominator / confirmed semantic coverage: **null / NOT_ASSESSED**.
- Execution readiness: **BLOCKED**.

## Physical observations
- FAIL: 473
- NOT_APPLICABLE_TOTALS: 135
- REVIEW_REQUIRED: 718

## Scope boundaries
Application-body shape only; transport framing/checksum, terminal assignment, account authenticity, conditional companion eligibility, crypto, routing and host outcomes are not certified. Unknown layouts are review-required, never guessed.
The new parser checks all 17 standard fields, explicit interior blanks, measured ASCII lengths,
message identifier, segment population and length-delimited segment boundaries. The synthetic
positive is one non-TransArmor financial application body, not an authorized terminal/account
or network-positive transaction. Companion bodies remain review-required without their oracle.
JSON declarations are not byte-level observations. Missing serialized producer output remains open.
Field failures are not automatically AI defects: testcase polarity, negative class and intended
violated element are retained for review; intentional negative data must not be repaired as a defect.
Petroleum rules retain their 2025-1 authority; they are not relabelled ATL105 2026-4 rules.
WEX fleet SAF recovery, COMDATA card SAF prohibition, COMDATA fuel-price retry and WEX Money
Code manual entry are deliberately separate. Generic PINless/EBT/check programs are not brands.

## Still unfinished
Whole-spec semantic classification, legacy overlap adjudication and complete conditional-segment
serializers/oracles remain repository work, not merely external approvals. This bounded package
does not claim to finish every network/program requirement. See the completion register for
implemented, repository-pending and external-pending items. Existing native failures are not repaired.

## Evidence
- [summary.json](summary.json)
- [model-package.json](model-package.json)
- [program-denominator-register.csv](program-denominator-register.csv)
- [scope-register.json](scope-register.json)
- [fleet-companion-evidence.json](fleet-companion-evidence.json)
- [fleet-companion-chains.json](fleet-companion-chains.json)
- [application-fixtures.json](application-fixtures.json)
- [application-observations.csv](application-observations.csv)
- [native-gates.json](native-gates.json)
- [completion-register.csv](completion-register.csv)
- [decision-register.csv](decision-register.csv)
- [input-manifest.json](input-manifest.json)
- [delivery-manifest.csv](delivery-manifest.csv)
