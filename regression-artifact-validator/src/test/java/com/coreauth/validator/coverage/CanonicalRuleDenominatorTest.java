package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CanonicalRuleDenominatorTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void consolidatedModelRetainsEveryFocusedExecutionArtifact() throws Exception {
        JsonNode focused = mapper.readTree(Atl105Paths.testJson()
                .resolve(GenerateSegment100AccountEntryPackage.FILE_NAME).toFile());
        JsonNode published = mapper.readTree(Atl105Paths.testOutput().resolve(
                "test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json").toFile());
        for (String level : List.of("businessRequirements", "testScenarios", "testCases", "testData")) {
            var byId = StreamSupport.stream(published.path(level).spliterator(), false)
                    .collect(Collectors.toMap(row -> row.path("id").asText(), row -> row));
            for (JsonNode expected : focused.path(level)) {
                assertThat(byId.get(expected.path("id").asText())).isEqualTo(expected);
            }
        }
        assertThat(published.path("summary").path("executionReadyElement2And12Requirements").asInt()).isEqualTo(13);
        assertThat(published.path("summary").path("executionReadyElement2And12TestCases").asInt()).isEqualTo(42);
        assertThat(published.path("summary").path("executionReady").asBoolean()).isFalse();
    }

    @Test
    void executionMergeIsIdempotentAndRejectsConflictingRecords() throws Exception {
        List<JsonNode> target = new java.util.ArrayList<>(List.of(mapper.readTree("{\"id\":\"TC-LOCAL\",\"status\":\"EXECUTION_READY\"}")));
        JsonNode same = mapper.readTree("[{\"id\":\"TC-LOCAL\",\"status\":\"EXECUTION_READY\"}]");
        GenerateCompleteCanonicalTestSolutionPackage.mergeExecutionArtifacts(target, same);
        assertThat(target).hasSize(1);
        JsonNode conflict = mapper.readTree("[{\"id\":\"TC-LOCAL\",\"status\":\"REVIEW_REQUIRED\"}]");
        assertThatThrownBy(() -> GenerateCompleteCanonicalTestSolutionPackage.mergeExecutionArtifacts(target, conflict))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("Conflicting execution artifact");
        assertThat(target).hasSize(1);
    }

    @Test
    void publishedRuleDerivedRequirementsMatchGovernedDenominator() throws Exception {
        JsonNode training = mapper.readTree(Atl105Paths.root().resolve("training-status.json").toFile());
        JsonNode packageRoot = mapper.readTree(Atl105Paths.testOutput().resolve(
            "test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json").toFile());
        int ruleCount = training.path("summary").path("totalCataloguedRules").asInt();
        Set<String> ruleIds = StreamSupport.stream(packageRoot.path("businessRequirements").spliterator(), false)
            .map(requirement -> requirement.path("id").asText())
            .filter(id -> id.startsWith("BR-RULE-"))
            .collect(Collectors.toSet());
        List<String> allRequirementIds = StreamSupport.stream(packageRoot.path("businessRequirements").spliterator(), false)
            .map(requirement -> requirement.path("id").asText())
            .toList();
        assertThat(packageRoot.path("summary").path("independentRuleDenominator").asInt()).isEqualTo(ruleCount);
        assertThat(ruleIds).hasSize(ruleCount);
        assertThat(allRequirementIds).doesNotHaveDuplicates();
        assertThat(packageRoot.path("summary").path("executionReady").asBoolean()).isFalse();
    }

    @Test
    void everyMissingScenarioIsAReviewOnlySourceAnchoredProposal() throws Exception {
        JsonNode packageRoot = mapper.readTree(Atl105Paths.testOutput().resolve(
            "test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json").toFile());
        List<JsonNode> proposals = StreamSupport.stream(packageRoot.path("testScenarios").spliterator(), false)
            .filter(scenario -> scenario.path("scenarioDefinitionStatus").asText()
                .startsWith("DRAFT_FROM_TEST_BR_TITLE_AND_SOURCE_ANCHOR"))
            .toList();

        assertThat(proposals).hasSize(packageRoot.path("summary").path("reviewRequiredScenarioProposals").asInt());
        assertThat(proposals).isNotEmpty().allSatisfy(scenario -> {
            assertThat(scenario.path("intent").asText()).isNotBlank();
            assertThat(scenario.path("requirementIds")).isNotEmpty();
            assertThat(scenario.path("sourceAnchors")).isNotEmpty();
            assertThat(scenario.path("scenarioDefinitionGaps")).hasSize(4);
            assertThat(scenario.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(scenario.has("preconditions")).isFalse();
            assertThat(scenario.has("expectedOutcome")).isFalse();
        });
        assertThat(packageRoot.path("summary").path("executionReady").asBoolean()).isFalse();
    }

    @Test
    void rulesWithSharedAnchorsKeepSeparateBusinessRequirements() throws Exception {
        JsonNode rules = mapper.readTree("""
            [{"ruleId":"SEG1-R-001","sourceAnchor":{"section":"11.1","rule":"shared"}},
             {"ruleId":"SEG1-R-002","sourceAnchor":{"section":"11.1","rule":"shared"}}]
            """);
        assertThat(GenerateCompleteCanonicalTestSolutionPackage.ruleRequirements(List.of(rules.get(0), rules.get(1)), mapper))
            .containsKeys("BR-RULE-SEG1-R-001", "BR-RULE-SEG1-R-002").hasSize(2);
    }

    @Test
    void missingAnchorAndDuplicateRuleIdFailClosed() throws Exception {
        JsonNode missing = mapper.readTree("[{\"ruleId\":\"SEG1-R-001\"}]");
        assertThatThrownBy(() -> GenerateCompleteCanonicalTestSolutionPackage.ruleRequirements(List.of(missing.get(0)), mapper))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("sourceAnchor");
        JsonNode repeated = mapper.readTree("[{\"ruleId\":\"SEG1-R-001\",\"sourceAnchor\":{}},"
            + "{\"ruleId\":\"SEG1-R-001\",\"sourceAnchor\":{}}]");
        assertThatThrownBy(() -> GenerateCompleteCanonicalTestSolutionPackage.ruleRequirements(List.of(repeated.get(0), repeated.get(1)), mapper))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("Duplicate rule ID");
    }

    @Test
    void missingScenarioGetsOnlyASourceAnchoredReviewProposal() throws Exception {
        JsonNode requirement = mapper.readTree("""
            {"id":"BR-RULE-SEG1-R-001","title":"Element 55 is fixed to ATL105",
             "sourceAnchor":{"specification":"ATL105","version":"2026-3","section":"1","segment":"100","element":"55","rule":"fixed-identifier"}}
            """);

        JsonNode scenario = GenerateCompleteCanonicalTestSolutionPackage.draftScenarioProposal(requirement, mapper);

        assertThat(scenario.path("id").asText()).isEqualTo("TS-REVIEW-BR-RULE-SEG1-R-001");
        assertThat(scenario.path("intent").asText()).contains("Element 55 is fixed to ATL105");
        assertThat(scenario.path("requirementIds").get(0).asText()).isEqualTo("BR-RULE-SEG1-R-001");
        assertThat(scenario.path("sourceAnchors")).hasSize(1);
        assertThat(scenario.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(scenario.path("scenarioDefinitionStatus").asText())
                .isEqualTo("DRAFT_FROM_TEST_BR_TITLE_AND_SOURCE_ANCHOR");
        assertThat(scenario.path("scenarioDefinitionGaps").get(0).asText())
            .isEqualTo("APPLICABILITY_AND_PRECONDITIONS_NOT_DERIVED");
        assertThat(scenario.path("scenarioDefinitionGaps").get(3).asText())
            .isEqualTo("EXPECTED_OUTCOME_NOT_DERIVED");
        assertThat(scenario.has("preconditions")).isFalse();
        assertThat(scenario.has("expectedOutcome")).isFalse();
    }
}