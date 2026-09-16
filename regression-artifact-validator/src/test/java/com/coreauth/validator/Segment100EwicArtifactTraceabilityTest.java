package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100EwicArtifactTraceabilityTest {
    @Test
    void ewicGapPackageHasCompleteTraceability() throws Exception {
        var packageFile = Path.of("test-output/test-json/segment-100-ewic-gap-package.json");
        var result = new CanonicalTraceabilityValidator().validate(new CanonicalPackageLoader().load(packageFile));

        assertThat(result.errors()).isEmpty();
    }
}
