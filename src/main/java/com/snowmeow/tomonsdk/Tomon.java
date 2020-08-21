package com.snowmeow.tomonsdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.snowmeow.tomonsdk.net.WebSocketClient;
import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Tomon {

    public static void main(String[] args) {
        String token = "token";

        Bot bot = new Bot(token);
        bot.start();

    }

}
