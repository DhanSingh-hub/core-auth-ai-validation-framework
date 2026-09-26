package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Prevents specialized appendix artifacts from being silently treated as generic Segment 100 output. */
public final class Segment100SpecializedDomainIntakeValidator {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Segment100SpecializedDomainRegistry registry = new Segment100SpecializedDomainRegistry();

    public ValidationResult validateDirectory(Path directory) {
        ValidationResult result = new ValidationResult("segment-100-specialized-domains");
        if (directory == null || !Files.isDirectory(directory)) {
            result.addError("SpecializedDomain", "AI specialized-domain directory is required");
            return result;
        }
        try (var files = Files.list(directory)) {
            files.filter(path -> path.toString().endsWith(".json")).sorted().forEach(path -> validateFile(path, result));
        } catch (IOException exception) {
            result.addError("SpecializedDomain", "Unable to read specialized-domain directory: " + exception.getMessage());
        }
        return result;
    }

    private void validateFile(Path file, ValidationResult result) {
        try {
            JsonNode artifact = mapper.readTree(file.toFile());
            String domain = artifact.path("metadata").path("specializedDomain").asText(null);
            if (domain == null || registry.domain(domain) == null) {
                result.addError("SpecializedDomain", file.getFileName() + " must declare a supported specializedDomain");
                return;
            }
            JsonNode request = artifact.path("Financial Request");
            if (!request.isObject()) {
                result.addError("SpecializedDomain", file.getFileName() + " requires a Financial Request object");
                return;
            }
            JsonNode segment = request.path("Standard Segment");
            if (!segment.isObject() || !"100".equals(segment.path("SegmentType").asText(null))) {
                result.addError("SpecializedDomain", file.getFileName() + " requires SegmentType 100");
            }
        } catch (IOException exception) {
            result.addError("SpecializedDomain", file.getFileName() + " is not valid JSON");
        }
    }
}
