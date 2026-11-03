package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Independent, bounded checks for ATL105 Appendix N (First Data&trade; Premium Gift Card
 * Magnetic Stripe Data Layout, Track II).
 *
 * <p>Transcribed from {@code specifications/ATL105/docs/specs/extracted_text.txt} Appendix N-1
 * (source lines 35040-35077). Appendix N applies only when a Segment 100 transaction context uses
 * the First Data Premium Gift Card magnetic-stripe flow; it defines a single fixed Track II layout
 * (Start Sentinel, 16-digit Account Number, Separator, Expiration Date, Service Code, PVKI, PVV,
 * Discretionary Data, End Sentinel, LRC) and a serial-number derivation note.
 *
 * <p>This validator answers only "does this Track II record match the Appendix N field widths,
 * separator, expiration shape, and Visa mod-10 check digit, and does the transmitted packet exclude
 * the Start/End Sentinel characters?" It does not certify that a given 6-digit BIN is actually
 * registered to the First Data Premium Gift Card program (no BIN registry is part of this source),
 * does not evaluate Service Code table meaning, and does not evaluate PVV cryptographic correctness
 * &mdash; none of those are defined by Appendix N itself. The PVKI field is explicitly documented as
 * "not currently used," so this validator checks only its presence/length and never its value.
 */
public final class AppendixNPremiumGiftCardTrackValidator {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED,
        NOT_ASSERTABLE
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    /** Field-by-field Track II record per Appendix N-1 (fields 1-10). */
    public record TrackRecord(
            String startSentinel,
            String accountNumber,
            String separator,
            String expirationDate,
            String serviceCode,
            String pvki,
            String pvv,
            String discretionaryData,
            String endSentinel,
            String lrc) {
    }

    public static final String BR_TRACK2_ACCOUNT = "BR-SEG100-APPN-TRACK2-ACCOUNT";
    public static final String BR_TRACK2_FORMAT = "BR-SEG100-APPN-TRACK2-FORMAT";
    public static final String BR_SERIAL_NUMBER = "BR-SEG100-APPN-SERIAL-NUMBER";
    public static final String BR_SERIAL_COMPONENT_LENGTHS = "BR-SEG100-APPN-SERIAL-COMPONENT-LENGTHS";
    public static final String BR_SENTINEL_EXCLUSION = "BR-SEG100-APPN-SENTINEL-EXCLUSION";
    public static final String BR_PVKI_UNUSED = "BR-SEG100-APPN-PVKI-UNUSED";

