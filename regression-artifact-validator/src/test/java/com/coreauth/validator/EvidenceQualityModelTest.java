package com.coreauth.validator;

import com.coreauth.validator.canonical.EvidenceQualityModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EvidenceQualityModelTest {
    private final EvidenceQualityModel model = new EvidenceQualityModel();

    @Test
    void classifiesValidatedAiArtifactAsCovered() {
        var status = model.classify(EvidenceQualityModel.SourceEvidence.SOURCE_CONFIRMED,
                EvidenceQualityModel.ImplementationEvidence.IMPLEMENTED_TESTED,
                EvidenceQualityModel.ArtifactEvidence.AI_ARTIFACT_VALIDATED, false);

        assertThat(status.status()).isEqualTo("COVERED");
    }

    @Test
    void distinguishesIndependentFixtureReadinessFromAiCoverage() {
        var status = model.classify(EvidenceQualityModel.SourceEvidence.SOURCE_CONFIRMED,
                EvidenceQualityModel.ImplementationEvidence.IMPLEMENTED_TESTED,
                EvidenceQualityModel.ArtifactEvidence.INDEPENDENT_FIXTURE_ONLY, false);

        assertThat(status.status()).isEqualTo("READY_FOR_AI_INTAKE");
    }

    @Test
    void requiresReviewForExternalOrConditionalEvidence() {
        var external = model.classify(EvidenceQualityModel.SourceEvidence.EXTERNAL_SPEC_REQUIRED,
                EvidenceQualityModel.ImplementationEvidence.EXTERNAL_IMPLEMENTATION,
                EvidenceQualityModel.ArtifactEvidence.AI_ARTIFACT_PENDING, false);
        var conditional = model.classify(EvidenceQualityModel.SourceEvidence.SOURCE_CONFIRMED,
                EvidenceQualityModel.ImplementationEvidence.IMPLEMENTED_TESTED,
                EvidenceQualityModel.ArtifactEvidence.INDEPENDENT_FIXTURE_ONLY, true);

        assertThat(external.status()).isEqualTo("REVIEW_REQUIRED");
        assertThat(conditional.status()).isEqualTo("REVIEW_REQUIRED");
    }

    @Test
    void blocksUnknownSourceOrMissingArtifact() {
        var unknown = model.classify(EvidenceQualityModel.SourceEvidence.UNKNOWN,
                EvidenceQualityModel.ImplementationEvidence.IMPLEMENTED_TESTED,
                EvidenceQualityModel.ArtifactEvidence.AI_ARTIFACT_PENDING, false);
        var missing = model.classify(EvidenceQualityModel.SourceEvidence.SOURCE_CONFIRMED,
                EvidenceQualityModel.ImplementationEvidence.NOT_IMPLEMENTED,
                EvidenceQualityModel.ArtifactEvidence.NO_ARTIFACT, false);

        assertThat(unknown.status()).isEqualTo("BLOCKED");
        assertThat(missing.status()).isEqualTo("BLOCKED");
    }
}
