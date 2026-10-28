package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Independent, bounded checks for the Moneris request and response table layouts in ATL105
 * Appendix V (2026-3).
 *
 * <p>The encoded input is a concatenation of 3-digit Table ID, 3-digit Table Length, and Table Data
 * records. Checks cover the published IDs, fixed/maximum lengths, the SPDH header's stated
 * character classes, and the four stated language indicator values. They do not infer table
 * requiredness, routing, amount/sequence correlations, MAC authenticity, or encryption-key
 * encoding.
 */
public final class AppendixVMonerisDataOracle {

    public enum Direction {
        REQUEST,
        RESPONSE
    }

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    public record LanguageProfile(String terminal, String ped, String merchantReceipt, String cardholderReceipt) {
    }

    private record Table(String id, String length, String data) {
    }

    private record ParseResult(List<Table> tables, String error) {
    }

    private record CheckResult(boolean passed, String reason) {
    }

    public static final String BR_SEGMENT = "BR-SEG100-APPV-SEGMENT";
    public static final String BR_REQUEST_TABLES = "BR-SEG100-APPV-REQUEST-TABLES";
    public static final String BR_COMPLETION = "BR-SEG100-APPV-COMPLETION";
    public static final String BR_SECURITY = "BR-SEG100-APPV-SECURITY";
    public static final String BR_SEQUENCE_LINK = "BR-SEG100-APPV-SEQUENCE-LINK";
    public static final String BR_MAC_LENGTH = "BR-SEG100-APPV-MAC-LENGTH";
    public static final String BR_RESPONSE_TABLES = "BR-SEG100-APPV-RESPONSE-TABLES";
    public static final String BR_SOURCE_CLARIFICATION = "BR-SEG100-APPV-SOURCE-CLARIFICATION";

    private static final List<String> REQUEST_TABLE_IDS =
            List.of("001", "002", "003", "004", "005", "006", "007", "008", "009");
    private static final List<String> RESPONSE_TABLE_IDS =
            List.of("001", "002", "003", "004", "005", "006");

    /**
     * Returns Appendix V's complete language matrix for a valid request Table 004 code.
     *
     * @param code one-character Moneris Language Indicator
     * @return the Terminal, PED, Merchant Receipt, and Cardholder Receipt languages; empty for an
     *         unrecognized code
     */
    public Optional<LanguageProfile> languageProfile(String code) {
        return switch (code == null ? "" : code) {
            case "3" -> Optional.of(new LanguageProfile("English", "English", "English", "English"));
            case "4" -> Optional.of(new LanguageProfile("French", "French", "French", "French"));
            case "5" -> Optional.of(new LanguageProfile("English", "French", "English", "French"));
            case "6" -> Optional.of(new LanguageProfile("French", "English", "French", "English"));
            default -> Optional.empty();
        };
    }

    /**
     * Identifies the key categories explicitly marked present by a three-character response
     * indicator. Appendix V specifies the meaning and order of Y markers, but not the code for an
     * absent key; non-Y values therefore do not assert absence or validity.
     *
     * @param indicators the first three characters of response Table 006
     * @return the key types explicitly marked Y, or empty when the input is not exactly 3 characters
     */
    public Optional<List<String>> indicatedKeyTypes(String indicators) {
        if (indicators == null || indicators.length() != 3) {
            return Optional.empty();
        }
        List<String> keyTypes = new ArrayList<>();
        String[] names = {"MAC Encryption", "Data Encryption", "PIN Encryption"};
        for (int i = 0; i < indicators.length(); i++) {
            if (indicators.charAt(i) == 'Y') {
                keyTypes.add(names[i]);
            }
        }
        return Optional.of(List.copyOf(keyTypes));
    }

