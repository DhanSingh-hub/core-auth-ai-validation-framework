package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class SemanticEvidenceValidator {
    private final Path trustedRoot;
    private final ObjectMapper mapper = new ObjectMapper();

    public SemanticEvidenceValidator(Path trustedRoot) {
        this.trustedRoot = trustedRoot.toAbsolutePath().normalize();
    }

    public ObjectNode assess(JsonNode observation, JsonNode evidence) {
        if (observation == null) observation = mapper.createObjectNode();
        if (evidence == null) evidence = mapper.createObjectNode();
        ObjectNode output = mapper.createObjectNode();
        output.set("brEquivalence", equivalence(observation.path("brClaims"), evidence.path("brEquivalence")));
        output.set("negativeIsolation", isolation(observation.path("controlPayload"), observation.path("mutantPayload"), evidence.path("negativeIsolation")));
        output.set("compatibility", compatibility(observation.path("canonicalPayload"), evidence.path("compatibility")));
        output.set("wireValidation", wire(observation.path("wireHex"), evidence.path("wireValidation")));
        output.set("hostOutcome", outcome(observation.path("request"), observation.path("response"), evidence.path("hostOutcome")));
        output.set("negativeEffectiveness", effectiveness(observation, evidence, output));
        output.put("allTechnicalGatesPassed", output.properties().stream().allMatch(entry -> "PASS".equals(entry.getValue().path("status").asText())));
        output.put("executionCertified", false);
        output.put("semanticApproval", "NOT_GRANTED_BY_TECHNICAL_EVIDENCE_CHECKS");
        output.put("fullMessageWireAssessed", "FULL_MESSAGE".equals(evidence.path("wireValidation").path("scope").asText())
            && !"NOT_ASSESSED".equals(output.path("wireValidation").path("status").asText()));
        return output;
    }

    private ObjectNode equivalence(JsonNode claims, JsonNode contract) {
        if (!completeClaims(claims) || !completeClaims(contract.path("independentClaims"))) {
            return result("NOT_ASSESSED", "Complete typed BR context and atoms are required on both sides");
        }
        if (!sourceBound(contract.path("source"))) return result("NOT_ASSESSED", "Independent source evidence is unavailable or not bound");
        JsonNode baseline = contract.path("independentClaims");
        if (!canonical(claims.path("context")).equals(canonical(baseline.path("context")))) {
            return result("FAIL", "BR applicability, transaction/card context or lifecycle scope differs");
        }
        Set<String> actual = atomKeys(claims.path("atoms"));
        Set<String> expected = atomKeys(baseline.path("atoms"));
        if (!actual.equals(expected)) return result("FAIL", "BR conditions, behavior, exceptions, modality or values differ");
        return result("PASS", "Complete declared structured claims are equivalent; source interpretation is not independently adjudicated here");
    }

    private boolean completeClaims(JsonNode claims) {
        if (!claims.isObject() || !claims.path("claimsComplete").isBoolean() || !claims.path("claimsComplete").asBoolean(false) || !claims.path("context").isObject()) return false;
        for (String key : List.of("specification", "version", "messageFamily", "transactionType", "cardType", "lifecycleRole")) {
            if (!text(claims.path("context"), key) || unresolved(claims.path("context").path(key).asText())) return false;
        }
        if (!"ATL105".equals(claims.path("context").path("specification").asText())
                || !"2026-3".equals(claims.path("context").path("version").asText())) return false;
        JsonNode atoms = claims.path("atoms");
        if (!atoms.isArray() || atoms.isEmpty()) return false;
        for (JsonNode atom : atoms) {
            for (String key : List.of("subject", "operator", "modality", "units")) if (!text(atom, key)) return false;
            if (!Set.of("required", "fixed", "maxLength", "range", "enum", "equals", "prohibited").contains(atom.path("operator").asText())) return false;
            if (!atom.hasNonNull("value") || !atom.path("conditions").isObject() || !atom.path("exceptions").isArray()) return false;
            if (!Set.of("must", "must-not", "should", "may").contains(atom.path("modality").asText())) return false;
            String operator = atom.path("operator").asText();
            JsonNode value = atom.path("value");
            if (Set.of("required", "prohibited").contains(operator) && !value.isBoolean()) return false;
            if ("maxLength".equals(operator) && (!value.isIntegralNumber() || !value.canConvertToInt() || value.asInt() < 0)) return false;
            if ("enum".equals(operator) && (!value.isArray() || value.isEmpty())) return false;
            if ("range".equals(operator) && (!value.path("min").isNumber() || !value.path("max").isNumber()
                    || value.path("min").decimalValue().compareTo(value.path("max").decimalValue()) > 0)) return false;
        }
        return atomKeys(atoms).size() == atoms.size();
    }

    private Set<String> atomKeys(JsonNode atoms) {
        Set<String> values = new HashSet<>();
        atoms.forEach(atom -> values.add(canonical(atom).toString()));
        return values;
    }

    private ObjectNode isolation(JsonNode control, JsonNode mutant, JsonNode contract) {
        if (!control.isObject() || !mutant.isObject() || !text(contract, "targetPointer") || !text(contract, "targetRule")) {
            return result("NOT_ASSESSED", "Actual valid control, mutant, target pointer and rule are required");
        }
        if (!pointer(contract.path("targetPointer").asText())) return result("NOT_ASSESSED", "Valid JSON target pointer required");
        if (!sourceBound(contract.path("source"))) return result("NOT_ASSESSED", "Source-bound negative rule evidence required");
        List<String> differences = new ArrayList<>();
        differences(control, mutant, "", differences);
        if (differences.size() != 1 || !differences.getFirst().equals(contract.path("targetPointer").asText())) {
            return result("FAIL", "Mutation is absent or contains additional changes: " + differences);
        }
        ValidationResult controlResult = rule(contract.path("targetRule").asText(), control.at(contract.path("targetPointer").asText()));
        ValidationResult mutantResult = rule(contract.path("targetRule").asText(), mutant.at(contract.path("targetPointer").asText()));
        if (controlResult == null || mutantResult == null) return result("NOT_ASSESSED", "Target predicate is not implemented");
        if (!controlResult.isValid() || mutantResult.isValid()) return result("FAIL", "Control must satisfy the intended rule and the mutant must violate it");
        JsonNode scope = contract.path("controlScope");
        if (!scope.isArray() || scope.isEmpty()) return result("NOT_ASSESSED", "All applicable control-scope predicates must be declared");
        boolean targetDeclared = false;
        for (JsonNode predicate : scope) {
            if (!text(predicate, "pointer") || !text(predicate, "rule")) return result("NOT_ASSESSED", "Incomplete control-scope predicate");
            String pointer = predicate.path("pointer").asText();
            if (!pointer(pointer)) return result("NOT_ASSESSED", "Malformed control-scope pointer");
            String name = predicate.path("rule").asText();
            targetDeclared |= pointer.equals(contract.path("targetPointer").asText()) && name.equals(contract.path("targetRule").asText());
            ValidationResult valid = rule(name, control.at(pointer));
            ValidationResult changed = rule(name, mutant.at(pointer));
            if (valid == null || changed == null) return result("NOT_ASSESSED", "Control-scope validator is not implemented: " + name);
            if (!valid.isValid()) return result("FAIL", "Control has unrelated or baseline defects: " + pointer);
                if (!(pointer.equals(contract.path("targetPointer").asText()) && name.equals(contract.path("targetRule").asText()))
                    && !changed.isValid()) return result("FAIL", "Mutant has unrelated rule defects: " + pointer + " / " + name);
        }
        if (!targetDeclared) return result("NOT_ASSESSED", "Target rule is missing from declared control scope");
        return result("PASS", "Exactly one intended violation detected within declared predicate scope; not full-message or host effectiveness");
    }

    private ObjectNode effectiveness(JsonNode observation, JsonNode evidence, JsonNode assessed) {
        if (!"PASS".equals(assessed.path("negativeIsolation").path("status").asText())) {
            return result("NOT_ASSESSED", "An isolated intended source violation must first be demonstrated");
        }
        JsonNode proof = evidence.path("negativeIsolation").path("fullControl");
        if (!proof.path("scopeComplete").isBoolean() || !proof.path("scopeComplete").asBoolean(false) || !sourceBound(proof.path("source"))
                || !"FULL_MESSAGE".equals(proof.path("wireValidation").path("scope").asText())
                || !"FULL_MESSAGE".equals(evidence.path("wireValidation").path("scope").asText())) {
            return result("NOT_ASSESSED", "Complete independent control scope, full control/mutant wire and outcome evidence required");
        }
        if (!observation.path("controlPayload").equals(observation.path("controlCanonicalPayload").path("request"))
                || !observation.path("mutantPayload").equals(observation.path("canonicalPayload").path("request"))
                || !observation.path("controlPayload").equals(observation.path("controlRequest"))
                || !observation.path("mutantPayload").equals(observation.path("request"))) {
            return result("FAIL", "Predicate, canonical and oracle observations are not the same physical control/mutant requests");
        }
        ObjectNode control = mapper.createObjectNode();
        control.set("coreFields", controlCore(observation.path("controlCanonicalPayload")));
        control.set("compatibility", compatibility(observation.path("controlCanonicalPayload"), proof.path("compatibility")));
        control.set("wireValidation", wire(observation.path("controlWireHex"), proof.path("wireValidation")));
        control.set("hostOutcome", outcome(observation.path("controlRequest"), observation.path("controlResponse"), proof.path("hostOutcome")));
        for (JsonNode gate : control) {
            if (!"PASS".equals(gate.path("status").asText())) {
                ObjectNode result = result("NOT_ASSESSED".equals(gate.path("status").asText()) ? "NOT_ASSESSED" : "FAIL", "Full positive control is not independently valid");
                result.set("controlAssessment", control);
                return result;
            }
        }
        if (!"REJECT".equals(evidence.path("hostOutcome").path("expectedDisposition").asText())
                || !"ACCEPT".equals(proof.path("hostOutcome").path("expectedDisposition").asText())) {
            return result("NOT_ASSESSED", "Independent oracle must distinguish positive acceptance from intended negative rejection");
        }
        for (String gate : List.of("compatibility", "wireValidation", "hostOutcome")) {
            if (!"PASS".equals(assessed.path(gate).path("status").asText())) {
                return result("NOT_ASSESSED".equals(assessed.path(gate).path("status").asText()) ? "NOT_ASSESSED" : "FAIL", "Negative has unrelated message defects or does not meet its rejection oracle: " + gate);
            }
        }
        ObjectNode result = result("PASS", "Complete declared control evidence passes, exactly one source mutation is isolated, and request-bound rejection oracle matches observed negative response");
        result.set("controlAssessment", control);
        return result;
    }

    private ObjectNode controlCore(JsonNode payload) {
        if (!payload.isObject() || !payload.path("request").path("dataSection3").isArray()
                || !payload.path("request").path("dataSection3").isEmpty()) {
            return result("NOT_ASSESSED", "Full positive core-field validation currently supports only the single-Segment-100 financial profile; companion-segment core semantics require additional validators");
        }
        ObjectNode copy = ((ObjectNode) payload).deepCopy();
        copy.putObject("testControls").put("validateCoreFields", true);
        CanonicalArtifactPackage pack = new CanonicalArtifactPackage();
        CanonicalTestData datum = new CanonicalTestData();
        datum.setId("control-core");
        datum.setPayload(copy);
        pack.setTestData(List.of(datum));
        ValidationResult validation = new Segment100PayloadValidator().validate(pack);
        return result(validation.isValid() ? "PASS" : "FAIL", validation.isValid() ? "Independent core-field validator ran on physical control" : validation.errors().toString());
    }

    private ValidationResult rule(String name, JsonNode value) {
        ValidationResult result = new ValidationResult(name);
        boolean valid;
        switch (name) {
            case "SEG100_TYPE" -> valid = value.isTextual() && "100".equals(value.asText());
            case "SEQUENCE_SIX_DIGITS" -> valid = value.isTextual() && value.asText().matches("[0-9]{6}");
            case "REQUIRED" -> valid = !value.isMissingNode() && !value.isNull() && !(value.isTextual() && value.asText().isEmpty());
            case "BLOCK_MAX_51" -> valid = value.isTextual() && value.asText().chars().allMatch(character -> character <= 127) && value.asText().length() <= 51;
            default -> { return null; }
        }
        if (!valid) result.addError(name, "Intended source predicate violated");
        return result;
    }

    private ObjectNode compatibility(JsonNode payload, JsonNode contract) {
        if (!payload.path("request").path("dataSection2").path("standardSegment").isObject()
                || !payload.path("request").path("dataSection3").isArray()) return result("NOT_ASSESSED", "Supported canonical request representation required");
        if (!sourceBound(contract.path("source")) || !"STANDARD_FINANCIAL_REQUEST".equals(contract.path("messageCategory").asText())) {
            return result("NOT_ASSESSED", "Source-bound standard-financial applicability required");
        }
        if (!contract.path("requiredSegments").isArray() || contract.path("requiredSegments").isEmpty()
                || !strings(contract.path("requiredSegments")) || !has(contract.path("requiredSegments"), "100")) return result("NOT_ASSESSED", "Independent required-companion set must include Segment 100");
        if (!strings(contract.path("allowedSegments")) || !has(contract.path("allowedSegments"), "100")) return result("NOT_ASSESSED", "Independent allowed-companion set required");
        JsonNode request = payload.path("request");
        List<String> actualSegments = new ArrayList<>();
        actualSegments.add(request.path("dataSection2").path("standardSegment").path("segmentType").asText());
        for (JsonNode segment : request.path("dataSection3")) {
            if (!segment.isObject() || !text(segment, "segmentType")) return result("NOT_ASSESSED", "Explicit physical segment identities required");
            actualSegments.add(segment.path("segmentType").asText());
        }
        for (String segment : actualSegments) {
            if (!has(contract.path("allowedSegments"), segment)) return result("FAIL", "Companion segment not allowed by independent profile: " + segment);
        }
        if (!payload.path("request").path("dataSection1").path("numberOfSegments").isTextual()
                || !payload.path("request").path("dataSection1").path("numberOfSegments").asText().matches("[0-9]{1,2}")) {
            return result("NOT_ASSESSED", "Explicit numeric declared segment count required");
        }
        ObjectNode normalized = ((ObjectNode) payload).deepCopy();
        ObjectNode controls = normalized.putObject("testControls");
        controls.put("messageCategory", "STANDARD_FINANCIAL_REQUEST");
        controls.set("requiredSegments", contract.path("requiredSegments"));
        if (contract.path("expectedOrder").isArray()) controls.set("expectedOrder", contract.path("expectedOrder"));
        CanonicalArtifactPackage pack = new CanonicalArtifactPackage();
        CanonicalTestData datum = new CanonicalTestData();
        datum.setId("evidence-case");
        datum.setPayload(normalized);
        pack.setTestData(List.of(datum));
        ValidationResult validation = new Segment100CompatibilityValidator().validate(pack);
        return result(validation.isValid() ? "PASS" : "FAIL", validation.isValid() ? "Source-bound companions, cardinality, count and declared order checked" : validation.errors().toString());
    }

    private ObjectNode wire(JsonNode wireHex, JsonNode contract) {
        if (!wireHex.isTextual() || wireHex.asText().isBlank() || !sourceBound(contract.path("source"))) return result("NOT_ASSESSED", "Actual wire bytes and source-bound framing contract required");
        if ("FULL_MESSAGE".equals(contract.path("scope").asText())) return fullWire(wireHex.asText(), contract);
        if (!"SEGMENT_100".equals(contract.path("scope").asText())) return result("NOT_ASSESSED", "Explicit supported wire scope required");
        if (!"US-ASCII".equals(contract.path("encoding").asText()) || !"1c".equalsIgnoreCase(contract.path("fieldSeparatorHex").asText())) {
            return result("NOT_ASSESSED", "Only explicit ASCII/1C field framing is supported");
        }
        byte[] bytes;
        try { bytes = HexFormat.of().parseHex(wireHex.asText()); }
        catch (IllegalArgumentException exception) { return result("FAIL", "Wire hexadecimal is malformed"); }
        for (byte value : bytes) if (value < 0) return result("NOT_ASSESSED", "Non-ASCII wire encoding is unsupported");
        String wire = new String(bytes, StandardCharsets.US_ASCII);
        String[] fields = wire.split("\u001c", -1);
        if (fields.length < 3 || !"100".equals(fields[0]) || !fields[1].matches("[0-9]{3}")) return result("FAIL", "Wire Segment 100 identity/length framing invalid");
        if (Integer.parseInt(fields[1]) != bytes.length) return result("FAIL", "Declared segment length does not equal actual bytes including separators");
        if (!contract.path("orderedFields").isArray() || contract.path("orderedFields").size() != fields.length - 1 || !fields[fields.length - 1].isEmpty()) {
            return result("NOT_ASSESSED", "Explicit field manifest and terminal field separator required");
        }
        for (int index = 0; index < contract.path("orderedFields").size(); index++) {
            if (!contract.path("orderedFields").get(index).isTextual() || !fields[index].equals(contract.path("orderedFields").get(index).asText())) {
                return result("FAIL", "Wire field sequence/value differs from bound field manifest");
            }
        }
        return result("PASS", "Actual ASCII segment bytes, declared length, separators and field manifest agree; full-message framing is not certified");
    }

    private ObjectNode fullWire(String wireHex, JsonNode contract) {
        if (!"US-ASCII".equals(contract.path("encoding").asText()) || !"1c".equalsIgnoreCase(contract.path("fieldSeparatorHex").asText())
                || !"UINT16_BE_BODY_INCLUDING_TPDU".equals(contract.path("transportLengthConvention").asText())
                || !contract.path("tpduHex").asText().matches("60[0-9a-fA-F]{8}")
                || !strings(contract.path("section1Fields")) || contract.path("section1Fields").size() != 9
                || !contract.path("segments").isArray() || contract.path("segments").isEmpty()) {
            return result("NOT_ASSESSED", "Complete supported transport, TPDU, Section 1 and segment manifest required");
        }
        byte[] bytes;
        byte[] tpdu;
        try { bytes = HexFormat.of().parseHex(wireHex); tpdu = HexFormat.of().parseHex(contract.path("tpduHex").asText()); }
        catch (IllegalArgumentException exception) { return result("FAIL", "Full-message wire/TPDU hexadecimal is malformed"); }
        if (bytes.length < 7 || bytes.length > 65537) return result("FAIL", "Full-message transport size invalid");
        int length = Byte.toUnsignedInt(bytes[0]) * 256 + Byte.toUnsignedInt(bytes[1]);
        if (length != bytes.length - 2) return result("FAIL", "Network-order transport length does not match captured body bytes");
        JsonNode section1 = contract.path("section1Fields");
        if (!"ATL105".equals(section1.get(0).asText()) || !section1.get(section1.size() - 1).asText().matches("[0-9]{1,2}")
                || Integer.parseInt(section1.get(section1.size() - 1).asText()) != contract.path("segments").size()) {
            return result("NOT_ASSESSED", "Independent Section 1 manifest must bind ATL105 and physical segment count");
        }
        StringBuilder body = new StringBuilder();
        for (JsonNode field : section1) {
            if (!printable(field.asText())) return result("NOT_ASSESSED", "Unsupported Section 1 field encoding");
            body.append(field.asText()).append('\u001c');
        }
        for (JsonNode segment : contract.path("segments")) {
            JsonNode fields = segment.path("fields");
            if (!strings(fields) || fields.size() < 3 || !fields.get(0).asText().matches("[0-9]{3}")
                    || !fields.get(1).asText().matches("[0-9]{3,4}")) return result("NOT_ASSESSED", "Explicit segment type, resolved length and ordered values required");
                int digits = Set.of("103", "114", "115", "118", "120", "130", "131").contains(fields.get(0).asText()) ? 4 : 3;
                if (fields.get(1).asText().length() != digits) return result("NOT_ASSESSED", "Independent segment manifest uses unsupported source length width");
            StringBuilder encoded = new StringBuilder();
            for (JsonNode field : fields) {
                if (!printable(field.asText())) return result("NOT_ASSESSED", "Unsupported segment field encoding");
                encoded.append(field.asText()).append('\u001c');
            }
            if (Integer.parseInt(fields.get(1).asText()) != encoded.length()) return result("NOT_ASSESSED", "Independent segment manifest has incorrect byte length");
            body.append(encoded);
        }
        byte[] expected = body.toString().getBytes(StandardCharsets.US_ASCII);
        if (expected.length + tpdu.length != length) return result("FAIL", "Wire body size differs from independent complete manifest");
        for (int index = 0; index < tpdu.length; index++) if (bytes[index + 2] != tpdu[index]) return result("FAIL", "TPDU protocol/address differs from bound profile");
        for (int index = 0; index < expected.length; index++) if (bytes[index + 7] != expected[index]) return result("FAIL", "Section/segment values, order, separators or lengths differ from complete manifest");
        return result("PASS", "All captured message bytes covered: transport length, TPDU, Section 1/count, segment lengths, order and separators match independent manifest");
    }

    private ObjectNode outcome(JsonNode request, JsonNode response, JsonNode contract) {
        if (!request.isObject() || !response.isObject() || response.isEmpty()) return result("NOT_ASSESSED", "Actual request and observed response are required");
        if (!sourceBound(contract.path("source")) || !contract.path("expectedResponse").isObject() || contract.path("expectedResponse").isEmpty()) {
            return result("NOT_ASSESSED", "Authoritative source-bound outcome oracle required");
        }
        if (!Set.of("SOURCE_DEFINED_DETERMINISTIC", "INDEPENDENT_HOST_REFERENCE").contains(contract.path("oracleKind").asText())
                || !text(contract, "environment") || !text(contract, "stateFixtureSha256") || !contract.path("stateFixtureSha256").asText().matches("[0-9a-fA-F]{64}")) {
            return result("NOT_ASSESSED", "Oracle authority, environment and host-state fixture binding required");
        }
        if (!digest(canonical(request).toString().getBytes(StandardCharsets.UTF_8)).equalsIgnoreCase(contract.path("requestSha256").asText())) {
            return result("FAIL", "Outcome oracle is not bound to this exact canonical request");
        }
        if (!digest(canonical(response).toString().getBytes(StandardCharsets.UTF_8)).equalsIgnoreCase(contract.path("responseSha256").asText())) {
            return result("FAIL", "Observed-response evidence hash does not match");
        }
        String family = contract.path("responseFamily").asText();
        if (Atl105ResponseCodeFamilyOracle.expectedCodes(family).isEmpty()) return result("NOT_ASSESSED", "Source-defined response family required");
        ValidationResult validation = new Atl105ResponseCodeFamilyOracle().validate(family, response.path("responseCode").asText(null));
        if (!validation.isValid()) return result("NOT_ASSESSED", "Response-family matrix/source conflict requires adjudication: " + validation.errors());
        return result(canonical(response).equals(canonical(contract.path("expectedResponse"))) ? "PASS" : "FAIL",
                "Observed response compared with request-bound authoritative oracle; host execution or business approval is not inferred");
    }

    private boolean sourceBound(JsonNode source) {
        if (!text(source, "file") || !text(source, "sha256") || !text(source, "quote")) return false;
        if (!source.path("startLine").isIntegralNumber() || !source.path("startLine").canConvertToInt()
            || !source.path("endLine").isIntegralNumber() || !source.path("endLine").canConvertToInt()) return false;
        try {
            Path file = trustedRoot.resolve(source.path("file").asText()).normalize();
            if (!file.startsWith(trustedRoot) || !Files.isRegularFile(file) || !file.toRealPath().startsWith(trustedRoot.toRealPath())) return false;
            byte[] bytes = Files.readAllBytes(file);
            if (!digest(bytes).equalsIgnoreCase(source.path("sha256").asText())) return false;
            List<String> lines = StandardCharsets.UTF_8.newDecoder().decode(java.nio.ByteBuffer.wrap(bytes)).toString().lines().toList();
            int first = source.path("startLine").asInt(0);
            int last = source.path("endLine").asInt(0);
            return first >= 1 && last >= first && last <= lines.size()
                    && String.join("\n", lines.subList(first - 1, last)).contains(source.path("quote").asText());
        } catch (Exception exception) { return false; }
    }

    private JsonNode canonical(JsonNode node) {
        if (node.isObject()) {
            ObjectNode result = mapper.createObjectNode();
            Map<String, JsonNode> sorted = new TreeMap<>();
            node.properties().forEach(entry -> sorted.put(entry.getKey(), entry.getValue()));
            sorted.forEach((key, value) -> result.set(key, canonical(value)));
            return result;
        }
        if (node.isArray()) {
            var result = mapper.createArrayNode();
            node.forEach(value -> result.add(canonical(value)));
            return result;
        }
        return node;
    }

    private void differences(JsonNode first, JsonNode second, String pointer, List<String> changed) {
        if (first.equals(second)) return;
        if (first.isObject() && second.isObject()) {
            Set<String> keys = new HashSet<>();
            first.fieldNames().forEachRemaining(keys::add);
            second.fieldNames().forEachRemaining(keys::add);
            for (String key : keys) differences(first.path(key), second.path(key), pointer + "/" + key.replace("~", "~0").replace("/", "~1"), changed);
        } else changed.add(pointer);
    }

    private static boolean text(JsonNode node, String key) {
        return node.path(key).isTextual() && !node.path(key).asText().isBlank();
    }

    private static boolean unresolved(String value) {
        return Set.of("UNKNOWN", "REVIEW_REQUIRED", "NOT_ASSESSED", "UNAVAILABLE", "?").contains(value.toUpperCase(java.util.Locale.ROOT));
    }

    private static boolean pointer(String value) {
        return value.startsWith("/") && !value.matches(".*~([^01]|$).*");
    }

    private static boolean strings(JsonNode values) {
        if (!values.isArray() || values.isEmpty()) return false;
        for (JsonNode value : values) if (!value.isTextual()) return false;
        return true;
    }

    private static boolean has(JsonNode values, String expected) {
        for (JsonNode value : values) if (value.isTextual() && expected.equals(value.asText())) return true;
        return false;
    }

    private static boolean printable(String value) {
        return value.chars().allMatch(character -> character >= 32 && character <= 126);
    }

    private static String digest(byte[] bytes) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes)); }
        catch (Exception exception) { throw new IllegalStateException(exception); }
    }

    private ObjectNode result(String status, String reason) {
        return mapper.createObjectNode().put("status", status).put("reason", reason);
    }
}