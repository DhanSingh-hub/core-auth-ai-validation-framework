package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Machine-readable AI artifact coverage result for all 36 Segment 100 card types. */
public final class Segment100CardTypeCoverageReport {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Segment100CardTypeOracle oracle = new Segment100CardTypeOracle();

    public Report generate(Path aiArtifactDirectory, String provenance) throws IOException {
        Set<String> present = new LinkedHashSet<>();
        List<String> invalid = new ArrayList<>();
        List<String> duplicates = new ArrayList<>();
        if (aiArtifactDirectory == null || !Files.isDirectory(aiArtifactDirectory)) {
            throw new IOException("AI artifact directory is required: " + aiArtifactDirectory);
        }
        try (var files = Files.list(aiArtifactDirectory)) {
            for (Path file : files.filter(path -> path.toString().endsWith(".json")).sorted().toList()) {
                JsonNode payload;
                try {
                    payload = mapper.readTree(file.toFile());
                } catch (IOException exception) {
                    invalid.add(file.getFileName().toString() + ": invalid JSON");
                    continue;
                }
                if (!oracle.validateAiPayload(file.getFileName().toString(), payload).isValid()) {
                    invalid.add(file.getFileName().toString());
                    continue;
                }
                String cardType = payload.path("Financial Request").path("Standard Segment")
                        .path("PromptCode").path("CardType").asText();
                if (!present.add(cardType)) duplicates.add(cardType);
            }
        }
        List<String> missing = oracle.expectedCardTypes().keySet().stream()
                .filter(code -> !present.contains(code)).toList();
        return new Report(
                provenance,
                oracle.expectedCardTypes().size(),
                List.copyOf(present),
                missing,
                List.copyOf(duplicates),
                List.copyOf(invalid));
    }

    public record Report(String provenance, int expectedCount, List<String> present,
                         List<String> missing, List<String> duplicates, List<String> invalid) {
        public boolean complete() {
            return missing.isEmpty() && duplicates.isEmpty() && invalid.isEmpty()
                    && present.size() == expectedCount;
        }

        public String status() {
            return complete() ? "PASS" : "INCOMPLETE";
        }
    }
}
