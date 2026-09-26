package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Item 3 (Test-Data Independence) validator for Segment 113.
 *
 * <p>Delegates the generic independence checks to {@link TestDataIndependenceValidator} and
 * adds Segment 113 specific checks: a test datum must anchor to a Segment 113 rule (segment
 * value {@code 113}) and its payload must contain an ECA/TeleCheck Data Segment object (either
 * canonical or AI JSON form).
 *
 * <p>Rule anchors correspond to {@code SEG113-R-###} in
 * {@code specifications/ATL105/docs/specs/kb/segment-113/coverage/segment-113-rule-catalog.json}.
 */
public final class Segment113IndependenceValidator {
    private static final String SOURCE = "Segment113Independence";
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
            boolean anchorsSegment113 = false;
            if (data.getSourceAnchors() != null) {
                for (SourceAnchor anchor : data.getSourceAnchors()) {
                    if (anchor != null && isSegment113Anchor(anchor)) {
                        anchorsSegment113 = true;
                        break;
                    }
                }
            }
            if (!anchorsSegment113) {
                result.addError(SOURCE, id + " has no Segment 113 source anchor");
            }
            if (data.getPayload() != null && !data.getPayload().isMissingNode()) {
                boolean hasEcaSegment = data.getPayload().findParent("ECA/TeleCheck Data Segment") != null
                    || data.getPayload().findValue("ecaSegment") != null
                    || data.getPayload().findValue("aiJsonReference") != null;
                if (!hasEcaSegment) {
                    result.addError(SOURCE, id + " payload does not reference an ECA/TeleCheck Data Segment or AI JSON fixture");
                }
            }
        }
        return result;
    }

    private static boolean isSegment113Anchor(SourceAnchor anchor) {
        String segment = anchor.getSegment();
        if (segment == null) {
            return false;
        }
        return segment.contains("113");
    }
}
