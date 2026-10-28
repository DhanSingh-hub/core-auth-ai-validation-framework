package com.coreauth.validator.canonical;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Logical field and supplied-evidence checks for Appendix Z, not a wire parser or network certification.
 * Request map keys are canonical Table IDs, optionally followed by "/Sub-Table ID".
 */
public final class AppendixZStoredCredentialOracle {
    public enum Status { PASS, FAIL, REVIEW_REQUIRED }
    public enum Flow {
        INITIAL_STORAGE, MIT_RECURRING, MIT_INSTALLMENT, MIT_UNSCHEDULED,
        CIT_RECURRING_INITIAL, CIT_INSTALLMENT_INITIAL, CIT_SUBSEQUENT
    }
    public enum Purpose { ADHOC, STANDING_ORDER, SUBSCRIPTION, INSTALLMENT, DELAYED_SALE, NO_SHOW }

    public record Evidence(
            String originalPosEntryMode, Boolean cardIssuedInIndia,
            BigDecimal amountUsdEquivalent, BigDecimal amountInrEquivalent,
            String firstApprovedAmountMinorUnits, Boolean cardholderParticipating, Boolean priorConsent,
            Boolean thirdPartyProvider, String providerName, String retailerName,
            String retailerCity, String retailerState, String retailerCountry, String retailerSic,
            Boolean ecommerce, Boolean cavvPresent, Boolean registrationSuccessful,
            Boolean amountDue, Boolean accountVerification) {
    }

    public record Scenario(String cardBrand, Flow flow, Purpose purpose, Map<String, String> request,
                           Map<String, String> originalResponse, Map<String, String> originalRequest,
                           Map<String, String> lastModification, Evidence evidence) {
        public Scenario {
            Objects.requireNonNull(flow, "flow");
            Objects.requireNonNull(purpose, "purpose");
            request = Map.copyOf(Objects.requireNonNull(request, "request"));
            originalResponse = Map.copyOf(Objects.requireNonNull(originalResponse, "originalResponse"));
            originalRequest = Map.copyOf(Objects.requireNonNull(originalRequest, "originalRequest"));
            lastModification = Map.copyOf(Objects.requireNonNull(lastModification, "lastModification"));
            Objects.requireNonNull(evidence, "evidence");
        }
    }

    public record Assessment(String businessRequirementId, Status status, String reason) { }

    public static final List<String> RULES = List.of("SCOPE", "INITIAL-STORAGE", "MIT-RECURRING",
            "MIT-INSTALLMENT", "MIT-UNSCHEDULED", "CIT-INITIAL", "CIT-SUBSEQUENT", "CVV2",
            "REFERENCES", "DISCOVER-AMOUNT", "DISCOVER-INDIA", "DISCOVER-INSTALLMENT",
            "DISCOVER-SPECIAL", "MASTERCARD-CATEGORY", "VISA-INDIA", "TABLE56-FORMAT", "CONSENT");
    private static final Set<String> BRANDS = Set.of("VISA", "MASTERCARD", "DISCOVER", "AMEX");
    private static final Set<String> VISA_TAGS = Set.of("01", "02", "03", "05", "06", "12", "13");
    private static final Map<String, Integer> WIDTHS = Map.ofEntries(
            Map.entry("01", 2), Map.entry("02", 1), Map.entry("03", 12), Map.entry("04", 14),
            Map.entry("05", 2), Map.entry("06", 1), Map.entry("07", 20), Map.entry("08", 2),
            Map.entry("09", 4), Map.entry("12", 1), Map.entry("13", 35));

