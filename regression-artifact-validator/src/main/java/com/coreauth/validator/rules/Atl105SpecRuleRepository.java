package com.coreauth.validator.rules;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Authoritative rules derived from the BUYPASS ATL105 Message Format Specification
 * (docs/specs/kb), keyed by JSON test-data field name rather than (context, segment, element)
 * - chapter 13 data element definitions apply globally regardless of which segment/context
 * carries them, so context and segment are ignored on lookup.
 *
 * Only fields that could be confidently and unambiguously mapped to a single numbered data
 * element are included (see src/main/resources/atl105/spec-elements.json). Fields backed by
 * Appendix I/J/K sub-tables, TransArmor-specific elements, or elements with no fixed/enum
 * shape are intentionally left out here so lookups fall through to other rule sources.
 */
public final class Atl105SpecRuleRepository implements RuleSource {

    private static final String RESOURCE_PATH = "/atl105/spec-elements.json";

    private final Map<String, FieldRule> rulesByFieldName;

    private Atl105SpecRuleRepository(Map<String, FieldRule> rulesByFieldName) {
        this.rulesByFieldName = rulesByFieldName;
    }

    public static Atl105SpecRuleRepository loadDefault() throws IOException {
        try (InputStream in = Atl105SpecRuleRepository.class.getResourceAsStream(RESOURCE_PATH)) {
            if (in == null) {
                throw new IOException("Spec resource not found on classpath: " + RESOURCE_PATH);
            }
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode entries = (ArrayNode) mapper.readTree(in);
            Map<String, FieldRule> rules = new HashMap<>();
            for (JsonNode entry : entries) {
                String fieldName = entry.get("fieldName").asText();
                rules.put(fieldName, toFieldRule(fieldName, entry));
            }
            return new Atl105SpecRuleRepository(rules);
        }
    }

    private static FieldRule toFieldRule(String fieldName, JsonNode entry) {
        String elementName = entry.path("elementName").asText(fieldName);
        boolean allowEmpty = entry.path("allowEmpty").asBoolean(false);
        FieldRule.Kind kind = FieldRule.Kind.valueOf(entry.get("kind").asText());

        Set<String> enumValues = Collections.emptySet();
        Pattern pattern = null;
        switch (kind) {
            case ENUM:
                Set<String> values = new LinkedHashSet<>();
                for (JsonNode v : (ArrayNode) entry.get("enumValues")) {
                    values.add(v.asText());
                }
                enumValues = values;
                break;
            case PATTERN:
                pattern = Pattern.compile(entry.get("pattern").asText());
                break;
            case ANY:
            default:
                break;
        }
        return new FieldRule("*", "*", elementName, kind, enumValues, pattern, allowEmpty);
    }

    @Override
    public Optional<FieldRule> find(String context, String segment, String element) {
        return Optional.ofNullable(rulesByFieldName.get(element));
    }

    public int ruleCount() {
        return rulesByFieldName.size();
    }
}
