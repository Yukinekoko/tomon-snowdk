package com.snowmeow.tomonsdk;

import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.model.Message;
import com.snowmeow.tomonsdk.net.Route;
import com.snowmeow.tomonsdk.plugin.BasePlugin;
import okhttp3.WebSocket;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class PluginLoader {

    static final String PACKAGE_NAME = "com.snowmeow.tomonsdk.plugin";

    private Service service;

    private Set<Class<?>> classSet;

    public PluginLoader(String token) {
        this.service = new Service(new Route(token));
        this.classSet = findClass(PACKAGE_NAME);
    }

    /** 获取指定包下的所有Class对象
     *
     *  */
    private Set<Class<?>> findClass(String packageName) {

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

        //获得类对象
        for(String className : classPath) {
            try {
                Class<?> clazz = Class.forName(className);
                if(clazz.getSuperclass().equals(BasePlugin.class)) {
                    classSet.add(clazz);
                    System.out.println(clazz.getName());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classSet;
    }

    /** 获取带有指定注解的Method对象
     *
     *  */
    private Set<Method> findMethodWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {

        Set<Method> methodSet = new HashSet<Method>();

        Method[] allMethod = clazz.getMethods();

        for(Method m : allMethod)
            if (m.getAnnotation(annotation) != null)
                methodSet.add(m);

        return methodSet;
    }

    /** 加载full match事件
     *
     * */
    public Map<String, Command> fullMatchLoader() {

        Map<Class<?>, Set<Method>> methodMap = new HashMap<Class<?>, Set<Method>>();
        for (Class clazz : classSet)
            methodMap.put(clazz, findMethodWithAnnotation(clazz, OnMessage.class));

        Map<String, Command> commandMap = new HashMap<String, Command>();

        for(Map.Entry<Class<?>, Set<Method>> entry : methodMap.entrySet()) {
            try {
               /* // 获取有参参构造器
                Constructor constructor = entry.getKey().getEnclosingConstructor();*/
                // 实例化对象
                Object classObject = entry.getKey().newInstance();
                Field serviceField = entry.getKey().getDeclaredField("service");
                serviceField.setAccessible(true);
                serviceField.set(classObject, service);
                //获取指令内容并加入Map
                for(Method method : entry.getValue()) {
                    String word = method.getAnnotation(OnMessage.class).value();
                    commandMap.put(word, new Command(classObject, method));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return commandMap;

    }

    static class Command {

        Object object;

        Method method;

        public Command(Object object, Method method) {
            this.object = object;
            this.method = method;
        }
    }



}
