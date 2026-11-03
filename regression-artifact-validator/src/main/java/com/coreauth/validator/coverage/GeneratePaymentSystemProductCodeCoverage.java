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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ingests the Conexxus Payment System Product Codes (PSPC) reference that ATL105 2026-4 Appendix K-27
 * (Table ID 034) points to, and cross-references it with ATL105 Appendix F (Element 77). ATL105 does not
 * name a PSPC version, so every supplied version is ingested and compared.
 */
public final class GeneratePaymentSystemProductCodeCoverage {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonNodeFactory NODES = JsonNodeFactory.instance;
    public static final String ATL105_VERSION = "2026-4";
    public static final Path ATL105_2026_4 = Path.of("specifications/ATL105/docs/specs/extracted_text_new.txt");
    public static final Path ATL105_2026_3 = Path.of("specifications/ATL105/docs/specs/extracted_text.txt");
    public static final Path APPENDIX_F_ORACLE = Path.of("src/main/resources/atl105/appendix-f-product-code-oracle.json");

    public record Source(String version, String date, Path text, Path pdf, Path oracle, Path chains, Path crosswalk, String idPrefix) {
    }

    public static final Source V41 = new Source("4.1", "2022-05-12",
            Path.of("specifications/ATL105/docs/specs/extracted_text_pspc_v4.1.txt"),
            Path.of("specifications/ATL105/docs/specs/Conexxus_Payment_System_Product_Codes_Version_4.1_2022-05-12.pdf"),
            Path.of("src/main/resources/atl105/pspc-product-code-oracle.json"),
            Path.of("specifications/ATL105/test-output/test-json/pspc-product-code-chain-package.json"),
            Path.of("specifications/ATL105/test-output/test-json/pspc-appendix-f-crosswalk.csv"),
            "PSPC");
    public static final Source V37 = new Source("3.7", "2015-09-04",
            Path.of("specifications/ATL105/docs/specs/extracted_text_pspc_v3.7.txt"),
            Path.of("specifications/ATL105/docs/specs/Conexxus_Payment_System_Product_Codes_Version_3.7_2015-09-04.pdf"),
            Path.of("src/main/resources/atl105/pspc-product-code-oracle-v3.7.json"),
            Path.of("specifications/ATL105/test-output/test-json/pspc-v3.7-product-code-chain-package.json"),
            Path.of("specifications/ATL105/test-output/test-json/pspc-v3.7-appendix-f-crosswalk.csv"),
            "PSPC37");
    public static final List<Source> SOURCES = List.of(V37, V41);
    public static final Path VERSION_DIFF = Path.of("specifications/ATL105/test-output/test-json/pspc-version-diff-v3.7-v4.1.csv");

    /** Latest supplied version; retained names used by existing callers. */
    public static final String PSPC_VERSION = V41.version();
    public static final Path SOURCE = V41.text();
    public static final Path SOURCE_PDF = V41.pdf();
    public static final Path ORACLE = V41.oracle();
    public static final Path CHAINS = V41.chains();
    public static final Path CROSSWALK = V41.crosswalk();

    private static final Pattern PAGE = Pattern.compile("^--- PAGE (\\d+) ---$");
    private static final Pattern ROW = Pattern.compile("^(.*?\\S)\\s+(\\d{3})(?:\\s*-\\s*(\\d{3}))?$");
    private static final Pattern LONE_CODE = Pattern.compile("^(\\d{3})(?:\\s*-\\s*(\\d{3}))?$");
    private static final Pattern NOTE_CODE = Pattern.compile("(\\d{3})(?:-(\\d{3}))?(?=[:)])");
    private static final Pattern APPENDIX_F_NOISE = Pattern.compile("^(--- PAGE.*|BUYPASS.*|Software Version.*|Specification Release.*|\\w+ \\d\\d, 20\\d\\d|Appendix F-\\d+)$");
    private static final Set<String> CATEGORY_HEADERS = Set.of("001-099", "100-149", "150-174", "175-224", "225-249", "250-299",
            "300-399", "400-599", "560-590", "600-624", "650-699", "700-719", "900-949", "950-999");
    private static final Set<String> STABLE_RELATIONS = Set.of("UNCHANGED", "RENAMED");
    private static final String FOOTNOTE_MARKER = "\u2217";
    private static final String FOOTNOTE_032_033 = "Code 032 and 033 should only be used by existing implementations that limit fuel to codes 001-099. "
            + "The preferred codes are 311-320, which provide more granularity. 032 and 033 are likely to be deprecated in the future.";

