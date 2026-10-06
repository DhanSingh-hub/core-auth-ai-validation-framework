package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Independent source catalog for ATL105 Appendix L (Valid Currency Codes, Element No. 20).
 *
 * <p>Transcribed row-by-row from {@code specifications/ATL105/docs/specs/extracted_text.txt}
 * Appendix L-1 through L-9 (source lines 34456-34944). Appendix L is a flat currency/country/code
 * table, not a classification range table like Appendix F: a three-digit value either names a row
 * in this table or it does not. Several currency codes are listed more than once with a different
 * "Visa, MC, or Both" value per country (for example Euro 978, U.S. Dollar 840, Ethiopian Birr 230,
 * Rand 710, and Indian Rupee 356). This catalog preserves every row instead of collapsing them, and
 * flags codes whose network scope is not uniform across all of their rows as a network-scope
 * conflict that stays {@code REVIEW_REQUIRED} rather than silently resolving to one scope.
 *
 * <p>This catalog answers only "is this exact three-digit value a listed Appendix L currency code,
 * and what does Appendix L say about it (currency name, country/territory, Visa/MC/Both scope)?"
 * It does not decide per-transaction network eligibility (which requires knowing the acquiring
 * country and card network, neither of which is this element), does not certify the implied-decimal
 * relationship described for Element 76 Product Amount, and does not certify Appendix M's
 * fixed-840-only Currency Code subelement used by the HIP purchase/return amount. See
 * {@code docs/specs/kb/segment-100/appendix-l-currency-code-training.md} for the full usage map.
 *
 * <p>The simple {@link AppendixLCurrencyCodes#isValid(String)} allowlist used by
 * {@link Segment103PayloadValidator} for the Element 153 WIC Discount Amount currency subfield is
 * unchanged and remains independent of this richer training catalog.
 */
public final class AppendixLCurrencyCodeCatalog {

    public enum NetworkScope {
        VISA,
        MC,
        BOTH
    }

    public record Row(String code, String currencyName, List<String> countries, NetworkScope networkScope, String pageAnchor) {
        public Row {
            countries = List.copyOf(countries);
        }
    }

    public record Classification(String code, boolean recognized, List<Row> rows, boolean networkScopeConflict, NetworkScope aggregatedScope) {
    }

    private static final List<Row> ROWS;
    private static final Map<String, List<Row>> BY_CODE;

    static {
        List<Row> rows = new ArrayList<>();
        // Appendix L-1 (page 667)
        add(rows, "971", "Afghani", List.of("Afghanistan"), NetworkScope.BOTH, "L-1");
        add(rows, "012", "Algerian Dinar", List.of("Algeria"), NetworkScope.BOTH, "L-1");
        add(rows, "973", "Angola Kwanza", List.of("Angola"), NetworkScope.BOTH, "L-1");
        add(rows, "032", "Argentine Peso", List.of("Argentina"), NetworkScope.BOTH, "L-1");
        add(rows, "051", "Armenian Dram", List.of("Armenia"), NetworkScope.BOTH, "L-1");
        add(rows, "036", "Australian Dollar", List.of("Australia", "Christmas Island", "Cocos (Keeling) Islands", "Heard and McDonald Islands", "Kiribati", "Nauru", "Norfolk Island", "Tuvalu"), NetworkScope.BOTH, "L-1");
        add(rows, "944", "Azerbaijanian Manat", List.of("Azerbaijan"), NetworkScope.BOTH, "L-1");
        add(rows, "044", "Bahamian Dollar", List.of("Bahamas"), NetworkScope.BOTH, "L-1");
        add(rows, "933", "Belarusian Ruble", List.of("Belarus"), NetworkScope.BOTH, "L-1");
        add(rows, "048", "Bahraini Dinar", List.of("Bahrain"), NetworkScope.BOTH, "L-1");
        add(rows, "764", "Baht", List.of("Thailand"), NetworkScope.BOTH, "L-1");
        add(rows, "590", "Balboa", List.of("Panama"), NetworkScope.BOTH, "L-1");
        add(rows, "052", "Barbados Dollar", List.of("Barbados"), NetworkScope.BOTH, "L-1");
        add(rows, "084", "Belize Dollar", List.of("Belize"), NetworkScope.BOTH, "L-1");
        add(rows, "060", "Bermudian Dollar", List.of("Bermuda"), NetworkScope.BOTH, "L-1");
        add(rows, "064", "Bhutanese Ngultrum", List.of("Bhutan"), NetworkScope.BOTH, "L-1");
        add(rows, "928", "Bolivar Soberano", List.of("Venezuela"), NetworkScope.BOTH, "L-1");
        add(rows, "068", "Boliviano", List.of("Bolivia"), NetworkScope.BOTH, "L-1");
        add(rows, "986", "Brazilian Real", List.of("Brazil"), NetworkScope.BOTH, "L-1");
        add(rows, "096", "Brunei Dollar", List.of("Brunei Darussalam"), NetworkScope.BOTH, "L-1");

        // Appendix L-2 (page 668)
        add(rows, "108", "Burundi Franc", List.of("Burundi"), NetworkScope.BOTH, "L-2");
        add(rows, "124", "Canadian Dollar", List.of("Canada"), NetworkScope.BOTH, "L-2");
        add(rows, "132", "Cape Verde Escudo", List.of("Cape Verde"), NetworkScope.BOTH, "L-2");
        add(rows, "136", "Cayman Islands Dollar", List.of("Cayman Islands"), NetworkScope.BOTH, "L-2");
        add(rows, "936", "Cedi", List.of("Ghana"), NetworkScope.BOTH, "L-2");
        add(rows, "953", "CFP Franc", List.of("French Polynesia", "New Caledonia", "Wallis and Futuna"), NetworkScope.BOTH, "L-2");
        add(rows, "952", "CFA Franc BCEAO", List.of("Benin", "Burkina Faso", "Cote D'Ivoire (Ivory Coast)", "Guinea-Bissau", "Mali", "Niger", "Senegal", "Togo"), NetworkScope.BOTH, "L-2");
        add(rows, "950", "CFA Franc BEAC", List.of("Cameroon", "Central African Republic", "Chad", "Congo", "Equatorial Guinea", "Gabon"), NetworkScope.BOTH, "L-2");
        add(rows, "152", "Chilean Peso", List.of("Chile"), NetworkScope.BOTH, "L-2");
        add(rows, "158", "Chinese People's Bank Dollar", List.of("China"), NetworkScope.MC, "L-2");
        add(rows, "156", "Chinese Yuan Renminbi", List.of("China"), NetworkScope.BOTH, "L-2");
        add(rows, "170", "Colombian Peso", List.of("Columbia"), NetworkScope.BOTH, "L-2");
        add(rows, "174", "Comoro Franc", List.of("Comoros"), NetworkScope.BOTH, "L-2");
        add(rows, "976", "Congolese Franc", List.of("Democratic Republic of the Congo"), NetworkScope.BOTH, "L-2");
        add(rows, "977", "Convertible Mark", List.of("Bosnia and Herzegovina"), NetworkScope.BOTH, "L-2");
        add(rows, "558", "Cordoba Oro", List.of("Nicaragua"), NetworkScope.BOTH, "L-2");
        add(rows, "188", "Costa Rican Colon", List.of("Costa Rica"), NetworkScope.BOTH, "L-2");

        // Appendix L-3 (page 669)
        add(rows, "270", "Dalasi", List.of("Gambia"), NetworkScope.BOTH, "L-3");
        add(rows, "208", "Danish Krone", List.of("Denmark", "Faroe Islands", "Greenland"), NetworkScope.BOTH, "L-3");
        add(rows, "807", "Denar", List.of("Macedonia"), NetworkScope.BOTH, "L-3");
        add(rows, "262", "Djibouti Franc", List.of("Djibouti"), NetworkScope.BOTH, "L-3");
        add(rows, "930", "Dobra", List.of("Sao Tome and Principe"), NetworkScope.BOTH, "L-3");
        add(rows, "214", "Dominican Peso", List.of("Dominican Republic"), NetworkScope.BOTH, "L-3");
        add(rows, "704", "Dong", List.of("Vietnam"), NetworkScope.BOTH, "L-3");
        add(rows, "951", "East Caribbean Dollar", List.of("Anguilla", "Antigua and Barbuda", "Dominica", "Grenada", "Montserrat", "St. Kitts-Nevis", "St. Lucia", "St. Vincent and the Grenadines"), NetworkScope.BOTH, "L-3");
        add(rows, "818", "Egyptian Pound", List.of("Egypt"), NetworkScope.BOTH, "L-3");
        add(rows, "222", "El Salvador Colon", List.of("El Salvador"), NetworkScope.MC, "L-3");
        add(rows, "232", "Eritrean Nakfa", List.of("Eritrea"), NetworkScope.VISA, "L-3");
        add(rows, "230", "Ethiopian Birr", List.of("Ethiopia"), NetworkScope.BOTH, "L-3");
        add(rows, "230", "Ethiopian Birr", List.of("Eritrea"), NetworkScope.MC, "L-3");
        add(rows, "978", "Euro", List.of("SAINT Barthelemy", "SAINT MARTIN, French part"), NetworkScope.MC, "L-3");
        add(rows, "978", "Euro", List.of("France, Metropolitan"), NetworkScope.VISA, "L-3");

        // Appendix L-4 (page 670)
        add(rows, "978", "Euro", List.of("Aland Islands", "Andorra", "Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus", "Estonia",
                "European Union Countries", "Finland", "France", "French Guiana", "French Southern Territories", "Germany", "Greece",
                "Guadeloupe", "Holy See (Vatican City State)", "Ireland", "Italy", "Kosovo, United Nations Mission in Kosovo (UNMIK)",
                "Latvia", "Lithuania", "Luxembourg", "Malta", "Martinique", "Mayotte", "Monaco", "Montenegro", "Netherlands", "Portugal",
                "Reunion", "St. Pierre and Miquelon", "San Marino", "Slovakia", "Slovenia", "Spain"), NetworkScope.BOTH, "L-4");

        // Appendix L-5 (page 671)
        add(rows, "238", "Falkland Islands Pound", List.of("Falkland Islands (Malvinas)"), NetworkScope.BOTH, "L-5");
        add(rows, "242", "Fiji Dollar", List.of("Fiji"), NetworkScope.BOTH, "L-5");
        add(rows, "348", "Forint", List.of("Hungary"), NetworkScope.BOTH, "L-5");
        add(rows, "292", "Gibraltar Pound", List.of("Gibraltar"), NetworkScope.BOTH, "L-5");
        add(rows, "332", "Gourde", List.of("Haiti"), NetworkScope.BOTH, "L-5");
        add(rows, "600", "Guarani", List.of("Paraguay"), NetworkScope.BOTH, "L-5");
        add(rows, "533", "Guilder", List.of("Aruba"), NetworkScope.BOTH, "L-5");
        add(rows, "324", "Guinea Franc", List.of("Guinea"), NetworkScope.BOTH, "L-5");
        add(rows, "328", "Guyana Dollar", List.of("Guyana"), NetworkScope.BOTH, "L-5");
        add(rows, "344", "Hong Kong Dollar", List.of("Hong Kong"), NetworkScope.BOTH, "L-5");
        add(rows, "980", "Hryvnia", List.of("Ukraine"), NetworkScope.BOTH, "L-5");
        add(rows, "352", "Iceland Krona", List.of("Iceland"), NetworkScope.BOTH, "L-5");
        add(rows, "356", "Indian Rupee", List.of("Bhutan"), NetworkScope.MC, "L-5");
        add(rows, "356", "Indian Rupee", List.of("India"), NetworkScope.BOTH, "L-5");
        add(rows, "368", "Iraqi Dinar", List.of("Iraq"), NetworkScope.BOTH, "L-5");
        add(rows, "388", "Jamaican Dollar", List.of("Jamaica"), NetworkScope.BOTH, "L-5");
        add(rows, "400", "Jordanian Dinar", List.of("Jordan"), NetworkScope.BOTH, "L-5");
        add(rows, "404", "Kenyan Shilling", List.of("Kenya"), NetworkScope.BOTH, "L-5");
        add(rows, "598", "Kina", List.of("Papua New Guinea"), NetworkScope.BOTH, "L-5");
        add(rows, "418", "Kip", List.of("Lao People's Democratic Republic"), NetworkScope.BOTH, "L-5");
        add(rows, "203", "Koruna", List.of("Czech Republic"), NetworkScope.BOTH, "L-5");
        add(rows, "414", "Kuwaiti Dinar", List.of("Kuwait"), NetworkScope.BOTH, "L-5");
        add(rows, "981", "Lari", List.of("Georgia"), NetworkScope.BOTH, "L-5");
        add(rows, "422", "Lebanese Pound", List.of("Lebanon"), NetworkScope.BOTH, "L-5");
        add(rows, "008", "Lek", List.of("Albania"), NetworkScope.BOTH, "L-5");
        add(rows, "340", "Lempira", List.of("Honduras"), NetworkScope.BOTH, "L-5");
        add(rows, "925", "Leone", List.of("Sierra Leone"), NetworkScope.BOTH, "L-5");
        add(rows, "430", "Liberian Dollar", List.of("Liberia"), NetworkScope.BOTH, "L-5");
        add(rows, "434", "Libyan Dinar", List.of("Libyan Arab Jamahiriya"), NetworkScope.VISA, "L-5");

        // Appendix L-6 (page 672)
        add(rows, "748", "Lilangeni", List.of("Swaziland"), NetworkScope.BOTH, "L-6");
        add(rows, "426", "Loti", List.of("Lesotho"), NetworkScope.BOTH, "L-6");
        add(rows, "969", "Malagasy Ariary", List.of("Madagascar"), NetworkScope.BOTH, "L-6");
        add(rows, "458", "Malaysian Ringgit", List.of("Malaysia"), NetworkScope.BOTH, "L-6");
        add(rows, "454", "Malawi Kwacha", List.of("Malawi"), NetworkScope.BOTH, "L-6");
        add(rows, "934", "Manat", List.of("Turkmenistan"), NetworkScope.BOTH, "L-6");
        add(rows, "480", "Mauritius Rupee", List.of("Mauritius"), NetworkScope.BOTH, "L-6");
        add(rows, "484", "Mexican Peso", List.of("Mexico"), NetworkScope.BOTH, "L-6");
        add(rows, "498", "Moldovan Lau", List.of("Moldova, Republic of"), NetworkScope.BOTH, "L-6");
        add(rows, "504", "Moroccan Dirham", List.of("Morocco", "Western Sahara"), NetworkScope.BOTH, "L-6");
        add(rows, "943", "Mozambique Metical", List.of("Mozambique"), NetworkScope.BOTH, "L-6");
        add(rows, "104", "Myanmar Kyat", List.of("Myanmar"), NetworkScope.BOTH, "L-6");
        add(rows, "566", "Naira", List.of("Nigeria"), NetworkScope.BOTH, "L-6");
        add(rows, "516", "Namibian Dollar", List.of("Namibia"), NetworkScope.BOTH, "L-6");
        add(rows, "524", "Nepalese Rupee", List.of("Nepal"), NetworkScope.BOTH, "L-6");
        add(rows, "532", "Caribbean Guilder", List.of("Curacao", "Sint Maarten (Dutch Part)"), NetworkScope.BOTH, "L-6");
        add(rows, "376", "New Israeli Shekel", List.of("Israel"), NetworkScope.BOTH, "L-6");
        add(rows, "901", "New Taiwan Dollar", List.of("The Republic of China (Taiwan)"), NetworkScope.BOTH, "L-6");
        add(rows, "554", "New Zealand Dollar", List.of("Cook Islands", "New Zealand", "Niue", "Pitcairn", "Tokelau"), NetworkScope.BOTH, "L-6");
        add(rows, "578", "Norwegian Krone", List.of("Bouvet Island", "Norway", "Svalbard and Jan Mayen"), NetworkScope.BOTH, "L-6");
        add(rows, "578", "Norwegian Krone", List.of("Antarctica"), NetworkScope.MC, "L-6");
        add(rows, "604", "Nuevo Sol", List.of("Peru"), NetworkScope.BOTH, "L-6");
        add(rows, "929", "Ouguiya", List.of("Mauritania"), NetworkScope.BOTH, "L-6");

        // Appendix L-7 (page 673)
        add(rows, "776", "Pa'anga", List.of("Tonga"), NetworkScope.BOTH, "L-7");
        add(rows, "586", "Pakistan Rupee", List.of("Pakistan"), NetworkScope.BOTH, "L-7");
        add(rows, "446", "Pataca", List.of("Macao"), NetworkScope.BOTH, "L-7");
        add(rows, "858", "Peso Uruguayo", List.of("Uruguay"), NetworkScope.BOTH, "L-7");
        add(rows, "608", "Philippine Peso", List.of("Philippines"), NetworkScope.BOTH, "L-7");
        add(rows, "826", "Pound Sterling", List.of("Guernsey", "Isle of Man", "Jersey", "United Kingdom"), NetworkScope.BOTH, "L-7");
        add(rows, "826", "Pound Sterling", List.of("South Georgia and South Sandwich Islands"), NetworkScope.BOTH, "L-7");
        add(rows, "072", "Pula", List.of("Botswana"), NetworkScope.BOTH, "L-7");
        add(rows, "634", "Qatari Rial", List.of("Qatar"), NetworkScope.BOTH, "L-7");
        add(rows, "320", "Quetzal", List.of("Guatemala"), NetworkScope.BOTH, "L-7");
        add(rows, "710", "Rand", List.of("Lesotho", "Namibia"), NetworkScope.VISA, "L-7");
        add(rows, "710", "Rand", List.of("South Africa"), NetworkScope.BOTH, "L-7");
        add(rows, "512", "Rial Omani", List.of("Oman"), NetworkScope.BOTH, "L-7");
        add(rows, "116", "Riel", List.of("Cambodia"), NetworkScope.BOTH, "L-7");
        add(rows, "946", "Romanian Leu", List.of("Romania"), NetworkScope.BOTH, "L-7");
        add(rows, "462", "Rufiyaa", List.of("Maldives"), NetworkScope.BOTH, "L-7");
        add(rows, "360", "Rupiah", List.of("Indonesia"), NetworkScope.BOTH, "L-7");
        add(rows, "643", "Russian Ruble", List.of("Russian Federation"), NetworkScope.BOTH, "L-7");
        add(rows, "646", "Rwanda Franc", List.of("Rwanda"), NetworkScope.BOTH, "L-7");
        add(rows, "682", "Saudi Riyal", List.of("Saudi Arabia"), NetworkScope.BOTH, "L-7");
        add(rows, "891", "Serbian Dinar", List.of("Serbia"), NetworkScope.MC, "L-7");
        add(rows, "941", "Serbian Dinar", List.of("Serbia, Republic of"), NetworkScope.BOTH, "L-7");
        add(rows, "690", "Seychelles Rupee", List.of("Seychelles"), NetworkScope.BOTH, "L-7");
        add(rows, "702", "Singapore Dollar", List.of("Singapore"), NetworkScope.BOTH, "L-7");
        add(rows, "417", "Som", List.of("Kyrgyzstan"), NetworkScope.BOTH, "L-7");
        add(rows, "090", "Solomon Islands Dollar", List.of("Solomon Islands"), NetworkScope.BOTH, "L-7");

        // Appendix L-8 (page 674)
        add(rows, "706", "Somali Shilling", List.of("Somalia"), NetworkScope.BOTH, "L-8");
        add(rows, "728", "South Sudanese Pound", List.of("Republic of South Sudan"), NetworkScope.BOTH, "L-8");
        add(rows, "996", "Spanish Peseta (ESA)", List.of("Brazil"), NetworkScope.MC, "L-8");
        add(rows, "144", "Sri Lanka Rupee", List.of("Sri Lanka"), NetworkScope.BOTH, "L-8");
        add(rows, "654", "St. Helena Pound", List.of("St. Helena"), NetworkScope.BOTH, "L-8");
        add(rows, "938", "Sudan Pound", List.of("Sudan"), NetworkScope.BOTH, "L-8");
        add(rows, "968", "Suriname Dollar", List.of("Suriname"), NetworkScope.BOTH, "L-8");
        add(rows, "752", "Swedish Krona", List.of("Sweden"), NetworkScope.BOTH, "L-8");
        add(rows, "756", "Swiss Franc", List.of("Liechtenstein", "Switzerland"), NetworkScope.BOTH, "L-8");
        add(rows, "972", "Tajikistan Somoni", List.of("Tajikistan"), NetworkScope.BOTH, "L-8");
        add(rows, "050", "Taka", List.of("Bangladesh"), NetworkScope.BOTH, "L-8");
        add(rows, "882", "Tala", List.of("Samoa"), NetworkScope.BOTH, "L-8");
        add(rows, "834", "Tanzanian Shilling", List.of("Tanzania, United Republic of"), NetworkScope.BOTH, "L-8");
        add(rows, "398", "Tenge", List.of("Kazakhstan"), NetworkScope.BOTH, "L-8");
        add(rows, "780", "Trinidad and Tobago Dollar", List.of("Trinidad and Tobago"), NetworkScope.BOTH, "L-8");
        add(rows, "496", "Tugrik", List.of("Mongolia"), NetworkScope.BOTH, "L-8");
        add(rows, "788", "Tunisian Dinar", List.of("Tunisia"), NetworkScope.BOTH, "L-8");
        add(rows, "949", "Turkish Lira", List.of("Turkey"), NetworkScope.BOTH, "L-8");
        add(rows, "784", "UAE Dirham", List.of("United Arab Emirates"), NetworkScope.BOTH, "L-8");
        add(rows, "800", "Uganda Shilling", List.of("Uganda"), NetworkScope.BOTH, "L-8");
        add(rows, "840", "U.S. Dollar", List.of("Libyan Arab Jamahiriya", "Palestine", "Panama"), NetworkScope.MC, "L-8");

        // Appendix L-9 (page 675)
        add(rows, "840", "U.S. Dollar", List.of("American Samoa", "Bonaire, Sint Eustatius and Saba", "British Indian Ocean Territory",
                "Ecuador", "El Salvador", "Guam", "Marshall Islands", "Micronesia", "Northern Mariana Islands", "Palau", "Puerto Rico",
                "Timor-Leste", "Turks and Caicos Islands", "United States", "U.S. Minor Outlying Islands", "Virgin Islands, British",
                "Virgin Islands, U.S."), NetworkScope.BOTH, "L-9");
        add(rows, "860", "Uzbekistan Sum", List.of("Uzbekistan"), NetworkScope.BOTH, "L-9");
        add(rows, "548", "Vatu", List.of("Vanuatu"), NetworkScope.BOTH, "L-9");
        add(rows, "410", "Won", List.of("Korea, Republic of"), NetworkScope.BOTH, "L-9");
        add(rows, "886", "Yemeni Rial", List.of("Yemen"), NetworkScope.BOTH, "L-9");
        add(rows, "392", "Yen", List.of("Japan"), NetworkScope.BOTH, "L-9");
        add(rows, "967", "Zambian Kwacha", List.of("Zambia"), NetworkScope.BOTH, "L-9");
        add(rows, "924", "Zimbabwe Gold", List.of("Zimbabwe"), NetworkScope.BOTH, "L-9");
        add(rows, "985", "Zloty", List.of("Poland"), NetworkScope.BOTH, "L-9");

        ROWS = List.copyOf(rows);
        Map<String, List<Row>> byCode = new LinkedHashMap<>();
        for (Row row : ROWS) {
            byCode.computeIfAbsent(row.code(), key -> new ArrayList<>()).add(row);
        }
        Map<String, List<Row>> frozen = new LinkedHashMap<>();
        for (var entry : byCode.entrySet()) {
            frozen.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        BY_CODE = Collections.unmodifiableMap(frozen);
    }

    /** All 161 Appendix L source rows, including every per-country split of a repeated code. */
    public List<Row> rows() {
        return ROWS;
    }

    /** The 151 distinct three-digit codes assigned anywhere in Appendix L. */
    public Set<String> codes() {
        return BY_CODE.keySet();
    }

    /**
     * Classifies a candidate Element 20 / Appendix L value. Only an exact three-ASCII-digit string
     * can ever be recognized. A recognized code whose rows disagree on network scope is reported
     * with {@code networkScopeConflict=true} and a {@code null} aggregated scope; callers must keep
     * such codes {@code REVIEW_REQUIRED} rather than picking one of the conflicting claims.
     */
    public Classification classify(String code) {
        if (code == null || !code.matches("[0-9]{3}")) {
            return new Classification(code, false, List.of(), false, null);
        }
        List<Row> rows = BY_CODE.get(code);
        if (rows == null) {
            return new Classification(code, false, List.of(), false, null);
        }
        Set<NetworkScope> scopes = rows.stream().map(Row::networkScope).collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new));
        boolean conflict = scopes.size() > 1;
        NetworkScope aggregated = conflict ? null : scopes.iterator().next();
        return new Classification(code, true, rows, conflict, aggregated);
    }

    private static void add(List<Row> rows, String code, String currencyName, List<String> countries, NetworkScope scope, String pageAnchor) {
        rows.add(new Row(code, currencyName, countries, scope, pageAnchor));
    }
}
