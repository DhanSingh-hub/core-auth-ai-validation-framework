package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** Seeds per-code, shared transaction-type training tasks while preserving existing team progress. */
public final class GenerateTransactionTypeTrainingLedger {
    private static final List<String[]> GATES = List.of(
            new String[]{"SOURCE_REVIEW", "Verify Appendix G meaning and source anchor"},
            new String[]{"BUSINESS_RULES", "Derive independent Test Solution BRs and conditions"},
            new String[]{"SCENARIOS", "Derive positive, negative, boundary, and conditional scenarios as applicable"},
            new String[]{"LIFECYCLE", "Derive lifecycle/correlation scenarios or record NOT_APPLICABLE with evidence"},
            new String[]{"TEST_CASES", "Derive linked test cases with explicit expected outcomes"},
            new String[]{"TEST_DATA_JSON", "Create synthetic request Test Data JSON for the cases"},
            new String[]{"VALIDATION", "Run schema, rule, and BR->TS->TC->TD traceability validation"},
            new String[]{"HANDOFF_REVIEW", "Record unresolved items, reviewer, and handoff"});

    private GenerateTransactionTypeTrainingLedger() { }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Path baselineFile = Atl105Paths.testJson("segment-100-all-23-transaction-type-training-baseline.json");
        Path root = Atl105Paths.docs().resolve(Path.of("specs", "kb", "segment-100", "transaction-type-training"));
        Path codesRoot = root.resolve("codes");
        Files.createDirectories(codesRoot);
        JsonNode baseline = mapper.readTree(baselineFile.toFile());
        ArrayNode transactionTypes = (ArrayNode) baseline.path("transactionTypes");
        List<TaskSummary> summaries = new ArrayList<>();

        for (JsonNode transaction : transactionTypes) {
            String code = transaction.path("code").asText();
            Path taskFile = codesRoot.resolve("transaction-" + safe(code) + ".json");
            ObjectNode task = Files.isRegularFile(taskFile)
                    ? (ObjectNode) mapper.readTree(taskFile.toFile()) : newTask(transaction, mapper);
            refreshBaselineFields(task, transaction);
            mapper.writerWithDefaultPrettyPrinter().writeValue(taskFile.toFile(), task);
            summaries.add(new TaskSummary(code, transaction.path("name").asText(),
                    task.path("status").asText(), task.path("owner").path("user").asText(""),
                    countStatus(task.path("gates"), "COMPLETE"), GATES.size()));
        }

        ObjectNode manifest = mapper.createObjectNode();
        manifest.put("artifact", "atl105-transaction-type-training-ledger");
        manifest.put("specification", "ATL105");
        manifest.put("specificationVersion", "2026-3");
        manifest.put("baseline", "test-output/test-json/segment-100-all-23-transaction-type-training-baseline.json");
        manifest.put("taskCount", summaries.size());
        manifest.put("generatedAt", Instant.now().toString());
        manifest.put("policy", "Baseline descriptions are not completed training. Every completion needs source-backed BRs, linked TS/TC/Test Data JSON, validation evidence, and reviewer/handoff record.");
        ArrayNode tasks = manifest.putArray("tasks");
        for (TaskSummary summary : summaries) {
            ObjectNode row = tasks.addObject();
            row.put("code", summary.code());
            row.put("name", summary.name());
            row.put("status", summary.status());
            row.put("owner", summary.owner());
            row.put("completedGates", summary.completedGates());
            row.put("gateCount", summary.gateCount());
            row.put("taskFile", "codes/transaction-" + safe(summary.code()) + ".json");
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(root.resolve("training-ledger-index.json").toFile(), manifest);
        Files.writeString(root.resolve("README.md"), readme());
        Files.writeString(root.resolve("training-ledger.md"), markdown(summaries));
        System.out.printf("transactionTypes=%d unclaimed=%d inProgress=%d readyForReview=%d complete=%d%n",
                summaries.size(), count(summaries, "UNCLAIMED"), count(summaries, "IN_PROGRESS"),
                count(summaries, "READY_FOR_REVIEW"), count(summaries, "COMPLETE"));
    }

    private static ObjectNode newTask(JsonNode transaction, ObjectMapper mapper) {
        ObjectNode task = mapper.createObjectNode();
        task.put("artifact", "atl105-transaction-type-training-task");
        task.put("schemaVersion", "1.0");
        task.put("status", "UNCLAIMED");
        task.set("transactionType", transaction.deepCopy());
        task.putNull("owner");
        ArrayNode gates = task.putArray("gates");
        for (String[] definition : GATES) {
            ObjectNode gate = gates.addObject();
            gate.put("id", definition[0]);
            gate.put("description", definition[1]);
            gate.put("status", "NOT_STARTED");
            gate.putArray("evidence");
            gate.putArray("blockers");
        }
        task.putArray("openBlockers");
        task.putArray("evidence");
        task.putArray("workLog");
        task.putObject("handoff").put("nextAction", "Claim this code and begin SOURCE_REVIEW.");
        task.put("createdAt", Instant.now().toString());
        task.put("updatedAt", Instant.now().toString());
        return task;
    }

