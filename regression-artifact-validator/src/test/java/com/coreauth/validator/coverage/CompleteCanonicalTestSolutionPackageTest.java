package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CompleteCanonicalTestSolutionPackageTest {
    private static final Path PACKAGE = Path.of(
            "specifications", "ATL105", "test-output", "test-solution-independent-review",
            "complete-all-test-solution-br-ts-tc-td-package.json");

    @TempDir Path outputDirectory;

    @Test
    void canonicalPackageHasValidChainsAndReportsReviewOnlyEvidence() throws Exception {
        ValidateAllTestSolutionBrTsTcTdAggregate.main(
                new String[]{PACKAGE.toString(), outputDirectory.toString()});
        JsonNode report = new ObjectMapper().readTree(
                outputDirectory.resolve("all-test-solution-chain-validation.json").toFile());

        assertThat(report.path("inputValid").asBoolean()).isTrue();
        assertThat(report.path("structurallyComplete").asBoolean()).isTrue();
        assertThat(report.path("requirementsWithoutScenario").asInt()).isZero();
        assertThat(report.path("scenariosWithoutTestCase").asInt()).isZero();
        assertThat(report.path("testCasesWithoutData").asInt()).isZero();
        assertThat(report.path("scenarioLinksToMissingRequirements").asInt()).isZero();
        assertThat(report.path("testCaseLinksToMissingScenarios").asInt()).isZero();
        assertThat(report.path("testCaseLinksToMissingTestData").asInt()).isZero();
        assertThat(report.path("testDataLinksToMissingTestCases").asInt()).isZero();
        assertThat(report.path("testCaseDataLinkMismatches").asInt()).isZero();
        assertThat(report.path("duplicateArtifactIds").asInt()).isZero();

        assertThat(report.path("scenariosWithoutRequirement").asInt()).isGreaterThan(0);
        assertThat(report.path("reviewOnlyScenariosWithoutRequirement").asInt())
                .isEqualTo(report.path("scenariosWithoutRequirement").asInt());
        assertThat(report.path("reviewRequiredArtifactCount").asInt()).isGreaterThan(0);
        assertThat(report.path("hasReviewPlaceholders").asBoolean()).isTrue();
        assertThat(report.path("executionReady").asBoolean()).isFalse();
    }
}
