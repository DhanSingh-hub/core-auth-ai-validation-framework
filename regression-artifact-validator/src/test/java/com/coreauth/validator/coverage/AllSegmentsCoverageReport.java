package com.coreauth.validator.coverage;

import java.util.LinkedHashMap;
import java.util.Map;

/** Mirrors the "all segments" aggregate produced by report-all-segments-br-ratio.ps1. */
public record AllSegmentsCoverageReport(
        Map<String, SegmentTotals> segmentSummaries,
        int combinedTestSolutionRequirements,
        int combinedBaselineCovered,
        int reviewQueue,
        int potentialMatches,
        int unmatchedAiRequirements,
        double confirmedBaselineCoveragePercent,
        boolean containsSegmentSummaryPanel) {

    public record SegmentTotals(int testSolutionRequirements, int baselineCovered) {
    }

    public static AllSegmentsCoverageReport from(Map<String, SegmentTotals> segments,
            int potentialMatches, int unmatchedAiRequirements) {
        int combinedTotal = 0;
        int combinedCovered = 0;
        for (SegmentTotals totals : segments.values()) {
            combinedTotal += totals.testSolutionRequirements();
            combinedCovered += totals.baselineCovered();
        }
        double percent = combinedTotal == 0
                ? 0.0
                : Math.round(1000.0 * combinedCovered / combinedTotal) / 10.0;
        int reviewQueue = potentialMatches + unmatchedAiRequirements;
        return new AllSegmentsCoverageReport(new LinkedHashMap<>(segments), combinedTotal, combinedCovered,
                reviewQueue, potentialMatches, unmatchedAiRequirements, percent, true);
    }
}
