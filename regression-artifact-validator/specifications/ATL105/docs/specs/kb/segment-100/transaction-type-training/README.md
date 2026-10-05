# Transaction-Type Training Handoff

This shared ledger coordinates Test Solution training for all 23 Appendix G transaction codes. The baseline is not completion evidence.

## Claim and Work

1. Pull the latest `Develop` before choosing work.
2. Choose one task file under `codes/`. Claim it by setting `status` to `IN_PROGRESS` and filling `owner.user`, `owner.machine`, `owner.branch`, and `owner.claimedAt`. Commit/push the claim before substantial work so teammates can see ownership.
3. Update only that code's task file. Record each activity in `workLog` with timestamp, user, machine, branch, action, outcome, evidence paths, blockers, and next action.
4. Also add one immutable event file under `events/` for each claim, release, handoff, significant training update, or validation run. Use a unique filename containing timestamp, user, machine, code, and action; do not edit or replace another person's event file.
5. Keep gate evidence linked to repository files. Mark a gate `COMPLETE` only when its evidence exists and passes; use `NOT_APPLICABLE` only with a source-backed rationale.
6. Before handing off, update `handoff.nextAction`, `handoff.notes`, and owner. Set `UNCLAIMED` when releasing the task.
7. Commit and push the code task file and event. Run `GenerateTransactionTypeTrainingLedger` to refresh the index and overview.

For codes `9`, `D`, `E`, `K`, `L`, `M`, `N`, `Q`, `T`, and `V`, follow [the special-flow training technique](special-flow-training-technique.md), review the [draft BR baseline](../../../../../test-output/test-json/special-transaction-type-br-baseline.md), [draft chain package](../../../../../test-output/test-json/special-transaction-type-br-ts-tc-td-draft-package.md), and [combined 23-code matrix](../../../../../test-output/test-json/combined-23-transaction-type-br-ts-tc-td-matrix.md). These are separate from the standard-financial Segment 100 code-flow package.

For the BR inventory and traceability split, see [single-step and multi-step BR traceability](single-multi-step-br-traceability.md). It records counts, source anchors, multi-step catalog gaps, and the TOR eligibility discrepancy.

## Completion Gate

Do not mark a code `COMPLETE` until source review, independent BRs, applicable positive/negative/boundary/scenario cases, lifecycle or justified N/A, physical synthetic Test Data JSON, schema/rule/chain validation, and reviewer/handoff evidence are recorded. Never copy AI output into Test Solution truth.

Each transaction code has a separate file to reduce parallel-edit conflicts. If two users claim the same code, the first pushed claim owns it; coordinate before continuing.
