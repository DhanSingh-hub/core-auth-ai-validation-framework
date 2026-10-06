package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100PaymentNetworkOracle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100PaymentNetworkOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void expectedMatrixContainsAllSixteenLeafNetworkTypes() {
        assertThat(new Segment100PaymentNetworkOracle().expectedNetworks()).hasSize(16);
    }

    @Test
    void sectionFiveLeafRequirementsMatchOracleAndKeepUnprovenContextReviewRequired() throws Exception {
        var oracle = new Segment100PaymentNetworkOracle();
        var matrix = MAPPER.readTree(Path.of(
                "specifications/ATL105/test-output/test-json/section-5-payment-network-segment-100-matrix.json").toFile());
        List<String> leafTypes = new ArrayList<>();
        List<String> requirementIds = new ArrayList<>();
        boolean contextRequirementReviewRequired = false;

        for (var requirement : matrix.path("businessRequirements")) {
            requirementIds.add(requirement.path("id").asText());
            if (requirement.hasNonNull("networkType")) {
                leafTypes.add(requirement.path("networkType").asText());
            }
            if ("BR-SEG100-SEC5-CONTEXT".equals(requirement.path("id").asText())) {
                contextRequirementReviewRequired = "REVIEW_REQUIRED".equals(requirement.path("status").asText());
            }
        }

        assertThat(leafTypes).containsExactlyInAnyOrderElementsOf(oracle.expectedNetworks().keySet());
        assertThat(requirementIds).doesNotHaveDuplicates();
        assertThat(contextRequirementReviewRequired).isTrue();
    }

    @Test
    void paymentNetworkAndReceiptMatrixHasCompleteIndependentTraceability() throws Exception {
        var matrix = MAPPER.readTree(Path.of(
                "specifications/ATL105/test-output/test-json/payment-network-card-type-br-ts-tc-td-matrix.json").toFile());
        var requirements = matrix.path("businessRequirements");
        var scenarios = matrix.path("testScenarios");
        var cases = matrix.path("testCases");
        var testData = matrix.path("testData");
        Set<String> requirementIds = ids(requirements);
        Set<String> scenarioIds = ids(scenarios);
        Set<String> caseIds = ids(cases);
        Set<String> testDataIds = ids(testData);
        Set<String> linkedRequirementIds = new HashSet<>();

        assertThat(requirements).hasSize(26);
        assertThat(scenarios).hasSize(26);
        assertThat(cases).hasSize(26);
        assertThat(testData).hasSize(26);
        assertThat(requirements.findValues("networkType")).hasSize(16);
        assertThat(requirements.findValues("receiptCardTypeId")).hasSize(9);

        for (var scenario : scenarios) {
            for (var requirementId : scenario.path("requirementIds")) {
                assertThat(requirementIds).contains(requirementId.asText());
                linkedRequirementIds.add(requirementId.asText());
            }
        }
        for (var testCase : cases) {
            for (var scenarioId : testCase.path("scenarioIds")) {
                assertThat(scenarioIds).contains(scenarioId.asText());
            }
            for (var testDataId : testCase.path("testDataIds")) {
                assertThat(testDataIds).contains(testDataId.asText());
            }
        }
        for (var fixture : testData) {
            assertThat(fixture.path("producer").asText()).isEqualTo("TEST_SOLUTION");
            for (var caseId : fixture.path("testCaseIds")) {
                assertThat(caseIds).contains(caseId.asText());
            }
        }
        for (var blockedRequirement : matrix.path("blockedRequirements")) {
            String blockedId = blockedRequirement.path("businessRequirementId").asText();
            assertThat(requirementIds).doesNotContain(blockedId);
            assertThat(blockedRequirement.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(linkedRequirementIds).doesNotContain(blockedId);
        }
    }

    @Test
    void acceptsEverySupportedPaymentNetworkWithSegment100() {
        Segment100PaymentNetworkOracle oracle = new Segment100PaymentNetworkOracle();
        for (String network : oracle.expectedNetworks().keySet()) {
            assertThat(oracle.validateArtifact(network, payload(network)).errors()).as(network).isEmpty();
        }
    }

    @Test
    void rejectsUnknownPaymentNetwork() {
        var result = new Segment100PaymentNetworkOracle().validateArtifact("UNKNOWN", payload("UNKNOWN"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Unsupported payment network type"));
    }

    @Test
    void rejectsPaymentNetworkWithoutSegment100() {
        ObjectNode artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("paymentNetworkType", "CREDIT_NONPROPRIETARY");
        artifact.putObject("Financial Request");

        var result = new Segment100PaymentNetworkOracle().validateArtifact("NO-SEGMENT", artifact);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Standard Segment Type 100 is required"));
    }

    private static ObjectNode payload(String network) {
        ObjectNode artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("paymentNetworkType", network);
        ObjectNode request = artifact.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.putObject("Standard Segment").put("SegmentType", "100");
        return artifact;
    }

    private static Set<String> ids(com.fasterxml.jackson.databind.JsonNode artifacts) {
        Set<String> ids = new HashSet<>();
        for (var artifact : artifacts) {
            assertThat(ids.add(artifact.path("id").asText())).isTrue();
        }
        return ids;
    }
}
