package com.snowmeow.tomonsdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.snowmeow.tomonsdk.net.Api;
import com.snowmeow.tomonsdk.net.Route;
import com.snowmeow.tomonsdk.net.WebSocketClient;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Bot {

    WebSocketClient webSocketClient;
    WebSocket session;

    static final String WS_URL = "ws://gateway.tomon.co";

    public Bot(String token) {
        webSocketClient = new WebSocketClient(token);
        Event event = new Event(session, new Service(new Route(token)));
        webSocketClient.setEvent(event);
    }

    public Bot(String fullName, String password) {
        webSocketClient = new WebSocketClient(getToken(fullName, password));
    }

    public void start() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(WS_URL)
                .build();
        session = okHttpClient.newWebSocket(request, webSocketClient);

    }

    public void stop() {
        session.cancel();
    }

    private String getToken(String fullName, String password) {

        OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        String url = "https://beta.tomon.co/api/v1/auth/login";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("full_name", fullName);
        jsonObject.addProperty("password", password);
        String jsonString = new Gson().toJson(jsonObject);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);

        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            // TODO : 登录错误时的反馈
            e.printStackTrace();
            System.out.println("error!");
            return "error";
        }
    }
}