    public List<Assessment> assess(Scenario s) {
        Objects.requireNonNull(s, "scenario");
        if (!BRANDS.contains(brand(s))) {
            return RULES.stream().map(rule -> result(rule, Status.REVIEW_REQUIRED,
                    "Appendix Z specifies Visa, MasterCard, Discover and Amex; unsupported or absent brand.")).toList();
        }
        List<Assessment> checks = new ArrayList<>();
        boolean purposeMatches = switch (s.flow()) {
            case MIT_RECURRING, CIT_RECURRING_INITIAL ->
                    s.purpose() == Purpose.STANDING_ORDER || s.purpose() == Purpose.SUBSCRIPTION;
            case MIT_INSTALLMENT, CIT_INSTALLMENT_INITIAL -> s.purpose() == Purpose.INSTALLMENT;
            case MIT_UNSCHEDULED, CIT_SUBSEQUENT ->
                    s.purpose() == Purpose.ADHOC || s.purpose() == Purpose.DELAYED_SALE || s.purpose() == Purpose.NO_SHOW;
            case INITIAL_STORAGE -> true;
        };
        checks.add(condition("SCOPE", purposeMatches, "Declared brand/flow/purpose are in scope.",
                "Declared transaction purpose conflicts with the selected stored-credential flow."));
        checks.add(initial(s));
        checks.add(mit(s, Flow.MIT_RECURRING, "MIT-RECURRING", "R"));
        checks.add(mit(s, Flow.MIT_INSTALLMENT, "MIT-INSTALLMENT", "I"));
        checks.add(mit(s, Flow.MIT_UNSCHEDULED, "MIT-UNSCHEDULED", null));
        checks.add(citInitial(s));
        checks.add(s.flow() == Flow.CIT_SUBSEQUENT
                ? fields(s, "CIT-SUBSEQUENT", Map.of("032/08", "C", "049/02", "C"), true, null)
                : notApplicable("CIT-SUBSEQUENT"));
        boolean cvvProhibited = s.flow() == Flow.MIT_RECURRING || s.flow() == Flow.MIT_INSTALLMENT
                || s.flow() == Flow.CIT_RECURRING_INITIAL || s.flow() == Flow.CIT_INSTALLMENT_INITIAL;
        checks.add(cvvProhibited
                ? condition("CVV2", !s.request().containsKey("004"),
                        "CVV2 Table 004 is absent.", "CVV2 Table 004 must not be sent in this flow.")
                : result("CVV2", s.flow() == Flow.CIT_SUBSEQUENT ? Status.PASS : Status.REVIEW_REQUIRED,
                        s.flow() == Flow.CIT_SUBSEQUENT ? "Appendix Z explicitly permits optional CVV2 for subsequent CIT."
                                : "Appendix Z supplies no CVV2 prohibition for this profile; no universal rule is inferred."));
        checks.add(references(s));
        checks.add(discoverAmount(s));
        checks.add(discoverIndia(s));
        checks.add(discoverInstallment(s));
        checks.add(discoverSpecial(s));
        checks.add(mastercard(s));
        checks.add(visaIndia(s));
        checks.add(table56(s));
        checks.add(consent(s));
        return List.copyOf(checks);
    }

    private static Assessment initial(Scenario s) {
        if (s.flow() != Flow.INITIAL_STORAGE) return notApplicable("INITIAL-STORAGE");
        String payment = switch (s.purpose()) {
            case STANDING_ORDER, SUBSCRIPTION -> "R";
            case INSTALLMENT -> "I";
            default -> null;
        };
        if (s.evidence().amountDue() == null) {
            return result("INITIAL-STORAGE", Status.REVIEW_REQUIRED,
                    "Amount-due context is needed to distinguish optional zero-dollar verification from an initial purchase.");
        }
        if (!s.evidence().amountDue()) {
            if (!Boolean.TRUE.equals(s.evidence().accountVerification())
                    || !"000000000000".equals(s.request().get("accountVerificationAmount"))) {
                return result("INITIAL-STORAGE", Status.REVIEW_REQUIRED,
                        "No amount is due: Appendix Z permits a $0 Account Verification; its type/amount evidence is needed.");
            }
            payment = null;
        }
        return fields(s, "INITIAL-STORAGE", Map.of("032/08", "I"), false, payment);
    }

    private static Assessment mit(Scenario s, Flow flow, String rule, String payment) {
        return s.flow() == flow
                ? fields(s, rule, Map.of("032/08", "C", "049/02", "M"), true, payment)
                : notApplicable(rule);
    }

