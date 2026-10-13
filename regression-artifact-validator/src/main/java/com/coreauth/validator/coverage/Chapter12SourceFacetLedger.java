package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Lossless source-text work inventory, deliberately not an atomic semantic-closure claim. */
final class Chapter12SourceFacetLedger {
    private static final List<String> FACETS = List.of("representation", "valid-value", "conditional-trigger",
            "dependency", "placement", "wire", "lifecycle");

    private Chapter12SourceFacetLedger() { }

    static ObjectNode build(JsonNode sectionLedger, ArrayNode executions, JsonNode extension) throws IOException {
        JsonNode bindings = extension.path("sourceFacetBindings");
        List<String> source = Files.readAllLines(Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt")));
        ObjectNode ledger = JsonNodeFactory.instance.objectNode().put("status", "IN_PROGRESS")
                .put("sectionCount", 49).put("facetCategorySlots", 49 * FACETS.size())
                .put("atomicClauseDenominatorEstablished", false).put("fullyClosedFacetCategories", 0)
                .put("policy", "343 section/facet category slots are a work inventory, NOT 343 atomic clauses. "
                        + "Source text units exhaustively retain nonblank non-page-header lines inside all 49 section boundaries, "
                        + "but table/paragraph units can contain several clauses. Atomic clause splitting and semantic reconciliation remain source-local work. "
                        + "Only explicit source-line/rule/scope bindings receive bounded execution links. Catalog links and REVIEW_REQUIRED targets never close a facet.");
        ArrayNode sections = ledger.putArray("sections");
        ArrayNode boundaries = ledger.putArray("sourceOwnerAndExternalBoundaries");
        for (JsonNode boundary : extension.path("sourceBoundaries")) {
            int start = boundary.path("sourceEvidence").path("startLine").asInt(), end = boundary.path("sourceEvidence").path("endLine").asInt();
            JsonNode section = null;
            for (JsonNode candidate : sectionLedger.path("sections"))
                if (candidate.path("section").equals(boundary.path("section"))) section = candidate;
            if (!boundary.isObject() || section == null || start < section.path("sourceEvidence").path("startLine").asInt()
                    || end > section.path("sourceEvidence").path("endLine").asInt() || end < start)
                throw new IOException("Source boundary must be anchored inside its stated section");
            ObjectNode record = boundary.deepCopy();
            ArrayNode lines = record.putArray("sourceLines");
            for (int line = start; line <= end; line++) lines.add(source.get(line - 1));
            boundaries.add(record);
        }
        int unitCount = 0, retainedLineCount = 0, boundedCategories = 0;
        for (JsonNode existing : sectionLedger.path("sections")) {
            String section = existing.path("section").asText();
            int start = existing.path("sourceEvidence").path("startLine").asInt();
            int end = existing.path("sourceEvidence").path("endLine").asInt();
            ObjectNode record = sections.addObject().put("section", section).put("status", "SOURCE_LOCAL_RECONCILIATION_REQUIRED");
            record.set("sourceEvidence", existing.path("sourceEvidence").deepCopy());
            ArrayNode units = record.putArray("sourceTextUnits");
            ObjectNode unit = null;
            for (int line = start; line <= end; line++) {
                String text = source.get(line - 1).trim();
                if (text.isEmpty() || boilerplate(text)) { unit = null; continue; }
                if (unit == null) {
                    unit = units.addObject().put("id", section + "-L" + line).put("status", "ATOMIC_CLAUSES_UNRECONCILED");
                    unit.putObject("sourceAnchor").put("specification", "ATL105").put("version", "2026-3")
                            .put("section", section);
                    unit.putObject("sourceEvidence").put("startLine", line).put("endLine", line);
                    unit.putArray("lines");
                }
                ((ObjectNode) unit.path("sourceEvidence")).put("endLine", line);
                ((ArrayNode) unit.path("lines")).add(source.get(line - 1));
                retainedLineCount++;
            }
            unitCount += units.size();
            ArrayNode categories = record.putArray("facets");
            for (String facet : FACETS) {
                ObjectNode category = categories.addObject().put("facet", facet).put("status", "SOURCE_LOCAL_UNRECONCILED")
                        .put("semanticClosure", false);
                ArrayNode linked = category.putArray("boundedSourcePredicates");
                for (JsonNode binding : bindings) {
                    if (!section.equals(binding.path("section").asText()) || !facet.equals(binding.path("facet").asText())) continue;
                    int from = binding.path("startLine").asInt(), to = binding.path("endLine").asInt();
                    if (from < start || to > end || to < from) throw new IOException("Source facet binding outside section " + section);
                    ObjectNode predicate = linked.addObject();
                    if (!binding.isObject()) throw new IOException("Source facet binding must be an object");
                    ObjectNode authoredBinding = binding.deepCopy();
                    predicate.setAll(authoredBinding);
                    ArrayNode sourceLines = predicate.putArray("sourceLines");
                    for (int line = from; line <= to; line++) sourceLines.add(source.get(line - 1));
                    ArrayNode chains = predicate.putArray("executedChains");
                    boolean executed = false;
                    for (JsonNode execution : executions) {
                        if (!binding.path("ruleId").asText().equals(execution.path("targetRuleId").asText())
                                || !binding.path("scope").asText().equals(execution.path("scope").asText())) continue;
                        if (!execution.path("actualTargetStatus").equals(execution.path("expectedTargetStatus")))
                            throw new IOException("Source facet binding has mismatched executed evidence");
                        ObjectNode chain = chains.addObject();
                        for (String key : List.of("businessRequirementId", "scenarioId", "testCaseId", "testDataId", "actualTargetStatus"))
                            chain.set(key, execution.path(key).deepCopy());
                        executed |= !execution.path("actualTargetStatus").asText().equals("REVIEW_REQUIRED");
                    }
                    predicate.put("status", executed ? "BOUNDED_IMPLEMENTATION" : "MISSING_EXECUTABLE_PREDICATE_EVIDENCE");
                    predicate.put("semanticClosure", false);
                }
                if (linked.findValuesAsText("status").contains("BOUNDED_IMPLEMENTATION")) {
                    category.put("status", "BOUNDED_IMPLEMENTATION_WITH_SOURCE_LOCAL_REMAINDER");
                    boundedCategories++;
                }
                category.put("remainingWork", "Split and reconcile this category's applicable clauses in sourceTextUnits; "
                        + "source conflicts, unavailable external layouts and trust boundaries require separate clause-specific classification.");
            }
        }
        ledger.put("sourceTextUnits", unitCount).put("retainedSourceLines", retainedLineCount)
                .put("boundedFacetCategories", boundedCategories).put("unreconciledFacetCategories", 343 - boundedCategories);
        return ledger;
    }

    private static boolean boilerplate(String line) {
        return line.matches("--- PAGE [0-9]+ ---") || line.startsWith("BUYPASS® Platform ATL105 Message Format Specifications")
                || line.startsWith("Software Version:") || line.startsWith("Specification Release Version:")
                || line.equals("August 07, 2026") || line.matches("12-[0-9]+");
    }
}
