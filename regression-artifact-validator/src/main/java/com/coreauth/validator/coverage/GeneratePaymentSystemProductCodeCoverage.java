package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Ingests the Conexxus Payment System Product Codes (PSPC) reference that ATL105 2026-4 Appendix K-27
 * (Table ID 034) points to, and cross-references it with ATL105 Appendix F (Element 77).
 */
public final class GeneratePaymentSystemProductCodeCoverage {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonNodeFactory NODES = JsonNodeFactory.instance;
    public static final String PSPC_VERSION = "4.1";
    public static final String ATL105_VERSION = "2026-4";
    public static final Path SOURCE = Path.of("specifications/ATL105/docs/specs/extracted_text_pspc_v4.1.txt");
    public static final Path SOURCE_PDF = Path.of("specifications/ATL105/docs/specs/Conexxus_Payment_System_Product_Codes_Version_4.1_2022-05-12.pdf");
    public static final Path ATL105_2026_4 = Path.of("specifications/ATL105/docs/specs/extracted_text_new.txt");
    public static final Path ATL105_2026_3 = Path.of("specifications/ATL105/docs/specs/extracted_text.txt");
    public static final Path APPENDIX_F_ORACLE = Path.of("src/main/resources/atl105/appendix-f-product-code-oracle.json");
    public static final Path ORACLE = Path.of("src/main/resources/atl105/pspc-product-code-oracle.json");
    public static final Path CHAINS = Path.of("specifications/ATL105/test-output/test-json/pspc-product-code-chain-package.json");
    public static final Path CROSSWALK = Path.of("specifications/ATL105/test-output/test-json/pspc-appendix-f-crosswalk.csv");

    private static final Pattern PAGE = Pattern.compile("^--- PAGE (\\d+) ---$");
    private static final Pattern ROW = Pattern.compile("^(.*?\\S)\\s+(\\d{3})(?:\\s*-\\s*(\\d{3}))?$");
    private static final Pattern RELEASE_NOTE = Pattern.compile("^\u2022 (\\d{3}):\\s+.+$");
    private static final Pattern APPENDIX_F_NOISE = Pattern.compile("^(--- PAGE.*|BUYPASS.*|Software Version.*|Specification Release.*|\\w+ \\d\\d, 20\\d\\d|Appendix F-\\d+)$");
    private static final Set<String> CATEGORY_HEADERS = Set.of("001-099", "100-149", "150-174", "175-224", "225-249", "250-299",
            "300-399", "400-599", "560-590", "600-624", "650-699", "700-719", "900-949", "950-999");
    private static final String FOOTNOTE_MARKER = "\u2217";
    private static final String FOOTNOTE_032_033 = "Code 032 and 033 should only be used by existing implementations that limit fuel to codes 001-099. "
            + "The preferred codes are 311-320, which provide more granularity. 032 and 033 are likely to be deprecated in the future.";

    private GeneratePaymentSystemProductCodeCoverage() {
    }

