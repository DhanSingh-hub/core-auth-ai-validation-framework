package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.RequirementCrosswalkEntry;
import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.validation.ValidationResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Coordinates the six independent Test Solution strategies for one canonical AI package. */
public final class AiCoverageAssessmentService {
    private final CanonicalTraceabilityValidator traceabilityValidator = new CanonicalTraceabilityValidator();
    private final CanonicalTestCaseQualityValidator testCaseValidator = new CanonicalTestCaseQualityValidator();

    public AiCoverageAssessmentReport assess(CanonicalArtifactPackage aiPackage,
                                             IndependentRequirementBaseline baseline,
                                             List<NamedPayloadValidator> payloadValidators) {
        ValidationResult baselineValidation = baseline.validate();
        ValidationResult testCaseValidation = testCaseValidator.validate(aiPackage);
        ValidationResult traceabilityValidation = traceabilityValidator.validate(aiPackage);
        ValidationResult payloadValidation = validatePayloads(aiPackage, payloadValidators);

        List<IndependentRequirementBaseline.Requirement> denominator = baseline.inScopeRequirements();
        Map<String, CanonicalRequirement> aiRequirements = indexAiRequirements(aiPackage);
        Map<String, RequirementCrosswalkEntry> crosswalk = indexCrosswalk(aiPackage);
        Set<String> referencedAiIds = new HashSet<>();
        List<CoverageCrosswalkRecord> coverageRecords = new ArrayList<>();
        int fullChain = 0;
        int missing = 0;
        int reviewRequired = 0;

        for (IndependentRequirementBaseline.Requirement requirement : denominator) {
            RequirementCrosswalkEntry entry = crosswalk.get(requirement.id());
            String status = evidenceQualifiedStatus(aiPackage, requirement, entry, aiRequirements, referencedAiIds);
            CoverageCrosswalkRecord record = new CoverageCrosswalkRecord(status,
                    entry == null ? null : entry.getReviewOwner());
            boolean hasFullChain = "CONFIRMED".equals(status) && hasFullChain(aiPackage, requirement.sourceAnchors());
            if (!hasFullChain) {
                record.markMissingScenario();
            } else {
                fullChain++;
            }
            coverageRecords.add(record);
            if ("MISSING".equals(status)) missing++;
            if ("REVIEW_REQUIRED".equals(status)) reviewRequired++;
        }

        int unmatchedAi = (int) aiRequirements.keySet().stream().filter(id -> !referencedAiIds.contains(id)).count();
        CoverageMappingRatioResult ratio = CoverageMappingRatioCalculator.calculate(
                denominator.size(), coverageRecords, unmatchedAi);
        double fullChainPercent = denominator.isEmpty() ? 0.0
                : Math.round(1000.0 * fullChain / denominator.size()) / 10.0;

        Map<String, Boolean> readiness = new LinkedHashMap<>();
        readiness.put("CANONICAL_ANCHORS", baselineValidation.isValid());
        readiness.put("INDEPENDENT_BR_BASELINE", baselineValidation.isValid() && !baseline.requirements().isEmpty());
        readiness.put("TEST_CASE_QUALITY", testCaseValidation.isValid());
        readiness.put("PAYLOAD_COMPLIANCE", payloadValidation.isValid() && payloadValidators != null
                && !payloadValidators.isEmpty());
        readiness.put("COVERAGE_DENOMINATOR", baselineValidation.isValid() && !denominator.isEmpty());
        readiness.put("INDEPENDENT_TRACEABILITY", traceabilityValidation.isValid()
                && fullChain == denominator.size() && !denominator.isEmpty());

        return new AiCoverageAssessmentReport(denominator.size(), ratio.baselineCovered(), fullChain,
                reviewRequired, missing, unmatchedAi, ratio.confirmedBaselineCoveragePercent(),
                fullChainPercent, readiness, baselineValidation, testCaseValidation,
                traceabilityValidation, payloadValidation);
    }

    private ValidationResult validatePayloads(CanonicalArtifactPackage aiPackage,
                                               List<NamedPayloadValidator> validators) {
        ValidationResult result = new ValidationResult("payload-compliance");
        for (NamedPayloadValidator validator : safe(validators)) {
            ValidationResult current = validator.validator().apply(aiPackage);
            if (current == null) {
                result.addError("PayloadCompliance", validator.name() + " returned no result");
            } else {
                result.merge(current);
            }
        }
        if (validators == null || validators.isEmpty()) {
            result.addWarning("No segment payload validator was supplied; payload compliance is not ready");
        }
        return result;
    }

