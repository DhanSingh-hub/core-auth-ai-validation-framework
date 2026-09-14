package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Validates package integrity and traceability using source anchors, not producer-local IDs. */
public final class CanonicalTraceabilityValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "canonical-package" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "canonical-package" : packageId);
        if (artifactPackage == null) {
            result.addError("Package", "artifact package is required");
            return result;
        }

        validateManifest(artifactPackage.getManifest(), result);
        Map<String, CanonicalRequirement> requirements = index(artifactPackage.getBusinessRequirements(),
                CanonicalRequirement::getId, CanonicalRequirement::getSourceAnchors, "BusinessRequirement", result);
        Map<String, CanonicalScenario> scenarios = index(artifactPackage.getTestScenarios(),
                CanonicalScenario::getId, CanonicalScenario::getSourceAnchors, "TestScenario", result);
        Map<String, CanonicalTestCase> testCases = index(artifactPackage.getTestCases(),
                CanonicalTestCase::getId, CanonicalTestCase::getSourceAnchors, "TestCase", result);
        Map<String, CanonicalTestData> testData = index(artifactPackage.getTestData(),
                CanonicalTestData::getId, CanonicalTestData::getSourceAnchors, "TestData", result);

        validateScenarios(scenarios, requirements, result);
        validateTestCases(testCases, scenarios, result);
        validateTestData(testData, testCases, result);
        validateOrphans(requirements, scenarios, testCases, testData, result);
        return result;
    }

    private static void validateManifest(PackageManifest manifest, ValidationResult result) {
        if (manifest == null) {
            result.addError("Manifest", "manifest is required");
            return;
        }
        required(manifest.getPackageId(), "Manifest", "packageId", result);
        if (!"ATL105".equalsIgnoreCase(manifest.getSpecification())) {
            result.addError("Manifest", "specification must be ATL105, got: " + manifest.getSpecification());
        }
        required(manifest.getSpecificationVersion(), "Manifest", "specificationVersion", result);
    }

    private static <T> Map<String, T> index(List<T> values, Function<T, String> idFunction,
                                             Function<T, List<SourceAnchor>> anchorsFunction, String stage,
                                             ValidationResult result) {
        Map<String, T> indexed = new HashMap<>();
        if (values == null || values.isEmpty()) {
            result.addError(stage, "at least one artifact is required");
            return indexed;
        }
        for (T value : values) {
            String id = value == null ? null : idFunction.apply(value);
            if (isBlank(id)) {
                result.addError(stage, "artifact id is required");
                continue;
            }
            if (indexed.putIfAbsent(id, value) != null) {
                result.addError(stage, "duplicate artifact id: " + id);
            }
            validateAnchors(anchorsFunction.apply(value), stage, id, result);
        }
        return indexed;
    }

    private static void validateAnchors(List<SourceAnchor> anchors, String stage, String id, ValidationResult result) {
        if (anchors == null || anchors.isEmpty()) {
            result.addError(stage, id + " must contain at least one sourceAnchor");
            return;
        }
        for (SourceAnchor anchor : anchors) {
            if (anchor == null || isBlank(anchor.getSpecification()) || isBlank(anchor.getVersion())
                    || (isBlank(anchor.getSection()) && isBlank(anchor.getSegment())
                    && isBlank(anchor.getElement()) && isBlank(anchor.getRule()))) {
                result.addError(stage, id + " contains an incomplete sourceAnchor");
            }
        }
    }

    private static void validateScenarios(Map<String, CanonicalScenario> scenarios,
                                          Map<String, CanonicalRequirement> requirements, ValidationResult result) {
        for (CanonicalScenario scenario : scenarios.values()) {
            List<String> links = safe(scenario.getRequirementIds());
            if (links.isEmpty()) {
                result.addError("TestScenario", scenario.getId() + " must link to a requirement");
            }
            for (String requirementId : links) {
                CanonicalRequirement requirement = requirements.get(requirementId);
                if (requirement == null) {
                    result.addError("TestScenario", scenario.getId() + " references missing requirement: " + requirementId);
                } else if (!sharesAnchor(scenario.getSourceAnchors(), requirement.getSourceAnchors())) {
                    result.addError("Traceability", scenario.getId() + " has no sourceAnchor in common with " + requirementId);
                }
            }
        }
    }

    private static void validateTestCases(Map<String, CanonicalTestCase> testCases,
                                          Map<String, CanonicalScenario> scenarios, ValidationResult result) {
        for (CanonicalTestCase testCase : testCases.values()) {
            required(testCase.getExpectedOutcome(), "TestCase", testCase.getId() + ".expectedOutcome", result);
            List<String> links = safe(testCase.getScenarioIds());
            if (links.isEmpty()) {
                result.addError("TestCase", testCase.getId() + " must link to a scenario");
            }
            for (String scenarioId : links) {
                CanonicalScenario scenario = scenarios.get(scenarioId);
                if (scenario == null) {
                    result.addError("TestCase", testCase.getId() + " references missing scenario: " + scenarioId);
                } else if (!sharesAnchor(testCase.getSourceAnchors(), scenario.getSourceAnchors())) {
                    result.addError("Traceability", testCase.getId() + " has no sourceAnchor in common with " + scenarioId);
                }
            }
        }
    }

    private static void validateTestData(Map<String, CanonicalTestData> testData,
                                         Map<String, CanonicalTestCase> testCases, ValidationResult result) {
        for (CanonicalTestData data : testData.values()) {
            required(data.getExpectedValidation(), "TestData", data.getId() + ".expectedValidation", result);
            if (data.getPayload() == null || data.getPayload().isNull()) {
                result.addError("TestData", data.getId() + " payload is required");
            }
            List<String> links = safe(data.getTestCaseIds());
            if (links.isEmpty()) {
                result.addError("TestData", data.getId() + " must link to a test case");
            }
            for (String testCaseId : links) {
                CanonicalTestCase testCase = testCases.get(testCaseId);
                if (testCase == null) {
                    result.addError("TestData", data.getId() + " references missing test case: " + testCaseId);
                } else if (!sharesAnchor(data.getSourceAnchors(), testCase.getSourceAnchors())) {
                    result.addError("Traceability", data.getId() + " has no sourceAnchor in common with " + testCaseId);
                }
            }
        }
    }

    private static void validateOrphans(Map<String, CanonicalRequirement> requirements,
                                        Map<String, CanonicalScenario> scenarios,
                                        Map<String, CanonicalTestCase> testCases,
                                        Map<String, CanonicalTestData> testData, ValidationResult result) {
        Set<String> usedRequirements = scenarios.values().stream()
                .flatMap(value -> safe(value.getRequirementIds()).stream()).collect(Collectors.toSet());
        Set<String> usedScenarios = testCases.values().stream()
                .flatMap(value -> safe(value.getScenarioIds()).stream()).collect(Collectors.toSet());
        Set<String> usedTestCases = testData.values().stream()
                .flatMap(value -> safe(value.getTestCaseIds()).stream()).collect(Collectors.toSet());
        requirements.keySet().stream().filter(id -> !usedRequirements.contains(id))
                .forEach(id -> result.addError("Orphan", "requirement has no linked scenario: " + id));
        scenarios.keySet().stream().filter(id -> !usedScenarios.contains(id))
                .forEach(id -> result.addError("Orphan", "scenario has no linked test case: " + id));
        testCases.keySet().stream().filter(id -> !usedTestCases.contains(id))
                .forEach(id -> result.addError("Orphan", "test case has no linked test data: " + id));
    }

    private static boolean sharesAnchor(List<SourceAnchor> left, List<SourceAnchor> right) {
        Set<String> leftKeys = safe(left).stream().filter(anchor -> anchor != null)
                .map(SourceAnchor::canonicalKey).collect(Collectors.toCollection(HashSet::new));
        return safe(right).stream().filter(anchor -> anchor != null)
                .map(SourceAnchor::canonicalKey).anyMatch(leftKeys::contains);
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? Collections.emptyList() : values;
    }

    private static void required(String value, String stage, String field, ValidationResult result) {
        if (isBlank(value)) {
            result.addError(stage, field + " is required");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}