package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

/** Records source-cited, append-only SME decisions for one element/family/segment context. */
public final class FieldRuleSmeDecisionRegister {
    private static final String ARTIFACT = "atl105-field-rule-sme-decision-register";
    private static final String SUBJECT_TYPE = "FIELD_RULE_CONTEXT";
    private static final Set<String> APPLICABILITY = Set.of("REQUIRED", "OPTIONAL", "CONDITIONAL", "NOT_APPLICABLE", "UNRESOLVED");
    private static final Set<String> TRIGGERS = Set.of("NOT_APPLICABLE", "SOURCE_STATED", "CONFIGURATION_DEPENDENT", "UNRESOLVED");
    private static final Set<String> OMISSION = Set.of("PERMITTED", "NOT_PERMITTED", "CONDITION_DEPENDENT", "UNRESOLVED");
    private static final Set<String> DEPENDENCIES = Set.of("NONE", "REQUIRES", "CONDITION_DEPENDENT", "MUTUALLY_CONSTRAINED", "UNRESOLVED");
    private static final Set<String> DIMENSIONS = Set.of("applicability", "conditionalTrigger", "permittedOmission", "crossSegmentDependency");
    private static final Pattern PAGE_MARKER = Pattern.compile("(?m)^--- PAGE (\\d+) ---\\s*$");

    private FieldRuleSmeDecisionRegister() { }

    public static void main(String[] args) throws Exception {
        if (args.length == 1 && "--validate".equals(args[0])) {
            validateFromFiles();
            return;
        }
        if (args.length != 2 || !"--decision-json".equals(args[0])) {
            throw new IllegalArgumentException("Usage: FieldRuleSmeDecisionRegister --validate | --decision-json <reviewer-decision.json>");
        }
        Path root = Atl105Paths.root();
        Path reviewQueuePath = root.resolve("test-output/test-solution-independent-review/atl105-field-rule-adjudication-queue.json");
        Path registerPath = root.resolve("test-output/test-solution-independent-review/atl105-field-rule-sme-decision-register.json");
        Path specificationPath = root.resolve("docs/specs/extracted_text.txt");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode queue = mapper.readTree(reviewQueuePath.toFile());
        ObjectNode register = Files.exists(registerPath) ? (ObjectNode) mapper.readTree(registerPath.toFile()) : emptyRegister(mapper);
        JsonNode input = mapper.readTree(Path.of(args[1]).toFile());
        ObjectNode recorded = record(register, queue, input, Files.readString(specificationPath));
        Files.createDirectories(registerPath.getParent());
        mapper.writerWithDefaultPrettyPrinter().writeValue(registerPath.toFile(), register);
        System.out.printf("subject=%s decision=%s revision=%d events=%d%n", recorded.path("subjectId").asText(),
            recorded.path("decision").asText(), recorded.path("revision").asInt(), register.path("decisions").size());
    }

    private static void validateFromFiles() throws Exception {
        Path root = Atl105Paths.root();
        Path queuePath = root.resolve("test-output/test-solution-independent-review/atl105-field-rule-adjudication-queue.json");
        Path registerPath = root.resolve("test-output/test-solution-independent-review/atl105-field-rule-sme-decision-register.json");
        Path specificationPath = root.resolve("docs/specs/extracted_text.txt");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode queue = mapper.readTree(queuePath.toFile());
        JsonNode register = Files.exists(registerPath) ? mapper.readTree(registerPath.toFile()) : emptyRegister(mapper);
        Map<String, JsonNode> latest = validateRegister(register, queue, Files.readString(specificationPath));
        long confirmed = latest.values().stream().filter(decision -> "RULE_CONFIRMED".equals(decision.path("decision").asText())).count();
        System.out.printf("queueSubjects=%d historyEvents=%d effectiveDecisions=%d confirmed=%d reviewRequired=%d status=VALID%n",
            queue.path("subjects").size(), register.path("decisions").size(), latest.size(), confirmed, latest.size() - confirmed);
    }

    public static ObjectNode emptyRegister(ObjectMapper mapper) {
        ObjectNode register = mapper.createObjectNode();
        register.put("artifact", ARTIFACT);
        register.put("specification", "ATL105");
        register.put("specificationVersion", "2026-3");
        register.put("authority", "TEST_SOLUTION_INDEPENDENT_SME_REVIEW");
        register.put("policy", "Append-only field-context decisions. Each dimension requires reviewer rationale and source citation; unresolved decisions never certify a context.");
        register.putArray("subjectTypes").add(SUBJECT_TYPE);
        register.putArray("decisions");
        return register;
    }

