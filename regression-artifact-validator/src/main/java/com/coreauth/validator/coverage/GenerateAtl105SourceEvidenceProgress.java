package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Rebuilds and reconciles review-only rule evidence from the independent source gates. */
public final class GenerateAtl105SourceEvidenceProgress {
    private GenerateAtl105SourceEvidenceProgress() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        String[] argument = {pack.toString()};
        GenerateAtl105SourceBackedRuleGate.main(argument);
        GenerateAtl105Element84WidthGate.main(argument);
        GenerateAtl105FixedSegmentTypeGate.main(argument);
        GenerateAtl105Segment110FieldEvidence.main(argument);

        Path directory = pack.resolve("test-output/test-solution-independent-review");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode coverage = mapper.readTree(directory.resolve("knowledge-base-br-coverage-package.json").toFile());
        JsonNode approval = mapper.readTree(directory.resolve("atl105-br-approval-matrix.json").toFile());
        JsonNode element83 = mapper.readTree(directory.resolve("atl105-source-backed-rule-gate.json").toFile());
        JsonNode element84 = mapper.readTree(directory.resolve("atl105-element-84-width-gate.json").toFile());
        JsonNode segmentType = mapper.readTree(directory.resolve("atl105-fixed-segment-type-gate.json").toFile());
        JsonNode segment110 = mapper.readTree(directory.resolve("atl105-segment-110-field-evidence.json").toFile());
        Map<String, JsonNode> rules = new LinkedHashMap<>();
        for (JsonNode rule : coverage.path("businessRequirements")) {
            String id = rule.path("id").asText();
            if (id.isBlank() || rules.putIfAbsent(id, rule) != null) throw new IllegalStateException("Duplicate or blank rule id " + id);
        }
        int denominator = rules.size();
        if (denominator == 0 || approval.path("oracleRuleCount").asInt(-1) != denominator) {
            throw new IllegalStateException("Catalog and approval denominators disagree");
        }
        String version = coverage.path("specificationVersion").asText();
        for (JsonNode gate : new JsonNode[]{element83, element84, segmentType, segment110}) {
            if (!version.equals(gate.path("specificationVersion").asText())
                || denominator != gate.path("summary").path("catalogRuleCount").asInt(-1)) {
                throw new IllegalStateException("Source-gate version or denominator mismatch");
            }
        }
        Set<String> approvedIds = new HashSet<>();
        for (JsonNode row : approval.path("rows")) if (!approvedIds.add(row.path("ruleId").asText())) {
            throw new IllegalStateException("Duplicate approval rule ID");
        }
        if (!approvedIds.equals(rules.keySet())) throw new IllegalStateException("Approval matrix rule identities differ");

