package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment100CardTypeOracle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100CardTypeOracleTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String[] CARD_CODES = {
            "001", "011", "012", "013", "020", "040", "041", "045", "046", "050",
            "051", "052", "053", "055", "056", "057", "058", "059", "060", "061",
            "064", "070", "071", "072", "073", "074", "075", "077", "078", "079",
            "080", "081", "083", "084", "085", "090"
    };

    @Test
    void expectedMatrixContainsAllThirtySixFinancialCardCodes() {
        assertThat(new Segment100CardTypeOracle().expectedCardTypes().keySet())
                .containsExactly(CARD_CODES);
    }

    @Test
    void acceptsEveryCardCodeWithEveryStandardTransactionType() {
        Segment100CardTypeOracle oracle = new Segment100CardTypeOracle();
        String[] transactionTypes = {"0", "3", "4", "5", "6", "7", "8", "A", "B", "C", "S", "U", "Z"};
        for (String transactionType : transactionTypes) {
            for (String cardCode : CARD_CODES) {
                assertThat(oracle.validateAiPayload(transactionType + cardCode, payload(transactionType, cardCode)).errors())
                        .as("Prompt Code %s%s", transactionType, cardCode).isEmpty();
            }
        }
    }

    @Test
    void rejectsUnknownCardCodes() {
        Segment100CardTypeOracle oracle = new Segment100CardTypeOracle();
        for (String cardCode : new String[]{"021", "099"}) {
            assertThat(oracle.validateAiPayload("0" + cardCode, payload("0", cardCode)).errors())
                    .as("card code %s", cardCode)
                    .anyMatch(error -> error.reason().contains("Unsupported financial card type"));
        }
    }

    @Test
    void rejectsAlphabeticCardCodeAsMalformed() {
        var result = new Segment100CardTypeOracle().validateAiPayload("0XXX", payload("0", "XXX"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("CardType must be exactly three digits"));
    }

    @Test
    void rejectsMalformedCardCodes() {
        Segment100CardTypeOracle oracle = new Segment100CardTypeOracle();
        for (String cardCode : new String[]{"20", "0020", "02A", "", " 020"}) {
            assertThat(oracle.validateAiPayload("0020", payload("0", cardCode)).errors())
                    .as("card code %s", cardCode)
                    .anyMatch(error -> error.reason().contains("CardType must be exactly three digits"));
        }
    }

    @Test
    void rejectsUnknownTransactionTypeAndValidCardCombination() {
        var result = new Segment100CardTypeOracle().validateAiPayload("X020", payload("X", "020"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Unsupported financial transaction type"));
    }

    @Test
    void cardCoverageValidatorAcceptsAllThirtySixAiArtifacts() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-card-types-");
        int index = 0;
        for (String cardCode : CARD_CODES) {
            MAPPER.writeValue(directory.resolve("card-" + index++ + ".json").toFile(), payload("0", cardCode));
        }

        var result = new com.coreauth.validator.canonical.Segment100CardTypeCoverageValidator()
                .validateDirectory(directory);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void cardCoverageValidatorReportsMissingCardArtifacts() throws Exception {
        Path directory = Files.createTempDirectory("segment-100-card-types-");
        for (int index = 0; index < CARD_CODES.length - 1; index++) {
            MAPPER.writeValue(directory.resolve("card-" + index + ".json").toFile(), payload("0", CARD_CODES[index]));
        }

        var result = new com.coreauth.validator.canonical.Segment100CardTypeCoverageValidator()
                .validateDirectory(directory);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("Missing AI artifact for card type 090"));
    }

    private static ObjectNode payload(String transactionType, String cardType) {
        ObjectNode payload = MAPPER.createObjectNode();
        ObjectNode request = payload.putObject("Financial Request");
        ObjectNode segment = request.putObject("Standard Segment");
        segment.putObject("PromptCode").put("TransactionType", transactionType).put("CardType", cardType);
        return payload;
    }
}
