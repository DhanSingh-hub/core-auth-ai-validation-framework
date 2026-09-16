package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100PaymentNetworkOracle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100PaymentNetworkOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void expectedMatrixContainsAllSixteenLeafNetworkTypes() {
        assertThat(new Segment100PaymentNetworkOracle().expectedNetworks()).hasSize(16);
    }

    @Test
    void acceptsEverySupportedPaymentNetworkWithSegment100() {
        Segment100PaymentNetworkOracle oracle = new Segment100PaymentNetworkOracle();
        for (String network : oracle.expectedNetworks().keySet()) {
            assertThat(oracle.validateArtifact(network, payload(network)).errors()).as(network).isEmpty();
        }
    }

    @Test
    void rejectsUnknownPaymentNetwork() {
        var result = new Segment100PaymentNetworkOracle().validateArtifact("UNKNOWN", payload("UNKNOWN"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Unsupported payment network type"));
    }

    @Test
    void rejectsPaymentNetworkWithoutSegment100() {
        ObjectNode artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("paymentNetworkType", "CREDIT_NONPROPRIETARY");
        artifact.putObject("Financial Request");

        var result = new Segment100PaymentNetworkOracle().validateArtifact("NO-SEGMENT", artifact);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Standard Segment Type 100 is required"));
    }

    private static ObjectNode payload(String network) {
        ObjectNode artifact = MAPPER.createObjectNode();
        artifact.putObject("metadata").put("paymentNetworkType", network);
        ObjectNode request = artifact.putObject("Financial Request");
        request.put("MessageType", "ATL105");
        request.putObject("Standard Segment").put("SegmentType", "100");
        return artifact;
    }
}
