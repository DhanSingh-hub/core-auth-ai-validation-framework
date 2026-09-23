package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Item 3 (Test-Data Independence) validator for Segment 103.
 *
 * <p>Delegates the generic independence checks to {@link TestDataIndependenceValidator} and
 * adds Segment 103 specific checks: a test datum must anchor to a Segment 103 rule (segment
 * value {@code 103}) and its payload must contain an EBT Data Segment object (either
 * canonical or AI JSON form).
 *
 * <p>Rule anchors correspond to {@code SEG103-R-###} in
 * {@code specifications/ATL105/docs/specs/kb/segment-103/coverage/segment-103-rule-catalog.json}.
 */
public final class Segment103IndependenceValidator {
    private static final String SOURCE = "Segment103Independence";
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
            boolean anchorsSegment103 = false;
            if (data.getSourceAnchors() != null) {
                for (SourceAnchor anchor : data.getSourceAnchors()) {
                    if (anchor != null && isSegment103Anchor(anchor)) {
                        anchorsSegment103 = true;
                        break;
                    }
                }
            }
            if (!anchorsSegment103) {
                result.addError(SOURCE, id + " has no Segment 103 source anchor");
            }
            if (data.getPayload() != null && !data.getPayload().isMissingNode()) {
                boolean hasEbtSegment = data.getPayload().findParent("EBT Data Segment") != null
                    || data.getPayload().findValue("ebtSegment") != null
                    || data.getPayload().findValue("aiJsonReference") != null;
                if (!hasEbtSegment) {
                    result.addError(SOURCE, id + " payload does not reference an EBT Data Segment or AI JSON fixture");
                }
            }
        }
        return result;
    }

    private static boolean isSegment103Anchor(SourceAnchor anchor) {
        String segment = anchor.getSegment();
        if (segment == null) {
            return false;
        }
        return segment.contains("103");
    }
}
