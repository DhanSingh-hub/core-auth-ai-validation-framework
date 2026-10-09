package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.canonical.PartialApprovalValidator;
import com.coreauth.validator.canonical.Segment100PayloadValidator;
import com.coreauth.validator.canonical.TestDataReadiness;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Section102DebitFixtureEvidenceTest {
    private static final Path PACKAGE = Path.of("specifications", "ATL105", "test-output",
            "test-solution-independent-review", "section-10-2-debit-fixture-oracle-candidates-v1",
            "candidate-test-solution-package.json");

    @Test
    void partialApprovalFragmentPassesOnlyDeclaredCoreAndIndicatorChecks() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(PACKAGE);

        assertThat(new CanonicalTraceabilityValidator().validate(artifactPackage).errors()).isEmpty();
        assertThat(new Segment100PayloadValidator().validate(artifactPackage).errors()).isEmpty();
        assertThat(new PartialApprovalValidator().validate(artifactPackage).errors()).isEmpty();
        assertThat(artifactPackage.getTestData()).hasSize(1);
        assertThat(artifactPackage.getTestData().get(0).getReadiness()).isEqualTo(TestDataReadiness.REVIEW_REQUIRED);
        assertThat(new CanonicalTraceabilityValidator().validateForExecution(artifactPackage).errors()).isNotEmpty();
    }

    @Test
    void invalidPartialApprovalIndicatorIsDetectedByTheNarrowValidator() throws Exception {
        CanonicalArtifactPackage artifactPackage = new CanonicalPackageLoader().load(PACKAGE);
        JsonNode payload = artifactPackage.getTestData().get(0).getPayload();
        ((ObjectNode) payload.at("/request/dataSection2/standardSegment")).put("partialApprovalIndicator", "9");

        assertThat(new PartialApprovalValidator().validate(artifactPackage).errors()).isNotEmpty();
    }
}