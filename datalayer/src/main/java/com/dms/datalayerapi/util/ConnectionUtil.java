package com.dms.datalayerapi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

/**
 * Created by Raja.p on 12-07-2016.
 */
public class ConnectionUtil {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connec.getActiveNetworkInfo();
        return (activeNetwork != null
                && activeNetwork.isConnectedOrConnecting());
    }

    public static boolean isNetworkAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("http://www.google.com");
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }

    }

}
