package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105ElementValueValidator;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Source-template envelope checks; aliases are resolved, external layouts are never guessed. */
public final class Chapter11MessageLayoutValidator {
    public record Result(Status status, List<Finding> findings) { }
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonNode templates;
    private final Atl105ElementValueValidator elementValidator;
    private final Chapter13DataElementValidator chapter13Validator;

    public Chapter11MessageLayoutValidator() throws IOException {
        JsonNode catalog = mapper.readTree(Atl105Paths.docs().resolve("atl105_complete_templates.json").toFile());
        templates = catalog.path("message_templates");
        if (!templates.isObject() || templates.size() != catalog.path("total_transaction_types").asInt()) {
            throw new IOException("Incomplete Chapter 11 template catalog");
        }
        elementValidator = new Atl105ElementValueValidator(Atl105Paths.root());
        chapter13Validator = new Chapter13DataElementValidator(Atl105Paths.root());
    }

    public Result validate(JsonNode input) throws IOException {
        List<Finding> findings = new ArrayList<>();
        if (input == null || !input.isObject() || !"2026-3".equals(input.path("specificationVersion").asText())
                || !input.path("elements").isObject() || !input.path("segments").isArray()) {
            return new Result(Status.INVALID, List.of(new Finding("", "input", Status.INVALID,
                    "Version 2026-3, text-valued numbered elements and ordered segments array are required")));
        }
        String family = input.path("messageFamily").asText();
        JsonNode template = templates.get(family);
        if (template == null) return new Result(Status.INVALID,
                List.of(new Finding("", "message-family", Status.INVALID, "Unknown Chapter 11 message family")));
        String section = template.path("source_section").asText();
        Set<String> visited = new HashSet<>();
        while (template.has("alias_of")) {
            String alias = template.path("alias_of").asText();
            if (!visited.add(alias) || templates.get(alias) == null) throw new IOException("Invalid/cyclic message-template alias " + alias);
            template = templates.get(alias);
        }
        String anchor = "ATL105|2026-3|" + section + "|MESSAGE";
        if ("BLOCKED_EXTERNAL_SOURCE".equals(template.path("review_status").asText())) {
            return new Result(Status.REVIEW_REQUIRED, List.of(new Finding(anchor, "external-layout",
                    Status.REVIEW_REQUIRED, "TransArmor external specification is required; empty template is not a valid message")));
        }
        boolean partial = "PARTIAL".equals(input.path("completeness").asText());
        fields(template.path("data_section_1_fields"), input.path("elements"), partial, true, anchor, findings);
        fields(template.path("fields"), input.path("elements"), partial, false, anchor, findings);
        if ("Electronic Mail Response".equals(family)) {
            ObjectNode observation = mapper.createObjectNode().put("specificationVersion", "2026-3")
                    .put("messageFamily", family).put("segment", "MESSAGE");
            observation.set("elements", input.path("elements"));
            for (String field : List.of("chapter13Semantics", "qualifiers", "representation", "history")) {
                if (input.has(field)) observation.set(field, input.path(field));
            }
            if (partial) observation.put("completeness", "PARTIAL");
            elementFindings(observation, findings);
        }
        Map<String, JsonNode> allowed = new HashMap<>();
        for (JsonNode segment : template.path("segments")) allowed.put(segment.path("segment_number").asText(), segment);
        for (JsonNode extension : template.path("approved_extensions")) allowed.put(extension.path("segment_number").asText(), extension);
        Set<String> present = new HashSet<>();
        for (JsonNode segment : input.path("segments")) {
            JsonNode idNode = segment.get("segment");
            if (!segment.isObject() || idNode == null || !idNode.isTextual() || !idNode.textValue().matches("[0-9]{3}|DL[1-8]")) {
                findings.add(new Finding(anchor, "segment-identity", Status.INVALID, "Each segment needs an explicit textual source identity"));
                continue;
            }
            String id = idNode.textValue();
            present.add(id);
            if ((Set.of("100", "105", "109").contains(id) || input.path("chapter13Semantics").asBoolean(false))
                    && segment.has("observation")) {
                JsonNode supplied = segment.get("observation");
                if (!supplied.isObject() || !supplied.path("elements").isObject()) {
                    findings.add(new Finding(anchor, "chapter13-representation", Status.INVALID,
                            "Child element observation must be an object with numbered elements"));
                } else {
                    ObjectNode observation = supplied.deepCopy();
                    for (String field : List.of("specificationVersion", "messageFamily", "segment")) {
                        String expected = "specificationVersion".equals(field) ? "2026-3" : "messageFamily".equals(field) ? family : id;
                        if (supplied.has(field) && (!supplied.path(field).isTextual() || !expected.equals(supplied.path(field).textValue()))) {
                            findings.add(new Finding(anchor, "chapter13-context", Status.INVALID,
                                    "Child observation " + field + " contradicts parent context"));
                        }
                        observation.put(field, expected);
                    }
                    if (partial) observation.put("completeness", "PARTIAL");
                    for (String field : List.of("representation", "history")) {
                        if (input.has(field) && supplied.has(field) && !input.path(field).equals(supplied.path(field))) {
                            findings.add(new Finding(anchor, "chapter13-context", Status.INVALID, "Child " + field + " contradicts parent"));
                        }
                        if (input.has(field)) observation.set(field, input.path(field));
                    }
                    if (input.path("chapter13Semantics").asBoolean(false)) observation.put("chapter13Semantics", true);
                    for (String field : List.of("qualifiers", "records", "history")) {
                        if (!observation.has(field) && input.has(field)) observation.set(field, input.path(field));
                    }
                    if (input.path("qualifiers").isObject() && supplied.has("qualifiers")) {
                        if (!supplied.path("qualifiers").isObject()) {
                            findings.add(new Finding(anchor, "chapter13-context", Status.INVALID, "Child qualifiers must be an object"));
                        } else {
                            ObjectNode qualifiers = observation.path("qualifiers").deepCopy();
                            var fields = input.path("qualifiers").fields();
                            while (fields.hasNext()) {
                                var field = fields.next();
                                if (qualifiers.has(field.getKey()) && !qualifiers.path(field.getKey()).equals(field.getValue())) {
                                    findings.add(new Finding(anchor, "chapter13-context", Status.INVALID, "Child qualifier contradicts parent: " + field.getKey()));
                                }
                                qualifiers.set(field.getKey(), field.getValue());
                            }
                            observation.set("qualifiers", qualifiers);
                        }
                    }
                    elementFindings(observation, findings);
                }
            }
            if ("109".equals(id) && segment.has("processingObservation")) {
                JsonNode processing = segment.get("processingObservation");
                var result = new Segment109PayloadValidator().validateProcessingObservation(processing);
                for (var error : result.errors()) findings.add(new Finding("ATL105|2026-3|10.11|109",
                        "electronic-mail-processing", Status.INVALID, error.reason()));
                for (String warning : result.warnings()) findings.add(new Finding("ATL105|2026-3|10.11|109",
                        "electronic-mail-processing", Status.REVIEW_REQUIRED, warning));
                if (segment.path("observation").path("elements").has("78")
                        && !segment.path("observation").path("elements").path("78").equals(processing.path("promptCode"))) {
                    findings.add(new Finding(anchor, "electronic-mail-processing", Status.INVALID,
                            "Processing Prompt Code contradicts supplied Element 78"));
                }
            }
            JsonNode descriptor = allowed.get(id);
            if (descriptor == null) findings.add(new Finding(anchor, "membership", Status.REVIEW_REQUIRED,
                    "Segment " + id + " is not in this Chapter 11 table; later Chapter 12/appendix extensions need evidence"));
            else if (descriptor.has("data_section")) {
                JsonNode actualSection = segment.get("dataSection");
                if (actualSection == null) findings.add(new Finding(anchor, "placement", Status.REVIEW_REQUIRED, "Data section not supplied for " + id));
                else if (!actualSection.isIntegralNumber()) {
                    findings.add(new Finding(anchor, "placement", Status.INVALID, "Data section must be an integer for " + id));
                } else if (actualSection.asInt() != descriptor.path("data_section").asInt()) {
                    findings.add(new Finding(anchor, "placement", "131".equals(id) ? Status.REVIEW_REQUIRED : Status.INVALID,
                            "Unexpected data section for " + id + ("131".equals(id) ? "; known Chapters 11/12 placement conflict" : "")));
                }
            }
            if (segment.has("observation") && Chapter12SegmentPayloadValidator.SEGMENTS.contains(id)) {
                JsonNode observation = segment.get("observation");
                if (!observation.isObject() || !id.equals(observation.path("segment").asText())) {
                    findings.add(new Finding(anchor, "segment-observation", Status.INVALID, "Child observation identity does not match " + id));
                } else {
                    var child = new Chapter12SegmentPayloadValidator().validate(observation);
                    findings.addAll(child.findings());
                    if (!child.unassessedCatalogRules().isEmpty()) findings.add(new Finding(anchor, "child-rule-gaps", Status.REVIEW_REQUIRED,
                            "Unassessed catalog rules for " + id + ": " + child.unassessedCatalogRules()));
                }
            } else findings.add(new Finding(anchor, "segment-content", Status.REVIEW_REQUIRED,
                    "Envelope presence is not field/wire validation for " + id));
        }
        for (var entry : allowed.entrySet()) {
            if (Set.of("R", "PRESENT").contains(entry.getValue().path("presence_in_message").asText()) && !present.contains(entry.getKey())) {
                findings.add(new Finding(anchor, "required-segment", partial ? Status.REVIEW_REQUIRED : Status.INVALID,
                        "Required Segment " + entry.getKey() + " absent"));
            }
        }
        if (template.path("data_section_1_fields").isArray() && template.path("data_section_1_fields").size() > 0) {
            String count = text(input.path("elements"), "63");
            if (count != null && count.matches("[0-9]{1,2}")) {
                if (Integer.parseInt(count) != input.path("segments").size()) findings.add(new Finding(anchor, "segment-count", Status.INVALID,
                        "Element 63 does not equal actual segment occurrences"));
                if ("Financial Transaction Request".equals(family) || "EMV Financial Transaction Request".equals(family)) {
                    String error = DataSection1StructureValidator.element63RangeError(Integer.parseInt(count),
                            "EMV Financial Transaction Request".equals(family));
                    if (error != null) findings.add(new Finding(anchor, "segment-slots", Status.INVALID, error));
                }
            } else if (!partial || count != null) findings.add(new Finding(anchor, "segment-count", Status.INVALID,
                    "Element 63 is one or two ASCII digits"));
        }
        if (present.contains("102") && present.contains("157") || present.contains("101") && present.contains("145")) {
            findings.add(new Finding(anchor, "companion-exclusion", Status.INVALID, "Mutually exclusive product/fleet segments coexist"));
        }
        int maximum = template.path("message_length_maximum").asInt();
        if (maximum > 0 && input.has("serializedMessage")) {
            String wire = text(input, "serializedMessage");
            if (wire == null || wire.length() > maximum) findings.add(new Finding(anchor, "message-cap", Status.INVALID,
                    "Serialized message exceeds template maximum " + maximum + " or is not text"));
        }
        findings.add(new Finding(anchor, "scope", Status.REVIEW_REQUIRED,
                "Conditional companions, actual serialization, scalar semantics and lifecycle need segment/element oracles and matched context; "
                        + "template extraction is not complete message certification"));
        Status status = findings.stream().anyMatch(f -> f.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED;
        return new Result(status, List.copyOf(findings));
    }

    private void elementFindings(JsonNode observation, List<Finding> findings) {
        var result = observation.path("chapter13Semantics").asBoolean(false)
                ? chapter13Validator.validate(observation) : elementValidator.validate(observation);
        for (var finding : result.findings()) {
            findings.add(new Finding(finding.ruleId(), "chapter13-representation",
                    Status.valueOf(finding.status().name()), finding.reason()));
        }
    }

    private static void fields(JsonNode definitions, JsonNode elements, boolean partial, boolean requiredByLayout,
                               String anchor, List<Finding> findings) {
        for (JsonNode field : definitions) {
            String element = field.path("element_no").asText();
            if (element.isBlank()) {
                findings.add(new Finding(anchor, "unnumbered-field", Status.REVIEW_REQUIRED, "Unnumbered field requires positional observation"));
                continue;
            }
            JsonNode value = elements.get(element);
            if (value == null && (requiredByLayout || "R".equals(field.path("entry").asText()))) {
                findings.add(new Finding(anchor, "element-" + element, partial ? Status.REVIEW_REQUIRED : Status.INVALID, "Required field missing"));
            } else if (value != null) {
                if (!value.isTextual()) findings.add(new Finding(anchor, "element-" + element, Status.INVALID, "Element values must be text; no numeric coercion"));
                else {
                    if (value.textValue().isEmpty() && (requiredByLayout || "R".equals(field.path("entry").asText()))) {
                        findings.add(new Finding(anchor, "element-" + element, Status.INVALID, "Explicit required field cannot be empty"));
                    }
                    if (field.path("fixed_value").isTextual() && !field.path("fixed_value").textValue().equals(value.textValue())) {
                        findings.add(new Finding(anchor, "element-" + element, Status.INVALID, "Incorrect source-fixed field value"));
                    }
                    if (field.path("length").isIntegralNumber() && field.path("length").asInt() > 0
                            && value.textValue().length() > field.path("length").asInt()) {
                        findings.add(new Finding(anchor, "element-" + element, Status.INVALID, "Exceeds source field maximum"));
                    }
                }
            }
        }
    }

    private static String text(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value != null && value.isTextual() ? value.textValue() : null;
    }

    public static void main(String[] args) throws IOException {
        Chapter11MessageLayoutValidator validator = new Chapter11MessageLayoutValidator();
        ObjectNode report = validator.mapper.createObjectNode();
        report.put("scope", "Executed partial-observation template dispatch for every Chapter 11 family; not full message coverage");
        report.put("completeMessageCoverage", false);
        var executions = report.putArray("executions");
        var families = validator.templates.fieldNames();
        while (families.hasNext()) {
            String family = families.next();
            ObjectNode input = validator.mapper.createObjectNode().put("specificationVersion", "2026-3")
                    .put("messageFamily", family).put("completeness", "PARTIAL");
            input.putObject("elements");
            input.putArray("segments");
            ObjectNode row = executions.addObject().put("messageFamily", family);
            row.set("assessment", validator.mapper.valueToTree(validator.validate(input)));
        }
        Path output = Atl105Paths.testJson("chapter-11-envelope-evidence.json");
        Files.createDirectories(output.getParent());
        validator.mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
    }
}
