package com.coreauth.validator.canonical;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Independent, bounded checks for ATL105 Appendix S (Valid CA Public Key File Record Layout), the
 * record layout of the CA Public Key File used in conjunction with the EMV "CA Public Key File Data"
 * element and the Data Segment No. 132 (CA Public Key File Load Segment) exchange.
 *
 * <p>Transcribed from {@code specifications/ATL105/docs/specs/extracted_text.txt}, Appendix S-1
 * through S-2 (around lines 35450-35507): the CA Public Key File is a standalone ASCII file,
 * comma-field-separated and CRLF-terminated (Hex {@code 0D0A}), containing one header record followed
 * by one or more Public Key records. This is a file artifact exchanged alongside EMV key-load
 * processing, not a field carried inside the Segment 100 authorization payload itself (confirmed by
 * cross-checking Data Segment No. 132 at line ~14234, which only carries a checksum/version summary of
 * this file, not the file's own content) - so, as with Appendix N/O/Q/R, this oracle is intentionally
 * standalone and not wired into {@code Segment100PayloadValidator}.
 *
 * <p>Header record (two comma-separated fields): File Name ({@code AN}, max 8, fixed value
 * {@code CA_KEYS}) and File Version ({@code N}, max 4, e.g. {@code 0100}).
 *
 * <p>Each Public Key (key) record has eight comma-separated fields, split across the appendix's two
 * pages but forming one continuous record layout matching the well-known CA Public Key (CAPK) record
 * fields used industry-wide for EMV terminal key management: Expiry Date ({@code N8}, format
 * {@code MMDDYYYY}), Certification Authority Hash Algorithm Indicator ({@code N2}, fixed {@code 01} -
 * only SHA-1 currently supported), Certification Authority Public Key Algorithm Indicator ({@code N2},
 * fixed {@code 01} - only RSA currently supported), Registered Application Provider Identifier/RID
 * ({@code AN}, max 10), Index ({@code Hex}, exactly 2 hex characters), Modulus ({@code Hex}, variable
 * length), Exponent ({@code N}, 2 or 6 characters, value {@code 03} or {@code 2^16+1} i.e.
 * {@code 065537}), and Certification Authority Public Key Check Sum ({@code Hex}, exactly 40 hex
 * characters - "a check value calculated on the concatenation of all parts of the Certification
 * Authority Public Key (RID, ... Index, ... Modulus, ... Exponent) using SHA-1").
 *
 * <p>The checksum definition is the one genuinely cross-field, cryptographically verifiable rule in
 * this appendix (mirroring Appendix N's Luhn check digit and Appendix O's Luhn token check digit): it
 * is independently recomputed here as {@code SHA-1(RID || Index || Modulus || Exponent)} over the
 * hex-decoded bytes of each field, in the order the appendix lists them.
 *
 * <p>{@link #BR_CA_EMV_LINK} is always {@code REVIEW_REQUIRED}: resolving whether a given EMV AID
 * actually maps to a specific production CA key requires a real, network-issued CA Public Key File (a
 * synthetic key cannot validate genuine AID-to-key resolution), and Appendix S itself states only the
 * record layout, not an AID-to-key resolution formula - the same reasoning Appendix R uses for its
 * {@code assessCrossField()} BR.
 *
 * <p>Out of scope for every check below: whether a given RSA modulus/exponent pair is cryptographically
 * sound as a real CA key (Appendix S states only field format/length, not key strength policy).
 */
public final class AppendixSCaPublicKeyFileOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED,
        NOT_ASSERTABLE
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    /** One decoded CA Public Key (key) record. */
    public record KeyRecord(
            String expiryDate,
            String hashAlgorithmIndicator,
            String pubKeyAlgorithmIndicator,
            String rid,
            String index,
            String modulus,
            String exponent,
            String checkSum) {
    }

    public static final String BR_HEADER_RECORD = "BR-SEG100-APPS-CA-FILE-HEADER";
    public static final String BR_KEY_RECORD_FRAMING = "BR-SEG100-APPS-CA-KEY-RECORD";
    public static final String BR_EXPIRY_DATE = "BR-SEG100-APPS-EXPIRY-DATE";
    public static final String BR_HASH_ALGORITHM = "BR-SEG100-APPS-HASH-ALGORITHM";
    public static final String BR_PUBKEY_ALGORITHM = "BR-SEG100-APPS-PUBKEY-ALGORITHM";
    public static final String BR_RID_FORMAT = "BR-SEG100-APPS-RID-FORMAT";
    public static final String BR_INDEX_FORMAT = "BR-SEG100-APPS-INDEX-FORMAT";
    public static final String BR_MODULUS_FORMAT = "BR-SEG100-APPS-MODULUS-FORMAT";
    public static final String BR_EXPONENT_VALUE = "BR-SEG100-APPS-EXPONENT-VALUE";
    public static final String BR_CHECKSUM_VERIFICATION = "BR-SEG100-APPS-CHECKSUM-VERIFICATION";
    public static final String BR_CA_EMV_LINK = "BR-SEG100-APPS-CA-EMV-LINK";

    private static final String FILE_NAME = "CA_KEYS";
    private static final String CRLF = "\r\n";
    private static final String SHA1_SUPPORTED_INDICATOR = "01";
    private static final String RSA_SUPPORTED_INDICATOR = "01";

    /** Runs every independently-assertable Appendix S header-record check. */
    public List<Assessment> assessHeader(String headerRecord) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessHeaderRecord(headerRecord));
        return assessments;
    }

    /** Runs every independently-assertable Appendix S key-record check against one raw record line. */
    public List<Assessment> assessKeyRecord(String keyRecordLine) {
        List<Assessment> assessments = new ArrayList<>();
        KeyRecordParseResult parsed = parseKeyRecord(keyRecordLine);
        assessments.add(assessKeyRecordFraming(parsed));
        assessments.add(assessExpiryDate(parsed));
        assessments.add(assessHashAlgorithm(parsed));
        assessments.add(assessPubKeyAlgorithm(parsed));
        assessments.add(assessRidFormat(parsed));
        assessments.add(assessIndexFormat(parsed));
        assessments.add(assessModulusFormat(parsed));
        assessments.add(assessExponentValue(parsed));
        assessments.add(assessChecksumVerification(parsed));
        assessments.add(assessCaEmvLink());
        return assessments;
    }

    /**
     * Whether a given EMV transaction's AID resolves to the correct production CA Public Key requires
     * a real, network-issued CA Public Key File; Appendix S states the record layout but not an
     * AID-to-key resolution formula, so this is always {@code REVIEW_REQUIRED}, mirroring Appendix R's
     * {@code assessCrossField()} posture for the same reason (no stated formula + no synthesizable
     * production key material).
     */
    private Assessment assessCaEmvLink() {
        return new Assessment(BR_CA_EMV_LINK, Status.REVIEW_REQUIRED, "ca-emv-link",
                "Appendix S does not itself state an AID-to-key resolution formula, and confirming a real "
                        + "EMV AID resolves to the correct production CA key requires the actual network-issued "
                        + "CA Public Key File; a synthetic key cannot validate genuine AID-to-key resolution.");
    }

    private record KeyRecordParseResult(boolean wellFormed, KeyRecord record, String reason) {
    }

    /**
     * Splits one raw key-record line into its eight comma-separated fields, requiring a terminating
     * CRLF per Appendix S's framing note ("All fields are comma-separated and the record is
     * terminated with a CRLF").
     */
    private KeyRecordParseResult parseKeyRecord(String keyRecordLine) {
        if (keyRecordLine == null) {
            return new KeyRecordParseResult(false, null, "Key record is null.");
        }
        if (!keyRecordLine.endsWith(CRLF)) {
            return new KeyRecordParseResult(false, null,
                    "Key record is not terminated with CRLF (Hex 0D0A) as required by Appendix S.");
        }
        String body = keyRecordLine.substring(0, keyRecordLine.length() - CRLF.length());
        if (body.contains("\r") || body.contains("\n")) {
            return new KeyRecordParseResult(false, null,
                    "Key record contains an embedded line break before the terminating CRLF.");
        }
        String[] fields = body.split(",", -1);
        if (fields.length != 8) {
            return new KeyRecordParseResult(false, null,
                    "Key record must contain exactly 8 comma-separated fields "
                            + "(Expiry Date, Hash Algorithm Indicator, Public Key Algorithm Indicator, "
                            + "RID, Index, Modulus, Exponent, Check Sum); found " + fields.length + ".");
        }
        KeyRecord record = new KeyRecord(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                fields[6], fields[7]);
        return new KeyRecordParseResult(true, record, null);
    }

    private Assessment assessHeaderRecord(String headerRecord) {
        if (headerRecord == null || !headerRecord.endsWith(CRLF)) {
            return new Assessment(BR_HEADER_RECORD, Status.FAIL, "header-record",
                    "Header record must be terminated with CRLF (Hex 0D0A).");
        }
        String body = headerRecord.substring(0, headerRecord.length() - CRLF.length());
        if (body.contains("\r") || body.contains("\n")) {
            return new Assessment(BR_HEADER_RECORD, Status.FAIL, "header-record",
                    "Header record contains an embedded line break before the terminating CRLF.");
        }
        String[] fields = body.split(",", -1);
        if (fields.length != 2) {
            return new Assessment(BR_HEADER_RECORD, Status.FAIL, "header-record",
                    "Header record must contain exactly 2 comma-separated fields (File Name, File Version); "
                            + "found " + fields.length + ".");
        }
        String fileName = fields[0];
        String fileVersion = fields[1];
        if (!FILE_NAME.equals(fileName)) {
            return new Assessment(BR_HEADER_RECORD, Status.FAIL, "header-record",
                    "File Name must be the fixed value '" + FILE_NAME + "'; found '" + fileName + "'.");
        }
        if (fileVersion.length() != 4 || !fileVersion.chars().allMatch(Character::isDigit)) {
            return new Assessment(BR_HEADER_RECORD, Status.FAIL, "header-record",
                    "File Version must be exactly 4 numeric digits; found '" + fileVersion + "'.");
        }
        return new Assessment(BR_HEADER_RECORD, Status.PASS, "header-record",
                "Header record is 'CA_KEYS' plus a 4-digit File Version, CRLF-terminated.");
    }

    private Assessment assessKeyRecordFraming(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return new Assessment(BR_KEY_RECORD_FRAMING, Status.FAIL, "key-record", parsed.reason());
        }
        return new Assessment(BR_KEY_RECORD_FRAMING, Status.PASS, "key-record",
                "Key record has exactly 8 comma-separated fields and is CRLF-terminated.");
    }

    private Assessment assessExpiryDate(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_EXPIRY_DATE, "expiry-date");
        }
        String expiry = parsed.record().expiryDate();
        if (expiry.length() != 8 || !expiry.chars().allMatch(Character::isDigit)) {
            return new Assessment(BR_EXPIRY_DATE, Status.FAIL, "expiry-date",
                    "Expiry Date must be exactly 8 numeric digits in MMDDYYYY format; found '" + expiry + "'.");
        }
        int month = Integer.parseInt(expiry.substring(0, 2));
        int day = Integer.parseInt(expiry.substring(2, 4));
        int year = Integer.parseInt(expiry.substring(4, 8));
        if (month < 1 || month > 12) {
            return new Assessment(BR_EXPIRY_DATE, Status.FAIL, "expiry-date",
                    "Expiry Date month must be 01-12; found '" + expiry.substring(0, 2) + "'.");
        }
        int maxDay = daysInMonth(month, year);
        if (day < 1 || day > maxDay) {
            return new Assessment(BR_EXPIRY_DATE, Status.FAIL, "expiry-date",
                    "Expiry Date day must be a valid day for MM=" + expiry.substring(0, 2) + "; found '"
                            + expiry.substring(2, 4) + "'.");
        }
        return new Assessment(BR_EXPIRY_DATE, Status.PASS, "expiry-date",
                "Expiry Date is a structurally valid MMDDYYYY calendar date.");
    }

    private static int daysInMonth(int month, int year) {
        boolean leap = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
        return switch (month) {
            case 4, 6, 9, 11 -> 30;
            case 2 -> leap ? 29 : 28;
            default -> 31;
        };
    }

    private Assessment assessHashAlgorithm(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_HASH_ALGORITHM, "hash-algorithm-indicator");
        }
        String indicator = parsed.record().hashAlgorithmIndicator();
        if (!SHA1_SUPPORTED_INDICATOR.equals(indicator)) {
            return new Assessment(BR_HASH_ALGORITHM, Status.FAIL, "hash-algorithm-indicator",
                    "Certification Authority Hash Algorithm Indicator must be '01' (SHA-1); "
                            + "Appendix S states no other value is currently supported. Found '" + indicator + "'.");
        }
        return new Assessment(BR_HASH_ALGORITHM, Status.PASS, "hash-algorithm-indicator",
                "Hash Algorithm Indicator is '01' (SHA-1), the only currently supported value.");
    }

    private Assessment assessPubKeyAlgorithm(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_PUBKEY_ALGORITHM, "pubkey-algorithm-indicator");
        }
        String indicator = parsed.record().pubKeyAlgorithmIndicator();
        if (!RSA_SUPPORTED_INDICATOR.equals(indicator)) {
            return new Assessment(BR_PUBKEY_ALGORITHM, Status.FAIL, "pubkey-algorithm-indicator",
                    "Certification Authority Public Key Algorithm Indicator must be '01' (RSA); "
                            + "Appendix S states no other value is currently supported. Found '" + indicator + "'.");
        }
        return new Assessment(BR_PUBKEY_ALGORITHM, Status.PASS, "pubkey-algorithm-indicator",
                "Public Key Algorithm Indicator is '01' (RSA), the only currently supported value.");
    }

    private Assessment assessRidFormat(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_RID_FORMAT, "rid");
        }
        String rid = parsed.record().rid();
        if (rid.isEmpty() || rid.length() > 10 || !isAlphanumeric(rid)) {
            return new Assessment(BR_RID_FORMAT, Status.FAIL, "rid",
                    "Registered Application Provider Identifier (RID) must be 1-10 alphanumeric characters; "
                            + "found '" + rid + "'.");
        }
        return new Assessment(BR_RID_FORMAT, Status.PASS, "rid",
                "RID is 1-10 alphanumeric characters.");
    }

    private Assessment assessIndexFormat(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_INDEX_FORMAT, "index");
        }
        String index = parsed.record().index();
        if (index.length() != 2 || !isHex(index)) {
            return new Assessment(BR_INDEX_FORMAT, Status.FAIL, "index",
                    "Index must be exactly 2 hex characters; found '" + index + "'.");
        }
        return new Assessment(BR_INDEX_FORMAT, Status.PASS, "index",
                "Index is exactly 2 hex characters.");
    }

    private Assessment assessModulusFormat(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_MODULUS_FORMAT, "modulus");
        }
        String modulus = parsed.record().modulus();
        if (modulus.isEmpty() || modulus.length() % 2 != 0 || !isHex(modulus)) {
            return new Assessment(BR_MODULUS_FORMAT, Status.FAIL, "modulus",
                    "Modulus must be non-empty, even-length hex (whole bytes); found '" + modulus + "'.");
        }
        return new Assessment(BR_MODULUS_FORMAT, Status.PASS, "modulus",
                "Modulus is non-empty, even-length hex.");
    }

    private Assessment assessExponentValue(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_EXPONENT_VALUE, "exponent");
        }
        String exponent = parsed.record().exponent();
        if (!"03".equals(exponent) && !"065537".equals(exponent)) {
            return new Assessment(BR_EXPONENT_VALUE, Status.FAIL, "exponent",
                    "Exponent must be '03' or '065537' (2^16+1); Appendix S states no other value is defined. "
                            + "Found '" + exponent + "'.");
        }
        return new Assessment(BR_EXPONENT_VALUE, Status.PASS, "exponent",
                "Exponent is one of the two values Appendix S defines ('03' or '065537').");
    }

    private Assessment assessChecksumVerification(KeyRecordParseResult parsed) {
        if (!parsed.wellFormed()) {
            return cascadeFailure(BR_CHECKSUM_VERIFICATION, "checksum");
        }
        KeyRecord record = parsed.record();
        String checkSum = record.checkSum();
        if (checkSum.length() != 40 || !isHex(checkSum)) {
            return new Assessment(BR_CHECKSUM_VERIFICATION, Status.FAIL, "checksum",
                    "Certification Authority Public Key Check Sum must be exactly 40 hex characters "
                            + "(a SHA-1 digest); found '" + checkSum + "'.");
        }
        if (!isEvenLengthHex(record.rid()) || !isEvenLengthHex(record.index())
                || !isEvenLengthHex(record.modulus()) || !isEvenLengthHex(record.exponent())) {
            return new Assessment(BR_CHECKSUM_VERIFICATION, Status.FAIL, "checksum",
                    "Check Sum cannot be independently recomputed because RID, Index, Modulus, or Exponent "
                            + "is not valid even-length hex.");
        }
        String computed = computeChecksum(record.rid(), record.index(), record.modulus(), record.exponent());
        if (computed == null) {
            return new Assessment(BR_CHECKSUM_VERIFICATION, Status.FAIL, "checksum",
                    "Check Sum could not be recomputed (SHA-1 unavailable in this JVM).");
        }
        if (!computed.equalsIgnoreCase(checkSum)) {
            return new Assessment(BR_CHECKSUM_VERIFICATION, Status.FAIL, "checksum",
                    "Check Sum does not match SHA-1(RID || Index || Modulus || Exponent). Expected '" + computed
                            + "', found '" + checkSum.toUpperCase(Locale.ROOT) + "'.");
        }
        return new Assessment(BR_CHECKSUM_VERIFICATION, Status.PASS, "checksum",
                "Check Sum matches the independently recomputed SHA-1(RID || Index || Modulus || Exponent).");
    }

    /**
     * Independently recomputes the Certification Authority Public Key Check Sum as
     * {@code SHA-1(RID || Index || Modulus || Exponent)} over the hex-decoded bytes of each field, in
     * the order Appendix S lists them, returning the result as upper-case hex. Returns {@code null} if
     * SHA-1 is unavailable.
     */
    public static String computeChecksum(String ridHex, String indexHex, String modulusHex, String exponentHex) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(hexToBytes(ridHex));
            digest.update(hexToBytes(indexHex));
            digest.update(hexToBytes(modulusHex));
            digest.update(hexToBytes(exponentHex));
            byte[] result = digest.digest();
            StringBuilder hex = new StringBuilder();
            for (byte b : result) {
                hex.append(String.format("%02X", b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static Assessment cascadeFailure(String businessRequirementId, String scope) {
        return new Assessment(businessRequirementId, Status.FAIL, scope,
                "Key record framing is invalid, so this field cannot be independently evaluated.");
    }

    private static boolean isAlphanumeric(String value) {
        return value.chars().allMatch(Character::isLetterOrDigit);
    }

    private static boolean isEvenLengthHex(String value) {
        return isHex(value) && value.length() % 2 == 0;
    }

    private static boolean isHex(String value) {
        if (value.isEmpty()) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (Character.digit(value.charAt(i), 16) < 0) {
                return false;
            }
        }
        return true;
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return result;
    }
}
