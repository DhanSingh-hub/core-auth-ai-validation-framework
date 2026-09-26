package com.coreauth.validator.coverage;

/** Auditable Step 3 evidence combining canonical-chain and physical-payload validation. */
public record Run2SegmentValidationEvidence(
        String segment,
        String sourceRun,
        String versionResolutionId,
        int requirements,
        int scenarios,
        int testCases,
        int testData,
        AiCoverageAssessmentReport assessment,
        Run2PayloadBatchValidator.BatchResult payloadBatch) {
}