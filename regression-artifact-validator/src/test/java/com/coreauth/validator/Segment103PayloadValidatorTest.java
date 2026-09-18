package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment103PayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Item 1 (Coverage Closure) baseline tests for {@link Segment103PayloadValidator}.
 *
 * <p>Anchored to {@code docs/specs/kb/segment-103/coverage/segment-103-rule-catalog.json}
 * and the synthetic fixtures under
 * {@code test-input/ai-solution/test-data/segment-103/}.
 */
class Segment103PayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path SEG103_ROOT = Paths.get("test-input", "ai-solution", "test-data", "segment-103");

    @Test
    void acceptsEbtPurchaseOriginalHappyPath() {
        ValidationResult result = new Segment103PayloadValidator()
            .validateFile(SEG103_ROOT.resolve(Path.of("lifecycle", "ebt-purchase-original.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsEwicPurchaseCompletionWithWicAndEbtProgramData() {
        ValidationResult result = new Segment103PayloadValidator()
            .validateFile(SEG103_ROOT.resolve(Path.of("lifecycle", "ewic-purchase-completion.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void acceptsEwicVoucherClearWithTagIt() {
        ValidationResult result = new Segment103PayloadValidator()
            .validateFile(SEG103_ROOT.resolve(Path.of("lifecycle", "ewic-voucher-clear.synthetic.json")));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsDuplicateSegment103Occurrence() {
        ValidationResult result = new Segment103PayloadValidator()
            .validateFile(SEG103_ROOT.resolve(Path.of("rejection", "segment-103-duplicate-occurrence.synthetic.json")));

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-018"));
    }

    @Test
    void rejectsWrongSegmentType() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .put("SegmentType", "999");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-003"));
    }

    @Test
    void rejectsSegmentLengthWithNonNumericValue() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .put("SegmentLength", "AB1");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-004"));
    }

    @Test
    void rejectsSegmentLengthOverMaximum() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .put("SegmentLength", "3335");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-005"));
    }

    @Test
    void rejectsClerkIdWithNonNumericContent() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .put("ClerkId", "CLERK12A");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-009"));
    }

    @Test
    void rejectsVoucherIdWithNonNumericContent() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .put("VoucherId", "VOUCH1XY");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-010"));
    }

    @Test
    void rejectsWicDiscountAmountWithBadPositionalPrefix() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .put("WicDiscountAmount", "00000000000000000000");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-011"));
    }

    @Test
    void rejectsWicProductDataTotalLengthMismatch() {
        ObjectNode payload = validEbtPayload();
        ObjectNode wicProductData = ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .putObject("WicProductData");
        wicProductData.put("totalLength", "0999");
        wicProductData.put("data", "EF20270101");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-013"));
    }

    @Test
    void rejectsEbtProgramDataUnknownTag() {
        ObjectNode payload = validEbtPayload();
        ObjectNode ebtProgramData = ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .putObject("EbtProgramData");
        ebtProgramData.put("totalLength", "010");
        ebtProgramData.putArray("subelements").addObject().put("tag", "99").put("data", "ABCDEF");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-016"));
    }

    @Test
    void rejectsEbtProgramDataTag50WithoutAccountType98() {
        ObjectNode payload = validEbtPayload();
        ObjectNode ebtProgramData = ((ObjectNode) payload.path("Financial Request").path("EBT Data Segment"))
            .putObject("EbtProgramData");
        ebtProgramData.put("totalLength", "044");
        ebtProgramData.putArray("subelements").addObject().put("tag", "50").put("data", "00000000050000");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-017"));
    }

    @Test
    void rejectsMissingEbtDataSegment() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request")).remove("EBT Data Segment");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-002"));
    }

    @Test
    void rejectsEbtDataSegmentWithoutStandardSegment() {
        ObjectNode payload = validEbtPayload();
        ((ObjectNode) payload.path("Financial Request")).remove("Standard Segment");

        ValidationResult result = new Segment103PayloadValidator().validatePayload(payload);

        assertThat(result.errors())
            .anyMatch(error -> error.reason().contains("SEG103-R-001"));
    }

    private static ObjectNode validEbtPayload() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "02");

        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100");
        standard.put("SegmentLength", "070");
        standard.put("InformationByte", "0");
        standard.put("TerminalID", "EB500001");
        standard.putObject("PromptCode").put("TransactionType", "0").put("CardType", "050");
        standard.put("SequenceNumber", "600101");
        standard.put("PartialApprovalIndicator", "0");

        ObjectNode ebt = request.putObject("EBT Data Segment");
        ebt.put("SegmentType", "103");
        ebt.put("SegmentLength", "018");
        ebt.put("ClerkId", "0000012345");
        ebt.put("VoucherId", "0000098765");
        return payload;
    }
}
