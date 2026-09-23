package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;

/**
 * Item 3 (Test-Data Independence) validator for Segment 111.
 *
 * <p>Delegates the generic independence checks to {@link TestDataIndependenceValidator} and
 * adds Segment 111 specific checks: a test datum must anchor to a Segment 111 rule (segment
 * value {@code 111}) and its payload must contain a Variable Information Data Segment object
 * (either canonical or AI JSON form).
 *
 * <p>Rule anchors correspond to {@code SEG111-R-###} in
 * {@code docs/specs/kb/segment-111/coverage/segment-111-rule-catalog.json}.
 */
public final class Segment111IndependenceValidator {
    private static final String SOURCE = "Segment111Independence";
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
            boolean anchorsSegment111 = false;
            if (data.getSourceAnchors() != null) {
                for (SourceAnchor anchor : data.getSourceAnchors()) {
                    if (anchor != null && isSegment111Anchor(anchor)) {
                        anchorsSegment111 = true;
                        break;
                    }
                }
            }
            if (!anchorsSegment111) {
                result.addError(SOURCE, id + " has no Segment 111 source anchor");
            }
            if (data.getPayload() != null && !data.getPayload().isMissingNode()) {
                boolean hasVariableInformationSegment = data.getPayload().findParent("Variable Information Data Segment") != null
                    || data.getPayload().findValue("variableInformationSegment") != null
                    || data.getPayload().findValue("aiJsonReference") != null;
                if (!hasVariableInformationSegment) {
                    result.addError(SOURCE, id + " payload does not reference a Variable Information Data Segment or AI JSON fixture");
                }
            }
        }
        return result;
    }

    private static boolean isSegment111Anchor(SourceAnchor anchor) {
        String segment = anchor.getSegment();
        return segment != null && segment.contains("111");
    }
}


