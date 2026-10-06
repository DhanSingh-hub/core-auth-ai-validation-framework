package com.coreauth.validator.canonical;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/** Source-derived alphabetical and ANSI state-code pairs from ATL105 Appendix D. */
public final class AppendixDStateCodes {
    private static final Map<String, String> ANSI_BY_ALPHABETICAL;
    private static final Set<String> ALPHABETICAL_CODES;
    private static final Set<String> ANSI_CODES;

    static {
        Map<String, String> codes = new LinkedHashMap<>();
        codes.put("AL", "01");
        codes.put("AK", "02");
        codes.put("AZ", "04");
        codes.put("AR", "05");
        codes.put("CA", "06");
        codes.put("CO", "08");
        codes.put("CT", "09");
        codes.put("DE", "10");
        codes.put("DC", "11");
        codes.put("FL", "12");
        codes.put("GA", "13");
        codes.put("HI", "15");
        codes.put("ID", "16");
        codes.put("IL", "17");
        codes.put("IN", "18");
        codes.put("IA", "19");
        codes.put("KS", "20");
        codes.put("KY", "21");
        codes.put("LA", "22");
        codes.put("ME", "23");
        codes.put("MD", "24");
        codes.put("MA", "25");
        codes.put("MI", "26");
        codes.put("MN", "27");
        codes.put("MS", "28");
        codes.put("MO", "29");
        codes.put("MT", "30");
        codes.put("NE", "31");
        codes.put("NV", "32");
        codes.put("NH", "33");
        codes.put("NJ", "34");
        codes.put("NM", "35");
        codes.put("NY", "36");
        codes.put("NC", "37");
        codes.put("ND", "38");
        codes.put("OH", "39");
        codes.put("OK", "40");
        codes.put("OR", "41");
        codes.put("PA", "42");
        codes.put("RI", "44");
        codes.put("SC", "45");
        codes.put("SD", "46");
        codes.put("TN", "47");
        codes.put("TX", "48");
        codes.put("UT", "49");
        codes.put("VT", "50");
        codes.put("VA", "51");
        codes.put("WA", "53");
        codes.put("WV", "54");
        codes.put("WI", "55");
        codes.put("WY", "56");
        codes.put("GU", "43");
        codes.put("PR", "14");
        codes.put("VI", "52");
        codes.put("AA", "57");
        codes.put("AE", "58");
        codes.put("AP", "59");
        codes.put("XX", "60");
        codes.put("AB", "61");
        codes.put("BC", "62");
        codes.put("MB", "63");
        codes.put("NB", "64");
        codes.put("NL", "65");
        codes.put("NS", "66");
        codes.put("NT", "67");
        codes.put("NU", "76");
        codes.put("ON", "68");
        codes.put("PE", "69");
        codes.put("QC", "70");
        codes.put("SK", "71");
        codes.put("YT", "72");
        codes.put("NA", "00");
        ANSI_BY_ALPHABETICAL = Collections.unmodifiableMap(codes);
        ALPHABETICAL_CODES = Collections.unmodifiableSet(new LinkedHashSet<>(codes.keySet()));
        ANSI_CODES = Collections.unmodifiableSet(new LinkedHashSet<>(codes.values()));
    }

    private AppendixDStateCodes() {
    }

    public static Map<String, String> ansiByAlphabeticalCode() {
        return ANSI_BY_ALPHABETICAL;
    }

    public static Set<String> alphabeticalCodes() {
        return ALPHABETICAL_CODES;
    }

    public static Set<String> ansiCodes() {
        return ANSI_CODES;
    }

    public static boolean isValidAlphabeticalCode(String code) {
        return code != null && ALPHABETICAL_CODES.contains(code.toUpperCase(Locale.ROOT));
    }

    public static boolean isValidAnsiCode(String code) {
        return code != null && ANSI_CODES.contains(code);
    }
}
