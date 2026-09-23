package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Item 3 (Test-Data Independence) validator for Segment 104.
 *
 * <p>Delegates the generic independence checks to {@link TestDataIndependenceValidator} and
 * adds Segment 104 specific checks: a test datum must anchor to a Segment 104 rule (segment
 * value {@code 104}) and its payload must contain a Purchase Card Data Segment object
 * (either canonical or AI JSON form).
 *
 * <p>Rule anchors correspond to {@code SEG104-R-###} in
 * {@code specifications/ATL105/docs/specs/kb/segment-104/coverage/segment-104-rule-catalog.json}.
 */
public final class Segment104IndependenceValidator {
    private static final String SOURCE = "Segment104Independence";
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
            boolean anchorsSegment104 = false;
            if (data.getSourceAnchors() != null) {
                for (SourceAnchor anchor : data.getSourceAnchors()) {
                    if (anchor != null && isSegment104Anchor(anchor)) {
                        anchorsSegment104 = true;
                        break;
                    }
                }
            }
            if (!anchorsSegment104) {
                result.addError(SOURCE, id + " has no Segment 104 source anchor");
            }
            if (data.getPayload() != null && !data.getPayload().isMissingNode()) {
                boolean hasPurchaseCardSegment = data.getPayload().findParent("Purchase Card Data Segment") != null
                    || data.getPayload().findValue("purchaseCardSegment") != null
                    || data.getPayload().findValue("aiJsonReference") != null;
                if (!hasPurchaseCardSegment) {
                    result.addError(SOURCE, id + " payload does not reference a Purchase Card Data Segment or AI JSON fixture");
                }
            }
        }
        return result;
    }

    private static boolean isSegment104Anchor(SourceAnchor anchor) {
        String segment = anchor.getSegment();
        return segment != null && segment.contains("104");
    }
}
