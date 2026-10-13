package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixAFIncrementalAuthorizationValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixAFIncrementalAuthorizationValidatorTest {
    private static final String ROOT = "specifications/ATL105/";
    private final ObjectMapper mapper = new ObjectMapper();
    private final AppendixAFIncrementalAuthorizationValidator validator = new AppendixAFIncrementalAuthorizationValidator();

    private JsonNode read(String path) throws Exception {
        return mapper.readTree(Path.of(ROOT + path).toFile());
    }

    @Test
    void sourceCatalogAndCoverageModelArePinnedToThe2026_4AfPage() throws Exception {
        JsonNode catalog = read("docs/specs/kb/appendix-af-rule-catalog.json");
        JsonNode model = read("test-output/test-json/appendices/appendix-af-segment-100-coverage.json");
        String sourceQuotes = Files.readString(Path.of(ROOT + "docs/specs/kb/appendix-af-source-quotes.txt"));
        assertThat(catalog.path("specificationVersion").asText()).isEqualTo("2026-4");
        assertThat(catalog.path("sourcePdfPage").asInt()).isEqualTo(730);
        assertThat(catalog.path("sourcePrintedPage").asText()).isEqualTo("AF-1");
        assertThat(catalog.path("sourceDocumentSha256").asText())
                .isEqualTo("568c04d3081cac6c5cc545adee9c6f8f3a34334e788fdf5ee0260ffef57c77af");
        assertThat(catalog.path("sourceQuotesFile").asText()).isEqualTo("docs/specs/kb/appendix-af-source-quotes.txt");
        assertThat(catalog.path("rules")).hasSize(7);
        for (JsonNode rule : catalog.path("rules")) {
            assertThat(rule.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
            for (JsonNode quote : rule.path("sourceQuotes")) assertThat(sourceQuotes).contains(quote.asText());
        }
        assertThat(model.path("sourceDocumentSha256").asText()).isEqualTo(catalog.path("sourceDocumentSha256").asText());
        assertThat(model.path("executionCertified").asBoolean()).isFalse();
    }

    @Test
    void coverageModelHasCompleteReviewOnlyTraceability() throws Exception {
        JsonNode model = read("test-output/test-json/appendices/appendix-af-segment-100-coverage.json");
        Set<String> brIds = new HashSet<>(model.path("businessRequirements").findValuesAsText("id"));
        Set<String> scenarioIds = new HashSet<>(model.path("testScenarios").findValuesAsText("id"));
        Set<String> testCaseIds = new HashSet<>(model.path("testCases").findValuesAsText("id"));
        assertThat(brIds).hasSize(7);
        for (JsonNode br : model.path("businessRequirements")) {
            assertThat(br.path("sourceAnchor").path("version").asText()).isEqualTo("2026-4");
            assertThat(br.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        }
        for (JsonNode scenario : model.path("testScenarios")) {
            for (JsonNode br : scenario.path("covers")) assertThat(brIds).contains(br.asText());
        }
        for (JsonNode testCase : model.path("testCases")) {
            assertThat(scenarioIds).contains(testCase.path("scenarioId").asText());
            assertThat(testCase.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        }
        for (JsonNode testData : model.path("testData")) {
            assertThat(brIds).contains(testData.path("coversBr").asText());
            assertThat(testData.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(testData.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
            for (JsonNode tc : testData.path("testCaseIds")) assertThat(testCaseIds).contains(tc.asText());
        }
        assertThat(model.path("testDataReadinessSummary").path("executable").asInt()).isZero();
        JsonNode afTraining = read("training-status.json").path("appendixTraining").path("AF");
        assertThat(afTraining.path("specificationVersion").asText()).isEqualTo("2026-4");
        assertThat(afTraining.path("draftRuleCount").asInt()).isEqualTo(7);
        assertThat(afTraining.path("logicalFixtureCount").asInt()).isEqualTo(12);
        assertThat(afTraining.path("countsExcludedFromSegmentCatalogSummary").asBoolean()).isTrue();
    }

    @Test
    void replaysAllPersistedCasesAgainstTheLogicalValidator() throws Exception {
        JsonNode fixture = mapper.readTree(Path.of("src/test/resources/atl105/appendix-af-logical-flows.json").toFile());
        JsonNode model = read("test-output/test-json/appendices/appendix-af-segment-100-coverage.json");
        assertThat(fixture.path("specificationVersion").asText()).isEqualTo("2026-4");
        assertThat(fixture.path("executionCertified").asBoolean()).isFalse();
        assertThat(model.path("testData")).hasSize(fixture.path("cases").size());
        for (JsonNode testCase : fixture.path("cases")) {
            JsonNode testData = null;
            for (JsonNode candidate : model.path("testData")) {
                if (testCase.path("id").asText().equals(candidate.path("fixtureCaseId").asText())) testData = candidate;
            }
            assertThat(testData).as(testCase.path("id").asText()).isNotNull();
            assertThat(testData.path("expectedRule").asText()).isEqualTo(testCase.path("expectedRule").asText());
            ObjectNode flow = fixture.path("baseFlow").deepCopy();
            for (JsonNode change : testCase.path("changes")) apply(flow, change);
            ValidationResult result = validator.validate(flow);
            String expectedRule = testCase.path("expectedRule").asText();
            if ("NONE".equals(expectedRule)) assertThat(result.errors()).as(testCase.path("id").asText()).isEmpty();
            else assertThat(result.errors()).as(testCase.path("id").asText())
                    .anyMatch(error -> error.reason().contains(expectedRule));
            assertThat(result.warnings()).as(testCase.path("id").asText()).isNotEmpty();
            if (testCase.has("expectedWarning")) {
                assertThat(result.warnings()).anyMatch(warning -> warning.contains(testCase.path("expectedWarning").asText()));
            }
        }
    }

    @Test
    void missingOpenStateAndWrongReleaseRemainUnassessedRatherThanPassingSilently() throws Exception {
        JsonNode fixture = mapper.readTree(Path.of("src/test/resources/atl105/appendix-af-logical-flows.json").toFile());
        ObjectNode flow = fixture.path("baseFlow").deepCopy();
        flow.remove("openIncrementalAuthTransactions");
        flow.remove("openIncrementalAuthorizationHistoryComplete");
        flow.remove("terminalId");
        ValidationResult missingState = validator.validate(flow);
        assertThat(missingState.errors()).isEmpty();
        assertThat(missingState.warnings()).anyMatch(warning -> warning.contains("AF-TRANSACTION-ID-UNIQUENESS"));
        flow.put("specificationVersion", "2026-3");
        assertThat(validator.validate(flow).warnings()).anyMatch(warning -> warning.contains("AF-CONTEXT"));
    }

    private void apply(ObjectNode flow, JsonNode change) {
        String pointer = change.path("pointer").asText();
        if ("append".equals(change.path("op").asText())) {
            ((ArrayNode) flow.at(pointer)).add(change.path("value").deepCopy());
            return;
        }
        JsonNode parent = flow.at(pointer.substring(0, pointer.lastIndexOf('/')));
        String key = pointer.substring(pointer.lastIndexOf('/') + 1);
        if (parent.isArray()) {
            ((ArrayNode) parent).set(Integer.parseInt(key), change.path("value").deepCopy());
        } else {
            ((ObjectNode) parent).set(key, change.path("value").deepCopy());
        }
    }
}