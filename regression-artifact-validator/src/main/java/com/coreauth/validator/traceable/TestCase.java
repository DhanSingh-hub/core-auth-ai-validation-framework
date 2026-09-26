package com.coreauth.validator.traceable;

/** A concrete test built to exercise one test scenario. */
public final class TestCase {
    private String id;
    private String scenarioId;
    private String description;
    private String expectedOutcome;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getScenarioId() { return scenarioId; }
    public void setScenarioId(String scenarioId) { this.scenarioId = scenarioId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getExpectedOutcome() { return expectedOutcome; }
    public void setExpectedOutcome(String expectedOutcome) { this.expectedOutcome = expectedOutcome; }
}
