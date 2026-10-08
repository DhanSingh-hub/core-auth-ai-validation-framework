package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixABPurchaseRepaymentOracle;
import com.coreauth.validator.canonical.AppendixABPurchaseRepaymentOracle.Scenario;
import com.coreauth.validator.canonical.AppendixABPurchaseRepaymentOracle.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixABPurchaseRepaymentOracleTest {
    private final ObjectMapper json = new ObjectMapper();
    private final AppendixABPurchaseRepaymentOracle oracle = new AppendixABPurchaseRepaymentOracle();

    private JsonNode coverage() throws Exception {
        return json.readTree(Atl105Paths.testJson().resolve("appendices")
                .resolve("appendix-ab-segment-100-coverage.json").toFile());
    }
    private ObjectNode defaults() throws Exception {
        return coverage().path("fixtureDefaults").deepCopy();
    }
    private Status status(ObjectNode payload, String rule) throws Exception {
        return oracle.assess(json.treeToValue(payload, Scenario.class)).stream()
                .filter(a -> a.businessRequirementId().equals("BR-SEG100-APPAB-" + rule))
                .findFirst().orElseThrow().status();
    }

    @Test
    void executesEveryPersistedFixtureAndChecksCompleteTraceability() throws Exception {
        JsonNode pack = coverage();
        var ids = pack.path("businessRequirements").findValuesAsText("id");
        assertThat(ids).hasSize(AppendixABPurchaseRepaymentOracle.RULES.size()).doesNotHaveDuplicates();
        for (String rule : AppendixABPurchaseRepaymentOracle.RULES) {
            assertThat(ids).contains("BR-SEG100-APPAB-" + rule);
        }
        for (JsonNode br : pack.path("businessRequirements")) {
            assertThat(br.path("sourceAnchor").path("specification").asText()).isEqualTo("ATL105");
            assertThat(br.path("sourceAnchor").path("version").asText()).isEqualTo("2026-3");
            assertThat(br.path("sourceAnchor").path("section").asText()).isNotBlank();
            assertThat(br.path("sourceAnchor").path("rule").asText()).isNotBlank();
        }
        var scenarioIds = pack.path("testScenarios").findValuesAsText("id");
        for (JsonNode scenario : pack.path("testScenarios")) {
            for (JsonNode id : scenario.path("covers")) assertThat(ids).contains(id.asText());
        }
        for (JsonNode test : pack.path("testCases")) {
            assertThat(scenarioIds).contains(test.path("scenarioId").asText());
        }
        assertThat(pack.path("testData").size()).isEqualTo(25)
                .isEqualTo(pack.path("testDataReadinessSummary").path("executable").asInt());
        for (JsonNode fixture : pack.path("testData")) {
            ObjectNode payload = defaults();
            payload.setAll((ObjectNode) fixture.path("payload"));
            String id = fixture.path("coversBr").asText();
            assertThat(ids).contains(id);
            assertThat(fixture.path("expected").path("businessRequirementId").asText()).isEqualTo(id);
            assertThat(fixture.path("status").asText()).isEqualTo("EXECUTABLE");
            assertThat(status(payload, id.substring("BR-SEG100-APPAB-".length())).name())
                    .as(fixture.path("id").asText()).isEqualTo(fixture.path("expected").path("status").asText());
        }
    }

    @Test
    void checksDefinitionForThirdPartyAndFacilitatorWithoutInventingEligibility() throws Exception {
        ObjectNode p = defaults();
        p.put("thirdPartyProvider", false);
        assertThat(status(p, "DEFINITION")).isEqualTo(Status.FAIL);
        p.putNull("thirdPartyProvider");
        assertThat(status(p, "DEFINITION")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("paymentFacilitator", true);
        assertThat(status(p, "DEFINITION")).isEqualTo(Status.PASS);
        p.putNull("installment");
        assertThat(status(p, "DEFINITION")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("installment", false);
        assertThat(status(p, "DEFINITION")).isEqualTo(Status.FAIL);
        p.put("purchaseRepayment", false);
        assertThat(status(p, "DEFINITION")).isEqualTo(Status.PASS);
    }

    @Test
    void testsEveryBrandAgainstBothBusinessPaymentValues() throws Exception {
        ObjectNode p = defaults();
        p.put("purchaseRepayment", false);
        for (var brand : AppendixABPurchaseRepaymentOracle.Brand.values()) {
            p.put("brand", brand.name());
            for (String value : new String[]{"CB", "IR"}) {
                p.put("businessPaymentType", value);
                boolean allowed = "CB".equals(value) ? brand == AppendixABPurchaseRepaymentOracle.Brand.VISA
                        : brand == AppendixABPurchaseRepaymentOracle.Brand.MASTERCARD;
                assertThat(status(p, "VALUE-SCOPE")).isEqualTo(allowed ? Status.PASS : Status.FAIL);
            }
        }
        p = defaults();
        p.putNull("businessPaymentType");
        assertThat(status(p, "VALUE-SCOPE")).isEqualTo(Status.FAIL);
        p.put("businessPaymentType", "XX");
        assertThat(status(p, "VALUE-SCOPE")).isEqualTo(Status.FAIL);
        for (String brand : new String[]{"VISA", "DISCOVER", "AMEX", "OTHER"}) {
            p.put("brand", brand);
            p.putNull("businessPaymentType");
            assertThat(status(p, "VALUE-SCOPE")).isEqualTo(Status.REVIEW_REQUIRED);
            assertThat(status(p, "MASTERCARD-INSTALLMENT-INDICATOR")).isEqualTo(Status.PASS);
        }
    }

    @Test
    void requiresExactMastercardInstallmentFlag() throws Exception {
        ObjectNode p = defaults();
        for (String value : new String[]{"", "R", "i", "II"}) {
            p.put("installmentIndicator", value);
            assertThat(status(p, "MASTERCARD-INSTALLMENT-INDICATOR")).isEqualTo(Status.FAIL);
        }
        p.putNull("installmentIndicator");
        assertThat(status(p, "MASTERCARD-INSTALLMENT-INDICATOR")).isEqualTo(Status.FAIL);
        p.put("installmentIndicator", "I");
        assertThat(status(p, "MASTERCARD-INSTALLMENT-INDICATOR")).isEqualTo(Status.PASS);
    }

    @Test
    void verifiesDescriptorAtExactByteBoundaryAndIdentityOrder() throws Exception {
        ObjectNode p = defaults();
        p.put("providerName", "XYZ");
        for (int width : new int[]{37, 38, 39}) {
            String retailer = "A".repeat(width - 4);
            p.put("retailerName", retailer);
            p.put("merchantName", "XYZ*" + retailer);
            assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(width > 38 ? Status.FAIL : Status.PASS);
        }
        for (String value : new String[]{"", "   ", "*SHOP", "XYZ*", "XYZ*  ", "  *SHOP", "XYZSHOP", "XYZ*\u001cSHOP"}) {
            p.put("merchantName", value);
            assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(Status.FAIL);
        }
        p.putNull("merchantName");
        assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(Status.FAIL);
        p.put("merchantName", "XYZ*CAF\u00e9");
        assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void missingIdentityEvidenceDoesNotMaskDefiniteDescriptorMismatch() throws Exception {
        ObjectNode p = defaults();
        p.putNull("retailerName");
        p.put("providerName", "OTHER PROVIDER");
        assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(Status.FAIL);
        p.put("providerName", "XYZ");
        assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(Status.REVIEW_REQUIRED);
        p.putNull("providerName");
        p.put("retailerName", "OTHER RETAILER");
        assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(Status.FAIL);
        p.put("retailerName", "A SMALL CO");
        assertThat(status(p, "MASTERCARD-MERCHANT-NAME")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void checksAdviceAcceptanceNetworkAndTwoByteRepresentation() throws Exception {
        ObjectNode p = defaults();
        for (String value : new String[]{"N", "y", "", "YY"}) {
            p.put("adviceAcceptance", value);
            assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.FAIL);
        }
        p.put("adviceAcceptance", "Y");
        for (String value : new String[]{"2", "222", " 2", "\u00e922"}) {
            p.put("merchantAdviceCode", value);
            assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.FAIL);
        }
        p.put("merchantAdviceCode", "22");
        for (String brand : new String[]{"VISA", "DISCOVER", "AMEX", "OTHER"}) {
            p.put("brand", brand);
            assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.FAIL);
        }
    }

    @Test
    void evaluatesConditionalEnrollmentDeclineAndOptInWithoutReverseInference() throws Exception {
        ObjectNode p = defaults();
        p.put("merchantAdviceCode", "03");
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.FAIL);
        p.put("programRegistered", true);
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("merchantAdviceCode", "22");
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.REVIEW_REQUIRED);
        p.putNull("merchantAdviceCode");
        p.putNull("declined");
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("declined", false);
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.PASS);
        p.put("responseObserved", false);
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("merchantAdviceCode", "22");
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.FAIL);
    }

    @Test
    void forbidsRetryForAllThreeStopCodesAndReviewsFutureCodes() throws Exception {
        ObjectNode p = defaults();
        for (String code : new String[]{"03", "21", "22"}) {
            p.put("merchantAdviceCode", code);
            p.put("retryAttempted", false);
            assertThat(status(p, "NO-RETRY")).isEqualTo(Status.PASS);
            p.put("retryAttempted", true);
            assertThat(status(p, "NO-RETRY")).isEqualTo(Status.FAIL);
            p.putNull("retryAttempted");
            assertThat(status(p, "NO-RETRY")).isEqualTo(Status.REVIEW_REQUIRED);
        }
        p.put("merchantAdviceCode", "ZZ");
        p.put("programRegistered", true);
        assertThat(status(p, "ADVICE-CODE")).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(p, "NO-RETRY")).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(p, "EXTERNAL-EVIDENCE")).isEqualTo(Status.REVIEW_REQUIRED);
    }
}
