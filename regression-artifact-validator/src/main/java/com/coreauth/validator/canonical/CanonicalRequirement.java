package com.coreauth.validator.canonical;

import java.util.List;

public final class CanonicalRequirement {
    private String id;
    private String title;
    private String category;
    private String applicability;
    private String priority;
    private String executionStatus;
    private String notBeforeDate;
    private List<SourceAnchor> sourceAnchors;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getApplicability() { return applicability; }
    public void setApplicability(String applicability) { this.applicability = applicability; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getExecutionStatus() { return executionStatus; }
    public void setExecutionStatus(String executionStatus) { this.executionStatus = executionStatus; }
    public String getNotBeforeDate() { return notBeforeDate; }
    public void setNotBeforeDate(String notBeforeDate) { this.notBeforeDate = notBeforeDate; }
    public List<SourceAnchor> getSourceAnchors() { return sourceAnchors; }
    public void setSourceAnchors(List<SourceAnchor> sourceAnchors) { this.sourceAnchors = sourceAnchors; }
}