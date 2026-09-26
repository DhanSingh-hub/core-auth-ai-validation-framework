package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

import java.util.Map;
import java.util.Set;

/**
 * Segment 103 applicability matrix (SEG103-R-002, SEG103-R-024).
 *
 * <p>Resolves the ATL105 Section 10.5.3 (EBT Supported Transaction Types), 10.5.4.8 (Approved
 * HIP transaction data), 10.5.5 (eWIC Transaction Processing), and 10.5.6 (HIP Transaction
 * Processing) text into a deterministic requirement per transaction/lifecycle role:
 *
 * <ul>
 *   <li>{@code REQUIRED} — the transaction cannot be represented without EBT Data Segment data
 *       (a Voucher ID or WIC Discount/Product Data field that the transaction's own definition
 *       mandates).</li>
 *   <li>{@code OPTIONAL} — the transaction's core definition does not mandate EBT-specific
 *       data; Segment 103 appears only when Clerk ID, HIP program data (state-dependent), or
 *       other conditional fields are supplied.</li>
 *   <li>{@code PROHIBITED} — the transaction is explicitly unsupported by the specification
 *       ("These specifications do not support Return transactions" for eWIC, Section
 *       10.5.5.1).</li>
 * </ul>
 */
public final class Segment103ApplicabilityValidator {
    private static final String SOURCE = "Segment103Applicability";

    public enum Applicability { REQUIRED, OPTIONAL, PROHIBITED }

    private static final Map<String, Applicability> MATRIX = Map.ofEntries(
        // Section 10.5.3 Supported Transaction Types (EBT Food Stamp / Cash Benefit)
        Map.entry("FOOD_STAMP_PURCHASE", Applicability.OPTIONAL),
        Map.entry("FOOD_STAMP_REVERSAL_VOID", Applicability.OPTIONAL),
        Map.entry("FOOD_STAMP_RETURN", Applicability.OPTIONAL),
        Map.entry("CASH_BENEFIT_PURCHASE", Applicability.OPTIONAL),
        Map.entry("CASH_BENEFIT_REVERSAL_VOID", Applicability.OPTIONAL),
        Map.entry("FOOD_STAMP_BALANCE_INQUIRY", Applicability.OPTIONAL),
        // Section 10.5.2: Voucher Number "must appear" -> Voucher ID (Element 109) is mandatory.
        Map.entry("FOOD_STAMP_ELECTRONIC_VOUCHER", Applicability.REQUIRED),
        Map.entry("CASH_BENEFIT_PURCHASE_WITH_CASH_BACK", Applicability.OPTIONAL),
        Map.entry("CASH_BENEFIT_BALANCE_INQUIRY", Applicability.OPTIONAL),
        Map.entry("TIME_OUT_REVERSAL", Applicability.OPTIONAL),
        Map.entry("FOOD_STAMP_VOID_OF_MERCHANDISE_RETURN", Applicability.OPTIONAL),
        // Section 10.5.5.6: WIC Discount Amount / WIC Product Data are the eWIC-specific
        // Segment 103 fields central to Purchase Completion and Voucher Clear.
        Map.entry("EWIC_PURCHASE_COMPLETION", Applicability.REQUIRED),
        Map.entry("EWIC_VOUCHER_CLEAR", Applicability.REQUIRED),
        // Section 13.2 Element 154: WIC Product Data appears in the Authorization / Balance
        // Inquiry / Cancellation *response*; the request itself does not mandate it.
        Map.entry("EWIC_AUTHORIZATION", Applicability.OPTIONAL),
        Map.entry("EWIC_AUTHORIZATION_CANCELLATION", Applicability.OPTIONAL),
        Map.entry("EWIC_BALANCE_INQUIRY", Applicability.OPTIONAL),
        Map.entry("EWIC_PURCHASE_REVERSAL_VOID", Applicability.OPTIONAL),
        // Section 10.5.5.1: "These specifications do not support Return transactions."
        Map.entry("EWIC_RETURN", Applicability.PROHIBITED)
    );

    private static final Set<String> REQUIRES_VOUCHER_ID = Set.of("FOOD_STAMP_ELECTRONIC_VOUCHER");
    private static final Set<String> REQUIRES_WIC_DATA =
        Set.of("EWIC_PURCHASE_COMPLETION", "EWIC_VOUCHER_CLEAR");

    public Applicability classify(String lifecycleRole) {
        if (lifecycleRole == null) {
            return Applicability.OPTIONAL;
        }
        return MATRIX.getOrDefault(lifecycleRole.toUpperCase(java.util.Locale.ROOT), Applicability.OPTIONAL);
    }

    public ValidationResult validate(String lifecycleRole, boolean segment103Present, boolean voucherIdPresent,
                                      boolean wicDataPresent) {
        ValidationResult result = new ValidationResult("segment-103-applicability");
        String role = lifecycleRole == null ? null : lifecycleRole.toUpperCase(java.util.Locale.ROOT);
        Applicability applicability = classify(role);

        switch (applicability) {
            case PROHIBITED -> result.addError(SOURCE,
                "Lifecycle role " + role + " is not supported by ATL105 (eWIC Return is prohibited; SEG103-R-020)");
            case REQUIRED -> {
                if (!segment103Present) {
                    result.addError(SOURCE,
                        "Lifecycle role " + role + " requires the EBT Data Segment (SEG103-R-002/SEG103-R-024)");
                }
                if (REQUIRES_VOUCHER_ID.contains(role) && !voucherIdPresent) {
                    result.addError(SOURCE,
                        "Lifecycle role " + role + " requires Voucher ID (Element 109) (SEG103-R-024)");
                }
                if (REQUIRES_WIC_DATA.contains(role) && !wicDataPresent) {
                    result.addError(SOURCE,
                        "Lifecycle role " + role + " requires WIC Discount Amount or WIC Product Data (SEG103-R-024)");
                }
            }
            case OPTIONAL -> {
                // No EBT-specific field is mandated by the transaction's own definition; presence
                // is driven by Clerk ID, state HIP participation, or eWIC data, none of which are
                // structurally required (SEG103-R-002 is satisfied by "conditional").
            }
        }
        return result;
    }
}
