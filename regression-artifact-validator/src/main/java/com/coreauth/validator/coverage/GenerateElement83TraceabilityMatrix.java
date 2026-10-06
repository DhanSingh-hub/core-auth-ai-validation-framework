package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

/** Builds code-family BR -> TS -> TC -> TD -> mapping traceability for Element 83. */
public final class GenerateElement83TraceabilityMatrix {
    private static final String MATRIX = "docs/specs/kb/element-83-response-code/coverage/element-83-response-code-br-validation-matrix.json";
    private static final String RESPONSE_PACKAGE = "test-output/test-json/segment-100-response-code-package.json";
    private static final String OUTPUT_JSON = "test-output/test-solution-independent-review/element-83-traceability-matrix.json";
    private static final String OUTPUT_CSV = "test-output/test-solution-independent-review/element-83-traceability-matrix.csv";

    private GenerateElement83TraceabilityMatrix() { }

    public static void main(String[] args) throws Exception {
        Path pack = Atl105Paths.root();
        if (args.length > 0) pack = Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseMatrix = mapper.readTree(pack.resolve(MATRIX).toFile());
        JsonNode responsePackage = mapper.readTree(pack.resolve(RESPONSE_PACKAGE).toFile());
        JsonNode complete = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json").toFile());
        JsonNode fourLevel = mapper.readTree(pack.resolve("test-output/ai-solution-independent-review/four-level-matches/four-level-traceability-matches.json").toFile());
        Map<String, JsonNode> scenarios = index(complete.path("testScenarios"));
        Map<String, JsonNode> cases = index(complete.path("testCases"));
        Map<String, JsonNode> data = index(complete.path("testData"));
        Map<String, JsonNode> responseScenarios = index(responsePackage.path("testScenarios"));
        Map<String, JsonNode> responseCases = index(responsePackage.path("testCases"));
        Map<String, JsonNode> responseData = index(responsePackage.path("testData"));
        Map<String, JsonNode> responseBrs = index(responsePackage.path("businessRequirements"));
        Map<String, List<JsonNode>> mappingsByRequirement = new HashMap<>();
        for (JsonNode match : fourLevel.path("businessRequirements"))
            mappingsByRequirement.computeIfAbsent(match.path("testRequirementId").asText(""), key -> new ArrayList<>()).add(match);

        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-ELEMENT-83-BR-TS-TC-TD-MAPPING-MATRIX");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("element", "83");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION");
        output.put("humanReviewRequired", true);
        output.put("policy", "One row per source-defined valid code-family BR. BR presence comes from the source matrix; TS/TC/TD links require an exact Element 83 response-code fixture. AI matches remain candidates unless the four-level report marks them CONFIRMED.");
        output.put("sourceBrMatrix", MATRIX);
        output.put("responseCodeTestPackage", RESPONSE_PACKAGE);
        output.put("canonicalChainPackage", "test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json");
        output.put("aiMappingSource", "test-output/ai-solution-independent-review/four-level-matches/four-level-traceability-matches.json");
        ArrayNode rows = output.putArray("rows");
        Map<String, Integer> counts = new TreeMap<>();

