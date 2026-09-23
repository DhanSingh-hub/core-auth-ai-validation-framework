# ATL105 Artifact Storage Policy

## Ownership boundaries

| Location | Owner | Policy |
|---|---|---|
| `contract/` | Framework team | Producer-neutral schemas and vocabulary; version changes explicitly. |
| `specifications/ATL105/contract/` | ATL105 Test Team | Specification-specific aliases and contract extensions; aliases never auto-confirm coverage. |
| `specifications/ATL105/docs/` | ATL105 Test Team | Maintained knowledge, strategy, governance, and source-grounded analysis. |
| `specifications/ATL105/schemas/` | ATL105 Test Team | Validation schemas consumed by framework and review tooling. |
| `specifications/ATL105/test-input/ai-solution/runs/<date>/<run>/` | AI producer, read-only after intake | Preserve received files byte-for-byte. Add review metadata outside the imported run. |
| `specifications/ATL105/test-input/ai-solution/test-data/` | Test Team | Controlled fixtures used to validate segment rules and mutations. |
| `specifications/ATL105/test-output/` | Test Team | Independent baselines, crosswalks, traceability, validation evidence, and reproducible outputs. |
| `specifications/ATL105/reports/` | Test Team | Published stakeholder reports and retained historical views. |
| `target/` | Maven | Disposable build output; never treat as source evidence. |

## AI run intake

1. Store each run under a date and run identifier.
2. Do not rename, normalize, or repair files inside an imported run.
3. Record provenance and hashes during intake.
4. Treat producer coverage statistics, approval labels, and confidence values as claims under test.
5. Put Test Solution crosswalks and review decisions under `test-output`, not inside the run.
6. A specification-version discrepancy blocks `CONFIRMED` coverage until independently resolved.

## Generated artifacts

- Commit independently maintained baselines, canonical rule catalogs, explicit crosswalks, and evidence needed to reproduce approval decisions.
- Commit stakeholder reports only when they are intentional review deliverables or retained history.
- Regenerate transient reports from scripts where practical; do not duplicate them under module-root or `bin/` mirrors.
- Never use files under `target/` as committed evidence.
- Every generated report must identify its source run, baseline version, generation command or producer, and generation time.

## Coverage integrity

- Producer-local IDs remain separate.
- Canonical anchors provide shared semantic identity.
- Only evidence-supported `CONFIRMED` mappings count toward baseline coverage.
- `REVIEW_REQUIRED`, aliases, heuristic matches, or unmatched AI requirements do not count as covered.
- Full-chain coverage requires linked BR -> TS -> TC -> TD artifacts, not merely matching anchor labels.
