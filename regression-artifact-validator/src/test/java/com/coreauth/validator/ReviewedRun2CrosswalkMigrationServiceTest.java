package com.coreauth.validator;

import com.coreauth.validator.canonical.RequirementMatchStatus;
import com.coreauth.validator.coverage.ReviewedRun2CrosswalkMigrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewedRun2CrosswalkMigrationServiceTest {

    @Test
    void migratesOnlyExactCanonicalEvidenceAndCompletesDenominator(@TempDir Path root) throws Exception {
        Path catalog = root.resolve("catalog.json");
        Files.writeString(catalog, """
                {"rules":[
                  {"ruleId":"SEG105-R-001","title":"Segment Type","sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"12","segment":"105","element":"85","rule":"segment-type"}},
                  {"ruleId":"SEG105-R-002","title":"Segment Length","sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"12","segment":"105","element":"84","rule":"segment-length"}},
                  {"ruleId":"SEG105-R-003","title":"Information Byte","sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"12","segment":"105","element":"44","rule":"information-byte"}}
                ]}
                """);
        Path baselineRoot = Files.createDirectories(root.resolve("baseline"));
        Files.writeString(baselineRoot.resolve("package.json"), """
                {"businessRequirements":[
                  {"id":"BR-CUSTOM","sourceAnchors":[{"specification":"ATL105","version":"2026-3","section":"12","segment":"105","element":"85","rule":"segment-type"}]}
                ]}
                """);
        Path prior = root.resolve("prior.json");
        Files.writeString(prior, """
                {"aiToTestSolution":[
                  {"aiRequirementId":"AI-1","testRequirementId":"BR-CUSTOM","testMatchStatus":"MATCHED_ELEMENT_AND_SEMANTICS"},
                  {"aiRequirementId":"AI-2","testRequirementId":"BR-SEG105-002","testMatchStatus":"POTENTIAL_MATCH_REVIEW_REQUIRED"},
                  {"aiRequirementId":"AI-3","testRequirementId":"BR-UNKNOWN","testMatchStatus":"MATCHED_ELEMENT_AND_SEMANTICS"}
                ]}
                """);

        var result = new ReviewedRun2CrosswalkMigrationService().migrate(
                "105", catalog, prior, baselineRoot, "ATL105-SME-105");

        assertThat(result.denominator()).isEqualTo(3);
        assertThat(result.confirmed()).isEqualTo(1);
        assertThat(result.reviewRequired()).isEqualTo(1);
        assertThat(result.missing()).isEqualTo(1);
        assertThat(result.unresolvedPriorConfirmedAiRequirementIds()).containsExactly("AI-3");
        assertThat(result.crosswalk().mappings()).extracting(mapping -> mapping.matchStatus())
                .containsExactly(RequirementMatchStatus.CONFIRMED,
                        RequirementMatchStatus.REVIEW_REQUIRED, RequirementMatchStatus.MISSING);
        assertThat(result.crosswalk().mappings().get(1).reviewOwner()).isEqualTo("ATL105-SME-105");
    }
}