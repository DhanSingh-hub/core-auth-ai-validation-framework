package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AggregateReadinessGateTest {
    @TempDir Path directory;

    @Test
    void linkCompleteAggregateDoesNotCertifyExecution() throws Exception {
        Path input = directory.resolve("chain.json");
        Files.writeString(input, """
            {"businessRequirements":[{"id":"BR-1"}],
             "testScenarios":[{"id":"TS-1","requirementIds":["BR-1"]}],
             "testCases":[{"id":"TC-1","scenarioIds":["TS-1"],"testDataIds":["TD-1"]}],
             "testData":[{"id":"TD-1","testCaseIds":["TC-1"]}]}
            """);
        ValidateAllTestSolutionBrTsTcTdAggregate.main(new String[]{input.toString(), directory.toString()});
        JsonNode result = new ObjectMapper().readTree(directory.resolve("all-test-solution-chain-validation.json").toFile());
        assertThat(result.path("structurallyComplete").asBoolean()).isTrue();
        assertThat(result.path("inputValid").asBoolean()).isTrue();
        assertThat(result.path("hasReviewPlaceholders").asBoolean()).isFalse();
        assertThat(result.path("reviewRequiredArtifactCount").asInt()).isZero();
        assertThat(result.path("executionReady").asBoolean()).isFalse();
        assertThat(result.path("executionReadinessReason").asText()).contains("canonical anchor continuity");
    }

    @Test
    void danglingReferencesMakeAggregateStructurallyIncomplete() throws Exception {
        Path input = directory.resolve("dangling-chain.json");
        Files.writeString(input, """
            {"businessRequirements":[{"id":"BR-1"}],
             "testScenarios":[{"id":"TS-1","requirementIds":["BR-MISSING"]}],
             "testCases":[{"id":"TC-1","scenarioIds":["TS-MISSING"],"testDataIds":["TD-MISSING"]}],
             "testData":[{"id":"TD-1","testCaseIds":["TC-MISSING"]}]}
            """);

        ValidateAllTestSolutionBrTsTcTdAggregate.main(new String[]{input.toString(), directory.toString()});
        JsonNode result = new ObjectMapper().readTree(directory.resolve("all-test-solution-chain-validation.json").toFile());

        assertThat(result.path("scenarioLinksToMissingRequirements").asInt()).isEqualTo(1);
        assertThat(result.path("testCaseLinksToMissingScenarios").asInt()).isEqualTo(1);
        assertThat(result.path("testCaseLinksToMissingTestData").asInt()).isEqualTo(1);
        assertThat(result.path("testDataLinksToMissingTestCases").asInt()).isEqualTo(1);
        assertThat(result.path("structurallyComplete").asBoolean()).isFalse();
    }

    @Test
    void emptyCollectionsAreNotReportedAsACompleteChain() throws Exception {
        Path input = directory.resolve("empty-chain.json");
        Files.writeString(input, """
            {"businessRequirements":[],"testScenarios":[],"testCases":[],"testData":[]}
            """);

        ValidateAllTestSolutionBrTsTcTdAggregate.main(new String[]{input.toString(), directory.toString()});
        JsonNode result = new ObjectMapper().readTree(directory.resolve("all-test-solution-chain-validation.json").toFile());

        assertThat(result.path("inputValid").asBoolean()).isFalse();
        assertThat(result.path("inputErrors").isArray()).isTrue();
        assertThat(result.path("inputErrors").size()).isEqualTo(4);
        assertThat(result.path("structurallyComplete").asBoolean()).isFalse();
    }

    @Test
    void reportsReviewRequiredArtifactsByChainLevel() throws Exception {
        Path input = directory.resolve("review-required-chain.json");
        Files.writeString(input, """
            {"businessRequirements":[{"id":"BR-1","status":"DRAFT_REVIEW_REQUIRED"}],
             "testScenarios":[{"id":"TS-1","requirementIds":["BR-1"],"status":"REVIEW_REQUIRED"}],
             "testCases":[{"id":"TC-1","scenarioIds":["TS-1"],"testDataIds":["TD-1"],
                           "status":"REVIEW_REQUIRED"}],
             "testData":[{"id":"TD-1","testCaseIds":["TC-1"],"readiness":"REVIEW_REQUIRED"}]}
            """);

        ValidateAllTestSolutionBrTsTcTdAggregate.main(new String[]{input.toString(), directory.toString()});
        JsonNode result = new ObjectMapper().readTree(directory.resolve("all-test-solution-chain-validation.json").toFile());

        assertThat(result.path("reviewRequiredArtifactCount").asInt()).isEqualTo(4);
        assertThat(result.path("reviewRequiredArtifactsByType").path("businessRequirements").asInt()).isEqualTo(1);
        assertThat(result.path("reviewRequiredArtifactsByType").path("testScenarios").asInt()).isEqualTo(1);
        assertThat(result.path("reviewRequiredArtifactsByType").path("testCases").asInt()).isEqualTo(1);
        assertThat(result.path("reviewRequiredArtifactsByType").path("testData").asInt()).isEqualTo(1);
        assertThat(result.path("artifactStatusCountsByType").path("testData")
                .path("readiness").path("REVIEW_REQUIRED").asInt())
                .isEqualTo(1);
        assertThat(result.path("hasReviewPlaceholders").asBoolean()).isTrue();
    }

    @Test
    void reviewOnlyScenariosWithoutRequirementsAreReportedSeparately() throws Exception {
        Path input = directory.resolve("review-only-scenario.json");
        Files.writeString(input, """
            {"businessRequirements":[{"id":"BR-1"}],
             "testScenarios":[{"id":"TS-1","requirementIds":["BR-1"]},
                              {"id":"TS-REVIEW","requirementIds":[],"status":"REVIEW_REQUIRED"}],
             "testCases":[{"id":"TC-1","scenarioIds":["TS-1"],"testDataIds":["TD-1"]},
                          {"id":"TC-REVIEW","scenarioIds":["TS-REVIEW"],"testDataIds":["TD-REVIEW"]}],
             "testData":[{"id":"TD-1","testCaseIds":["TC-1"]},
                         {"id":"TD-REVIEW","testCaseIds":["TC-REVIEW"]}]}
            """);

        ValidateAllTestSolutionBrTsTcTdAggregate.main(new String[]{input.toString(), directory.toString()});
        JsonNode result = new ObjectMapper().readTree(directory.resolve("all-test-solution-chain-validation.json").toFile());

        assertThat(result.path("inputValid").asBoolean()).isTrue();
        assertThat(result.path("scenariosWithoutRequirement").asInt()).isEqualTo(1);
        assertThat(result.path("reviewOnlyScenariosWithoutRequirement").asInt()).isEqualTo(1);
        assertThat(result.path("structurallyComplete").asBoolean()).isTrue();
    }

    @Test
    void inconsistentCaseAndDataLinksMakeAggregateIncomplete() throws Exception {
        Path input = directory.resolve("mismatched-links.json");
        Files.writeString(input, """
            {"businessRequirements":[{"id":"BR-1"}],
             "testScenarios":[{"id":"TS-1","requirementIds":["BR-1"]}],
             "testCases":[{"id":"TC-1","scenarioIds":["TS-1"],"testDataIds":["TD-1"]}],
             "testData":[{"id":"TD-1","testCaseIds":["TC-1"]},
                         {"id":"TD-2","testCaseIds":["TC-1"]}]}
            """);

        ValidateAllTestSolutionBrTsTcTdAggregate.main(new String[]{input.toString(), directory.toString()});
        JsonNode result = new ObjectMapper().readTree(directory.resolve("all-test-solution-chain-validation.json").toFile());

        assertThat(result.path("testCaseDataLinkMismatches").asInt()).isEqualTo(1);
        assertThat(result.path("structurallyComplete").asBoolean()).isFalse();
    }
}