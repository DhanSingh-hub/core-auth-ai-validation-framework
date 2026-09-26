package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100AiJsonCompatibilityValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100AiJsonCompatibilityValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void acceptsConverterReadyFinancialRequest() {
        var result = new Segment100AiJsonCompatibilityValidator().validatePayload(validPayload());

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsWrongMessageType() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request")).put("MessageType", "OTHER");

        var result = new Segment100AiJsonCompatibilityValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("MessageType must be ATL105"));
    }

    @Test
    void rejectsSegmentCountMismatch() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request")).put("NumSegments", "02");

        var result = new Segment100AiJsonCompatibilityValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("does not match segment objects found: 3"));
    }

    @Test
    void rejectsMalformedPromptCode() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Standard Segment").path("PromptCode"))
                .put("CardType", "?");

        var result = new Segment100AiJsonCompatibilityValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("CardType must be 2-3 alphanumeric characters"));
    }

    @Test
    void rejectsMissingSequenceNumber() {
        ObjectNode payload = validPayload();
        ((ObjectNode) payload.path("Financial Request").path("Standard Segment")).remove("SequenceNumber");

        var result = new Segment100AiJsonCompatibilityValidator().validatePayload(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SequenceNumber must be six digits"));
    }

    private static ObjectNode validPayload() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "03");
        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100");
        standard.put("SegmentLength", "086");
        standard.put("InformationByte", "0");
        standard.put("TerminalID", "HC375003");
        standard.putObject("PromptCode").put("TransactionType", "0").put("CardType", "020");
        standard.put("SequenceNumber", "842262");
        standard.put("PartialApprovalIndicator", "1");
        request.putObject("Variable Info Segment").put("SegmentType", "111").put("SegmentLength", "071");
        request.putObject("EMV Data Segment").put("SegmentType", "130").put("SegmentLength", "203");
        return payload;
    }
}