    public List<Assessment> assessRequest(String encodedTables) {
        ParseResult parsed = parse(encodedTables);
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(review(BR_SEGMENT, "segment-applicability",
                "Appendix V describes the layouts but does not define a Segment 100 routing predicate or "
                        + "segment-presence rule; verify Moneris routing against the approved implementation."));
        assessments.add(checkAssessment(BR_REQUEST_TABLES, "request-table-layout",
                assessTables(parsed, Direction.REQUEST)));
        assessments.add(review(BR_COMPLETION, "completion-linkage",
                "Table 005 identifies an authorization amount for completion and Table 006 a sequence number, "
                        + "but Appendix V does not define a field-to-field comparison or transaction lifecycle "
                        + "formula."));
        assessments.add(checkAssessment(BR_SECURITY, "request-security-layout",
                assessRequestSecurity(parsed)));
        assessments.add(review(BR_SEQUENCE_LINK, "sequence-number-link",
                "The request Table 006 value is described as the sequence number sent to Moneris; Appendix V "
                        + "does not state that it must equal Segment 100 Element 86."));
        assessments.add(checkAssessment(BR_MAC_LENGTH, "request-mac-length",
                assessMacLength(parsed, Direction.REQUEST)));
        assessments.add(review(BR_SOURCE_CLARIFICATION, "source-description-ambiguities",
                "Appendix V-2 describes Table 004 data as an13 despite its fixed length of 001, and Table 005 "
                        + "as an3 despite a variable maximum length of 7. These conflicting descriptions are "
                        + "not silently reconciled. Appendix V-4 does not state the absent-key indicator value "
                        + "or text encoding for Table 006 key bytes."));
        return List.copyOf(assessments);
    }

    public List<Assessment> assessResponse(String encodedTables) {
        ParseResult parsed = parse(encodedTables);
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(review(BR_SEGMENT, "segment-applicability",
                "Appendix V describes the layouts but does not define a Segment 100 routing predicate or "
                        + "segment-presence rule; verify Moneris routing against the approved implementation."));
        assessments.add(checkAssessment(BR_RESPONSE_TABLES, "response-table-layout",
                assessTables(parsed, Direction.RESPONSE)));
        assessments.add(checkAssessment(BR_SECURITY, "response-security-layout",
                assessResponseSecurity(parsed)));
        assessments.add(review(BR_SEQUENCE_LINK, "response-sequence-correlation",
                "Appendix V identifies the response Table 004 as a returned sequence number but does not state "
                        + "which request value or external transaction it must match."));
        assessments.add(checkAssessment(BR_MAC_LENGTH, "response-mac-length",
                assessMacLength(parsed, Direction.RESPONSE)));
        assessments.add(review(BR_SOURCE_CLARIFICATION, "source-description-ambiguities",
                "Appendix V-4 gives an encryption-key maximum and indicator order but does not define the "
                        + "absent-key indicator value, key-byte textual encoding, or cryptographic validity "
                        + "checks."));
        return List.copyOf(assessments);
    }

    private static CheckResult assessTables(ParseResult parsed, Direction direction) {
        if (parsed.error() != null) {
            return new CheckResult(false, parsed.error());
        }
        List<String> allowedIds = direction == Direction.REQUEST ? REQUEST_TABLE_IDS : RESPONSE_TABLE_IDS;
        for (Table table : parsed.tables()) {
            if (!allowedIds.contains(table.id())) {
                return new CheckResult(false, "Table ID '" + table.id() + "' is not defined for the "
                        + direction.name().toLowerCase() + " layout in Appendix V.");
            }
            CheckResult result = validateTable(table, direction);
            if (!result.passed()) {
                return result;
            }
        }
        return new CheckResult(true, "Every supplied table has a known Appendix V Table ID and conforms to "
                + "its stated length and independently-checkable data representation. Appendix V does not "
                + "state that every listed table is mandatory or prescribe an order.");
    }

