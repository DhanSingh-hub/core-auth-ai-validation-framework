package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100EwicValidator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100EwicValidatorTest {
    @Test
    void acceptsAllDocumentedEwicPromptOperations() {
        Segment100EwicValidator validator = new Segment100EwicValidator();
        for (String operation : new String[]{"AUTHORIZATION", "CANCELLATION", "BALANCE_INQUIRY", "COMPLETION", "VOUCHER_CLEAR", "REVERSAL_VOID"}) {
            String code = switch (operation) {
                case "AUTHORIZATION" -> "3";
                case "CANCELLATION" -> "S";
                case "BALANCE_INQUIRY" -> "E";
                case "COMPLETION", "VOUCHER_CLEAR" -> "0";
                default -> "8";
            };
            assertThat(validator.validateRequest(operation, payload(code, "100001", "TERM001"), operation).errors())
                    .as(operation).isEmpty();
        }
    }

    @Test
    void acceptsAuthorizationToCompletionLifecycle() {
        Segment100EwicValidator validator = new Segment100EwicValidator();
        var result = validator.validateLifecycle("EWIC-001",
                payload("3", "100001", "TERM001"), payload("0", "100001", "TERM001"));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsMissingSegment103() {
        ObjectNode payload = payload("3", "100001", "TERM001");
        ((ObjectNode) payload.path("Financial Request")).remove("EBT Data Segment");

        var result = new Segment100EwicValidator().validateRequest("MISSING-103", payload, "AUTHORIZATION");

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires Segment 103"));
    }

    @Test
    void rejectsWrongCardTypeAndPromptCode() {
        var result = new Segment100EwicValidator().validateRequest("WRONG-EWIC", payload("3", "100001", "TERM001", "020"), "AUTHORIZATION");

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires eWIC Prompt Code"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Card Type must be 086"));
    }

    @Test
    void rejectsCompletionSequenceMismatch() {
        var result = new Segment100EwicValidator().validateLifecycle("EWIC-MISMATCH",
                payload("3", "100001", "TERM001"), payload("0", "100002", "TERM001"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("must reuse authorization SequenceNumber"));
    }

    @Test
    void rejectsEwicReturnPrompt() {
        var result = new Segment100EwicValidator().validateRequest("EWIC-RETURN", payload("7", "100001", "TERM001"), "COMPLETION");

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires eWIC Prompt Code"));
    }

    private static ObjectNode payload(String transactionType, String sequence, String terminal) {
        return payload(transactionType, sequence, terminal, "086");
    }

    private static ObjectNode payload(String transactionType, String sequence, String terminal, String cardType) {
        ObjectNode payload = Segment100EwicValidator.objectMapper().createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        request.put("MessageType", "ATL105").put("NumSegments", "02");
        ObjectNode standard = request.putObject("Standard Segment");
        standard.put("SegmentType", "100").put("TerminalID", terminal).put("SequenceNumber", sequence);
        standard.putObject("PromptCode").put("TransactionType", transactionType).put("CardType", cardType);
        request.putObject("EBT Data Segment").put("SegmentType", "103").put("SegmentLength", "010")
                .put("WICDiscountAmount", "").put("WICProductData", "WIC-DATA").put("EBTProgramData", "");
        return payload;
    }
}
