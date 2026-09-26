package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalPackageLoader;
import com.coreauth.validator.canonical.CanonicalTraceabilityValidator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Segment100ResponseArtifactTraceabilityTest {
    @Test
    void responseCodePackageHasCompleteTraceability() throws Exception {
        var result = new CanonicalTraceabilityValidator().validate(new CanonicalPackageLoader().load(
                Path.of("specifications/ATL105/test-output/test-json/segment-100-response-code-package.json")));

        assertThat(result.errors()).isEmpty();
    }
}
