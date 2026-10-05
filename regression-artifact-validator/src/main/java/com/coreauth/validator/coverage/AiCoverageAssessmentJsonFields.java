package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.node.ObjectNode;

/** Writes coverage counts with explicit denominator names and null percentages when a denominator is ineligible. */
final class AiCoverageAssessmentJsonFields {
    private AiCoverageAssessmentJsonFields() {
    }

    static void write(ObjectNode target, AiCoverageAssessmentReport report) {
        target.put("coverageDenominatorType", "INDEPENDENT_TEST_SOLUTION_RULES");
        target.put("coverageDenominatorStructurallyValid", report.independentRuleDenominatorStructurallyValid());
        target.put("coverageDenominatorCertification", "NOT_ESTABLISHED_BY_STRUCTURAL_ASSESSMENT");
        target.put("coverageDenominator", report.coverageDenominator());
        target.put("confirmedRequirements", report.confirmedRequirements());
        target.put("fullChainRequirements", report.fullChainRequirements());
        target.put("reviewRequired", report.reviewRequired());
        target.put("missingRequirements", report.missingRequirements());
        target.put("unmatchedAiRequirements", report.unmatchedAiRequirements());
        putNullable(target, "confirmedRequirementCoveragePercent", report.confirmedRequirementCoveragePercent());
        putNullable(target, "fullChainCoveragePercent", report.fullChainCoveragePercent());

        target.put("aiBusinessRequirementDenominator", report.aiBusinessRequirementDenominator());
        target.put("aiBusinessRequirementsWithCrosswalkDisposition",
                report.aiBusinessRequirementsWithCrosswalkDisposition());
        target.put("confirmedAiBusinessRequirements", report.confirmedAiBusinessRequirements());
        target.put("reviewRequiredAiBusinessRequirements", report.reviewRequiredAiBusinessRequirements());
        target.put("missingMappedAiBusinessRequirements", report.missingMappedAiBusinessRequirements());
        target.put("unmappedAiBusinessRequirements", report.unmappedAiBusinessRequirements());
        putNullable(target, "confirmedAiBusinessRequirementInventoryPercent",
                report.confirmedAiBusinessRequirementInventoryPercent());
    }

    private static void putNullable(ObjectNode target, String field, Double value) {
        if (value == null) {
            target.putNull(field);
        } else {
            target.put(field, value);
        }
    }
}
