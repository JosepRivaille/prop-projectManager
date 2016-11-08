package edu.upc.fib.prop.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    public static String hashData(String data) throws NoSuchAlgorithmException { MessageDigest md = MessageDigest.getInstance("SHA-256"); md.update(data.getBytes()); byte byteData[] = md.digest(); //Convert byte to hex format
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        } return sb.toString();
    }

    public static String buildJSONFromMap(Map<String, Float> data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static Map<String, Float> buildMapFromJSON(String json) {
        Map<String, Float> parsedData = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        for (Map.Entry<String, JsonElement> word : jsonObject.entrySet()) {
            parsedData.put(word.getKey(), Float.parseFloat(word.getValue().toString()));
        }
        return parsedData;
    }

}
