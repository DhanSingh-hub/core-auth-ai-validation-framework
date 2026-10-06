package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateElement118ContextMatrix;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Element118ContextMatrixTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void preservesThreeDistinctElement118MeaningsAndCaps() throws Exception {
        GenerateElement118ContextMatrix.main(new String[]{PACK.toString()});
        Path output = PACK.resolve("test-output/test-solution-independent-review/element-118-context-matrix.json");
        JsonNode matrix = MAPPER.readTree(output.toFile());
        assertThat(matrix.path("sourceDefinitionCount").asInt()).isEqualTo(2);
        assertThat(matrix.path("contexts")).hasSize(3);
        assertThat(matrix.path("contexts").get(0).path("segment").asText()).isEqualTo("112");
        assertThat(matrix.path("contexts").get(0).path("aggregateByteCap").asInt()).isEqualTo(984);
        assertThat(matrix.path("contexts").get(1).path("aggregateByteCap").asInt()).isEqualTo(2000);
        assertThat(matrix.path("contexts").get(2).path("aggregateByteCap").asInt()).isEqualTo(2800);
        assertThat(matrix.path("contexts").get(0).path("repetition").asText()).isEqualTo("SINGLE_TRIAD");
        assertThat(matrix.path("contexts").get(1).path("repetition").asText()).isEqualTo("REPEATING_GROUP");
        assertThat(matrix.path("contexts").get(2).path("repetition").asText()).isEqualTo("REPEATING_GROUP");
        for (JsonNode context : matrix.path("contexts"))
            assertThat(context.path("status").asText()).isEqualTo("SOURCE_DERIVED_REVIEW_REQUIRED");
    }
}
