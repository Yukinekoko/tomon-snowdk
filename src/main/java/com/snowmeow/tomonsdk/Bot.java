package com.snowmeow.tomonsdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.snowmeow.tomonsdk.net.Api;
import com.snowmeow.tomonsdk.net.Route;
import com.snowmeow.tomonsdk.net.WebSocketClient;
import com.snowmeow.tomonsdk.net.WebSocketManage;
import com.snowmeow.tomonsdk.util.LoggerType;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Bot {

    private static final Logger logger = LogManager.getLogger(LoggerType.MAIN);

    WebSocketManage webSocketManage;

    static final String WS_URL = "ws://gateway.tomon.co";

    public Bot(String token) {
        webSocketManage = new WebSocketManage(token);
    }

/*    public Bot(String fullName, String password) {
        webSocketManage = new WebSocketManage(getToken(fullName, password));
    }*/

    public void start() {
        webSocketManage.connect();
    }

    public void stop() {
        // TODO
    }

/*    *//** 使用用户名和密码登录
     * @param fullName 全名
     * @param password 密码
     * @return 账号token
     * *//*
    private String getToken(String fullName, String password) {

        logger.info("Get account Token...");

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
            e.printStackTrace();
            System.out.println("error!");
            return "error";
        }
    }*/
}
