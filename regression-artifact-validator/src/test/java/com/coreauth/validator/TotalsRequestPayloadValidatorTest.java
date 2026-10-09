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

    @Test
    void rejectsAnIncorrectSegment105PromptCode() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ((ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST)
                .path("Totals Data Segment")).put("PromptCode", "991");

        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("SEG105-R-005"));
    }

    @Test
    void acceptsEveryTotalsDateSpecialCodeAndRejectsAnArbitraryOne() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ObjectNode segment = (ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST)
                .path("Totals Data Segment");
        for (String code : new String[]{"111111", "222222", "333333", "444444", "555555", "999999", "010125"}) {
            segment.put("TotalsDate", code);
            assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                    .as(code).noneMatch(error -> error.reason().contains("SEG105-R-008"));
        }
        segment.put("TotalsDate", "131325");
        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("SEG105-R-008"));
    }

    @Test
    void rejectsDuplicateOrOversizedCategoryTotals() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ObjectNode segment = (ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST)
                .path("Totals Data Segment");
        var duplicate = MAPPER.createArrayNode();
        duplicate.addObject().put("CardLabel", "CC").put("CardTypeTotalCount", "00001").put("CardTypeTotalAmount", "00000100");
        duplicate.addObject().put("CardLabel", "CC").put("CardTypeTotalCount", "00001").put("CardTypeTotalAmount", "00000100");
        segment.set("CategoryTotals", duplicate);

        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("appears more than once") && error.reason().contains("SEG105-R-015"));
    }

    @Test
    void requiresEverySegment105FieldLevelElement() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ObjectNode segment = (ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST)
                .path("Totals Data Segment");
        for (String field : new String[]{"InformationByte", "TerminalID", "HardwareVersion",
                "SoftwareVersion", "FirmwareVersion", "SequenceNumber", "GrandTotal"}) {
            ObjectNode mutated = payload.deepCopy();
            ((ObjectNode) mutated.path(TotalsRequestPayloadValidator.TOTALS_REQUEST)
                    .path("Totals Data Segment")).put(field, "");
            assertThat(new TotalsRequestPayloadValidator().validatePayload(mutated).errors())
                    .as(field).isNotEmpty();
        }
    }

    @Test
    void rejectsInvalidCalendarDatesZeroCountsAndZeroSequence() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ObjectNode segment = (ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST).path("Totals Data Segment");
        for (String date : List.of("020025", "023025", "022925")) {
            segment.put("TotalsDate", date);
            assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                    .anyMatch(e -> e.reason().contains("SEG105-R-008"));
        }
        segment.put("TotalsDate", "333333").put("SequenceNumber", "000000");
        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(e -> e.reason().contains("SEG105-R-013"));
        segment.put("SequenceNumber", "000123");
        ((ObjectNode) segment.path("CategoryTotals").get(0)).put("CardTypeTotalCount", "00000");
        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(e -> e.reason().contains("SEG105-R-015"));
    }

    @Test
    void checksComputedLengthVersionWidthsAndCardBucketOrder() throws IOException {
        ObjectNode payload = (ObjectNode) MAPPER.readTree(TEST_DATA.resolve(
                Paths.get("segment-105", "message", "totals-request.valid.json")).toFile());
        ObjectNode segment = (ObjectNode) payload.path(TotalsRequestPayloadValidator.TOTALS_REQUEST).path("Totals Data Segment");
        segment.put("SegmentLength", "105");
        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(e -> e.reason().contains("SEG105-R-002"));
        segment.put("SegmentLength", "106").put("HardwareVersion", "HW001");
        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(e -> e.reason().contains("SEG105-R-010"));
        segment.put("HardwareVersion", "HW01");
        var reordered = MAPPER.createArrayNode().add(segment.path("CategoryTotals").get(1)).add(segment.path("CategoryTotals").get(0));
        segment.set("CategoryTotals", reordered);
        assertThat(new TotalsRequestPayloadValidator().validatePayload(payload).errors())
                .anyMatch(e -> e.reason().contains("source order"));
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
