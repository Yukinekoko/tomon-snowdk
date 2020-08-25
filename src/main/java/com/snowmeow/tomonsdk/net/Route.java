package com.snowmeow.tomonsdk.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.snowmeow.tomonsdk.util.RequestMethod;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author snowmeow
 * @date 2020/08/23
 * */
public class Route {

    final static String BASE_URL = "https://beta.tomon.co/api/v1";
    final static int TYPE_JSON = 0;
    final static int TYPE_MULTIPART = 1;
    private String token;
    private OkHttpClient okHttpClient;

    public Route(String token) {
        this.token = "Bearer " + token;
        this.okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public void request(RequestMethod method, String url, String data, List<File> fileList, int type, boolean useToken) {

        String mediaType;
        if (type == TYPE_JSON) {
            mediaType = "application/json; charset=utf-8";
        }
        else if (type == TYPE_MULTIPART) {
            mediaType = "multipart/form-data; charset=utf-8";
        }
        else return;

        Request.Builder requestBuilder = new Request.Builder().url(BASE_URL + url);
        if(useToken) requestBuilder = requestBuilder.addHeader("Authorization", token);

        if(method == RequestMethod.GET) requestBuilder = requestBuilder.get();
        //else if(method == RequestMethod.POST) requestBuilder = requestBuilder.


    }

    public void get(String url, Map<String, Object> query, boolean useToken) {

    }

    public void post(String url, String data, boolean useToken, Callback callback) {

        Request.Builder requestBuilder = new Request.Builder().url(BASE_URL + url);
        if(useToken) requestBuilder = requestBuilder.addHeader("Authorization", token);
        requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data));
        Request request = requestBuilder.build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);

    }

    public void post(String url, MultipartBody data, boolean useToken, Callback callback) {

        Request.Builder requestBuilder = new Request.Builder().url(BASE_URL + url);
        if(useToken) requestBuilder = requestBuilder.addHeader("Authorization", token);
        requestBuilder.post(data);
        Request request = requestBuilder.build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(callback);
    }

    public void post(String url, Map<String, Object> data, boolean useToken, Callback callback) {
        post(url, new Gson().toJson(data), useToken, callback);
    }


    public void post(String url, JsonObject data, boolean useToken, Callback callback) {
        post(url, new Gson().toJson(data), useToken, callback);
    }
}
