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

/** Generates an exhaustive Element x Appendix-G transaction-code review matrix without inferring unsupported applicability. */
public final class GenerateAtl105ElementTransactionApplicability {
    private GenerateAtl105ElementTransactionApplicability() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode reference = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-all-elements-reference.json").toFile());
        JsonNode transactionCatalog = mapper.readTree(pack.resolve("test-output/test-json/segment-100-all-23-transaction-type-training-baseline.json").toFile());
        ArrayNode rows = mapper.createArrayNode();
        ArrayNode transactions = mapper.createArrayNode();
        transactionCatalog.path("transactionTypes").forEach(tx -> transactions.add(tx.deepCopy()));
        int sourceDefined = 0;
        int conditionalReview = 0;
        int unmappedReview = 0;
        for (JsonNode element : reference.path("elements")) {
            String elementId = element.path("element").asText();
            for (JsonNode transaction : transactions) {
                String code = transaction.path("code").asText();
                ObjectNode row = rows.addObject();
                row.put("element", elementId);
                row.put("transactionTypeCode", code);
                row.put("transactionTypeName", transaction.path("name").asText());
                row.put("transactionTypeClass", transaction.path("class").asText());
                row.put("sourceAnchor", transaction.path("sourceAnchor").asText());
                row.put("segmentsWithElementEvidence", String.join(";", strings(element.path("segments"))));
                row.put("messageFamiliesWithElementEvidence", String.join(";", strings(element.path("messageFamilyContexts"), "messageFamily")));
                String status;
                String reason;
                String evidence;
                if ("78".equals(elementId)) {
                    status = "SOURCE_DEFINED_CODE_DOMAIN";
                    reason = "Appendix G transaction-type code is the transaction component of Element 78 Prompt Code.";
                    evidence = "Appendix G / Element 78 / transaction-type-" + code;
                    sourceDefined++;
                } else if (contains(element.path("segments"), "100")) {
                    status = "CONDITIONAL_FLOW_REVIEW_REQUIRED";
                    reason = "Element is associated with Segment 100, but transaction type alone does not determine companion fields or segment applicability.";
                    evidence = "Segment compatibility matrix; Appendix G transaction-type-" + code;
                    conditionalReview++;
                } else {
                    status = "NOT_EXHAUSTIVELY_MAPPED_REVIEW_REQUIRED";
                    reason = "No source-backed Element x transaction-code applicability rule is currently transcribed.";
                    evidence = "No direct element transaction-code anchor; transaction code is listed for complete review coverage only.";
                    unmappedReview++;
                }
                row.put("applicabilityStatus", status);
                row.put("reason", reason);
                row.put("evidence", evidence);
                row.put("validityDecision", "REVIEW_REQUIRED_UNLESS_STATUS_IS_SOURCE_DEFINED_CODE_DOMAIN");
            }
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-ELEMENT-TRANSACTION-APPLICABILITY-REVIEW-MATRIX");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION_SOURCE_DERIVED_REVIEW");
        output.put("humanReviewRequired", true);
        output.put("status", "EXHAUSTIVE_REVIEW_MATRIX_NOT_EXHAUSTIVE_SEMANTIC_PROOF");
        output.put("policy", "Every Element x Appendix-G transaction-code pair is represented. Only Element 78's transaction-code domain is source-defined; conditional and unmapped pairs remain REVIEW_REQUIRED. Do not interpret a review row as valid or prohibited.");
        output.put("elementCount", reference.path("elements").size());
        output.put("transactionTypeCount", transactions.size());
        output.put("pairCount", rows.size());
        output.put("sourceDefinedCodeDomainRows", sourceDefined);
        output.put("conditionalFlowReviewRows", conditionalReview);
        output.put("unmappedReviewRows", unmappedReview);
        output.set("transactionTypes", transactions);
        output.set("rows", rows);
        Path json = pack.resolve("test-output/test-solution-independent-review/atl105-element-transaction-applicability.json");
        Path csv = pack.resolve("test-output/test-solution-independent-review/atl105-element-transaction-applicability.csv");
        Files.createDirectories(json.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), output);
        writeCsv(csv, rows);
        System.out.printf("elements=%d transactions=%d pairs=%d sourceDefined=%d conditionalReview=%d unmappedReview=%d%n",
            reference.path("elements").size(), transactions.size(), rows.size(), sourceDefined, conditionalReview, unmappedReview);
    }

    private static boolean contains(JsonNode array, String value) {
        for (JsonNode item : array) if (value.equals(item.asText())) return true;
        return false;
    }

    private static List<String> strings(JsonNode values) {
        List<String> result = new ArrayList<>();
        values.forEach(value -> result.add(value.asText()));
        return result;
    }

    private static List<String> strings(JsonNode values, String property) {
        List<String> result = new ArrayList<>();
        values.forEach(value -> result.add(value.path(property).asText()));
        return result;
    }

    private static void writeCsv(Path path, ArrayNode rows) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,transactionTypeCode,transactionTypeName,transactionTypeClass,sourceAnchor,segmentsWithElementEvidence,messageFamiliesWithElementEvidence,applicabilityStatus,reason,evidence,validityDecision");
        for (JsonNode row : rows) {
            List<String> fields = List.of(row.path("element").asText(), row.path("transactionTypeCode").asText(),
                row.path("transactionTypeName").asText(), row.path("transactionTypeClass").asText(), row.path("sourceAnchor").asText(),
                row.path("segmentsWithElementEvidence").asText(), row.path("messageFamiliesWithElementEvidence").asText(),
                row.path("applicabilityStatus").asText(), row.path("reason").asText(), row.path("evidence").asText(), row.path("validityDecision").asText());
            lines.add(fields.stream().map(GenerateAtl105ElementTransactionApplicability::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
