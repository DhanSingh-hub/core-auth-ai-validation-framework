package com.coreauth.validator.schemas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/** Guards that the AI-team hand-off schema and its example stay in sync. */
class Atl105Segment100SchemaExampleTest {

    @Test
    void exampleConformsToAiHandoffSchema() throws Exception {
        File schemaFile = new File(System.getProperty("user.dir"), "schemas/atl105-segment100-artifact-package.schema.json");
        File exampleFile = new File(System.getProperty("user.dir"), "schemas/atl105-segment100-artifact-package.example.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode schemaNode = mapper.readTree(schemaFile);
        JsonNode exampleNode = mapper.readTree(exampleFile);

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        JsonSchema schema = factory.getSchema(schemaNode);
        Set<com.networknt.schema.ValidationMessage> errors = schema.validate(exampleNode);

        assertThat(errors).isEmpty();
    }
}
