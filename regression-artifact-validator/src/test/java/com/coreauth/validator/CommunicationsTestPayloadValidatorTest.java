package com.coreauth.validator;

import com.coreauth.validator.canonical.CommunicationsTestPayloadValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** Focused tests for the positional Communications Test request and response layouts. */
class CommunicationsTestPayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void acceptsCommunicationsTestRequest() {
        assertThat(new CommunicationsTestPayloadValidator().validatePayload(request()).errors()).isEmpty();
    }

    @Test
    void acceptsCommunicationsTestResponseWithoutOptionalDisplayText() {
        assertThat(new CommunicationsTestPayloadValidator().validatePayload(response(false)).errors()).isEmpty();
    }

    @Test
    void acceptsCommunicationsTestResponseWithPassedDisplayText() {
        assertThat(new CommunicationsTestPayloadValidator().validatePayload(response(true)).errors()).isEmpty();
    }

    @Test
    void rejectsSegment120ObjectInRequest() {
        ObjectNode payload = request();
        ((ObjectNode) payload.path(CommunicationsTestPayloadValidator.REQUEST_ROOT))
            .putObject("Network Management Message Segment").put("SegmentType", "120");

        assertRule(payload, "COMM-REQ-003");
    }

    @Test
    void rejectsNonZeroNumberOfSegments() {
        ObjectNode payload = request();
        ((ObjectNode) payload.path(CommunicationsTestPayloadValidator.REQUEST_ROOT)).put("NumSegments", "01");

        assertRule(payload, "COMM-REQ-002");
    }

    @Test
    void rejectsInvalidResponseConstants() {
        ObjectNode payload = response(true);
        ObjectNode response = (ObjectNode) payload.path(CommunicationsTestPayloadValidator.RESPONSE_ROOT);
        response.put("ResponseCode", "0");
        response.put("DownloadIndicator", "1");
        response.put("TerminalDisplayCommunicationsMessage", "FAILED");

        ValidationResult result = new CommunicationsTestPayloadValidator().validatePayload(payload);

        assertThat(result.errors()).extracting(error -> error.reason())
            .anyMatch(reason -> reason.contains("COMM-RESP-001"))
            .anyMatch(reason -> reason.contains("COMM-RESP-002"))
            .anyMatch(reason -> reason.contains("COMM-RESP-003"));
    }

    private static void assertRule(ObjectNode payload, String ruleId) {
        assertThat(new CommunicationsTestPayloadValidator().validatePayload(payload).errors())
            .anyMatch(error -> error.reason().contains(ruleId));
    }

    private static ObjectNode request() {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject(CommunicationsTestPayloadValidator.REQUEST_ROOT);
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "0");
        request.put("NetworkManagementMessage", "COMMTEST");
        return payload;
    }

    private static ObjectNode response(boolean withDisplayText) {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode response = payload.putObject(CommunicationsTestPayloadValidator.RESPONSE_ROOT);
        response.put("ResponseCode", "1");
        response.put("DownloadIndicator", "0");
        if (withDisplayText) {
            response.put("TerminalDisplayCommunicationsMessage", "COMM_TEST_PASSED");
        }
        return payload;
    }
}
