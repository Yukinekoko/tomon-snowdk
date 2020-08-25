package com.snowmeow.tomonsdk;

import com.google.gson.JsonObject;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.net.Route;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/** 一些服务
 * @author snowmeow
 * @date 2020/08/24
 * */
public class Service {

    Route route;

    public Service(Route route) {
        this.route = route;
    }

    /** 回复消息
     *
     * */
    public void replyMessage(Message message, String content, List<File> fileList) {

        String url = "/channels/" + message.getChannelId() + "/messages";

        JsonObject data = new JsonObject();
        data.addProperty("content", content);
        if(fileList != null) {
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for(int i = 0; i < fileList.size(); i++)
                multipartBodyBuilder.addFormDataPart("file" + i, fileList.get(i).getName(),
                        MultipartBody.create(MediaType.parse("application/octet-stream"), fileList.get(i)));
            multipartBodyBuilder.addFormDataPart("payload_json", data.toString());
            route.post(url, multipartBodyBuilder.build(), true, new Callback() {
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                    System.out.println("fail");
                }

                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println("success");
                    System.out.println(response.body());
                }
            });
        }
        else {
            route.post(url, data, true, new Callback() {
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                }
            });
        }
    }
}
