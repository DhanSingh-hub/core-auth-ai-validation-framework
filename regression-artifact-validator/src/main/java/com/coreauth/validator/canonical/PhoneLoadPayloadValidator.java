package com.coreauth.validator.canonical;

import com.coreauth.validator.validation.ValidationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

/** Validates the positional Phone Load request and DL2 response envelope from ATL105 Section 11.7.2. */
public final class PhoneLoadPayloadValidator {
    public static final String REQUEST_ROOT = "Phone Load Request";
    public static final String RESPONSE_ROOT = "Phone Load Response";
    private static final String SOURCE = "PhoneLoadPayload";
    private static final Pattern TERMINAL_ID = Pattern.compile("^[A-Za-z0-9]{13}$");
    private static final Pattern ALNUM_1 = Pattern.compile("^[A-Za-z0-9]$");
    private static final Pattern PHONE = Pattern.compile("^[A-Za-z0-9]{1,18}$");

    private final ObjectMapper objectMapper;
    public PhoneLoadPayloadValidator() { this(new ObjectMapper()); }
    PhoneLoadPayloadValidator(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    public ValidationResult validateFile(Path file) {
        ValidationResult result = new ValidationResult(file == null ? "phone-load-payload" : file.getFileName().toString());
        if (file == null) { result.addError(SOURCE, "AI JSON file is required"); return result; }
        try { validatePayload(objectMapper.readTree(file.toFile()), result); }
        catch (IOException e) { result.addError(SOURCE, "AI JSON is not readable: " + e.getMessage()); }
        return result;
    }
    public ValidationResult validatePayload(JsonNode payload) {
        ValidationResult result = new ValidationResult("phone-load-payload"); validatePayload(payload,result); return result;
    }
    private static void validatePayload(JsonNode payload, ValidationResult result) {
        if (payload == null || !payload.isObject()) { result.addError(SOURCE,"AI JSON root must be an object"); return; }
        boolean req=payload.path(REQUEST_ROOT).isObject(), resp=payload.path(RESPONSE_ROOT).isObject();
        if (req == resp) { result.addError(SOURCE,"AI JSON must contain exactly one Phone Load Request or Response root"); return; }
        if (req) validateRequest(payload.path(REQUEST_ROOT),result); else validateResponse(payload.path(RESPONSE_ROOT),result);
    }
    private static void validateRequest(JsonNode n, ValidationResult r) {
        required(n,"InformationByte","?","PHONE-REQ-001",r);
        pattern(n,"TerminalIdentifier",TERMINAL_ID,"13 alphanumeric characters","PHONE-REQ-002",r);
        required(n,"LoadType","P","PHONE-REQ-003",r);
    }
    private static void validateResponse(JsonNode n, ValidationResult r) {
        required(n,"DataTypeIndicator","!","PHONE-RESP-001",r);
        JsonNode dl2=n.path("DL2");
        if (!dl2.isObject()) { r.addError(SOURCE,"DL2 Dial String Data Segment is required (PHONE-RESP-002)"); return; }
        required(dl2,"DialStringType","1","PHONE-RESP-003",r);
        pattern(dl2,"PrimaryPhoneNumber",PHONE,"1-18 alphanumeric characters","PHONE-RESP-004",r);
        required(dl2,"PrimaryDialStringTerminator","A","PHONE-RESP-005",r);
        required(dl2,"SecondaryDialStringTerminator","F","PHONE-RESP-006",r);
        required(n,"EndOfDataIndicator","~","PHONE-RESP-007",r);
    }
    private static void required(JsonNode n,String f,String expected,String rule,ValidationResult r){if(!expected.equals(text(n,f)))r.addError(SOURCE,f+" must be "+expected+" ("+rule+")");}
    private static void pattern(JsonNode n,String f,Pattern p,String d,String rule,ValidationResult r){String v=text(n,f);if(v==null||!p.matcher(v).matches())r.addError(SOURCE,f+" must be "+d+" ("+rule+")");}
    private static String text(JsonNode n,String f){return n.has(f)&&!n.path(f).isNull()?n.path(f).asText():null;}
}
