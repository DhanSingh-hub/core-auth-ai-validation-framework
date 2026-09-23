package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the independently maintained Segment 111 rule catalog produced by Item 1.
 *
 * <p>Mirrors {@link Segment101RuleCatalogLoader}. The catalog file lives at
 * {@code docs/specs/kb/segment-111/coverage/segment-111-rule-catalog.json}.
 */
public final class Segment111RuleCatalogLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public Catalog load(Path file) throws IOException {
        JsonNode root = mapper.readTree(file.toFile());
        List<Rule> rules = new ArrayList<>();
        for (JsonNode node : root.path("rules")) {
            rules.add(new Rule(
                node.path("ruleId").asText(),
                node.path("title").asText(),
                mapper.treeToValue(node.path("sourceAnchor"), SourceAnchor.class)
            ));
        }
        return new Catalog(root.path("catalogId").asText(), rules);
    }

    public record Rule(String ruleId, String title, SourceAnchor sourceAnchor) {
    }

    public record Catalog(String catalogId, List<Rule> rules) {
        public List<SourceAnchor> anchors() {
            return rules.stream().map(Rule::sourceAnchor).toList();
        }
    }
}


