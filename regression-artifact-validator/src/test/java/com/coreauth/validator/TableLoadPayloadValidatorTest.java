package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixETableLoadCardTypeCodes;
import com.coreauth.validator.canonical.TableLoadPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableLoadPayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test void acceptsRequest() { assertThat(new TableLoadPayloadValidator().validatePayload(request()).errors()).isEmpty(); }
    @Test void acceptsResponseWithoutConditionalDl6() { assertThat(new TableLoadPayloadValidator().validatePayload(response("001", false)).errors()).isEmpty(); }
    @Test void requiresDl6ForCardType173() { assertRule(response("173", false), "TABLE-RESP-005"); }
    @Test void acceptsCardType173WhenDl6Present() { assertThat(new TableLoadPayloadValidator().validatePayload(response("173", true)).errors()).isEmpty(); }
    @Test void appendixETableLoadCatalogContainsFortyThreeCardTypesAndThirteenFeatureCodes() {
        assertThat(AppendixETableLoadCardTypeCodes.cardTypeCodes()).hasSize(43);
        assertThat(AppendixETableLoadCardTypeCodes.terminalFeatureCodes()).hasSize(13);
        assertThat(AppendixETableLoadCardTypeCodes.supportedCodes()).hasSize(56);
        assertThat(AppendixETableLoadCardTypeCodes.cardTypeCodes())
                .doesNotContainAnyElementsOf(AppendixETableLoadCardTypeCodes.terminalFeatureCodes());
    }
    @Test void acceptsEveryAppendixETableLoadCodeInDl1() {
        for (String code : AppendixETableLoadCardTypeCodes.supportedCodes()) {
            assertThat(new TableLoadPayloadValidator().validatePayload(response(code, "173".equals(code))).errors())
                    .as("Table Load CardType %s", code).isEmpty();
        }
    }
    @Test void rejectsCodeOutsideAppendixETableLoadTables() {
        assertRule(response("999", false), "TABLE-RESP-006");
        assertRule(response("900", false), "TABLE-RESP-006");
    }
    @Test void rejectsWrongLoadType() { ObjectNode p=request(); ((ObjectNode)p.path(TableLoadPayloadValidator.REQUEST_ROOT)).put("LoadType", "D"); assertRule(p,"TABLE-REQ-003"); }
    @Test void rejectsTerminalIdentifierNotThirteenCharacters() { ObjectNode p=request(); ((ObjectNode)p.path(TableLoadPayloadValidator.REQUEST_ROOT)).put("TerminalIdentifier", "SYNTHETIC01X"); assertRule(p,"TABLE-REQ-002"); }

    private static void assertRule(ObjectNode p, String rule) { assertThat(new TableLoadPayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains(rule)); }
    private static ObjectNode request() {
        ObjectNode p=MAPPER.createObjectNode(), r=p.putObject(TableLoadPayloadValidator.REQUEST_ROOT);
        // Element 102 is 13 bytes for load requests: Device Type(2) State(2) Merchant(6) Device(3).
        r.put("InformationByte","?"); r.put("TerminalIdentifier","01GA123456001"); r.put("LoadType","P"); r.put("HardwareVersion","HW01"); r.put("SoftwareVersion","SW010203"); r.put("FirmwareVersion","FW010203"); return p;
    }
    private static ObjectNode response(String cardType, boolean includeDl6) {
        ObjectNode p=MAPPER.createObjectNode(), r=p.putObject(TableLoadPayloadValidator.RESPONSE_ROOT);
        r.putObject("DL1").put("CardType", cardType); r.putObject("DL2"); r.putObject("DL3"); if (includeDl6) r.putObject("DL6"); return p;
    }
}
