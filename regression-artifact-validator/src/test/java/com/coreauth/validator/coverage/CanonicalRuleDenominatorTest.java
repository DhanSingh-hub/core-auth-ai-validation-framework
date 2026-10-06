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
}