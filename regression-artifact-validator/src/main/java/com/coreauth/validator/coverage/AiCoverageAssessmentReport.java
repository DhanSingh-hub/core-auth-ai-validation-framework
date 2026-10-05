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
        Double confirmedRequirementCoveragePercent,
        Double fullChainCoveragePercent,
        Map<String, Boolean> strategyReadiness,
        ValidationResult baselineValidation,
        ValidationResult testCaseValidation,
        ValidationResult traceabilityValidation,
        ValidationResult payloadValidation,
        boolean independentRuleDenominatorStructurallyValid,
        int aiBusinessRequirementDenominator,
        int aiBusinessRequirementsWithCrosswalkDisposition,
        int confirmedAiBusinessRequirements,
        int reviewRequiredAiBusinessRequirements,
        int missingMappedAiBusinessRequirements,
        int unmappedAiBusinessRequirements,
        Double confirmedAiBusinessRequirementInventoryPercent) {

    public AiCoverageAssessmentReport(
            int coverageDenominator,
            int confirmedRequirements,
            int fullChainRequirements,
            int reviewRequired,
            int missingRequirements,
            int unmatchedAiRequirements,
            Double confirmedRequirementCoveragePercent,
            Double fullChainCoveragePercent,
            Map<String, Boolean> strategyReadiness,
            ValidationResult baselineValidation,
            ValidationResult testCaseValidation,
            ValidationResult traceabilityValidation,
            ValidationResult payloadValidation) {
        this(coverageDenominator, confirmedRequirements, fullChainRequirements, reviewRequired,
                missingRequirements, unmatchedAiRequirements, confirmedRequirementCoveragePercent,
                fullChainCoveragePercent, strategyReadiness, baselineValidation, testCaseValidation,
                traceabilityValidation, payloadValidation, true, 0, 0, 0, 0, 0, 0, null);
    }

    public AiCoverageAssessmentReport {
        strategyReadiness = Map.copyOf(strategyReadiness);
    }

    public boolean executionReady() {
        return independentRuleDenominatorStructurallyValid
            && strategyReadiness.values().stream().allMatch(Boolean::booleanValue)
                && fullChainRequirements == coverageDenominator;
    }
}