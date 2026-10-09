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
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
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
    private record ActualSegment(String name, String segment, JsonNode body) { }
    public record MessageAssessment(String testCaseId, Status status, List<Observed> observations, List<Finding> intakeFindings,
                                    String scenarioType, String negativeClass, String violatedElement, Set<String> provisional) { }
    private static final Pattern SEGMENT_CATALOG = Pattern.compile("kb/segment-([0-9A-Z]+)/");
    private final Atl105ElementValueValidator validator;
    private final Chapter13DataElementValidator semanticValidator;
    private final boolean chapter13Semantics;
    private final com.coreauth.validator.canonical.Segment119ActualPayloadAdapter segment119Adapter;
    private final JsonNode crosswalk;
    private final Map<String, String> aliases = new HashMap<>();
    private final Map<String, Set<String>> names = new HashMap<>();
    private final Map<String, Set<String>> segmentElements = new HashMap<>();
    private final Map<String, Long> mappingCounts = new TreeMap<>();
    private final Set<String> families = new HashSet<>();

    public Atl105AiElementIntake(Path pack) throws IOException {
        this(pack, false);
    }

    public Atl105AiElementIntake(Path pack, boolean chapter13Semantics) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        validator = new Atl105ElementValueValidator(pack);
        semanticValidator = chapter13Semantics ? new Chapter13DataElementValidator(pack) : null;
        segment119Adapter = chapter13Semantics ? new com.coreauth.validator.canonical.Segment119ActualPayloadAdapter(
                mapper.readTree(pack.resolve("test-output").resolve("test-solution-independent-review").resolve("all-element-inventory.json").toFile())) : null;
        this.chapter13Semantics = chapter13Semantics;
        crosswalk = mapper.readTree(pack.resolve("docs/specs/kb/elements/ai-intake-crosswalk.json").toFile());
        if (!"ATL105".equals(crosswalk.path("specification").asText()) || !"2026-3".equals(crosswalk.path("specificationVersion").asText()))
            throw new IOException("Unsupported AI intake crosswalk");
        Path inventoryFile = pack.resolve("test-output/test-solution-independent-review/all-element-inventory.json");
        mapper.readTree(pack.resolve("docs/atl105_complete_templates.json").toFile()).path("message_templates").fieldNames().forEachRemaining(families::add);
        for (JsonNode root : crosswalk.path("rootFamilies")) {
            if (!families.contains(root.path("family").asText()) || root.path("evidence").asText().isBlank()
                    || root.has("emvFamily") && !families.contains(root.path("emvFamily").asText())) {
                throw new IOException("AI root crosswalk lacks a known source family or evidence");
            }
        }
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
        if (chapter13Semantics) return assessSemantic(meta, payload);
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

    private MessageAssessment assessSemantic(JsonNode meta, JsonNode payload) {
        if (meta == null || !meta.isObject()) {
            return new MessageAssessment("", Status.INVALID, List.of(), List.of(invalid("", "AI metadata must be an object")),
                    "", "", "", Set.of());
        }
        String testCase = meta.path("testCaseId").asText("");
        String scenario = meta.path("scenarioType").asText("");
        String negativeClass = meta.path("negativeClass").asText("");
        Set<String> violatedCandidates = names.getOrDefault(normalize(meta.path("violatedElementName").asText("")), Set.of());
        String violated = violatedCandidates.size() == 1 ? violatedCandidates.iterator().next() : "";
        List<Finding> intake = new ArrayList<>();
        List<Observed> observations = new ArrayList<>();
        Set<String> provisional = new HashSet<>();
        if (payload == null || !payload.isObject() || payload.size() != 1) {
            return new MessageAssessment(testCase, Status.INVALID, List.of(), List.of(invalid("", "AI payload must have exactly one root object")),
                    scenario, negativeClass, violated, Set.of());
        }
        var root = payload.fields().next();
        JsonNode rootFamily = crosswalk.path("rootFamilies").path(root.getKey());
        boolean canonicalRoot = families.contains(root.getKey());
        if ((rootFamily.isMissingNode() && !canonicalRoot) || !root.getValue().isObject()) {
            return new MessageAssessment(testCase, Status.REVIEW_REQUIRED, List.of(), List.of(review("", "Unknown AI root family or non-object message")),
                    scenario, negativeClass, violated, Set.of());
        }
        if (meta.has("specificationVersion") && !"2026-3".equals(meta.path("specificationVersion").asText())) {
            intake.add(invalid("", "AI metadata version contradicts the 2026-3 source contract"));
        }
        Map<String, List<ActualSegment>> actual = new LinkedHashMap<>();
        List<ActualSegment> occurrences = new ArrayList<>();
        ObjectNode envelope = JsonNodeFactory.instance.objectNode();
        var children = root.getValue().fields();
        int count = 0;
        while (children.hasNext()) {
            var child = children.next();
            if (child.getValue().isObject()) {
                count++;
                JsonNode type = child.getValue().get("SegmentType");
                if (type == null && child.getValue().path("DataTypeIndicator").isTextual()) {
                    String load = Chapter13ContextValidator.segmentForLoadIndicator(child.getValue().path("DataTypeIndicator").textValue());
                    if (load != null) {
                        type = JsonNodeFactory.instance.textNode(load);
                        mappingCounts.merge("actualSourceLoadIndicator", 1L, Long::sum);
                    }
                }
                if (type == null || !type.isTextual() || !type.textValue().matches("[0-9]{3}|DL[1-8]")) {
                    intake.add(review("", "Actual AI segment has no explicit textual source identity: " + child.getKey()));
                } else {
                    ActualSegment occurrence = new ActualSegment(child.getKey(), type.textValue(), child.getValue());
                    actual.computeIfAbsent(type.textValue(), key -> new ArrayList<>()).add(occurrence);
                    occurrences.add(occurrence);
                }
            } else {
                String element = crosswalk.path("envelopeFields").path(child.getKey()).path("element").asText("");
                if (element.isBlank()) {
                    Set<String> candidates = names.getOrDefault(normalize(child.getKey()), Set.of());
                    if (candidates.size() == 1 && Set.of("55", "63").containsAll(candidates)) {
                        element = candidates.iterator().next();
                        mappingCounts.merge("actualEnvelopeUniqueChapter13Name", 1L, Long::sum);
                    }
                }
                if (element.isBlank()) intake.add(review("", "Unmapped AI envelope field " + child.getKey()));
                else envelope.set(element, child.getValue());
            }
        }
        String familyName = canonicalRoot ? root.getKey() : actual.containsKey(rootFamily.path("emvSegment").asText())
                ? rootFamily.path("emvFamily").asText() : rootFamily.path("family").asText();
        String declared = meta.path("messageFamily").asText("");
        String resolved = families.contains(declared) ? declared
                : families.contains(declared.replaceAll(" Request$", "")) ? declared.replaceAll(" Request$", "") : null;
        if (declared.endsWith("Response") && root.getKey().endsWith("Request")) {
            intake.add(invalid("", "AI metadata declares a response family but the payload root is a request"));
        } else if (resolved == null) intake.add(review("", "AI metadata family label is not a Section 11 message family; payload-root family used"));
        else if (!familyName.equals(resolved)) intake.add(invalid("", "Metadata message family contradicts the actual payload segment/root family"));
        final String actualFamily = familyName;
        for (ActualSegment occurrence : occurrences) {
            if (!"119".equals(occurrence.segment())) continue;
            var assessed = segment119Adapter.validate(occurrence.body(), actualFamily, meta);
            for (var finding : assessed.findings()) {
                intake.add(new Finding("", switch (finding.status()) {
                    case INVALID -> Status.INVALID;
                    case CHECKS_PASSED -> Status.CHECKS_PASSED;
                    case REVIEW_REQUIRED -> Status.REVIEW_REQUIRED;
                }, finding.ruleId(), finding.reason()));
            }
        }
        Map<String, ObjectNode> numbered = new LinkedHashMap<>();
        Set<String> metaIdentities = new HashSet<>();
        Map<String, String> metadataMappings = new HashMap<>();
        if (meta.has("fields") && !meta.path("fields").isArray()) intake.add(invalid("", "AI metadata fields must be an array"));
        for (JsonNode field : meta.path("fields")) {
            String segment = field.path("segment").asText();
            String aiName = field.path("element").asText();
            String element = map(segment, aiName, field.path("specElementName").asText());
            if (element == null) {
                intake.add(review("", "Unmapped AI metadata field " + segment + "/" + aiName));
                continue;
            }
            List<ActualSegment> candidates = actual.getOrDefault(segment, List.of());
            String friendlyName = field.path("segmentFriendlyName").asText("");
            List<ActualSegment> named = candidates.stream().filter(candidate -> candidate.name().equals(friendlyName)).toList();
            ActualSegment occurrence = named.size() == 1 ? named.get(0) : candidates.size() == 1 ? candidates.get(0) : null;
            if (occurrence == null) {
                intake.add(review(element, candidates.isEmpty() ? "Mapped metadata has no corresponding actual payload segment"
                        : "Repeated actual segments require an unambiguous metadata occurrence name; metadata value not validated"));
                continue;
            }
            if (!metaIdentities.add(occurrence.name() + "|" + element)) {
                intake.add(invalid(element, "Multiple AI metadata fields claim the same numbered identity"));
                continue;
            }
            JsonNode value = occurrence.body().get(aiName);
            if (value == null) {
                intake.add(review(element, "Mapped metadata has no corresponding actual payload field; metadata-only value not validated"));
                continue;
            }
            if (!field.path("value").equals(value)) intake.add(invalid(element, "AI metadata value contradicts actual payload value"));
            if (!value.isTextual()) intake.add(invalid(element, "Actual AI scalar representation is non-text; no metadata coercion"));
            numbered.computeIfAbsent(occurrence.name(), key -> JsonNodeFactory.instance.objectNode()).set(element, value);
            metadataMappings.put(occurrence.name() + "|" + aiName, element);
            if (field.path("provisional").asBoolean(false)) provisional.add(segment + "|" + element);
        }
        occurrences.forEach(occurrence -> {
            String segment = occurrence.segment();
            ObjectNode values = numbered.computeIfAbsent(occurrence.name(), key -> JsonNodeFactory.instance.objectNode());
            Set<String> actualIdentities = new HashSet<>();
            var fields = occurrence.body().fields();
            while (fields.hasNext()) {
                var field = fields.next();
                String element = metadataMappings.get(occurrence.name() + "|" + field.getKey());
                if (element == null) element = map(segment, field.getKey(), field.getKey());
                if (element == null) {
                    intake.add(review("", "Actual AI field has no source-grounded mapping: " + segment + "/" + field.getKey()));
                    continue;
                }
                if (!actualIdentities.add(element)) {
                    intake.add(invalid(element, "Multiple actual AI aliases collapse onto one numbered identity"));
                } else values.set(element, field.getValue());
            }
            ObjectNode observation = observation(actualFamily, segment, values);
            observation.put("completeness", "PARTIAL");
            for (String field : List.of("qualifiers", "history", "records", "representation")) {
                if (meta.has(field)) observation.set(field, meta.path(field));
            }
            observations.add(new Observed(segment, semanticValidator.validate(observation)));
        });
        ObjectNode message = observation(actualFamily, "MESSAGE", envelope);
        ObjectNode qualifiers = meta.path("qualifiers").isObject() ? meta.path("qualifiers").deepCopy() : JsonNodeFactory.instance.objectNode();
        qualifiers.put("dataSegmentCount", String.valueOf(count));
        message.set("qualifiers", qualifiers);
        if (occurrences.size() == count) {
            ArrayNode present = message.putArray("segmentsPresent");
            occurrences.forEach(occurrence -> present.add(occurrence.segment()));
        } else intake.add(review("", "Actual segment identity mapping is incomplete; no complete-envelope verdict"));
        observations.add(new Observed("MESSAGE", semanticValidator.validate(message)));
        Status status = observations.stream().anyMatch(item -> item.result().status() == Status.INVALID)
                || intake.stream().anyMatch(item -> item.status() == Status.INVALID) ? Status.INVALID : Status.REVIEW_REQUIRED;
        intake.add(review("", "Actual payload was evaluated independently; aliases/metadata/history claims do not confirm producer matches or host authenticity"));
        return new MessageAssessment(testCase, status, List.copyOf(observations), List.copyOf(intake), scenario, negativeClass, violated, Set.copyOf(provisional));
    }

    private static Finding invalid(String element, String reason) {
        return new Finding(element, Status.INVALID, "CH13-AI-INTAKE", reason);
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
        if (args.length < 2 || args.length > 4 || args.length == 4 && !"--chapter13-semantics".equals(args[3]))
            throw new IllegalArgumentException("Usage: Atl105AiElementIntake <ATL105-pack> <qe-shaped-run-folder> [report.json] [--chapter13-semantics]");
        Path pack = Path.of(args[0]);
        Path run = Path.of(args[1]);
        Path output = args.length > 2 ? Path.of(args[2]) : pack.resolve("test-output/test-solution-independent-review/run2-ai-element-validation.json");
        if (output.toAbsolutePath().normalize().startsWith(run.toAbsolutePath().normalize())) {
            throw new IllegalArgumentException("Intake report must be written outside the producer input folder");
        }
        ObjectMapper mapper = new ObjectMapper();
        Atl105AiElementIntake intake = new Atl105AiElementIntake(pack, args.length == 4);
        Map<String, Map<String, Long>> messages = new TreeMap<>();
        Map<String, Map<String, Long>> negatives = new TreeMap<>();
        Map<String, Long> observations = new TreeMap<>();
        Map<String, ObjectNode> findings = new TreeMap<>();
        ArrayNode executions = JsonNodeFactory.instance.arrayNode();
        List<Path> metas;
        try (var files = Files.list(run)) {
            metas = files.filter(path -> path.getFileName().toString().endsWith(".meta.json")).sorted().toList();
        }
        for (Path metaFile : metas) {
            Path payloadFile = metaFile.resolveSibling(metaFile.getFileName().toString().replace(".meta.json", ".json"));
            JsonNode payload = Files.isRegularFile(payloadFile) ? mapper.readTree(payloadFile.toFile()) : null;
            MessageAssessment assessment = intake.assess(mapper.readTree(metaFile.toFile()), payload);
            ObjectNode execution = executions.addObject().put("testCaseId", assessment.testCaseId())
                    .put("metadataFile", metaFile.getFileName().toString()).put("payloadFile", payloadFile.getFileName().toString())
                    .put("status", assessment.status().name()).put("observations", assessment.observations().size());
            execution.put("metadataSha256", HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(metaFile))));
            if (payload != null) execution.put("payloadSha256", HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(payloadFile))));
            else execution.putNull("payloadSha256");
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
        Path packRoot = pack.toAbsolutePath().normalize(), runRoot = run.toAbsolutePath().normalize();
        if (runRoot.startsWith(packRoot)) report.put("runRelativeToPack", packRoot.relativize(runRoot).toString().replace('\\', '/'));
        report.put("messagesAssessed", metas.size());
        report.put("chapter13Semantics", args.length == 4);
        report.put("policy", "Read-only. AI values are not copied into this report. REVIEW_REQUIRED is not a pass; CHECKS_PASSED is not business approval. INVALID on AI negative scenarios is expected detection; INVALID on other scenarios is an AI artifact defect candidate.");
        report.set("messagesByScenarioTypeAndStatus", mapper.valueToTree(messages));
        report.set("negativeScenarioDetection", mapper.valueToTree(negatives));
        report.set("observationsByStatus", mapper.valueToTree(observations));
        report.set("fieldMapping", mapper.valueToTree(new TreeMap<>(intake.mappingCounts())));
        report.set("executions", executions);
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
