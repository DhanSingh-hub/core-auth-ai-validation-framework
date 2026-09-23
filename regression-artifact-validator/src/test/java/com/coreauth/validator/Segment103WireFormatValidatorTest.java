package com.coreauth.validator;

import com.coreauth.validator.canonical.Segment103WireFormatValidator;
import com.coreauth.validator.validation.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Segment103WireFormatValidatorTest {
    private static final List<String> FIELDS = List.of("103", "0180", "0000012345", "", "", "", "");
    private static final String FS = "\u001c";

    @Test
    void preservesEmptyRequestFieldsWithSeparators() {
        String serialized = String.join(FS, FIELDS);

        ValidationResult result = new Segment103WireFormatValidator()
            .validateRequest(FIELDS, serialized);

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsRequestThatDropsAnEmptyFieldSeparator() {
        String serialized = String.join(FS, FIELDS).replace(FS + FS, FS);

        ValidationResult result = new Segment103WireFormatValidator()
            .validateRequest(FIELDS, serialized);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-007"));
    }

    @Test
    void rejectsSeparatorsInResponse() {
        ValidationResult result = new Segment103WireFormatValidator()
            .validateResponse(FIELDS, String.join(FS, FIELDS));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-008"));
    }

    @Test
    void acceptsResponseWithoutSeparatorsInFieldOrder() {
        ValidationResult result = new Segment103WireFormatValidator()
            .validateResponse(FIELDS, String.join("", FIELDS));

        assertThat(result.errors()).isEmpty();
    }

    @Test
    void rejectsWrongFieldOrder() {
        ValidationResult result = new Segment103WireFormatValidator()
            .validateFieldOrder(List.of("SegmentType", "ClerkId", "SegmentLength", "VoucherId",
                "WicDiscountAmount", "WicProductData", "EbtProgramData"));

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-006"));
    }

    @Test
    void acceptsRecognizedEwicPromptCodeFromDevice() {
        ValidationResult result = new Segment103WireFormatValidator()
            .validateContext("device", "0086", false);

        assertThat(result.errors()).isEmpty();
        assertThat(result.warnings()).isEmpty();
    }

    @Test
    void rejectsUnknownEwicPromptCodeAndReturnTransaction() {
        ValidationResult result = new Segment103WireFormatValidator()
            .validateContext("host", "9999", true);

        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-019"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-021"));
        assertThat(result.errors()).anyMatch(error -> error.reason().contains("SEG103-R-020"));
    }
}