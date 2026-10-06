package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

/** Validates the positional Software Load request and response envelope from ATL105 Section 11.7.4. */
public final class SoftwareLoadPayloadValidator {
    public static final String REQUEST_ROOT = "Software Load Request";
    public static final String RESPONSE_ROOT = "Software Load Response";
    private static final String SOURCE = "SoftwareLoadPayload";
    private static final Pattern TERMINAL_ID = Pattern.compile("^[A-Za-z0-9]{13}$");
    private static final Pattern VERSION_4 = Pattern.compile("^[A-Za-z0-9]{4}$");
    private static final Pattern VERSION_8 = Pattern.compile("^[A-Za-z0-9]{8}$");
    private final ObjectMapper objectMapper;

    public SoftwareLoadPayloadValidator() { this(new ObjectMapper()); }
    SoftwareLoadPayloadValidator(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    public ValidationResult validateFile(Path file) {
        ValidationResult result = new ValidationResult(file == null ? "software-load-payload" : file.getFileName().toString());
        if (file == null) { result.addError(SOURCE, "AI JSON file is required"); return result; }
        try { validatePayload(objectMapper.readTree(file.toFile()), result); }
        catch (IOException e) { result.addError(SOURCE, "AI JSON is not readable: " + e.getMessage()); }
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("software-load-payload");
        validatePayload(payload, result);
        return result;
    }

    private static void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) { result.addError(SOURCE, "AI JSON root must be an object"); return; }
        boolean request = payload.path(REQUEST_ROOT).isObject();
        boolean response = payload.path(RESPONSE_ROOT).isObject();
        if (request == response) { result.addError(SOURCE, "AI JSON must contain exactly one Software Load Request or Response root"); return; }
        if (request) validateRequest(payload.path(REQUEST_ROOT), result); else validateResponse(payload.path(RESPONSE_ROOT), result);
    }

    private static void validateRequest(JsonNode request, ValidationResult result) {
        required(request, "InformationByte", "?", "SW-REQ-001", result);
        pattern(request, "TerminalIdentifier", TERMINAL_ID, "13 alphanumeric characters", "SW-REQ-002", result);
        required(request, "LoadType", "P", "SW-REQ-003", result);
        pattern(request, "HardwareVersion", VERSION_4, "4 alphanumeric characters", "SW-REQ-004", result);
        pattern(request, "SoftwareVersion", VERSION_8, "8 alphanumeric characters", "SW-REQ-005", result);
        pattern(request, "FirmwareVersion", VERSION_8, "8 alphanumeric characters", "SW-REQ-006", result);
    }

    private static void validateResponse(JsonNode response, ValidationResult result) {
        required(response, "StartOfDataBlockIndicator", ")", "SW-RESP-001", result);
        block(response, "DL4", 52, "SW-RESP-002", result);
        block(response, "DL5", 66, "SW-RESP-003", result);
    }

    private static void block(JsonNode response, String name, int maxLength, String rule, ValidationResult result) {
        JsonNode block = response.path(name);
        if (!block.isObject()) { result.addError(SOURCE, name + " block is required (" + rule + ")"); return; }
        String encodedLength = block.path("EncodedLength").asText(null);
        if (encodedLength != null && (!encodedLength.matches("^[0-9]+$") || Integer.parseInt(encodedLength) > maxLength)) {
            result.addError(SOURCE, name + " EncodedLength must not exceed " + maxLength + " (" + rule + ")");
        }
    }

    private static void required(JsonNode node, String field, String expected, String rule, ValidationResult result) {
        if (!expected.equals(node.path(field).asText(null))) result.addError(SOURCE, field + " must be " + expected + " (" + rule + ")");
    }
    private static void pattern(JsonNode node, String field, Pattern pattern, String description, String rule, ValidationResult result) {
        String value = node.path(field).asText(null);
        if (value == null || !pattern.matcher(value).matches()) result.addError(SOURCE, field + " must be " + description + " (" + rule + ")");
    }
}
