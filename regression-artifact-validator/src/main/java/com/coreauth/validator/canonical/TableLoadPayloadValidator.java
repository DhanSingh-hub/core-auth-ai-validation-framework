package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.regex.Pattern;

/** Validates the positional Table Load Request and its four documented response blocks. */
public final class TableLoadPayloadValidator {
    public static final String REQUEST_ROOT = "Table Load Request";
    public static final String RESPONSE_ROOT = "Table Load Response";
    private static final String SOURCE = "TableLoadPayload";
    private static final Pattern TERMINAL_ID = Pattern.compile("^[A-Za-z0-9]{13}$");
    private static final Pattern VERSION_4 = Pattern.compile("^[A-Za-z0-9]{4}$");
    private static final Pattern VERSION_8 = Pattern.compile("^[A-Za-z0-9]{8}$");
    private static final Set<String> BLOCKS = Set.of("DL1", "DL2", "DL3", "DL6");

    private final ObjectMapper objectMapper;

    public TableLoadPayloadValidator() { this(new ObjectMapper()); }
    TableLoadPayloadValidator(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    public ValidationResult validateFile(Path file) {
        ValidationResult result = new ValidationResult(file == null ? "table-load-payload" : file.getFileName().toString());
        if (file == null) { result.addError(SOURCE, "AI JSON file is required"); return result; }
        try { validatePayload(objectMapper.readTree(file.toFile()), result); }
        catch (IOException e) { result.addError(SOURCE, "AI JSON is not readable: " + e.getMessage()); }
        return result;
    }

    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("table-load-payload");
        validatePayload(payload, result);
        return result;
    }

    private static void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) { result.addError(SOURCE, "AI JSON root must be an object"); return; }
        boolean request = payload.path(REQUEST_ROOT).isObject();
        boolean response = payload.path(RESPONSE_ROOT).isObject();
        if (request == response) { result.addError(SOURCE, "AI JSON must contain exactly one Table Load Request or Response root"); return; }
        if (request) validateRequest(payload.path(REQUEST_ROOT), result); else validateResponse(payload.path(RESPONSE_ROOT), result);
    }

    private static void validateRequest(JsonNode request, ValidationResult result) {
        required(request, "InformationByte", "?", "TABLE-REQ-001", result);
        pattern(request, "TerminalIdentifier", TERMINAL_ID, "13 alphanumeric characters", "TABLE-REQ-002", result);
        required(request, "LoadType", "P", "TABLE-REQ-003", result);
        pattern(request, "HardwareVersion", VERSION_4, "4 alphanumeric characters", "TABLE-REQ-004", result);
        pattern(request, "SoftwareVersion", VERSION_8, "8 alphanumeric characters", "TABLE-REQ-005", result);
        pattern(request, "FirmwareVersion", VERSION_8, "8 alphanumeric characters", "TABLE-REQ-006", result);
    }

    private static void validateResponse(JsonNode response, ValidationResult result) {
        for (String block : new String[]{"DL1", "DL2", "DL3"}) {
            if (!response.path(block).isObject()) result.addError(SOURCE, block + " block is required (TABLE-RESP-00" + (block.equals("DL1") ? "1" : block.equals("DL2") ? "2" : "3") + ")");
        }
        for (var fields = response.fields(); fields.hasNext();) {
            var entry = fields.next();
            if (!BLOCKS.contains(entry.getKey())) result.addError(SOURCE, "Unknown Table Load Response block " + entry.getKey() + " (TABLE-RESP-004)");
        }
        JsonNode dl1 = response.path("DL1");
        if (dl1.isObject() && "173".equals(text(dl1, "CardType")) && !response.path("DL6").isObject()) {
            result.addError(SOURCE, "DL6 is required when DL1 contains Card Type 173 (TABLE-RESP-005)");
        }
    }

    private static void required(JsonNode node, String field, String expected, String rule, ValidationResult result) {
        if (!expected.equals(text(node, field))) result.addError(SOURCE, field + " must be " + expected + " (" + rule + ")");
    }
    private static void pattern(JsonNode node, String field, Pattern pattern, String desc, String rule, ValidationResult result) {
        String value=text(node,field); if (value==null || !pattern.matcher(value).matches()) result.addError(SOURCE, field + " must be " + desc + " (" + rule + ")");
    }
    private static String text(JsonNode node, String field) { return node.has(field) && !node.path(field).isNull() ? node.path(field).asText() : null; }
}
