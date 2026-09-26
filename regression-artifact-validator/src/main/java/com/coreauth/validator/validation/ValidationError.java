package com.coreauth.validator.validation;

/** One rule violation found in a JSON test-data document. */
public final class ValidationError {
    private final String context;
    private final String segment;
    private final String element;
    private final String reason;

    public ValidationError(String context, String segment, String element, String reason) {
        this.context = context;
        this.segment = segment;
        this.element = element;
        this.reason = reason;
    }

    public String context() { return context; }
    public String segment() { return segment; }
    public String element() { return element; }
    public String reason() { return reason; }

    @Override
    public String toString() {
        return "[" + context + " > " + segment + " > " + element + "] " + reason;
    }
}
