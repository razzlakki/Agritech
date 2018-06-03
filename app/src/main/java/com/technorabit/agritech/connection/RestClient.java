package com.technorabit.agritech.connection;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.dms.datalayerapi.network.HttpHeaderMaker;
import com.dms.datalayerapi.network.NetworkManager;
import com.dms.datalayerapi.network.exception.NetworkManagerException;

/**
 * Created by Raja.p on 09-08-2016.
 */
public class RestClient extends NetworkManager {

    protected RestClient(Context fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public HttpHeaderMaker getDefaultHeaders(HttpHeaderMaker headers) {
        headers = new HttpHeaderMaker();
        headers.addHeader("Accept", "application/json");
        headers.addHeader("Content-Type", "text/json");
        headers.addHeader("User-Agent", "Mozilla/6.0");
        return headers;
    }

    @Override
    protected void updateException(NetworkManagerException.Type connectionTimeoutException, Exception exceptionDetail) {
        switch (connectionTimeoutException) {
            case CONNECTION_TIMEOUT_EXCEPTION:
                break;
            case UNKNOWN_HOST_EXCEPTION:
                break;
            case IO_EXCEPTION:
                break;
            case UNKNOWN_EXCEPTION:
                break;
            case UNKNOWN_RESPONSE_EXCEPTION:
                break;
            case UNKNOWN_HEADERS_EXCEPTION:
                break;
        }
    }

    public static RestClient get(FragmentActivity fragmentActivity) {
        return new RestClient(fragmentActivity);
    }

    public static RestClient get(Context fragmentActivity) {
        return new RestClient(fragmentActivity);
    }

}
