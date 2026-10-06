package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import java.util.Set;

public final class Atl105ElementInventory {
    private static final Pattern HEADING = Pattern.compile("(?m)^Number:[ \\t]*(\\d+)[ \\t]+Name:[ \\t]*([^\\r\\n]+)");
    private static final Pattern CHAPTER_END = Pattern.compile("(?m)^14\\.[ \\t]+Support, Testing, and Certification");
    private static final Pattern TYPE_LENGTH = Pattern.compile("Character Type:[ \\t]*([A-Za-z0-9/]+)[ \\t]+(?:(Maximum|Max\\.|Fixed|Variable)[ \\t]+)?Length:[ \\t]*(?:([0-9,]+)[ \\t]*bytes?\\b)?");
    private static final Pattern VARIABLE_TEXT = Pattern.compile("(?i)^(?:variable|maximum variable)\\b");
    private static final Pattern REPRESENTATION = Pattern.compile("(?s)Representation:[ \\t]*(.*?)(?=Purpose:|Processing Rules:|Valid Codes/Values:|$)");
    private static final Set<String> SUPPORTED_TYPES = Set.of("A", "N", "AN", "ANS", "ANSB");

    public static void main(String[] args) throws Exception {
        Path pack = args.length == 0 ? Atl105Paths.root() : Path.of(args[0]);
        Path output = args.length < 2 ? pack.resolve("test-output/test-solution-independent-review/all-element-inventory.json") : Path.of(args[1]);
        ObjectMapper mapper = new ObjectMapper();
        Path sourceFile = pack.resolve("docs/specs/extracted_text.txt");
        String source = Files.readString(sourceFile);
        Atl105ElementInventory generator = new Atl105ElementInventory();
        ObjectNode inventory = generator.extract(source);
        inventory.put("artifact", "ATL105-ALL-ELEMENT-SOURCE-AND-BR-INVENTORY");
        inventory.put("specification", "ATL105");
        inventory.put("specificationVersion", "2026-3");
        inventory.put("sourceFile", "docs/specs/extracted_text.txt");
        inventory.put("sourceSha256", HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
            .digest(source.getBytes(StandardCharsets.UTF_8))));
        inventory.put("policy", "Element identity locates existing evidence; it does not establish semantic equivalence or approval. No BRs are created or promoted by this inventory.");
        ArrayNode inputs = inventory.putArray("inputs");
        for (String directory : List.of("docs/specs/kb", "test-output/test-brs", "test-output/test-json", "test-output/test-solution-independent-review")) {
            Path folder = pack.resolve(directory);
            if (!Files.isDirectory(folder)) continue;
            try (var files = Files.walk(folder)) {
                for (Path file : files.filter(path -> path.toString().endsWith(".json")).sorted().toList()) {
                    String name = file.getFileName().toString();
                    if (file.toAbsolutePath().normalize().equals(output.toAbsolutePath().normalize())
                        || name.startsWith("ai-") || name.startsWith("atomic-br-ai-")
                        || name.startsWith("semantic-br-") || name.equals("all-element-inventory.json")) continue;
                    JsonNode document = mapper.readTree(file.toFile());
                    if (name.endsWith("-rule-catalog.json") || document.path("businessRequirements").isArray()) {
                        String relative = pack.relativize(file).toString().replace('\\', '/');
                        inputs.add(relative);
                        generator.linkRequirements(inventory, document, relative);
                    }
                }
            }
        }
        generator.linkTemplates(inventory, mapper.readTree(pack.resolve("docs/atl105_complete_templates.json").toFile()));
        Path profilesFile = pack.resolve("docs/specs/kb/elements/validation-profiles.json");
        if (Files.isRegularFile(profilesFile)) {
            for (JsonNode profile : mapper.readTree(profilesFile.toFile()).path("profiles")) {
                JsonNode element = inventory.path("elements").get(profile.path("element").asText());
                if (element != null) ((ArrayNode) element.get("contextualValidationProfiles")).add(profile);
            }
        }
        Path legacyFile = pack.getParent().getParent().resolve("src/main/resources/atl105/spec-elements.json");
        if (Files.isRegularFile(legacyFile)) {
            for (JsonNode entry : mapper.readTree(legacyFile.toFile())) {
                ObjectNode element = (ObjectNode) inventory.path("elements").get(entry.path("elementNumber").asText());
                if (element != null) ((ArrayNode) element.get("legacyFieldRules")).add(entry);
            }
        }
        Path module = pack.getParent().getParent();
        Set<String> implemented = ruleTokens(module.resolve("src/main/java"));
        Set<String> tested = ruleTokens(module.resolve("src/test"));
        ObjectNode summary = inventory.putObject("summary");
        ArrayNode reviewQueue = inventory.putArray("reviewQueue");
        int withRules = 0;
        int withBrs = 0;
        int withTemplates = 0;
        int withConstraints = 0;
        int withImplemented = 0;
        int withTested = 0;
        int withProfiles = 0;
        StringBuilder markdown = new StringBuilder("# ATL105 Element Inventory\n\nSource/BR reconciliation only. NOT full semantic validation or model training. Approved = 0 until human review.\n\n| Element | Source name(s) | Constraint | Catalog refs | BR refs | Template refs | Code refs | Test refs | Profiles |\n|---|---|---|---:|---:|---:|---:|---:|---:|\n");
        for (JsonNode node : inventory.path("elements")) {
            ObjectNode element = (ObjectNode) node;
            Set<String> ruleIds = new java.util.TreeSet<>();
            element.path("catalogRules").forEach(rule -> ruleIds.add(rule.path("id").asText()));
            long code = ruleIds.stream().filter(implemented::contains).count();
            long tests = ruleIds.stream().filter(tested::contains).count();
            boolean constrained = element.path("definitions").size() == 1
                && "EXTRACTED".equals(element.path("definitions").path(0).path("constraints").path("extractionStatus").asText());
            ObjectNode stages = element.putObject("stages");
            stages.put("inventoried", true);
            stages.put("baselineConstraintExtracted", constrained);
            stages.put("catalogRuleCandidates", ruleIds.size());
            stages.put("businessRequirementCandidates", element.path("existingBusinessRequirements").size());
            stages.put("rulesReferencedInValidatorCode", code);
            stages.put("rulesReferencedInTests", tests);
            stages.put("contextualProfiles", element.path("contextualValidationProfiles").size());
            stages.put("approved", false);
            ArrayNode reasons = JsonNodeFactory.instance.arrayNode();
            if (element.path("definitions").size() > 1) reasons.add("Multiple Chapter 13 definitions");
            if (!constrained) reasons.add("Baseline type/length constraint not extracted");
            if (ruleIds.isEmpty()) reasons.add("No existing catalog rule candidate");
            if (!ruleIds.isEmpty() && code == 0) reasons.add("Catalog rules not referenced by validator code");
            if (!reasons.isEmpty()) reviewQueue.addObject().put("element", element.path("element").asText()).set("reasons", reasons);
            if (!ruleIds.isEmpty()) withRules++;
            if (!element.path("existingBusinessRequirements").isEmpty()) withBrs++;
            if (!element.path("templateUsages").isEmpty()) withTemplates++;
            if (constrained) withConstraints++;
            if (code > 0) withImplemented++;
            if (tests > 0) withTested++;
            if (!element.path("contextualValidationProfiles").isEmpty()) withProfiles++;
            markdown.append('|').append(element.path("element").asText()).append('|');
            for (JsonNode definition : element.path("definitions")) markdown.append(definition.path("name").asText().replace('|', '/')).append("; ");
            JsonNode constraint = element.path("definitions").path(0).path("constraints");
            markdown.append('|').append(constrained ? constraint.path("characterType").asText() + " " + constraint.path("lengthMode").asText()
                    + " max " + constraint.path("maxLength").asText() : "REVIEW")
                .append('|').append(ruleIds.size()).append('|')
                .append(element.path("existingBusinessRequirements").size()).append('|')
                .append(element.path("templateUsages").size()).append('|').append(code).append('|').append(tests).append('|')
                .append(element.path("contextualValidationProfiles").size()).append("|\n");
        }
        summary.put("elementsWithCatalogReferences", withRules);
        summary.put("elementsWithBrReferences", withBrs);
        summary.put("elementsWithTemplateOccurrences", withTemplates);
        summary.put("elementsWithBaselineConstraints", withConstraints);
        summary.put("elementsWithRulesReferencedInValidatorCode", withImplemented);
        summary.put("elementsWithRulesReferencedInTests", withTested);
        summary.put("elementsWithContextualProfiles", withProfiles);
        summary.put("elementsInReviewQueue", reviewQueue.size());
        summary.put("elementsApproved", 0);
        summary.put("codeReferencePolicy", "Literal rule-ID references only; catalog-driven validators may implement rules without literal references.");
        summary.put("newBusinessRequirementsCreated", 0);
        summary.put("allCombinationsVerified", false);
        summary.put("profilePolicy", "Profile presence documents implemented checks, not coverage of every context or full BR semantics.");
        Files.createDirectories(output.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), inventory);
        Files.writeString(output.resolveSibling("all-element-inventory.md"), markdown);
        System.out.printf("elements=%d definitions=%d catalogLinked=%d brLinked=%d templateLinked=%d constraints=%d code=%d tests=%d profiles=%d review=%d%n",
            inventory.path("elementCount").asInt(), inventory.path("definitionCount").asInt(), withRules, withBrs, withTemplates,
            withConstraints, withImplemented, withTested, withProfiles, reviewQueue.size());
    }

    private static Set<String> ruleTokens(Path folder) throws java.io.IOException {
        Set<String> tokens = new java.util.HashSet<>();
        if (!Files.isDirectory(folder)) return tokens;
        Pattern rule = Pattern.compile("SEG[0-9A-Z]+-R-[0-9]+");
        try (var files = Files.walk(folder)) {
            for (Path file : files.filter(path -> path.toString().endsWith(".java") || path.toString().endsWith(".feature")).toList()) {
                Matcher matcher = rule.matcher(Files.readString(file));
                while (matcher.find()) tokens.add(matcher.group());
            }
        }
        return tokens;
    }

    public void linkRequirements(ObjectNode inventory, JsonNode document, String sourceFile) {
        for (String collection : List.of("rules", "businessRequirements")) {
            if (!document.path(collection).isArray()) continue;
            for (JsonNode requirement : document.path(collection)) {
                ArrayNode anchors = JsonNodeFactory.instance.arrayNode();
                if (requirement.path("sourceAnchor").isObject()) anchors.add(requirement.path("sourceAnchor"));
                if (requirement.path("sourceAnchors").isArray()) anchors.addAll((ArrayNode) requirement.path("sourceAnchors"));
                for (JsonNode anchor : anchors) {
                    for (String id : anchor.path("element").asText("").split("[,|]")) {
                        if (id.isBlank()) continue;
                        JsonNode element = inventory.path("elements").get(id.trim());
                        ObjectNode reference = JsonNodeFactory.instance.objectNode();
                        reference.put("id", requirement.path("ruleId").asText(requirement.path("id").asText("")));
                        reference.put("sourceFile", sourceFile);
                        reference.put("title", requirement.path("title").asText(""));
                        reference.set("sourceAnchor", anchor.deepCopy());
                        reference.put("matchStatus", "ELEMENT_ID_CANDIDATE_NOT_SEMANTIC_MATCH");
                        if (element == null) {
                            ((ArrayNode) inventory.get("unresolvedElementReferences")).add(reference);
                        } else {
                            ((ArrayNode) element.get(collection.equals("rules") ? "catalogRules" : "existingBusinessRequirements")).add(reference);
                        }
                    }
                }
            }
        }
    }

    public void linkTemplates(ObjectNode inventory, JsonNode catalog) {
        var templates = catalog.path("message_templates").fields();
        while (templates.hasNext()) {
            var template = templates.next();
            scanTemplate(inventory, template.getValue(), template.getKey(), "MESSAGE", "");
        }
    }

    private static void scanTemplate(ObjectNode inventory, JsonNode node, String family, String segment, String location) {
        if (node.isArray()) {
            for (int index = 0; index < node.size(); index++) scanTemplate(inventory, node.get(index), family, segment, location + "/" + index);
        } else if (node.isObject()) {
            String owner = node.path("segment_number").asText(segment);
            String id = node.path("element_no").asText("");
            JsonNode element = inventory.path("elements").get(id);
            if (element != null) {
                ObjectNode usage = ((ArrayNode) element.get("templateUsages")).addObject();
                usage.put("messageFamily", family);
                usage.put("segment", owner);
                usage.put("path", location);
                usage.set("field", node.deepCopy());
                usage.put("status", "DERIVED_TEMPLATE_REQUIRES_SOURCE_CROSSCHECK");
            }
            var fields = node.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                if (!field.getKey().equals("extracted_candidate_segments"))
                    scanTemplate(inventory, field.getValue(), family, owner, location + "/" + field.getKey());
            }
        }
    }

    public ObjectNode extract(String source) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        ObjectNode elements = result.putObject("elements");
        result.putArray("unresolvedElementReferences");
        Matcher heading = HEADING.matcher(source);
        int previousStart = -1;
        int previousLine = 0;
        int scanOffset = 0;
        int line = 1;
        String previousId = null;
        String previousName = null;
        int definitions = 0;
        while (heading.find()) {
            if (previousId != null) {
                add(elements, previousId, previousName, previousLine, source.substring(previousStart, heading.start()));
            }
            while (scanOffset < heading.start()) {
                if (source.charAt(scanOffset++) == '\n') line++;
            }
            previousStart = heading.start();
            previousLine = line;
            previousId = heading.group(1);
            previousName = heading.group(2).trim();
            definitions++;
        }
        if (previousId != null) {
            Matcher end = CHAPTER_END.matcher(source);
            int endOffset = end.find(previousStart) ? end.start() : source.length();
            add(elements, previousId, previousName, previousLine, source.substring(previousStart, endOffset));
        }
        result.put("definitionCount", definitions);
        result.put("elementCount", elements.size());
        result.put("semanticApproval", false);
        return result;
    }

    private static void add(ObjectNode elements, String id, String name, int line, String evidence) {
        ObjectNode element;
        if (elements.has(id)) {
            element = (ObjectNode) elements.get(id);
        } else {
            element = elements.putObject(id);
            element.put("element", id);
            element.put("status", "SOURCE_REVIEW_REQUIRED");
            element.putArray("definitions");
            element.putArray("catalogRules");
            element.putArray("existingBusinessRequirements");
            element.putArray("templateUsages");
            element.putArray("legacyFieldRules");
            element.putArray("contextualValidationProfiles");
        }
        ArrayNode definitions = (ArrayNode) element.get("definitions");
        ObjectNode definition = definitions.addObject();
        int typeOffset = evidence.indexOf("Character Type:");
        int nameOffset = evidence.indexOf("Name:");
        String fullName = typeOffset > nameOffset && typeOffset - nameOffset < 160
            ? evidence.substring(nameOffset + 5, typeOffset).replaceAll("\\s+", " ").trim() : name;
        definition.put("name", fullName);
        definition.put("sourceSection", "13.2");
        definition.put("sourceLine", line);
        definition.put("sourceText", evidence.strip());
        definition.set("constraints", constraints(evidence));
        if (definitions.size() > 1) element.put("status", "MULTIPLE_DEFINITIONS_REVIEW_REQUIRED");
    }

    public static ObjectNode constraints(String evidence) {
        ObjectNode constraints = JsonNodeFactory.instance.objectNode();
        Matcher line = TYPE_LENGTH.matcher(evidence);
        Matcher representation = REPRESENTATION.matcher(evidence);
        String text = representation.find() ? representation.group(1).replaceAll("\\s+", " ").trim() : "";
        boolean found = line.find();
        boolean sized = found && line.group(3) != null;
        String label = found && line.group(2) != null ? line.group(2) : "";
        String textMode = text.startsWith("Fixed length") ? "FIXED" : VARIABLE_TEXT.matcher(text).find() ? "VARIABLE" : "UNKNOWN";
        String labelMode = label.equals("Fixed") ? "FIXED" : label.equals("Variable") ? "VARIABLE" : "UNKNOWN";
        String mode = textMode.equals("UNKNOWN") ? labelMode : labelMode.equals("UNKNOWN") || labelMode.equals(textMode) ? textMode : "CONFLICT";
        boolean typed = found && SUPPORTED_TYPES.contains(line.group(1));
        if (typed) constraints.put("characterType", line.group(1));
        if (sized) constraints.put("maxLength", Integer.parseInt(line.group(3).replace(",", "")));
        constraints.put("lengthLabel", label.isEmpty() ? "Length" : label + " Length");
        constraints.put("lengthMode", mode);
        constraints.put("representation", text);
        constraints.put("extractionStatus", typed && sized && (mode.equals("FIXED") || mode.equals("VARIABLE")) ? "EXTRACTED" : "REVIEW_REQUIRED");
        return constraints;
    }
}