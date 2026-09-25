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

/** Decomposes AI statements into reviewable behavioral clauses without creating Test BRs automatically. */
public final class GenerateAiBehaviorDecomposition {
    private GenerateAiBehaviorDecomposition() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path root = Atl105Paths.testOutput().resolve("test-solution-independent-review");
        JsonNode crosswalk = mapper.readTree(root.resolve("atomic-br-ai-crosswalk.json").toFile());
        ArrayNode entries = mapper.createArrayNode();
        int composite = 0;
        for (JsonNode mapping : crosswalk.path("mappings")) {
            String statement = mapping.path("statement").asText("");
            List<String> clauses = clauses(statement);
            ObjectNode entry = entries.addObject();
            entry.put("aiRequirementId", mapping.path("aiRequirementId").asText());
            entry.put("sourceRuleId", mapping.path("sourceRuleId").asText());
            entry.put("segment", mapping.path("aiSegment").asText());
            entry.put("status", clauses.size() > 1 ? "COMPOSITE_REVIEW_REQUIRED" : "ATOMIC_OR_UNRESOLVED");
            entry.put("sourceStatement", statement);
            ArrayNode behaviors = entry.putArray("behaviorCandidates");
            for (int i = 0; i < clauses.size(); i++) {
                ObjectNode behavior = behaviors.addObject();
                behavior.put("candidateId", mapping.path("aiRequirementId").asText() + "-B" + (i + 1));
                behavior.put("statement", clauses.get(i));
                behavior.put("requiresIndependentSourceCheck", true);
                behavior.put("requiresIndependentTestChain", true);
            }
            if (clauses.size() > 1) composite++;
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "atl105-ai-behavior-decomposition");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION_REVIEW");
        output.put("aiArtifactsIncluded", true);
        output.put("policy", "Clause decomposition creates reviewable candidates only; it never creates or approves Test BRs automatically.");
        output.put("aiRequirementCount", entries.size());
        output.put("compositeReviewCount", composite);
        output.set("entries", entries);
        output.putObject("nextGate").put("name", "SOURCE_CHECK_EACH_BEHAVIOR_CANDIDATE");
        mapper.writerWithDefaultPrettyPrinter().writeValue(root.resolve("ai-behavior-decomposition.json").toFile(), output);
        Files.writeString(root.resolve("ai-behavior-decomposition.md"), markdown(output));
        System.out.printf("requirements=%d compositeReview=%d%n", entries.size(), composite);
    }

    private static List<String> clauses(String statement) {
        if (statement == null || statement.isBlank()) return List.of("");
        String normalized = statement.replaceAll("\\s+", " ").trim();
        String[] parts = normalized.split("\\s+(?:and|or|;|plus)\\s+|[,;]\\s+(?=[A-Z])");
        List<String> result = new ArrayList<>();
        for (String part : parts) if (!part.isBlank()) result.add(part.trim());
        return result.isEmpty() ? List.of(normalized) : result;
    }

    private static String markdown(JsonNode output) {
        return "# AI Behavior Decomposition\n\n"
                + "This artifact decomposes AI statements into reviewable behavior candidates. It does not create Test BRs automatically.\n\n"
                + "| Metric | Count |\n|---|---:|\n"
                + "| AI requirements | " + output.path("aiRequirementCount").asInt() + " |\n"
                + "| Composite review candidates | " + output.path("compositeReviewCount").asInt() + " |\n";
    }
}