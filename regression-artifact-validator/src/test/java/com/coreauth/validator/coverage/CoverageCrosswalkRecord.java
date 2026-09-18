package com.coreauth.validator.coverage;

/** A single AI-to-Test-Solution crosswalk entry backing the Coverage Mapping Ratio calculation. */
public class CoverageCrosswalkRecord {

    private final String matchStatus;
    private final String reviewOwner;
    private boolean hasScenario = true;

    public CoverageCrosswalkRecord(String matchStatus, String reviewOwner) {
        this.matchStatus = matchStatus;
        this.reviewOwner = reviewOwner;
    }

    public String matchStatus() {
        return matchStatus;
    }

    public boolean hasScenario() {
        return hasScenario;
    }

    public void markMissingScenario() {
        this.hasScenario = false;
    }

    /** A REVIEW_REQUIRED record without a reviewOwner cannot be trusted for coverage. */
    public boolean isValid() {
        return !"REVIEW_REQUIRED".equals(matchStatus) || reviewOwner != null;
    }
}
