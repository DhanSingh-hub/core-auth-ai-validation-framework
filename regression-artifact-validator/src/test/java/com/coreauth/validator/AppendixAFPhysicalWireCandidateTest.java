package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Chapter11MessageLayoutValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.canonical.DataSection1StructureValidator;
import com.coreauth.validator.coverage.GenerateAppendixAFPhysicalWireCandidate;
import com.coreauth.validator.canonical.Segment100SerializationValidator;
import com.coreauth.validator.canonical.Segment111SerializationValidator;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixAFPhysicalWireCandidateTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final char FIELD_SEPARATOR = '\u001c';
    private static final String CANDIDATE_FILE = "appendix-af-physical-wire-candidate-v1.json";

    @Test
    void candidateIsReproducibleAndContainsMeasuredSyntheticRequestWire() throws Exception {
        Path file = Atl105Paths.testOutput().resolve(Path.of("test-solution-independent-review", CANDIDATE_FILE));
        JsonNode candidate = MAPPER.readTree(file.toFile());
        assertThat(candidate).isEqualTo(GenerateAppendixAFPhysicalWireCandidate.buildCandidate());
        assertThat(candidate.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(candidate.path("executionReady").asBoolean()).isFalse();
        assertThat(candidate.path("request").path("segment100").path("segmentLength").asText()).isEqualTo("081");
        assertThat(candidate.path("request").path("segment100").path("byteCount").asInt()).isEqualTo(81);
        assertThat(candidate.path("request").path("segment111").path("segmentLength").asText()).isEqualTo("019");
        assertThat(candidate.path("request").path("segment111").path("byteCount").asInt()).isEqualTo(19);
        assertThat(candidate.path("request").path("applicationPayloadByteCount").asInt()).isEqualTo(110);
        assertThat(candidate.path("request").path("tcpIpTpduFraming").asText()).isEqualTo("NOT_INCLUDED");
        assertThat(candidate.path("request").path("segment111").path("table82IndicatorEncodingStatus").asText())
                .isEqualTo("REVIEW_REQUIRED");
        assertThat(candidate.path("request").path("segment111").path("afTransactionIdMappingStatus").asText())
                .isEqualTo("REVIEW_REQUIRED");
        assertThat(candidate.path("response").path("status").asText()).isEqualTo("NOT_PROVIDED");

        String escaped = candidate.path("request").path("applicationPayloadEscaped").asText();
        String wire = escaped.replace("\\u001c", String.valueOf(FIELD_SEPARATOR));
        byte[] bytes = wire.getBytes(StandardCharsets.US_ASCII);
        assertThat(bytes).hasSize(110);
        assertThat(java.util.HexFormat.of().withUpperCase().formatHex(bytes))
                .isEqualTo(candidate.path("request").path("applicationPayloadHex").asText());
        assertThat(wire).startsWith("ATL105" + FIELD_SEPARATOR + "02" + FIELD_SEPARATOR + "100" + FIELD_SEPARATOR);
        assertThat(wire).contains("111" + FIELD_SEPARATOR + "019" + FIELD_SEPARATOR + "0820040001" + FIELD_SEPARATOR);
    }

    @Test
    void existingValidatorsCheckTheSyntheticRequestStructureWithoutPromotingIt() throws Exception {
        JsonNode candidate = MAPPER.readTree(Atl105Paths.testOutput().resolve(
                Path.of("test-solution-independent-review", CANDIDATE_FILE)).toFile());
        CanonicalTestData data = new CanonicalTestData();
        data.setId("TD-AF-PHYSICAL-WIRE-CANDIDATE");
        data.setPayload(candidate.path("testDataPayload"));
        CanonicalArtifactPackage pkg = new CanonicalArtifactPackage();
        pkg.setTestData(List.of(data));

        assertThat(new DataSection1StructureValidator().validate(pkg).errors()).isEmpty();
        assertThat(new Segment111SerializationValidator().validate(pkg).errors()).isEmpty();

        var standard = candidate.path("chapter11LayoutObservation").path("segments").get(0).path("observation");
        var standardFindings = new Segment100SerializationValidator().validateActual(standard,
                new Chapter13DataElementValidator(Atl105Paths.root()));
        assertThat(standardFindings).anyMatch(finding -> finding.ruleId().equals("SEG100-R-021")
                && finding.status() == Status.CHECKS_PASSED);
        assertThat(standardFindings).noneMatch(finding -> finding.status() == Status.INVALID);

        var layout = new Chapter11MessageLayoutValidator().validate(candidate.path("chapter11LayoutObservation"));
        assertThat(layout.findings()).noneMatch(finding -> finding.status() == Status.INVALID);
        assertThat(layout.findings()).anyMatch(finding -> finding.status() == Status.REVIEW_REQUIRED);
    }
}