package com.coreauth.validator.coverage;

import com.coreauth.validator.paths.Atl105Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Persists authored atomic source clauses with verified executed identities, never abstract category closure. */
final class Chapter12AtomicClauseReconciliation {
    private Chapter12AtomicClauseReconciliation() { }

    static ObjectNode build(JsonNode executions, JsonNode sourceLedger) throws IOException {
        var mapper = new ObjectMapper();
        Path definitions = Atl105Paths.docs().resolve(Path.of("specs", "kb", "chapter-12-atomic-clause-definitions.json"));
        JsonNode authored = mapper.readTree(definitions.toFile());
        List<String> source = Files.readAllLines(Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt")));
        var report = mapper.createObjectNode();
        report.put("artifact", "ATL105-CHAPTER12-ATOMIC-SOURCE-CLAUSE-PREFIX");
        report.put("globalAtomicDenominatorEstablished", false).put("wholeChapterComplete", false);
        report.put("policy", "Authored paraphrases are atomic bounded clauses, not catalogue rules/category slots. "
                + "BOUNDED_EXECUTION requires real executed chains but does not certify every boundary or authenticate captures. "
                + "Unenumerated sections remain source-local work; OPEN_IMPLEMENTATION is not an SME-only designation.");
        report.set("enumeratedSections", authored.path("enumeratedSections").deepCopy());
        report.set("enumerationScope", authored.path("enumerationScope").deepCopy());
        report.put("authoredPrefixSectionPolicy", "enumeratedSections/unenumeratedSections describe the earlier authored execution-linked prefix only. "
                + "allSectionSourceInventory now accounts source text in all49 sections; it separately preserves every unresolved atomic split.");
        report.put("semanticAssessmentPrecedence", "allSectionSourceInventory.semanticAdjudication contains the newer reviewed "
                + "semantic scope. Earlier authored OPEN assessments are not independently verified missing validators; "
                + "reviewed code may already implement a predicate without a reconciled target chain.");
        var unenumerated = report.putArray("unenumeratedSections");
        for (int section = 1; section <= 49; section++) {
            String name = "12." + section;
            boolean included = false;
            for (JsonNode item : authored.path("enumeratedSections")) if (name.equals(item.asText())) included = true;
            if (!included) unenumerated.add(name);
        }
        var clauses = report.putArray("clauses");
        Set<String> identities = new HashSet<>();
        int bounded = 0, open = 0, conflict = 0, external = 0, links = 0;
        for (JsonNode definition : authored.path("clauses")) {
            String id = definition.path("id").asText(), status = definition.path("status").asText();
            if (id.isBlank() || !identities.add(id) || definition.path("requirement").asText().isBlank())
                throw new IOException("Atomic clause identity/requirement missing or repeated: " + id);
            int start = definition.path("startLine").asInt(), end = definition.path("endLine").asInt();
            if (start < 1 || end < start || end > source.size()) throw new IOException("Atomic source range invalid: " + id);
            ObjectNode clause = definition.deepCopy();
            if ("BOUNDED_IMPLEMENTATION".equals(status)) clause.put("status", "BOUNDED_EXECUTION");
            var anchor = clause.putObject("sourceEvidence").put("file", "docs/specs/extracted_text.txt").put("startLine", start).put("endLine", end);
            var lines = anchor.putArray("lines");
            for (int line = start; line <= end; line++) lines.add(source.get(line - 1));
            if (definition.has("implementation")) {
                Path implementation = Path.of(definition.path("implementation").asText());
                if (!Files.isRegularFile(implementation)) throw new IOException("Atomic implementation file missing: " + id);
            }
            var chains = clause.putArray("executedChains");
            for (JsonNode fixture : definition.path("fixtureIds")) {
                boolean found = false;
                for (JsonNode execution : executions) if (execution.path("testCaseId").asText()
                        .equals("TC-TEST-CH12-" + execution.path("observedSegment").asText() + "-" + fixture.asText() + "-FIELDS")) {
                    found = true;
                    if (!execution.path("expectedStatus").equals(execution.path("actualStatus"))
                            || execution.has("expectedTargetStatus") && !execution.path("expectedTargetStatus").equals(execution.path("actualTargetStatus")))
                        throw new IOException("Atomic link points at unexpected execution: " + id);
                    var chain = chains.addObject();
                    for (String field : List.of("businessRequirementId", "scenarioId", "testCaseId", "testDataId", "ruleId", "scope",
                            "validationPath", "expectedStatus", "actualStatus", "targetRuleId", "expectedTargetStatus", "actualTargetStatus"))
                        if (execution.has(field)) chain.set(field, execution.path(field).deepCopy());
                    links++;
                }
                if (!found) throw new IOException("Atomic fixture has no executed canonical chain: " + id + "/" + fixture.asText());
            }
            switch (status) {
                case "BOUNDED_IMPLEMENTATION" -> {
                    bounded++;
                    if (!definition.has("implementation") || chains.isEmpty()) throw new IOException("Atomic bounded implementation needs code and execution: " + id);
                }
                case "OPEN_IMPLEMENTATION" -> open++;
                case "SOURCE_CONFLICT" -> conflict++;
                case "EXTERNAL_EVIDENCE" -> external++;
                default -> throw new IOException("Atomic status unsupported: " + id + "/" + status);
            }
            clauses.add(clause);
        }
        report.putObject("summary").put("enumeratedClauses", clauses.size()).put("boundedImplementation", bounded)
                .put("openImplementation", open).put("sourceConflict", conflict).put("externalEvidence", external).put("executedChainLinks", links);
        var inventory = report.putObject("remainingSourceClauseInventory");
        inventory.put("exhaustiveAtomicDenominatorVerified", false);
        inventory.put("policy", "Source-specific sentence/field records extracted from all49 sections, not abstract categories. "
                + "These are reconciliation candidates, not certified atomic clauses: table records may need splitting and examples may not be normative. "
                + "OPEN_IMPLEMENTATION means source/code/execution reconciliation remains, not necessarily missing code. "
                + "Overlapping authored clause IDs are navigation only, never executable coverage by range overlap.");
        var records = inventory.putArray("sections");
        int candidates = 0;
        for (JsonNode section : sourceLedger.path("sections")) {
            var record = records.addObject().put("section", section.path("section").asText());
            var candidateList = record.putArray("clauseCandidates");
            for (JsonNode unit : section.path("sourceTextUnits")) {
                int first = unit.path("sourceEvidence").path("startLine").asInt();
                StringBuilder text = new StringBuilder();
                int start = first;
                boolean table = false;
                for (int offset = 0; offset < unit.path("lines").size(); offset++) {
                    String line = unit.path("lines").get(offset).asText().trim();
                    boolean row = line.matches("[0-9]+\\s+[0-9]+\\s+.*");
                    if ((row || line.startsWith("•")) && !text.isEmpty()) {
                        candidates += candidate(candidateList, section.path("section").asText(), start, first + offset - 1,
                                text.toString(), table, clauses, source);
                        text.setLength(0);
                        start = first + offset;
                        table = false;
                    }
                    if (text.isEmpty()) start = first + offset;
                    table |= row;
                    if (!text.isEmpty()) text.append(' ');
                    text.append(line);
                    if (!table && line.endsWith(".")) {
                        candidates += candidate(candidateList, section.path("section").asText(), start, first + offset,
                                text.toString(), false, clauses, source);
                        text.setLength(0);
                    }
                }
                if (!text.isEmpty()) candidates += candidate(candidateList, section.path("section").asText(), start,
                        unit.path("sourceEvidence").path("endLine").asInt(), text.toString(), table, clauses, source);
            }
            record.put("candidateRecords", candidateList.size());
            record.put("atomicSplitAndExhaustivenessVerified", false);
        }
        inventory.put("sectionsScanned", records.size()).put("candidateRecords", candidates);
        report.set("allSectionSourceInventory", sourceInventory(sourceLedger, clauses, source, executions));
        return report;
    }

    private record SourceText(String text, int[] lines) {
        static SourceText of(JsonNode unit) {
            StringBuilder text = new StringBuilder();
            var lineNumbers = new java.util.ArrayList<Integer>();
            int first = unit.path("sourceEvidence").path("startLine").asInt();
            for (int offset = 0; offset < unit.path("lines").size(); offset++) {
                String line = unit.path("lines").get(offset).asText().trim();
                if (!text.isEmpty()) { text.append('\n'); lineNumbers.add(first + offset); }
                for (int character = 0; character < line.length(); character++) {
                    text.append(line.charAt(character));
                    lineNumbers.add(first + offset);
                }
            }
            return new SourceText(text.toString(), lineNumbers.stream().mapToInt(Integer::intValue).toArray());
        }
    }

    private record FieldHeader(String number, String element, String name, String length, String classification,
                               int nameStart, int lengthEnd, int classificationEnd, int end) { }

    private static ObjectNode sourceInventory(JsonNode ledger, JsonNode authored, List<String> source,
                                               JsonNode executions) throws IOException {
        var result = com.fasterxml.jackson.databind.node.JsonNodeFactory.instance.objectNode();
        result.put("policy", "All49 sections are traversed without a normative-keyword omission filter. "
                + "Primary field rows are split into independently sourced declared length and entry classification, "
                + "then fixed values, origin declarations and source sentences. Exact quote/line anchors are verified. "
                + "Table continuations, compound sentences, enumeration lists and external references are preserved "
                + "as precise OPEN_ATOMIC_SPLIT spans, never silently counted as atomic closure. "
                + "OPEN_IMPLEMENTATION means no independently reconciled executable link for this specific clause, "
                + "not necessarily absent validator code. Authored chains are retained separately; overlap navigation "
                + "does not prove execution. Structural field splitting is not a global normative denominator.");
        result.put("globalAtomicDenominatorEstablished", false).put("wholeChapterComplete", false);
        var boundaries = result.putArray("sourceConflictAndExternalBoundaries");
        for (JsonNode boundary : ledger.path("sourceOwnerAndExternalBoundaries")) {
            if (!boundary.isObject()) throw new IOException("Source boundary must be an object");
            ObjectNode classified = boundary.deepCopy();
            String classification = boundary.path("classification").asText();
            classified.put("status", classification.startsWith("EXTERNAL") ? "EXTERNAL_EVIDENCE"
                    : classification.equals("SOURCE_CONFLICT") ? "SOURCE_CONFLICT" : "OPEN_IMPLEMENTATION");
            classified.put("policy", "An exact authored source boundary, not coverage of every nearby clause. "
                    + "SOURCE_UNSPECIFIED classifications are not automatically external or implemented.");
            boundaries.add(classified);
        }
        var sections = result.putArray("sections");
        var rowStart = java.util.regex.Pattern.compile("(?m)^\\d+\\s+(?:\\d+\\s+(?![ROC]\\b|\\d+[ROC]?\\b)|(?=Market\\s+Basket\\s+Data))");
        var rowHeader = java.util.regex.Pattern.compile(
                "^(\\d+)\\s+(\\d+)\\s+(.+?)\\s+(Var\\.|\\d+\\s+or\\s+\\d+|(?:\\.\\.)?[\\d,]+(?:[–-][\\d,]+)?)\\s+([ROC])\\s+",
                java.util.regex.Pattern.DOTALL);
        var unnamedElementHeader = java.util.regex.Pattern.compile(
                "^(\\d+)\\s+(Market\\s+Basket\\s+Data)\\s+(Var\\.\\s+Max\\s+230\\s+0)\\s+([ROC])\\s+",
                java.util.regex.Pattern.DOTALL);
        int fieldRows = 0, direct = 0, statements = 0, unresolved = 0, accountedLines = 0, supportingCount = 0;
        int rowsWithoutElement = 0;
        int verifiedSplits = 0, openRecords = 0, externalRecords = 0;
        var groupedOpen = result.putArray("openImplementationBySection");
        for (JsonNode section : ledger.path("sections")) {
            String name = section.path("section").asText();
            var record = sections.addObject().put("section", name).put("status", "PARTIAL_ATOMIC_RECONCILIATION");
            record.set("sourceEvidence", section.path("sourceEvidence").deepCopy());
            var clauses = record.putArray("sourceClauses");
            var pending = record.putArray("unresolvedSourceSpans");
            var supporting = record.putArray("supportingLayoutSpans");
            var linked = record.putArray("authoredExecutedAndBoundaryClauses");
            for (JsonNode clause : authored) if (name.equals(clause.path("section").asText())) linked.add(clause.deepCopy());
            var lines = new java.util.TreeSet<Integer>();
            for (JsonNode unit : section.path("sourceTextUnits")) {
                SourceText text = SourceText.of(unit);
                var matcher = rowStart.matcher(text.text());
                var offsets = new java.util.ArrayList<Integer>();
                while (matcher.find()) offsets.add(matcher.start());
                if (offsets.isEmpty()) {
                    inventoryStatements(text, 0, text.text().length(), name, "", clauses, pending, authored, source);
                } else {
                    if (offsets.get(0) > 0)
                        inventoryStatements(text, 0, offsets.get(0), name, "", clauses, pending, authored, source);
                    for (int row = 0; row < offsets.size(); row++) {
                        int start = offsets.get(row), end = row + 1 < offsets.size() ? offsets.get(row + 1) : text.text().length();
                        String rowText = text.text().substring(start, end);
                        var header = rowHeader.matcher(rowText);
                        var noElement = unnamedElementHeader.matcher(rowText);
                        FieldHeader parsed;
                        if (header.find()) parsed = new FieldHeader(header.group(1), header.group(2), normalize(header.group(3)),
                                normalize(header.group(4)), header.group(5), header.start(3), header.end(4), header.end(5), header.end());
                        else if (List.of("12.36", "12.37").contains(name) && noElement.find()) {
                            parsed = new FieldHeader(noElement.group(1), "", normalize(noElement.group(2)), normalize(noElement.group(3)),
                                    noElement.group(4), noElement.start(2), noElement.end(3), noElement.end(4), noElement.end());
                            rowsWithoutElement++;
                        } else {
                            inventorySpan(pending, name, text, start, end, "OPEN_ATOMIC_SPLIT",
                                    "Unparsed primary field row; exact remaining text needs field/length/classification split", "", authored, source);
                            continue;
                        }
                        String field = "Field" + parsed.number() + (parsed.element().isEmpty() ? "/ElementNumberNotDeclared" : "/Element" + parsed.element())
                                + " (" + parsed.name() + ")";
                        inventorySpan(clauses, name, text, start + parsed.nameStart(), start + parsed.lengthEnd(),
                                "DECLARED_FIELD_LENGTH", field + " has source-listed Len. " + parsed.length() + ".",
                                field, authored, source);
                        inventorySpan(clauses, name, text, start + parsed.nameStart(), start + parsed.classificationEnd(),
                                "FIELD_ENTRY_CLASSIFICATION", field + " has entry classification " + parsed.classification()
                                        + " (" + switch (parsed.classification()) { case "R" -> "required"; case "O" -> "optional"; default -> "conditional"; }
                                        + "); conditional trigger is not invented from the letter alone.",
                                field, authored, source);
                        inventoryStatements(text, start + parsed.end(), end, name, field, clauses, pending, authored, source);
                        fieldRows++;
                    }
                }
                for (int line : text.lines()) lines.add(line);
            }
            for (int index = pending.size() - 1; index >= 0; index--) {
                if ("SUPPORTING_LAYOUT_LABEL".equals(pending.get(index).path("sourceClauseKind").asText())) {
                    JsonNode removed = pending.remove(index);
                    if (!removed.isObject()) throw new IOException("Supporting source span must be an object");
                    ObjectNode label = removed.deepCopy();
                    label.put("status", "SUPPORTING_SOURCE_NOT_A_REQUIREMENT");
                    supporting.add(label);
                }
            }
            var coverage = record.putObject("sourceLineAccounting").put("retainedNonBoilerplateLines", lines.size());
            var unaccounted = coverage.putArray("unaccountedRetainedLines");
            for (int line : lines) {
                boolean found = false;
                for (JsonNode span : clauses) found |= covers(span, line);
                for (JsonNode span : pending) found |= covers(span, line);
                for (JsonNode span : supporting) found |= covers(span, line);
                if (!found) unaccounted.add(line);
            }
            if (!unaccounted.isEmpty()) throw new IOException("Source inventory omitted retained lines in " + name + ": " + unaccounted);
            accountedLines += lines.size();
            int attributes = 0;
            int sectionVerified = 0, sectionOpen = 0, sectionExternal = 0;
            for (JsonNode clause : clauses) if (List.of("DECLARED_FIELD_LENGTH", "FIELD_ENTRY_CLASSIFICATION",
                    "FIXED_VALUE", "DECLARED_ORIGIN", "NESTED_TABLE_ATTRIBUTE").contains(clause.path("sourceClauseKind").asText())) attributes++;
            var openGroup = groupedOpen.addObject().put("section", name)
                    .put("policy", "These specific source records require clause-to-code-to-case reconciliation, "
                            + "not a claim that all their validators are absent. Unresolved source splits are separate.");
            var openIds = openGroup.putArray("sourceClauseIds");
            for (JsonNode clause : clauses) {
                if (clause.path("atomicSplitVerified").asBoolean()) sectionVerified++;
                if ("OPEN_IMPLEMENTATION".equals(clause.path("status").asText())) { sectionOpen++; openIds.add(clause.path("id").asText()); }
                if ("EXTERNAL_EVIDENCE".equals(clause.path("status").asText())) sectionExternal++;
            }
            openGroup.put("sourceRecords", sectionOpen).put("unresolvedAtomicSourceSpans", pending.size());
            verifiedSplits += sectionVerified;
            openRecords += sectionOpen;
            externalRecords += sectionExternal;
            direct += attributes;
            statements += clauses.size() - attributes;
            unresolved += pending.size();
            supportingCount += supporting.size();
            record.putObject("summary").put("explicitAttributeRecords", attributes)
                    .put("sourceStatementRecords", clauses.size() - attributes)
                    .put("unresolvedSourceSpans", pending.size()).put("supportingLayoutSpans", supporting.size())
                    .put("authoredClauseRecords", linked.size()).put("verifiedStructuralOrReviewedSplits", sectionVerified)
                    .put("sourceRecordsNeedingAtomicAdjudication", clauses.size() - sectionVerified);
            record.put("atomicExhaustivenessVerified", false);
        }
        result.putObject("summary").put("sectionsEnumeratedForSourceInventory", sections.size())
                .put("primaryFieldRowsSplit", fieldRows).put("explicitAttributeRecords", direct)
                .put("fieldRowsWithoutDeclaredElementNumber", rowsWithoutElement)
                .put("sourceStatementRecords", statements).put("unresolvedSourceSpans", unresolved)
                .put("supportingLayoutSpans", supportingCount)
                .put("accountedRetainedSourceLines", accountedLines).put("unaccountedRetainedSourceLines", 0)
                .put("authoredClauseRecords", authored.size()).put("verifiedStructuralOrReviewedSplits", verifiedSplits)
                .put("sourceRecordsNeedingAtomicAdjudication", direct + statements - verifiedSplits)
                .put("openReconciliationSourceRecords", openRecords).put("externalReferenceSourceRecords", externalRecords)
                .put("certifiedWholeSourceAtomicDenominator", false);
        result.set("semanticAdjudication", semanticAdjudication(result, authored, source, executions));
        return result;
    }

    private static ObjectNode semanticAdjudication(JsonNode inventory, JsonNode authored, List<String> source,
                                                   JsonNode executions) throws IOException {
        var mapper = new ObjectMapper();
        Path path = Atl105Paths.docs().resolve(Path.of("specs", "kb", "chapter-12-semantic-adjudications.json"));
        JsonNode decisions = mapper.readTree(path.toFile());
        return semanticAdjudication(inventory, authored, source, executions, decisions);
    }

    static ObjectNode semanticAdjudication(JsonNode inventory, JsonNode authored, List<String> source,
                                           JsonNode executions, JsonNode decisions) throws IOException {
        var mapper = new ObjectMapper();
        String hash;
        try {
            hash = java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(
                    Files.readAllBytes(Atl105Paths.docs().resolve(Path.of("specs", "extracted_text.txt")))));
        } catch (java.security.NoSuchAlgorithmException exception) {
            throw new IOException("SHA-256 unavailable for reviewed source verification", exception);
        }
        if (!hash.equalsIgnoreCase(decisions.path("sourceSha256").asText()))
            throw new IOException("Semantic adjudication source changed; re-review exact source before regeneration");
        var report = mapper.createObjectNode();
        report.put("policy", "Human-reviewed exact source decisions, not keyword-derived verdicts. Supporting text remains "
                + "source-accounted but is excluded from the reviewed obligation count. RECONCILIATION_REQUIRED means "
                + "validator/case equivalence has not been established, NOT missing implementation. Only explicit "
                + "authored clause equivalence can copy executed chains; nearby source overlap cannot. "
                + "Only deduplicatedReconciledScope sections have final normalized obligations and code/case dispositions. "
                + "Other reviewed clause counts remain candidates, not a chapter denominator.");
        report.put("globalAtomicDenominatorEstablished", false).put("wholeChapterComplete", false);
        report.put("sourceSha256", hash);
        report.put("executedLinksAreReusedNotNewObservations", true);
        var reviewed = report.putArray("reviewedDecisions");
        var clauses = report.putArray("reviewedClauses");
        var consumed = new HashSet<String>();
        var clauseIds = new HashSet<String>();
        var decisionIds = new HashSet<String>();
        var finalizedSections = new HashSet<String>();
        for (JsonNode section : decisions.path("finalReconciliationSections")) {
            if (!section.isTextual() || !finalizedSections.add(section.asText()))
                throw new IOException("Repeated/invalid final reconciliation section");
        }
        int residualReviewed = 0, statementsReviewed = 0, structuralReviewed = 0, linked = 0;
        for (JsonNode decision : decisions.path("decisions")) {
            String id = decision.path("id").asText(), sectionName = decision.path("section").asText();
            String collection = decision.path("collection").asText();
            if (!decisionIds.add(id) || id.isBlank() || decision.path("rationale").asText().isBlank()
                    || !List.of("sourceClauses", "unresolvedSourceSpans").contains(collection)
                    || !List.of("EXECUTABLE_OBLIGATIONS", "SOURCE_CONFLICT", "SUPPORTING_DESCRIPTION",
                            "SUPPORTING_PROVENANCE", "SUPPORTING_LAYOUT", "SUPPORTING_ENUMERATION",
                            "IN_CHAPTER_REFERENCE", "ILLUSTRATIVE_EXAMPLE").contains(decision.path("classification").asText()))
                throw new IOException("Invalid semantic decision: " + id);
            JsonNode section = null;
            for (JsonNode item : inventory.path("sections"))
                if (sectionName.equals(item.path("section").asText())) section = item;
            if (section == null) throw new IOException("Semantic decision section missing: " + id);
            var applied = reviewed.addObject().put("id", id).put("section", sectionName)
                    .put("classification", decision.path("classification").asText())
                    .put("rationale", decision.path("rationale").asText());
            var inputs = applied.putArray("sourceRecords");
            var selected = new java.util.ArrayList<JsonNode>();
            for (JsonNode record : section.path(collection)) {
                int first = record.path("sourceEvidence").path("startLine").asInt();
                int last = record.path("sourceEvidence").path("endLine").asInt();
                boolean kindMatch = !decision.has("recordKinds")
                        && ("unresolvedSourceSpans".equals(collection)
                        || "SOURCE_STATEMENT".equals(record.path("sourceClauseKind").asText()));
                for (JsonNode kind : decision.path("recordKinds"))
                    if (kind.asText().equals(record.path("sourceClauseKind").asText())) kindMatch = true;
                boolean identityMatch = !decision.has("recordIds");
                for (JsonNode identity : decision.path("recordIds"))
                    if (identity.equals(record.path("id"))) identityMatch = true;
                if (first >= decision.path("recordStartLine").asInt() && last <= decision.path("recordEndLine").asInt()
                        && kindMatch && identityMatch) {
                    if (!consumed.add(record.path("id").asText()))
                        throw new IOException("Source record adjudicated twice: " + record.path("id"));
                    selected.add(record);
                    inputs.add(record.deepCopy());
                    if ("unresolvedSourceSpans".equals(collection)) residualReviewed++;
                    else if (record.path("atomicSplitVerified").asBoolean()) structuralReviewed++;
                    else statementsReviewed++;
                }
            }
            if (selected.size() != decision.path("expectedRecords").asInt() || selected.isEmpty())
                throw new IOException("Semantic decision selector changed: " + id + " found " + selected.size());
            var definitions = mapper.createArrayNode();
            if (decision.path("retainOriginalClauses").asBoolean()) {
                for (JsonNode record : selected) {
                    var definition = definitions.addObject().put("id", id + "-" + (definitions.size()))
                            .put("requirement", record.path("requirement").asText());
                    definition.put("startLine", record.path("sourceEvidence").path("startLine").asInt());
                    definition.put("endLine", record.path("sourceEvidence").path("endLine").asInt());
                }
            }
            for (JsonNode definition : decision.path("clauses")) definitions.add(definition);
            if (decision.path("splitCharacterTypeAndWidth").asBoolean()) {
                var split = mapper.createArrayNode();
                for (JsonNode definition : definitions) {
                    var dimensions = java.util.regex.Pattern.compile(
                            "^(.+) data is (alphanumeric|numeric), maximum(\\d+) characters\\.$")
                            .matcher(definition.path("requirement").asText());
                    if (!dimensions.matches()) throw new IOException("Reviewed character/width split changed: " + id);
                    ObjectNode character = definition.deepCopy(), width = definition.deepCopy();
                    character.put("id", definition.path("id").asText() + "-CHARACTERS")
                            .put("requirement", dimensions.group(1) + " data is " + dimensions.group(2) + ".");
                    width.put("id", definition.path("id").asText() + "-WIDTH")
                            .put("requirement", dimensions.group(1) + " data has maximum" + dimensions.group(3) + " characters.");
                    split.add(character).add(width);
                }
                definitions = split;
            }
            boolean obligations = List.of("EXECUTABLE_OBLIGATIONS", "SOURCE_CONFLICT")
                    .contains(decision.path("classification").asText());
            if (obligations == definitions.isEmpty())
                throw new IOException("Semantic supporting/obligation classification inconsistent: " + id);
            for (JsonNode definition : definitions) {
                String clauseId = definition.path("id").asText();
                int first = definition.path("startLine").asInt(), last = definition.path("endLine").asInt();
                if (!clauseIds.add(clauseId) || clauseId.isBlank() || definition.path("requirement").asText().isBlank()
                        || first < section.path("sourceEvidence").path("startLine").asInt()
                        || last > section.path("sourceEvidence").path("endLine").asInt() || last < first)
                    throw new IOException("Invalid reviewed clause identity/range: " + clauseId);
                ObjectNode clause = definition.deepCopy();
                clause.put("section", sectionName).put("decisionId", id).put("atomicSplitVerified", true);
                clause.put("implementationStatus", "RECONCILIATION_REQUIRED");
                if (definition.has("codeReviewId")) {
                    JsonNode review = decisions.path("codeReviews").path(definition.path("codeReviewId").asText());
                    if (!review.isObject() || review.path("analysis").asText().isBlank() || review.path("evidence").isEmpty())
                        throw new IOException("Missing concrete implementation review: " + clauseId);
                    for (JsonNode code : review.path("evidence")) {
                        Path path = Path.of(code.path("file").asText());
                        if (!Files.isRegularFile(path) || code.path("member").asText().isBlank())
                            throw new IOException("Implementation review file/member missing: " + clauseId);
                        List<String> content = Files.readAllLines(path);
                        int begin = code.path("startLine").asInt(), end = code.path("endLine").asInt();
                        if (begin < 1 || end < begin || end > content.size()
                                || code.path("contains").asText().isBlank()
                                || !String.join("\n", content.subList(begin - 1, end)).contains(code.path("contains").asText()))
                            throw new IOException("Implementation review anchor drift: " + clauseId);
                    }
                    clause.set("implementationReview", review.deepCopy());
                }
                if (definition.has("implementationStatus")) {
                    String status = definition.path("implementationStatus").asText();
                    if (List.of("OPEN_IMPLEMENTATION", "OPEN_EXECUTION_EVIDENCE").contains(status)) {
                        if (!definition.has("codeReviewId") || definition.path("gap").asText().isBlank())
                            throw new IOException("Concrete open obligation needs code review and gap: " + clauseId);
                    } else if (!List.of("SOURCE_CONFLICT", "EXTERNAL_EVIDENCE").contains(status)
                            || definition.path("boundary").asText().isBlank())
                        throw new IOException("Explicit semantic status needs evidence/reason: " + clauseId);
                    clause.put("implementationStatus", status);
                }
                var evidence = clause.putObject("sourceEvidence").put("file", "docs/specs/extracted_text.txt")
                        .put("startLine", first).put("endLine", last);
                var lines = evidence.putArray("lines");
                for (int line = first; line <= last; line++) lines.add(source.get(line - 1));
                clause.put("sourceQuote", normalize(String.join(" ", source.subList(first - 1, last))));
                var references = clause.putArray("additionalSourceReferences");
                for (JsonNode reference : definition.path("additionalSourceReferences")) {
                    int begin = reference.path("startLine").asInt(), end = reference.path("endLine").asInt();
                    if (begin < 1 || end < begin || end > source.size() || reference.path("reason").asText().isBlank())
                        throw new IOException("Invalid additional source reference: " + clauseId);
                    ObjectNode retained = reference.deepCopy();
                    var quoted = retained.putArray("lines");
                    for (int line = begin; line <= end; line++) quoted.add(source.get(line - 1));
                    references.add(retained);
                }
                if (definition.has("implementationCandidate")
                        && !Files.isRegularFile(Path.of(definition.path("implementationCandidate").asText())))
                    throw new IOException("Reviewed implementation candidate missing: " + clauseId);
                clause.putArray("executedChains");
                if (definition.has("authoredClauseId")) {
                    JsonNode match = null;
                    for (JsonNode candidate : authored)
                        if (definition.path("authoredClauseId").equals(candidate.path("id"))) match = candidate;
                    if (match == null || !sectionName.equals(match.path("section").asText())
                            || definition.path("equivalenceRationale").asText().isBlank())
                        throw new IOException("Reviewed clause needs explicit same-section equivalence: " + clauseId);
                    String inherited = match.path("status").asText();
                    clause.put("authoredAssessment", inherited);
                    clause.put("implementationStatus", "OPEN_IMPLEMENTATION".equals(inherited)
                            ? "RECONCILIATION_REQUIRED" : inherited);
                    if (match.has("implementation")) clause.set("implementation", match.path("implementation").deepCopy());
                    clause.set("executedChains", match.path("executedChains").deepCopy());
                    linked += match.path("executedChains").size();
                }
                if (definition.has("executionTestCaseIds")) {
                    if (definition.has("authoredClauseId") || definition.path("equivalenceRationale").asText().isBlank()
                            || !Files.isRegularFile(Path.of(definition.path("implementation").asText())))
                        throw new IOException("Direct semantic execution link needs reviewed code equivalence: " + clauseId);
                    var chains = clause.putArray("executedChains");
                    for (JsonNode identity : definition.path("executionTestCaseIds")) {
                        JsonNode match = null;
                        for (JsonNode execution : executions)
                            if (identity.equals(execution.path("testCaseId"))) match = execution;
                        if (match == null || !definition.path("targetRuleId").equals(match.path("targetRuleId"))
                                || !definition.path("targetScope").equals(match.path("scope"))
                                || !match.path("expectedStatus").equals(match.path("actualStatus"))
                                || !match.path("expectedTargetStatus").equals(match.path("actualTargetStatus")))
                            throw new IOException("Semantic target execution missing/mismatched: " + clauseId + "/" + identity);
                        var chain = chains.addObject();
                        for (String field : List.of("businessRequirementId", "scenarioId", "testCaseId", "testDataId",
                                "ruleId", "scope", "validationPath", "expectedStatus", "actualStatus",
                                "targetRuleId", "expectedTargetStatus", "actualTargetStatus"))
                            if (match.has(field)) chain.set(field, match.path(field).deepCopy());
                        linked++;
                    }
                    if (chains.isEmpty()) throw new IOException("Empty semantic execution selection: " + clauseId);
                    clause.put("implementationStatus", "BOUNDED_EXECUTION");
                }
                if (definition.has("executionPredicates")) {
                    String equivalence = definition.path("equivalenceRationale").asText();
                    if (equivalence.isBlank()) equivalence = decisions.path("codeReviews")
                            .path(definition.path("codeReviewId").asText()).path("predicateEquivalence").asText();
                    if (!definition.has("codeReviewId") || equivalence.isBlank()
                            || definition.path("executionPredicates").isEmpty())
                        throw new IOException("Predicate execution links need code equivalence: " + clauseId);
                    clause.put("equivalenceRationale", equivalence);
                    var chains = clause.putArray("executedChains");
                    for (JsonNode predicate : definition.path("executionPredicates")) {
                        JsonNode match = null;
                        for (JsonNode execution : executions)
                            if (predicate.path("testCaseId").equals(execution.path("testCaseId"))) match = execution;
                        if (match == null || !match.path("expectedStatus").equals(match.path("actualStatus"))
                                || !match.path("expectedTargetStatus").equals(match.path("actualTargetStatus")))
                            throw new IOException("Predicate case missing/mismatched: " + clauseId);
                        int matches = 0;
                        for (JsonNode finding : match.path("findings"))
                            if (predicate.path("ruleId").equals(finding.path("ruleId"))
                                    && predicate.path("scope").equals(finding.path("scope"))
                                    && predicate.path("status").equals(finding.path("status"))) matches++;
                        if (matches != 1) throw new IOException("Predicate finding missing/ambiguous: " + clauseId);
                        var chain = chains.addObject();
                        for (String field : List.of("businessRequirementId", "scenarioId", "testCaseId", "testDataId",
                                "ruleId", "scope", "validationPath", "expectedStatus", "actualStatus",
                                "targetRuleId", "expectedTargetStatus", "actualTargetStatus"))
                            if (match.has(field)) chain.set(field, match.path(field).deepCopy());
                        chain.set("verifiedPredicate", predicate.deepCopy());
                        linked++;
                    }
                    clause.put("implementationStatus", "BOUNDED_EXECUTED");
                }
                if (definition.path("implementationStatus").asText().startsWith("OPEN_")
                        && !clause.path("executedChains").isEmpty())
                    throw new IOException("Open disposition conflicts with executed closure: " + clauseId);
                if (finalizedSections.contains(sectionName)) {
                    if ("BOUNDED_EXECUTION".equals(clause.path("implementationStatus").asText())) {
                        if (!definition.has("codeReviewId")) throw new IOException("Final execution needs code review: " + clauseId);
                        clause.put("implementationStatus", "BOUNDED_EXECUTED");
                    }
                    if ("RECONCILIATION_REQUIRED".equals(clause.path("implementationStatus").asText())
                            || clause.has("sameObligationAs"))
                        throw new IOException("Final section still has unresolved/duplicate obligation: " + clauseId);
                }
                clauses.add(clause);
            }
        }
        int aliases = 0;
        for (JsonNode clause : clauses) {
            if (clause.has("sameObligationAs")) {
                if (!clauseIds.contains(clause.path("sameObligationAs").asText())
                        || clause.path("id").equals(clause.path("sameObligationAs")))
                    throw new IOException("Semantic obligation alias missing/self-referential: " + clause.path("id"));
                aliases++;
            }
        }
        var remaining = report.putArray("remainingBySection");
        var reconciliations = report.putArray("implementationAssessmentBySection");
        int residualRemaining = 0, recordsRemaining = 0, structuralRemaining = 0;
        var uniqueExecutedCases = new HashSet<String>();
        var statusCounts = report.putObject("reviewedClauseStatusCounts");
        for (JsonNode section : inventory.path("sections")) {
            var item = remaining.addObject().put("section", section.path("section").asText());
            var residualIds = item.putArray("unreviewedResidualSpanIds");
            var recordIds = item.putArray("unreviewedNonStructuralRecordIds");
            var structuralIds = item.putArray("unreviewedStructuralRecordIds");
            for (JsonNode record : section.path("unresolvedSourceSpans"))
                if (!consumed.contains(record.path("id").asText())) residualIds.add(record.path("id").asText());
            for (JsonNode record : section.path("sourceClauses")) if (!consumed.contains(record.path("id").asText())) {
                if (record.path("atomicSplitVerified").asBoolean()) structuralIds.add(record.path("id").asText());
                else recordIds.add(record.path("id").asText());
            }
            residualRemaining += residualIds.size();
            recordsRemaining += recordIds.size();
            structuralRemaining += structuralIds.size();
            item.put("residualReviewComplete", residualIds.isEmpty());
            item.put("nonStructuralSourceRecordReviewComplete", recordIds.isEmpty());
            item.put("allSourceRecordReviewComplete", recordIds.isEmpty() && structuralIds.isEmpty());
            boolean finalized = finalizedSections.contains(section.path("section").asText());
            if (finalized && (!residualIds.isEmpty() || !recordIds.isEmpty() || !structuralIds.isEmpty()))
                throw new IOException("Final section has unreviewed source records: " + section.path("section"));
            item.put("sectionAtomicDenominatorEstablished", finalized);
            var assessments = reconciliations.addObject().put("section", section.path("section").asText());
            for (String status : List.of("BOUNDED_EXECUTION", "BOUNDED_EXECUTED", "OPEN_IMPLEMENTATION",
                    "OPEN_EXECUTION_EVIDENCE", "RECONCILIATION_REQUIRED",
                    "SOURCE_CONFLICT", "EXTERNAL_EVIDENCE")) assessments.putArray(status);
            for (JsonNode clause : clauses) if (clause.path("section").equals(section.path("section"))) {
                String status = clause.path("implementationStatus").asText();
                ((com.fasterxml.jackson.databind.node.ArrayNode) assessments.path(status)).add(clause.path("id").asText());
                statusCounts.put(status, statusCounts.path(status).asInt() + 1);
                for (JsonNode chain : clause.path("executedChains")) uniqueExecutedCases.add(chain.path("testCaseId").asText());
            }
        }
        report.putObject("summary").put("reviewedDecisions", reviewed.size()).put("reviewedResidualSpans", residualReviewed)
                .put("reviewedNonStructuralRecords", statementsReviewed).put("reviewedStructuralRecords", structuralReviewed)
                .put("reviewedIndependentClauses", clauses.size()).put("verifiedExecutedChainLinks", linked)
                .put("explicitRepeatedObligationAliases", aliases)
                .put("reviewedDistinctObligationsInPartialScope", clauses.size() - aliases)
                .put("uniqueReusedCanonicalCases", uniqueExecutedCases.size())
                .put("remainingResidualSpans", residualRemaining).put("remainingNonStructuralRecords", recordsRemaining)
                .put("remainingStructuralRecordsNeedingSemanticReview", structuralRemaining)
                .put("certifiedWholeSourceAtomicDenominator", false);
        var finalScope = report.putObject("deduplicatedReconciledScope");
        var finalSections = finalScope.putArray("sections");
        var finalStatuses = finalScope.putObject("statusCounts");
        int finalDenominator = 0;
        for (int number = 1; number <= 49; number++) {
            String name = "12." + number;
            if (!finalizedSections.contains(name)) continue;
            finalSections.add(name);
            for (JsonNode clause : clauses) if (name.equals(clause.path("section").asText())) {
                finalDenominator++;
                String status = clause.path("implementationStatus").asText();
                finalStatuses.put(status, finalStatuses.path(status).asInt() + 1);
            }
        }
        if (finalSections.size() != finalizedSections.size()) throw new IOException("Unknown final reconciliation section");
        finalScope.put("distinctObligations", finalDenominator).put("wholeChapterDenominator", false)
                .put("policy", "Final obligations replace prior candidates in reviewedClauses for these sections; "
                        + "supporting decisions remain source-accounted. Code reviews and exact predicate/case links "
                        + "do not claim unexercised boundaries or external authenticity.");
        return report;
    }

