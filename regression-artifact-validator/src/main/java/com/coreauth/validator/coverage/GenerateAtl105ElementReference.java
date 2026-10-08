package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/** Builds a source-linked all-element reference and Excel-friendly element/context CSV exports. */
public final class GenerateAtl105ElementReference {
    private static final String INVENTORY = "test-output/test-solution-independent-review/all-element-inventory.json";
    private static final String BR_COVERAGE = "test-output/test-solution-independent-review/knowledge-base-br-coverage-package.json";
    private static final String OUTPUT = "test-output/test-solution-independent-review/atl105-all-elements-reference.json";
    private static final String ELEMENT_CSV = "test-output/test-solution-independent-review/atl105-all-elements.csv";
    private static final String CONTEXT_CSV = "test-output/test-solution-independent-review/atl105-element-contexts.csv";
    private static final Pattern RULE_ID = Pattern.compile("SEG[0-9A-Z]+-R-\\d{3}");
    private static final Pattern ELEMENT_MENTION = Pattern.compile("(?i)\\belement(?:\\s+no\\.?|\\s+number)?\\s*#?\\s*(\\d{1,3})\\b");
    private static final Pattern ABSENCE = Pattern.compile("(?i)\\b(absent|not required|not present|does not (?:contain|include|appear|occur)|prohibited|not used|exclude[sd]?|only when)\\b");
    private static final Pattern TYPE_LENGTH = Pattern.compile("Character Type:[ \\t]*([A-Za-z0-9/]+)[ \\t]+(?:(Maximum|Max\\.|Fixed|Variable)[ \\t]+)?Length:[ \\t]*(?:([0-9,]+)[ \\t]*bytes?\\b)?");
    private static final Pattern TX_CODE = Pattern.compile("(?m)^\\s*([0-9A-Z])\\s+([^\\r\\n]{3,100})$");

    private GenerateAtl105ElementReference() { }

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode inventory = mapper.readTree(pack.resolve(INVENTORY).toFile());
        JsonNode templates = mapper.readTree(pack.resolve("docs/atl105_complete_templates.json").toFile()).path("message_templates");
        JsonNode brCoverage = mapper.readTree(pack.resolve(BR_COVERAGE).toFile());
        JsonNode transactionCatalog = mapper.readTree(pack.resolve("test-output/test-json/segment-100-all-23-transaction-type-training-baseline.json").toFile());
        Map<String, JsonNode> rules = loadRules(mapper, pack);
        Map<String, JsonNode> brByRule = new HashMap<>();
        for (JsonNode br : brCoverage.path("businessRequirements")) brByRule.put(br.path("id").asText(), br);
        String source = Files.readString(pack.resolve("docs/specs/extracted_text.txt"));

        ObjectNode document = mapper.createObjectNode();
        document.put("artifact", "ATL105-ALL-ELEMENT-SOURCE-CONTEXT-AND-RULE-REFERENCE");
        document.put("specification", "ATL105");
        document.put("specificationVersion", "2026-3");
        document.put("elementCount", inventory.path("elementCount").asInt());
        document.put("definitionCount", inventory.path("definitionCount").asInt());
        document.put("authority", "INDEPENDENT_TEST_SOLUTION_SOURCE_DERIVED_REFERENCE");
        document.put("status", "SOURCE_DERIVED_REVIEW_REQUIRED");
        document.put("humanReviewRequired", true);
        document.put("policy", "Character constraints are taken from each Chapter 13 definition. Segment and family contexts come from active Section 11 templates and rule anchors. Not listed is not equivalent to prohibited. Unsupported transaction-code applicability is REVIEW_REQUIRED.");
        document.put("source", "docs/specs/extracted_text.txt, docs/atl105_complete_templates.json, docs/specs/kb/*-rule-catalog.json, knowledge-base-br-coverage-package.json");
        document.put("transactionApplicabilityMatrix", "test-output/test-solution-independent-review/atl105-element-transaction-applicability.json");
        ArrayNode txTypes = document.putArray("transactionTypes");
        transactionCatalog.path("transactionTypes").forEach(tx -> txTypes.add(tx.deepCopy()));
        ArrayNode elements = document.putArray("elements");
        ArrayNode contextRows = mapper.createArrayNode();
        Map<String, Set<String>> seenContexts = new HashMap<>();
        int extractedConstraints = 0;
        int elementsWithAllDefinitionConstraints = 0;
        int elementsWithAnyDefinitionConstraint = 0;
        int duplicatedElements = 0;
        int elementsWithBr = 0;
        int elementsWithActiveTemplateUsages = 0;
        int elementsWithContextRows = 0;
        int activeContextRows = 0;
        int omittedContextReviewRows = 0;
        int linkedBusinessRuleReferences = 0;
        int dependencyCandidates = 0;
        int explicitAbsenceCandidates = 0;

