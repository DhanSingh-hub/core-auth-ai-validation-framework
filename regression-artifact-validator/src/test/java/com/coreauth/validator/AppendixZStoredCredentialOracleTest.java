package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixZStoredCredentialOracle;
import com.coreauth.validator.canonical.AppendixZStoredCredentialOracle.Assessment;
import com.coreauth.validator.canonical.AppendixZStoredCredentialOracle.Evidence;
import com.coreauth.validator.canonical.AppendixZStoredCredentialOracle.Flow;
import com.coreauth.validator.canonical.AppendixZStoredCredentialOracle.Purpose;
import com.coreauth.validator.canonical.AppendixZStoredCredentialOracle.Scenario;
import com.coreauth.validator.canonical.AppendixZStoredCredentialOracle.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppendixZStoredCredentialOracleTest {
    private static final ObjectMapper JSON = new ObjectMapper();
    private final AppendixZStoredCredentialOracle oracle = new AppendixZStoredCredentialOracle();

    private static Evidence evidence(Map<String, Object> overrides) {
        Map<String, Object> values = new HashMap<>(Map.of(
                "originalPosEntryMode", "901", "cardIssuedInIndia", false,
                "cardholderParticipating", false, "priorConsent", true,
                "amountDue", true, "thirdPartyProvider", false));
        values.putAll(overrides);
        return JSON.convertValue(values, Evidence.class);
    }

    private static Scenario scenario(String brand, Flow flow, Purpose purpose, Map<String, String> fields,
                                     Map<String, Object> evidence) {
        return new Scenario(brand, flow, purpose, fields,
                Map.of("028", "ORIGINALVISA001", "012", "DISCOVERREF0001"),
                Map.of(), Map.of(), evidence(evidence));
    }

    private Map<String, Assessment> assess(Scenario s) {
        return oracle.assess(s).stream().collect(Collectors.toMap(Assessment::businessRequirementId, a -> a));
    }

    private Status status(Scenario s, String rule) {
        return assess(s).get("BR-SEG100-APPZ-" + rule).status();
    }

    private static Map<String, String> mitFields(String payment) {
        Map<String, String> fields = new HashMap<>(Map.of(
                "032/08", "C", "049/02", "M", "005", "101", "044", "ORIGINALVISA001",
                "015", "DISCOVERREF0001", "045", "000000004500"));
        if (payment != null) fields.put("013", payment);
        return fields;
    }

    @Test
    void validatesInitialAmountDueAndOptionalZeroDollarVerificationWithoutInventingEntryModes() {
        Scenario initial = scenario("Visa", Flow.INITIAL_STORAGE, Purpose.ADHOC,
                Map.of("032/08", "I", "005", "901"), Map.of());
        assertThat(status(initial, "INITIAL-STORAGE")).isEqualTo(Status.PASS);
        Scenario zero = scenario("Visa", Flow.INITIAL_STORAGE, Purpose.ADHOC,
                Map.of("032/08", "I", "005", "901", "accountVerificationAmount", "000000000000"),
                Map.of("amountDue", false, "accountVerification", true));
        assertThat(status(zero, "INITIAL-STORAGE")).isEqualTo(Status.PASS);
        assertThat(status(scenario("Visa", Flow.INITIAL_STORAGE, Purpose.ADHOC,
                Map.of("032/08", "C", "005", "901"), Map.of()), "INITIAL-STORAGE")).isEqualTo(Status.FAIL);
        assertThat(status(scenario("Visa", Flow.INITIAL_STORAGE, Purpose.ADHOC,
                Map.of("032/08", "I", "005", "021"), Map.of()), "INITIAL-STORAGE")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksAllMitProfilesAcrossAllFourBrandsAndMutationOfEachRequiredField() {
        for (String brand : new String[]{"Visa", "MasterCard", "Discover", "Amex"}) {
            for (Flow flow : new Flow[]{Flow.MIT_RECURRING, Flow.MIT_INSTALLMENT, Flow.MIT_UNSCHEDULED}) {
                String payment = flow == Flow.MIT_RECURRING ? "R" : flow == Flow.MIT_INSTALLMENT ? "I" : null;
                Purpose purpose = flow == Flow.MIT_RECURRING ? Purpose.SUBSCRIPTION
                        : flow == Flow.MIT_INSTALLMENT ? Purpose.INSTALLMENT : Purpose.ADHOC;
                String rule = flow.name().replace('_', '-');
                Map<String, String> fields = mitFields(payment);
                assertThat(status(scenario(brand, flow, purpose, fields, Map.of()), rule)).isEqualTo(Status.PASS);
                for (String key : new String[]{"032/08", "049/02", "005"}) {
                    Map<String, String> mutated = new HashMap<>(fields);
                    mutated.remove(key);
                    assertThat(status(scenario(brand, flow, purpose, mutated, Map.of()), rule))
                            .as("%s %s missing %s", brand, flow, key).isEqualTo(Status.FAIL);
                }
            }
        }
        Map<String, String> wrongPayment = mitFields("R");
        assertThat(status(scenario("Visa", Flow.MIT_UNSCHEDULED, Purpose.ADHOC, wrongPayment, Map.of()),
                "MIT-UNSCHEDULED")).isEqualTo(Status.FAIL);
        assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.INSTALLMENT, mitFields("R"), Map.of()),
                "SCOPE")).isEqualTo(Status.FAIL);
    }

    @Test
    void distinguishesInitialCitAndSubsequentCitIncludingOptionalCvv2() {
        for (Flow flow : new Flow[]{Flow.CIT_RECURRING_INITIAL, Flow.CIT_INSTALLMENT_INITIAL}) {
            String payment = flow == Flow.CIT_RECURRING_INITIAL ? "R" : "I";
            Purpose purpose = flow == Flow.CIT_RECURRING_INITIAL ? Purpose.SUBSCRIPTION : Purpose.INSTALLMENT;
            Map<String, String> fields = new HashMap<>(Map.of("032/08", "I", "049/02", "C", "005", "901", "013", payment));
            assertThat(status(scenario("Amex", flow, purpose, fields, Map.of()), "CIT-INITIAL")).isEqualTo(Status.PASS);
            fields.put("004", "1234");
            assertThat(status(scenario("Amex", flow, purpose, fields, Map.of()), "CVV2")).isEqualTo(Status.FAIL);
        }
        Map<String, String> fields = Map.of("032/08", "C", "049/02", "C", "005", "101", "004", "123");
        Scenario subsequent = scenario("Visa", Flow.CIT_SUBSEQUENT, Purpose.ADHOC, fields, Map.of());
        assertThat(status(subsequent, "CIT-SUBSEQUENT")).isEqualTo(Status.PASS);
        assertThat(status(subsequent, "CVV2")).isEqualTo(Status.PASS);
    }

    @Test
    void detectsProhibitedCvv2AndConsentContradictionsWithoutCertifyingEnrollment() {
        Map<String, String> fields = mitFields("R");
        fields.put("004", "123");
        Scenario s = scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, fields, Map.of());
        assertThat(status(s, "CVV2")).isEqualTo(Status.FAIL);
        assertThat(status(s, "CONSENT")).isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, fields,
                Map.of("cardholderParticipating", true)), "CONSENT")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksNetworkReferenceAndDiscoverApprovedAmountCarryForward() {
        Map<String, String> fields = mitFields("R");
        Scenario valid = scenario("Discover", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, fields,
                Map.of("firstApprovedAmountMinorUnits", "4500"));
        assertThat(status(valid, "REFERENCES")).isEqualTo(Status.PASS);
        assertThat(status(valid, "DISCOVER-AMOUNT")).isEqualTo(Status.PASS);
        fields.put("045", "000000004501");
        fields.put("015", "ALTEREDREF000001");
        Scenario altered = scenario("Discover", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, fields,
                Map.of("firstApprovedAmountMinorUnits", "4500"));
        assertThat(status(altered, "REFERENCES")).isEqualTo(Status.FAIL);
        assertThat(status(altered, "DISCOVER-AMOUNT")).isEqualTo(Status.FAIL);
        fields.put("044", "LATERREFERENCE1");
        assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, fields, Map.of()),
                "REFERENCES")).isEqualTo(Status.REVIEW_REQUIRED);
        fields.remove("044");
        assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, fields, Map.of()),
                "REFERENCES")).isEqualTo(Status.FAIL);
    }

    @Test
    void gatesDiscoverIndiaAtStrictlyAbove52UsdAndDoesNotGuessCurrencyConversion() {
        for (String amount : new String[]{"51.99", "52.00", "52.01"}) {
            Scenario s = scenario("Discover", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, mitFields("R"),
                    Map.of("cardIssuedInIndia", true, "amountUsdEquivalent", new BigDecimal(amount)));
            assertThat(status(s, "DISCOVER-INDIA"))
                    .isEqualTo("52.01".equals(amount) ? Status.REVIEW_REQUIRED : Status.PASS);
        }
        assertThat(status(scenario("Discover", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, mitFields("R"),
                Map.of("cardIssuedInIndia", true)), "DISCOVER-INDIA")).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void checksDiscoverInstallmentProviderDescriptorLocationAndSic() {
        Map<String, String> fields = mitFields("I");
        fields.putAll(Map.of("056/01", "12", "056/08", "02", "049/13", "T",
                "047", "PROVIDER*RETAILER", "057", "CITY", "058", "NY", "059", "USA", "007", "5411"));
        Map<String, Object> evidence = Map.of("thirdPartyProvider", true, "providerName", "PROVIDER",
                "retailerName", "RETAILER", "retailerCity", "CITY", "retailerState", "NY",
                "retailerCountry", "USA", "retailerSic", "5411");
        assertThat(status(scenario("Discover", Flow.MIT_INSTALLMENT, Purpose.INSTALLMENT, fields, evidence),
                "DISCOVER-INSTALLMENT")).isEqualTo(Status.REVIEW_REQUIRED);
        Scenario descending = new Scenario("Discover", Flow.MIT_INSTALLMENT, Purpose.INSTALLMENT,
                fields, Map.of(), Map.of("056/08", "03"), Map.of(), evidence(evidence));
        assertThat(status(descending, "DISCOVER-INSTALLMENT")).isEqualTo(Status.FAIL);
        fields.put("047", "PROVIDER");
        assertThat(status(scenario("Discover", Flow.MIT_INSTALLMENT, Purpose.INSTALLMENT, fields, evidence),
                "DISCOVER-INSTALLMENT")).isEqualTo(Status.FAIL);
        fields.put("047", "PROVIDER*RETAILER");
        fields.remove("057");
        assertThat(status(scenario("Discover", Flow.MIT_INSTALLMENT, Purpose.INSTALLMENT, fields, evidence),
                "DISCOVER-INSTALLMENT")).isEqualTo(Status.FAIL);
        fields.remove("056/08");
        assertThat(status(scenario("Discover", Flow.MIT_INSTALLMENT, Purpose.INSTALLMENT, fields, evidence),
                "DISCOVER-INSTALLMENT")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksDiscoverDelayedSaleAndNoShowIndicators() {
        Map<String, String> fields = mitFields(null);
        fields.put("016", "D");
        fields.put("017", "N");
        assertThat(status(scenario("Discover", Flow.MIT_UNSCHEDULED, Purpose.DELAYED_SALE, fields, Map.of()),
                "DISCOVER-SPECIAL")).isEqualTo(Status.PASS);
        assertThat(status(scenario("Discover", Flow.MIT_UNSCHEDULED, Purpose.NO_SHOW, fields, Map.of()),
                "DISCOVER-SPECIAL")).isEqualTo(Status.PASS);
        fields.put("017", "X");
        assertThat(status(scenario("Discover", Flow.MIT_UNSCHEDULED, Purpose.NO_SHOW, fields, Map.of()),
                "DISCOVER-SPECIAL")).isEqualTo(Status.FAIL);
    }

    @Test
    void checksAllEightMastercardCategoryCodes() {
        for (boolean mit : new boolean[]{false, true}) {
            for (Purpose purpose : new Purpose[]{Purpose.ADHOC, Purpose.STANDING_ORDER, Purpose.SUBSCRIPTION, Purpose.INSTALLMENT}) {
                String suffix = switch (purpose) {
                    case ADHOC -> "101";
                    case STANDING_ORDER -> "102";
                    case SUBSCRIPTION -> "103";
                    case INSTALLMENT -> "104";
                    default -> throw new IllegalStateException();
                };
                Flow flow = mit ? switch (purpose) {
                    case ADHOC -> Flow.MIT_UNSCHEDULED;
                    case INSTALLMENT -> Flow.MIT_INSTALLMENT;
                    default -> Flow.MIT_RECURRING;
                } : Flow.INITIAL_STORAGE;
                Map<String, String> fields = Map.of("056/09", (mit ? "M" : "C") + suffix);
                assertThat(status(scenario("MasterCard", flow, purpose, fields, Map.of()), "MASTERCARD-CATEGORY"))
                        .isEqualTo(Status.PASS);
                assertThat(status(scenario("MasterCard", flow, purpose,
                        Map.of("056/09", (mit ? "C" : "M") + suffix), Map.of()), "MASTERCARD-CATEGORY"))
                        .isEqualTo(Status.FAIL);
            }
        }
    }

    private static Map<String, String> visaIndiaFields() {
        Map<String, String> fields = mitFields("R");
        fields.putAll(Map.of("056/01", "12", "056/02", "F", "056/03", "000000004500",
                "056/05", "04", "056/06", "1", "056/12", "2", "056/13", "A".repeat(35),
                "030/terminalType", "25"));
        return fields;
    }

    @Test
    void checksVisaIndia15000InrBoundaryAndParameterCarryForward() {
        Map<String, String> fields = visaIndiaFields();
        for (String amount : new String[]{"14999.99", "15000", "15000.01"}) {
            boolean auth = "15000.01".equals(amount);
            Evidence evidence = evidence(Map.of("cardIssuedInIndia", true,
                    "amountInrEquivalent", new BigDecimal(amount), "ecommerce", auth,
                    "cavvPresent", auth, "registrationSuccessful", true));
            Scenario s = new Scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, fields,
                    Map.of(), fields, Map.of(), evidence);
            assertThat(status(s, "VISA-INDIA")).isEqualTo(Status.PASS);
        }
        Map<String, String> changed = new HashMap<>(fields);
        changed.put("056/03", "000000005000");
        Evidence evidence = evidence(Map.of("cardIssuedInIndia", true, "amountInrEquivalent", 100,
                "ecommerce", false, "cavvPresent", false, "registrationSuccessful", true));
        assertThat(status(new Scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, changed,
                Map.of(), fields, Map.of(), evidence), "VISA-INDIA")).isEqualTo(Status.FAIL);
        assertThat(status(new Scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, changed,
                Map.of(), fields, Map.of("056/03", "000000005000"), evidence), "VISA-INDIA")).isEqualTo(Status.PASS);
    }

    @Test
    void checksVisaRegistrationModificationAndCancellationAuthentication() {
        for (String action : new String[]{"1", "3", "4"}) {
            Map<String, String> fields = visaIndiaFields();
            fields.put("056/12", action);
            fields.put("accountVerificationAmount", "000000000000");
            Flow flow = "1".equals(action) ? Flow.CIT_RECURRING_INITIAL : Flow.MIT_RECURRING;
            Evidence evidence = evidence(Map.of("cardIssuedInIndia", true, "ecommerce", true,
                    "cavvPresent", true, "accountVerification", true));
            Scenario s = new Scenario("Visa", flow, Purpose.SUBSCRIPTION, fields, Map.of(), fields, Map.of(), evidence);
            assertThat(status(s, "VISA-INDIA")).isEqualTo(Status.PASS);
            fields.remove("030/terminalType");
            assertThat(status(new Scenario("Visa", flow, Purpose.SUBSCRIPTION, fields,
                    Map.of(), Map.of(), Map.of(), evidence), "VISA-INDIA")).isEqualTo(Status.FAIL);
        }
    }

    @Test
    void validatesTable56ValueSetsWidthsAndBrandApplicability() {
        for (Map<String, String> invalid : List.of(
                Map.of("056/03", "123"), Map.of("056/05", "13"), Map.of("056/06", "2"),
                Map.of("056/12", "5"), Map.of("056/01", "00"), Map.of("056/04", "A".repeat(14)))) {
            assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION, invalid, Map.of()),
                    "TABLE56-FORMAT")).isEqualTo(Status.FAIL);
        }
        assertThat(status(scenario("Discover", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION,
                Map.of("056/01", "UD"), Map.of()), "TABLE56-FORMAT")).isEqualTo(Status.PASS);
        assertThat(status(scenario("Discover", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION,
                Map.of("056/05", "09"), Map.of()), "TABLE56-FORMAT")).isEqualTo(Status.FAIL);
        assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION,
                Map.of("056/24", "A", "056/03", "bad"), Map.of()), "TABLE56-FORMAT")).isEqualTo(Status.FAIL);
        assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION,
                Map.of("056/03", "000000004500"), Map.of()), "TABLE56-FORMAT")).isEqualTo(Status.FAIL);
        assertThat(status(scenario("Visa", Flow.MIT_RECURRING, Purpose.SUBSCRIPTION,
                Map.of("056/03", "000000004500"), Map.of("cardIssuedInIndia", true)),
                "TABLE56-FORMAT")).isEqualTo(Status.PASS);
    }

    @Test
    void executesEveryCoverageFixtureAndChecksCompleteRequirementTraceability() throws Exception {
        JsonNode coverage = JSON.readTree(Atl105Paths.testJson()
                .resolve("appendices").resolve("appendix-z-segment-100-coverage.json").toFile());
        assertThat(coverage.path("businessRequirements").size()).isEqualTo(AppendixZStoredCredentialOracle.RULES.size());
        var ids = coverage.path("businessRequirements").findValuesAsText("id");
        for (String rule : AppendixZStoredCredentialOracle.RULES) assertThat(ids).contains("BR-SEG100-APPZ-" + rule);
        int executed = 0;
        for (JsonNode fixture : coverage.path("testData")) {
            ObjectNode payload = coverage.path("fixtureDefaults").deepCopy();
            payload.setAll((ObjectNode) fixture.path("payload"));
            Scenario s = JSON.treeToValue(payload, Scenario.class);
            Map<String, Assessment> results = assess(s);
            String rule = fixture.path("expected").path("businessRequirementId").asText();
            assertThat(results.get(rule)).as(fixture.path("id").asText()).isNotNull();
            assertThat(results.get(rule).status().name()).as(fixture.path("id").asText())
                    .isEqualTo(fixture.path("expected").path("status").asText());
            executed++;
        }
        assertThat(executed).isEqualTo(coverage.path("testData").size()).isGreaterThanOrEqualTo(10);
    }
}
