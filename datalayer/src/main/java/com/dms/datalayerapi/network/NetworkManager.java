package com.dms.datalayerapi.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.dms.datalayerapi.network.event.ClientUpdateListener;
import com.dms.datalayerapi.network.event.NetworkConErrorListener;
import com.dms.datalayerapi.network.exception.NetworkManagerException;
import com.dms.datalayerapi.network.executors.CommonPoolExecutor;
import com.dms.datalayerapi.util.ConnectionUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Raja.p on 20-05-2016.
 */
public abstract class NetworkManager {

    private static final String CACHE_STORE = "cache_store";
    private SharedPreferences sharedPrefrence;
    protected Context context;
    private ClientProperties clientProperties;
    protected MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private HttpHeaderMaker headers;
    protected Gson gson;
    private boolean diskCacheEnable;

    public void updateMediaType(MediaType mediaType) {
        this.JSON = mediaType;
    }

    protected NetworkManager(Context fragmentActivity) {
        this.context = fragmentActivity;
        clientProperties = ClientProperties.getInstance();
        gson = new Gson();
        diskCacheEnable = false;
        if (context != null)
            sharedPrefrence = context.getSharedPreferences(CACHE_STORE, Context.MODE_PRIVATE);
        headers = new HttpHeaderMaker();
        headers = getDefaultHeaders(headers);

        clientProperties.setOnClientUpdateListener(new ClientUpdateListener() {

            @Override
            public void onClientUpdated() {
                updateInputs();
            }
        });
    }

    public void setClientProperties(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        updateInputs();
    }

    public abstract HttpHeaderMaker getDefaultHeaders(HttpHeaderMaker headers);

    public HttpHeaderMaker getHeaders() {
        return headers;
    }

    public NetworkManager addHeaders(HttpHeaderMaker headers) {
        this.headers = headers;
        return this;
    }

    public NetworkManager addOrUpdateHeaders(String key, String value) {
        this.headers.headers.put(key, value);
        return this;
    }

    public NetworkManager diskCacheEnable(boolean diskCacheEnable) {
        this.diskCacheEnable = diskCacheEnable;
        return this;
    }

