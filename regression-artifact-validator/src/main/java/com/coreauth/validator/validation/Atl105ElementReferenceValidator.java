package com.coreauth.validator.validation;

import com.coreauth.validator.coverage.GenerateAtl105ElementReference;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Result;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Validates reference integrity and observations without treating undocumented contexts as invalid. */
public final class Atl105ElementReferenceValidator {
    public record MatrixResult(boolean structurallyValid, int elementCount, List<String> findings) { }
    public record ObservationResult(Status status, List<Finding> findings) { }

    private final Path pack;
    private final JsonNode matrix;
    private final JsonNode templates;
    private final Atl105ElementValueValidator valueValidator;

    public Atl105ElementReferenceValidator(Path pack) throws IOException {
        this.pack = pack;
        ObjectMapper mapper = new ObjectMapper();
        matrix = mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-all-elements-reference.json").toFile());
        templates = mapper.readTree(pack.resolve("docs/atl105_complete_templates.json").toFile()).path("message_templates");
        valueValidator = new Atl105ElementValueValidator(pack);
    }

    public MatrixResult validateMatrix() {
        List<String> findings = new ArrayList<>();
        try {
            GenerateAtl105ElementReference.assertMatrix(matrix);
        } catch (IllegalStateException exception) {
            findings.add(exception.getMessage());
        }
        if (matrix.path("elementCount").asInt() != matrix.path("elements").size())
            findings.add("Declared elementCount does not equal element rows");
        if (matrix.path("summary").path("approvedBusinessRules").asInt() != 0)
            findings.add("Generated matrix must not claim source approval");
        if (matrix.path("summary").path("absentFromTemplateMeansProhibited").asBoolean())
            findings.add("Template omissions must not be promoted to prohibited values");
        for (JsonNode element : matrix.path("elements")) {
            Set<String> contexts = new HashSet<>();
            for (JsonNode context : element.path("messageFamilyContexts")) {
                String key = context.path("messageFamily").asText() + "|" + context.path("segment").asText();
                if (!contexts.add(key)) findings.add("Duplicate context " + key + " for Element " + element.path("element").asText());
                if (!templates.has(context.path("messageFamily").asText()))
                    findings.add("Unknown message family on Element " + element.path("element").asText() + ": " + key);
            }
        }
        return new MatrixResult(findings.isEmpty(), matrix.path("elements").size(), List.copyOf(findings));
    }

    public ObservationResult validate(JsonNode observation) {
        List<Finding> findings = new ArrayList<>();
        if (observation == null || !observation.isObject()
            || !"2026-3".equals(observation.path("specificationVersion").asText())
            || observation.path("messageFamily").asText().isBlank()
            || !observation.path("segments").isArray()) {
            return new ObservationResult(Status.REVIEW_REQUIRED, List.of(new Finding("", Status.REVIEW_REQUIRED, "",
                "Provide specificationVersion 2026-3, a known messageFamily and a segments array")));
        }
        String family = observation.path("messageFamily").asText();
        JsonNode template = templates.get(family);
        if (template == null) {
            return new ObservationResult(Status.REVIEW_REQUIRED, List.of(new Finding("", Status.REVIEW_REQUIRED, "",
                "No extracted Section 11 template for message family " + family)));
        }
        Set<String> present = new HashSet<>();
        for (JsonNode segmentNode : observation.path("segments")) {
            String segment = segmentNode.path("segment").asText();
            if (segment.isBlank() || !segmentNode.path("elements").isObject()) {
                findings.add(new Finding(segment, Status.REVIEW_REQUIRED, "", "Segment needs an identity and element-number-keyed string map"));
                continue;
            }
            present.add(segment);
            ObjectNode segmentObservation = baseObservation(family, segment, (ObjectNode) segmentNode.path("elements"));
            segmentObservation.put("completeness", "PARTIAL");
            if (observation.has("qualifiers")) segmentObservation.set("qualifiers", observation.path("qualifiers").deepCopy());
            Result result = valueValidator.validate(segmentObservation);
            findings.addAll(result.findings());
            recordContextReview(elementFields(segmentNode.path("elements")), family, segment, findings);
        }
        ObjectNode message = baseObservation(family, "MESSAGE", JsonNodeFactory.instance.objectNode());
        ArrayNode segmentsPresent = message.putArray("segmentsPresent");
        present.forEach(segmentsPresent::add);
        message.put("completeness", observation.path("completeness").asText("COMPLETE"));
        if (observation.has("qualifiers")) message.set("qualifiers", observation.path("qualifiers").deepCopy());
        findings.addAll(valueValidator.validate(message).findings());
        if (observation.has("transactionTypeCode")) {
            String txCode = observation.path("transactionTypeCode").asText();
            boolean known = false;
            for (JsonNode tx : matrix.path("transactionTypes")) if (txCode.equals(tx.path("code").asText())) known = true;
            if (!known) findings.add(new Finding("78", Status.REVIEW_REQUIRED, "", "Transaction type code is not in the extracted Segment 100 transaction-type list"));
            else findings.add(new Finding("", Status.REVIEW_REQUIRED, "", "Transaction-type code is known, but applicability of every element/segment to that code is not exhaustively established"));
        }
        Status status = findings.stream().anyMatch(finding -> finding.status() == Status.INVALID) ? Status.INVALID
            : findings.isEmpty() || findings.stream().anyMatch(finding -> finding.status() == Status.REVIEW_REQUIRED)
                ? Status.REVIEW_REQUIRED : Status.CHECKS_PASSED;
        return new ObservationResult(status, List.copyOf(findings));
    }

