package com.snowmeow.tomonsdk.plugin;

import com.snowmeow.tomonsdk.Service;
import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.net.Api;
import okhttp3.WebSocket;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BasePlugin {

    WebSocket webSocket;
    Service service;

    public BasePlugin() {

    }

    public BasePlugin(WebSocket webSocket, Service service) {
        this.webSocket = webSocket;
        this.service = service;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @OnMessage(value = "/help")
    public void sayHello(Message message) {

        List<File> fileList = new ArrayList<File>();
        fileList.add(new File("D:\\NasDrive\\Programmer\\Project\\tomonsdk\\src\\main\\resources\\1.jpg"));

        String data = "还在写框架！根本没有功能哒！";
        try {
            service.replyMessage(message, data, fileList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
