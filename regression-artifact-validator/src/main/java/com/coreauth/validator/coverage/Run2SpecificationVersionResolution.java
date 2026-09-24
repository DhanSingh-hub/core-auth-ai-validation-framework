package com.coreauth.validator.coverage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Map;

/** Hash-bound Test Solution decision that corrects Run2 metadata without editing AI artifacts. */
public record Run2SpecificationVersionResolution(
        String resolutionId,
        String decision,
        String sourceId,
        String sourcePdfFile,
        String declaredVersion,
        String effectiveVersion,
        String authoritativeSourcePath,
        String authoritativeSourceSha256,
        String provenanceCaveat,
        Map<String, String> artifactSha256) {

    public Run2SpecificationVersionResolution {
        artifactSha256 = artifactSha256 == null ? Map.of() : Map.copyOf(artifactSha256);
    }

    public String resolve(Path traceabilityFile, Path runRoot, String actualSourceId,
                          String actualPdfFile, String actualDeclaredVersion) throws IOException {
        require("METADATA_LABEL_DEFECT".equals(decision), "resolution decision is not approved");
        require(equal(sourceId, actualSourceId), "sourceId does not match resolution");
        require(equal(sourcePdfFile, actualPdfFile), "source PDF filename does not match resolution");
        require(equal(declaredVersion, actualDeclaredVersion), "declared version does not match resolution");
        require(effectiveVersion != null && !effectiveVersion.isBlank(), "effective version is required");
        require(!artifactSha256.isEmpty(), "at least one artifact hash is required");
        require(authoritativeSourcePath != null && !authoritativeSourcePath.isBlank(),
            "authoritative source path is required");
        Path authoritativeSource = Path.of(authoritativeSourcePath);
        require(Files.isRegularFile(authoritativeSource), "authoritative source is missing");
        require(authoritativeSourceSha256 != null
                && authoritativeSourceSha256.equalsIgnoreCase(sha256(authoritativeSource)),
            "authoritative source hash does not match resolution");

        for (Map.Entry<String, String> binding : artifactSha256.entrySet()) {
            Path artifact = "traceability_matrix_full.json".equals(binding.getKey())
                    ? traceabilityFile : runRoot.resolve(binding.getKey());
            require(Files.isRegularFile(artifact), "bound artifact is missing: " + binding.getKey());
            require(binding.getValue().equalsIgnoreCase(sha256(artifact)),
                    "artifact hash does not match resolution: " + binding.getKey());
        }
        return effectiveVersion;
    }

    private static String sha256(Path file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream input = Files.newInputStream(file)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = input.read(buffer)) >= 0) {
                    digest.update(buffer, 0, read);
                }
            }
            return HexFormat.of().withUpperCase().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private static boolean equal(String expected, String actual) {
        return expected != null && expected.equals(actual);
    }

    private static void require(boolean condition, String message) throws IOException {
        if (!condition) throw new IOException(message);
    }
}