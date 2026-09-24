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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Triages the trained-segment AI-only BRs (Test Solution has a rule catalog for the segment, but this AI
 * requirement matches none of its rules) using one exact, non-fuzzy signal: the AI's own source_rule_id
 * (for example "ENT-ELEM-2") encodes the ATL105 element number. If that element number already appears in
 * the segment's independent rule catalog, the Test Solution already has a rule for this exact field and the
 * gap is a crosswalk-matching miss, not a missing rule. If the element number appears nowhere in the catalog,
 * this is a genuine candidate for a new independently-derived rule (or a reject, pending SME review).
 */
public final class TriageAiOnlyTrainedSegmentRequirements {
    private static final List<String> SEGMENTS = List.of(
            "100", "101", "102", "103", "104", "105", "108", "109", "111", "113");
    private static final java.util.regex.Pattern ELEMENT_ID_PATTERN = java.util.regex.Pattern.compile("(\\d+)$");

    private record Requirement(String id, String segment, String sourceRuleId, String statement, int sourcePage) { }

    private TriageAiOnlyTrainedSegmentRequirements() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        Path deliveryRoot = Atl105Paths.testInput().resolve(Path.of("ai-solution", "runs", "2026-09-23"));
        Path traceability = deliveryRoot.resolve(Path.of("Run2", "traceability_matrix_full.json"));

        Set<String> referencedAiIds = new HashSet<>();
        Map<String, Set<String>> catalogElementsBySegment = new HashMap<>();
        for (String segment : SEGMENTS) {
            Path file = reviewRoot.resolve(Path.of("run2-crosswalks", "segment-" + segment + "-run2-crosswalk.json"));
            JsonNode crosswalk = mapper.readTree(file.toFile());
            for (JsonNode mapping : crosswalk.path("mappings")) {
                for (JsonNode id : mapping.path("aiRequirementIds")) referencedAiIds.add(id.asText());
            }
            JsonNode catalog = mapper.readTree(Atl105Paths.ruleCatalog(segment).toFile());
            Set<String> elements = new HashSet<>();
            for (JsonNode rule : catalog.path("rules")) {
                String element = rule.path("sourceAnchor").path("element").asText("");
                if (!element.isBlank()) elements.add(element);
            }
            catalogElementsBySegment.put(segment, elements);
        }

        List<Requirement> all = new ArrayList<>();
        try (JsonParser parser = mapper.getFactory().createParser(traceability.toFile())) {
            require(parser.nextToken() == JsonToken.START_OBJECT, "root must be an object");
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if (!"requirements".equals(field)) {
                    parser.skipChildren();
                    continue;
                }
                require(parser.currentToken() == JsonToken.START_ARRAY, "requirements must be an array");
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    JsonNode requirement = mapper.readTree(parser);
                    String segment = requirement.path("segment_id").asText("UNKNOWN").replaceFirst("^ENT-SEG-", "");
                    if (!SEGMENTS.contains(segment)) continue;
                    all.add(new Requirement(
                            requirement.path("requirement_id").asText(),
                            segment,
                            requirement.path("source_rule_id").asText(""),
                            requirement.path("statement").asText(""),
                            requirement.path("source_page").asInt(-1)));
                }
            }
        }

        Map<String, List<Requirement>> unmatchedBySegment = new TreeMap<>();
        Map<String, Long> elementInCatalogBySegment = new TreeMap<>();
        Map<String, Long> elementNotInCatalogBySegment = new TreeMap<>();
        StringBuilder csv = new StringBuilder(
                "requirement_id,segment,source_rule_id,inferred_element,category,triage_priority,source_page,statement\n");

        for (Requirement requirement : all) {
            if (referencedAiIds.contains(requirement.id())) continue;
            java.util.regex.Matcher matcher = ELEMENT_ID_PATTERN.matcher(requirement.sourceRuleId());
            String inferredElement = matcher.find() ? matcher.group(1) : "";
            Set<String> catalogElements = catalogElementsBySegment.getOrDefault(requirement.segment(), Set.of());
            boolean elementInCatalog = !inferredElement.isBlank() && catalogElements.contains(inferredElement);
            String category = elementInCatalog ? "ELEMENT_IN_CATALOG_BUT_UNLINKED" : "ELEMENT_NOT_IN_CATALOG_NEW_RULE_CANDIDATE";
            String priority = elementInCatalog ? "LOW_REMATCH" : "HIGH_SME_REVIEW";
            unmatchedBySegment.computeIfAbsent(requirement.segment(), ignored -> new ArrayList<>()).add(requirement);
            if (elementInCatalog) elementInCatalogBySegment.merge(requirement.segment(), 1L, Long::sum);
            else elementNotInCatalogBySegment.merge(requirement.segment(), 1L, Long::sum);
            csv.append(csv(requirement.id())).append(',').append(csv(requirement.segment())).append(',')
                    .append(csv(requirement.sourceRuleId())).append(',').append(csv(inferredElement)).append(',')
                    .append(category).append(',').append(priority).append(',').append(requirement.sourcePage()).append(',')
                    .append(csv(requirement.statement())).append('\n');
        }

        ObjectNode summary = mapper.createObjectNode();
        summary.put("artifact", "ai-only-trained-segment-triage");
        summary.put("policy", "ELEMENT_IN_CATALOG_BUT_UNLINKED: the AI's source_rule_id (e.g. ENT-ELEM-2) resolves to an "
                + "element number that already has a Test Solution rule in this segment's catalog; the gap is a "
                + "crosswalk-matching miss, priority LOW_REMATCH. ELEMENT_NOT_IN_CATALOG_NEW_RULE_CANDIDATE: no rule in "
                + "the segment's catalog references this element number at all; priority HIGH_SME_REVIEW to decide "
                + "new-rule-candidate vs. AI over-slicing vs. reject.");
        ArrayNode bySegment = summary.putArray("bySegment");
        long totalInCatalog = 0;
        long totalNotInCatalog = 0;
        for (String segment : SEGMENTS) {
            long inCatalog = elementInCatalogBySegment.getOrDefault(segment, 0L);
            long notInCatalog = elementNotInCatalogBySegment.getOrDefault(segment, 0L);
            totalInCatalog += inCatalog;
            totalNotInCatalog += notInCatalog;
            ObjectNode row = bySegment.addObject();
            row.put("segment", segment);
            row.put("elementInCatalogButUnlinked", inCatalog);
            row.put("elementNotInCatalogNewRuleCandidate", notInCatalog);
            row.put("total", inCatalog + notInCatalog);
        }
        summary.put("totalElementInCatalogButUnlinked", totalInCatalog);
        summary.put("totalElementNotInCatalogNewRuleCandidate", totalNotInCatalog);

        Files.createDirectories(reviewRoot);
        mapper.writerWithDefaultPrettyPrinter().writeValue(
                reviewRoot.resolve("ai-only-trained-segment-triage.json").toFile(), summary);
        Files.writeString(reviewRoot.resolve("ai-only-trained-segment-triage.csv"), csv);
        System.out.printf("totalElementInCatalogButUnlinked=%d totalElementNotInCatalogNewRuleCandidate=%d%n",
                totalInCatalog, totalNotInCatalog);
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"").replace("\r", " ").replace("\n", " ") + "\"";
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}
