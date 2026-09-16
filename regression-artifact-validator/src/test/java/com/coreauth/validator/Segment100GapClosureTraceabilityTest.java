package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import com.coreauth.validator.validation.ValidationResult;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100GapClosureTraceabilityTest {
    @Test
    void gapClosurePackageHasCompleteBrTsTcTdTraceability() throws Exception {
        Path packageFile = Path.of("test-output/test-json/segment-100-gap-closure-package.json");
        ValidationResult result = new CanonicalTraceabilityValidator()
                .validate(new CanonicalPackageLoader().load(packageFile));

        assertThat(result.errors()).isEmpty();
    }
}