    public static void main(String[] args) throws Exception {
        ObjectNode oracle = sourceOracle(SOURCE);
        Files.createDirectories(ORACLE.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(ORACLE.toFile(), oracle);
        JsonNode appendixF = MAPPER.readTree(APPENDIX_F_ORACLE.toFile());
        Map<String, ObjectNode> crosswalk = crosswalk(oracle, appendixF);
        Files.createDirectories(CHAINS.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(CHAINS.toFile(), chains(oracle, crosswalk));
        Files.writeString(CROSSWALK, crosswalkCsv(crosswalk), StandardCharsets.UTF_8);
        System.out.printf("PSPC v%s rows=%d codes=1000 crosswalk=%s%n", PSPC_VERSION, oracle.path("rows").size(), relationCounts(crosswalk));
    }

    public static ObjectNode sourceOracle(Path source) throws Exception {
        List<String> lines = Files.readAllLines(source, StandardCharsets.UTF_8);
        ObjectNode oracle = MAPPER.createObjectNode();
        oracle.put("reference", "Conexxus Payment System Product Codes (PSPC)");
        oracle.put("publisher", "Conexxus, Inc. (Registration Authority for ASC X9.104 Part 2 product codes)");
        oracle.put("version", PSPC_VERSION);
        oracle.put("versionDate", "2022-05-12");
        oracle.put("scope", "SOURCE_CLASSIFICATION_ONLY_NOT_TRANSACTION_ACCEPTANCE");
        oracle.put("source", path(source));
        oracle.put("sourcePdf", path(SOURCE_PDF));
        oracle.put("versionBinding", "ATL105 2026-4 Appendix K-27 names the Conexxus PSPC document without a version. Version 4.1 is the copy supplied to the Test Team; see register item SEG111-SME-012.");
        oracle.set("atl105References", atl105References());
        oracle.set("appendixFVersionCheck", appendixFVersionCheck());
        oracle.set("releaseNotesV41Codes", releaseNotes(lines));
        ArrayNode rows = oracle.putArray("rows");
        boolean[] seen = new boolean[1000];
        boolean started = false;
        int page = 0;
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index).strip();
            var pageMatch = PAGE.matcher(line);
            if (pageMatch.matches()) {
                page = Integer.parseInt(pageMatch.group(1));
                continue;
            }
            if (line.equals("DESCRIPTION CODE")) {
                started = true;
                continue;
            }
            if (!started || line.isEmpty()) continue;
            var row = ROW.matcher(line);
            if (!row.matches()) continue;
            int start = Integer.parseInt(row.group(2));
            int end = row.group(3) == null ? start : Integer.parseInt(row.group(3));
            String range = row.group(3) == null ? code(start) : code(start) + "-" + code(end);
            String description = row.group(1).strip();
            int firstLine = index + 1;
            String sourceNote = null;
            if (line.equals("Fuel and Fuel-Related Services 000")) {
                description = lines.get(index - 1).strip();
                firstLine = index;
                sourceNote = "The PDF text layer places 'Not Used' above the 'Fuel and Fuel-Related Services' heading on the 000 row; 000 is classified as Not Used, consistent with ATL105 Appendix F-1.";
            } else if (description.startsWith("(")) {
                description = lines.get(index - 1).strip() + " " + description;
                firstLine = index;
            } else if (CATEGORY_HEADERS.contains(range)) {
                continue;
            }
            boolean footnote = description.endsWith(FOOTNOTE_MARKER);
            if (footnote) description = description.substring(0, description.length() - 1).strip();
            String category = category(start);
            if (!category.equals(category(end))) throw new IllegalStateException("Row spans categories " + range);
            String assignment = assignment(start, description);
            ObjectNode entry = rows.addObject();
            entry.put("startCode", code(start));
            entry.put("endCode", code(end));
            entry.put("sourceRange", range);
            entry.put("description", description);
            entry.put("category", category);
            entry.put("assignment", assignment);
            entry.put("deprecated", "DEPRECATED".equals(assignment));
            entry.put("addedInV41", false);
            if (footnote) entry.put("sourceFootnote", FOOTNOTE_032_033);
            if (sourceNote != null) entry.put("sourceNote", sourceNote);
            entry.set("sourceAnchor", pspcAnchor(page, "pspc-row-" + code(start), firstLine, index + 1));
            for (int value = start; value <= end; value++) {
                if (seen[value]) throw new IllegalStateException("Overlapping PSPC code " + code(value));
                seen[value] = true;
            }
        }
        for (int value = 0; value < 1000; value++) {
            if (!seen[value]) throw new IllegalStateException("Missing PSPC code " + code(value));
        }
        Set<String> added = new HashSet<>();
        oracle.path("releaseNotesV41Codes").forEach(node -> added.add(node.asText()));
        for (JsonNode row : rows) {
            if (added.contains(row.path("startCode").asText()) && row.path("startCode").equals(row.path("endCode"))) {
                ((ObjectNode) row).put("addedInV41", true);
            }
        }
        return oracle;
    }

    static String assignment(int start, String description) {
        String lower = description.toLowerCase(Locale.ROOT);
        String result = start == 0 ? "UNUSED"
                : lower.contains("<deprecated>") ? "DEPRECATED"
                : lower.contains("preauthorization") ? "RESERVED_PREAUTHORIZATION"
                : lower.contains("proprietary use") || lower.contains("proprietary future") ? "RESERVED_PROPRIETARY"
                : lower.contains("reserved for conexxus") ? "RESERVED_CONEXXUS"
                : "ASSIGNED";
        if ("ASSIGNED".equals(result) && (lower.contains("undefined") || lower.contains("reserved for"))) {
            throw new IllegalStateException("Unclassified reservation text: " + description);
        }
        if (result.startsWith("RESERVED_") && !lower.contains("undefined") && !lower.contains("reserved")) {
            throw new IllegalStateException("Reservation without reservation text: " + description);
        }
        return result;
    }

