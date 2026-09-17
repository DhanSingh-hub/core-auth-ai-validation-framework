package com.coreauth.validator.canonical;

import java.nio.file.Path;

/** Describes where an AI artifact package was received from. */
public record AiArtifactSource(Type type, String location, String revision, Path localRoot) {
    public enum Type { LOCAL, SHARED, GITHUB }

    public AiArtifactSource {
        if (type == null) throw new IllegalArgumentException("source type is required");
        if (location == null || location.isBlank()) throw new IllegalArgumentException("source location is required");
        if (type == Type.GITHUB && (revision == null || revision.isBlank())) {
            throw new IllegalArgumentException("GitHub source revision is required");
        }
    }

    public static AiArtifactSource local(Path root) {
        return new AiArtifactSource(Type.LOCAL, root.toString(), "working-tree", root);
    }

    public static AiArtifactSource shared(Path root) {
        return new AiArtifactSource(Type.SHARED, root.toString(), "shared-copy", root);
    }

    public static AiArtifactSource github(String repositoryUrl, String revision, Path checkoutRoot) {
        return new AiArtifactSource(Type.GITHUB, repositoryUrl, revision, checkoutRoot);
    }
}
