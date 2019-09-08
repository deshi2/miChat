package com.mirosh;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageController {

    public Boolean isHelloMessage(String message) {
        return getPurpose(createJSON(message)).equals("hello");
    }

    public String createHelloMessage(String senderName, String recieverName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender", senderName);
        jsonObject.put("purpose", "hello");
        jsonObject.put("message", "Добро пожаловать, " + recieverName + "!");

        return jsonObject.toString();
    }

    public String createMessage(String senderName, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender", senderName);
        jsonObject.put("purpose", "messaging");
        jsonObject.put("message", message);

        return jsonObject.toString();
    }

    private JSONObject createJSON(String message) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(message);
        }catch (ParseException exception) {
            throw new RuntimeException(exception);
        }
        return jsonObject;
    }

    public String createDisplayableText(String message) {
        JSONObject jsonObject = createJSON(message);
        String sender = getSenderName(jsonObject);
        String text   = getMessage(jsonObject);
        return sender + ": " + text;
    }

    public String getSenderName(String message) {
        JSONObject jsonObject = createJSON(message);
        return getSenderName(jsonObject);
    }

    private String getSenderName(JSONObject jsonObject) {
        return jsonObject.get("sender").toString();
    }

    private String getPurpose(JSONObject jsonObject) {
        return jsonObject.get("purpose").toString();
    }

    private String getMessage(JSONObject jsonObject) {
        return jsonObject.get("message").toString();
    }


}
