package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the independently maintained Segment 120 rule catalog produced by Item 1.
 *
 * <p>Mirrors {@link Segment111RuleCatalogLoader} / {@link Segment101RuleCatalogLoader}. The
 * catalog file lives at {@code docs/specs/kb/segment-120/coverage/segment-120-rule-catalog.json}.
 *
 * <p>Unlike the Segment 111/101 loaders, each {@link Rule} also carries the catalog's optional
 * {@code provisional} / {@code resolved} tags so that Item 4 (Traceability Matrix) can report
 * which of the 8 rules are (or were) PROVISIONAL, per the catalog's {@code provisionalItems}
 * (P-01, P-02-WIDTH, P-02-RESIDUAL, P-03, P-04).
 */
public final class Segment120RuleCatalogLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public Catalog load(Path file) throws IOException {
        JsonNode root = mapper.readTree(file.toFile());
        List<Rule> rules = new ArrayList<>();
        for (JsonNode node : root.path("rules")) {
            rules.add(new Rule(
                node.path("ruleId").asText(),
                node.path("title").asText(),
                mapper.treeToValue(node.path("sourceAnchor"), SourceAnchor.class),
                node.hasNonNull("provisional") ? node.path("provisional").asText() : null,
                node.hasNonNull("resolved") ? node.path("resolved").asText() : null
            ));
        }
        return new Catalog(root.path("catalogId").asText(), rules);
    }

    /**
     * @param provisionalId  non-null when the rule is currently tied to an OPEN provisional item
     *                       (e.g. {@code "P-02-RESIDUAL"} for SEG120-R-004)
     * @param resolvedId     non-null when the rule was previously provisional but the catalog
     *                       records it as RESOLVED (e.g. {@code "P-01"} for SEG120-R-007)
     */
    public record Rule(String ruleId, String title, SourceAnchor sourceAnchor, String provisionalId, String resolvedId) {
        public boolean isProvisionalOrBlocked() {
            return provisionalId != null || resolvedId != null;
        }
    }

    public record Catalog(String catalogId, List<Rule> rules) {
        public List<SourceAnchor> anchors() {
            return rules.stream().map(Rule::sourceAnchor).toList();
        }
    }
}
