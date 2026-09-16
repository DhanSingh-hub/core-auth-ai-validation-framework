package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/** Verifies that the canonical appendix inventory points to complete evidence records. */
public final class AppendixCoverageConsistency {
    private final ObjectMapper mapper = new ObjectMapper();

    public Report validate(Path inventoryFile, Path evidenceDirectory) throws IOException {
        JsonNode inventory = mapper.readTree(inventoryFile.toFile());
        List<String> missingFiles = new ArrayList<>();
        List<String> countMismatches = new ArrayList<>();
        List<String> incompleteEvidence = new ArrayList<>();
        Map<String, Integer> statusCounts = new LinkedHashMap<>();
        for (JsonNode appendix : inventory.path("appendices")) {
            String sourceRecord = appendix.path("sourceRecord").asText(null);
            Path recordPath = sourceRecord == null ? null : evidenceDirectory.resolve(sourceRecord);
            if (recordPath != null && !Files.exists(recordPath)) {
                recordPath = evidenceDirectory.resolve(Path.of(sourceRecord).getFileName());
            }
            if (sourceRecord == null || recordPath == null || !Files.exists(recordPath)) {
                missingFiles.add(sourceRecord == null ? appendix.path("id").asText() : sourceRecord);
                continue;
            }
            JsonNode evidence = mapper.readTree(recordPath.toFile());
            int expected = appendix.path("brRecords").asInt(-1);
            int actual = evidence.path("businessRequirements").size();
            if (expected != actual) countMismatches.add(sourceRecord + " expected=" + expected + " actual=" + actual);
            if (!evidence.path("testScenarios").isArray() || !evidence.path("testCases").isArray()) {
                incompleteEvidence.add(sourceRecord + " does not embed testScenarios and testCases arrays");
            }
            for (JsonNode requirement : evidence.path("businessRequirements")) {
                String status = requirement.path("status").asText("UNSPECIFIED");
                statusCounts.merge(status, 1, Integer::sum);
            }
        }
        return new Report(inventory.path("appendices").size(), missingFiles, countMismatches, incompleteEvidence, statusCounts);
    }

    public record Report(int appendixCount, List<String> missingFiles, List<String> countMismatches,
                         List<String> incompleteEvidence, Map<String, Integer> statusCounts) {
        public boolean isConsistent() { return missingFiles.isEmpty() && countMismatches.isEmpty(); }
    }
}
