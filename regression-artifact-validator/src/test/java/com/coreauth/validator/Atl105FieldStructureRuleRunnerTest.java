package com.coreauth.validator;

import com.coreauth.validator.canonical.Atl105FieldStructureRuleRunner;
import com.coreauth.validator.validation.Atl105ElementValueValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105FieldStructureRuleRunnerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACK = Path.of("specifications", "ATL105");

    @Test
    void catalogDenominatorsAndProfileExecutorsStayExplicit() throws Exception {
        var inventory = MAPPER.readTree(PACK.resolve("test-output/test-solution-independent-review/source-derived-requirement-inventory.json").toFile());
        var profiles = MAPPER.readTree(PACK.resolve("docs/specs/kb/elements/validation-profiles.json").toFile());
        var rules = Atl105FieldStructureRuleRunner.loadFieldStructureRules(inventory);
        var executable = Atl105FieldStructureRuleRunner.loadExecutableProfiles(profiles, rules);
        var partialStructure = Atl105FieldStructureRuleRunner.partialStructureExecutors(rules);

        assertThat(rules).hasSize(326);
        assertThat(rules.values().stream().filter(rule -> "field".equals(rule.path("class").asText())).count()).isEqualTo(264);
        assertThat(rules.values().stream().filter(rule -> "structure".equals(rule.path("class").asText())).count()).isEqualTo(62);
        assertThat(profiles.path("profiles")).isNotEmpty();
        long expectedBindings = 0;
        for (var profile : profiles.path("profiles")) {
            var rule = rules.get(profile.path("existingRuleId").asText());
            if (rule != null) expectedBindings++;
        }
        assertThat(executable.values().stream().mapToLong(java.util.List::size).sum()).isEqualTo(expectedBindings);
        assertThat(executable.keySet()).contains("SEG100-R-015", "SEG105-R-020", "SEG108-R-025", "SEG110-R-021");
        assertThat(partialStructure).containsEntry("SEG100-R-001", "SECTION11_REQUIRED_SEGMENT_100_MISSING_ONLY");
        assertThat(Atl105FieldStructureRuleRunner.reportStatus(Atl105ElementValueValidator.Status.CHECKS_PASSED))
            .isEqualTo("PASS");
        assertThat(Atl105FieldStructureRuleRunner.reportStatus(Atl105ElementValueValidator.Status.INVALID))
            .isEqualTo("FAIL");
        assertThat(Atl105FieldStructureRuleRunner.reportStatus(Atl105ElementValueValidator.Status.REVIEW_REQUIRED))
            .isEqualTo("REVIEW_REQUIRED");
    }
}