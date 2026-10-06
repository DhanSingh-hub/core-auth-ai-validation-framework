package com.coreauth.validator.coverage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

public final class GenerateAppendixITrainingInventory {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Pattern ID = Pattern.compile(
            "^Table\\s+ID\\s+(?:an|n)\\d?\\s+Fixed\\s+Value\\s*:\\s*(\\d{2,3})\\b",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern SUB_ID = Pattern.compile(
            "^Sub[- ]*Table\\s+ID\\s*[- ]*[\"\\u201c](\\d{2})[\"\\u201d]",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern PAGE = Pattern.compile("^Appendix I-(\\d+)\\s*$");
    private static final Pattern PDF_PAGE = Pattern.compile("^--- PAGE (\\d+) ---$");

    private GenerateAppendixITrainingInventory() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 2) {
            throw new IllegalArgumentException("Usage: [ATL105 root] [output file]");
        }
        Path root = args.length > 0 ? Path.of(args[0]) : Path.of("specifications", "ATL105");
        Path output = args.length > 1 ? Path.of(args[1])
                : root.resolve(Path.of("test-output", "test-json", "knowledge", "appendix-i-inventory.json"));
        ObjectNode inventory = generate(root);
        if (output.toAbsolutePath().getParent() != null) {
            Files.createDirectories(output.toAbsolutePath().getParent());
        }
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), inventory);
        System.out.println("Appendix I source inventory: " + inventory.path("tableCount").asInt()
                + " tables; semantic training and AI coverage NOT_ESTABLISHED.");
    }

    public static ObjectNode generate(Path root) throws IOException {
        Path source = root.resolve(Path.of("docs", "specs", "extracted_text.txt"));
        Path pdf = root.resolve(Path.of("docs", "specs",
                "BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf"));
        List<String> lines = Files.readAllLines(source, StandardCharsets.UTF_8);
        ObjectNode result = scan(lines);
        ObjectNode provenance = result.putObject("source");
        provenance.put("textPath", "docs/specs/extracted_text.txt");
        provenance.put("textSha256", sha256(Files.readAllBytes(source)));
        provenance.put("pdfPath", "docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf");
        provenance.put("pdfSha256", sha256(Files.readAllBytes(pdf)));
        provenance.put("producer", GenerateAppendixITrainingInventory.class.getName());
        return result;
    }

    static ObjectNode scan(List<String> lines) {
        int start = uniqueHeading(lines, "Appendix I.  Variable Information Data Layouts");
        int end = uniqueHeading(lines, "Appendix J.  Valid Point-of-Service Entry Mode Codes");
        if (end <= start || lines.stream().noneMatch(line ->
                line.strip().equals("Specification Release Version:    2026-3"))) {
            throw new IllegalArgumentException("Missing or invalid ATL105 2026-3 Appendix I boundaries");
        }
        ObjectNode out = MAPPER.createObjectNode();
        out.put("inventoryId", "ATL105-APPENDIX-I-SOURCE-INVENTORY-001");
        out.put("specification", "ATL105");
        out.put("specificationVersion", "2026-3");
        out.put("status", "SOURCE_LOCATED_NOT_SEMANTICALLY_TRAINED");
        out.put("appendixStartLine", start + 1);
        out.put("appendixEndLine", end);
        out.put("policy", "Navigation spans are not complete rules or executable coverage. Read titles, notes, "
                + "continuations and contextual references before deriving BR/TS/TC/TD. AI input is not used.");
        out.putArray("elements").add("111").add("112").add("113");
        ArrayNode tables = out.putArray("tables");
        TreeSet<String> ids = new TreeSet<>();
        ObjectNode current = null;
        List<ObjectNode> rows = new ArrayList<>();
        String page = "";
        int physicalPage = 0;
        for (int i = 0; i < start; i++) {
            var marker = PDF_PAGE.matcher(lines.get(i).strip());
            if (marker.matches()) {
                physicalPage = Integer.parseInt(marker.group(1));
            }
        }
        for (int i = start; i < end; i++) {
            String line = lines.get(i).strip();
            var pageMatch = PAGE.matcher(line);
            if (pageMatch.matches()) {
                page = "Appendix I-" + pageMatch.group(1);
            }
            var pdfMatch = PDF_PAGE.matcher(line);
            if (pdfMatch.matches()) {
                physicalPage = Integer.parseInt(pdfMatch.group(1));
            }
            String window = String.join(" ", lines.subList(i, Math.min(i + 4, end)))
                    .replaceAll("\\s+", " ").strip();
            var idMatch = ID.matcher(window);
            if (line.matches("(?i)^Table(?:\\s+ID.*)?$") && idMatch.find()) {
                String id = idMatch.group(1);
                if (id.length() == 3) {
                    if (!ids.add(id)) {
                        throw new IllegalArgumentException("Duplicate top-level Appendix I table: " + id);
                    }
                    if (current != null) {
                        current.put("navigationEndLine", i);
                    }
                    current = tables.addObject();
                    rows.add(current);
                    current.put("tableId", id);
                    current.put("headerLine", i + 1);
                    current.put("sourcePage", page);
                    current.put("pdfPage", physicalPage);
                    current.put("status", "SOURCE_LOCATED_NOT_SEMANTICALLY_TRAINED");
                    current.putArray("nestedSelectorOccurrences");
                    current.putObject("sourceAnchor")
                            .put("specification", "ATL105")
                            .put("version", "2026-3")
                            .put("section", page)
                            .put("segment", "111")
                            .put("element", "111")
                            .put("rule", "appendix-i-table-" + id);
                } else {
                    addNested(current, id, i + 1, page);
                }
            }
            var subMatch = SUB_ID.matcher(line);
            if (subMatch.find()) {
                addNested(current, subMatch.group(1), i + 1, page);
            }
        }
        if (current == null) {
            throw new IllegalArgumentException("No Appendix I tables found");
        }
        current.put("navigationEndLine", end);
        for (ObjectNode row : rows) {
            row.put("nestedStatus", "OCCURRENCES_ONLY_REQUIRES_FIELD_AND_CONTEXT_REVIEW");
        }
        out.put("tableCount", ids.size());
        ArrayNode gaps = out.putArray("unlocatedIdsWithinAllocatedRange");
        for (int id = 1; id <= Integer.parseInt(ids.last()); id++) {
            String candidate = "%03d".formatted(id);
            if (!ids.contains(candidate)) {
                gaps.add(candidate);
            }
        }
        out.put("unlocatedIdPolicy", "Not evidence of a defined or invalid Table ID; source review required.");
        ObjectNode coverage = out.putObject("coverage");
        coverage.put("semanticallyTrainedTables", 0);
        coverage.put("approvedRules", 0);
        coverage.put("executedCases", 0);
        coverage.put("certifiedRules", 0);
        coverage.putNull("aiCoveragePercent");
        coverage.put("aiIntakeStatus", "NOT_ASSESSED");
        return out;
    }

    private static void addNested(ObjectNode table, String id, int line, String page) {
        if (table == null) {
            throw new IllegalArgumentException("Nested selector precedes an Appendix I table at line " + line);
        }
        ArrayNode occurrences = (ArrayNode) table.path("nestedSelectorOccurrences");
        occurrences.addObject().put("subTableId", id).put("line", line).put("sourcePage", page);
    }

    private static int uniqueHeading(List<String> lines, String heading) {
        int found = -1;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).strip().equals(heading)) {
                if (found >= 0) {
                    throw new IllegalArgumentException("Duplicate source boundary: " + heading);
                }
                found = i;
            }
        }
        if (found < 0) {
            throw new IllegalArgumentException("Missing source boundary: " + heading);
        }
        return found;
    }

    private static String sha256(byte[] bytes) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
        } catch (NoSuchAlgorithmException error) {
            throw new IllegalStateException("SHA-256 is unavailable", error);
        }
    }
}
