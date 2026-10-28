package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixADAccountUpdaterOracle;
import com.coreauth.validator.canonical.AppendixADAccountUpdaterOracle.Scenario;
import com.coreauth.validator.canonical.AppendixADAccountUpdaterOracle.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.util.Map;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixADAccountUpdaterOracleTest {
    private final ObjectMapper json = new ObjectMapper();
    private final AppendixADAccountUpdaterOracle oracle = new AppendixADAccountUpdaterOracle();

    private JsonNode coverage() throws Exception {
        return json.readTree(Atl105Paths.testJson().resolve("appendices")
                .resolve("appendix-ad-segment-100-coverage.json").toFile());
    }
    private ObjectNode defaults() throws Exception {
        return coverage().path("fixtureDefaults").deepCopy();
    }
    private Status status(ObjectNode p, String rule) throws Exception {
        return oracle.assess(json.treeToValue(p, Scenario.class)).stream()
                .filter(a -> a.businessRequirementId().equals("BR-SEG100-APPAD-" + rule))
                .findFirst().orElseThrow().status();
    }
    private void request(ObjectNode p, Map<String, String> fields) {
        p.set("requestData", json.valueToTree(fields));
    }
    private void response(ObjectNode p, Map<String, String> fields) {
        p.set("responseData", json.valueToTree(fields));
    }
    private void credential(ObjectNode p, String field, String account, String expiry) {
        p.putObject(field).put("account", account).put("expirationMmyy", expiry);
    }

    @Test
    void executesEveryFixtureAndChecksRequirementScenarioAndDataReferences() throws Exception {
        JsonNode pack = coverage();
        var ids = pack.path("businessRequirements").findValuesAsText("id");
        assertThat(ids).hasSize(AppendixADAccountUpdaterOracle.RULES.size()).doesNotHaveDuplicates();
        for (String rule : AppendixADAccountUpdaterOracle.RULES) assertThat(ids).contains("BR-SEG100-APPAD-" + rule);
        for (JsonNode br : pack.path("businessRequirements")) {
            assertThat(br.path("sourceAnchor").path("specification").asText()).isEqualTo("ATL105");
            assertThat(br.path("sourceAnchor").path("version").asText()).isEqualTo("2026-3");
            assertThat(br.path("sourceAnchor").path("section").asText()).isNotBlank();
            assertThat(br.path("sourceAnchor").path("rule").asText()).isNotBlank();
        }
        for (JsonNode s : pack.path("testScenarios")) {
            for (JsonNode id : s.path("covers")) assertThat(ids).contains(id.asText());
        }
        var scenarioIds = pack.path("testScenarios").findValuesAsText("id");
        for (JsonNode tc : pack.path("testCases")) assertThat(scenarioIds).contains(tc.path("scenarioId").asText());
        assertThat(pack.path("testData").size()).isEqualTo(44)
                .isEqualTo(pack.path("testDataReadinessSummary").path("executable").asInt());
        assertThat(pack.path("testData").findValuesAsText("id")).doesNotHaveDuplicates();
        for (JsonNode fixture : pack.path("testData")) {
            ObjectNode p = defaults();
            p.setAll((ObjectNode) fixture.path("payload"));
            String id = fixture.path("coversBr").asText();
            assertThat(ids).contains(id);
            assertThat(fixture.path("expected").path("businessRequirementId").asText()).isEqualTo(id);
            assertThat(fixture.path("status").asText()).isEqualTo("EXECUTABLE");
            assertThat(status(p, id.substring("BR-SEG100-APPAD-".length())).name())
                    .as(fixture.path("id").asText()).isEqualTo(fixture.path("expected").path("status").asText());
        }
    }

    @Test
    void checksEveryParticipationEvidenceFlagAndAllThreeQualifyingContexts() throws Exception {
        for (String flag : new String[]{"cardNotPresent", "acquirerParticipating", "merchantRegistered",
                "merchantCertified", "securelyStored"}) {
            ObjectNode p = defaults();
            p.put(flag, false);
            assertThat(status(p, "PARTICIPATION-SCOPE")).as(flag).isEqualTo(Status.FAIL);
            p.putNull(flag);
            assertThat(status(p, "PARTICIPATION-SCOPE")).as(flag).isEqualTo(Status.REVIEW_REQUIRED);
        }
        for (String context : new String[]{"credentialOnFile", "installment", "recurring"}) {
            ObjectNode p = defaults();
            p.put("credentialOnFile", false).put("installment", false).put("recurring", false);
            assertThat(status(p, "PARTICIPATION-SCOPE")).isEqualTo(Status.FAIL);
            p.put(context, true);
            assertThat(status(p, "PARTICIPATION-SCOPE")).isEqualTo(Status.PASS);
            assertThat(status(p, "TRIGGER-INDICATOR")).isEqualTo(Status.PASS);
            request(p, Map.of());
            assertThat(status(p, "TRIGGER-INDICATOR")).isEqualTo(Status.FAIL);
        }
        ObjectNode p = defaults();
        p.putNull("merchantCertified").put("securelyStored", false);
        assertThat(status(p, "PARTICIPATION-SCOPE")).isEqualTo(Status.FAIL);
        p.put("securelyStored", true).putNull("credentialOnFile");
        assertThat(status(p, "PARTICIPATION-SCOPE")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void validatesIndicatorNetworkAndOperationMatrixAndExactValues() throws Exception {
        ObjectNode p = defaults();
        for (var brand : AppendixADAccountUpdaterOracle.Brand.values()) {
            p.put("brand", brand.name());
            for (var operation : AppendixADAccountUpdaterOracle.Operation.values()) {
                p.put("operation", operation.name());
                boolean allowed = brand != AppendixADAccountUpdaterOracle.Brand.OTHER
                        && (operation == AppendixADAccountUpdaterOracle.Operation.AUTH
                        || operation == AppendixADAccountUpdaterOracle.Operation.SALE
                        || operation == AppendixADAccountUpdaterOracle.Operation.REFUND);
                for (Map<String, String> fields : java.util.List.of(Map.of("01", "Y"), Map.of("01", "I"), Map.of("02", "O"))) {
                    request(p, fields);
                    assertThat(status(p, "REQUEST-LAYOUT")).as(brand + "/" + operation + "/" + fields)
                            .isEqualTo(allowed ? Status.PASS : Status.FAIL);
                }
            }
        }
        p = defaults();
        for (String value : new String[]{"", "N", "y", "YY", "O"}) {
            request(p, Map.of("01", value));
            assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
        }
        request(p, Map.of("02", "Y"));
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
        request(p, Map.of("01", "Y", "02", "O"));
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
        assertThat(status(p, "OVERRIDE")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksTable060Reconstructed100ByteBoundaryAndUnknownSubtableGates() throws Exception {
        ObjectNode p = defaults();
        for (int length : new int[]{99, 100, 101}) {
            request(p, Map.of("03", "A".repeat(25), "04", "B".repeat(25),
                    "05", "C".repeat(25), "06", "D".repeat(length - 91)));
            assertThat(status(p, "REQUEST-LAYOUT")).as("bytes " + length)
                    .isEqualTo(length > 100 ? Status.FAIL : Status.REVIEW_REQUIRED);
        }
        for (String id : new String[]{"00", "26", "1", "001", "AA"}) {
            request(p, Map.of(id, "Y"));
            assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
        }
        request(p, Map.of("03", "A".repeat(26)));
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
        request(p, Map.of("03", "\u00e9"));
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
        request(p, Map.of("01", "Y"));
        p.putNull("cardNotPresent");
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("cardNotPresent", false);
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksOverrideDoesNotPermitOptimizationOrSilentlyForceY() throws Exception {
        ObjectNode p = defaults();
        request(p, Map.of("02", "O"));
        response(p, Map.of());
        p.put("optimizerUsed", false);
        assertThat(status(p, "OVERRIDE")).isEqualTo(Status.PASS);
        assertThat(status(p, "TRIGGER-INDICATOR")).isEqualTo(Status.REVIEW_REQUIRED);
        p.putNull("optimizerUsed");
        assertThat(status(p, "OVERRIDE")).isEqualTo(Status.REVIEW_REQUIRED);
        p.put("optimizerUsed", true);
        assertThat(status(p, "OVERRIDE")).isEqualTo(Status.FAIL);
        p.put("optimizerUsed", false);
        response(p, Map.of("004", "E"));
        assertThat(status(p, "OVERRIDE")).isEqualTo(Status.FAIL);
        assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksAllResponseFieldWidthsStatusesExpiryAndSourceConflict() throws Exception {
        ObjectNode p = defaults();
        for (String id : new String[]{"001", "002"}) {
            for (int width : new int[]{1, 18, 19, 20}) {
                response(p, Map.of(id, "A".repeat(width)));
                assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(width > 19 ? Status.FAIL : Status.PASS);
            }
        }
        for (String value : new String[]{"3001", "3012", "0001", "9912"}) {
            response(p, Map.of("003", value));
            assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(Status.PASS);
        }
        for (String value : new String[]{"3000", "3013", "1230", "301", "30011", "AA12"}) {
            response(p, Map.of("003", value));
            assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(Status.FAIL);
        }
        for (String value : new String[]{"A", "E", "Q", "C", "U"}) {
            response(p, Map.of("004", value));
            assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(Status.PASS);
        }
        response(p, Map.of("004", "X"));
        assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(Status.FAIL);
        for (int width : new int[]{1, 2, 3, 4, 5}) {
            response(p, Map.of("005", "A".repeat(width)));
            assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(width > 4 ? Status.FAIL : Status.REVIEW_REQUIRED);
        }
        response(p, Map.of("007", "A"));
        assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(Status.REVIEW_REQUIRED);
        response(p, Map.of("01", "A"));
        assertThat(status(p, "RESPONSE-LAYOUT")).isEqualTo(Status.FAIL);
    }

    @Test
    void enforcesYVersusIResponseInformationAndPanTokenExclusions() throws Exception {
        ObjectNode p = defaults();
        for (String id : new String[]{"001", "002", "003"}) {
            p.put("transArmor", id.equals("002"));
            response(p, Map.of(id, id.equals("003") ? "3012" : "1234567890123456"));
            request(p, Map.of("01", "Y"));
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.PASS);
            request(p, Map.of("01", "I"));
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.FAIL);
            request(p, Map.of());
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.FAIL);
        }
        p = defaults();
        p.put("transArmor", true);
        assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.FAIL);
        p.put("transArmor", false);
        response(p, Map.of("002", "1234567890123456"));
        assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.FAIL);
        response(p, Map.of());
        request(p, Map.of());
        assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.PASS);
    }

    @Test
    void checksResponseBrandServiceAndDeclineReattemptEvidence() throws Exception {
        ObjectNode p = defaults();
        request(p, Map.of("01", "I"));
        for (String brand : new String[]{"VISA", "MASTERCARD", "OTHER"}) {
            p.put("brand", brand);
            response(p, Map.of("004", "E"));
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(brand.equals("OTHER") ? Status.FAIL : Status.PASS);
            response(p, Map.of("006", "VAU001"));
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(brand.equals("VISA") ? Status.PASS : Status.FAIL);
        }
        p = defaults();
        for (String flag : new String[]{"optimizerQualified", "optimizerUsed"}) {
            p.put(flag, false);
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.FAIL);
            p.putNull(flag);
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.REVIEW_REQUIRED);
            p.put(flag, true);
        }
        response(p, Map.of("005", "05"));
        for (String flag : new String[]{"networkDeclined", "networkReattempted"}) {
            p.put("networkDeclined", true).put("networkReattempted", true);
            p.put(flag, false);
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.FAIL);
            p.putNull(flag);
            assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.REVIEW_REQUIRED);
        }
        p.put("networkDeclined", true).put("networkReattempted", true);
        assertThat(status(p, "RESPONSE-GATES")).isEqualTo(Status.PASS);
    }

    @Test
    void recognizesEveryVauResultFromSourceWithoutInventingVau015() throws Exception {
        String source = Files.readString(Atl105Paths.docs().resolve("specs").resolve("extracted_text.txt"));
        int start = source.indexOf("VAU001");
        int end = source.indexOf("--- PAGE 314 ---", start);
        var codes = Pattern.compile("VAU[0-9]{3}").matcher(source.substring(start, end)).results()
                .map(match -> match.group()).toList();
        assertThat(codes).hasSize(15).containsExactlyInAnyOrderElementsOf(AppendixADAccountUpdaterOracle.RESULT_CODES.keySet());
        ObjectNode p = defaults();
        for (String code : codes) {
            response(p, Map.of("006", code));
            assertThat(status(p, "RESULT-CODE")).isEqualTo(Status.PASS);
            assertThat(status(p, "EXTERNAL-EVIDENCE")).isEqualTo(Status.REVIEW_REQUIRED);
        }
        for (String code : new String[]{"VAU015", "VAU000", "VAU999", "vau001"}) {
            response(p, Map.of("006", code));
            assertThat(status(p, "RESULT-CODE")).isEqualTo(Status.FAIL);
        }
    }

    @Test
    void checksOriginalAndUpdatedPanTokenPairsWithCorrectExpiryConversion() throws Exception {
        for (boolean token : new boolean[]{false, true}) {
            ObjectNode p = defaults();
            p.put("phase", "FOLLOW_ON").put("operation", "COMPLETION").put("transArmor", token);
            String original = token ? "ORIGINALTOKEN" : "4000000000000002";
            String updated = token ? "UPDATEDTOKEN" : "4111111111111111";
            credential(p, "originalCredential", original, "1229");
            response(p, Map.of(token ? "002" : "001", updated, "003", "3012"));
            credential(p, "sentCredential", original, "1229");
            assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.PASS);
            credential(p, "sentCredential", updated, "1230");
            assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.PASS);
            credential(p, "sentCredential", updated, "1229");
            assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.FAIL);
            credential(p, "sentCredential", original, "1230");
            assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.FAIL);
            response(p, Map.of("003", "3012"));
            assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.PASS);
            credential(p, "sentCredential", updated, "1230");
            response(p, Map.of(token ? "002" : "001", updated));
            assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.REVIEW_REQUIRED);
            response(p, Map.of());
            assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.FAIL);
        }
    }

    @Test
    void followonUsesOriginalUpdaterRequestOperationRatherThanCompletionOperation() throws Exception {
        ObjectNode p = defaults();
        p.put("phase", "FOLLOW_ON").put("operation", "COMPLETION");
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.PASS);
        p.put("updaterRequestOperation", "REFUND");
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.PASS);
        p.put("updaterRequestOperation", "REVERSAL");
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.FAIL);
        p.putNull("updaterRequestOperation");
        assertThat(status(p, "REQUEST-LAYOUT")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void validatesMissingAndMalformedCredentialEvidenceExplicitly() throws Exception {
        ObjectNode p = defaults();
        p.put("phase", "FOLLOW_ON");
        assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.REVIEW_REQUIRED);
        credential(p, "sentCredential", "4111111111111111", "0030");
        p.putNull("originalCredential");
        assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.FAIL);
        credential(p, "sentCredential", "4111111111111111", "1230");
        assertThat(status(p, "ACCOUNT-DEPENDENCY")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void retainsCredentialsOnEveryListedFollowonRetryOperation() throws Exception {
        ObjectNode p = defaults();
        p.put("phase", "RETRY");
        for (String op : new String[]{"REVERSAL", "COMPLETION", "CANCELLATION", "VOID_RETURN", "TIMEOUT_REVERSAL"}) {
            p.put("operation", op);
            credential(p, "initialAttemptCredential", "4000000000000002", "1229");
            credential(p, "sentCredential", "4000000000000002", "1229");
            assertThat(status(p, "RETRY-CREDENTIALS")).isEqualTo(Status.PASS);
            credential(p, "sentCredential", "4111111111111111", "1230");
            assertThat(status(p, "RETRY-CREDENTIALS")).isEqualTo(Status.FAIL);
            credential(p, "sentCredential", "4000000000000002", "1230");
            assertThat(status(p, "RETRY-CREDENTIALS")).isEqualTo(Status.FAIL);
        }
        p.putNull("initialAttemptCredential");
        assertThat(status(p, "RETRY-CREDENTIALS")).isEqualTo(Status.REVIEW_REQUIRED);
    }
}
