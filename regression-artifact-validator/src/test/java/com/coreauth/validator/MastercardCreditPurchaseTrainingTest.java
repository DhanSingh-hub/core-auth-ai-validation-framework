package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Training gate for the Mastercard Credit Purchase transaction context
 * (scripts/train-mastercard-credit-purchase.py). Proves the trained package is canonical, built only from
 * verbatim Test Solution records, consistent with the business FR matrix, and reported honestly in
 * training-status.json, the training report and the BR coverage report.
 */
class MastercardCreditPurchaseTrainingTest {
    private static final String CONTEXT = "MASTERCARD_CREDIT_PURCHASE";
    private static final Path PACKAGE = Atl105Paths.testJson("mc-credit-purchase-training-package.json");
    private static final Path COVERAGE_JSON = Atl105Paths.testOutput()
            .resolve("test-solution-independent-review/mc-credit-purchase-br-coverage.json");
    private static final Path COVERAGE_MD = Atl105Paths.testOutput()
            .resolve("test-solution-independent-review/mc-credit-purchase-br-coverage.md");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static JsonNode pkg;
    private static JsonNode source;

    @BeforeAll
    static void load() throws Exception {
        pkg = MAPPER.readTree(PACKAGE.toFile());
        source = MAPPER.readTree(Path.of(pkg.path("provenance").path("sourcePackage").asText()).toFile());
    }

    @Test
    void trainedPackageIsCanonicalAndPassesTraceabilityValidation() throws Exception {
        JsonNode manifest = pkg.path("manifest");
        assertThat(manifest.path("specification").asText()).isEqualTo("ATL105");
        assertThat(manifest.path("specificationVersion").asText()).isEqualTo("2026-3");
        assertThat(manifest.path("producer").asText()).isEqualTo("TEST_SOLUTION");
        assertThat(manifest.path("authority").asText()).isEqualTo("INDEPENDENT_TEST_SOLUTION");
        assertThat(manifest.path("aiArtifactsIncluded").asBoolean(true)).isFalse();
        assertThat(manifest.has("scope")).as("a scoped manifest would hide the package from TestSolutionJsonFormatTest").isFalse();

        CanonicalArtifactPackage artifactPackage = MAPPER.copy()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(PACKAGE.toFile(), CanonicalArtifactPackage.class);
        assertThat(new CanonicalTraceabilityValidator().validate(artifactPackage).errors())
                .as("canonical BR -> TS -> TC -> TD traceability errors").isEmpty();
    }

