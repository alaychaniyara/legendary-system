package com.example.rubby.inclass10;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Rubby on 4/4/2018.
 */

public class JWTUtils {

    public static User decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT", JWTEncoded);
            Log.d("JWT", getJson(split[0]));
            Log.d("JWT", getJson(split[1]));
            Gson gson = new Gson();
            User u = gson.fromJson(getJson(split[1]), User.class);
            return u;
        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return null;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}