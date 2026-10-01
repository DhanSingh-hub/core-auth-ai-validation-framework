package com.coreauth.validator;

import com.coreauth.validator.canonical.TotalsRequestPayloadValidator;
import com.coreauth.validator.validation.ValidationError;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/** Message-level tests for the Totals Request (11.4.1.1) and Totals with Proprietary Data Load Request (11.4.1.2). */
class TotalsRequestPayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path TEST_DATA = Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data");
    private static final List<Path> FIXTURE_DIRECTORIES = List.of(
            TEST_DATA.resolve(Paths.get("segment-105", "message")),
            TEST_DATA.resolve(Paths.get("segment-119", "message")));

    @Test
    void everyFixtureMeetsItsDeclaredExpectation() throws IOException {
        List<Path> fixtures = fixtures();
        assertThat(fixtures).as("Totals fixtures").isNotEmpty();

        for (Path fixture : fixtures) {
            JsonNode meta = MAPPER.readTree(fixture.toFile()).path("_meta");
            ValidationResult result = new TotalsRequestPayloadValidator().validateFile(fixture);

            if ("ACCEPT".equals(meta.path("expected").asText())) {
                assertThat(result.errors()).as("%s should be accepted", fixture).isEmpty();
            } else {
                assertThat(result.errors()).as("%s should be rejected", fixture).isNotEmpty();
                for (JsonNode rule : meta.path("expectedRules")) {
                    assertThat(result.errors()).extracting(ValidationError::reason)
                            .as("%s should cite %s", fixture, rule.asText())
                            .anyMatch(reason -> reason.contains(rule.asText()));
                }
            }
        }
    }

    @Test
    void acceptsElement63WrittenAsOneDigit() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ((ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST)).put("NumSegments", "1");

        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors()).isEmpty();
    }

    @Test
    void warnsWhenSegment119ExceedsTheLayoutTableLength() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-119", "message", "totals-pdl-request.valid.json")).toFile());
        ((ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_WITH_PDL_REQUEST)
                .path("Totals with Proprietary Data Load Data Segment")).put("SegmentLength", "400");

        ValidationResult result = new TotalsRequestPayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("SEG119-R-038"));
    }

    @Test
    void rejectsSegment105LongerThan409() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ((ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST)
                .path("Totals Data Segment")).put("SegmentLength", "410");

        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("SEG105-R-021"));
    }

    private static List<Path> fixtures() throws IOException {
        List<Path> files = new ArrayList<>();
        for (Path directory : FIXTURE_DIRECTORIES) {
            try (Stream<Path> stream = Files.list(directory)) {
                stream.filter(path -> path.toString().endsWith(".json")).sorted().forEach(files::add);
            }
        }
        return files;
    }
}
