package com.dms.datalayerapi.util;

import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raja on 02/06/18.
 */

public class Util {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public static boolean isValidJson(String configResponse) {
        if (configResponse != null) {
            try {
                new JSONObject(configResponse);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
