package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixBExampleFixtureValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixBCompositePurchaseTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path PACKAGE = Path.of("specifications", "ATL105", "test-output", "test-json",
            "appendices", "appendix-b-segment-100-coverage.json");

    @Test
    void validatesAppendixBCompositeAndKeepsTaxDiscrepancyReviewRequired() throws Exception {
        JsonNode testData = MAPPER.readTree(PACKAGE.toFile()).path("testData").get(0);
        JsonNode payload = testData.path("payload");

        var result = new AppendixBExampleFixtureValidator().validate(payload);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("SEG102-SME-006"));
        assertThat(testData.path("expectedValidation").asText()).isEqualTo("REVIEW_REQUIRED");
    }

    @Test
    void rejectsAppendixBFixtureWhenProductOrderIsChanged() throws Exception {
        JsonNode testData = MAPPER.readTree(PACKAGE.toFile()).path("testData").get(0);
        JsonNode payload = testData.path("payload").deepCopy();
        var products = (com.fasterxml.jackson.databind.node.ArrayNode) payload.path("Financial Request")
                .path("Product Code Segment").path("products");
        JsonNode first = products.get(0).deepCopy();
        products.set(0, products.get(1).deepCopy());
        products.set(1, first);

        var result = new AppendixBExampleFixtureValidator().validate(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("product order"));
    }

    @Test
    void rejectsAppendixBFixtureWhenIndividualProductAmountsAreChanged() throws Exception {
        JsonNode testData = MAPPER.readTree(PACKAGE.toFile()).path("testData").get(0);
        JsonNode payload = testData.path("payload").deepCopy();
        var products = (com.fasterxml.jackson.databind.node.ArrayNode) payload.path("Financial Request")
                .path("Product Code Segment").path("products");
        ((com.fasterxml.jackson.databind.node.ObjectNode) products.get(0)).put("productAmount", "501");
        ((com.fasterxml.jackson.databind.node.ObjectNode) products.get(1)).put("productAmount", "1789");

        var result = new AppendixBExampleFixtureValidator().validate(payload);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("product 1.productAmount"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("product 2.productAmount"));
    }
}