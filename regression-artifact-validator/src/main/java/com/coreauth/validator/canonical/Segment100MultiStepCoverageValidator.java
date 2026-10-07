package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/** Validates original/follow-up AI JSON pairs for Segment 100 lifecycle flows. */
public final class Segment100MultiStepCoverageValidator {
    public enum Flow {
        AUTHORIZATION_COMPLETION(Set.of("3", "5", "B"), Set.of("0")),
        AUTHORIZATION_CANCELLATION(Set.of("3", "5", "B"), Set.of("S")),
        AUTHORIZATION_VOID(Set.of("3", "5", "B"), Set.of("8")),
        REFUND_VOID_OF_RETURN(Set.of("7"), Set.of("U")),
        SALE_VOID(Set.of("0", "4", "6"), Set.of("8", "C")),
        TIMEOUT_REVERSAL(Set.of("0", "3", "4", "5", "6", "B"), Set.of("Z"));

        private final Set<String> originalCodes;
        private final Set<String> followUpCodes;

        Flow(Set<String> originalCodes, Set<String> followUpCodes) {
            this.originalCodes = originalCodes;
            this.followUpCodes = followUpCodes;
        }
    }

    private final ObjectMapper objectMapper;
    private final Segment100TransactionTypeOracle oracle;

    public Segment100MultiStepCoverageValidator() {
        this(new ObjectMapper(), new Segment100TransactionTypeOracle());
    }

    Segment100MultiStepCoverageValidator(ObjectMapper objectMapper, Segment100TransactionTypeOracle oracle) {
        this.objectMapper = objectMapper;
        this.oracle = oracle;
    }

    public ValidationResult validateFiles(Flow flow, Path originalFile, Path followUpFile) {
        ValidationResult result = new ValidationResult(flow.name());
        if (flow == null || originalFile == null || followUpFile == null) {
            result.addError("Segment100MultiStepCoverage", "Flow and both AI JSON files are required");
            return result;
        }
        try {
            JsonNode original = objectMapper.readTree(originalFile.toFile());
            JsonNode followUp = objectMapper.readTree(followUpFile.toFile());
            validatePair(flow, originalFile.getFileName().toString(), original, followUpFile.getFileName().toString(), followUp, result);
        } catch (IOException exception) {
            result.addError("Segment100MultiStepCoverage", "Unable to read lifecycle JSON: " + exception.getMessage());
        }
        return result;
    }

    public ValidationResult validateThreeMessageFiles(Flow flow, Path originalFile, Path torFile,
                                                      Path nextTransactionFile) {
        ValidationResult result = new ValidationResult(flow == null ? "three-message-lifecycle" : flow.name());
        if (flow != Flow.TIMEOUT_REVERSAL || originalFile == null || torFile == null || nextTransactionFile == null) {
            result.addError("Segment100MultiStepCoverage", "Timeout flow and three JSON files are required");
            return result;
        }
        try {
            JsonNode original = objectMapper.readTree(originalFile.toFile());
            JsonNode tor = objectMapper.readTree(torFile.toFile());
            JsonNode next = objectMapper.readTree(nextTransactionFile.toFile());
            validatePair(flow, originalFile.getFileName().toString(), original, torFile.getFileName().toString(), tor, result);
            result.merge(oracle.validateAiPayload(nextTransactionFile.getFileName().toString(), next));
            if (!result.isValid()) return result;
            JsonNode originalSegment = segment(original);
            JsonNode torSegment = segment(tor);
            JsonNode nextSegment = segment(next);
            String originalSequence = originalSegment.path("SequenceNumber").asText(null);
            String torSequence = torSegment.path("SequenceNumber").asText(null);
            String nextSequence = nextSegment.path("SequenceNumber").asText(null);
            if (!originalSequence.equals(torSequence)) {
                result.addError("Segment100MultiStepCoverage", "TOR must reuse original SequenceNumber");
            }
            if (originalSequence.equals(nextSequence)) {
                result.addError("Segment100MultiStepCoverage", "Next financial transaction must receive a new SequenceNumber");
            }
            if (!sameIdentity(originalSegment, torSegment)) {
                result.addError("Segment100MultiStepCoverage", "TOR must preserve original account and amount identity");
            }
            if (!sameTerminal(torSegment, nextSegment)) {
                result.addError("Segment100MultiStepCoverage", "Next transaction must use the same originating TerminalID");
            }
        } catch (IOException exception) {
            result.addError("Segment100MultiStepCoverage", "Unable to read three-message lifecycle JSON: " + exception.getMessage());
        }
        return result;
    }

