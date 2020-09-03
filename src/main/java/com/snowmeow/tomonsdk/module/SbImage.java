package com.snowmeow.tomonsdk.module;


import com.snowmeow.tomonsdk.Service;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.annotation.OnFullMatch;
import com.snowmeow.tomonsdk.model.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Module(value = "沙雕图", help = "帮助~")
public class SbImage extends BaseModule {

    private Service service;

    public void onCreate() {
    }

    public void onOpen() {

    }

    @OnFullMatch(value = "/沙雕图")
    public void sendSbImage(Message message) {
        String basePath = "res/sb/";
        String[] fileNameList = new File(basePath).list();
        int randomIndex = new Random().nextInt(fileNameList.length);
        List<File> fileList = new ArrayList<File>();
        fileList.add(new File(basePath + fileNameList[randomIndex]));
        service.replyMessage(message, null, fileList);

    }
}
