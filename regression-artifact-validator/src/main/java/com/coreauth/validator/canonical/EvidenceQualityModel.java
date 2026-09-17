package com.coreauth.validator.canonical;

/** Applies the independent evidence-quality policy to one rule. */
public final class EvidenceQualityModel {
    public enum SourceEvidence { SOURCE_CONFIRMED, SOURCE_DERIVED, EXTERNAL_SPEC_REQUIRED, UNKNOWN }
    public enum ImplementationEvidence { IMPLEMENTED_TESTED, IMPLEMENTED_PARTIAL, NOT_IMPLEMENTED, EXTERNAL_IMPLEMENTATION }
    public enum ArtifactEvidence { AI_ARTIFACT_VALIDATED, INDEPENDENT_FIXTURE_ONLY, AI_ARTIFACT_PENDING, NO_ARTIFACT }
    public enum ReviewState { AUTO_APPROVABLE, REVIEW_REQUIRED, BLOCKED }

    public Status classify(SourceEvidence source, ImplementationEvidence implementation,
                           ArtifactEvidence artifact, boolean conditionalContextMissing) {
        if (source == SourceEvidence.UNKNOWN || artifact == ArtifactEvidence.NO_ARTIFACT) {
            return new Status("BLOCKED", ReviewState.BLOCKED, "Missing authoritative evidence");
        }
        if (source == SourceEvidence.EXTERNAL_SPEC_REQUIRED
                || implementation == ImplementationEvidence.EXTERNAL_IMPLEMENTATION
                || conditionalContextMissing) {
            return new Status("REVIEW_REQUIRED", ReviewState.REVIEW_REQUIRED, "External or conditional evidence required");
        }
        if (implementation == ImplementationEvidence.IMPLEMENTED_PARTIAL) {
            return new Status("PARTIALLY_COVERED", ReviewState.REVIEW_REQUIRED, "Implementation evidence is incomplete");
        }
        if (artifact == ArtifactEvidence.AI_ARTIFACT_VALIDATED) {
            return new Status("COVERED", ReviewState.AUTO_APPROVABLE, "Source, implementation, and AI artifact evidence agree");
        }
        return new Status("READY_FOR_AI_INTAKE", ReviewState.AUTO_APPROVABLE, "Independent implementation is ready; AI artifact evidence is pending");
    }

    public record Status(String status, ReviewState reviewState, String rationale) { }
}
