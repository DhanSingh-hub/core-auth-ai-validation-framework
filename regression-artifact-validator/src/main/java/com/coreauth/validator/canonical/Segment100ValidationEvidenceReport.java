package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.coreauth.validator.validation.ValidationResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/** Generates refreshed evidence for Segment 100 AI-artifact validation coverage. */
public final class Segment100ValidationEvidenceReport {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Segment100TransactionTypeOracle oracle = new Segment100TransactionTypeOracle();
    private final Segment100AiJsonCompatibilityValidator compatibilityValidator = new Segment100AiJsonCompatibilityValidator();
    private final Segment100SingleStepCoverageValidator singleStepValidator = new Segment100SingleStepCoverageValidator();
    private final Segment100MultiStepCoverageValidator multiStepValidator = new Segment100MultiStepCoverageValidator();

    public ReportFiles generate(Path singleStepDirectory, Path lifecycleDirectory,
                                Path matrixFile, Path outputDirectory) throws IOException {
        Files.createDirectories(outputDirectory);
        ObjectNode root = mapper.createObjectNode();
        root.put("reportId", "ATL105-SEG100-VALIDATION-EVIDENCE");
        root.put("specification", "ATL105");
        root.put("specificationVersion", "2026-3");
        root.put("scope", "Independent validation of AI Solution artifacts");
        root.put("sourceOfTruth", "ATL105 specification and independent oracle");

        ObjectNode transactionCoverage = root.putObject("transactionTypeCoverage");
        transactionCoverage.put("expectedCodes", oracle.expectedCodeMatrix().size());
        ArrayNode codes = transactionCoverage.putArray("codes");
        for (Map.Entry<String, String> entry : oracle.expectedCodeMatrix().entrySet()) {
            ObjectNode code = codes.addObject();
            code.put("code", entry.getKey());
            code.put("meaning", entry.getValue());
            code.put("multiStepEligible", oracle.isMultiStepCode(entry.getKey()));
        }
        ValidationResult singleStepResult = singleStepValidator.validateDirectory(singleStepDirectory);
        transactionCoverage.put("singleStepStatus", status(singleStepResult));
        transactionCoverage.put("singleStepErrors", singleStepResult.errors().size());
        transactionCoverage.put("singleStepArtifactCount", countJson(singleStepDirectory));

        Segment100CardTypeCoverageReport.Report cardTypeResult = new Segment100CardTypeCoverageReport()
            .generate(singleStepDirectory, "LOCAL|" + singleStepDirectory.toAbsolutePath().normalize() + "|working-tree");
        ObjectNode cardCoverage = root.putObject("cardTypeCoverage");
        cardCoverage.put("expectedCardTypes", cardTypeResult.expectedCount());
        cardCoverage.put("status", cardTypeResult.status());
        cardCoverage.put("presentCount", cardTypeResult.present().size());
        cardCoverage.put("missingCount", cardTypeResult.missing().size());
        cardCoverage.put("duplicateCount", cardTypeResult.duplicates().size());
        cardCoverage.put("invalidCount", cardTypeResult.invalid().size());
        cardCoverage.put("provenance", cardTypeResult.provenance());
        ArrayNode presentCodes = cardCoverage.putArray("present");
        cardTypeResult.present().forEach(presentCodes::add);
        ArrayNode missingCodes = cardCoverage.putArray("missing");
        cardTypeResult.missing().forEach(missingCodes::add);
        cardCoverage.put("note", "AI artifact coverage is measured independently; the current generated baseline uses card type 020 only.");

        ObjectNode converter = root.putObject("converterPreflight");
        int preflightFailures = 0;
        int preflightFiles = 0;
        if (Files.isDirectory(singleStepDirectory)) {
            try (var files = Files.list(singleStepDirectory)) {
                for (Path file : files.filter(path -> path.toString().endsWith(".json")).sorted().toList()) {
                    preflightFiles++;
                    if (!compatibilityValidator.validateFile(file).isValid()) {
                        preflightFailures++;
                    }
                }
            }
        }
        converter.put("filesChecked", preflightFiles);
        converter.put("failures", preflightFailures);
        converter.put("status", preflightFailures == 0 ? "PASS" : "FAIL");
        converter.put("note", "Preflight only; actual ATL105 converter execution remains an external gate.");

        ObjectNode lifecycle = root.putObject("multiStepCoverage");
        ArrayNode flows = lifecycle.putArray("flows");
        addFlow(flows, "AUTHORIZATION_COMPLETION", Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION,
                lifecycleDirectory, "authorization-completion-authorization.source.json", "authorization-completion-completion.valid.json", "authorization-completion-completion.source.json");
        addFlow(flows, "AUTHORIZATION_CANCELLATION", Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_CANCELLATION,
            lifecycleDirectory, "authorization-cancellation-authorization.synthetic.json", "authorization-cancellation-cancellation.synthetic.json", null);
        addFlow(flows, "AUTHORIZATION_VOID", Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_VOID,
                lifecycleDirectory, "authorization-void-authorization.source.json", "authorization-void-void.valid.json", "authorization-void-void.source.json");
        addFlow(flows, "REFUND_VOID_OF_RETURN", Segment100MultiStepCoverageValidator.Flow.REFUND_VOID_OF_RETURN,
                lifecycleDirectory, "refund-void-refund.source.json", "refund-void-void-of-return.valid.json", "refund-void-void-of-return.source.json");
        addFlow(flows, "SALE_VOID", Segment100MultiStepCoverageValidator.Flow.SALE_VOID,
                lifecycleDirectory, "sale-void-sale.source.json", "sale-void-void.valid.json", "sale-void-void.source.json");
        addFlow(flows, "TIMEOUT_REVERSAL", Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL,
                lifecycleDirectory, "timeout-tor-original.synthetic.json", "timeout-tor-reversal.synthetic.json", null);
        addThreeMessageTimeout(flows, lifecycleDirectory);
        addTorRetryEvidence(flows, lifecycleDirectory);
        lifecycle.put("flowCount", flows.size());
        lifecycle.put("status", flows.findValuesAsText("positiveStatus").stream().allMatch("PASS"::equals) ? "PASS" : "FAIL");

        ObjectNode matrix = root.putObject("brTsTcTestData");
        JsonNode matrixInput = mapper.readTree(matrixFile.toFile());
        matrix.put("transactionTypes", matrixInput.path("transactionTypeArtifacts").size());
        matrix.put("status", matrixInput.path("transactionTypeArtifacts").size() == oracle.expectedCodeMatrix().size() ? "PASS" : "FAIL");
        matrix.put("source", matrixFile.toString());

        ArrayNode findings = root.putArray("findings");
        addFinding(findings, "AI_OUTPUT_PENDING", "Actual AI-generated artifacts have not yet been supplied.", "PENDING");
        addFinding(findings, "EXTERNAL_CONVERTER_PENDING", "Run the real ATL105 converter against AI-generated JSON.", "PENDING");
        addFinding(findings, "EWIC_FLOW_PENDING", "eWIC Authorization to Completion requires the separate EBT/eWIC artifact set.", "PENDING");
        addFinding(findings, "SME_REVIEW_PENDING", "SME review remains outside this automated report.", "OUT_OF_SCOPE");
        root.put("overallStatus", "READY_FOR_AI_ARTIFACT_INTAKE");

        Path json = outputDirectory.resolve("segment-100-validation-evidence.json");
        Path markdown = outputDirectory.resolve("segment-100-validation-evidence.md");
        mapper.writerWithDefaultPrettyPrinter().writeValue(json.toFile(), root);
        Files.writeString(markdown, markdown(root));
        return new ReportFiles(json, markdown);
    }

