package com.snowmeow.tomonsdk.module.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.snowmeow.tomonsdk.Service;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.core.ModuleInstruction;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.module.BaseModule;
import com.snowmeow.tomonsdk.util.JsonUtil;

import java.util.Set;

@Module("模块管理")
public class ModuleControl extends BaseModule {

    private Service service;

    private JsonObject moduleOn;

    private JsonObject moduleOff;

    private Set<ModuleInstruction> moduleSet;


    public void onCreate() {

    }

    public void onOpen() {

    }

    /* 查看当前群的模块情况 */
    @OnMessage("/mdlist")
    public void moduleList(Message message) {
        String response = "模块列表: \n";
        for(ModuleInstruction m : moduleSet)
            response += m.getModuleName() + "\n";
        service.replyMessage(message, response, null);
    }

    /* 已开启模块列表 */
    @OnMessage("/onlist")
    public void enableModuleList(Message message) {
        String response = "已开启模块列表: \n";
        for(ModuleInstruction m : moduleSet) {
            if(isEnableModule(m, message.getChannelId()))
                response += m.getModuleName() + "\n";
        }
        service.replyMessage(message, response, null);
    }

    @OnMessage("/offlist")
    /* 未开启模块列表 */
    public void disableModuleList(Message message) {
        String response = "未开启模块列表: \n";
        for(ModuleInstruction m : moduleSet) {
            if(!isEnableModule(m, message.getChannelId()))
                response += m.getModuleName() + "\n";
        }
        service.replyMessage(message, response, null);
    }

    /* 开启某个模块 */
    @OnMessage(value = "/enable", type = OnMessage.Type.PREFIX)
    public void enableModule(Message message) {
        String moduleName = message.getContent().substring(7).trim();
        try {
            for(ModuleInstruction m : moduleSet) {
                if (m.getModuleName().equals(moduleName)) {
                    if(isEnableModule(m, message.getChannelId())) {
                        service.replyMessage(message, "该模块已在本频道开启！", null);
                    }
                    else {
                        if (m.isDefaultEnable()) {
                            switchModule(m, message.getChannelId(), moduleOff, false);
                        }
                        else {
                            switchModule(m, message.getChannelId(), moduleOn, true);
                        }
                        updateConfigFile();
                        service.replyMessage(message, "已开启该模块！", null);
                    }
                    return;
                }
            }
            service.replyMessage(message, "不存在该模块，请检查输入！", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 关闭某个模块 */
    @OnMessage(value = "/disable", type = OnMessage.Type.PREFIX)
    public void disableModule(Message message) {
        String moduleName = message.getContent().substring(8).trim();
        for(ModuleInstruction m : moduleSet) {
            if (m.getModuleName().equals(moduleName)) {
                if(!isEnableModule(m, message.getChannelId())) {
                    service.replyMessage(message, "该模块已在本频道关闭！", null);
                }
                else {
                    try {
                        if (m.isDefaultEnable()) {
                            switchModule(m, message.getChannelId(), moduleOff, true);
                        }
                        else {
                            switchModule(m, message.getChannelId(), moduleOn, false);
                        }
                        updateConfigFile();
                        service.replyMessage(message, "已关闭该模块！", null);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }
        service.replyMessage(message, "不存在该模块，请检查输入！", null);

    }


    /* 群是否开启指定模块 */
    private boolean isEnableModule(ModuleInstruction module, String channelId) {
        if(module.isDefaultEnable()) {
            JsonArray value = moduleOff.getAsJsonArray(module.getModuleName());
            return !(value != null && value.contains(new JsonPrimitive(channelId)));
        }
        else {
            JsonArray value = moduleOn.getAsJsonArray(module.getModuleName());
            return value != null && value.contains(new JsonPrimitive(channelId));
        }
    }

    private void updateConfigFile() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("module_on", moduleOn);
        jsonObject.add("module_off", moduleOff);
        JsonUtil.writeJsonFile("module_config.json", jsonObject);
    }

    private void switchModule(ModuleInstruction module, String channelId, JsonObject type, boolean add) {

        JsonArray list = type.getAsJsonArray(module.getModuleName());
        if(list == null) list = new JsonArray();
        if(add) {
            list.add(channelId);
        }
        else {
            list.remove(new JsonPrimitive(channelId));
        }
        type.add(module.getModuleName(), list);

    }

    public void setModuleOn(JsonObject moduleOn) {
        this.moduleOn = moduleOn;
    }

    public void setModuleOff(JsonObject moduleOff) {
        this.moduleOff = moduleOff;
    }

    public void setModuleSet(Set<ModuleInstruction> moduleSet) {
        this.moduleSet = moduleSet;
    }

}