    static String category(int code) {
        if (code == 0) return "UNUSED";
        if (code <= 99) return "DISPENSED_MOTOR_FUEL";
        if (code <= 149) return "VEHICLE_PRODUCT_SERVICE";
        if (code <= 174) return "AVIATION_FUEL";
        if (code <= 224) return "AVIATION_PRODUCT_SERVICE";
        if (code <= 249) return "MARINE_FUEL";
        if (code <= 299) return "MARINE_PRODUCT_SERVICE";
        if (code <= 399) return "OTHER_DISPENSED_FUEL_METERED_PRODUCT";
        if (code >= 560 && code <= 590) return "PREPAID_BILL_PAY";
        if (code <= 599) return "MERCHANDISE";
        if (code <= 624) return "PACKAGED_FUEL";
        if (code <= 649) return "RESERVED_CONEXXUS_TABLE";
        if (code <= 699) return "VEHICLE_PRODUCT_SERVICE";
        if (code <= 719) return "CANNABINOID";
        if (code <= 799) return "RESERVED_CONEXXUS_TABLE";
        if (code <= 899) return "RESERVED_PROPRIETARY_TABLE";
        if (code <= 949) return "NEGATIVE";
        return "ADMINISTRATIVE";
    }

    private static ArrayNode releaseNotes(List<String> lines) {
        ArrayNode codes = MAPPER.createArrayNode();
        int page = 0;
        for (String raw : lines) {
            String line = raw.strip();
            var pageMatch = PAGE.matcher(line);
            if (pageMatch.matches()) page = Integer.parseInt(pageMatch.group(1));
            var note = RELEASE_NOTE.matcher(line);
            if (page == 6 && note.matches()) codes.add(note.group(1));
        }
        if (codes.size() != 10) throw new IllegalStateException("Expected ten v4.1 release-note codes, found " + codes.size());
        return codes;
    }

    private static ArrayNode atl105References() throws Exception {
        List<String> lines = Files.readAllLines(ATL105_2026_4, StandardCharsets.UTF_8).stream().map(String::strip).toList();
        ArrayNode refs = MAPPER.createArrayNode();
        int pspc = unique(lines, "Conexxus PSPC reference document.");
        if (!lines.get(pspc - 2).contains("please refer to the")) throw new IllegalStateException("PSPC pointer text moved");
        refs.add(atl105Anchor("APPX-K-034-PSPC-REFERENCE", "Appendix K-27", "112", "116,118", pspc - 1, pspc,
                "For a full list of values supported in Table ID 034 Table Data, refer to the Conexxus PSPC reference document."));
        int layout = last(lines, "Host-based Purchase Restriction");
        int layoutEnd = unique(lines, "Visa Fleet EMV 2.0 and MasterCard Enhanced Fleet EMV transactions");
        if (layoutEnd - layout > 25 || unique(lines, "ans32 Variable length of up to 32 bytes.") > layoutEnd) {
            throw new IllegalStateException("Table 034 layout moved");
        }
        refs.add(atl105Anchor("APPX-K-034-LAYOUT", "Appendix K-26", "112", "116,118", layout, layoutEnd,
                "Table ID n3 fixed 034; Table Data ans32 variable up to 32 bytes; received only when the merchant sent Purchase Restriction Method 2 or 3 and the network returned a value; applicable to Visa Fleet EMV 2.0 and MasterCard Enhanced Fleet EMV."));
        int method = unique(lines, "Identifies Purchase Restriction Method");
        int methodEnd = unique(lines, "will default the value to Visa as No");
        if (methodEnd - method > 45) throw new IllegalStateException("Sub-table 18 layout moved");
        refs.add(atl105Anchor("APPX-I-049-18-PURCHASE-RESTRICTION-METHOD", "Appendix I-46", "111", "111", method, methodEnd,
                "Table 049 Sub Table ID 18 an1: 0 Neither Chip nor Host Based; 1 Chip-Based Only; 2 Host-Based Only; 3 Both Chip and Host Based."));
        int notice = -1;
        for (int index = methodEnd; index < Math.min(lines.size(), methodEnd + 30); index++) {
            if (lines.get(index).contains("no earlier than June 2026")) {
                notice = index + 1;
                break;
            }
        }
        if (notice < 0) throw new IllegalStateException("Sub-table 18 availability notice moved");
        refs.add(atl105Anchor("APPX-I-049-18-MC-AVAILABILITY", "Appendix I-47", "111", "111", notice - 1, notice + 2,
                "MasterCard Enhanced functionality expected in First Data production no earlier than June 2026; development and certification preparation only until then."));
        int indicator = unique(lines, "value of 034, the Host-based Purchase Restriction is included");
        refs.add(atl105Anchor("ELEMENT-116-VALUE-034", "13.2 Element 118", "112", "116,118", indicator - 1, indicator + 1,
                "When Element 116 has value 034, the Host-based Purchase Restriction is included."));
        int title = last(lines, "Appendix F.  Valid Payment Systems Product Codes");
        int authority = unique(lines, "These codes are valid entries for Element No. 77, Product Code");
        refs.add(atl105Anchor("APPX-F-ELEMENT-77-AUTHORITY", "Appendix F-1", "102,157", "77", title, authority + 1,
                "Appendix F codes are the valid entries for Element 77; ATL105 does not delegate Element 77 values to the external PSPC document."));
        return refs;
    }

