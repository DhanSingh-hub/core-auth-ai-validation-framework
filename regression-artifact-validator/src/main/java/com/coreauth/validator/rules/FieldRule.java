package com.coreauth.validator.rules;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * A single business rule for one (context, segment, element) field, derived from
 * the ATL105 reference data (observed value population) or, later, a formal spec.
 */
public final class FieldRule {

    public enum Kind {
        /** Small, closed set of legal values (e.g. SegmentType, InformationByte). */
        ENUM,
        /** Open set of values that must conform to a shape (e.g. fixed-length numeric amount). */
        PATTERN,
        /** No reliable constraint could be derived; presence/format is not checked. */
        ANY
    }

    private final String context;
    private final String segment;
    private final String element;
    private final Kind kind;
    private final Set<String> allowedValues; // used when kind == ENUM
    private final Pattern pattern;           // used when kind == PATTERN
    private final boolean allowEmpty;

    public FieldRule(String context, String segment, String element, Kind kind,
                      Set<String> allowedValues, Pattern pattern, boolean allowEmpty) {
        this.context = context;
        this.segment = segment;
        this.element = element;
        this.kind = kind;
        this.allowedValues = allowedValues;
        this.pattern = pattern;
        this.allowEmpty = allowEmpty;
    }

    public String context() { return context; }
    public String segment() { return segment; }
    public String element() { return element; }
    public Kind kind() { return kind; }
    public boolean allowEmpty() { return allowEmpty; }

    /** Returns null if the value is acceptable, otherwise a human-readable reason. */
    public String check(String value) {
        String v = value == null ? "" : value;
        if (v.isEmpty()) {
            return allowEmpty ? null : "empty value is not allowed for this field";
        }
        switch (kind) {
            case ENUM:
                return allowedValues.contains(v)
                        ? null
                        : "value '" + v + "' is not in the known set of values " + allowedValues;
            case PATTERN:
                return pattern.matcher(v).matches()
                        ? null
                        : "value '" + v + "' does not match expected format " + pattern.pattern();
            case ANY:
            default:
                return null;
        }
    }

    public String key() {
        return context + "|" + segment + "|" + element;
    }
}