    private static Assessment citInitial(Scenario s) {
        if (s.flow() != Flow.CIT_RECURRING_INITIAL && s.flow() != Flow.CIT_INSTALLMENT_INITIAL) {
            return notApplicable("CIT-INITIAL");
        }
        return fields(s, "CIT-INITIAL", Map.of("032/08", "I", "049/02", "C"), false,
                s.flow() == Flow.CIT_RECURRING_INITIAL ? "R" : "I");
    }

    private static Assessment fields(Scenario s, String rule, Map<String, String> required,
                                     boolean subsequent, String payment) {
        for (var field : required.entrySet()) {
            if (!field.getValue().equals(s.request().get(field.getKey()))) {
                return result(rule, Status.FAIL, field.getKey() + " must be " + field.getValue() + ".");
            }
        }
        String indicator = s.request().get("013");
        if (payment != null && !payment.equals(indicator)
                || s.flow() == Flow.MIT_UNSCHEDULED && ("R".equals(indicator) || "I".equals(indicator))) {
            return result(rule, Status.FAIL, "Table 013 recurring/installment indicator disagrees with the profile.");
        }
        String mode = s.request().get("005");
        if (!new AppendixILogicalDataValidator().validate("005", mode, brand(s)).representationValid()) {
            return result(rule, Status.FAIL, "Table 005 must contain an Appendix J PAN mode and PIN capability.");
        }
        if (subsequent) {
            return condition(rule, mode.startsWith("10"), "Subsequent COF has PAN entry mode 10.",
                    "Subsequent COF requires Table 005 PAN entry mode 10 (not a two-byte table value).");
        }
        if (s.evidence().originalPosEntryMode() == null) {
            return result(rule, Status.REVIEW_REQUIRED, "Original POS entry mode evidence is absent.");
        }
        return condition(rule, mode.equals(s.evidence().originalPosEntryMode()),
                "Original POS entry mode is preserved.", "Initial storage must preserve original POS entry mode.");
    }

    private static Assessment references(Scenario s) {
        if (!isMit(s)) return result("REFERENCES", Status.REVIEW_REQUIRED,
                "Store response Table 028 (Visa/Amex) or 012 (Discover) for subsequent MIT; storage needs lifecycle evidence.");
        if ("MASTERCARD".equals(brand(s))) return notApplicable("REFERENCES");
        String requestKey = "DISCOVER".equals(brand(s)) ? "015" : "044";
        String responseKey = "DISCOVER".equals(brand(s)) ? "012" : "028";
        String value = s.request().get(requestKey);
        if (value == null || value.isBlank()) return result("REFERENCES", Status.FAIL,
                "Subsequent MIT is missing request Table " + requestKey + " from response Table " + responseKey + ".");
        if (!value.matches("[A-Za-z0-9]{15}")) return result("REFERENCES", Status.FAIL,
                "Request Table " + requestKey + " requires a 15-character alphanumeric network reference.");
        String original = s.originalResponse().get(responseKey);
        if (original == null) return result("REFERENCES", Status.REVIEW_REQUIRED, "Original response reference is unavailable.");
        if (!value.equals(original) && !"DISCOVER".equals(brand(s))) {
            return result("REFERENCES", Status.REVIEW_REQUIRED,
                    "Appendix Z describes original-reference reuse, while Appendix I Table 044 shows rolling response "
                            + "identifiers; an authoritative lifecycle decision is needed for this mismatch.");
        }
        return condition("REFERENCES", value.equals(original), "Stored response reference is carried forward.",
                "Discover request reference differs from the stored response reference.");
    }

    private static Assessment discoverAmount(Scenario s) {
        if (!"DISCOVER".equals(brand(s)) || !isMit(s)) return notApplicable("DISCOVER-AMOUNT");
        String amount = s.request().get("045");
        if (amount == null || !amount.matches("[0-9]{1,12}")) return result("DISCOVER-AMOUNT", Status.FAIL,
                "Discover MIT requires Table 045: 1-12 digits with two assumed decimals.");
        String original = s.evidence().firstApprovedAmountMinorUnits();
        if (original == null || !original.matches("[0-9]{1,12}")) return result("DISCOVER-AMOUNT",
                Status.REVIEW_REQUIRED, "First approved amount evidence is missing or invalid.");
        return condition("DISCOVER-AMOUNT", new BigInteger(amount).equals(new BigInteger(original)),
                "First approved amount is retained (leading zeros do not change value).",
                "First Authorization Amount differs from the original approved amount.");
    }

