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

/** Consolidates verified status for the six all-element limitations. */
public final class GenerateAtl105RemainingLimitationsStatus {
    private GenerateAtl105RemainingLimitationsStatus() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode reference = read(mapper, pack, "test-output/test-solution-independent-review/atl105-all-elements-reference.json");
        JsonNode transactions = read(mapper, pack, "test-output/test-solution-independent-review/atl105-element-transaction-applicability.json");
        JsonNode omissions = read(mapper, pack, "test-output/test-solution-independent-review/atl105-element-omission-matrix.json");
        JsonNode approval = read(mapper, pack, "test-output/test-solution-independent-review/atl105-br-approval-matrix.json");
        JsonNode dependencies = read(mapper, pack, "test-output/test-solution-independent-review/atl105-dependency-absence-review-matrix.json");
        JsonNode chains = read(mapper, pack, "test-output/test-solution-independent-review/atl105-element-chain-coverage.json");
        ArrayNode limitations = mapper.createArrayNode();
        add(limitations, "transaction-type-applicability", "EXHAUSTIVE_REVIEW_MATRIX_COMPLETE_SEMANTIC_MAPPING_PENDING",
            "Every Element x Appendix-G transaction-code pair is represented, but only Element 78 has source-defined transaction-code domain semantics.",
            "atl105-element-transaction-applicability.json", transactions.path("pairCount").asInt(), transactions.path("sourceDefinedCodeDomainRows").asInt(), transactions.path("pairCount").asInt() - transactions.path("sourceDefinedCodeDomainRows").asInt());
        add(limitations, "element-family-segment-omissions", "OMISSION_CLASSIFICATION_COMPLETE_NOT_PROHIBITION_PROOF",
            "Active template fields, listed-but-not-field rows and segment-not-listed rows are separated; omissions remain review-required.",
            "atl105-element-omission-matrix.json", omissions.path("rowCount").asInt(), omissions.path("counts").path("CALLED_IN_ACTIVE_TEMPLATE").asInt(), omissions.path("counts").path("NOT_LISTED_REVIEW_REQUIRED").asInt() + omissions.path("counts").path("NOT_CALLED_NOT_ESTABLISHED").asInt());
        add(limitations, "element-118-context", "CONTEXT_MATRIX_COMPLETE_SEMANTIC_REVIEW_REQUIRED",
            "Element 118 is separated into Segment 112, 130 and 131 meanings with distinct repetition and byte caps.",
            "element-118-context-matrix.json", 3, 3, 3);
        add(limitations, "br-sme-approval", "AUTHORED_LINKAGE_COMPLETE_SME_APPROVAL_PENDING",
            "All oracle rules have authored BR linkage, but no SME semantic approval is recorded.",
            "atl105-br-approval-matrix.json", approval.path("oracleRuleCount").asInt(), approval.path("authoredBrLinkedRuleCount").asInt(), approval.path("rulesPendingSmeApproval").asInt());
        add(limitations, "dependency-absence-review", "CANDIDATE_MATRIX_COMPLETE_ENFORCEMENT_BLOCKED",
            "Dependency and explicit absence candidates are cataloged, but no semantic/SME approval or enforcement is recorded.",
            "atl105-dependency-absence-review-matrix.json", dependencies.path("candidateCount").asInt(), dependencies.path("semanticApprovedCount").asInt(), dependencies.path("reviewRequiredCount").asInt());
        add(limitations, "br-ts-tc-td-execution", chains.path("structuralCompleteRules").asInt() == chains.path("oracleRulesWithElementAnchors").asInt()
                ? "STRUCTURAL_PLACEHOLDER_CHAINS_COMPLETE_EXECUTABLE_COVERAGE_MISSING" : "STRUCTURAL_CHAIN_PARTIAL_EXECUTABLE_COVERAGE_MISSING",
            "Generated structural chains may include placeholders; these do not establish executable coverage or execution certification.",
            "atl105-element-chain-coverage.json", chains.path("oracleRulesWithElementAnchors").asInt(), chains.path("executableCompleteRules").asInt(), chains.path("oracleRulesWithElementAnchors").asInt() - chains.path("executableCompleteRules").asInt());

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-REMAINING-LIMITATIONS-STATUS");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        output.put("humanReviewRequired", true);
        output.put("policy", "Complete means the review artifact and validator boundary exist. It does not mean SME approval, semantic certification or execution readiness.");
        output.set("limitations", limitations);
        Path json = pack.resolve("test-output/test-solution-independent-review/atl105-remaining-limitations-status.json");
        Path md = pack.resolve("test-output/test-solution-independent-review/atl105-remaining-limitations-status.md");
        Files.createDirectories(json.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), output);
        Files.writeString(md, markdown(limitations));
        System.out.println("limitations=" + limitations.size() + " output=" + json);
    }

    private static JsonNode read(ObjectMapper mapper, Path pack, String relative) throws Exception {
        return mapper.readTree(pack.resolve(relative).toFile());
    }

    private static void add(ArrayNode array, String id, String status, String meaning, String artifact, int denominator, int completed, int remaining) {
        ObjectNode row = array.addObject();
        row.put("id", id);
        row.put("status", status);
        row.put("meaning", meaning);
        row.put("artifact", artifact);
        row.put("denominator", denominator);
        row.put("completedOrMeasured", completed);
        row.put("remainingOrReview", remaining);
    }

    private static String markdown(ArrayNode limitations) {
        StringBuilder text = new StringBuilder("# ATL105 Remaining Limitations Status\n\n");
        text.append("This report verifies whether each limitation has a review artifact and an explicit validator boundary. It does not claim SME approval or execution readiness.\n\n");
        text.append("| Limitation | Status | Denominator | Measured/completed | Remaining/review |\n|---|---|---:|---:|---:|\n");
        for (JsonNode row : limitations) text.append('|').append(row.path("id").asText()).append('|').append(row.path("status").asText()).append('|')
            .append(row.path("denominator").asInt()).append('|').append(row.path("completedOrMeasured").asInt()).append('|').append(row.path("remainingOrReview").asInt()).append("|\n");
        text.append("\n");
        for (JsonNode row : limitations) text.append("- **").append(row.path("id").asText()).append("**: ").append(row.path("meaning").asText()).append(" Artifact: `").append(row.path("artifact").asText()).append("`.\n");
        return text.toString();
    }
}
