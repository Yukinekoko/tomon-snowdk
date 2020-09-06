package com.snowmeow.tomonsdk.core;

import java.lang.reflect.Method;
import java.util.Map;

/** 模块命令集
 *
 * @date 2020/09/04
 * */
public class ModuleInstruction {

    private Object classInstance;

    private String moduleName;

    private String help;

    private boolean defaultEnable;

    private Map<String, Method> instructionFullMatch;

    private Map<String, Method> instructionPrefix;

    private Map<String, Method> instructionRegex;

    public ModuleInstruction(Object classInstance, String moduleName, String help, boolean defaultEnable) {
        this.moduleName = moduleName;
        this.classInstance = classInstance;
        this.help = help;
        this.defaultEnable = defaultEnable;
    }

    public Object getClassInstance() {
        return classInstance;
    }

    public void setClassInstance(Object classInstance) {
        this.classInstance = classInstance;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public boolean isDefaultEnable() {
        return defaultEnable;
    }

    public void setDefaultEnable(boolean defaultEnable) {
        this.defaultEnable = defaultEnable;
    }

    public Map<String, Method> getInstructionFullMatch() {
        return instructionFullMatch;
    }

    public void setInstructionFullMatch(Map<String, Method> instructionFullMatch) {
        this.instructionFullMatch = instructionFullMatch;
    }

    public Map<String, Method> getInstructionPrefix() {
        return instructionPrefix;
    }

    public void setInstructionPrefix(Map<String, Method> instructionPrefix) {
        this.instructionPrefix = instructionPrefix;
    }

    public Map<String, Method> getInstructionRegex() {
        return instructionRegex;
    }

    public void setInstructionRegex(Map<String, Method> instructionRegex) {
        this.instructionRegex = instructionRegex;
    }

    /** @deprecated */
    private Object object;
    /** @deprecated */
    private Method method;

    /**
     * @deprecated
     * */
    public ModuleInstruction(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    /**
     * @deprecated
     * */
    public Object getObject() {
        return object;
    }

    /**
     * @deprecated
     * */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @deprecated
     * */
    public Method getMethod() {
        return method;
    }

    /**
     * @deprecated
     * */
    public void setMethod(Method method) {
        this.method = method;
    }
}
