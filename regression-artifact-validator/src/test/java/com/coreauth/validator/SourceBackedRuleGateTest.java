package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105SourceBackedRuleGate;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SourceBackedRuleGateTest {
    @TempDir Path pack;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void derivesOnlyCuratedSourceMatchedReviewCases() throws Exception {
        copyInputs();
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("catalogRuleCount").asInt()).isEqualTo(601);
        assertThat(report.path("summary").path("citationResolvedRuleCount").asInt()).isEqualTo(600);
        assertThat(report.path("summary").path("bodyLocatedRuleCount").asInt()).isEqualTo(553);
        assertThat(report.path("summary").path("partiallyLocatedRuleCount").asInt()).isEqualTo(14);
        assertThat(report.path("summary").path("bodyUnlocatedRuleCount").asInt()).isEqualTo(34);
        assertThat(report.path("summary").path("assertionSourceBackedRuleCount").asInt()).isEqualTo(8);
        assertThat(report.path("summary").path("partiallyBackedRuleCount").asInt()).isEqualTo(2);
        assertThat(report.path("summary").path("assertionsSourceBacked").asInt()).isEqualTo(21);
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isEqualTo(42);
        assertThat(report.path("summary").path("smeApprovedRuleCount").asInt()).isZero();
        assertThat(report.path("assertions").get(0).path("sourceLine").asInt()).isPositive();
        assertThat(report.path("assertions").get(0).path("sourceFingerprintSha256").asText()).hasSize(64);
        assertThat(report.path("draftCases").get(0).path("executionReady").asBoolean()).isFalse();
        assertThat(report.path("rules")).hasSize(601);
        assertThat(ruleStatus(report, "SEG100-R-063")).isEqualTo("SOURCE_BACKED_ASSERTIONS_REVIEW_REQUIRED");
        assertThat(ruleStatus(report, "SEG100-R-001")).isEqualTo("ASSERTION_NOT_CURATED_REVIEW_REQUIRED");
        assertThat(ruleStatus(report, "SEG118-R-027")).isEqualTo("PARTIAL_RULE_ASSERTIONS_REVIEW_REQUIRED");
        assertThat(ruleStatus(report, "SEG118-R-028")).isEqualTo("PARTIAL_RULE_ASSERTIONS_REVIEW_REQUIRED");
        assertThat(assertion(report, "SEG118-R-027", "SITE_CONFIGURATION_FINAL_APPROVED").path("contextLine").asInt()).isGreaterThan(13000);
        assertThat(assertion(report, "SEG118-R-028", "TOTALS_HOST_DISCOUNT_PENDING_DECLINED").path("contextFingerprintSha256").asText()).hasSize(64);
        assertThat(report.path("draftCases").findValuesAsText("status")).contains("PARTIAL_SOURCE_DERIVED_REVIEW_REQUIRED");
        JsonNode requestRule = rule(report, "SEG100-R-001");
        assertThat(requestRule.path("sourceLocatorStatus").asText()).isEqualTo("BODY_LOCATED_REFERENCE_ONLY");
        assertThat(requestRule.path("sourceLocations").get(0).path("sourceLine").asInt()).isGreaterThan(7000);
        assertThat(requestRule.path("sourceLocations").get(0).path("sourceHeading").asText()).isEqualTo("11.1.1 Request");
        String queue = Files.readString(pack.resolve("test-output/test-solution-independent-review/atl105-source-backed-rule-review-queue.csv"));
        assertThat(queue.lines().count()).isEqualTo(602);
        assertThat(queue).contains("sourceLocatorStatus,sourceLines,assertionStatus");
        assertThat(queue).contains("BODY_UNLOCATED_REVIEW_REQUIRED");
    }

    @Test
    void changedQuoteRemovesItsCasesAndRequiresReview() throws Exception {
        copyInputs();
        Path manifest = pack.resolve("contract/element-83-source-assertions.json");
        ObjectNode assertions = (ObjectNode) mapper.readTree(manifest.toFile());
        ((ObjectNode) assertions.path("assertions").get(0)).put("sourcePhrase", "0 Approved for every transaction");
        mapper.writeValue(manifest.toFile(), assertions);
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("assertionsSourceMismatch").asInt()).isEqualTo(1);
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isEqualTo(40);
        assertThat(report.path("assertions").get(0).path("status").asText()).isEqualTo("SOURCE_MISMATCH_REVIEW_REQUIRED");
        assertThat(ruleStatus(report, "SEG100-R-063")).isEqualTo("ASSERTION_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void quotedRowCannotValidateDifferentCodeOrOutcome() throws Exception {
        copyInputs();
        Path manifest = pack.resolve("contract/element-83-source-assertions.json");
        ObjectNode assertions = (ObjectNode) mapper.readTree(manifest.toFile());
        ((ObjectNode) assertions.path("assertions").get(0)).put("code", "Z");
        ((ObjectNode) assertions.path("assertions").get(1)).put("expected", "DECLINED");
        mapper.writeValue(manifest.toFile(), assertions);
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("assertionsSourceMismatch").asInt()).isEqualTo(2);
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isEqualTo(38);
    }

    @Test
    void contextQuoteOutsideCitedSectionCannotSupportHostDiscountTrigger() throws Exception {
        copyInputs();
        Path source = pack.resolve("docs/specs/extracted_text.txt");
        String specification = Files.readString(source);
        String original = "A Prompt Code value of 904 is used when the Response Code in a Totals Response indicates";
        assertThat(specification).contains(original);
        Files.writeString(source, specification.replace(original,
            "A Prompt Code value of 904 is used when a different indicator in a Totals Response indicates") + "\n" + original
            + " that proprietary load data is pending. The device then sends a Proprietary Data Load Request, using a Prompt Code value of 904.\n");
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("assertionsSourceMismatch").asInt()).isEqualTo(4);
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isEqualTo(34);
        assertThat(ruleStatus(report, "SEG118-R-028")).isEqualTo("ASSERTION_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void missingSiteConfigurationContextBlocksOnlyItsDraftCases() throws Exception {
        copyInputs();
        Path manifest = pack.resolve("contract/element-83-source-assertions.json");
        ObjectNode assertions = (ObjectNode) mapper.readTree(manifest.toFile());
        ((ObjectNode) assertions.path("assertions").get(15)).put("contextPhrase", "Not present in site configuration");
        mapper.writeValue(manifest.toFile(), assertions);
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("summary").path("assertionsSourceMismatch").asInt()).isEqualTo(1);
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isEqualTo(40);
        assertThat(ruleStatus(report, "SEG118-R-027")).isEqualTo("ASSERTION_MISMATCH_REVIEW_REQUIRED");
    }

    @Test
    void changedElementLengthBlocksAllDraftCases() throws Exception {
        copyInputs();
        Path source = pack.resolve("docs/specs/extracted_text.txt");
        String spec = Files.readString(source);
        int start = spec.indexOf("Number: 83 Name: Response Code");
        int end = spec.indexOf("Number: 84 Name: Segment Length", start);
        String definition = spec.substring(start, end);
        assertThat(definition).contains("Maximum Length: 1 byte");
        Files.writeString(source, spec.substring(0, start) + definition.replace("Maximum Length: 1 byte", "Maximum Length: 2 bytes") + spec.substring(end));
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("shapeEvidenceVerified").asBoolean()).isFalse();
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isZero();
        assertThat(report.path("summary").path("assertionsSourceMismatch").asInt()).isEqualTo(21);
    }

    @Test
    void missingShapeEvidenceBlocksCaseDerivation() throws Exception {
        copyInputs();
        Path manifest = pack.resolve("contract/element-83-source-assertions.json");
        ObjectNode assertions = (ObjectNode) mapper.readTree(manifest.toFile());
        assertions.remove("shapeEvidence");
        mapper.writeValue(manifest.toFile(), assertions);
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        assertThat(report.path("shapeEvidenceVerified").asBoolean()).isFalse();
        assertThat(report.path("summary").path("draftCaseCount").asInt()).isZero();
    }

    @Test
    void changedBodyHeadingRemovesLocationButNotCitationStatus() throws Exception {
        copyInputs();
        Path source = pack.resolve("docs/specs/extracted_text.txt");
        String spec = Files.readString(source);
        Pattern heading = Pattern.compile("(?m)^11\\.1\\.1 Request[ \\t]*\\r?$");
        assertThat(heading.matcher(spec).find()).isTrue();
        Files.writeString(source, heading.matcher(spec).replaceFirst("Altered Request"));
        GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()});
        JsonNode report = report();
        JsonNode rule = rule(report, "SEG100-R-001");
        assertThat(rule.path("citationResolved").asBoolean()).isTrue();
        assertThat(rule.path("sourceLocatorStatus").asText()).isEqualTo("BODY_UNLOCATED_REVIEW_REQUIRED");
        assertThat(rule.path("status").asText()).isEqualTo("ASSERTION_NOT_CURATED_REVIEW_REQUIRED");
        assertThat(report.path("summary").path("assertionSourceBackedRuleCount").asInt()).isEqualTo(8);
    }

    @Test
    void versionMismatchFailsBeforePublishingCases() throws Exception {
        copyInputs();
        Path manifest = pack.resolve("contract/element-83-source-assertions.json");
        ObjectNode assertions = (ObjectNode) mapper.readTree(manifest.toFile());
        assertions.put("specificationVersion", "unsupported-version");
        mapper.writeValue(manifest.toFile(), assertions);
        assertThatThrownBy(() -> GenerateAtl105SourceBackedRuleGate.main(new String[]{pack.toString()}))
            .isInstanceOf(IllegalStateException.class).hasMessageContaining("version");
        assertThat(Files.exists(pack.resolve("test-output/test-solution-independent-review/atl105-source-backed-rule-gate.json"))).isFalse();
    }

    private void copyInputs() throws Exception {
        copy("contract/element-83-source-assertions.json");
        copy("docs/specs/extracted_text.txt");
        copy("test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json");
        copy("test-output/test-solution-independent-review/atl105-br-approval-matrix.json");
    }

    private void copy(String relative) throws Exception {
        Path target = pack.resolve(relative);
        Files.createDirectories(target.getParent());
        Files.copy(Atl105Paths.root().resolve(relative), target);
    }

    private JsonNode report() throws Exception {
        return mapper.readTree(pack.resolve("test-output/test-solution-independent-review/atl105-source-backed-rule-gate.json").toFile());
    }

    private String ruleStatus(JsonNode report, String ruleId) {
        return rule(report, ruleId).path("status").asText();
    }

    private JsonNode rule(JsonNode report, String ruleId) {
        for (JsonNode row : report.path("rules")) if (ruleId.equals(row.path("ruleId").asText())) return row;
        throw new IllegalStateException("Missing rule " + ruleId);
    }

    private JsonNode assertion(JsonNode report, String ruleId, String context) {
        for (JsonNode row : report.path("assertions")) {
            if (ruleId.equals(row.path("ruleId").asText()) && context.equals(row.path("context").asText())) return row;
        }
        throw new IllegalStateException("Missing assertion " + ruleId + ":" + context);
    }
}