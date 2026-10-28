package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100SerializationValidator;
import com.coreauth.validator.canonical.Segment101PayloadValidator;
import com.coreauth.validator.canonical.Segment102PayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.coverage.GenerateVoyagerWireShapeCandidate;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;

class VoyagerWireShapeCandidateTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void candidateIsReproducibleAndExplicitlyNotProducerOrExecutionEvidence() throws Exception {
        JsonNode checkedIn = MAPPER.readTree(GenerateVoyagerWireShapeCandidate.outputPath().toFile());
        JsonNode generated = GenerateVoyagerWireShapeCandidate.buildCandidate();

        assertThat(checkedIn).isEqualTo(generated);
        assertThat(checkedIn.path("status").asText()).isEqualTo("SYNTHETIC_REVIEW_REQUIRED");
        assertThat(checkedIn.path("executionReady").asBoolean()).isFalse();
        assertThat(checkedIn.path("producerConverterUsed").asBoolean()).isFalse();
        assertThat(checkedIn.path("businessValidity").asText()).isEqualTo("NOT_ASSESSED");
        assertThat(checkedIn.path("segment101").path("numberedElements").path("85").asText()).isEqualTo("101");
        assertThat(checkedIn.path("segment102").path("numberOfProducts").asText()).isEqualTo("01");
        assertThat(checkedIn.path("section1").path("numberOfSegments").asText()).isEqualTo("03");
        String accountNumber = checkedIn.path("segment100").path("numberedElements").path("2").asText();
        assertThat(accountNumber).hasSize(19).startsWith("85");
    }

    @Test
    void byteCountsAndActualWireAgreeWithIndependentAtl105Validation() throws Exception {
        JsonNode candidate = MAPPER.readTree(GenerateVoyagerWireShapeCandidate.outputPath().toFile());
        String segmentWire = candidate.path("segment100").path("serializedWireEscaped").asText()
                .replace("\\u001c", "\u001c");
        String payloadWire = candidate.path("applicationPayloadEscaped").asText()
                .replace("\\u001c", "\u001c");
        byte[] segmentBytes = segmentWire.getBytes(StandardCharsets.US_ASCII);
        byte[] payloadBytes = payloadWire.getBytes(StandardCharsets.US_ASCII);

        assertThat(segmentBytes).hasSize(candidate.path("segment100").path("byteCount").asInt());
        assertThat(segmentWire).endsWith("\u001c");
        assertThat(candidate.path("segment100").path("segmentLength").asText())
                .isEqualTo("%03d".formatted(segmentBytes.length));
        assertThat(HexFormat.of().withUpperCase().formatHex(segmentBytes))
                .isEqualTo(candidate.path("segment100").path("serializedWireHex").asText());
        assertThat(payloadBytes).hasSize(candidate.path("applicationPayloadByteCount").asInt());

        var findings = new Segment100SerializationValidator().validateActual(candidate.path("validatorObservation"),
                new Chapter13DataElementValidator(Atl105Paths.root()));
        assertThat(findings).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-021");
            assertThat(finding.status()).isEqualTo(Status.CHECKS_PASSED);
        });
        assertThat(findings).anySatisfy(finding -> {
            assertThat(finding.ruleId()).isEqualTo("SEG100-R-016");
            assertThat(finding.scope()).isEqualTo("standard-wire-length");
            assertThat(finding.status()).isEqualTo(Status.CHECKS_PASSED);
        });
        assertThat(findings).noneMatch(finding -> finding.status() == Status.INVALID);
    }

    @Test
    void allThreeSegmentsHaveMeasuredLengthsAndExistingLogicalValidatorsAcceptTheFixture() throws Exception {
        JsonNode candidate = MAPPER.readTree(GenerateVoyagerWireShapeCandidate.outputPath().toFile());
        for (String id : new String[]{"100", "101", "102"}) {
            JsonNode segment = candidate.path("segment" + id);
            String wire = segment.path("serializedWireEscaped").asText().replace("\\u001c", "\u001c");
            byte[] bytes = wire.getBytes(StandardCharsets.US_ASCII);
            assertThat(bytes).as("Segment " + id).hasSize(segment.path("byteCount").asInt());
            assertThat(segment.path("segmentLength").asText()).as("Segment " + id)
                    .isEqualTo("%03d".formatted(bytes.length));
            assertThat(HexFormat.of().withUpperCase().formatHex(bytes))
                    .isEqualTo(segment.path("serializedWireHex").asText());
            assertThat(wire).as("Segment " + id).endsWith("\u001c");
        }

        assertThat(new Segment101PayloadValidator()
                .validatePayload(candidate.path("segment101ValidatorPayload")).errors()).isEmpty();
        assertThat(new Segment102PayloadValidator()
                .validatePayload(candidate.path("segment102ValidatorPayload")).errors()).isEmpty();
        assertThat(candidate.path("segment102").path("serializedWireEscaped").asText())
                .contains("3001000\\3001000\\00001000\\u001c");
    }

    @Test
    void applicationPayloadMatchesDeclaredSegmentCountAndOrderedSegmentBytes() throws Exception {
        JsonNode candidate = MAPPER.readTree(GenerateVoyagerWireShapeCandidate.outputPath().toFile());
        String section1 = candidate.path("section1").path("serializedWireEscaped").asText()
                .replace("\\u001c", "\u001c");
        StringBuilder expected = new StringBuilder(section1);
        for (String id : new String[]{"100", "101", "102"}) {
            expected.append(candidate.path("segment" + id).path("serializedWireEscaped").asText()
                    .replace("\\u001c", "\u001c"));
        }
        String applicationPayload = candidate.path("applicationPayloadEscaped").asText()
                .replace("\\u001c", "\u001c");

        assertThat(candidate.path("section1").path("numberOfSegments").asText()).isEqualTo("03");
        assertThat(candidate.path("dataSection3")).hasSize(2);
        assertThat(candidate.path("dataSection3").get(0).path("segment").asText()).isEqualTo("101");
        assertThat(candidate.path("dataSection3").get(1).path("segment").asText()).isEqualTo("102");
        assertThat(applicationPayload).isEqualTo(expected.toString());
        assertThat(applicationPayload.getBytes(StandardCharsets.US_ASCII))
                .hasSize(candidate.path("applicationPayloadByteCount").asInt());
        assertThat(applicationPayload).contains("102\u001c040\u001cS01001G3001000\\3001000\\00001000\u001c");
    }

    @Test
    void detectsMissingSeparatorAndWrongDeclaredLengthMutations() throws Exception {
        JsonNode candidate = MAPPER.readTree(GenerateVoyagerWireShapeCandidate.outputPath().toFile());
        var oracle = new Chapter13DataElementValidator(Atl105Paths.root());
        String correctWire = candidate.path("validatorObservation").path("serializedSegment").asText();

        ObjectNode missingSeparator = candidate.path("validatorObservation").deepCopy();
        missingSeparator.put("serializedSegment", correctWire.substring(0, correctWire.length() - 1));
        assertThat(new Segment100SerializationValidator().validateActual(missingSeparator, oracle))
                .anySatisfy(finding -> {
                    assertThat(finding.ruleId()).isEqualTo("SEG100-R-021");
                    assertThat(finding.status()).isEqualTo(Status.INVALID);
                });

        ObjectNode wrongLength = candidate.path("validatorObservation").deepCopy();
        ((ObjectNode) wrongLength.path("elements")).put("84", "001");
        wrongLength.put("serializedSegment", correctWire.replaceFirst("100\u001c[0-9]{3}\u001c", "100\u001c001\u001c"));
        assertThat(new Segment100SerializationValidator().validateActual(wrongLength, oracle))
                .anySatisfy(finding -> {
                    assertThat(finding.ruleId()).isEqualTo("SEG100-R-016");
                    assertThat(finding.scope()).isEqualTo("standard-wire-length");
                    assertThat(finding.status()).isEqualTo(Status.INVALID);
                });
    }
}
