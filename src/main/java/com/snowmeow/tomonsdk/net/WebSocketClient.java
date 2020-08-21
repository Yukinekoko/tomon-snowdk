package com.snowmeow.tomonsdk.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snowmeow.tomonsdk.Event;
import com.snowmeow.tomonsdk.model.Message;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class WebSocketClient extends WebSocketListener {

    private boolean isOpen;
    private String session_id;
    private int heartbeat_interval;
    private String token;
    private Event event;

    public WebSocketClient(String token) {
        super();
        this.token = token;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        System.out.println("关闭连接");
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
        System.out.println("服务端发送close包");
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        System.out.println("WebSocket连接错误！");
        System.out.println(t);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        /*
        * DISPATCH : 0
        * HEARTBEAT : 1
        * IDENTIFY : 2
        * HELLO : 3
        * HEARTBEAT_ACK : 4
        * VOICE_STASE_UPDATE : 5
        * */
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            JsonObject payload = JsonParser.parseString(text).getAsJsonObject();
            int op = payload.get("op").getAsInt();
            switch (op) {
                case 0:
                    // 接收业务事件
                    System.out.println("on: DISPATCH");
                    if(payload.get("e").getAsString().equals("MESSAGE_CREATE")) {
                        Message message = gson.fromJson(gson.toJson(payload.get("d").getAsJsonObject()), Message.class);
                        String content = message.getContent();
                        String channelId=  message.getChannelId();
                        event.sendMessage(content, channelId);
                    }
                    break;
                case 1:
                    System.out.println("on: HEARTBEAT");
                    break;
                case 2:
                    // 接收bot的账号信息
                    break;
                case 3:
                    // 接收服务器发来的连接初始化信息
                    this.session_id = payload.getAsJsonObject("d").get("session_id").getAsString();
                    this.heartbeat_interval = payload.getAsJsonObject("d").get("heartbeat_interval").getAsInt();
                    JsonObject identify = new JsonObject();
                    JsonObject identify_d = new JsonObject();
                    identify_d.addProperty("token", token);
                    identify.addProperty("op", 2);
                    identify.add("d", identify_d);
                    String identifyString =gson.toJson(identify);
                    webSocket.send(identifyString); //鉴权 发送token
                    sendHeartbeat(webSocket);   //定时发送心跳包
                    break;
                case 4:
                    // 接收心跳包
                    System.out.println("pong!");
                    break;
                case 5:
                    // 接收语音服务（暂未开放）
                    System.out.println("on: VOICE_STASE_UPDATE");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }

    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        this.isOpen = true;

    }

    private void sendHeartbeat(final WebSocket webSocket) {
        final int delay = this.heartbeat_interval;
        final String dataString = "{\"op\":1}";
        new Thread(new Runnable() {
            public void run() {
                while(isOpen) {
                    webSocket.send(dataString);
                    System.out.println("send heartbeat!");
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("sleep error");
                    }
                }
            }
        }).start();
    }
}
