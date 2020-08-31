package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/* 表情 */
public class Stamp {

    /* 表情ID */
    private String id;
    /* 表情别名 */
    private String alias;
    /* 作者ID */
    @SerializedName("author_id")
    private String authorId;
    /* 表情排序坐标 */
    private int position;
    /* hash值 */
    private String hash;
    /* 是否为动图 */
    private boolean animated;
    /* 链接 */
    private String url;
    /* 宽 */
    private int width;
    /* 高 */
    private int height;
    /* 更新于 */
    @SerializedName("update_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
