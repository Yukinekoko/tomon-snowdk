package com.snowmeow.tomonsdk.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.module.base.ModuleControl;
import com.snowmeow.tomonsdk.util.JsonUtil;
import com.snowmeow.tomonsdk.util.LoggerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/** 插件管理
 * @author snowmeow
 * @date 2020/08/27
 * */
public class ModuleManage {

    private static final String MODULE_FILE_PATH = "module_config.json";
    private static final Logger logger = LogManager.getLogger(LoggerType.NETWORK);
/*    *//* fullMatch事件 *//*
    private Map<String, ModuleInstruction> instructionFullMatch;
    *//* regex事件 *//*
    private Map<String, ModuleInstruction> instructionRegex;
    *//* 前缀匹配 *//*
    private Map<String, ModuleInstruction> instructionPrefix;*/

    private Set<ModuleInstruction> moduleSet;

    private JsonObject moduleOn;

    private JsonObject moduleOff;

/*    private Set<Module> moduleSet;*/

    private ModuleControl moduleControl;


    /** 加载插件 */
    public void load(String token) {
        ModuleLoader moduleLoader = new ModuleLoader(token);

        this.moduleSet = moduleLoader.getModuleSet();

        loadModuleConfig();

        //处理模块控制器
        for(ModuleInstruction module : moduleSet) {
            if(module.getClassInstance().getClass().equals(ModuleControl.class)) {
                moduleControl = (ModuleControl) module.getClassInstance();
                break;
            }
        }
        moduleSwitchSynchronized();

    }

    public void loadModuleConfig() {
        JsonObject config = JsonUtil.readJsonFile(MODULE_FILE_PATH);
        if(config == null)
            config = createModuleConfigFile();
        moduleOn = config.getAsJsonObject("module_on");
        moduleOff = config.getAsJsonObject("module_off");
    }

    private JsonObject createModuleConfigFile() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("module_on", new JsonObject());
        jsonObject.add("module_off", new JsonObject());
        JsonUtil.writeJsonFile(MODULE_FILE_PATH, jsonObject);

        return jsonObject;
    }

    /** 暂 处理指令信息 */
    public void onMessage(Message message) {

        try{
            for(ModuleInstruction module : moduleSet) {
                if(!isEnableModule(module, message.getChannelId())) {
                    logger.info("忽略" +module.getModuleName());
                    continue;
                }

                Method currentMethod = null;

                Method fullMatch = module.getInstructionFullMatch().get(message.getContent());

                Method prefix = null;
                for(String key : module.getInstructionPrefix().keySet()) {
                    if(message.getContent().startsWith(key))
                        prefix = module.getInstructionPrefix().get(key);
                }

                Method regex = null;
                for(String key: module.getInstructionRegex().keySet()) {
                    if(message.getContent().matches(key))
                        regex = module.getInstructionRegex().get(key);
                }

                if(fullMatch != null)
                    currentMethod = fullMatch;
                else if (prefix != null)
                    currentMethod = prefix;
                else if(regex != null)
                    currentMethod = regex;

                if(currentMethod != null)
                    currentMethod.invoke(module.getClassInstance(), message);
            }

//            logger.info("Event: " + instruction.getObject().getClass().getName() + "." + instruction.getMethod().getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void moduleSwitchSynchronized() {

        if(moduleControl == null) {
            logger.error("Can not load control module");
            return;
        }

        moduleControl.setModuleOff(moduleOff);
        moduleControl.setModuleOn(moduleOn);
        moduleControl.setModuleSet(moduleSet);

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


}
