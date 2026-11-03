# Mastercard / Maestro 2026-4 Test Model Improvement

## Outcome
- Independent, source-pinned atomic draft rules: **26**.
- Rule/context applicability rows: **42**.
- Logical scenarios / positive and negative TC/TD: **42 / 84 / 84**.
- Legacy BRs retained for release/applicability review: **1343**.
- Candidate crosswalk rows / rows with AI candidates: **42 / 12**.
- Confirmed independent BR coverage: **NOT CALCULABLE / NOT ASSESSED**.
- Approved denominator: **not established**; the bounded draft overlay is not a complete denominator.
- Execution readiness: **BLOCKED**. No legacy package or AI delivery was modified.

## What improved
1. A distinct 2026-4 overlay pins every literal quote to its PDF page and text SHA-256.
2. Each rule has explicit network/program context and transaction preconditions; generic credit
   rules are not silently inherited by Fleet, and debit rules exclude Signature Debit as Credit.
3. Every context gets source-linked positive/control and single-predicate negative/mutant vectors.
   Logical observations are synthetic, not wire messages, device measurements or issuer outcomes.
4. Every legacy BR is retained with existing structural chain counts, source anchors and three
   unresolved context decisions. No heuristic exclusion or 2026-4 relabelling occurs.
5. A delivery-hash-bound crosswalk records same-page lexical candidates and physical-chain
   evidence separately. Missing candidates mean NOT_ASSESSED, never AI_ONLY or proven absence.
6. Eight persisted BA/SME decision groups cover completeness/overlap, Fleet inheritance,
   savings/ACH, Signature Debit/Common AID, TOR conflicts, MOTO completion, fixtures and subtypes.

## Existing companion evidence preserved
The existing Petroleum 2025-1 Mastercard Fleet draft has 24 BRs and 60/60/60 TS/TC/TD.
It is indexed and hash-bound in companion-evidence.json, not copied or silently approved.
The companion's source-release and overlap decisions remain separate from this ATL105 overlay.
The AI review register retains every supplied supporting rule, including those without a candidate.

## Boundaries
Clause presence in both releases is an exact normalized text observation, not whole-rule equivalence.
The draft atomicity/context choices need independent review; source quotation does not certify them.
Do not add this overlay to the 1343-rule legacy population: overlap is unresolved.
Do not interpret candidate counts or logical fixture passes as confirmed semantic coverage.
Full payload execution and simulator/host response checks remain outside this builder.

## Evidence
- [Summary and hashes](summary.json)
- [Independent BR/TS/TC/TD overlay](model-package.json)
- [Applicability and denominator register](denominator-register.csv)
- [Legacy carry-forward review register](legacy-review-register.csv)
- [Delivery-bound AI candidate crosswalk](delivery-crosswalk.csv)
- [Complete AI supporting-rule review register](ai-rule-review-register.csv)
- [Existing Fleet companion evidence](companion-evidence.json)
- [BA/SME decisions](ba-sme-decisions.csv)
- [Input identity manifest](input-manifest.json)
