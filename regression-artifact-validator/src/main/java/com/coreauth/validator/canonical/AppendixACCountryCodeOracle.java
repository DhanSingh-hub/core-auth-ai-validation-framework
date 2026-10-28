package com.coreauth.validator.canonical;

import java.util.List;
import java.util.Objects;

public final class AppendixACCountryCodeOracle {
    public enum Status { PASS, FAIL, REVIEW_REQUIRED }
    public record Scenario(String countryCode, String assertedCountry, Boolean discoverThirdParty,
                           String retailerCountryCode) { }
    public record Assessment(String businessRequirementId, Status status, String reason) { }
    public static final List<String> RULES = List.of("VALID-CODE", "SOURCE-MAPPING",
            "ACQUIRER-CONTEXT", "DISCOVER-RETAILER", "EXTERNAL-EVIDENCE");

    public List<Assessment> assess(Scenario s) {
        Objects.requireNonNull(s, "scenario");
        return List.of(valid(s), mapping(s),
                result("ACQUIRER-CONTEXT", Status.REVIEW_REQUIRED,
                        "AC describes acquiring-institution country; Appendix I-64/I-65 describes Merchant "
                                + "Country in Table 059. Do not select a general role without clarification."),
                retailer(s), result("EXTERNAL-EVIDENCE", Status.REVIEW_REQUIRED,
                        "Source-listed is not current ISO certification, boarded location, transaction "
                                + "eligibility, full Table 059 wire validation or AI artifact certification."));
    }
    private static Assessment valid(Scenario s) {
        if (s.countryCode() == null || !s.countryCode().matches("[0-9]{3}")) {
            return result("VALID-CODE", Status.FAIL, "A numeric source code is exactly three ASCII digits, preserving leading zeros.");
        }
        return result("VALID-CODE", AppendixACCountryCodeCatalog.forCode(s.countryCode()).isEmpty() ? Status.FAIL : Status.PASS,
                "Recognition against ATL105 2026-3 Appendix AC only; blank/N/A/reference rows are not numeric codes.");
    }
    private static Assessment mapping(Scenario s) {
        List<AppendixACCountryCodeCatalog.Row> rows = AppendixACCountryCodeCatalog.forCode(s.countryCode());
        if (rows.isEmpty()) return result("SOURCE-MAPPING", Status.FAIL, "No numeric Appendix AC row matches this code.");
        if (s.assertedCountry() != null) {
            var countryRows = AppendixACCountryCodeCatalog.forCountry(s.assertedCountry());
            if (countryRows.isEmpty()) return result("SOURCE-MAPPING", Status.REVIEW_REQUIRED,
                    "Country label is not an exact source label; aliases/renamings are not inferred.");
            if (countryRows.stream().noneMatch(row -> row.code().matches("[0-9]{3}"))) {
                return result("SOURCE-MAPPING", Status.REVIEW_REQUIRED,
                        "This country/reference has no numeric source code; do not substitute a code automatically.");
            }
            if (countryRows.stream().noneMatch(row -> row.code().equals(s.countryCode()))) {
                return result("SOURCE-MAPPING", Status.FAIL, "Code disagrees with the explicitly supplied source-country label.");
            }
        }
        if (AppendixACCountryCodeCatalog.ambiguous(s.countryCode())) return result("SOURCE-MAPPING", Status.REVIEW_REQUIRED,
                "840 is listed for American Samoa and United States; no automatic unique-country resolution.");
        return result("SOURCE-MAPPING", Status.PASS, "Source mapping is consistent; historical/unusual entries remain unchanged.");
    }
    private static Assessment retailer(Scenario s) {
        if (s.discoverThirdParty() == null) return result("DISCOVER-RETAILER", Status.REVIEW_REQUIRED,
                "Discover third-party transaction context is absent.");
        if (!s.discoverThirdParty()) return result("DISCOVER-RETAILER", Status.PASS,
                "Not applicable to supplied context; this does not certify general country-role semantics.");
        if (AppendixACCountryCodeCatalog.forCode(s.countryCode()).isEmpty()) {
            return result("DISCOVER-RETAILER", Status.FAIL, "Table 059 requires a source-listed numeric country code.");
        }
        if (s.retailerCountryCode() == null) return result("DISCOVER-RETAILER", Status.REVIEW_REQUIRED,
                "Underlying retailer country evidence is required.");
        if (AppendixACCountryCodeCatalog.forCode(s.retailerCountryCode()).isEmpty()) {
            return result("DISCOVER-RETAILER", Status.FAIL, "Supplied retailer-country code is not source-listed.");
        }
        if (!s.countryCode().equals(s.retailerCountryCode())) return result("DISCOVER-RETAILER", Status.FAIL,
                "Appendix I requires the underlying retailer's country for Discover third-party payments.");
        if (AppendixACCountryCodeCatalog.ambiguous(s.countryCode())) return result("DISCOVER-RETAILER", Status.REVIEW_REQUIRED,
                "Equal numeric evidence does not resolve the source's ambiguous country mapping.");
        return result("DISCOVER-RETAILER", Status.PASS,
                "Table 059 matches supplied retailer-country evidence; actual retailer location is not certified.");
    }
    private static Assessment result(String rule, Status status, String reason) {
        return new Assessment("BR-SEG100-APPAC-" + rule, status, reason);
    }
}
