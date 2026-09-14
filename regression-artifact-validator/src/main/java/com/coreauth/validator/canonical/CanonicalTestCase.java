package com.coreauth.validator.canonical;

import java.util.List;

public final class CanonicalTestCase {
    private String id;
    private List<String> scenarioIds;
    private List<SourceAnchor> sourceAnchors;
    private String expectedOutcome;
    private String category;
    private List<String> tags;
    private String priority;
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getScenarioIds() { return scenarioIds; }
    public void setScenarioIds(List<String> scenarioIds) { this.scenarioIds = scenarioIds; }
    public List<SourceAnchor> getSourceAnchors() { return sourceAnchors; }
    public void setSourceAnchors(List<SourceAnchor> sourceAnchors) { this.sourceAnchors = sourceAnchors; }
    public String getExpectedOutcome() { return expectedOutcome; }
    public void setExpectedOutcome(String expectedOutcome) { this.expectedOutcome = expectedOutcome; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}