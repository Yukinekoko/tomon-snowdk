package com.snowmeow.tomonsdk.module.base;

import com.google.gson.JsonObject;
import com.snowmeow.tomonsdk.Service;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.annotation.OnFullMatch;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.module.BaseModule;

import java.util.Set;

@Module("模块管理")
public class ModuleControl extends BaseModule {

    private Service service;

    private JsonObject moduleOn;

    private JsonObject moduleOff;

    private Set<Module> moduleSet;


    public void onCreate() {

    }

    public void onOpen() {

    }

    /* 查看当前群的模块情况 */
    @OnFullMatch("/mdlist")
    public void moduleList(Message message) {
        String response = "模块列表: \n";
        for(Module m : moduleSet)
            response += m.value() + "\n";
        service.replyMessage(message, response, null);
    }

    /* 已开启模块列表 */
    @OnFullMatch("/onlist")
    public void enableModuleList() {

    }

    @OnFullMatch("/offlist")
    /* 未开启模块列表 */
    public void disableModuleList() {

    }

    /* 开启某个模块 */
    public void enableModule() {

    }

    /* 关闭某个模块 */
    public void disableModule() {

    }


    public void setModuleOn(JsonObject moduleOn) {
        this.moduleOn = moduleOn;
    }

    public void setModuleOff(JsonObject moduleOff) {
        this.moduleOff = moduleOff;
    }

    public void setModuleSet(Set<Module> moduleSet) {
        this.moduleSet = moduleSet;
    }
}
