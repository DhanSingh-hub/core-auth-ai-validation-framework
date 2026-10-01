package com.coreauth.validator;

import com.coreauth.validator.canonical.SoftwareLoadPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SoftwareLoadPayloadValidatorTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Test void acceptsRequest() { assertThat(new SoftwareLoadPayloadValidator().validatePayload(request()).errors()).isEmpty(); }
    @Test void acceptsResponse() { assertThat(new SoftwareLoadPayloadValidator().validatePayload(response()).errors()).isEmpty(); }
    @Test void rejectsWrongLoadType() { ObjectNode p=request(); ((ObjectNode)p.path(SoftwareLoadPayloadValidator.REQUEST_ROOT)).put("LoadType","D"); assertRule(p,"SW-REQ-003"); }
    @Test void rejectsMissingDl5() { ObjectNode p=response(); ((ObjectNode)p.path(SoftwareLoadPayloadValidator.RESPONSE_ROOT)).remove("DL5"); assertRule(p,"SW-RESP-003"); }
    private static void assertRule(ObjectNode p,String id) { assertThat(new SoftwareLoadPayloadValidator().validatePayload(p).errors()).anyMatch(e -> e.reason().contains(id)); }
    private static ObjectNode request() { ObjectNode p=MAPPER.createObjectNode(), n=p.putObject(SoftwareLoadPayloadValidator.REQUEST_ROOT); n.put("InformationByte","?"); n.put("TerminalIdentifier","SYNTHETIC01XX"); n.put("LoadType","P"); n.put("HardwareVersion","HW01"); n.put("SoftwareVersion","SW010203"); n.put("FirmwareVersion","FW010203"); return p; }
    private static ObjectNode response() { ObjectNode p=MAPPER.createObjectNode(), n=p.putObject(SoftwareLoadPayloadValidator.RESPONSE_ROOT); n.put("StartOfDataBlockIndicator",")"); n.putObject("DL4").put("EncodedLength","52"); n.putObject("DL5").put("EncodedLength","66"); return p; }
}
