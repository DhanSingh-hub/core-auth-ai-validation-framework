package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.coreauth.validator.canonical.AppendixILogicalDataValidator.Status.*;

public final class AppendixIScopedCandidateValidator {
    public static final Set<String> CANDIDATE_IDS = Set.of("APPI-C010-01", "APPI-C010-02",
            "APPI-C011-01", "APPI-C011-02", "APPI-C011-03", "APPI-C012-01",
            "APPI-C013-01", "APPI-C013-02", "APPI-C014-01", "APPI-C015-01", "APPI-C017-01");

    public AppendixILogicalDataValidator.Result validate(JsonNode candidate) {
        List<AppendixILogicalDataValidator.Finding> findings = new ArrayList<>();
        if (candidate == null || !candidate.isObject()) {
            add(findings, "APPI-CANDIDATE-SHAPE", FAIL, "A structured logical candidate is required.");
            return new AppendixILogicalDataValidator.Result(findings);
        }
        String id = text(candidate, "candidateId");
        if (!CANDIDATE_IDS.contains(id)) {
            add(findings, "APPI-CANDIDATE-SCOPE", NOT_ASSERTABLE,
                    "No implemented predicate for this logical candidate.");
            return new AppendixILogicalDataValidator.Result(findings);
        }
        if (!id.substring(6, 9).equals(text(candidate, "tableId"))) {
            add(findings, "APPI-CANDIDATE-IDENTITY", FAIL, "Candidate and table identities disagree.");
            return new AppendixILogicalDataValidator.Result(findings);
        }
        JsonNode fields = candidate.path("logicalFields");
        JsonNode context = candidate.path("context");
        switch (text(candidate, "tableId")) {
            case "010" -> {
                check(findings, "APPI-T010-LAYOUT", matches(fields, "visaBID", "[0-9A-Fa-f]{10}")
                                && matches(fields, "visaTAP", "[0-9A-Fa-f]{12}")
                                && printable(fields, "tppVarId", 6, 6)
                                && "  ".equals(text(fields, "reserved")),
                        "BID/TAP widths and hexadecimal syntax, TPP width and reserved fill only.");
                JsonNode flags = fields.path("context");
                if (!booleans(flags, "bidUsed", "tapUsed", "tppVarIncluded")) {
                    add(findings, "APPI-T010-ZERO-FILL", REVIEW_REQUIRED,
                            "Explicit identifier-use flags are required; assignment is not inferred.");
                } else {
                    boolean bid = flags.path("bidUsed").booleanValue();
                    boolean tap = flags.path("tapUsed").booleanValue();
                    boolean tpp = flags.path("tppVarIncluded").booleanValue();
                    check(findings, "APPI-T010-ZERO-FILL",
                            (bid || !(tap || tpp) || "0".repeat(10).equals(text(fields, "visaBID")))
                                    && (tap || !(bid || tpp) || "0".repeat(12).equals(text(fields, "visaTAP"))),
                            "Unused BID/TAP zero fill under explicit supplied preconditions only.");
                }
            }
            case "011" -> {
                if (!booleans(context, "taxable", "salesTaxIncluded")) {
                    add(findings, "APPI-T011-MAPPING", REVIEW_REQUIRED, "Tax context is required.");
                } else if (!context.path("taxable").booleanValue()
                        && context.path("salesTaxIncluded").booleanValue()) {
                    add(findings, "APPI-T011-MAPPING", FAIL, "Nontaxable context cannot include sales tax.");
                } else {
                    String expected = !context.path("taxable").booleanValue() ? "N"
                            : context.path("salesTaxIncluded").booleanValue() ? "Y" : " ";
                    check(findings, "APPI-T011-MAPPING", expected.equals(text(candidate, "data")),
                            "Tax indicator must match the supplied taxable/tax-included context.");
                }
            }
            case "012" -> check(findings, "APPI-T012-MAPPING",
                    "M".equals(text(fields, "medical")) && "B".equals(text(fields, "billPayment"))
                            && "E".equals(text(fields, "ecommerceAggregate")) && "H".equals(text(fields, "hotel")),
                    "The four printed market mappings only; operational availability is unassessed.");
            case "013" -> {
                String kind = text(context, "paymentKind");
                if (!Set.of("Recurring", "Installment").contains(kind)) {
                    add(findings, "APPI-T013-MAPPING", REVIEW_REQUIRED, "Explicit payment kind is required.");
                } else {
                    check(findings, "APPI-T013-MAPPING",
                            ("Recurring".equals(kind) ? "R" : "I").equals(text(candidate, "data")),
                            "Payment indicator must match the supplied payment kind.");
                }
                if ("R".equals(text(candidate, "data"))) {
                    String card = text(context, "cardType");
                    String brand = text(context, "binBrandClassification");
                    if (card.isEmpty() || !Set.of("Visa", "MasterCard", "Discover", "Amex").contains(brand)) {
                        add(findings, "APPI-T013-RECURRING", REVIEW_REQUIRED,
                                "Credit card type and classified BIN brand are required; no BIN database is used.");
                    } else {
                        check(findings, "APPI-T013-RECURRING", "020".equals(card),
                                "Recurring eligibility against supplied classification only, not actual BIN validity.");
                    }
                }
            }
            case "014" -> {
                check(findings, "APPI-T014-WIDTH", printable(fields, "requestUserData", 1, 15),
                        "Printable ASCII candidate user data within fifteen characters only.");
                check(findings, "APPI-T014-ECHO", !text(fields, "requestUserData").isEmpty()
                                && text(fields, "requestUserData").equals(text(fields, "responseEcho")),
                        "Supplied logical echo must equal the request value; no host response is executed.");
                if (!booleans(fields, "issuerForwarded")) {
                    add(findings, "APPI-T014-NONFORWARD", REVIEW_REQUIRED,
                            "Explicit logical forwarding evidence is required.");
                } else {
                    check(findings, "APPI-T014-NONFORWARD", !fields.path("issuerForwarded").booleanValue(),
                            "Supplied forwarding assertion must be false; no issuer trace is verified.");
                }
            }
            case "015" -> {
                check(findings, "APPI-T015-WIDTH", printable(fields, "associatedResponseRRN", 15, 15)
                                && printable(fields, "subsequentRequestRRN", 15, 15),
                        "Printable ASCII reference widths only; actual network issuance is unassessed.");
                JsonNode flags = fields.path("context");
                if (!"Discover".equals(text(flags, "brand")) || !"MIT".equals(text(flags, "initiation"))
                        || !"Subsequent".equals(text(flags, "cofState"))) {
                    add(findings, "APPI-T015-RELATION", REVIEW_REQUIRED,
                            "This relation check is bounded to Discover subsequent merchant-initiated COF.");
                } else {
                    check(findings, "APPI-T015-RELATION", !text(fields, "associatedResponseRRN").isEmpty()
                                    && text(fields, "associatedResponseRRN").equals(text(fields, "subsequentRequestRRN")),
                            "Subsequent request reference must equal the supplied associated response reference.");
                }
            }
            case "017" -> {
                check(findings, "APPI-T017-VALUE", "34".equals(text(candidate, "data")),
                        "Suspected-fraud indicator has fixed value 34.");
                if (!booleans(context, "suspectedFraud") || text(context, "transactionClass").isEmpty()
                        || text(context, "cardPresence").isEmpty() || text(context, "brand").isEmpty()) {
                    add(findings, "APPI-T017-CONTEXT", REVIEW_REQUIRED,
                            "Explicit reversal, fraud, presence and brand context is required.");
                } else if (!"MasterCard".equals(text(context, "brand"))
                        || !"CNP".equals(text(context, "cardPresence"))) {
                    add(findings, "APPI-T017-CONTEXT", REVIEW_REQUIRED,
                            "Other brand or presence classifications are outside this candidate's context check.");
                } else {
                    check(findings, "APPI-T017-CONTEXT",
                            context.path("suspectedFraud").booleanValue()
                                    && "Purchase reversal request".equals(text(context, "transactionClass")),
                            "This candidate checks the supplied MasterCard CNP fraud-reversal context only.");
                }
            }
            default -> throw new IllegalStateException("Unhandled implemented candidate");
        }
        add(findings, "APPI-CANDIDATE-READINESS", REVIEW_REQUIRED,
                "Logical checks do not establish framing, full-message validity, SME approval or host acceptance.");
        return new AppendixILogicalDataValidator.Result(findings);
    }

