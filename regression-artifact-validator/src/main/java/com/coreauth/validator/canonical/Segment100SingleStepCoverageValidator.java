package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/** Validates single-step AI JSON coverage for all standard Segment 100 transaction codes. */
public final class Segment100SingleStepCoverageValidator {
    private final ObjectMapper objectMapper;
    private final Segment100TransactionTypeOracle oracle;

    public Segment100SingleStepCoverageValidator() {
        this(new ObjectMapper(), new Segment100TransactionTypeOracle());
    }

    Segment100SingleStepCoverageValidator(ObjectMapper objectMapper, Segment100TransactionTypeOracle oracle) {
        this.objectMapper = objectMapper;
        this.oracle = oracle;
    }

    public ValidationResult validateDirectory(Path aiTestDataDirectory) {
        ValidationResult result = new ValidationResult("segment-100-single-step-coverage");
        if (aiTestDataDirectory == null || !Files.isDirectory(aiTestDataDirectory)) {
            result.addError("Segment100SingleStepCoverage", "AI test-data directory is required");
            return result;
        }

        Set<String> discoveredCodes = new LinkedHashSet<>();
        try (var files = Files.list(aiTestDataDirectory)) {
            files.filter(path -> path.getFileName().toString().endsWith(".json"))
                    .sorted()
                    .forEach(path -> validateFile(path, discoveredCodes, result));
        } catch (IOException exception) {
            result.addError("Segment100SingleStepCoverage", "Unable to read AI test-data directory: " + exception.getMessage());
            return result;
        }

        for (String expectedCode : oracle.expectedCodeMatrix().keySet()) {
            if (!discoveredCodes.contains(expectedCode)) {
                result.addError("Segment100SingleStepCoverage", "Missing single-step AI artifact for transaction type " + expectedCode);
            }
        }
        return result;
    }

    private void validateFile(Path path, Set<String> discoveredCodes, ValidationResult result) {
        try {
            JsonNode payload = objectMapper.readTree(path.toFile());
            ValidationResult payloadResult = oracle.validateAiPayload(path.getFileName().toString(), payload);
            result.merge(payloadResult);
            if (!payloadResult.isValid()) {
                return;
            }
            String code = payload.path("Financial Request").path("Standard Segment")
                    .path("PromptCode").path("TransactionType").asText();
            if (!discoveredCodes.add(code)) {
                result.addError("Segment100SingleStepCoverage", "Duplicate single-step AI artifacts for transaction type " + code);
            }
        } catch (IOException exception) {
            result.addError("Segment100SingleStepCoverage", path.getFileName() + " is not valid JSON: " + exception.getMessage());
        }
    }
}
