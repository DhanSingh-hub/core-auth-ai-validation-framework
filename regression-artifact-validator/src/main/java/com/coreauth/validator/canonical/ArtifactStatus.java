package com.coreauth.validator.canonical;

public enum ArtifactStatus {
    DRAFT,
    REVIEW_REQUIRED,
    APPROVED_FOR_REVIEW,
    EXECUTION_READY,
    BLOCKED,
    /** Correctly specified and certifiable, but not live before {@code notBeforeDate}; distinct from BLOCKED. */
    SCHEDULED
}