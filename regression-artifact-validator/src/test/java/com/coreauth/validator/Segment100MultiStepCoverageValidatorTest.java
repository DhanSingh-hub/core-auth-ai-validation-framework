package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100MultiStepCoverageValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100MultiStepCoverageValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Segment100MultiStepCoverageValidator validator = new Segment100MultiStepCoverageValidator();

    @Test
    void acceptsAuthorizationCompletion() throws Exception {
        assertValid(Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION, "3", "0");
    }

    @Test
    void acceptsAuthorizationVoid() throws Exception {
        for (String authorizationCode : List.of("3", "5", "B")) {
            assertValid(Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_VOID, authorizationCode, "8");
        }
    }

    @Test
    void acceptsAuthorizationCancellation() throws Exception {
        assertValid(Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_CANCELLATION, "3", "S");
    }

    @Test
    void acceptsRefundVoidOfReturn() throws Exception {
        assertValid(Segment100MultiStepCoverageValidator.Flow.REFUND_VOID_OF_RETURN, "7", "U");
    }

    @Test
    void acceptsSaleVoid() throws Exception {
        assertValid(Segment100MultiStepCoverageValidator.Flow.SALE_VOID, "0", "8");
    }

    @Test
    void acceptsTimeoutReversal() throws Exception {
        assertValid(Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL, "0", "Z");
    }

    @Test
    void rejectsAuthorizationOnlyAsTimeoutReversalOriginal() throws Exception {
        for (String authorizationCode : List.of("3", "5", "B")) {
            Path directory = Files.createTempDirectory("segment-100-auth-only-tor-");
            Path original = write(directory.resolve("authorization.json"), authorizationCode, "100001");
            Path tor = write(directory.resolve("tor.json"), "Z", "100001");

            var result = validator.validateFiles(
                    Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL, original, tor);

            assertThat(result.errors()).anyMatch(error -> error.reason().contains("original transaction code"));
        }
    }

    @Test
    void rejectsSequenceMismatch() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-multistep-");
        Path original = write(directory.resolve("original.json"), "3", "100001");
        Path followUp = write(directory.resolve("completion.json"), "0", "100002");

        var result = validator.validateFiles(Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION, original, followUp);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("must reuse original SequenceNumber 100001"));
    }

    @Test
    void rejectsWrongFollowUpCodeForFlow() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-multistep-");
        Path original = write(directory.resolve("original.json"), "7", "100001");
        Path followUp = write(directory.resolve("followup.json"), "8", "100001");

        var result = validator.validateFiles(Segment100MultiStepCoverageValidator.Flow.REFUND_VOID_OF_RETURN, original, followUp);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("follow-up transaction code"));
    }

    @Test
    void rejectsMalformedSequenceInLifecyclePair() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-multistep-");
        Path original = write(directory.resolve("original.json"), "0", "10001");
        Path followUp = write(directory.resolve("void.json"), "8", "10001");

        var result = validator.validateFiles(Segment100MultiStepCoverageValidator.Flow.SALE_VOID, original, followUp);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SequenceNumber must be six digits"));
    }

    @Test
    void rejectsReversalWithoutApprovalNumber() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-multistep-");
        Path original = write(directory.resolve("original.json"), "0", "100001");
        Path followUp = write(directory.resolve("void.json"), "8", "100001");
        ObjectNode followUpPayload = (ObjectNode) MAPPER.readTree(followUp.toFile());
        ((ObjectNode) followUpPayload.path("Financial Request").path("Standard Segment")).remove("ApprovalNumber");
        MAPPER.writeValue(followUp.toFile(), followUpPayload);

        var result = validator.validateFiles(Segment100MultiStepCoverageValidator.Flow.SALE_VOID, original, followUp);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("follow-up requires ApprovalNumber"));
    }

    @Test
    void acceptsAuthorizationCompletionFromDifferentTerminal() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-multistep-");
        Path original = write(directory.resolve("original.json"), "3", "100001");
        Path followUp = write(directory.resolve("completion.json"), "0", "100001");
        ObjectNode followUpPayload = (ObjectNode) MAPPER.readTree(followUp.toFile());
        ((ObjectNode) followUpPayload.path("Financial Request").path("Standard Segment")).put("TerminalID", "OTHER001");
        MAPPER.writeValue(followUp.toFile(), followUpPayload);

        var result = validator.validateFiles(Segment100MultiStepCoverageValidator.Flow.AUTHORIZATION_COMPLETION, original, followUp);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsTimeoutReversalFromDifferentTerminal() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-timeout-");
        Path original = write(directory.resolve("original.json"), "0", "100001");
        Path tor = write(directory.resolve("tor.json"), "Z", "100001");
        ObjectNode torPayload = (ObjectNode) MAPPER.readTree(tor.toFile());
        ((ObjectNode) torPayload.path("Financial Request").path("Standard Segment")).put("TerminalID", "OTHER001");
        MAPPER.writeValue(tor.toFile(), torPayload);

        var result = validator.validateFiles(Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL, original, tor);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("must use the original TerminalID"));
    }

    @Test
    void acceptsCompletionBoundToActualAuthorizationResponse() throws Exception {
        ObjectNode authorization = writeRequest("3", "100001", "AUTH-TERM");
        ObjectNode completion = writeRequest("0", "100001", "COMPLETION-TERM");
        ((ObjectNode) completion.path("Financial Request").path("Standard Segment")).put("ApprovalNumber", "APR001");
        ObjectNode hostResponse = authorizationResponse("2", "100001", "APR001");

        var result = validator.validateAuthorizationCompletionWithHostResponse(authorization, hostResponse, completion);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsCompletionApprovalThatDoesNotMatchHostResponse() throws Exception {
        ObjectNode authorization = writeRequest("3", "100001", "TERM001");
        ObjectNode completion = writeRequest("0", "100001", "TERM001");
        ((ObjectNode) completion.path("Financial Request").path("Standard Segment")).put("ApprovalNumber", "APR999");

        var result = validator.validateAuthorizationCompletionWithHostResponse(
                authorization, authorizationResponse("2", "100001", "APR001"), completion);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("must equal the actual Authorization response ApprovalNumber"));
    }

    @Test
    void rejectsCompletionWhenHostAuthorizationHasNoApprovalNumber() throws Exception {
        ObjectNode authorization = writeRequest("3", "100001", "TERM001");
        ObjectNode completion = writeRequest("0", "100001", "TERM001");
        ((ObjectNode) completion.path("Financial Request").path("Standard Segment")).put("ApprovalNumber", "APR001");

        var result = validator.validateAuthorizationCompletionWithHostResponse(
                authorization, authorizationResponse("2", "100001", null), completion);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Actual Authorization response must contain"));
    }

    @Test
    void rejectsCompletionWhenAuthorizationResponseSequenceDiffers() throws Exception {
        ObjectNode authorization = writeRequest("3", "100001", "TERM001");
        ObjectNode completion = writeRequest("0", "100001", "TERM001");
        ((ObjectNode) completion.path("Financial Request").path("Standard Segment")).put("ApprovalNumber", "APR001");

        var result = validator.validateAuthorizationCompletionWithHostResponse(
                authorization, authorizationResponse("2", "100002", "APR001"), completion);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("host response SequenceNumber must match"));
    }

    @Test
    void acceptsThreeMessageTimeoutFlow() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-timeout-");
        Path original = write(directory.resolve("original.json"), "0", "100001");
        Path tor = write(directory.resolve("tor.json"), "Z", "100001");
        Path next = write(directory.resolve("next.json"), "0", "100002");

        var result = validator.validateThreeMessageFiles(
                Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL, original, tor, next);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsThreeMessageTimeoutWhenNextTransactionReusesSequence() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-timeout-");
        Path original = write(directory.resolve("original.json"), "0", "100001");
        Path tor = write(directory.resolve("tor.json"), "Z", "100001");
        Path next = write(directory.resolve("next.json"), "0", "100001");

        var result = validator.validateThreeMessageFiles(
                Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL, original, tor, next);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("must receive a new SequenceNumber"));
    }

    @Test
    void acceptsUpToThreeConsistentTorAttempts() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-tor-retries-");
        Path first = write(directory.resolve("tor-1.json"), "Z", "100001");
        Path second = write(directory.resolve("tor-2.json"), "Z", "100001");
        Path third = write(directory.resolve("tor-3.json"), "Z", "100001");

        var result = validator.validateTorAttempts(
                Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL, List.of(first, second, third));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsMoreThanThreeTorAttempts() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-tor-retries-");
        Path first = write(directory.resolve("tor-1.json"), "Z", "100001");
        Path second = write(directory.resolve("tor-2.json"), "Z", "100001");
        Path third = write(directory.resolve("tor-3.json"), "Z", "100001");
        Path fourth = write(directory.resolve("tor-4.json"), "Z", "100001");

        var result = validator.validateTorAttempts(
                Segment100MultiStepCoverageValidator.Flow.TIMEOUT_REVERSAL, List.of(first, second, third, fourth));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("one to three attempt files"));
    }

    private static void assertValid(Segment100MultiStepCoverageValidator.Flow flow,
                                    String originalCode, String followUpCode) throws Exception {
        Path directory = Files.createTempDirectory("segment-100-multistep-");
        Path original = write(directory.resolve("original.json"), originalCode, "100001");
        Path followUp = write(directory.resolve("followup.json"), followUpCode, "100001");

        var result = new Segment100MultiStepCoverageValidator().validateFiles(flow, original, followUp);

        assertThat(result.errors()).as(flow.name()).isEmpty();
    }

    private static Path write(Path path, String transactionType, String sequenceNumber) throws Exception {
        MAPPER.writeValue(path.toFile(), writeRequest(transactionType, sequenceNumber, "TERM001"));
        return path;
    }

    private static ObjectNode writeRequest(String transactionType, String sequenceNumber, String terminalId) {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.put("NumSegments", "01");
        ObjectNode segment = request.putObject("Standard Segment");
        segment.put("SegmentType", "100");
        segment.put("TerminalID", terminalId);
        segment.put("SequenceNumber", sequenceNumber);
        segment.put("ApprovalNumber", "APR001");
        segment.put("TrackData", "TRACK-001");
        segment.put("NonFuelAmount", "00001000");
        segment.putObject("PromptCode").put("TransactionType", transactionType);
        return payload;
    }

    private static ObjectNode authorizationResponse(String responseCode, String sequenceNumber, String approvalNumber) {
        ObjectNode artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("expectedTransactionType", "3");
        ObjectNode response = artifact.putObject("Financial Response");
        response.put("MessageType", "ATL105");
        response.put("ResponseCode", responseCode);
        response.put("SequenceNumber", sequenceNumber);
        if (approvalNumber != null) response.put("ApprovalNumber", approvalNumber);
        response.put("AuthorizerResponseCode", "00");
        return artifact;
    }
}
