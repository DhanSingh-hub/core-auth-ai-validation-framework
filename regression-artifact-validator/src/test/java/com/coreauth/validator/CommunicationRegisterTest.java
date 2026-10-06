package com.coreauth.validator;

import com.coreauth.validator.register.CommunicationRegisterValidator;
import com.coreauth.validator.register.GenerateCommunicationRegisterViews;
import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CommunicationRegisterTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void registerIsValidAndMatchesEveryCatalogProvisionalItem() throws IOException {
        ValidationResult result = new CommunicationRegisterValidator()
                .validate(register(), GenerateCommunicationRegisterViews.KNOWLEDGE_BASE);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void generatedViewsAreUpToDate() throws IOException {
        Map<Path, String> views = new GenerateCommunicationRegisterViews().render(register());

        assertThat(views).isNotEmpty();
        for (Map.Entry<Path, String> view : views.entrySet()) {
            assertThat(view.getKey()).as("run GenerateCommunicationRegisterViews").exists();
            String onDisk = Files.readString(view.getKey(), StandardCharsets.UTF_8).replace("\r\n", "\n");
            assertThat(onDisk).as("%s is stale: run GenerateCommunicationRegisterViews", view.getKey()).isEqualTo(view.getValue());
        }
    }

    @Test
    void rejectsDuplicateIdsAndUnknownRelatedItems() throws IOException {
        ObjectNode register = (ObjectNode) register();
        ArrayNode items = (ArrayNode) register.path("items");
        items.add(items.get(0).deepCopy());
        ((ObjectNode) items.get(1)).putArray("relatedItems").add("TT-9999");

        ValidationResult result = new CommunicationRegisterValidator().validate(register, null);

        assertThat(result.errors()).anyMatch(e -> e.reason().contains("is duplicated"));
        assertThat(result.errors()).anyMatch(e -> e.reason().contains("unknown item TT-9999"));
    }

    @Test
    void rejectsResolvedItemWithoutResolutionAndBadIdPattern() throws IOException {
        ObjectNode register = (ObjectNode) register();
        ObjectNode item = (ObjectNode) register.path("items").get(0);
        item.put("status", "RESOLVED");
        item.remove("resolution");
        item.put("id", "TT-1");

        ValidationResult result = new CommunicationRegisterValidator().validate(register, null);

        assertThat(result.errors()).anyMatch(e -> e.reason().contains("lacks resolution"));
        assertThat(result.errors()).anyMatch(e -> e.reason().contains("id pattern"));
    }

    @Test
    void rejectsCatalogThatRepeatsQuestionOrDisagreesOnStatus(@TempDir Path kb) throws IOException {
        JsonNode register = MAPPER.readTree("""
                {"registerId":"R","channels":{"SME_QUERY":{"idPattern":"^SEG[0-9A-Z]+-SME-[0-9]{3}$"}},
                 "statuses":["OPEN","RESOLVED"],"segments":{"999":{}},
                 "items":[{"id":"SEG999-SME-001","channel":"SME_QUERY","segments":["999"],"subject":"Q?",
                           "status":"OPEN","owner":"SME/TBA","raisedOn":"2026-09-29","raisedBy":"t",
                           "links":{"catalogItem":"P-01"}}]}""");
        Path coverage = Files.createDirectories(kb.resolve("segment-999").resolve("coverage"));
        Files.writeString(coverage.resolve("segment-999-rule-catalog.json"), """
                {"segment":"999","rules":[],"provisionalItems":[
                  {"id":"P-01","status":"RESOLVED","impacts":[],"question":"Q?","registerId":"SEG999-SME-001"}]}""");

        ValidationResult result = new CommunicationRegisterValidator().validate(register, kb);

        assertThat(result.errors()).anyMatch(e -> e.reason().contains("differs from SEG999-SME-001"));
        assertThat(result.errors()).anyMatch(e -> e.reason().contains("must not repeat the question"));
    }

    @Test
    void crossSegmentCatalogDependencyRequiresExplicitRelatedCatalogLink() throws IOException {
        ObjectNode register = (ObjectNode) register();
        for (JsonNode value : register.path("items")) {
            if ("SEGDL1-SME-003".equals(value.path("id").asText())) {
                ((ObjectNode) value.path("links")).remove("relatedCatalogItems");
            }
        }

        ValidationResult result = new CommunicationRegisterValidator().validate(
                register, GenerateCommunicationRegisterViews.KNOWLEDGE_BASE);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("segment-DL6-rule-catalog.json P-06"));
    }

    private static JsonNode register() throws IOException {
        return MAPPER.readTree(GenerateCommunicationRegisterViews.REGISTER.toFile());
    }
}
