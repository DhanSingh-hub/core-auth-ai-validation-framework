package com.coreauth.validator;

import com.coreauth.validator.canonical.ProductActualPayloadAdapter;
import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Atl105AiElementIntake;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductActualPayloadAdapterTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ProductActualPayloadAdapter adapter = new ProductActualPayloadAdapter();

    private ObjectNode payload(String id) throws Exception {
        for (var fixture : mapper.readTree(Atl105Paths.testJson("chapter-12-segment-observations.json").toFile()).path("productExecutions")) {
            if (id.equals(fixture.path("payload").path("segment").asText())) return fixture.path("payload").path("productPayload").deepCopy();
        }
        throw new IllegalStateException("Missing actual product fixture " + id);
    }

    @Test void aliasesAndFlatFieldsAreMappedWithoutMutatingProducerInput() throws Exception {
        for (String id : new String[]{"102", "157"}) {
            var input = payload(id);
            var before = input.deepCopy();
            assertThat(adapter.validatePayload(input, id).errors()).isEmpty();
            assertThat(input).isEqualTo(before);
        }
    }

    @Test void ambiguousRepresentationsAndContradictoryAliasesAreInvalid() throws Exception {
        var input = payload("102");
        var segment = (ObjectNode) input.path("Product Code Data Segment");
        segment.put("NumberOfProducts", "02");
        assertThat(adapter.validatePayload(input, "102").errors()).anyMatch(e -> e.reason().contains("Conflicting actual aliases"));
        segment.remove("NumberOfProducts");
        segment.putArray("Products").addObject().put("ProductCode", "001").put("UnitOfMeasure", "G")
                .put("Quantity", "205").put("UnitPrice", "3005").put("ProductAmount", "210");
        assertThat(adapter.validatePayload(input, "102").errors()).anyMatch(e -> e.reason().contains("ambiguous"));
        segment.remove("Products");
        segment.put("UnitOfMeasure", "L");
        assertThat(adapter.validatePayload(input, "102").errors()).anyMatch(e -> e.reason().contains("Conflicting actual aliases"));
    }

    @Test void evidenceCannotReplaceActualRequiredFieldsOrOverrideActualCardType() throws Exception {
        var input = payload("102");
        var segment = (ObjectNode) input.path("Product Code Data Segment");
        segment.remove("SegmentLength");
        input.putObject("context").put("SegmentLength", "028");
        assertThat(adapter.validatePayload(input, "102").errors()).anyMatch(e -> e.reason().contains("SEG102-R-023"));
        input = payload("157");
        input.putObject("context").put("comdata", false);
        assertThat(adapter.validatePayload(input, "157").errors()).anyMatch(e -> e.reason().contains("contradicts actual card"));
        input.putArray("context");
        assertThat(adapter.validatePayload(input, "157").errors()).anyMatch(e -> e.reason().contains("context must be an object"));
    }

    @Test void semanticIntakeExecutesEveryActualProductOccurrenceIncluding157WithoutMetadataDeclarations() throws Exception {
        var root = payload("102");
        ((ObjectNode) root.path("Standard Segment")).put("SegmentType", "100");
        var second = root.path("Product Code Data Segment").deepCopy();
        ((ObjectNode) second).put("Quantity", "25");
        root.set("Second Product", second);
        root.set("Adjusted Product Code Data Segment", payload("157").path("Adjusted Product Code Data Segment"));
        var input = mapper.createObjectNode();
        input.set("Financial Transaction Request", root);
        var before = input.deepCopy();
        var meta = mapper.createObjectNode().put("testCaseId", "TC-PRODUCT-ACTUAL").put("messageFamily", "Financial Transaction Request");
        var assessment = new Atl105AiElementIntake(Atl105Paths.root(), true).assess(meta, input);
        assertThat(assessment.intakeFindings()).filteredOn(f -> f.ruleId().equals("SEG102-R-018")).hasSize(2);
        assertThat(assessment.intakeFindings()).anyMatch(f -> f.ruleId().equals("SEG102-R-012") && f.status().name().equals("INVALID"))
                .anyMatch(f -> f.ruleId().equals("SEG157-R-002") && f.status().name().equals("INVALID"));
        assertThat(input).isEqualTo(before);
    }
}
