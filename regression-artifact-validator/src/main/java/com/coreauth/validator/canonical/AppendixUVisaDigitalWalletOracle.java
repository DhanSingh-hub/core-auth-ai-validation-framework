package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;

/**
 * Independent, bounded checks for ATL105 Appendix U (Visa Digital Wallet), transcribed from
 * {@code specifications/ATL105/docs/specs/extracted_text.txt}, Appendix U-1 (lines ~35624-35653).
 *
 * <p>Appendix U's own text is narrative only - it describes two Visa Digital Wallet categories
 * (Pass-through and Staged) and their business-process characteristics, with no field layout, code
 * table, or message-format definition of its own. The only concrete, independently-assertable field
 * anchored to Appendix U anywhere in the specification is the <b>Wallet Method Indicator</b>, Data
 * Segment No. 111, Table ID 049 (Merchant Supplementary Data), Sub Table ID "10" (main-body text,
 * lines ~28543-28565):
 *
 * <ul>
 *   <li>Sub-Table ID: {@code an2}, fixed value {@code 10}.
 *   <li>Sub-Table Length: {@code an3}, fixed length {@code 001}.
 *   <li>Sub-Table Data: {@code a}, exactly 1 character, Valid Codes/Values {@code S} (Staged Digital
 *       Wallet) or {@code P} (Pass-through Wallet) - the field's own text says "Refer Appendix U for
 *       more details on Digital Wallet", making this the sole structural anchor between the main
 *       authorization message and Appendix U's two narrative categories.
 * </ul>
 *
 * <p>Every other rule stated in Appendix U-1 - cardholder written consent before a pass-through DWO
 * completes a transaction, a pass-through DWO not performing Visa payment services for another DWO,
 * and the staged wallet's Purchase/Funding stage behavior and cross-transaction correlation - is a
 * business-process or cross-message rule with no corresponding field in any single Segment 100/130
 * request or response. None of these can be independently asserted from one message snapshot, so they
 * stay {@code REVIEW_REQUIRED} by design, the same bounding used for Appendix Q's flow-consistency BR,
 * Appendix R's cross-field BR, and Appendix T's echo BR.
 *
 * <p>This is a standalone companion-field check, not wired into {@code Segment100PayloadValidator}:
 * the Wallet Method Indicator is a Sub-Table of Data Segment 111 (Table ID 049), not part of the
 * Segment 100 payload itself.
 */
public final class AppendixUVisaDigitalWalletOracle {

    public enum Status {
        PASS,
        FAIL,
        REVIEW_REQUIRED
    }

    public record Assessment(String businessRequirementId, Status status, String assertionScope, String reason) {
    }

    public static final String BR_WALLET_INDICATOR_FORMAT = "BR-SEG100-APPU-WALLET-INDICATOR-FORMAT";
    public static final String BR_CATEGORY_CLASSIFICATION = "BR-SEG100-APPU-CATEGORY-CLASSIFICATION";
    public static final String BR_PASS_THROUGH = "BR-SEG100-APPU-PASS-THROUGH";
    public static final String BR_DWO_CONSENT = "BR-SEG100-APPU-DWO-CONSENT";
    public static final String BR_DWO_EXCLUSIVITY = "BR-SEG100-APPU-DWO-EXCLUSIVITY";
    public static final String BR_STAGED_PURCHASE = "BR-SEG100-APPU-STAGED-PURCHASE";
    public static final String BR_STAGED_FUNDING = "BR-SEG100-APPU-STAGED-FUNDING";
    public static final String BR_STAGED_CORRELATION = "BR-SEG100-APPU-STAGED-CORRELATION";

    private static final String SUB_TABLE_ID = "10";
    private static final String SUB_TABLE_LENGTH = "001";
    private static final String STAGED_CODE = "S";
    private static final String PASS_THROUGH_CODE = "P";

    /**
     * Runs every independently-assertable Appendix U check for one Wallet Method Indicator
     * occurrence.
     *
     * @param subTableId     Sub-Table ID raw value (expected fixed {@code 10})
     * @param subTableLength Sub-Table Length raw value (expected fixed {@code 001})
     * @param value          Sub-Table Data raw value (expected 1 character, {@code S} or {@code P})
     */
    public List<Assessment> assessSection(String subTableId, String subTableLength, String value) {
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(assessWalletIndicatorFormat(subTableId, subTableLength, value));
        assessments.add(assessCategoryClassification(value));
        assessments.add(assessPassThrough());
        assessments.add(assessDwoConsent());
        assessments.add(assessDwoExclusivity());
        assessments.add(assessStagedPurchase());
        assessments.add(assessStagedFunding());
        assessments.add(assessStagedCorrelation());
        return assessments;
    }

    private Assessment assessWalletIndicatorFormat(String subTableId, String subTableLength, String value) {
        if (!SUB_TABLE_ID.equals(subTableId)) {
            return new Assessment(BR_WALLET_INDICATOR_FORMAT, Status.FAIL, "wallet-indicator-format",
                    "Wallet Method Indicator Sub-Table ID must be fixed value '10'; found '" + subTableId + "'.");
        }
        if (!SUB_TABLE_LENGTH.equals(subTableLength)) {
            return new Assessment(BR_WALLET_INDICATOR_FORMAT, Status.FAIL, "wallet-indicator-format",
                    "Wallet Method Indicator Sub-Table Length must be fixed value '001'; found '"
                            + subTableLength + "'.");
        }
        if (!STAGED_CODE.equals(value) && !PASS_THROUGH_CODE.equals(value)) {
            return new Assessment(BR_WALLET_INDICATOR_FORMAT, Status.FAIL, "wallet-indicator-format",
                    "Wallet Method Indicator value must be exactly 1 character, 'S' or 'P'; found '"
                            + value + "'.");
        }
        return new Assessment(BR_WALLET_INDICATOR_FORMAT, Status.PASS, "wallet-indicator-format",
                "Wallet Method Indicator Sub-Table ID/Length are the stated fixed values and the value is "
                        + "one of the two stated codes, 'S' or 'P'.");
    }

