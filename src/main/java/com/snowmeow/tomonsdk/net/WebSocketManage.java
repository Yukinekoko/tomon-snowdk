package com.snowmeow.tomonsdk.net;
import com.snowmeow.tomonsdk.ModuleManage;
import com.snowmeow.tomonsdk.util.LoggerType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class WebSocketManage {

    private static final Logger logger = LogManager.getLogger(LoggerType.NETWORK);
    private static final String WS_URL = "ws://gateway.tomon.co";

    private WebSocket session;
    private String token;
    private int reconnectCount;
    private ModuleManage moduleManage;

    public WebSocketManage(String token, ModuleManage moduleManage) {
        this.token = token;
        this.moduleManage = moduleManage;
        resetReconnectCount();
    }

    public void connect() {

        logger.info("Connect to server...");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(WS_URL)
                .build();
        session = okHttpClient.newWebSocket(request, new WebSocketClient(token, moduleManage) {
            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                reconnect();
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
                reconnect();
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                resetReconnectCount();
            }
        });
    }

    private void reconnect() {

        logger.warn("Websocket connection failed. Try connecting again after " + reconnectCount + " second");

        session.cancel();
        try {
            Thread.sleep(reconnectCount * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reconnectCount *= 2;
        if(reconnectCount > 3600) reconnectCount = 3600;
        connect();
    }

    private void resetReconnectCount() {
        reconnectCount = 3;
    }
}
