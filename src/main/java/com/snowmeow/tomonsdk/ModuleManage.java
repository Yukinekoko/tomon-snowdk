package com.snowmeow.tomonsdk;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.module.base.ModuleControl;
import com.snowmeow.tomonsdk.util.JsonUtil;
import com.snowmeow.tomonsdk.util.LoggerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/** 插件管理
 * @author snowmeow
 * @date 2020/08/27
 * */
public class ModuleManage {

    private static final String MODULE_FILE_PATH = "module_config.json";
    private static final Logger logger = LogManager.getLogger(LoggerType.NETWORK);
    /* fullMatch事件 */
    private Map<String, Instruction> fullMatchCommand;
    /* regex事件 */
    private Map<String, Instruction> regex;
    /* 前缀匹配 */
    //
    private JsonObject moduleOn;

    private JsonObject moduleOff;

    private Set<Module> moduleSet;

    private ModuleControl moduleControl;


    /** 加载插件 */
    public void load(String token) {
        ModuleLoader moduleLoader = new ModuleLoader(token);
        this.fullMatchCommand = moduleLoader.fullMatchLoader();
        this.moduleSet = moduleLoader.getModuleList();
        loadModuleConfig();

        for(Instruction i : fullMatchCommand.values()) {
            if(i.getObject().getClass().equals(ModuleControl.class)) {
                moduleControl = (ModuleControl) i.getObject();
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
        Instruction instruction = fullMatchCommand.get(message.getContent());
        if(instruction == null) return;

        String moduleName = instruction.getObject().getClass().getAnnotation(Module.class).value();
        boolean isDefaultEnable = instruction.getObject().getClass().getAnnotation(Module.class).defaultEnable();
        String channelId = message.getChannelId();

        try {
            if(isDefaultEnable) {
                JsonArray value = moduleOff.getAsJsonArray(moduleName);
                if(value == null || !value.contains(new JsonPrimitive(channelId)))
                    instruction.getMethod().invoke(instruction.getObject(), message);
            }
            else {
                JsonArray value = moduleOn.getAsJsonArray(moduleName);
                if(value != null && value.contains(new JsonPrimitive(channelId)))
                    instruction.getMethod().invoke(instruction.getObject(), message);
            }
            logger.info("Event: " + instruction.getObject().getClass().getName() + "." + instruction.getMethod().getName());
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


}
