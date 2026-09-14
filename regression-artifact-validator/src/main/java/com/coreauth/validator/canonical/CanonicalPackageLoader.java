package com.coreauth.validator.canonical;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

/** Loads the canonical artifact-package JSON contract. */
public final class CanonicalPackageLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public CanonicalArtifactPackage load(Path file) throws IOException {
        return mapper.readValue(file.toFile(), CanonicalArtifactPackage.class);
    }
}