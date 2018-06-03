package com.dms.datalayerapi.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Raja.p on 09-08-2016.
 */
public class GetPathMaker {
    HashMap<String, String> allValues = new HashMap<>();


    public GetPathMaker addElement(String key, String value) {
        allValues.put(key, value);
        return this;
    }

    public static GetPathMaker init() {
        return new GetPathMaker();
    }

    public void setAllUrlValues(HashMap<String, String> allValues) {
        this.allValues = allValues;
    }

    public String getPathForGetUrl(String baseURL) {
        StringBuilder url = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = allValues.entrySet();
        Iterator<Map.Entry<String, String>> iteratorValues = entrySet.iterator();
        int i = 0;
        url.append(baseURL);
        while (iteratorValues.hasNext()) {
            Map.Entry<String, String> mapEntry = iteratorValues.next();
            String keyValue = mapEntry.getKey();
            String value = mapEntry.getValue();
            if (i == 0) {
                url.append("?" + keyValue);
            } else {
                url.append("&" + keyValue);
            }
            try {
                url.append("=" + URLEncoder.encode(value, "UTF-8"));
            } catch (Exception e) {

            }
            i++;
        }
        return url.toString();
    }

    public String getAsPostPerms() {
        StringBuilder url = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = allValues.entrySet();
        Iterator<Map.Entry<String, String>> iteratorValues = entrySet.iterator();
        int i = 0;
        while (iteratorValues.hasNext()) {
            Map.Entry<String, String> mapEntry = iteratorValues.next();
            String keyValue = mapEntry.getKey();
            String value = mapEntry.getValue();
            if (i == 0) {
                url.append("" + keyValue);
            } else {
                url.append("&" + keyValue);
            }
            try {
                url.append("=" + URLEncoder.encode(value, "UTF-8"));
            } catch (Exception e) {
                url.append("=");
            }
            i++;
        }
        return url.toString();
    }
}
