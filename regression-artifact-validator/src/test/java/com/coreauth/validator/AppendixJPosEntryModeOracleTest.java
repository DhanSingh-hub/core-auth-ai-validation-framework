package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixJPosEntryModeOracle;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixJPosEntryModeOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AppendixJPosEntryModeOracle oracle = new AppendixJPosEntryModeOracle();

    @Test
    void catalogsAllSourceListedPanAndTerminalCapabilityValues() throws Exception {
        var source = Files.readAllLines(Path.of("specifications/ATL105/docs/specs/extracted_text.txt"));
        var appendixJ = String.join("\n", source.subList(32490, 32576));

        assertThat(oracle.panEntryModes()).hasSize(14);
        assertThat(oracle.terminalCapabilityModes()).hasSize(6);
        for (String code : oracle.panEntryModes().keySet()) {
            assertThat(appendixJ).containsPattern("(?m)^\\s*" + code + "\\s+");
        }
        for (String code : oracle.terminalCapabilityModes().keySet()) {
            assertThat(appendixJ).containsPattern("(?m)^\\s*" + code + "\\s+");
        }
    }

    @Test
    void recognizesThreeDigitCompositionWithoutClaimingContextEligibility() {
        var result = oracle.evaluate("900");

        assertThat(result.outcome()).isEqualTo(AppendixJPosEntryModeOracle.Outcome.PASS);
        assertThat(result.panEntryMode()).isEqualTo("90");
        assertThat(result.terminalCapabilityMode()).isEqualTo("0");
        assertThat(result.contextStatus()).isEqualTo("NOT_EVALUATED");
    }

    @Test
    void recognizesEachListedComponentWithoutInferringCombinationEligibility() {
        for (String panMode : oracle.panEntryModes().keySet()) {
            assertThat(oracle.evaluate(panMode + "0").outcome())
                    .as("PAN Entry Mode %s", panMode)
                    .isEqualTo(AppendixJPosEntryModeOracle.Outcome.PASS);
        }
        for (String terminalMode : oracle.terminalCapabilityModes().keySet()) {
            assertThat(oracle.evaluate("00" + terminalMode).outcome())
                    .as("Terminal Capability Mode %s", terminalMode)
                    .isEqualTo(AppendixJPosEntryModeOracle.Outcome.PASS);
        }
    }

    @Test
    void rejectsMalformedAndUnlistedComponents() {
        assertThat(oracle.evaluate("90").outcome()).isEqualTo(AppendixJPosEntryModeOracle.Outcome.INVALID_FORMAT);
        assertThat(oracle.evaluate("0A0").outcome()).isEqualTo(AppendixJPosEntryModeOracle.Outcome.INVALID_FORMAT);
        assertThat(oracle.evaluate("030").outcome()).isEqualTo(AppendixJPosEntryModeOracle.Outcome.INVALID_PAN_ENTRY_MODE);
        assertThat(oracle.evaluate("001").outcome()).isEqualTo(AppendixJPosEntryModeOracle.Outcome.PASS);
        assertThat(oracle.evaluate("008").outcome()).isEqualTo(AppendixJPosEntryModeOracle.Outcome.INVALID_TERMINAL_CAPABILITY_MODE);
    }

    @Test
    void coveragePackageHasResolvedLinksAndKeepsContextCasesReviewRequired() throws Exception {
        JsonNode pack = MAPPER.readTree(Path.of(
                "specifications/ATL105/test-output/test-json/appendices/appendix-j-segment-100-coverage.json").toFile());

        assertThat(pack.path("businessRequirements")).hasSize(5);
        assertThat(pack.path("testScenarios")).hasSize(3);
        assertThat(pack.path("testCases")).hasSize(5);
        assertThat(pack.path("testData")).hasSize(26);
        assertThat(countReadiness(pack.path("testData"), "EXECUTABLE")).isEqualTo(24);
        assertThat(countReadiness(pack.path("testData"), "REVIEW_REQUIRED")).isEqualTo(2);

        Set<String> panValues = valuesForMode(pack.path("testData"), "PAN_ENTRY_MODE");
        Set<String> terminalValues = valuesForMode(pack.path("testData"), "TERMINAL_CAPABILITY_MODE");
        assertThat(panValues).containsExactlyInAnyOrderElementsOf(oracle.panEntryModes().keySet());
        assertThat(terminalValues).containsExactlyInAnyOrderElementsOf(oracle.terminalCapabilityModes().keySet());

        for (JsonNode data : pack.path("testData")) {
            assertThat(contains(pack.path("businessRequirements"), data.path("coversBr").asText())).isTrue();
            for (JsonNode testCaseId : data.path("testCaseIds")) {
                assertThat(contains(pack.path("testCases"), testCaseId.asText())).isTrue();
            }
        }
        for (JsonNode testCase : pack.path("testCases")) {
            assertThat(contains(pack.path("testScenarios"), testCase.path("scenarioId").asText())).isTrue();
        }
    }

    private static boolean contains(JsonNode artifacts, String id) {
        for (JsonNode artifact : artifacts) {
            if (id.equals(artifact.path("id").asText())) return true;
        }
        return false;
    }

    private static int countReadiness(JsonNode testData, String readiness) {
        int count = 0;
        for (JsonNode data : testData) {
            if (readiness.equals(data.path("readiness").asText())) count++;
        }
        return count;
    }

    private static Set<String> valuesForMode(JsonNode testData, String mode) {
        Set<String> values = new HashSet<>();
        for (JsonNode data : testData) {
            if (mode.equals(data.path("payload").path("mode").asText())) {
                values.add(data.path("payload").path("value").asText());
            }
        }
        return values;
    }
}