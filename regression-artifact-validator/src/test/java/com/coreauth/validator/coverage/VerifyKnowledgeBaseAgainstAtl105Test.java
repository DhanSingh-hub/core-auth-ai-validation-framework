package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VerifyKnowledgeBaseAgainstAtl105Test {
    @Test
    void allCatalogAnchorsResolveStructurallyAgainstAtl105() throws Exception {
        VerifyKnowledgeBaseAgainstAtl105.main(new String[0]);
        JsonNode report = new ObjectMapper().readTree(Atl105Paths.testOutput().resolve(
                "ai-solution-independent-review/knowledge-base-atl105-verification.json").toFile());

        assertThat(report.path("catalogCount").asInt()).isEqualTo(49);
        assertThat(report.path("ruleCount").asInt()).isEqualTo(601);
        assertThat(report.path("incompleteAnchors")).isEmpty();
        assertThat(report.path("duplicateAnchors")).isEmpty();
        assertThat(report.path("unsupportedVersionFiles")).isEmpty();
        assertThat(report.path("missingSectionEvidence")).isEmpty();
        assertThat(report.path("sourceSectionEvidenceVerified").asBoolean()).isTrue();
        assertThat(report.path("catalogsValidStructurally").asBoolean()).isTrue();
        assertThat(report.path("sourceEvidenceRequiresManualSemanticReview").asBoolean()).isTrue();
    }
}