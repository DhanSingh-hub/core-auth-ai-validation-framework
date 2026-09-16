package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/** Reports card-type presence, duplicates, and invalid codes across AI JSON artifacts. */
public final class Segment100CardTypeCoverageValidator {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Segment100CardTypeOracle oracle = new Segment100CardTypeOracle();

    public ValidationResult validateDirectory(Path directory) {
        ValidationResult result = new ValidationResult("segment-100-card-type-coverage");
        Set<String> found = new LinkedHashSet<>();
        if (directory == null || !Files.isDirectory(directory)) {
            result.addError("Segment100CardTypeCoverage", "AI artifact directory is required");
            return result;
        }
        try (var files = Files.list(directory)) {
            files.filter(path -> path.toString().endsWith(".json")).sorted().forEach(path -> {
                try {
                    JsonNode payload = mapper.readTree(path.toFile());
                    ValidationResult artifact = oracle.validateAiPayload(path.getFileName().toString(), payload);
                    result.merge(artifact);
                    if (artifact.isValid()) {
                        String cardType = payload.path("Financial Request").path("Standard Segment")
                                .path("PromptCode").path("CardType").asText();
                        if (!found.add(cardType)) {
                            result.addError("Segment100CardTypeCoverage", "Duplicate AI artifact for card type " + cardType);
                        }
                    }
                } catch (IOException exception) {
                    result.addError("Segment100CardTypeCoverage", path.getFileName() + " is not valid JSON");
                }
            });
        } catch (IOException exception) {
            result.addError("Segment100CardTypeCoverage", "Unable to read AI artifact directory");
            return result;
        }
        for (String expected : oracle.expectedCardTypes().keySet()) {
            if (!found.contains(expected)) {
                result.addError("Segment100CardTypeCoverage", "Missing AI artifact for card type " + expected);
            }
        }
        return result;
    }
}
