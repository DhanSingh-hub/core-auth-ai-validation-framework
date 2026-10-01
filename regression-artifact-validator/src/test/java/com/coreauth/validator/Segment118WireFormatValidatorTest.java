package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment118WireFormatValidator;
import com.coreauth.validator.validation.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Segment118WireFormatValidatorTest {
    private static final String FS = "\u001c";
    private static final List<String> CORE_FIELDS = List.of(
        "118", "0100", "000001", "0", "TERM001A12345", "903", "0902",
        "00001000020000300004000050000600007", "00000000000000000000000000000000000",
        "0002", "SYNTHETICKEY", "202609261200", ""
    );
    private final Segment118WireFormatValidator validator = new Segment118WireFormatValidator();

    @Test
    void requestPreservesEmptyCoreFieldAndSeparatorAfterField13() {
        String serialized = String.join(FS, CORE_FIELDS) + FS + "SYNTHETIC-SITE-CONFIG";

        ValidationResult result = validator.validateRequest(CORE_FIELDS, serialized);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void requestRejectsMissingTrailingSeparatorAfterField13() {
        String serialized = String.join(FS, CORE_FIELDS) + "SYNTHETIC-SITE-CONFIG";

        ValidationResult result = validator.validateRequest(CORE_FIELDS, serialized);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG118-R-005"));
    }

    @Test
    void requestRejectsDroppedSeparatorBetweenEmptyCoreFields() {
        String serialized = String.join(FS, CORE_FIELDS) + FS + "SYNTHETIC-SITE-CONFIG";
        serialized = serialized.replace(FS + FS, FS);

        ValidationResult result = validator.validateRequest(CORE_FIELDS, serialized);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG118-R-005"));
    }

    @Test
    void responseSerializesCoreFieldsPositionallyBeforePayload() {
        String serialized = String.join("", CORE_FIELDS) + "SYNTHETIC-CARD-TABLE";

        ValidationResult result = validator.validateResponse(CORE_FIELDS, serialized);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void responseRejectsFieldSeparators() {
        ValidationResult result = validator.validateResponse(CORE_FIELDS, String.join(FS, CORE_FIELDS));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG118-R-005"));
    }

    @Test
    void responseRejectsCoreFieldsOutOfSpecificationOrder() {
        List<String> reordered = new ArrayList<>(CORE_FIELDS);
        reordered.set(1, CORE_FIELDS.get(2));
        reordered.set(2, CORE_FIELDS.get(1));

        ValidationResult result = validator.validateResponse(reordered, String.join("", CORE_FIELDS));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG118-R-005"));
    }

    @Test
    void fieldOrderMustMatchSection1216() {
        List<String> reorderedNames = new ArrayList<>(Segment118WireFormatValidator.coreFieldOrder());
        reorderedNames.set(1, reorderedNames.get(2));
        reorderedNames.set(2, "SegmentLength");

        ValidationResult result = validator.validateFieldOrder(reorderedNames);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG118-R-005"));
    }

    @Test
    void rejectsIncorrectCoreFieldCount() {
        ValidationResult result = validator.validateRequest(CORE_FIELDS.subList(0, 12), "118");

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("exactly 13"));
    }
}