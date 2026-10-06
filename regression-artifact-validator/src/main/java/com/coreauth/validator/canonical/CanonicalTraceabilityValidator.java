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
        return validate(artifactPackage, false);
    }

    public ValidationResult validateForExecution(CanonicalArtifactPackage artifactPackage) {
        return validate(artifactPackage, true);
    }

    private ValidationResult validate(CanonicalArtifactPackage artifactPackage, boolean forExecution) {
        String packageId = artifactPackage == null || artifactPackage.getManifest() == null
                ? "canonical-package" : artifactPackage.getManifest().getPackageId();
        ValidationResult result = new ValidationResult(packageId == null ? "canonical-package" : packageId);
        if (artifactPackage == null) {
            result.addError("Package", "artifact package is required");
            return result;
        }

        validateManifest(artifactPackage.getManifest(), result);
        boolean strict = forExecution || (artifactPackage.getManifest() != null && artifactPackage.getManifest().isStrictExecutionContract());
        Map<String, CanonicalRequirement> requirements = index(artifactPackage.getBusinessRequirements(),
                CanonicalRequirement::getId, CanonicalRequirement::getSourceAnchors, "BusinessRequirement", result);
        Map<String, CanonicalScenario> scenarios = index(artifactPackage.getTestScenarios(),
                CanonicalScenario::getId, CanonicalScenario::getSourceAnchors, "TestScenario", result);
        Map<String, CanonicalTestCase> testCases = index(artifactPackage.getTestCases(),
                CanonicalTestCase::getId, CanonicalTestCase::getSourceAnchors, "TestCase", result);
        Map<String, CanonicalTestData> testData = index(artifactPackage.getTestData(),
                CanonicalTestData::getId, CanonicalTestData::getSourceAnchors, "TestData", result);

        validateAnchorVersions(artifactPackage.getManifest(), requirements, scenarios, testCases, testData, result);
        validateRequirements(requirements, strict, result);
        validateScenarios(scenarios, requirements, strict, result);
        validateTestCases(testCases, scenarios, strict, result);
        validateTestData(testData, testCases, strict, result);
        validateChainAnchorContinuity(requirements, scenarios, testCases, testData, result);
        validateExecutionStatuses(requirements, scenarios, testCases, testData, strict, result);
        if (forExecution) {
            for (CanonicalRequirement requirement : requirements.values()) {
                requireExecutionReady(requirement.getExecutionStatus(), requirement.getId(), result);
            }
            for (CanonicalScenario scenario : scenarios.values()) {
                requireExecutionReady(scenario.getStatus() == null ? null : scenario.getStatus().name(), scenario.getId(), result);
            }
            for (CanonicalTestCase testCase : testCases.values()) {
                requireExecutionReady(testCase.getStatus(), testCase.getId(), result);
            }
            for (CanonicalTestData data : testData.values()) {
                if (data.getReadiness() != TestDataReadiness.EXECUTABLE) {
                    result.addError("ExecutionReadiness", data.getId() + " must have EXECUTABLE readiness");
                }
                if ("REVIEW_REQUIRED".equals(data.getExpectedValidation())) {
                    result.addError("ExecutionReadiness", data.getId() + " has an unresolved expectedValidation");
                }
            }
        }
        validateScheduledAvailability(requirements, scenarios, testCases, result);
        validateRequirementCrosswalk(artifactPackage.getRequirementCrosswalk(), requirements, result);
        validateOrphans(requirements, scenarios, testCases, testData, result);
        return result;
    }

    private static void requireExecutionReady(String status, String id, ValidationResult result) {
        if (!"EXECUTION_READY".equals(status)) {
            result.addError("ExecutionReadiness", id + " must have EXECUTION_READY status, got: " + status);
        }
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

    private static void validateRequirements(Map<String, CanonicalRequirement> requirements,
                                             boolean strict, ValidationResult result) {
        if (!strict) return;
        for (CanonicalRequirement requirement : requirements.values()) {
            required(requirement.getCategory(), "BusinessRequirement", requirement.getId() + ".category", result);
            required(requirement.getApplicability(), "BusinessRequirement", requirement.getId() + ".applicability", result);
            required(requirement.getPriority(), "BusinessRequirement", requirement.getId() + ".priority", result);
            validateStatus(requirement.getExecutionStatus(), "BusinessRequirement", requirement.getId(), result);
        }
    }

    private static void validateScenarios(Map<String, CanonicalScenario> scenarios,
                                          Map<String, CanonicalRequirement> requirements,
                                          boolean strict, ValidationResult result) {
        for (CanonicalScenario scenario : scenarios.values()) {
            if (strict && scenario.getStatus() == null) {
                result.addError("TestScenario", scenario.getId() + ".status is required");
            }
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
                                          Map<String, CanonicalScenario> scenarios,
                                          boolean strict, ValidationResult result) {
        for (CanonicalTestCase testCase : testCases.values()) {
            required(testCase.getExpectedOutcome(), "TestCase", testCase.getId() + ".expectedOutcome", result);
            if (strict) {
                validateStatus(testCase.getStatus(), "TestCase", testCase.getId(), result);
                required(testCase.getTestDataFile(), "TestCase", testCase.getId() + ".testDataFile", result);
            }
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
                                         Map<String, CanonicalTestCase> testCases,
                                         boolean strict, ValidationResult result) {
        for (CanonicalTestData data : testData.values()) {
            required(data.getExpectedValidation(), "TestData", data.getId() + ".expectedValidation", result);
            if (data.getPayload() == null || data.getPayload().isNull()) {
                result.addError("TestData", data.getId() + " payload is required");
            }
            if (strict) {
                required(data.getFileName(), "TestData", data.getId() + ".fileName", result);
                if (data.getReadiness() == null) {
                    result.addError("TestData", data.getId() + ".readiness is required");
                }
                if (data.getPayload() == null || !data.getPayload().isObject()
                        || !data.getPayload().path("request").isObject()) {
                    result.addError("TestData", data.getId() + " must contain an object request envelope");
                }
                if (data.getResponse() == null || !data.getResponse().isObject()) {
                    result.addError("TestData", data.getId() + " must contain an object response envelope");
                }
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

    private static void validateAnchorVersions(PackageManifest manifest,
                                               Map<String, CanonicalRequirement> requirements,
                                               Map<String, CanonicalScenario> scenarios,
                                               Map<String, CanonicalTestCase> testCases,
                                               Map<String, CanonicalTestData> testData,
                                               ValidationResult result) {
        if (manifest == null || isBlank(manifest.getSpecificationVersion())) return;
        String version = manifest.getSpecificationVersion().trim();
        requirements.forEach((id, value) -> validateAnchorVersions(value.getSourceAnchors(), "BusinessRequirement", id, version, result));
        scenarios.forEach((id, value) -> validateAnchorVersions(value.getSourceAnchors(), "TestScenario", id, version, result));
        testCases.forEach((id, value) -> validateAnchorVersions(value.getSourceAnchors(), "TestCase", id, version, result));
        testData.forEach((id, value) -> validateAnchorVersions(value.getSourceAnchors(), "TestData", id, version, result));
    }

    private static void validateAnchorVersions(List<SourceAnchor> anchors, String stage, String id,
                                               String version, ValidationResult result) {
        for (SourceAnchor anchor : safe(anchors)) {
            if (anchor != null && !version.equalsIgnoreCase(anchor.getVersion())) {
                result.addError("Traceability", stage + " " + id + " anchor version " + anchor.getVersion()
                        + " does not match manifest version " + version);
            }
        }
    }

    private static void validateChainAnchorContinuity(Map<String, CanonicalRequirement> requirements,
                                                      Map<String, CanonicalScenario> scenarios,
                                                      Map<String, CanonicalTestCase> testCases,
                                                      Map<String, CanonicalTestData> testData,
                                                      ValidationResult result) {
        for (CanonicalTestData data : testData.values()) {
            for (String testCaseId : safe(data.getTestCaseIds())) {
                CanonicalTestCase testCase = testCases.get(testCaseId);
                if (testCase == null) continue;
                for (String scenarioId : safe(testCase.getScenarioIds())) {
                    CanonicalScenario scenario = scenarios.get(scenarioId);
                    if (scenario == null) continue;
                    for (String requirementId : safe(scenario.getRequirementIds())) {
                        CanonicalRequirement requirement = requirements.get(requirementId);
                        if (requirement != null && !sharesAllAnchors(List.of(data.getSourceAnchors(), testCase.getSourceAnchors(),
                            scenario.getSourceAnchors(), requirement.getSourceAnchors()))) {
                            result.addError("Traceability", data.getId() + " does not preserve one common ATL105 anchor through "
                                    + requirementId + " -> " + scenarioId + " -> " + testCaseId);
                        }
                    }
                }
            }
        }
    }

    private static boolean sharesAllAnchors(List<List<SourceAnchor>> levels) {
        Set<String> common = null;
        for (List<SourceAnchor> level : levels) {
            Set<String> keys = safe(level).stream().filter(anchor -> anchor != null)
                    .map(SourceAnchor::canonicalKey).collect(Collectors.toSet());
            if (common == null) common = new HashSet<>(keys); else common.retainAll(keys);
        }
        return common != null && !common.isEmpty();
    }

    private static void validateExecutionStatuses(Map<String, CanonicalRequirement> requirements,
                                                  Map<String, CanonicalScenario> scenarios,
                                                  Map<String, CanonicalTestCase> testCases,
                                                  Map<String, CanonicalTestData> testData,
                                                  boolean strict, ValidationResult result) {
        if (!strict) return;
        for (CanonicalRequirement requirement : requirements.values()) {
            if ("EXECUTION_READY".equals(requirement.getExecutionStatus())) {
                boolean linked = scenarios.values().stream().anyMatch(s -> safe(s.getRequirementIds()).contains(requirement.getId()));
                if (!linked) result.addError("Status", requirement.getId() + " cannot be EXECUTION_READY without a scenario");
            }
        }
        for (CanonicalTestData data : testData.values()) {
            if (data.getReadiness() == TestDataReadiness.EXECUTABLE
                    && (data.getPayload() == null || !data.getPayload().path("request").isObject()
                    || data.getResponse() == null || !data.getResponse().isObject())) {
                result.addError("Status", data.getId() + " cannot be EXECUTABLE without request and response envelopes");
            }
        }
    }

    private static void validateStatus(String value, String stage, String id, ValidationResult result) {
        required(value, stage, id + ".status", result);
        if (!isBlank(value)) {
            try { ArtifactStatus.valueOf(value); }
            catch (IllegalArgumentException exception) { result.addError(stage, id + " has invalid status: " + value); }
        }
    }

    private static void validateScheduledAvailability(Map<String, CanonicalRequirement> requirements,
                                                      Map<String, CanonicalScenario> scenarios,
                                                      Map<String, CanonicalTestCase> testCases,
                                                      ValidationResult result) {
        requirements.values().forEach(requirement -> validateScheduledAvailability("BusinessRequirement",
                requirement.getId(), requirement.getExecutionStatus(), requirement.getNotBeforeDate(), result));
        scenarios.values().forEach(scenario -> validateScheduledAvailability("TestScenario", scenario.getId(),
                scenario.getStatus() == null ? null : scenario.getStatus().name(), scenario.getNotBeforeDate(), result));
        testCases.values().forEach(testCase -> validateScheduledAvailability("TestCase", testCase.getId(),
                testCase.getStatus(), testCase.getNotBeforeDate(), result));
    }

    private static void validateScheduledAvailability(String stage, String id, String status,
                                                      String notBeforeDate, ValidationResult result) {
        boolean scheduled = "SCHEDULED".equals(status);
        if (scheduled && isBlank(notBeforeDate)) {
            result.addError(stage, id + ".notBeforeDate is required when status is SCHEDULED");
            return;
        }
        if (isBlank(notBeforeDate)) return;
        java.time.LocalDate parsedDate;
        try {
            parsedDate = java.time.LocalDate.parse(notBeforeDate.trim());
        } catch (java.time.format.DateTimeParseException exception) {
            result.addError(stage, id + ".notBeforeDate must be an ISO-8601 date, got: " + notBeforeDate);
            return;
        }
        boolean future = parsedDate.isAfter(java.time.LocalDate.now());
        if (future && "EXECUTION_READY".equals(status)) {
            result.addError(stage, id + " cannot be EXECUTION_READY before notBeforeDate " + notBeforeDate
                    + "; use SCHEDULED until that date");
        }
    }

    private static void validateRequirementCrosswalk(List<RequirementCrosswalkEntry> crosswalk,
                                                     Map<String, CanonicalRequirement> requirements,
                                                     ValidationResult result) {
        if (crosswalk == null) {
            return;
        }
        Set<String> baselineIds = new HashSet<>();
        for (RequirementCrosswalkEntry entry : crosswalk) {
            if (entry == null) {
                result.addError("RequirementCrosswalk", "entry is required");
                continue;
            }
            String baselineId = entry.getTestRequirementId();
            if (isBlank(baselineId)) {
                result.addError("RequirementCrosswalk", "testRequirementId is required");
            } else if (!baselineIds.add(baselineId)) {
                result.addError("RequirementCrosswalk", "duplicate testRequirementId: " + baselineId);
            }
            if (entry.getMatchStatus() == null) {
                result.addError("RequirementCrosswalk", baselineId + ".matchStatus is required");
            }
            required(entry.getMatchReason(), "RequirementCrosswalk", baselineId + ".matchReason", result);
            if (entry.getMatchStatus() == RequirementMatchStatus.REVIEW_REQUIRED) {
                required(entry.getReviewOwner(), "RequirementCrosswalk", baselineId + ".reviewOwner", result);
            }
            List<String> aiIds = safe(entry.getAiRequirementIds());
            if (entry.getMatchStatus() == RequirementMatchStatus.CONFIRMED && aiIds.isEmpty()) {
                result.addError("RequirementCrosswalk", baselineId + " confirmed mapping must reference an AI requirement");
            }
            for (String aiId : aiIds) {
                if (!requirements.containsKey(aiId)) {
                    result.addError("RequirementCrosswalk", baselineId + " references missing AI requirement: " + aiId);
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