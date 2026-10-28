package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AppendixACCountryCodeCatalog;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class GenerateAppendixACCountryCodeChains {
    private GenerateAppendixACCountryCodeChains() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper json = new ObjectMapper();
        var path = Atl105Paths.testJson().resolve("appendices").resolve("appendix-ac-segment-100-coverage.json");
        ObjectNode pack = (ObjectNode) json.readTree(path.toFile());
        ArrayNode fixtures = json.createArrayNode();
        for (var fixture : pack.withArray("testData")) {
            if (!fixture.path("id").asText().startsWith("TD-SEG100-APPAC-SOURCE-")) fixtures.add(fixture);
        }
        ArrayNode rows = pack.putArray("countryRows");
        int index = 0;
        for (var row : AppendixACCountryCodeCatalog.rows()) {
            rows.add(json.valueToTree(row));
            ObjectNode fixture = fixtures.addObject();
            fixture.put("id", "TD-SEG100-APPAC-SOURCE-" + String.format("%03d", ++index));
            String rule = "BR-SEG100-APPAC-VALID-CODE";
            fixture.put("coversBr", rule);
            fixture.put("status", "EXECUTABLE");
            fixture.putObject("payload").put("countryCode", row.code()).put("assertedCountry", row.country());
            fixture.putObject("expected").put("businessRequirementId", rule)
                    .put("status", row.code().matches("[0-9]{3}") ? "PASS" : "FAIL");
            fixture.putObject("sourceAnchor").put("specification", "ATL105").put("version", "2026-3")
                    .put("section", "Appendix " + row.pageAnchor()).put("segment", "111")
                    .put("tableId", "059").put("rule", "country-row-" + index);
        }
        pack.set("testData", fixtures);
        long numeric = AppendixACCountryCodeCatalog.rows().stream().filter(row -> row.code().matches("[0-9]{3}")).count();
        long unique = AppendixACCountryCodeCatalog.rows().stream().map(AppendixACCountryCodeCatalog.Row::code)
                .filter(code -> code.matches("[0-9]{3}")).distinct().count();
        pack.putObject("catalogSummary").put("sourceRows", index).put("numericRows", numeric)
                .put("uniqueNumericCodes", unique).put("nonnumericRows", index - numeric);
        pack.putObject("testDataReadinessSummary").put("total", fixtures.size()).put("executable", fixtures.size())
                .put("externalFixtureRequired", 0).put("reviewRequired", 0)
                .put("note", "Synthetic source recognition vectors; executable review outcomes exercise gates only.");
        json.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), pack);
        System.out.println("Appendix AC: " + index + " source rows, " + unique
                + " unique numeric codes, " + fixtures.size() + " executable fixtures.");
    }
}