    private Assessment assessCategoryClassification(String value) {
        if (!STAGED_CODE.equals(value) && !PASS_THROUGH_CODE.equals(value)) {
            return new Assessment(BR_CATEGORY_CLASSIFICATION, Status.FAIL, "category-classification",
                    "Cannot classify a Visa Digital Wallet transaction category from an unrecognized "
                            + "Wallet Method Indicator value '" + value + "'.");
        }
        String category = STAGED_CODE.equals(value) ? "Staged Digital Wallet" : "Pass-through Digital Wallet";
        return new Assessment(BR_CATEGORY_CLASSIFICATION, Status.PASS, "category-classification",
                "Wallet Method Indicator value '" + value + "' classifies this transaction as Appendix U's '"
                        + category + "' category. Confirming that the category-specific processing rules "
                        + "(pass-through credential handling, staged Purchase/Funding stage behavior) were "
                        + "actually applied remains out of scope of this format/classification check.");
    }

    /**
     * Appendix U-1 states a pass-through Digital Wallet "[c]ompletes a transaction by transferring the
     * stored credential to the merchant without interrupting the flow of funds." No field in Segment
     * 100/130 states what the originally-stored credential was, so preservation of that credential
     * cannot be independently verified from a single authorization message.
     */
    private Assessment assessPassThrough() {
        return new Assessment(BR_PASS_THROUGH, Status.REVIEW_REQUIRED, "pass-through",
                "Appendix U-1 states a pass-through wallet transfers the cardholder's stored credential "
                        + "to the merchant without interrupting the flow of funds, but no field in Segment "
                        + "100/130 records what credential was originally stored with the Digital Wallet "
                        + "Operator, so preservation cannot be verified from one message.");
    }

    /**
     * Appendix U-1's written-consent requirement is a business/compliance process between the
     * cardholder and the Digital Wallet Operator, with no corresponding field in the ATL105 message set.
     */
    private Assessment assessDwoConsent() {
        return new Assessment(BR_DWO_CONSENT, Status.REVIEW_REQUIRED, "dwo-consent",
                "Appendix U-1 requires the pass-through Digital Wallet Operator to obtain the "
                        + "cardholder's written consent before using their account in the wallet; this is a "
                        + "DWO-side compliance record with no corresponding ATL105 message field.");
    }

    /**
     * Appendix U-1's DWO-exclusivity rule ("must not perform Visa payment services for another DWO")
     * is a network/operator-registration constraint, not something any single authorization message
     * states or can confirm.
     */
    private Assessment assessDwoExclusivity() {
        return new Assessment(BR_DWO_EXCLUSIVITY, Status.REVIEW_REQUIRED, "dwo-exclusivity",
                "Appendix U-1 requires a pass-through Digital Wallet Operator to not perform Visa "
                        + "payment services for another DWO; this is a network/operator-registration "
                        + "constraint with no corresponding ATL105 message field to assert it against.");
    }

    /**
     * Appendix U-1 states the staged wallet Purchase stage "[p]ays the retailer using the account
     * assigned by the SDWO." Confirming the account used was in fact SDWO-assigned (versus the
     * cardholder's own Visa account) requires out-of-band wallet-operator configuration data, not
     * anything stated in the authorization message itself.
     */
    private Assessment assessStagedPurchase() {
        return new Assessment(BR_STAGED_PURCHASE, Status.REVIEW_REQUIRED, "staged-purchase",
                "Appendix U-1 states the staged wallet Purchase stage pays the retailer using the "
                        + "SDWO-assigned account, but confirming an account was in fact SDWO-assigned requires "
                        + "the Staged Digital Wallet Operator's own account configuration, not anything "
                        + "stated in the authorization message.");
    }

    /**
     * Appendix U-1 states the staged wallet Funding stage "[u]ses the Visa account number provided by
     * the cardholder to fund or reimburse the Staged Digital Wallet." Like the Purchase stage, this
     * depends on the SDWO's own account linkage, not a message-level field.
     */
    private Assessment assessStagedFunding() {
        return new Assessment(BR_STAGED_FUNDING, Status.REVIEW_REQUIRED, "staged-funding",
                "Appendix U-1 states the staged wallet Funding stage uses the cardholder's Visa account "
                        + "number to fund or reimburse the wallet, but confirming that linkage requires the "
                        + "Staged Digital Wallet Operator's own account configuration, not anything stated in "
                        + "the authorization message.");
    }

    /**
     * Appendix U-1 states Purchase and Funding "[c]ompletes a transaction (via an SDWO) in more than
     * one stage, in any order." Verifying that both stages belong to the same overall staged-wallet
     * transaction requires correlating multiple messages across a transaction lifecycle, not a single
     * request/response snapshot, mirroring Appendix T's echo BR and Appendix R's cross-field BR.
     */
    private Assessment assessStagedCorrelation() {
        return new Assessment(BR_STAGED_CORRELATION, Status.REVIEW_REQUIRED, "staged-correlation",
                "Appendix U-1 states a staged wallet transaction completes via Purchase and Funding "
                        + "stages in any order, but confirming both stages belong to the same overall staged "
                        + "transaction requires correlating multiple messages across a transaction lifecycle, "
                        + "not a single request/response snapshot.");
    }
}
