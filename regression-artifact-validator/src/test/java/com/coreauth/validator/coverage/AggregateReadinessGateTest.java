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
             "testCases":[{"id":"TC-1","scenarioIds":["TS-1"]}],
             "testData":[{"id":"TD-1","testCaseIds":["TC-1"]}]}
            """);
        ValidateAllTestSolutionBrTsTcTdAggregate.main(new String[]{input.toString(), directory.toString()});
        JsonNode result = new ObjectMapper().readTree(directory.resolve("all-test-solution-chain-validation.json").toFile());
        assertThat(result.path("structurallyComplete").asBoolean()).isTrue();
        assertThat(result.path("hasReviewPlaceholders").asBoolean()).isFalse();
        assertThat(result.path("executionReady").asBoolean()).isFalse();
        assertThat(result.path("executionReadinessReason").asText()).contains("canonical anchor continuity");
    }
}