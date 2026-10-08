package com.coreauth.validator;

import com.coreauth.validator.canonical.VisaEstimatedIncrementalAuthorizationValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class VisaEstimatedIncrementalAuthorizationValidatorTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final VisaEstimatedIncrementalAuthorizationValidator validator = new VisaEstimatedIncrementalAuthorizationValidator();

    private ObjectNode flow() throws Exception {
        return (ObjectNode) mapper.readTree(Path.of("src/test/resources/atl105/appendix-ae-logical-flows.json").toFile()).path("baseFlow").deepCopy();
    }

    private ObjectNode step(ObjectNode flow, int index) {
        return (ObjectNode) flow.path("steps").get(index);
    }

    private void fails(ObjectNode flow, String rule) {
        assertThat(validator.validatePayload(flow).errors()).anyMatch(error -> error.reason().contains(rule));
    }

    @Test
    void lodgingIsNotExcludedByTheThreeListedMccRestrictions() throws Exception {
        var result = validator.validatePayload(flow());
        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("not certified"));
    }

    @Test
    void enforcesCnpAndEvRestrictionsWithoutInventingMerchantEligibility() throws Exception {
        for (String mcc : new String[]{"4121", "5411"}) {
            ObjectNode flow = flow().put("mcc", mcc).put("cardPresent", true);
            fails(flow, "AE-CNP");
            flow.put("cardPresent", false);
            assertThat(validator.validatePayload(flow).errors()).isEmpty();
        }
        ObjectNode flow = flow().put("mcc", "5552").put("evTransaction", false);
        fails(flow, "AE-EV");
        flow.put("evTransaction", true);
        assertThat(validator.validatePayload(flow).errors()).isEmpty();
    }

    @Test
    void initialIndicatorAndIncrementalApprovalAreIndependentRequirements() throws Exception {
        ObjectNode flow = flow();
        step(flow, 0).remove("estimatedAuthIndicator");
        fails(flow, "AE-ESTIMATE");
        flow = flow();
        step(flow, 1).put("estimatedAuthIndicator", "0");
        fails(flow, "AE-NO-REESTIMATE");
        flow = flow();
        step(flow, 1).remove("authorizationIdentificationResponse");
        fails(flow, "AE-APPROVAL");
        flow = flow();
        step(flow, 2).put("estimatedAuthIndicator", "1");
        fails(flow, "AE-NO-REESTIMATE");
    }

    @Test
    void incrementalRequiresInitialAndPreservesAllThreeIdentities() throws Exception {
        for (String field : new String[]{"sequenceNumber", "transactionIdentifier", "terminalTransactionNumberTable63"}) {
            ObjectNode flow = flow();
            step(flow, 1).put(field, "different");
            assertThat(validator.validatePayload(flow).errors()).anyMatch(error -> error.reason().contains(field));
        }
        ObjectNode flow = flow();
        ((com.fasterxml.jackson.databind.node.ArrayNode) flow.path("steps")).remove(0);
        fails(flow, "AE-PREDECESSOR");
    }

    @Test
    void expiryUsesOriginalInitialDateNotTheLatestIncremental() throws Exception {
        ObjectNode flow = flow();
        step(flow, 1).put("date", "2026-10-14");
        step(flow, 2).put("date", "2026-10-16");
        fails(flow, "AE-WINDOW");
        flow = flow().put("merchantCategory", "OTHER_RENTAL");
        step(flow, 2).put("date", "2026-10-08");
        assertThat(validator.validatePayload(flow).errors()).isEmpty();
        step(flow, 2).put("date", "2026-10-09");
        fails(flow, "AE-WINDOW");
    }

    @Test
    void longerStayRequiresBothCompletionAndSecondInitiationByOriginalDeadline() throws Exception {
        ObjectNode flow = flow().put("stayExceedsWindow", true);
        fails(flow, "AE-ROLLOVER");
        ((com.fasterxml.jackson.databind.node.ArrayNode) flow.path("steps")).addObject()
            .put("role", "SECOND_INITIAL").put("date", "2026-10-15");
        assertThat(validator.validatePayload(flow).errors()).isEmpty();
        step(flow, 3).put("date", "2026-10-16");
        fails(flow, "AE-WINDOW");
    }

    @Test
    void completionUsesFinalAmountAndUnknownContextsRemainReviewable() throws Exception {
        ObjectNode flow = flow();
        step(flow, 2).put("amount", "400.00");
        fails(flow, "AE-FINAL-AMOUNT");
        flow = flow().put("merchantCategory", "OTHER");
        step(flow, 1).remove("transactionIdentifier");
        var result = validator.validatePayload(flow);
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("AE-TRANSACTION-ID"));
        assertThat(result.warnings()).anyMatch(warning -> warning.contains("AE-WINDOW"));
    }

    @Test
    void trainingSourceRetainsTheMissingSegmentReferenceAndFullRolloverClause() throws Exception {
        String source = Files.readString(Path.of("specifications/ATL105/docs/specs/extracted_text.txt"));
        String appendix = source.substring(source.lastIndexOf("Appendix AE. Visa Estimated and Incremental"));
        assertThat(appendix).contains("Contains the same Transaction Identifier in Segment  as", "complete the first transaction and then initiate the second transaction");
        var draft = mapper.readTree(Path.of("specifications/ATL105/test-output/test-json/appendices/appendix-ae-segment-100-coverage.json").toFile());
        assertThat(draft.path("businessRequirements").get(0).path("title").asText()).doesNotContain("permitted only for MCC");
        var catalog = mapper.readTree(Path.of("specifications/ATL105/docs/specs/kb/appendix-ae-rule-catalog.json").toFile());
        assertThat(catalog.path("rules")).hasSize(13);
        for (var rule : catalog.path("rules")) {
            assertThat(appendix).contains(rule.path("sourceQuote").asText());
            assertThat(rule.path("status").asText()).isEqualTo("REVIEW_REQUIRED");
        }
        var training = mapper.readTree(Path.of("specifications/ATL105/training-status.json").toFile()).path("appendixTraining").path("AE");
        assertThat(training.path("status").asText()).isEqualTo("IN_PROGRESS");
        assertThat(training.path("draftRuleCount").asInt()).isEqualTo(catalog.path("rules").size());
        assertThat(training.path("countsExcludedFromSegmentCatalogSummary").asBoolean()).isTrue();
        assertThat(draft.path("logicalTrainingEvidence").path("logicalFixtureCount").asInt()).isEqualTo(16);
    }

    @Test
    void replaysRuleLinkedLogicalFixturesWithoutGrantingExecutionReadiness() throws Exception {
        var fixtures = mapper.readTree(Path.of("src/test/resources/atl105/appendix-ae-logical-flows.json").toFile());
        assertThat(fixtures.path("executionCertified").asBoolean()).isFalse();
        assertThat(fixtures.path("cases")).hasSize(16);
        for (var testCase : fixtures.path("cases")) {
            ObjectNode observation = (ObjectNode) fixtures.path("baseFlow").deepCopy();
            for (var change : testCase.path("changes")) {
                String pointer = change.path("pointer").asText();
                int split = pointer.lastIndexOf('/');
                var parent = observation.at(pointer.substring(0, split));
                String key = pointer.substring(split + 1);
                if (parent.isArray()) {
                    ((com.fasterxml.jackson.databind.node.ArrayNode) parent).remove(Integer.parseInt(key));
                } else if (change.path("remove").asBoolean()) {
                    ((ObjectNode) parent).remove(key);
                } else {
                    ((ObjectNode) parent).set(key, change.path("value"));
                }
            }
            var result = validator.validatePayload(observation);
            String expected = testCase.path("expectedRule").asText();
            if ("NONE".equals(expected)) assertThat(result.errors()).as(testCase.path("id").asText()).isEmpty();
            else assertThat(result.errors()).as(testCase.path("id").asText()).anyMatch(error -> error.reason().contains(expected));
            assertThat(result.warnings()).isNotEmpty();
        }
    }
}