        for (JsonNode element : inventory.path("elements")) {
            String id = element.path("element").asText();
            ObjectNode row = elements.addObject();
            row.put("element", id);
            row.put("sourceStatus", element.path("status").asText("SOURCE_REVIEW_REQUIRED"));
            ArrayNode names = row.putArray("sourceNames");
            ArrayNode definitions = row.putArray("definitions");
            int extractedForElement = 0;
            for (JsonNode definition : element.path("definitions")) {
                names.add(definition.path("name").asText());
                ObjectNode def = definitions.addObject();
                def.put("name", definition.path("name").asText());
                def.put("sourceSection", definition.path("sourceSection").asText());
                def.put("sourceLine", definition.path("sourceLine").asInt());
                JsonNode extracted = definition.path("constraints");
                def.set("constraints", extracted.deepCopy());
                def.put("validCodesEvidence", validCodesEvidence(definition.path("sourceText").asText()));
                def.put("sourceEvidence", "Chapter 13 Element " + id + ", line " + definition.path("sourceLine").asText());
                if ("EXTRACTED".equals(extracted.path("extractionStatus").asText())) {
                    extractedConstraints++;
                    extractedForElement++;
                }
            }
            if (extractedForElement > 0) elementsWithAnyDefinitionConstraint++;
            if (extractedForElement == element.path("definitions").size()) elementsWithAllDefinitionConstraints++;
            if (element.path("definitions").size() > 1) duplicatedElements++;

            TreeSet<String> segmentSet = new TreeSet<>();
            for (JsonNode ruleRef : element.path("catalogRules")) {
                String segment = ruleRef.path("sourceAnchor").path("segment").asText();
                if (!segment.isBlank()) for (String part : segment.split("[,|]"))
                    if (part.trim().matches("\\d{3}|DL[1-8]")) segmentSet.add(part.trim());
            }
            for (JsonNode usage : element.path("templateUsages")) {
                String segment = usage.path("segment").asText();
                if (segment.matches("\\d{3}|DL[1-8]")) segmentSet.add(segment);
            }
            ArrayNode segments = row.putArray("segments");
            segmentSet.forEach(segments::add);

            ArrayNode contexts = row.putArray("messageFamilyContexts");
            Set<String> usedPairs = new HashSet<>();
            Map<String, JsonNode> usageByPair = new HashMap<>();
            for (JsonNode usage : element.path("templateUsages")) {
                String family = usage.path("messageFamily").asText();
                String segment = usage.path("segment").asText("MESSAGE");
                String pair = family + "|" + segment;
                usageByPair.putIfAbsent(pair, usage);
            }
            for (Map.Entry<String, JsonNode> item : usageByPair.entrySet()) {
                String[] parts = item.getKey().split("\\|", 2);
                String family = parts[0];
                String segment = parts[1];
                JsonNode usage = item.getValue();
                String pair = family + "|" + segment;
                usedPairs.add(pair);
                JsonNode template = templates.path(family);
                ObjectNode context = contexts.addObject();
                context.put("messageFamily", family);
                context.put("transactionTypeLabel", template.path("transaction_type").asText(family));
                context.put("sourceSection", template.path("source_section").asText(""));
                context.put("templateReviewStatus", template.path("review_status").asText("NOT_STATED"));
                context.put("segment", segment);
                String presence = presence(template, segment);
                context.put("segmentPresence", presence);
                context.put("elementPresence", fieldPresence(usage.path("field")));
                addTemplateFieldEvidence(context, usage.path("field"));
                context.set("fieldRuleDecision", fieldRuleDecision(usage.path("field"), true));
                context.put("contextKind", "ACTIVE_TEMPLATE_FIELD");
                context.put("evidenceStatus", usage.path("status").asText("REVIEW_REQUIRED"));
                context.put("evidence", template.path("source_section").asText("Section 11 template") + "; template path " + usage.path("path").asText());
                ObjectNode exportedContext = context.deepCopy();
                exportedContext.put("element", id);
                contextRows.add(exportedContext);
                activeContextRows++;
            }
            for (String segment : segmentSet) {
                var familyIterator = templates.fields();
                while (familyIterator.hasNext()) {
                    var familyEntry = familyIterator.next();
                    String family = familyEntry.getKey();
                    JsonNode template = familyEntry.getValue();
                    String pair = family + "|" + segment;
                    if (usedPairs.contains(pair) || usedPairs.contains("ABSENT|" + pair)) continue;
                    usedPairs.add("ABSENT|" + pair);
                    boolean segmentListed = familyHasSegment(template, segment);
                    ObjectNode missing = contextRows.addObject();
                    missing.put("element", id);
                    missing.put("messageFamily", family);
                    missing.put("transactionTypeLabel", template.path("transaction_type").asText(family));
                    missing.put("sourceSection", template.path("source_section").asText(""));
                    missing.put("segment", segment);
                    missing.put("segmentPresence", presence(template, segment));
                    missing.put("elementPresence", "NOT_LISTED_IN_TEMPLATE");
                    missing.set("fieldRuleDecision", fieldRuleDecision(null, false));
                    missing.put("contextKind", segmentListed ? "SEGMENT_LISTED_FIELD_NOT_LISTED" : "SEGMENT_NOT_LISTED_IN_FAMILY_TEMPLATE");
                    missing.put("evidenceStatus", "REVIEW_REQUIRED_NOT_PROOF_OF_ABSENCE");
                    missing.put("evidence", segmentListed
                        ? "Segment is listed in the family template but the element has no active field row; omission is not proof of prohibition."
                        : "Element's source-linked segment is not listed in this family's Section 11 template; omission is not proof of prohibition.");
                    ObjectNode contextCopy = missing.deepCopy();
                    contextCopy.remove("element");
                    contexts.add(contextCopy);
                    omittedContextReviewRows++;
                }
            }
            if (!element.path("templateUsages").isEmpty()) elementsWithActiveTemplateUsages++;
            if (!contexts.isEmpty()) elementsWithContextRows++;
            row.put("contextEvidencePolicy", "Template presence is contextual evidence; C/OPTIONAL/CONDITIONAL/PROVISIONAL remain conditional or review-required. A missing field row is not proof the element is prohibited.");

            ArrayNode usages = row.putArray("activeTemplateFieldUsages");
            for (JsonNode usage : element.path("templateUsages")) {
                ObjectNode copy = usage.deepCopy();
                usages.add(copy);
            }

            ArrayNode businessRules = row.putArray("businessRules");
            TreeSet<String> seenRuleIds = new TreeSet<>();
            for (JsonNode ref : element.path("catalogRules")) {
                String ruleId = ref.path("id").asText();
                if (ruleId.isBlank() || !seenRuleIds.add(ruleId)) continue;
                JsonNode sourceRule = rules.get(ruleId);
                ObjectNode businessRule = businessRules.addObject();
                businessRule.put("ruleId", ruleId);
                businessRule.put("title", ref.path("title").asText());
                businessRule.put("ruleClass", sourceRule == null ? "UNSPECIFIED" : sourceRule.path("class").asText("UNSPECIFIED"));
                businessRule.put("severity", sourceRule == null ? "UNSPECIFIED" : sourceRule.path("severity").asText("UNSPECIFIED"));
                businessRule.put("sourceCatalog", ref.path("sourceFile").asText());
                businessRule.set("sourceAnchor", ref.path("sourceAnchor").deepCopy());
                businessRule.put("matchStatus", "ELEMENT_ANCHOR_CANDIDATE_REQUIRES_SEMANTIC_REVIEW");
                JsonNode br = brByRule.get(ruleId);
                ArrayNode linked = businessRule.putArray("existingBusinessRequirements");
                if (br != null) {
                    for (JsonNode existing : br.path("existingBusinessRequirements")) {
                        ObjectNode link = linked.addObject();
                        link.put("id", existing.path("id").asText());
                        link.put("source", existing.path("source").asText());
                        link.put("evidence", existing.path("evidence").asText());
                    }
                }
                businessRule.put("brStatus", linked.isEmpty() ? "BR_REVIEW_OR_MISSING" : "AUTHORED_BR_CANDIDATE_LINKED");
                businessRule.put("negativeCoverageCount", br == null ? 0 : br.path("negativeCoverage").size());
            }
            row.put("ruleAnchoredBusinessRuleCount", businessRules.size());
            linkedBusinessRuleReferences += businessRules.size();
            boolean hasLinkedBr = false;
            for (JsonNode businessRule : businessRules)
                if (!businessRule.path("existingBusinessRequirements").isEmpty()) hasLinkedBr = true;
            if (hasLinkedBr) elementsWithBr++;

            ArrayNode dependencies = row.putArray("dependencies");
            ArrayNode absence = row.putArray("explicitAbsenceOrConditionRules");
            for (JsonNode rule : rules.values()) {
                String ruleId = rule.path("ruleId").asText();
                String anchorElements = rule.path("sourceAnchor").path("element").asText();
                boolean direct = containsElement(anchorElements, id);
                String text = rule.path("title").asText() + " " + rule.path("note").asText();
                boolean mentions = mentionsElement(text, id);
                if (!direct && !mentions) continue;
                String kind = rule.path("class").asText("UNSPECIFIED");
                if (Set.of("dependency", "interdependency", "conditional", "compatibility", "lifecycle", "applicability", "response").contains(kind)
                    || mentions) {
                    ObjectNode dep = dependencies.addObject();
                    dep.put("ruleId", ruleId);
                    dep.put("ruleClass", kind);
                    dep.put("title", rule.path("title").asText());
                    dep.put("relationship", direct ? "DIRECT_ELEMENT_ANCHOR" : "TEXT_MENTION_CANDIDATE");
                    dep.put("status", direct ? "SOURCE_ANCHORED_REVIEW_REQUIRED" : "CROSS_FIELD_DEPENDENCY_REVIEW_REQUIRED");
                    dep.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
                }
                if (direct && ABSENCE.matcher(text).find()) {
                    ObjectNode restriction = absence.addObject();
                    restriction.put("ruleId", ruleId);
                    restriction.put("title", rule.path("title").asText());
                    restriction.put("status", "EXPLICIT_ABSENCE_CONDITION_REVIEW_REQUIRED");
                    restriction.set("sourceAnchor", rule.path("sourceAnchor").deepCopy());
                }
            }
            row.put("absenceConclusion", absence.isEmpty() ? "NO_EXPLICIT_ABSENCE_RULE_FOUND_NOT_PROOF_OF_USAGE" : "EXPLICIT_RULES_LISTED_REVIEW_SCOPE");
            dependencyCandidates += dependencies.size();
            explicitAbsenceCandidates += absence.size();
            addDependencyDecision(contexts, dependencies);
            for (JsonNode contextRow : contextRows) {
                if (id.equals(contextRow.path("element").asText()))
                    addDependencyDecision((ObjectNode) contextRow, dependencies);
            }

            ArrayNode txCodes = row.putArray("transactionTypeCodes");
            if ("78".equals(id)) {
                for (JsonNode tx : transactionCatalog.path("transactionTypes")) {
                    ObjectNode code = txCodes.addObject();
                    code.put("code", tx.path("code").asText());
                    code.put("name", tx.path("name").asText());
                    code.put("sourceAnchor", tx.path("sourceAnchor").asText());
                    code.put("status", "TRANSACTION_TYPE_COMPONENT_OF_ELEMENT_78_PROMPT_CODE");
                }
                row.put("transactionTypeSpecificity", "SOURCE_DEFINED_TRANSACTION_TYPE_COMPONENT_FOR_ELEMENT_78_PROMPT_CODE");
            } else {
                row.putArray("transactionTypeCodes");
                row.put("transactionTypeSpecificity", "MESSAGE_FAMILY_CONTEXTS_ONLY_TRANSACTION_CODE_APPLICABILITY_NOT_EXHAUSTIVELY_DEFINED_PER_ELEMENT");
            }
            row.put("transactionTypeApplicabilityStatus", "NOT_EXHAUSTIVELY_MAPPED_REVIEW_REQUIRED");
            row.put("businessRuleSemanticsApproved", false);
            row.put("combinationsVerified", false);
        }

