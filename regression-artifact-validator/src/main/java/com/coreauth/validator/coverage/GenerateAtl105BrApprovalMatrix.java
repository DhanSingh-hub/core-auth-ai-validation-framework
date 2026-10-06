package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.coreauth.validator.paths.Atl105Paths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/** Creates a rule-level matrix separating authored BR linkage from SME semantic approval. */
public final class GenerateAtl105BrApprovalMatrix {
    private GenerateAtl105BrApprovalMatrix() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode coverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode approved = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/approved-atomic-test-solution-business-requirements.json").toFile());
        JsonNode decisions = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/semantic-br-decision-register.json").toFile());
        ArrayNode rows = mapper.createArrayNode();
        TreeMap<String, Integer> counts = new TreeMap<>();
        int pendingDecisions = decisions.path("decisions").size();
        int approvedNewRules = approved.path("summary").path("approvedNewRuleDecisions").asInt();
        for (JsonNode rule : coverage.path("businessRequirements")) {
            ObjectNode row = rows.addObject();
            row.put("ruleId", rule.path("id").asText());
            row.put("title", rule.path("title").asText());
            row.put("class", rule.path("class").asText());
            row.put("severity", rule.path("severity").asText());
            row.put("sourceCatalog", rule.path("sourceCatalog").asText());
            row.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
            row.put("sourceEvidenceVerified", rule.path("sourceEvidenceVerified").asBoolean());
            row.put("sourceEvidenceResolution", rule.path("sourceEvidenceResolution").asText());
            row.put("authoredBrLinkStatus", rule.path("brCoverageStatus").asText());
            row.put("authoredBrCount", rule.path("existingBusinessRequirements").size());
            row.put("negativeBrCount", rule.path("negativeCoverage").size());
            row.put("semanticApprovalStatus", "SME_APPROVAL_NOT_RECORDED");
            row.put("businessRuleStatus", "DRAFT_REVIEW_REQUIRED");
            row.put("decisionAuthority", "SME_SEMANTIC_REVIEW_REQUIRED");
            row.put("chainDerivationStatus", "NOT_APPROVED_FOR_CHAIN_DERIVATION");
            row.put("approvalEvidence", "No rule-level SME approval record was found; authored BR linkage is not approval.");
            counts.merge("rules", 1, Integer::sum);
            counts.merge(rule.path("brCoverageStatus").asText("UNKNOWN"), 1, Integer::sum);
            if (rule.path("sourceEvidenceVerified").asBoolean()) counts.merge("sourceEvidenceVerified", 1, Integer::sum);
            else counts.merge("sourceEvidenceReviewRequired", 1, Integer::sum);
            if (rule.path("existingBusinessRequirements").size() > 0) counts.merge("authoredBrLinked", 1, Integer::sum);
            if (rule.path("negativeCoverage").size() > 0) counts.merge("negativeBrLinked", 1, Integer::sum);
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-BR-AUTHORSHIP-AND-SME-APPROVAL-MATRIX");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION_SOURCE_DERIVED_REVIEW");
        output.put("status", "REVIEW_REQUIRED_UNTIL_SME_SEMANTIC_SIGNOFF");
        output.put("policy", "Authored BR linkage proves traceability to a rule candidate only. It does not prove semantic equivalence, SME approval, execution readiness or business acceptance.");
        output.put("oracleRuleCount", rows.size());
        output.put("authoredBrLinkedRuleCount", counts.getOrDefault("authoredBrLinked", 0));
        output.put("smeApprovedRuleCount", 0);
        output.put("rulesPendingSmeApproval", rows.size());
        output.put("approvedNewRuleDecisions", approvedNewRules);
        output.put("pendingDecisionRegisterEntries", pendingDecisions);
        output.put("chainDerivationAllowed", false);
        output.set("counts", mapper.valueToTree(counts));
        output.set("rows", rows);
        Path json = pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.json");
        Path csv = pack.resolve("test-output/test-solution-independent-review/atl105-br-approval-matrix.csv");
        Files.createDirectories(json.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), output);
        writeCsv(csv, rows);
        System.out.printf("rules=%d authoredLinked=%d smeApproved=0 pending=%d approvedNewRuleDecisions=%d pendingDecisions=%d%n",
            rows.size(), counts.getOrDefault("authoredBrLinked", 0), rows.size(), approvedNewRules, pendingDecisions);
    }

    private static void writeCsv(Path path, ArrayNode rows) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("ruleId,title,class,severity,sourceCatalog,sourceSection,sourceSegment,sourceElement,sourceRule,sourceEvidenceVerified,sourceEvidenceResolution,authoredBrLinkStatus,authoredBrCount,negativeBrCount,semanticApprovalStatus,businessRuleStatus,decisionAuthority,chainDerivationStatus,approvalEvidence");
        for (JsonNode row : rows) {
            List<String> fields = List.of(row.path("ruleId").asText(), row.path("title").asText(), row.path("class").asText(), row.path("severity").asText(),
                row.path("sourceCatalog").asText(), row.path("sourceAnchor").path("section").asText(), row.path("sourceAnchor").path("segment").asText(),
                row.path("sourceAnchor").path("element").asText(), row.path("sourceAnchor").path("rule").asText(), row.path("sourceEvidenceVerified").asText(),
                row.path("sourceEvidenceResolution").asText(), row.path("authoredBrLinkStatus").asText(), row.path("authoredBrCount").asText(), row.path("negativeBrCount").asText(),
                row.path("semanticApprovalStatus").asText(), row.path("businessRuleStatus").asText(), row.path("decisionAuthority").asText(), row.path("chainDerivationStatus").asText(), row.path("approvalEvidence").asText());
            lines.add(fields.stream().map(GenerateAtl105BrApprovalMatrix::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String csv(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }
}
