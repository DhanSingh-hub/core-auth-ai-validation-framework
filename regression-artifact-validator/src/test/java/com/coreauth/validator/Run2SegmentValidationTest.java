package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalRequirement;
import com.coreauth.validator.canonical.CanonicalScenario;
import com.coreauth.validator.canonical.CanonicalTestCase;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.RequirementCrosswalkEntry;
import com.coreauth.validator.canonical.SourceAnchor;
import com.coreauth.validator.coverage.CanonicalSegmentPackageFilter;
import com.coreauth.validator.coverage.Run2PayloadBatchValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Run2SegmentValidationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void filtersOnlyTheSelectedSegmentsLinkedChain() {
        CanonicalArtifactPackage source = new CanonicalArtifactPackage();
        CanonicalRequirement requirement100 = requirement("AI-BR-100", "100");
        CanonicalRequirement requirement101 = requirement("AI-BR-101", "101");
        CanonicalScenario scenario100 = scenario("AI-SC-100", requirement100.getId(), "100");
        CanonicalScenario scenario101 = scenario("AI-SC-101", requirement101.getId(), "101");
        CanonicalTestCase testCase100 = testCase("AI-TC-100", scenario100.getId(), "100");
        CanonicalTestCase testCase101 = testCase("AI-TC-101", scenario101.getId(), "101");
        CanonicalTestData data100 = testData("AI-TD-100", testCase100.getId(), "100", "100.json");
        CanonicalTestData data101 = testData("AI-TD-101", testCase101.getId(), "101", "101.json");
        source.setBusinessRequirements(List.of(requirement100, requirement101));
        source.setTestScenarios(List.of(scenario100, scenario101));
        source.setTestCases(List.of(testCase100, testCase101));
        source.setTestData(List.of(data100, data101));

        CanonicalArtifactPackage filtered = new CanonicalSegmentPackageFilter().filter(
                source, "100", List.of(new RequirementCrosswalkEntry()));

        assertThat(filtered.getBusinessRequirements()).extracting(CanonicalRequirement::getId)
                .containsExactly("AI-BR-100");
        assertThat(filtered.getTestScenarios()).extracting(CanonicalScenario::getId)
                .containsExactly("AI-SC-100");
        assertThat(filtered.getTestCases()).extracting(CanonicalTestCase::getId)
                .containsExactly("AI-TC-100");
        assertThat(filtered.getTestData()).extracting(CanonicalTestData::getId)
                .containsExactly("AI-TD-100");
    }

    @Test
    void unsupportedPayloadValidatorFailsClosed(@TempDir Path runRoot) throws Exception {
        Path payload = Files.writeString(runRoot.resolve("segment-105.json"), """
                {"Financial Request":{"Totals Segment":{"Segment Type":"105"}}}
                """);
        CanonicalArtifactPackage artifactPackage = new CanonicalArtifactPackage();
        artifactPackage.setTestData(List.of(testData("TD-105", "TC-105", "105",
                payload.getFileName().toString())));

        var result = new Run2PayloadBatchValidator().validate("105", runRoot, artifactPackage);

        assertThat(result.validatorImplemented()).isFalse();
        assertThat(result.applicablePayloadFiles()).isEqualTo(1);
        assertThat(result.asValidationResult().isValid()).isFalse();
        assertThat(result.asValidationResult().errors()).anyMatch(error ->
                error.reason().contains("validator is not implemented"));
    }

    private static CanonicalRequirement requirement(String id, String segment) {
        CanonicalRequirement value = new CanonicalRequirement();
        value.setId(id);
        value.setSourceAnchors(List.of(anchor(segment)));
        return value;
    }

    private static CanonicalScenario scenario(String id, String requirementId, String segment) {
        CanonicalScenario value = new CanonicalScenario();
        value.setId(id);
        value.setRequirementIds(List.of(requirementId));
        value.setSourceAnchors(List.of(anchor(segment)));
        return value;
    }

    private static CanonicalTestCase testCase(String id, String scenarioId, String segment) {
        CanonicalTestCase value = new CanonicalTestCase();
        value.setId(id);
        value.setScenarioIds(List.of(scenarioId));
        value.setSourceAnchors(List.of(anchor(segment)));
        return value;
    }

    private CanonicalTestData testData(String id, String testCaseId, String segment, String sourceFile) {
        CanonicalTestData value = new CanonicalTestData();
        value.setId(id);
        value.setTestCaseIds(List.of(testCaseId));
        value.setSourceAnchors(List.of(anchor(segment)));
        value.setPayload(mapper.createObjectNode().put("sourceFile", sourceFile));
        return value;
    }

    private static SourceAnchor anchor(String segment) {
        SourceAnchor value = new SourceAnchor();
        value.setSpecification("ATL105");
        value.setVersion("2026-3");
        value.setSegment(segment);
        value.setRule("fixture-rule-" + segment);
        return value;
    }
}