    private void addThreeMessageTimeout(ArrayNode flows, Path directory) {
        ObjectNode item = flows.addObject();
        item.put("flow", "TIMEOUT_TOR_NEXT_TRANSACTION");
        ValidationResult result = multiStepValidator.validateThreeMessageFiles(
                Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL,
                directory.resolve("timeout-tor-original.synthetic.json"),
                directory.resolve("timeout-tor-reversal.synthetic.json"),
                directory.resolve("timeout-tor-next-transaction.synthetic.json"));
        item.put("positiveStatus", status(result));
        item.put("negativeMismatchDetected", false);
    }

    private void addTorRetryEvidence(ArrayNode flows, Path directory) {
        ObjectNode item = flows.addObject();
        item.put("flow", "TIMEOUT_TOR_RETRIES");
        ValidationResult result = multiStepValidator.validateTorAttempts(
                Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL,
                List.of(directory.resolve("timeout-tor-attempt-1.synthetic.json"),
                        directory.resolve("timeout-tor-attempt-2.synthetic.json"),
                        directory.resolve("timeout-tor-attempt-3.synthetic.json")));
        item.put("positiveStatus", status(result));
        item.put("negativeMismatchDetected", false);
    }

    private void addFlow(ArrayNode flows, String name, Segment100MultiStepCoverageValidator.Flow flow,
                         Path directory, String original, String validFollowUp, String mismatchFollowUp) {
        ObjectNode item = flows.addObject();
        item.put("flow", name);
        ValidationResult positive = multiStepValidator.validateFiles(flow, directory.resolve(original), directory.resolve(validFollowUp));
        item.put("positiveStatus", status(positive));
        if (mismatchFollowUp != null) {
            ValidationResult negative = multiStepValidator.validateFiles(flow, directory.resolve(original), directory.resolve(mismatchFollowUp));
            item.put("negativeMismatchDetected", !negative.isValid());
            item.put("negativeErrorCount", negative.errors().size());
        } else {
            item.put("negativeMismatchDetected", false);
            item.put("negativeErrorCount", 0);
        }
    }

