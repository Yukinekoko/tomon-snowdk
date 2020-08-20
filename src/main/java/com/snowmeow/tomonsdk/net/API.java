package com.snowmeow.tomonsdk.net;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class API {

    String token;

    OkHttpClient okHttpClient;

    static final String BASE_URL = "https://beta.tomon.co/api/v1";

    public API(String token) {
        this.token = token;
        this.okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public void post(String url, String data) {

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), data);

        final Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(requestBody)
                .header("Authorization","Bearer " + token)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {

            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("http失败");
            }

            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("发送成功！");
            }
        });
    }

}
