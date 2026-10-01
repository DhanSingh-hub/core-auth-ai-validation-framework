package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/** Classifies element/family/segment presence without promoting template omission to prohibition. */
public final class GenerateAtl105ElementOmissionMatrix {
    private static final String REFERENCE = "test-output/test-solution-independent-review/atl105-all-elements-reference.json";
    private GenerateAtl105ElementOmissionMatrix() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode reference = mapper.readTree(pack.resolve(REFERENCE).toFile());
        ArrayNode rows = mapper.createArrayNode();
        TreeMap<String, Integer> counts = new TreeMap<>();
        for (JsonNode element : reference.path("elements")) {
            for (JsonNode context : element.path("messageFamilyContexts")) {
                String kind = context.path("contextKind").asText();
                String status;
                String reason;
                if ("ACTIVE_TEMPLATE_FIELD".equals(kind)) {
                    status = "CALLED_IN_ACTIVE_TEMPLATE";
                    reason = "Element has an active extracted Section 11 field usage in this family/segment context.";
                } else if ("SEGMENT_LISTED_FIELD_NOT_LISTED".equals(kind)) {
                    status = "NOT_LISTED_REVIEW_REQUIRED";
                    reason = "Segment is listed for the family, but the element field is absent from the extracted template; this is not proof of prohibition.";
                } else {
                    status = "NOT_CALLED_NOT_ESTABLISHED";
                    reason = "Element's source-linked segment is not listed in this family template; no source rule establishes prohibition or non-applicability.";
                }
                ObjectNode row = rows.addObject();
                row.put("element", element.path("element").asText());
                row.put("sourceNames", String.join("; ", values(element.path("sourceNames"))));
                row.put("messageFamily", context.path("messageFamily").asText());
                row.put("transactionFamilyLabel", context.path("transactionTypeLabel").asText());
                row.put("sourceSection", context.path("sourceSection").asText());
                row.put("segment", context.path("segment").asText());
                row.put("segmentPresence", context.path("segmentPresence").asText());
                row.put("elementPresence", context.path("elementPresence").asText());
                row.put("contextKind", kind);
                row.put("calledStatus", status);
                row.put("reason", reason);
                row.put("sourceEvidenceStatus", context.path("evidenceStatus").asText());
                row.put("decision", "REVIEW_REQUIRED_UNLESS_EXPLICIT_SOURCE_ABSENCE_RULE_IS_SEMANTICALLY_CONFIRMED");
                counts.merge(status, 1, Integer::sum);
            }
        }
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-ELEMENT-FAMILY-SEGMENT-OMISSION-MATRIX");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION_SOURCE_DERIVED_REVIEW");
        output.put("humanReviewRequired", true);
        output.put("policy", "Called means an active extracted Section 11 field usage. Not listed means review-required, not prohibited. No row is automatically certified as NOT_CALLED.");
        output.put("rowCount", rows.size());
        output.set("counts", mapper.valueToTree(counts));
        output.set("rows", rows);
        Path json = pack.resolve("test-output/test-solution-independent-review/atl105-element-omission-matrix.json");
        Path csv = pack.resolve("test-output/test-solution-independent-review/atl105-element-omission-matrix.csv");
        Files.createDirectories(json.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), output);
        writeCsv(csv, rows);
        System.out.printf("rows=%d called=%d listedReview=%d notEstablished=%d%n", rows.size(),
            counts.getOrDefault("CALLED_IN_ACTIVE_TEMPLATE", 0), counts.getOrDefault("NOT_LISTED_REVIEW_REQUIRED", 0),
            counts.getOrDefault("NOT_CALLED_NOT_ESTABLISHED", 0));
    }

    private static List<String> values(JsonNode array) {
        List<String> values = new ArrayList<>();
        array.forEach(value -> values.add(value.asText()));
        return values;
    }

    private static void writeCsv(Path path, ArrayNode rows) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,sourceNames,messageFamily,transactionFamilyLabel,sourceSection,segment,segmentPresence,elementPresence,contextKind,calledStatus,reason,sourceEvidenceStatus,decision");
        for (JsonNode row : rows) {
            List<String> fields = List.of(row.path("element").asText(), row.path("sourceNames").asText(), row.path("messageFamily").asText(),
                row.path("transactionFamilyLabel").asText(), row.path("sourceSection").asText(), row.path("segment").asText(),
                row.path("segmentPresence").asText(), row.path("elementPresence").asText(), row.path("contextKind").asText(),
                row.path("calledStatus").asText(), row.path("reason").asText(), row.path("sourceEvidenceStatus").asText(), row.path("decision").asText());
            lines.add(fields.stream().map(GenerateAtl105ElementOmissionMatrix::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String csv(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }
}