    private static String status(ValidationResult result) { return result.isValid() ? "PASS" : "FAIL"; }

    private static int countJson(Path directory) throws IOException {
        if (!Files.isDirectory(directory)) return 0;
        try (var files = Files.list(directory)) {
            return (int) files.filter(path -> path.toString().endsWith(".json")).count();
        }
    }

    private static void addFinding(ArrayNode findings, String id, String message, String status) {
        ObjectNode finding = findings.addObject();
        finding.put("id", id);
        finding.put("message", message);
        finding.put("status", status);
    }

    private static String markdown(ObjectNode root) {
        StringBuilder text = new StringBuilder("# Segment 100 Validation Evidence\n\n");
        text.append("- Overall status: **").append(root.path("overallStatus").asText()).append("**\n");
        text.append("- Scope: ").append(root.path("scope").asText()).append("\n\n");
        text.append("## Transaction Codes\n\n");
        text.append("- Expected standard financial codes: ").append(root.path("transactionTypeCoverage").path("expectedCodes").asInt()).append("\n");
        text.append("- Single-step coverage: **").append(root.path("transactionTypeCoverage").path("singleStepStatus").asText()).append("**\n");
        text.append("- Converter preflight: **").append(root.path("converterPreflight").path("status").asText()).append("**\n\n");
        text.append("- Card-type coverage: **").append(root.path("cardTypeCoverage").path("status").asText()).append("** (")
            .append(root.path("cardTypeCoverage").path("expectedCardTypes").asInt()).append(" expected codes)\n\n");
        text.append("## Multi-Step Flows\n\n| Flow | Positive | Negative mismatch detected |\n| --- | --- | --- |\n");
        for (JsonNode flow : root.path("multiStepCoverage").path("flows")) {
            text.append("| ").append(flow.path("flow").asText()).append(" | ")
                    .append(flow.path("positiveStatus").asText()).append(" | ")
                    .append(flow.path("negativeMismatchDetected").asBoolean()).append(" |\n");
        }
        text.append("\n## Pending Gates\n\n");
        for (JsonNode finding : root.path("findings")) {
            text.append("- ").append(finding.path("status").asText()).append(": ")
                    .append(finding.path("message").asText()).append("\n");
        }
        return text.toString();
    }

    public record ReportFiles(Path json, Path markdown) { }
}
