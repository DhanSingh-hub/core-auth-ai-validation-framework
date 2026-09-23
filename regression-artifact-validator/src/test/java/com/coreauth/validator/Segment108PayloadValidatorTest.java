package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment108PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 1 (Coverage Closure) baseline tests for {@link Segment108PayloadValidator}.
 *
 * <p>Anchored to {@code specifications/ATL105/docs/specs/kb/segment-108/coverage/segment-108-rule-catalog.json}
 * and the synthetic fixtures under
 * {@code specifications/ATL105/test-input/ai-solution/test-data/segment-108/}.
 */
class Segment108PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG108_ROOT = Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-108");

    @Test
    void acceptsLoyaltyPurchaseOriginalHappyPath() {
        ValidationResult result = new Segment108PayloadValidator()
            .validateFile(SEG108_ROOT.resolve(Path.of("lifecycle", "loyalty-purchase-original.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsLoyaltyPointsRedemption() {
        ValidationResult result = new Segment108PayloadValidator()
            .validateFile(SEG108_ROOT.resolve(Path.of("lifecycle", "loyalty-points-redemption.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsLoyaltyAccountInquiryCardNotPresent() {
        ValidationResult result = new Segment108PayloadValidator()
            .validateFile(SEG108_ROOT.resolve(Path.of("lifecycle", "loyalty-account-inquiry-card-not-present.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsDuplicateSegment108Occurrence() {
        ValidationResult result = new Segment108PayloadValidator()
            .validateFile(SEG108_ROOT.resolve(Path.of("rejection", "segment-108-duplicate-occurrence.synthetic.json")));

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-021"));
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("SegmentType", "999");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-003"));
    }

    @Test
    void rejectsSegmentLengthWithNonNumericValue() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("SegmentLength", "AB1");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-004"));
    }

    @Test
    void rejectsSegmentLengthOverMaximum() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("SegmentLength", "143");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-005"));
    }

    @Test
    void rejectsMissingLoyaltyProgramId() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .remove("LoyaltyProgramId");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-008"));
    }

    @Test
    void rejectsLoyaltyProgramIdWithNonNumericContent() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("LoyaltyProgramId", "PRG1AB");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-008"));
    }

    @Test
    void rejectsLoyaltyAccountNumberWithNonNumericContent() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("LoyaltyAccountNumber", "ACCT123XYZ");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-009"));
    }

    @Test
    void rejectsUpdateCodeOutsideDocumentedSet() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("UpdateCode", "Z");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-013"));
    }

    @Test
    void rejectsMissingPaymentTenderType() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .remove("PaymentTenderType");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-017"));
    }

    @Test
    void rejectsPaymentTenderTypeOutsideDocumentedSet() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("PaymentTenderType", "ZZ");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-017"));
    }

    @Test
    void rejectsLoyaltyTrack2DataOverMaximum() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("LoyaltyTrack2Data", "1".repeat(39));

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-018"));
    }

    @Test
    void rejectsLoyaltyInformationVersionOutsideDocumentedSet() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("LoyaltyInformationVersion", "9");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-019"));
    }

    @Test
    void acceptsExpirationDateDefaultSentinelValue() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("ExpirationDate", "1249");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsExpirationDateWithInvalidMonth() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request").path("Loyalty Card Data Segment"))
            .put("ExpirationDate", "1349");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-016"));
    }

    @Test
    void rejectsMissingLoyaltyCardDataSegment() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request")).remove("Loyalty Card Data Segment");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-002"));
    }

    @Test
    void rejectsLoyaltyCardDataSegmentWithoutStandardSegment() {
        ObjectNode payload = validLoyaltyPayload();
        ((ObjectNode) payload.path("Loyalty Card Transaction Request")).remove("Standard Segment");

        ValidationResult result = new Segment108PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG108-R-001"));
    }

    private static ObjectNode validLoyaltyPayload() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Loyalty Card Transaction Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "02");

        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100");
        standard.put("SegmentLength", "070");
        standard.put("InformationByte", "0");
        standard.put("TerminalID", "LC500001");
        standard.putObject("PromptCode").put("TransactionType", "0").put("CardType", "060");
        standard.put("SequenceNumber", "700101");
        standard.put("PartialApprovalIndicator", "0");

        ObjectNode loyalty = request.putObject("Loyalty Card Data Segment");
        loyalty.put("SegmentType", "108");
        loyalty.put("SegmentLength", "042");
        loyalty.put("LoyaltyProgramId", "100200");
        loyalty.put("LoyaltyAccountNumber", "400500600700");
        loyalty.put("UpdateCode", "S");
        loyalty.put("PaymentTenderType", "VS");
        loyalty.put("LoyaltyInformationVersion", "1");
        return payload;
    }
}