    private static boolean covers(JsonNode span, int line) {
        return span.path("sourceEvidence").path("startLine").asInt() <= line
                && span.path("sourceEvidence").path("endLine").asInt() >= line;
    }

    private static void inventoryStatements(SourceText text, int from, int to, String section, String field,
                                           com.fasterxml.jackson.databind.node.ArrayNode clauses,
                                           com.fasterxml.jackson.databind.node.ArrayNode pending, JsonNode authored,
                                           List<String> source) throws IOException {
        String body = text.text().substring(from, to);
        var breaks = java.util.regex.Pattern.compile(
                "(?<=[.!?])\\s+(?=[A-Z•])|(?m)(?=^•)|(?m)(?=^Source:\\s)|(?m)(?=^Fixed(?:\\s+value)?:\\s)"
                        + "|(?m)(?=^1Required/Optional/Conditional)|(?m)(?=^Table (?:ID|Length|Data)\\s)"
                        + "|(?m)(?=^Valid Values:)|(?m)(?=^Note:)|(?m)(?=^Example\\b)"
                        + "|(?im)(?=^Fixed(?:\\s+value|\\s+length)?\\s*:?[ \\t]*\\d)");
        var matcher = breaks.matcher(body);
        int start = 0;
        while (matcher.find()) {
            if (matcher.start() > 0 && body.substring(0, matcher.start()).matches("(?s).*\\b(?:No|Nos|Var|Len|Max|Ex)\\.$"))
                continue;
            if (matcher.start() > start)
                inventoryStatement(text, from + start, from + matcher.start(), section, field, clauses, pending, authored, source);
            start = matcher.end();
        }
        if (start < body.length())
            inventoryStatement(text, from + start, to, section, field, clauses, pending, authored, source);
    }

