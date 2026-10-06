package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixLCurrencyCodeCatalog;
import com.coreauth.validator.canonical.AppendixLCurrencyCodeCatalog.Classification;
import com.coreauth.validator.canonical.AppendixLCurrencyCodeCatalog.NetworkScope;
import com.coreauth.validator.canonical.AppendixLCurrencyCodeCatalog.Row;
import com.coreauth.validator.coverage.GenerateAppendixLCurrencyCodeChains;
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

class AppendixLCurrencyCodeCatalogTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Set<String> KNOWN_NETWORK_SCOPE_CONFLICT_CODES = Set.of("230", "356", "578", "710", "840", "978");

    @Test
    void catalogHasOneHundredSixtyOneRowsAndOneHundredFiftyThreeDistinctCodes() {
        var catalog = new AppendixLCurrencyCodeCatalog();
        assertThat(catalog.rows()).hasSize(161);
        assertThat(catalog.codes()).hasSize(153);
    }

    @Test
    void everyCatalogRowCurrencyNameAndCodeOccursInTheAppendixLSourceTable() throws Exception {
        String source = Files.readString(Path.of("specifications/ATL105/docs/specs/extracted_text.txt"));
        int start = source.indexOf("Appendix L.  Valid Currency Codes", source.indexOf("Appendix K-34"));
        int end = source.indexOf("Appendix M.  Element Format Examples", start);
        assertThat(start).isGreaterThanOrEqualTo(0);
        assertThat(end).isGreaterThan(start);
        String appendixL = source.substring(start, end);
        var catalog = new AppendixLCurrencyCodeCatalog();
        for (Row row : catalog.rows()) {
            assertThat(appendixL).as("currency name %s", row.currencyName()).contains(row.currencyName());
            assertThat(Pattern.compile("\\b" + Pattern.quote(row.code()) + "\\b").matcher(appendixL).find())
                    .as("code %s (%s)", row.code(), row.currencyName()).isTrue();
        }
    }

    @Test
    void classifyRecognizesEveryDistinctCodeWithItsAggregatedNetworkScope() {
        var catalog = new AppendixLCurrencyCodeCatalog();
        for (String code : catalog.codes()) {
            Classification classification = catalog.classify(code);
            assertThat(classification.recognized()).as("code %s", code).isTrue();
            assertThat(classification.rows()).as("code %s", code).isNotEmpty();
            if (KNOWN_NETWORK_SCOPE_CONFLICT_CODES.contains(code)) {
                assertThat(classification.networkScopeConflict()).as("code %s", code).isTrue();
                assertThat(classification.aggregatedScope()).as("code %s", code).isNull();
                assertThat(classification.rows().size()).as("code %s row count", code).isGreaterThanOrEqualTo(2);
            } else {
                assertThat(classification.networkScopeConflict()).as("code %s", code).isFalse();
                assertThat(classification.aggregatedScope()).as("code %s", code).isNotNull();
            }
        }
        assertThat(catalog.codes()).containsAll(KNOWN_NETWORK_SCOPE_CONFLICT_CODES);
    }

    @Test
    void wellKnownRepresentativeCodesResolveToTheirSourceCurrencyAndScope() {
        var catalog = new AppendixLCurrencyCodeCatalog();
        assertSingleScope(catalog, "840", "U.S. Dollar", null); // conflict code; verified separately below
        Classification usd = catalog.classify("840");
        assertThat(usd.networkScopeConflict()).isTrue();
        assertThat(usd.rows()).extracting(Row::networkScope).containsExactlyInAnyOrder(NetworkScope.MC, NetworkScope.BOTH);

        assertSingleScope(catalog, "124", "Canadian Dollar", NetworkScope.BOTH);
        assertSingleScope(catalog, "826", "Pound Sterling", NetworkScope.BOTH);
        assertSingleScope(catalog, "232", "Eritrean Nakfa", NetworkScope.VISA);
        assertSingleScope(catalog, "158", "Chinese People's Bank Dollar", NetworkScope.MC);

        Classification euro = catalog.classify("978");
        assertThat(euro.networkScopeConflict()).isTrue();
        assertThat(euro.rows()).hasSize(3);
        assertThat(euro.rows()).extracting(Row::networkScope).containsExactlyInAnyOrder(NetworkScope.MC, NetworkScope.VISA, NetworkScope.BOTH);
    }

    private static void assertSingleScope(AppendixLCurrencyCodeCatalog catalog, String code, String currencyName, NetworkScope expectedScope) {
        Classification classification = catalog.classify(code);
        assertThat(classification.recognized()).as("code %s", code).isTrue();
        assertThat(classification.rows().get(0).currencyName()).as("code %s", code).isEqualTo(currencyName);
        if (expectedScope != null) {
            assertThat(classification.networkScopeConflict()).as("code %s", code).isFalse();
            assertThat(classification.aggregatedScope()).as("code %s", code).isEqualTo(expectedScope);
        }
    }

    @Test
    void unknownAndMalformedValuesDoNotClassify() {
        var catalog = new AppendixLCurrencyCodeCatalog();
        for (String code : new String[]{"000", "001", "005", "097", "100", "300", "500", "700", "900", "999"}) {
            assertThat(catalog.classify(code).recognized()).as("unknown but well-formed %s", code).isFalse();
        }
        for (String value : new String[]{null, "", "84", "0840", "USD", "-840", "84O", " 840", "840 ", "\u0668\u0664\u0660"}) {
            assertThat(catalog.classify(value).recognized()).as("malformed value [%s]", value).isFalse();
        }
    }

    @Test
    void generatedPackageHasIndependentChainsForEverySourceCodeAndEveryNegative() throws Exception {
        JsonNode pack = MAPPER.readTree(GenerateAppendixLCurrencyCodeChains.OUTPUT.toFile());
        Map<String, JsonNode> requirements = index(pack.path("businessRequirements"));
        Map<String, JsonNode> scenarios = index(pack.path("testScenarios"));
        Map<String, JsonNode> cases = index(pack.path("testCases"));
        Map<String, JsonNode> data = index(pack.path("testData"));

        var catalog = new AppendixLCurrencyCodeCatalog();
        int expectedChains = catalog.codes().size() + 10 /* unknown well-formed */ + 10 /* malformed */;
        assertThat(requirements).hasSize(catalog.codes().size() + 2);
        assertThat(scenarios).hasSize(expectedChains);
        assertThat(cases).hasSize(expectedChains);
        assertThat(data).hasSize(expectedChains);
        assertThat(pack.path("status").asText()).isEqualTo("PARTIALLY_COVERED");
        assertThat(pack.path("transactionEligibilityCertified").asBoolean()).isFalse();
        assertThat(pack.path("validCodeCount").asInt()).isEqualTo(153);
        assertThat(pack.path("sourceRowCount").asInt()).isEqualTo(161);

        Set<String> conflictCodes = new HashSet<>();
        pack.path("networkScopeConflictCodes").forEach(node -> conflictCodes.add(node.asText()));
        assertThat(conflictCodes).isEqualTo(KNOWN_NETWORK_SCOPE_CONFLICT_CODES);

        Set<String> seenCodes = new HashSet<>();
        for (JsonNode td : data.values()) {
            JsonNode payload = td.path("payload");
            JsonNode expected = td.path("expected");
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

            if ("BR-APPL-UNKNOWN-CODE".equals(requirement.path("id").asText()) || "BR-APPL-FORMAT-INVALID".equals(requirement.path("id").asText())) {
                assertThat(expected.path("outcome").asText()).isEqualTo("FAIL");
                JsonNode currencyCodeNode = payload.path("currencyCode");
                String code = currencyCodeNode.isNull() ? null : currencyCodeNode.asText();
                assertThat(catalog.classify(code).recognized()).as("negative TD %s", td.path("id").asText()).isFalse();
                continue;
            }

            String code = payload.path("currencyCode").asText();
            assertThat(seenCodes.add(code)).as("duplicate positive chain for %s", code).isTrue();
            Classification actual = catalog.classify(code);
            assertThat(actual.recognized()).as("code %s", code).isTrue();
            if (conflictCodes.contains(code)) {
                assertThat(expected.path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
                assertThat(expected.path("networkScopeConflict").asBoolean()).isTrue();
                assertThat(expected.path("rows")).hasSize(actual.rows().size());
            } else {
                assertThat(expected.path("outcome").asText()).isEqualTo("PASS");
                assertThat(expected.path("networkScope").asText()).isEqualTo(actual.aggregatedScope().name());
            }
        }
        assertThat(seenCodes).containsExactlyInAnyOrderElementsOf(catalog.codes());
    }

    @Test
    void generatorOutputIsDeterministic() throws Exception {
        byte[] before = Files.readAllBytes(GenerateAppendixLCurrencyCodeChains.OUTPUT);
        GenerateAppendixLCurrencyCodeChains.main(new String[0]);
        assertThat(Files.readAllBytes(GenerateAppendixLCurrencyCodeChains.OUTPUT)).isEqualTo(before);
    }

    private static Map<String, JsonNode> index(JsonNode values) {
        Map<String, JsonNode> indexed = new HashMap<>();
        for (JsonNode value : values) assertThat(indexed.put(value.path("id").asText(), value)).isNull();
        return indexed;
    }
}