    private static Assessment discoverIndia(Scenario s) {
        if (!"DISCOVER".equals(brand(s)) || (s.flow() != Flow.MIT_RECURRING && s.flow() != Flow.MIT_UNSCHEDULED)) {
            return notApplicable("DISCOVER-INDIA");
        }
        if (Boolean.FALSE.equals(s.evidence().cardIssuedInIndia())) return notApplicable("DISCOVER-INDIA");
        if (s.evidence().cardIssuedInIndia() == null || s.evidence().amountUsdEquivalent() == null
                || s.evidence().amountUsdEquivalent().signum() < 0) {
            return result("DISCOVER-INDIA", Status.REVIEW_REQUIRED,
                    "Issued-in-India and nonnegative USD-equivalent amount evidence is needed; no currency conversion is guessed.");
        }
        if (s.evidence().amountUsdEquivalent().compareTo(new BigDecimal("52")) <= 0) {
            return notApplicable("DISCOVER-INDIA");
        }
        return result("DISCOVER-INDIA", Status.REVIEW_REQUIRED,
                "Appendix Z calls for Table 056 sub-tables 01-08 above $52 for India-issued Discover MIT, "
                        + "but Appendix I limits 01/02/05 to first payments. Presence and valid representations "
                        + "can be checked; subsequent requiredness needs source-owner clarification.");
    }

    private static Assessment discoverInstallment(Scenario s) {
        if (!"DISCOVER".equals(brand(s)) || s.flow() != Flow.MIT_INSTALLMENT) return notApplicable("DISCOVER-INSTALLMENT");
        if (!s.request().containsKey("056/01") || !s.request().containsKey("056/08")
                || !Set.of("S", "T", "V").contains(s.request().getOrDefault("049/13", ""))) {
            return result("DISCOVER-INSTALLMENT", Status.FAIL,
                    "Discover MIT installment requires Table 056 tags 01/08 and Table 049/13 installment type S/T/V.");
        }
        String previousSequence = s.originalRequest().get("056/08");
        String sequence = s.request().get("056/08");
        if (!sequence.matches("[0-9]{2}")) {
            return result("DISCOVER-INSTALLMENT", Status.FAIL, "Discover installment sequence must contain two digits.");
        }
        if (previousSequence != null && previousSequence.matches("[0-9]{2}")
                && Integer.parseInt(sequence) <= Integer.parseInt(previousSequence)) {
            return result("DISCOVER-INSTALLMENT", Status.FAIL,
                    "Discover installment sequence must ascend relative to the supplied prior installment.");
        }
        Boolean thirdParty = s.evidence().thirdPartyProvider();
        if ("T".equals(s.request().get("049/13"))) {
            if (Boolean.FALSE.equals(thirdParty)) return result("DISCOVER-INSTALLMENT", Status.FAIL,
                    "Third-party installment type T conflicts with supplied provider context.");
            thirdParty = true;
        }
        if (Boolean.TRUE.equals(thirdParty)) {
            Evidence e = s.evidence();
            for (String key : Set.of("047", "057", "058", "059", "007")) {
                if (!s.request().containsKey(key) || s.request().get(key).isBlank()) {
                    return result("DISCOVER-INSTALLMENT", Status.FAIL,
                            "Third-party installment is missing required retailer descriptor Table " + key + ".");
                }
            }
            String descriptor = s.request().get("047");
            int separator = descriptor.indexOf('*');
            if (descriptor.length() > 38 || separator <= 0 || separator == descriptor.length() - 1
                    || s.request().get("057").length() > 20
                    || !new AppendixILogicalDataValidator().validate("007", s.request().get("007"), brand(s))
                            .representationValid()) {
                return result("DISCOVER-INSTALLMENT", Status.FAIL,
                        "Provider*retailer name, city width or underlying retailer SIC representation is invalid.");
            }
            if (e.providerName() == null || e.retailerName() == null || e.retailerCity() == null
                    || e.retailerState() == null || e.retailerCountry() == null || e.retailerSic() == null) {
                return result("DISCOVER-INSTALLMENT", Status.REVIEW_REQUIRED,
                        "Third-party provider and underlying retailer identity/address/SIC evidence is incomplete.");
            }
            if (!Objects.equals(s.request().get("047"), e.providerName() + "*" + e.retailerName())
                    || !Objects.equals(s.request().get("057"), e.retailerCity())
                    || !Objects.equals(s.request().get("058"), e.retailerState())
                    || !Objects.equals(s.request().get("059"), e.retailerCountry())
                    || !Objects.equals(s.request().get("007"), e.retailerSic())) {
                return result("DISCOVER-INSTALLMENT", Status.FAIL,
                        "Tables 047/057/058/059/007 must identify provider*retailer and underlying retailer location/SIC.");
            }
        } else if (thirdParty == null) {
            return result("DISCOVER-INSTALLMENT", Status.REVIEW_REQUIRED, "Third-party-provider applicability is unknown.");
        }
        return result("DISCOVER-INSTALLMENT", Status.REVIEW_REQUIRED,
                "Installment fields and supplied retailer evidence match; Appendix I first-payment-only tag 01 "
                        + "conflicts with Appendix Z subsequent installment wording.");
    }

