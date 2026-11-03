package com.coreauth.validator.coverage;

import com.coreauth.validator.canonical.AccountNumberEntryValidator;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Path;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

public final class GenerateSegment100AccountEntryPackage {
    public static final String FILE_NAME = "segment-100-elements-2-and-12-execution-package.json";
    private static final String SOURCE_SHA256 = "6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa";
    private static final String SYNTHETIC_ACCOUNT = "0000000000000000";
    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode output = mapper.createObjectNode();
    private final ArrayNode requirements = output.putArray("businessRequirements");
    private final ArrayNode scenarios = output.putArray("testScenarios");
    private final ArrayNode cases = output.putArray("testCases");
    private final ArrayNode data = output.putArray("testData");

    private record Rule(String id, String scenarioId, String element, ObjectNode anchor) { }

    public static void main(String[] args) throws Exception {
        ObjectNode generated = new GenerateSegment100AccountEntryPackage().generate();
        Path file = Atl105Paths.testJson().resolve(FILE_NAME);
        ObjectMapper mapper = new ObjectMapper();
        var artifactPackage = mapper.treeToValue(generated,
                com.coreauth.validator.canonical.CanonicalArtifactPackage.class);
        var readiness = new CanonicalTraceabilityValidator().validateForExecution(artifactPackage,
                Atl105Paths.root());
        if (!readiness.isValid()) throw new IllegalStateException(readiness.errors().toString());
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), generated);
        ObjectNode report = mapper.createObjectNode().put("scope", "ELEMENT_2_12_LOCAL_PREDICATES")
                .put("packageFile", FILE_NAME).put("packageSha256", sha256(file))
                .put("sourceSha256", SOURCE_SHA256)
                .put("validatorSourceSha256", sha256(Path.of("src/main/java/com/coreauth/validator/canonical/AccountNumberEntryValidator.java")))
                .put("semanticApproval", "NOT_ASSESSED").put("hostExecution", "NOT_EXECUTED")
                .put("wireValidation", "NOT_EXECUTED").put("executionReadyRequirements", artifactPackage.getBusinessRequirements().size());
        ArrayNode results = report.putArray("results");
        AccountNumberEntryValidator validator = new AccountNumberEntryValidator();
        int passed = 0;
        for (var fixture : artifactPackage.getTestData()) {
            var actual = validator.validateTestData(fixture);
            String observed = actual.isValid() ? "PASS" : "FAIL";
            String expected = fixture.getExpectedValidation();
            String expectedContext = fixture.getResponse().path("errorContext").asText();
            String expectedReason = fixture.getResponse().path("errorContains").asText();
            boolean matched = expected.equals(observed) && actual.errors().stream().allMatch(error ->
                    expectedContext.equals(error.context()) && error.reason().contains(expectedReason));
            if (matched) passed++;
            ObjectNode row = results.addObject().put("testDataId", fixture.getId())
                    .put("testCaseId", fixture.getTestCaseIds().getFirst()).put("expected", expected)
                    .put("observed", observed).put("matched", matched);
            ArrayNode errors = row.putArray("errors");
            for (var error : actual.errors()) errors.addObject().put("context", error.context()).put("reason", error.reason());
        }
        report.put("executedFixtures", results.size()).put("matchedFixtures", passed)
                .put("allLocalAssertionsMatched", passed == results.size());
        Path reportFile = Atl105Paths.testOutput().resolve(
                "test-solution-independent-review/segment-100-elements-2-and-12-local-execution.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(reportFile.toFile(), report);
        if (passed != results.size()) throw new IllegalStateException("Element 2/12 local assertion mismatch; see " + reportFile);
        System.out.printf("Element 2/12 local package: BR=%d TS=%d TC=%d TD=%d%n",
                generated.path("businessRequirements").size(), generated.path("testScenarios").size(),
                generated.path("testCases").size(), generated.path("testData").size());
    }

    public ObjectNode generate() {
        if (!requirements.isEmpty()) throw new IllegalStateException("Use a fresh generator for each package");
        output.putObject("manifest").put("packageId", "ATL105-SEG100-ELEMENTS-2-AND-12-EXECUTION")
                .put("specification", "ATL105").put("specificationVersion", "2026-3")
                .put("strictExecutionContract", true);

        Rule presence = rule("E2-PRESENCE", "2", "account-number",
                "Element 2 identifies the card or account for the declared manual-entry request.",
                "Non-TransArmor manual-entry request fixtures", "Identifies the card or account.");
        fixture(presence, "PRESENT", SYNTHETIC_ACCOUNT, "", entry("manual"), "PASS", "");
        fixture(presence, "MISSING", "", "", entry("manual"), "FAIL", "Account Number is required");

        Rule manual = rule("E2-MANUAL-CHARACTERS", "2", "account-number",
                "Non-TransArmor manually entered account identifiers use alphanumeric bytes.",
                "Non-TransArmor manual-entry request fixtures", "up to 24 alphanumeric");
        fixture(manual, "ALPHANUMERIC", "SYNTHETICACCOUNT01", "", entry("manual"), "PASS", "");
        fixture(manual, "PUNCTUATION", "INVALID!ACCOUNT", "", entry("manual"), "FAIL", "must be alphanumeric");

        Rule length = rule("E2-REQUEST-LENGTH", "2", "account-number",
                "Non-TransArmor request Element 2 does not exceed 24 bytes.",
                "Non-TransArmor initial or subsequent request; not response or encrypted data",
                "up to 24 alphanumeric");
        fixture(length, "24", "A".repeat(24), "", controls().put("validateNonTransArmorRequestAccountLength", true), "PASS", "");
        fixture(length, "25", "A".repeat(25), "", controls().put("validateNonTransArmorRequestAccountLength", true), "FAIL", "24-byte");

        for (String mode : List.of("track1", "track2")) {
            String separator = "track1".equals(mode) ? "^" : "=";
            String suffix = "track1".equals(mode) ? "SYNTHETIC/TEST^2612101" : "261210100000";
            String track = SYNTHETIC_ACCOUNT + separator + suffix;
            Rule accountSplit = rule("E2-" + mode.toUpperCase(java.util.Locale.ROOT) + "-SPLIT", "2",
                    "account-number-entry-method", "Element 2 preserves only the source " + mode + " account portion.",
                    "Non-TransArmor " + mode + " fixture; Track 1 is credit-card context; sentinels excluded",
                    "track1".equals(mode) ? "data before the first ^ character" : "data before the = character");
            fixture(accountSplit, "MATCH", SYNTHETIC_ACCOUNT, suffix, track(mode, track), "PASS", "");
            fixture(accountSplit, "WRONG-ACCOUNT", "0000000000000001", suffix, track(mode, track), "FAIL", "Element 2 must contain only");
            fixture(accountSplit, "WRONG-DELIMITER", SYNTHETIC_ACCOUNT, suffix,
                    track(mode, SYNTHETIC_ACCOUNT + ":" + suffix.replace('^', ':')), "FAIL", "declared separator");

            Rule discretionarySplit = rule("E12-" + mode.toUpperCase(java.util.Locale.ROOT) + "-SPLIT", "12",
                    "card-discretionary-block-data", "Element 12 preserves the source " + mode + " data after its separator.",
                    "Non-TransArmor " + mode + " fixture; Track 1 is credit-card context; sentinels excluded",
                    "track1".equals(mode) ? "data after the first ^ character" : "data after the = character");
            fixture(discretionarySplit, "MATCH", SYNTHETIC_ACCOUNT, suffix, track(mode, track), "PASS", "");
            fixture(discretionarySplit, "MISSING-SUFFIX", SYNTHETIC_ACCOUNT, "", track(mode, track), "FAIL", "Element 12 must contain");
        }

        Rule discretionaryLength = rule("E12-MAX-LENGTH", "12", "card-discretionary-block-data",
                "A supplied Element 12 value does not exceed 51 bytes.", "Element 12 maximum-length predicate only",
                "Character Type: AN Maximum Length: 51 bytes");
        fixture(discretionaryLength, "51", SYNTHETIC_ACCOUNT, "A".repeat(51), discretionary(), "PASS", "");
        fixture(discretionaryLength, "52", SYNTHETIC_ACCOUNT, "A".repeat(52), discretionary(), "FAIL", "51-byte");

        Rule manualExpiration = rule("E12-MANUAL-EXPIRATION", "12", "card-discretionary-block-data",
                "For a manually entered card, Element 12 begins with expiration in MMYY format, including expired cards.",
                "Manually entered card authorization; not a non-card account or YYMM follow-on representation",
                "expiration date is in MMYY format");
        fixture(manualExpiration, "MMYY", SYNTHETIC_ACCOUNT, "1226", discretionary().put("manualCardExpiration", true), "PASS", "");
        fixture(manualExpiration, "EXPIRED", SYNTHETIC_ACCOUNT, "0120", discretionary().put("manualCardExpiration", true), "PASS", "");
        fixture(manualExpiration, "INVALID-MONTH", SYNTHETIC_ACCOUNT, "1326", discretionary().put("manualCardExpiration", true), "FAIL", "MMYY");
        fixture(manualExpiration, "MISSING", SYNTHETIC_ACCOUNT, "", discretionary().put("manualCardExpiration", true), "FAIL", "MMYY");

        Rule skipped = rule("E12-SKIPPED-CARD-TYPES", "12", "card-discretionary-block-data",
                "Element 12 is skipped for Money Code/Check 093 and Comdata Express Code/Check 095/096 requests.",
                "Financial request with explicitly populated Prompt Code card type 093, 095, or 096",
                "this field should be skipped.");
        for (String cardType : List.of("093", "095", "096")) {
            fixture(skipped, cardType + "-SKIPPED", SYNTHETIC_ACCOUNT, "", discretionary(), "PASS", "")
                    .putObject("promptCode").put("cardType", cardType);
            fixture(skipped, cardType + "-POPULATED", SYNTHETIC_ACCOUNT, "1226", discretionary(), "FAIL", "must be skipped")
                    .putObject("promptCode").put("cardType", cardType);
        }

        Rule transarmor = rule("E12-TRANSARMOR-EXPIRATION", "12", "card-discretionary-block-data",
                "TransArmor processing includes expiration data in Element 12.",
                "Presence predicate only; TransArmor encryption, header, EDATA and expiry decoding are not validated",
                "must be included here.");
        fixture(transarmor, "PRESENT", "SYNTHETICREPRESENTATION", "1226", discretionary().put("entryMode", "transarmor"), "PASS", "");
        fixture(transarmor, "MISSING", "SYNTHETICREPRESENTATION", "", discretionary().put("entryMode", "transarmor"), "FAIL", "TransArmor processing requires");

        Rule networkExpiration = rule("E12-ACCEL-STAR-EXPIRATION", "12", "card-discretionary-block-data",
                "Accel and STAR e-commerce and one-time bill payments require expiration data in Element 12.",
                "Explicit ACCEL or STAR network and ECOMMERCE or ONE_TIME_BILL_PAYMENT context; presence predicate only",
                "commerce and one-time bill payment transactions.");
        for (String network : List.of("ACCEL", "STAR")) {
            for (String payment : List.of("ECOMMERCE", "ONE_TIME_BILL_PAYMENT")) {
                fixture(networkExpiration, network + "-" + payment + "-PRESENT", SYNTHETIC_ACCOUNT, "1226",
                        discretionary().put("network", network).put("paymentContext", payment), "PASS", "");
                fixture(networkExpiration, network + "-" + payment + "-MISSING", SYNTHETIC_ACCOUNT, "",
                        discretionary().put("network", network).put("paymentContext", payment), "FAIL", "requires expiration data");
            }
        }
        Rule cof = rule("E12-ACCEL-STAR-COF", "12", "card-discretionary-block-data",
                "Expiration is optional for applicable Accel or STAR credentials-on-file transactions from May 1, 2025.",
                "Post-May-2025 merchant internal database COF recurring fixture, not one-time bill payment or e-commerce",
                "expiration date is optional for Accel and STAR");
        for (String network : List.of("ACCEL", "STAR")) {
            fixture(cof, network + "-OMITTED", SYNTHETIC_ACCOUNT, "",
                    discretionary().put("network", network).put("paymentContext", "COF_RECURRING"), "PASS", "");
            fixture(cof, network + "-SUPPLIED", SYNTHETIC_ACCOUNT, "1226",
                    discretionary().put("network", network).put("paymentContext", "COF_RECURRING"), "PASS", "");
        }
        return output;
    }

    private Rule rule(String suffix, String element, String ruleName, String statement, String applicability, String quote) {
        String id = "BR-SEG100-" + suffix;
        String scenarioId = "TS-SEG100-" + suffix;
        ObjectNode anchor = mapper.createObjectNode().put("specification", "ATL105").put("version", "2026-3")
                .put("section", "13.2").put("segment", "100").put("element", element).put("rule", ruleName);
        ObjectNode requirement = requirements.addObject().put("id", id).put("title", statement).put("statement", statement)
                .put("atomicity", "ATOMIC").put("category", "field").put("applicability", applicability)
                .put("priority", "HIGH").put("executionStatus", "EXECUTION_READY");
        requirement.putArray("sourceAnchors").add(anchor);
        requirement.putArray("sourceEvidence").addObject().put("sourcePath", "docs/specs/extracted_text.txt")
                .put("sha256", SOURCE_SHA256).put("locator", "Chapter 13, Element " + element)
                .put("quote", quote);
        ObjectNode scenario = scenarios.addObject().put("id", scenarioId).put("intent", statement)
                .put("status", "EXECUTION_READY");
        scenario.putArray("preconditions").add("Synthetic local-predicate fixture; no wire or host certification.").add(applicability);
        scenario.putArray("requirementIds").add(id);
        scenario.putArray("sourceAnchors").add(anchor);
        return new Rule(id, scenarioId, element, anchor);
    }

    private ObjectNode fixture(Rule rule, String variant, String account, String discretionary,
                               ObjectNode controls, String outcome, String errorContains) {
        String suffix = rule.id().substring("BR-".length()) + "-" + variant;
        ObjectNode testCase = cases.addObject().put("id", "TC-" + suffix).put("expectedOutcome", outcome)
                .put("category", "field").put("priority", "high").put("status", "EXECUTION_READY")
                .put("testDataFile", FILE_NAME);
        testCase.putArray("scenarioIds").add(rule.scenarioId());
        testCase.putArray("sourceAnchors").add(rule.anchor());
        testCase.putArray("tags").add("segment-100").add("element-" + rule.element()).add("synthetic").add("local-predicate");
        ObjectNode assertion = testCase.putArray("assertions").addObject().put("requirementId", rule.id())
                .put("assertion", "Apply the source-bound predicate to the declared fixture context: " + variant)
                .put("expectedResult", outcome);
        assertion.putArray("sourceAnchors").add(rule.anchor());
        ObjectNode fixture = data.addObject().put("id", "TD-" + suffix).put("fileName", FILE_NAME)
                .put("readiness", "EXECUTABLE").put("expectedValidation", outcome);
        fixture.putArray("testCaseIds").add("TC-" + suffix);
        fixture.putArray("sourceAnchors").add(rule.anchor());
        ObjectNode payload = fixture.putObject("payload");
        ObjectNode standard = payload.putObject("request").putObject("dataSection2").putObject("standardSegment")
                .put("accountNumber", account).put("cardDiscretionaryBlockData", discretionary);
        payload.set("testControls", controls);
        fixture.putObject("response").put("scope", "LOCAL_PREDICATE_EXPECTATION")
                .put("localValidation", outcome).put("errorContext", "2".equals(rule.element()) ? "AccountNumber" : "CardDiscretionaryBlockData")
                .put("errorContains", errorContains).put("hostExecution", "NOT_EXECUTED");
        return standard;
    }

    private ObjectNode controls() { return mapper.createObjectNode(); }
        private static String sha256(Path file) throws Exception {
                return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(file)));
        }
    private ObjectNode entry(String mode) { return controls().put("validateAccountNumberEntry", true).put("entryMode", mode); }
    private ObjectNode discretionary() { return controls().put("validateCardDiscretionaryBlockData", true); }
    private ObjectNode track(String mode, String track) { return entry(mode).put("rawTrackData", track); }
}