    private static void inventoryStatement(SourceText text, int from, int to, String section, String field,
                                          com.fasterxml.jackson.databind.node.ArrayNode clauses,
                                          com.fasterxml.jackson.databind.node.ArrayNode pending, JsonNode authored,
                                          List<String> source) throws IOException {
        while (from < to && Character.isWhitespace(text.text().charAt(from))) from++;
        while (to > from && Character.isWhitespace(text.text().charAt(to - 1))) to--;
        if (from == to) return;
        String quote = normalize(text.text().substring(from, to));
        boolean fixed = quote.matches("(?i)^Fixed(?:\\s+(?:value|length))?\\s*:?.*");
        boolean origin = quote.matches("^Source:?\\s+.*");
        boolean structural = fixed || origin;
        List<String> split = reviewedSplits(quote);
        if (!split.isEmpty()) {
            for (String requirement : split) {
                var clause = inventorySpan(clauses, section, text, from, to, "REVIEWED_INDEPENDENT_CLAUSE",
                        field.isEmpty() ? requirement : field + ": " + requirement, field, authored, source);
                clause.put("atomicSplitVerified", true);
            }
            return;
        }
        boolean heading = quote.matches("(?i)^(?:Element Name|Len\\.|Max\\.? ?Len\\.?|Description|Entry is: R/O/C1?|"
                + "1Required/Optional/Conditional|Element No\\.|Field No\\.|Field Attributes Description|"
                + "R/O/C1?|Table Data NA)$")
                || quote.matches("^12\\.\\d+(?:\\.\\d+)? [^.]+(?:This section describes .*\\.)?$")
                || quote.matches("(?i)^This section describes .*")
                || quote.matches("(?i)^(?:The following table illustrates .*|.+ Data Segment (?:Format )?Data Segment No\\. \\w+(?: Field No\\.)?)$")
                || quote.matches("(?i)^(?:Data Segment No\\. \\w+(?: Field No\\.)?|For an example .*|POS Purchase Example\\.)$")
                || quote.matches("(?i)^.+ (?:Segment )?Format(?: \\([^)]*\\))? Data Segment No\\..*")
                || quote.matches("(?i)^.+ Data Segment No\\.\\s+\\w+(?: Field No\\.)?")
                || quote.matches("^DL[1-8], .+ Data Segment\\.$")
                || quote.matches("(?i)^Element Name Max\\.? ?(?:Len\\.?)?$")
                || quote.matches("(?i)^Len\\. Entry is: R/O/C1?$")
                || quote.matches("(?i)^Field No\\. Element No\\.(?: Element Name)?$")
                || quote.matches("(?i)^Element Name Max \\.?Len\\s*\\.?$")
                || quote.matches("^(?:Source:|13-1|< ▲ >)$");
        if (heading) {
            var supporting = inventorySpan(pending, section, text, from, to, "SUPPORTING_LAYOUT_LABEL", quote, field, authored, source);
            supporting.put("remainingWork", "Retained source layout/heading/reference label; not an executable requirement or atomic denominator entry.");
            return;
        }
        boolean normative = quote.toLowerCase(java.util.Locale.ROOT).matches(
                ".*\\b(must|required|optional|conditional|shall|only|always|never|maximum|minimum|valid|fixed|when|if|should|not|follows?|used|included|repeated|originates|indicates|identifies|appears|contains|contained|sent|sends?|equal|length|limited|separated|denotes|refer|exclusive|supports|provided|returned|will|can|may|bytes|characters|format|represents|corresponds|justified|filled|reserved|echoed|validated)\\b.*");
        boolean reference = quote.contains("BUYPASS®_Platform_ATL105_Specification_Updates_for_TransArmor_Processing")
                || quote.contains("Asynchronous Communications Protocol Specifications")
                || quote.contains("Buypass Incomm Market Basket Data format document");
        boolean compound = quote.matches("(?is).*\\b(and|also|while|instead|exception)\\b.*")
                || quote.contains("Table ID") || quote.contains("Valid Values:") || quote.contains("Example")
                || quote.contains("Field Attributes Description") || quote.matches("(?s).*\\b[ROC]1?\\s+Description.*");
        boolean definition = !field.isEmpty() && quote.matches("(?i)^(?:Identifies|Indicates|Describes|Contains)\\b.*");
        var nested = java.util.regex.Pattern.compile("^(Table ID|Table Length|Table Data)\\s+(\\.?\\.?[aAnN]+(?:[0-9]+|\\*+)|NA)\\s+(.+)$").matcher(quote);
        if (nested.matches()) {
            String subject = nested.group(1);
            var representation = inventorySpan(clauses, section, text, from, to, "NESTED_TABLE_ATTRIBUTE",
                    subject + " has source-listed attributes " + nested.group(2) + ".", subject, authored, source);
            representation.put("declaredAttributes", nested.group(2));
            var details = inventorySpan(clauses, section, text, from, to, "NESTED_TABLE_CONSTRAINT",
                    subject + ": " + nested.group(3), subject, authored, source);
            details.put("furtherAtomicSplitRequired", nested.group(3).matches("(?i).*\\b(and|if|when|note|example)\\b.*"));
            return;
        }
        if (definition || quote.startsWith("Format:") || quote.startsWith("Format is")) compound = false;
        if (!field.isEmpty() && !compound && !quote.startsWith("Example")) normative = true;
        if (structural || normative && !compound || reference) {
            String kind = fixed ? "FIXED_VALUE" : origin ? "DECLARED_ORIGIN" : reference ? "EXTERNAL_REFERENCE" : "SOURCE_STATEMENT";
            String requirement = field.isEmpty() ? quote : field + ": " + quote;
            var clause = inventorySpan(clauses, section, text, from, to, kind, requirement, field, authored, source);
            if (reference) {
                clause.put("status", "EXTERNAL_EVIDENCE");
                clause.put("remainingWork", "Named source document is outside this source-local chapter; do not fabricate its omitted requirements.");
            }
        } else {
            inventorySpan(pending, section, text, from, to,
                    normative ? "OPEN_ATOMIC_SPLIT" : "SUPPORTING_SOURCE_REQUIRES_CLASSIFICATION",
                    normative ? "Split the exact compound/table/list/example text into independent normative clauses; code execution cannot substitute for this split."
                            : "Classify this exact heading, continuation, list or descriptive text; it may contain an obligation without a normative keyword.",
                    field, authored, source);
        }
    }

