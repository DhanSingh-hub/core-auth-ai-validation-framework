package com.coreauth.validator.canonical;

/** Bounded ATL105 Appendix I-30 predicate for the PAR request indicator. */
public final class AppendixIParRequestOracle {
    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED
    }

    public record Assessment(Status status, String assertionScope, String reason) {
    }

    private static final String TABLE_ID = "032";
    private static final String SUB_TABLE_ID = "14";
    private static final String SUB_TABLE_LENGTH = "001";
    private static final String REQUEST_VALUE = "Y";

    public Assessment assess(String tableId, String subTableId, String subTableLength, String value) {
        if (tableId == null && subTableId == null && subTableLength == null && value == null) {
            return assessment(Status.REVIEW_REQUIRED, "PAR_REQUEST_OPT_IN",
                    "No PAR request indicator was supplied; merchant opt-in context is not available.");
        }
        if (!TABLE_ID.equals(tableId)) {
            return assessment(Status.FAIL, "PAR_REQUEST_REPRESENTATION",
                    "PAR request indicator must be in Variable Information Table 032.");
        }
        if (!SUB_TABLE_ID.equals(subTableId)) {
            return assessment(Status.FAIL, "PAR_REQUEST_REPRESENTATION",
                    "PAR request indicator must use Sub-Table ID 14.");
        }
        if (!SUB_TABLE_LENGTH.equals(subTableLength)) {
            return assessment(Status.FAIL, "PAR_REQUEST_REPRESENTATION",
                    "PAR request indicator Sub-Table Length must be 001.");
        }
        if (!REQUEST_VALUE.equals(value)) {
            return assessment(Status.FAIL, "PAR_REQUEST_REPRESENTATION",
                    "PAR request indicator value must be Y.");
        }
        return assessment(Status.PASS, "PAR_REQUEST_REPRESENTATION",
                "The PAR request indicator matches the ATL105 Table 032/Sub-Table 14 representation.");
    }

    private static Assessment assessment(Status status, String scope, String reason) {
        return new Assessment(status, scope, reason);
    }
}
