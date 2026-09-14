package com.coreauth.validator.traceable;

import com.coreauth.validator.validation.ValidationResult;

/** Checks internal consistency of a {@link TestCase} against the {@link TestScenario} it claims to exercise. */
public final class TestCaseValidator {

    public void validate(TestCase testCase, TestScenario scenario, ValidationResult result) {
        if (testCase == null) {
            result.addError("TestCase", "document has no testCase");
            return;
        }
        if (testCase.getId() == null || testCase.getId().isEmpty()) {
            result.addError("TestCase", "testCase.id is required");
        }
        if (testCase.getExpectedOutcome() == null || testCase.getExpectedOutcome().isEmpty()) {
            result.addError("TestCase", "testCase.expectedOutcome is required");
        }
        if (testCase.getScenarioId() == null || testCase.getScenarioId().isEmpty()) {
            result.addError("TestCase", "testCase.scenarioId is required");
        } else if (scenario != null && !testCase.getScenarioId().equals(scenario.getId())) {
            result.addError("TestCase", "testCase.scenarioId '" + testCase.getScenarioId()
                    + "' does not match testScenario.id '" + scenario.getId() + "'");
        }
    }
}
