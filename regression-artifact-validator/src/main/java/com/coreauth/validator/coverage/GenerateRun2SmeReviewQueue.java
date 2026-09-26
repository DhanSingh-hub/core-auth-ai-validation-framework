package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Generates Step 6 mapping and validation action queues from independent evidence. */
public final class GenerateRun2SmeReviewQueue {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private GenerateRun2SmeReviewQueue() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path crosswalkRoot = reviewRoot.resolve("run2-crosswalks");
        Path validationRoot = reviewRoot.resolve("run2-validation");
        Path outputRoot = reviewRoot.resolve("run2-sme-review-queue");
        Files.createDirectories(outputRoot);
        ObjectNode queue = mapper.createObjectNode();
        queue.put("artifact", "run2-sme-and-remediation-queue");
        queue.put("sourceRun", "2026-09-23/Run1+Run2");
        queue.put("status", "OPEN");
        ArrayNode items = queue.putArray("items");
        Map<String, Integer> counts = new LinkedHashMap<>();
        int sequence = 1;

        for (String segment : SEGMENTS) {
            JsonNode crosswalk = mapper.readTree(crosswalkRoot.resolve(
                    "segment-" + segment + "-run2-crosswalk.json").toFile());
            for (JsonNode mapping : crosswalk.path("mappings")) {
                String status = mapping.path("matchStatus").asText();
                if ("REVIEW_REQUIRED".equals(status)) {
                    add(items, counts, sequence++, segment, "MAPPING_REVIEW", "HIGH",
                            mapping.path("reviewOwner").asText("ATL105-SME-" + segment),
                            mapping.path("testRequirementId").asText(), join(mapping.path("aiRequirementIds")),
                            mapping.path("matchReason").asText());
                } else if ("MISSING".equals(status)) {
                    add(items, counts, sequence++, segment, "MISSING_AI_COVERAGE", "MEDIUM",
                            "ATL105-SME-" + segment, mapping.path("testRequirementId").asText(), "",
                            "Confirm whether AI omitted this rule or whether a valid equivalent remains unmapped.");
                }
            }
            for (JsonNode aiId : crosswalk.path("unresolvedPriorConfirmedAiRequirementIds")) {
                add(items, counts, sequence++, segment, "PRIOR_CONFIRMATION_RECONCILIATION", "HIGH",
                        "ATL105-SME-" + segment, "", aiId.asText(),
                        "A prior confirmation could not be attached to exactly one canonical denominator rule.");
            }

            JsonNode evidence = mapper.readTree(validationRoot.resolve(
                    "segment-" + segment + "-validation.json").toFile());
            JsonNode assessment = evidence.path("assessment");
            JsonNode payload = evidence.path("payloadBatch");
            if (!assessment.path("traceabilityValidation").path("valid").asBoolean()) {
                add(items, counts, sequence++, segment, "CHAIN_VALIDATION_FAILURE", "HIGH",
                        "TEST-SOLUTION-" + segment, "", "",
                        assessment.path("traceabilityValidation").path("errorCount").asInt()
                                + " traceability errors require remediation.");
            }
            if (!payload.path("validatorImplemented").asBoolean()) {
                add(items, counts, sequence++, segment, "VALIDATOR_IMPLEMENTATION", "HIGH",
                        "TEST-AUTOMATION", "", "",
                        "No segment payload validator is implemented; payload compliance cannot be certified.");
            } else if (payload.path("linkedPayloadFiles").asInt() > 0
                    && payload.path("applicablePayloadFiles").asInt() == 0) {
                add(items, counts, sequence++, segment, "PAYLOAD_SEGMENT_ABSENCE", "HIGH",
                        "AI-SOLUTION", "", "",
                        "Linked data files exist, but none contain a detectable Segment " + segment + " payload.");
            }
            if (payload.path("invalidPayloadFiles").asInt() > 0) {
                add(items, counts, sequence++, segment, "PAYLOAD_VALIDATION_FAILURE", "HIGH",
                        "AI-SOLUTION", "", "",
                        payload.path("invalidPayloadFiles").asInt() + " applicable payloads failed validation.");
            }
        }

        ObjectNode summary = queue.putObject("summary");
        summary.put("totalOpenItems", items.size());
        ObjectNode byType = summary.putObject("byType");
        counts.forEach(byType::put);
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("run2-sme-review-queue.json").toFile(), queue);
        Files.writeString(outputRoot.resolve("run2-sme-review-queue.csv"), csv(items));
        Files.writeString(outputRoot.resolve("run2-sme-review-queue.md"), markdown(queue));
        System.out.println("totalOpenItems=" + items.size() + " byType=" + counts);
    }

    private static void add(ArrayNode items, Map<String, Integer> counts, int sequence,
                            String segment, String type, String priority, String owner,
                            String testRuleId, String aiRequirementIds, String reason) {
        ObjectNode item = items.addObject();
        item.put("queueId", "RUN2-Q-" + String.format("%04d", sequence));
        item.put("segment", segment);
        item.put("type", type);
        item.put("priority", priority);
        item.put("owner", owner);
        item.put("status", "OPEN");
        item.put("testRuleId", testRuleId);
        item.put("aiRequirementIds", aiRequirementIds);
        item.put("reason", reason);
        counts.merge(type, 1, Integer::sum);
    }

    private static String csv(ArrayNode items) {
        StringBuilder value = new StringBuilder(
                "queueId,segment,type,priority,owner,status,testRuleId,aiRequirementIds,reason\n");
        for (JsonNode item : items) {
            value.append(csv(item.path("queueId").asText())).append(',')
                    .append(csv(item.path("segment").asText())).append(',')
                    .append(csv(item.path("type").asText())).append(',')
                    .append(csv(item.path("priority").asText())).append(',')
                    .append(csv(item.path("owner").asText())).append(',')
                    .append(csv(item.path("status").asText())).append(',')
                    .append(csv(item.path("testRuleId").asText())).append(',')
                    .append(csv(item.path("aiRequirementIds").asText())).append(',')
                    .append(csv(item.path("reason").asText())).append('\n');
        }
        return value.toString();
    }

    private static String markdown(JsonNode queue) {
        StringBuilder value = new StringBuilder("# Run2 SME and Remediation Queue\n\n");
        value.append("Composite delivery: **").append(queue.path("sourceRun").asText()).append("**\n\n");
        value.append("Open items: **").append(queue.path("summary").path("totalOpenItems").asInt())
                .append("**\n\n");
        value.append("| Queue ID | Segment | Type | Priority | Owner | Test rule | AI requirement IDs | Reason |\n");
        value.append("|---|---|---|---|---|---|---|---|\n");
        for (JsonNode item : queue.path("items")) {
            value.append('|').append(item.path("queueId").asText()).append('|')
                    .append(item.path("segment").asText()).append('|')
                    .append(item.path("type").asText()).append('|')
                    .append(item.path("priority").asText()).append('|')
                    .append(item.path("owner").asText()).append('|')
                    .append(item.path("testRuleId").asText()).append('|')
                    .append(item.path("aiRequirementIds").asText()).append('|')
                    .append(item.path("reason").asText().replace("|", "\\|")).append("|\n");
        }
        return value.toString();
    }

    private static String join(JsonNode array) {
        StringBuilder value = new StringBuilder();
        for (JsonNode item : array) {
            if (!value.isEmpty()) value.append(';');
            value.append(item.asText());
        }
        return value.toString();
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}