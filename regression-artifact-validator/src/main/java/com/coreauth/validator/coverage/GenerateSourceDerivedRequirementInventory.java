package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/** Builds the source-derived Test Solution requirement denominator without reading AI artifacts. */
public final class GenerateSourceDerivedRequirementInventory {
    private GenerateSourceDerivedRequirementInventory() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path kbRoot = Atl105Paths.docs().resolve(Path.of("specs", "kb"));
        List<Path> catalogFiles = catalogFiles(kbRoot);
        List<JsonNode> rules = new ArrayList<>();
        Map<String, Integer> rulesBySegment = new TreeMap<>();
        Map<String, Integer> rulesByClass = new TreeMap<>();
        Map<String, Integer> rulesBySeverity = new TreeMap<>();
        Map<String, Integer> rulesByCatalog = new TreeMap<>();
        Map<String, Integer> duplicateIds = new TreeMap<>();
        Map<String, Integer> duplicateAnchors = new TreeMap<>();

        for (Path catalogFile : catalogFiles) {
            JsonNode catalog = mapper.readTree(catalogFile.toFile());
            String catalogName = kbRoot.relativize(catalogFile).toString().replace('\\', '/');
            for (JsonNode rule : catalog.path("rules")) {
                ObjectNode inventoryRule = rule.deepCopy();
                JsonNode anchor = rule.path("sourceAnchor");
                inventoryRule.put("sourceCatalog", catalogName);
                inventoryRule.put("canonicalAnchor", canonicalAnchor(anchor));
                inventoryRule.put("sourceEvidenceResolution", evidenceResolution(anchor));
                rules.add(inventoryRule);

                String id = rule.path("ruleId").asText("");
                String anchorKey = canonicalAnchor(anchor);
                duplicateIds.merge(id, 1, Integer::sum);
                duplicateAnchors.merge(anchorKey, 1, Integer::sum);
                rulesBySegment.merge(anchor.path("segment").asText("UNSPECIFIED"), 1, Integer::sum);
                rulesByClass.merge(rule.path("class").asText("UNSPECIFIED"), 1, Integer::sum);
                rulesBySeverity.merge(rule.path("severity").asText("UNSPECIFIED"), 1, Integer::sum);
                rulesByCatalog.merge(catalogName, 1, Integer::sum);
            }
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-source-derived-test-solution-requirement-inventory");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("sourcePolicy", "One inventory entry per Test Solution KB rule catalog entry; AI BRs are excluded from the denominator.");
        output.put("anchorPolicy", "Every rule must retain specification, version, section, segment, and rule source-anchor fields.");
        output.put("catalogCount", catalogFiles.size());
        output.put("ruleCount", rules.size());
        output.put("duplicateRuleIdCount", duplicateCount(duplicateIds));
        output.put("duplicateAnchorCount", duplicateCount(duplicateAnchors));
        output.put("incompleteAnchorCount", incompleteAnchorCount(rules));
        output.set("businessRequirements", array(rules, mapper));
        output.set("countsBySegment", counts(rulesBySegment, mapper));
        output.set("countsByClass", counts(rulesByClass, mapper));
        output.set("countsBySeverity", counts(rulesBySeverity, mapper));
        output.set("countsByCatalog", counts(rulesByCatalog, mapper));
        ObjectNode validation = output.putObject("validation");
        validation.put("valid", duplicateCount(duplicateIds) == 0
                && duplicateCount(duplicateAnchors) == 0 && incompleteAnchorCount(rules) == 0);
        validation.put("requiresSemanticReview", true);
        validation.put("requiresChainDerivation", true);
        validation.put("nextPhase", "ATOMIC_RULE_CLASSIFICATION_AND_AI_CROSSWALK");

        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        Files.createDirectories(outputRoot);
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("source-derived-requirement-inventory.json").toFile(), output);
        Files.writeString(outputRoot.resolve("source-derived-requirement-inventory.md"), markdown(output));
        System.out.printf("catalogs=%d rules=%d valid=%s output=%s%n", catalogFiles.size(), rules.size(),
                validation.path("valid").asBoolean(), outputRoot.resolve("source-derived-requirement-inventory.json"));
    }

    private static List<Path> catalogFiles(Path kbRoot) throws Exception {
        try (Stream<Path> files = Files.walk(kbRoot)) {
            return files.filter(path -> path.getFileName().toString().matches("segment-.*-rule-catalog\\.json"))
                    .sorted().toList();
        }
    }

    private static String canonicalAnchor(JsonNode anchor) {
        return String.join("|", anchor.path("specification").asText(""), anchor.path("version").asText(""),
                anchor.path("section").asText(""), anchor.path("segment").asText(""),
                anchor.path("element").asText(""), anchor.path("rule").asText(""));
    }

    private static String evidenceResolution(JsonNode anchor) {
        String section = anchor.path("section").asText("");
        if (section.contains(",")) return "COMPOSITE_SECTIONS";
        if (section.equalsIgnoreCase("Totals Request")) return "TITLE_ALIAS_REVIEW_REQUIRED";
        if (section.toLowerCase().startsWith("appendix")) return "APPENDIX_EVIDENCE";
        return "DIRECT_SECTION";
    }

    private static int duplicateCount(Map<String, Integer> counts) {
        return (int) counts.values().stream().filter(count -> count > 1).count();
    }

    private static int incompleteAnchorCount(List<JsonNode> rules) {
        return (int) rules.stream().filter(rule -> {
            JsonNode anchor = rule.path("sourceAnchor");
            return anchor.path("specification").asText("").isBlank()
                    || anchor.path("version").asText("").isBlank()
                    || anchor.path("section").asText("").isBlank()
                    || anchor.path("segment").asText("").isBlank()
                    || anchor.path("rule").asText("").isBlank();
        }).count();
    }

    private static ObjectNode counts(Map<String, Integer> values, ObjectMapper mapper) {
        ObjectNode output = mapper.createObjectNode();
        values.forEach(output::put);
        return output;
    }

    private static ArrayNode array(List<JsonNode> values, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        values.forEach(output::add);
        return output;
    }

    private static String markdown(JsonNode output) {
        return "# Source-Derived Test Solution Requirement Inventory\n\n"
                + "This inventory is derived only from the independent Test Solution KB rule catalogs. AI artifacts are excluded.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| Rule catalogs | " + output.path("catalogCount").asInt() + " |\n"
                + "| Source-derived requirements | " + output.path("ruleCount").asInt() + " |\n"
                + "| Duplicate rule IDs | " + output.path("duplicateRuleIdCount").asInt() + " |\n"
                + "| Duplicate source anchors | " + output.path("duplicateAnchorCount").asInt() + " |\n"
                + "| Incomplete source anchors | " + output.path("incompleteAnchorCount").asInt() + " |\n\n"
                + "The inventory is the denominator for later AI reconciliation. It still requires semantic review and BR -> TS -> TC -> TD chain derivation.\n";
    }
}