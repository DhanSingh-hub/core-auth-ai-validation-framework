package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;

/**
 * Independent, bounded checks for the two Download Data table layouts in ATL105 Appendix W.
 *
 * <p>The encoded input contains consecutive 3-digit Table ID, 3-digit Table Length, and Table Data
 * records. This oracle validates the table values and lengths described by Appendix W, but does
 * not validate the enclosing DL7 Segment Length because its counting convention remains unresolved.
 * Appendix W's {@code an1} Table Data attributes conflict with its 3-character language code and
 * 13-character postal-code descriptions; that source conflict is reported separately as
 * {@link Status#REVIEW_REQUIRED}.
 */
public final class AppendixWDownloadDataOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    private record Table(String id, String length, String data) {
    }

    private record ParseResult(List<Table> tables, String error, boolean reviewRequired) {
    }

    private record CheckResult(boolean passed, String reason) {
    }

    public static final String BR_TABLE_FRAMING = "BR-SEG100-APPW-TABLE-FRAMING";
    public static final String BR_LANGUAGE_CODE = "BR-SEG100-APPW-LANGUAGE-CODE";
    public static final String BR_POSTAL_CODE = "BR-SEG100-APPW-POSTAL-CODE";
    public static final String BR_SOURCE_CONFLICT = "BR-SEG100-APPW-SOURCE-CONFLICT";
    public static final String BR_VALUE_ASSOCIATION = "BR-SEG100-APPW-VALUE-ASSOCIATION";

    private static final int MAX_DOWNLOAD_DATA_LENGTH = 100;

    /**
     * Assesses Appendix W tables carried as Download Data (Element 232).
     *
     * <p>Each of tables 001 and 002 is optional here: Appendix W defines their layouts but does not
     * say they must both appear in a single Download Data value. Unknown tables, malformed records,
     * and a value containing no records fail the framing/layout assessment.
     *
     * @param encodedTables concatenated Table ID + Table Length + Table Data records
     * @return framing, per-table, and source-ambiguity assessments
     */
    public List<Assessment> assessDownloadData(String encodedTables) {
        ParseResult parsed = parse(encodedTables);
        List<Assessment> assessments = new ArrayList<>();
        CheckResult framing = assessTableFraming(parsed);
        assessments.add(new Assessment(BR_TABLE_FRAMING,
                parsed.reviewRequired() ? Status.REVIEW_REQUIRED : framing.passed() ? Status.PASS : Status.FAIL,
                "appendix-w-table-framing", framing.reason()));
        assessments.add(assessTable(parsed, "001", BR_LANGUAGE_CODE));
        assessments.add(assessTable(parsed, "002", BR_POSTAL_CODE));
        assessments.add(new Assessment(BR_SOURCE_CONFLICT, Status.REVIEW_REQUIRED, "source-attribute-conflict",
                "Appendix W specifies Table Data as an1 for both tables, while assigning Table Length 003 "
                        + "and describing an ISO 639-2 language code for Table 001, and assigning Table "
                        + "Length 013 and describing a 13-character international postal code for Table 002. "
                        + "A literal one-alphanumeric-character reading of an1 conflicts with the fixed "
                        + "lengths and prose descriptions. The source does not resolve which reading governs; "
                        + "source-owner confirmation is required."));
        assessments.add(new Assessment(BR_VALUE_ASSOCIATION, Status.REVIEW_REQUIRED, "descriptive-value-association",
                "Appendix W describes the language as associated with the terminal and the postal code as "
                        + "associated with the transmitting device. The Download Data value alone does not "
                        + "identify or verify either external association."));
        return List.copyOf(assessments);
    }

    private static CheckResult assessTableFraming(ParseResult parsed) {
        if (parsed.error() != null) {
            return new CheckResult(false, parsed.error());
        }
        for (Table table : parsed.tables()) {
            if (!"001".equals(table.id()) && !"002".equals(table.id())) {
                return new CheckResult(false, "Table ID '" + table.id()
                        + "' is not defined by Appendix W.");
            }
            CheckResult tableCheck = validateTable(table);
            if (!tableCheck.passed()) {
                return tableCheck;
            }
        }
        return new CheckResult(true, "Download Data is fully framed as known Appendix W records. The source "
                + "does not require both tables, prescribe an order, or prohibit repeated table IDs.");
    }

    private static Assessment assessTable(ParseResult parsed, String tableId, String businessRequirementId) {
        if (parsed.error() != null) {
            return new Assessment(businessRequirementId,
                    parsed.reviewRequired() ? Status.REVIEW_REQUIRED : Status.FAIL, tableId.equals("001")
                    ? "site-language-table" : "postal-code-table", parsed.error());
        }
        boolean found = false;
        for (Table table : parsed.tables()) {
            if (tableId.equals(table.id())) {
                found = true;
                CheckResult result = validateTable(table);
                if (!result.passed()) {
                    return new Assessment(businessRequirementId, Status.FAIL, tableId.equals("001")
                            ? "site-language-table" : "postal-code-table", result.reason());
                }
            }
        }
        if (!found) {
            return new Assessment(businessRequirementId, Status.PASS, tableId.equals("001")
                    ? "site-language-table" : "postal-code-table",
                    "Table " + tableId + " is absent; Appendix W defines its layout but does not state that "
                            + "it is mandatory in every Download Data value.");
        }
        String description = tableId.equals("001")
                ? "ISO 639-2 language code associated with this terminal"
                : "13-character international postal code associated with the transmitting device";
        return new Assessment(businessRequirementId, Status.REVIEW_REQUIRED,
                tableId.equals("001") ? "site-language-table" : "postal-code-table",
                "Every supplied occurrence has fixed Table ID " + tableId + " and Table Length "
                        + (tableId.equals("001") ? "003" : "013")
                        + " with data spanning that declared length. Appendix W's an1 attribute conflicts "
                        + "with the length/description; the value's content and the described association "
                        + "cannot be certified without clarification. Description: " + description + ".");
    }

    private static CheckResult validateTable(Table table) {
        if (!"001".equals(table.id()) && !"002".equals(table.id())) {
            return new CheckResult(false, "Table ID '" + table.id() + "' is not defined by Appendix W.");
        }
        if (table.length() == null || table.length().length() != 3 || !isAsciiDigits(table.length())) {
            return new CheckResult(false, "Table " + table.id()
                    + " Table Length must contain exactly 3 numeric digits.");
        }
        String expectedLength = "001".equals(table.id()) ? "003" : "013";
        if (!expectedLength.equals(table.length())) {
            return new CheckResult(false, "Appendix W Table " + table.id() + " Table Length must be "
                    + expectedLength + "; found " + table.length() + ".");
        }
        if (table.data() == null || table.data().length() != Integer.parseInt(expectedLength)) {
            return new CheckResult(false, "Appendix W Table " + table.id() + " Table Data must contain "
                    + Integer.parseInt(expectedLength) + " characters to match its stated Table Length "
                    + expectedLength + ".");
        }
        return new CheckResult(true, "Table " + table.id() + " conforms to its Appendix W identifier, "
                + "stated fixed Table Length, and data width implied by that length.");
    }

    private static ParseResult parse(String encodedTables) {
        if (encodedTables == null || encodedTables.isEmpty()) {
            return new ParseResult(List.of(), "Download Data must contain at least one complete Appendix W "
                    + "table record.", false);
        }
        if (encodedTables.length() > MAX_DOWNLOAD_DATA_LENGTH) {
            return new ParseResult(List.of(), "Download Data must not exceed " + MAX_DOWNLOAD_DATA_LENGTH
                    + " bytes; found at least " + encodedTables.length() + " characters.", false);
        }
        if (!isAscii(encodedTables)) {
            return new ParseResult(List.of(), "Non-ASCII Download Data cannot be byte-counted or framed without "
                    + "an authoritative character encoding.", true);
        }
        List<Table> tables = new ArrayList<>();
        int offset = 0;
        while (offset < encodedTables.length()) {
            if (encodedTables.length() - offset < 6) {
                return new ParseResult(List.of(), "Truncated Appendix W table header at character " + offset
                        + "; Table ID and Table Length must each contain 3 digits.", false);
            }
            String id = encodedTables.substring(offset, offset + 3);
            String length = encodedTables.substring(offset + 3, offset + 6);
            if (!isAsciiDigits(id) || !isAsciiDigits(length)) {
                return new ParseResult(List.of(), "Malformed Appendix W table header at character " + offset
                        + "; Table ID and Table Length must each contain 3 numeric digits.", false);
            }
            int dataLength = Integer.parseInt(length);
            int dataStart = offset + 6;
            int dataEnd = dataStart + dataLength;
            if (dataEnd > encodedTables.length()) {
                return new ParseResult(List.of(), "Table " + id + " declares " + length
                        + " data characters but only " + (encodedTables.length() - dataStart) + " remain.",
                        false);
            }
            tables.add(new Table(id, length, encodedTables.substring(dataStart, dataEnd)));
            offset = dataEnd;
        }
        return new ParseResult(List.copyOf(tables), null, false);
    }

    private static boolean isAsciiDigits(String value) {
        return value != null && !value.isEmpty()
                && value.chars().allMatch(character -> character >= '0' && character <= '9');
    }

    private static boolean isAscii(String value) {
        return value != null && value.chars().allMatch(character -> character <= 0x7F);
    }
}
