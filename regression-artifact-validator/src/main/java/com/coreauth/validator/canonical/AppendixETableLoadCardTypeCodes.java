package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/** Appendix E Table Load Response card-type and terminal-feature code families. */
public final class AppendixETableLoadCardTypeCodes {
    private static final Set<String> CARD_TYPE_CODES = Set.of(
            "001", "011", "012", "013", "020", "040", "041", "045", "046", "050",
            "051", "052", "053", "055", "056", "057", "058", "059", "060", "061",
            "064", "070", "071", "072", "073", "074", "075", "077", "078", "079",
            "080", "081", "084", "085", "086", "088", "090", "091", "092", "093",
            "094", "095", "096");
    private static final Set<String> TERMINAL_FEATURE_CODES = Set.of(
            "127", "148", "150", "162", "163", "164", "165", "166", "167", "168",
            "171", "173", "174");
    private static final Set<String> SUPPORTED_CODES;

    static {
        Set<String> codes = new LinkedHashSet<>(CARD_TYPE_CODES);
        codes.addAll(TERMINAL_FEATURE_CODES);
        SUPPORTED_CODES = Collections.unmodifiableSet(codes);
    }

    private AppendixETableLoadCardTypeCodes() {
    }

    public static Set<String> cardTypeCodes() {
        return CARD_TYPE_CODES;
    }

    public static Set<String> terminalFeatureCodes() {
        return TERMINAL_FEATURE_CODES;
    }

    public static Set<String> supportedCodes() {
        return SUPPORTED_CODES;
    }
}
