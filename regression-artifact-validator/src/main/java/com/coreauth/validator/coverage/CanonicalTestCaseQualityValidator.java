package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.validation.ValidationResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Checks whether canonical test cases are specific, linked, anchored, and backed by test data. */
public final class CanonicalTestCaseQualityValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        ValidationResult result = new ValidationResult("canonical-test-case-quality");
        if (artifactPackage == null || artifactPackage.getTestCases() == null) {
            result.addError("TestCaseQuality", "test cases are required");
            return result;
        }
        Set<String> testCasesWithData = linkedTestCaseIds(artifactPackage.getTestData());
        for (CanonicalTestCase testCase : artifactPackage.getTestCases()) {
            if (testCase == null || blank(testCase.getId())) {
                result.addError("TestCaseQuality", "testCase.id is required");
                continue;
            }
            if (blank(testCase.getExpectedOutcome())) {
                result.addError("TestCaseQuality", testCase.getId() + ".expectedOutcome is required");
            }
            if (testCase.getScenarioIds() == null || testCase.getScenarioIds().isEmpty()) {
                result.addError("TestCaseQuality", testCase.getId() + " must link to a scenario");
            }
            if (testCase.getSourceAnchors() == null || testCase.getSourceAnchors().isEmpty()) {
                result.addError("TestCaseQuality", testCase.getId() + " must contain a sourceAnchor");
            }
            if (blank(testCase.getTestDataFile())) {
                result.addError("TestCaseQuality", testCase.getId() + ".testDataFile is required");
            }
            if (!testCasesWithData.contains(testCase.getId())) {
                result.addError("TestCaseQuality", testCase.getId() + " has no linked test-data artifact");
            }
        }
        return result;
    }

    private static Set<String> linkedTestCaseIds(List<CanonicalTestData> testData) {
        Set<String> result = new HashSet<>();
        if (testData != null) {
            testData.stream().filter(data -> data != null && data.getTestCaseIds() != null)
                    .flatMap(data -> data.getTestCaseIds().stream()).forEach(result::add);
        }
        return result;
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }
}