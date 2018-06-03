package com.dms.datalayerapi.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Raja.p on 15-12-2015.
 */
public class ParseUtils {

    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static <T> T getParseObj(String response, Class<T> classOfT) {
        if (response == null || !Util.isValidJson(response))
            return null;
        return gson.fromJson(response, classOfT);
    }

    public static ArrayList<?> getAllArrayElements(String response, Class<? extends Object> classType) {
        ArrayList<Object> allElements = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                allElements.add(getParseObj(jsonArray.getJSONObject(i).toString(), classType));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allElements;
    }


    public static String getJsonString(Object loginreq) {
        return getGson().toJson(loginreq);
    }
}