    private static CheckResult validateTable(Table table, Direction direction) {
        if (table.data() == null) {
            return new CheckResult(false, "Table " + table.id() + " has no Table Data.");
        }
        if (table.length() == null || table.length().length() != 3 || !isAsciiDigits(table.length())) {
            return new CheckResult(false, "Table " + table.id() + " Table Length must be exactly 3 numeric "
                    + "digits; found '" + table.length() + "'.");
        }
        int declaredLength = Integer.parseInt(table.length());
        if (declaredLength != table.data().length()) {
            return new CheckResult(false, "Table " + table.id() + " declares length " + table.length()
                    + " but contains " + table.data().length() + " characters.");
        }

        int fixedLength = fixedLength(table.id(), direction);
        if (fixedLength >= 0 && declaredLength != fixedLength) {
            return new CheckResult(false, "Table " + table.id() + " length must be "
                    + String.format("%03d", fixedLength) + "; found " + table.length() + ".");
        }
        int maximumLength = maximumLength(table.id(), direction);
        if (maximumLength >= 0 && declaredLength > maximumLength) {
            return new CheckResult(false, "Table " + table.id() + " length must not exceed "
                    + String.format("%03d", maximumLength) + "; found " + table.length() + ".");
        }
        if (direction == Direction.RESPONSE && "006".equals(table.id()) && declaredLength < 3) {
            return new CheckResult(false, "Response Table 006 must contain its 3-character key indicator "
                    + "before any key data.");
        }
        if (direction == Direction.RESPONSE && "006".equals(table.id())
                && (declaredLength - 3) % 16 != 0) {
            return new CheckResult(false, "Response Table 006 must contain a 3-character indicator followed "
                    + "by zero to three 16-byte key units; found " + declaredLength + " total characters. "
                    + "Key byte encoding is not asserted.");
        }
        if ("001".equals(table.id())) {
            return validateSpdhHeader(table.data());
        }
        if ("004".equals(table.id()) && direction == Direction.REQUEST) {
            if (!List.of("3", "4", "5", "6").contains(table.data())) {
                return new CheckResult(false, "Request Table 004 Language Indicator must be one of 3, 4, 5, "
                        + "or 6; found '" + table.data() + "'.");
            }
            return new CheckResult(true, "Request Table 004 uses one of the four Appendix V language codes.");
        }
        if (direction == Direction.RESPONSE && "006".equals(table.id())) {
            return new CheckResult(true, "Response Table 006 contains a 3-character indicator and zero to "
                    + "three 16-byte key units within its maximum. Indicator characters 1/2/3 identify the "
                    + "MAC/Data Encryption/PIN Encryption key when marked Y; absent-key indicator values, "
                    + "key byte encoding, and cryptographic correctness are not asserted.");
        }
        if (isAlphanumericTable(table.id(), direction) && !isAsciiAlphanumericOrSpace(table.data())) {
            return new CheckResult(false, "Table " + table.id()
                    + " contains characters outside its stated alphanumeric data representation.");
        }
        return new CheckResult(true, "Table " + table.id() + " conforms to its stated length bound.");
    }

    private static CheckResult validateSpdhHeader(String data) {
        if (data.length() != 48) {
            return new CheckResult(false, "SPDH Header Table 001 must contain exactly 48 characters.");
        }
        int[] starts = {0, 2, 4, 12, 20, 26, 38, 39, 40, 42, 45};
        int[] ends = {2, 4, 12, 20, 26, 38, 39, 40, 42, 45, 48};
        char[] formats = {'A', 'N', 'A', 'A', 'A', 'A', 'A', 'A', 'N', 'N', 'N'};
        for (int i = 0; i < starts.length; i++) {
            String field = data.substring(starts[i], ends[i]);
            boolean valid = formats[i] == 'N' ? isAsciiDigits(field) : isAsciiAlphanumericOrSpace(field);
            if (!valid) {
                return new CheckResult(false, "SPDH Header Table 001 field " + (i + 1) + " ("
                        + (ends[i] - starts[i]) + " characters, format " + formats[i]
                        + ") contains an invalid character.");
            }
        }
        return new CheckResult(true, "SPDH Header Table 001 conforms to all 11 stated field widths, numeric "
                + "fields, and A-field alphanumeric representation.");
    }

    private static CheckResult assessRequestSecurity(ParseResult parsed) {
        if (parsed.error() != null) {
            return new CheckResult(false, parsed.error());
        }
        for (Table table : parsed.tables()) {
            if ("009".equals(table.id())) {
                CheckResult result = validateTable(table, Direction.REQUEST);
                if (!result.passed()) {
                    return result;
                }
            }
        }
        return new CheckResult(true, "Request Terminal Capabilities and Contactless Processing Table 009, "
                + "when present, uses the stated 2-character representation. Moneris-specific flag meanings "
                + "are not defined in Appendix V.");
    }

    private static CheckResult assessResponseSecurity(ParseResult parsed) {
        if (parsed.error() != null) {
            return new CheckResult(false, parsed.error());
        }
        for (Table table : parsed.tables()) {
            if ("006".equals(table.id())) {
                CheckResult result = validateTable(table, Direction.RESPONSE);
                if (!result.passed()) {
                    return result;
                }
            }
        }
        return new CheckResult(true, "Response Encryption Key Table 006, when present, has a 3-character "
                + "indicator followed by zero to three 16-byte key units within the stated maximum. Key "
                + "indicator semantics, key encoding, and authenticity are not fully specified by Appendix V.");
    }