    private static ObjectNode inventorySpan(com.fasterxml.jackson.databind.node.ArrayNode output, String section,
                                            SourceText text, int from, int to, String kind, String requirement, String field,
                                            JsonNode authored, List<String> source) throws IOException {
        while (from < to && Character.isWhitespace(text.text().charAt(from))) from++;
        while (to > from && Character.isWhitespace(text.text().charAt(to - 1))) to--;
        int first = text.lines()[from], last = text.lines()[to - 1];
        String quote = normalize(text.text().substring(from, to));
        if (kind.equals("REVIEWED_INDEPENDENT_CLAUSE") && quote.equals("In this case, the tax type and amount should be omitted."))
            first--;
        String full = normalize(String.join(" ", source.subList(first - 1, last)));
        if (!full.contains(quote)) throw new IOException("Source clause quote is not present in exact anchor: " + section + "/" + first);
        var span = output.addObject().put("id", section + "-L" + first + "-" + last + "-" + kind + "-" + (output.size()))
                .put("section", section).put("requirement", requirement).put("sourceQuote", quote)
                .put("sourceClauseKind", kind).put("status", "OPEN_IMPLEMENTATION")
                .put("atomicExhaustivenessVerified", false);
        span.put("atomicSplitVerified", List.of("DECLARED_FIELD_LENGTH", "FIELD_ENTRY_CLASSIFICATION",
                "NESTED_TABLE_ATTRIBUTE", "REVIEWED_INDEPENDENT_CLAUSE").contains(kind));
        if (!field.isEmpty()) span.put("field", field);
        for (int line = first; line >= 1; line--) {
            String heading = source.get(line - 1).trim();
            if (heading.matches(java.util.regex.Pattern.quote(section) + "(?:\\.\\d+)?\\s+.*")) {
                span.put("sourceSubsection", heading.split("\\s+", 2)[0]);
                span.put("sourceSubsectionHeading", heading);
                break;
            }
            if (heading.matches("12\\.\\d+\\s+.*") && !heading.startsWith(section + " ")) break;
        }
        var anchor = span.putObject("sourceEvidence").put("file", "docs/specs/extracted_text.txt")
                .put("startLine", first).put("endLine", last);
        var lines = anchor.putArray("lines");
        for (int line = first; line <= last; line++) lines.add(source.get(line - 1));
        var navigation = span.putArray("authoredClauseNavigationIds");
        for (JsonNode clause : authored) if (section.equals(clause.path("section").asText())
                && clause.path("sourceEvidence").path("startLine").asInt() <= last
                && clause.path("sourceEvidence").path("endLine").asInt() >= first)
            navigation.add(clause.path("id").asText());
        span.putArray("executedChains");
        span.put("remainingWork", "Reconcile this exact source obligation to validator and independently targeted canonical/intake positive, negative, boundary and missing-evidence executions. Nearby authored links are navigation only.");
        if (kind.equals("OPEN_ATOMIC_SPLIT") || kind.equals("SUPPORTING_SOURCE_REQUIRES_CLASSIFICATION")) {
            span.put("remainingWork", requirement);
            span.put("requirement", quote);
        }
        return span;
    }