    public ValidationResult validateTorAttempts(Flow flow, java.util.List<Path> attemptFiles) {
        ValidationResult result = new ValidationResult("tor-attempts");
        if (flow != Flow.TIMEOUT_REVERSAL || attemptFiles == null || attemptFiles.isEmpty() || attemptFiles.size() > 3) {
            result.addError("Segment100MultiStepCoverage", "TOR requires one to three attempt files");
            return result;
        }
        JsonNode first = null;
        for (Path attemptFile : attemptFiles) {
            try {
                JsonNode attempt = objectMapper.readTree(attemptFile.toFile());
                result.merge(oracle.validateAiPayload(attemptFile.getFileName().toString(), attempt));
                if (first == null) {
                    first = attempt;
                } else if (!sameIdentity(segment(first), segment(attempt))) {
                    result.addError("Segment100MultiStepCoverage", "TOR retry must preserve original account, amount, and sequence identity");
                }
                if (!"Z".equals(transactionCode(segment(attempt)))) {
                    result.addError("Segment100MultiStepCoverage", "Every TOR attempt must use transaction code Z");
                }
            } catch (IOException exception) {
                result.addError("Segment100MultiStepCoverage", "Unable to read TOR attempt JSON: " + exception.getMessage());
            }
        }
        return result;
    }

    private void validatePair(Flow flow, String originalId, JsonNode original, String followUpId,
                              JsonNode followUp, ValidationResult result) {
        result.merge(oracle.validateAiPayload(originalId, original));
        result.merge(oracle.validateAiPayload(followUpId, followUp));
        if (!result.isValid()) {
            return;
        }

        JsonNode originalSegment = segment(original);
        JsonNode followUpSegment = segment(followUp);
        String originalCode = transactionCode(originalSegment);
        String followUpCode = transactionCode(followUpSegment);
        if (!flow.originalCodes.contains(originalCode)) {
            result.addError("Segment100MultiStepCoverage", flow + " original transaction code must be one of " + flow.originalCodes + ", got " + originalCode);
        }
        if (!flow.followUpCodes.contains(followUpCode)) {
            result.addError("Segment100MultiStepCoverage", flow + " follow-up transaction code must be one of " + flow.followUpCodes + ", got " + followUpCode);
        }

        String originalSequence = originalSegment.path("SequenceNumber").asText(null);
        String followUpSequence = followUpSegment.path("SequenceNumber").asText(null);
        if (originalSequence == null || !originalSequence.matches("^[0-9]{6}$")) {
            result.addError("Segment100MultiStepCoverage", originalId + " SequenceNumber must be six digits");
        }
        if (followUpSequence == null || !followUpSequence.matches("^[0-9]{6}$")) {
            result.addError("Segment100MultiStepCoverage", followUpId + " SequenceNumber must be six digits");
        }
        if (originalSequence != null && followUpSequence != null && !originalSequence.equals(followUpSequence)) {
            result.addError("Segment100MultiStepCoverage", flow + " follow-up must reuse original SequenceNumber " + originalSequence);
        }
        String originalTerminal = originalSegment.path("TerminalID").asText(null);
        String followUpTerminal = followUpSegment.path("TerminalID").asText(null);
        if (originalTerminal != null && followUpTerminal != null && !originalTerminal.equals(followUpTerminal)) {
            result.addError("Segment100MultiStepCoverage", flow + " follow-up must use the original TerminalID");
        }
        // ATL105 13-28 (Element 5): reversal-family follow-ups (Purchase Reversal "8"/"C",
        // Authorization Only Cancellation "S", and Void of a Merchandise Return "U") must
        // carry the original transaction's Approval Number, identical to the original.
        if (flow.followUpCodes.contains("8") || flow.followUpCodes.contains("C")
                || flow.followUpCodes.contains("S") || flow.followUpCodes.contains("U")) {
            String approvalNumber = followUpSegment.path("ApprovalNumber").asText("");
            if (approvalNumber.isBlank()) {
                result.addError("Segment100MultiStepCoverage", flow + " follow-up requires ApprovalNumber");
            }
        }
    }

    private static JsonNode segment(JsonNode payload) {
        return payload.path("Financial Request").path("Standard Segment");
    }

    private static String transactionCode(JsonNode segment) {
        return segment.path("PromptCode").path("TransactionType").asText(null);
    }

    private static boolean sameIdentity(JsonNode left, JsonNode right) {
        return left.path("SequenceNumber").asText(null).equals(right.path("SequenceNumber").asText(null))
                && value(left, "TrackData").equals(value(right, "TrackData"))
                && value(left, "NonFuelAmount").equals(value(right, "NonFuelAmount"));
    }

    private static boolean sameTerminal(JsonNode left, JsonNode right) {
        return value(left, "TerminalID").equals(value(right, "TerminalID"));
    }

    private static String value(JsonNode node, String field) {
        return node.path(field).asText("");
    }
}
