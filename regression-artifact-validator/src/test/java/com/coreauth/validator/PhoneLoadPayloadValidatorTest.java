package com.coreauth.validator;

import com.coreauth.validator.canonical.PhoneLoadPayloadValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PhoneLoadPayloadValidatorTest {
    private static final ObjectMapper M=new ObjectMapper();
    @Test void acceptsRequest(){assertThat(new PhoneLoadPayloadValidator().validatePayload(request()).errors()).isEmpty();}
    @Test void acceptsResponse(){assertThat(new PhoneLoadPayloadValidator().validatePayload(response()).errors()).isEmpty();}
    @Test void rejectsWrongLoadType(){ObjectNode p=request();((ObjectNode)p.path(PhoneLoadPayloadValidator.REQUEST_ROOT)).put("LoadType","D");assertRule(p,"PHONE-REQ-003");}
    @Test void rejectsMissingPrimaryPhone(){ObjectNode p=response();((ObjectNode)p.path(PhoneLoadPayloadValidator.RESPONSE_ROOT).path("DL2")).remove("PrimaryPhoneNumber");assertRule(p,"PHONE-RESP-004");}
    private static void assertRule(ObjectNode p,String id){assertThat(new PhoneLoadPayloadValidator().validatePayload(p).errors()).anyMatch(e->e.reason().contains(id));}
    private static ObjectNode request(){ObjectNode p=M.createObjectNode(),n=p.putObject(PhoneLoadPayloadValidator.REQUEST_ROOT);n.put("InformationByte","?");n.put("TerminalIdentifier","SYNTHETIC01XX");n.put("LoadType","P");return p;}
    private static ObjectNode response(){ObjectNode p=M.createObjectNode(),n=p.putObject(PhoneLoadPayloadValidator.RESPONSE_ROOT);n.put("DataTypeIndicator","!");n.put("EndOfDataIndicator","~");ObjectNode d=n.putObject("DL2");d.put("DialStringType","1");d.put("PrimaryPhoneNumber","5551234567");d.put("PrimaryDialStringTerminator","A");d.put("SecondaryDialStringTerminator","F");return p;}
}
