package ru.omg_lol.stackover.api.facades.common;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.omg_lol.stackover.Constants;
import ru.omg_lol.stackover.api.response.ApiResponse;
import timber.log.Timber;

public class Facade {
    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 15;

    private static final String API_LOG_TAG =  Constants.LOG_TAG + "_API";
    public static final String API_NAMESPACE =  "api/";

    private OkHttpClient mClient;

    public Facade() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

        mClient = clientBuilder.build();
    }

    protected <T extends ApiResponse> T post(Class<T> classOfT, String url, RequestBody postBody) throws ApiException {
        return request(classOfT, Method.POST, url, postBody);
    }

    protected <T extends ApiResponse> T get(Class<T> classOfT, String url) throws ApiException {
        return request(classOfT, Method.GET, url);
    }

    protected <T extends ApiResponse> T put(Class<T> classOfT, String url, RequestBody postBody) throws ApiException {
        return request(classOfT, Method.PUT, url, postBody);
    }

    protected <T extends ApiResponse> T delete(Class<T> classOfT, String url) throws ApiException {
        return request(classOfT, Method.DELETE, url);
    }

    private <T extends ApiResponse> T request(Class<T> classOfT, Method method, String url)  throws ApiException {
        return request(classOfT, method, url, null);
    }

    private <T extends ApiResponse> T request(Class<T> classOfT, Method method, String url, RequestBody requestBody)  throws ApiException {
        String targetUrl = Constants.API_ROOT + "/" + url;
        Log.e("URL", targetUrl);
        Request.Builder requestBuilder = new Request.Builder()
                .addHeader("Content-type", "application/json")
                .url(targetUrl);

        switch (method) {
            case POST:
                requestBuilder.post(requestBody);
                break;
            case GET:
                requestBuilder.get();
                break;
            case PUT:
                requestBuilder.put(requestBody);
                break;
            case DELETE:
                requestBuilder.delete();
                break;
        }

        Request request = requestBuilder.build();

        String body;
        int resultCode;

        try {
            Response response = mClient.newCall(request).execute();
            body = response.body().string();
            resultCode = response.code();
            Timber.d(body);
            Timber.d(resultCode + "");
        } catch (IOException e) {
            throw new ApiException(ApiExceptionType.NETWORK);
        }

        T responseData;

        try {
            responseData = gson().fromJson(body, classOfT);

            if (responseData == null) {
                throw new JsonSyntaxException("Empty response");
            } else {
                responseData.resultCode = resultCode;
            }
        } catch (JsonSyntaxException exception) {
            throw new ApiException(ApiExceptionType.PARSER);
        }

        if (responseData.isError()) {
            throw new ApiException(responseData.error);
        }

        return responseData;
    }

    private static Gson gson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                long timestamp = json.getAsJsonPrimitive().getAsLong();
                return timestamp == 0 ? null : new Date(timestamp * 1000L);
            }
        });

        return builder.create();
    }

    protected enum Method {
        POST,
        GET,
        PUT,
        DELETE
    }
}