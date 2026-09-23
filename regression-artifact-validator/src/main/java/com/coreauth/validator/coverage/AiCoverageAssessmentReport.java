package com.coreauth.validator.coverage;

import com.coreauth.validator.validation.ValidationResult;

import java.util.Map;

/** One independent verdict spanning the six Test Solution review strategies. */
public record AiCoverageAssessmentReport(
        int coverageDenominator,
        int confirmedRequirements,
        int fullChainRequirements,
        int reviewRequired,
        int missingRequirements,
        int unmatchedAiRequirements,
        double confirmedRequirementCoveragePercent,
        double fullChainCoveragePercent,
        Map<String, Boolean> strategyReadiness,
        ValidationResult baselineValidation,
        ValidationResult testCaseValidation,
        ValidationResult traceabilityValidation,
        ValidationResult payloadValidation) {

    public AiCoverageAssessmentReport {
        strategyReadiness = Map.copyOf(strategyReadiness);
    }

    public boolean executionReady() {
        return strategyReadiness.values().stream().allMatch(Boolean::booleanValue)
                && fullChainRequirements == coverageDenominator;
    }
}