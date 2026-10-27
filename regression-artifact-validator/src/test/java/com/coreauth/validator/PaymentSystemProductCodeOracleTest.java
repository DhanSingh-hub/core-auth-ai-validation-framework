package com.coreauth.validator;

import com.coreauth.validator.canonical.PaymentSystemProductCodeOracle;
import com.coreauth.validator.coverage.GeneratePaymentSystemProductCodeCoverage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentSystemProductCodeOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void committedOracleMatchesEveryPspcSourceRowAndCoversAllCodesOnce() throws Exception {
        JsonNode resource = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.ORACLE.toFile());
        assertThat(resource).isEqualTo(GeneratePaymentSystemProductCodeCoverage.sourceOracle(GeneratePaymentSystemProductCodeCoverage.SOURCE));
        assertThat(resource.path("version").asText()).isEqualTo("4.1");
        assertThat(resource.path("rows")).hasSize(413);
        List<String> source = Files.readAllLines(GeneratePaymentSystemProductCodeCoverage.SOURCE, StandardCharsets.UTF_8);
        Set<String> codes = new HashSet<>();
        Map<String, Integer> assignments = new HashMap<>();
        for (JsonNode row : resource.path("rows")) {
            int first = row.path("sourceAnchor").path("startLine").asInt();
            int last = row.path("sourceAnchor").path("endLine").asInt();
            String literal = String.join(" ", source.subList(first - 1, last).stream().map(String::strip).toList());
            assertThat(literal).contains(row.path("description").asText(), row.path("startCode").asText(), row.path("endCode").asText());
            assignments.merge(row.path("assignment").asText(), 1, Integer::sum);
            for (int value = Integer.parseInt(row.path("startCode").asText()); value <= Integer.parseInt(row.path("endCode").asText()); value++) {
                assertThat(codes.add(String.format(Locale.ROOT, "%03d", value))).as("No duplicate %s", value).isTrue();
            }
        }
        assertThat(codes).hasSize(1000).contains("000", "999");
        assertThat(assignments).containsEntry("ASSIGNED", 342).containsEntry("DEPRECATED", 6).containsEntry("RESERVED_CONEXXUS", 34)
                .containsEntry("RESERVED_PREAUTHORIZATION", 1).containsEntry("RESERVED_PROPRIETARY", 29).containsEntry("UNUSED", 1);
    }

    @Test
    void atl105ReferencesResolveInVersion2026Dash4AndAppendixFIsUnchanged() throws Exception {
        JsonNode resource = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.ORACLE.toFile());
        List<String> spec = Files.readAllLines(GeneratePaymentSystemProductCodeCoverage.ATL105_2026_4, StandardCharsets.UTF_8);
        Map<String, String> literals = Map.of(
                "APPX-K-034-PSPC-REFERENCE", "Conexxus PSPC reference document.",
                "APPX-K-034-LAYOUT", "ans32 Variable length of up to 32 bytes.",
                "APPX-I-049-18-PURCHASE-RESTRICTION-METHOD", "Both Chip and Host Based",
                "APPX-I-049-18-MC-AVAILABILITY", "no earlier than June 2026",
                "ELEMENT-116-VALUE-034", "Host-based Purchase Restriction is included",
                "APPX-F-ELEMENT-77-AUTHORITY", "These codes are valid entries for Element No. 77, Product Code");
        assertThat(resource.path("atl105References")).hasSize(literals.size());
        for (JsonNode ref : resource.path("atl105References")) {
            assertThat(ref.path("version").asText()).isEqualTo("2026-4");
            String text = String.join(" ", spec.subList(ref.path("startLine").asInt() - 1, ref.path("endLine").asInt()));
            assertThat(text).as(ref.path("id").asText()).contains(literals.get(ref.path("id").asText()));
        }
        assertThat(resource.path("appendixFVersionCheck").path("identicalBody").asBoolean()).isTrue();
        assertThat(GeneratePaymentSystemProductCodeCoverage.appendixFBody(GeneratePaymentSystemProductCodeCoverage.ATL105_2026_3))
                .isEqualTo(GeneratePaymentSystemProductCodeCoverage.appendixFBody(GeneratePaymentSystemProductCodeCoverage.ATL105_2026_4))
                .hasSize(521);
    }

    @Test
    void classifiesSourceDefinedCategoriesAssignmentsAndExceptions() throws Exception {
        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        String[][] cases = {
                {"000", "UNUSED", "UNUSED", "Not Used"},
                {"001", "DISPENSED_MOTOR_FUEL", "ASSIGNED", "Regular"},
                {"011", "DISPENSED_MOTOR_FUEL", "DEPRECATED", "<Deprecated> Ethanol (5.7% blend)"},
                {"032", "DISPENSED_MOTOR_FUEL", "ASSIGNED", "Diesel Off-Road (#1 and #2 Non-Taxable)"},
                {"035", "DISPENSED_MOTOR_FUEL", "RESERVED_CONEXXUS", "Undefined Fuel - Reserved for Conexxus Future"},
                {"057", "DISPENSED_MOTOR_FUEL", "RESERVED_CONEXXUS", "Undefined Fuel - Reserved for Conexxus Future Use"},
                {"073", "DISPENSED_MOTOR_FUEL", "RESERVED_PREAUTHORIZATION", "Reserved for Preauthorization Use Only"},
                {"076", "DISPENSED_MOTOR_FUEL", "RESERVED_PROPRIETARY", "Undefined Fuel\u2013Reserved for Proprietary Use"},
                {"157", "AVIATION_FUEL", "ASSIGNED", "Aviation Biofuel (Gasoline)"},
                {"308", "OTHER_DISPENSED_FUEL_METERED_PRODUCT", "ASSIGNED", "EVC-1 \u2013 Level 1 charge = 110v 15 amp"},
                {"333", "OTHER_DISPENSED_FUEL_METERED_PRODUCT", "ASSIGNED", "Renewable Diesel (>=R95) Off-Road (Non-Taxable) (meets ASTM D975 in US / EN 590 in Europe)"},
                {"548", "MERCHANDISE", "RESERVED_PROPRIETARY", "Undefined Health & Beauty Care\u2013Proprietary Use"},
                {"560", "PREPAID_BILL_PAY", "ASSIGNED", "PIN Activate Prepaid Card"},
                {"568", "PREPAID_BILL_PAY", "ASSIGNED", "Proprietary Prepaid Card Activate"},
                {"584", "PREPAID_BILL_PAY", "RESERVED_PROPRIETARY", "Undefined Prepaid and Bill Pay- Reserved for Proprietary Use"},
                {"591", "MERCHANDISE", "RESERVED_PROPRIETARY", "Undefined Merchandise\u2013Reserved Proprietary Use"},
                {"600", "PACKAGED_FUEL", "ASSIGNED", "Packaged DEF (Diesel Exhaust Fluid)"},
                {"625", "RESERVED_CONEXXUS_TABLE", "RESERVED_CONEXXUS", "Reserved for Conexxus Future Use"},
                {"657", "VEHICLE_PRODUCT_SERVICE", "ASSIGNED", "EV Charging Fee"},
                {"700", "CANNABINOID", "ASSIGNED", "Cannabidiol (CBD)"},
                {"799", "RESERVED_CONEXXUS_TABLE", "RESERVED_CONEXXUS", "Reserved for Conexxus Future Use"},
                {"850", "RESERVED_PROPRIETARY_TABLE", "RESERVED_PROPRIETARY", "Reserved for Proprietary Use"},
                {"920", "NEGATIVE", "ASSIGNED", "POS/Loyalty Reserved Discount 1"},
                {"955", "ADMINISTRATIVE", "ASSIGNED", "Cash Back"},
                {"999", "ADMINISTRATIVE", "ASSIGNED", "Miscellaneous Administrative"}
        };
        for (String[] example : cases) {
            JsonNode result = oracle.evaluate(payload("CLASSIFICATION", example[0]));
            assertThat(result.path("outcome").asText()).as(example[0]).isEqualTo("PASS");
            assertThat(result.path("category").asText()).as(example[0]).isEqualTo(example[1]);
            assertThat(result.path("assignment").asText()).as(example[0]).isEqualTo(example[2]);
            assertThat(result.path("description").asText()).as(example[0]).isEqualTo(example[3]);
            assertThat(result.path("transactionEligibility").asText()).isEqualTo("NOT_EVALUATED");
        }
        for (String malformed : new String[]{"1", "0001", "01A", " 001", ""}) {
            assertThat(oracle.evaluate(payload("CLASSIFICATION", malformed)).path("outcome").asText()).as(malformed).isEqualTo("FAIL");
        }
        JsonNode resource = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.ORACLE.toFile());
        assertThat(resource.path("releaseNotesV41Codes")).extracting(JsonNode::asText)
                .containsExactly("052", "061", "053", "054", "333", "334", "335", "336", "337", "338");
        for (JsonNode code : resource.path("releaseNotesV41Codes")) {
            assertThat(oracle.evaluate(payload("CLASSIFICATION", code.asText())).path("assignment").asText()).isEqualTo("ASSIGNED");
        }
    }

    @Test
    void element77CrosswalkKeepsAppendixFAuthoritativeAndFlagsDivergence() throws Exception {
        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        String[][] cases = {
                {"000", "BOTH_UNUSED", "PASS"},
                {"100", "DESCRIPTION_MATCH", "PASS"},
                {"001", "DESCRIPTION_DIFFERS", "REVIEW_REQUIRED"},
                {"004", "PSPC_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"052", "DESCRIPTION_DIFFERS", "REVIEW_REQUIRED"},
                {"006", "APPENDIX_F_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"011", "PSPC_DEPRECATED", "REVIEW_REQUIRED"},
                {"700", "PSPC_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"800", "APPENDIX_F_PROPRIETARY_ASSIGNMENT", "PASS"},
                {"810", "NEITHER_ASSIGNED", "PASS"},
                {"895", "APPENDIX_F_PROPRIETARY_ASSIGNMENT", "REVIEW_REQUIRED"},
                {"920", "PSPC_ONLY_ASSIGNED", "REVIEW_REQUIRED"}
        };
        for (String[] example : cases) {
            JsonNode result = oracle.evaluate(payload("ELEMENT_77_CROSSWALK", example[0]));
            assertThat(result.path("relation").asText()).as(example[0]).isEqualTo(example[1]);
            assertThat(result.path("outcome").asText()).as(example[0]).isEqualTo(example[2]);
            assertThat(result.path("element77Authority").asText()).isEqualTo("ATL105_APPENDIX_F");
        }
        assertThat(oracle.evaluate(payload("ELEMENT_77_CROSSWALK", "001")).path("appendixF").path("description").asText()).isEqualTo("Unleaded (88 octane or less)");
    }

    @Test
    void table034HostPurchaseRestrictionAppliesGatingStructureAndPspcValues() throws Exception {
        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        JsonNode visa = oracle.evaluate(host("034", "001", "2", PaymentSystemProductCodeOracle.VISA_FLEET, "001"));
        assertThat(visa.path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(visa.toString()).contains("SEG111-SME-003");
        JsonNode mastercard = oracle.evaluate(host("034", "001062", "3", PaymentSystemProductCodeOracle.MASTERCARD_ENHANCED_FLEET, "001", "062"));
        assertThat(mastercard.path("outcome").asText()).isEqualTo("PASS");
        assertThat(mastercard.path("availabilityNotice").asText()).contains("June 2026");
        assertThat(mastercard.path("productCodeResults").get(1).path("description").asText()).isEqualTo("DEF (Diesel Exhaust Fluid)");
        assertThat(oracle.evaluate(host("034", "001", "1", PaymentSystemProductCodeOracle.MASTERCARD_ENHANCED_FLEET, "001")).path("outcome").asText()).isEqualTo("FAIL");
        assertThat(oracle.evaluate(host("034", "x".repeat(32), "2", PaymentSystemProductCodeOracle.MASTERCARD_ENHANCED_FLEET, "001")).path("outcome").asText()).isEqualTo("PASS");
        assertThat(oracle.evaluate(host("034", "x".repeat(33), "2", PaymentSystemProductCodeOracle.MASTERCARD_ENHANCED_FLEET, "001")).path("outcome").asText()).isEqualTo("FAIL");
        assertThat(oracle.evaluate(host("034", "001", "2", PaymentSystemProductCodeOracle.MASTERCARD_ENHANCED_FLEET)).path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(oracle.evaluate(host("034", "001", "2", PaymentSystemProductCodeOracle.MASTERCARD_ENHANCED_FLEET, "000")).path("outcome").asText()).isEqualTo("FAIL");
    }

    @Test
    void chainPackageReplaysEveryBrScenarioCaseAndTestData() throws Exception {
        JsonNode oracleSource = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.ORACLE.toFile());
        JsonNode appendixF = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.APPENDIX_F_ORACLE.toFile());
        Map<String, ObjectNode> crosswalk = GeneratePaymentSystemProductCodeCoverage.crosswalk(oracleSource, appendixF);
        JsonNode pack = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.CHAINS.toFile());
        assertThat(pack).isEqualTo(GeneratePaymentSystemProductCodeCoverage.chains(oracleSource, crosswalk));
        assertThat(Files.readString(GeneratePaymentSystemProductCodeCoverage.CROSSWALK, StandardCharsets.UTF_8))
                .isEqualTo(GeneratePaymentSystemProductCodeCoverage.crosswalkCsv(crosswalk));

        JsonNode summary = pack.path("coverageSummary");
        assertThat(summary.path("classificationTestData").asInt()).isEqualTo(1000);
        assertThat(summary.path("crosswalkTestData").asInt()).isEqualTo(1000);
        assertThat(summary.path("hostRestrictionTestData").asInt()).isEqualTo(21);
        int relationTotal = 0;
        for (JsonNode count : summary.path("crosswalkRelations")) relationTotal += count.asInt();
        assertThat(relationTotal).isEqualTo(1000);

        Set<String> brs = ids(pack.path("businessRequirements"));
        Set<String> scenarios = ids(pack.path("testScenarios"));
        Set<String> cases = ids(pack.path("testCases"));
        assertThat(brs).hasSize(413 + 1 + 1 + 5);
        for (JsonNode ts : pack.path("testScenarios")) assertThat(brs).containsAll(texts(ts.path("requirementIds")));
        for (JsonNode tc : pack.path("testCases")) assertThat(scenarios).containsAll(texts(tc.path("scenarioIds")));

        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        for (JsonNode td : pack.path("testData")) {
            assertThat(brs).contains(td.path("coversBr").asText());
            assertThat(cases).containsAll(texts(td.path("testCaseIds")));
            JsonNode actual = oracle.evaluate(td.path("payload"));
            td.path("expected").fieldNames().forEachRemaining(field ->
                    assertThat(actual.path(field)).as("%s %s", td.path("id").asText(), field).isEqualTo(td.path("expected").path(field)));
        }
    }

    private static Set<String> ids(JsonNode array) {
        Set<String> ids = new HashSet<>();
        array.forEach(node -> assertThat(ids.add(node.path("id").asText())).as("Unique %s", node.path("id").asText()).isTrue());
        return ids;
    }

    private static List<String> texts(JsonNode array) {
        return java.util.stream.StreamSupport.stream(array.spliterator(), false).map(JsonNode::asText).toList();
    }

    private static ObjectNode payload(String mode, String code) {
        return MAPPER.createObjectNode().put("mode", mode).put("ProductCode", code);
    }

    private static ObjectNode host(String tableId, String data, String method, String program, String... codes) {
        ObjectNode payload = MAPPER.createObjectNode().put("mode", "HOST_PURCHASE_RESTRICTION").put("tableId", tableId)
                .put("tableData", data).put("purchaseRestrictionMethod", method).put("fleetProgram", program);
        if (codes.length > 0) {
            var array = payload.putArray("productCodes");
            for (String code : codes) array.add(code);
        }
        return payload;
    }
}
