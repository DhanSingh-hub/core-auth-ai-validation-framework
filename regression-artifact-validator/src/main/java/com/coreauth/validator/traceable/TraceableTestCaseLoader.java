package com.coreauth.validator.traceable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

/** Reads one "Spec > Scenario > Test Case > Test Data" JSON file. */
public final class TraceableTestCaseLoader {

    private final ObjectMapper mapper = new ObjectMapper();

    public TraceableTestCase load(Path file) throws IOException {
        return mapper.readValue(file.toFile(), TraceableTestCase.class);
    }
}
