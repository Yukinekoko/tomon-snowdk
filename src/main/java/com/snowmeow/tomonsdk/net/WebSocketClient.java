package com.snowmeow.tomonsdk.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snowmeow.tomonsdk.Event;
import com.snowmeow.tomonsdk.PluginLoader;
import com.snowmeow.tomonsdk.PluginManage;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.util.JsonUtil;
import com.snowmeow.tomonsdk.util.LoggerType;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class WebSocketClient extends WebSocketListener {

    private static final Logger logger = LogManager.getLogger(LoggerType.WEBSOCKET);

    private boolean isOpen;
    private String session_id;
    private int heartbeat_interval;
    private String token;
    private PluginManage pluginManage;

    public WebSocketClient(String token) {
        super();
        this.token = token;
        pluginManage = new PluginManage();
        pluginManage.load(token);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        logger.warn("WebSocket connection closed");

        isOpen = !isOpen;
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
        logger.warn("WebSocket connection closing");

        isOpen = !isOpen;
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        logger.error("WebSocket connection failed");
        logger.error(t);
        isOpen = !isOpen;
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
        Gson gson = JsonUtil.GSON;
        try {
            JsonObject payload = JsonParser.parseString(text).getAsJsonObject();
            int op = payload.get("op").getAsInt();
            switch (op) {
                case 0:
                    // 接收业务事件
                    if(payload.get("e").getAsString().equals("MESSAGE_CREATE")) {
                        // logger.debug(gson.toJson(payload.get("d").getAsJsonObject()));
                        Message message = gson.fromJson(gson.toJson(payload.get("d").getAsJsonObject()), Message.class);
                        if(message.getAuthor() != null)
                            logger.info("Recv: MESSAGE_CREATE."
                                    + message.getChannelId()
                                    + "."
                                    + message.getAuthor().getName()
                                    + " > "
                                    + message.getContent());
                        else
                            logger.info("Recv: MESSAGE_CREATE." + message.getChannelId()
                                    + ".SYSTEM_MESSAGE" + " > " + message.getContent());

                        // TODO : 忽略自己的信息
                        if(message.getAuthor().getUsername().equals("yukidevbot")) return;
                        pluginManage.sendMessage(message);
                    }
                    else {
                        logger.info("Recv: DISPATCH");
                    }
                    break;
                case 1:
                    logger.warn("Recv: HEARTBEAT");
                    break;
                case 2:
                    // 接收bot的账号信息
                    logger.info("Recv: IDENTIFY");
                    break;
                case 3:
                    // 接收服务器发来的连接初始化信息
                    logger.info("Recv: HELLO");
                    this.session_id = payload.getAsJsonObject("d").get("session_id").getAsString();
                    this.heartbeat_interval = payload.getAsJsonObject("d").get("heartbeat_interval").getAsInt();
                    JsonObject identify = new JsonObject();
                    JsonObject identify_d = new JsonObject();
                    identify_d.addProperty("token", token);
                    identify.addProperty("op", 2);
                    identify.add("d", identify_d);
                    String identifyString =gson.toJson(identify);

                    logger.info("Send: Authentication token");
                    webSocket.send(identifyString); //鉴权 发送token

                    sendHeartbeat(webSocket);   //定时发送心跳包
                    break;
                case 4:
                    // 接收心跳包
                    logger.debug("Recv: Response heartbeat");
                    break;
                case 5:
                    // 接收语音服务（暂未开放）
                    logger.warn("Recv: VOICE_STASE_UPDATE");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }

    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);

        logger.info("Successfully connected to the server!");

        isOpen = true;

    }

    private void sendHeartbeat(final WebSocket webSocket) {

        logger.debug("Send: Heartbeat");

        final int delay = this.heartbeat_interval;
        final String dataString = "{\"op\":1}";
        new Thread(new Runnable() {
            public void run() {
                while(isOpen) {
                    webSocket.send(dataString);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
