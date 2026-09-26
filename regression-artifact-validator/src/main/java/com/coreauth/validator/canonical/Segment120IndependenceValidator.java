package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Item 3 (Test-Data Independence) validator for Segment 120.
 *
 * <p>Delegates the generic independence checks to {@link TestDataIndependenceValidator} and
 * adds Segment 120 specific checks: a test datum must anchor to a Segment 120 rule (segment
 * value {@code 120}) and its payload must contain a Print Data 2 Segment object (either
 * canonical or AI JSON form) or an {@code aiJsonReference} pointer.
 *
 * <p>Rule anchors correspond to {@code SEG120-R-###} in
 * {@code docs/specs/kb/segment-120/coverage/segment-120-rule-catalog.json}.
 */
public final class Segment120IndependenceValidator {
    private static final String SOURCE = "Segment120Independence";
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
            boolean anchorsSegment120 = false;
            if (data.getSourceAnchors() != null) {
                for (SourceAnchor anchor : data.getSourceAnchors()) {
                    if (anchor != null && isSegment120Anchor(anchor)) {
                        anchorsSegment120 = true;
                        break;
                    }
                }
            }
            if (!anchorsSegment120) {
                result.addError(SOURCE, id + " has no Segment 120 source anchor");
            }
            if (data.getPayload() != null && !data.getPayload().isMissingNode()) {
                boolean hasPrintData2Segment = data.getPayload().findParent("Print Data 2 Segment") != null
                    || data.getPayload().findValue("printData2Segment") != null
                    || data.getPayload().findValue("aiJsonReference") != null;
                if (!hasPrintData2Segment) {
                    result.addError(SOURCE, id + " payload does not reference a Print Data 2 Segment or AI JSON fixture");
                }
            }
        }
        return result;
    }

    private static boolean isSegment120Anchor(SourceAnchor anchor) {
        String segment = anchor.getSegment();
        return segment != null && segment.contains("120");
    }
}
