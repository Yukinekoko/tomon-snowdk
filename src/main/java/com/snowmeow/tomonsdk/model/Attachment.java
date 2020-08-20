package com.snowmeow.tomonsdk.model;

/* 消息附件 */
public class Attachment {

    /* 消息附件ID */
    private String id;
    /* 附件文件名 */
    private String filename;
    /* 附件文件的hash */
    private String hash;
    /* 附件图片宽度 */
    private int width;
    /* 附件图片高度 */
    private int height;
    /* 附件文件大小 */
    private long size;
    /* 附件文件链接 */
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
