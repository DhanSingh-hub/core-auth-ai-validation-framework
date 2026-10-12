package com.coreauth.validator.canonical;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Finding;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Sections 12.31/12.32 table sublayouts; configuration and host authenticity remain external. */
final class EnhancedFleetTableValidator {
    private static final Set<String> WEX_CATEGORIES = Set.of("ADD", "ANFR", "BRAK", "CADV", "CLTH", "DEF", "DELI",
            "ELEC", "ETAX", "EVC1", "EVC2", "EVC3", "FAX", "FLAT", "GROC", "HARD", "IDLE", "LMPR", "LUBE",
            "MERC", "OIL", "OILC", "PADV", "PART", "PHON", "PNT", "RECP", "REPR", "REST", "SCAN", "SCLE",
            "SHWR", "TCHN", "TIRE", "TOLL", "TRAL", "TRPP", "UREA", "WASH", "WWFL");
    private static final Map<String, Integer> LABELS = Map.of("FNAM", 15, "LNAM", 20, "ATHN", 4, "ACCT", 5, "DMSG", 80);
    private EnhancedFleetTableValidator() { }

    static List<Finding> validate(IndicatorLengthValueSectionValidator.Triad table, boolean request, String authorizer, JsonNode history) throws IOException {
        List<Finding> findings = new ArrayList<>();
        String id = table.indicator(), value = table.value();
        String rule = request ? "SEG145-R-006" : "SEG146-R-002";
        if (value == null || id == null) return List.of();
        if (!request && "005".equals(id)) {
            check(findings, rule, "customer-layout", value.length() >= 42 && value.length() <= 70,
                    "Customer has fixed name25/city15/state2 positions and remaining account code");
        }
        if (!Set.of("002", "003", "010").contains(id) || request && !"002".equals(id)) return List.copyOf(findings);
        boolean framed = value.length() > 2 && value.startsWith("|") && value.endsWith("|");
        check(findings, rule, "fleet-product-framing", framed, "Repeated entries retain leading/trailing pipes");
        if (!framed) return List.copyOf(findings);
        Set<String> seen = new HashSet<>();
        AppendixFProductCodeOracle oracle = !request && "003".equals(id) ? new AppendixFProductCodeOracle() : null;
        for (String entry : value.substring(1, value.length() - 1).split("\\|", -1)) {
            int colon = entry.indexOf(':');
            String label = colon < 0 ? "" : entry.substring(0, colon);
            String data = colon < 0 ? "" : entry.substring(colon + 1);
            boolean labelValid = label.matches("003".equals(id) ? "[0-9]{3}" : "010".equals(id) ? "[A-Z]{4}" : "[A-Z0-9]{1,4}");
            boolean unique = seen.add(label);
            check(findings, rule, "fleet-entry-label", labelValid && (!request || unique), "Entries use source-shaped labels; request categories are aggregated once");
            if (!request && !unique) findings.add(new Finding(rule, "duplicate-response-entry", Status.REVIEW_REQUIRED,
                    "Repeated response label requires authorizer/configuration resolution; no last-value or summed-limit policy is inferred"));
            if ("010".equals(id)) {
                check(findings, rule, "additional-response-label", LABELS.containsKey(label)
                        && data.length() <= LABELS.getOrDefault(label, 0), "Additional response uses published labels and individual caps");
            } else if (request) {
                check(findings, rule, "nonfuel-purchase", data.matches("[0-9]{1,3},[0-9]+\\.[0-9]+")
                        && data.substring(data.indexOf(',') + 1).length() <= 9, "Nonfuel category has quantity max3 and decimal amount max9");
            } else {
                check(findings, rule, "product-limit", data.matches("[0-9]+\\.[0-9]+") && data.length() <= ("003".equals(id) ? 8 : 9),
                        "Limit is a decimal currency amount within source field cap");
            }
            if ("002".equals(id)) {
                if ("WEX_OTR".equals(authorizer)) check(findings, request ? "SEG145-R-007" : rule,
                        "wex-category", WEX_CATEGORIES.contains(label), "Category is in the source-listed WEX OTR table");
                else findings.add(new Finding(rule, "category-configuration", Status.REVIEW_REQUIRED,
                        "Other authorizers use independently observed Dynamic Card Table categories; no WEX list is imposed"));
            }
            if (oracle != null && labelValid) {
                JsonNode classification = oracle.evaluate(JsonNodeFactory.instance.objectNode().put("messageRole", "TRANSACTION_PRODUCT_CODE")
                        .put("mode", "CLASSIFICATION").put("ProductCode", label));
                String outcome = classification.path("outcome").asText();
                if ("REVIEW_REQUIRED".equals(outcome) || !"ASSIGNED".equals(classification.path("assignment").asText())) {
                    findings.add(new Finding("SEG146-R-005", "fuel-limit-code", Status.REVIEW_REQUIRED, "Appendix F assignment/classification is not resolved"));
                } else check(findings, "SEG146-R-005", "fuel-limit-code",
                        Set.of("MOTOR_FUEL", "AVIATION_FUEL", "MARINE_FUEL", "OTHER_FUEL").contains(classification.path("role").asText()),
                        "Fuel Product Limits use source-classified fuel codes from Appendix F");
            }
        }
        if (Set.of("002", "003").contains(id)) reconcile(value, request, id, history, findings);
        return List.copyOf(findings);
    }

