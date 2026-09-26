package com.coreauth.validator.canonical;

import java.io.IOException;
import java.nio.file.Files;

/** Resolves AI artifacts from a checked-out GitHub revision. */
public final class GitHubAiArtifactIntakeAdapter implements AiArtifactIntakeAdapter {
    @Override
    public boolean supports(AiArtifactSource source) {
        return source != null && source.type() == AiArtifactSource.Type.GITHUB;
    }

    @Override
    public AiArtifactIntakeResult resolve(AiArtifactSource source) throws IOException {
        if (source.localRoot() == null || !Files.isDirectory(source.localRoot())) {
            throw new IOException("GitHub checkout is required before intake: " + source.location()
                    + " @ " + source.revision());
        }
        return new AiArtifactIntakeResult(source, source.localRoot().toAbsolutePath().normalize());
    }
}
