package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Pattern;

public final class GenerateAppendixFProductCodeCoverage {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Pattern PAGE = Pattern.compile("Appendix F-(\\d+)");
    private static final Pattern ROW = Pattern.compile("^(\\d{3})(?:[-\u2013](\\d{3}))? (.+)$");
    private static final Pattern PROPRIETARY = Pattern.compile("^Code Nos\\. (\\d{3})\u2013(\\d{3}) are reserved for proprietary use\\.$");
    public static final Path SOURCE = Path.of("specifications/ATL105/docs/specs/extracted_text.txt");
    public static final Path ORACLE = Path.of("src/main/resources/atl105/appendix-f-product-code-oracle.json");
    public static final Path CHAINS = Path.of("specifications/ATL105/test-output/test-json/appendix-f-product-code-chain-package.json");

    private GenerateAppendixFProductCodeCoverage() {
    }

    public static void main(String[] args) throws Exception {
        ObjectNode oracle = sourceOracle(SOURCE);
        Files.createDirectories(ORACLE.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(ORACLE.toFile(), oracle);
        ObjectNode chains = chains(oracle);
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(CHAINS.toFile(), chains);
        System.out.printf("Source rows=%d codes=1000 classification TDs=1000 format TDs=13%n", oracle.path("rows").size());
    }

    public static ObjectNode sourceOracle(Path source) throws Exception {
        var lines = Files.readAllLines(source, StandardCharsets.UTF_8);
        ObjectNode oracle = MAPPER.createObjectNode();
        oracle.put("specification", "ATL105");
        oracle.put("version", "2026-3");
        oracle.put("scope", "SOURCE_CLASSIFICATION_ONLY_NOT_TRANSACTION_ACCEPTANCE");
        oracle.put("source", SOURCE.toString().replace('\\', '/'));
        ArrayNode rows = oracle.putArray("rows");
        int page = 0;
        boolean[] seen = new boolean[1000];
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index).strip();
            if (page > 0 && line.equals("Appendix G-1")) break;
            var heading = PAGE.matcher(line);
            if (heading.matches()) page = Integer.parseInt(heading.group(1));
            if (page == 0) continue;
            var row = ROW.matcher(line);
            var proprietary = PROPRIETARY.matcher(line);
            if (!row.matches() && !proprietary.matches()) continue;
            int start = Integer.parseInt(row.matches() ? row.group(1) : proprietary.group(1));
            int end = Integer.parseInt(row.matches() ? (row.group(2) == null ? row.group(1) : row.group(2)) : proprietary.group(2));
            String description = row.matches() ? row.group(3) : "reserved for proprietary use.";
            int endLine = index + 1;
            if (start == 606 || start == 658) {
                String continuation = lines.get(index + 1).strip();
                String expected = start == 606 ? "PCATS Future Use" : "Reserved for PCATS Use";
                if (!expected.equals(continuation)) throw new IllegalStateException("Unexpected wrapped source row " + code(start));
                description += " " + continuation;
                endLine++;
            }
            ObjectNode entry = rows.addObject();
            entry.put("startCode", code(start));
            entry.put("endCode", code(end));
            entry.put("sourceRange", row.matches() ? line.substring(0, line.indexOf(' ')) : code(start) + "\u2013" + code(end));
            entry.put("description", description);
            entry.put("table", table(page));
            String lower = description.toLowerCase(Locale.ROOT);
            entry.put("assignment", start == 0 ? "UNUSED" : lower.contains("reserved") ? "RESERVED" : lower.contains("undefined") ? "UNDEFINED" : "ASSIGNED");
            entry.put("role", page == 17 ? (start <= 802 ? "ADMINISTRATIVE" : start <= 806 ? "MERCHANDISE" : start <= 808 ? "MOTOR_FUEL" : "NEGATIVE") : table(page));
            entry.set("sourceAnchor", anchor("Appendix F-" + page, "source-row-" + code(start), index + 1, endLine));
            for (int value = start; value <= end; value++) {
                if (seen[value]) throw new IllegalStateException("Overlapping source code " + code(value));
                seen[value] = true;
            }
        }
        for (int value = 0; value < 1000; value++) {
            if (!seen[value]) throw new IllegalStateException("Missing source code " + code(value));
        }
        ObjectNode conflict = oracle.putObject("sourceConflict");
        conflict.putArray("codes").add("895").add("896");
        conflict.put("overviewClaim", "Appendix F-1: FSA/HRA 890-894; proprietary reserved 895-899");
        conflict.put("detailedClaim", "Appendix F-19: 895 Co-Payment, 896 Transit; F-20: proprietary reserved 897-899");
        conflict.put("status", "REVIEW_REQUIRED");
        return oracle;
    }

    private static String table(int page) {
        return switch (page) {
            case 1 -> "UNUSED";
            case 2, 3, 4 -> "MOTOR_FUEL";
            case 5, 6 -> "AUTOMOTIVE_PRODUCT_SERVICE";
            case 7 -> "AVIATION_FUEL";
            case 8, 9 -> "AVIATION_PRODUCT_SERVICE";
            case 10 -> "MARINE_FUEL";
            case 11 -> "MARINE_PRODUCT_SERVICE";
            case 12 -> "OTHER_FUEL";
            case 13, 14, 15 -> "MERCHANDISE";
            case 16 -> "RESERVED_FUTURE_USE_TABLE";
            case 17 -> "DEBIT_SURCHARGE_TABLE";
            case 18, 20 -> "PROPRIETARY_RESERVED";
            case 19 -> "FSA_HRA";
            case 21 -> "NEGATIVE";
            case 22 -> "ADMINISTRATIVE";
            default -> throw new IllegalStateException("Unexpected Appendix F page " + page);
        };
    }

    private static ObjectNode chains(ObjectNode oracle) {
        ObjectNode pack = MAPPER.createObjectNode();
        pack.putObject("manifest").put("packageId", "ATL105-APPF-PRODUCT-CODE-CLASSIFICATION-FORMAT").put("specification", "ATL105").put("specificationVersion", "2026-3");
        pack.put("scope", "Classification/format harness only; not merchant eligibility, full Segment 102, converter or SME certification");
        pack.put("status", "PARTIALLY_COVERED");
        pack.set("sourceConflict", oracle.path("sourceConflict").deepCopy());
        pack.putObject("coverageSummary").put("sourceRows", oracle.path("rows").size())
            .put("classificationTestData", 1000).put("formatTestData", 13)
            .put("classificationPasses", 998).put("sourceConflictReviews", 2)
            .put("formatPasses", 3).put("formatFailures", 10)
            .put("transactionEligibilityCertified", false).put("smeApproved", false);
        pack.putArray("businessRequirements");
        pack.putArray("testScenarios");
        pack.putArray("testCases");
        pack.putArray("testData");
        for (JsonNode row : oracle.path("rows")) {
            String suffix = row.path("startCode").asText();
            String br = "BR-APPF-ROW-" + suffix;
            addRequirement(pack, br, "Preserve source row " + row.path("sourceRange").asText() + " and its detailed-table classification.", (ObjectNode) row.path("sourceAnchor"), "SOURCE_CLASSIFICATION_ONLY");
            for (int value = Integer.parseInt(suffix); value <= Integer.parseInt(row.path("endCode").asText()); value++) {
                String product = code(value);
                ObjectNode expected = MAPPER.createObjectNode();
                boolean conflict = value == 895 || value == 896;
                expected.put("outcome", conflict ? "REVIEW_REQUIRED" : "PASS");
                expected.put("table", row.path("table").asText());
                expected.put("assignment", row.path("assignment").asText());
                expected.put("role", row.path("role").asText());
                expected.put("description", row.path("description").asText());
                expected.put("sourceConflict", conflict);
                addChain(pack, br, "CLASSIFY-" + product, "CLASSIFICATION", MAPPER.getNodeFactory().textNode(product), expected, (ObjectNode) row.path("sourceAnchor"));
            }
        }
        ObjectNode formatAnchor = anchor("13.2 Element 77", "fixed-three-ascii-digits-transaction-context", 21018, 21023);
        addRequirement(pack, "BR-APPF-FORMAT", "Transaction Product Code is a string of exactly three ASCII digits; Prompt 904 wildcard patterns are outside this scope.", formatAnchor, "FORMAT_ONLY");
        JsonNode[] inputs = {MAPPER.getNodeFactory().textNode("001"), MAPPER.getNodeFactory().textNode("999"), MAPPER.getNodeFactory().textNode("000"), MAPPER.getNodeFactory().textNode("1"), MAPPER.getNodeFactory().textNode("0001"), MAPPER.getNodeFactory().textNode("01A"), MAPPER.getNodeFactory().textNode("01*"), MAPPER.getNodeFactory().textNode(" 001"), MAPPER.getNodeFactory().textNode("001\n"), MAPPER.getNodeFactory().textNode(""), MAPPER.getNodeFactory().nullNode(), MAPPER.getNodeFactory().numberNode(1), MAPPER.getNodeFactory().textNode("\u0660\u0660\u0661")};
        for (int index = 0; index < inputs.length; index++) {
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", index < 3 ? "PASS" : "FAIL");
            addChain(pack, "BR-APPF-FORMAT", "FORMAT-" + index, "FORMAT", inputs[index], expected, formatAnchor);
        }
        return pack;
    }

    private static void addRequirement(ObjectNode pack, String id, String title, ObjectNode anchor, String scope) {
        ObjectNode br = ((ArrayNode) pack.path("businessRequirements")).addObject();
        br.put("id", id).put("title", title).put("status", "REVIEW_REQUIRED").put("assertionScope", scope).put("smeApproved", false);
        br.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static void addChain(ObjectNode pack, String br, String suffix, String mode, JsonNode input, ObjectNode expected, ObjectNode anchor) {
        String tsId = "TS-APPF-" + suffix;
        String tcId = "TC-APPF-" + suffix;
        String tdId = "TD-APPF-" + suffix;
        String status = "REVIEW_REQUIRED";
        ObjectNode ts = ((ArrayNode) pack.path("testScenarios")).addObject();
        ts.put("id", tsId).put("status", status);
        ts.putArray("requirementIds").add(br);
        ObjectNode tc = ((ArrayNode) pack.path("testCases")).addObject();
        tc.put("id", tcId).put("expectedOutcome", expected.path("outcome").asText()).put("status", status);
        tc.putArray("scenarioIds").add(tsId);
        tc.putArray("testDataIds").add(tdId);
        ObjectNode td = ((ArrayNode) pack.path("testData")).addObject();
        td.put("id", tdId).put("coversBr", br).put("producer", "TEST_SOLUTION").put("readiness", "EXECUTABLE").put("fixtureKind", "PRODUCT_CODE_CLASSIFICATION_FORMAT_HARNESS").put("converterReady", false).put("expectedValidation", expected.path("outcome").asText());
        td.putArray("testCaseIds").add(tcId);
        ObjectNode payload = td.putObject("payload");
        payload.put("mode", mode).put("messageRole", "TRANSACTION_PRODUCT_CODE");
        payload.set("ProductCode", input);
        td.set("expected", expected);
        for (ObjectNode artifact : new ObjectNode[]{ts, tc, td}) artifact.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static ObjectNode anchor(String section, String rule, int start, int end) {
        return MAPPER.createObjectNode().put("specification", "ATL105").put("version", "2026-3").put("section", section).put("segment", "102,157").put("element", "77").put("rule", rule).put("startLine", start).put("endLine", end);
    }

    private static String code(int value) {
        return String.format(Locale.ROOT, "%03d", value);
    }
}