    private static void refreshBaselineFields(ObjectNode task, JsonNode transaction) {
        ObjectNode saved = task.with("transactionType");
        boolean changed = false;
        var fields = transaction.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            JsonNode current = saved.get(field.getKey());
            if (current == null || !current.equals(field.getValue())) {
                saved.set(field.getKey(), field.getValue().deepCopy());
                changed = true;
            }
        }
        if (changed) task.put("updatedAt", Instant.now().toString());
        if (!task.path("gates").isArray() || task.path("gates").size() != GATES.size()) {
            throw new IllegalStateException("Task gate schema mismatch for code " + transaction.path("code").asText());
        }
    }

    private static int countStatus(JsonNode gates, String status) {
        int count = 0;
        for (JsonNode gate : gates) if (status.equals(gate.path("status").asText())) count++;
        return count;
    }

    private static int count(List<TaskSummary> tasks, String status) {
        return (int) tasks.stream().filter(task -> status.equals(task.status())).count();
    }

    private static String safe(String code) {
        return code.replaceAll("[^A-Za-z0-9]+", "-");
    }

    private static String readme() {
        return "# Transaction-Type Training Handoff\n\n"
                + "This shared ledger coordinates Test Solution training for all 23 Appendix G transaction codes. The baseline is not completion evidence.\n\n"
                + "## Claim and Work\n\n"
                + "1. Pull the latest `Develop` before choosing work.\n"
                + "2. Choose one task file under `codes/`. Claim it by setting `status` to `IN_PROGRESS` and filling `owner.user`, `owner.machine`, `owner.branch`, and `owner.claimedAt`. Commit/push the claim before substantial work so teammates can see ownership.\n"
                + "3. Update only that code's task file. Record each activity in `workLog` with timestamp, user, machine, branch, action, outcome, evidence paths, blockers, and next action.\n"
                + "4. Also add one immutable event file under `events/` for each claim, release, handoff, significant training update, or validation run. Use a unique filename containing timestamp, user, machine, code, and action; do not edit or replace another person's event file.\n"
                + "5. Keep gate evidence linked to repository files. Mark a gate `COMPLETE` only when its evidence exists and passes; use `NOT_APPLICABLE` only with a source-backed rationale.\n"
                + "6. Before handing off, update `handoff.nextAction`, `handoff.notes`, and owner. Set `UNCLAIMED` when releasing the task.\n"
                + "7. Commit and push the code task file and event. Run `GenerateTransactionTypeTrainingLedger` to refresh the index and overview.\n\n"
                + "For codes `9`, `D`, `E`, `K`, `L`, `M`, `N`, `Q`, `T`, and `V`, follow [the special-flow training technique](special-flow-training-technique.md) and review the [draft BR baseline](../../../../../test-output/test-json/special-transaction-type-br-baseline.md). These are separate from the standard-financial Segment 100 code-flow package.\n\n"
                + "## Completion Gate\n\n"
                + "Do not mark a code `COMPLETE` until source review, independent BRs, applicable positive/negative/boundary/scenario cases, lifecycle or justified N/A, physical synthetic Test Data JSON, schema/rule/chain validation, and reviewer/handoff evidence are recorded. Never copy AI output into Test Solution truth.\n\n"
                + "Each transaction code has a separate file to reduce parallel-edit conflicts. If two users claim the same code, the first pushed claim owns it; coordinate before continuing.\n";
    }

    private static String markdown(List<TaskSummary> tasks) {
        StringBuilder out = new StringBuilder("# Transaction-Type Training Progress\n\n")
                .append("| Code | Transaction | Status | Owner | Gates | Task file |\n|---|---|---|---|---:|---|\n");
        for (TaskSummary task : tasks) {
            out.append('|').append(task.code()).append('|').append(task.name()).append('|')
                    .append(task.status()).append('|').append(task.owner().isBlank() ? "—" : task.owner()).append('|')
                    .append(task.completedGates()).append('/').append(task.gateCount()).append('|')
                    .append("[open](codes/transaction-").append(safe(task.code())).append(".json)|\n");
        }
        out.append("\nProgress is shared through Git. Per-code files record user, machine, branch, evidence, blockers, and handoff history.\n");
        return out.toString();
    }

    private record TaskSummary(String code, String name, String status, String owner, int completedGates, int gateCount) { }
}