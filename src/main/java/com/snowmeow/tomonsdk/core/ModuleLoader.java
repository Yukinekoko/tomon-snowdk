package com.snowmeow.tomonsdk.core;

import com.snowmeow.tomonsdk.Service;
import com.snowmeow.tomonsdk.annotation.Module;
import com.snowmeow.tomonsdk.annotation.OnMessage;
import com.snowmeow.tomonsdk.net.Route;
import com.snowmeow.tomonsdk.module.BaseModule;
import com.snowmeow.tomonsdk.util.LoggerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader {

    private static final Logger logger = LogManager.getLogger(LoggerType.MAIN);
    private static final String PACKAGE_NAME = "com.snowmeow.tomonsdk.module";

    private Service service;

    /* 模块类Set */
    private Set<Class<?>> classSet;

    public ModuleLoader(String token) {
        this.service = new Service(new Route(token));
        this.classSet = classSet = findClassWithSuperclass(findClass(PACKAGE_NAME), BaseModule.class);
    }

    /** 获取指定包下的所有Class对象
     *
     * */
    private Set<Class<?>> findClass(String packageName) {

        Set<Class<?>> classSet;

        //检测当前是class文件运行还是从jar包运行
        URL url = this.getClass()
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();

        if(new File(url.getPath()).isFile()) {  //jar包
            logger.debug("Run from JAR file...");
            classSet = findClassFromJar(packageName);
        }
        else {  //class文件
            logger.debug("Run from class file...");
            classSet = findClassFromClass(packageName);
        }
        return classSet;
    }

    /** class文件运行下获取指定包下的所有Class对象
     *
     *  */
    private Set<Class<?>> findClassFromClass(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        //获取工程目录
        String classpath = this.getClass().getClassLoader().getResource("").getPath();
        //包名转路径名
        String packagePath = packageName.replace(".", File.separator);
        //获得包的绝对路径
        String searchPath = classpath + packagePath;

        //获取该目录下的所有class文件路径
        List<String> classPath = new ArrayList<String>();
        File file = new File(searchPath);
        File[] classFile = file.listFiles();

        for(File i : classFile) {
            if(!i.isFile()) {
                classSet.addAll(findClassFromClass(packageName + "." + i.getName()));
            }
            else {
                if (i.getName().endsWith(".class")) {
                    classPath.add(packageName + "." + i.getName().replace(".class", ""));
                }
            }

        }

        //获得类对象
        for(String className : classPath) {
            try {
                Class<?> clazz = Class.forName(className);
                classSet.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classSet;
    }

    /** Jar包运行下获取指定包下的所有Class对象
     * @param packageName 指定的包名
     * @return 获取到的Class set
     * */
    private Set<Class<?>> findClassFromJar(String packageName) {

        Set<Class<?>> classSet = new HashSet<Class<?>>();

        URL fileUrl = this.getClass()
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();

        try {
            URL jarUrl = new URL("jar:file:" + fileUrl.getPath() + "!/");
            JarURLConnection jarURLConnection = (JarURLConnection) jarUrl.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> jarEntry = jarFile.entries();
            while(jarEntry.hasMoreElements()) {
                JarEntry jar = jarEntry.nextElement();

                String jarName = jar.getName();
                if(jar.isDirectory() || !jarName.endsWith(".class")) {
                    continue;
                }

                String className = jarName
                        .replace("/", ".")
                        .replace(".class", "");
                if(!className.contains(packageName)) continue;

                classSet.add(Class.forName(className));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return classSet;
    }

    /** 筛选继承指定类的Class对象
     *
     * */
    private Set<Class<?>> findClassWithSuperclass(Set<Class<?>> classSet, Class<?> superclass) {

        Set<Class<?>> responseSet = new HashSet<Class<?>>();

        for(Class<?> clazz : classSet)
            if(clazz.getSuperclass().equals(superclass)) {
                responseSet.add(clazz);
                logger.debug("Find plugin class: " + clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1));
            }

        return responseSet;
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

    /** 加载指定消息事件
     *
     * */
    public Map<String, ModuleInstruction> instructionLoader(OnMessage.Type type) {

        Map<Class<?>, Set<Method>> methodMap = new HashMap<Class<?>, Set<Method>>();
        //获取每一个类带有OnMessage注解的方法Set并注定type类型
        for (Class clazz : classSet) {
            Set<Method> allMethodSet = findMethodWithAnnotation(clazz, OnMessage.class);
            Set<Method> currentMethodSet = new HashSet<Method>();

            for(Method m : allMethodSet) {
                if (m.getAnnotation(OnMessage.class).type().equals(type)) {
                    currentMethodSet.add(m);
                }
            }
            methodMap.put(clazz, currentMethodSet);
        }


        Map<String, ModuleInstruction> commandMap = new HashMap<String, ModuleInstruction>();

        for(Map.Entry<Class<?>, Set<Method>> entry : methodMap.entrySet()) {
            try {
                // 实例化对象
                Object classObject = entry.getKey().newInstance();
                Field serviceField = entry.getKey().getDeclaredField("service");
                serviceField.setAccessible(true);
                serviceField.set(classObject, service);
                //获取指令内容并加入Map
                for(Method method : entry.getValue()) {
                    String word = method.getAnnotation(OnMessage.class).value();
                    commandMap.put(word, new ModuleInstruction(classObject, method));
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

    /* 获取模块信息列表 */
    public Set<Module> getModuleList() {
        Set<Module> moduleSet = new HashSet<Module>();
        for(Class<?> clazz : classSet)
            moduleSet.add(clazz.getAnnotation(Module.class));

        return moduleSet;
    }

    /** 获取一个模块中指定type的OnMessage事件 */
    public Map<String, Method> findEventMethod(Class<?> clazz, OnMessage.Type type) {

        Map<String, Method> methodMap = new HashMap<String, Method>();

        Method[] allMethod = clazz.getMethods();

        for(Method m : allMethod) {
            OnMessage message = m.getAnnotation(OnMessage.class);
            if(message != null && message.type() == type) {
                methodMap.put(message.value(), m);
            }
        }

        return methodMap;

    }

    /** 获取模块Set */
    public Set<ModuleInstruction> getModuleSet() {
        Set<ModuleInstruction> moduleInstructionSet = new HashSet<ModuleInstruction>();

        for(Class<?> clazz : classSet) {

            Module moduleMessage = clazz.getAnnotation(Module.class);
            if(moduleMessage == null) { //error
                logger.error("模块" + clazz.getName() + "没有 Module 注解，加载失败！");
                continue;
            }

            try {
                Object moduleInstance = clazz.newInstance();
                Field serviceField = clazz.getDeclaredField("service");
                serviceField.setAccessible(true);
                serviceField.set(moduleInstance, service);

                ModuleInstruction moduleInstruction = new ModuleInstruction(
                        moduleInstance,
                        moduleMessage.value(),
                        moduleMessage.help(),
                        moduleMessage.defaultEnable());
                //fullMatch
                moduleInstruction.setInstructionFullMatch(findEventMethod(clazz, OnMessage.Type.FULL_MATCH));
                //prefix
                moduleInstruction.setInstructionPrefix(findEventMethod(clazz, OnMessage.Type.PREFIX));
                //regex
                moduleInstruction.setInstructionRegex(findEventMethod(clazz, OnMessage.Type.REGEX));

                moduleInstructionSet.add(moduleInstruction);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return moduleInstructionSet;
    }


}
