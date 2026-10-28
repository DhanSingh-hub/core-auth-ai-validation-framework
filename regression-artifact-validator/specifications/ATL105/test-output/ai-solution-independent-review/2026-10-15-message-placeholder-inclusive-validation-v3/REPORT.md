# Message BR Matching and Artifact Validation

The comparison includes Test BR, scenario, case and data records even when their status is REVIEW_REQUIRED or their IDs use review placeholders. Placeholder status is not an exclusion criterion for candidate matching. It remains recorded as validation/readiness evidence and is never converted into execution or approval.

| Measure | Result |
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
| testMessageBrsWithAlternativeExactAnchorChains | 41 |
| testMessageBrsWithAlternativeExecutablePayloadData | 0 |
| candidatePairsWithFixedTypeConflict | 0 |
| candidatePairsWithFormatCapacityGap | 5 |
| physicalPayloadMetadataPairsHashVerified | 6,754 |
| physicalPayloadMetadataPairsMissingOrIncomplete | 126 |
| semanticApprovedPairs | 0 |
| certifiedCompleteChainMatches | 0 |
| candidatePairs | 1,049 |
| pairsWithAiCleanPhysicalChain | 805 |
| pairsWithTestStructuralChainPlaceholderIncluded | 1,049 |
| pairsWithExactAnchorAuthoredTestAlternatives | 187 |
| pairsWithDraftTestFixtures | 187 |
| pairsWithExactScalarOverlap | 46 |
| pairsWithExactSegmentTypeOverlap | 46 |
| sameCategoryMessagePairsWithExactScalarOverlap | 11 |
| messageTestDraftFixtureVectorsIncluded | 58 |
| messageTestDraftFixtureVectorsReviewRequired | 58 |
| executedPairs | 0 |
| certifiedMatches | 0 |
| reviewPlaceholdersUsedAsExclusion | 0 |

Draft Test fixture `expectedValidation` labels (not executed): COVERED=1, FAIL=9, PASS=14, PASS_WITH_REVIEW=1, REVIEW=6, REVIEW_REQUIRED=27.

## Artifact Crossmatch

The pair CSV joins every existing anchor candidate and context lead to the full primary Test chain and exact-full-anchor authored alternatives. It checks AI observed structure scalars from complete, hash-verified routes against scalar values in draft Test fixture payloads. An exact scalar overlap means only that the same key/value was observed on both sides; it does not show that the Test case exercised the AI BR, that the Test vector is approved, or that expected outcomes agree.

## Validation Status

AI payload and metadata hashes are checked against the frozen delivery inventory. Test placeholder records remain in the match set and expose their scenario/case/data links. The selected Test package contains draft fixture values, but these remain REVIEW_REQUIRED; no Test execution is inferred. Semantic equivalence, wire agreement, host outcomes and SME decisions remain NOT_ASSESSED.

The Segment Length AI BR :1907 has four clean physical cases but no observable Segment Length scalar in those payloads. Its four-digit format candidates and maximum-capacity candidates remain distinct obligations; exact element 84 alone is not validation.
