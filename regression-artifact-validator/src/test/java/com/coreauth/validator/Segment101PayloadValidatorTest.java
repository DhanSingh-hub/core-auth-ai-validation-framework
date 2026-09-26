package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment101PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 1 (Coverage Closure) baseline tests for {@link Segment101PayloadValidator}.
 *
 * <p>Anchored to {@code specifications/ATL105/docs/specs/kb/segment-101/coverage/segment-101-rule-catalog.json}
 * and the synthetic fixtures under
 * {@code specifications/ATL105/test-input/ai-solution/test-data/segment-101/}.
 */
class Segment101PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG101_ROOT = Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-101");

    @Test
    void acceptsFleetAuthOriginalHappyPath() {
        ValidationResult result = new Segment101PayloadValidator()
            .validateFile(SEG101_ROOT.resolve(Path.of("lifecycle", "fleet-auth-original.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsAuthCompletionCarryingFleetTags() {
        ValidationResult result = new Segment101PayloadValidator()
            .validateFile(SEG101_ROOT.resolve(Path.of("lifecycle", "fleet-auth-completion.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsFleetCancellationWithoutFleetTags() {
        ValidationResult result = new Segment101PayloadValidator()
            .validateFile(SEG101_ROOT.resolve(Path.of("lifecycle", "fleet-auth-cancellation.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsCoexistenceWithEnhancedFleetSegment145() {
        ValidationResult result = new Segment101PayloadValidator()
            .validateFile(SEG101_ROOT.resolve(Path.of("rejection", "fleet-and-145-mutual-exclusion.synthetic.json")));

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-003"));
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("SegmentType", "999");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-004"));
    }

    @Test
    void rejectsSegmentLengthWithNonNumericValue() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("SegmentLength", "AB1");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-005"));
    }

    @Test
    void rejectsBaseSegmentLengthOverSixtyOne() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("SegmentLength", "099");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-006"));
    }

    @Test
    void rejectsOdometerWithNonNumericContent() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("Odometer", "ABCDEFGH");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-009"));
    }

    @Test
    void rejectsUserIdOfAllZeros() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("UserId", "000000000000");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-018"));
    }

    @Test
    void rejectsUnknownFleetTagCode() {
        ObjectNode payload = validCompletionPayload();
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("FleetTag1", "XYZbadpayload");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-023"));
    }

    @Test
    void rejectsFleetTagInNonCompletionMessage() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("FleetTag1", "PONWO-101");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-020"));
    }

    @Test
    void rejectsMissingFleetDataSegment() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request")).remove("Fleet Data Segment");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-002"));
    }

    @Test
    void rejectsFleetDataWithoutStandardSegment() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request")).remove("Standard Segment");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-001"));
    }

    @Test
    void rejectsFleetTagPayloadFailingPerCodeFormat() {
        ObjectNode payload = validCompletionPayload();
        // TLH must be numeric 1-6; put alphabetic value to trigger SEG101-R-024
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("FleetTag2", "TLHabcdef");

        ValidationResult result = new Segment101PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG101-R-024"));
    }

    private static ObjectNode validAuthPayload() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "02");

        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100");
        standard.put("SegmentLength", "086");
        standard.put("InformationByte", "0");
        standard.put("TerminalID", "FL500001");
        standard.putObject("PromptCode").put("TransactionType", "0").put("CardType", "060");
        standard.put("SequenceNumber", "500101");
        standard.put("PartialApprovalIndicator", "0");

        ObjectNode fleet = request.putObject("Fleet Data Segment");
        fleet.put("SegmentType", "101");
        fleet.put("SegmentLength", "041");
        fleet.put("Odometer", "00087652");
        fleet.put("VehicleNumber", "VH0018");
        fleet.put("DriverIdentificationNumber", "DRV00042");
        fleet.put("FleetEmployeeNumber", "EMP01997");
        fleet.put("UserId", "USR000042");
        return payload;
    }

    private static ObjectNode validCompletionPayload() {
        ObjectNode payload = validAuthPayload();
        ((ObjectNode) payload.path("Financial Request").path("Standard Segment").path("PromptCode"))
            .put("TransactionType", "S");
        ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment"))
            .put("SegmentLength", "128");
        return payload;
    }
}
