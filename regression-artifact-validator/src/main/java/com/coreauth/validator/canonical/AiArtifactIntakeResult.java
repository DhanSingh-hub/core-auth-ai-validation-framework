package com.coreauth.validator.canonical;

import java.nio.file.Path;

/** Resolved AI artifact package plus immutable source provenance. */
public record AiArtifactIntakeResult(AiArtifactSource source, Path packageRoot) {
    public AiArtifactIntakeResult {
        if (source == null) throw new IllegalArgumentException("source is required");
        if (packageRoot == null) throw new IllegalArgumentException("package root is required");
    }

    public String provenance() {
        return source.type() + "|" + source.location() + "|" + source.revision();
    }
}
