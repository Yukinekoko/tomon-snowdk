package com.snowmeow.tomonsdk.plugin;


import com.snowmeow.tomonsdk.Service;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.model.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Module(name = "测试模块", help = "帮助~")
public class SbImage extends BasePlugin {

    private Service service;

    public void onCreate() {
    }

    public void onOpen() {

    }

    @OnMessage(value = "/沙雕图")
    public void sendSbImage(Message message) {
        String basePath = "res/sb/";
        String[] fileNameList = new File(basePath).list();
        int randomIndex = new Random().nextInt(fileNameList.length);
        List<File> fileList = new ArrayList<File>();
        fileList.add(new File(basePath + fileNameList[randomIndex]));
        service.replyMessage(message, null, fileList);

    }
}
