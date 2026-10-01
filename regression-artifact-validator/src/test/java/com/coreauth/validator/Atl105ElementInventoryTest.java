package com.coreauth.validator;

import com.coreauth.validator.coverage.Atl105ElementInventory;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105ElementInventoryTest {
    @Test
    void reconcilesAll231SourceElementsWithoutLosingDuplicate118OrWrappedNames() throws Exception {
        var inventory = new Atl105ElementInventory().extract(Files.readString(
            Path.of("specifications/ATL105/docs/specs/extracted_text.txt")));
        assertThat(inventory.path("elementCount").asInt()).isEqualTo(231);
        assertThat(inventory.path("definitionCount").asInt()).isEqualTo(232);
        assertThat(inventory.path("elements").path("118").path("definitions").size()).isEqualTo(2);
        assertThat(inventory.path("elements").path("55").path("definitions").get(0).path("name").asText())
            .isEqualTo("Message Format Version Identifier");
    }

    @Test
    void preservesRepeatedDefinitionsAndExcludesChapterFourteen() {
        String source = """
            Number: 1 Name: Access Code
            Character Type: AN Maximum Length: 12 bytes
            Number: 83 Name: Response Code
            Valid Codes/Values: 0 Approved Purchase/Capture
            Number: 83 Name: Response Code Continued
            Valid Codes/Values: X Context dependent
            14. Support, Testing, and Certification
            Not an element definition
            """;

        var inventory = new Atl105ElementInventory().extract(source);

        assertThat(inventory.path("elementCount").asInt()).isEqualTo(2);
        assertThat(inventory.path("definitionCount").asInt()).isEqualTo(3);
        assertThat(inventory.path("elements").path("83").path("definitions").size()).isEqualTo(2);
        assertThat(inventory.path("elements").path("83").path("status").asText())
            .isEqualTo("MULTIPLE_DEFINITIONS_REVIEW_REQUIRED");
        assertThat(inventory.path("elements").path("83").path("definitions").get(1).path("sourceText").asText())
            .doesNotContain("Support, Testing");
        assertThat(inventory.path("elements").path("83").path("definitions").get(0).path("sourceLine").asInt())
            .isEqualTo(3);
    }

    @Test
    void extractsBaselineConstraintsAndKeepsAmbiguousDefinitionsInReview() throws Exception {
        var elements = new Atl105ElementInventory().extract(Files.readString(
            Path.of("specifications/ATL105/docs/specs/extracted_text.txt"))).path("elements");
        var e84 = elements.path("84").path("definitions").get(0).path("constraints");
        assertThat(e84.path("characterType").asText()).isEqualTo("N");
        assertThat(e84.path("lengthMode").asText()).isEqualTo("VARIABLE");
        assertThat(e84.path("maxLength").asInt()).isEqualTo(4);
        var e86 = elements.path("86").path("definitions").get(0).path("constraints");
        assertThat(e86.path("lengthMode").asText()).isEqualTo("FIXED");
        assertThat(e86.path("maxLength").asInt()).isEqualTo(6);
        var e171 = elements.path("171").path("definitions").get(0).path("constraints");
        assertThat(e171.path("lengthLabel").asText()).isEqualTo("Fixed Length");
        assertThat(e171.path("extractionStatus").asText()).isEqualTo("EXTRACTED");
        for (String ambiguous : new String[]{"2", "43", "202", "243"}) {
            assertThat(elements.path(ambiguous).path("definitions").get(0).path("constraints").path("extractionStatus").asText())
                .as(ambiguous).isEqualTo("REVIEW_REQUIRED");
        }
    }

    @Test
    void lengthLabelConflictingWithRepresentationIsNotExtracted() {
        var constraints = Atl105ElementInventory.constraints("""
            Number: 9 Name: Test
            Character Type: A Variable Length: 4 bytes
            Representation: Fixed length of four characters
            Purpose: Test
            """);
        assertThat(constraints.path("lengthMode").asText()).isEqualTo("CONFLICT");
        assertThat(constraints.path("extractionStatus").asText()).isEqualTo("REVIEW_REQUIRED");
    }

    @Test
    void emptySourceDoesNotFabricateElements() {
        assertThat(new Atl105ElementInventory().extract("").path("elementCount").asInt()).isZero();
    }

    @Test
    void linksExistingBrsWithoutConflatingElement8And83OrCreatingNewBrs() throws Exception {
        var generator = new Atl105ElementInventory();
        var inventory = generator.extract("Number: 8 Name: Authorizer Response Code\nNumber: 83 Name: Response Code\n");
        var document = new ObjectMapper().readTree("""
            {"businessRequirements":[{"id":"EXISTING-BR","title":"Outcome",
            "sourceAnchors":[{"element":"83,239","segment":"financial-response"}]}]}
            """);
        generator.linkRequirements(inventory, document, "test-brs.json");
        assertThat(inventory.path("elements").path("83").path("existingBusinessRequirements").size()).isEqualTo(1);
        assertThat(inventory.path("elements").path("8").path("existingBusinessRequirements").size()).isZero();
        assertThat(inventory.path("unresolvedElementReferences").size()).isEqualTo(1);
        assertThat(inventory.path("semanticApproval").asBoolean()).isFalse();
    }

    @Test
    void excludesUnverifiedCandidateSegmentsFromActiveTemplateUsage() throws Exception {
        var generator = new Atl105ElementInventory();
        var inventory = generator.extract("Number: 83 Name: Response Code\n");
        var catalog = new ObjectMapper().readTree("""
            {"message_templates":{"Financial Response":{"fields":[{"element_no":"83"}],
            "extracted_candidate_segments":[{"fields":[{"element_no":"83"}]}]}}}
            """);
        generator.linkTemplates(inventory, catalog);
        assertThat(inventory.path("elements").path("83").path("templateUsages").size()).isEqualTo(1);
    }
}