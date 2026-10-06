package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixDStateCodes;
import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment110PayloadValidator;
import com.coreauth.validator.canonical.TerminalIdentifierValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixDStateCodesTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void appendixDContainsSeventyTwoAlphabeticalAndAnsiPairs() {
        assertThat(AppendixDStateCodes.ansiByAlphabeticalCode()).hasSize(72);
        assertThat(AppendixDStateCodes.alphabeticalCodes()).hasSize(72);
        assertThat(AppendixDStateCodes.ansiCodes()).hasSize(72);
        assertThat(AppendixDStateCodes.ansiByAlphabeticalCode())
                .containsEntry("CA", "06")
                .containsEntry("GU", "43")
                .containsEntry("AB", "61")
                .containsEntry("AA", "57")
                .containsEntry("NA", "00");
    }

    @Test
    void terminalIdentifierAcceptsEveryAppendixDAnsiCodeAtMembershipLevel() {
        for (String ansiCode : AppendixDStateCodes.ansiCodes()) {
            assertThat(validateTerminalIdentifier(ansiCode).errors()).as(ansiCode).isEmpty();
        }
    }

    @Test
    void terminalIdentifierRejectsUnlistedAndAlphabeticalStateValues() {
        assertThat(validateTerminalIdentifier("03").errors())
                .anyMatch(error -> error.reason().contains("Appendix D ANSI state code"));
        assertThat(validateTerminalIdentifier("CA").errors())
                .anyMatch(error -> error.reason().contains("Appendix D ANSI state code"));
    }

    @Test
    void declaredTerminalComponentsMustUseAndMatchAnsiCode() {
        ObjectNode payload = terminalIdentifierPayload("PO06123456001", "06");
        assertThat(validateTerminalIdentifier(payload).errors()).isEmpty();

        ObjectNode invalid = terminalIdentifierPayload("PO06123456001", "CA");
        assertThat(validateTerminalIdentifier(invalid).errors())
                .anyMatch(error -> error.reason().contains("valid two-digit Appendix D ANSI code"));
    }

    @Test
    void segment110AcceptsEveryAppendixDAlphabeticalCode() {
        Segment110PayloadValidator validator = new Segment110PayloadValidator();
        for (String alphaCode : AppendixDStateCodes.alphabeticalCodes()) {
            ObjectNode payload = segment110Payload(alphaCode);
            assertThat(validator.validatePayload(payload).errors()).as(alphaCode).isEmpty();
        }
    }

    @Test
    void appendixDCodeSetTestDataMatchesAndExercisesAllValidatorMappings() throws Exception {
        var coverage = MAPPER.readTree(Path.of(
                "specifications/ATL105/test-output/test-json/appendices/appendix-d-segment-100-coverage.json").toFile());
        Set<String> ansiCodes = valuesForFixture(coverage, "TD-SEG100-APPD-ANSI-CODESET", "ansiCodes");
        Set<String> alphabeticalCodes = valuesForFixture(coverage, "TD-SEG100-APPD-ALPHA-CODESET", "alphabeticalCodes");

        assertThat(ansiCodes).containsExactlyInAnyOrderElementsOf(AppendixDStateCodes.ansiCodes());
        assertThat(alphabeticalCodes).containsExactlyInAnyOrderElementsOf(AppendixDStateCodes.alphabeticalCodes());
        for (String ansiCode : ansiCodes) {
            assertThat(validateTerminalIdentifier(ansiCode).errors()).as(ansiCode).isEmpty();
        }
        Segment110PayloadValidator segment110Validator = new Segment110PayloadValidator();
        for (String alphaCode : alphabeticalCodes) {
            assertThat(segment110Validator.validatePayload(segment110Payload(alphaCode)).errors()).as(alphaCode).isEmpty();
        }
    }

    @Test
    void appendixDCoveragePackageHasLinkedIndependentTestDataAndReviewBlockedCountryRule() throws Exception {
        Path packagePath = Path.of("specifications/ATL105/test-output/test-json/appendices/appendix-d-segment-100-coverage.json");
        var coverage = MAPPER.readTree(packagePath.toFile());
        Set<String> brIds = ids(coverage.path("businessRequirements"), "id");
        Set<String> scenarioIds = ids(coverage.path("testScenarios"), "id");
        Set<String> testCaseIds = ids(coverage.path("testCases"), "id");
        Set<String> testDataIds = ids(coverage.path("testData"), "id");
        Set<String> linkedBrIds = new HashSet<>();

        assertThat(coverage.path("testData")).hasSize(11);
        for (var scenario : coverage.path("testScenarios")) {
            for (var brId : scenario.path("requirementIds")) {
                assertThat(brIds).contains(brId.asText());
                linkedBrIds.add(brId.asText());
            }
        }
        for (var testCase : coverage.path("testCases")) {
            for (var scenarioId : testCase.path("scenarioIds")) {
                assertThat(scenarioIds).contains(scenarioId.asText());
            }
            for (var testDataId : testCase.path("testDataIds")) {
                assertThat(testDataIds).contains(testDataId.asText());
            }
        }
        for (var testData : coverage.path("testData")) {
            assertThat(testData.path("producer").asText()).isEqualTo("TEST_SOLUTION");
            for (var testCaseId : testData.path("testCaseIds")) {
                assertThat(testCaseIds).contains(testCaseId.asText());
            }
        }
        var nonUsRequirement = findById(coverage.path("businessRequirements"), "BR-SEG100-APPD-NON-US-STATE");
        var nonUsScenario = findById(coverage.path("testScenarios"), "TS-SEG100-APPD-NON-US-STATE");
        var nonUsTestCase = findById(coverage.path("testCases"), "TC-SEG100-APPD-NON-US-STATE");
        var nonUsTestData = findById(coverage.path("testData"), "TD-SEG100-APPD-NON-US-00-REVIEW");
        var table041Deferral = findById(coverage.path("deferredReviewItems"), "APPD-REVIEW-001");
        var element12Deferral = findById(coverage.path("deferredReviewItems"), "APPD-REVIEW-002");
        assertThat(nonUsRequirement.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(nonUsScenario.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(nonUsTestCase.path("expectedOutcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(nonUsTestCase.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(nonUsTestData.path("expectedValidation").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(table041Deferral.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(table041Deferral.path("requiredEvidence").asText()).contains("serialized Segment 111/Table 041");
        assertThat(element12Deferral.path("status").asText()).isEqualTo("PARTIALLY_COVERED");
        assertThat(element12Deferral.path("requiredEvidence").asText()).contains("Element 12");
        assertThat(linkedBrIds).contains("BR-SEG100-APPD-TERMINAL-STATE", "BR-SEG100-APPD-ALPHA-STATE");
    }

    private static Set<String> ids(Iterable<com.fasterxml.jackson.databind.JsonNode> nodes, String property) {
        Set<String> ids = new HashSet<>();
        for (var node : nodes) {
            assertThat(ids.add(node.path(property).asText())).isTrue();
        }
        return ids;
    }

    private static Set<String> valuesForFixture(com.fasterxml.jackson.databind.JsonNode coverage,
                                                String fixtureId, String valueField) {
        for (var testData : coverage.path("testData")) {
            if (fixtureId.equals(testData.path("id").asText())) {
                Set<String> values = new HashSet<>();
                for (var value : testData.path("payload").path(valueField)) {
                    assertThat(values.add(value.asText())).isTrue();
                }
                return values;
            }
        }
        throw new AssertionError("Missing Appendix D test-data vector " + fixtureId);
    }

    private static com.fasterxml.jackson.databind.JsonNode findById(
            com.fasterxml.jackson.databind.JsonNode artifacts, String id) {
        for (var artifact : artifacts) {
            if (id.equals(artifact.path("id").asText())) {
                return artifact;
            }
        }
        throw new AssertionError("Missing Appendix D artifact " + id);
    }

    private static com.coreauth.validator.validation.ValidationResult validateTerminalIdentifier(String ansiCode) {
        return validateTerminalIdentifier(terminalIdentifierPayload("PO" + ansiCode + "123456001", ansiCode));
    }

    private static com.coreauth.validator.validation.ValidationResult validateTerminalIdentifier(ObjectNode payload) {
        CanonicalTestData testData = new CanonicalTestData();
        testData.setId("TD-APPD-TERMINAL-STATE");
        testData.setPayload(payload);
        CanonicalArtifactPackage artifactPackage = new CanonicalArtifactPackage();
        artifactPackage.setTestData(List.of(testData));
        return new TerminalIdentifierValidator().validate(artifactPackage);
    }

    private static ObjectNode terminalIdentifierPayload(String terminalIdentifier, String stateCode) {
        ObjectNode payload = MAPPER.createObjectNode();
        payload.putObject("request").putObject("dataSection2").putObject("standardSegment")
                .put("terminalIdentifier", terminalIdentifier);
        ObjectNode controls = payload.putObject("testControls");
        controls.put("validateTerminalIdentifier", true);
        controls.put("terminalIdentifierMode", "financial");
        ObjectNode components = controls.putObject("terminalIdentifierComponents");
        components.put("deviceType", "PO");
        components.put("stateCode", stateCode);
        components.put("merchantNumber", "123456");
        components.put("deviceNumber", "001");
        return payload;
    }

    private static ObjectNode segment110Payload(String stateCode) {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("ECA/TeleCheck Service Transaction Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "03");
        request.putObject("Standard Segment").put("SegmentType", "100");
        ObjectNode segment = request.putObject("Check Data Segment");
        segment.put("SegmentType", "110");
        segment.put("SegmentLength", "060");
        segment.put("MICRData", "T123456780T0301123D456D7O");
        segment.put("CheckType", "P");
        segment.put("DriversLicense", "SYN12345");
        segment.put("StateCode", stateCode);
        request.putObject("Variable Information Data Segment").put("SegmentType", "111");
        return payload;
    }
}
