package com.coreauth.validator.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Aggregated outcome of validating one JSON test-data document. */
public final class ValidationResult {
    private final String documentId;
    private final List<ValidationError> errors = new ArrayList<>();
    private final List<String> warnings = new ArrayList<>();

    public ValidationResult(String documentId) {
        this.documentId = documentId;
    }

    public void addError(ValidationError error) { errors.add(error); }
    public void addError(String stage, String reason) {
        errors.add(new ValidationError(stage, "-", "-", reason));
    }
    public void addWarning(String warning) { warnings.add(warning); }
    public void merge(ValidationResult other) {
        errors.addAll(other.errors);
        warnings.addAll(other.warnings);
    }

    public String documentId() { return documentId; }
    public boolean isValid() { return errors.isEmpty(); }
    public List<ValidationError> errors() { return Collections.unmodifiableList(errors); }
    public List<String> warnings() { return Collections.unmodifiableList(warnings); }

    @Override
    public String toString() {
        return documentId + ": " + (isValid() ? "VALID" : errors.size() + " error(s)") +
                (warnings.isEmpty() ? "" : ", " + warnings.size() + " warning(s)");
    }
}
