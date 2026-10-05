package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** Source catalog for all Appendix G codes; classification does not imply flow eligibility. */
public final class AppendixGTransactionTypeCatalog {
    public enum Scope {
        STANDARD_FINANCIAL,
        SPECIALIZED
    }

    public record Entry(String code, String type, String description, Scope scope) {
    }

    private static final Map<String, Entry> ENTRIES;

    static {
        Map<String, Entry> entries = new LinkedHashMap<>();
        add(entries, "0", "POS Purchase/Capture", "Point-of-sale purchase/preauthorized completion", Scope.STANDARD_FINANCIAL);
        add(entries, "3", "POS Authorization Only", "Point-of-sale authorization only (no draft capture)", Scope.STANDARD_FINANCIAL);
        add(entries, "4", "Customer-activated Purchase/Capture", "Customer-activated purchase completion", Scope.STANDARD_FINANCIAL);
        add(entries, "5", "Customer-activated Authorization Only", "Customer-activated authorization only (no draft capture)", Scope.STANDARD_FINANCIAL);
        add(entries, "6", "Mail/Phone Purchase", "Mail/Telephone order", Scope.STANDARD_FINANCIAL);
        add(entries, "7", "Merchandise return", "Merchandise return/refund", Scope.STANDARD_FINANCIAL);
        add(entries, "8", "Purchase reversal", "Purchase reversal/void", Scope.STANDARD_FINANCIAL);
        add(entries, "9", "Special Transactions", "Special transactions such as totals, downloads, communications test, electronic mail, and special device prompts.", Scope.SPECIALIZED);
        add(entries, "A", "Account Verification", "Account verification", Scope.STANDARD_FINANCIAL);
        add(entries, "B", "Mail/Phone Authorization Only", "Mail/Telephone authorization only", Scope.STANDARD_FINANCIAL);
        add(entries, "C", "Mail/Phone Reversal", "Mail/Telephone reversal/void", Scope.STANDARD_FINANCIAL);
        add(entries, "D", "RBC Loyalty Lookup", "Identify the loyalty number associated with an RBC card.", Scope.SPECIALIZED);
        add(entries, "E", "Balance Inquiry", "Balance Inquiry—Credit, EBT cash benefits and stored value card", Scope.SPECIALIZED);
        add(entries, "K", "Activate", "Activate a stored value card.", Scope.SPECIALIZED);
        add(entries, "L", "Deactivate", "Deactivate a stored value card.", Scope.SPECIALIZED);
        add(entries, "M", "Balance Merge", "Merge the balances of two stored value cards.", Scope.SPECIALIZED);
        add(entries, "N", "Replace Card", "Replace a stored value card.", Scope.SPECIALIZED);
        add(entries, "Q", "Recharge Card", "Recharge a stored value card.", Scope.SPECIALIZED);
        add(entries, "S", "Cancellation", "Cancels a stored value card, credit Authorization Only transaction, and debit Authorization Only transaction.", Scope.STANDARD_FINANCIAL);
        add(entries, "T", "Token Registration", "TransArmor token registration", Scope.SPECIALIZED);
        add(entries, "U", "Void of a Merchandise Return", "Void of a merchandise return", Scope.STANDARD_FINANCIAL);
        add(entries, "V", "Loyalty advice function", "The following loyalty advice functions are supported: Add account; Coupon redemption; Expiration Date update; Account inquiry; Points redemption; Reversal of coupon redeem; Reversal of points redeem; Sale update; Account update; Totals report, loyalty.", Scope.SPECIALIZED);
        add(entries, "Z", "Time-out Reversal", "Time-out reversal", Scope.STANDARD_FINANCIAL);
        ENTRIES = Collections.unmodifiableMap(entries);
    }

    public Map<String, Entry> entries() {
        return ENTRIES;
    }

    public Optional<Entry> classify(String code) {
        if (code == null || !code.matches("[0-9A-Z]")) return Optional.empty();
        return Optional.ofNullable(ENTRIES.get(code));
    }

    private static void add(Map<String, Entry> entries, String code, String type, String description, Scope scope) {
        entries.put(code, new Entry(code, type, description, scope));
    }
}