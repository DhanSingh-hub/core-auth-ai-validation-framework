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
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class AiArtifactDefectRegister {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String REVIEW_STATUS = "CANDIDATE_REVIEW_REQUIRED";

    private AiArtifactDefectRegister() { }

    public static List<ObjectNode> inspectTestCase(JsonNode testCase, JsonNode scenario, Path payloadRoot,
                                                   AiExpectedResponseComparator responseComparator) throws IOException {
        List<ObjectNode> findings = new ArrayList<>();
        String caseId = testCase.path("id").asText("");
        String scenarioId = testCase.path("scenario_id").asText("");
        if (scenario == null) {
            findings.add(finding("CASE_SCENARIO_REFERENCE_MISSING", "TEST_CASE", caseId, scenarioId,
                MAPPER.createObjectNode().put("scenarioReference", scenarioId)));
        }

        Set<String> caseRequirements = requirementIds(testCase);
        Set<String> scenarioRequirements = requirementIds(scenario);
        if (caseRequirements.isEmpty() || scenarioRequirements.isEmpty() || !caseRequirements.equals(scenarioRequirements)) {
            ObjectNode evidence = MAPPER.createObjectNode();
            evidence.set("caseRequirementIds", MAPPER.valueToTree(caseRequirements));
            evidence.set("scenarioRequirementIds", MAPPER.valueToTree(scenarioRequirements));
            evidence.set("sharedRequirementIds", MAPPER.valueToTree(intersection(caseRequirements, scenarioRequirements)));
            findings.add(finding(attributionIssue(caseRequirements, scenarioRequirements), "TEST_CASE", caseId, scenarioId, evidence));
        }

        if (scenario != null) {
            AiExpectedResponseComparator.Comparison comparison = responseComparator.compareTestCase(testCase, scenario);
            if (comparison.declarationConflict() || comparison.status() == AiExpectedResponseComparator.Status.AI_DEFECT) {
                ObjectNode evidence = MAPPER.createObjectNode();
                JsonNode scenarioCode = scenario.get("expected_response_code");
                JsonNode responseCode = testCase.path("expected_response").path("response_code");
                if (responseCode.isObject()) responseCode = responseCode.path("value");
                if (scenarioCode != null && !scenarioCode.isNull()) evidence.set("scenarioExpectedResponseCode", scenarioCode.deepCopy());
                if (!responseCode.isMissingNode() && !responseCode.isNull()) evidence.set("testCaseExpectedResponseCode", responseCode.deepCopy());
                evidence.put("oracleStatus", comparison.oracleStatus().name());
                evidence.put("reason", comparison.reason());
                findings.add(finding(comparison.declarationConflict() ? "EXPECTED_RESPONSE_CONFLICT" : "MALFORMED_EXPECTED_RESPONSE",
                    "TEST_CASE", caseId, scenarioId, evidence));
            }
        }

        for (String relativeFile : declaredPayloadFiles(testCase)) {
            Path file = safePayloadPath(payloadRoot, relativeFile, caseId);
            ObjectNode evidence = MAPPER.createObjectNode().put("payloadFile", relativeFile);
            if (file == null) {
                findings.add(finding("UNSAFE_OR_UNSUPPORTED_PAYLOAD_PATH", "TEST_CASE", caseId, scenarioId, evidence));
            } else if (!Files.isRegularFile(file)) {
                findings.add(finding("DECLARED_PAYLOAD_MISSING", "TEST_CASE", caseId, scenarioId, evidence));
            } else {
                JsonNode payload = MAPPER.readTree(file.toFile());
                if (!hasNonEmptyValue(payload))
                    findings.add(finding("EMPTY_PHYSICAL_PAYLOAD", "TEST_CASE", caseId, scenarioId, evidence));
            }
        }
        return findings;
    }

    public static List<ObjectNode> missingFlowScenarios(Iterable<JsonNode> scenarios, Map<String, Integer> casesPerScenario) throws IOException {
        List<ObjectNode> findings = new ArrayList<>();
        for (JsonNode scenario : scenarios) {
            if (!"flow".equalsIgnoreCase(scenario.path("type").asText(""))) continue;
            String id = scenario.path("id").asText("");
            if (casesPerScenario.getOrDefault(id, 0) != 0) continue;
            ObjectNode evidence = MAPPER.createObjectNode().put("scenarioName", scenario.path("name").asText(""));
            evidence.set("requirementIds", MAPPER.valueToTree(requirementIds(scenario)));
            findings.add(finding("FLOW_SCENARIO_WITHOUT_TEST_CASE", "SCENARIO", id, id, evidence));
        }
        return findings;
    }

    public static boolean hasNonEmptyValue(JsonNode node) {
        if (node == null || node.isNull()) return false;
        if (node.isValueNode()) return !node.isTextual() || !node.asText().isBlank();
        for (JsonNode child : node) if (hasNonEmptyValue(child)) return true;
        return false;
    }

    public static Set<String> requirementIds(JsonNode row) {
        Set<String> ids = new LinkedHashSet<>();
        if (row == null) return ids;
        JsonNode many = row.path("requirement_ids");
        if (many.isTextual()) ids.add(many.asText());
        else if (many.isArray()) many.forEach(item -> { if (item.isTextual() && !item.asText().isBlank()) ids.add(item.asText()); });
        String single = row.path("requirement_id").asText("");
        if (!single.isBlank()) ids.add(single);
        return ids;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) throw new IllegalArgumentException(
            "Usage: AiArtifactDefectRegister <approved_scenarios.json> <test_case_candidates.json> <qe_shaped_test_data> <report.json>");
        Path scenarioFile = Path.of(args[0]);
        Path testCaseFile = Path.of(args[1]);
        Path payloadRoot = Path.of(args[2]).toAbsolutePath().normalize();
        Path output = Path.of(args[3]);
        if (!Files.isRegularFile(scenarioFile) || !Files.isRegularFile(testCaseFile) || !Files.isDirectory(payloadRoot))
            throw new IOException("Scenario catalog, test-case catalog, and payload directory must exist");
        if (Files.exists(output)) throw new IOException("Refusing to overwrite existing report: " + output);

        JsonNode scenarioCatalog = MAPPER.readTree(scenarioFile.toFile());
        if (!scenarioCatalog.path("scenarios").isArray()) throw new IOException("Scenario catalog has no scenarios array");
        Map<String, JsonNode> scenarios = new TreeMap<>();
        for (JsonNode scenario : scenarioCatalog.path("scenarios")) {
            String id = scenario.path("id").asText("");
            if (id.isBlank() || scenarios.putIfAbsent(id, scenario) != null) throw new IOException("Scenario id missing or duplicated: " + id);
        }

        AiExpectedResponseComparator comparator = new AiExpectedResponseComparator();
        ArrayNode findings = MAPPER.createArrayNode();
        Map<String, Integer> casesPerScenario = new TreeMap<>();
        int testCaseCount = 0;
        int payloadFilesExamined = 0;
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
                    String scenarioId = testCase.path("scenario_id").asText("");
                    casesPerScenario.merge(scenarioId, 1, Integer::sum);
                    List<ObjectNode> caseFindings = inspectTestCase(testCase, scenarios.get(scenarioId), payloadRoot, comparator);
                    caseFindings.forEach(findings::add);
                    payloadFilesExamined += declaredPayloadFiles(testCase).size();
                    testCaseCount++;
                }
            }
        }
        missingFlowScenarios(scenarios.values(), casesPerScenario).forEach(findings::add);

        Map<String, Long> issueCounts = new TreeMap<>();
        for (JsonNode finding : findings) issueCounts.merge(finding.path("issueCode").asText(""), 1L, Long::sum);
        ObjectNode report = MAPPER.createObjectNode();
        report.put("artifact", "ATL105-AI-ARTIFACT-DEFECT-CANDIDATES");
        report.put("specificationVersion", "2026-3");
        report.put("status", "AUTOMATED_CANDIDATES_REQUIRE_REVIEW");
        report.put("policy", "Records identify structural or declaration evidence only. They do not establish semantic defect, BR meaning, conditional-field applicability, execution failure, or business approval.");
        report.put("scenarioCount", scenarios.size());
        report.put("testCaseCount", testCaseCount);
        report.put("payloadFilesDeclared", payloadFilesExamined);
        report.put("defectCandidateCount", findings.size());
        report.set("issueCounts", MAPPER.valueToTree(issueCounts));
        ObjectNode hashes = report.putObject("inputSha256");
        hashes.put("scenarioCatalog", sha256(scenarioFile));
        hashes.put("testCaseCatalog", sha256(testCaseFile));
        report.set("findings", findings);
        Files.createDirectories(output.toAbsolutePath().getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println("scenarios=" + scenarios.size() + " testCases=" + testCaseCount
            + " payloadFilesDeclared=" + payloadFilesExamined + " defectCandidates=" + findings.size()
            + " issueCounts=" + issueCounts);
    }

    private static List<String> declaredPayloadFiles(JsonNode testCase) {
        List<String> files = new ArrayList<>();
        JsonNode primary = testCase.path("test_data_file");
        if (primary.isTextual() && !primary.asText().isBlank()) files.add(primary.asText());
        JsonNode legs = testCase.path("test_data_leg_files");
        if (legs.isArray()) legs.forEach(item -> { if (item.isTextual()) files.add(item.asText()); });
        else if (legs.isObject()) legs.elements().forEachRemaining(item -> { if (item.isTextual()) files.add(item.asText()); });
        if (files.isEmpty() && !testCase.path("is_flow").asBoolean(false))
            files.add("qe_shaped_test_data/" + testCase.path("id").asText("") + ".json");
        return files.stream().distinct().toList();
    }

    private static Path safePayloadPath(Path payloadRoot, String declared, String caseId) {
        if (declared == null || declared.isBlank() || declared.contains(":") || declared.startsWith("/") || declared.startsWith("\\")) return null;
        String normalized = declared.replace('\\', '/');
        Path relative = Path.of(normalized);
        if (relative.isAbsolute()) return null;
        for (Path part : relative) if ("..".equals(part.toString())) return null;
        String filename = relative.getFileName() == null ? "" : relative.getFileName().toString();
        if (!filename.endsWith(".json") || filename.endsWith(".meta.json")
            || (!filename.startsWith(caseId + ".") && !filename.startsWith(caseId + "_"))) return null;
        if (relative.getNameCount() > 1 && !"qe_shaped_test_data".equals(relative.getName(0).toString())) return null;
        Path resolved = payloadRoot.resolve(filename).normalize();
        return resolved.startsWith(payloadRoot) ? resolved : null;
    }

    private static ObjectNode finding(String issueCode, String scope, String subjectId, String scenarioId, ObjectNode evidence) throws IOException {
        ObjectNode finding = MAPPER.createObjectNode();
        finding.put("recordId", "AID-" + sha256(issueCode + "|" + scope + "|" + subjectId + "|" + scenarioId).substring(0, 16).toUpperCase());
        finding.put("issueCode", issueCode);
        finding.put("scope", scope);
        finding.put("subjectId", subjectId);
        finding.put("scenarioId", scenarioId);
        finding.put("status", REVIEW_STATUS);
        finding.set("evidence", evidence);
        return finding;
    }

    private static String attributionIssue(Set<String> caseIds, Set<String> scenarioIds) {
        if (caseIds.isEmpty() && scenarioIds.isEmpty()) return "BR_ATTRIBUTION_MISSING_BOTH";
        if (caseIds.isEmpty()) return "BR_ATTRIBUTION_MISSING_CASE";
        if (scenarioIds.isEmpty()) return "BR_ATTRIBUTION_MISSING_SCENARIO";
        return intersection(caseIds, scenarioIds).isEmpty() ? "BR_ATTRIBUTION_NO_SHARED_LINK" : "BR_ATTRIBUTION_LINK_SET_DIVERGENCE";
    }

    private static Set<String> intersection(Set<String> left, Set<String> right) {
        Set<String> shared = new LinkedHashSet<>(left);
        shared.retainAll(right);
        return shared;
    }

    private static String sha256(String value) throws IOException {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (java.security.NoSuchAlgorithmException exception) {
            throw new IOException("SHA-256 is unavailable", exception);
        }
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