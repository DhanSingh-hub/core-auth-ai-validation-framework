package com.coreauth.validator.validation;

import com.coreauth.validator.validation.Atl105ElementValueValidator.Finding;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Result;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Read-only adapter from AI qe-shaped payloads to element-number observations. */
public final class Atl105AiElementIntake {
    public record Observed(String segment, Result result) { }
    public record MessageAssessment(String testCaseId, Status status, List<Observed> observations, List<Finding> intakeFindings,
                                    String scenarioType, String negativeClass, String violatedElement, Set<String> provisional) { }
    private static final Pattern SEGMENT_CATALOG = Pattern.compile("kb/segment-([0-9A-Z]+)/");
    private final Atl105ElementValueValidator validator;
    private final JsonNode crosswalk;
    private final Map<String, String> aliases = new HashMap<>();
    private final Map<String, Set<String>> names = new HashMap<>();
    private final Map<String, Set<String>> segmentElements = new HashMap<>();
    private final Map<String, Long> mappingCounts = new TreeMap<>();
    private final Set<String> families = new HashSet<>();

    public Atl105AiElementIntake(Path pack) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        validator = new Atl105ElementValueValidator(pack);
        crosswalk = mapper.readTree(pack.resolve("docs/specs/kb/elements/ai-intake-crosswalk.json").toFile());
        if (!"ATL105".equals(crosswalk.path("specification").asText()) || !"2026-3".equals(crosswalk.path("specificationVersion").asText()))
            throw new IOException("Unsupported AI intake crosswalk");
        Path inventoryFile = pack.resolve("test-output/test-solution-independent-review/all-element-inventory.json");
        mapper.readTree(pack.resolve("docs/atl105_complete_templates.json").toFile()).path("message_templates").fieldNames().forEachRemaining(families::add);
        if (!Files.isRegularFile(inventoryFile)) throw new IOException("Run Atl105ElementInventory before AI intake");
        for (JsonNode element : mapper.readTree(inventoryFile.toFile()).path("elements")) {
            String id = element.path("element").asText();
            element.path("definitions").forEach(definition -> names.computeIfAbsent(normalize(definition.path("name").asText()), key -> new HashSet<>()).add(id));
            for (JsonNode rule : element.path("catalogRules")) {
                Matcher segment = SEGMENT_CATALOG.matcher(rule.path("sourceFile").asText());
                if (segment.find()) segmentElements.computeIfAbsent(segment.group(1), key -> new HashSet<>()).add(id);
            }
        }
        try (var files = Files.list(pack.resolve("contract"))) {
            for (Path file : files.filter(path -> path.getFileName().toString().matches("segment-.+-field-alias-crosswalk\\.json")).sorted().toList()) {
                JsonNode document = mapper.readTree(file.toFile());
                if (!"2026-3".equals(document.path("specificationVersion").asText())) continue;
                for (JsonNode alias : document.path("aliases"))
                    aliases.put(document.path("segment").asText() + "|" + alias.path("aiElementName").asText(), alias.path("testElementNumber").asText());
            }
        }
    }

    public MessageAssessment assess(JsonNode meta, JsonNode payload) {
        String testCase = meta.path("testCaseId").asText("");
        String scenario = meta.path("scenarioType").asText("");
        String negativeClass = meta.path("negativeClass").asText("");
        Set<String> violatedCandidates = names.getOrDefault(normalize(meta.path("violatedElementName").asText("")), Set.of());
        String violated = violatedCandidates.size() == 1 ? violatedCandidates.iterator().next() : "";
        Set<String> provisional = new HashSet<>();
        List<Finding> intake = new ArrayList<>();
        List<Observed> observations = new ArrayList<>();
        if (payload == null || !payload.isObject() || payload.size() != 1) {
            intake.add(review("", "AI payload must have exactly one root object"));
            return new MessageAssessment(testCase, Status.REVIEW_REQUIRED, observations, intake, scenario, negativeClass, violated, provisional);
        }
        var root = payload.fields().next();
        JsonNode family = crosswalk.path("rootFamilies").path(root.getKey());
        if (family.isMissingNode()) {
            intake.add(review("", "AI root label has no reviewed family mapping"));
            return new MessageAssessment(testCase, Status.REVIEW_REQUIRED, observations, intake, scenario, negativeClass, violated, provisional);
        }
        Map<String, ObjectNode> segments = new LinkedHashMap<>();
        for (JsonNode field : meta.path("fields")) {
            String segment = field.path("segment").asText();
            String element = map(segment, field.path("element").asText(), field.path("specElementName").asText());
            ObjectNode elements = segments.computeIfAbsent(segment, key -> JsonNodeFactory.instance.objectNode());
            if (element != null && field.path("provisional").asBoolean()) provisional.add(segment + "|" + element);
            if (element == null) intake.add(review("", "Unmapped AI field " + segment + "/" + field.path("element").asText()));
            else if (!field.path("value").isTextual()) intake.add(review(element, "Non-text AI value in segment " + segment));
            else if (elements.has(element)) intake.add(review(element, "Two AI fields map to one element in segment " + segment));
            else elements.set(element, field.path("value"));
        }
        String familyName = segments.containsKey(family.path("emvSegment").asText()) ? family.path("emvFamily").asText() : family.path("family").asText();
        String declared = meta.path("messageFamily").asText("");
        String resolved = families.contains(declared) ? declared : families.contains(declared.replaceAll(" Request$", "")) ? declared.replaceAll(" Request$", "") : null;
        if (declared.endsWith("Response") && root.getKey().endsWith("Request"))
            intake.add(new Finding("", Status.INVALID, "", "AI metadata declares a response family but the payload root is a request"));
        else if (resolved == null) intake.add(review("", "AI metadata family label is not a Section 11 message family; payload-root family used"));
        else familyName = resolved;
        final String validatedFamily = familyName;
        segments.forEach((segment, elements) -> observations.add(new Observed(segment, validator.validate(observation(validatedFamily, segment, elements)))));
        ObjectNode envelope = JsonNodeFactory.instance.objectNode();
        int count = 0;
        var children = root.getValue().fields();
        while (children.hasNext()) {
            var child = children.next();
            if (child.getValue().isObject()) {
                count++;
                continue;
            }
            String element = crosswalk.path("envelopeFields").path(child.getKey()).path("element").asText("");
            if (element.isBlank()) intake.add(review("", "Unmapped AI envelope field " + child.getKey()));
            else envelope.set(element, child.getValue());
        }
        ObjectNode message = observation(validatedFamily, "MESSAGE", envelope);
        message.putObject("qualifiers").put("dataSegmentCount", String.valueOf(count));
        if (segments.size() == count) {
            ArrayNode present = message.putArray("segmentsPresent");
            segments.keySet().forEach(present::add);
        } else {
            intake.add(review("", "Meta segment identities do not match the payload segment count"));
        }
        observations.add(new Observed("MESSAGE", validator.validate(message)));
        Status status = observations.stream().anyMatch(item -> item.result().status() == Status.INVALID)
            || intake.stream().anyMatch(item -> item.status() == Status.INVALID) ? Status.INVALID
            : intake.isEmpty() && observations.stream().allMatch(item -> item.result().status() == Status.CHECKS_PASSED)
                ? Status.CHECKS_PASSED : Status.REVIEW_REQUIRED;
        return new MessageAssessment(testCase, status, observations, intake, scenario, negativeClass, violated, provisional);
    }

    public Map<String, Long> mappingCounts() {
        return Map.copyOf(mappingCounts);
    }

    private String map(String segment, String aiName, String specName) {
        String alias = aliases.get(segment + "|" + aiName);
        Set<String> named = names.getOrDefault(normalize(aiName), Set.of());
        if (alias != null && named.size() == 1 && !named.contains(alias)) {
            mappingCounts.merge("rejectedAliasContradictsChapter13Name", 1L, Long::sum);
            return null;
        }
        if (alias != null) {
            mappingCounts.merge("contractAlias", 1L, Long::sum);
            return alias;
        }
        Set<String> candidates = names.getOrDefault(normalize(specName), Set.of());
        if (candidates.size() == 1 && segmentElements.getOrDefault(segment, Set.of()).containsAll(candidates)) {
            mappingCounts.merge("uniqueChapter13NameAnchoredInSegmentCatalog", 1L, Long::sum);
            return candidates.iterator().next();
        }
        mappingCounts.merge(candidates.size() > 1 ? "unmappedAmbiguousName" : candidates.isEmpty() ? "unmappedUnknownName" : "unmappedNotInSegmentCatalog", 1L, Long::sum);
        return null;
    }

    private static ObjectNode observation(String family, String segment, ObjectNode elements) {
        ObjectNode observation = JsonNodeFactory.instance.objectNode();
        observation.put("specificationVersion", "2026-3");
        observation.put("messageFamily", family);
        observation.put("segment", segment);
        observation.set("elements", elements);
        return observation;
    }

    private static Finding review(String element, String reason) {
        return new Finding(element, Status.REVIEW_REQUIRED, "", reason);
    }

    private static String normalize(String name) {
        return name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("Usage: Atl105AiElementIntake <ATL105-pack> <qe-shaped-run-folder> [report.json]");
        Path pack = Path.of(args[0]);
        Path run = Path.of(args[1]);
        Path output = args.length > 2 ? Path.of(args[2]) : pack.resolve("test-output/test-solution-independent-review/run2-ai-element-validation.json");
        ObjectMapper mapper = new ObjectMapper();
        Atl105AiElementIntake intake = new Atl105AiElementIntake(pack);
        Map<String, Map<String, Long>> messages = new TreeMap<>();
        Map<String, Map<String, Long>> negatives = new TreeMap<>();
        Map<String, Long> observations = new TreeMap<>();
        Map<String, ObjectNode> findings = new TreeMap<>();
        List<Path> metas;
        try (var files = Files.list(run)) {
            metas = files.filter(path -> path.getFileName().toString().endsWith(".meta.json")).sorted().toList();
        }
        for (Path metaFile : metas) {
            Path payloadFile = metaFile.resolveSibling(metaFile.getFileName().toString().replace(".meta.json", ".json"));
            JsonNode payload = Files.isRegularFile(payloadFile) ? mapper.readTree(payloadFile.toFile()) : null;
            MessageAssessment assessment = intake.assess(mapper.readTree(metaFile.toFile()), payload);
            String kind = "negative".equals(assessment.scenarioType()) ? "negative" : "nonNegative";
            messages.computeIfAbsent(assessment.scenarioType().isBlank() ? "unspecified" : assessment.scenarioType(), key -> new TreeMap<>())
                .merge(assessment.status().name(), 1L, Long::sum);
            for (Finding finding : assessment.intakeFindings()) record(findings, "INTAKE", finding, assessment.testCaseId(), kind, false);
            boolean onViolated = false;
            for (Observed observed : assessment.observations()) {
                observations.merge(observed.result().status().name(), 1L, Long::sum);
                for (Finding finding : observed.result().findings()) {
                    record(findings, observed.segment(), finding, assessment.testCaseId(), kind,
                        assessment.provisional().contains(observed.segment() + "|" + finding.element()));
                    if (finding.status() == Status.INVALID && !assessment.violatedElement().isBlank()
                        && finding.element().equals(assessment.violatedElement())) onViolated = true;
                }
            }
            if (kind.equals("negative")) {
                Map<String, Long> row = negatives.computeIfAbsent(assessment.negativeClass().isBlank() ? "unspecified" : assessment.negativeClass(), key -> new TreeMap<>());
                row.merge("cases", 1L, Long::sum);
                if (assessment.status() == Status.INVALID) row.merge("detectedInvalid", 1L, Long::sum);
                if (onViolated) row.merge("detectedOnDeclaredViolatedElement", 1L, Long::sum);
                if (assessment.violatedElement().isBlank()) row.merge("declaredViolatedElementUnresolved", 1L, Long::sum);
            }
        }
        ObjectNode report = JsonNodeFactory.instance.objectNode();
        report.put("artifact", "ATL105-AI-RUN-ELEMENT-VALIDATION");
        report.put("specificationVersion", "2026-3");
        report.put("runFolder", run.getFileName().toString());
        report.put("messagesAssessed", metas.size());
        report.put("policy", "Read-only. AI values are not copied into this report. REVIEW_REQUIRED is not a pass; CHECKS_PASSED is not business approval. INVALID on AI negative scenarios is expected detection; INVALID on other scenarios is an AI artifact defect candidate.");
        report.set("messagesByScenarioTypeAndStatus", mapper.valueToTree(messages));
        report.set("negativeScenarioDetection", mapper.valueToTree(negatives));
        report.set("observationsByStatus", mapper.valueToTree(observations));
        report.set("fieldMapping", mapper.valueToTree(new TreeMap<>(intake.mappingCounts())));
        ArrayNode list = report.putArray("findings");
        findings.values().stream().sorted((left, right) -> Long.compare(right.path("count").asLong(), left.path("count").asLong())).forEach(list::add);
        Files.createDirectories(output.toAbsolutePath().getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), report);
        System.out.println("messages=" + metas.size() + " observations=" + observations + " negatives=" + negatives + " mapping=" + new TreeMap<>(intake.mappingCounts()));
    }

    private static void record(Map<String, ObjectNode> findings, String scope, Finding finding, String testCase, String kind, boolean provisional) {
        String key = String.join("|", kind, String.valueOf(provisional), scope, finding.element(), finding.status().name(), finding.ruleId(), finding.reason());
        ObjectNode entry = findings.computeIfAbsent(key, ignored -> {
            ObjectNode created = JsonNodeFactory.instance.objectNode();
            created.put("scenarioKind", kind);
            created.put("aiValueMarkedProvisional", provisional);
            created.put("scope", scope);
            created.put("element", finding.element());
            created.put("status", finding.status().name());
            created.put("ruleId", finding.ruleId());
            created.put("reason", finding.reason());
            created.put("count", 0);
            created.putArray("sampleTestCaseIds");
            return created;
        });
        entry.put("count", entry.path("count").asLong() + 1);
        if (entry.path("sampleTestCaseIds").size() < 5) ((ArrayNode) entry.get("sampleTestCaseIds")).add(testCase);
    }
}