    private String evidenceQualifiedStatus(CanonicalArtifactPackage aiPackage,
                                           IndependentRequirementBaseline.Requirement baselineRequirement,
                                           RequirementCrosswalkEntry entry,
                                           Map<String, CanonicalRequirement> aiRequirements,
                                           Set<String> referencedAiIds) {
        if (entry == null || entry.getMatchStatus() == RequirementMatchStatus.MISSING) {
            return "MISSING";
        }
        List<String> aiIds = safe(entry.getAiRequirementIds());
        referencedAiIds.addAll(aiIds);
        if (entry.getMatchStatus() != RequirementMatchStatus.CONFIRMED || blank(entry.getMatchReason())) {
            return "REVIEW_REQUIRED";
        }
        if (!manifestMatchesAnchors(aiPackage, baselineRequirement.sourceAnchors())) {
            return "REVIEW_REQUIRED";
        }
        boolean anchoredEvidence = aiIds.stream().map(aiRequirements::get).filter(value -> value != null)
                .anyMatch(aiRequirement -> sharesAnchor(baselineRequirement.sourceAnchors(), aiRequirement.getSourceAnchors()));
        return anchoredEvidence ? "CONFIRMED" : "REVIEW_REQUIRED";
    }

    private static boolean manifestMatchesAnchors(CanonicalArtifactPackage aiPackage, List<SourceAnchor> anchors) {
        if (aiPackage == null || aiPackage.getManifest() == null
                || blank(aiPackage.getManifest().getSpecificationVersion())) {
            return false;
        }
        String version = aiPackage.getManifest().getSpecificationVersion();
        return safe(anchors).stream().filter(anchor -> anchor != null)
                .allMatch(anchor -> version.equalsIgnoreCase(anchor.getVersion()));
    }

    private boolean hasFullChain(CanonicalArtifactPackage aiPackage, List<SourceAnchor> anchors) {
        if (aiPackage == null) return false;
        Map<String, CanonicalRequirement> requirements = indexAiRequirements(aiPackage);
        Map<String, CanonicalScenario> scenarios = indexById(aiPackage.getTestScenarios(), CanonicalScenario::getId);
        Map<String, CanonicalTestCase> testCases = indexById(aiPackage.getTestCases(), CanonicalTestCase::getId);
        List<CanonicalTestData> testData = safe(aiPackage.getTestData());

        return safe(anchors).stream().allMatch(anchor -> requirements.values().stream()
            .filter(requirement -> sharesAnchor(List.of(anchor), requirement.getSourceAnchors()))
            .anyMatch(requirement -> scenarios.values().stream()
                .filter(scenario -> safe(scenario.getRequirementIds()).contains(requirement.getId()))
                .filter(scenario -> sharesAnchor(List.of(anchor), scenario.getSourceAnchors()))
                .anyMatch(scenario -> testCases.values().stream()
                    .filter(testCase -> safe(testCase.getScenarioIds()).contains(scenario.getId()))
                    .filter(testCase -> sharesAnchor(List.of(anchor), testCase.getSourceAnchors()))
                    .anyMatch(testCase -> testData.stream()
                        .filter(data -> safe(data.getTestCaseIds()).contains(testCase.getId()))
                        .anyMatch(data -> sharesAnchor(List.of(anchor), data.getSourceAnchors()))))));
    }

    private static Map<String, CanonicalRequirement> indexAiRequirements(CanonicalArtifactPackage aiPackage) {
        if (aiPackage == null || aiPackage.getBusinessRequirements() == null) return Map.of();
        return aiPackage.getBusinessRequirements().stream().filter(value -> value != null && value.getId() != null)
                .collect(Collectors.toMap(CanonicalRequirement::getId, Function.identity(), (left, right) -> left));
    }

    private static Map<String, RequirementCrosswalkEntry> indexCrosswalk(CanonicalArtifactPackage aiPackage) {
        Map<String, RequirementCrosswalkEntry> result = new HashMap<>();
        if (aiPackage != null && aiPackage.getRequirementCrosswalk() != null) {
            aiPackage.getRequirementCrosswalk().stream()
                    .filter(value -> value != null && value.getTestRequirementId() != null)
                    .forEach(value -> result.putIfAbsent(value.getTestRequirementId(), value));
        }
        return result;
    }

    private static <T> Map<String, T> indexById(List<T> values, Function<T, String> idFunction) {
        return safe(values).stream().filter(value -> value != null && idFunction.apply(value) != null)
                .collect(Collectors.toMap(idFunction, Function.identity(), (left, right) -> left));
    }

    private static boolean sharesAnchor(List<SourceAnchor> left, List<SourceAnchor> right) {
        Set<String> keys = safe(left).stream().filter(value -> value != null)
                .map(SourceAnchor::canonicalKey).collect(Collectors.toSet());
        return safe(right).stream().filter(value -> value != null)
                .map(SourceAnchor::canonicalKey).anyMatch(keys::contains);
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? Collections.emptyList() : values;
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }

    public record NamedPayloadValidator(
            String name,
            Function<CanonicalArtifactPackage, ValidationResult> validator) {
    }
}