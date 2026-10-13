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

        assertOnlySourceNumericViolationsInPinnedSynthetic(result);
    }

    @Test
    void acceptsAuthCompletionCarryingFleetTags() {
        ValidationResult result = new Segment101PayloadValidator()
            .validateFile(SEG101_ROOT.resolve(Path.of("lifecycle", "fleet-auth-completion.synthetic.json")));

        assertOnlySourceNumericViolationsInPinnedSynthetic(result);
    }

    @Test
    void acceptsFleetCancellationWithoutFleetTags() {
        ValidationResult result = new Segment101PayloadValidator()
            .validateFile(SEG101_ROOT.resolve(Path.of("lifecycle", "fleet-auth-cancellation.synthetic.json")));

        assertOnlySourceNumericViolationsInPinnedSynthetic(result);
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

    @Test
    void validAuthPayloadHasNoErrors() {
        assertThat(new Segment101PayloadValidator().validatePayload(validAuthPayload()).errors()).isEmpty();
        assertThat(new Segment101PayloadValidator().validatePayload(validCompletionPayload()).errors())
                .noneMatch(error -> error.reason().matches(".*SEG101-R-01[0-3].*"));
    }

    @Test
    void rejectsAlphabeticReferencedNumericFleetElements() {
        String[][] fields = {{"VehicleNumber", "SEG101-R-010"}, {"JobNumber", "SEG101-R-011"},
            {"DriverIdentificationNumber", "SEG101-R-012"}, {"FleetEmployeeNumber", "SEG101-R-013"}};
        for (String[] field : fields) {
            ObjectNode payload = validAuthPayload();
            ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment")).put(field[0], "123456789A");
            assertThat(new Segment101PayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains(field[1]));
            ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment")).put(field[0], "0123456789");
            assertThat(new Segment101PayloadValidator().validatePayload(payload).errors()).isEmpty();
            ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment")).put(field[0], "01234567890");
            assertThat(new Segment101PayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains(field[1]));
        }
    }

    @Test
    void dobTagDataIsNumericWithMaximumEight() {
        for (String data : new String[] {"1", "1980010", "19800101"}) {
            ObjectNode payload = validCompletionPayload();
            ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment")).put("FleetTag1", "DOB" + data);
            assertThat(new Segment101PayloadValidator().validatePayload(payload).errors()).isEmpty();
        }
        for (String data : new String[] {"198001011", "1980O101"}) {
            ObjectNode payload = validCompletionPayload();
            ((ObjectNode) payload.path("Financial Request").path("Fleet Data Segment")).put("FleetTag1", "DOB" + data);
            assertThat(new Segment101PayloadValidator().validatePayload(payload).errors())
                .anyMatch(error -> error.reason().contains("SEG101-R-024"));
        }
    }

    @Test
    void hostPromptsAcceptMatchingDeviceTypeTerminal() throws Exception {
        var segment = MAPPER.readTree("{\"FleetTag1\":\"DLSNY0\"}");
        var request = MAPPER.readTree("{\"MessageType\":\"0220\"}");
        var standard = MAPPER.readTree("{\"TerminalIdentifier\":\"+*13500101001\"}");
        var evidence = MAPPER.readTree("{\"context\":{\"hostPrompts\":{\"supported\":true,\"TerminalIdentifier\":\"+*13500101001\"}}}");
        assertThat(new Segment101PayloadValidator().validateActualTagContext(segment, request, standard, evidence))
            .anyMatch(f -> f.scope().equals("actual-fleet-host-prompts") && f.status().name().equals("CHECKS_PASSED"));
        var mismatched = MAPPER.readTree("{\"context\":{\"hostPrompts\":{\"supported\":true,\"TerminalIdentifier\":\"++13500101001\"}}}");
        assertThat(new Segment101PayloadValidator().validateActualTagContext(segment, request, standard, mismatched))
            .anyMatch(f -> f.scope().equals("actual-fleet-host-prompts") && f.status().name().equals("INVALID"));
    }

    @Test
    void actualTagGrammarSeparatesCodeWidthDataCapAndDictionaryData() throws Exception {
        var validator = new Segment101PayloadValidator();
        assertThat(validator.validateActualTagGrammar(MAPPER.readTree("{}"))).isEmpty();
        assertThat(validator.validateActualTagGrammar(MAPPER.readTree("{\"FleetTag1\":\"DL\"}")))
            .anyMatch(f -> f.scope().equals("actual-fleet-tag-code") && f.status().name().equals("INVALID"));
        assertThat(validator.validateActualTagGrammar(MAPPER.readTree("{\"FleetTag1\":\"PON" + "A".repeat(32) + "\"}")))
            .anyMatch(f -> f.scope().equals("actual-fleet-tag-data-cap") && f.status().name().equals("INVALID"));
        assertThat(validator.validateActualTagGrammar(MAPPER.readTree("{\"FleetTag1\":\"PON" + "A".repeat(31) + "\"}")))
            .allMatch(f -> f.status().name().equals("CHECKS_PASSED"));
        assertThat(validator.validateActualTagGrammar(MAPPER.readTree("{\"FleetTag1\":\"\"}")))
            .allMatch(f -> f.status().name().equals("REVIEW_REQUIRED"));
    }

    @Test
    void actualPlacementAndPresenceNeverFillMissingObservations() throws Exception {
        var validator = new Segment101PayloadValidator();
        assertThat(validator.validateActualPlacement(MAPPER.readTree("{\"DataSection\":\"3\",\"FieldNumber\":\"12\"}")).getFirst().status().name())
            .isEqualTo("CHECKS_PASSED");
        assertThat(validator.validateActualPlacement(MAPPER.readTree("{\"DataSection\":\"2\"}")).getFirst().status().name()).isEqualTo("INVALID");
        assertThat(validator.validateActualPlacement(MAPPER.readTree("{\"DataSection\":3}")).getFirst().status().name()).isEqualTo("INVALID");
        assertThat(validator.validateActualPlacement(MAPPER.readTree("{}")).getFirst().status().name()).isEqualTo("REVIEW_REQUIRED");
        var fleet = MAPPER.readTree("{\"context\":{\"fleetCardTransaction\":true}}");
        assertThat(validator.validateActualPresence(false, false, fleet).getFirst().status().name()).isEqualTo("INVALID");
        assertThat(validator.validateActualPresence(false, true, fleet).getFirst().status().name()).isEqualTo("CHECKS_PASSED");
        assertThat(validator.validateActualPresence(true, false, fleet).getFirst().status().name()).isEqualTo("CHECKS_PASSED");
        assertThat(validator.validateActualPresence(false, false, MAPPER.readTree("{}")).getFirst().status().name()).isEqualTo("REVIEW_REQUIRED");
        assertThat(validator.validateActualPresence(true, false, MAPPER.readTree("{\"context\":{\"fleetCardTransaction\":\"true\"}}"))
            .getFirst().status().name()).isEqualTo("INVALID");
    }

    /**
     * The pinned lifecycle synthetics are producer input and are not edited. Their alphabetic VH/DRV/EMP
     * identifiers violate the source numeric Elements 108/31/40, so only those findings are allowed.
     */
    private static void assertOnlySourceNumericViolationsInPinnedSynthetic(ValidationResult result) {
        assertThat(result.errors()).isNotEmpty().allMatch(error -> error.reason().contains("SEG101-R-010")
            || error.reason().contains("SEG101-R-012") || error.reason().contains("SEG101-R-013"));
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
        fleet.put("VehicleNumber", "0018");
        fleet.put("DriverIdentificationNumber", "00042");
        fleet.put("FleetEmployeeNumber", "01997");
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