        ObjectNode summary = document.putObject("summary");
        summary.put("elements", elements.size());
        summary.put("chapter13Definitions", document.path("definitionCount").asInt());
        summary.put("definitionsWithConstraintsExtracted", extractedConstraints);
        summary.put("elementsWithAnyDefinitionConstraintsExtracted", elementsWithAnyDefinitionConstraint);
        summary.put("elementsWithAllDefinitionConstraintsExtracted", elementsWithAllDefinitionConstraints);
        summary.put("elementsWithMultipleDefinitions", duplicatedElements);
        summary.put("elementsWithLinkedExistingBrCandidates", elementsWithBr);
        summary.put("elementRuleBrReferences", linkedBusinessRuleReferences);
        summary.put("dependencyRuleCandidates", dependencyCandidates);
        summary.put("explicitAbsenceOrConditionRuleCandidates", explicitAbsenceCandidates);
        TreeMap<String, Integer> fieldDecisionCounts = new TreeMap<>();
        for (JsonNode context : contextRows) {
            JsonNode decision = context.path("fieldRuleDecision");
            fieldDecisionCounts.merge("decisionStatus:" + decision.path("decisionStatus").asText("MISSING"), 1, Integer::sum);
            fieldDecisionCounts.merge("applicability:" + decision.path("applicability").path("status").asText("MISSING"), 1, Integer::sum);
            fieldDecisionCounts.merge("conditionalTrigger:" + decision.path("conditionalTrigger").path("status").asText("MISSING"), 1, Integer::sum);
            fieldDecisionCounts.merge("permittedOmission:" + decision.path("permittedOmission").path("status").asText("MISSING"), 1, Integer::sum);
            fieldDecisionCounts.merge("crossSegmentDependency:" + decision.path("crossSegmentDependency").path("status").asText("MISSING"), 1, Integer::sum);
            if (!decision.path("crossSegmentDependency").path("candidateRuleIds").isEmpty())
                fieldDecisionCounts.merge("contextsWithDependencyCandidates", 1, Integer::sum);
        }
        summary.set("fieldRuleDecisionCounts", mapper.valueToTree(fieldDecisionCounts));
        summary.put("elementsWithActiveSection11FieldUsages", elementsWithActiveTemplateUsages);
        summary.put("elementsWithAnyFamilySegmentContextRows", elementsWithContextRows);
        summary.put("activeSection11FieldContextRows", activeContextRows);
        summary.put("omittedFamilySegmentReviewRows", omittedContextReviewRows);
        summary.put("sourceDefinedTransactionTypesListed", txTypes.size());
        summary.put("transactionCodeApplicabilityExhaustivelyMappedForAllElements", false);
        summary.put("approvedBusinessRules", 0);
        summary.put("absentFromTemplateMeansProhibited", false);
        summary.put("explicitAbsenceCandidatesRequireReview", true);
        document.put("validatorContract", "Atl105ElementReferenceValidator validates matrix integrity and source-backed observation shapes. Unknown or unsupported combinations return REVIEW_REQUIRED; template omission does not imply PROHIBITED.");
        assertMatrix(document);

