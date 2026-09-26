package com.coreauth.validator.canonical;

import java.util.List;

/** Explicit producer-to-baseline mapping; unresolved SME decisions remain review-required. */
public final class RequirementCrosswalkEntry {
    private String testRequirementId;
    private List<String> aiRequirementIds;
    private RequirementMatchStatus matchStatus;
    private String matchReason;
    private String reviewOwner;

    public String getTestRequirementId() { return testRequirementId; }
    public void setTestRequirementId(String testRequirementId) { this.testRequirementId = testRequirementId; }
    public List<String> getAiRequirementIds() { return aiRequirementIds; }
    public void setAiRequirementIds(List<String> aiRequirementIds) { this.aiRequirementIds = aiRequirementIds; }
    public RequirementMatchStatus getMatchStatus() { return matchStatus; }
    public void setMatchStatus(RequirementMatchStatus matchStatus) { this.matchStatus = matchStatus; }
    public String getMatchReason() { return matchReason; }
    public void setMatchReason(String matchReason) { this.matchReason = matchReason; }
    public String getReviewOwner() { return reviewOwner; }
    public void setReviewOwner(String reviewOwner) { this.reviewOwner = reviewOwner; }
}