    /** Runs every independently-assertable Appendix N check against one Track II record. */
    public List<Assessment> assess(TrackRecord record) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessAccountNumber(record.accountNumber()));
        assessments.add(assessTrack2Format(record));
        assessments.add(assessSerialComponentLengths(record.accountNumber()));
        assessments.add(assessSerialNumber(record));
        assessments.add(assessSentinelExclusion(record));
        assessments.add(assessPvkiUnused(record.pvki()));
        return assessments;
    }

    /** Field 2: 16-digit Account Number using the Visa standard mod-10 check digit. */
    public Assessment assessAccountNumber(String accountNumber) {
        if (accountNumber == null || !accountNumber.matches("[0-9]{16}")) {
            return assessment(BR_TRACK2_ACCOUNT, Status.FAIL, "REPRESENTATION",
                    "Account Number must be exactly 16 numeric digits.");
        }
        if (!passesVisaModulo10(accountNumber)) {
            return assessment(BR_TRACK2_ACCOUNT, Status.FAIL, "CHECK_DIGIT",
                    "Account Number fails the Visa modulo-10 (Luhn) check digit in field position 16.");
        }
        return assessment(BR_TRACK2_ACCOUNT, Status.PASS, "REPRESENTATION_AND_CHECK_DIGIT_ONLY",
                "16-digit Account Number passes the Visa mod-10 check digit; membership in the First Data "
                        + "Premium Gift Card BIN registry is not independently assertable from Appendix N alone.");
    }

    /** Fields 3-8: Separator, Expiration Date (YYMM), Service Code, PVKI length, PVV, Discretionary Data. */
    public Assessment assessTrack2Format(TrackRecord record) {
        if (!"=".equals(record.separator())) {
            return assessment(BR_TRACK2_FORMAT, Status.FAIL, "REPRESENTATION",
                    "Field 3 Separator must be the literal '=' character.");
        }
        if (record.expirationDate() == null || !record.expirationDate().matches("[0-9]{2}(0[1-9]|1[0-2])")) {
            return assessment(BR_TRACK2_FORMAT, Status.FAIL, "REPRESENTATION",
                    "Field 4 Expiration Date must be YYMM with a valid month 01-12.");
        }
        if (record.serviceCode() == null || !record.serviceCode().matches("[0-9]{3}")) {
            return assessment(BR_TRACK2_FORMAT, Status.FAIL, "REPRESENTATION",
                    "Field 5 Service Code must be exactly three numeric digits.");
        }
        if (record.pvv() == null || !record.pvv().matches("[0-9]{4}")) {
            return assessment(BR_TRACK2_FORMAT, Status.FAIL, "REPRESENTATION",
                    "Field 7 PVV must be exactly four numeric digits.");
        }
        if (record.discretionaryData() == null || record.discretionaryData().length() != 8) {
            return assessment(BR_TRACK2_FORMAT, Status.FAIL, "REPRESENTATION",
                    "Field 8 Discretionary Data must be exactly 8 characters.");
        }
        return assessment(BR_TRACK2_FORMAT, Status.PASS, "REPRESENTATION_ONLY",
                "Separator, Expiration Date, Service Code, PVV, and Discretionary Data match the source field "
                        + "widths; Service Code table meaning and PVV cryptographic correctness are not evaluated.");
    }

    /** Account Number field 2 decomposes into a 6-byte BIN, 9-byte Partial Account Number, 1-byte check digit. */
    public Assessment assessSerialComponentLengths(String accountNumber) {
        if (accountNumber == null || !accountNumber.matches("[0-9]{16}")) {
            return assessment(BR_SERIAL_COMPONENT_LENGTHS, Status.FAIL, "REPRESENTATION",
                    "Account Number must be 16 digits to decompose into a 6+9+1 byte layout.");
        }
        return assessment(BR_SERIAL_COMPONENT_LENGTHS, Status.PASS, "REPRESENTATION_ONLY",
                "16-digit Account Number decomposes into BIN(6) + Partial Account Number(9) + Visa check digit(1).");
    }

    /** Serial number = 9 bytes following the BIN in the Account Number + first 4 bytes of Discretionary Data. */
    public Assessment assessSerialNumber(TrackRecord record) {
        Optional<String> serial = deriveSerialNumber(record.accountNumber(), record.discretionaryData());
        if (serial.isEmpty()) {
            return assessment(BR_SERIAL_NUMBER, Status.FAIL, "DERIVATION",
                    "Serial number requires a 16-digit Account Number and Discretionary Data of at least 4 characters.");
        }
        return assessment(BR_SERIAL_NUMBER, Status.PASS, "DERIVATION_ONLY",
                "Serial number derives as the 9-byte Partial Account Number plus the first 4 bytes of "
                        + "Discretionary Data (" + serial.get() + "); downstream serial-number usage is not evaluated.");
    }

    /** Derives the Appendix N-1 note's serial number, or empty when inputs cannot support it. */
    public Optional<String> deriveSerialNumber(String accountNumber, String discretionaryData) {
        if (accountNumber == null || !accountNumber.matches("[0-9]{16}")
                || discretionaryData == null || discretionaryData.length() < 4) {
            return Optional.empty();
        }
        String partialAccountNumber = accountNumber.substring(6, 15);
        String discretionaryPrefix = discretionaryData.substring(0, 4);
        return Optional.of(partialAccountNumber + discretionaryPrefix);
    }

    /** Fields 1/9: Start/End Sentinel are cut from the packet and must not reach the transmitted data. */
    public Assessment assessSentinelExclusion(TrackRecord record) {
        String transmitted = transmittedPacket(record);
        boolean startLeaked = record.startSentinel() != null && !record.startSentinel().isEmpty()
                && transmitted.contains(record.startSentinel());
        boolean endLeaked = record.endSentinel() != null && !record.endSentinel().isEmpty()
                && transmitted.contains(record.endSentinel());
        if (startLeaked || endLeaked) {
            return assessment(BR_SENTINEL_EXCLUSION, Status.FAIL, "REPRESENTATION",
                    "Start and/or End Sentinel characters must be cut from the packet transmitted to the host application.");
        }
        return assessment(BR_SENTINEL_EXCLUSION, Status.PASS, "REPRESENTATION_ONLY",
                "Transmitted packet excludes the Start Sentinel (field 1) and End Sentinel (field 9).");
    }

    /** Assembles the packet transmitted to the host application: fields 2-8 and 10 only (sentinels cut). */
    public String transmittedPacket(TrackRecord record) {
        StringBuilder packet = new StringBuilder();
        appendIfPresent(packet, record.accountNumber());
        appendIfPresent(packet, record.separator());
        appendIfPresent(packet, record.expirationDate());
        appendIfPresent(packet, record.serviceCode());
        appendIfPresent(packet, record.pvki());
        appendIfPresent(packet, record.pvv());
        appendIfPresent(packet, record.discretionaryData());
        appendIfPresent(packet, record.lrc());
        return packet.toString();
    }

    private static void appendIfPresent(StringBuilder builder, String value) {
        if (value != null) {
            builder.append(value);
        }
    }

    /** Field 6: PVKI is documented as not currently used; only presence/length is checked, never its value. */
    public Assessment assessPvkiUnused(String pvki) {
        if (pvki == null || pvki.length() != 1) {
            return assessment(BR_PVKI_UNUSED, Status.FAIL, "REPRESENTATION",
                    "Field 6 PVKI must occupy exactly one character position, even though it carries no required value.");
        }
        return assessment(BR_PVKI_UNUSED, Status.PASS, "LENGTH_ONLY_NO_VALUE_SEMANTICS",
                "PVKI field-width presence is verified; its value is intentionally not evaluated because Appendix N "
                        + "documents PVKI as not currently used by the First Data Premium Gift Card system.");
    }

    /** Standard Visa/Luhn modulo-10 check digit over all 16 digits (including the check digit itself). */
    private static boolean passesVisaModulo10(String sixteenDigits) {
        int sum = 0;
        boolean doubleDigit = true;
        for (int i = sixteenDigits.length() - 2; i >= 0; i--) {
            int digit = sixteenDigits.charAt(i) - '0';
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        int checkDigit = sixteenDigits.charAt(sixteenDigits.length() - 1) - '0';
        return (sum + checkDigit) % 10 == 0;
    }

    private static Assessment assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
        return new Assessment(businessRequirementId, status, assertionScope, reason);
    }
}