    static ObjectNode record(ObjectNode register, JsonNode queue, JsonNode input, String specificationText) {
        validateQueue(queue);
        validateRegisterHeader(register);
        if (!SUBJECT_TYPE.equals(input.path("subjectType").asText()))
            throw new IllegalArgumentException("subjectType must be " + SUBJECT_TYPE);
        String queueSubjectId = required(input, "subjectId");
        JsonNode subject = findSubject(queue, queueSubjectId);
        String segment = required(input, "segment");
        if (!segment.equals(subject.path("segment").asText())) throw new IllegalArgumentException("segment does not match queued field context");
        if (!required(input, "element").equals(subject.path("element").asText())
                || !required(input, "messageFamily").equals(subject.path("messageFamily").asText())) {
            throw new IllegalArgumentException("element or messageFamily does not match queued field context");
        }
        validateContextAnchor(input.path("sourceAnchor"), subject.path("sourceAnchor"));

        String reviewer = required(input, "reviewer");
        if ("UNREVIEWED".equals(reviewer)) throw new IllegalArgumentException("reviewer must identify the SME");
        String rationale = required(input, "rationale");
        JsonNode fieldDecision = input.path("fieldRuleDecision");
        validateFieldDecision(fieldDecision, queue, specificationText, subject);
        String outcome = hasUnresolved(fieldDecision) ? "REVIEW_REQUIRED" : "RULE_CONFIRMED";

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode event = mapper.createObjectNode();
        event.put("decisionId", "FIELD-RULE-" + UUID.randomUUID());
        event.put("subjectType", SUBJECT_TYPE);
        event.put("subjectId", queueSubjectId);
        event.put("segment", segment);
        event.put("decision", outcome);
        event.put("reviewer", reviewer);
        event.put("reviewedAt", Instant.now().toString());
        event.put("rationale", rationale);
        event.put("element", subject.path("element").asText());
        event.put("messageFamily", subject.path("messageFamily").asText());
        event.set("sourceAnchor", input.path("sourceAnchor").deepCopy());
        event.set("fieldRuleDecision", fieldDecision.deepCopy());

        ObjectNode candidate = register.deepCopy();
        SmeDecisionHistory.append(candidate, event);
        validateRegister(candidate, queue, specificationText);
        return SmeDecisionHistory.append(register, event);
    }

    public static Map<String, JsonNode> validateRegister(JsonNode register, JsonNode queue, String specificationText) {
        validateQueue(queue);
        validateRegisterHeader(register);
        Map<String, JsonNode> latest = SmeDecisionHistory.latestBySubject(register);
        for (JsonNode event : register.path("decisions")) {
            if (!SUBJECT_TYPE.equals(event.path("subjectType").asText()))
                throw new IllegalStateException("Invalid field decision subjectType");
            JsonNode subject = findSubject(queue, required(event, "subjectId"));
            if (!event.path("segment").asText().equals(subject.path("segment").asText())
                    || !event.path("element").asText().equals(subject.path("element").asText())
                    || !event.path("messageFamily").asText().equals(subject.path("messageFamily").asText())) {
                throw new IllegalStateException("Decision context no longer matches adjudication queue subject " + event.path("subjectId").asText());
            }
            validateContextAnchor(event.path("sourceAnchor"), subject.path("sourceAnchor"));
            validateFieldDecision(event.path("fieldRuleDecision"), queue, specificationText, subject);
            String expected = hasUnresolved(event.path("fieldRuleDecision")) ? "REVIEW_REQUIRED" : "RULE_CONFIRMED";
            if (!expected.equals(event.path("decision").asText()))
                throw new IllegalStateException("Decision status disagrees with field-rule dimensions for " + event.path("subjectId").asText());
            String reviewer = required(event, "reviewer");
            if ("UNREVIEWED".equals(reviewer)) throw new IllegalStateException("Reviewed field decision cannot use reviewer UNREVIEWED");
            try {
                Instant.parse(required(event, "reviewedAt"));
            } catch (RuntimeException exception) {
                throw new IllegalStateException("Field decision reviewedAt must be an ISO-8601 timestamp", exception);
            }
            required(event, "rationale");
        }
        return latest;
    }

    private static void validateRegisterHeader(JsonNode register) {
        if (!ARTIFACT.equals(register.path("artifact").asText()) || !register.path("decisions").isArray())
            throw new IllegalStateException("Invalid field-rule SME decision register");
        boolean declaresFieldRuleContext = false;
        for (JsonNode type : register.path("subjectTypes"))
            if (SUBJECT_TYPE.equals(type.asText())) declaresFieldRuleContext = true;
        if (!declaresFieldRuleContext) throw new IllegalStateException("Register must declare FIELD_RULE_CONTEXT");
    }

