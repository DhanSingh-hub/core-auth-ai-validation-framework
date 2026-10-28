package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixAATransArmorOracle;
import com.coreauth.validator.canonical.AppendixAATransArmorOracle.Scenario;
import com.coreauth.validator.canonical.AppendixAATransArmorOracle.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixAATransArmorOracleTest {
    private final ObjectMapper json = new ObjectMapper();
    private final AppendixAATransArmorOracle oracle = new AppendixAATransArmorOracle();

    private JsonNode coverage() throws Exception {
        return json.readTree(Atl105Paths.testJson().resolve("appendices")
                .resolve("appendix-aa-segment-100-coverage.json").toFile());
    }
    private Status status(ObjectNode payload, String rule) throws Exception {
        return oracle.assess(json.treeToValue(payload, Scenario.class)).stream()
                .filter(a -> a.businessRequirementId().equals("BR-SEG100-APPAA-" + rule))
                .findFirst().orElseThrow().status();
    }

    @Test
    void executesAllPersistedFixturesWithSourceTraceability() throws Exception {
        JsonNode pack = coverage();
        assertThat(pack.path("businessRequirements").size()).isEqualTo(AppendixAATransArmorOracle.RULES.size());
        for (String rule : AppendixAATransArmorOracle.RULES) {
            assertThat(pack.path("businessRequirements").findValuesAsText("id")).contains("BR-SEG100-APPAA-" + rule);
        }
        int count = 0;
        for (JsonNode fixture : pack.path("testData")) {
            ObjectNode payload = pack.path("fixtureDefaults").deepCopy();
            payload.setAll((ObjectNode) fixture.path("payload"));
            String id = fixture.path("expected").path("businessRequirementId").asText();
            var result = oracle.assess(json.treeToValue(payload, Scenario.class)).stream()
                    .filter(a -> a.businessRequirementId().equals(id)).findFirst().orElseThrow();
            assertThat(result.status().name()).as(fixture.path("id").asText())
                    .isEqualTo(fixture.path("expected").path("status").asText());
            count++;
        }
        assertThat(count).isEqualTo(pack.path("testDataReadinessSummary").path("executable").asInt());
    }

    @Test
    void checksExactEncryptedEdata400ByteBoundaryAndNonAsciiEvidence() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        for (int size : new int[]{399, 400, 401}) {
            payload.put("accountNumber", "TAP1234561KEYID123456AB12" + "A".repeat(size));
            assertThat(status(payload, "ACCOUNT-FORMAT")).isEqualTo(size > 400 ? Status.FAIL : Status.PASS);
        }
        payload.put("accountNumber", "TAP1234561KEYID123456AB12\u00e9");
        assertThat(status(payload, "ACCOUNT-FORMAT")).isEqualTo(Status.REVIEW_REQUIRED);
        payload.put("accountNumber", "TAP1234563KEYID123456AB12" + "A".repeat(400) + "\u001c1229");
        assertThat(status(payload, "ACCOUNT-FORMAT")).isEqualTo(Status.PASS);
        payload.put("method", "TA_VE");
        payload.put("manuallyKeyed", true);
        payload.put("accountNumber", "TAP1234566DOM01;BRD01AB12PROCESSOR:" + "A".repeat(400) + "\u001c1229");
        assertThat(status(payload, "ACCOUNT-FORMAT")).isEqualTo(Status.PASS);
    }

    @Test
    void checksAllEightEdataIdentifiersAndMethodMismatch() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        for (String method : new String[]{"RSA_PKI", "TDES_DUKPT", "ONGUARD_FPE", "AES_DUKPT"}) {
            payload.put("method", method);
            String key = "RSA_PKI".equals(method) ? "KEYID123456" : "00000000000";
            for (char identifier : new char[]{'1', '2', '3'}) {
                payload.put("accountNumber", "TAP123456" + identifier + key + "AB12"
                        + (identifier == '3' ? "CIPHER\u001c1229" : "CIPHER"));
                assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.PASS);
            }
        }
        payload.put("method", "TOKEN_ONLY");
        payload.put("accountNumber", "TAP1234564KEYID123456AB12SYNTHETICPAN");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.REVIEW_REQUIRED);
        payload.put("method", "TA_VE");
        payload.put("processorCode", "PROCESSOR");
        payload.put("manuallyKeyed", false);
        payload.put("pumpLaneNumber", "01");
        payload.put("cardAcceptorTerminalId", "TESTTERMINAL");
        for (char identifier : new char[]{'6', '7', 'M'}) {
            payload.put("accountNumber", "TAP123456" + identifier + "DOM01;BRD01AB12PROCESSOR:CIPHER");
            assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.PASS);
        }
        payload.put("stage", "SUBSEQUENT");
        payload.put("accountNumber", "TAP1234560DOM01;BRD01AB12TOKEN");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.REVIEW_REQUIRED);
        payload.put("stage", "INITIAL");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
    }

    @Test
    void validatesProcessorCodeMaximumAndManualExpiration() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("method", "TA_VE");
        payload.put("manuallyKeyed", true);
        payload.put("expirationMmyy", "1229");
        for (int size : new int[]{16, 17}) {
            payload.put("processorCode", "A".repeat(size));
            payload.put("accountNumber", "TAP1234566DOM01;BRD01AB12" + "A".repeat(size) + ":CIPHER\u001c1229");
            assertThat(status(payload, "EDATA-METHOD")).isEqualTo(size == 16 ? Status.PASS : Status.FAIL);
        }
        payload.put("method", "RSA_PKI");
        payload.put("accountNumber", "TAP1234563KEYID123456AB12CIPHER\u001c1329");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksAdditionalDataRequirementsAndWidthLimits() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("method", "AES_DUKPT");
        ObjectNode additional = payload.putObject("additionalData");
        additional.put("01", "A".repeat(40));
        additional.put("02", "A".repeat(8));
        assertThat(status(payload, "ADDITIONAL-DATA")).isEqualTo(Status.REVIEW_REQUIRED);
        additional.put("01", "A".repeat(41));
        assertThat(status(payload, "ADDITIONAL-DATA")).isEqualTo(Status.FAIL);
        additional.put("01", "KSN");
        additional.put("02", "A".repeat(9));
        assertThat(status(payload, "ADDITIONAL-DATA")).isEqualTo(Status.FAIL);
        additional.remove("02");
        assertThat(status(payload, "ADDITIONAL-DATA")).isEqualTo(Status.FAIL);
        payload.put("method", "TDES_DUKPT");
        assertThat(status(payload, "ADDITIONAL-DATA")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void rejectsWrongLoadTypeResponseAndPkiDeviceType() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("stage", "KEY_LOAD");
        payload.put("loadType", "K");
        payload.put("loadApproved", true);
        payload.put("responseCode", "L");
        payload.put("terminalIdentifier", "++TESTTERMINAL");
        assertThat(status(payload, "KEY-LOAD-RESPONSE")).isEqualTo(Status.FAIL);
        payload.put("responseCode", "K");
        payload.put("terminalIdentifier", "S0TESTTERMINAL");
        assertThat(status(payload, "KEY-LOAD-RESPONSE")).isEqualTo(Status.FAIL);
        payload.put("terminalIdentifier", "++TESTTERMINAL");
        payload.put("loadType", "X");
        assertThat(status(payload, "KEY-LOAD-RESPONSE")).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsBadPrefixAndMissingExpirationAndWrongRetrievalDecline() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("merchantNumber", "123456789");
        payload.put("plaintextBeforeEncryption", "00999999SYNTHETICPAN");
        assertThat(status(payload, "PKI-PREPROCESSING")).isEqualTo(Status.FAIL);
        payload.put("stage", "SUBSEQUENT");
        payload.put("accountNumber", "TAP1234560KEYID123456AB12TOKEN");
        assertThat(status(payload, "EXPIRATION-TOKEN-PERSISTENCE")).isEqualTo(Status.FAIL);
        payload.put("stage", "INITIAL");
        payload.put("method", "TOKEN_ONLY");
        payload.put("tokenRetrievalFailed", true);
        payload.put("declineCode", "XX");
        assertThat(status(payload, "DECLINE-TOKEN-RETRIEVAL")).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsBadKeyIdAndUnknownHeaderIdentifiers() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("loadedKeyId", "OTHERKEY123");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.put("method", "AES_DUKPT");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        for (String account : new String[]{"TAP1234569KEYID123456AB12CIPHER",
                "BAD1234561KEYID123456AB12CIPHER", "TAP1234561KEYID123456AB!2CIPHER",
                "TAP1234561KEYID123456AB12", "TAP123"}) {
            payload.put("accountNumber", account);
            assertThat(status(payload, "ACCOUNT-FORMAT")).isEqualTo(Status.FAIL);
        }
    }

    @Test
    void rejectsEmptyCiphertextAndContradictoryManualContext() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("accountNumber", "TAP1234563KEYID123456AB12\u001c1229");
        assertThat(status(payload, "ACCOUNT-FORMAT")).isEqualTo(Status.FAIL);
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.put("method", "TA_VE");
        payload.put("manuallyKeyed", true);
        payload.put("processorCode", "PROC");
        payload.put("accountNumber", "TAP1234566DOM01;BRD01AB12PROC:\u001c1229");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.put("method", "RSA_PKI");
        payload.put("accountNumber", "TAP1234561KEYID123456AB12CIPHER");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.put("manuallyKeyed", false);
        payload.put("accountNumber", "TAP1234563KEYID123456AB12CIPHER\u001c1229");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.remove("manuallyKeyed");
        payload.remove("loadedKeyId");
        payload.put("accountNumber", "TAP1234563KEYID123456AB12CIPHER\u001c1329");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.put("method", "TA_VE");
        payload.remove("processorCode");
        payload.put("accountNumber", "TAP123456MDOM01;BRD01AB12PROC:CIPHER");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
    }

    @Test
    void exercisesTokenOnFileAndSyntheticCompletionCancellationProfiles() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("stage", "SUBSEQUENT");
        payload.put("storedOnFileToken", "SYNTHETICTOKEN");
        payload.put("expirationMmyy", "1229");
        payload.put("originalExpirationMmyy", "1229");
        for (String method : new String[]{"RSA_PKI", "TA_VE"}) {
            payload.put("method", method);
            String key = "TA_VE".equals(method) ? "DOM01;BRD01" : "KEYID123456";
            payload.put("accountNumber", "TAP1234560" + key + "AB12SYNTHETICTOKEN");
            assertThat(status(payload, "EXPIRATION-TOKEN-PERSISTENCE")).isEqualTo(Status.PASS);
            assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.REVIEW_REQUIRED);
            assertThat(status(payload, "EXTERNAL-VALIDITY")).isEqualTo(Status.REVIEW_REQUIRED);
        }
        payload.put("originalResponseToken", "OTHER");
        assertThat(status(payload, "EXPIRATION-TOKEN-PERSISTENCE")).isEqualTo(Status.REVIEW_REQUIRED);
        payload.remove("originalResponseToken");
        payload.put("expirationMmyy", "1129");
        assertThat(status(payload, "EXPIRATION-TOKEN-PERSISTENCE")).isEqualTo(Status.FAIL);
    }

    @Test
    void testsAllRoutingIdentityRequirementsAndRefreshEvidence() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        payload.put("method", "TA_VE");
        payload.put("processorCode", "PROC");
        payload.put("manuallyKeyed", false);
        payload.remove("terminalIdentifier");
        payload.put("accountNumber", "TAP1234566DOM01;BRD01AB12PROC:CIPHER");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.REVIEW_REQUIRED);
        payload.put("accountNumber", "TAP123456MDOM01;BRD01AB12PROC:CIPHER");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.put("cardAcceptorTerminalId", "SYNTHETICTERMINAL");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.PASS);
        payload.put("accountNumber", "TAP1234567DOM01;BRD01AB12PROC:CIPHER");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.FAIL);
        payload.put("pumpLaneNumber", "01");
        assertThat(status(payload, "EDATA-METHOD")).isEqualTo(Status.PASS);
        payload.put("downloadIndicator", "8");
        assertThat(status(payload, "KEY-REFRESH")).isEqualTo(Status.REVIEW_REQUIRED);
        payload.put("keyUpdateScheduled", true);
        assertThat(status(payload, "KEY-REFRESH")).isEqualTo(Status.PASS);
        payload.put("keyUpdateScheduled", false);
        assertThat(status(payload, "KEY-REFRESH")).isEqualTo(Status.FAIL);
    }

    @Test
    void testsAdditionalDataRequirednessForEveryEncryptionMethod() throws Exception {
        ObjectNode payload = coverage().path("fixtureDefaults").deepCopy();
        for (String method : new String[]{"TDES_DUKPT", "ONGUARD_FPE", "AES_DUKPT"}) {
            payload.put("method", method);
            payload.putObject("additionalData");
            assertThat(status(payload, "ADDITIONAL-DATA")).isEqualTo(Status.FAIL);
            ObjectNode additional = payload.putObject("additionalData");
            additional.put("01", "KSN");
            if ("AES_DUKPT".equals(method)) additional.put("02", "DEVICE");
            assertThat(status(payload, "ADDITIONAL-DATA")).isEqualTo(Status.REVIEW_REQUIRED);
        }
        payload.put("method", "RSA_PKI");
        payload.put("merchantNumber", "123");
        payload.put("plaintextBeforeEncryption", "00123SYNTHETIC");
        assertThat(status(payload, "PKI-PREPROCESSING")).isEqualTo(Status.REVIEW_REQUIRED);
        payload.put("merchantNumber", "1234567890123456");
        assertThat(status(payload, "PKI-PREPROCESSING")).isEqualTo(Status.FAIL);
    }
}
