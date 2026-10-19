# All-AI-BR Chain-to-Test-BR Review Queue

This queue contains one register row and one chain-evidence record for every AI BR in the 6,880-row candidate catalog. The candidate file ranks up to the configured top-K Test BR retrieval leads for each AI BR and retains every existing v6 source-anchor lead.

All Test package BR records, including authored BRs outside the catalog, are searched. `test-chain-evidence.jsonl.gz` retains their direct scenario/case/data records. `review-progress.csv` consolidates prior preliminary screenings; batches are incomplete until every assigned record is reviewed. No full-chain review is marked complete by this builder.

## Review order

1. Start with `all-ai-br-review-register.csv`; filter by `reviewDisposition`, category, chain status, or source page.
2. For each AI BR, inspect its linked scenarios, cases, and physical-evidence paths in `ai-br-chain-evidence.jsonl.gz`.
3. Compare each ranked Test BR lead in `ai-test-br-ranked-review-leads.csv` against the full BR obligation and chain context. The Test package currently marks its catalogued rules `DRAFT_REVIEW_REQUIRED`; treat them as candidate targets, not approved coverage.
4. Record adjudication separately. `retrievalScore` is prioritization only; all semantic statuses remain `NOT_ASSESSED`. A row with no retrieval lead still requires a direct review across all Test BRs.

AI scenario approval is not required for inclusion. AI physical evidence paths and frozen inventory hashes are references to inspect, not independent proof of payload semantics.
