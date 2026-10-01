package com.coreauth.validator;

import com.coreauth.validator.coverage.GenerateAtl105ElementOmissionMatrix;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class Atl105ElementOmissionMatrixTest {
    private static final Path PACK = Path.of("specifications", "ATL105");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void classifiesOmissionsWithoutCallingThemProhibited() throws Exception {
        GenerateAtl105ElementOmissionMatrix.main(new String[]{PACK.toString()});
        Path output = PACK.resolve("test-output/test-solution-independent-review/atl105-element-omission-matrix.json");
        JsonNode matrix = MAPPER.readTree(output.toFile());
        assertThat(matrix.path("rowCount").asInt()).isEqualTo(12893);
        assertThat(matrix.path("counts").path("CALLED_IN_ACTIVE_TEMPLATE").asInt()).isEqualTo(285);
        assertThat(matrix.path("counts").path("NOT_LISTED_REVIEW_REQUIRED").asInt()).isEqualTo(347);
        assertThat(matrix.path("counts").path("NOT_CALLED_NOT_ESTABLISHED").asInt()).isEqualTo(12261);
        assertThat(matrix.path("policy").asText()).contains("not prohibited");
        for (JsonNode row : matrix.path("rows"))
            assertThat(row.path("decision").asText()).contains("REVIEW_REQUIRED");
    }
}
