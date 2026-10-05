package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenerateSegmentDl3AiArtifactCoverageReportTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final GenerateSegmentDl3AiArtifactCoverageReport generator =
            new GenerateSegmentDl3AiArtifactCoverageReport();

    @Test
    void separatesSuppliedAiCatalogFromRepresentativeSample(@TempDir Path tempDir) throws Exception {
        JsonNode report = generate(tempDir);

        assertThat(report.path("artifactInventory").path("testSolutionRules").asInt()).isEqualTo(9);
        assertThat(report.path("artifactInventory").path("testSolutionBusinessRequirements").asInt()).isEqualTo(9);
        assertThat(report.path("artifactInventory").path("testSolutionScenarios").asInt()).isEqualTo(9);
        assertThat(report.path("artifactInventory").path("testSolutionTestCases").asInt()).isEqualTo(18);
        assertThat(report.path("artifactInventory").path("testSolutionTestDataRecords").asInt()).isEqualTo(18);
        assertThat(report.path("artifactInventory").path("suppliedAiCatalogDl3Requirements").asInt()).isEqualTo(12);
        assertThat(report.path("artifactInventory").path("phaseOneRepresentativeDl3Chains").asInt()).isEqualTo(1);
        assertThat(report.path("artifactInventory").path("phaseOneTestDataFilesScannedForFieldNames").asInt())
                .isEqualTo(37);
        assertThat(report.path("phaseOneAiChain").path("chainIdentityStatus").asText()).isEqualTo("ID_CONSISTENT");
        assertThat(report.path("phaseOneAiChain").path("dataHasRequest").asBoolean()).isFalse();
        assertThat(report.path("testSolutionRuleCoverage").get(8).path("candidateStatus").asText())
                .isEqualTo("CANDIDATE_CHAIN_REVIEW_REQUIRED");

        JsonNode metrics = report.path("testSolutionMetrics");
        assertThat(metrics.path("rulesWithCandidateBrTsTcTdChains").asInt()).isEqualTo(9);
        assertThat(metrics.path("rulesWithPositiveAndNegativeCandidates").asInt()).isEqualTo(6);
        assertThat(metrics.path("approvedTestDataPairs").asInt()).isZero();
        assertThat(metrics.path("executedTestCases").asInt()).isZero();
        assertThat(metrics.path("certifiedCoveredRules").asInt()).isZero();
        assertThat(report.path("suppliedAiDl3Requirements").size()).isEqualTo(12);
        assertThat(report.path("aiFieldAliasCrosswalk").size()).isEqualTo(7);
    }

    @Test
    void independentlyIdentifiesKnownSampleDefectsAndOpenQuestions(@TempDir Path tempDir) throws Exception {
        JsonNode report = generate(tempDir);

        assertThat(finding(report, "SEGDL3-R-001").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(finding(report, "SEGDL3-R-002").path("status").asText()).isEqualTo("PASS");
        assertThat(finding(report, "SEGDL3-R-003").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(finding(report, "SEGDL3-R-004").path("status").asText()).isEqualTo("NOT_ASSERTABLE");
        assertThat(finding(report, "SEGDL3-R-005").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL3-R-006").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(finding(report, "SEGDL3-R-007").path("status").asText()).isEqualTo("FAIL");
        assertThat(finding(report, "SEGDL3-R-008").path("status").asText()).isEqualTo("NOT_ASSERTABLE");
        assertThat(finding(report, "SEGDL3-R-009").path("status").asText()).isEqualTo("NOT_ASSERTABLE");
        assertThat(report.path("aiTestDataValidationSummary").path("passwordValueIncluded").asBoolean()).isFalse();
        assertThat(report.path("aiTestDataValidationSummary").path("validForExecution").asBoolean()).isFalse();
    }

    @Test
    void validatesAllRuleAndTestDataLinksAndOpenSmeStatuses() throws Exception {
        JsonNode testPackage = mapper.readTree(
                Atl105Paths.testJson("segment-DL3-coverage-package.json").toFile());
        JsonNode catalog = mapper.readTree(Atl105Paths.ruleCatalog("DL3").toFile());
        assertThat(testPackage.path("_meta").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(testPackage.path("businessRequirements").size()).isEqualTo(9);
        assertThat(testPackage.path("testScenarios").size()).isEqualTo(9);
        assertThat(testPackage.path("testCases").size()).isEqualTo(18);
        assertThat(testPackage.path("testData").size()).isEqualTo(18);
        assertUniqueIds(testPackage.path("businessRequirements"));
        assertUniqueIds(testPackage.path("testScenarios"));
        assertUniqueIds(testPackage.path("testCases"));
        assertUniqueIds(testPackage.path("testData"));
        assertThat(testPackage.path("coverageSummary").path("approvedTestDataPairs").asInt()).isZero();
        assertThat(testPackage.path("coverageSummary").path("executedTestCases").asInt()).isZero();

        Set<String> mappedRuleIds = new HashSet<>();
        for (JsonNode br : testPackage.path("businessRequirements")) {
            mappedRuleIds.add(br.path("ruleId").asText());
            if (br.has("provisionalItem")) {
                JsonNode provisional = find(catalog.path("provisionalItems"), "id",
                        br.path("provisionalItem").asText());
                assertThat(provisional.path("status").asText()).isEqualTo("OPEN");
                assertThat(br.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
            }
        }
        assertThat(mappedRuleIds).containsExactlyInAnyOrderElementsOf(ids(catalog.path("rules"), "ruleId"));
        for (JsonNode testCase : testPackage.path("testCases")) {
            String caseId = testCase.path("id").asText();
            assertThat(testCase.path("scenarioIds").size()).isPositive();
            assertThat(testCase.path("testDataIds").size()).isEqualTo(1);
            JsonNode linkedData = find(testPackage.path("testData"), "id",
                    testCase.path("testDataIds").get(0).asText());
            assertThat(linkedData.isMissingNode()).isFalse();
            assertThat(contains(linkedData.path("testCaseIds"), caseId)).isTrue();
            assertThat(linkedData.path("payload").path("request").isObject()).isTrue();
            assertThat(linkedData.path("payload").path("response").isObject()).isTrue();
        }
    }

    @Test
    void rejectsADeletedTestDataLink(@TempDir Path tempDir) throws Exception {
        JsonNode pkg = mapper.readTree(Atl105Paths.testJson("segment-DL3-coverage-package.json").toFile());
        ((ArrayNode) pkg.path("testCases").get(0).path("testDataIds")).removeAll();
        Path brokenPackage = tempDir.resolve("broken-package.json");
        mapper.writeValue(brokenPackage.toFile(), pkg);

        assertThatThrownBy(() -> generate(tempDir, brokenPackage))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("one symbolic test-data pair");
    }

    @Test
    void doesNotExposePasswordValuesInValidationFindings() {
        ObjectNode data = mapper.createObjectNode();
        ObjectNode dl3 = data.putObject("Date and Time Load Response")
                .putObject("Date and Time Data Segment");
        dl3.put("DataTypeIndicator", ":");
        dl3.put("DayoftheWeek", "3");
        dl3.put("CurrentDate", "093026");
        dl3.put("CurrentTime", "1405");
        dl3.put("CutTime", "2300");
        dl3.put("Password", "synthetic-secret-marker");
        dl3.put("EndofDataIndicator", "~");
        ObjectNode report = mapper.createObjectNode();

        generator.addAiDataValidation(report, data, mapper.createObjectNode());

        assertThat(report.toString()).doesNotContain("synthetic-secret-marker");
        assertThat(finding(report, "SEGDL3-R-003").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(finding(report, "SEGDL3-R-006").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
    }

    @Test
    void requiredDateTimeFieldsDoNotDependOnDisputedPasswordOrFraming() {
        ObjectNode data = mapper.createObjectNode();
        ObjectNode dl3 = data.putObject("Table Load Response")
                .putObject("Date and Time Data Segment");
        dl3.put("DayoftheWeek", "3");
        dl3.put("CurrentDate", "093026");
        dl3.put("CurrentTime", "1405");
        dl3.put("CutTime", "2300");
        ObjectNode report = mapper.createObjectNode();

        generator.addAiDataValidation(report, data, mapper.createObjectNode().put("messageFamily", "Table Load Response"));

        assertThat(finding(report, "SEGDL3-R-002").path("status").asText()).isEqualTo("PASS");
        assertThat(finding(report, "SEGDL3-R-003").path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(finding(report, "SEGDL3-R-001").path("status").asText()).isEqualTo("FAIL");
    }

    @Test
    void failsExplicitlyWhenRequiredInputIsMissing(@TempDir Path tempDir) {
        assertThatThrownBy(() -> generator.generate(tempDir, Path.of("missing-package.json"),
                Path.of("missing-catalog.json"), Path.of("missing-ai-catalog.json"),
                Path.of("missing-aliases.json"), tempDir.resolve("report.json"), tempDir.resolve("report.md"),
                tempDir.resolve("consolidated.txt")))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Required DL3 coverage input is missing");
    }

    private JsonNode generate(Path tempDir) throws Exception {
        return generate(tempDir, Atl105Paths.testJson("segment-DL3-coverage-package.json"));
    }

    private JsonNode generate(Path tempDir, Path packageFile) throws Exception {
        Path runRoot = Atl105Paths.testInput().resolve(Path.of(
                "ai-solution", "runs", "2026-09-29", "phase_1_single_leg"));
        Path aiCatalog = Atl105Paths.testOutput().resolve(Path.of(
                "ai-artifacts", "coverage-reports", "supplied-ai-catalog", "supplied-ai-catalog-coverage.json"));
        Path aliases = Atl105Paths.root().resolve(Path.of("contract", "segment-DL3-field-alias-crosswalk.json"));
        return generator.generate(runRoot, packageFile, Atl105Paths.ruleCatalog("DL3"),
                aiCatalog, aliases, tempDir.resolve("report.json"), tempDir.resolve("report.md"),
                tempDir.resolve("consolidated.txt"));
    }

    private static JsonNode finding(JsonNode report, String ruleId) {
        for (JsonNode row : report.path("aiTestDataRuleValidation")) {
            if (ruleId.equals(row.path("ruleId").asText())) return row;
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static JsonNode find(JsonNode rows, String field, String value) {
        for (JsonNode row : rows) {
            if (value.equals(row.path(field).asText())) return row;
        }
        return com.fasterxml.jackson.databind.node.MissingNode.getInstance();
    }

    private static Set<String> ids(JsonNode rows, String field) {
        Set<String> result = new HashSet<>();
        rows.forEach(row -> result.add(row.path(field).asText()));
        return result;
    }

    private static void assertUniqueIds(JsonNode rows) {
        Set<String> ids = new HashSet<>();
        for (JsonNode row : rows) assertThat(ids.add(row.path("id").asText())).isTrue();
    }

    private static boolean contains(JsonNode array, String expected) {
        for (JsonNode value : array) if (expected.equals(value.asText())) return true;
        return false;
    }
}
