package com.coreauth.validator.coverage;

import java.util.List;

/**
 * Mirrors the segment-level Coverage Mapping Ratio math used by
 * {@code report-all-segments-br-ratio.ps1}: only CONFIRMED, valid records count as covered;
 * REVIEW_REQUIRED and MISSING records (plus unmatched AI requirements) fall into the review queue.
 */
public final class CoverageMappingRatioCalculator {

    private CoverageMappingRatioCalculator() {
    }

    public static CoverageMappingRatioResult calculate(int testSolutionRequirements,
            List<CoverageCrosswalkRecord> records,
            int unmatchedAiRequirements) {
        int confirmed = 0;
        int reviewRequired = 0;
        int missing = 0;
        int missingScenarios = 0;
        int invalidRecords = 0;

        for (CoverageCrosswalkRecord record : records) {
            if (!record.isValid()) {
                invalidRecords++;
                continue;
            }
            switch (record.matchStatus()) {
                case "CONFIRMED" -> {
                    confirmed++;
                    if (!record.hasScenario()) {
                        missingScenarios++;
                    }
                }
                case "REVIEW_REQUIRED" -> reviewRequired++;
                case "MISSING" -> missing++;
                default -> throw new IllegalArgumentException("Unknown matchStatus: " + record.matchStatus());
            }
        }

        int reviewQueue = reviewRequired + missing + unmatchedAiRequirements;
        double percent = testSolutionRequirements == 0
                ? 0.0
                : Math.round(1000.0 * confirmed / testSolutionRequirements) / 10.0;

        return new CoverageMappingRatioResult(confirmed, reviewQueue, unmatchedAiRequirements,
                missingScenarios, invalidRecords, percent);
    }
}
