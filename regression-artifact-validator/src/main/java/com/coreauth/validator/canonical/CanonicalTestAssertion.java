package com.coreauth.validator.canonical;

import java.util.List;

/** One independently checkable expected behavior for one linked business requirement. */
public final class CanonicalTestAssertion {
    private String requirementId;
    private String assertion;
    private String expectedResult;
    private List<SourceAnchor> sourceAnchors;

    public String getRequirementId() { return requirementId; }
    public void setRequirementId(String requirementId) { this.requirementId = requirementId; }
    public String getAssertion() { return assertion; }
    public void setAssertion(String assertion) { this.assertion = assertion; }
    public String getExpectedResult() { return expectedResult; }
    public void setExpectedResult(String expectedResult) { this.expectedResult = expectedResult; }
    public List<SourceAnchor> getSourceAnchors() { return sourceAnchors; }
    public void setSourceAnchors(List<SourceAnchor> sourceAnchors) { this.sourceAnchors = sourceAnchors; }
}