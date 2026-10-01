package com.coreauth.validator;

import com.coreauth.validator.coverage.BusinessRequirementIndex;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessRequirementIndexTest {
    @Test
    void linksExistingJsonBusinessRequirementsByExactSourceAnchor() throws Exception {
        Path pack = Path.of("specifications", "ATL105");
        Path kb = pack.resolve("docs/specs/kb");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, JsonNode> rules = new LinkedHashMap<>();
        try (var files = Files.walk(kb)) {
            for (Path file : files.filter(path -> path.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).toList())
                for (JsonNode rule : mapper.readTree(file.toFile()).path("rules"))
                    rules.put(rule.path("ruleId").asText(), rule);
        }
        BusinessRequirementIndex index = BusinessRequirementIndex.load(pack, rules);

        assertThat(index.requirements("SEG100-R-001"))
            .anySatisfy(link -> {
                assertThat(link.requirementId()).isEqualTo("BR-SEG100-BASE-001");
                assertThat(link.evidence()).isEqualTo("JSON_PACKAGE_EXACT_SOURCE_ANCHOR");
            });
        assertThat(index.unknownRuleCitations()).isEmpty();
    }
}
