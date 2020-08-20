package com.snowmeow.tomonsdk.model;

import com.google.gson.annotations.SerializedName;

/** E m o j i
 * @author snowmeow
 * @date 2020/08/20
 * */
public class Emoji {

    /* emoji ID */
    private String id;
    /* 群组ID */
    @SerializedName("guild_id")
    private String guildId;
    /* 名称 */
    private String name;
    /* 图片 */
    private String img;
    /* 图片链接 */
    private String img_url;
    /* 用户数据 */
    private User user;
    /* (类型未知?) */
    private String managed;
    /* 是否为动图(boolean?) */
    private boolean animated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getManaged() {
        return managed;
    }

    public void setManaged(String managed) {
        this.managed = managed;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }
}
