package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter11MessageLayoutValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Segment119ObservationValidatorTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Chapter12SegmentPayloadValidator validator = new Chapter12SegmentPayloadValidator();

    private ObjectNode fixture() throws IOException {
        for (var row : mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()).path("observations")) {
            if ("119".equals(row.path("payload").path("segment").asText())) return row.path("payload").deepCopy();
        }
        throw new IOException("Missing Segment 119 fixture");
    }

    private static ObjectNode fields(ObjectNode input) { return (ObjectNode) input.path("elements"); }
    private static ObjectNode context(ObjectNode input) { return (ObjectNode) input.path("context"); }
    private void target(ObjectNode input, String rule, String scope, Status expected) throws IOException {
        var findings = validator.validate(input).findings().stream()
                .filter(f -> f.ruleId().equals("SEG119-R-" + rule) && f.scope().equals(scope)).toList();
        assertThat(findings).hasSize(1);
        assertThat(findings.getFirst().status()).isEqualTo(expected);
    }

    @Test void measuresActualWireAndRetainsGenuineSourceBoundaries() throws Exception {
        var input = fixture();
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
        target(input, "009", "wire-measured-length", Status.CHECKS_PASSED);
        target(input, "007", "wire-buckets", Status.CHECKS_PASSED);
        target(input, "038", "source-length-conflict", Status.REVIEW_REQUIRED);
        target(input, "039", "source-wire-conflict", Status.REVIEW_REQUIRED);
        var original = input.deepCopy();
        validator.validate(input);
        assertThat(input).isEqualTo(original);
        input.put("serializedSegment", input.path("serializedSegment").asText() + "\u001c");
        target(input, "009", "wire-measured-length", Status.INVALID);
        input = fixture();
        context(input).remove("wireProfile");
        target(input, "007", "wire-profile", Status.REVIEW_REQUIRED);
        input = fixture();
        input.put("serializedSegment", input.path("serializedSegment").asText().replace("\u001c\u001c\u001c", "\u001c"));
        target(input, "006", "wire-empty-fields", Status.INVALID);
    }

    @Test void rejectsMissingRequiredFieldsAndNontextWithoutNumericCoercion() throws Exception {
        for (String id : List.of("85", "84", "44", "102", "78", "105", "43", "96", "39", "86", "176", "179", "42")) {
            var input = fixture();
            input.remove("serializedSegment");
            fields(input).remove(id);
            assertThat(validator.validate(input).status()).as("missing " + id).isEqualTo(Status.INVALID);
            fields(input).put(id, 1);
            assertThat(validator.validate(input).status()).as("nontext " + id).isEqualTo(Status.INVALID);
        }
    }

    @Test void timestampSequenceAndConditionalControlsAreMeasured() throws Exception {
        for (String timestamp : List.of("202602291930", "202613011930", "202610092460", "000010091930", "20261009193")) {
            var input = fixture();
            fields(input).put("179", timestamp);
            target(input, "021", "discount-timestamp", Status.INVALID);
        }
        var input = fixture();
        fields(input).put("179", "202402292359");
        target(input, "021", "discount-timestamp", Status.CHECKS_PASSED);
        fields(input).put("86", "000000");
        target(input, "019", "sequence", Status.INVALID);
        fields(input).put("86", "999999");
        target(input, "019", "sequence", Status.CHECKS_PASSED);
        context(input).put("passwordRequired", true);
        target(input, "014", "conditional-requiredness", Status.INVALID);
        fields(input).put("65", "000001");
        target(input, "014", "conditional-requiredness", Status.CHECKS_PASSED);
        context(input).put("employeeNumberRequired", "false");
        target(input, "013", "conditional-requiredness", Status.INVALID);
        input.putArray("context");
        target(input, "002", "context-shape", Status.INVALID);
    }

    @Test void requestDatesAndCurrencyReuseExistingSourceOracles() throws Exception {
        for (String date : List.of("111111", "222222", "333333", "444444", "555555", "999999", "022924")) {
            var input = fixture();
            fields(input).put("105", date);
            target(input, "015", "totals-date", Status.CHECKS_PASSED);
        }
        var input = fixture();
        fields(input).put("105", "023126");
        target(input, "015", "totals-date", Status.INVALID);
        fields(input).put("105", "022900");
        target(input, "015", "century", Status.REVIEW_REQUIRED);
        fields(input).put("20", "999");
        target(input, "022", "currency", Status.INVALID);
        fields(input).remove("20");
        assertThat(validator.validate(input).findings()).noneMatch(f -> f.ruleId().equals("SEG119-R-022"));
    }

    @Test void bucketsRequireWidthsSourceOrderAndNoDuplicates() throws Exception {
        Map<String, String> mutations = Map.of("13", "CC", "16", "00000", "15", "-0000100");
        for (var mutation : mutations.entrySet()) {
            var input = fixture();
            ((ObjectNode) input.path("cardBuckets").get(0)).put(mutation.getKey(), mutation.getValue());
            assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        }
        var input = fixture();
        input.withArray("cardBuckets").add(input.path("cardBuckets").get(0).deepCopy());
        assertThat(validator.validate(input).findings()).anyMatch(f -> f.scope().equals("bucket-order") && f.status() == Status.INVALID);
        input = fixture();
        input.putArray("cardBuckets");
        target(input, "027", "bucket-count", Status.INVALID);
        for (int i = 0; i < 21; i++) input.withArray("cardBuckets").addObject().put("13", "CC  ").put("16", "00001").put("15", "00000100");
        target(input, "027", "bucket-count", Status.INVALID);
    }

    @Test void nonfinancialTotalsAreExcludedOnlyWithCompleteObservedBucketScope() throws Exception {
        var input = fixture();
        input.withArray("cardBuckets").addObject().put("13", "AO  ").put("16", "00001").put("15", "00000500");
        input.withArray("cardBuckets").addObject().put("13", "HD  ").put("16", "00001").put("15", "00000700");
        target(input, "030", "grand-total-exclusion", Status.CHECKS_PASSED);
        fields(input).put("42", "00001300");
        target(input, "030", "grand-total-exclusion", Status.INVALID);
        context(input).put("bucketCoverageComplete", false);
        target(input, "030", "grand-total-exclusion", Status.REVIEW_REQUIRED);
        context(input).put("bucketCoverageComplete", "true");
        target(input, "030", "grand-total-exclusion", Status.INVALID);
    }

    @Test void parentFamilyDirectionAndPlacementAreAuthoritative() throws Exception {
        var child = fixture();
        child.put("messageFamily", "Financial Transaction Response").put("direction", "RESPONSE").put("dataSection", 2);
        var parent = mapper.createObjectNode().put("specificationVersion", "2026-3")
                .put("messageFamily", "Totals with Proprietary Data Load Request").put("completeness", "PARTIAL");
        parent.putObject("elements").put("55", "ATL105").put("63", "01");
        parent.putArray("segments").addObject().put("segment", "119").put("dataSection", 3).put("fieldNumber", 3).set("observation", child);
        var before = parent.deepCopy();
        var result = new Chapter11MessageLayoutValidator().validate(parent);
        assertThat(result.findings()).anyMatch(f -> f.scope().equals("segment-context") && f.status() == Status.INVALID);
        assertThat(result.findings()).anyMatch(f -> f.ruleId().equals("SEG119-R-021") && f.status() == Status.CHECKS_PASSED);
        assertThat(parent).isEqualTo(before);
    }

    @Test void declaredLengthAndAllNineteenListedLabelsRespectExactBoundaries() throws Exception {
        for (String length : List.of("001", "389", "390", "493")) {
            var input = fixture();
            input.remove("serializedSegment");
            fields(input).put("84", length);
            target(input, "004", "length-cap", Status.CHECKS_PASSED);
            target(input, "038", "source-length-conflict", Status.REVIEW_REQUIRED);
        }
        var input = fixture();
        fields(input).put("84", "494");
        target(input, "004", "length-cap", Status.INVALID);
        fields(input).put("84", "000");
        target(input, "009", "length-width", Status.INVALID);
        var labels = List.of("CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR", "CK", "EF", "EC",
                "SV1", "SV2", "SV3", "SV4", "ECA", "EWIC", "EK", "HD");
        input = fixture();
        fields(input).put("84", "453").put("42", "00001400");
        var buckets = input.putArray("cardBuckets");
        StringBuilder encoded = new StringBuilder();
        for (String label : labels) {
            String padded = label + " ".repeat(4 - label.length());
            buckets.addObject().put("13", padded).put("16", "00001").put("15", "00000100");
            encoded.append(padded).append("00001").append("00000100");
        }
        StringBuilder wire = new StringBuilder();
        for (String id : List.of("85", "84", "44", "102", "78", "32", "65", "105", "43", "96", "39", "86", "176", "179", "20", "42")) {
            wire.append(fields(input).path(id).asText()).append('\u001c');
        }
        wire.append(encoded).append('\u001c');
        assertThat(wire.length()).isEqualTo(453);
        input.put("serializedSegment", wire.toString());
        target(input, "009", "wire-measured-length", Status.CHECKS_PASSED);
        target(input, "030", "grand-total-exclusion", Status.CHECKS_PASSED);
        assertThat(validator.validate(input).findings()).noneMatch(f -> f.status() == Status.INVALID);
        var first = buckets.get(0).deepCopy();
        buckets.set(0, buckets.get(1).deepCopy());
        buckets.set(1, first);
        assertThat(validator.validate(input).findings()).anyMatch(f -> f.scope().equals("bucket-order") && f.status() == Status.INVALID);
    }
}
