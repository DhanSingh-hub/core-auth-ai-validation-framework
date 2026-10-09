package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

/** Section 12.3 adapter for the shared product-segment checks. */
public final class Segment102PayloadValidator {
    private final ObjectMapper mapper = new ObjectMapper();

    public ValidationResult validateFile(Path file) {
        if (file == null) {
            ValidationResult result = new ValidationResult("segment-102-payload");
            result.addError("Segment102Payload", "AI JSON file is required");
            return result;
        }
        try {
            return validatePayload(mapper.readTree(file.toFile()));
        } catch (IOException exception) {
            ValidationResult result = new ValidationResult(file.getFileName().toString());
            result.addError("Segment102Payload", "AI JSON is not readable: " + exception.getMessage());
            return result;
        }
    }

    public ValidationResult validatePayload(JsonNode payload) {
        return new ProductSegmentPayloadValidator().validate(payload, "102");
    }
}
