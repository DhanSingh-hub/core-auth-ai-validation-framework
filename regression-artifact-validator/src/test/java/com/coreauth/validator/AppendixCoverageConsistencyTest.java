package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixCoverageConsistency;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixCoverageConsistencyTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void allAppendixRecordsMatchTheCanonicalInventory() throws Exception {
        var report = new AppendixCoverageConsistency().validate(
                Path.of("specifications/ATL105/test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json"),
                Path.of("specifications/ATL105/test-output/test-json/appendices"));

        assertThat(report.appendixCount()).isEqualTo(31);
        assertThat(report.missingFiles()).isEmpty();
        assertThat(report.countMismatches()).isEmpty();
        assertThat(report.incompleteEvidence()).doesNotContain("appendix-e-segment-100-coverage.json does not embed testScenarios and testCases arrays");
        assertThat(report.invalidTestDataReadiness()).isEmpty();
        assertThat(report.danglingTestDataReferences()).isEmpty();
        assertThat(report.isConsistent()).isTrue();
        assertThat(report.statusCounts()).containsKeys("COVERED", "PARTIALLY_COVERED", "REVIEW_REQUIRED");
        assertThat(report.readinessCounts()).containsKeys("EXECUTABLE", "EXTERNAL_FIXTURE_REQUIRED");
        assertThat(report.readinessCounts().get("EXTERNAL_FIXTURE_REQUIRED")).isEqualTo(4);

        JsonNode inventory = MAPPER.readTree(Path.of(
            "specifications/ATL105/test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json").toFile());
        JsonNode appendixE = findAppendix(inventory, "E");
        JsonNode appendixEPackage = MAPPER.readTree(Path.of(
            "specifications/ATL105/test-output/test-json/appendices/appendix-e-segment-100-coverage.json").toFile());
        assertThat(appendixE.path("status").asText()).isEqualTo("PARTIALLY_COVERED");
        assertThat(appendixE.path("brRecords").asInt()).isEqualTo(61);
        assertThat(appendixEPackage.path("businessRequirements")).hasSize(61);
        assertThat(appendixEPackage.path("testScenarios")).hasSize(57);
        assertThat(appendixEPackage.path("testCases")).hasSize(57);
        assertThat(appendixEPackage.path("testData")).hasSize(57);
        assertThat(appendixEPackage.path("codeFamilies").path("tableLoadResponseCodes").path("count").asInt()).isEqualTo(56);
        assertThat(appendixEPackage.path("codeFamilies").path("financialPromptCodes").path("count").asInt()).isEqualTo(36);
        assertThat(appendixEPackage.path("codeFamilies").path("specialPromptCodes").path("count").asInt()).isEqualTo(11);
        assertThat(appendixEPackage.path("tableLoadCoverageSummary").path("perValueBrTsTcTdChains").asInt()).isEqualTo(56);
        assertTableLoadCodeChains(appendixEPackage);

        JsonNode appendixK = findAppendix(inventory, "K");
        JsonNode appendixKPackage = MAPPER.readTree(Path.of(
          "specifications/ATL105/test-output/test-json/appendices/appendix-k-segment-100-coverage.json").toFile());
        assertThat(appendixK.path("status").asText()).isEqualTo("PARTIALLY_COVERED");
        assertThat(appendixK.path("brRecords").asInt()).isEqualTo(49);
        assertThat(appendixKPackage.path("businessRequirements")).hasSize(49);
        assertThat(appendixKPackage.path("testScenarios")).hasSize(46);
        assertThat(appendixKPackage.path("testCases")).hasSize(46);
        assertThat(appendixKPackage.path("testData")).hasSize(47);
        assertThat(appendixKPackage.path("tableIdCatalog").path("sourceSelectorCount").asInt()).isEqualTo(44);
        assertThat(appendixKPackage.path("tableIdCatalog").path("assignedSelectorCount").asInt()).isEqualTo(43);
    }

      private static JsonNode findAppendix(JsonNode inventory, String id) {
        for (JsonNode appendix : inventory.path("appendices")) {
          if (id.equals(appendix.path("id").asText())) return appendix;
        }
        throw new AssertionError("Missing Appendix " + id);
      }

      private static void assertTableLoadCodeChains(JsonNode appendixEPackage) {
        JsonNode requirements = appendixEPackage.path("businessRequirements");
        JsonNode scenarios = appendixEPackage.path("testScenarios");
        JsonNode cases = appendixEPackage.path("testCases");
        JsonNode testData = appendixEPackage.path("testData");
        Set<String> codeBrIds = new HashSet<>();

        for (JsonNode requirement : requirements) {
          String brId = requirement.path("id").asText();
          if (!brId.startsWith("BR-SEG100-APPE-TABLE-LOAD-CODE-")) continue;
          codeBrIds.add(brId);
          String code = requirement.path("code").asText();
          String tsId = "TS-SEG100-APPE-TABLE-LOAD-" + code;
          String tcId = "TC-SEG100-APPE-TABLE-LOAD-" + code + "-PASS";
          String tdId = "TD-SEG100-APPE-TABLE-LOAD-" + code + "-PASS";
          JsonNode scenario = findById(scenarios, tsId);
          JsonNode testCase = findById(cases, tcId);
          JsonNode data = findById(testData, tdId);

            assertThat(arrayContains(scenario.path("requirementIds"), brId)).isTrue();
            assertThat(arrayContains(testCase.path("scenarioIds"), tsId)).isTrue();
            assertThat(arrayContains(testCase.path("testDataIds"), tdId)).isTrue();
            assertThat(arrayContains(data.path("testCaseIds"), tcId)).isTrue();
          assertThat(data.path("coversBr").asText()).isEqualTo(brId);
          assertThat(data.path("producer").asText()).isEqualTo("TEST_SOLUTION");
          assertThat(data.path("readiness").asText()).isEqualTo("EXECUTABLE");
        }
        assertThat(codeBrIds).hasSize(56);
      }

      private static JsonNode findById(JsonNode artifacts, String id) {
        for (JsonNode artifact : artifacts) {
          if (id.equals(artifact.path("id").asText())) return artifact;
        }
        throw new AssertionError("Missing Appendix E artifact " + id);
      }

      private static boolean arrayContains(JsonNode values, String expected) {
        for (JsonNode value : values) {
          if (expected.equals(value.asText())) return true;
        }
        return false;
      }

    @Test
    void rejectsInvalidReadinessAndDanglingTestDataReferences(@org.junit.jupiter.api.io.TempDir Path tempDir) throws Exception {
        Path evidenceDir = tempDir.resolve("appendices");
        Files.createDirectories(evidenceDir);
        Files.writeString(evidenceDir.resolve("appendix-z-segment-100-coverage.json"), """
                {
                  "businessRequirements": [{"id":"BR-1","status":"COVERED"}],
                  "testScenarios": [],
                  "testCases": [],
                  "testData": [
                    {"id":"TD-1","coversBr":"BR-1","readiness":"NOT_A_REAL_VALUE"},
                    {"id":"TD-2","coversBr":"BR-MISSING","readiness":"EXECUTABLE"}
                  ]
                }
                """);
        Files.writeString(tempDir.resolve("inventory.json"), """
                {"appendices": [{"id":"Z","brRecords":1,"sourceRecord":"appendix-z-segment-100-coverage.json"}]}
                """);

        var report = new AppendixCoverageConsistency().validate(tempDir.resolve("inventory.json"), evidenceDir);

        assertThat(report.invalidTestDataReadiness()).anyMatch(message -> message.contains("TD-1") && message.contains("NOT_A_REAL_VALUE"));
        assertThat(report.danglingTestDataReferences()).anyMatch(message -> message.contains("TD-2") && message.contains("BR-MISSING"));
        assertThat(report.isConsistent()).isFalse();
    }
}
