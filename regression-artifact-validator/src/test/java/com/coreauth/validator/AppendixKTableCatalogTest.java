package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixKTableCatalog;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixKTableCatalogTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AppendixKTableCatalog catalog = new AppendixKTableCatalog();

    @Test
    void catalogMatchesEveryFixedSelectorInAppendixKSource() throws Exception {
        var source = Files.readAllLines(Path.of("specifications/ATL105/docs/specs/extracted_text.txt"));
        var appendixK = String.join(" ", source.subList(32575, 34456));
        var selectorPattern = Pattern.compile(
                "(?i)(?:Table\\s+ID|Additional\\s+Information\\s+Indicator)\\s+n\\d*\\s+Fixed\\s+Value:\\s*(\\d{3})");
        var matcher = selectorPattern.matcher(appendixK);
        Set<String> sourceTableIds = new HashSet<>();
        while (matcher.find()) sourceTableIds.add(matcher.group(1));

        assertThat(sourceTableIds).hasSize(44).isEqualTo(catalog.entries().keySet());
        assertThat(catalog.entries()).hasSize(44);
        assertThat(catalog.entries().keySet()).doesNotContain("014", "015", "033");
        assertThat(catalog.entries().keySet()).contains("044", "045", "046", "047");
        assertThat(catalog.entries().get("016")).isEqualTo("BUYPASS Card Type Code");
        assertThat(catalog.entries().get("017")).isEqualTo("PIN on Receipt Information");
    }

    @Test
    void distinguishesAssignedReservedUnlistedAndMalformedSelectors() {
        assertThat(catalog.classify("001").disposition()).isEqualTo(AppendixKTableCatalog.Disposition.ASSIGNED);
        assertThat(catalog.classify("018").disposition()).isEqualTo(AppendixKTableCatalog.Disposition.ASSIGNED);
        assertThat(catalog.classify("019").disposition()).isEqualTo(AppendixKTableCatalog.Disposition.ASSIGNED);
        assertThat(catalog.classify("047").title()).isEqualTo("Visa Category Code");
        assertThat(catalog.classify("002").disposition()).isEqualTo(AppendixKTableCatalog.Disposition.RESERVED);
        assertThat(catalog.classify("014").disposition()).isEqualTo(AppendixKTableCatalog.Disposition.UNLISTED);
        assertThat(catalog.classify("999").disposition()).isEqualTo(AppendixKTableCatalog.Disposition.UNLISTED);
        assertThat(catalog.classify("18").disposition()).isEqualTo(AppendixKTableCatalog.Disposition.INVALID_FORMAT);
        assertThat(catalog.classify(null).disposition()).isEqualTo(AppendixKTableCatalog.Disposition.INVALID_FORMAT);
    }

    @Test
    void generatedPackageHasCompleteAssignedSelectorChainsAndReviewBoundaries() throws Exception {
        JsonNode pack = MAPPER.readTree(Path.of(
                "specifications/ATL105/test-output/test-json/appendices/appendix-k-segment-100-coverage.json").toFile());
        JsonNode requirements = pack.path("businessRequirements");
        JsonNode scenarios = pack.path("testScenarios");
        JsonNode cases = pack.path("testCases");
        JsonNode testData = pack.path("testData");

        assertThat(requirements).hasSize(49);
        assertThat(scenarios).hasSize(46);
        assertThat(cases).hasSize(46);
        assertThat(testData).hasSize(47);
        assertThat(pack.path("applicability").asText()).contains("Segment 112").doesNotContain("Segment 118");
        assertThat(findById(requirements, "BR-SEG100-APPK-TABLE-LAYOUT")
                .path("sourceAnchor").path("segment").asText()).isEqualTo("112");
        assertThat(findById(requirements, "BR-SEG100-APPK-FLAG")
                .path("sourceAnchor").path("element").asText()).isEqualTo("115");

        for (var entry : catalog.entries().entrySet()) {
            String tableId = entry.getKey();
            if (catalog.classify(tableId).disposition() != AppendixKTableCatalog.Disposition.ASSIGNED) continue;
            String brId = "BR-SEG100-APPK-ID-" + tableId;
            String tsId = "TS-SEG100-APPK-ID-" + tableId;
            String tcId = "TC-SEG100-APPK-ID-" + tableId;
            String tdId = "TD-SEG100-APPK-ID-" + tableId;
            JsonNode br = findById(requirements, brId);
            JsonNode ts = findById(scenarios, tsId);
            JsonNode testCase = findById(cases, tcId);
            JsonNode data = findById(testData, tdId);

            assertThat(contains(ts.path("requirementIds"), brId)).isTrue();
            assertThat(contains(testCase.path("scenarioIds"), tsId)).isTrue();
            assertThat(contains(testCase.path("testDataIds"), tdId)).isTrue();
            assertThat(contains(data.path("testCaseIds"), tcId)).isTrue();
            assertThat(data.path("coversBr").asText()).isEqualTo(brId);
            assertThat(data.path("readiness").asText()).isEqualTo("EXECUTABLE");
            assertThat(data.path("payload").path("segmentType").asText()).isEqualTo("112");
            assertThat(data.path("payload").path("element").asText()).isEqualTo("116");
            assertThat(data.path("payload").path("tableId").asText()).isEqualTo(tableId);
            assertThat(data.path("expected").path("disposition").asText()).isEqualTo("ASSIGNED");
            assertThat(data.path("expected").path("layoutSemantics").asText()).isEqualTo("NOT_EVALUATED");
            assertThat(br.path("sourceAnchors").get(0).path("segment").asText()).isEqualTo("112");
        }

        for (String tableId : new String[]{"002", "014", "015", "033"}) {
            JsonNode data = findById(testData, "TD-SEG100-APPK-ID-BOUNDARY-" + tableId);
            String expectedDisposition = tableId.equals("002") ? "RESERVED" : "UNLISTED";
            assertThat(data.path("readiness").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(data.path("expectedValidation").asText()).isEqualTo("REVIEW");
            assertThat(data.path("expected").path("disposition").asText()).isEqualTo(expectedDisposition);
        }
    }

    private static JsonNode findById(JsonNode artifacts, String id) {
        for (JsonNode artifact : artifacts) {
            if (id.equals(artifact.path("id").asText())) return artifact;
        }
        throw new AssertionError("Missing Appendix K artifact " + id);
    }

    private static boolean contains(JsonNode values, String expected) {
        for (JsonNode value : values) {
            if (expected.equals(value.asText())) return true;
        }
        return false;
    }
}