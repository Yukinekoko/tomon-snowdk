package com.snowmeow.tomonsdk;

import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.net.API;
import okhttp3.WebSocket;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Event {

    static final String GUILD_CREATE = "GUILD_CREATE";
    static final String GUILD_DELETE = "GUILD_DELETE";
    static final String GUILD_UPDATE = "GUILD_UPDATE";
    static final String GUILD_POSITION = "GUILD_POSITION";
    static final String CHANNEL_CREATE = "CHANNEL_CREATE";
    static final String CHANNEL_DELETE = "CHANNEL_DELETE";
    static final String CHANNEL_UPDATE = "CHANNEL_UPDATE";
    static final String CHANNEL_POSITION = "CHANNEL_POSITION";
    static final String GUILD_ROLE_CREATE = "GUILD_ROLE_CREATE";
    static final String GUILD_ROLE_DELETE = "GUILD_ROLE_DELETE";
    static final String GUILD_ROLE_UPDATE = "GUILD_ROLE_UPDATE";
    static final String GUILD_ROLE_POSITION = "GUILD_ROLE_POSITION";
    static final String GUILD_MEMBER_ADD = "GUILD_MEMBER_ADD";
    static final String GUILD_MEMBER_REMOVE = "GUILD_MEMBER_REMOVE";
    static final String GUILD_MEMBER_UPDATE = "GUILD_MEMBER_UPDATE";
    static final String MESSAGE_CREATE = "MESSAGE_CREATE";
    static final String MESSAGE_DELETE = "MESSAGE_DELETE";
    static final String MESSAGE_UPDATE = "MESSAGE_UPDATE";
    static final String MESSAGE_REACTION_ADD = "MESSAGE_REACTION_ADD";
    static final String MESSAGE_REACTION_REMOVE = "MESSAGE_REACTION_REMOVE";
    static final String MESSAGE_REACTION_REMOVE_ALL = "MESSAGE_REACTION_REMOVE_ALL";
    static final String EMOJI_CREATE = "EMOJI_CREATE";
    static final String EMOJI_DELETE = "EMOJI_DELETE";
    static final String EMOJI_UPDATE = "EMOJI_UPDATE";
    static final String VOICE_STATE_UPDATE = "VOICE_STATE_UPDATE";
    static final String USER_TYPING = "USER_TYPING";
    static final String USER_PRESENCE_UPDATE = "USER_PRESENCE_UPDATE";

    WebSocket webSocket;
    API api;
    Map<String, Command> commandMap;

    public Event(WebSocket webSocket, API api) {
        this.webSocket = webSocket;
        this.api = api;
        commandMap = new HashMap<String, Command>();
        searchClass("com.snowmeow.tomonsdk.plugin");
    }

    public void searchClass(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        //获取工程目录
        String classpath = this.getClass().getResource("/").getPath();
        //包名转路径名
        String packagePath = packageName.replace(".", File.separator);
        //获得包的绝对路径
        String searchPath = classpath + packagePath;
        System.out.println(searchPath);

        //获取该目录下的所有class文件路径
        List<String> classPath = new ArrayList<String>();
        File file = new File(searchPath);
        File[] classFile = file.listFiles();
        for(File i : classFile) {
            if (i.getName().endsWith(".class")) {
                classPath.add(packageName + "." + i.getName().replace(".class", ""));
            }
        }

        try {
            //获得类对象
            for(String className : classPath)
                classSet.add(Class.forName(className));

            //获取实例化的对象
            List<Object> classObject = new ArrayList<Object>();
            for(Class<?> c : classSet) {
                Constructor constructor = c.getDeclaredConstructor(WebSocket.class, API.class);
                classObject.add(constructor.newInstance(webSocket, api));
            }

            Object classTest = classObject.get(0);

            //扫描指定注解的Method
            List<Method> methodList = new ArrayList<Method>();

            Method[] allMethod = classTest.getClass().getMethods();
            for(Method m : allMethod) {
                if (m.getAnnotation(OnMessage.class) != null) {
                    String commandWord = m.getAnnotation(OnMessage.class).value();
                    Command command = new Command();
                    command.object = classTest;
                    command.method = m;
                    commandMap.put(commandWord, command);
                }
            }
        }
        catch (Exception e) {
            System.out.println("class error!");
        }


    }

    public void sendMessage(String commandWord, String channelId) throws InvocationTargetException, IllegalAccessException {
        Command command = commandMap.get(commandWord);
        if(command == null) return;
        command.method.invoke(command.object, channelId);
        System.out.println("调用方法！");
    }


}
