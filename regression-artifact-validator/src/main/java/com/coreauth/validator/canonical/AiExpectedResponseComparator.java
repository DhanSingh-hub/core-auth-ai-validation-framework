package com.coreauth.validator.canonical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class AiExpectedResponseComparator {
    public enum Status { AGREE, DISAGREE, AI_DEFECT, ORACLE_GAP }
    public record Comparison(Status status, Status oracleStatus, String oracleFamily, boolean declarationConflict, String reason) { }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Map<String, String> RESPONSE_FAMILIES = Map.ofEntries(
        Map.entry("Financial Transaction Request", "financial"),
        Map.entry("Financial Transaction Response", "financial"),
        Map.entry("EMV Financial Transaction Request", "financial"),
        Map.entry("EMV Financial Transaction Response", "financial"),
        Map.entry("Totals Request", "totals"),
        Map.entry("Totals with Proprietary Data Load Request", "totals"),
        Map.entry("Approved Totals Response", "totals"),
        Map.entry("Declined Totals Response", "totals"),
        Map.entry("Electronic Mail Request", "electronic-mail"),
        Map.entry("Electronic Mail Response", "electronic-mail"),
        Map.entry("Communications Test Request", "communications-test"),
        Map.entry("Communications Test Response", "communications-test"),
        Map.entry("Proprietary Data Load Request", "proprietary-load"),
        Map.entry("Proprietary Data Load Response", "proprietary-load"),
        Map.entry("CA Public Key File Load Request", "emv-key-load"),
        Map.entry("CA Public Key File Load Response", "emv-key-load"),
        Map.entry("TransArmor Key and Key ID Load Request", "transarmor")
    );

    private final Atl105ResponseCodeFamilyOracle oracle = new Atl105ResponseCodeFamilyOracle();

    public Comparison compare(String transaction, JsonNode expectedCode) {
        String family = RESPONSE_FAMILIES.get(transaction);
        if (family == null) return result(Status.ORACLE_GAP, "", "No independently mapped response family for transaction label");
        if (expectedCode == null || expectedCode.isMissingNode() || expectedCode.isNull())
            return result(Status.ORACLE_GAP, family, "Expected response code is not declared");
        if (!expectedCode.isTextual() || expectedCode.asText().isBlank() || expectedCode.asText().length() != 1)
            return result(Status.AI_DEFECT, family, "Expected response code must be one non-empty character");
        if (!oracle.validate(family, expectedCode.asText()).errors().isEmpty())
            return result(Status.DISAGREE, family, "Expected code is not allowed by the independent family oracle");
        return result(Status.AGREE, family, "Code is allowed for this family; exact transaction outcome is not established");
    }

    public Comparison compareTestCase(JsonNode testCase, JsonNode scenario) {
        JsonNode expected = testCase.path("expected_response");
        if (expected.isMissingNode() || expected.isNull())
            return result(Status.ORACLE_GAP, "", "Test case expected response is absent; scenario declaration is not a TC assertion");
        if (!expected.isObject()) return result(Status.AI_DEFECT, "", "Test case expected response must be an object");
        JsonNode responseCode = expected.path("response_code");
        JsonNode code = responseCode.isObject() ? responseCode.path("value") : responseCode;
        if (code.isMissingNode() || code.isNull())
            return result(Status.ORACLE_GAP, "", "Test case expected response code is absent");
        JsonNode scenarioCode = scenario == null ? null : scenario.path("expected_response_code");
        Comparison oracleResult = compare(scenario == null ? "" : scenario.path("target_transaction").asText(""), code);
        boolean conflict = scenarioCode != null && !scenarioCode.isMissingNode() && !scenarioCode.isNull()
            && (!scenarioCode.isTextual() || !scenarioCode.equals(code));
        if (!conflict) return oracleResult;
        return new Comparison(Status.AI_DEFECT, oracleResult.oracleStatus(), oracleResult.oracleFamily(), true,
            "Test case and scenario expected response codes conflict; test-case oracle status is " + oracleResult.oracleStatus());
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) throw new IllegalArgumentException(
            "Usage: AiExpectedResponseComparator <ATL105-pack> <approved_scenarios.json> <test_case_candidates.json> <report.json>");
        Path pack = Path.of(args[0]);
        Path scenarioFile = Path.of(args[1]);
        Path testCaseFile = Path.of(args[2]);
        Path output = Path.of(args[3]);
        Path oracleMatrixFile = pack.resolve("test-output/test-json/atl105-response-code-family-matrix.json");
        if (!Files.isRegularFile(scenarioFile) || !Files.isRegularFile(testCaseFile))
            throw new IOException("Scenario and test-case catalog files must exist");
        if (!Files.isRegularFile(oracleMatrixFile)) throw new IOException("Independent response-code matrix is missing");
        if (Files.exists(output)) throw new IOException("Refusing to overwrite existing report: " + output);

        validateOracleMatrix(oracleMatrixFile);
        JsonNode scenarioCatalog = MAPPER.readTree(scenarioFile.toFile());
        if (!scenarioCatalog.path("scenarios").isArray()) throw new IOException("Scenario catalog has no scenarios array");
        AiExpectedResponseComparator comparator = new AiExpectedResponseComparator();
        Map<String, JsonNode> scenarios = new LinkedHashMap<>();
        Map<String, Long> scenarioCounts = new TreeMap<>();
        ArrayNode scenarioResults = MAPPER.createArrayNode();
        for (JsonNode scenario : scenarioCatalog.path("scenarios")) {
            String id = scenario.path("id").asText("");
            if (id.isBlank() || scenarios.putIfAbsent(id, scenario) != null) throw new IOException("Scenario id missing or duplicated: " + id);
            Comparison result = comparator.compare(scenario.path("target_transaction").asText(""), scenario.get("expected_response_code"));
            record(scenarioCounts, result.status());
            ObjectNode row = scenarioResults.addObject();
            row.put("scenarioId", id);
            row.put("transaction", scenario.path("target_transaction").asText(""));
            if (scenario.hasNonNull("expected_response_code")) row.set("expectedResponseCode", scenario.get("expected_response_code").deepCopy());
            if (scenario.hasNonNull("oracle_authority")) row.put("aiOracleAuthority", scenario.path("oracle_authority").asText());
            writeResult(row, result);
        }

        Map<String, Long> caseCounts = new TreeMap<>();
        Map<String, Long> caseOracleCounts = new TreeMap<>();
        ArrayNode caseResults = MAPPER.createArrayNode();
        int testCases = 0;
        try (JsonParser parser = MAPPER.getFactory().createParser(testCaseFile.toFile())) {
            if (parser.nextToken() != JsonToken.START_OBJECT) throw new IOException("Test-case catalog must be a JSON object");
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String field = parser.currentName();
                parser.nextToken();
                if (!"test_cases".equals(field)) {
                    parser.skipChildren();
                    continue;
                }
                if (parser.currentToken() != JsonToken.START_ARRAY) throw new IOException("test_cases must be an array");
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    JsonNode testCase = MAPPER.readTree(parser);
                    String id = testCase.path("id").asText("");
                    String scenarioId = testCase.path("scenario_id").asText("");
                    JsonNode scenario = scenarios.get(scenarioId);
                    Comparison result = comparator.compareTestCase(testCase, scenario);
                    record(caseCounts, result.status());
                    record(caseOracleCounts, result.oracleStatus());
                    ObjectNode row = caseResults.addObject();
                    row.put("testCaseId", id);
                    row.put("scenarioId", scenarioId);
                    JsonNode scenarioCode = scenario == null ? null : scenario.get("expected_response_code");
                    JsonNode responseCodeValue = testCase.path("expected_response").path("response_code");
                    if (responseCodeValue.isObject()) responseCodeValue = responseCodeValue.path("value");
                    if (scenarioCode != null && !scenarioCode.isMissingNode() && !scenarioCode.isNull())
                        row.set("scenarioExpectedResponseCode", scenarioCode.deepCopy());
                    if (!responseCodeValue.isMissingNode() && !responseCodeValue.isNull())
                        row.set("testCaseExpectedResponseCode", responseCodeValue.deepCopy());
                    JsonNode aiResponseCode = testCase.path("expected_response").path("response_code");
                    if (aiResponseCode.isObject()) {
                        if (aiResponseCode.hasNonNull("tier")) row.put("responseCodeTier", aiResponseCode.path("tier").asText());
                        if (aiResponseCode.path("flags").isArray()) row.set("responseCodeFlags", aiResponseCode.path("flags").deepCopy());
                    }
                    writeResult(row, result);
                    testCases++;
                }
            }
        }

        ObjectNode report = MAPPER.createObjectNode();
        report.put("artifact", "ATL105-AI-EXPECTED-RESPONSE-ORACLE-COMPARISON");
        report.put("specificationVersion", "2026-3");
        report.put("status", "INDEPENDENT_CODE_FAMILY_CHECK_NOT_BUSINESS_OUTCOME_APPROVAL");
        report.put("policy", "AGREE means only that the declared code is allowed for a mapped family. DISAGREE means the independent family oracle rejects the code. AI_DEFECT identifies malformed or internally conflicting declarations. ORACLE_GAP means the family or expected TC response is unavailable.");
        report.put("scenarioCount", scenarioResults.size());
        report.put("testCaseCount", testCases);
        report.set("scenarioStatusCounts", MAPPER.valueToTree(scenarioCounts));
        report.set("testCaseStatusCounts", MAPPER.valueToTree(caseCounts));
        report.set("testCaseOracleStatusCounts", MAPPER.valueToTree(caseOracleCounts));
        ObjectNode inputs = report.putObject("inputSha256");
        inputs.put("scenarioCatalog", sha256(scenarioFile));
        inputs.put("testCaseCatalog", sha256(testCaseFile));
        inputs.put("independentResponseCodeMatrix", sha256(oracleMatrixFile));
        report.put("responseFamilyMappingSpecificationVersion", "2026-3");
        report.set("scenarios", scenarioResults);
        report.set("testCases", caseResults);
        Files.createDirectories(output.toAbsolutePath().getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println("scenarios=" + scenarioResults.size() + " scenarioStatuses=" + scenarioCounts
            + " testCases=" + testCases + " testCaseStatuses=" + caseCounts);
    }

    private static void validateOracleMatrix(Path matrixFile) throws IOException {
        JsonNode matrix = MAPPER.readTree(matrixFile.toFile());
        if (!"ATL105".equals(matrix.path("specification").asText())
            || !"2026-3".equals(matrix.path("specificationVersion").asText())
            || !matrix.path("codes").isArray()) throw new IOException("Unsupported response-code matrix");
        Map<String, Set<String>> matrixCodes = new HashMap<>();
        Set<String> uniqueCodes = new HashSet<>();
        for (JsonNode entry : matrix.path("codes")) {
            String code = entry.path("code").asText("");
            if (code.isBlank()) throw new IOException("Response-code matrix contains a blank code");
            uniqueCodes.add(code);
            for (JsonNode family : entry.path("families")) {
                String familyName = family.asText();
                if (!Atl105ResponseCodeFamilyOracle.expectedCodes(familyName).contains(code))
                    throw new IOException("Independent Java oracle differs from response-code matrix for " + familyName + "/" + code);
                matrixCodes.computeIfAbsent(familyName, ignored -> new HashSet<>()).add(code);
            }
        }
        if (uniqueCodes.size() != matrix.path("uniqueCodes").asInt(-1))
            throw new IOException("Response-code matrix unique-code count is inconsistent");
        for (String family : Set.of("financial", "totals", "electronic-mail", "communications-test",
                "proprietary-load", "transarmor", "emv-key-load")) {
            if (!Atl105ResponseCodeFamilyOracle.expectedCodes(family).equals(matrixCodes.getOrDefault(family, Set.of())))
                throw new IOException("Independent Java oracle family differs from response-code matrix for " + family);
        }
    }

    private static void record(Map<String, Long> counts, Status status) {
        counts.merge(status.name(), 1L, Long::sum);
    }

    private static void writeResult(ObjectNode row, Comparison result) {
        row.put("status", result.status().name());
        row.put("oracleStatus", result.oracleStatus().name());
        row.put("oracleFamily", result.oracleFamily());
        row.put("declarationConflict", result.declarationConflict());
        row.put("reason", result.reason());
    }

    private static Comparison result(Status status, String family, String reason) {
        return new Comparison(status, status, family, false, reason);
    }

    private static String sha256(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (var input = Files.newInputStream(path)) {
            byte[] buffer = new byte[1024 * 1024];
            int count;
            while ((count = input.read(buffer)) != -1) digest.update(buffer, 0, count);
        }
        return HexFormat.of().formatHex(digest.digest());
    }
}