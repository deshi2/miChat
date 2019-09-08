package com.mirosh;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Can pack/unpack messages to JSON format, get some fields from JSON string and does other helpful things with strings.
 */
public class MessageController {

    /**
     * Checks if the message is a special "hello message", so server will process it in a special way.
     * @param message in JSON format.
     * @return true if this message is "hello message" else return false.
     */
    public Boolean isHelloMessage(String message) {
        return getPurpose(createJSON(message)).equals("hello");
    }

    /**
     * Creates new hello message for server.
     * @param senderName user name who sends it
     * @param recieverName those for who we sends.
     * @return JSONObject - our hello message
     */
    public String createHelloMessage(String senderName, String recieverName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender", senderName);
        jsonObject.put("purpose", "hello");
        jsonObject.put("message", "Добро пожаловать, " + recieverName + "!");

        return jsonObject.toString();
    }

    /**
     * Creates message in JSON format that will be sent to server.
     * @param senderName user name who sends it
     * @param message String for creating message
     * @return JSONObject - out message to be send
     */
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

    /**
     * Creates text from JSON like "senderName: message" to be shown in chat window.
     * @param message JSON message
     * @return String - our text to see
     */
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