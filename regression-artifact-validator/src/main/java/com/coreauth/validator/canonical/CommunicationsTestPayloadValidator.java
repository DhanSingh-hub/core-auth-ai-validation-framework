package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

/** Validates the positional Communications Test request and response layouts from ATL105 Section 11.6. */
public final class CommunicationsTestPayloadValidator {
    public static final String REQUEST_ROOT = "Communications Test Request";
    public static final String RESPONSE_ROOT = "Communications Test Response";
    private static final String SOURCE = "CommunicationsTestPayload";
    private static final Pattern MESSAGE_TYPE = Pattern.compile("^ATL105$");
    private static final Pattern ELEMENT_63 = Pattern.compile("^[0-9]{1,2}$");
    private static final Pattern RESPONSE_CODE = Pattern.compile("^1$");
    private static final Pattern DOWNLOAD_INDICATOR = Pattern.compile("^0$");
    private static final Pattern COMM_TEST_PASSED = Pattern.compile("^COMM_TEST_PASSED$");

    private final ObjectMapper objectMapper;

    public CommunicationsTestPayloadValidator() {
        this(new ObjectMapper());
    }

    CommunicationsTestPayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValidationResult validateFile(Path file) {
        String documentId = file == null ? "communications-test-payload" : file.getFileName().toString();
        ValidationResult result = new ValidationResult(documentId);
        if (file == null) {
            result.addError(SOURCE, "AI JSON file is required");
            return result;
        }
        try {
            validatePayload(objectMapper.readTree(file.toFile()), result);
        } catch (IOException exception) {
            result.addError(SOURCE, "AI JSON is not readable: " + exception.getMessage());
        }
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("communications-test-payload");
        validatePayload(payload, result);
        return result;
    }

    private static void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) {
            result.addError(SOURCE, "AI JSON root must be an object");
            return;
        }
        boolean hasRequest = payload.path(REQUEST_ROOT).isObject();
        boolean hasResponse = payload.path(RESPONSE_ROOT).isObject();
        if (hasRequest == hasResponse) {
            result.addError(SOURCE, "AI JSON must contain exactly one Communications Test Request or Response root");
            return;
        }
        if (hasRequest) {
            validateRequest(payload.path(REQUEST_ROOT), result);
        } else {
            validateResponse(payload.path(RESPONSE_ROOT), result);
        }
    }

    private static void validateRequest(JsonNode request, ValidationResult result) {
        requirePattern(request, "MessageType", MESSAGE_TYPE, "ATL105", "COMM-REQ-001", result);
        String numberOfSegments = text(request, "NumSegments");
        if (!matches(numberOfSegments, ELEMENT_63)) {
            result.addError(SOURCE, "Communications Test Request.NumSegments (Element 63) must be one or two numeric characters (COMM-REQ-002)");
        } else if (!"0".equals(numberOfSegments)) {
            result.addError(SOURCE, "Communications Test Request.NumSegments must be 0 because COMMTEST is an element, not a data segment (COMM-REQ-002)");
        }
        requireValue(request, "NetworkManagementMessage", "COMMTEST", "COMM-REQ-003", result);
        request.fields().forEachRemaining(entry -> {
            if (entry.getValue().isObject() && entry.getValue().has("SegmentType")) {
                result.addError(SOURCE, "Communications Test Request must not contain a Segment object; Element 120 is NetworkManagementMessage (COMM-REQ-003)");
            }
        });
    }

    private static void validateResponse(JsonNode response, ValidationResult result) {
        requirePattern(response, "ResponseCode", RESPONSE_CODE, "fixed value 1", "COMM-RESP-001", result);
        requirePattern(response, "DownloadIndicator", DOWNLOAD_INDICATOR, "fixed value 0", "COMM-RESP-002", result);
        if (response.has("TerminalDisplayCommunicationsMessage")
            && !matches(text(response, "TerminalDisplayCommunicationsMessage"), COMM_TEST_PASSED)) {
            result.addError(SOURCE, "Communications Test Response.TerminalDisplayCommunicationsMessage must be COMM_TEST_PASSED when present (COMM-RESP-003)");
        }
    }

    private static void requireValue(JsonNode node, String field, String expected, String ruleId, ValidationResult result) {
        if (!expected.equals(text(node, field))) {
            result.addError(SOURCE, field + " must be " + expected + " (" + ruleId + ")");
        }
    }

    private static void requirePattern(JsonNode node, String field, Pattern pattern, String description,
                                       String ruleId, ValidationResult result) {
        if (!matches(text(node, field), pattern)) {
            result.addError(SOURCE, field + " must be " + description + " (" + ruleId + ")");
        }
    }

    private static String text(JsonNode node, String field) {
        return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null;
    }

    private static boolean matches(String value, Pattern pattern) {
        return value != null && pattern.matcher(value).matches();
    }
}
