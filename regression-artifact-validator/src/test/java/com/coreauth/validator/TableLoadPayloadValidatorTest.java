package com.coreauth.validator;

import com.coreauth.validator.canonical.TableLoadPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableLoadPayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test void acceptsRequest() { assertThat(new TableLoadPayloadValidator().validatePayload(request()).errors()).isEmpty(); }
    @Test void acceptsResponseWithoutConditionalDl6() { assertThat(new TableLoadPayloadValidator().validatePayload(response(false)).errors()).isEmpty(); }
    @Test void requiresDl6ForCardType173() { assertRule(response(true), "TABLE-RESP-005"); }
    @Test void rejectsWrongLoadType() { ObjectNode p=request(); ((ObjectNode)p.path(TableLoadPayloadValidator.REQUEST_ROOT)).put("LoadType", "D"); assertRule(p,"TABLE-REQ-003"); }

    private static void assertRule(ObjectNode p, String rule) { assertThat(new TableLoadPayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains(rule)); }
    private static ObjectNode request() {
        ObjectNode p=MAPPER.createObjectNode(), r=p.putObject(TableLoadPayloadValidator.REQUEST_ROOT);
        r.put("InformationByte","?"); r.put("TerminalIdentifier","SYNTHETIC01X"); r.put("LoadType","P"); r.put("HardwareVersion","HW01"); r.put("SoftwareVersion","SW010203"); r.put("FirmwareVersion","FW010203"); return p;
    }
    private static ObjectNode response(boolean dl6) {
        ObjectNode p=MAPPER.createObjectNode(), r=p.putObject(TableLoadPayloadValidator.RESPONSE_ROOT);
        r.putObject("DL1").put("CardType", dl6 ? "173" : "001"); r.putObject("DL2"); r.putObject("DL3"); if (dl6) r.putObject("DL6"); return p;
    }
}
