package com.snowmeow.tomonsdk.model;

/* 消息反应emoji */
public class PartialEmoji {

    /* 群组定义 Emoji 的 ID，如果是系统 Emoji，则没有 ID */
    private String id;
    /* Emoji 名称，或者系统 Emoji 的字符 */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