    private static ObjectNode appendixFVersionCheck() throws Exception {
        List<String> previous = appendixFBody(ATL105_2026_3);
        List<String> current = appendixFBody(ATL105_2026_4);
        ObjectNode check = MAPPER.createObjectNode();
        check.put("comparedVersions", "2026-3 vs 2026-4");
        check.put("normalizedBodyLines", current.size());
        check.put("identicalBody", previous.equals(current));
        check.put("consequence", "The 2026-3 Appendix F source oracle remains valid for ATL105 2026-4 Element 77 classification.");
        return check;
    }

    public static List<String> appendixFBody(Path spec) throws Exception {
        List<String> lines = Files.readAllLines(spec, StandardCharsets.UTF_8).stream().map(String::strip).toList();
        int start = last(lines, "Appendix F.  Valid Payment Systems Product Codes") - 1;
        int end = start + 1;
        while (end < lines.size() && !lines.get(end).startsWith("Appendix G.  ")) end++;
        if (end >= lines.size()) throw new IllegalStateException("Appendix G heading not found in " + spec);
        List<String> body = new ArrayList<>();
        for (String line : lines.subList(start, end)) {
            if (!line.isEmpty() && !APPENDIX_F_NOISE.matcher(line).matches()) body.add(line);
        }
        return body;
    }

    public static Map<String, ObjectNode> crosswalk(JsonNode pspc, JsonNode appendixF) {
        Map<String, JsonNode> pspcRows = expand(pspc);
        Map<String, JsonNode> appendixRows = expand(appendixF);
        Set<String> conflicts = new HashSet<>();
        appendixF.path("sourceConflict").path("codes").forEach(node -> conflicts.add(node.asText()));
        Map<String, ObjectNode> result = new TreeMap<>();
        for (int value = 0; value < 1000; value++) {
            String product = code(value);
            JsonNode p = pspcRows.get(product);
            JsonNode a = appendixRows.get(product);
            String pAssignment = p.path("assignment").asText();
            String aAssignment = a.path("assignment").asText();
            boolean pAssigned = "ASSIGNED".equals(pAssignment);
            boolean aAssigned = "ASSIGNED".equals(aAssignment);
            String relation;
            if ("UNUSED".equals(pAssignment) && "UNUSED".equals(aAssignment)) relation = "BOTH_UNUSED";
            else if ("DEPRECATED".equals(pAssignment)) relation = "PSPC_DEPRECATED";
            else if (pAssigned && aAssigned) relation = sameText(p.path("description").asText(), a.path("description").asText()) ? "DESCRIPTION_MATCH" : "DESCRIPTION_DIFFERS";
            else if (pAssigned) relation = "PSPC_ONLY_ASSIGNED";
            else if (aAssigned) relation = "RESERVED_PROPRIETARY".equals(pAssignment) ? "APPENDIX_F_PROPRIETARY_ASSIGNMENT" : "APPENDIX_F_ONLY_ASSIGNED";
            else relation = "NEITHER_ASSIGNED";
            boolean consistent = Set.of("BOTH_UNUSED", "DESCRIPTION_MATCH", "NEITHER_ASSIGNED", "APPENDIX_F_PROPRIETARY_ASSIGNMENT").contains(relation);
            boolean conflict = conflicts.contains(product);
            ObjectNode entry = MAPPER.createObjectNode();
            entry.put("code", product);
            entry.put("relation", relation);
            entry.put("element77Authority", "ATL105_APPENDIX_F");
            entry.put("outcome", consistent && !conflict ? "PASS" : "REVIEW_REQUIRED");
            entry.put("appendixFSourceConflict", conflict);
            entry.put("pspcAssignment", pAssignment);
            entry.put("pspcCategory", p.path("category").asText());
            entry.put("pspcDescription", p.path("description").asText());
            entry.put("appendixFTable", a.path("table").asText());
            entry.put("appendixFAssignment", aAssignment);
            entry.put("appendixFDescription", a.path("description").asText());
            result.put(product, entry);
        }
        return result;
    }

