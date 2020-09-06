package com.snowmeow.tomonsdk.module;

import com.snowmeow.tomonsdk.Service;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.model.Message;

@Module(value = "测试模块", help = "帮助~")
public class FirstModule extends BaseModule {

    private Service service;

    @Override
    public void onCreate() {

    }

    @Override
    public void onOpen() {

    }

    @OnMessage(value = "/help")
    public void sayHello(Message message) {


        String data = "/沙雕图 来一张沙雕图 \n 现在只有这个弱智功能呢！";
        try {
            service.replyMessage(message, data, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnMessage(value = "/yukineko", help = "~")
    public void neko(Message message) {
        service.replyMessage(message, "/yukineko", null);
    }

}
