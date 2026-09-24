package com.coreauth.validator.coverage;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Writes compact per-segment validation evidence while bounding diagnostic samples. */
public final class Run2SegmentValidationEvidenceWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public void write(Run2SegmentValidationEvidence evidence, Path output) throws IOException {
        ObjectNode root = mapper.createObjectNode();
        root.put("artifact", "run2-segment-validation-evidence");
        root.put("segment", evidence.segment());
        root.put("sourceRun", evidence.sourceRun());
        root.put("versionResolutionId", evidence.versionResolutionId());
        ObjectNode artifacts = root.putObject("artifacts");
        artifacts.put("requirements", evidence.requirements());
        artifacts.put("scenarios", evidence.scenarios());
        artifacts.put("testCases", evidence.testCases());
        artifacts.put("testData", evidence.testData());

        AiCoverageAssessmentReport report = evidence.assessment();
        ObjectNode assessment = root.putObject("assessment");
        assessment.put("decision", report.executionReady() ? "EXECUTION_READY" : "REVIEW_REQUIRED");
        assessment.put("coverageDenominator", report.coverageDenominator());
        assessment.put("confirmedRequirements", report.confirmedRequirements());
        assessment.put("fullChainRequirements", report.fullChainRequirements());
        assessment.put("reviewRequired", report.reviewRequired());
        assessment.put("missingRequirements", report.missingRequirements());
        assessment.put("unmatchedAiRequirements", report.unmatchedAiRequirements());
        assessment.put("confirmedRequirementCoveragePercent", report.confirmedRequirementCoveragePercent());
        assessment.put("fullChainCoveragePercent", report.fullChainCoveragePercent());
        assessment.set("strategyReadiness", mapper.valueToTree(report.strategyReadiness()));
        addValidation(assessment, "baselineValidation", report.baselineValidation());
        addValidation(assessment, "testCaseValidation", report.testCaseValidation());
        addValidation(assessment, "traceabilityValidation", report.traceabilityValidation());
        addValidation(assessment, "payloadValidation", report.payloadValidation());
        root.set("payloadBatch", mapper.valueToTree(evidence.payloadBatch()));

        Files.createDirectories(output.toAbsolutePath().normalize().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), root);
    }

    private void addValidation(ObjectNode parent, String name, ValidationResult result) {
        ObjectNode node = parent.putObject(name);
        node.put("valid", result.isValid());
        node.put("errorCount", result.errors().size());
        node.put("warningCount", result.warnings().size());
        node.set("errorSamples", mapper.valueToTree(result.errors().stream().limit(25)
                .map(error -> error.reason()).toList()));
        node.set("warningSamples", mapper.valueToTree(result.warnings().stream().limit(25).toList()));
    }
}