    private static void validateQueue(JsonNode queue) {
        if (!"ATL105-FIELD-RULE-SME-ADJUDICATION-QUEUE".equals(queue.path("artifact").asText())
                || !"ATL105".equals(queue.path("specification").asText())
                || !"2026-3".equals(queue.path("specificationVersion").asText())
            || !"PENDING_REVIEW_NOT_APPROVED".equals(queue.path("status").asText())
                || !queue.path("subjects").isArray()
                || queue.path("subjectCount").asInt(-1) != queue.path("subjects").size()) {
            throw new IllegalStateException("Invalid field-rule adjudication queue header or subject count");
        }
        Set<String> subjectIds = new java.util.HashSet<>();
        Set<String> contexts = new java.util.HashSet<>();
        for (JsonNode subject : queue.path("subjects")) {
            String subjectId = required(subject, "subjectId");
            String element = required(subject, "element");
            String family = required(subject, "messageFamily");
            String segment = required(subject, "segment");
            if (!subjectIds.add(subjectId)) throw new IllegalStateException("Duplicate field-rule queue subjectId: " + subjectId);
            if (!contexts.add(family + "|" + segment + "|" + element))
                throw new IllegalStateException("Duplicate field-rule queue context: " + family + "|" + segment + "|" + element);
            JsonNode anchor = subject.path("sourceAnchor");
            if (!"ATL105".equals(anchor.path("specification").asText())
                    || !"2026-3".equals(anchor.path("version").asText())
                    || !segment.equals(anchor.path("segment").asText())
                    || !element.equals(anchor.path("element").asText())
                    || anchor.path("rule").asText().isBlank()) {
                throw new IllegalStateException("Invalid source anchor for field-rule queue subject " + subjectId);
            }
            if (!"PENDING".equals(subject.path("decisionStatus").asText())
                    || !"UNREVIEWED".equals(subject.path("reviewer").asText())
                    || !"REVIEW_REQUIRED".equals(subject.path("currentGeneratedDecision").path("decisionStatus").asText())) {
                throw new IllegalStateException("Generated field-rule queue subject must remain pending: " + subjectId);
            }
            Set<String> declaredDimensions = new HashSet<>();
            subject.path("dimensionsToAdjudicate").forEach(dimension -> declaredDimensions.add(dimension.asText()));
            for (String dimension : DIMENSIONS)
                if (!declaredDimensions.contains(dimension))
                    throw new IllegalStateException("Queue subject omits decision dimension " + dimension + ": " + subjectId);
        }
    }

    private static JsonNode findSubject(JsonNode queue, String subjectId) {
        JsonNode found = null;
        for (JsonNode subject : queue.path("subjects")) {
            if (!subjectId.equals(subject.path("subjectId").asText())) continue;
            if (found != null) throw new IllegalStateException("Duplicate adjudication queue subjectId: " + subjectId);
            found = subject;
        }
        if (found == null || !SUBJECT_TYPE.equals(found.path("subjectType").asText()))
            throw new IllegalArgumentException("Unknown FIELD_RULE_CONTEXT subject: " + subjectId);
        return found;
    }

    private static void validateContextAnchor(JsonNode supplied, JsonNode queued) {
        for (String field : Set.of("specification", "version", "section", "segment", "element", "rule")) {
            String value = required(supplied, field);
            if (!value.equals(queued.path(field).asText()))
                throw new IllegalArgumentException("sourceAnchor " + field + " does not match queued context");
        }
        if (!"ATL105".equals(supplied.path("specification").asText())
                || !"2026-3".equals(supplied.path("version").asText())) {
            throw new IllegalArgumentException("sourceAnchor must identify ATL105 2026-3");
        }
    }

