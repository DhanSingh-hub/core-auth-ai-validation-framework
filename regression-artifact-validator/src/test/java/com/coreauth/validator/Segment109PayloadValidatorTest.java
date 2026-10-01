package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment109PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/** Item 1 baseline tests for source-confirmed Segment 109 request rules. */
class Segment109PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void acceptsRetrievalRequest() {
        ValidationResult result = new Segment109PayloadValidator().validatePayload(validRequest("981"));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsSubmissionRequest() {
        ValidationResult result = new Segment109PayloadValidator().validatePayload(validRequest("995"));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsProprietaryCardRetrievalRequest() {
        ValidationResult result = new Segment109PayloadValidator().validatePayload(validRequest("996"));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validRequest("981");
        segment(payload).put("SegmentType", "100");

        assertRule(payload, "SEG109-R-004");
    }

    @Test
    void rejectsSegmentLengthAboveMaximum() {
        ObjectNode payload = validRequest("981");
        segment(payload).put("SegmentLength", "233");

        assertRule(payload, "SEG109-R-006");
    }

    @Test
    void rejectsUnsupportedPromptCode() {
        ObjectNode payload = validRequest("981");
        segment(payload).put("PromptCode", "000");

        assertRule(payload, "SEG109-R-011");
    }

    @Test
    void rejectsMalformedSequenceNumber() {
        ObjectNode payload = validRequest("981");
        segment(payload).put("SequenceNumber", "ABC123");

        assertRule(payload, "SEG109-R-015");
    }

    @Test
    void rejectsMismatchedTextDataLength() {
        ObjectNode payload = validRequest("981");
        segment(payload).put("TextDataLength", "004");

        assertRule(payload, "SEG109-R-018");
    }

    @Test
    void rejectsMissingElectronicMailSegment() {
        ObjectNode payload = validRequest("981");
        ((ObjectNode) payload.path("Electronic Mail Request")).remove("Electronic Mail Data Segment");

        assertRule(payload, "SEG109-R-003");
    }

    @Test
    void acceptsOneDigitElement63() {
        ObjectNode payload = validRequest("981");
        ((ObjectNode) payload.path("Electronic Mail Request")).put("NumSegments", "1");

        assertThat(new Segment109PayloadValidator().validatePayload(payload).errors()).isEmpty();
    }

    @Test
    void rejectsSegment100InElectronicMailRequest() {
        ObjectNode payload = validRequest("981");
        ((ObjectNode) payload.path("Electronic Mail Request")).putObject("Standard Segment")
            .put("SegmentType", "100");

        assertRule(payload, "SEG109-R-001");
    }

    @Test
    void validatesMessageFixtures() throws IOException {
        Path fixtureDirectory = Paths.get("specifications", "ATL105", "test-input", "ai-solution",
            "test-data", "segment-109", "message");
        try (Stream<Path> fixtures = Files.list(fixtureDirectory)) {
            fixtures.filter(path -> path.toString().endsWith(".json")).sorted().forEach(path -> {
                try {
                    JsonNodeFixture.assertExpected(path);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            });
        }
    }

    private static final class JsonNodeFixture {
        private static void assertExpected(Path path) throws IOException {
            ObjectNode document = (ObjectNode) MAPPER.readTree(path.toFile());
            String expected = document.path("_meta").path("expected").asText();
            ValidationResult result = new Segment109PayloadValidator().validateFile(path);
            if ("ACCEPT".equals(expected)) {
                assertThat(result.errors()).as("%s should be accepted", path).isEmpty();
            } else {
                assertThat(result.errors()).as("%s should be rejected", path).isNotEmpty();
                for (JsonNode rule : document.path("_meta").path("expectedRules")) {
                    assertThat(result.errors()).extracting(error -> error.reason())
                        .as("%s should cite %s", path, rule.asText())
                        .anyMatch(reason -> reason.contains(rule.asText()));
                }
            }
        }
    }

    private static void assertRule(ObjectNode payload, String ruleId) {
        ValidationResult result = new Segment109PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains(ruleId));
    }

    private static ObjectNode validRequest(String promptCode) {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Electronic Mail Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "01");
        ObjectNode segment = request.putObject("Electronic Mail Data Segment");
        segment.put("SegmentType", "109");
        segment.put("SegmentLength", "060");
        segment.put("InformationByte", "0");
        segment.put("TerminalIdentifier", "SYNTHETIC01");
        segment.put("PromptCode", promptCode);
        segment.put("BlockNumber", "001");
        segment.put("SequenceNumber", "000001");
        segment.put("TextDataLength", "003");
        segment.put("TextData", "ABC");
        return payload;
    }

    private static ObjectNode segment(ObjectNode payload) {
        return (ObjectNode) payload.path("Electronic Mail Request").path("Electronic Mail Data Segment");
    }
}