    private GeneratePaymentSystemProductCodeCoverage() {
    }

    public static void main(String[] args) throws Exception {
        JsonNode appendixF = MAPPER.readTree(APPENDIX_F_ORACLE.toFile());
        Map<String, ObjectNode> oracles = new LinkedHashMap<>();
        for (Source source : SOURCES) {
            ObjectNode oracle = sourceOracle(source);
            oracles.put(source.version(), oracle);
            Files.createDirectories(source.oracle().getParent());
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(source.oracle().toFile(), oracle);
            Map<String, ObjectNode> crosswalk = crosswalk(oracle, appendixF);
            Files.writeString(source.crosswalk(), crosswalkCsv(crosswalk), StandardCharsets.UTF_8);
            System.out.printf("PSPC v%s rows=%d codes=1000 crosswalk=%s%n", source.version(), oracle.path("rows").size(), relationCounts(crosswalk));
        }
        Map<String, ObjectNode> diff = versionDiff(oracles.get(V37.version()), oracles.get(V41.version()));
        Files.writeString(VERSION_DIFF, versionDiffCsv(diff), StandardCharsets.UTF_8);
        System.out.printf("Version diff v3.7->v4.1=%s%n", diffCounts(diff));
        Files.createDirectories(CHAINS.getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(V41.chains().toFile(), chains(oracles.get(V41.version()), crosswalk(oracles.get(V41.version()), appendixF)));
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(V37.chains().toFile(),
                legacyChains(oracles.get(V37.version()), crosswalk(oracles.get(V37.version()), appendixF), diff));
    }

    public static Source source(Path text) {
        for (Source source : SOURCES) {
            if (source.text().equals(text)) return source;
        }
        throw new IllegalArgumentException("Unknown PSPC source " + text);
    }

    public static ObjectNode sourceOracle(Path text) throws Exception {
        return sourceOracle(source(text));
    }

    public static ObjectNode sourceOracle(Source source) throws Exception {
        List<String> lines = Files.readAllLines(source.text(), StandardCharsets.UTF_8);
        ObjectNode oracle = MAPPER.createObjectNode();
        oracle.put("reference", "Conexxus Payment System Product Codes (PSPC)");
        oracle.put("publisher", "Conexxus, Inc. (Registration Authority for ASC X9.104 Part 2 product codes)");
        oracle.put("version", source.version());
        oracle.put("versionDate", source.date());
        oracle.put("scope", "SOURCE_CLASSIFICATION_ONLY_NOT_TRANSACTION_ACCEPTANCE");
        oracle.put("source", path(source.text()));
        oracle.put("sourcePdf", path(source.pdf()));
        oracle.put("versionBinding", "ATL105 2026-4 Appendix K-27 names the Conexxus PSPC document without a version. Versions 3.7 and 4.1 were supplied to the Test Team; "
                + "codes whose meaning differs between them stay REVIEW_REQUIRED unless a version is bound (register item SEG111-SME-012).");
        ArrayNode suppliedVersions = oracle.putArray("suppliedVersions");
        SOURCES.forEach(item -> suppliedVersions.add(item.version()));
        oracle.set("atl105References", atl105References());
        oracle.set("appendixFVersionCheck", appendixFVersionCheck());
        oracle.set("releaseNoteAddedCodes", releaseNotes(lines, source.version()));
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
            Matcher row = ROW.matcher(line);
            Matcher lone = LONE_CODE.matcher(line);
            String startText;
            String endText;
            String description;
            int firstLine = index + 1;
            String sourceNote = null;
            if (lone.matches()) {
                int previous = index - 1;
                while (previous >= 0 && lines.get(previous).isBlank()) previous--;
                startText = lone.group(1);
                endText = lone.group(2);
                description = lines.get(previous).strip();
                firstLine = previous + 1;
                sourceNote = "The PDF text layer wraps this row: the description and the code are on separate lines.";
            } else if (row.matches()) {
                startText = row.group(2);
                endText = row.group(3);
                description = row.group(1).strip();
            } else {
                continue;
            }
            int start = Integer.parseInt(startText);
            int end = endText == null ? start : Integer.parseInt(endText);
            String range = endText == null ? code(start) : code(start) + "-" + code(end);
            if (line.equals("Fuel and Fuel-Related Services 000")) {
                description = lines.get(index - 1).strip();
                firstLine = index;
                sourceNote = "The PDF text layer places 'Not Used' above the 'Fuel and Fuel-Related Services' heading on the 000 row; 000 is classified as Not Used, consistent with ATL105 Appendix F-1.";
            } else if (description.startsWith("(")) {
                description = lines.get(index - 1).strip() + " " + description;
                firstLine = index;
            } else if (!lone.matches() && CATEGORY_HEADERS.contains(range)) {
                continue;
            }
            boolean footnote = description.endsWith(FOOTNOTE_MARKER);
            if (footnote) description = description.substring(0, description.length() - 1).strip();
            String category = category(start, source.version());
            if (!category.equals(category(end, source.version()))) throw new IllegalStateException("Row spans categories " + range);
            String assignment = assignment(start, description);
            ObjectNode entry = rows.addObject();
            entry.put("startCode", code(start));
            entry.put("endCode", code(end));
            entry.put("sourceRange", range);
            entry.put("description", description);
            entry.put("category", category);
            entry.put("assignment", assignment);
            entry.put("deprecated", "DEPRECATED".equals(assignment));
            entry.put("addedInRelease", false);
            if (footnote) entry.put("sourceFootnote", FOOTNOTE_032_033);
            if (sourceNote != null) entry.put("sourceNote", sourceNote);
            entry.set("sourceAnchor", pspcAnchor(source.version(), page, "pspc-row-" + code(start), firstLine, index + 1));
            for (int value = start; value <= end; value++) {
                if (seen[value]) throw new IllegalStateException("Overlapping PSPC v" + source.version() + " code " + code(value));
                seen[value] = true;
            }
        }
        for (int value = 0; value < 1000; value++) {
            if (!seen[value]) throw new IllegalStateException("Missing PSPC v" + source.version() + " code " + code(value));
        }
        Set<String> added = new HashSet<>();
        oracle.path("releaseNoteAddedCodes").forEach(node -> added.add(node.asText()));
        for (JsonNode row : rows) {
            if (added.contains(row.path("startCode").asText()) && row.path("startCode").equals(row.path("endCode"))) {
                ((ObjectNode) row).put("addedInRelease", true);
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

    static String category(int code, String version) {
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
        // The Cannabinoids table (700-719) first appears in version 4.0; earlier versions reserve 700-799.
        if (code <= 719) return "3.7".equals(version) ? "RESERVED_CONEXXUS_TABLE" : "CANNABINOID";
        if (code <= 799) return "RESERVED_CONEXXUS_TABLE";
        if (code <= 899) return "RESERVED_PROPRIETARY_TABLE";
        if (code <= 949) return "NEGATIVE";
        return "ADMINISTRATIVE";
    }

    private static ArrayNode releaseNotes(List<String> lines, String version) {
        StringBuilder block = new StringBuilder();
        boolean inBlock = false;
        for (String raw : lines) {
            String line = raw.strip();
            if (line.startsWith("Release Notes for Version " + version)) inBlock = true;
            if (line.equals("DESCRIPTION CODE")) break;
            if (inBlock && !PAGE.matcher(line).matches()) block.append(' ').append(line);
        }
        if (block.isEmpty()) throw new IllegalStateException("Release notes for version " + version + " not found");
        Set<String> codes = new LinkedHashSet<>();
        Matcher matcher = NOTE_CODE.matcher(block);
        while (matcher.find()) {
            int start = Integer.parseInt(matcher.group(1));
            int end = matcher.group(2) == null ? start : Integer.parseInt(matcher.group(2));
            for (int value = start; value <= end; value++) codes.add(code(value));
        }
        int expected = "4.1".equals(version) ? 10 : "3.7".equals(version) ? 8 : -1;
        if (codes.size() != expected) throw new IllegalStateException("Unexpected v" + version + " release-note codes " + codes);
        ArrayNode result = MAPPER.createArrayNode();
        codes.forEach(result::add);
        return result;
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

    /** Classifies how each code changed between an older and a newer PSPC version. */
    public static Map<String, ObjectNode> versionDiff(JsonNode older, JsonNode newer) {
        Map<String, JsonNode> before = expand(older);
        Map<String, JsonNode> after = expand(newer);
        Map<String, ObjectNode> result = new TreeMap<>();
        for (int value = 0; value < 1000; value++) {
            String product = code(value);
            JsonNode o = before.get(product);
            JsonNode n = after.get(product);
            String oa = o.path("assignment").asText();
            String na = n.path("assignment").asText();
            boolean sameDescription = sameText(o.path("description").asText(), n.path("description").asText());
            String relation;
            if (oa.equals(na) && (sameDescription || oa.startsWith("RESERVED_"))) relation = "UNCHANGED";
            else if ("ASSIGNED".equals(oa) && "ASSIGNED".equals(na)) relation = "RENAMED";
            else if ("DEPRECATED".equals(oa) && "ASSIGNED".equals(na)) relation = "REUSED_AFTER_DEPRECATION";
            else if (oa.startsWith("RESERVED_") && "ASSIGNED".equals(na)) relation = "NEWLY_ASSIGNED";
            else if ("ASSIGNED".equals(oa) && "DEPRECATED".equals(na)) relation = "NEWLY_DEPRECATED";
            else if ("DEPRECATED".equals(oa) && na.startsWith("RESERVED_")) relation = "DEPRECATED_TO_RESERVED";
            else relation = "STATE_CHANGED";
            boolean sensitive = !STABLE_RELATIONS.contains(relation);
            ObjectNode entry = MAPPER.createObjectNode();
            entry.put("code", product);
            entry.put("relation", relation);
            entry.put("versionSensitive", sensitive);
            entry.put("outcome", sensitive ? "REVIEW_REQUIRED" : "PASS");
            entry.put("olderVersion", older.path("version").asText());
            entry.put("olderAssignment", oa);
            entry.put("olderDescription", o.path("description").asText());
            entry.put("newerVersion", newer.path("version").asText());
            entry.put("newerAssignment", na);
            entry.put("newerDescription", n.path("description").asText());
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

    public static Map<String, Integer> diffCounts(Map<String, ObjectNode> diff) {
        return relationCounts(diff);
    }

    public static String crosswalkCsv(Map<String, ObjectNode> crosswalk) {
        return csv(crosswalk, "code", "relation", "outcome", "element77Authority", "appendixFSourceConflict", "pspcAssignment", "pspcCategory",
                "pspcDescription", "appendixFTable", "appendixFAssignment", "appendixFDescription");
    }

    public static String versionDiffCsv(Map<String, ObjectNode> diff) {
        return csv(diff, "code", "relation", "versionSensitive", "outcome", "olderVersion", "olderAssignment", "olderDescription",
                "newerVersion", "newerAssignment", "newerDescription");
    }

    private static String csv(Map<String, ObjectNode> entries, String... columns) {
        StringBuilder csv = new StringBuilder(String.join(",", columns)).append('\n');
        for (ObjectNode entry : entries.values()) {
            List<String> cells = new ArrayList<>();
            for (String column : columns) cells.add(csvCell(entry.path(column).asText()));
            csv.append(String.join(",", cells)).append('\n');
        }
        return csv.toString();
    }

    private static String csvCell(String value) {
        return value.matches(".*[,\"\\n].*") ? '"' + value.replace("\"", "\"\"") + '"' : value;
    }

    /** Latest-version package: classification, Element 77 crosswalk, format and Table 034 chains. */
    public static ObjectNode chains(JsonNode oracle, Map<String, ObjectNode> crosswalk) {
        ObjectNode pack = newPackage("ATL105-PSPC-PRODUCT-CODE-REFERENCE", oracle.path("version").asText(),
                "PSPC classification, Element 77 crosswalk and Appendix K Table 034 host purchase restriction harness; not merchant eligibility, network packing, converter or SME certification");
        Map<String, JsonNode> anchors = anchors(oracle);
        int classification = classificationChains(pack, oracle, V41.idPrefix(), false);
        int review = crosswalkChains(pack, crosswalk, V41.idPrefix(), anchors.get("APPX-F-ELEMENT-77-AUTHORITY"), null);

        addRequirement(pack, "BR-PSPC-FORMAT", "A PSPC product code is a string of exactly three ASCII digits.", "FORMAT_ONLY", anchors.get("APPX-F-ELEMENT-77-AUTHORITY"));
        JsonNode[] inputs = {NODES.textNode("001"), NODES.textNode("999"), NODES.textNode("000"), NODES.textNode("1"), NODES.textNode("0001"),
                NODES.textNode("01A"), NODES.textNode(" 001"), NODES.textNode(""), NODES.nullNode(), NODES.numberNode(1), NODES.textNode("\u0660\u0660\u0661")};
        for (int index = 0; index < inputs.length; index++) {
            ObjectNode payload = MAPPER.createObjectNode().put("mode", "CLASSIFICATION");
            payload.set("ProductCode", inputs[index]);
            addChain(pack, V41.idPrefix(), "BR-PSPC-FORMAT", "FORMAT-" + index, payload, MAPPER.createObjectNode().put("outcome", index < 3 ? "PASS" : "FAIL"), anchors.get("APPX-F-ELEMENT-77-AUTHORITY"));
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

    /** Earlier-version package: classification, Element 77 crosswalk and the version-to-version diff. */
    public static ObjectNode legacyChains(JsonNode oracle, Map<String, ObjectNode> crosswalk, Map<String, ObjectNode> diff) {
        String version = oracle.path("version").asText();
        ObjectNode pack = newPackage("ATL105-PSPC-V" + version + "-PRODUCT-CODE-REFERENCE", version,
                "PSPC v" + version + " classification, Element 77 crosswalk and v" + version + " to v" + V41.version() + " version diff; not merchant eligibility, network packing, converter or SME certification");
        Map<String, JsonNode> anchors = anchors(oracle);
        int classification = classificationChains(pack, oracle, V37.idPrefix(), true);
        int review = crosswalkChains(pack, crosswalk, V37.idPrefix(), anchors.get("APPX-F-ELEMENT-77-AUTHORITY"), version);
        JsonNode pointer = anchors.get("APPX-K-034-PSPC-REFERENCE");
        addRequirement(pack, "BR-PSPC-VERSION-DIFF", "Every code whose assignment state or meaning changed between PSPC v" + version + " and v" + V41.version()
                + " is version-sensitive and stays REVIEW_REQUIRED unless the PSPC version is bound.", "VERSION_DIFF", pointer);
        int sensitive = 0;
        for (ObjectNode entry : diff.values()) {
            if (entry.path("versionSensitive").asBoolean()) sensitive++;
            ObjectNode payload = MAPPER.createObjectNode().put("mode", "VERSION_DIFF").put("ProductCode", entry.path("code").asText());
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", entry.path("outcome").asText())
                    .put("relation", entry.path("relation").asText()).put("versionSensitive", entry.path("versionSensitive").asBoolean());
            addChain(pack, V37.idPrefix(), "BR-PSPC-VERSION-DIFF", "DIFF-" + entry.path("code").asText(), payload, expected, pointer);
        }
        ObjectNode summary = pack.putObject("coverageSummary");
        summary.put("pspcSourceRows", oracle.path("rows").size()).put("classificationTestData", classification)
                .put("crosswalkTestData", crosswalk.size()).put("crosswalkReviewRequired", review).put("crosswalkConsistent", crosswalk.size() - review)
                .put("versionDiffTestData", diff.size()).put("versionSensitiveCodes", sensitive).put("versionStableCodes", diff.size() - sensitive)
                .put("transactionEligibilityCertified", false).put("smeApproved", false);
        ObjectNode relations = summary.putObject("crosswalkRelations");
        relationCounts(crosswalk).forEach(relations::put);
        ObjectNode diffRelations = summary.putObject("versionDiffRelations");
        diffCounts(diff).forEach(diffRelations::put);
        return pack;
    }

    private static ObjectNode newPackage(String id, String version, String scope) {
        ObjectNode pack = MAPPER.createObjectNode();
        pack.putObject("manifest").put("packageId", id).put("specification", "ATL105")
                .put("specificationVersion", ATL105_VERSION).put("reference", "Conexxus PSPC").put("referenceVersion", version);
        pack.put("scope", scope);
        pack.put("status", "PARTIALLY_COVERED");
        pack.putArray("businessRequirements");
        pack.putArray("testScenarios");
        pack.putArray("testCases");
        pack.putArray("testData");
        return pack;
    }

    private static Map<String, JsonNode> anchors(JsonNode oracle) {
        Map<String, JsonNode> anchors = new LinkedHashMap<>();
        oracle.path("atl105References").forEach(ref -> anchors.put(ref.path("id").asText(), ref));
        return anchors;
    }

    private static int classificationChains(ObjectNode pack, JsonNode oracle, String prefix, boolean explicitVersion) {
        String version = oracle.path("version").asText();
        int count = 0;
        for (JsonNode row : oracle.path("rows")) {
            String br = "BR-" + prefix + "-ROW-" + row.path("startCode").asText();
            addRequirement(pack, br, "Preserve PSPC v" + version + " row " + row.path("sourceRange").asText() + " (" + row.path("description").asText() + ") with its category and assignment state.",
                    "SOURCE_CLASSIFICATION_ONLY", row.path("sourceAnchor"));
            for (int value = Integer.parseInt(row.path("startCode").asText()); value <= Integer.parseInt(row.path("endCode").asText()); value++) {
                String product = code(value);
                ObjectNode expected = MAPPER.createObjectNode().put("outcome", "PASS")
                        .put("category", row.path("category").asText()).put("assignment", row.path("assignment").asText())
                        .put("description", row.path("description").asText()).put("deprecated", row.path("deprecated").asBoolean());
                ObjectNode payload = MAPPER.createObjectNode().put("mode", "CLASSIFICATION").put("ProductCode", product);
                if (explicitVersion) payload.put("referenceVersion", version);
                addChain(pack, prefix, br, "CLASSIFY-" + product, payload, expected, row.path("sourceAnchor"));
                count++;
            }
        }
        return count;
    }

    private static int crosswalkChains(ObjectNode pack, Map<String, ObjectNode> crosswalk, String prefix, JsonNode anchor, String version) {
        String br = "BR-" + prefix + "-XWALK-E77";
        addRequirement(pack, br, "Element 77 values remain governed by ATL105 Appendix F; PSPC v" + (version == null ? V41.version() : version)
                + " divergence is flagged for review and never substituted.", "ELEMENT_77_CROSSWALK", anchor);
        int review = 0;
        for (ObjectNode entry : crosswalk.values()) {
            ObjectNode expected = MAPPER.createObjectNode().put("outcome", entry.path("outcome").asText()).put("relation", entry.path("relation").asText());
            if ("REVIEW_REQUIRED".equals(entry.path("outcome").asText())) review++;
            ObjectNode payload = MAPPER.createObjectNode().put("mode", "ELEMENT_77_CROSSWALK").put("ProductCode", entry.path("code").asText());
            if (version != null) payload.put("referenceVersion", version);
            addChain(pack, prefix, br, "XWALK-" + entry.path("code").asText(), payload, expected, anchor);
        }
        return review;
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
        addRequirement(pack, "BR-PSPC-T034-VERSION", "ATL105 does not name a PSPC version. Without a bound version a code is judged against every supplied version (3.7 and 4.1); "
                + "if their verdicts differ the result is REVIEW_REQUIRED. A stated version is judged against that version only.", "HOST_PURCHASE_RESTRICTION", pointer);
        String mc = "MASTERCARD_ENHANCED_FLEET_EMV";
        String visa = "VISA_FLEET_EMV_2_0";
        Object[][] cases = {
                {"T034-MC-HOST-ONLY", "BR-PSPC-T034-VALUES", "034", "001002", "2", mc, new String[]{"001", "002"}, null, "PASS"},
                {"T034-MC-CHIP-AND-HOST", "BR-PSPC-T034-VALUES", "034", "062", "3", mc, new String[]{"062"}, null, "PASS"},
                {"T034-MAX-32", "BR-PSPC-T034-STRUCTURE", "034", "001".repeat(10) + "00", "2", mc, new String[]{"001"}, null, "PASS"},
                {"T034-V41-ADDED-338", "BR-PSPC-T034-VERSION", "034", "338", "2", mc, new String[]{"338"}, "4.1", "PASS"},
                {"T034-V37-338", "BR-PSPC-T034-VERSION", "034", "338", "2", mc, new String[]{"338"}, "3.7", "REVIEW_REQUIRED"},
                {"T034-UNBOUND-338", "BR-PSPC-T034-VERSION", "034", "338", "2", mc, new String[]{"338"}, null, "REVIEW_REQUIRED"},
                {"T034-CBD-700", "BR-PSPC-T034-VERSION", "034", "700", "2", mc, new String[]{"700"}, "4.1", "PASS"},
                {"T034-UNBOUND-700", "BR-PSPC-T034-VERSION", "034", "700", "2", mc, new String[]{"700"}, null, "REVIEW_REQUIRED"},
                {"T034-V41-052", "BR-PSPC-T034-VERSION", "034", "052", "2", mc, new String[]{"052"}, "4.1", "PASS"},
                {"T034-V37-052", "BR-PSPC-T034-VERSION", "034", "052", "2", mc, new String[]{"052"}, "3.7", "REVIEW_REQUIRED"},
                {"T034-UNBOUND-052", "BR-PSPC-T034-VERSION", "034", "052", "2", mc, new String[]{"052"}, null, "REVIEW_REQUIRED"},
                {"T034-V37-325", "BR-PSPC-T034-VERSION", "034", "325", "2", mc, new String[]{"325"}, "3.7", "PASS"},
                {"T034-UNBOUND-325", "BR-PSPC-T034-VERSION", "034", "325", "2", mc, new String[]{"325"}, null, "PASS"},
                {"T034-V37-041", "BR-PSPC-T034-VERSION", "034", "041", "2", mc, new String[]{"041"}, "3.7", "PASS"},
                {"T034-V41-041", "BR-PSPC-T034-VERSION", "034", "041", "2", mc, new String[]{"041"}, "4.1", "REVIEW_REQUIRED"},
                {"T034-UNSUPPORTED-VERSION", "BR-PSPC-T034-VERSION", "034", "001", "2", mc, new String[]{"001"}, "9.9", "FAIL"},
                {"T034-VISA-FLEET", "BR-PSPC-T034-PROGRAM", "034", "001", "2", visa, new String[]{"001"}, null, "REVIEW_REQUIRED"},
                {"T034-PACKING-UNSPECIFIED", "BR-PSPC-T034-PACKING", "034", "001002", "2", mc, null, null, "REVIEW_REQUIRED"},
                {"T034-DEPRECATED-011", "BR-PSPC-T034-VALUES", "034", "011", "2", mc, new String[]{"011"}, "4.1", "REVIEW_REQUIRED"},
                {"T034-RESERVED-CONEXXUS-006", "BR-PSPC-T034-VALUES", "034", "006", "2", mc, new String[]{"006"}, null, "REVIEW_REQUIRED"},
                {"T034-RESERVED-PREAUTH-071", "BR-PSPC-T034-VALUES", "034", "071", "2", mc, new String[]{"071"}, null, "REVIEW_REQUIRED"},
                {"T034-RESERVED-PROPRIETARY-850", "BR-PSPC-T034-VALUES", "034", "850", "2", mc, new String[]{"850"}, null, "REVIEW_REQUIRED"},
                {"T034-UNUSED-000", "BR-PSPC-T034-VALUES", "034", "000", "2", mc, new String[]{"000"}, null, "FAIL"},
                {"T034-BAD-CODE-FORMAT", "BR-PSPC-T034-VALUES", "034", "1", "2", mc, new String[]{"1"}, null, "FAIL"},
                {"T034-PRM-0", "BR-PSPC-T034-OPT-IN", "034", "001", "0", mc, new String[]{"001"}, null, "FAIL"},
                {"T034-PRM-1", "BR-PSPC-T034-OPT-IN", "034", "001", "1", mc, new String[]{"001"}, null, "FAIL"},
                {"T034-PRM-ABSENT", "BR-PSPC-T034-OPT-IN", "034", "001", null, mc, new String[]{"001"}, null, "FAIL"},
                {"T034-NON-FLEET-PROGRAM", "BR-PSPC-T034-PROGRAM", "034", "001", "2", "AMEX_CREDIT", new String[]{"001"}, null, "FAIL"},
                {"T034-WRONG-TABLE-ID", "BR-PSPC-T034-STRUCTURE", "035", "001", "2", mc, new String[]{"001"}, null, "FAIL"},
                {"T034-OVER-32", "BR-PSPC-T034-STRUCTURE", "034", "001".repeat(11), "2", mc, new String[]{"001"}, null, "FAIL"},
                {"T034-EMPTY", "BR-PSPC-T034-STRUCTURE", "034", "", "2", mc, new String[]{"001"}, null, "FAIL"},
                {"T034-NON-PRINTABLE", "BR-PSPC-T034-STRUCTURE", "034", "001\t002", "2", mc, new String[]{"001"}, null, "FAIL"}
        };
        for (Object[] example : cases) {
            ObjectNode payload = MAPPER.createObjectNode().put("mode", "HOST_PURCHASE_RESTRICTION").put("tableId", (String) example[2]).put("tableData", (String) example[3]);
            if (example[4] != null) payload.put("purchaseRestrictionMethod", (String) example[4]);
            payload.put("fleetProgram", (String) example[5]);
            if (example[6] != null) {
                ArrayNode codes = payload.putArray("productCodes");
                for (String value : (String[]) example[6]) codes.add(value);
            }
            if (example[7] != null) payload.put("referenceVersion", (String) example[7]);
            addChain(pack, V41.idPrefix(), (String) example[1], (String) example[0], payload, MAPPER.createObjectNode().put("outcome", (String) example[8]), layout);
        }
        return cases.length;
    }

    private static void addRequirement(ObjectNode pack, String id, String title, String scope, JsonNode... anchors) {
        ObjectNode br = ((ArrayNode) pack.path("businessRequirements")).addObject();
        br.put("id", id).put("title", title).put("status", "REVIEW_REQUIRED").put("assertionScope", scope).put("smeApproved", false);
        ArrayNode sources = br.putArray("sourceAnchors");
        for (JsonNode anchor : anchors) sources.add(anchor.deepCopy());
    }

    private static void addChain(ObjectNode pack, String prefix, String br, String suffix, ObjectNode payload, ObjectNode expected, JsonNode anchor) {
        String tsId = "TS-" + prefix + "-" + suffix;
        String tcId = "TC-" + prefix + "-" + suffix;
        String tdId = "TD-" + prefix + "-" + suffix;
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

    private static ObjectNode pspcAnchor(String version, int page, String rule, int start, int end) {
        return MAPPER.createObjectNode().put("document", "Conexxus PSPC").put("version", version).put("page", page)
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
