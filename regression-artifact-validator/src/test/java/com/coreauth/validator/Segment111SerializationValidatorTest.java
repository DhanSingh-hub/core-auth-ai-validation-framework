package com.coreauth.validator;

import com.coreauth.validator.canonical.CanonicalArtifactPackage;
import com.coreauth.validator.canonical.CanonicalTestData;
import com.coreauth.validator.canonical.Segment111SerializationValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Segment111SerializationValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test void serializationIsGatedByControl() {
        CanonicalTestData d = new CanonicalTestData();
        d.setId("td-111");
        d.setPayload(MAPPER.createObjectNode());
        CanonicalArtifactPackage p = new CanonicalArtifactPackage();
        p.setTestData(List.of(d));
        assertThat(new Segment111SerializationValidator().validate(p).errors()).isEmpty();
    }

    @Test void requiresWireFormatWhenSerializationEnabled() throws Exception {
        CanonicalArtifactPackage p = packageWith("""
            {"testControls":{"validateSerialization":true},"request":{"dataSection3":{"variableInfoSegment":{"segmentType":"111","segmentLength":"009","variableInformationSections":[]}}}}
            """);
        assertThat(new Segment111SerializationValidator().validate(p).errors()).anyMatch(e -> e.reason().contains("wireFormat is required"));
    }

    @Test void acceptsWireFormatWithExactlyThreeSeparators() throws Exception {
        String sep = "\\u001c";
        CanonicalArtifactPackage p = packageWith("""
            {"testControls":{"validateSerialization":true},"request":{"dataSection3":{"variableInfoSegment":{"segmentType":"111","segmentLength":"009","variableInformationSections":[],"wireFormat":"111%s009%s%s"}}}}
            """.formatted(sep, sep, sep));
        assertThat(new Segment111SerializationValidator().validate(p).errors()).isEmpty();
    }

    @Test void rejectsWireFormatWithoutExactlyThreeSeparators() throws Exception {
        String sep = "\\u001c";
        CanonicalArtifactPackage p = packageWith("""
            {"testControls":{"validateSerialization":true},"request":{"dataSection3":{"variableInfoSegment":{"segmentType":"111","segmentLength":"009","variableInformationSections":[],"wireFormat":"111%s009%s"}}}}
            """.formatted(sep, sep));
        assertThat(new Segment111SerializationValidator().validate(p).errors()).anyMatch(e -> e.reason().contains("exactly three field separators"));
    }

    private static CanonicalArtifactPackage packageWith(String payload) throws Exception {
        CanonicalTestData d = new CanonicalTestData();
        d.setId("td-111");
        d.setPayload(MAPPER.readTree(payload));
        CanonicalArtifactPackage p = new CanonicalArtifactPackage();
        p.setTestData(List.of(d));
        return p;
    }
}



