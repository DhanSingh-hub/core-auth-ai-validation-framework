package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Independent, bounded checks for ATL105 Appendix Q (Valid National Point-of-Service Condition
 * Codes), carried in Variable Information Element 113, Table ID 030.
 *
 * <p>Transcribed from {@code specifications/ATL105/docs/specs/extracted_text.txt} Appendix Q-1
 * through Q-7 (source lines around 35151-35383, between Appendix P and Appendix R). The ten-digit
 * code is a fixed-position composite: Terminal Class (positions 1-3), Presentation Type (positions
 * 4-7), Security Condition (position 8), and Terminal Type (positions 9-10). This mirrors Appendix
 * J's bounded composite-code pattern ({@link AppendixJPosEntryModeOracle}): recognition/
 * classification of the source-defined digit values only, never transaction, channel, or lifecycle
 * eligibility.
 *
 * <p>Positions 1-3, 4-7, and 8 each explicitly distinguish three digit categories in the source
 * text: an assigned value with a stated meaning, a range "Reserved for national use," and a range
 * "Reserved for private use." A reserved digit is syntactically well-formed but has no source-stated
 * meaning yet, so it is classified {@code REVIEW_REQUIRED}, not {@code FAIL}. Terminal Type (positions
 * 9-10) is a flat list of assigned two-digit codes with no reserved-range language in the source, so
 * any two-digit value outside that list is classified {@code FAIL}.
 *
 * <p>Out of scope for every check below: whether a given code is consistent with the transaction's
 * Prompt Code, POS Entry Mode, lifecycle stage (apart from the eWIC self-checkout case modeled below),
 * or general channel context (Appendix Q does not itself state those cross-field rules), and the
 * Q-4/Q-7 "codes for Position Nos." crosswalk tables, which the source explicitly describes as "a few
 * examples" rather than an exhaustive combination list, so they are not used as a pass/fail oracle here.
 *
 * <p>Three additional rules about this same Table ID 030 field are stated outside the Q-1 through Q-7
 * pages, in the field's own attribute description and in the eWIC section, and are modeled here as
 * context-dependent checks (via {@link TransactionContext}) because they describe Table ID 030 itself
 * rather than merely referencing it from another element's definition:
 * <ul>
 *   <li>Appendix I-19 (Table ID 030 description): "Required for all EMV transactions, all internet
 *       based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File
 *       transactions. Optional for all other transactions." ({@link #BR_REQUIREDNESS})</li>
 *   <li>Appendix I-19 (same description): "For Visa, Terminal/Merchant should send Visa CAVV Data when
 *       Terminal Type values '25' or '26' are used." ({@link #BR_VISA_CAVV})</li>
 *   <li>Section 10.5.5.6 (eWIC Data Elements): "Attended self-checkout lanes are considered unattended
 *       by the eWIC processors. This should be reflected in the value of POS Condition Code." (folded
 *       into {@link #assessFlowConsistency(String, TransactionContext)})</li>
 * </ul>
 * Still out of scope (referenced FROM other elements ABOUT Appendix Q's codes, not stated BY Appendix Q
 * or its field description, and therefore belonging to those other elements' own future validators):
 * the Element 203 "Safekey Data" (Segment 123, NFC payment tokenization) AEVV/AESK mandatory/optional
 * table keyed off Terminal Type 25/26/27, and the Visa-India recurring-payment rule requiring Terminal
 * Type '25' together with CAVV Cryptogram and recurring-transaction-indicator conditions.
 */
public final class AppendixQNationalPosConditionCodeOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED,
        NOT_ASSERTABLE
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    /** Digit-level classification shared by the Terminal Class, Presentation Type, and Security Condition positions. */
    public enum DigitClass {
        ASSIGNED,
        RESERVED_NATIONAL_USE,
        RESERVED_PRIVATE_USE,
        INVALID
    }

    public record DigitClassification(char value, DigitClass digitClass, String description) {
    }

    /**
     * Optional transaction context needed to evaluate the three field-level rules that are stated
     * about Table ID 030 outside the Q-1 through Q-7 pages ({@link #BR_REQUIREDNESS},
     * {@link #BR_VISA_CAVV}, and the eWIC self-checkout case of {@link #BR_FLOW_CONSISTENCY}). Every
     * field is a nullable {@link Boolean}: {@code null} means "not stated/unknown" and yields
     * {@code REVIEW_REQUIRED} rather than guessing.
     */
    public record TransactionContext(
            Boolean emvTransaction,
            Boolean internetBasedTransaction,
            Boolean credentialOnFileTransaction,
            Boolean posConditionCodePresent,
            Boolean visaTransaction,
            Boolean cavvDataPresent,
            Boolean eWicTransaction,
            Boolean selfCheckoutLane) {

        /** Context carrying no known facts; every field-level check falls back to REVIEW_REQUIRED. */
        public static TransactionContext unknown() {
            return new TransactionContext(null, null, null, null, null, null, null, null);
        }
    }

    public static final String BR_COMPOSITION = "BR-SEG100-APPQ-COMPOSITION";
    public static final String BR_TERMINAL = "BR-SEG100-APPQ-TERMINAL";
    public static final String BR_PRESENTATION = "BR-SEG100-APPQ-PRESENTATION";
    public static final String BR_SECURITY = "BR-SEG100-APPQ-SECURITY";
    public static final String BR_FLOW_CONSISTENCY = "BR-SEG100-APPQ-FLOW-CONSISTENCY";
    public static final String BR_REQUIREDNESS = "BR-SEG100-APPQ-REQUIREDNESS";
    public static final String BR_VISA_CAVV = "BR-SEG100-APPQ-VISA-CAVV";

    // Terminal Class, position 1 ("Attended"/"Unattended").
    private static final Map<Character, String> POS1_ASSIGNED = Map.of('0', "Attended", '1', "Unattended");
    private static final Set<Character> POS1_RESERVED_NATIONAL = Set.of('2', '3', '4', '5', '6', '7');
    private static final Set<Character> POS1_RESERVED_PRIVATE = Set.of('8', '9');

    // Terminal Class, position 2 (operator).
    private static final Map<Character, String> POS2_ASSIGNED =
            Map.of('0', "Customer-operated", '1', "Card acceptor-operated", '2', "Administrative");
    private static final Set<Character> POS2_RESERVED_NATIONAL = Set.of('3', '4', '5', '6', '7');
    private static final Set<Character> POS2_RESERVED_PRIVATE = Set.of('8', '9');

    // Terminal Class, position 3 (premise).
    private static final Map<Character, String> POS3_ASSIGNED = Map.of('0', "On premise", '1', "Off premise");
    private static final Set<Character> POS3_RESERVED_NATIONAL = Set.of('2', '3', '4', '5', '6', '7');
    private static final Set<Character> POS3_RESERVED_PRIVATE = Set.of('8', '9');

    // Presentation Type, position 4 (customer presence).
    private static final Map<Character, String> POS4_ASSIGNED = Map.of(
            '0', "Customer present", '1', "Customer not present",
            '2', "Mail and/or telephone order (customer not present)", '8', "Preauthorized purchase");
    private static final Set<Character> POS4_RESERVED_NATIONAL = Set.of('3', '4', '5', '6', '7');
    private static final Set<Character> POS4_RESERVED_PRIVATE = Set.of('9');

    // Presentation Type, position 5 (card presence).
    private static final Map<Character, String> POS5_ASSIGNED =
            Map.of('0', "Card present", '1', "Card not present", '8', "Preauthorized purchase");
    private static final Set<Character> POS5_RESERVED_NATIONAL = Set.of('2', '3', '4', '5', '6', '7');
    private static final Set<Character> POS5_RESERVED_PRIVATE = Set.of('9');

    // Presentation Type, position 6 (card retention capability).
    private static final Map<Character, String> POS6_ASSIGNED = Map.of(
            '0', "Device does not have card retention capability", '1', "Device has card retention capability");
    private static final Set<Character> POS6_RESERVED_NATIONAL = Set.of('2', '3', '4', '5', '6', '7');
    private static final Set<Character> POS6_RESERVED_PRIVATE = Set.of('8', '9');

    // Presentation Type, position 7 (presentment/re-presentment state).
    private static final Map<Character, String> POS7_ASSIGNED = Map.ofEntries(
            Map.entry('0', "Original presentment"), Map.entry('1', "First re-presentment"),
            Map.entry('2', "Second re-presentment"), Map.entry('3', "Third re-presentment"),
            Map.entry('4', "Previously authorized request"), Map.entry('5', "Resubmit"));
    private static final Set<Character> POS7_RESERVED_NATIONAL = Set.of('6', '7');
    private static final Set<Character> POS7_RESERVED_PRIVATE = Set.of('8', '9');

    // Security Condition, position 8.
    private static final Map<Character, String> POS8_ASSIGNED = Map.of(
            '0', "No security concern", '1', "Suspected fraud",
            '2', "Identification verified", '3', "Electronic commerce transaction with digital signature");
    private static final Set<Character> POS8_RESERVED_NATIONAL = Set.of('4', '5', '6', '7');
    private static final Set<Character> POS8_RESERVED_PRIVATE = Set.of('8', '9');

    // Terminal Type, positions 9-10: flat assigned-code list, no reserved-range language in the source.
    private static final Map<String, String> TERMINAL_TYPE = Map.ofEntries(
            Map.entry("00", "Administrative terminal"), Map.entry("01", "Point-of-service terminal"),
            Map.entry("02", "ATM"), Map.entry("03", "Home terminal"), Map.entry("04", "ECR"),
            Map.entry("05", "Dial terminal"), Map.entry("06", "Traveler's check machine"),
            Map.entry("07", "Fuel machine"), Map.entry("08", "Scrip machine"), Map.entry("09", "Coupon machine"),
            Map.entry("10", "Ticket machine"), Map.entry("11", "Point-of-banking terminal"),
            Map.entry("19", "Call Center Operator"), Map.entry("20", "Voice response unit (VRU)"),
            Map.entry("21", "Channel encryption; cardholder certificate is not used"),
            Map.entry("24", "Digital secure remote payment with UCAF data transactions"),
            Map.entry("25", "Internet (nonrecurring) (secure using Verified by Visa or 3DES)"),
            Map.entry("26", "Internet (secure; 3DES attempted)"),
            Map.entry("27", "Internet (secure using Secure Socket Layer [SSL])"),
            Map.entry("28", "Internet (nonsecure)"),
            Map.entry("29", "Internet recurring payment transaction after initial transaction was processed "
                    + "with a mobile device-issued payment token (MasterCard Only)"));

    public Map<String, String> terminalTypes() {
        return TERMINAL_TYPE;
    }

    /** Runs every independently-assertable Appendix Q check against one ten-digit National POS Condition Code. */
    public List<Assessment> assess(String code) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessComposition(code));
        assessments.add(assessTerminal(code));
        assessments.add(assessPresentation(code));
        assessments.add(assessSecurity(code));
        assessments.add(assessFlowConsistency());
        return assessments;
    }

    /**
     * Runs every independently-assertable Appendix Q check, including the three context-dependent
     * field-level rules ({@link #BR_REQUIREDNESS}, {@link #BR_VISA_CAVV}, and the eWIC self-checkout
     * case of {@link #BR_FLOW_CONSISTENCY}) stated about Table ID 030 outside the Q-1 through Q-7 pages.
     */
    public List<Assessment> assess(String code, TransactionContext context) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessComposition(code));
        assessments.add(assessTerminal(code));
        assessments.add(assessPresentation(code));
        assessments.add(assessSecurity(code));
        assessments.add(assessFlowConsistency(code, context));
        assessments.add(assessRequiredness(context));
        assessments.add(assessVisaCavv(code, context));
        return assessments;
    }

    /**
     * "The National Point-of-Service Condition Codes data element contains the following elements:
     * Terminal Class (1-3), Presentation Type (4-7), Security Condition (8), Terminal Type (9-10)."
     * Format and decomposition only.
     */
    public Assessment assessComposition(String code) {
        if (!isWellFormed(code)) {
            return assessment(BR_COMPOSITION, Status.FAIL, "COMPOSITION",
                    "National POS Condition Code must be exactly ten numeric digits decomposed into "
                            + "Terminal Class(3) + Presentation Type(4) + Security Condition(1) + Terminal Type(2).");
        }
        return assessment(BR_COMPOSITION, Status.PASS, "COMPOSITION",
                "Value is ten numeric digits correctly decomposed into Terminal Class, Presentation Type, "
                        + "Security Condition, and Terminal Type.");
    }

    /**
     * Terminal Class (positions 1-3) and Terminal Type (positions 9-10). Recognition/classification
     * only; whether the resulting terminal context agrees with the physical or digital POS channel in
     * use is not independently assertable from Appendix Q alone.
     */
    public Assessment assessTerminal(String code) {
        if (!isWellFormed(code)) {
            return assessment(BR_TERMINAL, Status.FAIL, "CODE_RECOGNITION_ONLY",
                    "National POS Condition Code composition is invalid; terminal class/type cannot be evaluated.");
        }
        DigitClassification pos1 = classify(code.charAt(0), POS1_ASSIGNED, POS1_RESERVED_NATIONAL, POS1_RESERVED_PRIVATE);
        DigitClassification pos2 = classify(code.charAt(1), POS2_ASSIGNED, POS2_RESERVED_NATIONAL, POS2_RESERVED_PRIVATE);
        DigitClassification pos3 = classify(code.charAt(2), POS3_ASSIGNED, POS3_RESERVED_NATIONAL, POS3_RESERVED_PRIVATE);
        String terminalType = code.substring(8, 10);
        if (!TERMINAL_TYPE.containsKey(terminalType)) {
            return assessment(BR_TERMINAL, Status.FAIL, "CODE_RECOGNITION_ONLY",
                    "Terminal Type code '" + terminalType + "' is not one of Appendix Q's listed valid entries.");
        }
        if (pos1.digitClass() != DigitClass.ASSIGNED || pos2.digitClass() != DigitClass.ASSIGNED
                || pos3.digitClass() != DigitClass.ASSIGNED) {
            return assessment(BR_TERMINAL, Status.REVIEW_REQUIRED, "CODE_RECOGNITION_ONLY",
                    "One or more Terminal Class positions fall in a source-reserved (national/private use) range: "
                            + reservedSummary(pos1, pos2, pos3) + ".");
        }
        return assessment(BR_TERMINAL, Status.PASS, "CODE_RECOGNITION_ONLY",
                "Terminal Class (" + pos1.description() + "/" + pos2.description() + "/" + pos3.description()
                        + ") and Terminal Type (" + TERMINAL_TYPE.get(terminalType) + ") are recognized Appendix Q values.");
    }

    /**
     * Presentation Type (positions 4-7): customer presence, card presence, card retention capability,
     * and presentment/re-presentment state. Recognition/classification only.
     */
    public Assessment assessPresentation(String code) {
        if (!isWellFormed(code)) {
            return assessment(BR_PRESENTATION, Status.FAIL, "CODE_RECOGNITION_ONLY",
                    "National POS Condition Code composition is invalid; presentation type cannot be evaluated.");
        }
        DigitClassification pos4 = classify(code.charAt(3), POS4_ASSIGNED, POS4_RESERVED_NATIONAL, POS4_RESERVED_PRIVATE);
        DigitClassification pos5 = classify(code.charAt(4), POS5_ASSIGNED, POS5_RESERVED_NATIONAL, POS5_RESERVED_PRIVATE);
        DigitClassification pos6 = classify(code.charAt(5), POS6_ASSIGNED, POS6_RESERVED_NATIONAL, POS6_RESERVED_PRIVATE);
        DigitClassification pos7 = classify(code.charAt(6), POS7_ASSIGNED, POS7_RESERVED_NATIONAL, POS7_RESERVED_PRIVATE);
        if (pos4.digitClass() != DigitClass.ASSIGNED || pos5.digitClass() != DigitClass.ASSIGNED
                || pos6.digitClass() != DigitClass.ASSIGNED || pos7.digitClass() != DigitClass.ASSIGNED) {
            return assessment(BR_PRESENTATION, Status.REVIEW_REQUIRED, "CODE_RECOGNITION_ONLY",
                    "One or more Presentation Type positions fall in a source-reserved (national/private use) range: "
                            + reservedSummary(pos4, pos5, pos6, pos7) + ".");
        }
        return assessment(BR_PRESENTATION, Status.PASS, "CODE_RECOGNITION_ONLY",
                "Presentation Type (" + pos4.description() + "; " + pos5.description() + "; " + pos6.description()
                        + "; " + pos7.description() + ") is a recognized Appendix Q combination.");
    }

    /** Security Condition (position 8). Recognition/classification only. */
    public Assessment assessSecurity(String code) {
        if (!isWellFormed(code)) {
            return assessment(BR_SECURITY, Status.FAIL, "CODE_RECOGNITION_ONLY",
                    "National POS Condition Code composition is invalid; security condition cannot be evaluated.");
        }
        DigitClassification pos8 = classify(code.charAt(7), POS8_ASSIGNED, POS8_RESERVED_NATIONAL, POS8_RESERVED_PRIVATE);
        if (pos8.digitClass() != DigitClass.ASSIGNED) {
            return assessment(BR_SECURITY, Status.REVIEW_REQUIRED, "CODE_RECOGNITION_ONLY",
                    "Security Condition position falls in a source-reserved (national/private use) range: "
                            + reservedSummary(pos8) + ".");
        }
        return assessment(BR_SECURITY, Status.PASS, "CODE_RECOGNITION_ONLY",
                "Security Condition (" + pos8.description() + ") is a recognized Appendix Q value.");
    }

    /**
     * Appendix Q does not itself state how the National POS Condition Code must relate to the
     * transaction's Prompt Code, POS Entry Mode, lifecycle stage, or eWIC/channel context, so this
     * cross-field consistency claim is always out of this appendix's independently-assertable scope.
     */
    public Assessment assessFlowConsistency() {
        return assessment(BR_FLOW_CONSISTENCY, Status.REVIEW_REQUIRED, "CROSS_FIELD_NOT_IN_APPENDIX_Q",
                "Consistency with Segment 100 Prompt Code, POS Entry Mode, transaction lifecycle, and eWIC/channel "
                        + "context is not stated anywhere in Appendix Q and is not independently assertable from it alone. "
                        + "One narrow exception (eWIC attended self-checkout, section 10.5.5.6) is assertable when a "
                        + "TransactionContext is supplied; see the (code, context) overload of this method.");
    }

    /**
     * Section 10.5.5.6 (eWIC Data Elements): "Attended self-checkout lanes are considered unattended
     * by the eWIC processors. This should be reflected in the value of POS Condition Code." When the
     * context confirms both an eWIC transaction and a self-checkout lane, Terminal Class position 1
     * must read '1' (Unattended) rather than '0' (Attended). Every other cross-field relationship
     * remains out of Appendix Q's independently-assertable scope, as in the no-argument overload.
     */
    public Assessment assessFlowConsistency(String code, TransactionContext context) {
        if (context == null || !Boolean.TRUE.equals(context.eWicTransaction()) || !Boolean.TRUE.equals(context.selfCheckoutLane())) {
            return assessFlowConsistency();
        }
        if (!isWellFormed(code)) {
            return assessment(BR_FLOW_CONSISTENCY, Status.FAIL, "EWIC_SELF_CHECKOUT",
                    "National POS Condition Code composition is invalid; eWIC self-checkout coding cannot be evaluated.");
        }
        char pos1 = code.charAt(0);
        if (pos1 == '1') {
            return assessment(BR_FLOW_CONSISTENCY, Status.PASS, "EWIC_SELF_CHECKOUT",
                    "Terminal Class position 1 is '1' (Unattended), matching section 10.5.5.6: eWIC processors treat "
                            + "attended self-checkout lanes as unattended.");
        }
        return assessment(BR_FLOW_CONSISTENCY, Status.FAIL, "EWIC_SELF_CHECKOUT",
                "Terminal Class position 1 is '" + pos1 + "' (Attended), but section 10.5.5.6 requires eWIC attended "
                        + "self-checkout lanes to be coded '1' (Unattended).");
    }

    /**
     * Appendix I-19 (Table ID 030 field description): "Required for all EMV transactions, all internet
     * based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File
     * transactions. Optional for all other transactions." This is a presence/absence obligation on the
     * field itself, independent of the code's digit values, so it takes a TransactionContext rather
     * than the code string.
     */
    public Assessment assessRequiredness(TransactionContext context) {
        if (context == null) {
            return assessment(BR_REQUIREDNESS, Status.REVIEW_REQUIRED, "FIELD_PRESENCE",
                    "No transaction context supplied; whether this transaction is EMV, internet-based, or "
                            + "Credential on File (which would make the field required) is unknown.");
        }
        boolean required = Boolean.TRUE.equals(context.emvTransaction())
                || Boolean.TRUE.equals(context.internetBasedTransaction())
                || Boolean.TRUE.equals(context.credentialOnFileTransaction());
        boolean knownOptional = Boolean.FALSE.equals(context.emvTransaction())
                && Boolean.FALSE.equals(context.internetBasedTransaction())
                && Boolean.FALSE.equals(context.credentialOnFileTransaction());
        if (required) {
            if (context.posConditionCodePresent() == null) {
                return assessment(BR_REQUIREDNESS, Status.REVIEW_REQUIRED, "FIELD_PRESENCE",
                        "Transaction is EMV, internet-based, or Credential on File, so National POS Condition Code "
                                + "is required per Appendix I-19, but field presence is unknown.");
            }
            if (!context.posConditionCodePresent()) {
                return assessment(BR_REQUIREDNESS, Status.FAIL, "FIELD_PRESENCE",
                        "Transaction is EMV, internet-based, or Credential on File, so National POS Condition Code "
                                + "is required per Appendix I-19, but the field is absent.");
            }
            return assessment(BR_REQUIREDNESS, Status.PASS, "FIELD_PRESENCE",
                    "Transaction is EMV, internet-based, or Credential on File; National POS Condition Code is "
                            + "present as required per Appendix I-19.");
        }
        if (knownOptional) {
            return assessment(BR_REQUIREDNESS, Status.PASS, "FIELD_PRESENCE",
                    "Transaction is none of EMV, internet-based, or Credential on File; National POS Condition "
                            + "Code is optional per Appendix I-19, and its presence or absence is not a defect.");
        }
        return assessment(BR_REQUIREDNESS, Status.REVIEW_REQUIRED, "FIELD_PRESENCE",
                "Transaction context does not confirm or rule out EMV, internet-based, or Credential on File "
                        + "status, so whether National POS Condition Code is required per Appendix I-19 is unknown.");
    }

    /**
     * Appendix I-19 (Table ID 030 field description): "For Visa, Terminal/Merchant should send Visa
     * CAVV Data when Terminal Type values '25' or '26' are used." Terminal Type is read from the
     * code's positions 9-10; whether the transaction is Visa and whether CAVV data is present must
     * come from the TransactionContext.
     */
    public Assessment assessVisaCavv(String code, TransactionContext context) {
        if (!isWellFormed(code)) {
            return assessment(BR_VISA_CAVV, Status.FAIL, "VISA_CAVV_TRIGGER",
                    "National POS Condition Code composition is invalid; Terminal Type cannot be read to evaluate "
                            + "the Visa CAVV trigger.");
        }
        String terminalType = code.substring(8, 10);
        if (!"25".equals(terminalType) && !"26".equals(terminalType)) {
            return assessment(BR_VISA_CAVV, Status.PASS, "VISA_CAVV_TRIGGER",
                    "Terminal Type '" + terminalType + "' is not '25' or '26'; the Appendix I-19 Visa CAVV trigger "
                            + "does not apply.");
        }
        if (context == null || context.visaTransaction() == null) {
            return assessment(BR_VISA_CAVV, Status.REVIEW_REQUIRED, "VISA_CAVV_TRIGGER",
                    "Terminal Type '" + terminalType + "' triggers the Appendix I-19 Visa CAVV note, but whether "
                            + "this is a Visa transaction is unknown.");
        }
        if (!context.visaTransaction()) {
            return assessment(BR_VISA_CAVV, Status.PASS, "VISA_CAVV_TRIGGER",
                    "Terminal Type '" + terminalType + "' is present, but the Appendix I-19 Visa CAVV note is "
                            + "explicitly scoped to Visa transactions, and this transaction is not Visa.");
        }
        if (context.cavvDataPresent() == null) {
            return assessment(BR_VISA_CAVV, Status.REVIEW_REQUIRED, "VISA_CAVV_TRIGGER",
                    "Visa transaction with Terminal Type '" + terminalType + "' triggers the Appendix I-19 Visa "
                            + "CAVV note, but whether CAVV data is present is unknown.");
        }
        if (!context.cavvDataPresent()) {
            return assessment(BR_VISA_CAVV, Status.FAIL, "VISA_CAVV_TRIGGER",
                    "Visa transaction with Terminal Type '" + terminalType + "' per Appendix I-19 should send Visa "
                            + "CAVV Data, but none is present.");
        }
        return assessment(BR_VISA_CAVV, Status.PASS, "VISA_CAVV_TRIGGER",
                "Visa transaction with Terminal Type '" + terminalType + "' sends Visa CAVV Data as noted in "
                        + "Appendix I-19.");
    }

    private static boolean isWellFormed(String code) {
        return code != null && code.matches("[0-9]{10}");
    }

    private static DigitClassification classify(char value, Map<Character, String> assigned,
            Set<Character> reservedNational, Set<Character> reservedPrivate) {
        if (assigned.containsKey(value)) {
            return new DigitClassification(value, DigitClass.ASSIGNED, assigned.get(value));
        }
        if (reservedNational.contains(value)) {
            return new DigitClassification(value, DigitClass.RESERVED_NATIONAL_USE, "Reserved for national use");
        }
        if (reservedPrivate.contains(value)) {
            return new DigitClassification(value, DigitClass.RESERVED_PRIVATE_USE, "Reserved for private use");
        }
        return new DigitClassification(value, DigitClass.INVALID, "Not a defined digit for this position");
    }

    private static String reservedSummary(DigitClassification... classifications) {
        StringBuilder summary = new StringBuilder();
        for (DigitClassification classification : classifications) {
            if (classification.digitClass() != DigitClass.ASSIGNED) {
                if (summary.length() > 0) {
                    summary.append(", ");
                }
                summary.append(classification.value()).append('=').append(classification.description());
            }
        }
        return summary.toString();
    }

    private static Assessment assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
        return new Assessment(businessRequirementId, status, assertionScope, reason);
    }
}
