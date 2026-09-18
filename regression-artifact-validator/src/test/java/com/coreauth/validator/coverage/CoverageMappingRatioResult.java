package com.coreauth.validator.coverage;

/** Result of a single segment's Coverage Mapping Ratio calculation. */
public record CoverageMappingRatioResult(
        int baselineCovered,
        int reviewQueue,
        int unmatchedAiRequirements,
        int missingScenarios,
        int invalidRecords,
        double confirmedBaselineCoveragePercent) {
}
