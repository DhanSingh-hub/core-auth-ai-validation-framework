package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixAEIncrementalAuthorizationOracle;
import com.coreauth.validator.canonical.AppendixAEIncrementalAuthorizationOracle.Scenario;
import com.coreauth.validator.canonical.AppendixAEIncrementalAuthorizationOracle.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixAEIncrementalAuthorizationOracleTest {
    private final ObjectMapper json = new ObjectMapper();
    private final AppendixAEIncrementalAuthorizationOracle oracle = new AppendixAEIncrementalAuthorizationOracle();
    private JsonNode coverage() throws Exception {
        return json.readTree(Atl105Paths.testJson().resolve("appendices")
                .resolve("appendix-ae-segment-100-coverage.json").toFile());
    }
    private ObjectNode defaults() throws Exception {
        return coverage().path("fixtureDefaults").deepCopy();
    }
    private Status status(ObjectNode p, String rule) throws Exception {
        return oracle.assess(json.treeToValue(p, Scenario.class)).stream()
                .filter(a -> a.businessRequirementId().equals("BR-SEG100-APPAE-" + rule))
                .findFirst().orElseThrow().status();
    }

    @Test
    void executesAllPersistedFixturesAndChecksFullArtifactTraceability() throws Exception {
        JsonNode pack = coverage();
        var ids = pack.path("businessRequirements").findValuesAsText("id");
        assertThat(ids).hasSize(AppendixAEIncrementalAuthorizationOracle.RULES.size()).doesNotHaveDuplicates();
        for (String rule : AppendixAEIncrementalAuthorizationOracle.RULES) assertThat(ids).contains("BR-SEG100-APPAE-" + rule);
        for (JsonNode br : pack.path("businessRequirements")) {
            assertThat(br.path("sourceAnchor").path("version").asText()).isEqualTo("2026-3");
            assertThat(br.path("sourceAnchor").path("section").asText()).isNotBlank();
            assertThat(br.path("sourceAnchor").path("rule").asText()).isNotBlank();
        }
        for (JsonNode scenario : pack.path("testScenarios")) {
            for (JsonNode id : scenario.path("covers")) assertThat(ids).contains(id.asText());
        }
        var scenarios = pack.path("testScenarios").findValuesAsText("id");
        for (JsonNode tc : pack.path("testCases")) assertThat(scenarios).contains(tc.path("scenarioId").asText());
        assertThat(pack.path("testData").size()).isEqualTo(29)
                .isEqualTo(pack.path("testDataReadinessSummary").path("executable").asInt());
        assertThat(pack.path("testData").findValuesAsText("id")).doesNotHaveDuplicates();
        for (JsonNode fixture : pack.path("testData")) {
            ObjectNode p = defaults();
            p.setAll((ObjectNode) fixture.path("payload"));
            String id = fixture.path("coversBr").asText();
            assertThat(ids).contains(id);
            assertThat(fixture.path("expected").path("businessRequirementId").asText()).isEqualTo(id);
            assertThat(status(p, id.substring("BR-SEG100-APPAE-".length())).name()).as(fixture.path("id").asText())
                    .isEqualTo(fixture.path("expected").path("status").asText());
        }
    }

    @Test
    void enforcesSpecialMccRestrictionsWithoutInventingExhaustiveAllowlist() throws Exception {
        ObjectNode p = defaults();
        for (String mcc : new String[]{"4121", "5411"}) {
            p.put("mcc", mcc).put("cardNotPresent", false);
            assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.FAIL);
            p.putNull("cardNotPresent");
            assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.REVIEW_REQUIRED);
            p.put("cardNotPresent", true);
            assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.PASS);
        }
        p.put("mcc", "5552").put("cardNotPresent", false);
        assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.FAIL);
        p.put("evTransaction", true);
        assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.PASS);
        p.putNull("evTransaction");
        assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("mcc", "7011");
        assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.PASS);
        p.putNull("merchantPermitted");
        assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("merchantPermitted", false);
        assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.FAIL);
        p.put("merchantPermitted", true).put("brand", "MASTERCARD");
        assertThat(status(p, "MCC-SCOPE")).isEqualTo(Status.FAIL);
    }

    @Test
    void testsInitialEstimatedFlagAndIncrementalOmissionExactly() throws Exception {
        ObjectNode p = defaults();
        for (String flag : new String[]{"", "0", "11", "I"}) {
            p.put("estimatedIndicator", flag);
            assertThat(status(p, "INITIAL-INDICATOR")).isEqualTo(Status.FAIL);
        }
        p.putNull("estimatedIndicator");
        assertThat(status(p, "INITIAL-INDICATOR")).isEqualTo(Status.FAIL);
        p.put("estimatedIndicator", "1").putNull("finalAmountUnknown");
        assertThat(status(p, "INITIAL-INDICATOR")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("finalAmountUnknown", false);
        assertThat(status(p, "INITIAL-INDICATOR")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("stage", "INCREMENTAL");
        assertThat(status(p, "INITIAL-INDICATOR")).isEqualTo(Status.FAIL);
        p.put("estimatedIndicator", "");
        assertThat(status(p, "INITIAL-INDICATOR")).isEqualTo(Status.FAIL);
        p.putNull("estimatedIndicator");
        assertThat(status(p, "INITIAL-INDICATOR")).isEqualTo(Status.PASS);
    }

    @Test
    void checksAllThreeSeriesIdentifiersAndMissingEvidenceDoesNotHideMismatch() throws Exception {
        for (String field : new String[]{"sequenceNumber", "transactionIdentifier", "table063"}) {
            ObjectNode p = defaults();
            p.put("stage", "INCREMENTAL");
            p.put(field, field.equals("sequenceNumber") ? "123457"
                    : field.equals("transactionIdentifier") ? "123456789012346" : "0323050059P");
            p.putNull("precedingInitialAuthorization");
            assertThat(status(p, "INCREMENTAL-CONTINUITY")).as(field).isEqualTo(Status.FAIL);
        }
        ObjectNode p = defaults();
        p.put("stage", "INCREMENTAL").put("table063", "0324060058P");
        assertThat(status(p, "INCREMENTAL-CONTINUITY")).isEqualTo(Status.PASS);
        p.putNull("initialTransactionIdentifier");
        assertThat(status(p, "INCREMENTAL-CONTINUITY")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("sameMerchantAndCardholder", false);
        assertThat(status(p, "INCREMENTAL-CONTINUITY")).isEqualTo(Status.FAIL);
    }

    @Test
    void testsApprovalReuseAndHostSuppliedIncrementalIndicator() throws Exception {
        ObjectNode p = defaults();
        p.put("stage", "INCREMENTAL").put("approvalCode", "WRONG");
        assertThat(status(p, "HOST-INDICATOR")).isEqualTo(Status.FAIL);
        p.put("approvalCode", "ABC123").put("hostIncrementalIndicatorSent", false);
        assertThat(status(p, "HOST-INDICATOR")).isEqualTo(Status.FAIL);
        p.putNull("hostIncrementalIndicatorSent");
        assertThat(status(p, "HOST-INDICATOR")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("hostIncrementalIndicatorSent", true).putNull("originalApprovalCode");
        assertThat(status(p, "HOST-INDICATOR")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("approvalCode", " ");
        assertThat(status(p, "HOST-INDICATOR")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksTable063LodgingNonTravelAndEvLayoutsAndUniqueness() throws Exception {
        ObjectNode p = defaults();
        for (String value : new String[]{"0323050058P", "      0058 ", "0229050058P", "1231000058 ", "    050058P"}) {
            p.put("table063", value);
            assertThat(status(p, "LODGING-DATA")).isEqualTo(Status.PASS);
        }
        for (String value : new String[]{"0323050058", "0323050058PP", "0230050058P",
                "1301050058P", "0323AA0058P", "032305AB58P", "0323050058X"}) {
            p.put("table063", value);
            assertThat(status(p, "LODGING-DATA")).as(value).isEqualTo(Status.FAIL);
        }
        p.put("table063", "0323050058P").put("terminalNumberUnique", false);
        assertThat(status(p, "LODGING-DATA")).isEqualTo(Status.FAIL);
        p.putNull("terminalNumberUnique");
        assertThat(status(p, "LODGING-DATA")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("stage", "COMPLETION").put("table063", "0323050059P");
        assertThat(status(p, "LODGING-DATA")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksFinalAmountUsingNumericValueAndExplicitMissingEvidence() throws Exception {
        ObjectNode p = defaults();
        p.put("stage", "COMPLETION").put("completionAmount", "00015000");
        assertThat(status(p, "FINAL-AMOUNT")).isEqualTo(Status.PASS);
        p.put("completionAmount", "15001");
        assertThat(status(p, "FINAL-AMOUNT")).isEqualTo(Status.FAIL);
        p.put("completionAmount", "15000").putNull("finalAmount");
        assertThat(status(p, "FINAL-AMOUNT")).isEqualTo(Status.REVIEW_REQUIRED);
        for (String amount : new String[]{"", "-1", "150.00", "A"}) {
            p.put("completionAmount", amount);
            assertThat(status(p, "FINAL-AMOUNT")).isEqualTo(Status.FAIL);
        }
    }

    @Test
    void testsExactFractional7And14DayThresholdsFromInitialAcrossMerchantCategories() throws Exception {
        ObjectNode p = defaults();
        p.put("stage", "COMPLETION");
        for (String category : new String[]{"CRUISE", "LODGING", "VEHICLE_RENTAL", "OTHER_RENTAL"}) {
            p.put("category", category);
            int limit = category.equals("OTHER_RENTAL") ? 7 : 14;
            for (BigDecimal days : new BigDecimal[]{BigDecimal.valueOf(limit).subtract(new BigDecimal("0.001")),
                    BigDecimal.valueOf(limit), BigDecimal.valueOf(limit).add(new BigDecimal("0.001"))}) {
                p.put("elapsedDaysFromInitial", days);
                assertThat(status(p, "EXPIRATION-WINDOW")).as(category + "/" + days)
                        .isEqualTo(days.compareTo(BigDecimal.valueOf(limit)) <= 0 ? Status.PASS : Status.FAIL);
            }
        }
        p.put("category", "OTHER");
        assertThat(status(p, "EXPIRATION-WINDOW")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("category", "LODGING").put("elapsedDaysFromInitial", -1);
        assertThat(status(p, "EXPIRATION-WINDOW")).isEqualTo(Status.FAIL);
        p.putNull("elapsedDaysFromInitial");
        assertThat(status(p, "EXPIRATION-WINDOW")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void requiresBothRolloverEventsInOrderBeforeApplicableDeadline() throws Exception {
        ObjectNode p = defaults();
        p.put("stayExceedsWindow", true);
        for (String category : new String[]{"LODGING", "OTHER_RENTAL"}) {
            int limit = category.equals("OTHER_RENTAL") ? 7 : 14;
            p.put("category", category).put("completedAtDays", limit).put("nextInitialAtDays", limit);
            assertThat(status(p, "LONG-STAY-RESTART")).isEqualTo(Status.PASS);
            p.put("nextInitialAtDays", new BigDecimal(limit + ".001"));
            assertThat(status(p, "LONG-STAY-RESTART")).isEqualTo(Status.FAIL);
            p.put("nextInitialAtDays", limit - 1);
            assertThat(status(p, "LONG-STAY-RESTART")).isEqualTo(Status.FAIL);
            p.putNull("completedAtDays");
            assertThat(status(p, "LONG-STAY-RESTART")).isEqualTo(Status.REVIEW_REQUIRED);
            p.put("nextInitialAtDays", limit + 1);
            assertThat(status(p, "LONG-STAY-RESTART")).isEqualTo(Status.FAIL);
        }
    }

    @Test
    void coversSignatureDebitAssociatedTransactionRuleAndExternalBoundary() throws Exception {
        ObjectNode p = defaults();
        for (String stage : new String[]{"INCREMENTAL", "COMPLETION"}) {
            p.put("stage", stage).putNull("pinlessDebitAcceptance");
            assertThat(status(p, "SIGNATURE-DEBIT")).isEqualTo(Status.FAIL);
            p.put("pinlessDebitAcceptance", "X");
            assertThat(status(p, "SIGNATURE-DEBIT")).isEqualTo(Status.PASS);
            p.putNull("initialSettlementType");
            assertThat(status(p, "SIGNATURE-DEBIT")).isEqualTo(Status.REVIEW_REQUIRED);
            p.put("initialSettlementType", "X");
            assertThat(status(p, "EXTERNAL-EVIDENCE")).isEqualTo(Status.REVIEW_REQUIRED);
        }
    }
}
