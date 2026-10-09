package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105ElementChainCoverage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105ElementChainCoverageTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @TempDir Path temporaryPack;

    @Test
    void usableBranchIsNotBlockedByAnotherPlaceholderBranch() throws Exception {
        Path directory = temporaryPack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(directory);
        Files.writeString(directory.resolve("atl105-all-elements-reference.json"), """
            {"elements":[{"element":"1","sourceNames":["Example"],"businessRules":[{"ruleId":"SEG1-R-001"}]}]}
            """);
        Files.writeString(directory.resolve("complete-all-test-solution-br-ts-tc-td-package.json"), """
            {"businessRequirements":[{"id":"BR-RULE-SEG1-R-001","status":"APPROVED"}],
             "testScenarios":[{"id":"TS-REVIEW-1","requirementIds":["BR-RULE-SEG1-R-001"],"status":"REVIEW_REQUIRED"},
                              {"id":"TS-1","requirementIds":["BR-RULE-SEG1-R-001"],"status":"APPROVED"}],
             "testCases":[{"id":"TC-REVIEW-1","scenarioIds":["TS-REVIEW-1"]},
                          {"id":"TC-1","scenarioIds":["TS-1"],"status":"APPROVED"}],
             "testData":[{"id":"TD-REVIEW-1","testCaseIds":["TC-REVIEW-1"]},
                         {"id":"TD-1","testCaseIds":["TC-1"],"status":"APPROVED","payload":{"segment":"1"}}]}
            """);
        GenerateAtl105ElementChainCoverage.main(new String[]{temporaryPack.toString()});
        JsonNode coverage = MAPPER.readTree(directory.resolve("atl105-element-chain-coverage.json").toFile());
        assertThat(coverage.path("structuralCompleteRules").asInt()).isEqualTo(1);
        assertThat(coverage.path("executableCompleteRules").asInt()).isEqualTo(0);
    }

    @Test
    void separatesStructuralPlaceholderChainsFromExecutableCoverage() throws Exception {
        Path inputs = PACK.resolve("test-output/test-solution-independent-review");
        Path outputs = temporaryPack.resolve("test-output/test-solution-independent-review");
        Files.createDirectories(outputs);
        Files.copy(inputs.resolve("atl105-all-elements-reference.json"), outputs.resolve("atl105-all-elements-reference.json"));
        Files.copy(inputs.resolve("complete-all-test-solution-br-ts-tc-td-package.json"), outputs.resolve("complete-all-test-solution-br-ts-tc-td-package.json"));
        GenerateAtl105ElementChainCoverage.main(new String[]{temporaryPack.toString()});
        Path output = outputs.resolve("atl105-element-chain-coverage.json");
        JsonNode coverage = MAPPER.readTree(output.toFile());
        assertThat(coverage.path("elementCount").asInt()).isEqualTo(231);
        assertThat(coverage.path("oracleRulesWithElementAnchors").asInt()).isGreaterThan(0);
        assertThat(coverage.path("structuralCompleteRules").asInt()).isGreaterThanOrEqualTo(coverage.path("executableCompleteRules").asInt());
        assertThat(coverage.path("placeholderOrReviewRules").asInt()).isGreaterThan(0);
        assertThat(coverage.path("oracleRulesWithElementAnchors").asInt()).isEqualTo(554);
        assertThat(coverage.path("structuralCompleteRules").asInt()).isEqualTo(554);
        assertThat(coverage.path("executionReady").isMissingNode()).isTrue();
        assertThat(coverage.path("status").asText()).isEqualTo("REVIEW_REQUIRED_NOT_EXECUTION_CERTIFIED");
    }
}
