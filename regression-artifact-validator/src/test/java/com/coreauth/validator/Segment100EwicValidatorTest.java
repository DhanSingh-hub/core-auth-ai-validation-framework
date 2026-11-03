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
        ObjectNode payload = payload("0", "100001", "TERM001");
        ObjectNode request = (ObjectNode) payload.path("Financial Request");
        request.remove("EBT Data Segment");
        request.put("NumSegments", "01");

        var result = new Segment100EwicValidator().validateRequest("MISSING-103", payload, "VOUCHER_CLEAR");

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires the EBT Data Segment"));
    }

    @Test
    void acceptsOptionalEwicOperationsWithoutSegment103() {
        Segment100EwicValidator validator = new Segment100EwicValidator();
        String[][] cases = {
                {"AUTHORIZATION", "3"},
                {"CANCELLATION", "S"},
                {"BALANCE_INQUIRY", "E"},
                {"REVERSAL_VOID", "8"}
        };
        for (String[] candidate : cases) {
            ObjectNode payload = payload(candidate[1], "100001", "TERM001");
            ObjectNode request = (ObjectNode) payload.path("Financial Request");
            request.remove("EBT Data Segment");
            request.put("NumSegments", "01");

            assertThat(validator.validateRequest(candidate[0], payload, candidate[0]).errors())
                    .as(candidate[0]).isEmpty();
        }
    }

    @Test
    void rejectsNumSegmentsThatDoesNotMatchIncludedEwicSegments() {
        ObjectNode absentSegment = payload("3", "100001", "TERM001");
        ObjectNode absentRequest = (ObjectNode) absentSegment.path("Financial Request");
        absentRequest.remove("EBT Data Segment");
        absentRequest.put("NumSegments", "02");
        assertThat(new Segment100EwicValidator().validateRequest("COUNT-WITHOUT-103", absentSegment, "AUTHORIZATION").errors())
                .anyMatch(error -> error.reason().contains("segment-object count 01"));

        ObjectNode includedSegment = payload("3", "100001", "TERM001");
        ((ObjectNode) includedSegment.path("Financial Request")).put("NumSegments", "01");
        assertThat(new Segment100EwicValidator().validateRequest("COUNT-WITH-103", includedSegment, "AUTHORIZATION").errors())
                .anyMatch(error -> error.reason().contains("segment-object count 02"));
    }

    @Test
    void countsAnAdditionalBuypassSegmentInNumSegments() {
        ObjectNode payload = payload("3", "100001", "TERM001");
        ObjectNode request = (ObjectNode) payload.path("Financial Request");
        request.putObject("Variable Information Data Segment").put("SegmentType", "111");
        request.put("NumSegments", "03");

        assertThat(new Segment100EwicValidator().validateRequest("COUNT-WITH-111", payload, "AUTHORIZATION").errors())
                .isEmpty();
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