    private static Assessment discoverSpecial(Scenario s) {
        if (!"DISCOVER".equals(brand(s)) || s.flow() != Flow.MIT_UNSCHEDULED) return notApplicable("DISCOVER-SPECIAL");
        if (s.purpose() == Purpose.DELAYED_SALE) return condition("DISCOVER-SPECIAL",
                "D".equals(s.request().get("016")), "Delayed Card Sale is D.", "Table 016 must be D for Delayed Card Sale.");
        if (s.purpose() == Purpose.NO_SHOW) return condition("DISCOVER-SPECIAL",
                "N".equals(s.request().get("017")), "No Show is N.", "Table 017 must be N for No Show Charge.");
        return notApplicable("DISCOVER-SPECIAL");
    }

    private static Assessment mastercard(Scenario s) {
        if (!"MASTERCARD".equals(brand(s))) return notApplicable("MASTERCARD-CATEGORY");
        String suffix = switch (s.flow()) {
            case MIT_INSTALLMENT, CIT_INSTALLMENT_INITIAL -> "104";
            case MIT_UNSCHEDULED, CIT_SUBSEQUENT -> "101";
            default -> switch (s.purpose()) {
                case STANDING_ORDER -> "102";
                case SUBSCRIPTION -> "103";
                case INSTALLMENT -> "104";
                default -> "101";
            };
        };
        if ((s.flow() == Flow.MIT_RECURRING || s.flow() == Flow.CIT_RECURRING_INITIAL) && "101".equals(suffix)) {
            return result("MASTERCARD-CATEGORY", Status.REVIEW_REQUIRED,
                    "Recurring category selection requires standing-order versus subscription context.");
        }
        String expected = (isMit(s) ? "M" : "C") + suffix;
        return condition("MASTERCARD-CATEGORY", expected.equals(s.request().get("056/09")),
                "MasterCard Table 056/09 category is " + expected + ".", "MasterCard Table 056/09 must be " + expected + ".");
    }

