package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Item 3 (Test-Data Independence) validator for Segment 108.
 *
 * <p>Delegates the generic independence checks to {@link TestDataIndependenceValidator} and
 * adds Segment 108 specific checks: a test datum must anchor to a Segment 108 rule (segment
 * value {@code 108}) and its payload must contain a Loyalty Card Data Segment object (either
 * canonical or AI JSON form).
 *
 * <p>Rule anchors correspond to {@code SEG108-R-###} in
 * {@code specifications/ATL105/docs/specs/kb/segment-108/coverage/segment-108-rule-catalog.json}.
 */
public final class Segment108IndependenceValidator {
    private static final String SOURCE = "Segment108Independence";
    private final TestDataIndependenceValidator generic = new TestDataIndependenceValidator();

    public ValidationResult validate(CanonicalArtifactPackage pkg) {
        ValidationResult result = generic.validate(pkg);
        if (pkg == null || pkg.getTestData() == null) {
            return result;
        }
        for (CanonicalTestData data : pkg.getTestData()) {
            if (data == null) {
                continue;
            }
            String id = data.getId() == null ? "unknown-test-data" : data.getId();
            boolean anchorsSegment108 = false;
            if (data.getSourceAnchors() != null) {
                for (SourceAnchor anchor : data.getSourceAnchors()) {
                    if (anchor != null && isSegment108Anchor(anchor)) {
                        anchorsSegment108 = true;
                        break;
                    }
                }
            }
            if (!anchorsSegment108) {
                result.addError(SOURCE, id + " has no Segment 108 source anchor");
            }
            if (data.getPayload() != null && !data.getPayload().isMissingNode()) {
                boolean hasLoyaltySegment = data.getPayload().findParent("Loyalty Card Data Segment") != null
                    || data.getPayload().findValue("loyaltySegment") != null
                    || data.getPayload().findValue("aiJsonReference") != null;
                if (!hasLoyaltySegment) {
                    result.addError(SOURCE, id + " payload does not reference a Loyalty Card Data Segment or AI JSON fixture");
                }
            }
        }
        return result;
    }

    private static boolean isSegment108Anchor(SourceAnchor anchor) {
        String segment = anchor.getSegment();
        if (segment == null) {
            return false;
        }
        return segment.contains("108");
    }
}