    private static boolean sameText(String left, String right) {
        return normalize(left).equals(normalize(right));
    }

    private static String normalize(String text) {
        return text.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    private static Map<String, JsonNode> expand(JsonNode oracle) {
        Map<String, JsonNode> indexed = new HashMap<>();
        for (JsonNode row : oracle.path("rows")) {
            for (int value = Integer.parseInt(row.path("startCode").asText()); value <= Integer.parseInt(row.path("endCode").asText()); value++) {
                if (indexed.put(code(value), row) != null) throw new IllegalStateException("Overlapping code " + code(value));
            }
        }
        if (indexed.size() != 1000) throw new IllegalStateException("Incomplete code oracle");
        return indexed;
    }

    public static Map<String, Integer> relationCounts(Map<String, ObjectNode> crosswalk) {
        Map<String, Integer> counts = new TreeMap<>();
        crosswalk.values().forEach(entry -> counts.merge(entry.path("relation").asText(), 1, Integer::sum));
        return counts;
    }

    public static String crosswalkCsv(Map<String, ObjectNode> crosswalk) {
        String[] columns = {"code", "relation", "outcome", "element77Authority", "appendixFSourceConflict", "pspcAssignment", "pspcCategory",
                "pspcDescription", "appendixFTable", "appendixFAssignment", "appendixFDescription"};
        StringBuilder csv = new StringBuilder(String.join(",", columns)).append('\n');
        for (ObjectNode entry : crosswalk.values()) {
            List<String> cells = new ArrayList<>();
            for (String column : columns) cells.add(csvCell(entry.path(column).asText()));
            csv.append(String.join(",", cells)).append('\n');
        }
        return csv.toString();
    }

    private static String csvCell(String value) {
        return value.matches(".*[,\"\\n].*") ? '"' + value.replace("\"", "\"\"") + '"' : value;
    }

    public static ObjectNode chains(JsonNode oracle, Map<String, ObjectNode> crosswalk) {
        ObjectNode pack = MAPPER.createObjectNode();
        pack.putObject("manifest").put("packageId", "ATL105-PSPC-PRODUCT-CODE-REFERENCE").put("specification", "ATL105")
                .put("specificationVersion", ATL105_VERSION).put("reference", "Conexxus PSPC").put("referenceVersion", PSPC_VERSION);
        pack.put("scope", "PSPC classification, Element 77 crosswalk and Appendix K Table 034 host purchase restriction harness; not merchant eligibility, network packing, converter or SME certification");
        pack.put("status", "PARTIALLY_COVERED");
        pack.putArray("businessRequirements");
        pack.putArray("testScenarios");
        pack.putArray("testCases");
        pack.putArray("testData");
        Map<String, JsonNode> anchors = new LinkedHashMap<>();
        oracle.path("atl105References").forEach(ref -> anchors.put(ref.path("id").asText(), ref));

        int classification = 0;
        for (JsonNode row : oracle.path("rows")) {
            String br = "BR-PSPC-ROW-" + row.path("startCode").asText();
            addRequirement(pack, br, "Preserve PSPC v" + PSPC_VERSION + " row " + row.path("sourceRange").asText() + " (" + row.path("description").asText() + ") with its category and assignment state.",
                    "SOURCE_CLASSIFICATION_ONLY", row.path("sourceAnchor"));
            for (int value = Integer.parseInt(row.path("startCode").asText()); value <= Integer.parseInt(row.path("endCode").asText()); value++) {
                String product = code(value);
                ObjectNode expected = MAPPER.createObjectNode().put("outcome", "PASS")
                        .put("category", row.path("category").asText()).put("assignment", row.path("assignment").asText())
                        .put("description", row.path("description").asText()).put("deprecated", row.path("deprecated").asBoolean());
                ObjectNode payload = MAPPER.createObjectNode().put("mode", "CLASSIFICATION").put("ProductCode", product);
                addChain(pack, br, "CLASSIFY-" + product, payload, expected, row.path("sourceAnchor"));
                classification++;
            }
        }

        JsonNode element77 = anchors.get("APPX-F-ELEMENT-77-AUTHORITY");
        addRequirement(pack, "BR-PSPC-XWALK-E77", "Element 77 values remain governed by ATL105 Appendix F; PSPC v" + PSPC_VERSION + " divergence is flagged for review and never substituted.",
                "ELEMENT_77_CROSSWALK", element77);
        int review = 0;
        for (ObjectNode entry : crosswalk.values()) {
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", entry.path("outcome").asText()).put("relation", entry.path("relation").asText());
            if ("REVIEW_REQUIRED".equals(entry.path("outcome").asText())) review++;
            ObjectNode payload = MAPPER.createObjectNode().put("mode", "ELEMENT_77_CROSSWALK").put("ProductCode", entry.path("code").asText());
            addChain(pack, "BR-PSPC-XWALK-E77", "XWALK-" + entry.path("code").asText(), payload, expected, element77);
        }

        addRequirement(pack, "BR-PSPC-FORMAT", "A PSPC product code is a string of exactly three ASCII digits.", "FORMAT_ONLY", anchors.get("APPX-F-ELEMENT-77-AUTHORITY"));
        JsonNode[] inputs = {NODES.textNode("001"), NODES.textNode("999"), NODES.textNode("000"), NODES.textNode("1"), NODES.textNode("0001"),
                NODES.textNode("01A"), NODES.textNode(" 001"), NODES.textNode(""), NODES.nullNode(), NODES.numberNode(1), NODES.textNode("\u0660\u0660\u0661")};
        for (int index = 0; index < inputs.length; index++) {
            ObjectNode payload = MAPPER.createObjectNode().put("mode", "CLASSIFICATION");
            payload.set("ProductCode", inputs[index]);
            addChain(pack, "BR-PSPC-FORMAT", "FORMAT-" + index, payload, MAPPER.createObjectNode().put("outcome", index < 3 ? "PASS" : "FAIL"), anchors.get("APPX-F-ELEMENT-77-AUTHORITY"));
        }

        int hostCases = hostRestrictionChains(pack, anchors);
        int bothUnused = (int) crosswalk.values().stream().filter(entry -> "BOTH_UNUSED".equals(entry.path("relation").asText())).count();
        ObjectNode summary = pack.putObject("coverageSummary");
        summary.put("pspcSourceRows", oracle.path("rows").size()).put("classificationTestData", classification)
                .put("crosswalkTestData", crosswalk.size()).put("crosswalkReviewRequired", review).put("crosswalkConsistent", crosswalk.size() - review)
                .put("crosswalkBothUnused", bothUnused).put("formatTestData", inputs.length).put("hostRestrictionTestData", hostCases)
                .put("transactionEligibilityCertified", false).put("smeApproved", false);
        ObjectNode relations = summary.putObject("crosswalkRelations");
        relationCounts(crosswalk).forEach(relations::put);
        return pack;
    }

    private static int hostRestrictionChains(ObjectNode pack, Map<String, JsonNode> anchors) {
        JsonNode layout = anchors.get("APPX-K-034-LAYOUT");
        JsonNode pointer = anchors.get("APPX-K-034-PSPC-REFERENCE");
        JsonNode method = anchors.get("APPX-I-049-18-PURCHASE-RESTRICTION-METHOD");
        addRequirement(pack, "BR-PSPC-T034-STRUCTURE", "Table ID is 034 and Table Data is printable ans of 1 to 32 bytes.", "HOST_PURCHASE_RESTRICTION", layout);
        addRequirement(pack, "BR-PSPC-T034-OPT-IN", "Table 034 is received only when the request carried Table 049 Sub Table 18 Purchase Restriction Method 2 or 3.", "HOST_PURCHASE_RESTRICTION", layout, method);
        addRequirement(pack, "BR-PSPC-T034-PROGRAM", "Table 034 applies only to Visa Fleet EMV 2.0 and MasterCard Enhanced Fleet EMV; Visa applicability stays REVIEW_REQUIRED under SEG111-SME-003.", "HOST_PURCHASE_RESTRICTION", layout, method);
        addRequirement(pack, "BR-PSPC-T034-VALUES", "Product codes carried by Table 034 are Conexxus PSPC codes: assigned PASS, unused FAIL, deprecated or reserved REVIEW_REQUIRED.", "HOST_PURCHASE_RESTRICTION", pointer);
        addRequirement(pack, "BR-PSPC-T034-PACKING", "ATL105 does not define how PSPC codes are packed in the ans32 Table Data; value semantics without explicit codes stay REVIEW_REQUIRED.", "HOST_PURCHASE_RESTRICTION", layout, pointer);
        String mc = "MASTERCARD_ENHANCED_FLEET_EMV";
        String visa = "VISA_FLEET_EMV_2_0";
        Object[][] cases = {
                {"T034-MC-HOST-ONLY", "BR-PSPC-T034-VALUES", "034", "001002", "2", mc, new String[]{"001", "002"}, "PASS"},
                {"T034-MC-CHIP-AND-HOST", "BR-PSPC-T034-VALUES", "034", "062", "3", mc, new String[]{"062"}, "PASS"},
                {"T034-MAX-32", "BR-PSPC-T034-STRUCTURE", "034", "001".repeat(10) + "00", "2", mc, new String[]{"001"}, "PASS"},
                {"T034-V41-ADDED-338", "BR-PSPC-T034-VALUES", "034", "338", "2", mc, new String[]{"338"}, "PASS"},
                {"T034-CBD-700", "BR-PSPC-T034-VALUES", "034", "700", "2", mc, new String[]{"700"}, "PASS"},
                {"T034-VISA-FLEET", "BR-PSPC-T034-PROGRAM", "034", "001", "2", visa, new String[]{"001"}, "REVIEW_REQUIRED"},
                {"T034-PACKING-UNSPECIFIED", "BR-PSPC-T034-PACKING", "034", "001002", "2", mc, null, "REVIEW_REQUIRED"},
                {"T034-DEPRECATED-011", "BR-PSPC-T034-VALUES", "034", "011", "2", mc, new String[]{"011"}, "REVIEW_REQUIRED"},
                {"T034-RESERVED-CONEXXUS-006", "BR-PSPC-T034-VALUES", "034", "006", "2", mc, new String[]{"006"}, "REVIEW_REQUIRED"},
                {"T034-RESERVED-PREAUTH-071", "BR-PSPC-T034-VALUES", "034", "071", "2", mc, new String[]{"071"}, "REVIEW_REQUIRED"},
                {"T034-RESERVED-PROPRIETARY-850", "BR-PSPC-T034-VALUES", "034", "850", "2", mc, new String[]{"850"}, "REVIEW_REQUIRED"},
                {"T034-UNUSED-000", "BR-PSPC-T034-VALUES", "034", "000", "2", mc, new String[]{"000"}, "FAIL"},
                {"T034-BAD-CODE-FORMAT", "BR-PSPC-T034-VALUES", "034", "1", "2", mc, new String[]{"1"}, "FAIL"},
                {"T034-PRM-0", "BR-PSPC-T034-OPT-IN", "034", "001", "0", mc, new String[]{"001"}, "FAIL"},
                {"T034-PRM-1", "BR-PSPC-T034-OPT-IN", "034", "001", "1", mc, new String[]{"001"}, "FAIL"},
                {"T034-PRM-ABSENT", "BR-PSPC-T034-OPT-IN", "034", "001", null, mc, new String[]{"001"}, "FAIL"},
                {"T034-NON-FLEET-PROGRAM", "BR-PSPC-T034-PROGRAM", "034", "001", "2", "AMEX_CREDIT", new String[]{"001"}, "FAIL"},
                {"T034-WRONG-TABLE-ID", "BR-PSPC-T034-STRUCTURE", "035", "001", "2", mc, new String[]{"001"}, "FAIL"},
                {"T034-OVER-32", "BR-PSPC-T034-STRUCTURE", "034", "001".repeat(11), "2", mc, new String[]{"001"}, "FAIL"},
                {"T034-EMPTY", "BR-PSPC-T034-STRUCTURE", "034", "", "2", mc, new String[]{"001"}, "FAIL"},
                {"T034-NON-PRINTABLE", "BR-PSPC-T034-STRUCTURE", "034", "001\t002", "2", mc, new String[]{"001"}, "FAIL"}
        };
        for (Object[] example : cases) {
            ObjectNode payload = MAPPER.createObjectNode().put("mode", "HOST_PURCHASE_RESTRICTION").put("tableId", (String) example[2]).put("tableData", (String) example[3]);
            if (example[4] != null) payload.put("purchaseRestrictionMethod", (String) example[4]);
            payload.put("fleetProgram", (String) example[5]);
            if (example[6] != null) {
                ArrayNode codes = payload.putArray("productCodes");
                for (String value : (String[]) example[6]) codes.add(value);
            }
            addChain(pack, (String) example[1], (String) example[0], payload, MAPPER.createObjectNode().put("outcome", (String) example[7]), layout);
        }
        return cases.length;
    }

    private static void addRequirement(ObjectNode pack, String id, String title, String scope, JsonNode... anchors) {
        ObjectNode br = ((ArrayNode) pack.path("businessRequirements")).addObject();
        br.put("id", id).put("title", title).put("status", "REVIEW_REQUIRED").put("assertionScope", scope).put("smeApproved", false);
        ArrayNode sources = br.putArray("sourceAnchors");
        for (JsonNode anchor : anchors) sources.add(anchor.deepCopy());
    }

    private static void addChain(ObjectNode pack, String br, String suffix, ObjectNode payload, ObjectNode expected, JsonNode anchor) {
        String tsId = "TS-PSPC-" + suffix;
        String tcId = "TC-PSPC-" + suffix;
        String tdId = "TD-PSPC-" + suffix;
        ObjectNode ts = ((ArrayNode) pack.path("testScenarios")).addObject();
        ts.put("id", tsId).put("status", "REVIEW_REQUIRED");
        ts.putArray("requirementIds").add(br);
        ObjectNode tc = ((ArrayNode) pack.path("testCases")).addObject();
        tc.put("id", tcId).put("expectedOutcome", expected.path("outcome").asText()).put("status", "REVIEW_REQUIRED");
        tc.putArray("scenarioIds").add(tsId);
        tc.putArray("testDataIds").add(tdId);
        ObjectNode td = ((ArrayNode) pack.path("testData")).addObject();
        td.put("id", tdId).put("coversBr", br).put("producer", "TEST_SOLUTION").put("readiness", "EXECUTABLE")
                .put("fixtureKind", "PSPC_PRODUCT_CODE_HARNESS").put("converterReady", false).put("expectedValidation", expected.path("outcome").asText());
        td.putArray("testCaseIds").add(tcId);
        td.set("payload", payload);
        td.set("expected", expected);
        for (ObjectNode artifact : new ObjectNode[]{ts, tc, td}) artifact.putArray("sourceAnchors").add(anchor.deepCopy());
    }

    private static ObjectNode pspcAnchor(int page, String rule, int start, int end) {
        return MAPPER.createObjectNode().put("document", "Conexxus PSPC").put("version", PSPC_VERSION).put("page", page)
                .put("rule", rule).put("startLine", start).put("endLine", end);
    }

    private static ObjectNode atl105Anchor(String id, String section, String segment, String element, int start, int end, String summary) {
        return MAPPER.createObjectNode().put("id", id).put("specification", "ATL105").put("version", ATL105_VERSION).put("section", section)
                .put("segment", segment).put("element", element).put("startLine", start).put("endLine", end).put("summary", summary);
    }

    private static int unique(List<String> lines, String literal) {
        int found = -1;
        for (int index = 0; index < lines.size(); index++) {
            if (lines.get(index).contains(literal)) {
                if (found >= 0) throw new IllegalStateException("Literal is not unique: " + literal);
                found = index + 1;
            }
        }
        if (found < 0) throw new IllegalStateException("Literal not found: " + literal);
        return found;
    }

    private static int last(List<String> lines, String exact) {
        for (int index = lines.size() - 1; index >= 0; index--) {
            if (lines.get(index).strip().equals(exact)) return index + 1;
        }
        throw new IllegalStateException("Line not found: " + exact);
    }

    private static String path(Path value) {
        return value.toString().replace('\\', '/');
    }

    static String code(int value) {
        return String.format(Locale.ROOT, "%03d", value);
    }
}
