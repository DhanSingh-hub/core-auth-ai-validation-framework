package com.coreauth.validator.canonical;

import java.util.List;

public final class CanonicalScenario {
    private String id;
    private List<String> requirementIds;
    private List<SourceAnchor> sourceAnchors;
    private ArtifactStatus status;
    private String notBeforeDate;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getRequirementIds() { return requirementIds; }
    public void setRequirementIds(List<String> requirementIds) { this.requirementIds = requirementIds; }
    public List<SourceAnchor> getSourceAnchors() { return sourceAnchors; }
    public void setSourceAnchors(List<SourceAnchor> sourceAnchors) { this.sourceAnchors = sourceAnchors; }
    public ArtifactStatus getStatus() { return status; }
    public void setStatus(ArtifactStatus status) { this.status = status; }
    public String getNotBeforeDate() { return notBeforeDate; }
    public void setNotBeforeDate(String notBeforeDate) { this.notBeforeDate = notBeforeDate; }
}