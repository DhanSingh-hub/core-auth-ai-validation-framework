package com.coreauth.validator.source;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Reads every *.json file from a local folder (phase 1 source). */
public final class LocalFileJsonSource implements JsonDataSource {

    private final Path folder;
    private final ObjectMapper mapper = new ObjectMapper();

    public LocalFileJsonSource(Path folder) {
        this.folder = folder;
    }

    @Override
    public List<JsonDocument> loadAll() {
        try (Stream<Path> files = Files.list(folder)) {
            return files
                    .filter(p -> p.toString().toLowerCase().endsWith(".json"))
                    .sorted(Comparator.comparing(Path::getFileName))
                    .map(this::read)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to list JSON files in " + folder, e);
        }
    }

    private JsonDocument read(Path file) {
        try {
            JsonNode node = mapper.readTree(file.toFile());
            return new JsonDocument(file.getFileName().toString(), node);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to parse JSON file " + file, e);
        }
    }
}