        Map<String, ObjectNode> rows = new LinkedHashMap<>();
        for (Map.Entry<String, JsonNode> entry : rules.entrySet()) {
            ObjectNode row = mapper.createObjectNode();
            row.put("ruleId", entry.getKey());
            row.put("title", entry.getValue().path("title").asText());
            String catalog = entry.getValue().path("sourceCatalog").asText();
            if (!catalog.matches("segment-[^/]+/coverage/[^/]+")) {
                throw new IllegalStateException("Missing segment catalog provenance for " + entry.getKey());
            }
            row.put("segment", catalog.substring("segment-".length(), catalog.indexOf('/')));
            row.put("citationResolved", entry.getValue().path("sourceEvidenceVerified").asBoolean());
            row.put("sourceBackedAssertionCount", 0);
            row.put("draftCaseCount", 0);
            row.put("smeApprovalStatus", "SME_APPROVAL_NOT_ESTABLISHED");
            row.putArray("evidenceScopes");
            rows.put(entry.getKey(), row);
        }
        Map<String, Integer> casesByRule = new HashMap<>();
        Set<String> sourceMismatches = new HashSet<>();
        for (JsonNode gate : new JsonNode[]{element83, element84, segmentType, segment110}) {
            for (JsonNode assertion : gate.path("assertions")) {
                String id = assertion.path("ruleId").asText();
                ObjectNode row = rows.get(id);
                if (row == null) throw new IllegalStateException("Gate cites unknown rule " + id);
                if (assertion.path("status").asText().contains("SOURCE_MISMATCH")) {
                    sourceMismatches.add(id);
                    continue;
                }
                row.put("sourceBackedAssertionCount", row.path("sourceBackedAssertionCount").asInt() + 1);
                String scope = assertion.path("claimScope").asText("PARTIAL_FIXED_SEGMENT_TYPE");
                ArrayNode scopes = (ArrayNode) row.path("evidenceScopes");
                boolean alreadyPresent = false;
                for (JsonNode existing : scopes) if (scope.equals(existing.asText())) alreadyPresent = true;
                if (!alreadyPresent) scopes.add(scope);
            }
            for (JsonNode testCase : gate.path("draftCases")) {
                if (!rows.containsKey(testCase.path("ruleId").asText()) || testCase.path("executionReady").asBoolean()) {
                    throw new IllegalStateException("Unexpected draft case identity or readiness");
                }
                casesByRule.merge(testCase.path("ruleId").asText(), 1, Integer::sum);
            }
        }
        ArrayNode resultRows = mapper.createArrayNode();
        Map<String, int[]> bySegment = new java.util.TreeMap<>();
        int backedRules = 0;
        int draftCases = 0;
        for (Map.Entry<String, ObjectNode> entry : rows.entrySet()) {
            ObjectNode row = entry.getValue();
            int count = casesByRule.getOrDefault(entry.getKey(), 0);
            row.put("draftCaseCount", count);
            draftCases += count;
            if (row.path("sourceBackedAssertionCount").asInt() > 0) backedRules++;
            row.put("status", sourceMismatches.contains(entry.getKey()) ? "SOURCE_MISMATCH_REVIEW_REQUIRED"
                : row.path("sourceBackedAssertionCount").asInt() > 0 ? "PARTIAL_SOURCE_EVIDENCE_REVIEW_REQUIRED"
                : "NOT_CURATED_REVIEW_REQUIRED");
            int[] counts = bySegment.computeIfAbsent(row.path("segment").asText(), ignored -> new int[4]);
            counts[0]++;
            if (row.path("sourceBackedAssertionCount").asInt() > 0) counts[1]++;
            if (sourceMismatches.contains(entry.getKey())) counts[2]++;
            if (row.path("sourceBackedAssertionCount").asInt() == 0) counts[3]++;
            resultRows.add(row);
        }
        ObjectNode result = mapper.createObjectNode();
        result.put("artifact", "ATL105-SOURCE-EVIDENCE-PROGRESS");
        result.put("specificationVersion", version);
        result.put("status", "REVIEW_REQUIRED_NOT_EXECUTION_CERTIFIED");
        result.put("policy", "Evidence scopes are partial claims. Citation resolution, draft examples and reviewer approval are separate measures.");
        ObjectNode summary = result.putObject("summary");
        summary.put("catalogRuleCount", denominator);
        summary.put("rulesWithAnySourceBackedAssertion", backedRules);
        summary.put("rulesWithoutSourceBackedAssertions", denominator - backedRules);
        summary.put("rulesWithSourceMismatch", sourceMismatches.size());
        summary.put("draftCaseCount", draftCases);
        summary.put("smeApprovedRuleCount", approval.path("smeApprovedRuleCount").asInt());
        summary.put("executionCertifiedRuleCount", 0);
        ArrayNode segments = result.putArray("segments");
        StringBuilder segmentTable = new StringBuilder("\n## Segment Review Backlog\n\n"
            + "| Catalog | Rules | Partial evidence | Source mismatches | No backed assertion |\n"
            + "|---|---:|---:|---:|---:|\n");
        for (Map.Entry<String, int[]> entry : bySegment.entrySet()) {
            int[] counts = entry.getValue();
            segments.addObject().put("segment", entry.getKey()).put("catalogRules", counts[0])
                .put("partiallyBackedRules", counts[1]).put("sourceMismatches", counts[2])
                .put("rulesWithoutBackedAssertion", counts[3]);
            segmentTable.append('|').append(entry.getKey()).append('|').append(counts[0]).append('|')
                .append(counts[1]).append('|').append(counts[2]).append('|').append(counts[3]).append("|\n");
        }
        result.set("rules", resultRows);
        mapper.writerWithDefaultPrettyPrinter().writeValue(directory.resolve("atl105-source-evidence-progress.json").toFile(), result);
        Files.writeString(directory.resolve("atl105-source-evidence-progress.md"), "# ATL105 Source Evidence Progress\n\n"
            + "| Measure | Rules |\n|---|---:|\n"
            + "| Catalog denominator | " + denominator + " |\n"
            + "| Any backed partial assertion | " + backedRules + " |\n"
            + "| No backed assertion | " + (denominator - backedRules) + " |\n"
            + "| Source mismatch needing review | " + sourceMismatches.size() + " |\n"
            + "| Review-only draft cases | " + draftCases + " |\n"
            + "| SME-approved rules | " + summary.path("smeApprovedRuleCount").asInt() + " |\n"
            + "| Execution-certified rules | 0 |\n\n"
            + "This measures partial evidence, not complete semantic training. Resolve mismatches and curate the remaining rules against ATL105 without inferring approval.\n"
            + segmentTable);
        System.out.printf("rules=%d backedPartial=%d remaining=%d mismatched=%d drafts=%d%n",
            denominator, backedRules, denominator - backedRules, sourceMismatches.size(), draftCases);
    }
}