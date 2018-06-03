package com.dms.datalayerapi.network;

import android.support.annotation.NonNull;

import com.dms.datalayerapi.network.constants.NetworkConstants;
import com.dms.datalayerapi.network.event.ClientUpdateListener;
import com.dms.datalayerapi.network.exception.InvalidClientException;
import com.dms.datalayerapi.network.exception.NetworkManagerException;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by Raja.p on 20-05-2016.
 */
public class ClientProperties {

    private OkHttpClient okHttpClient;
    private TimeUnit defaultTimeConstant = TimeUnit.MINUTES;
    private ClientUpdateListener onClientUpdateListener;

    static ClientProperties getInstance() {
        return new ClientProperties();
    }

    private ClientProperties() {
        okHttpClient = new OkHttpClient();
        setDefaultSettings();
    }

    private void setDefaultSettings() {
        okHttpClient.setConnectTimeout(NetworkConstants.CONNECTION_TIMEOUT, defaultTimeConstant);
        okHttpClient.setReadTimeout(NetworkConstants.READ_TIMEOUT, defaultTimeConstant);
    }

    /**
     * Send in min
     *
     * @param conTimeOut
     * @param readTimeOut
     */
    public void setTimeOut(int conTimeOut, int readTimeOut) {
        okHttpClient.setConnectTimeout(conTimeOut, defaultTimeConstant);
        okHttpClient.setReadTimeout(readTimeOut, defaultTimeConstant);
        try {
            updateClient(okHttpClient);
        } catch (InvalidClientException e) {
            e.printStackTrace();
        }
    }

    public OkHttpClient getClient() {
        return this.okHttpClient;
    }

    /**
     * It throws an InvalidClientException
     *
     * @param okHttpClient
     * @throws InvalidClientException
     */
    public void updateClient(@NonNull OkHttpClient okHttpClient) throws InvalidClientException {
        if (okHttpClient != null) {
            this.okHttpClient = okHttpClient;
            if (this.onClientUpdateListener != null)
                this.onClientUpdateListener.onClientUpdated();
        } else {
            throw new InvalidClientException();
        }
    }

    public void setOnClientUpdateListener(ClientUpdateListener onClientUpdateListener) {
        this.onClientUpdateListener = onClientUpdateListener;
    }
}
