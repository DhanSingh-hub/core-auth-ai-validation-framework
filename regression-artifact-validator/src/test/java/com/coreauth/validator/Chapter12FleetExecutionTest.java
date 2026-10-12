package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12FleetExecutionTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Chapter12SegmentPayloadValidator validator = new Chapter12SegmentPayloadValidator();

    private ObjectNode observation(String id, String table, String value, String authorizer) {
        var input = mapper.createObjectNode().put("specificationVersion", "2026-3").put("segment", id)
                .put("direction", "145".equals(id) ? "REQUEST" : "RESPONSE");
        input.putObject("elements").put("85", id).put("84", String.format(java.util.Locale.ROOT, "%03d", 12 + value.length()));
        input.putArray("tables").addObject().put("id", table)
                .put("length", String.format(java.util.Locale.ROOT, "%03d", value.length())).put("value", value);
        input.putObject("context").put("authorizer", authorizer).putArray("segmentsPresent").add("100").add(id);
        return input;
    }

    private void target(ObjectNode input, String scope, Status status) throws Exception {
        assertThat(validator.validate(input).findings()).filteredOn(f -> f.scope().equals(scope))
                .singleElement().satisfies(f -> assertThat(f.status()).isEqualTo(status));
    }

    @Test void appliesStringNumericExactAndPatternMasksAtBothBoundaries() throws Exception {
        for (var pair : Map.of("N;TS;M1;X6", List.of("1", "123456"), "TN;M123;X456", List.of("123", "456"),
                "V1100", List.of("1100"), "O;PUN####", List.of("UN1234"), "A", List.of("Ab12"), "B", List.of("Ab12"),
                "I", List.of("hello!"), "P@#*", List.of("A2b")).entrySet()) {
            for (String value : pair.getValue()) {
                var input = observation("146", "004", "|DRID:" + pair.getKey() + "|", "COMDATA");
                input.putObject("history").putObject("fleet").putObject("capturedResponses").put("DRID", value);
                target(input, "mask-value", Status.CHECKS_PASSED);
                assertThat(validator.validate(input).findings()).noneMatch(f -> f.status() == Status.INVALID);
            }
        }
        for (var pair : Map.of("N;TS;M2;X4", List.of("1", "12345", "abcd"), "TN;M123;X456", List.of("122", "457", "abc"),
                "V1100", List.of("1101"), "PUN####", List.of("UN12x4", "UN12345"), "A", List.of("A!"), "B", List.of("A!"),
                "P@#*", List.of("2Ab")).entrySet()) {
            for (String value : pair.getValue()) {
                var input = observation("146", "004", "|DRID:" + pair.getKey() + "|", "COMDATA");
                input.putObject("history").putObject("fleet").putObject("capturedResponses").put("DRID", value);
                target(input, "mask-value", Status.INVALID);
            }
        }
    }

    @Test void optionalMissingResponseIsNotARequiredFailureAndZCannotBeResent() throws Exception {
        var input = observation("146", "004", "|UNIT:O;PUN####|", "COMDATA");
        input.putObject("history").putObject("fleet").putObject("capturedResponses");
        target(input, "prompt-requiredness", Status.CHECKS_PASSED);
        input = observation("146", "004", "|UNIT:PUN####|", "COMDATA");
        input.putObject("history").putObject("fleet").putObject("capturedResponses");
        target(input, "prompt-requiredness", Status.INVALID);
        input = observation("146", "004", "|UNIT:Z;PUN####|", "COMDATA");
        var history = input.putObject("history").putObject("fleet");
        history.putObject("capturedResponses").put("UNIT", "UN1234");
        target(input, "capture-without-resend", Status.REVIEW_REQUIRED);
        history.putObject("subsequentResponses").put("DRID", "100");
        target(input, "capture-without-resend", Status.CHECKS_PASSED);
        ((ObjectNode) history.path("subsequentResponses")).put("UNIT", "UN1234");
        target(input, "capture-without-resend", Status.INVALID);
        history.putArray("subsequentResponses");
        target(input, "capture-without-resend", Status.INVALID);
        input = observation("146", "004", "|UNIT:O;Z|", "COMDATA");
        history = input.putObject("history").putObject("fleet");
        history.putObject("capturedResponses");
        history.putObject("subsequentResponses").put("UNIT", "UN1234");
        target(input, "capture-without-resend", Status.INVALID);
    }

    @Test void repromptUsesObservedPriorFormatAndDoesNotGuessMissingFormatOrBoundType() throws Exception {
        var input = observation("146", "004", "|DRID:?|", "COMDATA");
        var history = input.putObject("history").putObject("fleet");
        history.putObject("capturedResponses").put("DRID", "1100");
        target(input, "reprompt-format", Status.REVIEW_REQUIRED);
        history.putObject("priorPromptFormats").put("DRID", "V1100");
        target(input, "mask-value", Status.CHECKS_PASSED);
        ((ObjectNode) history.path("capturedResponses")).put("DRID", "1101");
        target(input, "mask-value", Status.INVALID);
        ((ObjectNode) history.path("priorPromptFormats")).put("DRID", "?");
        target(input, "reprompt-format", Status.INVALID);
        input = observation("146", "004", "|DRID:M1|", "COMDATA");
        input.putObject("history").putObject("fleet").putObject("capturedResponses").put("DRID", "11");
        target(input, "bound-type", Status.REVIEW_REQUIRED);
        target(input, "mask-value", Status.REVIEW_REQUIRED);
        input = observation("146", "004", "|DRID:TN;TS|", "COMDATA");
        input.putObject("history").putObject("fleet").putObject("capturedResponses").put("DRID", "11");
        target(input, "mask-value", Status.INVALID);
    }

    @Test void requestUsesActualSentResponsesAndMatchedPriorResponseFormats() throws Exception {
        var input = observation("145", "004", "|DRID:1100|", "COMDATA");
        var history = input.putObject("history").putObject("fleet");
        history.putObject("promptFormats").put("DRID", "V1100");
        history.putObject("capturedResponses").put("DRID", "IGNORED");
        target(input, "mask-value", Status.CHECKS_PASSED);
        ((ObjectNode) history.path("promptFormats")).put("DRID", "V1101");
        target(input, "mask-value", Status.INVALID);
        history.putArray("promptFormats");
        target(input, "prompt-history", Status.INVALID);
        input.putArray("history");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test void enumeratesAllAuthorizersIncludingVoyagerFutureTagsAndVisaSharedFieldRestriction() throws Exception {
        for (var pair : Map.of("VOYAGER_EMV", "DF32", "VISA_FLEET_2", "AD1", "COMDATA", "DRID",
                "WEX_OTR", "ODRD", "MASTERCARD_ENHANCED_FLEET_EMV", "001").entrySet()) {
            var input = observation("145", "004", "|" + pair.getValue() + ":1|", pair.getKey());
            target(input, "authorizer-prompt", Status.CHECKS_PASSED);
            input = observation("145", "004", "|ZZZZ:1|", pair.getKey());
            target(input, "authorizer-prompt", Status.INVALID);
        }
        for (String tag : List.of("DF31", "DF70")) target(observation("145", "004", "|" + tag + ":1|", "VOYAGER_EMV"),
                "authorizer-prompt", Status.INVALID);
        target(observation("145", "004", "|DF6F:1|", "VOYAGER_EMV"), "authorizer-prompt", Status.CHECKS_PASSED);
        target(observation("145", "004", "|GEN:1|VHI:2|", "VISA_FLEET_2"), "visa-shared-prompt", Status.INVALID);
        var longValue = observation("145", "004", "|XPRS:" + "1".repeat(25) + "|", "COMDATA");
        target(longValue, "prompt-cap-conflict", Status.REVIEW_REQUIRED);
        assertThat(validator.validate(longValue).findings()).noneMatch(f -> f.status() == Status.INVALID);
        target(observation("145", "004", "|DLST:123|", "COMDATA"), "prompt-value-cap", Status.INVALID);
        target(observation("145", "004", "|ZZZZ:1|", "WEX"), "authorizer-prompt", Status.REVIEW_REQUIRED);
        assertThat(validator.validate(observation("145", "002", "|XXXX:1,1.00|", "WEX")).findings())
                .anyMatch(f -> f.scope().equals("category-configuration") && f.status() == Status.REVIEW_REQUIRED)
                .noneMatch(f -> f.scope().equals("wex-category") && f.status() == Status.INVALID);
    }

    @Test void productAndAdditionalResponseSublayoutsRejectMalformedContent() throws Exception {
        for (String value : List.of("|SCLE:1,10.00|", "|MERC:99,999.00|")) {
            assertThat(validator.validate(observation("145", "002", value, "WEX_OTR")).findings()).noneMatch(f -> f.status() == Status.INVALID);
        }
        for (String value : List.of("|SCLE:1,10|", "|SCLE:1000,10.00|", "|SCLE:1,10.00|SCLE:2,20.00|", "|XXXX:1,10.00|")) {
            assertThat(validator.validate(observation("145", "002", value, "WEX_OTR")).status()).isEqualTo(Status.INVALID);
        }
        assertThat(validator.validate(observation("146", "003", "|001:100.00|", "COMDATA")).findings()).noneMatch(f -> f.status() == Status.INVALID);
        assertThat(validator.validate(observation("146", "003", "|500:100.00|", "COMDATA")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(observation("146", "010", "|FNAM:JOHN|DMSG:Hello there|", "COMDATA")).findings()).noneMatch(f -> f.status() == Status.INVALID);
        assertThat(validator.validate(observation("146", "010", "|ZZZZ:Hello|", "COMDATA")).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(observation("146", "005", "A".repeat(42), "COMDATA")).findings()).noneMatch(f -> f.status() == Status.INVALID);
        assertThat(validator.validate(observation("146", "005", "A".repeat(41), "COMDATA")).status()).isEqualTo(Status.INVALID);
    }

    @Test void reconcilesGroupedCategoriesAndMERCWithCompleteEvidenceAndExecutesProductLimits() throws Exception {
        var input = observation("145", "002", "|SCLE:3,30.00|MERC:1,5.00|", "WEX_OTR");
        var history = input.putObject("history").putObject("fleet").put("nonfuelProductsComplete", true);
        var products = history.putArray("nonfuelProducts");
        products.addObject().put("category", "SCLE").put("quantity", "1").put("amount", "10.00");
        products.addObject().put("category", "SCLE").put("quantity", "2").put("amount", "20.00");
        products.addObject().put("quantity", "1").put("amount", "5.00");
        target(input, "fleet-product-correlation", Status.CHECKS_PASSED);
        ((ObjectNode) products.get(1)).put("amount", "21.00");
        target(input, "fleet-product-correlation", Status.INVALID);
        history.put("nonfuelProductsComplete", false);
        ((ObjectNode) products.get(1)).put("amount", "10.00");
        target(input, "fleet-product-correlation", Status.REVIEW_REQUIRED);
        ((ObjectNode) products.get(1)).put("amount", "21.00");
        target(input, "fleet-product-correlation", Status.INVALID);
        input = observation("146", "003", "|001:100.00|", "COMDATA");
        history = input.putObject("history").putObject("fleet").put("fuelProductsComplete", true);
        var product = history.putArray("fuelProducts").addObject().put("productCode", "001").put("amount", "100.00");
        target(input, "fleet-product-correlation", Status.CHECKS_PASSED);
        product.put("amount", "100.01");
        target(input, "fleet-product-correlation", Status.INVALID);
        product.put("amount", 100);
        target(input, "fleet-product-correlation", Status.INVALID);
    }

    @Test void envelopeSuppliesAuthoritativeFleetHistoryAndCannotBeOverriddenByTheChild() throws Exception {
        var child = observation("145", "004", "|DRID:1100|", "COMDATA");
        var parent = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "Financial Transaction Request").put("completeness", "PARTIAL");
        parent.putObject("elements").put("55", "ATL105").put("63", "01");
        parent.putObject("context").put("authorizer", "COMDATA");
        parent.putArray("segments").addObject().put("segment", "145").put("dataSection", 3).put("fieldNumber", 4).set("observation", child);
        var formats = parent.putObject("history").putObject("fleet").putObject("promptFormats").put("DRID", "V1100");
        var envelopeValidator = new com.coreauth.validator.canonical.Chapter11MessageLayoutValidator();
        var original = parent.deepCopy();
        assertThat(envelopeValidator.validate(parent).findings()).anyMatch(f -> f.scope().equals("mask-value") && f.status() == Status.CHECKS_PASSED);
        assertThat(parent).isEqualTo(original);
        child.putObject("history").putObject("fleet").putObject("promptFormats").put("DRID", "V1100");
        formats.put("DRID", "V1101");
        original = parent.deepCopy();
        assertThat(envelopeValidator.validate(parent).findings())
                .anyMatch(f -> f.scope().equals("segment-context") && f.status() == Status.INVALID)
                .anyMatch(f -> f.scope().equals("mask-value") && f.status() == Status.INVALID);
        assertThat(parent).isEqualTo(original);
    }
}
