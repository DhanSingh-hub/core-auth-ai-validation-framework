package com.coreauth.validator.canonical;

import java.io.IOException;
import java.nio.file.Files;

/** Resolves AI artifacts from the local workspace. */
public final class LocalAiArtifactIntakeAdapter implements AiArtifactIntakeAdapter {
    @Override
    public boolean supports(AiArtifactSource source) {
        return source != null && source.type() == AiArtifactSource.Type.LOCAL;
    }

    @Override
    public AiArtifactIntakeResult resolve(AiArtifactSource source) throws IOException {
        requireDirectory(source);
        return new AiArtifactIntakeResult(source, source.localRoot().toAbsolutePath().normalize());
    }

    static void requireDirectory(AiArtifactSource source) throws IOException {
        if (source.localRoot() == null || !Files.isDirectory(source.localRoot())) {
            throw new IOException("AI artifact directory does not exist: " + source.localRoot());
        }
    }
}
