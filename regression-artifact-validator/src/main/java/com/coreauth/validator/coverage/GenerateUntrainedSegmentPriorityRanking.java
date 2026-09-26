package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Ranks the untrained ATL105 segments by AI-only requirement volume, to prioritize the next segment to train. */
public final class GenerateUntrainedSegmentPriorityRanking {

    private GenerateUntrainedSegmentPriorityRanking() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path reviewRoot = Atl105Paths.testOutput().resolve("ai-solution-independent-review");
        JsonNode analysis = mapper.readTree(reviewRoot.resolve("ai-only-requirements-analysis.json").toFile());

        List<JsonNode> untrained = new ArrayList<>();
        analysis.path("unreferencedBySegment").forEach(row -> {
            if (!row.path("trainedSegment").asBoolean() && !"UNMAPPED".equals(row.path("segment").asText())) {
                untrained.add(row);
            }
        });
        untrained.sort(Comparator.comparingLong((JsonNode row) -> row.path("unreferenced").asLong()).reversed());

        StringBuilder text = new StringBuilder("# Untrained Segment Training Priority Ranking\n\n")
                .append("Ranked by AI-only requirement volume (excludes `UNMAPPED`, which is an AI segment-assignment defect, ")
                .append("not a real ATL105 segment). Each segment requires independent Phase 1-4 training from the ATL105 ")
                .append("specification text before any AI comparison is possible.\n\n")
                .append("| Rank | Segment | AI requirements with no Test Solution rule catalog |\n|---:|---|---:|\n");
        int rank = 1;
        for (JsonNode row : untrained) {
            text.append('|').append(rank++).append('|').append(row.path("segment").asText()).append('|')
                    .append(row.path("unreferenced").asLong()).append("|\n");
        }
        text.append("\nTotal untrained segments: **").append(untrained.size())
                .append("**. Total AI requirements blocked pending training: **")
                .append(untrained.stream().mapToLong(row -> row.path("unreferenced").asLong()).sum()).append("**.\n");

        Files.writeString(reviewRoot.resolve("untrained-segment-priority-ranking.md"), text.toString());
        System.out.printf("untrainedSegments=%d topSegment=%s%n",
                untrained.size(), untrained.isEmpty() ? "none" : untrained.get(0).path("segment").asText());
    }
}
