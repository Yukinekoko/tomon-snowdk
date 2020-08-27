package com.snowmeow.tomonsdk.plugin;

import com.snowmeow.tomonsdk.Service;

/** 插件的父类
 * @author snowmeow
 * @date 2020/08/25
 * */
public abstract class BasePlugin {

    /** 创建对象时调用的初始化方法 */
    public abstract void onCreate();
    /** 创建完所有插件对象后调用的初始化方法 */
    public abstract void onOpen();


}