    private static Assessment visaIndia(Scenario s) {
        boolean recurring = s.flow() == Flow.MIT_RECURRING || s.flow() == Flow.CIT_RECURRING_INITIAL
                || s.flow() == Flow.INITIAL_STORAGE && (s.purpose() == Purpose.STANDING_ORDER || s.purpose() == Purpose.SUBSCRIPTION);
        if (!"VISA".equals(brand(s)) || !recurring) return notApplicable("VISA-INDIA");
        Evidence e = s.evidence();
        if (Boolean.FALSE.equals(e.cardIssuedInIndia())) return notApplicable("VISA-INDIA");
        if (e.cardIssuedInIndia() == null) return result("VISA-INDIA", Status.REVIEW_REQUIRED, "Issuer country evidence is absent.");
        for (String tag : VISA_TAGS) {
            if (!s.request().containsKey("056/" + tag)) return result("VISA-INDIA", Status.FAIL,
                    "Visa India recurring requires Table 056/" + tag + "; Discover-only tags are not imposed.");
        }
        String action = s.request().get("056/12");
        if (!Set.of("1", "2", "3", "4").contains(action)) {
            return result("VISA-INDIA", Status.FAIL, "Visa recurring action must be 1, 2, 3 or 4.");
        }
        if (s.flow() != Flow.MIT_RECURRING && !"1".equals(action)) {
            return result("VISA-INDIA", Status.FAIL, "Initial recurring registration requires Table 056/12 value 1.");
        }
        boolean authentication = !"2".equals(action);
        if ("2".equals(action)) {
            if (e.amountInrEquivalent() == null || e.amountInrEquivalent().signum() < 0) return result("VISA-INDIA",
                    Status.REVIEW_REQUIRED, "Nonnegative INR-equivalent amount evidence is required at the 15,000 INR boundary.");
            authentication = e.amountInrEquivalent().compareTo(new BigDecimal("15000")) > 0;
            if (Boolean.FALSE.equals(e.registrationSuccessful())) return result("VISA-INDIA", Status.FAIL,
                    "Subsequent Visa recurring cannot follow unsuccessful registration/authentication/authorization.");
            if (e.registrationSuccessful() == null) return result("VISA-INDIA", Status.REVIEW_REQUIRED,
                    "Subsequent recurring needs evidence of successful original registration/authentication/authorization.");
        }
        if (e.ecommerce() == null || e.cavvPresent() == null) return result("VISA-INDIA", Status.REVIEW_REQUIRED,
                "E-commerce and CAVV presence evidence is missing.");
        if (e.ecommerce() != authentication || e.cavvPresent() != authentication
                || authentication && !"25".equals(s.request().get("030/terminalType"))) {
            return result("VISA-INDIA", Status.FAIL,
                    "Visa India authentication/e-commerce/Terminal Type 25 does not match action and 15,000 INR boundary.");
        }
        if ("4".equals(action) && (!Boolean.TRUE.equals(e.accountVerification())
                || !"000000000000".equals(s.request().get("accountVerificationAmount")))) {
            return result("VISA-INDIA", Status.FAIL, "Cancellation requires zero-amount Account Verification evidence.");
        }
        if ("2".equals(action) || "3".equals(action) || "4".equals(action)) {
            if (s.originalRequest().isEmpty()) return result("VISA-INDIA", Status.REVIEW_REQUIRED, "Original registration parameters are absent.");
            if (!Objects.equals(s.request().get("056/13"), s.originalRequest().get("056/13"))) {
                return result("VISA-INDIA", Status.FAIL, "Visa registration reference must remain unchanged.");
            }
            if ("2".equals(action)) {
                for (String tag : Set.of("01", "02", "03", "05", "06")) {
                    String expected = s.lastModification().getOrDefault("056/" + tag, s.originalRequest().get("056/" + tag));
                    if (!Objects.equals(s.request().get("056/" + tag), expected)) return result("VISA-INDIA",
                            Status.FAIL, "Recurring parameter 056/" + tag + " differs from registration/latest modification.");
                }
            }
        }
        return result("VISA-INDIA", Status.PASS,
                "Source-version Visa India recurring fields, authentication boundary and supplied lifecycle evidence match; cryptographic authenticity is not certified.");
    }

