package com.coreauth.validator;

import com.coreauth.validator.canonical.Chapter11MessageLayoutValidator;
import com.coreauth.validator.canonical.Chapter12SegmentPayloadValidator.Status;
import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter11MessageLayoutValidatorTest {
    private final ObjectMapper mapper = new ObjectMapper();

    private ObjectNode financial(String family) {
        ObjectNode input = mapper.createObjectNode().put("specificationVersion", "2026-3").put("messageFamily", family);
        input.putObject("elements").put("55", "ATL105").put("63", "01");
        input.putArray("segments").addObject().put("segment", "100").put("dataSection", 2);
        return input;
    }

    @Test
    void financialCountsAreOccurrencesNotTemplateAlternativeCount() throws Exception {
        var validator = new Chapter11MessageLayoutValidator();
        ObjectNode input = financial("Financial Transaction Request");
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
        ((ObjectNode) input.path("elements")).put("63", "14");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        ((ObjectNode) input.path("elements")).put("63", "02");
        input.withArray("segments").addObject().put("segment", "105").put("dataSection", 3);
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void emvRequires130AndHonorsSixTotalSlots() throws Exception {
        var validator = new Chapter11MessageLayoutValidator();
        ObjectNode input = financial("EMV Financial Transaction Request");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        input.withArray("segments").addObject().put("segment", "130").put("dataSection", 3);
        ((ObjectNode) input.path("elements")).put("63", "02");
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
        for (int i = 0; i < 5; i++) input.withArray("segments").addObject().put("segment", "111").put("dataSection", 3);
        ((ObjectNode) input.path("elements")).put("63", "07");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void aliasesReuseRequiredResponseFieldsAndExternalLayoutsNeverPass() throws Exception {
        var validator = new Chapter11MessageLayoutValidator();
        ObjectNode input = financial("Loyalty Card Transaction Response");
        input.putArray("segments");
        input.putObject("elements");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        input.put("messageFamily", "TransArmor Key and Key ID Load Request");
        assertThat(validator.validate(input).status()).isEqualTo(Status.REVIEW_REQUIRED);
        input.put("messageFamily", "unknown");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void everyCatalogFamilyDispatchesPartialInputsWithoutInventingMissingFields() throws Exception {
        var validator = new Chapter11MessageLayoutValidator();
        var templates = mapper.readTree(Atl105Paths.docs().resolve("atl105_complete_templates.json").toFile()).path("message_templates");
        var families = templates.fieldNames();
        int count = 0;
        while (families.hasNext()) {
            String family = families.next();
            ObjectNode input = financial(family).put("completeness", "PARTIAL");
            input.putObject("elements");
            input.putArray("segments");
            assertThat(validator.validate(input).status()).as(family).isEqualTo(Status.REVIEW_REQUIRED);
            count++;
        }
        assertThat(count).isEqualTo(31);
    }

    @Test
    void segmentObservationIsWiredAndMismatchedIdentityIsRejected() throws Exception {
        var validator = new Chapter11MessageLayoutValidator();
        ObjectNode input = financial("Financial Transaction Request");
        var segment = input.withArray("segments").addObject().put("segment", "156").put("dataSection", 3);
        ObjectNode observation = segment.putObject("observation").put("specificationVersion", "2026-3").put("segment", "156");
        observation.putObject("elements").put("85", "999").put("84", "006");
        observation.putArray("tables");
        ((ObjectNode) input.path("elements")).put("63", "02");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
        observation.put("segment", "153");
        assertThat(validator.validate(input).status()).isEqualTo(Status.INVALID);
    }

    @Test
    void processingHistoryIsInheritedAndChildCannotOverrideIt() throws Exception {
        var validator = new Chapter11MessageLayoutValidator();
        ObjectNode input = financial("Phone Load Response").put("completeness", "PARTIAL").put("chapter13Semantics", true);
        input.putArray("segments");
        var child = input.withArray("segments").addObject().put("segment", "DL1").put("dataSection", 2).putObject("observation");
        child.putObject("elements").put("98", "********");
        var actions = input.putObject("history").putObject("storeNumberActions").put("displayed", true).put("printed", false);
        var saved = input.deepCopy();
        assertThat(validator.validate(input).findings().toString()).contains("MASKED-ACTIONS", "INVALID");
        assertThat(input).isEqualTo(saved);
        actions.put("displayed", false);
        child.putObject("history").putObject("storeNumberActions").put("displayed", true).put("printed", false);
        assertThat(validator.validate(input).findings().toString()).contains("Child history contradicts parent");
    }
}