    private static CheckResult assessMacLength(ParseResult parsed, Direction direction) {
        if (parsed.error() != null) {
            return new CheckResult(false, parsed.error());
        }
        String macId = direction == Direction.REQUEST ? "007" : "005";
        for (Table table : parsed.tables()) {
            if (macId.equals(table.id())) {
                CheckResult result = validateTable(table, direction);
                if (!result.passed()) {
                    return result;
                }
            }
        }
        return new CheckResult(true, "Every supplied MAC table uses the specified fixed length of 016. "
                + "MAC computation and authenticity cannot be checked without Moneris' algorithm and keys.");
    }

    private static int fixedLength(String id, Direction direction) {
        if ("001".equals(id)) {
            return 48;
        }
        if ("002".equals(id)) {
            return 8;
        }
        if ("003".equals(id)) {
            return 13;
        }
        if ("004".equals(id)) {
            return direction == Direction.REQUEST ? 1 : 10;
        }
        if (direction == Direction.REQUEST) {
            return switch (id) {
                case "006" -> 10;
                case "007" -> 16;
                case "008" -> 1;
                case "009" -> 2;
                default -> -1;
            };
        }
        return "005".equals(id) ? 16 : -1;
    }

    private static int maximumLength(String id, Direction direction) {
        if (direction == Direction.REQUEST && "005".equals(id)) {
            return 7;
        }
        if (direction == Direction.RESPONSE && "006".equals(id)) {
            return 51;
        }
        return -1;
    }

    private static boolean isAlphanumericTable(String id, Direction direction) {
        if ("001".equals(id)) {
            return false;
        }
        if (direction == Direction.REQUEST && "005".equals(id)) {
            return true;
        }
        return !(direction == Direction.RESPONSE && "006".equals(id));
    }

    private static ParseResult parse(String encodedTables) {
        if (encodedTables == null || encodedTables.isEmpty()) {
            return new ParseResult(List.of(), "Moneris Data must contain at least one complete "
                    + "<Table ID><Table Length><Table Data> record.");
        }
        List<Table> tables = new ArrayList<>();
        int offset = 0;
        while (offset < encodedTables.length()) {
            if (encodedTables.length() - offset < 6) {
                return new ParseResult(List.of(), "Truncated Moneris table header at character " + offset
                        + "; each Table ID and Table Length must contain 3 digits.");
            }
            String id = encodedTables.substring(offset, offset + 3);
            String length = encodedTables.substring(offset + 3, offset + 6);
            if (!isAsciiDigits(id) || !isAsciiDigits(length)) {
                return new ParseResult(List.of(), "Malformed Moneris table header at character " + offset
                        + "; Table ID and Table Length must each be 3 numeric digits.");
            }
            int dataLength = Integer.parseInt(length);
            int dataStart = offset + 6;
            int dataEnd = dataStart + dataLength;
            if (dataEnd > encodedTables.length()) {
                return new ParseResult(List.of(), "Table " + id + " declares " + length
                        + " data characters but only " + (encodedTables.length() - dataStart) + " remain.");
            }
            tables.add(new Table(id, length, encodedTables.substring(dataStart, dataEnd)));
            offset = dataEnd;
        }
        return new ParseResult(List.copyOf(tables), null);
    }

    private static Assessment checkAssessment(String businessRequirementId, String scope, CheckResult result) {
        return new Assessment(businessRequirementId, result.passed() ? Status.PASS : Status.FAIL, scope,
                result.reason());
    }

    private static Assessment review(String businessRequirementId, String scope, String reason) {
        return new Assessment(businessRequirementId, Status.REVIEW_REQUIRED, scope, reason);
    }

    private static boolean isAsciiDigits(String value) {
        return value != null && !value.isEmpty()
                && value.chars().allMatch(character -> character >= '0' && character <= '9');
    }

    private static boolean isAsciiAlphanumericOrSpace(String value) {
        return value != null && !value.isEmpty()
                && value.chars().allMatch(character -> character == ' '
                        || (character >= 'A' && character <= 'Z')
                        || (character >= 'a' && character <= 'z')
                        || (character >= '0' && character <= '9'));
    }
}
