package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public final class CanonicalTestData {
    private String id;
    private List<String> testCaseIds;
    private List<SourceAnchor> sourceAnchors;
    private String expectedValidation;
    private String fileName;
    private TestDataReadiness readiness;
    private JsonNode payload;
    private JsonNode response;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getTestCaseIds() { return testCaseIds; }
    public void setTestCaseIds(List<String> testCaseIds) { this.testCaseIds = testCaseIds; }
    public List<SourceAnchor> getSourceAnchors() { return sourceAnchors; }
    public void setSourceAnchors(List<SourceAnchor> sourceAnchors) { this.sourceAnchors = sourceAnchors; }
    public String getExpectedValidation() { return expectedValidation; }
    public void setExpectedValidation(String expectedValidation) { this.expectedValidation = expectedValidation; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public TestDataReadiness getReadiness() { return readiness; }
    public void setReadiness(TestDataReadiness readiness) { this.readiness = readiness; }
    public JsonNode getPayload() { return payload; }
    public void setPayload(JsonNode payload) { this.payload = payload; }
    public JsonNode getResponse() { return response; }
    public void setResponse(JsonNode response) { this.response = response; }
}