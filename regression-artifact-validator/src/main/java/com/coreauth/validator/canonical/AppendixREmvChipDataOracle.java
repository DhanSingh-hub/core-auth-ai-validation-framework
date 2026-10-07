package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Independent, bounded checks for ATL105 Appendix R (EMV Chip Data Example), which illustrates
 * Element 189 (EMV Chip Data Length) and Element 190 (EMV Chip Data).
 *
 * <p>Transcribed from {@code specifications/ATL105/docs/specs/extracted_text.txt}: Appendix R's own
 * one-page text (around lines 35403-35436, between Appendix Q and Appendix S) states only two field
 * attributes - Length (N3, e.g. {@code 0nnn}) and Tag/length/value data (ANSB, up to 999 bytes,
 * "BCD Packed/Hex Nibbles" format {@code nn\}) - followed by a worked example of one EMV chip data
 * packet. The appendix explicitly disclaims completeness: "The below table does not contain complete
 * list of tags. For complete list of EMV tags, please contact your BUYPASS representative." This is
 * the opposite posture from Appendix Q's exhaustive code tables, so no BR here depends on a closed
 * tag catalog; every check below is a generic structural rule.
 *
 * <p>The concrete, independently-assertable rules come from the main-body definitions of the two
 * elements Appendix R exemplifies (around lines 24341-24383, immediately before the "For an example of
 * this data element, please see Appendix R" cross-reference): the three-digit length prefix (Element
 * 189), the binary tag/length/value decoding rules (a tag is two bytes only when the last five bits of
 * its first byte are all 1; a length byte with its high bit set carries a byte count for a following
 * big-endian length value, otherwise its low seven bits are the length itself), and two explicit
 * processing-rule notes: the Application ID (tag {@code 9F06}) or Dedicated File Name (tag {@code 84})
 * must be included, and PAN (tag {@code 5A}) and Track 2 equivalent data (tag {@code 57}) must not be.
 *
 * <p>Out of scope for every check below: validating specific EMV tag values beyond generic TLV framing
 * (the appendix itself says its tag list is not complete), cryptographic verification of the
 * Application Cryptogram (tag {@code 9F26}; that requires a certified EMV kernel/HSM, not AI-generated
 * fixtures), and agreement between decoded tag values (e.g. {@code 9F02} Amount Authorized, {@code
 * 5F2A} Transaction Currency Code) and the corresponding Segment 100 elements - neither Appendix R nor
 * Element 189/190's own definitions state such a cross-field formula, so {@link #assessCrossField()}
 * is always {@code REVIEW_REQUIRED}, mirroring Appendix Q's {@code assessFlowConsistency()} posture for
 * the same reason. Whether Segment 130 as a whole is present is a Segment 130 structural question, not
 * something Appendix R's own one-page text states, so it is not modeled here beyond the narrow
 * Element 189/190 field-presence check in {@link #assessEmvCompanion}.
 */
public final class AppendixREmvChipDataOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED,
        NOT_ASSERTABLE
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    /** One decoded EMV TLV sub-element: tag and value as upper-case hex, and the decoded byte length. */
    public record TlvEntry(String tag, int length, String valueHex) {
    }

    /** Result of decomposing EMV Chip Data into TLV entries; {@code wellFormed} false means {@code reason} explains why. */
    public record TlvDecodeResult(boolean wellFormed, List<TlvEntry> entries, String reason) {
    }

    /**
     * Optional transaction context needed to evaluate {@link #BR_EMV_COMPANION}, which depends on
     * whether the transaction is EMV at all (not stated anywhere in Element 189/190 itself).
     * {@code null} means "not stated/unknown" and yields {@code REVIEW_REQUIRED} rather than guessing.
     */
    public record TransactionContext(Boolean emvTransaction) {

        public static TransactionContext unknown() {
            return new TransactionContext(null);
        }
    }

    public static final String BR_EMV_COMPANION = "BR-SEG100-APPR-EMV-COMPANION";
    public static final String BR_LENGTH_PREFIX = "BR-SEG100-APPR-CHIP-LENGTH";
    public static final String BR_TLV_FORMAT = "BR-SEG100-APPR-TLV-FORMAT";
    public static final String BR_MANDATORY_IDENTIFIER = "BR-SEG100-APPR-MANDATORY-IDENTIFIER";
    public static final String BR_PROHIBITED_SENSITIVE_TAG = "BR-SEG100-APPR-PROHIBITED-SENSITIVE-TAG";
    public static final String BR_DUPLICATE_TAG_REVIEW = "BR-SEG100-APPR-DUPLICATE-TAG-REVIEW";
    public static final String BR_CROSS_FIELD = "BR-SEG100-APPR-CROSS-FIELD";

    private static final String AID_TAG = "9F06";
    private static final String DF_NAME_TAG = "84";
    private static final String PAN_TAG = "5A";
    private static final String TRACK2_TAG = "57";

    /** Runs every independently-assertable Appendix R check against one EMV Chip Data field pair. */
    public List<Assessment> assess(String lengthField, String chipDataValue) {
        return assess(lengthField, chipDataValue, TransactionContext.unknown());
    }

    /** Runs every independently-assertable Appendix R check, including the context-dependent {@link #BR_EMV_COMPANION}. */
    public List<Assessment> assess(String lengthField, String chipDataValue, TransactionContext context) {
        List<Assessment> assessments = new ArrayList<>();
        TlvDecodeResult decode = decode(chipDataValue);
        assessments.add(assessLengthPrefix(lengthField, chipDataValue));
        assessments.add(assessTlvFormat(decode));
        assessments.add(assessMandatoryIdentifier(decode));
        assessments.add(assessProhibitedSensitiveTag(decode));
        assessments.add(assessDuplicateTagReview(decode));
        assessments.add(assessEmvCompanion(lengthField, chipDataValue, context));
        assessments.add(assessCrossField());
        return assessments;
    }

    /** Decomposes {@code chipDataValue} into EMV TLV entries per Element 190's binary encoding rules. */
    public TlvDecodeResult decode(String chipDataValue) {
        String hex = normalizeHex(chipDataValue);
        if (hex == null) {
            return new TlvDecodeResult(false, List.of(),
                    "EMV Chip Data value is not valid BCD-packed/hex nibble data "
                            + "(Appendix R-1 note: data is in format 'nn\\').");
        }
        byte[] data = hexToBytes(hex);
        List<TlvEntry> entries = new ArrayList<>();
        int index = 0;
        while (index < data.length) {
            int tagStart = index;
            int firstTagByte = data[index] & 0xFF;
            int tagLen = (firstTagByte & 0x1F) == 0x1F ? 2 : 1;
            if (index + tagLen > data.length) {
                return new TlvDecodeResult(false, entries,
                        "Tag field truncated at byte offset " + tagStart + ".");
            }
            StringBuilder tagHex = new StringBuilder();
            for (int i = 0; i < tagLen; i++) {
                tagHex.append(String.format("%02X", data[index + i] & 0xFF));
            }
            index += tagLen;
            if (index >= data.length) {
                return new TlvDecodeResult(false, entries,
                        "Length field missing after tag " + tagHex + " at byte offset " + tagStart + ".");
            }
            int firstLenByte = data[index] & 0xFF;
            int length;
            if ((firstLenByte & 0x80) != 0) {
                int numLengthBytes = firstLenByte & 0x7F;
                index += 1;
                if (numLengthBytes == 0 || index + numLengthBytes > data.length) {
                    return new TlvDecodeResult(false, entries,
                            "Long-form length field truncated/invalid for tag " + tagHex
                                    + " at byte offset " + tagStart + ".");
                }
                length = 0;
                for (int i = 0; i < numLengthBytes; i++) {
                    length = (length << 8) | (data[index + i] & 0xFF);
                }
                index += numLengthBytes;
            } else {
                length = firstLenByte;
                index += 1;
            }
            if (index + length > data.length) {
                return new TlvDecodeResult(false, entries,
                        "Value field for tag " + tagHex + " declares length " + length
                                + " but only " + (data.length - index) + " byte(s) remain.");
            }
            StringBuilder valueHex = new StringBuilder();
            for (int i = 0; i < length; i++) {
                valueHex.append(String.format("%02X", data[index + i] & 0xFF));
            }
            entries.add(new TlvEntry(tagHex.toString(), length, valueHex.toString()));
            index += length;
        }
        return new TlvDecodeResult(true, entries,
                "Fully decoded " + entries.size() + " TLV entr" + (entries.size() == 1 ? "y" : "ies")
                        + " with no leftover bytes.");
    }

    /**
     * Element 189 (EMV Chip Data Length): "Fixed length of three digits ... Valid Codes/Values:
     * 000-999" (Appendix R-1: "Length N3 Example: 0nnn"). Also cross-checks the stated length against
     * the actual encoded chip data byte count, since the length is defined as identifying "the length
     * of the following field."
     */
    public Assessment assessLengthPrefix(String lengthField, String chipDataValue) {
        if (lengthField == null || !lengthField.matches("\\d{3}")) {
            return assessment(BR_LENGTH_PREFIX, Status.FAIL, "LENGTH_FORMAT",
                    "EMV Chip Data Length (Element 189) must be exactly three numeric digits in 000-999 "
                            + "(Appendix R-1 'Length N3 Example: 0nnn').");
        }
        String hex = normalizeHex(chipDataValue);
        if (hex == null) {
            return assessment(BR_LENGTH_PREFIX, Status.FAIL, "LENGTH_FORMAT",
                    "EMV Chip Data value is not valid BCD-packed/hex nibble data; length cannot be "
                            + "cross-checked against it.");
        }
        int statedLength = Integer.parseInt(lengthField);
        int actualLength = hex.length() / 2;
        if (statedLength != actualLength) {
            return assessment(BR_LENGTH_PREFIX, Status.FAIL, "LENGTH_FORMAT",
                    "Stated EMV Chip Data Length (" + statedLength + ") does not match the actual encoded "
                            + "chip data byte count (" + actualLength + ").");
        }
        return assessment(BR_LENGTH_PREFIX, Status.PASS, "LENGTH_FORMAT",
                "EMV Chip Data Length (" + statedLength + ") is a valid three-digit value matching the "
                        + "encoded chip data byte count.");
    }

    /**
     * Element 190 (EMV Chip Data) binary tag/length/value decoding rules: the sub-element stream must
     * decompose cleanly with no truncated tag, length, or value field and no leftover bytes.
     */
    public Assessment assessTlvFormat(TlvDecodeResult decode) {
        Status status = decode.wellFormed() ? Status.PASS : Status.FAIL;
        return assessment(BR_TLV_FORMAT, status, "TLV_DECOMPOSITION", decode.reason());
    }

    /**
     * "For the EMV transaction to be processed properly, the Application ID (AID) TLV (tag 9F06) or
     * Dedicated File Name (tag 84) must be included in Data Element No. 190 (EMV Chip Data)."
     */
    public Assessment assessMandatoryIdentifier(TlvDecodeResult decode) {
        if (!decode.wellFormed()) {
            return assessment(BR_MANDATORY_IDENTIFIER, Status.FAIL, "TAG_PRESENCE",
                    "TLV structure is invalid; mandatory Application Identifier/DF Name presence cannot "
                            + "be evaluated.");
        }
        boolean hasAid = decode.entries().stream().anyMatch(e -> e.tag().equalsIgnoreCase(AID_TAG));
        boolean hasDfName = decode.entries().stream().anyMatch(e -> e.tag().equalsIgnoreCase(DF_NAME_TAG));
        if (!hasAid && !hasDfName) {
            return assessment(BR_MANDATORY_IDENTIFIER, Status.FAIL, "TAG_PRESENCE",
                    "Neither Application Identifier (tag 9F06) nor Dedicated File Name (tag 84) is "
                            + "present; Element 190 requires at least one.");
        }
        return assessment(BR_MANDATORY_IDENTIFIER, Status.PASS, "TAG_PRESENCE",
                "Application Identifier and/or Dedicated File Name tag is present as required.");
    }

    /**
     * "Note: Tag '5A' (PAN) and Tag '57' (Track 2 equivalent data) must not be included in Data
     * Element 190 - EMV Chip Data."
     */
    public Assessment assessProhibitedSensitiveTag(TlvDecodeResult decode) {
        if (!decode.wellFormed()) {
            return assessment(BR_PROHIBITED_SENSITIVE_TAG, Status.FAIL, "TAG_PROHIBITION",
                    "TLV structure is invalid; prohibited-tag check cannot be evaluated.");
        }
        List<String> prohibited = new ArrayList<>();
        for (TlvEntry entry : decode.entries()) {
            if (entry.tag().equalsIgnoreCase(PAN_TAG)) {
                prohibited.add("5A (PAN)");
            }
            if (entry.tag().equalsIgnoreCase(TRACK2_TAG)) {
                prohibited.add("57 (Track 2 equivalent data)");
            }
        }
        if (!prohibited.isEmpty()) {
            return assessment(BR_PROHIBITED_SENSITIVE_TAG, Status.FAIL, "TAG_PROHIBITION",
                    "Prohibited tag(s) present: " + String.join(", ", prohibited) + ".");
        }
        return assessment(BR_PROHIBITED_SENSITIVE_TAG, Status.PASS, "TAG_PROHIBITION",
                "Neither PAN (tag 5A) nor Track 2 equivalent data (tag 57) is present, as required.");
    }

    /**
     * "If the same data element appears several times on the card to terminal interface, this field
     * must contain the last value that was present ... during the transaction." Duplicate tags cannot
     * be ruled invalid from a submitted snapshot alone (the correct "last value" cannot be independently
     * confirmed), so this is flagged {@code REVIEW_REQUIRED} rather than {@code FAIL}.
     */
    public Assessment assessDuplicateTagReview(TlvDecodeResult decode) {
        if (!decode.wellFormed()) {
            return assessment(BR_DUPLICATE_TAG_REVIEW, Status.FAIL, "DUPLICATE_TAG",
                    "TLV structure is invalid; duplicate-tag review cannot be evaluated.");
        }
        Set<String> seen = new LinkedHashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();
        for (TlvEntry entry : decode.entries()) {
            if (!seen.add(entry.tag())) {
                duplicates.add(entry.tag());
            }
        }
        if (!duplicates.isEmpty()) {
            return assessment(BR_DUPLICATE_TAG_REVIEW, Status.REVIEW_REQUIRED, "DUPLICATE_TAG",
                    "Tag(s) " + duplicates + " appear more than once; Element 190 requires the last value "
                            + "present on the card-to-terminal interface, which cannot be independently "
                            + "verified from a submitted snapshot alone.");
        }
        return assessment(BR_DUPLICATE_TAG_REVIEW, Status.PASS, "DUPLICATE_TAG",
                "No tag appears more than once in the decoded TLV stream.");
    }

    /**
     * Element 190's own processing rule: "Required in all EMV transactions." Checks only that Element
     * 189/190 are present when the transaction is EMV - not their content, which the other assessments
     * above cover.
     */
    public Assessment assessEmvCompanion(String lengthField, String chipDataValue, TransactionContext context) {
        if (context == null || context.emvTransaction() == null) {
            return assessment(BR_EMV_COMPANION, Status.REVIEW_REQUIRED, "FIELD_PRESENCE",
                    "EMV-transaction flag not provided; cannot assert whether Element 189 (EMV Chip Data "
                            + "Length) and Element 190 (EMV Chip Data) presence is required.");
        }
        boolean present = lengthField != null && !lengthField.isBlank()
                && chipDataValue != null && !chipDataValue.isBlank();
        if (context.emvTransaction()) {
            if (!present) {
                return assessment(BR_EMV_COMPANION, Status.FAIL, "FIELD_PRESENCE",
                        "Transaction is EMV but Element 189/190 are missing; they are 'Required in all "
                                + "EMV transactions' per Element 190's Processing Rules.");
            }
            return assessment(BR_EMV_COMPANION, Status.PASS, "FIELD_PRESENCE",
                    "Transaction is EMV and Element 189/190 are present as required.");
        }
        return assessment(BR_EMV_COMPANION, Status.PASS, "FIELD_PRESENCE",
                "Transaction is not EMV; Element 189/190 presence is not required by their own "
                        + "definition.");
    }

    /**
     * Neither Appendix R nor Element 189/190's own definitions state a formula linking decoded EMV tag
     * values (e.g. 9F02 Amount Authorized, 5F2A Transaction Currency Code, 9C Transaction Type, 9F1A
     * Terminal Country Code) to the corresponding Segment 100 elements, so agreement between them is
     * always out of this appendix's independently-assertable scope.
     */
    public Assessment assessCrossField() {
        return assessment(BR_CROSS_FIELD, Status.REVIEW_REQUIRED, "CROSS_FIELD_NOT_IN_APPENDIX_R",
                "Agreement between decoded EMV tag values and the corresponding Segment 100 elements is "
                        + "not stated anywhere in Appendix R or in Element 189/190's own definitions, so it "
                        + "is not independently assertable from them alone.");
    }

    /** Strips backslash delimiters/whitespace and validates the remainder is well-formed hex (even length, hex digits only). */
    private static String normalizeHex(String raw) {
        if (raw == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : raw.toCharArray()) {
            if (c == '\\' || Character.isWhitespace(c)) {
                continue;
            }
            sb.append(c);
        }
        String hex = sb.toString();
        if (hex.isEmpty() || hex.length() % 2 != 0) {
            return null;
        }
        for (char c : hex.toCharArray()) {
            if (Character.digit(c, 16) < 0) {
                return null;
            }
        }
        return hex.toUpperCase(Locale.ROOT);
    }

    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }

    private static Assessment assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
        return new Assessment(businessRequirementId, status, assertionScope, reason);
    }
}