        for (JsonNode meaning : responseMatrix.path("allowedCodeFamilyMeanings")) {
            String code = meaning.path("code").asText();
            String family = meaning.path("family").asText();
            String brId = meaning.path("businessRequirementId").asText();
            ObjectNode row = rows.addObject();
            row.put("element", "83");
            row.put("expectedResponseCode", code);
            row.put("responseFamily", family);
            row.put("meaning", meaning.path("meaning").asText());
            row.put("element83BusinessRequirementId", brId);
            row.put("element83BusinessRequirementStatus", "SOURCE_DERIVED_REVIEW_REQUIRED");
            row.putArray("testSolutionResponseBusinessRequirements");
            ArrayNode scenarioIds = row.putArray("testScenarios");
            ArrayNode caseIds = row.putArray("testCases");
            ArrayNode dataIds = row.putArray("testData");
            ArrayNode mappingCandidates = row.putArray("aiMappingCandidates");
            String mappingStatus = "NO_CONFIRMED_MAPPING";
            int exactData = 0;

            for (JsonNode td : responsePackage.path("testData")) {
                if (!hasResponseCode(td.path("payload"), code) || !hasResponseFamily(td.path("payload"), family)
                    || !hasElement83Anchor(td.path("sourceAnchors"))) continue;
                exactData++;
                String tdId = td.path("id").asText();
                dataIds.add(tdId);
                for (JsonNode tcRef : td.path("testCaseIds")) {
                    JsonNode tc = responseCases.get(tcRef.asText());
                    if (tc == null) continue;
                    String tcId = tc.path("id").asText();
                    if (!contains(caseIds, tcId)) caseIds.add(tcId);
                    for (JsonNode tsRef : tc.path("scenarioIds")) {
                        JsonNode ts = responseScenarios.get(tsRef.asText());
                        if (ts == null) continue;
                        String tsId = ts.path("id").asText();
                        if (!contains(scenarioIds, tsId)) scenarioIds.add(tsId);
                        for (JsonNode requirement : ts.path("requirementIds")) {
                            JsonNode br = responseBrs.get(requirement.asText());
                            if (br == null) continue;
                            addUnique(row.withArray("testSolutionResponseBusinessRequirements"), br.path("id").asText());
                            for (JsonNode match : mappingsByRequirement.getOrDefault(br.path("id").asText(), List.of())) {
                                String status = match.path("matchStatus").asText("REVIEW_REQUIRED");
                                for (JsonNode ai : match.path("aiBusinessRequirements")) addUnique(mappingCandidates, ai.path("id").asText());
                                if ("CONFIRMED".equals(status)) mappingStatus = "CONFIRMED";
                                else if (!"CONFIRMED".equals(mappingStatus) && !match.path("aiBusinessRequirements").isEmpty()) mappingStatus = "REVIEW_REQUIRED_CANDIDATE";
                            }
                        }
                    }
                }
            }
            boolean brLinked = !row.path("testSolutionResponseBusinessRequirements").isEmpty();
            boolean tsLinked = !scenarioIds.isEmpty();
            boolean tcLinked = !caseIds.isEmpty();
            boolean tdLinked = !dataIds.isEmpty();
            row.put("testSolutionBrStatus", brLinked ? "LINKED" : "NO_CODE_SPECIFIC_BR_CHAIN");
            row.put("testScenarioStatus", tsLinked ? "LINKED" : "MISSING");
            row.put("testCaseStatus", tcLinked ? "LINKED" : "MISSING");
            row.put("testDataStatus", tdLinked ? "LINKED_EXACT_RESPONSE_CODE" : "MISSING_EXACT_RESPONSE_CODE_DATA");
            row.put("exactResponseCodeTestDataCount", exactData);
            row.put("aiMappingStatus", mappingStatus);
            row.put("chainCompleteThroughTestData", brLinked && tsLinked && tcLinked && tdLinked);
            counts.merge("validCodeFamilyBrs", 1, Integer::sum);
            if (brLinked) counts.merge("rowsWithTestSolutionResponseBr", 1, Integer::sum);
            if (tsLinked) counts.merge("rowsWithTestScenario", 1, Integer::sum);
            if (tcLinked) counts.merge("rowsWithTestCase", 1, Integer::sum);
            if (tdLinked) counts.merge("rowsWithExactCodeTestData", 1, Integer::sum);
            if (row.path("chainCompleteThroughTestData").asBoolean()) counts.merge("completeBrTsTcTdChains", 1, Integer::sum);
            if ("CONFIRMED".equals(mappingStatus)) counts.merge("confirmedAiMappings", 1, Integer::sum);
            if ("REVIEW_REQUIRED_CANDIDATE".equals(mappingStatus)) counts.merge("mappingReviewCandidates", 1, Integer::sum);
        }
        output.set("summary", mapper.valueToTree(counts));
        Path jsonPath = pack.resolve(OUTPUT_JSON);
        Path csvPath = pack.resolve(OUTPUT_CSV);
        Files.createDirectories(jsonPath.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonPath.toFile(), output);
        writeCsv(csvPath, rows);
        System.out.printf("validCodeFamilyBRs=%d exactCodeTD=%d completeChains=%d confirmedMappings=%d json=%s csv=%s%n",
            counts.getOrDefault("validCodeFamilyBrs", 0), counts.getOrDefault("rowsWithExactCodeTestData", 0),
            counts.getOrDefault("completeBrTsTcTdChains", 0), counts.getOrDefault("confirmedAiMappings", 0), jsonPath, csvPath);
    }

    private static boolean hasResponseCode(JsonNode node, String code) {
        if (node.isObject()) {
            var fields = node.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                if (field.getKey().equalsIgnoreCase("ResponseCode") && field.getValue().isValueNode()
                    && code.equals(field.getValue().asText())) return true;
                if (hasResponseCode(field.getValue(), code)) return true;
            }
        } else if (node.isArray()) {
            for (JsonNode child : node) if (hasResponseCode(child, code)) return true;
        }
        return false;
    }

    private static boolean hasResponseFamily(JsonNode payload, String family) {
        if (!payload.isObject()) return false;
        String expectedRoot = switch (family) {
            case "financial" -> "Financial Response";
            case "totals" -> "Totals Response";
            case "electronic-mail" -> "Electronic Mail Response";
            case "communications-test" -> "Communications Test Response";
            case "proprietary-load" -> "Proprietary Data Load Response";
            case "transarmor" -> "TransArmor Load Response";
            case "emv-key-load" -> "CA Public Key File Load Response";
            default -> "";
        };
        return !expectedRoot.isBlank() && payload.has(expectedRoot);
    }

    private static boolean hasElement83Anchor(JsonNode anchors) {
        if (!anchors.isArray()) return false;
        for (JsonNode anchor : anchors) {
            String element = anchor.path("element").asText();
            if (Pattern.compile("(?:^|[,|])\\s*83\\s*(?:$|[,|])").matcher(element).find()) return true;
        }
        return false;
    }

    private static Map<String, JsonNode> index(JsonNode array) {
        Map<String, JsonNode> result = new LinkedHashMap<>();
        if (array.isArray()) for (JsonNode item : array) result.put(item.path("id").asText(), item);
        return result;
    }

    private static boolean contains(ArrayNode values, String wanted) {
        for (JsonNode value : values) if (wanted.equals(value.asText())) return true;
        return false;
    }

    private static void addUnique(ArrayNode values, String value) {
        if (!value.isBlank() && !contains(values, value)) values.add(value);
    }

    private static void writeCsv(Path path, ArrayNode rows) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,expectedResponseCode,responseFamily,meaning,element83BusinessRequirementId,testSolutionResponseBusinessRequirements,testScenarios,testCases,testData,exactResponseCodeTestDataCount,testSolutionBrStatus,testScenarioStatus,testCaseStatus,testDataStatus,chainCompleteThroughTestData,aiMappingStatus,aiMappingCandidates");
        for (JsonNode row : rows) {
            List<String> fields = List.of(row.path("element").asText(), row.path("expectedResponseCode").asText(),
                row.path("responseFamily").asText(), row.path("meaning").asText(), row.path("element83BusinessRequirementId").asText(),
                join(row.path("testSolutionResponseBusinessRequirements")), join(row.path("testScenarios")), join(row.path("testCases")),
                join(row.path("testData")), row.path("exactResponseCodeTestDataCount").asText(), row.path("testSolutionBrStatus").asText(),
                row.path("testScenarioStatus").asText(), row.path("testCaseStatus").asText(), row.path("testDataStatus").asText(),
                row.path("chainCompleteThroughTestData").asText(), row.path("aiMappingStatus").asText(), join(row.path("aiMappingCandidates")));
            lines.add(fields.stream().map(GenerateElement83TraceabilityMatrix::csv).reduce((left, right) -> left + "," + right).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String join(JsonNode values) {
        List<String> result = new ArrayList<>();
        if (values.isArray()) values.forEach(value -> result.add(value.asText()));
        return String.join(";", result);
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
