package com.snowmeow.tomonsdk;

import com.snowmeow.tomonsdk.core.ModuleManage;
import com.snowmeow.tomonsdk.net.WebSocketManage;
import com.snowmeow.tomonsdk.util.LoggerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bot {

    private static final Logger logger = LogManager.getLogger(LoggerType.MAIN);

    //private static final String WS_URL = "ws://gateway.tomon.co";

    private WebSocketManage webSocketManage;

    private ModuleManage moduleManage;

    public Bot(String token) {
        moduleManage = new ModuleManage();
        moduleManage.load(token);
        webSocketManage = new WebSocketManage(token, moduleManage);
    }


    public void start() {
        webSocketManage.connect();
    }

    public void stop() {
        // TODO
    }


/*
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
    }
*/
}