    private static void validateFieldDecision(JsonNode fieldDecision, JsonNode queue, String specificationText, JsonNode subject) {
        for (String dimension : DIMENSIONS) {
            JsonNode decision = fieldDecision.path(dimension);
            String status = required(decision, "status");
            required(decision, "rationale");
            if (!decision.path("evidence").isArray() || decision.path("evidence").isEmpty())
                throw new IllegalArgumentException(dimension + " requires source evidence[]");
            Set<String> allowed = switch (dimension) {
                case "applicability" -> APPLICABILITY;
                case "conditionalTrigger" -> TRIGGERS;
                case "permittedOmission" -> OMISSION;
                default -> DEPENDENCIES;
            };
            if (!allowed.contains(status)) throw new IllegalArgumentException("Invalid " + dimension + " status: " + status);
            for (JsonNode evidence : decision.path("evidence")) validateCitation(evidence, specificationText);
        }

        String applicability = fieldDecision.path("applicability").path("status").asText();
        String trigger = fieldDecision.path("conditionalTrigger").path("status").asText();
        String omission = fieldDecision.path("permittedOmission").path("status").asText();
        if ("CONDITIONAL".equals(applicability)
                && (!Set.of("SOURCE_STATED", "CONFIGURATION_DEPENDENT", "UNRESOLVED").contains(trigger)
                    || !Set.of("CONDITION_DEPENDENT", "UNRESOLVED").contains(omission))) {
            throw new IllegalArgumentException("CONDITIONAL applicability requires a trigger and condition-dependent omission decision");
        }
        if ("SOURCE_STATED".equals(trigger) && fieldDecision.path("conditionalTrigger").path("condition").asText().isBlank())
            throw new IllegalArgumentException("conditionalTrigger.condition is required");
        if ("CONFIGURATION_DEPENDENT".equals(trigger)) {
            if (fieldDecision.path("conditionalTrigger").path("condition").asText().isBlank())
                throw new IllegalArgumentException("conditionalTrigger.condition is required");
            if (fieldDecision.path("conditionalTrigger").path("configurationKey").asText().isBlank())
                throw new IllegalArgumentException("conditionalTrigger.configurationKey is required");
        }
        if ("CONDITION_DEPENDENT".equals(omission) && fieldDecision.path("permittedOmission").path("condition").asText().isBlank())
            throw new IllegalArgumentException("permittedOmission.condition is required");
        if ("REQUIRED".equals(applicability) && !Set.of("NOT_PERMITTED", "UNRESOLVED").contains(omission))
            throw new IllegalArgumentException("REQUIRED applicability cannot permit omission");
        if ("OPTIONAL".equals(applicability) && !Set.of("PERMITTED", "UNRESOLVED").contains(omission))
            throw new IllegalArgumentException("OPTIONAL applicability must permit omission or remain unresolved");
        JsonNode dependency = fieldDecision.path("crossSegmentDependency");
        String dependencyStatus = dependency.path("status").asText();
        JsonNode related = dependency.path("relatedContextSubjectIds");
        if (!related.isArray()) throw new IllegalArgumentException("crossSegmentDependency requires relatedContextSubjectIds[]");
        if ("NONE".equals(dependencyStatus)) {
            if (!related.isEmpty())
                throw new IllegalArgumentException("NONE dependency decision cannot list related contexts");
        } else if (!"UNRESOLVED".equals(dependencyStatus)) {
            required(dependency, "relation");
            if (related.isEmpty()) throw new IllegalArgumentException("Dependency decision requires relatedContextSubjectIds");
            for (JsonNode relatedId : related) {
                if (relatedId.asText().equals(subject.path("subjectId").asText()))
                    throw new IllegalArgumentException("A dependency cannot point to the same field context");
                JsonNode relatedSubject = findSubject(queue, relatedId.asText());
                if (subject.path("segment").asText().equals(relatedSubject.path("segment").asText()))
                    throw new IllegalArgumentException("Cross-segment dependency must reference a different segment");
            }
        } else {
            for (JsonNode relatedId : related) findSubject(queue, relatedId.asText());
        }
    }

    private static void validateCitation(JsonNode evidence, String specificationText) {
        JsonNode anchor = evidence.path("sourceAnchor");
        if (!"ATL105".equals(anchor.path("specification").asText())
                || !"2026-3".equals(anchor.path("version").asText())
                || anchor.path("section").asText().isBlank()
                || anchor.path("segment").asText().isBlank()
                || anchor.path("element").asText().isBlank()
                || anchor.path("rule").asText().isBlank()) {
            throw new IllegalArgumentException("Each dimension citation requires a complete ATL105 sourceAnchor");
        }
        if (!"SRC-ATL105-PDF-001".equals(required(evidence, "sourceId")))
            throw new IllegalArgumentException("Field-rule evidence must cite ATL105 source SRC-ATL105-PDF-001");
        int page = evidence.path("sourcePage").asInt(0);
        if (page < 1) throw new IllegalArgumentException("Source citation requires sourcePage");
        String quote = required(evidence, "quote");
        if (!pageContainsQuote(specificationText, page, quote))
            throw new IllegalArgumentException("Source quote does not occur on cited ATL105 PDF page " + page);
    }

    private static boolean hasUnresolved(JsonNode fieldDecision) {
        for (String dimension : DIMENSIONS)
            if ("UNRESOLVED".equals(fieldDecision.path(dimension).path("status").asText())) return true;
        return false;
    }

    private static String normalize(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    private static boolean pageContainsQuote(String specificationText, int expectedPage, String quote) {
        Matcher matcher = PAGE_MARKER.matcher(specificationText);
        while (matcher.find()) {
            int page = Integer.parseInt(matcher.group(1));
            int start = matcher.end();
            int end = matcher.find() ? matcher.start() : specificationText.length();
            if (page == expectedPage && normalize(specificationText.substring(start, end)).contains(normalize(quote))) return true;
            if (matcher.hitEnd()) break;
            matcher.region(end, specificationText.length());
        }
        return false;
    }

    private static String required(JsonNode node, String field) {
        String value = node.path(field).asText("");
        if (value.isBlank()) throw new IllegalArgumentException("Missing required field: " + field);
        return value;
    }
}
