package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

/** Verifies that the canonical appendix inventory points to complete evidence records. */
public final class AppendixCoverageConsistency {
    private final ObjectMapper mapper = new ObjectMapper();

    public Report validate(Path inventoryFile, Path evidenceDirectory) throws IOException {
        JsonNode inventory = mapper.readTree(inventoryFile.toFile());
        List<String> missingFiles = new ArrayList<>();
        List<String> countMismatches = new ArrayList<>();
        List<String> incompleteEvidence = new ArrayList<>();
        List<String> invalidTestDataReadiness = new ArrayList<>();
        List<String> danglingTestDataReferences = new ArrayList<>();
        Map<String, Integer> statusCounts = new LinkedHashMap<>();
        Map<String, Integer> readinessCounts = new LinkedHashMap<>();
        Set<String> validReadiness = Set.of("EXECUTABLE", "EXTERNAL_FIXTURE_REQUIRED", "REVIEW_REQUIRED");
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
            Set<String> brIds = new HashSet<>();
            for (JsonNode requirement : evidence.path("businessRequirements")) {
                brIds.add(requirement.path("id").asText());
            }
            for (JsonNode testData : evidence.path("testData")) {
                String readiness = testData.path("readiness").asText("UNSPECIFIED");
                readinessCounts.merge(readiness, 1, Integer::sum);
                if (!validReadiness.contains(readiness)) {
                    invalidTestDataReadiness.add(sourceRecord + ": " + testData.path("id").asText() + " has invalid readiness: " + readiness);
                }
                JsonNode coversBr = testData.path("coversBr");
                if (coversBr.isTextual() && !brIds.contains(coversBr.asText())) {
                    danglingTestDataReferences.add(sourceRecord + ": " + testData.path("id").asText() + " references missing BR: " + coversBr.asText());
                }
            }
        }
        return new Report(inventory.path("appendices").size(), missingFiles, countMismatches, incompleteEvidence,
                statusCounts, invalidTestDataReadiness, danglingTestDataReferences, readinessCounts);
    }

    public record Report(int appendixCount, List<String> missingFiles, List<String> countMismatches,
                         List<String> incompleteEvidence, Map<String, Integer> statusCounts,
                         List<String> invalidTestDataReadiness, List<String> danglingTestDataReferences,
                         Map<String, Integer> readinessCounts) {
        public boolean isConsistent() {
            return missingFiles.isEmpty() && countMismatches.isEmpty()
                    && invalidTestDataReadiness.isEmpty() && danglingTestDataReferences.isEmpty();
        }
    }
}
