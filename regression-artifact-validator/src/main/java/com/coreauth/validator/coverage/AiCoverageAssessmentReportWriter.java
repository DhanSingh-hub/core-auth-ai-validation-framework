package com.coreauth.validator.coverage;

import com.coreauth.validator.validation.ValidationError;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Path;

/** Writes the independent assessment as stable JSON suitable for CI and audit review. */
public final class AiCoverageAssessmentReportWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public void write(AiCoverageAssessmentReport report, Path outputFile) throws IOException {
        ObjectNode root = mapper.createObjectNode();
        root.put("decision", report.executionReady() ? "EXECUTION_READY" : "REVIEW_REQUIRED");
        AiCoverageAssessmentJsonFields.write(root, report);
        root.set("strategyReadiness", mapper.valueToTree(report.strategyReadiness()));

        ObjectNode validation = root.putObject("validation");
        addValidation(validation, "baseline", report.baselineValidation());
        addValidation(validation, "testCases", report.testCaseValidation());
        addValidation(validation, "traceability", report.traceabilityValidation());
        addValidation(validation, "payloads", report.payloadValidation());

        Path parent = outputFile.toAbsolutePath().normalize().getParent();
        if (parent != null) {
            java.nio.file.Files.createDirectories(parent);
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile.toFile(), root);
    }

    private void addValidation(ObjectNode parent, String name, ValidationResult result) {
        ObjectNode node = parent.putObject(name);
        node.put("valid", result.isValid());
        ArrayNode errors = node.putArray("errors");
        for (ValidationError error : result.errors()) {
            ObjectNode item = errors.addObject();
            item.put("context", error.context());
            item.put("segment", error.segment());
            item.put("element", error.element());
            item.put("reason", error.reason());
        }
        ArrayNode warnings = node.putArray("warnings");
        result.warnings().forEach(warnings::add);
    }
}