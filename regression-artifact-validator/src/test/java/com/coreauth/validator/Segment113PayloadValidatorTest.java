package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment113PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 1 (Coverage Closure) baseline tests for {@link Segment113PayloadValidator}.
 *
 * <p>Anchored to {@code specifications/ATL105/docs/specs/kb/segment-113/coverage/segment-113-rule-catalog.json}
 * and the synthetic fixtures under
 * {@code specifications/ATL105/test-input/ai-solution/test-data/segment-113/}.
 */
class Segment113PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG113_ROOT = Paths.get("specifications", "ATL105", "test-input", "ai-solution", "test-data", "segment-113");

    @Test
    void acceptsCheckPurchaseOriginalHappyPath() {
        ValidationResult result = new Segment113PayloadValidator()
            .validateFile(SEG113_ROOT.resolve(Path.of("lifecycle", "eca-check-purchase-original.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsVoidWithTraceId() {
        ValidationResult result = new Segment113PayloadValidator()
            .validateFile(SEG113_ROOT.resolve(Path.of("lifecycle", "ecatelecheck-void-with-trace-id.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsDenialRecordDeclineWithExtendedMicrData() {
        ValidationResult result = new Segment113PayloadValidator()
            .validateFile(SEG113_ROOT.resolve(Path.of("lifecycle", "ecatelecheck-denial-record-decline.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsDuplicateSegment113Occurrence() {
        ValidationResult result = new Segment113PayloadValidator()
            .validateFile(SEG113_ROOT.resolve(Path.of("rejection", "segment-113-duplicate-occurrence.synthetic.json")));

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-015"));
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("SegmentType", "999");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-003"));
    }

    @Test
    void rejectsSegmentLengthWithNonNumericValue() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("SegmentLength", "AB1");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-004"));
    }

    @Test
    void rejectsSegmentLengthOverMaximum() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("SegmentLength", "157");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-005"));
    }

    @Test
    void rejectsMissingClerkId() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .remove("EcaClerkId");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-008"));
    }

    @Test
    void rejectsClerkIdWithInvalidCharacter() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("EcaClerkId", "CL#1");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-008"));
    }

    @Test
    void rejectsProductCodeWithInvalidCharacter() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("EcaProductCode", "PC#01");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-009"));
    }

    @Test
    void rejectsPhoneNumberWithNonNumericContent() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("EcaPhoneNumber", "555PHONE1");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-010"));
    }

    @Test
    void rejectsTraceIdOverMaximum() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("EcaTraceId", "1234567890123456789012X");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-011"));
    }

    @Test
    void rejectsMerchantTraceIdOverMaximum() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("MerchantTraceId", "12345678901234567890123456");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-012"));
    }

    @Test
    void rejectsDenialRecordNumberOverMaximum() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("DenialRecordNumber", "DR123456");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-013"));
    }

    @Test
    void rejectsExtendedMicrDataOverMaximum() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request").path("ECA/TeleCheck Data Segment"))
            .put("ExtendedMicrData", "1".repeat(66));

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-014"));
    }

    @Test
    void allowsMissingEcaTeleCheckDataSegmentSinceItIsConditional() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request")).remove("ECA/TeleCheck Data Segment");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsEcaTeleCheckDataSegmentWithoutStandardSegment() {
        ObjectNode payload = validEcaPayload();
        ((ObjectNode) payload.path("ECA/TeleCheck Service Transaction Request")).remove("Standard Segment");

        ValidationResult result = new Segment113PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG113-R-001"));
    }

    private static ObjectNode validEcaPayload() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("ECA/TeleCheck Service Transaction Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "03");

        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100");
        standard.put("SegmentLength", "070");
        standard.put("InformationByte", "0");
        standard.put("TerminalID", "EC500001");
        standard.putObject("PromptCode").put("TransactionType", "0").put("CardType", "046");
        standard.put("SequenceNumber", "800101");
        standard.put("PartialApprovalIndicator", "0");

        ObjectNode eca = request.putObject("ECA/TeleCheck Data Segment");
        eca.put("SegmentType", "113");
        eca.put("SegmentLength", "030");
        eca.put("EcaClerkId", "123456");
        eca.put("EcaProductCode", "GROC01");
        return payload;
    }
}
