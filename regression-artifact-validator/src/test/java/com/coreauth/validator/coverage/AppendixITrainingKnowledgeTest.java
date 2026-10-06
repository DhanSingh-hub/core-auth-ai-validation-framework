package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixITrainingKnowledgeTest {
    private static final Path ROOT = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void batchesRetainIndependentSourceIdentityReviewGatesAndConcreteTestDesign() throws IOException {
        var inventory = GenerateAppendixITrainingInventory.generate(ROOT);
        Set<String> sourceIds = new HashSet<>();
        inventory.path("tables").forEach(row -> sourceIds.add(row.path("tableId").asText()));
        Set<String> tableIds = new HashSet<>();
        Set<String> ruleIds = new HashSet<>();
        int sourceLineCount = Files.readAllLines(ROOT.resolve(Path.of("docs", "specs", "extracted_text.txt"))).size();
        Path knowledge = ROOT.resolve(Path.of("test-output", "test-json", "knowledge"));
        try (var files = Files.list(knowledge)) {
            for (Path path : files.filter(file -> file.getFileName().toString()
                    .matches("appendix-i-\\d{3}(?:-\\d{3})?\\.json")).toList()) {
                JsonNode batch = MAPPER.readTree(path.toFile());
                assertThat(batch.path("specification").asText()).isEqualTo("ATL105");
                assertThat(batch.path("specificationVersion").asText()).isEqualTo("2026-3");
                assertThat(batch.path("status").asText()).isEqualTo("DRAFT_REVIEW_REQUIRED");
                assertThat(batch.path("tables").isArray()).isTrue();
                assertThat(batch.path("tables")).isNotEmpty();
                for (JsonNode table : batch.path("tables")) {
                    String id = table.path("tableId").asText();
                    assertThat(sourceIds).contains(id);
                    assertThat(tableIds.add(id)).as("Unique top-level Table ID " + id).isTrue();
                    evidence(table, sourceLineCount);
                    assertThat(table.path("fields").isArray()).isTrue();
                    assertThat(table.path("fields")).isNotEmpty();
                    for (JsonNode field : table.path("fields")) {
                        evidence(field, sourceLineCount);
                    }
                    assertThat(table.path("nestedLayouts").isArray()).isTrue();
                    for (JsonNode nested : table.path("nestedLayouts")) {
                        evidence(nested, sourceLineCount);
                        assertThat(nested.path("fields").isArray()).isTrue();
                        for (JsonNode field : nested.path("fields")) {
                            evidence(field, sourceLineCount);
                        }
                    }
                    assertThat(table.path("rules").isArray()).isTrue();
                    assertThat(table.path("rules")).isNotEmpty();
                    for (JsonNode rule : table.path("rules")) {
                        assertThat(ruleIds.add(rule.path("id").asText())).as("Unique rule ID").isTrue();
                        assertThat(rule.path("id").asText()).startsWith("APPI-T" + id + "-R");
                        assertThat(rule.path("statement").asText()).isNotBlank();
                        assertThat(rule.path("status").asText()).isEqualTo("DRAFT_REVIEW_REQUIRED");
                        assertThat(rule.path("validationMode").asText()).isEqualTo("REVIEW_ONLY");
                        JsonNode anchor = rule.path("sourceAnchor");
                        assertThat(anchor.path("specification").asText()).isEqualTo("ATL105");
                        assertThat(anchor.path("version").asText()).isEqualTo("2026-3");
                        assertThat(anchor.path("segment").asText()).isEqualTo("111");
                        assertThat(anchor.path("element").asText()).isIn("111", "112", "113");
                        assertThat(anchor.path("section").asText()).isNotBlank();
                        assertThat(anchor.path("rule").asText()).isNotBlank();
                        evidence(rule, sourceLineCount);
                        assertThat(rule.path("testDesign").path("positive").asText()).isNotBlank();
                        assertThat(rule.path("testDesign").path("negative").asText()).isNotBlank();
                        assertThat(rule.path("dependencies").isArray()).isTrue();
                    }
                }
            }
        }
        assertThat(tableIds).contains("001", "002", "003", "004", "005", "006", "007", "008", "009");
        assertThat(ruleIds).isNotEmpty();
    }

    private static void evidence(JsonNode node, int sourceLineCount) {
        assertThat(node.path("sourceEvidence").isArray()).as("Source evidence array").isTrue();
        assertThat(node.path("sourceEvidence")).isNotEmpty();
        for (JsonNode item : node.path("sourceEvidence")) {
            assertThat(item.path("section").asText()).isNotBlank();
            int start = item.path("startLine").asInt();
            int end = item.path("endLine").asInt();
            assertThat(start).isPositive();
            assertThat(end).isGreaterThanOrEqualTo(start).isLessThanOrEqualTo(sourceLineCount);
        }
    }
}
