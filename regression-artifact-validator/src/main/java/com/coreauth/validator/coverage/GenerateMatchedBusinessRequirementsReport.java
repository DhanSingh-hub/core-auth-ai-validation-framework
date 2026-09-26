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

/** Writes a complete AI-to-Test Solution comparison for every mapped BR. */
public final class GenerateMatchedBusinessRequirementsReport {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");

    private GenerateMatchedBusinessRequirementsReport() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path deliveryRoot = Atl105Paths.testInput().resolve(
                Path.of("ai-solution", "runs", "2026-09-23"));
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path outputRoot = args.length == 0
                ? reviewRoot.resolve("matched-business-requirements")
                : Path.of(args[0]);
        Files.createDirectories(outputRoot);

        Map<String, JsonNode> aiRequirements = loadAiRequirements(mapper, deliveryRoot);
        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "complete-ai-test-solution-matched-business-requirements");
        report.put("deliveryId", "ATL105-AI-2026-09-23-RUN1-RUN2");
        report.put("sourceRun", "2026-09-23/Run1+Run2");
        report.put("comparisonAuthority", "TEST_SOLUTION_REVIEW");
        report.put("scope", "Every crosswalk mapping with one or more AI requirement IDs; MISSING rules are excluded.");
        report.put("matchingPolicy", "CONFIRMED requires evidence-backed Test Solution review; REVIEW_REQUIRED remains a candidate and does not count as confirmed coverage.");

        ArrayNode matches = report.putArray("matches");
        ArrayNode segmentSummary = report.putArray("segmentSummary");
        StringBuilder csv = new StringBuilder("segment,matchStatus,testRequirementId,testTitle,testClass,testSeverity,canonicalAnchor,aiRequirementId,aiStatement,aiSourceRuleId,aiSourcePage,aiSegment,aiCategory,aiRequirementType,aiExpectedBehavior,aiConfidencePct,aiConfidenceBand,aiDerivationMethod,matchReason,reviewOwner\n");
        StringBuilder markdown = new StringBuilder("# Complete Matched Business Requirements: AI vs Test Solution\n\n")
                .append("- Delivery: `2026-09-23/Run1+Run2`\n")
                .append("- Scope: every crosswalk mapping with one or more AI requirement IDs\n")
                .append("- `CONFIRMED` mappings count toward independent coverage; `REVIEW_REQUIRED` mappings do not.\n\n");

        int confirmedMappings = 0;
        int reviewMappings = 0;
        int aiLinks = 0;
        Map<String, Integer> distinctAiIds = new LinkedHashMap<>();

        for (String segment : SEGMENTS) {
            JsonNode catalog = mapper.readTree(Atl105Paths.ruleCatalog(segment).toFile());
            Map<String, JsonNode> rules = indexRules(catalog);
            JsonNode crosswalk = mapper.readTree(reviewRoot.resolve(Path.of(
                    "run2-crosswalks", "segment-" + segment + "-run2-crosswalk.json")).toFile());
            int segmentMappings = 0;
            int segmentLinks = 0;
            int segmentConfirmed = 0;
            int segmentReview = 0;

            for (JsonNode mapping : crosswalk.path("mappings")) {
                if (!mapping.path("aiRequirementIds").elements().hasNext()) continue;
                String status = mapping.path("matchStatus").asText();
                JsonNode rule = rules.get(mapping.path("testRequirementId").asText());
                ObjectNode match = matches.addObject();
                match.put("segment", segment);
                match.put("matchStatus", status);
                match.put("matchReason", mapping.path("matchReason").asText());
                match.put("reviewOwner", mapping.path("reviewOwner").isNull()
                        ? "" : mapping.path("reviewOwner").asText());
                ObjectNode test = match.putObject("testSolution");
                test.put("requirementId", mapping.path("testRequirementId").asText());
                if (rule != null) {
                    test.put("title", rule.path("title").asText());
                    test.put("class", rule.path("class").asText());
                    test.put("severity", rule.path("severity").asText());
                    test.set("sourceAnchor", rule.path("sourceAnchor"));
                } else {
                    test.put("title", "");
                    test.put("class", "");
                    test.put("severity", "");
                    test.set("sourceAnchor", mapping.path("sourceAnchors").path(0));
                }
                test.set("crosswalkAnchors", mapping.path("sourceAnchors"));

                ArrayNode ai = match.putArray("aiSolution");
                for (JsonNode aiIdNode : mapping.path("aiRequirementIds")) {
                    String aiId = aiIdNode.asText();
                    JsonNode requirement = aiRequirements.get(aiId);
                    ObjectNode aiDetail = ai.addObject();
                    aiDetail.put("requirementId", aiId);
                    if (requirement == null) {
                        aiDetail.put("availableInRun1", false);
                    } else {
                        aiDetail.put("availableInRun1", true);
                        copy(aiDetail, requirement, "statement", "source_rule_id", "source_page",
                                "segment_number", "category", "requirement_type", "expected_behavior",
                                "confidence_pct", "confidence_band", "derivation_method",
                                "segment_assignment_method", "scope_name");
                        aiDetail.set("relatedEntityIds", requirement.path("related_entity_ids"));
                        aiDetail.set("segmentIds", requirement.path("segment_ids"));
                        aiDetail.set("flags", requirement.path("flags"));
                    }
                    distinctAiIds.put(aiId, 1);
                    aiLinks++;
                    segmentLinks++;
                    csv.append(csv(segment)).append(',').append(csv(status)).append(',')
                            .append(csv(mapping.path("testRequirementId").asText())).append(',')
                            .append(csv(rule == null ? "" : rule.path("title").asText())).append(',')
                            .append(csv(rule == null ? "" : rule.path("class").asText())).append(',')
                            .append(csv(rule == null ? "" : rule.path("severity").asText())).append(',')
                            .append(csv(anchor(mapping.path("sourceAnchors").path(0)))).append(',')
                            .append(csv(aiId)).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("statement").asText())).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("source_rule_id").asText())).append(',')
                            .append(requirement == null ? "" : requirement.path("source_page").asInt()).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("segment_number").asText())).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("category").asText())).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("requirement_type").asText())).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("expected_behavior").asText())).append(',')
                            .append(requirement == null ? "" : requirement.path("confidence_pct").asInt()).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("confidence_band").asText())).append(',')
                            .append(csv(requirement == null ? "" : requirement.path("derivation_method").asText())).append(',')
                            .append(csv(mapping.path("matchReason").asText())).append(',')
                            .append(csv(mapping.path("reviewOwner").isNull() ? "" : mapping.path("reviewOwner").asText())).append('\n');
                }

                segmentMappings++;
                if ("CONFIRMED".equals(status)) {
                    confirmedMappings++;
                    segmentConfirmed++;
                } else if ("REVIEW_REQUIRED".equals(status)) {
                    reviewMappings++;
                    segmentReview++;
                }
                appendMarkdown(markdown, segment, mapping, rule, aiRequirements);
            }

            ObjectNode segmentNode = segmentSummary.addObject();
            segmentNode.put("segment", segment);
            segmentNode.put("matchedMappings", segmentMappings);
            segmentNode.put("aiRequirementLinks", segmentLinks);
            segmentNode.put("confirmedMappings", segmentConfirmed);
            segmentNode.put("reviewRequiredMappings", segmentReview);
        }

        ObjectNode summary = report.putObject("summary");
        summary.put("matchedMappings", matches.size());
        summary.put("aiRequirementLinks", aiLinks);
        summary.put("distinctAiRequirements", distinctAiIds.size());
        summary.put("confirmedMappings", confirmedMappings);
        summary.put("reviewRequiredMappings", reviewMappings);
        summary.put("testSolutionRulesWithAiMatches", confirmedMappings + reviewMappings);

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                outputRoot.resolve("all-matched-business-requirements.json").toFile(), report);
        Files.writeString(outputRoot.resolve("all-matched-business-requirements.csv"), csv);
        Files.writeString(outputRoot.resolve("all-matched-business-requirements.md"), markdown);
        System.out.printf("matchedMappings=%d aiLinks=%d distinctAiRequirements=%d confirmed=%d reviewRequired=%d%n",
                matches.size(), aiLinks, distinctAiIds.size(), confirmedMappings, reviewMappings);
    }

    private static Map<String, JsonNode> loadAiRequirements(ObjectMapper mapper, Path deliveryRoot) throws Exception {
        JsonNode catalog = mapper.readTree(deliveryRoot.resolve(Path.of(
                "Run1", "step5_requirements", "approved", "requirement_catalog.json")).toFile());
        Map<String, JsonNode> result = new LinkedHashMap<>();
        for (JsonNode requirement : catalog.path("requirements")) {
            result.put(requirement.path("id").asText(), requirement);
        }
        return result;
    }

    private static Map<String, JsonNode> indexRules(JsonNode catalog) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        for (JsonNode rule : catalog.path("rules")) result.put(rule.path("ruleId").asText(), rule);
        return result;
    }

    private static void copy(ObjectNode target, JsonNode source, String... fields) {
        for (String field : fields) target.set(field, source.path(field));
    }

    private static void appendMarkdown(StringBuilder markdown, String segment, JsonNode mapping,
                                       JsonNode rule, Map<String, JsonNode> aiRequirements) {
        markdown.append("## Segment ").append(segment).append(" - ")
                .append(mapping.path("testRequirementId").asText()).append(" - ")
                .append(mapping.path("matchStatus").asText()).append("\n\n");
        markdown.append("**Test Solution rule:** ")
                .append(rule == null ? "" : rule.path("title").asText()).append("  \n")
                .append("**Class/severity:** ")
                .append(rule == null ? "" : rule.path("class").asText()).append(" / ")
                .append(rule == null ? "" : rule.path("severity").asText()).append("  \n")
                .append("**Canonical anchor:** `").append(anchor(mapping.path("sourceAnchors").path(0))).append("`  \n")
                .append("**Reason:** ").append(mapping.path("matchReason").asText()).append("  \n")
                .append("**Review owner:** ").append(mapping.path("reviewOwner").isNull()
                        ? "" : mapping.path("reviewOwner").asText()).append("\n\n");
        markdown.append("| AI BR | Statement | Source rule | Page | Confidence |\n")
                .append("|---|---|---|---:|---|\n");
        for (JsonNode idNode : mapping.path("aiRequirementIds")) {
            JsonNode requirement = aiRequirements.get(idNode.asText());
            markdown.append('|').append(idNode.asText()).append('|')
                    .append(cell(requirement == null ? "[Not found in Run1 catalog]" : requirement.path("statement").asText())).append('|')
                    .append(cell(requirement == null ? "" : requirement.path("source_rule_id").asText())).append('|')
                    .append(requirement == null ? "" : requirement.path("source_page").asInt()).append('|')
                    .append(requirement == null ? "" : requirement.path("confidence_pct").asInt())
                    .append("% ").append(requirement == null ? "" : requirement.path("confidence_band").asText()).append("|\n");
        }
        markdown.append('\n');
    }

    private static String anchor(JsonNode value) {
        return String.join("|", value.path("specification").asText(), value.path("version").asText(),
                value.path("section").asText(), value.path("segment").asText(),
                value.path("element").asText(), value.path("rule").asText());
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"").replace("\r", " ").replace("\n", " ") + "\"";
    }

    private static String cell(String value) {
        return value.replace("|", "\\|").replace("\n", " ");
    }
}