# Unmatched AI and Test BRs by Category

This view adds the reciprocal Test BR gap column to the existing complete-chain AI BR gap listing.

| Category | AI BRs with complete physical chain and no v3 Test BR candidate | Test BRs with no v3 AI BR candidate |
|---|---:|---:|
| Field | 1,565 | 26 |
| Dependency | 749 | 19 |
| Account/Card | 283 | 7 |
| Amount | 277 | 32 |
| Security | 247 | 8 |
| Lifecycle | 222 | 10 |
| Message | 58 | 139 |
| Needs review | 0 | 16 |
| **Total** | **3,401** | **257** |

## Population Criteria

- **AI BR column:** AI BRs in the previously verified list with at least one clean, physically complete scenario-to-test-case-to-payload/metadata chain, and no candidate in the v3 AI-to-Test BR crosswalk.
- **Test BR column:** all 605 catalogued Test BRs in the category register, less the 348 Test BR IDs present in the v3 Test BR candidate index. The 257 remaining Test BRs have no AI BR candidate in that index.

## Interpretation

Candidate absence is not an authorized semantic `NOT_MATCH`. Candidate links and category labels remain review proposals. Physical chain presence in the AI BR column does not establish semantic agreement or host execution; both remain `NOT_ASSESSED`.

The Test BR detail list retains category review status and source-anchor metadata. See `test-brs-without-ai-br-candidates.csv`; category counts are also available as `unmatched-br-counts-by-category.csv`.

The AI BR detail list remains in [the complete-chain listing](../2026-10-15-complete-chain-unmatched-ai-br-v1/complete-chain-ai-brs-without-test-br-candidates.csv).

## Inputs

- `2026-10-14-ai-test-br-crosswalk-v3/test-br-ai-br-index.csv`
- `2026-10-15-ai-test-br-category-groups-v1/categorized-business-requirements.csv`
- `2026-10-15-complete-chain-unmatched-ai-br-v1/complete-chain-ai-brs-without-test-br-candidates.csv`
