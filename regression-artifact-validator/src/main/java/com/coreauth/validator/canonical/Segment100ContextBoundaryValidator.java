package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Validates explicit declarations for Segment 100 context areas still requiring specialized evidence. */
public final class Segment100ContextBoundaryValidator {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Segment100ContextBoundaryRegistry registry = new Segment100ContextBoundaryRegistry();

    public ValidationResult validateDirectory(Path directory) {
        ValidationResult result = new ValidationResult("segment-100-context-boundaries");
        if (directory == null || !Files.isDirectory(directory)) {
            result.addError("ContextBoundary", "Context artifact directory is required");
            return result;
        }
        try (var files = Files.list(directory)) {
            files.filter(path -> path.toString().endsWith(".json")).sorted().forEach(path -> validateFile(path, result));
        } catch (IOException exception) {
            result.addError("ContextBoundary", "Unable to read context artifact directory: " + exception.getMessage());
        }
        return result;
    }

    private void validateFile(Path file, ValidationResult result) {
        try {
            JsonNode artifact = mapper.readTree(file.toFile());
            String context = artifact.path("metadata").path("segment100Context").asText(null);
            if (context == null || registry.contexts().get(context) == null) {
                result.addError("ContextBoundary", file.getFileName() + " must declare a supported segment100Context");
                return;
            }
            JsonNode request = artifact.path("Financial Request");
            if (!request.isObject() || !request.path("Standard Segment").isObject()
                    || !"100".equals(request.path("Standard Segment").path("SegmentType").asText(null))) {
                result.addError("ContextBoundary", file.getFileName() + " requires a Segment 100 Financial Request");
            }
            if ("REVIEW_REQUIRED".equals(registry.contexts().get(context).status())) {
                result.addWarning("ContextBoundary " + context + " requires specialized evidence or review");
            }
        } catch (IOException exception) {
            result.addError("ContextBoundary", file.getFileName() + " is not valid JSON");
        }
    }
}
