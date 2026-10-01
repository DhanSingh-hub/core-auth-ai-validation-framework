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
import java.util.TreeMap;

/** Builds a review queue for cross-field dependencies and explicit absence/condition candidates. */
public final class GenerateAtl105DependencyReviewMatrix {
    private GenerateAtl105DependencyReviewMatrix() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode reference = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-all-elements-reference.json").toFile());
        ArrayNode rows = mapper.createArrayNode();
        TreeMap<String, Integer> counts = new TreeMap<>();
        for (JsonNode element : reference.path("elements")) {
            for (JsonNode dependency : element.path("dependencies")) {
                addRow(rows, counts, element.path("element").asText(), "DEPENDENCY_CANDIDATE", dependency, null);
            }
            for (JsonNode absence : element.path("explicitAbsenceOrConditionRules")) {
                addRow(rows, counts, element.path("element").asText(), "EXPLICIT_ABSENCE_OR_CONDITION_CANDIDATE", null, absence);
            }
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-DEPENDENCY-AND-ABSENCE-REVIEW-MATRIX");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION_SOURCE_DERIVED_REVIEW");
        output.put("status", "REVIEW_REQUIRED_UNTIL_SEMANTIC_SME_SIGNOFF");
        output.put("humanReviewRequired", true);
        output.put("policy", "Rule anchoring identifies candidates only. A dependency or absence rule is not enforced or certified until source meaning, message family, segment, transaction condition and companion behavior are semantically reviewed.");
        output.put("candidateCount", rows.size());
        output.put("dependencyCandidateCount", counts.getOrDefault("DEPENDENCY_CANDIDATE", 0));
        output.put("explicitAbsenceOrConditionCandidateCount", counts.getOrDefault("EXPLICIT_ABSENCE_OR_CONDITION_CANDIDATE", 0));
        output.put("semanticApprovedCount", 0);
        output.put("enforcedCount", 0);
        output.put("reviewRequiredCount", rows.size());
        output.set("countsByRuleClass", mapper.valueToTree(counts));
        output.set("rows", rows);
        Path json = pack.resolve("test-output/test-solution-independent-review/atl105-dependency-absence-review-matrix.json");
        Path csv = pack.resolve("test-output/test-solution-independent-review/atl105-dependency-absence-review-matrix.csv");
        Files.createDirectories(json.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), output);
        writeCsv(csv, rows);
        System.out.printf("candidates=%d dependencies=%d absences=%d approved=0 enforced=0%n", rows.size(), counts.getOrDefault("DEPENDENCY_CANDIDATE", 0), counts.getOrDefault("EXPLICIT_ABSENCE_OR_CONDITION_CANDIDATE", 0));
    }

    private static void addRow(ArrayNode rows, TreeMap<String, Integer> counts, String element, String kind, JsonNode dependency, JsonNode absence) {
        JsonNode value = dependency == null ? absence : dependency;
        ObjectNode row = rows.addObject();
        row.put("element", element);
        row.put("candidateType", kind);
        row.put("ruleId", value.path("ruleId").asText());
        row.put("ruleClass", value.path("ruleClass").asText());
        row.put("title", value.path("title").asText());
        row.put("relationship", value.path("relationship").asText("DIRECT_ELEMENT_ANCHOR"));
        row.put("candidateStatus", value.path("status").asText("REVIEW_REQUIRED"));
        row.put("sourceSection", value.path("sourceAnchor").path("section").asText());
        row.put("sourceSegment", value.path("sourceAnchor").path("segment").asText());
        row.put("sourceElement", value.path("sourceAnchor").path("element").asText());
        row.put("sourceRule", value.path("sourceAnchor").path("rule").asText());
        row.put("semanticReviewStatus", "REVIEW_REQUIRED");
        row.put("approvalStatus", "NOT_SME_APPROVED");
        row.put("enforcementStatus", "NOT_ENFORCED");
        row.put("decisionNeeded", "Confirm exact condition, allowed/forbidden scope, message family, transaction type, companion segments and negative/positive test evidence.");
        counts.merge(kind, 1, Integer::sum);
        counts.merge("ruleClass:" + value.path("ruleClass").asText("UNSPECIFIED"), 1, Integer::sum);
    }

    private static void writeCsv(Path path, ArrayNode rows) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,candidateType,ruleId,ruleClass,title,relationship,candidateStatus,sourceSection,sourceSegment,sourceElement,sourceRule,semanticReviewStatus,approvalStatus,enforcementStatus,decisionNeeded");
        for (JsonNode row : rows) {
            List<String> fields = List.of(row.path("element").asText(), row.path("candidateType").asText(), row.path("ruleId").asText(),
                row.path("ruleClass").asText(), row.path("title").asText(), row.path("relationship").asText(), row.path("candidateStatus").asText(),
                row.path("sourceSection").asText(), row.path("sourceSegment").asText(), row.path("sourceElement").asText(), row.path("sourceRule").asText(),
                row.path("semanticReviewStatus").asText(), row.path("approvalStatus").asText(), row.path("enforcementStatus").asText(), row.path("decisionNeeded").asText());
            lines.add(fields.stream().map(GenerateAtl105DependencyReviewMatrix::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String csv(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }
}