    private static Assessment table56(Scenario s) {
        String unsupportedTag = null;
        for (var field : s.request().entrySet()) {
            if (!field.getKey().startsWith("056/")) continue;
            String tag = field.getKey().substring(4);
            Integer width = WIDTHS.get(tag);
            if (width == null) {
                unsupportedTag = tag;
                continue;
            }
            String value = field.getValue();
            if (value.length() != width || value.chars().anyMatch(c -> c < 32 || c > 126)) return result("TABLE56-FORMAT",
                    Status.FAIL, "Table 056/" + tag + " must have " + width + " ASCII characters.");
            boolean applicable = switch (tag) {
                case "04", "07", "08" -> "DISCOVER".equals(brand(s));
                case "09" -> "MASTERCARD".equals(brand(s));
                case "12", "13" -> "VISA".equals(brand(s));
                default -> "VISA".equals(brand(s)) || "DISCOVER".equals(brand(s));
            };
            if (!applicable) return result("TABLE56-FORMAT", Status.FAIL,
                    "Table 056/" + tag + " is not applicable to the declared card brand.");
            if ("VISA".equals(brand(s)) && VISA_TAGS.contains(tag)) {
                boolean recurring = s.flow() == Flow.MIT_RECURRING || s.flow() == Flow.CIT_RECURRING_INITIAL
                        || s.flow() == Flow.INITIAL_STORAGE && (s.purpose() == Purpose.STANDING_ORDER
                        || s.purpose() == Purpose.SUBSCRIPTION);
                if (!recurring || Boolean.FALSE.equals(s.evidence().cardIssuedInIndia())) {
                    return result("TABLE56-FORMAT", Status.FAIL,
                            "Visa Table 056/" + tag + " is only applicable to India-issued recurring payments.");
                }
            }
            boolean valid = switch (tag) {
                case "01" -> "VISA".equals(brand(s)) ? value.matches("(0[1-9]|[1-9][0-9])")
                        : value.matches("(0[2-9]|[1-9][0-9]|UD|UC)");
                case "02" -> Set.of("F", "V").contains(value);
                case "03" -> value.matches("[0-9]{12}");
                case "04", "13" -> value.matches("[A-Za-z0-9]+");
                case "05" -> value.matches("0[1-8]") || "VISA".equals(brand(s)) && value.matches("(09|1[0-2])");
                case "06" -> Set.of("0", "1").contains(value);
                case "08" -> value.matches("[0-9]{2}");
                case "09" -> Set.of("C101", "C102", "C103", "C104", "M101", "M102", "M103", "M104").contains(value);
                case "12" -> Set.of("1", "2", "3", "4").contains(value);
                default -> true;
            };
            if (!valid) return result("TABLE56-FORMAT", Status.FAIL, "Invalid Table 056/" + tag + " value.");
        }
        if (unsupportedTag != null) return result("TABLE56-FORMAT", Status.REVIEW_REQUIRED,
                "Table 056 tag " + unsupportedTag + " is outside this bounded Appendix Z layout.");
        return result("TABLE56-FORMAT", Status.PASS, "Supplied in-scope Table 056 fields fit their logical widths/value sets; wire framing is not certified.");
    }

    private static Assessment consent(Scenario s) {
        if (!isMit(s)) return result("CONSENT", Status.REVIEW_REQUIRED,
                "Stored credential identity, initial consent, and single-transaction/incremental exclusions require business evidence.");
        Evidence e = s.evidence();
        if (Boolean.TRUE.equals(e.cardholderParticipating()) || Boolean.FALSE.equals(e.priorConsent())) {
            return result("CONSENT", Status.FAIL, "MIT requires prior consent and no active cardholder engagement.");
        }
        return result("CONSENT", Status.REVIEW_REQUIRED,
                "Verify prior consent, credential storage, standing instructions and no active cardholder engagement externally; flags alone are not certification.");
    }

    private static boolean isMit(Scenario s) {
        return s.flow() == Flow.MIT_RECURRING || s.flow() == Flow.MIT_INSTALLMENT || s.flow() == Flow.MIT_UNSCHEDULED;
    }
    private static String brand(Scenario s) {
        return s.cardBrand() == null ? "" : s.cardBrand().replace(" ", "").toUpperCase(Locale.ROOT);
    }
    private static Assessment notApplicable(String rule) {
        return result(rule, Status.PASS, "This rule is not applicable to the declared brand/flow.");
    }
    private static Assessment condition(String rule, boolean valid, String positive, String negative) {
        return result(rule, valid ? Status.PASS : Status.FAIL, valid ? positive : negative);
    }
    private static Assessment result(String rule, Status status, String reason) {
        return new Assessment("BR-SEG100-APPZ-" + rule, status, reason);
    }
}
