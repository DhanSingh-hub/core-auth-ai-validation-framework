package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ElementChainLinkIndexTest {
    @Test
    void indexesOnlyExactArrayIdentifiers() throws Exception {
        JsonNode scenarios = new ObjectMapper().readTree("""
            [{"id":"one","requirementIds":["BR-RULE-1","BR-RULE-10"]},
             {"id":"two","requirementIds":["BR-RULE-10"]},
             {"id":"three","requirementIds":"BR-RULE-1"}]
            """);
        Map<String, List<JsonNode>> links = GenerateAtl105ElementChainCoverage.indexByLink(scenarios, "requirementIds");
        assertThat(links.get("BR-RULE-1")).extracting(node -> node.path("id").asText()).containsExactly("one");
        assertThat(links.get("BR-RULE-10")).extracting(node -> node.path("id").asText()).containsExactly("one", "two");
        assertThat(links).doesNotContainKey("BR-RULE-");
    }

    @Test
    void executablePathRequiresOneCommonCanonicalAnchor() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode first = mapper.readTree("{\"sourceAnchors\":[{\"specification\":\"ATL105\",\"version\":\"2026-3\",\"rule\":\"A\"}]}");
        JsonNode equivalent = mapper.readTree("{\"sourceAnchors\":[{\"specification\":\"atl105\",\"version\":\"2026-3\",\"rule\":\"a\"}]}");
        JsonNode different = mapper.readTree("{\"sourceAnchors\":[{\"specification\":\"ATL105\",\"version\":\"2026-3\",\"rule\":\"B\"}]}");
        assertThat(GenerateAtl105ElementChainCoverage.sharesSourceAnchor(mapper, first, equivalent, first, equivalent)).isTrue();
        assertThat(GenerateAtl105ElementChainCoverage.sharesSourceAnchor(mapper, first, equivalent, different, equivalent)).isFalse();
        assertThat(GenerateAtl105ElementChainCoverage.sharesSourceAnchor(mapper, first, mapper.createObjectNode())).isFalse();
    }
}