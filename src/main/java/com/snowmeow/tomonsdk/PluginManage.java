package com.snowmeow.tomonsdk;

import com.snowmeow.tomonsdk.model.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class PluginManage {

    /* fullMatch事件 */
    private Map<String, PluginLoader.Command> fullMatchCommand;
    /* regular事件 */
    private Map<String, PluginLoader.Command> regular;
    /* 前缀匹配 */



    public void load(String token) {

        PluginLoader pluginLoader = new PluginLoader(token);
        this.fullMatchCommand = pluginLoader.fullMatchLoader();

    }

    public void sendMessage(Message message) throws InvocationTargetException, IllegalAccessException {
        PluginLoader.Command command = fullMatchCommand.get(message.getContent());
        if(command == null) return;
        command.method.invoke(command.object, message);
        System.out.println("调用方法！");
    }

}