    private void recordContextReview(Set<String> ids, String family, String segment, List<Finding> findings) {
        for (String id : ids) {
            JsonNode row = null;
            for (JsonNode item : matrix.path("elements")) {
                if (id.equals(item.path("element").asText())) { row = item; break; }
            }
            if (row == null) continue;
            boolean matched = false;
            for (JsonNode context : row.path("messageFamilyContexts")) {
                if (family.equals(context.path("messageFamily").asText()) && segment.equals(context.path("segment").asText())
                    && "ACTIVE_TEMPLATE_FIELD".equals(context.path("contextKind").asText())) {
                    matched = true;
                    break;
                }
            }
            if (!matched) findings.add(new Finding(id, Status.REVIEW_REQUIRED, "",
                "Element/segment/family combination has no active Section 11 field usage; absence is not proof of prohibition"));
        }
    }

    private static Set<String> elementFields(JsonNode fields) {
        Set<String> result = new HashSet<>();
        fields.fieldNames().forEachRemaining(result::add);
        return result;
    }

    private static ObjectNode baseObservation(String family, String segment, ObjectNode elements) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("specificationVersion", "2026-3");
        result.put("messageFamily", family);
        result.put("segment", segment);
        result.set("elements", elements.deepCopy());
        return result;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("Usage: Atl105ElementReferenceValidator <ATL105-pack> <observation.json> <report.json>");
        Path pack = Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        Atl105ElementReferenceValidator validator = new Atl105ElementReferenceValidator(pack);
        JsonNode observation = mapper.readTree(Path.of(args[1]).toFile());
        MatrixResult matrixResult = validator.validateMatrix();
        ObservationResult observationResult = validator.validate(observation);
        ObjectNode report = mapper.createObjectNode();
        report.put("artifact", "ATL105-ELEMENT-OBSERVATION-VALIDATION");
        report.put("specificationVersion", "2026-3");
        report.put("matrixStructurallyValid", matrixResult.structurallyValid());
        report.put("matrixElements", matrixResult.elementCount());
        report.put("status", observationResult.status().name());
        report.put("policy", "INVALID is limited to extracted shape/profile or required-segment failures; unsupported and absent-from-template contexts remain REVIEW_REQUIRED.");
        report.set("matrixFindings", mapper.valueToTree(matrixResult.findings()));
        report.set("findings", mapper.valueToTree(observationResult.findings()));
        Path output = Path.of(args[2]);
        Files.createDirectories(output.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println("matrixValid=" + matrixResult.structurallyValid() + " elements=" + matrixResult.elementCount()
            + " observation=" + observationResult.status());
        if (!matrixResult.structurallyValid() || observationResult.status() != Status.CHECKS_PASSED) System.exit(1);
    }
}
