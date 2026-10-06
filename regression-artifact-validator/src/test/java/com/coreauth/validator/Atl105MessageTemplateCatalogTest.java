package com.coreauth.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105MessageTemplateCatalogTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Path CATALOG_PATH = Path.of("specifications", "ATL105", "docs", "atl105_complete_templates.json");

    @Test
    void catalogEntryCountMatchesExtractedTransactionCountWithoutApprovingCatalog() throws IOException {
        JsonNode catalog = readCatalog();

        assertThat(catalog.path("state").asText()).isEqualTo("EXTRACTED");
        assertThat(catalog.path("human_review_required").asBoolean()).isTrue();
        assertThat(catalog.path("total_transaction_types").asInt())
            .isEqualTo(catalog.path("message_templates").size());
        assertThat(catalog.path("total_transaction_types").asInt()).isEqualTo(31);
    }

    @Test
    void monerisKeyLoadRequestAndResponseTemplatesMatchSourceLayouts() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");
        JsonNode request = templates.path("Moneris Key Load Request");
        JsonNode response = templates.path("Moneris Key Load Response");

        assertThat(request.path("layout").asText()).isEqualTo("POSITIONAL_NO_FIELD_SEPARATORS");
        assertThat(request.path("fields")).hasSize(7);
        assertThat(request.path("fields").get(0).path("fixed_value").asText()).isEqualTo("?");
        assertThat(request.path("fields").get(3).path("fixed_value").asText()).isEqualTo("M");
        assertThat(request.path("fields").get(4).path("length").asInt()).isEqualTo(48);

        assertThat(response.path("fields")).hasSize(8);
        assertThat(response.path("repeating_group").path("maximum_count").asInt()).isEqualTo(3);
        assertThat(response.path("repeating_group").path("maximum_total_length").asInt()).isEqualTo(48);
        assertThat(request.path("spdh_header_fields")).hasSize(11);
        assertThat(response.path("spdh_header_fields")).hasSize(11);
    }

    @Test
    void caPublicKeyTemplatesRetainTheOpenPlacementConflict() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");
        JsonNode request = templates.path("CA Public Key File Load Request");
        JsonNode response = templates.path("CA Public Key File Load Response");

        assertThat(request.path("review_status").asText()).isEqualTo("PROVISIONAL_SEG132_SME_006");
        assertThat(request.path("data_section_1_fields")).hasSize(2);
        assertThat(request.path("segments").get(0).path("fields")).hasSize(10);
        assertThat(request.path("notes").toString()).contains("SEG132-SME-006");
        assertThat(response.path("layout").asText()).isEqualTo("POSITIONAL_NO_FIELD_SEPARATORS");
        assertThat(response.path("fields")).hasSize(7);
    }

    @Test
    void transArmorKeyTemplateCannotBeMistakenForACompleteLayout() throws IOException {
        JsonNode template = readCatalog().path("message_templates").path("TransArmor Key and Key ID Load Request");

        assertThat(template.path("review_status").asText()).isEqualTo("BLOCKED_EXTERNAL_SOURCE");
        assertThat(template.path("layout").asText()).isEqualTo("NOT_AVAILABLE_EXTERNAL_TRANSARMOR_SPECIFICATION");
        assertThat(template.path("segments").get(0).path("fields")).isEmpty();
        assertThat(template.path("notes").toString()).contains("not a usable message template");
    }

    @Test
    void financialAndEmvRequestSegmentTablesMatchSectionEleven() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");

        assertThat(segmentNumbers(templates.path("Financial Transaction Request")))
            .containsExactly("100", "101", "102", "103", "104", "111", "123", "135", "143", "145", "146", "151", "152", "153");
        assertThat(templates.path("Financial Transaction Request").path("approved_extensions").get(0).path("segment_number").asText())
            .isEqualTo("110");
        assertThat(templates.path("Financial Transaction Request").path("approved_extensions").get(0).path("decision").asText())
            .contains("SEG110-R-001");
        assertThat(segmentNumbers(templates.path("EMV Financial Transaction Request")))
            .containsExactly("100", "101", "102", "103", "104", "111", "123", "130", "135", "143", "145", "146", "151", "152", "153");
    }

    @Test
    void financialAndEmvResponseTablesMatchSectionEleven() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");

        assertThat(templates.path("Financial Transaction Response").path("fields")).hasSize(15);
        assertThat(segmentNumbers(templates.path("Financial Transaction Response"))).containsExactly("112", "115");
        assertThat(templates.path("EMV Financial Transaction Response").path("fields")).hasSize(15);
        assertThat(segmentNumbers(templates.path("EMV Financial Transaction Response")))
            .containsExactly("112", "115", "120", "131", "134");
    }

    @Test
    void loyaltyAndEcaResponsesExplicitlyReuseFinancialResponse() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");

        assertThat(templates.path("Loyalty Card Transaction Response").path("alias_of").asText())
            .isEqualTo("Financial Transaction Response");
        assertThat(templates.path("Loyalty Card Transaction Response").path("source_section").asText())
            .isEqualTo("11.2.2");
        assertThat(templates.path("ECA/TeleCheck Service Transaction Response").path("alias_of").asText())
            .isEqualTo("Financial Transaction Response");
        assertThat(templates.path("ECA/TeleCheck Service Transaction Response").path("source_section").asText())
            .isEqualTo("11.3.2");
    }

    @Test
    void commonTransactionRequestsExposeDataSectionOneFields() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");
        for (String key : new String[]{
            "Financial Transaction Request", "Loyalty Card Transaction", "ECA/TeleCheck Service Transaction",
            "EMV Financial Transaction Request", "Totals Request", "Totals with Proprietary Data Load Request",
            "Electronic Mail Request"
        }) {
            assertThat(templates.path(key).path("data_section_1_fields")).as(key).hasSize(2);
        }
    }

    @Test
    void communicationsTestRequestPreservesUnnumberedElement63AndField2Element120() throws IOException {
        JsonNode request = readCatalog().path("message_templates").path("Communications Test Request");
        JsonNode fields = request.path("fields");

        assertThat(fields).hasSize(3);
        assertThat(fields.get(0).path("field_no").asText()).isEqualTo("1");
        assertThat(fields.get(1).path("field_no").asText()).isEmpty();
        assertThat(fields.get(1).path("element_no").asText()).isEqualTo("63");
        assertThat(fields.get(1).path("position").asInt()).isEqualTo(7);
        assertThat(fields.get(2).path("field_no").asText()).isEqualTo("2");
        assertThat(fields.get(2).path("element_no").asText()).isEqualTo("120");
        assertThat(fields.get(2).path("position").asInt()).isEqualTo(9);
        assertThat(fields.get(2).path("source").path("page").asInt()).isEqualTo(184);
        assertThat(request.path("notes").toString()).contains("Element 63 (Number of Segments) is an unnumbered required table row");
    }

    @Test
    void sectionElevenIndexCoversEveryMessageLayoutWithoutApprovingIt() throws IOException {
        JsonNode catalog = readCatalog();
        JsonNode index = MAPPER.readTree(Path.of("specifications", "ATL105", "docs", "specs", "kb", "section-11-message-layout-index.json").toFile());

        assertThat(index.path("status").asText()).isEqualTo("SOURCE_RECONCILED_NOT_APPROVED");
        assertThat(index.path("approval_status").asText()).isEqualTo("NOT_APPROVED");
        assertThat(index.path("layouts")).hasSize(31);
        assertThat(index.path("layouts").findValuesAsText("section")).containsExactly(
            "11.1.1", "11.1.2", "11.2.1", "11.2.2", "11.3.1", "11.3.2",
            "11.4.1.1", "11.4.1.2", "11.4.2.1", "11.4.2.2", "11.5.1", "11.5.2",
            "11.6.1", "11.6.2", "11.7.1.1", "11.7.1.2", "11.7.2.1", "11.7.2.2",
            "11.7.3.1", "11.7.3.2", "11.7.4.1", "11.7.4.2", "11.7.5", "11.7.6.1",
            "11.7.7", "11.7.8.1", "11.7.8.2", "11.8.1", "11.8.2", "11.9.1", "11.9.2");
        assertThat(catalog.path("section_11_layout_index").asText()).isEqualTo("specs/kb/section-11-message-layout-index.json");
        for (JsonNode layout : index.path("layouts")) {
            String key = layout.path("template_key").asText();
            assertThat(catalog.path("message_templates").has(key)).as(key).isTrue();
        }
    }

    @Test
    void conditionalDownloadAndProprietaryDataVariantsAreExplicit() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");
        JsonNode tableResponse = templates.path("Table Load Response");
        JsonNode pdlRequest = templates.path("Proprietary Data Load Request");
        JsonNode pdlResponse = templates.path("Proprietary Data Load Response");

        assertThat(tableResponse.path("blocks").get(3).path("condition").asText()).contains("Card Type 173");
        assertThat(pdlRequest.path("promptCodes").toString()).contains("903", "905", "request only");
        assertThat(pdlResponse.path("promptCodes").toString()).contains("901", "902", "904", "response only");
    }

    @Test
    void sourceLengthConflictsRemainVisibleInMessageTemplates() throws IOException {
        JsonNode templates = readCatalog().path("message_templates");
        assertThat(templates.path("EMV Financial Transaction Request").path("source_conflicts").toString())
            .contains("9,999", "3,043");
        assertThat(templates.path("EMV Financial Transaction Response").path("source_conflicts").toString())
            .contains("3,850", "3,834");
        assertThat(templates.path("Totals with Proprietary Data Load Request").path("source_conflicts").toString())
            .contains("389", "493");
    }

    @Test
    void segment105RuleAnchorsUseNumberedSourceSections() throws IOException {
        JsonNode catalog = MAPPER.readTree(Path.of("specifications", "ATL105", "docs", "specs", "kb", "segment-105", "coverage", "segment-105-rule-catalog.json").toFile());
        for (JsonNode rule : catalog.path("rules")) {
            if (rule.path("ruleId").asText().matches("SEG105-R-(0[1-9]|1[0-7])")) {
                assertThat(rule.path("sourceAnchor").path("section").asText())
                    .as(rule.path("ruleId").asText()).matches("(?:11|12|13)(?:\\.[0-9]+)+(?:,(?:11|12|13)(?:\\.[0-9]+)+)*");
            }
        }
    }

    private static java.util.List<String> segmentNumbers(JsonNode template) {
        java.util.List<String> numbers = new java.util.ArrayList<>();
        template.path("segments").forEach(segment -> numbers.add(segment.path("segment_number").asText()));
        return numbers;
    }

    private static JsonNode readCatalog() throws IOException {
        return MAPPER.readTree(CATALOG_PATH.toFile());
    }
}