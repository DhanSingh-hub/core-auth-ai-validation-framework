package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/** Checks Segment 100 knowledge artifacts for duplicate, missing, and conflicting definitions. */
public final class Segment100KnowledgeConsistencyChecker {
    private final ObjectMapper mapper = new ObjectMapper();

    public Report check(Path inventoryFile, Path contextModelFile, Path evidenceDirectory,
                        Path ruleCatalogFile) throws IOException {
        JsonNode inventory = mapper.readTree(inventoryFile.toFile());
        JsonNode context = mapper.readTree(contextModelFile.toFile());
        Set<String> ids = new HashSet<>();
        Set<String> sourceRecords = new HashSet<>();
        Set<String> duplicateIds = new HashSet<>();
        Set<String> duplicateRecords = new HashSet<>();
        Set<String> invalidCategories = new HashSet<>();
        Set<String> missingRecords = new HashSet<>();
        Set<String> conflictingStatuses = new HashSet<>();

        for (JsonNode category : inventory.path("categories")) {
            String categoryId = category.path("id").asText();
            if (categoryId.isBlank()) invalidCategories.add("blank category id");
            for (JsonNode packageName : category.path("sourcePackages")) {
                String value = packageName.asText();
                if (!sourceRecords.add(value)) duplicateRecords.add(value);
                if (value.endsWith(".json") && resolve(evidenceDirectory, value) == null) missingRecords.add(value);
            }
        }
        for (JsonNode appendix : inventory.path("appendices")) {
            String id = appendix.path("id").asText();
            if (!id.matches("[A-V]")) invalidCategories.add("appendix=" + id);
            String record = appendix.path("sourceRecord").asText();
            if (!sourceRecords.add(record)) duplicateRecords.add(record);
            Path recordPath = resolve(evidenceDirectory, record);
            if (recordPath == null) { missingRecords.add(record); continue; }
            for (JsonNode status : mapper.readTree(recordPath.toFile()).path("businessRequirements")) {
                String statusValue = status.path("status").asText("UNSPECIFIED");
                if ("COVERED".equals(statusValue) && "REVIEW_REQUIRED".equals(appendix.path("status").asText())) {
                    conflictingStatuses.add(record + " claims COVERED evidence under REVIEW_REQUIRED appendix");
                }
            }
        }
        for (JsonNode rule : mapper.readTree(ruleCatalogFile.toFile()).path("rules")) {
            String ruleId = rule.path("ruleId").asText();
            if (!ids.add(ruleId)) duplicateIds.add(ruleId);
        }
        boolean contextPresent = context.path("requiredContext").has("transactionType")
                && context.path("requiredContext").has("cardType")
                && context.path("requiredContext").has("messageFamily");
        if (!contextPresent) invalidCategories.add("context model missing required dimensions");
        return new Report(duplicateIds, duplicateRecords, invalidCategories, missingRecords, conflictingStatuses);
    }

    private static Path resolve(Path root, String name) {
        Path direct = root.resolve(name);
        if (Files.exists(direct)) return direct;
        Path appendix = root.resolve("appendices").resolve(Path.of(name).getFileName());
        if (Files.exists(appendix)) return appendix;
        Path knowledge = root.resolve("knowledge").resolve(Path.of(name).getFileName());
        return Files.exists(knowledge) ? knowledge : null;
    }

    public record Report(Set<String> duplicateIds, Set<String> duplicateRecords,
                         Set<String> invalidCategories, Set<String> missingRecords,
                         Set<String> conflictingStatuses) {
        public boolean isConsistent() {
            return duplicateIds.isEmpty() && duplicateRecords.isEmpty() && invalidCategories.isEmpty()
                    && missingRecords.isEmpty() && conflictingStatuses.isEmpty();
        }
    }
}
