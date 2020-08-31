package com.snowmeow.tomonsdk.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;

/** Json工具
 * @author snowmeow
 * @date 2020/08/28
 * */
public class JsonUtil {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /** 读取Json文件
     * @param filePath 文件完整路径
     * @return 解析出来的JsonObject;若文件不存在则返回 null
     * */
    public static JsonObject readJsonFile(String filePath) {
        File file = new File(filePath);
        if(!file.exists()) return null;
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(file));
            JsonObject data = JsonParser.parseReader(jsonReader).getAsJsonObject();
            jsonReader.close();
            return data;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JsonSyntaxException e) {
            // 格式错误
            e.printStackTrace();
        }
        catch (IllegalStateException e) {
            //非json结构
            e.printStackTrace();
        }
        return null;
    }

    /** 将Json写入文件（覆盖）
     * @param filePath 文件完整路径
     * @param jsonObject 写入的JsonObject
     * */
    public static void writeJsonFile(String filePath, JsonObject jsonObject){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(GSON.toJson(jsonObject));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