    @Test
    void trainingIsCurrentWithTheTestSolutionSource() throws Exception {
        Path sourcePath = Path.of(pkg.path("provenance").path("sourcePackage").asText());
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(sourcePath));
        assertThat(HexFormat.of().formatHex(digest))
                .as("source package changed; rerun scripts/train-mastercard-credit-purchase.py")
                .isEqualTo(pkg.path("provenance").path("sourcePackageSha256").asText());
    }

    @Test
    void everyTrainedRecordIsAVerbatimTestSolutionRecord() {
        Map<String, JsonNode> brs = index(source.path("businessRequirements"));
        Map<String, JsonNode> tss = index(source.path("testScenarios"));
        Map<String, JsonNode> tcs = index(source.path("testCases"));
        Map<String, JsonNode> tds = index(source.path("testData"));
        Set<String> logged = new HashSet<>();
        Map<String, String> statusFrom = new HashMap<>();
        pkg.path("provenance").path("normalizations").forEach(n -> {
            logged.add(n.path("id").asText() + "#" + n.path("field").asText());
            if ("status".equals(n.path("field").asText())) statusFrom.put(n.path("id").asText(), n.path("from").asText());
        });

        for (JsonNode br : pkg.path("businessRequirements")) {
            assertThat(br).as("BR %s", br.path("id").asText()).isEqualTo(brs.get(br.path("id").asText()));
        }
        for (JsonNode ts : pkg.path("testScenarios")) {
            JsonNode original = required(tss, ts);
            String id = ts.path("id").asText();
            boolean addedSourceStatus = ts.has("sourceStatus") && !original.has("sourceStatus");
            String[] changeable = addedSourceStatus
                    ? new String[] {"requirementIds", "status", "sourceStatus"} : new String[] {"requirementIds", "status"};
            assertThat(strip(ts, changeable)).as("TS %s", id).isEqualTo(strip(original, changeable));
            assertLinksAllowedAndLogged(ts, "requirementIds", texts(original.path("requirementIds")), logged, original);
            if (logged.contains(id + "#status")) {
                assertThat(ts.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
                assertThat(statusFrom.get(id)).isEqualTo(original.path("status").asText());
                if (addedSourceStatus) assertThat(ts.path("sourceStatus")).isEqualTo(original.path("status"));
            } else {
                assertThat(ts.path("status")).as("TS %s status", id).isEqualTo(original.path("status"));
                assertThat(addedSourceStatus).as("TS %s sourceStatus without a logged normalization", id).isFalse();
            }
        }
        for (JsonNode tc : pkg.path("testCases")) {
            JsonNode original = required(tcs, tc);
            assertThat(strip(tc, "scenarioIds")).isEqualTo(strip(original, "scenarioIds"));
            Set<String> allowed = texts(original.path("scenarioIds"));
            if (original.hasNonNull("scenarioId")) allowed.add(original.path("scenarioId").asText());
            assertLinksAllowedAndLogged(tc, "scenarioIds", allowed, logged, original);
        }
        for (JsonNode td : pkg.path("testData")) {
            JsonNode original = required(tds, td);
            assertThat(strip(td, "testCaseIds")).isEqualTo(strip(original, "testCaseIds"));
            Set<String> allowed = texts(original.path("testCaseIds"));
            tcs.values().stream().filter(tc -> texts(tc.path("testDataIds")).contains(td.path("id").asText()))
                    .forEach(tc -> allowed.add(tc.path("id").asText()));
            assertLinksAllowedAndLogged(td, "testCaseIds", allowed, logged, original);
        }
    }

    @Test
    void businessMatrixCoversEveryScopeRequirementAndReviewQueueIsExplicit() {
        JsonNode context = pkg.path("businessContext");
        assertThat(context.path("businessRequirement").path("id").asText()).isEqualTo("BR-01");
        assertThat(context.path("businessRequirement").path("title").asText())
                .isEqualTo("Mastercard credit financial purchase transaction processing");
        assertThat(texts(context.path("functionalRequirements"), "id"))
                .containsExactly("FR-001", "FR-002", "FR-003", "FR-004", "FR-005", "FR-006", "FR-007", "FR-008");

        Set<String> trained = texts(pkg.path("businessRequirements"), "id");
        Set<String> review = texts(pkg.path("reviewQueue"), "id");
        Set<String> trainedTs = texts(pkg.path("testScenarios"), "id");
        assertThat(trained).doesNotContainAnyElementsOf(review);

        Set<String> matrixBrs = new HashSet<>();
        Set<String> scenarioIds = new HashSet<>();
        String previousFr = "";
        for (JsonNode scenario : context.path("scenarios")) {
            String id = scenario.path("id").asText();
            int number = Integer.parseInt(id.substring(3));
            assertThat(scenarioIds.add(id)).as("duplicate business scenario %s", id).isTrue();
            assertThat(scenario.path("origin").asText()).isEqualTo(number <= 37 ? "TEMPLATE" : "TEST_SOLUTION");
            String fr = scenario.path("functionalRequirementId").asText();
            assertThat(fr.compareTo(previousFr)).as("%s must follow FR order", id).isGreaterThanOrEqualTo(0);
            previousFr = fr;
            assertThat(scenario.path("title").asText()).as("%s title", id).isNotBlank();
            assertThat(scenario.path("acceptanceCriteria").asText()).as("%s acceptance criteria", id).isNotBlank();
            assertThat(scenario.path("validations")).as("%s validations", id).isNotEmpty();
            assertThat(trained).containsAll(texts(scenario.path("trainedRequirementIds")));
            assertThat(review).containsAll(texts(scenario.path("reviewRequirementIds")));
            assertThat(trainedTs).containsAll(texts(scenario.path("trainedScenarioIds")));
            if ("NOT_IN_TEST_SOLUTION".equals(scenario.path("coverageStatus").asText())) {
                assertThat(scenario.path("testSolutionRequirementIds")).as("%s", id).isEmpty();
                assertThat(scenario.path("trainingState").asText()).isEqualTo("NO_TEST_SOLUTION_EVIDENCE");
            }
            matrixBrs.addAll(texts(scenario.path("testSolutionRequirementIds")));
        }
        int total = pkg.path("training").path("counts").path("businessScenarios").asInt();
        Set<String> expectedIds = new HashSet<>();
        for (int i = 1; i <= total; i++) expectedIds.add(String.format("TS-%03d", i));
        assertThat(scenarioIds).as("business scenarios must be TS-001..TS-%03d", total).isEqualTo(expectedIds);
        Set<String> scope = new HashSet<>(trained);
        scope.addAll(review);
        assertThat(matrixBrs).isEqualTo(scope);

        for (JsonNode item : pkg.path("reviewQueue")) {
            assertThat(item.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
            assertThat(item.path("reasons")).as("reasons for %s", item.path("id").asText()).isNotEmpty();
            assertThat(item.path("requiredEvidence").asText()).isNotBlank();
        }
        JsonNode merchantName = context.path("merchantEvidence").path("merchantName");
        assertThat(merchantName.path("status").asText()).isEqualTo("NO_EVIDENCE");
        assertThat(merchantName.path("values")).isEmpty();
    }

    @Test
    void trainingStatusReportAndCoverageAgreeWithThePackage() throws Exception {
        JsonNode training = pkg.path("training");
        JsonNode counts = training.path("counts");
        assertThat(counts.path("trainedRequirements").asInt()).isEqualTo(pkg.path("businessRequirements").size());
        assertThat(counts.path("reviewQueue").asInt()).isEqualTo(pkg.path("reviewQueue").size());
        assertThat(counts.path("scopeRequirements").asInt())
                .isEqualTo(pkg.path("businessRequirements").size() + pkg.path("reviewQueue").size());
        assertThat(counts.path("testScenarios").asInt()).isEqualTo(pkg.path("testScenarios").size());
        assertThat(counts.path("testCases").asInt()).isEqualTo(pkg.path("testCases").size());
        assertThat(counts.path("testData").asInt()).isEqualTo(pkg.path("testData").size());

        JsonNode status = MAPPER.readTree(Atl105Paths.root().resolve("training-status.json").toFile());
        JsonNode entry = status.path("transactionContextTraining").path(CONTEXT);
        assertThat(entry.isObject()).as("training-status.json transactionContextTraining.%s", CONTEXT).isTrue();
        assertThat(entry.path("status").asText()).isEqualTo(training.path("status").asText());
        assertThat(entry.path("gates")).isEqualTo(training.path("gates"));
        assertThat(texts(entry.path("gates").fieldNames())).isEqualTo(texts(status.path("requiredGates")));
        assertThat(entry.path("blockers")).isEqualTo(training.path("blockers"));
        assertThat(entry.path("counts")).isEqualTo(counts);
        assertThat(Path.of(entry.path("evidence").asText())).isEqualTo(PACKAGE);
        assertThat(Path.of(entry.path("coverageReport").asText())).isEqualTo(COVERAGE_MD);
        if (!pkg.path("reviewQueue").isEmpty() || texts(training.path("blockers")).contains("sme-tba-certification-pending")) {
            assertThat(entry.path("status").asText())
                    .as("cannot be trained for intake with review items or pending SME/TBA certification")
                    .isNotEqualTo("TRAINED_FOR_INTAKE");
        }

        String report = Files.readString(Atl105Paths.docs().resolve("specs/kb/SEGMENT-TRAINING-EXECUTION-REPORT.md"));
        assertThat(report).contains("| " + CONTEXT + " | " + entry.path("status").asText() + " | "
                + counts.path("trainedRequirements").asInt() + " / " + counts.path("scopeRequirements").asInt() + " |");

        JsonNode coverage = MAPPER.readTree(COVERAGE_JSON.toFile());
        assertThat(coverage.path("counts")).isEqualTo(counts);
        String markdown = Files.readString(COVERAGE_MD);
        assertThat(markdown).contains("| BRs with a canonical chain (trained) | " + counts.path("trainedRequirements").asInt() + " |");
        assertThat(markdown).contains("| BR coverage | " + counts.path("brCoveragePercent").asText() + "% |");
        assertThat(markdown).contains("| BRs in review queue | " + counts.path("reviewQueue").asInt() + " |");
    }

    private static void assertLinksAllowedAndLogged(JsonNode record, String field, Set<String> allowed,
                                                    Set<String> logged, JsonNode original) {
        String id = record.path("id").asText();
        assertThat(record.path(field).isArray()).as("%s.%s must be an array", id, field).isTrue();
        assertThat(allowed).as("%s.%s links must come from the Test Solution source", id, field)
                .containsAll(texts(record.path(field)));
        if (!record.path(field).equals(original.path(field))) {
            assertThat(logged).as("%s.%s normalization must be logged", id, field).contains(id + "#" + field);
        }
    }

    private static JsonNode required(Map<String, JsonNode> sourceRecords, JsonNode record) {
        JsonNode original = sourceRecords.get(record.path("id").asText());
        assertThat(original).as("%s must exist in the Test Solution source", record.path("id").asText()).isNotNull();
        return original;
    }

    private static JsonNode strip(JsonNode node, String... fields) {
        ObjectNode copy = node.deepCopy();
        copy.remove(List.of(fields));
        return copy;
    }

    private static Map<String, JsonNode> index(JsonNode array) {
        Map<String, JsonNode> map = new HashMap<>();
        array.forEach(node -> map.put(node.path("id").asText(), node));
        return map;
    }

    private static Set<String> texts(JsonNode array) {
        Set<String> values = new LinkedHashSet<>();
        array.forEach(node -> values.add(node.asText()));
        return values;
    }

    private static Set<String> texts(JsonNode array, String field) {
        Set<String> values = new LinkedHashSet<>();
        array.forEach(node -> values.add(node.path(field).asText()));
        return values;
    }

    private static Set<String> texts(java.util.Iterator<String> names) {
        List<String> values = new ArrayList<>();
        names.forEachRemaining(values::add);
        return new LinkedHashSet<>(values);
    }
}
