# Run4 Semantic BR Review Packet

This is an evidence packet, not a semantic approval. Every Test Solution rule remains PENDING_SEMANTIC_REVIEW and receives no coverage credit.

Manual-search findings are recorded separately in `run4-manual-search-findings.json`. A `NO_EQUIVALENT_AI_BR_FOUND` finding documents the completed corpus search only; it does not record SME approval or grant coverage.

Run4 AI BRs included: 5,153 (AI approval filter: none). Independent Test Solution BR denominator: 601.
Source-anchor candidate pairs: 1,950. Independent Test BRs with candidates: 270. Independent Test BRs requiring manual corpus search: 331.

## Review sequence

1. Start with each `run4-test-br-semantic-review.csv` Test BR row; do not limit review to rows that already have anchor candidates.
2. For candidate rows, compare the full AI statement, candidate Test Rule, cited ATL105 source excerpt, and chain evidence in `run4-ai-test-br-semantic-review-pairs.jsonl`.
3. For rows without anchor candidates, search the complete 5,153-row AI BR chain export by source/context and behavior. Empty candidate lists are not `AI_ONLY` decisions.
4. A named authorized reviewer records one decision with exact ATL105 2026-3 anchor and rationale. `CONFIRMED_MATCH` only when source and business behavior are equivalent; partial/composite/context mismatch stays `REVIEW_REQUIRED` or receives the appropriate allowed disposition.
5. Recompute coverage as distinct independent Test BR IDs with effective confirmed Run4 decisions / eligible independent Test BR denominator. Count a Test BR once even if multiple AI BRs map to it.

## Recording a decision

Use the append-only Java recorder with the Run4-specific register. Do not edit the seeded register in place. A decision input uses the pair's `reviewId` as `subjectId`, the pair's `matchSegment`, the matched Test Rule's `sourceAnchor`, a named reviewer, rationale, and `matchedTestRuleId` for `CONFIRMED_MATCH`.

```powershell
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.RecordSmeDecision' '-Dexec.args=--decision-json path/to/reviewer-decision.json --decision-register specifications/ATL105/test-output/ai-solution-independent-review/2026-10-07-Run4/analysis-v2/run4-semantic-review-v2/run4-semantic-decision-register.json'
mvn.cmd exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.ValidateSmeDecisionRegister' '-Dexec.args=specifications/ATL105/test-output/ai-solution-independent-review/2026-10-07-Run4/analysis-v2/run4-semantic-review-v2/run4-semantic-decision-register.json'
```

Example decision input (replace placeholders with one pair's exact values; never use a generated/fabricated reviewer identity):

```json
{
  "subjectType": "AI_TO_TEST_CROSSWALK",
  "subjectId": "<reviewId>",
  "segment": "<matchSegment>",
  "decision": "CONFIRMED_MATCH",
  "reviewer": "<authorized reviewer identity>",
  "rationale": "<source-backed equivalence rationale>",
  "sourceAnchor": {
    "specification": "ATL105",
    "version": "2026-3",
    "section": "<test rule section>",
    "segment": "<test rule segment>",
    "element": "<test rule element>",
    "rule": "<test rule source key>"
  },
  "matchedTestRuleId": "<testRuleId>"
}
```

No reviewer identity, decision, approval, or semantic result has been fabricated. Existing September Run2 decisions were not reused.
