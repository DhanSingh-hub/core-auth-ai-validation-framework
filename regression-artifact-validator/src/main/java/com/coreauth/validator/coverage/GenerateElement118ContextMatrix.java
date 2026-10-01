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

/** Separates the three source-backed meanings of Element 118 by segment and serialization context. */
public final class GenerateElement118ContextMatrix {
    private GenerateElement118ContextMatrix() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode reference = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-all-elements-reference.json").toFile());
        JsonNode brCoverage = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json").toFile());
        JsonNode definitions = null;
        for (JsonNode element : reference.path("elements")) {
            if ("118".equals(element.path("element").asText())) {
                definitions = element.path("definitions");
                break;
            }
        }
        if (definitions == null || !definitions.isArray()) throw new IllegalStateException("Element 118 definitions are missing from the reference");
        ObjectNode output = mapper.createObjectNode();
        output.put("artifact", "ATL105-ELEMENT-118-CONTEXT-MATRIX");
        output.put("specification", "ATL105");
        output.put("specificationVersion", "2026-3");
        output.put("element", "118");
        output.put("authority", "INDEPENDENT_TEST_SOLUTION_SOURCE_DERIVED_REVIEW");
        output.put("status", "MULTIPLE_DEFINITIONS_REVIEW_REQUIRED");
        output.put("policy", "Element 118 is not globally unique semantically. Segment, message family, companion fields, repetition and byte caps are required to interpret its value. Never reuse Segment 112 rules for Segment 130/131.");
        output.put("sourceDefinitionCount", definitions.size());
        ArrayNode contexts = output.putArray("contexts");
        addContext(contexts, definitions, "112", "Additional Information", "Financial Transaction Response", "SINGLE_TRIAD", 984, "Element 116 Indicator + Element 117 Length + Element 118 Value", "Field Separators/triad semantics from Section 12.11", brCoverage, List.of("SEG112-R-006", "SEG112-R-008", "SEG112-R-009", "SEG112-R-010"));
        addContext(contexts, definitions, "130", "EMV Additional Information", "EMV Financial Transaction Request", "REPEATING_GROUP", 2000, "Element 191 Indicator + Element 192 Length + Element 118 Information", "No separators within/between repetitions; one final separator; Section 12.20", brCoverage, List.of("SEG130-R-011", "SEG130-R-012", "SEG130-R-013", "SEG130-R-020", "SEG130-R-021", "SEG130-R-022"));
        addContext(contexts, definitions, "131", "EMV Additional Information", "EMV Financial Transaction Response", "REPEATING_GROUP", 2800, "Element 191 Indicator + Element 192 Length + Element 118 Information", "Inverted response serialization; Section 12.21", brCoverage, List.of("SEG131-R-009", "SEG131-R-010"));
        output.put("unresolvedQuestions", "Element 118 values must be interpreted with the segment-specific companion indicator/length and repetition rules. A raw element number or value without segment/family context is REVIEW_REQUIRED.");
        output.put("validityPolicy", "Segment 112: variable value <= 984 and agrees with Element 117 and Element 116. Segment 130: repeating EMV value <= 2,000 aggregate and agrees with Element 192. Segment 131: repeating EMV value <= 2,800 aggregate and follows response serialization. These are not interchangeable.");
        Path json = pack.resolve("test-output/test-solution-independent-review/element-118-context-matrix.json");
        Path csv = pack.resolve("test-output/test-solution-independent-review/element-118-context-matrix.csv");
        Files.createDirectories(json.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), output);
        writeCsv(csv, contexts);
        System.out.printf("element=118 definitions=%d contexts=%d json=%s csv=%s%n", definitions.size(), contexts.size(), json, csv);
    }

    private static void addContext(ArrayNode contexts, JsonNode definitions, String segment, String name, String family,
                                   String repetition, int cap, String companions, String serialization, JsonNode brCoverage,
                                   List<String> rules) {
        ObjectNode context = contexts.addObject();
        context.put("segment", segment);
        context.put("meaning", name);
        context.put("messageFamily", family);
        context.put("definitionFound", definitions.toString().contains(name));
        context.put("repetition", repetition);
        context.put("aggregateByteCap", cap);
        context.put("companionElements", companions);
        context.put("serialization", serialization);
        ArrayNode ruleArray = context.putArray("sourceRules");
        ArrayNode brArray = context.putArray("businessRequirements");
        for (String rule : rules) {
            ruleArray.add(rule);
            for (JsonNode br : brCoverage.path("businessRequirements")) {
                if (rule.equals(br.path("id").asText()) || rule.equals(br.path("sourceAnchor").path("rule").asText()))
                    brArray.add(rule + ":" + br.path("brCoverageStatus").asText("REVIEW"));
            }
        }
        context.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
    }

    private static void writeCsv(Path path, ArrayNode contexts) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,segment,meaning,messageFamily,definitionFound,repetition,aggregateByteCap,companionElements,serialization,sourceRules,businessRequirements,status");
        for (JsonNode row : contexts) {
            List<String> fields = List.of("118", row.path("segment").asText(), row.path("meaning").asText(), row.path("messageFamily").asText(),
                row.path("definitionFound").asText(), row.path("repetition").asText(), row.path("aggregateByteCap").asText(),
                row.path("companionElements").asText(), row.path("serialization").asText(), join(row.path("sourceRules")),
                join(row.path("businessRequirements")), row.path("status").asText());
            lines.add(fields.stream().map(GenerateElement118ContextMatrix::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String join(JsonNode array) {
        List<String> values = new ArrayList<>();
        array.forEach(value -> values.add(value.asText()));
        return String.join(";", values);
    }

    private static String csv(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }
}
