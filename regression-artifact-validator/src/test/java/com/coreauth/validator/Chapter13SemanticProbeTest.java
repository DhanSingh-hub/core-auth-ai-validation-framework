package com.coreauth.validator;

import com.coreauth.validator.paths.Atl105Paths;
import com.coreauth.validator.validation.Chapter13DataElementValidator;
import com.coreauth.validator.validation.Atl105ElementValueValidator.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class Chapter13SemanticProbeTest {
    @TestFactory
    List<DynamicTest> executesEveryIndependentSemanticFixtureWithoutMutatingIt() throws IOException {
        var mapper = new ObjectMapper();
        var validator = new Chapter13DataElementValidator(Atl105Paths.root());
        var probes = mapper.readTree(Atl105Paths.testJson("chapter-13-semantic-probes.json").toFile()).path("probes");
        List<DynamicTest> tests = new ArrayList<>();
        for (var probe : probes) {
            tests.add(dynamicTest(probe.path("id").asText(), () -> {
                var observation = mapper.createObjectNode().put("specificationVersion", "2026-3")
                        .put("messageFamily", "SOURCE_DOMAIN_OBSERVATION").put("segment", "100")
                        .put("completeness", "PARTIAL").put("representation", "LOGICAL");
                observation.set("elements", probe.path("elements").deepCopy());
                for (String field : List.of("qualifiers", "records", "history", "messageFamily", "segment", "completeness", "representation", "segmentsPresent")) {
                    if (probe.has(field)) observation.set(field, probe.path(field).deepCopy());
                }
                var before = observation.deepCopy();
                var result = validator.validate(observation);
                String id = probe.path("element").asText(), rule = "CH13-E" + id + "-" + probe.path("rule").asText();
                var expected = Status.valueOf(probe.path("expected").asText());
                var targets = result.findings().stream().filter(f -> f.element().equals(id) && f.ruleId().equals(rule)).toList();
                assertThat(targets).hasSize(1);
                assertThat(targets.getFirst().status()).isEqualTo(expected);
                if (expected == Status.INVALID) assertThat(result.status()).isEqualTo(Status.INVALID);
                assertThat(observation).isEqualTo(before);
            }));
        }
        return tests;
    }
}
