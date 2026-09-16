package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** Intake gate for eWIC JSON artifacts produced by the AI Solution. */
public final class Segment100EwicArtifactIntakeValidator {
    private static final Map<String, String> REQUIRED_OPERATIONS = Map.of(
            "AUTHORIZATION", "3086",
            "CANCELLATION", "S086",
            "BALANCE_INQUIRY", "E086",
            "COMPLETION", "0086",
            "VOUCHER_CLEAR", "0086",
            "REVERSAL_VOID", "8086");

    private final ObjectMapper mapper = new ObjectMapper();
    private final Segment100EwicValidator validator = new Segment100EwicValidator();

    public ValidationResult validateDirectory(Path aiEwicDirectory) {
        ValidationResult result = new ValidationResult("segment-100-ewic-ai-intake");
        Set<String> foundOperations = new LinkedHashSet<>();
        if (aiEwicDirectory == null || !Files.isDirectory(aiEwicDirectory)) {
            result.addError("Segment100EwicIntake", "AI-generated eWIC artifact directory is required");
            return result;
        }
        try (var files = Files.list(aiEwicDirectory)) {
            files.filter(path -> path.toString().endsWith(".json")).sorted().forEach(path -> validateFile(path, foundOperations, result));
        } catch (IOException exception) {
            result.addError("Segment100EwicIntake", "Unable to read AI eWIC directory: " + exception.getMessage());
            return result;
        }
        for (String operation : REQUIRED_OPERATIONS.keySet()) {
            if (!foundOperations.contains(operation)) {
                result.addError("Segment100EwicIntake", "Missing AI-generated eWIC artifact for operation " + operation);
            }
        }
        return result;
    }

    private void validateFile(Path file, Set<String> foundOperations, ValidationResult result) {
        try {
            JsonNode payload = mapper.readTree(file.toFile());
            String operation = operation(payload);
            if (operation == null) {
                result.addError("Segment100EwicIntake", file.getFileName() + " must declare eWIC operation metadata");
                return;
            }
            String expectedPromptCode = REQUIRED_OPERATIONS.get(operation);
            if (expectedPromptCode == null) {
                result.addError("Segment100EwicIntake", file.getFileName() + " has unsupported eWIC operation " + operation);
                return;
            }
            String actualPromptCode = promptCode(payload);
            if (!expectedPromptCode.equals(actualPromptCode)) {
                result.addError("Segment100EwicIntake", file.getFileName() + " operation " + operation
                        + " requires Prompt Code " + expectedPromptCode + ", got " + actualPromptCode);
            }
            String transactionType = actualPromptCode.length() == 4 ? actualPromptCode.substring(0, 1) : "";
            ValidationResult structure = validator.validateRequest(file.getFileName().toString(),
                    normalizePromptPayload(payload, transactionType), operation);
            result.merge(structure);
            if (!foundOperations.add(operation)) {
                result.addError("Segment100EwicIntake", "Duplicate AI-generated eWIC artifact for operation " + operation);
            }
        } catch (IOException exception) {
            result.addError("Segment100EwicIntake", file.getFileName() + " is not valid JSON");
        }
    }

    private static String operation(JsonNode payload) {
        String value = payload.path("metadata").path("ewicOperation").asText(null);
        if (value != null) return value;
        return payload.path("ewicOperation").asText(null);
    }

    private static String promptCode(JsonNode payload) {
        JsonNode prompt = payload.path("Financial Request").path("Standard Segment").path("PromptCode");
        return prompt.isObject()
                ? prompt.path("TransactionType").asText("") + prompt.path("CardType").asText("")
                : payload.path("promptCode").asText("");
    }

    private static JsonNode normalizePromptPayload(JsonNode payload, String transactionType) {
        if (payload.path("Financial Request").isObject()) return payload;
        return payload;
    }
}
