package com.coreauth.validator;

import com.coreauth.validator.canonical.ProprietaryDataLoadPayloadValidator;
import com.coreauth.validator.canonical.Segment109PayloadValidator;
import com.coreauth.validator.canonical.Segment118PayloadValidator;
import com.coreauth.validator.canonical.TotalsRequestPayloadValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixESpecialPromptChainTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACKAGE = Path.of(
            "specifications/ATL105/test-output/test-json/appendix-e-special-prompt-chain-package.json");

    @Test
    void buildsValidatorHarnessChainsForNinePromptsAndBlocksTwoUnderSpecifiedPrompts() throws Exception {
        JsonNode matrix = MAPPER.readTree(PACKAGE.toFile());
        Set<String> brIds = ids(matrix.path("businessRequirements"));
        Set<String> tsIds = ids(matrix.path("testScenarios"));
        Set<String> tcIds = ids(matrix.path("testCases"));
        Set<String> tdIds = ids(matrix.path("testData"));
        Set<String> blockedCodes = new HashSet<>();

        assertThat(matrix.path("businessRequirements")).hasSize(11);
        assertThat(matrix.path("testScenarios")).hasSize(9);
        assertThat(matrix.path("testCases")).hasSize(9);
        assertThat(matrix.path("testData")).hasSize(9);
        assertThat(matrix.path("summary").path("businessCertified").asBoolean()).isFalse();
        assertThat(matrix.path("summary").path("converterCertified").asBoolean()).isFalse();

        for (JsonNode scenario : matrix.path("testScenarios")) {
            for (JsonNode requirementId : scenario.path("requirementIds")) {
                assertThat(brIds).contains(requirementId.asText());
            }
        }
        for (JsonNode testCase : matrix.path("testCases")) {
            for (JsonNode scenarioId : testCase.path("scenarioIds")) {
                assertThat(tsIds).contains(scenarioId.asText());
            }
            for (JsonNode dataId : testCase.path("testDataIds")) {
                assertThat(tdIds).contains(dataId.asText());
            }
        }
        for (JsonNode blocked : matrix.path("blockedPrompts")) {
            blockedCodes.add(blocked.path("promptCode").asText());
            assertThat(brIds).contains(blocked.path("businessRequirementId").asText());
        }
        assertThat(blockedCodes).containsExactlyInAnyOrder("900", "997");

        for (JsonNode data : matrix.path("testData")) {
            assertThat(data.path("producer").asText()).isEqualTo("TEST_SOLUTION");
            assertThat(data.path("readiness").asText()).isEqualTo("EXECUTABLE");
            for (JsonNode caseId : data.path("testCaseIds")) {
                assertThat(tcIds).contains(caseId.asText());
            }
            validateWithDeclaredOracle(data);
        }
    }

    private static void validateWithDeclaredOracle(JsonNode data) {
        String validator = data.path("validator").asText();
        JsonNode payload = data.path("payload");
        if (validator.contains("Segment118PayloadValidator")) {
            assertThat(new ProprietaryDataLoadPayloadValidator().validatePayload(payload).errors())
                    .as(data.path("id").asText() + " PDL envelope").isEmpty();
            assertThat(new Segment118PayloadValidator().validatePayload(payload).errors())
                    .as(data.path("id").asText() + " Segment 118 payload").isEmpty();
        } else if ("Segment109PayloadValidator".equals(validator)) {
            assertThat(new Segment109PayloadValidator().validatePayload(payload).errors())
                    .as(data.path("id").asText() + " Segment 109 payload").isEmpty();
        } else if ("TotalsRequestPayloadValidator".equals(validator)) {
            assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                    .as(data.path("id").asText() + " Totals request payload").isEmpty();
        } else {
            throw new AssertionError("Unknown special-prompt validator: " + validator);
        }
    }

    private static Set<String> ids(JsonNode artifacts) {
        Set<String> ids = new HashSet<>();
        for (JsonNode artifact : artifacts) {
            assertThat(ids.add(artifact.path("id").asText())).isTrue();
        }
        return ids;
    }
}