    private static String normalize(String text) { return text.replaceAll("\\s+", " ").trim(); }

    private static List<String> reviewedSplits(String quote) {
        if (quote.contains("A Product Data Field Delimiter always follows Quantity and Unit Price"))
            return List.of("A Product Data Field Delimiter follows Quantity.",
                    "A Product Data Field Delimiter follows Unit Price.");
        if (quote.contains("A Field Separator always follows Segment Type and Segment Length"))
            return List.of("A Field Separator follows Segment Type.", "A Field Separator follows Segment Length.");
        if (quote.contains("entries should be in the same order in both the segments"))
            return List.of("Each Segment102 entry has a corresponding Tax by Product entry.",
                    "Tax by Product entries retain the same order as Segment102 entries.");
        if (quote.equals("In this case, the tax type and amount should be omitted."))
            return List.of("Under the preceding no-tax N condition, Tax Type is structurally omitted.",
                    "Under the preceding no-tax N condition, Tax Amount is structurally omitted.");
        if (quote.contains("this value should be “N” and the tax type and amount should be skipped"))
            return List.of("If this tax is not applicable to the product, the Inclusive/Exclusive flag isN.",
                    "If this tax is not applicable to the product, Tax Type is omitted.",
                    "If this tax is not applicable to the product, Tax Amount is omitted.");
        if (quote.startsWith("The Block Number starts at 1 and is incremented"))
            return List.of("Site configuration Block Number starts at1.",
                    "Site configuration Block Number increments with each sent site-configuration data segment.");
        if (quote.startsWith("A value of 0 in the Block Number field is sent as a final message"))
            return List.of("Site configuration Block Number0 is sent as a final message.",
                    "Final Block Number0 contains no site-configuration data.");
        if (quote.startsWith("If multiple products in a transaction map to the same category"))
            return List.of("Products mapping to the same Dynamic Card Table category are summed.",
                    "The same-category products are included in a single category.");
        if (quote.startsWith("Dynamic Card Table, and Custom Receipt Text, and Host Discounts are driven by the host"))
            return List.of("Dynamic Card Table is host-driven data push toPOS.",
                    "Custom Receipt Text is host-driven data push toPOS.", "Host Discounts are host-driven data push toPOS.",
                    "Site Configuration isPOS-driven data push tohost.", "Fuel Volume isPOS-driven data push tohost.");
        if (quote.startsWith("A,B Alpha numeric;"))
            return List.of("Prompt edit-mask A andB are alphanumeric and treated identically as of01/02/2015.",
                    "Prompt edit-mask N is numeric only.", "Prompt edit-mask I is free format.",
                    "Prompt edit-mask O permits an optional response.");
        if (quote.startsWith("Once the device receives the proprietary load response data"))
            return List.of("Received proprietary host-discount data is applied to applicable transactions.",
                    "Received host-discount data is applied during the designated date and time period.");
        if (quote.contains("required on all initial and recurring transactions involving tokenized data"))
            return List.of("Segment123 is required on initial transactions involving tokenized data.",
                    "Segment123 is required on recurring transactions involving tokenized data.",
                    "Segment123 is required on transactions including MasterCard Token data.",
                    "Segment123 is required on transactions including Digital Secure Remote Payment data.",
                    "Segment123 is required on transactions including Visa TAVV data.");
        if (quote.startsWith("Left justified and space filled"))
            return List.of("The field is left justified.", "The field is space filled.");
        if (quote.contains("For DUKPT PIN encryption, this is a 16- to 20-byte KSN plus a 16-byte PIN block"))
            return List.of("For DUKPT PIN encryption, Encrypted PIN Block Data contains a16-to20-byte KSN.",
                    "For DUKPT PIN encryption, Encrypted PIN Block Data contains a16-byte PIN block.");
        if (quote.contains("quantity is 0.05") && quote.contains("‘205’") && quote.contains("‘25’"))
            return List.of("At two assumed decimal places, source quantity0.05 is represented as205.",
                    "At two assumed decimal places, quantity25 is invalid for source quantity0.05 because fractional digits are missing.");
        if (quote.contains("unit price is $0.059") && quote.contains("‘3059’") && quote.contains("‘359’"))
            return List.of("At three assumed decimal places, source unit price0.059 is represented as3059.",
                    "At three assumed decimal places, unit price359 is invalid for source unit price0.059 because fractional digits are missing.");
        if (quote.startsWith("This data will be maintained at the Buypass Host and when this data is changed"))
            return List.of("EMV floor-limit data is maintained at the Buypass Host.",
                    "When floor-limit data changes, the table-download flag is set for all terminals with the specified terminal-level Special.");
        if (quote.startsWith("Note: There is a Field Separator between") || quote.startsWith("Note: Field Nos")
                || quote.startsWith("Note: Field No.s")) {
            var pairs = java.util.regex.Pattern.compile("(?:Field Nos?\\.?s?\\s*)?(\\d+)\\s+and\\s+(\\d+)").matcher(quote);
            var rules = new java.util.ArrayList<String>();
            while (pairs.find()) rules.add("A Field Separator separates Field" + pairs.group(1) + " and Field" + pairs.group(2) + ".");
            if (quote.contains("and EMV Additional Information Section"))
                rules.add("A Field Separator separates Field6 and the EMV Additional Information Section.");
            if (quote.contains("and Variable Information Section"))
                rules.add("A Field Separator separates Field2 and Variable Information Section.");
            if (rules.size() > 1) return List.copyOf(rules);
        }
        return List.of();
    }

