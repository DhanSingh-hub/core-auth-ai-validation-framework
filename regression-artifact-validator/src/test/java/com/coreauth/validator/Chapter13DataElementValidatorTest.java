package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.coreauth.validator.validation.Atl105ElementReferenceValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator;
import com.coreauth.validator.canonical.Chapter11MessageLayoutValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Result;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class Chapter13DataElementValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Chapter13DataElementValidator validator;

    @BeforeAll static void initialize() throws IOException {
        validator = new Chapter13DataElementValidator(Atl105Paths.root());
    }

    @TestFactory List<DynamicTest> executesEveryDomainAndItsBoundaryMutations() throws IOException {
        List<DynamicTest> tests = new ArrayList<>();
        var catalog = MAPPER.readTree(Atl105Paths.root().resolve("docs").resolve("specs").resolve("kb")
                .resolve("elements").resolve("value-domains.json").toFile()).path("domains");
        assertThat(catalog).hasSize(69);
        for (var domain : catalog) {
            String id = domain.path("element").asText();
            String rule = "CH13-E" + id + "-DOMAIN";
            tests.add(dynamicTest("E" + id + " positive", () -> assertFinding(observe(id, domain.path("positive").asText()), rule, Status.CHECKS_PASSED)));
            tests.add(dynamicTest("E" + id + " mutation", () -> assertFinding(observe(id, domain.path("negative").asText()), rule, Status.INVALID)));
            if ("range".equals(domain.path("kind").asText())) {
                BigInteger min = new BigInteger(domain.path("min").asText()), max = new BigInteger(domain.path("max").asText());
                tests.add(dynamicTest("E" + id + " min", () -> assertFinding(observe(id, min.toString()), rule, Status.CHECKS_PASSED)));
                tests.add(dynamicTest("E" + id + " max", () -> assertFinding(observe(id, max.toString()), rule, Status.CHECKS_PASSED)));
                tests.add(dynamicTest("E" + id + " above-max", () -> assertFinding(observe(id, max.add(BigInteger.ONE).toString()), rule, Status.INVALID)));
                tests.add(dynamicTest("E" + id + " below-min", () -> assertFinding(observe(id, min.subtract(BigInteger.ONE).toString()), rule, Status.INVALID)));
            } else if ("codes".equals(domain.path("kind").asText())) {
                for (var value : domain.path("values")) {
                    tests.add(dynamicTest("E" + id + " assigned " + value.asText(),
                            () -> assertFinding(observe(id, value.asText()), rule, Status.CHECKS_PASSED)));
                }
            }
            tests.add(dynamicTest("E" + id + " JSON number cannot be coerced", () -> {
                var observation = observe(id, "0");
                ((ObjectNode) observation.path("elements")).put(id, 1);
                assertThat(validator.validate(observation).status()).isEqualTo(Status.INVALID);
            }));
        }
        return tests;
    }

    @Test void wireWidthIsNotAppliedToLogicalNumericValues() {
        var logical = observe("86", "1");
        assertFinding(logical, "CH13-E86-DOMAIN", Status.CHECKS_PASSED);
        assertThat(validator.validate(logical).findings()).noneMatch(f -> f.ruleId().equals("CH13-E86-WIRE-WIDTH"));
        logical.put("representation", "WIRE");
        assertFinding(logical, "CH13-E86-WIRE-WIDTH", Status.INVALID);
        ((ObjectNode) logical.path("elements")).put("86", "000001");
        assertFinding(logical, "CH13-E86-WIRE-WIDTH", Status.CHECKS_PASSED);
    }

    @Test void calendarAndSourceClockBoundariesRemainDistinct() {
        assertFinding(observe("21", "022924"), "CH13-E21-CALENDAR", Status.CHECKS_PASSED);
        assertFinding(observe("21", "022923"), "CH13-E21-CALENDAR", Status.INVALID);
        assertFinding(observe("21", "022900"), "CH13-E21-CENTURY", Status.REVIEW_REQUIRED);
        assertFinding(observe("22", "2460"), "CH13-E22-SOURCE-TIME", Status.CHECKS_PASSED);
        assertFinding(observe("22", "0000"), "CH13-E22-SOURCE-TIME", Status.INVALID);
        assertFinding(observe("166", "0000"), "CH13-E166-TIME", Status.CHECKS_PASSED);
        assertFinding(observe("166", "2460"), "CH13-E166-TIME", Status.INVALID);
        assertFinding(observe("179", "202402291259"), "CH13-E179-TIMESTAMP", Status.CHECKS_PASSED);
        assertFinding(observe("179", "202302291259"), "CH13-E179-CALENDAR", Status.INVALID);
        assertFinding(observe("125", "02292024"), "CH13-E125-CALENDAR", Status.CHECKS_PASSED);
        assertFinding(observe("125", "02292023"), "CH13-E125-CALENDAR", Status.INVALID);
        assertFinding(observe("119", "0229"), "CH13-E119-MONTH-DAY", Status.CHECKS_PASSED);
        assertFinding(observe("119", "0230"), "CH13-E119-MONTH-DAY", Status.INVALID);
    }

    @Test void totalsDateDependsOnDirectionRatherThanFlatSpecialCodeSet() {
        var observation = observe("105", "333333");
        assertFinding(observation, "CH13-E105-DATE-DIRECTION", Status.REVIEW_REQUIRED);
        observation.putObject("qualifiers").put("direction", "REQUEST");
        assertFinding(observation, "CH13-E105-SPECIAL-DATE", Status.CHECKS_PASSED);
        ((ObjectNode) observation.path("qualifiers")).put("direction", "RESPONSE");
        assertFinding(observation, "CH13-E105-CALENDAR", Status.INVALID);
        ((ObjectNode) observation.path("elements")).put("105", "240229");
        assertFinding(observation, "CH13-E105-CALENDAR", Status.CHECKS_PASSED);
    }

    @Test void transportAndDeclaredLengthsAreActuallyCorrelated() {
        var observation = observe("51", "x".repeat(151));
        ((ObjectNode) observation.path("elements")).put("52", "151");
        observation.putObject("qualifiers").put("transport", "IP");
        assertFinding(observation, "CH13-E51-TRANSPORT", Status.CHECKS_PASSED);
        assertFinding(observation, "CH13-E52-LENGTH-EQUALITY", Status.CHECKS_PASSED);
        ((ObjectNode) observation.path("qualifiers")).put("transport", "DIAL");
        assertFinding(observation, "CH13-E51-TRANSPORT", Status.INVALID);
        ((ObjectNode) observation.path("elements")).put("52", "150");
        assertFinding(observation, "CH13-E52-LENGTH-EQUALITY", Status.INVALID);
        ((ObjectNode) observation.path("elements")).remove("51");
        assertFinding(observation, "CH13-E51-COMPANION", Status.REVIEW_REQUIRED);
        observation.put("completeness", "COMPLETE");
        assertFinding(observation, "CH13-E51-COMPANION", Status.INVALID);
    }

    @Test void taxIncludesQstAndNOmitsFieldsEntirely() {
        var observation = observe("223", "I");
        var elements = (ObjectNode) observation.path("elements");
        elements.put("224", "QST").put("225", "0");
        observation.putObject("qualifiers").put("country", "CANADA");
        assertFinding(observation, "CH13-E224-COUNTRY-TAX", Status.CHECKS_PASSED);
        elements.put("223", "N");
        assertFinding(observation, "CH13-E223-TAX-OMISSION", Status.INVALID);
        elements.put("224", "").put("225", "");
        assertFinding(observation, "CH13-E223-TAX-OMISSION", Status.INVALID);
        elements.remove(List.of("224", "225"));
        assertFinding(observation, "CH13-E223-TAX-OMISSION", Status.CHECKS_PASSED);
    }

    @Test void sequenceAndAuthorizationReversalPromptReuseAreChecked() {
        var observation = observe("86", "000123");
        ((ObjectNode) observation.path("elements")).put("78", "4080");
        var context = observation.putObject("qualifiers").put("flow", "AUTHORIZATION_REVERSAL");
        context.putObject("originalElements").put("86", "000123").put("78", "4080");
        assertFinding(observation, "CH13-E86-LIFECYCLE", Status.CHECKS_PASSED);
        assertFinding(observation, "CH13-E78-LIFECYCLE", Status.CHECKS_PASSED);
        ((ObjectNode) observation.path("elements")).put("86", "000124").put("78", "0080");
        assertFinding(observation, "CH13-E86-LIFECYCLE", Status.INVALID);
        assertFinding(observation, "CH13-E78-LIFECYCLE", Status.INVALID);
    }

    @Test void actualNetAndBinOrderingAreNotJustTypeChecks() {
        var observation = observe("56", "9950");
        var elements = (ObjectNode) observation.path("elements");
        elements.put("42", "10000").put("38", "50");
        assertFinding(observation, "CH13-E56-NET-TOTAL", Status.CHECKS_PASSED);
        elements.put("56", "10000");
        assertFinding(observation, "CH13-E56-NET-TOTAL", Status.INVALID);
        elements.put("183", "400000      ").put("184", "499999      ");
        assertFinding(observation, "CH13-E184-BIN-ORDER", Status.CHECKS_PASSED);
        elements.put("184", "399999      ");
        assertFinding(observation, "CH13-E184-BIN-ORDER", Status.INVALID);
    }

    @Test void compositeOraclesAreReusedWithExplicitNetworkEvidence() {
        var observation = observe("116", "042");
        ((ObjectNode) observation.path("elements")).put("118", "2").put("117", "001");
        assertFinding(observation, "CH13-E118-APPK-REPRESENTATION_ONLY", Status.CHECKS_PASSED);
        ((ObjectNode) observation.path("elements")).put("118", "9");
        assertThat(validator.validate(observation).findings()).anyMatch(f -> f.element().equals("118") && f.status() == Status.INVALID);
        observation = observe("189", "005");
        ((ObjectNode) observation.path("elements")).put("190", "8403A00001");
        assertThat(validator.validate(observation).findings()).anyMatch(f -> f.ruleId().startsWith("CH13-E190-EMV-")
                && f.status() == Status.CHECKS_PASSED);
    }

    @Test void refusesSuccessShapedResultsForMalformedObservations() {
        assertThat(validator.validate(null).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(MAPPER.createObjectNode()).status()).isEqualTo(Status.INVALID);
        assertThat(validator.validate(observe("86", "000001")).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test void sourceDefinedNumericExceptionsDoNotProduceFalseInvalidity() throws IOException {
        var baseline = new Atl105ElementValueValidator(Atl105Paths.root());
        for (var entry : java.util.Map.of("65", "   123", "98", "********", "138", "ERN").entrySet()) {
            assertThat(baseline.validate(observe(entry.getKey(), entry.getValue())).findings())
                    .noneMatch(f -> f.element().equals(entry.getKey()) && f.status() == Status.INVALID);
        }
        var password = observe("65", "   123");
        password.put("representation", "WIRE");
        assertFinding(password, "CH13-E65-PASSWORD-PADDING", Status.CHECKS_PASSED);
        assertThat(validator.validate(password).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(validator.validate(observe("98", "********")).status()).isEqualTo(Status.REVIEW_REQUIRED);
        assertFinding(observe("98", "0"), "CH13-E98-STORE", Status.INVALID);
    }

    @Test void explicitSemanticModeIsWiredThroughReferenceAndEnvelopeWithoutChangingLegacyMode() throws IOException {
        var parent = MAPPER.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "Financial Transaction Request").put("completeness", "PARTIAL");
        parent.putObject("elements");
        var segment = parent.putArray("segments").addObject().put("segment", "100").put("dataSection", 2);
        segment.set("observation", observe("86", "000000"));
        ((ObjectNode) segment.path("observation")).remove(List.of("messageFamily", "segment", "specificationVersion"));
        var envelope = new Chapter11MessageLayoutValidator();
        assertThat(envelope.validate(parent).findings()).noneMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN"));
        parent.put("chapter13Semantics", true);
        assertThat(envelope.validate(parent).findings()).anyMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN")
                && f.status().name().equals("INVALID"));
        segment.set("elements", segment.path("observation").path("elements"));
        var reference = new Atl105ElementReferenceValidator(Atl105Paths.root());
        assertThat(reference.validate(parent).findings()).anyMatch(f -> f.ruleId().equals("CH13-E86-DOMAIN")
                && f.status() == Status.INVALID);
    }

    @Test void distinctRecordCountsAndKeyIterationsAreMeasuredFromActualArrays() {
        var observation = observe("61", "2");
        var records = observation.putObject("records");
        records.putArray("printLines").add("first").add("second");
        records.putArray("receiptLines").add("receipt");
        ((ObjectNode) observation.path("elements")).put("168", "01");
        assertFinding(observation, "CH13-E61-RECORD-COUNT", Status.CHECKS_PASSED);
        assertFinding(observation, "CH13-E168-RECORD-COUNT", Status.CHECKS_PASSED);
        ((ObjectNode) observation.path("elements")).put("61", "1");
        assertFinding(observation, "CH13-E61-RECORD-COUNT", Status.INVALID);
        ((ObjectNode) observation.path("elements")).put("211", "Y Y");
        records.putArray("monerisKeys").add("A".repeat(16)).add("B".repeat(16));
        assertFinding(observation, "CH13-E212-KEY-COUNT", Status.CHECKS_PASSED);
        ((com.fasterxml.jackson.databind.node.ArrayNode) records.path("monerisKeys")).remove(1);
        assertFinding(observation, "CH13-E212-KEY-COUNT", Status.INVALID);
    }

    @Test void micrTruncationAndChecksumReuseRequireOriginalEvidence() {
        String raw = "1".repeat(50) + "EXTRA";
        var observation = observe("122", raw.substring(0, 50));
        observation.putObject("qualifiers").put("rawMicr", raw).put("checkService", "ECA_TELECHECK");
        ((ObjectNode) observation.path("elements")).put("137", raw);
        assertFinding(observation, "CH13-E122-MICR-TRUNCATION", Status.CHECKS_PASSED);
        assertFinding(observation, "CH13-E137-MICR-EXTENSION", Status.CHECKS_PASSED);
        ((ObjectNode) observation.path("elements")).put("122", "2".repeat(50));
        assertFinding(observation, "CH13-E122-MICR-TRUNCATION", Status.INVALID);
        observation = observe("187", "1".repeat(25));
        observation.putObject("qualifiers").put("emvTransaction", true).putObject("originalElements").put("187", "1".repeat(25));
        assertFinding(observation, "CH13-E187-CHECKSUM-REUSE", Status.CHECKS_PASSED);
        ((ObjectNode) observation.path("elements")).put("187", "2".repeat(25));
        assertFinding(observation, "CH13-E187-CHECKSUM-REUSE", Status.INVALID);
    }

    @Test void assembledCapkFileIsNotMeasuredAgainstOneBlockLength() {
        var observation = observe("193", "001");
        ((ObjectNode) observation.path("elements")).put("194", "A".repeat(1000));
        assertFinding(observation, "CH13-E193-BLOCK-SCOPE", Status.REVIEW_REQUIRED);
        observation.putObject("qualifiers").put("capkDataScope", "BLOCK");
        assertFinding(observation, "CH13-E193-LENGTH-EQUALITY", Status.INVALID);
    }

    private static ObjectNode observe(String element, String value) {
        var observation = MAPPER.createObjectNode();
        observation.put("specificationVersion", "2026-3").put("messageFamily", "SOURCE_DOMAIN_OBSERVATION")
                .put("segment", "100").put("completeness", "PARTIAL").put("representation", "LOGICAL");
        observation.putObject("elements").put(element, value);
        return observation;
    }

    private static void assertFinding(ObjectNode observation, String rule, Status status) {
        Result result = validator.validate(observation);
        assertThat(result.findings()).as("%s should be %s, received %s", rule, status, result.findings())
                .anyMatch(f -> f.ruleId().equals(rule) && f.status() == status);
    }
}
