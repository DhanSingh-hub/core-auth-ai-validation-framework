package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public final class CanonicalTestData {
    private String id;
    private List<String> testCaseIds;
    private List<SourceAnchor> sourceAnchors;
    private String expectedValidation;
    private JsonNode payload;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getTestCaseIds() { return testCaseIds; }
    public void setTestCaseIds(List<String> testCaseIds) { this.testCaseIds = testCaseIds; }
    public List<SourceAnchor> getSourceAnchors() { return sourceAnchors; }
    public void setSourceAnchors(List<SourceAnchor> sourceAnchors) { this.sourceAnchors = sourceAnchors; }
    public String getExpectedValidation() { return expectedValidation; }
    public void setExpectedValidation(String expectedValidation) { this.expectedValidation = expectedValidation; }
    public JsonNode getPayload() { return payload; }
    public void setPayload(JsonNode payload) { this.payload = payload; }
}