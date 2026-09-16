package com.coreauth.validator;

import com.coreauth.validator.canonical.AiArtifactIntakeService;
import com.coreauth.validator.canonical.AiArtifactSource;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AiArtifactIntakeServiceTest {
    @Test
    void resolvesLocalSourceWithProvenance() throws Exception {
        var root = Files.createTempDirectory("ai-local-");
        var result = new AiArtifactIntakeService().resolve(AiArtifactSource.local(root));

        assertThat(result.packageRoot()).isEqualTo(root.toAbsolutePath().normalize());
        assertThat(result.provenance()).startsWith("LOCAL|");
    }

    @Test
    void resolvesSharedSourceWithSameCanonicalResult() throws Exception {
        var root = Files.createTempDirectory("ai-shared-");
        var result = new AiArtifactIntakeService().resolve(AiArtifactSource.shared(root));

        assertThat(result.packageRoot()).isEqualTo(root.toAbsolutePath().normalize());
        assertThat(result.provenance()).startsWith("SHARED|");
    }

    @Test
    void resolvesGitHubSourceFromCheckedOutRevision() throws Exception {
        var checkout = Files.createTempDirectory("ai-github-checkout-");
        var source = AiArtifactSource.github(
                "https://github.com/example/ai-artifacts.git", "refs/heads/main", checkout);

        var result = new AiArtifactIntakeService().resolve(source);

        assertThat(result.packageRoot()).isEqualTo(checkout.toAbsolutePath().normalize());
        assertThat(result.provenance()).contains("GITHUB|https://github.com/example/ai-artifacts.git|refs/heads/main");
    }

    @Test
    void rejectsGitHubSourceWithoutCheckout() {
        var source = AiArtifactSource.github(
                "https://github.com/example/private-ai-artifacts.git", "refs/tags/v1", null);

        assertThatThrownBy(() -> new AiArtifactIntakeService().resolve(source))
                .isInstanceOf(java.io.IOException.class)
                .hasMessageContaining("GitHub checkout is required");
    }
}
