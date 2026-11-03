package com.coreauth.validator;

import com.coreauth.validator.canonical.PaymentSystemProductCodeOracle;
import com.coreauth.validator.coverage.GeneratePaymentSystemProductCodeCoverage;
import com.coreauth.validator.coverage.GeneratePaymentSystemProductCodeCoverage.Source;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
    void committedOraclesMatchEveryPspcSourceRowAndCoverAllCodesOnce() throws Exception {
        verifyOracle(GeneratePaymentSystemProductCodeCoverage.V41, 413,
                Map.of("ASSIGNED", 342, "DEPRECATED", 6, "RESERVED_CONEXXUS", 34, "RESERVED_PREAUTHORIZATION", 1, "RESERVED_PROPRIETARY", 29, "UNUSED", 1));
        verifyOracle(GeneratePaymentSystemProductCodeCoverage.V37, 394,
                Map.of("ASSIGNED", 312, "DEPRECATED", 25, "RESERVED_CONEXXUS", 28, "RESERVED_PROPRIETARY", 28, "UNUSED", 1));
    }

    private static void verifyOracle(Source source, int rowCount, Map<String, Integer> expectedAssignments) throws Exception {
        JsonNode resource = MAPPER.readTree(source.oracle().toFile());
        assertThat(resource).isEqualTo(GeneratePaymentSystemProductCodeCoverage.sourceOracle(source));
        assertThat(resource.path("version").asText()).isEqualTo(source.version());
        assertThat(resource.path("rows")).hasSize(rowCount);
        List<String> text = Files.readAllLines(source.text(), StandardCharsets.UTF_8);
        Set<String> codes = new HashSet<>();
        Map<String, Integer> assignments = new HashMap<>();
        for (JsonNode row : resource.path("rows")) {
            int first = row.path("sourceAnchor").path("startLine").asInt();
            int last = row.path("sourceAnchor").path("endLine").asInt();
            String literal = String.join(" ", text.subList(first - 1, last).stream().map(String::strip).toList());
            assertThat(literal).as("v%s %s", source.version(), row.path("startCode").asText())
                    .contains(row.path("description").asText(), row.path("startCode").asText(), row.path("endCode").asText());
            assertThat(row.path("sourceAnchor").path("version").asText()).isEqualTo(source.version());
            assignments.merge(row.path("assignment").asText(), 1, Integer::sum);
            for (int value = Integer.parseInt(row.path("startCode").asText()); value <= Integer.parseInt(row.path("endCode").asText()); value++) {
                assertThat(codes.add(String.format(Locale.ROOT, "%03d", value))).as("No duplicate v%s %s", source.version(), value).isTrue();
            }
        }
        assertThat(codes).hasSize(1000).contains("000", "999");
        assertThat(assignments).isEqualTo(expectedAssignments);
    }

    @Test
    void atl105ReferencesResolveInVersion2026Dash4AndAppendixFIsUnchanged() throws Exception {
        List<String> spec = Files.readAllLines(GeneratePaymentSystemProductCodeCoverage.ATL105_2026_4, StandardCharsets.UTF_8);
        Map<String, String> literals = Map.of(
                "APPX-K-034-PSPC-REFERENCE", "Conexxus PSPC reference document.",
                "APPX-K-034-LAYOUT", "ans32 Variable length of up to 32 bytes.",
                "APPX-I-049-18-PURCHASE-RESTRICTION-METHOD", "Both Chip and Host Based",
                "APPX-I-049-18-MC-AVAILABILITY", "no earlier than June 2026",
                "ELEMENT-116-VALUE-034", "Host-based Purchase Restriction is included",
                "APPX-F-ELEMENT-77-AUTHORITY", "These codes are valid entries for Element No. 77, Product Code");
        for (Source source : GeneratePaymentSystemProductCodeCoverage.SOURCES) {
            JsonNode resource = MAPPER.readTree(source.oracle().toFile());
            assertThat(resource.path("suppliedVersions")).extracting(JsonNode::asText).containsExactly("3.7", "4.1");
            assertThat(resource.path("atl105References")).hasSize(literals.size());
            for (JsonNode ref : resource.path("atl105References")) {
                assertThat(ref.path("version").asText()).isEqualTo("2026-4");
                String text = String.join(" ", spec.subList(ref.path("startLine").asInt() - 1, ref.path("endLine").asInt()));
                assertThat(text).as(ref.path("id").asText()).contains(literals.get(ref.path("id").asText()));
            }
            assertThat(resource.path("appendixFVersionCheck").path("identicalBody").asBoolean()).isTrue();
        }
        assertThat(GeneratePaymentSystemProductCodeCoverage.appendixFBody(GeneratePaymentSystemProductCodeCoverage.ATL105_2026_3))
                .isEqualTo(GeneratePaymentSystemProductCodeCoverage.appendixFBody(GeneratePaymentSystemProductCodeCoverage.ATL105_2026_4))
                .hasSize(521);
    }

    @Test
    void classifiesSourceDefinedCategoriesAssignmentsAndExceptions() throws Exception {
        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        String[][] cases = {
                {"4.1", "000", "UNUSED", "UNUSED", "Not Used"},
                {"4.1", "001", "DISPENSED_MOTOR_FUEL", "ASSIGNED", "Regular"},
                {"4.1", "011", "DISPENSED_MOTOR_FUEL", "DEPRECATED", "<Deprecated> Ethanol (5.7% blend)"},
                {"4.1", "032", "DISPENSED_MOTOR_FUEL", "ASSIGNED", "Diesel Off-Road (#1 and #2 Non-Taxable)"},
                {"4.1", "035", "DISPENSED_MOTOR_FUEL", "RESERVED_CONEXXUS", "Undefined Fuel - Reserved for Conexxus Future"},
                {"4.1", "057", "DISPENSED_MOTOR_FUEL", "RESERVED_CONEXXUS", "Undefined Fuel - Reserved for Conexxus Future Use"},
                {"4.1", "073", "DISPENSED_MOTOR_FUEL", "RESERVED_PREAUTHORIZATION", "Reserved for Preauthorization Use Only"},
                {"4.1", "076", "DISPENSED_MOTOR_FUEL", "RESERVED_PROPRIETARY", "Undefined Fuel\u2013Reserved for Proprietary Use"},
                {"4.1", "157", "AVIATION_FUEL", "ASSIGNED", "Aviation Biofuel (Gasoline)"},
                {"4.1", "308", "OTHER_DISPENSED_FUEL_METERED_PRODUCT", "ASSIGNED", "EVC-1 \u2013 Level 1 charge = 110v 15 amp"},
                {"4.1", "333", "OTHER_DISPENSED_FUEL_METERED_PRODUCT", "ASSIGNED", "Renewable Diesel (>=R95) Off-Road (Non-Taxable) (meets ASTM D975 in US / EN 590 in Europe)"},
                {"4.1", "548", "MERCHANDISE", "RESERVED_PROPRIETARY", "Undefined Health & Beauty Care\u2013Proprietary Use"},
                {"4.1", "560", "PREPAID_BILL_PAY", "ASSIGNED", "PIN Activate Prepaid Card"},
                {"4.1", "568", "PREPAID_BILL_PAY", "ASSIGNED", "Proprietary Prepaid Card Activate"},
                {"4.1", "584", "PREPAID_BILL_PAY", "RESERVED_PROPRIETARY", "Undefined Prepaid and Bill Pay- Reserved for Proprietary Use"},
                {"4.1", "591", "MERCHANDISE", "RESERVED_PROPRIETARY", "Undefined Merchandise\u2013Reserved Proprietary Use"},
                {"4.1", "600", "PACKAGED_FUEL", "ASSIGNED", "Packaged DEF (Diesel Exhaust Fluid)"},
                {"4.1", "625", "RESERVED_CONEXXUS_TABLE", "RESERVED_CONEXXUS", "Reserved for Conexxus Future Use"},
                {"4.1", "657", "VEHICLE_PRODUCT_SERVICE", "ASSIGNED", "EV Charging Fee"},
                {"4.1", "700", "CANNABINOID", "ASSIGNED", "Cannabidiol (CBD)"},
                {"4.1", "799", "RESERVED_CONEXXUS_TABLE", "RESERVED_CONEXXUS", "Reserved for Conexxus Future Use"},
                {"4.1", "850", "RESERVED_PROPRIETARY_TABLE", "RESERVED_PROPRIETARY", "Reserved for Proprietary Use"},
                {"4.1", "920", "NEGATIVE", "ASSIGNED", "POS/Loyalty Reserved Discount 1"},
                {"4.1", "955", "ADMINISTRATIVE", "ASSIGNED", "Cash Back"},
                {"4.1", "999", "ADMINISTRATIVE", "ASSIGNED", "Miscellaneous Administrative"},
                {"3.7", "000", "UNUSED", "UNUSED", "Not Used"},
                {"3.7", "001", "DISPENSED_MOTOR_FUEL", "ASSIGNED", "Unleaded Regular"},
                {"3.7", "006", "DISPENSED_MOTOR_FUEL", "DEPRECATED", "<Deprecated> Unleaded Methanol (5.7% blend)"},
                {"3.7", "011", "DISPENSED_MOTOR_FUEL", "ASSIGNED", "Unleaded Ethanol (5.7% blend)"},
                {"3.7", "052", "DISPENSED_MOTOR_FUEL", "DEPRECATED", "<Deprecated> Ultra Low Sulfur #1"},
                {"3.7", "071", "DISPENSED_MOTOR_FUEL", "RESERVED_PROPRIETARY", "Undefined Fuel\u2013Reserved for Proprietary Use"},
                {"3.7", "156", "AVIATION_FUEL", "RESERVED_CONEXXUS", "Undefined Aviation Fuel\u2013Reserved for Conexxus Future Use"},
                {"3.7", "179", "AVIATION_PRODUCT_SERVICE", "ASSIGNED", "Aircraft Work Order"},
                {"3.7", "303", "OTHER_DISPENSED_FUEL_METERED_PRODUCT", "DEPRECATED", "<Deprecated>Bottled Propane"},
                {"3.7", "325", "OTHER_DISPENSED_FUEL_METERED_PRODUCT", "ASSIGNED", "Off-Road Unleaded Regular (Non-Taxable)"},
                {"3.7", "333", "OTHER_DISPENSED_FUEL_METERED_PRODUCT", "RESERVED_CONEXXUS", "Undefined Other Fuel\u2013Reserved for Conexxus Future Use"},
                {"3.7", "568", "PREPAID_BILL_PAY", "ASSIGNED", "Proprietary Prepaid Card Activate"},
                {"3.7", "700", "RESERVED_CONEXXUS_TABLE", "RESERVED_CONEXXUS", "Reserved for Conexxus Future Use"},
                {"3.7", "915", "NEGATIVE", "RESERVED_CONEXXUS", "Undefined Negative\u2013Reserved for Conexxus Future Use"},
                {"3.7", "971", "ADMINISTRATIVE", "RESERVED_CONEXXUS", "Undefined Administrative-Reserved for Conexxus Future Use"}
        };
        for (String[] example : cases) {
            JsonNode result = oracle.evaluate(payload("CLASSIFICATION", example[1]).put("referenceVersion", example[0]));
            String label = "v" + example[0] + " " + example[1];
            assertThat(result.path("outcome").asText()).as(label).isEqualTo("PASS");
            assertThat(result.path("referenceVersion").asText()).as(label).isEqualTo(example[0]);
            assertThat(result.path("category").asText()).as(label).isEqualTo(example[2]);
            assertThat(result.path("assignment").asText()).as(label).isEqualTo(example[3]);
            assertThat(result.path("description").asText()).as(label).isEqualTo(example[4]);
            assertThat(result.path("transactionEligibility").asText()).isEqualTo("NOT_EVALUATED");
        }
        assertThat(oracle.evaluate(payload("CLASSIFICATION", "001")).path("referenceVersion").asText()).isEqualTo("4.1");
        assertThat(oracle.evaluate(payload("CLASSIFICATION", "001").put("referenceVersion", "3.5")).path("outcome").asText()).isEqualTo("FAIL");
        for (String malformed : new String[]{"1", "0001", "01A", " 001", ""}) {
            assertThat(oracle.evaluate(payload("CLASSIFICATION", malformed)).path("outcome").asText()).as(malformed).isEqualTo("FAIL");
        }
        assertThat(MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V41.oracle().toFile()).path("releaseNoteAddedCodes")).extracting(JsonNode::asText)
                .containsExactly("052", "061", "053", "054", "333", "334", "335", "336", "337", "338");
        assertThat(MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V37.oracle().toFile()).path("releaseNoteAddedCodes")).extracting(JsonNode::asText)
                .containsExactly("325", "326", "327", "328", "329", "330", "331", "332");
        for (Source source : GeneratePaymentSystemProductCodeCoverage.SOURCES) {
            for (JsonNode code : MAPPER.readTree(source.oracle().toFile()).path("releaseNoteAddedCodes")) {
                assertThat(oracle.evaluate(payload("CLASSIFICATION", code.asText()).put("referenceVersion", source.version())).path("assignment").asText())
                        .as("v%s %s", source.version(), code.asText()).isEqualTo("ASSIGNED");
            }
        }
    }

    @Test
    void element77CrosswalkKeepsAppendixFAuthoritativeAndFlagsDivergence() throws Exception {
        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        String[][] cases = {
                {"4.1", "000", "BOTH_UNUSED", "PASS"},
                {"4.1", "100", "DESCRIPTION_MATCH", "PASS"},
                {"4.1", "001", "DESCRIPTION_DIFFERS", "REVIEW_REQUIRED"},
                {"4.1", "004", "PSPC_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"4.1", "052", "DESCRIPTION_DIFFERS", "REVIEW_REQUIRED"},
                {"4.1", "006", "APPENDIX_F_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"4.1", "011", "PSPC_DEPRECATED", "REVIEW_REQUIRED"},
                {"4.1", "700", "PSPC_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"4.1", "800", "APPENDIX_F_PROPRIETARY_ASSIGNMENT", "PASS"},
                {"4.1", "810", "NEITHER_ASSIGNED", "PASS"},
                {"4.1", "895", "APPENDIX_F_PROPRIETARY_ASSIGNMENT", "REVIEW_REQUIRED"},
                {"4.1", "920", "PSPC_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"3.7", "052", "PSPC_DEPRECATED", "REVIEW_REQUIRED"},
                {"3.7", "006", "PSPC_DEPRECATED", "REVIEW_REQUIRED"},
                {"3.7", "700", "NEITHER_ASSIGNED", "PASS"},
                {"3.7", "920", "NEITHER_ASSIGNED", "PASS"},
                {"3.7", "325", "PSPC_ONLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"3.7", "100", "DESCRIPTION_MATCH", "PASS"}
        };
        for (String[] example : cases) {
            JsonNode result = oracle.evaluate(payload("ELEMENT_77_CROSSWALK", example[1]).put("referenceVersion", example[0]));
            String label = "v" + example[0] + " " + example[1];
            assertThat(result.path("relation").asText()).as(label).isEqualTo(example[2]);
            assertThat(result.path("outcome").asText()).as(label).isEqualTo(example[3]);
            assertThat(result.path("element77Authority").asText()).isEqualTo("ATL105_APPENDIX_F");
        }
        assertThat(oracle.evaluate(payload("ELEMENT_77_CROSSWALK", "001")).path("appendixF").path("description").asText()).isEqualTo("Unleaded (88 octane or less)");
    }

    @Test
    void versionDiffIdentifiesCodesWhoseMeaningChangedBetweenSuppliedVersions() throws Exception {
        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        String[][] cases = {
                {"001", "RENAMED", "PASS"},
                {"100", "UNCHANGED", "PASS"},
                {"700", "NEWLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"710", "UNCHANGED", "PASS"},
                {"052", "REUSED_AFTER_DEPRECATION", "REVIEW_REQUIRED"},
                {"016", "REUSED_AFTER_DEPRECATION", "REVIEW_REQUIRED"},
                {"011", "NEWLY_DEPRECATED", "REVIEW_REQUIRED"},
                {"006", "DEPRECATED_TO_RESERVED", "REVIEW_REQUIRED"},
                {"071", "STATE_CHANGED", "REVIEW_REQUIRED"},
                {"718", "STATE_CHANGED", "REVIEW_REQUIRED"},
                {"333", "NEWLY_ASSIGNED", "REVIEW_REQUIRED"},
                {"325", "RENAMED", "PASS"},
                {"000", "UNCHANGED", "PASS"}
        };
        for (String[] example : cases) {
            JsonNode result = oracle.evaluate(payload("VERSION_DIFF", example[0]));
            assertThat(result.path("relation").asText()).as(example[0]).isEqualTo(example[1]);
            assertThat(result.path("outcome").asText()).as(example[0]).isEqualTo(example[2]);
            assertThat(result.path("versionSensitive").asBoolean()).as(example[0]).isEqualTo("REVIEW_REQUIRED".equals(example[2]));
        }
        Map<String, ObjectNode> diff = GeneratePaymentSystemProductCodeCoverage.versionDiff(
                MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V37.oracle().toFile()), MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V41.oracle().toFile()));
        assertThat(GeneratePaymentSystemProductCodeCoverage.diffCounts(diff)).isEqualTo(Map.of(
                "UNCHANGED", 905, "RENAMED", 33, "NEWLY_ASSIGNED", 24, "REUSED_AFTER_DEPRECATION", 12,
                "DEPRECATED_TO_RESERVED", 13, "NEWLY_DEPRECATED", 6, "STATE_CHANGED", 7));
        assertThat(Files.readString(GeneratePaymentSystemProductCodeCoverage.VERSION_DIFF, StandardCharsets.UTF_8).replace("\r\n", "\n"))
                .isEqualTo(GeneratePaymentSystemProductCodeCoverage.versionDiffCsv(diff));
    }

    @Test
    void table034HostPurchaseRestrictionAppliesGatingStructureVersionAndPspcValues() throws Exception {
        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        String mc = PaymentSystemProductCodeOracle.MASTERCARD_ENHANCED_FLEET;
        JsonNode visa = oracle.evaluate(host("034", "001", "2", PaymentSystemProductCodeOracle.VISA_FLEET, null, "001"));
        assertThat(visa.path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(visa.toString()).contains("SEG111-SME-003");
        JsonNode mastercard = oracle.evaluate(host("034", "001062", "3", mc, null, "001", "062"));
        assertThat(mastercard.path("outcome").asText()).isEqualTo("PASS");
        assertThat(mastercard.path("referenceVersion").asText()).isEqualTo("UNBOUND");
        assertThat(mastercard.path("availabilityNotice").asText()).contains("June 2026");
        assertThat(mastercard.path("productCodeResults").get(1).path("v4.1").path("description").asText()).isEqualTo("DEF (Diesel Exhaust Fluid)");
        assertThat(oracle.evaluate(host("034", "001", "1", mc, null, "001")).path("outcome").asText()).isEqualTo("FAIL");
        assertThat(oracle.evaluate(host("034", "x".repeat(32), "2", mc, null, "001")).path("outcome").asText()).isEqualTo("PASS");
        assertThat(oracle.evaluate(host("034", "x".repeat(33), "2", mc, null, "001")).path("outcome").asText()).isEqualTo("FAIL");
        assertThat(oracle.evaluate(host("034", "001", "2", mc, null)).path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(oracle.evaluate(host("034", "001", "2", mc, null, "000")).path("outcome").asText()).isEqualTo("FAIL");
        JsonNode unbound = oracle.evaluate(host("034", "052", "2", mc, null, "052"));
        assertThat(unbound.path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(unbound.path("productCodeResults").get(0).path("versionSensitive").asBoolean()).isTrue();
        assertThat(unbound.toString()).contains("SEG111-SME-012");
        assertThat(oracle.evaluate(host("034", "052", "2", mc, "4.1", "052")).path("outcome").asText()).isEqualTo("PASS");
        assertThat(oracle.evaluate(host("034", "052", "2", mc, "3.7", "052")).path("outcome").asText()).isEqualTo("REVIEW_REQUIRED");
        assertThat(oracle.evaluate(host("034", "325", "2", mc, null, "325")).path("outcome").asText()).isEqualTo("PASS");
    }

    @Test
    void chainPackagesReplayEveryBrScenarioCaseAndTestData() throws Exception {
        JsonNode appendixF = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.APPENDIX_F_ORACLE.toFile());
        JsonNode v41 = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V41.oracle().toFile());
        JsonNode v37 = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V37.oracle().toFile());
        Map<String, ObjectNode> crosswalk41 = GeneratePaymentSystemProductCodeCoverage.crosswalk(v41, appendixF);
        Map<String, ObjectNode> crosswalk37 = GeneratePaymentSystemProductCodeCoverage.crosswalk(v37, appendixF);
        Map<String, ObjectNode> diff = GeneratePaymentSystemProductCodeCoverage.versionDiff(v37, v41);

        JsonNode latest = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V41.chains().toFile());
        assertThat(latest).isEqualTo(GeneratePaymentSystemProductCodeCoverage.chains(v41, crosswalk41));
        JsonNode earlier = MAPPER.readTree(GeneratePaymentSystemProductCodeCoverage.V37.chains().toFile());
        assertThat(earlier).isEqualTo(GeneratePaymentSystemProductCodeCoverage.legacyChains(v37, crosswalk37, diff));
        assertCsv(GeneratePaymentSystemProductCodeCoverage.V41.crosswalk(), GeneratePaymentSystemProductCodeCoverage.crosswalkCsv(crosswalk41));
        assertCsv(GeneratePaymentSystemProductCodeCoverage.V37.crosswalk(), GeneratePaymentSystemProductCodeCoverage.crosswalkCsv(crosswalk37));

        assertThat(latest.path("coverageSummary").path("classificationTestData").asInt()).isEqualTo(1000);
        assertThat(latest.path("coverageSummary").path("crosswalkTestData").asInt()).isEqualTo(1000);
        assertThat(latest.path("coverageSummary").path("crosswalkReviewRequired").asInt()).isEqualTo(142);
        assertThat(latest.path("coverageSummary").path("hostRestrictionTestData").asInt()).isEqualTo(32);
        assertThat(earlier.path("coverageSummary").path("crosswalkReviewRequired").asInt()).isEqualTo(104);
        assertThat(earlier.path("coverageSummary").path("versionSensitiveCodes").asInt()).isEqualTo(62);

        PaymentSystemProductCodeOracle oracle = new PaymentSystemProductCodeOracle();
        replay(latest, 413 + 1 + 1 + 6, 2043, oracle);
        replay(earlier, 394 + 1 + 1, 3000, oracle);
    }

    private static void replay(JsonNode pack, int brCount, int tdCount, PaymentSystemProductCodeOracle oracle) {
        Set<String> brs = ids(pack.path("businessRequirements"));
        Set<String> scenarios = ids(pack.path("testScenarios"));
        Set<String> cases = ids(pack.path("testCases"));
        assertThat(brs).hasSize(brCount);
        assertThat(pack.path("testData")).hasSize(tdCount);
        for (JsonNode ts : pack.path("testScenarios")) assertThat(brs).containsAll(texts(ts.path("requirementIds")));
        for (JsonNode tc : pack.path("testCases")) assertThat(scenarios).containsAll(texts(tc.path("scenarioIds")));
        Set<String> covered = new HashSet<>();
        for (JsonNode td : pack.path("testData")) {
            assertThat(brs).contains(td.path("coversBr").asText());
            covered.add(td.path("coversBr").asText());
            assertThat(cases).containsAll(texts(td.path("testCaseIds")));
            JsonNode actual = oracle.evaluate(td.path("payload"));
            td.path("expected").fieldNames().forEachRemaining(field ->
                    assertThat(actual.path(field)).as("%s %s", td.path("id").asText(), field).isEqualTo(td.path("expected").path(field)));
        }
        assertThat(covered).isEqualTo(brs);
    }

    private static void assertCsv(Path path, String expected) throws Exception {
        assertThat(Files.readString(path, StandardCharsets.UTF_8).replace("\r\n", "\n")).isEqualTo(expected);
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

    private static ObjectNode host(String tableId, String data, String method, String program, String version, String... codes) {
        ObjectNode payload = MAPPER.createObjectNode().put("mode", "HOST_PURCHASE_RESTRICTION").put("tableId", tableId)
                .put("tableData", data).put("purchaseRestrictionMethod", method).put("fleetProgram", program);
        if (version != null) payload.put("referenceVersion", version);
        if (codes.length > 0) {
            var array = payload.putArray("productCodes");
            for (String code : codes) array.add(code);
        }
        return payload;
    }
}
