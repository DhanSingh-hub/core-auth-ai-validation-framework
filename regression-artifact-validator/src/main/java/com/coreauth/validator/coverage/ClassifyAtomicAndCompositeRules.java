package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/** Classifies independent source rules for later BR design without changing the source-derived denominator. */
public final class ClassifyAtomicAndCompositeRules {
    private static final Pattern AMBIGUOUS_CONJUNCTION = Pattern.compile("\\b(and|or)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern EXPLICIT_COMPOSITE = Pattern.compile(
            "\\b(matrix|layout|siblings|mutually exclusive|multiple|every|all .* fields|full .* layout|set of|operations use)\\b",
            Pattern.CASE_INSENSITIVE);

    private ClassifyAtomicAndCompositeRules() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        Path inventoryFile = outputRoot.resolve("source-derived-requirement-inventory.json");
        JsonNode inventory = mapper.readTree(inventoryFile.toFile());
        ArrayNode classified = mapper.createArrayNode();
        Map<String, Integer> counts = new TreeMap<>();
        Map<String, Integer> byClass = new TreeMap<>();
        List<JsonNode> reviewQueue = new ArrayList<>();

        for (JsonNode rule : inventory.path("businessRequirements")) {
            ObjectNode entry = rule.deepCopy();
            Classification result = classify(rule);
            entry.put("atomicity", result.atomicity());
            entry.put("classificationConfidence", result.confidence());
            entry.put("classificationReason", result.reason());
            entry.set("verificationDimensions", dimensions(rule, mapper));
            classified.add(entry);
            counts.merge(result.atomicity(), 1, Integer::sum);
            byClass.merge(rule.path("class").asText("UNSPECIFIED"), 1, Integer::sum);
            if ("REVIEW_REQUIRED".equals(result.atomicity())) reviewQueue.add(entry);
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-atomic-composite-rule-classification");
        output.put("specification", inventory.path("specification").asText("ATL105"));
        output.put("specificationVersion", inventory.path("specificationVersion").asText("2026-3"));
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", false);
        output.put("sourceInventory", "source-derived-requirement-inventory.json");
        output.put("policy", "Classification is conservative triage for BR design; it does not split, merge, approve, or delete source rules.");
        output.set("counts", counts(counts, mapper));
        output.set("sourceRuleClassCounts", counts(byClass, mapper));
        output.set("rules", classified);
        output.set("reviewQueue", array(reviewQueue, mapper));
        ObjectNode validation = output.putObject("validation");
        validation.put("sourceRuleCount", inventory.path("ruleCount").asInt());
        validation.put("classifiedRuleCount", classified.size());
        validation.put("preservesSourceRuleCount", inventory.path("ruleCount").asInt() == classified.size());
        validation.put("nextPhase", "ATOMIC_BR_DERIVATION_AND_AI_CROSSWALK");

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("atomic-composite-rule-classification.json").toFile(), output);
        Files.writeString(outputRoot.resolve("atomic-composite-rule-classification.md"), markdown(output));
        System.out.printf("atomic=%d composite=%d review=%d total=%d%n",
                counts.getOrDefault("ATOMIC", 0), counts.getOrDefault("COMPOSITE_CANDIDATE", 0),
                counts.getOrDefault("REVIEW_REQUIRED", 0), classified.size());
    }

    private static Classification classify(JsonNode rule) {
        String title = rule.path("title").asText("");
        String section = rule.path("sourceAnchor").path("section").asText("");
        String segment = rule.path("sourceAnchor").path("segment").asText("");
        if (section.contains(",") || segment.contains(",") || EXPLICIT_COMPOSITE.matcher(title).find()) {
            return new Classification("COMPOSITE_CANDIDATE", "HIGH",
                    "The source anchor or title names multiple sections, segments, fields, branches, or a structured matrix/layout.");
        }
        if (AMBIGUOUS_CONJUNCTION.matcher(title).find()) {
            return new Classification("REVIEW_REQUIRED", "MEDIUM",
                    "The title contains a conjunction; confirm whether it expresses one behavior or multiple independently testable behaviors.");
        }
        return new Classification("ATOMIC", "MEDIUM",
                "The rule has one source anchor and no explicit multi-behavior signal; verify testability during BR derivation.");
    }

    private static ArrayNode dimensions(JsonNode rule, ObjectMapper mapper) {
        ArrayNode dimensions = mapper.createArrayNode();
        String title = rule.path("title").asText("").toLowerCase();
        String ruleClass = rule.path("class").asText("").toLowerCase();
        if (ruleClass.contains("structure") || title.contains("required") || title.contains("duplicat")) dimensions.add("presence_and_structure");
        if (ruleClass.contains("field") || title.contains("length") || title.contains("format") || title.contains("value")) dimensions.add("field_constraint");
        if (ruleClass.contains("compatibility") || ruleClass.contains("dependency") || title.contains("requires") || title.contains("companion")) dimensions.add("applicability_and_dependency");
        if (ruleClass.contains("conditional") || title.contains("when") || title.contains("if ")) dimensions.add("conditional_behavior");
        if (ruleClass.contains("lifecycle") || title.contains("lifecycle") || title.contains("completion") || title.contains("reversal")) dimensions.add("lifecycle_behavior");
        if (ruleClass.contains("serialization") || title.contains("serialized") || title.contains("order")) dimensions.add("serialization");
        if (ruleClass.contains("response") || title.contains("response") || title.contains("declin")) dimensions.add("response_behavior");
        if (dimensions.isEmpty()) dimensions.add("behavioral_rule");
        return dimensions;
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
        JsonNode counts = output.path("counts");
        return "# Atomic and Composite Rule Classification\n\n"
                + "This is conservative triage of the independent source-derived inventory. It does not create BRs or alter source rules.\n\n"
                + "| Classification | Count |\n|---|---:|\n"
                + "| Atomic candidates | " + counts.path("ATOMIC").asInt() + " |\n"
                + "| Composite candidates | " + counts.path("COMPOSITE_CANDIDATE").asInt() + " |\n"
                + "| Review required | " + counts.path("REVIEW_REQUIRED").asInt() + " |\n\n"
                + "Composite candidates must be decomposed into separate BRs only when each component has its own source evidence, expected behavior, and executable TS/TC/TD chain.\n";
    }

    private record Classification(String atomicity, String confidence, String reason) { }
}