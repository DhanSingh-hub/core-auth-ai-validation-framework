package com.coreauth.validator.canonical;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Source-release country rows, not a current ISO registry or a production eligibility list. */
public final class AppendixACCountryCodeCatalog {
    public record Row(String code, String country, String pageAnchor) { }
    private static final List<Row> ROWS;
    static {
        List<Row> rows = new ArrayList<>();
        page(rows, "AC-1", """
                004|Afghanistan;092|British Virgin Islands
                008|Albania;096|Brunei Darussalam
                012|Algeria;100|Bulgaria
                840|American Samoa;854|Burkina Faso
                020|Andorra;108|Burundi
                024|Angola;116|Cambodia
                660|Anguilla;120|Cameroon
                |Antarctica (no universal currency);124|Canada
                028|Antigua and Barbuda;132|Cape Verde
                032|Argentina;136|Cayman Islands
                051|Armenia;140|Central African Republic
                533|Aruba;148|Chad
                036|Australia;152|Chile
                040|Austria;156|China
                031|Azerbaijan;162|Christmas Island
                044|Bahamas;166|Cocos (Keeling) Island
                048|Bahrain;170|Columbia
                050|Bangladesh;174|Comoros
                052|Barbados;178|Congo
                112|Belarus;184|Cook Islands
                056|Belgium;188|Costa Rica
                084|Belize;191|Croatia
                204|Benin;196|Cyprus
                060|Bermuda;203|Czech Republic
                064|Bhutan;180|Democratic Republic of Congo
                068|Bolivia;208|Denmark
                070|Bosnia – Herzegovina;262|Djibouti
                072|Botswana;212|Dominica
                074|Bouvet Island;214|Dominican Republic
                076|Brazil;626|East Timor
                086|British Indian Ocean Territory;332|Haiti
                """);
        page(rows, "AC-2", """
                218|Ecuador;334|Heard Island and McDonald Island
                818|Egypt;N/A|Holy See (See Vatican City State.)
                222|El Salvador;340|Honduras
                226|Equatorial Guinea;344|Hong Kong
                232|Eritrea;348|Hungary
                233|Estonia;352|Iceland
                231|Ethiopia;356|India
                N/A|European Economic and Monetary Union;360|Indonesia
                234|Faeroe Islands;368|Iraq
                238|Falkland Islands (Malvinas);372|Ireland
                242|Fiji;376|Israel
                246|Finland;380|Italy
                250|France;384|Ivory Coast
                249|France, Metropolitan;388|Jamaica
                254|French Guiana;392|Japan
                258|French Polynesia;400|Jordan
                260|French Southern Territory;398|Kazakhstan
                266|Gabon;404|Kenya
                270|Gambia;296|Kiribati
                268|Georgia;410|Korea, Republic of
                276|Germany;414|Kuwait
                288|Ghana;417|Kyrgyzstan
                292|Gibraltar;418|Lao People's Democratic Republic
                300|Greece;428|Latvia
                304|Greenland;422|Lebanon
                308|Grenada;426|Lesotho
                312|Guadeloupe;430|Liberia
                316|Guam;434|Libyan Arab Jamahiriya
                320|Guatemala;438|Liechtenstein
                324|Guinea;574|Norfolk Island
                624|Guinea-Bissau;580|Northern Mariana Islands
                328|Guyana;578|Norway
                """);
        page(rows, "AC-3", """
                440|Lithuania;512|Oman
                442|Luxembourg;586|Pakistan
                446|Macau;585|Palau
                807|Macedonia;275|Palestinian Territory, Occupied
                450|Madagascar;591|Panama
                454|Malawi;598|Papua New Guinea
                458|Malaysia;600|Paraguay
                462|Maldives;604|Peru
                466|Mali;608|Philippines
                470|Malta;612|Pitcairn Island
                584|Marshall Islands;616|Poland
                474|Martinique;620|Portugal
                929|Mauritania;630|Puerto Rico
                480|Mauritius;634|Qatar
                175|Mayotte;638|Reunion
                484|Mexico;642|Romania
                583|Micronesia;643|Russian Federation
                498|Moldova;646|Rwanda
                492|Monaco;654|St. Helena
                496|Mongolia;659|St. Kitts-Nevis
                499|Montenegro;662|St. Lucia
                500|Montserrat;666|St. Pierre and Miquelon
                504|Morocco;670|St. Vincent and the Grenadines
                508|Mozambique;882|Samoa
                104|Myanmar;674|San Marino
                516|Namibia;930|Sao Tome and Principe
                520|Nauru;682|Saudi Arabia
                524|Nepal;686|Senegal
                528|Netherlands;688|Serbia, Republic of
                540|New Caledonia;690|Seychelles
                554|New Zealand;694|Sierra Leone
                558|Nicaragua;702|Singapore
                562|Niger;703|Slovakia
                566|Nigeria;840|United States
                570|Niue;581|U.S. Minor Outlying Islands
                850|United States Virgin Islands
                """);
        page(rows, "AC-4", """
                705|Slovenia;858|Uruguay
                090|Solomon Islands;860|Uzbekistan
                706|Somalia;548|Vanuatu
                710|South Africa;336|Vatican City State (Holy See)
                239|South Georgia and South Sandwich Islands;862|Venezuela
                724|Spain;704|Vietnam
                144|Sri Lanka;|Virgin Islands: See British Virgin Islands and United States Virgin Islands.
                729|Sudan;876|Wallis and Futuna Islands
                740|Suriname;732|Western Sahara
                744|Svalbard and Jan Mayen Islands;887|Yemen
                748|Swaziland;894|Zambia
                752|Sweden;716|Zimbabwe
                756|Switzerland;663|Saint Martin
                158|Taiwan;534|Sint Maarten
                762|Tajikistan;728|South Sudan
                834|Tanzania, United Republic of;760|Syria
                764|Thailand;832|Jersey
                768|Togo;833|Isle of Man
                772|Tokelau;535|Bonaire, Sint Eustatius and Saba
                776|Tonga;248|Aland Islands
                780|Trinidad and Tobago;192|Cuba
                788|Tunisia;364|Iran
                792|Turkey;408|Korea-north
                795|Turkmenistan;531|Curaçao
                796|Turks and Caicos Islands;580|Northern Mariana Islands
                798|Tuvalu;652|Saint Barthelemy
                800|Uganda
                804|Ukraine
                784|United Arab Emirates
                826|United Kingdom
                900|United Nations Interim Administration Mission in Kosovo (UNMIK)
                """);
        ROWS = List.copyOf(rows);
    }
    private AppendixACCountryCodeCatalog() { }

    private static void page(List<Row> rows, String page, String data) {
        data.lines().forEach(line -> {
            for (String entry : line.split(";")) {
                String[] fields = entry.split("\\|", -1);
                if (fields.length != 2 || fields[1].isBlank()
                        || !(fields[0].isEmpty() || fields[0].equals("N/A") || fields[0].matches("[0-9]{3}"))) {
                    throw new IllegalStateException("Malformed Appendix AC catalog entry: " + entry);
                }
                rows.add(new Row(fields[0], fields[1], page));
            }
        });
    }
    public static List<Row> rows() {
        return ROWS;
    }
    public static List<Row> forCode(String code) {
        if (code == null || !code.matches("[0-9]{3}")) return List.of();
        return ROWS.stream().filter(row -> row.code().equals(code)).toList();
    }
    public static List<Row> forCountry(String country) {
        Objects.requireNonNull(country, "country");
        return ROWS.stream().filter(row -> row.country().equals(country)).toList();
    }
    public static boolean ambiguous(String code) {
        return forCode(code).stream().map(Row::country).distinct().count() > 1;
    }
}