    public static String mutationTarget(String id) {
        return switch (id) {
            case "APPI-C010-01" -> "APPI-T010-LAYOUT";
            case "APPI-C010-02" -> "APPI-T010-ZERO-FILL";
            case "APPI-C011-01", "APPI-C011-02", "APPI-C011-03" -> "APPI-T011-MAPPING";
            case "APPI-C012-01" -> "APPI-T012-MAPPING";
            case "APPI-C013-01" -> "APPI-T013-RECURRING";
            case "APPI-C013-02" -> "APPI-T013-MAPPING";
            case "APPI-C014-01" -> "APPI-T014-ECHO";
            case "APPI-C015-01" -> "APPI-T015-RELATION";
            case "APPI-C017-01" -> "APPI-T017-VALUE";
            default -> throw new IllegalArgumentException("Unsupported candidate: " + id);
        };
    }

    private static String text(JsonNode node, String field) {
        return node.path(field).isTextual() ? node.path(field).textValue() : "";
    }

    private static boolean printable(JsonNode node, String field, int min, int max) {
        String value = text(node, field);
        return value.length() >= min && value.length() <= max
                && value.chars().allMatch(c -> c >= 32 && c <= 126);
    }

    private static boolean matches(JsonNode node, String field, String pattern) {
        return node.path(field).isTextual() && node.path(field).textValue().matches(pattern);
    }

    private static boolean booleans(JsonNode node, String... fields) {
        for (String field : fields) {
            if (!node.path(field).isBoolean()) return false;
        }
        return true;
    }

    private static void check(List<AppendixILogicalDataValidator.Finding> findings,
                              String rule, boolean valid, String scope) {
        add(findings, rule, valid ? PASS : FAIL, scope);
    }

    private static void add(List<AppendixILogicalDataValidator.Finding> findings,
                            String rule, AppendixILogicalDataValidator.Status status, String reason) {
        findings.add(new AppendixILogicalDataValidator.Finding(rule, status, reason));
    }
}
