package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixACCountryCodeCatalog;
import com.coreauth.validator.canonical.AppendixACCountryCodeOracle;
import com.coreauth.validator.canonical.AppendixACCountryCodeOracle.Scenario;
import com.coreauth.validator.canonical.AppendixACCountryCodeOracle.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixACCountryCodeOracleTest {
    private final ObjectMapper json = new ObjectMapper();
    private final AppendixACCountryCodeOracle oracle = new AppendixACCountryCodeOracle();

    private JsonNode coverage() throws Exception {
        return json.readTree(Atl105Paths.testJson().resolve("appendices")
                .resolve("appendix-ac-segment-100-coverage.json").toFile());
    }
    private Status status(Scenario s, String rule) {
        return oracle.assess(s).stream().filter(a -> a.businessRequirementId().equals("BR-SEG100-APPAC-" + rule))
                .findFirst().orElseThrow().status();
    }

    @Test
    void verifiesEveryTranscribedRowAndNumericSequenceAgainstAllFourSourcePages() throws Exception {
        String source = Files.readString(Atl105Paths.docs().resolve("specs").resolve("extracted_text.txt"));
        assertThat(AppendixACCountryCodeCatalog.rows()).hasSize(254);
        for (int page = 1; page <= 4; page++) {
            String anchor = "AC-" + page;
            int start = source.indexOf("Appendix " + anchor);
            int end = source.indexOf("--- PAGE " + (717 + page) + " ---", start);
            assertThat(start).isNotNegative();
            assertThat(end).isGreaterThan(start);
            String text = source.substring(start, end);
            var rows = AppendixACCountryCodeCatalog.rows().stream().filter(row -> row.pageAnchor().equals(anchor)).toList();
            List<String> sourceCodes = Pattern.compile("\\b[0-9]{3}\\b").matcher(text).results()
                    .map(match -> match.group()).toList();
            assertThat(rows.stream().map(AppendixACCountryCodeCatalog.Row::code)
                    .filter(code -> code.matches("[0-9]{3}")).toList()).as(anchor).containsExactlyElementsOf(sourceCodes);
            String compact = text.replaceAll("\\s+", "");
            for (var row : rows) {
                assertThat(compact).as(row.country()).contains((row.country() + row.code()).replaceAll("\\s+", ""));
            }
        }
        assertThat(AppendixACCountryCodeCatalog.rows().stream().filter(row -> row.code().matches("[0-9]{3}")).count())
                .isEqualTo(250);
        assertThat(AppendixACCountryCodeCatalog.rows().stream().map(AppendixACCountryCodeCatalog.Row::code)
                .filter(code -> code.matches("[0-9]{3}")).distinct().count()).isEqualTo(248);
    }

    @Test
    void executesAllPersistedFixturesWithRequirementAndSourceTraceability() throws Exception {
        JsonNode pack = coverage();
        var ids = pack.path("businessRequirements").findValuesAsText("id");
        assertThat(ids).hasSize(AppendixACCountryCodeOracle.RULES.size()).doesNotHaveDuplicates();
        for (String rule : AppendixACCountryCodeOracle.RULES) assertThat(ids).contains("BR-SEG100-APPAC-" + rule);
        for (JsonNode br : pack.path("businessRequirements")) {
            assertThat(br.path("sourceAnchor").path("section").asText()).isNotBlank();
            assertThat(br.path("sourceAnchor").path("version").asText()).isEqualTo("2026-3");
        }
        for (JsonNode scenario : pack.path("testScenarios")) {
            for (JsonNode id : scenario.path("covers")) assertThat(ids).contains(id.asText());
        }
        var scenarioIds = pack.path("testScenarios").findValuesAsText("id");
        for (JsonNode test : pack.path("testCases")) assertThat(scenarioIds).contains(test.path("scenarioId").asText());
        assertThat(pack.path("testData").size()).isEqualTo(266)
                .isEqualTo(pack.path("testDataReadinessSummary").path("executable").asInt());
        assertThat(pack.path("testData").findValuesAsText("id")).doesNotHaveDuplicates();
        for (JsonNode fixture : pack.path("testData")) {
            ObjectNode payload = pack.path("fixtureDefaults").deepCopy();
            payload.setAll((ObjectNode) fixture.path("payload"));
            String id = fixture.path("coversBr").asText();
            assertThat(ids).contains(id);
            assertThat(fixture.path("expected").path("businessRequirementId").asText()).isEqualTo(id);
            assertThat(status(json.treeToValue(payload, Scenario.class), id.substring("BR-SEG100-APPAC-".length())).name())
                    .as(fixture.path("id").asText()).isEqualTo(fixture.path("expected").path("status").asText());
        }
        assertThat(pack.path("countryRows")).isEqualTo(json.valueToTree(AppendixACCountryCodeCatalog.rows()));
    }

    @Test
    void enumeratesAllThousandNumericCandidatesAgainstSourceCatalog() {
        for (int value = 0; value < 1000; value++) {
            String code = String.format("%03d", value);
            Status expected = AppendixACCountryCodeCatalog.forCode(code).isEmpty() ? Status.FAIL : Status.PASS;
            assertThat(status(new Scenario(code, null, false, null), "VALID-CODE")).as(code).isEqualTo(expected);
        }
    }

    @Test
    void rejectsMalformedCodesWithoutTrimmingPaddingOrUnicodeNormalization() {
        for (String code : new String[]{"", " 124", "124 ", "12", "0124", "ABC", "N/A", "1.4", "\u0661\u0662\u0664"}) {
            assertThat(status(new Scenario(code, null, false, null), "VALID-CODE")).isEqualTo(Status.FAIL);
        }
        assertThat(status(new Scenario(null, null, false, null), "VALID-CODE")).isEqualTo(Status.FAIL);
        assertThat(status(new Scenario("004", "Afghanistan", false, null), "VALID-CODE")).isEqualTo(Status.PASS);
    }

    @Test
    void preservesAmbiguousRepeatedHistoricalAndNonnumericRows() {
        assertThat(AppendixACCountryCodeCatalog.forCode("840")).extracting(AppendixACCountryCodeCatalog.Row::country)
                .containsExactly("American Samoa", "United States");
        assertThat(AppendixACCountryCodeCatalog.ambiguous("840")).isTrue();
        assertThat(AppendixACCountryCodeCatalog.forCode("580")).hasSize(2);
        assertThat(AppendixACCountryCodeCatalog.ambiguous("580")).isFalse();
        for (String code : new String[]{"249", "929", "930", "900"}) {
            assertThat(status(new Scenario(code, null, false, null), "VALID-CODE")).isEqualTo(Status.PASS);
        }
        assertThat(status(new Scenario("840", "American Samoa", false, null), "SOURCE-MAPPING"))
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(new Scenario("124", "India", false, null), "SOURCE-MAPPING")).isEqualTo(Status.FAIL);
        assertThat(status(new Scenario("356", "Republic of India", false, null), "SOURCE-MAPPING"))
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(new Scenario("336", "Holy See (See Vatican City State.)", false, null), "SOURCE-MAPPING"))
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(new Scenario("336", "Vatican City State (Holy See)", false, null), "SOURCE-MAPPING"))
                .isEqualTo(Status.PASS);
    }

    @Test
    void checksEveryNumericSourceCountryMappingWithoutTreatingDuplicate580AsConflict() {
        for (var row : AppendixACCountryCodeCatalog.rows()) {
            if (!row.code().matches("[0-9]{3}")) continue;
            Status expected = row.code().equals("840") ? Status.REVIEW_REQUIRED : Status.PASS;
            assertThat(status(new Scenario(row.code(), row.country(), false, null), "SOURCE-MAPPING"))
                    .as(row.country()).isEqualTo(expected);
        }
    }

    @Test
    void enforcesDiscoverRetailerEvidenceButNeverAutoResolvesGeneralRoleConflict() {
        assertThat(status(new Scenario("124", "Canada", true, "124"), "DISCOVER-RETAILER")).isEqualTo(Status.PASS);
        assertThat(status(new Scenario("124", "Canada", true, "356"), "DISCOVER-RETAILER")).isEqualTo(Status.FAIL);
        assertThat(status(new Scenario("124", "Canada", true, null), "DISCOVER-RETAILER")).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(new Scenario("124", "Canada", null, "124"), "DISCOVER-RETAILER")).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(new Scenario("124", "Canada", true, "USA"), "DISCOVER-RETAILER")).isEqualTo(Status.FAIL);
        assertThat(status(new Scenario("999", null, true, null), "DISCOVER-RETAILER")).isEqualTo(Status.FAIL);
        assertThat(status(new Scenario("840", null, true, "840"), "DISCOVER-RETAILER")).isEqualTo(Status.REVIEW_REQUIRED);
        for (String rule : new String[]{"ACQUIRER-CONTEXT", "EXTERNAL-EVIDENCE"}) {
            assertThat(status(new Scenario("124", "Canada", true, "124"), rule)).isEqualTo(Status.REVIEW_REQUIRED);
        }
    }
}
