package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Maps AI BRs to exact source-derived atomic-rule candidates without fuzzy coverage claims. */
public final class GenerateAtomicBrAiCrosswalk {
    private static final Pattern ELEMENT_ID = Pattern.compile("(\\d+)$");

    private GenerateAtomicBrAiCrosswalk() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path outputRoot = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        JsonNode inventory = mapper.readTree(outputRoot.resolve("atomic-composite-rule-classification.json").toFile());
        Map<String, List<JsonNode>> rulesBySegmentElement = indexRules(inventory.path("rules"));
        Path traceability = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-23", "Run2", "traceability_matrix_full.json"));

        ArrayNode mappings = mapper.createArrayNode();
        Map<String, Integer> counts = new TreeMap<>();
        Map<String, Integer> bySegment = new TreeMap<>();
        StringBuilder csv = new StringBuilder("aiRequirementId,aiSegment,sourceRuleId,inferredElement,status,candidateTestRuleIds,statement,sourcePage\n");
        try (JsonParser parser = mapper.getFactory().createParser(traceability.toFile())) {
            require(parser.nextToken() == JsonToken.START_OBJECT, "AI traceability root must be an object");
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if (!"requirements".equals(field)) {
                    parser.skipChildren();
                    continue;
                }
                require(parser.currentToken() == JsonToken.START_ARRAY, "AI requirements must be an array");
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    JsonNode ai = mapper.readTree(parser);
                    ObjectNode mapping = mapper.createObjectNode();
                    String aiId = ai.path("requirement_id").asText("");
                    String segment = normalizeSegment(ai.path("segment_id").asText(""));
                    String sourceRuleId = ai.path("source_rule_id").asText("");
                    String element = inferredElement(sourceRuleId);
                    List<JsonNode> candidates = rulesBySegmentElement.getOrDefault(segment + "|" + element, List.of());
                    String status = status(candidates);
                    ObjectNode behavior = normalizedBehavior(ai, segment, element, mapper);
                    mapping.put("aiRequirementId", aiId);
                    mapping.put("aiSegment", segment);
                    mapping.put("sourceRuleId", sourceRuleId);
                    mapping.put("inferredElement", element);
                    mapping.put("status", status);
                    mapping.put("statement", ai.path("statement").asText(""));
                    mapping.put("sourcePage", ai.path("source_page").asInt(-1));
                    mapping.set("normalizedBehavior", behavior);
                    mapping.put("confidenceStatus", confidenceStatus(ai, candidates, status));
                    mapping.put("confidenceReason", confidenceReason(ai, candidates, status));
                    mapping.set("candidateTestRules", candidatesArray(candidates, mapper));
                    mappings.add(mapping);
                    counts.merge(status, 1, Integer::sum);
                    bySegment.merge(segment, 1, Integer::sum);
                    csv.append(csv(aiId)).append(',').append(csv(segment)).append(',')
                            .append(csv(sourceRuleId)).append(',').append(csv(element)).append(',')
                            .append(csv(status)).append(',').append(csv(candidateIds(candidates)))
                            .append(',').append(csv(ai.path("statement").asText(""))).append(',')
                            .append(ai.path("source_page").asInt(-1)).append('\n');
                }
            }
        }

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-atomic-br-ai-crosswalk");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("aiArtifactsIncluded", true);
        output.put("sourceInventory", "atomic-composite-rule-classification.json");
        output.put("aiSource", "test-input/ai-solution/runs/2026-09-23/Run2/traceability_matrix_full.json");
        output.put("matchingPolicy", "Only exact normalized segment plus inferred numeric source-rule element creates a candidate; no title similarity counts as coverage.");
        output.put("coveragePolicy", "Candidate mappings are not confirmed BR coverage and do not create Test Solution BRs.");
        output.set("counts", counts(counts, mapper));
        output.set("aiRequirementCountsBySegment", counts(bySegment, mapper));
        output.set("mappings", mappings);
        ObjectNode validation = output.putObject("validation");
        validation.put("aiRequirementCount", mappings.size());
        validation.put("mappingCount", mappings.size());
        validation.put("oneEntryPerAiRequirement", mappings.size() == 6473);
        validation.put("nextPhase", "SME_REVIEW_AND_ATOMIC_BR_DERIVATION");

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("atomic-br-ai-crosswalk.json").toFile(), output);
        Files.writeString(outputRoot.resolve("atomic-br-ai-crosswalk.csv"), csv);
        Files.writeString(outputRoot.resolve("atomic-br-ai-crosswalk.md"), markdown(output));
        System.out.printf("aiRequirements=%d exactAtomic=%d compositeOrReview=%d noExact=%d%n",
                mappings.size(), counts.getOrDefault("EXACT_ATOMIC_CANDIDATE", 0),
                counts.getOrDefault("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS", 0),
                counts.getOrDefault("NO_EXACT_ATOMIC_MATCH", 0));
    }

    private static Map<String, List<JsonNode>> indexRules(JsonNode rules) {
        Map<String, List<JsonNode>> result = new LinkedHashMap<>();
        for (JsonNode rule : rules) {
            String element = rule.path("sourceAnchor").path("element").asText("");
            if (element.isBlank()) continue;
            for (String segment : rule.path("sourceAnchor").path("segment").asText("").split(",")) {
                String key = segment.trim().toUpperCase() + "|" + element;
                result.computeIfAbsent(key, ignored -> new ArrayList<>()).add(rule);
            }
        }
        return result;
    }

    private static String normalizeSegment(String value) {
        return value.replaceFirst("^ENT-SEG-", "").trim().toUpperCase();
    }

    private static String inferredElement(String sourceRuleId) {
        Matcher matcher = ELEMENT_ID.matcher(sourceRuleId == null ? "" : sourceRuleId);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static String status(List<JsonNode> candidates) {
        if (candidates.isEmpty()) return "NO_EXACT_ATOMIC_MATCH";
        boolean allAtomic = candidates.stream().allMatch(rule -> "ATOMIC".equals(rule.path("atomicity").asText()));
        return allAtomic ? "EXACT_ATOMIC_CANDIDATE" : "REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS";
    }

    private static String confidenceStatus(JsonNode ai, List<JsonNode> candidates, String status) {
        if (ai.path("source_rule_id").asText("").isBlank()
                || ai.path("segment_id").asText("").isBlank()) return "AUTOMATED_UNSUPPORTED";
        if (candidates.isEmpty()) return "REVIEW_REQUIRED";
        boolean atomic = candidates.stream().allMatch(rule -> "ATOMIC".equals(rule.path("atomicity").asText()));
        if (!atomic) return "REVIEW_REQUIRED";
        String statement = normalizeText(ai.path("statement").asText(""));
        boolean behaviorSignal = statement.contains("required") || statement.contains("must")
                || statement.contains("shall") || statement.contains("length")
                || statement.contains("numeric") || statement.contains("alphanumeric");
        return behaviorSignal ? "AUTOMATED_STRUCTURAL" : "AUTOMATED_PARTIAL";
    }

    private static String confidenceReason(JsonNode ai, List<JsonNode> candidates, String status) {
        if ("AUTOMATED_UNSUPPORTED".equals(confidenceStatus(ai, candidates, status))) return "AI requirement has no usable source segment or source rule anchor.";
        if (candidates.isEmpty()) return "No independent Test Rule candidate shares the normalized segment and element key.";
        if (!candidates.stream().allMatch(rule -> "ATOMIC".equals(rule.path("atomicity").asText()))) return "Candidate includes composite or ambiguous source behavior.";
        return "Exact segment and element candidate found; wording and behavioral equivalence still require deterministic normalization checks.";
    }

    private static ObjectNode normalizedBehavior(JsonNode ai, String segment, String element, ObjectMapper mapper) {
        ObjectNode behavior = mapper.createObjectNode();
        String statement = normalizeText(ai.path("statement").asText(""));
        behavior.put("specification", "ATL105");
        behavior.put("version", "2026-3");
        behavior.put("segment", segment);
        behavior.put("element", element);
        behavior.put("sourceRule", normalizeText(ai.path("source_rule_id").asText("")));
        behavior.put("condition", extractTerms(statement, "when", "if", "unless", "only", "required", "optional", "prohibited"));
        behavior.put("expectedBehavior", extractTerms(statement, "must", "shall", "required", "numeric", "alphanumeric", "length", "format", "value", "equal", "contain"));
        behavior.put("normalizedStatement", statement);
        return behavior;
    }

    private static String extractTerms(String statement, String... terms) {
        List<String> found = new ArrayList<>();
        for (String term : terms) if (statement.contains(term)) found.add(term);
        return String.join(" ", found);
    }

    private static String normalizeText(String value) {
        return value == null ? "" : value.toLowerCase(java.util.Locale.ROOT)
                .replaceAll("[^a-z0-9 ]", " ").replaceAll("\\s+", " ").trim();
    }

    private static ArrayNode candidatesArray(List<JsonNode> candidates, ObjectMapper mapper) {
        ArrayNode output = mapper.createArrayNode();
        for (JsonNode rule : candidates) {
            ObjectNode candidate = output.addObject();
            candidate.put("testRuleId", rule.path("ruleId").asText());
            candidate.put("title", rule.path("title").asText());
            candidate.put("atomicity", rule.path("atomicity").asText());
            candidate.put("canonicalAnchor", rule.path("canonicalAnchor").asText());
        }
        return output;
    }

    private static String candidateIds(List<JsonNode> candidates) {
        return candidates.stream().map(rule -> rule.path("ruleId").asText()).reduce((left, right) -> left + ";" + right).orElse("");
    }

    private static ObjectNode counts(Map<String, Integer> values, ObjectMapper mapper) {
        ObjectNode output = mapper.createObjectNode();
        values.forEach(output::put);
        return output;
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"").replace("\r", " ").replace("\n", " ") + "\"";
    }

    private static String markdown(JsonNode output) {
        JsonNode counts = output.path("counts");
        return "# Atomic BR to AI Crosswalk\n\n"
                + "This crosswalk maps AI requirements to exact source-derived atomic-rule candidates. It does not confirm coverage or create BRs.\n\n"
                + "| Status | Count |\n|---|---:|\n"
                + "| Exact atomic candidate | " + counts.path("EXACT_ATOMIC_CANDIDATE").asInt() + " |\n"
                + "| Composite or ambiguous review | " + counts.path("REVIEW_REQUIRED_COMPOSITE_OR_AMBIGUOUS").asInt() + " |\n"
                + "| No exact atomic match | " + counts.path("NO_EXACT_ATOMIC_MATCH").asInt() + " |\n\n"
                + "Only exact segment-plus-element evidence creates a candidate. Title similarity is intentionally excluded.\n";
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}