    private static void reconcile(String table, boolean request, String id, JsonNode history, List<Finding> findings) {
        String rule = request ? "SEG145-R-007" : "003".equals(id) ? "SEG146-R-005" : "SEG146-R-002";
        String property = "003".equals(id) ? "fuelProducts" : "nonfuelProducts";
        JsonNode products = history.get(property);
        if (products == null) {
            findings.add(new Finding(rule, "fleet-product-correlation", Status.REVIEW_REQUIRED, "Observed purchases and category mapping required to execute aggregation/limits"));
            return;
        }
        boolean complete = history.path(property + "Complete").isBoolean() && history.path(property + "Complete").booleanValue();
        if (!products.isArray() || !history.path(property + "Complete").isBoolean()) {
            check(findings, rule, "fleet-product-correlation", false, "Product observations must be an array with explicit boolean completeness");
            return;
        }
        Map<String, BigDecimal> amounts = new java.util.LinkedHashMap<>(), quantities = new java.util.LinkedHashMap<>();
        for (JsonNode product : products) {
            JsonNode code = product.get("003".equals(id) ? "productCode" : "category");
            String label = code == null && !"003".equals(id) ? "MERC" : code != null && code.isTextual() ? code.textValue() : "";
            String amount = text(product, "amount"), quantity = text(product, "quantity");
            if (!product.isObject() || !label.matches("003".equals(id) ? "[0-9]{3}" : "[A-Z0-9]{1,4}")
                    || amount == null || !amount.matches("[0-9]{1,9}(?:\\.[0-9]{1,9})?")
                    || request && (quantity == null || !quantity.matches("[0-9]{1,3}"))) {
                check(findings, rule, "fleet-product-correlation", false, "Observed product uses source-shaped identity, unsigned currency and request quantity");
                return;
            }
            amounts.merge(label, new BigDecimal(amount), BigDecimal::add);
            if (request) quantities.merge(label, new BigDecimal(quantity), BigDecimal::add);
        }
        Set<String> labels = new HashSet<>();
        boolean valid = true;
        for (String entry : table.substring(1, table.length() - 1).split("\\|", -1)) {
            int colon = entry.indexOf(':');
            if (colon < 1) { valid = false; continue; }
            String label = entry.substring(0, colon), data = entry.substring(colon + 1);
            labels.add(label);
            String encodedAmount = request && data.contains(",") ? data.substring(data.indexOf(',') + 1) : data;
            if (!encodedAmount.matches("[0-9]+\\.[0-9]+")) { valid = false; continue; }
            BigDecimal measured = amounts.getOrDefault(label, BigDecimal.ZERO), limit = new BigDecimal(encodedAmount);
            if (request) {
                String encodedQuantity = data.contains(",") ? data.substring(0, data.indexOf(',')) : "";
                if (!encodedQuantity.matches("[0-9]{1,3}")) { valid = false; continue; }
                int amountComparison = measured.compareTo(limit);
                int quantityComparison = quantities.getOrDefault(label, BigDecimal.ZERO).compareTo(new BigDecimal(encodedQuantity));
                valid &= complete ? amountComparison == 0 && quantityComparison == 0 : amountComparison <= 0 && quantityComparison <= 0;
            } else valid &= measured.compareTo(limit) <= 0;
        }
        if (request && complete) valid &= labels.equals(amounts.keySet());
        if (!request && !labels.containsAll(amounts.keySet())) findings.add(new Finding(rule, "unlisted-product-limit", Status.REVIEW_REQUIRED,
                "Observed product has no returned limit; eligibility/default limit requires configuration rather than an invented zero"));
        if ((!complete || labels.size() != table.substring(1, table.length() - 1).split("\\|", -1).length) && valid) findings.add(new Finding(rule, "fleet-product-correlation", Status.REVIEW_REQUIRED,
                "Incomplete or ambiguous product/limit observations cannot certify aggregate equality or all-purchase limit compliance"));
        else check(findings, rule, "fleet-product-correlation", valid,
                request ? "All products sharing a mapped category are summed; unmapped categories use MERC"
                        : "Observed sums within each returned category/product limit; unlisted-product eligibility is not invented");
    }

    private static String text(JsonNode node, String key) {
        JsonNode value = node.get(key);
        return value != null && value.isTextual() ? value.textValue() : null;
    }

    private static void check(List<Finding> findings, String rule, String scope, boolean valid, String reason) {
        findings.add(new Finding(rule, scope, valid ? Status.CHECKS_PASSED : Status.INVALID, reason));
    }
}
