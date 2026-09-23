package com.coreauth.validator;

import com.coreauth.validator.rules.Atl105RuleRepository;
import com.coreauth.validator.rules.Atl105SpecRuleRepository;
import com.coreauth.validator.rules.CompositeRuleRepository;
import com.coreauth.validator.source.JsonDataSource;
import com.coreauth.validator.source.JsonDocument;
import com.coreauth.validator.source.LocalFileJsonSource;
import com.coreauth.validator.validation.Atl105JsonValidator;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105JsonValidatorTest {

    private static Atl105JsonValidator validator;

    @BeforeAll
    static void setUp() throws IOException, com.opencsv.exceptions.CsvValidationException {
        // Phase 1: reference data is the CSV already generated from the ATL105 Visa sample report.
        Path csv = Paths.get(System.getProperty("user.dir"))
            .resolve("..").resolve("reports").resolve("atl105-visa").resolve("ATL105 Visa - Field Values.csv").normalize();
        Atl105RuleRepository csvRules = Atl105RuleRepository.loadFromCsv(csv);
        assertThat(csvRules.ruleCount()).isGreaterThan(0);

        // Spec-derived rules take precedence; CSV-observed-data heuristics are the fallback.
        Atl105SpecRuleRepository specRules = Atl105SpecRuleRepository.loadDefault();
        assertThat(specRules.ruleCount()).isGreaterThan(0);

        validator = new Atl105JsonValidator(new CompositeRuleRepository(specRules, csvRules));
    }

    @Test
    void validSampleHasNoErrors() throws Exception {
        List<JsonDocument> docs = loadResourceFolder("valid");
        for (JsonDocument doc : docs) {
            ValidationResult result = validator.validate(doc);
            assertThat(result.errors())
                    .as("errors for " + doc.id())
                    .isEmpty();
        }
    }

    @Test
    void invalidSampleIsRejected() throws Exception {
        List<JsonDocument> docs = loadResourceFolder("invalid");
        for (JsonDocument doc : docs) {
            ValidationResult result = validator.validate(doc);
            assertThat(result.isValid()).as(doc.id()).isFalse();
            assertThat(result.errors()).isNotEmpty();
        }
    }

    @Test
    void specRuleAcceptsValidTerminalIdNotPresentInObservedCsvData() throws Exception {
        // The CSV-only ENUM heuristic would reject this because it only whitelists the ~28
        // exact TerminalID values observed in sample data. The spec rule (AN, max 22 bytes)
        // authoritatively allows any alphanumeric value up to 22 characters.
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree("{"
                + "\"Financial Request\": {"
                + "  \"MessageType\": \"ATL105\","
                + "  \"NumSegments\": \"1\","
                + "  \"Standard Segment\": { \"TerminalID\": \"NEWDEVICE001\" }"
                + "}"
                + "}");
        JsonDocument doc = new JsonDocument("inline-terminal-id-test", root);

        ValidationResult result = validator.validate(doc);

        assertThat(result.errors()).isEmpty();
    }

    private static List<JsonDocument> loadResourceFolder(String folderName) throws URISyntaxException {
        URL url = Atl105JsonValidatorTest.class.getClassLoader().getResource(folderName);
        assertThat(url).as("test resource folder '" + folderName + "' must exist").isNotNull();
        JsonDataSource source = new LocalFileJsonSource(Paths.get(url.toURI()));
        return source.loadAll();
    }
}
