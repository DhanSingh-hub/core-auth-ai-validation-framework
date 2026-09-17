package com.coreauth.validator.canonical;

import java.io.IOException;

/** Resolves one AI artifact source into the common intake representation. */
public interface AiArtifactIntakeAdapter {
    boolean supports(AiArtifactSource source);
    AiArtifactIntakeResult resolve(AiArtifactSource source) throws IOException;
}
