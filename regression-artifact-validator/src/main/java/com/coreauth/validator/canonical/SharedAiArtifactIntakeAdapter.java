package com.coreauth.validator.canonical;

import java.io.IOException;

/** Resolves AI artifacts from a mounted/shared folder without changing their contents. */
public final class SharedAiArtifactIntakeAdapter implements AiArtifactIntakeAdapter {
    @Override
    public boolean supports(AiArtifactSource source) {
        return source != null && source.type() == AiArtifactSource.Type.SHARED;
    }

    @Override
    public AiArtifactIntakeResult resolve(AiArtifactSource source) throws IOException {
        LocalAiArtifactIntakeAdapter.requireDirectory(source);
        return new AiArtifactIntakeResult(source, source.localRoot().toAbsolutePath().normalize());
    }
}
