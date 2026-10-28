package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/** Validates the published Appendix B example without promoting its tax-total conflict. */
public final class AppendixBExampleFixtureValidator {
    private static final String SOURCE = "AppendixBExample";

    public ValidationResult validate(JsonNode payload) {
        ValidationResult result = new Segment100AiJsonCompatibilityValidator().validatePayload(payload);
        JsonNode request = payload == null ? null : payload.path("Financial Request");
        if (request == null || !request.isObject()) {
            result.addError(SOURCE, "Financial Request object is required");
            return result;
        }

        checkEquals(request, "NumSegments", "03", "Financial Request", result);
        JsonNode standard = request.path("Standard Segment");
        JsonNode products = request.path("Product Code Segment");
        JsonNode variable = request.path("Variable Info Segment");
        checkEquals(products, "SegmentType", "102", "Product Code Segment", result);
        checkEquals(products, "SegmentLength", "049", "Product Code Segment", result);
        checkEquals(standard, "SegmentLength", "078", "Standard Segment", result);
        checkEquals(variable, "SegmentType", "111", "Variable Info Segment", result);
        checkEquals(variable, "SegmentLength", "054", "Variable Info Segment", result);
        checkEquals(standard.path("PromptCode"), "TransactionType", "0", "PromptCode", result);
        checkEquals(standard.path("PromptCode"), "CardType", "020", "PromptCode", result);
        checkEquals(standard, "PartialApprovalIndicator", "1", "PartialApprovalIndicator", result);
        checkEquals(standard, "FuelPurchaseAmount", "500", "FuelPurchaseAmount", result);
        checkEquals(standard, "NonfuelAmount", "1790", "NonfuelAmount", result);
        checkEquals(standard, "TaxAmount", "97", "TaxAmount", result);
        checkEquals(standard, "CashAmount", "", "CashAmount", result);
        checkEquals(products, "ServiceLevel", "S", "ServiceLevel", result);
        checkEquals(products, "NumberOfProducts", "02", "NumberOfProducts", result);

        JsonNode entries = products.path("products");
        if (!entries.isArray() || entries.size() != 2) {
            result.addError(SOURCE, "Appendix B example must contain its two documented product entries");
            return result;
        }
        List<String> productCodes = new ArrayList<>();
        BigInteger productTotal = BigInteger.ZERO;
        for (JsonNode entry : entries) {
            productCodes.add(entry.path("productCode").asText(""));
            String amount = entry.path("productAmount").asText(null);
            if (amount == null || !amount.matches("[0-9]+")) {
                result.addError(SOURCE, "Each Appendix B product amount must contain digits only");
            } else {
                productTotal = productTotal.add(new BigInteger(amount));
            }
        }
        if (!List.of("001", "411").equals(productCodes)) {
            result.addError(SOURCE, "Appendix B example product order must be fuel 001 followed by cigarettes 411");
        }
        checkProduct(entries.get(0), "001", "G", "34170", "31199", "500", 1, result);
        checkProduct(entries.get(1), "411", "U", "010", "2179", "1790", 2, result);

        JsonNode additionalInformation = variable.path("AdditionalInformation");
        if (!additionalInformation.isArray() || additionalInformation.size() != 2) {
            result.addError(SOURCE, "Appendix B example must preserve both documented Variable Information triads");
        } else {
            checkEquals(additionalInformation.get(0), "indicator", "004", "Variable Information triad 1", result);
            checkEquals(additionalInformation.get(0), "length", "003", "Variable Information triad 1", result);
            checkEquals(additionalInformation.get(0), "value", "969", "Variable Information triad 1", result);
            checkEquals(additionalInformation.get(1), "indicator", "010", "Variable Information triad 2", result);
            checkEquals(additionalInformation.get(1), "length", "030", "Variable Information triad 2", result);
            String value = additionalInformation.get(1).path("value").asText("");
            if (value.length() != 30 || !value.endsWith("VAR001")) {
                result.addError(SOURCE, "Appendix B second Variable Information value must be 30 characters and end with VAR001");
            }
        }

        BigInteger sectionTotal = amount(standard, "FuelPurchaseAmount", result)
                .add(amount(standard, "NonfuelAmount", result))
                .add(amount(standard, "TaxAmount", result))
                .add(amount(standard, "CashAmount", result));
        if (!new BigInteger("2290").equals(productTotal)
                || !new BigInteger("2387").equals(sectionTotal)) {
            result.addError(SOURCE, "Appendix B example values changed; expected product total 2290 and Segment 100 amount sum 2387");
        }
        if (!productTotal.equals(sectionTotal)) {
            result.addWarning("REVIEW_REQUIRED (SEG102-SME-006): Appendix B product amounts total "
                    + productTotal + " but Section 12.3's Segment 100 aggregate totals " + sectionTotal
                    + "; the example's 97 tax amount is separate from its product amounts.");
        }
        return result;
    }

    private static void checkProduct(JsonNode product, String code, String unit, String quantity,
            String unitPrice, String productAmount, int number, ValidationResult result) {
        String label = "Appendix B product " + number;
        checkEquals(product, "productCode", code, label, result);
        checkEquals(product, "unitOfMeasure", unit, label, result);
        checkEquals(product, "quantity", quantity, label, result);
        checkEquals(product, "unitPrice", unitPrice, label, result);
        checkEquals(product, "productAmount", productAmount, label, result);
    }

    private static BigInteger amount(JsonNode node, String field, ValidationResult result) {
        String value = node.path(field).asText(null);
        if (value == null || value.isEmpty()) return BigInteger.ZERO;
        if (!value.matches("[0-9]+")) {
            result.addError(SOURCE, field + " must contain digits only in the Appendix B example");
            return BigInteger.ZERO;
        }
        return new BigInteger(value);
    }

    private static void checkEquals(JsonNode node, String field, String expected, String label,
            ValidationResult result) {
        String actual = node == null ? null : node.path(field).asText(null);
        if (!expected.equals(actual)) {
            result.addError(SOURCE, label + "." + field + " must be " + expected + ", found " + actual);
        }
    }
}