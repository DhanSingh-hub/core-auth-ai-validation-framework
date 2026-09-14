package com.coreauth.validator.traceable;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * One file = one Test Case: the full traceability chain from specification rule, through the
 * business scenario and the test case built for it, down to the JSON test data that exercises it.
 */
public final class TraceableTestCase {
    private SpecReference specReference;
    private TestScenario testScenario;
    private TestCase testCase;
    private JsonNode testData;

    public SpecReference getSpecReference() { return specReference; }
    public void setSpecReference(SpecReference specReference) { this.specReference = specReference; }
    public TestScenario getTestScenario() { return testScenario; }
    public void setTestScenario(TestScenario testScenario) { this.testScenario = testScenario; }
    public TestCase getTestCase() { return testCase; }
    public void setTestCase(TestCase testCase) { this.testCase = testCase; }
    public JsonNode getTestData() { return testData; }
    public void setTestData(JsonNode testData) { this.testData = testData; }
}