    private void updateInputs() {
        clientProperties.getClient().interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return onOnIntercept(chain);
            }
        });
    }

    protected Response onOnIntercept(Interceptor.Chain chain) throws IOException {
        try {
            Response response = chain.proceed(chain.request());
            String content = convertResponseToString(response);
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), content)).build();
        } catch (SocketTimeoutException exception) {
            updateException(NetworkManagerException.Type.CONNECTION_TIMEOUT_EXCEPTION, exception);
        } catch (UnknownHostException exception) {
            updateException(NetworkManagerException.Type.UNKNOWN_HOST_EXCEPTION, exception);
        } catch (IOException exception) {
            updateException(NetworkManagerException.Type.IO_EXCEPTION, exception);
        } catch (Exception exception) {
            updateException(NetworkManagerException.Type.UNKNOWN_EXCEPTION, exception);
        }
        return chain.proceed(chain.request());
    }

    protected abstract void updateException(NetworkManagerException.Type connectionTimeoutException, Exception exceptionDetail);


    public String convertResponseToString(Response response) {
        String responseString = "";
        try {
            responseString = response.body().string();
        } catch (IOException e) {
            updateException(NetworkManagerException.Type.UNKNOWN_RESPONSE_EXCEPTION, e);
        }
        return responseString;
    }


    public ClientProperties getClientProperties() {
        return clientProperties;
    }


    public String doGet(String url) {
        if (url != null)
            Log.e("URL get", url);

        if (diskCacheEnable)
            return cacheCheck(url, null, Http.GET);
        else
            return doBaseCall(url, null, Http.GET);
    }

    private String cacheCheck(final String url, final String body, final Http type) {
        String sharedResult = sharedPrefrence.getString(url, null);
        if (sharedResult == null) {
            String result = doBaseCall(url, body, type);
            SharedPreferences.Editor editor = sharedPrefrence.edit();
            editor.putString(url, result);
            editor.apply();
            return result;
        } else {
            CommonPoolExecutor.get().startDownload(new Runnable() {
                @Override
                public void run() {
                    String result = doBaseCall(url, body, type);
                    if (result != null && (!result.equals("null"))) {
                        SharedPreferences.Editor editor = sharedPrefrence.edit();
                        editor.putString(url, result);
                        editor.apply();
                    }
                }
            });
            return sharedResult;
        }

    }

    public String doPost(String url, String body) {
        if (diskCacheEnable)
            return cacheCheck(url, body, Http.POST);
        else
            return doBaseCall(url, body, Http.POST);
    }


    public String doPostCall(String url, String body) {
        return doPostWithByteCall(url, body.getBytes());
    }


    public String doPostWithByteCall(String url, byte[] body) {
        if (url != null)
            Log.e("URL", url);
        String responseString = null;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url)
                .method("POST", RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), body));

        if (headers != null) {
            try {
                headers.build(requestBuilder);
            } catch (Exception e) {
                updateException(NetworkManagerException.Type.UNKNOWN_HEADERS_EXCEPTION, e);
            }
        }
        Response response = null;
        try {
            response = clientProperties.getClient().newCall(requestBuilder.build()).execute();
            responseString = response.body().string();
            Log.e("Resp : ", responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    private String doBaseCall(String url, String body, Http type) {
        String stringResponse = null;
        try {
            Request.Builder absRequest = new Request.Builder()
                    .url(url);
            if (type == Http.POST)
                absRequest.post(RequestBody.create(JSON, body != null ? body : ""));
            if (headers != null) {
                try {
                    headers.build(absRequest);
                } catch (Exception e) {
                    updateException(NetworkManagerException.Type.UNKNOWN_HEADERS_EXCEPTION, e);
                }
            }
            Request request = absRequest.build();
            Response response = null;
            try {
                response = clientProperties.getClient().newCall(request).execute();
            } catch (IOException e) {
                updateException(NetworkManagerException.Type.IO_EXCEPTION, e);
            }
            try {
                stringResponse = response.body().string();
                Log.e("response", stringResponse);
            } catch (IOException e) {
                updateException(NetworkManagerException.Type.UNKNOWN_RESPONSE_EXCEPTION, e);
            }
        } catch (Exception ex) {
            updateException(NetworkManagerException.Type.UNKNOWN_EXCEPTION, ex);
        }
        return stringResponse;
    }

    public class NetworkTask<Progress, Result> extends AsyncTask<String, Progress, Result> {

        private NetworkConErrorListener networkConErrorListener;
        private Class<Result> type;
        private Http callType;
        private boolean isConnectionAvailable;


        public NetworkTask(Class<Result> result, Http callType) {
            type = result;
            this.callType = callType;
        }

        public NetworkTask(Class<Result> result, Http callType, NetworkConErrorListener networkConErrorListener) {
            type = result;
            this.callType = callType;
            this.networkConErrorListener = networkConErrorListener;
        }

        public NetworkTask setNetworkConErrorListener(NetworkConErrorListener networkConErrorListener) {
            this.networkConErrorListener = networkConErrorListener;
            return this;
        }

        public void onNetworkNotAvailable() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isConnectionAvailable = ConnectionUtil.checkInternetConnection(context);
            if (!isConnectionAvailable) {
                onNetworkNotAvailable();
                if (networkConErrorListener != null) {
                    networkConErrorListener.onNetworkConnectionErrorListener();
                }
            }
        }

        @Override
        protected Result doInBackground(String... params) {
            Result classType = null;
            if (isConnectionAvailable) {
                params = doMoreManipulationBefore(params);
                String response = "";
                switch (callType) {
                    case GET:
                        response = doGet(params[0]);
                        break;
                    case POST:
                        response = doPost(params[0], params[1]);
                        break;
                }
                try {
                    classType = gson.fromJson(response, type);
                } catch (JsonSyntaxException e) {
                    return null;
                } catch (Exception e) {
                    return null;
                }
                return manipulateMoreOnBackGround(classType);
            } else return null;

        }

        protected String[] doMoreManipulationBefore(String... params) {
            return params;
        }

        protected Result manipulateMoreOnBackGround(Result classType) {
            return classType;
        }
    }

}
