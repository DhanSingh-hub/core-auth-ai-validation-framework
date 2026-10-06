package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105SourceBodyLocatorTest {
    @Test
    void selectsBodyNotContentsForNumberedSectionsAndElements() throws Exception {
        String spec = Files.readString(Atl105Paths.docs().resolve("specs/extracted_text.txt"));
        Atl105SourceBodyLocator.Location request = Atl105SourceBodyLocator.find(spec, "11.1.1").orElseThrow();
        assertThat(request.line()).isGreaterThan(7000);
        assertThat(request.heading()).isEqualTo("11.1.1 Request");
        Atl105SourceBodyLocator.Location element = Atl105SourceBodyLocator.find(spec, "Chapter13-Element83").orElseThrow();
        assertThat(element.heading()).isEqualTo("Number: 83 Name: Response Code");
        assertThat(element.preview()).contains("Maximum Length: 1 byte");
    }

    @Test
    void refusesAmbiguousAndUnsupportedLocations() {
        assertThat(Atl105SourceBodyLocator.find("11.1.1 Request\n11.1.1 Request again\n", "11.1.1")).isEmpty();
        assertThat(Atl105SourceBodyLocator.find("11.1.1 Request .... 11-3\n11.1.1 Request\n", "11.1.1")).isPresent();
        assertThat(Atl105SourceBodyLocator.find("Appendix AA\n", "Appendix AA")).isEmpty();
    }
}