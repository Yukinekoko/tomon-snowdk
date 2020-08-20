package com.snowmeow.tomonsdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.snowmeow.tomonsdk.net.WebSocketClient;
import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Tomon {

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjE2MTE4NDYxNjExMDU2NzQyNCIsIm5vbmNlIjoiUDhxUzNmNkZYNlpnVFl2bzlyc1Zldz09IiwiaWF0IjoxNTk3Nzc2NjA3fQ.NDOY-JTtOGK20OKEoDZDUv0GRfHnnIm0hKOOaRL0wrs";

        Bot bot = new Bot(token);
        bot.start();

    }

}