        Path output = pack.resolve(OUTPUT);
        Path elementCsv = pack.resolve(ELEMENT_CSV);
        Path contextCsv = pack.resolve(CONTEXT_CSV);
        Files.createDirectories(output.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), document);
        writeElementCsv(elementCsv, elements);
        writeContextCsv(contextCsv, contextRows);
        writeMarkdown(pack.resolve("test-output/test-solution-independent-review/atl105-all-elements-reference.md"), document, elements);
        System.out.printf("elements=%d definitions=%d constraints=%d contexts=%d linkedBrElements=%d txCodes=%d output=%s%n",
            elements.size(), document.path("definitionCount").asInt(), extractedConstraints, contextRows.size(), elementsWithBr,
            txTypes.size(), output);
    }

    public static void assertMatrix(JsonNode matrix) {
        if (!"ATL105".equals(matrix.path("specification").asText())
            || !"2026-3".equals(matrix.path("specificationVersion").asText())
            || !matrix.path("elements").isArray() || matrix.path("elements").size() != 231) {
            throw new IllegalStateException("Element reference must contain exactly 231 ATL105 2026-3 elements");
        }
        Set<String> ids = new HashSet<>();
        for (JsonNode element : matrix.path("elements")) {
            String id = element.path("element").asText();
            if (!id.matches("\\d+") || !ids.add(id) || !element.path("definitions").isArray() || element.path("definitions").isEmpty())
                throw new IllegalStateException("Invalid or duplicate element entry: " + id);
            if (element.path("businessRuleSemanticsApproved").asBoolean() || element.path("combinationsVerified").asBoolean())
                throw new IllegalStateException("Generated element claims approval without review: " + id);
            for (JsonNode context : element.path("messageFamilyContexts")) {
                JsonNode decision = context.path("fieldRuleDecision");
                if (!"REVIEW_REQUIRED".equals(decision.path("decisionStatus").asText())
                    || !hasDecisionStatus(decision, "applicability")
                    || !hasDecisionStatus(decision, "conditionalTrigger")
                    || !hasDecisionStatus(decision, "permittedOmission")
                    || !hasDecisionStatus(decision, "crossSegmentDependency")
                    || !decision.path("crossSegmentDependency").path("candidateRuleIds").isArray())
                    throw new IllegalStateException("Incomplete or over-promoted field-rule decision for Element " + id);
                if ("NOT_LISTED_IN_TEMPLATE".equals(context.path("elementPresence").asText())
                    && !"REVIEW_REQUIRED_NOT_PROOF_OF_ABSENCE".equals(context.path("evidenceStatus").asText()))
                    throw new IllegalStateException("Template omission was promoted to a negative rule: " + id);
            }
        }
        if (!"APPROVED".equals(matrix.path("status").asText())) {
            return;
        }
        throw new IllegalStateException("Generated element reference cannot be APPROVED automatically");
    }

    private static boolean hasDecisionStatus(JsonNode decision, String dimension) {
        return !decision.path(dimension).path("status").asText().isBlank();
    }

    private static Map<String, JsonNode> loadRules(ObjectMapper mapper, Path pack) throws Exception {
        Map<String, JsonNode> rules = new TreeMap<>();
        try (Stream<Path> files = Files.walk(pack.resolve("docs/specs/kb"))) {
            for (Path file : files.filter(path -> path.getFileName().toString().matches("segment-.*-rule-catalog\\.json")).sorted().toList())
                for (JsonNode rule : mapper.readTree(file.toFile()).path("rules")) rules.put(rule.path("ruleId").asText(), rule);
        }
        return rules;
    }

    private static String presence(JsonNode template, String segment) {
        for (JsonNode item : template.path("segments")) if (segment.equals(item.path("segment_number").asText()))
            return item.path("presence_in_message").asText("NOT_STATED");
        JsonNode extensions = template.path("approved_extensions");
        if (extensions.isObject() && segment.equals(extensions.path("segment_number").asText())) return "APPROVED_EXTENSION";
        for (JsonNode extension : extensions) if (segment.equals(extension.path("segment_number").asText())) return "APPROVED_EXTENSION";
        return "NOT_LISTED_IN_TEMPLATE";
    }

    private static boolean familyHasSegment(JsonNode template, String segment) {
        if (template.path("segments").isArray()) for (JsonNode listed : template.path("segments"))
            if (segment.equals(listed.path("segment_number").asText())) return true;
        JsonNode ext = template.path("approved_extensions");
        if (ext.isObject() && segment.equals(ext.path("segment_number").asText())) return true;
        for (JsonNode listed : ext) if (segment.equals(listed.path("segment_number").asText())) return true;
        return false;
    }

    private static String fieldPresence(JsonNode field) {
        String entry = field.path("entry").asText("");
        if (entry.equals("R")) return "REQUIRED_IN_TEMPLATE";
        if (entry.equals("O")) return "OPTIONAL_IN_TEMPLATE";
        if (entry.equals("C")) return "CONDITIONAL_IN_TEMPLATE";
        return entry.isBlank() ? "TEMPLATE_FIELD_STATUS_NOT_STATED" : entry;
    }

    private static void addTemplateFieldEvidence(ObjectNode context, JsonNode field) {
        context.put("templateFieldEntry", field.path("entry").asText());
        context.put("templateFieldDescription", field.path("description").asText());
        if (field.has("source")) context.set("templateFieldSource", field.path("source").deepCopy());
    }

    private static ObjectNode fieldRuleDecision(JsonNode field, boolean active) {
        String entry = active ? field.path("entry").asText("") : "";
        String description = active ? field.path("description").asText("") : "";
        ObjectNode decision = JsonNodeFactory.instance.objectNode();
        decision.put("decisionStatus", "REVIEW_REQUIRED");

        ObjectNode applicability = decision.putObject("applicability");
        applicability.put("status", !active ? "NOT_ESTABLISHED_TEMPLATE_OMISSION_NOT_PROOF" : switch (entry) {
            case "R" -> "REQUIRED_IN_DECLARED_TEMPLATE";
            case "O" -> "OPTIONAL_IN_DECLARED_TEMPLATE";
            case "C" -> "CONDITIONAL_IN_DECLARED_TEMPLATE";
            default -> "NOT_ESTABLISHED_TEMPLATE_ENTRY_UNKNOWN";
        });
        applicability.put("evidence", active ? "Section 11 template entry marker; does not establish transaction-code or cross-segment applicability." :
            "No active field row in this template; absence is not proof of prohibition or non-applicability.");

        ObjectNode trigger = decision.putObject("conditionalTrigger");
        trigger.put("status", !active ? "NOT_ESTABLISHED" : "C".equals(entry)
            ? (description.isBlank() ? "TRIGGER_NOT_STATED_REVIEW_REQUIRED" : "DESCRIPTION_CAPTURED_TRIGGER_NOT_SEMANTICALLY_RESOLVED")
            : "NO_CONDITION_MARKER_IN_THIS_ROW_OTHER_RULES_UNCHECKED");
        trigger.put("sourceText", description);

        ObjectNode omission = decision.putObject("permittedOmission");
        omission.put("status", !active ? "NOT_ESTABLISHED_TEMPLATE_OMISSION_NOT_PROOF" : switch (entry) {
            case "R" -> "NOT_PERMITTED_WHEN_DECLARED_TEMPLATE_APPLIES";
            case "O" -> "PERMITTED_BY_DECLARED_TEMPLATE";
            case "C" -> "CONDITION_DEPENDENT_TRIGGER_REVIEW_REQUIRED";
            default -> "NOT_ESTABLISHED_TEMPLATE_ENTRY_UNKNOWN";
        });
        omission.put("evidence", "Section 11 template entry marker only; complete request conditions and separators require contextual review.");

        ObjectNode dependency = decision.putObject("crossSegmentDependency");
        dependency.put("status", "POPULATED_FROM_ELEMENT_RULE_SCAN_AFTER_CONTEXT_BUILD");
        dependency.putArray("candidateRuleIds");
        return decision;
    }

    private static void addDependencyDecision(ObjectNode context, JsonNode dependencies) {
        ObjectNode decision = (ObjectNode) context.path("fieldRuleDecision");
        ObjectNode dependency = (ObjectNode) decision.path("crossSegmentDependency");
        ArrayNode ids = (ArrayNode) dependency.path("candidateRuleIds");
        dependencies.forEach(candidate -> ids.add(candidate.path("ruleId").asText()));
        dependency.put("status", dependencies.isEmpty()
            ? "NO_CANDIDATE_FOUND_NOT_PROOF_OF_NO_DEPENDENCY"
            : "ELEMENT_LEVEL_CANDIDATES_NOT_SCOPED_TO_THIS_CONTEXT_REVIEW_REQUIRED");
        dependency.put("reviewArtifact", "atl105-dependency-absence-review-matrix.json");
    }

    private static void addDependencyDecision(ArrayNode contexts, JsonNode dependencies) {
        contexts.forEach(context -> addDependencyDecision((ObjectNode) context, dependencies));
    }

    private static String validCodesEvidence(String definition) {
        int start = definition.indexOf("Valid Codes/Values:");
        if (start < 0) return "NOT_STATED_IN_EXTRACTED_DEFINITION";
        String values = definition.substring(start + "Valid Codes/Values:".length());
        int page = values.indexOf("--- PAGE");
        if (page >= 0) values = values.substring(0, page);
        if (values.length() > 5000) values = values.substring(0, 5000) + " [TRUNCATED; see source line/evidence]";
        return values.replaceAll("\\s+", " ").trim();
    }

    private static boolean containsElement(String anchors, String id) {
        for (String item : anchors.split("[,|]")) if (id.equals(item.trim())) return true;
        return false;
    }

    private static boolean mentionsElement(String text, String id) {
        Matcher matcher = ELEMENT_MENTION.matcher(text);
        while (matcher.find()) if (id.equals(matcher.group(1))) return true;
        return false;
    }

    private static List<String> explicitSegments(JsonNode element) {
        TreeSet<String> segments = new TreeSet<>();
        element.path("catalogRules").forEach(rule -> {
            String value = rule.path("sourceAnchor").path("segment").asText("");
            for (String segment : value.split("[,|]")) if (!segment.isBlank()) segments.add(segment.trim());
        });
        element.path("templateUsages").forEach(usage -> {
            String value = usage.path("segment").asText("");
            if (!value.isBlank() && !"MESSAGE".equals(value)) segments.add(value);
        });
        return List.copyOf(segments);
    }

    private static List<String> familyNames(JsonNode element) {
        TreeSet<String> families = new TreeSet<>();
        element.path("templateUsages").forEach(usage -> families.add(usage.path("messageFamily").asText()));
        return List.copyOf(families);
    }

    private static String constraintsSummary(JsonNode element) {
        List<String> constraints = new ArrayList<>();
        for (JsonNode definition : element.path("definitions")) {
            JsonNode value = definition.path("constraints");
            if ("EXTRACTED".equals(value.path("extractionStatus").asText()))
                constraints.add(value.path("characterType").asText() + " " + value.path("lengthMode").asText()
                    + " max=" + value.path("maxLength").asText());
            else constraints.add("REVIEW");
        }
        return String.join("; ", constraints);
    }

    private static String ruleIds(JsonNode values) {
        List<String> ids = new ArrayList<>();
        values.forEach(value -> ids.add(value.path("ruleId").asText()));
        return String.join(";", ids);
    }

    private static String brIds(JsonNode values) {
        TreeSet<String> ids = new TreeSet<>();
        values.forEach(value -> value.path("existingBusinessRequirements").forEach(br -> ids.add(br.path("id").asText())));
        return String.join(";", ids);
    }

    private static void writeElementCsv(Path path, ArrayNode elements) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,sourceNames,definitionCount,sourceStatus,constraints,validCodesEvidence,segments,messageFamilies,transactionTypeApplicabilityStatus,transactionTypeSpecificity,transactionTypeCodes,businessRuleCount,businessRuleIds,existingBrIds,dependencyRuleIds,explicitAbsenceRuleIds,absenceConclusion,reviewRequired");
        for (JsonNode element : elements) {
            List<String> names = new ArrayList<>();
            element.path("sourceNames").forEach(name -> names.add(name.asText()));
            List<String> codes = new ArrayList<>();
            element.path("transactionTypeCodes").forEach(tx -> codes.add(tx.path("code").asText() + " " + tx.path("name").asText()));
            List<String> absence = new ArrayList<>();
            element.path("explicitAbsenceOrConditionRules").forEach(item -> absence.add(item.path("ruleId").asText()));
            List<String> fields = List.of(element.path("element").asText(), String.join("; ", names),
                element.path("definitions").size() + "", element.path("sourceStatus").asText(), constraintsSummary(element),
                String.join("; ", definitionsCodes(element.path("definitions"))), String.join(";", explicitSegments(element)),
                String.join(";", familyNames(element)), element.path("transactionTypeApplicabilityStatus").asText(),
                element.path("transactionTypeSpecificity").asText(), String.join(";", codes),
                element.path("businessRules").size() + "", ruleIds(element.path("businessRules")), brIds(element.path("businessRules")),
                ruleIds(element.path("dependencies")), String.join(";", absence), element.path("absenceConclusion").asText(), "REVIEW_REQUIRED");
            lines.add(fields.stream().map(GenerateAtl105ElementReference::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static List<String> definitionsCodes(JsonNode definitions) {
        List<String> result = new ArrayList<>();
        definitions.forEach(definition -> result.add(definition.path("validCodesEvidence").asText()));
        return result;
    }

    private static void writeContextCsv(Path path, ArrayNode contexts) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("element,messageFamily,transactionTypeLabel,sourceSection,templateReviewStatus,segment,segmentPresence,elementPresence,contextKind,evidenceStatus,evidence");
        for (JsonNode context : contexts) {
            List<String> fields = List.of(context.path("element").asText(), context.path("messageFamily").asText(),
                context.path("transactionTypeLabel").asText(), context.path("sourceSection").asText(),
                context.path("templateReviewStatus").asText(), context.path("segment").asText(),
                context.path("segmentPresence").asText(), context.path("elementPresence").asText(),
                context.path("contextKind").asText(), context.path("evidenceStatus").asText(), context.path("evidence").asText());
            lines.add(fields.stream().map(GenerateAtl105ElementReference::csv).reduce((a, b) -> a + "," + b).orElse(""));
        }
        Files.write(path, lines);
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static void writeMarkdown(Path path, ObjectNode document, ArrayNode elements) throws Exception {
        StringBuilder text = new StringBuilder("# ATL105 All-Element Reference\n\n");
        text.append("**Specification:** ATL105, Release 2026-3\n**Elements:** ").append(elements.size())
            .append(" (source definitions: ").append(document.path("definitionCount").asInt()).append(")\n")
            .append("**Status:** SOURCE_DERIVED_REVIEW_REQUIRED; no element combination is automatically approved.\n\n");
        text.append("## What This Matrix Covers\n\n")
            .append("The JSON and CSV exports provide Chapter 13 type, length, representation and valid-code evidence; Segment 11 template contexts; Segment 12/13 rule references and BR candidates; dependency candidates; and explicit absence/conditional rule candidates. ")
            .append("The 23 source-defined transaction codes are listed only as Element 78 Prompt Code components. See `atl105-element-transaction-applicability.json` and `.csv` for the exhaustive 231 x 23 review matrix. Transaction-code applicability for other elements is not exhaustively specified in the extracted knowledge base and remains REVIEW_REQUIRED. Message-family labels are not a complete transaction-code cross-product.\n\n");
        text.append("## Reading Absence\n\n")
            .append("`NOT_LISTED_IN_TEMPLATE` means the extracted Section 11 template has no field row; it does **not** mean prohibited or not transmitted. `NOT_EXPLICITLY_ESTABLISHED` means no direct absence rule was found by this extractor. Explicit source-rule candidates still require context review before an element can be called prohibited or not applicable. Conditional, optional, provisional, repeated, and duplicate definitions remain distinct.\n\n");
        text.append("## Field Rule Decision Contract\n\n")
            .append("Each element/family/segment context carries `fieldRuleDecision` with separate applicability, conditional-trigger, permitted-omission, and cross-segment-dependency states. R/O/C markers are scoped to the declared Section 11 template. Conditional description text is preserved verbatim but is not treated as a resolved trigger; dependency links are element-level candidates until mapped to a specific context. Every generated decision remains `REVIEW_REQUIRED`, and whole-observation validation cannot pass while these rules are unresolved.\n\n");
        text.append("## Validation\n\n").append("Use `Atl105ElementReferenceValidator` with a 2026-3 observation containing message family, segment instances and element-number-keyed string values. Baseline overlength/numeric shape checks run where Chapter 13 extraction is unambiguous; contextual profiles/rules are applied only where the existing Test Solution has source and catalog evidence. Unknown combinations remain REVIEW_REQUIRED.\n\n");
        text.append("Example observation:\n\n```json\n{\n  \"specificationVersion\": \"2026-3\",\n  \"messageFamily\": \"Financial Transaction Request\",\n  \"transactionTypeCode\": \"0\",\n  \"completeness\": \"PARTIAL\",\n  \"segments\": [{\"segment\": \"100\", \"elements\": {\"84\": \"001\", \"85\": \"100\", \"86\": \"123456\"}}]\n}\n```\n\n");
        text.append("Regenerate from the Java module with `GenerateAtl105ElementReference specifications/ATL105`; validate with `Atl105ElementReferenceValidator specifications/ATL105 observation.json report.json`. Export the Excel workbook with `powershell -File scripts/powershell/export-atl105-element-reference-to-excel.ps1`. The validator reports CHECKS_PASSED only for checks backed by the available source/profile evidence.\n\n");
        text.append("## Element Summary\n\n| Element | Name(s) | Constraints | Segments | Message families | Segment 100 transaction codes | Catalog rules | Existing BR candidates | Absence status |\n|---:|---|---|---|---|---|---:|---:|---|");
        for (JsonNode element : elements) {
            List<String> names = new ArrayList<>(); element.path("sourceNames").forEach(v -> names.add(v.asText()));
            text.append("\n|").append(element.path("element").asText()).append('|')
                .append(String.join("; ", names).replace('|', '/')).append('|')
                .append(constraintsSummary(element).replace('|', '/')).append('|')
                .append(String.join(";", explicitSegments(element))).append('|')
                .append(String.join(";", familyNames(element))).append('|')
                .append(transactionCodes(element)).append('|')
                .append(element.path("businessRules").size()).append('|')
                .append(brIds(element.path("businessRules")).replace('|', '/')).append('|')
                .append(element.path("absenceConclusion").asText()).append('|');
        }
        text.append("\n\nDetailed definitions, source excerpts, field usages, dependency candidates and context statuses are in the adjacent JSON and CSV files.\n");
        Files.createDirectories(path.getParent());
        Files.writeString(path, text);
    }

    private static String transactionCodes(JsonNode element) {
        List<String> values = new ArrayList<>();
        element.path("transactionTypeCodes").forEach(tx -> values.add(tx.path("code").asText()));
        return values.isEmpty() ? "Not mapped per element" : String.join(";", values);
    }
}
