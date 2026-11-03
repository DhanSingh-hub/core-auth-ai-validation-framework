package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter12ActualPayloadAdapter;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.coverage.Chapter12SourceLocalFixtures;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter12StandardDownloadIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @TestFactory Stream<DynamicTest> actualIntakeExecutesStandardSourcePresencePlacementAndPromptPredicates() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        return fixtures().stream().filter(f -> f.path("id").asText().startsWith("STANDARD12-") && f.path("payload").has("actualPayload"))
                .map(f -> DynamicTest.dynamicTest(f.path("id").asText() + "-INTAKE", () -> {
                    JsonNode payload = f.path("payload");
                    var root = mapper.createObjectNode();
                    root.putObject(payload.path("messageFamily").asText()).put("MessageType", "ATL105")
                            .put("NumSegments", "01").set("Observed Standard", payload.path("actualPayload").deepCopy());
                    ObjectNode metadata = payload.path("actualEvidence").deepCopy();
                    metadata.put("messageFamily", payload.path("messageFamily").asText());
                    var before = root.deepCopy();
                    var metadataBefore = metadata.deepCopy();
                    var adapter = new Chapter12ActualPayloadAdapter(new Chapter13DataElementValidator(Atl105Paths.root()));
                    String predicateRule = f.path("targetRuleId").asText(f.path("ruleId").asText());
                    assertThat(adapter.validate(payload.path("actualPayload"), "100", payload.path("messageFamily").asText(),
                            payload.path("actualEvidence")).findings()).anyMatch(finding ->
                            finding.ruleId().equals(predicateRule)
                                    && finding.scope().equals(f.path("targetScope").asText())
                                    && finding.status().name().equals(f.path("expectedTargetStatus").asText()));
                    if (f.path("id").asText().equals("STANDARD12-REQUIRED85-MISSING")) {
                        assertThat(intake.assess(metadata, root).intakeFindings()).anyMatch(finding ->
                                finding.status().name().equals("REVIEW_REQUIRED")
                                        && finding.reason().contains("no explicit textual source identity"));
                    } else if (f.path("id").asText().equals("STANDARD12-REQUIRED85-EMPTY")) {
                        assertThat(intake.assess(metadata, root).intakeFindings()).anyMatch(finding ->
                                finding.status().name().equals("INVALID")
                                        && finding.reason().contains("malformed observed source identity"));
                    } else if (f.path("id").asText().equals("STANDARD12-TYPE-WRONG")) {
                        assertThat(intake.assess(metadata, root).intakeFindings()).noneMatch(finding ->
                                finding.ruleId().equals("SEG100-R-015"));
                    } else {
                        assertThat(intake.assess(metadata, root).intakeFindings()).anyMatch(finding ->
                                finding.ruleId().equals(predicateRule)
                                        && finding.status().name().equals(f.path("expectedTargetStatus").asText()));
                    }

                    assertThat(root).isEqualTo(before);
                    assertThat(metadata).isEqualTo(metadataBefore);
                }));
    }

    @Test void missingActualStandardCannotBeFilledFromMetadataEnvelopeClaim() throws Exception {
        var root = mapper.createObjectNode();
        root.putObject("Financial Transaction Request").put("MessageType", "ATL105").put("NumSegments", "00");
        var metadata = mapper.createObjectNode().put("messageFamily", "Financial Transaction Request");
        metadata.putObject("segments").putObject("Standard").put("SegmentType", "100");
        assertThat(new Atl105AiElementIntake(Atl105Paths.root(), true).assess(metadata, root).intakeFindings())
                .anyMatch(f -> f.status().name().equals("REVIEW_REQUIRED") && f.reason().contains("Required Segment 100 absent"));
    }

    private List<JsonNode> fixtures() throws Exception {
        return Chapter12SourceLocalFixtures.expand(mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()),
                mapper.readTree(Atl105Paths.testJson("chapter-12-source-local-probes.json").toFile()));
    }

    @TestFactory Stream<DynamicTest> actualIntakeExecutesObservedStandardAndAllSixDownloadWiresWithoutMutation() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        return fixtures().stream().filter(f -> f.path("id").asText().matches("ACTUAL-WIRE-(100|DL[2-7])-VALID"))
                .map(f -> DynamicTest.dynamicTest(f.path("id").asText(), () -> {
                    JsonNode payload = f.path("payload");
                    var root = mapper.createObjectNode();
                    var body = root.putObject(payload.path("messageFamily").asText());
                    if ("100".equals(payload.path("segment").asText())) body.put("MessageType", "ATL105").put("NumSegments", "01");
                    body.set("Observed block", payload.path("actualPayload").deepCopy());
                    if (payload.has("actualMerchant")) body.set("Observed merchant", payload.path("actualMerchant").deepCopy());
                    ObjectNode meta = payload.path("actualEvidence").deepCopy();
                    meta.put("messageFamily", payload.path("messageFamily").asText());
                    var before = root.deepCopy();
                    var metadataBefore = meta.deepCopy();
                    assertThat(intake.assess(meta, root).intakeFindings()).anyMatch(finding ->
                            finding.ruleId().equals(f.path("ruleId").asText()) && finding.status().name().equals("CHECKS_PASSED")
                                    && (finding.reason().contains("concatenate") || finding.reason().contains("retains FS")));
                    assertThat(root).isEqualTo(before);
                    assertThat(meta).isEqualTo(metadataBefore);
                }));
    }

    @TestFactory Stream<DynamicTest> malformedObservedDownloadScalarsStayInvalidInsteadOfCoercing() throws Exception {
        var adapter = new Chapter12ActualPayloadAdapter(new Chapter13DataElementValidator(Atl105Paths.root()));
        return fixtures().stream().filter(f -> f.path("id").asText().matches("ACTUAL-WIRE-(100|DL[2-7])-VALID"))
                .map(f -> DynamicTest.dynamicTest(f.path("id").asText() + "-NONTEXT", () -> {
                    ObjectNode payload = f.path("payload").deepCopy();
                    ObjectNode actual = (ObjectNode) payload.path("actualPayload");
                    String id = payload.path("segment").asText();
                    if ("DL2".equals(id)) ((ObjectNode) actual.path("Primary")).put("RedialCount", 1);
                    else actual.put(switch (id) {
                        case "100" -> "SequenceNumber";
                        case "DL3" -> "CurrentDate";
                        case "DL4", "DL5" -> "SoftwareLoadType";
                        case "DL6" -> "StartTime";
                        default -> "DownloadData";
                    }, 1);
                    assertThat(adapter.validate(actual, id, payload.path("messageFamily").asText(), payload.path("actualEvidence"),
                            null, null, payload.get("actualMerchant")).status()).isEqualTo(Status.INVALID);
                }));
    }

    @Test void actualMerchantCardInventoryCannotComeFromMetadataOrContraryAliases() throws Exception {
        var profile = fixtures().stream().filter(f -> "ACTUAL-WIRE-DL6-VALID".equals(f.path("id").asText()))
                .findFirst().orElseThrow().path("payload");
        var adapter = new Chapter12ActualPayloadAdapter(new Chapter13DataElementValidator(Atl105Paths.root()));
        var meta = mapper.createObjectNode();
        meta.putObject("context").putArray("merchantCardTypes").add("173");
        assertThat(adapter.validate(profile.path("actualPayload"), "DL6", "Table Load Response", meta).findings())
                .anyMatch(f -> f.scope().equals("merchant-card173") && f.status() == Status.REVIEW_REQUIRED);
        meta.withObject("/context").putArray("merchantCardTypes").add("001");
        assertThat(adapter.validate(profile.path("actualPayload"), "DL6", "Table Load Response", meta, null, null,
                profile.path("actualMerchant")).status()).isEqualTo(Status.INVALID);
    }

    @Test void sourceConflictsAndUnspecifiedDailyWindowConventionsAreNotPassed() throws Exception {
        var profile = fixtures().stream().filter(f -> "ACTUAL-WIRE-DL6-VALID".equals(f.path("id").asText()))
                .findFirst().orElseThrow().path("payload").deepCopy();
        var actual = (ObjectNode) profile.path("actualPayload");
        actual.put("StartTime", "2300").put("EndTime", "0100").put("SerializedSegment", "\\23000100~");
        var meta = mapper.createObjectNode();
        meta.putObject("context").putArray("storeForwardObservations").addObject().put("localTime", "0030").put("blocked", true);
        var result = new Chapter12ActualPayloadAdapter().validate(actual, "DL6", "Table Load Response", meta,
                null, null, profile.get("actualMerchant"));
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("download-cap") && f.status() == Status.REVIEW_REQUIRED)
                .anyMatch(f -> f.scope().equals("store-forward-window") && f.status() == Status.REVIEW_REQUIRED);
    }

    @Test void legacyDefaultIntakeDoesNotActivateNewActualWireSemantics() throws Exception {
        var root = mapper.createObjectNode();
        root.putObject("Phone Load Response").putObject("Observed dial").put("DataTypeIndicator", "!").put("SerializedSegment", "WRONG");
        assertThat(new Atl105AiElementIntake(Atl105Paths.root()).assess(mapper.createObjectNode(), root).intakeFindings())
                .noneMatch(f -> f.ruleId().equals("SEGDL2-R-004"));
    }

    @TestFactory Stream<DynamicTest> actualIntakeExecutesSourceNonfuelFirstDiscountDependencies() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        return fixtures().stream().filter(f -> f.path("id").asText().startsWith("PRODUCT-DISCOUNT-"))
                .map(f -> DynamicTest.dynamicTest(f.path("id").asText() + "-INTAKE", () -> {
                    JsonNode source = f.path("payload").path("productPayload");
                    var root = mapper.createObjectNode();
                    var request = root.putObject("Financial Transaction Request").put("MessageType", "ATL105").put("NumSegments", "02");
                    request.set("Product", source.path("Product Code Data Segment").deepCopy());
                    ObjectNode standard = source.path("Standard Segment").deepCopy();
                    standard.put("SegmentType", "100");
                    request.set("Standard", standard);
                    var metadata = mapper.createObjectNode().put("messageFamily", "Financial Transaction Request");
                    metadata.set("context", source.path("context").deepCopy());
                    var before = root.deepCopy();
                    var result = intake.assess(metadata, root);
                    if ("INVALID".equals(f.path("expectedStatus").asText())) assertThat(result.intakeFindings())
                            .anyMatch(finding -> finding.ruleId().equals("SEG102-R-026") && finding.status().name().equals("INVALID"));
                    else assertThat(result.intakeFindings()).noneMatch(finding ->
                            finding.ruleId().equals("SEG102-R-026") && finding.status().name().equals("INVALID"));
                    assertThat(root).isEqualTo(before);
                }));
    }

    @TestFactory Stream<DynamicTest> actualIntakeExecutesProprietaryRequestResponseWireAndBodyBoundaries() throws Exception {
        var intake = new Atl105AiElementIntake(Atl105Paths.root(), true);
        return fixtures().stream().filter(f -> f.path("id").asText().startsWith("ACTUAL118-")
                        && !"actual-load-echo".equals(f.path("targetScope").asText()))
                .map(f -> DynamicTest.dynamicTest(f.path("id").asText() + "-INTAKE", () -> {
                    JsonNode payload = f.path("payload");
                    var root = mapper.createObjectNode();
                    root.set(payload.path("messageFamily").asText(), payload.path("actualMessage").deepCopy());
                    var before = root.deepCopy();
                    var result = intake.assess(mapper.createObjectNode().put("messageFamily", payload.path("messageFamily").asText()), root);
                    assertThat(result.intakeFindings()).anyMatch(finding ->
                            finding.ruleId().equals(f.path("targetRuleId").asText(f.path("ruleId").asText()))
                                    && finding.status().name().equals(f.path("expectedTargetStatus").asText()));
                    assertThat(root).isEqualTo(before);
                }));
    }
}
