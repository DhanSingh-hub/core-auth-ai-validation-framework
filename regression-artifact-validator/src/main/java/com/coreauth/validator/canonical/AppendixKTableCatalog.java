package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Appendix K selectors for Element 116/118 in Segment 112; layout semantics are separate. */
public final class AppendixKTableCatalog {
    public enum Disposition {
        ASSIGNED,
        RESERVED,
        UNLISTED,
        INVALID_FORMAT
    }

    public record Classification(String tableId, String title, Disposition disposition) {
    }

    private static final Map<String, String> TITLES;

    static {
        Map<String, String> titles = new LinkedHashMap<>();
        add(titles, "001", "Balance Information");
        add(titles, "002", "Reserved Indicator Information");
        add(titles, "003", "AVS or NVS or Both Information");
        add(titles, "004", "Card Verification Value Information");
        add(titles, "005", "ECA/TeleCheck Trace ID");
        add(titles, "006", "ECA/TeleCheck Denial Record Number");
        add(titles, "007", "ECA/TeleCheck Return Check Data");
        add(titles, "008", "Loyalty Information - Version 1");
        add(titles, "009", "Visa Product Result Information");
        add(titles, "010", "Loyalty Information - Version 2");
        add(titles, "011", "User Data Information");
        add(titles, "012", "Discover Network Retrieval Reference Number");
        add(titles, "013", "Expiration Date - TransArmor VeriFone");
        add(titles, "016", "BUYPASS Card Type Code");
        add(titles, "017", "PIN on Receipt Information");
        add(titles, "018", "PINless Debit Information");
        add(titles, "019", "Visa Spend Qualified Indicator Information");
        add(titles, "020", "Host Prompts Information");
        add(titles, "021", "Re-Price Data Response Information");
        add(titles, "022", "CAVV Result");
        add(titles, "023", "MCX Reference Number");
        add(titles, "024", "Carwash Indicator");
        add(titles, "025", "Language Indicator");
        add(titles, "026", "DST Response");
        add(titles, "027", "Universal Unique Identifier");
        add(titles, "028", "Transaction Identifier");
        add(titles, "029", "PAR (Payment Account Reference) Data");
        add(titles, "030", "Merchant Advice Code");
        add(titles, "031", "DAF Indicator");
        add(titles, "032", "Agreement ID");
        add(titles, "034", "Host-based Purchase Restriction");
        add(titles, "035", "Cardholder Additional Information Result Code");
        add(titles, "036", "Account Type");
        add(titles, "037", "Account Funding Source");
        add(titles, "038", "Transaction Link Identifier");
        add(titles, "039", "Transaction Link Action Indicator");
        add(titles, "040", "Fraud Score");
        add(titles, "041", "Fraud Score Reason Code");
        add(titles, "042", "Authentication Data Quality Indicator");
        add(titles, "043", "Token Update First Use Indicator");
        add(titles, "044", "Merchant Tran ID");
        add(titles, "045", "Applied Special Service");
        add(titles, "046", "Voyager Restriction Code");
        add(titles, "047", "Visa Category Code");
        TITLES = Collections.unmodifiableMap(titles);
    }

    public Map<String, String> entries() {
        return TITLES;
    }

    public Classification classify(String tableId) {
        if (tableId == null || !tableId.matches("[0-9]{3}")) {
            return new Classification(tableId, null, Disposition.INVALID_FORMAT);
        }
        String title = TITLES.get(tableId);
        if (title == null) {
            return new Classification(tableId, null, Disposition.UNLISTED);
        }
        Disposition disposition = "002".equals(tableId) ? Disposition.RESERVED : Disposition.ASSIGNED;
        return new Classification(tableId, title, disposition);
    }

    private static void add(Map<String, String> titles, String tableId, String title) {
        titles.put(tableId, title);
    }
}