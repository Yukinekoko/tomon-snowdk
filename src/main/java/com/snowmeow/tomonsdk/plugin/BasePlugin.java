package com.snowmeow.tomonsdk.plugin;

import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.net.API;
import okhttp3.WebSocket;

public class BasePlugin {

    WebSocket webSocket;
    API api;

    public BasePlugin() {

    }

    public BasePlugin(WebSocket webSocket, API api) {
        this.webSocket = webSocket;
        this.api = api;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public void setApi(API api) {
        this.api = api;
    }

    @OnMessage(value = "/hello")
    public void sayHello(String channelId) {
        String url = "/channels/" + channelId + "/messages";
        String data = "{\"content\":\"hello!\"}";
        api.post(url, data);
    }
}
