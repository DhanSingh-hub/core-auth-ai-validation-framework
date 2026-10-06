package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixGTransactionTypeCatalog;
import com.coreauth.validator.coverage.GenerateAppendixGTransactionTypeChains;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixGTransactionTypeCatalogTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Map<String, String> SOURCE_TYPES = Map.ofEntries(
            Map.entry("0", "POS Purchase/Capture"), Map.entry("3", "POS Authorization Only"),
            Map.entry("4", "Customer-activated Purchase/Capture"), Map.entry("5", "Customer-activated Authorization Only"),
            Map.entry("6", "Mail/Phone Purchase"), Map.entry("7", "Merchandise return"),
            Map.entry("8", "Purchase reversal"), Map.entry("9", "Special Transactions"),
            Map.entry("A", "Account Verification"), Map.entry("B", "Mail/Phone Authorization Only"),
            Map.entry("C", "Mail/Phone Reversal"), Map.entry("D", "RBC Loyalty Lookup"),
            Map.entry("E", "Balance Inquiry"), Map.entry("K", "Activate"), Map.entry("L", "Deactivate"),
            Map.entry("M", "Balance Merge"), Map.entry("N", "Replace Card"), Map.entry("Q", "Recharge Card"),
            Map.entry("S", "Cancellation"), Map.entry("T", "Token Registration"),
            Map.entry("U", "Void of a Merchandise Return"), Map.entry("V", "Loyalty advice function"),
            Map.entry("Z", "Time-out Reversal"));

    @Test
    void catalogMatchesAllTwentyThreeAppendixGSourceTypeLabels() {
        var catalog = new AppendixGTransactionTypeCatalog();
        assertThat(catalog.entries()).hasSize(23);
        for (var source : SOURCE_TYPES.entrySet()) {
            var entry = catalog.classify(source.getKey()).orElseThrow();
            assertThat(entry.type()).as(source.getKey()).isEqualTo(source.getValue());
            assertThat(entry.description()).as(source.getKey()).isNotBlank();
            assertThat(entry.code()).isEqualTo(source.getKey());
        }
        assertThat(catalog.entries().values().stream().filter(entry -> entry.scope() == AppendixGTransactionTypeCatalog.Scope.STANDARD_FINANCIAL).count()).isEqualTo(13);
        assertThat(catalog.entries().values().stream().filter(entry -> entry.scope() == AppendixGTransactionTypeCatalog.Scope.SPECIALIZED).count()).isEqualTo(10);
    }

    @Test
    void everyCatalogCodeAndTypeLabelOccursInTheAppendixGSourceTable() throws Exception {
        String source = Files.readString(Path.of("specifications/ATL105/docs/specs/extracted_text.txt"));
        int start = source.lastIndexOf("Appendix G-1");
        int end = source.indexOf("Appendix H-1", start);
        assertThat(start).isGreaterThanOrEqualTo(0);
        assertThat(end).isGreaterThan(start);
        String appendixG = source.substring(start, end);
        for (var entry : new AppendixGTransactionTypeCatalog().entries().values()) {
            String row = "(?m)^\\s*" + Pattern.quote(entry.code()) + "\\s+" + Pattern.quote(entry.type()) + "\\s+";
            assertThat(Pattern.compile(row).matcher(appendixG).find())
                    .as("Appendix G source row %s %s", entry.code(), entry.type()).isTrue();
        }
    }

    @Test
    void onlyExactUppercaseOneCharacterAppendixGValuesClassify() {
        var catalog = new AppendixGTransactionTypeCatalog();
        for (String value : new String[]{null, "", "x", "X", "00", "9*", "*", "\u0661"}) {
            assertThat(catalog.classify(value)).as("value %s", value).isEmpty();
        }
    }

    @Test
    void generatedPackageHasIndependentChainsForEverySourceCodeAndNegative() throws Exception {
        JsonNode pack = MAPPER.readTree(GenerateAppendixGTransactionTypeChains.OUTPUT.toFile());
        JsonNode appendix = MAPPER.readTree(Path.of("specifications/ATL105/test-output/test-json/appendices/appendix-g-segment-100-coverage.json").toFile());
        JsonNode inventory = MAPPER.readTree(Path.of("specifications/ATL105/test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json").toFile());
        Map<String, JsonNode> requirements = index(pack.path("businessRequirements"));
        Map<String, JsonNode> scenarios = index(pack.path("testScenarios"));
        Map<String, JsonNode> cases = index(pack.path("testCases"));
        Map<String, JsonNode> data = index(pack.path("testData"));
        assertThat(requirements).hasSize(24);
        assertThat(scenarios).hasSize(30);
        assertThat(cases).hasSize(30);
        assertThat(data).hasSize(30);
        assertThat(pack.path("status").asText()).isEqualTo("PARTIALLY_COVERED");
        assertThat(pack.path("transactionEligibilityCertified").asBoolean()).isFalse();
        assertThat(appendix.path("classificationEvidence").path("chainPackage").asText()).isEqualTo("../appendix-g-transaction-type-chain-package.json");
        assertThat(appendix.path("businessRequirements").get(0).path("status").asText()).isEqualTo("PARTIALLY_COVERED");
        assertThat(appendix.path("businessRequirements").get(2).path("status").asText()).isEqualTo("COVERED");
        assertThat(findAppendix(inventory, "G").path("status").asText()).isEqualTo("PARTIALLY_COVERED");

        var catalog = new AppendixGTransactionTypeCatalog();
        Set<String> seen = new HashSet<>();
        for (JsonNode td : data.values()) {
            String code = td.path("payload").path("transactionTypeCode").asText();
            JsonNode expected = td.path("expected");
            if ("PASS".equals(expected.path("outcome").asText())) {
                assertThat(seen.add(code)).isTrue();
                var actual = catalog.classify(code).orElseThrow();
                assertThat(expected.path("type").asText()).isEqualTo(actual.type());
                assertThat(expected.path("description").asText()).isEqualTo(actual.description());
                assertThat(expected.path("scope").asText()).isEqualTo(actual.scope().name());
            } else {
                assertThat(catalog.classify(code)).isEmpty();
            }
            String tcId = td.path("testCaseIds").get(0).asText();
            JsonNode testCase = cases.get(tcId);
            assertThat(testCase).isNotNull();
            assertThat(testCase.path("testDataIds").get(0).asText()).isEqualTo(td.path("id").asText());
            JsonNode scenario = scenarios.get(testCase.path("scenarioIds").get(0).asText());
            assertThat(scenario).isNotNull();
            JsonNode requirement = requirements.get(scenario.path("requirementIds").get(0).asText());
            assertThat(requirement).isNotNull();
            assertThat(td.path("coversBr").asText()).isEqualTo(requirement.path("id").asText());
            assertThat(td.path("producer").asText()).isEqualTo("TEST_SOLUTION");
            assertThat(td.path("readiness").asText()).isEqualTo("EXECUTABLE");
            for (JsonNode artifact : new JsonNode[]{scenario, testCase, td}) {
                assertThat(artifact.path("sourceAnchors")).isEqualTo(requirement.path("sourceAnchors"));
            }
        }
        assertThat(seen).containsExactlyInAnyOrderElementsOf(SOURCE_TYPES.keySet());
        assertThat(pack.path("specialCode9CardTypeConstraint").asText()).contains("REVIEW_REQUIRED");
    }

    @Test
    void generatorOutputIsDeterministicAndReferencesExistingSource() throws Exception {
        byte[] before = Files.readAllBytes(GenerateAppendixGTransactionTypeChains.OUTPUT);
        GenerateAppendixGTransactionTypeChains.main(new String[0]);
        assertThat(Files.readAllBytes(GenerateAppendixGTransactionTypeChains.OUTPUT)).isEqualTo(before);
    }

    private static Map<String, JsonNode> index(JsonNode values) {
        Map<String, JsonNode> indexed = new HashMap<>();
        for (JsonNode value : values) assertThat(indexed.put(value.path("id").asText(), value)).isNull();
        return indexed;
    }

    private static JsonNode findAppendix(JsonNode inventory, String id) {
        for (JsonNode appendix : inventory.path("appendices")) {
            if (id.equals(appendix.path("id").asText())) return appendix;
        }
        throw new AssertionError("Missing Appendix " + id);
    }
}