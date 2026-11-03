package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;

/** Applies a source-anchored Test Model lifecycle case to a supplied AI request pair. */
public final class Segment100LifecycleArtifactReview {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String LIFECYCLE_BR = "BR-SEG100-E86-002";

    private Segment100LifecycleArtifactReview() { }

    public static void main(String[] args) throws Exception {
        if (args.length != 6) {
            throw new IllegalArgumentException("Usage: Segment100LifecycleArtifactReview <test-model-package.json> "
                    + "<test-case-id> <flow> <original.json> <follow-up.json> <new-report.json>");
        }
        Path testModel = Path.of(args[0]);
        String testCaseId = args[1];
        Segment100MultiStepCoverageValidator.Flow flow = Segment100MultiStepCoverageValidator.Flow.valueOf(args[2]);
        Path original = Path.of(args[3]);
        Path followUp = Path.of(args[4]);
        Path output = Path.of(args[5]);
        if (output.toAbsolutePath().normalize().toFile().exists()) {
            throw new IOException("Refusing to overwrite review evidence: " + output);
        }
        ObjectNode report = evaluate(testModel, testCaseId, flow, original, followUp);
        Files.createDirectories(output.toAbsolutePath().normalize().getParent());
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(report));
    }

    public static ObjectNode evaluate(Path testModelPath, String testCaseId,
                                      Segment100MultiStepCoverageValidator.Flow flow,
                                      Path originalPath, Path followUpPath) throws Exception {
        Path model = testModelPath.toRealPath();
        Path originalFile = originalPath.toRealPath();
        Path followUpFile = followUpPath.toRealPath();
        JsonNode testModel = MAPPER.readTree(model.toFile());
        JsonNode testCase = findById(testModel.path("testCases"), testCaseId);
        if (testCase == null) throw new IllegalArgumentException("Test Case not found in model: " + testCaseId);
        String expectedOutcome = testCase.path("expectedOutcome").asText("");
        if (!"FAIL".equals(expectedOutcome)) {
            throw new IllegalArgumentException("Selected Test Case must expect FAIL for a negative-pair review: " + testCaseId);
        }
        JsonNode scenario = findLinkedScenario(testModel, testCase);
        JsonNode requirement = scenario == null ? null : findById(testModel.path("businessRequirements"), LIFECYCLE_BR);
        if (scenario == null || !contains(scenario.path("requirementIds"), LIFECYCLE_BR) || requirement == null) {
            throw new IllegalArgumentException("Selected Test Case is not linked to " + LIFECYCLE_BR);
        }
        JsonNode testData = findTestData(testModel, testCaseId);
        if (testData == null || !"FAIL".equals(testData.path("expectedValidation").asText())) {
            throw new IllegalArgumentException("Selected Test Case lacks a matching negative Test Data oracle: " + testCaseId);
        }

        JsonNode originalPayload = MAPPER.readTree(originalFile.toFile());
        JsonNode followUpPayload = MAPPER.readTree(followUpFile.toFile());
        Segment100MultiStepCoverageValidator lifecycle = new Segment100MultiStepCoverageValidator();
        ValidationResult lifecycleResult = lifecycle.validateFiles(flow, originalFile, followUpFile);
        Segment100CardTypeOracle cardTypes = new Segment100CardTypeOracle();
        ValidationResult originalCardType = cardTypes.validateAiPayload(originalFile.getFileName().toString(), originalPayload);
        ValidationResult followUpCardType = cardTypes.validateAiPayload(followUpFile.getFileName().toString(), followUpPayload);

        String sequenceReason = flow + " follow-up must reuse original SequenceNumber";
        boolean targetMismatchObserved = lifecycleResult.errors().stream()
                .anyMatch(error -> error.reason().contains("follow-up must reuse original SequenceNumber"));
        boolean promptAndCardTypesValid = originalCardType.isValid() && followUpCardType.isValid();
        boolean testCaseExpectationMatched = "FAIL".equals(expectedOutcome) && targetMismatchObserved
                && promptAndCardTypesValid;

        ObjectNode report = MAPPER.createObjectNode();
        report.put("artifact", "ATL105-SEG100-AI-LIFECYCLE-ARTIFACT-REVIEW");
        report.put("status", testCaseExpectationMatched
                ? "TEST_CASE_EXPECTATION_MATCHED_AI_PAIR_REJECTED"
                : "TEST_CASE_EXPECTATION_NOT_MATCHED_REVIEW_REQUIRED");
        report.put("executionCertified", false);
        report.put("testCaseExpectationMatched", testCaseExpectationMatched);
        report.put("aiArtifactConformance", lifecycleResult.isValid() ? "PASS" : "FAIL");
        report.put("networkStatus", "NOT_DECLARED_NOT_INFERRED");

        ObjectNode modelEvidence = report.putObject("testModelEvidence");
        modelEvidence.put("testModelPath", model.toString());
        modelEvidence.put("testModelSha256", sha256(model));
        modelEvidence.put("businessRequirementId", LIFECYCLE_BR);
        modelEvidence.put("businessRequirementTitle", requirement.path("title").asText());
        modelEvidence.set("businessRequirementSourceAnchors", requirement.path("sourceAnchors").deepCopy());
        modelEvidence.put("scenarioId", scenario.path("id").asText());
        modelEvidence.put("testCaseId", testCaseId);
        modelEvidence.put("expectedOutcome", expectedOutcome);
        modelEvidence.put("testDataId", testData.path("id").asText());
        modelEvidence.put("expectedValidation", testData.path("expectedValidation").asText());

        ArrayNode inputArtifacts = report.putArray("aiInputArtifacts");
        addInput(inputArtifacts, originalFile, originalPayload);
        addInput(inputArtifacts, followUpFile, followUpPayload);
        report.set("cardTypeOracle", MAPPER.createObjectNode()
                .put("originalStatus", originalCardType.isValid() ? "PASS" : "FAIL")
                .put("followUpStatus", followUpCardType.isValid() ? "PASS" : "FAIL"));
        ArrayNode errors = report.putArray("lifecycleValidationErrors");
        lifecycleResult.errors().forEach(error -> errors.add(error.reason()));
        ArrayNode cardErrors = report.putArray("cardTypeValidationErrors");
        originalCardType.errors().forEach(error -> cardErrors.add(error.reason()));
        followUpCardType.errors().forEach(error -> cardErrors.add(error.reason()));
        report.put("targetSequenceMismatchObserved", targetMismatchObserved);
        report.put("targetedRule", sequenceReason);
        report.set("observedRequestFields", requestSummaries(originalPayload, followUpPayload));
        report.set("limitations", MAPPER.valueToTree(new String[]{
                "Offline request validation only; no host, issuer, or settlement response was executed.",
                "The AI request files are hashed and summarized; account and Track Data values are not copied into this report.",
                "No Visa/network identity is asserted from Track Data or the generic Credit card type.",
                "A matching negative Test Case means the validator detected the stated sequence mismatch, not that every business assertion was tested."
        }));
        return report;
    }

    private static void addInput(ArrayNode inputs, Path path, JsonNode payload) throws Exception {
        ObjectNode input = inputs.addObject();
        input.put("path", path.toString());
        input.put("sha256", sha256(path));
        JsonNode segment = payload.path("Financial Request").path("Standard Segment");
        input.put("transactionType", segment.path("PromptCode").path("TransactionType").asText(""));
        input.put("cardType", segment.path("PromptCode").path("CardType").asText(""));
        input.put("sequenceNumber", segment.path("SequenceNumber").asText(""));
        input.put("terminalId", segment.path("TerminalID").asText(""));
        input.put("accountNumberFieldPresent", segment.hasNonNull("AccountNumber"));
        input.put("trackDataIncluded", segment.hasNonNull("TrackData"));
    }

    private static ArrayNode requestSummaries(JsonNode original, JsonNode followUp) {
        ArrayNode result = MAPPER.createArrayNode();
        for (JsonNode payload : new JsonNode[]{original, followUp}) {
            JsonNode segment = payload.path("Financial Request").path("Standard Segment");
            result.addObject()
                    .put("transactionType", segment.path("PromptCode").path("TransactionType").asText(""))
                    .put("cardType", segment.path("PromptCode").path("CardType").asText(""))
                    .put("sequenceNumber", segment.path("SequenceNumber").asText(""))
                    .put("terminalId", segment.path("TerminalID").asText(""));
        }
        return result;
    }

    private static JsonNode findById(JsonNode rows, String id) {
        if (!rows.isArray()) return null;
        for (JsonNode row : rows) if (id.equals(row.path("id").asText())) return row;
        return null;
    }

    private static JsonNode findLinkedScenario(JsonNode testModel, JsonNode testCase) {
        JsonNode scenarioIds = testCase.path("scenarioIds");
        if (!scenarioIds.isArray()) return null;
        for (JsonNode scenarioId : scenarioIds) {
            JsonNode scenario = findById(testModel.path("testScenarios"), scenarioId.asText());
            if (scenario != null) return scenario;
        }
        return null;
    }

    private static JsonNode findTestData(JsonNode testModel, String testCaseId) {
        JsonNode rows = testModel.path("testData");
        if (!rows.isArray()) return null;
        for (JsonNode row : rows) if (contains(row.path("testCaseIds"), testCaseId)) return row;
        return null;
    }

    private static boolean contains(JsonNode array, String value) {
        if (!array.isArray()) return false;
        for (JsonNode row : array) if (value.equals(row.asText())) return true;
        return false;
    }

    private static String sha256(Path path) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
    }
}