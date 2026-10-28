# Message BR and Chain Comparison

| Measure | Count |
|---|---:|
| aiMessageBrs | 97 |
| testMessageBrs | 243 |
| messageToMessageMatrixRows | 23,571 |
| existingCandidatePairsTouchingMessage | 240 |
| additionalContextReviewLeads | 809 |
| testMessageBrsWithExistingCandidates | 104 |
| testMessageBrsWithAnyReviewLead | 146 |
| aiMessageBrsWithAnyReviewLead | 45 |
| aiMessageBrsWithCleanPhysicalChain | 62 |
| testMessageBrsWithStructuralChain | 243 |
| testMessageBrsWithPlaceholders | 243 |
| testMessageBrsWithExecutablePayloadData | 0 |
| candidatePairsWithFixedTypeConflict | 0 |
| candidatePairsWithFormatCapacityGap | 5 |
| physicalPayloadMetadataPairsHashVerified | 6,754 |
| physicalPayloadMetadataPairsMissingOrIncomplete | 126 |
| semanticApprovedPairs | 0 |
| certifiedCompleteChainMatches | 0 |

## Interpretation

AI scenario producer approval is not required for crosswalk inclusion; the candidate scenario catalog is used, and scenario links remain source evidence rather than semantic validation. Existing anchor links and additional context leads are separate review queues, not semantic matches. Retrieval scores only prioritize review; they are not coverage percentages or confidence. All Message BRs are retained, including rows with no lead. AI and Test complete chains are recorded separately with IDs and original records. Physical AI requests/metadata are checked against the frozen inventory. Test placeholders, unset case oracles and missing executable payloads block complete-chain certification. No host execution or SME approval is inferred.

The maximum reported here is reviewable candidate reach, not a maximum approved match count. Compare the BR obligations, transaction/segment applicability, scenario intent, independent expected outcome and data before adjudicating any pair. Cross-category links are retained because categories alone do not prove or disprove equivalence.

Known example: AI :1907 concerns four-digit Segment Length. Some anchor candidates instead concern maximum segment capacity; shared element 84 alone does not establish the same obligation. Its SC-7281 chain has clean cases TC-009116 through TC-009119; TC-009120 is not credited by the clean v6 route set. Test counterparts are review-placeholder chains.

Fixed Segment Type contradictions and format-versus-capacity gaps are flagged on existing candidates; new leads with these issues are excluded. Missing/incomplete physical pairs are retained as gaps, not credited as verified. Actual scalar structure values are observations only, not wire-length or independent expected-outcome validation.