    private static int candidate(com.fasterxml.jackson.databind.node.ArrayNode records, String section, int start, int end,
                                 String requirement, boolean field, JsonNode authored, List<String> source) {
        if (!field && !requirement.toLowerCase(java.util.Locale.ROOT).matches(
                ".*\\b(must|required|optional|conditional|shall|only|always|never|maximum|minimum|valid|fixed|when|if|should|not|follow|used|included|repeated|originates|indicates|identifies|refer)\\b.*")) return 0;
        var record = records.addObject().put("id", section + "-SOURCE-L" + start + "-" + end)
                .put("requirement", requirement).put("status", "OPEN_IMPLEMENTATION")
                .put("candidateKind", field ? "FIELD_RECORD_NEEDS_ATOMIC_SPLITTING" : "SOURCE_SENTENCE_NEEDS_NORMATIVE_RECONCILIATION")
                .put("atomicSplitVerified", false);
        var anchor = record.putObject("sourceEvidence").put("file", "docs/specs/extracted_text.txt").put("startLine", start).put("endLine", end);
        var lines = anchor.putArray("lines");
        for (int line = start; line <= end; line++) lines.add(source.get(line - 1));
        var overlaps = record.putArray("authoredClauseNavigationIds");
        for (JsonNode clause : authored) if (section.equals(clause.path("section").asText())
                && clause.path("startLine").asInt() <= end && clause.path("endLine").asInt() >= start)
            overlaps.add(clause.path("id").asText());
        record.putArray("executedChains");
        return 1;
    }
}
