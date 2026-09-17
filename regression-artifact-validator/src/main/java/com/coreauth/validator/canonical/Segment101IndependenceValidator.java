package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Item 3 (Test-Data Independence) validator for Segment 101.
 *
 * <p>Delegates the generic independence checks to {@link TestDataIndependenceValidator} and
 * adds Segment 101 specific checks: a test datum must anchor to a Segment 101 rule (segment
 * value {@code 101} or fleet-tag rule) and its payload must contain a Fleet Data Segment
 * object (either canonical or AI JSON form).
 *
 * <p>Rule anchors correspond to {@code SEG101-R-###} in
 * {@code docs/specs/kb/segment-101/coverage/segment-101-rule-catalog.json}.
 */
public final class Segment101IndependenceValidator {
    private static final String SOURCE = "Segment101Independence";
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
            boolean anchorsSegment101 = false;
            if (data.getSourceAnchors() != null) {
                for (SourceAnchor anchor : data.getSourceAnchors()) {
                    if (anchor != null && isSegment101Anchor(anchor)) {
                        anchorsSegment101 = true;
                        break;
                    }
                }
            }
            if (!anchorsSegment101) {
                result.addError(SOURCE, id + " has no Segment 101 source anchor");
            }
            if (data.getPayload() != null && !data.getPayload().isMissingNode()) {
                boolean hasFleetSegment = data.getPayload().findParent("Fleet Data Segment") != null
                    || data.getPayload().findValue("fleetSegment") != null
                    || data.getPayload().findValue("aiJsonReference") != null;
                if (!hasFleetSegment) {
                    result.addError(SOURCE, id + " payload does not reference a Fleet Data Segment or AI JSON fixture");
                }
            }
        }
        return result;
    }

    private static boolean isSegment101Anchor(SourceAnchor anchor) {
        String segment = anchor.getSegment();
        if (segment == null) {
            return false;
        }
        return segment.contains("101") || "FLEET-TAG".equalsIgnoreCase(segment);
    }
}
