package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Validates that test data is independently traceable and complete.
 *
 * Test data independence means:
 * - Each test datum has canonical source anchors (rules it tests).
 * - Each test datum links to at least one test case.
 * - Payloads contain the actual data needed to validate the rule, not just references.
 * - testControls capture configuration-driven values (e.g., lifecycleKey, expectedOriginalSequence).
 * - Test data can be extracted, distributed, and used outside its containing package.
 */
public final class TestDataIndependenceValidator {

    public ValidationResult validate(CanonicalArtifactPackage artifactPackage) {
        String packageId = packageId(artifactPackage);
        ValidationResult result = new ValidationResult(packageId);

        if (artifactPackage == null || artifactPackage.getTestData() == null) {
            return result;
        }

        for (CanonicalTestData testData : artifactPackage.getTestData()) {
            if (testData == null) {
                continue;
            }
            validateTestDatumIndependence(testData, result);
        }

        return result;
    }

    private static void validateTestDatumIndependence(CanonicalTestData testData, ValidationResult result) {
        String id = testData.getId() == null ? "unknown-test-data" : testData.getId();

        // Check 1: Source anchors must be present
        if (testData.getSourceAnchors() == null || testData.getSourceAnchors().isEmpty()) {
            result.addError("TestData", id + " has no source anchors; cannot trace to rules");
            return;
        }

        // Check 2: At least one test case must be linked
        if (testData.getTestCaseIds() == null || testData.getTestCaseIds().isEmpty()) {
            result.addError("TestData", id + " is not linked to any test case");
        }

        // Check 3: Expected validation must be explicit
        if (testData.getExpectedValidation() == null || testData.getExpectedValidation().isEmpty()) {
            result.addError("TestData", id + " does not declare expectedValidation (PASS/FAIL/REVIEW)");
        } else if (!isValidExpectation(testData.getExpectedValidation())) {
            result.addError("TestData", id + " has invalid expectedValidation: "
                    + testData.getExpectedValidation() + " (must be PASS, FAIL, or REVIEW)");
        }

        // Check 4: Payload must exist and be non-empty
        if (testData.getPayload() == null) {
            result.addError("TestData", id + " has no payload; cannot exercise the rule");
        } else if (testData.getPayload().isMissingNode() || testData.getPayload().isEmpty()) {
            result.addError("TestData", id + " has an empty payload");
        }

        // Check 5: Canonical anchors must be consistent
        for (SourceAnchor anchor : testData.getSourceAnchors()) {
            if (anchor == null) {
                result.addError("TestData", id + " has null source anchor");
                continue;
            }
            if (anchor.getSpecification() == null || anchor.getSpecification().isEmpty()) {
                result.addError("TestData", id + " anchor lacks specification");
            }
            if (anchor.getRule() == null || anchor.getRule().isEmpty()) {
                result.addError("TestData", id + " anchor lacks rule identifier");
            }
        }
    }

    private static boolean isValidExpectation(String expectation) {
        return "PASS".equalsIgnoreCase(expectation)
                || "FAIL".equalsIgnoreCase(expectation)
                || "REVIEW".equalsIgnoreCase(expectation)
                || "PASS_WITH_REVIEW".equalsIgnoreCase(expectation);
    }

    private static String packageId(CanonicalArtifactPackage pkg) {
        if (pkg == null || pkg.getManifest() == null) {
            return "test-data-independence";
        }
        String id = pkg.getManifest().getPackageId();
        return id == null ? "test-data-independence" : id;
    }
}
