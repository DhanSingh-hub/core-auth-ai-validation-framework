package com.coreauth.validator.canonical;

import java.io.IOException;
import java.util.List;

/** Selects the source adapter and resolves AI artifacts into one common intake contract. */
public final class AiArtifactIntakeService {
    private final List<AiArtifactIntakeAdapter> adapters = List.of(
            new LocalAiArtifactIntakeAdapter(),
            new SharedAiArtifactIntakeAdapter(),
            new GitHubAiArtifactIntakeAdapter());

    public AiArtifactIntakeResult resolve(AiArtifactSource source) throws IOException {
        return adapters.stream()
                .filter(adapter -> adapter.supports(source))
                .findFirst()
                .orElseThrow(() -> new IOException("No AI artifact intake adapter supports " + source.type()))
                .resolve(source);
    }
}
