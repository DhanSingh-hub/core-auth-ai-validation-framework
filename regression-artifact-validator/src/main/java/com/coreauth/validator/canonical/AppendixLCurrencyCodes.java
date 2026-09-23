package com.coreauth.validator.canonical;

import java.util.Set;

/**
 * ATL105 Appendix L (Valid Currency Codes, Element No. 20) numeric code set.
 *
 * <p>Transcribed from {@code docs/specs/extracted_text.txt} lines 34456-34944 (Appendix L-1
 * through L-9). Used by {@link Segment103PayloadValidator} to validate the WIC Discount Amount
 * (Element 153) Currency Code subfield instead of accepting any 3-digit value.
 */
public final class AppendixLCurrencyCodes {
    private static final Set<String> CODES = Set.of(
        "971", "012", "973", "032", "051", "036", "944", "044", "933", "048", "764", "590",
        "052", "084", "060", "064", "928", "068", "986", "096", "108", "124", "132", "136",
        "936", "953", "952", "950", "152", "158", "156", "170", "174", "976", "977", "558",
        "188", "270", "208", "807", "262", "930", "214", "704", "951", "818", "222", "232",
        "230", "978", "238", "242", "348", "292", "332", "600", "533", "324", "328", "344",
        "980", "352", "356", "368", "388", "400", "404", "598", "418", "203", "414", "981",
        "422", "008", "340", "925", "430", "434", "748", "426", "969", "458", "454", "934",
        "480", "484", "498", "504", "943", "104", "566", "516", "524", "532", "376", "901",
        "554", "578", "604", "929", "776", "586", "446", "858", "608", "826", "072", "634",
        "320", "710", "512", "116", "946", "462", "360", "643", "646", "682", "891", "941",
        "690", "702", "417", "090", "706", "728", "996", "144", "654", "938", "968", "752",
        "756", "972", "050", "882", "834", "398", "780", "496", "788", "949", "784", "800",
        "840", "860", "548", "410", "886", "392", "967", "924", "985"
    );

    private AppendixLCurrencyCodes() {
    }

    public static boolean isValid(String code) {
        return code != null && CODES.contains(code);
    }

    public static Set<String> codes() {
        return CODES;
    }
}
