# All-AI-BR Chain-to-Test-BR Review Queue

This queue contains one register row and one chain-evidence record for every AI BR in the 6,880-row candidate catalog. The candidate file ranks up to the configured top-K Test BR retrieval leads for each AI BR and retains every existing v6 source-anchor lead. Preliminary batches 001-004 cover 28 AI BRs; all 28 remain open pending authorized review, and 6,852 AI BRs have not yet received preliminary review.

`review-batch-index.csv` assigns all 6,880 BRs to review batches. Batch 001 has four records. Batches 002-069 each have 100 assigned records, and batch 070 has 76. Only eight records in each of batches 002-004 were screened in the original preliminary files; those batches are not complete. Every preliminary record still requires authorized review.

## October 16 Continuation

[continuation-2026-10-16.json](continuation-2026-10-16.json) screens ten further records (:010-:019) from batch 002, including eight hash-verified physical payload samples. The union with the original preliminary files is 38 screened BRs and 6,842 not yet screened. The original CSV index and summary retain their earlier 28-record snapshot; consult the continuation as an additional progress record. No full-payload or semantic review is claimed complete.

The Test package has 1,330 unique BR records, of which the ranked queue searches only the 605 `BR-RULE-*` records. The other 725 authored records must also be searched; they are not necessarily distinct atomic obligations. The continuation identifies additional counterparts, including `BR-SEG100-GAP-DISCRETIONARY` and Appendix E Table Load code rules. Absence from the ranked queue is not proof of absence from the Test model.

## Review order

1. Start with `all-ai-br-review-register.csv`; filter by `reviewDisposition`, category, chain status, or source page.
2. Select a `batchId` in `review-batch-index.csv`, then inspect each AI BR's linked scenarios, cases, and physical-evidence paths in `ai-br-chain-evidence.jsonl.gz`.
3. Compare ranked Test BR leads in `ai-test-br-ranked-review-leads.csv` against the full BR obligation and chain context. The Test package currently marks its catalogued rules `DRAFT_REVIEW_REQUIRED`; treat them as candidate targets, not approved coverage.
4. Use `preliminary-review-batch-001.json` through `preliminary-review-batch-004.json` as worked preliminary batches, not an authorized decision register. Record subsequent adjudications separately. `retrievalScore` is prioritization only; top-K is not an exhaustive semantic search and all semantic statuses remain `NOT_ASSESSED`. Every AI BR requires direct review across the full Test BR population, including candidates below top-K.

AI scenario approval is not required for inclusion. AI physical evidence paths and frozen inventory hashes are references to inspect, not independent proof of payload semantics.
