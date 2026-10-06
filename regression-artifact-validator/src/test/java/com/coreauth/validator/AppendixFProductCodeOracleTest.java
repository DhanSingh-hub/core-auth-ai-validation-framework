package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixFProductCodeOracle;
import com.coreauth.validator.coverage.GenerateAppendixFProductCodeCoverage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixFProductCodeOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void committedOracleMatchesEverySourceRowAndCoversAllCodesOnce() throws Exception {
        JsonNode resource = MAPPER.readTree(GenerateAppendixFProductCodeCoverage.ORACLE.toFile());
        assertThat(resource).isEqualTo(GenerateAppendixFProductCodeCoverage.sourceOracle(GenerateAppendixFProductCodeCoverage.SOURCE));
        assertThat(resource.path("rows")).hasSize(380);
        var source = Files.readAllLines(GenerateAppendixFProductCodeCoverage.SOURCE);
        Set<String> codes = new HashSet<>();
        AppendixFProductCodeOracle oracle = new AppendixFProductCodeOracle();
        for (JsonNode row : resource.path("rows")) {
            int first = row.path("sourceAnchor").path("startLine").asInt();
            int last = row.path("sourceAnchor").path("endLine").asInt();
            String literal = String.join(" ", source.subList(first - 1, last).stream().map(String::strip).toList());
            assertThat(literal).contains(row.path("sourceRange").asText(), row.path("description").asText());
            for (int value = Integer.parseInt(row.path("startCode").asText()); value <= Integer.parseInt(row.path("endCode").asText()); value++) {
                String code = String.format(Locale.ROOT, "%03d", value);
                assertThat(codes.add(code)).as("No duplicate %s", code).isTrue();
                JsonNode result = oracle.evaluate(payload("CLASSIFICATION", code));
                for (String field : new String[]{"table", "assignment", "role", "description", "sourceAnchor", "sourceRange"}) {
                    assertThat(result.path(field)).as("%s %s", code, field).isEqualTo(row.path(field));
                }
                assertThat(result.path("transactionEligibility").asText()).isEqualTo("NOT_EVALUATED");
            }
        }
        assertThat(codes).hasSize(1000).contains("000", "999");
    }

    @Test
    void preservesAssignedUndefinedReservedAndMixedTableExceptions() throws Exception {
        AppendixFProductCodeOracle oracle = new AppendixFProductCodeOracle();
        String[][] cases = {
                {"000", "UNUSED", "UNUSED", "UNUSED", "Not used."},
                {"001", "MOTOR_FUEL", "ASSIGNED", "MOTOR_FUEL", "Unleaded (88 octane or less)"},
                {"004", "MOTOR_FUEL", "UNDEFINED", "MOTOR_FUEL", "Undefined Unleaded"},
                {"071", "MOTOR_FUEL", "UNDEFINED", "MOTOR_FUEL", "Undefined fuel"},
                {"098", "MOTOR_FUEL", "UNDEFINED", "MOTOR_FUEL", "Undefined fuel"},
                {"099", "MOTOR_FUEL", "ASSIGNED", "MOTOR_FUEL", "Miscellaneous fuel"},
                {"100", "AUTOMOTIVE_PRODUCT_SERVICE", "ASSIGNED", "AUTOMOTIVE_PRODUCT_SERVICE", "General automotive merchandise"},
                {"156", "AVIATION_FUEL", "UNDEFINED", "AVIATION_FUEL", "Undefined aviation fuel"},
                {"214", "AVIATION_PRODUCT_SERVICE", "UNDEFINED", "AVIATION_PRODUCT_SERVICE", "Undefined aviation"},
                {"231", "MARINE_FUEL", "UNDEFINED", "MARINE_FUEL", "Undefined marine fuel"},
                {"255", "MARINE_PRODUCT_SERVICE", "UNDEFINED", "MARINE_PRODUCT_SERVICE", "Undefined marine service"},
                {"308", "OTHER_FUEL", "ASSIGNED", "OTHER_FUEL", "EVC-1 \u2013 Level 1 charge = 110v 15 amp"},
                {"325", "OTHER_FUEL", "UNDEFINED", "OTHER_FUEL", "Undefined other fuel"},
                {"402", "MERCHANDISE", "UNDEFINED", "MERCHANDISE", "General undefined"},
                {"600", "RESERVED_FUTURE_USE_TABLE", "ASSIGNED", "RESERVED_FUTURE_USE_TABLE", "DEF (Diesel Exhaust Fluid)"},
                {"606", "RESERVED_FUTURE_USE_TABLE", "RESERVED", "RESERVED_FUTURE_USE_TABLE", "Undefined Packaged Fuels \u2013 Reserved for PCATS Future Use"},
                {"623", "RESERVED_FUTURE_USE_TABLE", "RESERVED", "RESERVED_FUTURE_USE_TABLE", "Undefined Packaged Fuels \u2013 Reserved for PCATS Future Use"},
                {"624", "RESERVED_FUTURE_USE_TABLE", "ASSIGNED", "RESERVED_FUTURE_USE_TABLE", "Miscellaneous Packaged Fuels"},
                {"625", "RESERVED_FUTURE_USE_TABLE", "RESERVED", "RESERVED_FUTURE_USE_TABLE", "Reserved for PCATS Future Use"},
                {"657", "RESERVED_FUTURE_USE_TABLE", "ASSIGNED", "RESERVED_FUTURE_USE_TABLE", "EV Charging Fee"},
                {"658", "RESERVED_FUTURE_USE_TABLE", "RESERVED", "RESERVED_FUTURE_USE_TABLE", "Undefined Vehicle Product/Services \u2013 Reserved for PCATS Use"},
                {"799", "RESERVED_FUTURE_USE_TABLE", "RESERVED", "RESERVED_FUTURE_USE_TABLE", "Reserved for PCATS Future Use"},
                {"800", "DEBIT_SURCHARGE_TABLE", "ASSIGNED", "ADMINISTRATIVE", "Administrative\u2014Debit surcharge"},
                {"803", "DEBIT_SURCHARGE_TABLE", "ASSIGNED", "MERCHANDISE", "Merchandise\u2014BUYPASS default nonfuel/noncash/nontax"},
                {"807", "DEBIT_SURCHARGE_TABLE", "ASSIGNED", "MOTOR_FUEL", "Motor Fuels\u2014Special"},
                {"809", "DEBIT_SURCHARGE_TABLE", "ASSIGNED", "NEGATIVE", "Negative Transaction\u2014Tax exemption credit"},
                {"810", "PROPRIETARY_RESERVED", "RESERVED", "PROPRIETARY_RESERVED", "reserved for proprietary use."},
                {"894", "FSA_HRA", "ASSIGNED", "FSA_HRA", "Total qualified healthcare products (QHP)"},
                {"897", "PROPRIETARY_RESERVED", "RESERVED", "PROPRIETARY_RESERVED", "reserved for proprietary use."},
                {"900", "NEGATIVE", "ASSIGNED", "NEGATIVE", "Discount 1"},
                {"915", "NEGATIVE", "UNDEFINED", "NEGATIVE", "Undefined negative"},
                {"950", "ADMINISTRATIVE", "ASSIGNED", "ADMINISTRATIVE", "Tax 1"},
                {"971", "ADMINISTRATIVE", "UNDEFINED", "ADMINISTRATIVE", "Undefined administrative"},
                {"999", "ADMINISTRATIVE", "ASSIGNED", "ADMINISTRATIVE", "Miscellaneous administrative"}
        };
        for (String[] example : cases) {
            JsonNode result = oracle.evaluate(payload("CLASSIFICATION", example[0]));
            assertThat(result.path("outcome").asText()).isEqualTo("PASS");
            assertThat(result.path("table").asText()).as(example[0]).isEqualTo(example[1]);
            assertThat(result.path("assignment").asText()).isEqualTo(example[2]);
            assertThat(result.path("role").asText()).isEqualTo(example[3]);
            assertThat(result.path("description").asText()).isEqualTo(example[4]);
        }
    }

    @Test
    void disputedCodesRetainBothClaimsButFormatStillPasses() throws Exception {
        AppendixFProductCodeOracle oracle = new AppendixFProductCodeOracle();
        for (String code : new String[]{"895", "896"}) {
            JsonNode result = oracle.evaluate(payload("CLASSIFICATION", code));
            assertThat(result.path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(result.path("sourceConflict").asBoolean()).isTrue();
            assertThat(result.path("conflictingClaims").path("overviewClaim").asText()).contains("895-899");
            assertThat(result.path("conflictingClaims").path("detailedClaim").asText()).contains("895 Co-Payment", "896 Transit");
            assertThat(oracle.evaluate(payload("FORMAT", code)).path("outcome").asText()).isEqualTo("PASS");
        }
    }

    @Test
    void independentChainsHaveUniqueResolvedIdsSourceAnchorsAndReplayablePayloads() throws Exception {
        JsonNode pack = MAPPER.readTree(GenerateAppendixFProductCodeCoverage.CHAINS.toFile());
        Map<String, JsonNode> brs = index(pack.path("businessRequirements"));
        Map<String, JsonNode> scenarios = index(pack.path("testScenarios"));
        Map<String, JsonNode> cases = index(pack.path("testCases"));
        Map<String, JsonNode> data = index(pack.path("testData"));
        assertThat(brs).hasSize(381);
        assertThat(scenarios).hasSize(1013);
        assertThat(cases).hasSize(1013);
        assertThat(data).hasSize(1013);
        AppendixFProductCodeOracle oracle = new AppendixFProductCodeOracle();
        int reviewCount = 0;
        for (JsonNode td : data.values()) {
            JsonNode tc = cases.get(td.path("testCaseIds").get(0).asText());
            assertThat(tc).isNotNull();
            assertThat(tc.path("testDataIds").get(0).asText()).isEqualTo(td.path("id").asText());
            JsonNode ts = scenarios.get(tc.path("scenarioIds").get(0).asText());
            assertThat(ts).isNotNull();
            JsonNode br = brs.get(ts.path("requirementIds").get(0).asText());
            assertThat(br).isNotNull();
            assertThat(td.path("coversBr").asText()).isEqualTo(br.path("id").asText());
            assertThat(td.path("producer").asText()).isEqualTo("TEST_SOLUTION");
            assertThat(td.path("readiness").asText()).isEqualTo("EXECUTABLE");
            assertThat(td.path("converterReady").asBoolean()).isFalse();
            for (JsonNode artifact : new JsonNode[]{ts, tc, td}) assertThat(artifact.path("sourceAnchors")).isEqualTo(br.path("sourceAnchors"));
            JsonNode actual = oracle.evaluate(td.path("payload"));
            td.path("expected").fields().forEachRemaining(field -> assertThat(actual.path(field.getKey())).as(td.path("id").asText()).isEqualTo(field.getValue()));
            assertThat(tc.path("expectedOutcome").asText()).isEqualTo(td.path("expectedValidation").asText()).isEqualTo(actual.path("outcome").asText());
            if (actual.path("outcome").asText().equals("REVIEW_REQUIRED")) {
                reviewCount++;
                for (JsonNode artifact : new JsonNode[]{br, ts, tc}) {
                    assertThat(artifact.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
                }
            }
        }
        assertThat(reviewCount).isEqualTo(2);
    }

    @Test
    void rejectsUnsupportedContextModesAndMalformedClassificationValues() throws Exception {
        AppendixFProductCodeOracle oracle = new AppendixFProductCodeOracle();
        assertThat(oracle.evaluate(null).path("outcome").asText()).isEqualTo("FAIL");
        assertThat(oracle.evaluate(payload("ELIGIBILITY", "001")).path("outcome").asText()).isEqualTo("FAIL");
        assertThat(oracle.evaluate(payload("CLASSIFICATION", "01*").put("messageRole", "HOST_DISCOUNT_904")).path("outcome").asText()).isEqualTo("FAIL");
        for (String code : new String[]{"01*", "1", "0001", "01A", "001\n", "\u0660\u0660\u0661"}) {
            assertThat(oracle.evaluate(payload("CLASSIFICATION", code)).path("outcome").asText()).as(code).isEqualTo("FAIL");
        }
        assertThat(oracle.evaluate(payload("FORMAT", "001").put("ProductCode", true)).path("outcome").asText()).isEqualTo("FAIL");
        assertThat(oracle.evaluate(payload("FORMAT", "001").putObject("ProductCode")).path("outcome").asText()).isEqualTo("FAIL");
    }

    private static Map<String, JsonNode> index(JsonNode artifacts) {
        Map<String, JsonNode> result = new HashMap<>();
        for (JsonNode artifact : artifacts) assertThat(result.put(artifact.path("id").asText(), artifact)).isNull();
        return result;
    }

    private static ObjectNode payload(String mode, String code) {
        return MAPPER.createObjectNode().put("mode", mode).put("messageRole", "TRANSACTION_PRODUCT_CODE").put("ProductCode", code);
    }
}