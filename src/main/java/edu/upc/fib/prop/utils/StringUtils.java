package edu.upc.fib.prop.utils;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class StringUtils {

    public static String hashData(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        byte byteData[] = md.digest();

        //Convert byte to hex format
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        } return sb.toString();
    }

    public static String buildJSON(Map<String, Float> data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
