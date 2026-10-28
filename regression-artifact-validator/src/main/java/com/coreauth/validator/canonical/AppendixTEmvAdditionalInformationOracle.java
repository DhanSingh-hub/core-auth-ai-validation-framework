package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;

/**
 * Independent, bounded checks for ATL105 Appendix T (EMV Additional Information Data Layouts), the
 * layout of the two EMV additional-information table types - EMV Table Data (Table ID 001) and Card
 * Authentication Results Code/CARC (Table ID 002) - carried via Element 191 (EMV Additional
 * Information Indicator), Element 192 (EMV Additional Information Length), and Element 118 (EMV
 * Additional Information).
 *
 * <p>Transcribed from {@code specifications/ATL105/docs/specs/extracted_text.txt}, Appendix T-1
 * through T-2 (lines ~35557-35616), cross-checked against Elements 191/192/118's own definitions
 * (lines ~24426-24454, since Appendix T's own text states only the per-table layout attributes, not
 * the surrounding Indicator/Length/Value field framing):
 *
 * <ul>
 *   <li>Element 191 (EMV Additional Information Indicator): {@code N3}, fixed length of 3 digits.
 *       Its own "Valid Codes/Values" list states only {@code 001 EMV table data} - it omits
 *       {@code 002}, even though Appendix T itself defines Table ID 002 (CARC) and the specification's
 *       own appendix overview (line 1799: "This appendix contains data layout information for EMV
 *       Table Data (Code 001) and Card Authentication Results Code (CARC)") confirms 002 is a
 *       legitimate indicator value. This oracle treats {@code 001} and {@code 002} as the two valid
 *       Indicator values, reconciling Element 191's incomplete own table against Appendix T's
 *       authoritative two-table definition rather than fabricating a third value or dropping 002.
 *   <li>Element 192 (EMV Additional Information Length): {@code N3}, fixed length of 3 digits, valid
 *       range {@code 001}-{@code 985}.
 *   <li>Element 118 (EMV Additional Information): variable-length value whose content is one of the
 *       two Appendix T table layouts, each starting with a 3-digit Table ID that must match the
 *       Indicator value ({@code 001} or {@code 002}).
 *   <li>Table ID 001 (EMV Table Data): the value is exactly 6 characters, either {@code EMVYES} or
 *       {@code EMVNOT} (the appendix's own stated "Request / Valid Codes/Values").
 *   <li>Table ID 002 (CARC): the value is exactly 1 character ("Maximum Length n6 Len.: 1"), but
 *       Appendix T states no concrete code catalog for it beyond "Identifies the CARC value returned
 *       by Visa in Bit No. 44.8 of the response" - so only the 1-character length is independently
 *       assertable; recognizing specific CARC codes requires Visa's own Bit 44.8 definition.
 *   <li>"Table Length" (the middle column of each Appendix T layout table) is described only as
 *       {@code n / Fixed} with no digit count or concrete value stated anywhere in the appendix, so
 *       {@link #BR_TABLE_LENGTH_FIELD} stays {@code REVIEW_REQUIRED} rather than fabricating a width.
 *   <li>Appendix T-1's own "Response" note - "If this field is included in an authorization response,
 *       the device will echo it back on any subsequent advice or batch upload request" - is a
 *       cross-message consistency rule that cannot be verified from a single request/response
 *       snapshot, so {@link #BR_ECHO} stays {@code REVIEW_REQUIRED}, the same bounding used for
 *       Appendix Q's flow-consistency BR and Appendix R's duplicate-tag/cross-field BRs.
 * </ul>
 *
 * <p>This is a standalone companion-field check, not wired into {@code Segment100PayloadValidator}:
 * the EMV Additional Information section (Elements 191/192/118) is a repeating section of Data
 * Segment No. 130/131, not part of the Segment 100 payload itself.
 */
public final class AppendixTEmvAdditionalInformationOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    public static final String BR_INDICATOR_FORMAT = "BR-SEG100-APPT-INDICATOR-FORMAT";
    public static final String BR_LENGTH_FORMAT = "BR-SEG100-APPT-LENGTH-FORMAT";
    public static final String BR_TABLE_ID_MATCH = "BR-SEG100-APPT-TABLE-ID-MATCH";
    public static final String BR_EMV_TABLE_DATA = "BR-SEG100-APPT-EMV-TABLE-DATA";
    public static final String BR_CARC = "BR-SEG100-APPT-CARC";
    public static final String BR_TABLE_LENGTH_FIELD = "BR-SEG100-APPT-TABLE-LENGTH-FIELD";
    public static final String BR_ECHO = "BR-SEG100-APPT-ECHO";

    private static final String TABLE_ID_EMV_TABLE_DATA = "001";
    private static final String TABLE_ID_CARC = "002";

    /**
     * Runs every independently-assertable Appendix T check for one EMV Additional Information
     * section occurrence (one Indicator + Length + Value triple).
     *
     * @param indicator Element 191 raw value (expected 3 digits)
     * @param length    Element 192 raw value (expected 3 digits)
     * @param tableId   the 3-digit Table ID embedded at the start of the Element 118 value
     * @param tableValue the table-specific payload that follows the Table ID/Table Length framing
     *                   (e.g. {@code EMVYES}/{@code EMVNOT} for Table ID 001, or the 1-character CARC
     *                   value for Table ID 002)
     */
    public List<Assessment> assessSection(String indicator, String length, String tableId, String tableValue) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessIndicatorFormat(indicator));
        assessments.add(assessLengthFormat(length));
        assessments.add(assessTableIdMatch(indicator, tableId));
        assessments.add(assessEmvTableData(tableId, tableValue));
        assessments.add(assessCarc(tableId, tableValue));
        assessments.add(assessTableLengthField());
        assessments.add(assessEcho());
        return assessments;
    }

    private Assessment assessIndicatorFormat(String indicator) {
        if (indicator == null || indicator.length() != 3 || !indicator.chars().allMatch(Character::isDigit)) {
            return new Assessment(BR_INDICATOR_FORMAT, Status.FAIL, "indicator-format",
                    "EMV Additional Information Indicator must be exactly 3 numeric digits; found '"
                            + indicator + "'.");
        }
        if (!TABLE_ID_EMV_TABLE_DATA.equals(indicator) && !TABLE_ID_CARC.equals(indicator)) {
            return new Assessment(BR_INDICATOR_FORMAT, Status.FAIL, "indicator-format",
                    "EMV Additional Information Indicator must be '001' (EMV table data) or '002' (CARC); "
                            + "found '" + indicator + "'.");
        }
        return new Assessment(BR_INDICATOR_FORMAT, Status.PASS, "indicator-format",
                "EMV Additional Information Indicator is exactly 3 numeric digits with value '001' or '002'.");
    }

    private Assessment assessLengthFormat(String length) {
        if (length == null || length.length() != 3 || !length.chars().allMatch(Character::isDigit)) {
            return new Assessment(BR_LENGTH_FORMAT, Status.FAIL, "length-format",
                    "EMV Additional Information Length must be exactly 3 numeric digits; found '"
                            + length + "'.");
        }
        int value = Integer.parseInt(length);
        if (value < 1 || value > 985) {
            return new Assessment(BR_LENGTH_FORMAT, Status.FAIL, "length-format",
                    "EMV Additional Information Length must be in range 001-985; found '" + length + "'.");
        }
        return new Assessment(BR_LENGTH_FORMAT, Status.PASS, "length-format",
                "EMV Additional Information Length is exactly 3 numeric digits in range 001-985.");
    }

    private Assessment assessTableIdMatch(String indicator, String tableId) {
        if (indicator == null || tableId == null) {
            return new Assessment(BR_TABLE_ID_MATCH, Status.FAIL, "table-id-match",
                    "Indicator and embedded Table ID must both be present to compare.");
        }
        if (!indicator.equals(tableId)) {
            return new Assessment(BR_TABLE_ID_MATCH, Status.FAIL, "table-id-match",
                    "Embedded Table ID '" + tableId + "' must equal the EMV Additional Information "
                            + "Indicator value '" + indicator + "'.");
        }
        return new Assessment(BR_TABLE_ID_MATCH, Status.PASS, "table-id-match",
                "Embedded Table ID matches the EMV Additional Information Indicator value.");
    }

    private Assessment assessEmvTableData(String tableId, String tableValue) {
        if (!TABLE_ID_EMV_TABLE_DATA.equals(tableId)) {
            return new Assessment(BR_EMV_TABLE_DATA, Status.FAIL, "emv-table-data",
                    "This rule only applies when Table ID is '001'; found '" + tableId + "'.");
        }
        if (!"EMVYES".equals(tableValue) && !"EMVNOT".equals(tableValue)) {
            return new Assessment(BR_EMV_TABLE_DATA, Status.FAIL, "emv-table-data",
                    "EMV Table Data value must be 'EMVYES' or 'EMVNOT'; found '" + tableValue + "'.");
        }
        return new Assessment(BR_EMV_TABLE_DATA, Status.PASS, "emv-table-data",
                "EMV Table Data value is one of the two stated codes, 'EMVYES' or 'EMVNOT'.");
    }

    private Assessment assessCarc(String tableId, String tableValue) {
        if (!TABLE_ID_CARC.equals(tableId)) {
            return new Assessment(BR_CARC, Status.FAIL, "carc",
                    "This rule only applies when Table ID is '002'; found '" + tableId + "'.");
        }
        if (tableValue == null || tableValue.length() != 1) {
            return new Assessment(BR_CARC, Status.FAIL, "carc",
                    "CARC value must be exactly 1 character; found '" + tableValue + "'.");
        }
        return new Assessment(BR_CARC, Status.PASS, "carc",
                "CARC value is exactly 1 character, matching Appendix T-2's stated Maximum Length. "
                        + "Appendix T states no concrete CARC code catalog, so only the length is "
                        + "independently assertable; recognizing specific codes requires Visa's own Bit "
                        + "44.8 definition.");
    }

    /**
     * Appendix T's "Table Length" column is stated only as {@code n / Fixed} with no digit count or
     * concrete value given anywhere in the appendix text, for either table. Fabricating a width here
     * would not be independently verifiable, so this always stays {@code REVIEW_REQUIRED}.
     */
    private Assessment assessTableLengthField() {
        return new Assessment(BR_TABLE_LENGTH_FIELD, Status.REVIEW_REQUIRED, "table-length-field",
                "Appendix T states the 'Table Length' field is numeric and fixed-width, but never states "
                        + "the digit count or a concrete value for either table; no independent rule can be "
                        + "asserted without fabricating a width the spec does not provide.");
    }

    /**
     * Appendix T-1's own "Response" note states that EMV Table Data included in an authorization
     * response must be echoed back on any subsequent advice or batch upload request. This is a
     * cross-message consistency rule that cannot be verified from a single request/response snapshot,
     * so it always stays {@code REVIEW_REQUIRED}, mirroring Appendix R's {@code assessCrossField()} and
     * Appendix Q's {@code assessFlowConsistency()} postures.
     */
    private Assessment assessEcho() {
        return new Assessment(BR_ECHO, Status.REVIEW_REQUIRED, "echo",
                "Appendix T-1 states EMV Table Data included in an authorization response must be echoed "
                        + "back on subsequent advice/batch-upload requests, but confirming that echo requires "
                        + "comparing multiple messages across a transaction lifecycle, not a single "
                        + "request/response snapshot.");
    }
}
