package com.coreauth.validator.traceable;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Checks that a {@link SpecReference} actually points at rules that exist in the ATL105
 * knowledge base (docs/specs/kb) - catches hallucinated element numbers or appendix letters
 * before they're trusted as the justification for a test.
 */
public final class SpecReferenceValidator {

    private static final String RESOURCE_PATH = "/atl105/known-spec-references.json";

    private final Set<Integer> knownElementNumbers = new HashSet<>();
    private final Set<String> knownAppendices = new HashSet<>();

    private SpecReferenceValidator() {
    }

    public static SpecReferenceValidator loadDefault() throws IOException {
        SpecReferenceValidator v = new SpecReferenceValidator();
        try (InputStream in = SpecReferenceValidator.class.getResourceAsStream(RESOURCE_PATH)) {
            if (in == null) {
                throw new IOException("Resource not found on classpath: " + RESOURCE_PATH);
            }
            JsonNode root = new ObjectMapper().readTree(in);
            for (JsonNode n : root.get("knownElementNumbers")) {
                v.knownElementNumbers.add(n.asInt());
            }
            for (JsonNode n : root.get("knownAppendices")) {
                v.knownAppendices.add(n.asText());
            }
        }
        return v;
    }

    public void validate(SpecReference specRef, ValidationResult result) {
        if (specRef == null) {
            result.addError("SpecReference", "test case has no specReference - cannot verify traceability to the spec");
            return;
        }
        List<SpecRuleReference> rules = specRef.getRules();
        if (rules == null || rules.isEmpty()) {
            result.addError("SpecReference", "specReference has no rules - cannot verify traceability to the spec");
            return;
        }
        for (SpecRuleReference rule : rules) {
            if (rule.getElementNumber() != null && !knownElementNumbers.contains(rule.getElementNumber())) {
                result.addError("SpecReference", "elementNumber " + rule.getElementNumber()
                        + " is not a known ATL105 data element (per docs/specs/kb/13-data-elements.md)");
            }
            if (rule.getAppendixId() != null && !knownAppendices.contains(rule.getAppendixId())) {
                result.addError("SpecReference", "appendixId '" + rule.getAppendixId()
                        + "' is not a known ATL105 appendix (per docs/specs/kb/00-index.md)");
            }
            if (rule.getElementNumber() == null && rule.getAppendixId() == null) {
                result.addWarning("spec rule reference has neither elementNumber nor appendixId - only a free-text description: "
                        + rule.getDescription());
            }
        }
    }
}
