package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100ResponseCodeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100ResponseCodeValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void acceptsAllSevenStandardFinancialResponseCodes() {
        Segment100ResponseCodeValidator validator = new Segment100ResponseCodeValidator();
        for (String code : new String[]{"0", "1", "2", "3", "4", "F", "S"}) {
            ObjectNode response = response(code, "2".equals(code) || "3".equals(code) ? "3" : "0");
            if ("1".equals(code) || "S".equals(code)) ((ObjectNode) response.path("Financial Response")).put("DeclineCode", "05");
            if ("F".equals(code)) ((ObjectNode) response.path("Financial Response")).put("ApprovedAmount", "000001000");
            assertThat(validator.validate("RC-" + code, response).errors()).as(code).isEmpty();
        }
    }

    @Test
    void acceptsAuthorizationResponseCodesForAuthorizationTransaction() {
        var validator = new Segment100ResponseCodeValidator();
        assertThat(validator.validate("AUTH-2", response("2", "3")).errors()).isEmpty();
        assertThat(validator.validate("AUTH-3", response("3", "3")).errors()).isEmpty();
    }

    @Test
    void rejectsUnknownResponseCode() {
        var result = new Segment100ResponseCodeValidator().validate("UNKNOWN", response("A", "0"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Unsupported standard financial ResponseCode"));
    }

    @Test
    void rejectsPurchaseApprovalForAuthorizationTransaction() {
        var result = new Segment100ResponseCodeValidator().validate("WRONG-CONTEXT", response("0", "3"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("incompatible with transaction type"));
    }

    @Test
    void rejectsPartialApprovalWithoutApprovedAmount() {
        var result = new Segment100ResponseCodeValidator().validate("PARTIAL", response("F", "0"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires nine-digit ApprovedAmount"));
    }

    @Test
    void rejectsDeclineWithoutDeclineCode() {
        var result = new Segment100ResponseCodeValidator().validate("DECLINE", response("1", "0"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires two-character DeclineCode"));
    }

    @Test
    void rejectsApprovedResponseWithoutApprovalNumber() {
        ObjectNode response = response("0", "0");
        ((ObjectNode) response.path("Financial Response")).remove("ApprovalNumber");

        var result = new Segment100ResponseCodeValidator().validate("NO-APPROVAL", response);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("requires ApprovalNumber"));
    }

    private static ObjectNode response(String code, String transactionType) {
        ObjectNode artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("expectedTransactionType", transactionType);
        ObjectNode response = artifact.putObject("Financial Response");
        response.put("MessageType", "ATL105");
        response.put("ResponseCode", code);
        response.put("SequenceNumber", "100001");
        response.put("ApprovalNumber", "APR001");
        response.put("AuthorizerResponseCode", "00");
        return artifact;
    }
}
