package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;

/** Writes the independent AI and Test Solution traceability chains for one requirement pair, for manual review. */
public final class GeneratePairTraceabilityMatrixReport {

    private GeneratePairTraceabilityMatrixReport() { }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("Usage: <aiRequirementId> <testRequirementId>");
        String aiRequirementId = args[0];
        String testRequirementId = args[1];

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = GenerateFourLevelTraceabilityMatchReport.buildPairTraceability(aiRequirementId, testRequirementId);

        Path outputRoot = Atl105Paths.testOutput().resolve(
                Path.of("ai-solution-independent-review", "four-level-traceability-matches"));
        String slug = slug(aiRequirementId) + "__vs__" + slug(testRequirementId);
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("pair-traceability-" + slug + ".json").toFile(), root);
        Files.writeString(outputRoot.resolve("pair-traceability-" + slug + ".md"), markdown(root));
        System.out.printf("aiBRs=%d aiTS=%d aiTC=%d aiTD=%d testBRs=%d testTS=%d testTC=%d testTD=%d%n",
                root.path("ai").path("businessRequirements").size(), root.path("ai").path("testScenarios").size(),
                root.path("ai").path("testCases").size(), root.path("ai").path("testData").size(),
                root.path("testSolution").path("businessRequirements").size(), root.path("testSolution").path("testScenarios").size(),
                root.path("testSolution").path("testCases").size(), root.path("testSolution").path("testData").size());
    }

    private static String markdown(ObjectNode root) {
        String aiId = root.path("aiRequirementId").asText();
        String testId = root.path("testRequirementId").asText();
        StringBuilder text = new StringBuilder("# Traceability Matrix: ").append(aiId).append(" vs ").append(testId).append("\n\n")
                .append("Independent chains for each side; artifacts are only listed if reachable through this side's own BR-\u003eTS-\u003eTC-\u003eTD links.\n\n")
                .append("## AI (Run2) - ").append(aiId).append("\n\n");
        chainTables(text, root.path("ai"));
        text.append("## Test Solution - ").append(testId).append("\n\n");
        chainTables(text, root.path("testSolution"));
        return text.toString();
    }

    private static void chainTables(StringBuilder text, JsonNode chain) {
        table(text, "Business Requirements", chain.path("businessRequirements"), "title");
        table(text, "Test Scenarios", chain.path("testScenarios"), "requirementIds");
        table(text, "Test Cases", chain.path("testCases"), "expectedOutcome");
        table(text, "Test Data", chain.path("testData"), "expectedValidation");
    }

    private static void table(StringBuilder text, String label, JsonNode values, String detailField) {
        text.append("### ").append(label).append(" (").append(values.size()).append(")\n\n");
        if (values.isEmpty()) {
            text.append("_None._\n\n");
            return;
        }
        text.append("| ID | ").append(detailField).append(" |\n|---|---|\n");
        for (JsonNode value : values) {
            text.append('|').append(value.path("id").asText("")).append('|')
                    .append(cell(detail(value.path(detailField)))).append("|\n");
        }
        text.append('\n');
    }

    private static String detail(JsonNode value) {
        if (value.isMissingNode() || value.isNull()) return "";
        if (value.isArray()) {
            StringBuilder joined = new StringBuilder();
            for (JsonNode item : value) {
                if (joined.length() > 0) joined.append(", ");
                joined.append(item.asText());
            }
            return joined.toString();
        }
        return value.asText();
    }

    private static String cell(String value) {
        return value.replace("|", "\\|").replace("\n", " ");
    }

    private static String slug(String value) {
        return value.replaceAll("[^A-Za-z0-9]+", "-");
    }
}
